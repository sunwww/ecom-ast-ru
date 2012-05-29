<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://struts.apache.org/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://www.nuzmsh.ru/tags/msh" prefix="msh" %>
<%@ taglib uri="http://www.ecom-ast.ru/tags/ecom" prefix="ecom" %>
<%@ taglib tagdir="/WEB-INF/tags" prefix="tags" %>

<tiles:insert page="/WEB-INF/tiles/mainLayout.jsp" flush="true">
	<tiles:put name="style" type="string">
        <style type="text/css">


            #clinicalDiagnosLabel, #clinicalMkbLabel, #clinicalActuityLabel {
                color: blue ;
            }
        </style>
     </tiles:put>
  
  <tiles:put name="title" type="string">
    <ecom:titleTrail mainMenu="Patient" beginForm="stac_extsslForm" guid="638ddd30-b48e-4058-b3ad-866c0c70ee1f" />
  </tiles:put>
  <tiles:put name="side" type="string">
    <msh:ifFormTypeAreViewOrEdit formName="stac_extsslForm" guid="c3694b44-f3ad-44fd-b4ee-a92ded473300">
 <msh:sideMenu title="Госпитализация в другом ЛПУ">
        <msh:sideLink action="/entityParentEdit-stac_extssl" params="id" name="Изменить" title="Изменить" roles="/Policy/Mis/MedCase/Stac/Ssl/Ext/Edit"/>
        <msh:sideLink action="/entityParentDeleteGoParentView-stac_extssl" params="id" name="Удалить" title="Удалить" key="ALT+DEL" roles="/Policy/Mis/MedCase/Stac/Ssl/Ext/Delete" />
        <msh:sideLink action="/stac_sslList.do?sslid=${param.id}" name="⇧Все госпитализации пациента" title="Все госпитализации пациента" />
</msh:sideMenu>
    </msh:ifFormTypeAreViewOrEdit>
  </tiles:put>
  <tiles:put name="body" type="string">
    <!-- 
    	  - Случай стационарного лечения (поступление)
    	  -->
    <msh:form action="/entityParentSaveGoView-stac_extssl.do" defaultField="" title="Госпитализация в другом ЛПУ" editRoles="/Policy/Mis/MedCase/Stac/Ssl/Ext/Edit" createRoles="/Policy/Mis/MedCase/Stac/Ssl/Ext/Create" guid="b37b5d4a-9687-45bf-b42c-613fd9b1739d">
      <msh:hidden property="id" guid="c5a9c42c-d593-4fae-a685-44586a81938b" />
      <msh:hidden property="patient" guid="fbedab7b-1153-4f3c-b9d4-14643e32a6f7" />
      <msh:hidden property="outcome" guid="fbed4-14643e32a6f7" />
      <msh:hidden property="saveType" guid="7cb7bcb7-2cb3-4e65-92ce-2a4a2cec809f" />
      <msh:hidden property="dischargeTime" guid="5d563628-c95b-40a5-a3d6-aa2d99fa0e74" />
      <msh:hidden property="provisional" guid="38fe07ac-6706-4911-a217-65edb3c85dac" />
      <msh:hidden property="result" guid="156ff02c-61dd-40b9-80f4-d88885db16f8" />
      <msh:hidden property="moveToAnotherLPU" guid="c0b69264-2081-4952-8c0a-7ea12712f14c" />
      <msh:hidden property="rwDate" guid="9438b469-d5b6-4d11-8dc9-91a551e2f2d1" />
      <msh:hidden property="rwNumber" guid="70e2513e-0d2e-48fd-9d08-3e83415755f9" />
      <msh:hidden property="dischargeEpicrisis" guid="290e9247-43d1-4f8b-a7c5-3a091d9f78ce" />
      <msh:hidden property="rareCase"/>
      <msh:ifNotInRole roles="/Policy/Mis/MedCase/IsPsychiatry">
        <msh:hidden property="compulsoryTreatment"/>
        <msh:hidden property="incapacity"/>
        <msh:hidden property="lawCourtDesicionDate"/>
        <msh:hidden property="psychReason"/>      
      </msh:ifNotInRole>
      <msh:panel guid="6e8d827a-d32c-4a05-b4b0-5ff7eed6eedc">
	

        <msh:row guid="0e91a1ca-c366-435c-8f2c-274d23d87fd3">
          <msh:textField property="dateStart" label="Дата поступления"  />
          <msh:textField property="dateFinish" label="Дата выписки"  />
        </msh:row>
        <msh:row guid="e101a36c-d874-4d43-9cfe-fff88ff64ffa">
          <msh:autoComplete property="lpu" label="Лечебное учреждение" vocName="lpu" fieldColSpan="3" horizontalFill="true" guid="ee4b9-2961-42be-9a05-caff23" />
        </msh:row>

        <msh:separator label="Направлен" colSpan="6" guid="fd40b634-ea84-450b-ac57-60e339d4fd11" />
        <msh:row guid="a53d1f37-afe8-4779-9e63-0b2684e14828">
          <msh:autoComplete property="orderLpu" label="Кем направлен" vocName="mainLpu" guid="c44b474f-6dba-4ba8-9af7-56a0dca363ad" horizontalFill="true" fieldColSpan="3" />
        </msh:row>

        <msh:ifInRole roles="/Policy/Mis/MedCase/IsPsychiatry">
        <msh:row>
	        <msh:autoComplete label="Причина госпитализации в псих. стационар" vocName="vocPsychHospitalReason" property="psychReason" labelColSpan="2" fieldColSpan="2" horizontalFill="true"/>
        </msh:row>

        <msh:row>
        	<msh:checkBox property="compulsoryTreatment" label="Принудительное лечение"/>
        </msh:row>
        <msh:row>
        	<msh:checkBox property="incapacity" label="Недееспособный (статья 29)"/>
        	<msh:textField property="lawCourtDesicionDate" label="Дата решения суда"/>
        </msh:row>
        </msh:ifInRole>
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
        <msh:row>
	        <msh:autoComplete vocName="vocIdc10" label="МКБ-10 клин.диаг.соп." property="concomitantMkb" fieldColSpan="3" horizontalFill="true"/>
        </msh:row>
        <msh:row>
    	    <msh:textField label="Клин. диаг. сопут" property="concomitantDiagnos" fieldColSpan="3" horizontalFill="true"/>
        </msh:row>
        <msh:separator label="Дополнительно" colSpan="9" guid="777a7e06-fef8-40cc-ad41-bc3ee2511aab" />
        <msh:row guid="fa25468-6d3f-4cf9-bb0a-5c7fe0b25e53">
          <msh:label property="username" label="Оператор" guid="2258d5ca-cde5-46e9-a1cc-3ffc278353fe" />
          <msh:checkBox property="noActuality" label="Недействительность" guid="6299a6be-428f-4a09-9db5-e4c60154b605" />
        </msh:row>
        <msh:submitCancelButtonsRow guid="submitCancel" colSpan="4" labelSave="Сохранить изменения" labelCreating="Создание" labelCreate="Создать новый случай" labelSaving="Сохранение данных" />
      </msh:panel>
    </msh:form>
    <msh:ifFormTypeIsView formName="stac_extsslForm">
      <msh:ifInRole roles="/Policy/Mis/MedCase/Diagnosis/View" guid="4cc62e2b-bf2a-4839-8bab-481886e8e721">
         <ecom:webQuery name="diags" nativeSql="select Diagnosis.id, VocDiagnosisRegistrationType.name as vdrtname, Diagnosis.establishDate, &#xA;  ' '||Diagnosis.name,VocIdc10.code, VocPriorityDiagnosis.name as vpdname&#xA;  from Diagnosis&#xA;  left outer join VocDiagnosisRegistrationType on Diagnosis.registrationType_id = VocDiagnosisRegistrationType.id left outer join VocPriorityDiagnosis on Diagnosis.priority_id = VocPriorityDiagnosis.id&#xA;  left outer join VocIdc10     on Diagnosis.idc10_id = VocIdc10.id&#xA; where Diagnosis.medCase_id=${param.id} &#xA;" guid="b72ef17b-92bc-4d99-9d2f-a129e9b2cc3f" />
        <msh:section title="Диагнозы. <a href='entityParentPrepareCreate-stac_diagnosis.do?id=${param.id }'>Добавить новый диагноз</a>" guid="a2bc2525-333d-4548-a7bf-0065569f87ba">
          <msh:table name="diags" action="entityParentView-stac_diagnosis.do" idField="1" guid="7312ce0c-0c61-482d-9079-71b2a0f29484">
              <msh:tableColumn property="sn" columnName="#" />
            <msh:tableColumn columnName="Тип регистрации" property="2" guid="e2fc05a6-e089-4c90-8fb2-a8377e6f8967" />
            <msh:tableColumn columnName="Приоритет" property="6" guid="e2fc05a6-e089-4c90-8fb2-a8377e6f8967" />
            <msh:tableColumn columnName="Дата" property="3" guid="718ec416-3543-4f8d-89cd-b24aa8177377" />
            <msh:tableColumn columnName="Наименование" property="4" guid="2a519337-384d-4695-9d19-72dd7e02936c" />
            <msh:tableColumn columnName="Код МКБ" property="5" guid="150732a8-0a5a-4ac3-bbbb-9ff669be37a6" />
          </msh:table>
        </msh:section>
      </msh:ifInRole>
    </msh:ifFormTypeIsView>

  </tiles:put>
  <tiles:put type="string" name="javascript">
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
    function setDiagnosisText(aFieldMkb,aFieldText) {
			var val = $(aFieldMkb+'Name').value ;
			var ind = val.indexOf(' ') ;
			//alert(ind+' '+val)
			if (ind!=-1) {
				if ($(aFieldText).value=="") $(aFieldText).value=val.substring(ind+1) ;
			}
		}
  	</script>
  </tiles:put>

</tiles:insert>

