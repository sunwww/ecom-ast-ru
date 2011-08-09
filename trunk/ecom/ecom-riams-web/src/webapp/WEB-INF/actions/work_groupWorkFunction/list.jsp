<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://struts.apache.org/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://www.nuzmsh.ru/tags/msh" prefix="msh" %>
<%@ taglib uri="http://www.ecom-ast.ru/tags/ecom" prefix="ecom" %>

<tiles:insert page="/WEB-INF/tiles/mainLayout.jsp" flush="true" >

  <tiles:put name="title" type="string">
    <ecom:titleTrail beginForm="mis_lpuForm" mainMenu="Lpu" guid="14568ace-1821-4897-841f-b9b42d93026d" title="Список рабочих групп" />
  </tiles:put>
  <tiles:put name="side" type="string">
    <msh:sideMenu title="Добавить" guid="1665955f-37a4-4bde-bf54-8089600901ac">
      <msh:sideLink roles="/Policy/Mis/Worker/WorkFunction/Create" key="ALT+N" action="/entityParentPrepareCreate-work_groupWorkFunction" name="Групповая функция" params="id" title="Добавить новую групповую функцию" guid="9eb8a6c2-eb12-46cb-9ba9-68c3f5ae84ac" />
    </msh:sideMenu>
    <msh:sideMenu title="Перейти" guid="a493c808-be07-418e-a082-a5eeca2d2f02">
      <msh:sideLink roles="/Policy/Mis/Worker/WorkFunction/View" key="ALT+2" action="/entityParentList-mis_worker" name="К списку сотрудников" title="Перейти к списку сотрудников" guid="93a41d40-bb79-45ff-a8e7-d45241d0fc3f" params="id" />
    </msh:sideMenu>
  </tiles:put>
  <tiles:put name="body" type="string">
    <msh:table name="list" action="entityView-work_groupWorkFunction.do" idField="id" guid="b621e361-1e0b-4ebd-9f58-b7d919b45bd6">
      <msh:tableColumn columnName="Название" property="groupName" guid="6682eeef-105f-43a0-be61-30a865f27972" />
      <msh:tableColumn columnName="Функция" property="name" guid="f34e1b12-3392-4978-b31f-5e54ff2e45bd" />
    </msh:table>
  </tiles:put>
</tiles:insert>

