
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
	<msh:sideLink styleId="conPerson" 
		roles="/Policy/Mis/Contract/ContractPerson/View" 
		action="/contract_find_person" name="Контрактные лица (юридические, физические)" 
		title="Список контрактных лиц (юридических, физических)"/>
	<msh:sideLink styleId="price" 
		action="/entityList-contract_priceList" name="прейскурантов" title="Список прейскурантов"
		roles="/Policy/Mis/Contract/PriceList/View"/>
	<msh:sideLink styleId="medContract" 
		action="/contract_find_by_number.do" name="мед.договоров" title="Поиск договора"
			roles="/Policy/Mis/Contract/MedContract/View"/>
	<msh:sideLink styleId="contract_account" 
		action="/contract_find_by_account_number.do" name="счетов" title="Поиск счетов"
			roles="/Policy/Mis/Contract/Journals/ContractAccount"/>
	<msh:sideLink styleId="medServiceGroup" 
		action="/entityList-contract_medServiceGroup" 
		name="групп медицинских услуг" roles="/Policy/Mis/Contract/GroupRules/ContractMedServiceGroup/View"
		title="Список групп медицинских услуг по договорам"/>
	<msh:sideLink styleId="guaranteeGroup" 
		action="/entityList-contract_contractGuaranteeGroup" 
		name="групп гарантийных документов" roles="/Policy/Mis/Contract/GroupRules/ContractGuaranteeGroup/View"
		title="Список групп гарантийных документов по договорам"/>
	<msh:sideLink styleId="nosologyGroup" 
		action="/entityList-contract_nosologyGroup" 
		name="нозологических групп" roles="/Policy/Mis/Contract/GroupRules/ContractNosologyGroup/View"
		title="Список нозологических групп по договорам"
		/>
	<msh:sideLink styleId="attorneyGroup" 
		action="/entityList-work_attorney" 
		name="доверенностей" roles="/Policy/Mis/Worker/Attorney/View"
		title="Список доверенностей"
		/>

		
</msh:sideMenu>
<msh:sideMenu title="Отчеты">
	<msh:sideLink styleId="controlReport" 
		action="/contract_reports_accrual" 
		name="Контрольный отчет" roles="/Policy/Mis/Contract/Journals/Control,/Policy/Mis/Contract/MedContract/View,/Policy/Mis/Contract/MedContract/ServedPerson/ContractAccount/ContractAccountOperation/View"
		title="Контрольный отчет"
		/>
	<msh:sideLink styleId="controlReportNoAccrual" 
		action="/contact_analisis_no_accrual" 
		name="Контрольный отчет по счетам на оплату" roles="/Policy/Mis/Contract/Journals/ControlNoAccrual,/Policy/Mis/Contract/MedContract/View,/Policy/Mis/Contract/MedContract/ServedPerson/ContractAccount/ContractAccountOperation/View"
		title="Контрольный отчет по счетам на оплату"
		/>
	<msh:sideLink styleId="serviciesReport" 
		action="/contract_reports_services" 
		name="Отчет по услугам" roles="/Policy/Mis/Contract/Journals/Servicies,/Policy/Mis/Contract/MedContract/View,/Policy/Mis/Contract/MedContract/ServedPerson/ContractAccount/ContractAccountOperation/View"
		title="Разбивка по услугам"
		/>
	<msh:sideLink styleId="serviciesReportGroup" 
		action="/contract_reports_services_group" 
		name="Отчет по услугам (группы)" roles="/Policy/Mis/Contract/Journals/Servicies,/Policy/Mis/Contract/MedContract/View,/Policy/Mis/Contract/MedContract/ServedPerson/ContractAccount/ContractAccountOperation/View"
		title="Разбивка по услугам (группы)"
		/>

	<msh:sideLink styleId="nationalityReportGroup"
		action="/contract_nationalityReport"
		name="Сведения об оказанной мед. помощи иногородним и иностранным гражданам" roles="/Policy/Mis/Contract/Journals/Servicies,/Policy/Mis/Contract/MedContract/View,/Policy/Mis/Contract/MedContract/ServedPerson/ContractAccount/ContractAccountOperation/View"
		title="Сведения об оказанной мед. помощи иногородним и иностранным гражданам"
		/>

	<msh:sideLink styleId="financeReport"
		action="/contract_reports_finance" 
		name="финансовый отчет" roles="/Policy/Mis/Contract/Journals/Finance,/Policy/Mis/Contract/MedContract/View,/Policy/Mis/Contract/MedContract/ServedPerson/ContractAccount/ContractAccountOperation/View"
		title="Финансовый отчет"
		/>
	<msh:sideLink styleId="serviciesPolicReport"
		action="/contract_policlinic_render" 
		name="Отчет по поликлинике" roles="/Policy/Mis/Contract/Journals/ContractPolicRender"
		title="Отчет по поликлинике"
		/>
	<msh:sideLink styleId="analisisMedServices"
		action="/contact_analisis_by_medServices.do" 
		name="Анализ услуг " roles="/Policy/Mis/Contract/Journals/AnalisisMedServices"
		title="Анализ услуг "
		/>
	<msh:sideLink styleId="analisisPriceServices"
		action="/contact_analisis_by_priceServices.do" 
		name="Анализ услуг прейскуранта" roles="/Policy/Mis/Contract/Journals/AnalisisPriceServices"
		title="Анализ услуг прейскуранта"
		/>
	<msh:sideLink styleId="quaranteeReport"
		action="/contract_list_quarantee_by_period.do" 
		name="Отчет по остаткам по гар. письмам" roles="/Policy/Mis/Contract/Journals/AnalisisPriceServices"
		title="Отчет по остаткам по гар. письмам"
		/>
</msh:sideMenu>
<msh:sideMenu title="Работа с ККМ">
<msh:sideLink styleId="printZReport"
		action="/javascript:printKKMReport('Z')"
		name="Распечатать Z отчет" roles="/Policy/Config/KKMWork"
		title="Распечатать Z отчет"
		/>
<msh:sideLink styleId="printXReport"
		action="/javascript:printKKMReport('X')" 
		name="Распечатать X отчет" roles="/Policy/Config/KKMWork"
		title="Распечатать X отчет"
		/>		

</msh:sideMenu>
<script type="text/javascript" src="./dwr/interface/ContractService.js"></script>
<script type="text/javascript">
function printKKMReport(type) {
	if (confirm("Распечатать "+type+" отчет?")) {
		ContractService.sendKKMRequest("print"+type+"Report", {
			callback: function (a) {
				alert (""+a);
			}
		});
	}
}
</script>