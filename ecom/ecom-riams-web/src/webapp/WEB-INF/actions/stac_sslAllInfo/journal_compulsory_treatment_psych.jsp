<%@page import="ru.ecom.web.util.ActionUtil"%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://struts.apache.org/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://www.nuzmsh.ru/tags/msh" prefix="msh" %>
<%@ taglib uri="http://www.ecom-ast.ru/tags/ecom" prefix="ecom" %>
<%@ taglib tagdir="/WEB-INF/tags" prefix="tags" %>

<tiles:insert page="/WEB-INF/tiles/mainLayout.jsp" flush="true" >

  <tiles:put name="title" type="string">
    <msh:title guid="helloItle-123" mainMenu="Journals" title="Журнал по принудительному лечению"/>
  </tiles:put>
  <tiles:put name="side" type="string">
  	
    
  </tiles:put>
  <tiles:put name="body" type="string">
  <%
  
	String typeAdmissionOrder =ActionUtil.updateParameter("Hospital_Reestr_Psych1","typeAdmissionOrder","1", request) ;
	String typeDate =ActionUtil.updateParameter("Hospital_Reestr_Psych1","typeDate","1", request) ;
	//String typeDirect =ActionUtil.updateParameter("Hospital_Reestr_Psych","typeAdmissionOrder","2", request) ;
	String typeEmergency =ActionUtil.updateParameter("Hospital_Reestr_Psych1","typeEmergency","3", request) ;
	String typeView =ActionUtil.updateParameter("Hospital_Reestr_Psych1","typeView","2", request) ;
  %>
  
    <msh:form action="/stac_journal_compulsory_treatment_psych.do" defaultField="dateBegin" disableFormDataConfirm="true" method="GET" guid="d7b31bc2-38f0-42cc-8d6d-19395273168f">
    <msh:panel guid="6ae283c8-7035-450a-8eb4-6f0f7da8a8ff">
    
      <msh:row guid="53627d05-8914-48a0-b2ec-792eba5b07d9">
        <msh:separator label="Параметры поиска" colSpan="7" guid="15c6c628-8aab-4c82-b3d8-ac77b7b3f700" />
      </msh:row>
      <msh:row>
        <td class="label" title="Поиск по дате (typeDate)" colspan="1"><label for="typeDateName" id="typeDateLabel">Дата:</label></td>
        <td onclick="this.childNodes[1].checked='checked';"  colspan="2">
        	<input type="radio" name="typeDate" value="1">  поступления
        </td>
        <td onclick="this.childNodes[1].checked='checked';"  colspan="2">
        	<input type="radio" name="typeDate" value="2" >  выбытия
        </td>
      </msh:row>
      <msh:row>
        <td class="label" title="Поиск по показаниям поступления (typeEmergency)" colspan="1"><label for="typeEmergencyName" id="typeEmergencyLabel">Показания:</label></td>
        <td onclick="this.childNodes[1].checked='checked';"  colspan="2">
        	<input type="radio" name="typeEmergency" value="1">  экстренные
        </td>
        <td onclick="this.childNodes[1].checked='checked';"  colspan="2">
        	<input type="radio" name="typeEmergency" value="2" >  плановые
        </td>
        <td onclick="this.childNodes[1].checked='checked';"  colspan="2">
        	<input type="radio" name="typeEmergency" value="3">  все
        </td>
      </msh:row>      
      <msh:row>
        <td class="label" title="Поиск по показаниям поступления (typeAdmissionOrder)" colspan="1"><label for="typeAdmissionOrderName" id="typeAdmissionOrderLabel">Поступил:</label></td>
        <td onclick="this.childNodes[1].checked='checked';"  colspan="2">
        	<input type="radio" name="typeAdmissionOrder" value="1">  находящихся в стационаре
        </td>
        <td onclick="this.childNodes[1].checked='checked';"  colspan="2">
        	<input type="radio" name="typeAdmissionOrder" value="2" >  выписанных
        </td>
      </msh:row>
      <msh:row>
        <td></td>
        <td onclick="this.childNodes[1].checked='checked';"  colspan="2">
        	<input type="radio" name="typeAdmissionOrder" value="3"> переведенных
        </td>
      </msh:row>

        <msh:row>
	        <td class="label" title="Просмотр данных (typeView)" colspan="1"><label for="typeViewName" id="typeViewLabel">Отобразить:</label></td>
	        <td onclick="this.childNodes[1].checked='checked';"  colspan="2">
	        	<input type="radio" name="typeView" value="1">  реестр
	        </td>
	        <td onclick="this.childNodes[1].checked='checked';"  colspan="4">
	        	<input type="radio" name="typeView" value="2">  свод по отделениям 
	        </td>
      </msh:row>

      <msh:row>
        <msh:textField fieldColSpan="2" property="dateBegin" label="Период с" guid="8d7ef035-1273-4839-a4d8-1551c623caf1" />
        <msh:textField property="dateEnd" label="по" guid="f54568f6-b5b8-4d48-a045-ba7b9f875245" />
      </msh:row>
        <msh:row>
        	<msh:autoComplete property="department" fieldColSpan="7" horizontalFill="true" label="Отделение" vocName="lpu"/>
        </msh:row>
      <msh:row>
           <td colspan="11">
            <input type="submit" onclick="find()" value="Найти" />
          </td>
      </msh:row>
      
    </msh:panel>
    </msh:form>
    <script type='text/javascript'>
    
    checkFieldUpdate('typeEmergency','${typeEmergency}',3) ;
    checkFieldUpdate('typeView','${typeView}',1) ;
    checkFieldUpdate('typeDate','${typeDate}',1) ;
    checkFieldUpdate('typeAdmissionOrder','${typeAdmissionOrder}',1) ;
  
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
   	//frm.action='expert_journal_ker.do' ;
   }
			 
   
    </script>

    
    <%
    String date = (String)request.getParameter("dateBegin") ;
    String date1 = (String)request.getParameter("dateEnd") ;
    
    if (date!=null && !date.equals(""))  {
    	if (date1==null ||date1.equals("")) {
    		request.setAttribute("dateEnd", date);
    	} else {
    		request.setAttribute("dateEnd", date1) ;
    	}
    	//String view = (String)request.getAttribute("typeView") ;
    	
    	if (typeEmergency!=null && typeEmergency.equals("1")) {
    		request.setAttribute("emergencySql", " and sls.emergency='1' ") ;
    		request.setAttribute("emergencyInfo", ", поступивших по экстренным показаниям") ;
    	} else if (typeEmergency!=null && typeEmergency.equals("2")) {
    		request.setAttribute("emergencySql", " and (sls.emergency is null or sls.emergency='0') ") ;
    		request.setAttribute("emergencyInfo", ", поступивших по плановым показаниям") ;
    	} 
    	
    	ActionUtil.setParameterFilterSql("department","ml.id", request) ;
    	/*if (typeDate!=null && typeDate.equals("2")) {
    		request.setAttribute("dateSql", "sls.dateFinish") ;
    	} else {
    		request.setAttribute("dateSql", "sls.dateStart") ;
    	}*/
    	/*if (typeAdmissionOrder==null) typeAdmissionOrder = "1" ;
    	if (typeAdmissionOrder.equals("1")) {
    		request.setAttribute("admissionOrderSql", "and (vao.code='1' or vao.code='3')") ;
    	} else if (typeAdmissionOrder.equals("2")) {
    		request.setAttribute("admissionOrderSql", "and vao.code='2'") ;
    	} else if (typeAdmissionOrder.equals("3")) {
    		request.setAttribute("admissionOrderSql", "and vao.code='1'") ;
    	} else if (typeAdmissionOrder.equals("4")) {
    		request.setAttribute("admissionOrderSql", "and vao.code='3'") ;
    	}*/
    	%>
    	  <ecom:webQuery name="diag_typeReg_ord_sql" nativeSql="select id,name from VocDiagnosisRegistrationType where code='2'"/>
    	
 <%
 
    if (typeView!=null && (typeView.equals("1"))) {
    ActionUtil.getValueByList("diag_typeReg_ord_sql", "diag_typeReg_ord", request) ;
      	%>
    
    <msh:section title="Реестр за период ${param.dateBegin}-${param.dateEnd} ${emergencyInfo}">
    <ecom:webQuery nameFldSql="reestr_sql" name="journal_reestr" nativeSql="
    select ct.id as ctid,sls.id as slsid,pat.lastname||' '||pat.firstname||' '||pat.middlename as fio
,pat.birthday as birthday,sls.datestart, ml.name as mlname,slo.id as sloid,
ct.registrationDate as ctregistrationDate
,list(distinct case when vdrt.id='${diag_typeReg_ord}' then mkb.code else null end) as mkbcode

 from compulsorytreatment ct 
left join PsychiatricCareCard pcc on pcc.id=ct.careCard_id
left join patient pat on pat.id=pcc.patient_id
left join MedCase sls on sls.patient_id=pat.id and sls.dtype='HospitalMedCase' and sls.dateFinish is null and sls.deniedhospitalizating_id is null
left join MedCase slo on slo.parent_id=sls.id and slo.dtype='DepartmentMedCase' and slo.transferDate is null
left join MisLpu ml on ml.id=slo.department_id
where ct.datereplace is null
and ct.kind_id in (2,3)
    ${emergencySql} ${departmentSql} ${admissionOrderSql}
    


    " guid="4a720225-8d94-4b47-bef3-4dbbe79eec74" />
    <msh:sectionTitle>
    
    <form action="print-stac_journal_compulsory_treatment_psych_r.do" method="post" target="_blank">
    Реестр с ${param.dateBegin} по ${dateEnd}.
    <input type='hidden' name="sqlText" id="sqlText" value="${reestr_sql}"> 
    <input type='hidden' name="sqlInfo" id="sqlInfo" value="Период с ${param.dateBegin} по ${param.dateEnd}.">
    <input type='hidden' name="sqlColumn" id="sqlColumn" value="">
    <input type='hidden' name="s" id="s" value="PrintService">
    <input type='hidden' name="m" id="m" value="printNativeQuery">
    <input type="submit" value="Печать"> 
    </form>
    </msh:sectionTitle>
    <msh:sectionContent>
    <msh:table name="journal_reestr"
    viewUrl="entityParentView-stac_ssl.do?short=Short" 
     action="entityParentView-stac_ssl.do" idField="1" >
      <msh:tableColumn columnName="#" property="sn" />
      <msh:tableColumn columnName="№Стат карты" property="2" />
      <msh:tableColumn columnName="ФИО пациента" property="3" />
      <msh:tableColumn columnName="Дата рождения" property="4" />
      <msh:tableColumn columnName="Диагноз" property="5" />
      <msh:tableColumn columnName="Дата поступления" property="6" />
      <msh:tableColumn columnName="Кем направлен" property="7" />
      <msh:tableColumn columnName="Отделение поступления" property="8" />
      <msh:tableColumn columnName="Порядок поступления (статья)" property="9" />
      <msh:tableColumn columnName="Решение по статье 35" property="10" />
      <msh:tableColumn columnName="Отделение выписки или нахождения" property="11" />
    </msh:table>
    </msh:sectionContent>
    </msh:section>

    <%} else if (typeView!=null && (typeView.equals("2"))) {%>
    
    <msh:section>
    <msh:sectionTitle>Свод по отделениям за период ${param.dateBegin}-${dateEnd} ${emergencyInfo}.</msh:sectionTitle>
    <msh:sectionContent>
   </msh:sectionContent></msh:section>
   
    <% }  %>
    <% 
    } else {%>
    	<i>Нет данных </i>
    	<% 
    	}%>
    
  </tiles:put>
</tiles:insert>