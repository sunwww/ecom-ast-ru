<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://struts.apache.org/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://www.nuzmsh.ru/tags/msh" prefix="msh" %>
<%@ taglib uri="http://www.ecom-ast.ru/tags/ecom" prefix="ecom" %>

<tiles:insert page="/WEB-INF/tiles/mainLayout.jsp" flush="true" >

  <tiles:put name="title" type="string">
    <msh:title guid="helloItle-123" mainMenu="Patient">Проба</msh:title>
  </tiles:put>
  <tiles:put name="side" type="string">
    <msh:sideMenu guid="helloSideMenu-123">
      <msh:sideLink guid="helloSideLinkNew" roles="/Policy/IdeMode/Hello/Create" key="ALT+N" action="/entityPrepareCreate-smo_smo" name="Создать новое" />
    </msh:sideMenu>
  </tiles:put>
  <tiles:put name="body" type="string">
    <msh:table name="list" action="entityView-smo_smo.do" idField="id" guid="b621e361-1e0b-4ebd-9f58-b7d919b45bd6">
      <msh:tableColumn columnName="Номер" property="id" guid="0694f6a7-ed40-4ebf-a274-1efd6901cfe4" />
      <msh:tableColumn columnName="Дата открытия" property="dateStart" guid="6682eeef-105f-43a0-be61-30a865f27972" />
      <msh:tableColumn columnName="Кем открыт" property="parent" guid="f34e1b12-3392-4978-b31f-5e54ff2e45bd" />
      <msh:tableColumn columnName="Дата закрытия" property="hello" guid="1408921e-752c-4f20-bd68-b2488317fe87" />
      <msh:tableColumn columnName="Кем закрыт" property="hello" guid="b794b147-9ae6-475e-a94a-97777f52e869" />
      <msh:tableColumn columnName="Владелец" property="hello" guid="96f2908c-9f84-41f7-b504-4347226e5df2" />
      <msh:tableColumn columnName="Количество визитов" property="link" guid="95bddc56-ad9c-4745-b92e-85909b683d79" />
      <msh:tableColumn columnName="Количество дней" property="link" guid="72726d1c-839d-40d1-b9cd-c2347263543c" />
    </msh:table>
  </tiles:put>
</tiles:insert>

