<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://struts.apache.org/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://www.nuzmsh.ru/tags/msh" prefix="msh" %>
<%@ taglib uri="http://www.ecom-ast.ru/tags/ecom" prefix="ecom" %>

<tiles:insert page="/WEB-INF/tiles/mainLayout.jsp" flush="true">

  <tiles:put name="body" type="string">
    <!-- 
    	  - Проба
    	  -->
    <msh:form action="/entitySaveGoView-mis_workerNew.do" defaultField="hello">
      <msh:hidden property="id" />
      <msh:hidden property="saveType" />
      <msh:hidden property="parent" />
      <msh:panel>
        <msh:row>
          <msh:textField property="hello" label="Имя входа в систему" size="50" />
        </msh:row>
        <msh:submitCancelButtonsRow colSpan="4" />
      </msh:panel>
    </msh:form>
    <msh:ifFormTypeIsView formName="mis_workerNewForm">
      <msh:section title="Рабочие функции">
        <ecom:parentEntityListAll formName="ecom_helloForm" attribute="childs" />
        <msh:table idField="id" name="childs" action="entityParentView-mis_workerNew.do">
          <msh:tableColumn columnName="Функции" identificator="false" property="hello" />
        </msh:table>
      </msh:section>
    </msh:ifFormTypeIsView>
  </tiles:put>
  <tiles:put name="title" type="string">
    <ecom:titleTrail mainMenu="Patient" beginForm="mis_workerNewForm" />
  </tiles:put>
  <tiles:put name="side" type="string">
    <msh:sideMenu title="Проба">
      <msh:sideLink key="ALT+2" params="id" action="/entityEdit-mis_workerNew" name="Изменить" roles="/Policy/IdeMode/Hello/Edit" />
      <msh:sideLink key="ALT+N" params="id" action="/entityParentPrepareCreate-mis_workerNew" name="Потомка" roles="/Policy/IdeMode/Hello/Create" title="Добавить потомка" />
      <msh:sideLink key="ALT+DEL" confirm="Удалить?" params="id" action="/entityDelete-mis_workerNew" name="Удалить" roles="/Policy/IdeMode/Hello/Delete" />
    </msh:sideMenu>
    <msh:sideMenu title="Добавить" />
  </tiles:put>
</tiles:insert>

