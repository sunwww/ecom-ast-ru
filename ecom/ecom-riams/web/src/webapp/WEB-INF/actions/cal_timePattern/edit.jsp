<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://struts.apache.org/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://www.nuzmsh.ru/tags/msh" prefix="msh" %>
<%@ taglib uri="http://www.ecom-ast.ru/tags/ecom" prefix="ecom" %>
<%@ taglib tagdir="/WEB-INF/tags" prefix="tags" %>
<tiles:insert page="/WEB-INF/tiles/mainLayout.jsp" flush="true">
	<tiles:put name="body" type="string">
		<msh:form action="/entitySaveGoView-cal_timePattern.do" defaultField="">
			<msh:hidden property="id" />
			<msh:hidden property="saveType" />
			<msh:hidden property="parent" />
			<msh:panel>
				<msh:row>
					<msh:autoComplete property="dayPattern" label="Шаблон дня" vocName="workCalendarDayPattern" horizontalFill="true" />
				</msh:row>
			<msh:submitCancelButtonsRow colSpan="4" />
			</msh:panel>
		</msh:form>
		<msh:ifFormTypeIsView formName="cal_timePatternForm">
			<msh:section title="Шаблоны резервов">
			<ecom:parentEntityListAll formName="cal_timePatternForm" attribute="reservePatterns" />
				<msh:table name="reservePatterns" action="entityParentView-workcalendar_workCalendarReservePattern.do" idField="id">
					<msh:tableColumn columnName="id" property="id"/>
				</msh:table>
			</msh:section>
		</msh:ifFormTypeIsView>
	</tiles:put>
	<tiles:put name="title" type="string">
		<ecom:titleTrail mainMenu="Patient" beginForm="cal_timePatternForm" />
	</tiles:put>
	<tiles:put name="side" type="string">
		<msh:ifFormTypeAreViewOrEdit formName="cal_timePatternForm">
			<msh:sideMenu>
				<msh:sideLink key="ALT+2" params="id" action="/entityParentEdit-cal_timePattern" name="Изменить" title="Изменить" roles="/Policy/Mis/Worker/WorkCalendar/Pattern/Day/Time/Edit"/>
				<msh:sideLink key="ALT+DEL" params="id" action="/entityParentDelete-cal_timePattern" name="Удалить" title="Удалить" roles="/Policy/Mis/Worker/WorkCalendar/Pattern/Day/Time/Delete"/>
			</msh:sideMenu>
		</msh:ifFormTypeAreViewOrEdit>
	</tiles:put>
</tiles:insert>
