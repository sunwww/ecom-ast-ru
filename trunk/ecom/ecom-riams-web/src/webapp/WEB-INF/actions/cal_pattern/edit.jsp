<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://struts.apache.org/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://www.nuzmsh.ru/tags/msh" prefix="msh" %>
<%@ taglib uri="http://www.ecom-ast.ru/tags/ecom" prefix="ecom" %>
<%@ taglib tagdir="/WEB-INF/tags" prefix="tags" %>
<tiles:insert page="/WEB-INF/tiles/mainLayout.jsp" flush="true">
	<tiles:put name="body" type="string">
		<msh:form action="/entityParentSaveGoView-cal_pattern.do" defaultField="calendarTypeName">
			<msh:hidden property="id" />
			<msh:hidden property="saveType" />
			<msh:hidden property="workFunction" />
			<msh:panel>
				<msh:row>
					<msh:textField property="name" label="Название"/>
				</msh:row>
				<msh:row>
					<msh:autoComplete property="calendarType" label="Тип календаря" vocName="vocWorkCalendarType" horizontalFill="true" />
				</msh:row>
				<msh:row>
					<msh:autoComplete property="workBusy" label="Тип занятости" vocName="vocWorkBusy" horizontalFill="true" />
				</msh:row>
				
			<msh:submitCancelButtonsRow colSpan="2" />
			</msh:panel>
		</msh:form>
		<msh:ifFormTypeIsView formName="cal_patternForm">
			<msh:section title="Алгоритмы шаблона рабочего календаря">
			<ecom:parentEntityListAll formName="cal_algorithmForm" attribute="pattern" />
				<msh:table name="algorithms" action="entityParentView-workcalendar_workCalendarAlgorithm.do" idField="id">
					<msh:tableColumn columnName="id" property="id"/>
				</msh:table>
			</msh:section>
		</msh:ifFormTypeIsView>
	</tiles:put>
	<tiles:put name="title" type="string">
		<ecom:titleTrail mainMenu="Patient" beginForm="cal_patternForm" />
	</tiles:put>
	<tiles:put name="side" type="string">
		<msh:ifFormTypeAreViewOrEdit formName="cal_patternForm">
			<msh:sideMenu>
				<msh:sideLink key="ALT+2" params="id" action="/entityParentEdit-cal_pattern" name="Изменить" title="Изменить" roles=""/>
				<msh:sideLink key="ALT+DEL" params="id" action="/entityParentDelete-cal_pattern" name="Удалить" title="Удалить" roles=""/>
			</msh:sideMenu>
			<msh:sideMenu title="Добавить" >
				<msh:sideLink key="ALT+N" params="id" action="/entityParentPrepareCreate-workcalendar_workCalendarAlgorithm" name="Алгоритмы шаблона рабочего календаря" title="Алгоритмы шаблона рабочего календаря" roles=""/>
			</msh:sideMenu>
		</msh:ifFormTypeAreViewOrEdit>
	</tiles:put>
</tiles:insert>
