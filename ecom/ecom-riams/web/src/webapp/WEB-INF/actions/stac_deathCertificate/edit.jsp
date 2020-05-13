<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://struts.apache.org/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://www.nuzmsh.ru/tags/msh" prefix="msh" %>
<%@ taglib uri="http://www.ecom-ast.ru/tags/ecom" prefix="ecom" %>
<%@ taglib tagdir="/WEB-INF/tags" prefix="tags" %>
<tiles:insert page="/WEB-INF/tiles/mainLayout.jsp" flush="true">
  <tiles:put name="title" type="string">
    <ecom:titleTrail mainMenu="Patient" beginForm="stac_deathCertificateForm" />
  </tiles:put>
  <tiles:put name="side" type="string">
    <msh:ifFormTypeIsView formName="stac_deathCertificateForm">
      <msh:sideMenu title="Свидетельство">
       <msh:sideLink key="ALT+2" params="id" action="/entityEdit-stac_deathCertificate" name="Изменить" roles="/Policy/Mis/Certificate/Death/Edit" />
        <msh:sideLink key="ALT+DEL" confirm="Удалить?" params="id" action="/entityDelete-stac_deathCertificate" name="Удалить" roles="/Policy/Mis/Certificate/Death/Delete" />
      <msh:sideLink roles="/Policy/Mis/Certificate/Death/Print" name="Печать свидетельства о смерти" params="id"  action='/print-death.do?m=printDeathStac&s=CertificatePrintService' title="Печать свидетельства о смерти"/>	
      </msh:sideMenu>
    </msh:ifFormTypeIsView>
  </tiles:put>
  <tiles:put name="body" type="string">
    <!-- 
    	  - Свидетельство о смерти
    	  -->
    <msh:form action="/entityParentSaveGoView-stac_deathCertificate.do" defaultField="dateIssue" title="Свидетельство о смерти" editRoles="/Policy/Mis/Certificate/Death/Edit" createRoles="/Policy/Mis/Certificate/Death/Create">
      <msh:hidden property="id" />
      <msh:hidden property="deathCase" />
      <msh:hidden property="saveType" />
      <msh:panel>
      <msh:row>
          <msh:textField property="dateIssue" label="Дата выдачи документа" />
        </msh:row>
        <msh:row>
          <msh:textField property="series" label="Серия" />
          <msh:textField property="number" label="Номер" />
        </msh:row>
        <msh:row>
          <msh:autoComplete property="certificateType" label="Тип свидетельства" vocName="vocCertificateType" horizontalFill="true" fieldColSpan="3" />
        </msh:row>
        <msh:row>
          <msh:textField property="seriesPreCertificate" label="Серия предыдущего свидетельства" />
          <msh:textField property="numberPreCertificate" label="Номер предыдущего свидетельства" />
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
        <msh:submitCancelButtonsRow colSpan="3" />
      </msh:panel>
    </msh:form>
  </tiles:put>
</tiles:insert>

