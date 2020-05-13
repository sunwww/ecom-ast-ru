<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://struts.apache.org/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://www.nuzmsh.ru/tags/msh" prefix="msh" %>
<%@ taglib uri="http://www.ecom-ast.ru/tags/ecom" prefix="ecom" %>
<%@ taglib tagdir="/WEB-INF/tags" prefix="tags" %>
<tiles:insert page="/WEB-INF/tiles/mainLayout.jsp" flush="true">

  <tiles:put name="title" type="string">
    <ecom:titleTrail mainMenu="Patient" beginForm="preg_confCertificateForm" />
  </tiles:put>
  <tiles:put name="side" type="string">
    <msh:ifFormTypeIsView formName="preg_confCertificateForm">
      <msh:sideMenu title="Родовый сертификат">
        <msh:sideLink key="ALT+2" params="id" action="/entityParentEdit-preg_confCertificate" 
        name="Изменить" roles="/Policy/Mis/Pregnancy/ConfinementCertificate/Edit" />
        <msh:sideLink key="ALT+DEL" confirm="Удалить?" params="id" 
        action="/entityParentDelete-preg_confCertificate" name="Удалить" roles="/Policy/Mis/Pregnancy/ConfinementCertificate/Delete" />
      </msh:sideMenu>
    </msh:ifFormTypeIsView>
  </tiles:put>
  <tiles:put name="body" type="string">
    <!-- 
    	  - Свидетельство о рождении
    	  -->
    <msh:form action="/entityParentSaveGoParentView-preg_confCertificate.do" defaultField="dateIssue" title="Свидетельство о рождении">
      <msh:hidden property="id" />
      <msh:hidden property="pregnancy" />
      <msh:hidden property="saveType" />
      <msh:panel>
        <msh:row>
          <msh:textField property="dateIssue" label="Дата выдачи документа" />
        </msh:row>
        <msh:row>
          <msh:textField property="series" label="Серия" />
          <msh:textField property="number" label="Номер" />
        </msh:row>
        <msh:submitCancelButtonsRow colSpan="3" />
      </msh:panel>
    </msh:form>
  </tiles:put>
</tiles:insert>

