<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://struts.apache.org/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://www.nuzmsh.ru/tags/msh" prefix="msh" %>
<%@ taglib uri="http://www.ecom-ast.ru/tags/ecom" prefix="ecom" %>
<%@ taglib tagdir="/WEB-INF/tags" prefix="tags" %>
<tiles:insert page="/WEB-INF/tiles/main${param.short}Layout.jsp" flush="true">
	<tiles:put name="body" type="string">
		<msh:form action="/entityParentSaveGoParentView-pd_identifier.do" defaultField="typeName">
			<msh:hidden property="id" />
			<msh:hidden property="saveType" />
			<msh:hidden property="person" />
			<msh:panel>
				<msh:row>
					<msh:autoComplete label="Система" property="identificationSystem" vocName="vocIdentificationSystem" fieldColSpan="5" horizontalFill="true"/>
				</msh:row>
				<msh:row>
					<msh:textField property="identificationNumber" label="Номер"/>
					<msh:checkBox property="isTransient" label="Временный"/>
					
				</msh:row>
			<msh:submitCancelButtonsRow colSpan="4" />
			</msh:panel>
		</msh:form>

	</tiles:put>
	<tiles:put name="title" type="string">
		<ecom:titleTrail mainMenu="Patient" beginForm="pd_identifierForm" />
	</tiles:put>
	<tiles:put name="side" type="string">
		<msh:ifFormTypeAreViewOrEdit formName="pd_identifierForm">
			<msh:sideMenu>
				<msh:sideLink key="ALT+2" params="id" action="/entityParentEdit-pd_identifier" name="Изменить" title="Изменить" roles=""/>
				<msh:sideLink key="ALT+DEL" params="id" action="/entityParentDeleteGoParentView-pd_identifier" name="Удалить" title="Удалить" roles=""/>
			</msh:sideMenu>
		</msh:ifFormTypeAreViewOrEdit>
	</tiles:put>
</tiles:insert>
