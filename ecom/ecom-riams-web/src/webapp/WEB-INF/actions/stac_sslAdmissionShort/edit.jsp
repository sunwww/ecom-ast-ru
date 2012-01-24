<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://struts.apache.org/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://www.nuzmsh.ru/tags/msh" prefix="msh" %>
<%@ taglib uri="http://www.ecom-ast.ru/tags/ecom" prefix="ecom" %>
<%@ taglib tagdir="/WEB-INF/tags" prefix="tags" %>
<%@ taglib uri="/WEB-INF/mis.tld" prefix="mis" %>

<tiles:insert page="/WEB-INF/tiles/mainLayout.jsp" flush="true">

  <tiles:put name="title" type="string">
    <ecom:titleTrail mainMenu="Patient" beginForm="stac_sslAdmissionShortForm" guid="638ddd30-b48e-4058-b3ad-866c0c70ee1f" />
  </tiles:put>
  <tiles:put name="side" type="string">
    <msh:ifFormTypeAreViewOrEdit formName="stac_sslAdmissionShortForm" guid="c3694b44-f3ad-44fd-b4ee-a92ded473300">
      <tags:stac_hospitalMenu currentAction="stac_sslAdmission" />
    </msh:ifFormTypeAreViewOrEdit>
  </tiles:put>
  <tiles:put name="body" type="string">
    <!-- 
    	  - Случай стационарного лечения (поступление)
    	  -->
    <msh:form action="/entityParentSaveGoView-stac_sslAdmissionShort.do" defaultField="statCardNumber" title="Случай стационарного лечения. Поступление" editRoles="/Policy/Mis/MedCase/Stac/Ssl/Admission/Edit" createRoles="/Policy/Mis/MedCase/Stac/Ssl/Admission/Create" guid="b37b5d4a-9687-45bf-b42c-613fd9b1739d">
      <msh:hidden property="id" guid="c5a9c42c-d593-4fae-a685-44586a81938b" />
      <msh:hidden property="patient" guid="fbedab7b-1153-4f3c-b9d4-14643e32a6f7" />
      <msh:hidden property="outcome" guid="fbed4-14643e32a6f7" />
      <msh:hidden property="saveType" guid="7cb7bcb7-2cb3-4e65-92ce-2a4a2cec809f" />
      <msh:hidden property="dateFinish" guid="526ef07a-eb53-47a9-b145-02a81ede9b25" />
      <msh:hidden property="dischargeTime" guid="5d563628-c95b-40a5-a3d6-aa2d99fa0e74" />
      <msh:hidden property="provisional" guid="38fe07ac-6706-4911-a217-65edb3c85dac" />
      <msh:hidden property="result" guid="156ff02c-61dd-40b9-80f4-d88885db16f8" />
      <msh:hidden property="moveToAnotherLPU" guid="c0b69264-2081-4952-8c0a-7ea12712f14c" />
      <msh:hidden property="rwDate" guid="9438b469-d5b6-4d11-8dc9-91a551e2f2d1" />
      <msh:hidden property="rwNumber" guid="70e2513e-0d2e-48fd-9d08-3e83415755f9" />
      <msh:hidden property="dischargeEpicrisis" guid="290e9247-43d1-4f8b-a7c5-3a091d9f78ce" />
      <msh:hidden property="rareCase"/>
      <msh:panel guid="6e8d827a-d32c-4a05-b4b0-5ff7eed6eedc">
        <msh:separator label="Приемное отделение" colSpan="9" guid="af11419b-1c80-4025-be30-b7e83df06024" />
     	
        <msh:row guid="e101a36c-d874-4d43-9cfe-fff88ff64ffa">
          <msh:autoComplete property="lpu" label="Лечебное учреждение" vocName="lpu" fieldColSpan="3" horizontalFill="true" guid="ee4b9-2961-42be-9a05-caff23" />
        </msh:row>
        <msh:row guid="25f2a536-4fb6-4413-89db-a478145e097e">
          <msh:textField property="statCardNumber" label="Номер стат.карты" labelColSpan="1" guid="e5f3d524-cca8-4a5a-a408-196ab6b79627" />
          <msh:autoComplete vocName="vocHospType" property="hospType" label="Тип тек. стационара" fieldColSpan="1" horizontalFill="true" guid="10h64-23b2-42c0-ba47-65p16c" />
        </msh:row>

        <msh:row guid="0e91a1ca-c366-435c-8f2c-274d23d87fd3">
          <msh:textField property="dateStart" label="Дата поступления" guid="e3fd4642-a532-4510-a528-c6e766328d61" />
          <msh:textField property="entranceTime" label="время" fieldColSpan="3" guid="f94ff57c-bbf9-44f8-9e8d-f21927edbcff" />
        </msh:row>
        <msh:row guid="f2haba5-68fb-4ccc-9982-7b4480cmca147">
          <msh:autoComplete vocName="vocServiceStream"  property="serviceStream" label="Поток обслуживания" fieldColSpan="3" horizontalFill="true" guid="10h64-23b2-42c0-ba47-65p8g16c" />
        </msh:row>
        <msh:row>
        	<msh:autoComplete property="kinsman" label="Представитель (иног.)" 
        	parentId="stac_sslAdmissionShortForm.patient" vocName="kinsmanBySMO" horizontalFill="true" fieldColSpan="3"/>
        </msh:row>

        <msh:row guid="b128654b-7eff-41b3-b73f-3cb819122a44">
          <msh:checkBox hideLabel="false" property="emergency" viewOnlyField="false" guid="e3373f9c-ca5f-4aa2-957b-9e7a0cb6cc39" horizontalFill="false" />
        </msh:row>
        <msh:separator label="Направлен" colSpan="6" guid="fd40b634-ea84-450b-ac57-60e339d4fd11" />
        <msh:row guid="a53d1f37-afe8-4779-9e63-0b2684e14828">
          <msh:autoComplete property="orderLpu" label="Кем направлен" vocName="mainLpu" guid="c44b474f-6dba-4ba8-9af7-56a0dca363ad" horizontalFill="true" fieldColSpan="3" />
        </msh:row>
        <msh:row guid="f2aba5-68fb-4ccc-9982-7b44a147">
          <msh:autoComplete vocName="vocHospType" property="sourceHospType" label="Тип направившего ЛПУ" fieldColSpan="3" horizontalFill="true" guid="1064-23b2-42c0-ba47-65847816c" />
        </msh:row>
        <msh:row guid="36c67c6c-b817-4863-835d-0c37bcc96d19">
          <msh:autoComplete property="orderMkb" label="Код МКБ направителя" guid="d956d424-ffa2-4874-ae98-7a26fcc6a49d" vocName="vocIdc10" horizontalFill="true" fieldColSpan="3" />
        </msh:row>
        <msh:row guid="8036eaf5-7144-46a4-9015-e4f198230a2c">
          <msh:textField property="orderDiagnos" label="ДИАГНОЗ напр. учреждения" guid="7d5f0ad3-3f43-42b7-8c46-f2fcceead05c" fieldColSpan="3" horizontalFill="true" />
        </msh:row>
        <msh:separator label="Доставлен" colSpan="9" guid="f2136abf-c311-42e4-86ad-c24b7da708d5" />
        <msh:row guid="e811bd41-db0a-4275-b786-75f958759453">
          <msh:autoComplete showId="false" vocName="vocOmcFrm" hideLabel="false" property="orderType" viewOnlyField="false" guid="ff2a0045-c4fc-4efd-9bcd-f84951ac74a2" horizontalFill="true" fieldColSpan="3"/>
        </msh:row>
        <msh:separator label="*Госпитализация" colSpan="9" guid="4909ac97-3ad7-4eab-a657-1d103779ed47" />
        <msh:row guid="b88b81ab-1b89-4747-ac27-a865e920eb33">
          <msh:autoComplete property="department" label="Отделение" guid="bf59f5d5-2843-4abc-bf23-cbbbda89a67e" vocName="vocLpuOtd" horizontalFill="true" parentAutocomplete="lpu" fieldColSpan="3" />
        </msh:row>
        <msh:row guid="16f1e99-4017-4385-87c1-bf5895e2">
          <msh:autoComplete labelColSpan="3" property="hospitalization" label="Госпитализация в данном году по данному заболевания" guid="ddc10e76-8ee913984f" vocName="vocHospitalization" horizontalFill="true" fieldColSpan="1" />
        </msh:row>
        <msh:ifFormTypeIsCreate formName="stac_sslAdmissionShortForm">
	        <msh:row>
	        	<msh:autoComplete parentId="stac_sslAdmissionShortForm.patient" property="attachedPolicies" label="Прик.полис ОМС" horizontalFill="true" fieldColSpan="3" vocName="omcPolicyByPatient"/>
	        </msh:row>
	        <msh:row>
	        	<msh:autoComplete parentId="stac_sslAdmissionShortForm.patient" property="attachedPolicyDmc" label="Прик.полис ДМС" horizontalFill="true" fieldColSpan="3" vocName="dmcPolicyByPatient"/>
	        </msh:row>
        </msh:ifFormTypeIsCreate>
        <msh:separator label="Дополнительно" colSpan="9" guid="777a7e06-fef8-40cc-ad41-bc3ee2511aab" />
        <msh:row guid="fa25468-6d3f-4cf9-bb0a-5c7fe0b25e53">
          <msh:label property="username" label="Оператор" guid="2258d5ca-cde5-46e9-a1cc-3ffc278353fe" />
          <msh:checkBox property="noActuality" label="Недействительность" guid="6299a6be-428f-4a09-9db5-e4c60154b605" />
        </msh:row>
        <msh:submitCancelButtonsRow guid="submitCancel" colSpan="4" labelSave="Сохранить изменения" labelCreating="Создание" labelCreate="Создать новый случай" labelSaving="Сохранение данных" />
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
    <msh:ifFormTypeIsNotView formName="stac_sslAdmissionShortForm" guid="76f69ba0-a7b7-4cdb-8007-4de4ae2836ec">
      <script type="text/javascript">// при отказе в госпитализации ставим признак "Амбулаторное лечение"
                    $('departmentName').className="autocomplete horizontalFill required";
                    $('hospitalizationName').className="autocomplete horizontalFill required";
                    $('serviceStreamName').className="autocomplete horizontalFill required";
        </script>
    </msh:ifFormTypeIsNotView>
    <msh:ifFormTypeIsView formName="stac_sslAdmissionShortForm" guid="6c59f9b3-c9b2-4822-aff8-9225ca67edb1">
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

