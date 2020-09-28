<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://struts.apache.org/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://www.nuzmsh.ru/tags/msh" prefix="msh" %>
<%@ taglib uri="http://www.ecom-ast.ru/tags/ecom" prefix="ecom" %>
<%@ taglib tagdir="/WEB-INF/tags" prefix="tags" %>
<%@ taglib uri="/WEB-INF/mis.tld" prefix="mis" %>

<tiles:insert page="/WEB-INF/tiles/mainLayout.jsp" flush="true">

  <tiles:put name="title" type="string">
    <ecom:titleTrail mainMenu="Patient" beginForm="stac_sslAdmissionShortForm" />
  </tiles:put>
  <tiles:put name="side" type="string">
    <msh:ifFormTypeAreViewOrEdit formName="stac_sslAdmissionShortForm">
      <tags:stac_hospitalMenu currentAction="stac_sslAdmission" />
    </msh:ifFormTypeAreViewOrEdit>
  </tiles:put>
  <tiles:put name="body" type="string">
    <!-- 
    	  - Случай стационарного лечения (поступление)
    	  -->
    <msh:form action="/entityParentSaveGoView-stac_sslAdmissionShort.do" defaultField="statCardNumber" title="Случай стационарного лечения. Поступление" editRoles="/Policy/Mis/MedCase/Stac/Ssl/Admission/Edit" createRoles="/Policy/Mis/MedCase/Stac/Ssl/Admission/Create">
      <msh:hidden property="id" />
      <msh:hidden property="patient" />
      <msh:hidden property="outcome" />
      <msh:hidden property="saveType" />
      <msh:hidden property="dateFinish" />
      <msh:hidden property="dischargeTime" />
      <msh:hidden property="result" />
      <msh:hidden property="moveToAnotherLPU" />
      <msh:hidden property="height" />
      <msh:hidden property="weight" />

       <msh:ifNotInRole roles="/Policy/Mis/Contract/MedContract/ContractGuarantee/ContractGuaranteeLetter/View">
      <msh:hidden property="guarantee"/>
      </msh:ifNotInRole>       
      <msh:hidden property="dischargeEpicrisis" />

      <msh:hidden property="rareCase"/>
      <msh:panel>
        <msh:separator label="Приемное отделение" colSpan="9" />
     	
        <msh:row>
          <msh:autoComplete property="lpu" label="Лечебное учреждение" vocName="lpu" fieldColSpan="3" horizontalFill="true" />
        </msh:row>
        <msh:row>
          <msh:textField property="statCardNumber" label="Номер стат.карты" labelColSpan="1" />
          <msh:autoComplete vocName="vocHospType" size="35" property="hospType" label="Тип тек. стационара" fieldColSpan="1" horizontalFill="true" />
        </msh:row>

        <msh:row>
          <msh:textField property="dateStart" label="Дата поступления" />
          <msh:textField property="entranceTime" label="время" fieldColSpan="3" />
        </msh:row>
        <msh:row>
          <msh:autoComplete vocName="vocServiceStream"  property="serviceStream" label="Поток обслуживания" fieldColSpan="3" horizontalFill="true" />
        </msh:row>
        <msh:ifInRole roles="/Policy/Mis/Contract/MedContract/ContractGuarantee/ContractGuaranteeLetter/View">
      	<msh:row>
          <msh:autoComplete  vocName="guaranteeByPatient" parentId="smo_directionForm.patient" property="guarantee" label="Гарантийное письмо" fieldColSpan="3" horizontalFill="true" />
        </msh:row>
      </msh:ifInRole>
        <msh:row>
        	<msh:autoComplete property="kinsman" label="Представитель (иног.)" 
        	parentId="stac_sslAdmissionShortForm.patient" vocName="kinsmanBySMO" horizontalFill="true" fieldColSpan="3"/>
        </msh:row>
        <msh:ifInRole roles="/Policy/Mis/Patient/Newborn">
	        <msh:row>
	        	<msh:checkBox property="hotelServices" label="Находится в больнице по уходу за пациентом" fieldColSpan="3"/>
	        </msh:row>
        </msh:ifInRole>
        <msh:ifNotInRole roles="/Policy/Mis/Patient/Newborn">
        	<msh:hidden property="hotelServices"/>
        </msh:ifNotInRole>
        <msh:row>
          <msh:textField property="attendant" label="Сопровождающее лицо" fieldColSpan="3" horizontalFill="true" />
        </msh:row>
        <msh:row>
          <msh:checkBox hideLabel="false" property="emergency" viewOnlyField="false" horizontalFill="false" />
        </msh:row>
        <msh:separator label="Направлен" colSpan="6" />
        <msh:row>
          <msh:autoComplete property="orderLpu" label="Кем направлен" vocName="mainLpu" horizontalFill="true" fieldColSpan="3" />
        </msh:row>
        <msh:row>
          <msh:autoComplete vocName="vocHospType" property="sourceHospType" label="Тип направившего ЛПУ" fieldColSpan="1" horizontalFill="true" />
        </msh:row>
         <msh:row>
          <msh:textField property="orderNumber" label="№ напр" />
          <msh:textField property="orderDate" label="Дата" />
        </msh:row>

        <msh:row>
          <msh:autoComplete property="orderMkb" label="Код МКБ направителя" vocName="vocIdc10" horizontalFill="true" fieldColSpan="3" />
        </msh:row>
        <msh:row>
          <msh:textField property="orderDiagnos" label="ДИАГНОЗ напр. учреждения" fieldColSpan="3" horizontalFill="true" />
        </msh:row>
        <msh:separator label="Доставлен" colSpan="9" />
        <msh:row>
          <msh:autoComplete showId="false" vocName="vocOmcFrm" hideLabel="false" property="orderType" viewOnlyField="false" horizontalFill="true" fieldColSpan="3"/>
        </msh:row>
        <msh:row>
          <msh:autoComplete property="preAdmissionTime" label="Часы заболевания" vocName="vocPreAdmissionTime" horizontalFill="true" fieldColSpan="3"/>
        </msh:row>
        <msh:separator label="*Госпитализация" colSpan="9" />
        <msh:row>
          <msh:autoComplete property="department" label="Отделение" vocName="vocLpuHospOtd" horizontalFill="true" parentAutocomplete="lpu" fieldColSpan="3" />
        </msh:row>
        <msh:row>
          <msh:autoComplete labelColSpan="3" property="hospitalization" label="Госпитализация в данном году по данному заболевания" vocName="vocHospitalization" horizontalFill="true" fieldColSpan="1" />
        </msh:row>
        <msh:ifFormTypeIsCreate formName="stac_sslAdmissionShortForm">
	        <msh:row>
	        	<msh:autoComplete parentId="stac_sslAdmissionShortForm.patient" property="attachedPolicies" label="Прик.полис ОМС" horizontalFill="true" fieldColSpan="3" vocName="omcPolicyByPatient"/>
	        </msh:row>
	        <msh:row>
	        	<msh:autoComplete parentId="stac_sslAdmissionShortForm.patient" property="attachedPolicyDmc" label="Прик.полис ДМС" horizontalFill="true" fieldColSpan="3" vocName="dmcPolicyByPatient"/>
	        </msh:row>
        </msh:ifFormTypeIsCreate>
        <msh:separator label="Дополнительно" colSpan="9" />
        <msh:row>
          <msh:label property="username" label="Оператор" />
          <msh:checkBox property="noActuality" label="Недействительность" />
        </msh:row>
        <msh:submitCancelButtonsRow colSpan="4" labelSave="Сохранить изменения" labelCreating="Создание" labelCreate="Создать новый случай" labelSaving="Сохранение данных" />
      </msh:panel>
    </msh:form>
    <tags:stac_infoBySls form="stac_sslAdmissionShortForm"/>
    <msh:ifFormTypeIsCreate formName="stac_sslAdmissionShortForm">
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
    <msh:ifFormTypeAreViewOrEdit formName="stac_sslAdmissionShortForm">
    <msh:ifFormTypeIsNotView formName="stac_sslAdmissionShortForm">
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
  	
    <script type="text/javascript" src="./dwr/interface/HospitalMedCaseService.js">/**/</script>
 
    <script type="text/javascript" src="./dwr/interface/PatientService.js"></script>


     <msh:ifFormTypeIsCreate formName="stac_sslAdmissionShortForm">
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
	                        } else {
	                        	if (confirm('Нет действующего полиса или корректно заполненного полиса у пациента!!! Вы хотите перевести его на бюджет?')) {
	                        		$('serviceStream').value = aResult.substring(aResult.indexOf('#')+1) ;
	                        	} else {
	                        	}
	                        	
	                        }
	                        document.forms[0].action = oldURL ;
	                        document.forms[0].submit() ;
	                      }
	                     }
	                    );
	    			
	    		}
	    	</script>
    	</msh:ifInRole>
    </msh:ifFormTypeIsCreate>
    <msh:ifFormTypeIsNotView formName="stac_sslAdmissionShortForm">
    <msh:ifInRole roles="/Policy/Mis/Contract/MedContract/ContractGuarantee/ContractGuaranteeLetter/View">
      <script type="text/javascript" src="./dwr/interface/ContractService.js"></script>
      <script type="text/javascript">
       	function checkIfDogovorNeeded() {
  		if (+$('serviceStream').value>0&&$('guaranteeName')) {
  			ContractService.checkIfDogovorIsNeeded($('patient').value, $('serviceStream').value, $('dateStart').value,null,'HOSPITAL', {
  	  			callback: function (res) {
  	  				if (res!=null&&res!='') {
  	  					if (res.startsWith("0")) {
  	  						alert ("Ошибка: "+res.substring(1));
  	  					} else {
  	  						var arr = res.substring(1).split("|");
  	  						$('guarantee').value = arr[0];
  	  						$('guaranteeName').value = arr[1];
  	  					}	 
  	  				} else {
  	  				
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
      <script type="text/javascript">// при отказе в госпитализации ставим признак "Амбулаторное лечение"
      serviceStreamAutocomplete.addOnChangeCallback(function(){checkIfDogovorNeeded();});
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
		
                    $('departmentName').className="autocomplete horizontalFill required";
                    $('hospitalizationName').className="autocomplete horizontalFill required";
                    $('serviceStreamName').className="autocomplete horizontalFill required";
                    
                    eventutil.addEventListener($('emergency'), "change",function(){
                        if (+$('emergency').checked) {
                            $('orderTypeName').className="autocomplete horizontalFill required";
                            $('preAdmissionTimeName').className="autocomplete horizontalFill required";
                        } else {
                            $('orderTypeName').className="autocomplete horizontalFill";
                            $('preAdmissionTimeName').className="autocomplete horizontalFill";
                    	}

                    }) ;
            		 if (+$('emergency').checked) {
                         $('orderTypeName').className="autocomplete horizontalFill required";
                         $('preAdmissionTimeName').className="autocomplete horizontalFill required";
                     } else {
                         $('orderTypeName').className="autocomplete horizontalFill";
                         $('preAdmissionTimeName').className="autocomplete horizontalFill";
                 	}
        </script>
    </msh:ifFormTypeIsNotView>
    <msh:ifFormTypeIsView formName="stac_sslAdmissionShortForm">
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
        }</script>
    </msh:ifFormTypeIsView>
  </tiles:put>
</tiles:insert>

