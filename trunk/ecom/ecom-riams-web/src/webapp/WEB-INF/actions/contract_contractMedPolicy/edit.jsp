<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://struts.apache.org/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://www.nuzmsh.ru/tags/msh" prefix="msh" %>
<%@ taglib uri="http://www.ecom-ast.ru/tags/ecom" prefix="ecom" %>
<%@ taglib tagdir="/WEB-INF/tags" prefix="tags" %>

<tiles:insert page="/WEB-INF/tiles/mainLayout.jsp" flush="true">
	<tiles:put name="body" type="string">
		<msh:form action="/entityParentSaveGoView-contract_contractMedPolicy.do" defaultField="">
			<msh:hidden property="id" />
			<msh:hidden property="saveType" />
			<msh:hidden property="contract" />
			<msh:panel>
				<msh:row>
					<msh:autoComplete fieldColSpan="3" property="medPolicy" label="Медицинский полис" vocName="medPolicy" horizontalFill="true" />
				</msh:row>
				<msh:row>
					<msh:textField property="lastname" label="Фамилия"/>
					<msh:textField property="firstname" label="Имя"/>
				</msh:row>
				<msh:row>
					<msh:textField property="middlename" label="Отчество"/>
					<msh:textField property="birthday" label="День рождения"/>
				</msh:row>
				<msh:row>
					<msh:textField property="series" label="Серия"/>
					<msh:textField property="number" label="Номер"/>
				</msh:row>
				<msh:row>
					<msh:autoComplete property="serviceProgram" label="Программа обслуживания" vocName="vocServiceProgram" horizontalFill="true" fieldColSpan="3"/>
				</msh:row>
				<msh:row>
					<msh:autoComplete property="servedPersonStatus" label="Статус обслуживаемой персоны" vocName="vocServedPersonStatus" horizontalFill="true" fieldColSpan="3"/>
				</msh:row>
				<msh:row>
					<msh:autoComplete property="territory" label="Территория" vocName="omcKodTer" horizontalFill="true" fieldColSpan="3"/>
				</msh:row>
				<msh:row>
					<msh:textField property="dateFrom" label="Дата начала действия"/>
					<msh:textField property="dateTo" label="Дата окончания"/>
				</msh:row>
				<msh:row>
					<msh:textField property="nullityDate" label="Дата объявления недействительности"/>
				</msh:row>
			<msh:submitCancelButtonsRow colSpan="4" />
			</msh:panel>
		</msh:form>
		<msh:ifFormTypeIsView formName="contract_contractMedPolicyForm">
		</msh:ifFormTypeIsView>
	</tiles:put>
	<tiles:put name="title" type="string">
		<ecom:titleTrail mainMenu="Patient" beginForm="contract_contractMedPolicyForm" />
	</tiles:put>
	<tiles:put name="side" type="string">
		<msh:ifFormTypeAreViewOrEdit formName="contract_contractMedPolicyForm">
			<msh:sideMenu>
				<msh:sideLink key="ALT+2" params="id" action="/entityParentEdit-contract_contractMedPolicy" name="Изменить" title="Изменить" roles="/Policy/Mis/Contract/MedContract/ContractGuarantee/ContractMedPolicy/Edit"/>
				<msh:sideLink key="ALT+DEL" params="id" action="/entityParentDelete-contract_contractMedPolicy" name="Удалить" title="Удалить" roles="/Policy/Mis/Contract/MedContract/ContractGuarantee/ContractMedPolicy/Delete"/>
			</msh:sideMenu>
			<tags:contractMenu currentAction="medContract"/>
		</msh:ifFormTypeAreViewOrEdit>
	</tiles:put>
</tiles:insert>
