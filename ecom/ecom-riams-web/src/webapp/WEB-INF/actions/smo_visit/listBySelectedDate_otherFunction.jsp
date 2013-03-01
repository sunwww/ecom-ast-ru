<%@page import="ru.ecom.web.login.LoginInfo"%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://struts.apache.org/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://www.nuzmsh.ru/tags/msh" prefix="msh" %>
<%@ taglib uri="http://www.ecom-ast.ru/tags/ecom" prefix="ecom" %>
<%@ taglib tagdir="/WEB-INF/tags" prefix="tags" %>

<tiles:insert page="/WEB-INF/tiles/mainLayout.jsp" flush="true" >

  <tiles:put name="title" type="string">
    <msh:title guid="helloItle-123" mainMenu="Poly" title="Рабочий календарь" property="worker" />
    
  </tiles:put>
  <tiles:put name="side" type="string">
    <msh:sideMenu guid="helloSideMenu-123">
      <tags:smo_workDay name="calendarWork" action="js-smo_visit-findOtherFunctionsPolyAdmissions.do" title="Выбрать дату"/>
    </msh:sideMenu>
    <tags:visit_finds currentAction="otherWorkFunction"/>
  </tiles:put>
  <tiles:put name="body" type="string">
  	<ecom:webQuery  name="wf1" nativeSql="select wcd1.id
  	,vwf1.name
  	,count(case when wct1.medCase_id is not null then 1 else null end) as cntAll
  	,count(case when mc1.dateStart is not null then 1 else null end) as cntPrin
  	,lpu1.name as lpu1name
  	from WorkCalendarDay wcd
  	left join workCalendar wc on wc.id=wcd.workCalendar_id
  	left join workFunction wf on wf.id=wc.workFunction_id
  	left join worker w on w.id=wf.worker_id
  	left join worker w1 on w1.person_id=w.person_id
  	left join workFunction wf1 on wf1.worker_id=w1.id
  	left join vocworkfunction vwf1 on vwf1.id =wf1.workFunction_id
  	left join workcalendar wc1 on wc1.workFunction_id=wf1.id
  	left join mislpu lpu1 on lpu1.id=w1.lpu_id
  	
  	left join workCalendarDay wcd1 on wcd1.workCalendar_id=wc1.id 
  	left join workCalendarTime wct1 on wct1.workCalendarDay_id=wcd1.id
  	left join medCase mc1 on wct1.id=mc1.timePlan_id
  	
  	where wcd.id='${calenDayId}'  and 
  	wcd1.calendarDate=wcd.calendarDate and wcd1.id!=wcd.id 
  	
  	 and wf1.group_id is null
  	group by wcd1.id,vwf1.name,lpu1.name
  	"/>
  	<ecom:webQuery  name="wf3" nativeSql="select
	wcd2.id
  	,vwf2.name as vfw2name
  	,count(case when wct2.medCase_id is not null then 1 else null end) as cntAll
  	,count(case when mc2.dateStart is not null then 1 else null end) as cntPrin
  	,lpu2.name as lpu2name
  	from WorkCalendarDay wcd
  	left join workCalendar wc on wc.id=wcd.workCalendar_id
  	left join workFunction wf on wf.id=wc.workFunction_id
  	left join worker w on w.id=wf.worker_id
  	left join worker w1 on w1.person_id=w.person_id
  	left join workFunction wf1 on wf1.worker_id=w1.id
  	left join workFunction wf2 on wf2.id=wf1.group_id
  	left join worker w2 on w2.id=wf2.worker_id
  	left join vocworkfunction vwf2 on vwf2.id=wf2.workFunction_id
  	left join workcalendar wc2 on wc2.workFunction_id=wf2.id
  	left join workCalendarDay wcd2 on wcd2.workCalendar_id=wc2.id
  	left join workCalendarTime wct2 on wct2.workCalendarDay_id=wcd2.id 
  	left join medCase mc2 on wct2.id=mc2.timePlan_id
  	left join mislpu lpu2 on lpu2.id=w2.lpu_id
  	where wcd.id='${calenDayId}' and 
  	
  	wcd2.calendarDate=wcd.calendarDate and wcd2.id!=wcd.id
  	
  	group by wcd2.id,vwf2.name,lpu2.name
  	"/>
  	<msh:section title="Направления по другим специализациям">
  		<msh:table name="wf1" action="js-smo_visit-replaceWF.do"
  		 idField="1" guid="b621e361-1e0b-4ebd-9f58-b7d919b45bd6">
	      <msh:tableColumn columnName="специальность" property="2" />
	      <msh:tableColumn columnName="кол-во направленных" property="3"/>
	      <msh:tableColumn columnName="кол-во принятых" property="4" />
	      <msh:tableColumn columnName="ЛПУ" property="5" />
	    </msh:table>
  	</msh:section>
  	
  	<msh:section title="Направления по другим (групповым) специализациям">
  		<msh:table name="wf3" action="js-smo_visit-replaceWF.do"
  		 idField="1" guid="b621e361-1e0b-4ebd-9f58-b7d919b45bd6">
	      <msh:tableColumn columnName="специальность" property="2" />
	      <msh:tableColumn columnName="кол-во направленных" property="3"/>
	      <msh:tableColumn columnName="кол-во принятых" property="4" />
	      <msh:tableColumn columnName="ЛПУ" property="5" />
	    </msh:table>
  	</msh:section>
  	
  </tiles:put>
  
</tiles:insert>

