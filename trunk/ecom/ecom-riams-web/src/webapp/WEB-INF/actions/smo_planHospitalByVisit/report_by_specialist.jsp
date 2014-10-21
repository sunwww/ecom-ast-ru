<%@page import="ru.ecom.web.login.LoginInfo"%>
<%@page import="ru.nuzmsh.web.tags.helper.RolesHelper"%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://struts.apache.org/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://www.nuzmsh.ru/tags/msh" prefix="msh" %>
<%@ taglib uri="http://www.ecom-ast.ru/tags/ecom" prefix="ecom" %>
<%@ taglib tagdir="/WEB-INF/tags" prefix="tags" %>

<%@page import="ru.ecom.web.util.ActionUtil"%>
<%@page import="ru.ecom.poly.web.action.ticket.JournalBySpecialistForm"%>
<tiles:insert page="/WEB-INF/tiles/mainLayout.jsp" flush="true" >

    <tiles:put name='title' type='string'>
        <msh:title mainMenu="Poly">Просмотр данных по направленным пациентам</msh:title>
    </tiles:put>

    <tiles:put name='side' type='string'>
        <tags:visit_finds currentAction="reportPlanHospital"/>
    </tiles:put>
    
  <tiles:put name="body" type="string">
  <%
    ActionUtil.updateParameter("ReportPlanHospitalByVisit","typeView","3", request) ;
    ActionUtil.updateParameter("ReportPlanHospitalByVisit","typeDate","2", request) ;
    boolean isZav = RolesHelper.checkRoles("/Policy/Mis/MedCase/Visit/ViewAll", request) ;
    String infoSql = "" ;
    if (isZav) {
    	infoSql = "wchb.visit_id is not null" ;
    } else {
    	String login = LoginInfo.find(request.getSession(true)).getUsername() ;
    	infoSql = "wchb.createUsername='"+login+"'" ;
    }
    request.setAttribute("infoSql", infoSql) ;
  %>
    <msh:form action="/smo_report_plan_hospital_by_visit.do" defaultField="beginDate" disableFormDataConfirm="true" method="GET" guid="d7b31bc2-38f0-42cc-8d6d-19395273168f">
    <input type="hidden" name="m" id="m" value="f039"/>
    <input type="hidden" name="s" id="s" value="VisitPrintService"/>
    <input type="hidden" name="id" id="id"/>
    <msh:panel colsWidth="1%,1%,1%">
      <msh:row guid="53627d05-8914-48a0-b2ec-792eba5b07d9">
        <msh:separator label="Параметры поиска" colSpan="7" guid="15c6c628-8aab-4c82-b3d8-ac77b7b3f700" />
      </msh:row>
      <msh:row>
        	<msh:textField property="beginDate" label="Период с" guid="8d7ef035-1273-4839-a4d8-1551c623caf1" />
        	<msh:textField property="finishDate" fieldColSpan="3" label="по" guid="f54568f6-b5b8-4d48-a045-ba7b9f875245" />
        </msh:row>
        <tr><td colspan="12"><table>    

        <msh:row>
	        <td class="label" title="Просмотр (typeView)" colspan="1"><label for="typeViewName" id="typeViewLabel">Госпитализация:</label></td>
	        <td onclick="this.childNodes[1].checked='checked';" colspan="2">
	        	<input type="radio" name="typeView" value="1"> оформлена
	        </td>
	        <td colspan="2" onclick="this.childNodes[1].checked='checked';">
	        	<input type="radio" name="typeView" value="2"> неоформлена
	        </td>
	        <td colspan="2" onclick="this.childNodes[1].checked='checked';">
	        	<input type="radio" name="typeView" value="3"> все
	        </td>
        </msh:row>
        <msh:row>
	        <td class="label" title="Дата поиска (typeDate)" colspan="1"><label for="typeDateName" id="typeDateLabel">Дата:</label></td>
	        <td onclick="this.childNodes[1].checked='checked';" colspan="2">
	        	<input type="radio" name="typeDate" value="1"> пред.госпитализации
	        </td>
	        <td colspan="2" onclick="this.childNodes[1].checked='checked';">
	        	<input type="radio" name="typeDate" value="2"> направления
	        </td>
	        <td colspan="2" onclick="this.childNodes[1].checked='checked';">
	        	<input type="radio" name="typeDate" value="3"> госпитализации
	        </td>
        </msh:row>

        </table></td></tr>
        <msh:row>
        <td colspan="3" class="buttons">
			<input type="button" title="Найти" onclick="this.value=&quot;Поиск...&quot;;  this.form.action=&quot;smo_report_plan_hospital_by_visit.do&quot;;this.form.target=&quot;&quot; ; this.form.submit(); return true ;" value="Найти" class="default" id="submitButton" autocomplete="off">
<%--			<input type="button" title="Печать" onclick="this.value=&quot;Печать&quot;; getId(0);this.form.action=&quot;print-f039.do&quot;;this.form.target=&quot;_blank&quot; ; this.form.submit(); return true ;" value="Печать" class="default" id="submitButton" autocomplete="off">
			<input type="button" title="Печать bis" onclick="this.value=&quot;Печать bis&quot;; getId(1);this.form.action=&quot;print-f039add.do&quot;;this.form.target=&quot;_blank&quot; ; this.form.submit(); return true ;" value="Печать bis" class="default" id="submitButton" autocomplete="off">
			<input type="button" title="Печать cons" onclick="this.value=&quot;Печать cons&quot;; getId(0);this.form.action=&quot;print-f039cons.do&quot;;this.form.target=&quot;_blank&quot; ; this.form.submit(); return true ;" value="Печать cons" class="default" id="submitButton" autocomplete="off">
 --%>		
 		</td>
        
        </msh:row>

    </msh:panel>
    </msh:form>
           <script type='text/javascript'>
    
    checkFieldUpdate('typeDate','${typeDate}',2) ;
<%--     checkFieldUpdate('typePatient','${typePatient}',3) ;
    checkFieldUpdate('typeEmergency','${typeEmergency}',3) ;
    checkFieldUpdate('typeHour','${typeHour}',3) ;--%>
    checkFieldUpdate('typeView','${typeView}',3) ;
    
  
   function checkFieldUpdate(aField,aValue,aDefaultValue) {
   	eval('var chk =  document.forms[0].'+aField) ;
   	var aMax=chk.length ;
   	//alert(aField+" "+aValue+" "+aMax+" "+chk) ;
   	if ((+aValue)==0 || (+aValue)>(+aMax)) {
   		chk[+aDefaultValue-1].checked='checked' ;
   	} else {
   		chk[+aValue-1].checked='checked' ;
   	}
   }
   </script>
    
    <%
    	if (request.getParameter("beginDate")!=null	) {
    		request.setAttribute("dateFrom",request.getParameter("beginDate")) ;
    		if (request.getParameter("finishDate")==null) {
    			request.setAttribute("dateTo", request.getParameter("beginDate")) ;
    		} else {
    			request.setAttribute("dateTo", request.getParameter("finishDate")) ;
    		}
    	    String date = (String)request.getAttribute("typeDate") ;
    	    String dateSql = "" ;
    	    if (date.equals("1")) {
    	    	dateSql = "wchb.dateFrom" ;
    	    } else if (date.equals("3")) {
    	    	dateSql = "mc.dateStart" ;
    	    } else {
    	    	dateSql = "wchb.createDate" ;
    	    }
    	    String view = (String)request.getAttribute("typeView") ;
    	    String viewSql ="" ;
        	if (view!=null && (view.equals("1"))) {
        		viewSql = " and mc.id is not null" ;
        	} else if (view!=null && (view.equals("2"))) {
        		viewSql = " and mc.id is null" ;
        	} 
        	request.setAttribute("viewSql", viewSql) ;
        	request.setAttribute("dateSql", dateSql) ;
    		%>
    
    <msh:section>
<ecom:webQuery name="journal_reestr" nameFldSql="journal_reestr_sql" nativeSql="
select wchb.id,ml.name as mlname,p.id,p.lastname||' '||p.firstname||' '||p.middlename as fio,p.birthday
as birthday,mkb.code,wchb.diagnosis
 ,wchb.dateFrom,mc.dateStart,mc.dateFinish,list(mkbF.code),wchb.phone
 ,wchb.createDate as wchbcreatedate
from WorkCalendarHospitalBed wchb
left join Patient p on p.id=wchb.patient_id
left join MedCase mc on mc.id=wchb.medcase_id
left join VocIdc10 mkb on mkb.id=wchb.idc10_id
left join MisLpu ml on ml.id=wchb.department_id
left join Diagnosis diag on diag.medcase_id=mc.id
left join VocIdc10 mkbF on mkbF.id=diag.idc10_id
where ${infoSql}
and ${dateSql} between to_date('${dateFrom}','dd.mm.yyyy') and to_date('${dateTo}','dd.mm.yyyy')
${viewSql} 
group by wchb.id,wchb.createDate,ml.name,p.id,p.lastname,p.firstname,p.middlename,p.birthday
,mkb.code,wchb.diagnosis,wchb.dateFrom,mc.dateStart,mc.dateFinish,wchb.phone
order by ${dateSql}
"/> 
    <msh:sectionTitle>
    <form action="print-smo_report_plan_hospital_by_visit.do" method="post" target="_blank">
    Период с ${dateFrom} по ${dateTo}.
    <input type='hidden' name="sqlText" id="sqlText" value="${journal_reestr_sql}"> 
    <input type='hidden' name="sqlInfo" id="sqlInfo" value="Период с ${dateFrom} по ${dateTo}.">
    <input type='hidden' name="sqlColumn" id="sqlColumn" value="${groupName}">
    <input type='hidden' name="s" id="s" value="PrintService">
    <input type='hidden' name="m" id="m" value="printNativeQuery">
    <input type="submit" value="Печать">
    </form>    
    </msh:sectionTitle>
    <msh:sectionContent>
        <msh:table
         name="journal_reestr" viewUrl="entityShortView-smo_planHospitalByVisit.do" action="entityView-smo_planHospitalByVisit.do" idField="1" noDataMessage="Не найдено">
            <msh:tableColumn columnName="#" property="sn"/>
            <msh:tableColumn columnName="Направлен в отделение" property="2"/>
            <msh:tableColumn columnName="Телефон пациента" property="12"/>
            <msh:tableColumn columnName="ФИО пациента" property="4"/>
            <msh:tableColumn columnName="Дата рождения" property="5"/>
            <msh:tableColumn columnName="Код МКБ" property="6"/>
            <msh:tableColumn columnName="Диагноз" property="7"/>
            <msh:tableColumn columnName="Дата создания" property="13"/>
            <msh:tableColumn columnName="Дата пред.госпитализации" property="8"/>
            <msh:tableColumn columnName="Дата факт. госпитализации" property="9"/>
            <msh:tableColumn columnName="Дата выписки" property="10"/>
            <msh:tableColumn columnName="Выписной диагноз" property="11"/>
        </msh:table>
    </msh:sectionContent>

    </msh:section>
    <% } else {%>
    	<i>Выберите параметры поиска и нажмите "Найти" </i>
    	<% }   %>
  </tiles:put>
  <tiles:put name="javascript" type="string">
  	<script type="text/javascript">

  		
  	</script>
  </tiles:put>

</tiles:insert>