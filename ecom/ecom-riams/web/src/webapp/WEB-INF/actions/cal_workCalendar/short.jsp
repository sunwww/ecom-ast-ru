<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://struts.apache.org/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://www.nuzmsh.ru/tags/msh" prefix="msh" %>
<%@ taglib uri="http://www.ecom-ast.ru/tags/ecom" prefix="ecom" %>

<tiles:insert page="/WEB-INF/tiles/mainShortLayout.jsp" flush="true">

  <tiles:put name="body" type="string">
    <!-- 
    	  - 
    	  -->
    <msh:form action="/entityParentSaveGoSubclassView-cal_workCalendar.do" defaultField="hello">
      <msh:hidden property="id" />
      <msh:hidden property="saveType" />
      <msh:hidden property="workFunction" />
      <msh:panel>
        <msh:row />
        <msh:submitCancelButtonsRow colSpan="4" />
      </msh:panel>
    </msh:form>
    <msh:ifFormTypeIsView formName="cal_workCalendarForm">
      <msh:ifInRole roles="/Policy/Mis/Worker/WorkCalendar/JournalPattern/View">
        <msh:section title="Журналы шаблонов">
          <ecom:parentEntityListAll formName="work_journalPatternCalendarForm" attribute="journals" />
          <msh:table deleteUrl="entityParentDeleteGoParentView-work_journalPatternCalendar.do" editUrl="entityParentEdit-work_journalPatternCalendar.do" viewUrl="entityShortView-work_journalPatternCalendar.do" name="journals" action="entityParentView-work_journalPatternCalendar.do" idField="id">
            <msh:tableColumn columnName="ИД" property="id" />
            <msh:tableColumn columnName="Информация о шаблоне" property="info" />
            <msh:tableColumn columnName="Отключен" property="noActive" />
            <msh:tableColumn columnName="Действует с" property="dateFrom" />
            <msh:tableColumn columnName="Действует по" property="dateTo" />
          </msh:table>
        </msh:section>
      </msh:ifInRole>
    </msh:ifFormTypeIsView>
  </tiles:put>
  <tiles:put name="title" type="string">
    <ecom:titleTrail mainMenu="Lpu" beginForm="cal_workCalendarForm" />
  </tiles:put>
  <tiles:put name="side" type="string">
    <msh:sideMenu title="Рабочий календаря">
      <msh:sideLink key="ALT+DEL" confirm="Удалить?" params="id" action="/entityParentDeleteGoSubclassView-cal_workCalendar" name="Удалить" roles="/Policy/Mis/Worker/WorkCalendar/Delete" />
    </msh:sideMenu>
    <msh:sideMenu title="Добавить">
      <msh:sideLink params="id" action="/entityParentPrepareCreate-work_journalPatternCalendar" name="Журнал шаблона" title="Добавить журнал шаблона" roles="/Policy/Mis/Worker/WorkCalendar/JournalPattern/Create" />
    </msh:sideMenu>
    <msh:sideMenu title="Показать все">
      <msh:sideLink params="id" action="/entityParentList-cal_workCalendarDay" name="Рабочие дни" title="Добавить шаблон" roles="/Policy/Mis/Worker/WorkCalendar/View" />
    </msh:sideMenu>
  </tiles:put>
</tiles:insert>

