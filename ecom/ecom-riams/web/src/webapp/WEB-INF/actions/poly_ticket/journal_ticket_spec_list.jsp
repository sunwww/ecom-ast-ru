<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://struts.apache.org/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://www.nuzmsh.ru/tags/msh" prefix="msh" %>
<%@ taglib uri="http://www.ecom-ast.ru/tags/ecom" prefix="ecom" %>
<%@ taglib tagdir="/WEB-INF/tags" prefix="tags" %>

<%@page import="ru.ecom.poly.web.action.ticket.JournalBySpecialistForm"%>
<tiles:insert page="/WEB-INF/tiles/mainLayout.jsp" flush="true" >

    <tiles:put name='title' type='string'>
        <msh:title mainMenu="Medcard">Просмотр данных по пациентам</msh:title>
    </tiles:put>

    <tiles:put name='side' type='string'>
        <tags:ticket_finds currentAction="journalRegisterVisit"/>
    </tiles:put>
    
  <tiles:put name="body" type="string">
    <msh:form action="/poly_journalRegisterVisit_list.do" defaultField="beginDate" disableFormDataConfirm="true" method="GET">
    <input type="hidden" name="m" id="m" value="journalRegisterVisitByFrm"/>
    <input type="hidden" name="s" id="s" value="TicketService"/>
    <input type="hidden" name="id" id="id"/>
    <msh:panel>
      <msh:row>
        <msh:separator label="Параметры поиска" colSpan="7" />
      </msh:row>
        <msh:row>
        	<msh:textField property="numberInJournal" labelColSpan="3" size="25"/>
        </msh:row>
        <msh:row>
        	<msh:textField property="beginDate" label="Период с" />
        	<msh:textField property="finishDate" label="по" />
        </msh:row>
        <msh:row>
        	<msh:autoComplete property="workFunction" vocName="vocWorkFunction" 
        		horizontalFill="true" fieldColSpan="3"/>
        	`
        </msh:row>
        <msh:row>
        	<msh:autoComplete property="specialist" vocName="workFunction" 
        		horizontalFill="true" fieldColSpan="3"/>
        	`
        </msh:row>
        <msh:row>
        	<msh:autoComplete labelColSpan="3" property="primaryInYear" vocName="vocHospitalization" size="25"/>
        </msh:row>
        <msh:row>
        	<msh:autoComplete property="rayon" vocName="vocRayon" size="25"/>
        	
        </msh:row>
        <msh:row>
        
        <td colspan="4" class="buttons">
			<input type="button" value="Отменить" title="Отменить изменения [SHIFT+ESC]" onclick="this.disabled=true; window.history.back()" id="cancelButton">
			<input type="button" title="Найти [CTRL+ENTER]" onclick="this.value=&quot;Поиск...&quot;;  this.form.action=&quot;poly_journalRegisterVisit_list.do&quot;;this.form.target=&quot;&quot; ; this.form.submit(); return true ;" value="Найти" class="default" id="submitButton" autocomplete="off">
			<input type="button" title="Печать [CTRL+ENTER]" onclick="this.value=&quot;Печать&quot;; getId();this.form.action=&quot;print-journalRegistration.do&quot;;this.form.target=&quot;_blank&quot; ; this.form.submit(); return true ;" value="Печать" class="default" id="submitButton" autocomplete="off">
		</td>
        
        </msh:row>

    </msh:panel>
    </msh:form>
    
    <%
    	if (request.getParameter("beginDate")!=null && request.getParameter("finishDate")!=null) {
    	%>
    
    <msh:section>
    <msh:sectionTitle>Период с ${beginDate} по ${finishDate}</msh:sectionTitle>
    <msh:sectionContent>
        <msh:table deleteUrl="entityParentDeleteGoParentView-poly_ticket.do" editUrl="entityEdit-poly_ticket.do" viewUrl="entityShortView-poly_ticket.do" name="listRegisterVisit" action="entityView-poly_ticket.do" idField="id" noDataMessage="Не найдено">
            <msh:tableColumn columnName="#" property="sn"/>
            <msh:tableColumn columnName="№ талона" property="id"/>            
            <msh:tableColumn columnName="Дата приема" property="date"/>            
            <msh:tableColumn columnName="Специалист" property="spec"/>
            <msh:tableColumn columnName="ФИО" property="fio"/>
            <msh:tableColumn columnName="Пол" property="sex"/>
            <msh:tableColumn columnName="Дата рождения" property="birthday"/>
            <msh:tableColumn columnName="Адрес" property="address"/>
            <msh:tableColumn columnName="Район проживания" property="rayon"/>
            <msh:tableColumn columnName="Номер амб. карты" property="medcard"/>
            <msh:tableColumn columnName="Серия номер полиса" property="policy"/>
            <msh:tableColumn columnName="Диагноз. Код МКБ" property="mkb"/>
            <msh:tableColumn columnName="Код посещения" property="primary"/>
            <msh:tableColumn columnName="Напр. на стац.лечение" property="directHospital"/>
        </msh:table>
        <msh:table name="listCount" action="/" idField="directHospital">
        	<msh:tableColumn columnName="Напр. на стац.лечение" property="directHospital"/>
        	<msh:tableColumn columnName="Кол-во" property="count"/>
        </msh:table>
    </msh:sectionContent>

    </msh:section>
    <% } else {%>
    	<i>Выберите параметры поиска и нажмите "Найти" </i>
    	<% }   %>
  </tiles:put>
  <tiles:put name="javascript" type="string">
  	<script type="text/javascript">
  		function getId() {
  			var args=$('beginDate').value+":"+$('finishDate').value
  			+":"+$('specialist').value+":"+$('rayon').value+":"+$('primaryInYear').value 
  			+":" +$('numberInJournal').value +":";
  			
  			if ($('orderBySpecialist') && $('orderBySpecialist').checked) {
  				args=args+"t.workFunction_id" ;
  			}  else {
  				args=args+"t.date,t.recordtime" ;
  			}
  			args=args+":"+$('workFunction').value ;
  			$('id').value =args ; 
  		}
  	</script>
  </tiles:put>

</tiles:insert>

