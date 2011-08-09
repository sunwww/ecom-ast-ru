<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://struts.apache.org/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://www.nuzmsh.ru/tags/msh" prefix="msh" %>
<%@ taglib uri="http://www.ecom-ast.ru/tags/ecom" prefix="ecom" %>
<%@ taglib tagdir="/WEB-INF/tags" prefix="tags" %>
<tiles:insert page="/WEB-INF/tiles/mainLayout.jsp" flush="true">
	<tiles:put name="body" type="string">
		<msh:form action="/entityParentSaveGoView-cal_timeInterval.do" defaultField="timeFrom">
			<msh:hidden property="id" />
			<msh:hidden property="saveType" />
			<msh:hidden property="dayPattern" />
			<msh:panel>
				<msh:row>
					<msh:autoComplete property="workBusy" label="Тип занятости" vocName="vocWorkBusy" horizontalFill="true" fieldColSpan="3"/>
				</msh:row>
				<msh:row>
					<msh:textField property="timeFrom" label="Начиная с времени"/>
					<msh:textField property="timeTo" label="Заканчивая временем"/>
				</msh:row>
				<msh:row>
					<msh:textField property="visitTime" label="Среднее время на визит"/>
					<td>мин.</td>
				</msh:row>
			<msh:submitCancelButtonsRow colSpan="4" />
			</msh:panel>
		</msh:form>
		<msh:ifFormTypeIsView formName="cal_timeIntervalForm">
		</msh:ifFormTypeIsView>
	</tiles:put>
	<tiles:put name="title" type="string">
		<ecom:titleTrail mainMenu="Patient" beginForm="cal_timeIntervalForm" />
	</tiles:put>
	<tiles:put name="side" type="string">
		<msh:ifFormTypeAreViewOrEdit formName="cal_timeIntervalForm">
			<msh:sideMenu>
				<msh:sideLink key="ALT+2" params="id" action="/entityParentEdit-cal_timeInterval" name="Изменить" title="Изменить" roles="/Policy/Mis/Worker/WorkCalendar/Pattern/Day/Time/Edit"/>
				<msh:sideLink key="ALT+DEL" params="id" action="/entityParentDelete-cal_timeInterval" name="Удалить" title="Удалить" roles="/Policy/Mis/Worker/WorkCalendar/Pattern/Day/Time/Delete" confirm="Удалить?"/>
			</msh:sideMenu>
			<msh:sideMenu title="Добавить" >
				<msh:sideLink key="ALT+3" params="id" action="/entityParentPrepareCreate-cal_servicePattern" name="Шаблон резерва услуги" title="Добавить шаблон резерва по услуге" roles="/Policy/Mis/Worker/WorkCalendar/Pattern/Day/Time/Reserve/Create"/>
				<msh:sideLink key="ALT+4" params="id" action="/entityParentPrepareCreate-cal_streamPattern" name="Шаблон резерва по потоку обслуживания" title="Добавить шаблон резерва по потоку обслуживания" roles="/Policy/Mis/Worker/WorkCalendar/Pattern/Day/Time/Reserve/Create"/>
			</msh:sideMenu>
		</msh:ifFormTypeAreViewOrEdit>
	</tiles:put>
</tiles:insert>
