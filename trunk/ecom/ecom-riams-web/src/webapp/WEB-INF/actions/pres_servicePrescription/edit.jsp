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
    <msh:form guid="formHello" action="/entityParentSaveGoView-pres_servicePrescription.do" defaultField="id" title="Назначение медицинской услуги">
      <msh:hidden guid="hiddenId" property="id" />
      <msh:hidden property="prescriptionList" guid="8b852c-d5aa-40f0-a9f5-21dfgd6" />
      <msh:hidden guid="hiddenSaveType" property="saveType" />
      <msh:panel guid="panel" colsWidth="3">
        <msh:row guid="b5srehb-b971-441e-9a90-53217">
          <msh:autoComplete vocName="medService" property="medService" label="Медицинская услуга" guid="3a3eg4d1b-8802-467d-b205-711tre18" horizontalFill="true" fieldColSpan="3" size="50" />
        </msh:row>
        <msh:row guid="bwhb-b971-441e-9a90-53217">
          <msh:separator colSpan="6" label="График приема" guid="5r7v-7fee-45e5-96ec-7753b" />
        </msh:row>
        <msh:row guid="breb-b971-441e-9a90-5258c07">
          <msh:textField label="Дата начала" property="planStartDate" guid="3at4d1b-8802-467d-b205-714658" horizontalFill="true" />
          <msh:textField label="Время" property="planStartTime" guid="3eb-8802-467d-b205-71r5fb3" horizontalFill="true" />
        </msh:row>
        <msh:row guid="byb-b971-441e-9a90-58257">
          <msh:textField label="Дата окончания" property="planEndDate" guid="3a357b-8802-467d-b205-7f18" horizontalFill="true" />
          <msh:textField label="Время" property="planEndTime" guid="3d5b-8802-467d-b205-71r5fb3" horizontalFill="true" />
        </msh:row>
        <msh:row guid="3deb-b971-441e-9a90-513217">
          <msh:autoComplete vocName="workFunction" label="Назначил" property="prescriptSpecial" guid="30gfn-8802-467d-b205-798518" horizontalFill="true" fieldColSpan="3" size="50" />
        </msh:row>
        <msh:row guid="33d4b-b971-441e-9a90-5951">
          <msh:textField label="Дата отмены" property="cancelDate" guid="34sd1b-8802-467d-b205-764518" horizontalFill="true" />
          <msh:textField label="Время" property="cancelTime" guid="3aa3-8802-467d-b205-7312" horizontalFill="true" />
        </msh:row>
        <msh:row guid="06efb-b971-441e-9a90-591507">
          <msh:autoComplete vocName="vocPrescriptCancelReason" label="Причина" property="cancelReason" guid="d35f495n-8802-467d-b205-7159b018" horizontalFill="true" fieldColSpan="3" size="50" />
        </msh:row>
        <msh:row guid="32fvs6eb-b971-441e-9a90-584627">
          <msh:autoComplete vocName="workFunction" label="Отменил" property="cancelSpecial" guid="303s6541n-8802-467d-b205-715f18" horizontalFill="true" fieldColSpan="3" size="50" />
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
    
    <msh:ifFormTypeIsView guid="ifFormTypeIsView" formName="pres_servicePrescriptionForm">
      <msh:section guid="sectionChilds" title="Исполнения">
        <ecom:parentEntityListAll guid="parentEntityListChilds" formName="pres_prescriptionFulfilmentForm" attribute="fulfilments" />
        <msh:table guid="tableChilds3434" name="fulfilments" action="entityParentView-pres_prescriptionFulfilment.do" idField="id">
          <msh:tableColumn columnName="Дата исполнения" property="fulfilDate" guid="8c2s9b-89d7-46a9-a8c3-c08527e" />
          <msh:tableColumn columnName="Время исполнения" property="fulfilTime" guid="d6dd49-a94d-4cf2-af1b-02581f" />
          <msh:tableColumn columnName="Исполнитель" property="executorInfo" guid="bfv1-0967-45f2-a6af-f654e" />
        </msh:table>
      </msh:section>
    </msh:ifFormTypeIsView>
    
  </tiles:put>
  <tiles:put name="title" type="string">
    <ecom:titleTrail guid="titleTrail-123" mainMenu="Prescription" beginForm="pres_servicePrescriptionForm" />
  </tiles:put>
  <tiles:put name="side" type="string">
    <msh:ifFormTypeIsView formName="pres_servicePrescriptionForm" guid="99ca692-c1d3-4d79-bc37-c6726c">
      <msh:sideMenu title="Назначения" guid="eb3f54-b971-441e-9a90-51jhf">
        <msh:sideLink roles="/Policy/Mis/Prescription/ServicePrescription/Edit" params="id" action="/entityParentEdit-pres_servicePrescription" name="Изменить" guid="ca5sui7r-9239-47e3-aec4-995462584" key="ALT+2"/>
        <msh:sideLink confirm="Удалить?" roles="/Policy/Mis/Prescription/ServicePrescription/Delete" params="id" action="/entityParentDelete-pres_servicePrescription" name="Удалить" guid="ca5sui7r-9239-47e3-aec4-995462584" key="ALT+DEL"/>
      </msh:sideMenu>
      <msh:sideMenu title="Добавить" guid="0e2ac7-5361-434d-a8a7-1284796f">
    
        <msh:sideLink roles="/Policy/Mis/Prescription/PrescriptionFulfilment/Create" params="id" action="/entityParentPrepareCreate-pres_prescriptionFulfilment" name="Исполнение назначения" guid="ca3s9-9239-47e3-aec4-9a846547144" key="ALT+3"/>
      
      </msh:sideMenu>
      <msh:sideMenu title="Показать" guid="67aa758-3ad2-4e6f-a791-4839460955" >
        <msh:sideLink roles="/Policy/Mis/Prescription/ServicePrescription/View" params="id" action="/entityParentListRedirect-pres_servicePrescription" name="К списку назначений на услугу" guid="e9d94-fe74-4c43-85b1-267231ff" key="ALT+4"/>
        <msh:sideLink roles="/Policy/Mis/Prescription/View" params="id" action="/entityParentListRedirect-pres_prescription" name="К списку назначений" guid="e9d94-fe74-4c43-85b1-267231ff" key="ALT+4"/>
      </msh:sideMenu>
    </msh:ifFormTypeIsView>
  </tiles:put>
</tiles:insert>

