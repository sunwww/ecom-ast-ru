<%@page import="ru.ecom.mis.ejb.service.patient.HospitalLibrary"%>
<%@page import="ru.ecom.mis.web.action.util.ActionUtil"%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://struts.apache.org/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://www.nuzmsh.ru/tags/msh" prefix="msh" %>
<%@ taglib uri="http://www.ecom-ast.ru/tags/ecom" prefix="ecom" %>
<%@ taglib tagdir="/WEB-INF/tags" prefix="tags" %>

<%@page import="ru.ecom.poly.web.action.ticket.JournalBySpecialistForm"%>
<tiles:insert page="/WEB-INF/tiles/mainLayout.jsp" flush="true" >

    <tiles:put name='title' type='string'>
        <msh:title mainMenu="Report">Просмотр отчета по гражданству </msh:title>
    </tiles:put>

    <tiles:put name='side' type='string'>
    </tiles:put>
    
  <tiles:put name="body" type="string">
  <%
	String typeEmergency =ActionUtil.updateParameter("Report_nationality","typeEmergency","3", request) ;
	String typePatient =ActionUtil.updateParameter("Report_nationality","typePatient","1", request) ;
	String typeGroup =ActionUtil.updateParameter("Report_nationality","typeGroup","1", request) ;
	String typeView =ActionUtil.updateParameter("Report_nationality","typeView","2", request) ;

  %>
    <msh:form action="/journal_nationality_smo.do" defaultField="beginDate" disableFormDataConfirm="true" method="GET" guid="d7b31bc2-38f0-42cc-8d6d-19395273168f">
    <input type="hidden" name="m" id="m" value="categoryForeignNationals"/>
    <input type="hidden" name="s" id="s" value="VisitPrintService"/>
    <input type="hidden" name="id" id="id"/>
    <msh:panel>
      <msh:row guid="53627d05-8914-48a0-b2ec-792eba5b07d9">
        <msh:separator label="Параметры поиска" colSpan="7" guid="15c6c628-8aab-4c82-b3d8-ac77b7b3f700" />
      </msh:row>
        <msh:row styleId="noswod">
	        <td class="label" title="Поиск по показаниям поступления (typeEmergency)" colspan="1"><label for="typeEmergencyName" id="typeEmergencyLabel">Показания:</label></td>
	        <td onclick="this.childNodes[1].checked='checked';">
	        	<input type="radio" name="typeEmergency" value="1">  экстренные
	        </td>
	        <td onclick="this.childNodes[1].checked='checked';">
	        	<input type="radio" name="typeEmergency" value="2"  >  плановые
	        </td>
	        <td onclick="this.childNodes[1].checked='checked';">
	        	<input type="radio" name="typeEmergency" value="3">  все
	        </td>
        </msh:row>
      <msh:row>
        <td class="label" title="Поиск по пациентам (typeGroup)" colspan="1"><label for="typeGroupName" id="typeGroupLabel">Группировать:</label></td>
        <td onclick="this.childNodes[1].checked='checked';">
        	<input type="radio" name="typeGroup" value="1">  по отделениям
        </td>
        <td onclick="this.childNodes[1].checked='checked';">
        	<input type="radio" name="typeGroup" value="2">  по гражданству
        </td>
        <td onclick="this.childNodes[1].checked='checked';">
        	<input type="radio" name="typeGroup" value="3">  по потоку обслуживания
        </td>
      </msh:row>
      <msh:row>
        <td class="label" title="Отображать (typeView)" colspan="1"><label for="typeGroupName" id="typeGroupLabel">Отображать:</label></td>
        <td onclick="this.childNodes[1].checked='checked';">
        	<input type="radio" name="typeView" value="1">  реестр
        </td>
        <td onclick="this.childNodes[1].checked='checked';">
        	<input type="radio" name="typeView" value="2">  свод
        </td>
      </msh:row>
      <msh:row guid="7d80be13-710c-46b8-8503-ce0413686b69">
        <td class="label" title="Поиск по пациентам (typePatient)" colspan="1"><label for="typePatientName" id="typePatientLabel">Пациенты:</label></td>
        <td onclick="this.childNodes[1].checked='checked';">
        	<input type="radio" name="typePatient" value="1">  иностранцы
        </td>
        <td onclick="this.childNodes[1].checked='checked';">
        	<input type="radio" name="typePatient" value="2">  все
        </td>
      </msh:row>
       <msh:row>
       	<msh:autoComplete property="department" fieldColSpan="4"
       	label="Отделение" horizontalFill="true" vocName="lpu"/>
       </msh:row>
        <msh:row>
        	<msh:autoComplete property="nationality" fieldColSpan="4"
        	label="Гражданство" horizontalFill="true" vocName="omcOksm"/>
        </msh:row>
        <msh:row>
        	<msh:autoComplete property="serviceStream" fieldColSpan="4"
        	label="Поток облуживания" horizontalFill="true" vocName="vocServiceStream"/>
        </msh:row>
      <msh:row>
        	<msh:textField property="beginDate"  label="Период с" guid="8d7ef035-1273-4839-a4d8-1551c623caf1" />
        	<msh:textField property="finishDate" fieldColSpan="7" label="по" guid="f54568f6-b5b8-4d48-a045-ba7b9f875245" />
        </msh:row>
        <msh:row>
        <td colspan="3" class="buttons">
			<input type="button" title="Найти" onclick="this.value=&quot;Поиск...&quot;;  this.form.action=&quot;journal_nationality_smo.do&quot;;this.form.target=&quot;&quot; ; this.form.submit(); return true ;" value="Найти" class="default" id="submitButton" autocomplete="off">
 		</td>
        
        </msh:row>

    </msh:panel>
    </msh:form>
    
    <%
    	if (request.getParameter("beginDate")!=null && request.getParameter("finishDate")!=null
    	 || request.getParameter("id")!=null && !request.getParameter("id").equals("")
    	) {
    		
        	if (typeEmergency!=null && typeEmergency.equals("1")) {
        		request.setAttribute("emergencySql", " and m.emergency='1' ") ;
        		request.setAttribute("emergencyInfo", ", поступивших по экстренным показаниям") ;
        		request.setAttribute("emergencyTicketSql", " and t.emergency='1' ") ;
        	} else if (typeEmergency!=null && typeEmergency.equals("2")) {
        		request.setAttribute("emergencySql", " and (m.emergency is null or m.emergency='0') ") ;
        		request.setAttribute("emergencyInfo", ", поступивших по плановым показаниям") ;
        		request.setAttribute("emergencyTicketSql", " and (t.emergency is null or t.emergency='0') ") ;
        	} 
        	if (typePatient.equals("1")){
    			//aRequest.setAttribute("add", "and $$isForeignPatient^ZExpCheck(m.patient_id,m.dateStart)=0") ;
    			request.setAttribute("patientSql", HospitalLibrary.getSqlGringo(true, "vn")) ;
    			request.setAttribute("infoTypePat", "Поиск по иностранцам") ;
    		} else {
    			request.setAttribute("patientSql", "") ;
    			request.setAttribute("infoTypePat", "Поиск по всем") ;
    		}
        	
        	if (typeGroup!=null&&typeGroup.equals("1")) {
    			request.setAttribute("groupSql", "coalesce(mlV.name,ml.name)") ;
    			request.setAttribute("groupSqlId", "coalesce(mlV.id,ml.id)") ;
    			request.setAttribute("groupId", "'&department='||coalesce(mlV.id,ml.id)") ;
    			request.setAttribute("groupName", "Наименование отделения") ;
    			request.setAttribute("group1Sql", "ml.name") ;
    			request.setAttribute("group1SqlId", "ml.id") ;
    			request.setAttribute("group1Id", "'&department='||ml.id") ;
        	} else if (typeGroup!=null&&typeGroup.equals("3")) {
    			request.setAttribute("groupSql", "vss.name") ;
    			request.setAttribute("groupSqlId", "vss.id") ;
    			request.setAttribute("groupId", "'&serviceStream='||vss.id") ;
    			request.setAttribute("groupName", "Поток обслуживания") ;
    			request.setAttribute("group1Sql", "vss.name") ;
    			request.setAttribute("group1SqlId", "vss.id") ;
    			request.setAttribute("group1Id", "'&serviceStream='||vss.id") ;
        	} else {
    			request.setAttribute("groupSql", "vn.name") ;
    			request.setAttribute("groupSqlId", "coalesce(p.nationality_id,0)") ;
    			request.setAttribute("groupId", "'&nationality='||coalesce(p.nationality_id,0)") ;
    			request.setAttribute("group1Sql", "vn.name") ;
    			request.setAttribute("group1SqlId", "coalesce(p.nationality_id,0)") ;
    			request.setAttribute("group1Id", "'&nationality='||coalesce(p.nationality_id,0)") ;
    			request.setAttribute("groupName", "Гражданство") ;
        	}
        	ActionUtil.setParameterFilterSql("serviceStream","vss.id", request) ;
        	ActionUtil.setParameterFilterSql("department","m.department_id", request) ;
        	ActionUtil.setParameterFilterSql("department","departmentD","m.department_id", request) ;
        	ActionUtil.setParameterFilterSql("department","departmentWF","we.lpu_id", request) ;
        	ActionUtil.setParameterFilterSql("nationality","p.nationality_id", request) ;
    		%>
    <% 
    //начало реестра
    if (typeView.equals("1")) {%>


  	<msh:section title="Поликлиника">

  	
	    <ecom:webQuery name="list_yes" maxResult="1000" nativeSql="select m.id
	    
	    ,to_char(m.dateStart,'DD.MM.YYYY')||' '||cast(m.timeExecute as varchar(5)) as dateStart

	    ,p.lastname||' '||p.firstname||' '||p.middlename||' г.р.'||to_char(p.birthday,'DD.MM.YYYY') as pfio
	    ,vwfe.name||' '||pe.lastname as pefio

from medcase m 
left join patient p on p.id=m.patient_id
left join Omc_Oksm vn on vn.id=p.nationality_id
left join WorkFunction wfe on wfe.id=m.workFunctionExecute_id
left join Worker we on we.id=wfe.worker_id
left join MisLpu ml on ml.id=we.lpu_id
left join Patient pe on pe.id=we.person_id
left join VocWorkFunction vwfe on vwfe.id=wfe.workFunction_id
left join VocVisitResult vvr on vvr.id=m.visitResult_id
left join VocServiceStream vss on vss.id=m.serviceStream_id
where  m.dateStart between to_date('${param.beginDate}','dd.mm.yyyy') and to_date('${param.finishDate}','dd.mm.yyyy')
and m.DTYPE='Visit' 
and (m.noActuality is null or m.noActuality='0')
${emergencySql} ${departmentWFSql}
${serviceStreamSql}
 ${nationalitySql} ${patientSql}
order by p.lastname,p.firstname,p.middlename"/>
<msh:table viewUrl="entityShortView-smo_visit.do" editUrl="entityParentEdit-smo_visit.do" name="list_yes" action="entityView-smo_visit.do" idField="1" guid="b621e361-1e0b-4ebd-9f58-b7d919b45bd6">
	      <msh:tableColumn columnName="№" identificator="false" property="sn" guid="270ae0dc-e1c6-45c5-b8b8-26d034ec3878" />
	      <msh:tableColumn columnName="Пациент" property="3" guid="315cb6eb-3db8-4de5-8b0c-a49e3cacf382" />
	      <msh:tableColumn columnName="Дата" identificator="false" property="2" guid="b3e2fb6e-53b6-4e69-8427-2534cf1edcca" />
	      <msh:tableColumn columnName="Исполнитель" identificator="false" property="4" guid="3145e72a-cce5-4994-a507-b1a81efefdfe" />
	    </msh:table>
  	</msh:section>
  	<msh:section title="Стационар">

  	
	    <ecom:webQuery name="list_stac" maxResult="1000" nativeSql="select smo.id
	    
	    ,to_char(smo.dateStart,'DD.MM.YYYY') as dateStart
	    ,to_char(smo.dateFinish,'DD.MM.YYYY') as dateFinish
	    ,p.lastname||' '||p.firstname||' '||p.middlename||' г.р.'||to_char(p.birthday,'DD.MM.YYYY') as pfio
	    ,ss.code as sscode 
	    ,ml.name as mlname,vss.name as vssname
from medcase m 
left join medcase smo on smo.id=m.parent_id
left join patient p on p.id=m.patient_id
left join Omc_Oksm vn on vn.id=p.nationality_id
left join statisticstub ss on ss.id=smo.statisticStub_id
left join mislpu ml on ml.id=m.department_id
left join VocServiceStream vss on vss.id=smo.serviceStream_id
where  
m.DTYPE='DepartmentMedCase' and m.dateFinish between to_date('${param.beginDate}','dd.mm.yyyy') and to_date('${param.finishDate}','dd.mm.yyyy')
and (m.noActuality is null or m.noActuality='0')
and smo.deniedHospitalizating_id is null
${emergencySql} ${departmentSql} 
${serviceStreamSql}
${nationalitySql} ${patientSql}
order by p.lastname,p.firstname,p.middlename"/>
<msh:table viewUrl="entityShortView-stac_ssl.do" 
 name="list_stac"
 action="entityView-stac_ssl.do" idField="1" >
	      <msh:tableColumn columnName="№" identificator="false" property="sn" />
	      <msh:tableColumn columnName="Номер стат.карты" property="5" />
	      <msh:tableColumn columnName="Пациент" property="4" />
	      <msh:tableColumn columnName="Дата поступления" property="2" />
	      <msh:tableColumn columnName="Дата выписки" identificator="false" property="3" />
	      <msh:tableColumn property="6" columnName="Отделение"/>
	      <msh:tableColumn property="7" columnName="Поток обслуживания"/>
	    </msh:table>
  	</msh:section>
  	<msh:section title="Отказы от госпитализаций">

  	
	    <ecom:webQuery name="list_stac1" maxResult="1000" nativeSql="select m.id
	    
	    ,to_char(m.dateStart,'DD.MM.YYYY') as dateStart
	    ,to_char(m.dateFinish,'DD.MM.YYYY') as dateFinish
	    ,p.lastname||' '||p.firstname||' '||p.middlename||' г.р.'||to_char(p.birthday,'DD.MM.YYYY') as pfio
	    ,ss.code as sscode 

from medcase m 
left join patient p on p.id=m.patient_id
left join Omc_Oksm vn on vn.id=p.nationality_id
left join statisticstub ss on ss.id=m.statisticStub_id
left join MisLpu ml on ml.id=m.department_id
left join VocServiceStream vss on vss.id=m.serviceStream_id
where  m.DTYPE='HospitalMedCase'
and m.dateStart between to_date('${param.beginDate}','dd.mm.yyyy') and to_date('${param.finishDate}','dd.mm.yyyy')
and (m.noActuality is null or m.noActuality='0')
and m.deniedHospitalizating_id is not null
${emergencySql} ${departmentSql} 
${serviceStreamSql}
${nationalitySql} ${patientSql}
order by p.lastname,p.firstname,p.middlename"/>
<msh:table viewUrl="entityShortView-stac_ssl.do" 
 name="list_stac1"
 action="entityView-stac_ssl.do" idField="1" >
	      <msh:tableColumn columnName="№" identificator="false" property="sn" />
	      <msh:tableColumn columnName="Пациент" property="4" />
	      <msh:tableColumn columnName="Дата обращения" property="2" />
	    </msh:table>
  	</msh:section>  	
  	<msh:section title="Талоны">

  	
	    <ecom:webQuery name="list_ticket" maxResult="1000" nativeSql="select m.id
	    
	    ,to_char(m.date,'DD.MM.YYYY') as dateStart

	   
	    ,p.lastname||' '||p.firstname||' '||p.middlename||' г.р.'||to_char(p.birthday,'DD.MM.YYYY') as pfio
	    ,vwfe.name||' '||pe.lastname as pefio
	

from ticket m 
left join medcard mc on mc.id=m.medcard_id
left join patient p on p.id=mc.person_id
left join Omc_Oksm vn on vn.id=p.nationality_id
left join WorkFunction wfe on wfe.id=m.workFunction_id
left join Worker we on we.id=wfe.worker_id
left join MisLpu ml on ml.id=we.lpu_id
left join Patient pe on pe.id=we.person_id
left join VocWorkFunction vwfe on vwfe.id=wfe.workFunction_id
left join VocServiceStream vss on vss.id=m.vocPaymentType_id

where  m.date between to_date('${param.beginDate}','dd.mm.yyyy') and to_date('${param.finishDate}','dd.mm.yyyy')
and m.status='2'
${emergencySql} ${departmentWFSql} ${serviceStreamSql} ${nationalitySql} ${patientSql}
order by p.lastname,p.firstname,p.middlename"/>
<msh:table viewUrl="entityShortView-poly_ticket.do" 
editUrl="entityParentEdit-poly_ticket.do" 
name="list_ticket" action="entityView-poly_ticket.do" 
idField="1" guid="b621e361-1e0b-4ebd-9f58-b7d919b45bd6" >
	      <msh:tableColumn columnName="№" identificator="false" property="sn" guid="270ae0dc-e1c6-45c5-b8b8-26d034ec3878" />
	      <msh:tableColumn columnName="Пациент" property="3" guid="315cb6eb-3db8-4de5-8b0c-a49e3cacf382" />
	      <msh:tableColumn columnName="Дата" identificator="false" property="2" guid="b3e2fb6e-53b6-4e69-8427-2534cf1edcca" />
	      <msh:tableColumn columnName="Исполнитель" identificator="false" property="4" guid="3145e72a-cce5-4994-a507-b1a81efefdfe" />
	    </msh:table>
  	</msh:section>



    <% 
    //окончание реестра
    } else {
    	// начало свода
    	%>
    	
    <msh:section>
<ecom:webQuery nameFldSql="sql_journal_swod" name="journal_swod" nativeSql="
select ${groupId}||${departmentSqlId}||${nationalitySqlId}||${serviceStreamSqlId} as idparam,${groupSql} as vnname
,count(*) as cntAll
,count(distinct case when m.dtype='Visit' then m.id else null end) as polic
,count(distinct case when m.dtype='Visit' and vss.code='CHARGED' then m.id else null end) as policCh
,count(distinct case when m.dtype='DepartmentMedCase' and (m.hospType_id is null or vht.code='ALLTIMEHOSP') then m.id else null end) as hospitAll
,sum(case when m.dtype='DepartmentMedCase' and (m.hospType_id is null or vht.code='ALLTIMEHOSP') then case when smo.dateFinish=smo.dateStart then 1 else smo.dateFinish-smo.dateStart end else null end) as hospitDaysAll
,count(distinct case when m.dtype='DepartmentMedCase' and (m.hospType_id is null or vht.code='ALLTIMEHOSP') and vss.code='CHARGED' then m.id else null end) as hospitAllCh
,sum( case when m.dtype='DepartmentMedCase' and (m.hospType_id is null or vht.code='ALLTIMEHOSP') and vss.code='CHARGED' then case when smo.dateFinish=smo.dateStart then 1 else smo.dateFinish-smo.dateStart end else null end) as hospitAllDaysCh
,count(distinct case when m.dtype='DepartmentMedCase' 
and (m.hospType_id is null or vht.code='ALLTIMEHOSP') and vss.code!='CHARGED' then m.id else null end) as cntNoChan
,list(distinct case when m.dtype='DepartmentMedCase'  
and (m.hospType_id is null or vht.code='ALLTIMEHOSP') and vss.code!='CHARGED' then vss.name else null end) as listVssNoChan
,case when 
count(distinct case when m.dtype='DepartmentMedCase' 
and (m.hospType_id is null or vht.code='ALLTIMEHOSP') and vss.code!='CHARGED' then m.id else null end)>0
then 
cast(round(1.0*sum(case when m.dtype='DepartmentMedCase' 
and (m.hospType_id is null or vht.code='ALLTIMEHOSP') and vss.code!='CHARGED' then smo.dateFinish-smo.dateStart+1 else null end)
/
count(distinct case when m.dtype='DepartmentMedCase'  
and (m.hospType_id is null or vht.code='ALLTIMEHOSP') and vss.code!='CHARGED' then m.id else null end)
,2) as numeric)
else null 
end as srDaysNoCh
,count(distinct case when m.dtype='DepartmentMedCase' and vht.code='DAYTIMEHOSP' then m.id else null end) as hospitDn
,sum(case when m.dtype='DepartmentMedCase' and vht.code='DAYTIMEHOSP' then smo.dateFinish-smo.dateStart+1 else null end) as hospitDaysDn
,count(distinct case when m.dtype='DepartmentMedCase' and vht.code='DAYTIMEHOSP'and vss.code='CHARGED' then m.id else null end) as hospitDnCh
,sum(case when m.dtype='DepartmentMedCase' and vht.code='DAYTIMEHOSP'and vss.code='CHARGED' then smo.dateFinish-smo.dateStart+1 else null end) as hospitDnDaysCh
,count(distinct case when m.dtype='HospitalMedCase' and m.deniedHospitalizating_id is not null then m.id else null end) as hospitDenied
from medcase m
left join medcase smo on smo.id=m.parent_id
left join patient p on p.id=m.patient_id
left join Omc_Oksm vn on vn.id=p.nationality_id
left join VocHospType vht on vht.id=m.hospType_id
left join VocServiceStream vss on vss.id=m.serviceStream_id

left join WorkFunction wf on wf.id=m.workFunctionExecute_id and m.dtype='Visit'
left join Worker w on w.id=wf.worker_id
left join MisLpu mlV on mlV.id=w.lpu_id
left join MisLpu ml on ml.id=m.department_id

where (m.dtype='Visit' 
and m.dateStart between to_date('${param.beginDate}','dd.mm.yyyy') and to_date('${param.finishDate}','dd.mm.yyyy')

or m.dtype='DepartmentMedCase'
and m.dateFinish between to_date('${param.beginDate}','dd.mm.yyyy') and to_date('${param.finishDate}','dd.mm.yyyy')

or m.dtype='HospitalMedCase' and m.deniedHospitalizating_id is not null
and m.dateStart between to_date('${param.beginDate}','dd.mm.yyyy') and to_date('${param.finishDate}','dd.mm.yyyy')
) 
and (m.noActuality is null or m.noActuality='0') ${emergencySql}
${departmentSql}  ${serviceStreamSql} ${patientSql} ${nationalitySql} 
group by ${groupSqlId},${groupSql}

" guid="4a720225-8d94-4b47-bef3-4dbbe79eec74" /> 
<ecom:webQuery name="journal_swod1" nameFldSql="sql_journal_swod1" nativeSql="
select ${group1Id}||${departmentSqlId}||${nationalitySqlId}||${serviceStreamSqlId} as idparam,${group1Sql} as vnname
,count(distinct m.id) as polic
,count(distinct case when vss.code='CHARGED' then m.id else null end) as policCh
from Ticket m
left join WorkFunction wfe on wfe.id=m.workFunction_id
left join Worker we on we.id=wfe.worker_id
left join MisLpu ml on ml.id=we.lpu_id
left join MedCard mc on mc.id=m.medCard_id
left join patient p on p.id=mc.person_id
left join Omc_Oksm vn on vn.id=p.nationality_id
left join VocServiceStream vss on vss.id=m.vocPaymentType_id
where m.date between to_date('${param.beginDate}','dd.mm.yyyy') and to_date('${param.finishDate}','dd.mm.yyyy')
${emergencySql} ${department1Sql}  ${serviceStreamSql}
${patientSql}
 ${nationalitySql}
group by ${group1SqlId},${group1Sql}

" guid="4a720225-8d94-4b47-bef3-4dbbe79eec74" /> 

    <msh:sectionTitle>Период с ${param.beginDate} по ${param.finishDate}${emergencyInfo}
    <form action="print-report_categoryForeignNationals.do" method="post" target="_blank">
    <input type='hidden' name="sqlText1" id="sqlText1" value="${sql_journal_swod}"> 
    <input type='hidden' name="sqlText2" id="sqlText2" value="${sql_journal_swod1}">
    <input type='hidden' name="sqlCount" id="sqlCount" value="2">
    <input type='hidden' name="sqlInfo1" id="sqlInfo1" value="${param.beginDate}-${param.finishDate}${emergencyInfo}.">
    <input type='hidden' name="sqlInfo2" id="sqlInfo2" value="${groupName}">
    <input type='hidden' name="s" id="s" value="PrintService">
    <input type='hidden' name="m" id="m" value="printManyNativeQuery">
    <input type="submit" value="Печать"> 
    </form>
    </msh:sectionTitle>
    <msh:sectionContent>
        <msh:table
         name="journal_swod" action="journal_nationality_smo.do?beginDate=${param.beginDate}&finishDate=${param.finishDate}&typeView=1&typeGroup=${typeGroup}&typePatient=${typePatient}&typeEmergency=${typeEmergency}" idField="1" noDataMessage="Не найдено">
            <msh:tableNotEmpty>
              <tr>
                <th colspan="1" />
                <th colspan="1" />
                <th colspan="1" />
                <th colspan="2" class="rightBold">Амбулаторно-поликлиническая помощь</th>
                <th colspan="7" class="rightBold">Стационарная медицинская помощь</th>
                <th colspan="4" class="rightBold">Стационарно-замещающая медицинская помощь</th>
                <th colspan="1" />
              </tr>
            </msh:tableNotEmpty>            
            <msh:tableColumn columnName="#" property="sn"/>
            <msh:tableColumn columnName="${groupName}" property="2"/>            
            <msh:tableColumn columnName="Общее кол-во" property="3" isCalcAmount="true"/>
            <msh:tableColumn columnName="всего" property="4" isCalcAmount="true"/>
            <msh:tableColumn columnName="в т.ч. платно" property="5" isCalcAmount="true"/>
            <msh:tableColumn columnName="всего" property="6" isCalcAmount="true"/>
            <msh:tableColumn columnName="к.дней" property="7" isCalcAmount="true"/>
            <msh:tableColumn columnName="в т.ч. платно" property="8" isCalcAmount="true"/>
            <msh:tableColumn columnName="к.дней" property="9" isCalcAmount="true"/>
            <msh:tableColumn columnName="в т.ч. др. потоки" property="10" isCalcAmount="true"/>
            <msh:tableColumn columnName="потоки обс." property="11"/>
            <msh:tableColumn columnName="сред. к.дней" property="12"/>
            <msh:tableColumn columnName="всего" property="13" isCalcAmount="true"/>
            <msh:tableColumn columnName="к.дней" property="14" isCalcAmount="true"/>
            <msh:tableColumn columnName="в т.ч. платно" property="15" isCalcAmount="true"/>
            <msh:tableColumn columnName="к.дней" property="16" isCalcAmount="true"/>
            <msh:tableColumn columnName="отказы от госп." property="17" isCalcAmount="true"/>
        </msh:table>
    </msh:sectionContent>
    <msh:sectionTitle>Данные по талонной версии (посещения)</msh:sectionTitle>
<msh:sectionContent>
        <msh:table
         name="journal_swod1" action="journal_nationality_smo.do?typeView=1&typeGroup=${typeGroup}&typePatient=${typePatient}&typeEmergency=${typeEmergency}" idField="1" noDataMessage="Не найдено">
            <msh:tableNotEmpty guid="a6284e48-9209-412d-8436-c1e8e37eb8aa">
              <tr>
                <th colspan="1" />
                <th colspan="1" />
                <th colspan="2" class="rightBold">Амбулаторно-поликлиническая помощь</th>
              </tr>
            </msh:tableNotEmpty>            
            <msh:tableColumn columnName="#" property="sn"/>
            <msh:tableColumn columnName="${groupName}" property="2"/>            
            <msh:tableColumn columnName="всего" property="3" isCalcAmount="true"/>
            <msh:tableColumn columnName="в т.ч. платно" property="4" isCalcAmount="true"/>
        </msh:table>
    </msh:sectionContent>
    </msh:section>    	
    	<%
    	//окончание свода
    }
    	} else {%>
    	<i>Выберите параметры поиска и нажмите "Найти" </i>
    	<% }   %>
  </tiles:put>
  <tiles:put name="javascript" type="string">
  	<script type="text/javascript">
  	checkFieldUpdate('typeView','${typeView}',2) ;
  	checkFieldUpdate('typeGroup','${typeGroup}',2) ;
  	//checkFieldUpdate('typeDate','${typeDate}',2) ;
  	//checkFieldUpdate('typeUser','${typeUser}',3) ;
    checkFieldUpdate('typePatient','${typePatient}',1) ;
    checkFieldUpdate('typeEmergency','${typeEmergency}',4) ;

  	function checkFieldUpdate(aField,aValue,aDefault) {
  		aValue=+aValue ;
    	eval('var chk =  document.forms[0].'+aField) ;
    	max = chk.length ;
    	if (aValue<1) aValue=+aDefault ;
    	if (aValue>max) {
    		if (aDefault>max) {
    			chk[max-1].checked='checked' ;
    		} else {
    			chk[aDefault-1].checked='checked' ;
    		}
    	} else {
    		chk[aValue-1].checked='checked' ;
    	}
    }
    
  	function getId(aBis) {
		 
		
	}
  	function getCheckedValue(radioGrp) {
  		var radioValue ;
  		for(i=0; i < radioGrp.length; i++){
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