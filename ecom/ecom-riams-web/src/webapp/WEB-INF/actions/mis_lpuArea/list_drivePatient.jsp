<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://struts.apache.org/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://www.nuzmsh.ru/tags/msh" prefix="msh" %>
<%@ taglib uri="http://www.ecom-ast.ru/tags/ecom" prefix="ecom" %>
<%@ taglib tagdir="/WEB-INF/tags" prefix="tags" %>

<tiles:insert page="/WEB-INF/tiles/mainLayout.jsp" flush="true" >

    <tiles:put name='title' type='string'>
        <ecom:titleTrail title="Просмотр данных по участку" beginForm="mis_lpuAreaForm" mainMenu="Lpu"/>
    </tiles:put>

    <tiles:put name='side' type='string'>
        
    </tiles:put>
    
  <tiles:put name="body" type="string">
    <msh:form action="psych_listByArea.do" defaultField="dateBegin" disableFormDataConfirm="true" method="GET" guid="d7b31bc2-38f0-42cc-8d6d-19395273168f">
    <msh:panel guid="6ae283c8-7035-450a-8eb4-6f0f7da8a8ff">
	<input type="hidden" id="id" name="id" value="${param.id}"/>
      <msh:row guid="53627d05-8914-48a0-b2ec-792eba5b07d9">
        <msh:separator label="Параметры поиска" colSpan="7" guid="15c6c628-8aab-4c82-b3d8-ac77b7b3f700" />
      </msh:row>
      <msh:row guid="7d80be13-710c-46b8-8503-ce0413686b69">
        <td class="label" title="Поиск по дате  (typeDate)" colspan="1"><label for="typeDateName" id="typeDateLabel">Искать по дате:</label></td>
        <td onclick="this.childNodes[1].checked='checked';">
        	<input type="radio" name="typeDate" value="1">  состоящие
        </td>
        <td onclick="this.childNodes[1].checked='checked';">
        	<input type="radio" name="typeDate" value="2">  выбывшие
        </td>
        <td onclick="this.childNodes[1].checked='checked';">
        	<input type="radio" name="typeDate" value="3">  поступившие
        </td>
        </msh:row>
              <msh:row >
        <td class="label" title="Впервые взятые на учет  (typeFirst)" colspan="1"><label for="typeFirstName" id="typeFirstLabel">Впервые в жизни взятые на учет:</label></td>
        <td onclick="this.childNodes[1].checked='checked';">
        	<input type="radio" name="typeFirst" value="1">  все
        </td>
        <td onclick="this.childNodes[1].checked='checked';">
        	<input type="radio" name="typeFirst" value="2">  нет
        </td>
        <td onclick="this.childNodes[1].checked='checked';">
        	<input type="radio" name="typeFirst" value="3">  да
        </td>
        </msh:row>
        <msh:row >
        <td class="label" title="Инвалидность" colspan="1"><label for="typeInvName" id="typeFirstLabel">Инвалидность:</label></td>
        <td onclick="this.childNodes[1].checked='checked';">
        	<input type="radio" name="typeInv" value="1">  все
        </td>
        <td onclick="this.childNodes[1].checked='checked';">
        	<input type="radio" name="typeInv" value="2">  нет
        </td>
        <td onclick="this.childNodes[1].checked='checked';">
        	<input type="radio" name="typeInv" value="3">  есть
        </td>
        <msh:autoComplete property="groupInv" vocName="vocInvalidity" hideLabel="true"/>
        </msh:row>
        <%--
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
        --%>
        <msh:row>
        	<msh:autoComplete property="ambulatoryCare" vocName="vocPsychAmbulatoryCare" label="Вид наблюдения"/>
        	<msh:autoComplete parentAutocomplete="ambulatoryCare" property="group" vocName="vocPsychDispensaryGroup" label="Группа"/>
        </msh:row>
        <msh:row>
        <msh:textField property="dateBegin" label="Период с" guid="8d7ef035-1273-4839-a4d8-1551c623caf1" />
        <msh:textField property="dateEnd" label="по" guid="f54568f6-b5b8-4d48-a045-ba7b9f875245" />
        </msh:row>
        <msh:row>
        <td class="label" title="Длительность (period)" colspan="1"><label for="periodName" id="peroidLabel">Длительность:</label></td>
        
        <td onclick="this.childNodes[1].checked='checked';changePeriod()">
        	<input type="radio" name="period" value="3"> День
        </td>
        <td onclick="this.childNodes[1].checked='checked';changePeriod()">
        	<input type="radio" name="period" value="1"> Неделя
        </td>
        <td onclick="this.childNodes[1].checked='checked';changePeriod()">
        	<input type="radio" name="period" value="2"> Месяц
        </td>
        <td onclick="this.childNodes[1].checked='checked';changePeriod()">
        	<input type="radio" name="period" value="4"> Квартал
        </td>
           <td>
            <input type="submit" onclick="find()" value="Найти" />
            <input type="submit" onclick="print()" value="Печать" />
          </td>

      </msh:row>
    </msh:panel>
    </msh:form>
    
    <script type='text/javascript' src='/skin/ext/jscalendar/calendar.js'></script> 
    <script type='text/javascript' src='/skin/ext/jscalendar/calendar-setup.js'></script> 
    <script type='text/javascript' src='/skin/ext/jscalendar/calendar-ru.js'></script> 
    <style type="text/css">@import url(/skin/ext/jscalendar/css/calendar-blue.css);</style>
    <script type='text/javascript'>
    var typePatient = document.forms[0].typePatient ;
     var typeDate = document.forms[0].typeDate ;
     var typeFirst = document.forms[0].typeFirst ;
     var typeInv = document.forms[0].typeInv ;
     var period = document.forms[0].period ;
    
    if ((+'${typeDate}')==1) {
    	typeDate[0].checked='checked' ;
    } else  if ((+'${typeDate}')==3) {
    	typeDate[2].checked='checked' ;
    } else {
    	typeDate[1].checked='checked' ;
    }    
    if ((+'${typeFirst}')==3) {
    	typeFirst[2].checked='checked' ;
    } else  if ((+'${typeFirst}')==2) {
    	typeFirst[1].checked='checked' ;
    } else {
    	typeFirst[0].checked='checked' ;
    }    
    if ((+'${typeInv}')==3) {
    	typeInv[2].checked='checked' ;
    } else  if ((+'${typeInv}')==2) {
    	typeInv[1].checked='checked' ;
    } else {
    	typeInv[0].checked='checked' ;
    }    
    if ((+'${period}')==1) {
    	period[1].checked='checked' ;
    } else if ((+'${period}')==3) {
    	period[0].checked='checked' ;
    } else if ((+'${period}')==4) {
    	period[3].checked='checked' ;
    } else {
    	period[2].checked='checked' ;
    }   
   /*
    if ((+'${typePatient}')==1) {
    	typePatient[0].checked='checked' ;
    } else if ((+'${typePatient}')==2) {
    	typePatient[1].checked='checked' ;
    } else {
    	typePatient[2].checked='checked' ;
    }*/
    function find() {
    	var frm = document.forms[0] ;
    	frm.target='' ;
    	frm.action='psych_listByArea.do?id=${param.id}' ;
    }
    function print() {
    	var frm = document.forms[0] ;
    	frm.target='_blank' ;
    	frm.action='psych_listByArea_print.do?id=${param.id}' ;
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
		 } else if (getPeriod()==4){
		 	date2=new Date(date.getFullYear(),date.getMonth()+3,date.getDate()-1) ;
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
				 } else if (getPeriod()==4) {
				 	date2 = new Date(date.getFullYear(),date.getMonth()-3,date.getDate()+1) ;
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
				 }else if (getPeriod()==4) {
				 	date2 = new Date(date.getFullYear(),date.getMonth()+3,date.getDate()-1) ;
				 	//time += Date.WEEK; // substract one week
				 } else {
				 	date2=new Date(date.getFullYear(),date.getMonth(),date.getDate()) ;
				 }
			 }
			 //var date2 = new Date(time);
			 field.value = date2.print("%Y-%m-%d");
	}
			 Calendar.setup({
				 inputField : "dateBegin", // id of the input field
				 ifFormat : "%Y-%m-%d", // format of the input field
				 showsTime : false,
				 timeFormat : "24",
				 eventName: "focus",
				 onUpdate : catcalc
			 });
			 Calendar.setup({
				 inputField : "dateEnd",
				 ifFormat : "%Y-%m-%d",
				 showsTime : false,
				 timeFormat : "24",
				 eventName: "focus",
				 onUpdate : catcalc
 			});
    </script>
    
    <%
    String date = (String)request.getParameter("dateBegin") ;
    if (date!=null && !date.equals(""))  {
    	%>
    
    <msh:section>
    <msh:sectionTitle>Результаты поиска ${infoTypePat}. Период с ${param.dateBegin} по ${param.dateEnd}. ${infoSearch} ${dateInfo} ${dateFInfo} ${typeInvInfo}</msh:sectionTitle>
    <msh:sectionContent>
    <ecom:webQuery name="journal_ticket" nativeSql="
   select distinct pcc.id as pccid
   ,pcc.cardNumber as pcccardNumber
   ,coalesce(p.lastname,'-')||' '||coalesce(p.firstname,'-')|| ' '||coalesce(p.middlename,'-') as lfm
   ,area.startDate as areastartdate,area.finishDate as areafinishdate,p.birthday as pbirthday 
   ,$$ByPatient^ZAddressLib(p.id)
   ,$$getDiagnosis^ZPsychUtil(p.id,isnull(area.finishDate,CURRENT_DATE))
   from PsychiatricCareCard pcc 
   left join Patient p on p.id=pcc.patient_id 
   left join LpuAreaPsychCareCard area on pcc.id=area.careCard_id 
   ${lpo}
   where  area.lpuArea_id=${param.id} 
   ${dateT} ${dateF} ${typeI} ${group} order by p.lastname,p.firstname,p.middlename" guid="4a720225-8d94-4b47-bef3-4dbbe79eec74" />
        <msh:table viewUrl="entityShortView-psych_careCard.do" editUrl="entityParentEdit-psych_careCard.do" deleteUrl="entityParentDeleteGoParentView-psych_careCard.do" name="journal_ticket" action="entityView-psych_careCard.do" idField="1" noDataMessage="Не найдено">
			<msh:tableColumn columnName="#" property="sn"/>
			<msh:tableColumn columnName="№карты" property="2"/>
			<msh:tableColumn columnName="ФИО пациента" property="3"/>
			<msh:tableColumn columnName="Год рождения" property="6"/>
			<msh:tableColumn columnName="Дата прибытия" property="4"/>
			<msh:tableColumn columnName="Дата убытия" property="5"/>
			<msh:tableColumn columnName="Адрес" property="7"/>
			<msh:tableColumn columnName="Диагноз" property="8"/>
        </msh:table>
    <%-- 
   ,(select list(mkb.code) from Diagnosis dd left join vocidc10 mkb on mkb.id=dd.idc10_id where dd.patient_id=pcc.patient_id and dd.establishDate between area.startDate and area.finishDate)
   
    --%>
    </msh:sectionContent>
    </msh:section>
    <% } else {%>
    	<i>Выберите параметры и нажмите найти </i>
    	<% }   %>
  </tiles:put>
</tiles:insert>

