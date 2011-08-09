<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://struts.apache.org/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://www.nuzmsh.ru/tags/msh" prefix="msh" %>
<%@ taglib uri="http://www.ecom-ast.ru/tags/ecom" prefix="ecom" %>
<%@ taglib tagdir="/WEB-INF/tags" prefix="tags" %>

<tiles:insert page="/WEB-INF/tiles/mainLayout.jsp" flush="true" >
	<tiles:put name='title' type='string'>
		<ecom:titleTrail mainMenu="Lpu" beginForm="contract_contractGuaranteeGroupForm" title="Список интервалов гарантийных документов"/>
	</tiles:put>
	<tiles:put name='side' type='string'>
		<msh:sideMenu title="Добавить">
			<msh:sideLink key='ALT+N' roles="/Policy/Mis/Contract/GroupRules/ContractGuaranteeGroup/GuaranteeInterval/Create" params="id" action="/entityParentPrepareCreate-contract_guaranteeInterval" title="Интервал гарантийных документов" name="Интервал гарантийных документов" />
		</msh:sideMenu>
		<tags:contractMenu currentAction="guaranteeGroup"/>
	</tiles:put>
	<tiles:put name='body' type='string' >
		<msh:table name="list" action="entityView-contract_guaranteeInterval.do" idField="id">
			<msh:tableColumn columnName="#" property="sn" />
			<msh:tableColumn columnName="Название" property="name" />
			<msh:tableColumn columnName="Серия" property="series" />
			<msh:tableColumn columnName="Маска выбора номеров" property="numberMask" />
			<msh:tableColumn columnName="Начиная с номера" property="fromNumber" />
			<msh:tableColumn columnName="Заканчивая номером" property="toNumber" />
			<msh:tableColumn columnName="Программа обслуживания" property="serviceProgramInfo" />
			<msh:tableColumn columnName="Статус обслуживаемой персоны" property="servedPersonStatusInfo" />
		</msh:table>
	</tiles:put>
</tiles:insert>
