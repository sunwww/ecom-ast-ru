<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://struts.apache.org/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://www.nuzmsh.ru/tags/msh" prefix="msh" %>
<%@ taglib uri="http://www.ecom-ast.ru/tags/ecom" prefix="ecom" %>

<tiles:insert page="/WEB-INF/tiles/mainLayout.jsp" flush="true">

  <tiles:put name="body" type="string">
    <!-- 
    	  - Проба
    	  -->
    <msh:form action="/entitySaveGoView-rec_specialist.do" defaultField="hello">
      <msh:hidden property="id" />
      <msh:hidden property="saveType" />
      <msh:hidden property="parent" />
      <msh:panel>
        <msh:row>
          <msh:textField property="hello" label="Специальность" />
        </msh:row>
        <msh:submitCancelButtonsRow colSpan="4" />
      </msh:panel>
    </msh:form>
    <msh:ifFormTypeIsView formName="rec_specialistForm">
      <msh:section title="Выбор врача">
        <ecom:parentEntityListAll formName="rec_dateForm" attribute="childs" />
        <msh:table name="childs" action="entityParentList-rec_date.do" idField="id">
          <msh:tableColumn columnName="Врач" property="hello" />
          <msh:tableColumn columnName="Первая свободная дата" property="hello" />
          <msh:tableColumn columnName="Первое свободное время" property="hello" />
          <msh:tableColumn columnName="Выбор" identificator="false" property="id" />
        </msh:table>
      </msh:section>
    </msh:ifFormTypeIsView>
  </tiles:put>
  <tiles:put name="title" type="string">
    <ecom:titleTrail mainMenu="Patient" beginForm="rec_specialistForm" />
  </tiles:put>
  <tiles:put name="side" type="string">
    <msh:sideMenu title="Проба">
      <msh:sideLink params="id" action="/entityParentPrepareCreate-rec_recording" name="Запись на прием" title="Записаться на прием" />
      <msh:sideLink params="id" action="/entityParentList-rec_date" name="Выбрать другую дату" title="Выбрать другую дату для записи на прием" />
      <msh:sideLink params="id" action="/entityParentList-rec_time" name="Выбрать другое время" title="Выбрать другое время для записи на прием" />
    </msh:sideMenu>
    <msh:sideMenu title="Добавить" />
  </tiles:put>
</tiles:insert>

