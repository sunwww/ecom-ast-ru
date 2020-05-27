<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://struts.apache.org/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://www.nuzmsh.ru/tags/msh" prefix="msh" %>
<%@ taglib uri="http://www.ecom-ast.ru/tags/ecom" prefix="ecom" %>
<%@ taglib tagdir="/WEB-INF/tags" prefix="tags" %>
<%@ taglib uri="/WEB-INF/mis.tld" prefix="mis" %>

<tiles:insert page="/WEB-INF/tiles/main${param.short}Layout.jsp" flush="true">

  <tiles:put name="title" type="string">
    <ecom:titleTrail mainMenu="Patient" beginForm="stac_sslAdmissionForm"  />
  </tiles:put>
  <tiles:put name="side" type="string">
    <msh:ifFormTypeAreViewOrEdit formName="stac_sslAdmissionForm" >
      <tags:stac_hospitalMenu currentAction="stac_sslAdmission" />
    </msh:ifFormTypeAreViewOrEdit>
  </tiles:put>
  <tiles:put name="body" type="string">
  
      	  <msh:ifFormTypeIsView formName="stac_sslAdmissionForm">
    	  <ecom:webQuery name="isTransferLpu" nativeSql="select id,lpu_id from medcase where id=${param.id} and moveToAnotherLpu_id is not null"/>
    	  <msh:tableNotEmpty name="isTransferLpu">
    	  		<ecom:webQuery name="directOtherLpuQ" nativeSql="select 
    	  		wchb.id as i1d
    	  		, to_char(wchb.createDate,'yyyy-MM-dd') as w2chbcreatedate
 , lpu.codef as l3puSent
 , olpu.codef as l4puDirect
 ,mkb.code as m5kbcode
 ,vbt.name as v6btcodef
 ,wchb.dateFrom as w7chbdatefrom
, vbst.name as v8bstcode
 from WorkCalendarHospitalBed wchb
 left join VocBedType vbt on vbt.id=wchb.bedType_id
 left join VocBedSubType vbst on vbst.id=wchb.bedSubType_id
 left join VocIdc10 mkb on mkb.id=wchb.idc10_id
 left join MisLpu lpu on lpu.id=wchb.department_id
 left join mislpu olpu on olpu.id=wchb.orderLpu_id
 where wchb.visit_id =${param.id}
    	"/>
    	<msh:tableEmpty name="directOtherLpuQ">
    	<span style="background-color: red; font-size: 200%">НЕОБХОДИМО ЗАПОЛНИТЬ ФОРМУ НАПРАВЛЕНИЯ В ДРУГОЕ ЛПУ !!!
    	<msh:link action="entityParentPrepareCreate-smo_planHospitalByHosp.do?id=${param.id}">Создать</msh:link>
    	</span>
    	</msh:tableEmpty>
    	<msh:tableNotEmpty name="directOtherLpuQ">
    	<msh:table  action="entityView-smo_planHospitalByHosp.do" name="directOtherLpuQ" idField="1">
    		<msh:tableColumn property="4" columnName="Направлен в ЛПУ"/>
    		<msh:tableColumn property="6" columnName="Профиль"/>
    		<msh:tableColumn property="5" columnName="Диагноз"/>
    	</msh:table>
    	</msh:tableNotEmpty>
    	  </msh:tableNotEmpty>
  </msh:ifFormTypeIsView>
    <!-- 
    	  - Случай стационарного лечения (поступление)
    	  -->
    <msh:form action="/entityParentSaveGoView-stac_sslAdmission.do" defaultField="" title="Случай стационарного лечения. Поступление" editRoles="/Policy/Mis/MedCase/Stac/Ssl/Admission/Edit" createRoles="/Policy/Mis/MedCase/Stac/Ssl/Admission/Create" >
      <msh:hidden property="id"  />
      <msh:hidden property="patient"  />
      <msh:hidden property="outcome"  />
      <msh:hidden property="saveType"  />
      <msh:hidden property="dateFinish"  />
      <msh:hidden property="dischargeTime"  />
      <msh:hidden property="result"  />
      <msh:hidden property="moveToAnotherLPU"  />
      <msh:hidden property="dischargeEpicrisis"  />
      <msh:hidden property="reasonDischarge"  />
      <msh:hidden property="rareCase"/>
      <msh:hidden property="preHosp"/>
       <msh:ifNotInRole roles="/Policy/Mis/Patient/Newborn">
       	<msh:hidden property="hotelServices"/>
       </msh:ifNotInRole>
      <msh:ifNotInRole roles="/Policy/Mis/MedCase/IsPsychiatry">
        <msh:hidden property="admissionOrder"/>
        <msh:hidden property="lawCourtDesicionDate"/>
        <msh:hidden property="psychReason"/>      
      </msh:ifNotInRole>
      <msh:ifNotInRole roles="/Policy/Mis/Contract/MedContract/ContractGuarantee/ContractGuaranteeLetter/View">
      <msh:hidden property="guarantee"/>
      </msh:ifNotInRole>
        <msh:hidden property="isIdentified"/>
     

      <msh:panel >
        <msh:separator label="Приемное отделение" colSpan="9"  />
        
       	<msh:row >
      		<td colspan="6"><div style="display: none;" id='medPolicyInformation' class="errorMessage"/></td>
      	</msh:row>
      	
      	       <msh:row >
          <msh:textField property="statCardNumber" label="Номер стат.карты" labelColSpan="1"  />
          <msh:ifFormTypeIsView formName="stac_sslAdmissionForm" >
            <msh:ifInRole roles="/Policy/Mis/MedCase/Stac/Ssl/Admission/ChangeStatCardNumber" >
              <td colspan="2">
                <a id="changeStatCardNumber" href="javascript:changeStatCardNumber()">Изменить номер стат. карты</a>
              </td>
            </msh:ifInRole>
          </msh:ifFormTypeIsView>
        </msh:row>
        <msh:row >
          <msh:textField property="dateStart" label="Дата поступления"  />
          <msh:textField property="entranceTime" label="время" fieldColSpan="3"  />
        </msh:row>
        <msh:row >
          <msh:textField property="transferDate" label="Выбыт. из приемника"  viewOnlyField="true"/>
          <msh:textField property="transferTime" label="время" fieldColSpan="3"  viewOnlyField="true"/>
        </msh:row>
        <msh:row >
          <msh:autoComplete property="lpu" label="Лечебное учреждение" vocName="lpu" fieldColSpan="3" horizontalFill="true"  />
        </msh:row>
        <msh:row>
        	<msh:autoComplete property="kinsman" label="Представитель" 
        	parentId="stac_sslAdmissionForm.patient" vocName="kinsmanBySMO" horizontalFill="true" fieldColSpan="3"/>
        </msh:row>
        <msh:ifInRole roles="/Policy/Mis/Patient/Newborn">
	        <msh:row>
	        	<msh:checkBox property="hotelServices" label="Находится в больнице по уходу за пациентом" fieldColSpan="3"/>
	        </msh:row>
        </msh:ifInRole>
        <msh:ifNotInRole roles="/Policy/Mis/Patient/Newborn">
        	<msh:hidden property="hotelServices"/>
        </msh:ifNotInRole>
        <msh:row >
          <msh:autoComplete vocName="vocHospType" property="hospType" label="Тип тек. стационара" fieldColSpan="1" horizontalFill="true"  />
          <msh:autoComplete vocName="vocServiceStream" property="serviceStream" label="Поток обслуживания" fieldColSpan="1" horizontalFill="true"  />
        </msh:row>
         <msh:ifInRole roles="/Policy/Mis/Contract/MedContract/ContractGuarantee/ContractGuaranteeLetter/View">
      <msh:row>
          <msh:autoComplete  vocName="guaranteeByPatient" parentId="stac_sslAdmissionForm.patient" property="guarantee" label="Гарантийное письмо"  fieldColSpan="3" horizontalFill="true" />
        </msh:row>
      </msh:ifInRole>
        <msh:row >
          <msh:autoComplete property="pediculosis" vocName="vocPediculosis" label="Педикулез" horizontalFill="true"  />
          <msh:autoComplete property="deniedHospitalizating" label="Причина отказа от госп." vocName="vocDeniedHospitalizating" horizontalFill="true" fieldColSpan="1"  />
        </msh:row>
        <msh:ifFormTypeIsNotView formName="stac_sslAdmissionForm" >
          <msh:row >
            <td colspan="9">
              <div id="formInfoMessage2" onclick="this.style.display = &quot;none&quot;">
                <table style="margin-left: 4em">
                  <tr>
                    <td>
                      <div class="formInfoMessage">* При отказе от госпитализации не забудьте направить на амбулаторно-поликлиническое лечение!!!</div>
                    </td>
                  </tr>
                </table>
              </div>
            </td>
          </msh:row>
        </msh:ifFormTypeIsNotView>
        <msh:row >
          <msh:checkBox property="ambulanceTreatment" label="Амбул. лечение"  />
          <msh:checkBox hideLabel="false" property="emergency" viewOnlyField="false"  horizontalFill="false" />
        </msh:row>
        <msh:row >
          <msh:checkBox property="relativeMessage" label="Сообщение родственникам"  />
          <msh:checkBox property="medicalAid" label="Оказана помощь в прием. отделении"  />
        </msh:row>
        <msh:row >
          <msh:textField property="attendant" label="Сопровождающее лицо"  fieldColSpan="3" horizontalFill="true" />
        </msh:row>
          <msh:row >
              <msh:textField property="height" label="Рост (см)"   fieldColSpan="1" horizontalFill="false" />
              <msh:textField property="weight" label="Вес (кг)"  fieldColSpan="1" horizontalFill="false" />
              <msh:textField  property="theIMT" label="ИМТ"  viewOnlyField="true" fieldColSpan="1" horizontalFill="false" />
          </msh:row>
        <msh:ifFormTypeIsNotView formName="stac_sslAdmissionForm">
        <msh:separator label="Направлен <input type='button' value='Список направлений' onclick='viewTable263narp_byPat()'///>" colSpan="6" />
        </msh:ifFormTypeIsNotView>
        <msh:ifFormTypeIsView formName="stac_sslAdmissionForm">
        <msh:separator label="Направлен" colSpan="6" />
        </msh:ifFormTypeIsView>
        
        <msh:row >
          <msh:autoComplete property="orderLpu" label="Кем направлен" vocName="mainLpu"  horizontalFill="true" fieldColSpan="3" />
        </msh:row>
        <msh:row >
          <msh:autoComplete vocName="vocHospTypeInAdmission" property="sourceHospType" label="Тип направившего ЛПУ" fieldColSpan="3" horizontalFill="true"  />
        </msh:row>
        <msh:row >
          <msh:textField property="orderNumber" label="№ напр"  />
          <msh:textField property="orderDate" label="Дата"  />
        </msh:row>
        <msh:row >
          <msh:autoComplete property="orderMkb" label="Код МКБ направителя"  vocName="vocIdc10" horizontalFill="true" fieldColSpan="3" />
        </msh:row>
        <msh:row >
          <msh:textField property="orderDiagnos" label="ДИАГНОЗ напр. учреждения"  fieldColSpan="3" horizontalFill="true" />
        </msh:row>
        <msh:separator label="Доставлен (только для экстренных больных)" colSpan="9"  />
        <msh:row >
          <msh:autoComplete showId="false" vocName="vocOmcFrm" hideLabel="false" property="orderType" viewOnlyField="false"  horizontalFill="true" />
          <msh:textField property="supplyOrderNumber" label="Номер наряда"  />
        </msh:row>
        <msh:row >
          <msh:autoComplete property="intoxication" label="Доставлен в сост. опьян.:"  vocName="vocIntoxication" fieldColSpan="3" horizontalFill="true" />
        </msh:row>
        <msh:row >
          <msh:autoComplete property="preAdmissionDefect" label="Дефекты догосп. этапа" vocName="vocPreAdmissionDefect"  fieldColSpan="3" horizontalFill="true" />
        </msh:row>
        <msh:row >
          <msh:autoComplete property="preAdmissionTime" label="Часы заболевания"  vocName="vocPreAdmissionTime" horizontalFill="true" />
        </msh:row>
        <msh:separator label="*Госпитализация" colSpan="9"  />
        <msh:row >
          <msh:autoComplete property="department" label="Отделение" vocName="vocLpuHospOtd" horizontalFill="true" parentAutocomplete="lpu" fieldColSpan="3" />
        </msh:row>
        <msh:row >
          <msh:autoComplete property="ownerFunction" label="Деж. врач отд." vocName="workFunctionByLpu" horizontalFill="true" parentAutocomplete="department" fieldColSpan="3" />
        </msh:row>
        <msh:row >
          <msh:autoComplete labelColSpan="3" property="hospitalization" label="Госпитализация в данном году по данному заболевания"  vocName="vocHospitalization" horizontalFill="true" fieldColSpan="1" />
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
        <msh:ifFormTypeIsCreate formName="stac_sslAdmissionForm">
	        <msh:row>
	        	<msh:autoComplete parentId="stac_sslAdmissionForm.patient" property="attachedPolicies" label="Прик.полис ОМС" horizontalFill="true" fieldColSpan="3" vocName="omcPolicyByPatient"/>
	        </msh:row>
	        <msh:row>
	        	<msh:autoComplete parentId="stac_sslAdmissionForm.patient" property="attachedPolicyDmc" label="Прик.полис ДМС" horizontalFill="true" fieldColSpan="3" vocName="dmcPolicyByPatient"/>
	        </msh:row>
        </msh:ifFormTypeIsCreate>
        <msh:ifFormTypeIsNotView formName="stac_sslAdmissionForm" >
          <msh:row >
            <td colspan="9">
              <div id="formInfoMessage1" onclick="this.style.display = &quot;none&quot;">
                <table style="margin-left: 4em">
                  <tr>
                    <td>
                      <div class="formInfoMessage">* Поле отделение является обязательным. При отказе госпитализации не заполняется!!!</div>
                    </td>
                  </tr>
                </table>
              </div>
            </td>
          </msh:row>
        </msh:ifFormTypeIsNotView>
        <mis:ifPatientIsWoman classByObject="Patient" idObject="stac_sslAdmissionForm.patient" roles="/Policy/Mis/Pregnancy/History/View">
        <msh:separator label="Беременность" colSpan="9"/>
        <msh:row>
        	<msh:autoComplete viewAction="entityParentView-preg_pregnancy.do" property="pregnancy" label="Беременность" fieldColSpan="3" parentId="stac_sslAdmissionForm.patient" vocName="pregnancyByPatient" horizontalFill="true"/>
        </msh:row>
        <msh:ifFormTypeIsCreate formName="stac_sslAdmissionForm">
        <msh:row>
        	<msh:textField property="pregnancyOrderNumber" label="Какая беременность"/>
        	<msh:textField property="childbirthAmount" label="Кол-во родов"/>
        </msh:row>
        </msh:ifFormTypeIsCreate>
        </mis:ifPatientIsWoman>
        <msh:separator label="Дополнительно" colSpan="9"  />
        <msh:row >
          <msh:checkBox property="noActuality" label="Недействительность"  />
        </msh:row>
        </msh:panel>
        <msh:panel>
        <msh:row>
        	<msh:label property="createDate" label="Дата создания"/>
        	<msh:label property="createTime" label="время"/>
            <msh:label property="username" label="пользователь"  />
        </msh:row>
        <msh:row>
        	<msh:label property="editDate" label="Дата редак."/>
        	<msh:label property="editTime" label="время"/>
          	<msh:label property="editUsername" label="пользователь"  />
        </msh:row>
        <msh:row>
            <msh:label property="identDate" label="Дата идент."/>
            <msh:label property="identTime" label="время"/>
            <msh:label property="identUsername" label="пользователь"  />
        </msh:row>
        <msh:submitCancelButtonsRow  functionSubmit="saveIdentityWithAsk();" colSpan="4" labelSave="Сохранить изменения" labelCreating="Создание" labelCreate="Создать новый случай" labelSaving="Сохранение данных" />
      </msh:panel>
    </msh:form>
    <tags:stac_infoBySls form="stac_sslAdmissionForm"/>

    <msh:ifFormTypeIsCreate formName="stac_sslAdmissionForm">
    <tags:hosp_263 name="Direct"/>
        	<msh:ifInRole roles="/Policy/Mis/MedCase/Stac/Ssl/Admission/CreateStatCardNumberByHand">
    		<script type="text/javascript">
    			$('statCardNumber').select() ;
    			$('statCardNumber').focus() ;
    		</script>
    	</msh:ifInRole>
    	<msh:ifNotInRole roles="/Policy/Mis/MedCase/Stac/Ssl/Admission/CreateStatCardNumberByHand">
    		<script type="text/javascript">
    			$('dateStart').select() ;
    			$('dateStart').focus() ;
    		</script>
    	</msh:ifNotInRole>
    </msh:ifFormTypeIsCreate>
    <msh:ifFormTypeAreViewOrEdit formName="stac_sslAdmissionForm">
    <msh:ifFormTypeIsNotView formName="stac_sslAdmissionForm">
    	<msh:ifInRole roles="/Policy/Mis/MedCase/Stac/Ssl/Admission/ChangeStatCardNumber">
    		<script type="text/javascript">
    			$('statCardNumber').select() ;
    			$('statCardNumber').focus() ;
    		</script>
    	</msh:ifInRole>
    	<msh:ifNotInRole roles="/Policy/Mis/MedCase/Stac/Ssl/Admission/ChangeStatCardNumber">
    		<script type="text/javascript">
    			$('dateStart').select() ;
    			$('dateStart').focus() ;
    		</script>
    	</msh:ifNotInRole>
			   	
    	</msh:ifFormTypeIsNotView>
    </msh:ifFormTypeAreViewOrEdit>
  </tiles:put>
  <tiles:put name="javascript" type="string">
  	
    <script type="text/javascript" src="./dwr/interface/PatientService.js"></script>
    <script type="text/javascript" src="./dwr/interface/HospitalMedCaseService.js"></script>

      <script>
          //сохранить/проставить идентификацию
          function saveIdentityWithAsk() {
              <msh:ifFormTypeIsCreate formName="stac_sslAdmissionForm">
              $('isIdentified').value = confirm('Проведена ли идентификация личности пациента?');
                  document.forms["mainForm"].submit();
              </msh:ifFormTypeIsCreate>
              <msh:ifFormTypeAreViewOrEdit formName="stac_sslAdmissionForm">
                  <msh:ifFormTypeIsNotView formName="stac_sslAdmissionForm">
                        document.forms["mainForm"].submit();
                  </msh:ifFormTypeIsNotView>
                  <msh:ifFormTypeIsView formName="stac_sslAdmissionForm">
              <msh:ifInRole roles="/Policy/Mis/MedCase/Stac/PatientIdentify">
                  HospitalMedCaseService.getIsPatientIdentified(${param.id}, {
                      callback: function(aResult) {
                          if (aResult!='1' && confirm('Проведена ли идентификация личности пациента?')) {
                              HospitalMedCaseService.setIsPatientIdentified(${param.id}, {
                                  callback: function() {
                                      showToastMessage('Отметка об идентификации пациента проставлена',null,true);
                                      window.location.reload() ;
                                  }
                              });
                          }
                      }
                  });
              </msh:ifInRole>
                  </msh:ifFormTypeIsView>
              </msh:ifFormTypeAreViewOrEdit>
          }
          <msh:ifFormTypeAreViewOrEdit formName="stac_sslAdmissionForm">
          <msh:ifFormTypeIsView formName="stac_sslAdmissionForm">
          saveIdentityWithAsk();
          </msh:ifFormTypeIsView>
          </msh:ifFormTypeAreViewOrEdit>

          eventutil.addEventListener($('weight'), "change",function(){
              $('weight').value=parseInt($('weight').value);
              if ($('weight').value=="NaN") $('weight').value="";
              var w = parseInt($('weight').value);
              var h = parseInt($('height').value);
              var imt=(w / (0.0001 * h * h)).toFixed(2);
              $('theIMT').value=imt;
              if ($('theIMT').value=="NaN") $('theIMT').value="0.0";
              $('theIMTReadOnly').value=$('theIMT').value;
          }) ;
          eventutil.addEventListener($('height'), "change",function(){
              $('height').value=parseInt($('height').value);
              if ($('height').value=="NaN") $('height').value="";
              var w = parseInt($('weight').value);
              var h = parseInt($('height').value);
              var imt=(w / (0.0001 * h * h)).toFixed(2);
              $('theIMT').value=imt;
              if ($('theIMT').value=="NaN") $('theIMT').value="0.0";
              $('theIMTReadOnly').value=$('theIMT').value;
          }) ;
          eventutil.addEventListener($('theIMT'), "change",function(){
              var w = parseInt($('weight').value);
              var h = parseInt($('height').value);
              var imt=(w / (0.0001 * h * h)).toFixed(2);
              $('theIMT').value=imt;
              if ($('theIMT').value=="NaN") $('theIMT').value="0.0";
          }) ;
      </script>
     <msh:ifFormTypeIsCreate formName="stac_sslAdmissionForm">
    	<msh:ifInRole roles="/Policy/Mis/MedCase/Stac/Ssl/Admission/TransferAtBudget">
	    	<script type="text/javascript">
	    	//TransferAtBudget
	    		var oldURL = document.forms[0].action ;
	    		document.forms[0].action = 'javascript:checkPolicy()' ;
	    		function checkPolicy() {
	    			var patPk = $('patient').value ;
	    			var streamPk = $('serviceStream').value ;
	    			
	    			HospitalMedCaseService.checkMedPolicy(patPk,streamPk, {
	                    callback: function(aResult) {
	                        //alert(aResult) ;
	                        if (+aResult==1)  {
	                        	//alert('Есть действующий полис!') ;
	                        } else if (confirm('Нет действующего полиса или корректно заполненного полиса у пациента!!! Вы хотите перевести его на бюджет?')) {
	                        		$('serviceStream').value = aResult.substring(aResult.indexOf('#')+1) ;

	                        }
	                        document.forms[0].action = oldURL ;
	                        document.forms[0].submit() ;
	                      }
	                     }
	                    );
	    			
	    		}


	    	</script>
    	</msh:ifInRole>
         <script type="text/javascript">
             if (+'${param.preHosp}'>0) {
                 HospitalMedCaseService.getInfoByPreHosp(+'${param.preHosp}', {
                     callback: function(ret) {
                         ret = JSON.parse(ret);
                         if (ret.length>0) {
                             ret = ret[0];
                             $('preHosp').value='${param.preHosp}';
                             $('serviceStream').value = ret.serviceStream;
                             $('serviceStreamName').value = ret.serviceStreamName;
                             $('orderLpu').value=ret.orderLpu;
                             $('orderLpuName').value=ret.orderLpuName;
                             $('department').value=ret.department;
                             $('departmentName').value=ret.departmentName;
                             $('orderMkb').value=ret.orderMkb;
                             $('orderMkbName').value=ret.orderMkbName;
                             $('orderDiagnos').value=ret.orderDiagnos;
                         }

                     }
                 });
             }
         </script>
    </msh:ifFormTypeIsCreate>
    <msh:ifFormTypeIsNotView formName="stac_sslAdmissionForm" >
      <msh:ifInRole roles="/Policy/Mis/Contract/MedContract/ContractGuarantee/ContractGuaranteeLetter/View">
      <script type="text/javascript" src="./dwr/interface/ContractService.js"></script>
      <script type="text/javascript">
       	function checkIfDogovorNeeded() {
  		if (+$('serviceStream').value>0 && $('guaranteeName')) {
  			ContractService.checkIfDogovorIsNeeded($('patient').value, $('serviceStream').value, $('dateStart').value,null,'HOSPITAL', {
  	  			callback: function (letter) {
  	  			    letter = JSON.parse(letter);
  	  				if (letter.status=="ok") {
                        console.log("2"+letter.guaranteeInfo);
  	  					if (letter.guaranteeInfo) { //нашли г. письмо
  	  						$('guarantee').value = letter.id;
  	  						$('guaranteeName').value = letter.guaranteeInfo;
  	  					}	 
  	  				} else {
  	  				    showToastMessage(letter.errorName);
                    }
  	  			$('guaranteeName').disabled=true;
  	  			}
  	  		});
  		}
  		
  	}
       	</script>
      </msh:ifInRole>
      <msh:ifNotInRole roles="/Policy/Mis/Contract/MedContract/ContractGuarantee/ContractGuaranteeLetter/View">
      <script type="text/javascript"> function checkIfDogovorNeeded() {}
      </script>
      </msh:ifNotInRole>
    <script type="text/javascript">

    serviceStreamAutocomplete.addOnChangeCallback(function(){checkIfDogovorNeeded();});
    function viewTable263narp_byPat() {
    	if ($('orderDate').value=="") {
    		alert("Введите дату направления");
    		$('orderDate').focus() ;
    		$('orderDate').select() ;
    	} else {
    		showDirect263naprByPat($('patient').value,$('orderDate').value) ;
    	}
    }
    function setHospByHDF(aHDF,aPat) {
    	HospitalMedCaseService.getInfoByHDF(aHDF,{
 			callback: function(aResult) {
 				result=aResult.split("###@###") ;
 				$('orderLpu').value=result[0];
 				$('orderLpuName').value=result[1];
 				$('orderNumber').value=result[2];
 				$('orderDate').value=result[3];
 				$('orderMkb').value=result[4];
 				$('orderMkbName').value=result[5];
 				cancelDirect263() ;
 			}
    	}) ;
    }
		try{	
		    if (orderMkbAutocomplete) orderMkbAutocomplete.addOnChangeCallback(function() {
	      	 	setDiagnosisText('orderMkb','orderDiagnos');
	    });} catch(e) {}
  		function setDiagnosisText(aFieldMkb,aFieldText) {
  			var val = $(aFieldMkb+'Name').value ;
  			var ind = val.indexOf(' ') ;
  			//alert(ind+' '+val)
  			if (ind!=-1) {
  				if ($(aFieldText).value=="") $(aFieldText).value=val.substring(ind+1) ;
  			}
  		}
  		</script> 
      <script type="text/javascript">// при отказе в госпитализации ставим признак "Амбулаторное лечение"
      eventutil.addEventListener($('emergency'), "change",function(){
            if (+$('emergency').checked) {
                $('orderTypeName').className="autocomplete horizontalFill required";
                $('preAdmissionTimeName').className="autocomplete horizontalFill required";
                $('height').className=" ";
                $('weight').className=" ";
            } else {
                $('orderTypeName').className="autocomplete horizontalFill";
                $('preAdmissionTimeName').className="autocomplete horizontalFill";
                $('height').className=" required";
                $('weight').className=" required";

        	}

        }) ;
		 if (+$('emergency').checked) {
             $('orderTypeName').className="autocomplete horizontalFill required";
             $('preAdmissionTimeName').className="autocomplete horizontalFill required";
             $('height').className=" ";
             $('weight').className=" ";
         } else {
             $('orderTypeName').className="autocomplete horizontalFill";
             $('preAdmissionTimeName').className="autocomplete horizontalFill";
             $('height').className=" required";
             $('weight').className=" required";
     	}
		if ($('deniedHospitalizatingName')) {
            var ch = function ch() {
            
                if (+$('deniedHospitalizating').value>0) {
                    $('ambulanceTreatment').checked = true;
                    $('medicalAid').checked = true;
                    $('departmentName').className="autocomplete horizontalFill";
                    $('hospitalizationName').className="autocomplete horizontalFill";
                    $('serviceStreamName').className="autocomplete horizontalFill";
                } else {
                	$('ambulanceTreatment').checked = false;
                    $('medicalAid').checked = false;
                    $('departmentName').className="autocomplete horizontalFill required";
                    $('hospitalizationName').className="autocomplete horizontalFill required";
                    $('serviceStreamName').className="autocomplete horizontalFill required";
            	}
            }              
       		eventutil.addEventListener($('deniedHospitalizatingName'),'blur',ch);
       		if (+$('deniedHospitalizating').value>0) {
                $('ambulanceTreatment').checked = true;
                $('departmentName').className="autocomplete horizontalFill";
                $('hospitalizationName').className="autocomplete horizontalFill";
                $('serviceStreamName').className="autocomplete horizontalFill";
            } else {
            	$('ambulanceTreatment').checked = false;
                $('departmentName').className="autocomplete horizontalFill required";
                $('hospitalizationName').className="autocomplete horizontalFill required";
                $('serviceStreamName').className="autocomplete horizontalFill required";
        	}
        }

		</script>
    </msh:ifFormTypeIsNotView>
    <msh:ifFormTypeIsView formName="stac_sslAdmissionForm" >
      <script type="text/javascript">/** функция изменения стат.номера*/
      function changeStatCardNumber() {
            var slsPk = $('id').value ;
            var oldStatCardNumber = $('statCardNumber').value ;
            var statCardNumberField = $('statCardNumber') ;
            var newNumber = prompt("Введите новый номер стат. карты ", oldStatCardNumber);

            if (newNumber!=null) {
                if (newNumber!=oldStatCardNumber) {
	
	                HospitalMedCaseService.changeStatCardNumber(newNumber, slsPk, {
	                    callback: function(aResult) {
	                        alert("Номер карты изменен на "+aResult) ;
	                        if ($('statCardNumberReadOnly')) {
		                        $('statCardNumberReadOnly').value = aResult ;
		                        $('statCardNumber').value = aResult ;
	                        }
	                    }
	                }
	            );
	
	            }
	        }
        }
          <msh:ifInRole roles="/Policy/Mis/Journal/CheckDiabetes">
          function goCreateAssessmentCard(codeCard) {
              if (codeCard) {
                  HospitalMedCaseService.getDiabetCardByCode(
                      codeCard, {
                          callback: function(typeCard) {
                              var way = "entityParentPrepareCreate-mis_assessmentCard.do?id="+$('patient').value+"&slo="+${param.id};
                              window.location.href = typeCard? way+"&typeCard="+typeCard : way;
                          }}
                  );
              }
              else
                  window.location.href ="entityParentPrepareCreate-mis_assessmentCard.do?id="+$('patient').value+"&slo="+${param.id};
          }
      </msh:ifInRole>
      </script>
        
        
    </msh:ifFormTypeIsView>
  </tiles:put>
  <tiles:put type="string" name="style">
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
  </tiles:put>
</tiles:insert>

