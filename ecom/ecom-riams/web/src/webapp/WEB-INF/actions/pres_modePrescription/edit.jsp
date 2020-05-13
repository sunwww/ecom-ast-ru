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
      <msh:hidden property="id" />
      <msh:hidden property="prescriptionList" />
      <msh:hidden property="saveType" />
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
        <msh:ifFormTypeIsView formName="pres_modePrescriptionForm">
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
          <msh:autoComplete vocName="vocPrescriptCancelReason" label="Причина" property="cancelReason" horizontalFill="true" fieldColSpan="5" />
        </msh:row>
        <msh:row >
          <msh:autoComplete vocName="workFunction" label="Отменил" property="cancelSpecial" horizontalFill="true" fieldColSpan="5" />
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
        <mis:canShowPrescriptionFulfilments formName="pres_modePrescriptionForm">
    <msh:ifFormTypeIsView formName="pres_modePrescriptionForm">
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
    <ecom:titleTrail mainMenu="Prescription" beginForm="pres_modePrescriptionForm" />
  </tiles:put>
  <tiles:put name="side" type="string">
    <msh:ifFormTypeIsView formName="pres_modePrescriptionForm">
      <msh:sideMenu title="Назначение диеты">
        <msh:sideLink roles="/Policy/Mis/Prescription/ModePrescription/Edit" params="id" action="/entityParentEdit-pres_modePrescription" name="Изменить" key="ALT+2"/>
        <msh:sideLink  confirm="Удалить?" roles="/Policy/Mis/Prescription/ModePrescription/Delete" params="id" action="/entityParentDelete-pres_modePrescription" name="Удалить" key="ALT+DEL"/>
      </msh:sideMenu>
      <msh:sideMenu title="Добавить">
       <mis:canShowPrescriptionFulfilments formName="pres_modePrescriptionForm">
        <msh:sideLink roles="/Policy/Mis/Prescription/PrescriptionFulfilment/Create" params="id" action="/entityParentPrepareCreate-pres_prescriptionFulfilment" name="Исполнение назначения" key="ALT+3"/>
       </mis:canShowPrescriptionFulfilments>
      </msh:sideMenu>
      <msh:sideMenu title="Перейти" >
        <msh:sideLink roles="/Policy/Mis/Prescription/ModePrescription/View" params="id" action="/entityParentListRedirect-pres_modePrescription" name="К списку назначенных диет" key="ALT+4"/>
        <msh:sideLink roles="/Policy/Mis/Prescription/View" params="id" action="/entityParentListRedirect-pres_prescription" name="К списку назначений" key="ALT+5"/>
      </msh:sideMenu>
    </msh:ifFormTypeIsView>
  </tiles:put>
</tiles:insert>

