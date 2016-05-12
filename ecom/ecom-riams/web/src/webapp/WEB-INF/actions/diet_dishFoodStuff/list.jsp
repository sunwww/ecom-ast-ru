<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://struts.apache.org/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://www.nuzmsh.ru/tags/msh" prefix="msh" %>
<%@ taglib uri="http://www.ecom-ast.ru/tags/ecom" prefix="ecom" %>

<tiles:insert page="/WEB-INF/tiles/mainLayout.jsp" flush="true" >

  <tiles:put name="title" type="string">
    <msh:title guid="helloItle-123" mainMenu="Diet">Список продуктов</msh:title>
  </tiles:put>
  <tiles:put name="side" type="string">
    <msh:sideMenu guid="352799f4-0a5e-475a-a3e1-e202756cce4d">
      <msh:sideLink roles="/Policy/Mis/DishFoodStuff/Create" key="ALT+N" action="/entityPrepareCreate-diet_dishFoodStuff" name="Добавить продукт" guid="a32b9fa1-4b2d-4762-8ee9-9a0814211412" />
    </msh:sideMenu>
  </tiles:put>
  <tiles:put name="body" type="string">
    <msh:table name="list" action="entityView-diet_dishFoodStuff.do" idField="id" guid="05185d85-1d31-4e70-8174-29492219211e">
      <msh:tableColumn columnName="ИД" property="id" guid="9cc14434-3929-4b9a-9892-008e42638d41" />
      <msh:tableColumn columnName="Наименование" property="name" guid="7cc19158-3122-425f-9f66-0de641c5c51c" />
    </msh:table>
  </tiles:put>
</tiles:insert>

