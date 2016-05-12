<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://struts.apache.org/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://www.nuzmsh.ru/tags/msh" prefix="msh" %>
<%@ taglib uri="http://www.ecom-ast.ru/tags/ecom" prefix="ecom" %>

<tiles:insert page="/WEB-INF/tiles/mainLayout.jsp" flush="true">

  <tiles:put name="body" type="string">
    <!-- 
    	  - Проба
    	  -->
    <msh:form guid="formHello" action="/entitySaveGoView-rec_date.do" defaultField="hello">
      <msh:hidden guid="hiddenId" property="id" />
      <msh:hidden guid="hiddenSaveType" property="saveType" />
      <msh:hidden guid="hiddenParent" property="parent" />
      <msh:panel guid="panel">
        <msh:row guid="row1">
          <msh:label fromRequestScope="false" property="hello" viewOnlyField="false" label="Специальность" guid="ddd3db65-db04-46be-8d1b-f4176ba1a41a" horizontalFill="true" size="50" fieldColSpan="3" />
        </msh:row>
        <msh:row guid="b5f456eb-b971-441e-9a90-5194a8019c07">
          <msh:label fromRequestScope="false" property="hello" viewOnlyField="false" label="Врач" guid="962fdb2f-986c-4f6c-a188-b47a1657e1fa" horizontalFill="true" size="50" fieldColSpan="3" />
        </msh:row>
        <msh:submitCancelButtonsRow guid="submitCancel" colSpan="4" />
      </msh:panel>
    </msh:form>
    <msh:ifFormTypeIsView guid="ifFormTypeIsView" formName="rec_dateForm">
      <msh:section guid="sectionChilds" title="Потомки">
        <ecom:parentEntityListAll guid="parentEntityListChilds" formName="rec_dateForm" attribute="childs" />
        <msh:table guid="tableChilds" name="childs" action="entityParentView-rec_date.do" idField="id">
          <msh:tableColumn columnName="Дата" property="id" guid="23eed88f-9ea7-4b8f-a955-20ecf89ca86c" />
          <msh:tableColumn columnName="Первое свободное время" property="hello" guid="a744754f-5212-4807-910f-e4b252aec108" />
          <msh:tableColumn columnName="Выбор" property="parent" guid="bf4cb2b2-eb35-4e8f-b8cb-4ccccb06d5ac" />
        </msh:table>
      </msh:section>
    </msh:ifFormTypeIsView>
  </tiles:put>
  <tiles:put name="title" type="string">
    <ecom:titleTrail guid="titleTrail-123" mainMenu="Patient" beginForm="rec_dateForm" />
  </tiles:put>
  <tiles:put name="side" type="string">
    <msh:sideMenu guid="sideMenu-123" title="Выбрать дату">
      <msh:sideLink guid="sideLinkEdit" key="ALT+2" params="id" action="/entityParentPrepareCreate-rec_recording" name="Записаться" title="Записаться на прием" />
      <msh:sideLink guid="sideLinkNew" key="ALT+N" params="id" action="/entityParentPrepareCreate-rec_time" name="Выбрать другое время" roles="/Policy/IdeMode/Hello/Create" title="Выбрать другое время для записи на прием" />
    </msh:sideMenu>
    <msh:sideMenu title="Добавить" guid="dcf2e072-d44e-47ca-ad16-db0ec61e35c8" />
  </tiles:put>
</tiles:insert>

