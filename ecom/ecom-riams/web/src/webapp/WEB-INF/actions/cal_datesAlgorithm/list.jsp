<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://struts.apache.org/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://www.nuzmsh.ru/tags/msh" prefix="msh" %>
<%@ taglib uri="http://www.ecom-ast.ru/tags/ecom" prefix="ecom" %>
<tiles:insert page="/WEB-INF/tiles/mainLayout.jsp" flush="true" >
	<tiles:put name='title' type='string'>
		<ecom:titleTrail mainMenu="Lpu" beginForm="cal_patternForm" title="Список алгоритмов шаблона рабочего календаря по датам"/>
	</tiles:put>
	<tiles:put name='side' type='string'>
		<msh:sideMenu title="Добавить">
			<msh:sideLink key='ALT+N' roles="/Policy/Mis/Worker/WorkCalendar/Pattern/Algorithm/Create" params="id" action="/entityParentPrepareCreate-cal_datesAlgorithm" title="Алгоритм шаблона рабочего календаря по датам" name="Алгоритм шаблона рабочего календаря по датам" />
		</msh:sideMenu>
	</tiles:put>
	<tiles:put name='body' type='string' >
		<ecom:webQuery name="algDates" nativeSql="
					 select wca.id,wca.dateFrom,wca.dateTo,wcdp.name
					 from WorkCalendarAlgorithm wca 
					 left join WorkCalendarDayPattern wcdp on wcdp.id=wca.dayPattern_id
					 where wca.dtype='WorkCalendarDatesAlgorithm' and pattern_id='${param.id}'
					"/>
					<msh:table 
						editUrl="entityParentEdit-cal_datesAlgorithm.do" 
						deleteUrl="entityParentDeleteGoSubclassView-cal_datesAlgorithm.do" 
						name="algDates" action="entitySubclassView-cal_algorithm.do" idField="1">
						<msh:tableColumn property="sn" columnName="#"/>
						<msh:tableColumn property="2" columnName="С какого"/>
						<msh:tableColumn property="3" columnName="По какой день"/>
						<msh:tableColumn property="4" columnName="Шаблон дня"/>
					</msh:table>
	</tiles:put>
</tiles:insert>
