<%@page import="ru.ecom.web.util.ActionUtil"%>
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
	<tags:contractMenu currentAction="serviciesPolicReport"/>
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
        	<input type="submit" onclick="this.form.action='js-contract_medContract-updateCAOSbyCharged.do'" value="Обновить соответствия"/>
        </msh:row>
			</msh:panel>
		</msh:form>
		
<%
  }
	String dateFrom = request.getParameter("dateFrom") ;
		String dFrom ;
		if (dateFrom==null ||dateFrom.equals("") ) {
			dFrom=" is null " ;
		} else {
			dFrom = ">=to_date('"+dateFrom+"', 'dd.mm.yyyy')" ;
		}
		request.setAttribute("dFrom",dFrom) ;
		
		String dateTo = request.getParameter("dateTo") ;
		String dTo ;
		if (dateTo==null ||dateTo.equals("") ) {
			dTo=" is null " ;
		} else {
			dTo = "<=to_date('"+dateTo+"', 'dd.mm.yyyy')" ;
		}
		request.setAttribute("dTo",dTo) ;
		
		String fromTo ;
		if  (dateTo!=null && dateFrom!=null ) {
			fromTo=" c "+dateFrom+" по "+dateTo;
		} else {
			fromTo = "";
		}

		request.setAttribute("fromTo", fromTo);
		String isReestr = request.getParameter("isReestr");
		if ("1".equals(isReestr)) {
		%>
		<msh:section title="Реестр по пациентам ">
			<ecom:webQuery name="finansReportReestr" nameFldSql="finansReportReestr_sql" nativeSql="
select caos.id as caosid,p.patientSync,p.lastname||' '||p.firstname||' '||p.middlename as fiopat
,to_char(cao.operationdate,'dd.mm.yyyy') as operdate
,pp.code||' '||pp.name as rendername
,sum(round(cams.cost*((100-coalesce(cao.discount,0))/100),2)) as sumRender
,p.id as f7_pid
from ContractAccountOperationByService caos
left join ContractAccountOperation cao on caos.accountOperation_id=cao.id and cao.dtype='OperationAccrual' and cao.repealOperation_id is null
left join ContractAccountMedService cams on cams.id=caos.accountMedService_id
left join ServedPerson sp on cao.account_id=sp.account_id
left join ContractPerson cp on sp.person_id=cp.id
left join Patient p on cp.patient_id=p.id
left join pricemedservice pms on cams.medService_id=pms.id
left join priceposition pp on pms.priceposition_id=pp.id
where caos.id in (${param.id})
group by caos.id,p.id,p.patientSync,p.lastname,p.firstname,p.middlename,pp.code,pp.name
,cao.operationdate
order by p.lastname,p.firstname,p.middlename,pp.name
			"/>

			<msh:table name="finansReportReestr" action="entityView-mis_patient.do" idField="7" openNewWindow="true">
				<msh:tableColumn columnName="Код пациента" property="2"/>
				<msh:tableColumn columnName="ФИО" property="3" />
				<msh:tableColumn columnName="Дата услуги"  property="4" />
				<msh:tableColumn columnName="Код услуги" property="5" />
				<msh:tableColumn columnName="Сумма" isCalcAmount="true" property="6" />
			</msh:table>

		</msh:section>
		<%
			} else {
			if ("1".equals(typeGroup)) {%>
			<msh:section title="Финасовый отчет по услугам за период ${fromTo} ">
			<ecom:webQuery name="finansReport" nameFldSql="finansReport_sql" nativeSql="
select pp.id as ppid,pp.code as ppcode,pp.name as ppname
,vwf.name as vwfnam,wp.lastname,count(distinct caos.id) as cnt
,sum(round(1*(cams.cost*(100-coalesce(cao.discount,0))/100),2)) as sumRender
,list(caos.id||'') as f8_caoses
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
and ppG.lpu_id in (184,180,246)
group by ppG.lpu_id,pp.id,pp.code,pp.name,wp.lastname,vwf.name
order by pp.name

			"/>

				<msh:table name="finansReport" cellFunction="true"
				action="contract_policlinic_render.do?isReestr=1&short=Short"
				idField="8">
					<msh:tableColumn columnName="Код услуги" property="2" />
					<msh:tableColumn columnName="Наименование" property="3" />
					<msh:tableColumn columnName="Должность"  property="4" />
					<msh:tableColumn columnName="Фамилия специалиста" property="5" />
					<msh:tableColumn columnName="Кол-во" isCalcAmount="true" property="6" />
					<msh:tableColumn columnName="Сумма" isCalcAmount="true" property="7" />
				</msh:table>

			</msh:section>	
	<%} else if ("2".equals(typeGroup)) {%>
			<msh:section>
			<ecom:webQuery name="finansReport" nameFldSql="finansReport_sql" nativeSql="
select caos.id as caosid,p.patientSync,p.lastname||' '||p.firstname||' '||p.middlename as fiopat
,to_char(cao.operationdate,'dd.mm.yyyy') as operdate 
,pp.code||' '||pp.name as rendername,count(distinct caos.id) as cntRender
,sum(round(cams.cost*((100-coalesce(cao.discount,0))/100),2)) as sumRender
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
	    Ошибки соответствий за период ${FromTo}
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
	
	<%} }%>
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