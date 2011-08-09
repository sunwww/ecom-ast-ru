<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://struts.apache.org/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://www.nuzmsh.ru/tags/msh" prefix="msh" %>
<%@ taglib uri="http://www.ecom-ast.ru/tags/ecom" prefix="ecom" %>

<tiles:insert page="/WEB-INF/tiles/mainLayout.jsp" flush="true">

  <tiles:put name="body" type="string">
    <!-- 
    	  - Проба
    	  -->
    <msh:form guid="formHello" action="/entitySaveGoView-smo_smo.do" defaultField="hello">
      <msh:hidden guid="hiddenId" property="id" />
      <msh:hidden guid="hiddenSaveType" property="saveType" />
      <msh:hidden guid="hiddenParent" property="parentId" />
      <msh:panel guid="panel">
        <msh:row guid="row1">
          <msh:textField guid="textFieldHello" property="id" label="Номер" />
        </msh:row>
        <msh:row guid="b5f456eb-b971-441e-9a90-5194a8019c07">
          <msh:textField property="dateStart" label="Дата открытия" guid="507f519c-de41-4b70-b68f-0f695011a73a" />
          <msh:autoComplete viewAction="entityView-smo_smo.do" vocName="vocWorker" property="startWorker" guid="3a3e4d1b-8802-467d-b205-715fb379b018" horizontalFill="true" label="Кем открыт" />
        </msh:row>
        <msh:row guid="bf047dcf-151a-46b2-ae9b-8d437a934472">
          <msh:textField property="dateFinish" label="Дата закрытия" guid="034313c8-dcd5-4574-9dc6-d8184ceed4ef" />
          <msh:autoComplete property="startWorker" label="Кем закрыт" guid="eb8f3d7d-f124-457f-b7fd-456e9a2ff4c3" vocName="vocHello" />
        </msh:row>
        <msh:row guid="2b536f21-7019-4201-b278-0a30e7abf712">
          <msh:autoComplete vocName="vocHello" property="owner" label="Владелец" guid="9d75f750-4a10-46c2-8a22-c1285769febc" />
        </msh:row>
        <msh:row guid="f7ede57d-5dec-438f-afa5-8d5f2ae62470">
          <msh:label property="visitsCount" label="Количество визитов" guid="b326c2f9-7b10-4370-8141-5afe5a31b104" />
          <msh:label property="daysCount" label="Количество дней" guid="631ebea7-cf53-4ef1-8c08-460b24054530" />
        </msh:row>
        <msh:submitCancelButtonsRow guid="submitCancel" colSpan="2" />
      </msh:panel>
    </msh:form>
    <msh:ifFormTypeIsView guid="ifFormTypeIsView" formName="smo_smoForm">
      <msh:section guid="sectionChilds" title="Визиты">
        <ecom:parentEntityListAll guid="parentEntityListChilds" formName="smo_smoForm" attribute="childs" />
        <msh:table guid="tableChilds" name="childs" action="entityParentView-smo_visit.do" idField="id">
          <msh:tableColumn columnName="ИД" property="id" guid="23eed88f-9ea7-4b8f-a955-20ecf89ca86c" />
          <msh:tableColumn columnName="Дата" property="hello" guid="a744754f-5212-4807-910f-e4b252aec108" />
          <msh:tableColumn columnName="Специалист" property="parent" guid="bf4cb2b2-eb35-4e8f-b8cb-4ccccb06d5ac" />
          <msh:tableColumn columnName="ФИО специалиста" property="hello" guid="fe9d6086-8289-4d48-b46f-788c5e983c7a" />
          <msh:tableColumn columnName="Услуги" property="hello" guid="6d386c83-96ae-4912-9ae7-84b3f68b6b67" />
        </msh:table>
      </msh:section>
    </msh:ifFormTypeIsView>
  </tiles:put>
  <tiles:put name="title" type="string">
    <ecom:titleTrail guid="titleTrail-123" mainMenu="Patient" beginForm="smo_smoForm" />
  </tiles:put>
  <tiles:put name="side" type="string">
    <msh:sideMenu guid="sideMenu-123">
      <msh:sideLink guid="sideLinkEdit" key="ALT+2" params="id" action="/entityEdit-smo_smo" name="Изменить" roles="/Policy/IdeMode/Hello/Edit" />
      <msh:sideLink guid="sideLinkNew" key="ALT+N" params="id" action="/entityParentPrepareCreate-smo_smo" name="Добавить потомка" roles="/Policy/IdeMode/Hello/Create" />
      <msh:sideLink guid="sideLinkDelete" key="ALT+DEL" confirm="Удалить?" params="id" action="/entityDelete-smo_smo" name="Удалить" roles="/Policy/IdeMode/Hello/Delete" />
    </msh:sideMenu>
  </tiles:put>
</tiles:insert>

