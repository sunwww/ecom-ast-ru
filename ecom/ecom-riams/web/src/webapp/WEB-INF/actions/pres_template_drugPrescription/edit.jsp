<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://struts.apache.org/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://www.nuzmsh.ru/tags/msh" prefix="msh" %>
<%@ taglib uri="http://www.ecom-ast.ru/tags/ecom" prefix="ecom" %>

<tiles:insert page="/WEB-INF/tiles/mainLayout.jsp" flush="true">

  <tiles:put name="body" type="string">
    <!-- 
    	  - Назначение лекарственного средства
    	  -->
    <msh:form action="/entityParentSaveGoParentView-pres_template_drugPrescription.do" defaultField="id" title="Назначение лекарственного средства">
      <msh:hidden property="id" />
      <msh:hidden property="saveType" />
      <msh:hidden property="prescriptionList" />
      <msh:panel>
        <msh:row>
          <msh:autoComplete viewAction="entityView-voc_drug.do" vocName="vocDrugClassify" property="drug" label="Лекарственный препарат" horizontalFill="true" fieldColSpan="3" size="50" />
        </msh:row>
        <msh:row>
          <msh:autoComplete vocName="vocDrugMethod" label="Способ введения" property="method" horizontalFill="true" fieldColSpan="3" size="50" />
        </msh:row>
        <msh:row>
          <msh:textField property="frequency" label="Частота" horizontalFill="true" />
          <msh:autoComplete vocName="vocFrequencyUnit" label="раза в " property="frequencyUnit" horizontalFill="true" />
        </msh:row>
        <msh:row>
          <msh:textField property="orderTime" label="Время приема (в минутах)" horizontalFill="true" />
          <msh:autoComplete vocName="vocPrescriptOrderType" label="Тип приема" property="orderType" horizontalFill="true" />
        </msh:row>
        <msh:row>
          <msh:textField label="Количество на один прием" property="amount" horizontalFill="true" />
          <msh:autoComplete vocName="vocDrugAmountUnit" label="в единицах (мг, г, мл)" property="amountUnit" horizontalFill="true" />
        </msh:row>
        <msh:row>
          <msh:textField property="duration" label="Продолжительность" horizontalFill="true" />
          <msh:autoComplete vocName="vocDurationUnit" label="в единицах времени" property="durationUnit" horizontalFill="true" />
        </msh:row>
        <msh:row />
        <msh:submitCancelButtonsRow colSpan="4" />
      </msh:panel>
    </msh:form>
  </tiles:put>
  <tiles:put name="title" type="string">
    <ecom:titleTrail mainMenu="Template" beginForm="pres_template_drugPrescriptionForm" />
  </tiles:put>
  <tiles:put name="side" type="string">
    <msh:ifFormTypeIsView formName="pres_template_drugPrescriptionForm">
      <msh:sideMenu title="Назначение ЛС">
        <msh:sideLink roles="/Policy/Mis/Prescription/Template/DrugPrescription/Edit" params="id" action="/entityParentEdit-pres_template_drugPrescription" name="Изменить" key="ALT+3" />
        <msh:sideLink roles="/Policy/Mis/Prescription/Template/DrugPrescription/Delete" params="id" action="/entityParentDelete-pres_template_drugPrescription" name="Удалить" key="ALT+DEL" confirm="Удалить назначение лекарственного средства?" />
      </msh:sideMenu>
      <msh:sideMenu title="Перейти">
        <msh:sideLink key="ALT+9" action="/entityParentList-pres_template_prescription" name="⇧К списку шаблонов назначений" roles="/Policy/Mis/Prescription/Template/View" params="id" />
      </msh:sideMenu>
    </msh:ifFormTypeIsView>
  </tiles:put>
</tiles:insert>

