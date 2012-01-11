<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://struts.apache.org/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://www.nuzmsh.ru/tags/msh" prefix="msh" %>
<%@ taglib uri="http://www.ecom-ast.ru/tags/ecom" prefix="ecom" %>
<%@ taglib tagdir="/WEB-INF/tags" prefix="tags" %>

<tiles:insert page="/WEB-INF/tiles/mainLayout.jsp" flush="true">
	<tiles:put name="body" type="string">
		<msh:form action="/entitySaveGoView-contract_priceList.do" defaultField="name">
			<msh:hidden property="id" />
			<msh:hidden property="saveType" />
			<msh:panel>
				<msh:row>
					<msh:textField property="name" label="Название" fieldColSpan="3" horizontalFill="true"/>
				</msh:row>
				<msh:row>
					<msh:autoComplete property="vocPrice" label="Справочник типов цен" vocName="vocPrice" horizontalFill="true" fieldColSpan="3"/>
				</msh:row>
				<msh:row>
					<msh:textField property="dateFrom" label="Дата начала действия"/>
					<msh:textField property="dateTo" label="Дата окончания"/>
				</msh:row>
			<msh:submitCancelButtonsRow colSpan="4" />
			</msh:panel>
		</msh:form>
		<msh:ifFormTypeIsView formName="contract_priceListForm">
			<msh:section title="Позиции прейскуранта">
			<ecom:parentEntityListAll formName="contract_pricePositionForm" attribute="priceList" />
				<msh:table name="priceList" action="entityParentView-contract_pricePosition.do" idField="id">
					<msh:tableColumn columnName="#" property="sn"/>
					<msh:tableColumn columnName="Код" property="code"/>
					<msh:tableColumn columnName="Наименование" property="name"/>
					<msh:tableColumn columnName="Дата начала действия" property="dateFrom"/>
					<msh:tableColumn columnName="Дата окончания" property="dateTo"/>
					<msh:tableColumn columnName="Цена" property="cost"/>
				</msh:table>
			</msh:section>
		</msh:ifFormTypeIsView>
	</tiles:put>
	<tiles:put name="title" type="string">
		<ecom:titleTrail mainMenu="Contract" beginForm="contract_priceListForm" />
	</tiles:put>
	<tiles:put name="side" type="string">
		<msh:sideMenu>
			<msh:sideLink key="ALT+2" params="id" action="/entityEdit-contract_priceList" name="Изменить" title="Изменить" roles="/Policy/Mis/Contract/PriceList/Edit"/>
			<msh:sideLink key="ALT+DEL" params="id" action="/entityDelete-contract_priceList" name="Удалить" title="Удалить" roles="/Policy/Mis/Contract/PriceList/Delete"/>
		</msh:sideMenu>
		<msh:sideMenu title="Добавить" >
			<msh:sideLink key="ALT+N" params="id" action="/entityParentPrepareCreate-contract_pricePosition" name="Позиции прейскуранта" title="Позиции прейскуранта" roles="/Policy/Mis/Contract/PriceList/PricePosition/Create"/>
		</msh:sideMenu>
		<tags:contractMenu currentAction="price"/>
	</tiles:put>
</tiles:insert>
