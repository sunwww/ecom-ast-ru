<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@ taglib uri="http://struts.apache.org/tags-tiles" prefix="tiles"%>
<%@ taglib uri="http://www.nuzmsh.ru/tags/msh" prefix="msh"%>
<%@ taglib uri="http://www.ecom-ast.ru/tags/ecom" prefix="ecom"%>

<tiles:insert page="/WEB-INF/tiles/mainLayout.jsp" flush="true">

  <tiles:put name="body" type="string">
    <!-- 
    	  - Проба
    	  -->
    <msh:form guid="formHello" action="entitySaveGoView-diet_vocFoodStuff.do" defaultField="hello">
      <msh:hidden guid="hiddenId" property="id" />
      <msh:hidden guid="hiddenSaveType" property="saveType" />
      <msh:panel guid="panel">
        <msh:row guid="row1" />
        <msh:textField property="name" label="Наименование" guid="84e7-0a81-4762-9864-69b" fieldColSpan="5" horizontalFill="true"  />
        <msh:row guid="b5f45-b971-441e-9a90-51a8" />
        <msh:row guid="7n49hg8gd7-1dd4-4af3-be58-9b1c3">
          <msh:separator colSpan="6" label="Белки" guid="5kff6-7fee-45e5-96ec-7b0282cfcb" />
        </msh:row>
        <msh:row guid="71254d7-1dd4-4af3-be58-d217c3">
        <msh:textField property="proteins" label="Белки, (г)" guid="e73a-c6c2-4221-bb7" />
        <msh:textField property="plantProteins" label="Растительные, (г)" guid="2f2a-27a3-42ed-8951-666ff64" />
          <msh:textField property="animalProteins" label="Животные, (г)" guid="8259a-27a3-42ed-8951-6f9edf64" />
        </msh:row>
        <msh:row guid="785kf-1dd4-4af3-be58-9b9661c3">
          <msh:separator colSpan="6" label="Жиры" guid="52167-7fee-45e5-96ec-7b1232cfcb" />
        </msh:row>
        <msh:row guid="71246fd7-1dd4-4af3-be58-d217c3">
          <msh:textField property="lipids" label="Жиры, (г)" guid="828-2e93-418b-ae23-8987a5" horizontalFill="false" />
          <msh:textField property="plantLipids" label="Растительные, (г)" guid="2512a-27a3-42ed-8951-2325" />
          <msh:textField property="animalLipids" label="Животные, (г)" guid="8259a-27a3-42ed-8951-61789f4" />
        </msh:row>
        <msh:row guid="7n456d7-1dd4-4af3-be58-9b13">
          <msh:separator colSpan="6" label="Углеводы" guid="53574bf6-7fee-45e5-96ec-7b0gf19182cfcb" />
        </msh:row>
        <msh:row guid="a7147d8f-4b8c-4bff-8fa1-ef951487">
        <msh:textField property="carbohydrates" label="Углеводы, (г)" guid="e8d-9c62-475e-a340-334627" horizontalFill="false" />
         </msh:row>
        <msh:row guid="b5f4b-b971-441e-9a90-5194" />
        <msh:textField property="calorieContent" label="Калорийность" guid="e73a-c6c2-4221-bb72-7217" />
        <msh:row guid="71b47-1dd4-4af3-be58-9b1d76c3">
          <msh:separator colSpan="4" label="Витамины" guid="5mbf6-7fee-45e5-96ec-7b0191" />
        </msh:row>
        <msh:row guid="71my7-1dd4-4af3-be58-9bt61c3">
          <msh:textField property="retinol" label="Витамин А, (мг)" guid="m3b5c12a-27a3-42ed-8951-669edf64" horizontalFill="true" />
          <msh:textField property="betaCarotin" label="B-каротин, (мг)" guid="58b5c12a-27a3-42ed-8951-66df64" horizontalFill="true" />
          <msh:textField property="tiamin" label="Витамин B1, (мг)" guid="854c12a-27a3-42ed-8951-619edf64" horizontalFill="true" />
        </msh:row>
        <msh:row guid="71dh49d7-1dd4-4af3-b58-9b1d03">
          <msh:textField property="riboflavin" label="Витамин B2, (мг)" guid="mtt3b5c12a-27a3-42ed-8951-6661df64" horizontalFill="true" />
          <msh:textField property="nicotinamid" label="Витамин PP, (мг)" guid="5ma-27a3-42ed-8951-666d19edf64" horizontalFill="true" />
          <msh:textField property="cevitamicAcid" label="Витамин C, (мг)" guid="2-27a3-42ed-8951-666df64" horizontalFill="true" />
        </msh:row>
        <msh:row guid="79d7-1dd4-4af3-be58-9b3">
          <msh:separator colSpan="4" label="Минералы" guid="5mf6-7fee-45e5-96ec-7b0fcb" />
        </msh:row>
        <msh:row guid="71my49d7-1dd4-4af3-be58-93l">
          <msh:textField property="potassium" label="Калий, (мг)" guid="m2ha-27a3-42ed-8951-664" horizontalFill="true" />
          <msh:textField property="natrium" label="Натрий, (мг)" guid="m12a-27a3-42ed-8951-6661d19edf64" horizontalFill="true" />
          <msh:textField property="calcium" label="Кальций, (мг)" guid="212a-27a3-42ed-8951-6661df64" horizontalFill="true" />
        </msh:row>
        <msh:row guid="71x4-4af3-be58-9b1d766h31l">
          <msh:textField property="magnesium" label="Магний, (мг)" guid="2t3b5c12a-27a3-42ed-8951-6661d19edf64" horizontalFill="true" />
          <msh:textField property="phosphorus" label="Фосфор, (мг)" guid="55ddgt3b5c12a-27a3-42ed-8951-669edf64" horizontalFill="true" />
          <msh:textField property="ferrum" label="Железо, (мг)" guid="mn2c12a-27a3-42ed-8951-6664" horizontalFill="true" />
        </msh:row>
        <msh:submitCancelButtonsRow guid="submitCancel" colSpan="6" />
      </msh:panel>
    </msh:form>
    <msh:ifFormTypeIsView formName="diet_vocFoodStuffForm" guid="8c9ed4403e-3dda-408e-85f8-7d8081f">
      <msh:section guid="b6be3-5330-4589-8da8-78d53a" title="Список шаблонов">
        <ecom:parentEntityListAll formName="diet_vocFoodStuffTemplateForm" attribute="templates" guid="3v07-4d9b-4ddc-99f5-5vb48447" />
        <msh:table hideTitle="false" idField="id" name="templates" action="entityParentView-diet_vocFoodStuffTemplate.do" guid="6cf5-03a5-43de-8754-3fafde17">
          <msh:tableColumn columnName="Продукт" property="foodStuffText" guid="da4e58036-c475-4178-bdc6-7a6056818c04" />
          <msh:tableColumn columnName="Брутто" identificator="false" property="gross" guid="90be005fc-2d25-46d1-b850-460c0bc6b0e0" />
          <msh:tableColumn columnName="Месяц начала действия" identificator="false" property="monthStartText" guid="69hdf-29f8-46e0-8c53-8b733b61843e" />
          <msh:tableColumn columnName="Месяц окончания действия" identificator="false" property="monthEndText" guid="7h79ce237-6e75-4113-8f8b-fahh1c" />
        </msh:table>
      </msh:section>
    </msh:ifFormTypeIsView>
  </tiles:put>
  <tiles:put name="title" type="string">
    <ecom:titleTrail guid="titleTrail-123" mainMenu="Diet" beginForm="diet_vocFoodStuffForm" />
  </tiles:put>
  <tiles:put name="side" type="string">
    <msh:sideMenu guid="sideMenu-123">
      <msh:sideLink action="/entityList-diet_diet.do?id=-1" name="Список диет" guid="b509-ed7d-4bb5-8d55-e0183bcf4b" />
      <msh:sideLink action="/entityList-diet_dish.do" name="Список блюд" guid="djfb59-ed7d-4bb5-8d55-e018f4b" />
      <msh:sideLink guid="sideLinkEdit" key="ALT+2" params="id" action="/entityEdit-diet_vocFoodStuff" name="Изменить" roles="/Policy/Mis/InvalidFood/VocFoodStuff/Edit" />
      <msh:sideLink guid="sideLinkDelete" key="ALT+DEL" confirm="Удалить?" params="id" action="/entityDelete-diet_vocFoodStuff" name="Удалить" roles="/Policy/Mis/InvalidFood/VocFoodStuff/Delete" />
    </msh:sideMenu>
    <msh:sideMenu title="Добавить" guid="c13252a-27a3-42ed-8951-62596">
      <msh:sideLink key="ALT+2" params="id" action="/entityParentPrepareCreate-diet_vocFoodStuffTemplate" name="Шаблон" roles="/Policy/Mis/InvalidFood/VocFoodStuffTemplate/Create" guid="2234a-27a3-42ed-8951-6256" />
    </msh:sideMenu>
  </tiles:put>
</tiles:insert>

