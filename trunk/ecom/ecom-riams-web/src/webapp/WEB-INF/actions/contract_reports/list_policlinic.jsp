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
	<tags:contractMenu currentAction="serviciesReport"/>
	</tiles:put>
	<tiles:put name='body' type='string' >
  <%
  	String typeGroup =ActionUtil.updateParameter("Form039Action","typeGroup","1", request) ;
  if (request.getParameter("short")==null) {
%>
		<msh:form action="/contract_policlinic_render.do" defaultField="dateFrom">
			<msh:panel>
				<msh:row>
				<msh:textField property="dateFrom" label="c"/>
				<msh:textField property="dateTo" label="по"/>
				</msh:row>
				
				
        <msh:row>
	        <td class="label" title="Группировака (typePatient)" colspan="1"><label for="typeGroupName" id="typeGroupLabel">Группировка по:</label></td>
	        <td onclick="this.childNodes[1].checked='checked';">
	        	<input type="radio" name="typeGroup" value="1"> свод по специалистам
	        </td>
	        <td onclick="this.childNodes[1].checked='checked';">
	        	<input type="radio" name="typeGroup" value="2"> ошибки соответствий
	        </td>
        </msh:row>		
        <msh:row>
        	<msh:submitCancelButtonsRow labelSave="Сформировать" doNotDisableButtons="cancel" labelSaving="Формирование..." colSpan="4"/>
        </msh:row>		
        <msh:row>
        	<input type="submit" onclick="this.form.action='js-contract_medContract-updateCAOSbyCharged.do'" value="Обновить соответствия"/></td>
        </msh:row>
			</msh:panel>
		</msh:form>
		
<%
  } else{
	  %>
		<msh:form action="/contract_policlinic_render.do" defaultField="dateFrom">
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
		/*
		if (typeGroup.equals("1")||typeGroup.equals("2")) {
			// Группировка по услугам 
   			request.setAttribute("groupSql", "pp.code||' '||pp.name") ;
   			request.setAttribute("groupSqlId", "'&priceMedService='||pms.id") ;
   			request.setAttribute("groupName", "Услуга") ;
       		request.setAttribute("groupGroupNext", "5") ;
   			request.setAttribute("groupGroup", "pms.id,pp.code,pp.name,pp.isVat,lpu.name") ;
   			request.setAttribute("groupOrder", "lpu.name,pp.code") ;
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
		} else {
		
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
		*/
		%>
		<% if (typeGroup!=null&& typeGroup.equals("1")) {%>
			<msh:section title="Финасовый отчет по услугам за период ${FromTo} ">
			<ecom:webQuery name="finansReport" nameFldSql="finansReport_sql" nativeSql="
select pp.id as ppid,pp.code as ppcode,pp.name as ppname
,vwf.name as vwfnam,wp.lastname,count(distinct caos.id) as cnt,
sum(round(cams.countMedService*(cams.cost*(100-coalesce(cao.discount,0))/100),2)) as sumRender
from ContractAccountOperationByService caos
left join ContractAccountOperation cao on caos.accountOperation_id=cao.id and cao.dtype='OperationAccrual' and cao.repealOperation_id is null
left join medcase mc on mc.id=caos.medcase_id
left join WorkFunction wf on wf.id=mc.workFunctionExecute_id
left join Worker w on w.id=wf.worker_id
left join Patient wp on wp.id=w.person_id
left join VocWorkFunction vwf on vwf.id=wf.workFunction_id
left join ContractAccountMedService cams on cams.id=caos.accountMedService_id
left join ServedPerson sp on cao.account_id=sp.account_id
left join ContractPerson cp on sp.person_id=cp.id
left join Patient p on cp.patient_id=p.id

left join pricemedservice pms on cams.medService_id=pms.id
left join priceposition pp on pms.priceposition_id=pp.id
left join priceposition ppG on ppG.id=pp.parent_id
left join mislpu ml on ml.id=ppG.lpu_id
left join medservice ms on pms.medservice_id=ms.id
where cao.operationdate
between to_date('${param.dateFrom}', 'dd.mm.yyyy') AND to_date('${param.dateTo}', 'dd.mm.yyyy')
and ppG.lpu_id in (184,180 )
and pp.positionType_id=2
group by ppG.lpu_id,pp.id,pp.code,pp.name,wp.lastname,vwf.name
order by pp.name

			"/>

				<msh:table name="finansReport" 
				action="javascript:void(0)" 
				idField="1">
					<msh:tableColumn columnName="Код услуги" property="2" />
					<msh:tableColumn columnName="Наименование" property="3" />
					<msh:tableColumn columnName="Должность"  property="4" />
					<msh:tableColumn columnName="Фамилия специалиста" property="5" />
					<msh:tableColumn columnName="Кол-во" isCalcAmount="true" property="6" />
					<msh:tableColumn columnName="Сумма" isCalcAmount="true" property="7" />
				</msh:table>

			</msh:section>	
	<%} else if (typeGroup!=null&& typeGroup.equals("2")) {%>
			<msh:section>
			<ecom:webQuery name="finansReport" nameFldSql="finansReport_sql" nativeSql="
select caos.id as caosid,p.patientSync,p.lastname||' '||p.firstname||' '||p.middlename as fiopat
,to_char(cao.operationdate,'dd.mm.yyyy') as operdate 
,pp.code||' '||pp.name as rendername,count(distinct caos.id) as cntRender,
sum(round(cams.countMedService*(cams.cost*(100-coalesce(cao.discount,0))/100),2)) as sumRender
from ContractAccountOperationByService caos
left join ContractAccountOperation cao on caos.accountOperation_id=cao.id and cao.dtype='OperationAccrual' and cao.repealOperation_id is null
left join medcase mc on mc.id=caos.medcase_id
left join WorkFunction wf on wf.id=mc.workFunctionExecute_id
left join Worker w on w.id=wf.worker_id
left join Patient wp on wp.id=w.person_id
left join VocWorkFunction vwf on vwf.id=wf.workFunction_id
left join ContractAccountMedService cams on cams.id=caos.accountMedService_id
left join ServedPerson sp on cao.account_id=sp.account_id
left join ContractPerson cp on sp.person_id=cp.id
left join Patient p on cp.patient_id=p.id

left join pricemedservice pms on cams.medService_id=pms.id
left join priceposition pp on pms.priceposition_id=pp.id
left join priceposition ppG on ppG.id=pp.parent_id
left join mislpu ml on ml.id=ppG.lpu_id
left join medservice ms on pms.medservice_id=ms.id
where cao.operationdate
between to_date('${param.dateFrom}','dd.mm.yyyy') and to_date('${param.dateTo}','dd.mm.yyyy')
and ppG.lpu_id in (184,180 )
and wp.lastname is null
group by caos.id,p.patientSync,p.lastname,p.firstname,p.middlename,ppG.lpu_id,pp.code,pp.name,wp.lastname,vwf.name
,cao.operationdate
order by p.lastname,p.firstname,p.middlename,pp.name
			"/>

			<msh:sectionTitle> 
	    <form action="print-contract_reports_services_2_4.do" method="post" target="_blank">
	    Финасовый отчет по услугам за период ${FromTo}
	    <input type='hidden' name="sqlText" id="sqlText" value="${finansReport_sql}"> 
	    <input type='hidden' name="sqlInfo" id="sqlInfo" value="Финасовый отчет по услугам за период ${FromTo}">
	    <input type='hidden' name="sqlColumn" id="sqlColumn" value="${groupName}">
	    <input type='hidden' name="s" id="s" value="PrintService">
	    <input type='hidden' name="m" id="m" value="printNativeQuery">
	    <input type="submit" value="Печать"> 
	    </form>     
			</msh:sectionTitle>
			<msh:sectionContent>
				<msh:table name="finansReport" action="javascript:void(0)" idField="1">
					<msh:tableColumn columnName="Код пациента" property="2" />
					<msh:tableColumn columnName="ФИО пациента" property="3" />
					<msh:tableColumn columnName="Дата" property="4" />
					<msh:tableColumn columnName="Услуга" property="5" />
					<msh:tableColumn columnName="Кол-во" isCalcAmount="true" property="6" />
					<msh:tableColumn columnName="Сумма" isCalcAmount="true" property="7" />
				</msh:table>
			</msh:sectionContent>

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