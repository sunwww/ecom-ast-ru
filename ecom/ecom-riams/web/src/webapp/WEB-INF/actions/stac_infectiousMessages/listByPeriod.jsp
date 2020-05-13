<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://struts.apache.org/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://www.nuzmsh.ru/tags/msh" prefix="msh" %>
<%@ taglib uri="http://www.ecom-ast.ru/tags/ecom" prefix="ecom" %>
<%@ taglib tagdir="/WEB-INF/tags" prefix="tags" %>

<tiles:insert page="/WEB-INF/tiles/mainLayout.jsp" flush="true" >

  <tiles:put name="title" type="string">
    <msh:title mainMenu="Journals" title="Журнал экстренных извещений об инфекционных заболеваниях"></msh:title>
  </tiles:put>
  <tiles:put name="side" type="string">
  	<tags:style_currentMenu currentAction="stac_infectiousMessages" />
    	<tags:mis_journal />
  </tiles:put>
  <tiles:put name="body" type="string">
    <msh:form action="/journal_infectMessage.do" defaultField="dateBegin" disableFormDataConfirm="true" method="GET">
    <msh:panel>
      <msh:row>
        <msh:separator label="Параметры поиска" colSpan="7" />
      </msh:row>
      <msh:row>
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
    <msh:sectionTitle>Результаты поиска за период с ${param.dateBegin} по ${param.dateEnd}.</msh:sectionTitle>
    <msh:sectionContent>
    <ecom:webQuery isReportBase="true" name="journal_infect" nativeSql="select phoneDate, count(id) from PhoneMessage where phoneMessageType_id=1 and phoneDate  between '${param.dateBegin}'  and '${param.dateEnd}'  group by phoneDate " />
    <msh:table name="journal_infect" action="js-stac_infectiousMessages-listByDate.do?dateSearch=${dateSearch}" idField="1">
      <msh:tableColumn columnName="Дата" property="1" />
      <msh:tableColumn columnName="Количество сообщений" identificator="false" property="2" />
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
    
			 Calendar.setup({
				 inputField : "dateBegin", // id of the input field
				 ifFormat : "%Y-%m-%d", // format of the input field
				 showsTime : false,
				 timeFormat : "24",
				 eventName: "focus"
			 });
			 Calendar.setup({
				 inputField : "dateEnd",
				 ifFormat : "%Y-%m-%d",
				 showsTime : false,
				 timeFormat : "24",
				 eventName: "focus"
 			});
    </script>
    
  </tiles:put>
</tiles:insert>