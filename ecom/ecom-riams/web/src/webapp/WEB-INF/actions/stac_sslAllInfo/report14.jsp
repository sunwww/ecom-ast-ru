<%@page import="ru.ecom.web.util.ActionUtil"%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://struts.apache.org/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://www.nuzmsh.ru/tags/msh" prefix="msh" %>
<%@ taglib uri="http://www.ecom-ast.ru/tags/ecom" prefix="ecom" %>
<%@ taglib tagdir="/WEB-INF/tags" prefix="tags" %>

<tiles:insert page="/WEB-INF/tiles/main${param.short}Layout.jsp" flush="true" >

  <tiles:put name="title" type="string">
    <msh:title mainMenu="StacJournal" title="14 форма"/>
  </tiles:put>
  <tiles:put name="side" type="string">

  </tiles:put>
  <tiles:put name="body" type="string">
  ${isReportBase}<ecom:webQuery isReportBase="${isReportBase}" name="sex_woman_sql" nativeSql="select id,name from VocSex where omccode='2'"/>
  ${isReportBase}<ecom:webQuery isReportBase="${isReportBase}" name="result_death_sql" nativeSql="select id,name from VocHospitalizationResult where code='11'"/>
  ${isReportBase}<ecom:webQuery isReportBase="${isReportBase}" name="orderType_amb_sql" nativeSql="select id,name from Omc_Frm where voc_code='К'"/>
  ${isReportBase}<ecom:webQuery isReportBase="${isReportBase}" name="diag_typeReg_direct_sql" nativeSql="select id,name from VocDiagnosisRegistrationType where code='2'"/>
  ${isReportBase}<ecom:webQuery isReportBase="${isReportBase}" name="diag_typeReg_cl_sql" nativeSql="select id,name from VocDiagnosisRegistrationType where code='3'"/>
  ${isReportBase}<ecom:webQuery isReportBase="${isReportBase}" name="diag_typeReg_pat_sql" nativeSql="select id,name from VocDiagnosisRegistrationType where code='5'"/>
  ${isReportBase}<ecom:webQuery isReportBase="${isReportBase}" name="diag_priority_m_sql" nativeSql="select id,name from VocPriorityDiagnosis where code='1'"/>
  <%
  	String noViewForm = request.getParameter("noViewForm") ;
  	
  	ActionUtil.getValueByList("sex_woman_sql", "sex_woman", request) ;
  	String sexWoman = (String)request.getAttribute("sex_woman") ;
  	String typeAge=ActionUtil.updateParameter("Report14","typeAge","1", request) ;
  	String typeView=ActionUtil.updateParameter("Report14","typeView","1", request) ;
  	String typeDate=ActionUtil.updateParameter("Report14","typeDate","2", request) ;
  	String dateAge="dateStart" ;
  	if (typeDate!=null && typeDate.equals("2")) {
  		dateAge="dateFinish" ;
  	}
  	request.setAttribute("dateAgeFld", dateAge) ;
  	if (typeAge!=null &&typeAge.equals("1")) {
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
  	} else if (typeAge!=null &&typeAge.equals("2")) {
  		StringBuilder age = new StringBuilder() ;
  		age.append(" and cast(to_char(sls.").append(dateAge).append(",'yyyy') as int)")
			.append(" -cast(to_char(p.birthday,'yyyy') as int)")
			.append(" +(case when (cast(to_char(sls.").append(dateAge).append(", 'mm') as int)")
			.append(" -cast(to_char(p.birthday, 'mm') as int)")
			.append(" +(case when (cast(to_char(sls.").append(dateAge).append(",'dd') as int)") 
			.append(" - cast(to_char(p.birthday,'dd') as int)<0) then -1 else 0 end)")
			.append(" <0)")
			.append(" then -1 else 0 end) >= case when p.sex_id='").append(sexWoman).append("' then 55 else 60 end ") ;
		request.setAttribute("age_sql", age.toString()) ;
  		request.setAttribute("reportInfo", "Б. Взрослые старше трудоспособного возраста") ;
  	} else if (typeAge!=null &&typeAge.equals("3")) {
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
  	} else if (typeAge!=null &&typeAge.equals("4")) {
  		StringBuilder age = new StringBuilder() ;
  		age.append(" and sls.").append(dateAge).append("-p.birthday < 7 ") ;
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
			.append(" then -1 else 0 end) < 1 ") ;
		request.setAttribute("age_sql", age.toString()) ;
  		request.setAttribute("reportInfo", "до года") ;
  	} else if (typeAge!=null &&typeAge.equals("6")) {
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
  	} else if (typeAge!=null &&typeAge.equals("7")) {
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
  	} else if (typeAge!=null &&typeAge.equals("8")) {
  		StringBuilder age = new StringBuilder() ;
  		age.append(" and cast(to_char(sls.").append(dateAge).append(",'yyyy') as int)")
			.append(" -cast(to_char(p.birthday,'yyyy') as int)")
			.append(" +(case when (cast(to_char(sls.").append(dateAge).append(", 'mm') as int)")
			.append(" -cast(to_char(p.birthday, 'mm') as int)")
			.append(" +(case when (cast(to_char(sls.").append(dateAge).append(",'dd') as int)") 
			.append(" - cast(to_char(p.birthday,'dd') as int)<0) then -1 else 0 end)")
			.append(" <0)")
			.append(" then -1 else 0 end) between 0 and 17 ") ;
		request.setAttribute("age_sql", age.toString()) ;
  		request.setAttribute("reportInfo", "0-17") ;
  	} else if (typeAge!=null &&typeAge.equals("9")) {
  		StringBuilder age = new StringBuilder() ;
  		age.append(" and cast(to_char(sls.").append(dateAge).append(",'yyyy') as int)")
			.append(" -cast(to_char(p.birthday,'yyyy') as int)")
			.append(" +(case when (cast(to_char(sls.").append(dateAge).append(", 'mm') as int)")
			.append(" -cast(to_char(p.birthday, 'mm') as int)")
			.append(" +(case when (cast(to_char(sls.").append(dateAge).append(",'dd') as int)") 
			.append(" - cast(to_char(p.birthday,'dd') as int)<0) then -1 else 0 end)")
			.append(" <0)")
  			.append(" then -1 else 0 end) between 16 and case when p.sex_id='")
  			.append(sexWoman).append("' then 54 else 59 end ") ;
		request.setAttribute("age_sql", age.toString()) ;
  		request.setAttribute("reportInfo", "А.1. Взрослые трудоспособного возраста с 16 лет") ;
  	} else if (typeAge!=null &&typeAge.equals("10")) {
  		StringBuilder age = new StringBuilder() ;
  		age.append(" and cast(to_char(sls.").append(dateAge).append(",'yyyy') as int)")
			.append(" -cast(to_char(p.birthday,'yyyy') as int)")
			.append(" +(case when (cast(to_char(sls.").append(dateAge).append(", 'mm') as int)")
			.append(" -cast(to_char(p.birthday, 'mm') as int)")
			.append(" +(case when (cast(to_char(sls.").append(dateAge).append(",'dd') as int)") 
			.append(" - cast(to_char(p.birthday,'dd') as int)<0) then -1 else 0 end)")
			.append(" <0)")
  			.append(" then -1 else 0 end) between 18 and case when p.sex_id='")
  			.append(sexWoman).append("' then 54 else 59 end ") ;
		request.setAttribute("age_sql", age.toString()) ;
  		request.setAttribute("reportInfo", "А.1. Взрослые трудоспособного возраста с 18 лет") ;
  	}
  	//request.setAttribute("result_death", "6") ;
  	//request.setAttribute("orderType_amb", "3") ;
  	ActionUtil.getValueByList("result_death_sql", "result_death", request) ;
  	ActionUtil.getValueByList("orderType_amb_sql", "orderType_amb", request) ;
  	StringBuilder paramSql= new StringBuilder() ;
  	StringBuilder paramHref= new StringBuilder() ;
  	
  	paramSql.append(" ").append(ActionUtil.setParameterFilterSql("sex", "p.sex_id", request)) ;
  	//--old---paramSql.append(" ").append(ActionUtil.setParameterFilterSql("department", "sloa.department_id", request)) ;
  	String view = (String)request.getAttribute("typeView") ;

  	if (view.equals("5")) {
  		paramSql.append(" ").append(ActionUtil.setParameterManyFilterSql("departments","departments", "sls.department_id", request)) ;
  	} else {
  		paramSql.append(" ").append(ActionUtil.setParameterManyFilterSql("departments","departments", "sloa.department_id", request)) ;
  	}
  	paramSql.append(" ").append(ActionUtil.setParameterFilterSql("hospType", "sls.hospType_id", request)) ;
  	paramSql.append(" ").append(ActionUtil.setParameterFilterSql("serviceStream", "sls.serviceStream_id", request)) ;
  	paramSql.append(" ").append(ActionUtil.setParameterFilterSql("additionStatus", "p.additionStatus_id", request)) ;
  	paramSql.append(" ").append(ActionUtil.setParameterFilterSql("bedType", "bf.bedType_id", request)) ;
  	paramSql.append(" ").append(ActionUtil.setParameterFilterSql("bedSubType", "bf.bedSubType_id", request)) ;
  	/*
  	ActionUtil.setParameterFilterSql("sex", "p.sex_id", request) ;
  	ActionUtil.setParameterFilterSql("department", "sloa.department_id", request) ;
  	ActionUtil.setParameterFilterSql("hospType", "sls.hospType_id", request) ;
  	
  	paramSql.append(" ").append(request.getAttribute("departmentSql")!=null?request.getAttribute("departmentSql"):"") ;
  	paramSql.append(" ").append(request.getAttribute("hospTypeSql")!=null?request.getAttribute("hospTypeSql"):"") ;
  	paramSql.append(" ").append(request.getAttribute("sexSql")!=null?request.getAttribute("sexSql"):"") ;
  	*/
  	paramHref.append("sex=").append(request.getParameter("sex")!=null?request.getParameter("sex"):"") ;
  	paramHref.append("&hospType=").append(request.getParameter("hospType")!=null?request.getParameter("hospType"):"") ;
  	paramHref.append("&serviceStream=").append(request.getParameter("serviceStream")!=null?request.getParameter("serviceStream"):"") ;
  	
  	paramHref.append("&bedType=").append(request.getParameter("bedType")!=null?request.getParameter("bedType"):"") ;
  	paramHref.append("&bedSubType=").append(request.getParameter("bedSubType")!=null?request.getParameter("bedSubType"):"") ;
  	request.setAttribute("paramSql", paramSql.toString()) ;
  	request.setAttribute("paramHref", paramHref.toString()) ;
  	ActionUtil.getValueByList("diag_typeReg_cl_sql", "diag_typeReg_cl", request) ;
  	ActionUtil.getValueByList("diag_typeReg_pat_sql", "diag_typeReg_pat", request) ;
  	ActionUtil.getValueByList("diag_priority_m_sql", "diag_priority_m", request) ;
  	ActionUtil.getValueByList("diag_typeReg_direct_sql", "diag_typeReg_direct", request) ;
  	//request.setAttribute("diag_typeReg_cl", "4") ;
  	//request.setAttribute("diag_typeReg_pat", "5") ;
  	//request.setAttribute("diag_priority_m", "1") ;
  	if (noViewForm!=null && noViewForm.equals("1")) {
  	} else {
  		
  %>
    <msh:form action="/stac_report_14.do" defaultField="dateBegin" disableFormDataConfirm="true" method="GET">
    <input type="hidden" name="s" id="s" value="HospitalPrintReport" />
    <input type="hidden" name="m" id="m" value="printReport14" />
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
        	<input type="radio" name="typeAge" value="4">  0-6 дней
        </td>
        <td onclick="this.childNodes[1].checked='checked';">
        	<input type="radio" name="typeAge" value="5">  до 1 года
        </td>
        <td onclick="this.childNodes[1].checked='checked';">
        	<input type="radio" name="typeAge" value="6"  >  0-14 лет
        </td>
        <td onclick="this.childNodes[1].checked='checked';">
        	<input type="radio" name="typeAge" value="7"  >  15-17 лет
        </td>
        <td onclick="this.childNodes[1].checked='checked';">
        	<input type="radio" name="typeAge" value="8"  >  0-17 лет
        </td>
        </msh:row>
        <msh:row>
        <td class="label" title="Возрастная группа (typeAge)" colspan="1"></td>
         <td onclick="this.childNodes[1].checked='checked';" colspan="3">
        	<input type="radio" name="typeAge" value="9"  >  взрослые труд. возраста с 16 лет
        </td>
        </msh:row>
        <msh:row>
        <td class="label" title="Возрастная группа (typeAge)" colspan="1"></td>
         <td onclick="this.childNodes[1].checked='checked';" colspan="3">
        	<input type="radio" name="typeAge" value="10"  >  взрослые труд. возраста с 18 лет
        </td>
        <td onclick="this.childNodes[1].checked='checked';">
        	<input type="radio" name="typeAge" value="11"  >  все
        </td>
       </msh:row>
      <msh:row>
        <td class="label" title="Просмотр данных (typeView)" colspan="1"><label for="typeViewName" id="typeViewLabel">Отобразить:</label></td>
        <td onclick="this.childNodes[1].checked='checked';" colspan="2">
        	<input type="radio" name="typeView" value="1"> по выбывшие по отд.
        </td>
        <td onclick="this.childNodes[1].checked='checked';" colspan="2">
        	<input type="radio" name="typeView" value="2"> по нозоологиям (выписанные)
        </td>
        <td onclick="this.childNodes[1].checked='checked';" colspan="2">
        	<input type="radio" name="typeView" value="3"> по нозоологиям (переведенные)
        </td>
       </msh:row>
        <msh:row>
        <td></td>
        <td onclick="this.childNodes[1].checked='checked';" colspan="2">
        	<input type="radio" name="typeView" value="4"> по нозоологиям (выпис.+перев.)
        </td>
        <td onclick="this.childNodes[1].checked='checked';" colspan="2">
        	<input type="radio" name="typeView" value="5"> по нозоологиям (направит.)
        </td>
        <td onclick="this.childNodes[1].checked='checked';" colspan="2">
        	<input type="radio" name="typeView" value="6"> по нозоологиям (умершие)
        </td>
       </msh:row>
        <msh:row>
        <td></td>
        <td onclick="this.childNodes[1].checked='checked';">
        	<input type="radio" name="typeView" value="7"> по операциям
        </td>
        <td onclick="this.childNodes[1].checked='checked';" colspan="2">
        	<input type="radio" name="typeView" value="8"> по выбывшие по доп.статусу
        </td>
        <td onclick="this.childNodes[1].checked='checked';">
        	<input type="radio" name="typeView" value="9"> по операции без кода
        </td>
        <td onclick="this.childNodes[1].checked='checked';" colspan="2">
        	<input type="radio" name="typeView" value="10"> по нозоологиям (умершие) ДОСУТОЧНЫЕ
        </td>       </msh:row>
        <msh:row>
	        <ecom:oneToManyOneAutocomplete label="Отделения" vocName="vocLpuHospOtdAll" property="departments" colSpan="10"/>
        </msh:row>
        <msh:row>
        	<msh:autoComplete property="hospType" fieldColSpan="4" horizontalFill="true" label="Тип стационара" vocName="vocHospType"/>
        </msh:row>
        <msh:row>
        	<msh:autoComplete property="serviceStream" fieldColSpan="4" horizontalFill="true" label="Поток обслуживания" vocName="vocServiceStream"/>
        </msh:row>
        <msh:row>
        	<msh:autoComplete property="sex" fieldColSpan="4" horizontalFill="true" label="Пол" vocName="vocSex"/>
        </msh:row>
        <msh:row>
        	<msh:autoComplete property="additionStatus" fieldColSpan="4" horizontalFill="true" label="Доп.статус пациента" vocName="vocAdditionStatus"/>
        </msh:row>
        <msh:row>
        	<msh:autoComplete property="bedType" fieldColSpan="2" horizontalFill="true" label="Профиль коек" vocName="vocBedType"/>
        	<msh:autoComplete property="bedSubType" fieldColSpan="2" horizontalFill="true" label="Тип коек" vocName="vocBedSubType"/>
        </msh:row>        
      <msh:row>
        <msh:textField property="dateBegin" label="Период с" />
        <msh:textField property="dateEnd" label="по" />
           <td>
            <input type="button" onclick="this.disabled=true; this.form.submit();" value="Найти" />
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
    function print() {
    	var frm = document.forms[0] ;
    	frm.m.value="printHistology" ;
    	frm.target='_blank' ;
    	frm.action='print-stac_histology.do' ;
    	$('id').value = $('dateBegin').value+":"
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
    
    String date = request.getParameter("dateBegin") ;
    String dateEnd = request.getParameter("dateEnd") ;
    //String id = (String)request.getParameter("id") ;
    String period = request.getParameter("period") ;
    String strcode =request.getParameter("strcode") ;
    if (dateEnd==null || dateEnd.equals("")) dateEnd=date ;
    request.setAttribute("dateBegin", date) ;
    request.setAttribute("dateEnd", dateEnd) ;
    //request.setAttribute("isReportBase", ActionUtil.isReportBase(date, dateEnd,request));));
    
    if (view.equals("1")) {
        if (date!=null && !date.equals("")) {
           
            request.setAttribute("isReportBase", ActionUtil.isReportBase(date, dateEnd,request));
            %>
       
        <msh:section>
        <msh:sectionTitle>Результаты поиска за период с ${dateBegin} по ${dateEnd}.</msh:sectionTitle>
        </msh:section>
      
        <msh:section>
        <msh:sectionTitle>Свод по отделениям</msh:sectionTitle>
        <msh:sectionContent>
        ${isReportBase}
        <ecom:webQuery isReportBase="${isReportBase}" name="report14swod"
       nameFldSql="v1_report14swod" nativeSql="
    select
    ${departmentsSqlId} as mlid,ml.name as mlname
    ,count(case when sls.result_id!=${result_death}  and vho.code='1' then sls.id else null end) as cntNoDeath
    ,count(case when sls.result_id!=${result_death}  and vho.code='1' and sls.emergency='1' then sls.id else null end) as cntNoDeathEmer
    ,count(case when sls.result_id!=${result_death}  and vho.code='1' and sls.emergency='1' and sls.orderType_id='3' then sls.id else null end) as cntNoDeathEmerSk
    ,sum(case when sls.result_id!=${result_death} then
    case
     when (sls.dateFinish-sls.dateStart)=0 then 1
     when bf.addCaseDuration='1' then (sls.dateFinish-sls.dateStart+1)
     else (sls.dateFinish-sls.dateStart)
    end
    else 0 end) as sumDays
    ,count(case when sls.result_id=${result_death} then sls.id else null end) as cntDeath
    ,count(distinct sls.patient_id) as cntPat
    ,count(distinct case when (oo.id is null or oo.voc_code='643') and (ad.addressid is null or ad.kladr not like '30%') then sls.patient_id else null end) as cntPatInog
    ,count(distinct case when (oo.id is not null and oo.voc_code!='643') then sls.patient_id else null end) as cntPatInostr
    ,sum(case when sls.result_id=${result_death} then
    case
     when (sls.dateFinish-sls.dateStart)=0 then 1
     when bf.addCaseDuration='1' then (sls.dateFinish-sls.dateStart+1)
     else (sls.dateFinish-sls.dateStart)
    end
    else 0 end) as sumDaysDeath
    ,count(case when sls.result_id!=${result_death} and vho.code!='1' then sls.id else null end) as cntNoDeathTransfer
     from medcase sls
    left join vochospitalizationoutcome vho on vho.id=sls.outcome_id
    left join Patient p on p.id=sls.patient_id
    left join MedCase sloa on sloa.parent_id=sls.id
    left join MisLpu ml on ml.id=sloa.department_id
    left join BedFund bf on bf.id=sloa.bedFund_id
    left join Address2 ad on p.address_AddressId=ad.addressid
    left join Omc_Oksm oo on oo.id=p.nationality_id
    where
    sls.dtype='HospitalMedCase' and sls.dateFinish
    between to_date('${dateBegin}','dd.mm.yyyy') and to_date('${dateEnd}','dd.mm.yyyy')
    ${paramSql} and sloa.dateFinish is not null
    ${age_sql}
    group by sloa.department_id,ml.name
    order by ml.name
    " />
        <msh:table printToExcelButton="Сохранить в excel" name="report14swod"
        viewUrl="stac_report_14.do?${paramHref}&additionStatus=${param.additionStatus}&typeAge=${typeAge}&typeView=${typeView}&typeAge=${typeAge}&noViewForm=1&short=Short&period=${dateBegin}-${dateEnd}"
         action="stac_report_14.do?${paramHref}&additionStatus=${param.additionStatus}&typeAge=${typeAge}&typeView=${typeView}&typeAge=${typeAge}&noViewForm=1&period=${dateBegin}-${dateEnd}" idField="1" >
          <msh:tableColumn columnName="Отделение" property="2" />
          <msh:tableColumn isCalcAmount="true" columnName="Кол-во переведнных" property="12"/>
          <msh:tableColumn isCalcAmount="true" columnName="Кол-во выписанных" property="3"/>
          <msh:tableColumn isCalcAmount="true" columnName="из них доставленых по экстренным показаниям" property="4"/>
          <msh:tableColumn isCalcAmount="true" columnName="из них экст. пациентов, доставленных скорой мед.помощью" property="5"/>
          <msh:tableColumn isCalcAmount="true" columnName="Проведено выписанными койко-дней" property="6"/>
          <msh:tableColumn isCalcAmount="true" columnName="Умерло" property="7"/>
          <msh:tableColumn isCalcAmount="true" columnName="Умерло (койко-дней)" property="11"/>
          <msh:tableColumn isCalcAmount="true" columnName="Кол-во больных (чел)" property="8"/>
          <msh:tableColumn isCalcAmount="true" columnName="Кол-во иног (чел)" property="9"/>
          <msh:tableColumn isCalcAmount="true" columnName="Кол-во иностр (чел)" property="10"/>
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
            request.setAttribute("isReportBase", ActionUtil.isReportBase(dateBegin, dateEnd,request));
           
                %>
        <msh:section>
        <msh:sectionTitle>Результаты поиска за период с ${dateBegin} по ${dateEnd}.</msh:sectionTitle>
        </msh:section>
      
        <msh:section>
        <msh:sectionTitle>Список пациентов ${param.strname}
       
        </msh:sectionTitle>
        <msh:sectionContent>
        ${isReportBase}<ecom:webQuery isReportBase="${isReportBase}" name="journal_surOperation" nativeSql="
    select
    sls.id as slsid,(select list(vrspt.strCode) from ReportSetTYpeParameterType rspt 
    left join VocReportSetParameterType vrspt on rspt.parameterType_id=vrspt.id
    where vrspt.classname='F14_DIAG' and coalesce(
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
     ,coalesce(
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
        ) as mkbcode
     ,case when sls.result_id='${result_death}' then 'Да' else null end as isDeath
    ,case when sls.emergency='1' then 'Да' else null end as emer
    ,case when sls.emergency='1' and sls.orderType_id='${orderType_amb}' then 'Да' else null end as emerSk
    ,vho.name as vhoname
     from medcase sls
    left join StatisticStub ss on ss.id=sls.statisticStub_id
    left join VocHospitalizationResult vhr on vhr.id=sls.result_id
    left join MedCase sloa on sloa.parent_id=sls.id
    left join BedFund bf on bf.id=sloa.bedFund_id
    left join Patient p on p.id=sls.patient_id
    left join vochospitalizationoutcome vho on vho.id=sls.outcome_id

    where sls.dtype='HospitalMedCase'
    and sls.dateFinish between to_date('${dateBegin}','dd.mm.yyyy')
        and to_date('${dateEnd}','dd.mm.yyyy')

    and sloa.dateFinish is not null
    ${paramSql}
    ${age_sql} 

    group by sls.id
    ,ss.code,sls.emergency,sls.orderType_id,p.lastname,p.firstname
    ,p.middlename,p.birthday,sls.dateStart,sls.dateFinish
    ,bf.addCaseDuration,sls.result_id,vho.name
    order by p.lastname,p.firstname,p.middlename " />
        <msh:table printToExcelButton="Сохранить в excel" name="journal_surOperation"
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
          <msh:tableColumn columnName="Исход" property="13"/>

        </msh:table>
        </msh:sectionContent>
        </msh:section>           
                <%
            } else {%>
            <i>Нет данных </i>
            <% }
        } 
    
    if (view.equals("8")) {
    if (date!=null && !date.equals("")) {
    	request.setAttribute("isReportBase", ActionUtil.isReportBase(date, dateEnd,request));
    	%>
    
    <msh:section>
    <msh:sectionTitle>Результаты поиска за период с ${dateBegin} по ${dateEnd}.</msh:sectionTitle>
    </msh:section>
   
    <msh:section>
    <msh:sectionTitle>Свод по дополнительному статусу</msh:sectionTitle>
        <msh:sectionContent>
    ${isReportBase}<ecom:webQuery isReportBase="${isReportBase}" name="report14swod" nativeSql="
select 
'&additionStatus='||vas.id as vasid,vas.name as mlname
,count(distinct sls.patient_id) as cntPat
,count(distinct case when (oo.id is null or oo.voc_code='643') and (ad.addressid is null or ad.kladr not like '30%') then sls.patient_id else null end) as cntPatInog
,count(distinct case when (oo.id is not null and oo.voc_code!='643') then sls.patient_id else null end) as cntPatInostr
,count(distinct sls.id) as cntAll
,count(distinct case when (oo.id is null or oo.voc_code='643') and (ad.addressid is null or ad.kladr not like '30%') then sls.id else null end) as cntSmoInog
,count(distinct case when (oo.id is not null and oo.voc_code!='643') then sls.id else null end) as cntSmoInostr
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
left join VocAdditionStatus vas on vas.id=p.additionStatus_id
left join MedCase sloa on sloa.parent_id=sls.id
left join MisLpu ml on ml.id=sloa.department_id
left join BedFund bf on bf.id=sloa.bedFund_id
left join Address2 ad on p.address_AddressId=ad.addressid
left join Omc_Oksm oo on oo.id=p.nationality_id
where 
sls.dtype='HospitalMedCase' and sls.dateFinish 
between to_date('${dateBegin}','dd.mm.yyyy') and to_date('${dateEnd}','dd.mm.yyyy')
${paramSql} and sloa.dateFinish is not null
${age_sql}
group by vas.id,vas.name
order by vas.name
" />
    <msh:table printToExcelButton="Сохранить в excel" name="report14swod"
    viewUrl="stac_report_14.do?${paramHref}${departmentsUrlId}&typeAge=${typeAge}&typeView=${typeView}&typeAge=${typeAge}&noViewForm=1&short=Short&period=${dateBegin}-${dateEnd}" 
     action="stac_report_14.do?${paramHref}${departmentsUrlId}&typeAge=${typeAge}&typeView=${typeView}&typeAge=${typeAge}&noViewForm=1&period=${dateBegin}-${dateEnd}" idField="1" >
      <msh:tableColumn columnName="Доп.статус" property="2" />
      <msh:tableColumn isCalcAmount="true" columnName="Кол-во больных (чел)" property="3"/>
      <msh:tableColumn isCalcAmount="true" columnName="Кол-во иног (чел)" property="4"/>
      <msh:tableColumn isCalcAmount="true" columnName="Кол-во иностр (чел)" property="5"/>
      <msh:tableColumn isCalcAmount="true" columnName="Кол-во выбывших (госпитал.)" property="6"/>
      <msh:tableColumn isCalcAmount="true" columnName="Кол-во выбывших иног. (госпитал.)" property="7"/>
      <msh:tableColumn isCalcAmount="true" columnName="Кол-во выбывших иностр. (госпитал.)" property="8"/>
      <msh:tableColumn isCalcAmount="true" columnName="Кол-во выписанных" property="9"/>
      <msh:tableColumn isCalcAmount="true" columnName="из них доставленых по экстренным показаниям" property="10"/>
      <msh:tableColumn isCalcAmount="true" columnName="из них экст. пациентов, доставленных скорой мед.помощью" property="11"/>
      <msh:tableColumn isCalcAmount="true" columnName="Проведено выписанными койко-дней" property="12"/>
      <msh:tableColumn isCalcAmount="true" columnName="Умерло (чел.)" property="13"/>
    </msh:table>
    
    </msh:sectionContent>
    </msh:section>
    <%
    } else if (period!=null && !period.equals("") 
    ) {
    	
    	String[] obj = period.split("-") ;
    	String dateBegin=obj[0] ;
    	dateEnd=obj[1];
    	request.setAttribute("dateBegin", dateBegin);
    	request.setAttribute("dateEnd", dateEnd);
    	request.setAttribute("isReportBase", ActionUtil.isReportBase(dateBegin, dateEnd,request));
    		%>
    <msh:section>
    <msh:sectionTitle>Результаты поиска за период с ${dateBegin} по ${dateEnd}.</msh:sectionTitle>
    </msh:section>
   
    <msh:section>
    <msh:sectionTitle>Список пациентов ${param.strname}
    
    </msh:sectionTitle>
    <msh:sectionContent>
    ${isReportBase}<ecom:webQuery isReportBase="${isReportBase}" name="journal_surOperation" nativeSql="
select 
sls.id as slsid,(select list(vrspt.strCode) from ReportSetTYpeParameterType rspt  
left join VocReportSetParameterType vrspt on rspt.parameterType_id=vrspt.id
where vrspt.classname='F14_DIAG' and mkb.code between rspt.codefrom and rspt.codeto
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
    <msh:table printToExcelButton="Сохранить в excel" name="journal_surOperation"
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
    	<% }
    } 
    
    if (view.equals("2")||view.equals("3")||view.equals("4")||view.equals("5")) {
    	if (view.equals("2")) {
    		request.setAttribute("outcomeSql", "and vho.code='1'") ;
    		request.setAttribute("outcomeInfo", "выписанные");
    	} else if (view.equals("3")) {
    		request.setAttribute("outcomeSql", " and vho.code!='1'") ;
    		request.setAttribute("outcomeInfo", "переведенные");
    	} else if (view.equals("4")) {
    		request.setAttribute("outcomeSql", " ") ;
    		request.setAttribute("outcomeInfo", "выписанные и переведенные");
    	} else if (view.equals("5")) {
    		request.setAttribute("outcomeSql", " ") ;
    		request.setAttribute("outcomeInfo", "направленные");
    	} 
    	if (view.equals("5")) {
    		request.setAttribute("diagnosisCodeSql", " and vdrt.id='"+request.getAttribute("diag_typeReg_direct")+"' ") ;
    	} else {
    		request.setAttribute("diagnosisCodeSql", " and vdrt.id='"+request.getAttribute("diag_typeReg_cl")+"' and vpd.id='"+request.getAttribute("diag_priority_m")+"' and sls.result_id!='"+request.getAttribute("result_death")+"'") ;
    	}
    if (date!=null && !date.equals("")) {
	if (view.equals("2")) {
		request.setAttribute("periodSql"," and sls.dateFinish between to_date('"+request.getAttribute("dateBegin")+"','dd.mm.yyyy') and to_date('"+request.getAttribute("dateEnd")+"','dd.mm.yyyy') and sloa.datefinish is not null") ;
    	} else if (view.equals("3")) {
		request.setAttribute("periodSql"," and sls.dateFinish between to_date('"+request.getAttribute("dateBegin")+"','dd.mm.yyyy') and to_date('"+request.getAttribute("dateEnd")+"','dd.mm.yyyy') and sloa.datefinish is not null") ;
    	} else if (view.equals("4")) {
		request.setAttribute("periodSql"," and sls.dateFinish between to_date('"+request.getAttribute("dateBegin")+"','dd.mm.yyyy') and to_date('"+request.getAttribute("dateEnd")+"','dd.mm.yyyy') and sloa.datefinish is not null") ;
    	} else if (view.equals("5")) {
		request.setAttribute("periodSql"," and sls.dateStart between to_date('"+request.getAttribute("dateBegin")+"','dd.mm.yyyy') and to_date('"+request.getAttribute("dateEnd")+"','dd.mm.yyyy') and sloa.prevmedcase_id is null and sls.deniedHospitalizating_id is null") ;
    	} 

    	request.setAttribute("isReportBase", ActionUtil.isReportBase(date, dateEnd,request));
    	%>
    
    <msh:section>
    <msh:sectionTitle>Результаты поиска за период с ${dateBegin} по ${dateEnd}.</msh:sectionTitle>
    </msh:section>
   
    <msh:section>
    ${isReportBase}<ecom:webQuery isReportBase="${isReportBase}" name="report14swod" nameFldSql="report14swod_sql" nativeSql="
    select vrspt.id||'&strcode='||vrspt.id,vrspt.name,vrspt.strCode,vrspt.code 
,count(distinct sls.id) as cntNoDeath
,count(distinct case when sls.emergency='1' then sls.id else null end) as cntNoDeathEmer
,count(distinct case when sls.emergency='1' and sls.orderType_id='${orderType_amb}' then sls.id else null end) as cntNoDeathEmerSk
,sum(
case 
 when (sls.dateFinish-sls.dateStart)=0 then 1 
 when bf.addCaseDuration='1' then (sls.dateFinish-sls.dateStart+1) 
 else (sls.dateFinish-sls.dateStart)
end
) as sumDays
 from medcase sls
left join Patient p on p.id=sls.patient_id
left join vochospitalizationoutcome vho on vho.id=sls.outcome_id
left join MedCase sloa on sloa.parent_id=sls.id
left join BedFund bf on bf.id=sloa.bedFund_id
left join diagnosis diag on sls.id=diag.medcase_id
left join vocidc10 mkb on mkb.id=diag.idc10_id
left join VocDiagnosisRegistrationType vdrt on vdrt.id=diag.registrationType_id
left join VocPriorityDiagnosis vpd on vpd.id=diag.priority_id
left join VocReportSetParameterType vrspt on vrspt.classname='F14_DIAG'
left join ReportSetTYpeParameterType rspt on rspt.parameterType_id=vrspt.id
where
sls.dtype='HospitalMedCase' ${periodSql}
${paramSql} ${diagnosisCodeSql}
and mkb.code between rspt.codefrom and rspt.codeto
 ${outcomeSql}
${age_sql} 
 
group by vrspt.id,vrspt.name,vrspt.strCode,vrspt.code
order by vrspt.strCode
" />
    <msh:sectionTitle>Свод по нозоологиям (${outcomeInfo})
    
    	    <form action="print-report_14_2.do" method="post" target="_blank">
	    Свод по нозоологиям (${outcomeInfo})
	    <input type='hidden' name="sqlText" id="sqlText" value="${report14swod_sql}"> 
	    <input type='hidden' name="sqlInfo" id="sqlInfo" value="Свод по нозоологиям (${outcomeInfo}) за ${param.dateBegin}-${dateEnd}.">
	    <input type='hidden' name="sqlColumn" id="sqlColumn" value="${groupName}">
	    <input type='hidden' name="s" id="s" value="PrintService"><input type='hidden' name="isReportBase" id="isReportBase" value="${isReportBase}">
	    <input type='hidden' name="m" id="m" value="printNativeQuery">
	    <input type="submit" value="Печать"> 
	    </form>     
    </msh:sectionTitle>
    <msh:sectionContent>

    <msh:table printToExcelButton="Сохранить в excel" name="report14swod"
    viewUrl="stac_report_14.do?${paramHref}&typeAge=${typeAge}&typeView=${typeView}${departmentsUrlId}&additionStatus=${param.additionStatus}&typeAge=${typeAge}&noViewForm=1&short=Short&period=${dateBegin}-${dateEnd}" 
     action="stac_report_14.do?${paramHref}&typeAge=${typeAge}&typeView=${typeView}${departmentsUrlId}&additionStatus=${param.additionStatus}&typeAge=${typeAge}&noViewForm=1&period=${dateBegin}-${dateEnd}" idField="1" >
      <msh:tableColumn columnName="Наименование" property="2" />
      <msh:tableColumn columnName="№ строки" property="3" />
      <msh:tableColumn columnName="Код МКБ10" property="4" />
      <msh:tableColumn columnName="Кол-во госпитализаций (${outcomeInfo})" property="5"/>
      <msh:tableColumn columnName="из них доставленых по экстренным показаниям" property="6"/>
      <msh:tableColumn columnName="из них экст. пациентов, доставленных скорой мед.помощью" property="7"/>
      <msh:tableColumn columnName="Проведено выписанными койко-дней" property="8"/>
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
    	request.setAttribute("isReportBase", ActionUtil.isReportBase(dateBegin, dateEnd,request));
	if (view.equals("2")) {
		request.setAttribute("periodSql"," and sls.dateFinish between to_date('"+dateBegin+"','dd.mm.yyyy') and to_date('"+dateEnd+"','dd.mm.yyyy') and sloa.datefinish is not null") ;
    	} else if (view.equals("3")) {
		request.setAttribute("periodSql"," and sls.dateFinish between to_date('"+dateBegin+"','dd.mm.yyyy') and to_date('"+dateEnd+"','dd.mm.yyyy') and sloa.datefinish is not null") ;
    	} else if (view.equals("4")) {
		request.setAttribute("periodSql"," and sls.dateFinish between to_date('"+dateBegin+"','dd.mm.yyyy') and to_date('"+dateEnd+"','dd.mm.yyyy') and sloa.datefinish is not null") ;
    	} else if (view.equals("5")) {
		request.setAttribute("periodSql"," and sls.dateStart between to_date('"+dateBegin+"','dd.mm.yyyy') and to_date('"+dateEnd+"','dd.mm.yyyy') and sloa.prevmedcase_id is null") ;
    	} 

    		%>
    <msh:section>
    <msh:sectionTitle>Результаты поиска за период с ${dateBegin} по ${dateEnd}.</msh:sectionTitle>
    </msh:section>
   
    <msh:section>
    <msh:sectionTitle> 
    <ecom:webQuery isReportBase="${isReportBase}" nameFldSql="journal_surOperation_sql" name="journal_surOperation" nativeSql="
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
,vho.name as vhoname
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
left join vochospitalizationoutcome vho on vho.id=sls.outcome_id
where sls.dtype='HospitalMedCase' ${periodSql} and vrspt.id='${param.strcode}'
${paramSql}
${diagnosisCodeSql}
 ${outcomeSql}
${age_sql}  
and vrspt1.classname='F14_DIAG' 
group by sls.id
,ss.code,sls.emergency,sls.orderType_id,p.lastname,p.firstname
,p.middlename,p.birthday,sls.dateStart,sls.dateFinish
,bf.addCaseDuration,sls.result_id,mkb.code,vho.name
order by p.lastname,p.firstname,p.middlename " />
    <form action="print-stac_report14_r2.do" method="post" target="_blank">
	    Реестр пациентов (${outcomeInfo}) ${param.strname}
	    <input type='hidden' name="sqlText" id="sqlText" value="${journal_surOperation_sql}"> 
	    <input type='hidden' name="sqlInfo" id="sqlInfo" value="Реестр пациентов (${outcomeInfo}) ${param.strname} за ${param.dateBegin}-${dateEnd}.">
	    <input type='hidden' name="sqlColumn" id="sqlColumn" value="${groupName}">
	    <input type='hidden' name="s" id="s" value="PrintService"><input type='hidden' name="isReportBase" id="isReportBase" value="${isReportBase}">
	    <input type='hidden' name="m" id="m" value="printNativeQuery">
	    <input type="submit" value="Печать"> 
	    </form>     
    </msh:sectionTitle>
    <msh:sectionContent>
    ${isReportBase}
    <msh:table printToExcelButton="Сохранить в excel" name="journal_surOperation"
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
      <msh:tableColumn columnName="Исход" property="13"/>
    </msh:table>
    </msh:sectionContent>
    </msh:section>    		
    		<%
    	} else {%>
    	<i>Нет данных </i>
    	<% }} 
    

    if (view.equals("6")) {
        if (date!=null && !date.equals("")) {
        	request.setAttribute("isReportBase", ActionUtil.isReportBase(date, dateEnd,request));
        	%>
        
        <msh:section>
        <msh:sectionTitle>Результаты поиска за период с ${dateBegin} по ${dateEnd}.</msh:sectionTitle>
        </msh:section>
       
        <msh:section>
        
    <msh:sectionTitle>
        ${isReportBase}<ecom:webQuery isReportBase="${isReportBase}" name="report14swod" nameFldSql="report14swod_sql" nativeSql="
        select vrspt.id||'&strcode='||vrspt.id,vrspt.name,vrspt.strCode,vrspt.code 
    ,count(distinct sls.id) as c5ntDeath
    ,count(distinct
case when dc.categoryDifference_id is not null or dc.latrogeny_id is not null then sls.id else null end    ) as c6ntRash
    ,
    count(distinct case when dc.isAutopsy='1' then sls.id else null end) a7utopsy
    , count(distinct case when dc.id is null then sls.id else null end) n8oDeathof
    , count(distinct case when sls.emergency='1' then sls.id else null end) n9emergency
    , count(distinct case when dc.postmortemBureauDt is not null or (dc.postmortemBureauNumber!='' and dc.PostmortemBureauNumber is not null) then sls.id else null end) n10pat
    , count(distinct case when (dc.postmortemBureauDt is not null or (dc.postmortemBureauNumber!='' and dc.PostmortemBureauNumber is not null)) and (dc.categoryDifference_id is not null or dc.latrogeny_id is not null) then sls.id else null end) n11patRaz
    , count(distinct case when dc.DateForensic is not null  then sls.id else null end) n12fopat
    , count(distinct case when (dc.DateForensic is not null ) and (dc.categoryDifference_id is not null or dc.latrogeny_id is not null) then sls.id else null end) n13patforRaz
     from medcase sls
    left join DeathCase dc on dc.medCase_id=sls.id
    left join certificate cert on cert.deathcase_id = dc.id
    left join MedCase sloa on sloa.parent_id=sls.id
    left join BedFund bf on bf.id=sloa.bedFund_id
    left join VocReportSetParameterType vrspt on vrspt.classname='F14_DIAG'
    left join ReportSetTYpeParameterType rspt on rspt.parameterType_id=vrspt.id
    left join Patient p on p.id=sls.patient_id
    
    where 
    sls.dtype='HospitalMedCase' and sls.dateFinish 
    between to_date('${dateBegin}','dd.mm.yyyy') and to_date('${dateEnd}','dd.mm.yyyy')
     
    ${paramSql} and sloa.dateFinish is not null
    and sls.result_id='${result_death}'
    and 
    coalesce(
    (select max(mkb.code) from Diagnosis diag 
    left join vocidc10 mkb on mkb.id=diag.idc10_id
    left join VocDiagnosisRegistrationType vdrt on vdrt.id=diag.registrationType_id
    left join VocPriorityDiagnosis vpd on vpd.id=diag.priority_id
    where sls.id=diag.medcase_id  
    and vdrt.id='${diag_typeReg_pat}'
    and vpd.id='${diag_priority_m}'
    )
    ,
    (select max(mkb.code) from Diagnosis diag 
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
    	    <form action="print-stac_report_14_3.do" method="post" target="_blank">
	    Свод по нозоологиям (умершие)
	    <input type='hidden' name="sqlText" id="sqlText" value="${report14swod_sql}"> 
	    <input type='hidden' name="sqlInfo" id="sqlInfo" value="Свод по нозоологиям (умершие) за ${param.dateBegin}-${dateEnd}.">
	    <input type='hidden' name="sqlColumn" id="sqlColumn" value="${groupName}">
	    <input type='hidden' name="s" id="s" value="PrintService"><input type='hidden' name="isReportBase" id="isReportBase" value="${isReportBase}">
	    <input type='hidden' name="m" id="m" value="printNativeQuery">
	    <input type="submit" value="Печать"> 
	    </form>     
    </msh:sectionTitle>
    <msh:sectionContent>
        <msh:table printToExcelButton="Сохранить в excel" name="report14swod"
        viewUrl="stac_report_14.do?${paramHref}&typeAge=${typeAge}&typeView=${typeView}${departmentsUrlId}&additionStatus=${param.additionStatus}&typeAge=${typeAge}&noViewForm=1&short=Short&period=${dateBegin}-${dateEnd}" 
         action="stac_report_14.do?${paramHref}&typeAge=${typeAge}&typeView=${typeView}${departmentsUrlId}&additionStatus=${param.additionStatus}&typeAge=${typeAge}&noViewForm=1&period=${dateBegin}-${dateEnd}" idField="1" >
          <msh:tableColumn columnName="Наименование" property="2" />
          <msh:tableColumn columnName="№ строки" property="3" />
          <msh:tableColumn columnName="Код МКБ10" property="4" />
          <msh:tableColumn columnName="Умерло" property="5"/>
          <msh:tableColumn columnName="Кол-во экстр." property="9"/>
          <msh:tableColumn columnName="Расхождение диагноза" property="6"/>
          <msh:tableColumn columnName="Кол-во вскрытий" property="7"/>
          <msh:tableColumn columnName="Кол-во неоформл" property="8"/>
          <msh:tableColumn columnName="пат.-анат. вскрытий" property="10"/>
          <msh:tableColumn columnName="пат.-анат. вскрытий расх." property="11"/>
          <msh:tableColumn columnName="суд.-мед. вскрытий" property="12"/>
          <msh:tableColumn columnName="суд.-мед. вскрытий расх." property="13"/>
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
        	request.setAttribute("isReportBase", ActionUtil.isReportBase(dateBegin, dateEnd,request));
        		%>
        <msh:section>
        <msh:sectionTitle>Результаты поиска за период с ${dateBegin} по ${dateEnd}.</msh:sectionTitle>
        </msh:section>
       
        <msh:section>
        <msh:sectionTitle>
        ${isReportBase}<ecom:webQuery isReportBase="${isReportBase}" name="journal_surOperation" nameFldSql="journal_reestr_sql" nativeSql="
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
    ,(select list(mkb.code) from Diagnosis diag 
    left join vocidc10 mkb on mkb.id=diag.idc10_id
    left join VocDiagnosisRegistrationType vdrt on vdrt.id=diag.registrationType_id
    left join VocPriorityDiagnosis vpd on vpd.id=diag.priority_id
    where sls.id=diag.medcase_id  
    and vdrt.id='${diag_typeReg_cl}'
    and vpd.id='${diag_priority_m}'
    ) as mkbcode1
    ,
    (select list(mkb.code) from Diagnosis diag 
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
    ,case when dc.isAutopsy='1' then 'Да' else null end as dcisAutopsy
    , vdc.name || coalesce (' ятрогения кат.'||vdcL.name,'')as vdcname
    ,case when dc.id is null then 'Да' else null end as dcid
    ,dc.DateForensic as dcDateForensic
    ,dc.postmortemBureauDt as dcpostmortemBureauDt
    ,dc.postmortemBureauNumber as dcpostmortemBureauNumber
    
,coalesce(max(case when sloa.datefinish is not null and (ml.isnoomc is null or ml.isnoomc='0') then ml.name else null end)
,
(select pml.name from medcase fslo 
 left join medcase fpslo on fpslo.id=fslo.prevmedcase_id
left join mislpu pml on pml.id=fpslo.department_id where fslo.parent_id=sls.id and fslo.datefinish is not null 
and fslo.dtype='DepartmentMedCase' and (pml.isnoomc is null or pml.isnoomc='0'))) as mlname
,'№ '||cert.number ||'-'|| cert.series as certofdeath
     ,dc.isPresenceDoctorAutopsy as isPresenceDoctorAutopsy
     from medcase sls

    left join StatisticStub ss on ss.id=sls.statisticStub_id
    left join VocHospitalizationResult vhr on vhr.id=sls.result_id
    left join MedCase sloa on sloa.parent_id=sls.id
    left join BedFund bf on bf.id=sloa.bedFund_id
    left join VocReportSetParameterType vrspt on vrspt.classname='F14_DIAG'
    left join ReportSetTYpeParameterType rspt on rspt.parameterType_id=vrspt.id
    left join Patient p on p.id=sls.patient_id
    left join DeathCase dc on dc.medCase_id=sls.id
    left join certificate cert on cert.deathcase_id = dc.id
    left join VocDeathCategory vdc on vdc.id=dc.categoryDifference_id
    left join VocDeathCategory vdcL on vdcL.id=dc.latrogeny_id
    
    left join mislpu as ml on ml.id=sloa.department_id 
    
    where sls.dtype='HospitalMedCase' and sls.dateFinish between to_date('${dateBegin}','dd.mm.yyyy') 
        and to_date('${dateEnd}','dd.mm.yyyy')
    and vrspt.id='${param.strcode}'
    and sloa.dateFinish is not null
    ${paramSql}
    and sls.result_id='${result_death}'
    ${age_sql}  
    and 
    coalesce(
    (select max(mkb.code) from Diagnosis diag 
    left join vocidc10 mkb on mkb.id=diag.idc10_id
    left join VocDiagnosisRegistrationType vdrt on vdrt.id=diag.registrationType_id
    left join VocPriorityDiagnosis vpd on vpd.id=diag.priority_id
    where sls.id=diag.medcase_id  
    and vdrt.id='${diag_typeReg_pat}'
    and vpd.id='${diag_priority_m}'
    )
    ,
    (select max(mkb.code) from Diagnosis diag 
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
    ,bf.addCaseDuration,sls.result_id,dc.isAutopsy,vdc.name,dc.id,vdcL.name,dc.dateforensic
    ,dc.postmortemBureauDt
    ,dc.postmortemBureauNumber,cert.number, cert.series 
    order by p.lastname,p.firstname,p.middlename " />
    	    <form action="print-stac_report_14_3r.do" method="post" target="_blank">
	    Реестр пациентов ${param.strname} по нозоологиям (умершие)
	    <input type='hidden' name="sqlText" id="sqlText" value="${journal_reestr_sql}"> 
	    <input type='hidden' name="sqlInfo" id="sqlInfo" value="Реестр пациентов ${param.strname} по нозоологиям (умершие) за ${param.dateBegin}-${dateEnd}.">
	    <input type='hidden' name="sqlColumn" id="sqlColumn" value="${groupName}">
	    <input type='hidden' name="s" id="s" value="PrintService"><input type='hidden' name="isReportBase" id="isReportBase" value="${isReportBase}">
	    <input type='hidden' name="m" id="m" value="printNativeQuery">
	    <input type="submit" value="Печать"> 
	    </form>     
        
        </msh:sectionTitle>
        <msh:sectionContent>
        <msh:table printToExcelButton="Сохранить в excel" name="journal_surOperation"
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
          <msh:tableColumn columnName="Было вскрытие?" property="13"/>
          <msh:tableColumn columnName="Категория расхождений" property="14"/>
          <msh:tableColumn columnName="Присутствияе врача на вскрытии" property="21"/>
          <msh:tableColumn columnName="Cвидетельство о смерти" property="20"/>
          <msh:tableColumn columnName="Неоформлен случай смерти" property="15"/>
          <msh:tableColumn columnName="Дата суд-мед. эксп" property="16"/>
          <msh:tableColumn columnName="Дата пат.-анат. вскрытия" property="17"/>
          <msh:tableColumn columnName="Номер пат.-анат. вскрытия" property="18"/>
          <msh:tableColumn columnName="Отделение" property="19"/>
        </msh:table>
        </msh:sectionContent>
        </msh:section>    		
        		<%
        	} else {%>
        	<i>Нет данных </i>
        	<% }} 


    if (view.equals("10")) {
        if (date!=null && !date.equals("")) {
        	request.setAttribute("isReportBase", ActionUtil.isReportBase(date, dateEnd,request));
        	%>
        
        <msh:section>
        <msh:sectionTitle>Результаты поиска за период с ${dateBegin} по ${dateEnd}.</msh:sectionTitle>
        </msh:section>
       
        <msh:section>
        
    <msh:sectionTitle>
        ${isReportBase}<ecom:webQuery isReportBase="${isReportBase}" name="report14swod" nameFldSql="report14swod_sql" nativeSql="
        select vrspt.id||'&strcode='||vrspt.id,vrspt.name,vrspt.strCode,vrspt.code 
    ,count(distinct sls.id) as c5ntDeath
    ,count(distinct
case when dc.categoryDifference_id is not null or dc.latrogeny_id is not null then sls.id else null end    ) as c6ntRash
    ,
    count(distinct case when dc.isAutopsy='1' then sls.id else null end) a7utopsy
    , count(distinct case when dc.id is null then sls.id else null end) n8oDeathof
    , count(distinct case when sls.emergency='1' then sls.id else null end) n9emergency
    , count(distinct case when dc.postmortemBureauDt is not null or (dc.postmortemBureauNumber!='' and dc.PostmortemBureauNumber is not null) then sls.id else null end) n10pat
    , count(distinct case when (dc.postmortemBureauDt is not null or (dc.postmortemBureauNumber!='' and dc.PostmortemBureauNumber is not null)) and (dc.categoryDifference_id is not null or dc.latrogeny_id is not null) then sls.id else null end) n11patRaz
    , count(distinct case when dc.DateForensic is not null  then sls.id else null end) n12fopat
    , count(distinct case when (dc.DateForensic is not null ) and (dc.categoryDifference_id is not null or dc.latrogeny_id is not null) then sls.id else null end) n13patforRaz
     from medcase sls
    left join DeathCase dc on dc.medCase_id=sls.id
    left join MedCase sloa on sloa.parent_id=sls.id
    left join BedFund bf on bf.id=sloa.bedFund_id
    left join VocReportSetParameterType vrspt on vrspt.classname='F14_DIAG'
    left join ReportSetTYpeParameterType rspt on rspt.parameterType_id=vrspt.id
    left join Patient p on p.id=sls.patient_id
    
    where 
    sls.dtype='HospitalMedCase' and sls.dateFinish 
    between to_date('${dateBegin}','dd.mm.yyyy') and to_date('${dateEnd}','dd.mm.yyyy')
     
    ${paramSql} and sloa.dateFinish is not null
    and sls.result_id='${result_death}'
    and (sls.dateStart=sls.dateFinish or sls.dateFinish-sls.dateStart=1 and sls.dischargetime<sls.entrancetime)
    and 
    coalesce(
    (select max(mkb.code) from Diagnosis diag 
    left join vocidc10 mkb on mkb.id=diag.idc10_id
    left join VocDiagnosisRegistrationType vdrt on vdrt.id=diag.registrationType_id
    left join VocPriorityDiagnosis vpd on vpd.id=diag.priority_id
    where sls.id=diag.medcase_id  
    and vdrt.id='${diag_typeReg_pat}'
    and vpd.id='${diag_priority_m}'
    )
    ,
    (select max(mkb.code) from Diagnosis diag 
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
    	    <form action="print-stac_report_14_3.do" method="post" target="_blank">
	    Свод по нозоологиям (умершие)
	    <input type='hidden' name="sqlText" id="sqlText" value="${report14swod_sql}"> 
	    <input type='hidden' name="sqlInfo" id="sqlInfo" value="Свод по нозоологиям (умершие) ДОСУТОЧНО за ${param.dateBegin}-${dateEnd}.">
	    <input type='hidden' name="sqlColumn" id="sqlColumn" value="${groupName}">
	    <input type='hidden' name="s" id="s" value="PrintService"><input type='hidden' name="isReportBase" id="isReportBase" value="${isReportBase}">
	    <input type='hidden' name="m" id="m" value="printNativeQuery">
	    <input type="submit" value="Печать"> 
	    </form>     
    </msh:sectionTitle>
    <msh:sectionContent>
        <msh:table printToExcelButton="Сохранить в excel" name="report14swod"
        viewUrl="stac_report_14.do?${paramHref}&typeAge=${typeAge}&typeView=${typeView}${departmentsUrlId}&additionStatus=${param.additionStatus}&typeAge=${typeAge}&noViewForm=1&short=Short&period=${dateBegin}-${dateEnd}" 
         action="stac_report_14.do?${paramHref}&typeAge=${typeAge}&typeView=${typeView}${departmentsUrlId}&additionStatus=${param.additionStatus}&typeAge=${typeAge}&noViewForm=1&period=${dateBegin}-${dateEnd}" idField="1" >
          <msh:tableColumn columnName="Наименование" property="2" />
          <msh:tableColumn columnName="№ строки" property="3" />
          <msh:tableColumn columnName="Код МКБ10" property="4" />
          <msh:tableColumn columnName="Умерло" property="5"/>
          <msh:tableColumn columnName="Кол-во экстр." property="9"/>
          <msh:tableColumn columnName="Расхождение диагноза" property="6"/>
          <msh:tableColumn columnName="Кол-во вскрытий" property="7"/>
          <msh:tableColumn columnName="Кол-во неоформл" property="8"/>
          <msh:tableColumn columnName="пат.-анат. вскрытий" property="10"/>
          <msh:tableColumn columnName="пат.-анат. вскрытий расх." property="11"/>
          <msh:tableColumn columnName="суд.-мед. вскрытий" property="12"/>
          <msh:tableColumn columnName="суд.-мед. вскрытий расх." property="13"/>
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
        	request.setAttribute("isReportBase", ActionUtil.isReportBase(dateBegin, dateEnd,request));
        		%>
        <msh:section>
        <msh:sectionTitle>Результаты поиска за период с ${dateBegin} по ${dateEnd}.</msh:sectionTitle>
        </msh:section>
       
        <msh:section>
        <msh:sectionTitle>
        ${isReportBase}<ecom:webQuery isReportBase="${isReportBase}" name="journal_surOperation" nameFldSql="journal_reestr_sql" nativeSql="
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
    end as cntDays
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
    ,case when dc.isAutopsy='1' then 'Да' else null end as dcisAutopsy
    , vdc.name || coalesce (' ятрогения кат.'||vdcL.name,'')as vdcname
    ,case when dc.id is null then 'Да' else null end as dcid
    ,dc.DateForensic as dcDateForensic
    ,dc.postmortemBureauDt as dcpostmortemBureauDt
    ,dc.postmortemBureauNumber as dcpostmortemBureauNumber
    ,pml.name as mlname
    ,cert.number ||' '|| cert.series as certofdeath
    ,dc.isPresenceDoctorAutopsy as isPresenceDoctorAutopsy
	from medcase sls
   
    left join StatisticStub ss on ss.id=sls.statisticStub_id
    left join VocHospitalizationResult vhr on vhr.id=sls.result_id
    left join MedCase sloa on sloa.parent_id=sls.id
    left join BedFund bf on bf.id=sloa.bedFund_id
    left join VocReportSetParameterType vrspt on vrspt.classname='F14_DIAG'
    left join ReportSetTYpeParameterType rspt on rspt.parameterType_id=vrspt.id
    left join Patient p on p.id=sls.patient_id
    left join DeathCase dc on dc.medCase_id=sls.id
    left join certificate cert on cert.deathcase_id = dc.id
    left join VocDeathCategory vdc on vdc.id=dc.categoryDifference_id
    left join VocDeathCategory vdcL on vdcL.id=dc.latrogeny_id
     left join mislpu pml on pml.id=sloa.department_id
    where sls.dtype='HospitalMedCase' and sls.dateFinish between to_date('${dateBegin}','dd.mm.yyyy') 
        and to_date('${dateEnd}','dd.mm.yyyy')
    and vrspt.id='${param.strcode}'
    and sloa.dateFinish is not null
    ${paramSql}
    and sls.result_id='${result_death}'
    ${age_sql}  
    and 
    coalesce(
    (select max(mkb.code) from Diagnosis diag 
    left join vocidc10 mkb on mkb.id=diag.idc10_id
    left join VocDiagnosisRegistrationType vdrt on vdrt.id=diag.registrationType_id
    left join VocPriorityDiagnosis vpd on vpd.id=diag.priority_id
    where sls.id=diag.medcase_id  
    and vdrt.id='${diag_typeReg_pat}'
    and vpd.id='${diag_priority_m}'
    )
    ,
    (select max(mkb.code) from Diagnosis diag 
    left join vocidc10 mkb on mkb.id=diag.idc10_id
    left join VocDiagnosisRegistrationType vdrt on vdrt.id=diag.registrationType_id
    left join VocPriorityDiagnosis vpd on vpd.id=diag.priority_id
    where sls.id=diag.medcase_id  
    and vdrt.id='${diag_typeReg_cl}'
    and vpd.id='${diag_priority_m}'
    )
    ) between rspt.codefrom and rspt.codeto

    and (sls.dateStart=sls.dateFinish or sls.dateFinish-sls.dateStart=1 and sls.dischargetime<sls.entrancetime)
    group by sls.id
    ,ss.code,sls.emergency,sls.orderType_id,p.lastname,p.firstname
    ,p.middlename,p.birthday,sls.dateStart,sls.dateFinish
    ,bf.addCaseDuration,sls.result_id,dc.isAutopsy,vdc.name,dc.id,vdcL.name, pml.name,cert.number,cert.series
    order by p.lastname,p.firstname,p.middlename " />
    	    <form action="print-stac_report_14_3r.do" method="post" target="_blank">
	    Реестр пациентов ${param.strname} по нозоологиям (умершие)
	    <input type='hidden' name="sqlText" id="sqlText" value="${journal_reestr_sql}"> 
	    <input type='hidden' name="sqlInfo" id="sqlInfo" value="Реестр пациентов ${param.strname} по нозоологиям (умершие) за ${param.dateBegin}-${dateEnd}.">
	    <input type='hidden' name="sqlColumn" id="sqlColumn" value="${groupName}">
	    <input type='hidden' name="s" id="s" value="PrintService"><input type='hidden' name="isReportBase" id="isReportBase" value="${isReportBase}">
	    <input type='hidden' name="m" id="m" value="printNativeQuery">
	    <input type="submit" value="Печать"> 
	    </form>     
        
        </msh:sectionTitle>
        <msh:sectionContent>
        <msh:table printToExcelButton="Сохранить в excel" name="journal_surOperation"
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
          <msh:tableColumn columnName="Было вскрытие?" property="13"/>
          <msh:tableColumn columnName="Категория расхождений" property="14"/>
            <msh:tableColumn columnName="Присутствияе врача на вскрытии" property="21"/>
          <msh:tableColumn columnName="Cвидетельство о смерти" property="20"/>
          <msh:tableColumn columnName="Неоформлен случай смерти" property="15"/>
          <msh:tableColumn columnName="Дата суд-мед. эксп" property="16"/>
          <msh:tableColumn columnName="Дата пат.-анат. вскрытия" property="17"/>
          <msh:tableColumn columnName="Номер пат.-анат. вскрытия" property="18"/>
          <msh:tableColumn columnName="Отделение" property="19"/>
         
        </msh:table>
        </msh:sectionContent>
        </msh:section>    		
        		<%
        	} else {%>
        	<i>Нет данных </i>
        	<% }} 

    if (view.equals("9")) {
    if (date!=null && !date.equals("")) {
    	request.setAttribute("isReportBase", ActionUtil.isReportBase(date, dateEnd,request));
    	%>
    
    <msh:section>
    <msh:sectionTitle>Результаты поиска за период с ${dateBegin} по ${dateEnd}.</msh:sectionTitle>
    </msh:section>
   
    <msh:section>
    <msh:sectionTitle>Свод по операциям без кода</msh:sectionTitle>
    <msh:sectionContent>
    ${isReportBase}<ecom:webQuery isReportBase="${isReportBase}" name="report14swod" nativeSql="

select '&medService='||ms.id,ms.code,ms.name 
,count(distinct so.id) as cntOper
,count(distinct sls.id) as cntPat
,count(distinct case when vot.code='1' then so.id else null end) as cntTech
,count(distinct case when sls.result_id='${result_death}' then sls.id else null end) as cntPatDeath

,count(distinct case when voo.code='2' then so.id else null end) as cntDeathOper
,count(distinct case when sls.result_id='${result_death}' and vot.code='1' then so.id else null end) as cntDeathTech
,count(distinct case when vha.code='EMERGENCY' then so.id else null end) as cntEmergencyOper
,count(distinct case when so.endoscopyUse='1' then so.id else null end) as cntEndoscopyOper
 from medcase sls
left join MedCase sloa on sloa.parent_id=sls.id
left join BedFund bf on bf.id=sloa.bedFund_id
left join MedCase sloall on sloall.parent_id=sls.id
left join surgicaloperation so on so.medCase_id=sloall.id
left join MedService ms on ms.id=so.medService_id
left join VocOperationTechnology vot on vot.id=so.technology_id
left join VocOperationOutcome voo on voo.id=so.outcome_id
left join Patient p on p.id=sls.patient_id
left join VocHospitalAspect vha on vha.id=so.aspect_id
where 
sls.dtype='HospitalMedCase' and sls.dateFinish 
between to_date('${dateBegin}','dd.mm.yyyy') and to_date('${dateEnd}','dd.mm.yyyy')
and sloa.dateFinish is not null
${paramSql}
and ms.id is not null and  (ms.additioncode ='' or ms.additioncode is null) 
${age_sql}  
group by ms.id,ms.code,ms.name
order by ms.code
" />
    <msh:table printToExcelButton="Сохранить в excel" name="report14swod"
    viewUrl="stac_report_14.do?${paramHref}&typeAge=${typeAge}&typeView=${typeView}${departmentsUrlId}&additionStatus=${param.additionStatus}&typeAge=${typeAge}&noViewForm=1&short=Short&period=${dateBegin}-${dateEnd}" 
     action="stac_report_14.do?${paramHref}&typeAge=${typeAge}&typeView=${typeView}${departmentsUrlId}&additionStatus=${param.additionStatus}&typeAge=${typeAge}&noViewForm=1&period=${dateBegin}-${dateEnd}" idField="1" >
      <msh:tableColumn columnName="#" property="sn" />
      <msh:tableColumn columnName="Код" property="2" />
      <msh:tableColumn columnName="Наименование" property="3" />
      <msh:tableColumn columnName="Кол-во операций" property="4"/>
      <msh:tableColumn columnName="Кол-во пациентов" property="5"/>
      <msh:tableColumn columnName="кол-во операций с испол. ВМТ" property="6"/>
      <msh:tableColumn columnName="Кол-во умерших пациентов" property="7"/>
      <msh:tableColumn columnName="Отмечено исход операции летальный" property="8"/>
      <msh:tableColumn columnName="Отмечено исход операции летальный с испол. ВМТ" property="9"/>
      <msh:tableColumn columnName="Экстренные" property="10"/>
      <msh:tableColumn columnName="Эндоскопические" property="11"/>
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
    	request.setAttribute("isReportBase", ActionUtil.isReportBase(dateBegin, dateEnd,request));
    		%>
    <msh:section>
    <msh:sectionTitle>Результаты поиска за период с ${dateBegin} по ${dateEnd}.</msh:sectionTitle>
    </msh:section>
   
    <msh:section>
    <msh:sectionTitle>Список пациентов ${param.strname}
    
    </msh:sectionTitle>
    <msh:sectionContent>
    ${isReportBase}<ecom:webQuery isReportBase="${isReportBase}" name="journal_surOperation" nativeSql="
    select 
so.id as soid
,ms.id as msid
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
left join MedCase sloa on sloa.id=so.medCase_id
left join BedFund bf on bf.id=sloa.bedFund_id
left join patient p on p.id=sloa.patient_id
left join MedCase sls on sls.id=sloa.parent_id
left join StatisticStub ss on ss.id=sls.statisticStub_id
where sls.dtype='HospitalMedCase' and sls.dateFinish between to_date('${dateBegin}','dd.mm.yyyy') 
    and to_date('${dateEnd}','dd.mm.yyyy')
and ms.id='${param.medService}'
${paramSql} ${age_sql} 
group by so.id
,ss.code,p.lastname,p.firstname,p.middlename,p.birthday,sls.dateStart,sls.dateFinish
,ms.id,ms.code ,ms.name,ms.additioncode
order by p.lastname,p.firstname,p.middlename " />
    <msh:table printToExcelButton="Сохранить в excel" name="journal_surOperation"
    viewUrl="entityShortView-stac_surOperation.do" 
     action="entityView-stac_surOperation.do" idField="1">
      <msh:tableColumn columnName="##" property="sn" />
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
    
    } 
    }
    if (view.equals("7")) {
    if (date!=null && !date.equals("")) {
    	request.setAttribute("isReportBase", ActionUtil.isReportBase(date, dateEnd,request));
    	%>
    
    <msh:section>
    <msh:sectionTitle>Результаты поиска за период с ${dateBegin} по ${dateEnd}.</msh:sectionTitle>
    </msh:section>
   
    <msh:section>
    <msh:sectionTitle>
    <ecom:webQuery isReportBase="${isReportBase}" name="report14swod" nameFldSql="report14swod_sql" nativeSql="

select vrspt.id||'&strcode='||vrspt.id as vrsptid,vrspt.name,vrspt.strCode 
,count(distinct so.id) as cntOper
,count(distinct sls.id) as cntPat
,count(distinct case when vot.code='1' then so.id else null end) as cntTech
,count(distinct case when sls.result_id='${result_death}' then sls.id else null end) as cntPatDeath

,count(distinct case when voo.code='2' then so.id else null end) as cntDeathOper
,count(distinct case when sls.result_id='${result_death}' and vot.code='1' then so.id else null end) as cntDeathTech
,count(distinct case when vha.code='EMERGENCY' then so.id else null end) as cntEmergencyOper
,count(distinct case when so.endoscopyUse='1' then so.id else null end) as cntEndoscopyOper
 from medcase sls
left join MedCase sloa on sloa.parent_id=sls.id
left join BedFund bf on bf.id=sloa.bedFund_id
left join MedCase sloall on sloall.parent_id=sls.id
left join surgicaloperation so on so.medCase_id=sloall.id
left join MedService ms on ms.id=so.medService_id
left join VocReportSetParameterType vrspt on vrspt.classname='F14_OPER'
left join ReportSetTYpeParameterType rspt on rspt.parameterType_id=vrspt.id
left join VocOperationTechnology vot on vot.id=so.technology_id
left join VocOperationOutcome voo on voo.id=so.outcome_id
left join Patient p on p.id=sls.patient_id
left join VocHospitalAspect vha on vha.id=so.aspect_id
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
    
        	    <form action="print-report_14_7.do" method="post" target="_blank">
	    Свод по операциям
	    <input type='hidden' name="sqlText" id="sqlText" value="${report14swod_sql}"> 
	    <input type='hidden' name="sqlInfo" id="sqlInfo" value="Свод по операциям за ${dateBegin}-${dateEnd}.">
	    <input type='hidden' name="sqlColumn" id="sqlColumn" value="${groupName}">
	    <input type='hidden' name="s" id="s" value="PrintService"><input type='hidden' name="isReportBase" id="isReportBase" value="${isReportBase}">
	    <input type='hidden' name="m" id="m" value="printNativeQuery">
	    <input type="submit" value="Печать"> 
	    </form>     
    
    </msh:sectionTitle>
    <msh:sectionContent>
    ${isReportBase}
    <msh:table printToExcelButton="Сохранить в excel" name="report14swod"
    viewUrl="stac_report_14.do?${paramHref}&typeAge=${typeAge}&typeView=${typeView}${departmentsUrlId}&additionStatus=${param.additionStatus}&typeAge=${typeAge}&noViewForm=1&short=Short&period=${dateBegin}-${dateEnd}" 
     action="stac_report_14.do?${paramHref}&typeAge=${typeAge}&typeView=${typeView}${departmentsUrlId}&additionStatus=${param.additionStatus}&typeAge=${typeAge}&noViewForm=1&period=${dateBegin}-${dateEnd}" idField="1" >
      <msh:tableColumn columnName="Наименование" property="2" />
      <msh:tableColumn columnName="№ строки" property="3" />
      <msh:tableColumn columnName="Кол-во операций" property="4"/>
      <msh:tableColumn columnName="Кол-во пациентов" property="5"/>
      <msh:tableColumn columnName="кол-во операций с испол. ВМТ" property="6"/>
      <msh:tableColumn columnName="Кол-во умерших пациентов" property="7"/>
      <msh:tableColumn columnName="Отмечено исход операции летальный" property="8"/>
      <msh:tableColumn columnName="Отмечено исход операции летальный с испол. ВМТ" property="9"/>
      <msh:tableColumn columnName="Экстренные" property="10"/>
      <msh:tableColumn columnName="Эндоскопические" property="11"/>
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
    	request.setAttribute("isReportBase", ActionUtil.isReportBase(dateBegin, dateEnd,request));
    		%>
    <msh:section>
    <msh:sectionTitle>Результаты поиска за период с ${dateBegin} по ${dateEnd}.</msh:sectionTitle>
    </msh:section>
   
    <msh:section>
    <msh:sectionTitle>Список пациентов ${param.strname}
    
    </msh:sectionTitle>
    <msh:sectionContent>
    ${isReportBase}<ecom:webQuery isReportBase="true" name="journal_surOperation" nativeSql="
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
left join MedCase sloa on sloa.id=so.medCase_id
left join BedFund bf on bf.id=sloa.bedFund_id
left join patient p on p.id=sloa.patient_id
left join MedCase sls on sls.id=sloa.parent_id
left join StatisticStub ss on ss.id=sls.statisticStub_id
where sls.dtype='HospitalMedCase' and sls.dateFinish between to_date('${dateBegin}','dd.mm.yyyy') 
    and to_date('${dateEnd}','dd.mm.yyyy')
and vrspt.id='${param.strcode}'
and vrspt1.classname='F14_OPER'
${paramSql} ${age_sql} 
group by so.id
,ss.code,p.lastname,p.firstname,p.middlename,p.birthday,sls.dateStart,sls.dateFinish
,ms.code ,ms.name,ms.additioncode
order by p.lastname,p.firstname,p.middlename " />
    <msh:table printToExcelButton="Сохранить в excel" name="journal_surOperation"
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