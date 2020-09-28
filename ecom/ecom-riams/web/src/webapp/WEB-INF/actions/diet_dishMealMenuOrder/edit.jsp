<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://struts.apache.org/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://www.nuzmsh.ru/tags/msh" prefix="msh" %>
<%@ taglib uri="http://www.ecom-ast.ru/tags/ecom" prefix="ecom" %>

<tiles:insert page="/WEB-INF/tiles/mainLayout.jsp" flush="true">

  <tiles:put name="body" type="string">
    <msh:form action="/entityParentSaveGoParentView-diet_dishMealMenuOrder.do" defaultField="menu">
      <msh:hidden property="id" />
      <msh:hidden property="saveType" />
      <!--      <msh:hidden property="dish" />-->
      <msh:hidden property="menu" />
      <msh:panel>
        <msh:autoComplete vocName="Dish" property="dish" label="Блюдо" size="50" />
        <msh:submitCancelButtonsRow colSpan="6" />
      </msh:panel>
    </msh:form>
  </tiles:put>
  <tiles:put name="title" type="string">
    <ecom:titleTrail mainMenu="Diet" beginForm="diet_dishMealMenuOrderForm" />
  </tiles:put>
  <tiles:put name="side" type="string">
    <msh:ifFormTypeIsView formName="diet_dishMealMenuOrderForm">
      <msh:sideMenu title="Заказ блюда меню">
        <msh:sideLink key="ALT+2" params="id" action="/entityEdit-diet_dishMealMenuOrder" name="Изменить" roles="/Policy/Mis/InvalidFood/DishMealMenuOrder/Edit" />
        <msh:sideLink key="ALT+DEL" confirm="Удалить?" params="id" action="/entityParentDeleteGoParentView-diet_dishMealMenuOrder" name="Удалить" roles="/Policy/Mis/InvalidFood/DishMealMenuOrder/Delete" />
        <msh:sideLink params="id" action="/entityParentListRedirect-diet_dishMealMenuOrder" name="Список блюд меню" />
      </msh:sideMenu>
      <msh:sideMenu title="Дополнительно">
        <msh:sideLink action="/entityList-diet_diet" name="Список всех диет" />
        <msh:sideLink roles="/Policy/Mis/Dish/View" action="/entityList-diet_dish" name="Справочник блюд" />
        <msh:sideLink roles="/Policy/Mis/InvalidFood/VocFoodStuff/View" action="/diet_foodStuffs" name="Справочник продуктов питания" title="Показать справочник продуктов питания" />
        <msh:sideLink params="" action="/entityList-diet_mealMenuTemplate" name="Шаблоны-меню" title="Просмотр списка шаблонов меню" />
      </msh:sideMenu>
    </msh:ifFormTypeIsView>
  </tiles:put>
</tiles:insert>

