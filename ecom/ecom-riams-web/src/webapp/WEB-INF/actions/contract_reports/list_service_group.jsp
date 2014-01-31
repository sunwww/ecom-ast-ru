<%@page import="java.util.ArrayList"%>
<%@page import="java.util.Collection"%>
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
		<% if (typeGroup!=null&& typeGroup.equals("1")) {%>
			
			
			
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
			
	<%} else if (typeGroup!=null&& typeGroup.equals("2") ) {
	%>
				<ecom:webQuery name="dep_list" nameFldSql="dep_list_sql" nativeSql="
SELECT lpu.id as sqlId
,lpu.name as lpuname

, sum(case when cao.dtype='OperationAccrual' then cams.countMedService else 0 end) 
- sum(case when cao.dtype='OperationReturn' then cams.countMedService else 0 end)
as sumCountMedServiceItog 

, sum(case when cao.dtype='OperationAccrual' then round(cams.countMedService*(cams.cost*(100-coalesce(cao.discount,0))/100),2) else 0 end)   
-
 sum(case when cao.dtype='OperationReturn' then round(cams.countMedService*(cams.cost*(100-coalesce(cao.discount,0))/100),2) else 0 end)
    
as sumItog
, (sum(case when cao.dtype='OperationAccrual' then round(cams.countMedService*(case when pp.isVat='1' then 0.1*1000/118 else 1 end) *(cams.cost*(100-coalesce(cao.discount,0))/100),2) else 0 end)   
-
 sum(case when cao.dtype='OperationReturn' then round(cams.countMedService*(case when pp.isVat='1' then 0.1*1000/118 else 1 end) *(cams.cost*(100-coalesce(cao.discount,0))/100),2) else 0 end)
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
${nationalitySql} ${departmentSql} ${positionTypeSql}
${departmentTypeSql}
and pp.positionType_id in (3,7)

group by lpu.id,lpu.name
order by lpu.name
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
				/*out.print("<th>"); out.print(wqr.get8());out.println("</th>") ;
				out.print("<th>"); out.print(wqr.get9());out.println("</th>") ;
				out.print("<th>"); out.print(wqr.get10());out.println("</th>") ;
				out.print("<th>"); out.print(wqr.get11());out.println("</th>") ;
				out.print("<th>"); out.print(wqr.get12());out.println("</th>") ;
				out.print("<th>"); out.print(wqr.get13());out.println("</th>") ;*/
				out.println("</tr>") ;
			%>
			
				<ecom:webQuery name="pat_list" nameFldSql="pat_list_sql" nativeSql="
SELECT mc.id as sqlId
,MC.contractnumber || ' '||to_char(mc.dateFrom,'dd.mm.yyyy') as dateNum
,coalesce(CCP.lastname||' '||CCP.firstname||' '||CCP.middlename||' г.р. '||to_char(CCP.birthday,'dd.mm.yyyy')
,CCO.name) as kontragent
, sum(case when cao.dtype='OperationAccrual' then cams.countMedService else 0 end) 
- sum(case when cao.dtype='OperationReturn' then cams.countMedService else 0 end)
as sumCountMedServiceItog 

, sum(case when cao.dtype='OperationAccrual' then round(cams.countMedService*(cams.cost*(100-coalesce(cao.discount,0))/100),2) else 0 end)   
-
 sum(case when cao.dtype='OperationReturn' then round(cams.countMedService*(cams.cost*(100-coalesce(cao.discount,0))/100),2) else 0 end)
    
as sumItog
, (sum(case when cao.dtype='OperationAccrual' then round(cams.countMedService*(case when pp.isVat='1' then 0.1*1000/118 else 1 end) *(cams.cost*(100-coalesce(cao.discount,0))/100),2) else 0 end)   
-
 sum(case when cao.dtype='OperationReturn' then round(cams.countMedService*(case when pp.isVat='1' then 0.1*1000/118 else 1 end) *(cams.cost*(100-coalesce(cao.discount,0))/100),2) else 0 end)
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
${nationalitySql} ${departmentSql} ${positionTypeSql}
${departmentTypeSql}
and lpu.id ${groupDep_id}
and pp.positionType_id in (3,7)
group by mc.id,lpu.name,CCP.lastname,CCP.firstname,CCP.middlename,CCP.birthday,CCO.name,MC.contractnumber,mc.dateFrom
order by CCP.lastname,CCP.firstname,CCP.middlename
			"/>
			<%
List listPat = (List) request.getAttribute("pat_list") ;
for (int ii=0;ii<listPat.size();ii++) { 
	WebQueryResult wqr1 = (WebQueryResult)listPat.get(ii) ;
	//request.setAttribute("pat", wqr1) ;
	out.println("<tr>") ;
	//out.print("<td>"); out.print(wqr1.get1());out.println("</td>") ;
	out.print("<td><i>"); out.print(wqr1.get3());out.println("</i></td>") ;
	//out.print("<td>"); out.print(wqr1.get3());out.println("</td>") ;
	out.print("<td>"); out.print(wqr1.get4());out.println("</td>") ;
	out.print("<td>"); out.print(wqr1.get5());out.println("</td>") ;
	out.print("<td>"); out.print(wqr1.get6());out.println("</td>") ;
	out.print("<td>"); out.print(wqr1.get7());out.println("</td>") ;
	/*out.print("<td>"); out.print(wqr1.get8());out.println("</td>") ;
	out.print("<td>"); out.print(wqr1.get9());out.println("</td>") ;
	out.print("<td>"); out.print(wqr1.get10());out.println("</td>") ;
	out.print("<td>"); out.print(wqr1.get11());out.println("</td>") ;
	out.print("<td>"); out.print(wqr1.get12());out.println("</td>") ;
	out.print("<td>"); out.print(wqr1.get13());out.println("</td>") ;
	out.print("<td>"); out.print(wqr1.get14());out.println("</td>") ;*/
	out.println("</tr>") ;
	request.setAttribute("groupContract_id", wqr1.get1()!=null?"="+wqr1.get1():" is null") ;
	
	%>
				<ecom:webQuery name="service_list" nameFldSql="service_list_sql" nativeSql="
SELECT pp.id as sqlId
,pp.code||' '||pp.name as pmsname

, sum(case when cao.dtype='OperationAccrual' then cams.countMedService else 0 end) 
- sum(case when cao.dtype='OperationReturn' then cams.countMedService else 0 end)
as sumCountMedServiceItog 

, sum(case when cao.dtype='OperationAccrual' then round(cams.countMedService*(cams.cost*(100-coalesce(cao.discount,0))/100),2) else 0 end)   
-
 sum(case when cao.dtype='OperationReturn' then round(cams.countMedService*(cams.cost*(100-coalesce(cao.discount,0))/100),2) else 0 end)
    
as sumItog
, (sum(case when cao.dtype='OperationAccrual' then round(cams.countMedService*(case when pp.isVat='1' then 0.1*1000/118 else 1 end) *(cams.cost*(100-coalesce(cao.discount,0))/100),2) else 0 end)   
-
 sum(case when cao.dtype='OperationReturn' then round(cams.countMedService*(case when pp.isVat='1' then 0.1*1000/118 else 1 end) *(cams.cost*(100-coalesce(cao.discount,0))/100),2) else 0 end)
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
${nationalitySql} ${departmentSql} ${positionTypeSql}
${departmentTypeSql}
and lpu.id ${groupDep_id}
and mc.id ${groupContract_id} 
and pp.positionType_id in (3,7)
group by pp.id,pp.code,pp.name
order by pp.code
			"/>
	<%
List listService = (List) request.getAttribute("service_list") ;
for (int iii=0;iii<listService.size();iii++) { 
	WebQueryResult wqr2 = (WebQueryResult)listService.get(iii) ;
	//request.setAttribute("service", wqr2) ;
	out.println("<tr>") ;
	//out.print("<td>"); out.print(wqr1.get1());out.println("</td>") ;
	out.print("<td>"); out.print(wqr2.get2());out.println("</td>") ;
	out.print("<td>"); out.print(wqr2.get3());out.println("</td>") ;
	out.print("<td>"); out.print(wqr2.get4());out.println("</td>") ;
	out.print("<td>"); out.print(wqr2.get5());out.println("</td>") ;
	out.print("<td>"); out.print(wqr2.get6());out.println("</td>") ;
	/*out.print("<td>"); out.print(wqr2.get7());out.println("</td>") ;
	out.print("<td>"); out.print(wqr2.get8());out.println("</td>") ;
	out.print("<td>"); out.print(wqr2.get9());out.println("</td>") ;
	out.print("<td>"); out.print(wqr2.get10());out.println("</td>") ;
	out.print("<td>"); out.print(wqr2.get11());out.println("</td>") ;
	out.print("<td>"); out.print(wqr2.get12());out.println("</td>") ;
	out.print("<td>"); out.print(wqr2.get13());out.println("</td>") ;
	out.print("<td>"); out.print(wqr2.get14());out.println("</td>") ;*/
	out.println("</tr>") ;
	%>
	
	<%} %>
	<%} %>
	<%} %>
	
	<%
	} 
	} else {
		%>
		Выберите параметры и нажмите  кнопку "СФОРМИРОВАТЬ"
		<%
		}
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