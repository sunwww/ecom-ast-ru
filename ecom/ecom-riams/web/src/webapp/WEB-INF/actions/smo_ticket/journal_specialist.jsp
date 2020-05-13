<%@page import="ru.ecom.web.util.ActionUtil"%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://struts.apache.org/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://www.nuzmsh.ru/tags/msh" prefix="msh" %>
<%@ taglib uri="http://www.ecom-ast.ru/tags/ecom" prefix="ecom" %>
<%@ taglib tagdir="/WEB-INF/tags" prefix="tags" %>

<tiles:insert page="/WEB-INF/tiles/mainLayout.jsp" flush="true" >

    <tiles:put name='title' type='string'>
        <msh:title mainMenu="Medcard">Просмотр данных по специалистам</msh:title>
    </tiles:put>

    <tiles:put name='side' type='string'>
        <tags:ticket_finds currentAction="ticketsBySpec"/>
    </tiles:put>
    
  <tiles:put name="body" type="string">
    <%
	String typePatient =ActionUtil.updateParameter("Form039Action","typePatient","4", request) ;
	String typeDtype =ActionUtil.updateParameter("Form039Action","typeDtype","3", request) ;
  %>
    <msh:form action="/smo_journal_visit_by_specialist.do"
     defaultField="dateBegin" disableFormDataConfirm="true" method="GET" >
    <msh:panel >
      <msh:row>
        <msh:separator label="Параметры поиска" colSpan="7" />
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
	        <td class="label" title="База (typeDtype)" colspan="1">
	        <label for="typeDtypeName" id="typeDtypeLabel">Отобразить:</label></td>
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
               <msh:autoComplete vocName="workFunction" property="spec" 
        horizontalFill="true" fieldColSpan="8"
        label="Специалист" size="100"
        	
        />
        
        </msh:row>
         <msh:row>
        <msh:textField property="dateBegin" label="Период с" />
        <msh:textField property="dateEnd" label="по" />

           <td>
            <input type="submit" value="Найти" />
          </td>
        </msh:row>

    </msh:panel>
    </msh:form>
    
    <%
	if (typeDtype.equals("1")) {
		request.setAttribute("dtypeSql", "t.dtype='Visit'") ;
	} else if (typeDtype.equals("2")) {
		request.setAttribute("dtypeSql", "t.dtype='ShortMedCase'") ;
	} else {
		request.setAttribute("dtypeSql", "(t.dtype='ShortMedCase' or t.dtype='Visit')") ;
	}
    
    String date = (String)request.getParameter("dateBegin") ;
    if (date!=null && !date.equals(""))  {
    	String date1 = (String)request.getParameter("dateEnd") ;
    	if (date1==null || date1.equals("")) {
    		request.setAttribute("dateEnd", date) ;
    	} else {
    		request.setAttribute("dateEnd", date1) ;
    	}
    	String spec =""+request.getParameter("spec") ;
    	if (spec!=null && !spec.equals("") && !spec.equals("0") &!spec.equals("null")) {
    		request.setAttribute("spec", " and t.workFunctionExecute_id='"+spec+"'") ;
    	} else {
    		request.setAttribute("spec", "") ;
    	}
    	%>
    
    <msh:section>
    <msh:sectionTitle>Результаты поиска талонов ${infoTypePat}. Период с ${param.dateBegin} по ${dateEnd}. ${infoSearch}</msh:sectionTitle>
    <msh:sectionContent>
    <ecom:webQuery name="journal_ticket" nativeSql="select  
    to_CHAR(t.dateStart,'DD.MM.YYYY')||':${param.typePatient}'||':'||t.workFunctionExecute_id as idPar
    ,t.dateStart as tdate
    ,count(*) as cnt,vwf.name||' '|| wp.lastname||' '||wp.firstname||' '||wp.middlename as doctor
    ,count(distinct case when t.isTalk='1' then t.id else null end) as cntTalk
    from MedCase t 
    left join medcard as m on m.id=t.medcard_id 
    left join Patient p on p.id=m.person_id
    left join VocSocialStatus pvss on pvss.id=p.socialStatus_id
    left join Omc_Oksm ok on p.nationality_id=ok.id  
    left join WorkFunction as wf on wf.id=t.workFunctionExecute_id 
    left join Worker as w on w.id=wf.worker_id 
    left join Patient as wp on wp.id=w.person_id 
    left join VocWorkFunction vwf on vwf.id=wf.workFunction_id 
    where t.dateStart  between to_date('${param.dateBegin}','dd.mm.yyyy')  
    and to_date('${dateEnd}','dd.mm.yyyy')
     and ${dtypeSql}  ${add} ${spec} group by t.dateStart
     ,t.workFunctionExecute_id,wp.lastname,wp.middlename,wp.firstname,vwf.name" />
	<msh:ifInRole roles="/Policy/Mis/MisLpu/Psychiatry">
        <msh:table name="journal_ticket" action="poly_ticketsBySpecialistData.do" idField="1" noDataMessage="Не найдено">
            <msh:tableColumn columnName="#" property="sn"/>
            <msh:tableColumn columnName="Дата" property="2"/>
            <msh:tableColumn columnName="Специалист" property="4"/>
            <msh:tableColumn columnName="Кол-во беседа с род." property="5" isCalcAmount="true"/>
            <msh:tableColumn columnName="Кол-во" property="3" isCalcAmount="true"/>
        </msh:table>
	</msh:ifInRole>
	<msh:ifNotInRole roles="/Policy/Mis/MisLpu/Psychiatry">
        <msh:table name="journal_ticket" action="poly_ticketsBySpecialistData.do" idField="1" noDataMessage="Не найдено">
            <msh:tableColumn columnName="#" property="sn"/>
            <msh:tableColumn columnName="Дата" property="2"/>
            <msh:tableColumn columnName="Специалист" property="4"/>
            <msh:tableColumn columnName="Кол-во" property="3" isCalcAmount="true"/>
        </msh:table>
	</msh:ifNotInRole>
    </msh:sectionContent>
    <msh:sectionTitle>Разбивка по МКБ</msh:sectionTitle>
    <msh:sectionContent>
    <ecom:webQuery name="journal_ticket_mkb" nativeSql="select 
    diag.idc10_id||':${param.typePatient}'||':'||t.workFunctionExecute_id||':${param.dateBegin}:${param.dateEnd}' as idPar
    ,count(*) as cnt1
    ,vwf.name||' '|| wp.lastname||' '||wp.firstname||' '||wp.middlename as doctor,mkb.code as mkbcode 
    ,count(distinct case when t.istalk='1' then t.id else null end) as cntTalk
    from medcase t left join medcard as m on m.id=t.medcard_id 
    left join Patient p on p.id=m.person_id
    left join Omc_Oksm ok on p.nationality_id=ok.id  
    left join VocSocialStatus pvss on pvss.id=p.socialStatus_id
    left join WorkFunction as wf on wf.id=t.workFunctionExecute_id 
    left join Worker as w on w.id=wf.worker_id 
    left join Patient as wp on wp.id=w.person_id 
    left join diagnosis diag on diag.medCase_id=t.id
    left join vocprioritydiagnosis vpd on vpd.id=diag.priority_id
    left join vocidc10 as mkb on mkb.id=diag.idc10_id
    inner join VocWorkFunction vwf on vwf.id=wf.workFunction_id  
    where t.dateStart  between to_date('${param.dateBegin}','dd.mm.yyyy')  
    and to_date('${dateEnd}','dd.mm.yyyy') 
    and ${dtypeSql} and vpd.code='1' ${spec}
     ${add} 
     group by t.workFunctionExecute_id,diag.idc10_id
     ,vwf.name,wp.lastname,wp.firstname,wp.middlename,mkb.code
     " />
       <msh:table name="journal_ticket_mkb" action="poly_ticketsBySpecialistMkbData.do" idField="1" noDataMessage="Не найдено">
            <msh:tableColumn columnName="#" property="sn"/>
            <msh:tableColumn columnName="Специалист" property="3"/>
            <msh:tableColumn columnName="Диагноз" property="4"/>
            <msh:tableColumn columnName="Кол-во бесед" property="5" isCalcAmount="true"  cssClass="cssPsychiatry"/>
            <msh:tableColumn columnName="Кол-во" property="2" isCalcAmount="true"/>
        </msh:table>

    </msh:sectionContent>
    <msh:sectionTitle>Итог</msh:sectionTitle>
    <msh:sectionContent>
    <ecom:webQuery name="journal_ticket_sum" nativeSql="select count(*)
    ,vwf.name||' '|| wp.lastname||' '||wp.firstname||' '||wp.middlename as doctor
    , count(case when mkb.code like 'Z%' ${add1} then t.id else null end) as idccnt 
    ,count(case when t.isTalk='1'  then 1 else null end) as cnttalk,wp.snils
    from MedCase t left join medcard as m on m.id=t.medcard_id 
    left join Patient p on p.id=m.person_id
    left join Omc_Oksm ok on p.nationality_id=ok.id  
    left join VocSocialStatus pvss on pvss.id=p.socialStatus_id
    left join WorkFunction as wf on wf.id=t.workFunctionExecute_id 
    left join Worker as w on w.id=wf.worker_id 
    left join Patient as wp on wp.id=w.person_id 
    left join diagnosis diag on diag.medCase_id=t.id
    left join vocprioritydiagnosis vpd on vpd.id=diag.priority_id
    left join vocidc10 as mkb on mkb.id=diag.idc10_id
    inner join VocWorkFunction vwf on vwf.id=wf.workFunction_id  
    where t.dateStart  between to_date('${param.dateBegin}','dd.mm.yyyy')
      and to_date('${dateEnd}','dd.mm.yyyy') 
      and ${dtypeSql} 
      ${spec} and vpd.code='1' ${add}  group by t.workFunctionExecute_id
      ,vwf.name,wp.lastname,wp.firstname,wp.middlename,wp.snils
      " />
       <msh:table name="journal_ticket_sum" action="" idField="1" noDataMessage="Не найдено">
            <msh:tableColumn columnName="#" property="sn"/>
            <msh:tableColumn columnName="Специалист" property="2"/>
            <msh:tableColumn columnName="СНИЛС спец." property="5"/>
            <msh:tableColumn columnName="Кол-во Z*" property="3" isCalcAmount="true"/>
            <msh:tableColumn columnName="Кол-во бесед" property="4" isCalcAmount="true" cssClass="cssPsychiatry"/>
            <msh:tableColumn columnName="Всего" property="1" isCalcAmount="true"/>
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

    </script>
  </tiles:put>
</tiles:insert>

