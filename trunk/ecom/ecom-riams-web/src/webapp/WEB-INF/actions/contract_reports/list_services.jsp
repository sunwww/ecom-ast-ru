<%@page import="ru.ecom.mis.web.action.util.ActionUtil"%>
<%@page import="ru.ecom.ejb.services.query.WebQueryResult"%>
<%@page import="java.util.List"%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://struts.apache.org/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://www.nuzmsh.ru/tags/msh" prefix="msh" %>
<%@ taglib uri="http://www.ecom-ast.ru/tags/ecom" prefix="ecom" %>
<%@ taglib tagdir="/WEB-INF/tags" prefix="tags" %>

<tiles:insert page="/WEB-INF/tiles/main${param.short}Layout.jsp" flush="true" >
	<tiles:put name='title' type='string'>
		<msh:title mainMenu="Contract" >Отчет по услугам</msh:title>
	</tiles:put>
	<tiles:put name='side' type='string'>
	<tags:contractMenu currentAction="serviciesReport"/>
	</tiles:put>
	<tiles:put name='body' type='string' >
  <%
  	String typeGroup =ActionUtil.updateParameter("Form039Action","typeGroup","1", request) ;
%>
		<msh:form action="/contract_reports_services.do" defaultField="dateFrom">
			<msh:panel>
				<msh:row>
				<msh:textField property="dateFrom" label="c"/>
				<msh:textField property="dateTo" label="по"/>
				</msh:row>
				<msh:row>
					<msh:autoComplete property="nationality" fieldColSpan="4" label="Гражданство" vocName="omcOksm" horizontalFill="true"/>
				</msh:row>
				<msh:row>
					<msh:autoComplete property="operator" fieldColSpan="4" label="Оператор" vocName="workFunction" horizontalFill="true"/>
				</msh:row>
				<msh:row>
					<msh:autoComplete property="priceList" label="Прейскурант" fieldColSpan="3"  vocName="priceList" horizontalFill="true" />
				</msh:row>
				<msh:row>
					<msh:autoComplete property="priceMedService" parentAutocomplete="priceList" label="Медицинская услуга" vocName="priceMedServiceByPriceList" horizontalFill="true" fieldColSpan="3"/>
				</msh:row>
        <msh:row>
	        <td class="label" title="Группировака (typePatient)" colspan="1"><label for="typeGroupName" id="typeGroupLabel">Группировка по:</label></td>
	        <td onclick="this.childNodes[1].checked='checked';">
	        	<input type="radio" name="typeGroup" value="1"> услугам
	        </td>
	        <td onclick="this.childNodes[1].checked='checked';">
	        	<input type="radio" name="typeGroup" value="2"> реестр
	        </td>
        </msh:row>				
        <msh:row>
        	<msh:submitCancelButtonsRow labelSave="Сформировать" doNotDisableButtons="cancel" labelSaving="Формирование..." colSpan="4"/>
        </msh:row>
			</msh:panel>
		</msh:form>
<%
		String dateFrom = request.getParameter("dateFrom") ;
		String dFrom = "" ;
		if (dateFrom==null ||dateFrom.equals("") ) {
			dFrom=" is null " ;
		} else {
			dFrom = ">=to_date('"+dateFrom+"', 'dd.mm.yyyy')" ;
		}
		request.setAttribute("dFrom",dFrom) ;
		
		String dateTo = request.getParameter("dateTo") ;
		String dTo = "" ;
		if (dateTo==null ||dateTo.equals("") ) {
			dTo=" is null " ;
		} else {
			dTo = "<=to_date('"+dateTo+"', 'dd.mm.yyyy')" ;
		}
		request.setAttribute("dTo",dTo) ;
		
		String FromTo = "";
		if  (dateTo==null ||dateTo.equals("") ) {}
		else if (dateFrom==null ||dateFrom.equals("") ) {}
		else FromTo="C "+dFrom+" По "+dTo;
		
		if (typeGroup.equals("1")) {
			// Группировка по услугам 
   			request.setAttribute("groupSql", "pp.code||' '||pp.name") ;
   			request.setAttribute("groupSqlId", "'&priceMedService='||pms.id") ;
   			request.setAttribute("groupName", "Услуга") ;
       		request.setAttribute("groupGroupNext", "2") ;
   			request.setAttribute("groupGroup", "pms.id,pp.code,pp.name") ;
   			request.setAttribute("groupOrder", "pp.code") ;
		} else {
			//Реестр
   			request.setAttribute("groupSql", "pms.name") ;
   			request.setAttribute("groupSqlId", "'&priceMedService='||pms.id") ;
   			request.setAttribute("groupName", "Сотрудник") ;
   			request.setAttribute("groupGroup", "pms.id,pms.code,pms.name") ;
   			request.setAttribute("groupOrder", "pms.code") ;
		}
		ActionUtil.setParameterFilterSql("operator","cao.workFunction_id", request) ;
		ActionUtil.setParameterFilterSql("priceMedService","pms.id", request) ;
		ActionUtil.setParameterFilterSql("priceList","pp.priceList_id", request) ;
		ActionUtil.setParameterFilterSql("nationality","ccp.nationality_id", request) ;
		%>
		<% if (typeGroup!=null&& typeGroup.equals("1")) {%>
			<msh:section title="Финасовый отчет по услугам за период ${FromTo} ">
			<ecom:webQuery name="finansReport" nativeSql="
SELECT ${groupSqlId}||${operatorSqlId}||${priceMedServiceSqlId}||${priceListSqlId}||'&dateFrom=${param.dateFrom}&dateTo=${param.dateTo}' as sqlId
,${groupSql} as dateNum

, sum(case when cao.dtype='OperationAccrual' then cams.countMedService else 0 end) as sumCountMedService 
, sum(case when cao.dtype='OperationAccrual' and coalesce(cao.discount,0)>0 then cams.countMedService else 0 end) as sumCountMedServiceWithDiscount 
, sum(case when cao.dtype='OperationAccrual' then cams.countMedService*cams.cost else 0 end) as sumNoAccraulMedService 
, sum(case when cao.dtype='OperationAccrual' then round(cams.countMedService*(cams.cost*(100-coalesce(cao.discount,0))/100),2) else 0 end) sumNoAccraulMedServiceWithDiscount  

, sum(case when cao.dtype='OperationReturn' then cams.countMedService else 0 end) as sumCountMedServiceRet 
, sum(case when cao.dtype='OperationReturn' and coalesce(cao.discount,0)>0 then cams.countMedService else 0 end) as sumCountMedServiceWithDiscountRet 
, sum(case when cao.dtype='OperationReturn' then cams.countMedService*cams.cost else 0 end) as sumNoAccraulMedServiceRet 
, sum(case when cao.dtype='OperationReturn' then round(cams.countMedService*(cams.cost*(100-coalesce(cao.discount,0))/100),2) else 0 end) sumNoAccraulMedServiceWithDiscountRet  

FROM medcontract MC
LEFT JOIN contractaccount as CA ON CA.contract_id=MC.id 
LEFT JOIN contractPerson CC ON CC.id=MC.customer_id
LEFT JOIN patient CCP ON CCP.id=CC.patient_id
LEFT JOIN VocOrg CCO ON CCO.id=CC.organization_id
left join ContractAccountOperation CAO on CAO.account_id=CA.id 
left join ContractAccountOperationByService caos on cao.id=caos.accountOperation_id
left join ContractAccountMedService cams on caos.accountMedService_id=cams.id

left join PriceMedService pms on pms.id=cams.medService_id
left join PricePosition pp on pp.id=pms.pricePosition_id

left join WorkFunction wf on wf.id=cao.workFunction_id
left join VocWorkFunction vwf on vwf.id=wf.workFunction_id
left join Worker w on w.id=wf.worker_id
left join Patient wp on wp.id=w.person_id
WHERE	CAo.operationdate between to_date('${param.dateFrom}', 'dd.mm.yyyy') AND to_date('${param.dateTo}', 'dd.mm.yyyy') 
and (cao.dtype='OperationAccrual' or cao.dtype='OperationReturn')  ${priceMedServiceSql} ${operatorSql} ${priceListSql}
${nationalitySql}
group by ${groupGroup}
order by ${groupOrder}
			"/>

				<msh:table name="finansReport" 
				action="contract_reports_services.do?typeGroup=${groupGroupNext}" 
				viewUrl="contract_reports_services.do?typeGroup=${groupGroupNext}&short=Short" 
				idField="1">
					<msh:tableNotEmpty>
						<tr>
							<th></th>
							<th></th>
							<th colspan="4">Оплата</th>
							<th colspan="4">Возврат</th>
						</tr>
					</msh:tableNotEmpty>
					<msh:tableColumn columnName="${groupName}" property="2" />
					<msh:tableColumn columnName="Кол-во" isCalcAmount="true" property="3" />
					<msh:tableColumn columnName="Кол-во со скидкой" isCalcAmount="true" property="4" />
					<msh:tableColumn columnName="Сумма" isCalcAmount="true" property="5" />
					<msh:tableColumn columnName="с учетом скидки" isCalcAmount="true" property="6" />
					<msh:tableColumn columnName="Кол-во" isCalcAmount="true" property="7" />
					<msh:tableColumn columnName="Кол-во со скидкой" isCalcAmount="true" property="8" />
					<msh:tableColumn columnName="Сумма" isCalcAmount="true" property="9" />
					<msh:tableColumn columnName="с учетом скидки" isCalcAmount="true" property="10" />
				</msh:table>

			</msh:section>	
	<%} else if (typeGroup!=null&& typeGroup.equals("2")) {%>
			<msh:section title="Финасовый отчет за период ${FromTo} ">
			<ecom:webQuery name="finansReport" nativeSql="
SELECT cao.id
,MC.contractnumber || ' '||to_char(mc.dateFrom,'dd.mm.yyyy') as dateNum
,coalesce(CCP.lastname||' '||CCP.firstname||' '||CCP.middlename||' г.р. '||to_char(CCP.birthday,'dd.mm.yyyy')
,CCO.name) as kontragent
,round(sum(case when cao.dtype='OperationAccrual' then cao.cost*(100-coalesce(cao.discount,0))/100 else 0 end),2) as accrualSum
,round(sum(case when cao.dtype='OperationReturn' then cao.cost*(100-coalesce(cao.discount,0))/100 else 0 end),2) as returnSum
,wp.lastname||' '||wp.firstname||' '||wp.middlename
, sum(case when cao.dtype='OperationAccrual' then cams.countMedService else 0 end) as sumCountMedService 

FROM medcontract MC
LEFT JOIN contractaccount as CA ON CA.contract_id=MC.id 
LEFT JOIN contractPerson CC ON CC.id=MC.customer_id
LEFT JOIN patient CCP ON CCP.id=CC.patient_id
LEFT JOIN VocOrg CCO ON CCO.id=CC.organization_id
left join ContractAccountOperation CAO on CAO.account_id=CA.id 

left join ContractAccountOperationByService caos on cao.id=caos.accountOperation_id
left join ContractAccountMedService cams on caos.accountMedService_id=cams.id

left join PriceMedService pms on pms.id=cams.medService_id
left join PricePosition pp on pp.id=pms.pricePosition_id


left join WorkFunction wf on wf.id=cao.workFunction_id
left join VocWorkFunction vwf on vwf.id=wf.workFunction_id
left join Worker w on w.id=wf.worker_id
left join Patient wp on wp.id=w.person_id
WHERE	CAo.operationdate between to_date('${param.dateFrom}', 'dd.mm.yyyy') AND to_date('${param.dateTo}', 'dd.mm.yyyy') 
and (cao.dtype='OperationAccrual' or cao.dtype='OperationReturn')  ${priceMedServiceSql} ${operatorSql} ${priceListSql}
${nationalitySql}
group by cao.id,mc.id,CCP.lastname,CCP.firstname,CCP.middlename,CCP.birthday,CCO.name,MC.contractnumber,mc.dateFrom
,wp.lastname,wp.firstname,wp.middlename
			"/>

				<msh:table name="finansReport" 
				action="entitySubclassView-contract_accountOperation.do" 
				idField="1">
					<msh:tableColumn columnName="#" property="sn" />
					<msh:tableColumn columnName="Договор" property="2" />
					<msh:tableColumn columnName="Наименование контрагента" property="3" />
					<msh:tableColumn columnName="Оплачено" isCalcAmount="true" property="4" />
					<msh:tableColumn columnName="Возврат"  isCalcAmount="true" property="5" />
					<msh:tableColumn columnName="Оператор" property="6" />
					<msh:tableColumn columnName="Кол-во" isCalcAmount="true" property="7" />
				</msh:table>

			</msh:section>
	<%} %>
	</tiles:put>
  <tiles:put name="javascript" type="string">
  	<script type="text/javascript">

    checkFieldUpdate('typeGroup','${typeGroup}',1) ;
    checkFieldUpdate('typeView','${typeView}',1) ;
    checkFieldUpdate('typeDtype','${typeDtype}',3) ;
    checkFieldUpdate('typeDate','${typeDate}',2) ;
    
    
    function checkFieldUpdate(aField,aValue,aDefault) {
    	
    	eval('var chk =  document.forms[0].'+aField) ;
    	var max = chk.length ;
    	if ((+aValue)>max) {
    		chk[+aDefault-1].checked='checked' ;
    	} else {
    		chk[+aValue-1].checked='checked' ;
    	}
    }
    </script>
    </tiles:put>
</tiles:insert>