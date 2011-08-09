<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://struts.apache.org/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://www.nuzmsh.ru/tags/msh" prefix="msh" %>
<%@ taglib uri="http://www.ecom-ast.ru/tags/ecom" prefix="ecom" %>

<tiles:insert page="/WEB-INF/tiles/mainLayout.jsp" flush="true">

  <tiles:put name="body" type="string">
    <!-- 
    	  - Проба
    	  -->
    <msh:form guid="formHello" action="/entityParentSaveGoParentView-mis_languageSkill.do" defaultField="languageName">
      <msh:hidden guid="hiddenId" property="id" />
      <msh:hidden guid="hiddenSaveType" property="saveType" />
      <msh:hidden guid="hiddenParent" property="person" />
      <msh:panel guid="panel" colsWidth="6">
        <msh:row guid="b5f456eb-b971-441e-9a90-5194a8019c07">
          <msh:autoComplete vocName="vocLanguage" property="language" guid="3a3e4d1b-8802-467d-b205-715fb379b018" horizontalFill="true" label="Язык" size="50" />
        </msh:row>
        <msh:row guid="b594dd6e-e66a-43bb-b872-95a99cc406d2">
          <msh:autoComplete vocName="vocLanguageSkillLevel" property="skillLevel" label="Уровень знания языка" guid="2150f6c2-4fdd-4608-a0e9-850861fee97e" horizontalFill="true" />
        </msh:row>
        <msh:submitCancelButtonsRow guid="submitCancel" colSpan="4" />
      </msh:panel>
    </msh:form>
    <!--
    <msh:ifFormTypeIsView guid="ifFormTypeIsView" formName="mis_languageSkillForm">
      <msh:section guid="sectionChilds" title="Потомки">
        <ecom:parentEntityListAll guid="parentEntityListChilds" formName="mis_languageSkillForm" attribute="childs" />
        <msh:table guid="tableChilds" name="childs" action="entityParentView-mis_languageSkill.do" idField="id">
          <msh:tableColumn columnName="ИД" property="id" guid="23eed88f-9ea7-4b8f-a955-20ecf89ca86c" />
          <msh:tableColumn columnName="Проба" property="hello" guid="a744754f-5212-4807-910f-e4b252aec108" />
          <msh:tableColumn columnName="Родитель" property="parent" guid="bf4cb2b2-eb35-4e8f-b8cb-4ccccb06d5ac" />
        </msh:table>
      </msh:section>
    </msh:ifFormTypeIsView>
  -->
  </tiles:put>
  <tiles:put name="title" type="string">
    <ecom:titleTrail guid="titleTrail-123" mainMenu="Patient" beginForm="mis_languageSkillForm" />
  </tiles:put>
  <tiles:put name="side" type="string">
    <msh:ifFormTypeIsView formName="mis_languageSkillForm" insideJavascript="false" guid="198f34e1-fbbe-426f-a228-3f58d8435dcf">
      <msh:sideMenu guid="sideMenu-123">
        <msh:sideLink guid="sideLinkEdit" key="ALT+2" params="id" action="/entityEdit-mis_languageSkill" name="Изменить" roles="/Policy/Mis/Worker/LaguageSkill/Edit" />
        <msh:sideLink guid="sideLinkDelete" key="ALT+DEL" confirm="Удалить?" params="id" action="/entityParentDeleteGoParentView-mis_languageSkill" name="Удалить" roles="/Policy/Mis/Worker/LaguageSkill/Delete" />
      </msh:sideMenu>
    </msh:ifFormTypeIsView>
  </tiles:put>
</tiles:insert>

