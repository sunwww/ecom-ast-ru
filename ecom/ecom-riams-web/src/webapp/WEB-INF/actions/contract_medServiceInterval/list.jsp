<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://struts.apache.org/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://www.nuzmsh.ru/tags/msh" prefix="msh" %>
<%@ taglib uri="http://www.ecom-ast.ru/tags/ecom" prefix="ecom" %>
<%@ taglib tagdir="/WEB-INF/tags" prefix="tags" %>

<tiles:insert page="/WEB-INF/tiles/mainLayout.jsp" flush="true" >
	<tiles:put name='title' type='string'>
		<ecom:titleTrail mainMenu="Contract" beginForm="contract_contractMedServiceGroupForm" title="Список интервалов"/>
	</tiles:put>
	<tiles:put name='side' type='string'>
		<msh:sideMenu title="Добавить">
			<msh:sideLink key='ALT+N' roles="/Policy/Mis/Contract/GroupRules/ContractMedServiceGroup/MedServiceInterval/Create" params="id" action="/entityParentPrepareCreate-contract_medserviceInterval" title="Интервал" name="Интервал" />
		</msh:sideMenu>
		<tags:contractMenu currentAction="medServiceGroup"/>
	</tiles:put>
	<tiles:put name='body' type='string' >
		<msh:table name="list" action="entityView-contract_medserviceInterval.do" idField="id">
			<msh:tableColumn columnName="Название" property="name" />
			<msh:tableColumn columnName="Маска кодов мед. услуг" property="medServiceMask" />
			<msh:tableColumn columnName="Начиная с кода" property="fromMedServiceCode" />
			<msh:tableColumn columnName="Заканчивая кодом" property="toMedServiceCode" />
		</msh:table>
	</tiles:put>
</tiles:insert>
