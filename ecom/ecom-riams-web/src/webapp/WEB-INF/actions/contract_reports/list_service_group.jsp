<%@page import="java.text.DecimalFormatSymbols"%>
<%@page import="java.text.DecimalFormat"%>
<%@page import="java.math.BigDecimal"%>
<%@page import="java.util.ArrayList"%>
<%@page import="java.util.Collection"%>
<%@page import="ru.ecom.web.util.ActionUtil"%>
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
	<tags:contractMenu currentAction="serviciesReportGroup"/>
	</tiles:put>
	<tiles:put name='body' type='string' >
  <%
  	String typeGroup =ActionUtil.updateParameter("Form039Action","typeGroup","1", request) ;
  if (request.getParameter("short")==null) {
%>
		<msh:form action="/contract_reports_services_group.do" defaultField="dateFrom">
			<msh:panel>
				<msh:row>
				<msh:textField property="dateFrom" label="c"/>
				<msh:textField property="dateTo" label="по"/>
				</msh:row>
				<msh:row>
					<msh:autoComplete property="department" label="Отделение" vocName="lpu" horizontalFill="true" fieldColSpan="3"/>
				</msh:row>
				<msh:row>
					<msh:autoComplete property="departmentType" fieldColSpan="4" label="Тип отделения" vocName="vocLpuFunction" horizontalFill="true"/>
				</msh:row>
				<msh:row>
					<msh:autoComplete property="nationality" fieldColSpan="4" label="Гражданство" vocName="omcOksm" horizontalFill="true"/>
				</msh:row>
				<msh:row>
					<msh:autoComplete property="operator" fieldColSpan="4" label="Оператор" vocName="workFunction" horizontalFill="true"/>
				</msh:row>
				<msh:row>
					<msh:autoComplete property="positionType" fieldColSpan="4" label="Тип услуги" vocName="vocPositionType" horizontalFill="true"/>
				</msh:row>
				<msh:row>
					<msh:autoComplete property="priceList" label="Прейскурант" fieldColSpan="4"  vocName="priceList" horizontalFill="true" />
				</msh:row>
				<msh:row>
					<msh:autoComplete property="priceMedService" parentAutocomplete="priceList" label="Медицинская услуга" vocName="priceMedServiceByPriceList" horizontalFill="true" fieldColSpan="4"/>
				</msh:row>
				
        <msh:row>
	        <td class="label" title="Группировака (typePatient)" colspan="1"><label for="typeGroupName" id="typeGroupLabel">Группировка по:</label></td>
	        <td onclick="this.childNodes[1].checked='checked';">
	        	<input type="radio" name="typeGroup" value="1"> услуги по отделениям
	        </td>
	        <td onclick="this.childNodes[1].checked='checked';">
	        	<input type="radio" name="typeGroup" value="2"> разбивка по пациентам
	        </td>
	        <td onclick="this.childNodes[1].checked='checked';">
	        	<input type="radio" name="typeGroup" value="3"> разбивка по специалистам
	        </td>
	        <td onclick="this.childNodes[1].checked='checked';">
	        	<input type="radio" name="typeGroup" value="4"> ???
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
		<msh:form action="/contract_reports_services_group.do" defaultField="dateFrom">
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
					<msh:autoComplete viewOnlyField="true" property="positionType" fieldColSpan="4" label="Тип услуги" vocName="vocPositionType" horizontalFill="true"/>
				</msh:row>
				<msh:row>
					<msh:autoComplete viewOnlyField="true" property="priceList" label="Прейскурант" fieldColSpan="4"  vocName="priceList" horizontalFill="true" />
				</msh:row>
				<msh:row>
					<msh:autoComplete viewOnlyField="true" property="priceMedService" parentAutocomplete="priceList" label="Медицинская услуга" vocName="priceMedServiceByPriceList" horizontalFill="true" fieldColSpan="4"/>
				</msh:row>
			</msh:panel>
		</msh:form>
		
<%  }
  
		String dateFrom = request.getParameter("dateFrom") ;
		String dateTo = request.getParameter("dateTo") ;
		if (dateFrom!=null) {
	if (dateFrom!=null && !dateTo.equals("")) {
		
		if (dateTo==null || dateTo.equals("")) {
			dateTo=dateFrom ;
		}
		
		if (typeGroup.equals("2")||typeGroup.equals("2")) {
			// Группировка по услугам 
   			request.setAttribute("groupSql", "pp.code||' '||pp.name") ;
   			request.setAttribute("groupSql1", "lpu.name") ;
   			request.setAttribute("groupSqlId", "'&priceMedService='||pms.id") ;
   			request.setAttribute("groupSqlId1", "'&lpu='||lpu.id") ;
   			request.setAttribute("groupSqlIdN1", "lpu.id") ;
   			request.setAttribute("groupName", "Услуга") ;
       		request.setAttribute("groupGroupNext", "5") ;
   			request.setAttribute("groupGroup", "pms.id,pp.code,pp.name,pp.isVat,lpu.name") ;
   			request.setAttribute("groupGroup1", "lpu.id,lpu.name") ;
   			request.setAttribute("groupOrder", "lpu.name,pp.code") ;
   			request.setAttribute("groupOrder1", "lpu.name") ;
		} else if (typeGroup.equals("3")){
			// Группировка по отделению 
   			request.setAttribute("groupSql", "lpu.name") ;
   			request.setAttribute("groupSqlId", "'&department='||lpu.id") ;
   			request.setAttribute("groupName", "Отделение") ;
       		request.setAttribute("groupGroupNext", "2") ;
   			request.setAttribute("groupGroup", "lpu.id,lpu.name") ;
   			request.setAttribute("groupOrder", "lpu.name") ;
		} else if (typeGroup.equals("4")){
			// Группировка по типу услуг 
   			request.setAttribute("groupSql", "vpt.name") ;
   			request.setAttribute("groupSqlId", "'&department='||lpu.id||'&positionType='||vpt.id") ;
   			request.setAttribute("groupName", "Тип услуги") ;
       		request.setAttribute("groupGroupNext", "2") ;
   			request.setAttribute("groupGroup", "lpu.id,lpu.name,vpt.id,vpt.name") ;
   			request.setAttribute("groupOrder", "lpu.name,vpt.name") ;
		} else if (typeGroup.equals("1")) {
		
			//Реестр
   			request.setAttribute("groupSql", "pp.code||' '||pp.name") ;
   			request.setAttribute("groupSqlId", "'&priceMedService='||pms.id") ;
   			request.setAttribute("groupName", "Сотрудник") ;
   			request.setAttribute("groupGroup", "pms.id,pp.code,pp.name,pp.isVat") ;
   			request.setAttribute("groupOrder", "pp.code") ;
		}
		ActionUtil.setParameterFilterSql("operator","cao.workFunction_id", request) ;
		ActionUtil.setParameterFilterSql("priceMedService","pms.id", request) ;
		ActionUtil.setParameterFilterSql("priceList","pp.priceList_id", request) ;
		ActionUtil.setParameterFilterSql("nationality","ccp.nationality_id", request) ;
		ActionUtil.setParameterFilterSql("department","lpu.id", request) ;
		ActionUtil.setParameterFilterSql("positionType","pp.positionType_id", request) ;
		ActionUtil.setParameterFilterSql("departmentType","lpu.lpuFunction_id", request) ;
		%>
		<% if (typeGroup!=null&& typeGroup.equals("1")) { %>
			
			
			
			<ecom:setAttribute name="id_group" value="${groupSqlId1}||${operatorSqlId}||${priceMedServiceSqlId}||${departmentSqlId}||${positionTypeSqlId}||${priceListSqlId}||'&dateFrom=${param.dateFrom}&dateTo=${param.dateTo}"/>
			<ecom:setAttribute name="queryGroup_sql" value="
SELECT lpu.id as sqlId
,lpu.name


, count(distinct case when cao.dtype='OperationAccrual' then mc.id else null end) as cntDogMedService 
, sum(case when cao.dtype='OperationAccrual' then cams.countMedService else 0 end) as sumCountMedService 
, sum(case when cao.dtype='OperationAccrual' then round(cams.countMedService*(cams.cost*(100-coalesce(cao.discount,0))/100),2) else 0 end) sumNoAccraulMedServiceWithDiscount  
, sum(case when cao.dtype='OperationAccrual' then round(cams.countMedService*(cams.cost*(100-coalesce(cao.discount,0))*(case when pp.isVat='1' then 0.1*1000/118 else 1 end)/100),2) else 0 end) sumNoAccraulMedServiceWithDiscountWithoutVat  

, count(distinct case when cao.dtype='OperationReturn' then mc.id else null end) as cntDogMedServiceRet 
, sum(case when cao.dtype='OperationReturn' then cams.countMedService else 0 end) as sumCountMedServiceRet 
, sum(case when cao.dtype='OperationReturn' then round(cams.countMedService*(cams.cost*(100-coalesce(cao.discount,0))/100),2) else 0 end) sumNoAccraulMedServiceWithDiscountRet  
, sum(case when cao.dtype='OperationReturn' then round(cams.countMedService*(cams.cost*(100-coalesce(cao.discount,0))*(case when pp.isVat='1' then 0.1*1000/118 else 1 end)/100),2) else 0 end) sumNoAccraulMedServiceWithDiscountRetWithoutVat  

, sum(case when cao.dtype='OperationAccrual' then cams.countMedService else 0 end) 
- sum(case when cao.dtype='OperationReturn' then cams.countMedService else 0 end) as sumCountMedServiceItog 

, sum(case when cao.dtype='OperationAccrual' then round(cams.countMedService*(cams.cost*(100-coalesce(cao.discount,0))/100),2) else 0 end)   
-
 sum(case when cao.dtype='OperationReturn' then round(cams.countMedService*(cams.cost*(100-coalesce(cao.discount,0))/100),2) else 0 end)
    
as sumItog
, (sum(case when cao.dtype='OperationAccrual' then round(cams.countMedService*(case when pp.isVat='1' then 0.1*1000/118 else 1 end)*(cams.cost*(100-coalesce(cao.discount,0))/100),2) else 0 end)   
-
 sum(case when cao.dtype='OperationReturn' then round(cams.countMedService*(case when pp.isVat='1' then 0.1*1000/118 else 1 end)*(cams.cost*(100-coalesce(cao.discount,0))/100),2) else 0 end)
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
left join VocPositionType vpt on vpt.id=pp.positionType_id 
left join PricePosition pg on pg.id=pp.parent_id

left join MisLpu lpu on lpu.id=pg.lpu_id
left join VocLpuFunction vlf on vlf.id=lpu.lpuFunction_id

left join WorkFunction wf on wf.id=cao.workFunction_id
left join VocWorkFunction vwf on vwf.id=wf.workFunction_id
left join Worker w on w.id=wf.worker_id
left join Patient wp on wp.id=w.person_id
WHERE	CAo.operationdate between to_date('${param.dateFrom}', 'dd.mm.yyyy') AND to_date('${param.dateTo}', 'dd.mm.yyyy') 
and (cao.dtype='OperationAccrual' or cao.dtype='OperationReturn')  ${priceMedServiceSql} ${operatorSql} ${priceListSql}
${nationalitySql} ${departmentSql} ${positionTypeSql}
${departmentTypeSql}
group by lpu.id,lpu.name
order by lpu.name
			"/>
			<ecom:webQuery name="department_list" nativeSql="${queryGroup_sql}"/>
			<table border='1px solid'>

			
<tr>
							<th rowspan="2">Наименование</th>
							<th colspan="4">Оплата</th>
							<th colspan="4">Возврат</th>
							<th colspan="3">Итог</th>

						</tr>
					<tr>

<th>Кол-во</th>
<th>Кол-во услуг</th>
<th>Сумма с НДС</th>

<th>Сумма без НДС</th>
<th>Кол-во</th>
<th>Кол-во услуг</th>
<th>Сумма с НДС</th>
<th>Сумма без НДС</th>
<th>Кол-во услуг</th>
<th>Итог с НДС</th>
<th>Итог без НДС</th>
			
</tr>
			<% 
			List list = (List) request.getAttribute("department_list") ;
			for (int i=0;i<list.size();i++) { 
				WebQueryResult wqr = (WebQueryResult)list.get(i) ;
				request.setAttribute("lpu", wqr) ;
				request.setAttribute("groupDep_id", wqr.get1()!=null?"="+wqr.get1():" is null") ;
				out.println("<tr>") ;
				//out.print("<th>"); out.print(wqr.get1());out.println("</th>") ;
				out.print("<th>"); out.print(wqr.get2());out.println("</th>") ;
				out.print("<th>"); out.print(wqr.get3());out.println("</th>") ;
				out.print("<th>"); out.print(wqr.get4());out.println("</th>") ;
				out.print("<th>"); out.print(wqr.get5());out.println("</th>") ;
				out.print("<th>"); out.print(wqr.get6());out.println("</th>") ;
				out.print("<th>"); out.print(wqr.get7());out.println("</th>") ;
				out.print("<th>"); out.print(wqr.get8());out.println("</th>") ;
				out.print("<th>"); out.print(wqr.get9());out.println("</th>") ;
				out.print("<th>"); out.print(wqr.get10());out.println("</th>") ;
				out.print("<th>"); out.print(wqr.get11());out.println("</th>") ;
				out.print("<th>"); out.print(wqr.get12());out.println("</th>") ;
				out.print("<th>"); out.print(wqr.get13());out.println("</th>") ;
				out.println("</tr>") ;
			%>
				<ecom:setAttribute name="queryElement_id"
value="
SELECT ${groupSqlId}||${operatorSqlId}||${priceMedServiceSqlId}||${departmentSqlId}||${positionTypeSqlId}||${priceListSqlId}||'&dateFrom=${param.dateFrom}&dateTo=${param.dateTo}' as sqlId
,${groupSql} as dateNum
,list(distinct lpu.name)


, count(distinct case when cao.dtype='OperationAccrual' then mc.id else null end) as cntDogMedService 
, sum(case when cao.dtype='OperationAccrual' then cams.countMedService else 0 end) as sumCountMedService 
, sum(case when cao.dtype='OperationAccrual' then round(cams.countMedService*(cams.cost*(100-coalesce(cao.discount,0))/100),2) else 0 end) sumNoAccraulMedServiceWithDiscount  
, sum(case when cao.dtype='OperationAccrual' then round(cams.countMedService*(cams.cost*(100-coalesce(cao.discount,0))*(case when pp.isVat='1' then 0.1*1000/118 else 1 end)/100),2) else 0 end) sumNoAccraulMedServiceWithDiscountWithoutVat  

, count(distinct case when cao.dtype='OperationReturn' then mc.id else null end) as cntDogMedServiceRet 
, sum(case when cao.dtype='OperationReturn' then cams.countMedService else 0 end) as sumCountMedServiceRet 
, sum(case when cao.dtype='OperationReturn' then round(cams.countMedService*(cams.cost*(100-coalesce(cao.discount,0))/100),2) else 0 end) sumNoAccraulMedServiceWithDiscountRet  
, sum(case when cao.dtype='OperationReturn' then round(cams.countMedService*(cams.cost*(100-coalesce(cao.discount,0))*(case when pp.isVat='1' then 0.1*1000/118 else 1 end)/100),2) else 0 end) sumNoAccraulMedServiceWithDiscountRetWithoutVat  

, sum(case when cao.dtype='OperationAccrual' then cams.countMedService else 0 end) 
- sum(case when cao.dtype='OperationReturn' then cams.countMedService else 0 end) as sumCountMedServiceItog 

, sum(case when cao.dtype='OperationAccrual' then round(cams.countMedService*(cams.cost*(100-coalesce(cao.discount,0))/100),2) else 0 end)   
-
 sum(case when cao.dtype='OperationReturn' then round(cams.countMedService*(cams.cost*(100-coalesce(cao.discount,0))/100),2) else 0 end)
    
as sumItog
, (sum(case when cao.dtype='OperationAccrual' then round(cams.countMedService*(case when pp.isVat='1' then 0.1*1000/118 else 1 end)*(cams.cost*(100-coalesce(cao.discount,0))/100),2) else 0 end)   
-
 sum(case when cao.dtype='OperationReturn' then round(cams.countMedService*(case when pp.isVat='1' then 0.1*1000/118 else 1 end)*(cams.cost*(100-coalesce(cao.discount,0))/100),2) else 0 end)
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
left join VocPositionType vpt on vpt.id=pp.positionType_id 
left join PricePosition pg on pg.id=pp.parent_id

left join MisLpu lpu on lpu.id=pg.lpu_id

left join WorkFunction wf on wf.id=cao.workFunction_id
left join VocWorkFunction vwf on vwf.id=wf.workFunction_id
left join Worker w on w.id=wf.worker_id
left join Patient wp on wp.id=w.person_id
WHERE	CAo.operationdate between to_date('${param.dateFrom}', 'dd.mm.yyyy') AND to_date('${param.dateTo}', 'dd.mm.yyyy') 
and (cao.dtype='OperationAccrual' or cao.dtype='OperationReturn')  
and lpu.id  ${groupDep_id}
${priceMedServiceSql} ${operatorSql} ${priceListSql}
${nationalitySql} ${departmentSql} ${positionTypeSql}
${departmentTypeSql}

group by ${groupGroup}
order by ${groupOrder}
			"
			
			/>
			
			<ecom:webQuery name="patient_list" nativeSql="${queryElement_id}"/>
<%
List listPat = (List) request.getAttribute("patient_list") ;
for (int ii=0;ii<listPat.size();ii++) { 
	WebQueryResult wqr1 = (WebQueryResult)listPat.get(ii) ;
	request.setAttribute("pat", wqr1) ;
	out.println("<tr>") ;
	//out.print("<td>"); out.print(wqr1.get1());out.println("</td>") ;
	out.print("<td>"); out.print(wqr1.get2());out.println("</td>") ;
	//out.print("<td>"); out.print(wqr1.get3());out.println("</td>") ;
	out.print("<td>"); out.print(wqr1.get4());out.println("</td>") ;
	out.print("<td>"); out.print(wqr1.get5());out.println("</td>") ;
	out.print("<td>"); out.print(wqr1.get6());out.println("</td>") ;
	out.print("<td>"); out.print(wqr1.get7());out.println("</td>") ;
	out.print("<td>"); out.print(wqr1.get8());out.println("</td>") ;
	out.print("<td>"); out.print(wqr1.get9());out.println("</td>") ;
	out.print("<td>"); out.print(wqr1.get10());out.println("</td>") ;
	out.print("<td>"); out.print(wqr1.get11());out.println("</td>") ;
	out.print("<td>"); out.print(wqr1.get12());out.println("</td>") ;
	out.print("<td>"); out.print(wqr1.get13());out.println("</td>") ;
	out.print("<td>"); out.print(wqr1.get14());out.println("</td>") ;
	out.println("</tr>") ;
	%>


				<%} %>
				 
				<% }%>
			</table>
			
	<%} else if (typeGroup!=null&& typeGroup.equals("2") ) { %>
		<ecom:webQuery name="dep_list" nameFldSql="dep_list_sql" nativeSql="
SELECT lpu.id as sqlId
,lpu.name as lpuname
,mc.id as mcId
,MC.contractnumber || ' '||to_char(mc.dateFrom,'dd.mm.yyyy') as dateNum
,coalesce(CCP.lastname||' '||CCP.firstname||' '||CCP.middlename||' г.р. '||to_char(CCP.birthday,'dd.mm.yyyy')
,CCO.name) as kontragent
,pp.id as sqlId
,pp.positionType_id
,pp.code||' '||pp.name as pmsname
, sum(cams.countMedService) as sumCountMedServiceItog 
, sum(round(cams.countMedService*(cams.cost*(100-coalesce(cao.discount,0))/100),2))   
as sumItog
, sum(round(cams.countMedService*(case when pp.isVat='1' then 0.1*1000/118 else 1 end) *(cams.cost*(100-coalesce(cao.discount,0))/100),2))   
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
WHERE	CAo.operationdate between to_date('${param.dateFrom}', 'dd.mm.yyyy') 
AND to_date('${param.dateTo}', 'dd.mm.yyyy') 
and (cao.dtype='OperationAccrual' ) and cao.repealOperation_id is null  ${priceMedServiceSql} ${operatorSql} ${priceListSql}
${nationalitySql} ${departmentSql} ${positionTypeSql}
${departmentTypeSql}

group by lpu.id,lpu.name,mc.id,lpu.name,CCP.lastname,CCP.firstname,CCP.middlename,CCP.birthday,CCO.name,MC.contractnumber,mc.dateFrom,pp.positionType_id,pp.id,pp.code,pp.name
order by lpu.name,CCP.lastname,CCP.firstname,CCP.middlename,pp.positionType_id,pp.code
			"/>
			<table border='1px solid'>
<tr>
	<th>Наименование</th>
	<th>Кол-во услуг</th>
	<th>Сумма с НДС</th>
	<th>Сумма без НДС</th>

</tr>

			<% 
			List list = (List) request.getAttribute("dep_list") ;
			DecimalFormat decimal_formatter = new DecimalFormat("##0.00") ;
			DecimalFormatSymbols custom = new DecimalFormatSymbols() ;
			custom.setDecimalSeparator(',') ;
			decimal_formatter.setDecimalFormatSymbols(custom);
			decimal_formatter.setGroupingSize(0);
			
			Object vLpu1 = null ;
			Object vContract = null;
			Object vService = null;
			int cntMC = 1 ;
			BigDecimal sLpu1 = new BigDecimal(0) ;BigDecimal sLpu2 = new BigDecimal(0) ; BigDecimal sLpu3 = new BigDecimal(0) ;BigDecimal sLpu4 = new BigDecimal(0) ;
			BigDecimal sLpu1d = new BigDecimal(0) ;BigDecimal sLpu2d = new BigDecimal(0) ; BigDecimal sLpu3d = new BigDecimal(0) ;
			BigDecimal sContract1 = new BigDecimal(0) ;BigDecimal sContract2 = new BigDecimal(0) ; BigDecimal sContract3 = new BigDecimal(0) ;
			BigDecimal sContract1d = new BigDecimal(0) ;BigDecimal sContract2d = new BigDecimal(0) ; BigDecimal sContract3d = new BigDecimal(0) ;
			boolean isNewLpu = false; boolean isNewContract=false ;
			for (int i=0;i<list.size();i++) { 
				WebQueryResult wqr = (WebQueryResult)list.get(i) ;
				isNewContract=false ; isNewLpu=false ;
				if (i>0) {
					if (vLpu1==null&&wqr.get2()==null || vLpu1!=null&&wqr.get2()!=null&&vLpu1.equals(wqr.get2())) {
						isNewLpu=false ;
						//vLpu1=wqr.get2() ;
						if (vContract==null&&wqr.get3()==null || vContract!=null&&wqr.get3()!=null&&vContract.equals(wqr.get3())) {
							isNewContract=false;
							
						} else {
							isNewContract=true;
						}
					} else {
						isNewLpu=true ;
						isNewContract=true;
					}
					/*if (isNewContract) {
						out.println("<tr>") ;
						out.print("<th>"); out.print("ИТОГО по к.дн.:");out.println("</th>") ;
						out.print("<th>"); out.print(sContract1);out.println("</th>") ;
						out.print("<th>"); out.print(sContract2);out.println("</th>") ;
						out.print("<th>"); out.print(sContract3);out.println("</th>") ;
						out.println("</tr>") ;
						out.println("<tr>") ;
						out.print("<td><i>"); out.print("итог опер.:");out.println("</i></td>") ;
						out.print("<td>"); out.print(sContract1d);out.println("</td>") ;
						out.print("<td>"); out.print(sContract2d);out.println("</td>") ;
						out.print("<td>"); out.print(sContract3d);out.println("</td>") ;
						out.println("<tr>") ;
					} */
					
					if (isNewLpu) {
						out.println("<tr>") ;
						out.print("<th align='right'>"); out.print("ИТОГО по к.дн.:");out.println("</th>") ;
						out.print("<th>"); out.print(sLpu1d);out.println("</th>") ;
						out.print("<th>"); out.print(decimal_formatter.format(sLpu2d));out.println("</th>") ;
						out.print("<th>"); out.print(decimal_formatter.format(sLpu3d));out.println("</th>") ;
						out.println("</tr>") ;
						out.println("<tr>") ;
						out.print("<th align='right'>"); out.print("ИТОГО по др.усл.:");out.println("</th>") ;
						out.print("<th>"); out.print(sLpu1);out.println("</th>") ;
						out.print("<th>"); out.print(decimal_formatter.format(sLpu2));out.println("</th>") ;
						out.print("<th>"); out.print(decimal_formatter.format(sLpu3));out.println("</th>") ;
						out.println("</tr>") ;
						out.println("<tr>") ;
						out.print("<th align='right'>"); out.print("ВСЕГО:");out.println("</th>") ;
						out.print("<th>"); out.print(sLpu1.add(sLpu1d));out.println("</th>") ;
						out.print("<th>"); out.print(decimal_formatter.format(sLpu2.add(sLpu2d)));out.println("</th>") ;
						out.print("<th>"); out.print(decimal_formatter.format(sLpu3.add(sLpu3d)));out.println("</th>") ;
						out.println("</tr>") ;
					}
				} else{
					vContract=wqr.get3() ;
					vLpu1=wqr.get2() ;
					isNewLpu = true ;
					isNewContract = true ;
					sContract1 = new BigDecimal(0) ;sContract2 = new BigDecimal(0) ; sContract3 = new BigDecimal(0) ;
					sContract1d = new BigDecimal(0) ;sContract2d = new BigDecimal(0) ; sContract3d = new BigDecimal(0) ;

					
				}
					
					
					if (isNewLpu) {
						sLpu1 = new BigDecimal(0) ;sLpu2 = new BigDecimal(0) ;sLpu3 = new BigDecimal(0) ;
						sLpu1d = new BigDecimal(0) ;sLpu2d = new BigDecimal(0) ;sLpu3d = new BigDecimal(0) ;
						out.println("<tr>") ;
						out.print("<th colspan='4'>"); out.print(wqr.get2());out.println("</th>") ;
						out.println("</tr>") ;
						cntMC = 1 ;
					}
					if (isNewContract) {
						out.println("<tr>") ;
						out.print("<td colspan='4'><i>");out.print(cntMC++);out.print(". "); out.print(wqr.get5());out.println("</i></td>") ;
						out.println("</tr>") ;
					} 
				
				
				
				if (wqr.get7()!=null && (""+wqr.get7()).equals("7")) {
					sContract1d=sContract1d.add(new BigDecimal(wqr.get9()!=null?""+wqr.get9():"0")) ;
					sContract2d=sContract2d.add(new BigDecimal(wqr.get10()!=null?""+wqr.get10():"0")) ;
					sContract3d=sContract3d.add(new BigDecimal(wqr.get11()!=null?""+wqr.get11():"0")) ;
					sLpu1d=sLpu1d.add(new BigDecimal(wqr.get9()!=null?""+wqr.get9():"0")) ;
					sLpu2d=sLpu2d.add(new BigDecimal(wqr.get10()!=null?""+wqr.get10():"0")) ;
					sLpu3d=sLpu3d.add(new BigDecimal(wqr.get11()!=null?""+wqr.get11():"0")) ;
				} else {
					sContract1=sContract1.add(new BigDecimal(wqr.get9()!=null?""+wqr.get9():"0")) ;
					sContract2=sContract2.add(new BigDecimal(wqr.get10()!=null?""+wqr.get10():"0")) ;
					sContract3=sContract3.add(new BigDecimal(wqr.get11()!=null?""+wqr.get11():"0")) ;
					sLpu1=sLpu1.add(new BigDecimal(wqr.get9()!=null?""+wqr.get9():"0")) ;
					sLpu2=sLpu2.add(new BigDecimal(wqr.get10()!=null?""+wqr.get10():"0")) ;
					sLpu3=sLpu3.add(new BigDecimal(wqr.get11()!=null?""+wqr.get11():"0")) ;
				}
				
				
				//out.print("<td>"); out.print(wqr.get3());out.println("</td>") ;
				out.print("<td>"); out.print(wqr.get8());out.println("</td>") ;
				out.print("<td align='center'>"); out.print(wqr.get9());out.println("</td>") ;
				out.print("<td align='center'>"); out.print(decimal_formatter.format(wqr.get10()));out.println("</td>") ;
				out.print("<td align='center'>"); out.print(decimal_formatter.format(wqr.get11()));out.println("</td>") ;
				
				out.println("</tr>") ;
				vLpu1=wqr.get2();
				vContract=wqr.get3() ;
				
			}
			out.println("<tr>") ;
			out.print("<th align='right'>"); out.print("ИТОГО по к.дн.:");out.println("</th>") ;
			out.print("<th>"); out.print(sLpu1d);out.println("</th>") ;
			out.print("<th>"); out.print(decimal_formatter.format(sLpu2d));out.println("</th>") ;
			out.print("<th>"); out.print(decimal_formatter.format(sLpu3d));out.println("</th>") ;
			out.println("</tr>") ;
			out.println("<tr>") ;
			out.print("<th align='right'>"); out.print("ИТОГО по др.усл.:");out.println("</th>") ;
			out.print("<th>"); out.print(sLpu1);out.println("</th>") ;
			out.print("<th>"); out.print(decimal_formatter.format(sLpu2));out.println("</th>") ;
			out.print("<th>"); out.print(decimal_formatter.format(sLpu3));out.println("</th>") ;
			out.println("</tr>") ;
			out.println("<tr>") ;
			out.print("<th align='right'>"); out.print("ВСЕГО:");out.println("</th>") ;
			out.print("<th>"); out.print(sLpu1.add(sLpu1d));out.println("</th>") ;
			out.print("<th>"); out.print(decimal_formatter.format(sLpu2.add(sLpu2d)));out.println("</th>") ;
			out.print("<th>"); out.print(decimal_formatter.format(sLpu3.add(sLpu3d)));out.println("</th>") ;
			out.println("</tr>") ;
			
			%>
			
	<%}%>
	
	</table>
	<%
			
	} else {
		%>
		Выберите параметры и нажмите  кнопку "СФОРМИРОВАТЬ"
		<%
		}}
		%>
		
		
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