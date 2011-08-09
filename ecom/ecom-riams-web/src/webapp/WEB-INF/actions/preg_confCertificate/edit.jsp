<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://struts.apache.org/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://www.nuzmsh.ru/tags/msh" prefix="msh" %>
<%@ taglib uri="http://www.ecom-ast.ru/tags/ecom" prefix="ecom" %>
<%@ taglib tagdir="/WEB-INF/tags" prefix="tags" %>
<tiles:insert page="/WEB-INF/tiles/mainLayout.jsp" flush="true">

  <tiles:put name="title" type="string">
    <ecom:titleTrail mainMenu="Patient" beginForm="preg_confCertificateForm" guid="727d7847-7eca-4a47-8f78-8377419e3cfd" />
  </tiles:put>
  <tiles:put name="side" type="string">
    <msh:ifFormTypeIsView formName="preg_confCertificateForm" guid="45671f5b-6ec3-47b4-be5f-b702b1a7a599">
      <msh:sideMenu title="Родовый сертификат" guid="86691e50-ec88-4b2f-858d-01d755e973a5">
        <msh:sideLink key="ALT+2" params="id" action="/entityParentEdit-preg_confCertificate" 
        name="Изменить" roles="/Policy/Mis/Pregnancy/ConfinementCertificate/Edit" guid="ea5ae5fe-e804-4288-8c4b-36a51684e486" />
        <msh:sideLink key="ALT+DEL" confirm="Удалить?" params="id" 
        action="/entityParentDelete-preg_confCertificate" name="Удалить" roles="/Policy/Mis/Pregnancy/ConfinementCertificate/Delete" guid="bf201b8d-dc09-429d-8840-6938e484b043" />
      </msh:sideMenu>
    </msh:ifFormTypeIsView>
  </tiles:put>
  <tiles:put name="body" type="string">
    <!-- 
    	  - Свидетельство о рождении
    	  -->
    <msh:form action="/entityParentSaveGoParentView-preg_confCertificate.do" defaultField="dateIssue" title="Свидетельство о рождении" guid="6070d695-c8c1-4ab7-9c60-d8526f25a4fe">
      <msh:hidden property="id" guid="1ce76f0e-c269-4ddf-a299-5710db04b2bb" />
      <msh:hidden property="pregnancy" guid="8ce6e5fc-e517-4972-9f1d-9b814759d1c5" />
      <msh:hidden property="saveType" guid="97deaff5-7165-4a3a-ba93-3e91b2a6e6a2" />
      <msh:panel guid="f14302d7-09d1-4d99-9f14-48b1ea9b9e5d">
        <msh:row guid="879f3994-44cf-4d0b-b802-f1bf01e4c347">
          <msh:textField property="dateIssue" label="Дата выдачи документа" guid="6feb14c8-f3c1-4ae4-b83a-ae8373ced007" />
        </msh:row>
        <msh:row guid="cff8d7f0-4031-47ea-85cb-9cbf5328dba9">
          <msh:textField property="series" label="Серия" guid="81d3977d-c639-4b67-b3dc-477b6bf81260" />
          <msh:textField property="number" label="Номер" guid="e33baec3-ad0f-42ce-bad6-9699a263ee4c" />
        </msh:row>
        <msh:submitCancelButtonsRow colSpan="3" guid="submitCancel" />
      </msh:panel>
    </msh:form>
  </tiles:put>
</tiles:insert>

