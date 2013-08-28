<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://struts.apache.org/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://www.nuzmsh.ru/tags/msh" prefix="msh" %>
<%@ taglib uri="http://www.ecom-ast.ru/tags/ecom" prefix="ecom" %>
<%@ taglib tagdir="/WEB-INF/tags" prefix="tags" %>
<tiles:insert page="/WEB-INF/tiles/mainLayout.jsp" flush="true">
	<tiles:put name="body" type="string">
		<msh:form action="/entityParentSaveGoParentView-asset_room.do" defaultField="floor">
			<msh:hidden property="id" />
			<msh:hidden property="saveType" />
			<msh:hidden property="building" />
			<msh:hidden property="lpu" />
			<msh:panel>
				<msh:row>
					<msh:textField property="floor" label="Этаж"/>
					<msh:textField property="roomNumber" label="Номер помещения"/>
				</msh:row>
				<msh:row>
					<msh:textField property="localPhone" label="Местный телефон"/>
					<msh:textField property="cityPhone" label="Городской телефон"/>
				</msh:row>
				<msh:row>
					<msh:autoComplete property="securityMark" label="Метка безопасности" vocName="vocSecurityMark" horizontalFill="true" fieldColSpan="3"/>
				</msh:row>
				<msh:row>
					<msh:autoComplete property="responsiblePerson" label="Ответственное лицо" vocName="vocAssetResponsiblePerson" horizontalFill="true" fieldColSpan="3"/>
				</msh:row>
				<msh:row>
					<msh:textArea property="comment" label="Комментарии" fieldColSpan="3" horizontalFill="true"/>
				</msh:row>
			<msh:submitCancelButtonsRow colSpan="3" />
			</msh:panel>
		</msh:form>
		<msh:ifFormTypeIsView formName="asset_roomForm">
		</msh:ifFormTypeIsView>
	</tiles:put>
	<tiles:put name="title" type="string">
		<ecom:titleTrail mainMenu="Patient" beginForm="asset_roomForm" />
	</tiles:put>
	<tiles:put name="side" type="string">
		<msh:ifFormTypeAreViewOrEdit formName="asset_roomForm">
			<msh:sideMenu>
				<msh:sideLink key="ALT+2" params="id" action="/entityParentEdit-asset_room" name="Изменить" title="Изменить" roles="/Policy/Mis/Asset/PermanentAsset/Territory/Building/Room/Edit"/>
				<msh:sideLink key="ALT+DEL" params="id" action="/entityParentDelete-asset_room" name="Удалить" title="Удалить" roles="/Policy/Mis/Asset/PermanentAsset/Territory/Building/Room/Delete"/>
			</msh:sideMenu>
			<msh:sideMenu title="Добавить" >
			</msh:sideMenu>
		</msh:ifFormTypeAreViewOrEdit>
	</tiles:put>
</tiles:insert>
