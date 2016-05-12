<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://struts.apache.org/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://www.nuzmsh.ru/tags/msh" prefix="msh" %>
<%@ taglib uri="http://www.ecom-ast.ru/tags/ecom" prefix="ecom" %>
<%@ taglib tagdir="/WEB-INF/tags" prefix="tags" %>
<tiles:insert page="/WEB-INF/tiles/mainLayout.jsp" flush="true">
	<tiles:put name="body" type="string">
		<msh:form action="/entityParentSaveGoParentView-mis_copyingEquipmentMaskFiles.do" defaultField="name">
			<msh:hidden property="id" />
			<msh:hidden property="saveType" />
			<msh:hidden property="lpu"/>
			<msh:hidden property="parent"/>
			<msh:panel colsWidth="10%,10%,10%">
				<msh:row>
					<msh:textField property="name" fieldColSpan="3" horizontalFill="true"/>
				</msh:row>
				<msh:row>
					<msh:textField property="accountNumber"/>
					<msh:textField property="ipaddress"/>
				</msh:row>
				<msh:row>
					<msh:textField property="model"/>
					<msh:textField property="serialNumber"/>
				</msh:row>
				<msh:checkBox property="isTxtFile"/>
				<msh:row>
					<msh:textArea property="maskFiles" fieldColSpan="3"  />
				</msh:row>
			<msh:submitCancelButtonsRow colSpan="4" />
			</msh:panel>
		</msh:form>
		<msh:ifFormTypeIsView formName="mis_copyingEquipmentMaskFilesForm">
		</msh:ifFormTypeIsView>
	</tiles:put>
	<tiles:put name="title" type="string">
		<ecom:titleTrail mainMenu="Lpu" beginForm="mis_copyingEquipmentForm"/>
	</tiles:put>
	<tiles:put name="side" type="string">
		<msh:ifFormTypeAreViewOrEdit formName="mis_copyingEquipmentMaskFilesForm">
			<msh:sideMenu>
				<msh:sideLink key="ALT+2" params="id" action="/entityEdit-mis_copyingEquipmentMaskFiles" name="Изменить" title="Изменить" 
				roles="/Policy/Mis/Asset/PermanentAsset/AutomatedWorkplace/Equipment/CopyingEquipment/Edit"/>
				<msh:sideLink key="ALT+DEL" params="id" action="/entityDelete-mis_copyingEquipmentMaskFiles" name="Удалить" 
				title="Удалить" roles="/Policy/Mis/Asset/PermanentAsset/AutomatedWorkplace/Equipment/CopyingEquipment/Delete"/>
			</msh:sideMenu>

		</msh:ifFormTypeAreViewOrEdit>
	</tiles:put>
</tiles:insert>
