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
        <msh:hidden property="reagentPT2"/>
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
          <msh:separator label="Данные о реципиенте" colSpan="10" />
        </msh:row>
        <msh:row>
          <msh:autoComplete property="patientBloodGroup" label="Группа крови" vocName="vocBloodGroup" horizontalFill="true" />
          <msh:autoComplete property="patientRhesusFactor" label="Резус-фактор" vocName="vocRhesusFactor"  />
        </msh:row>
        </msh:panel>
        <msh:panel colsWidth="1%,5%,1%,1%,1%,1%,1%,1%,1%,1%,1%,100%,1%,1%,100%">
        <msh:ifFormTypeIsNotView formName="trans_bloodForm">
        <msh:row>
        	<msh:textField viewOnlyField="true" property="phenotype" label="Фенотип" size="20" />
        	<msh:checkBox property="phenotypeC" label="C"/>
        	<msh:checkBox property="phenotypec1" label="c"/>
        	<msh:checkBox property="phenotypeD" label="D"/>
        	<msh:checkBox property="phenotypeE" label="E"/>
        	<msh:checkBox property="phenotypee1" label="e"/>
            <msh:checkBox property="phenotypeNone" label="Не опр"/>
        </msh:row>
        </msh:ifFormTypeIsNotView>
        </msh:panel>
        <msh:panel>

        <msh:row>
        	<msh:autoComplete property="definitionRhesus" vocName="vocTransfusionDefinitionRhesus" label="Определение резус-принад. рециента производилось" labelColSpan="2" fieldColSpan="2" horizontalFill="true"/>
        </msh:row>
        <msh:row>
        	<msh:autoComplete property="researchAntibodies" label="Исследование антител" vocName="vocTransfusionResearchAntibodies"/>
        	<msh:textField property="antibodiesList" size="100" fieldColSpan="3" horizontalFill="true" label="Выявленные антитела"/>
        </msh:row>
        <msh:row>
        	<msh:autoComplete property="transfusionHistory" label="Были переливания" vocName="vocYesNo"/>
        	<msh:autoComplete property="personalTransfusionHistory" label="Были трансфизии по инд.подбору" vocName="vocYesNo"/>
        </msh:row>
        <msh:row>
        	<msh:autoComplete property="transfusionReactionLast" label="Были реакции осложнения на переливания" vocName="vocYesNo" labelColSpan="3"/>
        </msh:row>
        <msh:row>
        	<msh:textField property="countPregnancy" label="Кол-во беременностей"/>
        	<msh:autoComplete property="pregnancyHang" label="Особенности течения" vocName="vocTransfusionPregnancyHang" horizontalFill="true"/>
        </msh:row>
        <msh:row>
        	<msh:autoComplete  property="macroBall" label="Макроскопическая оценка: пригодна к переливанию" vocName="vocYesNo" labelColSpan="3"/>
        </msh:row>
        </msh:panel>
        <msh:panel>
        <msh:row>
          <msh:separator label="Данные с этикетки контейнера" colSpan="10" />
        </msh:row>
        <msh:row>
          <msh:autoComplete property="bloodPreparation" label="Наименование компонента крови" horizontalFill="true" vocName="vocBloodPreparation" fieldColSpan="5" />
        </msh:row>
        <msh:row>
          <msh:autoComplete vocName="vocPreparatorBlood" property="preparator" label="Изготовитель" horizontalFill="true" fieldColSpan="1" size="35"/>
          <msh:textField property="preparationDate" label="Дата заготовки" />
          <msh:textField property="expirationDate" label="Срок годности" />
        </msh:row>
        <msh:row>
          <msh:textField property="containerNumber" label="№ контейнера" size="35"/>
          <msh:textField property="doze" label="Объем"/>
        </msh:row>
        <msh:row>
        </msh:row>
        <msh:row>
          <msh:textField property="donor" label="Код донор" horizontalFill="true" size="35"/> 
          <msh:autoComplete property="preparationBloodGroup" label="Группа крови" vocName="vocBloodGroup" horizontalFill="true" />
          <msh:autoComplete property="preparationRhesusFactor" label="Резус-фактор" vocName="vocRhesusFactor" horizontalFill="true" />
        </msh:row>
        </msh:panel>
        <msh:panel>
        <msh:row>
          <msh:separator label="Информация о контрольных проверках" colSpan="10" />
        </msh:row>
        <msh:row>
          <msh:autoComplete property="patBloodGroupCheck" label="Группа крови реципиента" vocName="vocBloodGroup" horizontalFill="true" />
          <msh:autoComplete property="prepBloodGroupCheck" label="Группа крови донора" vocName="vocBloodGroup" horizontalFill="true" />
        </msh:row>
        <msh:row>
          <msh:separator label="Информация о реактивах, использовавшихся при определении показателей" colSpan="10"/>
        </msh:row>
        <msh:row>
        	<msh:autoComplete property="reagentForm1.reagent" label="Реактив 1" vocName="vocTransfusionReagent" fieldColSpan="3" horizontalFill="true"/>
        	<msh:textField property="reagentForm1.series" label="Серия"/>
        	<msh:textField property="reagentForm1.expirationDate" label="Срок годности"/>
        </msh:row>
        <msh:row>
        	<msh:autoComplete property="reagentForm2.reagent" label="Реактив 2" vocName="vocTransfusionReagent" fieldColSpan="3" horizontalFill="true"/>
        	<msh:textField property="reagentForm2.series" label="Серия"/>
        	<msh:textField property="reagentForm2.expirationDate" label="Срок годности"/>
        </msh:row>
        <msh:row>
          <msh:separator label="Проведенные пробы на индивидуальную совместимость (для эритроцитосодержащих компонентов)" colSpan="10"/>
        </msh:row>
        <msh:row>
        	<msh:autoComplete fieldColSpan="9" horizontalFill="true" vocName="vocTransfusionMethodPT" property="methodPT1" label="1. Метод"/>
        </msh:row>
        <msh:row>
        	<msh:autoComplete fieldColSpan="9" horizontalFill="true" property="methodPT2" vocName="vocTransfusionMethodPT" label="2. Метод"/>
        </msh:row>
        <msh:row>
        	<msh:textField property="reagentSeriesPT2" label="Серия"/>
        	<msh:textField property="reagentExpDatePT2" label="Срок годности"/>
        	<msh:autoComplete vocName="vocYesNo" property="resultGoodPT2" label="Совместима?"/>
        </msh:row>
         </msh:panel>
        <msh:panel>
        <msh:row>
          <msh:separator label="Биологическая проба" colSpan="10"/>
        </msh:row>
        <!--msh:ifFormTypeIsNotView formName="trans_bloodForm"-->
        <msh:row>
        	<msh:checkBox property="isIllPatientsBT" label="Пациент находится под наркозом или в коме" fieldColSpan="9" horizontalFill="true"/>
        </msh:row>
            <msh:row>
                <msh:autoComplete vocName="vocBloodBioProbProcedure" horizontalFill="true" property="bloodBioProbProcedure" label="Переливание:" fieldColSpan="9"/>
            </msh:row>
        <msh:row styleId="row10"><td colspan="10"></td></msh:row>
        <msh:row styleId="row11">
        	<msh:textField property="pulseRateBT" size="4" label="PS"/>
        	<msh:textField property="bloodPressureTopBT" size="4" label="AD сис."/>
        	<msh:textField property="bloodPressureLowerBT" size="4" label="AD дис."/>
        	<msh:textField property="respiratoryRateBT" size="4" label="ЧДД"/>
        	<msh:textField property="temperatureBT" size="4" label="t"/>
        </msh:row>
        <msh:row styleId="row12">
           	<msh:autoComplete size="4" property="stateBT"  vocName="vocYesNo" label="Состояние изменилось?" />
           	<msh:textField property="lamentBT" label="Жалобы" fieldColSpan="9" horizontalFill="true"/>
        </msh:row>
            <msh:row styleId="row20">
                <msh:row styleId="row22"><td colspan="10">Изменилось без видимой причины:</td></msh:row>
                <msh:row styleId="row23">
                    <msh:checkBox property="wasBleedingIncreased" label="Кровотечение усилилось"/>
                    <msh:checkBox property="wasADDecreased" label="АД снизилось"/>
                    <msh:checkBox property="wasPulseIncreased" label="Пульс участился"/>
                    <msh:checkBox property="wasUrineColorChanged" label="Цвет мочи изменился"/>
                </msh:row>
            </msh:row>
        <msh:ifFormTypeIsView formName="trans_bloodForm">
        <msh:row>
        	<msh:label property="biologicTest" label="Описание" fieldColSpan="9"/>
        </msh:row>
        </msh:ifFormTypeIsView>
        <msh:row>
        	<msh:checkBox label="Переливание прекращено" fieldColSpan="9" property="isBreakBT"/>
        </msh:row>
        <msh:row>
          <msh:separator label="Информация о переливании" colSpan="10" />
        </msh:row>
        <msh:row>
          <msh:autoComplete property="reason" label="Показания к применению" parentAutocomplete="bloodPreparation" vocName="vocTransfusionReason" fieldColSpan="9" horizontalFill="true" />
        </msh:row>
        <msh:row>
          <msh:autoComplete property="transfusionMethod" label="Способ переливания" vocName="vocTransfusionMethod" horizontalFill="true" fieldColSpan="9" />
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
  	eventutil.addEventListener($('isIllPatientsBT'), 'click', biologTest) ;
    bloodPreparationAutocomplete.addOnChangeCallback(function() {
        checkMasReq() ;
    }) ;
  	function phenotype() {
  		$('phenotype').value="C"+check("phenotypeC")+"c"+check("phenotypec1")+"D"+check("phenotypeD")+"E"+check("phenotypeE")+"e"+check("phenotypee1") + "Не опр"+check("phenotypeNone");
  		$('phenotypeReadOnly').value=$('phenotype').value ;
  	}
  	function check(aField) {
  		var fld = $(aField).checked ;
  		if (fld==true) {return "+";} else {return "-" ;}
  	}
  	function biologTest() {
  		if ($("isIllPatientsBT").checked==true) {
  			try { $("row20").style.display = 'table-row' ;$("row22").style.display ='table-row';$("row23").style.display ='table-row' ;
  			$("row10").style.display = 'none' ;$("row11").style.display = 'none' ;$("row12").style.display = 'none' ; 
  			} catch (e) { $("row20").style.display = 'block' ; $("row10").style.display = 'none'; $("row11").style.display = 'none'; $("row12").style.display = 'none' ;}
  		} else {
  			try { $("row20").style.display ='none'; $("row22").style.display ='none';$("row23").style.display ='none';$("row10").style.display = 'table-row' ; $("row11").style.display = 'table-row' ; $("row12").style.display = 'table-row' ;
  			$("row20").style.display = 'none' ;
  			} catch (e) { $("row10").style.display = 'block' ; $("row11").style.display = 'block' ; $("row12").style.display = 'block' ;
  			$("row20").style.display ='none'; $("row22").style.display ='none';$("row23").style.display ='none' ;}
            //alert($("row20").style.display);
  		}
  	}

    //если поле prop имеет значение из vals или не имеет значение из notVals, все поля в req сделать обязательными
    //если другое значение - убрать обязательность, снимать значения не надо
    var masReq = [{prop: 'bloodPreparation', vals:[1,2,3,4,5,6,7,8,9,10,11,12,13,14,15], notVals: [], req: [{field: 'patBloodGroupCheckName', cmnt: 'группа крови реципиента'}
            ,{field: 'reagentForm1.reagentName',cmnt: 'реактив 1'}
            ,{field: 'reagentForm1.series',cmnt: 'серия 1'}
            ,{field: 'reagentForm1.expirationDate',cmnt: 'срок годности 1'}
            ,{field: 'reagentForm2.reagentName',cmnt: 'реактив 2'}
            ,{field: 'reagentForm2.series',cmnt: 'серия 2'}
            ,{field: 'reagentForm2.expirationDate',cmnt: 'срок годности 2'}]}
        , {prop: 'bloodPreparation', vals:[1,2,3,4,5,6,7,8,9,10,11,12,13,14], notVals: [], req: [{field: 'bloodBioProbProcedureName', cmnt: 'биологическая проба'}]}];

    //ф-я настраивает обязательность полей req в masReq
    function checkMasReq() {
        for (var i=0; i<masReq.length; i++) {
            var el = masReq[i];
            if ($(el.prop).value!='' && (el.vals.length>0 && el.vals.indexOf(+$(el.prop).value)!=-1 || el.notVals.length>0 && el.notVals.indexOf(+$(el.prop).value)==-1)) {
                el.req.forEach(function (reqMasEl) {
                    $(reqMasEl.field).className += " required"
                });}
            else {
                el.req.map(function (reqMasEl) {
                    $(reqMasEl.field).className = $(reqMasEl.field).className.replace(new RegExp("required", "g"), "");
                    //$(fieldName).value = '';
                });
            }
        }
    }

    //ф-я проверяет заполнение masReq
    function checkSaveMasReq() {
        var msg = '';
        for (var i = 0; i < masReq.length; i++) {
            var el = masReq[i];
            if ($(el.prop).value!='' && (el.vals.length>0 && el.vals.indexOf(+$(el.prop).value)!=-1 || el.notVals.length>0 && el.notVals.indexOf(+$(el.prop).value)==-1)) {
                el.req.forEach(function (reqMasEl) {
                    if ($(reqMasEl.field).value=='') {
                        if (!msg)
                            msg = 'Проверьте заполнение следующих полей:';
                        msg+=' ' + reqMasEl.cmnt + ';';
                    }
                });
            }
        }
        return msg;
    }
  	biologTest();
  	phenotype();
    checkMasReq();

    //проверка и сохранение
    function save(btn) {
        btn.value='Создание...';
        var msg = checkSaveMasReq();
        if (msg) {
            showToastMessage(checkSaveMasReq(),null,true);
            btn.removeAttribute("disabled");
            btn.value='Создать';
        }
        else
            document.forms[0].submit();
    }
  	</script>
  	</msh:ifFormTypeIsNotView>
      <script type="text/javascript">
          //Milamesher 08112018 - выпадающий список вместо текстового поля
          function addReagentCmb() {
              var row=document.getElementById('reagentSeriesPT2Label').parentNode.parentNode;
              var div = document.createElement('div'); var tdID = document.createElement('td');

              var new_comboBox = document.createElement('select');
              var option_0 = document.createElement('option');
              option_0.setAttribute('value', '');
              var txt_0 = document.createTextNode("");
              option_0.appendChild(txt_0);

              var option_1 = document.createElement('option');
              option_1.setAttribute('value', 'Полиглюкин 33%');
              var txt_1 = document.createTextNode("Полиглюкин 33%");
              option_1.appendChild(txt_1);

              var option_2 = document.createElement('option');
              option_2.setAttribute('value', 'Желатин');
              var txt_2 = document.createTextNode("Желатин");
              option_2.appendChild(txt_2);

              var option_3 = document.createElement('option');
              option_3.setAttribute('value', 'Гелевая карта');
              var txt_3 = document.createTextNode("Гелевая карта");
              option_3.appendChild(txt_3);

              new_comboBox.appendChild(option_0);
              new_comboBox.appendChild(option_1);
              new_comboBox.appendChild(option_2);
              new_comboBox.appendChild(option_3);
              div.appendChild(document.createTextNode("Реактив:"));
              new_comboBox.id='rg1';
              onchange = function onchangeCmb() {
                  $('reagentPT2').value=document.getElementById('rg1').options[document.getElementById('rg1').selectedIndex].text;
              };
              <msh:ifFormTypeAreViewOrEdit formName="trans_bloodForm">
                new_comboBox.value=$('reagentPT2').value;
              <msh:ifFormTypeIsView formName="trans_bloodForm">
                new_comboBox.setAttribute('disabled',true);
              </msh:ifFormTypeIsView>
            </msh:ifFormTypeAreViewOrEdit>
              <msh:ifFormTypeIsCreate formName="trans_bloodForm">
                $('reagentPT2').value='';
              </msh:ifFormTypeIsCreate>
              div.appendChild(new_comboBox);tdID.appendChild(div);
              row.appendChild(tdID);
              //Перемещение столбца в строке
              jQuery.moveColumn = function (row, from, to) {
                  var cols;
                  cols = jQuery(row).children('th, td');
                  cols.eq(from).detach().insertBefore(cols.eq(to));
              };
              jQuery.moveColumn(document.getElementById('reagentSeriesPT2Label').parentNode.parentNode,6,0);
          }
          addReagentCmb();
      </script>
  </tiles:put>
</tiles:insert>

