<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://struts.apache.org/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://www.nuzmsh.ru/tags/msh" prefix="msh" %>
<%@ taglib uri="http://www.ecom-ast.ru/tags/ecom" prefix="ecom" %>

<tiles:insert page="/WEB-INF/tiles/mainLayout.jsp" flush="true">

  <tiles:put name="body" type="string">
    <!-- 
    	  - smo_disability (Документ нетрудоспособности)
    	  -->
    <msh:form action="/entityParentSaveGoView-smo_disability.do" defaultField="primarityName">
      <msh:hidden property="id" />
      <msh:hidden property="saveType" />
      <msh:hidden property="disabilityCase" />
      <msh:panel>
        <msh:row>
          <msh:row>
            <msh:autoComplete vocName="vocDisabilityDocumentPrimarity" property="primarity" label="Первичность" horizontalFill="true" fieldColSpan="3" />
          </msh:row>
          <msh:row>
            <msh:autoComplete vocName="vocCombo" property="combo" label="Тип совместительства" horizontalFill="true" fieldColSpan="3" />
          </msh:row>
          <msh:row>
            <msh:textField property="mainWorkDocumentSeries" label="Серия" />
            <msh:textField property="mainWorkDocumentNumber" label="Номер" />
          </msh:row>
          <msh:textField property="closeSeries" label="Серия по основному месту работы" />
          <msh:textField property="number" label="Номер по основному месту работы" />
        </msh:row>
        <msh:row>
          <msh:autoComplete vocName="vocDisabilityReason" property="disabilityReason" label="Вид нетрудоспособности" fieldColSpan="3" horizontalFill="true" />
        </msh:row>
        <msh:row>
          <msh:autoComplete vocName="vocIdc10" property="idc10" label="Код МКБ-10" fieldColSpan="3" horizontalFill="true" />
        </msh:row>
        <msh:row>
          <msh:textField property="nursingPatientLastName" label="Фамилия больного по уходу" />
          <msh:textField property="nursingPatientFirstName" label="Имя больного по уходу" />
        </msh:row>
        <msh:row>
          <msh:textField property="nursingPatientAge" label="Возраст больного по уходу" />
        </msh:row>
        <msh:row>
          <msh:textField property="sanatoriumDateFrom" label="Дата начала санаторного лечения" />
          <msh:textField property="sanatoriumDateTo" label="Дата окончания санаторного лечения" />
        </msh:row>
        <msh:row>
          <msh:textField property="sanatoriumTicketNumber" label="Номер путевки" />
        </msh:row>
        <msh:textField property="sanatoriumPlace" label="Место нахождения санатория" fieldColSpan="3" horizontalFill="true" />
        <msh:row>
          <msh:textField property="supposeBirthDate" label="Предполагаемая дата родов" />
        </msh:row>
        <msh:row>
          <msh:autoComplete vocName="vocDisabilityDocumentCloseReason" property="closeReason" label="Причина закрытия" fieldColSpan="3" horizontalFill="true" />
        </msh:row>
        <msh:row>
          <msh:textField property="beginWorkDate" label="Дата начала работы" />
          <msh:textField property="issueDate" label="Дата выдачи" />
        </msh:row>
        <msh:row>
          <msh:checkBox property="id" label="Испорчен" />
        </msh:row>
        <msh:row>
          <msh:submitCancelButtonsRow colSpan="4" />
        </msh:row>
      </msh:panel>
    </msh:form>
    <msh:ifFormTypeIsView formName="smo_disabilityForm">
      <msh:section title="Периоды нетрудоспособности">
        <ecom:parentEntityListAll formName="dis_disabilityRecordForm" attribute="childs" />
        <msh:table name="childs" action="entityParentView-dis_disabilityRecord.do" idField="id">
          <msh:tableColumn columnName="Дата начала" property="dateFrom" />
          <msh:tableColumn columnName="Дата окончания" property="dateTo" />
          <msh:tableColumn columnName="Режим" identificator="false" property="regimeText" />
        </msh:table>
      </msh:section>
      <msh:section title="Нарушение режима">
        <ecom:parentEntityListAll formName="dis_regimeViolationForm" attribute="violation" />
        <msh:table idField="id" name="violation" action="entityParentView-dis_regimeViolation.do">
          <msh:tableColumn columnName="Дата начала" property="dateFrom" />
          <msh:tableColumn columnName="Дата окончания" property="dateTo" />
          <msh:tableColumn columnName="Комментарий" property="comment" />
        </msh:table>
      </msh:section>
      <msh:section title="МСЭ">
        <ecom:parentEntityListAll formName="dis_MedSocCommissionForm" attribute="medSoc" />
        <msh:table idField="id" name="medSoc" action="entityParentView-dis_MedSocCommission.do">
          <msh:tableColumn columnName="Дата направления" property="assignmentDate" />
          <msh:tableColumn columnName="Дата регистрации" property="registrationDate" />
          <msh:tableColumn columnName="Дата освидетельствования" property="examinationDate" />
        </msh:table>
      </msh:section>
    </msh:ifFormTypeIsView>
  </tiles:put>
  <tiles:put name="title" type="string">
    <ecom:titleTrail mainMenu="Patient" beginForm="smo_disabilityForm" />
  </tiles:put>
  <tiles:put name="side" type="string">
    <msh:ifFormTypeIsView formName="smo_disabilityForm">
      <msh:sideMenu title="Нетрудоспособность">
        <msh:sideLink key="ALT+2" params="id" action="/entityEdit-smo_disability" name="Изменить" roles="/Policy/Mis/Disability/DisabilityDocument/Edit" />
        <msh:sideLink key="ALT+DEL" confirm="Удалить?" params="id" action="/entityParentDeleteGoParentView-smo_disability" name="Удалить" roles="/Policy/Mis/Disability/DisabilityDocument/Delete" />
      </msh:sideMenu>
      <msh:sideMenu title="Добавить">
        <msh:sideLink params="id" action="/entityParentPrepareCreate-dis_regimeViolation" roles="/Policy/Mis/Disability/RegimeViolationRecord/Create" name="Нарушение режима" title="Добавить запись о нарушении режима" />
        <msh:sideLink params="id" action="/entityParentPrepareCreate-dis_disabilityRecord" roles="/Policy/Mis/Disability/DisabilityRecord/Create" name="Периоды нетрудоспособности" title="Добавить период нетрудоспособности" />
        <msh:sideLink params="id" action="/entityParentPrepareCreate-dis_MedSocCommission" roles="/Policy/Mis/Disability/MedSocCommission/Create" name="МСЭ" title="Добавить решение МСЭ" />
      </msh:sideMenu>
    </msh:ifFormTypeIsView>
  </tiles:put>
</tiles:insert>

