<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://struts.apache.org/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://www.nuzmsh.ru/tags/msh" prefix="msh" %>
<%@ taglib uri="http://www.ecom-ast.ru/tags/ecom" prefix="ecom" %>
<%@ taglib tagdir="/WEB-INF/tags" prefix="tags" %>

<tiles:insert page="/WEB-INF/tiles/mainLayout.jsp" flush="true" >
	<tiles:put name='title' type='string'>
		<ecom:titleTrail mainMenu="Lpu" beginForm="contract_contractMedPolicyForm" title="Список Медицинский полис по договору"/>
	</tiles:put>
	<tiles:put name='side' type='string'>
		<msh:sideMenu title="Добавить">
			<msh:sideLink key='ALT+N' roles="/Policy/Mis/Contract/MedContract/ContractGuarantee/ContractMedPolicy/Create" params="id" action="/entityPrepareCreate-contract_contractMedPolicy" title="Медицинский полис по договору" name="Медицинский полис по договору" />
		</msh:sideMenu>
		<tags:contractMenu currentAction="medContract"/>
	</tiles:put>
	<tiles:put name='body' type='string' >
		<msh:table name="list" action="entityView-contract_contractMedPolicy.do" idField="id">
			<msh:tableColumn columnName="Медицинский полис" property="medPolicy" />
			<msh:tableColumn columnName="Фамилия" property="lastname" />
			<msh:tableColumn columnName="Имя" property="firstname" />
			<msh:tableColumn columnName="Отчество" property="middlename" />
			<msh:tableColumn columnName="День рождения" property="birthday" />
			<msh:tableColumn columnName="Серия" property="series" />
			<msh:tableColumn columnName="Номер" property="number" />
			<msh:tableColumn columnName="Дата начала действия" property="dateFrom" />
			<msh:tableColumn columnName="Дата окончания действия" property="dateTo" />
			<msh:tableColumn columnName="Дата объявления недействительности" property="nullityDate" />
			<msh:tableColumn columnName="Программа обслуживания" property="serviceProgram" />
			<msh:tableColumn columnName="Статус обслуживаемой персоны" property="servedPersonStatus" />
			<msh:tableColumn columnName="Территория" property="territory" />
		</msh:table>
	</tiles:put>
</tiles:insert>
