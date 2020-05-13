<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://struts.apache.org/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://www.nuzmsh.ru/tags/msh" prefix="msh" %>
<%@ taglib uri="http://www.ecom-ast.ru/tags/ecom" prefix="ecom" %>

<tiles:insert page="/WEB-INF/tiles/mainLayout.jsp" flush="true" >

  <tiles:put name="title" type="string">
    <msh:title mainMenu="Diet">Блюдо</msh:title>
  </tiles:put>
  <tiles:put name="side" type="string">
    <msh:sideMenu title="Показать">
      <msh:sideLink params="id" action="/entityList-diet_diet" name="Диеты" title="Показать список диет" />
    </msh:sideMenu>
    <msh:sideMenu>
      <msh:sideLink roles="/Policy/Mis/Dish/Create" key="ALT+N" action="/entityPrepareCreate-diet_dish" name="Создать новое блюдо" />
      <msh:sideLink roles="/Policy/Mis/InvalidFood/VocFoodStuff/View" action="/diet_foodStuffs" name="Справочник продуктов питания" title="Показать справочник продуктов питания" />
    </msh:sideMenu>
  </tiles:put>
  <tiles:put name="body" type="string">
    <msh:table name="list" action="entityView-diet_dish.do" idField="id"  navigationAction="js-diet_dish-listTemplate.do">
      <msh:tableColumn columnName="№" property="dishNumber" />
      <msh:tableColumn columnName="Наименование" property="name" />
      <msh:tableColumn columnName="Выход, (г)" identificator="false" property="weight" />
      <msh:tableColumn columnName="Диета" property="dietsShortName" />
    </msh:table>
  </tiles:put>
</tiles:insert>

