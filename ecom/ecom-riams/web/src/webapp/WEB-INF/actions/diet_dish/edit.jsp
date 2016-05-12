<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://struts.apache.org/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://www.nuzmsh.ru/tags/msh" prefix="msh" %>
<%@ taglib uri="http://www.ecom-ast.ru/tags/ecom" prefix="ecom" %>

<tiles:insert page="/WEB-INF/tiles/mainLayout.jsp" flush="true">

  <tiles:put name="body" type="string">
    <!-- 
    	  - Проба
    	  -->
    <msh:form guid="886bd847-1725-44c0-898b-db8de7" action="entitySaveGoView-diet_dish.do" defaultField="hello" title="Блюдо">
      <msh:hidden guid="hiddenId" property="id" />
      <msh:hidden guid="hiddenSaveType" property="saveType" />
      <msh:panel guid="panel">
        <msh:row guid="771015eb-1c51-4f0d-ab04-0b472dd0615" />
        <msh:row guid="2f701a74-2ed8-4e0e-9222-68ddee9d4c23">
          <msh:textField property="dishNumber" label="Номер карточки-раскладки" guid="d0b8d134-03ef-4bb6-b254-39ce482b4a4b" />
          <msh:autoComplete vocName="vocDishType" property="dishType" label="Тип блюда" guid="298c3187-d323-4506-afb0-043519f9e597" horizontalFill="true" fieldColSpan="5" />
        </msh:row>
        <msh:row guid="c6e4432f-6e8d-4d1d-b101-518209158bbf">
          <msh:textField property="name" size="40" label="Наименование" guid="c0150729-615a-4049-98b2-fe67ec2b36c4" fieldColSpan="5" horizontalFill="true" />
        </msh:row>
        <msh:row guid="c6it8ety8u4432f-6e8d-4d1d-b101-518209158bbf">
          <ecom:oneToManyOneAutocomplete vocName="Diet" label="Диеты" property="diets" />
        </msh:row>
        <msh:row guid="7n49ntlgd7-1dd4-4af3-be58-9b1d7669vyt61c3">
          <msh:separator colSpan="6" label="Белки" guid="5kfn54bf6-7fee-45e5-96ec-7b0gf19182cfcb" />
        </msh:row>
        <msh:row guid="71mkfd7-1dd4-4af3-be58-d7669vyt61c3">
          <msh:textField property="proteins" label="Всего, (г)" guid="mc1232a-27a3-42ed-8951-6661d19edf64" viewOnlyField="true" />
          <msh:textField property="plantProteins" label="Растительные, (г)" guid="25812a-27a3-42ed-8951-6661d19edf64" viewOnlyField="true" />
          <msh:textField property="animalProteins" label="Животные, (г)" guid="8259a-27a3-42ed-8951-6661d19edf64" viewOnlyField="true" />
        </msh:row>
        <msh:row guid="7n13kf-1dd4-4af3-be58-9b1d7669vyt61c3">
          <msh:separator colSpan="6" label="Жиры" guid="52167-7fee-45e5-96ec-7b0gf19182cfcb" />
        </msh:row>
        <msh:row guid="71mkfd7-1dd4-4af3-be58-d21387c3">
          <msh:textField property="lipids" label="Всего, (г)" guid="mc1232a-27a3-42ed-8951-nhfyridhd4" viewOnlyField="true" />
          <msh:textField property="plantLipids" label="Растительные, (г)" guid="2512a-27a3-42ed-8951-258df64" viewOnlyField="true" />
          <msh:textField property="animalLipids" label="Животные, (г)" guid="8259a-27a3-42ed-8951-6169hf4" viewOnlyField="true" />
        </msh:row>
        <msh:row guid="7n49nt647lgd7-1dd4-4af3-be58-9b1d7669vyt61c3">
          <msh:separator colSpan="6" label="Углеводы" guid="5kf93n54bf6-7fee-45e5-96ec-7b0gf19182cfcb" />
        </msh:row>
        <msh:row guid="a7147d8f-4b8c-4bff-8fa1-ef72616b3487">
          <msh:textField passwordEnabled="false" hideLabel="false" property="carbohydrates" viewOnlyField="true" label="Всего, (г)" guid="0e74d288-1061-4b0d-98c7-14a0a587099e" />
        </msh:row>
        <msh:row guid="7n49d7-1dd4-4af3-be58-9b1d7669vyt61c3">
          <msh:separator colSpan="6" label="Витамины" guid="5bf6-7fee-45e5-96ec-7b019182cfcb" />
        </msh:row>
        <msh:row guid="71my1ddh49d7-1dd4-4af3-be58-d7669vyt61c3">
          <msh:textField property="retinol" label="Витамин А, (мг)" guid="mc12a-27a3-42ed-8951-6661d19edf64" viewOnlyField="true" />
          <msh:textField property="betaCarotin" label="B-каротин, (мг)" guid="nfhc12a-27a3-42ed-8951-6661d19edf64" viewOnlyField="true" />
          <msh:textField property="tiamin" label="Витамин B1, (мг)" guid="854mf9a-27a3-42ed-8951-6661d19edf64" viewOnlyField="true" />
        </msh:row>
        <msh:row guid="71my1ddh49d7-1dd4-4af3-be58-9fd61c203">
          <msh:textField property="riboflavin" label="Витамин B2, (мг)" guid="125c12a-27a3-42ed-8951-6661d19edf64" viewOnlyField="true" />
          <msh:textField property="nicotinamid" label="Витамин PP, (мг)" guid="52583b5c12a-27a3-42ed-8951-6661d19edf64" viewOnlyField="true" />
          <msh:textField property="cevitamicAcid" label="Витамин C, (мг)" guid="4t3b5c12a-27a3-42ed-8951-6661d19edf64" viewOnlyField="true" />
        </msh:row>
        <msh:row guid="71my1ddhv49d7-1dd4-4af3-be58-9b1jfjfjf3">
          <msh:separator colSpan="6" label="Минералы" guid="f8a3gyr5y3bf6-7fee-45e5-96ec-7b019182cfcb" />
        </msh:row>
        <msh:row guid="71my1ddh49d7-1dd4-4af3-be58-9b1d1">
          <msh:textField property="potassium" label="Калий, (мг)" guid="1212a-27a3-42ed-8951-6661d19edf64" viewOnlyField="true" />
          <msh:textField property="natrium" label="Натрий, (мг)" guid="125t3b5c12a-27a3-42ed-8951-6661d19edf64" viewOnlyField="true" />
          <msh:textField property="calcium" label="Кальций, (мг)" guid="158t3b5c12a-27a3-42ed-8951-6661d19edf64" viewOnlyField="true" />
        </msh:row>
        <msh:row guid="71xmy1ddh49d7-1dd4-4af3-be58-9b1d711c31">
          <msh:textField property="magnesium" label="Магний, (мг)" guid="ytt3b5c12a-27a3-42ed-8951-6661d19edf64" viewOnlyField="true" />
          <msh:textField property="phosphorus" label="Фосфор, (мг)" guid="5nggt3b5c12a-27a3-42ed-8951-6661d19edf64" viewOnlyField="true" />
          <msh:textField property="ferrum" label="Железо, (мг)" guid="b3b5c12a-27a3-42ed-8951-6661d19edf64" viewOnlyField="true" />
        </msh:row>
        <msh:separator colSpan="6" label="" guid="5bfsf6-7fee-45e5-96ec-7b019182cfcb" />
        <msh:row guid="c6ekf4432f-6e8d-4d1d-b101-518209158bbf">
          <msh:textField passwordEnabled="false" hideLabel="false" property="weight" viewOnlyField="false" label="Выход готового блюда" guid="622424a5-7354-461b-837e-11b09763a83a" horizontalFill="false" />
          <msh:textField passwordEnabled="false" hideLabel="false" property="calorieContent" viewOnlyField="false" label="Энергоценность" guid="a99dab8e-9064-428b-ba1f-748296eaad7b" horizontalFill="false" />
        </msh:row>
        <msh:separator colSpan="6" label="Бракераж" guid="5a3gyr5y3bf6-7fee-45e5-96ec-7b019182cfcb" />
        <msh:row guid="711ddh49d7-1dd4-4af3-be58-9b1d7669vyt61c3">
          <msh:textField property="dishAppearance" label="Внешний вид блюда" guid="9dgt3b5c12a-27a3-42ed-8951-6661d19edf64" horizontalFill="true" fieldColSpan="5" />
        </msh:row>
        <msh:row guid="7tr11ddh49d7-1dd4-4af3-be58-9b1d7669g61c3">
          <msh:textField property="dishColor" label="Цвет блюда" guid="9dgto3b5c12a-27a3-42ed-8951-6661d19edf64" horizontalFill="true" fieldColSpan="5" />
        </msh:row>
        <msh:row guid="711ddh49d7-1dd4-4af3-be58-9b1d766961blc3">
          <msh:textField property="dishScent" label="Запах блюда" guid="8569dgt3b5c12a-27a3-42ed-8951-6661d19edf64" fieldColSpan="5" horizontalFill="true" />
        </msh:row>
        <msh:row guid="711ddh49d7-1dd4-4af3-be58-9b1d7669kv61c3">
          <msh:textField property="dishTaste" label="Вкус блюда" guid="28589dgt3b5c12a-27a3-42ed-8951-6661d19edf64" fieldColSpan="5" horizontalFill="true" />
        </msh:row>
        <msh:row guid="711ddh49d7-1dd4-4af3-be58-9b1d766ck961c3">
          <msh:textField property="dishConsistence" label="Консистенция блюда" guid="9dgt365b52c12a-27a3-42ed-8951-6661d19edf64" fieldColSpan="5" horizontalFill="true" />
        </msh:row>
        <msh:row guid="711ddh49d7-1dd4-4af3-be58-9b1d7669th61c3">
          <msh:textArea property="dishStorageConditions" label="Срок годности и условия хранения блюда" guid="9dg258647t3b5c12a-27a3-42ed-8951-6661d19edf64" rows="2" fieldColSpan="5" horizontalFill="true" />
        </msh:row>
        <msh:separator colSpan="6" label="Технология приготовления" guid="5agy3gyr5y3bf6-7fee-45e5-96ec-7b019182cfcb" />
        <msh:row guid="71cx1ddh49d7-1dd4-4af3-be58-9b1d7669vyt61c3">
          <msh:textArea property="preparationTechnology" label="Технология приготовления" guid="9dbrlgt3b5c12a-27a3-42ed-8951-6661d19edf64" horizontalFill="true" rows="30" fieldColSpan="5" size="1500" />
        </msh:row>
        <msh:submitCancelButtonsRow guid="submitCancel" colSpan="4" />
      </msh:panel>
    </msh:form>
    <msh:ifFormTypeIsView formName="diet_dishForm" guid="8c94403e-3dda-408e-85f8-78081f">
      <msh:section guid="b67c5be3-5330-4589-8da8-7888453a" title="Список продуктов">
        <ecom:parentEntityListAll formName="diet_dishFoodStuffForm" attribute="dishFoodStuffs" guid="31a8c507-4d9b-4ddc-99f5-5e3a07b48447" />
        <msh:table hideTitle="false" idField="id" name="dishFoodStuffs" action="entityParentView-diet_dishFoodStuff.do" guid="6caaa435-03a5-43de-8754-3fa4d54ede17">
          <msh:tableColumn columnName="Продукт" property="name" guid="da458036-c475-4178-bdc6-7a6056818c04" />
          <msh:tableColumn columnName="Брутто" identificator="false" property="gross" guid="90b005fc-2d25-46d1-b850-460c0bc6b0e0" />
          <msh:tableColumn columnName="Нетто" identificator="false" property="net" guid="691b39df-29f8-46e0-8c53-8b733b61843e" />
          <msh:tableColumn columnName="Белки" identificator="false" property="proteins" guid="c5098153-358d-43a4-920a-a3e73aa6e82b" />
          <msh:tableColumn columnName="Белки" identificator="false" property="proteins" guid="a0288b40-636e-440e-bbe2-70a3dfdf53b2" />
          <msh:tableColumn columnName="А" identificator="false" property="retinol" guid="779ce237-6e75-4113-8f8b-fad25cc9221c" />
          <msh:tableColumn columnName="B-каротин" identificator="false" property="betaCarotin" guid="3123eac7-ef2a-4a48-a849-e51b57416ede" />
          <msh:tableColumn columnName="B1" identificator="false" property="tiamin" guid="25d844ad-a011-4433-8353-2d92ddb544eb" />
          <msh:tableColumn columnName="B2" identificator="false" property="riboflavin" guid="4086934c-385d-47f6-a3ec-cffc1835cb11" />
          <msh:tableColumn columnName="PP" identificator="false" property="nicotinamid" guid="acf122c5-fdae-44dd-a5e5-5270d0044aac" />
          <msh:tableColumn columnName="C" identificator="false" property="cevitamicAcid" guid="3a6a0e52-8f7e-4e90-9cf9-02edc179e802" />
          <msh:tableColumn columnName="K" identificator="false" property="potassium" guid="4ffed57b-6e4c-46aa-9cfe-84b9998f6214" />
          <msh:tableColumn columnName="Na" identificator="false" property="natrium" guid="8e690349-859c-442b-8a34-176317dc7ba6" />
          <msh:tableColumn columnName="Ca" identificator="false" property="calcium" guid="59857a91-e6e2-4a21-bbb5-70e08c4fcdb1" />
          <msh:tableColumn columnName="Mg" identificator="false" property="magnesium" guid="e35d893f-88cb-4eec-8068-0a349fb529f9" />
          <msh:tableColumn columnName="P" identificator="false" property="phosphorus" guid="08571e44-28e5-4ebd-9caa-d3dbf453c11b" />
          <msh:tableColumn columnName="Fe" identificator="false" property="ferrum" guid="28f060bb-f64f-4d7a-bd4f-ad8fbaa998b0" />
        </msh:table>
      </msh:section>
    </msh:ifFormTypeIsView>
  </tiles:put>
  <tiles:put name="title" type="string">
    <ecom:titleTrail guid="titleTrail-123" mainMenu="Diet" beginForm="diet_dishForm" />
  </tiles:put>
  <tiles:put name="side" type="string">
    <msh:sideMenu guid="sideMenu-123">
      <msh:sideLink guid="sideLinkEdit" key="ALT+2" params="id" action="/entityEdit-diet_dish" name="Изменить" roles="/Policy/Mis/Dish/Edit" />
      <msh:sideLink guid="sideLinkDelete" key="ALT+DEL" confirm="Удалить?" params="id" action="/entityDelete-diet_dish" name="Удалить" roles="/Policy/Mis/Dish/Delete" />
    </msh:sideMenu>
    <msh:sideMenu title="Показать" guid="a978d961-2d05-4b5d-abb6-8c0c1a060198">
      <msh:sideLink guid="sideLinkView" params="id" action="/entityList-diet_dish" name="Список блюд" roles="/Policy/Mis/Dish/View" />
    </msh:sideMenu>
    <msh:sideMenu title="Продукт питания" guid="a35dd936-eb5e-476f-a799-74fa38f9983c">
      <msh:sideLink roles="/Policy/Mis/DishFoodStuff/Create" params="id" action="/entityParentPrepareCreate-diet_dishFoodStuff" name="Добавить продукт" title="Добавить новый продукт" guid="d08cbbf2-84a3-4a0d-8d9b-14f4f5c8fe4f" />
    </msh:sideMenu>
  </tiles:put>
</tiles:insert>

