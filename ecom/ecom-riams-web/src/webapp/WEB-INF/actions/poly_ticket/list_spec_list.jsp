<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://struts.apache.org/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://www.nuzmsh.ru/tags/msh" prefix="msh" %>
<%@ taglib uri="http://www.ecom-ast.ru/tags/ecom" prefix="ecom" %>
<%@ taglib tagdir="/WEB-INF/tags" prefix="tags" %>

<tiles:insert page="/WEB-INF/tiles/mainLayout.jsp" flush="true" >

    <tiles:put name='title' type='string'>
        <msh:title mainMenu="Medcard">Просмотр данных по специалистам</msh:title>
    </tiles:put>

    <tiles:put name='side' type='string'>
        <tags:ticket_finds currentAction="ticketsBySpec"/>
    </tiles:put>
    
  <tiles:put name="body" type="string">
    <msh:form action="/poly_ticketsBySpecialistList.do" defaultField="" disableFormDataConfirm="true" method="GET" guid="d7b31bc2-38f0-42cc-8d6d-19395273168f">
    <msh:panel guid="6ae283c8-7035-450a-8eb4-6f0f7da8a8ff">
      <msh:row guid="53627d05-8914-48a0-b2ec-792eba5b07d9">
        <msh:separator label="Параметры поиска" colSpan="7" guid="15c6c628-8aab-4c82-b3d8-ac77b7b3f700" />
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
        <td class="label" title="Длительность (period)" colspan="1"><label for="periodName" id="peroidLabel">Длительность:</label></td>
        <td onclick="this.childNodes[1].checked='checked';changePeriod()">
        	<input type="radio" name="period" value="1"> Неделя
        </td>
        <td onclick="this.childNodes[1].checked='checked';changePeriod()">
        	<input type="radio" name="period" value="2"> Месяц
        </td>
           <td>
            <input type="submit" onclick="find()" value="Найти" />
          </td>

      </msh:row>
    </msh:panel>
    </msh:form>
    
    <%
    String date = (String)request.getParameter("dateBegin") ;
    if (date!=null && !date.equals(""))  {
    	%>
    
    <msh:section>
    <msh:sectionTitle>Результаты поиска талонов ${infoTypePat}. Период с ${param.dateBegin} по ${param.dateEnd}. ${infoSearch}</msh:sectionTitle>
    <msh:sectionContent>
    <ecom:webQuery name="journal_ticket" nativeSql="select  to_CHAR(t.date,'DD.MM.YYYY')||':${param.typePatient}'||':'||t.workFunction_id,t.date
    ,count(*),vwf.name||' '|| p.lastname||' '||p.firstname||' '||p.middlename
    ,count(case when t.talk=1 then 1 else null end)
    from Ticket t left join medcard as m on m.id=t.medcard_id 
    left join WorkFunction as wf on wf.id=t.workFunction_id 
    left join Worker as w on w.id=wf.worker_id left join Patient as p on p.id=w.person_id inner join VocWorkFunction vwf on vwf.id=wf.workFunction_id 
    where t.date  between '${param.dateBegin}'  and '${param.dateEnd}' and t.status='2'  ${add} group by t.date,t.workFunction_id,p.lastname,p.middlename,p.firstname,vwf.name" guid="4a720225-8d94-4b47-bef3-4dbbe79eec74" />
	<msh:ifInRole roles="/Policy/Mis/MisLpu/Psychiatry">
        <msh:table name="journal_ticket" action="poly_ticketsBySpecialistData.do" idField="1" noDataMessage="Не найдено">
            <msh:tableColumn columnName="#" property="sn"/>
            <msh:tableColumn columnName="Дата" property="2"/>
            <msh:tableColumn columnName="Специалист" property="4"/>
            <msh:tableColumn columnName="Кол-во беседа с род." property="5"/>
            <msh:tableColumn columnName="Кол-во" property="3"/>
        </msh:table>
	</msh:ifInRole>
	<msh:ifNotInRole roles="/Policy/Mis/MisLpu/Psychiatry">
        <msh:table name="journal_ticket" action="poly_ticketsBySpecialistData.do" idField="1" noDataMessage="Не найдено">
            <msh:tableColumn columnName="#" property="sn"/>
            <msh:tableColumn columnName="Дата" property="2"/>
            <msh:tableColumn columnName="Специалист" property="4"/>
            <msh:tableColumn columnName="Кол-во" property="3"/>
        </msh:table>
	</msh:ifNotInRole>
    </msh:sectionContent>
    <msh:sectionTitle>Разбивка по МКБ</msh:sectionTitle>
    <msh:sectionContent>
    <ecom:webQuery name="journal_ticket_mkb" nativeSql="select t.idc10_id||':${param.typePatient}'||':'||t.workFunction_id||':${param.dateBegin}:${param.dateEnd}' ,count(*),vwf.name||' '|| p.lastname||' '||p.firstname||' '||p.middlename,mkb.code 
    ,count(case when t.talk=1 then 1 else null end)
    from Ticket t left join medcard as m on m.id=t.medcard_id 
    left join vocidc10 as mkb on mkb.id=t.idc10_id 
    left join WorkFunction as wf on wf.id=t.workFunction_id 
    left join Worker as w on w.id=wf.worker_id 
    left join Patient as p on p.id=w.person_id 
    inner join VocWorkFunction vwf on vwf.id=wf.workFunction_id  
    where t.date  between '${param.dateBegin}'  and '${param.dateEnd}' and t.status='2'  ${add} group by t.workFunction_id,t.idc10_id" guid="4a720225-8d94-4b47-bef3-4dbbe79eec74" />
    <msh:ifInRole roles="/Policy/Mis/MisLpu/Psychiatry">
        <msh:table name="journal_ticket_mkb" action="poly_ticketsBySpecialistMkbData.do" idField="1" noDataMessage="Не найдено">
            <msh:tableColumn columnName="#" property="sn"/>
            <msh:tableColumn columnName="Специалист" property="3"/>
            <msh:tableColumn columnName="Диагноз" property="4"/>
            <msh:tableColumn columnName="Кол-во бесед" property="5"/>
            <msh:tableColumn columnName="Кол-во" property="2"/>
        </msh:table>
    </msh:ifInRole>
    <msh:ifNotInRole roles="/Policy/Mis/MisLpu/Psychiatry">
        <msh:table name="journal_ticket_mkb" action="poly_ticketsBySpecialistMkbData.do" idField="1" noDataMessage="Не найдено">
            <msh:tableColumn columnName="#" property="sn"/>
            <msh:tableColumn columnName="Специалист" property="3"/>
            <msh:tableColumn columnName="Диагноз" property="4"/>
            <msh:tableColumn columnName="Кол-во" property="2"/>
        </msh:table>
    </msh:ifNotInRole>
    </msh:sectionContent>
    <msh:sectionTitle>Итог</msh:sectionTitle>
    <msh:sectionContent>
    <ecom:webQuery name="journal_ticket_sum" nativeSql="select count(*),vwf.name||' '|| p.lastname||' '||p.firstname||' '||p.middlename, (select count(*) from Ticket t1 left join VocIdc10 as mkb on mkb.id=t1.idc10_id left join Medcard m1 on m1.id=t1.medcard_id where t1.date  between '${param.dateBegin}'  and '${param.dateEnd}' and t1.workfunction_id=t.workfunction_id and mkb.code like 'Z%' ${add1} ) 
    ,count(case when t.talk=1 then 1 else null end),p.snils
    from Ticket t left join medcard as m on m.id=t.medcard_id left join WorkFunction as wf on wf.id=t.workFunction_id left join Worker as w on w.id=wf.worker_id left join Patient as p on p.id=w.person_id inner join VocWorkFunction vwf on vwf.id=wf.workFunction_id  
    where t.date  between '${param.dateBegin}'  and '${param.dateEnd}' and t.status='2' ${add} group by t.workFunction_id" guid="4a720225-8d94-4b47-bef3-4dbbe79eec74" />
    <msh:ifInRole roles="/Policy/Mis/MisLpu/Psychiatry">
        <msh:table name="journal_ticket_sum" action="" idField="1" noDataMessage="Не найдено">
            <msh:tableColumn columnName="#" property="sn"/>
            <msh:tableColumn columnName="Специалист" property="2"/>
            <msh:tableColumn columnName="СНИЛС спец." property="5"/>
            <msh:tableColumn columnName="Кол-во Z*" property="3"/>
            <msh:tableColumn columnName="Кол-во бесед" property="4"/>
            <msh:tableColumn columnName="Всего" property="1"/>
        </msh:table>
    </msh:ifInRole>
    <msh:ifNotInRole roles="/Policy/Mis/MisLpu/Psychiatry">
        <msh:table name="journal_ticket_sum" action="" idField="1" noDataMessage="Не найдено">
            <msh:tableColumn columnName="#" property="sn"/>
            <msh:tableColumn columnName="Специалист" property="2"/>
            <msh:tableColumn columnName="СНИЛС спец." property="5"/>
            <msh:tableColumn columnName="Кол-во Z*" property="3"/>
            <msh:tableColumn columnName="Всего" property="1"/>
        </msh:table>
    </msh:ifNotInRole>
    </msh:sectionContent>
    </msh:section>
    <% } else {%>
    	<i>Выберите параметры поиска и нажмите "Найти" </i>
    	<% }   %>
    
    <script type='text/javascript' src='/skin/ext/jscalendar/calendar.js'></script> 
    <script type='text/javascript' src='/skin/ext/jscalendar/calendar-setup.js'></script> 
    <script type='text/javascript' src='/skin/ext/jscalendar/calendar-ru.js'></script> 
    <style type="text/css">@import url(/skin/ext/jscalendar/css/calendar-blue.css);</style>
    <script type='text/javascript'>
    var typePatient = document.forms[0].typePatient ;
     var period = document.forms[0].period ;
    
    
    if ((+'${period}')==1) {
    	period[0].checked='checked' ;
    } else {
    	period[1].checked='checked' ;
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
    	frm.action='poly_ticketsBySpecialistList.do' ;
    }
    function print() {
    	var frm = document.forms[0] ;
    	frm.target='_blank' ;
    	frm.action='poly_ticketsBySpecialistPrint.do' ;
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
		 } else {
		 	date2=new Date(date.getFullYear(),date.getMonth()+1,date.getDate()-1) ;
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
				 } else {
				 	date2=new Date(date.getFullYear(),date.getMonth()-1,date.getDate()+1) ;
				 	
				 }
			 } else {
				 if (getPeriod()==1) {
				 	date2 = new Date(date.getFullYear(),date.getMonth(),date.getDate()+6) ;
				 	//time += Date.WEEK; // substract one week
				 } else {
				 	date2=new Date(date.getFullYear(),date.getMonth()+1,date.getDate()-1) ;
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
  </tiles:put>
</tiles:insert>

