<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://struts.apache.org/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://www.nuzmsh.ru/tags/msh" prefix="msh" %>
<%@ taglib uri="http://www.ecom-ast.ru/tags/ecom" prefix="ecom" %>
<%@ taglib tagdir="/WEB-INF/tags" prefix="tags" %>

<tiles:insert page="/WEB-INF/tiles/mainLayout.jsp" flush="true" >
	<tiles:put name='title' type='string'>
		<msh:title mainMenu="Lpu" >Список договорных лиц</msh:title>
	</tiles:put>
	<tiles:put name='side' type='string'>
		<msh:sideMenu title="Добавить">
			<msh:sideLink key='ALT+2' roles="/Policy/Mis/Contract/ContractPerson/JuridicalPerson/Create" params="" action="/entityPrepareCreate-contract_naturalPerson" title="Физическое лицо" name="Физическое лицо" />
			<msh:sideLink key='ALT+3' roles="/Policy/Mis/Contract/ContractPerson/NaturalPerson/Create" params="" action="/entityPrepareCreate-contract_juridicalPerson" title="Юридическое лицо" name="Юридическое лицо" />
		</msh:sideMenu>
		<tags:contractMenu currentAction="temp"/>

	</tiles:put>
	<tiles:put name='body' type='string' >
		<msh:table name="list" action="entitySubclassView-contract_contractPerson.do" idField="id">
			<msh:tableColumn property="sn" columnName="#"/>
			<msh:tableColumn property="information" columnName="Информация о договорном лице"/>
		</msh:table>
	</tiles:put>
</tiles:insert>
