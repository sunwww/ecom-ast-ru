<%@page import="java.text.SimpleDateFormat"%>
<%@page import="java.util.Calendar"%>
<%@page import="ru.nuzmsh.util.format.DateFormat"%>
<%@page import="java.util.Date"%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://struts.apache.org/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://www.nuzmsh.ru/tags/msh" prefix="msh" %>
<%@ taglib uri="http://www.ecom-ast.ru/tags/ecom" prefix="ecom" %>
<%@ taglib tagdir="/WEB-INF/tags" prefix="tags" %>

<tiles:insert page="/WEB-INF/tiles/mainLayout.jsp" flush="true" >

  <tiles:put name="title" type="string">
    <msh:title guid="helloItle-123" mainMenu="StacJournal">Реестр по пациентам поступившим/ выбывшим из стационара</msh:title>
  </tiles:put>
  <tiles:put name="side" type="string">
  	<tags:stac_journal currentAction="stac_reestrByHospital"/>
  </tiles:put>
  <tiles:put name="body" type="string">
    <msh:form action="/stac_reestrByHospital.do" defaultField="dateBegin" disableFormDataConfirm="true" method="GET" guid="d7b31bc2-38f0-42cc-8d6d-19395273168f">
    <input type="hidden" name="s" id="s" value="HospitalPrintService" />
    <input type="hidden" name="m" id="m" value="printReestrByDay" />
    <input type="hidden" name="id" id="id" value=""/>
    <msh:panel guid="6ae283c8-7035-450a-8eb4-6f0f7da8a8ff">
      <msh:row guid="53627d05-8914-48a0-b2ec-792eba5b07d9">
        <msh:separator label="Параметры поиска" colSpan="7" guid="15c6c628-8aab-4c82-b3d8-ac77b7b3f700" />
      </msh:row>
      <msh:row guid="7d80be13-710c-46b8-8503-ce0413686b69">
      	<msh:autoComplete property="pigeonHole" fieldColSpan="3" 
      		horizontalFill="true" label="Приемник"
      		vocName="vocPigeonHole"
      		/>
      </msh:row>
      <msh:row>
        <td class="label" title="Поиск по показаниям поступления (typeEmergency)" colspan="1"><label for="typeEmergencyName" id="typeEmergencyLabel">Показания:</label></td>
        <td onclick="this.childNodes[1].checked='checked';">
        	<input type="radio" name="typeEmergency" value="1">  экстренные
        </td>
        <td onclick="this.childNodes[1].checked='checked';">
        	<input type="radio" name="typeEmergency" value="2" >  плановые
        </td>
        <td onclick="this.childNodes[1].checked='checked';">
        	<input type="radio" name="typeEmergency" value="3">  все
        </td>
      </msh:row>
      <msh:row>
        <td class="label" title="Начало суток (typeeHour)" colspan="1"><label for="typeHourName" id="typeHourLabel">Начало суток:</label></td>
        <td onclick="this.childNodes[1].checked='checked';">
        	<input type="radio" name="typeHour" value="1">  8 часов
        </td>
        <td onclick="this.childNodes[1].checked='checked';">
        	<input type="radio" name="typeHour" value="2" >  9 часов
        </td>
        <td onclick="this.childNodes[1].checked='checked';">
        	<input type="radio" name="typeHour" value="3">  календар. день
        </td>
      </msh:row>
      <msh:row guid="7d80be13-710c-46b8-8503-ce0413686b69">
        <td class="label" title="Поиск по дате  (typeDate)" colspan="1"><label for="typeDateName" id="typeDateLabel">Искать по дате:</label></td>
        <td onclick="this.childNodes[1].checked='checked';">
        	<input type="radio" name="typeDate" value="1">  поступления
        </td>
        <td onclick="this.childNodes[1].checked='checked';" colspan="1">
        	<input type="radio" name="typeDate" value="2">  выписки
        </td>
        <td onclick="this.childNodes[1].checked='checked';" colspan="3">
        	<input type="radio" name="typeDate" value="3">  состоящие
        </td>
        </msh:row>
                <msh:row>
	        <td class="label" title="Просмотр данных (typeView)" colspan="1"><label for="typeViewName" id="typeViewLabel">Отобразить:</label></td>
	        <td onclick="this.childNodes[1].checked='checked';">
	        	<input type="radio" name="typeView" value="1">  реестр
	        </td>
	        <td onclick="this.childNodes[1].checked='checked';">
	        	<input type="radio" name="typeView" value="2"  >  свод по отделениям
	        </td>
        </msh:row>
      <msh:row guid="Дата">
        <msh:textField fieldColSpan="2" property="dateBegin" label="Дата" guid="8d7ef035-1273-4839-a4d8-1551c623caf1" />
      </msh:row>
        <msh:row>
        	<msh:autoComplete property="department" fieldColSpan="4" horizontalFill="true" label="Отделение" vocName="lpu"/>
        </msh:row>
      <msh:row>
           <td colspan="11">
            <input type="submit" onclick="find()" value="Найти" />
            <input type="submit" onclick="print()" value="Печать" />
            <input type="submit" onclick="printJournal()" value="Печать журнала госпитализаций и отказов от госпитализаций" />
<%--            <input type="submit" onclick="printNew()" value="Печать (по отделениям)" />
            <input type="submit" onclick="printNew1()" value="Печать (сопроводительного листа) 1" />
            <input type="submit" onclick="printNew2()" value="Печать (сопроводительного листа) 2" />
             --%>
          </td>
      </msh:row>
    </msh:panel>
    </msh:form>
       <script type='text/javascript'>
    
    checkFieldUpdate('typeDate','${typeDate}',1) ;
<%--     checkFieldUpdate('typePatient','${typePatient}',3) ;--%>
    checkFieldUpdate('typeEmergency','${typeEmergency}',3) ;
    checkFieldUpdate('typeView','${typeView}',1) ;
    checkFieldUpdate('typeHour','${typeHour}',3) ;
    
  
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
    	frm.action='stac_reestrByHospital.do' ;
    }
    function print() {
    	var frm = document.forms[0] ;
    	frm.m.value="printReestrByDay" ;
    	frm.s.value="HospitalPrintService" ;
    	frm.target='_blank' ;
    	frm.action='print-stac_reestrForDay.do' ;
    	$('id').value = getCheckedRadio(frm,"typeEmergency")+":"
    		+getCheckedRadio(frm,"typeHour")+":"
    		+getCheckedRadio(frm,"typeDate")+":"
    		+$('dateBegin').value+":"
    		+$('pigeonHole').value+":"
    		+$('department').value;
    }
    function printJournal() {
    	var frm = document.forms[0] ;
    	frm.m.value="printJournalByDay" ;
    	frm.s.value="HospitalPrintReport" ;
    	frm.target='_blank' ;
    	frm.action='print-stac_report001.do' ;
    	$('id').value = getCheckedRadio(frm,"typeEmergency")+":"
    		+getCheckedRadio(frm,"typeHour")+":"
    		+getCheckedRadio(frm,"typeDate")+":"
    		+$('dateBegin').value+":"
    		+$('pigeonHole').value+":"
    		+$('department').value;
    }
    function printNew() {
    	//print() ;
    	var frm = document.forms[0] ;
    	frm.m.value="printReestrByDay" ;
    	frm.s.value="HospitalPrintService" ;
    	frm.target='_blank' ;
    	frm.action='print-stac_reestrForDay.do' ;
    }/*
    function printNew1() {
    	print() ;
    	var frm = document.forms[0] ;
    	frm.m.value="printCoveringLetterByDay" ;
    	frm.target='_blank' ;
    	frm.action='print-stac_coveringLetterByDay1.do' ;
    }
    function printNew2() {
    	print() ;
    	var frm = document.forms[0] ;
    	frm.m.value="printCoveringLetterByDay" ;
    	frm.target='_blank' ;
    	frm.action='print-stac_coveringLetterByDay2.do' ;
    }*/
    if ($('dateBegin').value=="") {
    	$('dateBegin').value=getCurrentDate() ;
    }

			 
    </script>
    <%
    String date = request.getParameter("dateBegin") ;
    
    if (date!=null && !date.equals(""))  {
    	String view = (String)request.getAttribute("typeView") ;
    	String pigeonHole="" ;
    	String pigeonHole1="" ;
    	String pHole = request.getParameter("pigeonHole") ;
    	if (pHole!=null && !pHole.equals("") && !pHole.equals("0")) {
    		pigeonHole1= " and (ml.pigeonHole_id='"+pHole+"' or ml1.pigeonHole_id='"+pHole+"')" ;
    		pigeonHole= " and ml.pigeonHole_id='"+pHole+"'" ;
    	}
    	request.setAttribute("pigeonHole", pigeonHole) ;
    	request.setAttribute("pigeonHole1", pigeonHole1) ;
    	
    	String department="" ;
    	String dep = request.getParameter("department") ;
    	if (dep!=null && !dep.equals("") && !dep.equals("0")) {
    		department= " and ml.id='"+dep+"'" ;
    	}
    	request.setAttribute("department", department) ;
    	if (view!=null && (view.equals("1"))) {
    	%>
    
    <msh:section>
    <msh:sectionTitle>Реестр ${dateInfo} за день ${param.dateBegin}. По ${dischInfo}  ${emerInfo} ${hourInfo}
    </msh:sectionTitle>
    <msh:sectionContent>
    <ecom:webQuery name="journal_priem" nativeSql="select 
    m.id as mid
    ,to_char(m.dateStart,'dd.mm.yyyy')||' '||cast(m.entranceTime as varchar(5)) as mdateStart
    ,to_char(m.dateFinish,'dd.mm.yyyy')||' '||cast(m.dischargeTime as varchar(5)) as mdateFinish
    ,cast(m.dischargeTime as varchar(5)) as mdischargeTime
    ,pat.lastname ||' ' ||pat.firstname|| ' ' || pat.middlename||' гр '||to_char(pat.birthday,'dd.mm.yyyy') as fio
    ,to_char(pat.birthday,'dd.mm.yyyy') as birthday,sc.code as sccode,m.emergency as memergency
    ,ml.name as mlname
    , case when (ok.voc_code is not null and ok.voc_code!='643') then 'ИНОСТ'  
    when (pvss.omccode='И0' or adr.kladr is not null and adr.kladr not like '30%') then 'ИНОГ' else '' end as typePatient
    
     from MedCase as m  
     left join StatisticStub as sc on sc.medCase_id=m.id 
     left outer join Patient pat on m.patient_id = pat.id 
     left join MisLpu as ml on ml.id=m.department_id 
     left join Omc_Oksm ok on pat.nationality_id=ok.id
	 left join VocSocialStatus pvss on pvss.id=pat.socialStatus_id
	 left join address2 adr on adr.addressId = pat.address_addressId
     
     where m.DTYPE='HospitalMedCase' ${period}
     and m.deniedHospitalizating_id is null
       ${emerIs} ${pigeonHole} ${department} order by m.${dateI},ml.name,pat.lastname,pat.firstname,pat.middlename
      " guid="4a720225-8d94-4b47-bef3-4dbbe79eec74" />
    <msh:table name="journal_priem" viewUrl="entityShortView-stac_ssl.do" action="entityParentView-stac_ssl.do" idField="1" guid="b621e361-1e0b-4ebd-9f58-b7d919b45bd6">
      <msh:tableColumn columnName="#" property="sn" guid="34a9f56ab-a3fa-5c1afdf6c41d" />
      <msh:tableColumn columnName="Стат.карта" property="7" guid="34a9f56a-2b47-4feb-a3fa-5c1afdf6c41d" />
      <msh:tableColumn columnName="Фамилия имя отчество пациента" property="5" guid="34a9f56a-2b47-4feb-a3fa-5c1afdf6c41d" />
      <msh:tableColumn columnName="Дата поступления" property="2" guid="3cf775aa-e94d-4393-a489-b83b2be02d60" />
      <msh:tableColumn columnName="Отделение" property="9" guid="e29229e1-d243-47d6-a5c7-997df74eaf73" />
      <msh:tableColumn columnName="Дата выписки" property="3" guid="d9642df9-5653-4920-bb78-1622cbeefa34" />
      <msh:tableColumn columnName="Пациент" property="10" guid="d9642df9-5653-4920-bb78-1622cbeefa34" />
    </msh:table>
    </msh:sectionContent>
    </msh:section>
    <% if (request.getAttribute("period1")!=null) { %>
    <msh:section>
    <msh:sectionTitle>Реестр отказов от госпитализаций за день ${param.dateBegin}. По ${emerInfo} ${hourInfo}</msh:sectionTitle>
    <msh:sectionContent>
    <ecom:webQuery name="journal_priem" nativeSql="select 
    m.id as mid
    ,to_char(m.dateStart,'dd.mm.yyyy')||' '||cast(m.entranceTime as varchar(5)) as mdateStart
    ,pat.lastname ||' ' ||pat.firstname|| ' ' || pat.middlename||' гр '||to_char(pat.birthday,'dd.mm.yyyy') as fio
    ,sc.code as sccode,m.emergency as memergency
    ,vdh.name as vdhname
    , case when (ok.voc_code is not null and ok.voc_code!='643') then 'ИНОСТ'  
    when (pvss.omccode='И0' or adr.kladr is not null and adr.kladr not like '30%') then 'ИНОГ' else '' end as typePatient
    
     from MedCase as m  
     left join StatisticStub as sc on sc.medCase_id=m.id 
     left outer join Patient pat on m.patient_id = pat.id 
     left join VocDeniedHospitalizating as vdh on vdh.id=m.deniedHospitalizating_id
     left join Omc_Oksm ok on pat.nationality_id=ok.id
	 left join VocSocialStatus pvss on pvss.id=pat.socialStatus_id
	 left join address2 adr on adr.addressId = pat.address_addressId  

	left join MisLpu as ml on ml.id=m.department_id 

	 left join SecUser su on su.login=m.username
	left join WorkFunction wf on wf.secUser_id=su.id
	left join Worker w on w.id=wf.worker_id
	left join MisLpu ml1 on ml1.id=w.lpu_id 
     where m.DTYPE='HospitalMedCase' ${period}
      and m.deniedHospitalizating_id is not null
      ${emerIs} ${pigeonHole1} order by m.${dateI},pat.lastname,pat.firstname,pat.middlename
      " guid="4a720225-8d94-4b47-bef3-4dbbe79eec74" />
    <msh:table name="journal_priem" viewUrl="entityShortView-stac_ssl.do" action="entityParentView-stac_ssl.do" idField="1" guid="b621e361-1e0b-4ebd-9f58-b7d919b45bd6">
      <msh:tableColumn columnName="#" property="sn" guid="34a9f56ab-a3fa-5c1afdf6c41d" />
      <msh:tableColumn columnName="Стат.карта" property="4" guid="34a9f56a-2b47-4feb-a3fa-5c1afdf6c41d" />
      <msh:tableColumn columnName="Фамилия имя отчество пациента" property="3" guid="34a9f56a-2b47-4feb-a3fa-5c1afdf6c41d" />
      <msh:tableColumn columnName="Дата обращения" property="2" guid="3cf775aa-e94d-4393-a489-b83b2be02d60" />
      <msh:tableColumn columnName="Экстрено" property="5" guid="3cf775aa-e94d-4393-a489-b83b2be02d60" />
      <msh:tableColumn columnName="Причина отказа" property="6" guid="e29229e1-d243-47d6-a5c7-997df74eaf73" />
      <msh:tableColumn columnName="Пациент" property="7"/>
    </msh:table>
    </msh:sectionContent>
    </msh:section>    <% }
    	}else {
    		%>
    		    <msh:section>
    <msh:sectionTitle>Реестр ${dateInfo} за день ${param.dateBegin}. По ${dischInfo}  ${emerInfo} ${hourInfo}
    </msh:sectionTitle>
    <msh:sectionContent>
    <ecom:webQuery name="journal_priem" nativeSql="select 
    
    ml.name as mlname
    ,count(distinct m.id)
  	,count(distinct case when m.emergency='1' then m.id else null end) as memergency
    ,count(distinct case when (ok.voc_code is not null and ok.voc_code!='643') then m.id else null end) as inostr  
    ,count(distinct case when (pvss.omccode='И0' or adr.kladr is not null and adr.kladr not like '30%') then m.id else null end) as inog
    
     from MedCase as m  
     left join StatisticStub as sc on sc.medCase_id=m.id 
     left outer join Patient pat on m.patient_id = pat.id 
     left join MisLpu as ml on ml.id=m.department_id 
     left join Omc_Oksm ok on pat.nationality_id=ok.id
	 left join VocSocialStatus pvss on pvss.id=pat.socialStatus_id
	 left join address2 adr on adr.addressId = pat.address_addressId
     
     where m.DTYPE='HospitalMedCase' ${period}
     and m.deniedHospitalizating_id is null
     ${emerIs} ${pigeonHole} ${department}
     group by ml.name
        order by ml.name
      " guid="4a720225-8d94-4b47-bef3-4dbbe79eec74" />
    <msh:table name="journal_priem" action="stac_reestrByHospital.do" idField="1" guid="b621e361-1e0b-4ebd-9f58-b7d919b45bd6">
      <msh:tableColumn columnName="#" property="sn" guid="34a9f56ab-a3fa-5c1afdf6c41d" />
      <msh:tableColumn columnName="Отделение" property="1" guid="34a9f56a-2b47-4feb-a3fa-5c1afdf6c41d" />
      <msh:tableColumn columnName="Кол-во" property="2" guid="d9642df9-5653-4920-bb78-1622cbeefa34" />
      <msh:tableColumn columnName="Кол-во экстренных" property="3" guid="34a9f56a-2b47-4feb-a3fa-5c1afdf6c41d" />
      <msh:tableColumn columnName="Кол-во иностранцев" property="4" guid="3cf775aa-e94d-4393-a489-b83b2be02d60" />
      <msh:tableColumn columnName="Кол-во иногородних" property="5" guid="e29229e1-d243-47d6-a5c7-997df74eaf73" />
    </msh:table>
    </msh:sectionContent>
    </msh:section>
    <% if (request.getAttribute("period1")!=null) { %>
    <msh:section>
    <msh:sectionTitle>Реестр отказов от госпитализаций за день ${param.dateBegin}. По ${emerInfo} ${hourInfo}</msh:sectionTitle>
    <msh:sectionContent>
    <ecom:webQuery name="journal_priem" nativeSql="select 
    vdh.name as vdhname
    ,count(distinct m.id)
  	,count(distinct case when m.emergency='1' then m.id else null end) as memergency
    ,count(distinct case when (ok.voc_code is not null and ok.voc_code!='643') then m.id else null end) as inostr  
    ,count(distinct case when (pvss.omccode='И0' or adr.kladr is not null and adr.kladr not like '30%') then m.id else null end) as inog
     from MedCase as m  
     left join StatisticStub as sc on sc.medCase_id=m.id 
     left outer join Patient pat on m.patient_id = pat.id 
     left join VocDeniedHospitalizating as vdh on vdh.id=m.deniedHospitalizating_id
     left join Omc_Oksm ok on pat.nationality_id=ok.id
	 left join VocSocialStatus pvss on pvss.id=pat.socialStatus_id
	 left join address2 adr on adr.addressId = pat.address_addressId  
	 left join SecUser su on su.login=m.username
	left join WorkFunction wf on wf.secUser_id=su.id
	left join MisLpu as ml on ml.id=m.department_id 
	left join Worker w on w.id=wf.worker_id
	left join MisLpu ml1 on ml1.id=w.lpu_id 
     where m.DTYPE='HospitalMedCase' ${period}
      and m.deniedHospitalizating_id is not null
      ${emerIs} ${pigeonHole1} group by vdh.name order by vdh.name
      " guid="4a720225-8d94-4b47-bef3-4dbbe79eec74" />
    <msh:table name="journal_priem" action="stac_reestrByHospital.do" idField="1" guid="b621e361-1e0b-4ebd-9f58-b7d919b45bd6">
      <msh:tableColumn columnName="#" property="sn" guid="34a9f56ab-a3fa-5c1afdf6c41d" />
      <msh:tableColumn columnName="Причина отказа" property="1" guid="34a9f56a-2b47-4feb-a3fa-5c1afdf6c41d" />
      <msh:tableColumn columnName="Кол-во" property="2" guid="d9642df9-5653-4920-bb78-1622cbeefa34" />
      <msh:tableColumn columnName="Кол-во экстренных" property="3" guid="34a9f56a-2b47-4feb-a3fa-5c1afdf6c41d" />
      <msh:tableColumn columnName="Кол-во иностранцев" property="4" guid="3cf775aa-e94d-4393-a489-b83b2be02d60" />
      <msh:tableColumn columnName="Кол-во иногородних" property="5" guid="e29229e1-d243-47d6-a5c7-997df74eaf73" />
    </msh:table>
    </msh:sectionContent>
    </msh:section>
    		<%
    	}
    	}} else {%>
    	<i>Нет данных </i>
    	<% }   %>
    

  </tiles:put>
</tiles:insert>

