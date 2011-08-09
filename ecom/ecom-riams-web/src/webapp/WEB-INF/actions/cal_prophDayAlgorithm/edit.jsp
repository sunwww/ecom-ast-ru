<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://struts.apache.org/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://www.nuzmsh.ru/tags/msh" prefix="msh" %>
<%@ taglib uri="http://www.ecom-ast.ru/tags/ecom" prefix="ecom" %>
<%@ taglib tagdir="/WEB-INF/tags" prefix="tags" %>
<tiles:insert page="/WEB-INF/tiles/mainLayout.jsp" flush="true">
	<tiles:put name="body" type="string">
		<msh:form action="/entityParentSaveGoSubclassView-cal_prophDayAlgorithm.do" defaultField="monthOrderName">
			<msh:hidden property="id" />
			<msh:hidden property="saveType" />
			<msh:hidden property="pattern" />
			<msh:panel>
				<msh:separator label="Проф.день в зависимости от дня недели" colSpan="2"/>
				<msh:row>
					<msh:autoComplete size="40" property="monthOrder" label="Порядок недели в месяце" vocName="vocWeekMonthOrder" horizontalFill="true" />
				</msh:row>
				<msh:row>
					<msh:autoComplete viewAction="entityParentView-cal_dayPattern.do" shortViewAction="entityShortView-cal_dayPattern.do" size="40" property="weekDay" label="День недели" vocName="vocWeekDay" horizontalFill="true" />
				</msh:row>
				<msh:separator label="Проф.день в зависимости от числа" colSpan="2"/>
				<msh:row>
					<msh:textField property="monthDay" label="Число"/>
				</msh:row>
			<msh:submitCancelButtonsRow colSpan="2" />
			</msh:panel>
		</msh:form>
		<msh:ifFormTypeIsView formName="cal_prophDayAlgorithmForm">
		</msh:ifFormTypeIsView>
	</tiles:put>
	<tiles:put name="title" type="string">
		<ecom:titleTrail mainMenu="Patient" beginForm="cal_prophDayAlgorithmForm" />
	</tiles:put>
	<tiles:put name="side" type="string">
		<msh:ifFormTypeAreViewOrEdit formName="cal_prophDayAlgorithmForm">
			<msh:sideMenu>
				<msh:sideLink key="ALT+2" params="id" action="/entityParentEdit-cal_prophDayAlgorithm" name="Изменить" title="Изменить" roles="/Policy/Mis/Worker/WorkCalendar/Pattern/Algorithm/Edit"/>
				<msh:sideLink key="ALT+DEL" params="id" action="/entityParentDeleteGoSubclassView-cal_prophDayAlgorithm" name="Удалить" title="Удалить" roles="/Policy/Mis/Worker/WorkCalendar/Pattern/Algorithm/Delete" confirm="Удалить?"/>
			</msh:sideMenu>
		</msh:ifFormTypeAreViewOrEdit>
	</tiles:put>
</tiles:insert>
