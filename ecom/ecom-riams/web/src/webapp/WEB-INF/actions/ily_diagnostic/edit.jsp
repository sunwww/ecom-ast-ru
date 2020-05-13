<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://struts.apache.org/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://www.nuzmsh.ru/tags/msh" prefix="msh" %>
<%@ taglib uri="http://www.ecom-ast.ru/tags/ecom" prefix="ecom" %>

<tiles:insert page="/WEB-INF/tiles/mainLayout.jsp" flush="true">

  <tiles:put name="body" type="string">
    <!-- 
    	  - Проба
    	  -->
    <msh:form action="/entitySaveGoView-ily_diagnostic.do" defaultField="hello">
      <msh:hidden property="id" />
      <msh:hidden property="saveType" />
      <msh:hidden property="parent" />
      <msh:panel>
        <msh:row>
          <msh:textField property="hello" label="Код" />
        </msh:row>
        <msh:row>
          <msh:autoComplete viewAction="entityView-ily_diagnostic.do" vocName="vocHello" property="link" label="Услуга" />
        </msh:row>
        <msh:row />
        <msh:submitCancelButtonsRow colSpan="2" />
      </msh:panel>
    </msh:form>
    <msh:ifFormTypeIsView formName="ily_diagnosticForm">
      <msh:section title="Потомки">
        <ecom:parentEntityListAll formName="ily_diagnosticForm" attribute="childs" />
        <msh:table name="childs" action="entityParentView-ily_diagnostic.do" idField="id">
          <msh:tableColumn columnName="ИД" property="id" />
          <msh:tableColumn columnName="Проба" property="hello" />
          <msh:tableColumn columnName="Родитель" property="parent" />
        </msh:table>
      </msh:section>
    </msh:ifFormTypeIsView>
  </tiles:put>
  <tiles:put name="title" type="string">
    <ecom:titleTrail mainMenu="Patient" beginForm="ily_diagnosticForm" title="Диагностика" />
  </tiles:put>
  <tiles:put name="side" type="string">
    <msh:sideMenu>
      <msh:sideLink key="ALT+2" params="id" action="/entityEdit-ily_diagnostic" name="Изменить" roles="/Policy/IdeMode/Hello/Edit" />
      <msh:sideLink key="ALT+N" params="id" action="/entityParentPrepareCreate-ily_diagnostic" name="Добавить потомка" roles="/Policy/IdeMode/Hello/Create" />
      <msh:sideLink key="ALT+DEL" confirm="Удалить?" params="id" action="/entityDelete-ily_diagnostic" name="Удалить" roles="/Policy/IdeMode/Hello/Delete" />
    </msh:sideMenu>
  </tiles:put>
</tiles:insert>

