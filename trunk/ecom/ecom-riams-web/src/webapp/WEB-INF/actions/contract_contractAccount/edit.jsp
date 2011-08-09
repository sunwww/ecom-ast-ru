<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://struts.apache.org/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://www.nuzmsh.ru/tags/msh" prefix="msh" %>
<%@ taglib uri="http://www.ecom-ast.ru/tags/ecom" prefix="ecom" %>
<%@ taglib tagdir="/WEB-INF/tags" prefix="tags" %>

<tiles:insert page="/WEB-INF/tiles/mainLayout.jsp" flush="true">
	<tiles:put name="body" type="string">
		<msh:form action="/entityParentSaveGoView-contract_contractAccount.do" defaultField="dateFrom">
			<msh:hidden property="id" />
			<msh:hidden property="saveType" />
			<msh:hidden property="servedPerson" />
			<msh:panel>
				<msh:row>
					<msh:textField property="dateFrom" label="Дата открытия"/>
					<msh:textField property="dateTo" label="Дата закрытия"/>
				</msh:row>
				<msh:row>
					<msh:textField property="balanceSum" label="Сумма баланса"/>
					<msh:textField property="reservationSum" label="Резервированная сумма"/>
				</msh:row>
				<msh:row>
					<msh:checkBox property="block" label="Блокирован"/>
				</msh:row>
			<msh:submitCancelButtonsRow colSpan="4" />
			</msh:panel>
		</msh:form>
		<msh:ifFormTypeIsView formName="contract_contractAccountForm">
			<msh:section title="Операции по счету">
			<ecom:parentEntityListAll formName="contract_contractAccountOperationForm" attribute="account" />
				<msh:table name="account" action="entityParentView-contract_contractAccountOperation.do" idField="id">
					<msh:tableColumn columnName="Тип операции" property="type" />
					<msh:tableColumn columnName="Дата" property="operationDate" />
					<msh:tableColumn columnName="Время операции" property="operationTime" />
					<msh:tableColumn columnName="Стоимость" property="cost" />
					<msh:tableColumn columnName="Случай медицинского обслуживания" property="medcase" />
					<msh:tableColumn columnName="Отменившая операция" property="repealOperation" />
					<msh:tableColumn columnName="Оператор" property="workFunction" />
					<msh:tableColumn columnName="Скидка" property="discount" />
				</msh:table>
			</msh:section>
		</msh:ifFormTypeIsView>
	</tiles:put>
	<tiles:put name="title" type="string">
		<ecom:titleTrail mainMenu="Patient" beginForm="contract_contractAccountForm" />
	</tiles:put>
	<tiles:put name="side" type="string">
		<msh:ifFormTypeAreViewOrEdit formName="contract_contractAccountForm">
			<msh:sideMenu>
				<msh:sideLink key="ALT+2" params="id" action="/entityParentEdit-contract_contractAccount" name="Изменить" title="Изменить" roles="/Policy/Mis/Contract/MedContract/ServedPerson/ContractAccount/Edit"/>
				<msh:sideLink key="ALT+DEL" params="id" action="/entityParentDelete-contract_contractAccount" name="Удалить" title="Удалить" roles="/Policy/Mis/Contract/MedContract/ServedPerson/ContractAccount/Delete"/>
			</msh:sideMenu>
			<msh:sideMenu title="Добавить" >
				<msh:sideLink key="ALT+N" params="id" action="/entityParentPrepareCreate-contract_contractAccountOperation" name="Операции по счету" title="Операции по счету" roles="/Policy/Mis/Contract/MedContract/ServedPerson/ContractAccount/ContractAccountOperation/Create"/>
			</msh:sideMenu>
			<tags:contractMenu currentAction="medContract"/>
		</msh:ifFormTypeAreViewOrEdit>
	</tiles:put>
</tiles:insert>
