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
		<msh:title mainMenu="Contract" >Финансовый отчет</msh:title>
	</tiles:put>
	<tiles:put name='side' type='string'>
	<tags:contractMenu currentAction="financeReport"/>
	</tiles:put>
	<tiles:put name='body' type='string' >
  <%
  	String typeGroup =ActionUtil.updateParameter("Form039Action","typeGroup","1", request) ;
%>
		<msh:form action="/contract_reports_finance.do" defaultField="dateFrom">
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
	        <td class="label" title="Группировака (typePatient)" colspan="1"><label for="typeGroupName" id="typeGroupLabel">Группировка по:</label></td>
	        <td onclick="this.childNodes[1].checked='checked';">
	        	<input type="radio" name="typeGroup" value="1">  датам
	        </td>
	        <td onclick="this.childNodes[1].checked='checked';">
	        	<input type="radio" name="typeGroup" value="2"> операторам
	        </td>
	        <td onclick="this.childNodes[1].checked='checked';">
	        	<input type="radio" name="typeGroup" value="3"> реестр
	        </td>
	        <td onclick="this.childNodes[1].checked='checked';">
	        	<input type="radio" name="typeGroup" value="4"> гражданству
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
			// Группировка по дате
			request.setAttribute("groupSql", "to_char(CAo.operationdate,'dd.mm.yyyy')") ;
       		request.setAttribute("groupSqlId", "'&dateFrom='||to_char(CAo.operationdate,'dd.mm.yyyy')||'&dateTo='||to_char(CAo.operationdate,'dd.mm.yyyy')") ;
       		request.setAttribute("groupName", "Дате операции") ;
       		request.setAttribute("groupGroup", "CAo.operationdate") ;
       		request.setAttribute("groupGroupNext", "2") ;
       		request.setAttribute("groupOrder", "CAo.operationdate") ;
		} else if (typeGroup.equals("2")) {
			// Группировка по операторам
   			request.setAttribute("groupSql", "wp.lastname||' '||wp.firstname||' '||wp.middlename") ;
   			request.setAttribute("groupSqlId", "'&operator='||wf.id") ;
   			request.setAttribute("groupName", "Оператор") ;
   			request.setAttribute("groupGroup", "wf.id,vwf.name,wp.lastname,wp.firstname,wp.middlename") ;
       		request.setAttribute("groupGroupNext", "3") ;
   			request.setAttribute("groupOrder", "wp.lastname") ;
		} else if (typeGroup.equals("4")) {
			// Группировка по гражданству
   			request.setAttribute("groupSql", "vn.name") ;
   			request.setAttribute("groupSqlId", "'&nationality='||vn.id") ;
   			request.setAttribute("groupName", "Гражданство") ;
   			request.setAttribute("groupGroup", "vn.id,vn.name") ;
       		request.setAttribute("groupGroupNext", "3") ;
   			request.setAttribute("groupOrder", "vn.name") ;
		/*} else if (typeGroup.equals("3")) {
			// Группировка по услугам 
   			request.setAttribute("groupSql", "pp.code||' '||pp.name") ;
   			request.setAttribute("groupSqlId", "'&medService='||pms.id") ;
   			request.setAttribute("groupName", "Услуга") ;
       		request.setAttribute("groupGroupNext", "4") ;
   			request.setAttribute("groupGroup", "pms.id,pp.code,pp.name") ;
   			request.setAttribute("groupOrder", "pp.code") ;*/
		} else {
			//Реестр
   			request.setAttribute("groupSql", "pms.name") ;
   			request.setAttribute("groupSqlId", "'&medService='||pms.id") ;
   			request.setAttribute("groupName", "Сотрудник") ;
   			request.setAttribute("groupGroup", "pms.id,pms.code,pms.name") ;
   			request.setAttribute("groupOrder", "pms.code") ;
		}
		ActionUtil.setParameterFilterSql("operator","cao.workFunction_id", request) ;
		ActionUtil.setParameterFilterSql("medService","pms.id", request) ;
		ActionUtil.setParameterFilterSql("nationality","ccp.nationality_id", request) ;
		%>
		<% if (typeGroup!=null && (typeGroup.equals("1") || typeGroup.equals("2")|| typeGroup.equals("4"))) {%>
			<msh:section>
			<ecom:webQuery name="finansReport_operators" nameFldSql="finansReport_operators_sql" nativeSql="
SELECT wp.lastname as sqlId
,wp.lastname||' '||substring(wp.firstname,1,1)||substring(wp.middlename,1,1) as wpfio
FROM medcontract MC
LEFT JOIN contractaccount as CA ON CA.contract_id=MC.id
left join ContractAccountOperation CAO on CAO.account_id=CA.id 
left join ContractAccountOperationByService caos on cao.id=caos.accountOperation_id
left join ContractAccountMedService cams on caos.accountMedService_id=cams.id

left join PriceMedService pms on pms.id=cams.medService_id
left join PricePosition pp on pp.id=pms.pricePosition_id
 
LEFT JOIN contractPerson CC ON CC.id=MC.customer_id
LEFT JOIN patient CCP ON CCP.id=CC.patient_id
left join Omc_Oksm vn on vn.id=ccp.nationality_id
LEFT JOIN VocOrg CCO ON CCO.id=CC.organization_id
left join WorkFunction wf on wf.id=cao.workFunction_id
left join VocWorkFunction vwf on vwf.id=wf.workFunction_id
left join Worker w on w.id=wf.worker_id
left join Patient wp on wp.id=w.person_id
WHERE	CAo.operationdate between to_date('${param.dateFrom}', 'dd.mm.yyyy') AND to_date('${param.dateTo}', 'dd.mm.yyyy') 
and (cao.dtype='OperationAccrual' or cao.dtype='OperationReturn') ${operatorSql}
${nationalitySql}
group by wp.lastname,wp.firstname,wp.middlename
order by wp.lastname
			"/>

			<ecom:webQuery name="finansReport_without_vat" nameFldSql="finansReport_without_vat_sql" nativeSql="
SELECT ${groupSqlId}||${operatorSqlId}||${medServiceSqlId}||'&dateFrom=${param.dateFrom}&dateTo=${param.dateTo}' as sqlId
,${groupSql} as dateNum
,round(sum(case when cao.dtype='OperationAccrual' then cams.cost*cams.countMedService*(100-coalesce(cao.discount,0))/100 else 0 end),2) as accrualSum
,1 as cnt1
,round(sum(case when cao.dtype='OperationReturn' then cams.cost*cams.countMedService*(100-coalesce(cao.discount,0))/100 else 0 end),2) as returnSum
,1 as cnt2
,list(distinct wp.lastname||' '||wp.firstname||' '||wp.middlename) as wpfio
FROM medcontract MC
LEFT JOIN contractaccount as CA ON CA.contract_id=MC.id
left join ContractAccountOperation CAO on CAO.account_id=CA.id 
left join ContractAccountOperationByService caos on cao.id=caos.accountOperation_id
left join ContractAccountMedService cams on caos.accountMedService_id=cams.id
left join PriceMedService pms on pms.id=cams.medService_id
left join PricePosition pp on pp.id=pms.pricePosition_id
LEFT JOIN contractPerson CC ON CC.id=MC.customer_id
LEFT JOIN patient CCP ON CCP.id=CC.patient_id
left join Omc_Oksm vn on vn.id=ccp.nationality_id
LEFT JOIN VocOrg CCO ON CCO.id=CC.organization_id
left join WorkFunction wf on wf.id=cao.workFunction_id
left join VocWorkFunction vwf on vwf.id=wf.workFunction_id
left join Worker w on w.id=wf.worker_id
left join Patient wp on wp.id=w.person_id
WHERE	CAo.operationdate between to_date('${param.dateFrom}', 'dd.mm.yyyy') AND to_date('${param.dateTo}', 'dd.mm.yyyy') 
and (cao.dtype='OperationAccrual' or cao.dtype='OperationReturn') ${operatorSql}
${nationalitySql}
and (pp.isVat='0' or pp.isVat is null)
group by ${groupGroup}
order by ${groupOrder}
			"/>

			<ecom:webQuery name="finansReport_with_vat" nameFldSql="finansReport_with_vat_sql" nativeSql="
SELECT ${groupSqlId}||${operatorSqlId}||${medServiceSqlId}||'&dateFrom=${param.dateFrom}&dateTo=${param.dateTo}' as sqlId
,${groupSql} as dateNum
,round(sum(case when cao.dtype='OperationAccrual' then cams.cost*cams.countMedService*(100-coalesce(cao.discount,0))/100 else 0 end),2) as accrualSum
,round(sum(case when cao.dtype='OperationAccrual' then cams.cost*cams.countMedService*0.13*(100-coalesce(cao.discount,0))/100 else 0 end),2) as accrualSumVat
,round(sum(case when cao.dtype='OperationReturn' then cams.cost*cams.countMedService*(100-coalesce(cao.discount,0))/100 else 0 end),2) as returnSum
,round(sum(case when cao.dtype='OperationReturn' then cams.cost*cams.countMedService*0.13*(100-coalesce(cao.discount,0))/100 else 0 end),2) as returnSumVat
,list(distinct wp.lastname||' '||wp.firstname||' '||wp.middlename) as wpfio
FROM medcontract MC
LEFT JOIN contractaccount as CA ON CA.contract_id=MC.id
left join ContractAccountOperation CAO on CAO.account_id=CA.id 
left join ContractAccountOperationByService caos on cao.id=caos.accountOperation_id
left join ContractAccountMedService cams on caos.accountMedService_id=cams.id

left join PriceMedService pms on pms.id=cams.medService_id
left join PricePosition pp on pp.id=pms.pricePosition_id
 
LEFT JOIN contractPerson CC ON CC.id=MC.customer_id
LEFT JOIN patient CCP ON CCP.id=CC.patient_id
left join Omc_Oksm vn on vn.id=ccp.nationality_id
LEFT JOIN VocOrg CCO ON CCO.id=CC.organization_id
left join WorkFunction wf on wf.id=cao.workFunction_id
left join VocWorkFunction vwf on vwf.id=wf.workFunction_id
left join Worker w on w.id=wf.worker_id
left join Patient wp on wp.id=w.person_id
WHERE	CAo.operationdate between to_date('${param.dateFrom}', 'dd.mm.yyyy') AND to_date('${param.dateTo}', 'dd.mm.yyyy') 
and (cao.dtype='OperationAccrual' or cao.dtype='OperationReturn') ${operatorSql}
${nationalitySql}
and pp.isVat='1'
group by ${groupGroup}
order by ${groupOrder}
			"/>

			<msh:sectionTitle> 
    <form action="print-contract_finance_group_operator.do" method="post" target="_blank">
    Финасовый отчет за период ${FromTo}
    <input type='hidden' name="sqlText3" id="sqlText3" value="${finansReport_operators_sql}"> 
    <input type='hidden' name="sqlText2" id="sqlText2" value="${finansReport_with_vat_sql}">
    <input type='hidden' name="sqlText1" id="sqlText1" value="${finansReport_without_vat_sql}">
    <input type='hidden' name="sqlCount" id="sqlCount" value="3">
    <input type='hidden' name="totalCount" id="totalCount" value="1">
    <input type='hidden' name="totalList1" id="totalList1" value="1,2">
    <input type='hidden' name="sqlInfo1" id="sqlInfo1" value="${param.dateFrom}-${param.dateTo}.">
    <input type='hidden' name="sqlInfo2" id="sqlInfo2" value="${groupName}">
    <input type='hidden' name="sqlInfo2" id="sqlInfo3" value="${groupName}">
    <input type='hidden' name="s" id="s" value="PrintService">
    <input type='hidden' name="m" id="m" value="printManyNativeQuery">
    <input type="submit" value="Печать"> 
    </form>
			</msh:sectionTitle>
			<msh:sectionTitle>Услуги без НДС</msh:sectionTitle>

				<msh:table name="finansReport_without_vat" 
				action="contract_reports_finance.do?typeGroup=${groupGroupNext}"
				viewUrl="contract_reports_finance.do?typeGroup=${groupGroupNext}&short=Short" 
				idField="1">
					<msh:tableColumn columnName="${groupName}" property="2" />
					<msh:tableColumn columnName="Оплачено" isCalcAmount="true" property="3" />
					<msh:tableColumn columnName="Возврат" isCalcAmount="true" property="5" />
					<msh:tableColumn columnName="Оператор(ы)" property="7" />
				</msh:table>
</msh:section>
<msh:section title="Услуги с НДС">

				<msh:table name="finansReport_with_vat" 
				action="contract_reports_finance.do?typeGroup=${groupGroupNext}"
				viewUrl="contract_reports_finance.do?typeGroup=${groupGroupNext}&short=Short" 
				idField="1">
					<msh:tableColumn columnName="${groupName}" property="2" />
					<msh:tableColumn columnName="Оплачено" isCalcAmount="true" property="3" />
					<msh:tableColumn columnName="НДС" isCalcAmount="true" property="4" />
					<msh:tableColumn columnName="Возврат" isCalcAmount="true" property="5" />
					<msh:tableColumn columnName="НДС" isCalcAmount="true" property="6" />
					<msh:tableColumn columnName="Оператор(ы)" property="7" />
				</msh:table>

			</msh:section>
	<%} else if (typeGroup!=null&& typeGroup.equals("3")) {%>
			<msh:section>
			<msh:sectionTitle> 
			<ecom:webQuery name="finansReport_operators" nameFldSql="finansReport_operators_sql" nativeSql="
SELECT wp.lastname as sqlId
,wp.lastname||' '||substring(wp.firstname,1,1)||substring(wp.middlename,1,1) as wpfio
FROM medcontract MC
LEFT JOIN contractaccount as CA ON CA.contract_id=MC.id
left join ContractAccountOperation CAO on CAO.account_id=CA.id 
left join ContractAccountOperationByService caos on cao.id=caos.accountOperation_id
left join ContractAccountMedService cams on caos.accountMedService_id=cams.id

left join PriceMedService pms on pms.id=cams.medService_id
left join PricePosition pp on pp.id=pms.pricePosition_id
 
LEFT JOIN contractPerson CC ON CC.id=MC.customer_id
LEFT JOIN patient CCP ON CCP.id=CC.patient_id
left join Omc_Oksm vn on vn.id=ccp.nationality_id
LEFT JOIN VocOrg CCO ON CCO.id=CC.organization_id
left join WorkFunction wf on wf.id=cao.workFunction_id
left join VocWorkFunction vwf on vwf.id=wf.workFunction_id
left join Worker w on w.id=wf.worker_id
left join Patient wp on wp.id=w.person_id
WHERE	CAo.operationdate between to_date('${param.dateFrom}', 'dd.mm.yyyy') AND to_date('${param.dateTo}', 'dd.mm.yyyy') 
and (cao.dtype='OperationAccrual' or cao.dtype='OperationReturn') ${operatorSql}
${nationalitySql}
group by wp.lastname,wp.firstname,wp.middlename
order by wp.lastname
			"/>

			<ecom:webQuery name="finansReport_without_vat" nameFldSql="finansReport_without_vat_sql" nativeSql="
SELECT cao.id as caoid
,MC.contractnumber || ' '||to_char(mc.dateFrom,'dd.mm.yyyy') as dateNum
,coalesce(CCP.lastname||' '||CCP.firstname||' '||CCP.middlename||' г.р. '||to_char(CCP.birthday,'dd.mm.yyyy'),CCO.name) as kontragent
,round(sum(case when cao.dtype='OperationAccrual' then cams.cost*cams.countMedService*(100-coalesce(cao.discount,0))/100 else 0 end),2) as accrualSum
,1 as cnt1
,round(sum(case when cao.dtype='OperationReturn' then cams.cost*cams.countMedService*(100-coalesce(cao.discount,0))/100 else 0 end),2) as returnSum
,1 as cnt2
,wp.lastname||' '||wp.firstname||' '||wp.middlename as wpfio
FROM medcontract MC
LEFT JOIN contractaccount as CA ON CA.contract_id=MC.id
left join ContractAccountOperation CAO on CAO.account_id=CA.id 
left join ContractAccountOperationByService caos on cao.id=caos.accountOperation_id
left join ContractAccountMedService cams on caos.accountMedService_id=cams.id
left join PriceMedService pms on pms.id=cams.medService_id
left join PricePosition pp on pp.id=pms.pricePosition_id
LEFT JOIN contractPerson CC ON CC.id=MC.customer_id
LEFT JOIN patient CCP ON CCP.id=CC.patient_id
left join Omc_Oksm vn on vn.id=ccp.nationality_id
LEFT JOIN VocOrg CCO ON CCO.id=CC.organization_id
left join WorkFunction wf on wf.id=cao.workFunction_id
left join VocWorkFunction vwf on vwf.id=wf.workFunction_id
left join Worker w on w.id=wf.worker_id
left join Patient wp on wp.id=w.person_id
WHERE	CAo.operationdate between to_date('${param.dateFrom}', 'dd.mm.yyyy') AND to_date('${param.dateTo}', 'dd.mm.yyyy') 
and (cao.dtype='OperationAccrual' or cao.dtype='OperationReturn')  ${operatorSql} ${nationalitySql}
and (pp.isVat='0' or pp.isVat is null)
group by cao.id,mc.id,CCP.lastname,CCP.firstname,CCP.middlename,CCP.birthday,CCO.name,MC.contractnumber,mc.dateFrom
,wp.lastname,wp.firstname,wp.middlename,cao.operationDate,cao.operationTime
order by cao.operationDate,cao.operationTime
			"/>
			<ecom:webQuery name="finansReport_with_vat" nameFldSql="finansReport_with_vat_sql" nativeSql="
SELECT cao.id as caoid
,MC.contractnumber || ' '||to_char(mc.dateFrom,'dd.mm.yyyy') as dateNum
,coalesce(CCP.lastname||' '||CCP.firstname||' '||CCP.middlename||' г.р. '||to_char(CCP.birthday,'dd.mm.yyyy'),CCO.name) as kontragent
,round(sum(case when cao.dtype='OperationAccrual' then cams.cost*cams.countMedService*(100-coalesce(cao.discount,0))/100 else 0 end),2) as accrualSum
,round(sum(case when cao.dtype='OperationAccrual' then cams.cost*cams.countMedService*0.13*(100-coalesce(cao.discount,0))/100 else 0 end),2) as accrualSumVat
,round(sum(case when cao.dtype='OperationReturn' then cams.cost*cams.countMedService*(100-coalesce(cao.discount,0))/100 else 0 end),2) as returnSum
,round(sum(case when cao.dtype='OperationReturn' then cams.cost*cams.countMedService*0.13*(100-coalesce(cao.discount,0))/100 else 0 end),2) as returnSumVat
,wp.lastname||' '||wp.firstname||' '||wp.middlename as wpfio
FROM medcontract MC
LEFT JOIN contractaccount as CA ON CA.contract_id=MC.id
left join ContractAccountOperation CAO on CAO.account_id=CA.id 
left join ContractAccountOperationByService caos on cao.id=caos.accountOperation_id
left join ContractAccountMedService cams on caos.accountMedService_id=cams.id
left join PriceMedService pms on pms.id=cams.medService_id
left join PricePosition pp on pp.id=pms.pricePosition_id
LEFT JOIN contractPerson CC ON CC.id=MC.customer_id
LEFT JOIN patient CCP ON CCP.id=CC.patient_id
left join Omc_Oksm vn on vn.id=ccp.nationality_id
LEFT JOIN VocOrg CCO ON CCO.id=CC.organization_id
left join WorkFunction wf on wf.id=cao.workFunction_id
left join VocWorkFunction vwf on vwf.id=wf.workFunction_id
left join Worker w on w.id=wf.worker_id
left join Patient wp on wp.id=w.person_id
WHERE	CAo.operationdate between to_date('${param.dateFrom}', 'dd.mm.yyyy') AND to_date('${param.dateTo}', 'dd.mm.yyyy') 
and (cao.dtype='OperationAccrual' or cao.dtype='OperationReturn')  ${operatorSql} ${nationalitySql}
and (pp.isVat='1')
group by cao.id,mc.id,CCP.lastname,CCP.firstname,CCP.middlename,CCP.birthday,CCO.name,MC.contractnumber,mc.dateFrom
,wp.lastname,wp.firstname,wp.middlename,cao.operationDate,cao.operationTime
order by cao.operationDate,cao.operationTime
			"/>
    <form action="print-contract_finance_reestr_operator.do" method="post" target="_blank">
    Финасовый отчет за период ${FromTo}
    <input type='hidden' name="sqlText3" id="sqlText3" value="${finansReport_operators_sql}"> 
    <input type='hidden' name="sqlText2" id="sqlText2" value="${finansReport_with_vat_sql}">
    <input type='hidden' name="sqlText1" id="sqlText1" value="${finansReport_without_vat_sql}">
    <input type='hidden' name="sqlCount" id="sqlCount" value="3">
    <input type='hidden' name="totalCount" id="totalCount" value="1">
    <input type='hidden' name="totalList1" id="totalList1" value="1,2">
    <input type='hidden' name="sqlInfo1" id="sqlInfo1" value="${param.dateFrom}-${param.dateTo}.">
    <input type='hidden' name="sqlInfo2" id="sqlInfo2" value="${groupName}">
    <input type='hidden' name="sqlInfo2" id="sqlInfo3" value="${groupName}">
    <input type='hidden' name="s" id="s" value="PrintService">
    <input type='hidden' name="m" id="m" value="printManyNativeQuery">
    <input type="submit" value="Печать"> 
    </form>
			</msh:sectionTitle>
			<msh:sectionTitle>Услуги без НДС</msh:sectionTitle>

				<msh:table name="finansReport_without_vat" 
				action="entitySubclassView-contract_accountOperation.do" 
				idField="1">
					<msh:tableColumn columnName="Договор" property="2" />
					<msh:tableColumn columnName="Наименование контрагента" property="3" />
					<msh:tableColumn columnName="Оплачено" isCalcAmount="true" property="4" />
					<msh:tableColumn columnName="Возврат" isCalcAmount="true" property="6" />
					<msh:tableColumn columnName="Оператор(ы)" property="8" />
				</msh:table>

			</msh:section>
			<msh:section>
			<msh:sectionTitle>Услуги с НДС</msh:sectionTitle>

				<msh:table name="finansReport_with_vat" 
				action="entitySubclassView-contract_accountOperation.do" 
				idField="1">
					<msh:tableColumn columnName="Договор" property="2" />
					<msh:tableColumn columnName="Наименование контрагента" property="3" />
					<msh:tableColumn columnName="Оплачено" isCalcAmount="true" property="4" />
					<msh:tableColumn columnName="НДС" isCalcAmount="true" property="5" />
					<msh:tableColumn columnName="Возврат" isCalcAmount="true" property="6" />
					<msh:tableColumn columnName="НДС" isCalcAmount="true" property="7" />
					<msh:tableColumn columnName="Оператор(ы)" property="8" />
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