<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://struts.apache.org/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://www.nuzmsh.ru/tags/msh" prefix="msh" %>
<%@ taglib uri="http://www.ecom-ast.ru/tags/ecom" prefix="ecom" %>
<%@ taglib tagdir="/WEB-INF/tags" prefix="tags" %>
<tiles:insert page="/WEB-INF/tiles/mainLayout.jsp" flush="true">
	<tiles:put name="body" type="string">
		<msh:form action="/entitySaveGoView-asset_computer.do" defaultField="">
			<msh:hidden property="id" />
			<msh:hidden property="saveType" />
			<msh:hidden property="parent" />
			<msh:panel>
				<msh:row>
					<msh:checkBox property="internetAccess" label="Доступ в Интернет"/>
				</msh:row>
				<msh:row>
					<msh:checkBox property="usbAccess" label="Доступ к USB"/>
				</msh:row>
				<msh:row>
					<msh:checkBox property="biosPassword" label="Пароль на BIOS"/>
				</msh:row>
				<msh:row>
					<msh:textField property="networkName" label="Сетевое имя"/>
				</msh:row>
				<msh:row>
					<msh:autoComplete property="" label="" vocName="computerProgramm" horizontalFill="true" />
				</msh:row>
				<msh:row>
					<msh:textField property="ipaddress" label="IP адрес"/>
				</msh:row>
				<msh:row>
					<msh:checkBox property="sealing" label="Опечатывание"/>
				</msh:row>
			<msh:submitCancelButtonsRow colSpan="4" />
			</msh:panel>
		</msh:form>
		<msh:ifFormTypeIsView formName="asset_computerForm">
			<msh:section title="Компоненты">
			<ecom:parentEntityListAll formName="asset_computerForm" attribute="components" />
				<msh:table name="components" action="entityParentView-asset_computerComponent.do" idField="id">
					<msh:tableColumn columnName="id" property="id"/>
				</msh:table>
			</msh:section>
		</msh:ifFormTypeIsView>
	</tiles:put>
	<tiles:put name="title" type="string">
		<ecom:titleTrail mainMenu="Patient" beginForm="asset_computerForm" />
	</tiles:put>
	<tiles:put name="side" type="string">
		<msh:ifFormTypeAreViewOrEdit formName="asset_computerForm">
			<msh:sideMenu>
				<msh:sideLink key="ALT+2" params="id" action="/entityParentEdit-asset_computer" name="Изменить" title="Изменить" roles=""/>
				<msh:sideLink key="ALT+DEL" params="id" action="/entityParentDelete-asset_computer" name="Удалить" title="Удалить" roles=""/>
			</msh:sideMenu>
			<msh:sideMenu title="Добавить" >
				<msh:sideLink key="ALT+N" params="id" action="/entityParentPrepareCreate-asset_computerComponent" name="Компоненты" title="Компоненты" roles=""/>
			</msh:sideMenu>
		</msh:ifFormTypeAreViewOrEdit>
	</tiles:put>
</tiles:insert>
