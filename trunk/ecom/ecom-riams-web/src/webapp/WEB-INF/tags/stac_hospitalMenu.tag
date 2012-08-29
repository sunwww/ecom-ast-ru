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
#side ul li a#deleteDischarge {
	color: red ;
	background-color: silver;
}

</style>

<msh:sideMenu>
    <msh:sideLink roles="/Policy/Mis/MedCase/Stac/Ssl/InfoShow" name="Информация" params="id" 
    	action='/entityParentView-stac_ssl' key='Alt+1'
    	styleId="stac_ssl"/>
    <msh:sideLink key="ALT+2" params="id" action="/entityParentEdit-stac_sslAdmission" 
    	name="Поступление &rarr;" title="Изменить информацию о поступлении" 
    	roles="/Policy/Mis/MedCase/Stac/Ssl/Admission/Show" 
    	styleId="stac_sslAdmission"/>

    <msh:sideLink roles="/Policy/Mis/MedCase/Stac/Ssl/MedPolicy/Show" name="Полисы" params="id"  
    	action='/stac_policiesEdit'  key='Alt+4' 
    	styleId="stac_policies"
    	title='Полисы данного случая лечения в стационаре!'/> 
    	<tags:stac_rw name="RW" key="ALT+3" title="Данные RW" number="rwNumber" date="rwDate" roles="/Policy/Mis/MedCase/Stac/Ssl/Admission/Show" />
   </msh:sideMenu>
  
   <msh:sideMenu title="Показать">
    <msh:sideLink roles="/Policy/Mis/MedCase/QualityEstimationCard/View" name="Экспертные карты" params="id" action="/entityParentList-expert_card"/>
 
 <%--    <mis:sideLinkForWoman classByObject="MedCase" id="${param.id}"  params=""
    	action="/javascript:gotoPregHistory('${param.id}','.do')" name="История родов" 
    	title="История родов" roles="/Policy/Mis/Pregnancy/History/View" styleId="preg_pregHistory" />
    	--%>
        <msh:sideLink action="/javascript:viewOtherVisitsByPatient('.do')" name='ВИЗИТЫ<img src="/skin/images/main/view1.png" alt="Просмотр записи" title="Просмотр записи" height="16" width="16">' title="Просмотр визитов по пациенту" key="ALT+4" guid="2156670f-b32c-4634-942b-2f8a4467567c" params="" roles="/Policy/Mis/MedCase/Visit/View" />
        <msh:sideLink action="/javascript:viewOtherDiagnosisByPatient('.do')" name='ДИАГНОЗЫ<img src="/skin/images/main/view1.png" alt="Просмотр записи" title="Просмотр записи" height="16" width="16">' title="Просмотр диагнозов по пациенту" key="ALT+5" guid="2156670f-b32c-4634-942b-2f8a4467567c" params="" roles="/Policy/Mis/MedCase/Diagnosis/View" />
    <msh:sideLink roles="/Policy/Mis/MedCase/Stac/Ssl/PhoneMessage/CriminalMessage/View" 
    	name="Милиция" params="id"  
    	action='/entityParentList-stac_criminalMessages' title='Сообщения в милицию'
    	styleId="stac_criminalMessages" />
    <msh:sideLink roles="/Policy/Mis/Vaccination/View" name="Вакцинация" params="id"  
    	action='/entityParentList-vac_vaccination' title='Вакцинация'
    	styleId="vac_vacination"
    	/>
    
    <msh:sideLink roles="/Policy/Mis/MedCase/Stac/Ssl/PhoneMessage/InfectiousMessages/View" name="Инфекция" 
    	params="id"  action='/entityParentList-stac_infectiousMessages' 
    	title='Регистрация инфекционных заболеваний'
    	styleId="stac_infectiousMessage"
    	/> 
	<msh:sideLink roles="" name="Листы назначений" params="id" 
		action='/entityParentList-pres_prescriptList' title='Показать листы назначений'
		styleId="pres_prescriptList"
		/>
    <msh:sideLink roles="/Policy/Mis/MedCase/Stac/Ssl/SurOper/ShowSls,/Policy/Mis/MedCase/Stac/Ssl/SurOper/View" name="Операции"  
    	params="id"  action='/entityParentList-stac_surOperation'  key='Alt+7' title='Операции'
    	styleId="stac_surOperation"
    	/>
    <msh:sideLink roles="/Policy/Mis/MedCase/MedService/View" name="Мед.услуги"  
    	params="id"  action='/entityParentList-smo_medService'  key='Alt+7' title='Мед.услуги'
    	styleId="smo_medService" 
    	/>
    <msh:sideLink roles="/Policy/Mis/MedCase/Transfusion/View" name="Переливание"     
    	params="id"  action='/entityParentList-trans_transfusion'  key='Alt+8' 
    	title='Переливание трансфузионных сред'/>
    <msh:sideLink roles="/Policy/Mis/Inspection/View" name="Осмотры"     
    	params="id"  action='/entityParentList-preg_inspection'  key='Alt+0' 
    	title='Медицинские осмотры'/>

	<msh:sideLink roles="/Policy/Mis/MedCase/Protocol/View"  name="Дневник специалиста"   
		params="id"  action='/entityParentList-smo_visitProtocol' title='Список дневников специалистов'
		styleId="smo_visitProtocol"
		/>
    <msh:sideLink roles="/Policy/Mis/MedCase/Stac/Ssl/Diagnosis/View" name="Диагнозы" 
       params="id" action="/entityParentList-stac_diagnosis" title="Показать все диагнозы ССЛ"
       styleId="stac_diagnosis"
        />
	<msh:sideLink roles="/Policy/Mis/MedCase/Stac/Ssl/TemperatureCurve" name="Температурных листов" 
	   params="id" action="/entityParentList-stac_temperatureCurve" title="Показать все температурные листы" 
	   styleId="stac_temperatureCurve" />

    <msh:sideLink roles="/Policy/Mis/MedCase/Stac/Ssl/Slo/View" name="Отделения" 
    	params="id"  action='/entityParentList-stac_slo'  key='Alt+6' title='Лечение в отделениях' 
    	styleId="stac_slo"
    	/>

    <msh:sideLink styleId="stac_protocol" params="id" action="/printProtocolsBySLS.do?stNoPrint=selected" name="Список нераспечатанных протоколов"/>
    
</msh:sideMenu>



<msh:sideMenu title="Печать">
    <msh:sideLink roles="/Policy/Mis/MedCase/Stac/Ssl/PrintStatCard3" 
    	name="Печать стат. карты в формате А3" params="id"  
    	action='/print-statcard.do?m=printStatCardInfo&s=HospitalPrintService' title='Печать истории болезни'
    	/>
    <msh:sideLink roles="/Policy/Mis/MedCase/Stac/Ssl/PrintStatCard" 
    	name="Печать стат. карты в формате А4" params="id"  
    	action='/print-statcard_four.do?m=printStatCardInfo&s=HospitalPrintService' title='Печать истории болезни формат А4'
    	/>
    <msh:sideLink roles="/Policy/Mis/MedCase/Stac/Ssl/PrintStatCard066" 
    	name="Печать адресных листков (прибытия и убытия)"  params="id"  
    	action='/print-listAddressHospital.do?m=printAddressSheetByHospital&s=HospitalPrintService' title='Печать адресных листков'
    	/>
    <msh:sideLink roles="/Policy/Mis/MedCase/Stac/Ssl/PrintStatCard066" 
    	name="Печать стат.карты выбывшего из стационара" params="id"  
    	action='/print-066.do?m=printStatCardInfo&s=HospitalPrintService' title='Печать стат.карты выбывшего из стационара'
    	/>
    <msh:sideLink roles="/Policy/Mis/MedCase/Stac/Ssl/PrintStatCard066All" 
    	name="Печать стат.карты выбывшего из стационара" params="id"  
    	action='/print-066_all.do?m=printStatCardInfo&s=HospitalPrintService' title='Печать стат.карты выбывшего из стационара'
    	/>
    <msh:sideLink roles="/Policy/Mis/MedCase/Stac/Ssl/PrintStatCard033" 
    	name="Печать истории болезни и стат.карты выбывшего из стационара" params="id"  
    	action='/print-003.do?m=printStatCardInfo&s=HospitalPrintService' title='Печать стат.карты выбывшего из стационара'
    	/>
    <msh:sideLink roles="/Policy/Mis/MedCase/Stac/Ssl/PrintDischarge" 
    	name="Печать выписки" params="id"  
    	action='/print-discharge_hospital.do?m=printBilling&s=HospitalPrintService' title='Печать выписки'
    	/>
     <mis:sideLinkForWoman roles="" classByObject="MedCase" id="${param.id}"
     	action="/print-preghistory.do?s=HospitalPrintService&amp;m=printPregHistoryByMC" 
     	params="id" name="Истории родов" title="Печать истории родов"/>
		
</msh:sideMenu>

<msh:sideMenu>
    	
    	
    	
    <msh:sideLink roles="/Policy/Mis/MedCase/Stac/Ssl/Discharge/Show" 
    	name="Выписка &larr;"   params="id"  action='/entityParentEdit-stac_sslDischarge'  
    	key='Alt+9' title='Выписка' styleId="stac_sslDischarge" />
<!--     <msh:sideLink roles="/Policy/Mis/MedCase/Stac/Ssl/ExpertCard/Show" name="Эксперт"   params="id"  action='/expertCardListView' title='Экспертные карты'/>   -->
<!--     <msh:sideLink roles="/Policy/Mis/MedCase/Stac/Ssl/Nurse/Show" name="Лица по уходу"   params="id"  action='/nurseListView' title='Лица по уходу'/>  -->
<%--     <msh:sideLink roles="/Policy/Mis/Disability/DisabilityCase/View"  
    	name="Нетрудоспособность"   params="id"  action='/entityParentList-dis_case' 
    	title='Листы нетрудоспособности' 
    	styleId="dis_case"
    	/> --%>
<!--    <msh:sideLink roles="/Policy/Stac/ExpOmcService/Show" name="Цена по ОМС"   params="slsId"  action='/viewCalcPriceResultSls' title='Результат определения цены'/>  -->
	<msh:sideLink roles="/Policy/Mis/MedCase/DeathCase/View"  name="Случай смерти"   
		params="id"  action='/entityParentList-stac_deathCase' title='Просмотр случая смерти'
		styleId="stac_deathCase"
		/>
		<msh:sideLink roles="/Policy/Mis/MedCase/BirthCase/View"  name="Случай рождения"   
		params="id"  action='/entityParentList-stac_birthCase' title='Просмотр случая рождения'
		styleId="stac_birthCase"
		/>
<msh:sideLink roles="/Policy/Mis/MedCase/Stac/Ssl/Delete;/Policy/Mis/MedCase/Stac/Ssl/DeleteAdmin" 
    	name="Удалить"   params="id"  action="/entityParentDeleteGoParentView-stac_ssl"  
    	key='ALT+DEL' title='Удалить' confirm="Удалить?" />
</msh:sideMenu>
<msh:sideMenu title="Администрирование"  >
	<msh:sideLink action=".javascript:deleteDischarge('${param.id}','.do')"
		name="Удалить данные выписки"
		title="Удалить данные выписки"
		confirm="Вы действительно желаете удалить данные выписки?"
		roles="/Policy/Mis/MedCase/Stac/Ssl/DischargeDelete"
		styleId="deleteDischarge"
	/>
</msh:sideMenu>
<msh:sideMenu title="Перейти">
	    <msh:sideLink 
		        roles="/Policy/Mis/MedCase/Stac/Journal/ByDepartmentAdmission" 
		        action="/stac_journalByDepartmentAdmission" name="Журнал по направленным в отделение" />
	    <msh:sideLink
		         roles="/Policy/Mis/MedCase/Stac/Journal/CurrentByUserDepartment"
		         action="/stac_journalCurrentByUserDepartment" name="Журнал по состоящим в отделение пациентам" />
 	    <msh:sideLink 
		        roles="/Policy/Mis/MedCase/Stac/Journal/ByCurator" 
		        action="/stac_journalByCurator" name="Журнал по лечащему врачу" />  
</msh:sideMenu>
 <msh:sideMenu title="Дополнительно">
        <msh:sideLink action="/stac_sslList.do?sslid=${param.id}" name="⇧Все госпитализации пациента" title="Все госпитализации пациента" />
        <msh:sideLink action="/mis_patients" name="Новая госпитализация" />
</msh:sideMenu>

<msh:sideMenu title = "Добавить">
		<msh:sideLink action = "/entityParentPrepareCreate-stac_disabilityCase" name = "Нетрудоспособность" params = "id" title = "Нетрудоспособность" roles = "/Policy/Mis/MedCase/Stac/Ssl//Disability/Disability/Create"  />
</msh:sideMenu>
  <script type='text/javascript' src='./dwr/interface/PregnancyService.js'></script>
  <msh:ifInRole roles="/Policy/Mis/MedCase/Stac/Ssl/DischargeDelete">
		
		<script type="text/javascript">
		  function viewOtherVisitsByPatient(d) {
			  //alert("js-smo_visit-infoShortByPatient.do?id="+$('patient').value) ;
			  
			  getDefinition("js-smo_visit-infoShortByPatient.do?id="+$('patient').value, null); 
		  }
		  function viewOtherDiagnosisByPatient(d) {
			  getDefinition("js-smo_diagnosis-infoShortByPatient.do?id="+$('patient').value, null);
		  }
  		function deleteDischarge(aId) {
  			HospitalMedCaseService.deleteDischarge(
     		'${param.id}', {
     			callback: function(aString) {
     				if ($('dateFinish')) $('dateFinish').value="" ;
     				if ($('dateFinishReadOnly')) $('dateFinishReadOnly').value="" ;
     				if ($('dischargeTime')) $('dischargeTime').value="" ;
     				if ($('dischargeTimeReadOnly')) $('dischargeTimeReadOnly').value="" ;
     				alert("Данные удалены") ;
     			}
     		}
     	);
  		}
  	</script>
  </msh:ifInRole>
  
<script type="text/javascript">
function gotoPregHistory(aMedCase,aUrl) {
 	PregnancyService.getPregHistoryByMedCase(
			     		'${param.id}' , {
			 callback: function(aId) {
			     if (aId!=null) {
			          window.location.href = "entityParentView-preg_pregHistory.do?id="+aId ;
			     } else {
			     
			    PregnancyService.able2createByPregnancyHistory ({
			    	callback: function(aBool) {
			    		if (aBool!=null && aBool==true) {
					     	if (confirm("История родов не заведена. Хотите завести её сейчас?")) {
								window.location.href = "entityParentPrepareCreate-preg_pregHistory.do?id=${param.id}"  ;
							} else {
								alert("Ну и не надо");
							}
						} else {
							alert("История родов не заведена.") ;
						}
				     }
				   }
				   );
				   
				                
			     }
			  }
			}) ;
  }	
function gotoNewBornHistory(aMedCase,aUrl) {
	PregnancyService.getNewBornHistoryByMedCase(
		'${param.id}' , {
			 callback: function(aId) {
			     if (aId!=null) {
			          window.location.href = "entityParentView-preg_newBornHistory.do?id="+aId ;
			     } else {
			     
			    PregnancyService.able2createByNewBornHistory ({
			    	callback: function(aBool) {
			    		if (aBool!=null && aBool==true) {
					     	if (confirm("История новорожденного не заведена. Хотите завести её сейчас?")) {
								window.location.href = "entityParentPrepareCreate-preg_newBornHistory.do?id=${param.id}"  ;
							} else {
								alert("Ok");
							}
						} else {
							alert("История новорожденного не заведена.") ;
						}
				     }
				   }
				   );
				   
				                
			     }
			  }
			}) ;
  }	
</script>