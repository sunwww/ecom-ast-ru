<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://struts.apache.org/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://www.nuzmsh.ru/tags/msh" prefix="msh" %>
<%@ taglib uri="http://www.ecom-ast.ru/tags/ecom" prefix="ecom" %>
<%@ taglib tagdir="/WEB-INF/tags" prefix="tags" %>

<tiles:insert page="/WEB-INF/tiles/mainLayout.jsp" flush="true" >
	<tiles:put name='title' type='string'>
		<ecom:titleTrail mainMenu="Lpu" beginForm="contract_medContractForm" title="Список обслуживаемых персон"/>
	</tiles:put>
	<tiles:put name='side' type='string'>
		<msh:sideMenu title="Добавить">
			<msh:sideLink key='ALT+N' roles="/Policy/Mis/Contract/MedContract/ServedPerson/Create" params="" action="/entityPrepareCreate-contract_servedPerson" title="Обслуживаемая персона" name="Обслуживаемая персона" />
		</msh:sideMenu>
		<tags:contractMenu currentAction="medContract"/>
	</tiles:put>
	<tiles:put name='body' type='string' >
		<msh:table name="list" action="entityParentView-contract_servedPerson.do" idField="id">
			<msh:tableColumn columnName="Договорная персона" property="person" />
			<msh:tableColumn columnName="Дата начала обслуживания" property="dateFrom" />
			<msh:tableColumn columnName="Дата окончания обслуживания" property="dateTo" />
		</msh:table>
	</tiles:put>
</tiles:insert>
