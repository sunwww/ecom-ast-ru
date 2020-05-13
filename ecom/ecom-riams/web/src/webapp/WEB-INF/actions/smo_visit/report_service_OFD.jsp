<%@page import="ru.ecom.web.util.ActionUtil"%>
<%@page import="ru.nuzmsh.web.tags.helper.RolesHelper"%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://struts.apache.org/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://www.nuzmsh.ru/tags/msh" prefix="msh" %>
<%@ taglib uri="http://www.ecom-ast.ru/tags/ecom" prefix="ecom" %>
<%@ taglib tagdir="/WEB-INF/tags" prefix="tags" %>

<tiles:insert page="/WEB-INF/tiles/mainLayout.jsp" flush="true" >

    <tiles:put name='title' type='string'>
        <msh:title mainMenu="Poly">Просмотр данных по услугам </msh:title>
    </tiles:put>

    <tiles:put name='side' type='string'>
        <tags:visit_finds currentAction="report_service_OFD"/>
    </tiles:put>
    
  <tiles:put name="body" type="string">
<%
String typeGroup="";
%>
    <msh:form action="/visit_report_service_OFD.do" defaultField="beginDate" disableFormDataConfirm="true" method="GET">
    
    <msh:panel>
      <msh:row>
        <msh:separator label="Параметры поиска" colSpan="9" />
      </msh:row>
      <msh:row>
        	<msh:textField property="beginDate" label="Период с" />
        	<msh:textField property="finishDate" label="по" />
      </msh:row>
        <%-- <msh:row>
        	<msh:autoComplete property="serviceStream" vocName="vocServiceStream"
        		horizontalFill="true" fieldColSpan="5" size="50"/>
        </msh:row>
        <msh:row>
	        <td class="label" title="Группировка (typePatient)" colspan="1"><label for="typeGroupName" id="typeGroupLabel">Группировка:</label></td>
	        <td onclick="this.childNodes[1].checked='checked';">
	        	<input type="radio" name="typeGroup" value="1"> общий
	        </td>
	        <td onclick="this.childNodes[1].checked='checked';">
	        	<input type="radio" name="typeGroup" value="2"> поликлиника
	        </td>
	        <td onclick="this.childNodes[1].checked='checked';">
	        	<input type="radio" name="typeGroup" value="3"> стационар
	        </td>
	        
        </msh:row> --%>
        <msh:row>
	        <td></td>
	        <td></td>
	        <td></td>
	        <td colspan="2" align="right">
	        	<br><input type="button" title="Найти" onclick="this.value=&quot;Поиск...&quot;;  this.form.action=&quot;visit_report_service_OFD.do&quot;;this.form.target=&quot;&quot; ; this.form.submit(); return true ;" value="Найти" class="default" id="submitButton" autocomplete="off">
	        </td>
        </msh:row>
       <msh:row>
	       <td colspan="5" class="buttons">
			</td>
       </msh:row>

    </msh:panel>
    </msh:form>
    
<%
	if (request.getParameter("beginDate")!=null && request.getParameter("finishDate")!=null) {
		/* ActionUtil.setParameterFilterSql("serviceStream","smo.serviceStream_id", request) ;
    		if (typeGroup.equals("1")) {
    		} else if (typeGroup.equals("2")) {
    		} else if (typeGroup.equals("3")) {
    		} */
%>
    <msh:section>
		<ecom:webQuery name="journal_ticket" nameFldSql="journal_ticket_sql" nativeSql="
select owflpu.name lpuname, ms.name msname, 
count(case when (vss.code='CHARGED') then smc.id else null end) as cntCharged,
count(case when (vss.code!='CHARGED') then smc.id else null end) as cntOthers,
count(smc.id) as cntAll FROM MedCase smo
left join medcase smc on smc.parent_id=smo.id and smc.dtype='ServiceMedCase'
left join medservice ms on ms.id=smc.medservice_id
LEFT JOIN WorkFunction wf on wf.id=smo.workFunctionExecute_id 
LEFT JOIN Worker w on w.id=wf.worker_id 
LEFT JOIN VocServiceStream vss on vss.id=smo.serviceStream_id
LEFT JOIN WorkFunction owf on owf.id=smo.orderWorkFunction_id 
LEFT JOIN Worker ow on ow.id=owf.worker_id 
LEFT JOIN MisLpu owflpu on owflpu.id=ow.lpu_id 
WHERE (smo.dtype='ShortMedCase' or smo.dtype='Visit')  
and smo.dateStart BETWEEN TO_DATE('${beginDate}','dd.mm.yyyy') and TO_DATE('${finishDate}','dd.mm.yyyy') 
/* and smc.medservice_id is not null and (smo.noActuality is null or smo.noActuality='0') ${serviceStreamSql} */  
and smc.medservice_id is not null and (smo.noActuality is null or smo.noActuality='0')  
and w.lpu_id='273'
group by ms.name, owflpu.lpufunction_id, owflpu.name
order by owflpu.name, owflpu.lpufunction_id
" />
    <msh:sectionTitle>
    <form action="" method="post" target="_blank">
    Период с ${beginDate} по ${finishDate}. <%-- ${serviceStreamInfo} --%>
    </form>
    </msh:sectionTitle>
    <msh:sectionContent>
        <msh:table name="journal_ticket" action="" idField="1" noDataMessage="Не найдено">
         <msh:tableNotEmpty>
         </msh:tableNotEmpty>  
            <msh:tableColumn columnName="Отделения" property="1"/>            
            <msh:tableColumn columnName="Методики" property="2"/>
            <msh:tableColumn isCalcAmount="true" columnName="Платные" property="3"/>
            <msh:tableColumn isCalcAmount="true" columnName="Прочие" property="4"/>
            <msh:tableColumn isCalcAmount="true" columnName="Всего" property="5"/>
        </msh:table>
    </msh:sectionContent>
    </msh:section>    	

    <% } else {%>
    	<i>Выберите параметры поиска и нажмите "Найти" </i>
    	<% }   %>
  </tiles:put>
</tiles:insert>