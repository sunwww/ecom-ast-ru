<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://struts.apache.org/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://www.nuzmsh.ru/tags/msh" prefix="msh" %>
<%@ taglib uri="http://www.ecom-ast.ru/tags/ecom" prefix="ecom" %>
<%@ taglib tagdir="/WEB-INF/tags" prefix="tags" %>
<%@ taglib uri="/WEB-INF/mis.tld" prefix="mis" %>

<tiles:insert page="/WEB-INF/tiles/main${param.short}Layout.jsp" flush="true">

  <tiles:put name="title" type="string">
    <ecom:titleTrail mainMenu="Patient" beginForm="stac_sslAdmissionForm" guid="638ddd30-b48e-4058-b3ad-866c0c70ee1f" />
  </tiles:put>
  <tiles:put name="side" type="string">
    <msh:ifFormTypeAreViewOrEdit formName="stac_sslAdmissionForm" guid="c3694b44-f3ad-44fd-b4ee-a92ded473300">
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
 ,case when lpu.codef is null or lpu.codef='' then plpu.codef else lpu.codef end as l3puSent
 ,case when olpu.codef is null or olpu.codef='' then oplpu.codef else olpu.codef end as l4puDirect
 ,mkb.code as m5kbcode
 ,vbt.name as v6btcodef
 ,wchb.dateFrom as w7chbdatefrom
, vbst.name as v8bstcode
 from WorkCalendarHospitalBed wchb
 left join VocBedType vbt on vbt.id=wchb.bedType_id
 left join VocBedSubType vbst on vbst.id=wchb.bedSubType_id
 left join VocIdc10 mkb on mkb.id=wchb.idc10_id
 left join MisLpu ml on ml.id=wchb.department_id
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
    <msh:form action="/entityParentSaveGoView-stac_sslAdmission.do" defaultField="" title="Случай стационарного лечения. Поступление" editRoles="/Policy/Mis/MedCase/Stac/Ssl/Admission/Edit" createRoles="/Policy/Mis/MedCase/Stac/Ssl/Admission/Create" guid="b37b5d4a-9687-45bf-b42c-613fd9b1739d">
      <msh:hidden property="id" guid="c5a9c42c-d593-4fae-a685-44586a81938b" />
      <msh:hidden property="patient" guid="fbedab7b-1153-4f3c-b9d4-14643e32a6f7" />
      <msh:hidden property="outcome" guid="fbed4-14643e32a6f7" />
      <msh:hidden property="saveType" guid="7cb7bcb7-2cb3-4e65-92ce-2a4a2cec809f" />
      <msh:hidden property="dateFinish" guid="526ef07a-eb53-47a9-b145-02a81ede9b25" />
      <msh:hidden property="dischargeTime" guid="5d563628-c95b-40a5-a3d6-aa2d99fa0e74" />
      <msh:hidden property="provisional" guid="38fe07ac-6706-4911-a217-65edb3c85dac" />
      <msh:hidden property="result" guid="156ff02c-61dd-40b9-80f4-d88885db16f8" />
      <msh:hidden property="moveToAnotherLPU" guid="c0b69264-2081-4952-8c0a-7ea12712f14c" />
      <msh:hidden property="dischargeEpicrisis" guid="290e9247-43d1-4f8b-a7c5-3a091d9f78ce" />
      <msh:hidden property="reasonDischarge" guid="290e9247-43d1-4f8b-a7c5-3a091d9f78ce" />
      <msh:hidden property="rareCase"/>
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
     

      <msh:panel guid="6e8d827a-d32c-4a05-b4b0-5ff7eed6eedc">
        <msh:separator label="Приемное отделение" colSpan="9" guid="af11419b-1c80-4025-be30-b7e83df06024" />
        
       	<msh:row >
      		<td colspan="6"><div style="display: none;" id='medPolicyInformation' class="errorMessage"/></td>
      	</msh:row>
      	
      	       <msh:row guid="25f2a536-4fb6-4413-89db-a478145e097e">
          <msh:textField property="statCardNumber" label="Номер стат.карты" labelColSpan="1" guid="e5f3d524-cca8-4a5a-a408-196ab6b79627" />
          <msh:ifFormTypeIsView formName="stac_sslAdmissionForm" guid="a780ef5e-7a57-4583-85a5-18a88357a0a1">
            <msh:ifInRole roles="/Policy/Mis/MedCase/Stac/Ssl/Admission/ChangeStatCardNumber" guid="951762e7-12b2-4352-a7b7-2ab99be7e601">
              <td colspan="2">
                <a id="changeStatCardNumber" href="javascript:changeStatCardNumber()">Изменить номер стат. карты</a>
              </td>
            </msh:ifInRole>
          </msh:ifFormTypeIsView>
        </msh:row>
        <msh:row guid="0e91a1ca-c366-435c-8f2c-274d23d87fd3">
          <msh:textField property="dateStart" label="Дата поступления" guid="e3fd4642-a532-4510-a528-c6e766328d61" />
          <msh:textField property="entranceTime" label="время" fieldColSpan="3" guid="f94ff57c-bbf9-44f8-9e8d-f21927edbcff" />
        </msh:row>
        <msh:row guid="0e91a1ca-c366-435c-8f2c-274d23d87fd3">
          <msh:textField property="transferDate" label="Выбыт. из приемника" guid="e3fd4642-a532-4510-a528-c6e766328d61" />
          <msh:textField property="transferTime" label="время" fieldColSpan="3" guid="f94ff57c-bbf9-44f8-9e8d-f21927edbcff" />
        </msh:row>
        <msh:row guid="e101a36c-d874-4d43-9cfe-fff88ff64ffa">
          <msh:autoComplete property="lpu" label="Лечебное учреждение" vocName="lpu" fieldColSpan="3" horizontalFill="true" guid="ee4b9-2961-42be-9a05-caff23" />
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
        <msh:row guid="f2haba5-68fb-4ccc-9982-7b4480cmca147">
          <msh:autoComplete vocName="vocHospType" property="hospType" label="Тип тек. стационара" fieldColSpan="1" horizontalFill="true" guid="10h64-23b2-42c0-ba47-65p16c" />
          <msh:autoComplete vocName="vocServiceStream" property="serviceStream" label="Поток обслуживания" fieldColSpan="1" horizontalFill="true" guid="10h64-23b2-42c0-ba47-65p8g16c" />
        </msh:row>
         <msh:ifInRole roles="/Policy/Mis/Contract/MedContract/ContractGuarantee/ContractGuaranteeLetter/View">
      <msh:row>
          <msh:autoComplete  vocName="guaranteeByPatient" parentId="smo_directionForm.patient" property="guarantee" label="Гарантийное письмо" guid="58d43ea6-3555-4eaf-978e-f259920d179c" fieldColSpan="3" horizontalFill="true" />
        </msh:row>
      </msh:ifInRole>
        <msh:row guid="5d9db3cf-010f-463e-a2e6-3bbec49fa646">
          <msh:autoComplete property="pediculosis" vocName="vocPediculosis" label="Педикулез" horizontalFill="true" guid="30c4f14a-0fa4-4b5b-9c13-4ecfca6000f6" />
          <msh:autoComplete property="deniedHospitalizating" label="Причина отказа от госп." vocName="vocDeniedHospitalizating" horizontalFill="true" fieldColSpan="1" guid="f1dab596-6c7d-4cb4-848b-71b62bd6bf3a" />
        </msh:row>
        <msh:ifFormTypeIsNotView formName="stac_sslAdmissionForm" guid="2d66uyt565b-265f-499f-9116-7755ec98c043">
          <msh:row guid="fafct8638-6d3f-4cf9-bb0a-5c7fhe0b25e53">
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
        <msh:row guid="b128654b-7eff-41b3-b73f-3cb819122a44">
          <msh:checkBox property="ambulanceTreatment" label="Амбул. лечение" guid="8aa14ab3-7940-4b9d-aa14-2c3ce9fb4e7b" />
          <msh:checkBox hideLabel="false" property="emergency" viewOnlyField="false" guid="e3373f9c-ca5f-4aa2-957b-9e7a0cb6cc39" horizontalFill="false" />
        </msh:row>
        <msh:row guid="544d70a3-19bf-4793-af89-fc11a5a56837">
          <msh:checkBox property="relativeMessage" label="Сообщение родственникам" guid="21e6d68e-e0a2-4854-85e7-9344d25e3d46" />
          <msh:checkBox property="medicalAid" label="Оказана помощь в прием. отделении" guid="a76f672d-6978-4735-bea9-277cc583acae" />
        </msh:row>
        <msh:row guid="8gaf5-7144-46a4-9015-eg230a2c">
          <msh:textField property="attendant" label="Сопровождающее лицо" guid="7fvd3-3f43-42b7-8c46-ffd05c" fieldColSpan="3" horizontalFill="true" />
        </msh:row>
          <msh:row guid="8gaf5-7144-46a4-9015-eg230a2c">
              <msh:textField property="height" label="Рост (см)"  guid="7fvd3-3f43-42b7-8c46-ffd05c" fieldColSpan="3" horizontalFill="false" />
              <msh:textField property="weight" label="Вес (кг)" guid="7fvd3-3f43-42b7-8c46-ffd05c" fieldColSpan="3" horizontalFill="false" />
              <msh:textField  property="theIMT" label="ИМТ" guid="7fvd3-3f43-42b7-8c46-ffd05c" fieldColSpan="3" horizontalFill="false" />
          </msh:row>
        <msh:ifFormTypeIsNotView formName="stac_sslAdmissionForm">
        <msh:separator label="Направлен <input type='button' value='Список направлений' onclick='viewTable263narp_byPat()'///>" colSpan="6" />
        </msh:ifFormTypeIsNotView>
        <msh:ifFormTypeIsView formName="stac_sslAdmissionForm">
        <msh:separator label="Направлен" colSpan="6" />
        </msh:ifFormTypeIsView>
        
        <msh:row guid="a53d1f37-afe8-4779-9e63-0b2684e14828">
          <msh:autoComplete property="orderLpu" label="Кем направлен" vocName="mainLpu" guid="c44b474f-6dba-4ba8-9af7-56a0dca363ad" horizontalFill="true" fieldColSpan="3" />
        </msh:row>
        <msh:row guid="f2aba5-68fb-4ccc-9982-7b44a147">
          <msh:autoComplete vocName="vocHospTypeInAdmission" property="sourceHospType" label="Тип направившего ЛПУ" fieldColSpan="3" horizontalFill="true" guid="1064-23b2-42c0-ba47-65847816c" />
        </msh:row>
        <msh:row guid="544d70a3-19bf-4793-af89-fc135837">
          <msh:textField property="orderNumber" label="№ напр" guid="51e5754c-2356-4ef6-91b2-9634893cc329" />
          <msh:textField property="orderDate" label="Дата" guid="3e74c0ff-d603-4923-b207-b4ce0d665841" />
        </msh:row>
        <msh:row guid="36c67c6c-b817-4863-835d-0c37bcc96d19">
          <msh:autoComplete property="orderMkb" label="Код МКБ направителя" guid="d956d424-ffa2-4874-ae98-7a26fcc6a49d" vocName="vocIdc10" horizontalFill="true" fieldColSpan="3" />
        </msh:row>
        <msh:row guid="8036eaf5-7144-46a4-9015-e4f198230a2c">
          <msh:textField property="orderDiagnos" label="ДИАГНОЗ напр. учреждения" guid="7d5f0ad3-3f43-42b7-8c46-f2fcceead05c" fieldColSpan="3" horizontalFill="true" />
        </msh:row>
        <msh:separator label="Доставлен (только для экстренных больных)" colSpan="9" guid="f2136abf-c311-42e4-86ad-c24b7da708d5" />
        <msh:row guid="e811bd41-db0a-4275-b786-75f958759453">
          <msh:autoComplete showId="false" vocName="vocOmcFrm" hideLabel="false" property="orderType" viewOnlyField="false" guid="ff2a0045-c4fc-4efd-9bcd-f84951ac74a2" horizontalFill="true" />
          <msh:textField property="supplyOrderNumber" label="Номер наряда" guid="63f0777e-6c0f-4ea9-b389-8a620004a456" />
        </msh:row>
        <msh:row guid="f99e7379-1503-44ae-8d45-184ada064f84">
          <msh:autoComplete property="intoxication" label="Доставлен в сост. опьян.:" guid="ca12abf3-6c85-4bf8-a4f4-cbfbb8c184a9" vocName="vocIntoxication" fieldColSpan="3" horizontalFill="true" />
        </msh:row>
        <msh:row guid="cff159af-0e93-42de-b29d-b68c05e371d0">
          <msh:autoComplete property="preAdmissionDefect" label="Дефекты догосп. этапа" vocName="vocPreAdmissionDefect" guid="7d35c85f-1f39-48c1-b48c-0b8fd4da28e3" fieldColSpan="3" horizontalFill="true" />
        </msh:row>
        <msh:row guid="16d11e99-4017-4385-87c1-b2fe485895e2">
          <msh:autoComplete property="preAdmissionTime" label="Часы заболевания" guid="ddc10e76-a708-4efd-8ef3-f18ee913984f" vocName="vocPreAdmissionTime" horizontalFill="true" />
        </msh:row>
        <msh:separator label="*Госпитализация" colSpan="9" guid="4909ac97-3ad7-4eab-a657-1d103779ed47" />
        <msh:row guid="b88b81ab-1b89-4747-ac27-a865e920eb33">
          <msh:autoComplete property="department" label="Отделение" vocName="vocLpuHospOtd" horizontalFill="true" parentAutocomplete="lpu" fieldColSpan="3" />
        </msh:row>
        <msh:row guid="b88b81ab-1b89-4747-ac27-a865e920eb33">
          <msh:autoComplete property="ownerFunction" label="Деж. врач отд." vocName="workFunctionByLpu" horizontalFill="true" parentAutocomplete="department" fieldColSpan="3" />
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
        <msh:ifFormTypeIsCreate formName="stac_sslAdmissionForm">
	        <msh:row>
	        	<msh:autoComplete parentId="stac_sslAdmissionForm.patient" property="attachedPolicies" label="Прик.полис ОМС" horizontalFill="true" fieldColSpan="3" vocName="omcPolicyByPatient"/>
	        </msh:row>
	        <msh:row>
	        	<msh:autoComplete parentId="stac_sslAdmissionForm.patient" property="attachedPolicyDmc" label="Прик.полис ДМС" horizontalFill="true" fieldColSpan="3" vocName="dmcPolicyByPatient"/>
	        </msh:row>
        </msh:ifFormTypeIsCreate>
        <msh:ifFormTypeIsNotView formName="stac_sslAdmissionForm" guid="2d66565b-265f-499f-9116-7755ec98c043">
          <msh:row guid="fafc8638-6d3f-4cf9-bb0a-5c7fe0b25e53">
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
        <msh:separator label="Дополнительно" colSpan="9" guid="777a7e06-fef8-40cc-ad41-bc3ee2511aab" />
        <msh:row guid="fa25468-6d3f-4cf9-bb0a-5c7fe0b25e53">
          <msh:checkBox property="noActuality" label="Недействительность" guid="6299a6be-428f-4a09-9db5-e4c60154b605" />
        </msh:row>
        </msh:panel>
        <msh:panel>
        <msh:row>
        	<msh:label property="createDate" label="Дата создания"/>
        	<msh:label property="createTime" label="время"/>
            <msh:label property="username" label="пользователь" guid="2258d5ca-cde5-46e9-a1cc-3ffc278353fe" />
        </msh:row>
        <msh:row>
        	<msh:label property="editDate" label="Дата редак."/>
        	<msh:label property="editTime" label="время"/>
          	<msh:label property="editUsername" label="пользователь" guid="2258d5ca-cde5-46e9-a1cc-3ffc278353fe" />
        </msh:row>
        <msh:submitCancelButtonsRow guid="submitCancel" colSpan="4" labelSave="Сохранить изменения" labelCreating="Создание" labelCreate="Создать новый случай" labelSaving="Сохранение данных" />
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
          eventutil.addEventListener($('weight'), "change",function(){
              $('weight').value=parseInt($('weight').value);
              if ($('weight').value=="NaN") $('weight').value="";
              var w = parseInt($('weight').value);
              var h = parseInt($('height').value);
              var imt=(w / (0.0001 * h * h)).toFixed(2);
              $('theIMT').value=imt;
              if ($('theIMT').value=="NaN") $('theIMT').value="0.0";
          }) ;
          eventutil.addEventListener($('height'), "change",function(){
              $('height').value=parseInt($('height').value);
              if ($('height').value=="NaN") $('height').value="";
              var w = parseInt($('weight').value);
              var h = parseInt($('height').value);
              var imt=(w / (0.0001 * h * h)).toFixed(2);
              $('theIMT').value=imt;
              if ($('theIMT').value=="NaN") $('theIMT').value="0.0";
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
    <msh:ifFormTypeIsNotView formName="stac_sslAdmissionForm" guid="76f69ba0-a7b7-4cdb-8007-4de4ae2836ec">
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
		if ($('deniedHospitalizatingName')) {
            var ch = function ch() {
            
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
    <msh:ifFormTypeIsView formName="stac_sslAdmissionForm" guid="6c59f9b3-c9b2-4822-aff8-9225ca67edb1">
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

