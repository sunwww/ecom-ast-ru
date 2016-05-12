<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://struts.apache.org/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://www.nuzmsh.ru/tags/msh" prefix="msh" %>
<%@ taglib uri="http://www.ecom-ast.ru/tags/ecom" prefix="ecom" %>

<tiles:insert page="/WEB-INF/tiles/mainLayout.jsp" flush="true">

  <tiles:put name="body" type="string">
    <!-- 
    	  - Проба
    	  -->
    <msh:form guid="formHello" action="/entitySaveGoView-diet_dishMealMenu.do" defaultField="hello">
      <msh:hidden guid="hiddenId" property="id" />
      <msh:hidden guid="hiddenSaveType" property="saveType" />
      <!--      <msh:hidden guid="hiddenParent" property="dish" />-->
      <msh:hidden property="menu" guid="a6102fd0-baa4-4726-8f60-781e74adb6c0" />
      <msh:panel guid="panel">
        <msh:row guid="row1" />
        <msh:row guid="ht5b5fgh456eb-b971-441e-9a90-5194a" />
        <msh:autoComplete vocName="Dish" property="dish" label="Блюдо" guid="84ahg9dgddd1e-0a81-4762-9864-fbdfg" size="50" />
        <msh:submitCancelButtonsRow guid="submitCancel" colSpan="6" />
      </msh:panel>
    </msh:form>
  </tiles:put>
  <tiles:put name="title" type="string">
    <ecom:titleTrail guid="titleTrail-123" mainMenu="Diet" beginForm="diet_mealMenuTemplateForm" />
  </tiles:put>
  <tiles:put name="side" type="string">
    <msh:sideMenu guid="sideMenu-123" title="Меню-заказ">
      <msh:sideLink guid="sideLinkEdit" key="ALT+2" params="id" action="/entityEdit-diet_dishMealMenu" name="Изменить" roles="/Policy/Mis/InvalidFood/DishMealMenu/Edit" />
      <msh:sideLink guid="sideLinkNew" key="ALT+N" params="id" action="/entityParentPrepareCreate-diet_dishMealMenu" name="Добавить блюдо" roles="/Policy/Mis/InvalidFood/DishMealMenu/Create" />
      <msh:sideLink guid="sideLinkDelete" key="ALT+DEL" confirm="Удалить?" params="id" action="/entityDelete-diet_dishMealMenu" name="Удалить" roles="/Policy/Mis/InvalidFood/DishMealMenu/Delete" />
    </msh:sideMenu>
  </tiles:put>
</tiles:insert>

