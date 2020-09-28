<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://struts.apache.org/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://www.nuzmsh.ru/tags/msh" prefix="msh" %>
<%@ taglib uri="http://www.ecom-ast.ru/tags/ecom" prefix="ecom" %>

<tiles:insert page="/WEB-INF/tiles/mainLayout.jsp" flush="true">

  <tiles:put name="body" type="string">
    <!-- 
    	  - Диета
    	  -->
    <msh:form action="entityParentSaveGoView-diet_diet.do" defaultField="name">
      <msh:hidden property="id" />
      <msh:hidden property="saveType" />
      <msh:panel colsWidth="5">
        <msh:row>
          <msh:autoComplete showId="false" vocName="lpu" hideLabel="false" property="lpu" viewOnlyField="false" horizontalFill="true" />
        </msh:row>
        <msh:textField property="name" label="Название" horizontalFill="true" fieldColSpan="3" size="50" />
        <msh:row />
        <msh:textField property="shortName" label="Аббревиатура" horizontalFill="true" fieldColSpan="3" />
        <msh:row>
          <msh:autoComplete showId="false" vocName="parentDiet" hideLabel="false" property="parent" viewOnlyField="false" horizontalFill="true" />
        </msh:row>
        <msh:row />
        <msh:row>
          <msh:textArea property="prescription" label="Показания к применению" horizontalFill="true" rows="30" size="1000" />
        </msh:row>
        <msh:textArea property="description" label="Описание" horizontalFill="true" rows="30" size="1000" />
        <msh:row>
        <msh:checkBox property="isArchival" label="Не используется" />
        </msh:row>
        <msh:submitCancelButtonsRow colSpan="2" />
      </msh:panel>
    </msh:form>
    <msh:ifFormTypeIsView formName="diet_dietForm">
      <msh:section title="Подчиненные диеты">
        <ecom:webQuery name="diet" nativeSql="select diet.id, diet.name from Diet where diet.parent_id=${param.id}" />
        <msh:table name="diet" action="entityParentView-diet_diet.do" idField="1">
          <msh:tableColumn columnName="Название" property="2" />
        </msh:table>
      </msh:section>
      <msh:section title="Блюда">
        <ecom:webQuery name="list" nativeSql="select d.id, d.dishNumber, d.name, d.weight&#xA; from dish as d&#xA;inner join dish_diet dd on d.id = dd.dish_id&#xA;where dd.diets_id = ${param.id}" />
        <msh:table idField="1" name="list" action="entityView-diet_dish.do">
          <msh:tableColumn columnName="ИД" property="1" />
          <msh:tableColumn columnName="Номер карточки" identificator="false" property="2" />
          <msh:tableColumn columnName="Наименование" identificator="false" property="3" />
          <msh:tableColumn columnName="Выход блюда (г)" identificator="false" property="4" />
        </msh:table>
      </msh:section>
    </msh:ifFormTypeIsView>
  </tiles:put>
  <tiles:put name="title" type="string">
    <ecom:titleTrail mainMenu="Diet" beginForm="diet_dietForm" />
  </tiles:put>
  <tiles:put name="side" type="string">
    <msh:ifFormTypeIsView formName="diet_dietForm">
      <msh:sideMenu title="Диета">
        <msh:sideLink action="/entityParentListRedirect-diet_diet" name="Список диет" params="id" />
        <msh:sideLink key="ALT+2" params="id" action="/entityEdit-diet_diet" name="Изменить диету" />
        <msh:sideLink key="ALT+DEL" confirm="Удалить?" params="id" action="/entityDelete-diet_diet" name="Удалить диету" roles="/Policy/IdeMode/Hello/Delete" />
      </msh:sideMenu>
      <msh:sideMenu title="Добавить">
        <msh:sideLink action="/entityParentPrepareCreate-diet_diet" name="Добавить подчиненную диету" roles="/Policy/Mis/Diet/Create" params="id"/>
        <msh:sideLink roles="/Policy/Mis/Dish/Create" action="/entityPrepareCreate-diet_dish" name="Новое блюдо" title="Добавить новое блюдо" />
      </msh:sideMenu>
      <msh:sideMenu title="Показать все">
        <msh:sideLink roles="/Policy/Mis/InvalidFood/MealMenuTemplate/View" params="id" action="/entityParentList-diet_mealMenuTemplate" name="Шаблон меню-раскладки" />
        <msh:sideLink roles="/Policy/Mis/InvalidFood/MealMenuOrder/View" params="id" action="/entityParentList-diet_mealMenuOrder" name="Меню-заказы" />
      </msh:sideMenu>
      <msh:sideMenu title="Дополнительно">
        <msh:sideLink action="/entityList-diet_diet" name="⇧Список всех диет" />
        <msh:sideLink roles="/Policy/Mis/Dish/View" action="/entityList-diet_dish" name="Справочник блюд" />
        <msh:sideLink roles="/Policy/Mis/InvalidFood/VocFoodStuff/View" action="/diet_foodStuffs" name="Справочник продуктов питания" title="Показать справочник продуктов питания" />
        <msh:sideLink action="/entityList-diet_mealMenuTemplate" name="Шаблоны меню-раскладок" title="Просмотр списка шаблонов меню" />
      </msh:sideMenu>
    </msh:ifFormTypeIsView>
  </tiles:put>
</tiles:insert>

