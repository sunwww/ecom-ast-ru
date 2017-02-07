<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://struts.apache.org/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://www.nuzmsh.ru/tags/msh" prefix="msh" %>
<%@ taglib uri="http://www.ecom-ast.ru/tags/ecom" prefix="ecom" %>
<tiles:insert page="/WEB-INF/tiles/main${param.short}Layout.jsp" flush="true" >
	<tiles:put name='title' type='string'>
	
	<ecom:titleTrail mainMenu="Lpu" beginForm="extDisp_cardForm" title="Список назначений"/>
		
		
	</tiles:put>
	
	<tiles:put name='side' type='string'>
		<msh:sideMenu title="Добавить">
			<msh:sideLink key='ALT+N' roles="/Policy/Mis/ExtDisp/Card/Create" params="id" action="/entityParentPrepareCreate-extDisp_appointment.do" title="Назначение" name="Назначение" />
		</msh:sideMenu>
	</tiles:put>
	
	
	<tiles:put name='body' type='string' >
	<msh:section createRoles="/Policy/Mis/ExtDisp/Card/Create" createUrl="entityParentPrepareCreate-extDisp_appointment.do?id=${param.id}"
		title="Список назначений">
		<ecom:webQuery name="list" nativeSql="
		select ea.id as id, vapp.name as appoint, vodt.name as profile, vks.name as survay 
			from extdispappointment ea
			left join VocOmcDepType vodt on vodt.id = ea.profile_id
			left join VocExtDispAppointment vapp on vapp.id = ea.appointment_id
			left join VocKindSurvey vks on vks.id=ea.kindsurvey_id
			left join extdispcard dc on dc.id = ea.dispcard_id
			where dc.id=${param.id}"/>
		<msh:table name="list" action="entityView-extDisp_appointment.do" idField="1" >
			<msh:tableColumn columnName="#" property="sn" />
			<msh:tableColumn columnName="Назначение" property="2" />
			<msh:tableColumn columnName="Профиль" property="3"/>
			<msh:tableColumn columnName="Вид обследования" property="4"/>
		</msh:table>
	</msh:section>
</tiles:put>
</tiles:insert>