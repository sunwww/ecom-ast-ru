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
  if (request.getParameter("short")==null) {
%>
		<msh:form action="/contract_reports_services.do" defaultField="dateFrom">
			<msh:panel>
				<msh:row>
				<msh:textField property="dateFrom" label="c"/>
				<msh:textField property="dateTo" label="по"/>
				</msh:row>
				<msh:row>
					<msh:autoComplete property="department" label="Отделение" vocName="lpu" horizontalFill="true" fieldColSpan="3"/>
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
	        	<input type="radio" name="typeGroup" value="2"> услугам итог
	        </td>
	        <td onclick="this.childNodes[1].checked='checked';">
	        	<input type="radio" name="typeGroup" value="3"> отделения итог
	        </td>
	        <td onclick="this.childNodes[1].checked='checked';">
	        	<input type="radio" name="typeGroup" value="4"> реестр
	        </td>
        </msh:row>				
        <msh:row>
        	<msh:submitCancelButtonsRow labelSave="Сформировать" doNotDisableButtons="cancel" labelSaving="Формирование..." colSpan="4"/>
        </msh:row>
			</msh:panel>
		</msh:form>
		
<%
  } else{
	  %>
		<msh:form action="/contract_reports_services.do" defaultField="dateFrom">
			<msh:panel>
				<msh:row>
				<msh:textField viewOnlyField="true" property="dateFrom" label="c"/>
				<msh:textField viewOnlyField="true" property="dateTo" label="по"/>
				</msh:row>
				<msh:row>
					<msh:autoComplete viewOnlyField="true" property="department" label="Отделение" vocName="lpu" horizontalFill="true" fieldColSpan="3"/>
				</msh:row>
				<msh:row>
					<msh:autoComplete viewOnlyField="true" property="nationality" fieldColSpan="4" label="Гражданство" vocName="omcOksm" horizontalFill="true"/>
				</msh:row>
				<msh:row>
					<msh:autoComplete viewOnlyField="true" property="operator" fieldColSpan="4" label="Оператор" vocName="workFunction" horizontalFill="true"/>
				</msh:row>
				<msh:row>
					<msh:autoComplete viewOnlyField="true" property="priceList" label="Прейскурант" fieldColSpan="3"  vocName="priceList" horizontalFill="true" />
				</msh:row>
				<msh:row>
					<msh:autoComplete viewOnlyField="true" property="priceMedService" parentAutocomplete="priceList" label="Медицинская услуга" vocName="priceMedServiceByPriceList" horizontalFill="true" fieldColSpan="3"/>
				</msh:row>
			</msh:panel>
		</msh:form>
		
<%  }
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
		
		if (typeGroup.equals("1")||typeGroup.equals("2")) {
			// Группировка по услугам 
   			request.setAttribute("groupSql", "pp.code||' '||pp.name") ;
   			request.setAttribute("groupSqlId", "'&priceMedService='||pms.id") ;
   			request.setAttribute("groupName", "Услуга") ;
       		request.setAttribute("groupGroupNext", "4") ;
   			request.setAttribute("groupGroup", "pms.id,pp.code,pp.name,pp.isVat") ;
   			request.setAttribute("groupOrder", "pp.code") ;
		} else if (typeGroup.equals("3")){
			// Группировка по отделению 
   			request.setAttribute("groupSql", "lpu.name") ;
   			request.setAttribute("groupSqlId", "'&department='||lpu.id") ;
   			request.setAttribute("groupName", "Отделение") ;
       		request.setAttribute("groupGroupNext", "2") ;
   			request.setAttribute("groupGroup", "lpu.id,lpu.name") ;
   			request.setAttribute("groupOrder", "lpu.name") ;
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
		ActionUtil.setParameterFilterSql("department","lpu.id", request) ;
		%>
		<% if (typeGroup!=null&& typeGroup.equals("1")) {%>
			<msh:section title="Финасовый отчет по услугам за период ${FromTo} ">
			<ecom:webQuery name="finansReport" nativeSql="
SELECT ${groupSqlId}||${operatorSqlId}||${priceMedServiceSqlId}||${departmentSqlId}||${priceListSqlId}||'&dateFrom=${param.dateFrom}&dateTo=${param.dateTo}' as sqlId
,${groupSql} as dateNum
,list(distinct lpu.name)
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
left join PricePosition pg on pg.id=pp.parent_id
left join MisLpu lpu on lpu.id=pg.lpu_id
left join WorkFunction wf on wf.id=cao.workFunction_id
left join VocWorkFunction vwf on vwf.id=wf.workFunction_id
left join Worker w on w.id=wf.worker_id
left join Patient wp on wp.id=w.person_id
WHERE	CAo.operationdate between to_date('${param.dateFrom}', 'dd.mm.yyyy') AND to_date('${param.dateTo}', 'dd.mm.yyyy') 
and (cao.dtype='OperationAccrual' or cao.dtype='OperationReturn')  ${priceMedServiceSql} ${operatorSql} ${priceListSql}
${nationalitySql} ${departmentSql}
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
							<th></th>
							<th colspan="4">Оплата</th>
							<th colspan="4">Возврат</th>
							<th></th>
						</tr>
					</msh:tableNotEmpty>
					<msh:tableColumn columnName="Отделения" property="3" />
					<msh:tableColumn columnName="${groupName}" property="2" />
					<msh:tableColumn columnName="Кол-во" isCalcAmount="true" property="4" />
					<msh:tableColumn columnName="Кол-во со скидкой" isCalcAmount="true" property="5" />
					<msh:tableColumn columnName="Сумма" isCalcAmount="true" property="6" />
					<msh:tableColumn columnName="с учетом скидки" isCalcAmount="true" property="7" />
					<msh:tableColumn columnName="Кол-во" isCalcAmount="true" property="8" />
					<msh:tableColumn columnName="Кол-во со скидкой" isCalcAmount="true" property="9" />
					<msh:tableColumn columnName="Сумма" isCalcAmount="true" property="10" />
					<msh:tableColumn columnName="с учетом скидки с НДС" isCalcAmount="true" property="11" />
				</msh:table>

			</msh:section>	
	<%} else if (typeGroup!=null&& typeGroup.equals("2")) {%>
			<msh:section title="Финасовый отчет по услугам за период ${FromTo} ">
			<ecom:webQuery name="finansReport" nativeSql="
SELECT ${groupSqlId}||${operatorSqlId}||${priceMedServiceSqlId}||${departmentSqlId}||${priceListSqlId}||'&dateFrom=${param.dateFrom}&dateTo=${param.dateTo}' as sqlId
,${groupSql} as dateNum
,list(distinct lpu.name)

, sum(case when cao.dtype='OperationAccrual' then cams.countMedService else 0 end) as sumCountMedService 
, sum(case when cao.dtype='OperationAccrual' then round(cams.countMedService*(cams.cost*(100-coalesce(cao.discount,0))/100),2) else 0 end) sumNoAccraulMedServiceWithDiscount  
, sum(case when cao.dtype='OperationAccrual' then round(cams.countMedService*(cams.cost*(100-coalesce(cao.discount,0))*(case when pp.isVat='1' then 0.87 else 1 end)/100),2) else 0 end) sumNoAccraulMedServiceWithDiscountWithoutVat  

, sum(case when cao.dtype='OperationReturn' then cams.countMedService else 0 end) as sumCountMedServiceRet 
, sum(case when cao.dtype='OperationReturn' then round(cams.countMedService*(cams.cost*(100-coalesce(cao.discount,0))/100),2) else 0 end) sumNoAccraulMedServiceWithDiscountRet  
, sum(case when cao.dtype='OperationReturn' then round(cams.countMedService*(cams.cost*(100-coalesce(cao.discount,0))*(case when pp.isVat='1' then 0.87 else 1 end)/100),2) else 0 end) sumNoAccraulMedServiceWithDiscountRetWithoutVat  

, sum(case when cao.dtype='OperationAccrual' then round(cams.countMedService*(cams.cost*(100-coalesce(cao.discount,0))/100),2) else 0 end)   
-
 sum(case when cao.dtype='OperationReturn' then round(cams.countMedService*(cams.cost*(100-coalesce(cao.discount,0))/100),2) else 0 end)
    
as sumItog
, (sum(case when cao.dtype='OperationAccrual' then round(cams.countMedService*(case when pp.isVat='1' then 0.87 else 1 end)*(cams.cost*(100-coalesce(cao.discount,0))/100),2) else 0 end)   
-
 sum(case when cao.dtype='OperationReturn' then round(cams.countMedService*(case when pp.isVat='1' then 0.87 else 1 end)*(cams.cost*(100-coalesce(cao.discount,0))/100),2) else 0 end)
 )   
as sumItogWithoutVat
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
left join PricePosition pg on pg.id=pp.parent_id
left join MisLpu lpu on lpu.id=pg.lpu_id

left join WorkFunction wf on wf.id=cao.workFunction_id
left join VocWorkFunction vwf on vwf.id=wf.workFunction_id
left join Worker w on w.id=wf.worker_id
left join Patient wp on wp.id=w.person_id
WHERE	CAo.operationdate between to_date('${param.dateFrom}', 'dd.mm.yyyy') AND to_date('${param.dateTo}', 'dd.mm.yyyy') 
and (cao.dtype='OperationAccrual' or cao.dtype='OperationReturn')  ${priceMedServiceSql} ${operatorSql} ${priceListSql}
${nationalitySql} ${departmentSql}
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
							<th></th>
							<th colspan="3">Оплата</th>
							<th colspan="3">Возврат</th>
							<th colspan="2">Итог</th>
						</tr>
					</msh:tableNotEmpty>
					<msh:tableColumn columnName="Отделение" property="3" />
					<msh:tableColumn columnName="${groupName}" property="2" />
					<msh:tableColumn columnName="Кол-во" isCalcAmount="true" property="4" />
					<msh:tableColumn columnName="Сумма с НДС" isCalcAmount="true" property="5" />
					<msh:tableColumn columnName="Сумма без НДС" isCalcAmount="true" property="6" />
					<msh:tableColumn columnName="Кол-во" isCalcAmount="true" property="7" />
					<msh:tableColumn columnName="Сумма с НДС" isCalcAmount="true" property="8" />
					<msh:tableColumn columnName="Сумма без НДС" isCalcAmount="true" property="9" />
					<msh:tableColumn columnName="Итог с НДС" isCalcAmount="true" property="10" />
					<msh:tableColumn columnName="Итог без НДС" isCalcAmount="true" property="11" />
				</msh:table>

			</msh:section>	
	<%} else if (typeGroup!=null&& typeGroup.equals("3")) {%>
			<msh:section title="Финасовый отчет по услугам за период ${FromTo} ">
			<ecom:webQuery name="finansReport" nativeSql="
SELECT ${groupSqlId}||${operatorSqlId}||${priceMedServiceSqlId}||${departmentSqlId}||${priceListSqlId}||'&dateFrom=${param.dateFrom}&dateTo=${param.dateTo}' as sqlId
,${groupSql} as dateNum

, sum(case when cao.dtype='OperationAccrual' then cams.countMedService else 0 end) as sumCountMedService 
, sum(case when cao.dtype='OperationAccrual' then round(cams.countMedService*(cams.cost*(100-coalesce(cao.discount,0))/100),2) else 0 end) sumNoAccraulMedServiceWithDiscount  
, sum(case when cao.dtype='OperationAccrual' then round(cams.countMedService*(cams.cost*(100-coalesce(cao.discount,0))*(case when pp.isVat='1' then 0.87 else 1 end)/100),2) else 0 end) sumNoAccraulMedServiceWithDiscountWithoutVat  

, sum(case when cao.dtype='OperationReturn' then cams.countMedService else 0 end) as sumCountMedServiceRet 
, sum(case when cao.dtype='OperationReturn' then round(cams.countMedService*(cams.cost*(100-coalesce(cao.discount,0))/100),2) else 0 end) sumNoAccraulMedServiceWithDiscountRet  
, sum(case when cao.dtype='OperationReturn' then round(cams.countMedService*(cams.cost*(100-coalesce(cao.discount,0))*(case when pp.isVat='1' then 0.87 else 1 end)/100),2) else 0 end) sumNoAccraulMedServiceWithDiscountRetWithoutVat  

, sum(case when cao.dtype='OperationAccrual' then round(cams.countMedService*(cams.cost*(100-coalesce(cao.discount,0))/100),2) else 0 end)   
-
 sum(case when cao.dtype='OperationReturn' then round(cams.countMedService*(cams.cost*(100-coalesce(cao.discount,0))/100),2) else 0 end)
    
as sumItog
, (sum(case when cao.dtype='OperationAccrual' then round(cams.countMedService*(case when pp.isVat='1' then 0.87 else 1 end) *(cams.cost*(100-coalesce(cao.discount,0))/100),2) else 0 end)   
-
 sum(case when cao.dtype='OperationReturn' then round(cams.countMedService*(case when pp.isVat='1' then 0.87 else 1 end) *(cams.cost*(100-coalesce(cao.discount,0))/100),2) else 0 end)
 )
as sumItogWithoutVat
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
left join PricePosition pg on pg.id=pp.parent_id
left join MisLpu lpu on lpu.id=pg.lpu_id

left join WorkFunction wf on wf.id=cao.workFunction_id
left join VocWorkFunction vwf on vwf.id=wf.workFunction_id
left join Worker w on w.id=wf.worker_id
left join Patient wp on wp.id=w.person_id
WHERE	CAo.operationdate between to_date('${param.dateFrom}', 'dd.mm.yyyy') AND to_date('${param.dateTo}', 'dd.mm.yyyy') 
and (cao.dtype='OperationAccrual' or cao.dtype='OperationReturn')  ${priceMedServiceSql} ${operatorSql} ${priceListSql}
${nationalitySql} ${departmentSql}
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
							<th colspan="3">Оплата</th>
							<th colspan="3">Возврат</th>
							<th colspan="2">Итог</th>
						</tr>
					</msh:tableNotEmpty>
					<msh:tableColumn columnName="${groupName}" property="2" />
					<msh:tableColumn columnName="Кол-во" isCalcAmount="true" property="3" />
					<msh:tableColumn columnName="Сумма с НДС" isCalcAmount="true" property="4" />
					<msh:tableColumn columnName="Сумма без НДС" isCalcAmount="true" property="5" />
					<msh:tableColumn columnName="Кол-во" isCalcAmount="true" property="6" />
					<msh:tableColumn columnName="Сумма с НДС" isCalcAmount="true" property="7" />
					<msh:tableColumn columnName="Сумма без НДС" isCalcAmount="true" property="8" />
					<msh:tableColumn columnName="с НДС" isCalcAmount="true" property="9" />
					<msh:tableColumn columnName="без НДС" isCalcAmount="true" property="10" />
				</msh:table>

			</msh:section>	
	<%} else if (typeGroup!=null&& typeGroup.equals("4")) {%>
			<msh:section title="Финасовый отчет за период ${FromTo} ">
			<ecom:webQuery name="finansReport" nativeSql="
SELECT cao.id
,MC.contractnumber || ' '||to_char(mc.dateFrom,'dd.mm.yyyy') as dateNum
,coalesce(CCP.lastname||' '||CCP.firstname||' '||CCP.middlename||' г.р. '||to_char(CCP.birthday,'dd.mm.yyyy')
,CCO.name) as kontragent
,round(sum(case when cao.dtype='OperationAccrual' then cams.cost*cams.countMedService*(100-coalesce(cao.discount,0))/100 else 0 end),2) as accrualSum
,round(sum(case when cao.dtype='OperationReturn' then cams.cost*cams.countMedService*(100-coalesce(cao.discount,0))/100 else 0 end),2) as returnSum
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
left join PricePosition pg on pg.id=pp.parent_id
left join MisLpu lpu on lpu.id=pg.lpu_id


left join WorkFunction wf on wf.id=cao.workFunction_id
left join VocWorkFunction vwf on vwf.id=wf.workFunction_id
left join Worker w on w.id=wf.worker_id
left join Patient wp on wp.id=w.person_id
WHERE	CAo.operationdate between to_date('${param.dateFrom}', 'dd.mm.yyyy') AND to_date('${param.dateTo}', 'dd.mm.yyyy') 
and (cao.dtype='OperationAccrual' or cao.dtype='OperationReturn')  ${priceMedServiceSql} ${operatorSql} ${priceListSql}
${nationalitySql} ${departmentSql}
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