<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://struts.apache.org/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://www.nuzmsh.ru/tags/msh" prefix="msh" %>
<%@ taglib uri="http://www.ecom-ast.ru/tags/ecom" prefix="ecom" %>

<tiles:insert page="/WEB-INF/tiles/mainLayout.jsp" flush="true" >

  <tiles:put name="title" type="string">
    <msh:title guid="helloItle-123" mainMenu="Patient">Журнал направлений</msh:title>
  </tiles:put>
  <tiles:put name="side" type="string">
    <msh:sideMenu guid="helloSideMenu-123">
      <msh:sideLink guid="helloSideLinkNew" roles="/Policy/Mis/MedCase/Direction/Create" key="ALT+N" action="/entityPrepareCreate-smo_direction" name="Направить" />
    </msh:sideMenu>
  </tiles:put>
  <tiles:put name="body" type="string">
    <msh:panel guid="e74eb606-5199-40da-831b-b90877f6ff84">
      <msh:row guid="97b4deaa-e5e1-484c-bc91-cb877d0b7db5">
        <msh:label property="worker" guid="19b91cd6-0022-43a7-8bbc-1e177443a627" label="Сотрудник" fromRequestScope="true" />
      </msh:row>
    </msh:panel>
    <msh:table name="list" action="entityView-smo_direction.do" idField="id" guid="b621e361-1e0b-4ebd-9f58-b7d919b45bd6">
      <msh:tableColumn columnName="Дата" property="date" guid="de1f591c-02b8-4875-969f-d2698689db5d" />
      <msh:tableColumn columnName="Время" property="time" guid="8410185d-711a-4851-bca4-913a77381989" />
      <msh:tableColumn columnName="Специалист" property="specialist" guid="6682eeef-105f-43a0-be61-30a865f27972" />
      <msh:tableColumn columnName="ФИО специалиста" property="doctor" guid="f34e1b12-3392-4978-b31f-5e54ff2e45bd" />
      <msh:tableColumn columnName="Пациент" property="patient" guid="315cb6eb-3db8-4de5-8b0c-a49e3cacf382" />
    </msh:table>
  </tiles:put>
</tiles:insert>

