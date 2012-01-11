<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://struts.apache.org/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://www.nuzmsh.ru/tags/msh" prefix="msh" %>
<%@ taglib uri="http://www.ecom-ast.ru/tags/ecom" prefix="ecom" %>
<%@ taglib tagdir="/WEB-INF/tags" prefix="tags" %>

<tiles:insert page="/WEB-INF/tiles/mainLayout.jsp" flush="true" >
	<tiles:put name='title' type='string'>
		<ecom:titleTrail mainMenu="Contract" beginForm="contract_contractNosologyGroupForm" title="Список интервалов нозологий"/>
	</tiles:put>
	<tiles:put name='side' type='string'>
		<msh:sideMenu title="Добавить">
			<msh:sideLink key='ALT+N' roles="/Policy/Mis/Contract/GroupRules/ContractNosologyGroup/NosologyInterval/Create" params="id" action="/entityParentPrepareCreate-contract_nosologyInterval" title="Интервал нозологий" name="Интервал нозологий" />
		</msh:sideMenu>
		<tags:contractMenu currentAction="nosologyGroup"/>
	</tiles:put>
	<tiles:put name='body' type='string' >
		<msh:table name="list" action="entityParentView-contract_nosologyInterval.do" idField="id">
			<msh:tableColumn columnName="Наименование" property="name" />
			<msh:tableColumn columnName="Начиная с кода" property="fromIdc10Code" />
			<msh:tableColumn columnName="Заканчивая кодом" property="toIdc10Code" />
		</msh:table>
	</tiles:put>
</tiles:insert>
