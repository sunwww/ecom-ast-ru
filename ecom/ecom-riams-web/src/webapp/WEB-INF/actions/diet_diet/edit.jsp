<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://struts.apache.org/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://www.nuzmsh.ru/tags/msh" prefix="msh" %>
<%@ taglib uri="http://www.ecom-ast.ru/tags/ecom" prefix="ecom" %>

<tiles:insert page="/WEB-INF/tiles/mainLayout.jsp" flush="true">

  <tiles:put name="body" type="string">
    <!-- 
    	  - Диета
    	  -->
    <msh:form guid="17f720e4-3690-4ae5-961b-6d69348757" action="entityParentSaveGoView-diet_diet.do" defaultField="name">
      <msh:hidden guid="hiddenId" property="id" />
      <msh:hidden guid="hiddenSaveType" property="saveType" />
      <msh:panel guid="5eb-1c51-4f0d-ab04-2454-87" colsWidth="5">
        <msh:row guid="5eb-1c51-4f0d-ab04-2454-87hf-hff-fg64-5454">
          <msh:autoComplete showId="false" vocName="lpu" hideLabel="false" property="lpu" viewOnlyField="false" guid="69fadbf0-55d1-4e40-92e8-c4097442f048" horizontalFill="true" />
        </msh:row>
        <msh:textField property="name" label="Название" guid="e71fa83a-c6c2-422" horizontalFill="true" fieldColSpan="3" size="50" />
        <msh:row guid="455d3594-b991-47d7-b0ff-267ab8932d66" />
        <msh:textField property="shortName" label="Аббревиатура" horizontalFill="true" fieldColSpan="3" guid="48658ec8-65e9-41e9-97ae-fd6040da5ccc" />
        <msh:row guid="76a00650-8e75-432c-9fc0-150d0150345b">
          <msh:autoComplete showId="false" vocName="parentDiet" hideLabel="false" property="parent" viewOnlyField="false" guid="2c962582-2f79-44b8-8eaf-3d7de201b950" horizontalFill="true" />
        </msh:row>
        <msh:row guid="4c812380-7866-4480-aa11-7f44db7fba5c" />
        <msh:row guid="c4d89d97-e429-47b1-9a88-a082e3d7f011">
          <msh:textArea property="prescription" label="Показания к применению" horizontalFill="true" rows="30" size="1000" guid="ff362af0-d3e5-49a0-91ac-d58e4951d1b4" />
        </msh:row>
        <msh:textArea property="description" label="Описание" horizontalFill="true" rows="30" size="1000" guid="9e8e72e3-5b37-4fef-ad93-a2cb2c36fb9b" />
        <msh:submitCancelButtonsRow guid="1015eb-1c51-4f0d-ab04-0b" colSpan="2" />
      </msh:panel>
    </msh:form>
    <msh:ifFormTypeIsView formName="diet_dietForm" guid="36f4af8f-877d-4393-b6b2-f1845cc6895c">
      <msh:section title="Подчиненные диеты" guid="28823a69-c139-4645-89fe-789263">
        <ecom:webQuery name="diet" guid="223fefa9-04f3-47d9-a841-d850ef118ce6" nativeSql="select diet.id, diet.name from Diet where diet.parent_id=${param.id}" />
        <msh:table name="diet" action="entityParentView-diet_diet.do" guid="e8550-c34b-40bb-9aac-fcdd4da" idField="1">
          <msh:tableColumn columnName="Название" property="2" guid="e85j50-c34b-40bb-9aac-fcdd4da97" />
        </msh:table>
      </msh:section>
      <msh:section title="Блюда" guid="28823a69-c139-4645-89fe-7892633f044a">
        <ecom:webQuery name="list" nativeSql="select d.id, d.dishNumber, d.name, d.weight&#xA; from dish as d&#xA;inner join dish_diet dd on d.id = dd.dish_id&#xA;where dd.diets_id = ${param.id}" guid="c7be92aa-a3e8-43b4-a4aa-7ec7e1a0390f" />
        <msh:table idField="1" name="list" action="entityView-diet_dish.do" guid="00a1c1a6-e9ce-4b50-9bd8-c1b3ebb88455">
          <msh:tableColumn columnName="ИД" property="1" guid="e8550-c34b-40bb-9aac-fcdd4da974546" />
          <msh:tableColumn columnName="Номер карточки" identificator="false" property="2" guid="50ed9de5-c8ae-47ff-86f7-c0755b2abe61" />
          <msh:tableColumn columnName="Наименование" identificator="false" property="3" guid="2a0eace0-48b1-45ae-bb66-36e92b2fb581" />
          <msh:tableColumn columnName="Выход блюда (г)" identificator="false" property="4" guid="cd12c3d9-2d6b-4d5c-baed-915e25f8b8d9" />
        </msh:table>
      </msh:section>
    </msh:ifFormTypeIsView>
  </tiles:put>
  <tiles:put name="title" type="string">
    <ecom:titleTrail guid="titleTrail-123" mainMenu="Diet" beginForm="diet_dietForm" />
  </tiles:put>
  <tiles:put name="side" type="string">
    <msh:ifFormTypeIsView formName="diet_dietForm" guid="6e08cf65-14fd-4370-bee3-b01456d6866a">
      <msh:sideMenu guid="sideMenu-123" title="Диета">
        <msh:sideLink action="/entityParentListRedirect-diet_diet" name="Список диет" guid="85c91be4-65de-43e3-be4c-95db308618ae" params="id" />
        <msh:sideLink guid="sideLinkEdit" key="ALT+2" params="id" action="/entityEdit-diet_diet" name="Изменить диету" />
        <msh:sideLink guid="sideLinkDelete" key="ALT+DEL" confirm="Удалить?" params="id" action="/entityDelete-diet_diet" name="Удалить диету" roles="/Policy/IdeMode/Hello/Delete" />
      </msh:sideMenu>
      <msh:sideMenu title="Добавить" guid="b44f5659-2a4a-4e49-9700-31b3a7eb150f">
        <msh:sideLink roles="/Policy/Mis/Dish/Create" action="/entityPrepareCreate-diet_dish" name="Новое блюдо" title="Добавить новое блюдо" guid="3250fe37-f85c-4e87-b447-4124027e2e4e" />
      </msh:sideMenu>
      <msh:sideMenu title="Показать все" guid="b6202b67-4d44-4463-a63f-40471c1086f8">
        <msh:sideLink roles="/Policy/Mis/InvalidFood/MealMenuTemplate/View" params="id" action="/entityParentList-diet_mealMenuTemplate" name="Шаблон меню-раскладки" guid="73fda363-6c83-42e0-8de4-806f65bb2427" />
        <msh:sideLink roles="/Policy/Mis/InvalidFood/MealMenuOrder/View" params="id" action="/entityParentList-diet_mealMenuOrder" name="Меню-заказы" guid="5883c1e8-70ef-4a84-a55d-6fa359b69ad3" />
        <msh:sideLink roles="/Policy/Mis/InvalidFood/MealQualityControl/View" params="id" action="/entityParentList-diet_mealQualityControl" name="Журнал контроля качества пищи" guid="178c6efa-50e4-4aeb-9d7f-61a03b122216" />
        <msh:sideLink roles="/Policy/Mis/InvalidFood/MealWorkerHealthControl/View" params="id" action="/entityParentList-diet_mealWorkerHealthControl.do?diet=${param.id}" name="Журнал контроля здоровья работающих с пищей" guid="f03f1358-59a7-4b84-a6c7-44c77cb6de0a" />
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

