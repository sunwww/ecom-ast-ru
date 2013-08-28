<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://struts.apache.org/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://www.nuzmsh.ru/tags/msh" prefix="msh" %>
<%@ taglib uri="http://www.ecom-ast.ru/tags/ecom" prefix="ecom" %>
<%@ taglib tagdir="/WEB-INF/tags" prefix="tags" %>
<tiles:insert page="/WEB-INF/tiles/mainLayout.jsp" flush="true">
	<tiles:put name="body" type="string">
		<msh:form action="/entityParentSaveGoView-asset_automatedWorkplace.do" defaultField="">
			<msh:hidden property="id" />
			<msh:hidden property="saveType" />
			<msh:hidden property="lpu" />
			<msh:panel>
				<msh:row>
					<msh:autoComplete property="territory" label="Территория" vocName="territory" horizontalFill="true" parentId="asset_automatedWorkplaceForm.lpu" fieldColSpan="3"/>
				</msh:row>
				<msh:row>
					<msh:autoComplete property="building" label="Здание" vocName="building" horizontalFill="true" parentAutocomplete="territory" fieldColSpan="3"/>
				</msh:row>
				<msh:row>
					<msh:autoComplete property="room" label="Помещение" vocName="room" horizontalFill="true" parentAutocomplete="building" />
					<msh:autoComplete property="network" label="Вычислительная сеть" vocName="network" horizontalFill="true" />
				</msh:row>
				<msh:row>
					<msh:autoComplete property="responsibleUser" label="Ответственный пользователь" vocName="worker" horizontalFill="true" fieldColSpan="3"/>
				</msh:row>
				<msh:row>
					<msh:autoComplete property="responsiblePerson" label="Ответственное лицо" vocName="vocAssetResponsiblePerson" horizontalFill="true" fieldColSpan="3"/>
				</msh:row>
				<msh:row>
					<msh:autoComplete property="securityMark" label="Метка безопасности" vocName="vocSecurityMark" horizontalFill="true" fieldColSpan="3"/>
				</msh:row>
				<msh:row>
					<msh:textArea property="comment" label="Комментарии" fieldColSpan="3" horizontalFill="true"/>
				</msh:row>
			<msh:submitCancelButtonsRow colSpan="4" />
			</msh:panel>
		</msh:form>
		<msh:ifFormTypeIsView formName="asset_automatedWorkplaceForm">
			<msh:section title="Оборудование">
			<ecom:parentEntityListAll formName="asset_equipmentForm" attribute="automatedWorkplace" />
				<msh:table name="automatedWorkplace" action="entityParentView-asset_equipment.do" idField="id">
					<msh:tableColumn columnName="#" property="sn"/>
					<msh:tableColumn columnName="id" property="id"/>
				</msh:table>
			</msh:section>
		</msh:ifFormTypeIsView>
	</tiles:put>
	<tiles:put name="title" type="string">
		<ecom:titleTrail mainMenu="Patient" beginForm="asset_automatedWorkplaceForm" />
	</tiles:put>
	<tiles:put name="side" type="string">
		<msh:ifFormTypeAreViewOrEdit formName="asset_automatedWorkplaceForm">
			<msh:sideMenu>
				<msh:sideLink key="ALT+2" params="id" action="/entityParentEdit-asset_automatedWorkplace" name="Изменить" title="Изменить" roles="/Policy/Mis/Asset/PermanentAsset/AutomatedWorkplace/Edit"/>
				<msh:sideLink key="ALT+DEL" params="id" action="/entityParentDelete-asset_automatedWorkplace" name="Удалить" title="Удалить" roles="/Policy/Mis/Asset/PermanentAsset/AutomatedWorkplace/Delete"/>
			</msh:sideMenu>
			<msh:sideMenu title="Добавить" >
				<msh:sideLink key="ALT+3" params="id" 
					action="/entityParentPrepareCreate-asset_networkEquipment" 
					name="Сетевое оборудование" title="Оборудование" 
					roles="/Policy/Mis/Asset/PermanentAsset/AutomatedWorkplace/Equipment/NetworkEquipment/Create"/>
				<msh:sideLink key="ALT+4" params="id" 
					action="/entityParentPrepareCreate-asset_server" 
					name="Сервер" title="Оборудование" 
					roles="/Policy/Mis/Asset/PermanentAsset/AutomatedWorkplace/Equipment/Server/Create"/>
				<msh:sideLink key="ALT+5" params="id" 
					action="/entityParentPrepareCreate-asset_terminal" 
					name="Терминал" title="Оборудование" 
					roles="/Policy/Mis/Asset/PermanentAsset/AutomatedWorkplace/Equipment/Terminal/Create"/>
				<msh:sideLink key="ALT+6" params="id" 
					action="/entityParentPrepareCreate-asset_personalComputer" 
					name="Персональный компьютер" title="Оборудование" 
					roles="/Policy/Mis/Asset/PermanentAsset/AutomatedWorkplace/Equipment/PersonalComputer/Create"/>
				<msh:sideLink key="ALT+7" params="id" 
					action="/entityParentPrepareCreate-asset_copyingEquipment" 
					name="Копировальное оборудование" title="Оборудование" 
					roles="/Policy/Mis/Asset/PermanentAsset/AutomatedWorkplace/Equipment/CopyingEquipment/Create"/>
			</msh:sideMenu>
		</msh:ifFormTypeAreViewOrEdit>
	</tiles:put>
</tiles:insert>
