<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://struts.apache.org/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://www.nuzmsh.ru/tags/msh" prefix="msh" %>
<%@ taglib uri="http://www.ecom-ast.ru/tags/ecom" prefix="ecom" %>
<%@ taglib tagdir="/WEB-INF/tags" prefix="tags" %>
<tiles:insert page="/WEB-INF/tiles/mainLayout.jsp" flush="true">
	<tiles:put name="body" type="string">
		<msh:form action="/entityParentSaveGoSubclassView-cal_weekAlgorithm.do" defaultField="workWeekName">
			<msh:hidden property="id" />
			<msh:hidden property="saveType" />
			<msh:hidden property="pattern" />
			<msh:panel>
				<msh:row>
					<msh:autoComplete property="workWeek" label="Рабочая недели" vocName="vocWorkWeek" horizontalFill="true" />
				</msh:row>
				<msh:row>
					<msh:autoComplete
					 viewAction="entityParentView-cal_dayPattern.do" shortViewAction="entityShortView-cal_dayPattern.do" 
					 size="100" parentId="cal_weekAlgorithmForm.pattern" property="dayPattern" label="Шаблон дня" vocName="workCalendarDayPattern" horizontalFill="true" />
				</msh:row>
				
				<msh:row>
					<msh:autoComplete property="parity" label="Тип дня" vocName="vocDayParity" horizontalFill="true"/>
				</msh:row>
				<msh:row>
					<msh:autoComplete property="calendarParity" label="Тип четности" vocName="vocWorkCalendarParity" horizontalFill="true" />
				</msh:row>
				
			<msh:submitCancelButtonsRow colSpan="4" />
			</msh:panel>
		</msh:form>
		<msh:ifFormTypeIsView formName="cal_weekAlgorithmForm">
		</msh:ifFormTypeIsView>
	</tiles:put>
	<tiles:put name="title" type="string">
		<ecom:titleTrail mainMenu="Patient" beginForm="cal_weekAlgorithmForm" />
	</tiles:put>
	<tiles:put name="side" type="string">
		<msh:ifFormTypeAreViewOrEdit formName="cal_weekAlgorithmForm">
			<msh:sideMenu>
				<msh:sideLink key="ALT+2" params="id" action="/entityParentEdit-cal_weekAlgorithm" name="Изменить" title="Изменить" roles="/Policy/Mis/Worker/WorkCalendar/Pattern/Algorithm/Edit"/>
				<msh:sideLink key="ALT+DEL" params="id" action="/entityParentDeleteGoSubclassView-cal_weekAlgorithm" name="Удалить" title="Удалить" roles="/Policy/Mis/Worker/WorkCalendar/Pattern/Algorithm/Delete" confirm="Удалить?"/>
			</msh:sideMenu>
			<msh:sideMenu title="Добавить" >
			</msh:sideMenu>
		</msh:ifFormTypeAreViewOrEdit>
	</tiles:put>
</tiles:insert>
