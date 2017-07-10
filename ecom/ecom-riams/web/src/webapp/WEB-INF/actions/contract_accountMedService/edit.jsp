<%@page import="ru.ecom.ejb.services.query.WebQueryResult"%>
<%@page import="java.util.List"%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://struts.apache.org/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://www.nuzmsh.ru/tags/msh" prefix="msh" %>
<%@ taglib uri="http://www.ecom-ast.ru/tags/ecom" prefix="ecom" %>
<%@ taglib tagdir="/WEB-INF/tags" prefix="tags" %>

<tiles:insert page="/WEB-INF/tiles/main${param.short}Layout.jsp" flush="true">
	<tiles:put name="javascript" type="string">
		
	</tiles:put>
	<tiles:put name="body" type="string">
		<msh:form action="/entityParentSaveGoParentView-contract_accountMedService.do" defaultField="typeName">
			<msh:hidden property="id" />
			<msh:hidden property="saveType" />
			<msh:hidden property="account" />
			<msh:panel>
				<msh:row>
					<msh:autoComplete property="medService" parentId="contract_accountMedServiceForm.account" label="Услуга" vocName="priceMedServiceByContractAccout" size="50" />
					<msh:textField property="cost" label="Цена" />
					<msh:textField property="countMedService" label="Кол-во"/>
				</msh:row>
				<msh:textField property="dateFrom" label="Дата с" />
				<msh:textField property="dateTo" label="Дата по" />
			<msh:submitCancelButtonsRow colSpan="4" />
			</msh:panel>
		</msh:form>
		<msh:ifFormTypeIsView formName="contract_accountMedServiceForm">
		</msh:ifFormTypeIsView>
	</tiles:put>
	<tiles:put name="title" type="string">
		<ecom:titleTrail mainMenu="Patient" beginForm="contract_accountMedServiceForm" />
	</tiles:put>
	<tiles:put name="side" type="string">
		<msh:ifFormTypeAreViewOrEdit formName="contract_accountMedServiceForm">
			<msh:sideMenu>
				<msh:sideLink key="ALT+2" params="id" action="/entityParentEdit-contract_accountMedService" name="Изменить" title="Изменить" roles="/Policy/Mis/Contract/MedContract/ServedPerson/ContractAccount/ContractAccountMedService/Edit"/>
				<msh:sideLink key="ALT+DEL" params="id" action="/entityParentDelete-contract_accountMedservice" name="Удалить" title="Удалить" roles="/Policy/Mis/Contract/MedContract/ServedPerson/ContractAccount/ContractAccountMedService/Delete"/>
			</msh:sideMenu>
			<tags:contractMenu currentAction="medContract"/>
		</msh:ifFormTypeAreViewOrEdit>
	</tiles:put>
	<tiles:put name="javascript" type="string">
	 <script type='text/javascript' src='./dwr/interface/ContractService.js'></script>
	<script type='text/javascript'>
	medServiceAutocomplete.addOnChangeCallback(function() {$('cost').value='';getCost();});
	function getCost() {
		ContractService.getCostByPriceMedService($('medService').value,{
			callback: function (a) {
				if (a!=null&&a!=''){
					$('cost').value=a;
				//	$('costReadOnly').value=a;
					
				}
			}
			});
		}
    eventutil.addEventListener($('dateFrom'),'change',function(){getAmountByPerid ();}) ;
    eventutil.addEventListener($('dateFrom'),'blur',function(){getAmountByPerid ();}) ;
    eventutil.addEventListener($('dateFrom'),'keyup',function(){getAmountByPerid ();}) ;
    eventutil.addEventListener($('dateTo'),'change',function(){getAmountByPerid ();}) ;
    eventutil.addEventListener($('dateTo'),'blur',function(){getAmountByPerid ();}) ;
    eventutil.addEventListener($('dateTo'),'keyup',function(){getAmountByPerid ();}) ;

	function getAmountByPerid () {

	    var startDate = $('dateFrom').value;
	    var finishDate = $('dateTo').value;

	    if (startDate!=null&& startDate!="" &&startDate.length==10 &&finishDate!=null&&finishDate.length==10) {
            var amount = "";
	        try {
                    var dt1 = startDate.substr(6,4)+'-'+startDate.substr(3,2)+'-'+startDate.substr(0,2);
                    var dt2= finishDate.substr(6,4)+'-'+finishDate.substr(3,2)+'-'+finishDate.substr(0,2);
                    var d1 = new Date(), d2 = new Date();
                    d1.setTime(Date.parse(dt1));
                    d2.setTime(Date.parse(dt2));
                    amount = (d2.getTime() - d1.getTime())/1000/60/60/24;
			} catch (e) {

			}
            $('countMedService').value=""+amount;
		}
	}
	</script>
	</tiles:put>
</tiles:insert>
