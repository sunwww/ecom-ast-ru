<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://struts.apache.org/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://www.nuzmsh.ru/tags/msh" prefix="msh" %>
<%@ include file="/WEB-INF/tiles/header.jsp" %>

<tiles:insert page="/WEB-INF/tiles/mainLayout.jsp" flush="true" >

  <tiles:put name="title" type="string">
    <ecom:titleTrail beginForm="mis_lpuForm" mainMenu="Lpu" title="Список сотрудников" />
  </tiles:put>
  <tiles:put name="side" type="string">
    <msh:sideMenu title="Добавить">
      <msh:sideLink key="ALT+N" params="id" action="/entityParentPrepareCreate-mis_worker" name="Добавить сотрудника" roles="/Policy/Mis/Worker/Worker/Create" />
    </msh:sideMenu>
    <msh:sideMenu title="Перейти">
      <msh:sideLink roles="/Policy/Mis/Worker/WorkFunction/Create" key="ALT+2" params="id" action="/entityParentList-work_groupWorkFunction" name="К списку рабочих групп" title="Перейти к списку рабочих групп" />
      <msh:sideLink roles="/Policy/Mis/Worker/WorkFunction/View" key="ALT+3" params="id" action="/js-mis_worker-archives" name="К списку архивных рабочих групп" title="Перейти к списку архивных рабочих функций" />
      <msh:sideLink roles="/Policy/Mis/Worker/WorkFunction/View" key="ALT+4" params="id" action="/js-mis_worker-running" name="К списку действующих рабочих групп" title="Перейти к списку действующих рабочих функций" />
      <msh:sideLink roles="/Policy/Mis/Worker/WorkFunction/View" key="ALT+5" params="id" action="/js-mis_worker-empty" name="К списку сотрудников без раб.функции" title="Перейти к списку сотрудников без раб.функции" />
      <msh:sideLink roles="/Policy/Mis/Worker/WorkFunction/View" key="ALT+6" params="id" action="/js-mis_worker-all" name="К полному списку сотрудников" title="Перейти к полному списку сотрудников" />
      <msh:sideLink roles="/Policy/Mis/Worker/WorkFunction/View" key="ALT+7" params="id" action="/js-mis_worker-pattern" name="Шаблоны расписания сотрудников" title="Перейти к установке шаблонов календарей по специалистам" />
    </msh:sideMenu>
  </tiles:put>
  <tiles:put name="body" type="string">
  	<ecom:webQuery name="listArch" nativeSql="select w.id,wp.lastname||' '||wp.firstname||' '||wp.middlename as fio
  	, case when wf.workFunction_id is not null then (select vwf.name from VocWorkFunction vwf where vwf.id=wf.workFunction_id) else '' end,case when wf.group_id is not null then (select gr.groupname from workfunction gr where gr.id=wf.group_id) else '' end as groufunc
  	, case when wf.archival='1' then 'Да' else 'Нет' end as isarch
  	, case when wf.archival='1' then 'color:red' else '' end as isarchStyle
  	, wpl.name as cab
  	from worker w 
  	left join workFunction wf on w.id=wf.worker_id 
  	left join Patient wp on wp.id=w.person_id
    left join workplace_workfunction wpwf on wpwf.workfunctions_id=wf.id
    left join workplace wpl on wpl.id=wpwf.workplace_id
  	where w.lpu_id=${param.id}
  	order by wp.lastname,wp.firstname,wp.middlename
  	"/>
    <msh:table styleRow="6" name="listArch" action="entityParentView-mis_worker.do" idField="1">
      <msh:tableColumn columnName="#" property="sn" />
      <msh:tableColumn columnName="ФИО" property="2" />
      <msh:tableColumn columnName="Должностные обязанности" property="3"/>
      <msh:tableColumn columnName="Групповая функция" property="4"/>
      <msh:tableColumn columnName="Архив" property="5"/>
      <msh:tableColumn columnName="Кабинет" property="7"/>
    </msh:table>
  </tiles:put>
</tiles:insert>

