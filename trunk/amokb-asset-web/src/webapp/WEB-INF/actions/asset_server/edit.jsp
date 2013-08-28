<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://struts.apache.org/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://www.nuzmsh.ru/tags/msh" prefix="msh" %>
<%@ taglib uri="http://www.ecom-ast.ru/tags/ecom" prefix="ecom" %>
<%@ taglib tagdir="/WEB-INF/tags" prefix="tags" %>
<tiles:insert page="/WEB-INF/tiles/mainLayout.jsp" flush="true">
	<tiles:put name="body" type="string">
		<msh:form action="/entityParentSaveGoView-asset_server.do" defaultField="name">
			<msh:hidden property="id" />
			<msh:hidden property="saveType" />
			<msh:hidden property="automatedWorkplace" />
			<msh:hidden property="lpu"/>
			<msh:panel>
				<msh:row>
					<msh:textField property="name" label="Название" fieldColSpan="3" horizontalFill="true"/>
				</msh:row>
				<msh:row>
					<msh:autoComplete property="producer" label="Производитель" vocName="vocAssetProducer" horizontalFill="true" fieldColSpan="3"/>
				</msh:row>
				<msh:row>
					<msh:autoComplete fieldColSpan="3" property="responsiblePerson" label="Ответственное лицо" vocName="vocAssetResponsiblePerson" horizontalFill="true" />
				</msh:row>
				<msh:row>
					<msh:textField property="productionDate" label="Дата производства"/>
				</msh:row>
				<msh:row >
					<msh:textField  property="entranceDate" label="Дата поступления"/>
					<msh:textField  property="discardingDate" label="Дата списания"/>
				</msh:row>
				<msh:row>
					<msh:textField property="inventoryNumber" label="Инвентарный номер" horizontalFill="true"/>
					<msh:autoComplete property="securityMark" label="Метка безопасности" vocName="vocSecurityMark" horizontalFill="true" />
				</msh:row>
				<msh:row>
					<msh:textField size="30" property="model" label="Модель"/>
					<msh:textField size="30" property="serialNumber" label="Серийный номер"/>
				</msh:row>
				<msh:row>
					<msh:textField property="accountNumber" label="Учетный номер" horizontalFill="true"/>
				</msh:row>				
				<msh:row>
					<msh:checkBox property="internetAccess" label="Доступ в интернет"/>
					<msh:checkBox property="usbAccess" label="Доступ к USB"/>
				</msh:row>
				<msh:row>
					<msh:checkBox property="biosPassword" label="Пароль на BIOS"/>
					<msh:checkBox property="sealing" label="Опечатывание"/>
				</msh:row>
				<msh:row>
					<msh:checkBox property="localAdminPassword" label="Пароль локального администратора" fieldColSpan="3" horizontalFill="true"/>
				</msh:row>
				<msh:row>
					<msh:textField horizontalFill="true" property="networkName" label="Сетевое имя"/>
					<msh:textField horizontalFill="true" property="ipaddress" label="IP адрес"/>
				</msh:row>
				<msh:row>
					<msh:textArea property="comment" label="Комментарии" fieldColSpan="3" horizontalFill="true"/>
				</msh:row>
			<msh:submitCancelButtonsRow colSpan="2" />
			</msh:panel>
		</msh:form>
		<msh:ifFormTypeIsView formName="asset_serverForm">
			<msh:section title="Серверные роли">
			<ecom:parentEntityListAll formName="asset_serverForm" attribute="serverRoles" />
				<msh:table name="serverRoles" action="entityParentView-asset_serverRole.do" idField="id">
					<msh:tableColumn columnName="id" property="id"/>
				</msh:table>
			</msh:section>
		</msh:ifFormTypeIsView>
	</tiles:put>
	<tiles:put name="title" type="string">
		<ecom:titleTrail mainMenu="Patient" beginForm="asset_serverForm" />
	</tiles:put>
	<tiles:put name="side" type="string">
		<msh:ifFormTypeAreViewOrEdit formName="asset_serverForm">
			<msh:sideMenu>
				<msh:sideLink key="ALT+2" params="id" action="/entityParentEdit-asset_server" name="Изменить" title="Изменить" roles=""/>
				<msh:sideLink key="ALT+DEL" params="id" action="/entityParentDelete-asset_server" name="Удалить" title="Удалить" roles=""/>
			</msh:sideMenu>
			<msh:sideMenu title="Добавить" >
				<msh:sideLink key="ALT+N" params="id" action="/entityParentPrepareCreate-asset_serverRole" name="Серверные роли" title="Серверные роли" roles=""/>
			</msh:sideMenu>
		</msh:ifFormTypeAreViewOrEdit>
	</tiles:put>
</tiles:insert>
