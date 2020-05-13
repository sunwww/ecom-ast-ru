<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://struts.apache.org/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://www.nuzmsh.ru/tags/msh" prefix="msh" %>
<%@ taglib uri="http://www.ecom-ast.ru/tags/ecom" prefix="ecom" %>
<%@ taglib uri="http://www.ecom-ast.ru/tags/mis" prefix="mis" %>
<tiles:insert page="/WEB-INF/tiles/mainLayout.jsp" flush="true">

  <tiles:put name="body" type="string">
    <!-- 
    	  - Назначение диеты
    	  -->
    <msh:form action="/entityParentSaveGoParentView-pres_template_dietPrescription.do" defaultField="id" title="Назначение диеты">
      <msh:hidden property="id" />
      <msh:hidden property="prescriptionList" />
      <msh:hidden property="saveType" />
      <msh:panel colsWidth="3">
        <msh:row>
          <msh:autoComplete viewAction="entityView-pres_template_dietPrescription.do" vocName="Diet" property="diet" label="Диета" horizontalFill="true" fieldColSpan="3" size="50" />
        </msh:row>
        <msh:submitCancelButtonsRow colSpan="3" />
      </msh:panel>
    </msh:form>
        <mis:canShowPrescriptionFulfilments formName="pres_template_dietPrescriptionForm">
    <msh:ifFormTypeIsView formName="pres_template_dietPrescriptionForm">
      <msh:section title="Исполнения">
        <ecom:parentEntityListAll formName="pres_prescriptionFulfilmentForm" attribute="fulfilments" />
        <msh:table name="fulfilments" action="entityParentView-pres_prescriptionFulfilment.do" idField="id">
          <msh:tableColumn columnName="Дата исполнения" property="fulfilDate" />
          <msh:tableColumn columnName="Время исполнения" property="fulfilTime" />
          <msh:tableColumn columnName="Исполнитель" property="executorInfo" />
        </msh:table>
      </msh:section>
    </msh:ifFormTypeIsView>
    </mis:canShowPrescriptionFulfilments>
  </tiles:put>
  <tiles:put name="title" type="string">
    <ecom:titleTrail mainMenu="Prescription" beginForm="pres_template_dietPrescriptionForm" />
  </tiles:put>
  <tiles:put name="side" type="string">
    <msh:ifFormTypeIsView formName="pres_template_dietPrescriptionForm">
      <msh:sideMenu title="Назначение диеты">
        <msh:sideLink roles="/Policy/Mis/Prescription/Template/DietPrescription/Edit" params="id" action="/entityParentEdit-pres_template_dietPrescription" name="Изменить" key="ALT+2"/>
        <msh:sideLink  confirm="Удалить?" roles="/Policy/Mis/Prescription/Template/DietPrescription/Delete" params="id" action="/entityParentDelete-pres_template_dietPrescription" name="Удалить" key="ALT+DEL"/>
      </msh:sideMenu>
      <msh:sideMenu title="Перейти" >
        <msh:sideLink roles="/Policy/Mis/Prescription/Template/DietPrescription/View" params="id" action="/entityParentListRedirect-pres_template_dietPrescription" name="К списку назначенных диет" key="ALT+4"/>
        <msh:sideLink roles="/Policy/Mis/Prescription/Template/View" params="id" action="/entityParentListRedirect-pres_prescription" name="К списку назначений" key="ALT+5"/>
      </msh:sideMenu>
    </msh:ifFormTypeIsView>
  </tiles:put>
</tiles:insert>