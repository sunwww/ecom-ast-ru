<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://struts.apache.org/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://www.nuzmsh.ru/tags/msh" prefix="msh" %>
<%@ taglib uri="http://www.ecom-ast.ru/tags/ecom" prefix="ecom" %>
<%@ taglib tagdir="/WEB-INF/tags" prefix="tags" %>

<tiles:insert page="/WEB-INF/tiles/mainLayout.jsp" flush="true">
	<tiles:put name="body" type="string">
		<msh:form action="/entitySaveGoView-contract_contractRule.do" defaultField="">
			<msh:hidden property="id" />
			<msh:hidden property="saveType" />
			<msh:hidden property="contract" />
			<msh:panel>
				<msh:row>
					<msh:separator label="Правила" colSpan="4"/>
				</msh:row>
				<msh:row>
					<msh:autoComplete viewAction="entityView-contract_contractNosologyGroup.do" property="nosologyGroup" label="Нозологическая группа" vocName="contractNosologyGroup" horizontalFill="true" fieldColSpan="3"/>
				</msh:row>
				<msh:row>
					<msh:autoComplete viewAction="entityView-contract_contractMedServiceGroup.do" property="medServiceGroup" label="Группа медицинских услуг" vocName="contractMedServiceGroup" horizontalFill="true"  fieldColSpan="3"/>
				</msh:row>
				<msh:row>
					<msh:autoComplete viewAction="entityView-contract_contractGuaranteeGroup.do" property="guaranteeGroup" label="Группа гарантийных документов" vocName="contractGuaranteeGroup" horizontalFill="true" fieldColSpan="3"/>
				</msh:row>
				<msh:row>
					<msh:autoComplete property="permission" label="Разрешение" vocName="vocContractPermission" horizontalFill="true" fieldColSpan="3"/>
				</msh:row>
				<msh:row>
					<msh:separator label="Количество" colSpan="4"/>
				</msh:row>
				<msh:row>
					<msh:textField property="medserviceAmount" label="Кол-во мед. услуг"/>
					<msh:textField property="courseAmount" label="Кол-во курсов"/>
				</msh:row>
				<msh:row>
					<msh:textField property="medserviceCourseAmount" label="Кол-во мед. услуг на курс" labelColSpan="3"/>
				</msh:row>
				<msh:row>
					<msh:separator label="Период действия" colSpan="4"/>
				</msh:row>
				<msh:row>
					<msh:autoComplete property="period" label="Период действия" vocName="vocContractRulePeriod" horizontalFill="true" fieldColSpan="3"/>
				</msh:row>
				<msh:row>
					<msh:textField property="dateFrom" label="Дата начала действия"/>
					<msh:textField property="dateTo" label="Дата окончания"/>
				</msh:row>
				<msh:row>
					<msh:autoComplete parentId="contract_contractRuleForm.contract" property="servedPerson" label="Обслуживаемая персона" vocName="servedPerson" horizontalFill="true" fieldColSpan="3"/>
				</msh:row>
			<msh:submitCancelButtonsRow colSpan="4" />
			</msh:panel>
		</msh:form>
		<msh:ifFormTypeIsView formName="contract_contractRuleForm">
		</msh:ifFormTypeIsView>
	</tiles:put>
	<tiles:put name="title" type="string">
		<ecom:titleTrail mainMenu="Contract" beginForm="contract_contractRuleForm" />
	</tiles:put>
	<tiles:put name="side" type="string">
		<msh:sideMenu>
			<msh:sideLink key="ALT+2" params="id" action="/entityParentEdit-contract_contractRule" name="Изменить" title="Изменить" roles=""/>
			<msh:sideLink key="ALT+DEL" params="id" action="/entityParentDelete-contract_contractRule" name="Удалить" title="Удалить" roles=""/>
		</msh:sideMenu>
	</tiles:put>
</tiles:insert>
