<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://struts.apache.org/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://www.nuzmsh.ru/tags/msh" prefix="msh" %>
<%@ taglib uri="http://www.ecom-ast.ru/tags/ecom" prefix="ecom" %>
<%@ taglib uri="/WEB-INF/mis.tld" prefix="mis" %>
<tiles:insert page="/WEB-INF/tiles/mainLayout.jsp" flush="true">

  <tiles:put name="side" type="string">
    <msh:ifFormTypeIsView formName="stac_newBornSloForm" guid="e2054544-fdd1-4285-a21c-3bb9b4569efc">
      <msh:sideMenu guid="9ec15353-1f35-4c18-b99d-e2b63ecc60c9" title="СЛО по новорожденному">
        <msh:sideLink key="ALT+2" params="id" action="/entityParentEdit-stac_newBornSlo" name="Изменить" roles="/Policy/Mis/MedCase/Stac/Ssl/Slo/Edit" guid="5a1450f5-7629-4458-b5a5-e5566af6a914" />
        <msh:sideLink key="ALT+DEL" confirm="Удалить?" params="id" action="/entityParentDelete-stac_newBornSlo" name="Удалить" roles="/Policy/Mis/MedCase/Stac/Ssl/Slo/Delete" guid="7767f5b6-c131-47f4-b8a0-2604050c450f" />
      </msh:sideMenu>
      <msh:sideMenu guid="9ec15353-1f35-4c18-b99d-e2b63ecc9" title="Добавить">
        <msh:sideLink roles="/Policy/Mis/MedCase/Protocol/Create" name="Дневник специалиста" params="id" action="/entityParentPrepareCreate-smo_visitProtocol" title="Дневник специалиста" guid="11cc057f-b309-4193-9d22-199373cfd28d" />
        <msh:sideLink roles="/Policy/Mis/MedCase/Stac/Ssl/Diagnosis/Create" name="Диагноз" params="id" action="/entityParentPrepareCreate-stac_diagnosis" title="Диагноз" guid="c3e59a04-8858-4523-9370-74b16ec784e6" />
        <msh:sideLink roles="/Policy/Mis/Prescription/Prescript/Create" name="Лист назначений" params="id" action="/entityParentPrepareCreate-pres_prescriptList" title="Диагноз" guid="abd8a59e-4968-4a55-adac-c257c1e8a899" />
        <msh:sideLink roles="/Policy/Mis/MedCase/Stac/Ssl/TemperatureCurve/Create" name="Температурный лист" params="id" action="/entityParentPrepareCreate-stac_temperatureCurve" title="Добавить температурный лист" guid="df23-45a-43cc-826d-5hfd" />
        <msh:sideLink roles="/Policy/Mis/MedCase/MedService/Create" name="Услуг" params="id" action="/entityParentPrepareCreate-smo_medService" title="Добавить услугу" guid="df23-45a26d-5hfd" />

      <msh:sideLink params="id" action="/entityParentPrepareCreate-trans_blood" name="Переливание крови" title="Добавить переливание крови" guid="dc488234-9da8-4290-9e71-3b4558d27ec7" />
      <msh:sideLink roles="/Policy/Mis/MedCase/Transfusion/Other/Create" key="ALT+3" params="id" action="/entityParentPrepareCreate-trans_other" name="Переливание транс.сред, кроме крови" title="Добавить переливание кроме трансфузионных сред всех, кроме крови" guid="42b3d7fc-e998-458f-b259-0c865d5270b8" />
    	
    	<msh:sideLink roles="/Policy/Mis/MedCase/Stac/Ssl/SurOper/Create" name="Операции"  
    	params="id"  action='/entityParentPrepareCreate-stac_surOperation'  key='Alt+7' title="Операции"
    	/>

      </msh:sideMenu>
      <msh:sideMenu title="Показать" guid="c65476c8-6c6a-43c4-a70a-84f40bda76e1">
        <msh:sideLink roles="/Policy/Mis/Prescription/Prescript/View" name="Листы назначений" params="id" action="/entityParentList-pres_prescriptList" title="Показать все листы назначений СЛО" guid="7b0b69ae-3b9c-47d9-ab3c-5055fbe6fa9f" />
        <msh:sideLink roles="/Policy/Mis/MedCase/Stac/Ssl/Diagnosis/View" name="Диагнозы" params="id" action="/entityParentList-stac_diagnosis" title="Показать все диагнозы СЛО" guid="4ac8c095-3853-4150-9e4a-d01b4abc8061" />
        <msh:sideLink roles="/Policy/Mis/MedCase/Protocol/View" name="Дневников специалиста" params="id" action="/entityParentList-smo_visitProtocol" title="Показать все дневники специалиста" guid="d43123-45ca-43cc-826d-bc85" />
        <msh:sideLink name="Температурных листов" action="/entityParentList-stac_temperatureCurve" title="Показать все температурные листы" guid="df23-45ca-43cc-826d-5hf5dd" params="id" />
        <msh:sideLink roles="/Policy/Mis/MedCase/MedService/View" name="Услуг" params="id" action="/entityParentList-smo_medService" title="Показать все услуги" guid="df23-45a26d-5hfd" />
        
        <msh:sideLink roles="/Policy/Mis/MedCase/Transfusion/View" name="Переливание"     
    	params="id"  action='/entityParentList-trans_transfusion'  key='Alt+8' 
    	title='Переливание трансфузионных сред'/>
    	
    	<msh:sideLink roles="/Policy/Mis/MedCase/Stac/Ssl/SurOper/View" name="Операции"  
    	params="id"  action='/entityParentList-stac_surOperation'  key='Alt+7' title='Операции'
    	styleId="stac_surOperation"
    	/>

    <msh:sideLink roles="/Policy/Mis/Inspection/View" name="Осмотры"     
    	params="id"  action='/entityParentList-preg_inspection'  key='Alt+0' 
    	title='Медицинские осмотры'/>
      </msh:sideMenu>
      <msh:sideMenu title="Печать">
      	<msh:sideLink roles="/Policy/Mis/MedCase/Protocol/View" name="Дневников по СЛО новорожденному" action="/printProtocolsBySLO.do?medcase=${param.id}" params="id"/>
      </msh:sideMenu>
      <msh:sideMenu title="Дополнительно" guid="ad80d37d-5a0b-44e3-a4ae-3df85de3d1c3">
        <msh:sideLink key="ALT+3" params="id" action="/entityParentListRedirect-stac_newBornSlo" name="⇧Cписок СЛО по новорожденному" roles="/Policy/Mis/MedCase/Stac/Ssl/Slo/View" guid="f6a4b395-ccee-4db6-aad7-9bc15aa2f7b8" title="Перейти к списку случаев лечения в отделении по новорожденному" />
      </msh:sideMenu>
    </msh:ifFormTypeIsView>
  </tiles:put>
  <tiles:put name="body" type="string">
    <!-- 
    	  - Случай стационарного лечения в отделении по новорожденному
    	  -->
    <msh:form action="/entityParentSaveGoView-stac_newBornSlo.do" defaultField="transferDate" guid="be2c889f-ed1d-4a2b-9cda-9127e9d94885">
      <msh:hidden property="id" guid="d10f460a-e434-45a5-90f0-b0a7aed00ec6" />
      <msh:hidden property="prevMedCase" guid="710eb92b-fc3f-4390-b32df6837280" />
      <msh:hidden property="parent" guid="710eb92b-fc3f-4b44-9390-b32df6837280" />
      <msh:hidden property="patient" guid="9d908e88-e051-4d0a-8da6-3f5f4b226493" />
      <msh:hidden property="saveType" guid="bd322f07-c944-4587-a963-a09db2b93caf" />
      <msh:hidden property="lpuAndDate" guid="9cc5ff9f-b68c-423a-be34-50ebeecf4b18" />
      <msh:hidden property="lpu" guid="756525c0-3c91-41da-b2ba-27ebdbdc001b" />
      <msh:panel guid="d1cd0310-bf53-4ce1-9dd5-06388b51ec01" colsWidth="10%,10%">
        <msh:separator label="Поступление в отделение" colSpan="6" guid="d4313623-45ca-43cc-826d-bc1b66526744" />
        <msh:row guid="d6321f29-4e95-42a5-9063-96df480e55a8">
          <msh:textField property="dateStart" label="Дата поступления" viewOnlyField="true" guid="a7996448-21ee-4647-b7bb-0d8501b9c2c5" />
          <msh:textField property="entranceTime" label="время" guid="11dd2d9a-3b35-4620-be61-eaede78e8a7a" />
        </msh:row>
        <msh:row guid="f244aba5-68fb-4ccc-9982-7b4480cca147">
          <msh:autoComplete parentId="stac_newBornSloForm.lpu" vocName="vocLpuHospOtd" property="department" label="Отделение" fieldColSpan="6" horizontalFill="true" guid="109f7264-23b2-42c0-ba47-65d90747816c" size="30" />
        </msh:row>
        <msh:row guid="f2-68fb-4ccc-9982-7b4480cca147">
          <msh:autoComplete vocName="serviceStreamByDepAndDate" property="serviceStream" label="Поток обслуживания" fieldColSpan="6" horizontalFill="true" guid="109f7264-23b216c" />
        </msh:row>
        <msh:row guid="f2aba5-68fb-4ccc-9982-7b4480cmca147">
          <msh:autoComplete vocName="bedFundByDepAndStreamAndDate" property="bedFund" label="Профиль коек" fieldColSpan="6" horizontalFill="true" guid="1064-23b2-42c0-ba47-65d90747816c" size="30" />
        </msh:row>
        <msh:ifNotInRole roles="/Policy/Mis/MedCase/Stac/Ssl/ShortEnter">
        <msh:row guid="9b781235-66ad-4f9d-991b-afb9aedfb7a8">
          <%-- <msh:textField label="№палаты" property="roomNumber" guid="fff1dd1d-b7a5-4fe2-899b-3292ec9f3fad" /> --%>
          <msh:autoComplete property="roomNumber" vocName="hospitalRoomByLpu" label="№палаты" parentId="stac_newBornSloForm.department"/>
          <msh:autoComplete property="bedNumber" vocName="hospitalBedByRoom" label="№ койки" parentAutocomplete="roomNumber"/>
          <%-- <msh:autoComplete property="roomType" vocName="vocRoomType" label="Тип палаты" horizontalFill="true"/> --%>
         </msh:row>
         <msh:row>
         <%-- <msh:textField label="№ койки" property="bedNumber" guid="ed0d86e6-71b9-44f6-9c3a-213f5e8465c8" />  --%>
        </msh:row>
        </msh:ifNotInRole>
        <msh:ifInRole roles="/Policy/Mis/MedCase/Stac/Ssl/ShortEnter">
        <msh:hidden property="roomNumber"/>
        <msh:hidden property="roomType"/>
        <msh:hidden property="bedNumber"/>
        </msh:ifInRole>
         <msh:row>
           <msh:checkBox label="Провизорность" property="provisional" guid="dh88d59-3adb-4485-af94-cahb04f82b" />
        	<msh:checkBox label="Экстренно" property="emergency" guid="dhcahb04f82b" />
        </msh:row>
        <msh:row guid="1d32ce64-883b-4be9-8db1-a421709f4470">
          <msh:autoComplete vocName="workFunction" property="ownerFunction" label="Лечащий врач" fieldColSpan="6" horizontalFill="true" guid="968469ce-dd95-40f4-af14-deef6cd3e4f3" viewAction="entitySubclassView-work_workFunction.do" size="30" />
        </msh:row>
        <msh:separator label="Перевод в другое отделение" colSpan="" guid="dd7185d0-e499-4307-9e58-6ef41d83c2b0" />
        <msh:row guid="a3509d1f-9324-4997-a7c3-6ca8f12a9347">
          <msh:textField property="transferDate" label="Дата" guid="f8f5c912-00b8-4fd8-87b9-abe417212d78" />
          <msh:textField property="transferTime" label="Время" guid="c04ab410-42df-4f5b-b365-b4acf17a2616" />
        </msh:row>
        <msh:row guid="72adfc11-ef9b-47c0-8eb4-a23ee9e84ed8">
          <msh:autoComplete vocName="vocLpuOtd" property="transferDepartment" label="Отделение" fieldColSpan="6" horizontalFill="true" guid="f793944a-6afe-4c26-82f3-50532049a8bc" />
        </msh:row>
        <msh:separator label="Выписка" colSpan="" guid="a5bd9711-b033-4104-b794-0ca3ebc8b827" />
        <msh:row guid="21b4ac48-1773-410d-b85f-537680420aa4">
          <msh:textField property="dateFinish" label="Дата" guid="bb7b87a8-c542-47ef-93b6-91106abf9f19" />
          <msh:textField property="dischargeTime" label="Время" guid="a8bfc5ac-8d19-4656-a30b-bd87da1918df" />
        </msh:row>
        <msh:submitCancelButtonsRow colSpan="3" guid="6bece8ec-9b93-4faf-b729-851f1447d54f" />
      </msh:panel>
    </msh:form>
    <msh:ifFormTypeIsView formName="stac_newBornSloForm" guid="48eb9700-d07d-4115-a476-a5a5e">
      <msh:ifInRole roles="/Policy/Mis/MedCase/Protocol/View" guid="932601e0-0d99-4b63-8f44-2466f6e91c0f">
          <ecom:parentEntityListAll formName="smo_visitProtocolForm" attribute="protocols" guid="30260c-7369-4ec7-a67c-882abcf" />
      <msh:tableNotEmpty name="protocols">
        <msh:section title="Дневники специалистов" guid="1f21294-8ea0-4b66-a0f3-62713c1">
          <msh:table hideTitle="true" idField="id" name="protocols" action="entityParentView-smo_visitProtocol.do" guid="d0267-9aec-4ee0-b20a-4f26b37">
            <msh:tableColumn columnName="Дата" property="dateRegistration" guid="b85fe4-b1cb-4320-aa85-014d26" cssClass="preCell" />
            <msh:tableColumn columnName="Время" property="timeRegistration" guid="b85b1cb-4320-aa85-014d26" cssClass="preCell" />
            <msh:tableColumn columnName="Запись" property="record" guid="bd2fe4-b1cb-4320-aa85-02bd26" cssClass="preCell" />
            <msh:tableColumn columnName="Специалист" property="specialistInfo" guid="bd2fe4-b1cb-4320-aa85-02bd26" cssClass="preCell" />
          </msh:table>
        </msh:section>
        </msh:tableNotEmpty>
      </msh:ifInRole>
      <msh:ifInRole roles="/Policy/Mis/MedCase/Stac/Ssl/Diagnosis/View" guid="b0ceb3e4-a6a2-41fa-be6b-ea222196a33d">
        <ecom:parentEntityListAll formName="stac_diagnosisForm" attribute="diagnosis" guid="302c-7369-4ec7-a67c-882abcf" />
		<msh:tableNotEmpty name="diagnosis">
        <msh:section title="Диагнозы" guid="1f214-8ea0-4b66-a0f3-62713c1">
          <msh:table name="diagnosis" action="entityParentView-stac_diagnosis.do" idField="id" guid="b621e361-1e0b-4ebd-9f58-b7d916">
            <msh:tableColumn columnName="Дата установления" property="establishDate" guid="0694f6a7-ed40-4ebf-a274-1efd6901cfe4" />
            <msh:tableColumn columnName="Тип регистрации" property="registrationTypeInfo" guid="6682eeef-105f-43a0-be61-30a865f27972" />
            <msh:tableColumn columnName="Наименование" property="name" guid="6682eeef-105f-43a0-be61-30a865f27972" />
            <msh:tableColumn columnName="Код МКБ" property="idc10Text" guid="f34e1b12-3392-4978-b31f-5e54ff2e45bd" />
            <msh:tableColumn columnName="Специалист" property="workerInfo" guid="f31b12-3392-4978-b31f-5e54ff2e45" />
          </msh:table>
        </msh:section>
        </msh:tableNotEmpty>
      </msh:ifInRole>
      <msh:ifInRole roles="/Policy/Mis/MedCase/Stac/Ssl/SurOper/View">
      </msh:ifInRole>
      
      <msh:ifInRole roles="/Policy/Mis/MedCase/Transfusion/View">
      	<ecom:parentEntityListAll attribute="transfusions" formName="trans_transfusionForm"/>
      	<msh:tableNotEmpty name="transfusions">
      	<msh:section title="Переливание">
      		<msh:table name="transfusions" action="entitySubclassView-trans_transfusion.do" idField="id">
		      <msh:tableColumn columnName="Номер в журнале" property="journalNumber" guid="ed7e6ec7-524e-4b87-8b2c-5a722792a123" />
		      <msh:tableColumn columnName="Трансфузионная среда" property="information" guid="c4b30e10-9ca0-42b1-94fb-88cf0f7afa2e" />
		      <msh:tableColumn columnName="Дата приготовления" property="preparationDate" guid="1ef2e314-6eb6-4c85-be47-ca392566d371" />
		      <msh:tableColumn columnName="Изготовитель" property="preparator" guid="dk29-5653-4920-bb78-168ha34" />
		      <msh:tableColumn columnName="Дата начала" property="startDate" guid="2976f5c7-3844-4ae2-be91-2a395cae0f1f" />
		      <msh:tableColumn columnName="Доза" property="doze" guid="d9642df9-5653-4920-bb78-1622cbeefa34" />
		      <msh:tableColumn columnName="Исполнитель" property="executorInfo" guid="8e832f90-6905-44cf-952e-76495689c35b" />
      		</msh:table>
      		</msh:section>
      	</msh:tableNotEmpty>
      </msh:ifInRole>
    </msh:ifFormTypeIsView>
  </tiles:put>
  <tiles:put name="title" type="string">
    <ecom:titleTrail mainMenu="Patient" beginForm="stac_newBornSloForm" guid="fb43e71c-1ba9-4e61-8632-a6f4a72b461c" />
  </tiles:put>
  <tiles:put name="javascript" type="string">
        <script type="text/javascript">//var theBedFund = $('bedFund').value;
      	function goDischarge() {
      		window.location = 'entityParentEdit-stac_sslDischarge.do?id='+$('parent').value+"&tmp="+Math.random() ;
      	}
      	</script>
  
        <msh:ifFormTypeIsNotView formName="stac_newBornSloForm" guid="518fe547-aed9-be2229f04ba3">
      <script type="text/javascript">//var theBedFund = $('bedFund').value;
      if (+$('prevMedCase').value==0) {
    	  $('serviceStreamName').select() ;
    	  $('serviceStreamName').focus() ;
      }
      var lpuDate ;
       
  		try {
	    if (clinicalMkbAutocomplete) clinicalMkbAutocomplete.addOnChangeCallback(function() {
	      	 	setDiagnosisText('clinicalMkb','clinicalDiagnos');
	    });} catch(e) {}
  		try {
	    if (concomitantMkbAutocomplete) concomitantMkbAutocomplete.addOnChangeCallback(function() {
	      	 	setDiagnosisText('concomitantMkb','concomitantDiagnos');
	    });} catch(e) {}
  		function setDiagnosisText(aFieldMkb,aFieldText) {
  			var val = $(aFieldMkb+'Name').value ;
  			var ind = val.indexOf(' ') ;
  			//alert(ind+' '+val)
  			if (ind!=-1) {
  				if ($(aFieldText).value=="") $(aFieldText).value=val.substring(ind+1) ;
  			}
  		}
      
      
      
      
      	function goDischarge() {
      		window.location = 'entityParentEdit-stac_sslDischarge.do?id='+$('parent').value+"&tmp="+Math.random() ;
      	}
      	$('lpuAndDate').value = (+$('department').value) +"#"+(+$('serviceStream').value)+"#" +$('dateStart').value;
      	lpuDate = (+$('department').value) +"#"+$('dateStart').value  ;
      	bedFundAutocomplete.setParentId($('lpuAndDate').value) ;
      	serviceStreamAutocomplete.setParentId(lpuDate) ;
      	//bedFundAutocomplete.setVocId(theBedFund);
      	//alert(departmentAutocomplete) ;
      	//transferDepartmentAutocomplete.setParentId($('lpu').value) ;
      	
      	if (bedFundAutocomplete) bedFundAutocomplete.setParentId($('lpuAndDate').value) ;
      	serviceStreamAutocomplete.addOnChangeCallback(function() {
      	 	updateLpuAndDate() ;
      	 	updateBedFund() ;
      	 });
      	updateLpuAndDate() ;
      	 function updateLpuAndDate() {
      		 //var serviceStream=+$('serviceStream').value ;
           	$('lpuAndDate').value = (+$('department').value) +"#"+(+$('serviceStream').value)+"#" +$('dateStart').value;
          	lpuDate = (+$('department').value) +"#"+$('dateStart').value  ;
      	 	//alert("lpuAndDate"+$('lpuAndDate').value) ;
      	 	lpuDate = (+$('department').value) +"#"+$('dateStart').value ;
      	 	bedFundAutocomplete.setParentId($('lpuAndDate').value) ;
      	 	serviceStreamAutocomplete.setParentId(lpuDate) ;
      	 	//var id = $('bedFund').value ;
      	 	//bedFundAutocomplete.setVocId(id);
      	 }
      	 function updateBedFund() {
      		HospitalMedCaseService.getDefaultBedFundBySlo($('parent').value
      				, $('department').value, $('serviceStream').value
      				, $('dateStart').value,{
      			callback: function(aResult) {
      				var res = aResult.split('#') ;

      				if (+res[0]!=0) {
      					$('bedFund').value = res[0] ; 
      					$('bedFundName').value = res[1] ; 
      				} else {
      		      	 	$('bedFund').value='0';
      		      	 	$('bedFundName').value='';
      				}
      			}
      		}) ;

      	 }
      	 </script>
      	 	
      	 <msh:ifNotInRole roles="/Policy/Mis/MedCase/Stac/Ssl/OwnerFunction">
      	 	<script type="text/javascript">
      	 	try {
          		//departmentAutocomplete.setParentId($('lpu').value) ;
    	      	departmentAutocomplete.addOnChangeCallback(function() {
    	      		try {
    	      			roomNumberAutocomplete.setParentId($('department').value) ;
    	      			$('roomNumber').value='0' ;
    	      			$('roomNumberName').value='' ;
    	      		} catch(e) {}
    	      		
    	      		updateLpuAndDate() ;
    	      	 	ownerFunctionAutocomplete.setParentId($('department').value) ;
    	      		updateBedFund() ;
    	      	 });
          	} catch (e) {
          	}
      	 	</script>
      	 </msh:ifNotInRole>
      	 <msh:ifInRole roles="/Policy/Mis/MedCase/Stac/Ssl/OwnerFunction">
      	 	<script type="text/javascript">
      	 	try {
          		//departmentAutocomplete.setParentId($('lpu').value) ;
    	      	departmentAutocomplete.addOnChangeCallback(function() {
    	      		updateLpuAndDate() ;
    	      		roomNumberAutocomplete.setParentId($('department').value) ;
    	      		$('roomNumber').value='0' ;
    	      		$('roomNumberName').value='' ;
    	      	 	ownerFunctionAutocomplete.setParentId($('department').value) ;
    	      		HospitalMedCaseService.getDefaultInfoBySlo($('parent').value
    	      				, $('department').value, $('serviceStream').value
    	      				, $('dateStart').value,{
    	      			callback: function(aResult) {
    	      				var res = aResult.split('#') ;
    	      				if (+res[0]!=0) {
    	      					$('ownerFunction').value = res[0] ; 
    	      					$('ownerFunctionName').value = res[1] ; 
    	      				} else {
    	      		      	 	$('ownerFunction').value='0';
    	      		      	 	$('ownerFunctionName').value='';
    	      				}
    	      				if (+res[2]!=0) {
    	      					$('bedFund').value = res[2] ; 
    	      					$('bedFundName').value = res[3] ; 
    	      				} else {
    	      		      	 	$('bedFund').value='0';
    	      		      	 	$('bedFundName').value='';
    	      				}
    	      			}
    	      		})
    	      	 });
          	} catch (e) {
          	}
      	 	</script>
      	 </msh:ifInRole>
    </msh:ifFormTypeIsNotView>
  </tiles:put>
</tiles:insert>

