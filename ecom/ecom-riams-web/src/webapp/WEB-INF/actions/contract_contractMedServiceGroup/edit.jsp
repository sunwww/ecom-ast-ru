<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://struts.apache.org/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://www.nuzmsh.ru/tags/msh" prefix="msh" %>
<%@ taglib uri="http://www.ecom-ast.ru/tags/ecom" prefix="ecom" %>
<%@ taglib tagdir="/WEB-INF/tags" prefix="tags" %>

<tiles:insert page="/WEB-INF/tiles/mainLayout.jsp" flush="true">
	<tiles:put name="body" type="string">
		<msh:form action="/entitySaveGoView-contract_contractMedServiceGroup.do" defaultField="name">
			<msh:hidden property="id" />
			<msh:hidden property="saveType" />
			<msh:panel>
				<msh:row>
					<msh:textField property="name" label="Название" size="150"/>
				</msh:row>
			<msh:submitCancelButtonsRow colSpan="4" />
			</msh:panel>
		</msh:form>
		<msh:ifFormTypeIsView formName="contract_contractMedServiceGroupForm">
			<msh:section title="Интервалы медицинских услуг">
			<ecom:parentEntityListAll formName="contract_medServiceIntervalForm" attribute="medServiceGroup" />
				<msh:table name="medServiceGroup" action="entityParentView-contract_medServiceInterval.do" idField="id">
					<msh:tableColumn columnName="id" property="id"/>
				</msh:table>
			</msh:section>
			<msh:section title="Договорные правила, в которых используется данное ограничение">
				<ecom:webQuery nativeSql="select cr.id,mc.contractNumber as mcname,cr.dateFrom as crdatefrom,cr.dateTo as crdateto from ContractRule cr left join MedContract mc on mc.id=cr.contract_id where cr.medserviceGroup_id=${param.id}" name="contractRule"/>
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
		<ecom:titleTrail mainMenu="Patient" beginForm="contract_contractMedServiceGroupForm" />
	</tiles:put>
	<tiles:put name="side" type="string">
		<msh:ifFormTypeAreViewOrEdit formName="contract_contractMedServiceGroupForm">
			<msh:sideMenu>
				<msh:sideLink key="ALT+2" params="id" action="/entityParentEdit-contract_contractMedServiceGroup" name="Изменить" title="Изменить" roles="/Policy/Mis/Contract/GroupRules/ContractMedServiceGroup/Edit"/>
				<msh:sideLink key="ALT+DEL" params="id" action="/entityParentDelete-contract_contractMedServiceGroup" name="Удалить" title="Удалить" roles="/Policy/Mis/Contract/GroupRules/ContractMedServiceGroup/Delete"/>
			</msh:sideMenu>
			<msh:sideMenu title="Добавить" >
				<msh:sideLink key="ALT+N" params="id" action="/entityParentPrepareCreate-contract_medServiceInterval" name="Интервалы медицинских услуг" title="Интервалы медицинских услуг" roles="/Policy/Mis/Contract/GroupRules/ContractMedServiceGroup/MedServiceInterval/Create"/>
			</msh:sideMenu>
			<tags:contractMenu currentAction="medServiceGroup"/>
		</msh:ifFormTypeAreViewOrEdit>
	</tiles:put>
</tiles:insert>
