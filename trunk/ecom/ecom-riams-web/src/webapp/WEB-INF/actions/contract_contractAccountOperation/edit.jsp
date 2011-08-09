<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://struts.apache.org/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://www.nuzmsh.ru/tags/msh" prefix="msh" %>
<%@ taglib uri="http://www.ecom-ast.ru/tags/ecom" prefix="ecom" %>
<%@ taglib tagdir="/WEB-INF/tags" prefix="tags" %>

<tiles:insert page="/WEB-INF/tiles/mainLayout.jsp" flush="true">
	<tiles:put name="body" type="string">
		<msh:form action="/entityParentSaveGoView-contract_contractAccountOperation.do" defaultField="typeName">
			<msh:hidden property="id" />
			<msh:hidden property="saveType" />
			<msh:hidden property="account" />
			<msh:panel>
				<msh:row>
					<msh:autoComplete property="type" label="Тип операции" vocName="VocAccountOperation" horizontalFill="true" />
				</msh:row>
				<msh:row>
					<msh:textField property="operationDate" label="Дата"/>
				</msh:row>
				<msh:row>
					<msh:textField property="operationTime" label="Время операции"/>
				</msh:row>
				<msh:row>
					<msh:textField property="cost" label="Стоимость"/>
				</msh:row>
				<msh:row>
					<msh:autoComplete property="medcase" label="Случай медицинского обслуживания" vocName="MedCase" horizontalFill="true" />
				</msh:row>
				<msh:row>
					<msh:autoComplete property="repealOperation" label="Отменившая операция" vocName="ContractAccountOperation" horizontalFill="true" />
				</msh:row>
				<msh:row>
					<msh:autoComplete property="workFunction" label="Оператор" vocName="workFunction" horizontalFill="true" />
				</msh:row>
				<msh:row>
					<msh:textField property="discount" label="Скидка"/>
				</msh:row>
			<msh:submitCancelButtonsRow colSpan="4" />
			</msh:panel>
		</msh:form>
		<msh:ifFormTypeIsView formName="contract_contractAccountOperationForm">
		</msh:ifFormTypeIsView>
	</tiles:put>
	<tiles:put name="title" type="string">
		<ecom:titleTrail mainMenu="Patient" beginForm="contract_contractAccountOperationForm" />
	</tiles:put>
	<tiles:put name="side" type="string">
		<msh:ifFormTypeAreViewOrEdit formName="contract_contractAccountOperationForm">
			<msh:sideMenu>
				<msh:sideLink key="ALT+2" params="id" action="/entityParentEdit-contract_contractAccountOperation" name="Изменить" title="Изменить" roles="/Policy/Mis/Contract/MedContract/ServedPerson/ContractAccount/ContractAccountOperation/Edit"/>
				<msh:sideLink key="ALT+DEL" params="id" action="/entityParentDelete-contract_contractAccountOperation" name="Удалить" title="Удалить" roles="/Policy/Mis/Contract/MedContract/ServedPerson/ContractAccount/ContractAccountOperation/Delete"/>
			</msh:sideMenu>
			<tags:contractMenu currentAction="medContract"/>
		</msh:ifFormTypeAreViewOrEdit>
	</tiles:put>
</tiles:insert>
