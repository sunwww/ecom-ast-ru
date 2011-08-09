<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://struts.apache.org/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://www.nuzmsh.ru/tags/msh" prefix="msh" %>
<%@ taglib uri="http://www.ecom-ast.ru/tags/ecom" prefix="ecom" %>

<tiles:insert page="/WEB-INF/tiles/mainLayout.jsp" flush="true">

  <tiles:put name="body" type="string">
    <!-- 
    	  - Проба
    	  -->
    <msh:form guid="formHello" action="/entitySaveGoView-smo_visit.do" defaultField="dateStart">
      <msh:hidden guid="hiddenId" property="id" />
      <msh:hidden guid="hiddenSaveType" property="saveType" />
      <msh:hidden guid="hiddenParent" property="parent" />
      <msh:hidden property="isDiaryCreate"/>
      <msh:hidden property="isPrint"/>
      <msh:hidden property="isDiagnosisCreate"/>
            <msh:hidden property="patient" guid="ef57d35d-e9a0-48ba-a00c-b77676505ab2" />
      <msh:panel guid="panel" colsWidth="20%, 10%, 10%">
        <msh:separator colSpan="4" label="Направлен" guid="86dbd4c5-cfa1-4af1-b250-fabe97b77971" />
        <msh:row guid="fa7ff4e9-4b3d-4402-b046-86283cf7938e">
          <msh:autoComplete viewAction="entityParentView-mis_lpu.do" vocName="mainLpu" property="orderLpu" label="Внешний направитель" guid="cbab0829-c896-4b74-9a68-c9f95676cc3b" horizontalFill="true" fieldColSpan="3" viewOnlyField="true" />
        </msh:row>
        <msh:row guid="b5f456eb-b971-441e-9a90-5194a8019c07">
          <msh:autoComplete viewAction="entitySubclassView-work_workFunction.do" vocName="workFunctionByDirect" property="workFunctionPlan" label="Куда" guid="377c0e96-6812-477d-b44f-d640d659b7b6" fieldColSpan="3" size="50" viewOnlyField="true" />
        </msh:row>
        <msh:row guid="row1">
          <msh:autoComplete vocName="vocWorkCalendarDayByWorkFunction" property="datePlan" label="Направлен на дату" guid="d7f4bef5-0f84-4d3c-b7d9-b7c7c5d51907" horizontalFill="true" parentAutocomplete="workFunctionPlan" viewOnlyField="true" />
          <msh:autoComplete vocName="vocWorkCalendarTimeWorkCalendarDay" property="timePlan" label="Время" guid="1d6b9712-62cc-4c67-a2d8-77bfef298ff3" parentAutocomplete="datePlan" viewOnlyField="true" />
        </msh:row>
        <msh:row guid="1f82-f4e4-4051-ae36-9bfded6">
          <msh:autoComplete viewAction="entitySubclassView-work_workFunction.do" vocName="workFunction" property="orderWorkFunction" label="Функция направителя" guid="01d5-ba7e-45da-a82a-9cdcd2" fieldColSpan="3" horizontalFill="true" viewOnlyField="true" size="50" />
        </msh:row>
        <msh:row guid="213e017e-0fd0-4027-a84b-96d11818c390">
          <msh:autoComplete viewAction="entityParentView-smo_spo.do" property="parent" label="СПО" guid="1daf90d8-052b-4776-809c-aedeeac116be" fieldColSpan="3" horizontalFill="true" vocName="vocOpenedSpoByPatient" parentId="smo_visitForm.patient" />
        </msh:row>
        <msh:row guid="8171a822-87ac-4a70-9c65-dd2234890dad">
          <msh:autoComplete showId="false" vocName="vocServiceStream" property="serviceStream" label="Поток" guid="d23cec11-2213-41d7-8877-73235062a656" horizontalFill="true" />
          <msh:autoComplete showId="false" vocName="vocWorkPlaceType" property="workPlaceType" label="Место" guid="7f2d4e25-c863-439c-99eb-4f82a6159067" horizontalFill="true" />
        </msh:row>
        <msh:row>
        	<msh:checkBox label="Неотложная помощь" property="emergency" fieldColSpan="3"/>
        </msh:row>
        <msh:separator colSpan="4" label="Прием" guid="86dbd4c5-cfa1-4af1-b250-fabe97b77971y" />
        <msh:row>
        	<msh:autoComplete property="kinsman" label="Представитель" viewAction="entityParentView-mis_kinsman.do" 
        	parentId="smo_visitForm.patient" vocName="kinsmanBySMO" horizontalFill="true" fieldColSpan="3"/>
        </msh:row>
        <msh:row guid="bb138544-81d9-4339-b3a7-cab980708336">
          <msh:textField property="dateStart" label="Дата приема" guid="b2d29f22-2b89-4b43-a6af-ef7c8b8c5fb3" />
          <msh:textField property="timeExecute" label="Время" guid="8d583c3f-dda1-43a9-8417-5a2d43a6cd40" />
        </msh:row>
        <msh:row guid="2c4a87f8-7053-4539-b172-c551603d828e">
          <msh:autoComplete vocName="vocReason" property="visitReason" viewOnlyField="false" label="Цель визита" guid="7250a2c7-21c3-4633-95ed-5f9fd37d05a3" fieldColSpan="3" horizontalFill="true" />
        </msh:row>
        <msh:row guid="84300380-adbd-4c7f-b3ba-65d5fe0fbfe6">
          <msh:autoComplete showId="false" vocName="vocVisitResult" property="visitResult" viewOnlyField="false" label="Результат визита" guid="77a0dc57-91e5-45a8-b12e-0cdebc6475bb" fieldColSpan="1" horizontalFill="true" />
          <msh:textField property="nextVisitDate" label="След.дата посещен. по плану"/>
        </msh:row>
        <msh:row guid="8g80-adbd-4c7f-b3ba-6ffe6">
          <msh:autoComplete vocName="vocDispanseryRegistration" property="dispRegistration" label="Диспансерный учет" guid="77g57-91e5-45a8-b12e-0r6475bb" fieldColSpan="3" horizontalFill="true" />
        </msh:row>
        <msh:row guid="18e3d5aa-abff-40e7-ad50-bcc01ee998c1">
	        <msh:textField property="uet" label="Усл.един.трудоем."/>
          <msh:textField property="privilegeRecipeAmount" label="Количество выписанных льготных рецептов" guid="d677be2b-3347-4379-a77e-04d06580ba29" />
        </msh:row>
        <msh:row guid="3738a973-a1d3-460e-ab4a-c9ef551a9951">
          <msh:autoComplete viewAction="entitySubclassView-work_workFunction.do" vocName="workFunction" property="workFunctionExecute" label="Функция" guid="010e3a75-ba7e-45da-a82a-9c618a0ffcd2" fieldColSpan="3" horizontalFill="true" viewOnlyField="true" />
        </msh:row>
        <msh:row guid="0e759382-d46b-413a-b9c2-f142b5476d4d">
          <msh:autoComplete vocName="vocWorker" property="startWorker" label="Исполнитель" guid="fcfa2efc-593e-4fcf-b6be-cc28d9aec89e" fieldColSpan="3" horizontalFill="true" viewOnlyField="true" viewAction="entityView-mis_worker.do" />
        </msh:row>
        <msh:row>
        	<msh:checkBox property="isPrintInfo" label="Распечатан?" viewOnlyField="true"/>
			<msh:checkBox property="noActuality" viewOnlyField="false" label="Недействительность визита" guid="6573be39-9a16-4a7c-bdef-5ca915d669c2" horizontalFill="false" fieldColSpan="1" labelColSpan="1" />        
		</msh:row>
        
        <msh:submitCancelButtonsRow guid="submitCancel" colSpan="8" labelSave="Принять пациента" labelSaving="Принять пациента..." />
      </msh:panel>
    </msh:form>
    <msh:ifFormTypeIsView guid="ifFormTypeIsView1212" formName="smo_visitForm">
      <msh:ifInRole roles="/Policy/Mis/MedCase/Diagnosis/View" guid="984f4783-f982-4aea-9652-0f5567c932d6">
        <msh:section guid="sectionChilds" title="Диагнозы">
          <ecom:parentEntityListAll guid="parentEntityListChilds" formName="smo_diagnosisForm" attribute="childs" />
          <msh:table guid="tableChilds" name="childs" action="entityParentView-smo_diagnosis.do" idField="id">
            <msh:tableColumn columnName="Дата установления" property="establishDate" guid="c975f4df-68b1-42b9-8ba7-44e4b82b0144" />
            <msh:tableColumn columnName="Наименование" property="name" guid="a744754f-5212-4807-910f-e4b252aec108" />
            <msh:tableColumn columnName="Приоритет" property="priorityDiagnosisText" guid="bf4cb2b2-eb35-4e8f-b8cb-4ccccb06d5ac" />
            <msh:tableColumn columnName="Код МКБ" property="idc10Text" guid="680ae408-52a8-4743-bb60-a129db69a544" />
          </msh:table>
        </msh:section>
      </msh:ifInRole>
      <msh:ifInRole roles="/Policy/Mis/MedCase/Protocol/View" guid="141a017a-569a-4fa2-9a0e-37093a8f69da">
        <msh:section title="Заключение" guid="1fbe1294-8ea0-4b66-a0f3-6c99bcea13c1">
          <ecom:parentEntityListAll formName="smo_visitProtocolForm" attribute="protocols" guid="307a660c-7369-4ec7-a67c-888f8c6aabcf" />
          <msh:table hideTitle="false" disableKeySupport="false" idField="id" name="protocols" action="entityParentView-smo_visitProtocol.do" disabledGoFirst="false" disabledGoLast="false" guid="d0e60067-9aec-4ee0-b20a-4f4b5aea9b37">
            <msh:tableColumn columnName="Дата" property="dateRegistration" guid="c16dd9e1-4534-44db-8b0a-972e2c67dd87" />
            <pre>
            <msh:tableColumn columnName="Запись" identificator="false" property="record" guid="bd9f7fe4-b1cb-4320-aa85-03952b4abd26" cssClass="preCell" />
            </pre>
            <msh:tableColumn columnName="Специалист" property="specialistInfo" guid="96c6570b-360d-46a5-9fc5-2f9c63ad1912" />
          </msh:table>
        </msh:section>
      </msh:ifInRole>
      <msh:ifInRole roles="/Policy/Mis/MedCase/PrescriptionBlank/View" guid="5a2a2f8d-9bd8-4443-962c-1bbd5aa6caa9">
        <msh:section title="Рецептурные бланки" guid="4d7584a1-8f31-468c-9185-b3cb98e8368e">
          <ecom:parentEntityListAll formName="smo_visitPrescriptionBlankForm" attribute="prescriptionBlanks" guid="c9756299-7963-4979-bdc3-a3f1290e279c" />
          <msh:table name="prescriptionBlanks" action="entityParentView-smo_visitPrescriptionBlank.do" idField="id" guid="e52580a8-d07a-47b8-b2a4-b875333e687f">
            <msh:tableColumn columnName="Дата выдачи" property="writingOutDate" guid="5a18547c-ed9d-48df-b448-253d004c7562" />
            <msh:tableColumn columnName="Серия" property="series" guid="56eb20e4-e7cc-4832-8fce-a69798817ba8" />
            <msh:tableColumn columnName="Номер" property="number" guid="f3a255cd-dda5-4a44-a782-cf4480680a4a" />
          </msh:table>
        </msh:section>
      </msh:ifInRole>
    </msh:ifFormTypeIsView>
  </tiles:put>
  <tiles:put name="title" type="string">
    <ecom:titleTrail guid="titleTrail-123" mainMenu="Patient" beginForm="smo_visitForm" />
  </tiles:put>
  <tiles:put name="side" type="string">
    <msh:ifFormTypeIsView guid="ifFormTypeIsView" formName="smo_visitForm">
      <msh:sideMenu guid="sideMenu-123" title="Визит">
        <msh:sideLink key="ALT+0" action="/js-smo_visit-findPolyAdmissions" name="Рабочий календарь"
        	roles="/Policy/Mis/MedCase/Visit/View" styleId="selected_menu"
        />
        <msh:sideLink guid="sideLinkEdit" key="ALT+2" params="id" action="/entityEdit-smo_visit" name="Принять пациента" roles="/Policy/Mis/MedCase/Visit/Edit" />
        <msh:sideLink confirm="Вы точно хотите оформить не явку на прием" key="ALT+3" params="id" action="/js-smo_visit-noPatient" name="Оформить не явку на прием" title="Оформить не явку на прием" roles="/Policy/Mis/MedCase/Visit/Edit"/>
        <msh:sideLink params="id" action="/js-smo_visit-closeSpo" name="Закрыть СПО" title="Закрыть СПО" confirm="Закрыть СПО?" guid="d84659f7-7ea9-4400-a11c-c83e7d5c578d" key="ALT+4" roles="/Policy/Mis/MedCase/Spo/Close" />
        
        <msh:sideLink guid="sideLinkDelete" key="ALT+DEL" confirm="Удалить?" params="id" action="/entityParentDeleteGoParentView-smo_visit" name="Удалить" roles="/Policy/Mis/MedCase/Visit/Delete" />
        
      </msh:sideMenu>
      <msh:sideMenu title="Добавить" guid="d2c3339b-5d34-41a4-8e29-c91aafc9f483">
        <msh:sideLink params="id" action="/entityParentPrepareCreate-smo_diagnosis" name="Диагноз" title="Добавить диагноз" guid="a54c4c9e-7248-467f-8095-de4edfec868d" roles="/Policy/Mis/MedCase/Diagnosis/Create" key="ALT+5" />
        <msh:sideLink params="id" action="/entityParentPrepareCreate-vac_vaccination" name="Вакцинацию" title="Добавить вакцинацию" guid="f417958a-1816-4fe0-8592-dfe35ac19dc8" roles="/Policy/Mis/Vaccination/Create" key="ALT+6" />
        <msh:sideLink params="id" action="/entityParentPrepareCreate-smo_visitProtocol" name="Заключение" title="Добавить протокол" guid="2209b5f9-4b4f-4ed5-b825-b66f2ac57e87" roles="/Policy/Mis/MedCase/Protocol/Create" key="ALT+7" />
         <msh:sideLink roles="/Policy/Mis/MedCase/MedService/Create" key="ALT+8" name="Услугу" params="id" 
         	action="/entityParentPrepareCreate-smo_medService" title="Добавить услугу" guid="df23d" />
<%--         <msh:sideLink params="id" action="/js-smo_visit-addDisabilityByRedirectFromVisit" name="Нетрудоспособность" title="Добавить нетрудоспособность" guid="784c86f1-44e5-4642-ae35-b68c2abd0604" roles="/Policy/Mis/Disability/DisabilityCase/Create" key="ALT+7" /> --%>
        <msh:sideLink params="id" action="/entityParentPrepareCreate-smo_visitPrescriptionBlank" name="Рецептурный бланк" title="Добавить рецептурный бланк" guid="78d-4642-ae35-b6d04" roles="/Policy/Mis/MedCase/PrescriptionBlank/Create" />
      </msh:sideMenu>
      <msh:sideMenu title="Показать" guid="1e56f157-fd4c-4c13-9275-cfe7868b4ceb">
        <msh:sideLink params="id" action="/entityParentList-vac_vaccination" name="Вакцинации" title="Показать все вакцинации" roles="/Policy/Mis/Vaccination/View" guid="0b4406ba-1860-4063-a26a-ea11f6f9fb23" />
        <msh:sideLink roles="/Policy/Mis/Prescription/Prescript/View" name="Листы назначений" params="id" action="/entityParentList-pres_prescriptList" title="Показать все листы назначений" guid="7hb0b69ae-3b9c-47d9-ab3c-5fbe6fa9f" />
         <msh:sideLink roles="/Policy/Mis/MedCase/MedService/View" name="Услуги" params="id" action="/entityParentList-smo_medService" title="Показать все услуги" guid="df23-45a26d-5hfd" />
      </msh:sideMenu>
      <msh:sideMenu title="Печать" guid="cdf02c63-67bc-4542-a68d-38398f5059bd">

        <msh:sideLink action="/javascript:printVisit('.do')" name="Визита" title="Печать визита" key="ALT+8" guid="2156670f-b32c-4634-942b-2f8a4467567c" params="" roles="/Policy/Mis/MedCase/Visit/PrintVisit" />
        <msh:sideLink params="id" action="/print-talon_amb.do?s=VisitPrintService&amp;m=printTalon" key="ALT+9" name="Талона" title="Печать талона" guid="12dbaf61-0108-4845-86c3-cee528329b33" roles="/Policy/Mis/MedCase/Visit/PrintTalon" />
        <msh:sideLink params="id" action="/print-talon_amb_short.do?s=VisitPrintService&amp;m=printTalon"  name="Укороченного талона" title="Печать короткой версии талона" guid="1daf61-0108-4845-86c3-cs3" roles="/Policy/Mis/MedCase/Visit/PrintShortTalon" />
        <msh:sideLink roles="/Policy/Mis/MedCase/Visit/PrintBakExp" params="id" action="/print-BakExp.do?s=VisitPrintService&amp;m=printBakExp" name="Направления на бак.исследование" guid="51g38-f936-45d0-ac70-0g66c" />
      </msh:sideMenu>
    </msh:ifFormTypeIsView>
  </tiles:put>
  <tiles:put name="javascript" type="string">
  <script type='text/javascript' src='./dwr/interface/TemplateProtocolService.js'></script>
  <script type="text/javascript">
  var isPrint=0, isDiary=0, isDiag=0 ;
  function printVisit() {
  	TemplateProtocolService.getCountSymbolsInProtocol ('${param.id}'  , {
  	 callback: function(aCount) {
			 	//alert(aCount) ;
			     if (aCount>2760) {
			          window.location.href = "print-visit1.do?s=VisitPrintService&m=printVisit&id=${param.id}" ;
			     } else {

			     	 window.location.href = "print-visit.do?s=VisitPrintService&m=printVisit&id=${param.id}" ;

			     }
			    
			}}) ;
		$('isPrintInfo').checked='checked' ;
  }
  </script>
  <msh:ifFormTypeIsView formName="smo_visitForm">
  	<msh:ifNotInRole roles="/Policy/Mis/MedCase/Visit/DisableCheck">
  		<script type='text/javascript' src='./dwr/interface/PatientService.js'></script>
  		<msh:ifInRole roles="/Policy/Mis/MedCase/Diagnosis/Create">
	  		<script type="text/javascript">
	  		 isDiag=1 ;
	  		</script>
	  	</msh:ifInRole>
  		<msh:ifInRole roles="/Policy/Mis/MedCase/Protocol/Create">
	  		<script type="text/javascript">
	  		 isDiary=1 ;
	  			function isCreateDiary() {
	  			if ($('dateStart').value!="" && +$('isDiaryCreate').value<1) {
		  			if (confirm('Вы хотите создать заключение по визиту?')) {
		  				PatientService.setDiaryCreateByMedCase ('${param.id}' ,2 , {
  	 					callback: function(aCount) {
			    		window.location = "entityParentPrepareCreate-smo_visitProtocol.do?id=${param.id}";
			    		}}) ;
			    	} else {
			    	PatientService.setDiaryCreateByMedCase ('${param.id}' ,1 ,{
  	 					callback: function(aCount) {
						   if (isPrint) {
			 					isPrintVisit() ;
			 				
			 				}				 				
						}}) ;
			    	}
			    	
		    	} else {
						   if (isPrint) {
			 					isPrintVisit() ;
			 				
			 				}		    	
		    	}}
		    		  		</script>
	  	</msh:ifInRole>
  		<msh:ifInRole roles="/Policy/Mis/MedCase/Visit/PrintVisit">
	  		<script type="text/javascript">
	  		isPrint=1 ;
	  		function isPrintVisit() {
	  			if ($('dateStart').value!="" && +$('isPrint').value<1) {
		  			if (confirm('Вы хотите распечатать визит?')) {
		  				//PatientService.setPrintByMedCase ('${param.id}' ,2 , {
  	 					//callback: function(aCount) {
			    				    		printVisit() ;
							//if (confirm('Вы хотите принять нового пациента?')) { 
					    	//	window.location = "js-smo_visit-findPolyAdmissions.do";
					    	//}			 				
			    	   //}}) ;
			    		
			    	} else {
			    	PatientService.setPrintByMedCase ('${param.id}' ,1 ,{
  	 					callback: function(aCount) {
							//if (confirm('Вы хотите принять нового пациента?')) { 
					    	//	window.location = "js-smo_visit-findPolyAdmissions.do";
					    	//}			 				
						}}) ;
			    	}
			    	}
			    	
			    	
			    }	
	  		</script>
	  	</msh:ifInRole>
  		
	  	<msh:ifInRole roles="/Policy/Mis/MedCase/Diagnosis/Create">
	  		<script type="text/javascript">
	  			if ($('dateStart').value!="" && +$('isDiagnosisCreate').value<1) {
		  			if (confirm('Вы хотите создать диагноз по визиту?')) {
		  				PatientService.setDiagnosisCreateByMedCase ('${param.id}' ,2 , {
  	 					callback: function(aCount) {
			 				window.location = "entityParentPrepareCreate-smo_diagnosis.do?id=${param.id}";
						}}) ;
			    		
			    	} else {
			    	PatientService.setDiagnosisCreateByMedCase ('${param.id}' ,1 ,{
  	 					callback: function(aCount) {
			 				if (isDiary) {
			 					isCreateDiary() ;
			 				} else if (isPrint) {
			 					isPrintVisit() ;
			 				}
						}}) ;
			    	}
		    	} else {
			 				if (isDiary) {
			 					isCreateDiary() ;
			 				} else if (isPrint) {
			 					isPrintVisit() ;
			 				
			 				}
		    	}
	  		</script>
	  	</msh:ifInRole>
	  	<msh:ifInRole roles="/Policy/Mis/MedCase/Protocol/Create">
	  		<script type="text/javascript">

		    	if (isDiag==0) {
		    		isCreateDiary() ;
		    	}
		    			    	

	  		</script>
	  	</msh:ifInRole>
	  	<msh:ifInRole roles="/Policy/Mis/MedCase/Visit/PrintVisit">
	  		<script type="text/javascript">
	  		
		    	if (isDiag==0 &&isDiary==0) {
		    		isPrintVisit() ;
		    		
		    	}	  			
	  		</script>
	  	</msh:ifInRole>
  	</msh:ifNotInRole>
  </msh:ifFormTypeIsView>
  </tiles:put>
</tiles:insert>

