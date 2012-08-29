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
      <!--<msh:sideLink action="/js-smo_visit-findByAllPlanDates" name="Выбрать дату" title="Выбор даты" guid="4c13d371-c72a-4bc0-b2cd-c0bcfce1be6f" />-->
      <tags:smo_workDay name="calendarWork" action="js-smo_visit-findPolyAdmissions.do" title="Выбрать дату"/>
    </msh:sideMenu>
    <tags:visit_finds currentAction="workCalendar"/>
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
  	<ecom:webQuery  name="wf2" nativeSql="select
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
  	<msh:tableNotEmpty name="wf1">
  	<msh:section title="Направления по другим специализациям">
  		<msh:table name="wf1" action="js-smo_visit-findPolyAdmissions.do"
  		 idField="1" guid="b621e361-1e0b-4ebd-9f58-b7d919b45bd6">
	      <msh:tableColumn columnName="специальность" property="2" />
	      <msh:tableColumn columnName="кол-во направленных" property="3"/>
	      <msh:tableColumn columnName="кол-во принятых" property="4" />
	      <msh:tableColumn columnName="ЛПУ" property="5" />
	    </msh:table>
  	</msh:section>
  	</msh:tableNotEmpty>
  	<msh:tableNotEmpty name="wf2">
  	<msh:section title="Направления по другим (групповым) специализациям">
  		<msh:table name="wf2" action="js-smo_visit-findPolyAdmissions.do"
  		 idField="1" guid="b621e361-1e0b-4ebd-9f58-b7d919b45bd6">
	      <msh:tableColumn columnName="специальность" property="2" />
	      <msh:tableColumn columnName="кол-во направленных" property="3"/>
	      <msh:tableColumn columnName="кол-во принятых" property="4" />
	      <msh:tableColumn columnName="ЛПУ" property="5" />
	    </msh:table>
  	</msh:section>
  	</msh:tableNotEmpty>
  	
  	<msh:section title="Непринятые пациенты" >
  		<ecom:webQuery name="list_no" nativeSql="select v.id
  		,cast(wct.timeFrom as varchar(5)) as timeFrom
  		,to_char(v.dateStart,'DD.MM.YYYY')||' '||cast(v.timeExecute as varchar(5)) as dateStart
  		,po.lastname||' '||po.firstname||' '||po.middlename as pofio
  		,lpuo.name as lpuoname
  		,p.lastname||' '||p.firstname||' '||p.middlename||' г.р.'||to_char(p.birthday,'DD.MM.YYYY') as pfio
		,pe.lastname||' '||pe.firstname||' '||pe.middlename as pefio
		,vvr.name as vvrname 
		,case when v.dateStart is not null then 'нет диагноза' when v.visitResult_id is null then '' else 'пред. оформлен' end as prerecord

from medcase v 
left join patient p on p.id=v.patient_id
left join WorkCalendarDay wcd on wcd.id=v.datePlan_id
left join WorkCalendarTime wct on wct.id=v.timePlan_id
left join WorkFunction wfo on wfo.id=v.orderWorkFunction_id
left join Worker wo on wo.id=wfo.worker_id
left join Patient po on po.id=wo.person_id
left join VocWorkFunction vwfo on vwfo.id=wfo.workFunction_id
left join WorkFunction wfe on wfe.id=v.workFunctionExecute_id
left join Worker we on we.id=wfe.worker_id
left join Patient pe on pe.id=we.person_id
left join VocWorkFunction vwfe on vwfe.id=wfe.workFunction_id
left join diagnosis d on d.medcase_id=v.id
left join mislpu lpuo on lpuo.id=v.orderLpu_id
left join VocVisitResult vvr on vvr.id=v.visitResult_id
where  v.datePlan_id='${calenDayId}' and v.DTYPE='Visit' and (v.noActuality is null or v.noActuality='0')
group by v.id,wct.timeFrom,v.dateStart,v.timeExecute
,po.lastname,po.firstname,po.middlename,lpuo.name
,p.lastname,p.firstname,p.middlename,p.birthday
,pe.lastname,pe.firstname,pe.middlename
,vvr.name,v.visitResult_id
having (v.dateStart is null or count(d.id)=0)
order by wct.timeFrom"/>
	    <msh:table name="list_no" action="entityEdit-smo_visit.do" idField="1" guid="b621e361-1e0b-4ebd-9f58-b7d919b45bd6">
	      <msh:tableColumn columnName="№" identificator="false" property="sn" guid="270ae0dc-e1c6-45c5-b8b8-26d034ec3878" />
	      <msh:tableColumn columnName="Доп. инф." property="9" guid="de1f591c-02b8-4875-969f-d2698689db5d" />
	      <msh:tableColumn columnName="Направлен" property="2" guid="de1f591c-02b8-4875-969f-d2698689db5d" />
	      <msh:tableColumn columnName="Пациент" property="6" guid="315cb6eb-3db8-4de5-8b0c-a49e3cacf382" />
	      <msh:tableColumn columnName="Кто направил" property="4" guid="6682eeef-105f-43a0-be61-30a865f27972" />
	      <msh:tableColumn columnName="Кем направлен" property="5" guid="f34e1b12-3392-4978-b31f-5e54ff2e45bd" />
	    </msh:table>
  	</msh:section>
    <msh:section title="Кол-во пациентов по предварительной записи">
  	    <ecom:webQuery name="list_prerecord" nativeSql="select min(wcd.id)
  	    	,count(distinct wct.id) as cntAll
  	    	,count(distinct case when wct.prepatientinfo like 'РЕЗЕРВ%' then wct.id else null end) as cntRez 
  	    	from workCalendarTime wct
					 left join WorkCalendarDay wcd on wcd.id=wct.workCalendarDay_id
					 left join WorkCalendar wc on wc.id=wcd.workCalendar_id
					 where wcd.id='${calenDayId}'
                    and wct.medCase_id is null and (wct.prePatient_id is not null or wct.prePatientInfo is not null and wct.prePatientInfo!='')"/>
     
		<msh:table name="list_prerecord" action="js-smo_visit-findPolyAdmissions.do" idField="1" hideTitle="false">
	      <msh:tableColumn columnName="общее кол-во"  identificator="false" property="2" />
	      <msh:tableColumn property="3" columnName="резерв кол-во"/>
		</msh:table>
    </msh:section>
  	
  	<msh:section title="Принятые пациенты">
	    <ecom:webQuery name="list_yes" nativeSql="select v.id
	    ,cast(wct.timeFrom as varchar(5)) as timeFrom
	    ,to_char(v.dateStart,'DD.MM.YYYY')||' '||cast(v.timeExecute as varchar(5)) as dateStart
	    ,po.lastname||' '||po.firstname||' '||po.middlename as pofio
	    ,lpuo.name as lpuoname 
	    ,p.lastname||' '||p.firstname||' '||p.middlename||' г.р.'||to_char(p.birthday,'DD.MM.YYYY') as pfio
	    ,pe.lastname||' '||pe.firstname||' '||pe.middlename as pefio
	    ,vvr.name as vvrname 

from medcase v 
left join patient p on p.id=v.patient_id
left join WorkCalendarDay wcd on wcd.id=v.datePlan_id
left join WorkCalendarTime wct on wct.id=v.timePlan_id
left join WorkFunction wfo on wfo.id=v.orderWorkFunction_id
left join Worker wo on wo.id=wfo.worker_id
left join Patient po on po.id=wo.person_id
left join VocWorkFunction vwfo on vwfo.id=wfo.workFunction_id
left join WorkFunction wfe on wfe.id=v.workFunctionExecute_id
left join Worker we on we.id=wfe.worker_id
left join Patient pe on pe.id=we.person_id
left join VocWorkFunction vwfe on vwfe.id=wfe.workFunction_id
left join diagnosis d on d.medcase_id=v.id
left join mislpu lpuo on lpuo.id=v.orderLpu_id
left join VocVisitResult vvr on vvr.id=v.visitResult_id
where  v.datePlan_id='${calenDayId}' and v.DTYPE='Visit' and v.dateStart is not null
and (v.noActuality is null or v.noActuality='0')
group by v.id,wct.timeFrom,v.dateStart,v.timeExecute
,po.lastname,po.firstname,po.middlename,lpuo.name
,p.lastname,p.firstname,p.middlename,p.birthday
,pe.lastname,pe.firstname,pe.middlename
,vvr.name,v.visitResult_id
having count(d.id)>0
order by v.timeExecute"/>
<msh:table viewUrl="entityShortView-smo_visit.do" editUrl="entityParentEdit-smo_visit.do" name="list_yes" action="entityView-smo_visit.do" idField="1" guid="b621e361-1e0b-4ebd-9f58-b7d919b45bd6">
	      <msh:tableColumn columnName="№" identificator="false" property="sn" guid="270ae0dc-e1c6-45c5-b8b8-26d034ec3878" />
	      <msh:tableColumn columnName="Пациент" property="6" guid="315cb6eb-3db8-4de5-8b0c-a49e3cacf382" />
	      <msh:tableColumn columnName="Направлен" property="2" guid="de1f591c-02b8-4875-969f-d2698689db5d" />
	      <msh:tableColumn columnName="Исполнен" identificator="false" property="3" guid="b3e2fb6e-53b6-4e69-8427-2534cf1edcca" />
	      <msh:tableColumn columnName="Кто направил" property="4" guid="6682eeef-105f-43a0-be61-30a865f27972" />
	      <msh:tableColumn columnName="Кем направлен" property="5" guid="f34e1b12-3392-4978-b31f-5e54ff2e45bd" />
	      <msh:tableColumn columnName="Результат" identificator="false" property="8"/>
	      <msh:tableColumn columnName="Исполнитель" identificator="false" property="7" guid="3145e72a-cce5-4994-a507-b1a81efefdfe" />
	    </msh:table>
  	</msh:section>
  	<msh:section title="Не явились на прием">
	    <ecom:webQuery name="list_no_other" nativeSql="select v.id
	    ,cast(wct.timeFrom as varchar(5)) as timeFrom
	    ,to_char(v.dateStart,'DD.MM.YYYY')||' '||cast(v.timeExecute as varchar(5)) as dateStart
	    ,po.lastname||' '||po.firstname||' '||po.middlename as pofio
	    ,lpuo.name as lpuoname 
	    ,p.lastname||' '||p.firstname||' '||p.middlename||' г.р.'||to_char(p.birthday,'DD.MM.YYYY') as pfio
	    ,pe.lastname||' '||pe.firstname||' '||pe.middlename as pefio
	    ,vvr.name as vvrname 

from medcase v 
left join patient p on p.id=v.patient_id
left join WorkCalendarDay wcd on wcd.id=v.datePlan_id
left join WorkCalendarTime wct on wct.id=v.timePlan_id
left join WorkFunction wfo on wfo.id=v.orderWorkFunction_id
left join Worker wo on wo.id=wfo.worker_id
left join Patient po on po.id=wo.person_id
left join VocWorkFunction vwfo on vwfo.id=wfo.workFunction_id
left join WorkFunction wfe on wfe.id=v.workFunctionExecute_id
left join Worker we on we.id=wfe.worker_id
left join Patient pe on pe.id=we.person_id
left join VocWorkFunction vwfe on vwfe.id=wfe.workFunction_id
left join diagnosis d on d.medcase_id=v.id
left join mislpu lpuo on lpuo.id=v.orderLpu_id
left join VocVisitResult vvr on vvr.id=v.visitResult_id
where  v.datePlan_id='${calenDayId}' and v.DTYPE='Visit' and v.noActuality='1'
order by v.timeExecute"/>
<msh:table viewUrl="entityShortView-smo_visit.do" editUrl="entityParentEdit-smo_visit.do" name="list_no_other" action="entityView-smo_visit.do" idField="1" guid="b621e361-1e0b-4ebd-9f58-b7d919b45bd6">
	      <msh:tableColumn columnName="№" identificator="false" property="sn" guid="270ae0dc-e1c6-45c5-b8b8-26d034ec3878" />
	      <msh:tableColumn columnName="Пациент" property="6" guid="315cb6eb-3db8-4de5-8b0c-a49e3cacf382" />
	      <msh:tableColumn columnName="Направлен" property="2" guid="de1f591c-02b8-4875-969f-d2698689db5d" />
	      <msh:tableColumn columnName="Исполнен" identificator="false" property="3" guid="b3e2fb6e-53b6-4e69-8427-2534cf1edcca" />
	      <msh:tableColumn columnName="Кто направил" property="4" guid="6682eeef-105f-43a0-be61-30a865f27972" />
	      <msh:tableColumn columnName="Кем направлен" property="5" guid="f34e1b12-3392-4978-b31f-5e54ff2e45bd" />
	      <msh:tableColumn columnName="Результат" identificator="false" property="8"/>
	      <msh:tableColumn columnName="Исполнитель" identificator="false" property="7" guid="3145e72a-cce5-4994-a507-b1a81efefdfe" />
	    </msh:table>
  	</msh:section>
    
  	<msh:section title="Принятые пациенты, направленные на другое число">
	    <ecom:webQuery name="list_other" nativeSql="select v.id 
	    ,to_char(wcd1.calendarDate,'DD.MM.YYYY')||' '||cast(wct.timeFrom as varchar(5)) as timeFrom
	    ,to_char(v.dateStart,'DD.MM.YYYY')||' '||cast(v.timeExecute as varchar(5)) as dateStart
	    ,po.lastname||' '||po.firstname||' '||po.middlename as pofio
	    ,lpuo.name as lpuoname 
	    ,p.lastname||' '||p.firstname||' '||p.middlename||' г.р.'||to_char(p.birthday,'DD.MM.YYYY') as pfio
	    ,pe.lastname||' '||pe.firstname||' '||pe.middlename as pefio
	    ,vvr.name as vvrname 

from medcase v 
left join patient p on p.id=v.patient_id
left join WorkCalendarDay wcd on wcd.calendarDate=v.dateStart
left join WorkCalendarDay wcd1 on wcd1.id=v.datePlan_id
left join WorkCalendarTime wct on wct.id=v.timePlan_id
left join WorkFunction wfo on wfo.id=v.orderWorkFunction_id
left join Worker wo on wo.id=wfo.worker_id
left join Patient po on po.id=wo.person_id
left join VocWorkFunction vwfo on vwfo.id=wfo.workFunction_id
left join WorkFunction wfe on wfe.id=v.workFunctionExecute_id
left join Worker we on we.id=wfe.worker_id
left join Patient pe on pe.id=we.person_id
left join VocWorkFunction vwfe on vwfe.id=wfe.workFunction_id

left join mislpu lpuo on lpuo.id=v.orderLpu_id
left join VocVisitResult vvr on vvr.id=v.visitResult_id
where  v.DTYPE='Visit' and wcd.id='${calenDayId}' and wcd.workCalendar_id=wcd1.workCalendar_id and wcd.id!=v.datePlan_id
order by v.timeExecute"/>
<msh:table viewUrl="entityShortView-smo_visit.do" editUrl="entityParentEdit-smo_visit.do" name="list_other" action="entityView-smo_visit.do" idField="1" guid="b621e361-1e0b-4ebd-9f58-b7d919b45bd6">
	      <msh:tableColumn columnName="№" identificator="false" property="sn" guid="270ae0dc-e1c6-45c5-b8b8-26d034ec3878" />
	      <msh:tableColumn columnName="Направлен" property="2" guid="de1f591c-02b8-4875-969f-d2698689db5d" />
	      <msh:tableColumn columnName="Пациент" property="6" guid="315cb6eb-3db8-4de5-8b0c-a49e3cacf382" />
	      <msh:tableColumn columnName="Исполнен" identificator="false" property="3" guid="b3e2fb6e-53b6-4e69-8427-2534cf1edcca" />
	      <msh:tableColumn columnName="Кто направил" property="4" guid="6682eeef-105f-43a0-be61-30a865f27972" />
	      <msh:tableColumn columnName="Кем направлен" property="5" guid="f34e1b12-3392-4978-b31f-5e54ff2e45bd" />
	      <msh:tableColumn columnName="Исполнитель" identificator="false" property="7" guid="3145e72a-cce5-4994-a507-b1a81efefdfe" />
	      <msh:tableColumn columnName="Результат" identificator="false" property="8"/>
	    </msh:table>
  	</msh:section>
    
  </tiles:put>
  
</tiles:insert>

