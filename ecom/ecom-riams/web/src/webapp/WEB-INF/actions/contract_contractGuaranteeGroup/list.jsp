<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://struts.apache.org/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://www.nuzmsh.ru/tags/msh" prefix="msh" %>
<%@ taglib uri="http://www.ecom-ast.ru/tags/ecom" prefix="ecom" %>
<%@ taglib tagdir="/WEB-INF/tags" prefix="tags" %>

<tiles:insert page="/WEB-INF/tiles/mainLayout.jsp" flush="true" >
	<tiles:put name='title' type='string'>
		<msh:title mainMenu="Contract" >Список групп гарантийных документов</msh:title>
	</tiles:put>
	<tiles:put name='side' type='string'>
		<msh:sideMenu title="Добавить">
			<msh:sideLink key='ALT+N' roles="/Policy/Mis/Contract/GroupRules/ContractGuaranteeGroup/Create" params="" action="/entityPrepareCreate-contract_contractGuaranteeGroup" title="Группа гарантийных документов по договору" name="Группа гарантийных документов по договору" />
		</msh:sideMenu>
		<tags:contractMenu currentAction="guaranteeGroup"/>
	</tiles:put>
	<tiles:put name='body' type='string' >
		<msh:table name="list" action="entityView-contract_contractGuaranteeGroup.do" idField="id">
			<msh:tableColumn columnName="Название" property="name" />
		</msh:table>
	</tiles:put>
</tiles:insert>
