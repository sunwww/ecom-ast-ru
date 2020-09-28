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
      <!--<msh:sideLink action="/js-smo_visit-findByAllPlanDates" name="Выбрать дату" title="Выбор даты" />-->
      <tags:smo_workDay name="calendarWork" action="js-smo_visit-findPolyAdmissions.do" title="Выбрать дату"/>
    </msh:sideMenu>
    <tags:visit_finds currentAction="workCalendar"/>
  </tiles:put>
  <tiles:put name="body" type="string">
  	<%
		String orderBy = request.getParameter("orderBy") ;
		if (orderBy!=null && !orderBy.equals("")) {
		    request.setAttribute("orderBy1"," p.lastname||' '||p.firstname||' '||p.middlename");
			request.setAttribute("orderBy2"," p.lastname||' '||p.firstname||' '||p.middlename");
		}
		else {
			request.setAttribute("orderBy1"," wct.timeFrom");
			request.setAttribute("orderBy2"," v.timeExecute");
		}
  	%>
	  <label><input type="checkbox" name="chbOrder" id="chbOrder" onclick="checkBox();"/><b>Сортировать по ФИО</b></label>
  	<msh:section title="Непринятые пациенты" >
  		<ecom:webQuery name="list_no" nativeSql="select v.id
  		,cast(wct.timeFrom as varchar(5)) as timeFrom
  		
  		,po.lastname||' '||po.firstname||' '||po.middlename||' ('||mlo.name||')' as pofio
  		,lpuo.name as lpuoname
  		,p.lastname||' '||p.firstname||' '||p.middlename||' г.р.'||to_char(p.birthday,'DD.MM.YYYY') as pfio
		
		,case when v.dateStart is not null then 'нет диагноза' when v.visitResult_id is null then '' else 'пред. оформлен' end as prerecord
		, list(distinct ms.code||' '||ms.name||(case when (smc.servicecomment is null or smc.servicecomment='') then '' else ' ('||smc.servicecomment||')' end)) as servicies
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
left join VocServiceStream vss on vss.id=v.serviceStream_id
where  v.datePlan_id='${calenDayId}' and (v.DTYPE='Visit' or v.DTYPE='ShortMedCase') and (v.noActuality is null or v.noActuality='0') and ((vss.isPaidConfirmation is null or vss.isPaidConfirmation ='0') or v.isPaid='1')
group by v.id,wct.timeFrom,v.dateStart,v.timeExecute,vwfe.isNoDiagnosis
,po.lastname,po.firstname,po.middlename,lpuo.name
,p.lastname,p.firstname,p.middlename,p.birthday
,pe.lastname,pe.firstname,pe.middlename
,vvr.name,v.visitResult_id,mlo.name
having (v.dateStart is null or (count(d.id)=0 and (vwfe.isNoDiagnosis is null or vwfe.isNoDiagnosis='0')))
order by ${orderBy1}"/>
	    <msh:table name="list_no" action="entitySubclassEdit-mis_medCase.do" idField="1">
	      <msh:tableColumn columnName="№" identificator="false" property="sn" />
	      <msh:tableColumn columnName="Доп. инф." property="6" />
	      <msh:tableColumn columnName="Направлен" property="2" />
	      <msh:tableColumn columnName="Пациент" property="5" />
	      <msh:tableColumn columnName="Услуги" property="7" />
	      <msh:tableColumn columnName="Кто направил" property="3" />
	      <msh:tableColumn columnName="Кем направлен" property="4" />
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
     
		<msh:table name="list_prerecord" action="js-smo_visit-showPreRecord.do" cellFunction="true" idField="1" hideTitle="false">
	      <msh:tableColumn columnName="общее кол-во"  identificator="false" property="2"  addParam="&"/>
	      <msh:tableColumn property="3" columnName="резерв кол-во" addParam="&"/>
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
order by ${orderBy2}"/>
<msh:table viewUrl="entityShortView-smo_visit.do" editUrl="entityParentEdit-smo_visit.do" name="list_yes" action="entityView-smo_visit.do" idField="1">
	      <msh:tableColumn columnName="№" identificator="false" property="sn" />
	      <msh:tableColumn columnName="Пациент" property="6" />
	      <msh:tableColumn columnName="Услуги" property="9" />
	      <msh:tableColumn columnName="Направлен" property="2" />
	      <msh:tableColumn columnName="Исполнен" identificator="false" property="3" />
	      <msh:tableColumn columnName="Кто направил" property="4" />
	      <msh:tableColumn columnName="Кем направлен" property="5" />
	      <msh:tableColumn columnName="Результат" identificator="false" property="8"/>
	      <msh:tableColumn columnName="Исполнитель" identificator="false" property="7" />
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
order by ${orderBy2}"/>
<msh:table viewUrl="entityShortView-smo_visit.do" editUrl="entityParentEdit-smo_visit.do" name="list_no_other" action="entityView-smo_visit.do" idField="1">
	      <msh:tableColumn columnName="№" identificator="false" property="sn" />
	      <msh:tableColumn columnName="Пациент" property="6" />
	      <msh:tableColumn columnName="Направлен" property="2" />
	      <msh:tableColumn columnName="Исполнен" identificator="false" property="3" />
	      <msh:tableColumn columnName="Кто направил" property="4" />
	      <msh:tableColumn columnName="Кем направлен" property="5" />
	      <msh:tableColumn columnName="Результат" identificator="false" property="8"/>
	      <msh:tableColumn columnName="Исполнитель" identificator="false" property="7" />
	    </msh:table>
  	</msh:section>
    
    
  </tiles:put>

</tiles:insert>

<script type='text/javascript'>
    //работа с группировкой
    function checkBox() {
        if (window.location.href.indexOf('.do?id')==-1) {
            window.location.href=
                window.location.href.indexOf('orderBy=1')==-1?
                    window.location.href.replace('.do?','.do?orderBy=1') :
                    window.location.href.replace('.do?orderBy=1','.do?');
		}
		else {
            window.location.href=
                window.location.href.indexOf('orderBy=1')==-1?
                    window.location.href.replace('.do?id=','.do?orderBy=1&id=') :
                    window.location.href.replace('orderBy=1&','');
		}
    }
    window.onload=function() {
        document.getElementById('chbOrder').checked=(window.location.href.indexOf('orderBy=1')!=-1);
    }
</script>
