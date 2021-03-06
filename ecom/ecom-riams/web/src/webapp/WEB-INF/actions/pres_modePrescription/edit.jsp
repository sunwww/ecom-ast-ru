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
    <msh:form action="/entityParentSaveGoView-pres_modePrescription.do" defaultField="id" title="Назначение режимма">
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
        <msh:row >
          <msh:textField label="Дата начала" property="planStartDate" />
          <msh:textField label="Время" property="planStartTime" />
        </msh:row>
        <msh:ifFormTypeIsView guid="ifFormTypeIsView" formName="pres_modePrescriptionForm">
        <msh:row >
          <msh:autoComplete vocName="workFunction" label="Назначил" property="prescriptSpecial" horizontalFill="true" fieldColSpan="5" />
        </msh:row>
        </msh:ifFormTypeIsView>
        <msh:row >
          <msh:textField label="Дата окончания" property="planEndDate" />
          <msh:textField label="Время" property="planEndTime" />
        </msh:row>
        <msh:row >
          <msh:textField label="Дата отмены" property="cancelDate" />
          <msh:textField label="Время" property="cancelTime" />
        </msh:row>
        <msh:row >
          <msh:autoComplete vocName="vocPrescriptCancelReason" label="Причина" property="cancelReason" guid="d35495n-8802-467d-b205-7159b018" horizontalFill="true" fieldColSpan="5" />
        </msh:row>
        <msh:row >
          <msh:autoComplete vocName="workFunction" label="Отменил" property="cancelSpecial" guid="3036541n-8802-467d-b205-715f18" horizontalFill="true" fieldColSpan="5" />
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
        <mis:canShowPrescriptionFulfilments formName="pres_modePrescriptionForm" guid="6eb-b971-441e-9a90-58">
    <msh:ifFormTypeIsView guid="ifFormTypeIsView" formName="pres_modePrescriptionForm">
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
    <ecom:titleTrail guid="titleTrail-123" mainMenu="Prescription" beginForm="pres_modePrescriptionForm" />
  </tiles:put>
  <tiles:put name="side" type="string">
    <msh:ifFormTypeIsView formName="pres_modePrescriptionForm" guid="998yr7aa692-c1d3-4d79-bc37-c6726c">
      <msh:sideMenu title="Назначение диеты" guid="eb387254-b971-441e-9a90-51jhf">
        <msh:sideLink roles="/Policy/Mis/Prescription/ModePrescription/Edit" params="id" action="/entityParentEdit-pres_modePrescription" name="Изменить" guid="ca519fhfui7r-9239-47e3-aec4-995462584" key="ALT+2"/>
        <msh:sideLink  confirm="Удалить?" roles="/Policy/Mis/Prescription/ModePrescription/Delete" params="id" action="/entityParentDelete-pres_modePrescription" name="Удалить" guid="ca519fhfui7r-9239-47e3-aec4-995462584" key="ALT+DEL"/>
      </msh:sideMenu>
      <msh:sideMenu title="Добавить" guid="0e25aac7-5361-434d-a8a7-1284796f">
       <mis:canShowPrescriptionFulfilments formName="pres_modePrescriptionForm" guid="4-b971-441e-9a90-51j">
        <msh:sideLink roles="/Policy/Mis/Prescription/PrescriptionFulfilment/Create" params="id" action="/entityParentPrepareCreate-pres_prescriptionFulfilment" name="Исполнение назначения" guid="ca3519-9239-47e3-aec4-9a846547144" key="ALT+3"/>
       </mis:canShowPrescriptionFulfilments>
      </msh:sideMenu>
      <msh:sideMenu title="Перейти" guid="67f8ha758-3ad2-4e6f-a791-4839460955" >
        <msh:sideLink roles="/Policy/Mis/Prescription/ModePrescription/View" params="id" action="/entityParentListRedirect-pres_modePrescription" name="К списку назначенных диет" guid="e98f5d94-fe74-4c43-85b1-267231ff" key="ALT+4"/>
        <msh:sideLink roles="/Policy/Mis/Prescription/View" params="id" action="/entityParentListRedirect-pres_prescription" name="К списку назначений" guid="e98f5d94-fe74-4c43-85b1-267231ff" key="ALT+5"/>
      </msh:sideMenu>
    </msh:ifFormTypeIsView>
  </tiles:put>
</tiles:insert>

