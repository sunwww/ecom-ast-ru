<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://struts.apache.org/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://www.nuzmsh.ru/tags/msh" prefix="msh" %>
<%@ taglib uri="http://www.ecom-ast.ru/tags/ecom" prefix="ecom" %>
<%@ taglib uri="http://www.ecom-ast.ru/tags/mis" prefix="mis" %>
<tiles:insert page="/WEB-INF/tiles/mainLayout.jsp" flush="true">

  <tiles:put name="body" type="string">
    <!-- 
    	  - Назначение режима
    	  -->
    <msh:form action="/entityParentSaveGoView-pres_template_modePrescription.do" defaultField="id" title="Назначение режимма">
      <msh:hidden guid="hiddenId" property="id" />
      <msh:hidden property="prescriptionList" />
      <msh:hidden guid="hiddenSaveType" property="saveType" />
      <msh:panel >
        <msh:row>
          <msh:autoComplete vocName="vocModePrescription" property="modePrescription" label="Режим" horizontalFill="true" fieldColSpan="5" />
        </msh:row>
        <msh:row >
          <msh:separator colSpan="8" label="График приема" />
        </msh:row>
        <msh:row>
        	<msh:separator label="Дополнительная информация" colSpan="8"/>
        </msh:row>
        <msh:row>
        	<msh:label property="createDate" label="Дата создания"/>
        	<msh:label property="createTime" label="время"/>
        	<msh:label property="createUsername" label="пользователь"/>
        </msh:row>
        <msh:row>
        	<msh:label property="editDate" label="Дата редактирования"/>
        	<msh:label property="editTime" label="время"/>
        	<msh:label property="editUsername" label="пользователь"/>
        </msh:row>                
        <msh:submitCancelButtonsRow colSpan="4" />
      </msh:panel>
    </msh:form>
        <mis:canShowPrescriptionFulfilments formName="pres_template_modePrescriptionForm" guid="6eb-b971-441e-9a90-58">
    <msh:ifFormTypeIsView guid="ifFormTypeIsView" formName="pres_template_modePrescriptionForm">
      <msh:section guid="sectionChilds" title="Исполнения">
        <ecom:parentEntityListAll guid="parentEntityListChilds" formName="pres_prescriptionFulfilmentForm" attribute="fulfilments" />
        <msh:table guid="tableChilds3434" name="fulfilments" action="entityParentView-pres_prescriptionFulfilment.do" idField="id">
          <msh:tableColumn columnName="Дата исполнения" property="fulfilDate" guid="8c2a3f9b-89d7-46a9-a8c3-c08527e" />
          <msh:tableColumn columnName="Время исполнения" property="fulfilTime" guid="d61b9d49-a94d-4cf2-af1b-02581f" />
          <msh:tableColumn columnName="Исполнитель" property="executorInfo" guid="bfe44281-0967-45f2-a6af-f654e" />
        </msh:table>
      </msh:section>
    </msh:ifFormTypeIsView>
    </mis:canShowPrescriptionFulfilments>
  </tiles:put>
  <tiles:put name="title" type="string">
    <ecom:titleTrail guid="titleTrail-123" mainMenu="Prescription" beginForm="pres_template_modePrescriptionForm" />
  </tiles:put>
  <tiles:put name="side" type="string">
    <msh:ifFormTypeIsView formName="pres_template_modePrescriptionForm" guid="998yr7aa692-c1d3-4d79-bc37-c6726c">
      <msh:sideMenu title="Назначение диеты" guid="eb387254-b971-441e-9a90-51jhf">
        <msh:sideLink roles="/Policy/Mis/Prescription/Template/ModePrescription/Edit" params="id" action="/entityParentEdit-pres_template_modePrescription" name="Изменить" guid="ca519fhfui7r-9239-47e3-aec4-995462584" key="ALT+2"/>
        <msh:sideLink  confirm="Удалить?" roles="/Policy/Mis/Prescription/Template/ModePrescription/Delete" params="id" action="/entityParentDelete-pres_template_modePrescription" name="Удалить" guid="ca519fhfui7r-9239-47e3-aec4-995462584" key="ALT+DEL"/>
      </msh:sideMenu>
    </msh:ifFormTypeIsView>
  </tiles:put>
</tiles:insert>

