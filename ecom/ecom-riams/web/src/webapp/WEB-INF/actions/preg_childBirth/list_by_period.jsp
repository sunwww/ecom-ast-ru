<%@page import="ru.ecom.mis.web.action.medcase.journal.AdmissionJournalForm"%>
<%@page import="ru.ecom.web.util.ActionUtil"%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://struts.apache.org/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://www.nuzmsh.ru/tags/msh" prefix="msh" %>
<%@ taglib uri="http://www.ecom-ast.ru/tags/ecom" prefix="ecom" %>
<%@ taglib tagdir="/WEB-INF/tags" prefix="tags" %>

<tiles:insert page="/WEB-INF/tiles/mainLayout.jsp" flush="true" >

  <tiles:put name="title" type="string">
    <msh:title guid="helloItle-123" mainMenu="Journals" title="Отчет по родам"></msh:title>
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
    <msh:form action="/preg_child_birth_report.do" defaultField="dateBegin" disableFormDataConfirm="true" method="GET" guid="d7b31bc2-38f0-42cc-8d6d-19395273168f">
    <input type="hidden" name="s" id="s" value="HospitalPrintReport" />
    <input type="hidden" name="m" id="m" value="printReport007" />
    <input type="hidden" name="id" id="id" value=""/>
    <msh:panel guid="6ae283c8-7035-450a-8eb4-6f0f7da8a8ff">
    <%--
      <msh:row guid="7d80be13-710c-46b8-8503-ce0413686b69">
        <td class="label" title="Представление (typeView)" colspan="1"><label for="typeViewName" id="typeViewLabel">Отобразить:</label></td>
        <td onclick="this.childNodes[1].checked='checked';">
        	<input type="radio" name="typeView" value="1">  свод по датам
        </td>
        <td onclick="this.childNodes[1].checked='checked';">
        	<input type="radio" name="typeView" value="2">  реестр
        </td>
      </msh:row> --%>
      <msh:row guid="53627d05-8914-48a0-b2ec-792eba5b07d9">
        <msh:separator label="Параметры поиска" colSpan="7" guid="15c6c628-8aab-4c82-b3d8-ac77b7b3f700" />
      </msh:row>
      <msh:row guid="7d80be13-710c-46b8-8503-ce0413686b69">
        <msh:textField property="dateBegin" label="Период с" guid="8d7ef035-1273-4839-a4d8-1551c623caf1" />
        <msh:textField property="dateEnd" label="по" guid="f54568f6-b5b8-4d48-a045-ba7b9f875245" />
           <td>
            <input type="submit" value="Найти" />
            <%--<input type="submit" onclick="print()" value="Печать" /> --%>
          </td>
      </msh:row>
    </msh:panel>
    </msh:form>
           <script type='text/javascript'>
    
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
    }
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
    if (date!=null && !date.equals("")) {
        AdmissionJournalForm frm = (AdmissionJournalForm) session.getAttribute("stac_admissionJournalForm");
        //String department = (String)request.getAttribute("department") ;
        %>
    
    <msh:section>
    <msh:sectionTitle>Результаты поиска за период с ${dateBegin} по ${dateEnd}.</msh:sectionTitle>
    </msh:section>
    <%
    //if (view!=null&&view.equals("1")) {
    %>
    <msh:section>
    <msh:sectionTitle>Разбивка по дням</msh:sectionTitle>
    <msh:sectionContent>
    <ecom:webQuery name="journal_surOperation" nativeSql="
    select slo.id as slo_id, ss.code as sscode,
    pat.lastname||' '||pat.firstname||' '||pat.middlename||' г.р. '||to_char(pat.birthday,'dd.mm.yyyy') as fio
    , to_char(sls.datestart, 'dd.MM.yyyy') || ' ' || cast(sls.entrancetime as varchar(5)) sls_start
    , to_char(slo.datestart, 'dd.MM.yyyy')||'-'||coalesce(to_char(coalesce(slo.transferdate,slo.datefinish), 'dd.MM.yyyy'),'') slo_start
    , to_char(cb.birthfinishdate, 'dd.MM.yyyy') cb_date
    , count(cb.id) as cntCb
    
     from MedCase slo 
     left join MedCase sls on sls.id=slo.parent_id
     left join statisticstub ss on ss.id=sls.statisticstub_id
     left join mislpu ml on ml.id=slo.department_id
     left join ChildBirth cb on cb.medcase_id = slo.id
     left join NewBorn nb on nb.childBirth_id=cb.id
     left join patient pat on pat.id=slo.patient_id
     where 
    slo.datestart between to_date('${dateBegin}','dd.mm.yyyy') 
    and to_date('${dateEnd}','dd.mm.yyyy') and slo.dtype='DepartmentMedCase'
    and ml.IsMaternityWard='1'
    group by slo.id, ss.code, pat.lastname, pat.firstname, pat.middlename, pat.birthday, sls.datestart, 
    sls.entrancetime, slo.datestart, cb.birthfinishdate
    order by slo.datestart, pat.lastname, pat.firstname, pat.middlename" />
    <msh:table name="journal_surOperation" viewUrl="entitySubclassView-mis_medCase.do?short=Short"  action="entitySubclassView-mis_medCase.do" idField="1" guid="b621e361-1e0b-4ebd-9f58-b7d919b45bd6">
    <msh:tableColumn property="sn" columnName="#"/>
    <msh:tableColumn property="2" columnName="№ и/б"/>
    <msh:tableColumn property="3" columnName="Пациент"/>
    <msh:tableColumn property="4" columnName="Нач. стац. лечения"/>
    <msh:tableColumn property="5" columnName="Поступ. в род. отд."/>
    <msh:tableColumn property="6" columnName="Дата родов"/>
    <msh:tableColumn property="7" columnName="Кол-во плодов"/>
    </msh:table>
    </msh:sectionContent>
    </msh:section>
    <%    } else {%>
    	<i>Нет данных </i>
    	<% }   %>
    
  </tiles:put>
</tiles:insert>