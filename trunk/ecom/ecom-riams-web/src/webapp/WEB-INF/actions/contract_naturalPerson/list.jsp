<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://struts.apache.org/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://www.nuzmsh.ru/tags/msh" prefix="msh" %>
<%@ taglib uri="http://www.ecom-ast.ru/tags/ecom" prefix="ecom" %>
<%@ taglib tagdir="/WEB-INF/tags" prefix="tags" %>

<tiles:insert page="/WEB-INF/tiles/mainLayout.jsp" flush="true" >
	<tiles:put name='title' type='string'>
		<msh:title mainMenu="Lpu">Список физических лиц</msh:title>
	</tiles:put>
	<tiles:put name='side' type='string'>
		<msh:sideMenu title="Добавить">
			<msh:sideLink key='ALT+N' roles="/Policy/Mis/Contract/ContractPerson/NaturalPerson/Create" params="" action="/entityPrepareCreate-contract_naturalPerson" title="Физическое лицо" name="Физическое лицо" />
		</msh:sideMenu>
		<tags:contractMenu currentAction="naturalPerson"/>
	</tiles:put>
	<tiles:put name='body' type='string' >
		<msh:table name="list" action="entityView-contract_naturalPerson.do" idField="id" hideTitle="true">
			<msh:tableColumn columnName="Пациента"  property="information" />
		</msh:table>
	</tiles:put>
	<tiles:put name="javascript" type="string">
		<script type="text/javascript">
			//document.location.href = "entityList-contract_contractPerson.do" ;
		</script>
	</tiles:put></tiles:insert>
