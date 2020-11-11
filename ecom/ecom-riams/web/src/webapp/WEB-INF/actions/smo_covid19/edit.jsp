<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib uri="http://struts.apache.org/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://www.nuzmsh.ru/tags/msh" prefix="msh" %>
<%@ taglib uri="http://www.ecom-ast.ru/tags/ecom" prefix="ecom" %>

<tiles:insert page="/WEB-INF/tiles/mainLayout.jsp" flush="true">

  <tiles:put name="title" type="string">
      <ecom:titleTrail title="Covid 2019" mainMenu="Patient" beginForm="smo_covid19Form" />
  </tiles:put>
    <style type="text/css">
    #side ul li a#start,tr#start  {
    background-color: lightgrey;
    }
    #side ul li a#firstExport,tr#first {
        background-color: lightcyan;
    }
    #side ul li a#doubleExport,tr#double {
        background-color: palegreen;
    }
    #side ul li a#dischExport,tr#disch {
        background-color: lightcoral;
    }
    </style>
  <tiles:put name="body" type="string">
    <msh:form action="entityParentSaveGoView-smo_covid19.do" defaultField="cardNumber">
      <msh:hidden property="id" />
      <msh:hidden property="saveType" />
      <msh:hidden property="patient" />
      <msh:hidden property="medCase" />
      <msh:panel>
        <msh:row>
         <msh:textField property="cardNumber" size="50" />
        </msh:row><msh:row>
              <msh:textField property="workPlace" horizontalFill="true" />
          </msh:row><msh:row>
          <msh:row>
          <msh:checkBox property="isDoctor"  />
      </msh:row>
         <msh:textField property="symptomsDate" />
      </msh:row><msh:row>
         <msh:textField property="brigadeNumber"  horizontalFill="true"/>
      </msh:row><msh:row>
         <msh:autoComplete property="mkb" vocName="vocIdc10Covid" horizontalFill="true" />
      </msh:row><msh:row>
         <msh:textField property="diagnosisDate" />
      </msh:row><msh:row>
        <msh:autoComplete property="vacPnKok" vocName="vocYesNoMaybe" horizontalFill="true" />
      </msh:row><msh:row>
        <msh:autoComplete property="vacFlu" vocName="vocYesNoMaybe" horizontalFill="true" />
      </msh:row><msh:row>
          <msh:checkBox property="isPregnant"  />
      </msh:row><msh:row>
          <msh:autoComplete property="isAntivirus" vocName="vocYesNoMaybe" />
      </msh:row><msh:row>
          <msh:autoComplete property="isIvl" vocName="vocYesNoMaybe" />
      </msh:row><msh:row>
          <msh:textField property="soputBronho" horizontalFill="true" />
      </msh:row><msh:row>
          <msh:textField property="soputHeart" horizontalFill="true" />
      </msh:row><msh:row>
          <msh:textField property="soputEndo" horizontalFill="true" />
      </msh:row><msh:row>
          <msh:textField property="soputOnko" horizontalFill="true" />
      </msh:row><msh:row>
          <msh:textField property="soputSpid" horizontalFill="true" />
      </msh:row><msh:row>
          <msh:textField property="soputTuber" horizontalFill="true" />
      </msh:row><msh:row>
          <msh:textField property="soputOther" horizontalFill="true" />
      </msh:row>
          <msh:row>
              <msh:textField property="covidResearchDate" />
          </msh:row><msh:row>
          <msh:autoComplete property="labOrganization" horizontalFill="true" vocName="vocUserDomainNameById" parentId="269" />
      </msh:row><msh:row>
          <msh:textField property="epidNumber" horizontalFill="true" />
      </msh:row><msh:row>
          <msh:textField property="labResultNumber" horizontalFill="true" />
      </msh:row><msh:row>
          <msh:autoComplete vocName="positiveNegative" property="labResult" horizontalFill="true" />
      </msh:row><msh:row>
          <msh:textField property="saturationLevel" horizontalFill="true" />
      </msh:row><msh:row>
          <msh:autoComplete property="CT"  vocName="vocCT" horizontalFill="true"/>
      </msh:row><msh:row>
              <msh:textField property="dateCT" horizontalFill="true"/>
          </msh:row><msh:row>
            <msh:textField property="lpuCT" horizontalFill="true" size="95" />
      </msh:row>
        <msh:row>
            <msh:textField property="ishodDate" viewOnlyField="true"/>
        </msh:row><msh:row>
            <msh:autoComplete vocName="vocHospitalizationResult" viewOnlyField="true" property="hospResult" horizontalFill="true" />
        </msh:row>
          <msh:row>
          <msh:autoComplete property="mkbDischarge" viewOnlyField="true" vocName="vocIdc10" horizontalFill="true" />
      </msh:row>
        <msh:ifFormTypeIsView formName="smo_covid19Form">
            <msh:separator label="Дополнительная информация" colSpan="4"/>
          <msh:row>
              <msh:label property="createDate" label="Дата создания"/>
          </msh:row><msh:row>
              <msh:label property="createTime" label="время"/>
        </msh:row><msh:row>
              <msh:label property="createUsername" label="пользователь"/>
          </msh:row>

            <msh:panel  colsWidth="15%,10%,15%,10%,15%,10%">
              <msh:row styleId="start">
                <msh:label property="exportDate" label="Дата выгрузки"/>
                <msh:label property="exportTime" label="время выгрузки"/>
                <msh:label property="exportUsername" label="пользователь выгрузки"/>
              </msh:row>
                <msh:row>
                    <msh:separator label="Выгрузка карты" colSpan="4"/>
                </msh:row>
                <msh:row styleId="first">
                    <msh:label property="exportFirstDate" label="Дата ПЕРВИЧНОЙ выгрузки"/>
                    <msh:label property="exportFirstTime" label="время выгрузки"/>
                    <msh:label property="exportFirstUsername" label="пользователь выгрузки" />
                </msh:row>
                <msh:row styleId="double">
                    <msh:label property="exportDoubleDate" label="Дата ПОВТОРНОЙ выгрузки"/>
                    <msh:label property="exportDoubleTime" label="время выгрузки"/>
                    <msh:label property="exportDoubleUsername" label="пользователь выгрузки" />
                </msh:row>
                <msh:row  styleId="disch">
                    <msh:label property="exportDischargeDate" label="Дата выгрузки ПРИ ВЫПИСКЕ"/>
                    <msh:label property="exportDischargeTime" label="время выгрузки"/>
                    <msh:label property="exportDischargeUsername" label="пользователь выгрузки" />
                </msh:row>
            </msh:panel>
            <msh:ifFormTypeIsView formName="smo_covid19Form">
          <msh:panel title="Добавление контактного лица"/>
          <table>
          <tr><td>
              <label>Фамилия контакта: <input type="text" id="contactLastname" /></label>
          </td></tr><tr><td>
              <label> Имя: <input type="text" id="contactFirstname" /></label>
        </td></tr><tr><td>
              <label>Отчество: <input type="text" id="contactMiddlename" /></label>
        </td></tr><tr><td>
              <label>Дата рождения: <input type="text" id="contactBirthDate"  /></label>
        </td></tr><tr><td>
              <label>Телефон: <input type="text" id="contactPhone" /></label>
        </td></tr><tr><td>
            <label>Адрес: <input type="text" id="contactAddress" /></label>
        </td></tr><tr><td>
            <input type="button" value="Добавить контактное лицо" onclick="createContactPatient()">
        </td></tr></table>
            </msh:ifFormTypeIsView>
        </msh:ifFormTypeIsView>
        <msh:submitCancelButtonsRow colSpan="4" functionSubmit="save();"/>
      </msh:panel>
    </msh:form>
    <msh:ifFormTypeIsView formName="smo_covid19Form">
      <msh:ifInRole roles="/Policy/Mis/MedCase/Covid19/View">
        <msh:section>
          <msh:sectionTitle>Контакты</msh:sectionTitle>
          <msh:sectionContent>
          	<ecom:webQuery name="contacts" nativeSql="select co.id,coalesce(co.lastname,'')||' '||coalesce(co.firstname,'')||' '||coalesce(co.middlename,'')||' '||COALESCE(to_char(co.birthdate,'dd.MM.yyyy'),'') as fio
          	,co.phone, co.address from covid19 c
          	 left join covid19 allC on allC.patient_id=c.patient_id
          	 left join covid19Contact co on co.card_id=allC.id
          	 where c.id=${param.id} and co.id is not null
             order by co.lastname, co.firstname, co.middlename"/>
            <msh:table name="contacts" action="/javascript:void()" idField="1" noDataMessage="Нет контактов"
                       deleteUrl="entityParentDeleteGoParentView-smo_covid19Contact.do">
              <msh:tableColumn columnName="ФИО человека" property="2" />
              <msh:tableColumn columnName="Телефон" property="3" />
              <msh:tableColumn columnName="Адрес" property="4" />
            </msh:table>
          </msh:sectionContent>
        </msh:section>
      </msh:ifInRole>
    </msh:ifFormTypeIsView>

  </tiles:put>
  <tiles:put name="side" type="string">
    <msh:ifFormTypeIsView formName="smo_covid19Form">

        <msh:sideMenu title="COVID">
            <msh:sideLink styleId="start" params="id" confirm="Удалить?" action="/entityParentDelete-smo_covid19" name="Удалить" roles="/Policy/Mis/MedCase/Covid19/Delete" />
            <msh:sideLink styleId="start" action="/javascript:exportCard()" name="Карта выгружена" roles="/Policy/Mis/MedCase/Covid19/Export" />
        </msh:sideMenu>

        <msh:sideMenu title="">
          <msh:sideLink params="id" action="/entityParentEdit-smo_covid19" name="Изменить" roles="/Policy/Mis/MedCase/Covid19/Edit" />
        </msh:sideMenu>

        <msh:sideMenu title="Первично">
            <msh:sideLink styleId="firstExport" params="id" action="/print-covid_058.do?m=printCovid&s=PatientPrintService" name="Форма 058"/>
            <msh:sideLink styleId="firstExport" action="/javascript:exportCardNumber('First')" name="Выгружена первично" roles="/Policy/Mis/MedCase/Covid19/Export" />
            <msh:sideLink styleId="firstExport" action="/javascript:deleteExport('First')" name="Отменить первичную выгрузку" roles="/Policy/Mis/MedCase/Covid19/Export" />
        </msh:sideMenu>

        <msh:sideMenu title="Повторно">
            <msh:sideLink styleId="doubleExport" params="id" action="/print-covid_058_double.do?m=printCovid&s=PatientPrintService" name="Форма 058 (повторная)"/>
            <msh:sideLink styleId="doubleExport" action="/javascript:exportCardNumber('Double')" name="Выгружена повторно" roles="/Policy/Mis/MedCase/Covid19/Export" />
            <msh:sideLink styleId="doubleExport" action="/javascript:deleteExport('Double')" name="Отменить повторную выгрузку" roles="/Policy/Mis/MedCase/Covid19/Export" />
        </msh:sideMenu>

        <msh:sideMenu title="При выписке">
            <msh:sideLink styleId="dischExport" params="id" action="/print-covid_058_disch.do?m=printCovid&s=PatientPrintService" name="Форма 058 (выписная)"/>
            <msh:sideLink styleId="dischExport" action="/javascript:exportCardNumber('Discharge')" name="Выгружена при выписке" roles="/Policy/Mis/MedCase/Covid19/Export" />
            <msh:sideLink styleId="dischExport" action="/javascript:deleteExport('Discharge')" name="Отменить выгрузку при выписке" roles="/Policy/Mis/MedCase/Covid19/Export" />
        </msh:sideMenu>
          <msh:sideLink params="id" action="/print-covid_058_gep.do?m=printCovid&s=PatientPrintService" name="Форма 058 (Гепатит)"/>
    </msh:ifFormTypeIsView>

 </tiles:put>
  <tiles:put name="javascript" type="string">
      <script type='text/javascript' src='./dwr/interface/PatientService.js'></script>
    <script type="text/javascript">

        //проверка на заполнение обязательных полей
        //из-за редактирования с saveType=1 неправильно считается parent
        //если не заполнить поле и отправить форму, то id карты превращается в parent - id госпитализации
        //проверка заполнения КТ: либо не проводилось, либо КТ+дата+место проведения
        function save() {
            if (!$('workPlace').value || !$('mkb').value
                ||  !$('CT').value || (+$('CT').value>1 && (!$('lpuCT').value || !$('dateCT').value))) {
                showToastMessage('Заполните обязательные поля!', null, true, true, 3000);
                $('submitButton').removeAttribute('disabled');
            }
            else
                document.forms[0].submit();
        }
    </script>
      <msh:ifFormTypeIsCreate formName="smo_covid19Form">
          <script type='text/javascript'>
              PatientService.getOpenHospByPatient($('medCase').value, {
                  callback: function(hosp) {
                      if (hosp!=null) {
                          hosp = JSON.parse(hosp);
                          $('cardNumber').value=hosp.cardnumber;
                          $('medCase').value=hosp.hospid;
                          $('workPlace').value=hosp.workplace;
                          $('patient').value=hosp.patient;
                      }
                  }
              });
          </script>
      </msh:ifFormTypeIsCreate>
    <msh:ifFormTypeAreViewOrEdit formName="smo_covid19Form">


    
    <script type="text/javascript">
      onload = function() {
        $('saveType').value='1';
        if ($('contactBirthDate')) new dateutil.DateField($('contactBirthDate'));
      };



      function exportCard() { // отметка о выгрузке карты на портал
        PatientService.markCovidAsSent($('id').value, {
          callback: function () {
            window.document.location.reload();
          }
        });
      }

      //Отметка о выгрузке карты:
      //First - первично
      //Double - повторно
      //Discharge - при выписке
      function exportCardNumber(number) {
          var msg = ' первично.';
          if (number=='Double')
              msg = ' повторно.';
          else if (number=='Discharge')
              msg = ' при выписке';
          PatientService.markCovidAsSentNumberCheck($('id').value, number, {
              callback: function (res) {
                  if ((res=='-1' && confirm('Карта' + msg + ' уже была выгружена. Вы уверены?') || res!='-1')) {
                          PatientService.markCovidAsSentNumber($('id').value, number, {
                              callback: function () {
                                  window.document.location.reload();
                              }
                          });
                  }
              }
          });
      }

      //Убрать отметку о выгрузке карты
      function deleteExport(number) {
          if (confirm('Вы уверены?')) {
              PatientService.markCovidAsUnSent($('id').value, number, {
                  callback: function (res) {
                      window.document.location.reload();
                  }
              });
          }
      }

      function createContactPatient() { //создаем информацию о контактном пациенте
        var url = 'entityParentSave-smo_covid19Contact.do';
        jQuery.ajax({
          url:url
            ,type: "POST"
          ,data: {
            id:0 //При создании всегда ноль
            ,saveType:1
            ,card:${param.id} //Если есть родитель
            ,lastname:$('contactLastname').value  //1 значит создать, 2 - изменить существующее
            ,firstname:$('contactFirstname').value
            ,middlename:$('contactMiddlename').value
            ,birthDate:$('contactBirthDate').value
            ,phone:$('contactPhone').value
            ,address:$('contactAddress').value
          }
        }).done (function() {
            document.location.reload();
        });
          alert("Добавлено!");
          document.location.reload();
      }
      <msh:ifFormTypeIsView formName="smo_covid19Form">
        document.getElementById('labOrganization').removeAttribute('type');
      </msh:ifFormTypeIsView>
      <msh:ifFormTypeIsNotView formName="smo_covid19Form">
        $('labOrganizationName').value=$('labOrganization').value;
      </msh:ifFormTypeIsNotView>
      </script>
    </msh:ifFormTypeAreViewOrEdit>
      <msh:ifFormTypeIsNotView formName="smo_covid19Form">
          <script type="text/javascript">
          //Справочник КТ обязателен для заполнения, дата и место - если выбраны соотв. значения
          function setCT() {
              if (+$('CT').value > 1) {
                  $('dateCT').removeAttribute('hidden');
                  $('lpuCT').removeAttribute('hidden');
                  $('dateCT').className = "required";
                  $('lpuCT').className = "required";
              } else {
                  $('dateCT').setAttribute('hidden', true);
                  $('lpuCT').setAttribute('hidden', true);
                  $('dateCT').className = "";
                  $('lpuCT').className = "";
                  $('dateCT').value = '';
                  $('lpuCT').value = '';
              }
          }

          CTAutocomplete.addOnChangeCallback(function() {
              setCT();
          });
          setCT();
          </script>
      </msh:ifFormTypeIsNotView>
  </tiles:put>
</tiles:insert>

