<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://struts.apache.org/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://www.nuzmsh.ru/tags/msh" prefix="msh" %>
<%@ taglib tagdir="/WEB-INF/tags" prefix="tags" %>

<tiles:insert page="/WEB-INF/tiles/mainLayout.jsp" flush="true">
	<tiles:put name="style" type="string">
	<style type=text/css>
	object.hiddenObject
    {
        visibility: hidden;
        width: 0px;
        height: 0px;
        margin: 0px;
        padding: 0px;
        border-style: none;
        border-width: 0px;
        max-width: 0px;
        max-height: 0px;
    }
	</style>
	</tiles:put>
  <tiles:put name="title" type="string">
    <msh:ifInRole roles="/Policy/MainMenu/Patient">
    	<msh:title mainMenu="Patient" title="Поиск персон" />
    </msh:ifInRole>
    <msh:ifNotInRole roles="/Policy/MainMenu/Patient">
    	<msh:title mainMenu="Patient" title="" />
    </msh:ifNotInRole>
  </tiles:put>
  <tiles:put name="side" type="string">
  <msh:ifInRole roles="/Policy/MainMenu/Patient">
    <msh:sideMenu guid="5120ac2f-43a7-4204-a2e7-187cf4969bcc">
      <msh:sideLink roles="/Policy/Mis/Patient/Create" key="ALT+N" params="lastname,hiddendata" action="/entityPrepareCreate-mis_patient" name="Добавить персону" guid="4cecc5e2-4e6b-4196-82ef-bf68124d90a5" />
    </msh:sideMenu>
    <msh:sideMenu>
    	<msh:sideLink roles="/Policy/Mis/Patient/SocialCard" key="ALT+2" action="/findSocPat.do" name="Поиск персоны из соц.карты" title="Поиск персоны из соц.карты"/>
    </msh:sideMenu>
   </msh:ifInRole>
    <msh:sideMenu>
    	<msh:sideLink roles="/Policy/Mis/MedCase/Direction/JournalByUsername" name="Учет направлений" action="/smo_journalDirectionByUsername_list.do"/>
    	<msh:sideLink roles="/Policy/Mis/Worker/WorkCalendar/Pattern/Day/Time/Create" name="Создание дополнительного времени" action="/work_create_timeBySpecialist.do"/>
    	<msh:sideLink roles="/Policy/Mis/MedCase/Direction/PreRecord" name="Пред. запись" action="/js-smo_direction-preRecorded.do"/>
    	<msh:sideLink roles="/Policy/Mis/MedCase/Direction/PreRecordMany" name="Пред. запись неск-ко специалистов" action="/js-smo_direction-preRecordedMany.do"/>
    	<msh:sideLink roles="/Policy/Mis/MedCase/Direction/Journal" name="Журнал направленных" action="/visit_journal_direction.do"/>
    </msh:sideMenu>

  </tiles:put>
  <tiles:put name="body" type="string">
	<object id="cadesplugin"  class="hiddenObject" type="application/x-cades">
    </object>
    <applet code="CommRead" archive="js-mis_patient-CommRead.do, js-mis_patient-jssc.do, js-mis_patient-barcode.do" width=1 height=1>
    <param name = "MAYSCRIPT" value = "TRUE">
    <param name = "comport" value = "COM3">
    </applet>
    
  <msh:ifNotInRole roles="/Policy/MainMenu/Patient">
	    <msh:sideLink roles="/Policy/Mis/MedCase/Stac/Ssl/View" 
	    	styleId="stac_findSlsByStatCard"
	    	action="/stac_findSlsByStatCard" name="Поиск по номеру стат.карты"
	    	/>
	    		        <msh:sideLink roles="/Policy/Mis/MedCase/Stac/Journal/InformationBesk"  
	        styleId="stac_journalByInformationBesk" 
	        action="/stac_journalByInformationBesk" name="Стол справок" />
	        <msh:sideLink roles="/Policy/Mis/MedCase/Stac/Journal/AddressList"
	        styleId="stac_address_list"
	        action="/stac_address_list" name="Адресный листок"
	        />
	        <msh:sideLink
	    		roles="/Policy/Mis/MedCase/Stac/Journal/ByHospital" 
	    		action="/stac_reestrByHospital" name="По поступившим/ выбывшим из стационара за день"
	    		styleId="stac_reestrByHospital"
	    	/>
	    	<msh:sideLink 
		        styleId="stac_journalByDepartmentAdmission" 
		        roles="/Policy/Mis/MedCase/Stac/Journal/ByDepartmentAdmission"  
		        action="/stac_journalByDepartmentAdmission" name="По направленным в отделение" />
	        <msh:sideLink
		        styleId="stac_journalCurrentByUserDepartment" 
		         roles="/Policy/Mis/MedCase/Stac/Journal/CurrentByUserDepartment"  
		         action="/stac_journalCurrentByUserDepartment" name="По состоящим в отделении пациентам" />
	        <msh:sideLink 
		        styleId="stac_journalByCurator" 
		        roles="/Policy/Mis/MedCase/Stac/Journal/ByCurator"  
		        action="/stac_journalByCurator" name="По лечащему врачу" />  

	<msh:sideLink name="Журнал посещений к другим специалистам"
	 action="/poly_directOtherSpecialist.do"
		styleId="reportDirectOtherSpec" roles="/Policy/Mis/MedCase/Visit/DirectOtherSpecialist"/>
		        
		        
    <msh:sideLink action="/js-smo_visit-findPolyAdmissions.do" name="Рабочий календарь"
    	 styleId="workCalendar" roles="/Policy/Mis/MedCase/Visit/View"/>
    <msh:sideLink styleId="otherWorkFunction" 
    	name="Пациенты, направленные по другим раб. функциям" 
    	title="Пациенты, направленные по другим раб.функциям" 
    	action="/js-smo_visit-findOtherFunctionsPolyAdmissions.do" />    
    <msh:sideLink styleId="otherDays" 
    	name="Принятые пациенты, направленные на другое число" 
    	title="Принятые пациенты, направленные на другое число" 
    	action="/js-smo_visit-findOtherDaysPolyAdmissions.do" />    

  	
  </msh:ifNotInRole>
  <msh:ifInRole roles="/Policy/MainMenu/Patient">
    <msh:form action="/mis_patients.do" defaultField="lastname" disableFormDataConfirm="true" method="GET" guid="d7b31bc2-38f0-42cc-8d6d-19395273168f">
      <msh:panel colsWidth="10%, 10%, 70%" guid="354f9651-7a86-447b-9066-43af5b3bf277">
        <msh:row guid="7648279d-8e6a-4004-aaee-efe8ba8287dc">
          <msh:autoComplete guid="lpuGuid" fieldColSpan="2" property="lpu" label="ЛПУ" horizontalFill="true" vocName="lpu" viewAction="hidden" />
        </msh:row>
        <msh:row guid="df2f72c4-96d1-49ff-a57d-9caade9a77a1">
          <msh:autoComplete fieldColSpan="2" property="lpuArea" label="Участок" horizontalFill="true" vocName="lpuAreaWithParent" guid="92c3087f-9109-4109-8659-151717d81beb" />
        </msh:row>
        <msh:row guid="6ebb763c-58d4-45f6-928e-2d03a5b55b5b">
          <msh:textField property="lastname" label="ФИО, полис или мед. карта" size="40" guid="56502d8a-33ae-463c-910b-59625f2d2778" />
          <td>
            <input type="submit" value="Найти" />
            <input type="button" onclick="showcmdpasPassword()" value="Найти по УЭК" />
            <input type="hidden" name="hiddendata" id="hiddendata">
          </td>
        </msh:row>
        <msh:row guid="b729833a-a47b-437e-8d1c-b3362a03ce80">
          <msh:commentBox text="Фамилия Имя Отчество. &lt;i&gt;Например: ИВАНОВ ИВАН ИВАНОВИЧ или ИВАНОВ&lt;/i&gt;&lt;br/&gt;&#xA;Серия Номер Полиса. &lt;i&gt;Например: АА 1234567 или 1234567&lt;/i&gt;&lt;br/&gt;&#xA;Номер мед. карты." guid="5c197db1-df55-446f-ada6-da48c26f4a6c" colSpan="2" />
        </msh:row>
      </msh:panel>
    </msh:form>
        <tags:password command="ShowCardData(aObj)" name="cmdpas" title="Ввод пароля"/>
    
   </msh:ifInRole>
    <%  if(request.getAttribute("list") != null) {  %>
    <msh:ifInRole roles="/Policy/MainMenu/Patient">
      <msh:section title="Результат поиска" guid="8bc5fc1c-72bb-45c8-9eb2-58715c967b81">
        <msh:table viewUrl="entityShortView-mis_patient.do" name="list" action="entityView-mis_patient.do" idField="id" disableKeySupport="true" guid="7df98006-d2f7-4055-98a4-3b687377d9be" noDataMessage="Не найдено">
          <msh:tableColumn columnName="Код" property="patientSync" guid="89c74-a164-4c5f-8fa9-5501c300bbf2" />
          <msh:tableColumn columnName="Фамилия" property="lastname" guid="87779c74-a164-4c5f-8fa9-5501c300bbf2" />
          <msh:tableColumn columnName="Имя" property="firstname" guid="88842354-b7d1-4c67-a43e-9837c179d5d1" />
          <msh:tableColumn columnName="Отчество" property="middlename" guid="4b8cb842-fcfb-4e91-b57f-ed881a1881c5" />
          <msh:tableColumn columnName="Дата рождения" property="birthday" guid="e63b0a34-7d09-4345-98c9-d9c0e37b69f4" />
          <msh:tableColumn columnName="Прикрепленное ЛПУ" property="lpuName" guid="210f1c10-2013-4a05-8ceb-af7d2d06694e" />
          <msh:tableColumn columnName="Участок" property="lpuAreaName" guid="44b16e3d-45a1-49a9-9b53-9a17320e0c67" />
        </msh:table>
      </msh:section>
      </msh:ifInRole>
      <% }%>
     
  </tiles:put>
  <tiles:put name="javascript" type="string">
    <script type="text/javascript">
    	
    	try { lpuAreaAutocomplete.setParent(lpuAutocomplete); } catch (e) {} // FIXME forms
    	$('lastname').focus() ;
    	$('lastname').select() ;
    
	
    var CADESCOM_CADES_X_LONG_TYPE_1 = 0x5d;
    var CADESCOM_CADES_BES = 1;
    var CAPICOM_CURRENT_USER_STORE = 2;
    var CADESCOM_ENCODE_BASE64 = 0;
    var CADESCOM_ENCODE_BINARY = 1;
    var CAPICOM_MY_STORE = "My";
    var CAPICOM_STORE_OPEN_MAXIMUM_ALLOWED = 2;
    var CAPICOM_CERTIFICATE_FIND_SUBJECT_NAME = 1;
    var CAPICOM_AUTHENTICATED_ATTRIBUTE_SIGNING_TIME = 0;
    var CADESCOM_AUTHENTICATED_ATTRIBUTE_DOCUMENT_NAME = 1;
    function GetErrorMessage(e) {
        var err = e.number;
        if (!err) {
			err = e;
        } else if (e.number) {
            err += " (" + e.number + ")";
        }
        return err;
    }
	
    function CreateObject(name) {
        switch (navigator.appName) {
            case "Microsoft Internet Explorer":
                return new ActiveXObject(name);
            default:
                var userAgent = navigator.userAgent;
                if (userAgent.match(/Trident\/./i)) { // IE11
                    return new ActiveXObject(name);
                }
                var cadesobject = document.getElementById("cadesplugin");
                return cadesobject.CreateObject(name);
        }
    }
    

    function ShowCardData(sPin1) {
        try {
            var oCard = CreateObject("CAdESCOM.UECard");
            
			
            oCard.SetPin1(sPin1);
            var oCardholderData = oCard.CardholderData;
            oText = document.getElementById("lastname");
            CardHolderDOB = oCardholderData.DateOfBirth[6]+oCardholderData.DateOfBirth[7]
            +"."+oCardholderData.DateOfBirth[4]+oCardholderData.DateOfBirth[5]
            +"."+oCardholderData.DateOfBirth[0]+oCardholderData.DateOfBirth[1]
            +oCardholderData.DateOfBirth[2]+oCardholderData.DateOfBirth[3];
            oText.value = oCardholderData.CardholderLastName + " " 
            + oCardholderData.CardholderFirstName + " " 
            + oCardholderData.CardholderSecondName + " "
            + CardHolderDOB;
            $('hiddendata').value= oCardholderData.CardholderLastName + "#" 
            + oCardholderData.CardholderFirstName + "#" 
            + oCardholderData.CardholderSecondName + "#"
            + CardHolderDOB + "#" + oCardholderData.OMSNumber+"#"
            + oCardholderData.SocialAccountNumber + "#" + oCardholderData.Sex; 
            document.forms[0].submit() ;
            

        } catch (err) {
            alert("Ошибка: " + GetErrorMessage(err));
            return;
        }
    }
	</script>
  </tiles:put>
</tiles:insert>

