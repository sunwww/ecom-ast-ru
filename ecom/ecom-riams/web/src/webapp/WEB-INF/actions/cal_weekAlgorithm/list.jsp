<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://struts.apache.org/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://www.nuzmsh.ru/tags/msh" prefix="msh" %>
<%@ taglib uri="http://www.ecom-ast.ru/tags/ecom" prefix="ecom" %>
<tiles:insert page="/WEB-INF/tiles/mainLayout.jsp" flush="true" >
	<tiles:put name='title' type='string'>
		<ecom:titleTrail mainMenu="Lpu" beginForm="cal_patternForm" title="Список алгоритмов шаблона рабочего календаря по неделям"/>
	</tiles:put>
	<tiles:put name='side' type='string'>
		<msh:sideMenu title="Добавить">
			<msh:sideLink key='ALT+N' roles="/Policy/Mis/Worker/WorkCalendar/Pattern/Algorithm/Create" params="id" action="/entityParentPrepareCreate-cal_weekAlgorithm" title="Алгоритм шаблона рабочего календаря по неделям" name="Алгоритм шаблона рабочего календаря по неделям" />
		</msh:sideMenu>
	</tiles:put>
	<tiles:put name='body' type='string' >
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
						deleteUrl="entityParentDeleteGoSubclassView-cal_weekAlgorithm.do" 
						 name="algWeeks" action="entitySubclassView-cal_algorithm.do" idField="1">
						<msh:tableColumn property="sn" columnName="#"/>
						<msh:tableColumn property="2" columnName="Рабочиая неделя"/>
						<msh:tableColumn property="3" columnName="Тип четности"/>
						<msh:tableColumn property="4" columnName="Четность"/>
						<msh:tableColumn property="5" columnName="Шаблон дня"/>
					</msh:table>
	</tiles:put>
</tiles:insert>
