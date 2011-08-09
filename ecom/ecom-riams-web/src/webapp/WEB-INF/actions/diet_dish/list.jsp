<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://struts.apache.org/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://www.nuzmsh.ru/tags/msh" prefix="msh" %>
<%@ taglib uri="http://www.ecom-ast.ru/tags/ecom" prefix="ecom" %>

<tiles:insert page="/WEB-INF/tiles/mainLayout.jsp" flush="true" >

  <tiles:put name="title" type="string">
    <msh:title guid="helloItle-123" mainMenu="Diet">Блюдо</msh:title>
  </tiles:put>
  <tiles:put name="side" type="string">
    <msh:sideMenu title="Показать" guid="b44bnfdf5659-2a4a-4e49-9700-31b3a7eb150f">
      <msh:sideLink params="id" action="/entityList-diet_diet" name="Диеты" title="Показать список диет" guid="325vtw0fe37-f85c-4e87-b447-4124027e2e4e" />
    </msh:sideMenu>
    <msh:sideMenu guid="helloSideMenu-123">
      <msh:sideLink guid="helloSideLinkNew" roles="/Policy/Mis/Dish/Create" key="ALT+N" action="/entityPrepareCreate-diet_dish" name="Создать новое блюдо" />
      <msh:sideLink roles="/Policy/Mis/InvalidFood/VocFoodStuff/View" action="/diet_foodStuffs" name="Справочник продуктов питания" title="Показать справочник продуктов питания" guid="dc0cbf2a-eb01-4812-9015-4b436a21353e" />
    </msh:sideMenu>
  </tiles:put>
  <tiles:put name="body" type="string">
    <msh:table name="list" action="entityView-diet_dish.do" idField="id"  navigationAction="js-diet_dish-listTemplate.do" guid="b621e361-1e0b-4ebd-9f58-b7d919b4">
      <msh:tableColumn columnName="№" property="dishNumber" guid="0694f6a7-ed40-4ebf-a274-1efd69" />
      <msh:tableColumn columnName="Наименование" property="name" guid="6682eeef-105f-43a0-be61-30a86" />
      <msh:tableColumn columnName="Выход, (г)" identificator="false" property="weight" guid="d9f64477-c64d-4748-bbee-30fd6b158b3b" />
      <msh:tableColumn columnName="Диета" property="dietsShortName" guid="f34e1b12-3392-4978-b31f" />
    </msh:table>
  </tiles:put>
</tiles:insert>

