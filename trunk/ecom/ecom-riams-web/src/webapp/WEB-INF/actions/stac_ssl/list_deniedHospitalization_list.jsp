<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://struts.apache.org/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://www.nuzmsh.ru/tags/msh" prefix="msh" %>
<%@ taglib uri="http://www.ecom-ast.ru/tags/ecom" prefix="ecom" %>
<%@ taglib tagdir="/WEB-INF/tags" prefix="tags" %>

<tiles:insert page="/WEB-INF/tiles/mainLayout.jsp" flush="true" >

    <tiles:put name='title' type='string'>
        <msh:title mainMenu="StacJournal">Просмотр данных по амбулаторным случаям</msh:title>
    </tiles:put>

    <tiles:put name='side' type='string'>
        <tags:stac_journal currentAction="stac_journalHospitalByDeniedHospitalizating"/>
    </tiles:put>
    
  <tiles:put name="body" type="string">
    <msh:form action="/stac_journalHospitalByDeniedHospitalizating.do" defaultField="department" disableFormDataConfirm="true" method="GET" guid="d7b31bc2-38f0-42cc-8d6d-19395273168f">
    <msh:panel guid="6ae283c8-7035-450a-8eb4-6f0f7da8a8ff">
      <msh:row guid="53627d05-8914-48a0-b2ec-792eba5b07d9">
        <msh:separator label="Параметры поиска" colSpan="7" guid="15c6c628-8aab-4c82-b3d8-ac77b7b3f700" />
      </msh:row>
      <msh:row guid="7d80be13-710c-46b8-8503-ce0413686b69">
        <td class="label" title="Поиск включая отказы от госпит.  (typeHospit)" colspan="1"><label for="typeHospitName" id="typeHospitLabel">Отказы от госпитализаций:</label></td>
        <td onclick="this.childNodes[1].checked='checked';">
        	<input type="radio" name="typeHospit" value="1">  только отказы
        </td>
        <td onclick="this.childNodes[1].checked='checked';">
        	<input type="radio" name="typeHospit" value="2">  исключая
        </td>
                <td onclick="this.childNodes[1].checked='checked';">
        	<input type="radio" name="typeHospit" value="3">  включая
        </td>
        </msh:row>
      <msh:row guid="7d80be13-710c-46b8-8503-ce0413686b69">
        <td class="label" title="Поиск по пациентам (typePatient)" colspan="1"><label for="typePatientName" id="typePatientLabel">Пациенты:</label></td>
        <td onclick="this.childNodes[1].checked='checked';">
        	<input type="radio" name="typePatient" value="1">  региональные
        </td>
        <td onclick="this.childNodes[1].checked='checked';">
        	<input type="radio" name="typePatient" value="2">  иногородные
        </td>
        <td onclick="this.childNodes[1].checked='checked';">
        	<input type="radio" name="typePatient" value="3">  все
        </td>
        </msh:row>
        <msh:row>
        <msh:textField property="dateBegin" label="Период с" guid="8d7ef035-1273-4839-a4d8-1551c623caf1" />
        <msh:textField property="dateEnd" label="по" guid="f54568f6-b5b8-4d48-a045-ba7b9f875245" />
        </msh:row>
        <msh:row>
           <td colspan="3" align="right">
            <input type="submit" onclick="find()" value="Найти" />
            <input type="submit" onclick="print()" value="Печать" />
          </td>

      </msh:row>
    </msh:panel>
    </msh:form>
    
    <%
    String date = (String)request.getParameter("dateBegin") ;
    if (date!=null && !date.equals(""))  {
    	%>
    
    <msh:section>
    <msh:sectionTitle>Результаты поиска ${infoTypePat}. Период с ${param.dateBegin} по ${dateEnd}. ${infoSearch} ${hospInfo}
    </msh:sectionTitle>
    <msh:sectionContent>
    <ecom:webQuery name="journal_ticket" nativeSql="select m.id as mid
    	, ss.code as sscode, p.lastname|| ' ' || p.firstname ||' '||p.middlename as pfio
    	,to_char(p.birthday,'dd.mm.yyyy') as pbirthday,m.datestart as mdatestart, d.name as dname
    	,vdh.name as vdhname  
    	from MedCase as m 
    	left join Patient p on p.id=m.patient_id 
    	left join statisticstub as ss on ss.id=m.statisticstub_id 
    	left join VocDeniedHospitalizating as vdh on vdh.id=m.deniedHospitalizating_id  
    	left join mislpu as d on d.id=m.department_id 
    	left join VocSocialStatus pvss on pvss.id=p.socialStatus_id 
    	where m.DTYPE='HospitalMedCase' and m.datestart between to_date('${param.dateBegin}','dd.mm.yyyy') 
    	and to_date('${dateEnd}','dd.mm.yyyy') 
    	and cast(m.ambulanceTreatment as int)=1 ${hospT} 
    	  ${add}" guid="4a720225-8d94-4b47-bef3-4dbbe79eec74" />
        <msh:table name="journal_ticket" action="entityParentView-stac_ssl.do" idField="1" noDataMessage="Не найдено">
            <msh:tableColumn columnName="#" property="sn"/>
            <msh:tableColumn columnName="Стат.карта" property="2"/>
            <msh:tableColumn columnName="ФИО пациента" property="3"/>
            <msh:tableColumn columnName="Год рождения" property="4"/>
            <msh:tableColumn columnName="Дата обращения" property="5"/>
            <msh:tableColumn columnName="Причина отказа" property="7"/>
        </msh:table>
    </msh:sectionContent>
    </msh:section>
    <% } else {%>
    	<i>Выберите параметры и нажмите найти </i>
    	<% }   %>
    
    <script type='text/javascript' src='/skin/ext/jscalendar/calendar.js'></script> 
    <script type='text/javascript' src='/skin/ext/jscalendar/calendar-setup.js'></script> 
    <script type='text/javascript' src='/skin/ext/jscalendar/calendar-ru.js'></script> 
    <style type="text/css">@import url(/skin/ext/jscalendar/css/calendar-blue.css);</style>
    <script type='text/javascript'>
    var typePatient = document.forms[0].typePatient ;
     var typeHospit = document.forms[0].typeHospit ;
     /*  var period = document.forms[0].period ;
    
   
    if ((+'${period}')==1) {
    	period[1].checked='checked' ;
    } else if ((+'${period}')==3) {
    	period[0].checked='checked' ;
    }else {
    	period[2].checked='checked' ;
    } */  
    if ((+'${typeHospit}')==1) {
    	typeHospit[0].checked='checked' ;
    } else if ((+'${typeHospit}')==2) {
    	typeHospit[1].checked='checked' ;
    } else {
    	typeHospit[2].checked='checked' ;
    }
    if ((+'${typePatient}')==1) {
    	typePatient[0].checked='checked' ;
    } else if ((+'${typePatient}')==2) {
    	typePatient[1].checked='checked' ;
    } else {
    	typePatient[2].checked='checked' ;
    }
    function find() {
    	var frm = document.forms[0] ;
    	frm.target='' ;
    	frm.action='stac_journalHospitalByDeniedHospitalizating.do' ;
    }
    function print() {
    	var frm = document.forms[0] ;
    	frm.target='_blank' ;
    	frm.action='stac_journalHospitalByDeniedHospitalizating_print.do' ;
    }
    function getPeriod() {
    	//var period = document.forms[0].period ;
    	for (i=0;i<period.length;i++) {
    		if (period[i].checked) return +period[i].value ;
    	}
    	return 1 ;
    }
    function changePeriod() {
    	
    	var field1 = document.getElementById("dateBegin").value;
    	var field2 = document.getElementById("dateEnd");
    	var date2 ;
    	var date = new Date(field1.substring(0,4),(+field1.substring(5,7)-1),field1.substring(8)) ;
    	if (getPeriod()==1) {
		 	date2 = new Date(date.getFullYear(),date.getMonth(),date.getDate()+6) ;
		 	//time += Date.WEEK; // substract one week
		 } else if (getPeriod()==2) {
		 	date2=new Date(date.getFullYear(),date.getMonth()+1,date.getDate()-1) ;
		 } else {
		 	date2=new Date(date.getFullYear(),date.getMonth(),date.getDate()) ;
		 }
		field2.value = date2.print("%Y-%m-%d");
    }
    function catcalc(cal) {
			var date = cal.date;
			var time = date.getTime() ;
			 // use the _other_ field
			 var field = document.getElementById("dateEnd");
			 var date2 ;
			 if (field == cal.params.inputField) {
				 field = document.getElementById("dateBegin");
				 if (getPeriod()==1) {
				 	date2 = new Date(date.getFullYear(),date.getMonth(),date.getDate()-6) ;
				 	//date2 = new Date(time) ;
				 } else if (getPeriod()==2) {
				 	date2 = new Date(date.getFullYear(),date.getMonth()-1,date.getDate()+1) ;
				 	//date2 = new Date(time) ;
				 } else{
				 	date2=new Date(date.getFullYear(),date.getMonth(),date.getDate()) ;
				 	
				 }
			 } else {
				 if (getPeriod()==2) {
				 	date2 = new Date(date.getFullYear(),date.getMonth()+1,date.getDate()-1) ;
				 	//time += Date.WEEK; // substract one week
				 } else if (getPeriod()==1) {
				 	date2 = new Date(date.getFullYear(),date.getMonth(),date.getDate()+6) ;
				 	//time += Date.WEEK; // substract one week
				 } else {
				 	date2=new Date(date.getFullYear(),date.getMonth(),date.getDate()) ;
				 }
			 }
			 //var date2 = new Date(time);
			 field.value = date2.print("%Y-%m-%d");
	}
    </script>
  </tiles:put>
</tiles:insert>