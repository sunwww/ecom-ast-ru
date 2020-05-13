<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://struts.apache.org/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://www.nuzmsh.ru/tags/msh" prefix="msh" %>
<%@ taglib uri="http://www.ecom-ast.ru/tags/ecom" prefix="ecom" %>
<%@ taglib uri="http://www.ecom-ast.ru/tags/mis" prefix="mis" %>
<tiles:insert page="/WEB-INF/tiles/mainLayout.jsp" flush="true">

  <tiles:put name="body" type="string">
    <!-- 
    	  - Назначение медицинской услуги
    	  -->
    <msh:form action="/entityParentSaveGoParentView-pres_template_servicePrescription.do" defaultField="id" title="Назначение медицинской услуги">
      <msh:hidden property="id" />
      <msh:hidden property="prescriptionList" />
      <msh:hidden property="saveType" />
      <msh:panel colsWidth="3">
        <msh:row>
          <msh:autoComplete vocName="medService" property="medService" label="Медицинская услуга" horizontalFill="true" fieldColSpan="3" size="50" />
        </msh:row>
        <msh:row>
          <msh:separator colSpan="6" label="График приема" />
        </msh:row>
        <msh:row>
          <msh:textField label="Дата начала" property="planStartDate" horizontalFill="true" />
          <msh:textField label="Время" property="planStartTime" horizontalFill="true" />
        </msh:row>
        <msh:row>
          <msh:textField label="Дата окончания" property="planEndDate" horizontalFill="true" />
          <msh:textField label="Время" property="planEndTime" horizontalFill="true" />
        </msh:row>
        <msh:row>
          <msh:autoComplete vocName="vocWorker" label="Назначил" property="prescriptor" horizontalFill="true" fieldColSpan="3" size="50" />
        </msh:row>
        <msh:row>
          <msh:textField label="Дата отмены" property="cancelDate" horizontalFill="true" />
          <msh:textField label="Время" property="cancelTime" horizontalFill="true" />
        </msh:row>
        <msh:row>
          <msh:autoComplete vocName="vocPrescriptCancelReason" label="Причина" property="cancelReason" horizontalFill="true" fieldColSpan="3" size="50" />
        </msh:row>
        <msh:row>
          <msh:autoComplete vocName="worker" label="Отменил" property="cancelWorker" horizontalFill="true" fieldColSpan="3" size="50" />
        </msh:row>
        <msh:submitCancelButtonsRow colSpan="4" />
      </msh:panel>
    </msh:form>
    
    <msh:ifFormTypeIsView formName="pres_template_servicePrescriptionForm">
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
    <ecom:titleTrail mainMenu="Prescription" beginForm="pres_template_servicePrescriptionForm" />
  </tiles:put>
  <tiles:put name="side" type="string">
    <msh:ifFormTypeIsView formName="pres_template_servicePrescriptionForm">
      <msh:sideMenu title="Назначения">
        <msh:sideLink roles="/Policy/Mis/Prescription/Template/ServicePrescription/Edit" params="id" action="/entityParentEdit-pres_template_servicePrescription" name="Изменить" key="ALT+2"/>
        <msh:sideLink confirm="Удалить?" roles="/Policy/Mis/Prescription/Template/ServicePrescription/Delete" params="id" action="/entityParentDelete-pres_template_servicePrescription" name="Удалить" key="ALT+DEL"/>
      </msh:sideMenu>
      <msh:sideMenu title="Показать" >
        <msh:sideLink roles="/Policy/Mis/Prescription/Template/ServicePrescription/View" params="id" action="/entityParentListRedirect-pres_template_servicePrescription" name="К списку назначений на услугу" key="ALT+4"/>
        <msh:sideLink roles="/Policy/Mis/Prescription/Template/View" params="id" action="/entityParentListRedirect-pres_prescription" name="К списку назначений" key="ALT+4"/>
      </msh:sideMenu>
    </msh:ifFormTypeIsView>
  </tiles:put>
</tiles:insert>