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
    <msh:form guid="formHello" action="/entityParentSaveGoParentView-poly_drugPrescription.do" defaultField="id" title="Назначение лекарственного средства">
      <msh:hidden guid="hiddenId" property="id" />
      <msh:hidden property="diary" guid="8bca0c6c-d5aa-40f0-a9f5-216fc7c32ad6" />
      <msh:hidden guid="hiddenSaveType" property="saveType" />
      <msh:panel>
      	<msh:row>
      		<msh:textField property="numberPrescript" label="Номер"/>
      	</msh:row>
        <msh:row guid="b556ehb-b971-441e-9a90-5194a8019c07">
          <msh:autoComplete  vocName="vocDrugClassify" property="drug" label="Лекарственный препарат" guid="3a3eg4d1b-8802-467d-b205-715fb379b018" fieldColSpan="3" horizontalFill="true"/>
        </msh:row>
        <msh:row guid="b55h6geb-b971-441e-9a90-5194a8019c07">
          <msh:textField label="Кол-во на один прием" property="amountString" guid="3a3d8-467d-b205-715fb379b018" />
          <msh:autoComplete vocName="vocDrugAmountUnit" label="в единицах (мг, г, мл)" property="amountUnit" guid="325b68-8802-467d-b205-7118" horizontalFill="true" />
        </msh:row>
        <msh:row guid="b556gd-b971-441e-9a90-сc07">
          <msh:autoComplete vocName="vocFrequencyUnit" label="Время приема" property="frequencyUnit" guid="3bbba3deЫ-8802-467d-b205-715fb379b018" horizontalFill="true" fieldColSpan="3" />
        </msh:row>
        <msh:row guid="326eb-b971-441e-9a90-5194a8019c07">
          <msh:textField property="duration" label="Продолжительность" guid="325b-8802-467d-b205-715fb379gb018" />
          <msh:autoComplete vocName="vocDurationUnit" label="в единицах времени" property="durationUnit" guid="32568-8802-467d-b205-715fb379b018" horizontalFill="true" />
        </msh:row>
        <msh:row guid="cb556ehb-b971-441e-9a90-5194a8019c07">
          <msh:autoComplete vocName="vocDrugMethod" label="Способ введения" property="method" guid="3adk3e4d1b-8802-467d-b205-715fb379b018" horizontalFill="true" fieldColSpan="3"  />
        </msh:row>
        <msh:submitCancelButtonsRow guid="submitCancel" colSpan="3" />
      </msh:panel>
    </msh:form>
  </tiles:put>
  <tiles:put name="title" type="string">
    <ecom:titleTrail guid="titleTrail-123" mainMenu="Medcard" beginForm="poly_drugPrescriptionForm" />
  </tiles:put>
  <tiles:put name="side" type="string">
    <msh:ifFormTypeIsView formName="poly_drugPrescriptionForm" guid="998yr7aa692-c1d3-4d79-bc37-cfa204fa846c">
      <msh:sideMenu title="Лекарственное назначение" guid="eb2154-b971-441e-9a90-51jhf">
        <msh:sideLink roles="/Policy/Poly/DrugPrescription/Edit" params="id" action="/entityParentEdit-poly_drugPrescription" name="Изменить" guid="ca519fhfui7r-9239-47e3-aec4-9a0336e47144" key="ALT+2"/>
        <msh:sideLink  confirm="Удалить?" roles="/Policy/Poly/DrugPrescription/Delete" params="id" action="/entityParentDelete-poly_drugPrescription" name="Удалить" guid="ca519fhfui7r-9239-47e3-aec4-9a0336e47144" key="ALT+DEL"/>
      </msh:sideMenu>
    </msh:ifFormTypeIsView>
  </tiles:put>
</tiles:insert>

