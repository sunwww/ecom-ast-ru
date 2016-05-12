<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://struts.apache.org/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://www.nuzmsh.ru/tags/msh" prefix="msh" %>
<%@ taglib uri="http://www.ecom-ast.ru/tags/ecom" prefix="ecom" %>
<%@ taglib tagdir="/WEB-INF/tags" prefix="tags" %>

<tiles:insert page="/WEB-INF/tiles/mainLayout.jsp" flush="true" >
	<tiles:put name='title' type='string'>
		<ecom:titleTrail mainMenu="Contract" beginForm="contract_priceGroupForm" title="Список позиций прейскуранта"/>
	</tiles:put>
	<tiles:put name='side' type='string'>
		<msh:sideMenu title="Добавить">
			<msh:sideLink key='ALT+N' roles="/Policy/Mis/Contract/PriceList/PriceGroup/Create" params="id" action="/entityParentPrepareCreate-contract_priceGroup" title="Добавить группу" name="Группу" />
		</msh:sideMenu>
		<tags:contractMenu currentAction="price"/>
	</tiles:put>
	<tiles:put name='body' type='string' >
		<msh:table name="list" action="entityParentView-contract_priceGroup.do" idField="id">
			<msh:tableColumn columnName="#" property="sn" />
			<msh:tableColumn columnName="Код" property="code" />
			<msh:tableColumn columnName="Название" property="name" />
		</msh:table>
	</tiles:put>
</tiles:insert>
