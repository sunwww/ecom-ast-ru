<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://struts.apache.org/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://www.nuzmsh.ru/tags/msh" prefix="msh" %>
<%@ taglib uri="http://www.ecom-ast.ru/tags/ecom" prefix="ecom" %>
<%@ taglib tagdir="/WEB-INF/tags" prefix="tags" %>

<tiles:insert page="/WEB-INF/tiles/mainLayout.jsp" flush="true">
	<tiles:put name="body" type="string">
		<msh:form action="/entitySaveGoView-contract_nosologyGroup.do" defaultField="name">
			<msh:hidden property="id" />
			<msh:hidden property="saveType" />
			<msh:panel>
				<msh:row>
					<msh:textField property="name" label="Название" size="150"/>
				</msh:row>
			<msh:submitCancelButtonsRow colSpan="4" />
			</msh:panel>
		</msh:form>
		<msh:ifFormTypeIsView formName="contract_nosologyGroupForm">
			<msh:section title="Нозологические интервалы">
			<ecom:parentEntityListAll formName="contract_nosologyIntervalForm" attribute="nosologyGroup" />
				<msh:table name="nosologyGroup" action="entityParentView-contract_nosologyInterval.do" idField="id">
					<msh:tableColumn columnName="Начиная с кода" property="fromIdc10Code" />
					<msh:tableColumn columnName="Заканчивая кодом" property="toIdc10Code" />
				</msh:table>
			</msh:section>
			<msh:section title="Договорные правила, в которых используется данное ограничение">
				<ecom:webQuery nativeSql="select cr.id,mc.contractNumber as mcname,cr.dateFrom as crdatefrom,cr.dateTo as crdateto from ContractRule cr left join MedContract mc on mc.id=cr.contract_id where cr.nosologyGroup_id=${param.id}" name="contractRule"/>
				<msh:table name="contractRule" action="entityParentView-contract_rule.do" idField="1">
					<msh:tableColumn property="sn" columnName="#"/>
					<msh:tableColumn property="2" columnName="Номер договора"/>
					<msh:tableColumn property="3" columnName="Дата начала действия правила"/>
					<msh:tableColumn property="4" columnName="Дата окончания"/>
				</msh:table>
			</msh:section>
		</msh:ifFormTypeIsView>
		
		<msh:ifFormTypeIsNotView formName="contract_nosologyGroupForm">
		<script type="text/javascript">
		eventutil.addEventListener($('rangeMkb'), eventutil.EVENT_KEY_UP, 
	 		  	function() {$('rangeMkb').value = latRus($('rangeMkb').value) ;}
			) ;
		function latRus(aText) {
				aText=aText.toUpperCase() ;
				aText=replaceAll(aText,"Й", "Q" ,1) ;
				aText=replaceAll(aText,"Ц", "W" ,1) ;
		    	aText=replaceAll(aText,"У","E"  ,1) ;
		    	aText=replaceAll(aText, "К", "R" ,1) ;
		    	aText=replaceAll(aText,"Е", "T"  ,1) ;
		    	aText=replaceAll(aText, "Ф","A" ,1) ;
		    	aText=replaceAll(aText, "Ы", "S",1) ;
		    	aText=replaceAll(aText,"В", "D" ,1 ) ;
		    	aText=replaceAll(aText,"А","F" ,1) ;
		    	aText=replaceAll(aText,"П","G"  ,1) ;
		    	aText=replaceAll(aText,"Я","Z" ,1 ) ;
		    	aText=replaceAll(aText,"Ч","X"  ,1) ;
		    	aText=replaceAll(aText,"С","C" ,1 ) ;
		    	aText=replaceAll(aText, "М", "V" ,1) ;
		    	aText=replaceAll(aText,"И", "B",1 ) ;
		    	aText=replaceAll(aText,"Н", "Y" ,1 ) ;
		    	aText=replaceAll(aText,"Г", "U" ,1 ) ;
		    	aText=replaceAll(aText,"Ш", "I" ,1 ) ;
		    	aText=replaceAll(aText,"Щ", "O" ,1 ) ;
		    	aText=replaceAll(aText,"З","P",1 ) ;
		    	aText=replaceAll(aText, "Р","H",1 ) ;
		    	aText=replaceAll(aText,"О", "J" ,1 ) ;
		    	aText=replaceAll(aText,"Л","K" ,1 ) ;
		    	aText=replaceAll(aText,"Д", "L",1 ) ;
		    	aText=replaceAll(aText,"Т","N" ,1) ;
		    	aText=replaceAll(aText, "Ь","M",1 ) ;
		    	aText=replaceAll(aText, "Ю",".",1 ) ;
		    	aText=replaceAll(aText, "Б","," ,1) ;
		    	aText=replaceAll(aText, "Х","[" ,1) ;
		    	aText=replaceAll(aText, "Ъ","]",1 ) ;
		    	aText=replaceAll(aText, "Ж",";",1 ) ;
		    	aText=replaceAll(aText, "Э","'",1 ) ;
		    	return aText ;
			}
			function replaceAll(aText,aSymbRep,aSymbIs,aRedict) {
				var sym1=aSymbRep,sym2=aSymbIs;
				if (+aRedict<1) {
					sym2=aSymbRep,sym1=aSymbIs;
				} 
				while (aText.indexOf(sym1)>-1) {
					aText = aText.replace(sym1,sym2) ;
				}
				return aText.toUpperCase() ;
			}
		</script>
		</msh:ifFormTypeIsNotView>
	</tiles:put>
	<tiles:put name="title" type="string">
		<ecom:titleTrail mainMenu="Contract" beginForm="contract_nosologyGroupForm" />
	</tiles:put>
	<tiles:put name="side" type="string">
		<msh:ifFormTypeAreViewOrEdit formName="contract_nosologyGroupForm">
			<msh:sideMenu>
				<msh:sideLink key="ALT+2" params="id" action="/entityParentEdit-contract_nosologyGroup" name="Изменить" title="Изменить" roles="/Policy/Mis/Contract/GroupRules/ContractNosologyGroup/Edit"/>
				<msh:sideLink key="ALT+DEL" params="id" action="/entityParentDelete-contract_nosologyGroup" name="Удалить" title="Удалить" roles="/Policy/Mis/Contract/GroupRules/ContractNosologyGroup/Delete"/>
			</msh:sideMenu>
			<msh:sideMenu title="Добавить" >
				<msh:sideLink key="ALT+N" params="id" action="/entityParentPrepareCreate-contract_nosologyInterval" name="Нозологические интервалы" title="Нозологические интервалы" roles="/Policy/Mis/Contract/GroupRules/ContractNosologyGroup/NosologyInterval/Create"/>
			</msh:sideMenu>
			<tags:contractMenu currentAction="nosologyGroup"/>
		</msh:ifFormTypeAreViewOrEdit>
	</tiles:put>
</tiles:insert>
