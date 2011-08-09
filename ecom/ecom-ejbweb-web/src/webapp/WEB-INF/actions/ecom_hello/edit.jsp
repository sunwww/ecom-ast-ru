<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://struts.apache.org/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://www.nuzmsh.ru/tags/msh" prefix="msh" %>
<%@ taglib uri="http://www.ecom-ast.ru/tags/ecom" prefix="ecom" %>

<tiles:insert page="/WEB-INF/tiles/mainLayout.jsp" flush="true">

  <tiles:put name="body" type="string">
    <!-- 
    	  - Проба
    	  -->
    <msh:form guid="formHello" action="/entitySaveGoView-ecom_hello.do" defaultField="hello">
      <msh:hidden guid="hiddenId" property="id" />
      <msh:hidden guid="hiddenSaveType" property="saveType" />
      <msh:hidden guid="hiddenParent" property="parent" />
      <msh:panel guid="panel">
        <msh:row guid="row1">
          <msh:textField guid="textFieldHello" property="hello" label="Проба" />
          <msh:textField property="parent" label="Тест2" guid="e71fa83a-c6c2-4221-bb72-77067f879971" />
        </msh:row>
        <msh:row guid="b5f456eb-b971-441e-9a90-5194a8019c07">
          <msh:autoComplete viewAction="entityView-ecom_hello.do" vocName="vocHello" property="link" guid="3a3e4d1b-8802-467d-b205-715fb379b018" horizontalFill="true" fieldColSpan="3" />
        </msh:row>
        <msh:submitCancelButtonsRow guid="submitCancel" colSpan="4" />
      </msh:panel>
    </msh:form>
    <msh:ifFormTypeIsView guid="ifFormTypeIsView" formName="ecom_helloForm">
      <msh:section guid="sectionChilds" title="Потомки">
        <ecom:parentEntityListAll guid="parentEntityListChilds" formName="ecom_helloForm" attribute="childs" />
        <msh:table guid="tableChilds" name="childs" action="entityParentView-ecom_hello.do" idField="id">
          <msh:tableColumn columnName="ИД" property="id" guid="23eed88f-9ea7-4b8f-a955-20ecf89ca86c" />
          <msh:tableColumn columnName="Проба" property="hello" guid="a744754f-5212-4807-910f-e4b252aec108" />
          <msh:tableColumn columnName="Родитель" property="parent" guid="bf4cb2b2-eb35-4e8f-b8cb-4ccccb06d5ac" />
        </msh:table>
      </msh:section>
    </msh:ifFormTypeIsView>
  </tiles:put>
  <tiles:put name="title" type="string">
    <ecom:titleTrail guid="titleTrail-123" mainMenu="Patient" beginForm="ecom_helloForm" />
  </tiles:put>
  <tiles:put name="side" type="string">
    <msh:sideMenu guid="sideMenu-123" title="Проба">
      <msh:sideLink guid="sideLinkEdit" key="ALT+2" params="id" action="/entityEdit-ecom_hello" name="Изменить" roles="/Policy/IdeMode/Hello/Edit" />
      <msh:sideLink guid="sideLinkNew" key="ALT+N" params="id" action="/entityParentPrepareCreate-ecom_hello" name="Потомка" roles="/Policy/IdeMode/Hello/Create" title="Добавить потомка" />
      <msh:sideLink guid="sideLinkDelete" key="ALT+DEL" confirm="Удалить?" params="id" action="/entityDelete-ecom_hello" name="Удалить" roles="/Policy/IdeMode/Hello/Delete" />
    </msh:sideMenu>
    <msh:sideMenu title="Добавить" guid="dcf2e072-d44e-47ca-ad16-db0ec61e35c8" />
  </tiles:put>
</tiles:insert>

