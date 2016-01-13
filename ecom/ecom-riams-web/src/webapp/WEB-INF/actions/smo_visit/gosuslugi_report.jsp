<%@page import="java.text.SimpleDateFormat"%>
<%@page import="ru.ecom.web.util.ActionUtil"%>
<%@page import="ru.nuzmsh.web.tags.helper.RolesHelper"%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://struts.apache.org/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://www.nuzmsh.ru/tags/msh" prefix="msh" %>
<%@ taglib uri="http://www.ecom-ast.ru/tags/ecom" prefix="ecom" %>
<%@ taglib tagdir="/WEB-INF/tags" prefix="tags" %>

<tiles:insert page="/WEB-INF/tiles/main${param.short}Layout.jsp" flush="true" >

    <tiles:put name='title' type='string'>
        <msh:title mainMenu="Poly">Отчет: количество поступивших обращение за получение услуги "Запись на прием к врачу" </msh:title>
    </tiles:put>

    <tiles:put name='side' type='string'>
        <tags:visit_finds currentAction="blood_report"/>
    </tiles:put>
    
  <tiles:put name="body" type="string">
  <%
	//String typeDate =request.getParameter("typeDate") ;
  String typeDate=ActionUtil.updateParameter("BloodReport","typeDate","1", request) ;
  String typeDiagnosis=ActionUtil.updateParameter("BloodReport","typeDiagnosis","1", request) ;
 
  %>
    <msh:form action="/gosuslugi_report.do" defaultField="dateBegin" disableFormDataConfirm="true" method="GET" guid="d7b31bc2-38f0-42cc-8d6d-19395273168f">
    <input type="hidden" name="id" id="id"/>
    <input type="hidden" name="ticketIs" id="ticketIs" value="0"/>
    <input type="hidden" name="typeReestr" id="typeReestr" value="2"/>
    <input type="hidden" name="person" id="person" value="${param.person}"/>
    <%if (request.getParameter("short")==null ||request.getParameter("short").equals(""))  {%>
    <msh:panel>
      <msh:row guid="53627d05-8914-48a0-b2ec-792eba5b07d9">
        <msh:separator label="Параметры поиска" colSpan="9" guid="15c6c628-8aab-4c82-b3d8-ac77b7b3f700" />
      </msh:row>
      <msh:row>
        	<msh:textField property="dateBegin" label="Период с" guid="8d7ef035-1273-4839-a4d8-1551c623caf1" />
        	<msh:textField property="dateEnd" label="по" guid="f54568f6-b5b8-4d48-a045-ba7b9f875245" />
        </msh:row>
          <msh:row>
	        <td class="label" title="Отображать" colspan="1">
	        <label for="typeDateName" id="typeDateLabel">Отобразить:</label></td>
	        <td onclick="this.childNodes[1].checked='checked';">
	        	<input type="radio" name="typeDate" value="1"> Дата создания пред. направления
	        </td>
	        <td onclick="this.childNodes[1].checked='checked';">
	        	<input type="radio" name="typeDate" value="2" >  Дата направления
	        </td>
        </msh:row>   
        <msh:row> 
        	<msh:submitCancelButtonsRow labelSave="Сформировать" doNotDisableButtons="cancel" labelSaving="Формирование..." colSpan="4"/>
        
        </msh:row>

    </msh:panel>
    <%} %>
    </msh:form>
    
    <%
    	String date = request.getParameter("dateBegin");
    	String dateEnd = request.getParameter("dateEnd");
    	if (date!=null  
    			&& !date.equals("") 
    			&& dateEnd!=null
    			&& !dateEnd.equals("")) {
    		request.setAttribute("dateBegin", date);
    		request.setAttribute("dateEnd", dateEnd);
    		request.setAttribute("isReportBase", ActionUtil.isReportBase(request.getParameter("dateBegin"),request.getParameter("dateEnd"),request));
    		
    		StringBuilder sqlAdd = new StringBuilder();
    		SimpleDateFormat format = new SimpleDateFormat("dd.MM.yyyy") ;		
    		if (typeDate.equals("1")) {
    			request.setAttribute("dateSql", "wct.createdateprerecord") ;
    		} else {
    			request.setAttribute("dateSql","wcd.calendardate" ) ;
    		}
    		    		
    		request.setAttribute ("appendSQL", sqlAdd.toString());
    	%>
    
    <msh:section>
${isReportBase}<ecom:webQuery isReportBase="${isReportBase}" maxResult="1500" name="journal_ticket" nameFldSql="journal_ticket_sql" nativeSql="
select 
count (wct.id) as cntAll
, count (case when su.isremoteuser='1' then 1 else null end) as cntRemote
from workcalendartime wct
left join workcalendarday wcd on wcd.id=wct.workcalendarday_id
left join secuser su on su.login=wct.createprerecord
where ${dateSql} between to_date('${dateBegin}','dd.MM.yyyy') and to_date('${dateEnd}','dd.MM.yyyy')
 and su.id is not null
" guid="4a720225-8d94-4b47-bef3-4dbbe79eec74" /> 
    <msh:sectionTitle>
       
    </msh:sectionTitle>

    <msh:sectionContent>
        <msh:table
         name="journal_ticket" action="/javascript:void()" idField="1" noDataMessage="Не найдено">
            <msh:tableColumn columnName="Записано всего" property="1"/>            
            <msh:tableColumn columnName="Записано удаленными пользователями" property="2"/>
            </msh:table>
    </msh:sectionContent>

    </msh:section>
    <% }  else {%>
    	<i>Выберите параметры поиска и нажмите "Найти" </i>
    	<% }   %>
  </tiles:put>
  <tiles:put name="javascript" type="string">
  	<script type="text/javascript">
  	checkFieldUpdate('typeDate','${typeDate}',1) ;
    
    function checkFieldUpdate(aField,aValue,aDefault) {
    	
    	eval('var chk =  document.forms[0].'+aField) ;
    	var max = chk.length ;
    	if ((+aValue)>max) {
    		chk[+aDefault-1].checked='checked' ;
    	} else {
    		chk[+aValue-1].checked='checked' ;
    	}
    }
  		
  	</script>
  </tiles:put>

</tiles:insert>