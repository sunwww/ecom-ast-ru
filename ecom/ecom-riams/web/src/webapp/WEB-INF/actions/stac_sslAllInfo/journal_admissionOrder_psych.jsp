<%@page import="ru.ecom.web.util.ActionUtil"%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://struts.apache.org/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://www.nuzmsh.ru/tags/msh" prefix="msh" %>
<%@ taglib uri="http://www.ecom-ast.ru/tags/ecom" prefix="ecom" %>
<%@ taglib tagdir="/WEB-INF/tags" prefix="tags" %>

<tiles:insert page="/WEB-INF/tiles/mainLayout.jsp" flush="true" >

  <tiles:put name="title" type="string">
    <msh:title mainMenu="Journals" title="Журнал учета больных в соответствие с поступлением (добровольно, недобровольно, а также состоящих на К или Д наблюдениях)"/>
  </tiles:put>
  <tiles:put name="side" type="string">
  	
    
  </tiles:put>
  <tiles:put name="body" type="string">
  <%
  
	String typeAdmissionOrder =ActionUtil.updateParameter("Hospital_Reestr_Psych","typeAdmissionOrder","1", request) ;
	String typeDate =ActionUtil.updateParameter("Hospital_Reestr_Psych","typeDate","1", request) ;
	//String typeDirect =ActionUtil.updateParameter("Hospital_Reestr_Psych","typeAdmissionOrder","2", request) ;
	String typeEmergency =ActionUtil.updateParameter("Hospital_Reestr_Psych","typeEmergency","3", request) ;
	String typeObservation =ActionUtil.updateParameter("Hospital_Reestr_Psych","typeObservation","3", request) ;
	String typeView =ActionUtil.updateParameter("Hospital_Reestr_Psych","typeView","2", request) ;
  %>
  
    <msh:form action="/stac_journal_direct_psych.do" defaultField="dateBegin" disableFormDataConfirm="true" method="GET">
    <msh:panel>
    
      <msh:row>
        <msh:separator label="Параметры поиска" colSpan="7" />
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
        	<input type="radio" name="typeAdmissionOrder" value="1">  добровольное+согласия не требуется
        </td>
        <td onclick="this.childNodes[1].checked='checked';"  colspan="2">
        	<input type="radio" name="typeAdmissionOrder" value="2" >  недобровольное
        </td>
      </msh:row>
      <msh:row>
        <td></td>
        <td onclick="this.childNodes[1].checked='checked';"  colspan="2">
        	<input type="radio" name="typeAdmissionOrder" value="3"> добровольное
        </td>
        <td onclick="this.childNodes[1].checked='checked';"  colspan="2">
        	<input type="radio" name="typeAdmissionOrder" value="4"> согласия не требуется
        </td>
        <td onclick="this.childNodes[1].checked='checked';"  colspan="2">
        	<input type="radio" name="typeAdmissionOrder" value="5"> все
        </td>
      </msh:row>

        <msh:row>
	        <td class="label" title="Просмотр данных (typeObservation)" colspan="1"><label for="typeObservationName" id="typeObservationLabel">Состоит на:</label></td>
	        <td onclick="this.childNodes[1].checked='checked';"  colspan="2">
	        	<input type="radio" name="typeObservation" value="1">  ДН
	        </td>
	        <td onclick="this.childNodes[1].checked='checked';"  colspan="4">
	        	<input type="radio" name="typeObservation" value="2">   КН
	        </td>
	        <td onclick="this.childNodes[1].checked='checked';"  colspan="4">
	        	<input type="radio" name="typeObservation" value="3">   все
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
        <td></td>
	        <td onclick="this.childNodes[1].checked='checked';"  colspan="2">
	        	<input type="radio" name="typeView" value="3">  свод по решениям судьи по ст.35 
	        </td>
	        <td onclick="this.childNodes[1].checked='checked';"  colspan="4">
	        	<input type="radio" name="typeView" value="4">  свод по порядку поступления  (статьи 29 и др)
	        </td>
      </msh:row>
      <msh:row>
        <td></td>
	        <td onclick="this.childNodes[1].checked='checked';"  colspan="4">
	        	<input type="radio" name="typeView" value="5">  свод по типу напр.учр.
	        </td>
        </msh:row>
      <msh:row>
        <msh:textField fieldColSpan="2" property="dateBegin" label="Период с" />
        <msh:textField property="dateEnd" label="по" />
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
    checkFieldUpdate('typeObservation','${typeObservation}',1) ;
  
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
    	if (typeDate!=null && typeDate.equals("2")) {
    		request.setAttribute("dateSql", "sls.dateFinish") ;
    	} else {
    		request.setAttribute("dateSql", "sls.dateStart") ;
    	}
    	if (typeObservation.equals("1")) {
    		request.setAttribute("observationSql", " and vpac.code='Д'") ;
    	} else if (typeObservation.equals("2")) {
    		request.setAttribute("observationSql", " and vpac.code='К'") ;
     	}
    	if (typeAdmissionOrder==null) typeAdmissionOrder = "1" ;
    	if (typeAdmissionOrder.equals("1")) {
    		request.setAttribute("admissionOrderSql", "and (vao.code='1' or vao.code='3')") ;
    	} else if (typeAdmissionOrder.equals("2")) {
    		request.setAttribute("admissionOrderSql", "and vao.code='2'") ;
    	} else if (typeAdmissionOrder.equals("3")) {
    		request.setAttribute("admissionOrderSql", "and vao.code='1'") ;
    	} else if (typeAdmissionOrder.equals("4")) {
    		request.setAttribute("admissionOrderSql", "and vao.code='3'") ;
    	}
    	%>
    	  <ecom:webQuery name="diag_typeReg_ord_sql" nativeSql="select id,name from VocDiagnosisRegistrationType where code='2'"/>
    	
 <%
 
    if (typeView!=null && (typeView.equals("1"))) {
    ActionUtil.getValueByList("diag_typeReg_ord_sql", "diag_typeReg_ord", request) ;
      	%>
    
    <msh:section title="Реестр за период ${param.dateBegin}-${param.dateEnd} ${emergencyInfo}">
    <ecom:webQuery nameFldSql="reestr_sql" name="journal_reestr" nativeSql="
    
    select sls.id as slsid, ss.code as sscoe,pat.lastname||' '||pat.firstname||' '||pat.middlename as fiopat
, to_char(pat.birthday,'dd.mm.yyyy') as patbirthday
,list(distinct case when vdrt.id='${diag_typeReg_ord}' then mkb.code else null end) as mkbcode
	 ,to_char(sls.dateStart,'dd.mm.yyyy') as slsdatestart,
vht.name as vhtname,ml.name as mlname,vao.name as vaoname
, vj.name as vjname
,list(distinct case when slo.dateFinish is not null then to_char(sls.dateFinish,'dd.mm.yyyy') ||' '||mlSlo.name 
when (slo.datefinish is null and slo.transferdate is null) then 'сост. на тек.момент '||mlSlo.name 
else null end) as depaDischarge
,coalesce(vpac.code,'')||' '||coalesce(vpdg.code||' '||vpdg.name,'') as vpacvpdg
,la.number as laname
from medcase sls
left join StatisticStub ss on ss.id=sls.statisticStub_id
left join Patient pat on pat.id=sls.patient_id
left join Diagnosis diag on diag.medcase_id=sls.id
left join VocIdc10 mkb on mkb.id=diag.idc10_id
left join VocDiagnosisRegistrationType vdrt on vdrt.id=diag.registrationType_id
left join VocHospType vht on sls.sourceHospType_id=vht.id
left join MedCase slo on slo.parent_id=sls.id and slo.dtype='DepartmentMedCase'
left join mislpu ml on ml.id=sls.department_id
left join mislpu mlSlo on mlSlo.id=slo.department_id
left join VocAdmissionOrder vao on vao.id=sls.admissionOrder_id
left join VocJudgment vj on vj.id=sls.judgment35_id

left join PsychiatricCareCard pcc on pcc.patient_id=sls.patient_id
left join LpuAreaPsychCareCard lapcc on lapcc.carecard_id=pcc.id and lapcc.finishDate is null and lapcc.transferDate is null
left join LpuArea la on la.id=lapcc.lpuArea_id
left join PsychiaticObservation po on po.lpuAreaPsychCareCard_id=lapcc.id
left join VocPsychDispensaryGroup vpdg on vpdg.id=po.dispensaryGroup_id 
left join VocPsychAmbulatoryCare vpac on vpac.id=po.ambulatoryCare_id
where sls.dtype='HospitalMedCase' and ${dateSql} between 
to_date('${param.dateBegin}','dd.mm.yyyy')  and to_date('${dateEnd}','dd.mm.yyyy')
and sls.deniedHospitalizating_id is null
${emergencySql} ${departmentSql} ${admissionOrderSql} ${observationSql}
group by sls.id, ss.code,pat.lastname,pat.firstname,pat.middlename
, pat.birthday,sls.dateStart,vht.name,ml.name,vao.name, vj.name
order by ml.name,pat.lastname,pat.firstname,pat.middlename
    " />
    <msh:sectionTitle>
    
    <form action="print-stac_journal_direct_psych_r.do" method="post" target="_blank">
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
      <msh:tableColumn columnName="Состоит в поликлинике" property="12" />
      <msh:tableColumn columnName="Участок" property="13" />

    </msh:table>
    </msh:sectionContent>
    </msh:section>

    <%} else if (typeView!=null && (typeView.equals("2"))) {%>
    
    <msh:section>
    <msh:sectionTitle>Свод по отделениям за период ${param.dateBegin}-${dateEnd} ${emergencyInfo}.</msh:sectionTitle>
    <msh:sectionContent>
    <ecom:webQuery name="swod_department" nativeSql="
select ml.id,ml.name,count(distinct sls.id)
from medcase sls
left join StatisticStub ss on ss.id=sls.statisticStub_id
left join Patient pat on pat.id=sls.patient_id
left join Diagnosis diag on diag.medcase_id=sls.id
left join VocIdc10 mkb on mkb.id=diag.idc10_id
left join VocHospType vht on sls.sourceHospType_id=vht.id
left join MedCase slo on slo.parent_id=sls.id and slo.dtype='DepartmentMedCase'
left join mislpu ml on ml.id=sls.department_id
left join mislpu mlSlo on mlSlo.id=slo.department_id
left join VocAdmissionOrder vao on vao.id=sls.admissionOrder_id
left join VocJudgment vj on vj.id=sls.judgment35_id
left join PsychiatricCareCard pcc on pcc.patient_id=sls.patient_id
left join LpuAreaPsychCareCard lapcc on lapcc.carecard_id=pcc.id and lapcc.finishDate is null and lapcc.transferDate is null
left join LpuArea la on la.id=lapcc.lpuArea_id
left join PsychiaticObservation po on po.lpuAreaPsychCareCard_id=lapcc.id
left join VocPsychDispensaryGroup vpdg on vpdg.id=po.dispensaryGroup_id
left join VocPsychAmbulatoryCare vpac on vpac.id=po.ambulatoryCare_id

where sls.dtype='HospitalMedCase' and ${dateSql} between to_date('${param.dateBegin}','dd.mm.yyyy')  and to_date('${dateEnd}','dd.mm.yyyy')
and sls.deniedHospitalizating_id is null ${admissionOrderSql} ${observationSql}
group by ml.id,ml.name
order by ml.id,ml.name
    " />
    <msh:table name="swod_department"
    viewUrl="stac_journal_direct_psych.do?short=Short&dateBegin=${param.dateBegin}&dateEnd=${param.dateEnd}&typeView=1&typeEmergency=${typeEmergency}&typeObservation=${param.typeObservation}" 
     action="stac_journal_direct_psych.do?dateBegin=${param.dateBegin}&dateEnd=${param.dateEnd}&typeView=1&typeEmergency=${typeEmergency}&typeObservation=${param.typeObservation}" idField="1" >
      <msh:tableColumn columnName="Отделение" property="2" />
      <msh:tableColumn columnName="Кол-во" property="3" />
    </msh:table>
    </msh:sectionContent>
    </msh:section>
    
    <%} else if (typeView!=null && (typeView.equals("3"))) {%>
    
    <msh:section>
    <msh:sectionTitle>Свод по решениям судьи по ст. 35 за период ${param.dateBegin}-${dateEnd} ${emergencyInfo}.</msh:sectionTitle>
    <msh:sectionContent>
    <ecom:webQuery name="swod_department" nativeSql="
select vj.id,vj.name,count(distinct sls.id)
from medcase sls
left join StatisticStub ss on ss.id=sls.statisticStub_id
left join Patient pat on pat.id=sls.patient_id
left join Diagnosis diag on diag.medcase_id=sls.id
left join VocIdc10 mkb on mkb.id=diag.idc10_id
left join VocHospType vht on sls.sourceHospType_id=vht.id
left join MedCase slo on slo.parent_id=sls.id and slo.dtype='DepartmentMedCase'
left join mislpu ml on ml.id=sls.department_id
left join mislpu mlSlo on mlSlo.id=slo.department_id
left join VocAdmissionOrder vao on vao.id=sls.admissionOrder_id
left join VocJudgment vj on vj.id=sls.judgment35_id
left join PsychiatricCareCard pcc on pcc.patient_id=sls.patient_id
left join LpuAreaPsychCareCard lapcc on lapcc.carecard_id=pcc.id and lapcc.finishDate is null and lapcc.transferDate is null
left join LpuArea la on la.id=lapcc.lpuArea_id
left join PsychiaticObservation po on po.lpuAreaPsychCareCard_id=lapcc.id
left join VocPsychDispensaryGroup vpdg on vpdg.id=po.dispensaryGroup_id
left join VocPsychAmbulatoryCare vpac on vpac.id=po.ambulatoryCare_id

where sls.dtype='HospitalMedCase' and ${dateSql} between to_date('${param.dateBegin}','dd.mm.yyyy')  and to_date('${dateEnd}','dd.mm.yyyy')
and sls.deniedHospitalizating_id is null ${admissionOrderSql} ${observationSql}
group by vj.id,vj.name
order by vj.id,vj.name
    " />
    <msh:table name="swod_department"
    viewUrl="stac_journal_direct_psych.do?short=Short&dateBegin=${param.dateBegin}&dateEnd=${param.dateEnd}&typeView=1&typeEmergency=${typeEmergency}&typeObservation=${param.typeObservation}" 
     action="stac_journal_direct_psych.do?dateBegin=${param.dateBegin}&dateEnd=${param.dateEnd}&typeView=1&typeEmergency=${typeEmergency}&typeObservation=${param.typeObservation}" idField="1" >
      <msh:tableColumn columnName="Решение по ст. 35" property="2" />
      <msh:tableColumn columnName="Кол-во" property="3" />
    </msh:table>
    </msh:sectionContent>
    </msh:section>
    <%} else if (typeView!=null && (typeView.equals("4"))) {%>
    
    <msh:section>
    <msh:sectionTitle>Свод по порядку поступления за период ${param.dateBegin}-${dateEnd} ${emergencyInfo}.</msh:sectionTitle>
    <msh:sectionContent>
    <ecom:webQuery name="swod_department" nativeSql="
select vao.id,vao.name,count(distinct sls.id)
from medcase sls
left join StatisticStub ss on ss.id=sls.statisticStub_id
left join Patient pat on pat.id=sls.patient_id
left join Diagnosis diag on diag.medcase_id=sls.id
left join VocIdc10 mkb on mkb.id=diag.idc10_id
left join VocHospType vht on sls.sourceHospType_id=vht.id
left join MedCase slo on slo.parent_id=sls.id and slo.dtype='DepartmentMedCase'
left join mislpu ml on ml.id=sls.department_id
left join mislpu mlSlo on mlSlo.id=slo.department_id
left join VocAdmissionOrder vao on vao.id=sls.admissionOrder_id
left join VocJudgment vj on vj.id=sls.judgment35_id
left join PsychiatricCareCard pcc on pcc.patient_id=sls.patient_id
left join LpuAreaPsychCareCard lapcc on lapcc.carecard_id=pcc.id and lapcc.finishDate is null and lapcc.transferDate is null
left join LpuArea la on la.id=lapcc.lpuArea_id
left join PsychiaticObservation po on po.lpuAreaPsychCareCard_id=lapcc.id
left join VocPsychDispensaryGroup vpdg on vpdg.id=po.dispensaryGroup_id
left join VocPsychAmbulatoryCare vpac on vpac.id=po.ambulatoryCare_id

where sls.dtype='HospitalMedCase' and ${dateSql} between to_date('${param.dateBegin}','dd.mm.yyyy')  and to_date('${dateEnd}','dd.mm.yyyy')
and sls.deniedHospitalizating_id is null ${admissionOrderSql} ${observationSql}
group by vao.id,vao.name
order by vao.id,vao.name
    " />
    <msh:table name="swod_department"
    viewUrl="stac_journal_direct_psych.do?short=Short&dateBegin=${param.dateBegin}&dateEnd=${param.dateEnd}&typeView=1&typeEmergency=${typeEmergency}&typeObservation=${param.typeObservation}" 
     action="stac_journal_direct_psych.do?dateBegin=${param.dateBegin}&dateEnd=${param.dateEnd}&typeView=1&typeEmergency=${typeEmergency}&typeObservation=${param.typeObservation}" idField="1" >
      <msh:tableColumn columnName="Порядок поступления" property="2" />
      <msh:tableColumn columnName="Кол-во" property="3" />
    </msh:table>
    </msh:sectionContent>
    </msh:section>
    <%} else if (typeView!=null && (typeView.equals("5"))) {%>
    
    <msh:section>
    <msh:sectionTitle>Свод по типу напр. учр. за период ${param.dateBegin}-${dateEnd} ${emergencyInfo}.</msh:sectionTitle>
    <msh:sectionContent>
    <ecom:webQuery name="swod_department" nativeSql="
select vht.id,vht.name,count(distinct sls.id)
from medcase sls
left join StatisticStub ss on ss.id=sls.statisticStub_id
left join Patient pat on pat.id=sls.patient_id
left join Diagnosis diag on diag.medcase_id=sls.id
left join VocIdc10 mkb on mkb.id=diag.idc10_id
left join VocHospType vht on sls.sourceHospType_id=vht.id
left join MedCase slo on slo.parent_id=sls.id and slo.dtype='DepartmentMedCase'
left join mislpu ml on ml.id=sls.department_id
left join mislpu mlSlo on mlSlo.id=slo.department_id
left join VocAdmissionOrder vao on vao.id=sls.admissionOrder_id
left join VocJudgment vj on vj.id=sls.judgment35_id
left join PsychiatricCareCard pcc on pcc.patient_id=sls.patient_id
left join LpuAreaPsychCareCard lapcc on lapcc.carecard_id=pcc.id and lapcc.finishDate is null and lapcc.transferDate is null
left join LpuArea la on la.id=lapcc.lpuArea_id
left join PsychiaticObservation po on po.lpuAreaPsychCareCard_id=lapcc.id
left join VocPsychDispensaryGroup vpdg on vpdg.id=po.dispensaryGroup_id
left join VocPsychAmbulatoryCare vpac on vpac.id=po.ambulatoryCare_id

where sls.dtype='HospitalMedCase' and ${dateSql} between to_date('${param.dateBegin}','dd.mm.yyyy')  and to_date('${dateEnd}','dd.mm.yyyy')
and sls.deniedHospitalizating_id is null ${admissionOrderSql} ${observationSql}
group by vht.id,vht.name
order by vht.id,vht.name
    " />
    <msh:table name="swod_department"
    viewUrl="stac_journal_direct_psych.do?short=Short&dateBegin=${param.dateBegin}&dateEnd=${param.dateEnd}&typeView=1&typeEmergency=${typeEmergency}&typeObservation=${param.typeObservation}" 
     action="stac_journal_direct_psych.do?dateBegin=${param.dateBegin}&dateEnd=${param.dateEnd}&typeView=1&typeEmergency=${typeEmergency}&typeObservation=${param.typeObservation}" idField="1" >
      <msh:tableColumn columnName="Тип напр. учр." property="2" />
      <msh:tableColumn columnName="Кол-во" property="3" />
    </msh:table>
    </msh:sectionContent>
    </msh:section>
    <% }  %>
    <% 
    } else {%>
    	<i>Нет данных </i>
    	<% 
    	}%>
    
  </tiles:put>
</tiles:insert>