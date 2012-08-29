<%@page import="ru.ecom.web.login.LoginInfo"%>
<%@page import="ru.ecom.mis.web.action.util.ActionUtil"%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://struts.apache.org/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://www.nuzmsh.ru/tags/msh" prefix="msh" %>
<%@ taglib uri="http://www.ecom-ast.ru/tags/ecom" prefix="ecom" %>
<%@ taglib tagdir="/WEB-INF/tags" prefix="tags" %>

<%@page import="ru.ecom.poly.web.action.ticket.JournalBySpecialistForm"%>
<tiles:insert page="/WEB-INF/tiles/mainLayout.jsp" flush="true" >

    <tiles:put name='title' type='string'>
        <msh:title mainMenu="Poly">Просмотр данных по направленным пациентам к другим специалистам за последний месяц</msh:title>
    </tiles:put>

    <tiles:put name='side' type='string'>
        <tags:visit_finds currentAction="reportDirectOtherSpec"/>
    </tiles:put>
    
  <tiles:put name="body" type="string">
  <msh:form action="/poly_directOtherSpecialist.do" defaultField="beginDate" disableFormDataConfirm="true" method="GET" guid="d7b31bc2-38f0-42cc-8d6d-19395273168f">
    <msh:panel colsWidth="1%,1%,1%">
      <msh:row guid="53627d05-8914-48a0-b2ec-792eba5b07d9">
        <msh:separator label="Параметры поиска" colSpan="7" guid="15c6c628-8aab-4c82-b3d8-ac77b7b3f700" />
      </msh:row>
      <msh:row>
        	<msh:textField property="beginDate" label="Период с" guid="8d7ef035-1273-4839-a4d8-1551c623caf1" />
        	<msh:textField property="finishDate" label="по" guid="f54568f6-b5b8-4d48-a045-ba7b9f875245" />
        </msh:row>
        <msh:row>
        	<msh:autoComplete property="workFunction" vocName="vocWorkFunction" 
        		horizontalFill="true" fieldColSpan="6" size="70"/>
        </msh:row>
        <msh:row>
        	<msh:autoComplete property="specialist" vocName="workFunction" 
        		horizontalFill="true" fieldColSpan="6" size="70"/>
        </msh:row>  
        <msh:row>
        	<msh:autoComplete property="patient" vocName="patient" 
        		horizontalFill="true" fieldColSpan="6" size="70" label="Пациент"/>
        </msh:row>  
        <msh:row>
	        <td class="label" title="Дата поиска (typeDate)" colspan="1"><label for="typeDateName" id="typeDateLabel">Дата:</label></td>
	        <td onclick="this.childNodes[1].checked='checked';">
	        	<input type="radio" name="typeDate" value="1"> направления
	        </td>
	        
	        <td colspan="2" onclick="this.childNodes[1].checked='checked';">
	        	<input type="radio" name="typeDate" value="2"> приема
	        </td>
        </msh:row>

        <msh:row>
        <td colspan="2" class="buttons">
			<input type="button" title="Найти" onclick="this.value=&quot;Поиск...&quot;;this.form.submit(); return true ;" value="Найти" class="default" id="submitButton" autocomplete="off">
		</td>
        </msh:row>
    </msh:panel>
    </msh:form>
    <%ActionUtil.updateParameter("SurgicalOperation","typeDate","1", request) ;%>
    <script type='text/javascript'>
    checkFieldUpdate('typeDate','${typeDate}',1) ;
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
   String beginDate = request.getParameter("beginDate") ;
   String typeDate = request.getParameter("typeDate") ;
   if (beginDate!=null && !beginDate.equals("")) {
	   String patient = request.getParameter("patient") ;
	   String specialist = request.getParameter("specialist") ;
	   String workFunction = request.getParameter("workFunction") ;
	   String username = LoginInfo.find(request.getSession(true)).getUsername() ;
	   request.setAttribute("username", username) ;
	   if (patient!=null && !patient.equals("") && !patient.equals("0")) {
		   request.setAttribute("patient", " and v.patient_id='"+patient+"'") ;
	   }
	   if (specialist!=null && !specialist.equals("") && !specialist.equals("0")) {
		   request.setAttribute("specialist", " and v.workFunctionPlan_id='"+specialist+"'");
	   }
	   if (workFunction!=null && !workFunction.equals("") && !workFunction.equals("0")) {
		   request.setAttribute("workFunction", " and wfp.workFunction_id='"+workFunction+"'");
	   }
	   if (typeDate!=null &&typeDate.equals("2")) {
		   request.setAttribute("date", "v.dateStart") ;
	   } else {
		   request.setAttribute("date", "wcd.calendarDate") ;
	   }
	   
   %>
   <msh:section title="Реестр пациентов">
   	    <ecom:webQuery name="list" nativeSql="select v.id
	    
	    ,to_char(v.dateStart,'DD.MM.YYYY')||' '||cast(v.timeExecute as varchar(5)) as dateStart

	    ,p.lastname||' '||p.firstname||' '||p.middlename||' г.р.'||to_char(p.birthday,'DD.MM.YYYY') as pfio
	    ,vwfe.name||' '||pe.lastname as pefio
	    ,list(mkb.code) as diagnosis
	    ,list(d.record) as diary
from medcase v 
left join Diagnosis diag on diag.medCase_id=v.id
left join VocIdc10 mkb on mkb.id=diag.idc10_id
left join Diary d on d.medCase_id=v.id
left join WorkCalendarDay wcd on wcd.id=v.datePlan_id
left join patient p on p.id=v.patient_id
left join WorkFunction wfe on wfe.id=v.workFunctionExecute_id
left join WorkFunction wfp on wfp.id=v.workFunctionPlan_id
left join VocWorkFunction vwfp on vwfp.id=wfp.workFunction_id
left join Worker we on we.id=wfe.worker_id
left join Patient pe on pe.id=we.person_id
left join VocWorkFunction vwfe on vwfe.id=wfe.workFunction_id
left join VocVisitResult vvr on vvr.id=v.visitResult_id
where  ${date} between to_date('${param.beginDate}','dd.mm.yyyy') and to_date('${param.finishDate}','dd.mm.yyyy')
and v.username='${username}'
and v.DTYPE='Visit' ${patient} and v.dateStart is not null
and (v.noActuality is null or v.noActuality='0')
 ${specialist} ${workFunction}
 group by v.id,v.dateStart,v.timeExecute,p.lastname,p.firstname,p.middlename
 ,p.birthday,vwfe.name,pe.lastname
order by p.lastname,p.firstname,p.middlename"/>
<msh:table viewUrl="entityShortView-smo_visit.do" name="list" 
	action="entityView-smo_visit.do" idField="1" >
	      <msh:tableColumn columnName="№" identificator="false" property="sn" guid="270ae0dc-e1c6-45c5-b8b8-26d034ec3878" />
	      <msh:tableColumn columnName="Пациент" property="3" guid="315cb6eb-3db8-4de5-8b0c-a49e3cacf382" />
	      <msh:tableColumn columnName="Дата приема" identificator="false" property="2" guid="b3e2fb6e-53b6-4e69-8427-2534cf1edcca" />
	      <msh:tableColumn columnName="Исполнитель" identificator="false" property="4" guid="3145e72a-cce5-4994-a507-b1a81efefdfe" />
	      <msh:tableColumn property="5" columnName="Диагноз"/>
	      <msh:tableColumn property="6" columnName="Заключение" cssClass="preCell"/>
	    </msh:table>
  	</msh:section>
  	<% } %>
  </tiles:put>

</tiles:insert>