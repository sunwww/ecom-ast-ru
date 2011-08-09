<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://struts.apache.org/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://www.nuzmsh.ru/tags/msh" prefix="msh" %>
<%@ include file="/WEB-INF/tiles/header.jsp" %>

<tiles:insert page="/WEB-INF/tiles/mainLayout.jsp" flush="true" >

  <tiles:put name="title" type="string">
    <ecom:titleTrail beginForm="mis_lpuForm" mainMenu="Lpu" title="Список сотрудников" guid="e51b1bad-82ba-4906-9829-7d9148b1174a" />
  </tiles:put>
  <tiles:put name="side" type="string">
    <msh:sideMenu guid="360e85c3-7aa1-4a04-8c1d-a9c0a6739efa" title="Добавить">
      <msh:sideLink key="ALT+N" params="id" action="/entityParentPrepareCreate-mis_worker" name="Добавить сотрудника" guid="0fd18715-d91c-422d-87e7-aafe9a3c0ca8" roles="/Policy/Mis/Worker/Worker/Create" />
    </msh:sideMenu>
    <msh:sideMenu title="Перейти" guid="720b3f9b-02cd-4235-a1ba-dc12a6dc356b">
      <msh:sideLink roles="/Policy/Mis/Worker/WorkFunction/Create" key="ALT+2" params="id" action="/entityParentList-work_groupWorkFunction" name="К списку рабочих групп" title="Перейти к списку рабочих групп" guid="712b4156-54e2-4e7b-b0da-a46821eba3de" />
      <msh:sideLink roles="/Policy/Mis/Worker/WorkFunction/View" key="ALT+3" params="id" action="/js-mis_worker-archives" name="К списку сотрудников архивных" title="Перейти к списку архивных рабочих функций" guid="712b4156-54e2-4e7b-b0da-a46821eba3de" />
      <msh:sideLink roles="/Policy/Mis/Worker/WorkFunction/View" key="ALT+4" params="id" action="/js-mis_worker-running" name="К списку сотрудников действующих" title="Перейти к списку действующих рабочих функций" guid="712b4156-54e2-4e7b-b0da-a46821eba3de" />
      <msh:sideLink roles="/Policy/Mis/Worker/WorkFunction/View" key="ALT+5" params="id" action="/js-mis_worker-empty" name="К списку сотрудников без раб.функции" title="Перейти к списку сотрудников без раб.функции" guid="712b4156-54e2-4e7b-b0da-a46821eba3de" />
      <msh:sideLink roles="/Policy/Mis/Worker/WorkFunction/View" key="ALT+6" params="id" action="/js-mis_worker-all" name="К полному списку сотрудников" title="Перейти к полному списку сотрудников" guid="712b-54e2-4e7b-b0da-a46821eba3de" />
      <msh:sideLink roles="/Policy/Mis/Worker/WorkFunction/View" key="ALT+7" params="id" action="/js-mis_worker-pattern" name="Шаблоны расписания сотрудников" title="Перейти к установке шаблонов календарей по специалистам" guid="712b-54e2-4e7b-b0da-a46821eba3de" />
    </msh:sideMenu>
  </tiles:put>
  <tiles:put name="body" type="string">
  	<ecom:webQuery name="listArch" nativeSql="select w.id,wp.lastname||' '||wp.firstname||' '||wp.middlename, vwf.name as vwfname, gr.groupName as grgroupName from workfunction wf left join worker w on w.id=wf.worker_id left join Patient wp on wp.id=w.person_id left join VocWorkFunction vwf on vwf.id=wf.workFunction_id left join WorkFunction gr on gr.id=wf.group_id where w.lpu_id=${param.id} and cast(wf.archival as integer)=1"/>
    <msh:table name="listArch" action="entityParentView-mis_worker.do" idField="1" guid="d20ae6f6-f534-4d56-affe-ff02d3034d32">
      <msh:tableColumn columnName="#" property="sn" guid="4797" />
      <msh:tableColumn columnName="ФИО" property="2" guid="4ceb96e" />
      <msh:tableColumn columnName="Должностные обязанности" property="3"/>
      <msh:tableColumn columnName="Групповая функция" property="4"/>
    </msh:table>
  </tiles:put>
</tiles:insert>

