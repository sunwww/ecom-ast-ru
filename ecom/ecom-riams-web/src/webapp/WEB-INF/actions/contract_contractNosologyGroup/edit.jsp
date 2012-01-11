<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://struts.apache.org/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://www.nuzmsh.ru/tags/msh" prefix="msh" %>
<%@ taglib uri="http://www.ecom-ast.ru/tags/ecom" prefix="ecom" %>
<%@ taglib tagdir="/WEB-INF/tags" prefix="tags" %>

<tiles:insert page="/WEB-INF/tiles/mainLayout.jsp" flush="true">
	<tiles:put name="body" type="string">
		<msh:form action="/entitySaveGoView-contract_contractNosologyGroup.do" defaultField="name">
			<msh:hidden property="id" />
			<msh:hidden property="saveType" />
			<msh:panel>
				<msh:row>
					<msh:textField property="name" label="Название" size="150"/>
				</msh:row>
			<msh:submitCancelButtonsRow colSpan="4" />
			</msh:panel>
		</msh:form>
		<msh:ifFormTypeIsView formName="contract_contractNosologyGroupForm">
			<msh:section title="Нозологические интервалы">
			<ecom:parentEntityListAll formName="contract_nosologyIntervalForm" attribute="nosologyGroup" />
				<msh:table name="nosologyGroup" action="entityParentView-contract_nosologyInterval.do" idField="id">
					<msh:tableColumn columnName="Наименование" property="name" />
					<msh:tableColumn columnName="Начиная с кода" property="fromIdc10Code" />
					<msh:tableColumn columnName="Заканчивая кодом" property="toIdc10Code" />
				</msh:table>
			</msh:section>
			<msh:section title="Договорные правила, в которых используется данное ограничение">
				<ecom:webQuery nativeSql="select cr.id,mc.contractNumber as mcname,cr.dateFrom as crdatefrom,cr.dateTo as crdateto from ContractRule cr left join MedContract mc on mc.id=cr.contract_id where cr.nosologyGroup_id=${param.id}" name="contractRule"/>
				<msh:table name="contractRule" action="entityParentView-contract_contractRule.do" idField="1">
					<msh:tableColumn property="sn" columnName="#"/>
					<msh:tableColumn property="2" columnName="Номер договора"/>
					<msh:tableColumn property="3" columnName="Дата начала действия правила"/>
					<msh:tableColumn property="4" columnName="Дата окончания"/>
				</msh:table>
			</msh:section>
		</msh:ifFormTypeIsView>
	</tiles:put>
	<tiles:put name="title" type="string">
		<ecom:titleTrail mainMenu="Contract" beginForm="contract_contractNosologyGroupForm" />
	</tiles:put>
	<tiles:put name="side" type="string">
		<msh:ifFormTypeAreViewOrEdit formName="contract_contractNosologyGroupForm">
			<msh:sideMenu>
				<msh:sideLink key="ALT+2" params="id" action="/entityParentEdit-contract_contractNosologyGroup" name="Изменить" title="Изменить" roles="/Policy/Mis/Contract/GroupRules/ContractNosologyGroup/Edit"/>
				<msh:sideLink key="ALT+DEL" params="id" action="/entityParentDelete-contract_contractNosologyGroup" name="Удалить" title="Удалить" roles="/Policy/Mis/Contract/GroupRules/ContractNosologyGroup/Delete"/>
			</msh:sideMenu>
			<msh:sideMenu title="Добавить" >
				<msh:sideLink key="ALT+N" params="id" action="/entityParentPrepareCreate-contract_nosologyInterval" name="Нозологические интервалы" title="Нозологические интервалы" roles="/Policy/Mis/Contract/GroupRules/ContractNosologyGroup/NosologyInterval/Create"/>
			</msh:sideMenu>
			<tags:contractMenu currentAction="nosologyGroup"/>
		</msh:ifFormTypeAreViewOrEdit>
	</tiles:put>
</tiles:insert>
