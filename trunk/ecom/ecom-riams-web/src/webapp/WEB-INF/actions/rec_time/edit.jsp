<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://struts.apache.org/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://www.nuzmsh.ru/tags/msh" prefix="msh" %>
<%@ taglib uri="http://www.ecom-ast.ru/tags/ecom" prefix="ecom" %>

<tiles:insert page="/WEB-INF/tiles/mainLayout.jsp" flush="true">

  <tiles:put name="body" type="string">
    <!-- 
    	  - Проба
    	  -->
    <msh:form guid="formHello" action="/entitySaveGoView-rec_time.do" defaultField="hello">
      <msh:hidden guid="hiddenId" property="id" />
      <msh:hidden guid="hiddenSaveType" property="saveType" />
      <msh:hidden guid="hiddenParent" property="parent" />
      <msh:panel guid="panel">
        <msh:row guid="row1">
          <msh:label fromRequestScope="false" property="hello" viewOnlyField="false" label="Специальность" guid="e811e72f-beed-4410-ab6d-ed073b84ba61" horizontalFill="false" />
          <msh:label fromRequestScope="false" property="hello" viewOnlyField="false" label="Врач" guid="b9cd26ee-fe09-45f3-a0a4-f5cb6a1124ee" horizontalFill="false" />
        </msh:row>
        <msh:row guid="b5f456eb-b971-441e-9a90-5194a8019c07">
          <msh:label fromRequestScope="false" property="hello" viewOnlyField="false" label="Дата" guid="aae61010-cd16-46f5-bcf0-34d8ad0a966a" horizontalFill="false" />
        </msh:row>
        <msh:submitCancelButtonsRow guid="submitCancel" colSpan="4" />
      </msh:panel>
    </msh:form>
    <msh:ifFormTypeIsView guid="ifFormTypeIsView" formName="rec_timeForm">
      <msh:section guid="sectionChilds" title="Выбор времени">
        <ecom:parentEntityListAll guid="parentEntityListChilds" formName="rec_timeForm" attribute="childs" />
        <msh:table guid="tableChilds" name="childs" action="entityParentView-rec_time.do" idField="id">
          <msh:tableColumn columnName="Время" property="id" guid="23eed88f-9ea7-4b8f-a955-20ecf89ca86c" />
          <msh:tableColumn columnName="Выбор" property="hello" guid="a744754f-5212-4807-910f-e4b252aec108" />
        </msh:table>
      </msh:section>
    </msh:ifFormTypeIsView>
  </tiles:put>
  <tiles:put name="title" type="string">
    <ecom:titleTrail guid="titleTrail-123" mainMenu="Patient" beginForm="rec_timeForm" />
  </tiles:put>
  <tiles:put name="side" type="string">
    <msh:sideMenu guid="sideMenu-123" title="Проба">
      <msh:sideLink params="id" action="/entityPrepareCreate-rec_recording" name="Записаться на прием" title="Записаться на прием" guid="9d5baad1-6491-4e6a-b482-3ddfa91b708c" />
      <msh:sideLink guid="sideLinkEdit" key="ALT+2" params="id" action="/entityPrepareCreate-rec_date" name="Выбрать другую дату" title="Выбрать другую дату для записи на прием" />
    </msh:sideMenu>
    <msh:sideMenu title="Добавить" guid="dcf2e072-d44e-47ca-ad16-db0ec61e35c8" />
  </tiles:put>
</tiles:insert>

