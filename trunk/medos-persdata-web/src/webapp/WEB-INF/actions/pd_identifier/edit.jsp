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
				<msh:row>
					<msh:textField property="urgencyStartDate" label="Дата начала"/>
					<msh:textField property="urgencyExpiryDate" label="Дата окончания"/>
				</msh:row>
				<msh:row>
					<msh:autoComplete viewAction="entityParentView-pd_comingDocument.do" shortViewAction="entityParentView-pd_comingDocument.do?short=Short" fieldColSpan="3" horizontalFill="true" parentId="pd_identifierForm.person" property="comingDocument" vocName="comingDocumentByPerson" label="Входящий документ"/>
				</msh:row>
				<msh:row>
					<msh:autoComplete viewAction="entityView-pd_copiesTransferAct.do" shortViewAction="entityView-pd_copiesTransferAct.do?short=Short" fieldColSpan="3" horizontalFill="true" property="transferAct" vocName="copiesTransferAct" label="Акт передачи копий"/>
				</msh:row>
				<msh:row>
					<msh:autoComplete fieldColSpan="3" horizontalFill="true" property="copiesDestructionAct" vocName="copiesDestructionAct" label="Акт уничтожения копий"/>
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
				<msh:sideLink key="ALT+2" params="id" action="/entityParentEdit-pd_identifier" name="Изменить" title="Изменить" roles="/Policy/PersData/Person/Identifier/Edit"/>
				<msh:sideLink key="ALT+DEL" params="id" action="/entityParentDeleteGoParentView-pd_identifier" name="Удалить" title="Удалить" roles="/Policy/PersData/Person/Identifier/Delete"/>
			</msh:sideMenu>
		</msh:ifFormTypeAreViewOrEdit>
	</tiles:put>
	<tiles:put name="javascript" type="string">
		<msh:ifFormTypeIsCreate formName="pd_identifierForm">
			<script type="text/javascript">
				$('isTransient').checked = true ;
			</script>
		</msh:ifFormTypeIsCreate>
		<msh:ifFormTypeIsNotView formName="pd_identirierForm">
			<script type="text/javascript">
				transferActAutocomplete.addOnChangeCallback(function() {
					var sp=$('trnasferActName').value.split(" от ") ;
					if (sp.length>0) $("urgencyStartDate").value=sp[1] ;
				}) ;
			</script>
		</msh:ifFormTypeIsNotView>
	</tiles:put>
</tiles:insert>
