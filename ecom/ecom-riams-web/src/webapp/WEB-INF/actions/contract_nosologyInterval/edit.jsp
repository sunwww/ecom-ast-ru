<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://struts.apache.org/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://www.nuzmsh.ru/tags/msh" prefix="msh" %>
<%@ taglib uri="http://www.ecom-ast.ru/tags/ecom" prefix="ecom" %>
<%@ taglib tagdir="/WEB-INF/tags" prefix="tags" %>

<tiles:insert page="/WEB-INF/tiles/mainLayout.jsp" flush="true">
	<tiles:put name="body" type="string">
		<msh:form action="/entityParentSaveGoParentView-contract_nosologyInterval.do" defaultField="name">
			<msh:hidden property="id" />
			<msh:hidden property="saveType" />
			<msh:hidden property="nosologyGroup" />
			<msh:hidden property="fromIdc10Code"/>
			<msh:hidden property="toIdc10Code"/>
			<msh:panel>
				<msh:row>
					<msh:textField size="150" property="name" label="Наименование"/>
				</msh:row>
				<msh:row>
					<msh:autoComplete size="150" property="fromCode" label="Начиная с кода" vocName="vocIdc10" horizontalFill="true" />
				</msh:row>
				<msh:row>
					<msh:autoComplete size="150" property="toCode" label="Заканчивая кодом" vocName="vocIdc10" horizontalFill="true" />
				</msh:row>
			<msh:submitCancelButtonsRow colSpan="4" />
			</msh:panel>
		</msh:form>
		<msh:ifFormTypeIsView formName="contract_nosologyIntervalForm">
		</msh:ifFormTypeIsView>
	</tiles:put>
	<tiles:put name="title" type="string">
		<ecom:titleTrail mainMenu="Patient" beginForm="contract_nosologyIntervalForm" />
	</tiles:put>
	<tiles:put name="side" type="string">
		<msh:ifFormTypeAreViewOrEdit formName="contract_nosologyIntervalForm">
			<msh:sideMenu>
				<msh:sideLink key="ALT+2" params="id" action="/entityParentEdit-contract_nosologyInterval" name="Изменить" title="Изменить" roles="/Policy/Mis/Contract/GroupRules/ContractNosologyGroup/NosologyInterval/Edit"/>
				<msh:sideLink key="ALT+DEL" params="id" action="/entityParentDelete-contract_nosologyInterval" name="Удалить" title="Удалить" roles="/Policy/Mis/Contract/GroupRules/ContractNosologyGroup/NosologyInterval/Delete"/>
			</msh:sideMenu>
			<tags:contractMenu currentAction="nosologyGroup"/>
		</msh:ifFormTypeAreViewOrEdit>
	</tiles:put>
	<tiles:put name="javascript" type="string">
	<msh:ifFormTypeIsNotView formName="contract_nosologyIntervalForm">
		<script type="text/javascript">
			fromCodeAutocomplete.addOnChangeCallback(function() {
				setMkbText('fromCode','fromIdc10Code');
			}) ;
			toCodeAutocomplete.addOnChangeCallback(function() {
				setMkbText('toCode','toIdc10Code');
			}) ;
			function setMkbText(aFieldMkb,aFieldText) {
	  			var val = $(aFieldMkb+'Name').value ;
	  			var ind = val.indexOf(' ') ;
	  			//alert(ind+' '+val)
	  			if (ind!=-1) {
	  				if ($(aFieldText).value=="") $(aFieldText).value=val.substring(0,ind) ;
	  			}
	  		}
			
		</script>
	</msh:ifFormTypeIsNotView>
	</tiles:put>
</tiles:insert>
