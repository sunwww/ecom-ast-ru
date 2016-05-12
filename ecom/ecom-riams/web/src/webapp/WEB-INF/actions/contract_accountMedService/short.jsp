<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://struts.apache.org/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://www.nuzmsh.ru/tags/msh" prefix="msh" %>
<%@ taglib uri="http://www.ecom-ast.ru/tags/ecom" prefix="ecom" %>
<%@ taglib tagdir="/WEB-INF/tags" prefix="tags" %>

<tiles:insert page="/WEB-INF/tiles/mainShortLayout.jsp" flush="true">
	<tiles:put name="body" type="string">
		<msh:form action="/entityParentSaveGoView-contract_contractAccountMedService.do" defaultField="typeName">
			<msh:hidden property="id" />
			<msh:hidden property="saveType" />
			<msh:hidden property="account" />
			<msh:panel>
				<msh:row>
					<msh:autoComplete property="medService" parentId="contract_contractAccountMedServiceForm.account" label="Услуга" vocName="priceMedServiceByContractAccout" fieldColSpan="3" horizontalFill="true" />
				</msh:row>
				<msh:row>
					<msh:autoComplete property="workFunction" label="Специалист" vocName="workFunction" fieldColSpan="3" horizontalFill="true" />
				</msh:row>
				<msh:row>
					<msh:textField property="cost" label="Цена" viewOnlyField="true"/>
					<msh:textField property="countMedService" label="Кол-во"/>
				</msh:row>
			<msh:submitCancelButtonsRow colSpan="4" />
			</msh:panel>
		</msh:form>
		<msh:ifFormTypeIsView formName="contract_contractAccountMedServiceForm">
		</msh:ifFormTypeIsView>
	</tiles:put>
	<tiles:put name="title" type="string">
		<ecom:titleTrail mainMenu="Contract" beginForm="contract_contractAccountMedServiceForm" />
	</tiles:put>
	<tiles:put name="side" type="string">
		<msh:ifFormTypeAreViewOrEdit formName="contract_contractAccountMedServiceForm">
			<msh:sideMenu>
				<msh:sideLink key="ALT+2" params="id" action="/entityParentEdit-contract_contractAccountMedService" name="Изменить" title="Изменить" roles="/Policy/Mis/Contract/MedContract/ServedPerson/ContractAccount/ContractAccountMedService/Edit"/>
				<msh:sideLink key="ALT+DEL" params="id" action="/entityParentDelete-contract_contractAccountMedservice" name="Удалить" title="Удалить" roles="/Policy/Mis/Contract/MedContract/ServedPerson/ContractAccount/ContractAccountMedService/Delete"/>
			</msh:sideMenu>
			<tags:contractMenu currentAction="medContract"/>
		</msh:ifFormTypeAreViewOrEdit>
	</tiles:put>
</tiles:insert>
