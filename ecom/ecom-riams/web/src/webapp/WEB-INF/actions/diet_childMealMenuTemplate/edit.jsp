<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://struts.apache.org/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://www.nuzmsh.ru/tags/msh" prefix="msh" %>
<%@ taglib uri="http://www.ecom-ast.ru/tags/ecom" prefix="ecom" %>
<%@ taglib tagdir="/WEB-INF/tags" prefix="tags" %>
<%@ taglib tagdir="/WEB-INF/tags/diet" prefix="diet" %>

<tiles:insert page="/WEB-INF/tiles/mainLayout.jsp" flush="true">

  <tiles:put name="body" type="string">
    <!-- 
    	  - Проба
    	  -->
    <msh:form guid="formHello" action="entitySaveGoView-diet_childMealMenuTemplate.do" defaultField="hello">
      <msh:hidden guid="hiddenId" property="id" />
      <msh:hidden guid="hiddenSaveType" property="saveType" />
      <msh:hidden property="parentMenu" guid="d15b1c3e-d4b1-483f-8ec3-74daac944606" />
      <msh:panel guid="panel">
        <msh:row guid="e439f1c0-ae72-48ad-9086-b54619a6cb1d">
          <msh:autoComplete vocName="vocMealTime" property="mealTime" guid="84add1e7-0a81-4762-9864-fbff" horizontalFill="true" fieldColSpan="3" />
        </msh:row>
        <msh:submitCancelButtonsRow guid="submitCancel" colSpan="6" />
      </msh:panel>
    </msh:form>
    <msh:ifFormTypeIsView formName="diet_childMealMenuTemplateForm" guid="4403e-3dda-408e-85f8-78081f">
      <msh:section guid="c5be3-5330-4589-8da8-7888453a" title="Список блюд ">
        <ecom:parentEntityListAll formName="diet_dishMealMenuTemplateForm" attribute="dishes" guid="c507-4d9b-4ddc-99f5-5e3a07b48447" />
        <msh:table hideTitle="false" idField="id" name="dishes" action="entityParentView-diet_dishMealMenuTemplate.do" guid="aa435-03a5-43de-8754-3fa4d54ede17">
          <msh:tableColumn identificator="false" property="dishName" guid="999ef32d-be02-4726-a1ab-e3a4d1f76c46" columnName="Название блюда" />
        </msh:table>
      </msh:section>
    </msh:ifFormTypeIsView>
  </tiles:put>
  <tiles:put name="title" type="string">
    <ecom:titleTrail guid="titleTrail-123" mainMenu="Diet" beginForm="diet_childMealMenuTemplateForm" />
  </tiles:put>
  <tiles:put name="side" type="string">
    <msh:ifFormTypeIsView formName="diet_childMealMenuTemplateForm" guid="fd86b2e8-e15a-4a0d-8f55-cce2ce862b44">
		<diet:templateDietDish record="1" parentId="${param.id}" name="add" />
		<msh:sideMenu guid="sideMenu-123" title="Меню-шаблон">
        <msh:sideLink action="/entityParentListRedirect-diet_mealMenuTemplate" name="⇧Список меню-шаблонов текущего ЛПУ" guid="2b27f74e-c1c6-4858-88ad-9c94d3867f15" params="id" roles="/Policy/Mis/InvalidFood/MealMenuTemplate/View" />
        <msh:sideLink guid="sideLinkEdit" key="ALT+2" params="id" action="/entityEdit-diet_mealMenuTemplate" name="Изменить" roles="/Policy/Mis/InvalidFood/MealMenuTemplate/Edit" />
        <msh:sideLink guid="sideLinkDelete" key="ALT+DEL" confirm="Удалить?" params="id" action="/entityDelete-diet_mealMenuTemplate" name="Удалить" roles="/Policy/Mis/InvalidFood/MealMenuTemplate/Delete" />
      </msh:sideMenu>
      <msh:sideMenu title="Добавить" guid="e9273674-e802-416d-a568-afa1fdcc46a8">
        <msh:sideLink params="id" action="/entityParentPrepareCreate-diet_dishMealMenuTemplate" name="Блюдо в меню" guid="839b28fe-0280-42a3-9074-926caf8bc6d8" title="Добавить блюдо в меню" roles="/Policy/Mis/InvalidFood/DishMealMenuTemplate/Create" />
        <msh:sideLink action="/javascript:showaddTemplateDiet('.do')" name="Блюда в меню из шаблона" title="Добавить блюда в меню-заказ из существующего шаблона приема пищи" guid="494ab7bb-371a-4d8a-913f-1c8c702f0a5" roles="/Policy/Mis/InvalidFood/DishMealMenuOrder/Create" />
      </msh:sideMenu>
      <msh:sideMenu title="Дополнительно" guid="0ecbb4d3-2ad1-4400-9736-a3ed7c5ca571">
        <msh:sideLink action="/entityList-diet_diet" name="⇧Список всех диет" guid="13a3d527-25c9-4ee7-92b2-5f445ddbb27f" />
        <msh:sideLink roles="/Policy/Mis/Dish/View" action="/entityList-diet_dish" name="Справочник блюд" guid="c2b93e08-54ba-45b3-ad9c-59ee145c7922" />
        <msh:sideLink roles="/Policy/Mis/InvalidFood/VocFoodStuff/View" action="/diet_foodStuffs" name="Справочник продуктов питания" title="Показать справочник продуктов питания" guid="3597-f85c-4e87-b447-4124027e2e4e" />
        <msh:sideLink action="/entityList-diet_mealMenuTemplate" name="Шаблоны меню-раскладок" title="Просмотр списка шаблонов меню" guid="87e557d5-a9c3-427e-9afc-b42214cb77e5" />
      </msh:sideMenu>
    </msh:ifFormTypeIsView>
  </tiles:put>
</tiles:insert>

