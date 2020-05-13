<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@ taglib uri="http://struts.apache.org/tags-tiles" prefix="tiles"%>
<%@ taglib uri="http://www.nuzmsh.ru/tags/msh" prefix="msh"%>
<%@ taglib uri="http://www.ecom-ast.ru/tags/ecom" prefix="ecom"%>

<tiles:insert page="/WEB-INF/tiles/mainLayout.jsp" flush="true">

  <tiles:put name="body" type="string">
    <!-- 
    	  - Проба
    	  -->
    <msh:form action="entitySaveGoView-diet_vocFoodStuff.do" defaultField="hello">
      <msh:hidden property="id" />
      <msh:hidden property="saveType" />
      <msh:panel>
        <msh:row />
        <msh:textField property="name" label="Наименование" fieldColSpan="5" horizontalFill="true"  />
        <msh:row />
        <msh:row>
          <msh:separator colSpan="6" label="Белки" />
        </msh:row>
        <msh:row>
        <msh:textField property="proteins" label="Белки, (г)" />
        <msh:textField property="plantProteins" label="Растительные, (г)" />
          <msh:textField property="animalProteins" label="Животные, (г)" />
        </msh:row>
        <msh:row>
          <msh:separator colSpan="6" label="Жиры" />
        </msh:row>
        <msh:row>
          <msh:textField property="lipids" label="Жиры, (г)" horizontalFill="false" />
          <msh:textField property="plantLipids" label="Растительные, (г)" />
          <msh:textField property="animalLipids" label="Животные, (г)" />
        </msh:row>
        <msh:row>
          <msh:separator colSpan="6" label="Углеводы" />
        </msh:row>
        <msh:row>
        <msh:textField property="carbohydrates" label="Углеводы, (г)" horizontalFill="false" />
         </msh:row>
        <msh:row />
        <msh:textField property="calorieContent" label="Калорийность" />
        <msh:row>
          <msh:separator colSpan="4" label="Витамины" />
        </msh:row>
        <msh:row>
          <msh:textField property="retinol" label="Витамин А, (мг)" horizontalFill="true" />
          <msh:textField property="betaCarotin" label="B-каротин, (мг)" horizontalFill="true" />
          <msh:textField property="tiamin" label="Витамин B1, (мг)" horizontalFill="true" />
        </msh:row>
        <msh:row>
          <msh:textField property="riboflavin" label="Витамин B2, (мг)" horizontalFill="true" />
          <msh:textField property="nicotinamid" label="Витамин PP, (мг)" horizontalFill="true" />
          <msh:textField property="cevitamicAcid" label="Витамин C, (мг)" horizontalFill="true" />
        </msh:row>
        <msh:row>
          <msh:separator colSpan="4" label="Минералы" />
        </msh:row>
        <msh:row>
          <msh:textField property="potassium" label="Калий, (мг)" horizontalFill="true" />
          <msh:textField property="natrium" label="Натрий, (мг)" horizontalFill="true" />
          <msh:textField property="calcium" label="Кальций, (мг)" horizontalFill="true" />
        </msh:row>
        <msh:row>
          <msh:textField property="magnesium" label="Магний, (мг)" horizontalFill="true" />
          <msh:textField property="phosphorus" label="Фосфор, (мг)" horizontalFill="true" />
          <msh:textField property="ferrum" label="Железо, (мг)" horizontalFill="true" />
        </msh:row>
        <msh:submitCancelButtonsRow colSpan="6" />
      </msh:panel>
    </msh:form>
    <msh:ifFormTypeIsView formName="diet_vocFoodStuffForm">
      <msh:section title="Список шаблонов">
        <ecom:parentEntityListAll formName="diet_vocFoodStuffTemplateForm" attribute="templates" />
        <msh:table hideTitle="false" idField="id" name="templates" action="entityParentView-diet_vocFoodStuffTemplate.do">
          <msh:tableColumn columnName="Продукт" property="foodStuffText" />
          <msh:tableColumn columnName="Брутто" identificator="false" property="gross" />
          <msh:tableColumn columnName="Месяц начала действия" identificator="false" property="monthStartText" />
          <msh:tableColumn columnName="Месяц окончания действия" identificator="false" property="monthEndText" />
        </msh:table>
      </msh:section>
    </msh:ifFormTypeIsView>
  </tiles:put>
  <tiles:put name="title" type="string">
    <ecom:titleTrail mainMenu="Diet" beginForm="diet_vocFoodStuffForm" />
  </tiles:put>
  <tiles:put name="side" type="string">
    <msh:sideMenu>
      <msh:sideLink action="/entityList-diet_diet.do?id=-1" name="Список диет" />
      <msh:sideLink action="/entityList-diet_dish.do" name="Список блюд" />
      <msh:sideLink key="ALT+2" params="id" action="/entityEdit-diet_vocFoodStuff" name="Изменить" roles="/Policy/Mis/InvalidFood/VocFoodStuff/Edit" />
      <msh:sideLink key="ALT+DEL" confirm="Удалить?" params="id" action="/entityDelete-diet_vocFoodStuff" name="Удалить" roles="/Policy/Mis/InvalidFood/VocFoodStuff/Delete" />
    </msh:sideMenu>
    <msh:sideMenu title="Добавить">
      <msh:sideLink key="ALT+2" params="id" action="/entityParentPrepareCreate-diet_vocFoodStuffTemplate" name="Шаблон" roles="/Policy/Mis/InvalidFood/VocFoodStuffTemplate/Create" />
    </msh:sideMenu>
  </tiles:put>
</tiles:insert>

