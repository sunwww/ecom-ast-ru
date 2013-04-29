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
    <msh:form guid="formHello" action="/entityParentSaveGoView-pres_drugPrescription.do" defaultField="id" title="Назначение лекарственного средства">
      <msh:hidden guid="hiddenId" property="id" />
      <msh:hidden property="prescriptionList" guid="8bca0c6c-d5aa-40f0-a9f5-216fc7c32ad6" />
      <msh:hidden guid="hiddenSaveType" property="saveType" />
      <msh:panel guid="panel" colsWidth="3">
        <msh:row guid="b556ehb-b971-441e-9a90-5194a8019c07">
          <msh:autoComplete  vocName="vocDrugClassify" property="drug" label="Лекарственный препарат" guid="3a3eg4d1b-8802-467d-b205-715fb379b018" horizontalFill="true" fieldColSpan="3" size="50" />
        </msh:row>
        <msh:row guid="cb556ehb-b971-441e-9a90-5194a8019c07">
          <msh:autoComplete vocName="vocDrugMethod" label="Способ введения" property="method" guid="3adk3e4d1b-8802-467d-b205-715fb379b018" horizontalFill="true" fieldColSpan="3" size="50" />
        </msh:row>
        <msh:row guid="b556gd-b971-441e-9a90-сc07">
          <msh:textField property="frequency" label="Частота" guid="3acgb-8802-467d-b205-71b018" horizontalFill="true" />
          <msh:autoComplete vocName="vocFrequencyUnit" label="раза в " property="frequencyUnit" guid="3bbba3deЫ-8802-467d-b205-715fb379b018" horizontalFill="true" />
        </msh:row>
        <msh:row guid="b55h6geb-b971-441e-9a90-5194a8019c07">
          <msh:textField label="Кол-во на один прием" property="amount" guid="3a3d8-467d-b205-715fb379b018" horizontalFill="true" />
          <msh:autoComplete vocName="vocDrugAmountUnit" label="в единицах (мг, г, мл)" property="amountUnit" guid="325b68-8802-467d-b205-7118" horizontalFill="true" />
        </msh:row>
        <msh:row guid="326eb-b971-441e-9a90-5194a8019c07">
          <msh:textField property="duration" label="Продолжительность" guid="325b-8802-467d-b205-715fb379gb018" horizontalFill="true" />
          <msh:autoComplete vocName="vocDurationUnit" label="в единицах времени" property="durationUnit" guid="32568-8802-467d-b205-715fb379b018" horizontalFill="true" />
        </msh:row>
        <msh:row guid="b556geb-b971-441e-9a90-019c07" />
        <msh:row guid="7n1b3kf-1dd4-4af3-be58-9b1d7669vyt61c3">
          <msh:separator colSpan="6" label="График приема" guid="52167v-7fee-45e5-96ec-7b0ggf19182cfcb" />
        </msh:row>
        <msh:row guid="b55j6eb-b971-441e-9a90-5194ac8019c07">
          <msh:textField label="Дата начала" property="planStartDate" guid="3am3e4d1b-8802-467d-b205-715fb379db018" horizontalFill="true" />
          <msh:textField label="Время" property="planStartTime" guid="3a3e4fd1b-8802-467d-b205-71r5fb3" horizontalFill="true" />
        </msh:row>
        <msh:row guid="bg55j6eb-b971-441e-9a90-5194ac8019c07">
          <msh:textField label="Дата окончания" property="planEndDate" guid="3a3e4dx1b-8802-467d-b205-71m5fb379b018" horizontalFill="true" />
          <msh:textField label="Время" property="planEndTime" guid="3a3e4df1b-8802-467d-b205-71r5fb3" horizontalFill="true" />
        </msh:row>
        <msh:row guid="32fv6eb-b971-441e-9a90-5194a8019c07">
          <msh:autoComplete vocName="workFunction" label="Назначил" property="prescriptSpecial" guid="30gn-8802-467d-b205-715fb379b018" horizontalFill="true" fieldColSpan="3" size="50" />
        </msh:row>
        <msh:row guid="32ffv6eb-b971-441e-9a90-5194a8019c07">
          <msh:textField label="Дата отмены" property="cancelDate" guid="34d1b-8802-467d-b205-715fb379db018" horizontalFill="true" />
          <msh:textField label="Время" property="cancelTime" guid="3a3-8802-467d-b205-71r5fb3" horizontalFill="true" />
        </msh:row>
        <msh:row guid="06eb-b971-441e-9a90-5194a8019c07">
          <msh:autoComplete vocName="vocPrescriptCancelReason" label="Причина" property="cancelReason" guid="d30gn-8802-467d-b205-7159b018" horizontalFill="true" fieldColSpan="3" size="50" />
        </msh:row>
        <msh:row guid="32fv6eb-b971-441e-9a90-51d207">
          <msh:autoComplete vocName="workFunction" label="Отменил" property="cancelSpecial" guid="30gсn-8802-467d-b205-715f18" horizontalFill="true" fieldColSpan="3" size="50" />
        </msh:row>
        <msh:row>
        	<msh:separator label="Дополнительная информация" colSpan="4"/>
        </msh:row>
        <msh:row>
        	<msh:label property="createDate" label="Дата создания"/>
        	<msh:label property="createTime" label="время"/>
        </msh:row>
        <msh:row>
        	<msh:label property="createUsername" label="пользователь"/>
        </msh:row>
        <msh:row>
        	<msh:label property="editDate" label="Дата редактирования"/>
        	<msh:label property="editTime" label="время"/>
        </msh:row>
        <msh:row>
        	<msh:label property="editUsername" label="пользователь"/>
        </msh:row>                        
        <msh:submitCancelButtonsRow guid="submitCancel" colSpan="4" />
      </msh:panel>
    </msh:form>
    <mis:canShowPrescriptionFulfilments formName="pres_drugPrescriptionForm" guid="6eb-b971-441e-9a90-51d2">
    <msh:ifFormTypeIsView guid="ifFormTypeIsView" formName="pres_drugPrescriptionForm" >
      <msh:section guid="sectionChilds" title="Исполнения">
        <ecom:parentEntityListAll guid="parentEntityListChilds" formName="pres_prescriptionFulfilmentForm" attribute="fulfilments" />
        <msh:table guid="tableChilds3434" name="fulfilments" action="entityParentView-pres_prescriptionFulfilment.do" idField="id">
          <msh:tableColumn columnName="Дата исполнения" property="fulfilDate" guid="8c2a3f9b-89d7-46a9-a8c3-c08029ec047e" />
          <msh:tableColumn columnName="Время исполнения" property="fulfilTime" guid="d61b9d49-a94d-4cf2-af1b-05020213901f" />
          <msh:tableColumn columnName="Исполнитель" property="executorInfo" guid="bfe44281-0967-45f2-a6af-f5b368cca8ae" />
        </msh:table>
      </msh:section>
    </msh:ifFormTypeIsView>
    </mis:canShowPrescriptionFulfilments>
  </tiles:put>
  <tiles:put name="title" type="string">
    <ecom:titleTrail guid="titleTrail-123" mainMenu="Prescription" beginForm="pres_drugPrescriptionForm" />
  </tiles:put>
  <tiles:put name="side" type="string">
    <msh:ifFormTypeIsView formName="pres_drugPrescriptionForm" guid="998yr7aa692-c1d3-4d79-bc37-cfa204fa846c">
      <msh:sideMenu title="Лекарственное назначение" guid="eb2154-b971-441e-9a90-51jhf">
        <msh:sideLink roles="/Policy/Mis/Prescription/DrugPrescription/Edit" params="id" action="/entityParentEdit-pres_drugPrescription" name="Изменить" guid="ca519fhfui7r-9239-47e3-aec4-9a0336e47144" key="ALT+2"/>
        <msh:sideLink  confirm="Удалить?" roles="/Policy/Mis/Prescription/DrugPrescription/Delete" params="id" action="/entityParentDelete-pres_drugPrescription" name="Удалить" guid="ca519fhfui7r-9239-47e3-aec4-9a0336e47144" key="ALT+DEL"/>
      </msh:sideMenu>
     
      <msh:sideMenu title="Добавить" guid="0e25aac7-5361-434d-a8a7-1237aabb506f">
        <mis:canShowPrescriptionFulfilments formName="pres_drugPrescriptionForm" guid="aa692-c1d3-4d79-bc37-cfa20">
        <msh:sideLink roles="/Policy/Mis/Prescription/PrescriptionFulfilment/Create" params="id" action="/entityParentPrepareCreate-pres_prescriptionFulfilment" name="Исполнение назначения" guid="ca3519-9239-47e3-aec4-9a847144" key="ALT+3"/>
      </mis:canShowPrescriptionFulfilments>
      </msh:sideMenu>
      <msh:sideMenu title="Перейти" guid="67f8ha758-3ad2-4e6f-a791-482e3cc20955" >
        <msh:sideLink roles="/Policy/Mis/Prescription/DrugPrescription/View" params="id" action="/entityParentListRedirect-pres_drugPrescription" name="К списку лекарственных назначений" guid="e98f5d94-fe74-4c43-85b1-2e4847ce3eff"  key="ALT+4"/>
        <msh:sideLink roles="/Policy/Mis/Prescription/View" params="id" action="/entityParentListRedirect-pres_prescription" name="К списку назначений" guid="e98f5d94-fe74-4c43-85b1-2e4847ce3eff" key="ALT+5"/>
      </msh:sideMenu>
    </msh:ifFormTypeIsView>
  </tiles:put>
</tiles:insert>

