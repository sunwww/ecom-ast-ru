<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://struts.apache.org/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://www.nuzmsh.ru/tags/msh" prefix="msh" %>
<%@ taglib uri="http://www.ecom-ast.ru/tags/ecom" prefix="ecom" %>

<tiles:insert page="/WEB-INF/tiles/mainLayout.jsp" flush="true">

  <tiles:put name="body" type="string">
    <!-- 
    	  - Проба
    	  -->
    <msh:form action="/entitySaveGoView-diet_dishMealMenu.do" defaultField="hello">
      <msh:hidden property="id" />
      <msh:hidden property="saveType" />
      <!--      <msh:hidden property="dish" />-->
      <msh:hidden property="menu" />
      <msh:panel>
        <msh:row />
        <msh:row />
        <msh:autoComplete vocName="Dish" property="dish" label="Блюдо" size="50" />
        <msh:submitCancelButtonsRow colSpan="6" />
      </msh:panel>
    </msh:form>
  </tiles:put>
  <tiles:put name="title" type="string">
    <ecom:titleTrail mainMenu="Diet" beginForm="diet_mealMenuTemplateForm" />
  </tiles:put>
  <tiles:put name="side" type="string">
    <msh:sideMenu title="Меню-заказ">
      <msh:sideLink key="ALT+2" params="id" action="/entityEdit-diet_dishMealMenu" name="Изменить" roles="/Policy/Mis/InvalidFood/DishMealMenu/Edit" />
      <msh:sideLink key="ALT+N" params="id" action="/entityParentPrepareCreate-diet_dishMealMenu" name="Добавить блюдо" roles="/Policy/Mis/InvalidFood/DishMealMenu/Create" />
      <msh:sideLink key="ALT+DEL" confirm="Удалить?" params="id" action="/entityDelete-diet_dishMealMenu" name="Удалить" roles="/Policy/Mis/InvalidFood/DishMealMenu/Delete" />
    </msh:sideMenu>
  </tiles:put>
</tiles:insert>

