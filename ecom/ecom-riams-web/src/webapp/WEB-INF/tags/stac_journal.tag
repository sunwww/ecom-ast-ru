<%@ tag pageEncoding="utf8" %>
<%@ taglib uri="http://www.nuzmsh.ru/tags/msh" prefix="msh" %>
<%@ taglib tagdir="/WEB-INF/tags" prefix="tags" %>

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

<msh:sideMenu title="Журналы">
	    <msh:sideMenu title="СЛС">
	    	<msh:sideLink roles="/Policy/Mis/MedCase/Stac/Ssl/View" 
	    	key="ALT+1" styleId="stac_findSlsByStatCard"
	    	action="/stac_findSlsByStatCard" name="Поиск по номеру стат.карты"
	    	/>
	        <msh:sideLink roles="/Policy/Mis/MedCase/Stac/Journal/ByHospital" key="ALT+2" 
	        styleId="stac_journalByHospital" 
	        action="/stac_journalByHospital" name="По стационару" />
	        <msh:sideLink roles="/Policy/Mis/MedCase/Stac/Journal/InformationBesk"  
	        styleId="stac_journalByInformationBesk" 
	        action="/stac_journalByInformationBesk" name="Стол справок" />
	        
	        <msh:sideLink roles="/Policy/Mis/MedCase/Stac/Journal/OpenningCaseByHospital" key="CTRL+1" 
	        styleId="stac_journalOpenByHospital" 
	        action="/stac_journalOpenByHospital" name="Открытым СЛС" />
	        
	        <msh:sideLink roles="/Policy/Mis/MedCase/Stac/Journal/ProblemOMCpolicy" key="CTRL+1" 
	        styleId="stac_problemPolicy" 
	        action="/stac_problemPolicy_list" name="Проблемы с полисами ОМС" />
	        
	        <msh:sideLink roles="/Policy/Mis/MedCase/Stac/Journal/HospitalByDeniedHospitalizating"
	        styleId="stac_journalHospitalByDeniedHospitalizating"
	        action="/stac_journalHospitalByDeniedHospitalizating" name="Отказы от госпитализации"
	        />
	        
	        <msh:sideLink roles="/Policy/Mis/MedCase/Stac/Journal/HospitalByRareCase"
	        styleId="stac_journalCaseRareByHospital"
	        action="/stac_journalCaseRareByHospital" name="Редкие случаи"
	        />
	        
	        <msh:sideLink roles="/Policy/Mis/MedCase/Stac/Journal/HospitalByRepeatCase"
	        styleId="stac_journalRepeatCaseByHospital"
	        action="/stac_journalRepeatCaseByHospital_list" name="Повторные случаи госпитализации"
	        />
	        
	        <msh:sideLink roles="/Policy/Mis/MedCase/Stac/Journal/DeathCase"
	        styleId="stac_deathCase_list"
	        action="/stac_deathCase_list" name="Журнал по смертности"
	        />
	        <msh:sideLink roles="/Policy/Mis/MedCase/Stac/Journal/AddressList"
	        styleId="stac_address_list"
	        action="/stac_address_list" name="Адресный листок"
	        />
	    </msh:sideMenu>
	    <msh:sideMenu title="СЛО">
	        <msh:sideLink
		        styleId="stac_journalByUserDepartmentOnly" 
		         roles="/Policy/Mis/MedCase/Stac/Journal/ByUserDepartmentOnly" key="ALT+3" 
		         action="/stac_journalByUserDepartmentOnly" name="По отделению" />
	        <msh:sideLink 
		        styleId="stac_journalByDepartmentAdmission" 
		        roles="/Policy/Mis/MedCase/Stac/Journal/ByDepartmentAdmission" key="ALT+4" 
		        action="/stac_journalByDepartmentAdmission" name="По направленным в отделение" />
	        <msh:sideLink
		        styleId="stac_journalCurrentByUserDepartment" 
		         roles="/Policy/Mis/MedCase/Stac/Journal/CurrentByUserDepartment" key="ALT+5" 
		         action="/stac_journalCurrentByUserDepartment" name="По состоящим в отделение пациентам" />
<!--	        <msh:sideLink 
		        styleId="stac_departmentJournal" 
		        roles="/Policy/Mis/MedCase/Stac/Journal/ByDoctorOnDuty" key="ALT+5" 
		        action="/stac_departmentJournal" name="По дежурному врачу" /> -->
 	        <msh:sideLink 
		        styleId="stac_journalByCurator" 
		        roles="/Policy/Mis/MedCase/Stac/Journal/ByCurator" key="ALT+6" 
		        action="/stac_journalByCurator" name="По лечащему врачу" />  

 	        <msh:sideLink 
		        styleId="stac_journalTrafficByPatient" 
		        roles="/Policy/Mis/MedCase/Stac/Journal/TrafficByPatient" key="ALT+7" 
		        action="/stac_journalByCurator" name="Движения больных и коечного фонда" />  
	    </msh:sideMenu>
	    <msh:sideMenu title="Реестры">
	    	<msh:sideLink
	    		roles="/Policy/Mis/MedCase/Stac/Journal/ByHospital" key="ALT+9"
	    		action="/stac_reestrByHospital" name="По поступившим/ выбывшим из стационара за день"
	    		styleId="stac_reestrByHospital"
	    	/>
	    	<msh:sideLink
	    		roles="/Policy/Mis/MedCase/Stac/Journal/ReceivedWithoutPolicy" key="ALT+9"
	    		action="/stac_receivedWithoutPolicy_list" name="По госпитализациям без прикрепленных полисов"
	    		styleId="stac_receivedWithoutPolicy"
	    	/>
	    	<msh:sideLink styleId="stac_reestrByDepartment"
	    		roles="/Policy/Mis/MedCase/Stac/Journal/ReestrByDepartment" key="ALT+8"
	    		action="/stac_reestrByDepartment" name="По отделению"
	    	/>
	    	<msh:sideLink styleId="stac_journalByBedFund"
	    		roles="/Policy/Mis/MedCase/Stac/Journal/ReestrByBedFund" 
	    		action="/stac_groupByBedFundList" name="По коечному фонду"
	    	/>
	    	<msh:sideLink styleId="stac_journalByMoveToAnotherLpu"
	    		roles="/Policy/Mis/MedCase/Stac/Journal/ReestrByMoveToAnotherLpu" 
	    		action="/stac_groupByMoveToAnotherLpuList" name="По выписанным в другие ЛПУ"
	    	/>
	    </msh:sideMenu>
    </msh:sideMenu>
