<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://struts.apache.org/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://www.nuzmsh.ru/tags/msh" prefix="msh" %>
<%@ taglib uri="http://www.ecom-ast.ru/tags/ecom" prefix="ecom" %>

<tiles:insert page="/WEB-INF/tiles/mainLayout.jsp" flush="true">

  <tiles:put name="body" type="string">
    <!-- 
    	  - Проба
    	  -->
    <msh:form action="/entitySaveGoView-rec_date.do" defaultField="hello">
      <msh:hidden property="id" />
      <msh:hidden property="saveType" />
      <msh:hidden property="parent" />
      <msh:panel>
        <msh:row>
          <msh:label fromRequestScope="false" property="hello" viewOnlyField="false" label="Специальность" horizontalFill="true" size="50" fieldColSpan="3" />
        </msh:row>
        <msh:row>
          <msh:label fromRequestScope="false" property="hello" viewOnlyField="false" label="Врач" horizontalFill="true" size="50" fieldColSpan="3" />
        </msh:row>
        <msh:submitCancelButtonsRow colSpan="4" />
      </msh:panel>
    </msh:form>
    <msh:ifFormTypeIsView formName="rec_dateForm">
      <msh:section title="Потомки">
        <ecom:parentEntityListAll formName="rec_dateForm" attribute="childs" />
        <msh:table name="childs" action="entityParentView-rec_date.do" idField="id">
          <msh:tableColumn columnName="Дата" property="id" />
          <msh:tableColumn columnName="Первое свободное время" property="hello" />
          <msh:tableColumn columnName="Выбор" property="parent" />
        </msh:table>
      </msh:section>
    </msh:ifFormTypeIsView>
  </tiles:put>
  <tiles:put name="title" type="string">
    <ecom:titleTrail mainMenu="Patient" beginForm="rec_dateForm" />
  </tiles:put>
  <tiles:put name="side" type="string">
    <msh:sideMenu title="Выбрать дату">
      <msh:sideLink key="ALT+2" params="id" action="/entityParentPrepareCreate-rec_recording" name="Записаться" title="Записаться на прием" />
      <msh:sideLink key="ALT+N" params="id" action="/entityParentPrepareCreate-rec_time" name="Выбрать другое время" roles="/Policy/IdeMode/Hello/Create" title="Выбрать другое время для записи на прием" />
    </msh:sideMenu>
    <msh:sideMenu title="Добавить" />
  </tiles:put>
</tiles:insert>

