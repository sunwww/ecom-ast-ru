<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://struts.apache.org/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://www.nuzmsh.ru/tags/msh" prefix="msh" %>
<%@ taglib uri="http://www.ecom-ast.ru/tags/ecom" prefix="ecom" %>
<tiles:insert page="/WEB-INF/tiles/mainLayout.jsp" flush="true" >
	<tiles:put name='title' type='string'>
		<ecom:titleTrail mainMenu="Lpu" beginForm="cal_streamPatternForm" title="Список Шаблон потока обслуживания рабочего календаря"/>
	</tiles:put>
	<tiles:put name='side' type='string'>
		<msh:sideMenu title="Добавить">
			<msh:sideLink key='ALT+N' 
				roles="/Policy/Mis/Worker/WorkCalendar/Pattern/Day/Time/Reserve/Create" 
				params="id" action="/entityPrepareCreate-cal_streamPattern" 
				title="Шаблон потока обслуживания рабочего календаря" name="Шаблон потока обслуживания рабочего календаря" />
		</msh:sideMenu>
	</tiles:put>
	<tiles:put name='body' type='string' >
		<msh:table name="list" action="entityView-workcalendar_workCalendarStreamPattern.do" idField="id">
			<msh:tableColumn columnName="#" property="sn" />
			<msh:tableColumn columnName="ИД" property="id" />
			<msh:tableColumn columnName="Поток обслуживания" property="serviceStream" />
			<msh:tableColumn columnName="Процент резерва" property="percent" />
		</msh:table>
	</tiles:put>
</tiles:insert>
