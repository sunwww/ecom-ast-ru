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
        <msh:title mainMenu="Poly">Просмотр данных по Форме 039/у-02 </msh:title>
    </tiles:put>

    <tiles:put name='side' type='string'>
        <tags:visit_finds currentAction="report039"/>
    </tiles:put>
    
  <tiles:put name="body" type="string">
  <%
  	String typeReestr =ActionUtil.updateParameter("Form039Action","typeReestr","2", request) ;
  	String typeGroup =ActionUtil.updateParameter("Form039Action","typeGroup","1", request) ;
	String typeView =ActionUtil.updateParameter("Form039Action","typeView","1", request) ;
	String typeDtype =ActionUtil.updateParameter("Form039Action","typeDtype","3", request) ;
	String person = request.getParameter("person") ;
	
	if (person!=null && !person.equals("") && !person.equals("0")) {
		request.setAttribute("personClear", "<input type='button' name='clearPerson' value='Очистить инф. о сотруднике' onclick=\"$('person').value='';this.style.display='none'\">") ;
	}
  %>
    <msh:form action="/visit_f039_list.do" defaultField="beginDate" disableFormDataConfirm="true" method="GET" guid="d7b31bc2-38f0-42cc-8d6d-19395273168f">
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
        </msh:row>
        <msh:row>
	        <td class="label" title="Просмотр данных (typeView)" colspan="1"><label for="typeViewName" id="typeViewLabel">Отобразить:</label></td>
	        <td onclick="this.childNodes[1].checked='checked';">
	        	<input type="radio" name="typeView" value="1">  039 форма
	        </td>
	        <td onclick="this.childNodes[1].checked='checked';" colspan="2">
	        	<input type="radio" name="typeView" value="2" >  039 bis
	        </td>
	        <td onclick="this.childNodes[1].checked='checked';">
	        	<input type="radio" name="typeView" value="3">  039 cons
	        </td>

        </msh:row>
        <msh:row>
	        <td class="label" title="База (typeDtype)" colspan="1"><label for="typeDtypeName" id="typeDtypeLabel">Отобразить:</label></td>
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
	        	<input type="button" title="Найти" onclick="this.value=&quot;Поиск...&quot;;  this.form.action=&quot;visit_f039_list.do&quot;;this.form.target=&quot;&quot; ; this.form.submit(); return true ;" value="Найти" class="default" id="submitButton" autocomplete="off">
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
    		ActionUtil.setParameterFilterSql("person","wp.id", request) ;
    		if (typeDtype.equals("1")) {
    			request.setAttribute("dtypeSql", "smo.dtype='Visit'") ;
    		} else if (typeDtype.equals("2")) {
    			request.setAttribute("dtypeSql", "smo.dtype='ShortMedCase'") ;
    		} else {
    			request.setAttribute("dtypeSql", "(smo.dtype='ShortMedCase' or smo.dtype='Visit')") ;
    		}
    		if (typeGroup.equals("1")) {
    			// Группировка по дате
       			request.setAttribute("groupSql", "to_char(smo.dateStart,'dd.mm.yyyy')") ;
       			request.setAttribute("groupSqlId", "'&beginDate='||to_char(smo.dateStart,'dd.mm.yyyy')||'&finishDate='||to_char(smo.dateStart,'dd.mm.yyyy')") ;
       			request.setAttribute("groupName", "Дата") ;
       			request.setAttribute("groupGroup", "smo.dateStart") ;
       			request.setAttribute("groupOrder", "smo.dateStart") ;
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
    			// Группировка по сотрудник 
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
,case when (ad1.domen=5 or ad2.domen=5) then 'сел' else null end as cntVil
,vr.name as vrname
,vwpt.name as vwptname 
,vss.name as vssname
FROM MedCase smo  
LEFT JOIN Patient p ON p.id=smo.patient_id 
LEFT JOIN Address2 ad1 on ad1.Id=p.address_Id 
LEFT JOIN Address2 ad2 on ad2.Id=ad1.parent_Id  
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
and smo.dateStart BETWEEN TO_DATE('${beginDate}','dd.mm.yyyy') and TO_DATE('${finishDate}','dd.mm.yyyy') 
and (smo.noActuality is null or smo.noActuality='0')  
${specialistSql} ${workFunctionSql} ${lpuSql} ${serviceStreamSql} ${workPlaceTypeSql} ${socialStatusSql}
${personSql}
ORDER BY ${groupOrder},p.lastname,p.firstname,p.middlename
" guid="4a720225-8d94-4b47-bef3-4dbbe79eec74" /> 
        <msh:table
         name="journal_ticket" action="entityShortView-smo_visit.do" idField="1" noDataMessage="Не найдено">
            <msh:tableColumn columnName="Дата посещения" property="2"/>            
            <msh:tableColumn columnName="ФИО пациента" property="3"/>
            <msh:tableColumn columnName="Дата рождения" property="4"/>
            <msh:tableColumn columnName="Возраст" property="5"/>
            <msh:tableColumn columnName="Житель" property="6"/>
            <msh:tableColumn columnName="цель визита" property="7"/>
            <msh:tableColumn columnName="место" property="8"/>
            <msh:tableColumn columnName="поток обсл." property="9"/>
        </msh:table>
    </msh:sectionContent>

    </msh:section>
    <% } else {
    	if (typeView!=null && (typeView.equals("1"))) {
    
    	%>
    <msh:section>
<ecom:webQuery name="journal_ticket" nameFldSql="journal_ticket_sql" nativeSql="
select
''||${groupSqlId}||${workFunctionSqlId}||${specialistSqlId}||${lpuSqlId}||${serviceStreamSqlId}||${workPlaceTypeSqlId}||${socialStatusSqlId}||'&beginDate=${beginDate}&finishDate=${finishDate}' as name
,${groupSql} as nameFld
,count(*) as cntAll
,count(case when (ad1.domen=5 or ad2.domen=5) then 1 else null end) as cntVil
,count(case when cast(to_char(smo.dateStart,'yyyy') as int)-cast(to_char(p.birthday,'yyyy') as int)
+(case when (cast(to_char(smo.dateStart, 'mm') as int)-cast(to_char(p.birthday, 'mm') as int)
+(case when (cast(to_char(smo.dateStart,'dd') as int) - cast(to_char(p.birthday,'dd') as int)<0) then -1 else 0 end)<0)
then -1 else 0 end)<18 
	then 1 else null end) as cntAll17
,count(case when 
	cast(to_char(smo.dateStart,'yyyy') as int)-cast(to_char(p.birthday,'yyyy') as int)
+(case when (cast(to_char(smo.dateStart, 'mm') as int)-cast(to_char(p.birthday, 'mm') as int)
+(case when (cast(to_char(smo.dateStart,'dd') as int) - cast(to_char(p.birthday,'dd') as int)<0) then -1 else 0 end)<0)
then -1 else 0 end)>=60 then 1 else null end) as cntAll60
,count(case when vr.code='ILLNESS' then 1 else null end) as cntIllnessAll
,count(case when vr.code='ILLNESS' 
	and (
	cast(to_char(smo.dateStart,'yyyy') as int)-cast(to_char(p.birthday,'yyyy') as int)
+(case when (cast(to_char(smo.dateStart, 'mm') as int)-cast(to_char(p.birthday, 'mm') as int)
+(case when (cast(to_char(smo.dateStart,'dd') as int) - cast(to_char(p.birthday,'dd') as int)<0) then -1 else 0 end)<0)
then -1 else 0 end)<18
	) then 1 else null end) as cntIllnes17
,count(case when vr.code='ILLNESS' and 
	(
		cast(to_char(smo.dateStart,'yyyy') as int)-cast(to_char(p.birthday,'yyyy') as int)
		+(case when (cast(to_char(smo.dateStart, 'mm') as int)-cast(to_char(p.birthday, 'mm') as int)
		+(case when (cast(to_char(smo.dateStart,'dd') as int) - cast(to_char(p.birthday,'dd') as int)<0) then -1 else 0 end)<0)
		then -1 else 0 end)>=60
	) then 1 else null end) as cntIllnes60
,count(case when vr.code='PROFYLACTIC' then 1 else null end) as cntProfAll
,count(case when (vwpt.code='HOME' or vwpt.code='HOMEACTIVE') then 1 else null end) as cntHomeAll
,count(case when vr.code='ILLNESS' and (vwpt.code='HOME' or vwpt.code='HOMEACTIVE') then 1 else null end) as cntIllnesHomeAll
,count(case when vr.code='ILLNESS' and (vwpt.code='HOME' or vwpt.code='HOMEACTIVE') 
	and (
		cast(to_char(smo.dateStart,'yyyy') as int)-cast(to_char(p.birthday,'yyyy') as int)
		+(case when (cast(to_char(smo.dateStart, 'mm') as int)-cast(to_char(p.birthday, 'mm') as int)
		+(case when (cast(to_char(smo.dateStart,'dd') as int) - cast(to_char(p.birthday,'dd') as int)<0) then -1 else 0 end)<0)
		then -1 else 0 end)<18
	) then 1 else null end) as cntIllnesHome17
,count(case when vr.code='ILLNESS' and (vwpt.code='HOME' or vwpt.code='HOMEACTIVE') 
	and (
		cast(to_char(smo.dateStart,'yyyy') as int)-cast(to_char(p.birthday,'yyyy') as int)
		+(case when (cast(to_char(smo.dateStart, 'mm') as int)-cast(to_char(p.birthday, 'mm') as int)
		+(case when (cast(to_char(smo.dateStart,'dd') as int) - cast(to_char(p.birthday,'dd') as int)<0) then -1 else 0 end)<0)
		then -1 else 0 end)<2
	) then 1 else null end) as cntIllnesHome01
,count(case when vr.code='ILLNESS' and (vwpt.code='HOME' or vwpt.code='HOMEACTIVE') 
	and (
		cast(to_char(smo.dateStart,'yyyy') as int)-cast(to_char(p.birthday,'yyyy') as int)
		+(case when (cast(to_char(smo.dateStart, 'mm') as int)-cast(to_char(p.birthday, 'mm') as int)
		+(case when (cast(to_char(smo.dateStart,'dd') as int) - cast(to_char(p.birthday,'dd') as int)<0) then -1 else 0 end)<0)
		then -1 else 0 end)>=60
	) then 1 else null end) as cntIllnesHome60
,count(case when vr.code='PROFYLACTIC' and (vwpt.code='HOME' or vwpt.code='HOMEACTIVE') 
	and (
		cast(to_char(smo.dateStart,'yyyy') as int)-cast(to_char(p.birthday,'yyyy') as int)
		+(case when (cast(to_char(smo.dateStart, 'mm') as int)-cast(to_char(p.birthday, 'mm') as int)
		+(case when (cast(to_char(smo.dateStart,'dd') as int) - cast(to_char(p.birthday,'dd') as int)<0) then -1 else 0 end)<0)
		then -1 else 0 end)<18
	) then 1 else null end) as cntProfHome17
,count(case when vr.code='PROFYLACTIC' and (vwpt.code='HOME' or vwpt.code='HOMEACTIVE') 
	and (
		cast(to_char(smo.dateStart,'yyyy') as int)-cast(to_char(p.birthday,'yyyy') as int)
		+(case when (cast(to_char(smo.dateStart, 'mm') as int)-cast(to_char(p.birthday, 'mm') as int)
		+(case when (cast(to_char(smo.dateStart,'dd') as int) - cast(to_char(p.birthday,'dd') as int)<0) then -1 else 0 end)<0)
		then -1 else 0 end)<2
	) then 1 else null end) as cntProfHome01
,count(case when (vss.code='OBLIGATORYINSURANCE') then 1 else null end) as cntOMC
,count(case when (vss.code='BUDGET') then 1 else null end) as cntBudget
,count(case when (vss.code='CHARGED') then 1 else null end) as cntCharged
,count(case when (vss.code='PRIVATEINSURANCE') then 1 else null end) as cntPrivateIns
,count(case when vr.code='CONSULTATION' then 1 else null end) as cntConsultationAll

FROM MedCase smo  
LEFT JOIN Patient p ON p.id=smo.patient_id 
LEFT JOIN Address2 ad1 on ad1.Id=p.address_Id 
LEFT JOIN Address2 ad2 on ad2.Id=ad1.parent_Id  
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
and smo.dateStart BETWEEN TO_DATE('${beginDate}','dd.mm.yyyy') and TO_DATE('${finishDate}','dd.mm.yyyy') 
and (smo.noActuality is null or smo.noActuality='0')
${specialistSql} ${workFunctionSql} ${lpuSql} ${serviceStreamSql} ${workPlaceTypeSql} ${socialStatusSql}
${personSql}
GROUP BY ${groupGroup} ORDER BY ${groupOrder}
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
         name="journal_ticket" action="visit_f039_list.do?typeReestr=1&typeView=${typeView}&typeGroup=${typeGroup}" 
         idField="1" noDataMessage="Не найдено">
            <msh:tableColumn columnName="${groupName}" property="2"/>            
            <msh:tableColumn isCalcAmount="true" columnName="Кол-во посещ" property="3"/>
            <msh:tableColumn isCalcAmount="true" columnName="из них сельс.жител." property="4"/>
            <msh:tableColumn isCalcAmount="true" columnName="в том числе до 17 лет" property="5"/>
            <msh:tableColumn isCalcAmount="true" columnName="в том числе старше 60 лет" property="6"/>
            <msh:tableColumn isCalcAmount="true" columnName="По поводу конс" property="22"/>
            <msh:tableColumn isCalcAmount="true" columnName="По поводу забол" property="7"/>
            <msh:tableColumn isCalcAmount="true" columnName="в том числе до 17 лет" property="8"/>
            <msh:tableColumn isCalcAmount="true" columnName="в том числе старше 60 лет" property="9"/>
            <msh:tableColumn isCalcAmount="true" columnName="Профилак." property="10"/>
            <msh:tableColumn isCalcAmount="true" columnName="На дому" property="11"/>
            <msh:tableColumn isCalcAmount="true" columnName="На дому по забол." property="12"/>
            <msh:tableColumn isCalcAmount="true" columnName="в том числе до 17 лет" property="13"/>
            <msh:tableColumn isCalcAmount="true" columnName="0-1(вкл) лет" property="14"/>
            <msh:tableColumn isCalcAmount="true" columnName="в том числе старше 60 лет" property="15"/>
            <msh:tableColumn isCalcAmount="true" columnName="На дому проф до 17 лет" property="16"/>
            <msh:tableColumn isCalcAmount="true" columnName="0-1(вкл) лет" property="17"/>
            <msh:tableColumn isCalcAmount="true" columnName="ОМС" property="18"/>
            <msh:tableColumn isCalcAmount="true" columnName="бюджет" property="19"/>
            <msh:tableColumn isCalcAmount="true" columnName="платные" property="20"/>
            <msh:tableColumn isCalcAmount="true" columnName="ДМС" property="21"/>
        </msh:table>
    </msh:sectionContent>

    </msh:section>    	
    	<%
    } else if (typeView!=null && (typeView.equals("2"))) {
    	%>
    <msh:section>
<ecom:webQuery name="journal_ticket" nativeSql="
select
''||${groupSqlId}||${workFunctionSqlId}||${specialistSqlId}||${lpuSqlId}||${serviceStreamSqlId}||${workPlaceTypeSqlId}||${socialStatusSqlId}||'&beginDate=${beginDate}&finishDate=${finishDate}' as name
,${groupSql} as nameFld
,count(*) as cntAll
,count(case when (ad1.domen=5 or ad2.domen=5) then 1 else null end) as cntVil
,count(case when 
	(to_date(case when to_char(smo.dateStart,'dd.mm')='29.02' then '28.02' else to_char(smo.dateStart,'dd.mm') end ||'.'
	||(cast(to_char(smo.dateStart,'yyyy') as int)-18),'dd.mm.yyyy') < p.birthday) 
	then 1 else null end) as cntAll17
,count(case when 
	(to_date(case when to_char(smo.dateStart,'dd.mm')='29.02' then '28.02' else to_char(smo.dateStart,'dd.mm') end ||'.'
	||(cast(to_char(smo.dateStart,'yyyy') as int)-60),'dd.mm.yyyy') >= p.birthday) then 1 else null end) as cntAll60

,count(case when vr.code='CONSULTATION' then 1 else null end) as cntConsultationAll
,count(case when vr.code='ILLNESS' then 1 else null end) as cntIllnessAll
,count(case when vr.code='ILLNESS' 
	and (to_date(case when to_char(smo.dateStart,'dd.mm')='29.02' then '28.02' else to_char(smo.dateStart,'dd.mm') end ||'.'
	||(cast(to_char(smo.dateStart,'yyyy') as int)-18),'dd.mm.yyyy') < p.birthday) then 1 else null end) as cntIllnes17
,count(case when vr.code='ILLNESS' and 
	(to_date(case when to_char(smo.dateStart,'dd.mm')='29.02' then '28.02' else to_char(smo.dateStart,'dd.mm') end ||'.'
	||(cast(to_char(smo.dateStart,'yyyy') as int)-60),'dd.mm.yyyy') >= p.birthday) then 1 else null end) as cntIllnes60
,count(case when vr.code='PROFYLACTIC' then 1 else null end) as cntProfAll
,count(case when (vwpt.code='HOME' or vwpt.code='HOMEACTIVE') then 1 else null end) as cntHomeAll
,count(case when vr.code='ILLNESS' and (vwpt.code='HOME' or vwpt.code='HOMEACTIVE') then 1 else null end) as cntIllnesHomeAll
,count(case when vr.code='ILLNESS' and (vwpt.code='HOME' or vwpt.code='HOMEACTIVE') 
	and (to_date(case when to_char(smo.dateStart,'dd.mm')='29.02' then '28.02' else to_char(smo.dateStart,'dd.mm') end ||'.'
	||(cast(to_char(smo.dateStart,'yyyy') as int)-18),'dd.mm.yyyy') < p.birthday) then 1 else null end) as cntIllnesHome17
,count(case when vr.code='ILLNESS' and (vwpt.code='HOME' or vwpt.code='HOMEACTIVE') 
	and (to_date(case when to_char(smo.dateStart,'dd.mm')='29.02' then '28.02' else to_char(smo.dateStart,'dd.mm') end ||'.'
	||(cast(to_char(smo.dateStart,'yyyy') as int)-2),'dd.mm.yyyy') < p.birthday) then 1 else null end) as cntIllnesHome01
,count(case when vr.code='ILLNESS' and (vwpt.code='HOME' or vwpt.code='HOMEACTIVE') 
	and (to_date(case when to_char(smo.dateStart,'dd.mm')='29.02' then '28.02' else to_char(smo.dateStart,'dd.mm') end ||'.'
	||(cast(to_char(smo.dateStart,'yyyy') as int)-60),'dd.mm.yyyy') >= p.birthday) 
	then 1 else null end) as cntIllnesHome60
,count(case when vr.code='PROFYLACTIC' and (vwpt.code='HOME' or vwpt.code='HOMEACTIVE') 
	and (to_date(case when to_char(smo.dateStart,'dd.mm')='29.02' then '28.02' else to_char(smo.dateStart,'dd.mm') end ||'.'
	||(cast(to_char(smo.dateStart,'yyyy') as int)-18),'dd.mm.yyyy') < p.birthday) then 1 else null end) as cntProfHome17
,count(case when vr.code='PROFYLACTIC' and (vwpt.code='HOME' or vwpt.code='HOMEACTIVE') 
	and (to_date(case when to_char(smo.dateStart,'dd.mm')='29.02' then '28.02' else to_char(smo.dateStart,'dd.mm') end ||'.'
	||(cast(to_char(smo.dateStart,'yyyy') as int)-2),'dd.mm.yyyy') < p.birthday) then 1 else null end) as cntProfHome01
,count(case when (vss.code='OBLIGATORYINSURANCE') then 1 else null end) as cntOMC
,count(case when (vss.code='BUDGET') then 1 else null end) as cntBudget
,count(case when (vss.code='CHARGED') then 1 else null end) as cntCharged
,count(case when (vss.code='PRIVATEINSURANCE') then 1 else null end) as cntPrivateIns

FROM MedCase smo  
LEFT JOIN Patient p ON p.id=smo.patient_id 
LEFT JOIN Address2 ad1 on ad1.Id=p.address_Id 
LEFT JOIN Address2 ad2 on ad2.Id=ad1.parent_Id  
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
and smo.dateStart BETWEEN TO_DATE('${beginDate}','dd.mm.yyyy') and TO_DATE('${finishDate}','dd.mm.yyyy') 
and (smo.noActuality is null or smo.noActuality='0')  
${specialistSql} ${workFunctionSql} ${lpuSql} ${serviceStreamSql} ${workPlaceTypeSql} ${socialStatusSql}
${personSql}
GROUP BY ${groupGroup} ORDER BY ${groupOrder}
" guid="4a720225-8d94-4b47-bef3-4dbbe79eec74" nameFldSql="journal_ticket_sql" /> 
    <msh:sectionTitle>
    <form action="print-f039_cons.do" method="post" target="_blank">
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
         name="journal_ticket" action="visit_f039_list.do?typeReestr=1&typeView=${typeView}&typeGroup=${typeGroup}" idField="1" noDataMessage="Не найдено">
            <msh:tableColumn columnName="${groupName}" property="2"/>            
            <msh:tableColumn isCalcAmount="true" columnName="Кол-во посещ" property="3"/>
            <msh:tableColumn isCalcAmount="true" columnName="из них сельс.жител." property="4"/>
            <msh:tableColumn isCalcAmount="true" columnName="в том числе до 17 лет" property="5"/>
            <msh:tableColumn isCalcAmount="true" columnName="в том числе старше 60 лет" property="6"/>
            <msh:tableColumn isCalcAmount="true" columnName="По поводу конс" property="7"/>
            <msh:tableColumn isCalcAmount="true" columnName="По поводу забол" property="8"/>
            <msh:tableColumn isCalcAmount="true" columnName="в том числе до 17 лет" property="9"/>
            <msh:tableColumn isCalcAmount="true" columnName="в том числе старше 60 лет" property="10"/>
            <msh:tableColumn isCalcAmount="true" columnName="Профилак." property="11"/>
            <msh:tableColumn isCalcAmount="true" columnName="На дому" property="12"/>
            <msh:tableColumn isCalcAmount="true" columnName="На дому по забол." property="13"/>
            <msh:tableColumn isCalcAmount="true" columnName="в том числе до 17 лет" property="14"/>
            <msh:tableColumn isCalcAmount="true" columnName="0-1(вкл) лет" property="15"/>
            <msh:tableColumn isCalcAmount="true" columnName="в том числе старше 60 лет" property="16"/>
            <msh:tableColumn isCalcAmount="true" columnName="На дому проф до 17 лет" property="17"/>
            <msh:tableColumn isCalcAmount="true" columnName="0-1(вкл) лет" property="18"/>
            <msh:tableColumn isCalcAmount="true" columnName="ОМС" property="19"/>
            <msh:tableColumn isCalcAmount="true" columnName="бюджет" property="20"/>
            <msh:tableColumn isCalcAmount="true" columnName="платные" property="21"/>
            <msh:tableColumn isCalcAmount="true" columnName="ДМС" property="22"/>
        </msh:table>
    </msh:sectionContent>

    </msh:section>    	<%
    } else if (typeView!=null && (typeView.equals("3"))) {
    	%>
    <msh:section>
<ecom:webQuery name="journal_ticket" nativeSql="
select
''||${groupSqlId}||${workFunctionSqlId}||${specialistSqlId}||${lpuSqlId}||${serviceStreamSqlId}||${workPlaceTypeSqlId}||${socialStatusSqlId}||'&beginDate=${beginDate}&finishDate=${finishDate}' as name
,${groupSql} as nameFld

,count(*) as cntAll 
,count(case when 
		cast(to_char(smo.dateStart,'yyyy') as int)-cast(to_char(p.birthday,'yyyy') as int)
		+(case when (cast(to_char(smo.dateStart, 'mm') as int)-cast(to_char(p.birthday, 'mm') as int)
		+(case when (cast(to_char(smo.dateStart,'dd') as int) - cast(to_char(p.birthday,'dd') as int)<0) then -1 else 0 end)<0)
		then -1 else 0 end)<15
then 1 else null end) as cntAll14 
,count(case when (ad1.domen=5 or ad2.domen=5) and 
(
		cast(to_char(smo.dateStart,'yyyy') as int)-cast(to_char(p.birthday,'yyyy') as int)
		+(case when (cast(to_char(smo.dateStart, 'mm') as int)-cast(to_char(p.birthday, 'mm') as int)
		+(case when (cast(to_char(smo.dateStart,'dd') as int) - cast(to_char(p.birthday,'dd') as int)<0) then -1 else 0 end)<0)
		then -1 else 0 end)<15
) then 1 else null end) as cntAll14V 
,count(case when (
		cast(to_char(smo.dateStart,'yyyy') as int)-cast(to_char(p.birthday,'yyyy') as int)
		+(case when (cast(to_char(smo.dateStart, 'mm') as int)-cast(to_char(p.birthday, 'mm') as int)
		+(case when (cast(to_char(smo.dateStart,'dd') as int) - cast(to_char(p.birthday,'dd') as int)<0) then -1 else 0 end)<0)
		then -1 else 0 end) between 15 and 17
)	then 1 else null end) as cntAll17 
,count(case when (ad1.domen=5 or ad2.domen=5) and (
		cast(to_char(smo.dateStart,'yyyy') as int)-cast(to_char(p.birthday,'yyyy') as int)
		+(case when (cast(to_char(smo.dateStart, 'mm') as int)-cast(to_char(p.birthday, 'mm') as int)
		+(case when (cast(to_char(smo.dateStart,'dd') as int) - cast(to_char(p.birthday,'dd') as int)<0) then -1 else 0 end)<0)
		then -1 else 0 end) between 15 and 17
	)	then 1 else null end) as cntAll17V 
,count(case when (
		cast(to_char(smo.dateStart,'yyyy') as int)-cast(to_char(p.birthday,'yyyy') as int)
		+(case when (cast(to_char(smo.dateStart, 'mm') as int)-cast(to_char(p.birthday, 'mm') as int)
		+(case when (cast(to_char(smo.dateStart,'dd') as int) - cast(to_char(p.birthday,'dd') as int)<0) then -1 else 0 end)<0)
		then -1 else 0 end)>17
) then 1 else null end) as cntAllold 
,count(case when (ad1.domen=5 or ad2.domen=5) and (
		cast(to_char(smo.dateStart,'yyyy') as int)-cast(to_char(p.birthday,'yyyy') as int)
		+(case when (cast(to_char(smo.dateStart, 'mm') as int)-cast(to_char(p.birthday, 'mm') as int)
		+(case when (cast(to_char(smo.dateStart,'dd') as int) - cast(to_char(p.birthday,'dd') as int)<0) then -1 else 0 end)<0)
		then -1 else 0 end)>17
) then 1 else null end) as cntAlloldV 
,count(case when vr.code='ILLNESS' then 1 else null end) as cntIllness 
,count(case when vr.code='ILLNESS' and (
		cast(to_char(smo.dateStart,'yyyy') as int)-cast(to_char(p.birthday,'yyyy') as int)
		+(case when (cast(to_char(smo.dateStart, 'mm') as int)-cast(to_char(p.birthday, 'mm') as int)
		+(case when (cast(to_char(smo.dateStart,'dd') as int) - cast(to_char(p.birthday,'dd') as int)<0) then -1 else 0 end)<0)
		then -1 else 0 end) between 0 and 14
) then 1 else null end) as cntIllnes14
,count(case when vr.code='ILLNESS' and (
		cast(to_char(smo.dateStart,'yyyy') as int)-cast(to_char(p.birthday,'yyyy') as int)
		+(case when (cast(to_char(smo.dateStart, 'mm') as int)-cast(to_char(p.birthday, 'mm') as int)
		+(case when (cast(to_char(smo.dateStart,'dd') as int) - cast(to_char(p.birthday,'dd') as int)<0) then -1 else 0 end)<0)
		then -1 else 0 end) between 15 and 17
) then 1 else null end) as cntIllnes17 
,count(case when vr.code='ILLNESS' and (
		cast(to_char(smo.dateStart,'yyyy') as int)-cast(to_char(p.birthday,'yyyy') as int)
		+(case when (cast(to_char(smo.dateStart, 'mm') as int)-cast(to_char(p.birthday, 'mm') as int)
		+(case when (cast(to_char(smo.dateStart,'dd') as int) - cast(to_char(p.birthday,'dd') as int)<0) then -1 else 0 end)<0)
		then -1 else 0 end) >17 
)then 1 else null end) as cntIllnesold 
,count(case when vr.code='PROFYLACTIC' then 1 else null end) as cntProf 
,count(case when (vwpt.code='HOME' or vwpt.code='HOMEACTIVE') then 1 else null end) as cntHome 
,count(case when vr.code='ILLNESS' and (vwpt.code='HOME' or vwpt.code='HOMEACTIVE') then 1 else null end) as cntIllnesHome 
,count(case when vr.code='ILLNESS' and (vwpt.code='HOME' or vwpt.code='HOMEACTIVE') and (
		cast(to_char(smo.dateStart,'yyyy') as int)-cast(to_char(p.birthday,'yyyy') as int)
		+(case when (cast(to_char(smo.dateStart, 'mm') as int)-cast(to_char(p.birthday, 'mm') as int)
		+(case when (cast(to_char(smo.dateStart,'dd') as int) - cast(to_char(p.birthday,'dd') as int)<0) then -1 else 0 end)<0)
		then -1 else 0 end)<15
) then 1 else null end) as cntIllnesHome14 
,count(case when vr.code='ILLNESS' and (vwpt.code='HOME' or vwpt.code='HOMEACTIVE') and (
		cast(to_char(smo.dateStart,'yyyy') as int)-cast(to_char(p.birthday,'yyyy') as int)
		+(case when (cast(to_char(smo.dateStart, 'mm') as int)-cast(to_char(p.birthday, 'mm') as int)
		+(case when (cast(to_char(smo.dateStart,'dd') as int) - cast(to_char(p.birthday,'dd') as int)<0) then -1 else 0 end)<0)
		then -1 else 0 end) between 15 and 17
) then 1 else null end) as cntIllnesHome17 
,count(case when vr.code='ILLNESS' and (vwpt.code='HOME' or vwpt.code='HOMEACTIVE') and (
		cast(to_char(smo.dateStart,'yyyy') as int)-cast(to_char(p.birthday,'yyyy') as int)
		+(case when (cast(to_char(smo.dateStart, 'mm') as int)-cast(to_char(p.birthday, 'mm') as int)
		+(case when (cast(to_char(smo.dateStart,'dd') as int) - cast(to_char(p.birthday,'dd') as int)<0) then -1 else 0 end)<0)
		then -1 else 0 end)>17
) then 1 else null end) as cntIllnesHomeold 
,count(case when vr.code='PROFYLACTIC' and (vwpt.code='HOME' or vwpt.code='HOMEACTIVE') then 1 else null end) as cntProfHome 
,count(case when vr.code='PROFYLACTIC' and (vwpt.code='HOME' or vwpt.code='HOMEACTIVE') and (
		cast(to_char(smo.dateStart,'yyyy') as int)-cast(to_char(p.birthday,'yyyy') as int)
		+(case when (cast(to_char(smo.dateStart, 'mm') as int)-cast(to_char(p.birthday, 'mm') as int)
		+(case when (cast(to_char(smo.dateStart,'dd') as int) - cast(to_char(p.birthday,'dd') as int)<0) then -1 else 0 end)<0)
		then -1 else 0 end)<15
) then 1 else null end) as cntProfHome14 
,count(case when vr.code='PROFYLACTIC' and (vwpt.code='HOME' or vwpt.code='HOMEACTIVE') and (
		cast(to_char(smo.dateStart,'yyyy') as int)-cast(to_char(p.birthday,'yyyy') as int)
		+(case when (cast(to_char(smo.dateStart, 'mm') as int)-cast(to_char(p.birthday, 'mm') as int)
		+(case when (cast(to_char(smo.dateStart,'dd') as int) - cast(to_char(p.birthday,'dd') as int)<0) then -1 else 0 end)<0)
		then -1 else 0 end) between 15 and 17
) then 1 else null end) as cntProfHome17 
,count(case when vr.code='PROFYLACTIC' and (vwpt.code='HOME' or vwpt.code='HOMEACTIVE') and (
		cast(to_char(smo.dateStart,'yyyy') as int)-cast(to_char(p.birthday,'yyyy') as int)
		+(case when (cast(to_char(smo.dateStart, 'mm') as int)-cast(to_char(p.birthday, 'mm') as int)
		+(case when (cast(to_char(smo.dateStart,'dd') as int) - cast(to_char(p.birthday,'dd') as int)<0) then -1 else 0 end)<0)
		then -1 else 0 end)>17
) then 1 else null end) as cntProfHomeold
FROM MedCase smo  
LEFT JOIN Patient p ON p.id=smo.patient_id 
LEFT JOIN Address2 ad1 on ad1.Id=p.address_Id 
LEFT JOIN Address2 ad2 on ad2.Id=ad1.parent_Id  
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
and smo.dateStart BETWEEN TO_DATE('${beginDate}','dd.mm.yyyy') and TO_DATE('${finishDate}','dd.mm.yyyy') 
and (smo.noActuality is null or smo.noActuality='0')  
${specialistSql} ${workFunctionSql} ${lpuSql} ${serviceStreamSql} ${workPlaceTypeSql} ${socialStatusSql}
${personSql}
GROUP BY ${groupGroup} ORDER BY ${groupOrder}
" guid="4a720225-8d94-4b47-bef3-4dbbe79eec74" nameFldSql="journal_ticket_sql"/> 
    <msh:sectionTitle>
    <form action="print-f039_bis.do" method="post" target="_blank">
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
         name="journal_ticket" action="visit_f039_list.do?typeReestr=1&typeView=${typeView}&typeGroup=${typeGroup}" idField="1" noDataMessage="Не найдено">
            <msh:tableColumn columnName="${groupByTitle}" property="2"/>            
            <msh:tableColumn isCalcAmount="true" columnName="Кол-во посещ" property="3"/>
            <msh:tableColumn isCalcAmount="true" columnName="Кол-во посещ" property="3"/>
            <msh:tableColumn isCalcAmount="true" columnName="0-14 лет" property="4"/>
            <msh:tableColumn isCalcAmount="true" columnName="0-14 лет сел." property="5"/>
            <msh:tableColumn isCalcAmount="true" columnName="15-17 лет" property="6"/>
            <msh:tableColumn isCalcAmount="true" columnName="15-17 лет сел." property="22"/>
            <msh:tableColumn isCalcAmount="true" columnName="взрослые" property="7"/>
            <msh:tableColumn isCalcAmount="true" columnName="взрослые сел." property="8"/>
            <msh:tableColumn isCalcAmount="true" columnName="по поводу заболевания" property="9"/>
            <msh:tableColumn isCalcAmount="true" columnName="заб. 0-14" property="10"/>
            <msh:tableColumn isCalcAmount="true" columnName="заб. 15-17" property="11"/>
            <msh:tableColumn isCalcAmount="true" columnName="заб. взр." property="12"/>
            <msh:tableColumn isCalcAmount="true" columnName="профил." property="13"/>
            <msh:tableColumn isCalcAmount="true" columnName="посещ. на дому" property="14"/>
        </msh:table>
    </msh:sectionContent>

    </msh:section>    	<%
    }
    }
    		} else {%>
    	<i>Выберите параметры поиска и нажмите "Найти" </i>
    	<% }   %>
  </tiles:put>
  <tiles:put name="javascript" type="string">
  	<script type="text/javascript">

    checkFieldUpdate('typeGroup','${typeGroup}',1) ;
    checkFieldUpdate('typeView','${typeView}',1) ;
    checkFieldUpdate('typeDtype','${typeDtype}',3) ;
    
    
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