<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://struts.apache.org/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://www.nuzmsh.ru/tags/msh" prefix="msh" %>
<%@ taglib uri="http://www.ecom-ast.ru/tags/ecom" prefix="ecom" %>

<tiles:insert page="/WEB-INF/tiles/mainLayout.jsp" flush="true">

  <tiles:put name="body" type="string">
    <!-- 
    	  - Журнал шаблона календаря
    	  -->
    <msh:form action="/entityParentSaveGoParentView-work_journalPatternCalendar.do" defaultField="hello" guid="05410aab-fb9e-4fb2-8194-3e9a1f38354a">
      <msh:hidden guid="hiddenId" property="id" />
      <msh:hidden guid="hiddenSaveType" property="saveType" />
      <msh:hidden guid="hiddenParent" property="workCalendar" />
      <msh:panel guid="panel">
        <msh:row guid="00237e8b-4ca9-40a6-af95-6b44444">
          <msh:autoComplete  shortViewAction="entityShortView-cal_patternBySpec.do" parentId="work_journalPatternCalendarForm.workCalendar" vocName="workCalendarPattern" property="pattern" guid="3a3e4d1b-8802-467d-b205-715fb379b018" horizontalFill="true" fieldColSpan="3" viewAction="entitySubclassView-cal_pattern.do" />
        </msh:row>
        <msh:row guid="00237e8b-4ca9-40a6-af95-6b44444">
          <msh:autoComplete  vocName="vocWorkBusy" property="workBusy" horizontalFill="true" fieldColSpan="3" />
        </msh:row>
        <msh:row guid="00237e8b-4ca9-40a6-af95-6b4e14bb9b74">
          <msh:checkBox property="noActive" label="Недействует" guid="65519dd8-f032-4a80-a311-e8df67b0c5ae" />
        </msh:row>
        <msh:row guid="64713a7e-aa81-41ec-a822-c1accce38fe0">
          <msh:textField property="dateFrom" label="Действует с" guid="f75a90ab-ec2c-4ab1-aff5-f8d937dd4b5d" />
          <msh:textField property="dateTo" label="по" guid="01fc9aba-0cec-4c02-b21b-a6cd7adacce2" />
        </msh:row>
        <msh:submitCancelButtonsRow guid="submitCancel" colSpan="4" />
      </msh:panel>
    </msh:form>
  </tiles:put>
  <tiles:put name="title" type="string">
    <ecom:titleTrail guid="titleTrail-123" mainMenu="Lpu" beginForm="work_journalPatternCalendarForm" />
  </tiles:put>
  <tiles:put name="side" type="string">
    <msh:ifFormTypeIsView formName="work_journalPatternCalendarForm" guid="f23df5de-8741-4611-bcbb-951011f2a19d">
      <msh:sideMenu title="Журнал шаблона календаря" guid="81da5897-a667-43f3-9eca-830e380b6e44">
        <msh:sideLink key="ALT+2" params="id" action="/entityParentEdit-work_journalPatternCalendar" name="Изменить" roles="/Policy/Mis/Worker/WorkCalendar/JournalPattern/Edit" guid="e4b8061b-28f5-4429-ba7d-0b00cf4ad3d0" />
        <msh:sideLink key="ALT+DEL" confirm="Удалить?" params="id" action="/entityParentDeleteGoParentView-work_journalPatternCalendar" name="Удалить" roles="/Policy/Mis/Worker/WorkCalendar/JournalPattern/Delete" guid="01fbfbe3-3dff-4766-a299-f9d4f634aff0" />
      </msh:sideMenu>
      <msh:sideMenu title="Добавить">
      	<msh:sideLink key="ALT+3" params="id" action="/entityParentPrepareCreate-cal_workCalendarPatternBySpec" name="Шаблон графика работы"/>
      </msh:sideMenu>
    </msh:ifFormTypeIsView>
  </tiles:put>
</tiles:insert>

