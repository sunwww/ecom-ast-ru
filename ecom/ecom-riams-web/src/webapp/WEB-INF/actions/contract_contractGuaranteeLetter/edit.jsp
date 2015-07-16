<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://struts.apache.org/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://www.nuzmsh.ru/tags/msh" prefix="msh" %>
<%@ taglib uri="http://www.ecom-ast.ru/tags/ecom" prefix="ecom" %>
<%@ taglib tagdir="/WEB-INF/tags" prefix="tags" %>

<tiles:insert page="/WEB-INF/tiles/mainLayout.jsp" flush="true">
	<tiles:put name="body" type="string">
	545646
		<msh:form action="/entityParentSaveGoParentView-contract_contractGuaranteeLetter.do" defaultField="contractPersonName">
			<msh:hidden property="id" />
			<msh:hidden property="saveType" />
			<msh:hidden property="contract" />
			<msh:panel>
			<msh:row>
			
				<msh:autoComplete property="contractPerson" size="150" vocName="contractPerson" label="Договорная персона"/>
			</msh:row>
							<msh:row>
					<msh:textField property="limitMoney"/>
				</msh:row>
			
			<msh:submitCancelButtonsRow colSpan="2" />
			</msh:panel>
		</msh:form>
		<msh:ifFormTypeIsView formName="contract_contractGuaranteeLetterForm">
		</msh:ifFormTypeIsView>
	</tiles:put>
	<tiles:put name="title" type="string">
		<ecom:titleTrail mainMenu="Contract" beginForm="contract_contractGuaranteeLetterForm" />
	</tiles:put>
	<tiles:put name="side" type="string">
		<msh:sideMenu>
			<msh:sideLink key="ALT+2" params="id" action="/entityParentEdit-contract_contractGuaranteeLetter" name="Изменить" title="Изменить" roles="/Policy/Mis/Contract/MedContract/ContractGuarantee/ContractGuaranteeLetter/Edit"/>
			<msh:sideLink key="ALT+DEL" params="id" action="/entityParentDelete-contract_contractGuaranteeLetter" name="Удалить" title="Удалить" roles="/Policy/Mis/Contract/MedContract/ContractGuarantee/ContractGuaranteeLetter/Delete"/>
		</msh:sideMenu>
	</tiles:put>
</tiles:insert>
