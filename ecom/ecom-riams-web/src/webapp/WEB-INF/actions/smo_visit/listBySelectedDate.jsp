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
  	
  	<msh:section title="Непринятые пациенты" >
  		<ecom:webQuery name="list_no" nativeSql="select v.id
  		,cast(wct.timeFrom as varchar(5)) as timeFrom
  		
  		,po.lastname||' '||po.firstname||' '||po.middlename||' ('||mlo.name||')' as pofio
  		,lpuo.name as lpuoname
  		,p.lastname||' '||p.firstname||' '||p.middlename||' г.р.'||to_char(p.birthday,'DD.MM.YYYY') as pfio
		
		,case when v.dateStart is not null then 'нет диагноза' when v.visitResult_id is null then '' else 'пред. оформлен' end as prerecord
		, list(distinct ms.code||' '||ms.name) as servicies
from medcase v 
left join patient p on p.id=v.patient_id
left join WorkCalendarDay wcd on wcd.id=v.datePlan_id
left join WorkCalendarTime wct on wct.id=v.timePlan_id
left join WorkFunction wfo on wfo.id=v.orderWorkFunction_id
left join Worker wo on wo.id=wfo.worker_id
left join Patient po on po.id=wo.person_id
left join VocWorkFunction vwfo on vwfo.id=wfo.workFunction_id
left join MisLpu mlo on mlo.id=wo.lpu_id
left join WorkFunction wfe on wfe.id=v.workFunctionExecute_id
left join Worker we on we.id=wfe.worker_id
left join Patient pe on pe.id=we.person_id
left join VocWorkFunction vwfe on vwfe.id=wfe.workFunction_id
left join diagnosis d on d.medcase_id=v.id
left join mislpu lpuo on lpuo.id=v.orderLpu_id
left join VocVisitResult vvr on vvr.id=v.visitResult_id
left join medCase smc on smc.parent_id=v.id and smc.dtype='ServiceMedCase'
left join MedService ms on ms.id=smc.medservice_id
where  v.datePlan_id='${calenDayId}' and v.DTYPE='Visit' and (v.noActuality is null or v.noActuality='0')
group by v.id,wct.timeFrom,v.dateStart,v.timeExecute,vwfe.isNoDiagnosis
,po.lastname,po.firstname,po.middlename,lpuo.name
,p.lastname,p.firstname,p.middlename,p.birthday
,pe.lastname,pe.firstname,pe.middlename
,vvr.name,v.visitResult_id,mlo.name
having (v.dateStart is null or (count(d.id)=0 and (vwfe.isNoDiagnosis is null or vwfe.isNoDiagnosis='0')))
order by wct.timeFrom"/>
	    <msh:table name="list_no" action="entityEdit-smo_visit.do" idField="1" guid="b621e361-1e0b-4ebd-9f58-b7d919b45bd6">
	      <msh:tableColumn columnName="№" identificator="false" property="sn" guid="270ae0dc-e1c6-45c5-b8b8-26d034ec3878" />
	      <msh:tableColumn columnName="Доп. инф." property="6" guid="de1f591c-02b8-4875-969f-d2698689db5d" />
	      <msh:tableColumn columnName="Направлен" property="2" guid="de1f591c-02b8-4875-969f-d2698689db5d" />
	      <msh:tableColumn columnName="Пациент" property="5" guid="315cb6eb-3db8-4de5-8b0c-a49e3cacf382" />
	      <msh:tableColumn columnName="Услуги" property="7" guid="de1f591c-02b8-4875-969f-d2698689db5d" />
	      <msh:tableColumn columnName="Кто направил" property="3" guid="6682eeef-105f-43a0-be61-30a865f27972" />
	      <msh:tableColumn columnName="Кем направлен" property="4" guid="f34e1b12-3392-4978-b31f-5e54ff2e45bd" />
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
	    ,po.lastname||' '||po.firstname||' '||po.middlename||' ('||mlo.name||')' as pofio
	    ,lpuo.name as lpuoname 
	    ,p.lastname||' '||p.firstname||' '||p.middlename||' г.р.'||to_char(p.birthday,'DD.MM.YYYY') as pfio
	    ,pe.lastname||' '||pe.firstname||' '||pe.middlename as pefio
	    ,vvr.name as vvrname 
		, list(distinct ms.code||' '||ms.name) as servicies

from medcase v 
left join patient p on p.id=v.patient_id
left join WorkCalendarDay wcd on wcd.id=v.datePlan_id
left join WorkCalendarTime wct on wct.id=v.timePlan_id
left join WorkFunction wfo on wfo.id=v.orderWorkFunction_id
left join Worker wo on wo.id=wfo.worker_id
left join Patient po on po.id=wo.person_id
left join VocWorkFunction vwfo on vwfo.id=wfo.workFunction_id
left join MisLpu mlo on mlo.id=wo.lpu_id
left join WorkFunction wfe on wfe.id=v.workFunctionExecute_id
left join Worker we on we.id=wfe.worker_id
left join Patient pe on pe.id=we.person_id
left join VocWorkFunction vwfe on vwfe.id=wfe.workFunction_id
left join diagnosis d on d.medcase_id=v.id
left join mislpu lpuo on lpuo.id=v.orderLpu_id
left join VocVisitResult vvr on vvr.id=v.visitResult_id
left join medCase smc on smc.parent_id=v.id and smc.dtype='ServiceMedCase'
left join MedService ms on ms.id=smc.medservice_id

where  v.datePlan_id='${calenDayId}' and v.DTYPE='Visit' and v.dateStart is not null
and (v.noActuality is null or v.noActuality='0')
group by v.id,wct.timeFrom,v.dateStart,v.timeExecute,vwfe.isNoDiagnosis
,po.lastname,po.firstname,po.middlename,lpuo.name
,p.lastname,p.firstname,p.middlename,p.birthday
,pe.lastname,pe.firstname,pe.middlename
,vvr.name,v.visitResult_id,mlo.name
having (count(d.id)>0 or vwfe.isNoDiagnosis='1')
order by v.timeExecute"/>
<msh:table viewUrl="entityShortView-smo_visit.do" editUrl="entityParentEdit-smo_visit.do" name="list_yes" action="entityView-smo_visit.do" idField="1" guid="b621e361-1e0b-4ebd-9f58-b7d919b45bd6">
	      <msh:tableColumn columnName="№" identificator="false" property="sn" guid="270ae0dc-e1c6-45c5-b8b8-26d034ec3878" />
	      <msh:tableColumn columnName="Пациент" property="6" guid="315cb6eb-3db8-4de5-8b0c-a49e3cacf382" />
	      <msh:tableColumn columnName="Услуги" property="9" guid="de1f591c-02b8-4875-969f-d2698689db5d" />
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
    
    
  </tiles:put>
  
</tiles:insert>

