<%@page import="ru.ecom.web.login.LoginInfo"%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://struts.apache.org/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://www.nuzmsh.ru/tags/msh" prefix="msh" %>
<%@ taglib tagdir="/WEB-INF/tags" prefix="tags" %>
<%@ taglib uri="http://www.ecom-ast.ru/tags/ecom" prefix="ecom" %>

<tiles:insert page="/WEB-INF/tiles/main${param.short}Layout.jsp" flush="true">
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
    <msh:sideMenu>
      <msh:sideLink roles="/Policy/Mis/Patient/Create" key="ALT+N" params="lastname,hiddendata" action='/entityPrepareCreate-mis_patient.do' name="Добавить персону" />
    </msh:sideMenu>
    <msh:sideMenu>
    	<msh:sideLink roles="/Policy/Mis/Patient/SocialCard" key="ALT+2" action="/findSocPat.do" name="Поиск персоны из соц.карты" title="Поиск персоны из соц.карты"/>
    </msh:sideMenu>
   </msh:ifInRole>
    <msh:sideMenu>
    	<msh:sideLink roles="/Policy/Mis/MedCase/Direction/JournalByUsername" name="Учет направлений" action="/smo_journalDirectionByUsername_list.do"/>
    	<msh:sideLink roles="/Policy/Mis/Worker/WorkCalendar/Pattern/Day/Time/Create" name="Создание дополнительного времени" action="/work_create_timeBySpecialist.do"/>
    	<msh:sideLink roles="/Policy/Mis/Worker/WorkCalendar/Pattern/Day/Time/Create" name="Создание времени по специалисту" action="/createNewTime.do"/>
    	<msh:sideLink roles="/Policy/Mis/MedCase/Direction/PreRecord" name="Пред. запись" action="/js-smo_direction-preRecorded.do"/>
    	<msh:sideLink roles="/Policy/Mis/MedCase/Direction/PreRecordMany" name="Пред. запись неск-ко специалистов" action="/js-smo_direction-preRecordedMany.do"/>
    	<msh:sideLink roles="/Policy/Mis/MedCase/Direction/Journal" name="Журнал направленных" action="/visit_journal_direction.do"/>
        <msh:sideLink roles="/Policy/Poly/Ticket/Create,/Policy/Poly/Ticket/Stream" action='/entityPrepareCreate-smo_short_spo_stream.do' name="Поточный ввод обращений" />
        <msh:sideLink roles="/Policy/Poly/Ticket/Create,/Policy/Poly/Ticket/Stream" action='/entityPrepareCreate-smo_spo_ticket_stream.do' name="Поточный ввод посещений" />

    </msh:sideMenu>

  </tiles:put>
  <tiles:put name="body" type="string">
  <%
  request.setAttribute("remoteAddress" , request.getRemoteAddr()) ;
  request.setAttribute("username" , LoginInfo.find(request.getSession(true)).getUsername()) ;
  %>
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
    <msh:form action="/mis_patients.do" defaultField="lastname" disableFormDataConfirm="true" method="GET">
      <msh:panel colsWidth="10%, 10%,10%,10%,10%, 70%">
        
        <msh:row>
          <msh:autoComplete fieldColSpan="6" property="lpuArea" label="Участок" horizontalFill="true" vocName="lpuAreaWithParent" />
        </msh:row>
        <msh:row>
          <msh:textField property="lastname" label="ФИО, полис или мед. карта" size="40" />
          <msh:textField property="year" label="Год рождения" size="4" />
          <td>
            <input type="submit" value="Найти" />
            <msh:ifInRole roles="/Policy/Mis/Patient/FindByBarcode">
            <input type="button" onclick="showcmdpasPassword()" value="Найти по УЭК" />
            </msh:ifInRole>
            <input type="hidden" name="hiddendata" id="hiddendata">
          </td>
        </msh:row>
        <msh:row>
          <msh:commentBox text="Фамилия Имя Отчество. &lt;i&gt;Например: ИВАНОВ ИВАН ИВАНОВИЧ или ИВАНОВ&lt;/i&gt;&lt;br/&gt;&#xA;Серия Номер Полиса. &lt;i&gt;Например: АА 1234567 или 1234567&lt;/i&gt;&lt;br/&gt;&#xA;Номер мед. карты." colSpan="2" />
        </msh:row>
      </msh:panel>
    </msh:form>

   </msh:ifInRole>
    <%  if(request.getAttribute("list_1") != null || request.getAttribute("list_2") != null || request.getAttribute("list_3") != null) {  %>
    <msh:ifInRole roles="/Policy/MainMenu/Patient">
      <msh:section title="Результат поиска">
      
        <msh:table viewUrl="entityShortView-mis_patient.do" name="list_1" 
        action="entityView-mis_patient.do" idField="id" disableKeySupport="true"
        navigationAction="mis_patients.do?${infoparam}"
        noDataMessage="" escapeSymbols="false"
        >
          <msh:tableColumn columnName="Код" property="patientSync" />
          <msh:tableColumn columnName="Фамилия" property="lastname" />
          <msh:tableColumn columnName="Имя" property="firstname" />
          <msh:tableColumn columnName="Отчество" property="middlename" />
          <msh:tableColumn columnName="Дата рождения" property="birthday" />
          <msh:tableColumn columnName="Дата прикрепления (тип) и ЛПУ" property="lpuName" />
          <msh:tableColumn columnName="Участок" property="lpuAreaName" />
          <msh:tableColumn columnName="Проверка по базе фонда" property="patientInfo" />
        </msh:table>
        
        <msh:table viewUrl="entityShortView-mis_patient.do" name="list_2" 
        action="entityView-mis_patient.do" idField="id" disableKeySupport="true"
        noDataMessage=""  escapeSymbols="false"
        >
          <msh:tableColumn columnName="Код" property="patientSync" />
          <msh:tableColumn columnName="Фамилия" property="lastname" />
          <msh:tableColumn columnName="Имя" property="firstname" />
          <msh:tableColumn columnName="Отчество" property="middlename" />
          <msh:tableColumn columnName="Дата рождения" property="birthday" />
          <msh:tableColumn columnName="Дата прикрепления (тип) и ЛПУ" property="lpuName" />
          <msh:tableColumn columnName="Участок" property="lpuAreaName" />
          <msh:tableColumn columnName="Проверка по базе фонда" property="patientInfo" />
        </msh:table>
        
        <msh:table viewUrl="entityShortView-mis_patient.do" name="list_3" 
        action="entityView-mis_patient.do" idField="id" disableKeySupport="true"
        noDataMessage="" escapeSymbols="false"
        >
          <msh:tableColumn columnName="Код" property="patientSync" />
          <msh:tableColumn columnName="Фамилия" property="lastname" />
          <msh:tableColumn columnName="Имя" property="firstname" />
          <msh:tableColumn columnName="Отчество" property="middlename" />
          <msh:tableColumn columnName="Дата рождения" property="birthday" />
          <msh:tableColumn columnName="Дата прикрепления (тип) и ЛПУ" property="lpuName" />
          <msh:tableColumn columnName="Участок" property="lpuAreaName" />
          <msh:tableColumn columnName="Проверка по базе фонда" property="patientInfo" />
        </msh:table>
        
      </msh:section>
      </msh:ifInRole>
      <% }%>
     
  </tiles:put>
  <tiles:put name="javascript" type="string">
      <script type="text/javascript" src="./dwr/interface/PatientService.js"></script>
    <script type="text/javascript">
    	try { lpuAreaAutocomplete.setParent(lpuAutocomplete); } catch (e) {} // FIXME forms
    	$('lastname').focus() ;
    	$('lastname').select() ;
	</script>
  </tiles:put>
</tiles:insert>

