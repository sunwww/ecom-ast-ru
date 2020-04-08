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
    	roles="/Policy/Mis/MedCase/Stac/Ssl/Admission/Edit,/Policy/Mis/MedCase/Stac/Ssl/Admission/Show" 
    	styleId="stac_sslAdmission"/>

    <msh:sideLink roles="/Policy/Mis/MedCase/Stac/Ssl/MedPolicy/Show" name="Полисы" params="id"  
    	action='/stac_policiesEdit'  key='Alt+4' 
    	styleId="stac_policies"
    	title='Полисы данного случая лечения в стационаре!'/>

	<msh:sideLink
				  params="id"
				  action="/entityParentPrepareCreate-oncology_case_reestr.do"
				  name="Создать онкологический случай"
				  roles="/Policy/Mis/Oncology/Case/Create"/>

	<msh:sideLink key="ALT+6"
				  params="id"
				  action="/entityParentPrepareCreate-preg_shortConfCertificate.do"
				  name="Добавить родовый сертификат"
				  roles="/Policy/Mis/Pregnancy/ConfinementCertificate"/>
</msh:sideMenu>
  
   <msh:sideMenu title="Показать">
    <msh:sideLink roles="/Policy/Mis/MedCase/Covid19/View" name="Карты COVID-19"
				  action="/entityParentList-smo_covid19" params="id"/>
    <msh:sideLink roles="/Policy/Mis/MedCase/QualityEstimationCard/View" name="онкологическая карта выписки"
				  params="id" action="/entityPrepareCreate-mis_dynamicDocument.do?type=1"/>
    <msh:sideLink roles="/Policy/Mis/MedCase/QualityEstimationCard/View" name="Экспертные карты" params="id" action="/entityParentList-expert_card"/>

 <%--    <mis:sideLinkForWoman classByObject="MedCase" id="${param.id}"  params=""
    	action="/javascript:gotoPregHistory('${param.id}','.do')" name="История родов" 
    	title="История родов" roles="/Policy/Mis/Pregnancy/History/View" styleId="preg_pregHistory" />
    	--%>
        <msh:sideLink styleId="viewShort" action="/javascript:viewOtherVisitsByPatient('.do')" name='ВИЗИТЫ' title="Просмотр визитов по пациенту" key="ALT+4"  params="" roles="/Policy/Mis/MedCase/Visit/View" />
        <msh:sideLink styleId="viewShort" action="/javascript:viewOtherDiagnosisByPatient('.do')" name='ДИАГНОЗЫ' title="Просмотр диагнозов по пациенту" key="ALT+5"  params="" roles="/Policy/Mis/MedCase/Diagnosis/View,/Policy/Mis/MedCase/Ssl/Diagnosis/View" />
        <msh:sideLink styleId="viewShort" action="/javascript:viewOtherExtMedserviceByPatient('.do')" name='Внешние лаб. исследования' title="Просмотр внешних лабораторных данных по пациенту" key="ALT+5"  params="" roles="/Policy/Mis/MedCase/Document/External/Medservice/View" />
        <msh:sideLink styleId="viewShort" action="/javascript:getDefinition('js-stac_sslAllInfo-list_fond_direct_by_sls.do?short=Short&id=${param.id}','.do')" name='Направления из фонда' title="Просмотр прикрепленных направлений из фонда" 
        	roles="" />
        <msh:sideLink styleId="viewShort" action="/javascript:getDefinition('js-stac_ssl-cost_case.do?short=Short&id=${param.id}','.do')" name='Цена' title="Просмотр стоимости услуг" 
        	roles="/Policy/Mis/Contract/Journals/AnalisisMedServices" />
        <msh:sideLink styleId="viewShort" action="/javascript:getDefinition('js-contract_juridicalContract-account_view_by_patient.do?short=Short&id=${param.id}','.do')" name='Услуги по счету' title="Просмотр услуг по счету" 
        	roles="/Policy/Mis/Contract/Journals/AnalisisMedServices" />
    <msh:sideLink roles="/Policy/Mis/MedCase/Stac/Ssl/PhoneMessage/CriminalMessage/View" 
    	name="Полиция" params="id"  
    	action='/entityParentList-stac_criminalMessages' title='Сообщения в полицию'
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
	<msh:sideLink roles="/Policy/Mis/Prescription/Prescript/View" name="Лист назначений"
		action="/javascript:showCreatePrescriptList('${param.id}','.do')" title='Показать/добавить лист назначений'
		/>
	   <msh:sideLink roles="/Policy/Mis/Prescription/Prescript/View" name="Сводный лист назначений" params="" action="/javascript:getDefinition('entityParentList-pres_prescriptList.do?short=Short&id=${param.id}')" title="Показать все назначения СЛС"  />
	   <msh:sideLink roles="/Policy/Mis/MedCase/Stac/Ssl/SurOper/ShowSls,/Policy/Mis/MedCase/Stac/Ssl/SurOper/View" name="Операции"
    	 styleId="viewShort" action="/javascript:getDefinition('entityParentList-stac_surOperation.do?short=Short&id=${param.id}','.do')"  title='Операции'
    	
    	/>
    <msh:sideLink roles="/Policy/Mis/MedCase/MedService/View" name="Мед.услуги"  
    	styleId="viewShort" action="/javascript:getDefinition('entityParentList-smo_medService.do?short=Short&id=${param.id}','.do')"  title='Мед.услуги'
    	/>

    <msh:sideLink roles="/Policy/Mis/Inspection/View" name="Осмотры"     
    	params="id"  action='/entityParentList-preg_inspection'  key='Alt+0' 
    	title='Медицинские осмотры'/>


	<msh:sideLink roles="/Policy/Mis/MedCase/Protocol/View,/Policy/Mis/MedCase/Stac/Ssl/Protocol/View"  name="Дневник специалиста ПРИЕМНОГО ОТДЕЛЕНИЯ"   
		params="id"  action='/entityParentList-smo_visitProtocol' title='Список дневников специалистов'
		styleId="smo_visitProtocol"
		/>
    <msh:sideLink roles="/Policy/Mis/MedCase/Stac/Ssl/Diagnosis/View" name="Диагнозы" 
       params="id" action="/entityParentList-stac_diagnosis" title="Показать все диагнозы ССЛ"
       styleId="stac_diagnosis"
        />
	<msh:sideLink roles="/Policy/Mis/MedCase/Stac/Ssl/TemperatureCurve/View" name="Температурных листов" 
	   params="id" action="/entityParentList-stac_temperatureCurve" title="Показать все температурные листы" 
	   styleId="stac_temperatureCurve" />

    <msh:sideLink roles="/Policy/Mis/MedCase/Stac/Ssl/Slo/View" name="Отделения" 
    	params="id"  action='/entityParentList-stac_slo'  key='Alt+6' title='Лечение в отделениях' 
    	styleId="stac_slo"
    	/>

    <msh:sideLink styleId="stac_protocol" params="id" roles="/Policy/Mis/MedCase/Protocol/View,/Policy/Mis/MedCase/Stac/Ssl/Protocol/View"
    action="/printProtocolsBySLS.do?stNoPrint=selected" name="Список нераспечатанных протоколов"
    />
    
    <msh:sideLink roles="/Policy/Mis/MedCase/Stac/Ssl/Slo/View" name="263 приказ" 
    	 action='/javascript:showorder263CloseDocument()' title='263 приказ' styleId="stac_slo"
    	/>
	   <msh:sideLink roles="/Policy/Mis/MedCase/Stac/Ssl/Slo/View" name="Изменить рост/вес/ИМТ"
					 action='/javascript:showIMTCloseDocument()' title='Изменить рост/вес/ИМТ' styleId="stac_slo"
	   />
	   <msh:sideLink roles="/Policy/Mis/Journal/JasperReports" name="Справка о стоимости"
					 action='/javascript:showReferenceFSS()' title='Справка о стоимости' styleId="stac_slo"
	   />
	   <msh:sideLink roles="/Policy/Mis/ColorIdentityEdit/PatientSet" name="Браслеты"
					 styleId="viewShort" action="/javascript:showidentityPatient(${param.id},true)"  title='Браслеты'
	   />
	   <msh:sideLink roles="/Policy/Mis/MedCase/Stac/Ssl/Slo/OmcCriterion" name="Клас. критерии" params="id"
					 styleId="viewShort" action="/entityParentList-stac_omcCriterion"  title="Клас. критерии"
	   />
	   <msh:sideLink roles="/Policy/Mis/Pregnancy/BirthNosologyCard/View" name="Карта по нозологиям" params=""
					 styleId="viewShort" action="/javascript:showbirthNosologyCard(${param.id},null,0)"  title="Карта по нозологиям"
	   />
	   <msh:sideLink roles="/Policy/Mis/Pregnancy/BirthNosologyCard/View" name="Карта по нозологиям: отмеченные" params=""
					 styleId="viewShort" action="/javascript:showOnlyCheckedbirthNosologyCard(${param.id})"  title="Карта по нозологиям: отмеченные"
	   />
</msh:sideMenu>



<msh:sideMenu title="Печать">
    <msh:sideLink roles="/Policy/Mis/MedCase/Stac/Ssl/PrintStatCard3" 
    	name="Печать стат. карты в формате А3"  
    	action='/javascript:initSelectPrinter("print-statcard.do?m=printStatCardInfo&s=HospitalPrintService&id=${param.id}",1)' title='Печать истории болезни'
    	/>
    <msh:sideLink roles="/Policy/Mis/MedCase/Stac/Ssl/PrintStatCard" 
    	name="Печать стат. карты в формате А4" 
    	action='/javascript:initSelectPrinter("print-statcard_four.do?m=printStatCardInfo&s=HospitalPrintService&id=${param.id}",1)' title='Печать истории болезни формат А4'
    	/>
    <msh:sideLink roles="/Policy/Mis/MedCase/Stac/Ssl/AddressSheetByHospital" 
    	name="Печать адресных листков (прибытия и убытия)"    
    	action='/javascript:initSelectPrinter("print-listAddressHospital.do?m=printAddressSheetByHospital&s=HospitalPrintService&id=${param.id}",1)' title='Печать адресных листков'
    	/>
    <msh:sideLink roles="/Policy/Mis/MedCase/Stac/Ssl/PrintStatCard066" 
    	name="Печать стат.карты выбывшего из стационара"  
    	action='/javascript:initSelectPrinter("print-066.do?m=printStatCardInfo&s=HospitalPrintService&id=${param.id}",1)' title='Печать стат.карты выбывшего из стационара'
    	/>
    <msh:sideLink roles="/Policy/Mis/MedCase/Stac/Ssl/PrintStatCard066All" 
    	name="Печать стат.карты выбывшего из стационара"   
    	action='/javascript:initSelectPrinter("print-066_all.do?m=printStatCardInfo&s=HospitalPrintService&check=1&id=${param.id}",1)' title='Печать стат.карты выбывшего из стационара'
    	/>
    <msh:sideLink roles="/Policy/Mis/MedCase/Stac/Ssl/PrintStatCard033" 
    	name="Печать истории болезни и стат.карты выбывшего из стационара" 
    	action='/javascript:initSelectPrinter("print-003.do?m=printStatCardInfo&s=HospitalPrintService&id=${param.id}",1)' title='Печать стат.карты выбывшего из стационара'
    	/>
    <msh:sideLink roles="/Policy/Mis/MedCase/Stac/Ssl/PrintDischarge" 
    	name="Печать выписки"   
    	action='/javascript:initSelectPrinter("print-discharge_hospital.do?m=printBilling&s=HospitalPrintService&id=${param.id}",1)' title='Печать выписки'
    	/>
    <msh:sideLink roles="/Policy/Mis/MedCase/Stac/Ssl/PrintDischarge" 
    	name="Печать экс. карты"  
    	action='/javascript:initSelectPrinter("print-expert_card_empty.do?m=printBilling&s=HospitalPrintService&id=${param.id}",1)' title='Печать экс. карты'
    	/>
     <mis:sideLinkForWoman roles="/Policy/Mis/Pregnancy/History/View" classByObject="MedCase" id="${param.id}"
     	action='/javascript:initSelectPrinter("print-preghistory.do?s=HospitalPrintService&amp;m=printPregHistoryByMC&id=${param.id}",1)' 
     	name="Истории родов" title="Печать истории родов"/>
</msh:sideMenu>
<msh:sideMenu>
    <msh:sideLink roles="/Policy/Mis/MedCase/Stac/Ssl/Discharge/Show,/Policy/Mis/MedCase/Stac/Ssl/Discharge/Edit"
    	name="Выписка &larr;"   params="id"  action='/entityParentEdit-stac_sslDischarge'  
    	key='Alt+9' title='Выписка' styleId="stac_sslDischarge" />

        <msh:sideLink styleId="viewShort" action="/javascript:getDefinition('entityParentList-stac_deathCase.do?short=Short&id=${param.id}','.do')" name='Случай смерти' title="Просмотр случая смерти"
        	roles="/Policy/Mis/MedCase/DeathCase/View" />

		<msh:sideLink roles="/Policy/Mis/MedCase/Stac/Ssl/Planning/View"  name="Направления на госпитализацию"
		params="id"  action='/entityParentList-smo_planHospitalByHosp' title='Список направлений на госпитализацию'
		styleId="smo_planHospitalByHosp"
		/>
<msh:sideLink roles="/Policy/Mis/MedCase/Stac/Ssl/Delete;/Policy/Mis/MedCase/Stac/Ssl/DeleteAdmin"
    	name="Удалить"   params="id"  action="/entityParentDeleteGoParentView-stac_ssl"  
    	key='ALT+DEL' title='Удалить' confirm="Удалить?" />
</msh:sideMenu>
<msh:sideMenu title="Администрирование"  >
	<msh:sideLink action=".javascript:deleteDischargeCheck('${param.id}','.do')"
		name="Удалить данные выписки"
		title="Удалить данные выписки"
		confirm="Вы действительно желаете удалить данные выписки?"
		roles="/Policy/Mis/MedCase/Stac/Ssl/Discharge/Show,/Policy/Mis/MedCase/Stac/Ssl/Discharge/Edit"
		styleId="deleteDischarge"
	/>
	<msh:sideLink action="/entityPrepareCreate-sec_userPermission.do?type=2&ido=${param.id}"
		name="Добавить разрешение на редактирование случая"
		title="Добавить разрешение на редактирование случая" 
		roles="/Policy/Jaas/Permission/User/Create" 
	/>
    	<tags:stac_deniedHospitalizating name="DH" title="Оформить отказ больного" roles="/Policy/Mis/MedCase/Stac/Ssl/DeniedHospitalizatingSls" />
    	<tags:mis_changeServiceStream name="CSS" title="Изменить поток обслуживания" roles="/Policy/Mis/MedCase/Stac/Ssl/ChangeServiceStream" />
		<tags:pres_newPrescriptList name="Create" parentID="${param.id}" />
		<tags:stac_selectPrinter  name="Select" roles="/Policy/Config/SelectPrinter" />
		<tags:birthNosologyCard  name="birthNosologyCard"  />
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
        <msh:sideLink action="/mis_patients" name="Новая госпитализация" roles="/Mis/MainMenu/Patient,/Policy/Mis/MedCase/Stac/Ssl/Admission/Create"/>
        <msh:sideLink action="/javascript:makeTimurHappy()" name="Расчитать вероятность диабета" roles="/Policy/Mis/Journal/CheckDiabetes"/>
</msh:sideMenu>

<msh:sideMenu title = "Добавить">
		<msh:sideLink action = "/entityParentPrepareCreate-stac_disabilityCase" name = "Нетрудоспособность" params = "id" title = "Нетрудоспособность" roles = "/Policy/Mis/MedCase/Stac/Ssl//Disability/Disability/Create"  />
</msh:sideMenu>
<tags:contract_getAccount name="ACCOUNT"  />
<tags:order263 name="order263" />
<tags:IMT name="IMT" />
<tags:identityPatient name="identityPatient" title="в госпитализации"/>
  <script type='text/javascript' src='./dwr/interface/PregnancyService.js'></script>
  <script type="text/javascript">
  function viewOtherVisitsByPatient(d) {
	  //alert("js-smo_visit-infoShortByPatient.do?id="+$('patient').value) ;
	  
	  getDefinition("js-smo_visit-infoShortByPatient.do?id="+$('patient').value, null); 
  }
  function viewOtherDiagnosisByPatient(d) {
	  getDefinition("js-smo_diagnosis-infoShortByPatient.do?id="+$('patient').value, null);
  }
  function viewOtherExtMedserviceByPatient(d) {
	  getDefinition("js-doc_externalMedservice-list.do?short=Short&id=${param.id}", null);
  }
  </script>

    <script type="text/javascript" src="./dwr/interface/HospitalMedCaseService.js"></script>
<script type="text/javascript">

    function deleteDischargeCheck(aId) {
        HospitalMedCaseService.checkUserIsALastSloTreatDoctorAndDishargeLess(
            aId, {
                callback: function(res) {
                    if (res==true) {
                        deleteDischarge();
                    }
                    else {
                        HospitalMedCaseService.checkUserIsAdminToDeleteDischarge(
                            {
                                callback: function(res) {
                                    if (res==true) {
                                        deleteDischarge();
                                    }
                                    else alert("Невозможно удалить данные! Удалить её может только лечаший врач в течение 1го календарного дня после выписки или администратор системы.");
                                }}) ;
                    }
                }}) ;
    }
    function deleteDischarge() {
        HospitalMedCaseService.deleteDischarge(
            '${param.id}', {
                callback: function() {
                    if ($('dateFinish')) $('dateFinish').value="" ;
                    if ($('dateFinishReadOnly')) $('dateFinishReadOnly').value="" ;
                    if ($('dischargeTime')) $('dischargeTime').value="" ;
                    if ($('dischargeTimeReadOnly')) $('dischargeTimeReadOnly').value="" ;
                    alert("Данные удалены") ;
                }
            }
        );
    }

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
  function showReferenceFSS() {
      HospitalMedCaseService.getSettingsKeyValueByKey("jasperServerUrl", {
          callback: function (res) {
              var resMas = res.split("#");
              if (res != "") {
                  var stat=$('statCardNumber').value;
                  var year=$('dateFinish').value.substring(6,11);
                  window.open("http://" + resMas[0] + "/jasperserver/flow.html?_flowId=viewReportFlow&_flowId=viewReportFlow&ParentFolderUri=%2Freports&reportUnit=%2Freports%2FCostCertificate&standAlone=true&decorate=no"
                      + "&j_username=" + resMas[1] + "&j_password=" + resMas[2]+"&hn="+stat+"&fy="+year+ "&user=" + document.getElementById('current_username_li').innerHTML);
              }
              else
                  alert("Нет настройки адреса сервиса!");
          }
      });
  }
</script>