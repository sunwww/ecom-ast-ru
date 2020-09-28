<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://struts.apache.org/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://www.nuzmsh.ru/tags/msh" prefix="msh" %>
<%@ taglib uri="http://www.ecom-ast.ru/tags/ecom" prefix="ecom" %>
<%@ taglib tagdir="/WEB-INF/tags" prefix="tags" %>

<tiles:insert page="/WEB-INF/tiles/mainLayout.jsp" flush="true">
	<tiles:put name="style" type="string">
        <style type="text/css">

            #concomitantDiagnosLabel, #concomitantMkbLabel {
                color: green ;
            }

            #concludingDiagnosLabel, #concludingMkbLabel, #concludingActuityLabel {
                color: blue ;
            }
            #pathanatomicalDiagnosLabel, #pathanatomicalMkbLabel {
                color: red ;
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
    <msh:ifFormTypeIsView formName="stac_deathCaseForm">
      <msh:sideMenu title="Случай смерти">
        <msh:sideLink key="ALT+2" params="id" action="/entityParentEdit-stac_deathCase" name="Изменить" roles="/Policy/Mis/MedCase/DeathCase/Edit" />
        <msh:sideLink key="ALT+DEL" confirm="Удалить?" params="id" action="/entityParentDelete-stac_deathCase" name="Удалить" roles="/Policy/Mis/MedCase/DeathCase/Delete" />
      </msh:sideMenu>
      <msh:sideMenu title="Добавить">
        <msh:sideLink roles="/Policy/Mis/Certificate/Death/Create" name="Свидетельство о смерти" params="id" action="/entityParentPrepareCreate-stac_deathCertificate" title="Добавить свидетельство о смерти" />
        <msh:sideLink roles="/Policy/Mis/MedCase/ProtocolKili/Create" name="Протокол КИЛИ" params="id" action="/entityParentPrepareCreate-mis_protocolKili" title="Добавить протокол КИЛИ" />
      </msh:sideMenu>
    </msh:ifFormTypeIsView>
  </tiles:put>
  <tiles:put name="body" type="string">
    <!-- 
    	  - Случай смерти
    	  -->
    <msh:form action="/entityParentSaveGoView-stac_deathCase.do"
    	 defaultField="postmortemBureauNumber" >
      <msh:hidden property="id" />
      <msh:hidden property="saveType" />
      <msh:hidden property="medCase" />
      <msh:hidden property="patient" />
      <msh:hidden property="deathPlaceAddress" />
      <msh:hidden property="deathPlaceFlatNumber" />
      <msh:hidden property="deathPlaceHouseNumber" />
      <msh:hidden property="deathPlaceHouseBuilding" />
      
      <msh:panel colsWidth="1%,1%,1%">
      	<msh:row>
      		<msh:separator label="Основные сведения" colSpan="4"/>
      	</msh:row>
        <msh:row>
          <msh:textField property="deathDate" label="Дата смерти" />
          <msh:textField property="deathTime" label="время" />
        </msh:row>
        <msh:row>
          <msh:textField property="postmortemBureauNumber" label="№ПАБ" />
          <msh:textField property="postmortemBureauDt" label="Дата ПАБ" />
        </msh:row>
        <msh:row>
          <msh:textField property="dateForensic" label="Дата СМЭ" />
          <msh:checkBox property="isAutopsy" label="Вскрытие" />
        </msh:row>
        <msh:row>
          <msh:checkBox property="isPresenceDoctorAutopsy" labelColSpan="3" label="Присутствовал леч.врач на вскрытие"/>
        </msh:row>
        <msh:row>
          <msh:checkBox property="isNeonatologic" labelColSpan="3" label="Мертворождение" viewOnlyField="true"/>
        </msh:row>
        <msh:row>
          <msh:autoComplete vocName="vocDeathPlace" property="deathPlace" label="Место смерти" fieldColSpan="3" horizontalFill="true" />
        </msh:row>
        <msh:row>
          <msh:autoComplete showId="false" vocName="vocDeathReason" hideLabel="false" property="deathReason" viewOnlyField="false" label="Смерть произошла от:" fieldColSpan="3" horizontalFill="true" />
        </msh:row>

        <msh:row>
          <td colspan="1" title="Адрес (deathPlaceAddressField)" class="label">
            <label id="deathPlaceAddressFieldLabel" for="deathPlaceAddressField">Адрес места смерти:</label>
          </td>
          <td colspan="3" class="deathPlaceAddressField" id="deathPlaceAddressFieldRow">
            <input title="АдресNoneField" class=" horizontalFill" id="deathPlaceAddressField" name="deathPlaceAddressField" size="10" value="Адрес... " type="text" />
          </td>
        </msh:row>
        <msh:row>
        	<msh:separator label="Диагнозы" colSpan="6"/>
        </msh:row>
        <msh:row>
        	<msh:autoComplete vocName="vocIdc10" label="МКБ-10 осн. ПС" property="reasonMainMkb" fieldColSpan="3" horizontalFill="true"/>
        </msh:row>
        <msh:row>
        	<msh:textArea property="commentReason" label="Причина смерти (ПС)" fieldColSpan="3" horizontalFill="true" rows="2"/>
        </msh:row>
        <msh:row>
        	<msh:autoComplete vocName="vocIllnesPrimary" property="concludingActuity" horizontalFill="true" label="Характер заболевания"
        		fieldColSpan="3"
        	/>
        </msh:row>
        <msh:row>
	        <msh:autoComplete vocName="vocIdc10" label="МКБ-10 закл.диаг." property="concludingMkb" fieldColSpan="3" horizontalFill="true"/>
        </msh:row>
        <msh:row>
	        <msh:textArea rows="3" label="Заключительный диагноз" property="concludingDiagnos" fieldColSpan="3" horizontalFill="true"/>
        </msh:row>
        <msh:row>
    	    <msh:autoComplete vocName="vocIdc10" label="МКБ-10 патанат.диаг." property="pathanatomicalMkb" fieldColSpan="3" horizontalFill="true"/>
        </msh:row>
        <msh:row>
        	<msh:textArea rows="3" label="Патанатомический диагноз" property="pathanatomicalDiagnos" fieldColSpan="3" horizontalFill="true"/>
        </msh:row>
        <msh:row>
        	<msh:autoComplete vocName="vocIdc10" label="МКБ-10 осл. ПС" property="reasonComplicationMkb" fieldColSpan="3" horizontalFill="true"/>
        </msh:row>
        <msh:row>
        	<msh:textArea rows="3" label="осл. ПС" property="reasonComplicationText" fieldColSpan="3" horizontalFill="true"/>
        </msh:row>
        <msh:row>
        	<msh:autoComplete vocName="vocIdc10" label="МКБ-10 соп. ПС" property="reasonConcomitantMkb" fieldColSpan="3" horizontalFill="true"/>
        </msh:row>
        <msh:row>
        	<msh:textArea rows="3" label="соп. ПС" property="reasonConcomitantText" fieldColSpan="3" horizontalFill="true"/>
        </msh:row>
        <msh:row>
        	<msh:textArea rows="3" property="competingDisease" label="Конкурирующее заболевание" fieldColSpan="3" horizontalFill="true"/>
        </msh:row>
        <msh:row>
        	<msh:textArea rows="3" property="polypathia" label="Сочетанное заболевание" fieldColSpan="3" horizontalFill="true"/>
        </msh:row>
        <msh:row>
        	<msh:textArea rows="3" property="backgroundDisease" label="Фоновое заболевание" fieldColSpan="3" horizontalFill="true"/>
        </msh:row>
        <msh:row>
        	<msh:separator label="Расхождения " colSpan="6"/>
        </msh:row>
        <msh:row>
        	<msh:autoComplete property="categoryDifference" label="Категория" vocName="vocDeathCategory"/>
        	<msh:autoComplete property="latrogeny" label="Ятрогения" vocName="vocDeathCategory"/>
        </msh:row>
        <msh:row>
        	<msh:autoComplete fieldColSpan="3" property="diagnosisDifference" label="Приор. диагноза" vocName="vocPriorityDiagnosis" horizontalFill="true"/>
        </msh:row>
        <msh:row>
        	<msh:textArea property="commentCategory" label="Комментарий" fieldColSpan="5" horizontalFill="true" rows="4"/>
        </msh:row>
        <msh:ifInRole roles="/Policy/Mis/MedCase/DeathCase/Child">
        <msh:separator label="Для детей, умерших в возрасте до 1 года" colSpan=""/>
        <msh:row>
          <msh:textField property="birthPlace" label="Место рождения" horizontalFill="true" fieldColSpan="3" />
        </msh:row>
        <msh:row>
          <msh:textField property="birthPlaceAdress" label="Адрес места рождения" fieldColSpan="3" horizontalFill="true" />
        </msh:row>
        <msh:row>
          <msh:autoComplete vocName="patient" property="mother" label="Мать" fieldColSpan="3" horizontalFill="true" />
        </msh:row>
        <msh:separator label="Для детей, умерших в возрасте от 6 дней до 1 месяца" colSpan="" />
        <msh:row>
          <msh:autoComplete vocName="vocIsPrematurity" property="isPrematurity" label="Доношенность" fieldColSpan="3" horizontalFill="true" />
        </msh:row>
        <msh:separator label="Для детей, умерших в возрасте от 6 дней до 1 года" colSpan="" />
        <msh:row>
          <msh:textField property="babyNumber" label="Какой ребенок по счету у матери" labelColSpan="2" horizontalFill="true" />
        </msh:row>
        <msh:row>
          <msh:textField property="birthWeight" labelColSpan="2" label="Масса (вес) при рождении, гр." />
        </msh:row>
        </msh:ifInRole>
        <msh:separator label="В случае смерти, не от заболевания" colSpan="" />
        <msh:row>
          <msh:textField label="Дата (травмы) отравления" property="accidentDate" />
          <msh:textField label="Место" property="accidentPlace" fieldColSpan="1" horizontalFill="true" />
        </msh:row>
        <msh:row>
          <msh:textField property="accidentCircumstance" label="Обстоятельства" fieldColSpan="3" horizontalFill="true" />
        </msh:row>
        <msh:separator label="Причина смерти установлена" colSpan="" />
        <msh:row>
          <msh:autoComplete vocName="vocDeathWitnessFunction" property="deathWitnessFunction" label="Кем установлена" fieldColSpan="3" horizontalFill="true" />
        </msh:row>
        <msh:row>
          <msh:autoComplete vocName="workFunction" property="deathWitness" label="Врач" fieldColSpan="3" horizontalFill="true" viewAction="entitySubclassView-work_workFunction.do" />
        </msh:row>
        <msh:row>
          <ecom:oneToManyOneAutocomplete property="deathEvidence" vocName="vocDeathEvidence" label="На основании" colSpan="4" />
        </msh:row>
        <msh:row>
          <msh:autoComplete vocName="vocAfterPregnance" property="afterPregnance" label="Умерла после окончания беременности" fieldColSpan="2" horizontalFill="true" labelColSpan="2" />
        </msh:row>
        <msh:row>
        	<msh:separator label="Дополнительная информация" colSpan="4"/>
        </msh:row>
        <msh:row>
        	<msh:label property="createDate" label="Дата создания"/>
        	<msh:label property="createTime" label="время"/>
        </msh:row>
        <msh:row>
        	<msh:label property="createUsername" label="пользователь"/>
        </msh:row>
        <msh:row>
        	<msh:label property="editDate" label="Дата редактирования"/>
        	<msh:label property="editTime" label="время"/>
        </msh:row>
        <msh:row>
        	<msh:label property="editUsername" label="пользователь"/>
        </msh:row>                
        <msh:submitCancelButtonsRow colSpan="" />
      </msh:panel>
    </msh:form>
    <msh:ifFormTypeIsView formName="stac_deathCaseForm">
      <msh:ifInRole roles="/Policy/Mis/Certificate/Death/View">
        <msh:section title="Свидетельства о смерти">
          <ecom:parentEntityListAll formName="stac_deathCertificateForm" attribute="deathCertificate" />
          <msh:table name="deathCertificate" action="entityParentView-stac_deathCertificate.do" idField="id">
            <msh:tableColumn columnName="Дата выдачи" property="dateIssue" cssClass="preCell" />
            <msh:tableColumn columnName="Серия" property="series" cssClass="preCell" />
            <msh:tableColumn columnName="Номер" property="number" cssClass="preCell" />
          </msh:table>
        </msh:section>
      </msh:ifInRole>
      <msh:ifInRole roles="/Policy/Mis/Certificate/Death/View">
        <msh:section title="КИЛИ о смерти">
          <ecom:parentEntityListAll formName="mis_protocolKiliForm" attribute="deathKilis" />
           <msh:table name="deathKilis" action="entityParentView-mis_protocolKili.do" idField="id">
            <msh:tableColumn columnName="Дата протокола" property="protocolDate" cssClass="preCell" />
            <msh:tableColumn columnName="Номер протокола" property="protocolNumber" cssClass="preCell" />
            </msh:table>  
        </msh:section>
      </msh:ifInRole>
    </msh:ifFormTypeIsView>
<script type="text/javascript" src='./dwr/interface/AddressService.js' ></script>
    <tags:addressNewTag form="stac_deathCaseForm" name="deathPlaceAddress" zipcode="" flatNumber="deathPlaceFlatNumber" houseNumber="deathPlaceHouseNumber" houseBuilding="deathPlaceHouseBuilding" addressField="deathPlaceAddressField" />
  </tiles:put>
  <tiles:put name="title" type="string">
    <ecom:titleTrail mainMenu="Patient" beginForm="stac_deathCaseForm" />
  </tiles:put>
  <tiles:put name="javascript" type="string" >
          <msh:ifFormTypeIsNotView formName="stac_deathCaseForm">
          <script type="text/javascript" src="./dwr/interface/HospitalMedCaseService.js">/**/</script>
      <script type="text/javascript">//var theBedFund = $('bedFund').value;
       try {
    	   deathReasonAutocomplete.addOnChangeCallback(function() {
    		   if (+$('deathReason').value>0) HospitalMedCaseService.getCriminalPhoneMessageByTrauma($('medCase').value,$('deathReason').value,{
    				
   	  			callback: function(aResult) {
   	  				//alert(aResult) ;
   	  				if (aResult!=null) {
   	  					var sp = aResult.split("@#@") ;
   	  					$('accidentDate').value=sp[0] ;
   	  					$('accidentPlace').value=sp[1] ;
   	  					$('accidentCircumstance').value=sp[2] ;
   	  				}
   	  				
   	  				
   	  			}
   	  		}) ;
    	   });
       } catch(e) {}
  		try {
	    if (pathanatomicalMkbAutocomplete) 
	    	pathanatomicalMkbAutocomplete.addOnChangeCallback(function() {
	      	 	setDiagnosisText('pathanatomicalMkb','pathanatomicalDiagnos');
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
      function isAutopsyCheck() {
    	  if ($('postmortemBureauNumber').value!="" || $('postmortemBureauDt').value!="" || $('dateForensic').value!="") {
    		  $('isAutopsy').checked="checked" ;
    	  }
      }
      eventutil.addEventListener($('postmortemBureauNumber'), "change", function() {isAutopsyCheck() ;}) ;
      eventutil.addEventListener($('postmortemBureauNumber'),'blur',function(){isAutopsyCheck() ;}) ;
      eventutil.addEventListener($('postmortemBureauDt'), "change", function() {isAutopsyCheck() ;}) ;
      eventutil.addEventListener($('postmortemBureauDt'),'blur',function(){isAutopsyCheck() ;}) ;
      eventutil.addEventListener($('dateForensic'), "change", function() {isAutopsyCheck() ;}) ;
      eventutil.addEventListener($('dateForensic'),'blur',function(){isAutopsyCheck() ;}) ;
    </script>
    </msh:ifFormTypeIsNotView>
  
  </tiles:put>
</tiles:insert>

