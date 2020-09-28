<%@page import="ru.ecom.web.util.ActionUtil"%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://struts.apache.org/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://www.nuzmsh.ru/tags/msh" prefix="msh" %>
<%@ taglib uri="http://www.ecom-ast.ru/tags/ecom" prefix="ecom" %>
<%@ taglib tagdir="/WEB-INF/tags" prefix="tags" %>

<tiles:insert page="/WEB-INF/tiles/main${param.short}Layout.jsp" flush="true" >

    <tiles:put name='title' type='string'>
        <msh:title mainMenu="Medcard">Статистика по пользователям</msh:title>
    </tiles:put>

    <tiles:put name='side' type='string'>
        <tags:ticket_finds currentAction="ticketsByUser"/>
    </tiles:put>
    
  <tiles:put name="body" type="string">
   <%
	String dateChange =ActionUtil.updateParameter("FormStatByUserAction","dateChange","2", request) ;
	String usernameChange =ActionUtil.updateParameter("FormStatByUserAction","usernameChange","2", request) ;
	if (request.getParameter("short")==null) {
   %>
    <msh:form action="/smo_ticketsByUser.do" defaultField="dateBegin" 
    disableFormDataConfirm="true" method="GET">
    <msh:panel>
      <msh:row>
        <msh:separator label="Параметры поиска" colSpan="7"/>
      </msh:row>
      <msh:row>
        <td class="label" title="Дата (usernameChange)" colspan="1"><label for="usernameChangeName" id="usernameChangeLabel">Пользователь:</label></td>
        <td onclick="this.childNodes[1].checked='checked';">
        	<input type="radio" name="usernameChange" value="1">  создавший
        </td>
        <td onclick="this.childNodes[1].checked='checked';">
        	<input type="radio" name="usernameChange" value="2">  редактирующий
        </td>
      </msh:row>
      <msh:row>
        <td class="label" title="Дата (dateChange)" colspan="1"><label for="dateChangeName" id="dateChangeLabel">Дата:</label></td>
        <td onclick="this.childNodes[1].checked='checked';">
        	<input type="radio" name="dateChange" value="1">  создания
        </td>
        <td onclick="this.childNodes[1].checked='checked';">
        	<input type="radio" name="dateChange" value="2">  приема
        </td>
        <td onclick="this.childNodes[1].checked='checked';">
        	<input type="radio" name="dateChange" value="3">  редактирования
        </td>
     </msh:row>
     <msh:row>
        <msh:textField property="dateBegin" label="Период с"/>
        <msh:textField property="dateEnd" label="по"/>
        <td>
            <input type="submit" value="Найти" />
          </td>
        </msh:row>
    </msh:panel>
    </msh:form>
    
    <%
	}
    String date = (String)request.getParameter("dateBegin") ;
    String idPar = request.getParameter("id");
    if (date!=null && !date.equals(""))  {
    	String date1 = (String)request.getParameter("dateEnd") ;
    	if (date1==null || date1.equals("")) {
    		request.setAttribute("dateEnd", date) ;
    	} else {
    		request.setAttribute("dateEnd", date1) ;
    	}
    	
    	if (dateChange.equals("1")) {
    		request.setAttribute("dateSearch", "createDate") ;
    	} else if (dateChange.equals("2")) {
    		request.setAttribute("dateSearch", "dateStart") ;
    	} else {
    		request.setAttribute("dateSearch", "editDate") ;
    	}
    	if (usernameChange.equals("1")) {
    		request.setAttribute("usernameSearch", "username") ;
    	} else {
    		request.setAttribute("usernameSearch", "editUsername") ;
    	}
    	if (idPar==null) {    		    				
    	%>
    
    <msh:section>
    <msh:sectionTitle>Результаты поиска талонов ${info}. Период с ${param.dateBegin} по ${dateEnd}. ${infoSearch}</msh:sectionTitle>
    <msh:sectionContent>
    <ecom:webQuery name="journal_ticket" nativeSql="select  
    coalesce(case when ${usernameSearch}='' then null else ${usernameSearch} end,'-')||'&dateTicket='||to_CHAR(${dateSearch},'DD.MM.YYYY') as idPar
    ,${dateSearch} as dateSearch,${usernameSearch},count(*) as cnt 
    from MedCase 
    where dtype='ShortMedCase' and ${dateSearch}  between to_date('${param.dateBegin}','dd.mm.yyyy')  
    	and to_date('${dateEnd}','dd.mm.yyyy')
    	 ${add} group by ${dateSearch},${usernameSearch}" />
        <msh:table name="journal_ticket" action="smo_ticketsByUser.do?dateBegin=${param.dateBegin}" 
        viewUrl="smo_ticketsByUser.do?dateBegin=${param.dateBegin}&short=Short"
        idField="1" noDataMessage="Не найдено">
            <msh:tableColumn columnName="#" property="sn"/>
            <msh:tableColumn columnName="Дата" property="2"/>
            <msh:tableColumn columnName="Пользователь" property="3"/>
            <msh:tableColumn columnName="Кол-во" property="4" isCalcAmount="true"/>
        </msh:table>
    </msh:sectionContent>    
    
		<msh:sectionTitle>Итог по пользователям:</msh:sectionTitle>    
		<msh:sectionContent>
        <ecom:webQuery name="journal_ticket_sum" nativeSql="select  
        ${usernameSearch},count(*) from MedCase where dtype='ShortMedCase' and ${dateSearch}  
        between to_date('${param.dateBegin}','dd.mm.yyyy')  
        and to_date('${dateEnd}','dd.mm.yyyy')
         ${add} group by ${usernameSearch}"/>
        <msh:table name="journal_ticket_sum"  idField="1" action="javascript:void(0)" noDataMessage="Не найдено">
            <msh:tableColumn columnName="#" property="sn"/>
            <msh:tableColumn columnName="Пользователь" property="1"/>
            <msh:tableColumn columnName="Кол-во" property="2" />
        </msh:table>
    </msh:sectionContent>
    </msh:section>
    <% } else {
    	StringBuilder sqlAdd = new StringBuilder();
		if (idPar.equals("-")) {
			sqlAdd.append("(m.").append(request.getAttribute("usernameSearch")).append(" is null or m.").append(request.getAttribute("usernameSearch")).append("='')");
		} else {
			sqlAdd.append("m.").append(request.getAttribute("usernameSearch")).append("='").append(idPar).append("'");
		}
		sqlAdd.append(" and m.").append(request.getAttribute("dateSearch")).append("=to_date('").append(request.getParameter("dateTicket")).append("','dd.mm.yyyy')");
		request.setAttribute("add", sqlAdd.toString());
    
    %>
       <msh:section>
    <msh:sectionTitle>Результаты поиска талонов ${info}. Период с ${param.dateBegin} по ${dateEnd}. ${infoSearch}</msh:sectionTitle>
    <msh:sectionContent>
    <ecom:webQuery name="journal_ticket" nameFldSql="nameSldSQL" nativeSql="select  
        m.id, p.patientinfo
    	,coalesce(vwf.name||' '||wp.lastname||' '||wp.firstname||' '||wp.middlename
    	,vwf1.name||' '||wp1.lastname||' '||wp1.firstname||' '||wp1.middlename) as worker
    	from medCase m
    	left join patient p on p.id=m.patient_id
    	left join workfunction wf on wf.id=m.workFunctionExecute_id
    	left join vocworkfunction vwf on vwf.id=wf.workFunction_id
    	left join workfunction wf1 on wf1.id=m.workFunctionPlan_id
    	left join vocworkfunction vwf1 on vwf1.id=wf1.workFunction_id
    	left join WorkCalendarDay wcd on wcd.id=m.datePlan_id
    	left join worker w on w.id=wf.worker_id
    	left join patient wp on wp.id=w.person_id
    	left join worker w1 on w1.id=wf1.worker_id
    	left join patient wp1 on wp1.id=w1.person_id
    	where m.dtype='ShortMedCase'
    	and ${add} 
		" />
        <msh:table name="journal_ticket" action="entityView-smo_ticket.do" viewUrl="entityView-smo_ticket.do?short=Short" idField="1" noDataMessage="Не найдено">
            <msh:tableColumn columnName="#" property="sn"/>
            <msh:tableColumn columnName="Номер" property="1"/>
            <msh:tableColumn columnName="Пациент" property="2"/>
            <msh:tableColumn columnName="Врач" property="3"/>
		</msh:table>
    </msh:sectionContent>    
    
    </msh:section>
    <% }} else {%>
    	<i>Выберите параметры поиска и нажмите "Найти"</i>
    	<% }   %>
    <script type='text/javascript'>

    
    checkFieldUpdate('dateChange','${dateChange}',2) ;
    checkFieldUpdate('usernameChange','${usernameChange}',2) ;
    
    function checkFieldUpdate(aField,aValue,aDefault) {
    	
    	eval('var chk =  document.forms[0].'+aField) ;
    	var max = chk.length ;
    	if ((+aValue)>max) {
    		chk[+aDefault-1].checked='checked' ;
    	} else {
    		chk[+aValue-1].checked='checked' ;
    	}
    }
    
    function find() {
    }
    </script>
  </tiles:put>
</tiles:insert>

