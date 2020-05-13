<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://struts.apache.org/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://www.nuzmsh.ru/tags/msh" prefix="msh" %>
<%@ taglib uri="http://www.ecom-ast.ru/tags/ecom" prefix="ecom" %>
<%@ taglib tagdir="/WEB-INF/tags" prefix="tags" %>

<tiles:insert page="/WEB-INF/tiles/mainLayout.jsp" flush="true" >

    <tiles:put name='title' type='string'>
        <msh:title mainMenu="Patient">Статистика по пользователям</msh:title>
    </tiles:put>

    <tiles:put name='side' type='string'>
        
    </tiles:put>
    
  <tiles:put name="body" type="string">
    <msh:form action="/smo_journalDirectionByUsername_list.do"
     defaultField="department" disableFormDataConfirm="true" method="GET">
    <msh:panel>
      <msh:row>
        <msh:separator label="Параметры поиска" colSpan="7" />
      </msh:row>
      <msh:row>
        <td class="label" title="Дата (dateChange)" colspan="1"><label for="dateChangeName" id="dateChangeLabel">Дата:</label></td>
        <td onclick="this.childNodes[1].checked='checked';">
        	<input type="radio" name="dateChange" value="createDate">  создания
        </td>
        <td onclick="this.childNodes[1].checked='checked';">
        	<input type="radio" name="dateChange" value="dateStart">  приема
        </td>
        <msh:textField property="dateBegin" label="Период с" />
        <msh:textField property="dateEnd" label="по" />
        <td>
            <input type="submit" onclick="find()" value="Найти" />
          </td>
        </msh:row>
        
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
    	String dateSearch = request.getParameter("dateChange") ;
    	if (dateSearch!=null && dateSearch.equals("dateStart")) {
    		request.setAttribute("dateSearch", "dateStart") ;
    	} else {
    		request.setAttribute("dateSearch", "createDate") ;
    	}
    	%>
    
    <msh:section>
    <msh:sectionTitle>Результаты поиска направлений ${info}. Период с ${param.dateBegin} по ${dateEnd}. ${infoSearch}</msh:sectionTitle>
    <msh:sectionContent>
    <ecom:webQuery name="journal_direction" nativeSql="select  
    to_CHAR(v.${dateSearch},'DD.MM.YYYY')||':'||coalesce(username,'')||':${dateSearch1}' as idPar
    ,v.${dateSearch} as dateSearch,usernameCreate,count(*) as cnt 
    from MedCase v where v.dtype='Visit' and v.${dateSearch}  between to_date('${param.dateBegin}','dd.mm.yyyy')  
    	and to_date('${dateEnd}','dd.mm.yyyy')
    	 ${add} group by v.${dateSearch},v.username" />
        <msh:table name="journal_ticket" action="poly_ticketsByUserData.do" idField="1" noDataMessage="Не найдено">
            <msh:tableColumn columnName="#" property="sn"/>
            <msh:tableColumn columnName="Дата" property="2"/>
            <msh:tableColumn columnName="Пользователь" property="3"/>
            <msh:tableColumn columnName="Кол-во" property="4"/>
        </msh:table>
    </msh:sectionContent>    
    
		<msh:sectionTitle>Итог по пользователям:</msh:sectionTitle>    
		<msh:sectionContent>
        <ecom:webQuery name="journal_ticket_sum" nativeSql="select  usernameCreate,count(*) from Ticket where ${dateSearch}  between '${param.dateBegin}'  and '${dateEnd}' ${add} group by usernameCreate" />
        <msh:table name="journal_ticket_sum"  idField="1" action="" noDataMessage="Не найдено">
            <msh:tableColumn columnName="#" property="sn"/>
            <msh:tableColumn columnName="Пользователь" property="1"/>
            <msh:tableColumn columnName="Кол-во" property="2"/>
        </msh:table>
    </msh:sectionContent>
    </msh:section>
    <% } else {%>
    	<i>Выберите параметры поиска и нажмите "Найти"</i>
    	<% }   %>
    
    <%--<script type='text/javascript' src='/skin/ext/jscalendar/calendar.js'></script> 
    <script type='text/javascript' src='/skin/ext/jscalendar/calendar-setup.js'></script> 
    <script type='text/javascript' src='/skin/ext/jscalendar/calendar-ru.js'></script> 
    <style type="text/css">@import url(/skin/ext/jscalendar/css/calendar-blue.css);</style>
     --%>
    <script type='text/javascript'>
    //var period = document.forms[0].period ;
    var dateChange = document.forms[0].dateChange ;
    /*
    if ((+'${period}')==1) {
    	period[0].checked='checked' ;
    } else {
    	period[1].checked='checked' ;
    }*/
    if ((+'${dateChange}')==2) {
    	dateChange[1].checked='checked' ;
    } else {
    	dateChange[0].checked='checked' ;
    }
    function find() {
    	var frm = document.forms[0] ;
    	frm.target='' ;
    	frm.action='poly_ticketsByUserList.do' ;
    }
    function print() {
    	var frm = document.forms[0] ;
    	frm.target='_blank' ;
    	frm.action='stac_print_reestrByDepartment.do' ;
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

