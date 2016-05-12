<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://struts.apache.org/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://www.nuzmsh.ru/tags/msh" prefix="msh" %>
<%@ taglib uri="http://www.ecom-ast.ru/tags/ecom" prefix="ecom" %>

<tiles:insert page="/WEB-INF/tiles/mainLayout.jsp" flush="true">

  <tiles:put name="body" type="string">
    <!-- 
    	  - Назначение лекарственного средства
    	  -->
    <msh:form action="/entityParentSaveGoParentView-pres_template_drugPrescription.do" defaultField="id" title="Назначение лекарственного средства" guid="112eae14-f772-4a7a-810f-b2fb21ecd727">
      <msh:hidden property="id" guid="5503cd86-19d0-4690-8393-6ca38cd12f94" />
      <msh:hidden property="saveType" guid="0ef44ff6-a77c-4590-8d15-29762b52ecdd" />
      <msh:hidden property="prescriptionList" guid="6a976239-5f6a-4a8b-ba76-9d26ecc44f19" />
      <msh:panel guid="a71ea46d-0ee9-4b59-9266-9f3ff7befb59">
        <msh:row guid="b556ehb-b971-441e-9a90-5194a8019c07">
          <msh:autoComplete viewAction="entityView-voc_drug.do" vocName="vocDrugClassify" property="drug" label="Лекарственный препарат" guid="3a3eg4d1b-8802-467d-b205-715fb379b018" horizontalFill="true" fieldColSpan="3" size="50" />
        </msh:row>
        <msh:row guid="cb556ehb-b971-441e-9a90-5194a8019c07">
          <msh:autoComplete vocName="vocDrugMethod" label="Способ введения" property="method" guid="3adk3e4d1b-8802-467d-b205-715fb379b018" horizontalFill="true" fieldColSpan="3" size="50" />
        </msh:row>
        <msh:row guid="b556gd-b971-441e-9a90-сc07">
          <msh:textField property="frequency" label="Частота" guid="3acgb-8802-467d-b205-71b018" horizontalFill="true" />
          <msh:autoComplete vocName="vocFrequencyUnit" label="раза в " property="frequencyUnit" guid="3bbba3deЫ-8802-467d-b205-715fb379b018" horizontalFill="true" />
        </msh:row>
        <msh:row guid="32v6eb-b971-441e-9a90-5194a8019c07">
          <msh:textField property="orderTime" label="Время приема (в минутах)" guid="3a3e-8802-467d-b205-715f" horizontalFill="true" />
          <msh:autoComplete vocName="vocPrescriptOrderType" label="Тип приема" property="orderType" guid="3gn-8802-467d-b205-715fb379b018" horizontalFill="true" />
        </msh:row>
        <msh:row guid="b55h6geb-b971-441e-9a90-5194a8019c07">
          <msh:textField label="Количество на один прием" property="amount" guid="3a3d8-467d-b205-715fb379b018" horizontalFill="true" />
          <msh:autoComplete vocName="vocDrugAmountUnit" label="в единицах (мг, г, мл)" property="amountUnit" guid="325b68-8802-467d-b205-7118" horizontalFill="true" />
        </msh:row>
        <msh:row guid="326eb-b971-441e-9a90-5194a8019c07">
          <msh:textField property="duration" label="Продолжительность" guid="325b-8802-467d-b205-715fb379gb018" horizontalFill="true" />
          <msh:autoComplete vocName="vocDurationUnit" label="в единицах времени" property="durationUnit" guid="32568-8802-467d-b205-715fb379b018" horizontalFill="true" />
        </msh:row>
        <msh:row guid="b556geb-b971-441e-9a90-019c07" />
        <msh:submitCancelButtonsRow guid="submitCancel" colSpan="4" />
      </msh:panel>
    </msh:form>
  </tiles:put>
  <tiles:put name="title" type="string">
    <ecom:titleTrail mainMenu="Template" beginForm="pres_template_drugPrescriptionForm" guid="1f35b378-0da6-432c-a08b-ff337979d1e1" />
  </tiles:put>
  <tiles:put name="side" type="string">
    <msh:ifFormTypeIsView formName="pres_template_drugPrescriptionForm" guid="a318e0b5-6e57-4979-9298-889aa351e858">
      <msh:sideMenu title="Назначение ЛС" guid="061ebcce-04e4-45ce-a352-4e701df99623">
        <msh:sideLink roles="/Policy/Mis/Prescription/Template/DrugPrescription/Edit" params="id" action="/entityParentEdit-pres_template_drugPrescription" name="Изменить" guid="8fc95f0e-a6c3-4ccc-8b57-d3817b100764" key="ALT+3" />
        <msh:sideLink roles="/Policy/Mis/Prescription/Template/DrugPrescription/Delete" params="id" action="/entityParentDelete-pres_template_drugPrescription" name="Удалить" guid="1e37111e-26e9-42d0-b9f2-48e967c6fb3f" key="ALT+DEL" confirm="Удалить назначение лекарственного средства?" />
      </msh:sideMenu>
      <msh:sideMenu title="Перейти" guid="1d0e402f-71fb-460f-b469-ff8b6bcc283a">
        <msh:sideLink key="ALT+9" action="/entityParentList-pres_template_prescription" name="⇧К списку шаблонов назначений" roles="/Policy/Mis/Prescription/Template/View" guid="66ef01c2-703b-4658-94cd-82eabf4d1687" params="id" />
      </msh:sideMenu>
    </msh:ifFormTypeIsView>
  </tiles:put>
</tiles:insert>

