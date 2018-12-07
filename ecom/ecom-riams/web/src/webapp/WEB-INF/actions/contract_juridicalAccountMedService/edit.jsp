
<%@page import="WebQueryResult"%>

<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://struts.apache.org/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://www.nuzmsh.ru/tags/msh" prefix="msh" %>
<%@ taglib uri="http://www.ecom-ast.ru/tags/ecom" prefix="ecom" %>
<%@ taglib tagdir="/WEB-INF/tags" prefix="tags" %>

<tiles:insert page="/WEB-INF/tiles/main${param.short}Layout.jsp" flush="true">
	<tiles:put name="javascript" type="string">
		
	</tiles:put>
	<tiles:put name="body" type="string">
		<msh:form action="/entityParentSaveGoParentView-contract_juridicalAccountMedService.do" defaultField="dateFrom" title="<a href='entityParentView-contract_juridicalAccount.do?id=${param.id}'>Счет</a>" >
			<msh:hidden property="id" />
			<msh:hidden property="saveType" />
			<msh:hidden property="account" />
			<msh:panel colsWidth="1%,1%,1%">
				<msh:row>
					<msh:textField property="dateFrom" label="Период с"/>
					<msh:textField property="dateTo" label="по"/>
				</msh:row>
				<msh:row>
					<msh:autoComplete label="Пациент" property="patient" fieldColSpan="3" vocName="patient" horizontalFill="true"/>
				</msh:row>
				<msh:row>
					<msh:textField property="lastname"/>
					<msh:textField property="firstname"/>
				</msh:row>
				<msh:row>
					<msh:textField property="middlename"/>
					<msh:textField property="birthday"/>
				</msh:row>
				<msh:row>
					<msh:textField property="polSeries"/>
					<msh:textField property="polNumber"/>
				</msh:row>
				
				<msh:row>
					<msh:autoComplete property="medService" label="Позиция прейскуранта" vocName="priceMedServiceByJuridicalContractAccout" parentId="contract_juridicalAccountMedServiceForm.account" fieldColSpan="3" horizontalFill="true"/>
				</msh:row>
				<msh:row>
					<msh:textField property="countMedService" label="Кол-во услуг"/>
				</msh:row>
				<msh:row>
					<msh:autoComplete property="doctor" label="Рабочая функция" vocName="workFunction" fieldColSpan="3" horizontalFill="true"/>
				</msh:row>
				<msh:row>
					<msh:autoComplete property="diagnosis" vocName="vocIdc10" label="Диагноз" fieldColSpan="3" horizontalFill="true"/>
				</msh:row>
				<msh:row>
					<msh:checkBox property="isDeath" label="Смерть"/>
				</msh:row>
			<msh:submitCancelButtonsRow colSpan="4" />
			</msh:panel>
		</msh:form>
		
	</tiles:put>
	<tiles:put name="title" type="string">
		<ecom:titleTrail mainMenu="Contract" beginForm="contract_juridicalAccountMedServiceForm" />
	</tiles:put>
	<tiles:put name="side" type="string">
		<msh:ifFormTypeAreViewOrEdit formName="contract_juridicalAccountMedServiceForm">
			<msh:sideMenu>
				<msh:sideLink key="ALT+2" params="id" action="/entityParentEdit-contract_juridicalAccountMedService" name="Изменить" title="Изменить" roles="/Policy/Mis/Contract/MedContract/ServedPerson/ContractAccount/JuridicalMedService/Create"/>
				<msh:sideLink key="ALT+DEL" confirm="Вы точно хотите удалить счет?" params="id" action="/entityParentDeleteGoParentView-contract_juridicalAccountMedService" name="Удалить" title="Удалить" roles="/Policy/Mis/Contract/MedContract/ServedPerson/ContractAccount/JuridicalMedService/Delete"/>
			</msh:sideMenu>

			<tags:contractMenu currentAction="medContract"/>
		</msh:ifFormTypeAreViewOrEdit>
	</tiles:put>
</tiles:insert>
