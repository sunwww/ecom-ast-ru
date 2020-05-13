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
    <ecom:titleTrail mainMenu="Patient" beginForm="stac_extsslForm" />
  </tiles:put>
  <tiles:put name="side" type="string">
    <msh:ifFormTypeAreViewOrEdit formName="stac_extsslForm">
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
    <msh:form action="/entityParentSaveGoView-stac_extssl.do" defaultField="" title="Госпитализация в другом ЛПУ" editRoles="/Policy/Mis/MedCase/Stac/Ssl/Ext/Edit" createRoles="/Policy/Mis/MedCase/Stac/Ssl/Ext/Create">
      <msh:hidden property="id" />
      <msh:hidden property="patient" />
      <msh:hidden property="outcome" />
      <msh:hidden property="saveType" />
      <msh:hidden property="dischargeTime" />
      <msh:hidden property="provisional" />
      <msh:hidden property="result" />
      <msh:hidden property="moveToAnotherLPU" />
      <msh:hidden property="dischargeEpicrisis" />
      <msh:hidden property="rareCase"/>
      <msh:ifNotInRole roles="/Policy/Mis/MedCase/IsPsychiatry">
        <msh:hidden property="compulsoryTreatment"/>
        <msh:hidden property="incapacity"/>
        <msh:hidden property="lawCourtDesicionDate"/>
        <msh:hidden property="psychReason"/>      
      </msh:ifNotInRole>
      <msh:panel>
	

        <msh:row>
          <msh:textField property="dateStart" label="Дата поступления"  />
          <msh:textField property="dateFinish" label="Дата выписки"  />
        </msh:row>
        <msh:row>
          <msh:autoComplete property="lpu" label="Лечебное учреждение" vocName="lpu" fieldColSpan="3" horizontalFill="true" />
        </msh:row>

        <msh:separator label="Направлен" colSpan="6" />
        <msh:row>
          <msh:autoComplete property="orderLpu" label="Кем направлен" vocName="mainLpu" horizontalFill="true" fieldColSpan="3" />
        </msh:row>

        <msh:ifInRole roles="/Policy/Mis/MedCase/IsPsychiatry">
	        <msh:row>
		        <msh:autoComplete label="Причина госпитализации в психиатрический стационар" vocName="vocPsychHospitalReason" property="psychReason" labelColSpan="3"/>
	        </msh:row>
	        <msh:row>
	        	<msh:autoComplete property="admissionOrder" label="Порядок поступления" fieldColSpan="3" vocName="vocAdmissionOrder"/>
	        </msh:row>
	        <msh:row>
	        	<msh:autoComplete property="judgment35" label="Решение судьи по ст. 35" fieldColSpan="3" vocName="vocJudgment"/>
	        </msh:row>
	        <msh:row>
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
        <msh:separator label="Дополнительно" colSpan="9" />
        <msh:row>
          <msh:label property="username" label="Оператор" />
          <msh:checkBox property="noActuality" label="Недействительность" />
        </msh:row>
        <msh:submitCancelButtonsRow colSpan="4" labelSave="Сохранить изменения" labelCreating="Создание" labelCreate="Создать новый случай" labelSaving="Сохранение данных" />
      </msh:panel>
    </msh:form>
    <msh:ifFormTypeIsView formName="stac_extsslForm">
      <msh:ifInRole roles="/Policy/Mis/MedCase/Diagnosis/View">
         <ecom:webQuery name="diags" nativeSql="select Diagnosis.id, VocDiagnosisRegistrationType.name as vdrtname, Diagnosis.establishDate, &#xA;  ' '||Diagnosis.name,VocIdc10.code, VocPriorityDiagnosis.name as vpdname&#xA;  from Diagnosis&#xA;  left outer join VocDiagnosisRegistrationType on Diagnosis.registrationType_id = VocDiagnosisRegistrationType.id left outer join VocPriorityDiagnosis on Diagnosis.priority_id = VocPriorityDiagnosis.id&#xA;  left outer join VocIdc10     on Diagnosis.idc10_id = VocIdc10.id&#xA; where Diagnosis.medCase_id=${param.id} &#xA;" />
        <msh:section title="Диагнозы. <a href='entityParentPrepareCreate-stac_diagnosis.do?id=${param.id }'>Добавить новый диагноз</a>">
          <msh:table name="diags" action="entityParentView-stac_diagnosis.do" idField="1">
              <msh:tableColumn property="sn" columnName="#" />
            <msh:tableColumn columnName="Тип регистрации" property="2" />
            <msh:tableColumn columnName="Приоритет" property="6" />
            <msh:tableColumn columnName="Дата" property="3" />
            <msh:tableColumn columnName="Наименование" property="4" />
            <msh:tableColumn columnName="Код МКБ" property="5" />
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

