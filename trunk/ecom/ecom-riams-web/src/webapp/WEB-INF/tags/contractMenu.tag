
<%@ tag pageEncoding="utf8" %>
<%@ taglib uri="http://www.nuzmsh.ru/tags/msh" prefix="msh" %>
<%@ taglib tagdir="/WEB-INF/tags" prefix="tags" %>
<%@ taglib uri="/WEB-INF/mis.tld" prefix="mis" %>

<%@  attribute name="currentAction" required="true" description="Текущее меню" %>
<style type="text/css">
a#${currentAction}, #side ul li a#${currentAction}, #side ul li a#${currentAction}  {
    color: black ;
    background-color: rgb(195,217,255) ;
    cursor: text ;
    border: none ;
    text-decoration: none ;
    background-image: url("/skin/images/main/sideSelected.gif");
    background-repeat: no-repeat;
    background-position: center left; ;
	font-weight: bold ;
    -moz-border-radius-topleft: 4px ;
    -moz-border-radius-bottomleft: 4px ;
}

</style>
<msh:sideMenu></msh:sideMenu>
<msh:sideMenu title="Списки">
	<msh:sideLink styleId="naturalPerson" 
		roles="/Policy/Mis/Contract/ContractPerson/NaturalPerson/View" 
		action="/entityList-contract_naturalPerson" 
		name="Физических лиц" title="Список физических лиц"/>
	<msh:sideLink styleId="juridicalPerson" 
		roles="/Policy/Mis/Contract/ContractPerson/JuridicalPerson/View" 
		action="/entityList-contract_juridicalPerson" name="Юридических лиц" 
		title="Список юридических лиц"/>
	<msh:sideLink styleId="price" 
		action="/entityList-contract_priceList" name="прейскурантов" title="Список прейскурантов"
		roles="/Policy/Mis/Contract/PriceList/View"/>
	<msh:sideLink styleId="medContract" 
		action="/entityList-contract_medContract" name="мед.договоров" title="Список мед. договоров"
			roles="/Policy/Mis/Contract/MedContract/View"/>
	<msh:sideLink styleId="medServiceGroup" 
		action="/entityList-contract_contractMedServiceGroup" 
		name="групп медицинских услуг" roles="/Policy/Mis/Contract/GroupRules/ContractMedServiceGroup/View"
		title="Список групп медицинских услуг по договорам"/>
	<msh:sideLink styleId="guaranteeGroup" 
		action="/entityList-contract_contractGuaranteeGroup" 
		name="групп гарантийных документов" roles="/Policy/Mis/Contract/GroupRules/ContractGuaranteeGroup/View"
		title="Список групп гарантийных документов по договорам"/>
	<msh:sideLink styleId="nosologyGroup" 
		action="/entityList-contract_contractNosologyGroup" 
		name="нозологических групп" roles="/Policy/Mis/Contract/GroupRules/ContractNosologyGroup/View"
		title="Список нозологических групп по договорам"
		/>
		
</msh:sideMenu>
<msh:sideMenu title="Отчеты">
	<msh:sideLink styleId="controlReport" 
		action="/contract_reports_accrual" 
		name="Контрольный отчет" roles="/Policy/Mis/Contract/Journals/Control,/Policy/Mis/Contract/MedContract/View,/Policy/Mis/Contract/MedContract/ServedPerson/ContractAccount/ContractAccountOperation/View"
		title="Контрольный отчет"
		/>
	<msh:sideLink styleId="serviciesReport" 
		action="/contract_reports_services" 
		name="Отчет по услугам" roles="/Policy/Mis/Contract/Journals/Servicies,/Policy/Mis/Contract/MedContract/View,/Policy/Mis/Contract/MedContract/ServedPerson/ContractAccount/ContractAccountOperation/View"
		title="Разбивка по услугам"
		/>
	<msh:sideLink styleId="financeReport"
		action="/contract_reports_finance" 
		name="финансовый отчет" roles="/Policy/Mis/Contract/Journals/Finance,/Policy/Mis/Contract/MedContract/View,/Policy/Mis/Contract/MedContract/ServedPerson/ContractAccount/ContractAccountOperation/View"
		title="Финансовый отчет"
		/>
</msh:sideMenu>