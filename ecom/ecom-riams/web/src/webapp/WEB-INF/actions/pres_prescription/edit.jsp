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
    <msh:form action="/entitySaveGoView-pres_prescription.do" defaultField="hello">
      <msh:hidden property="id" />
      <msh:hidden property="saveType" />
      <msh:hidden property="id" />
      <msh:hidden property="setPatologySpecial" />
      <msh:hidden property="setPatologyUsername" />
      <msh:hidden property="setPatologyDate" />
      <msh:hidden property="SetPatologyTime" />
      <msh:panel>
        <msh:row>
          <msh:label property="descriptionInfo" label="Назначение" viewOnlyField="true" horizontalFill="true" size="50" fieldColSpan="4" />
        </msh:row>
        <msh:row>
          <msh:textField property="planStartDate" label="Дата начала" viewOnlyField="true" />
          <msh:textField property="planStartTime" label="Время начала" viewOnlyField="true" />
        </msh:row>
        <msh:row>
          <msh:textField property="planEndDate" label="Дата окончания" viewOnlyField="true" />
          <msh:textField property="planEndTime" label="Время окончания" viewOnlyField="true" />
        </msh:row>
        <msh:row>
          <msh:textField property="cancelDate" label="Дата отмены" viewOnlyField="true" />
          <msh:textField property="cancelTime" label="Время отмены" viewOnlyField="true" />
        </msh:row>
        <msh:row>
          <msh:autoComplete vocName="vocPrescriptCancelReason" property="cancelReason" label="Причина" horizontalFill="true" fieldColSpan="4" />
        </msh:row>
        <msh:row>
          <msh:autoComplete vocName="vocWorker" property="cancelWorker" label="Отменил" horizontalFill="true" fieldColSpan="4" />
        </msh:row>
        <msh:row>
          <msh:autoComplete vocName="vocPrescriptFulfilState" property="fulfilmentState" label="Состояние назначения" fieldColSpan="4" horizontalFill="true" />
        </msh:row>
        <msh:submitCancelButtonsRow colSpan="4" />
      </msh:panel>
    </msh:form>
    <msh:ifFormTypeIsView formName="pres_prescriptionForm">
      <msh:section title="Исполнения">
        <ecom:parentEntityListAll formName="pres_prescriptionFulfilmentForm" attribute="fulfilments" />
        <msh:table name="fulfilments" action="entityParentView-pres_prescriptionFulfilment.do" idField="id">
          <msh:tableColumn columnName="Дата исполнения" property="fulfilDate" />
          <msh:tableColumn columnName="Время исполнения" property="fulfilTime" />
          <msh:tableColumn columnName="Исполнитель" property="executorInfo" />
        </msh:table>
      </msh:section>
    </msh:ifFormTypeIsView>
  </tiles:put>
  <tiles:put name="title" type="string">
    <ecom:titleTrail mainMenu="Prescription" beginForm="pres_prescriptionForm" />
  </tiles:put>
  <tiles:put name="side" type="string">
    <msh:sideMenu title="Назначения">
      <msh:sideLink roles="/Policy/Mis/Prescription/View" params="id" action="/entityList-pres_prescription" name="К сводному списку назначений" />
      <msh:sideLink roles="/Policy/Mis/Prescription/Edit" key="ALT+2" params="id" action="/entityEdit-pres_prescription" name="Изменить" />
      <msh:sideLink roles="/Policy/Mis/Prescription/Delete" key="ALT+DEL" confirm="Удалить?" params="id" action="/entityParentDeleteGoSubclassView-pres_prescription" name="Удалить" />
    </msh:sideMenu>
    <msh:sideMenu title="Добавить">
      <msh:sideLink roles="/Policy/Mis/Prescription/PrescriptionFulfilment/Create" params="id" action="/entityParentPrepareCreate-pres_prescriptionFulfilment" name="Исполнение назначения " title="Добавить исполнение назначения" />
    </msh:sideMenu>
    <msh:sideMenu title="Показать" />
  </tiles:put>
</tiles:insert>

