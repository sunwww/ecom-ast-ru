<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://struts.apache.org/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://www.nuzmsh.ru/tags/msh" prefix="msh" %>
<%@ taglib uri="http://www.ecom-ast.ru/tags/ecom" prefix="ecom" %>
<tiles:insert page="/WEB-INF/tiles/mainLayout.jsp" flush="true" >
	<tiles:put name='title' type='string'>
		<ecom:titleTrail mainMenu="Lpu" beginForm="cal_patternForm" title="Список алгоритмов шаблона рабочего календаря по дням недели"/>
	</tiles:put>
	<tiles:put name='side' type='string'>
		<msh:sideMenu title="Добавить">
			<msh:sideLink key='ALT+N' roles="/Policy/Mis/Worker/WorkCalendar/Pattern/Algorithm/Create" params="id" action="/entityParentPrepareCreate-cal_weekDaysAlgorithm" title="Алгоритм шаблона рабочего календаря по дням недели" name="Алгоритм шаблона рабочего календаря по дням недели" />
		</msh:sideMenu>
	</tiles:put>
	<tiles:put name='body' type='string' >
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
	</tiles:put>
</tiles:insert>
