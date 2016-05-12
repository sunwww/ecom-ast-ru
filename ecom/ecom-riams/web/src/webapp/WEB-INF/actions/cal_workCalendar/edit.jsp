<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://struts.apache.org/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://www.nuzmsh.ru/tags/msh" prefix="msh" %>
<%@ taglib uri="http://www.ecom-ast.ru/tags/ecom" prefix="ecom" %>

<tiles:insert page="/WEB-INF/tiles/mainLayout.jsp" flush="true">

  <tiles:put name="body" type="string">
    <!-- 
    	  - 
    	  -->
    <msh:form guid="formHello" action="/entityParentSaveGoSubclassView-cal_workCalendar.do" defaultField="hello">
      <msh:hidden guid="hiddenId" property="id" />
      <msh:hidden guid="hiddenSaveType" property="saveType" />
      <msh:hidden property="workFunction" guid="105b60c0-2387-453d-a99e-837e4efceeac" />
      <msh:panel guid="panel">
        <msh:row guid="b5f456eb-b971-441e-9a90-5194a8019c07" >
        	<msh:checkBox property="autoGenerate" label="Автоматическая генерация"/>
        </msh:row>
        <msh:row>
        	<msh:textField property="afterDaysGenerate" label="Через сколько дней генерировать"/>
        </msh:row>
        <msh:submitCancelButtonsRow guid="submitCancel" colSpan="4" />
      </msh:panel>
    </msh:form>
    <msh:ifFormTypeIsView guid="ifFormTypeIsView" formName="cal_workCalendarForm">
      <msh:ifInRole roles="/Policy/Mis/Worker/WorkCalendar/JournalPattern/View" guid="a4054052-1d42-4d0b-aef1-90c19bd53973">
        <msh:section guid="sectionChilds" title="Журналы шаблонов">
          <ecom:parentEntityListAll guid="parentEntityListChilds" formName="work_journalPatternCalendarForm" attribute="journals" />
          <msh:table deleteUrl="entityParentDeleteGoParentView-work_journalPatternCalendar.do" editUrl="entityParentEdit-work_journalPatternCalendar.do" viewUrl="entityShortView-work_journalPatternCalendar.do" guid="tableChilds" name="journals" action="entityParentView-work_journalPatternCalendar.do" idField="id">
            <msh:tableColumn columnName="ИД" property="id" guid="23eed88f-9ea7-4b8f-a955-20ecf89ca86c" />
            <msh:tableColumn columnName="Информация о шаблоне" property="info" guid="a744754f-5212-4807-910f-e4b2333" />
            <msh:tableColumn columnName="Отключен" property="noActive" guid="a744754f-5212-4807-910f-e1212" />
            <msh:tableColumn columnName="Действует с" property="dateFrom" guid="a744754f-5212-4807-910f-333aec108" />
            <msh:tableColumn columnName="Действует по" property="dateTo" guid="a744754f-5212-4807-910f-333aec19" />
          </msh:table>
        </msh:section>
      </msh:ifInRole>
    </msh:ifFormTypeIsView>
  </tiles:put>
  <tiles:put name="title" type="string">
    <ecom:titleTrail mainMenu="Lpu" beginForm="cal_workCalendarForm" guid="70377d58-344a-4359-b370-166d267a2107" />
  </tiles:put>
  <tiles:put name="side" type="string">
    <msh:sideMenu title="Рабочий календаря" guid="115d9e5c-3b7b-45f8-ae5e-89a86e647c65">
      <msh:sideLink params="id" action="/entityParentEdit-cal_workCalendar" name="Изменить" roles="/Policy/Mis/Worker/WorkCalendar/Edit" guid="22cac091-1077-41a3-a5ee-83f60227caac" />
      <msh:sideLink key="ALT+DEL" confirm="Удалить?" params="id" action="/entityParentDeleteGoSubclassView-cal_workCalendar" name="Удалить" roles="/Policy/Mis/Worker/WorkCalendar/Delete" guid="22cac091-1077-41a3-a5ee-83f60227caac" />
    </msh:sideMenu>
    <msh:sideMenu title="Добавить" guid="370826ac-2b89-4b6c-a936-636b236a133c">
      <msh:sideLink params="id" action="/entityParentPrepareCreate-work_journalPatternCalendar" name="Журнал шаблона" title="Добавить журнал шаблона" guid="b987f33-468f-992b-3316707d4a2f" roles="/Policy/Mis/Worker/WorkCalendar/JournalPattern/Create" />
    </msh:sideMenu>
    <msh:sideMenu title="Показать все" guid="509d7dbf-78b3-44f9-8a75-b96b7492f99f">
      <msh:sideLink params="id" action="/entityParentList-cal_workCalendarDay" name="Рабочие дни" title="Добавить шаблон" guid="b987f22c-2333-468f-992b-3316707d4a2f" roles="/Policy/Mis/Worker/WorkCalendar/View" />
    </msh:sideMenu>
  </tiles:put>
</tiles:insert>

