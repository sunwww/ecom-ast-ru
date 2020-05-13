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
    <msh:form action="/poly_ticketsBySpecialistList.do" defaultField="dateBegin" disableFormDataConfirm="true" method="GET">
    <msh:panel>
      <msh:row>
        <msh:separator label="Параметры поиска" colSpan="7" />
      </msh:row>
      <msh:row>
        <td class="label" title="Поиск по пациентам (typePatient)" colspan="1"><label for="typePatientName" id="typePatientLabel">Пациенты:</label></td>
        <td onclick="this.childNodes[1].checked='checked';">
        	<input type="radio" name="typePatient" value="1">  региональные
        </td>
        <td onclick="this.childNodes[1].checked='checked';">
        	<input type="radio" name="typePatient" value="2">  иногородные
        </td>
        <td onclick="this.childNodes[1].checked='checked';">
        	<input type="radio" name="typePatient" value="3">  иностранцы
        </td>
        <td onclick="this.childNodes[1].checked='checked';">
        	<input type="radio" name="typePatient" value="4">  все
        </td>
        </msh:row>
        <msh:autoComplete vocName="workFunction" property="spec" 
        horizontalFill="true" fieldColSpan="5"
        label="Специалист" size="100"
        	
        />
        <msh:row>
        <msh:textField property="dateBegin" label="Период с" />
        <msh:textField property="dateEnd" label="по" />

           <td>
            <input type="submit" onclick="find()" value="Найти" />
          </td>
        </msh:row>
        <%--
        <msh:row>
        <td class="label" title="Длительность (period)" colspan="1"><label for="periodName" id="peroidLabel">Длительность:</label></td>
        <td onclick="this.childNodes[1].checked='checked';changePeriod()">
        	<input type="radio" name="period" value="1"> Неделя
        </td>
        <td onclick="this.childNodes[1].checked='checked';changePeriod()">
        	<input type="radio" name="period" value="2"> Месяц
        </td>

      </msh:row>
       --%>
    </msh:panel>
    </msh:form>
    
    <%
    String date = (String)request.getParameter("dateBegin") ;
    if (date!=null && !date.equals(""))  {
    	String date1 = (String)request.getParameter("dateEnd") ;
    	if (date1==null || date1.equals("")) {
    		request.setAttribute("dateEnd", date) ;
    	} else {
    		request.setAttribute("dateEnd", date1) ;
    	}
    	String spec =""+request.getParameter("spec") ;
    	if (spec!=null && !spec.equals("") && !spec.equals("0") &!spec.equals("null")) {
    		request.setAttribute("spec", " and t.workFunction_id='"+spec+"'") ;
    	} else {
    		request.setAttribute("spec", "") ;
    	}
    	%>
    
    <msh:section>
    <msh:sectionTitle>Результаты поиска талонов ${infoTypePat}. Период с ${param.dateBegin} по ${dateEnd}. ${infoSearch}</msh:sectionTitle>
    <msh:sectionContent>
    <ecom:webQuery name="journal_ticket" nativeSql="select  
    to_CHAR(t.date,'DD.MM.YYYY')||':${param.typePatient}'||':'||t.workFunction_id as idPar
    ,t.date as tdate
    ,count(*) as cnt,vwf.name||' '|| wp.lastname||' '||wp.firstname||' '||wp.middlename as doctor
    ,count(case when cast(t.talk as int)=1 then 1 else null end) as cntTalk
    from Ticket t left join medcard as m on m.id=t.medcard_id 
    left join Patient p on p.id=m.person_id
    left join VocSocialStatus pvss on pvss.id=p.socialStatus_id
    left join Omc_Oksm ok on p.nationality_id=ok.id  
    left join WorkFunction as wf on wf.id=t.workFunction_id 
    left join Worker as w on w.id=wf.worker_id 
    left join Patient as wp on wp.id=w.person_id 
    inner join VocWorkFunction vwf on vwf.id=wf.workFunction_id 
    where t.date  between to_date('${param.dateBegin}','dd.mm.yyyy')  
    and to_date('${dateEnd}','dd.mm.yyyy')
     and t.status='2'  ${add} ${spec} group by t.date
     ,t.workFunction_id,wp.lastname,wp.middlename,wp.firstname,vwf.name" />
	<msh:ifInRole roles="/Policy/Mis/MisLpu/Psychiatry">
        <msh:table name="journal_ticket" action="poly_ticketsBySpecialistData.do" idField="1" noDataMessage="Не найдено">
            <msh:tableColumn columnName="#" property="sn"/>
            <msh:tableColumn columnName="Дата" property="2"/>
            <msh:tableColumn columnName="Специалист" property="4"/>
            <msh:tableColumn columnName="Кол-во беседа с род." property="5" isCalcAmount="true"/>
            <msh:tableColumn columnName="Кол-во" property="3" isCalcAmount="true"/>
        </msh:table>
	</msh:ifInRole>
	<msh:ifNotInRole roles="/Policy/Mis/MisLpu/Psychiatry">
        <msh:table name="journal_ticket" action="poly_ticketsBySpecialistData.do" idField="1" noDataMessage="Не найдено">
            <msh:tableColumn columnName="#" property="sn"/>
            <msh:tableColumn columnName="Дата" property="2"/>
            <msh:tableColumn columnName="Специалист" property="4"/>
            <msh:tableColumn columnName="Кол-во" property="3" isCalcAmount="true"/>
        </msh:table>
	</msh:ifNotInRole>
    </msh:sectionContent>
    <msh:sectionTitle>Разбивка по МКБ</msh:sectionTitle>
    <msh:sectionContent>
    <ecom:webQuery name="journal_ticket_mkb" nativeSql="select 
    t.idc10_id||':${param.typePatient}'||':'||t.workFunction_id||':${param.dateBegin}:${param.dateEnd}' as idPar
    ,count(*) as cnt1
    ,vwf.name||' '|| wp.lastname||' '||wp.firstname||' '||wp.middlename as doctor,mkb.code as mkbcode 
    ,count(case when cast(t.talk as int)=1 then 1 else null end) as cntTalk
    from Ticket t left join medcard as m on m.id=t.medcard_id 
    left join vocidc10 as mkb on mkb.id=t.idc10_id 
    left join Patient p on p.id=m.person_id
    left join Omc_Oksm ok on p.nationality_id=ok.id  
    left join VocSocialStatus pvss on pvss.id=p.socialStatus_id
    left join WorkFunction as wf on wf.id=t.workFunction_id 
    left join Worker as w on w.id=wf.worker_id 
    left join Patient as wp on wp.id=w.person_id 
    inner join VocWorkFunction vwf on vwf.id=wf.workFunction_id  
    where t.date  between to_date('${param.dateBegin}','dd.mm.yyyy')  
    and to_date('${dateEnd}','dd.mm.yyyy')
     and t.status='2'  ${add} ${spec}
     group by t.workFunction_id,t.idc10_id
     ,vwf.name,wp.lastname,wp.firstname,wp.middlename,mkb.code
     " />
    <msh:ifInRole roles="/Policy/Mis/MisLpu/Psychiatry">
        <msh:table name="journal_ticket_mkb" action="poly_ticketsBySpecialistMkbData.do" idField="1" noDataMessage="Не найдено">
            <msh:tableColumn columnName="#" property="sn"/>
            <msh:tableColumn columnName="Специалист" property="3"/>
            <msh:tableColumn columnName="Диагноз" property="4"/>
            <msh:tableColumn columnName="Кол-во бесед" property="5" isCalcAmount="true"/>
            <msh:tableColumn columnName="Кол-во" property="2" isCalcAmount="true"/>
        </msh:table>
    </msh:ifInRole>
    <msh:ifNotInRole roles="/Policy/Mis/MisLpu/Psychiatry">
        <msh:table name="journal_ticket_mkb" action="poly_ticketsBySpecialistMkbData.do" idField="1" noDataMessage="Не найдено">
            <msh:tableColumn columnName="#" property="sn"/>
            <msh:tableColumn columnName="Специалист" property="3"/>
            <msh:tableColumn columnName="Диагноз" property="4"/>
            <msh:tableColumn columnName="Кол-во" property="2" isCalcAmount="true"/>
        </msh:table>
    </msh:ifNotInRole>
    </msh:sectionContent>
    <msh:sectionTitle>Итог</msh:sectionTitle>
    <msh:sectionContent>
    <ecom:webQuery name="journal_ticket_sum" nativeSql="select count(*)
    ,vwf.name||' '|| wp.lastname||' '||wp.firstname||' '||wp.middlename as doctor
    , count(case when mkb.code like 'Z%' ${add1} then t.id else null end) as idccnt 
    ,count(case when t.talk='1'  then 1 else null end) as cnttalk,wp.snils
    from Ticket t left join medcard as m on m.id=t.medcard_id 
    left join Patient p on p.id=m.person_id
    left join Omc_Oksm ok on p.nationality_id=ok.id  
    left join VocSocialStatus pvss on pvss.id=p.socialStatus_id
    left join WorkFunction as wf on wf.id=t.workFunction_id 
    left join Worker as w on w.id=wf.worker_id 
    left join Patient as wp on wp.id=w.person_id 
    left join VocIdc10 as mkb on mkb.id=t.idc10_id
    inner join VocWorkFunction vwf on vwf.id=wf.workFunction_id  
    where t.date  between to_date('${param.dateBegin}','dd.mm.yyyy')
      and to_date('${dateEnd}','dd.mm.yyyy') 
      and t.status='2' ${add} ${spec} group by t.workFunction_id
      ,vwf.name,wp.lastname,wp.firstname,wp.middlename,wp.snils
      " />
    <msh:ifInRole roles="/Policy/Mis/MisLpu/Psychiatry">
        <msh:table name="journal_ticket_sum" action="" idField="1" noDataMessage="Не найдено">
            <msh:tableColumn columnName="#" property="sn"/>
            <msh:tableColumn columnName="Специалист" property="2"/>
            <msh:tableColumn columnName="СНИЛС спец." property="5"/>
            <msh:tableColumn columnName="Кол-во Z*" property="3" isCalcAmount="true"/>
            <msh:tableColumn columnName="Кол-во бесед" property="4" isCalcAmount="true"/>
            <msh:tableColumn columnName="Всего" property="1" isCalcAmount="true"/>
        </msh:table>
    </msh:ifInRole>
    <msh:ifNotInRole roles="/Policy/Mis/MisLpu/Psychiatry">
        <msh:table name="journal_ticket_sum" action="" idField="1" noDataMessage="Не найдено">
            <msh:tableColumn columnName="#" property="sn"/>
            <msh:tableColumn columnName="Специалист" property="2"/>
            <msh:tableColumn columnName="СНИЛС спец." property="5"/>
            <msh:tableColumn columnName="Кол-во Z*" property="3" isCalcAmount="true"/>
            <msh:tableColumn columnName="Всего" property="1" isCalcAmount="true"/>
        </msh:table>
    </msh:ifNotInRole>
    </msh:sectionContent>
    </msh:section>
    <% } else {%>
    	<i>Выберите параметры поиска и нажмите "Найти" </i>
    	<% }   %>
    <%--
    <script type='text/javascript' src='/skin/ext/jscalendar/calendar.js'></script> 
    <script type='text/javascript' src='/skin/ext/jscalendar/calendar-setup.js'></script> 
    <script type='text/javascript' src='/skin/ext/jscalendar/calendar-ru.js'></script> 
    <style type="text/css">@import url(/skin/ext/jscalendar/css/calendar-blue.css);</style>
     --%>
    <script type='text/javascript'>
    checkFieldUpdate('typePatient','${typePatient}',4) ;
    
    
    function checkFieldUpdate(aField,aValue,aDefault) {
    	eval('var chk =  document.forms[0].'+aField) ;
    	eval('var aMax =  chk.length') ;
    	if (aMax>aDefault) {aDefault=aMax}
    	if ((+aValue)>aMax) {
    		chk[+aDefault-1].checked='checked' ;
    	} else {
    		chk[+aValue-1].checked='checked' ;
    	}
    }
    function find() {
    	var frm = document.forms[0] ;
    	frm.target='' ;
    	frm.action='poly_ticketsBySpecialistList.do' ;
    }
    function print() {
    	var frm = document.forms[0] ;
    	frm.target='_blank' ;
    	frm.action='.do' ;
    }
    /*
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
			 */
    </script>
  </tiles:put>
</tiles:insert>

