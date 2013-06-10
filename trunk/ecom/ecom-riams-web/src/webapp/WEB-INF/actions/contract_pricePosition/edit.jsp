<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://struts.apache.org/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://www.nuzmsh.ru/tags/msh" prefix="msh" %>
<%@ taglib uri="http://www.ecom-ast.ru/tags/ecom" prefix="ecom" %>
<%@ taglib tagdir="/WEB-INF/tags" prefix="tags" %>

<tiles:insert page="/WEB-INF/tiles/mainLayout.jsp" flush="true">
	<tiles:put name="body" type="string">
		<msh:form action="/entityParentSaveGoView-contract_pricePosition.do" defaultField="name">
			<msh:hidden property="id" />
			<msh:hidden property="saveType" />
			<msh:hidden property="priceList" />
			<msh:panel>
				<msh:row>
					<msh:textField fieldColSpan="3" property="name" label="Название" horizontalFill="true"/>
				</msh:row>
				<msh:row>
					<msh:textField property="code" label="Код"/>
					<msh:textField property="cost" label="Цена"/>
				</msh:row>
				<msh:row>
					<msh:textField property="dateFrom" label="Дата начала действия"/>
					<msh:textField property="dateTo" label="Дата окончания действия"/>
				</msh:row>
			<msh:submitCancelButtonsRow colSpan="4" />
			</msh:panel>
		</msh:form>
		<msh:ifFormTypeIsView formName="contract_pricePositionForm">
			<msh:section title="Медицинские услуги">
			<ecom:parentEntityListAll formName="contract_priceMedServiceForm" attribute="pricePosition" />
				<msh:table name="pricePosition" action="entityParentView-contract_priceMedService.do" idField="id">
					<msh:tableColumn columnName="#" property="sn"/>
					<msh:tableColumn columnName="Мед. услуга" property="medServiceInfo"/>
					<msh:tableColumn columnName="Дата начала действия" property="dateFrom"/>
					<msh:tableColumn columnName="Дата окончания" property="dateTo"/>
				</msh:table>
			</msh:section>
		</msh:ifFormTypeIsView>
	</tiles:put>
	<tiles:put name="title" type="string">
		<ecom:titleTrail mainMenu="Contract" beginForm="contract_pricePositionForm" />
	</tiles:put>
	<tiles:put name="side" type="string">
		<msh:sideMenu>
			<msh:sideLink key="ALT+2" params="id" action="/entityParentEdit-contract_pricePosition" name="Изменить" title="Изменить" roles="/Policy/Mis/Contract/PriceList/PricePosition/Edit"/>
			<msh:sideLink key="ALT+DEL" params="id" action="/entityParentDelete-contract_pricePosition" name="Удалить" title="Удалить" roles="/Policy/Mis/Contract/PriceList/PricePosition/Delete"/>
		</msh:sideMenu>
		<msh:sideMenu title="Добавить" >
			<msh:sideLink key="ALT+N" params="id" action="/entityParentPrepareCreate-contract_priceMedService" name="Медицинские услуги" title="Медицинские услуги" roles="/Policy/Mis/Contract/PriceList/PricePosition/PriceMedService/Create"/>
		</msh:sideMenu>
		<tags:contractMenu currentAction="price"/>
	</tiles:put>
</tiles:insert>
