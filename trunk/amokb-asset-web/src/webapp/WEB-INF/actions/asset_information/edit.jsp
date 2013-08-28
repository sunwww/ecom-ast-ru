<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://struts.apache.org/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://www.nuzmsh.ru/tags/msh" prefix="msh" %>
<%@ taglib uri="http://www.ecom-ast.ru/tags/ecom" prefix="ecom" %>
<%@ taglib tagdir="/WEB-INF/tags" prefix="tags" %>
<tiles:insert page="/WEB-INF/tiles/mainLayout.jsp" flush="true">
	<tiles:put name="body" type="string">
		<msh:form action="/entitySaveGoView-asset_information.do" defaultField="">
			<msh:hidden property="id" />
			<msh:hidden property="saveType" />
			<msh:hidden property="parent" />
			<msh:panel>
				<msh:row>
					<msh:textField property="storagePath" label="Место хранения"/>
				</msh:row>
				<msh:row>
					<msh:autoComplete property="securityMark" label="Метка безопасности" vocName="vocSecurityMark" horizontalFill="true" />
				</msh:row>
			<msh:submitCancelButtonsRow colSpan="4" />
			</msh:panel>
		</msh:form>
		<msh:ifFormTypeIsView formName="asset_informationForm">
		</msh:ifFormTypeIsView>
	</tiles:put>
	<tiles:put name="title" type="string">
		<ecom:titleTrail mainMenu="Patient" beginForm="asset_informationForm" />
	</tiles:put>
	<tiles:put name="side" type="string">
		<msh:ifFormTypeAreViewOrEdit formName="asset_informationForm">
			<msh:sideMenu>
				<msh:sideLink key="ALT+2" params="id" action="/entityParentEdit-asset_information" name="Изменить" title="Изменить" roles=""/>
				<msh:sideLink key="ALT+DEL" params="id" action="/entityParentDelete-asset_information" name="Удалить" title="Удалить" roles=""/>
			</msh:sideMenu>
			<msh:sideMenu title="Добавить" >
			</msh:sideMenu>
		</msh:ifFormTypeAreViewOrEdit>
	</tiles:put>
</tiles:insert>
