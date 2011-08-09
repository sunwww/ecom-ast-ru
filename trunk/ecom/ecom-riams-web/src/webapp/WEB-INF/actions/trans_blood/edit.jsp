<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://struts.apache.org/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://www.nuzmsh.ru/tags/msh" prefix="msh" %>
<%@ taglib uri="http://www.ecom-ast.ru/tags/ecom" prefix="ecom" %>

<tiles:insert page="/WEB-INF/tiles/mainLayout.jsp" flush="true">

  <tiles:put name="side" type="string">
    <msh:ifFormTypeIsView formName="trans_bloodForm" guid="e20545-4285-a21c-3bb9b4569efc">
      <msh:sideMenu guid="9ec15353-1f35-4c18-b99d-e2b63ecc60c9" title="Переливание крови">
        <msh:sideLink key="ALT+2" params="id" action="/entityParentEdit-trans_blood" name="Изменить" roles="/Policy/Mis/MedCase/Transfusion/Blood/Edit" guid="5a1450f5-7629-4458-b5a5-e5566af6a914" />
        <msh:sideLink key="ALT+DEL" confirm="Удалить?" params="id" action="/entityParentDelete-trans_blood" name="Удалить" roles="/Policy/Mis/MedCase/Transfusion/Blood/Delete" guid="7767f5b6-c131-47f4-b8a0-2604050c450f" />
      </msh:sideMenu>
    </msh:ifFormTypeIsView>
  </tiles:put>
  <tiles:put name="body" type="string">
    <!-- 
    	  - Переливание крови
    	  -->
    <msh:form action="/entityParentSave-trans_blood.do" defaultField="journalNumber" guid="be2c889f-ed1d-4a2b-9cda-9127e9d94885">
      <msh:hidden property="id" guid="d10f460a-e434-45a5-90f0-b0a7aed00ec6" />
      <msh:hidden property="medCase" guid="9d90-e051-4d0a-8da6-3f5f4b226493" />
      <msh:hidden property="saveType" guid="bd322f07-c944-4587-a963-a09db2b93caf" />
      <msh:panel guid="d1cd0310-bf53-4ce1-9dd5-06388b51ec01" colsWidth="5%,20%,5%,20%">
        <msh:row guid="d758c040-4a94-4574-9f0b-4a1600a616fa">
          <msh:textField property="journalNumber" label="Номер в журнале" guid="aae4294a-21c3-4b10-830d-3c4e0c011a8c" />
          <msh:textField property="startDate" label="Дата" guid="f2b07ded-35d6-4f81-a1c6-a6ef27536158" />
        </msh:row>
        <msh:row guid="c374ff1e-e600-46a1-8bdb-55cdfec5a91b">
          <msh:separator label="Препарат крови" colSpan="4" guid="3062452d-a160-4b5d-aa76-8438eb054b03" />
        </msh:row>
        <msh:row guid="7fc751c1-21f2-49d8-9330-a0166cb">
          <msh:textField property="donor" label="Донор" guid="150d273e-a6545" fieldColSpan="3" horizontalFill="true" />
        </msh:row>
        <msh:row guid="04d8e4e9-b027-4bd1-acc5-e56a20946510">
          <msh:autoComplete property="bloodPreparation" label="Препарат крови" guid="a1f275e2-6780-4c50-a4e9-b1da5b3e15a9" horizontalFill="true" vocName="vocBloodPreparation" fieldColSpan="3" />
        </msh:row>
        <msh:row guid="5edd4c78-6105-4726-acba-cc999e71ed78">
          <msh:textField property="preparationDate" label="Дата приготовления" guid="62ec8453-03b4-4607-b3bf-809852d56564" />
          <msh:textField property="series" label="Серия" guid="fd59275f-0cb5-4959-b7d7-b49a701eb2ee" />
        </msh:row>
        <msh:row guid="d6e5aefa-913f-49a2-98e6-412a2de32e60">
          <msh:textField property="preparator" label="Изготовитель" guid="ec1d25e2-69fc-4064-bbc0-e95f273da0f4" horizontalFill="true" fieldColSpan="3" />
        </msh:row>
        <msh:row guid="1ab389cd-ac92-4bd7-96a8-cfb1b5755ebe">
          <msh:autoComplete property="preparationBloodGroup" label="Група крови препарата" vocName="vocBloodGroup" guid="c4581345-c5cd-4934-b99d-a4ba4571f4f8" horizontalFill="true" />
          <msh:autoComplete property="preparationRhesusFactor" label="Резус-фактор препарата" vocName="vocRhesusFactor" guid="f11b7586-7f17-4dd0-b638-c9ed408cc089" horizontalFill="true" />
        </msh:row>
        <msh:row guid="670226c3-f555-4f88-a135-b626dd1a11fe">
          <msh:separator label="Пациент" colSpan="4" guid="de581aae-1ede-45c6-b98e-273704344e8e" />
        </msh:row>
        <msh:row guid="b9e2538c-06b1-4ca3-8474-e026278b21b2">
          <msh:textField property="reason" label="Показания к применению" guid="2d972bf4-8bd1-4417-8631-6f794deb3fca" fieldColSpan="3" horizontalFill="true" />
        </msh:row>
        <msh:row guid="56ac5af5-0459-4d3b-b0aa-47d605e1f560">
          <msh:autoComplete property="patientBloodGroup" label="Группа крови пациента" vocName="vocBloodGroup" guid="6abd2322-b680-40ce-a737-da045d57f2f5" horizontalFill="true" />
          <msh:autoComplete property="patientRhesusFactor" label="Резус-фактор пациента" vocName="vocRhesusFactor" guid="c1d467f4-5d06-4e5f-b96d-6dc5cc22852d" horizontalFill="true" />
        </msh:row>
        <msh:row guid="8ca34f97-7080-42fc-82fe-3ccced0d289e">
          <msh:separator label="Информация о переливании" colSpan="4" guid="43c6f5c7-a52f-4da5-89ac-02b5a5c35986" />
        </msh:row>
        <msh:row guid="474922ed-c1b5-4181-8f51-b023cafa4ab0">
          <msh:textField property="doze" label="Доза (мл)" guid="c6bb90c2-93f3-4327-9e5e-bfded8167521" horizontalFill="true" />
          <msh:checkBox property="primaryCase" label="Первичное" guid="fc28910a-a203-44f5-bc4c-689d7e2e5627" />
        </msh:row>
        <msh:row guid="b5c84770-189d-4dca-83ee-0f4c83a5ed52">
          <msh:autoComplete property="transfusionMethod" label="Способ переливания" vocName="vocTransfusionMethod" guid="00d18127-4616-4339-846c-6d38a1df71c5" horizontalFill="true" fieldColSpan="3" />
        </msh:row>
        <msh:row guid="4cbe96c9-d023-4ed7-9d9b-2d5960458199">
          <msh:autoComplete property="transfusionReaction" label="Трансфузионная реакция" vocName="vocTransfusionReaction" guid="e2c027c4-2b6c-451a-a02d-4a7efc593dbb" fieldColSpan="3" horizontalFill="true" />
        </msh:row>
        <msh:row guid="7fc751c1-21f2-49d8-9330-a0166cb2cd56">
          <msh:textField property="complications" label="Осложнения после переливания" guid="150d273e-b9d4-47f2-ab6c-94d406fa6545" fieldColSpan="3" horizontalFill="true" />
        </msh:row>
        <msh:row guid="8cf0863d-991d-4e6c-bb0a-ead66299a21c">
          <msh:autoComplete property="executor" label="Исполнитель" vocName="workFunction" guid="56067cb3-f8bd-4c07-9330-ad6ffee3e83a" fieldColSpan="3" horizontalFill="true" />
        </msh:row>
        <msh:submitCancelButtonsRow colSpan="" guid="6bece8ec-9b93-4faf-b729-851f1447d54f" />
      </msh:panel>
    </msh:form>
  </tiles:put>
  <tiles:put name="title" type="string">
    <ecom:titleTrail mainMenu="Patient" beginForm="trans_bloodForm" guid="fb43e71c-1ba9-4e61-8632-a6f4a72b461c" />
  </tiles:put>
</tiles:insert>

