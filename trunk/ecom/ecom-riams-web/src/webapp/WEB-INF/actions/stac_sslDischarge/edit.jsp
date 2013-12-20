<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://struts.apache.org/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://www.nuzmsh.ru/tags/msh" prefix="msh" %>
<%@ taglib uri="http://www.ecom-ast.ru/tags/ecom" prefix="ecom" %>
<%@ taglib tagdir="/WEB-INF/tags" prefix="tags" %>
<%@ taglib uri="/WEB-INF/mis.tld" prefix="mis" %>

<tiles:insert page="/WEB-INF/tiles/main${param.short}Layout.jsp" flush="true">
	<tiles:put name="style" type="string">
	  	<msh:ifInRole roles="/Policy/Mis/MedCase/IsPsychiatry">
	                    <style type="text/css">
	                    #sourceHospTypeLabel,#admissionOrderLabel, #whereAdmissionLabel,
	                    #orderMkbLabel,#orderDiagnosLabel,
	                    #admissionInHospitalLabel, #psychReasonLabel, #clinicalActuityLabel
	                    ,#orderLpuLabel
	                     {
	                        color: blue ;
	                    }
	                    #sourceHospTypeName,#admissionOrderName, #whereAdmissionName,
	                    #orderMkbName,#orderDiagnos,
	                    #admissionInHospitalName, #psychReasonName, #clinicalActuityName {
	                        background-color:#FFFFA0;
	                    }
	                </style>
	          	</msh:ifInRole>
  	<msh:ifInRole roles="/Policy/Mis/MedCase/IsPsychiatry">
        <style type="text/css">
            #sourceHospTypeLabel,#admissionOrderLabel, #whereAdmissionLabel,
            #admissionInHospitalLabel, #psychReasonLabel, #clinicalActuityLabel
            ,#judgment35Label,#lawCourtDesicionDate
             {
                color: blue ;
            }
            #sourceHospTypeName,#admissionOrderName, #whereAdmissionName,
            #admissionInHospitalName, #psychReasonName, #clinicalActuityName {
                background-color:#FFFFA0;
            }
        </style>
  	</msh:ifInRole>
  	<style type="text/css">


            #clinicalDiagnosLabel, #clinicalMkbLabel, #clinicalActuityLabel {
                color: blue ;
            }
            #concomitantDiagnosLabel, #concomitantMkbLabel {
                color: green ;
            }

            #concludingDiagnosLabel, #concludingMkbLabel {
                color: black ;
            }
            #complicationDiagnosLabel, #complicationMkbLabel {
                color: purple;
            }
            
            .epicrisis {
				left:0px;  width:99%; 
				top:0px;  height:45em;
			}
			#epicriPanel {
			width:100%;
			}
			.dischargeEpicrisis {
			width:100%;
			}
        </style>

    </tiles:put>
      <tiles:put name="side" type="string">
    	  	<msh:ifNotInRole roles="/Policy/Mis/MedCase/Stac/Ssl/Discharge/NotViewDischargeEpicrisis">
    	  		<tags:template_new_diary name="newTemp" roles="/Policy/Diary/Template/Create" field="dischargeEpicrisis" title="Создание шаблона на основе выписки"/>
    	  	</msh:ifNotInRole>
    	  	<tags:stac_hospitalMenu currentAction="stac_sslDischarge"/>  
  </tiles:put>
  <tiles:put name="body" type="string">
    <!-- 
    	  - Случай стационарного лечения (выписка)
    	  -->
    <msh:form action="/entityParentSaveGoView-stac_sslDischarge.do" defaultField="" guid="d9a511ed-3808-4b26-9c6b-c0c4655f3bfb" title="Случай стационарного лечения. ВЫПИСКА">
      <msh:hidden property="id" guid="ca766a3b-4eb3-4c57-8997-68fe5cb52623" />
      <msh:hidden property="patient" guid="7ad1d4c1-b642-4f31-98d4-a22c6cccf6d8" />
      <msh:hidden property="saveType" guid="dab3ef4c-4014-43b7-be41-c2398a50b816" />
      <msh:hidden property="lpu" guid="14b0b5c0-045b-41b2-a2aa-6f799f1c2ea4" />
      <msh:hidden property="emergency"/>
      <msh:hidden property="ambulanceTreatment"/>
      <msh:hidden property="aidsExamination"/>
      <msh:hidden property="ownerFunction"/>
      <msh:hidden property="bedType"/>
      <msh:hidden property="department"/>
      <msh:hidden property="hospType"/>
      <msh:hidden property="serviceStream"/>
      <msh:hidden property="intoxication"/>
      <msh:hidden property="medicalAid"/>
      <msh:hidden property="relativeMessage"/>
      <msh:hidden property="orderLpu"/>
      <msh:hidden property="sourceHospType"/>
      <msh:hidden property="orderNumber"/>
      <msh:hidden property="orderDate"/>
      <msh:hidden property="orderType"/>
      <msh:hidden property="intoxication"/>
      <msh:hidden property="preAdmissionTime"/>
      <msh:hidden property="pediculosis"/>
      <msh:hidden property="attendant"/>
      <msh:hidden property="supplyOrderNumber"/>
      <msh:hidden property="deniedHospitalizating"/>
      <msh:hidden property="ambulanceTreatment"/>
      <msh:hidden property="username"/>
        <msh:hidden property="judgment35"/>
        <msh:hidden property="admissionOrder"/>
        <msh:hidden property="lawCourtDesicionDate"/>
        <msh:hidden property="psychReason"/>
       <msh:ifNotInRole roles="/Policy/Mis/Patient/Newborn">
       	<msh:hidden property="hotelServices"/>
       </msh:ifNotInRole>
              <msh:ifFormTypeIsView formName="stac_sslDischargeForm">
              	<msh:ifNotInRole roles="/Policy/Mis/MedCase/Stac/Ssl/Discharge/NotViewDischargeEpicrisis">
              		<msh:hidden property="dischargeEpicrisis"/>
              	</msh:ifNotInRole>
              </msh:ifFormTypeIsView>
      <msh:panel colsWidth="5%,10%,5%,80%">
        <msh:separator label="Приемное отделение" colSpan="8" guid="af11419b-1c80-4025-be30-b7e83df06024" />
        <msh:row guid="25f2a536-4fb6-4413-89db-a478145e097e">
          <msh:textField property="statCardNumber" label="Номер стат.карты" guid="e5f3d524-cca8-4a5a-a408-196ab6b79627" horizontalFill="true" viewOnlyField="true" />
        </msh:row>
        <msh:row guid="0e91a1ca-c366-435c-8f2c-274d23d87fd3">
          <msh:textField property="dateStart" label="Дата поступления" guid="e3fd4642-a532-4510-a528-c6e766328d61" viewOnlyField="true" />
          <msh:textField property="entranceTime" label="время" guid="f94ff57c-bbf9-44f8-9e8d-f21927edbcff" viewOnlyField="true" />
        </msh:row>
        <msh:row guid="b88b81ab-1b89-4747-ac27-a865e920eb33">
          <msh:checkBox property="relativeMessage" label="Сообщение родственникам" guid="21e6d68e-e0a2-4854-85e7-9344d25e3d46" viewOnlyField="true" />
          <msh:autoComplete property="department" label="Отделение" guid="bf59f5d5-2843-4abc-bf23-cbbbda89a67e" vocName="vocLpuOtd" horizontalFill="true" viewOnlyField="true" />
        </msh:row>
        <mis:ifPatientIsWoman classByObject="Patient" idObject="stac_sslDischargeForm.patient" roles="/Policy/Mis/Pregnancy/History/View">
        	<msh:separator label="Беременность" colSpan="9"/>
	        <msh:row>
	        	<msh:autoComplete viewOnlyField="true" viewAction="entityParentView-preg_pregnancy.do" property="pregnancy" label="Беременность" fieldColSpan="3" parentId="stac_sslAdmissionForm.patient" vocName="pregnancyByPatient" horizontalFill="true"/>
	        </msh:row>
        </mis:ifPatientIsWoman>
        </msh:panel>
        <msh:panel colsWidth="5%,10%,5%,80%">
	        <msh:row>
	        	<msh:autoComplete vocName="vocIllnesPrimary" property="clinicalActuity" horizontalFill="true" label="Характер заболевания"
	        		fieldColSpan="3"
	        	/>
	        </msh:row>
	        <msh:row>
		        <msh:autoComplete vocName="vocIdc10" label="МКБ клин.диаг." property="clinicalMkb" fieldColSpan="3" horizontalFill="true"/>
	        </msh:row>
	        <msh:row>
	    	    <msh:textField label="Клинический диагноз" property="clinicalDiagnos" fieldColSpan="3" horizontalFill="true"/>
	        </msh:row>
        </msh:panel>
        <msh:panel styleId="epicriPanel" colsWidth="1%,1%,1%,1%">
        <msh:ifInRole roles="/Policy/Mis/MedCase/Protocol/View" guid="580a3-19bf-4793-af89-f7a56837">
        <msh:ifNotInRole roles="/Policy/Mis/MedCase/Stac/Ssl/Discharge/NotViewDischargeEpicrisis">
	        <msh:row>
	        <msh:separator colSpan="8" label="Выписной эпикриз" />
	        </msh:row>
        	<msh:ifFormTypeIsNotView formName="stac_sslDischargeForm">
	        <msh:row>
              <td colspan="4" align="center">
	                        <input type="button" value="Шаблон" onclick="showTextTemplateProtocol()"/>
	                        <input type="button" value="Доп. сведения" onclick="showTextEpicrisis()"/>
	                        <input type="button" value="Сохранить пред. выписку" onclick="savePreRecord()"/>
	                        <input type="button" value="Сохранить пред. выписку+диагноз" onclick="this.form.action='entityParentSaveGoView-stac_sslDischargePre.do';this.form.submit();"/>
	                        <input type="button" id="changeSizeEpicrisisButton1" value="Увеличить" onclick="changeSizeEpicrisis()">
	                        
               </td>
	        </msh:row>
	        </msh:ifFormTypeIsNotView>
	        <msh:row>
	        <msh:textArea property="dischargeEpicrisis" fieldColSpan="3" label="Текст" />
	        </msh:row>
        	<msh:ifFormTypeIsNotView formName="stac_sslDischargeForm">
	        <msh:row>
              <td colspan="4" align="center">
	                        <input type="button" value="Шаблон" onclick="showTextTemplateProtocol()"/>
	                        <input type="button" value="Доп. сведения" onclick="showTextEpicrisis()"/>
	                        <input type="button" value="Сохранить пред. выписку" onclick="savePreRecord()"/>
	                        <input type="button" value="Сохранить пред. выписку+диагноз" onclick="this.form.action='entityParentSaveGoView-stac_sslDischargePre.do';this.form.submit();"/>
	                        <input type="button" id="changeSizeEpicrisisButton" value="Увеличить" onclick="changeSizeEpicrisis()">
               </td>
	        </msh:row>
	        </msh:ifFormTypeIsNotView>
        </msh:ifNotInRole>
        <msh:ifInRole roles="/Policy/Mis/MedCase/Stac/Ssl/Discharge/NotViewDischargeEpicrisis">
        	<msh:hidden property="dischargeEpicrisis"/>
        </msh:ifInRole>
        </msh:ifInRole>
        <msh:ifNotInRole roles="/Policy/Mis/MedCase/Protocol/View">
        	<msh:hidden property="dischargeEpicrisis"/>
        </msh:ifNotInRole>
        </msh:panel>
        <msh:panel colsWidth="5%,10%,5%,80%">
        <msh:separator colSpan="8" label="Выписка" guid="597ac93d-a5d0-4b08-a6b1-79efee0f497a" />
        <msh:row>
        	<msh:autoComplete vocName="vocIllnesPrimary" property="concludingActuity" horizontalFill="true" label="Характер заболевания"
        		fieldColSpan="3"
        	/>
        </msh:row>
        <msh:row>
	        <msh:autoComplete vocName="vocIdc10" label="МКБ-10 закл.диаг." property="concludingMkb" fieldColSpan="3" horizontalFill="true"/>
        </msh:row>
        <msh:row>
	        <msh:textField label="Заключительный диагноз" property="concludingDiagnos" fieldColSpan="3" horizontalFill="true"/>
        </msh:row>
        <msh:row>
	        <msh:autoComplete vocName="vocIdc10" label="МКБ-10 диаг.соп." property="concomitantMkb" fieldColSpan="3" horizontalFill="true"/>
        </msh:row>
        <msh:row>
    	    <msh:textArea label="Диаг. сопут" property="concomitantDiagnos" fieldColSpan="3" rows="3" horizontalFill="true"/>
        </msh:row>
        <msh:row>
	        <msh:autoComplete vocName="vocIdc10" label="МКБ-10 ослож." property="complicationMkb" fieldColSpan="3" horizontalFill="true"/>
        </msh:row>
        <msh:row>
    	    <msh:textField label="Осложнение" property="complicationDiagnos" fieldColSpan="3" horizontalFill="true"/>
        </msh:row>
        <msh:ifFormTypeIsView formName="stac_sslDischargeForm">
	        <msh:row>
	    	    <msh:autoComplete vocName="vocIdc10" label="МКБ-10 патанат.диаг." property="pathanatomicalMkb" fieldColSpan="3" horizontalFill="true"/>
	        </msh:row>
	        <msh:row>
	        	<msh:textField label="Патанатомический диагноз" property="pathanatomicalDiagnos" fieldColSpan="3" horizontalFill="true"/>
	        </msh:row>
        </msh:ifFormTypeIsView>
        <msh:row>
        	<msh:autoComplete property="kinsman" label="Представитель (иног.)" viewAction="entityParentView-mis_kinsman.do" 
        	parentId="stac_sslDischargeForm.patient" vocName="kinsmanBySMO" horizontalFill="true" fieldColSpan="3"/>
        </msh:row>
        <msh:ifInRole roles="/Policy/Mis/Patient/Newborn">
	        <msh:row>
	        	<msh:checkBox property="hotelServices" label="Находится в больнице по уходу за пациентом" fieldColSpan="3"/>
	        </msh:row>
        </msh:ifInRole>        
        <msh:row guid="03ac9346-99be-4d81-8696-9a1a8c339c38">
          <msh:autoComplete label="Исход" property="outcome" fieldColSpan="1" horizontalFill="true" guid="63d0b9-479f-8aef-0064a789fade" vocName="vocHospitalizationOutcome" />
          <msh:autoComplete label="Результат госп." property="result" fieldColSpan="1" horizontalFill="true" guid="63d091a8-90b9-479f-8aef-0064a789fade" vocName="vocHospitalizationResult" />
        </msh:row>
        <msh:row guid="03f46-99be-4d81-8696-9d39c38">
          <msh:autoComplete label="Дефекты догоспитального этапа" property="preAdmissionDefect" fieldColSpan="3" horizontalFill="true" vocName="vocPreAdmissionDefect" guid="6d1a8-90b9-479f-8aef-0s789fade"/>
        </msh:row>
        <msh:row guid="3009274e-f253-4805-baeb-0ab4ac5ffca8">
          <msh:textField label="Дата выписки" property="dateFinish" guid="430fa31a-5126-4628-8617-4ae67b4829a3" />
          <msh:textField label="Время выписки" property="dischargeTime" guid="1bee7682-f5a4-40f8-8e10-b6b6500ec0f4" />
        </msh:row>
        <msh:row guid="b2c54e43-3ae2-4716-af12-e32a7ac4a115">
          <msh:autoComplete label="Перевод в др ЛПУ" property="moveToAnotherLPU" guid="8c90d4e3-6351-405e-a5b0-0ea5cf61db87" horizontalFill="true" vocName="mainLpu" fieldColSpan="3" />
        </msh:row>  
        <msh:row guid="f2hba5-68fb-4ccc-9982-7b4h147">
          <msh:autoComplete vocName="vocHospType" property="targetHospType" label="Куда выписан" fieldColSpan="3" horizontalFill="true" guid="109g-23b2-42c0-ba47-65g0747816c" />
        </msh:row>
        <msh:row>
          <msh:checkBox label="Провизорность" property="provisional" guid="d8588d59-3adb-4485-af94-cadecb04f82b" />
          <msh:checkBox property="rareCase" label="Редкий случай" guid="6299a6be-428f-4a095" />
        </msh:row>
        <msh:row guid="16f1e99-4017-4385-87c1-bf5895e2">
          <msh:autoComplete labelColSpan="3" property="hospitalization" label="Госпитализация в данном году по данному заболевания" guid="ddc10e76-8ee913984f" vocName="vocHospitalization" horizontalFill="true" fieldColSpan="1" />
        </msh:row>
        <msh:ifInRole roles="/Policy/Mis/MedCase/IsPsychiatry">
	        <msh:row>
	        	<msh:autoComplete vocName="vocHospitalization" property="admissionInHospital" label="Поступление в стационар" horizontalFill="true" labelColSpan="1"/>
		        <msh:autoComplete label="Причина госпитализации" vocName="vocPsychHospitalReason" property="psychReason" labelColSpan="1" horizontalFill="true"/>
	        </msh:row>
	        <msh:row>
	        	<msh:autoComplete property="admissionOrder" label="Порядок поступления" fieldColSpan="1" vocName="vocAdmissionOrder" horizontalFill="true"/>
		        <msh:autoComplete label="Откуда поступил" vocName="vocHospitalizationWhereAdmission" property="whereAdmission" labelColSpan="1" horizontalFill="true"/>
	        </msh:row>
	        <msh:row>
	        	<msh:textField property="lawCourtDesicionDate" label="Дата решения суда"/>
	        	<msh:autoComplete property="judgment35" label="Решение судьи по ст. 35" horizontalFill="true" vocName="vocJudgment"/>
	        </msh:row>
        </msh:ifInRole>
        <msh:separator colSpan="8" label="RW" guid="597ac93d-a5d0-4b08-a6b1-79efee0f497a" />
        <msh:row guid="f6e5b8dd-89fd-4442-9779-4995ba7cc3d8">
          <msh:textField label="Дата RW" property="rwDate" guid="ffd282d7-95fe-40e5-a3a8-6d424c98dac0" />
          <msh:textField label="Номер RW" property="rwNumber" guid="2b3421f3-f4c8-40be-9820-8f887023fc1c" />
        </msh:row>
        <msh:row>
        	<msh:separator label="Дополнительно" colSpan="4"/>
        </msh:row>
                <msh:row>
        	<msh:label property="createDate" label="Дата создания"/>
          <msh:label property="username" label="Оператор" guid="2258d5ca-cde5-46e9-a1cc-3ffc278353fe" />
        </msh:row>
        <msh:row>
        	<msh:label property="editDate" label="Дата редак."/>
          	<msh:label property="editUsername" label="Оператор" guid="2258d5ca-cde5-46e9-a1cc-3ffc278353fe" />
        </msh:row>
        
        <msh:submitCancelButtonsRow guid="submitCancel" colSpan="4" labelSave="Сохранить изменения" labelCreating="Создание" labelCreate="Создать новый случай" labelSaving="Сохранение данных" />
      </msh:panel>
    </msh:form>
    <msh:ifNotInRole roles="/Policy/Mis/MedCase/Stac/Ssl/Discharge/NotViewDischargeEpicrisis">
    	<msh:ifFormTypeIsNotView formName="stac_sslDischargeForm">
    	<tags:templateProtocol property="dischargeEpicrisis" name="Text" version="Visit" idSmo="stac_sslDischargeForm.id" voc="protocolVisitByPatient" />
    	<tags:dischargeEpicrisis property="dischargeEpicrisis" name="Text" patient="patient" dateStart="dateStart" dateFinish="dateFinish"/>
    	</msh:ifFormTypeIsNotView>
    </msh:ifNotInRole>
    <tags:stac_infoBySls form="stac_sslDischargeForm"/>
  </tiles:put>
  <tiles:put name="title" type="string">
    <ecom:titleTrail mainMenu="Patient" beginForm="stac_sslDischargeForm" guid="ad9ca7d1-36d7-41ac-a186-cf6fca58b389" />
  </tiles:put>
  <tiles:put name="javascript" type="string">
        <msh:ifInRole roles="/Policy/Mis/MedCase/Stac/Ssl/Discharge/NotViewDischargeEpicrisis">
        	<script type="text/javascript">
        		$('outcomeName').select() ;
        		$('outcomeName').focus() ;
        	</script>
        </msh:ifInRole>
     <msh:ifFormTypeIsNotView formName="stac_sslDischargeForm">
        <msh:ifNotInRole roles="/Policy/Mis/MedCase/Stac/Ssl/Discharge/NotViewDischargeEpicrisis">
        	<script type="text/javascript">
        		$('dischargeEpicrisis').select() ;
        		$('dischargeEpicrisis').focus() ;
        		var isChangeSizeEpicrisis = 1 ;
        		function changeSizeEpicrisis() {
        			if (isChangeSizeEpicrisis==1) {
        				Element.addClassName($('dischargeEpicrisis'), "epicrisis") ;
        				if ($('changeSizeEpicrisisButton')) {
        					$('changeSizeEpicrisisButton').value='Уменьшить'
        					$('changeSizeEpicrisisButton1').value='Уменьшить'
        				} ;
        				isChangeSizeEpicrisis=0 ;
        			} else {
        				Element.removeClassName($('dischargeEpicrisis'), "epicrisis") ;
        				if ($('changeSizeEpicrisisButton')) {
        					$('changeSizeEpicrisisButton').value='Увеличить' ;
        					$('changeSizeEpicrisisButton1').value='Увеличить' ;
        				}
        				isChangeSizeEpicrisis=1;
        			}
        		}
        		eventutil.addEventListener($('dischargeEpicrisis'), "dblclick", 
        	  		  	function() {
        					changeSizeEpicrisis() ;
        	  		  	}) ;
        	</script>
        </msh:ifNotInRole>
        
     <script type="text/javascript" src="./dwr/interface/HospitalMedCaseService.js">/**/</script>
  	<script type="text/javascript">
  		try {
  		if (concludingMkbAutocomplete) concludingMkbAutocomplete.addOnChangeCallback(function() {
	      	 	setDiagnosisText('concludingMkb','concludingDiagnos');
	    });} catch(e) {
	    }
  		try {
	    if (clinicalMkbAutocomplete) clinicalMkbAutocomplete.addOnChangeCallback(function() {
	      	 	setDiagnosisText('clinicalMkb','clinicalDiagnos');
	    });} catch(e) {}
  		try {
	    if (pathanatomicalMkbAutocomplete) pathanatomicalMkbAutocomplete.addOnChangeCallback(function() {
	      	 	setDiagnosisText('pathanatomicalMkb','pathanatomicalDiagnos');
	    });} catch(e) {}
  		try {
	    if (concomitantMkbAutocomplete) concomitantMkbAutocomplete.addOnChangeCallback(function() {
	      	 	setDiagnosisText('concomitantMkb','concomitantDiagnos');
	    });} catch(e) {}
  		try {
	    if (complicationMkbAutocomplete) complicationMkbAutocomplete.addOnChangeCallback(function() {
	      	 	setDiagnosisText('complicationMkb','complicationDiagnos');
	    });} catch(e) {}
  		function setDiagnosisText(aFieldMkb,aFieldText) {
  			var val = $(aFieldMkb+'Name').value ;
  			var ind = val.indexOf(' ') ;
  			//alert(ind+' '+val)
  			if (ind!=-1) {
  				if ($(aFieldText).value=="") $(aFieldText).value=val.substring(ind+1) ;
  			}
  		}
  		function savePreRecord() {
  			HospitalMedCaseService.preRecordDischarge(
  					$('id').value,$('dischargeEpicrisis').value, {
	                    callback: function(aResult) {
	                        alert("Сохранено") ;
	                    }
  					}
  			) ;
  		}
  	</script>
  	</msh:ifFormTypeIsNotView>
  </tiles:put>
</tiles:insert>

