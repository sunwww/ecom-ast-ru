<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://struts.apache.org/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://www.nuzmsh.ru/tags/msh" prefix="msh" %>
<%@ taglib uri="http://www.ecom-ast.ru/tags/ecom" prefix="ecom" %>

<tiles:insert page="/WEB-INF/tiles/mainLayout.jsp" flush="true">

  <tiles:put name="body" type="string">
    <!-- 
    	  - Проба
    	  -->
    <msh:form action="/entitySaveGoView-rec_time.do" defaultField="hello">
      <msh:hidden property="id" />
      <msh:hidden property="saveType" />
      <msh:hidden property="parent" />
      <msh:panel>
        <msh:row>
          <msh:label fromRequestScope="false" property="hello" viewOnlyField="false" label="Специальность" horizontalFill="false" />
          <msh:label fromRequestScope="false" property="hello" viewOnlyField="false" label="Врач" horizontalFill="false" />
        </msh:row>
        <msh:row>
          <msh:label fromRequestScope="false" property="hello" viewOnlyField="false" label="Дата" horizontalFill="false" />
        </msh:row>
        <msh:submitCancelButtonsRow colSpan="4" />
      </msh:panel>
    </msh:form>
    <msh:ifFormTypeIsView formName="rec_timeForm">
      <msh:section title="Выбор времени">
        <ecom:parentEntityListAll formName="rec_timeForm" attribute="childs" />
        <msh:table name="childs" action="entityParentView-rec_time.do" idField="id">
          <msh:tableColumn columnName="Время" property="id" />
          <msh:tableColumn columnName="Выбор" property="hello" />
        </msh:table>
      </msh:section>
    </msh:ifFormTypeIsView>
  </tiles:put>
  <tiles:put name="title" type="string">
    <ecom:titleTrail mainMenu="Patient" beginForm="rec_timeForm" />
  </tiles:put>
  <tiles:put name="side" type="string">
    <msh:sideMenu title="Проба">
      <msh:sideLink params="id" action="/entityPrepareCreate-rec_recording" name="Записаться на прием" title="Записаться на прием" />
      <msh:sideLink key="ALT+2" params="id" action="/entityPrepareCreate-rec_date" name="Выбрать другую дату" title="Выбрать другую дату для записи на прием" />
    </msh:sideMenu>
    <msh:sideMenu title="Добавить" />
  </tiles:put>
</tiles:insert>

