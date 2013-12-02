<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://struts.apache.org/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://www.nuzmsh.ru/tags/msh" prefix="msh" %>
<%@ taglib uri="http://www.ecom-ast.ru/tags/ecom" prefix="ecom" %>
<%@ taglib tagdir="/WEB-INF/tags" prefix="tags" %>

<tiles:insert page="/WEB-INF/tiles/mainLayout.jsp" flush="true" >

  <tiles:put name="title" type="string">
    <msh:title guid="helloItle-123" mainMenu="Journals" title="Журнал сообщений в полицию"></msh:title>
  </tiles:put>
  <tiles:put name="side" type="string">
  	<tags:style_currentMenu currentAction="stac_criminalMessages" />
    	<tags:mis_journal />
  </tiles:put>
  <tiles:put name="body" type="string">
    <msh:form action="/journal_militiaMessage.do" defaultField="pigeonHoleName" disableFormDataConfirm="true" method="GET" guid="d7b31bc2-38f0-42cc-8d6d-19395273168f">
    <msh:panel guid="6ae283c8-7035-450a-8eb4-6f0f7da8a8ff">
    <input type="hidden" name="s" id="s" value="HospitalPrintService" />
    <input type="hidden" name="m" id="m" value="printReestrByDay" />
    <input type="hidden" name="id" id="id" value=""/>
    
      <msh:row guid="53627d05-8914-48a0-b2ec-792eba5b07d9">
        <msh:separator label="Параметры поиска" colSpan="7" guid="15c6c628-8aab-4c82-b3d8-ac77b7b3f700" />
      </msh:row>
      <msh:row guid="7d80be13-710c-46b8-8503-ce0413686b69">
      	<msh:autoComplete property="pigeonHole" fieldColSpan="7" 
      		horizontalFill="true" label="Приемник"
      		vocName="vocPigeonHole"
      		/>
      </msh:row>
      <msh:row>
        <td class="label" title="Поиск по показаниям поступления (typeEmergency)" colspan="1"><label for="typeEmergencyName" id="typeEmergencyLabel">Показания:</label></td>
        <td onclick="this.childNodes[1].checked='checked';"  colspan="2">
        	<input type="radio" name="typeEmergency" value="1">  экстренные
        </td>
        <td onclick="this.childNodes[1].checked='checked';"  colspan="2">
        	<input type="radio" name="typeEmergency" value="2" >  плановые
        </td>
        <td onclick="this.childNodes[1].checked='checked';"  colspan="2">
        	<input type="radio" name="typeEmergency" value="3">  все
        </td>
      </msh:row>
      <msh:row guid="7d80be13-710c-46b8-8503-ce0413686b69">
        <td class="label" title="Поиск по дате  (typeDate1)" colspan="1"><label for="typeDateName" id="typeDateLabel">Искать по дате:</label></td>
        <td onclick="this.childNodes[1].checked='checked';" colspan="2">
        	<input type="radio" name="typeDate1" value="1">  поступления
        </td>
        <td onclick="this.childNodes[1].checked='checked';" colspan="2">
        	<input type="radio" name="typeDate1" value="2">  регистрации
        </td>
        <td onclick="this.childNodes[1].checked='checked';" colspan="3">
        	<input type="radio" name="typeDate1" value="3">  когда произошло событие
        </td>
        </msh:row>
        <msh:row>
	        <td class="label" title="Просмотр данных (typeView)" colspan="1"><label for="typeViewName" id="typeViewLabel">Отобразить:</label></td>
	        <td onclick="this.childNodes[1].checked='checked';"  colspan="2">
	        	<input type="radio" name="typeView1" value="1">  реестр
	        </td>
	        <td onclick="this.childNodes[1].checked='checked';"  colspan="4">
	        	<input type="radio" name="typeView1" value="2">  реестр по приказу минзрава 03.02.2010 №42-Пр/13 
	        </td>
        </msh:row>
        <msh:row>
            <td></td>
 	        <td onclick="this.childNodes[1].checked='checked';"  colspan="2">
	        	<input type="radio" name="typeView1" value="3"  >  свод по дням 
	        </td>
	        <td onclick="this.childNodes[1].checked='checked';"  colspan="2">
	        	<input type="radio" name="typeView1" value="4"  >  общий свод по госпитализациям
	        </td>
        </msh:row>
        <msh:row>
            <td></td>
            <td onclick="this.childNodes[1].checked='checked';"  colspan="4">
	        	<input type="radio" name="typeView1" value="5"  >  общий свод отказов от госпитализаций
	        </td>
	        <td onclick="this.childNodes[1].checked='checked';"  colspan="2">
	        	<input type="radio" name="typeView1" value="6"  >  общий свод по обращениям
	        </td>
        </msh:row>
        <msh:row>
            <td></td>
	        <td onclick="this.childNodes[1].checked='checked';"  colspan="4">
	        	<input type="radio" name="typeView1" value="7">  реестр МДГП-ЦП 
	        </td>            
        </msh:row>
      <msh:row>
        <msh:textField fieldColSpan="2" property="dateBegin" label="Период с" guid="8d7ef035-1273-4839-a4d8-1551c623caf1" />
        <msh:textField property="dateEnd" label="по" guid="f54568f6-b5b8-4d48-a045-ba7b9f875245" />
      </msh:row>
        <msh:row>
        	<msh:autoComplete vocName="vocCriminalPhoneMessageType" property="phoneMessageType" label="Тип" fieldColSpan="5" horizontalFill="true"/>
        </msh:row>
        <msh:row>
        	<msh:autoComplete vocName="vocPhoneMessageSubType" property="phoneMessageSubType" parentAutocomplete="phoneMessageType" label="Описание" fieldColSpan="5" horizontalFill="true"/>
        </msh:row>
        <msh:row>
        	<msh:autoComplete property="department" fieldColSpan="7" horizontalFill="true" label="Отделение" vocName="lpu"/>
        </msh:row>
      <msh:row>
           <td colspan="11">
            <input type="submit" onclick="find()" value="Найти" />
            <input type="submit" onclick="print()" value="Печать реестра" />
<%--        <input type="submit" onclick="printNew()" value="Печать (по отделениям)" />
            <input type="submit" onclick="printNew1()" value="Печать (сопроводительного листа) 1" />
            <input type="submit" onclick="printNew2()" value="Печать (сопроводительного листа) 2" />
             --%>
          </td>
      </msh:row>
      
    </msh:panel>
    </msh:form>
    <script type='text/javascript'>
    
    checkFieldUpdate('typeDate1','${typeDate1}',1) ;
    checkFieldUpdate('typeEmergency','${typeEmergency}',3) ;
    checkFieldUpdate('typeView1','${typeView1}',1) ;
  
   function checkFieldUpdate(aField,aValue,aDefaultValue) {
   	eval('var chk =  document.forms[0].'+aField) ;
   	var aMax=chk.length ;
   	//alert(aField+" "+aValue+" "+aMax+" "+chk) ;
   	if ((+aValue)==0 || (+aValue)>(+aMax)) {
   		chk[+aDefaultValue-1].checked='checked' ;
   	} else {
   		chk[+aValue-1].checked='checked' ;
   	}
   }
			 
    function find() {
    	var frm = document.forms[0] ;
    	frm.target='' ;
    	frm.action='journal_militiaMessage.do' ;
    }
    function print() {
    	var frm = document.forms[0] ;
    	frm.m.value="printCriminalMessage" ;
    	frm.s.value="HospitalPrintReport" ;
    	frm.target='_blank' ;
    	frm.action='print-stac_criminalMessage.do' ;
    	
    }
    </script>

    
    <%
    String date = (String)request.getParameter("dateBegin") ;
    String date1 = (String)request.getParameter("dateEnd") ;
    
    if (date!=null && !date.equals(""))  {
    	if (date1==null ||date1.equals("")) {
    		request.setAttribute("dateEnd", date);
    	} else {
    		request.setAttribute("dateEnd", date1) ;
    	}
    	String view = (String)request.getAttribute("typeView1") ;
    	String pigeonHole1="" ;
    	String pigeonHole="" ;
    	String pHole = request.getParameter("pigeonHole") ;
    	if (pHole!=null && !pHole.equals("") && !pHole.equals("0")) {
    		pigeonHole1= " and (ml.pigeonHole_id='"+pHole+"' or ml1.pigeonHole_id='"+pHole+"')" ;
    		pigeonHole= " and ml.pigeonHole_id='"+pHole+"'" ;
    	}
    	request.setAttribute("pigeonHole", pigeonHole) ;
    	request.setAttribute("pigeonHole1", pigeonHole1) ;
    	
    	String phoneMessageType = request.getParameter("phoneMessageType") ;
    	if (phoneMessageType!=null && !phoneMessageType.equals("") && !phoneMessageType.equals("0")) {
    		request.setAttribute("phoneMessageType", " and pm.phoneMessageType_id='"+phoneMessageType+"'") ;
    	}
    	String phoneMessageSubType = request.getParameter("phoneMessageSubType") ;
    	if (phoneMessageSubType!=null && !phoneMessageSubType.equals("") && !phoneMessageSubType.equals("0")) {
    		request.setAttribute("phoneMessageSubType", " and pm.phoneMessageSubType_id='"+phoneMessageSubType+"'") ;
    	}
    	
    	String department="" ;
    	String dep = request.getParameter("department") ;
    	if (dep!=null && !dep.equals("") && !dep.equals("0")) {
    		department= " and ml.id='"+dep+"'" ;
    	}
    	request.setAttribute("department", department) ;
    	
    	String typeDate1 = (String)request.getAttribute("typeDate1") ;
    	if (typeDate1!=null && typeDate1.equals("1")) {
    		request.setAttribute("paramDate","m.dateStart") ;
    		request.setAttribute("paramDateInfo","Дата поступления") ;
    	} else if (typeDate1!=null && typeDate1.equals("3")) {
    		request.setAttribute("paramDate", "pm.whenDateEventOccurred") ;
    		request.setAttribute("paramDateInfo", "Дата, когда произошло событие") ;
    	} else {
    		request.setAttribute("paramDate","pm.phoneDate") ;
    		request.setAttribute("paramDateInfo","Дата регистрации сообщения") ;
    	}
    	%>
    	<%if (view!=null && (view.equals("1"))) {%>
    
    <msh:section>
    <msh:sectionTitle>Реестр с ${param.dateBegin} по ${param.dateEnd}.</msh:sectionTitle>
    <msh:sectionContent>
    <ecom:webQuery name="journal_militia" nativeSql="
    select pm.id, pm.phoneDate
    ,vpht.name||coalesce(' '||vpmst.name,'')
    ,to_char(pm.whenDateEventOccurred,'dd.mm.yyyy')||' '||cast(pm.whenTimeEventOccurred as varchar(5)) as whenevent
    ,pm.place as pmplace
    ,coalesce(vpme.name,pm.recieverFio) as reciever
    ,vpmo.name as vphoname,wp.lastname as wplastname
    ,p.lastname||' '||p.firstname||' '||p.middlename||' г.р.'||to_char(p.birthday,'dd.mm.yyyy') as fiopat
    ,coalesce(vpmorg.name,pm.phone,pm.recieverOrganization) as organization
    ,pm.diagnosis as pmdiagnosis
    from PhoneMessage pm 
    left join VocPhoneMessageType vpht on vpht.id=pm.phoneMessageType_id
    left join VocPhoneMessageSubType vpmst on vpmst.id=pm.phoneMessageSubType_id
    left join VocPhoneMessageOrganization vpmorg on vpmorg.id=pm.organization_id
    left join VocPhoneMessageEmploye vpme on vpme.id=pm.recieverEmploye_id
    left join VocPhoneMessageOutcome vpmo on vpmo.id=pm.outcome_id
    left join WorkFunction wf on wf.id=pm.workFunction_id
    left join Worker w on w.id=wf.worker_id
    left join Patient wp on wp.id=w.person_id
    left join medcase m on m.id=pm.medCase_id
    left join Patient p on p.id=m.patient_id
	left join MisLpu as ml on ml.id=m.department_id 
	left join SecUser su on su.login=m.username
	left join WorkFunction wf1 on wf1.secUser_id=su.id
	left join Worker w1 on w1.id=wf1.worker_id
	left join MisLpu ml1 on ml1.id=w1.lpu_id     
    where pm.dtype='CriminalPhoneMessage'
    and ${paramDate} between to_date('${param.dateBegin}','dd.mm.yyyy')  and to_date('${dateEnd}','dd.mm.yyyy')  
and ( m.noActuality is null or m.noActuality='0')
${period}
${emerIs} ${pigeonHole} ${department} ${phoneMessageType} ${phoneMessageSubType}
    order by ${paramDate}
    " guid="4a720225-8d94-4b47-bef3-4dbbe79eec74" />
    <msh:table name="journal_militia"
    viewUrl="entityShortView-stac_criminalMessages.do" 
     action="entityParentView-stac_criminalMessages.do" idField="1" >
      <msh:tableColumn columnName="Дата" property="2" />
      <msh:tableColumn property="9" columnName="Пациент"/>
      <msh:tableColumn columnName="Тип" property="3" />
      <msh:tableColumn columnName="Когда" property="4" />
      <msh:tableColumn columnName="Место" property="5" />
      <msh:tableColumn columnName="Фамилия принявшего" property="6" />
      <msh:tableColumn columnName="Фамилия передавшего" property="8" />
      <msh:tableColumn columnName="Диагноз" property="11" />
      <msh:tableColumn columnName="Исход" property="7" />
    </msh:table>
    </msh:sectionContent>
    </msh:section>
    <% }  
    if (view!=null && (view.equals("2"))) {%>
    
    <msh:section>
    <ecom:webQuery nameFldSql="journal_militia_sql" name="journal_militia" nativeSql="
    select pm.id, 
    p.lastname||' '||p.firstname||' '||p.middlename as fiopat
    ,pol.series||' '||polNumber as seriesPolicy
    ,ri.name as rename
    ,p.passportSeries||' '||p.passportNumber as passportInfo
    ,to_char(p.birthday,'dd.mm.yyyy') as pbirthday
    ,vs.name as vsname
    , case when p.address_addressId is not null 
          then coalesce(a.fullname,a.name) 
               ||case when p.houseNumber is not null and p.houseNumber!='' then ' д.'||p.houseNumber else '' end
               ||case when p.houseBuilding is not null and p.houseBuilding!='' then ' корп.'|| p.houseBuilding else '' end
	       ||case when p.flatNumber is not null and p.flatNumber!='' then ' кв. '|| p.flatNumber else '' end
       when p.territoryRegistrationNonresident_id is not null
	  then okt.name||' '||p.RegionRegistrationNonresident||' '||oq.name||' '||p.SettlementNonresident
	       ||' '||ost.name||' '||p.StreetNonresident
               ||case when p.HouseNonresident is not null and p.HouseNonresident!='' then ' д.'||p.HouseNonresident else '' end
	       ||case when p.BuildingHousesNonresident is not null and p.BuildingHousesNonresident!='' then ' корп.'|| p.BuildingHousesNonresident else '' end
	       ||case when p.ApartmentNonresident is not null and p.ApartmentNonresident!='' then ' кв. '|| p.ApartmentNonresident else '' end
       else  p.foreignRegistrationAddress end as address
    ,to_char(m.dateStart,'dd.mm.yyyy') as mdateStart
    ,to_char(m.dateFinish,'dd.mm.yyyy') as mdateFinish
    ,ml.name as mlname
    
    ,to_char(pm.whenDateEventOccurred,'dd.mm.yyyy')||' '||cast(pm.whenTimeEventOccurred as varchar(5))
    ||' '||vpht.name||coalesce(' '||vpmst.name,'') 
    ||' '||pm.place as pmplace
    ,pm.diagnosis as pmdiagnosis
    ,vpmo.name||case when vho.id is null then '' else ' - '||vho.name end as vphoname
    ,case when m.dtype='HospitalMedCase' and m.deniedHospitalizating_id is null then 'Стационар'
          when m.dtype='HospitalMedCase' and m.deniedHospitalizating_id is not null then 'Помощь в приемном отделении'
     else m.dtype end as typeHelp
    from PhoneMessage pm 
    left join VocPhoneMessageType vpht on vpht.id=pm.phoneMessageType_id
    left join VocPhoneMessageSubType vpmst on vpmst.id=pm.phoneMessageSubType_id
    left join VocPhoneMessageOrganization vpmorg on vpmorg.id=pm.organization_id
    left join VocPhoneMessageEmploye vpme on vpme.id=pm.recieverEmploye_id
    left join VocPhoneMessageOutcome vpmo on vpmo.id=pm.outcome_id
    left join WorkFunction wf on wf.id=pm.workFunction_id
    left join Worker w on w.id=wf.worker_id
    left join Patient wp on wp.id=w.person_id
    
    left join medcase m on m.id=pm.medCase_id
    left join medcase_medpolicy mcmp on mcmp.medcase_id=m.id
    left join medpolicy pol on pol.id=mcmp.policies_id
    left join reg_ic ri on ri.id=pol.company_id
    left join VocHospitalizationOutcome vho on vho.id=m.outcome_id
    left join Patient p on p.id=m.patient_id
    left join VocSex vs on vs.id=p.sex_id
    left join Address2 a on a.addressId=p.address_addressId
    left join Omc_KodTer okt on okt.id=p.territoryRegistrationNonresident_id
    left join Omc_Qnp oq on oq.id=p.TypeSettlementNonresident_id
    left join Omc_StreetT ost on ost.id=p.TypeStreetNonresident_id
    
    left join MisLpu as ml on ml.id=m.department_id 
    left join SecUser su on su.login=m.username
    left join WorkFunction wf1 on wf1.secUser_id=su.id
    left join Worker w1 on w1.id=wf1.worker_id
    left join MisLpu ml1 on ml1.id=w1.lpu_id     
    where pm.dtype='CriminalPhoneMessage'
    and ${paramDate} between to_date('${param.dateBegin}','dd.mm.yyyy')  and to_date('${dateEnd}','dd.mm.yyyy')  
and ( m.noActuality is null or m.noActuality='0')
${period}
${emerIs} ${pigeonHole} ${department} ${phoneMessageType} ${phoneMessageSubType}
    order by ${paramDate}
    " guid="4a720225-8d94-4b47-bef3-4dbbe79eec74" />
    <msh:sectionTitle>
    
    <form action="print-stac_criminalMessage_pr42.do" method="post" target="_blank">
    Реестр с ${param.dateBegin} по ${param.dateEnd}.
    <input type='hidden' name="sqlText" id="sqlText" value="${journal_militia_sql}"> 
    <input type='hidden' name="sqlInfo" id="sqlInfo" value="Период с ${param.dateBegin} по ${param.dateEnd}.">
    <input type='hidden' name="sqlColumn" id="sqlColumn" value="">
    <input type='hidden' name="s" id="s" value="PrintService">
    <input type='hidden' name="m" id="m" value="printNativeQuery">
    <input type="submit" value="Печать"> 
    </form>
    </msh:sectionTitle>
    <msh:sectionContent>
    <msh:table name="journal_militia"
    viewUrl="entityShortView-stac_criminalMessages.do" 
     action="entityParentView-stac_criminalMessages.do" idField="1" >
      <msh:tableColumn columnName="#" property="sn" />
      <msh:tableColumn columnName="ФИО пациента" property="2" />
      <msh:tableColumn columnName="Серия и номер полиса" property="3" />
      <msh:tableColumn columnName="Наименование СМО" property="4" />
      <msh:tableColumn columnName="Серия и номер паспорта" property="5" />
      <msh:tableColumn columnName="Дата рождения" property="6" />
      <msh:tableColumn columnName="пол" property="7" />
      <msh:tableColumn columnName="Домашний адрес, место работы" property="8" />
      <msh:tableColumn property="9" columnName="Дата начала лечения"/>
      <msh:tableColumn columnName="Дата окончания лечения" property="10" />
      <msh:tableColumn columnName="Название отделения" property="11" />
      <msh:tableColumn columnName="Краткая информация об обстоятельствах получения травмы" property="12" />
      <msh:tableColumn columnName="Диагноз" property="13" />
      <msh:tableColumn columnName="Исход лечения" property="14" />
      <msh:tableColumn columnName="Вид м/помощи" property="15" />
    </msh:table>
    </msh:sectionContent>
    </msh:section>
    <% }  
    	if (view!=null && (view.equals("3"))) {%>
    
    <msh:section>
    <msh:sectionTitle>Свод по дням с ${param.dateBegin} по ${param.dateEnd}.</msh:sectionTitle>
    <msh:sectionContent>
    <ecom:webQuery name="journal_militia" nativeSql="
    select '${typeEmergency}:${param.pigeonHole}:${department}:${paramDate}:'||to_char(${paramDate},'dd.mm.yyyy')||':${param.phoneMessageType}:${param.phoneMessageSubType}' as id,to_char(${paramDate},'dd.mm.yyyy') as dateSearch, count(pm.id) as cntMessages
    , count(distinct case when m.deniedHospitalizating_id is null then m.id else null end) as cntHosp
    , count(distinct case when m.deniedHospitalizating_id is not null then m.id else null end) as cntDenied
    ,count(distinct m.id) as cntHosp
    from PhoneMessage pm 
    left join medcase m on m.id=pm.medCase_id
    left join MisLpu as ml on ml.id=m.department_id 
    where pm.dtype='CriminalPhoneMessage'
    and ${paramDate} between to_date('${param.dateBegin}','dd.mm.yyyy')  and to_date('${dateEnd}','dd.mm.yyyy')  
and ( m.noActuality is null or m.noActuality='0')
${period}
${emerIs} ${pigeonHole} ${department} ${phoneMessageType} ${phoneMessageSubType}
    group by ${paramDate}
    order by ${paramDate}
    " guid="4a720225-8d94-4b47-bef3-4dbbe79eec74" />
    <msh:table name="journal_militia"
    viewUrl="js-stac_criminalMessages-listByDate.do?short=Short" 
     action="js-stac_criminalMessages-listByDate.do" idField="1">
      <msh:tableColumn columnName="${paramDateInfo}" property="2"/>
      <msh:tableColumn columnName="Кол-во сообщений" property="3"/>
      <msh:tableColumn columnName="Кол-во госпит." property="4" />
      <msh:tableColumn columnName="Кол-во отказов от госпит." property="5" />
      <msh:tableColumn columnName="Кол-во обращений" property="6" />
    </msh:table>
    </msh:sectionContent>
    </msh:section>
    <% 
    	}
    	
    if (view!=null && (view.equals("4"))) {%>
    <msh:section>
    <msh:sectionTitle>Свод по госпитализациям с ${param.dateBegin} по ${param.dateEnd}.</msh:sectionTitle>
    <msh:sectionContent>
    <ecom:webQuery name="journal_militia" nativeSql="
    select '${typeEmergency}:${param.pigeonHole}:${department}:${paramDate}:${param.dateBegin}:${dateEnd}:'||coalesce(m.department_id,0)||':'||coalesce(vpmt.id,0)||':${param.phoneMessageSubType}' as id,ml.name as mlname,vpmt.name as vpmtname, count(pm.id) as cntPm
    ,count(distinct m.id) as cntHosp
    from PhoneMessage pm 
    left join VocPhoneMessageType vpmt on vpmt.id=pm.phoneMessageType_id
    left join medcase m on m.id=pm.medCase_id
    left join mislpu ml on ml.id=m.department_id
    where pm.dtype='CriminalPhoneMessage'
    and ${paramDate} between to_date('${param.dateBegin}','dd.mm.yyyy')  and to_date('${dateEnd}','dd.mm.yyyy')  
and ( m.noActuality is null or m.noActuality='0')
and m.deniedHospitalizating_id is null
${period}
${emerIs} ${pigeonHole} ${department} ${phoneMessageType} ${phoneMessageSubType}
    group by m.department_id,ml.name,vpmt.id,vpmt.name
    order by ml.name,vpmt.name
    " guid="4a720225-8d94-4b47-bef3-4dbbe79eec74" />
    <msh:table name="journal_militia" 
    viewUrl="js-stac_criminalMessages-listByHospital.do?short=Short"
    action="js-stac_criminalMessages-listByHospital.do?dateSearch=${dateSearch}" idField="1" guid="b621e361-1e0b-4ebd-9f58-b7d919b45bd6">
      <msh:tableColumn columnName="Отделение" property="2" />
      <msh:tableColumn columnName="Тип" property="3" />
      <msh:tableColumn columnName="Кол-во сообщений" property="4" />
      <msh:tableColumn columnName="Кол-во госпитализаций" property="5" />
    </msh:table>
    </msh:sectionContent>
    </msh:section>
    <%
    } 
	if (view!=null && (view.equals("5"))) {%>
    <msh:section>
    <msh:sectionTitle>Свод по отказам с ${param.dateBegin} по ${param.dateEnd}.</msh:sectionTitle>
    <msh:sectionContent>
    <ecom:webQuery name="journal_militia" nativeSql="
    select '${typeEmergency}:${param.pigeonHole}:${department}:${paramDate}:${param.dateBegin}:${dateEnd}:'||coalesce(m.department_id,0)||':'||coalesce(vpmt.id,0)||':${param.phoneMessageSubType}' as id
    ,ml.name as mlname,vpmt.name as vpmtname
    ,count(pm.id) as cntPm
    ,count(distinct m.id) as cntHosp
    from PhoneMessage pm 
    left join VocPhoneMessageType vpmt on vpmt.id=pm.phoneMessageType_id
    left join medcase m on m.id=pm.medCase_id
    left join mislpu ml on ml.id=m.department_id
    where pm.dtype='CriminalPhoneMessage'
    and ${paramDate} between to_date('${param.dateBegin}','dd.mm.yyyy')  and to_date('${dateEnd}','dd.mm.yyyy')  
and ( m.noActuality is null or m.noActuality='0')
and m.deniedHospitalizating_id is not null
${period}
${emerIs} ${pigeonHole} ${department} ${phoneMessageType} ${phoneMessageSubType}
    group by m.department_id,ml.name,vpmt.id,vpmt.name
    order by ml.name,vpmt.name
    " guid="4a720225-8d94-4b47-bef3-4dbbe79eec74" />
    <msh:table name="journal_militia"
    viewUrl="js-stac_criminalMessages-listByDenied.do?short=Short" 
    action="js-stac_criminalMessages-listByDenied.do" idField="1" guid="b621e361-1e0b-4ebd-9f58-b7d919b45bd6">
      <msh:tableColumn columnName="Отделение" property="2" />
      <msh:tableColumn columnName="Тип" property="3" />
      <msh:tableColumn columnName="Кол-во сообщений" property="4" />
      <msh:tableColumn columnName="Кол-во отказов" property="5" />
    </msh:table>
    </msh:sectionContent>
    </msh:section>
    <%
    } 
	if (view!=null && (view.equals("6"))) {%>
    <msh:section>
    <msh:sectionTitle>Свод по обращениям с ${param.dateBegin} по ${param.dateEnd}.</msh:sectionTitle>
    <msh:sectionContent>
    <ecom:webQuery name="journal_militia" nativeSql="
    select '${typeEmergency}:${param.pigeonHole}:${department}:${paramDate}:${param.dateBegin}:${dateEnd}:'||coalesce(m.department_id,0)||':'||coalesce(vpmt.id,0)||':${param.phoneMessageSubType}' as id,ml.name as mlname,vpmt.name as vpmtname, count(pm.id) as cntPm
    , count(distinct case when m.deniedHospitalizating_id is null then m.id else null end) as cntHosp
    , count(distinct case when m.deniedHospitalizating_id is not null then m.id else null end) as cntDenied
    ,count(distinct m.id) as cntHosp
    from PhoneMessage pm 
    left join VocPhoneMessageType vpmt on vpmt.id=pm.phoneMessageType_id
    left join medcase m on m.id=pm.medCase_id
    left join mislpu ml on ml.id=m.department_id
    where pm.dtype='CriminalPhoneMessage'
    and ${paramDate} between to_date('${param.dateBegin}','dd.mm.yyyy')  and to_date('${dateEnd}','dd.mm.yyyy')  
and ( m.noActuality is null or m.noActuality='0')
${period}
${emerIs} ${pigeonHole} ${department} ${phoneMessageType} ${phoneMessageSubType}
    group by m.department_id,ml.name,vpmt.id,vpmt.name
    order by ml.name,vpmt.name
    " guid="4a720225-8d94-4b47-bef3-4dbbe79eec74" />
    <msh:table name="journal_militia"
        viewUrl="js-stac_criminalMessages-listByObr.do?short=Short" 
    action="js-stac_criminalMessages-listByObr.do" 
	idField="1" guid="b621e361-1e0b-4ebd-9f58-b7d919b45bd6">
      <msh:tableColumn columnName="Отделение" property="2" />
      <msh:tableColumn columnName="Тип" property="3" />
      <msh:tableColumn columnName="Кол-во сообщений" property="4" />
      <msh:tableColumn columnName="Кол-во госпит." property="5" />
      <msh:tableColumn columnName="Кол-во отказов от госпит." property="6" />
      <msh:tableColumn columnName="Кол-во обращений" property="7" />
    </msh:table>
    </msh:sectionContent>
    </msh:section>
    <%} 
    if (view!=null && (view.equals("7"))) {%>
    
    <msh:section >
    <ecom:webQuery nameFldSql="journal_militia_sql" name="journal_militia" nativeSql="
    select pm.id, ss.code as sscode,
    p.lastname||' '||p.firstname||' '||p.middlename ||' '|| to_char(p.birthday,'dd.mm.yyyy') as pbirthday
    ,to_char(m.dateStart,'dd.mm.yyyy') ||' '||cast(m.entranceTime as varchar(5)) as mdateStart
    ,pm.diagnosis as pmdiagnosis
    ,case when m.dateFinish is null then (select list(distinct mkb.code) from Diagnosis diag
    left join medcase slo on slo.id=diag.medCase_id
		left join VocIdc10 mkb on mkb.id=diag.idc10_id
		left join VocDiagnosisRegistrationType vdrt on vdrt.id=diag.registrationType_id
		left join VocPriorityDiagnosis vpd on vpd.id=diag.priority_id
where m.id=slo.parent_id and vdrt.code='4' and vpd.code='1' ) else (select list(distinct mkb.code) 
from Diagnosis diag

		left join VocIdc10 mkb on mkb.id=diag.idc10_id
		left join VocDiagnosisRegistrationType vdrt on vdrt.id=diag.registrationType_id
		left join VocPriorityDiagnosis vpd on vpd.id=diag.priority_id
where m.id=diag.medcase_id and vdrt.code='4' and vpd.code='1' ) end as mkbBefore
    ,case when m.dateFinish is not null then (select list(distinct mkb.code) from Diagnosis diag
		left join VocIdc10 mkb on mkb.id=diag.idc10_id
		left join VocDiagnosisRegistrationType vdrt on vdrt.id=diag.registrationType_id
		left join VocPriorityDiagnosis vpd on vpd.id=diag.priority_id
where m.id=diag.medcase_id and vdrt.code='3' and vpd.code='1' ) else '' end as mkbAfter
    ,vpms.name as vpmsname
    ,case when m.dateFinish is null then 'На лечении' else coalesce(to_char(m.dateFinish,'dd.mm.yyyy'),'')||' '||vho.name end as vphoname
    from PhoneMessage pm 
    left join VocPhoneMessageType vpht on vpht.id=pm.phoneMessageType_id
    left join VocPhoneMessageSubType vpmst on vpmst.id=pm.phoneMessageSubType_id
    left join VocPhoneMessageOrganization vpmorg on vpmorg.id=pm.organization_id
    left join VocPhoneMessageEmploye vpme on vpme.id=pm.recieverEmploye_id
    left join VocPhoneMessageOutcome vpmo on vpmo.id=pm.outcome_id
    left join VocPhoneMessageState vpms on vpms.id=pm.state_id
    left join WorkFunction wf on wf.id=pm.workFunction_id
    
    left join medcase m on m.id=pm.medCase_id
    left join StatisticStub ss on ss.id=m.statisticStub_id
    left join VocHospitalizationOutcome vho on vho.id=m.outcome_id
    left join Patient p on p.id=m.patient_id
    left join VocSex vs on vs.id=p.sex_id
    left join MisLpu as ml on ml.id=m.department_id
     
    where pm.dtype='CriminalPhoneMessage'
    and ${paramDate} between to_date('${param.dateBegin}','dd.mm.yyyy')  and to_date('${dateEnd}','dd.mm.yyyy')  
and m.deniedHospitalizating_id is null and ( m.noActuality is null or m.noActuality='0')
${period}
${emerIs} ${pigeonHole} ${department} ${phoneMessageType} ${phoneMessageSubType}
    order by ${paramDate}
    " guid="4a720225-8d94-4b47-bef3-4dbbe79eec74" />
    <msh:sectionTitle>
    
    <form action="print-stac_criminalMessage_pr_mdgp.do" method="post" target="_blank">
    Информация о пострадавших в результате ДТП, получивших медицинскую помощь в учреждении здравоохранения с ${param.dateBegin} по ${param.dateEnd}.
    <input type='hidden' name="sqlText" id="sqlText" value="${journal_militia_sql}"> 
    <input type='hidden' name="sqlInfo" id="sqlInfo" value="с ${param.dateBegin} по ${param.dateEnd}.">
    <input type='hidden' name="sqlColumn" id="sqlColumn" value="">
    <input type='hidden' name="s" id="s" value="PrintService">
    <input type='hidden' name="m" id="m" value="printNativeQuery">
    <input type="submit" value="Печать"> 
    </form>
    </msh:sectionTitle>
    <msh:sectionContent>
    <msh:table name="journal_militia"
    viewUrl="entityShortView-stac_criminalMessages.do" 
     action="entityParentView-stac_criminalMessages.do" idField="1" >
      <msh:tableColumn columnName="#" property="sn" />
      <msh:tableColumn columnName="№стат.карты" property="2" />
      <msh:tableColumn columnName="ФИО пациента" property="3" />
      <msh:tableColumn columnName="Дата и время поступления" property="4" />
      <msh:tableColumn columnName="Диагноз" property="5" />
      <msh:tableColumn columnName="Код диагноза по МКБ при поступлении" property="6" />
      <msh:tableColumn columnName="Код диагноза по МКБ при выписке (переводе, смерти)" property="7" />
      <msh:tableColumn columnName="Тяжесть состояния" property="8" />
      <msh:tableColumn columnName="Исход" property="9" />
    </msh:table>
    </msh:sectionContent>
    </msh:section>
     <%} %>
    <% 
    } else {%>
    	<i>Нет данных </i>
    	<% 
    	}%>
    
  </tiles:put>
</tiles:insert>