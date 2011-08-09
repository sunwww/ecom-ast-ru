<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://struts.apache.org/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://www.nuzmsh.ru/tags/msh" prefix="msh" %>
<%@ taglib uri="http://www.ecom-ast.ru/tags/ecom" prefix="ecom" %>
<%@ taglib tagdir="/WEB-INF/tags" prefix="tags" %>
<%@ taglib uri="/WEB-INF/mis.tld" prefix="mis" %>

<tiles:insert page="/WEB-INF/tiles/mainLayout.jsp" flush="true">

  <tiles:put name="body" type="string">
    <!-- 
    	  - Случай стационарного лечения
    	  -->
    <msh:form action="/entityParentSaveGoView-stac_sslAllInfo.do" defaultField="dateStart" guid="d9a511ed-3808-4b26-9c6b-c0c4655f3bfb" title="Случай стационарного лечения. ВЫПИСКА">
      <msh:hidden property="id" guid="ca766a3b-4eb3-4c57-8997-68fe5cb52623" />
      <msh:hidden property="patient" guid="7ad1d4c1-b642-4f31-98d4-a22c6cccf6d8" />
      <msh:hidden property="saveType" guid="dab3ef4c-4014-43b7-be41-c2398a50b816" />
      <msh:panel guid="ddf23842-a6ce-4da9-a2e6-1dd53c341551">
        <msh:separator colSpan="8" label="Поступление" guid="7b1b6e55-04dd-449d-9566-c5b6ae80f22e" />
        <msh:row guid="a1e5b5a4-f9c2-4f1b-b0f9-97bd9a7a3815">
          <msh:autoComplete property="orderLpu" label="Кем направлен" guid="d15f0749-51b4-4137-a941-9bdedaf6fe8f" />
          <msh:textField property="orderNumber" label="№ напр" guid="7b2ede2e-6b8e-4fe0-9fab-1b33baf23902" />
          <msh:textField property="orderDate" label="Дата" guid="5c15b74e-a920-4544-a291-ba6f9bef8c59" />
        </msh:row>
        <msh:row guid="ebd8d0e1-cf5c-47b2-acb8-1f12a686090b">
          <msh:autoComplete property="owner" label="Кем доставлен" guid="3a382392-dd9d-4800-9750-72732c409ba8" />
          <msh:textField property="externalId" label="Код " guid="78def119-02c7-4f6e-8787-92fdaee4019e" />
          <msh:textField property="supplyOrderNumber" label="Номер наряда" guid="1dd57703-62e7-4edc-ab0f-680ca1e56cdd" />
        </msh:row>
        <msh:row guid="f0b66cba-5201-44d3-9622-d2330b548aa5">
          <msh:autoComplete property="emergency" label="Экстренность" guid="6ceb1088-f67d-4bc3-8111-769fcda71a4d" />
          <msh:autoComplete property="intoxication" label="Состояние опьянения" vocName="vocIntoxication" guid="4ac3c80b-75ce-4a9e-9920-1c3da7b86dfe" labelColSpan="2" fieldColSpan="2" horizontalFill="true" />
        </msh:row>
        <msh:row guid="7573443b-53d1-413e-8d92-ec53fe182ce6">
          <msh:autoComplete property="preAdmissionTime" label="Доставлен от начала заболевания" vocName="vocPreAdmissionTime" guid="c4abcbe2-e98b-439f-a3f8-0186324ebd95" fieldColSpan="3" horizontalFill="true" labelColSpan="2" />
        </msh:row>
        <msh:row guid="a01e095c-1476-4741-ba52-e0dc7b845d73">
          <msh:autoComplete property="preAdmissionDefect" label="Дефекты догоспитального этапа" vocName="vocPreAdmissionDefect" fieldColSpan="3" horizontalFill="true" guid="69665462-53bb-48b8-9879-694e6b87f256" labelColSpan="2" />
        </msh:row>
        <msh:row guid="367f0639-af6f-49bf-a2f5-e5c483c7c20e">
          <msh:autoComplete property="trauma" label="Травма" guid="14ad94e3-ac4d-408f-a06d-360e2a6c9d16" horizontalFill="true" fieldColSpan="2" vocName="vocTraumaType" />
          <msh:autoComplete viewAction="entityView-mis_worker.do" vocName="vocWorker" property="startWorker" label="Кто начал" fieldColSpan="2" horizontalFill="true" guid="5d2b41ed-14d1-4925-8353-56cb82c56e4c" />
        </msh:row>
        <msh:row guid="255e57b1-2d78-4649-a508-64f2ef44ff1b">
          <msh:textField property="dateStart" label="Дата поступления" guid="792db52a-7c28-402d-b41d-47ab29bef10d" labelColSpan="2" />
          <msh:textField property="entranceTime" label="Время" guid="ebb7c575-9589-44cc-a4b6-a8b8a740ffae" />
        </msh:row>
        <msh:row guid="3d1ee967-33cf-4c0b-88aa-5e03187691dc">
          <msh:autoComplete viewAction="entityView-mis_Worker" vocName="vocWorker" property="owner" label="Владелец" fieldColSpan="3" horizontalFill="true" guid="5d0cff6c-fc80-4469-bcba-a193c123dc16" />
        </msh:row>
        <msh:separator colSpan="8" label="Выписка" guid="597ac93d-a5d0-4b08-a6b1-79efee0f497a" />
        <msh:row guid="8d706895-d889-4310-bdf2-5b3dd6e59e02">
          <msh:textField property="dateFinish" label="Дата выписки (смерти)" guid="d75f478b-cff2-4026-b960-bc642a718b67" />
          <msh:textField property="dischargeTime" label="время" guid="485e9361-af95-4348-a623-2e3514cdf113" />
        </msh:row>
        <msh:row guid="00a4acf1-54a7-4470-8e70-0f4d2cca94d5">
          <msh:autoComplete vocName="vocServiceStream" property="serviceStream" label="Поток обслуживания" fieldColSpan="2" horizontalFill="true" guid="10h64-23b2-42c0-ba47-65p8g16c" />
        </msh:row>
        <msh:row>
          <msh:checkBox property="ambulanceTreatment" label="Амбулаторное лечение" guid="8aa14ab3-7940-4b9d-aa14-2c3ce9fb4e7b" />
          <msh:checkBox property="rareCase" label="Редкий случай" guid="6299a6be-428f-4a095" />
        </msh:row>
        <msh:row guid="92eb5236-3e9f-43d6-b50b-47e7319dcbb3">
          <msh:autoComplete showId="false" hideLabel="false" property="result" viewOnlyField="false" label="Результат" guid="70bca051-7b34-400c-8864-6ff685a32fdb" horizontalFill="true" fieldColSpan="2" vocName="vocHospitalizationResult" />
          <msh:autoComplete vocName="vocHospitalizationOutcome" property="outcome" label="Исход госпитализации" horizontalFill="true" guid="3e424edd-f857-40b5-978c-1da8bba0d3ab" fieldColSpan="2" />
        </msh:row>
        <mis:ifPatientIsWoman classByObject="Patient" idObject="stac_sslAllInfoForm.patient" roles="/Policy/Mis/Pregnancy/History/View">
        <msh:separator label="Беременность" colSpan="9"/>
        <msh:row>
        	<msh:autoComplete viewAction="entityParentView-preg_pregnancy.do" property="pregnancy" label="Беременность" fieldColSpan="3" parentId="stac_sslAdmissionForm.patient" vocName="pregnancyByPatient" horizontalFill="true"/>
        </msh:row>

        </mis:ifPatientIsWoman>
        <msh:submitCancelButtonsRow guid="submitCancel" colSpan="4" labelSave="Сохранить изменения" labelCreating="Создание" labelCreate="Создать новый случай" labelSaving="Сохранение данных" />
      </msh:panel>
    </msh:form>
    <tags:stac_infoBySls form="stac_sslAllInfoForm"/>
  </tiles:put>
  <tiles:put name="title" type="string">
    <ecom:titleTrail mainMenu="Patient" beginForm="stac_sslCloseForm" guid="ad9ca7d1-36d7-41ac-a186-cf6fca58b389" />
  </tiles:put>
  <tiles:put name="side" type="string">
    	  	<tags:stac_hospitalMenu currentAction="stac_sslAllInfo"/>  
  </tiles:put>
</tiles:insert>

