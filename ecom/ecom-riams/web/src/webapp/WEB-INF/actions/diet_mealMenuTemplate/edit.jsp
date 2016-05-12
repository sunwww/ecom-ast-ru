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
    <msh:form guid="formHello" action="entitySaveGoView-diet_mealMenuTemplate.do" defaultField="hello">
      <msh:hidden guid="hiddenId" property="id" />
      <msh:hidden guid="hiddenSaveType" property="saveType" />
      <msh:hidden property="diet" guid="8781b388-02dc-4db5-8b24-a92d7a4caef6" />
      <msh:panel guid="panel">
        <msh:row guid="b5f4n256eb-b971-441e-9a90-5194a80">
          <msh:autoComplete showId="false" hideLabel="false" property="diet" viewOnlyField="false" guid="7b2046e2-663b-4dc5-869e-5936c0fbe3bd" horizontalFill="true" fieldColSpan="3" vocName="Diet" />
        </msh:row>
        <msh:row guid="b7ce514e-50d2-4a6e-a958-69f39a4e9730">
          <msh:autoComplete showId="false" vocName="vocServiceStream" hideLabel="false" property="serviceStream" viewOnlyField="false" label="Поток обслуживания" guid="ced6c3a1-1163-405c-ace7-bcf23d1464cb" horizontalFill="true" fieldColSpan="3" />
        </msh:row>
        <msh:row guid="e439f1c0-ae72-48ad-9086-b54619a6cb1d">
          <msh:autoComplete vocName="vocWeekDay" property="weekDay" label="День недели" guid="84add1e7-0a81-4762-9864-fbff" horizontalFill="true" fieldColSpan="3" />
        </msh:row>
        <msh:row guid="059022f5-f562-43b0-ad50-983bec13eac7">
          <msh:textField property="dateFrom" label="Дата начала действия" guid="83a-c6c2-4221-bb72-7706" />
          <msh:textField property="dateTo" label="Дата окончания действия" guid="73e32-d378-4206-896e-a6472dc7222f" />
        </msh:row>
        <msh:submitCancelButtonsRow guid="submitCancel" colSpan="6" />
      </msh:panel>
    </msh:form>
    <msh:ifFormTypeIsView formName="diet_mealMenuTemplateForm" guid="4403e-3dda-408e-85f8-78081f">
      <msh:section guid="c5be3-5330-4589-8da8-7888453a" title="Список шаблонов меню">
        <ecom:parentEntityListAll formName="diet_childMealMenuTemplateForm" attribute="childMenus" guid="c507-4d9b-4ddc-99f5-5e3a07b48447" />
        <msh:table hideTitle="false" idField="id" name="childMenus" action="entityParentView-diet_childMealMenuTemplate.do" guid="aa435-03a5-43de-8754-3fa4d54ede17">
          <msh:tableColumn identificator="false" property="description" guid="999ef32d-be02-4726-a1ab-e3a4d1f76c46" columnName="Название приема пищи" />
          <msh:tableColumn identificator="false" property="listDishes" guid="6b5f2f5b-31c6-40c1-a42f-a60ee477019c" columnName="Список блюд" />
        </msh:table>
      </msh:section>
    </msh:ifFormTypeIsView>
  </tiles:put>
  <tiles:put name="title" type="string">
    <ecom:titleTrail guid="titleTrail-123" mainMenu="Diet" beginForm="diet_mealMenuTemplateForm" />
  </tiles:put>
  <tiles:put name="side" type="string">
    <msh:ifFormTypeIsView formName="diet_mealMenuTemplateForm" guid="fd86b2e8-e15a-4a0d-8f55-cce2ce862b44">
      <tags:templateDietMeal parentId="${param.id}" name="add" />
      <msh:sideMenu guid="sideMenu-123" title="Меню-шаблон">
        <msh:sideLink guid="sideLinkEdit" key="ALT+2" params="id" action="/entityEdit-diet_mealMenuTemplate" name="Изменить" roles="/Policy/Mis/InvalidFood/MealMenuTemplate/Edit" />
        <msh:sideLink guid="sideLinkDelete" key="ALT+DEL" confirm="Удалить?" params="id" action="/entityDelete-diet_mealMenuTemplate" name="Удалить" roles="/Policy/Mis/InvalidFood/MealMenuTemplate/Delete" />
      </msh:sideMenu>
      <msh:sideMenu title="Добавить" guid="e9273674-e802-416d-a568-afa1fdcc46a8">
        <msh:sideLink params="id" action="/entityParentPrepareCreate-diet_childMealMenuTemplate" name="Прием пищи" guid="839b28fe-0280-42a3-9074-926caf8bc6d8" title="Добавить прием пищи в меню-раскладку" roles="/Policy/Mis/InvalidFood/MealMenuTemplate/Create" />
        <msh:sideLink action="/javascript:showaddTemplateDiet('.do')" name="прием пищи из шаблона" guid="b2fb0a32-5f35-4a82-afcd-0df64c13a567" title="Добавить приемы пищи из шаблона меню-раскладки" roles="/Policy/Mis/InvalidFood/MealMenuTemplate/Create" />

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

