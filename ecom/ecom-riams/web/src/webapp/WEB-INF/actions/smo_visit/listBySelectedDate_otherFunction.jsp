<%@page import="ru.ecom.web.login.LoginInfo"%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://struts.apache.org/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://www.nuzmsh.ru/tags/msh" prefix="msh" %>
<%@ taglib uri="http://www.ecom-ast.ru/tags/ecom" prefix="ecom" %>
<%@ taglib tagdir="/WEB-INF/tags" prefix="tags" %>

<tiles:insert page="/WEB-INF/tiles/mainLayout.jsp" flush="true" >

  <tiles:put name="title" type="string">
    <msh:title mainMenu="Poly" title="Рабочий календарь" property="worker" />
    
  </tiles:put>
  <tiles:put name="side" type="string">
    <msh:sideMenu>
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
  	<ecom:webQuery  name="wf3" nativeSql="select wcdN.id
  	,vwfN.name as vwfNname,wfNG.groupName as fwNGgroupname
  	,count(distinct case when wctN.medCase_id is not null then wctN.id else null end) as cntAll
  	,count(distinct case when mcN.dateStart is not null then mcN.id else null end) as cntPrin
from workCalendarDay wcdN
left join WorkCalendar wcN on wcN.id=wcdN.workCalendar_id
left join WorkFunction wfNG on wfNG.id=wcN.workFunction_id
left join VocWorkFunction vwfN on vwfN.id=wfNG.workFunction_id
left join MisLpu lpuN on lpuN.id=wfNG.lpu_id
left join WorkFunction wfNP on wfNP.group_id=wfNG.id
left join Worker wNP on wNP.id=wfNP.worker_id
left join Worker wOld on wOld.person_id=wNP.person_id
left join WorkFunction wfOld on wfOld.worker_id=wOld.id
left join WorkCalendar wcOld on wcOld.workFunction_id=wfOld.id
left join WorkCalendarDay wcdOld on wcdOld.workCalendar_id=wcOld.id
left join WorkCalendarTime wctN on wctN.workCalendarDay_id=wcdN.id
left join MedCase mcN on mcN.id=wctN.medCase_id
where wcdOld.id='${calenDayId}' and wcdN.calendarDate=wcdOld.calendarDate
and wcdN.id!='${calenDayId}'
group by wcdN.id,wfNG.groupName,vwfN.name,lpuN.name
  	"/>
  	<msh:section title="Направления по другим специализациям">
  		<msh:table name="wf1" action="js-smo_visit-replaceWF.do"
  		 idField="1">
	      <msh:tableColumn columnName="специальность" property="2" />
	      <msh:tableColumn columnName="кол-во направленных" property="3"/>
	      <msh:tableColumn columnName="кол-во принятых" property="4" />
	      <msh:tableColumn columnName="ЛПУ" property="5" />
	    </msh:table>
  	</msh:section>
  	
  	<msh:section title="Направления по другим (групповым) специализациям">
  		<msh:table name="wf3" action="js-smo_visit-replaceWF.do"
  		 idField="1">
	      <msh:tableColumn columnName="специальность" property="2" />
	      <msh:tableColumn columnName="название группы" property="3" />
	      <msh:tableColumn columnName="кол-во направленных" property="4"/>
	      <msh:tableColumn columnName="кол-во принятых" property="5" />
	      <msh:tableColumn columnName="ЛПУ" property="6" />
	    </msh:table>
  	</msh:section>
  	
  </tiles:put>
  
</tiles:insert>

