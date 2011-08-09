<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://struts.apache.org/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://www.nuzmsh.ru/tags/msh" prefix="msh" %>
<%@ taglib uri="http://www.ecom-ast.ru/tags/ecom" prefix="ecom" %>

<tiles:insert page="/WEB-INF/tiles/mainLayout.jsp" flush="true">

  <tiles:put name="body" type="string">
    <msh:form guid="formHello" action="/entityParentSaveGoParentView-diet_dishMealMenuOrder.do" defaultField="menu">
      <msh:hidden guid="hiddenId" property="id" />
      <msh:hidden guid="hiddenSaveType" property="saveType" />
      <!--      <msh:hidden guid="hiddenParent" property="dish" />-->
      <msh:hidden property="menu" guid="a6102f45d0-bah4-4726-8f60-781e74a5db6c0" />
      <msh:panel guid="panel">
        <msh:autoComplete vocName="Dish" property="dish" label="Блюдо" guid="84ah3g9d12gd5dd1e-0a81-4762-9864-fbdfg" size="50" />
        <msh:submitCancelButtonsRow guid="submitCancel" colSpan="6" />
      </msh:panel>
    </msh:form>
  </tiles:put>
  <tiles:put name="title" type="string">
    <ecom:titleTrail guid="titleTrail-123" mainMenu="Diet" beginForm="diet_dishMealMenuOrderForm" />
  </tiles:put>
  <tiles:put name="side" type="string">
    <msh:ifFormTypeIsView formName="diet_dishMealMenuOrderForm" guid="0f243dbb-5ee3-4ea5-9d9b-5018a9e82287">
      <msh:sideMenu guid="sideMenu-123" title="Заказ блюда меню">
        <msh:sideLink guid="sideLinkEdit" key="ALT+2" params="id" action="/entityEdit-diet_dishMealMenuOrder" name="Изменить" roles="/Policy/Mis/InvalidFood/DishMealMenuOrder/Edit" />
        <msh:sideLink guid="sideLinkDelete" key="ALT+DEL" confirm="Удалить?" params="id" action="/entityParentDeleteGoParentView-diet_dishMealMenuOrder" name="Удалить" roles="/Policy/Mis/InvalidFood/DishMealMenuOrder/Delete" />
        <msh:sideLink params="id" action="/entityParentListRedirect-diet_dishMealMenuOrder" name="Список блюд меню" guid="80310fda-25e1-42fc-95b8-5a57352adc14" />
      </msh:sideMenu>
      <msh:sideMenu title="Дополнительно" guid="0ecbb4d3-2ad1-4400-9736-a3ed7c5ca571">
        <msh:sideLink action="/entityList-diet_diet" name="Список всех диет" guid="13a3d527-25c9-4ee7-92b2-5f445ddbb27f" />
        <msh:sideLink roles="/Policy/Mis/Dish/View" action="/entityList-diet_dish" name="Справочник блюд" guid="c2b93e08-54ba-45b3-ad9c-59ee145c7922" />
        <msh:sideLink roles="/Policy/Mis/InvalidFood/VocFoodStuff/View" action="/diet_foodStuffs" name="Справочник продуктов питания" title="Показать справочник продуктов питания" guid="3597-f85c-4e87-b447-4124027e2e4e" />
        <msh:sideLink params="" action="/entityList-diet_mealMenuTemplate" name="Шаблоны-меню" title="Просмотр списка шаблонов меню" guid="87e557d5-a9c3-427e-9afc-b42214cb77e5" />
      </msh:sideMenu>
    </msh:ifFormTypeIsView>
  </tiles:put>
</tiles:insert>

