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
        <msh:title mainMenu="Poly">Просмотр данных по хронической болезни почек</msh:title>
    </tiles:put>

    <tiles:put name='side' type='string'>
        <tags:visit_finds currentAction="journal_gemma"/>
    </tiles:put>
    
  <tiles:put name="body" type="string">
  <%
	//String typeDate =request.getParameter("typeDate") ;
 // String typeDate=ActionUtil.updateParameter("journal_gemma","typeDate","1", request) ;
  %>
    <msh:form action="/journal_kidney.do" defaultField="dateBegin" disableFormDataConfirm="true" method="GET" guid="d7b31bc2-38f0-42cc-8d6d-19395273168f">
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
    		
    	%>
    
    <msh:section>
${isReportBase}<ecom:webQuery isReportBase="${isReportBase}" maxResult="1500" name="journal_ticket" nameFldSql="journal_ticket_sql" nativeSql="
select mkb.code, mkb.name, count (distinct m.patient_id)
from medcase m
left join diagnosis d on m.id=d.medcase_id
left join vocidc10 mkb on mkb.id=d.idc10_id
where mkb.code like 'N18.%'
and m.datestart between to_date('${dateBegin}','dd.MM.yyyy') and to_date('${dateEnd}','dd.MM.yyyy')
and m.id=(select m2.id from medcase m2 left join diagnosis d2 on d2.medcase_id=m2.id 
left join vocidc10 mkb2 on mkb2.id=d2.idc10_id
where m2.patient_id = m.patient_id and mkb2.code like 'N18.%' 
and m2.datestart between to_date('${dateBegin}','dd.MM.yyyy') and to_date('${dateEnd}','dd.MM.yyyy')
order by m2.datestart desc limit 1 )
group by mkb.code, mkb.name
order by mkb.code

" guid="4a720225-8d94-4b47-bef3-4dbbe79eec74" /> 
    <msh:sectionContent>
        <msh:table
         name="journal_ticket" action="/javascript:void()" idField="1" noDataMessage="Не найдено">
            <msh:tableColumn columnName="МКБ" property="1"/>            
            <msh:tableColumn columnName="Диагноз" property="2"/>
            <msh:tableColumn columnName="Количество пациентов" property="3"/>
         </msh:table>
    </msh:sectionContent>

    </msh:section>
    <% }  else {%>
    	<i>Выберите параметры поиска и нажмите "Найти" </i>
    	<% }   %>
  </tiles:put>
  <tiles:put name="javascript" type="string">
  	<script type="text/javascript">

     
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