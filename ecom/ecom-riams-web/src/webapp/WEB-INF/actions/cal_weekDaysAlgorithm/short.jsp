<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://struts.apache.org/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://www.nuzmsh.ru/tags/msh" prefix="msh" %>
<%@ taglib uri="http://www.ecom-ast.ru/tags/ecom" prefix="ecom" %>
<%@ taglib uri="/WEB-INF/mis.tld" prefix="mis" %>
<%@ taglib tagdir="/WEB-INF/tags" prefix="tags" %>

<tiles:insert page="/WEB-INF/tiles/mainShortLayout.jsp" flush="true">

  <tiles:put name="body" type="string">
		<msh:form
		title="<a href='entityView-cal_weekDaysAlgorithm.do?id=${param.id}'>Алгоритм по дням недели</a>" 
		 action="/entityParentSaveGoSubclassView-cal_weekDaysAlgorithm.do" defaultField="mondayName">
			<msh:hidden property="id" />
			<msh:hidden property="saveType" />
			<msh:hidden property="pattern" />
			<msh:panel>
				<msh:row>
					<msh:autoComplete
					 viewAction="entityParentView-cal_dayPattern.do" shortViewAction="entityShortView-cal_dayPattern.do" 
					 size="100" parentId="cal_weekDaysAlgorithmForm.pattern" property="monday" label="Понедельник" vocName="workCalendarDayPattern" horizontalFill="true" />
				</msh:row>
				<msh:row>
					<msh:autoComplete 
					 viewAction="entityParentView-cal_dayPattern.do" shortViewAction="entityShortView-cal_dayPattern.do" 
					size="100" parentId="cal_weekDaysAlgorithmForm.pattern" property="tuesday" label="Вторник" vocName="workCalendarDayPattern" horizontalFill="true" />
				</msh:row>
				<msh:row>
					<msh:autoComplete 
					 viewAction="entityParentView-cal_dayPattern.do" shortViewAction="entityShortView-cal_dayPattern.do" 
					size="100" parentId="cal_weekDaysAlgorithmForm.pattern" property="wednesday" label="Среда" vocName="workCalendarDayPattern" horizontalFill="true" />
				</msh:row>
				<msh:row>
					<msh:autoComplete 
					 viewAction="entityParentView-cal_dayPattern.do" shortViewAction="entityShortView-cal_dayPattern.do" 
					size="100" parentId="cal_weekDaysAlgorithmForm.pattern" property="thursday" label="Четверг" vocName="workCalendarDayPattern" horizontalFill="true" />
				</msh:row>
				<msh:row>
					<msh:autoComplete 
					 viewAction="entityParentView-cal_dayPattern.do" shortViewAction="entityShortView-cal_dayPattern.do" 
					size="100" parentId="cal_weekDaysAlgorithmForm.pattern" property="friday" label="Пятница" vocName="workCalendarDayPattern" horizontalFill="true" />
				</msh:row>
				<msh:row>
					<msh:autoComplete 
					 viewAction="entityParentView-cal_dayPattern.do" shortViewAction="entityShortView-cal_dayPattern.do" 
					size="100" parentId="cal_weekDaysAlgorithmForm.pattern" property="saturday" label="Суббота" vocName="workCalendarDayPattern" horizontalFill="true" />
				</msh:row>
				<msh:row>
					<msh:autoComplete 
					 viewAction="entityParentView-cal_dayPattern.do" shortViewAction="entityShortView-cal_dayPattern.do" 
					size="100" parentId="cal_weekDaysAlgorithmForm.pattern" property="sunday" label="Воскресенье" vocName="workCalendarDayPattern" horizontalFill="true" />
				</msh:row>
				<msh:row>
					<msh:autoComplete property="parity" label="Тип дня" vocName="vocDayParity" horizontalFill="true"/>
				</msh:row>
				<msh:row>
					<msh:autoComplete size="100" property="calendarParity" label="Тип четности" vocName="vocWorkCalendarParity" horizontalFill="true" />
				</msh:row>
			<msh:submitCancelButtonsRow colSpan="2" />
			</msh:panel>
		</msh:form>
  </tiles:put>
</tiles:insert>