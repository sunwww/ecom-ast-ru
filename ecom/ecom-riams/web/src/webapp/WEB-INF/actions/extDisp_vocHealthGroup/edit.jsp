<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://struts.apache.org/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://www.nuzmsh.ru/tags/msh" prefix="msh" %>
<%@ taglib uri="http://www.ecom-ast.ru/tags/ecom" prefix="ecom" %>
<%@ taglib tagdir="/WEB-INF/tags" prefix="tags" %>
<tiles:insert page="/WEB-INF/tiles/main${param.short}Layout.jsp" flush="true">
	<tiles:put name="body" type="string">
		<msh:form action="/entityParentSaveGoView-extDisp_vocHealthGroup.do" defaultField="code">
			<msh:hidden property="id" />
			<msh:hidden property="saveType" />
			<msh:hidden property="dispType" />
			<msh:panel>
				<msh:row>
					<msh:textField property="code" label="Код"/>
					<msh:textField property="name" label="Наименование"/>
				</msh:row>
			<msh:submitCancelButtonsRow colSpan="4" />
			</msh:panel>
		</msh:form>
	</tiles:put>
	<tiles:put name="title" type="string">
		<ecom:titleTrail mainMenu="Voc" beginForm="extDisp_vocHealthGroupForm" />
	</tiles:put>
	<tiles:put name="side" type="string">
		<msh:ifFormTypeAreViewOrEdit formName="extDisp_vocHealthGroupForm">
			<msh:sideMenu>
				<msh:sideLink key="ALT+2" params="id" action="/entityParentEdit-extDisp_vocHealthGroup" name="Изменить" title="Изменить" roles="/Policy/Mis/ExtDisp/Card/Voc/HealthGroup/Edit"/>
				<msh:sideLink key="ALT+DEL" params="id" action="/entityParentDeleteGoParentView-extDisp_vocHealthGroup" name="Удалить" title="Удалить" roles="/Policy/Mis/ExtDisp/Card/Voc/HealthGroup/Delete"/>
			</msh:sideMenu>
		</msh:ifFormTypeAreViewOrEdit>
        <tags:voc_menu currentAction="medService"/>
	</tiles:put>
</tiles:insert>
