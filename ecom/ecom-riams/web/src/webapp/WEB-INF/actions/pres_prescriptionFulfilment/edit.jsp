<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://struts.apache.org/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://www.nuzmsh.ru/tags/msh" prefix="msh" %>
<%@ taglib uri="http://www.ecom-ast.ru/tags/ecom" prefix="ecom" %>

<tiles:insert page="/WEB-INF/tiles/mainLayout.jsp" flush="true">

  <tiles:put name="body" type="string">
    <!-- 
    	  - 
    	  -->
    <msh:form action="/entitySaveGoView-pres_prescriptionFulfilment.do" defaultField="fulfilDate" title="Исполнение назначения" guid="d847-1725-44c0-898b-db">
      <msh:hidden guid="hiddenId" property="id" />
      <msh:hidden guid="hiddenSaveType" property="saveType" />
      <msh:panel guid="panel">
        <msh:row guid="bh5fn456eb-b971-441e-9a90-51941">
          <msh:label property="prescription" label="Назначение" viewOnlyField="true" guid="ae3h8cf5e-1794-4d32-8918-ad92gb5" fieldColSpan="3" horizontalFill="true" />
        </msh:row>
        <msh:row guid="row1">
          <msh:textField property="fulfilDate" label="Дата выполнения" guid="detgffg5-3e8bg-496d-9d2f-687" />
          <msh:textField property="fulfilTime" label="Время выполнения" guid="ae3h5e-1794-4d32-8918-adxae7eb5" />
        </msh:row>
        <msh:row guid="e3dh5e-1794-4d32-8918-a76dxae7">
          <msh:textField property="comments" label="Комментарий" guid="g5r5-3e8bg-496d-9d2f-687" horizontalFill="true" size="40" fieldColSpan="3" />
        </msh:row>
        <msh:row guid="456eb-b971-441e-9a90-51941">
          <msh:autoComplete vocName="workFunction" property="executorWorkFunction" label="Исполнитель" fieldColSpan="3" horizontalFill="true" guid="6g55-3e8bg-496d-9d2f-fb67" />
        </msh:row>
        <msh:row guid="456eb-b971">
          <msh:textField property="dateCreate" label="Дата создания" guid="5bc8195c-7aeb-4580-bb9c-62a40ad57e6b" viewOnlyField="true" />
          <msh:textField property="username" label="Пользователь" guid="a80d6359-1da8-4b59-a408-638bdcfa399a" viewOnlyField="true" />
        </msh:row>
        <msh:submitCancelButtonsRow guid="submitCancel" colSpan="4" />
      </msh:panel>
    </msh:form>
  </tiles:put>
  <tiles:put name="title" type="string">
    <ecom:titleTrail guid="titleTrail-123" mainMenu="Patient" beginForm="pres_prescriptionFulfilmentForm" />
  </tiles:put>
  <tiles:put name="side" type="string">
    <msh:sideMenu guid="sideMenu-123" title="">
      <msh:ifFormTypeIsView formName="pres_prescriptionFulfilmentForm" guid="63abbf1e-9176-4cf0-b457-95cb7cf62109">
        <msh:sideLink roles="/Policy/Mis/Prescription/PrescriptionFulfilment/Edit" key="ALT+2" params="id" action="/entityParentEdit-pres_prescriptionFulfilment" name="Изменить" guid="c7h48-a626-4b6e-af52-cdcc98b" />
        <msh:sideLink roles="/Policy/Mis/Prescription/PrescriptionFulfilment/Delete" key="ALT+DEL" confirm="Удалить?" params="id" action="/entityParentDeleteGoSubclassView-pres_prescriptionFulfilment" name="Удалить" guid="48-a626-4b6e-af52-cdcc98" />
      </msh:ifFormTypeIsView>
    </msh:sideMenu>
  </tiles:put>
</tiles:insert>

