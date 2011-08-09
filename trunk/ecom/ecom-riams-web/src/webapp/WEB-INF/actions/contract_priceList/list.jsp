<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://struts.apache.org/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://www.nuzmsh.ru/tags/msh" prefix="msh" %>
<%@ taglib uri="http://www.ecom-ast.ru/tags/ecom" prefix="ecom" %>
<%@ taglib tagdir="/WEB-INF/tags" prefix="tags" %>

<tiles:insert page="/WEB-INF/tiles/mainLayout.jsp" flush="true" >
	<tiles:put name='title' type='string'>
		<msh:title mainMenu="Lpu" >Список прейскурантов</msh:title>
	</tiles:put>
	<tiles:put name='side' type='string'>
		<msh:sideMenu title="Добавить">
			<msh:sideLink key='ALT+N' roles="/Policy/Mis/Contract/PriceList/Create" action="/entityPrepareCreate-contract_priceList" title="Добавить прейскурант" name="Прейскурант" />
		</msh:sideMenu>
		<tags:contractMenu currentAction="price"/>
	</tiles:put>
	<tiles:put name='body' type='string' >
		<msh:table name="list" action="entityView-contract_priceList.do" idField="id">
			<msh:tableColumn columnName="#" property="sn" />
			<msh:tableColumn columnName="Название" property="name" />
			<msh:tableColumn columnName="Справочник типов цен" property="vocPriceInfo" />
			<msh:tableColumn columnName="Дата начала действия" property="dateFrom" />
			<msh:tableColumn columnName="Дата окончания действия" property="dateTo" />
		</msh:table>
	</tiles:put>
</tiles:insert>
