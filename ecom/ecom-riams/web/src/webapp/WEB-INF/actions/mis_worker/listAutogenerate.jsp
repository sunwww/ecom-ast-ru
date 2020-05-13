<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://struts.apache.org/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://www.nuzmsh.ru/tags/msh" prefix="msh" %>
<%@ include file="/WEB-INF/tiles/header.jsp" %>

<tiles:insert page="/WEB-INF/tiles/mainLayout.jsp" flush="true" >

  <tiles:put name="title" type="string">
    <msh:title>Сотрудники, у которых установлена автоматическая генерация</msh:title>
  </tiles:put>
  <tiles:put name="side" type="string">
  <%  if (request.getParameter("noAutogenerate")!=null 
  && request.getParameter("noAutogenerate").equals("1")) {
	  request.setAttribute("autoG1", "selected") ;
	  request.setAttribute("autoG2", "") ;
  } else {
	  request.setAttribute("autoG1", "") ;
	  request.setAttribute("autoG2", "selected") ;
  } %>
    <msh:sideMenu title="Списки рабочих функций" >
      <msh:sideLink styleId="${autoG1}" action="/js-mis_worker-autogenerate.do?noAutogenerate=1" name="Нет автогенерации" roles="/Policy/Mis/Worker/Worker/Create" />
      <msh:sideLink styleId="${autoG2}" action="/js-mis_worker-autogenerate.do?" name="Автогенерация"  roles="/Policy/Mis/Worker/Worker/Create" />
    </msh:sideMenu>
    <msh:sideMenu title="Дополнительно">
      <msh:sideLink  action="/cal_workCalendar-journal.do?functionJournal=autogenerate" name="Запуск генерации" confirm="Вы точно хотите запустить автогенерацию?" roles="/Policy/Mis/Worker/Worker/Create" />
    </msh:sideMenu>
    
  </tiles:put>
  <tiles:put name="body" type="string">
  <%
  if (request.getParameter("noAutogenerate")!=null 
  && request.getParameter("noAutogenerate").equals("1")) {
	  request.setAttribute("autogenerateSql", "(wc.autogenerate='0' or wc.autogenerate is null)") ;
  } else {
	  request.setAttribute("autogenerateSql", "wc.autogenerate='1'") ;
  }
  %>
  <msh:section title="Рабочие функции">
  	<ecom:webQuery name="listPerson" nativeSql="
  	select wf.id
  	,coalesce(wp.lastname||' '||wp.firstname||' '||wp.middlename, wf.groupName,'нет данных') as i2nfowf
  	, vwf.name as v3wfname
  	,wc.id as w4cid ,wf.code
  	,case when wc.autogenerate='1' then 'автоматически генерировать' end as a6utogenerate
  	,case when wc.autoGenerate='1' then wc.id else null end as i7sAuto 
  	,case when wc.autoGenerate='1' then null else wc.id end as i8sNoAuto 
  	,(select max(wcd.calendardate) from workcalendarday wcd where wcd.workcalendar_id=wc.id) as maxdate
  	,coalesce(ml.name,ml1.name) as mlname
  	from workfunction wf 
  	left join worker w on w.id=wf.worker_id 
  	left join Patient wp on wp.id=w.person_id 
  	left join WorkFunction gr on gr.id=wf.group_id
  	 left join WorkCalendar wc on wc.workFunction_id=wf.id  
  	 left join VocWorkFunction vwf on vwf.id=wf.workFunction_id 
  	 left join MisLpu ml on ml.id=wf.lpu_id
  	 left join MisLpu ml1 on ml1.id=w.lpu_id
  	 where ${autogenerateSql}

  	 and (wf.archival is null or wf.archival='0') 
  	 and wf.group_id is null
  	 and wc.id is not null
  	 order by coalesce(ml.name,ml1.name),wf.groupName, wp.lastname,wp.middlename,wp.firstname
  	 "/>
  	 
    <msh:table selection="true" viewUrl="entitySubclassShortView-work_workFunction.do" name="listPerson" action="entitySubclassView-work_workFunction.do" idField="1">
      <msh:tableColumn columnName="#" property="sn" />
      <msh:tableColumn columnName="Код" property="5" />
      <msh:tableColumn columnName="Подразделение" property="10" />
      
      <msh:tableColumn columnName="ФИО (Название группы)" property="2" />
      <msh:tableColumn columnName="Должностные обязанности" property="3"/>
      <msh:tableColumn columnName="Рабочий календарь" property="4"/>
      <msh:tableButton property="7" hideIfEmpty="true" buttonFunction="setAutogenerate" addParam="'0'" buttonName="Снять автоматическую генерацию по календарю" buttonShortName="СА" role="/Policy/Mis/Worker/WorkCalendar/Edit"/>
      <msh:tableButton property="8" hideIfEmpty="true" buttonFunction="setAutogenerate" addParam="'1'" buttonName="Установить автоматическую генерацию по календарю" buttonShortName="УА" role="/Policy/Mis/Worker/WorkCalendar/Edit"/>
      <msh:tableColumn columnName="Примечания" property="6"/>
      <msh:tableColumn columnName="Последняя сгенерированная дата приема" property="9"/>
    </msh:table>
  </msh:section>
  
  </tiles:put>
  <tiles:put name="javascript" type="string">
  	<script type="text/javascript" src="./dwr/interface/WorkCalendarService.js"></script>
  	<script type="text/javascript">
  		if ($('beginDate')) new dateutil.DateField($('beginDate')) ;
  		if ($('finishDate')) new dateutil.DateField($('finishDate')) ;
  		function setAutogenerate(aWC,aVal) {
  			WorkCalendarService.setAutogenerateByWorkCalendar(aWC,aVal,{
  				callback: function(aResult) {
  					window.location.reload() ;
  				}
  			});
  		}
  		
  	</script>
  </tiles:put>
</tiles:insert>

