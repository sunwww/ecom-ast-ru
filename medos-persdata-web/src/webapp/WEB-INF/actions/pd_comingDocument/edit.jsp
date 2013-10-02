<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://struts.apache.org/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://www.nuzmsh.ru/tags/msh" prefix="msh" %>
<%@ taglib uri="http://www.ecom-ast.ru/tags/ecom" prefix="ecom" %>
<%@ taglib tagdir="/WEB-INF/tags" prefix="tags" %>
<tiles:insert page="/WEB-INF/tiles/main${param.short}Layout.jsp" flush="true">
	<tiles:put name="body" type="string">
		<msh:form action="/entityParentSaveGoParentView-pd_comingDocument.do" defaultField="typeName">
			<msh:hidden property="id" />
			<msh:hidden property="saveType" />
			<msh:hidden property="person" />
			<msh:panel>
				<msh:row>
					<msh:autoComplete property="stateStructure" vocName="vocStateStructure" fieldColSpan="5" horizontalFill="true"/>
				</msh:row>
				<msh:row>
					<msh:autoComplete property="type" vocName="vocComingDocument" fieldColSpan="5" horizontalFill="true"/>
				</msh:row>
				<msh:row>
					<msh:textField property="urgencyStartDate" label="Дата выдачи"/>
				</msh:row>
				<msh:row>
					<msh:textField property="series" label="Серия"/>
					<msh:textField property="documentNumber" label="Номер"/>
				</msh:row>
				<msh:row>
					<msh:autoComplete parentId="pd_comingDocumentForm.person" property="identifier" vocName="identifier" label="Идентификатор"/>
				</msh:row>
				<msh:row>
					<msh:autoComplete property="transferAct" vocName="cardTransferAct" label="Акт передачи копий"/>
				</msh:row>
				<msh:row>
					<msh:autoComplete property="copiesDestructionAct" vocName="copiesDestructionAct" label="Акт уничтожения копий"/>
				</msh:row>
			<msh:submitCancelButtonsRow colSpan="4" />
			</msh:panel>
		</msh:form>

	</tiles:put>
	<tiles:put name="title" type="string">
		<ecom:titleTrail mainMenu="Patient" beginForm="pd_comingDocumentForm" />
	</tiles:put>
	<tiles:put name="side" type="string">
		<msh:ifFormTypeAreViewOrEdit formName="pd_comingDocumentForm">
			<msh:sideMenu>
				<msh:sideLink key="ALT+2" params="id" action="/entityParentEdit-pd_comingDocument" name="Изменить" title="Изменить" roles=""/>
				<msh:sideLink key="ALT+DEL" params="id" action="/entityParentDeleteGoParentView-pd_comingDocument" name="Удалить" title="Удалить" roles=""/>
			</msh:sideMenu>
		</msh:ifFormTypeAreViewOrEdit>
	</tiles:put>
</tiles:insert>
