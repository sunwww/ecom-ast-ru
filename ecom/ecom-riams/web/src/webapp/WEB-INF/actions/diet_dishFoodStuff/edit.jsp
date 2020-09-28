<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://struts.apache.org/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://www.nuzmsh.ru/tags/msh" prefix="msh" %>
<%@ taglib uri="http://www.ecom-ast.ru/tags/ecom" prefix="ecom" %>

<tiles:insert page="/WEB-INF/tiles/mainLayout.jsp" flush="true">

  <tiles:put name="body" type="string">
    <!-- 
    	  - Проба
    	  -->
    <msh:form action="entitySaveGoView-diet_dishFoodStuff.do" defaultField="hello">
      <msh:hidden property="id" />
      <msh:hidden property="saveType" />
      <msh:hidden property="dish" />
      <msh:panel>
        <msh:row />
        <msh:autoComplete vocName="vocFoodStuff" property="foodStuff" label="Продукт питания" fieldColSpan="5" horizontalFill="true" />
        <msh:row />
        <msh:textField property="gross" label="Брутто" />
        <msh:textField property="net" label="Нетто" horizontalFill="false" />
        <msh:row />
        <msh:textField property="proteins" label="Белки, (г)" viewOnlyField="true"/>
        <msh:textField property="lipids" label="Жиры, (г)" horizontalFill="false" viewOnlyField="true"/>
        <msh:textField property="carbohydrates" label="Углеводы, (г)" horizontalFill="false" viewOnlyField="true" />
        <msh:row />
        <msh:textField property="calorieContent" label="Калорийность" />
        <msh:row>
          <msh:separator colSpan="6" label="Витамины" />
        </msh:row>
        <msh:row>
          <msh:textField property="retinol" label="Витамин А, (мг)" horizontalFill="true" viewOnlyField="true"/>
          <msh:textField property="betaCarotin" label="B-каротин, (мг)" horizontalFill="true" viewOnlyField="true"/>
          <msh:textField property="tiamin" label="Витамин B1, (мг)" horizontalFill="true" viewOnlyField="true"/>
        </msh:row>
        <msh:row>
          <msh:textField property="riboflavin" label="Витамин B2, (мг)" horizontalFill="true" viewOnlyField="true"/>
          <msh:textField property="nicotinamid" label="Витамин PP, (мг)" horizontalFill="true" viewOnlyField="true"/>
          <msh:textField property="cevitamicAcid" label="Витамин C, (мг)" horizontalFill="true" viewOnlyField="true"/>
        </msh:row>
        <msh:row>
          <msh:separator colSpan="6" label="Минералы" />
        </msh:row>
        <msh:row>
          <msh:textField property="potassium" label="Калий, (мг)" horizontalFill="true" viewOnlyField="true"/>
          <msh:textField property="natrium" label="Натрий, (мг)" horizontalFill="true" viewOnlyField="true"/>
          <msh:textField property="calcium" label="Кальций, (мг)" horizontalFill="true" viewOnlyField="true"/>
        </msh:row>
        <msh:row>
          <msh:textField property="magnesium" label="Магний, (мг)" horizontalFill="true" viewOnlyField="true"/>
          <msh:textField property="phosphorus" label="Фосфор, (мг)" horizontalFill="true" viewOnlyField="true"/>
          <msh:textField property="ferrum" label="Железо, (мг)" horizontalFill="true" viewOnlyField="true"/>
        </msh:row>
        <msh:submitCancelButtonsRow colSpan="6" />
      </msh:panel>
    </msh:form>
  </tiles:put>
  <tiles:put name="title" type="string">
    <ecom:titleTrail mainMenu="Diet" beginForm="diet_dishFoodStuffForm" />
  </tiles:put>
  <tiles:put name="side" type="string">
    <msh:sideMenu>
      <msh:sideLink action="/entityList-diet_diet.do?id=-1" name="Список диет" />
      <msh:sideLink action="/entityList-diet_dish.do" name="Список блюд" />
      <msh:sideLink key="ALT+2" params="id" action="/entityEdit-diet_dishFoodStuff" name="Изменить" roles="/Policy/Mis/DishFoodStuff/Edit" />
      <msh:sideLink key="ALT+DEL" confirm="Удалить?" params="id" action="/entityParentDeleteGoParentView-diet_dishFoodStuff" name="Удалить" roles="/Policy/Mis/DishFoodStuff/Delete" />
    </msh:sideMenu>
  </tiles:put>
</tiles:insert>

