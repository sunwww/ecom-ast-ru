<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib uri="http://struts.apache.org/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://www.nuzmsh.ru/tags/msh" prefix="msh" %>
<%@ taglib uri="http://www.ecom-ast.ru/tags/ecom" prefix="ecom" %>

<tiles:insert page="/WEB-INF/tiles/mainLayout.jsp" flush="true">

  <tiles:put name="title" type="string">
      <ecom:titleTrail title="Covid 2019" mainMenu="Patient" beginForm="smo_covid19Form" />
  </tiles:put>
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
         <msh:textField property="symptomsDate" />
      </msh:row><msh:row>
         <msh:textField property="brigadeNumber"  horizontalFill="true"/>
      </msh:row><msh:row>
         <msh:textField property="diagnosis" horizontalFill="true" />
      </msh:row><msh:row>
         <msh:autoComplete property="mkb" vocName="vocIdc10Covid" horizontalFill="true" />
      </msh:row><msh:row>
         <msh:textField property="diagnosisDate" />
      </msh:row><msh:row>
         <msh:textField property="covidResearchDate" />
      </msh:row><msh:row>
         <msh:textField property="labOrganization" horizontalFill="true" />
      </msh:row><msh:row>
         <msh:autoComplete vocName="positiveNegative" property="labResult" horizontalFill="true" />
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
      </msh:row><msh:row>
          <msh:textField property="saturationLevel" horizontalFill="true" />
        </msh:row>
        <msh:row>
            <msh:textField property="ishodDate" />
        </msh:row><msh:row>
            <msh:autoComplete vocName="covidResultAllValues" property="ishodResult" horizontalFill="true" />
        </msh:row>
        <msh:ifFormTypeAreViewOrEdit formName="smo_covid19Form">
            <msh:separator label="Дополнительная информация" colSpan="4"/>
          <msh:row>
              <msh:label property="createDate" label="Дата создания"/>
          </msh:row><msh:row>
              <msh:label property="createTime" label="время"/>
        </msh:row><msh:row>
              <msh:label property="createUsername" label="пользователь"/>
          </msh:row>
          <msh:row>
            <msh:label property="exportDate" label="Дата выгрузки"/>
          </msh:row><msh:row>
            <msh:label property="exportTime" label="время выгрузки"/>
        </msh:row><msh:row>
            <msh:label property="exportUsername" label="пользователь выгрузки"/>
          </msh:row>
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
        </msh:ifFormTypeAreViewOrEdit>
        <msh:submitCancelButtonsRow colSpan="4" />
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
        <msh:sideLink params="id" action="/entityParentEdit-smo_covid19" name="Изменить" roles="/Policy/Mis/MedCase/Covid19/Edit" />
        <msh:sideLink params="id" action="/entityParentDelete-smo_covid19" name="Удалить" roles="/Policy/Mis/MedCase/Covid19/Delete" />
        <msh:sideLink action="/javascript:exportCard()" name="Карта выгружена" roles="/Policy/Mis/MedCase/Covid19/Export" />
          <msh:sideLink params="id" action="/print-covid_058.do?m=printCovid&s=PatientPrintService" name="Форма 058"/>
          <msh:sideLink params="id" action="/print-covid_058_gep.do?m=printCovid&s=PatientPrintService" name="Форма 058 (Гепатит)"/>
          <msh:sideLink params="id" action="/print-covid_direct2.do?m=printCovid&s=PatientPrintService" name="Направление"/>

      </msh:sideMenu>
    </msh:ifFormTypeIsView>

 </tiles:put>
  <tiles:put name="javascript" type="string">
      <script type='text/javascript' src='./dwr/interface/PatientService.js'></script>
    <script type="text/javascript">
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
      </script>
    </msh:ifFormTypeAreViewOrEdit>

  </tiles:put>
</tiles:insert>

