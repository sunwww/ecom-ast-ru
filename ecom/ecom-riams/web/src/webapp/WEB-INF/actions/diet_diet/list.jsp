<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://struts.apache.org/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://www.nuzmsh.ru/tags/msh" prefix="msh" %>
<%@ taglib uri="http://www.ecom-ast.ru/tags/ecom" prefix="ecom" %>

<tiles:insert page="/WEB-INF/tiles/mainLayout.jsp" flush="true" >

  <tiles:put name="title" type="string">
    <msh:title mainMenu="Diet" title="Диеты">Диета</msh:title>
  </tiles:put>
  <tiles:put name="side" type="string">
    <msh:sideMenu title="Добавить">
      <msh:sideLink key="ALT+N" action="/entityPrepareCreate-diet_diet" name="Добавить диету" roles="/Policy/Mis/Diet/Create"  />
    </msh:sideMenu>
    <msh:sideMenu title="Дополнительно">
      <msh:sideLink action="/entityList-diet_diet" name="⇧Список всех диет" />
      <msh:sideLink roles="/Policy/Mis/Dish/View" action="/entityList-diet_dish" name="Справочник блюд" />
      <msh:sideLink roles="/Policy/Mis/InvalidFood/VocFoodStuff/View" action="/diet_foodStuffs" name="Справочник продуктов питания" title="Показать справочник продуктов питания" />
      <msh:sideLink action="/entityList-diet_mealMenuTemplate" name="Шаблоны-меню" title="Просмотр списка шаблонов меню" />
      <msh:sideLink action="/diet_dietJournal.do" name="Журнал назначенных диет" title="Просмотр журнала назначенных диет" />
    </msh:sideMenu>
  </tiles:put>
  <tiles:put name="body" type="string">
    <msh:table name="list" action="entityView-diet_diet.do" idField="id">
      <msh:tableColumn columnName="ИД" property="id" />
      <msh:tableColumn columnName="Название" property="name" />
      <msh:tableColumn columnName="Аббревиатура" property="shortName" />
    </msh:table>
  </tiles:put>
</tiles:insert>

