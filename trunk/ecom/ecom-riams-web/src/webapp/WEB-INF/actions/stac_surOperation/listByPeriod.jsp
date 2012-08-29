<%@page import="ru.ecom.mis.web.action.medcase.journal.AdmissionJournalForm"%>
<%@page import="ru.ecom.mis.web.action.util.ActionUtil"%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://struts.apache.org/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://www.nuzmsh.ru/tags/msh" prefix="msh" %>
<%@ taglib uri="http://www.ecom-ast.ru/tags/ecom" prefix="ecom" %>
<%@ taglib tagdir="/WEB-INF/tags" prefix="tags" %>

<tiles:insert page="/WEB-INF/tiles/mainLayout.jsp" flush="true" >

  <tiles:put name="title" type="string">
    <msh:title guid="helloItle-123" mainMenu="Journals" title="Журнал хирургических операций"></msh:title>
  </tiles:put>
  <tiles:put name="side" type="string">
  	<tags:style_currentMenu currentAction="stac_surOperation" />
    	<tags:mis_journal />
  </tiles:put>
  <tiles:put name="body" type="string">
  <% 
  if (request.getParameter("short")==null) {
	  ActionUtil.updateParameter("SurgicalOperation","typeView","1", request) ;
	  ActionUtil.updateParameter("SurgicalOperation","typeOrder","1", request) ;
  }
  %>
    <msh:form action="/journal_surOperation.do" defaultField="dateBegin" disableFormDataConfirm="true" method="GET" guid="d7b31bc2-38f0-42cc-8d6d-19395273168f">
    <msh:panel guid="6ae283c8-7035-450a-8eb4-6f0f7da8a8ff">
      <msh:row guid="7d80be13-710c-46b8-8503-ce0413686b69">
        <td class="label" title="Представление (typeView)" colspan="1"><label for="typeViewName" id="typeViewLabel">Отобразить:</label></td>
        <td onclick="this.childNodes[1].checked='checked';">
        	<input type="radio" name="typeView" value="1">  свод по датам
        </td>
        <td onclick="this.childNodes[1].checked='checked';" colspan="2">
        	<input type="radio" name="typeView" value="2">  свод по хирургам и операциям
        </td>
        <td onclick="this.childNodes[1].checked='checked';">
        	<input type="radio" name="typeView" value="3">  свод по отделению и операциям
        </td>
        <td onclick="this.childNodes[1].checked='checked';">
        	<input type="radio" name="typeView" value="4">  свод по операциям
        </td>
        <td onclick="this.childNodes[1].checked='checked';">
        	<input type="radio" name="typeView" value="5">  реестр
        </td>
      </msh:row>
      <msh:row guid="7d80be13-710c-46b8-8503-ce0413686b69">
        <td class="label" title="Сортировка (typeOrder)" colspan="1"><label for="typeOrderName" id="typeOrderLabel">Сортировка:</label></td>
        <td onclick="this.childNodes[1].checked='checked';">
        	<input type="radio" name="typeOrder" value="1">  название операция
        </td>
        <td onclick="this.childNodes[1].checked='checked';" colspan="2">
        	<input type="radio" name="typeOrder" value="2">  код операция
        </td>
        <td onclick="this.childNodes[1].checked='checked';">
        	<input type="radio" name="typeOrder" value="3">  доп. параметры, название операции
        </td>
        <td onclick="this.childNodes[1].checked='checked';">
        	<input type="radio" name="typeOrder" value="4">  доп. параметры, код операции
        </td>
      </msh:row>
      <msh:row guid="53627d05-8914-48a0-b2ec-792eba5b07d9">
        <msh:separator label="Параметры поиска" colSpan="7" guid="15c6c628-8aab-4c82-b3d8-ac77b7b3f700" />
      </msh:row>
      <msh:row>
      	<msh:autoComplete property="department" fieldColSpan="7" horizontalFill="true" vocName="lpu"/>
      </msh:row>
      <msh:row>
      	<msh:autoComplete property="spec" fieldColSpan="7" horizontalFill="true" vocName="workFunctionIsSurgical"/>
      </msh:row>
      <msh:row guid="7d80be13-710c-46b8-8503-ce0413686b69">
        <msh:textField property="dateBegin" label="Период с" guid="8d7ef035-1273-4839-a4d8-1551c623caf1" />
        <msh:textField property="dateEnd" label="по" guid="f54568f6-b5b8-4d48-a045-ba7b9f875245" />
           <td>
            <input type="submit" value="Найти" />
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
    checkFieldUpdate('typeOrder','${typeOrder}',1) ;
    
  
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

    /*
    function print() {
    	var frm = document.forms[0] ;
    	frm.m.value="printReestrByDay" ;
    	frm.target='_blank' ;
    	frm.action='print-stac_report007.do' ;
    	$('id').value = getCheckedRadio(frm,"typeEmergency")+":"
    		+getCheckedRadio(frm,"typeHour")+":"
    		+getCheckedRadio(frm,"typeDate")+":"
    		+$('dateBegin').value+":"
    		+$('pigeonHole').value+":"
    		+$('department').value;
    }
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
    String typeOrder = (String)request.getAttribute("typeOrder") ;
    if (date!=null && !date.equals("")) {
        AdmissionJournalForm frm = (AdmissionJournalForm) session.getAttribute("stac_admissionJournalForm");
        //String department = (String)request.getAttribute("department") ;
        //String spec = (String)request.getAttribute("spec") ;
    	if (typeOrder!=null) {
    		if (typeOrder.equals("1")) {
	    		request.setAttribute("order1", "vo.name,") ;
	    		request.setAttribute("order2", "") ;
	    		request.setAttribute("group1", "so.medservice_id,vo.name,vo.code,") ;
	    		request.setAttribute("group2", "") ;
	    	} else if (typeOrder.equals("2")) {
	    		request.setAttribute("order1", "vo.code,") ;
	    		request.setAttribute("order2", "") ;
	    		request.setAttribute("group1", "so.medservice_id,vo.name,vo.code,") ;
	    		request.setAttribute("group2", "") ;
	    	} else if (typeOrder.equals("3")) {
	    		request.setAttribute("order1", "") ;
	    		request.setAttribute("order2", ",vo.name") ;
	    		request.setAttribute("group1", "") ;
	    		request.setAttribute("group2", ",so.medservice_id,vo.name,vo.code") ;
	    	} else if (typeOrder.equals("4")) {
	    		request.setAttribute("order1", "") ;
	    		request.setAttribute("order2", ",vo.code") ;
	    		request.setAttribute("group1", "") ;
	    		request.setAttribute("group2", ",so.medservice_id,vo.name,vo.code") ;
	    	}
    	}
    	if (frm!=null) {
    	if (frm.getDepartment()!=null && !frm.getDepartment().equals(Long.valueOf(0))) {
    		request.setAttribute("department", " and so.department_id='"+frm.getDepartment()+"'") ;
    		request.setAttribute("departmentSql", " and so.department_id="+frm.getDepartment()+"") ;
    	}
    	if (frm.getSpec()!=null && !frm.getSpec().equals(Long.valueOf(0))) {
    		request.setAttribute("spec", " and so.surgeon_id='"+frm.getSpec()+"'") ;
    		request.setAttribute("specSql", " and so.surgeon_id="+frm.getSpec()+"") ;
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
    <ecom:webQuery name="journal_surOperation" nativeSql="
    select '${departmentSql} ${specSql}:'||to_char(so.operationDate,'dd.mm.yyyy')||':'||to_char(so.operationDate,'dd.mm.yyyy'),to_char(so.operationDate,'dd.mm.yyyy'), count(so.id)
     from SurgicalOperation so where 
    so.operationDate  between to_date('${dateBegin}','dd.mm.yyyy') 
    and to_date('${dateEnd}','dd.mm.yyyy') ${department} ${spec}
    group by so.operationDate 
    order by so.operationDate" />
    <msh:table name="journal_surOperation" viewUrl="journal_surOperationByDate.do?short=Short"  action="journal_surOperationByDate.do?dateSearch=${dateSearch}" idField="1" guid="b621e361-1e0b-4ebd-9f58-b7d919b45bd6">
      <msh:tableColumn columnName="Дата" property="2" guid="de1f591c-02b8-4875-969f-d2698689db5d" />
      <msh:tableColumn columnName="Количество операций" identificator="false" property="3" guid="7f73955-a5cb-4497-bd0b-f4d05848f049" />
    </msh:table>
    </msh:sectionContent>
    </msh:section>
    <%}
    if (view!=null&&view.equals("2")) {
    %>
    <msh:section>
    <msh:sectionTitle>Разбивка по хирургам и операциям</msh:sectionTitle>
    <msh:sectionContent>
    <ecom:webQuery name="journal_surOperationBySpec" nativeSql="select 
    '${departmentSql} ${specSql}:'||'${dateBegin}:${dateEnd}:'||so.surgeon_id||':'||so.medservice_id as id
    ,vwf.name||' '||p.lastname||' '|| p.firstname||' '|| p.middlename as doctor
    , vo.code as vocode, vo.name as voname,count(*) as cnt 
    from SurgicalOperation so
left join medservice vo on vo.id=so.medService_id
left join workfunction wf on wf.id=so.surgeon_id
left join worker w on w.id=wf.worker_id
left join patient p on p.id=w.person_id
left join vocworkfunction vwf on vwf.id=wf.workFunction_id
where so.operationDate between to_date('${dateBegin}','dd.mm.yyyy') 
and to_date('${dateEnd}','dd.mm.yyyy') ${department} ${spec}
group by ${group1}so.surgeon_id,vwf.name,p.lastname, p.firstname, p.middlename ${group2}
order by ${order1} p.lastname,p.firstname,p.middlename ${order2}" guid="4a720225-8d94-4b47-bef3-4dbbe79eec74" />
    <msh:table name="journal_surOperationBySpec" viewUrl="journal_surOperationByDate.do?short=Short" action="journal_surOperationByDate.do?dateSearch=${dateSearch}" idField="1" guid="b621e361-1e0b-4ebd-9f58-b7d919b45bd6">
      <msh:tableColumn columnName="Специалист" property="2" guid="de1f591c-02b8-4875-969f-d2698689db5d" />
      <msh:tableColumn columnName="Операция" property="3" guid="de1f591c-02b8-4875-969f-d2698689db5d" />
      <msh:tableColumn columnName="Операция" property="4" guid="de1f591c-02b8-4875-969f-d2698689db5d" />
      <msh:tableColumn columnName="Кол-во операций" property="5" guid="7f73955-a5cb-4497-bd0b-f4d05848f049" />
    </msh:table>
    </msh:sectionContent>    
    </msh:section>
    <%}
    if (view!=null&&view.equals("3")) {
    %>
    <msh:section>
    <msh:sectionTitle>Разбивка по отделениям и операциям</msh:sectionTitle>
    <msh:sectionContent>
    <ecom:webQuery name="journal_surOperationBySpec"  nativeSql="select 
    '${departmentSql} ${specSql}:'||'${dateBegin}:${dateEnd}:'||''||':'||so.medservice_id||':'||so.department_id as id
    ,dep.name as depname
    , vo.code as vocode, vo.name as voname,count(*) as cnt 
    from SurgicalOperation so
left join medservice vo on vo.id=so.medService_id
left join mislpu dep on dep.id=so.department_id
where so.operationDate between to_date('${dateBegin}','dd.mm.yyyy') 
and to_date('${dateEnd}','dd.mm.yyyy') ${department} ${spec}
group by  ${group1} so.department_id,dep.name ${group2}
order by ${order1} dep.name ${order2}" guid="4a720225-8d94-4b47-bef3-4dbbe79eec74" />
    <msh:table name="journal_surOperationBySpec" viewUrl="journal_surOperationByDate.do?short=Short" action="journal_surOperationByDate.do?dateSearch=${dateSearch}" idField="1" guid="b621e361-1e0b-4ebd-9f58-b7d919b45bd6">
      <msh:tableColumn columnName="Отделение" property="2" guid="de1f591c-02b8-4875-969f-d2698689db5d" />
      <msh:tableColumn columnName="Код" property="3" guid="de1f591c-02b8-4875-969f-d2698689db5d" />
      <msh:tableColumn columnName="Операция" property="4" guid="de1f591c-02b8-4875-969f-d2698689db5d" />
      <msh:tableColumn columnName="Количество операций" property="5" guid="7f73955-a5cb-4497-bd0b-f4d05848f049" />
    </msh:table>
    </msh:sectionContent>    
    </msh:section>
    <%}
    if (view!=null&&view.equals("4")) {
    %>
    <msh:section>
    <msh:sectionTitle>Разбивка по операциям</msh:sectionTitle>
    <msh:sectionContent>
    <ecom:webQuery name="journal_surOperationBySpec" nativeSql="select 
    '${departmentSql} ${specSql}:'||'${dateBegin}:${dateEnd}::'||so.medservice_id as id
    , vo.code as vocode, vo.name as voname,count(*) as cnt 
    from SurgicalOperation so
left join medservice vo on vo.id=so.medService_id
left join workfunction wf on wf.id=so.surgeon_id
left join worker w on w.id=wf.worker_id
left join patient p on p.id=w.person_id
left join vocworkfunction vwf on vwf.id=wf.workFunction_id
where so.operationDate between to_date('${dateBegin}','dd.mm.yyyy') 
and to_date('${dateEnd}','dd.mm.yyyy') ${department} ${spec}
group by so.medservice_id, vo.code,vo.name 

order by vo.name" guid="4a720225-8d94-4b47-bef3-4dbbe79eec74" />
    <msh:table name="journal_surOperationBySpec" viewUrl="journal_surOperationByDate.do?short=Short" action="journal_surOperationByDate.do?dateSearch=${dateSearch}" idField="1" guid="b621e361-1e0b-4ebd-9f58-b7d919b45bd6">
      <msh:tableColumn columnName="Код" property="2" guid="de1f591c-02b8-4875-969f-d2698689db5d" />
      <msh:tableColumn columnName="Операция" property="3" guid="de1f591c-02b8-4875-969f-d2698689db5d" />
      <msh:tableColumn columnName="Количество операций" property="4" guid="7f73955-a5cb-4497-bd0b-f4d05848f049" />
    </msh:table>
    </msh:sectionContent>    
    </msh:section>
    <%
    }
    if (view!=null&&view.equals("5")) {
    %>
    <msh:section>
    <msh:sectionTitle>Реестр хирургических операций</msh:sectionTitle>
    <msh:sectionContent>
	    <ecom:webQuery name="journal_surOperation1" nativeSql="select so.id as id
	    ,coalesce(to_char(so.operationDate,'DD.MM.YYYY')||' '||to_char(so.operationTime,'HH24:MI')||' - '||to_char(so.operationDateTo,'DD.MM.YYYY')||' '||to_char(so.operationTimeTo,'HH24:MI'),to_char(so.operationDate,'DD.MM.YYYY')) as operDate
	    , vo.name as voname
	    ,(select list(' '||vwf.name||' '||wp.lastname||' '||wp.firstname||' '||wp.middlename) from SurgicalOperation_WorkFunction sowf left join WorkFunction wf on wf.id=sowf.surgeonFunctions_id left join Worker w on w.id=wf.worker_id left join Patient wp on wp.id=w.person_id left join vocworkFunction vwf on vwf.id=wf.workFunction_id where sowf.SurgicalOperation_id=so.id ) as surgOper 
	    ,p.lastname||' '||p.firstname||' '||p.middlename ||' гр '||to_char(p.birthday,'DD.MM.YYYY') as patientInfo,
	    (select list(' '||vam.name|| ' '|| a.duration||' мин '||vwf.name||' '||wp.lastname||' '||wp.firstname||' '||wp.middlename) as aneth 
	    from Anesthesia a
	    left join VocAnesthesiaMethod vam on vam.id=a.method_id
	    left join WorkFunction wf on wf.id=a.anesthesist_id
	    left join VocWorkFunction vwf on vwf.id=wf.workFunction_id
	    left join Worker w on w.id=wf.worker_id
	    left join Patient wp on wp.id=w.person_id
	    
	    where a.surgicalOperation_id=so.id
	    )
	     ,vas.name as vasname
	     ,svwf.name||' '||swp.lastname||' '||swp.firstname||' '||swp.middlename as surinfo
	     from SurgicalOperation so

	    left join WorkFunction swf on swf.id=so.surgeon_id
	    left join VocWorkFunction svwf on svwf.id=swf.workFunction_id
	    left join Worker sw on sw.id=swf.worker_id
	    left join Patient swp on swp.id=sw.person_id

	      left join Patient p on p.id=so.patient_id
	      left join VocAdditionStatus vas on vas.id=p.additionStatus_id
	      left join MedService vo on vo.id=so.medService_id
	       where operationDate 
	        between to_date('${dateBegin}','dd.mm.yyyy')
	          and to_date('${dateEnd}','dd.mm.yyyy')   ${department} ${spec}
	          order by ${order1} p.lastname,p.firstname,p.middlename ${order2}
	        " guid="4a720225-8d94-4b47-bef3-4dbbe79eec74" />
	    <msh:table name="journal_surOperation1" 
	    action="entityView-stac_surOperation.do" idField="1" 
	    viewUrl="entityShortView-stac_surOperation.do"
	    >
	      <msh:tableColumn columnName="#" property="sn" guid="de1f591c-02b8-4875-969f-d2698689db5d" />
	      <msh:tableColumn columnName="Статус пациента" property="7"/>
	      <msh:tableColumn columnName="Пациент" property="5"/>
	      <msh:tableColumn columnName="Период операции" property="2" guid="de1f591c-02b8-4875-969f-d2698689db5d" />
	      <msh:tableColumn columnName="Хирург" property="8"/>
	      <msh:tableColumn columnName="Ассистенты" property="4"/>
	      <msh:tableColumn columnName="Операции" property="3"/>
	      <msh:tableColumn columnName="Анестезия" property="6"/>
	    </msh:table>
    </msh:sectionContent>
    </msh:section>
    <%} %>
    <% } else {%>
    	<i>Нет данных </i>
    	<% }   %>
    
  </tiles:put>
</tiles:insert>