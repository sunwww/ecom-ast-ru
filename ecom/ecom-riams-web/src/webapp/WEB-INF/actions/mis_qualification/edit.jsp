<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://struts.apache.org/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://www.nuzmsh.ru/tags/msh" prefix="msh" %>
<%@ taglib uri="http://www.ecom-ast.ru/tags/ecom" prefix="ecom" %>

<tiles:insert page="/WEB-INF/tiles/mainLayout.jsp" flush="true">

  <tiles:put name="body" type="string">
    <!-- 
    	  - Проба
    	  -->
    <msh:form guid="formHello" action="/entityParentSaveGoParentView-mis_qualification.do" defaultField="institutName">
      <msh:hidden guid="hiddenId" property="id" />
      <msh:hidden guid="hiddenSaveType" property="saveType" />
      <msh:hidden guid="hiddenParent" property="person" />
      <msh:panel guid="panel" title="Квалификация">
        <msh:row guid="b5f456eb-b971-441e-9a90-5194a8019c07">
          <msh:autoComplete vocName="vocInstitut" property="institut" guid="3a3e4d1b-8802-467d-b205-715fb379b018" fieldColSpan="3" label="Организация" size="50" horizontalFill="true" />
          <msh:row guid="ab8491c0-7b9d-4ad8-86d9-7c590f172347">
            <msh:autoComplete vocName="vocSpec" property="spec" label="Специальность" guid="6dbbe759-318c-44b3-9487-8ae1ac9ad02a" fieldColSpan="3" horizontalFill="true" />
          </msh:row>
          <msh:autoComplete vocName="vocCertificateIssueBase" property="certificateIssueBase" label="Основание для выдачи сертификата" guid="cb31760b-2d92-46f0-9d60-a0bc135b5215" horizontalFill="true" fieldColSpan="3" />
        </msh:row>
        <msh:row guid="d8f86ac6-620c-4f3a-ba64-439778d98d75">
          <msh:textField passwordEnabled="false" property="certificateNumber" viewOnlyField="false" label="Номер сертификата" guid="ac6ce988-982d-41ef-9252-2a79d3c7ac30" horizontalFill="false" />
          <msh:textField passwordEnabled="false" property="awardingDate" viewOnlyField="false" label="Дата присвоения" guid="e899cbf2-2425-4aa7-b06d-8e3bb6d4050f" horizontalFill="false" />
        </msh:row>
        <msh:row guid="bf5ce54d-93b3-496d-92af-07c2714fed8b">
          <msh:autoComplete vocName="vocCategory" property="category" label="Присвоенная категория" guid="76b7c663-f53e-4043-9603-ef646c56840b" horizontalFill="true" fieldColSpan="3" />
        </msh:row>
        <msh:row guid="3c930888-ce58-4945-8660-8ed4e4f842ad">
          <msh:autoComplete vocName="vocAcademicDegree" property="academicDegree" label="Присвоенная степень" guid="a05cc1e9-e7ec-4d22-9654-451c61c6e5b9" fieldColSpan="3" horizontalFill="true" />
        </msh:row>
        <msh:row guid="eb000745-5946-4cf9-9809-091698c2c022">
          <msh:autoComplete vocName="vocAcademicStatus" property="academicStatus" label="Присвоенное звание" guid="ac342c5d-b7ae-4fa0-a88b-2b0ad4969f01" fieldColSpan="3" horizontalFill="true" />
        </msh:row>
        <msh:row guid="e86fd85d-e8fe-44c9-b15b-162d665b9711">
          <msh:textField passwordEnabled="false" property="certificateIssueDate" viewOnlyField="false" label="Дата выдачи сертификата" guid="4850f292-2bc7-4c82-be53-cc57190b308c" horizontalFill="false" />
        </msh:row>
        <msh:submitCancelButtonsRow guid="submitCancel" colSpan="4" />
      </msh:panel>
    </msh:form>
  </tiles:put>
  <tiles:put name="title" type="string">
    <ecom:titleTrail guid="titleTrail-123" mainMenu="Patient" beginForm="mis_qualificationForm" />
  </tiles:put>
  <tiles:put name="side" type="string">
    <msh:ifFormTypeIsView formName="mis_qualificationForm" insideJavascript="false" guid="83269a39-a4e6-4ddf-a160-6a90bb834578">
      <msh:sideMenu guid="sideMenu-123">
        <msh:sideLink guid="sideLinkEdit" key="ALT+2" params="id" action="/entityEdit-mis_qualification" name="Изменить" roles="/Policy/Mis/Worker/Qualification/Edit" />
        <msh:sideLink guid="sideLinkDelete" key="ALT+DEL" confirm="Удалить?" params="id" action="/entityParentDeleteGoParentView-mis_qualification" name="Удалить" roles="/Policy/Mis/Worker/Qualification/Delete" />
      </msh:sideMenu>
    </msh:ifFormTypeIsView>
  </tiles:put>
</tiles:insert>

