<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://struts.apache.org/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://www.nuzmsh.ru/tags/msh" prefix="msh" %>
<%@ taglib uri="http://www.ecom-ast.ru/tags/ecom" prefix="ecom" %>

<tiles:insert page="/WEB-INF/tiles/mainLayout.jsp" flush="true">

  <tiles:put name="body" type="string">
    <!-- 
    	  - Проба
    	  -->
    <msh:form guid="formHello" action="entitySaveGoView-diet_dishFoodStuff.do" defaultField="hello">
      <msh:hidden guid="hiddenId" property="id" />
      <msh:hidden guid="hiddenSaveType" property="saveType" />
      <msh:hidden guid="hiddenParent" property="dish" />
      <msh:panel guid="panel">
        <msh:row guid="row1" />
        <msh:autoComplete vocName="vocFoodStuff" property="foodStuff" label="Продукт питания" guid="84add1e7-0a81-4762-9864-fbffc69b" fieldColSpan="5" horizontalFill="true" />
        <msh:row guid="b5f456eb-b971-441e-9a90-5194a801" />
        <msh:textField property="gross" label="Брутто" guid="e71fa83a-c6c2-4221-bb72-7706" />
        <msh:textField property="net" label="Нетто" guid="3fc73e32-d378-4206-896e-a6472dc7222f" horizontalFill="false" />
        <msh:row guid="b5f456eb-b971-441e-9a90-5194a8" />
        <msh:textField property="proteins" label="Белки, (г)" guid="e71fa83a-c6c2-4221-bb7" viewOnlyField="true"/>
        <msh:textField property="lipids" label="Жиры, (г)" guid="8d778128-2e93-418b-ae23-8a8ef04a27a5" horizontalFill="false" viewOnlyField="true"/>
        <msh:textField property="carbohydrates" label="Углеводы, (г)" guid="eedf558d-9c62-475e-a340-3345ab650327" horizontalFill="false" viewOnlyField="true" />
        <msh:row guid="b5f456eb-b971-441e-9a90-5194a" />
        <msh:textField property="calorieContent" label="Калорийность" guid="e71fa83a-c6c2-4221-bb72-77" />
        <msh:row guid="71b49d7-1dd4-4af3-be58-9b1d7669vyt61c3">
          <msh:separator colSpan="6" label="Витамины" guid="5mya3gyr5y3bf6-7fee-45e5-96ec-7b019182cfcb" />
        </msh:row>
        <msh:row guid="71my1ddh49d7-1dd4-4af3-be58-9b1d7669vyt61c3">
          <msh:textField property="retinol" label="Витамин А, (мг)" guid="mth9dgt3b5c12a-27a3-42ed-8951-6661d19edf64" horizontalFill="true" viewOnlyField="true"/>
          <msh:textField property="betaCarotin" label="B-каротин, (мг)" guid="58mth9dgt3b5c12a-27a3-42ed-8951-6661d19edf64" horizontalFill="true" viewOnlyField="true"/>
          <msh:textField property="tiamin" label="Витамин B1, (мг)" guid="854mth9dgt3b5c12a-27a3-42ed-8951-6661d19edf64" horizontalFill="true" viewOnlyField="true"/>
        </msh:row>
        <msh:row guid="71my1ddh49d7-1dd4-4af3-be58-9b1d7669vyt61c203">
          <msh:textField property="riboflavin" label="Витамин B2, (мг)" guid="mt91h9dgt3b5c12a-27a3-42ed-8951-6661d19edf64" horizontalFill="true" viewOnlyField="true"/>
          <msh:textField property="nicotinamid" label="Витамин PP, (мг)" guid="5m6t5h9dgt3b5c12a-27a3-42ed-8951-6661d19edf64" horizontalFill="true" viewOnlyField="true"/>
          <msh:textField property="cevitamicAcid" label="Витамин C, (мг)" guid="n5mth9dgt3b5c12a-27a3-42ed-8951-6661d19edf64" horizontalFill="true" viewOnlyField="true"/>
        </msh:row>
        <msh:row guid="71my1ddhv49d7-1dd4-4af3-be58-9b1d7669vyt61c3">
          <msh:separator colSpan="6" label="Минералы" guid="5m2y8a3gyr5y3bf6-7fee-45e5-96ec-7b019182cfcb" />
        </msh:row>
        <msh:row guid="71my1ddh49d7-1dd4-4af3-be58-9b1d766ht9vyt61c31l">
          <msh:textField property="potassium" label="Калий, (мг)" guid="m2th589dgt3b5c12a-27a3-42ed-8951-6661d19edf64" horizontalFill="true" viewOnlyField="true"/>
          <msh:textField property="natrium" label="Натрий, (мг)" guid="m2th5t8h9dgt3b5c12a-27a3-42ed-8951-6661d19edf64" horizontalFill="true" viewOnlyField="true"/>
          <msh:textField property="calcium" label="Кальций, (мг)" guid="2m2th589dgt3b5c12a-27a3-42ed-8951-6661d19edf64" horizontalFill="true" viewOnlyField="true"/>
        </msh:row>
        <msh:row guid="71xmy1ddh49d7-1dd4-4af3-be58-9b1d766ht9vyt61c31l">
          <msh:textField property="magnesium" label="Магний, (мг)" guid="23m2th5t8h9dgt3b5c12a-27a3-42ed-8951-6661d19edf64" horizontalFill="true" viewOnlyField="true"/>
          <msh:textField property="phosphorus" label="Фосфор, (мг)" guid="55dm2th589dgt3b5c12a-27a3-42ed-8951-6661d19edf64" horizontalFill="true" viewOnlyField="true"/>
          <msh:textField property="ferrum" label="Железо, (мг)" guid="mn22th5t8h9dgt3b5c12a-27a3-42ed-8951-6661d19edf64" horizontalFill="true" viewOnlyField="true"/>
        </msh:row>
        <msh:submitCancelButtonsRow guid="submitCancel" colSpan="6" />
      </msh:panel>
    </msh:form>
  </tiles:put>
  <tiles:put name="title" type="string">
    <ecom:titleTrail guid="titleTrail-123" mainMenu="Diet" beginForm="diet_dishFoodStuffForm" />
  </tiles:put>
  <tiles:put name="side" type="string">
    <msh:sideMenu guid="sideMenu-123">
      <msh:sideLink action="/entityList-diet_diet.do?id=-1" name="Список диет" guid="b54df909-ed7d-4bb5-8d55-e018f83bcf4b" />
      <msh:sideLink action="/entityList-diet_dish.do" name="Список блюд" guid="djfb54df909-ed7d-4bb5-8d55-e018f83bcf4b" />
      <msh:sideLink guid="sideLinkEdit" key="ALT+2" params="id" action="/entityEdit-diet_dishFoodStuff" name="Изменить" roles="/Policy/Mis/DishFoodStuff/Edit" />
      <msh:sideLink guid="sideLinkDelete" key="ALT+DEL" confirm="Удалить?" params="id" action="/entityParentDeleteGoParentView-diet_dishFoodStuff" name="Удалить" roles="/Policy/Mis/DishFoodStuff/Delete" />
    </msh:sideMenu>
  </tiles:put>
</tiles:insert>

