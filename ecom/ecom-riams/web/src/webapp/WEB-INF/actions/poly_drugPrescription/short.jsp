<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://struts.apache.org/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://www.nuzmsh.ru/tags/msh" prefix="msh" %>
<%@ taglib uri="http://www.ecom-ast.ru/tags/ecom" prefix="ecom" %>
<%@ taglib uri="http://www.ecom-ast.ru/tags/mis" prefix="mis" %>
<tiles:insert page="/WEB-INF/tiles/mainLayout.jsp" flush="true">

  <tiles:put name="body" type="string">
    <!-- 
    	  - Назначение лекарственного средства
    	  -->
    <msh:form action="/entityParentSaveGoView-poly_drugPrescription.do" defaultField="id" title="Назначение лекарственного средства">
      <msh:hidden property="id" />
      <msh:hidden property="diary" />
      <msh:hidden property="saveType" />
      <msh:panel colsWidth="3">
        <msh:row>
          <msh:autoComplete  vocName="vocDrugClassify" property="drug" label="Лекарственный препарат" horizontalFill="true" fieldColSpan="3" size="50" />
        </msh:row>
        <msh:row>
          <msh:autoComplete vocName="vocDrugMethod" label="Способ введения" property="method" horizontalFill="true" fieldColSpan="3" size="50" />
        </msh:row>
        <msh:row>
          <msh:textField property="frequency" label="Частота" horizontalFill="true" />
          <msh:autoComplete vocName="vocFrequencyUnit" label="раза в " property="frequencyUnit" horizontalFill="true" />
        </msh:row>
        <msh:row>
          <msh:textField property="orderTime" label="Время приема (в минутах)"  />
          <msh:autoComplete vocName="vocPrescriptOrderType" label="Тип приема" property="orderType" horizontalFill="true" />
        </msh:row>
        <msh:row>
          <msh:textField label="Количество на один прием" property="amount" />
          <msh:autoComplete vocName="vocDrugAmountUnit" label="в единицах (мг, г, мл)" property="amountUnit" horizontalFill="true" />
        </msh:row>
        <msh:row>
          <msh:textField property="duration" label="Продолжительность"  />
          <msh:autoComplete vocName="vocDurationUnit" label="в единицах времени" property="durationUnit" horizontalFill="true" />
        </msh:row>
        <msh:submitCancelButtonsRow colSpan="4" />
      </msh:panel>
    </msh:form>
  </tiles:put>
  <tiles:put name="title" type="string">
    <ecom:titleTrail mainMenu="Medcard" beginForm="poly_drugPrescriptionForm" />
  </tiles:put>
  <tiles:put name="side" type="string">
    <msh:ifFormTypeIsView formName="poly_drugPrescriptionForm">
      <msh:sideMenu title="Лекарственное назначение">
        <msh:sideLink roles="/Policy/Poly/DrugPrescription/Edit" params="id" action="/entityParentEdit-poly_drugPrescription" name="Изменить" key="ALT+2"/>
        <msh:sideLink  confirm="Удалить?" roles="/Policy/Poly/DrugPrescription/Delete" params="id" action="/entityParentDelete-poly_drugPrescription" name="Удалить" key="ALT+DEL"/>
      </msh:sideMenu>
    </msh:ifFormTypeIsView>
  </tiles:put>
</tiles:insert>

