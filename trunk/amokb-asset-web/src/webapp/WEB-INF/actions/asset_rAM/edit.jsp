<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://struts.apache.org/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://www.nuzmsh.ru/tags/msh" prefix="msh" %>
<%@ taglib uri="http://www.ecom-ast.ru/tags/ecom" prefix="ecom" %>
<%@ taglib tagdir="/WEB-INF/tags" prefix="tags" %>
<tiles:insert page="/WEB-INF/tiles/mainLayout.jsp" flush="true">
	<tiles:put name="body" type="string">
		<msh:form action="/entitySaveGoView-asset_rAM.do" defaultField="">
			<msh:hidden property="id" />
			<msh:hidden property="saveType" />
			<msh:hidden property="parent" />
			<msh:panel>
				<msh:row>
					<msh:autoComplete property="ramType" label="Тип оперативной памяти" vocName="vocRAM" horizontalFill="true" />
				</msh:row>
				<msh:row>
					<msh:textField property="storage" label="Емкость в МБ"/>
				</msh:row>
				<msh:row>
					<msh:textField property="busRate" label="Частота шины в МГц"/>
				</msh:row>
			<msh:submitCancelButtonsRow colSpan="4" />
			</msh:panel>
		</msh:form>
		<msh:ifFormTypeIsView formName="asset_rAMForm">
		</msh:ifFormTypeIsView>
	</tiles:put>
	<tiles:put name="title" type="string">
		<ecom:titleTrail mainMenu="Patient" beginForm="asset_rAMForm" />
	</tiles:put>
	<tiles:put name="side" type="string">
		<msh:ifFormTypeAreViewOrEdit formName="asset_rAMForm">
			<msh:sideMenu>
				<msh:sideLink key="ALT+2" params="id" action="/entityParentEdit-asset_rAM" name="Изменить" title="Изменить" roles=""/>
				<msh:sideLink key="ALT+DEL" params="id" action="/entityParentDelete-asset_rAM" name="Удалить" title="Удалить" roles=""/>
			</msh:sideMenu>
			<msh:sideMenu title="Добавить" >
			</msh:sideMenu>
		</msh:ifFormTypeAreViewOrEdit>
	</tiles:put>
</tiles:insert>
