<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://struts.apache.org/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://www.nuzmsh.ru/tags/msh" prefix="msh" %>
<%@ taglib uri="http://www.ecom-ast.ru/tags/ecom" prefix="ecom" %>

<tiles:insert page="/WEB-INF/tiles/mainLayout.jsp" flush="true">

  <tiles:put name="body" type="string">
    <!-- 
    	  - Проба
    	  -->
    <msh:form guid="formHello" action="/entitySaveGoView-work_workFunction.do" defaultField="hello">
      <msh:hidden guid="hiddenId" property="id" />
      <msh:hidden guid="hiddenSaveType" property="saveType" />
      <msh:hidden property="lpu" guid="384a5a43-d9f9-464e-a36b-bcf6e2e8c6d4" />
      <msh:hidden property="worker" guid="caf14c3e-853a-4150-aeee-c85053c88d72" />
      <msh:panel guid="panel">
        <msh:row guid="b5f456eb-b971-441e-9a90-5194a8019c07">
          <msh:autoComplete vocName="vocWorkFunction" property="workFunction" guid="3a3e4d1b-8802-467d-b205-715fb379b018" fieldColSpan="3" label="Название функции" size="50" />
        </msh:row>
        <msh:autoComplete viewAction="userView.do" vocName="secUser" property="secUser" label="Вход в систему" guid="8754e635-11ce-4c73-b398-4479988fd60d" fieldColSpan="3" horizontalFill="true" />
        <msh:submitCancelButtonsRow guid="submitCancel" colSpan="4" />
      </msh:panel>
    </msh:form>
    <msh:ifFormTypeIsView guid="ifFormTypeIsView" formName="work_workFunctionForm">
      <msh:section guid="sectionChilds" title="Календарь">
        <ecom:parentEntityListAll guid="parentEntityListChilds" formName="cal_workCalendarForm" attribute="childs" />
        <msh:table guid="tableChilds" name="childs" action="entityParentView-cal_workCalendar.do" idField="id">
          <msh:tableColumn columnName="ИД" property="id" guid="23eed88f-9ea7-4b8f-a955-20ecf89ca86c" />
        </msh:table>
      </msh:section>
    </msh:ifFormTypeIsView>
  </tiles:put>
  <tiles:put name="title" type="string">
    <ecom:titleTrail mainMenu="Lpu" beginForm="work_workFunctionForm" guid="fb43e71c-1ba9-4e61-8632-a6f4a72b461c" />
  </tiles:put>
  <tiles:put name="side" type="string">
    <msh:ifFormTypeIsView guid="ifFormTypeIsView" formName="work_workFunctionForm">
      <msh:sideMenu guid="sideMenu-123" title="Рабочая функция">
        <msh:sideLink guid="sideLinkEdit" key="ALT+2" params="id" action="/entityEdit-work_workFunction" name="Изменить" roles="/Policy/Mis/Worker/WorkFunction/Create" />
        <msh:sideLink guid="sideLinkDelete" key="ALT+DEL" confirm="Удалить?" params="id" action="/entityParentDeleteGoParentView-work_workFunction" name="Удалить" roles="/Policy/Mis/Worker/WorkFunction/Delete" />
      </msh:sideMenu>
      <msh:sideMenu title="Добавить" guid="53f4a828-71f4-4c29-a2e8-fd61ff083187">
        <msh:sideLink roles="/Policy/Mis/Worker/WorkCalendar/Create" key="ALT+3" params="id" action="/entityParentPrepareCreate-cal_workCalendar" name="Календарь" title="Добавить календарь" guid="2f18fed4-7259-479a-97df-ff073fc4569d" />
      </msh:sideMenu>
      <msh:sideMenu title="Показать" guid="5fgyr3df4a828-71f4-4c29-a2e8-fd61ff083187" />
    </msh:ifFormTypeIsView>
  </tiles:put>
</tiles:insert>

