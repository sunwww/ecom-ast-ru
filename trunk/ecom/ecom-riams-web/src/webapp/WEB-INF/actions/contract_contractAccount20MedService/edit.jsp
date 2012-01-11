<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://struts.apache.org/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://www.nuzmsh.ru/tags/msh" prefix="msh" %>
<%@ taglib uri="http://www.ecom-ast.ru/tags/ecom" prefix="ecom" %>
<%@ taglib tagdir="/WEB-INF/tags" prefix="tags" %>

<tiles:insert page="/WEB-INF/tiles/mainLayout.jsp" flush="true">
	<tiles:put name="body" type="string">
		<msh:form action="/entityParentSaveGoParentView-contract_contractAccount20MedService.do" defaultField="typeName">
			<msh:hidden property="id" />
			<msh:hidden property="saveType" />
			<msh:hidden property="account" />
			<msh:hidden property="additionMedService" />
			<msh:panel>
				<msh:row>
					<td colspan="6"><div id="divAllCount1"/></td>
				</msh:row>
				<msh:row>
					<msh:autoComplete property="medService" parentId="contract_contractAccount20MedServiceForm.account" label="Услуга" vocName="priceMedServiceByContractAccout" size="50" />
					<msh:textField property="cost" label="Цена" viewOnlyField="true"/>
					<msh:textField property="countMedService" label="Кол-во"/>
				</msh:row>
				<msh:row>
					<msh:autoComplete property="medService1" parentId="contract_contractAccount20MedServiceForm.account" label="Услуга" vocName="priceMedServiceByContractAccout" size="50" />
					<msh:textField property="cost1" label="Цена" viewOnlyField="true"/>
					<msh:textField property="countMedService1" label="Кол-во"/>
				</msh:row>
				<msh:row>
					<msh:autoComplete property="medService2" parentId="contract_contractAccount20MedServiceForm.account" label="Услуга" vocName="priceMedServiceByContractAccout" size="50" />
					<msh:textField property="cost2" label="Цена" viewOnlyField="true"/>
					<msh:textField property="countMedService2" label="Кол-во"/>
				</msh:row>
				<msh:row>
					<msh:autoComplete property="medService3" parentId="contract_contractAccount20MedServiceForm.account" label="Услуга" vocName="priceMedServiceByContractAccout" size="50" />
					<msh:textField property="cost3" label="Цена" viewOnlyField="true"/>
					<msh:textField property="countMedService3" label="Кол-во"/>
				</msh:row>
				<msh:row>
					<msh:autoComplete property="medService4" parentId="contract_contractAccount20MedServiceForm.account" label="Услуга" vocName="priceMedServiceByContractAccout" size="50" />
					<msh:textField property="cost4" label="Цена" viewOnlyField="true"/>
					<msh:textField property="countMedService4" label="Кол-во"/>
				</msh:row>
				<msh:row>
					<msh:autoComplete property="medService5" parentId="contract_contractAccount20MedServiceForm.account" label="Услуга" vocName="priceMedServiceByContractAccout" size="50" />
					<msh:textField property="cost5" label="Цена" viewOnlyField="true"/>
					<msh:textField property="countMedService5" label="Кол-во"/>
				</msh:row>
				<msh:row>
					<msh:autoComplete property="medService6" parentId="contract_contractAccount20MedServiceForm.account" label="Услуга" vocName="priceMedServiceByContractAccout" size="50" />
					<msh:textField property="cost6" label="Цена" viewOnlyField="true"/>
					<msh:textField property="countMedService6" label="Кол-во"/>
				</msh:row>
				<msh:row>
					<msh:autoComplete property="medService7" parentId="contract_contractAccount20MedServiceForm.account" label="Услуга" vocName="priceMedServiceByContractAccout" size="50" />
					<msh:textField property="cost7" label="Цена" viewOnlyField="true"/>
					<msh:textField property="countMedService7" label="Кол-во"/>
				</msh:row>
				<msh:row>
					<msh:autoComplete property="medService8" parentId="contract_contractAccount20MedServiceForm.account" label="Услуга" vocName="priceMedServiceByContractAccout" size="50" />
					<msh:textField property="cost8" label="Цена" viewOnlyField="true"/>
					<msh:textField property="countMedService8" label="Кол-во"/>
				</msh:row>
				<msh:row>
					<td colspan="6"><div id="divAllCount2"/></td>
				</msh:row>

			<msh:submitCancelButtonsRow colSpan="4" />
			</msh:panel>
		</msh:form>
		<msh:ifFormTypeIsView formName="contract_contractAccount20MedServiceForm">
		</msh:ifFormTypeIsView>
	</tiles:put>
	<tiles:put name="title" type="string">
		<ecom:titleTrail mainMenu="Contract" beginForm="contract_contractAccount20MedServiceForm" />
	</tiles:put>
	<tiles:put name="side" type="string">
		<msh:ifFormTypeAreViewOrEdit formName="contract_contractAccount20MedServiceForm">
			<msh:sideMenu>
				<msh:sideLink key="ALT+2" params="id" action="/entityParentEdit-contract_contractAccountMedService" name="Изменить" title="Изменить" roles="/Policy/Mis/Contract/MedContract/ServedPerson/ContractAccount/ContractAccountMedService/Edit"/>
				<msh:sideLink key="ALT+DEL" params="id" action="/entityParentDelete-contract_contractAccountMedservice" name="Удалить" title="Удалить" roles="/Policy/Mis/Contract/MedContract/ServedPerson/ContractAccount/ContractAccountMedService/Delete"/>
			</msh:sideMenu>
			<tags:contractMenu currentAction="medContract"/>
		</msh:ifFormTypeAreViewOrEdit>
	</tiles:put>
	<tiles:put name="javascript" type="string">
		<script type="text/javascript" src="./dwr/interface/ContractService.js"></script>
		<script type="text/javascript">
		theFields = new Array() ;
		function setUpdateCost() {
            for (var i=0;i<=8;i++) {
            	if (i==0){
            		j="" ;
            	} else {
            		j=i ;
            	}
            	eval("theFields[i] = medService"+j+"Autocomplete") ; 
            	eval("theFields[i].addOnChangeCallback(function() {updateCostByMedService('"+j+"') ;});");
            	eval("eventutil.addEventListener($('countMedService"+j+"'),'keyup',function(){checkSum() ;})");
            	eval("eventutil.addEventListener($('countMedService"+j+"'),'change',function(){checkSum() ;addMedService() ;})");
            }
		}	
		function updateCostByMedService(aPosition) {
			//alert(aPosition) ;
			var medService = $('medService'+aPosition).value ;
			if (+medService>0) {
				ContractService.getCostByPriceMedService(medService, {
	                callback: function(aResult) {
	                    //alert(aPosition) ;
	                    if (+aResult>0)  {
	                    	$('cost'+aPosition+'ReadOnly').value = aResult ;
	                    	$('cost'+aPosition).value = aResult ;
	                    } else {
	                    	$('cost'+aPosition+'ReadOnly').value = "0" ;
	                    	$('cost'+aPosition).value = "0" ;
	                    }
	                  }
	                 }
	                );
			} else {
            	$('cost'+aPosition+'ReadOnly').value = "0" ;
            	$('cost'+aPosition).value = "0" ;
			}
			checkSum() ;
			addMedService() ;
		
		}
		setUpdateCost() ;
		checkSum() ;
		function checkSum() {
				var costAll ;
				costAll= (+$('countMedService').value)*(+$('cost').value) ;
				for (var i=1; i<theFields.length;i++) {
					costAll = costAll + ((+$('countMedService'+i).value)*(+$('cost'+i).value)) ; 
				}
				$('divAllCount1').innerHTML = '<h1>Сумма: '+costAll+' руб.</h1>' 
				$('divAllCount2').innerHTML = '<h1>Сумма: '+costAll+' руб.</h1>' 
			}
			function addMedService() {
				var allMedService ="";
				
				for (var i=1; i<theFields.length;i++) {
					if ((+$('countMedService'+i).value>0)
							&& (+$('cost'+i).value>0)
							&& (+$('countMedService'+i).value>0)
						){
						allMedService = ""+allMedService +"#"
							+ $('medService'+i).value
							+":"+ $('cost'+i).value
							+":"+ $('countMedService'+i).value+":" 
						;
					}
				}
				$('additionMedService').value = allMedService.substring(1) ;
				//
			}
		</script>
	</tiles:put>
</tiles:insert>
