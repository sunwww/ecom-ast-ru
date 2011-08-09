<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://struts.apache.org/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://www.nuzmsh.ru/tags/msh" prefix="msh" %>
<%@ taglib uri="http://www.ecom-ast.ru/tags/ecom" prefix="ecom" %>
<%@ taglib tagdir="/WEB-INF/tags" prefix="tags" %>

<tiles:insert page="/WEB-INF/tiles/mainLayout.jsp" flush="true">
	<tiles:put name="body" type="string">
		<msh:form action="/entityParentSaveGoView-contract_contractAccountRule.do" defaultField="discount">
			<msh:hidden property="id" />
			<msh:hidden property="saveType" />
			<msh:hidden property="parent" />
			<msh:panel>
				<msh:row>
					<msh:textField property="discount" label="Скидка"/>
				</msh:row>
				<msh:row>
					<msh:textField property="paymentLimit" label="Предел платежей"/>
				</msh:row>
				<msh:row>
					<msh:textField property="blockLimit" label="Предел блокирования платежей"/>
				</msh:row>
				<msh:row>
					<msh:checkBox property="autocreateAccount" label="Автоматическое создание счета"/>
				</msh:row>
				<msh:row>
					<msh:textField property="dateFrom" label="Дата начала действия"/>
				</msh:row>
				<msh:row>
					<msh:textField property="dateTo" label="Дата окончания действия"/>
				</msh:row>
				<msh:row>
					<msh:autoComplete property="contract" label="Договор" vocName="MedContract" horizontalFill="true" />
				</msh:row>
				<msh:row>
					<msh:autoComplete property="servedPerson" label="Обслуживаемая персона" vocName="ServedPerson" horizontalFill="true" />
				</msh:row>
			<msh:submitCancelButtonsRow colSpan="4" />
			</msh:panel>
		</msh:form>
		<msh:ifFormTypeIsView formName="contract_contractAccountRuleForm">
		</msh:ifFormTypeIsView>
	</tiles:put>
	<tiles:put name="title" type="string">
		<ecom:titleTrail mainMenu="Patient" beginForm="contract_contractAccountRuleForm" />
	</tiles:put>
	<tiles:put name="side" type="string">
		<msh:ifFormTypeAreViewOrEdit formName="contract_contractAccountRuleForm">
			<msh:sideMenu>
				<msh:sideLink key="ALT+2" params="id" action="/entityParentEdit-contract_contractAccountRule" name="Изменить" title="Изменить" roles="/Policy/Mis/Contract/MedContract/ContractAccountRule/Edit"/>
				<msh:sideLink key="ALT+DEL" params="id" action="/entityParentDelete-contract_contractAccountRule" name="Удалить" title="Удалить" roles="/Policy/Mis/Contract/MedContract/ContractAccountRule/Delete"/>
			</msh:sideMenu>
			<tags:contractMenu currentAction="medContract"/>
		</msh:ifFormTypeAreViewOrEdit>
	</tiles:put>
</tiles:insert>
