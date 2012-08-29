<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://struts.apache.org/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://www.nuzmsh.ru/tags/msh" prefix="msh" %>
<%@ taglib uri="http://www.ecom-ast.ru/tags/ecom" prefix="ecom" %>
<%@ taglib tagdir="/WEB-INF/tags" prefix="tags" %>

<%@page import="ru.ecom.poly.web.action.ticket.JournalBySpecialistForm"%>
<tiles:insert page="/WEB-INF/tiles/mainLayout.jsp" flush="true" >

    <tiles:put name='title' type='string'>
        <msh:title mainMenu="Poly" title="Учет посещений" property="worker">Просмотр данных по пациентам</msh:title>
    </tiles:put>

    <tiles:put name='side' type='string'>
        <tags:visit_finds currentAction="journalRegisterVisit"/>
    </tiles:put>
    
  <tiles:put name="body" type="string">
    <msh:form action="/smo_journalRegisterVisit_list.do" defaultField="beginDate" disableFormDataConfirm="true" method="GET" guid="d7b31bc2-38f0-42cc-8d6d-19395273168f">
    <msh:panel guid="6ae283c8-7035-450a-8eb4-6f0f7da8a8ff">
    <input type="hidden" name="m" id="m" value="journalRegisterVisitByFrm"/>
    <input type="hidden" name="s" id="s" value="SmoVisitService"/>
    <input type="hidden" name="id" id="id"/>
      <msh:row guid="53627d05-8914-48a0-b2ec-792eba5b07d9">
        <msh:separator label="Параметры поиска" colSpan="7" guid="15c6c628-8aab-4c82-b3d8-ac77b7b3f700" />
      </msh:row>
        <msh:row>
        	<msh:textField property="numberInJournal" labelColSpan="3" size="25"/>
        </msh:row>
        <msh:row>
        	<msh:textField property="beginDate" label="Период с" guid="8d7ef035-1273-4839-a4d8-1551c623caf1" />
        	<msh:textField property="finishDate" label="по" guid="f54568f6-b5b8-4d48-a045-ba7b9f875245" />
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
        <td colspan="4" class="buttons">
			<input type="button" value="Отменить" title="Отменить изменения [SHIFT+ESC]" onclick="this.disabled=true; window.history.back()" id="cancelButton">
			<input type="button" title="Найти [CTRL+ENTER]" onclick="this.value=&quot;Поиск...&quot;;  this.form.action=&quot;smo_journalRegisterVisit_list.do&quot;;this.form.target=&quot;&quot; ; this.form.submit(); return true ;" value="Найти" class="default" id="submitButton" autocomplete="off">
			<input type="button" title="Печать [CTRL+ENTER]" onclick="this.value=&quot;Печать&quot;; getId();this.form.action=&quot;print-journalRegistration.do&quot;;this.form.target=&quot;_blank&quot; ; this.form.submit(); return true ;" value="Печать" class="default" id="submitButton" autocomplete="off">
		</td>

    </msh:panel>
    </msh:form>
    
    <%
    if (request.getParameter("beginDate")!=null && request.getParameter("finishDate")!=null) {
    	%>
    
    <msh:section>
    <msh:sectionTitle>Период с ${beginDate} по ${finishDate}</msh:sectionTitle>
    <msh:sectionContent>
        <msh:table viewUrl="entitySubclassShortView-mis_medCase.do" name="listRegisterVisit" action="entitySubclassView-mis_medCase.do" idField="id" noDataMessage="Не найдено">
            <msh:tableColumn columnName="#" property="sn"/>
            <msh:tableColumn columnName="№визита" property="id"/>            
            <msh:tableColumn columnName="Дата приема" property="date"/>            
            <msh:tableColumn columnName="Специалист" property="spec"/>
            <msh:tableColumn columnName="ФИО" property="fio"/>
            <msh:tableColumn columnName="Пол" property="sex"/>
            <msh:tableColumn columnName="Дата рождения" property="birthday"/>
            <msh:tableColumn columnName="Адрес" property="address"/>
            <msh:tableColumn columnName="Район проживания" property="rayon"/>
            <msh:tableColumn columnName="Серия номер полиса" property="policy"/>
            <msh:tableColumn columnName="Диагноз. Код МКБ" property="mkb"/>
            <msh:tableColumn columnName="Поток обслуживания" property="serviceStream"/>
            <msh:tableColumn columnName="Услуги" property="usernameCreate"/>
        </msh:table>
    </msh:sectionContent>

    </msh:section>
    <%} %>
  </tiles:put>
  <tiles:put name="javascript" type="string">
  	<script type="text/javascript">
  		function getId() {
  			var args=$('beginDate').value+":"+$('finishDate').value
  			+":"+$('specialist').value+":"+$('rayon').value+":"+$('primaryInYear').value 
  			+":" +$('numberInJournal').value +":";
  			
  			if ($('orderBySpecialist') && $('orderBySpecialist').checked) {
  				args=args+"workFunction_id" ;
  			}  else {
  				args=args+"dateStart,timeExecute" ;
  			}
  			args=args+":"+$('workFunction').value ;
  			$('id').value =args ; 
  		}
  	</script>
  </tiles:put>

</tiles:insert>

