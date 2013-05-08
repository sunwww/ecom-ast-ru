<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://struts.apache.org/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://www.nuzmsh.ru/tags/msh" prefix="msh" %>
<%@ taglib uri="http://www.ecom-ast.ru/tags/ecom" prefix="ecom" %>
<%@ taglib tagdir="/WEB-INF/tags" prefix="tags" %>

<tiles:insert page="/WEB-INF/tiles/main${param.short}Layout.jsp" flush="true" >

    <tiles:put name='title' type='string'>
        <msh:title mainMenu="Medcard">Просмотр дублей по специалистам</msh:title>
    </tiles:put>

    <tiles:put name='side' type='string'>
        <tags:ticket_finds currentAction="ticketdouble"/>
    </tiles:put>
    
  <tiles:put name="body" type="string">

    
    <%
    String reestr = request.getParameter("reestr") ;
    String date = request.getParameter("dateBegin") ;
    if (reestr!=null && reestr.equals("1")) {
    	%>
    <ecom:webQuery name="list" nativeSql=" select t.id as tid,m.number as mnumber
    , p.lastname||' '|| p.firstname||' '||p.middlename ||' г.р.'||to_char(p.birthday,'DD.MM.YYYY'),t.createDate,t.dateStart as tdate
    ,vwf.name||' '||wp.lastname||' '|| wp.firstname||' '||wp.middlename as wfinfo
    ,vr.name as vrname  from MedCase t 
    left join medcard m on m.id=t.medcard_id     
    left join patient p on p.id=m.person_id     
    left join workfunction wf on wf.id=t.workFunctionExecute_id    
    left join vocworkfunction vwf on vwf.id=wf.workFunction_id    
    left join worker  w on w.id=wf.worker_id    
    left join patient wp on wp.id=w.person_id       
    left join vocreason vr on vr.id=t.visitreason_id    
    where t.dateStart=to_date('${param.dateStart}','dd.mm.yyyy') 
    and t.workfunctionExecute_id='${param.workFunction}' 
    and t.medcard_id='${param.medcard}' order by t.id" guid="4a720225-8d94-4b47-bef3-4dbbe79eec74" />
    <msh:table name="list" action="entityParentView-smo_ticket.do" idField="1" noDataMessage="Не найдено" guid="6600cebc-4548-4f57-a048-5a3a2e67a673">
      <msh:tableColumn columnName="#" property="sn" guid="612d85fd-ca3a-46a4-9598-a611b83a01ab" />
      <msh:tableColumn columnName="№талона" property="1" guid="612d85fd-ca3a-46a4-9598-a611b83a01ab" />
      <msh:tableColumn columnName="№мед.карты" property="2" guid="612d85fd-ca3a-46a4-9598-a611b83a01ab" />
      <msh:tableColumn columnName="Пациент" property="3" guid="d7955208-4c68-42ce-85d6-684a4b9076a9" />
      <msh:tableColumn columnName="Дата создания" property="4" guid="ee9ce01d-4924-4e76-bc93-3ecb73d8b18f" />
      <msh:tableColumn columnName="Дата приема" property="5" guid="ee9ce01d-4924-4e76-bc93-3ecb73d8b18f" />
      <msh:tableColumn columnName="Специалист" property="6" guid="9465992e-5fe3-42ee-b125-63929fda5158" />
      <msh:tableColumn columnName="Цель посещения" property="7" guid="9465992e-5fe3-42ee-b125-63929fda5158" />
    </msh:table>
    	
    	<%
    } else {
    	
    	%>
    <msh:form action="/smo_doubleTickets.do" defaultField="dateBegin" disableFormDataConfirm="true" method="GET" guid="d7b31bc2-38f0-42cc-8d6d-19395273168f">
    <msh:panel guid="6ae283c8-7035-450a-8eb4-6f0f7da8a8ff">
      <msh:row guid="53627d05-8914-48a0-b2ec-792eba5b07d9">
        <msh:separator label="Параметры поиска" colSpan="7" guid="15c6c628-8aab-4c82-b3d8-ac77b7b3f700" />
      </msh:row>
        <msh:row>
        <msh:textField property="dateBegin" label="Период с" guid="8d7ef035-1273-4839-a4d8-1551c623caf1" />
        <msh:textField property="dateEnd" label="по" guid="f54568f6-b5b8-4d48-a045-ba7b9f875245" />
           <td>
            <input type="submit" onclick="find()" value="Найти" />
          </td>

      </msh:row>
    </msh:panel>
    </msh:form>    	<%
    	if (date!=null && !date.equals(""))  {
    	%>
    
    <msh:section>
    <msh:sectionContent>
    <ecom:webQuery name="journal_ticket" nativeSql="select '&reestr=1&medcard='||t.medcard_id||'&dateStart='||to_char(t.dateStart,'dd.mm.yyyy')||'&workFunction='||t.workFunctionExecute_id as idcolum, t.medcard_id as person,t.dateStart as tdateStart
			,count(*) as cnt
			,vwf.name||' '|| p.lastname||' '||p.firstname||' '||p.middlename as pfio,pm.lastname||' '||pm.firstname||' '||pm.middlename as pmfio, m.number 
    				from MedCase t 
    				
    				left join WorkFunction as wf on wf.id=t.workFunctionExecute_id 
    				left join Worker as w on w.id=wf.worker_id 
    				left join Patient as p on p.id=w.person_id 
    				left join Medcard as m on m.id=t.medcard_id
    				left join Patient as pm on pm.id=t.patient_id 
    				left join VocWorkFunction vwf on vwf.id=wf.workFunction_id 
    				where t.dtype='ShortMedCase' and t.dateStart  between '${param.dateBegin}'  
    				 and '${param.dateEnd}' 
     				group by t.patient_id,t.dateStart,t.workFunctionExecute_id
						,t.medcard_id,vwf.name,p.lastname,p.firstname,p.middlename
						,pm.lastname,pm.firstname
						,pm.middlename, m.number
						
				having count(t.id)>1						"/>
        <msh:table name="journal_ticket" action="smo_doubleTickets.do" idField="1" noDataMessage="Не найдено"
        viewUrl="smo_doubleTickets.do?short=Short">
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
    	<% }  } %>
    
    <script type='text/javascript' src='/skin/ext/jscalendar/calendar.js'></script> 
    <script type='text/javascript' src='/skin/ext/jscalendar/calendar-setup.js'></script> 
    <script type='text/javascript' src='/skin/ext/jscalendar/calendar-ru.js'></script> 
    <style type="text/css">@import url(/skin/ext/jscalendar/css/calendar-blue.css);</style>
    <script type='text/javascript'>
   // var typePatient = document.forms[0].typePatient ;

    function find() {
    	var frm = document.forms[0] ;
    	frm.target='' ;
    	frm.action='smo_doubleTickets.do' ;
    }
    </script>
  </tiles:put>
</tiles:insert>