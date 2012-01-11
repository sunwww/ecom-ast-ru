<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://struts.apache.org/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://www.nuzmsh.ru/tags/msh" prefix="msh" %>
<%@ taglib uri="http://www.ecom-ast.ru/tags/ecom" prefix="ecom" %>
<%@ taglib tagdir="/WEB-INF/tags" prefix="tags" %>

<tiles:insert page="/WEB-INF/tiles/mainLayout.jsp" flush="true" >
	<tiles:put name='title' type='string'>
		<ecom:titleTrail mainMenu="Contract" beginForm="contract_contractAccountForm" title="Список операций по счету"/>
	</tiles:put>
	<tiles:put name='side' type='string'>
		<msh:sideMenu title="Добавить">
			<msh:sideLink key='ALT+N' roles="/Policy/Mis/Contract/MedContract/ServedPerson/ContractAccount/ContractAccountOperation/Create" params="id" action="/entityParentPrepareCreate-contract_contractAccountOperation" title="Операция договорного счета" name="Операция договорного счета" />
		</msh:sideMenu>
		<tags:contractMenu currentAction="medContract"/>
	</tiles:put>
	<tiles:put name='body' type='string' >
		<msh:table name="list" action="entityView-contract_contractAccountOperation.do" idField="id">
			<msh:tableColumn columnName="Тип операции" property="type" />
			<msh:tableColumn columnName="Дата" property="operationDate" />
			<msh:tableColumn columnName="Время операции" property="operationTime" />
			<msh:tableColumn columnName="Стоимость" property="cost" />
			<msh:tableColumn columnName="Случай медицинского обслуживания" property="medcase" />
			<msh:tableColumn columnName="Отменившая операция" property="repealOperation" />
			<msh:tableColumn columnName="Оператор" property="workFunction" />
			<msh:tableColumn columnName="Скидка" property="discount" />
		</msh:table>
	</tiles:put>
</tiles:insert>