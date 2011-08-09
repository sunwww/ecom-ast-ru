<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://struts.apache.org/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://www.nuzmsh.ru/tags/msh" prefix="msh" %>
<%@ taglib uri="http://www.ecom-ast.ru/tags/ecom" prefix="ecom" %>
<%@ taglib tagdir="/WEB-INF/tags" prefix="tags" %>

<tiles:insert page="/WEB-INF/tiles/mainLayout.jsp" flush="true" >
	<tiles:put name='title' type='string'>
		<ecom:titleTrail mainMenu="Lpu" beginForm="contract_contractAccountRuleForm" title="Список Правило договорного счета"/>
	</tiles:put>
	<tiles:put name='side' type='string'>
		<msh:sideMenu title="Добавить">
			<msh:sideLink key='ALT+N' roles="/Policy/Mis/Contract/MedContract/ContractAccountRule/Create" params="" action="/entityPrepareCreate-contract_contractAccountRule" title="Правило договорного счета" name="Правило договорного счета" />
		</msh:sideMenu>
		<tags:contractMenu currentAction="medContract"/>
	</tiles:put>
	<tiles:put name='body' type='string' >
		<msh:table name="list" action="entityView-contract_contractAccountRule.do" idField="id">
			<msh:tableColumn columnName="Скидка" property="discount" />
			<msh:tableColumn columnName="Предел платежей" property="paymentLimit" />
			<msh:tableColumn columnName="Предел блокирования платежей" property="blockLimit" />
			<msh:tableColumn columnName="Автоматическое создание счета" property="autocreateAccount" />
			<msh:tableColumn columnName="Дата начала действия" property="dateFrom" />
			<msh:tableColumn columnName="Дата окончания действия" property="dateTo" />
			<msh:tableColumn columnName="Обслуживаемая персона" property="servedPerson" />
		</msh:table>
	</tiles:put>
</tiles:insert>
