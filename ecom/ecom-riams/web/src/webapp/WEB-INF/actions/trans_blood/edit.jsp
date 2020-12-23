<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://struts.apache.org/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://www.nuzmsh.ru/tags/msh" prefix="msh" %>
<%@ taglib uri="http://www.ecom-ast.ru/tags/ecom" prefix="ecom" %>

<tiles:insert page="/WEB-INF/tiles/mainLayout.jsp" flush="true">

  <tiles:put name="side" type="string">
    <msh:ifFormTypeIsView formName="trans_bloodForm">
      <msh:sideMenu title="Переливание крови">
        <msh:sideLink key="ALT+2" params="id" action="/entityParentEdit-trans_blood" name="Изменить" roles="/Policy/Mis/MedCase/Transfusion/Blood/Edit" />
        <msh:sideLink key="ALT+DEL" confirm="Удалить?" params="id" action="/entityParentDelete-trans_blood" name="Удалить" roles="/Policy/Mis/MedCase/Transfusion/Blood/Delete" />
      </msh:sideMenu>
      <msh:sideMenu title="Печать">
    <msh:sideLink roles="/Policy/Mis/MedCase/Transfusion/Blood/View" 
    	name="Протокол переливания" params="id"  
    	action='/print-stac_transfusion_blood_prot.do?m=printBloodTransfusionInfo&s=HospitalPrintService' title='Печать протокола переливания крови'
    	/>     
    </msh:sideMenu>
    </msh:ifFormTypeIsView>
  </tiles:put>
  <tiles:put name="body" type="string">
    <!-- 
    	  - Переливание крови
    	  -->
    <msh:form action="/entityParentSaveGoView-trans_blood.do" defaultField="journalNumber">
      <msh:hidden property="id" />
      <msh:hidden property="medCase" />
      <msh:hidden property="saveType" />
        <msh:hidden property="countPregnancy"/>
        <msh:hidden property="pregnancyHang"/>
        <msh:hidden property="patBloodGroupCheck"/>
        <msh:hidden property="prepBloodGroupCheck"/>
      <msh:panel colsWidth="1%,1%,15%,81%">
        <msh:row>
          <msh:textField property="journalNumber" label="Номер в журнале" />
          <msh:textField property="startDate" label="Дата" />
        </msh:row>
        <msh:row>
        	<msh:textField property="timeFrom" label="Время переливания с"/>
        	<msh:textField property="timeTo" label="по"/>
        </msh:row>
        <msh:row>
          <msh:separator label="Данные медицинского обследования реципиента" colSpan="10" />
        </msh:row>
        <msh:row>
          <msh:autoComplete property="patientBloodGroup" label="Группа крови" vocName="vocBloodGroup" horizontalFill="true" />
          <msh:autoComplete property="patientRhesusFactor" label="Резус-фактор" vocName="vocRhesusFactor"  />
        </msh:row>
        </msh:panel>
        <msh:row>
        	<msh:textField viewOnlyField="true" property="phenotype" label="Фенотип" size="20" />
        	<msh:checkBox property="phenotypeC" label="C"/>
        	<msh:checkBox property="phenotypec1" label="c"/>
        	<msh:checkBox property="phenotypeD" label="D"/>
        	<msh:checkBox property="phenotypeE" label="E"/>
        	<msh:checkBox property="phenotypee1" label="e"/>
            <msh:checkBox property="phenotypeNone" label="Не опр"/>
        </msh:row>
        <msh:panel>
        <msh:row>
        	<msh:autoComplete property="definitionRhesus" vocName="vocTransfusionDefinitionRhesus" label="Определение резус-принад. рециента производилось" labelColSpan="2" fieldColSpan="2" horizontalFill="true"/>
        </msh:row>
        <msh:row>
            <msh:autoComplete property="researchAntibodies" label="Аллоимунные антитела" vocName="vocTransfusionResearchAntibodies"/>
        </msh:row>
        <msh:row>
            <msh:separator label="Анамнез реципиента" colSpan="10" />
        </msh:row>
        <msh:row>
        	<msh:autoComplete property="transfusionHistory" label="Были переливания" vocName="vocYesNo"/>
        	<msh:autoComplete property="personalTransfusionHistory" label="Были трансфизии по инд.подбору" vocName="vocYesNo"/>
        </msh:row>
        <msh:row>
        	<msh:autoComplete property="transfusionReactionLast" label="Были реакции осложнения на переливания" vocName="vocYesNo" labelColSpan="3"/>
        </msh:row>
        </msh:panel>
        <msh:panel>
        <msh:row>
          <msh:separator label="Данные о донорской крови или её компоненте" colSpan="10" />
        </msh:row>
        <msh:row>
          <msh:autoComplete property="bloodPreparation" label="Наименование компонента крови" horizontalFill="true" vocName="vocBloodPreparation" fieldColSpan="5" />
        </msh:row>
        <msh:row>
          <msh:autoComplete vocName="vocPreparatorBlood" property="preparator" label="Изготовитель" horizontalFill="true" fieldColSpan="1" size="47"/>
          <msh:textField property="preparationDate" label="Дата заготовки" />
          <msh:textField property="expirationDate" label="Срок годности" />
        </msh:row>
        <msh:row>
          <msh:textField property="containerNumber" label="№ единицы компонента крови" size="35"/>
          <msh:textField property="doze" label="Количество (мл)"/>
        </msh:row>
        <msh:row>
        </msh:row>
        <msh:row>
          <msh:autoComplete property="preparationBloodGroup" label="Группа крови" vocName="vocBloodGroup" horizontalFill="true" />
          <msh:autoComplete property="preparationRhesusFactor" label="Резус-фактор" vocName="vocRhesusFactor" horizontalFill="true" />
        </msh:row>
            <msh:panel colsWidth="5%,1%,1%,1%,1%,1%,1%,1%,1%,1%,1%,1%,1%">
                <msh:row>
                    <msh:textField viewOnlyField="true" property="phenotypeDon" label="Фенотип" size="20" />
                    <msh:checkBox property="phenotypeDonC" label="C"/>
                    <msh:checkBox property="phenotypeDonc1" label="c"/>
                    <msh:checkBox property="phenotypeDonD" label="D"/>
                    <msh:checkBox property="phenotypeDonE" label="E"/>
                    <msh:checkBox property="phenotypeDone1" label="e"/>
                    <msh:checkBox property="phenotypeDonNone" label="Не опр"/>
                </msh:row>
            </msh:panel>
            <msh:row>
                <msh:separator label="Показания к трансфузии" colSpan="10" />
            </msh:row>
            <msh:row>
                <msh:autoComplete property="transfusionMethod" label="Способ переливания" vocName="vocTransfusionMethod" size="55" fieldColSpan="9" />
            </msh:row>
            <msh:row>
                <msh:autoComplete property="reason" label="Показания к трансфузии" parentAutocomplete="bloodPreparation" vocName="vocTransfusionReason" fieldColSpan="9" size="55" />
            </msh:row>
            <msh:row>
                <msh:separator label="Результаты индивидуального подбора" colSpan="10" />
            </msh:row>
        <msh:row>
            <msh:autoComplete vocName="vocPreparatorBloodIndOrg" property="indOrg" label="Организация инд. подбора" size="55"/>
        </msh:row>
            <br>
        <msh:row>
            <msh:textField property="dateResearch" label="Дата исследования" />
        </msh:row>
            <br>
        <msh:row>
            <msh:autoComplete property="conclusion" label="Заключение (совместимо/нет)?" vocName="vocYesNo"/>
        </msh:row>
        </msh:panel>
        <msh:panel>
        <msh:row>
          <msh:separator label="Пробы на индивидуальную совместимость в отделении" colSpan="10"/>
        </msh:row>
        <msh:row>
        	<msh:autoComplete property="reagentForm1.reagent" label="Реагент 1" vocName="vocTransfusionReagent" fieldColSpan="3" horizontalFill="true" size="20"/>
        	<msh:textField property="reagentForm1.series" label="Серия"/>
        	<msh:textField property="reagentForm1.expirationDate" label="Срок годности"/>
        </msh:row>
        <msh:row>
        	<msh:autoComplete property="reagentForm2.reagent" label="Реагент 2" vocName="vocTransfusionReagent" fieldColSpan="3" horizontalFill="true" size="20"/>
        	<msh:textField property="reagentForm2.series" label="Серия"/>
        	<msh:textField property="reagentForm2.expirationDate" label="Срок годности"/>
        </msh:row>
        <msh:row>
            <msh:autoComplete property="reagentForm3.reagent" label="Реагент 3" vocName="vocTransfusionReagent" fieldColSpan="3" horizontalFill="true" size="20"/>
            <msh:textField property="reagentForm3.series" label="Серия"/>
            <msh:textField property="reagentForm3.expirationDate" label="Срок годности"/>
        </msh:row>
         </msh:panel>
        <msh:panel>
        <msh:row>
            <msh:autoComplete property="planeCompatibility" label="На плоскости (совместимо/нет)?" vocName="vocYesNo"/>
        </msh:row>
        <msh:row>
            <msh:autoComplete property="bioProbeCompatibility" label="Биологическая проба (совместимо?)" vocName="vocYesNo"/>
        </msh:row>
        <msh:row>
          <msh:separator label="Реакции и осложнения" colSpan="10" />
        </msh:row>
        <msh:row>
			<ecom:checkGroup fieldColSpan="9" label="Осложнения" tableName="VocTransfusionReaction" tableField="'<b>'||name||'</b> - '||description" 
					tableId="id" property="complications"  />
		</msh:row>
        <msh:row>
          <msh:separator label="Наблюдение за состоянием реципиента. До начала трансфузии" colSpan="10"/>
        </msh:row>
        <msh:row>
        	<msh:textField size="4" property="monitorForm0.bloodPressureTop" label="АД сис."/>
        	<msh:textField size="4" property="monitorForm0.bloodPressureLower" label="АД дис."/>
        	<msh:textField size="4" property="monitorForm0.pulseRate" label="PS"/>
        	<msh:textField size="4" property="monitorForm0.temperature" label="t"/>
        	<msh:autoComplete size="15" property="monitorForm0.urineColor" horizontalFill="true" vocName="vocUrineColor" label="Цвет мочи"/>
            <msh:textField size="4" property="monitorForm0.diuresis" label="Диурез"/>
        </msh:row>
        <msh:row>
          <msh:separator label="через 1 час после переливания" colSpan="15"/>
        </msh:row>
        <msh:row>
        	<msh:textField size="4" property="monitorForm1.bloodPressureTop" label="АД сис."/>
        	<msh:textField size="4" property="monitorForm1.bloodPressureLower" label="АД дис."/>
        	<msh:textField size="4" property="monitorForm1.pulseRate" label="PS"/>
        	<msh:textField size="4" property="monitorForm1.temperature" label="t"/>
        	<msh:autoComplete size="15" property="monitorForm1.urineColor" horizontalFill="true" vocName="vocUrineColor" label="Цвет мочи"/>
            <msh:textField size="4" property="monitorForm1.diuresis" label="Диурез"/>
        </msh:row>
        <msh:row>
          <msh:separator label="через 2 часа после переливания" colSpan="15"/>
        </msh:row>
        <msh:row>
        	<msh:textField size="4" property="monitorForm2.bloodPressureTop" label="АД сис."/>
        	<msh:textField size="4" property="monitorForm2.bloodPressureLower" label="АД дис."/>
        	<msh:textField size="4" property="monitorForm2.pulseRate" label="PS"/>
        	<msh:textField size="4" property="monitorForm2.temperature" label="t"/>
        	<msh:autoComplete size="15" property="monitorForm2.urineColor" horizontalFill="true" vocName="vocUrineColor" label="Цвет мочи" />
            <msh:textField size="4" property="monitorForm2.diuresis" label="Диурез"/>
        </msh:row>

        <msh:row>
          <msh:autoComplete property="executor" label="Исполнитель" vocName="workFunction" fieldColSpan="9" horizontalFill="true" />
        </msh:row>
        <msh:submitCancelButtonsRow colSpan="5" functionSubmit="save(this);"/>
      </msh:panel>
    </msh:form>
  </tiles:put>
  <tiles:put name="title" type="string">
    <ecom:titleTrail mainMenu="Patient" beginForm="trans_bloodForm" />
  </tiles:put>
  <tiles:put name="javascript" type="string">
  <msh:ifFormTypeIsNotView formName="trans_bloodForm">
  	<script type="text/javascript">
  	eventutil.addEventListener($('phenotypeC'), 'click', phenotype) ;
  	eventutil.addEventListener($('phenotypec1'), 'click', phenotype) ;
  	eventutil.addEventListener($('phenotypeE'), 'click', phenotype) ;
  	eventutil.addEventListener($('phenotypee1'), 'click', phenotype) ;
  	eventutil.addEventListener($('phenotypeD'), 'click', phenotype) ;
    eventutil.addEventListener($('phenotypeNone'), 'click', phenotype) ;

    eventutil.addEventListener($('phenotypeDonC'), 'click', phenotype) ;
    eventutil.addEventListener($('phenotypeDonc1'), 'click', phenotype) ;
    eventutil.addEventListener($('phenotypeDonE'), 'click', phenotype) ;
    eventutil.addEventListener($('phenotypeDone1'), 'click', phenotype) ;
    eventutil.addEventListener($('phenotypeDonD'), 'click', phenotype) ;
    eventutil.addEventListener($('phenotypeDonNone'), 'click', phenotype) ;

  	function phenotype() {
  		$('phenotype').value="C"+check("phenotypeC")+"c"+check("phenotypec1")+"D"+check("phenotypeD")+"E"+check("phenotypeE")+"e"+check("phenotypee1") + "Не опр"+check("phenotypeNone");
  		$('phenotypeReadOnly').value=$('phenotype').value ;
        $('phenotypeDon').value="C"+check("phenotypeDonC")+"c"+check("phenotypeDonc1")+"D"+check("phenotypeDonD")+"E"+check("phenotypeDonE")+"e"+check("phenotypeDone1") + "Не опр"+check("phenotypeDonNone");
        $('phenotypeDonReadOnly').value=$('phenotypeDon').value ;
  	}
  	function check(aField) {
  		var fld = $(aField).checked ;
  		if (fld==true) {return "+";} else {return "-" ;}
  	}
  	phenotype();

  	//поля обязательны для заполнения
  	for (var i=1; i<=3; i++) {
        $('reagentForm'+i+'.reagentName').className += ' required';
        $('reagentForm'+i+'.series').className += ' required';
        $('reagentForm'+i+'.expirationDate').className += ' required';
    }
      <msh:ifFormTypeIsCreate formName="trans_bloodForm">
          $('reagentForm1.reagent').value=1;
          $('reagentForm2.reagent').value=2;
          $('reagentForm3.reagent').value=3;
          $('reagentForm1.reagentName').value='Цоликлоны анти А';
          $('reagentForm2.reagentName').value='Цоликлоны анти В';
          $('reagentForm3.reagentName').value='Цоликлоны анти D';
      </msh:ifFormTypeIsCreate>
    //проверка и сохранение
    function save(btn) {
  	    var msg = '';
        for (var i=1; i<=3; i++) {
            if (!$('reagentForm'+i+'.reagentName').value)
                msg+=' реагент '+i + "; ";
            if (!$('reagentForm'+i+'.series').value)
                msg+=' серия реагента '+i + "; ";
            if (!$('reagentForm'+i+'.expirationDate').value)
                msg+=' срок годности реагента '+i + "; ";
        }
        btn.value='Создание...';
        if (msg) {
            showToastMessage("Заполните обязательные поля: " + msg,null,true,true,5000);
            btn.removeAttribute("disabled");
            btn.value='Создать';
        }
        else
            document.forms[0].submit();
    }
  	</script>
  	</msh:ifFormTypeIsNotView>
  </tiles:put>
</tiles:insert>

