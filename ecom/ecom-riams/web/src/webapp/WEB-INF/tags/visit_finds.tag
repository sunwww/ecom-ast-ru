<%@ tag pageEncoding="utf8" %>
<%@ taglib uri="http://www.nuzmsh.ru/tags/msh" prefix="msh" %>
<%@ taglib tagdir="/WEB-INF/tags" prefix="tags" %>

<%@  attribute name="currentAction" required="true" description="Текущее меню" %>

<style type="text/css">
a#${currentAction}, #side ul li a#${currentAction}, #side ul li a#${currentAction}  {
    color: black ;
    background-color: rgb(195,217,255) ;
    cursor: text ;
    border: none ;
    text-decoration: none ;
    background-image: url("/skin/images/main/sideSelected.gif");
    background-repeat: no-repeat;
    background-position: center left; ;
	font-weight: bold ;
    -moz-border-radius-topleft: 4px ;
    -moz-border-radius-bottomleft: 4px ;
}
</style>
<msh:sideMenu></msh:sideMenu>
<msh:sideMenu title="Рабочий календарь">
    <msh:sideLink action="/js-smo_visit-findPolyAdmissions.do" name="Раб.календарь"
    	 styleId="workCalendar" roles="/Policy/Mis/MedCase/Visit/View"/>
    <msh:sideLink styleId="otherWorkFunction" 
    	name="Пациенты, направленные по другим раб. функциям" 
    	title="Пациенты, направленные по другим раб.функциям" 
    	action="/js-smo_visit-findOtherFunctionsPolyAdmissions.do" />    
    <msh:sideLink styleId="otherDays" 
    	name="Принятые пациенты, направленные на другое число" 
    	title="Принятые пациенты, направленные на другое число" 
    	action="/js-smo_visit-findOtherDaysPolyAdmissions.do" />    
</msh:sideMenu>

<msh:sideMenu title="Прием">
    <msh:sideLink action="/smo_goingToSmo.do" name="Переход к визиту"
    	 styleId="smo_goingToSmo" roles="/Policy/Mis/MedCase/Visit/View"/>
    	 
	<msh:sideLink name="Список отчетов" 
	action="/javascript:getDefinition('riams_journals.do?short=Short',null)" styleId="viewShort" />
	<msh:sideLink name="Учет посещений" action="/smo_journalRegisterVisit_list.do" 
		title="Учет посещений" roles="/Policy/Mis/MedCase/Visit/JournalRegisterVisit"
		styleId="journalRegisterVisit"/>
	<msh:sideLink name="Дубли" action="/smo_doubleVisits_list.do"
		styleId="journalDouble" roles="/Policy/Mis/MedCase/Visit/JournalDoubleVisit" 
		title="Просмотр повторных по посещениям"/>
		
	<msh:sideLink name="Форма 039У-02" action="/visit_f039_list.do?ticketIs=0" params="" 
		roles="/Policy/Mis/MedCase/Visit/Report039" styleId="report039"
	/>
    <msh:sideLink roles="/Policy/Mis/MedCase/Visit/Journal/PlanHospital" 
    name="Журнал предварительной госпитализации" action="/smo_report_plan_hospital_by_visit.do" styleId="reportPlanHospital"/>
    <msh:sideLink roles="/Policy/Mis/MedCase/Direction/Journal" 
    name="Журнал направленных" action="/visit_journal_direction.do" styleId="reportDirect"/>
    <msh:sideLink roles="/Policy/Mis/MedCase/Visit/ReportVisits" 
    name="Журнал обращений" action="/journal_visits_list.do" styleId="reportVisits"/>
    <msh:sideLink roles="/Policy/Mis/MedCase/Visit/ReportNationality" 
    name="Журнал СМО с разбивкой по гражданству" action="/journal_nationality_smo.do" styleId="reportNationality"/>
	<msh:sideLink name="Открытые СПО" action="/smo_journal_openSpo.do"
		styleId="smo_journal_openSpo" roles="/Policy/Mis/MedCase/Visit/OpenSpo"/>
	<msh:sideLink name="Закрытые СПО" action="/smo_journal_closeSpo.do"
		styleId="smo_journal_closeSpo" roles="/Policy/Mis/MedCase/Visit/CloseSpo"/>
	<msh:sideLink name="Активные направления" action="/smo_activeDirect_list.do"
		styleId="journalActiveDirect" roles="/Policy/Mis/MedCase/Visit/JournalActiveDirect"/>
	<msh:sideLink name="Журнал диагнозов по посещениям" action="/journal_visit_diagnosis.do"
		styleId="reportDiagnosis" roles="/Policy/Mis/MedCase/Visit/ReportDiagnosis"/>
	<msh:sideLink name="Журнал посещений к другим специалистам"
	 action="/poly_directOtherSpecialist.do"
		styleId="reportDirectOtherSpec" roles="/Policy/Mis/MedCase/Visit/DirectOtherSpecialist"/>
</msh:sideMenu>

