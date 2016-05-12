<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://struts.apache.org/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://www.nuzmsh.ru/tags/msh" prefix="msh" %>
<%@ taglib uri="http://www.ecom-ast.ru/tags/ecom" prefix="ecom" %>
<%@ taglib tagdir="/WEB-INF/tags" prefix="tags" %>

<tiles:insert page="/WEB-INF/tiles/mainLayout.jsp" flush="true">
	<tiles:put name="body" type="string">
		<msh:form action="/entityParentSaveGoView-contract_servedPerson.do" defaultField="personName">
			<msh:hidden property="id" />
			<msh:hidden property="saveType" />
			<msh:hidden property="contract" />
			<msh:hidden property="createDate" />
			<msh:hidden property="createTime" />
			<msh:hidden property="createUsername" />
			<msh:hidden property="editDate" />
			<msh:hidden property="editTime" />
			<msh:hidden property="editUsername" />
			<msh:panel colsWidth="1%,1%,1%">
				<msh:row>
					<msh:autoComplete fieldColSpan="3" property="person" label="Договорная персона" vocName="contractPerson" size="100" />
					
					<td align="right" width="1px" ><div id="personButton"></div></td>
				</msh:row>
				<msh:row>
					<msh:textField property="dateFrom" label="Дата начала обслуживания"/>
					<msh:textField property="dateTo" label="Дата окончания"/>
				</msh:row>
			
				<msh:row>
					<msh:checkBox fieldColSpan="3" property="autoAccount" label="Автосоздание счета" horizontalFill="true"/>
				</msh:row>
				<msh:ifFormTypeIsView formName="contract_servedPersonForm">
				<msh:row>
					<msh:separator label="Информация о создании" colSpan="4"/>
				</msh:row>
				<msh:row>
					<msh:textField property="createDate" label="Дата"/>
					<msh:textField property="createTime" label="Время"/>
				</msh:row>
				<msh:row>
					<msh:textField property="createUsername" label="Пользователь"/>
				</msh:row>
				<msh:row>
					<msh:separator label="Информация о последней редакции" colSpan="4"/>
				</msh:row>
				<msh:row>
					<msh:textField property="editDate" label="Дата"/>
					<msh:textField property="editTime" label="Время"/>
				</msh:row>
				<msh:row>
					<msh:textField property="editUsername" label="Пользователь"/>
				</msh:row>
				</msh:ifFormTypeIsView>
			<msh:submitCancelButtonsRow colSpan="3" />
			</msh:panel>
		</msh:form>
<msh:ifFormTypeIsNotView formName="contract_servedPersonForm">
			<tags:contract_natPerson_new autoCompliteContractPerson="contractPerson" 
			divForButton="personButton" name="Person" title="Поиск персоны"></tags:contract_natPerson_new>
		</msh:ifFormTypeIsNotView>
	</tiles:put>
	<tiles:put name="title" type="string">
		<ecom:titleTrail mainMenu="Contract" beginForm="contract_servedPersonForm" />
	</tiles:put>
	<tiles:put name="side" type="string">
		<msh:ifFormTypeAreViewOrEdit formName="contract_servedPersonForm">
			<msh:sideMenu>
				<msh:sideLink key="ALT+2" params="id" action="/entityParentEdit-contract_servedPerson" name="Изменить" title="Изменить" roles="/Policy/Mis/Contract/MedContract/ServedPerson/Edit"/>
				<msh:sideLink key="ALT+DEL" params="id" action="/entityParentDelete-contract_servedPerson" name="Удалить" title="Удалить" roles="/Policy/Mis/Contract/MedContract/ServedPerson/Delete"/>
			</msh:sideMenu>
			<tags:contractMenu currentAction="medContract"/>
		</msh:ifFormTypeAreViewOrEdit>
	</tiles:put>
	<tiles:put type="string" name="javascript">
		<msh:ifFormTypeIsCreate formName="contract_servedPersonForm">
			<script type="text/javascript">
				$('autoAccount').checked=true ;
			</script>
		</msh:ifFormTypeIsCreate>
	
  	<msh:ifFormTypeIsNotView formName="contract_servedPersonForm">
  	<script type="text/javascript">
  	initPersonContractPersonDialog()</script>
  		</msh:ifFormTypeIsNotView>
  </tiles:put>
</tiles:insert>
