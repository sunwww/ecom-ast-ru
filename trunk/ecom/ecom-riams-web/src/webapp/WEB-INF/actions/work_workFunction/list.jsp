<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://struts.apache.org/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://www.nuzmsh.ru/tags/msh" prefix="msh" %>
<%@ taglib uri="http://www.ecom-ast.ru/tags/ecom" prefix="ecom" %>

<tiles:insert page="/WEB-INF/tiles/mainLayout.jsp" flush="true" >

  <tiles:put name="title" type="string">
    <ecom:titleTrail beginForm="mis_workerForm" mainMenu="Lpu" guid="14568ace-1821-4897-841f-b9b42d93026d" title="Список рабочих функций" />
  </tiles:put>
  <tiles:put name="side" type="string">
    <msh:sideMenu>
      <msh:sideLink roles="/Policy/Mis/Worker/WorkFunction/Create" key="ALT+3" action="/entityParentPrepareCreate-work_groupWorkFunction" name="Создать новую групповую функцию" params="id" />
      <msh:sideLink roles="/Policy/Mis/Worker/WorkFunction/Create" key="ALT+2" action="/entityParentPrepareCreate-work_personalWorkFunction" name="Создать новую функцию специалиста" params="id" />
    </msh:sideMenu>
  </tiles:put>
  <tiles:put name="body" type="string">
    <msh:table name="list" action="entitySublassView-work_workFunction.do" idField="id" guid="b621e361-1e0b-4ebd-9f58-b7d919b45bd6">
      <msh:tableColumn columnName="ИД" property="id" guid="0694f6a7-ed40-4ebf-a274-1efd6901cfe4" />
      <msh:tableColumn columnName="Функция" property="name" guid="6682eeef-105f-43a0-be61-30a865f27972" />
      <msh:tableColumn columnName="Сотрудник" property="workerInfo" guid="f34e1b12-3392-4978-b31f-5e54ff2e45bd" />
    </msh:table>
  </tiles:put>
</tiles:insert>

