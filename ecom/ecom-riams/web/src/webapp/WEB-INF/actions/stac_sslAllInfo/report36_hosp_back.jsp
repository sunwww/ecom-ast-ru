<%@page import="ru.ecom.mis.web.action.medcase.journal.AdmissionJournalForm"%>
<%@page import="ru.ecom.web.util.ActionUtil"%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://struts.apache.org/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://www.nuzmsh.ru/tags/msh" prefix="msh" %>
<%@ taglib uri="http://www.ecom-ast.ru/tags/ecom" prefix="ecom" %>
<%@ taglib tagdir="/WEB-INF/tags" prefix="tags" %>

<tiles:insert page="/WEB-INF/tiles/main${param.short}Layout.jsp" flush="true" >

  <tiles:put name="title" type="string">
    <msh:title mainMenu="StacJournal" title="форма 36. 2300"/>
  </tiles:put>
  <tiles:put name="side" type="string">

  </tiles:put>
  <tiles:put name="body" type="string">
  <ecom:webQuery name="result_death_sql" nativeSql="select id,name from VocHospitalizationResult where code='11'"/>
  <ecom:webQuery name="orderType_amb_sql" nativeSql="select id,name from Omc_Frm where voc_code='К'"/>
  <ecom:webQuery name="diag_typeReg_cl_sql" nativeSql="select id,name from VocDiagnosisRegistrationType where code='4'"/>
  <ecom:webQuery name="diag_typeReg_order_sql" nativeSql="select id,name from VocDiagnosisRegistrationType where code='2'"/>
  <ecom:webQuery name="diag_typeReg_discharge_sql" nativeSql="select id,name from VocDiagnosisRegistrationType where code='3'"/>
  <ecom:webQuery name="diag_typeReg_pat_sql" nativeSql="select id,name from VocDiagnosisRegistrationType where code='5'"/>
  <ecom:webQuery name="diag_priority_m_sql" nativeSql="select id,name from VocPriorityDiagnosis where code='1'"/>
  <%
  	String noViewForm = request.getParameter("noViewForm") ;
  	String sexWoman = "1" ;
  	String typeAge=ActionUtil.updateParameter("Report36HOSP","typeAge","1", request) ;
  	String typeView=ActionUtil.updateParameter("Report36HOSP","typeView","1", request) ;
  	String typeDate=ActionUtil.updateParameter("Report36HOSP","typeDate","2", request) ;
  	String dateAge="dateStart" ;
  	if (typeDate!=null && typeDate.equals("2")) {
  		dateAge="dateFinish" ;
  	}
  	request.setAttribute("dateAgeFld", dateAge) ;
  	if (typeAge!=null &&typeAge.equals("3")) {
  		StringBuilder age = new StringBuilder() ;
  		age.append(" and cast(to_char(sls.").append(dateAge).append(",'yyyy') as int)")
  				.append(" -cast(to_char(p.birthday,'yyyy') as int)")
  				.append(" +(case when (cast(to_char(sls.").append(dateAge).append(", 'mm') as int)")
  				.append(" -cast(to_char(p.birthday, 'mm') as int)")
  				.append(" +(case when (cast(to_char(sls.").append(dateAge).append(",'dd') as int)") 
  				.append(" - cast(to_char(p.birthday,'dd') as int)<0) then -1 else 0 end)")
  				.append(" <0)")
  				.append(" then -1 else 0 end) < 18 ") ;
  		request.setAttribute("age_sql", age.toString()) ;
  		request.setAttribute("age_info", "В. Дети") ;
  	} else if (typeAge!=null &&typeAge.equals("2")) {
  		StringBuilder age = new StringBuilder() ;
  		age.append(" and cast(to_char(sls.").append(dateAge).append(",'yyyy') as int)")
			.append(" -cast(to_char(p.birthday,'yyyy') as int)")
			.append(" +(case when (cast(to_char(sls.").append(dateAge).append(", 'mm') as int)")
			.append(" -cast(to_char(p.birthday, 'mm') as int)")
			.append(" +(case when (cast(to_char(sls.").append(dateAge).append(",'dd') as int)") 
			.append(" - cast(to_char(p.birthday,'dd') as int)<0) then -1 else 0 end)")
			.append(" <0)")
			.append(" then -1 else 0 end) > case when p.sex_id='").append(sexWoman).append("' then 55 else 60 end ") ;
		request.setAttribute("age_sql", age.toString()) ;
  		request.setAttribute("reportInfo", "Б. Взрослые старше трудоспособного возраста") ;
  	} else if (typeAge!=null &&typeAge.equals("1")) {
  		StringBuilder age = new StringBuilder() ;
  		age.append(" and cast(to_char(sls.").append(dateAge).append(",'yyyy') as int)")
			.append(" -cast(to_char(p.birthday,'yyyy') as int)")
			.append(" +(case when (cast(to_char(sls.").append(dateAge).append(", 'mm') as int)")
			.append(" -cast(to_char(p.birthday, 'mm') as int)")
			.append(" +(case when (cast(to_char(sls.").append(dateAge).append(",'dd') as int)") 
			.append(" - cast(to_char(p.birthday,'dd') as int)<0) then -1 else 0 end)")
			.append(" <0)")
			.append(" then -1 else 0 end) >= 18 ") ;
  		//.append(" then -1 else 0 end) between 18 and case when p.sex_id='").append(sexWoman).append("' then 55 else 60 end ") ;
		request.setAttribute("age_sql", age.toString()) ;
  		request.setAttribute("reportInfo", "А. Взрослые") ;
  	} else if (typeAge!=null &&typeAge.equals("7")) {
  		StringBuilder age = new StringBuilder() ;
  		age.append(" and cast(to_char(sls.").append(dateAge).append(",'yyyy') as int)")
			.append(" -cast(to_char(p.birthday,'yyyy') as int)")
			.append(" +(case when (cast(to_char(sls.").append(dateAge).append(", 'mm') as int)")
			.append(" -cast(to_char(p.birthday, 'mm') as int)")
			.append(" +(case when (cast(to_char(sls.").append(dateAge).append(",'dd') as int)") 
			.append(" - cast(to_char(p.birthday,'dd') as int)<0) then -1 else 0 end)")
			.append(" <0)")
  			.append(" then -1 else 0 end) between 18 and case when p.sex_id='")
  			.append(sexWoman).append("' then 55 else 60 end ") ;
		request.setAttribute("age_sql", age.toString()) ;
  		request.setAttribute("reportInfo", "А.1. Взрослые трудоспособного возраста") ;
  	} else if (typeAge!=null &&typeAge.equals("4")) {
  		StringBuilder age = new StringBuilder() ;
  		age.append(" and cast(to_char(sls.").append(dateAge).append(",'yyyy') as int)")
			.append(" -cast(to_char(p.birthday,'yyyy') as int)")
			.append(" +(case when (cast(to_char(sls.").append(dateAge).append(", 'mm') as int)")
			.append(" -cast(to_char(p.birthday, 'mm') as int)")
			.append(" +(case when (cast(to_char(sls.").append(dateAge).append(",'dd') as int)") 
			.append(" - cast(to_char(p.birthday,'dd') as int)<0) then -1 else 0 end)")
			.append(" <0)")
			.append(" then -1 else 0 end) < 1 ") ;
		request.setAttribute("age_sql", age.toString()) ;
  		request.setAttribute("reportInfo", "до года") ;
  	} else if (typeAge!=null &&typeAge.equals("5")) {
  		StringBuilder age = new StringBuilder() ;
  		age.append(" and cast(to_char(sls.").append(dateAge).append(",'yyyy') as int)")
			.append(" -cast(to_char(p.birthday,'yyyy') as int)")
			.append(" +(case when (cast(to_char(sls.").append(dateAge).append(", 'mm') as int)")
			.append(" -cast(to_char(p.birthday, 'mm') as int)")
			.append(" +(case when (cast(to_char(sls.").append(dateAge).append(",'dd') as int)") 
			.append(" - cast(to_char(p.birthday,'dd') as int)<0) then -1 else 0 end)")
			.append(" <0)")
			.append(" then -1 else 0 end) between 0 and 14 ") ;
		request.setAttribute("age_sql", age.toString()) ;
  		request.setAttribute("reportInfo", "0-14") ;
  	} else if (typeAge!=null &&typeAge.equals("6")) {
  		StringBuilder age = new StringBuilder() ;
  		age.append(" and cast(to_char(sls.").append(dateAge).append(",'yyyy') as int)")
			.append(" -cast(to_char(p.birthday,'yyyy') as int)")
			.append(" +(case when (cast(to_char(sls.").append(dateAge).append(", 'mm') as int)")
			.append(" -cast(to_char(p.birthday, 'mm') as int)")
			.append(" +(case when (cast(to_char(sls.").append(dateAge).append(",'dd') as int)") 
			.append(" - cast(to_char(p.birthday,'dd') as int)<0) then -1 else 0 end)")
			.append(" <0)")
			.append(" then -1 else 0 end) between 15 and 17 ") ;
		request.setAttribute("age_sql", age.toString()) ;
  		request.setAttribute("reportInfo", "15-17") ;
  	}
  	//request.setAttribute("result_death", "6") ;
  	//request.setAttribute("orderType_amb", "3") ;
  	ActionUtil.getValueByList("result_death_sql", "result_death", request) ;
  	ActionUtil.getValueByList("orderType_amb_sql", "orderType_amb", request) ;
  	StringBuilder paramSql= new StringBuilder() ;
  	StringBuilder paramHref= new StringBuilder() ;
  	
  	ActionUtil.setParameterFilterSql("department","departmentAll", "sloAll.department_id", request) ;
  	ActionUtil.setParameterFilterSql("department","departmentDischarge", "sloD.department_id", request) ;
  	ActionUtil.setParameterFilterSql("department","departmentAdmission", "sls.department_id", request) ;
  	paramSql.append(" ").append(ActionUtil.setParameterFilterSql("sex", "p.sex_id", request)) ;
  	paramSql.append(" ").append(ActionUtil.setParameterFilterSql("hospType", "sls.hospType_id", request)) ;
  	paramHref.append("sex=").append(request.getParameter("sex")!=null?request.getParameter("sex"):"") ;
  	paramHref.append("&hospType=").append(request.getParameter("hospType")!=null?request.getParameter("hospType"):"") ;
  	request.setAttribute("paramSql", paramSql.toString()) ;
  	request.setAttribute("paramHref", paramHref.toString()) ;
  	ActionUtil.getValueByList("diag_typeReg_order_sql", "diag_typeReg_order", request) ;
  	ActionUtil.getValueByList("diag_typeReg_discharge_sql", "diag_typeReg_discharge", request) ;
  	ActionUtil.getValueByList("diag_typeReg_cl_sql", "diag_typeReg_cl", request) ;
  	ActionUtil.getValueByList("diag_typeReg_pat_sql", "diag_typeReg_pat", request) ;
  	ActionUtil.getValueByList("diag_priority_m_sql", "diag_priority_m", request) ;
  	//request.setAttribute("diag_typeReg_cl", "4") ;
  	//request.setAttribute("diag_typeReg_pat", "5") ;
  	//request.setAttribute("diag_priority_m", "1") ;
  	if (noViewForm!=null && noViewForm.equals("1")) {
  	} else {
  		
  %>
    <msh:form action="/stac_report_36.do" defaultField="dateBegin" disableFormDataConfirm="true" method="GET">
    <input type="hidden" name="id" id="id" value=""/>
    <msh:panel>
      <msh:row>
        <msh:separator label="Параметры поиска" colSpan="7" />
      </msh:row>
      <msh:row>
        <td class="label" title="Возраст считать нам момент (typeDate)" colspan="1"><label for="typeDateName" id="typeDateLabel">Возраст считать:</label></td>
        <td onclick="this.childNodes[1].checked='checked';" colspan="2">
        	<input type="radio" name="typeDate" value="1">  на момент поступления
        </td>
        <td onclick="this.childNodes[1].checked='checked';" colspan="2">
        	<input type="radio" name="typeDate" value="2"  >  на момент выписки
        </td>
       </msh:row>
      <msh:row>
        <td class="label" title="Возрастная группа (typeAge)" colspan="1"><label for="typeAgeName" id="typeAgeLabel">Возрастная группа:</label></td>
        <td onclick="this.childNodes[1].checked='checked';">
        	<input type="radio" name="typeAge" value="1">  взрослые
        </td>
        <td onclick="this.childNodes[1].checked='checked';" colspan="2">
        	<input type="radio" name="typeAge" value="2"  >  взрослые старше труд. возраста
        </td>
        <td onclick="this.childNodes[1].checked='checked';">
        	<input type="radio" name="typeAge" value="3"  >  дети
        </td>
       </msh:row>
      <msh:row>
        <td class="label" title="Возрастная группа (typeAge)" colspan="1"></td>
        <td onclick="this.childNodes[1].checked='checked';">
        	<input type="radio" name="typeAge" value="4">  до 1 года
        </td>
        <td onclick="this.childNodes[1].checked='checked';">
        	<input type="radio" name="typeAge" value="5"  >  0-14 лет
        </td>
        <td onclick="this.childNodes[1].checked='checked';">
        	<input type="radio" name="typeAge" value="6"  >  15-17 лет
        </td>
        </msh:row>
        <msh:row>
        <td class="label" title="Возрастная группа (typeAge)" colspan="1"></td>
         <td onclick="this.childNodes[1].checked='checked';" colspan="2">
        	<input type="radio" name="typeAge" value="7"  >  взрослые труд. возраста
        </td>
        <td onclick="this.childNodes[1].checked='checked';">
        	<input type="radio" name="typeAge" value="8"  >  все
        </td>
       </msh:row>
      <msh:row>
        <td class="label" title="Просмотр данных (typeView)" colspan="1"><label for="typeViewName" id="typeViewLabel">Отобразить:</label></td>
        <td onclick="this.childNodes[1].checked='checked';">
        	<input type="radio" name="typeView" value="1"> свод по отделениям
        </td>
        <td onclick="this.childNodes[1].checked='checked';" colspan="2">
        	<input type="radio" name="typeView" value="2"> форма 36. 2300
        </td>
        <td onclick="this.childNodes[1].checked='checked';" colspan="2">
        	<input type="radio" name="typeView" value="3"> форма 16
        </td>
       </msh:row>
       <msh:row>
        <td></td>
        <td onclick="this.childNodes[1].checked='checked';">
        	<input type="radio" name="typeView" value="4"> по реестр поступивших
        </td>
        <td onclick="this.childNodes[1].checked='checked';">
        	<input type="radio" name="typeView" value="5">  по реестр выбывших
        </td>
        <td onclick="this.childNodes[1].checked='checked';" colspan="2">
        	<input type="radio" name="typeView" value="6"> по состоящих на нач. периода
        </td>
        <td onclick="this.childNodes[1].checked='checked';">
        	<input type="radio" name="typeView" value="7"> по состоящих на конец периода
        </td>
       </msh:row>
        <msh:row>
        	<msh:autoComplete property="department" fieldColSpan="4" horizontalFill="true" label="Отделение" vocName="lpu"/>
        </msh:row>
        <msh:row>
        	<msh:autoComplete property="hospType" fieldColSpan="4" horizontalFill="true" label="Тип стационара" vocName="vocHospType"/>
        </msh:row>
        <msh:row>
        	<msh:autoComplete property="sex" fieldColSpan="4" horizontalFill="true" label="Пол" vocName="vocSex"/>
        </msh:row>
      <msh:row>
        <msh:textField property="dateBegin" label="Период с" />
        <msh:textField property="dateEnd" label="по" />
           <td>
            <input type="submit" value="Найти" />
            <input type="button" onclick="refresh()" value="Пересчет" />
          </td>
      </msh:row>
    </msh:panel>
    </msh:form>
    
    <%} %>
    <script type="text/javascript" src="./dwr/interface/HospitalMedCaseService.js">/**/</script>
           <script type='text/javascript'>
           checkFieldUpdate('typeAge','${typeAge}',1) ;
           checkFieldUpdate('typeView','${typeView}',1) ;
           checkFieldUpdate('typeDate','${typeDate}',2) ;

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
    	frm.action='journal_doc_externalMedService.do' ;
    }
    function refresh() {
    	var frm = document.forms[0] ;
    	frm.target='' ;
    	frm.action='refresh' ;
    }
    function print() {
    	var frm = document.forms[0] ;
    	frm.m.value="printHistology" ;
    	frm.target='_blank' ;
    	frm.action='print-stac_histology.do' ;
    	$('id').value = $('dateBegin').value+":"
    		+$('dateBegin').value+":"
    		+$('department').value;
    }
    if ($('dateBegin').value=="") {
    	$('dateBegin').value=getCurrentDate() ;
    }

			 
    </script>
    <%
    
    String date = request.getParameter("dateBegin") ;
    String dateEnd = request.getParameter("dateEnd") ;
    //String id = (String)request.getParameter("id") ;
    String period = request.getParameter("period") ;
    String strcode =request.getParameter("strcode") ;
    if (dateEnd==null || dateEnd.equals("")) dateEnd=date ;
    request.setAttribute("dateBegin", date) ;
    request.setAttribute("dateEnd", dateEnd) ;
    
    String view = (String)request.getAttribute("typeView") ;
    
    if (view.equals("1")) {
    if (date!=null && !date.equals("")) {
        
    	%>
    
    <msh:section>
    <msh:sectionTitle>Результаты поиска за период с ${dateBegin} по ${dateEnd}.</msh:sectionTitle>
    </msh:section>
   
    <msh:section>
    <msh:sectionTitle>Свод по отделениям</msh:sectionTitle>
    <msh:sectionContent>
    <ecom:webQuery name="Report36HOSPswod" nativeSql="
select 
'&department='||sloa.department_id,ml.name as mlname
,count(case when sls.result_id!=${result_death} then sls.id else null end) as cntNoDeath
,count(case when sls.result_id!=${result_death} and sls.emergency='1' then sls.id else null end) as cntNoDeathEmer
,count(case when sls.result_id!=${result_death} and sls.emergency='1' and sls.orderType_id='3' then sls.id else null end) as cntNoDeathEmerSk
,sum(case when sls.result_id!=${result_death} then
case 
 when (sls.dateFinish-sls.dateStart)=0 then 1 
 when bf.addCaseDuration='1' then (sls.dateFinish-sls.dateStart+1) 
 else (sls.dateFinish-sls.dateStart)
end
else 0 end) as sumDays
,count(case when sls.result_id=${result_death} then sls.id else null end) as cntDeath

 from medcase sls
left join Patient p on p.id=sls.patient_id
left join MedCase sloa on sloa.parent_id=sls.id
left join MisLpu ml on ml.id=sloa.department_id
left join BedFund bf on bf.id=sloa.bedFund_id
where 
sls.dtype='HospitalMedCase' and sls.dateFinish 
between to_date('${dateBegin}','dd.mm.yyyy') and to_date('${dateEnd}','dd.mm.yyyy')
${paramSql} and sloa.dateFinish is not null
${age_sql}
group by sloa.department_id,ml.name
order by ml.name
" />
    <msh:table name="Report36HOSPswod" 
    viewUrl="stac_report_36.do?${paramHref}&typeAge=${typeAge}&typeView=${typeView}&typeAge=${typeAge}&noViewForm=1&short=Short&period=${dateBegin}-${dateEnd}" 
     action="stac_report_36.do?${paramHref}&typeAge=${typeAge}&typeView=${typeView}&typeAge=${typeAge}&noViewForm=1&period=${dateBegin}-${dateEnd}" idField="1" >
      <msh:tableColumn columnName="Отделение" property="2" />
      <msh:tableColumn isCalcAmount="true" columnName="Кол-во выписанных" property="3"/>
      <msh:tableColumn isCalcAmount="true" columnName="из них доставленых по экстренным показаниям" property="4"/>
      <msh:tableColumn isCalcAmount="true" columnName="из них экст. пациентов, доставленных скорой мед.помощью" property="5"/>
      <msh:tableColumn isCalcAmount="true" columnName="Проведено выписанными койко-дней" property="6"/>
      <msh:tableColumn isCalcAmount="true" columnName="Умерло" property="7"/>
    </msh:table>
    
    </msh:sectionContent>
    </msh:section>
    <%} else if (period!=null && !period.equals("") 
    ) {
    	
    	String[] obj = period.split("-") ;
    	String dateBegin=obj[0] ;
    	dateEnd=obj[1];
    	request.setAttribute("dateBegin", dateBegin);
    	request.setAttribute("dateEnd", dateEnd);
    	
    		%>
    <msh:section>
    <msh:sectionTitle>Результаты поиска за период с ${dateBegin} по ${dateEnd}.</msh:sectionTitle>
    </msh:section>
   
    <msh:section>
    <msh:sectionTitle>Список пациентов ${param.strname}
    
    </msh:sectionTitle>
    <msh:sectionContent>
    <ecom:webQuery name="journal_surOperation" nativeSql="
select 
sls.id as slsid,(select list(vrspt.strCode) from ReportSetTYpeParameterType rspt  
left join VocReportSetParameterType vrspt on rspt.parameterType_id=vrspt.id
where vrspt.classname='F36_DIAG' and mkb.code between rspt.codefrom and rspt.codeto
) as listStr
,ss.code as sscode
,p.lastname||' '||p.firstname||' '||p.middlename as fio
,cast(to_char(sls.${dateAgeFld},'yyyy') as int)
-cast(to_char(p.birthday,'yyyy') as int)
+(case when (cast(to_char(sls.${dateAgeFld}, 'mm') as int)
-cast(to_char(p.birthday, 'mm') as int)
+(case when (cast(to_char(sls.${dateAgeFld},'dd') as int) 
- cast(to_char(p.birthday,'dd') as int)<0) then -1 else 0 end)
<0)
then -1 else 0 end) as age
,to_char(sls.dateStart,'dd.mm.yyyy') as slsdateStart
,to_char(sls.dateFinish,'dd.mm.yyyy') as slsdateFinish
,case 
 when (sls.dateFinish-sls.dateStart)=0 then 1 
 when bf.addCaseDuration='1' then (sls.dateFinish-sls.dateStart+1) 
 else (sls.dateFinish-sls.dateStart)
end
 as cntDays
 ,mkb.code as mkbcode
 ,case when sls.result_id='${result_death}' then 'Да' else null end as isDeath
,case when sls.emergency='1' then 'Да' else null end as emer
,case when sls.emergency='1' and sls.orderType_id='${orderType_amb}' then 'Да' else null end as emerSk

 from medcase sls
left join StatisticStub ss on ss.id=sls.statisticStub_id
left join VocHospitalizationResult vhr on vhr.id=sls.result_id
left join diagnosis diag on sls.id=diag.medcase_id
left join vocidc10 mkb on mkb.id=diag.idc10_id
left join VocDiagnosisRegistrationType vdrt on vdrt.id=diag.registrationType_id
left join VocPriorityDiagnosis vpd on vpd.id=diag.priority_id
left join MedCase sloa on sloa.parent_id=sls.id
left join BedFund bf on bf.id=sloa.bedFund_id
left join Patient p on p.id=sls.patient_id
where sls.dtype='HospitalMedCase' 
and sls.dateFinish between to_date('${dateBegin}','dd.mm.yyyy') 
    and to_date('${dateEnd}','dd.mm.yyyy')

and sloa.dateFinish is not null
${paramSql}
and vdrt.id='${diag_typeReg_cl}' and vpd.id='${diag_priority_m}'
${age_sql}  

group by sls.id
,ss.code,sls.emergency,sls.orderType_id,p.lastname,p.firstname
,p.middlename,p.birthday,sls.dateStart,sls.dateFinish
,bf.addCaseDuration,sls.result_id,mkb.code
order by p.lastname,p.firstname,p.middlename " />
    <msh:table name="journal_surOperation" 
    viewUrl="entityShortView-stac_ssl.do" 
     action="entityView-stac_ssl.do" idField="1">
      <msh:tableColumn columnName="##" property="sn" />
      <msh:tableColumn columnName="Строки отчета" property="2" />
      <msh:tableColumn columnName="№стат. карт" property="3" />
      <msh:tableColumn columnName="ФИО пациента" property="4" />
      <msh:tableColumn columnName="Возраст" property="5" />
      <msh:tableColumn columnName="Дата поступления" property="6"/>
      <msh:tableColumn columnName="Дата выписки" property="7"/>
      <msh:tableColumn columnName="Кол-во к.дней" property="8"/>
      <msh:tableColumn columnName="Диагноз" property="9"/>
      <msh:tableColumn columnName="Умер?" property="10"/>
      <msh:tableColumn columnName="Доставлен по экс. показаниям?" property="11"/>
      <msh:tableColumn columnName="Доставлен по экс. показаниям на карете скорой помощи?" property="12"/>
    </msh:table>
    </msh:sectionContent>
    </msh:section>    		
    		<%
    	} else {%>
    	<i>Нет данных </i>
    	<% }} 
    
    if (view.equals("2")) {
    if (date!=null && !date.equals("")) {
        
    	%>
    
    <msh:section>
    <msh:sectionTitle>Результаты поиска за период с ${dateBegin} по ${dateEnd}.</msh:sectionTitle>
    </msh:section>
   
    <msh:section>
    <ecom:webQuery name="Report36HOSPswod" nameFldSql="Report36HOSPswod_sql" nativeSql="

select 
vrspt.id||'&strcode='||vrspt.id,vrspt.name,vrspt.strCode,vrspt.code 
,count(case when
sloAll.dtype='DepartmentMedCase' 
and sloAll.dateStart < to_date('${dateBegin}','dd.mm.yyyy') 
and (sloAll.dateFinish > to_date('${dateBegin}','dd.mm.yyyy') 
or sloAll.dateFinish is null
) ${departmentAllSql}
and mkb.code between rspt.codefrom and rspt.codeto 
and vdrt.id='${diag_typeReg_cl}' and vpd.id='${diag_priority_m}'
then sls.id else null end) as cntBeginPeriodAll
,count(case when
sloAll.dtype='DepartmentMedCase' 
and sloAll.dateStart < to_date('${dateBegin}','dd.mm.yyyy') 
and (sloAll.dateFinish > to_date('${dateBegin}','dd.mm.yyyy') 
or sloAll.dateFinish is null
)  ${departmentAllSql}
and
cast(to_char(to_date('${dateBegin}','dd.mm.yyyy'),'yyyy') as int)
-cast(to_char(p.birthday,'yyyy') as int)
+(case when (cast(to_char(to_date('${dateBegin}','dd.mm.yyyy'), 'mm') as int)
-cast(to_char(p.birthday, 'mm') as int)
+(case when (cast(to_char(to_date('${dateBegin}','dd.mm.yyyy'),'dd') as int) 
- cast(to_char(p.birthday,'dd') as int)<0) then -1 else 0 end)
<0)
then -1 else 0 end) between 0 and 14
and mkb.code between rspt.codefrom and rspt.codeto 
and vdrt.id='${diag_typeReg_cl}' and vpd.id='${diag_priority_m}'
then sls.id else null end) as cntBeginPeriod0_14

,count(case when
sloAll.dtype='DepartmentMedCase' 
and sloAll.dateStart < to_date('${dateBegin}','dd.mm.yyyy') 
and (sloAll.dateFinish > to_date('${dateBegin}','dd.mm.yyyy') 
or sloAll.dateFinish is null
)  ${departmentAllSql}
and
cast(to_char(to_date('${dateBegin}','dd.mm.yyyy'),'yyyy') as int)
-cast(to_char(p.birthday,'yyyy') as int)
+(case when (cast(to_char(to_date('${dateBegin}','dd.mm.yyyy'), 'mm') as int)
-cast(to_char(p.birthday, 'mm') as int)
+(case when (cast(to_char(to_date('${dateBegin}','dd.mm.yyyy'),'dd') as int) 
- cast(to_char(p.birthday,'dd') as int)<0) then -1 else 0 end)
<0)
then -1 else 0 end) between 15 and 17
and mkb.code between rspt.codefrom and rspt.codeto 
and vdrt.id='${diag_typeReg_cl}' and vpd.id='${diag_priority_m}'
then sls.id else null end) as cntBeginPeriod15_17

,count(case when
sls.dtype='HospitalMedCase' ${departmentAdmissionSql}
and sls.dateStart between to_date('${dateBegin}','dd.mm.yyyy') and to_date('${dateEnd}','dd.mm.yyyy')
and mkb.code between rspt.codefrom and rspt.codeto 
and vdrt.id='${diag_typeReg_order}'
then sls.id else null end) as cntEntranceAll
,count(case when 
sls.dtype='HospitalMedCase' ${departmentAdmissionSql}
and sls.dateStart between to_date('${dateBegin}','dd.mm.yyyy') and to_date('${dateEnd}','dd.mm.yyyy')
and mkb.code between rspt.codefrom and rspt.codeto 
and vdrt.id='2'
and
cast(to_char(sls.dateStart,'yyyy') as int)
-cast(to_char(p.birthday,'yyyy') as int)
+(case when (cast(to_char(sls.dateStart, 'mm') as int)
-cast(to_char(p.birthday, 'mm') as int)
+(case when (cast(to_char(sls.dateStart,'dd') as int) 
- cast(to_char(p.birthday,'dd') as int)<0) then -1 else 0 end)
<0)
then -1 else 0 end) between 0 and 14 then sls.id else null end)  as cntEntrance0_14
,count(case when 
sls.dtype='HospitalMedCase' ${departmentAdmissionSql}
and sls.dateStart between to_date('${dateBegin}','dd.mm.yyyy') and to_date('${dateEnd}','dd.mm.yyyy')
and mkb.code between rspt.codefrom and rspt.codeto 
and vdrt.id='${diag_typeReg_order}'
and
cast(to_char(sls.dateStart,'yyyy') as int)
-cast(to_char(p.birthday,'yyyy') as int)
+(case when (cast(to_char(sls.dateStart, 'mm') as int)
-cast(to_char(p.birthday, 'mm') as int)
+(case when (cast(to_char(sls.dateStart,'dd') as int) 
- cast(to_char(p.birthday,'dd') as int)<0) then -1 else 0 end)
<0)
then -1 else 0 end) between 15 and 17 then sls.id else null end)  as cntEntrance15_17
,count(case when
sls.dtype='HospitalMedCase' ${departmentAdmissionSql}
and sls.dateStart between to_date('${dateBegin}','dd.mm.yyyy') and to_date('${dateEnd}','dd.mm.yyyy')
and mkb.code between rspt.codefrom and rspt.codeto 
and vdrt.id='${diag_typeReg_order}'
and sls.admissionInHospital_id=1
then sls.id else null end) as cntEntranceAdmHosp
,count(case when
sls.dtype='HospitalMedCase' ${departmentAdmissionSql}
and sls.dateStart between to_date('${dateBegin}','dd.mm.yyyy') and to_date('${dateEnd}','dd.mm.yyyy')
and mkb.code between rspt.codefrom and rspt.codeto 
and vdrt.id='${diag_typeReg_order}'
and sls.hospitalization_id=1
then sls.id else null end) as cntEntranceHosp
,count(case when
sls.dtype='HospitalMedCase' ${departmentAdmissionSql}
and sls.dateStart between to_date('${dateBegin}','dd.mm.yyyy') and to_date('${dateEnd}','dd.mm.yyyy')
and mkb.code between rspt.codefrom and rspt.codeto 
and vdrt.id='${diag_typeReg_order}'
and sls.admissionOrder_id in (2,4,5,6,7,8,9)
then sls.id else null end) as cntEntranceHospNeDobr
,count(case when
sls.dtype='HospitalMedCase' ${departmentDischargeSql}
and sls.dateFinish between to_date('${dateBegin}','dd.mm.yyyy') and to_date('${dateEnd}','dd.mm.yyyy')
and mkb.code between rspt.codefrom and rspt.codeto 
and vdrt.id='${diag_typeReg_discharge}' and vpd.id='${diag_priority_m}'
then sls.id else null end) as cntDischargeAll

,cast(sum(distinct case when 
sls.dtype='HospitalMedCase' ${departmentDischargeSql}
and sls.dateFinish between to_date('${dateBegin}','dd.mm.yyyy') and to_date('${dateEnd}','dd.mm.yyyy')
and mkb.code between rspt.codefrom and rspt.codeto 
and vdrt.id='3' and vpd.id='1'
then cast((
case 
		when (coalesce(sls.dateFinish,CURRENT_DATE)-sls.dateStart)=0 then 1 
		when vht.code='DAYTIMEHOSP' then ((coalesce(sls.dateFinish,CURRENT_DATE)-sls.dateStart)+1) 
		else (coalesce(sls.dateFinish,CURRENT_DATE)-sls.dateStart)
		end
)||'.'||sls.id as numeric) else null end)  

-sum(distinct case when 
sls.dtype='HospitalMedCase' ${departmentDischargeSql}
and sls.dateFinish between to_date('${dateBegin}','dd.mm.yyyy') and to_date('${dateEnd}','dd.mm.yyyy')
and mkb.code between rspt.codefrom and rspt.codeto 
and vdrt.id='${diag_typeReg_discharge}' and vpd.id='${diag_priority_m}'
then cast('0.'||sls.id as numeric) else null end)
as int)
as sumDayAllDischarge

,count(case when
sls.dtype='HospitalMedCase' ${departmentDischargeSql}
and sls.dateFinish between to_date('${dateBegin}','dd.mm.yyyy') and to_date('${dateEnd}','dd.mm.yyyy')
 and sls.result_id=6
and mkb.code between rspt.codefrom and rspt.codeto 
and vdrt.id='${diag_typeReg_discharge}' and vpd.id='${diag_priority_m}'
then sls.id else null end) as cntDischargeDeath

,cast(sum(distinct case when 
sls.dtype='HospitalMedCase' ${departmentDischargeSql}
and sls.dateFinish between to_date('${dateBegin}','dd.mm.yyyy') and to_date('${dateEnd}','dd.mm.yyyy')
and mkb.code between rspt.codefrom and rspt.codeto 
 and sls.result_id='${result_death}'
and vdrt.id='${diag_typeReg_discharge}' and vpd.id='${diag_priority_m}'
then cast((
case 
		when (coalesce(sls.dateFinish,CURRENT_DATE)-sls.dateStart)=0 then 1 
		when vht.code='DAYTIMEHOSP' then ((coalesce(sls.dateFinish,CURRENT_DATE)-sls.dateStart)+1) 
		else (coalesce(sls.dateFinish,CURRENT_DATE)-sls.dateStart)
		end
)||'.'||sls.id as numeric) else null end)  

-sum(distinct case when 
sls.dtype='HospitalMedCase'  ${departmentDischargeSql}
and sls.dateFinish between to_date('${dateBegin}','dd.mm.yyyy') and to_date('${dateEnd}','dd.mm.yyyy')
and mkb.code between rspt.codefrom and rspt.codeto 
 and sls.result_id='${result_death}'
and vdrt.id='${diag_typeReg_discharge}' and vpd.id='${diag_priority_m}'
then cast('0.'||sls.id as numeric) else null end)
as int)
as sumDayDeathDischarge


,count(case when 
sls.dtype='HospitalMedCase'  ${departmentDischargeSql}
and sls.dateFinish between to_date('${dateBegin}','dd.mm.yyyy') and to_date('${dateEnd}','dd.mm.yyyy')
and mkb.code between rspt.codefrom and rspt.codeto 
and vdrt.id='${diag_typeReg_discharge}' and vpd.id='${diag_priority_m}'
and
cast(to_char(sls.dateFinish,'yyyy') as int)
-cast(to_char(p.birthday,'yyyy') as int)
+(case when (cast(to_char(sls.dateFinish, 'mm') as int)
-cast(to_char(p.birthday, 'mm') as int)
+(case when (cast(to_char(sls.dateFinish,'dd') as int) 
- cast(to_char(p.birthday,'dd') as int)<0) then -1 else 0 end)
<0)
then -1 else 0 end) between 0 and 14 then sls.id else null end)  as cntDischarge0_14

,cast(sum(distinct case when 
sls.dtype='HospitalMedCase'  ${departmentDischargeSql}
and sls.dateFinish between to_date('${dateBegin}','dd.mm.yyyy') and to_date('${dateEnd}','dd.mm.yyyy')
and mkb.code between rspt.codefrom and rspt.codeto 
and vdrt.id='${diag_typeReg_discharge}' and vpd.id='${diag_priority_m}'
and
cast(to_char(sls.dateFinish,'yyyy') as int)
-cast(to_char(p.birthday,'yyyy') as int)
+(case when (cast(to_char(sls.dateFinish, 'mm') as int)
-cast(to_char(p.birthday, 'mm') as int)
+(case when (cast(to_char(sls.dateFinish,'dd') as int) 
- cast(to_char(p.birthday,'dd') as int)<0) then -1 else 0 end)
<0)
then -1 else 0 end) between 0 and 14 then cast((
case 
		when (coalesce(sls.dateFinish,CURRENT_DATE)-sls.dateStart)=0 then 1 
		when vht.code='DAYTIMEHOSP' then ((coalesce(sls.dateFinish,CURRENT_DATE)-sls.dateStart)+1) 
		else (coalesce(sls.dateFinish,CURRENT_DATE)-sls.dateStart)
		end
)||'.'||sls.id as numeric) else null end)  

-sum(distinct case when 
sls.dtype='HospitalMedCase'  ${departmentDischargeSql}
and sls.dateFinish between to_date('${dateBegin}','dd.mm.yyyy') and to_date('${dateEnd}','dd.mm.yyyy')
and mkb.code between rspt.codefrom and rspt.codeto 
and vdrt.id='${diag_typeReg_discharge}' and vpd.id='${diag_priority_m}'
and
cast(to_char(sls.dateFinish,'yyyy') as int)
-cast(to_char(p.birthday,'yyyy') as int)
+(case when (cast(to_char(sls.dateFinish, 'mm') as int)
-cast(to_char(p.birthday, 'mm') as int)
+(case when (cast(to_char(sls.dateFinish,'dd') as int) 
- cast(to_char(p.birthday,'dd') as int)<0) then -1 else 0 end)
<0)
then -1 else 0 end) between 0 and 14 then cast
('0.'||sls.id as numeric) else null end)  
as int)
as sumDayDischarge0_14

,count(case when 
sls.dtype='HospitalMedCase'  ${departmentDischargeSql}
and sls.dateFinish between to_date('${dateBegin}','dd.mm.yyyy') and to_date('${dateEnd}','dd.mm.yyyy')
and mkb.code between rspt.codefrom and rspt.codeto 
and vdrt.id='${diag_typeReg_discharge}' and vpd.id='${diag_priority_m}'
and
cast(to_char(sls.dateFinish,'yyyy') as int)
-cast(to_char(p.birthday,'yyyy') as int)
+(case when (cast(to_char(sls.dateFinish, 'mm') as int)
-cast(to_char(p.birthday, 'mm') as int)
+(case when (cast(to_char(sls.dateFinish,'dd') as int) 
- cast(to_char(p.birthday,'dd') as int)<0) then -1 else 0 end)
<0)
then -1 else 0 end) between 15 and 17 then sls.id else null end)  as cntDischarge15_17

,cast(sum(distinct case when 
sls.dtype='HospitalMedCase'  ${departmentDischargeSql}
and sls.dateFinish between to_date('${dateBegin}','dd.mm.yyyy') and to_date('${dateEnd}','dd.mm.yyyy')
and mkb.code between rspt.codefrom and rspt.codeto 
and vdrt.id='${diag_typeReg_discharge}' and vpd.id='${diag_priority_m}'
and
cast(to_char(sls.dateFinish,'yyyy') as int)
-cast(to_char(p.birthday,'yyyy') as int)
+(case when (cast(to_char(sls.dateFinish, 'mm') as int)
-cast(to_char(p.birthday, 'mm') as int)
+(case when (cast(to_char(sls.dateFinish,'dd') as int) 
- cast(to_char(p.birthday,'dd') as int)<0) then -1 else 0 end)
<0)
then -1 else 0 end) between 15 and 17 then cast((
case 
		when (coalesce(sls.dateFinish,CURRENT_DATE)-sls.dateStart)=0 then 1 
		when vht.code='DAYTIMEHOSP' then ((coalesce(sls.dateFinish,CURRENT_DATE)-sls.dateStart)+1) 
		else (coalesce(sls.dateFinish,CURRENT_DATE)-sls.dateStart)
		end
)||'.'||sls.id as numeric) else null end)  

-sum(distinct case when 
sls.dtype='HospitalMedCase'  ${departmentDischargeSql}
and sls.dateFinish between to_date('${dateBegin}','dd.mm.yyyy') and to_date('${dateEnd}','dd.mm.yyyy')
and mkb.code between rspt.codefrom and rspt.codeto 
and vdrt.id='${diag_typeReg_discharge}' and vpd.id='${diag_priority_m}'
and
cast(to_char(sls.dateFinish,'yyyy') as int)
-cast(to_char(p.birthday,'yyyy') as int)
+(case when (cast(to_char(sls.dateFinish, 'mm') as int)
-cast(to_char(p.birthday, 'mm') as int)
+(case when (cast(to_char(sls.dateFinish,'dd') as int) 
- cast(to_char(p.birthday,'dd') as int)<0) then -1 else 0 end)
<0)
then -1 else 0 end) between 15 and 17 then cast
('0.'||sls.id as numeric) else null end)  
as int)
as sumDayDischarge15_17


,count(case when
sloAll.dtype='DepartmentMedCase'  ${departmentAllSql}
and sloAll.dateStart <= to_date('${dateEnd}','dd.mm.yyyy') 
and (coalesce(sloAll.dateFinish,sloAll.transferDate) > to_date('${dateEnd}','dd.mm.yyyy') 
or coalesce(sloAll.dateFinish,sloAll.transferDate) is null
)
and mkb.code between rspt.codefrom and rspt.codeto 
and vdrt.id='${diag_typeReg_discharge}' and vpd.id='${diag_priority_m}'
then sls.id else null end) as cntFinishPeriodAll
,count(case when
sloAll.dtype='DepartmentMedCase' ${departmentAllSql}
and sloAll.dateStart <= to_date('${dateEnd}','dd.mm.yyyy') 
and (coalesce(sloAll.dateFinish,sloAll.transferDate) > to_date('${dateEnd}','dd.mm.yyyy') 
or coalesce(sloAll.dateFinish,sloAll.transferDate) is null
)
and
cast(to_char(to_date('${dateEnd}','dd.mm.yyyy'),'yyyy') as int)
-cast(to_char(p.birthday,'yyyy') as int)
+(case when (cast(to_char(to_date('${dateEnd}','dd.mm.yyyy'), 'mm') as int)
-cast(to_char(p.birthday, 'mm') as int)
+(case when (cast(to_char(to_date('${dateEnd}','dd.mm.yyyy'),'dd') as int) 
- cast(to_char(p.birthday,'dd') as int)<0) then -1 else 0 end)
<0)
then -1 else 0 end) between 0 and 14
and mkb.code between rspt.codefrom and rspt.codeto 
and vdrt.id='${diag_typeReg_discharge}' and vpd.id='${diag_priority_m}'
then sls.id else null end) as cntFinishPeriod0_14

,count(case when
sloAll.dtype='DepartmentMedCase' ${departmentAllSql}
and sloAll.dateStart <= to_date('${dateEnd}','dd.mm.yyyy') 
and (coalesce(sloAll.dateFinish,sloAll.transferDate) > to_date('${dateEnd}','dd.mm.yyyy') 
or coalesce(sloAll.dateFinish,sloAll.transferDate) is null
)
and
cast(to_char(to_date('${dateEnd}','dd.mm.yyyy'),'yyyy') as int)
-cast(to_char(p.birthday,'yyyy') as int)
+(case when (cast(to_char(to_date('${dateEnd}','dd.mm.yyyy'), 'mm') as int)
-cast(to_char(p.birthday, 'mm') as int)
+(case when (cast(to_char(to_date('${dateEnd}','dd.mm.yyyy'),'dd') as int) 
- cast(to_char(p.birthday,'dd') as int)<0) then -1 else 0 end)
<0)
then -1 else 0 end) between 15 and 17
and mkb.code between rspt.codefrom and rspt.codeto 
and vdrt.id='${diag_typeReg_discharge}' and vpd.id='${diag_priority_m}'
then sls.id else null end) as cntFinishPeriod15_17




from medcase sls
left join VocHospType vht on vht.id=sls.targetHospType_id
left join diagnosis diag on sls.id=diag.medcase_id
left join vocidc10 mkb on mkb.id=diag.idc10_id
left join VocDiagnosisRegistrationType vdrt on vdrt.id=diag.registrationType_id
left join VocPriorityDiagnosis vpd on vpd.id=diag.priority_id
left join MedCase sloAll on sloAll.parent_id=sls.id and sloAll.dtype='DepartmentMedCase'
left join MedCase sloD on sloD.parent_id=sls.id and sloD.dateFinish is not null
left join diagnosis diagD on sloD.id=diagD.medcase_id
left join BedFund bf on bf.id=sloD.bedFund_id
left join vocidc10 mkbD on mkbD.id=diagD.idc10_id
left join VocDiagnosisRegistrationType vdrtD on vdrtD.id=diagD.registrationType_id
left join VocPriorityDiagnosis vpdD on vpdD.id=diagD.priority_id

left join VocReportSetParameterType vrspt on vrspt.classname='F36_DIAG'
left join ReportSetTYpeParameterType rspt on rspt.parameterType_id=vrspt.id
left join Patient p on p.id=sls.patient_id
where (sls.dtype='HospitalMedCase' 
and (sls.dateFinish between to_date('${dateBegin}','dd.mm.yyyy') and to_date('${dateEnd}','dd.mm.yyyy')
or sls.dateFinish is null)
and (sls.dateStart <= to_date('${dateEnd}','dd.mm.yyyy') 
)) 

and (mkb.code between rspt.codefrom and rspt.codeto 
or mkbD.code between rspt.codefrom and rspt.codeto )
${age_sql} 
${paramSql}
${departmentAllSql}
group by vrspt.id,vrspt.name,vrspt.strCode,vrspt.code
order by vrspt.strCode

" />
    <msh:sectionTitle>Свод по нозоологиям (выписанные)
    
    	    <form action="print-report_14_2.do" method="post" target="_blank">
	    Свод по нозоологиям (выписанные)
	    <input type='hidden' name="sqlText" id="sqlText" value="${Report36HOSPswod_sql}"> 
	    <input type='hidden' name="sqlInfo" id="sqlInfo" value="Свод по нозоологиям (выписанные) за ${param.dateBegin}-${dateEnd}.">
	    <input type='hidden' name="sqlColumn" id="sqlColumn" value="${groupName}">
	    <input type='hidden' name="s" id="s" value="PrintService">
	    <input type='hidden' name="m" id="m" value="printNativeQuery">
	    <input type="submit" value="Печать"> 
	    </form>     
    </msh:sectionTitle>
    <msh:sectionContent>
    <msh:table name="Report36HOSPswod" 
    viewUrl="stac_report_36.do?${paramHref}&typeAge=${typeAge}&typeView=${typeView}&department=${param.department}&typeAge=${typeAge}&noViewForm=1&short=Short&period=${dateBegin}-${dateEnd}" 
     action="stac_report_36.do?${paramHref}&typeAge=${typeAge}&typeView=${typeView}&department=${param.department}&typeAge=${typeAge}&noViewForm=1&period=${dateBegin}-${dateEnd}" idField="1" >
      <msh:tableColumn columnName="Наименование" property="2" />
      <msh:tableColumn columnName="№ строки" property="3" />
      <msh:tableColumn columnName="Код МКБ10" property="4" />
      <msh:tableColumn columnName="Начало всего" property="5"/>
      <msh:tableColumn columnName="Начало 0-14" property="6"/>
      <msh:tableColumn columnName="Начало 15-17" property="7"/>
      <msh:tableColumn columnName="Поступ. всего" property="8"/>
      <msh:tableColumn columnName="Поступ. 0-14" property="9"/>
      <msh:tableColumn columnName="Поступ.15-17" property="10"/>
      <msh:tableColumn columnName="Поступ. вп.в д.г" property="11"/>
      <msh:tableColumn columnName="Поступ. втч вп. в жизни" property="12"/>
      <msh:tableColumn columnName="Пост. недобр." property="13"/>
      <msh:tableColumn columnName="Выбыло" property="14"/>
      <msh:tableColumn columnName="к.д" property="15"/>
      <msh:tableColumn columnName="умерло" property="16"/>
      <msh:tableColumn columnName="к.д" property="17"/>
      <msh:tableColumn columnName="0-14" property="18"/>
      <msh:tableColumn columnName="к.д." property="19"/>
      <msh:tableColumn columnName="15-17" property="20"/>
      <msh:tableColumn columnName="к.д." property="21"/>
      <msh:tableColumn columnName="сост. всего" property="22"/>
      <msh:tableColumn columnName="сост. 0-14" property="23"/>
      <msh:tableColumn columnName="сост. 15-17" property="24"/>
    </msh:table>
    
    </msh:sectionContent>
    </msh:section>
    <%} else if (period!=null && !period.equals("") 
    && strcode!=null && !strcode.equals("")) {
    	
    	String[] obj = period.split("-") ;
    	String dateBegin=obj[0] ;
    	dateEnd=obj[1];
    	request.setAttribute("dateBegin", dateBegin);
    	request.setAttribute("dateEnd", dateEnd);
    	
    		%>
    <msh:section>
    <msh:sectionTitle>Результаты поиска за период с ${dateBegin} по ${dateEnd}.</msh:sectionTitle>
    </msh:section>
   
    <msh:section>
    <msh:sectionTitle>Список пациентов ${param.strname}
    
    </msh:sectionTitle>
    <msh:sectionContent>
    <ecom:webQuery name="journal_surOperation" nativeSql="
select 
sls.id as slsid,list(vrspt1.strCode) as listStr
,ss.code as sscode
,p.lastname||' '||p.firstname||' '||p.middlename as fio
,cast(to_char(sls.${dateAgeFld},'yyyy') as int)
-cast(to_char(p.birthday,'yyyy') as int)
+(case when (cast(to_char(sls.${dateAgeFld}, 'mm') as int)
-cast(to_char(p.birthday, 'mm') as int)
+(case when (cast(to_char(sls.${dateAgeFld},'dd') as int) 
- cast(to_char(p.birthday,'dd') as int)<0) then -1 else 0 end)
<0)
then -1 else 0 end) as age
,to_char(sls.dateStart,'dd.mm.yyyy') as slsdateStart
,to_char(sls.dateFinish,'dd.mm.yyyy') as slsdateFinish
,case 
 when (sls.dateFinish-sls.dateStart)=0 then 1 
 when bf.addCaseDuration='1' then (sls.dateFinish-sls.dateStart+1) 
 else (sls.dateFinish-sls.dateStart)
end
 as cntDays
 ,mkb.code as mkbcode
 ,case when sls.result_id='${result_death}' then 'Да' else null end as isDeath
,case when sls.emergency='1' then 'Да' else null end as emer
,case when sls.emergency='1' and sls.orderType_id='${orderType_amb}' then 'Да' else null end as emerSk

 from medcase sls
left join StatisticStub ss on ss.id=sls.statisticStub_id
left join VocHospitalizationResult vhr on vhr.id=sls.result_id
left join diagnosis diag on sls.id=diag.medcase_id
left join vocidc10 mkb on mkb.id=diag.idc10_id
left join VocDiagnosisRegistrationType vdrt on vdrt.id=diag.registrationType_id
left join VocPriorityDiagnosis vpd on vpd.id=diag.priority_id
left join MedCase sloa on sloa.parent_id=sls.id
left join BedFund bf on bf.id=sloa.bedFund_id
left join ReportSetTYpeParameterType rspt on mkb.code between rspt.codefrom and rspt.codeto
left join VocReportSetParameterType vrspt on rspt.parameterType_id=vrspt.id
left join ReportSetTYpeParameterType rspt1 on mkb.code between rspt1.codefrom and rspt1.codeto
left join VocReportSetParameterType vrspt1 on rspt1.parameterType_id=vrspt1.id
left join Patient p on p.id=sls.patient_id
where sls.dtype='HospitalMedCase' and sls.dateFinish between to_date('${dateBegin}','dd.mm.yyyy') 
    and to_date('${dateEnd}','dd.mm.yyyy')
and vrspt.id='${param.strcode}'
and sloa.dateFinish is not null
${paramSql}
and vdrt.id='${diag_typeReg_cl}' and vpd.id='${diag_priority_m}'
and sls.result_id!='${result_death}'
${age_sql}  
and vrspt1.classname='F36_DIAG' 
group by sls.id
,ss.code,sls.emergency,sls.orderType_id,p.lastname,p.firstname
,p.middlename,p.birthday,sls.dateStart,sls.dateFinish
,bf.addCaseDuration,sls.result_id,mkb.code
order by p.lastname,p.firstname,p.middlename " />
    <msh:table name="journal_surOperation" 
    viewUrl="entityShortView-stac_ssl.do" 
     action="entityView-stac_ssl.do" idField="1">
      <msh:tableColumn columnName="##" property="sn" />
      <msh:tableColumn columnName="Строки отчета" property="2" />
      <msh:tableColumn columnName="№стат. карт" property="3" />
      <msh:tableColumn columnName="ФИО пациента" property="4" />
      <msh:tableColumn columnName="Возраст" property="5" />
      <msh:tableColumn columnName="Дата поступления" property="6"/>
      <msh:tableColumn columnName="Дата выписки" property="7"/>
      <msh:tableColumn columnName="Кол-во к.дней" property="8"/>
      <msh:tableColumn columnName="Диагноз" property="9"/>
      <msh:tableColumn columnName="Доставлен по экс. показаниям?" property="11"/>
      <msh:tableColumn columnName="Доставлен по экс. показаниям на карете скорой помощи?" property="12"/>
    </msh:table>
    </msh:sectionContent>
    </msh:section>    		
    		<%
    	} else {%>
    	<i>Нет данных </i>
    	<% }} 
    
    if (view.equals("3")) {
        if (date!=null && !date.equals("")) {
            
        	%>
        
        <msh:section>
        <msh:sectionTitle>Результаты поиска за период с ${dateBegin} по ${dateEnd}.</msh:sectionTitle>
        </msh:section>
       
        <msh:section>
        <msh:sectionTitle>Свод по нозоологиям (умершие)</msh:sectionTitle>
        <msh:sectionContent>
        <ecom:webQuery name="Report36HOSPswod" nativeSql="
        select vrspt.id||'&strcode='||vrspt.id,vrspt.name,vrspt.strCode,vrspt.code 
    ,count(sls.id) as cntDeath
    ,count(
    case when (select count(distinct mkb.code) from Diagnosis diag 
    left join vocidc10 mkb on mkb.id=diag.idc10_id
    left join VocDiagnosisRegistrationType vdrt on vdrt.id=diag.registrationType_id
    left join VocPriorityDiagnosis vpd on vpd.id=diag.priority_id
    where sls.id=diag.medcase_id  
    and (vdrt.id='${diag_typeReg_pat}') 
    and vpd.id='${diag_priority_m}'
    )>0 then 1 else null end
    ) as cntRash
     from medcase sls
    left join MedCase sloa on sloa.parent_id=sls.id
    left join BedFund bf on bf.id=sloa.bedFund_id
    left join VocReportSetParameterType vrspt on vrspt.classname='F36_DIAG'
    left join ReportSetTYpeParameterType rspt on rspt.parameterType_id=vrspt.id
    left join Patient p on p.id=sls.patient_id
    where 
    sls.dtype='HospitalMedCase' and sls.dateFinish 
    between to_date('${dateBegin}','dd.mm.yyyy') and to_date('${dateEnd}','dd.mm.yyyy')
     
    ${paramSql} and sloa.dateFinish is not null
    and sls.result_id='${result_death}'
    and coalesce(
    (select mkb.code from Diagnosis diag 
    left join vocidc10 mkb on mkb.id=diag.idc10_id
    left join VocDiagnosisRegistrationType vdrt on vdrt.id=diag.registrationType_id
    left join VocPriorityDiagnosis vpd on vpd.id=diag.priority_id
    where sls.id=diag.medcase_id  
    and vdrt.id='${diag_typeReg_pat}'
    and vpd.id='${diag_priority_m}'
    )
    ,
    (select mkb.code from Diagnosis diag 
    left join vocidc10 mkb on mkb.id=diag.idc10_id
    left join VocDiagnosisRegistrationType vdrt on vdrt.id=diag.registrationType_id
    left join VocPriorityDiagnosis vpd on vpd.id=diag.priority_id
    where sls.id=diag.medcase_id  
    and vdrt.id='${diag_typeReg_cl}'
    and vpd.id='${diag_priority_m}'
    )
    ) between rspt.codefrom and rspt.codeto
    ${age_sql}  
     
    group by vrspt.id,vrspt.name,vrspt.strCode,vrspt.code
    order by vrspt.strCode
    " />
        <msh:table name="Report36HOSPswod" 
        viewUrl="stac_report_36.do?${paramHref}&typeAge=${typeAge}&typeView=${typeView}&department=${param.department}&typeAge=${typeAge}&noViewForm=1&short=Short&period=${dateBegin}-${dateEnd}" 
         action="stac_report_36.do?${paramHref}&typeAge=${typeAge}&typeView=${typeView}&department=${param.department}&typeAge=${typeAge}&noViewForm=1&period=${dateBegin}-${dateEnd}" idField="1" >
          <msh:tableColumn columnName="Наименование" property="2" />
          <msh:tableColumn columnName="№ строки" property="3" />
          <msh:tableColumn columnName="Код МКБ10" property="4" />
          <msh:tableColumn columnName="Умерло" property="5"/>
          <msh:tableColumn columnName="Расхождение диагноза" property="6"/>
        </msh:table>
        
        </msh:sectionContent>
        </msh:section>
        <%} else if (period!=null && !period.equals("") 
        && strcode!=null && !strcode.equals("")) {
        	
        	String[] obj = period.split("-") ;
        	String dateBegin=obj[0] ;
        	dateEnd=obj[1];
        	request.setAttribute("dateBegin", dateBegin);
        	request.setAttribute("dateEnd", dateEnd);
        	
        		%>
        <msh:section>
        <msh:sectionTitle>Результаты поиска за период с ${dateBegin} по ${dateEnd}.</msh:sectionTitle>
        </msh:section>
       
        <msh:section>
        <msh:sectionTitle>Список пациентов ${param.strname}
        
        </msh:sectionTitle>
        <msh:sectionContent>
        <ecom:webQuery name="journal_surOperation" nativeSql="
    select 
    sls.id as slsid
    ,ss.code as sscode
    ,p.lastname||' '||p.firstname||' '||p.middlename as fio
    ,cast(to_char(sls.${dateAgeFld},'yyyy') as int)
    -cast(to_char(p.birthday,'yyyy') as int)
    +(case when (cast(to_char(sls.${dateAgeFld}, 'mm') as int)
    -cast(to_char(p.birthday, 'mm') as int)
    +(case when (cast(to_char(sls.${dateAgeFld},'dd') as int) 
    - cast(to_char(p.birthday,'dd') as int)<0) then -1 else 0 end)
    <0)
    then -1 else 0 end) as age
    ,to_char(sls.dateStart,'dd.mm.yyyy') as slsdateStart
    ,to_char(sls.dateFinish,'dd.mm.yyyy') as slsdateFinish
    ,case 
     when (sls.dateFinish-sls.dateStart)=0 then 1 
     when bf.addCaseDuration='1' then (sls.dateFinish-sls.dateStart+1) 
     else (sls.dateFinish-sls.dateStart)
    end
     as cntDays
    ,(select mkb.code from Diagnosis diag 
    left join vocidc10 mkb on mkb.id=diag.idc10_id
    left join VocDiagnosisRegistrationType vdrt on vdrt.id=diag.registrationType_id
    left join VocPriorityDiagnosis vpd on vpd.id=diag.priority_id
    where sls.id=diag.medcase_id  
    and vdrt.id='${diag_typeReg_cl}'
    and vpd.id='${diag_priority_m}'
    ) as mkbcode1
    ,
    (select mkb.code from Diagnosis diag 
    left join vocidc10 mkb on mkb.id=diag.idc10_id
    left join VocDiagnosisRegistrationType vdrt on vdrt.id=diag.registrationType_id
    left join VocPriorityDiagnosis vpd on vpd.id=diag.priority_id
    where sls.id=diag.medcase_id  
    and vdrt.id='${diag_typeReg_pat}'
    and vpd.id='${diag_priority_m}'
    ) as mkbcode2
     ,case when sls.result_id='${result_death}' then 'Да' else null end as isDeath
    ,case when sls.emergency='1' then 'Да' else null end as emer
    ,case when sls.emergency='1' and sls.orderType_id='${orderType_amb}' then 'Да' else null end as emerSk

     from medcase sls
    left join StatisticStub ss on ss.id=sls.statisticStub_id
    left join VocHospitalizationResult vhr on vhr.id=sls.result_id
    left join MedCase sloa on sloa.parent_id=sls.id
    left join BedFund bf on bf.id=sloa.bedFund_id
    left join VocReportSetParameterType vrspt on vrspt.classname='F36_DIAG'
    left join ReportSetTYpeParameterType rspt on rspt.parameterType_id=vrspt.id
    left join Patient p on p.id=sls.patient_id
    where sls.dtype='HospitalMedCase' and sls.dateFinish between to_date('${dateBegin}','dd.mm.yyyy') 
        and to_date('${dateEnd}','dd.mm.yyyy')
    and vrspt.id='${param.strcode}'
    and sloa.dateFinish is not null
    ${paramSql}
    and sls.result_id='${result_death}'
    ${age_sql}  
    and 
    coalesce(
    (select mkb.code from Diagnosis diag 
    left join vocidc10 mkb on mkb.id=diag.idc10_id
    left join VocDiagnosisRegistrationType vdrt on vdrt.id=diag.registrationType_id
    left join VocPriorityDiagnosis vpd on vpd.id=diag.priority_id
    where sls.id=diag.medcase_id  
    and vdrt.id='${diag_typeReg_pat}'
    and vpd.id='${diag_priority_m}'
    )
    ,
    (select mkb.code from Diagnosis diag 
    left join vocidc10 mkb on mkb.id=diag.idc10_id
    left join VocDiagnosisRegistrationType vdrt on vdrt.id=diag.registrationType_id
    left join VocPriorityDiagnosis vpd on vpd.id=diag.priority_id
    where sls.id=diag.medcase_id  
    and vdrt.id='${diag_typeReg_cl}'
    and vpd.id='${diag_priority_m}'
    )
    ) between rspt.codefrom and rspt.codeto
    group by sls.id
    ,ss.code,sls.emergency,sls.orderType_id,p.lastname,p.firstname
    ,p.middlename,p.birthday,sls.dateStart,sls.dateFinish
    ,bf.addCaseDuration,sls.result_id
    order by p.lastname,p.firstname,p.middlename " />
        <msh:table name="journal_surOperation" 
        viewUrl="entityShortView-stac_ssl.do" 
         action="entityView-stac_ssl.do" idField="1">
          <msh:tableColumn columnName="##" property="sn" />
          <msh:tableColumn columnName="№стат. карт" property="2" />
          <msh:tableColumn columnName="ФИО пациента" property="3" />
          <msh:tableColumn columnName="Возраст" property="4" />
          <msh:tableColumn columnName="Дата поступления" property="5"/>
          <msh:tableColumn columnName="Дата выписки" property="6"/>
          <msh:tableColumn columnName="Кол-во к.дней" property="7"/>
          <msh:tableColumn columnName="Диагноз клинический" property="8"/>
          <msh:tableColumn columnName="Диагноз пат.-анат." property="9"/>
          <msh:tableColumn columnName="Умер?" property="10"/>
          <msh:tableColumn columnName="Доставлен по экс. показаниям?" property="11"/>
          <msh:tableColumn columnName="Доставлен по экс. показаниям на карете скорой помощи?" property="12"/>
        </msh:table>
        </msh:sectionContent>
        </msh:section>    		
        		<%
        	} else {%>
        	<i>Нет данных </i>
        	<% }} 

    if (view.equals("4")) {
    if (date!=null && !date.equals("")) {
        
    	%>
    
    <msh:section>
    <msh:sectionTitle>Результаты поиска за период с ${dateBegin} по ${dateEnd}.</msh:sectionTitle>
    </msh:section>
   
    <msh:section>
    <msh:sectionTitle>Свод по операциям</msh:sectionTitle>
    <msh:sectionContent>
    <ecom:webQuery name="Report36HOSPswod" nativeSql="

select vrspt.id||'&strcode='||vrspt.id as vrsptid,vrspt.name,vrspt.strCode 
,count(distinct so.id) as cntOper
,count(distinct sls.id) as cntPat
,count(distinct case when vot.code='1' then so.id else null end) as cntTech
,count(distinct case when sls.result_id='${result_death}' then sls.id else null end) as cntPatDeath
,count(distinct case when voo.code='2' then so.id else null end) as cntDeathOper
,count(distinct case when sls.result_id='${result_death}' and vot.code='1' then so.id else null end) as cntDeathTech
 from medcase sls
left join MedCase sloa on sloa.parent_id=sls.id
left join BedFund bf on bf.id=sloa.bedFund_id
left join MedCase sloall on sloall.parent_id=sls.id
left join surgicaloperation so on so.medCase_id=sloall.id
left join MedService ms on ms.id=so.medService_id
left join VocReportSetParameterType vrspt on vrspt.classname='F36_OPER'
left join ReportSetTYpeParameterType rspt on rspt.parameterType_id=vrspt.id
left join VocOperationTechnology vot on vot.id=so.technology_id
left join VocOperationOutcome voo on voo.id=so.outcome_id
left join Patient p on p.id=sls.patient_id
where 
sls.dtype='HospitalMedCase' and sls.dateFinish 
between to_date('${dateBegin}','dd.mm.yyyy') and to_date('${dateEnd}','dd.mm.yyyy')
and sloa.dateFinish is not null
${paramSql}
and  ms.additioncode between rspt.codefrom and rspt.codeto
${age_sql}  
group by vrspt.id,vrspt.name,vrspt.strCode
order by vrspt.strCode
" />
    <msh:table name="Report36HOSPswod" 
    viewUrl="stac_report_36.do?${paramHref}&typeAge=${typeAge}&typeView=${typeView}&department=${param.department}&typeAge=${typeAge}&noViewForm=1&short=Short&period=${dateBegin}-${dateEnd}" 
     action="stac_report_36.do?${paramHref}&typeAge=${typeAge}&typeView=${typeView}&department=${param.department}&typeAge=${typeAge}&noViewForm=1&period=${dateBegin}-${dateEnd}" idField="1" >
      <msh:tableColumn columnName="Наименование" property="2" />
      <msh:tableColumn columnName="№ строки" property="3" />
      <msh:tableColumn columnName="Кол-во операций" property="4"/>
      <msh:tableColumn columnName="Кол-во пациентов" property="5"/>
      <msh:tableColumn columnName="кол-во операций с испол. ВМТ" property="6"/>
      <msh:tableColumn columnName="Кол-во умерших пациентов" property="7"/>
      <msh:tableColumn columnName="Отмечено исход операции летальный" property="8"/>
      <msh:tableColumn columnName="Отмечено исход операции летальный с испол. ВМТ" property="9"/>
    </msh:table>
    
    </msh:sectionContent>
    </msh:section>
    <%} else if (period!=null && !period.equals("") 
    && strcode!=null && !strcode.equals("")) {
    	
    	String[] obj = period.split("-") ;
    	String dateBegin=obj[0] ;
    	dateEnd=obj[1];
    	request.setAttribute("dateBegin", dateBegin);
    	request.setAttribute("dateEnd", dateEnd);
    	
    		%>
    <msh:section>
    <msh:sectionTitle>Результаты поиска за период с ${dateBegin} по ${dateEnd}.</msh:sectionTitle>
    </msh:section>
   
    <msh:section>
    <msh:sectionTitle>Список пациентов ${param.strname}
    
    </msh:sectionTitle>
    <msh:sectionContent>
    <ecom:webQuery name="journal_surOperation" nativeSql="
    select 
so.id as soid
,list(vrspt1.strCode)
,ss.code as sscode
,p.lastname||' '||p.firstname||' '||p.middlename
,sls.dateStart,sls.dateFinish
,cast(to_char(sls.${dateAgeFld},'yyyy') as int)
-cast(to_char(p.birthday,'yyyy') as int)
+(case when (cast(to_char(sls.${dateAgeFld}, 'mm') as int)
-cast(to_char(p.birthday, 'mm') as int)
+(case when (cast(to_char(sls.${dateAgeFld},'dd') as int) 
- cast(to_char(p.birthday,'dd') as int)<0) then -1 else 0 end)
<0)
then -1 else 0 end)
 as age
,ms.code as vacode,ms.name as vaname,ms.additionCode as msadditionCode
from SurgicalOperation so
left join MedService ms on so.medService_id=ms.id
left join ReportSetTYpeParameterType rspt on ms.additioncode between rspt.codefrom and rspt.codeto
left join VocReportSetParameterType vrspt on rspt.parameterType_id=vrspt.id
left join ReportSetTYpeParameterType rspt1 on ms.additioncode between rspt.codefrom and rspt.codeto
left join VocReportSetParameterType vrspt1 on rspt1.parameterType_id=vrspt1.id
left join MedCase slo on slo.id=so.medCase_id
left join patient p on p.id=slo.patient_id
left join MedCase sls on sls.id=slo.parent_id
left join StatisticStub ss on ss.id=sls.statisticStub_id
where sls.dtype='HospitalMedCase' and sls.dateFinish between to_date('${dateBegin}','dd.mm.yyyy') 
    and to_date('${dateEnd}','dd.mm.yyyy')
and vrspt.id='${param.strcode}'
and vrspt1.classname='F36_OPER'
${paramSql} ${age_sql} 
group by so.id
,ss.code,p.lastname,p.firstname,p.middlename,p.birthday,sls.dateStart,sls.dateFinish
,ms.code ,ms.name,ms.additioncode
order by p.lastname,p.firstname,p.middlename " />
    <msh:table name="journal_surOperation" 
    viewUrl="entityShortView-stac_surOperation.do" 
     action="entityView-stac_surOperation.do" idField="1">
      <msh:tableColumn columnName="##" property="sn" />
      <msh:tableColumn columnName="Строки отчета" property="2" />
      <msh:tableColumn columnName="№стат. карт" property="3" />
      <msh:tableColumn columnName="ФИО пациента" property="4" />
      <msh:tableColumn columnName="Возраст" property="7" />
      <msh:tableColumn columnName="Дата поступления" property="5"/>
      <msh:tableColumn columnName="Дата выписки" property="6"/>
      <msh:tableColumn columnName="Код операции" property="8"/>
      <msh:tableColumn columnName="Наименование операции" property="9"/>
      <msh:tableColumn columnName="Код соответствия для 14 формы" property="10"/>
    </msh:table>
    </msh:sectionContent>
    </msh:section>    		

    		<%
    	} else {%>
    	<i>Нет данных </i>
    	<% }} 
    	
    	
    	%>
    
  </tiles:put>
</tiles:insert>