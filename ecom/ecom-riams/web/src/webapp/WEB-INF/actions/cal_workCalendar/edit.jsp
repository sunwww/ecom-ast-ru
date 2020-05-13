<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://struts.apache.org/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://www.nuzmsh.ru/tags/msh" prefix="msh" %>
<%@ taglib uri="http://www.ecom-ast.ru/tags/ecom" prefix="ecom" %>

<tiles:insert page="/WEB-INF/tiles/mainLayout.jsp" flush="true">

  <tiles:put name="body" type="string">
    <!-- 
    	  - 
    	  -->
    <msh:form action="/entityParentSaveGoSubclassView-cal_workCalendar.do" defaultField="hello">
      <msh:hidden property="id" />
      <msh:hidden property="saveType" />
      <msh:hidden property="workFunction" />
      <msh:panel>
        <msh:row >
        	<msh:checkBox property="autoGenerate" label="Автоматическая генерация"/>
        </msh:row>
        <msh:row>
        	<msh:textField property="afterDaysGenerate" label="Через сколько дней генерировать"/>
        </msh:row>
        <msh:submitCancelButtonsRow colSpan="4" />
      </msh:panel>
    </msh:form>
    <msh:ifFormTypeIsView formName="cal_workCalendarForm">
      <msh:ifInRole roles="/Policy/Mis/Worker/WorkCalendar/JournalPattern/View">
        <msh:section title="Журналы шаблонов">
          <ecom:webQuery name="journals" nativeSql="select jp.id as f1_id, wcp.name as f2_name, jp.dateFrom as f3_datefrom, jp.dateTo as f4_dateto
,case when jp.noactive='1' then 'background-color:red' when jp.dateto<current_date then 'background-color:red' else 'background-color:green' end as f5_colorStyle
 from journalpatterncalendar jp
 left join workcalendarpattern wcp on wcp.id=jp.pattern_id

 where jp.workcalendar_id  =${param.id}"/>
          <msh:table styleRow="5" deleteUrl="entityParentDeleteGoParentView-work_journalPatternCalendar.do" editUrl="entityParentEdit-work_journalPatternCalendar.do" viewUrl="entityShortView-work_journalPatternCalendar.do"
                     name="journals" action="entityParentView-work_journalPatternCalendar.do" idField="1">
            <msh:tableColumn columnName="ИД" property="1" />
            <msh:tableColumn columnName="Информация о шаблоне" property="2" />
            <msh:tableColumn columnName="Действует с" property="3" />
            <msh:tableColumn columnName="Действует по" property="4" />
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
      <msh:sideLink params="id" action="/entityParentEdit-cal_workCalendar" name="Изменить" roles="/Policy/Mis/Worker/WorkCalendar/Edit" />
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

