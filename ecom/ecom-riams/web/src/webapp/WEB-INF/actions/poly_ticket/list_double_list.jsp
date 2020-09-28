<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://struts.apache.org/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://www.nuzmsh.ru/tags/msh" prefix="msh" %>
<%@ taglib uri="http://www.ecom-ast.ru/tags/ecom" prefix="ecom" %>
<%@ taglib tagdir="/WEB-INF/tags" prefix="tags" %>

<tiles:insert page="/WEB-INF/tiles/mainLayout.jsp" flush="true" >

    <tiles:put name='title' type='string'>
        <msh:title mainMenu="Medcard">Просмотр дублей по специалистам</msh:title>
    </tiles:put>

    <tiles:put name='side' type='string'>
        <tags:ticket_finds currentAction="ticketdouble"/>
    </tiles:put>
    
  <tiles:put name="body" type="string">
    <msh:form action="/poly_doubleTickets_list.do" defaultField="dateBegin" disableFormDataConfirm="true" method="GET">
    <msh:panel>
      <msh:row>
        <msh:separator label="Параметры поиска" colSpan="7" />
      </msh:row>
        <msh:row>
        <msh:textField property="dateBegin" label="Период с" />
        <msh:textField property="dateEnd" label="по" />
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
    <msh:sectionContent>
    <ecom:webQuery name="journal_ticket" nativeSql="select t.medcard_id||':'||to_char(t.date,'yyyy-mm-dd')||':'||t.workFunction_id, t.medcard_id as person,t.date
    				,(select count(*) from Ticket t2 where t2.date  between '${param.dateBegin}'  and '${param.dateEnd}' and t2.medcard_id=t.medcard_id and t2.workFunction_id=t.workFunction_id and t2.date=t.date)
    				,vwf.name||' '|| p.lastname||' '||p.firstname||' '||p.middlename,pm.lastname||' '||pm.firstname||' '||pm.middlename, m.number 
    				from Ticket t 
    				left join Ticket t1 on  t.medcard_id =t1.medcard_id and t1.workFunction_id=t.workFunction_id and t1.date=t.date
    				left join WorkFunction as wf on wf.id=t.workFunction_id 
    				left join Worker as w on w.id=wf.worker_id 
    				left join Patient as p on p.id=w.person_id 
    				left join Medcard as m on m.id=t.medcard_id
    				 left join Patient as pm on pm.id=m.person_id 
    				 inner join VocWorkFunction vwf on vwf.id=wf.workFunction_id 
    				 where t.date  between '${param.dateBegin}'  
    				 and '${param.dateEnd}' and t.id>t1.id 
    				 group by t.medcard_id,t.date,t.workFunction_id
						,vwf.name,p.lastname,p.firstname,p.middlename
						,pm.lastname,pm.firstname
						,pm.middlename, m.number"/>
        <msh:table name="journal_ticket" action="poly_doubleTickets_data.do" idField="1" noDataMessage="Не найдено">
            <msh:tableColumn columnName="#" property="sn"/>
            <msh:tableColumn columnName="Дата" property="3"/>
            <msh:tableColumn columnName="Медкарта" property="7"/>
            <msh:tableColumn columnName="Пациент" property="6"/>
            <msh:tableColumn columnName="Специалист" property="5"/>
            <msh:tableColumn columnName="Кол-во" property="4"/>
        </msh:table>
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
   // var typePatient = document.forms[0].typePatient ;
     var period = document.forms[0].period ;
    
    
    if ((+'${period}')==1) {
    	period[0].checked='checked' ;
    } else {
    	period[1].checked='checked' ;
    }   
    /*if ((+'${typePatient}')==1) {
    	typePatient[0].checked='checked' ;
    } else if ((+'${typePatient}')==2) {
    	typePatient[1].checked='checked' ;
    } else {
    	typePatient[2].checked='checked' ;
    }*/
    function find() {
    	var frm = document.forms[0] ;
    	frm.target='' ;
    	frm.action='poly_doubleTickets_list.do' ;
    }
    function print() {
    	var frm = document.forms[0] ;
    	frm.target='_blank' ;
    	frm.action='poly_doubleTickets_print.do' ;
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