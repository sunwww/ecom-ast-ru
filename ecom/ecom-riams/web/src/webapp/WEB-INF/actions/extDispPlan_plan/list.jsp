<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://struts.apache.org/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://www.nuzmsh.ru/tags/msh" prefix="msh" %>
<%@ taglib uri="http://www.ecom-ast.ru/tags/ecom" prefix="ecom" %>
<tiles:insert page="/WEB-INF/tiles/main${param.short}Layout.jsp" flush="true" >
	<tiles:put name='title' type='string'>
	</tiles:put>
	<tiles:put name='side' type='string'>
		<msh:sideMenu title="Добавить">
			<msh:sideLink key='ALT+N' roles="/Policy/Mis/ExtDisp/Card/Create" action="/entityPrepareCreate-extDispPlan_plan" title="Создать план ДД" name="Создать план ДД" />
		</msh:sideMenu>
	</tiles:put>
	<tiles:put name='body' type='string' >
	<msh:section createRoles="/Policy/Mis/ExtDisp/Card/Create" createUrl="entityPrepareCreate-extDispPlan_plan.do?id=${param.id}"
		title="Список планов ДД">
		<ecom:webQuery name="list" nativeSql="
		select id, year from extDispPlanPopulation
		order by year
		"/>
		<msh:table name="list" action="entityView-extDispPlan_plan.do" idField="1" >
			<msh:tableColumn columnName="#" property="sn" />
			<msh:tableColumn columnName="Год" property="2" />
		</msh:table>
	</msh:section>
</tiles:put>
</tiles:insert>
