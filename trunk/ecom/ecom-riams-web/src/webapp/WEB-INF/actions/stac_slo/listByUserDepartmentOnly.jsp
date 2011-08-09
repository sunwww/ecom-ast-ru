<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://struts.apache.org/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://www.nuzmsh.ru/tags/msh" prefix="msh" %>
<%@ taglib uri="http://www.ecom-ast.ru/tags/ecom" prefix="ecom" %>
<%@ taglib tagdir="/WEB-INF/tags" prefix="tags" %>

<tiles:insert page="/WEB-INF/tiles/mainLayout.jsp" flush="true" >

  <tiles:put name="title" type="string">
    <msh:title guid="helloItle-123" mainMenu="StacJournal">Журнал обращений по отделению</msh:title>
  </tiles:put>
  <tiles:put name="side" type="string">
  	<tags:stac_journal currentAction="stac_journalByUserDepartmentOnly"/>
  </tiles:put>
  <tiles:put name="body" type="string">
    <msh:form action="/stac_journalByUserDepartmentOnly.do" defaultField="department" disableFormDataConfirm="true" method="GET" guid="d7b31bc2-38f0-42cc-8d6d-19395273168f">
    <msh:panel guid="6ae283c8-7035-450a-8eb4-6f0f7da8a8ff">
      <msh:row guid="53627d05-8914-48a0-b2ec-792eba5b07d9">
        <msh:separator label="Параметры поиска" colSpan="7" guid="15c6c628-8aab-4c82-b3d8-ac77b7b3f700" />
      </msh:row>
      <msh:ifInRole roles="/Policy/Mis/MedCase/Stac/Journal/ShowInfoAllDepartments">
	      <msh:row>
	      	<msh:autoComplete property="department" vocName="lpu" label="Отделение" fieldColSpan="6" horizontalFill="true"/>
	      </msh:row>
      </msh:ifInRole>
      <msh:row guid="7d80be13-710c-46b8-8503-ce0413686b69">
        <msh:checkBox property="dischargeIs" label="Искать по дате выписки" guid="f5458f6-b5b8-4d48-a045-ba7b9f875245" />
        <msh:textField property="dateBegin" label="Период с" guid="8d7ef035-1273-4839-a4d8-1551c623caf1" />
        <msh:textField property="dateEnd" label="по" guid="f54568f6-b5b8-4d48-a045-ba7b9f875245" />
           <td>
            <input type="submit" value="Найти" />
          </td>
      </msh:row>
    </msh:panel>
    </msh:form>
    
    <%
    String date = (String)request.getParameter("dateBegin") ;
    Long department = (Long)request.getAttribute("department") ;
    if (date!=null && !date.equals("") && department!=null && department.intValue()>0 )  {
    	%>
    
    <msh:section>
    <msh:sectionTitle>Результаты поиска обращений в отделение  ${departmentInfo}. Период с ${param.dateBegin} по ${param.dateEnd}. ${infoSearch}</msh:sectionTitle>
    <msh:sectionContent>
    <ecom:webQuery name="journal_priem" nativeSql="select ${dateSearch}, count(*) as all from medcase where dtype='DepartmentMedCase' and department_id='${department}'  and ${dateSearch}  between '${param.dateBegin}'  and '${param.dateEnd}'  group by ${dateSearch} " guid="4a720225-8d94-4b47-bef3-4dbbe79eec74" />
    <msh:table name="journal_priem" action="js-stac_slo-findByDate.do?action=stac_journalByUserDepartmentOnly&dateSearch=${dateSearch}&department=${department}&departmentInfo=${departmentInfo}" idField="1" guid="b621e361-1e0b-4ebd-9f58-b7d919b45bd6">
      <msh:tableColumn property="sn" columnName="#"/>
      <msh:tableColumn columnName="Дата" property="1" guid="de1f591c-02b8-4875-969f-d2698689db5d" />
      <msh:tableColumn columnName="Кол-во" identificator="false" property="2" guid="7f73955-a5cb-4497-bd0b-f4d05848f049" />
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

