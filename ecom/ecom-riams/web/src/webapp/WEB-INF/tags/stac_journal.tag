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
	<msh:sideLink name="Список отчетов" 
	action="/javascript:getDefinition('riams_journals.do?short=Short',null)" styleId="viewShort" />
	<msh:sideLink name="Назначения на операцию" action="/direct_operation_list"
	roles="/Policy/Mis/Prescription/Prescript/View"
	 />
	<msh:sideLink name="Оборудование за отделением" action="/js-mis_medicalEquipment-listByDep"
	
	 />

	    <msh:sideMenu title="СЛС">
	    	<msh:sideLink roles="/Policy/Mis/MedCase/Stac/Ssl/View" 
	    	key="ALT+1" styleId="stac_findSlsByStatCard"
	    	action="/stac_findSlsByStatCard" name="Поиск по номеру стат.карты"
	    	/>
	        <msh:sideLink roles="/Policy/Mis/MedCase/Stac/Journal/HospitalByPeriod" key="ALT+2" 
	        styleId="stac_journalByHospital" 
	        action="/stac_journalByHospital" name="По стационару" />
	        	        
	        <msh:sideLink name="Планирование госпитализацией" 
	        styleId="stac_planning_hospitalizations"
	        action="/stac_planning_hospitalizations.do"
	        roles="/Policy/Mis/MedCase/Stac/Ssl/Planning/View" />
	        
	        <msh:sideLink roles="/Policy/Mis/MedCase/Stac/Journal/InformationBesk"  
	        styleId="stac_journalByInformationBesk" 
	        action="/stac_journalByInformationBesk" name="Стол справок" />
	        
	        <msh:sideLink roles="/Policy/Mis/MedCase/Stac/Journal/OpenningCaseByHospital" key="CTRL+1" 
	        styleId="stac_journalOpenByHospital" 
	        action="/stac_journalOpenByHospital" name="Открытым СЛС" />
	        
<%--	        <msh:sideLink roles="/Policy/Mis/MedCase/Stac/Journal/ProblemOMCpolicy" key="CTRL+1" 
	        styleId="stac_problemPolicy" 
	        action="/stac_problemPolicy_list" name="Проблемы с полисами ОМС" />
	         --%>
	        <msh:sideLink roles="/Policy/Mis/MedCase/Stac/Journal/HospitalByDeniedHospitalizating"
	        styleId="stac_journalHospitalByDeniedHospitalizating"
	        action="/stac_journalHospitalByDeniedHospitalizating" name="Отказы от госпитализации"
	        />
	        
	        <msh:sideLink roles="/Policy/Mis/MedCase/Stac/Journal/HospitalByRareCase"
	        styleId="stac_journalCaseRareByHospital"
	        action="/stac_journalCaseRareByHospital" name="Редкие случаи"
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
		         action="/stac_journalCurrentByUserDepartment" name="По состоящим в отделении пациентам" />
	        <msh:sideLink
		        styleId="stac_journalDichargeByUserDepartment" 
		         roles="/Policy/Mis/MedCase/Stac/Journal/DischargeByUserDepartment" 
		         action="/stac_journalDischargeByUserDepartment" name="По выписанным пациентам" />
	        <!--msh:sideLink
		        styleId="stac_departmentJournal" 
		        roles="/Policy/Mis/MedCase/Stac/Journal/ByDoctorOnDuty" key="ALT+5" 
		        action="/stac_departmentJournal" name="По дежурному врачу" /-->
 	        <msh:sideLink 
		        styleId="stac_journalByCurator" 
		        roles="/Policy/Mis/MedCase/Stac/Journal/ByCurator" key="ALT+6" 
		        action="/stac_journalByCurator" name="По лечащему врачу" />  
 	        <msh:sideLink 
		        styleId="stac_journalDraftByCurator" 
		        roles="/Policy/Mis/MedCase/Stac/Journal/ByCurator" 
		        action="/js-smo_draftProtocol-list.do" name="Черновики протоколов" />  

 	        <msh:sideLink 
		        styleId="stac_journalTrafficByPatient" 
		        roles="/Policy/Mis/MedCase/Stac/Journal/TrafficByPatient" key="ALT+7" 
		        action="/stac_journalByCurator" name="Движения больных и коечного фонда" />
			<msh:sideLink
					styleId="stac_journalCurrentByUserDepartmentMicroBio"
					roles="/Policy/Mis/MedCase/Stac/Journal/CurrentByUserDepartmentMicroBio"
					action="/stac_journalCurrentByUserDepartmentMicroBio" name="По микробиологическим исследованиям" />
			<msh:sideLink
					styleId="stac_directionHIVByUserDepartment"
					roles="/Policy/Mis/MedCase/Stac/Journal/HIVDirection" key="ALT+0"
					action="/stac_directionHIVByUserDepartment" name="Направление на ВИЧ" />
			<msh:sideLink
					styleId="stac_directionCovidByUserDepartment"
					roles="/Policy/Mis/MedCase/Covid19/View"
					action="/stac_directionCovidByUserDepartment" name="Направление на COVID" />
			<msh:sideLink
					styleId="stac_directionCovidByUserDepartment"
					roles="/Policy/Mis/MedCase/Covid19/View/EllaJournal"
					action="/stac_reportForElla" name="Отчет только для Эллы" />
			<msh:sideLink
					styleId="stac_directionCovidByUserDepartment"
					roles="/Policy/Mis/MedCase/Covid19/View"
					action="/stac_analysisCovid" name="По анализам на Covid19" />
	    </msh:sideMenu>
	    <msh:sideMenu title="Журналы">
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
	    	<msh:sideLink styleId="stac_journalByStandart"
	    		roles="/Policy/Mis/MedCase/Stac/Journal/StandartOmc" 
	    		action="/stac_report_standartOmc" name="По ВМП"
	    	/>
	    	<msh:sideLink styleId="stac_journalByBedFund"
	    		roles="/Policy/Mis/MedCase/Stac/Journal/ReestrByBedFund" 
	    		action="/stac_groupByBedFundList" name="По коечному фонду"
	    	/>
	    	<msh:sideLink styleId="stac_journalByMoveToAnotherLpu"
	    		roles="/Policy/Mis/MedCase/Stac/Journal/ReestrByMoveToAnotherLpu" 
	    		action="/stac_groupByMoveToAnotherLpuList" name="По выписанным в другие ЛПУ"
	    	/>
	    	<msh:sideLink styleId="journal_doc_externalMedService"
	    		roles="/Policy/Mis/MedCase/Document/External/Medservice/View,/Policy/Mis/MedCase/Stac/Journal/ExternalMedservice" 
	    		action="/journal_doc_externalMedService" name="Внеш. лаборатория"
	    	/>
			<msh:sideLink styleId="journal_doc_externalMedService"
						  roles="/Policy/Mis/MedCase/Stac/Ssl/View"
						  action="/patientWatch.do" name="Пациенты под наблюдением"
			/>
			<msh:sideLink styleId="journal_doc_externalMedService"
						  roles="/Policy/Mis/MedCase/Stac/Ssl/View"
						  action="/wfConsultationsAll.do" name="Консультации в стационаре"
			/>
			<msh:sideLink styleId="journal_doc_externalMedService"
						  roles="/Policy/Mis/MedCase/Stac/Ssl/View"
						  action="/pres_doctor_lab_journal.do" name="Журнал лабораторных назначений"
			/>
	    </msh:sideMenu>
</msh:sideMenu>
