<%@page import="ru.ecom.web.util.ActionUtil"%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://struts.apache.org/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://www.nuzmsh.ru/tags/msh" prefix="msh" %>
<%@ taglib uri="http://www.ecom-ast.ru/tags/ecom" prefix="ecom" %>
<%@ taglib tagdir="/WEB-INF/tags" prefix="tags" %>

<%@page import="ru.ecom.mis.web.action.medcase.journal.RepeatCaseJournalForm"%>
<%@page import="java.text.SimpleDateFormat"%>
<tiles:insert page="/WEB-INF/tiles/mainLayout.jsp" flush="true" >

    <tiles:put name='title' type='string'>
        <msh:title mainMenu="Journals">Просмотр повторных случаев медицинского обслуживания</msh:title>
    </tiles:put>

    <tiles:put name='side' type='string'>
        <tags:stac_journal currentAction="stac_journalRepeatCaseByHospital"/>
    </tiles:put>
    
  <tiles:put name="body" type="string">
  <% 
  if (request.getParameter("short")==null) {
	  ActionUtil.updateParameter("SurgicalOperation","typeView","1", request) ;
	  ActionUtil.updateParameter("SurgicalOperation","typeOrder","1", request) ;
	  ActionUtil.updateParameter("SurgicalOperation","typeEmergency","3", request) ;
  }
  %>
    <msh:form action="/stac_journalRepeatCaseByHospital_list.do" defaultField="dateBegin" >
    <msh:panel>
      <msh:row>
        <msh:separator label="Параметры поиска" colSpan="7" />
      </msh:row>
        <msh:row>
	        <td class="label" title="Представление (typeView)" colspan="1"><label for="typeViewName" id="typeViewLabel">Отобразить:</label></td>
	        <td onclick="this.childNodes[1].checked='checked';">
	        	<input type="radio" name="typeView" value="1">  по обращениям в стационар
	        </td>
	        <td onclick="this.childNodes[1].checked='checked';">
	        	<input type="radio" name="typeView" value="2">  по отказам в госпитализации
	        </td>
	        <td onclick="this.childNodes[1].checked='checked';">
	        	<input type="radio" name="typeView" value="3">  по госпитализациям
	        </td>
	    </msh:row>
	    <msh:row>
	        <td></td>
	        <td onclick="this.childNodes[1].checked='checked';" colspan="2">
	        	<input type="radio" name="typeView" value="4">  по дате приема в поликлинике
	        </td>
	        <td onclick="this.childNodes[1].checked='checked';">
	        	<input type="radio" name="typeView" value="5">  по обращениям к специалистам
	        </td>
        </msh:row>
        <msh:row>
	        <td class="label" title="Поиск по показаниям (typeEmergency)" colspan="1"><label for="typeEmergencyName" id="typeEmergencyLabel">Показания:</label></td>
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
	        <msh:textField property="dateBegin" label="Период с" />
	        <msh:textField property="dateEnd" label="по" />
        </msh:row>
        <msh:row>
        	<msh:textField property="cnt" label="искать более" />
        	<td>случаев</td>
        </msh:row>
        <msh:row>
        	<msh:autoComplete property="department" fieldColSpan="4"
        	label="Отделение" horizontalFill="true" vocName="lpu"/>
        </msh:row>
        <msh:row>
        	<msh:autoComplete property="serviceStream" fieldColSpan="4" label="Поток обслуживания" horizontalFill="true" vocName="vocServiceStream"/>
        </msh:row>
        <msh:row>
        	<msh:submitCancelButtonsRow colSpan="3" labelCreate="Найти" labelSave="Найти" labelCreating="Поиск..." labelSaving="Поиск..."/>
      	</msh:row>
    </msh:panel>
    </msh:form>
    <script type='text/javascript'>
    
<%--    checkFieldUpdate('typeDate','${typeDate}',1) ;
     checkFieldUpdate('typePatient','${typePatient}',3) ;
   ;
    checkFieldUpdate('typeHour','${typeHour}',3) ;--%>
    checkFieldUpdate('typeView','${typeView}',1) ;
    checkFieldUpdate('typeEmergency','${typeEmergency}',1) ;
<%--    checkFieldUpdate('typeOrder','${typeOrder}',1) ;--%>
    function checkFieldUpdate(aField,aValue,aDefaultValue) {
       	eval('var chk =  document.forms[0].'+aField) ;
       	var aMax=chk.length ;
       	aValue=+aValue ;
       	//alert(aField+" "+aValue+" "+aMax+" "+chk) ;
       	if (aValue==0 || aValue>aMax) {
       		chk[+aDefaultValue-1].checked='checked' ;
       	} else {
       		chk[+aValue-1].checked='checked' ;
       	}
       }
    </script>
    <%
    RepeatCaseJournalForm frm = (RepeatCaseJournalForm) session.getAttribute("stac_repeatCaseForm") ;
    String typeView = (String)request.getAttribute("typeView") ;
    String typeEmergency = (String)request.getAttribute("typeEmergency") ;
    if (frm!=null && frm.getDateBegin()!=null 
    		&&!frm.getDateBegin().equals("")
    		&& frm.getDateEnd()!=null 
    		&&!frm.getDateEnd().equals("")
    		&& frm.getCnt()!=null
    		&&frm.getCnt().intValue()>1
    		&&typeView!=null
    )  {
    	try {
    	SimpleDateFormat FORMAT_2 = new SimpleDateFormat("dd.MM.yyyy") ;
    	ActionUtil.setParameterFilterSql("department", "ml.id", request) ;
    	request.setAttribute("startDate",frm.getDateBegin()) ;
    	request.setAttribute("finishDate",frm.getDateEnd()) ;
    	request.setAttribute("count",frm.getCnt()) ;
    	} catch (Exception e) {
    		out.print(e) ;
    	}
        
    	if (typeEmergency.equals("1")) {
    		request.setAttribute("emergency", " and mm.emergency='1'") ;
    		request.setAttribute("emergencySql", " and m.emergency=@1@") ;
    	} else if (typeEmergency.equals("2")) {
    		request.setAttribute("emergency", " and (mm.emergency is null or mm.emergency='0')") ;
    		request.setAttribute("emergencySql", " and (m.emergency is null or m.emergency=@0@)") ;
    	}
    	
        if (typeView.equals("1")) {
    	%>
    
    <msh:section>
    <msh:sectionTitle>Повторные обращения по стационару</msh:sectionTitle>
    <msh:sectionContent>
    <ecom:webQuery name="journal_repeatCase" nativeSql="select 
    mm.patient_id||':${startDate}:${finishDate}:${count}:HospitalMedCase' 
    ||': ${emergencySql}' as id
    ,p.lastname||' '||p.firstname||' '||p.middlename||' '||to_char(p.birthday,'DD.MM.YYYY') as patient
    ,count(distinct mm.id) as cntAll
    ,count(case when mm.deniedHospitalizating_id is not null then mm.id else null end) as cntDenied
    , list(distinct case 
    when vpd.code='1' and vdrt.code='3' and mm.datefinish is not null then 'СК№'||ss.code||' '||mkb.code 
    when vpd.code='1' and vdrt.code='1' and mm.datefinish is null then 'невыпис. СК№'||ss.code||' '||mkb.code 
    else null end) as diag
    
     from medcase mm 
        	left join diagnosis diag on diag.medcase_id=mm.id
   	left join VocIdc10 mkb on mkb.id=diag.idc10_id 
   	left join VocPriorityDiagnosis vpd on vpd.id=diag.priority_id
   	left join VocDiagnosisRegistrationType vdrt on vdrt.id=diag.registrationType_id
     
left join patient p on mm.patient_id=p.id 
left join statisticstub ss on ss.id=mm.statisticstub_id
left join mislpu ml on ml.id=mm.department_id
where  mm.dateStart between to_date('${startDate}','dd.mm.yyyy')
and to_date('${finishDate}','dd.mm.yyyy') 
and upper(mm.dtype)='HOSPITALMEDCASE' 
${emergency} ${departmentSql}
group by mm.patient_id,p.lastname,p.middlename,p.firstname,p.birthday
having count(distinct mm.id)>=${count}
order by p.lastname,p.middlename,p.firstname
" />
        <msh:table name="journal_repeatCase" action="stac_journalRepeatCaseByHospital_data.do?department=${param.department}" idField="1" noDataMessage="Не найдено">
            <msh:tableColumn columnName="#" property="sn"/>
            <msh:tableColumn columnName="ФИО пациент" property="2"/>
            <msh:tableColumn columnName="Кол-во случаев" property="3" isCalcAmount="true"/>
            <msh:tableColumn columnName="из них отказы" property="4" isCalcAmount="true"/>
            <msh:tableColumn columnName="диагнозы" property="5" />
        </msh:table>
    </msh:sectionContent>
    </msh:section>
    <% }
        if (typeView.equals("2")) {%>
      <msh:section>
    <msh:sectionTitle>Повторные отказы от госпитализаций по стационару</msh:sectionTitle>
    <msh:sectionContent>
    <ecom:webQuery name="journal_repeatCase" nativeSql="select mm.patient_id||':${startDate}:${finishDate}:${count}:HospitalMedCase'
    ||': and m.deniedHospitalizating_id is not null ${emergencySql}' as id
    ,p.lastname||' '||p.firstname||' '||p.middlename||' '||to_char(p.birthday,'DD.MM.YYYY') as patient
    ,count(distinct mm.id) as cntAll

    , list(distinct case 
    when vpd.code='1' and vdrt.code='3' and mm.datefinish is not null then 'СК№'||ss.code||' '||mkb.code 
    when vpd.code='1' and vdrt.code='1' and mm.datefinish is null then 'невыпис. СК№'||ss.code||' '||mkb.code 
    else null end) as diag
    
     from medcase mm 
        	left join diagnosis diag on diag.medcase_id=mm.id
   	left join VocIdc10 mkb on mkb.id=diag.idc10_id 
   	left join VocPriorityDiagnosis vpd on vpd.id=diag.priority_id
   	left join VocDiagnosisRegistrationType vdrt on vdrt.id=diag.registrationType_id
left join mislpu ml on ml.id=mm.department_id
left join patient p on mm.patient_id=p.id 
left join statisticstub ss on ss.id=mm.statisticstub_id
where mm.dtype='HospitalMedCase'  and mm.dateStart between to_date('${startDate}','dd.mm.yyyy')
and to_date('${finishDate}','dd.mm.yyyy') and mm.deniedHospitalizating_id is not null
${emergency} ${departmentSql}
group by mm.patient_id,p.lastname,p.middlename,p.firstname,p.birthday
having count(distinct mm.id)>=${count}
order by p.lastname,p.middlename,p.firstname
" />
        <msh:table name="journal_repeatCase" action="stac_journalRepeatCaseByHospital_data.do?department=${param.department}" idField="1" noDataMessage="Не найдено">
            <msh:tableColumn columnName="#" property="sn"/>
            <msh:tableColumn columnName="ФИО пациент" property="2"/>
            <msh:tableColumn columnName="Кол-во случаев" property="3" isCalcAmount="true"/>
            <msh:tableColumn columnName="диагнозы" property="4" />
         </msh:table>
    </msh:sectionContent>
    </msh:section>

    <% }
        if (typeView.equals("3")) {%>
      <msh:section>
    <msh:sectionTitle>Повторные госпитализации</msh:sectionTitle>
    <msh:sectionContent>
    <ecom:webQuery name="journal_repeatCase" nativeSql="select mm.patient_id||':${startDate}:${finishDate}:${count}:HospitalMedCase: and m.deniedHospitalizating_id is null ${emergencySql}' as id
    ,p.lastname||' '||p.firstname||' '||p.middlename||' '||to_char(p.birthday,'DD.MM.YYYY') as patient
    ,count(distinct mm.id) as cntAll

    , list(distinct case 
    when vpd.code='1' and vdrt.code='3' and mm.datefinish is not null then 'СК№'||ss.code||' '||mkb.code 
    when vpd.code='1' and vdrt.code='1' and mm.datefinish is null then 'невыпис. СК№'||ss.code||' '||mkb.code 
    else null end) as diag
    
     from medcase mm 
        	left join diagnosis diag on diag.medcase_id=mm.id
   	left join VocIdc10 mkb on mkb.id=diag.idc10_id 
   	left join VocPriorityDiagnosis vpd on vpd.id=diag.priority_id
   	left join VocDiagnosisRegistrationType vdrt on vdrt.id=diag.registrationType_id
left join mislpu ml on ml.id=mm.department_id
left join patient p on mm.patient_id=p.id 
left join statisticstub ss on ss.id=mm.statisticstub_id
where mm.dtype='HospitalMedCase'  and mm.dateStart between to_date('${startDate}','dd.mm.yyyy')
and to_date('${finishDate}','dd.mm.yyyy') and mm.deniedHospitalizating_id is null
${emergency} ${departmentSql}
group by mm.patient_id,p.lastname,p.middlename,p.firstname,p.birthday
having count(distinct mm.id)>=${count}
order by p.lastname,p.middlename,p.firstname
" />
        <msh:table name="journal_repeatCase" action="stac_journalRepeatCaseByHospital_data.do?department=${param.department}" idField="1" noDataMessage="Не найдено">
            <msh:tableColumn columnName="#" property="sn"/>
            <msh:tableColumn columnName="ФИО пациент" property="2"/>
            <msh:tableColumn columnName="Кол-во случаев" property="3" isCalcAmount="true"/>
                        <msh:tableColumn columnName="диагнозы" property="4" />
            
        </msh:table>
    </msh:sectionContent>
    </msh:section>
    <% }
        if (typeView.equals("4")) {%>
      <msh:section>
    <msh:sectionTitle>Повторные визиты по дате приема в поликлинике</msh:sectionTitle>
    <msh:sectionContent>
    <ecom:webQuery name="journal_repeatCase" nativeSql="select mm.patient_id||':${startDate}:${finishDate}:${count}:Visit'
    ||': and m.dateStart=to_date(@'||to_char(mm.dateStart,'dd.mm.yyyy')||'@,@dd.mm.yyyy@) ${emergencySql}' as id
    ,p.lastname||' '||p.firstname||' '||p.middlename||' '||to_char(p.birthday,'DD.MM.YYYY') as patient
    ,count(*) as cntCount
    ,list(vwf.name) as listspec
    ,mm.dateStart as mmdateStart
     from medcase mm 
left join patient p on mm.patient_id=p.id 
left join statisticstub ss on ss.id=mm.statisticstub_id
left join workfunction wf on wf.id=mm.workFunctionExecute_id
left join worker w on w.id=wf.worker_id
left join mislpu ml on ml.id=w.lpu_id
left join vocworkfunction vwf on vwf.id=wf.workFunction_id
where mm.dtype='Visit'  and mm.dateStart between to_date('${startDate}','dd.mm.yyyy')
and to_date('${finishDate}','dd.mm.yyyy')
${emergency} ${departmentSql}
and  (mm.noActuality='0' or mm.noActuality is null) 

group by mm.patient_id,mm.dateStart,p.lastname,p.middlename,p.firstname,p.birthday
having count(*)>=${count}
order by p.lastname,p.middlename,p.firstname
" />
        <msh:table name="journal_repeatCase" action="stac_journalRepeatCaseByHospital_data.do?department=${param.department}" idField="1" noDataMessage="Не найдено">
            <msh:tableColumn columnName="#" property="sn"/>
            <msh:tableColumn columnName="ФИО пациент" property="2"/>
            <msh:tableColumn columnName="Дата приема" property="5"/>
            <msh:tableColumn columnName="Кол-во случаев" property="3" isCalcAmount="true"/>
            <msh:tableColumn columnName="Рабочая функция" property="4"/>
        </msh:table>
    </msh:sectionContent>
    </msh:section>
    <% }
        if (typeView.equals("5")) {%>
      <msh:section>
    <msh:sectionTitle>Повторные визиты по обращениям к специалистам</msh:sectionTitle>
    <msh:sectionContent>
    <ecom:webQuery name="journal_repeatCase" nativeSql="select mm.patient_id||':${startDate}:${finishDate}:${count}:Visit'
    ||':  ${emergencySql} and wf.workFunction_id='||wf.workFunction_id as id
    ,p.lastname||' '||p.firstname||' '||p.middlename||' '||to_char(p.birthday,'DD.MM.YYYY') as patient
    ,count(*) as cntCount
    ,vwf.name as vwfname
    ,list(wp.lastname||' '||substring(wp.firstname,1,1)||coalesce(substring(wp.middlename,1,1),'')) as listspec
     from medcase mm 
left join patient p on mm.patient_id=p.id 
left join statisticstub ss on ss.id=mm.statisticstub_id
left join workfunction wf on wf.id=mm.workFunctionExecute_id
left join vocworkfunction vwf on vwf.id=wf.workFunction_id
left join worker w on w.id=wf.worker_id
left join mislpu ml on ml.id=w.lpu_id
left join patient wp on wp.id=w.person_id
where mm.dtype='Visit'  and mm.dateStart between to_date('${startDate}','dd.mm.yyyy')
and to_date('${finishDate}','dd.mm.yyyy') 
${emergency} ${departmentSql}
and  (mm.noActuality='0' or mm.noActuality is null)
group by mm.patient_id,wf.workFunction_id,p.lastname,p.middlename,p.firstname,p.birthday,vwf.name
having count(*)>=${count}
order by p.lastname,p.middlename,p.firstname
" />
        <msh:table name="journal_repeatCase" action="stac_journalRepeatCaseByHospital_data.do?department=${param.department}" idField="1" noDataMessage="Не найдено">
            <msh:tableColumn columnName="#" property="sn"/>
            <msh:tableColumn columnName="ФИО пациент" property="2"/>
            <msh:tableColumn columnName="Кол-во случаев" property="3" isCalcAmount="true"/>
            <msh:tableColumn columnName="Рабочая функция" property="4"/>
            <msh:tableColumn columnName="Специалисты" property="5"/>
        </msh:table>
    </msh:sectionContent>
    </msh:section>
        
    <% } 
    } else {%>
    	<i>Выберите параметры и нажмите найти </i>
    	<% }   %>
  </tiles:put>
</tiles:insert>

