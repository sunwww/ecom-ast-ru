<%@page import="ru.ecom.web.login.LoginInfo"%>
<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib uri="http://struts.apache.org/tags-tiles" prefix="tiles"%>
<%@ taglib uri="http://www.nuzmsh.ru/tags/msh" prefix="msh"%>
<%@ taglib uri="http://www.ecom-ast.ru/tags/ecom" prefix="ecom" %>

<tiles:insert page="/WEB-INF/tiles/mainLayout.jsp" flush="true">

	<tiles:put name='title' type='string'>
		<msh:title mainMenu="Config">Смена рабочей функции по умолчанию</msh:title>
	</tiles:put>

	<tiles:put name='body' type='string'>
	
	<%
		String username = LoginInfo.find(request.getSession(true)).getUsername() ;
		request.setAttribute("username",username) ;
	%>
	<msh:ifInRole roles="/Policy/Jaas/SecUser/ReplaceWorkFunction">
	
		<ecom:webQuery name="workFunc" nativeSql="
		select wf.id as wfid,ml.name as mlname,vwf.name as vwfname,gwf.groupName as gwfgroupname
		,case when wf.id=swf.id then '&#9670;' else '' end wfdefault
from WorkFunction wf
left join WorkFunction gwf on gwf.id=wf.group_id
left join VocWorkFunction vwf on vwf.id=wf.workFunction_id
left join Worker w on w.id=wf.worker_id
left join MisLpu ml on ml.id=w.lpu_id
left join Worker sw on sw.person_id=w.person_id
left join WorkFunction swf on swf.worker_id=sw.id
left join SecUser su on su.id=swf.secUser_id
where su.login='${username}' and (wf.archival is null or wf.archival='0') 
order by ml.name
		"/>
		<msh:table name="workFunc" action="js-secuser-replaceWF.do" idField="1">
			<msh:tableColumn property="5" columnName=""/>
			<msh:tableColumn property="2" columnName="Отделение"/>
			<msh:tableColumn property="3" columnName="Рабочая функция"/>
			<msh:tableColumn property="4" columnName="Групповая функция"/>
		</msh:table>
	</msh:ifInRole>
	</tiles:put>
</tiles:insert>
