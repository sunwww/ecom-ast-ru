<%@page import="ru.ecom.mis.web.action.medcase.journal.AdmissionJournalForm"%>
<%@page import="ru.ecom.web.util.ActionUtil"%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://struts.apache.org/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://www.nuzmsh.ru/tags/msh" prefix="msh" %>
<%@ taglib uri="http://www.ecom-ast.ru/tags/ecom" prefix="ecom" %>
<%@ taglib tagdir="/WEB-INF/tags" prefix="tags" %>

<tiles:insert page="/WEB-INF/tiles/mainLayout.jsp" flush="true" >

  <tiles:put name="title" type="string">
    <msh:title mainMenu="Journals" title="Журнал гистологий плаценты"></msh:title>
  </tiles:put>
  <tiles:put name="side" type="string">
  	<tags:style_currentMenu currentAction="preg_histology" />
    	<tags:mis_journal />
  </tiles:put>
  <tiles:put name="body" type="string">
  <% 
  if (request.getParameter("short")==null) {
	  ActionUtil.updateParameter("ChildBirth","typeView","2", request) ;
	  ActionUtil.updateParameter("ChildBirth","typePhatology","3", request) ;
  }
  %>
    <msh:form action="/journal_roddom_histology.do" defaultField="dateBegin" disableFormDataConfirm="true" method="GET">
    <input type="hidden" name="s" id="s" value="HospitalPrintReport" />
    <input type="hidden" name="m" id="m" value="printReport007" />
    <input type="hidden" name="id" id="id" value=""/>
    <msh:panel>
      <msh:row>
        <td class="label" title="Представление (typeView)" colspan="1"><label for="typeViewName" id="typeViewLabel">Отобразить:</label></td>
        <td onclick="this.childNodes[1].checked='checked';">
        	<input type="radio" name="typeView" value="1">  свод по датам
        </td>
        <td onclick="this.childNodes[1].checked='checked';">
        	<input type="radio" name="typeView" value="2">  реестр
        </td>
      </msh:row>
      <msh:row>
        <td class="label" title="Гистология (typePhatology)" colspan="1"><label for="typePhatologyName" id="typePhatologyLabel">Гистология:</label></td>
        <td onclick="this.childNodes[1].checked='checked';">
        	<input type="radio" name="typePhatology" value="1">  с патологией
        </td>
        <td onclick="this.childNodes[1].checked='checked';" colspan="2">
        	<input type="radio" name="typePhatology" value="2">  без патологии
        </td>
        <td onclick="this.childNodes[1].checked='checked';" colspan="2">
        	<input type="radio" name="typePhatology" value="3">  все
        </td>
      </msh:row>
      <msh:row>
        <msh:separator label="Параметры поиска" colSpan="7" />
      </msh:row>
      <msh:row>
      	<msh:autoComplete property="department" fieldColSpan="7" horizontalFill="true" vocName="lpu"/>
      </msh:row>
      <msh:row>
        <msh:textField property="dateBegin" label="Период с" />
        <msh:textField property="dateEnd" label="по" />
           <td>
            <input type="submit" value="Найти" />
            <input type="submit" onclick="print()" value="Печать" />
          </td>
      </msh:row>
    </msh:panel>
    </msh:form>
           <script type='text/javascript'>
    
<%--    checkFieldUpdate('typeDate','${typeDate}',1) ;
     checkFieldUpdate('typePatient','${typePatient}',3) ;
    checkFieldUpdate('typeEmergency','${typeEmergency}',3) ;
    checkFieldUpdate('typeHour','${typeHour}',3) ;--%>
    checkFieldUpdate('typeView','${typeView}',1) ;
    checkFieldUpdate('typePhatology','${typePhatology}',1) ;
    
  
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
			 
    function find() {
    	var frm = document.forms[0] ;
    	frm.target='' ;
    	frm.action='journal_surOperation.do' ;
    }

   
    function print() {
    	var frm = document.forms[0] ;
    	frm.m.value="printHistology" ;
    	frm.target='_blank' ;
    	frm.action='print-stac_histology.do' ;
    	$('id').value = getCheckedRadio(frm,"typePhatology")+":"
    		
    		+$('dateBegin').value+":"
    		+$('dateBegin').value+":"
    		+$('department').value;
    }/*
    function printJournal() {
    	var frm = document.forms[0] ;
    	frm.m.value="printJournalByDay" ;
    	frm.target='_blank' ;
    	frm.action='print-stac_journal001.do' ;
    	$('id').value = 
    		$('dateBegin').value+":"
    		
    		+$('department').value;
    }
    */
    /**/
    if ($('dateBegin').value=="") {
    	$('dateBegin').value=getCurrentDate() ;
    }

			 
    </script>
    <%
    String date = (String)request.getParameter("dateBegin") ;
    String dateEnd = (String)request.getParameter("dateEnd") ;

    if (dateEnd==null || dateEnd.equals("")) dateEnd=date ;
    request.setAttribute("dateBegin", date) ;
    request.setAttribute("dateEnd", dateEnd) ;
    
    String view = (String)request.getAttribute("typeView") ;
    String typePhatology = (String)request.getAttribute("typePhatology") ;
    if (date!=null && !date.equals("")) {
        AdmissionJournalForm frm = (AdmissionJournalForm) session.getAttribute("stac_admissionJournalForm");
        //String department = (String)request.getAttribute("department") ;
        //String spec = (String)request.getAttribute("spec") ;
    	if (typePhatology!=null) {
    		if (typePhatology.equals("1")) {
	    		request.setAttribute("pathology", "1") ;
	    		request.setAttribute("pathologySql", " and vhr.isWithPathology='1'") ;
	    	} else if (typePhatology.equals("2")) {
	    		request.setAttribute("pathology", "0") ;
	    		request.setAttribute("pathologySql", " and (vhr.isWithPathology is null or vhr.isWithPathology='0')") ;
	    	} else {
	    		request.setAttribute("pathology", "") ;
	    		request.setAttribute("pathologySql", "") ;
	    	} 
    	}
    	if (frm!=null) {
    	if (frm.getDepartment()!=null && !frm.getDepartment().equals(Long.valueOf(0))) {
    		request.setAttribute("department", " and slo.department_id='"+frm.getDepartment()+"'") ;
    		request.setAttribute("departmentSql", " and slo.department_id="+frm.getDepartment()+"") ;
    	}
    	}
    	%>
    
    <msh:section>
    <msh:sectionTitle>Результаты поиска за период с ${dateBegin} по ${dateEnd}.</msh:sectionTitle>
    </msh:section>
    <%
    if (view!=null&&view.equals("1")) {
    %>
    <msh:section>
    <msh:sectionTitle>Разбивка по дням</msh:sectionTitle>
    <msh:sectionContent>
    <ecom:webQuery isReportBase="true" name="journal_surOperation" nativeSql="
    select '${departmentSql} :${pathology}:'||to_char(cb.birthFinishDate,'dd.mm.yyyy')||':'||to_char(cb.birthFinishDate,'dd.mm.yyyy'),to_char(cb.birthFinishDate,'dd.mm.yyyy') as cbbirthFinishDate
    , count(cb.id) as cntCb, count(case when cb.isHistologyWithPathology='1' then cb.id else null end) as cntPathology
     from ChildBirth cb where 
    cb.birthFinishDate  between to_date('${dateBegin}','dd.mm.yyyy') 
    and to_date('${dateEnd}','dd.mm.yyyy') ${department} ${pathologySql}
    and cb.placentaHistologyOrder='1'
    group by cb.birthFinishDate 
    order by cb.birthFinishDate" />
    <msh:table name="journal_surOperation" viewUrl="journal_surOperationByDate.do?short=Short"  action="journal_surOperationByDate.do?dateSearch=${dateSearch}" idField="1">
      <msh:tableColumn columnName="Дата" property="2" />
      <msh:tableColumn columnName="Кол-во направлений на гистологию" identificator="false" property="3" />
      <msh:tableColumn columnName="Кол-во c патологией" identificator="false" property="4" />
    </msh:table>
    </msh:sectionContent>
    </msh:section>
    <%}
    if (view!=null&&view.equals("2")) {
    %>
    <msh:section>
    <msh:sectionTitle>Реестр </msh:sectionTitle>
    <msh:sectionContent>
    <ecom:webQuery isReportBase="true" name="journal_reestr" nativeSql="select
    cb.id as id,pat.lastname ||' '||pat.firstname ||' '|| pat.middlename as patmiddlename
    ,to_char(pat.birthday,'dd.mm.yyyy') as patbirthday
         	,to_char(cb.birthFinishDate,'dd.mm.yyyy')||' '||cast(cb.birthFinishTime as varchar(5)) as datetimebirthday
        	,vof.name as ofname, list(distinct case 
        	when vdrt.code='4' and vpd.code='1' and (mkb.code='O82.1' or mkb.code='O82.0') then 'кесарево' 
        	when vdrt.code='4' and vpd.code='1' and (mkb.code = 'O80.0' or mkb.code='O80.1') then 'естествен.'
        	else '' end)
        	,case when cb.placentaHistologyOrder='1' then 'Направлена плацента на гистологию.'||coalesce(vhr.name,'') else '' end as histology
        	,preg.orderNumber as pregorderNumber,cons.name as consname
        	,pec.previousPregnancies as pecnotes,vms.name as vmsname,pec.pregnancyFeatures as pregpregnancyFeatures
        	from ChildBirth cb 
        	left join MedCase slo on slo.id=cb.medCase_id
        	left join MedCase sls on sls.id=slo.parent_id
         	left join Pregnancy preg on preg.id=sls.pregnancy_id 
         	left join Patient pat on pat.id=preg.patient_id
         	left join VocMarriageStatus vms on vms.id=pat.marriageStatus_id
        	left join Diagnosis d on d.medCase_id=slo.id
        	left join VocDiagnosisRegistrationType vdrt on vdrt.id=d.registrationType_id
        	left join VocPriorityDiagnosis vpd on vpd.id=d.priority_id
        	left join VocIdc10 mkb on mkb.id=d.idc10_id
        	left join Omc_Frm vof on vof.id=sls.orderType_id
        	left join PregnanExchangeCard pec on pec.pregnancy_id=preg.id
        	left join MisLpu cons on cons.id=pec.consultation_id
        	left join VocHistologyResult vhr on vhr.id=cb.histology_id
    where 
    cb.birthFinishDate  between to_date('${dateBegin}','dd.mm.yyyy') 
    and to_date('${dateEnd}','dd.mm.yyyy') ${department} ${pathologySql}
          	group by cb.id,cb.birthFinishDate,cb.birthFinishTime,
          	vof.name,cb.placentaHistologyOrder,vhr.name,pat.lastname,pat.firstname,pat.middlename
    		,pat.birthday,preg.orderNumber ,cons.name 
        	,pec.notes,vms.name,pec.pregnancyFeatures,pec.previousPregnancies
    order by cb.birthFinishDate
    " />
    <msh:table name="journal_reestr" viewUrl="entityParentView-preg_childBirth.do?short=Short" action="entityParentView-preg_childBirth.do" idField="1">
              <msh:tableColumn property="2" columnName="ФИО" />
              <msh:tableColumn property="3" columnName="Дата рождения" />
              <msh:tableColumn property="4" columnName="Окончания родов" />
              <msh:tableColumn property="8" columnName="Какая беременность по счету"/>
              <msh:tableColumn property="9" columnName="Жен. консультация"/>
              <msh:tableColumn property="10" columnName="Особенности течения прежних беременностей, родов, послеродового периода (акушерский анамнез)"/>
              <msh:tableColumn property="11" columnName="Семейное положение"/>
              <msh:tableColumn property="12" columnName="Течение настоящей беременности"/>
              <msh:tableColumn property="5" columnName="Кем доставлен" />
              <msh:tableColumn property="6" columnName="Роды" />
              <msh:tableColumn property="7" columnName="Гистология плаценты" cssClass="preCell" />
    </msh:table>
    </msh:sectionContent>    
    </msh:section>
    <%}
    } else {%>
    	<i>Нет данных </i>
    	<% }   %>
    
  </tiles:put>
</tiles:insert>