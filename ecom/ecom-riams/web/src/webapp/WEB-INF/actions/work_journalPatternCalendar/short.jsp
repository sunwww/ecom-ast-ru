<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://struts.apache.org/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://www.nuzmsh.ru/tags/msh" prefix="msh" %>
<%@ taglib uri="http://www.ecom-ast.ru/tags/ecom" prefix="ecom" %>

<tiles:insert page="/WEB-INF/tiles/mainShortLayout.jsp" flush="true">

  <tiles:put name="body" type="string">
    <!-- 
    	  - Журнал шаблона календаря
    	  -->
    <msh:form action="/entityParentSaveGoParentView-work_journalPatternCalendar.do" defaultField="hello">
      <msh:hidden property="id" />
      <msh:hidden property="saveType" />
      <msh:hidden property="workCalendar" />
      <msh:panel>
        <msh:row>
          <msh:autoComplete  vocName="vocWorkBusy" property="workBusy" horizontalFill="true" fieldColSpan="3" />
        </msh:row>
        <msh:row>
          <msh:autoComplete  shortViewAction="entityShortView-cal_patternBySpec.do" parentId="work_journalPatternCalendarForm.workCalendar" vocName="workCalendarPattern" property="pattern" horizontalFill="true" fieldColSpan="3" viewAction="entitySubclassView-cal_pattern.do" />
        </msh:row>
        <msh:row>
          <msh:checkBox property="noActive" label="Недействует" />
        </msh:row>
        <msh:row>
          <msh:textField property="dateFrom" label="Действует с" />
          <msh:textField property="dateTo" label="по" />
        </msh:row>
        <msh:submitCancelButtonsRow colSpan="4" />
      </msh:panel>
    </msh:form>
  </tiles:put>
  <tiles:put name="title" type="string">
    <ecom:titleTrail mainMenu="Lpu" beginForm="work_journalPatternCalendarForm" />
  </tiles:put>
  <tiles:put name="side" type="string">
    <msh:ifFormTypeIsView formName="work_journalPatternCalendarForm">
      <msh:sideMenu title="Журнал шаблона календаря">
        <msh:sideLink key="ALT+2" params="id" action="/entityParentEdit-work_journalPatternCalendar" name="Изменить" roles="/Policy/Mis/Worker/WorkCalendar/JournalPattern/Edit" />
        <msh:sideLink key="ALT+DEL" confirm="Удалить?" params="id" action="/entityParentDeleteGoParentView-work_journalPatternCalendar" name="Удалить" roles="/Policy/Mis/Worker/WorkCalendar/JournalPattern/Delete" />
      </msh:sideMenu>
    </msh:ifFormTypeIsView>
  </tiles:put>
</tiles:insert>

