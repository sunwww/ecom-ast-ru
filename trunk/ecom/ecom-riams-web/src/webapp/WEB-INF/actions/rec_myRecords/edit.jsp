<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://struts.apache.org/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://www.nuzmsh.ru/tags/msh" prefix="msh" %>
<%@ taglib uri="http://www.ecom-ast.ru/tags/ecom" prefix="ecom" %>

<tiles:insert page="/WEB-INF/tiles/mainLayout.jsp" flush="true">

  <tiles:put name="body" type="string">
    <!-- 
    	  - Проба
    	  -->
    <msh:form guid="formHello" action="/entitySaveGoView-rec_myRecords.do" defaultField="hello">
      <msh:hidden guid="hiddenId" property="id" />
      <msh:hidden guid="hiddenSaveType" property="saveType" />
      <msh:hidden guid="hiddenParent" property="parent" />
      <msh:panel guid="panel">
        <msh:row guid="row1">
          <msh:textField guid="textFieldHello" property="hello" label="Фамилия" />
          <msh:textField property="parent" label="Имя" guid="e71fa83a-c6c2-4221-bb72-77067f879971" />
          <msh:textField passwordEnabled="false" property="hello" viewOnlyField="false" label="Отчество" guid="09ec0363-efce-441c-a0e3-e299bd9902d8" horizontalFill="false" />
        </msh:row>
        <msh:submitCancelButtonsRow guid="submitCancel" colSpan="4" />
      </msh:panel>
    </msh:form>
    <msh:ifFormTypeIsView guid="ifFormTypeIsView" formName="rec_myRecordsForm">
      <msh:section guid="sectionChilds" title="Мои записи">
        <ecom:parentEntityListAll guid="parentEntityListChilds" formName="rec_myRecordsForm" attribute="childs" />
        <msh:table guid="tableChilds" name="childs" action="entityParentView-rec_myRecords.do" idField="id">
          <msh:tableColumn columnName="ЛПУ" property="hello" guid="23eed88f-9ea7-4b8f-a955-20ecf89ca86c" />
          <msh:tableColumn columnName="Специальность" property="hello" guid="a744754f-5212-4807-910f-e4b252aec108" />
          <msh:tableColumn columnName="Врач" property="hello" guid="bf4cb2b2-eb35-4e8f-b8cb-4ccccb06d5ac" />
          <msh:tableColumn columnName="Дата" identificator="false" property="id" guid="d03b0989-f5ff-4f6c-8094-7b7e4483ef44" />
          <msh:tableColumn columnName="Время" identificator="false" property="id" guid="f480325d-059a-4b02-84da-60d7b7ae848b" />
          <msh:tableColumn columnName="Выбор" identificator="false" property="id" guid="c01e9ae9-1ddf-45a7-ae2e-5a3150795c55" />
        </msh:table>
      </msh:section>
    </msh:ifFormTypeIsView>
  </tiles:put>
  <tiles:put name="title" type="string">
    <ecom:titleTrail guid="titleTrail-123" mainMenu="Patient" beginForm="rec_myRecordsForm" />
  </tiles:put>
  <tiles:put name="side" type="string">
    <msh:sideMenu guid="sideMenu-123" title="Проба">
      <msh:sideLink guid="sideLinkEdit" key="ALT+2" params="id" action="/entityEdit-rec_myRecords" name="Изменить" roles="/Policy/IdeMode/Hello/Edit" />
      <msh:sideLink guid="sideLinkDelete" key="ALT+DEL" confirm="Удалить?" params="id" action="/entityDelete-rec_myRecords" name="Удалить" roles="/Policy/IdeMode/Hello/Delete" />
    </msh:sideMenu>
    <msh:sideMenu title="Добавить" guid="dcf2e072-d44e-47ca-ad16-db0ec61e35c8">
      <msh:sideLink params="id" action="/entityParentPrepareCreate-rec_specialist" name="Новая запись" title="Добавить запись" guid="ab51eb25-69f5-4a38-aacd-930bcf4fba98" />
    </msh:sideMenu>
  </tiles:put>
</tiles:insert>

