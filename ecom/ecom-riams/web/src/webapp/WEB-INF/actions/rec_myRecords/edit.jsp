<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://struts.apache.org/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://www.nuzmsh.ru/tags/msh" prefix="msh" %>
<%@ taglib uri="http://www.ecom-ast.ru/tags/ecom" prefix="ecom" %>

<tiles:insert page="/WEB-INF/tiles/mainLayout.jsp" flush="true">

  <tiles:put name="body" type="string">
    <!-- 
    	  - Проба
    	  -->
    <msh:form action="/entitySaveGoView-rec_myRecords.do" defaultField="hello">
      <msh:hidden property="id" />
      <msh:hidden property="saveType" />
      <msh:hidden property="parent" />
      <msh:panel>
        <msh:row>
          <msh:textField property="hello" label="Фамилия" />
          <msh:textField property="parent" label="Имя" />
          <msh:textField passwordEnabled="false" property="hello" viewOnlyField="false" label="Отчество" horizontalFill="false" />
        </msh:row>
        <msh:submitCancelButtonsRow colSpan="4" />
      </msh:panel>
    </msh:form>
    <msh:ifFormTypeIsView formName="rec_myRecordsForm">
      <msh:section title="Мои записи">
        <ecom:parentEntityListAll formName="rec_myRecordsForm" attribute="childs" />
        <msh:table name="childs" action="entityParentView-rec_myRecords.do" idField="id">
          <msh:tableColumn columnName="ЛПУ" property="hello" />
          <msh:tableColumn columnName="Специальность" property="hello" />
          <msh:tableColumn columnName="Врач" property="hello" />
          <msh:tableColumn columnName="Дата" identificator="false" property="id" />
          <msh:tableColumn columnName="Время" identificator="false" property="id" />
          <msh:tableColumn columnName="Выбор" identificator="false" property="id" />
        </msh:table>
      </msh:section>
    </msh:ifFormTypeIsView>
  </tiles:put>
  <tiles:put name="title" type="string">
    <ecom:titleTrail mainMenu="Patient" beginForm="rec_myRecordsForm" />
  </tiles:put>
  <tiles:put name="side" type="string">
    <msh:sideMenu title="Проба">
      <msh:sideLink key="ALT+2" params="id" action="/entityEdit-rec_myRecords" name="Изменить" roles="/Policy/IdeMode/Hello/Edit" />
      <msh:sideLink key="ALT+DEL" confirm="Удалить?" params="id" action="/entityDelete-rec_myRecords" name="Удалить" roles="/Policy/IdeMode/Hello/Delete" />
    </msh:sideMenu>
    <msh:sideMenu title="Добавить">
      <msh:sideLink params="id" action="/entityParentPrepareCreate-rec_specialist" name="Новая запись" title="Добавить запись" />
    </msh:sideMenu>
  </tiles:put>
</tiles:insert>

