<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://struts.apache.org/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://www.nuzmsh.ru/tags/msh" prefix="msh" %>
<%@ taglib uri="http://www.ecom-ast.ru/tags/ecom" prefix="ecom" %>
<tiles:insert page="/WEB-INF/tiles/mainLayout.jsp" flush="true" >
	<tiles:put name='title' type='string'>
		<ecom:titleTrail mainMenu="Lpu" beginForm="mis_lpuForm" title="Список шаблонов дня рабочего календаря"/>
	</tiles:put>
	<tiles:put name='side' type='string'>
		<msh:sideMenu title="Добавить">
			<msh:sideLink key='ALT+N' roles="/Policy/Mis/Worker/WorkCalendar/Pattern/Day/Create" params="id" action="/entityParentPrepareCreate-cal_dayPattern" title="Шаблон дня рабочего календаря" name="Шаблон дня рабочего календаря" />
		</msh:sideMenu>
	</tiles:put>
	<tiles:put name='body' type='string' >
		<msh:table name="list" action="entityParentView-cal_dayPattern.do" idField="id">
			<msh:tableColumn columnName="#" property="sn" />
			<msh:tableColumn columnName="Название" property="name" />
			<msh:tableColumn columnName="Тип занятости" property="workBusy" />
		</msh:table>
	</tiles:put>
</tiles:insert>