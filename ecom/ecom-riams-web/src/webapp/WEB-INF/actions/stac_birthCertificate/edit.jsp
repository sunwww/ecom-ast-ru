<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://struts.apache.org/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://www.nuzmsh.ru/tags/msh" prefix="msh" %>
<%@ taglib uri="http://www.ecom-ast.ru/tags/ecom" prefix="ecom" %>
<%@ taglib tagdir="/WEB-INF/tags" prefix="tags" %>
<tiles:insert page="/WEB-INF/tiles/mainLayout.jsp" flush="true">
  <tiles:put name="title" type="string">
    <ecom:titleTrail mainMenu="Patient" beginForm="stac_birthCertificateForm" />
  </tiles:put>
  <tiles:put name="side" type="string">
    <msh:ifFormTypeIsView formName="stac_birthCertificateForm" >
      <msh:sideMenu title="Свидетельство" >
       <msh:sideLink key="ALT+2" params="id" action="/entityEdit-stac_birthCertificate" name="Изменить" roles="/Policy/Mis/Certificate/Birth/Edit" />
        <msh:sideLink key="ALT+DEL" confirm="Удалить?" params="id" action="/entityDelete-stac_birthCertificate" name="Удалить" roles="/Policy/Mis/Certificate/Birth/Delete" />
<!--      <msh:sideLink roles="/Policy/Mis/Certificate/Birth/Print" name="Печать свидетельства о рождении" params="id"  action='/print-death.do?m=printBirthStac&s=CertificatePrintService' title="Печать свидетельства о рождении"/>	-->
      </msh:sideMenu>
    </msh:ifFormTypeIsView>
  </tiles:put>
  <tiles:put name="body" type="string">
    <!-- 
    	  - Свидетельство о рождении
    	  -->
    <msh:form action="/entityParentSaveGoView-stac_birthCertificate.do" defaultField="dateIssue" title="Свидетельство о рождении" editRoles="/Policy/Mis/Certificate/Birth/Edit" createRoles="/Policy/Mis/Certificate/Birth/Create" >
      <msh:hidden property="id" />
      <msh:hidden property="birthCase" />
      <msh:hidden property="saveType" />
      <msh:panel >
      <msh:row >
          <msh:textField property="dateIssue" label="Дата выдачи документа" />
        </msh:row>
        <msh:row >
          <msh:textField property="series" label="Серия" />
          <msh:textField property="number" label="Номер" />
        </msh:row>
        <msh:submitCancelButtonsRow colSpan="3" guid="submitCancel" />
      </msh:panel>
    </msh:form>
  </tiles:put>
</tiles:insert>

