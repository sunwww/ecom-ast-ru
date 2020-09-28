<%@page import="ru.ecom.web.util.ActionUtil"%>
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
	String typePatient =ActionUtil.updateParameter("Form039Action","typePatient","4", request) ;
	String typeDtype =ActionUtil.updateParameter("Form039Action","typeDtype","3", request) ;
  %>
    <msh:form action="/smo_journal_nonredidentPatient.do" defaultField="department" disableFormDataConfirm="true" method="GET">
    <msh:panel>
      <msh:row>
        <msh:separator label="Параметры поиска" colSpan="7"/>
      </msh:row>
      <msh:row>
        <td class="label" title="Поиск по пациентам (typePatient)" colspan="1"><label for="typePatientName" id="typePatientLabel">Пациенты:</label></td>
        <td onclick="this.childNodes[1].checked='checked';">
        	<input type="radio" name="typePatient" value="1">  региональные
        </td>
        <td onclick="this.childNodes[1].checked='checked';" colspan="2">
        	<input type="radio" name="typePatient" value="2">  иногородные
        </td>
        <td onclick="this.childNodes[1].checked='checked';">
        	<input type="radio" name="typePatient" value="3">  иностранцы
        </td>
        <td onclick="this.childNodes[1].checked='checked';">
        	<input type="radio" name="typePatient" value="4">  все
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
        <msh:textField property="dateBegin" label="Период с" />
        <msh:textField property="dateEnd" label="по" />
           <td>
            <input type="submit" value="Найти" />
          </td>
           <td>
            <input type="submit" onclick="print()" value="Печать" />
          </td>
    </msh:row>
    </msh:panel>
    </msh:form>
    
    <%
    String date = (String)request.getParameter("dateBegin") ;
    String dateSearch = (String)request.getParameter("dateSearch") ;
	if (typeDtype.equals("1")) {
		request.setAttribute("dtypeSql", "t.dtype='Visit'") ;
	} else if (typeDtype.equals("2")) {
		request.setAttribute("dtypeSql", "t.dtype='ShortMedCase'") ;
	} else {
		request.setAttribute("dtypeSql", "(t.dtype='ShortMedCase' or t.dtype='Visit')") ;
	}
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
    '&dateSearch='||to_CHAR(t.dateStart,'DD.MM.YYYY')||
    '&typePatient=${param.typePatient}&typeDtype=${param.typeDtype}' as idPar
    ,t.dateStart as tdate,count(*) as cnt
    ,count(case when t.istalk='1' then 1 else null end) as cntTalk
    from MedCase t 
    left join medcard as m on m.id=t.medcard_id 
    left join Patient p on p.id=m.person_id
    left join VocSocialStatus pvss on pvss.id=p.socialStatus_id
    left join Omc_Oksm ok on p.nationality_id=ok.id
    where t.dateStart  between to_date('${param.dateBegin}','dd.mm.yyyy')
      and to_date('${dateEnd}','dd.mm.yyyy')
     and ${dtypeSql} ${add} 
    group by t.dateStart" />
        <msh:table name="journal_ticket" action="smo_journal_nonredidentPatient.do" idField="1" noDataMessage="Не найдено">
            <msh:tableColumn columnName="#" property="sn"/>
            <msh:tableColumn columnName="Дата" property="2"/>
            <msh:tableColumn isCalcAmount="true" columnName="Кол-во" property="3"/>
            <msh:tableColumn isCalcAmount="true" columnName="Кол-во беседа с род." property="4" cssClass="cssPsychiatry"/>
        </msh:table>
    </msh:sectionContent>
    <msh:sectionTitle>Итог</msh:sectionTitle>
    <msh:sectionContent>
    <ecom:webQuery name="journal_ticket_sum"  nativeSql="select 
    count(*) as cnt
    ,count(case when t.istalk='1' then 1 else null end) as tlk 
    ,vss.name as vssname
    from MedCase t 
    left join medcard as m on m.id=t.medcard_id 
    left join Patient p on p.id=m.person_id
    left join VocSocialStatus pvss on pvss.id=p.socialStatus_id
    left join VocServiceStream vss on vss.id=t.serviceStream_id
    where t.dateStart between to_date('${param.dateBegin}','dd.mm.yyyy') 
     and to_date('${dateEnd}','dd.mm.yyyy') 
     and ${dtypeSql} ${add}
    group by t.serviceStream_id,vss.name
   
    "/>
        <msh:table name="journal_ticket_sum" action="javascript:void(0)" idField="1" noDataMessage="Не найдено">
            <msh:tableColumn columnName="#" property="sn"/>
            <msh:tableColumn columnName="Поток обслуживания" property="3"/>
            <msh:tableColumn columnName="Кол-во" isCalcAmount="true" property="1"/>
            <msh:tableColumn columnName="Кол-во бесед с род." isCalcAmount="true" property="2"  cssClass="cssPsychiatry"/>
        </msh:table>
    </msh:sectionContent>
    </msh:section>
    <% } else if (dateSearch!=null && !dateSearch.equals(""))  {
    	request.setAttribute("dateSearch", dateSearch) ;
    	%>
    <ecom:webQuery name="journal_priem" nativeSql=" select p.id as pid,m.number as mnumber
    , p.lastname||' '|| p.firstname||' '||p.middlename as fiopat,p.birthday
    ,  t.id as tid,t.dateStart as tdate
    ,vwf.name||' '||wp.lastname||' '|| wp.firstname||' '||wp.middlename as wfinfo
    ,1 as mkbcode ,vr.name as vrname, case when t.istalk='1' then 'Да' else '' end
    from MedCase t left join medcard m on m.id=t.medcard_id     
    left join Patient p on p.id=m.person_id     
    left join VocSocialStatus pvss on pvss.id=p.socialStatus_id
    left join workfunction wf on wf.id=t.workFunctionExecute_id    
    left join vocworkfunction vwf on vwf.id=wf.workFunction_id    
    left join worker  w on w.id=wf.worker_id    
    left join patient wp on wp.id=w.person_id        
    left join vocreason vr on vr.id=t.visitreason_id  
    left join Omc_Oksm ok on p.nationality_id=ok.id  
    where t.dateStart  =  to_date('${dateSearch}','dd.mm.yyyy')
    and ${dtypeSql} ${add}
    order by p.lastname,p.firstname,p.middlename" />
    
 	    <msh:table viewUrl="entityParentView-smo_ticket.do?short=Short" editUrl="entityParentEdit-smo_ticket.do" 
	    deleteUrl="entityParentDeleteGoParentView-smo_ticket.do" 
	    name="journal_priem" action="entityParentView-smo_ticket.do" idField="1">
	      <msh:tableColumn columnName="#" property="sn" />
	      <msh:tableColumn columnName="№мед.карты" property="2" />
	      <msh:tableColumn columnName="ФИО пациента" property="3" />
	      <msh:tableColumn columnName="Год рождения" property="4" />
	      <msh:tableColumn columnName="№талона" property="5" />
	      <msh:tableColumn columnName="Дата приема" property="6" />
	      <msh:tableColumn columnName="Специалист" property="7" />
	      <msh:tableColumn columnName="Диагноз" property="8" />
	      <msh:tableColumn columnName="Цель посещения" property="9" />
	      <msh:tableColumn columnName="Беседа с родст." property="10" cssClass="cssPsychiatry" />
	    </msh:table>
     	<%
    }else {%>
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
    	frm.action='smo_journal_nonredidentPatient.do' ;
    }
    </script>
  </tiles:put>
</tiles:insert>

