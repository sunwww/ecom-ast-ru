<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://struts.apache.org/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://www.nuzmsh.ru/tags/msh" prefix="msh" %>
<%@ taglib uri="http://www.ecom-ast.ru/tags/ecom" prefix="ecom" %>
<%@ taglib tagdir="/WEB-INF/tags" prefix="tags" %>

<tiles:insert page="/WEB-INF/tiles/mainLayout.jsp" flush="true" >
	<tiles:put name='title' type='string'>
		<ecom:titleTrail mainMenu="Contract" beginForm="contract_medContractForm" title="Список гарантийных писем по договору"/>
	</tiles:put>
	<tiles:put name='side' type='string'>
		<msh:sideMenu title="Добавить">
			<msh:sideLink key='ALT+N' roles="/Policy/Mis/Contract/MedContract/ContractGuarantee/ContractGuaranteeLetter/Create" params="id" action="/entityParentPrepareCreate-contract_contractGuaranteeLetter" title="Гарантийное письмо по договору" name="Гарантийное письмо по договору" />
		</msh:sideMenu>
	</tiles:put>
	<tiles:put name='body' type='string' >
		<msh:table name="list" action="entityParentView-contract_contractGuaranteeLetter.do" idField="id">
			<msh:tableColumn property="id" columnName="id"/>
		</msh:table>
	</tiles:put>
</tiles:insert>
