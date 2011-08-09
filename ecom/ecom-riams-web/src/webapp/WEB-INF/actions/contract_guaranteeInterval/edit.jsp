<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://struts.apache.org/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://www.nuzmsh.ru/tags/msh" prefix="msh" %>
<%@ taglib uri="http://www.ecom-ast.ru/tags/ecom" prefix="ecom" %>
<%@ taglib tagdir="/WEB-INF/tags" prefix="tags" %>

<tiles:insert page="/WEB-INF/tiles/mainLayout.jsp" flush="true">
	<tiles:put name="body" type="string">
		<msh:form action="/entityParentSaveGoParentView-contract_guaranteeInterval.do" defaultField="name">
			<msh:hidden property="id" />
			<msh:hidden property="saveType" />
			<msh:hidden property="guaranteeGroup" />
			<msh:panel>
				<msh:row>
					<msh:textField property="name" label="Название" fieldColSpan="3" horizontalFill="true"/>
				</msh:row>
				<msh:row>
					<msh:textField property="series" label="Серия"/>
				</msh:row>
				<msh:row>
					<msh:textField property="numberMask" label="Маска выбора номеров" fieldColSpan="3" horizontalFill="true"/>
				</msh:row>
				<msh:row>
					<msh:textField property="fromNumber" label="Начиная с номера"/>
					<msh:textField property="toNumber" label="Заканчивая номером"/>
				</msh:row>
				<msh:row>
					<msh:autoComplete fieldColSpan="3" property="serviceProgram" label="Программа обслуживания" vocName="vocServiceProgram" horizontalFill="true" />
				</msh:row>
				<msh:row>
					<msh:autoComplete fieldColSpan="3" property="servedPersonStatus" label="Статус обслуживаемой персоны" vocName="vocServedPersonStatus" horizontalFill="true" />
				</msh:row>


			<msh:submitCancelButtonsRow colSpan="4" />
			</msh:panel>
		</msh:form>
		<msh:ifFormTypeIsView formName="contract_guaranteeIntervalForm">
		</msh:ifFormTypeIsView>
	</tiles:put>
	<tiles:put name="title" type="string">
		<ecom:titleTrail mainMenu="Patient" beginForm="contract_guaranteeIntervalForm" />
	</tiles:put>
	<tiles:put name="side" type="string">
		<msh:ifFormTypeAreViewOrEdit formName="contract_guaranteeIntervalForm">
			<msh:sideMenu>
				<msh:sideLink key="ALT+2" params="id" action="/entityParentEdit-contract_guaranteeInterval" name="Изменить" title="Изменить" roles="/Policy/Mis/Contract/GroupRules/ContractGuaranteeGroup/GuaranteeInterval/Edit"/>
				<msh:sideLink key="ALT+DEL" params="id" action="/entityParentDelete-contract_guaranteeInterval" name="Удалить" title="Удалить" roles="/Policy/Mis/Contract/GroupRules/ContractGuaranteeGroup/GuaranteeInterval/Delete"/>
			</msh:sideMenu>
			<tags:contractMenu currentAction="guaranteeGroup"/>
		</msh:ifFormTypeAreViewOrEdit>
	</tiles:put>
</tiles:insert>
