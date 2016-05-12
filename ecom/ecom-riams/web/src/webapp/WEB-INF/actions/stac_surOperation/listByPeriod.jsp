<%@page import="ru.ecom.mis.web.action.medcase.journal.AdmissionJournalForm"%>
<%@page import="ru.ecom.web.util.ActionUtil"%>
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
  
  String typeEndoscopyUse = ActionUtil.updateParameter("SurgicalOperation","typeEndoscopyUse","3", request) ;
  String typeEmergency = ActionUtil.updateParameter("SurgicalOperation","typeEmergency","3", request) ;
  if (request.getParameter("short")==null) {
	  //ActionUtil.updateParameter("SurgicalOperation","typeEndoscopyUse","3", request) ;
	  ActionUtil.updateParameter("SurgicalOperation","typeView","1", request) ;
	  
	  ActionUtil.updateParameter("SurgicalOperation","typeOrder","1", request) ;
	  ActionUtil.updateParameter("SurgicalOperation","typeDate","1", request) ;
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
     </msh:row>
     <msh:row>
     	<td></td>
        <td onclick="this.childNodes[1].checked='checked';">
        	<input type="radio" name="typeView" value="5">  свод по отделениям
        </td>
        <td onclick="this.childNodes[1].checked='checked';" colspan="2">
        	<input type="radio" name="typeView" value="6">  свод по хирургам и ур.сл.опер.
        </td>
        <td onclick="this.childNodes[1].checked='checked';">
        	<input type="radio" name="typeView" value="7">  свод по отделению и ур.сл.опер.
        </td>
        <td onclick="this.childNodes[1].checked='checked';">
        	<input type="radio" name="typeView" value="8">  реестр
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
      <msh:row guid="7d80be13-710c-46b8-8503-ce0413686b69">
        <td class="label" title="Искать по дате (typeDate)" colspan="1"><label for="typeDateName" id="typeDateLabel">Искать по дате:</label></td>
        <td onclick="this.childNodes[1].checked='checked';">
        	<input type="radio" name="typeDate" value="1">  операции
        </td>
        <td onclick="this.childNodes[1].checked='checked';" colspan="2">
        	<input type="radio" name="typeDate" value="2">  выписки (учит. операции в отд.)
        </td>
        <td onclick="this.childNodes[1].checked='checked';" colspan="2">
        	<input type="radio" name="typeDate" value="3">  выписки (учит. операции в прием.отд.)
        </td>
      </msh:row>
      <msh:row>
      <msh:row>
        <td class="label" title="Экстренность (typeEmergency)" colspan="1"><label for="typeEmergencyName" id="typeEmergencyLabel">Порядок поступления.:</label></td>
        <td onclick="this.childNodes[1].checked='checked';"  colspan="2">
        	<input type="radio" name="typeEmergency" value="1">  экстренно
        </td>
        <td onclick="this.childNodes[1].checked='checked';"  colspan="2">
        	<input type="radio" name="typeEmergency" value="2" >  планово
        </td>
        <td onclick="this.childNodes[1].checked='checked';"  colspan="2">
        	<input type="radio" name="typeEmergency" value="3">  не учит.параметр
        </td>
      </msh:row>      	
      <msh:row>
        <td class="label" title="Поиск по эндоскопии (typeEndoscopyUse)" colspan="1"><label for="typeEndoscopyUseName" id="typeEndoscopyUseLabel">Операции с испол.:</label></td>
        <td onclick="this.childNodes[1].checked='checked';"  colspan="2">
        	<input type="radio" name="typeEndoscopyUse" value="1">  эндоскопии
        </td>
        <td onclick="this.childNodes[1].checked='checked';"  colspan="2">
        	<input type="radio" name="typeEndoscopyUse" value="2" >  без эндоскопии
        </td>
        <td onclick="this.childNodes[1].checked='checked';"  colspan="2">
        	<input type="radio" name="typeEndoscopyUse" value="3">  все
        </td>
      </msh:row>      	
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
      <msh:row>
      	<msh:autoComplete property="serviceStream" fieldColSpan="7" horizontalFill="true" vocName="vocServiceStream"/>
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
    checkFieldUpdate('typeEmergency','${typeEmergency}',3) ;
    checkFieldUpdate('typeEndoscopyUse','${typeEndoscopyUse}',3) ;
    checkFieldUpdate('typeView','${typeView}',1) ;
    checkFieldUpdate('typeOrder','${typeOrder}',1) ;
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
    ActionUtil.setParameterFilterSql("serviceStream", "so.serviceStream_id", request) ;
    String view = (String)request.getAttribute("typeView") ;
    String typeOrder = (String)request.getAttribute("typeOrder") ;
    String typeEndoscopyUseSql=""; 
    if (typeEndoscopyUse!=null && typeEndoscopyUse.equals("1")) {
    	typeEndoscopyUseSql=" and so.endoscopyUse='1'" ;
    } else if (typeEndoscopyUse!=null && typeEndoscopyUse.equals("2")) {
    	typeEndoscopyUseSql= "and (so.endoscopyUse='0' or so.endoscopyUse is null)" ;
    }
    request.setAttribute("typeEndoscopyUseSql", typeEndoscopyUseSql) ;
	String typeDate=ActionUtil.updateParameter("SurgicalOperation","typeDate","1", request) ;
	if (typeEmergency.equals("1")) {
		if (typeDate!=null && typeDate.equals("2")) {
			request.setAttribute("typeEmergencySql", " and sls.emergency='1'") ;
    	} else if (typeDate!=null && typeDate.equals("3")) {
    		request.setAttribute("typeEmergencySql", " and slsHosp.emergency='1')") ;
    	} else {
    		request.setAttribute("typeEmergencySql", " and (slo.datestart is not null and sls.emergency='1' or slo.datestart is null and slsHosp.emergency='1')") ;
    	}
	} else if (typeEmergency.equals("2")) {
		
		if (typeDate!=null && typeDate.equals("2")) {
			request.setAttribute("typeEmergencySql", " and (sls.emergency='0' or sls.emergency is null)") ;
    	} else if (typeDate!=null && typeDate.equals("3")) {
    		request.setAttribute("typeEmergencySql", " and (slsHosp.emergency='0' or slsHosp.emergency is null)") ;
    	} else {
    		request.setAttribute("typeEmergencySql", " and (slo.datestart is not null and (sls.emergency='0' or sls.emergency is null) or slo.datestart is null and (slsHosp.emergency='0' or slsHosp.emergency is null))") ;
    	}
		
	}

    if (date!=null && !date.equals("")) {
    	String typeDateSql = "so.operationDate" ;
    	//String typeDate=ActionUtil.updateParameter("SurgicalOperation","typeDate","1", request) ;
    	if (typeDate!=null && typeDate.equals("2")) {
    		typeDateSql = "sls.dateFinish" ;
    	} else if (typeDate!=null && typeDate.equals("3")) {
    		typeDateSql = "slsHosp.dateFinish" ;
    	} 
    	request.setAttribute("typeDateSql", typeDateSql);
    	
        AdmissionJournalForm frm = (AdmissionJournalForm) session.getAttribute("stac_admissionJournalForm");
        //String department = (String)request.getAttribute("department") ;
        //String spec = (String)request.getAttribute("spec") ;
    	if (typeOrder!=null) {
    		String group1 = "so.medservice_id,vo.id,vo.name,vo.code,vo.complexity" ;
    		String orderName = "vo.name" ;
    		String orderId = "vo.id" ;
    		if (view!=null &&(view.equals("6")||view.equals("7"))) {
    			orderId="vo.complexity" ;orderName="vo.complexity" ;
    			group1 = "vo.complexity" ;
    		} 
    		if (typeOrder.equals("1")) {
	    		request.setAttribute("order1", orderName+",") ;
	    		request.setAttribute("order2", "") ;
	    		request.setAttribute("group1", group1+",") ;
	    		request.setAttribute("group2", "") ;
	    	} else if (typeOrder.equals("2")) {
	    		request.setAttribute("order1", orderId+",") ;
	    		request.setAttribute("order2", "") ;
	    		request.setAttribute("group1", group1+",") ;
	    		request.setAttribute("group2", "") ;
	    	} else if (typeOrder.equals("3")) {
	    		request.setAttribute("order1", "") ;
	    		request.setAttribute("order2", ","+orderName) ;
	    		request.setAttribute("group1", "") ;
	    		request.setAttribute("group2", ","+group1) ;
	    	} else if (typeOrder.equals("4")) {
	    		request.setAttribute("order1", "") ;
	    		request.setAttribute("order2", ","+orderId) ;
	    		request.setAttribute("group1", "") ;
	    		request.setAttribute("group2", ","+group1) ;
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
    select '${departmentSql} ${specSql}:'||to_char(${typeDateSql},'dd.mm.yyyy')||':'||to_char(${typeDateSql},'dd.mm.yyyy')
    ,to_char(${typeDateSql},'dd.mm.yyyy')
    , count(so.id) as cntOper
    , count(case when so.endoscopyUse='1' then so.id else null end) as cntEndoscopyUse
     from SurgicalOperation so 
     left join MedCase slo on slo.id=so.medCase_id and slo.dtype='DepartmentMedCase'
     left join MedCase sls on sls.id=slo.parent_id and sls.dtype='HospitalMedCase'
     left join MedCase slsHosp on slsHosp.id=so.medCase_id and slsHosp.dtype='HospitalMedCase'
     where 
    ${typeDateSql}  between to_date('${dateBegin}','dd.mm.yyyy') 
    and to_date('${dateEnd}','dd.mm.yyyy') ${department} ${spec} ${typeEndoscopyUseSql} ${typeEmergencySql} ${serviceStreamSql}
    
    group by ${typeDateSql} 
    order by ${typeDateSql}" />
    <msh:table name="journal_surOperation" viewUrl="journal_surOperationByDate.do?short=Short&dateSearch=${dateSearch}&typeDate=${param.typeDate}&typeEndoscopyUse=${param.typeEndoscopyUse}&typeEmergency=${param.typeEmergency}&serviceStream=${param.serviceStream}"  action="journal_surOperationByDate.do?dateSearch=${dateSearch}&typeDate=${param.typeDate}&typeEndoscopyUse=${param.typeEndoscopyUse}&typeEmergency=${param.typeEmergency}&serviceStream=${param.serviceStream}" idField="1" guid="b621e361-1e0b-4ebd-9f58-b7d919b45bd6">
      <msh:tableColumn columnName="Дата" property="2" guid="de1f591c-02b8-4875-969f-d2698689db5d" />
      <msh:tableColumn isCalcAmount="true" columnName="Количество операций" identificator="false" property="3" />
      <msh:tableColumn isCalcAmount="true" columnName="из них с испол. эндоскопии" property="4" />
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
    , vo.code as vocode, vo.name as voname,vo.complexity,count(*) as cnt 
    , count(case when so.endoscopyUse='1' then so.id else null end) as cntEndoscopyUse
    
    from SurgicalOperation so
left join medservice vo on vo.id=so.medService_id
left join workfunction wf on wf.id=so.surgeon_id
left join worker w on w.id=wf.worker_id
left join patient p on p.id=w.person_id
left join vocworkfunction vwf on vwf.id=wf.workFunction_id
     left join MedCase slo on slo.id=so.medCase_id and slo.dtype='DepartmentMedCase'
     left join MedCase sls on sls.id=slo.parent_id and sls.dtype='HospitalMedCase'
     left join MedCase slsHosp on slsHosp.id=so.medCase_id and slsHosp.dtype='HospitalMedCase'
where ${typeDateSql} between to_date('${dateBegin}','dd.mm.yyyy') 
and to_date('${dateEnd}','dd.mm.yyyy') ${department} ${spec} ${typeEndoscopyUseSql} ${typeEmergencySql} ${serviceStreamSql}
group by ${group1}so.surgeon_id,vwf.name,p.lastname, p.firstname, p.middlename ${group2}
order by ${order1} p.lastname,p.firstname,p.middlename ${order2}" guid="4a720225-8d94-4b47-bef3-4dbbe79eec74" />
    <msh:table name="journal_surOperationBySpec" viewUrl="journal_surOperationByDate.do?short=Short&dateSearch=${dateSearch}&typeDate=${param.typeDate}&typeEndoscopyUse=${param.typeEndoscopyUse}&typeEmergency=${param.typeEmergency}&serviceStream=${param.serviceStream}" action="journal_surOperationByDate.do?dateSearch=${dateSearch}&typeDate=${param.typeDate}&typeEndoscopyUse=${param.typeEndoscopyUse}&typeEmergency=${param.typeEmergency}&serviceStream=${param.serviceStream}" idField="1" guid="b621e361-1e0b-4ebd-9f58-b7d919b45bd6">
      <msh:tableColumn columnName="Специалист" property="2" guid="de1f591c-02b8-4875-969f-d2698689db5d" />
      <msh:tableColumn columnName="Операция" property="3" guid="de1f591c-02b8-4875-969f-d2698689db5d" />
      <msh:tableColumn columnName="Операция" property="4" guid="de1f591c-02b8-4875-969f-d2698689db5d" />
      <msh:tableColumn columnName="Уровень сложности" property="5" guid="de1f591c-02b8-4875-969f-d2698689db5d" />
      <msh:tableColumn isCalcAmount="true" columnName="Кол-во операций" property="6" guid="7f73955-a5cb-4497-bd0b-f4d05848f049" />
      <msh:tableColumn isCalcAmount="true" columnName="из них с испол. эндоскопии" property="7" guid="7f73955-a5cb-4497-bd0b-f4d05848f049" />
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
    , vo.code as vocode, vo.name as voname,vo.complexity,count(*) as cnt
    , count(case when so.endoscopyUse='1' then so.id else null end) as cntEndoscopyUse
     
    from SurgicalOperation so
left join medservice vo on vo.id=so.medService_id
left join mislpu dep on dep.id=so.department_id
     left join MedCase slo on slo.id=so.medCase_id and slo.dtype='DepartmentMedCase'
     left join MedCase sls on sls.id=slo.parent_id and sls.dtype='HospitalMedCase'
     left join MedCase slsHosp on slsHosp.id=so.medCase_id and slsHosp.dtype='HospitalMedCase'
where ${typeDateSql} between to_date('${dateBegin}','dd.mm.yyyy') 
and to_date('${dateEnd}','dd.mm.yyyy') ${department} ${spec} ${typeEndoscopyUseSql} ${typeEmergencySql} ${serviceStreamSql}
group by  ${group1} so.department_id,dep.name ${group2}
order by ${order1} dep.name ${order2}" guid="4a720225-8d94-4b47-bef3-4dbbe79eec74" />
    <msh:table name="journal_surOperationBySpec" viewUrl="journal_surOperationByDate.do?short=Short&typeDate=${param.typeDate}&typeEndoscopyUse=${param.typeEndoscopyUse}&typeEmergency=${param.typeEmergency}&serviceStream=${param.serviceStream}" action="journal_surOperationByDate.do?dateSearch=${dateSearch}&typeDate=${param.typeDate}&typeEndoscopyUse=${param.typeEndoscopyUse}&typeEmergency=${param.typeEmergency}&serviceStream=${param.serviceStream}" idField="1">
      <msh:tableColumn columnName="Отделение" property="2" guid="de1f591c-02b8-4875-969f-d2698689db5d" />
      <msh:tableColumn columnName="Код" property="3" guid="de1f591c-02b8-4875-969f-d2698689db5d" />
      <msh:tableColumn columnName="Операция" property="4" guid="de1f591c-02b8-4875-969f-d2698689db5d" />
      <msh:tableColumn columnName="Уровень сложности" property="5" guid="de1f591c-02b8-4875-969f-d2698689db5d" />
      <msh:tableColumn isCalcAmount="true" columnName="Количество операций" property="6" guid="7f73955-a5cb-4497-bd0b-f4d05848f049" />
      <msh:tableColumn isCalcAmount="true" columnName="из них эндоскопии" property="7"/>
    </msh:table>
    </msh:sectionContent>    
    </msh:section>
    <%}
    if (view!=null&&view.equals("5")) {
    %>
    <msh:section>
    <msh:sectionTitle>Разбивка по отделениям</msh:sectionTitle>
    <msh:sectionContent>
    <ecom:webQuery name="journal_surOperationBySpec" nativeSql="select 
    '${departmentSql} ${specSql}:'||'${dateBegin}:${dateEnd}:'||''||'::'||so.department_id as id
    ,dep.name as depname
    ,count(*) as cnt 
        , count(case when so.endoscopyUse='1' then so.id else null end) as cntEndoscopyUse
    
    from SurgicalOperation so
left join medservice vo on vo.id=so.medService_id
left join mislpu dep on dep.id=so.department_id
     left join MedCase slo on slo.id=so.medCase_id and slo.dtype='DepartmentMedCase'
     left join MedCase sls on sls.id=slo.parent_id and sls.dtype='HospitalMedCase'
     left join MedCase slsHosp on slsHosp.id=so.medCase_id and slsHosp.dtype='HospitalMedCase'
where ${typeDateSql} between to_date('${dateBegin}','dd.mm.yyyy') 
and to_date('${dateEnd}','dd.mm.yyyy') ${department} ${spec} ${typeEndoscopyUseSql} ${typeEmergencySql} ${serviceStreamSql}
group by so.department_id,dep.name
order by dep.name 
" guid="4a720225-8d94-4b47-bef3-4dbbe79eec74" />
    <msh:table name="journal_surOperationBySpec" viewUrl="journal_surOperationByDate.do?short=Short&typeDate=${param.typeDate}&typeEndoscopyUse=${param.typeEndoscopyUse}&typeEmergency=${param.typeEmergency}&serviceStream=${param.serviceStream}" action="journal_surOperationByDate.do?dateSearch=${dateSearch}&typeDate=${param.typeDate}&typeEndoscopyUse=${param.typeEndoscopyUse}&typeEmergency=${param.typeEmergency}&serviceStream=${param.serviceStream}" idField="1" guid="b621e361-1e0b-4ebd-9f58-b7d919b45bd6">
      <msh:tableColumn columnName="Отделение" property="2" />
      <msh:tableColumn isCalcAmount="true" columnName="Количество операций" property="3" />
      <msh:tableColumn isCalcAmount="true" columnName="из них с испол. эндоскопии" property="4" />
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
    , vo.code as vocode, vo.name as voname,vo.complexity,count(*) as cnt 
        , count(case when so.endoscopyUse='1' then so.id else null end) as cntEndoscopyUse
        , count(case when so.endoscopyUse='1' then so.id else null end) as cntEndoscopyUse
    
    from SurgicalOperation so
     
left join medservice vo on vo.id=so.medService_id
left join workfunction wf on wf.id=so.surgeon_id
left join worker w on w.id=wf.worker_id
left join patient p on p.id=w.person_id
left join vocworkfunction vwf on vwf.id=wf.workFunction_id
     left join MedCase slo on slo.id=so.medCase_id and slo.dtype='DepartmentMedCase'
     left join MedCase sls on sls.id=slo.parent_id and sls.dtype='HospitalMedCase'
     left join MedCase slsHosp on slsHosp.id=so.medCase_id and slsHosp.dtype='HospitalMedCase'
where ${typeDateSql} between to_date('${dateBegin}','dd.mm.yyyy') 
and to_date('${dateEnd}','dd.mm.yyyy') ${department} ${spec} ${typeEndoscopyUseSql} ${typeEmergencySql} ${serviceStreamSql}
group by so.medservice_id, vo.code,vo.name ,vo.complexity

order by vo.name" guid="4a720225-8d94-4b47-bef3-4dbbe79eec74" />
    <msh:table name="journal_surOperationBySpec" viewUrl="journal_surOperationByDate.do?short=Short&typeDate=${param.typeDate}&typeEndoscopyUse=${param.typeEndoscopyUse}&typeEmergency=${param.typeEmergency}&serviceStream=${param.serviceStream}" action="journal_surOperationByDate.do?dateSearch=${dateSearch}&typeDate=${param.typeDate}&typeEndoscopyUse=${param.typeEndoscopyUse}&typeEmergency=${param.typeEmergency}&serviceStream=${param.serviceStream}" idField="1" guid="b621e361-1e0b-4ebd-9f58-b7d919b45bd6">
      <msh:tableColumn columnName="Код" property="2" guid="de1f591c-02b8-4875-969f-d2698689db5d" />
      <msh:tableColumn columnName="Операция" property="3" guid="de1f591c-02b8-4875-969f-d2698689db5d" />
      <msh:tableColumn columnName="Уровень сложности" property="4" guid="de1f591c-02b8-4875-969f-d2698689db5d" />
      <msh:tableColumn isCalcAmount="true" columnName="Количество операций" property="5" guid="7f73955-a5cb-4497-bd0b-f4d05848f049" />
      <msh:tableColumn isCalcAmount="true" columnName="из них с испол. эндоскопии" property="6" />
    </msh:table>
    </msh:sectionContent>    
    </msh:section>
    <%
    }
    if (view!=null&&view.equals("6")) {
    %>
    <msh:section>
    <msh:sectionTitle>Разбивка по хирургам и уровням сложности операций</msh:sectionTitle>
    <msh:sectionContent>
    <ecom:webQuery name="journal_surOperationBySpec" nativeSql="select 
    '${departmentSql} ${specSql}:'||'${dateBegin}:${dateEnd}:'||so.surgeon_id||':' as id
    ,vwf.name||' '||p.lastname||' '|| p.firstname||' '|| p.middlename as doctor
   ,vo.complexity,count(*) as cnt 
       , count(case when so.endoscopyUse='1' then so.id else null end) as cntEndoscopyUse
   
    from SurgicalOperation so
left join medservice vo on vo.id=so.medService_id
left join workfunction wf on wf.id=so.surgeon_id
left join worker w on w.id=wf.worker_id
left join patient p on p.id=w.person_id
left join vocworkfunction vwf on vwf.id=wf.workFunction_id
     left join MedCase slo on slo.id=so.medCase_id and slo.dtype='DepartmentMedCase'
     left join MedCase sls on sls.id=slo.parent_id and sls.dtype='HospitalMedCase'
     left join MedCase slsHosp on slsHosp.id=so.medCase_id and slsHosp.dtype='HospitalMedCase'
where ${typeDateSql} between to_date('${dateBegin}','dd.mm.yyyy') 
and to_date('${dateEnd}','dd.mm.yyyy') ${department} ${spec} ${typeEndoscopyUseSql} ${typeEmergencySql} ${serviceStreamSql}
group by ${group1}so.surgeon_id,vwf.name,p.lastname, p.firstname, p.middlename ${group2}
order by ${order1} p.lastname,p.firstname,p.middlename ${order2}" guid="4a720225-8d94-4b47-bef3-4dbbe79eec74" />
    <msh:table name="journal_surOperationBySpec" viewUrl="journal_surOperationByDate.do?short=Short&typeDate=${param.typeDate}&typeEndoscopyUse=${param.typeEndoscopyUse}&typeEmergency=${param.typeEmergency}&serviceStream=${param.serviceStream}" action="journal_surOperationByDate.do?dateSearch=${dateSearch}&typeDate=${param.typeDate}&typeEndoscopyUse=${param.typeEndoscopyUse}&typeEmergency=${param.typeEmergency}&serviceStream=${param.serviceStream}" idField="1" guid="b621e361-1e0b-4ebd-9f58-b7d919b45bd6">
      <msh:tableColumn columnName="Специалист" property="2" guid="de1f591c-02b8-4875-969f-d2698689db5d" />
       <msh:tableColumn columnName="Уровень сложности" property="3" guid="de1f591c-02b8-4875-969f-d2698689db5d" />
      <msh:tableColumn isCalcAmount="true" columnName="Кол-во операций" property="4" guid="7f73955-a5cb-4497-bd0b-f4d05848f049" />
      <msh:tableColumn isCalcAmount="true" columnName="из них с испол. эндоскопии" property="5" />
    </msh:table>
    </msh:sectionContent>    
    </msh:section>
    <%}
    if (view!=null&&view.equals("7")) {
    %>
    <msh:section>
    <msh:sectionTitle>Разбивка по отделениям и уровням сложности операций</msh:sectionTitle>
    <msh:sectionContent>
    <ecom:webQuery name="journal_surOperationBySpec"  nativeSql="select 
    '${departmentSql} ${specSql}:'||'${dateBegin}:${dateEnd}:'||''||'::'||so.department_id as id
    ,dep.name as depname
    ,vo.complexity,count(*) as cnt 
    ,round(100*count(distinct so.id)/
cast((select count(distinct so1.id) from SurgicalOperation so1
where so1.operationDate between to_date('${dateBegin}','dd.mm.yyyy') 
and to_date('${dateEnd}','dd.mm.yyyy')  and so.department_id=so1.department_id
) as numeric) ,2) as srDep
    , count(case when so.endoscopyUse='1' then so.id else null end) as cntEndoscopyUse

    from SurgicalOperation so
left join medservice vo on vo.id=so.medService_id
left join mislpu dep on dep.id=so.department_id
     left join MedCase slo on slo.id=so.medCase_id and slo.dtype='DepartmentMedCase'
     left join MedCase sls on sls.id=slo.parent_id and sls.dtype='HospitalMedCase'
     left join MedCase slsHosp on slsHosp.id=so.medCase_id and slsHosp.dtype='HospitalMedCase'
where ${typeDateSql} between to_date('${dateBegin}','dd.mm.yyyy') 
and to_date('${dateEnd}','dd.mm.yyyy') ${department} ${spec} ${typeEndoscopyUseSql} ${typeEmergencySql} ${serviceStreamSql}
group by  ${group1} so.department_id,dep.name ${group2}
order by ${order1} dep.name ${order2}" guid="4a720225-8d94-4b47-bef3-4dbbe79eec74" />
    <msh:table name="journal_surOperationBySpec" viewUrl="journal_surOperationByDate.do?short=Short&typeDate=${param.typeDate}&typeEndoscopyUse=${param.typeEndoscopyUse}&typeEmergency=${param.typeEmergency}&serviceStream=${param.serviceStream}" action="journal_surOperationByDate.do?dateSearch=${dateSearch}&typeDate=${param.typeDate}&typeEndoscopyUse=${param.typeEndoscopyUse}&typeEmergency=${param.typeEmergency}&serviceStream=${param.serviceStream}" idField="1" guid="b621e361-1e0b-4ebd-9f58-b7d919b45bd6">
      <msh:tableColumn columnName="Отделение" property="2" guid="de1f591c-02b8-4875-969f-d2698689db5d" />
      <msh:tableColumn columnName="Уровень сложности" property="3" guid="de1f591c-02b8-4875-969f-d2698689db5d" />
      <msh:tableColumn isCalcAmount="true" columnName="Количество операций" property="4" guid="7f73955-a5cb-4497-bd0b-f4d05848f049" />
      <msh:tableColumn columnName="% от общ. числа опер. по отд." property="5"/>            
      <msh:tableColumn isCalcAmount="true" columnName="из них с испол. эндоскопии" property="6" />
    </msh:table>
    </msh:sectionContent>    
    </msh:section>
    <%}

    if (view!=null&&view.equals("8")) {
    %>
	    <ecom:webQuery name="journal_surOperation1" nameFldSql="journal_surOperation1_sql" nativeSql="select so.id as id
	    ,coalesce(to_char(${typeDateSql},'DD.MM.YYYY')||' '||to_char(so.operationTime,'HH24:MI')||' - '||to_char(${typeDateSql}To,'DD.MM.YYYY')||' '||to_char(so.operationTimeTo,'HH24:MI'),to_char(${typeDateSql},'DD.MM.YYYY')) as operDate
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
	     ,vo.complexity
	     from SurgicalOperation so

	    left join WorkFunction swf on swf.id=so.surgeon_id
	    left join VocWorkFunction svwf on svwf.id=swf.workFunction_id
	    left join Worker sw on sw.id=swf.worker_id
	    left join Patient swp on swp.id=sw.person_id

	      left join Patient p on p.id=so.patient_id
	      left join VocAdditionStatus vas on vas.id=p.additionStatus_id
	      left join MedService vo on vo.id=so.medService_id
     left join MedCase slo on slo.id=so.medCase_id and slo.dtype='DepartmentMedCase'
     left join MedCase sls on sls.id=slo.parent_id and sls.dtype='HospitalMedCase'
     left join MedCase slsHosp on slsHosp.id=so.medCase_id and slsHosp.dtype='HospitalMedCase'
	       where ${typeDateSql} 
	        between to_date('${dateBegin}','dd.mm.yyyy')
	          and to_date('${dateEnd}','dd.mm.yyyy')   ${department} ${spec} 
	          ${typeEndoscopyUseSql} ${typeEmergencySql} ${serviceStreamSql}
	          order by ${order1} p.lastname,p.firstname,p.middlename ${order2}
	        " guid="4a720225-8d94-4b47-bef3-4dbbe79eec74" />
    <msh:section>
    <msh:sectionTitle>
    
    <form action="print-stac_journal_surOperationByDate_8.do" method="post" target="_blank">
    Реестр хирургических операций с ${dateBegin} по ${dateEnd}.
    <input type='hidden' name="sqlText" id="sqlText" value="${journal_surOperation1_sql}"> 
    <input type='hidden' name="sqlInfo" id="sqlInfo" value="Реестр хирургических операций с ${dateBegin} по ${param.dateEnd}.">
    <input type='hidden' name="sqlColumn" id="sqlColumn" value="">
    <input type='hidden' name="s" id="s" value="PrintService">
    <input type='hidden' name="m" id="m" value="printNativeQuery">
    <input type="submit" value="Печать"> 
    </form></msh:sectionTitle>
    <msh:sectionContent>
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
	      <msh:tableColumn columnName="Операция" property="3"/>
	      <msh:tableColumn columnName="Уровень сложности" property="9"/>
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