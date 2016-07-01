<%@page import="ru.ecom.mis.ejb.service.patient.HospitalLibrary"%>
<%@page import="ru.ecom.web.util.ActionUtil"%>
<%@page import="java.util.Calendar"%>
<%@page import="ru.nuzmsh.util.format.DateFormat"%>
<%@page import="java.util.Date"%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://struts.apache.org/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://www.nuzmsh.ru/tags/msh" prefix="msh" %>
<%@ taglib uri="http://www.ecom-ast.ru/tags/ecom" prefix="ecom" %>
<%@ taglib tagdir="/WEB-INF/tags" prefix="tags" %>

<tiles:insert page="/WEB-INF/tiles/mainLayout.jsp" flush="true" >

    <tiles:put name='title' type='string'>
        <msh:title mainMenu="Patient">Отчет по диагнозам перевода</msh:title>
    </tiles:put>

    <tiles:put name='side' type='string'>
        
    </tiles:put>
    
  <tiles:put name="body" type="string">
  <%
	//String typeView =ActionUtil.updateParameter("Report_hospital_standart","typeView","1", request) ;
  

  %>
  
    <msh:form action="/mis_diagnosis_by_period.do" defaultField="beginDate" disableFormDataConfirm="true" method="GET" guid="d7b31bc2-38f0-42cc-8d6d-19395273168f">
    <msh:panel guid="6ae283c8-7035-450a-8eb4-6f0f7da8a8ff">
      <msh:row guid="53627d05-8914-48a0-b2ec-792eba5b07d9">
        <msh:separator label="Параметры поиска" colSpan="7" guid="15c6c628-8aab-4c82-b3d8-ac77b7b3f700" />
      </msh:row>
        <msh:row>
        <msh:textField property="beginDate" label="Период с" guid="8d7ef035-1273-4839-a4d8-1551c623caf1" />
        <msh:textField property="endDate" label="по" guid="f54568f6-b5b8-4d48-a045-ba7b9f875245" />
		<td>
            <input type="submit" onclick="find()" value="Найти" />
          </td>
                  </msh:row>
    </msh:panel>
    </msh:form>
    
    <%
    String date = (String)request.getParameter("beginDate") ;
    if (date!=null && !date.equals(""))  {
    	String dateEnd = (String)request.getParameter("endDate") ;
    	if (dateEnd==null||dateEnd.equals("")) {
    		request.setAttribute("endDate", date) ;
    	} else {
    		request.setAttribute("endDate", dateEnd) ;
    	}
    	request.setAttribute("beginDate", date) ;
    	%>
    		<ecom:webQuery nameFldSql="reestr_sql" name="reestr" nativeSql="
    	select pat.id,pat.lastname||' '||pat.firstname||' '||pat.middlename as fio,to_char(pat.birthday,'dd.mm.yyyy') as birthday
,list(distinct to_char(diag1.establishdate,'dd.mm.yyyy')||' '||mkb1.code)
,to_char(diag.establishdate,'dd.mm.yyyy')||' '||mkb.code
,(
select ml.name||' '||to_char(sls.datestart,'dd.mm.yyyy')||'-'||coalesce(to_char(sls.datefinish,'dd.mm.yyyy'),'В отделение')
from medcase sls
left join medcase slo on slo.parent_id=sls.id and slo.dtype='DepartmentMedCase' and slo.transferdate is null
left join mislpu ml on ml.id=slo.department_id
where sls.patient_id=pat.id and upper(sls.dtype)='HOSPITALMEDCASE'
) as listhosp
,list(distinct case when diag.establishdate between lpupcc.startdate and coalesce(lpupcc.finishdate,lpupcc.transferdate,current_date) then to_char(lpupcc.startdate,'dd.mm.yyyy') ||'-'|| coalesce(to_char(coalesce(lpupcc.finishdate,lpupcc.transferdate),'dd.mm.yyyy'),'по наст.время') ||' '||vpac.name else null end) as listob
,list(distinct case when current_date between lpupcc.startdate and coalesce(lpupcc.finishdate,lpupcc.transferdate,current_date) then to_char(lpupcc.startdate,'dd.mm.yyyy') ||'-'|| coalesce(to_char(coalesce(lpupcc.finishdate,lpupcc.transferdate),'dd.mm.yyyy'),'по наст.время') ||' '||vpac.name else null end) as listob1

 from diagnosis diag
left join patient pat on pat.id=diag.patient_id
left join PsychiatricCareCard pcc on pcc.patient_id=pat.id
left join LpuAreaPsychCareCard lpupcc on lpupcc.carecard_id=pcc.id
left join PsychiaticObservation po on po.lpuAreaPsychCareCard_id = lpupcc.id
left join VocPsychAmbulatoryCare vpac on vpac.id = po.AmbulatoryCare_id
left join vocidc10 mkb on mkb.id=diag.idc10_id
left join diagnosis diag1 on diag1.patient_id=diag.patient_id and diag1.medcase_id is null
left join vocidc10 mkb1 on mkb1.id=diag1.idc10_id
where diag.medcase_id is null and diag.establishdate between 
to_date('${beginDate}','dd.mm.yyyy')  and to_date('${endDate}','dd.mm.yyyy') 
and (mkb.code like 'F20.%' or mkb.code like 'F21.%' or mkb.code like 'F23.%' or mkb.code like 'F25.%')
and diag.establishdate>diag1.establishdate
and (mkb1.code like 'F70.%' or mkb1.code like 'F71.%' or mkb1.code like 'F78.%' or mkb1.code like 'F79.%'
or mkb1.code like 'F80.%' or mkb1.code like 'F83.%' or mkb1.code like 'F84.%' or mkb1.code between 'F88' and 'F94.999' or mkb1.code like 'F98.%')
group by pat.id,pat.lastname,pat.firstname,pat.middlename
,diag.establishdate,mkb.code     
    	"/>
    	    <form action="print-stac_report_standart_reestr.do" method="post" target="_blank">
    Период с ${param.beginDate} по ${endDate}
    <input type='hidden' name="sqlText" id="sqlText" value="${reestr_sql}"> 
    <input type='hidden' name="sqlInfo" id="sqlInfo" value="">
    <input type='hidden' name="s" id="s" value="PrintService">
    <input type='hidden' name="m" id="m" value="printNativeQuery">
    <input type="submit" value="Печать"> 
    </form>
    	
    	<msh:table name="reestr" 
    	action="entityParentView-mis_patient.do" viewUrl="mis_patient.do?short=Short" idField="1">
    		<msh:tableColumn property="sn" columnName="#"/>
    		<msh:tableColumn property="2" columnName="ФИО пациента"/>
    		<msh:tableColumn property="3" columnName="Дата рождения"/>
    		<msh:tableColumn property="4" columnName="Дата пост. диагноза"/>
    		<msh:tableColumn property="5" columnName="Дата смены диагноза"/>
    		<msh:tableColumn property="6" columnName="Госпитализации"/>
    		<msh:tableColumn property="7" columnName="На дату смены вид наблюдения"/>
    		<msh:tableColumn property="8" columnName="На тек.момент вид наблюдения"/>
    	</msh:table>
    	<%
    	} else {%>
    	<i>Выберите параметры и нажмите найти </i>
    	<% }   %>
     
    <script type='text/javascript'>
    checkFieldUpdate('typeDate','${typeDate}',2) ;
    checkFieldUpdate('typePatient','${typePatient}',4) ;
    checkFieldUpdate('typeEmergency','${typeEmergency}',4) ;
    checkFieldUpdate('typeStandart','${typeStandart}',2) ;
    checkFieldUpdate('typeViewDepartment','${typeViewDepartment}',1) ;
    checkFieldUpdate('typeViewBed','${typeViewBed}',1) ;
    checkFieldUpdate('typeView','${typeView}',2) ;
    
    function checkFieldUpdate(aField,aValue,aDefaultValue) {
       	eval('var chk =  document.forms[0].'+aField) ;
       	//alert(aField+" "+aValue+" "+aMax+" "+chk) ;
       	aValue=+aValue ;
       	var max=chk.length ;
       	if (aValue==0 || (aValue)>(max)) {
       		chk[+aDefaultValue-1].checked='checked' ;
       	} else {
       		chk[+aValue-1].checked='checked' ;
       	}
    }
  	</script>
  </tiles:put>
</tiles:insert>

