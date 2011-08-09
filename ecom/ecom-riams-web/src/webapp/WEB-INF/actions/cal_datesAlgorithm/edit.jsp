<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://struts.apache.org/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://www.nuzmsh.ru/tags/msh" prefix="msh" %>
<%@ taglib uri="http://www.ecom-ast.ru/tags/ecom" prefix="ecom" %>
<%@ taglib tagdir="/WEB-INF/tags" prefix="tags" %>
<tiles:insert page="/WEB-INF/tiles/mainLayout.jsp" flush="true">
	<tiles:put name="body" type="string">
		<msh:form action="/entityParentSaveGoSubclassView-cal_datesAlgorithm.do" defaultField="dateFrom">
			<msh:hidden property="id" />
			<msh:hidden property="saveType" />
			<msh:hidden property="pattern" />
			<msh:panel>
				<msh:row>
					<msh:textField property="dateFrom" label="Начиная с даты"/>
					<msh:textField property="dateTo" label="Заканчивая датой"/>
				</msh:row>
				<msh:row>
					<msh:autoComplete
					 viewAction="entityParentView-cal_dayPattern.do" shortViewAction="entityShortView-cal_dayPattern.do" 
					 fieldColSpan="3" parentId="cal_datesAlgorithmForm.pattern" property="dayPattern" label="Шаблон дня рабочего календаря" vocName="workCalendarDayPattern" horizontalFill="true" />
				</msh:row>
			<msh:submitCancelButtonsRow colSpan="4" />
			</msh:panel>
		</msh:form>
		<msh:ifFormTypeIsView formName="cal_datesAlgorithmForm">
		</msh:ifFormTypeIsView>
	</tiles:put>
	<tiles:put name="title" type="string">
		<ecom:titleTrail mainMenu="Patient" beginForm="cal_datesAlgorithmForm" />
	</tiles:put>
	<tiles:put name="side" type="string">
		<msh:ifFormTypeAreViewOrEdit formName="cal_datesAlgorithmForm">
			<msh:sideMenu>
				<msh:sideLink key="ALT+2" params="id" action="/entityParentEdit-cal_datesAlgorithm" name="Изменить" title="Изменить" roles="/Policy/Mis/Worker/WorkCalendar/Pattern/Algorithm/Edit"/>
				<msh:sideLink key="ALT+DEL" params="id" action="/entityParentDeleteGoSubclassView-cal_datesAlgorithm" name="Удалить" title="Удалить" roles="/Policy/Mis/Worker/WorkCalendar/Pattern/Algorithm/Delete" confirm="Удалить?"/>
			</msh:sideMenu>
			<msh:sideMenu title="Добавить" >
			</msh:sideMenu>
		</msh:ifFormTypeAreViewOrEdit>
	</tiles:put>
</tiles:insert>
