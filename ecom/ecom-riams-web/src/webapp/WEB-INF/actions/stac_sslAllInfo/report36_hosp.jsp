
<%@page import="ru.ecom.mis.web.action.medcase.journal.AdmissionJournalForm"%>
<%@page import="ru.ecom.mis.web.action.util.ActionUtil"%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://struts.apache.org/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://www.nuzmsh.ru/tags/msh" prefix="msh" %>
<%@ taglib uri="http://www.ecom-ast.ru/tags/ecom" prefix="ecom" %>
<%@ taglib tagdir="/WEB-INF/tags" prefix="tags" %>

<tiles:insert page="/WEB-INF/tiles/main${param.short}Layout.jsp" flush="true" >

  <tiles:put name="title" type="string">
    <msh:title guid="helloItle-123" mainMenu="StacJournal" title="форма 36. 2300"/>
  </tiles:put>
  <tiles:put name="side" type="string">

  </tiles:put>
  <tiles:put name="body" type="string">
  <%
  	String noViewForm = request.getParameter("noViewForm") ;
  	String typeAge=ActionUtil.updateParameter("Report36HOSP","typeAge","1", request) ;
  	String typeDiagOrder=ActionUtil.updateParameter("Report36HOSP","typeDiagOrder","1", request) ;
  	String typeView=ActionUtil.updateParameter("Report36HOSP","typeView","2", request) ;
  	String typeDate=ActionUtil.updateParameter("Report36HOSP","typeDate","2", request) ;
  	
  	StringBuilder paramSql= new StringBuilder() ;
  	StringBuilder paramHref= new StringBuilder() ;
  	
	ActionUtil.setParameterFilterSql("reportStr", "vrspt.id", request) ;
  	ActionUtil.setParameterFilterSql("department","department", "ahr.department", request) ;
  	paramSql.append(" ").append(ActionUtil.setParameterFilterSql("sex", "ahr.sex", request)) ;
  	paramSql.append(" ").append(ActionUtil.setParameterFilterSql("hospType", "ahr.hospType", request)) ;
  	paramHref.append("sex=").append(request.getParameter("sex")!=null?request.getParameter("sex"):"") ;
  	paramHref.append("&hospType=").append(request.getParameter("hospType")!=null?request.getParameter("hospType"):"") ;
  	request.setAttribute("paramSql", paramSql.toString()) ;
  	request.setAttribute("paramHref", paramHref.toString()) ;
  	
  	//request.setAttribute("diag_typeReg_cl", "4") ;
  	//request.setAttribute("diag_typeReg_pat", "5") ;
  	//request.setAttribute("diag_priority_m", "1") ;
  	if (noViewForm!=null && noViewForm.equals("1")) {
  	} else {
  		
  %>
    <msh:form action="/stac_report_36.do" defaultField="dateBegin" disableFormDataConfirm="true" method="GET" guid="d7b31bc2-38f0-42cc-8d6d-19395273168f">
    <input type="hidden" name="id" id="id" value=""/>
    <msh:panel guid="6ae283c8-7035-450a-8eb4-6f0f7da8a8ff">
      <msh:row guid="53627d05-8914-48a0-b2ec-792eba5b07d9">
        <msh:separator label="Параметры поиска" colSpan="7" />
      </msh:row>
      <msh:row>
        <td class="label" title="Какой диагноз учитывать при пост. (typeDiagOrder)" colspan="1"><label for="typeDiagOrderName" id="typeDiagOrderLabel">Диагноз при пост.:</label></td>
        <td onclick="this.childNodes[1].checked='checked';">
        	<input type="radio" name="typeDiagOrder" value="1"> направит.
        </td>
        <td onclick="this.childNodes[1].checked='checked';" colspan="2">
        	<input type="radio" name="typeDiagOrder" value="2"> клинический
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
       </msh:row>
       <msh:row>
        <td></td>
        <td onclick="this.childNodes[1].checked='checked';">
        	<input type="radio" name="typeView" value="7"> по состоящих на конец периода
        </td>
        <td onclick="this.childNodes[1].checked='checked';">
        	<input type="radio" name="typeView" value="8"> перевед. из др. отд.
        </td>
        <td onclick="this.childNodes[1].checked='checked';">
        	<input type="radio" name="typeView" value="9"> перевед. в др. отд.
        </td>
       </msh:row>
        <msh:row>
        	<msh:autoComplete property="department" fieldColSpan="4" horizontalFill="true" label="Отделение" vocName="vocLpuHospOtdAll"/>
        </msh:row>
        <msh:row>
        	<msh:autoComplete property="hospType" fieldColSpan="4" horizontalFill="true" label="Тип стационара" vocName="vocHospType"/>
        </msh:row>
        <msh:row>
        	<msh:autoComplete property="sex" fieldColSpan="4" horizontalFill="true" label="Пол" vocName="vocSex"/>
        </msh:row>
        <msh:row>
        	<msh:autoComplete property="reportStr" fieldColSpan="4" horizontalFill="true" label="Строка отчета" parentId="F36_DIAG" vocName="vocReportStr"/>
        </msh:row>
      <msh:row>
        <msh:textField property="dateBegin" label="Период с" />
        <msh:textField property="dateEnd" label="по" />
           <td>
            <input type="submit" value="Найти" />
            <input type="submit" onclick="refresh();this.forms[0].target='';" value="Пересчет" />
          </td>
      </msh:row>
    </msh:panel>
    </msh:form>
    
    <%} %>
    <script type="text/javascript" src="./dwr/interface/HospitalMedCaseService.js">/**/</script>
           <script type='text/javascript'>
          
           checkFieldUpdate('typeView','${typeView}',1) ;
           checkFieldUpdate('typeDiagOrder','${typeDiagOrder}',1) ;
           

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
    	frm.target='_blank' ;
    	frm.action='stac_report_refresh_save.do' ;
    	
    	
    }
    
    if ($('dateBegin').value=="") {
    	$('dateBegin').value=getCurrentDate() ;
    }

			 
    </script>
    <%
    
    String date = request.getParameter("dateBegin") ;
    if (date!=null) {
    String dateEnd = request.getParameter("dateEnd") ;
    
    //String id = (String)request.getParameter("id") ;
    
    String reportStr =request.getParameter("reportStr") ;
    if (reportStr!=null && !reportStr.equals("") &&!reportStr.equals("0")) {
    	request.setAttribute("reportStrLeftJoin","left join ReportSetTYpeParameterType rspt on ahr.idcDischarge between rspt.codefrom and rspt.codeto left join VocReportSetParameterType vrspt on rspt.parameterType_id=vrspt.id") ;
    }
    if (dateEnd==null || dateEnd.equals("")) dateEnd=date ;
    request.setAttribute("dateBegin", date) ;
    request.setAttribute("dateEnd", dateEnd) ;
	request.setAttribute("dateEnddd", dateEnd.substring(0,2)) ;
	request.setAttribute("dateEndmm", dateEnd.substring(3,5)) ;
	request.setAttribute("dateEndyyyy", dateEnd.substring(6)) ;

    String view = (String)request.getAttribute("typeView") ;
    if (date!=null) {
    if (view.equals("1")) { %>

    <%} else if (view.equals("2")) { %>
    
    <msh:section>
    <msh:sectionTitle>Результаты поиска за период с ${dateBegin} по ${dateEnd}.</msh:sectionTitle>
    </msh:section>
   
    <msh:section>
    <ecom:webQuery name="Report36HOSPswod" nameFldSql="Report36HOSPswod_sql" nativeSql="
select
vrspt.id||'&reportStr='||vrspt.id,vrspt.name,vrspt.strCode,vrspt.code 
,count(case when
ahr.entranceDate24 < to_date('${dateBegin}','dd.mm.yyyy') 
and (ahr.dischargeDate24 > to_date('${dateBegin}','dd.mm.yyyy') 
or ahr.dischargeDate24 is null
) ${departmentSql}
and ahr.idcDepartmentCode between rspt.codefrom and rspt.codeto 
then ahr.sls else null end) as cntBeginPeriodAll
,count(case when
ahr.entranceDate24 < to_date('${dateBegin}','dd.mm.yyyy') 
and (ahr.dischargeDate24 > to_date('${dateBegin}','dd.mm.yyyy') 
or ahr.dischargeDate24 is null
) ${departmentSql}
and
ahr.ageEntranceSlo between 0 and 14
and ahr.idcDepartmentCode between rspt.codefrom and rspt.codeto 
then ahr.sls else null end) as cntBeginPeriod0_14

,count(case when
ahr.entranceDate24 < to_date('${dateBegin}','dd.mm.yyyy') 
and (ahr.dischargeDate24 > to_date('${dateBegin}','dd.mm.yyyy') 
or ahr.dischargeDate24 is null
) ${departmentSql}
and
ahr.ageEntranceSlo between 15 and 17
and ahr.idcDepartmentCode between rspt.codefrom and rspt.codeto 
then ahr.sls else null end) as cntBeginPeriod15_17

,count(case when
ahr.entranceDate24 between to_date('${dateBegin}','dd.mm.yyyy') and to_date('${dateEnd}','dd.mm.yyyy')
and ahr.transferDepartmentFrom is null
${departmentSql}
and  ahr.idcEntranceCode between rspt.codefrom and rspt.codeto 
then ahr.sls else null end) as cntEntranceAll
,count(case when 
ahr.entranceDate24 between to_date('${dateBegin}','dd.mm.yyyy') and to_date('${dateEnd}','dd.mm.yyyy')
and  ahr.idcEntranceCode between rspt.codefrom and rspt.codeto 
and ahr.transferDepartmentFrom is null
${departmentSql}
and
ahr.ageEntranceSls between 0 and 14 then ahr.sls else null end)  as cntEntrance0_14
,count(case when 

ahr.entranceDate24 between to_date('${dateBegin}','dd.mm.yyyy') and to_date('${dateEnd}','dd.mm.yyyy')
and ahr.transferDepartmentFrom is null
${departmentSql}
and  ahr.idcEntranceCode between rspt.codefrom and rspt.codeto 

and
ahr.ageEntranceSls between 15 and 17 then ahr.sls else null end)  as cntEntrance15_17
,count(case when
ahr.entranceDate24 between to_date('${dateBegin}','dd.mm.yyyy') and to_date('${dateEnd}','dd.mm.yyyy')
and ahr.transferDepartmentFrom is null
and ahr.idcEntranceCode between rspt.codefrom and rspt.codeto 
and ahr.isFirstCurrentYear='1' ${departmentSql}
then ahr.sls else null end) as cntEntranceAdmHosp
,count(case when
ahr.entranceDate24 between to_date('${dateBegin}','dd.mm.yyyy') and to_date('${dateEnd}','dd.mm.yyyy')
and ahr.transferDepartmentFrom is null
and ahr.idcEntranceCode between rspt.codefrom and rspt.codeto 
and ahr.isFirstLife='1' ${departmentSql}
then ahr.sls else null end) as cntEntranceFirstLife
,count(case when
ahr.entranceDate24 between to_date('${dateBegin}','dd.mm.yyyy') and to_date('${dateEnd}','dd.mm.yyyy')
and ahr.transferDepartmentFrom is null
and ahr.idcEntranceCode between rspt.codefrom and rspt.codeto 
and ahr.isIncompetent='1' ${departmentSql}
then ahr.sls else null end) as cntEntranceHospNeDobr



,count(case when
ahr.dischargeDate24 between to_date('${dateBegin}','dd.mm.yyyy') and to_date('${dateEnd}','dd.mm.yyyy')
and ahr.idcDischarge between rspt.codefrom and rspt.codeto 
and ahr.transferDepartmentIn is null
${departmentSql}
then ahr.sls else null end) as cntDischargeAll

,sum(case when
ahr.dischargeDate24 between to_date('${dateBegin}','dd.mm.yyyy') and to_date('${dateEnd}','dd.mm.yyyy')
and ahr.idcDischarge between rspt.codefrom and rspt.codeto
and ahr.transferDepartmentIn is null
${departmentSql}
then case when (ahr.dischargeDate24-ahr.entranceHospDate24)=0 then 1
else 
ahr.dischargeDate24-ahr.entranceHospDate24+ahr.addbeddays
end else null end)
as sumDayAllDischarge

,count(case when
ahr.dischargeDate24 between to_date('${dateBegin}','dd.mm.yyyy') and to_date('${dateEnd}','dd.mm.yyyy')
and ahr.idcDischarge between rspt.codefrom and rspt.codeto
and ahr.isDeath='1'
and ahr.transferDepartmentIn is null
${departmentSql}
then ahr.sls else null end) as cntDischargeDeath

,
sum(case when
ahr.dischargeDate24 between to_date('${dateBegin}','dd.mm.yyyy') and to_date('${dateEnd}','dd.mm.yyyy')
and ahr.idcDischarge between rspt.codefrom and rspt.codeto
and ahr.isDeath='1'
and ahr.transferDepartmentIn is null
${departmentSql}
then case when (ahr.dischargeDate24-ahr.entranceHospDate24)=0 then 1
else 
ahr.dischargeDate24-ahr.entranceHospDate24+ahr.addbeddays
end else null end)
as sumDayDeathDischarge


,count(case when 
ahr.dischargeDate24 between to_date('${dateBegin}','dd.mm.yyyy') and to_date('${dateEnd}','dd.mm.yyyy')
and ahr.transferDepartmentIn is null
${departmentSql}
and ahr.idcDischarge between rspt.codefrom and rspt.codeto 
and ahr.ageDischargeSls between 0 and 14 then ahr.sls else null end)  as cntDischarge0_14

,sum(case when
ahr.dischargeDate24 between to_date('${dateBegin}','dd.mm.yyyy') and to_date('${dateEnd}','dd.mm.yyyy')
and ahr.idcDischarge between rspt.codefrom and rspt.codeto
and ahr.ageDischargeSls between 0 and 14
and ahr.transferDepartmentIn is null
${departmentSql}
then case when (ahr.dischargeDate24-ahr.entranceHospDate24)=0 then 1
else 
ahr.dischargeDate24-ahr.entranceHospDate24+ahr.addbeddays
end else null end)
as sumDayDischarge0_14

,count(case when 
ahr.dischargeDate24 between to_date('${dateBegin}','dd.mm.yyyy') and to_date('${dateEnd}','dd.mm.yyyy')
and ahr.transferDepartmentIn is null
${departmentSql}
and ahr.idcDischarge between rspt.codefrom and rspt.codeto 
and ahr.ageDischargeSls between 15 and 17 then ahr.sls else null end)  as cntDischarge15_17

,sum(case when
ahr.dischargeDate24 between to_date('${dateBegin}','dd.mm.yyyy') and to_date('${dateEnd}','dd.mm.yyyy')
and ahr.idcDischarge between rspt.codefrom and rspt.codeto
and ahr.ageDischargeSls between 15 and 17
and ahr.transferDepartmentIn is null
${departmentSql}
then case when (ahr.dischargeDate24-ahr.entranceHospDate24)=0 then 1
else 
ahr.dischargeDate24-ahr.entranceHospDate24+ahr.addbeddays
end else null end)
as sumDayDischarge15_17


,count(case when
 ahr.entranceDate24 <= to_date('${dateEnd}','dd.mm.yyyy') 
and (ahr.dischargeDate24 > to_date('${dateEnd}','dd.mm.yyyy') 
or ahr.dischargeDate24 is null
)
${departmentSql}
and ahr.idcDepartmentCode between rspt.codefrom and rspt.codeto 
then ahr.sls else null end) as cntFinishPeriodAll
,count(case when
 ahr.entranceDate24 <= to_date('${dateEnd}','dd.mm.yyyy') 
and (ahr.dischargeDate24 > to_date('${dateEnd}','dd.mm.yyyy') 
or ahr.dischargeDate24 is null
)
${departmentSql}
and cast('${dateEndyyyy}' as int)-cast(to_char(ahr.birthday,'yyyy') as int) +(case when (cast('${dateEndmm}' as int)-cast(to_char(ahr.birthday, 'mm') as int) +(case when (cast('${dateEnddd}' as int) - cast(to_char(ahr.birthday,'dd') as int)<0) then -1 else 0 end)<0) then -1 else 0 end) between 0 and 14
and ahr.idcDepartmentCode between rspt.codefrom and rspt.codeto 
then ahr.sls else null end) as cntFinishPeriod0_14

,count(case when
 ahr.entranceDate24 <= to_date('${dateEnd}','dd.mm.yyyy') 
and (ahr.dischargeDate24 > to_date('${dateEnd}','dd.mm.yyyy') 
or ahr.dischargeDate24 is null
)
${departmentSql}
and cast('${dateEndyyyy}' as int)-cast(to_char(ahr.birthday,'yyyy') as int) +(case when (cast('${dateEndmm}' as int)-cast(to_char(ahr.birthday, 'mm') as int) +(case when (cast('${dateEnddd}' as int) - cast(to_char(ahr.birthday,'dd') as int)<0) then -1 else 0 end)<0) then -1 else 0 end) between 15 and 17
and ahr.idcDepartmentCode between rspt.codefrom and rspt.codeto 
then ahr.sls else null end) as cntFinishPeriod15_17

,count(case when
ahr.entranceDate24 between to_date('${dateBegin}','dd.mm.yyyy') and to_date('${dateEnd}','dd.mm.yyyy')
and ahr.idcDepartmentCode between rspt.codefrom and rspt.codeto 
and ahr.transferDepartmentFrom is not null
${departmentSql}
then ahr.sls else null end) as cntTransferFrom

,count(case when
ahr.dischargeDate24 between to_date('${dateBegin}','dd.mm.yyyy') and to_date('${dateEnd}','dd.mm.yyyy')
and ahr.idcDepartmentCode between rspt.codefrom and rspt.codeto 
and ahr.transferDepartmentIn is not null
${departmentSql}
then ahr.sls else null end) as cntTransferIn
 from AggregateHospitalReport ahr

left join VocReportSetParameterType vrspt on vrspt.classname='F36_DIAG'
left join ReportSetTYpeParameterType rspt on rspt.parameterType_id=vrspt.id
left join Patient p on p.id=ahr.patient

where ( 
 ahr.dischargeDate24>=to_date('${dateBegin}','dd.mm.yyyy')
or ahr.dischargeDate24 is null)
and (ahr.entranceDate24 <= to_date('${dateEnd}','dd.mm.yyyy') 
) 

and (ahr.idcDepartmentCode between rspt.codefrom and rspt.codeto 
or ahr.idcDischarge between rspt.codefrom and rspt.codeto
or ahr.idcEntranceCode between rspt.codefrom and rspt.codeto

 )
${paramSql}
${departmentSql}
${reportStrSql}
group by vrspt.id,vrspt.name,vrspt.strCode,vrspt.code
order by vrspt.strCode

" />
    <msh:sectionTitle>
    
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
     
     action="stac_report_36.do?${paramHref}&noViewForm=1&typeAge=${typeAge}&department=${param.department}&typeAge=${typeAge}&noViewForm=0&dateBegin=${dateBegin}&dateEnd=${dateEnd}" idField="1"
    cellFunction="true"  
     >
      <msh:tableColumn columnName="Наименование" property="2" />
      <msh:tableColumn columnName="№ строки" property="3" />
      <msh:tableColumn columnName="Код МКБ10" property="4" />
      <msh:tableColumn columnName="Начало всего" property="5" addParam="&typeView=6"/>
      <msh:tableColumn columnName="Начало 0-14" property="6" addParam="&typeView=6&beginAge=0-14"/>
      <msh:tableColumn columnName="Начало 15-17" property="7" addParam="&typeView=6&beginAge=15-17"/>
      <msh:tableColumn columnName="Поступ. всего" property="8" addParam="&typeView=4"/>
      <msh:tableColumn columnName="Поступ. 0-14" property="9" addParam="&typeView=4&entranceAge=0-14"/>
      <msh:tableColumn columnName="Поступ.15-17" property="10" addParam="&typeView=4&entranceAge=15-17"/>
      <msh:tableColumn columnName="Поступ. вп.в д.г" property="11" addParam="&typeView=4&entrance=isFirstCurrentYear"/>
      <msh:tableColumn columnName="Поступ. втч вп. в жизни" property="12" addParam="&typeView=4&entrance=isFirstLife"/>
      <msh:tableColumn columnName="Пост. недобр." property="13" addParam="&typeView=4&entrance=isIncompetent"/>
      <msh:tableColumn columnName="Выбыло" property="14" addParam="&typeView=5"/>
      <msh:tableColumn columnName="к.д" property="15" addParam="&typeView=5"/>
      <msh:tableColumn columnName="умерло" property="16" addParam="&typeView=5&discharge=death"/>
      <msh:tableColumn columnName="к.д" property="17" addParam="&typeView=5&discharge=death"/>
      <msh:tableColumn columnName="0-14" property="18" addParam="&typeView=5&dischargeAge=0-14"/>
      <msh:tableColumn columnName="к.д." property="19" addParam="&typeView=5&dischargeAge=0-14"/>
      <msh:tableColumn columnName="15-17" property="20" addParam="&typeView=5&dischargeAge=15-17"/>
      <msh:tableColumn columnName="к.д." property="21" addParam="&typeView=5&dischargeAge=15-17"/>
      <msh:tableColumn columnName="сост. всего" property="22" addParam="&typeView=7"/>
      <msh:tableColumn columnName="сост. 0-14" property="23" addParam="&typeView=7&endAge=0-14"/>
      <msh:tableColumn columnName="сост. 15-17" property="24" addParam="&typeView=7&endAge=15-17"/>
      <msh:tableColumn columnName="перевед. из др." property="25" addParam="&typeView=8"/>
      <msh:tableColumn columnName="перевед. в др." property="26" addParam="&typeView=9"/>
    </msh:table>
    
    </msh:sectionContent>
    </msh:section>
    <%} else if (view.equals("3")) {
       
        	%>
     
        <% } else if (view.equals("4")||view.equals("5")||view.equals("6")
        		||view.equals("7")||view.equals("8")||view.equals("9")) {
        	String sql ="" ;
        	if (view.equals("4")) {
        		sql="ahr.entranceDate24 between to_date('"+date+"','dd.mm.yyyy') and to_date('"
        			+dateEnd+"','dd.mm.yyyy') and ahr.transferDepartmentFrom is null" ;

        	    request.setAttribute("titleReestr","Список поступивших пациентов") ;
        		request.setAttribute("dateSql", sql) ;
        		request.setAttribute("diagnosField","idcEntranceCode") ;
        	} else if (view.equals("5")) {
        		sql="ahr.dischargeDate24 between to_date('"+date+"','dd.mm.yyyy') and to_date('"
            			+dateEnd+"','dd.mm.yyyy') and ahr.transferDepartmentIn is null" ;
        		request.setAttribute("titleReestr","Список выбывших пациентов") ;
        		request.setAttribute("diagnosField","idcDischarge") ;
        	} else if (view.equals("6")) {
        		sql="ahr.entranceDate24 < to_date('"+date+"','dd.mm.yyyy') and (ahr.dischargeDate24 > to_date('"+date+"','dd.mm.yyyy') or ahr.dischargeDate24 is null)";
        		request.setAttribute("titleReestr","Список пациентов, состоящих на начало периода") ;
        		request.setAttribute("diagnosField","idcDepartmentCode") ;
        	} else if (view.equals("7")) {
        		sql="ahr.entranceDate24 <= to_date('"+dateEnd+"','dd.mm.yyyy') and (ahr.dischargeDate24 > to_date('"+dateEnd+"','dd.mm.yyyy') or ahr.dischargeDate24 is null)";
        		request.setAttribute("titleReestr","Список пациентов, состоящих на конец периода") ;
        		request.setAttribute("diagnosField","idcDepartmentCode") ;
        	
			} else if (view.equals("8")) {
        		sql="ahr.entranceDate24 between to_date('"+date+"','dd.mm.yyyy') and to_date('"
            			+dateEnd+"','dd.mm.yyyy') and ahr.transferDepartmentFrom is not null" ;
        		request.setAttribute("titleReestr","Список пациентов, переведенных из другого отделения") ;
        		request.setAttribute("diagnosField","idcDepartmentCode") ;
        	} else if (view.equals("9")) {
        		sql="ahr.dischargeDate24 between to_date('"+date+"','dd.mm.yyyy') and to_date('"
            			+dateEnd+"','dd.mm.yyyy') and ahr.transferDepartmentIn is not null" ;
        		request.setAttribute("titleReestr","Список пациентов, переведенных в другое отделение") ;
        		request.setAttribute("diagnosField","idcDepartmentCode") ;
        	}
    	    if (reportStr!=null && !reportStr.equals("") &&!reportStr.equals("0")) {
    	    	request.setAttribute("reportStrLeftJoin","left join ReportSetTYpeParameterType rspt on ahr."+request.getAttribute("diagnosField")+" between rspt.codefrom and rspt.codeto left join VocReportSetParameterType vrspt on rspt.parameterType_id=vrspt.id") ;
    	    }
        	
        	/*
        	entrance=firstYear"/>
      <msh:tableColumn columnName="Поступ. втч вп. в жизни" property="12" addParam="&typeView=4&entrance=firstLife"/>
      <msh:tableColumn columnName="Пост. недобр." property="13" addParam="&typeView=4&entrance=involuntarily
        	*/
        	String entrance=request.getParameter("entrance") ;String discharge=request.getParameter("discharge");
        	if (entrance!=null) {
        		if (entrance.equals("isFirstCurrentYear")) {
        			sql=sql+" and ahr.isFirstCurrentYear='1'" ;
        		} else if (entrance.equals("isFirstLife")) {
        			sql=sql+" and ahr.isFirstLife='1'" ;
        		} else if (entrance.equals("isIncompetent")) {
        			sql=sql+" and ahr.isIncompetent='1'" ;
        		}
        	}
        	if (discharge!=null) {
        		if (discharge.equals("death")) {
        			sql=sql+" and ahr.isDeath='1'" ;
        		}
        	}
        	String beginAge=request.getParameter("beginAge") ;
        	String entranceAge=request.getParameter("entranceAge") ;
        	String dischargeAge=request.getParameter("dischargeAge") ;
        	String endAge=request.getParameter("endAge") ;
        	
        	if (beginAge!=null) {
        		sql=sql+" and ahr.ageEntranceSls between "+beginAge.replaceAll("-", " and ");
        	} else if (entranceAge!=null){
        		sql=sql+" and ahr.ageEntranceSls between "+entranceAge.replaceAll("-", " and ");
        	} else if (dischargeAge!=null) {
        		sql=sql+" and ahr.ageDischargeSls between "+dischargeAge.replaceAll("-", " and ");
        	} else if (endAge!=null) {
        		
        		sql=sql+" and cast('"+dateEnd.substring(6)+"' as int)-cast(to_char(ahr.birthday,'yyyy') as int) +(case when (cast('";
        		sql=sql+dateEnd.substring(3,5)+"' as int)-cast(to_char(ahr.birthday, 'mm') as int) +(case when (cast('";
        		sql=sql+dateEnd.substring(0,2)+"' as int) - cast(to_char(ahr.birthday,'dd') as int)<0) then -1 else 0 end)<0) then -1 else 0 end) between ";
        		sql=sql+endAge.replaceAll("-", " and ");
        	}
        	
        	
        	
        	request.setAttribute("dateSql", sql) ;
    	%>
    <msh:section>
    <msh:sectionTitle>Результаты поиска за период с ${dateBegin} по ${dateEnd}.</msh:sectionTitle>
    </msh:section>
   
    <msh:section><ecom:webQuery maxResult="5000" name="reestr" nameFldSql="reestr_sql" nativeSql="
select ahr.sls as slsid
,(select list(vrspt1.strCode) from ReportSetTYpeParameterType rspt1 
left join VocReportSetParameterType vrspt1 on rspt1.parameterType_id=vrspt1.id 
where ahr.${diagnosField} between rspt1.codefrom and rspt1.codeto
 and vrspt1.classname='F36_DIAG') 
 as listStr
,ss.code as sscode
,p.lastname||' '||p.firstname||' '||p.middlename as fio
,to_char(p.birthday,'dd.mm.yyyy') as birthday
,to_char(ahr.entranceHospDate24,'dd.mm.yyyy') as slsdateStart
,to_char(ahr.entranceDate24,'dd.mm.yyyy') as slodateStart
,to_char(ahr.dischargeDate24,'dd.mm.yyyy') as slodateFinish
,ahr.idcEntranceCode as idcEntranceCode
,ahr.idcDepartmentCode as idcDepartmentCode
,ahr.idcDischarge as idcDischarge
,case when ahr.isDeath='1' then 'Да' else null end as death
,case when ahr.isEmergency='1' then 'Экстр.' else 'План.' end as emer
,
case when (ahr.dischargeDate24-ahr.entranceHospDate24)=0 then 1
else 
ahr.dischargeDate24-ahr.entranceHospDate24+cast(ahr.addbeddays as int)
end as beddays
,case when ahr.isFirstCurrentYear='1' then 'Да' else null end as isFirstCurrentYear
,case when ahr.isFirstLife='1' then 'Да' else null end as isFirstLife
 from AggregateHospitalReport ahr
 left join medcase sls on ahr.sls=sls.id
left join StatisticStub ss on ss.id=sls.statisticStub_id
left join VocHospitalizationResult vhr on vhr.id=sls.result_id
${reportStrLeftJoin}
left join Patient p on p.id=sls.patient_id
where ${dateSql}   ${reportStrSql}
      ${paramSql} 
${paramSql}
${departmentSql}

group by ahr.sls,ahr.dischargeDate24,ahr.entranceDate24,ahr.idcDepartmentCode,ahr.idcEntranceCode,ahr.idcDischarge
,ss.code,sls.emergency,sls.orderType_id,p.lastname,p.firstname
,p.middlename,p.birthday,sls.dateStart,sls.dateFinish,ahr.entranceHospDate24,ahr.addbeddays
,ahr.idcDischarge,ahr.isDeath,ahr.isEmergency,ahr.isFirstCurrentYear,ahr.isFirstLife
order by p.lastname,p.firstname,p.middlename " />
    <msh:sectionTitle>
    <form action="print-stac_report36_3.do" method="post" target="_blank">
    ${titleReestr} ${param.strname}
    <input type='hidden' name="sqlText" id="sqlText" value="${reestr_sql}"> 
    <input type='hidden' name="sqlInfo" id="sqlInfo" value="Список поступивших пациентов ${param.strname}">
    <input type='hidden' name="sqlColumn" id="sqlColumn" value="">
    <input type='hidden' name="s" id="s" value="PrintService">
    <input type='hidden' name="m" id="m" value="printNativeQuery">
    <input type="submit" value="Печать"> 
    </form>
    
    </msh:sectionTitle>
    <msh:sectionContent>
    ${reestr_sql}
    <msh:table name="reestr" 
    viewUrl="entityShortView-stac_ssl.do" 
     action="entityView-stac_ssl.do" idField="1">
      <msh:tableColumn columnName="##" property="sn" />
      <msh:tableColumn columnName="Строки отчета" property="2" />
      <msh:tableColumn columnName="№стат. карт" property="3" />
      <msh:tableColumn columnName="ФИО пациента" property="4" />
      <msh:tableColumn columnName="Возраст" property="5" />
      <msh:tableColumn columnName="Дата поступления в стац." property="6"/>
      <msh:tableColumn columnName="Дата поступления в отд." property="7"/>
      <msh:tableColumn columnName="Дата выписки (перевода из отделения)" property="8"/>
      <msh:tableColumn columnName="МКБ напр." property="9"/>
      <msh:tableColumn columnName="МКБ отд. кл." property="10"/>
      <msh:tableColumn columnName="МКБ вып." property="11"/>
      <msh:tableColumn columnName="Лет. исход" property="12"/>
      <msh:tableColumn columnName="Показания" property="13"/>
      <msh:tableColumn columnName="К.дн" property="14"/>
      <msh:tableColumn columnName="Впервые в этом году" property="15"/>
      <msh:tableColumn columnName="Впервые в жизни" property="16"/>
    </msh:table>
    </msh:sectionContent>
    </msh:section>    		

    <%
    } 
    	
    }
    }
    	%>
    
  </tiles:put>
</tiles:insert>