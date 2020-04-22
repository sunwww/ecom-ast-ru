<%@page import="ru.ecom.mis.ejb.form.medcase.hospital.SurgicalOperationForm"%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://struts.apache.org/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://www.nuzmsh.ru/tags/msh" prefix="msh" %>
<%@ taglib uri="http://www.ecom-ast.ru/tags/ecom" prefix="ecom" %>
<%@ taglib tagdir="/WEB-INF/tags" prefix="tags" %>
<%@ taglib uri="/WEB-INF/mis.tld" prefix="mis" %>

<tiles:insert page="/WEB-INF/tiles/mainLayout.jsp" flush="true">
	<tiles:put name="style" type="string">
        <style type="text/css">            
            .protocols {
				left:0px;  width:60em; 
				top:0px;  height:55em;
				overflow: auto;
			}
			.operationText {
			width:100%;
			}
			.histologicalStudy {
			width:100%;
			}
        </style>

    </tiles:put>
  <tiles:put name="body" type="string">
    <!-- 
    	  - Хирургическая операция
    	  -->
    	  <%
        	SurgicalOperationForm frm = (SurgicalOperationForm)request.getAttribute("stac_surOperationForm") ;
        	request.setAttribute("medcase", frm.getMedCase()) ;
        	
        %>
    <msh:form action="/entityParentSaveGoSubclassView-stac_surOperation.do" defaultField=""  editRoles="/Policy/Mis/MedCase/Stac/Ssl/SurOper/Edit" createRoles="/Policy/Mis/MedCase/Stac/Ssl/SurOper/Create">
      <msh:panel  colsWidth="15px,250px,15px">
        <msh:hidden property="id"/>
        <msh:hidden property="patient"/>
        <msh:hidden property="saveType"/>
        <msh:hidden property="medCase"/>
        <msh:hidden property="lpu"/>
          <msh:hidden property="allComps"/>
        <msh:ifNotInRole roles="/Policy/Mis/MedCase/Stac/Ssl/ShortEnter">
        <msh:row>
          <msh:textField property="numberInJournal" label="Номер протокола"  labelColSpan="1" fieldColSpan="3"/>
        </msh:row>
        <msh:separator label="Сведения до операции" colSpan="5"/>
        <msh:row>
          <msh:autoComplete property="idc10Before" label="МКБ до операции"  fieldColSpan="3" horizontalFill="true" vocName="vocIdc10"/>
        </msh:row>
        </msh:ifNotInRole>
        <msh:separator label="Сведения об операции" colSpan="5"/>
 	        <msh:row>
	          <msh:textField property="operationDate" label="Начало дата"/>
	          <msh:textField property="operationTime" label="время"/>
	        </msh:row>
	        <msh:row>
	          <msh:textField property="operationDateTo" label="Окончание дата"/>
	          <msh:textField property="operationTimeTo" label="время"/>
	        </msh:row>
        <msh:row>
          <msh:autoComplete property="department" label="Отделение"  fieldColSpan="3" horizontalFill="true" vocName="vocLpuOtd"/>
        </msh:row>
        <msh:row>
          <msh:autoComplete property="serviceStream" label="Поток обслуживания" fieldColSpan="3" horizontalFill="true" vocName="vocServiceStream"/>
        </msh:row>
        <msh:row>
        	<msh:autoComplete label="Хирург" property="surgeon" horizontalFill="true" fieldColSpan="3" vocName="workFunctionIsSurgical"/>
        </msh:row>
        <msh:ifFormTypeIsView formName="stac_surOperationForm">
        <msh:row>
          <msh:autoComplete property="operation" label="Операция" size="60"  fieldColSpan="3" horizontalFill="true" vocName="vocOperation"/>
        </msh:row>
        </msh:ifFormTypeIsView>
        <msh:row>
          <msh:autoComplete property="medService" label="Операция (услуга)" size="60" fieldColSpan="3" horizontalFill="true" vocName="medServiceOperation"/>
	        </msh:row>
        <mis:ifPatientIsWoman classByObject="MedCase" idObject="${medcase}">

        <msh:row>
        <msh:autoComplete property="profile" label="Профиль"  horizontalFill="true" vocName="vocSurgicalProfile"/>
        <msh:autoComplete property="method" label="Метод"  horizontalFill="true" vocName="vocOperationMethod"/>
        </msh:row>

        <msh:row>
   		<msh:autoComplete property="abortion" vocName="vocAbortationByProfile" parentAutocomplete="profile" fieldColSpan="3" horizontalFill="true" label="Тип аборта"/>
       	</msh:row>
        </mis:ifPatientIsWoman>
        <msh:ifInRole roles="/Policy/Mis/MedCase/Stac/Ssl/ShortEnter">
        	<msh:hidden property="surgeonFunctions"/>
        	<msh:hidden property="complications"/>

        	<msh:hidden property="operatingNurse"/>

        	<msh:hidden property="operationText"/>
        	<msh:hidden property="aspect"/>
        	<msh:hidden property="technology"/>
        </msh:ifInRole>


          <msh:separator label="Периоперационная антибиотикопрофилактика" colSpan="5"/>
          <msh:row>
              <td class="label" title="Поиск по промежутку  (antibio)" colspan="1"><label for="antibioName" id="tantibioLabel">Периоперационная антибиотикопрофилактика?</label></td>
              <td onclick="this.childNodes[1].checked='checked'; checkАntibioRb();" colspan="1">
                  <input type="radio" name="antibio" value="1"> Нет
              </td>
              <td onclick="this.childNodes[1].checked='checked'; checkАntibioRb();" colspan="2">
                  <input type="radio" name="antibio" value="2"> Да
              </td>
          </msh:row>
          <msh:row>
              <msh:autoComplete property="classWound" label="Класс раны"  fieldColSpan="3" horizontalFill="true" vocName="vocClassWound"/>
          </msh:row>
          <msh:row>
              <msh:autoComplete property="antibioticDrug" label="Препарат"  fieldColSpan="3" horizontalFill="true" vocName="vocAntibioticDrug"/>
          </msh:row>
          <msh:row>
              <msh:textField property="dose" label="Доза"/>
          </msh:row>
          <msh:row>
              <msh:autoComplete property="methodsDrugAdm" label="Путь введения"  fieldColSpan="3" horizontalFill="true" vocName="vocMethodsDrugAdm"/>
          </msh:row>
          <msh:row>
              <msh:textField property="firstDoseTime" label="Время первой дозы"/>
              <msh:textField property="secondDoseTime" label="Время повторной дозы (при необходимости)"/>
          </msh:row>
          <msh:separator label="Протокол" colSpan="5"/>
        <msh:ifNotInRole roles="/Policy/Mis/MedCase/Stac/Ssl/ShortEnter">
        <msh:row>
          <msh:textArea rows="6" hideLabel="false" property="operationText" viewOnlyField="false"  fieldColSpan="3" label="Протокол операции"/>
        </msh:row>
        <msh:row>
          <msh:ifFormTypeIsNotView formName="stac_surOperationForm">
            <td colspan="4" align="center">
              <input type="button" value="Шаблон" onClick="showOperationTextTempTemplateProtocol()"/>
              <input type="button" id="changeSizeEpicrisisButton" value="Увеличить" onclick="changeSizeEpicrisis()">
            </td>
          </msh:ifFormTypeIsNotView>
        </msh:row>
        </msh:ifNotInRole>
        <msh:row>
          <msh:autoComplete horizontalFill="true" property="aspect" label="Показания" vocName="vocHospitalAspect"/>
          <msh:autoComplete horizontalFill="true" vocName="vocOperationTechnology" property="technology" label="С испол. ВМТ"/>
        </msh:row>

        <msh:ifNotInRole roles="/Policy/Mis/MedCase/Stac/Ssl/SurOper/HideCheckBox">
	       <msh:row>
	          <msh:checkBox property="base" label="Основная"  fieldColSpan="1"/>
	          <msh:checkBox property="minor" label="Малая операция"/>
	       </msh:row>
        </msh:ifNotInRole>
      <msh:ifNotInRole roles="/Policy/Mis/MedCase/Stac/Ssl/ShortEnter">

        <msh:row>
          <ecom:oneToManyOneAutocomplete colSpan="3" label="Ассистенты" property="surgeonFunctions" vocName="workFunctionIsSurgical"/>
        </msh:row>
        <msh:row>
          <msh:autoComplete property="operatingNurse" label="Опер. медсестра"  fieldColSpan="3" horizontalFill="true" vocName="workFunctionIsInstrumentNurse"/>
        </msh:row>
        </msh:ifNotInRole>

        <msh:ifFormTypeIsCreate formName="stac_surOperationForm">
        		<msh:separator label="Анестезия" colSpan="5"/>
	        <msh:row>
	          <msh:autoComplete property="isAnesthesia" label="Анестезия проводилась?" horizontalFill="true" vocName="vocYesNo" fieldColSpan="3"/>
	        </msh:row>
	        <msh:row>
	          <msh:autoComplete property="anesthesia" label="Метод" horizontalFill="true" vocName="vocAnesthesiaMethod" fieldColSpan="3"/>
	        </msh:row>
	        <msh:row>
	          <msh:autoComplete property="anesthesiaService" label="Услуга" horizontalFill="true" vocName="medServiceAnesthesia" fieldColSpan="3"/>
	        </msh:row>
	        <msh:row>
	          <msh:autoComplete property="anesthesiaType" label="Вид" horizontalFill="true" vocName="vocAnesthesia" fieldColSpan="3"/>
	        </msh:row>
	        <msh:row>
	          <msh:textField property="anesthesiaDuration" label="Длительность (мин)"  fieldColSpan="3"/>
	        </msh:row>
	        <msh:row>
	          <msh:autoComplete property="anaesthetist" label="Анестезиолог" vocName="workFunction" fieldColSpan="3" horizontalFill="true"/>
	        </msh:row>
        </msh:ifFormTypeIsCreate>
         <msh:ifNotInRole roles="/Policy/Mis/MedCase/Stac/Ssl/SurOper/HideCheckBox">
        <msh:separator label="Использование специальной аппаратуры" colSpan="5"/>
	        <msh:row>
	          <msh:checkBox property="endoscopyUse" label="Эндоскопия"/>
	          <msh:checkBox property="laserUse" label="Лазерная аппаратура"/>
	        </msh:row>
	        <msh:row>
	          <msh:checkBox property="cryogenicUse" label="Криогенная аппаратура"   fieldColSpan="3"/>
	        </msh:row>
        <msh:separator label="Сведения после операции" colSpan="5"/>
        <msh:row>
          <msh:textArea hideLabel="false" property="histologicalStudy" viewOnlyField="false" label="Гистол. исследование"  fieldColSpan="3" horizontalFill="true" rows="6"/>
        </msh:row>
        <msh:row>
          <msh:ifFormTypeIsNotView formName="stac_surOperationForm">
            <td colspan="4" align="center">
              <input type="button" value="Шаблон" onClick="showHistologicalStudyTempTemplateProtocol()"/>
              <input type="button" id="changeSizeHistButton" value="Увеличить" onclick="changeSizeHist()">
            </td>
          </msh:ifFormTypeIsNotView>
        </msh:row>

        <msh:row>
          <msh:autoComplete property="idc10After" label="МКБ после операции"  fieldColSpan="3" horizontalFill="true" vocName="vocIdc10"/>
        </msh:row>
        <msh:row>
          <msh:autoComplete property="outcome" label="Исход операции" fieldColSpan="3" horizontalFill="true" vocName="vocOperationOutcome"/>
        </msh:row>
        </msh:ifNotInRole>
          <msh:row>
              <msh:autoComplete property="leftRight" label="Сторона (для парных органов)" fieldColSpan="3" horizontalFill="true" vocName="vocLeftRight"/>
          </msh:row>
                <msh:row>
        	<msh:label property="createDate" label="Дата создания"/>
          <msh:label property="createUsername" label="пользователь"/>
        </msh:row>
        <msh:row>
        	<msh:label property="editDate" label="Дата редак."/>
          	<msh:label property="editUsername" label="пользователь"/>
        </msh:row>
        <msh:row>
        	<msh:label property="printDate" label="Дата печати"/>
        	<msh:hidden property="printTime"/>
        	<msh:label property="printUsername" label="пользователь"/>
        </msh:row>
      </msh:panel>
        <div id="compDiv"></div>
        <div>
            <a id='noteH' href="#bottom" onclick="showNote();">Показать примечание</a><br>
            <div id="note" style="display:none;">
                <b>Класс I: чистая</b>: неинфицированная послеоперационная рана при отсутствии воспаления, при этом не затрагивались дыхательный,
                пищеварительный, половой или неинфицированный мочевыводящий тракты. Чистые раны закрываются первичным натяжением и в случае необходимости
                дренируются с помощью закрытого дренажа.<br>
                <b>Класс II: условно-чистая («чисто – контаминированная»)</b>: послеоперационная рана, затрагивающая дыхательный, пищеварительный, половой
                или мочевыводящий тракты в контролируемых условиях и без необычной контаминации.<br>
                <b>Класс III: контаминированная («загрязненная»)</b>: открытые, свежие, травматические раны. Кроме того, в эту категорию включены операции
                со значительными нарушениями асептики (например, открытый массаж сердца), или сопровождающиеся выраженной утечкой содержимого
                желудочно-кишечного тракта, а также операции, при которых наблюдается острое негнойное воспаление.<br>
                <b>Класс IV: «грязная» (инфицированная)</b>: старые травматические раны с нежизнеспособными тканями, а также послеоперационные раны,
                в области которых уже имелась инфекция или произошла перфорация кишечника. Подразумевается, что микроорганизмы, способные вызвать ИОХВ,
                присутствовали в области оперативного вмешательства до операции.<br>
            </div>
        </div>
        <msh:ifFormTypeIsNotView formName="stac_surOperationForm">
        <table><tbody><tr><td class="buttons" colspan="3">
            <input id="cancelButton" type="button" onclick="this.disabled=true; window.history.back()" title="Отменить изменения [SHIFT+ESC]" value="Отменить">
            <input id="submitButton" class="default" type="button" value="Сохранить изменения     " onclick="this.value='Сохранение изменений ...'; this.disabled=true; save();  return true ;" title="Сохранить изменения      [CTRL+ENTER]" autocomplete="off"></td>
        </tr></tbody></table>
        </msh:ifFormTypeIsNotView>
    </msh:form>
    <msh:ifFormTypeIsNotView formName="stac_surOperationForm">
      <tags:templateProtocol property="histologicalStudy" name="HistologicalStudyTemp"
      idSmo="stac_surOperationForm.medCase" version="Visit" voc="protocolVisitByPatient"
   />
      <tags:templateProtocol property="operationText" name="OperationTextTemp"
      idSmo="stac_surOperationForm.medCase" version="Visit" voc="protocolVisitByPatient"
   />
    </msh:ifFormTypeIsNotView>
    <msh:ifFormTypeIsView formName="stac_surOperationForm">
      <msh:ifInRole roles="/Policy/Mis/MedCase/Stac/Ssl/SurOper/Anesthesia/View">
        <ecom:parentEntityListAll attribute="anesthesies" formName="stac_anesthesiaForm"/>
        <msh:tableNotEmpty name="anesthesies">
          <msh:section title="Анестезия">
            <msh:table name="anesthesies" action="entityParentView-stac_anesthesia.do" idField="id">
              <msh:tableColumn columnName="Анестезиолог" property="anesthesistInfo"/>
              <msh:tableColumn columnName="Длительность (мин)" property="duration"/>
              <msh:tableColumn columnName="Метод" property="methodInfo"/>
            </msh:table>
          </msh:section>
        </msh:tableNotEmpty>
      </msh:ifInRole>
    </msh:ifFormTypeIsView>
    <msh:ifFormTypeIsNotView formName="stac_surOperationForm">
    	<tags:mis_double name='MedService' title='Данная операция оказана:' cmdAdd="document.forms[0].submitButton.disabled = false "/>
    	<tags:service_change name="ServiceChange" autoCompliteServiceFind="medService"/>
    </msh:ifFormTypeIsNotView>


    </tiles:put>
  <tiles:put name="title" type="string">
    <ecom:titleTrail mainMenu="StacJournal" beginForm="stac_surOperationForm"/>
  </tiles:put>
  <tiles:put name="side" type="string">
    <msh:ifFormTypeIsView formName="stac_surOperationForm">
      <msh:sideMenu title="Хир. операция">
        <msh:sideLink key="ALT+2" params="id" action="/entityParentEdit-stac_surOperation" name="Изменить" roles="/Policy/Mis/MedCase/Stac/Ssl/SurOper/Edit"/>
        <msh:sideLink key="ALT+DEL" confirm="Удалить?" params="id" action="/entityParentDeleteGoSubclassView-stac_surOperation" name="Удалить" roles="/Policy/Mis/MedCase/Stac/Ssl/SurOper/Delete"/>
        <msh:sideLink key="ALT+3" params="id" action="/entityParentListRedirect-stac_surOperation" name="⇧Cписок операций" roles="/Policy/Mis/MedCase/Stac/Ssl/SurOper/View"  title="Перейти к списку операций"/>
      </msh:sideMenu>
      <msh:sideMenu title="Добавить">
        <msh:sideLink key="CTRT+1" params="id" action="/entityParentPrepareCreate-stac_anesthesia" name="Анестезию" roles="/Policy/Mis/MedCase/Stac/Ssl/SurOper/Anesthesia/View"  title="Добавить анестезию"/>
      </msh:sideMenu>
      <msh:sideMenu title="Печать">
      	<msh:sideLink key="CTRL+2" params="id" action="/print-surgicalOperation.do?m=printSurOperation&s=HospitalPrintService" name="Протокола операции"/>
      	<msh:sideLink key="CTRL+3" params="id" action="/print-surgicalOperation2.do?m=printSurOperation&s=HospitalPrintService" name="Протокола операции 2 копии"/>
      </msh:sideMenu>
    </msh:ifFormTypeIsView>
  </tiles:put>

  <tiles:put name="javascript" type="string">
      <script type="text/javascript">
          //массив хранит имя элемента и то, обязателен ли он (req). Поле desc - описание, check - проверять заполнение при смене препарата
          var masAntibioElements = [{name:'classWoundName',req:true,desc:'класс раны'},{name:'antibioticDrugName',req:true,desc:'препарат'},
              {name:'dose',req:true, desc:'доза',check:true},{name:'methodsDrugAdm',req:true,check:true},
              {name:'methodsDrugAdmName',req:true,desc:'путь введения',check:true},{name:'firstDoseTime',req:true,desc:'первая доза',check:true},
              {name:'secondDoseTime',req:false,check:true},{name:'classWound',req:false,desc:'класс раны'},{name:'antibioticDrug',req:true,check:true}];

          //ф-я проверяет, выбрали ли препарат (или выбрано 'Нет') и делает доступными/недоступными элементы из массива объектов masAntibioElements
          function checkDrugRequired(checkEmpty) { //checkEmpty - обнулять ли значения
              var disabled=$('antibioticDrugName').value.trim()=='Нет' || $('antibioticDrugName').value.trim()=='';
              for (var m in masAntibioElements) {
                  var el = masAntibioElements[m];
                  if (!el.check) continue;
                  if (typeof el == 'function') break;
                  $(el.name).disabled=disabled;
                  if (!disabled && el.req)
                      $(el.name).className += " required";
                  else if (checkEmpty) {
                      $(el.name).className = $(el.name).className.replace(new RegExp("required", "g"), "");
                      $(el.name).value='';
                  }
              }
          }

          //показать/скрыть примечание
          function showNote() {
              var note = document.getElementById("note");
              var href = document.getElementById("noteH");
              if (note.style.display=="none") {
                  note.style.display="block";
                  jQuery(href).text("Скрыть примечание");
              }
              else {
                  note.style.display="none";
                  jQuery(href).text("Показать примечание");
              }
          }
          //работа с радиобатонами
          function checkАntibioRb() {
              var disabled = jQuery('[name="antibio"]')[0].checked;
              for (var m in masAntibioElements) {
                  var el = masAntibioElements[m];
                  if (typeof el == 'function') break;
                  $(el.name).disabled=disabled;
                  if (!disabled && el.req)
                      $(el.name).className += " required";
                  else {
                      $(el.name).className = $(el.name).className.replace(new RegExp("required", "g"), "");
                      $(el.name).value='';
                  }
                  checkDrugRequired(1);
              }
          }
      </script>
      <msh:ifFormTypeAreViewOrEdit formName="stac_surOperationForm">
      <script type="text/javascript" src="./dwr/interface/HospitalMedCaseService.js"></script>
      <script type="text/javascript">
          //проставить радиобаттоны Да-нет в операции при просмотре/редактировании
          function checkOnloadRb() {
                var el=null;
                <msh:ifFormTypeIsView formName="stac_surOperationForm">
                el=jQuery('#classWoundReadOnly')[0];
                jQuery('[name="antibio"]')[0].disabled=jQuery('[name="antibio"]')[1].disabled=true;
                jQuery('[name="antibio"]')[0].parentNode.onclick=jQuery('[name="antibio"]')[1].parentNode.onclick=function() {};
                </msh:ifFormTypeIsView>
                <msh:ifFormTypeIsNotView formName="stac_surOperationForm">
                el=jQuery('#classWoundName')[0];
                </msh:ifFormTypeIsNotView>
                jQuery('[name="antibio"]')[1].checked=el!=null && el.value!='';
                jQuery('[name="antibio"]')[0].checked=!(el!=null && el.value!='');
                <msh:ifFormTypeIsNotView formName="stac_surOperationForm">
                checkАntibioRb();
                </msh:ifFormTypeIsNotView>
          }
          checkOnloadRb();
      </script>
      </msh:ifFormTypeAreViewOrEdit>
    <msh:ifFormTypeIsNotView formName="stac_surOperationForm">
    <script type="text/javascript" src="./dwr/interface/HospitalMedCaseService.js"></script>
    <script type="text/javascript">
    checkDrugRequired();
	var isChangeSizeEpicrisis=1 ;
	var isChangeSizeHist=1 ;
	function changeSizeEpicrisis() {
		if (isChangeSizeEpicrisis==1) {
			Element.addClassName($('operationText'), "protocols") ;
			if ($('changeSizeEpicrisisButton')) $('changeSizeEpicrisisButton').value='Уменьшить' ;
			isChangeSizeEpicrisis=0 ;
		} else {
			Element.removeClassName($('operationText'), "protocols") ;
			if ($('changeSizeEpicrisisButton')) $('changeSizeEpicrisisButton').value='Увеличить' ;
			isChangeSizeEpicrisis=1;
		}
	}
	eventutil.addEventListener($('operationText'), "dblclick",
  		  	function() {
				changeSizeEpicrisis() ;
  		  	}) ;

	function changeSizeHist() {
		if (isChangeSizeHist==1) {
			Element.addClassName($('histologicalStudy'), "protocols") ;
			if ($('changeSizeHistButton')) $('changeSizeHistButton').value='Уменьшить' ;
			isChangeSizeHist=0 ;
		} else {
			Element.removeClassName($('histologicalStudy'), "protocols") ;
			if ($('changeSizeHistButton')) $('changeSizeHistButton').value='Увеличить' ;
			isChangeSizeHist=1;
		}
	}
	eventutil.addEventListener($('histologicalStudy'), "dblclick",
  		  	function() {
				changeSizeHist() ;
  		  	}) ;

    	var oldaction = document.forms[0].action ;
    	document.forms[0].action = 'javascript:isExistSurOperation()';
    	function isExistSurOperation() {

    		HospitalMedCaseService.findDoubleOperationByPatient($('id').value,$('medCase').value,$('medService').value, $('operationDate').value
    		, {
                   callback: function(aResult) {
                   		//alert(aResult);
                      if (aResult) {
				    		showMedServiceDouble(aResult) ;
                       } else {
                       		document.forms[0].action = oldaction ;
				    		document.forms[0].submit() ;
                       }
                   }
	        	}
	        	);
    	}

    	function setEndoscopyUse(){
    		if ($('endoscopyUse')){
	    		HospitalMedCaseService.checkIsEndoscopyMethod($('method').value,{
	    			callback: function (a){
	    				if (+a==1){
	    					$('endoscopyUse').checked=true;
	    				} else {
	    					$('endoscopyUse').checked=false;
	    				}
	    			}
	    		});
    		}
    	}
        //проверка даты-времени начала/окончания
    	function checkDateTime() {
            var flag=false;
    	    //если дата-время окончания необязательны
            if ($('operationDateTo').className.indexOf('required')==-1) {
                if ($('operationDateTo').value == '' && $('operationTimeTo').value == '') //и ничего не заполнено
                    return true;//проверка пройдена
                else if ($('operationDateTo').value != '' && $('operationTimeTo').value == '' ||
                    $('operationDateTo').value == '' && $('operationTimeTo').value != '') {  //если одно из полей не заполнено
                    $('submitButton').disabled = false;
                    showToastMessage('Нельзя заполнить только дату или только время окончания операции!',null,true,true,3000);
                }
                else
                    flag=checkDates(); //если необязатльно, но оба заполнены, проверяем
            }
            else {  //если поля обязательны - проверяем
                flag=checkDates();
            }
            return flag;
        }

        //проверка на то, что дата+время окончания > дата+время начала
        function checkDates() {
            var flag=false;
            if ($('operationDate').value!='' && $('operationTime').value!=''
                && $('operationDateTo').value!='' && $('operationTimeTo').value!='') {
                var date1 = Date.parse($('operationDate').value.replace(".", "/").replace(".", "/") + ' ' + $('operationTime').value);
                var date2 = Date.parse($('operationDateTo').value.replace(".", "/").replace(".", "/") + ' ' + $('operationTimeTo').value);
                if (isNaN(date1) || isNaN(date2)) {
                    date1 = new Date($('operationDate').value.split(".").reverse().join("-") + ' ' + $('operationTime').value);
                    date2 = new Date($('operationDateTo').value.split(".").reverse().join("-") + ' ' + $('operationTimeTo').value);
                }
                if (date2 > date1)
                    flag = true;
                else {
                    $('submitButton').disabled = false;
                    showToastMessage('Дата и время окончания должны быть больше даты и времени начала!', null, true, true, 3000);
                }
            }
            else {
                $('submitButton').disabled = false;
                showToastMessage('Поля дата и время начала и окончания обязательны для заполнения!', null, true, true, 3000);
            }
                return flag;
        }

        //проверка заполнения антибиотикотерапии
    	function checkAntibioticSave() {
    	    $('dose').value = $('dose').value.replace(new RegExp(",","g"),".").replace(/[^.\d]/g, '');
    	    if (jQuery('[name="antibio"]')[1].checked) {
                checkDrugRequired();
                var msg = '';
                for (var m in masAntibioElements) {
                    var el = masAntibioElements[m];
                    if (typeof el == 'function') break;
                    if (el.req && el.desc && $(el.name).value == '') {
                        if (!msg && $(el.name).className.include('required'))
                            msg = 'Проверьте заполнение следующих полей:';
                        if ($(el.name).className.include('required'))
                            msg += ' ' + el.desc + ' ; ';
                    }
                }
                if ($('dose').value<=0)
                    msg+='\nЗначение дозы должно быть строго больше нуля!';
                if (!!msg) {
                    $('submitButton').disabled = false;
                    showToastMessage(msg, null, true);
                }
                else
                    document.forms["mainForm"].submit();
            }
            else if (jQuery('[name="antibio"]')[0].checked)
                document.forms["mainForm"].submit();
    	    else  {
                $('submitButton').disabled = false;
                showToastMessage('Выберите, проводилась ли периоперационная антибиотикопрофилактика!', null, true, false,3000);
            }
        }
    	function save() {
            <msh:ifInRole roles="/Policy/Mis/MedCase/QualityEstimationCard/View">
            saveAllComps();
            </msh:ifInRole>
            if (checkDateTime())
                checkAntibioticSave();
        }

    <msh:ifInRole roles="/Policy/Mis/MedCase/QualityEstimationCard/View">
        //сохранение осложнений
        function saveAllComps() {
            var table = document.getElementById('allCompTble');
            $('allComps').value = "";
            var mas = {
                list: []
            };
            for (var i = 0; i < table.rows.length; i++) {
                var ii = table.rows[i].id.replace('row', '');
                if (typeof $('' + voc1 + ii) !== 'undefined'
                && $('' + voc1 + ii).value!='' && $('dateComp' + ii).value!=''
                    && $('reasonName'+ii).value!='' &&
                    (($(voc1+ii+'Name').value.indexOf('Другое')==0 && $('compName'+ii).value!='') ||
                        ($(voc1+ii+'Name').value.indexOf('Другое')!=0))
                      && $('dateComp'+ii).title.indexOf('Неправильно')==-1) {
                    var compName=$(voc1+ii+'Name').value.indexOf('Другое')==0?
                        $('compName'+ii).value : "";
                    var reasonName=$('reasonName'+ii).value;
                    var obj = {
                        comp: $('' + voc1 + ii).value,
                        compString: compName,
                        reasonString: reasonName,
                        date: $('dateComp' + ii).value
                    };
                    mas.list.push(obj);
                }
            }
            $('allComps').value = JSON.stringify(mas);
        }
    </msh:ifInRole>
        //ф-я проверяет, выбрали ли препарат (или выбрано 'Нет') и делает доступными/недоступными элементы из массива объектов masAntibioElements
        function checkDrugRequired(checkEmpty) { //checkEmpty - обнулять ли значения
    	    var disabled=$('antibioticDrugName').value.trim()=='Нет' || $('antibioticDrugName').value.trim()=='';
    	    for (var m in masAntibioElements) {
    	        var el = masAntibioElements[m];
                if (!el.check) continue;
    	        if (typeof el == 'function') break;
                $(el.name).disabled=disabled;
                if (!disabled && el.req)
                    $(el.name).className += " required";
                else if (checkEmpty) {
                    $(el.name).className = $(el.name).className.replace(new RegExp("required", "g"), "");
                    $(el.name).value='';
                }
            }
        }
    antibioticDrugAutocomplete.addOnChangeCallback(function() {
        checkDrugRequired(1);
    });
    	</script>

  </msh:ifFormTypeIsNotView>

    <msh:ifFormTypeIsNotView formName="stac_surOperationForm">
    	  <msh:ifNotInRole roles="/Policy/Mis/MedCase/Stac/Ssl/ShortEnter">
    	  <script type="text/javascript">
    	  	$('numberInJournal').focus() ;
    	  	$('numberInJournal').select() ;
    	  </script>
    	  </msh:ifNotInRole>
    	  <msh:ifInRole roles="/Policy/Mis/MedCase/Stac/Ssl/ShortEnter">
    	  <script type="text/javascript">
    	  	$('operationDate').focus() ;
    	  	$('operationDate').select() ;
    	  </script>
    	  </msh:ifInRole>
      <script type="text/javascript">
      departmentAutocomplete.setParentId($('lpu').value) ;
  	var oldValue = $('operationDate').value ;
  	try {
  		if (operationAutocomplete) operationAutocomplete.setParentId($('operationDate').value) ;
	} catch(e) {

	}

  	eventutil.addEventListener($('operationDate'), "change",
  	function() {
  		changeParentMedService() ;
  	}) ;
  	eventutil.addEventListener($('operationDate'),'blur',function(){

  		if (oldValue!=$('operationDate').value) {
  			changeParentMedService() ;
  			if ($('operationDateTo').value=="") {
	  			$('operationDateTo').value=$('operationDate').value ;
	  		}
  		}
  	}) ;
  	/*eventutil.addEventListener($('operationDate'),'blur',function(){
  		if (oldValue!=$('operationDate').value) {
  			changeParentMedService() ;

  		}

  	}) ;*/

  	function changeParentMedService() {


  		try {
  		    if (operationAutocomplete) {
  			var oper = $('operation').value ;
  			operationAutocomplete.setParentId($('operationDate').value) ;
  			operationAutocomplete.setVocId(oper) ;
  		    }
  		} catch(e) {

  		}
  		var medService = $('medService').value ;
  		if ($('operationDate').value && $('surgeon').value && $('department').value && $('serviceStream').value) {
            medServiceAutocomplete.setParentId($('operationDate').value+'#'+$('surgeon').value+'#'+$('department').value+'#'+$('serviceStream').value) ;
            medServiceAutocomplete.setVocId(medService) ;
            console.log("parent="+$('operationDate').value+'#'+$('surgeon').value+'#'+$('department').value+'#'+$('serviceStream').value);
        }


  		//$('operationName').value='' ;
  		oldValue = $('operationDate').value ;
  	}
  	serviceStreamAutocomplete.addOnChangeCallback(function() {changeParentMedService();})
  	 surgeonAutocomplete.addOnChangeCallback(function() {changeParentMedService() ;});
  	 departmentAutocomplete.addOnChangeCallback(function() {changeParentMedService() ;});

  	try {
  		methodAutocomplete.addOnChangeCallback(function() {
  	  		setEndoscopyUse() ;
  	  		});
  	} catch(e) {}

  	//Обязательны ли дата-время окончания операции
  	function checkIfDateTimeRequired() {
        //ф-я проверки, есть ли браслет у услуги.
        //Если есть - дата-время окончания необязательный
        //Если нет - дата-время окончания обязательны
        if ($('medService').value != '') {
            HospitalMedCaseService.isMedServiceGotBracelet($('medService').value, {
                callback: function (isGotBracelet) {
                    if (false == isGotBracelet) {
                        document.getElementById("operationDateTo").className += " required";
                        document.getElementById("operationTimeTo").className += " required";
                    } else {
                        document.getElementById("operationDateTo").className
                            = document.getElementById("operationDateTo").className.replace(new RegExp("required", "g"), "");
                        document.getElementById("operationTimeTo").className
                            = document.getElementById("operationTimeTo").className.replace(new RegExp("required", "g"), "");
                    }
                }
            });
        }
    }

      medServiceAutocomplete.addOnChangeCallback(function() {
          checkIfDateTimeRequired();
        HospitalMedCaseService.isAbortRequiredByOperation($('medService').value, {
            callback: function(isAbort) {
                jQuery('#abortionName').css('background-color',true==isAbort ? '#FFFFA0': '#FFFFFF');
            }
        });
      });
  	 changeParentMedService() ;
      checkIfDateTimeRequired();
  	</script>
    </msh:ifFormTypeIsNotView>
    <msh:ifFormTypeIsCreate formName="stac_surOperationForm">
    <script type="text/javascript">
    isAnesthesiaAutocomplete.addOnChangeCallback(function() {
    	anest() ;
    }) ;
    function anest(){
    	HospitalMedCaseService.getYesNo($('isAnesthesia').value,{
    	callback: function(aResult) {
          	 //alert(aResult);
             if (+aResult==1) {
            		$('anesthesiaName').style.display = "block" ;
              		$('anaesthetistName').style.display = "block" ;
            		$('anesthesiaTypeName').style.display = "block" ;
            		$('anesthesiaServiceName').style.display = "block" ;
            		$('anesthesiaDuration').style.display = "block" ;
              } else {
              		$('anesthesiaName').style.display = "none" ;
              		$('anaesthetistName').style.display = "none" ;
              		$('anesthesiaTypeName').style.display = "none" ;
              		$('anesthesiaServiceName').style.display = "none" ;
              		$('anesthesiaDuration').style.display = "none" ;
              }
          }
		});
    }
    	anest();
    </script>
            <style type="text/css">
            #anesthesiaLabel,#anaesthetistLabel, #isAnesthesiaLabel
            , #anesthesiaTypeLabel,#anaesthetistLabel, #isAnesthesiaTypeLabel,#anesthesiaDurationLabel,#methodName,#profileName
             {
                color: blue ;
            }
            #isAnesthesiaName,#anaesthetistName, #anesthesiaName,#anesthesiaDuration
            , #anesthesiaTypeName,#methodName,#profileName {
                background-color:#FFFFA0;
            }
        </style>
    </msh:ifFormTypeIsCreate>
      <msh:ifInRole roles="/Policy/Mis/MedCase/QualityEstimationCard/View">
      <script type="text/javascript">

          var voc1='vocComplication';

          //Добавление строки с осложнением
          function createRowComp(ii) {
              ii=+ii;
              ii=getNextId(ii);
              var table = document.getElementById('allCompTble');
              var tr = document.createElement('tr');
              tr.id='row'+ii;
              var td0 = document.createElement('td');
              var td1 = document.createElement('td');
              var td2 = document.createElement('td');
              var td2_5 = document.createElement('td');
              var td3 = document.createElement('td');
              var td5 = document.createElement('td');
              //текст, если выбрано "Другое"
              var td6 = document.createElement('td');
              var td7 = document.createElement('td');
              td1.innerHTML=createHtmlForVoc(voc1,ii,35);
              td2.innerHTML="<label id=\"dateComp"+ii+"Label\" for=\"dateComp"+ii+"\">Дата&nbsp;осложнения:</label>" +
                  "<input title=\"Дата&nbsp;осложненияNoneField\" class=\"\" id=\"dateComp"+ii+"\" name=\"dateComp"+ii+"\" size=\"10\" value=\"\" type=\"text\" autocomplete=\"off\">";
              if (ii!=0) td5.innerHTML="<input id=\"btnDel"+ii+"\" type=\"button\" value=\"-\" onclick=\"delRow(this);\">";
              td5.setAttribute("width","25px");
              td5.setAttribute("align","right");
              td2_5.innerHTML="<label>Причина:</label>";
              td3.innerHTML="<input  disabled=\"true\" id=\"btnAdd"+ii+"\" type=\"button\" value=\"+\" onclick=\"addRowAfter(this);\">";
              td6.innerHTML="<label>Осложнение:</label><input disabled id=\"compName"+ii+"\" size=\"10\" type=\"text\" >";
              td7.innerHTML="<label>Причина:</label><input id=\"reasonName"+ii+"\" size=\"10\" type=\"text\" >";
              tr.appendChild(td0);tr.appendChild(td1); tr.appendChild(td2);tr.appendChild(td2_5);
              tr.appendChild(td6);tr.appendChild(td7); tr.appendChild(td5);tr.appendChild(td3);
              table.appendChild(tr);
              evalVoc(voc1,ii);
              if ($('dateComp'+ii)) {
                  new dateutil.DateField($('dateComp'+ii)) ;
                  eventutil.addEventListener($('dateComp'+ii), "keyup", function(){checkEnableAdd(this.id.replace('dateComp',''));}) ;
                  eventutil.addEventListener($('dateComp'+ii), "input", function(){checkEnableAdd(this.id.replace('dateComp',''));}) ;
                  eventutil.addEventListener($('dateComp'+ii), "blur", function(){checkEnableAdd(this.id.replace('dateComp',''));}) ;
                  eventutil.addEventListener($('dateComp'+ii), "paste", function(){checkEnableAdd(this.id.replace('dateComp',''));});
                  eventutil.addEventListener($('dateComp'+ii), "dblclick", function(){checkEnableAdd(this.id.replace('dateComp',''));});
                  eventutil.addEventListener($('dateComp'+ii), "focus", function(){checkEnableAdd(this.id.replace('dateComp',''));});
              }
                setEvents('compName',ii);
                setEvents('reasonName',ii);
              return ii;
          }
          //получение следующего id для вставк:
          //+1 - если не удаляли и +/++ с последнего элемента
          //rowcount - если +/++ с любого элемента, но не удаляли
          //первый свободный после rowcount - если +/++ с любого элемента и удаляли
          //можно просто max(id)+1
          function getNextId(ii) {
              var max=ii;
              //if (ii<document.getElementById('allCompTble').rows.length) ii=document.getElementById('allCompTble').rows.length;
              for (var i=0; i<document.getElementById('allCompTble').rows.length; i++) {
                  var id=document.getElementById('allCompTble').rows[i].id.replace('row','');
                  if (id>max) max=id;
              }
              return ++max;
          }

          function setEvents(name,ii) {
              eventutil.addEventListener($(name+ii), "keyup", function(){checkEnableAdd(this.id.replace(name,''));}) ;
              eventutil.addEventListener($(name+ii), "input", function(){checkEnableAdd(this.id.replace(name,''));}) ;
              eventutil.addEventListener($(name+ii), "blur", function(){checkEnableAdd(this.id.replace(name,''));}) ;
              eventutil.addEventListener($(name+ii), "paste", function(){checkEnableAdd(this.id.replace(name,''));});
              eventutil.addEventListener($(name+ii), "dblclick", function(){checkEnableAdd(this.id.replace(name,''));});
              eventutil.addEventListener($(name+ii), "focus", function(){checkEnableAdd(this.id.replace(name,''));});
          }

          //создать voc
          function evalVoc(voc,ii) {
              eval("var "+voc+ii+"Autocomplete = new msh_autocomplete.Autocomplete()") ;
              eval(voc+ii+"Autocomplete.setUrl('simpleVocAutocomplete/"+voc+"') ");
              eval(voc+ii+"Autocomplete.setIdFieldId('"+voc+ii+"') ");
              eval(voc+ii+"Autocomplete.setNameFieldId('"+voc+ii+"Name') ");
              eval(voc+ii+"Autocomplete.setDivId('"+voc+ii+"Div') ");
              eval(voc+ii+"Autocomplete.setVocKey('"+voc+"') ");
              eval(voc+ii+"Autocomplete.setVocTitle('Осложнение')") ;
              eval(voc+ii+"Autocomplete.build() ");
              eval(voc+ii+"Autocomplete.addOnChangeCallback(function() { checkEnableAdd("+ii+"); })") ;
          }

          //создать html для voc
          function createHtmlForVoc(voc,ii,size) {
              return "<div><input type=\"hidden\" size=\"1\" name=\""+voc+"\" id=\"" + voc +
                  +ii+"\" value=\"\"><input title=\""+voc+ii+"\" type=\"text\" name=\"" + voc +
                  +ii+"Name\" id=\""+voc+ii+"Name\" size=\""+size+"\" class=\"autocomplete horizontalFill\" " +
                  "autocomplete=\"off\"><div id=\""+voc+ii+"Div\" style=\"visibility: hidden; display: none;\" " +
                  "class=\"autocomplete\"></div></div>";
          }
          function delRow(btn) {
              document.getElementById('allCompTble').deleteRow(document.getElementById('row'+btn.id.replace("btnDel","")).rowIndex);
          }
          function addRowAfter(btn) {
              createRowComp(+btn.id.replace("btnAdd",""));
          }
          function checkEnableAdd(id) {
              if ($(voc1+id+'Name').value.indexOf('Другое')==0)
                    document.getElementById('compName'+id).removeAttribute('disabled');
              else {
                    document.getElementById('compName'+id).setAttribute('disabled', true);
              }
              var ii=(''+id).replace('dateComp','');
              if ($(''+voc1+ii) && $('dateComp'+ii) && $(''+voc1+ii).value!='' && $('dateComp'+ii).value!=''
                  && $('dateComp'+ii).title.indexOf('Неправильно')==-1
                && $('reasonName'+id).value!='' &&
                  (($(voc1+id+'Name').value.indexOf('Другое')==0 && $('compName'+id).value!='') ||
                  ($(voc1+id+'Name').value.indexOf('Другое')!=0))) {
                  document.getElementById('btnAdd' + ii).removeAttribute('disabled');
              }
              else {
                  document.getElementById('btnAdd' + ii).setAttribute('disabled', true);
              }
          }
        document.getElementById("compDiv").innerHTML=" <label><b>Осложнения:</b></label>" +
            "<table id=\"allCompTble\"></table>";
          //Осложнения
          <msh:ifFormTypeIsCreate formName="stac_surOperationForm">
          createRowComp(-1);
          </msh:ifFormTypeIsCreate>
          <msh:ifFormTypeAreViewOrEdit formName="stac_surOperationForm">
              function loadComps() {
              HospitalMedCaseService.getCompJson(${param.id}, {
              callback: function (aResult) {
                var res=JSON.parse(aResult);
                  for (var ind1 = 0; ind1 < res.length; ind1++) {
                      var comp = res[ind1];
                      createRowComp(ind1-1);
                      $(voc1+ind1).value=comp.cid;
                      $(voc1+ind1+'Name').value=comp.cname;
                      $('dateComp'+ind1).value=comp.date;
                      $('reasonName'+ind1).value=comp.rtext;
                      $('compName'+ind1).value=comp.ctext;
                      <msh:ifFormTypeIsNotView formName="stac_surOperationForm">
                            checkEnableAdd(ind1);
                      </msh:ifFormTypeIsNotView>
                      <msh:ifFormTypeIsView formName="stac_surOperationForm">
                          $(voc1+ind1).setAttribute("disabled",true);
                          $(voc1+ind1+'Name').setAttribute("disabled",true);
                          $('dateComp'+ind1).setAttribute("disabled",true);
                          $('reasonName'+ind1).setAttribute("disabled",true);
                          $('compName'+ind1).setAttribute("disabled",true);
                      </msh:ifFormTypeIsView>
              }
              <msh:ifFormTypeIsNotView formName="stac_surOperationForm">
                  if (res.length==0)
                    createRowComp(-1);
              </msh:ifFormTypeIsNotView>
              }
              });
              }
              loadComps();
          </msh:ifFormTypeAreViewOrEdit>
          </msh:ifInRole>
      </script>
  </tiles:put>
</tiles:insert>

