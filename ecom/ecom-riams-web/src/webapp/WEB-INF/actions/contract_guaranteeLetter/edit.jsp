<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://struts.apache.org/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://www.nuzmsh.ru/tags/msh" prefix="msh" %>
<%@ taglib uri="http://www.ecom-ast.ru/tags/ecom" prefix="ecom" %>
<%@ taglib tagdir="/WEB-INF/tags" prefix="tags" %>

<tiles:insert page="/WEB-INF/tiles/main${param.short}Layout.jsp" flush="true">
	<tiles:put name="body" type="string">
	
		<msh:form action="/entityParentSaveGoParentView-contract_guaranteeLetter.do" defaultField="numberDoc">
			<msh:hidden property="id" />
			<msh:hidden property="saveType" />
			<msh:hidden property="contract" />
			<msh:panel>
			<msh:row>
				<msh:textField property="numberDoc" size="100" label="Номер" fieldColSpan="3"/>
			</msh:row>
		    <msh:row>
				<msh:textField property="issueDate" label="Дата Выдачи"/>
			</msh:row>
			<msh:row>
				<msh:autoComplete property="kindHelp" vocName="vocGuaranteeKindHelp" fieldColSpan="3" 
					horizontalFill="true" />
			</msh:row>
		    <msh:row>
					<msh:textField property="actionDate" label="Действует с"/>
					<msh:textField property="actionDateTo" label="по"/>
			</msh:row>
		    <msh:row>
					<msh:textField property="limitMoney"/>
					<msh:checkBox property="isNoLimit" label="Без лимита" />
			</msh:row>
			<msh:row>
			
				<msh:autoComplete property="contractPerson" fieldColSpan="3" horizontalFill="true" size="150" vocName="contractPerson" label="Договорная персона"/>
				<td align="right" width="1px" ><div id="personButton"></div></td>
			</msh:row>
			
			<msh:submitCancelButtonsRow colSpan="2" />
			</msh:panel>
		</msh:form>
		<msh:ifFormTypeIsNotView formName="contract_guaranteeLetterForm">
			<tags:contract_natPerson_new autoCompliteContractPerson="contractPerson" 
			divForButton="personButton" name="Person" title="Поиск персоны"></tags:contract_natPerson_new>
		</msh:ifFormTypeIsNotView>
		
	</tiles:put>
	<tiles:put name="title" type="string">
		<ecom:titleTrail mainMenu="Contract" beginForm="contract_guaranteeLetterForm" />
	</tiles:put>
	<tiles:put name="side" type="string">
		<msh:sideMenu>
			<msh:sideLink key="ALT+2" params="id" action="/entityParentEdit-contract_guaranteeLetter" name="Изменить" title="Изменить" roles="/Policy/Mis/Contract/MedContract/ContractGuarantee/ContractGuaranteeLetter/Edit"/>
			<msh:sideLink key="ALT+DEL" params="id" action="/entityParentDelete-contract_guaranteeLetter" name="Удалить" title="Удалить" roles="/Policy/Mis/Contract/MedContract/ContractGuarantee/ContractGuaranteeLetter/Delete"/>
		</msh:sideMenu>
	</tiles:put>
	<tiles:put name="javascript" type="string">
  	<msh:ifFormTypeIsNotView formName="contract_guaranteeLetterForm">
  	<script type="text/javascript">
  	initPersonContractPersonDialog()</script>
  		</msh:ifFormTypeIsNotView>
  </tiles:put>
</tiles:insert>
