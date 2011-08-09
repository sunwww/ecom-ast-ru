<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://struts.apache.org/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://www.nuzmsh.ru/tags/msh" prefix="msh" %>
<%@ taglib uri="http://www.ecom-ast.ru/tags/ecom" prefix="ecom" %>

<tiles:insert page="/WEB-INF/tiles/mainLayout.jsp" flush="true">

  <tiles:put name="body" type="string">
    <!-- 
    	  - Случай нетрудоспособности
    	  -->
    <msh:form guid="formHello" action="/entityParentSaveGoView-dis_case.do" defaultField="dateFrom">
      <msh:hidden guid="hiddenId" property="id" />
      <msh:hidden guid="hiddenSaveType" property="saveType" />
      <msh:hidden guid="hiddenParent" property="patient" />
      <msh:panel guid="panel">
        <msh:row guid="row1">
          <msh:textField guid="textFieldHello" property="dateFrom" label="Дата начала" viewOnlyField="true" />
          <msh:textField property="dateTo" label="Дата окончания" guid="ae38cf5e-1794-4d32-8918-ad928dae7eb5" viewOnlyField="true" />
        </msh:row>
        <msh:row>
        	<msh:checkBox fieldColSpan="3" horizontalFill="true" property="placementService" label="Состоит на учете в службе занятости"/>
        </msh:row>

        <msh:row>
        	<msh:checkBox property="earlyPregnancyRegistration" label="Поставлена на учет в ранние сроки беременности (до 12 недель)" horizontalFill="true" fieldColSpan="6"/>
        </msh:row>
        <msh:row guid="b5f456eb-b971-441e-9a90-5194a8019c07">
          <msh:label property="duration" label="Продолжительность в днях" guid="6e584655-3e8b-496d-9d2f-68706daafb67" />
        </msh:row>
        <msh:submitCancelButtonsRow guid="submitCancel" colSpan="4" />
      </msh:panel>
    </msh:form>
    <msh:ifFormTypeIsView guid="ifFormTypeIsView" formName="dis_caseForm">
      <msh:section guid="sectionChilds" title="Документы нетрудоспособности">
        <ecom:parentEntityListAll guid="parentEntityListChilds" formName="dis_documentForm" attribute="childs" />
        <msh:table guid="tableChilds3434" name="childs" action="entityParentView-dis_document.do" idField="id">
          <msh:tableColumn columnName="Дата выдачи" property="issueDate" guid="8c2a3f9b-89d7-46a9-a8c3-c08029ec047e" />
          <msh:tableColumn columnName="Тип документа" property="documentTypeInfo" guid="71edd77-ddd1-4ed-453fa2687534" />
          <msh:tableColumn columnName="Первичность" property="primarityInfo" guid="71edd774-ddd1-4e0b-ae7d-453fa2687534" />
          <msh:tableColumn columnName="Информация" property="info" guid="d61b9d49-a94d-4cf2-af1b-05020213901f" />
          <msh:tableColumn identificator="false" property="dateFrom" guid="7623c0df-b830-43de-9e73-957a91423b77" columnName="Дата начала" />
          <msh:tableColumn columnName="Дата окончания" identificator="false" property="dateTo" guid="5b05897f-5dfd-4aee-ada9-d04244ef20c6" />
        </msh:table>
      </msh:section>
    </msh:ifFormTypeIsView>
  </tiles:put>
  <tiles:put name="title" type="string">
    <ecom:titleTrail guid="titleTrail-123" mainMenu="Disability" beginForm="dis_caseForm" />
  </tiles:put>
  <tiles:put name="side" type="string">
    <msh:ifFormTypeIsView formName="dis_caseForm" guid="a2b73e72-2f8a-4b8e-a53e-2925252d9eba">
      <msh:sideMenu guid="sideMenu-123" title="Случай">
     <%--    <msh:sideLink guid="sideLinkEdit" key="ALT+2" params="id" action="/entityEdit-dis_case" name="Изменить" roles="/Policy/Mis/Disability/Case/Edit" /> --%>
        <msh:sideLink guid="sideLinkDelete" key="ALT+DEL" confirm="Удалить?" params="id" action="/entityParentDeleteGoParentView-dis_case" name="Удалить" roles="/Policy/Mis/Disability/Case/Delete" />
      </msh:sideMenu>
      <msh:sideMenu title="Добавить" guid="08906109-6c4d-469c-9c76-cd63fd470464">
        <msh:sideLink params="id" action="/entityParentPrepareCreate-dis_document" name="Документы нетрудоспособности" title="Добавить документ нетрудоспособности" guid="19aec748-a626-4b6e-af52-cdcc98b1ac62" roles="/Policy/Mis/Disability/Case/Document/Create" />
      </msh:sideMenu>
    </msh:ifFormTypeIsView>
  </tiles:put>
</tiles:insert>

