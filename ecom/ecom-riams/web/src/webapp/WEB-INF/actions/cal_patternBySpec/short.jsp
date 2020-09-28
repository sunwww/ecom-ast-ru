<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://struts.apache.org/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://www.nuzmsh.ru/tags/msh" prefix="msh" %>
<%@ taglib uri="http://www.ecom-ast.ru/tags/ecom" prefix="ecom" %>
<%@ taglib uri="/WEB-INF/mis.tld" prefix="mis" %>
<%@ taglib tagdir="/WEB-INF/tags" prefix="tags" %>

<tiles:insert page="/WEB-INF/tiles/mainShortLayout.jsp" flush="true">

  <tiles:put name="body" type="string">
    <msh:form title="<a href='entityView-cal_patternBySpec.do?id=${param.id}'>Шаблон рабочего календаря по специалисту</a>" action="/entityParentSaveGoView-cal_patternBySpec.do" defaultField="name">
      <msh:hidden property="id" />
      <msh:hidden property="saveType" />
      <msh:hidden property="lpu"/>
			<msh:panel>
				<msh:row>
					<msh:textField property="name" label="Название" size="100"/>
				</msh:row>
				<msh:row>
					<msh:autoComplete property="workFunction" vocName="workFunctionByDirect" size="100"/>
				</msh:row>
				<msh:row>
					<msh:autoComplete size="100" property="calendarType" label="Тип календаря" vocName="vocWorkCalendarType" horizontalFill="true" />
				</msh:row>
			<msh:submitCancelButtonsRow colSpan="2" />
			</msh:panel>
    	</msh:form>
		<msh:ifFormTypeIsView formName="cal_patternBySpecForm">
		<table>
		<tr><td valign="top"  style="padding-right: 1em">
			<msh:section title="Алгоритм по проф.дню" createRoles="/Policy/Mis/Worker/WorkCalendar/Pattern/Algorithm/Create" viewRoles="/Policy/Mis/Worker/WorkCalendar/Pattern/Algorithm/View"
				createUrl="entityParentPrepareCreate-cal_prophDayAlgorithm.do?id=${param.id}" listUrl="entityParentList-cal_prophDayAlgorithm.do?id=${param.id}">
				<msh:sectionContent>
					<ecom:webQuery  name="algProf" nativeSql="
					 select wca.id,wca.monthDay,vwd.name as vwdname,vwmo.name as vwmoname from WorkCalendarAlgorithm wca
					 left join VocWeekMonthOrder vwmo on vwmo.id=wca.monthOrder_id
					 left join VocWeekDay vwd on vwd.id=wca.weekDay_id
					where wca.dtype='WorkCalendarProphDayAlgorithm' and wca.pattern_id='${param.id}'
					" />
					<msh:table  name="algProf"
						editUrl="entityParentEdit-cal_prophDayAlgorithm.do" 
						viewUrl="entityShortView-cal_prophDayAlgorithm.do"
						deleteUrl="entityParentDeleteGoSubclassView-cal_prophDayAlgorithm.do" 
						 action="entitySubclassView-cal_algorithm.do" idField="1">
						<msh:tableColumn property="sn" columnName="#"/>
						<msh:tableColumn property="2" columnName="День месяца"/>
						<msh:tableColumn property="3" columnName="День недели"/>
						<msh:tableColumn property="4" columnName="Порядок недели в месяце"/>
					</msh:table>
				</msh:sectionContent>
			</msh:section>
			</td><td valign="top">
			<msh:section title="Алгоритм по датам"  createRoles="/Policy/Mis/Worker/WorkCalendar/Pattern/Algorithm/Create" viewRoles="/Policy/Mis/Worker/WorkCalendar/Pattern/Algorithm/View"
				createUrl="entityParentPrepareCreate-cal_datesAlgorithm.do?id=${param.id}" listUrl="entityParentList-cal_datesAlgorithm.do?id=${param.id}">
				<msh:sectionContent>
					<ecom:webQuery name="algDates" nativeSql="
					 select wca.id,wca.dateFrom,wca.dateTo,wcdp.name
					 from WorkCalendarAlgorithm wca 
					 left join WorkCalendarDayPattern wcdp on wcdp.id=wca.dayPattern_id
					 where wca.dtype='WorkCalendarDatesAlgorithm' and pattern_id='${param.id}'
					"/>
					<msh:table 
						editUrl="entityParentEdit-cal_datesAlgorithm.do" 
						viewUrl="entityShortView-cal_datesAlgorithm.do"
						deleteUrl="entityParentDeleteGoSubclassView-cal_datesAlgorithm.do" 
						name="algDates" action="entitySubclassView-cal_algorithm.do" idField="1">
						<msh:tableColumn property="sn" columnName="#"/>
						<msh:tableColumn property="2" columnName="С какого"/>
						<msh:tableColumn property="3" columnName="По какой день"/>
						<msh:tableColumn property="4" columnName="Шаблон дня"/>
					</msh:table>
				</msh:sectionContent>
			</msh:section>
			</td></tr><tr><td valign="top"  style="padding-right: 1em">
			<msh:section title="Алгоритм по дням недели"  createRoles="/Policy/Mis/Worker/WorkCalendar/Pattern/Algorithm/Create" viewRoles="/Policy/Mis/Worker/WorkCalendar/Pattern/Algorithm/View"
				createUrl="entityParentPrepareCreate-cal_weekDaysAlgorithm.do?id=${param.id}" listUrl="entityParentPrepareCreate-cal_weekDaysAlgorithm.do?id=${param.id}"
			>
				<msh:sectionContent>
					<ecom:webQuery name="algWeekDays" nativeSql="
					 select wca.id,vwcp.name as vwcpname,vdp.name as vspname
					 ,vwd1.name as vwd1name
					 ,vwd2.name as vwd2name,vwd3.name as vwd3name,vwd4.name as vwd4name
					 ,vwd5.name as vwd5name,vwd6.name as vwd6name,vwd7.name as vwd7name
					 
					 from WorkCalendarAlgorithm wca 
					 left join VocWorkCalendarParity vwcp on vwcp.id=wca.calendarParity_id
					 left join VocDayParity vdp on vdp.id=wca.parity_id
					 
					 left join WorkCalendarDayPattern vwd1 on vwd1.id=wca.monday_id
					 left join WorkCalendarDayPattern vwd2 on vwd2.id=wca.tuesday_id
					 left join WorkCalendarDayPattern vwd3 on vwd3.id=wca.wednesday_id
					 left join WorkCalendarDayPattern vwd4 on vwd4.id=wca.thursday_id
					 left join WorkCalendarDayPattern vwd5 on vwd5.id=wca.friday_id
					 left join WorkCalendarDayPattern vwd6 on vwd6.id=wca.saturday_id
					 left join WorkCalendarDayPattern vwd7 on vwd7.id=wca.sunday_id
					 
					 where wca.dtype='WorkCalendarWeekDaysAlgorithm' and wca.pattern_id='${param.id}'
					"/>
					<msh:table name="algWeekDays" 
						editUrl="entityParentEdit-cal_weekDaysAlgorithm.do" 
						viewUrl="entityShortView-cal_weekDaysAlgorithm.do"
						deleteUrl="entityParentDeleteGoSubclassView-cal_weekDaysAlgorithm.do" 
						action="entitySubclassView-cal_algorithm.do" idField="1">
						<msh:tableColumn property="sn" columnName="#"/>
						<msh:tableColumn property="2" columnName="Тип четности"/>
						<msh:tableColumn property="3" columnName="Четность"/>
						<msh:tableColumn property="4" columnName="Понедельник"/>
						<msh:tableColumn property="5" columnName="Вторник"/>
						<msh:tableColumn property="6" columnName="Среда"/>
						<msh:tableColumn property="7" columnName="Четверг"/>
						<msh:tableColumn property="8" columnName="Пятница"/>
						<msh:tableColumn property="9" columnName="Суббота"/>
						<msh:tableColumn property="10" columnName="Воскресение"/>
					</msh:table>
				</msh:sectionContent>
				
			</msh:section>
			</td><td valign="top">
			<msh:section title="Алгоритм по неделям"  createRoles="/Policy/Mis/Worker/WorkCalendar/Pattern/Algorithm/Create" viewRoles="/Policy/Mis/Worker/WorkCalendar/Pattern/Algorithm/View"
				createUrl="entityParentPrepareCreate-cal_weekAlgorithm.do?id=${param.id}" listUrl="entityParentList-cal_weekAlgorithm.do?id=${param.id}"
			>
				<msh:sectionContent>
					<ecom:webQuery name="algWeeks" nativeSql="
					select wca.id,vww.name,wcdp.name as wcdpname
					,vwcp.name as vwcpname,vdp.name as vdpname
					from WorkCalendarAlgorithm wca 
					left join VocWorkWeek vww on vww.id=wca.workWeek_id
					left join VocWorkCalendarParity vwcp on vwcp.id=wca.calendarParity_id
					left join VocDayParity vdp on vdp.id=wca.parity_id
					left join WorkCalendarDayPattern wcdp on wcdp.id=dayPattern_id
					 where dtype='WorkCalendarWeekAlgorithm' and pattern_id='${param.id}'
					"/>
					<msh:table
						editUrl="entityParentEdit-cal_weekAlgorithm.do" 
						viewUrl="entityShortView-cal_weekAlgorithm.do"
						deleteUrl="entityParentDeleteGoSubclassView-cal_weekAlgorithm.do" 
						 name="algWeeks" action="entitySubclassView-cal_algorithm.do" idField="1">
						<msh:tableColumn property="sn" columnName="#"/>
						<msh:tableColumn property="2" columnName="Рабочиая неделя"/>
						<msh:tableColumn property="3" columnName="Тип четности"/>
						<msh:tableColumn property="4" columnName="Четность"/>
						<msh:tableColumn property="5" columnName="Шаблон дня"/>
					</msh:table>
				</msh:sectionContent>
			</msh:section>
			</td>
			</tr>
			</table>
		</msh:ifFormTypeIsView>
  </tiles:put>
</tiles:insert>

