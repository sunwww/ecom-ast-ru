<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://struts.apache.org/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://www.nuzmsh.ru/tags/msh" prefix="msh" %>
<%@ taglib uri="http://www.ecom-ast.ru/tags/ecom" prefix="ecom" %>
<%@ taglib tagdir="/WEB-INF/tags" prefix="tags" %>

<tiles:insert page="/WEB-INF/tiles/mainLayout.jsp" flush="true">
	<tiles:put name="body" type="string">
		<msh:form action="/entityParentSaveGoView-contract_servedPerson.do" defaultField="personName">
			<msh:hidden property="id" />
			<msh:hidden property="saveType" />
			<msh:hidden property="contract" />
			<msh:hidden property="createDate" />
			<msh:hidden property="createTime" />
			<msh:hidden property="createUsername" />
			<msh:hidden property="editDate" />
			<msh:hidden property="editTime" />
			<msh:hidden property="editUsername" />
			<msh:panel colsWidth="1%,1%,1%">
				<msh:row>
					<msh:autoComplete fieldColSpan="3" property="person" label="Договорная персона" vocName="contractPerson" size="100" />
				</msh:row>
				<msh:row>
					<msh:textField property="dateFrom" label="Дата начала обслуживания"/>
					<msh:textField property="dateTo" label="Дата окончания"/>
				</msh:row>
			
				<msh:row>
					<msh:checkBox fieldColSpan="3" property="autoAccount" label="Автосоздание счета" horizontalFill="true"/>
				</msh:row>
				<msh:ifFormTypeIsView formName="contract_servedPersonForm">
				<msh:row>
					<msh:separator label="Информация о создании" colSpan="4"/>
				</msh:row>
				<msh:row>
					<msh:textField property="createDate" label="Дата"/>
					<msh:textField property="createTime" label="Время"/>
				</msh:row>
				<msh:row>
					<msh:textField property="createUsername" label="Пользователь"/>
				</msh:row>
				<msh:row>
					<msh:separator label="Информация о последней редакции" colSpan="4"/>
				</msh:row>
				<msh:row>
					<msh:textField property="editDate" label="Дата"/>
					<msh:textField property="editTime" label="Время"/>
				</msh:row>
				<msh:row>
					<msh:textField property="editUsername" label="Пользователь"/>
				</msh:row>
				</msh:ifFormTypeIsView>
			<msh:submitCancelButtonsRow colSpan="3" />
			</msh:panel>
		</msh:form>
		<msh:ifFormTypeIsView formName="contract_servedPersonForm">
		<msh:section title="Счет">
			<ecom:webQuery name="contractAccount" nativeSql="
							select id, datefrom  from ContractAccount
							where servedperson_id = '${param.id}'
			"/>
				<msh:table name="contractAccount" action="entityParentView-contract_contractAccount.do" idField="1">
					<msh:tableColumn columnName="Номер счета" property="1" />					
				</msh:table>
			</msh:section>
		</msh:ifFormTypeIsView>
	</tiles:put>
	<tiles:put name="title" type="string">
		<ecom:titleTrail mainMenu="Contract" beginForm="contract_servedPersonForm" />
	</tiles:put>
	<tiles:put name="side" type="string">
		<msh:ifFormTypeAreViewOrEdit formName="contract_servedPersonForm">
			<msh:sideMenu>
				<msh:sideLink key="ALT+2" params="id" action="/entityParentEdit-contract_servedPerson" name="Изменить" title="Изменить" roles="/Policy/Mis/Contract/MedContract/ServedPerson/Edit"/>
				<msh:sideLink key="ALT+DEL" params="id" action="/entityParentDelete-contract_servedPerson" name="Удалить" title="Удалить" roles="/Policy/Mis/Contract/MedContract/ServedPerson/Delete"/>
			</msh:sideMenu>
			<msh:sideMenu title="Добавить">
				<msh:sideLink name="Счет" action="/entityParentPrepareCreate-contract_contractAccount" key="ALT+3" params="id" roles="/Policy/Mis/Contract/MedContract/ServedPerson/ContractAccount/Create" title="Добавить счет"/>
			</msh:sideMenu>
			<tags:contractMenu currentAction="medContract"/>
		</msh:ifFormTypeAreViewOrEdit>
	</tiles:put>
	<tiles:put type="string" name="javascript">
		<msh:ifFormTypeIsCreate formName="contract_servedPersonForm">
			<script type="text/javascript">
				$('autoAccount').checked=true ;
			</script>
		</msh:ifFormTypeIsCreate>
	</tiles:put>
</tiles:insert>
