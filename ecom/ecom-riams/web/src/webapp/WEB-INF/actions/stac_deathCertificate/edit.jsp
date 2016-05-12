<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://struts.apache.org/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://www.nuzmsh.ru/tags/msh" prefix="msh" %>
<%@ taglib uri="http://www.ecom-ast.ru/tags/ecom" prefix="ecom" %>
<%@ taglib tagdir="/WEB-INF/tags" prefix="tags" %>
<tiles:insert page="/WEB-INF/tiles/mainLayout.jsp" flush="true">
  <tiles:put name="title" type="string">
    <ecom:titleTrail mainMenu="Patient" beginForm="stac_deathCertificateForm" guid="e94d91f6-0082-43b1-a328-ccd889a4aab9" />
  </tiles:put>
  <tiles:put name="side" type="string">
    <msh:ifFormTypeIsView formName="stac_deathCertificateForm" guid="82890b00-ed5a-4fea-983f-08911ff05528">
      <msh:sideMenu title="Свидетельство" guid="51564165">
       <msh:sideLink key="ALT+2" params="id" action="/entityEdit-stac_deathCertificate" name="Изменить" roles="/Policy/Mis/Certificate/Death/Edit" guid="d7c7f8c0-e2b1-425a-bc04-c2093a35140f" />
        <msh:sideLink key="ALT+DEL" confirm="Удалить?" params="id" action="/entityDelete-stac_deathCertificate" name="Удалить" roles="/Policy/Mis/Certificate/Death/Delete" guid="cae8e1f1-1052-4a47-84d4-3a4a5dee7cc8" />
      <msh:sideLink roles="/Policy/Mis/Certificate/Death/Print" name="Печать свидетельства о смерти" params="id"  action='/print-death.do?m=printDeathStac&s=CertificatePrintService' title="Печать свидетельства о смерти"/>	
      </msh:sideMenu>
    </msh:ifFormTypeIsView>
  </tiles:put>
  <tiles:put name="body" type="string">
    <!-- 
    	  - Свидетельство о смерти
    	  -->
    <msh:form action="/entityParentSaveGoView-stac_deathCertificate.do" defaultField="dateIssue" title="Свидетельство о смерти" editRoles="/Policy/Mis/Certificate/Death/Edit" createRoles="/Policy/Mis/Certificate/Death/Create" guid="989b84c0-6ae6-486e-b9ad-d55e214a7611">
      <msh:hidden property="id" guid="d6adf603-7138-4dc6-8786-1233465aa012" />
      <msh:hidden property="deathCase" guid="de6bf1cc-b370-4cd9-8589-fbe22b1e4139" />
      <msh:hidden property="saveType" guid="5344b5d6-fefa-45e5-9221-3e6060a89d25" />
      <msh:panel guid="fddf9ed7-9d76-4c71-ad12-03edb4443a1c">
      <msh:row guid="c13e2463-cafe-4527-abac-6f352dacf05e">
          <msh:textField property="dateIssue" label="Дата выдачи документа" guid="5e908f16-ab32-4e2a-a5ce-1decd44acd6c" />
        </msh:row>
        <msh:row guid="b79162ed-cf6d-4287-94e4-712d078d2ee7">
          <msh:textField property="series" label="Серия" guid="da7f8ed7-34f7-483f-badd-a3ca52191e6c" />
          <msh:textField property="number" label="Номер" guid="d6cda854-1c06-4a86-b2fd-408baf83a5f9" />
        </msh:row>
        <msh:row guid="6694caef-c484-4cea-9877-2d2b66dde7f7">
          <msh:autoComplete property="certificateType" label="Тип свидетельства" vocName="vocCertificateType" horizontalFill="true" fieldColSpan="3" guid="26f360b5-bb45-4e3c-9a7e-61ecd7bae28d" />
        </msh:row>
        <msh:row guid="8a5c0c14-edc7-42b7-a1d0-54d0681139a2">
          <msh:textField property="seriesPreCertificate" label="Серия предыдущего свидетельства" guid="b64e270a-1fa1-46e1-8a8e-d56a45f62af6" />
          <msh:textField property="numberPreCertificate" label="Номер предыдущего свидетельства" guid="94a3f5d5-2608-4616-a456-55d91d0a6b1e" />
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
        <msh:submitCancelButtonsRow colSpan="3" guid="submitCancel" />
      </msh:panel>
    </msh:form>
  </tiles:put>
</tiles:insert>

