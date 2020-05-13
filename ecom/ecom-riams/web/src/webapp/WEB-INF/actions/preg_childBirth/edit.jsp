 <%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@page import="ru.ecom.web.util.ActionUtil"%>
<%@ taglib uri="http://struts.apache.org/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://www.nuzmsh.ru/tags/msh" prefix="msh" %>
<%@ taglib uri="http://www.ecom-ast.ru/tags/ecom" prefix="ecom" %>
<%@ taglib tagdir="/WEB-INF/tags" prefix="tags" %>
<%@ taglib uri="/WEB-INF/mis.tld" prefix="mis" %>

<tiles:insert page="/WEB-INF/tiles/main${param.short}Layout.jsp" flush="true">
	<tiles:put name="style" type="string">
        <style type="text/css">            
            .protocols {
				left:0px;  width:99%; 
				top:0px;  height:55em;
			}
			#epicriPanel {
			width:100%;
			}
			.record {
			width:100%;
			}
        </style>

    </tiles:put>
  <tiles:put name="side" type="string">
	<msh:ifFormTypeIsNotView formName="preg_childBirthForm">
    	<tags:templateProtocol idSmo="preg_childBirthForm.medCase" version="Visit" name="tmp" property="histology" voc="protocolVisitByPatient"/>
    </msh:ifFormTypeIsNotView>  
    <msh:ifFormTypeIsView formName="preg_childBirthForm">
      <msh:sideMenu title="Роды">
        <msh:sideLink key="ALT+2" params="id" action="/entityParentEdit-preg_childBirth" name="Изменить" roles="/Policy/Mis/Pregnancy/ChildBirth/Edit" />
        <msh:sideLink key="ALT+DEL" confirm="Удалить?" params="id" action="/entityParentDeleteGoSubclassView-preg_childBirth" name="Удалить" roles="/Policy/Mis/Pregnancy/ChildBirth/Delete" />
          <msh:sideLink key="ALT+3" params="id" action="/javascript:addEmptyChild()" name="Добавить пустого ребёнка" roles="/Policy/Mis/MedCase/Stac/Ssl/Delete" />
      </msh:sideMenu>
      <msh:sideMenu title="Печать">
         <mis:sideLinkForWoman roles="/Policy/Mis/Pregnancy/History/View" classByObject="MedCase" id="${param.medcase}"
     	action="/javascript:printPregHistory()" name="Истории родов" title="Печать истории родов"/>
      </msh:sideMenu>
    </msh:ifFormTypeIsView>
  </tiles:put>
  <tiles:put name="body" type="string">
    <!-- 
    	  - Течение родов
    	  -->
   <ecom:webQuery isReportBase="${isReportBase}" name="dead_born_sql" nativeSql="select id,name from VocLiveBorn where code='2'"/>
   <%
   ActionUtil.getValueByList("dead_born_sql", "dead_born", request) ;
 	String dead_born = (String)request.getAttribute("dead_born") ;
 	request.setAttribute("dead_born", dead_born);
   %>
    <msh:form action="/entityParentSaveGoView-preg_childBirth.do" defaultField="durationPregnancy">
      <msh:hidden property="id" />
      <msh:hidden property="medCase" />
      <msh:hidden property="saveType" />
      <msh:hidden property="newBornsInfo"  />
      <msh:hidden property="isECO"  />
      <msh:hidden property="isRegisteredWithWomenConsultation"  />
      <msh:hidden property="robsonClass"  />
      <msh:hidden property="robsonSub"  />
      <input type='hidden' name='deadBorn' id="deadBorn" value='${dead_born}' />
      <msh:panel>
        <msh:row>
          <td width="1px" />
          <td width="1px" />
        </msh:row>
        <msh:row>
        	<msh:separator label="Первый период родовой деятельности" colSpan="4"/>
        </msh:row>
        <msh:row>
        	<msh:textField property="durationPregnancy" label="Срок беременности (нед.)"/>
        	<msh:autoComplete property="whereBirthOccurred" label="Роды произошли" horizontalFill="true" vocName="vocWhereBirthOccurred" fieldColSpan="1"/>
        </msh:row>
        <msh:row>
        	<msh:textField property="whereBirthOccurredOther" label="Где" fieldColSpan="3" horizontalFill="true"/>
        </msh:row>
        <msh:row>
        	<msh:autoComplete property="childBirthType" label="Роды" vocName="vocChildBirth" horizontalFill="true"/>
        	<msh:autoComplete property="emergency" parentAutocomplete="childBirthType" label="Показания" vocName="vocChildEmergency" horizontalFill="true"/>
        </msh:row>
        <msh:row>
          <msh:textField property="pangsStartDate" label="Дата начала родов" />
          <msh:textField property="pangsStartTime" label="Время" />
        </msh:row>
        <msh:row>
          <msh:autoComplete property="watersPrematurity" vocName="vocBirthWatesPrematurity" label="Преждевременность отхождения вод" fieldColSpan="3" horizontalFill="true" />
        </msh:row>
        <msh:row>
          <msh:textField property="watersDate" label="Дата отхождения вод" />
          <msh:textField property="watersTime" label="Время" />
        </msh:row>
        <msh:row>
          <msh:textField property="upperWatersAmount" label="Кол-во передних вод (мл)"  />
          <msh:textField property="downWatersAmount" label="Кол-во задних вод (мл)"  />
        </msh:row>
        <msh:row>
          <msh:textField property="upperWatersQuality" label="Качество передних вод"  />
          <msh:textField property="downWatersQuality" label="Качество задних вод"  />
        </msh:row>
          <msh:row>
              <msh:autoComplete property="paritetPregn" label="Паритет беременностей" horizontalFill="true" vocName="vocParitet" fieldColSpan="1"/>
          </msh:row>
          <msh:row>
              <msh:autoComplete property="paritet" label="Паритет родов" horizontalFill="true" vocName="vocParitet" fieldColSpan="1"/>
          </msh:row>
          <msh:ifFormTypeIsNotView formName="preg_childBirthForm">
              <msh:row>
                  <td class="label" title="Поиск по промежутку  (ecoGroup)" colspan="1"><label for="ecoGroupName" id="tecoGroupLabel">Выберите:</label></td>
                  <td onclick="this.childNodes[1].checked='checked';" colspan="1">
                      <input type="radio" name="ecoGroup" value="1"> ЭКО
                  </td>
                  <td onclick="this.childNodes[1].checked='checked';" colspan="3">
                      <input type="radio" name="ecoGroup" value="2"> Без ЭКО
                  </td>
              </msh:row>
              <msh:row>
                  <td class="label" title="Поиск по промежутку  (gkGroup)" colspan="1"><label for="gkGroupName" id="tgkGroupLabel">Выберите:</label></td>
                  <td onclick="this.childNodes[1].checked='checked'; checkWomenConsult();" colspan="1">
                      <input type="radio" name="gkGroup" value="1"> Состояла на учёте в ЖК
                  </td>
                  <td onclick="this.childNodes[1].checked='checked'; checkWomenConsult();" colspan="3">
                      <input type="radio" name="gkGroup" value="2"> НЕ состояла на учёте в ЖК
                  </td>
                  <msh:autoComplete property="womenConsult" fieldColSpan="3" horizontalFill="true" label="" vocName="vocWomenConsult"/>
              </msh:row>
              <msh:row>
                  <td class="label" title="Поиск по промежутку  (water)" colspan="1"><label for="waterName" id="twaterLabel">Длительный безводный период:</label></td>
                  <td onclick="this.childNodes[1].checked='checked'; checkWater();" colspan="1">
                      <input type="radio" name="water" value="1"> Нет
                  </td>
                  <td onclick="this.childNodes[1].checked='checked'; checkWater();" colspan="3">
                      <input type="radio" name="water" value="2"> Да
                  </td>
                  <msh:textField property="waterlessDurationHour" label="Длительность безводного периода (часы)" />
                  <msh:textField property="waterlessDurationMin" label="Длительность безводного периода (минуты)" />
              </msh:row>
              <msh:row>
                  <td class="label" title="Поиск по промежутку  (diabetIdentity)" colspan="1"><label for="diabetIdentityName" id="tdiabetIdentityLabel">Диабет у матери:</label></td>
                  <td onclick="this.childNodes[1].checked='checked'; checkdiabetIdentity();" colspan="1">
                      <input type="radio" name="diabetIdentityRad" value="1"> Нет
                  </td>
                  <td onclick="this.childNodes[1].checked='checked'; checkdiabetIdentity();" colspan="3">
                      <input type="radio" name="diabetIdentityRad" value="2"> Да
                  </td>
                  <msh:autoComplete property="diabetIdentity" fieldColSpan="3" horizontalFill="true" label="" vocName="vocIdentityPatientNewBorn"/>
              </msh:row>
          </msh:ifFormTypeIsNotView>
          <msh:ifFormTypeIsView formName="preg_childBirthForm">
              <msh:row>
                  <msh:checkBox property="isECO" label="ЭКО?" fieldColSpan="1"/>
              </msh:row>
              <msh:row>
                  <msh:checkBox property="isRegisteredWithWomenConsultation" label="Учёт в ЖК?" fieldColSpan="1"/>
                  <msh:autoComplete property="womenConsult" fieldColSpan="1" horizontalFill="true" label="Консультация" vocName="vocWomenConsult"  viewOnlyField="true"/>
              </msh:row>
              <msh:row>
                  <msh:textField property="waterlessDurationHour" label="Длительность безводного периода (часы)" />
                  <msh:textField property="waterlessDurationMin" label="Длительность безводного периода (минуты)" />
              </msh:row>
              <msh:row>
                <msh:autoComplete property="diabetIdentity" fieldColSpan="1" horizontalFill="true" label="Диабет у матери" vocName="vocIdentityPatientNewBorn" viewOnlyField="true"/>
              </msh:row>
          </msh:ifFormTypeIsView>
        <msh:row styleId="rwSam1"><msh:separator label="Второй период родовой деятельности" colSpan="4"/>
        </msh:row>
        <msh:row styleId="rwSam6">
          <msh:textField property="fullOpenDate" label="Дата полного открытия" />
          <msh:textField property="fullOpenTime" label="Время" />
        </msh:row>
        <msh:row styleId="rwSam2">
          <msh:textField property="travailStartDate" label="Дата начала потуг" />
          <msh:textField property="travailStartTime" label="Время" />
        </msh:row>

       <msh:row>
        	<msh:separator label="Новорожденные" colSpan="10"/>
        </msh:row>
        <msh:row>
        	<msh:textField property="newBornAmount" fieldColSpan="1" label="Кол-во плодов" />
        </msh:row>
        <msh:row>
        	<td colspan='10'>
        <table id="otherNewBorns">
        </table>
        </td>
        </msh:row>
        

        <msh:row styleId="rwSam7">
        	<msh:separator label="Третий период родовой деятельности" colSpan="9" />
        </msh:row>
        <msh:row styleId="rwKes1">
        	<msh:separator label="Окончание родовой деятельности" colSpan="9" />
        </msh:row>
         <msh:row>
          <msh:textField property="birthFinishDate" label="Дата окончания родов" />
          <msh:textField property="birthFinishTime" label="Время" />
        </msh:row>
        <msh:row  styleId="rwSam8">
          <msh:autoComplete label="Послед выделился" property="placentaSeparation" vocName="vocPlacentaSeparation" />
          <msh:textField property="placentaMinute" label="Через (мин)"/>
        </msh:row>
        <msh:row  styleId="rwSam9">
          <msh:textField property="placentaWeight" label="Вес плаценты (гр.)" />
          <msh:textField property="placentaSize" label="Размеры плаценты" />
        </msh:row>
        <msh:row styleId="rwSam10">
          <msh:autoComplete fieldColSpan="3" vocName="vocPlacentaIntegrity" property="placentaIntegrity" label="Детское место" horizontalFill="true" />
        </msh:row>
        <msh:row  styleId="rwSam11">
          <msh:autoComplete property="membranesIntegrity" label="Целостность оболочек" vocName="vocFetalMembranesIntegrity" fieldColSpan="3" horizontalFill="true" />
        </msh:row>
        <msh:row>
        	<msh:textField property="cordLength" label="Длина пуповины"/>
        </msh:row>
        <msh:row  styleId="rwSam13">
          <msh:textArea rows="3" property="placentaFeatures" label="Особенности плаценты" horizontalFill="true" fieldColSpan="3"/>
        </msh:row>
         <msh:row  styleId="rwSam14">
          <msh:textField property="hemorrhageVolume" label="Объем кровопотери (мл)" />
        </msh:row>
        <msh:row>
        	<msh:autoComplete property="anesthesiaMedication" label="Медикаментозное обезболивание" vocName="vocChildAnesthesiaMedication" fieldColSpan="3" horizontalFill="true"/>
        </msh:row>
        <msh:row>
        	<msh:autoComplete property="anesthesiaMedicationEffect" label="Эффект" vocName="vocChildAnesthesiaMedicationEffect" fieldColSpan="3" horizontalFill="true"/>
        </msh:row>
        <msh:row>
          <msh:checkBox property="placentaHistologyOrder" label="Направление плаценты на гистологию" fieldColSpan="3"/>
        </msh:row>
        <msh:row>
        	<msh:autoComplete property="histology" fieldColSpan="3" horizontalFill="true" label="Гистология плацента" vocName="vocHistologyResult"/>
        </msh:row>
        <msh:row styleId="rwSam3">
        <msh:separator label="Общая продолжительность родов" colSpan="9"/>
        </msh:row>
        <msh:row  styleId="rwSam4">
          <msh:textField property="period1Duration" label="1 периода (час)" />
          <msh:textField property="period2Duration" label="2 периода (час)" />
        </msh:row>
        <msh:row  styleId="rwSam5">
          <msh:textField property="period3Duration" label="3 периода (мин)" />
        </msh:row>
       
        <msh:row>
          <msh:autoComplete property="childTook" label="Ребенка принял" vocName="workFunction" fieldColSpan="3" horizontalFill="true" />
        </msh:row>
        <msh:row>
          <msh:autoComplete property="placentaInspector" label="Послед осматривал" vocName="workFunction" fieldColSpan="3" horizontalFill="true" />
        </msh:row>
        <msh:ifFormTypeIsView formName="preg_childBirthForm">
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
        </msh:ifFormTypeIsView>
          <h3><b>Классификация Робсона</b></h3>
          <div id="classRobsonsDiv"></div>
          <div id="subRobsonsDiv"></div>
        <msh:submitCancelButtonsRow colSpan="3"  functionSubmit="if (chekECOAndGK() && checkRobson() && checkWaterBeforeSafe() && checkSaveIdentity() && checkSaveWomenConsult()) { this.form.action='entityParentSaveGoView-preg_childBirth.do';checkForm(); } else {  document.forms[0].action = old_action;$('submitButton').disabled = false; }"/>
      </msh:panel>
    </msh:form>
     <tags:preg_childBirthYesNo name="DeadBorn" field="DeadBeforeLabors"/>
    <msh:ifFormTypeIsView formName="preg_childBirthForm">
      <msh:ifInRole roles="/Policy/Mis/NewBorn/View">
        <%-- <ecom:parentEntityListAll attribute="newBorns" formName="preg_newBornForm" /> --%>
        <ecom:webQuery name = "newBorns" nameFldSql="newBorns_sql" nativeSql="select nb.id as id, nb.birthDate as bDate, nb.birthTime as bTime, vs.name as sexName, pat.firstname as patName
         from newBorn nb left join patient pat on pat.id=nb.patient_id
         left join vocSex vs on vs.id=nb.sex_id
         where nb.childBirth_id='${param.id}'" />
        <msh:tableNotEmpty name="newBorns">
          <msh:section title="Данные о новорожденных">
            <msh:table name="newBorns" action="entityParentView-preg_newBorn.do" idField="1">
              <msh:tableColumn columnName="Дата рождения" property="2" />
              <msh:tableColumn columnName="Время рождения" property="3" />
              <msh:tableColumn columnName="Пол" property="4" />
              <msh:tableColumn columnName="Имя" property="5" />
            </msh:table>
          </msh:section>
        </msh:tableNotEmpty>
      </msh:ifInRole>
    </msh:ifFormTypeIsView>
  </tiles:put>
  <tiles:put name="title" type="string">
    <ecom:titleTrail mainMenu="Patient" beginForm="preg_childBirthForm" />
  </tiles:put>
    <script type="text/javascript" src="./dwr/interface/PregnancyService.js" >/**/</script>
  <tiles:put name="javascript" type="string">
  <script type="text/javascript">
  function printPregHistory() {
	  document.location='print-preghistory.do?s=HospitalPrintService&m=printPregHistoryByMC&id='+$('medCase').value;
  }
  <msh:ifFormTypeAreViewOrEdit formName="preg_childBirthForm">
  <msh:ifFormTypeIsNotView formName="preg_childBirthForm">
  if ($('waterlessDurationHour').value=='' && $('waterlessDurationMin').value=='')
      document.getElementsByName("water")[0].checked=true;
  else document.getElementsByName("water")[1].checked=true;
  </msh:ifFormTypeIsNotView>
  <msh:ifFormTypeIsView formName="preg_childBirthForm">
  function addEmptyChild() {
      PregnancyService.addEmptyChild(${param.id},{
          callback: function () {
              window.location.reload() ;
          }
      });
  }
  </msh:ifFormTypeIsView>
  </msh:ifFormTypeAreViewOrEdit>
  //настройка для безводного периода
  function  checkWater() {
      if (document.getElementsByName("water") && document.getElementsByName("water")[1]) {
          if (document.getElementsByName("water")[1].checked) {
              $('waterlessDurationHourLabel').removeAttribute('hidden');
              $('waterlessDurationHour').removeAttribute('hidden');
              $('waterlessDurationHour').className = "required";
              $('waterlessDurationMinLabel').removeAttribute('hidden');
              $('waterlessDurationMin').removeAttribute('hidden');
              $('waterlessDurationMin').className = "required";
          }
          else {
              $('waterlessDurationHourLabel').setAttribute('hidden', true);
              $('waterlessDurationHour').setAttribute('hidden', true);
              $('waterlessDurationHour').setAttribute('hidden', true);
              $('waterlessDurationMinLabel').setAttribute('hidden', true);
              $('waterlessDurationMin').setAttribute('hidden', true);
              $('waterlessDurationHour').className = "";
              $('waterlessDurationMin').className = "";
              $('waterlessDurationHour').value = "";
              $('waterlessDurationMin').value = "";
          }
      }
  }
  checkWater();
  //проверка диабета у матери
  function checkdiabetIdentity() {
      if (document.getElementsByName("diabetIdentityRad") && document.getElementsByName("diabetIdentityRad")[1]) {
          if (document.getElementsByName("diabetIdentityRad")[1].checked) {
              $('diabetIdentity').removeAttribute('hidden');
              $('diabetIdentityName').removeAttribute('hidden');
              $('diabetIdentity').value='';
              $('diabetIdentityName').value='';
              $('diabetIdentityName').className = "autocomplete horizontalFill required";
              setAutoBracelet();
          }
          else if (document.getElementsByName("diabetIdentityRad")[0].checked) {

              $('diabetIdentity').setAttribute('hidden', true);
              $('diabetIdentityName').setAttribute('hidden', true);
              $('diabetIdentity').value='';
              $('diabetIdentityName').value='';
              $('diabetIdentityName').className = 'autocomplete horizontalFill';
          }
      }
  }

  //проверка ЖК матери
  function checkWomenConsult() {
      if (document.getElementsByName("gkGroup") && document.getElementsByName("gkGroup")[0]) {
          if (document.getElementsByName("gkGroup")[0].checked) {
              $('womenConsult').removeAttribute('hidden');
              $('womenConsultName').removeAttribute('hidden');
              $('womenConsult').value='';
              $('womenConsultName').value='';
              $('womenConsultName').className = "autocomplete horizontalFill required";
          }
          else if (document.getElementsByName("gkGroup")[1].checked) {

              $('womenConsult').setAttribute('hidden', true);
              $('womenConsultName').setAttribute('hidden', true);
              $('womenConsult').value='';
              $('womenConsultName').value='';
              $('womenConsultName').className = 'autocomplete horizontalFill';
          }
      }
  }
  
  //если есть единственное значение для браслета новорожд., то выбрать его автоматически
  function setAutoBracelet() {
      PregnancyService.getAutoBracelet({
          callback: function (aResult) {
              if (aResult != null && aResult != '{}') {
                  var res = JSON.parse(aResult);
                  $('diabetIdentity').value=res.id;
                  $('diabetIdentityName').value=res.name;
              }
          }
      });
  }
  //проверка перед сохранением
  function checkSaveIdentity() {
      if (!(document.getElementsByName("diabetIdentityRad") && document.getElementsByName("diabetIdentityRad")[1] && document.getElementsByName("diabetIdentityRad")[1].checked && $('diabetIdentity').value!=''
      || document.getElementsByName("diabetIdentityRad") && document.getElementsByName("diabetIdentityRad")[0] && document.getElementsByName("diabetIdentityRad")[0].checked && $('diabetIdentity').value=='')) {
          alert('Заполните правильно диабет матери!');
          return false;
      }
      else return true;
  }

  //проверка перед сохранением
  function checkSaveWomenConsult() {
      if (!(document.getElementsByName("gkGroup") && document.getElementsByName("gkGroup")[0] && document.getElementsByName("gkGroup")[0].checked && $('womenConsult').value!=''
          || document.getElementsByName("gkGroup") && document.getElementsByName("gkGroup")[1] && document.getElementsByName("gkGroup")[1].checked && $('womenConsult').value=='')) {
          alert('Выберите женскую консультацию!');
          return false;
      }
      else return true;
  }
  <msh:ifFormTypeIsNotView formName="preg_childBirthForm">
  checkdiabetIdentity();
  $('womenConsultName').className = "autocomplete horizontalFill required";
  </msh:ifFormTypeIsNotView>
  //проверка перед сохранением
  function checkWaterBeforeSafe() {
      if (!document.getElementsByName("water")[0].checked && !document.getElementsByName("water")[1].checked
          || document.getElementsByName("water")[1].checked && $('waterlessDurationHour').value=='' && $('waterlessDurationMin').value=='') {
          alert('Нужно отметить длительность безводного периода и ввести значение (часов и/или минут)!');
          return false;
      }
      else return true;
  }
  //Milamesher #147 Робсон в родах
  var voc='VocRobsonClass';
  function loadYesNoRobson() {
      var txt="";
      VocService.getAllValueByVocs(voc,{
          callback: function(aResult) {
              var vocRes=JSON.parse(aResult).vocs[0];
              txt+="<table border='1px'><tbody>";
              for (var ind1 = 0; ind1 < vocRes.values.length; ind1++) {
                  var vocVal = vocRes.values[ind1];
                  txt+="<tr><td><label  id='" + voc + vocVal.id + "'>"+vocVal.name+"</label></td>";
                  txt+="<td><select id='yesNo" + vocVal.id + "'";
                  <msh:ifFormTypeIsView formName="preg_childBirthForm">
                  txt+=" disabled='true' ";
                  </msh:ifFormTypeIsView>
                  txt+=" onchange='loadSubs();' ";
                  txt+= "><option>Нет</option>" +
                      "<option>Да</option>" +
                      "</select><td></tr>";
              }
              txt+="</tbody></table>";
              document.getElementById('classRobsonsDiv').innerHTML+=txt;
              <msh:ifFormTypeAreViewOrEdit formName="preg_childBirthForm">
              if (document.getElementById('yesNo'+$('robsonClass').value)) document.getElementById('yesNo'+$('robsonClass').value).selectedIndex=1;
              </msh:ifFormTypeAreViewOrEdit>
              loadSubs();
          }
      });
  }
  //загрузка подгрупп в зависимости от группы, обнуление текущей
  function loadSubs() {
      var id=checkYesNoGetIndex();
      //$('robsonSub').value='';
      var txt="";
      if (id!=-1) {
          $('robsonClass').value=id;
          PregnancyService.getRobsonSub(id, {
              callback: function (aResult) {
                  if (aResult != null && aResult != '[]') {
                      var res = JSON.parse(aResult);
                      txt += "<table><tbody>";
                      txt += "<tr><td><label><b>Подгруппа:</b></label><td><td><select id='sub'";
                      <msh:ifFormTypeIsView formName="preg_childBirthForm">
                      txt += " disabled='true' ";
                      </msh:ifFormTypeIsView>
                      txt += "";
                      <msh:ifFormTypeIsNotView formName="preg_childBirthForm">
                      txt += "  style=\"background-color:#fcffa7\"' ";
                      </msh:ifFormTypeIsNotView>
                      txt+=">";
                      for (var ind1 = 0; ind1 < res.length; ind1++) {
                          var val = res[ind1];
                          txt += "<option style=\"background-color:white\" id='option" + val.id + "'>" + val.name + "</option>";
                      }
                      txt += "</select><td></tr></tbody></table>";
                  }
                  document.getElementById('subRobsonsDiv').innerHTML = txt;
                  if (document.getElementById('sub')) {
                      document.getElementById('sub').onselect = function () {
                          changeSub();
                      };
                      document.getElementById('sub').onclick = function () {
                          changeSub();
                      };
                      document.getElementById('sub').onchange = function () {
                          changeSub();
                      };
                      document.getElementById('sub').selectedIndex = -1;
                      <msh:ifFormTypeIsNotView formName="preg_childBirthForm">
                      $('robsonSub').value='';
                      </msh:ifFormTypeIsNotView>
                  }
                  if ($('robsonSub').value!='' && $('robsonSub').value!='0' && document.getElementById('sub'))
                      selectItemById(document.getElementById('sub'),$('robsonSub').value);
                  else if (document.getElementById('sub')) document.getElementById('sub').value='';
                  if (document.getElementById('sub')==null) $('robsonSub').value='';
              }
          });
      }
      else
          document.getElementById('subRobsonsDiv').innerHTML ="";
  }
  //установка значения подкатегории при выборе
  function changeSub() {
      var selects = document.getElementsByTagName('option');
      for (var i = 0; i < selects.length; i++) {
          if (selects[i].selected && selects[i].id.indexOf('option')!=-1 && selects[i].parentElement.id.indexOf('sub')!=-1)
              $('robsonSub').value=selects[i].id.replace('option','');
      }
  }
  //установка значения подкатегории при загрузке
  function selectItemById(elmnt, numId){
      for(var i=0; i < elmnt.options.length; i++) {
          if(elmnt.options[i].id === 'option'+numId) {
              elmnt.selectedIndex = i;
              break;
          }
      }
  }
  //коряво, но такие странные требования
  function checkYesNoGetIndex() {
      var selects = document.getElementsByTagName('select');
      var count=0;
      var id=-1;
      for (var i = 0; i < selects.length; i++) {
          if (selects[i].selectedIndex==1 && selects[i].id.indexOf('yesNo')!=-1) {
              id=selects[i].id.replace('yesNo',''); count++;
          }
      }
      if (count!=1) {
          if ($('submitButton')) $('submitButton').disabled = false;
          return -1;
      }
      else return id;
  }
  PregnancyService.getAllRobsonInfo($('medCase').value,{
      callback: function (aResult) {
          if (aResult != null && aResult != '{}') {
              var aResult = JSON.parse(aResult) ;
              $('robsonClass').value=(typeof aResult.robsonClass==='undefined')? '': aResult.robsonClass;
              $('robsonSub').value=(typeof aResult.robsonSub==='undefined')? '': aResult.robsonSub;
          }
          loadYesNoRobson();
      }});
  </script>
  <msh:ifFormTypeIsNotView formName="preg_childBirthForm">
  <script type="text/javascript">
  	var isSaveNewBorns ;
  	var old_action = document.forms["mainForm"].action ; 
  	
  	//document.forms["mainForm"].action="javascript:checkForm()" ; 

  </script>
  <msh:ifFormTypeAreViewOrEdit formName="preg_childBirthForm">
  <script type="text/javascript">
  isSaveNewBorns=0;
  //Milamesher 03092018 radiobuttons ЭКО и ЖК
  if ($('isECO').value=='true') document.getElementsByName("ecoGroup")[0].checked=true; else document.getElementsByName("ecoGroup")[1].checked=true;
  if ($('isRegisteredWithWomenConsultation').value=='true') document.getElementsByName("gkGroup")[0].checked=true; else document.getElementsByName("gkGroup")[1].checked=true;
      <msh:ifFormTypeIsNotView formName="preg_childBirthForm">
          if ($('diabetIdentity').value=='')
            document.getElementsByName("diabetIdentityRad")[0].checked=true;
          else
          document.getElementsByName("diabetIdentityRad")[1].checked=true;
            checkdiabetIdentity();

          if ($('womenConsult').value=='')
          document.getElementsByName("womenConsult")[0].checked=true;
          else
          document.getElementsByName("womenConsult")[1].checked=true;
          checkWomenConsult();
   </script>
      </msh:ifFormTypeIsNotView>
  </msh:ifFormTypeAreViewOrEdit>
  <msh:ifFormTypeIsCreate formName="preg_childBirthForm">
  <script type="text/javascript">
  document.forms["mainForm"].action="javascript:checkDeadBorn()" ;
  isSaveNewBorns=1 ;
  //значение по умолчанию для ЖК
  document.getElementsByName("gkGroup")[0].checked=$('isRegisteredWithWomenConsultation').value = true;
  </script>
  </msh:ifFormTypeIsCreate>
  <script type="text/javascript">
      //Milamesher 31082018 проверка на ЭКО и ЖК
      function chekECOAndGK() {
          if (!document.getElementsByName("ecoGroup")[0].checked && !document.getElementsByName("ecoGroup")[1].checked)
              alert("Необходимо установить, было ли проведено ЭКО!");
          else if (!document.getElementsByName("gkGroup")[0].checked && !document.getElementsByName("gkGroup")[1].checked)
            alert("Необходимо установить, состояла ли на учёте в женской консультации!");
         else {
              $('isECO').value = document.getElementsByName("ecoGroup")[0].checked;
              $('isRegisteredWithWomenConsultation').value = document.getElementsByName("gkGroup")[0].checked;
              return true;
          }
          return false;
      }
      //#147 проверка на заполнение Росбона
      function checkRobson() {
          if (checkYesNoGetIndex()!=-1&& $('robsonClass').value!='' && $('robsonClass').value!='0' && (document.getElementById('sub') && $('robsonSub').value!='' || document.getElementById('sub')==null))
              return true;
          else {
              alert("Необходимо заполнить классификацию Робсона!");
              return false;
          }
      }
  function checkForm() {
          if (isSaveNewBorns) {
              try {
                  createOtherNewBorns();
                  document.forms["mainForm"].action = old_action;
                  document.forms["mainForm"].submit();

              } catch (e) {
                  alert(e);
                  $('submitButton').disabled = false;
              }
          } else {
              document.forms["mainForm"].action = old_action;
              document.forms["mainForm"].submit();
          }
	}
  childBirthTypeAutocomplete.addOnChangeCallback(function() {
	  checkChildBirth() ;
	  $('emergency').value='' ;
	  $('emergencyName').value='' ;
	 });
  function checkChildBirth() {
	  if (+$('childBirthType').value>0) {VocService.getCodeById("vocChildBirth",$("childBirthType").value,"code",{
			
			callback: function(aResult) {
				//alert(aResult) ;
				var trViewK = true ;
				var trViewS = true ;
				if (aResult!=null && aResult=="KES") {trViewS=false ;}else{trViewK=false ;}
				for (var i=1;i<15;i++) {
					showRow("rwSam"+i,trViewS) ;
				}
				for (var i=1;i<2;i++) {
					showRow("rwKes"+i,trViewK) ;
				}
				
			}
		} ) ;}else{
			for (var i=1;i<15;i++) {
				showRow("rwSam"+i,false) ;
			}
			for (var i=1;i<2;i++) {
				showRow("rwKes"+i,false) ;
			}
		}
  }
  function showRow(aRowId, aCanShow ) {
		
		try {
			$(aRowId).style.display = aCanShow ? 'table-row' : 'none' ;
		} catch (e) {
			try{
			$(aRowId).style.display = aCanShow ? 'block' : 'none' ;
			}catch(e) {}
		}	
	}
  checkChildBirth() ;
  </script>
  </msh:ifFormTypeIsNotView>
  <msh:ifFormTypeIsCreate formName="preg_childBirthForm">
  	<script type="text/javascript">
	var theFld = [['Дата рождения','BirthDate',3,1,1,'birthDate',0,'',0,'pangsStartDate']
	,['Время','BirthTime',4,3,1,'birthTime',0,'',0]
	,['Родился','LiveBorn',1,7,1,'liveBorn',0,'VocLiveBorn',1]
	,['Родился','PartBodyBorn',1,5,1,'partBodyBorn',0,'VocPartBodyBorn',1]
	,['Пол','Sex',1,5,1,'sex',0,'VocSex',1]
	,['Масса','BirthWeight',2,7,1,'birthWeight',0,'',1]
	,['Рост','BirthHeight',2,9,1,'birthHeight',0,'',0]
	,['Окружность головы','HeadCircle',2,11,1,'headCircle',0,'',0] 
	,['Плеч','ShouldersCircle',2,11,0,'shouldersCircle',0,'',0] 
	,['Груди','BreastCircle',2,11,0,'breastCircle',0,'',0] 
	,['Обвитие','Entanglement',1,11,1,'entanglement',0,'VocBirthEntanglement',1] 
	,['Кол-во обвитий','EntanglementMultiplicity',1,11,0,'entanglementMultiplicity',0,'VocBirthEntanglementMultiplicity',1] 
	,['Где','WhereEntanglement',1,11,0,'whereEntanglement',0,'VocBirthWhereEntanglement',1]
	,['Отделение','Department',1,11,1,'department',0,"MisLpu where IsNewBornDep='1'",1]
	,['Зрелость','Maturity',1,2,1,'maturity',0,'VocNewBornMaturity',1]
    //,['Акушерский диагноз','Idc10',1,11,2,'idc10',1,'VocIdc10',1]
	,['','DeadBeforeLabors',6,2,0,'deadBeforeLabors',0,'',1]
	] ;
	var vocList = "" ;
	var vocValueList  ;


	for (var i=0; i<theFld.length;i ++) {
		if (theFld[i][7]!="") {
			if (vocList!="") vocList+="," ;
			vocList+=theFld[i][7] ;
		}
	}
	onload=function(){
	VocService.getAllValueByVocs(vocList,{
	
	callback: function(aResult) {
        vocValueList=JSON.parse(aResult) ;
        //$('whereBirthOccurredOther').value=aResult;
	  		if (+$('newBornAmount').value<1) {
	  			$('newBornAmount').value="1";
	  			
	  		}
	  		changeBorn(+$('newBornAmount').value) ;
	 		if ($('newBornsInfo').value!='') {
	 			//alert ("Info = "+$('newBornsInfo').value);
	               var addRowF="";
	               var ind_f=0 ;
	         		for (var i=0;i<theFld.length;i++) {
	         			addRowF+="ar["+(ind_f++)+"],";
	         			if (theFld[i][2]==1) {
	         				addRowF+="ar["+(ind_f++)+"],"
	         			}
	         		}
	       		//alert ("addRF = "+addRowF);
	         	    fillRows();
	         	}

	    
	  	var oldValueAmount =+$('newBornAmount').value;
	  	eventutil.addEventListener($('newBornAmount'),'blur',function(){
	  		if (+oldValueAmount!=$('newBornAmount').value) {
	  			changeBorn(+$('newBornAmount').value) ;
	  			oldValueAmount=$('newBornAmount').value ;
	  		}
	  	}) ;

	}} ) }
  	
	
  	function changeBorn(aNewValue) {
  		var borns = document.getElementById('otherNewBorns').childNodes.length-1;
  		if (borns<aNewValue) {
				addRow() ;
				borns++ ;
				//alert(borns+"---"+aNewValue+"add")
				changeBorn(aNewValue) ;
		} else if (borns>aNewValue) {
			//alert(borns+"---"+aNewValue+"del")
				deleteLastNewBorn() ;
			borns--;
				//changeBorn(aNewValue) ;
		}
		if (borns!=aNewValue) changeBorn(aNewValue);
	}
  	
  	/*function addNewBorn(check) {
       /* var addRowF="";
  		for (var i=0;i<theFld.length;i++) {
  			var fld_i = theFld[i] ;
				eval("var "+fld_i[1]+"=$('"+fld_i[5]+"').value;");
  			var fld_i = theFld[i] ;addRowF+=fld_i[1]+","
  			if (fld_i[2]==1) {
  				eval("var "+fld_i[1]+"Name=$('"+fld_i[5]+"Name').value;");
      			addRowF+=fld_i[1]+"Name," ;
  			}
  		}
		addRowF=addRow.length>0?addRowF.trim().substring(0,addRowF.length-1):"";
       addRow ();
  		//var isCheckReq=true ;
        /*if (isCheckReq) {
        	var servs = document.getElementById('otherNewBorns').childNodes;
              var l = servs.length;
              for (var i=1; i<l;i++) {
            	  var isCheckDouble = false;
            	 if (isCheckDouble) {
             		 if (+check!=1) {
             			 if (confirm("Такая услуга уже зарегистрирована. Вы хотите ее заменить?")) {
             			var node=servs[i];node.parentNode.removeChild(node);
             		 } else {
              			return;
              		 } 
             		} else {return;}
                }                 
             }
        		for (var i=0;i<theFld.length;i++) {
        		}
        }
        eval(addRowF) ;
  		for (var i=0;i<theFld.length;i++) {
  			var fld_i = theFld[i] ;
  			if (fld_i[6]==1) {
  				eval("$('"+fld_i[5]+"').value='';");
      			if (fld_i[2]==1) {
      				eval("$('"+fld_i[5]+"Name').value='';");
      			}
  			}
  		}
     }
    */
  	function checkDeadBorn() {
    	var borns = document.getElementById('otherNewBorns').childNodes;
    	var ret = 0;
  		for (var i=1;i<borns.length;i++) {
  			for (var ii=0;ii<theFld.length;ii++) {
  				
  				var val
	  			if (+theFld[ii][2]==1) {
	  				val = getCheckedRadio(document.forms["mainForm"],theFld[ii][1]+(i)) ;
	  				
	  				if ((theFld[ii][1]=='LiveBorn') &&($('DeadBeforeLabors'+i).value==null || $('DeadBeforeLabors'+i).value=='')) {
	  					//alert ("chDB "+theFld[ii][1] +" <==> "+$('DeadBeforeLabors'+i).value);
	  					if (val==$('deadBorn').value) {
	  						ret=1;
	  						showDeadBornCreateType(i, "Новорожденный "+i+" умер до начала родовой деятельности?");
	  					}	  					
	  				}
	  			}
  			}
  			
	  		
  		}
  		if (ret==0) {
  		checkForm ();	
  		}
    }
    // Заполняем данные по новорожденному, если сохранение не удалось 

    function fillRows() {
    	if ($('newBornsInfo').value !=null && $('newBornsInfo').value !='') {
    		var borns = $('newBornsInfo').value.split('@');
    		for (var i=0;i<borns.length;i++) {
    			var born = borns[i].split('#');
    			for (var ii=0;ii<theFld.length;ii++) {
    				if (+theFld[ii][2]==1) {
    					setCheckedRadio(theFld[ii][1]+(i+1),born[ii]);
    				} else {    					
    					$(theFld[ii][1]+(i+1)).value = born[ii];
    				}
    			}
    			var index=i+1;
                var t = new msh_autocomplete.Autocomplete();
                t.setUrl('simpleVocAutocomplete/vocIdc10');
                t.setIdFieldId('Idc10' + index);
                t.setNameFieldId('Idc10' + index + 'Name');
                t.setDivId('Idc10' + index + 'Div');
                t.build();
                //$('currentIndexOdNewBorn').value=i;
                t.addOnChangeCallback(closure(index));
                if (((document.getElementById('LiveBorn' + index)).checked)==false) {
                    var idc10div = document.getElementById('divIdc10' + index);
                    if (idc10div != null) {
                        idc10div.removeAttribute('hidden');
                    }
                }
    		}
    	}
    }
    
    function setCheckedRadio (aField, aValue) {    	
    	    var chk =  document.getElementsByName(aField) ;
    	    for (var i=0;i<chk.length;i++) {
    	    	if (chk[i].value ==+aValue) {
    	    		chk[i].checked='checked';
    	    	}
    	    }
    }
    function createOtherNewBorns() {
  		var borns = document.getElementById('otherNewBorns').childNodes;
  		var str = ""; $('newBornsInfo').value='';
  		for (var i=1;i<borns.length;i++) {
  			for (var ii=0;ii<theFld.length;ii++) {
  				var val
	  			if (+theFld[ii][2]==1) {
	  				val = getCheckedRadio(document.forms["mainForm"],theFld[ii][1]+(i)) ;
	  			} else {
	  				val = $(theFld[ii][1]+(i)).value ;
	  			}
  				if (theFld[ii][4]&&val=="") throw "Поле обязательное для заполнения: "+theFld[ii][0] ;
	  			str+=val+"#";
  			}
  			//alert(currentValue[i - 1]);
            //alert((document.getElementById('LiveBorn' + i)).checked);  //false if 2!!
  			if (((document.getElementById('LiveBorn' + i)).checked)==false) {  //если мёртвый, то диагноз+наименование
                var idc10Field = document.getElementById('Idc10' + i/* + 'Name'*/);
                var textField = document.getElementById('2IdcText' + i);
                if (textField != null && idc10Field != null) {
                    str += idc10Field.value + "#";
                    str += textField.value + "#";
                }
            }
            /*else {
                str += "#";
                str += "#";
            }*/
            str=str.length>0?str.trim().substring(0,str.length-1):"";
	  		str+="@" ;
  		}
  		str=str.length>0?str.trim().substring(0,str.length-1):"";
  		//alert(str);
  		$('newBornsInfo').value=str;
  	}
  	// 0. наименование 1. Наим. поля в функции 2. autocomplete-1,textFld-2 ,dateFld-3,timeFld-4
  	// 3. Номер node в добавленной услуге 4. Обяз.поля да-1 нет-2 
  	// 5. наим. поля в форме 6. очищать поле в форме при добавление да-1, нет-0 ,7-справочник
  	var fldIndexRow = 0;
  	function deleteLastNewBorn() {
  		var node = document.getElementById('otherNewBorns').lastChild ;
  		node.parentNode.removeChild(node);
  	}
  	function addRow () {
  		var cmd = "" ;
  		for (var i=0;i<theFld.length;i++) {
  			cmd += "a"+theFld[i][1]+"=" ;  
  			if (theFld[i].length>9 &&theFld[i][9]!="") {
  				
  				cmd += "$('"+theFld[i][9]+"').value" ;
  				if (theFld[i][2]==1) {
  					cmd += ";a"+theFld[i][1]+"Name=" ; 
  					cmd += "$('"+theFld[i][9]+"').value" ;
  				}
  			} else {
  				cmd += "''" ;
  				if (theFld[i][2]==1) {
  					cmd += ";a"+theFld[i][1]+"Name=''" ;
  				}
  			}
  			cmd+=";" ;
  		}
  		eval(cmd) ;
  		var table = document.getElementById('otherNewBorns');
  		var row = document.createElement('TR');
  		var td = document.createElement('TD');
  		var tdDel = document.createElement('TD');
  		table.appendChild(row);
  		row.appendChild(td);
  		var txt ="" ;
  		
  		var js="" ;
  		var idChild=document.getElementById('otherNewBorns').children.length ;
  		txt+="<h3>Новорожденный " +idChild+"</h3>";
  		for (var i=0;i<theFld.length;i++) {

  			var fld_i = theFld[i] ;//alert(fld_i);
  			if (fld_i[8]==1) {txt+="<br/>";}
  			if (fld_i[2]==1) {
  			    txt+=fld_i[0]+": ";
                for (var ind=0;ind<vocValueList.vocs.length;ind++) {
                    var voc = vocValueList.vocs[ind] ;
                    if (voc.name==fld_i[7]) { //если справочник
                        //if (fld_i[5]!='idc10') {
                            if (fld_i[4] == 0) txt += " <input type='radio' name='" + fld_i[1] + idChild + "' id='" + fld_i[1] + idChild + "' value=''>не выбрано";
                            for (var ind1 = 0; ind1 < voc.values.length; ind1++) {
                                var vocVal = voc.values[ind1];
                                txt += " <input type='radio' name='" + fld_i[1] + idChild + "' id='" + fld_i[1] + idChild + "' value='" + vocVal.id + "' onclick=handleClick(this,'"+idChild+"')>" + vocVal.name;
                            }
                        break ;
                    }
                }
  			} else if (fld_i[2]==2) {
  				txt+=" "+fld_i[0]+": <input type='text' size='5' name='"+fld_i[1]+idChild+"' id='"+fld_i[1]+idChild+"' value='"+eval("a"+fld_i[1])+"'>"
  			} else if (fld_i[2]==3) {
  				txt+=" "+fld_i[0]+": <input type='text' size='10' name='"+fld_i[1]+idChild+"' id='"+fld_i[1]+idChild+"' value='"+eval("a"+fld_i[1])+"'>"
  				js += "new dateutil.DateField($('"+fld_i[1]+idChild+"')) ;"
  			} else if (fld_i[2]==4) {
  				txt+=" "+fld_i[0]+": <input type='text' size='5' name='"+fld_i[1]+idChild+"' id='"+fld_i[1]+idChild+"' value='"+eval("a"+fld_i[1])+"'>"
  				js += "new timeutil.TimeField($('"+fld_i[1]+idChild+"')) ;"
  			} else if (fld_i[2]==6) {
  				txt+=" "+fld_i[0]+": <input type='hidden'  name='"+fld_i[1]+idChild+"' id='"+fld_i[1]+idChild+"' value='"+eval("a"+fld_i[1])+"'>"
  			}
  		}
  		txt+="<div hidden id='divIdc10"+idChild+"'><label id='IdcLabel"+idChild+"' for='IdcName'>Акушерский диагноз:</label></td>"+
            "<div colspan='1' class='Idc10'><div><input size='1' name='Idc10"+idChild+"' "+
            "id='Idc10"+idChild+"' type='hidden'><input autocomplete='off' title='Idc10' name='Idc10"+idChild+"Name' id='Idc10"+idChild+"Name'"+
            " size='10' class='autocomplete horizontalFill' type='text'><div style='visibility: hidden; display: none;'"+
            " id='Idc10"+idChild+"Div'></div></div>" +
            "<label id='2IdcLabel"+idChild+"' for='IdcName'>Наименование:</label>" +
            "<input type='text' size='10' style='width: 400px;' name='2IdcLabel"+idChild+"' id='2IdcText"+idChild+"'></div>";
  		td.innerHTML=txt;
  		row.appendChild(tdDel);
  		eval(js) ;
  	}
    var closure = function(i) {
        return function() {
            var idc10Field=document.getElementById('Idc10'+i+'Name');
            var textField=document.getElementById('2IdcText'+i);
            if (textField!=null && idc10Field!=null) {
                textField.value=idc10Field.value;
            }
        };
    };
    var currentValue = [$('newBornAmount').value];
    function handleClick(myRadio,i) {
       // alert(myRadio.getAttribute("id").indexOf("LiveBorn"));
        if (myRadio.getAttribute("id").indexOf("LiveBorn") != -1) {
            if (myRadio.value == 2 && myRadio.value != currentValue[i - 1]) {
                var t = new msh_autocomplete.Autocomplete();
                t.setUrl('simpleVocAutocomplete/vocIdc10');
                t.setIdFieldId('Idc10' + i);
                t.setNameFieldId('Idc10' + i + 'Name');
                t.setDivId('Idc10' + i + 'Div');
                t.build();
                //$('currentIndexOdNewBorn').value=i;
                t.addOnChangeCallback(closure(i));
                var idc10div = document.getElementById('divIdc10' + i);
                if (idc10div != null) {
                    idc10div.removeAttribute('hidden');
                }
                currentValue[i - 1] = myRadio.value;
            }
            else if (myRadio.value == 1 && myRadio.value != currentValue[i - 1]) {
                var idc10div = document.getElementById('divIdc10' + i);
                if (idc10div != null) {
                    idc10div.setAttribute('hidden', true);
                }
                currentValue[i - 1] = myRadio.value;
            }
        }
    }
  	</script>
  </msh:ifFormTypeIsCreate>
  <msh:ifFormTypeIsNotView formName="preg_childBirthForm">
    	<script type="text/javascript">
    	var isChangeSizeEpicrisis=1 ;
		function changeSizeEpicrisis() {
			if (isChangeSizeEpicrisis==1) {
				Element.addClassName($('histology'), "protocols") ;
				if ($('changeSizeEpicrisisButton')) $('changeSizeEpicrisisButton').value='Уменьшить' ;
				isChangeSizeEpicrisis=0 ;
			} else {
				Element.removeClassName($('histology'), "protocols") ;
				if ($('changeSizeEpicrisisButton')) $('changeSizeEpicrisisButton').value='Увеличить' ;
				isChangeSizeEpicrisis=1;
			}
		}
		eventutil.addEventListener($('histology'), "dblclick", 
	  		  	function() {
					changeSizeEpicrisis() ;
	  		  	}) ;
    	</script>
    	</msh:ifFormTypeIsNotView>
  </tiles:put>
</tiles:insert>