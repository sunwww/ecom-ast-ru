<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://struts.apache.org/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://www.nuzmsh.ru/tags/msh" prefix="msh" %>
<%@ taglib uri="http://www.ecom-ast.ru/tags/ecom" prefix="ecom" %>
<%@ taglib tagdir="/WEB-INF/tags" prefix="tags" %>

<tiles:insert page="/WEB-INF/tiles/mainLayout.jsp" flush="true" >

  <tiles:put name="title" type="string">
    <msh:title mainMenu="StacJournal" title="Журнал обращений по стационару"></msh:title>
  </tiles:put>
  <tiles:put name="side" type="string">
    	<tags:mis_journal currentAction="pres_prescription"/>
  </tiles:put>
  <tiles:put name="body" type="string">
    <msh:form action="/stac_journalByHospital.do" defaultField="dischargeIs" disableFormDataConfirm="true" method="GET">
    <msh:panel>
      <msh:row>
        <msh:separator label="Параметры поиска" colSpan="7" />
      </msh:row>
      <msh:row>
        <msh:checkBox property="dischargeIs" label="Искать по дате выписки" />
        <msh:textField property="dateBegin" label="Период с" />
        <msh:textField property="dateEnd" label="по" />
           <td>
            <input type="submit" value="Найти" />
          </td>
      </msh:row>
    </msh:panel>
    </msh:form>
    
    <%
    String date = (String)request.getParameter("dateBegin") ;
    
    if (date!=null && !date.equals("")) {
    	%>
    
    <msh:section>
    <msh:sectionTitle>Результаты поиска обращений за период с ${param.dateBegin} по ${param.dateEnd}. ${infoSearch}</msh:sectionTitle>
    <msh:sectionContent>
    <ecom:webQuery name="journal_priem" nativeSql="select ${dateSearch},CEILING(CASE WHEN count(*)-count(emergency)-count(deniedHospitalizating_id) < 1 THEN 0 ELSE count(*)-count(emergency)-count(deniedHospitalizating_id) END) as plan, CEILING(CASE WHEN count(emergency)-count(deniedHospitalizating_id) < 1 THEN 0 ELSE count(emergency)-count(deniedHospitalizating_id) END) as em, count(deniedHospitalizating_id) as denied, CEILING(count(*)-count(deniedHospitalizating_id)) as obr, count(*) as all from medcase where dtype='HospitalMedCase'  and ${dateSearch}  between '${param.dateBegin}'  and '${param.dateEnd}'  group by ${dateSearch} " />
    <msh:table name="journal_priem" action="js-stac_sslAllInfo-findByDate.do?dateSearch=${dateSearch}" idField="1">
      <msh:tableColumn columnName="Дата" property="1" />
      <msh:tableColumn columnName="Плановые" property="2" />
      <msh:tableColumn columnName="Экстренные" property="3" />
      <msh:tableColumn columnName="Отказ" property="4" />
      <msh:tableColumn columnName="Госпитализаций" identificator="false" property="5" />
      <msh:tableColumn columnName="Обращений" identificator="false" property="6" />
    </msh:table>
    </msh:sectionContent>
    </msh:section>
    <% } else {%>
    	<i>Нет данных </i>
    	<% }   %>
    
    <script type='text/javascript' src='/skin/ext/jscalendar/calendar.js'></script> 
    <script type='text/javascript' src='/skin/ext/jscalendar/calendar-setup.js'></script> 
    <script type='text/javascript' src='/skin/ext/jscalendar/calendar-ru.js'></script> 
    <style type="text/css">@import url(/skin/ext/jscalendar/css/calendar-blue.css);</style>
    <script type='text/javascript'>
    function catcalc(cal) {
			var date = cal.date;
			var time = date.getTime()
			 // use the _other_ field
			 var field = document.getElementById("dateEnd");
			 if (field == cal.params.inputField) {
				 field = document.getElementById("dateBegin");
				 time -= Date.WEEK; // substract one week
			 } else {
				 time += Date.WEEK; // add one week
			 }
			 var date2 = new Date(time);
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