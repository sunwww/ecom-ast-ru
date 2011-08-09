<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://struts.apache.org/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://www.nuzmsh.ru/tags/msh" prefix="msh" %>
<%@ taglib uri="http://www.ecom-ast.ru/tags/ecom" prefix="ecom" %>
<tiles:insert page="/WEB-INF/tiles/mainLayout.jsp" flush="true" >
	<tiles:put name='title' type='string'>
		<ecom:titleTrail mainMenu="Lpu" beginForm="cal_patternForm" title="Список алгоритмов шаблона рабочего календаря по профилактическому дню"/>
	</tiles:put>
	<tiles:put name='side' type='string'>
		<msh:sideMenu title="Добавить">
			<msh:sideLink key='ALT+N' roles="/Policy/Mis/Worker/WorkCalendar/Pattern/Algorithm/Create" params="id" action="/entityParentPrepareCreate-cal_prophDayAlgorithm" title="Алгоритм шаблога рабочего календаря по профилактическому дню" name="Алгоритм шаблога рабочего календаря по профилактическому дню" />
		</msh:sideMenu>
	</tiles:put>
	<tiles:put name='body' type='string' >
		<ecom:webQuery  name="algProf" nativeSql="
					 select wca.id,wca.monthDay,vwd.name as vwdname,vwmo.name as vwmoname from WorkCalendarAlgorithm wca
					 left join VocWeekMonthOrder vwmo on vwmo.id=wca.monthOrder_id
					 left join VocWeekDay vwd on vwd.id=wca.weekDay_id
					where wca.dtype='WorkCalendarProphDayAlgorithm' and wca.pattern_id='${param.id}'
					" />
					<msh:table  name="algProf"
						editUrl="entityParentEdit-cal_prophDayAlgorithm.do" 
						deleteUrl="entityParentDeleteGoSubclassView-cal_prophDayAlgorithm.do" 
						 action="entitySubclassView-cal_algorithm.do" idField="1">
						<msh:tableColumn property="sn" columnName="#"/>
						<msh:tableColumn property="2" columnName="День месяца"/>
						<msh:tableColumn property="3" columnName="День недели"/>
						<msh:tableColumn property="4" columnName="Порядок недели в месяце"/>
					</msh:table>
	</tiles:put>
</tiles:insert>
