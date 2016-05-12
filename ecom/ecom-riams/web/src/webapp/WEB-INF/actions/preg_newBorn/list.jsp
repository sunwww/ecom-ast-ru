<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://struts.apache.org/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://www.nuzmsh.ru/tags/msh" prefix="msh" %>
<%@ taglib uri="http://www.ecom-ast.ru/tags/ecom" prefix="ecom" %>

<tiles:insert page="/WEB-INF/tiles/mainLayout.jsp" flush="true" >

  <tiles:put name="title" type="string">
    <ecom:titleTrail beginForm="preg_childBirthForm" mainMenu="Patient" title="Данные о новорожденном" guid="e6e004a5-17a7-4dc5-87eb-f933b1150a4c" />
  </tiles:put>
  <tiles:put name="body" type="string">
    <msh:table name="list" action="entityParentView-preg_newBorn.do" idField="id" guid="ff14544d-88e5-4c0d-bc00-9987a2698a24">
      <msh:tableColumn columnName="Дата рождения" property="birthDate" guid="724d2c83-221e-4a44-9144-5346fa8fefd2" />
      <msh:tableColumn columnName="Время рождения" property="birthTime" guid="17991748-77de-4b16-8c94-740bbfa10e7a" />
    </msh:table>
  </tiles:put>
  <tiles:put name="side" type="string">
    <msh:sideMenu title="Добавить" guid="d05af144-f458-45dd-a3eb-94bb77eb2007">
      <msh:sideLink params="id" action="/entityParentPrepareCreate-preg_newBorn" name="Создать" roles="/Policy/Mis/NewBorn/Create" guid="71d2536e-c475-4b91-975c-a3440b2a6afa" />
    </msh:sideMenu>
  </tiles:put>
</tiles:insert>

