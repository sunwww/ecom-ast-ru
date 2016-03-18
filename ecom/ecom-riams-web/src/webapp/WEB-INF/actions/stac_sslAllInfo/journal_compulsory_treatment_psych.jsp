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
	//String typeDate =ActionUtil.updateParameter("Hospital_Reestr_Psych1","typeDate","1", request) ;
	//String typeDirect =ActionUtil.updateParameter("Hospital_Reestr_Psych","typeAdmissionOrder","2", request) ;
	String typeEmergency =ActionUtil.updateParameter("Hospital_Reestr_Psych1","typeEmergency","3", request) ;
	String typeView =ActionUtil.updateParameter("Hospital_Reestr_Psych1","typeView","1", request) ;
  %>
  
    <msh:form action="/stac_journal_compulsory_treatment_psych.do" defaultField="dateBegin" disableFormDataConfirm="true" method="GET" guid="d7b31bc2-38f0-42cc-8d6d-19395273168f">
    <msh:panel guid="6ae283c8-7035-450a-8eb4-6f0f7da8a8ff">
    
      <msh:row guid="53627d05-8914-48a0-b2ec-792eba5b07d9">
        <msh:separator label="Параметры поиска" colSpan="7" guid="15c6c628-8aab-4c82-b3d8-ac77b7b3f700" />
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
        <td class="label" title="Поиск (typeView)" colspan="1"><label for="typeViewName" id="typeViewLabel">Реестр:</label></td>
        <td onclick="this.childNodes[1].checked='checked';"  colspan="2">
        	<input type="radio" name="typeView" value="1">  находящихся в стационаре
        </td>
        <td onclick="this.childNodes[1].checked='checked';"  colspan="2">
        	<input type="radio" name="typeView" value="2" >  выписанных
        </td>
                   <td onclick="this.childNodes[1].checked='checked';"  colspan="2">
        	<input type="radio" name="typeView" value="3"> переведенных
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
    //checkFieldUpdate('typeDate','${typeDate}',1) ;
    //checkFieldUpdate('typeAdmissionOrder','${typeAdmissionOrder}',1) ;
  
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
       	if (typeEmergency!=null && typeEmergency.equals("1")) {
    		request.setAttribute("emergencySql", " and sls.emergency='1' ") ;
    		request.setAttribute("emergencyInfo", ", поступивших по экстренным показаниям") ;
    	} else if (typeEmergency!=null && typeEmergency.equals("2")) {
    		request.setAttribute("emergencySql", " and (sls.emergency is null or sls.emergency='0') ") ;
    		request.setAttribute("emergencyInfo", ", поступивших по плановым показаниям") ;
    	} 
    	
    	ActionUtil.setParameterFilterSql("department","(select ml.id from MedCase slo left join MisLpu ml on ml.id=slo.department_id where slo.parent_id=sls.id and upper(slo.dtype)='DEPARTMENTMEDCASE' and slo.transferDate is null )", request) ;
    	if (typeAdmissionOrder==null) typeAdmissionOrder = "1" ;
    	    		
    	%>
    	  <ecom:webQuery name="diag_typeReg_ord_sql" nativeSql="select id,name from VocDiagnosisRegistrationType where code='2'"/>
    	
 <%
 
    if (typeView!=null && (typeView.equals("1"))) {
    ActionUtil.getValueByList("diag_typeReg_ord_sql", "diag_typeReg_ord", request) ;
      	%>
    
    <msh:section title="Реестр за период ${param.dateBegin}-${param.dateEnd} ${emergencyInfo}">
    <ecom:webQuery nameFldSql="reestr_sql" name="journal_reestr" nativeSql="
    select ct.id as ctid,ss.code as slsid,pat.lastname||' '||pat.firstname||' '||pat.middlename as fio ,to_char(pat.birthday,'dd.mm.yyyy') as birthday
,to_char(sls.datestart,'dd.mm.yyyy') as slsdatestart, 
(select ml.name from MedCase slo 
left join MisLpu ml on ml.id=slo.department_id 
where 
slo.parent_id=sls.id and upper(slo.dtype)='DEPARTMENTMEDCASE' and slo.transferDate is null 
),oml.name as omlname
, to_char(ct.registrationDate,'dd.mm.yyyy') as ctregistrationDate 
,(select list(mkb.code) from diagnosis diag left join vocidc10 mkb on mkb.id=diag.idc10_id where diag.medcase_id=sls.id and diag.registrationtype_id=2) as mkbcode 
,pml.name as pmlname
,dml.name as dmlname
,(select list(mkb.code) from diagnosis diag 
left join medcase slo on slo.id=diag.medCase_id and slo.dtype='DepartmentMedCase' and slo.transferDate is null 
left join vocidc10 mkb on mkb.id=diag.idc10_id where slo.parent_id=sls.id and diag.registrationtype_id=4) as mkbcode1 

from compulsorytreatment ct left join PsychiatricCareCard pcc on pcc.id=ct.careCard_id 
left join patient pat on pat.id=pcc.patient_id 
left join MedCase sls on sls.patient_id=pat.id and sls.dtype='HospitalMedCase' and sls.dateFinish is null and sls.deniedhospitalizating_id is null 
left join statisticstub ss on ss.id=sls.statisticstub_id
  left join mislpu oml on oml.id=sls.orderlpu_id
  left join mislpu dml on dml.id=sls.department_id
  left join vochosptype pml on pml.id=sls.sourcehosptype_id
where ct.datereplace is null and ct.kind_id in (2,3) 
    ${emergencySql} ${departmentSql} ${admissionOrderSql}
group by ct.id ,sls.id ,pat.lastname,pat.firstname,pat.middlename ,pat.birthday ,sls.datestart,  ct.registrationDate,ss.code
order by  pat.lastname    ,pat.firstname, pat.middlename


    " guid="4a720225-8d94-4b47-bef3-4dbbe79eec74" />
    <msh:sectionTitle>
    
    <form action="print-stac_journal_compulsory_treatment_psych_r_1.do" method="post" target="_blank">
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
    viewUrl="entityParentView-psych_compulsoryTreatment.do?short=Short" 
     action="entityParentView-psych_compulsoryTreatment.do" idField="1" >
      <msh:tableColumn columnName="#" property="sn" />
      <msh:tableColumn columnName="№ИБ" property="2" />
      <msh:tableColumn columnName="ФИО пациента" property="3" />
      <msh:tableColumn columnName="Дата рождения" property="4" />
      <msh:tableColumn columnName="МКБ напр." property="9" />
      <msh:tableColumn columnName="МКБ клин." property="12" />
      <msh:tableColumn columnName="Дата поступления" property="5" />
      <msh:tableColumn columnName="Кем направлен" property="10" />
      <msh:tableColumn columnName="Отделение поступления" property="11" />
      <msh:tableColumn columnName="Отделение выписки или нахождения" property="6" />
    </msh:table>
    </msh:sectionContent>
    </msh:section>

    <%} else if (typeView!=null && (typeView.equals("2"))) {%>
    <msh:section title="Реестр выписанных за период ${param.dateBegin}-${param.dateEnd} ${emergencyInfo}">
    <ecom:webQuery nameFldSql="reestr_sql" name="journal_reestr" nativeSql="
    select ct.id as c1tid,ss.code as s2lsid,pat.lastname||' '||pat.firstname||' '||pat.middlename as f3io 
,to_char(pat.birthday,'dd.mm.yyyy') as b4irthday
,to_char(sls.datestart,'dd.mm.yyyy') as s5datestart,to_char(sls.datefinish,'dd.mm.yyyy') as s6datefinish

, to_char(coalesce((select max(ct1.datereplace) from compulsorytreatment ct1 where ct1.careCard_id=ct.careCard_id and ct1.ordernumber=ct.ordernumber and ct.datereplace>ct1.datereplace and ct1.courtdecisionreplace_id in (2,4)),
(select min(ct1.decisiondate) from compulsorytreatment ct1 where ct1.careCard_id=ct.careCard_id and ct1.ordernumber=ct.ordernumber and ct1.kind_id in (2,3) )),'dd.mm.yyyy') as c7treplaceDate
, to_char(ct.dateReplace,'dd.mm.yyyy') as c8treplaceDate
,(select ml.name from MedCase slo
left join MisLpu ml on ml.id=slo.department_id
where
slo.parent_id=sls.id and upper(slo.dtype)='DEPARTMENTMEDCASE' and slo.transferDate is null
) as c9urDep
,(select list(mkb.code) from diagnosis diag left join vocidc10 mkb on mkb.id=diag.idc10_id where diag.medcase_id=sls.id and diag.registrationtype_id=2) as m10k
,oml.name as o11mlname
,pml.name as p12mlname
,dml.name as d13mlname
,(select list(mkb.code) from diagnosis diag
left join medcase slo on slo.id=diag.medCase_id and slo.dtype='DepartmentMedCase' and slo.transferDate is null
left join vocidc10 mkb on mkb.id=diag.idc10_id where slo.parent_id=sls.id and diag.registrationtype_id=4) as m14kbcode1
,vpctd.name as v15pctdname

,ct.datereplace - 
case when sls.datestart>coalesce((select max(ct1.datereplace) from compulsorytreatment ct1 where ct1.careCard_id=ct.careCard_id and ct1.ordernumber=ct.ordernumber and ct.datereplace>ct1.datereplace and ct1.courtdecisionreplace_id in (2,4)),
(select min(ct1.decisiondate) from compulsorytreatment ct1 where ct1.careCard_id=ct.careCard_id and ct1.ordernumber=ct.ordernumber and ct1.kind_id in (2,3) ))
then  sls.datestart else coalesce((select max(ct1.datereplace) from compulsorytreatment ct1 where ct1.careCard_id=ct.careCard_id and ct1.ordernumber=ct.ordernumber and ct.datereplace>ct1.datereplace and ct1.courtdecisionreplace_id in (2,4)),
(select min(ct1.decisiondate) from compulsorytreatment ct1 where ct1.careCard_id=ct.careCard_id and ct1.ordernumber=ct.ordernumber and ct1.kind_id in (2,3) ))
 end
as k16dpl
,ct.datereplace - 
(select min(ct1.decisiondate) from compulsorytreatment ct1 where ct1.careCard_id=ct.careCard_id and ct1.ordernumber=ct.ordernumber)
as k17dpl

,to_char((select min(ct1.decisiondate) from compulsorytreatment ct1 where ct1.careCard_id=ct.careCard_id and ct1.ordernumber=ct.ordernumber),'dd.mm.yyyy') as d18atestartpl



from compulsorytreatment ct left join PsychiatricCareCard pcc on pcc.id=ct.careCard_id
left join patient pat on pat.id=pcc.patient_id
left join MedCase sls on sls.patient_id=pat.id and sls.dtype='HospitalMedCase' 
and (sls.datefinish is null or ct.datereplace>=sls.datefinish) and ct.datereplace<sls.datestart
left join statisticstub ss on ss.id=sls.statisticstub_id
  left join mislpu oml on oml.id=sls.orderlpu_id
  left join mislpu dml on dml.id=sls.department_id
  left join vochosptype pml on pml.id=sls.sourcehosptype_id
left join vocPsychCourtTreatmentDecision vpctd on vpctd.id=ct.courtDecisionReplace_id
where  ct.kind_id in (2,3)
and    ct.datereplace between to_date('${param.dateBegin}','dd.mm.yyyy') and to_date('${dateEnd}','dd.mm.yyyy')
and ct.courtDecisionReplace_id in (4,2)
 ${emergencySql} ${departmentSql} ${admissionOrderSql}
group by ct.id ,sls.id ,pat.lastname,pat.firstname,pat.middlename ,pat.birthday ,sls.datestart,  ct.registrationDate,ss.code

order by  pat.lastname    ,pat.firstname, pat.middlename   

    " guid="4a720225-8d94-4b47-bef3-4dbbe79eec74" />
    <msh:sectionTitle>
    
    <form action="print-stac_journal_compulsory_treatment_psych_r_2.do" method="post" target="_blank">
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
    viewUrl="entityParentView-psych_compulsoryTreatment.do?short=Short" 
     action="entityParentView-psych_compulsoryTreatment.do" idField="1" >
      <msh:tableColumn columnName="#" property="sn" />
      <msh:tableColumn columnName="№ИБ" property="2" />
      <msh:tableColumn columnName="ФИО пациента" property="3" />
      <msh:tableColumn columnName="Дата рождения" property="4" />
      <msh:tableColumn columnName="Дата назначения ПЛ" property="18" />
      <msh:tableColumn columnName="Дата назначения данного вида ПЛ" property="7" />
      <msh:tableColumn columnName="Дата поступления СЛС" property="5" />
      <msh:tableColumn columnName="Решение суда по ПЛ" property="15" />
      <msh:tableColumn columnName="Дата снятия данного вида ПЛ" property="8" />
      <msh:tableColumn columnName="Дата выписки СЛС" property="6" />
      <msh:tableColumn columnName="МКБ напр." property="10" />
      <msh:tableColumn columnName="МКБ клин." property="14" />
      <msh:tableColumn columnName="Кем направлен" property="12" />
      <msh:tableColumn columnName="Отделение поступления" property="13" />
      <msh:tableColumn columnName="Отделение выписки или нахождения" property="9" />
      <msh:tableColumn columnName="К/д по данному виду ПЛ" property="16" />
      <msh:tableColumn columnName="К/д ПЛ" property="17" />


    </msh:table>
    </msh:sectionContent>
    </msh:section>
   
    <% 
    } else if (typeView!=null && (typeView.equals("1"))) {
    ActionUtil.getValueByList("diag_typeReg_ord_sql", "diag_typeReg_ord", request) ;
      	%>
    
    <msh:section title="Реестр за период ${param.dateBegin}-${param.dateEnd} ${emergencyInfo}">
    <ecom:webQuery nameFldSql="reestr_sql" name="journal_reestr" nativeSql="
    select ct.id as ctid,ss.code as slsid,pat.lastname||' '||pat.firstname||' '||pat.middlename as fio ,to_char(pat.birthday,'dd.mm.yyyy') as birthday
,to_char(sls.datestart,'dd.mm.yyyy') as slsdatestart, 
(select ml.name from MedCase slo 
left join MisLpu ml on ml.id=slo.department_id 
where 
slo.parent_id=sls.id and upper(slo.dtype)='DEPARTMENTMEDCASE' and slo.transferDate is null 
),oml.name as omlname
, to_char(ct.registrationDate,'dd.mm.yyyy') as ctregistrationDate 
,(select list(mkb.code) from diagnosis diag left join vocidc10 mkb on mkb.id=diag.idc10_id where diag.medcase_id=sls.id and diag.registrationtype_id=2) as mkbcode 
,pml.name as pmlname
,dml.name as dmlname
,(select list(mkb.code) from diagnosis diag 
left join medcase slo on slo.id=diag.medCase_id and slo.dtype='DepartmentMedCase' and slo.transferDate is null 
left join vocidc10 mkb on mkb.id=diag.idc10_id where slo.parent_id=sls.id and diag.registrationtype_id=4) as mkbcode1 

from compulsorytreatment ct left join PsychiatricCareCard pcc on pcc.id=ct.careCard_id 
left join patient pat on pat.id=pcc.patient_id 
left join MedCase sls on sls.patient_id=pat.id and sls.dtype='HospitalMedCase' and sls.dateFinish is null and sls.deniedhospitalizating_id is null 
left join statisticstub ss on ss.id=sls.statisticstub_id
  left join mislpu oml on oml.id=sls.orderlpu_id
  left join mislpu dml on dml.id=sls.department_id
  left join vochosptype pml on pml.id=sls.sourcehosptype_id
where ct.datereplace is null and ct.kind_id in (2,3) 
    ${emergencySql} ${departmentSql} ${admissionOrderSql}
    and (select count(slo.id) from medcase slo where slo.parent_id=sls.id and upper(slo.dtype)='DEPARTMENTMEDCASE')>1
group by ct.id ,sls.id ,pat.lastname,pat.firstname,pat.middlename ,pat.birthday ,sls.datestart,  ct.registrationDate,ss.code
order by  pat.lastname    ,pat.firstname, pat.middlename


    " guid="4a720225-8d94-4b47-bef3-4dbbe79eec74" />
    <msh:sectionTitle>
    
    <form action="print-stac_journal_compulsory_treatment_psych_r_1.do" method="post" target="_blank">
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
    viewUrl="entityParentView-psych_compulsoryTreatment.do?short=Short" 
     action="entityParentView-psych_compulsoryTreatment.do" idField="1" >
      <msh:tableColumn columnName="#" property="sn" />
      <msh:tableColumn columnName="№ИБ" property="2" />
      <msh:tableColumn columnName="ФИО пациента" property="3" />
      <msh:tableColumn columnName="Дата рождения" property="4" />
      <msh:tableColumn columnName="МКБ напр." property="9" />
      <msh:tableColumn columnName="МКБ клин." property="12" />
      <msh:tableColumn columnName="Дата поступления" property="5" />
      <msh:tableColumn columnName="Кем направлен" property="10" />
      <msh:tableColumn columnName="Отделение поступления" property="11" />
      <msh:tableColumn columnName="Отделение выписки или нахождения" property="6" />
    </msh:table>
    </msh:sectionContent>
    </msh:section>

    
    
    <%} 
    } else {%>
    	<i>Нет данных </i>
    	<% 
    	}%>
    
  </tiles:put>
</tiles:insert>