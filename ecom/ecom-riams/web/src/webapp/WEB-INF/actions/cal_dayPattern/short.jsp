<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://struts.apache.org/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://www.nuzmsh.ru/tags/msh" prefix="msh" %>
<%@ taglib uri="http://www.ecom-ast.ru/tags/ecom" prefix="ecom" %>
<%@ taglib uri="/WEB-INF/mis.tld" prefix="mis" %>
<%@ taglib tagdir="/WEB-INF/tags" prefix="tags" %>

<tiles:insert page="/WEB-INF/tiles/mainShortLayout.jsp" flush="true">

  <tiles:put name="body" type="string">
		<msh:form action="/entityParentSaveGoView-cal_dayPattern.do" defaultField="name">
			<msh:hidden property="id" />
			<msh:hidden property="saveType" />
			<msh:hidden property="lpu" />
			<msh:panel>
				<msh:row>
					<msh:textField property="name" label="Название" size="100"/>
				</msh:row>
				<msh:row>
					<msh:autoComplete property="workFunction" vocName="workFunctionByDirect" size="100"/>
				</msh:row>
				<msh:row>
					<msh:autoComplete property="workBusy" label="Тип занятости" vocName="vocWorkBusy" horizontalFill="true"  size="100"/>
				</msh:row>
			<msh:submitCancelButtonsRow colSpan="2" />
			</msh:panel>
		</msh:form>
		<msh:ifFormTypeIsView formName="cal_dayPatternForm">
			<msh:section title="Шаблоны интервалов времен">
			<ecom:webQuery name="timePatterns" nativeSql="
			select wctp.id,vwb.name as vwbname, wctp.timeFrom,wctp.timeTo,wctp.visitTime
			from WorkCalendarTimePattern wctp
			left join VocWorkBusy vwb on vwb.id=wctp.workBusy_id
			where wctp.dayPattern_id = ${param.id} and wctp.dtype='WorkCalendarTimeInterval'
			" />
				<msh:table  name="timePatterns" action="entitySubclassView-cal_timePattern.do" idField="1">
					<msh:tableColumn columnName="##" property="sn"/>
					<msh:tableColumn columnName="Занятость" property="2"/>
					<msh:tableColumn columnName="Интервал с" property="3"/>
					<msh:tableColumn columnName="по" property="4"/>
					<msh:tableColumn columnName="Время визита" property="5"/>
				</msh:table>
			</msh:section>
			<msh:section title="Шаблоны сгенерированные времена">
			<ecom:webQuery name="timePatterns" nativeSql="
			select wctp.id,vwb.name as vwbname, wctp.calendarTime
			from WorkCalendarTimePattern wctp
			left join VocWorkBusy vwb on vwb.id=wctp.workBusy_id
			where wctp.dayPattern_id = ${param.id} and wctp.dtype='WorkCalendarTimeExample'
			" />
				<msh:table deleteUrl="entityParentDeleteGoParentView-cal_timeExample.do" editUrl="entityParentEdit-cal_timeExample.do" name="timePatterns" action="entitySubclassView-cal_timePattern.do" idField="1">
					<msh:tableColumn columnName="##" property="sn"/>
					<msh:tableColumn columnName="Время приема" property="3"/>
				</msh:table>
			</msh:section>
		</msh:ifFormTypeIsView>

  </tiles:put>
</tiles:insert>

