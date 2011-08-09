<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://struts.apache.org/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://www.nuzmsh.ru/tags/msh" prefix="msh" %>
<%@ taglib uri="http://www.ecom-ast.ru/tags/ecom" prefix="ecom" %>
<%@ taglib tagdir="/WEB-INF/tags" prefix="tags" %>

<tiles:insert page="/WEB-INF/tiles/mainLayout.jsp" flush="true">
	<tiles:put name="body" type="string">
		<msh:form action="/entitySaveGoView-contract_juridicalPerson.do" defaultField="organizationName">
			<msh:hidden property="id" />
			<msh:hidden property="saveType" />
			<msh:panel>
			
				<msh:row>
					<msh:autoComplete fieldColSpan="3" property="organization" label="Организация" vocName="vocOrg" horizontalFill="true" />
				</msh:row>
				<msh:row>
					<msh:textField property="shortName" label="Короткое название" horizontalFill="true"/>
				</msh:row>
				<msh:row>
					<msh:textField fieldColSpan="3" property="name" label="Полное название" size="200"/>
				</msh:row>
				<msh:row>
					<msh:autoComplete fieldColSpan="3" property="juridicalPersonType" label="Тип юридической персоны" vocName="vocJuridicalPerson" horizontalFill="true" />
				</msh:row>
				<msh:row>
					<msh:autoComplete fieldColSpan="3" property="servedPersonStatus" label="Статус обслуживаемой персоны" vocName="vocServedPersonStatus" horizontalFill="true" />
				</msh:row>
				<msh:row>
					<msh:autoComplete fieldColSpan="3" property="serviceProgram" label="Программа обслуживания" vocName="vocServiceProgram" horizontalFill="true" />
				</msh:row>
				<msh:row>
					<msh:textField fieldColSpan="3" property="director" label="Директор" horizontalFill="true"/>
				</msh:row>
				<msh:row>
					<msh:textField property="kpp" label="КПП" horizontalFill="true"/>
					<msh:textField property="inn" label="ИНН" horizontalFill="true"/>
				</msh:row>
				<msh:row>
					<msh:textField property="bic" label="БИК" horizontalFill="true"/>
					<msh:textField property="account" label="Счет" horizontalFill="true"/>
				</msh:row>
				<msh:row>
					<msh:textField property="corAccount" label="Корреспондентский счет" horizontalFill="true"/>
				</msh:row>
				<msh:row>
					<msh:autoComplete fieldColSpan="3" property="territory" label="Территория" vocName="omcKodTer" horizontalFill="true" />
				</msh:row>
				<msh:row>
					<msh:textField fieldColSpan="3" property="postAddress" label="Почтовый адрес" horizontalFill="true"/>
				</msh:row>
				<msh:row>
					<msh:textField fieldColSpan="3" property="juridicalAddress" label="Юридический адрес" horizontalFill="true"/>
				</msh:row>
				<msh:row>
					<msh:textField property="fax" label="Факс" horizontalFill="true"/>
					<msh:textField property="phones" label="Телефоны" horizontalFill="true"/>
				</msh:row>
				<msh:row>
					<msh:textField property="email" label="Электронная почта" horizontalFill="true"/>
				</msh:row>
			<msh:submitCancelButtonsRow colSpan="2" />
			</msh:panel>
		</msh:form>
		<msh:ifFormTypeIsView formName="contract_juridicalPersonForm">
		</msh:ifFormTypeIsView>
	</tiles:put>
	<tiles:put name="title" type="string">
		<ecom:titleTrail mainMenu="Patient" beginForm="contract_juridicalPersonForm" />
	</tiles:put>
	<tiles:put name="side" type="string">
		<msh:sideMenu>
			<msh:sideLink key="ALT+2" params="id" action="/entityParentEdit-contract_juridicalPerson" name="Изменить" title="Изменить" roles="/Policy/Mis/Contract/ContractPerson/JuridicalPerson/Edit"/>
			<msh:sideLink key="ALT+DEL" params="id" action="/entityParentDelete-contract_juridicalPerson" name="Удалить" title="Удалить" roles="/Policy/Mis/Contract/ContractPerson/JuridicalPerson/Delete" confirm="Вы уверены?"/>
		</msh:sideMenu>
		<tags:contractMenu currentAction="juridicalPerson"/>

		</tiles:put>
</tiles:insert>
