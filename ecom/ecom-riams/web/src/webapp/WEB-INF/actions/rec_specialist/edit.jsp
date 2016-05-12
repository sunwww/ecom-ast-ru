<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://struts.apache.org/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://www.nuzmsh.ru/tags/msh" prefix="msh" %>
<%@ taglib uri="http://www.ecom-ast.ru/tags/ecom" prefix="ecom" %>

<tiles:insert page="/WEB-INF/tiles/mainLayout.jsp" flush="true">

  <tiles:put name="body" type="string">
    <!-- 
    	  - Проба
    	  -->
    <msh:form guid="formHello" action="/entitySaveGoView-rec_specialist.do" defaultField="hello">
      <msh:hidden guid="hiddenId" property="id" />
      <msh:hidden guid="hiddenSaveType" property="saveType" />
      <msh:hidden guid="hiddenParent" property="parent" />
      <msh:panel guid="panel">
        <msh:row guid="row1">
          <msh:textField guid="textFieldHello" property="hello" label="Специальность" />
        </msh:row>
        <msh:submitCancelButtonsRow guid="submitCancel" colSpan="4" />
      </msh:panel>
    </msh:form>
    <msh:ifFormTypeIsView guid="ifFormTypeIsView" formName="rec_specialistForm">
      <msh:section guid="sectionChilds" title="Выбор врача">
        <ecom:parentEntityListAll guid="parentEntityListChilds" formName="rec_dateForm" attribute="childs" />
        <msh:table guid="tableChilds" name="childs" action="entityParentList-rec_date.do" idField="id">
          <msh:tableColumn columnName="Врач" property="hello" guid="23eed88f-9ea7-4b8f-a955-20ecf89ca86c" />
          <msh:tableColumn columnName="Первая свободная дата" property="hello" guid="a744754f-5212-4807-910f-e4b252aec108" />
          <msh:tableColumn columnName="Первое свободное время" property="hello" guid="bf4cb2b2-eb35-4e8f-b8cb-4ccccb06d5ac" />
          <msh:tableColumn columnName="Выбор" identificator="false" property="id" guid="61841aa8-ec35-488b-a13f-68c90d6293f1" />
        </msh:table>
      </msh:section>
    </msh:ifFormTypeIsView>
  </tiles:put>
  <tiles:put name="title" type="string">
    <ecom:titleTrail guid="titleTrail-123" mainMenu="Patient" beginForm="rec_specialistForm" />
  </tiles:put>
  <tiles:put name="side" type="string">
    <msh:sideMenu guid="sideMenu-123" title="Проба">
      <msh:sideLink params="id" action="/entityParentPrepareCreate-rec_recording" name="Запись на прием" title="Записаться на прием" guid="64c1307a-a7e6-4396-9651-aee2fcca3a6e" />
      <msh:sideLink params="id" action="/entityParentList-rec_date" name="Выбрать другую дату" title="Выбрать другую дату для записи на прием" guid="e63f889c-0714-4b98-81f0-201eb845943d" />
      <msh:sideLink params="id" action="/entityParentList-rec_time" name="Выбрать другое время" title="Выбрать другое время для записи на прием" guid="b993ce45-d4fa-40d9-b60b-e372b107ff8c" />
    </msh:sideMenu>
    <msh:sideMenu title="Добавить" guid="dcf2e072-d44e-47ca-ad16-db0ec61e35c8" />
  </tiles:put>
</tiles:insert>

