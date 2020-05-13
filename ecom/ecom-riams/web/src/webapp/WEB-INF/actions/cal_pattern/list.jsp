<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://struts.apache.org/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://www.nuzmsh.ru/tags/msh" prefix="msh" %>
<%@ taglib uri="http://www.ecom-ast.ru/tags/ecom" prefix="ecom" %>
<tiles:insert page="/WEB-INF/tiles/mainLayout.jsp" flush="true" >
	<tiles:put name='title' type='string'>
		<msh:title>Шаблоны рабочих календарей</msh:title>
	</tiles:put>
	<tiles:put name='side' type='string'>
		<msh:sideMenu title="Добавить">
			<msh:sideLink key='ALT+2' roles="/Policy/Mis/Worker/WorkCalendar/Pattern/Specialist/Create" params="" action="/entityPrepareCreate-cal_patternBySpec" title="Шаблон рабочего календаря специалиста" name="Шаблон рабочего календаря специалиста" />
			<msh:sideLink key='ALT+3' roles="/Policy/Mis/MisLpu/OperatingRoom/Pattern/Create" params="" action="/entityPrepareCreate-cal_patternByOperRoom" title="Шаблон рабочего календаря операционной" name="Шаблон рабочего календаря операционной" />
		</msh:sideMenu>
	</tiles:put>
	<tiles:put name='body' type='string' >
	<msh:section>
		<msh:sectionTitle>Список шаблонов рабочих календарей по специалистам</msh:sectionTitle>
		<msh:sectionContent>
		  	<ecom:webQuery name="list" nativeSql="select wcp.id,wcp.name as wcpname,vwb.name as vwbname,vwct.name as vwctname from WorkCalendarPattern wcp 
		  	left join VocWorkBusy vwb on vwb.id=wcp.workBusy_id
		  	left join VocWorkCalendarType vwct on vwct.id=wcp.calendarType_id
		  	where wcp.dtype='WorkCalenPatternBySpec'
		  	"/>
		    <msh:table  name="list" editUrl="entityEdit-cal_patternBySpec.do" deleteUrl="entityDelete-cal_pattern.do" viewUrl="entityShortView-cal_patternBySpec.do" action="entitySubclassView-cal_pattern.do" idField="1">
					<msh:tableColumn columnName="#" property="sn" />
					<msh:tableColumn columnName="Название" property="2" />
					<msh:tableColumn columnName="Тип календаря" property="4" />
					<msh:tableColumn columnName="Тип занятости" property="3" />
		    </msh:table>
	    </msh:sectionContent>
    </msh:section>
	</tiles:put>
</tiles:insert>
