<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://struts.apache.org/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://www.nuzmsh.ru/tags/msh" prefix="msh" %>
<%@ taglib uri="http://www.ecom-ast.ru/tags/ecom" prefix="ecom" %>
<%@ taglib tagdir="/WEB-INF/tags" prefix="tags" %>

<tiles:insert page="/WEB-INF/tiles/mainLayout.jsp" flush="true">
	<tiles:put name="body" type="string">
		<msh:form action="/entityParentSaveGoParentView-contract_medServiceInterval.do" defaultField="name">
			<msh:hidden property="id" />
			<msh:hidden property="saveType" />
			<msh:hidden property="medServiceGroup" />
			<msh:hidden property="toMedServiceCode"/>
			<msh:hidden property="fromMedServiceCode"/>
			<msh:panel>
				<msh:row>
					<msh:autoComplete size="150" property="fromCode" label="Начиная с кода" vocName="medService" horizontalFill="true" />
				</msh:row>
				<msh:row>
					<msh:autoComplete size="150" property="toCode" label="Заканчивая кодом" vocName="medService" horizontalFill="true" />
				</msh:row>
			<msh:submitCancelButtonsRow colSpan="2" />
			</msh:panel>
		</msh:form>
		<msh:ifFormTypeIsView formName="contract_medServiceIntervalForm">
		</msh:ifFormTypeIsView>
	</tiles:put>
	<tiles:put name="title" type="string">
		<ecom:titleTrail mainMenu="Contract" beginForm="contract_medServiceIntervalForm" />
	</tiles:put>
	<tiles:put name="side" type="string">
		<msh:ifFormTypeAreViewOrEdit formName="contract_medServiceIntervalForm">
			<msh:sideMenu>
				<msh:sideLink key="ALT+2" params="id" action="/entityParentEdit-contract_medServiceInterval" name="Изменить" title="Изменить" roles="/Policy/Mis/Contract/GroupRules/ContractMedServiceGroup/MedServiceInterval/Edit"/>
				<msh:sideLink key="ALT+DEL" params="id" action="/entityParentDelete-contract_medServiceInterval" name="Удалить" title="Удалить" roles="/Policy/Mis/Contract/GroupRules/ContractMedServiceGroup/MedServiceInterval/Delete"/>
			</msh:sideMenu>
			<tags:contractMenu currentAction="medServiceGroup"/>
		</msh:ifFormTypeAreViewOrEdit>
	</tiles:put>
</tiles:insert>
