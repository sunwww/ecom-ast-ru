<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://struts.apache.org/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://www.nuzmsh.ru/tags/msh" prefix="msh" %>
<%@ taglib uri="http://www.ecom-ast.ru/tags/ecom" prefix="ecom" %>
<%@ taglib tagdir="/WEB-INF/tags" prefix="tags" %>

<tiles:insert page="/WEB-INF/tiles/mainLayout.jsp" flush="true" >
	<tiles:put name='title' type='string'>
		<ecom:titleTrail mainMenu="Contract" beginForm="contract_pricePositionForm" title="Список Медицинская услуга прескуранта"/>
	</tiles:put>
	<tiles:put name='side' type='string'>
		<msh:sideMenu title="Добавить">
			<msh:sideLink key='ALT+N' roles="/Policy/Mis/Contract/PriceList/PricePosition/PriceMedService/Create" params="id" action="/entityParentPrepareCreate-contract_priceMedService" title="Добавить медицинскую услугу прескуранта" name="Медицинскую услугу прескуранта" />
		</msh:sideMenu>
		<tags:contractMenu currentAction="price"/>
	</tiles:put>
	<tiles:put name='body' type='string' >
		<msh:table name="list" action="entityParentView-contract_priceMedService.do" idField="id">
			<msh:tableColumn columnName="#" property="sn" />
			<msh:tableColumn columnName="Медицинская услуга" property="medServiceInfo" />
			<msh:tableColumn columnName="Дата начала действия" property="dateFrom" />
			<msh:tableColumn columnName="Дата окончания действия" property="dateTo" />
		</msh:table>
	</tiles:put>
</tiles:insert>
