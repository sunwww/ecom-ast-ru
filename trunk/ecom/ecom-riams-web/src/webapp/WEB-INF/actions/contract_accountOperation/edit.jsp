<%@page import="ru.ecom.ejb.services.query.WebQueryResult"%>
<%@page import="java.util.List"%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://struts.apache.org/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://www.nuzmsh.ru/tags/msh" prefix="msh" %>
<%@ taglib uri="http://www.ecom-ast.ru/tags/ecom" prefix="ecom" %>
<%@ taglib tagdir="/WEB-INF/tags" prefix="tags" %>

<tiles:insert page="/WEB-INF/tiles/mainLayout.jsp" flush="true">
	<tiles:put name="body" type="string">
		<msh:form action="/entityParentSaveGoView-contract_accountOperation.do" defaultField="typeName">
			<msh:hidden property="id" />
			<msh:hidden property="saveType" />
			<msh:hidden property="account" />
			<msh:hidden property="createDate" />
			<msh:hidden property="createTime" />
			<msh:hidden property="createUsername" />
			<msh:hidden property="editDate" />
			<msh:hidden property="editTime" />
			<msh:hidden property="editUsername" />
			<msh:hidden property="workFunction"/>
			<msh:panel colsWidth="10%,10%,10%">
				<msh:row>
					<msh:autoComplete property="type" label="Тип операции" vocName="vocAccountOperation" fieldColSpan="3" horizontalFill="true" />
				</msh:row>
				<msh:row>
					<msh:textField property="cost" label="Стоимость"/>
					<msh:textField property="discount" label="Скидка"/>
				</msh:row>

				<ecom:webQuery name="sumCost" nativeSql="
							select sum(CAMS.countMedService*CAMS.cost),1   
							from ContractAccountMedService CAMS 
							where CAMS.account_id= '${param.id }'
				"/>
				<!-- 
				<msh:row>
					<msh:autoComplete property="medcase" label="Случай медицинского обслуживания" vocName="MedCase" horizontalFill="true" />
				</msh:row>
				-->
				<msh:ifInRole roles="/Policy/Mis/Contract/MedContract/ServedPerson/ContractAccount/ContractAccountOperation/EditOperationInfo">
					<msh:ifFormTypeIsNotView formName="contract_accountOperationForm">
						<msh:row>
							<msh:textField property="operationDate" label="Дата операции"/>
							<msh:textField property="operationTime" label="Время"/>
						</msh:row>
					</msh:ifFormTypeIsNotView>
				</msh:ifInRole>
				<msh:ifNotInRole roles="/Policy/Mis/Contract/MedContract/ServedPerson/ContractAccount/ContractAccountOperation/EditOperationInfo">
					<msh:hidden property="operationDate" />
					<msh:hidden property="operationTime" />
				</msh:ifNotInRole>
				<msh:ifFormTypeIsView formName="contract_accountOperationForm">
				<msh:row>
					<msh:textField property="operationDate" label="Дата операции"/>
					<msh:textField property="operationTime" label="Время"/>
				</msh:row>
					<msh:row>
						<msh:autoComplete property="workFunction" label="Оператор" vocName="workFunction" fieldColSpan="3" size="100" />
					</msh:row>
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
		<msh:ifFormTypeIsView formName="contract_accountOperationForm">
		</msh:ifFormTypeIsView>
		<% 
		List list= (List)request.getAttribute("sumCost");
		out.println("<script>");
		out.println("var sum = 0;");
		for (int i=0 ; i<list.size();i++) {
			WebQueryResult res = (WebQueryResult)list.get(i) ;
			out.println("sum = sum*1 + ("+res.get1()+")*1;" ); 
		}	
		out.println("$('cost').value = sum"); 
		out.println("</script>");
		%>
	</tiles:put>
	<tiles:put name="title" type="string">
		<ecom:titleTrail mainMenu="Contract" beginForm="contract_accountOperationForm" />
	</tiles:put>
	<tiles:put name="side" type="string">
		<msh:ifFormTypeAreViewOrEdit formName="contract_accountOperationForm">
			<msh:sideMenu>
				<msh:sideLink key="ALT+2" params="id" action="/entityParentEdit-contract_accountOperation" name="Изменить" title="Изменить" roles="/Policy/Mis/Contract/MedContract/ServedPerson/ContractAccount/ContractAccountOperation/Edit"/>
				<msh:sideLink key="ALT+DEL" params="id" action="/entityParentDelete-contract_accountOperation" name="Удалить" title="Удалить" roles="/Policy/Mis/Contract/MedContract/ServedPerson/ContractAccount/ContractAccountOperation/Delete"/>
			</msh:sideMenu>
			<tags:contractMenu currentAction="medContract"/>
		</msh:ifFormTypeAreViewOrEdit>
	</tiles:put>
</tiles:insert>
