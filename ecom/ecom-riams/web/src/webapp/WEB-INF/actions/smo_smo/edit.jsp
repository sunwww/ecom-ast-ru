<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://struts.apache.org/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://www.nuzmsh.ru/tags/msh" prefix="msh" %>
<%@ taglib uri="http://www.ecom-ast.ru/tags/ecom" prefix="ecom" %>

<tiles:insert page="/WEB-INF/tiles/mainLayout.jsp" flush="true">

  <tiles:put name="body" type="string">
    <!-- 
    	  - Проба
    	  -->
    <msh:form action="/entitySaveGoView-smo_smo.do" defaultField="hello">
      <msh:hidden property="id" />
      <msh:hidden property="saveType" />
      <msh:hidden property="parentId" />
      <msh:panel>
        <msh:row>
          <msh:textField property="id" label="Номер" />
        </msh:row>
        <msh:row>
          <msh:textField property="dateStart" label="Дата открытия" />
          <msh:autoComplete viewAction="entityView-smo_smo.do" vocName="vocWorker" property="startWorker" horizontalFill="true" label="Кем открыт" />
        </msh:row>
        <msh:row>
          <msh:textField property="dateFinish" label="Дата закрытия" />
          <msh:autoComplete property="startWorker" label="Кем закрыт" vocName="vocHello" />
        </msh:row>
        <msh:row>
          <msh:autoComplete vocName="vocHello" property="owner" label="Владелец" />
        </msh:row>
        <msh:row>
          <msh:label property="visitsCount" label="Количество визитов" />
          <msh:label property="daysCount" label="Количество дней" />
        </msh:row>
        <msh:submitCancelButtonsRow colSpan="2" />
      </msh:panel>
    </msh:form>
    <msh:ifFormTypeIsView formName="smo_smoForm">
      <msh:section title="Визиты">
        <ecom:parentEntityListAll formName="smo_smoForm" attribute="childs" />
        <msh:table name="childs" action="entityParentView-smo_visit.do" idField="id">
          <msh:tableColumn columnName="ИД" property="id" />
          <msh:tableColumn columnName="Дата" property="hello" />
          <msh:tableColumn columnName="Специалист" property="parent" />
          <msh:tableColumn columnName="ФИО специалиста" property="hello" />
          <msh:tableColumn columnName="Услуги" property="hello" />
        </msh:table>
      </msh:section>
    </msh:ifFormTypeIsView>
  </tiles:put>
  <tiles:put name="title" type="string">
    <ecom:titleTrail mainMenu="Patient" beginForm="smo_smoForm" />
  </tiles:put>
  <tiles:put name="side" type="string">
    <msh:sideMenu>
      <msh:sideLink key="ALT+2" params="id" action="/entityEdit-smo_smo" name="Изменить" roles="/Policy/IdeMode/Hello/Edit" />
      <msh:sideLink key="ALT+N" params="id" action="/entityParentPrepareCreate-smo_smo" name="Добавить потомка" roles="/Policy/IdeMode/Hello/Create" />
      <msh:sideLink key="ALT+DEL" confirm="Удалить?" params="id" action="/entityDelete-smo_smo" name="Удалить" roles="/Policy/IdeMode/Hello/Delete" />
    </msh:sideMenu>
  </tiles:put>
</tiles:insert>

