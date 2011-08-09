<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://struts.apache.org/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://www.nuzmsh.ru/tags/msh" prefix="msh" %>
<%@ taglib uri="http://www.ecom-ast.ru/tags/ecom" prefix="ecom" %>
<%@ taglib tagdir="/WEB-INF/tags" prefix="tags" %>

<tiles:insert page="/WEB-INF/tiles/mainLayout.jsp" flush="true" >
	<tiles:put name='title' type='string'>
		<ecom:titleTrail mainMenu="Lpu" beginForm="contract_contractAccountForm" title="Список Договорной счет"/>
	</tiles:put>
	<tiles:put name='side' type='string'>
		<msh:sideMenu title="Добавить">
			<msh:sideLink key='ALT+N' roles="/Policy/Mis/Contract/MedContract/ServedPerson/ContractAccount/Create" params="" action="/entityPrepareCreate-contract_contractAccount" title="Договорной счет" name="Договорной счет" />
		</msh:sideMenu>
		<tags:contractMenu currentAction="medContract"/>
	</tiles:put>
	<tiles:put name='body' type='string' >
		<msh:table name="list" action="entityView-contract_contractAccount.do" idField="id">
			<msh:tableColumn columnName="Обслуживаемая персона" property="servedPerson" />
			<msh:tableColumn columnName="Операции по счету" property="operations" />
			<msh:tableColumn columnName="Сумма баланса" property="balanceSum" />
			<msh:tableColumn columnName="Резервированная сумма" property="reservationSum" />
			<msh:tableColumn columnName="Дата открытия" property="dateFrom" />
			<msh:tableColumn columnName="Дата закрытия" property="dateTo" />
			<msh:tableColumn columnName="Блокирован" property="block" />
		</msh:table>
	</tiles:put>
</tiles:insert>
