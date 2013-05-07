<%@page import="ru.ecom.mis.web.action.util.ActionUtil"%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://struts.apache.org/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://www.nuzmsh.ru/tags/msh" prefix="msh" %>
<%@ taglib uri="http://www.ecom-ast.ru/tags/ecom" prefix="ecom" %>
<%@ taglib tagdir="/WEB-INF/tags" prefix="tags" %>

<tiles:insert page="/WEB-INF/tiles/mainLayout.jsp" flush="true" >

    <tiles:put name='title' type='string'>
        <msh:title mainMenu="Medcard">Просмотр данных по пациентам</msh:title>
    </tiles:put>

    <tiles:put name='side' type='string'>
        <tags:ticket_finds currentAction="ticketsByResident"/>
    </tiles:put>
    
  <tiles:put name="body" type="string">
  <%
	String typePatient =ActionUtil.updateParameter("Form039Action","typePatient","1", request) ;
	String typeDtype =ActionUtil.updateParameter("Form039Action","typeDtype","3", request) ;
  %>
    <msh:form action="/smo_journal_nonredidentPatient.do" defaultField="department" disableFormDataConfirm="true" method="GET" guid="d7b31bc2-38f0-42cc-8d6d-19395273168f">
    <msh:panel>
      <msh:row>
        <msh:separator label="Параметры поиска" colSpan="7"/>
      </msh:row>
      <msh:row guid="7d80be13-710c-46b8-8503-ce0413686b69">
        <td class="label" title="Поиск по пациентам (typePatient)" colspan="1"><label for="typePatientName" id="typePatientLabel">Пациенты:</label></td>
        <td onclick="this.childNodes[1].checked='checked';">
        	<input type="radio" name="typePatient" value="1">  региональные
        </td>
        <td onclick="this.childNodes[1].checked='checked';" colspan="2">
        	<input type="radio" name="typePatient" value="2">  иногородные
        </td>
        <td onclick="this.childNodes[1].checked='checked';">
        	<input type="radio" name="typePatient" value="3">  все
        </td>
     </msh:row>
        <msh:row>
	        <td class="label" title="База (typeDtype)" colspan="1"><label for="typeDtypeName" id="typeDtypeLabel">Отобразить:</label></td>
	        <td onclick="this.childNodes[1].checked='checked';">
	        	<input type="radio" name="typeDtype" value="1">  Визит.
	        </td>
	        <td onclick="this.childNodes[1].checked='checked';" colspan="2">
	        	<input type="radio" name="typeDtype" value="2" >  Талон.
	        </td>
	        <td onclick="this.childNodes[1].checked='checked';">
	        	<input type="radio" name="typeDtype" value="3">  Все
	        </td>
        </msh:row>
     <msh:row>
        <msh:textField property="dateBegin" label="Период с" guid="8d7ef035-1273-4839-a4d8-1551c623caf1" />
        <msh:textField property="dateEnd" label="по" guid="f54568f6-b5b8-4d48-a045-ba7b9f875245" />
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
    String date = (String)request.getParameter("dateBegin") ;
    if (date!=null && !date.equals(""))  {
    	String date1 = (String)request.getParameter("dateEnd") ;
    	if (date1==null || date1.equals("")) {
    		request.setAttribute("dateEnd", date) ;
    	} else {
    		request.setAttribute("dateEnd", date1) ;
    	}
    	// /Policy/Mis/MisLpu/Psychiatry
    	%>
    
    <msh:section>
    <msh:sectionTitle>Результаты поиска талонов ${infoTypePat}. Период с ${param.dateBegin} по ${dateEnd}. ${infoSearch}</msh:sectionTitle>
    <msh:sectionContent>
    <ecom:webQuery name="journal_ticket" nativeSql="select  
    to_CHAR(t.dateStart,'DD.MM.YYYY')||':${param.typePatient}' as idPar
    ,t.dateStart as tdate,count(*) as cnt
    ,count(case when t.talk='1' then 1 else null end) as cntTalk
    from MedCase t 
    left join medcard as m on m.id=t.medcard_id 
    left join Patient p on p.id=m.person_id
    left join VocSocialStatus pvss on pvss.id=p.socialStatus_id
    left join Omc_Oksm ok on p.nationality_id=ok.id
    where t.dateStart  between to_date('${param.dateBegin}','dd.mm.yyyy')
      and to_date('${dateEnd}','dd.mm.yyyy')
      and t.dtype='ShortMedCase' ${add} 
    group by t.dateStart" guid="4a720225-8d94-4b47-bef3-4dbbe79eec74" />
        <msh:table name="journal_ticket" action="smo_ticketsByNonredidentPatientData.do" idField="1" noDataMessage="Не найдено">
            <msh:tableColumn columnName="#" property="sn"/>
            <msh:tableColumn columnName="Дата" property="2"/>
            <msh:tableColumn columnName="Кол-во" property="3"/>
            <msh:tableColumn columnName="Кол-во беседа с род." property="4" cssClass="cssPsychiatry"/>
        </msh:table>
    </msh:sectionContent>
    <msh:sectionTitle>Итог</msh:sectionTitle>
    <msh:sectionContent>
    <ecom:webQuery name="journal_ticket_sum" maxResult="1" nativeSql="select 
    count(*) as cnt
    ,count(case when t.talk='1' then 1 else null end) as tlk 
    ,vss.name as vssname
    from MedCase t 
    left join medcard as m on m.id=t.medcard_id 
    left join Patient p on p.id=m.person_id
    left join VocSocialStatus pvss on pvss.id=p.socialStatus_id
    left join VocServiceStream vss on vss.id=t.serviceStream_id
    where t.dateStart between to_date('${param.dateBegin}','dd.mm.yyyy') 
     and to_date('${dateEnd}','dd.mm.yyyy') 
     ${add}
    group by t.serviceStream_id,vss.name
    ,p.lastname,p.middlename,p.firstname
    "/>
        <msh:table name="journal_ticket_sum" action="" idField="1" noDataMessage="Не найдено">
            <msh:tableColumn columnName="#" property="sn"/>
            <msh:tableColumn columnName="Поток обслуживания" property="3"/>
            <msh:tableColumn columnName="Кол-во" property="1"/>
            <msh:tableColumn columnName="Кол-во бесед с род." property="2"  cssClass="cssPsychiatry"/>
        </msh:table>
    </msh:sectionContent>
    </msh:section>
    <% } else {%>
    	<i>Выберите параметры поиска и нажмите "Найти" </i>
    	<% }   %>
    <script type='text/javascript'>
    checkFieldUpdate('typePatient','${typePatient}',4) ;
    checkFieldUpdate('typeDtype','${typeDtype}',3) ;
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
    	frm.action='smo_journal_nonredidentPatient.do' ;
    }
    function print() {
    	var frm = document.forms[0] ;
    	frm.target='_blank' ;
    	frm.action='smo_ticketsByNonresidentPatientPrint.do' ;
    }
    </script>
  </tiles:put>
</tiles:insert>

