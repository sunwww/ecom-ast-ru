<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://struts.apache.org/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://www.nuzmsh.ru/tags/msh" prefix="msh" %>
<%@ taglib uri="http://www.ecom-ast.ru/tags/ecom" prefix="ecom" %>
<%@ taglib tagdir="/WEB-INF/tags" prefix="tags" %>
<tiles:insert page="/WEB-INF/tiles/mainLayout.jsp" flush="true">
	<tiles:put name="body" type="string">
		<msh:form action="/entitySaveGoView-asset_copyingEquipment.do" defaultField="name">
			<msh:hidden property="id" />
			<msh:hidden property="saveType" />
			<msh:hidden property="lpu"/>
			<msh:hidden property="automatedWorkplace"/>
			<msh:panel>
				<msh:row>
					<msh:textField property="name" label="Название"/>
				</msh:row>
				<msh:row>
					<msh:autoComplete property="producer" label="Производитель" vocName="vocAssetProducer" horizontalFill="true" />
				</msh:row>
				<msh:row>
					<msh:textField property="model" label="Модель"/>
					<msh:textField property="serialNumber" label="Серийный номер"/>
				</msh:row>
				<msh:row>
					<msh:textField property="inventoryNumber" label="Инвентарный номер"/>
					<msh:autoComplete property="securityMark" label="Метка безопасности" vocName="vocSecurityMark" horizontalFill="true" />
				</msh:row>
				<msh:row>
					<msh:textField property="accountNumber" label="Учетный номер"/>
					<msh:textField property="ipaddress" label="IP адрес"/>
				</msh:row>
				<msh:row>
					<msh:autoComplete fieldColSpan="3" property="responsiblePerson" label="Ответственное лицо" vocName="vocAssetResponsiblePerson" horizontalFill="true" />
				</msh:row>
				<msh:row>
					<msh:textField property="productionDate" label="Дата производства"/>
				</msh:row>
				<msh:row>
					<msh:textField property="entranceDate" label="Дата поступления"/>
					<msh:textField property="discardingDate" label="Дата списания"/>
				</msh:row>
				<msh:row>
					<msh:textArea property="comment" label="Комментарии"/>
				</msh:row>
			<msh:submitCancelButtonsRow colSpan="2" />
			</msh:panel>
		</msh:form>
		<msh:ifFormTypeIsView formName="asset_copyingEquipmentForm">
		</msh:ifFormTypeIsView>
	</tiles:put>
	<tiles:put name="title" type="string">
		<ecom:titleTrail mainMenu="Patient" beginForm="asset_copyingEquipmentForm" />
	</tiles:put>
	<tiles:put name="side" type="string">
		<msh:ifFormTypeAreViewOrEdit formName="asset_copyingEquipmentForm">
			<msh:sideMenu>
				<msh:sideLink key="ALT+2" params="id" action="/entityParentEdit-asset_copyingEquipment" name="Изменить" title="Изменить" roles="/Policy/Mis/Asset/PermanentAsset/AutomatedWorkplace/Equipment/CopyingEquipment/Edit"/>
				<msh:sideLink key="ALT+DEL" params="id" action="/entityParentDelete-asset_copyingEquipment" name="Удалить" title="Удалить" roles="/Policy/Mis/Asset/PermanentAsset/AutomatedWorkplace/Equipment/CopyingEquipment/Delete"/>
			</msh:sideMenu>
			<msh:sideMenu title="Добавить" >
			</msh:sideMenu>
		</msh:ifFormTypeAreViewOrEdit>
	</tiles:put>
</tiles:insert>
