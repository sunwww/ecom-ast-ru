<%@page import="ru.ecom.mis.web.action.util.ActionUtil"%>
<%@page import="ru.nuzmsh.web.tags.helper.RolesHelper"%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://struts.apache.org/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://www.nuzmsh.ru/tags/msh" prefix="msh" %>
<%@ taglib uri="http://www.ecom-ast.ru/tags/ecom" prefix="ecom" %>
<%@ taglib tagdir="/WEB-INF/tags" prefix="tags" %>

<%@page import="ru.ecom.poly.web.action.ticket.JournalBySpecialistForm"%>
<tiles:insert page="/WEB-INF/tiles/mainLayout.jsp" flush="true" >

    <tiles:put name='title' type='string'>
        <msh:title mainMenu="Poly">Просмотр данных по услугам </msh:title>
    </tiles:put>

    <tiles:put name='side' type='string'>
        <tags:visit_finds currentAction="report_service"/>
    </tiles:put>
    
  <tiles:put name="body" type="string">
  <%
  	String typeReestr =ActionUtil.updateParameter("Form039Action","typeReestr","2", request) ;
  	String typeGroup =ActionUtil.updateParameter("Form039Action","typeGroup","1", request) ;
	String typeView =ActionUtil.updateParameter("Form039Action","typeView","1", request) ;
	String typeAgeWork =ActionUtil.updateParameter("Form039Action","typeAgeWork","2", request) ;
	String typeDtype =ActionUtil.updateParameter("Form039Action","typeDtype","3", request) ;
	String typeDate =ActionUtil.updateParameter("Form039Action","typeDate","2", request) ;
	String typeEmergency =ActionUtil.updateParameter("Form039Action","typeEmergency","3", request) ;
	String person = request.getParameter("person") ;
	
	if (person!=null && !person.equals("") && !person.equals("0")) {
		request.setAttribute("personClear", "<input type='button' name='clearPerson' value='Очистить инф. о сотруднике' onclick=\"$('person').value='';this.style.display='none'\">") ;
	}
  %>
    <msh:form action="/visit_report_service.do" defaultField="beginDate" disableFormDataConfirm="true" method="GET" guid="d7b31bc2-38f0-42cc-8d6d-19395273168f">
    <input type="hidden" name="m" id="m" value="f039"/>
    <input type="hidden" name="s" id="s" value="TicketService"/>
    <input type="hidden" name="id" id="id"/>
    <input type="hidden" name="ticketIs" id="ticketIs" value="0"/>
    <input type="hidden" name="typeReestr" id="typeReestr" value="2"/>
    <input type="hidden" name="person" id="person" value="${param.person}"/>
    
    <msh:panel>
      <msh:row guid="53627d05-8914-48a0-b2ec-792eba5b07d9">
        <msh:separator label="Параметры поиска" colSpan="9" guid="15c6c628-8aab-4c82-b3d8-ac77b7b3f700" />
      </msh:row>
      <msh:row>
        	<msh:textField property="beginDate" label="Период с" guid="8d7ef035-1273-4839-a4d8-1551c623caf1" />
        	<msh:textField property="finishDate" label="по" guid="f54568f6-b5b8-4d48-a045-ba7b9f875245" />
        </msh:row>
        <msh:row>
        	<msh:autoComplete property="workFunction" vocName="vocWorkFunction" 
        		horizontalFill="true" fieldColSpan="9" size="70"/>
        </msh:row>
        <msh:row>
        	<msh:autoComplete property="additionStatus" vocName="vocAdditionStatus" 
        		horizontalFill="true" fieldColSpan="9" size="70"/>
        </msh:row>
        <msh:row>
        	<msh:autoComplete property="specialist" vocName="workFunction" 
        		horizontalFill="true" fieldColSpan="9" size="70"/>
        </msh:row>
        <msh:row>
        	<msh:autoComplete property="lpu" vocName="lpu"
        		horizontalFill="true" fieldColSpan="9" size="70"/>
        </msh:row>
        <msh:row>
        	<msh:autoComplete property="serviceStream" vocName="vocServiceStream"
        		horizontalFill="true" fieldColSpan="9" size="70"/>
        </msh:row>
        <msh:row>
        	<msh:autoComplete property="workPlaceType" vocName="vocWorkPlaceType"
        		horizontalFill="true" fieldColSpan="9" size="70"/>
        </msh:row>        
        <msh:row>
	        <td class="label" title="Группировака (typePatient)" colspan="1"><label for="typeGroupName" id="typeGroupLabel">Группировка по:</label></td>
	        <td onclick="this.childNodes[1].checked='checked';">
	        	<input type="radio" name="typeGroup" value="1">  датам
	        </td>
	        
	        <td onclick="this.childNodes[1].checked='checked';">
	        	<input type="radio" name="typeGroup" value="2"> ЛПУ
	        </td>
	        <td onclick="this.childNodes[1].checked='checked';">
	        	<input type="radio" name="typeGroup" value="3"> врачам
	        </td>
	        <td onclick="this.childNodes[1].checked='checked';">
	        	<input type="radio" name="typeGroup" value="4">  сотрудникам
	        </td>
	        <td colspan="2" onclick="this.childNodes[1].checked='checked';">
	        	<input type="radio" name="typeGroup" value="5">  специальностям
	        </td>
        </msh:row>
        <msh:row>
        	<td></td>
	        <td  onclick="this.childNodes[1].checked='checked';">
	        	<input type="radio" name="typeGroup" value="6">Поток обслуж.  
	        </td>
	        <td colspan="2" onclick="this.childNodes[1].checked='checked';" style="text-align: left">
	        	<input type="radio" name="typeGroup" value="7">Место обслуж.  
	        </td>
	        <td onclick="this.childNodes[1].checked='checked';">
	        	<input type="radio" name="typeGroup" value="8">Соц. статус  
	        </td>
	        <td onclick="this.childNodes[1].checked='checked';">
	        	<input type="radio" name="typeGroup" value="9">по месяцам  
	        </td>
	        <td onclick="this.childNodes[1].checked='checked';">
	        	<input type="radio" name="typeGroup" value="10">по доп.статусу  
	        </td>
        </msh:row>
        <msh:row>
	        <td class="label" title="Трудоспособный возраст считать с (typeAgeWork)" colspan="1"><label for="typeAgeWorkName" id="typeAgeWorkLabel">Трудоспособный возраст с:</label></td>
	        <td onclick="this.childNodes[1].checked='checked';">
	        	<input type="radio" name="typeAgeWork" value="1">  16 лет
	        </td>
	        <td onclick="this.childNodes[1].checked='checked';" colspan="2">
	        	<input type="radio" name="typeAgeWork" value="2" >  18 лет
	        </td>

        </msh:row>
        <msh:row>
	        <td class="label" title="Просмотр данных (typeView)" colspan="1"><label for="typeViewName" id="typeViewLabel">Отобразить:</label></td>
	        <td onclick="this.childNodes[1].checked='checked';">
	        	<input type="radio" name="typeView" value="1">  услугам
	        </td>
	        <td onclick="this.childNodes[1].checked='checked';" colspan="2">
	        	<input type="radio" name="typeView" value="2" >  кабинетам
	        </td>

        </msh:row>
        <msh:row>
	        <td class="label" title="Дата (typeDate)" colspan="1"><label for="typeDateName" id="typeDateLabel">Дата:</label></td>
	        <td onclick="this.childNodes[1].checked='checked';">
	        	<input type="radio" name="typeDate" value="1">  закрытия СПО
	        </td>
	        <td onclick="this.childNodes[1].checked='checked';">
	        	<input type="radio" name="typeDate" value="2">  посещения
	        </td>

        </msh:row>
        <msh:row>
	        <td class="label" title="Показания (typeEmergency)" colspan="1"><label for="typeEmergencyName" id="typeEmergencyLabel">Показания:</label></td>
	        <td onclick="this.childNodes[1].checked='checked';">
	        	<input type="radio" name="typeEmergency" value="1">  Экстренные
	        </td>
	        <td onclick="this.childNodes[1].checked='checked';" colspan="2">
	        	<input type="radio" name="typeEmergency" value="2" >  Плановые
	        </td>
	        <td onclick="this.childNodes[1].checked='checked';">
	        	<input type="radio" name="typeEmergency" value="3">  Все
	        </td>
	    </msh:row>
        <msh:row>
	        <td class="label" title="База (typeDtype)" colspan="1"><label for="typeDtypeName" id="typeDtypeLabel">Версия:</label></td>
	        <td onclick="this.childNodes[1].checked='checked';">
	        	<input type="radio" name="typeDtype" value="1">  Визит.
	        </td>
	        <td onclick="this.childNodes[1].checked='checked';" colspan="2">
	        	<input type="radio" name="typeDtype" value="2" >  Талон.
	        </td>
	        <td onclick="this.childNodes[1].checked='checked';">
	        	<input type="radio" name="typeDtype" value="3">  Все
	        </td>
	        <td colspan="2">
	        	<input type="button" title="Найти" onclick="this.value=&quot;Поиск...&quot;;  this.form.action=&quot;visit_report_service.do&quot;;this.form.target=&quot;&quot; ; this.form.submit(); return true ;" value="Найти" class="default" id="submitButton" autocomplete="off">
	        	${personClear}
	        </td>
        </msh:row>
        <msh:row>
        <td colspan="5" class="buttons">
		</td>
        
        </msh:row>

    </msh:panel>
    </msh:form>
    
    <%
    	if (request.getParameter("beginDate")!=null && request.getParameter("finishDate")!=null) {
    		ActionUtil.setParameterFilterSql("workFunction","wf.workFunction_id", request) ;
    		ActionUtil.setParameterFilterSql("specialist","smo.workFunctionExecute_id", request) ;
    		ActionUtil.setParameterFilterSql("lpu","w.lpu_id", request) ;
    		ActionUtil.setParameterFilterSql("serviceStream","smo.serviceStream_id", request) ;
    		ActionUtil.setParameterFilterSql("workPlaceType","smo.workPlaceType_id", request) ;
    		ActionUtil.setParameterFilterSql("socialStatus","pvss.id", request) ;
    		ActionUtil.setParameterFilterSql("workFunctionGroup","wf.group_id", request) ;
    		ActionUtil.setParameterFilterSql("medService","ms.id", request) ;
    		ActionUtil.setParameterFilterSql("additionStatus","vas.id", request) ;
    		ActionUtil.setParameterFilterSql("person","wp.id", request) ;
    		if (typeDtype.equals("1")) {
    			request.setAttribute("dtypeSql", "smo.dtype='Visit'") ;
    		} else if (typeDtype.equals("2")) {
    			request.setAttribute("dtypeSql", "smo.dtype='ShortMedCase'") ;
    		} else {
    			request.setAttribute("dtypeSql", "(smo.dtype='ShortMedCase' or smo.dtype='Visit')") ;
    		}
    		if (typeAgeWork.equals("1")) {
    			request.setAttribute("typeAgeWorkId", "16") ;
    		} else {
    			request.setAttribute("typeAgeWorkId", "18") ;
    		}
    		if (typeEmergency.equals("1")) {
    			request.setAttribute("emergencySql", " and smo.emergency='1'") ;
    		} else if (typeEmergency.equals("2")) {
    			request.setAttribute("emergencySql", " and (smo.emergency='0' or smo.emergency is null)") ;
    		}
    		if (typeDate.equals("1")) {
    			request.setAttribute("dateSql", "spo.dateFinish") ;
    		} else {
    			request.setAttribute("dateSql", "smo.dateStart") ;
    		}
    		if (typeGroup.equals("1")) {
    			// Группировка по дате
    			if (typeDate.equals("2")) {
	       			request.setAttribute("groupSql", "to_char(smo.dateStart,'dd.mm.yyyy')") ;
	       			request.setAttribute("groupSqlId", "'&beginDate='||to_char(smo.dateStart,'dd.mm.yyyy')||'&finishDate='||to_char(smo.dateStart,'dd.mm.yyyy')") ;
	       			request.setAttribute("groupName", "Дата посещения") ;
	       			request.setAttribute("groupGroup", "smo.dateStart") ;
	       			request.setAttribute("groupOrder", "smo.dateStart") ;
    			} else {
	       			request.setAttribute("groupSql", "to_char(spo.dateFinish,'dd.mm.yyyy')") ;
	       			request.setAttribute("groupSqlId", "'&beginDate='||to_char(spo.dateFinish,'dd.mm.yyyy')||'&finishDate='||to_char(spo.dateFinish,'dd.mm.yyyy')") ;
	       			request.setAttribute("groupName", "Дата закрытия СПО") ;
	       			request.setAttribute("groupGroup", "spo.dateFinish") ;
	       			request.setAttribute("groupOrder", "spo.dateFinish") ;
    			}
    		} else if (typeGroup.equals("2")) {
    			// Группировка по ЛПУ
       			request.setAttribute("groupSql", "lpu.name") ;
       			request.setAttribute("groupSqlId", "'&lpu='||w.lpu_id") ;
       			request.setAttribute("groupName", "ЛПУ") ;
       			request.setAttribute("groupGroup", "w.lpu_id,lpu.name") ;
       			request.setAttribute("groupOrder", "lpu.name") ;
    		} else if (typeGroup.equals("4")) {
    			// Группировка по врачам 
       			request.setAttribute("groupSql", "wp.lastname||' '||wp.firstname||' '||wp.middlename") ;
       			request.setAttribute("groupSqlId", "'&person='||wp.id") ;
       			request.setAttribute("groupName", "Врач") ;
       			request.setAttribute("groupGroup", "wp.id,wp.lastname,wp.firstname,wp.middlename") ;
       			request.setAttribute("groupOrder", "wp.lastname,wp.firstname,wp.middlename") ;
    		} else if (typeGroup.equals("3")) {
    			// Группировка по сотрудникам 
       			request.setAttribute("groupSql", "vwf.name||' '||wp.lastname||' '||wp.firstname||' '||wp.middlename") ;
       			request.setAttribute("groupSqlId", "'&specialist='||wf.id") ;
       			request.setAttribute("groupName", "Сотрудник") ;
       			request.setAttribute("groupGroup", "wf.id,vwf.name,wp.lastname,wp.firstname,wp.middlename") ;
       			request.setAttribute("groupOrder", "vwf.name,wp.lastname,wp.firstname,wp.middlename") ;
    		} else if (typeGroup.equals("5")) {
    			// Группировка по специальностям
       			request.setAttribute("groupSql", "vwf.name") ;
       			request.setAttribute("groupSqlId", "'&workFunction='||vwf.id") ;
       			request.setAttribute("groupName", "Рабочая функция") ;
       			request.setAttribute("groupGroup", "vwf.id,vwf.name") ;
       			request.setAttribute("groupOrder", "vwf.name") ;
    		} else if (typeGroup.equals("6")) {
    			// Группировка по потоку обслуживания
       			request.setAttribute("groupSql", "vss.name") ;
       			request.setAttribute("groupSqlId", "'&serviceStream='||smo.serviceStream_id") ;
       			request.setAttribute("groupName", "Поток обслуживания") ;
       			request.setAttribute("groupGroup", "smo.serviceStream_id,vss.name") ;
       			request.setAttribute("groupOrder", "vss.name") ;
    		} else if (typeGroup.equals("7")) {
    			// Группировка по месту обслуживания
       			request.setAttribute("groupSql", "vwpt.name") ;
       			request.setAttribute("groupSqlId", "'&workPlaceType='||smo.workPlaceType_id") ;
       			request.setAttribute("groupName", "Место обслуживания") ;
       			request.setAttribute("groupGroup", "smo.workPlaceType_id,vwpt.name") ;
       			request.setAttribute("groupOrder", "vwpt.name") ;
    		} else if (typeGroup.equals("8")) {
    			// Группировка по социальному статусу
       			request.setAttribute("groupSql", "pvss.name") ;
       			request.setAttribute("groupSqlId", "'&socialStatus='||pvss.id") ;
       			request.setAttribute("groupName", "Социальный статус") ;
       			request.setAttribute("groupGroup", "pvss.id,pvss.name") ;
       			request.setAttribute("groupOrder", "pvss.name") ;
    		} else if (typeGroup.equals("9")) {
    			// Группировка по месяцам
       			request.setAttribute("groupSql", "to_char(smo.dateStart,'mm.yyyy')") ;
       			request.setAttribute("groupSqlId", "''") ;
       			request.setAttribute("groupName", "Период") ;
       			request.setAttribute("groupGroup", "to_char(smo.dateStart,'mm.yyyy')") ;
       			request.setAttribute("groupOrder", "to_char(smo.dateStart,'mm.yyyy')") ;
    		} else if (typeGroup.equals("10")) {
    			// Группировка по доп.статусу
       			request.setAttribute("groupSql", "vas.name") ;
       			request.setAttribute("groupSqlId", "'&additionStatus='||vas.id") ;
       			request.setAttribute("groupName", "Доп.статус") ;
       			request.setAttribute("groupGroup", "vas.id,vas.name") ;
       			request.setAttribute("groupOrder", "vas.name") ;
    		}
    		if (typeReestr!=null && (typeReestr.equals("1"))) {
    	%>
    
    <msh:section>
    <msh:sectionTitle>Период с ${beginDate} по ${finishDate}. ${filterInfo} ${specInfo} ${workFunctionInfo} ${lpuInfo} ${serviceStreamInfo}</msh:sectionTitle>
    <msh:sectionContent>
<ecom:webQuery maxResult="1500" name="journal_ticket" nativeSql="
select smo.id as name
,smo.dateStart as nameFld
,p.lastname||' '||p.firstname||' '||p.middlename as fio
,p.birthday as birthday
,	cast(to_char(smo.dateStart,'yyyy') as int)-cast(to_char(p.birthday,'yyyy') as int)
+(case when (cast(to_char(smo.dateStart, 'mm') as int)-cast(to_char(p.birthday, 'mm') as int)
+(case when (cast(to_char(smo.dateStart,'dd') as int) - cast(to_char(p.birthday,'dd') as int)<0) then -1 else 0 end)<0)
then -1 else 0 end) as age
,case when (ad1.addressisvillage='1') then 'сел' else null end as cntVil
,vr.name as vrname
,vwpt.name as vwptname 
,vss.name ||' '|| case when vss.code='HOSPITAL' or vss.code='OTHER' then 
coalesce(' - '||(select 
list(case when sls.deniedHospitalizating_id is null then slsml.name||' '||
to_char(sls.dateStart,'dd.mm.yyyy')||'-'||coalesce(to_char(sls.dateFinish,'dd.mm.yyyy'),
'по тек.мом.') else ' ОТКАЗ '||slsml.name ||' '||to_char(sls.dateStart,'dd.mm.yyyy') end)
from medcase sls 
left join mislpu slsml on slsml.id=sls.department_id
where sls.patient_id=smo.patient_id and sls.dtype='HospitalMedCase'
and (
sls.deniedHospitalizating_id is null and smo.datestart between sls.dateStart and coalesce(sls.datefinish,current_date)
or
sls.deniedHospitalizating_id is not null and smo.datestart between sls.dateStart and sls.datestart+1
)
),'нет данных') 
else '' end as vssname
,list(mkb.code) as mkblist,list(ms.code||' '||ms.name) as servecilist
,olpu.name as olpuname
,ovwf.name||' '||owp.lastname||' '||owp.firstname||' '||owp.middlename||' ('||owflpu.name||')' as owfinfo

FROM MedCase smo  
left join MedCase spo on spo.id=smo.parent_id
LEFT JOIN Patient p ON p.id=smo.patient_id 
left join VocAdditionStatus vas on vas.id=p.additionStatus_id
LEFT JOIN Address2 ad1 on ad1.addressId=p.address_addressId 
LEFT JOIN VocReason vr on vr.id=smo.visitReason_id 
LEFT JOIN vocWorkPlaceType vwpt on vwpt.id=smo.workPlaceType_id 
LEFT JOIN VocServiceStream vss on vss.id=smo.serviceStream_id 
LEFT JOIN VocSocialStatus pvss on pvss.id=p.socialStatus_id
LEFT JOIN WorkFunction wf on wf.id=smo.workFunctionExecute_id 
LEFT JOIN VocWorkFunction vwf on vwf.id=wf.workFunction_id 
LEFT JOIN Worker w on w.id=wf.worker_id 
LEFT JOIN Patient wp on wp.id=w.person_id 
LEFT JOIN MisLpu lpu on lpu.id=w.lpu_id 
left join diagnosis diag on diag.medcase_id=smo.id
left join vocidc10 mkb on mkb.id=diag.idc10_id
left join medcase mssmo on mssmo.parent_id=smo.id and mssmo.dtype='ServiceMedCase'
left join medservice ms on ms.id=mssmo.medservice_id
left join mislpu olpu on olpu.id=smo.orderLpu_id
LEFT JOIN WorkFunction owf on owf.id=smo.orderWorkFunction_id 
LEFT JOIN VocWorkFunction ovwf on ovwf.id=owf.workFunction_id 
LEFT JOIN Worker ow on ow.id=owf.worker_id 
left join mislpu owflpu on owflpu.id=ow.lpu_id
LEFT JOIN Patient owp on owp.id=ow.person_id 
WHERE  ${dtypeSql} 
and ${dateSql} BETWEEN TO_DATE('${beginDate}','dd.mm.yyyy') and TO_DATE('${finishDate}','dd.mm.yyyy') 
and (smo.noActuality is null or smo.noActuality='0')  
${specialistSql} ${workFunctionSql} ${workFunctionGroupSql} ${lpuSql} ${serviceStreamSql} ${medServiceSql} ${workPlaceTypeSql} ${additionStatusSql} ${socialStatusSql}
${personSql}  and smo.dateStart is not null ${emergencySql}
group by ${groupOrder},smo.id,smo.dateStart,p.lastname,p.middlename,p.firstname,p.birthday,ad1.addressisvillage,vr.name,vwpt.name,vss.name
,olpu.name,ovwf.name,owp.lastname,owp.firstname,owp.middlename,smo.patient_id,vss.code,owflpu.name

ORDER BY ${groupOrder},p.lastname,p.firstname,p.middlename
" guid="4a720225-8d94-4b47-bef3-4dbbe79eec74" /> 
        <msh:table
         name="journal_ticket" action="entitySubclassView-mis_medCase.do" idField="1" noDataMessage="Не найдено">
            <msh:tableColumn columnName="Дата посещения" property="2"/>            
            <msh:tableColumn columnName="ФИО пациента" property="3"/>
            <msh:tableColumn columnName="Дата рождения" property="4"/>
            <msh:tableColumn columnName="Возраст" property="5"/>
            <msh:tableColumn columnName="Житель" property="6"/>
            <msh:tableColumn columnName="цель визита" property="7"/>
            <msh:tableColumn columnName="место" property="8"/>
            <msh:tableColumn columnName="поток обсл." property="9"/>
            <msh:tableColumn columnName="диагноз" property="10"/>
            <msh:tableColumn columnName="услуга" property="11"/>
            <msh:tableColumn columnName="напр. ЛПУ" property="12"/>
            <msh:tableColumn columnName="напр. внутр." property="13"/>
        </msh:table>
    </msh:sectionContent>

    </msh:section>
    <% } else {
    	if (typeView!=null && (typeView.equals("1"))) {
    
    	%>
    <msh:section>
<ecom:webQuery name="journal_ticket" nameFldSql="journal_ticket_sql" nativeSql="
select
''||'&medService='||ms.id||${groupSqlId}||${workFunctionSqlId}||${additionStatusSqlId}||${specialistSqlId}||${lpuSqlId}||${serviceStreamSqlId}||${workPlaceTypeSqlId}||${socialStatusSqlId}||'&beginDate=${beginDate}&finishDate=${finishDate}' as name
,${groupSql} as nameFld
,ms.code||' '||ms.name as vmsname
,count(*) as cntAll
,count(case when (vwpt.code='POLYCLINIC') then smc.id else null end) as cntAllPoly
,count(case when vwpt.code='POLYCLINIC' and (ad1.addressIsVillage='1') then smc.id else null end) as cntVil
,count(case when vwpt.code='POLYCLINIC' and cast(to_char(smo.dateStart,'yyyy') as int)-cast(to_char(p.birthday,'yyyy') as int)
+(case when (cast(to_char(smo.dateStart, 'mm') as int)-cast(to_char(p.birthday, 'mm') as int)
+(case when (cast(to_char(smo.dateStart,'dd') as int) - cast(to_char(p.birthday,'dd') as int)<0) then -1 else 0 end)<0)
then -1 else 0 end)<18 
	then smc.id else null end) as cntAll17
,count(case when vwpt.code='POLYCLINIC' and 
	cast(to_char(smo.dateStart,'yyyy') as int)-cast(to_char(p.birthday,'yyyy') as int)
+(case when (cast(to_char(smo.dateStart, 'mm') as int)-cast(to_char(p.birthday, 'mm') as int)
+(case when (cast(to_char(smo.dateStart,'dd') as int) - cast(to_char(p.birthday,'dd') as int)<0) then -1 else 0 end)<0)
then -1 else 0 end)>=60 then smc.id else null end) as cntAll60

,count(case when (vss.code='OBLIGATORYINSURANCE') then smc.id else null end) as cntOMC
,count(case when (vss.code='BUDGET') then smc.id else null end) as cntBudget
,count(case when (vss.code='CHARGED') then smc.id else null end) as cntCharged
,count(case when (vss.code='PRIVATEINSURANCE') then smc.id else null end) as cntPrivateIns
,count(case when (vss.code='DOGOVOR') then smc.id else null end) as cntdogovor
,count(case when (vss.code='HOSPITAL') then smc.id else null end) as cnthospital
,count(case when (vss.code='OTHER') then smc.id else null end) as cntother

FROM MedCase smo
left join medcase smc on smc.parent_id=smo.id and smc.dtype='ServiceMedCase'
left join medservice ms on ms.id=smc.medservice_id
left join MedCase spo on spo.id=smo.parent_id
LEFT JOIN Patient p ON p.id=smo.patient_id 
left join VocAdditionStatus vas on vas.id=p.additionStatus_id
LEFT JOIN Address2 ad1 on ad1.addressId=p.address_addressId 
LEFT JOIN VocReason vr on vr.id=smo.visitReason_id 
LEFT JOIN vocWorkPlaceType vwpt on vwpt.id=smo.workPlaceType_id 
LEFT JOIN VocServiceStream vss on vss.id=smo.serviceStream_id 
LEFT JOIN VocSocialStatus pvss on pvss.id=p.socialStatus_id
LEFT JOIN WorkFunction wf on wf.id=smo.workFunctionExecute_id 
LEFT JOIN VocWorkFunction vwf on vwf.id=wf.workFunction_id 
LEFT JOIN Worker w on w.id=wf.worker_id 
LEFT JOIN Patient wp on wp.id=w.person_id 
LEFT JOIN MisLpu lpu on lpu.id=w.lpu_id 
WHERE  ${dtypeSql} 
and ${dateSql} BETWEEN TO_DATE('${beginDate}','dd.mm.yyyy') and TO_DATE('${finishDate}','dd.mm.yyyy') 
and smc.medservice_id is not null
and (smo.noActuality is null or smo.noActuality='0')
${specialistSql} ${workFunctionSql} ${workFunctionGroupSql} ${lpuSql} ${serviceStreamSql} ${medServiceSql} ${workPlaceTypeSql} ${additionStatusSql} ${socialStatusSql}
${personSql}  and smo.dateStart is not null ${emergencySql}
GROUP BY ms.id,ms.code,ms.name,${groupGroup} ORDER BY ${groupOrder}
" guid="4a720225-8d94-4b47-bef3-4dbbe79eec74" /> 
    <msh:sectionTitle>
    <form action="print-f039_stand.do" method="post" target="_blank">
    Период с ${beginDate} по ${finishDate}. ${filterInfo} ${specInfo} ${workFunctionInfo} ${lpuInfo} ${serviceStreamInfo}
    <input type='hidden' name="sqlText" id="sqlText" value="${journal_ticket_sql}"> 
    <input type='hidden' name="sqlInfo" id="sqlInfo" value="Период с ${beginDate} по ${finishDate}. ${filterInfo} ${specInfo} ${workFunctionInfo} ${lpuInfo} ${serviceStreamInfo}.">
    <input type='hidden' name="sqlColumn" id="sqlColumn" value="${groupName}">
    <input type='hidden' name="s" id="s" value="PrintService">
    <input type='hidden' name="m" id="m" value="printNativeQuery">
    <input type="submit" value="Печать"> 
    </form>
    </msh:sectionTitle>
    <msh:sectionContent>
  
        <msh:table
         name="journal_ticket" action="visit_report_service.do?typeReestr=1&typeView=${typeView}&typeDtype=${typeDtype}&typeEmergency=${typeEmergency}&typeDate=${typeDate}&typeGroup=${typeGroup}" 
         idField="1" noDataMessage="Не найдено">
         <msh:tableNotEmpty>
         	<tr>
         		<th></th>
         		<th></th>
         		<th></th>
         		<th colspan="4">Услуги</th>

         		<th colspan="7">Услуги по видам оплаты</th>
         	</tr>
         </msh:tableNotEmpty>  
            <msh:tableColumn columnName="${groupName}" property="2"/>            
            <msh:tableColumn columnName="Услуги" property="3"/>
            <msh:tableColumn isCalcAmount="true" columnName="Общее кол-во" property="4"/>
            
            <msh:tableColumn isCalcAmount="true" columnName="Всего" property="5"/>
            <msh:tableColumn isCalcAmount="true" columnName="из всего с.ж." property="6"/>
            <msh:tableColumn isCalcAmount="true" columnName="из всего до 17 лет" property="7"/>
            <msh:tableColumn isCalcAmount="true" columnName="из всего старше 60 лет" property="8"/>
            
            
            <msh:tableColumn isCalcAmount="true" columnName="ОМС" property="9"/>
            <msh:tableColumn isCalcAmount="true" columnName="бюджет" property="10"/>
            <msh:tableColumn isCalcAmount="true" columnName="платные" property="11"/>
            <msh:tableColumn isCalcAmount="true" columnName="ДМС" property="12"/>
            <msh:tableColumn isCalcAmount="true" columnName="Договор" property="13"/>
            <msh:tableColumn isCalcAmount="true" columnName="Стационар" property="14"/>
            <msh:tableColumn isCalcAmount="true" columnName="Другое" property="15"/>
        </msh:table>

    </msh:sectionContent>

    </msh:section>    	
    	<%
    } else if (typeView!=null && (typeView.equals("2"))) {
    	%>


    <msh:section>
<ecom:webQuery name="journal_ticket" nameFldSql="journal_ticket_sql" nativeSql="
select
''||'&workFunctionGroup='||wfg.id||${groupSqlId}||${workFunctionSqlId}||${additionStatusSqlId}||${specialistSqlId}||${lpuSqlId}||${serviceStreamSqlId}||${workPlaceTypeSqlId}||${socialStatusSqlId}||'&beginDate=${beginDate}&finishDate=${finishDate}' as name
,${groupSql} as nameFld
,coalesce(wfg.groupname)
,count(*) as cntAll
,count(case when (vwpt.code='POLYCLINIC') then 1 else null end) as cntAllPoly
,count(case when vwpt.code='POLYCLINIC' and (ad1.addressIsVillage='1') then 1 else null end) as cntVil
,count(case when vwpt.code='POLYCLINIC' and cast(to_char(smo.dateStart,'yyyy') as int)-cast(to_char(p.birthday,'yyyy') as int)
+(case when (cast(to_char(smo.dateStart, 'mm') as int)-cast(to_char(p.birthday, 'mm') as int)
+(case when (cast(to_char(smo.dateStart,'dd') as int) - cast(to_char(p.birthday,'dd') as int)<0) then -1 else 0 end)<0)
then -1 else 0 end)<18 
	then 1 else null end) as cntAll17
,count(case when vwpt.code='POLYCLINIC' and 
	cast(to_char(smo.dateStart,'yyyy') as int)-cast(to_char(p.birthday,'yyyy') as int)
+(case when (cast(to_char(smo.dateStart, 'mm') as int)-cast(to_char(p.birthday, 'mm') as int)
+(case when (cast(to_char(smo.dateStart,'dd') as int) - cast(to_char(p.birthday,'dd') as int)<0) then -1 else 0 end)<0)
then -1 else 0 end)>=60 then 1 else null end) as cntAll60
,count(case when (vss.code='OBLIGATORYINSURANCE') then smc.id else null end) as cntOMC
,count(case when (vss.code='BUDGET') then smc.id else null end) as cntBudget
,count(case when (vss.code='CHARGED') then smc.id else null end) as cntCharged
,count(case when (vss.code='PRIVATEINSURANCE') then smc.id else null end) as cntPrivateIns
,count(case when (vss.code='DOGOVOR') then smc.id else null end) as cntdogovor
,count(case when (vss.code='HOSPITAL') then smc.id else null end) as cnthospital
,count(case when (vss.code='OTHER') then smc.id else null end) as cntother

FROM MedCase smo
left join medcase smc on smc.parent_id=smo.id and smc.dtype='ServiceMedCase'
left join medservice ms on ms.id=smc.medservice_id
left join MedCase spo on spo.id=smo.parent_id
LEFT JOIN Patient p ON p.id=smo.patient_id 
left join VocAdditionStatus vas on vas.id=p.additionStatus_id
LEFT JOIN Address2 ad1 on ad1.addressId=p.address_addressId 
LEFT JOIN VocReason vr on vr.id=smo.visitReason_id 
LEFT JOIN vocWorkPlaceType vwpt on vwpt.id=smo.workPlaceType_id 
LEFT JOIN VocServiceStream vss on vss.id=smo.serviceStream_id 
LEFT JOIN VocSocialStatus pvss on pvss.id=p.socialStatus_id
LEFT JOIN WorkFunction wf on wf.id=smo.workFunctionExecute_id
left join workfunction wfg on wfg.id=wf.group_id 
LEFT JOIN VocWorkFunction vwf on vwf.id=wf.workFunction_id 
LEFT JOIN Worker w on w.id=wf.worker_id 
LEFT JOIN Patient wp on wp.id=w.person_id 
LEFT JOIN MisLpu lpu on lpu.id=w.lpu_id 
WHERE  ${dtypeSql} 
and ${dateSql} BETWEEN TO_DATE('${beginDate}','dd.mm.yyyy') and TO_DATE('${finishDate}','dd.mm.yyyy') 
and smc.medservice_id is not null
and (smo.noActuality is null or smo.noActuality='0')
${specialistSql} ${workFunctionSql} ${workFunctionGroupSql} ${lpuSql} ${serviceStreamSql} ${medServiceSql} ${workPlaceTypeSql} ${additionStatusSql} ${socialStatusSql}
${personSql}  and smo.dateStart is not null ${emergencySql}
GROUP BY wfg.id,wfg.groupname,${groupGroup} ORDER BY ${groupOrder}
" guid="4a720225-8d94-4b47-bef3-4dbbe79eec74" /> 
    <msh:sectionTitle>
    <form action="print-f039_stand.do" method="post" target="_blank">
    Период с ${beginDate} по ${finishDate}. ${filterInfo} ${specInfo} ${workFunctionInfo} ${lpuInfo} ${serviceStreamInfo}
    <input type='hidden' name="sqlText" id="sqlText" value="${journal_ticket_sql}"> 
    <input type='hidden' name="sqlInfo" id="sqlInfo" value="Период с ${beginDate} по ${finishDate}. ${filterInfo} ${specInfo} ${workFunctionInfo} ${lpuInfo} ${serviceStreamInfo}.">
    <input type='hidden' name="sqlColumn" id="sqlColumn" value="${groupName}">
    <input type='hidden' name="s" id="s" value="PrintService">
    <input type='hidden' name="m" id="m" value="printNativeQuery">
    <input type="submit" value="Печать"> 
    </form>
    </msh:sectionTitle>
    <msh:sectionContent>
  
        <msh:table
         name="journal_ticket" action="visit_report_service.do?typeReestr=1&typeView=${typeView}&typeDtype=${typeDtype}&typeEmergency=${typeEmergency}&typeDate=${typeDate}&typeGroup=${typeGroup}" 
         idField="1" noDataMessage="Не найдено">
         <msh:tableNotEmpty>
         	<tr>
         		<th></th>
         		<th></th>
         		<th></th>
         		<th colspan="4">Услуги</th>

         		<th colspan="7">Услуги по видам оплаты</th>
         	</tr>
         </msh:tableNotEmpty>  
            <msh:tableColumn columnName="${groupName}" property="2"/>            
            <msh:tableColumn columnName="Кабинет" property="3"/>
            <msh:tableColumn isCalcAmount="true" columnName="Общее кол-во посещ." property="4"/>
            
            <msh:tableColumn isCalcAmount="true" columnName="Всего" property="5"/>
            <msh:tableColumn isCalcAmount="true" columnName="из всего с.ж." property="6"/>
            <msh:tableColumn isCalcAmount="true" columnName="из всего до 17 лет" property="7"/>
            <msh:tableColumn isCalcAmount="true" columnName="из всего старше 60 лет" property="8"/>
            
            
            <msh:tableColumn isCalcAmount="true" columnName="ОМС" property="9"/>
            <msh:tableColumn isCalcAmount="true" columnName="бюджет" property="10"/>
            <msh:tableColumn isCalcAmount="true" columnName="платные" property="11"/>
            <msh:tableColumn isCalcAmount="true" columnName="ДМС" property="12"/>
            <msh:tableColumn isCalcAmount="true" columnName="Договор" property="13"/>
            <msh:tableColumn isCalcAmount="true" columnName="Стационар" property="14"/>
            <msh:tableColumn isCalcAmount="true" columnName="Другое" property="15"/>
        </msh:table>
    </msh:sectionContent>

    </msh:section>    	

    <% }
    }
    		} else {%>
    	<i>Выберите параметры поиска и нажмите "Найти" </i>
    	<% }   %>
  </tiles:put>
  <tiles:put name="javascript" type="string">
  	<script type="text/javascript">

    checkFieldUpdate('typeGroup','${typeGroup}',1) ;
    checkFieldUpdate('typeAgeWork','${typeAgeWork}',1) ;
    checkFieldUpdate('typeView','${typeView}',1) ;
    checkFieldUpdate('typeDtype','${typeDtype}',3) ;
    checkFieldUpdate('typeDate','${typeDate}',2) ;
    checkFieldUpdate('typeEmergency','${typeEmergency}',3) ;
    
    
    function checkFieldUpdate(aField,aValue,aDefault) {
    	
    	eval('var chk =  document.forms[0].'+aField) ;
    	var max = chk.length ;
    	if ((+aValue)>max) {
    		chk[+aDefault-1].checked='checked' ;
    	} else {
    		chk[+aValue-1].checked='checked' ;
    	}
    }
    
  	function getId(aBis) {
  		var typeGroup = document.forms[0].typeGroup ;
		var args=$('beginDate').value+":"+$('finishDate').value
 			+":"+getCheckedValue(typeGroup)
 			+":"+$('specialist').value
 			+":"+$('workFunction').value
 			+":"+$('lpu').value
 			+":"+$('serviceStream').value
 			+":"+$('workPlaceType').value
 			+":0";
		//aSpecialist, aWorkFunction, aLpu, aServiceStream
		//aGroupBy, aStartDate, aFinishDate
		//, aSpecialist, aWorkFunction, aLpu, aServiceStream 			
		$('id').value =args ; 
		if (+aBis>0) {
			$('m').value='f039add' ;
		} else {
			$('m').value='f039' ;
		}
		
	}
  	function getCheckedValue(radioGrp) {
  		var radioValue ;
  		for(i=0; i < radioGrp.length; i++) {
  		  if (radioGrp[i].checked == true){
  		    radioValue = radioGrp[i].value;
  		    break ;
  		  }
  		}
  		return radioValue ;
  	}
  		
  	</script>
  </tiles:put>

</tiles:insert>