<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://struts.apache.org/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://www.nuzmsh.ru/tags/msh" prefix="msh" %>
<%@ taglib uri="http://www.ecom-ast.ru/tags/ecom" prefix="ecom" %>

<tiles:insert page="/WEB-INF/tiles/mainLayout.jsp" flush="true">

  <tiles:put name="title" type="string">
    <ecom:titleTrail mainMenu="Patient" beginForm="stac_infectiousMessagesForm" guid="638ddd30-b48e-4058-b3ad-866c0c70ee1f" />
  </tiles:put>
  <tiles:put name="side" type="string">
    <msh:ifFormTypeIsView formName="stac_infectiousMessagesForm" guid="e2054544-fdd1-4285-a21c-3bb9b4569efc">
      <msh:sideMenu guid="9ec15353-1f35-4c18-b99d-e2b63ecc60c9">
        <msh:sideLink key="ALT+2" params="id" action="/entityParentEdit-stac_infectiousMessages" name="Изменить" roles="/Policy/Mis/MedCase/Stac/Ssl/InfectiousMessages/Edit" guid="5a1450f5-7629-4458-b5a5-e5566af6a914" />
        <msh:sideLink key="ALT+DEL" confirm="Удалить?" params="id" action="/entityParentDelete-stac_infectiousMessages" name="Удалить" roles="/Policy/Mis/MedCase/Stac/Ssl/InfectiousMessages/Delete" guid="7767f5b6-c131-47f4-b8a0-2604050c450f" />
        <msh:sideLink key="ALT+3" params="id" action="/entityParentListRedirect-stac_infectiousMessages" name="⇧Cписок сообщений об инфекции" roles="/Policy/Mis/MedCase/Stac/Ssl/InfectiousMessages/View" guid="5a1450f5-7629-4458-b5a5-e5566af6a914" />
      </msh:sideMenu>
      <msh:sideMenu title="Дополнительно" guid="b458838c-52f8-4432-9e21-51739a7f0bdb">
        <msh:sideLink action="/mis_patients" name="Новая госпитализация" guid="5d0018ac-45a5-4ff1-b5fb-88625ef19d57" />
      </msh:sideMenu>
    </msh:ifFormTypeIsView>
  </tiles:put>
  <tiles:put name="body" type="string">
    <!-- 
    	  - Сообщения об инфекции
    	  -->
    <msh:form action="/entityParentSave-stac_infectiousMessages.do?sslid=${param.sslid}" defaultField="dateStart" guid="b37b5d4a-9687-45bf-b42c-613fd9b1739d" editRoles="/Policy/Mis/MedCase/Stac/Ssl/InfectiousMessages/Edit" createRoles="/Policy/Mis/MedCase/Stac/Ssl/InfectiousMessages/Create">
      <msh:hidden property="id" guid="c5a9c42c-d593-4fae-a685-44586a81938b" />
      <msh:hidden property="saveType" guid="7cb7bcb7-2cb3-4e65-92ce-2a4a2cec809f" />
      <msh:hidden property="medCase" guid="a5b77824-a218-425d-b687-89c701cf512d" />
      <msh:hidden property="phoneMessageType" guid="f7722f8f-876e-4972-b6f4-8c26295a3119" />
      <msh:panel guid="6e8d827a-d32c-4a05-b4b0-5ff7eed6eedc">
        <msh:separator colSpan="8" label="Сообщение об инфекции" guid="eaa65fd8-de5f-4532-9551-848489974e9a" />
        <msh:row guid="0e91a1ca-c366-435c-8f2c-274d23d87fd3">
          <msh:textField property="phoneDate" label="Дата регистрации" guid="e3fd4642-a532-4510-a528-c6e766328d61" />
          <msh:textField property="phoneTime" label="время" guid="f94ff57c-bbf9-44f8-9e8d-f21927edbcff" />
        </msh:row>
        <msh:row guid="a53d1f37-afe8-4779-9e63-0b2684e14828">
          <msh:textField property="phone" label="Телефон" guid="c44b474f-6dba-4ba8-9af7-56a0dca363ad" horizontalFill="true" />
          <msh:textField property="number" label="Номер сообщения" guid="51e5754c-2356-4ef6-91b2-9634893cc329" />
        </msh:row>
        <msh:row guid="cff159af-0e93-42de-b29d-b68c05e371d0">
          <msh:textField property="recieverOrganization" label="Принявшая сообщение организация" guid="7d35c85f-1f39-48c1-b48c-0b8fd4da28e3" horizontalFill="true" fieldColSpan="3" />
        </msh:row>
        <msh:row guid="f99e7379-1503-44ae-8d45-184ada064f84">
          <msh:textField property="recieverFio" label="Фамилия принявшего" guid="ca12abf3-6c85-4bf8-a4f4-cbfbb8c184a9" horizontalFill="true" fieldColSpan="3" />
        </msh:row>
        <msh:row guid="ffc77c31-8e93-48fc-b927-1c62e69cda44">
          <msh:autoComplete property="workFunction" label="Передавший сообщение специалист" vocName="workFunction" guid="f1dab596-6c7d-4cb4-848b-71b62bd6bf3a" horizontalFill="true" fieldColSpan="3" />
        </msh:row>
        <msh:submitCancelButtonsRow guid="submitCancel" colSpan="4" labelSave="Сохранить изменения" labelCreating="Создание" labelCreate="Создать новый случай" labelSaving="Сохранение данных" />
      </msh:panel>
    </msh:form>
  </tiles:put>
</tiles:insert>

