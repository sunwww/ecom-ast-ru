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
  	<tags:stac_journal currentAction="stac_reestrByHospital"/>
  </tiles:put>
  <tiles:put name="body" type="string">
    <msh:form action="/stac_reestrByHospital.do" defaultField="department" disableFormDataConfirm="true" method="GET" guid="d7b31bc2-38f0-42cc-8d6d-19395273168f">
    <msh:panel guid="6ae283c8-7035-450a-8eb4-6f0f7da8a8ff">
      <msh:row guid="53627d05-8914-48a0-b2ec-792eba5b07d9">
        <msh:separator label="Параметры поиска" colSpan="7" guid="15c6c628-8aab-4c82-b3d8-ac77b7b3f700" />
      </msh:row>
      <msh:row guid="7d80be13-710c-46b8-8503-ce0413686b69">
        <msh:checkBox property="dischargeIs" label="Искать по дате выписки" guid="f5458f6-b5b8-4d48-a045-ba7b9f875245" />
        <msh:checkBox property="emergancyIs" label="Экстренные" guid="fdsfsadf"/>
      </msh:row>
      <msh:row guid="Дата">
        <msh:textField property="dateBegin" label="Дата" guid="8d7ef035-1273-4839-a4d8-1551c623caf1" />
           <td>
            <input type="submit" onclick="find()" value="Найти" />
          </td>
           <td>
            <input type="submit" onclick="print()" value="Печать" />
          </td>
      </msh:row>
    </msh:panel>
    </msh:form>
    
    <%
    String date = request.getParameter("dateBegin") ;
    if (date!=null && !date.equals(""))  {
    	String emer= request.getParameter("emergancyIs") ;
    	if (emer!=null && emer.equals("on")) {
    		request.setAttribute("emerIs","m.emergency=1") ;
    		request.setAttribute("emerInfo","экстренно") ;
    	} else {
    		request.setAttribute("emerIs","(m.emergency is null or m.emergency = 0)") ;
    		request.setAttribute("emerInfo","планово") ;
    	}
    	String disc = request.getParameter("dischargeIs") ;
    	if (disc!=null && disc.equals("on")) {
    		request.setAttribute("dateI","dateFinish") ;
    		request.setAttribute("timeI","dischargeTime") ;
    		request.setAttribute("dischInfo","выбывшим ") ;
    	} else {
    		request.setAttribute("dateI","dateStart") ;
    		request.setAttribute("timeI","entranceTime") ;
    		request.setAttribute("dischInfo","поступившим ") ;
    	}
    	%>
    
    <msh:section>
    <msh:sectionTitle>Реестр за день ${param.dateBegin}. По ${dischInfo}  ${emerInfo}</msh:sectionTitle>
    <msh:sectionContent>
    <ecom:webQuery name="journal_priem" nativeSql="select m.id,m.dateStart,m.dateFinish,m.dischargeTime,pat.lastname ||' ' ||pat.firstname|| ' ' || pat.middlename,pat.birthday,sc.code,m.emergency,ml.name,m.entranceTime from MedCase as m  left join StatisticStub as sc on sc.medCase_id=m.id left outer join Patient pat on m.patient_id = pat.id left join MisLpu as ml on ml.id=m.department_id where m.DTYPE='HospitalMedCase' and m.${dateI} ='${param.dateBegin}' and  ${emerIs} order by m.${dateI},m.${timeI}" guid="4a720225-8d94-4b47-bef3-4dbbe79eec74" />
    <msh:table name="journal_priem" action="entityParentView-stac_ssl.do" idField="1" guid="b621e361-1e0b-4ebd-9f58-b7d919b45bd6">
      <msh:tableColumn columnName="#" property="sn" guid="34a9f56ab-a3fa-5c1afdf6c41d" />
      <msh:tableColumn columnName="Стат.карта" property="7" guid="34a9f56a-2b47-4feb-a3fa-5c1afdf6c41d" />
      <msh:tableColumn columnName="Фамилия имя отчество пациента" property="5" guid="34a9f56a-2b47-4feb-a3fa-5c1afdf6c41d" />
      <msh:tableColumn columnName="Год рождения" property="6" guid="34a9f56a-2b47-4feb-a3fa-5c1afdf6c41d" />
      <msh:tableColumn columnName="Дата поступления" property="2" guid="3cf775aa-e94d-4393-a489-b83b2be02d60" />
      <msh:tableColumn columnName="Время поступления" property="10" guid="3cf775aa-e94d-4393-a489-b83b2be02d60" />
      <msh:tableColumn columnName="Отделение" property="9" guid="e29229e1-d243-47d6-a5c7-997df74eaf73" />
      <msh:tableColumn columnName="Дата выписки" property="3" guid="d9642df9-5653-4920-bb78-1622cbeefa34" />
      <msh:tableColumn columnName="Время выписки" property="4" guid="d964-5653-4920-bb78-1622cbeefa34" />
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
				 eventName: "focus",
			 });
    function find() {
    	var frm = document.forms[0] ;
    	frm.target='' ;
    	frm.action='stac_reestrByHospital.do' ;
    }
    function print() {
    	var frm = document.forms[0] ;
    	frm.target='_blank' ;
    	frm.action='stac_print_reestrByHospital.do' ;
    }
			 
    </script>
  </tiles:put>
</tiles:insert>

