<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://struts.apache.org/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://www.nuzmsh.ru/tags/msh" prefix="msh" %>
<%@ taglib uri="http://www.ecom-ast.ru/tags/ecom" prefix="ecom" %>

<%@ taglib tagdir="/WEB-INF/tags/diet" prefix="tags" %>

<tiles:insert page="/WEB-INF/tiles/mainLayout.jsp" flush="true">

  <tiles:put name="body" type="string">
    <!-- 
    	  - Проба
    	  -->
    <msh:form guid="formHello" action="/entityParentSaveGoView-diet_mealMenuOrder.do" defaultField="hello">
      <msh:hidden guid="hiddenId" property="id" />
      <msh:hidden guid="hiddenSaveType" property="saveType" />
      <msh:hidden guid="hiddenParent" property="diet" />
      <msh:panel guid="panel">
        <msh:row guid="687ba18f-7b48-4d7a-b46e-681f71c8ab14">
          <msh:autoComplete showId="false" hideLabel="false" property="diet" viewOnlyField="false" guid="a088a6cc-15ae-41b0-84c8-5d17b4b49619" horizontalFill="true" vocName="Diet" fieldColSpan="3" />
        </msh:row>
        <msh:row guid="687ba18f-7b48-4d7a-b46e-681f71c8a14">
          <msh:autoComplete vocName="lpu" property="lpu" label="Отделение" guid="kjfdhiu7-0a81-4762-9864-fbffc6" horizontalFill="true" fieldColSpan="3" />
        </msh:row>
        <msh:row guid="b5f456eb-b971-441e-9a90-51a80ghgh">
          <msh:textField property="dateFrom" label="Дата" guid="54564653a-c6c2-4221-bb72-7706" />
          <msh:autoComplete vocName="vocServiceStream" property="serviceStream" label="Поток обслуживания" guid="84atedd1e7-0a81-4762-9264-fbffc6" horizontalFill="true" />
        </msh:row>
        <msh:row guid="b5f45hffghd6eb-b971-441e-9a90-51a80ghgh">
          <msh:autoComplete showId="false" hideLabel="false" property="mealTime" viewOnlyField="false" guid="61139449-1661-4763-9069-dd9bab697cc0" horizontalFill="false" vocName="vocMealTime" />
          <msh:textField passwordEnabled="false" hideLabel="false" property="portionAmount" viewOnlyField="false" guid="3c39d585-be4c-4530-9a74-0cda1d47e0ee" horizontalFill="false" />
        </msh:row>
        <msh:submitCancelButtonsRow guid="submitCancel" colSpan="6" />
      </msh:panel>
    </msh:form>
    <msh:ifFormTypeIsView formName="diet_mealMenuOrderForm" guid="4403e-3dda-408e-85f8-78081f">
      <msh:ifInRole roles="/Policy/Mis/InvalidFood/DishMealMenu/View" guid="b41d665b-6231-459e-a23a-ac88e2acdbbc">
        <msh:section guid="c5be3-5330-4589-8da8-7888453a" title="Список блюд меню-заказа">
          <ecom:parentEntityListAll formName="diet_dishMealMenuOrderForm" attribute="dishes" guid="c507-4d9b-4ddc-99f5-5e3a07b48447" />
          <msh:table hideTitle="false" idField="id" name="dishes" action="entityParentView-diet_dishMealMenuOrder.do" guid="aa435-03a5-43de-8754-3fa4d54ede17">
            <msh:tableColumn identificator="false" property="dishName" guid="999ef32d-be02-4726-a1ab-e3a4d1f76c46" columnName="Название блюда" />
          </msh:table>
        </msh:section>
      </msh:ifInRole>
    </msh:ifFormTypeIsView>
  </tiles:put>
  <tiles:put name="title" type="string">
    <ecom:titleTrail guid="titleTrail-123" mainMenu="Diet" beginForm="diet_mealMenuOrderForm" />
  </tiles:put>
  <tiles:put name="side" type="string">
    <msh:ifFormTypeIsView formName="diet_mealMenuOrderForm" guid="3175104d-57f5-4cca-a3d9-143c35041196">
      <tags:templateDietDish record="1" parentId="${param.id}" name="add" />
      <msh:sideMenu guid="sideMenu-123" title="Меню-заказ">
        <msh:sideLink guid="sideLinkEdit" key="ALT+2" params="id" action="/entityEdit-diet_mealMenuOrder" name="Изменить" roles="/Policy/Mis/InvalidFood/MealMenuOrder/Edit" />
        <msh:sideLink guid="sideLinkDelete" key="ALT+DEL" confirm="Удалить?" params="id" action="/entityDelete-diet_mealMenuOrder" name="Удалить" roles="/Policy/Mis/InvalidFood/MealMenuOrder/Delete" />
        <msh:sideLink action="/entityParentListRedirect-diet_mealMenuOrder" name="Список меню-заказов" guid="cfcc38f5-acdc-407d-ab94-f40ac55be477" params="id" />
      </msh:sideMenu>
      <msh:sideMenu title="Добавить" guid="9e730f59-3e55-41c8-add9-fbcd43d0f9be">
        <msh:sideLink params="id" action="/entityParentPrepareCreate-diet_dishMealMenuOrder" name="Блюдо в меню" title="Добавить блюдо в меню-заказ" guid="494ab7bb-371a-4d8a-913f-1c8c702f0a05" roles="/Policy/Mis/InvalidFood/DishMealMenuOrder/Create" />
        <msh:sideLink action="/javascript:showaddTemplateDiet('.do')" name="Блюда в меню из шаблона" title="Добавить блюда в меню-заказ из существующего шаблона приема пищи" guid="494ab7bb-371a-4d8a-913f-1c8c702f0a5" roles="/Policy/Mis/InvalidFood/DishMealMenuOrder/Create" />
      </msh:sideMenu>
      <msh:sideMenu title="Дополнительно" guid="0ecbb4d3-2ad1-4400-9736-a3ed7c5ca571">
        <msh:sideLink action="/entityList-diet_diet" name="⇧Список всех диет" guid="13a3d527-25c9-4ee7-92b2-5f445ddbb27f" />
        <msh:sideLink roles="/Policy/Mis/Dish/View" action="/entityList-diet_dish" name="Справочник блюд" guid="c2b93e08-54ba-45b3-ad9c-59ee145c7922" />
        <msh:sideLink roles="/Policy/Mis/InvalidFood/VocFoodStuff/View" action="/diet_foodStuffs" name="Справочник продуктов питания" title="Показать справочник продуктов питания" guid="3597-f85c-4e87-b447-4124027e2e4e" />
        <msh:sideLink params="id" action="/entityList-diet_mealMenuTemplate" name="Шаблоны-меню" title="Просмотр списка шаблонов меню" guid="87e557d5-a9c3-427e-9afc-b42214cb77e5" />
      </msh:sideMenu>
    </msh:ifFormTypeIsView>
  </tiles:put>
</tiles:insert>

