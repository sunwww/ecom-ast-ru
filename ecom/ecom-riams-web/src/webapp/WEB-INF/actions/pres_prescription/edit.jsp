<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://struts.apache.org/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://www.nuzmsh.ru/tags/msh" prefix="msh" %>
<%@ taglib uri="http://www.ecom-ast.ru/tags/ecom" prefix="ecom" %>
<%@ taglib uri="http://www.ecom-ast.ru/tags/mis" prefix="mis" %>

<tiles:insert page="/WEB-INF/tiles/mainLayout.jsp" flush="true">

  <tiles:put name="body" type="string">
    <!-- 
    	  - Журнал назначений
    	  -->
    <msh:form guid="formHello" action="/entitySaveGoView-pres_prescription.do" defaultField="hello">
      <msh:hidden guid="hiddenId" property="id" />
      <msh:hidden guid="hiddenSaveType" property="saveType" />
      <msh:hidden guid="hiddenParent" property="id" />
      <msh:panel guid="panel">
        <msh:row guid="bh5fb-b971-441e-9a90-51941">
          <msh:label property="descriptionInfo" label="Назначение" guid="ae3hf-1794-4d32-8918-ad928dae7eb5" viewOnlyField="true" horizontalFill="true" size="50" fieldColSpan="4" />
        </msh:row>
        <msh:row guid="row1">
          <msh:textField guid="textFieldHello" property="planStartDate" label="Дата начала" viewOnlyField="true" />
          <msh:textField property="planStartTime" label="Время начала" guid="ae3h8cf5e-1794-4d32-8918-ad928dae7eb5" viewOnlyField="true" />
        </msh:row>
        <msh:row guid="row1">
          <msh:textField guid="5f456eb-b971-441e-9a90-519" property="planEndDate" label="Дата окончания" viewOnlyField="true" />
          <msh:textField property="planEndTime" label="Время окончания" guid="ave3h8cf5e-1794-4d32-8918-ad928dae7eb5" viewOnlyField="true" />
        </msh:row>
        <msh:row guid="row1">
          <msh:textField guid="5f456eb-b971-441e-9a90-519ft" property="cancelDate" label="Дата отмены" viewOnlyField="true" />
          <msh:textField property="cancelTime" label="Время отмены" guid="ae3h8cgf5e-1794-4d32-8918-ad928dae7eb5" viewOnlyField="true" />
        </msh:row>
        <msh:row guid="b5f456eb-b971-441e-9a90-51941">
          <msh:autoComplete vocName="vocPrescriptCancelReason" property="cancelReason" label="Причина" guid="6g55-3e8b-496d-9d2f-68706daafb67" horizontalFill="true" fieldColSpan="4" />
        </msh:row>
        <msh:row guid="bhgf5fb-b971-441e-9a90-51941">
          <msh:autoComplete vocName="vocWorker" property="cancelWorker" label="Отменил" guid="6gf55-3e8b-496d-9d2f-68706daafb67" horizontalFill="true" fieldColSpan="4" />
        </msh:row>
        <msh:row guid="456eb-b971-441e-9a90-51941">
          <msh:autoComplete vocName="vocPrescriptFulfilState" property="fulfilmentState" label="Состояние назначения" guid="6g55-3e8bg-496d-9d2f-68706daafb67" fieldColSpan="4" horizontalFill="true" />
        </msh:row>
        <msh:submitCancelButtonsRow guid="submitCancel" colSpan="4" />
      </msh:panel>
    </msh:form>
    <msh:ifFormTypeIsView guid="ifFormTypeIsView" formName="pres_prescriptionForm">
      <msh:section guid="sectionChilds" title="Исполнения">
        <ecom:parentEntityListAll guid="parentEntityListChilds" formName="pres_prescriptionFulfilmentForm" attribute="fulfilments" />
        <msh:table guid="tableChilds3434" name="fulfilments" action="entityParentView-pres_prescriptionFulfilment.do" idField="id">
          <msh:tableColumn columnName="Дата исполнения" property="fulfilDate" guid="8c2a3f9b-89d7-46a9-a8c3-c08029ec047e" />
          <msh:tableColumn columnName="Время исполнения" property="fulfilTime" guid="d61b9d49-a94d-4cf2-af1b-05020213901f" />
          <msh:tableColumn columnName="Исполнитель" property="executorInfo" guid="bfe44281-0967-45f2-a6af-f5b368cca8ae" />
        </msh:table>
      </msh:section>
    </msh:ifFormTypeIsView>
  </tiles:put>
  <tiles:put name="title" type="string">
    <ecom:titleTrail guid="titleTrail-123" mainMenu="Prescription" beginForm="pres_prescriptionForm" />
  </tiles:put>
  <tiles:put name="side" type="string">
    <msh:sideMenu guid="sideMenu-123" title="Назначения">
      <msh:sideLink roles="/Policy/Mis/Prescription/View" params="id" action="/entityList-pres_prescription" name="К сводному списку назначений" guid="97ghf1f-5dfd-4aee-ada9-d0420c" />
      <msh:sideLink roles="/Policy/Mis/Prescription/Edit" key="ALT+2" params="id" action="/entityEdit-pres_prescription" name="Изменить" guid="97f-5dfd-4aee-ada9-d0420c" />
      <msh:sideLink roles="/Policy/Mis/Prescription/Delete" key="ALT+DEL" confirm="Удалить?" params="id" action="/entityParentDeleteGoSubclassView-pres_prescription" name="Удалить" guid="97f-5dfd-4aee-ada9-d04244ef20c" />
    </msh:sideMenu>
    <msh:sideMenu title="Добавить" guid="08906109-6c4d-469c-9c76-cd63fd470464">
      <msh:sideLink roles="/Policy/Mis/Prescription/PrescriptionFulfilment/Create" params="id" action="/entityParentPrepareCreate-pres_prescriptionFulfilment" name="Исполнение назначения " title="Добавить исполнение назначения" guid="19aec748-a626-4b6e-af52-cdcc982" />
    </msh:sideMenu>
    <msh:sideMenu title="Показать" guid="c6957457-cac4-42ea-b0e5-29cc12ec20ab" />
  </tiles:put>
</tiles:insert>

