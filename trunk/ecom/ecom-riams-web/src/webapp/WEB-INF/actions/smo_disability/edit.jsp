<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://struts.apache.org/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://www.nuzmsh.ru/tags/msh" prefix="msh" %>
<%@ taglib uri="http://www.ecom-ast.ru/tags/ecom" prefix="ecom" %>

<tiles:insert page="/WEB-INF/tiles/mainLayout.jsp" flush="true">

  <tiles:put name="body" type="string">
    <!-- 
    	  - smo_disability (Документ нетрудоспособности)
    	  -->
    <msh:form guid="formHello" action="/entityParentSaveGoView-smo_disability.do" defaultField="primarityName">
      <msh:hidden guid="hiddenId" property="id" />
      <msh:hidden guid="hiddenSaveType" property="saveType" />
      <msh:hidden guid="hiddenParent" property="disabilityCase" />
      <msh:panel guid="panel">
        <msh:row guid="row1">
          <msh:row guid="382fee95-4537-4242-abd4-fa4e40cda49e">
            <msh:autoComplete vocName="vocDisabilityDocumentPrimarity" property="primarity" label="Первичность" guid="2e7aa7a4-336c-4831-b3d9-97d6f64d2ef1" horizontalFill="true" fieldColSpan="3" />
          </msh:row>
          <msh:row guid="382f295-4537-4242-abd4-fa4e8e">
            <msh:autoComplete vocName="vocCombo" property="combo" label="Тип совместительства" guid="227a4-336c-4831-b3d9-9f12ef1" horizontalFill="true" fieldColSpan="3" />
          </msh:row>
          <msh:row guid="b91f60e2-b0b6-4f21-8b0e-29e11ca9178c">
            <msh:textField property="mainWorkDocumentSeries" label="Серия" guid="b9d0f37f-bd93-4e91-be9c-703c363ca9a8" />
            <msh:textField property="mainWorkDocumentNumber" label="Номер" guid="48e71893-5dc9-4cca-8b36-6e6fe8000365" />
          </msh:row>
          <msh:textField guid="textFieldHello" property="closeSeries" label="Серия по основному месту работы" />
          <msh:textField property="number" label="Номер по основному месту работы" guid="0cdf1a41-0d4d-40d6-81a4-4e61b1dd3095" />
        </msh:row>
        <msh:row guid="e1b2ffa3-e51c-46ef-9a21-6b6c19b60831">
          <msh:autoComplete vocName="vocDisabilityReason" property="disabilityReason" label="Вид нетрудоспособности" guid="c431085f-265a-40ab-9581-a1c8b5babeff" fieldColSpan="3" horizontalFill="true" />
        </msh:row>
        <msh:row guid="e25a3-e51c-46ef-9a21-63260831">
          <msh:autoComplete vocName="vocIdc10" property="idc10" label="Код МКБ-10" guid="c431085f-265a-40ab-9581-a121beff" fieldColSpan="3" horizontalFill="true" />
        </msh:row>
        <msh:row guid="6b069356-39d7-44d4-bf5b-111b87f531a2">
          <msh:textField property="nursingPatientLastName" label="Фамилия больного по уходу" guid="0aa846ea-f2ee-4b41-8158-a670faf7544d" />
          <msh:textField property="nursingPatientFirstName" label="Имя больного по уходу" guid="6b41399f-e5a0-4f91-a057-6522b1fff467" />
        </msh:row>
        <msh:row guid="9e7a70d7-cb8f-41bd-b7d2-3165215fe4c2">
          <msh:textField property="nursingPatientAge" label="Возраст больного по уходу" guid="e20c0706-1252-4ae9-a850-4d4c80773cd4" />
        </msh:row>
        <msh:row guid="184d86d4-851c-4a2a-a2be-850a6b1b2ae9">
          <msh:textField property="sanatoriumDateFrom" label="Дата начала санаторного лечения" guid="c78d8c08-23f6-4825-9910-050d0d4c41bb" />
          <msh:textField property="sanatoriumDateTo" label="Дата окончания санаторного лечения" guid="daa5ef7d-f6fa-474b-8d67-f56e7922c417" />
        </msh:row>
        <msh:row guid="67f2fb08-bc13-487d-910c-ebb7d4437344">
          <msh:textField property="sanatoriumTicketNumber" label="Номер путевки" guid="719c215c-d614-4c67-ba5d-8d1af83257ec" />
        </msh:row>
        <msh:textField property="sanatoriumPlace" label="Место нахождения санатория" guid="1934315c-53e9-4b96-9ce2-6e7bc7a59a2e" fieldColSpan="3" horizontalFill="true" />
        <msh:row guid="f20ccbea-e248-4d80-b84b-d78d82ca84c7">
          <msh:textField property="supposeBirthDate" label="Предполагаемая дата родов" guid="df59e18d-1d61-4821-b86d-424a4810cd7f" />
        </msh:row>
        <msh:row guid="e46a3-e51c-46ef-9a21-6bvb60831">
          <msh:autoComplete vocName="vocDisabilityDocumentCloseReason" property="closeReason" label="Причина закрытия" guid="c425f-265a-40ab-9581-a8ff" fieldColSpan="3" horizontalFill="true" />
        </msh:row>
        <msh:row guid="1a1e56f6-2eb3-4a8c-815f-263e5275d124">
          <msh:textField property="beginWorkDate" label="Дата начала работы" guid="e7253f4a-e60a-4d7e-b459-2dbb4be7aed0" />
          <msh:textField property="issueDate" label="Дата выдачи" guid="93922a54-4ad5-4e25-98f3-c714baa894aa" />
        </msh:row>
        <msh:row guid="156f6-2eb3-4a8c-815f-212124">
          <msh:checkBox property="id" label="Испорчен" guid="808c0138-da7c-4369-8b68-44c4f268a6a8" />
        </msh:row>
        <msh:row guid="6ded9bfe-a189-400f-9e14-f39921634666">
          <msh:submitCancelButtonsRow guid="submitCancel" colSpan="4" />
        </msh:row>
      </msh:panel>
    </msh:form>
    <msh:ifFormTypeIsView guid="ifFormTypeIsView" formName="smo_disabilityForm">
      <msh:section guid="sectionChilds" title="Периоды нетрудоспособности">
        <ecom:parentEntityListAll guid="parentEntityListChilds" formName="dis_disabilityRecordForm" attribute="childs" />
        <msh:table guid="tableChilds" name="childs" action="entityParentView-dis_disabilityRecord.do" idField="id">
          <msh:tableColumn columnName="Дата начала" property="dateFrom" guid="23eed88f-9ea7-4b8f-a955-20ecf89ca86c" />
          <msh:tableColumn columnName="Дата окончания" property="dateTo" guid="a744754f-5212-4807-910f-e4b252aec108" />
          <msh:tableColumn columnName="Режим" identificator="false" property="regimeText" guid="14e8c4f9-f430-496c-ae75-ae2a2240937d" />
        </msh:table>
      </msh:section>
      <msh:section title="Нарушение режима" guid="11e46cd9-93cd-44c0-a16d-2470243a0a65">
        <ecom:parentEntityListAll formName="dis_regimeViolationForm" attribute="violation" guid="16363824-17a0-4ba7-9022-720dcb016bad" />
        <msh:table idField="id" name="violation" action="entityParentView-dis_regimeViolation.do" guid="cac74c69-de47-4874-aa5f-a0466d479750">
          <msh:tableColumn columnName="Дата начала" property="dateFrom" guid="cc1f4517-871f-44b9-b614-3dee5bddd607" />
          <msh:tableColumn columnName="Дата окончания" property="dateTo" guid="7a12d9ed-fa3b-49c6-83be-a46de701aded" />
          <msh:tableColumn columnName="Комментарий" property="comment" guid="82b358c5-9acf-4936-90ff-1823d8c2046e" />
        </msh:table>
      </msh:section>
      <msh:section title="МСЭ" guid="15cd2fda-ef53-4af1-b71d-bb9d2b767158">
        <ecom:parentEntityListAll formName="dis_MedSocCommissionForm" attribute="medSoc" guid="56302751-44dd-4b75-8d7f-8f66bf0fe577" />
        <msh:table idField="id" name="medSoc" action="entityParentView-dis_MedSocCommission.do" guid="99c062d6-25c4-4609-9181-bfa155a7d704">
          <msh:tableColumn columnName="Дата направления" property="assignmentDate" guid="e956f01b-fd81-41f7-ac94-5bc1cc81443a" />
          <msh:tableColumn columnName="Дата регистрации" property="registrationDate" guid="88739c92-a1e1-4c74-b5ef-81f137685a4f" />
          <msh:tableColumn columnName="Дата освидетельствования" property="examinationDate" guid="fb4d52ce-97b4-4b7a-92cd-92d2aa7efee1" />
        </msh:table>
      </msh:section>
    </msh:ifFormTypeIsView>
  </tiles:put>
  <tiles:put name="title" type="string">
    <ecom:titleTrail guid="titleTrail-123" mainMenu="Patient" beginForm="smo_disabilityForm" />
  </tiles:put>
  <tiles:put name="side" type="string">
    <msh:ifFormTypeIsView formName="smo_disabilityForm" guid="609c845b-be8f-4778-9ed8-2ac4b68ac287">
      <msh:sideMenu guid="sideMenu-123" title="Нетрудоспособность">
        <msh:sideLink guid="sideLinkEdit" key="ALT+2" params="id" action="/entityEdit-smo_disability" name="Изменить" roles="/Policy/Mis/Disability/DisabilityDocument/Edit" />
        <msh:sideLink guid="sideLinkDelete" key="ALT+DEL" confirm="Удалить?" params="id" action="/entityParentDeleteGoParentView-smo_disability" name="Удалить" roles="/Policy/Mis/Disability/DisabilityDocument/Delete" />
      </msh:sideMenu>
      <msh:sideMenu title="Добавить" guid="c79769a2-8a1c-4c21-ab9c-b7ed71ceb99d">
        <msh:sideLink params="id" action="/entityParentPrepareCreate-dis_regimeViolation" roles="/Policy/Mis/Disability/RegimeViolationRecord/Create" name="Нарушение режима" guid="d9a0ba4a-a68a-4f48-8492-767e911bce80" title="Добавить запись о нарушении режима" />
        <msh:sideLink params="id" action="/entityParentPrepareCreate-dis_disabilityRecord" roles="/Policy/Mis/Disability/DisabilityRecord/Create" name="Периоды нетрудоспособности" guid="0634b894-60e2-4b73-acee-7bf7316a77fc" title="Добавить период нетрудоспособности" />
        <msh:sideLink params="id" action="/entityParentPrepareCreate-dis_MedSocCommission" roles="/Policy/Mis/Disability/MedSocCommission/Create" name="МСЭ" title="Добавить решение МСЭ" guid="4e09fb92-851a-4547-a12d-c384f63e31cd" />
      </msh:sideMenu>
    </msh:ifFormTypeIsView>
  </tiles:put>
</tiles:insert>

