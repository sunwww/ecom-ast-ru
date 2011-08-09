<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://struts.apache.org/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://www.nuzmsh.ru/tags/msh" prefix="msh" %>
<%@ taglib uri="http://www.ecom-ast.ru/tags/ecom" prefix="ecom" %>

<tiles:insert page="/WEB-INF/tiles/mainLayout.jsp" flush="true" >

  <tiles:put name="title" type="string">
    <msh:title mainMenu="Config" title="Разрешения пользователя" guid="5d110f49-20c3-4699-b6d0-b657032a8fae" />
  </tiles:put>
  <tiles:put name="body" type="string">
    <msh:table name="list" action="entityView-sec_userPermission.do" idField="id" guid="34ddbeac-1d7e-4552-a53a-c6938c9f8bad">
      <msh:tableColumn columnName="Дата начала" property="dateFrom" guid="34416cd7-307b-4963-9bec-4c3a9a7527af" />
      <msh:tableColumn columnName="Дата окончания" property="dateTo" guid="34416cd7-307b-4963-9bec-4c3a9a7527af" />
      <msh:tableColumn columnName="Объект" property="objectInfo" guid="83eb9636-f2a9-4377-a4e7-58fc0f46f748" />
      <msh:tableColumn columnName="Разрешение" property="permissionInfo" guid="4b323854-8fa1-41e0-b959-632b342cd506" />
      <msh:tableColumn columnName="Id объекта" property="idObject" guid="4b323854-8fa1-41e0-b959-632b342cd506" />
      <msh:tableColumn columnName="Пользователь" property="userInfo" guid="4b323854-8fa1-41e0-b959-632b342cd506" />
      <msh:tableColumn columnName="Кто дал разрешение" property="createUsername" guid="4b323854-8fa1-41e0-b959-632b342cd506" />
      <msh:tableColumn columnName="Когда" property="createDate" guid="4b323854-8fa1-41e0-b959-632b342cd506" />
    </msh:table>
  </tiles:put>
  <tiles:put name="side" type="string">
    <msh:sideMenu title="Добавить" guid="b1b57fd2-b9ed-4760-b54e-f59b245e755a">
      <msh:sideLink params="" action="/entityPrepareCreate-sec_userPermission" name="Новое разрешение" title="Добавить новое разрешение" roles="/Policy/Jaas/Permission/User/Create" guid="cd3d23c1-1cfe-42ec-b53d-6e45f7555073" />
    </msh:sideMenu>
  </tiles:put>
</tiles:insert>

