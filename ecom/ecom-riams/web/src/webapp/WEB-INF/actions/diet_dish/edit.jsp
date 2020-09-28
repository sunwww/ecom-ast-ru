<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://struts.apache.org/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://www.nuzmsh.ru/tags/msh" prefix="msh" %>
<%@ taglib uri="http://www.ecom-ast.ru/tags/ecom" prefix="ecom" %>

<tiles:insert page="/WEB-INF/tiles/mainLayout.jsp" flush="true">

  <tiles:put name="body" type="string">
    <!-- 
    	  - Проба
    	  -->
    <msh:form action="entitySaveGoView-diet_dish.do" defaultField="hello" title="Блюдо">
      <msh:hidden property="id" />
      <msh:hidden property="saveType" />
      <msh:panel>
        <msh:row />
        <msh:row>
          <msh:textField property="dishNumber" label="Номер карточки-раскладки" />
          <msh:autoComplete vocName="vocDishType" property="dishType" label="Тип блюда" horizontalFill="true" fieldColSpan="5" />
        </msh:row>
        <msh:row>
          <msh:textField property="name" size="40" label="Наименование" fieldColSpan="5" horizontalFill="true" />
        </msh:row>
        <msh:row>
          <ecom:oneToManyOneAutocomplete vocName="Diet" label="Диеты" property="diets" />
        </msh:row>
        <msh:row>
          <msh:separator colSpan="6" label="Белки" />
        </msh:row>
        <msh:row>
          <msh:textField property="proteins" label="Всего, (г)" viewOnlyField="true" />
          <msh:textField property="plantProteins" label="Растительные, (г)" viewOnlyField="true" />
          <msh:textField property="animalProteins" label="Животные, (г)" viewOnlyField="true" />
        </msh:row>
        <msh:row>
          <msh:separator colSpan="6" label="Жиры" />
        </msh:row>
        <msh:row>
          <msh:textField property="lipids" label="Всего, (г)" viewOnlyField="true" />
          <msh:textField property="plantLipids" label="Растительные, (г)" viewOnlyField="true" />
          <msh:textField property="animalLipids" label="Животные, (г)" viewOnlyField="true" />
        </msh:row>
        <msh:row>
          <msh:separator colSpan="6" label="Углеводы" />
        </msh:row>
        <msh:row>
          <msh:textField passwordEnabled="false" hideLabel="false" property="carbohydrates" viewOnlyField="true" label="Всего, (г)" />
        </msh:row>
        <msh:row>
          <msh:separator colSpan="6" label="Витамины" />
        </msh:row>
        <msh:row>
          <msh:textField property="retinol" label="Витамин А, (мг)" viewOnlyField="true" />
          <msh:textField property="betaCarotin" label="B-каротин, (мг)" viewOnlyField="true" />
          <msh:textField property="tiamin" label="Витамин B1, (мг)" viewOnlyField="true" />
        </msh:row>
        <msh:row>
          <msh:textField property="riboflavin" label="Витамин B2, (мг)" viewOnlyField="true" />
          <msh:textField property="nicotinamid" label="Витамин PP, (мг)" viewOnlyField="true" />
          <msh:textField property="cevitamicAcid" label="Витамин C, (мг)" viewOnlyField="true" />
        </msh:row>
        <msh:row>
          <msh:separator colSpan="6" label="Минералы" />
        </msh:row>
        <msh:row>
          <msh:textField property="potassium" label="Калий, (мг)" viewOnlyField="true" />
          <msh:textField property="natrium" label="Натрий, (мг)" viewOnlyField="true" />
          <msh:textField property="calcium" label="Кальций, (мг)" viewOnlyField="true" />
        </msh:row>
        <msh:row>
          <msh:textField property="magnesium" label="Магний, (мг)" viewOnlyField="true" />
          <msh:textField property="phosphorus" label="Фосфор, (мг)" viewOnlyField="true" />
          <msh:textField property="ferrum" label="Железо, (мг)" viewOnlyField="true" />
        </msh:row>
        <msh:separator colSpan="6" label="" />
        <msh:row>
          <msh:textField passwordEnabled="false" hideLabel="false" property="weight" viewOnlyField="false" label="Выход готового блюда" horizontalFill="false" />
          <msh:textField passwordEnabled="false" hideLabel="false" property="calorieContent" viewOnlyField="false" label="Энергоценность" horizontalFill="false" />
        </msh:row>
        <msh:separator colSpan="6" label="Бракераж" />
        <msh:row>
          <msh:textField property="dishAppearance" label="Внешний вид блюда" horizontalFill="true" fieldColSpan="5" />
        </msh:row>
        <msh:row>
          <msh:textField property="dishColor" label="Цвет блюда" horizontalFill="true" fieldColSpan="5" />
        </msh:row>
        <msh:row>
          <msh:textField property="dishScent" label="Запах блюда" fieldColSpan="5" horizontalFill="true" />
        </msh:row>
        <msh:row>
          <msh:textField property="dishTaste" label="Вкус блюда" fieldColSpan="5" horizontalFill="true" />
        </msh:row>
        <msh:row>
          <msh:textField property="dishConsistence" label="Консистенция блюда" fieldColSpan="5" horizontalFill="true" />
        </msh:row>
        <msh:row>
          <msh:textArea property="dishStorageConditions" label="Срок годности и условия хранения блюда" rows="2" fieldColSpan="5" horizontalFill="true" />
        </msh:row>
        <msh:separator colSpan="6" label="Технология приготовления" />
        <msh:row>
          <msh:textArea property="preparationTechnology" label="Технология приготовления" horizontalFill="true" rows="30" fieldColSpan="5" size="1500" />
        </msh:row>
        <msh:submitCancelButtonsRow colSpan="4" />
      </msh:panel>
    </msh:form>
    <msh:ifFormTypeIsView formName="diet_dishForm">
      <msh:section title="Список продуктов">
        <ecom:parentEntityListAll formName="diet_dishFoodStuffForm" attribute="dishFoodStuffs" />
        <msh:table hideTitle="false" idField="id" name="dishFoodStuffs" action="entityParentView-diet_dishFoodStuff.do">
          <msh:tableColumn columnName="Продукт" property="name" />
          <msh:tableColumn columnName="Брутто" identificator="false" property="gross" />
          <msh:tableColumn columnName="Нетто" identificator="false" property="net" />
          <msh:tableColumn columnName="Белки" identificator="false" property="proteins" />
          <msh:tableColumn columnName="Белки" identificator="false" property="proteins" />
          <msh:tableColumn columnName="А" identificator="false" property="retinol" />
          <msh:tableColumn columnName="B-каротин" identificator="false" property="betaCarotin" />
          <msh:tableColumn columnName="B1" identificator="false" property="tiamin" />
          <msh:tableColumn columnName="B2" identificator="false" property="riboflavin" />
          <msh:tableColumn columnName="PP" identificator="false" property="nicotinamid" />
          <msh:tableColumn columnName="C" identificator="false" property="cevitamicAcid" />
          <msh:tableColumn columnName="K" identificator="false" property="potassium" />
          <msh:tableColumn columnName="Na" identificator="false" property="natrium" />
          <msh:tableColumn columnName="Ca" identificator="false" property="calcium" />
          <msh:tableColumn columnName="Mg" identificator="false" property="magnesium" />
          <msh:tableColumn columnName="P" identificator="false" property="phosphorus" />
          <msh:tableColumn columnName="Fe" identificator="false" property="ferrum" />
        </msh:table>
      </msh:section>
    </msh:ifFormTypeIsView>
  </tiles:put>
  <tiles:put name="title" type="string">
    <ecom:titleTrail mainMenu="Diet" beginForm="diet_dishForm" />
  </tiles:put>
  <tiles:put name="side" type="string">
    <msh:sideMenu>
      <msh:sideLink key="ALT+2" params="id" action="/entityEdit-diet_dish" name="Изменить" roles="/Policy/Mis/Dish/Edit" />
      <msh:sideLink key="ALT+DEL" confirm="Удалить?" params="id" action="/entityDelete-diet_dish" name="Удалить" roles="/Policy/Mis/Dish/Delete" />
    </msh:sideMenu>
    <msh:sideMenu title="Показать">
      <msh:sideLink params="id" action="/entityList-diet_dish" name="Список блюд" roles="/Policy/Mis/Dish/View" />
    </msh:sideMenu>
    <msh:sideMenu title="Продукт питания">
      <msh:sideLink roles="/Policy/Mis/DishFoodStuff/Create" params="id" action="/entityParentPrepareCreate-diet_dishFoodStuff" name="Добавить продукт" title="Добавить новый продукт" />
    </msh:sideMenu>
  </tiles:put>
</tiles:insert>

