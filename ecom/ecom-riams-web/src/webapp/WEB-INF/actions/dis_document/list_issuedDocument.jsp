<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://struts.apache.org/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://www.nuzmsh.ru/tags/msh" prefix="msh" %>
<%@ taglib uri="http://www.ecom-ast.ru/tags/ecom" prefix="ecom" %>
<%@ taglib tagdir="/WEB-INF/tags" prefix="tags" %>

<tiles:insert page="/WEB-INF/tiles/mainLayout.jsp" flush="true" >

  <tiles:put name="title" type="string">
        <msh:title mainMenu="Disability">Выданные документы за период</msh:title>
  </tiles:put>
    <tiles:put name='side' type='string'>
        <tags:dis_menu currentAction="issuedDNT"/>
    </tiles:put>
  <tiles:put name="body" type="string">
    <msh:form action="/dis_issuedDocument.do" defaultField="dischargeIs" disableFormDataConfirm="true" method="GET" guid="d7b31bc2-38f0-42cc-8d6d-19395273168f">
    <msh:panel>
      <msh:row>
        <msh:separator label="Параметры поиска" colSpan="7" guid="15c6c628-8aab-4c82-b3d8-ac77b7b3f700" />
      </msh:row>
      <msh:row>
        <msh:checkBox property="dischargeIs" label="Искать по дате закрытия" />
        <msh:textField property="dateBegin" label="Период с" />
        <msh:textField property="dateEnd" label="по"/>
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
    <ecom:webQuery name="journal_priem" nativeSql="select ${dateGroup}  ,count(dd.id) from disabilitydocument as dd where dd.isclose =1 group by ${dateGroup} having ${dateGroup } between cast('${param.dateBegin}' as date) and isnull(cast('${param.dateEnd}' as date),cast('${param.dateBegin}' as date))" guid="4a720225-8d94-4b47-bef3-4dbbe79eec74" />
    <msh:table name="journal_priem" action="dis_documentByDate.do?info=закрытых&type=close&dateSearch=${dateSearch}&infoSearch=${infoSearch}&" idField="1" guid="b621e361-1e0b-4ebd-9f58-b7d919b45bd6">
      <msh:tableColumn columnName="Дата" property="1" guid="de1f591c-02b8-4875-969f-d2698689db5d" />
      <msh:tableColumn columnName="Количество" property="2" guid="8410185d-711a-4851-bca4-913a77381989" />
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
			
			/*var date = cal.date;
			var time = date.getTime()
			 // use the _other_ field
			 var field = document.getElementById("dateEnd");
			 i
			 f (field == cal.params.inputField) {
				 field = document.getElementById("dateBegin");
				 time -= Date.WEEK; // substract one week
			 } else {
				 time += Date.WEEK; // add one week
			 }
			 var date2 = new Date(time);
			 field.value = date2.print("%Y-%m-%d");*/
	}
			 Calendar.setup({
				 inputField : "dateBegin", // id of the input field
				 ifFormat : "%Y-%m-%d", // format of the input field
				 showsTime : false,
				 timeFormat : "24",
				 eventName: "focus",
				 //onUpdate : catcalc
			 });
			 Calendar.setup({
				 inputField : "dateEnd",
				 ifFormat : "%Y-%m-%d",
				 showsTime : false,
				 timeFormat : "24",
				 eventName: "focus",
				 //onUpdate : catcalc
 			});
    </script>
    
  </tiles:put>
</tiles:insert>

