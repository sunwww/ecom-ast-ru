<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://struts.apache.org/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://www.nuzmsh.ru/tags/msh" prefix="msh" %>
<%@ taglib uri="http://www.ecom-ast.ru/tags/ecom" prefix="ecom" %>
<%@ taglib tagdir="/WEB-INF/tags" prefix="tags" %>
<%@ taglib uri="/WEB-INF/mis.tld" prefix="mis" %>

<tiles:insert page="/WEB-INF/tiles/mainLayout.jsp" flush="true">

  <tiles:put name="title" type="string">
    <ecom:titleTrail mainMenu="Patient" beginForm="stac_sslForm" guid="638ddd30-b48e-4058-b3ad-866c0c70ee1f" />
  </tiles:put>
  <tiles:put name="side" type="string">
    <tags:stac_hospitalMenu currentAction="stac_ssl" />
  </tiles:put>
  <tiles:put name="body" type="string">
    <!-- 
    	  - Случай стационарного лечения (вся информация)
    	  -->
    <msh:form action="/entityParentSaveGoView-stac_ssl.do" defaultField="dateStart" guid="b37b5d4a-9687-45bf-b42c-613fd9b1739d" title="Случай стационарного лечения. Поступление" editRoles="/Policy/Mis/MedCase/Stac/Ssl/Edit" createRoles="/Police/Mis/MedCase/Stac/Ssl/Create">
      <msh:hidden property="id" guid="c5a9c42c-d593-4fae-a685-44586a81938b" />
      <msh:hidden property="patient" guid="fbedab7b-1153-4f3c-b9d4-14643e32a6f7" />
      <msh:hidden property="saveType" guid="7cb7bcb7-2cb3-4e65-92ce-2a4a2cec809f" />
      
      <msh:ifNotInRole roles="/Policy/Mis/MedCase/IsPsychiatry">
        <msh:hidden property="compulsoryTreatment"/>
        <msh:hidden property="incapacity"/>
        <msh:hidden property="lawCourtDesicionDate"/>
        <msh:hidden property="psychReason"/>      
      </msh:ifNotInRole>
      <msh:panel guid="6e8d827a-d32c-4a05-b4b0-5ff7eed6eedc">
        <msh:separator label="<a href='entityParentEdit-stac_sslAdmission.do?id=${param.id}'>Приемное отделение</a>" colSpan="8" guid="af11419b-1c80-4025-be30-b7e83df06024" />
       	<msh:row >
      		<td colspan="6"><div style="display: none;" id='medPolicyInformation' class="errorMessage"/></td>
      	</msh:row>        <msh:row guid="ee27ecec-c7ca-440c-abc1-1eb97022e39e">
          <msh:autoComplete showId="false" vocName="lpu" hideLabel="false" property="lpu" viewOnlyField="false" label="Лечебное учреждение" guid="b70bc036-80bd-4236-b766-428d1403d364" fieldColSpan="5" horizontalFill="true" />
        </msh:row>
        <msh:row guid="25f2a536-4fb6-4413-89db-a478145e097e">
          <msh:textField property="statCardNumber" label="Номер стат.карты" guid="e5f3d524-cca8-4a5a-a408-196ab6b79627" horizontalFill="true" viewOnlyField="true" />
        </msh:row>
        <msh:row guid="0e91a1ca-c366-435c-8f2c-274d23d87fd3">
          <msh:textField property="dateStart" label="Дата поступления" guid="e3fd4642-a532-4510-a528-c6e766328d61" />
          <msh:textField property="entranceTime" label="время" guid="f94ff57c-bbf9-44f8-9e8d-f21927edbcff" fieldColSpan="2" />
        </msh:row>
        <msh:row guid="a53d1f37-afe8-4779-9e63-0b2684e14828">
          <msh:autoComplete property="orderLpu" label="Кем направлен" vocName="mainLpu" guid="c44b474f-6dba-4ba8-9af7-56a0dca363ad" horizontalFill="true" />
          <msh:textField property="orderNumber" label="№ напр" guid="51e5754c-2356-4ef6-91b2-9634893cc329" />
          <msh:textField property="orderDate" label="Дата" guid="3e74c0ff-d603-4923-b207-b4ce0d665841" />
        </msh:row>
        <msh:row guid="8036eaf5-7144-46a4-9015-e4f198230a2c">
          <msh:textField property="orderDiagnos" label="ДИАГНОЗ направившего учреждения" fieldColSpan="6" horizontalFill="true" guid="7d5f0ad3-3f43-42b7-8c46-f2fcceead05c" />
        </msh:row>
        <msh:row guid="36c67c6c-b817-4863-835d-0c37bcc96d19">
          <msh:autoComplete property="orderMkb" label="Код МКБ направителя" horizontalFill="true" guid="d956d424-ffa2-4874-ae98-7a26fcc6a49d" vocName="vocIdc10" fieldColSpan="6" />
        </msh:row>
        <msh:row guid="e811bd41-db0a-4275-b786-75f958759453">
          <msh:autoComplete showId="false" vocName="vocOmcFrm" hideLabel="false" property="orderType" viewOnlyField="false" guid="ff2a0045-c4fc-4efd-9bcd-f84951ac74a2" horizontalFill="true" />
          <msh:textField property="supplyOrderNumber" label="Номер наряда" guid="63f0777e-6c0f-4ea9-b389-8a620004a456" />
        </msh:row>
        <msh:row guid="f99e7379-1503-44ae-8d45-184ada064f84">
          <msh:autoComplete property="intoxication" label="Состояние опьянения" guid="ca12abf3-6c85-4bf8-a4f4-cbfbb8c184a9" vocName="vocIntoxication" horizontalFill="true" />
          <msh:autoComplete property="deniedHospitalizating" label="Отказа от госп." vocName="vocDeniedHospitalizating" guid="f1dab596-6c7d-4cb4-848b-71b62bd6bf3a" fieldColSpan="2" horizontalFill="true" />
        </msh:row>
        <msh:row guid="cff159af-0e93-42de-b29d-b68c05e371d0">
          <msh:autoComplete property="preAdmissionDefect" label="Дефекты догоспитального этапа" vocName="vocPreAdmissionDefect" guid="7d35c85f-1f39-48c1-b48c-0b8fd4da28e3" fieldColSpan="3" horizontalFill="true" />
        </msh:row>
        <msh:row guid="5d9db3cf-010f-463e-a2e6-3bbec49fa646">
          <msh:autoComplete vocName="vocServiceStream" property="serviceStream" label="Поток обслуживания" fieldColSpan="1" horizontalFill="true" guid="10h64-23b2-42c0-ba47-65p8g16c" />
          <msh:checkBox property="ambulanceTreatment" label="Амбулаторное лечение" guid="8aa14ab3-7940-4b9d-aa14-2c3ce9fb4e7b" />
        </msh:row>
        <msh:row guid="194b2a92-9b7f-485e-b0f4-93092bf1e1c4">
          <msh:textField property="entranceDiagnos" label="ДИАГНОЗ приемного отделения" guid="cf405118-1d6c-4bde-81ee-57eb04dbb879" fieldColSpan="6" horizontalFill="true" />
        </msh:row>
        <msh:row guid="22259fc2-76df-4244-88fa-4a610e76ce31">
          <msh:autoComplete property="entranceMkb" label="Код МКБ приемного отделения" horizontalFill="true" guid="064ba98b-1b09-454a-8c1b-348aed0b0b01" vocName="vocIdc10" fieldColSpan="6" />
        </msh:row>
        <msh:row guid="16d11e99-4017-4385-87c1-b2fe485895e2">
          <msh:checkBox property="emergency" label="Экстренное поступление" guid="68f70746-42df-4d8f-a575-74bf4b4cc1ae"  />
          <msh:autoComplete property="preAdmissionTime" label="Часы заболевания" guid="ddc10e76-a708-4efd-8ef3-f18ee913984f" horizontalFill="true" vocName="vocPreAdmissionTime" fieldColSpan="2" />
        </msh:row>
        <msh:row>
        	<msh:autoComplete horizontalFill="true" property="pediculosis" vocName="vocPediculosis" label="Педикулез" fieldColSpan="3"/> 
        </msh:row>
        <msh:row guid="b88b81ab-1b89-4747-ac27-a865e920eb33">
          <msh:autoComplete property="department" label="Отделение" guid="bf59f5d5-2843-4abc-bf23-cbbbda89a67e" vocName="vocLpuOtd" fieldColSpan="5" horizontalFill="true" parentAutocomplete="lpu" />
        </msh:row>
        <msh:ifInRole roles="/Policy/Mis/MedCase/IsPsychiatry">
 	        <msh:row>
		        <msh:autoComplete label="Причина госпитализации в психиатрический стационар" vocName="vocPsychHospitalReason" property="psychReason" labelColSpan="3"/>
	        </msh:row>
	        <msh:row>
	        	<msh:checkBox property="compulsoryTreatment" label="Принудительное лечение"/>
	        </msh:row>
	        <msh:row>
	        	<msh:checkBox property="incapacity" label="Недееспособный (статья 29)"/>
	        	<msh:textField property="lawCourtDesicionDate" label="Дата решения суда"/>
	        </msh:row>
        </msh:ifInRole>
        <msh:row guid="bg81ab-1b89-4747-ac27-ag0eb33">
          <msh:autoComplete property="bedType" label="Профиль коек" guid="b57bb12a-0622-49bf-9905-11728fd8ecdb" horizontalFill="true" vocName="vocBedType" fieldColSpan="6" />
        </msh:row>
        <msh:row guid="544d70a3-19bf-4793-af89-fc11a5a56837">
          <msh:checkBox property="relativeMessage" label="Сообщение родственникам" guid="21e6d68e-e0a2-4854-85e7-9344d25e3d46" />
          <msh:checkBox property="medicalAid" label="Оказана помощь в приемном отделении" guid="a76f672d-6978-4735-bea9-277cc583acae" fieldColSpan="3" />
        </msh:row>
        <msh:row guid="8gaf5-7144-46a4-9015-eg230a2c">
          <msh:textField property="attendant" label="Сопровождающее лицо" guid="7fvd3-3f43-42b7-8c46-ffd05c" fieldColSpan="5" horizontalFill="true" />
        </msh:row>
        <msh:row guid="e101a36c-d874-4d43-9cfe-fff88ff64ffa">
          <msh:autoComplete property="trauma" label="Травма" guid="ee46d9b9-2961-42be-9a05-c015f4caff23" vocName="vocTraumaType" fieldColSpan="5" horizontalFill="true" />
        </msh:row>
        <msh:row guid="f0f8aa80-2ed5-460e-88b7-a86906e249a3" />
        <msh:row guid="20c4aa51-33db-4141-9de9-4c5060ee9049">
          <msh:checkBox property="noActuality" label="Недействительность" guid="6299a6be-428f-4a09-9db5-e4c60154b605" />
        <msh:label property="username" label="Оператор" guid="2258d5ca-cde5-46e9-a1cc-3ffc278353fe" />
        </msh:row>
        <msh:separator colSpan="8" label="<a href='entityParentEdit-stac_sslDischarge.do?id=${param.id}'>Выписка</a>" guid="597ac93d-a5d0-4b08-a6b1-79efee0f497a" />
        <msh:row guid="ef812a6e-c7ab-465f-8a63-25ae169ed2b2">
          <msh:autoComplete label="Результат госпитализации" property="result" horizontalFill="true" guid="63d091a8-90b9-479f-8aef-0064a789fade" vocName="vocHospitalizationResult" />
          <msh:autoComplete label="Исход" property="outcome" guid="117c2e24-87b0-4fb0-842e-5411b62b1d3e" vocName="vocHospitalizationOutcome" horizontalFill="true" />
        </msh:row>
        <msh:row guid="3009274e-f253-4805-baeb-0ab4ac5ffca8">
          <msh:textField label="Дата выписки" property="dateFinish" guid="430fa31a-5126-4628-8617-4ae67b4829a3" />
          <msh:textField label="Время выписки" property="dischargeTime" guid="1bee7682-f5a4-40f8-8e10-b6b6500ec0f4" />
        </msh:row>
        <msh:row guid="b2c54e43-3ae2-4716-af12-e32a7ac4a115">
          <msh:autoComplete label="Перевод в др ЛПУ" property="moveToAnotherLPU" guid="8c90d4e3-6351-405e-a5b0-0ea5cf61db87" fieldColSpan="5" horizontalFill="true" vocName="lpu" />
        </msh:row>
        <msh:row guid="efdbb0a9-eddc-4841-9985-5edf61220623">
          <msh:checkBox label="Провизорность" property="provisional" guid="d8588d59-3adb-4485-af94-cadecb04f82b" />
          <msh:checkBox property="rareCase" label="Редкий случай" guid="6299a6be-428f-4a095" />
        </msh:row>
        <mis:ifPatientIsWoman classByObject="Patient" idObject="stac_sslForm.patient" roles="/Policy/Mis/Pregnancy/History/View">
        <msh:separator label="Беременность" colSpan="9"/>
        <msh:row>
        	<msh:autoComplete viewAction="entityParentView-preg_pregnancy.do" property="pregnancy" label="Беременность" fieldColSpan="3" parentId="stac_sslAdmissionForm.patient" vocName="pregnancyByPatient" horizontalFill="true"/>
        </msh:row>
        </mis:ifPatientIsWoman>
        <msh:row guid="494efe58-5b3c-44b7-8148-dd3a32922082">
          <msh:separator colSpan="6" label="RW" guid="8ca2fac1-9f38-4cd2-9e7d-8e081222815d" />
        </msh:row>
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
    <tags:stac_infoBySls form="stac_sslForm"/>
  </tiles:put>
  <tiles:put name="javascript" type="string">
  	<script type='text/javascript' src='./dwr/interface/HospitalMedCaseService.js'></script>
    <script type="text/javascript" src="./dwr/interface/PatientService.js"></script>

  </tiles:put>
</tiles:insert>

