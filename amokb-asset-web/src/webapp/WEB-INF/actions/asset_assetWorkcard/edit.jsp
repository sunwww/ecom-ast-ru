<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://struts.apache.org/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://www.nuzmsh.ru/tags/msh" prefix="msh" %>
<%@ taglib uri="http://www.ecom-ast.ru/tags/ecom" prefix="ecom" %>
<%@ taglib tagdir="/WEB-INF/tags" prefix="tags" %>
<tiles:insert page="/WEB-INF/tiles/mainLayout.jsp" flush="true">
	<tiles:put name="body" type="string">
		<msh:form action="/entityParentSaveGoView-asset_assetWorkcard.do" defaultField="">
			<msh:hidden property="id" />
			<msh:hidden property="saveType" />
			<msh:hidden property="parent" />
			<msh:panel>
				<msh:row>
					<msh:autoComplete property="assetWork" label="Работа" vocName="vocAssetWork" horizontalFill="true" />
				</msh:row>
				<msh:row>
					<msh:textField property="workDate" label="Дата работы"/>
				</msh:row>
				<msh:row>
					<msh:autoComplete property="worker" label="Исполнитель" vocName="worker" horizontalFill="true" />
				</msh:row>
				<msh:row>
					<msh:autoComplete property="customer" label="Заказчик" vocName="worker" horizontalFill="true" />
				</msh:row>
				<msh:row>
					<msh:textField property="workTime" label="Время работы в часах"/>
				</msh:row>
				<msh:row>
					<msh:autoComplete property="sealingBreak" label="Нарушение опечатывания" vocName="boolen" horizontalFill="true" />
				</msh:row>
				<msh:row>
					<msh:checkBox property="sealing" label="Проведено опечатывание"/>
				</msh:row>
				<msh:row>
					<msh:autoComplete property="asset" label="Актив" vocName="asset" horizontalFill="true" />
				</msh:row>
			<msh:submitCancelButtonsRow colSpan="4" />
			</msh:panel>
		</msh:form>
		<msh:ifFormTypeIsView formName="asset_assetWorkcardForm">
		</msh:ifFormTypeIsView>
	</tiles:put>
	<tiles:put name="title" type="string">
		<ecom:titleTrail mainMenu="Patient" beginForm="asset_assetWorkcardForm" />
	</tiles:put>
	<tiles:put name="side" type="string">
		<msh:ifFormTypeAreViewOrEdit formName="asset_assetWorkcardForm">
			<msh:sideMenu>
				<msh:sideLink key="ALT+2" params="id" action="/entityParentEdit-asset_assetWorkcard" name="Изменить" title="Изменить" roles="/Policy/Mis/Asset/AssetWorkcard/Edit"/>
				<msh:sideLink key="ALT+DEL" params="id" action="/entityParentDelete-asset_assetWorkcard" name="Удалить" title="Удалить" roles="/Policy/Mis/Asset/AssetWorkcard/Delete"/>
			</msh:sideMenu>
			<msh:sideMenu title="Добавить" >
			</msh:sideMenu>
		</msh:ifFormTypeAreViewOrEdit>
	</tiles:put>
</tiles:insert>
