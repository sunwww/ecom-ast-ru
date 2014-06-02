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
  	String typeView=ActionUtil.updateParameter("Report36HOSP","typeView","1", request) ;
  	String typeDate=ActionUtil.updateParameter("Report36HOSP","typeDate","2", request) ;
  	
  	StringBuilder paramSql= new StringBuilder() ;
  	StringBuilder paramHref= new StringBuilder() ;
  	
	ActionUtil.setParameterFilterSql("strcode", "vrspt.id", request) ;
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
        	<msh:autoComplete property="department" fieldColSpan="4" horizontalFill="true" label="Отделение" vocName="vocLpuHospOtdAll"/>
        </msh:row>
        <msh:row>
        	<msh:autoComplete property="hospType" fieldColSpan="4" horizontalFill="true" label="Тип стационара" vocName="vocHospType"/>
        </msh:row>
        <msh:row>
        	<msh:autoComplete property="sex" fieldColSpan="4" horizontalFill="true" label="Пол" vocName="vocSex"/>
        </msh:row>
        <msh:row>
        	<msh:autoComplete property="strcode" parentId="F36_DIAG" fieldColSpan="4" horizontalFill="true" label="Строка" vocName="vocReportSetParameterTypeByClassname"/>
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
          
           checkFieldUpdate('typeView','${typeView}',1) ;
           

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
    if (date!=null) {
    if (view.equals("1")) { %>

    <%} else if (view.equals("2")) { %>
    
    <msh:section>
    <msh:sectionTitle>Результаты поиска за период с ${dateBegin} по ${dateEnd}.</msh:sectionTitle>
    </msh:section>
   
    <msh:section>
    <ecom:webQuery name="Report36HOSPswod" nameFldSql="Report36HOSPswod_sql" nativeSql="
select
vrspt.id||'&strcode='||vrspt.id,vrspt.name,vrspt.strCode,vrspt.code 
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
${departmentSql}
and
ahr.ageEntranceSls between 0 and 14 then ahr.sls else null end)  as cntEntrance0_14
,count(case when 

ahr.entranceDate24 between to_date('${dateBegin}','dd.mm.yyyy') and to_date('${dateEnd}','dd.mm.yyyy')
${departmentSql}
and  ahr.idcEntranceCode between rspt.codefrom and rspt.codeto 

and
ahr.ageEntranceSls between 15 and 17 then ahr.sls else null end)  as cntEntrance15_17
,count(case when
ahr.entranceDate24 between to_date('${dateBegin}','dd.mm.yyyy') and to_date('${dateEnd}','dd.mm.yyyy')
and ahr.idcEntranceCode between rspt.codefrom and rspt.codeto 
and ahr.isFirstCurrentYear='1' ${departmentSql}
then ahr.sls else null end) as cntEntranceAdmHosp
,count(case when
ahr.entranceDate24 between to_date('${dateBegin}','dd.mm.yyyy') and to_date('${dateEnd}','dd.mm.yyyy')
and ahr.idcEntranceCode between rspt.codefrom and rspt.codeto 
and ahr.isFirstLife='1' ${departmentSql}
then ahr.sls else null end) as cntEntranceHosp
,count(case when
ahr.entranceDate24 between to_date('${dateBegin}','dd.mm.yyyy') and to_date('${dateEnd}','dd.mm.yyyy')
and ahr.idcEntranceCode between rspt.codefrom and rspt.codeto 
and ahr.isIncompetent='1' ${departmentSql}
then ahr.sls else null end) as cntEntranceHospNeDobr



,count(case when
ahr.dischargeDate24 between to_date('${dateBegin}','dd.mm.yyyy') and to_date('${dateEnd}','dd.mm.yyyy')
and ahr.idcDischarge between rspt.codefrom and rspt.codeto 
and ahr.transferDepartmentIn is null
${departmentSql}
then ahr.sls else null end) as cntDischargeAll

,cast('1' as int)
as sumDayAllDischarge

,count(case when
ahr.dischargeDate24 between to_date('${dateBegin}','dd.mm.yyyy') and to_date('${dateEnd}','dd.mm.yyyy')
and ahr.idcDischarge between rspt.codefrom and rspt.codeto
and ahr.isDeath='1'
and ahr.transferDepartmentIn is null
${departmentSql}
then ahr.sls else null end) as cntDischargeDeath

,cast('1' as int)
as sumDayDeathDischarge


,count(case when 
ahr.dischargeDate24 between to_date('${dateBegin}','dd.mm.yyyy') and to_date('${dateEnd}','dd.mm.yyyy')
and ahr.transferDepartmentIn is null
${departmentSql}
and ahr.idcDischarge between rspt.codefrom and rspt.codeto 
and ahr.ageDischargeSlo between 0 and 14 then ahr.sls else null end)  as cntDischarge0_14

,cast('1'
as int)
as sumDayDischarge0_14

,count(case when 
ahr.dischargeDate24 between to_date('${dateBegin}','dd.mm.yyyy') and to_date('${dateEnd}','dd.mm.yyyy')
and ahr.transferDepartmentIn is null
${departmentSql}
and ahr.idcDischarge between rspt.codefrom and rspt.codeto 
and ahr.ageDischargeSlo between 15 and 17 then ahr.sls else null end)  as cntDischarge15_17

,cast(sum(1) as int)
as sumDayDischarge15_17


,count(case when
 ahr.entranceDate24 <= to_date('${dateEnd}','dd.mm.yyyy') 
and (ahr.dischargeDate24 > to_date('${dateEnd}','dd.mm.yyyy') 
or ahr.dischargeDate24 is null
)
${departmentSql}
and ahr.idcDischarge between rspt.codefrom and rspt.codeto 
then ahr.sls else null end) as cntFinishPeriodAll
,count(case when
 ahr.entranceDate24 <= to_date('${dateEnd}','dd.mm.yyyy') 
and (ahr.dischargeDate24 > to_date('${dateEnd}','dd.mm.yyyy') 
or ahr.dischargeDate24 is null
)
${departmentSql}
and ahr.ageDischargeSlo between 0 and 14
and ahr.idcDischarge between rspt.codefrom and rspt.codeto 
then ahr.sls else null end) as cntFinishPeriod0_14

,count(case when
 ahr.entranceDate24 <= to_date('${dateEnd}','dd.mm.yyyy') 
and (ahr.dischargeDate24 > to_date('${dateEnd}','dd.mm.yyyy') 
or ahr.dischargeDate24 is null
)
${departmentSql}
and ahr.ageDischargeSlo between 15 and 17
and ahr.idcDischarge between rspt.codefrom and rspt.codeto 
then ahr.sls else null end) as cntFinishPeriod15_17

,count(case when
ahr.entranceDate24 between to_date('${dateBegin}','dd.mm.yyyy') and to_date('${dateEnd}','dd.mm.yyyy')
and ahr.idcDischarge between rspt.codefrom and rspt.codeto 
and ahr.transferDepartmentFrom is not null
${departmentSql}
then ahr.sls else null end) as cntTransferFrom

,count(case when
ahr.dischargeDate24 between to_date('${dateBegin}','dd.mm.yyyy') and to_date('${dateEnd}','dd.mm.yyyy')
and ahr.idcDischarge between rspt.codefrom and rspt.codeto 
and ahr.transferDepartmentIn is not null
${departmentSql}
then ahr.sls else null end) as cntTransferIn
 from AggregateHospitalReport ahr

left join VocReportSetParameterType vrspt on vrspt.classname='F36_DIAG'
left join ReportSetTYpeParameterType rspt on rspt.parameterType_id=vrspt.id
left join Patient p on p.id=ahr.patient

where ( 
 ahr.dischargeDate24 between to_date('${dateBegin}','dd.mm.yyyy') and to_date('${dateEnd}','dd.mm.yyyy')
or ahr.dischargeDate24 is null)
and (ahr.entranceDate24 <= to_date('${dateEnd}','dd.mm.yyyy') 
) 

and (ahr.idcDepartmentCode between rspt.codefrom and rspt.codeto 
or ahr.idcDischarge between rspt.codefrom and rspt.codeto
or ahr.idcEntranceCode between rspt.codefrom and rspt.codeto

 )
${age_sql} 
${paramSql}
${departmentSql}
${strcodeSql}
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
    viewUrl="stac_report_36.do?${paramHref}&typeAge=${typeAge}&typeView=5&department=${param.department}&typeAge=${typeAge}&noViewForm=1&short=Short&dateBegin=${dateBegin}&dateEnd=${dateEnd}" 
     action="stac_report_36.do?${paramHref}&typeAge=${typeAge}&typeView=5&department=${param.department}&typeAge=${typeAge}&noViewForm=0&dateBegin=${dateBegin}&dateEnd=${dateEnd}" idField="1" >
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
      <msh:tableColumn columnName="перевед. из др." property="25"/>
      <msh:tableColumn columnName="перевед. в др." property="26"/>
    </msh:table>
    
    </msh:sectionContent>
    </msh:section>
    <%} else if (view.equals("3")) {
       
        	%>
     
        <% } else if (view.equals("4")) {
    	%>
    
    <msh:section>
    <msh:sectionTitle>Результаты поиска за период с ${dateBegin} по ${dateEnd}.</msh:sectionTitle>
    </msh:section>
   
    <msh:section>
    <msh:sectionTitle>Список поступивших пациентов ${param.strname}
    
    </msh:sectionTitle>
    <msh:sectionContent>
    <ecom:webQuery name="journal_surOperation" nativeSql="
select 
ahr.sls as slsid,list(vrspt1.strCode) as listStr
,ss.code as sscode
,p.lastname||' '||p.firstname||' '||p.middlename as fio
,p.birthday as birthday
,to_char(ahr.entranceHospDate24,'dd.mm.yyyy') as slsdateStart
,to_char(ahr.entranceDate24,'dd.mm.yyyy') as slodateStart
,to_char(ahr.dischargeDate24,'dd.mm.yyyy') as slodateFinish
 ,ahr.idcDischarge as idcDischarge
 ,ahr.idcDepartmentCode as idcDepartmentCode
 ,ahr.idcEntranceCode as idcEntranceCode
,case when ahr.isDeath='1' then 'Да' else null end as death
,case when ahr.isEmergency='1' then 'Экстр.' else 'План.' end as emer
,
case when (ahr.dischargeDate24-ahr.entranceHospDate24)=0 then 1
else 
ahr.dischargeDate24-ahr.entranceHospDate24+ahr.addbeddays
end as beddays

 from AggregateHospitalReport ahr
 left join medcase sls on ahr.sls=sls.id
left join StatisticStub ss on ss.id=sls.statisticStub_id
left join VocHospitalizationResult vhr on vhr.id=sls.result_id
left join ReportSetTYpeParameterType rspt on ahr.idcDischarge between rspt.codefrom and rspt.codeto
left join VocReportSetParameterType vrspt on rspt.parameterType_id=vrspt.id
left join ReportSetTYpeParameterType rspt1 on ahr.idcDischarge between rspt1.codefrom and rspt1.codeto
left join VocReportSetParameterType vrspt1 on rspt1.parameterType_id=vrspt1.id and vrspt1.classname='F36_DIAG' 
left join Patient p on p.id=sls.patient_id
where ahr.entranceDate24 between to_date('${dateBegin}','dd.mm.yyyy') and to_date('${dateEnd}','dd.mm.yyyy')
  and ahr.transferDepartmentFrom is null
${age_sql} 
${paramSql}
${departmentSql}
${strcodeSql}

group by ahr.sls,ahr.dischargeDate24,ahr.entranceDate24,ahr.idcDepartmentCode,ahr.idcEntranceCode,ahr.idcDischarge
,ss.code,sls.emergency,sls.orderType_id,p.lastname,p.firstname
,p.middlename,p.birthday,sls.dateStart,sls.dateFinish,ahr.entranceHospDate24,ahr.addbeddays
,ahr.idcDischarge,ahr.isDeath,ahr.isEmergency
order by p.lastname,p.firstname,p.middlename " />
    <msh:table name="journal_surOperation" 
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
      <msh:tableColumn columnName="МКБ вып." property="9"/>
      <msh:tableColumn columnName="МКБ отд. кл." property="10"/>
      <msh:tableColumn columnName="МКБ напр." property="11"/>
      <msh:tableColumn columnName="Лет. исход" property="12"/>
      <msh:tableColumn columnName="Показания" property="13"/>
      <msh:tableColumn columnName="К.дн" property="14"/>
    </msh:table>
    </msh:sectionContent>
    </msh:section>    		
        <% } else if (view.equals("5")) {
    	%>
    
    <msh:section>
    <msh:sectionTitle>Результаты поиска за период с ${dateBegin} по ${dateEnd}.</msh:sectionTitle>
    </msh:section>
   
    <msh:section>
    <msh:sectionTitle>Список выбывших пациентов ${param.strname}
    
    </msh:sectionTitle>
    <msh:sectionContent>
    <ecom:webQuery name="journal_surOperation" nativeSql="
select 
ahr.sls as slsid,list(vrspt1.strCode) as listStr
,ss.code as sscode
,p.lastname||' '||p.firstname||' '||p.middlename as fio
,p.birthday as birthday
,to_char(ahr.entranceHospDate24,'dd.mm.yyyy') as slsdateStart
,to_char(ahr.entranceDate24,'dd.mm.yyyy') as slodateStart
,to_char(ahr.dischargeDate24,'dd.mm.yyyy') as slodateFinish
 ,ahr.idcDischarge as idcDischarge
 ,ahr.idcDepartmentCode as idcDepartmentCode
 ,ahr.idcEntranceCode as idcEntranceCode
,case when ahr.isDeath='1' then 'Да' else null end as death
,case when ahr.isEmergency='1' then 'Экстр.' else 'План.' end as emer
,
case when (ahr.dischargeDate24-ahr.entranceHospDate24)=0 then 1
else 
ahr.dischargeDate24-ahr.entranceHospDate24+ahr.addbeddays
end as beddays

 from AggregateHospitalReport ahr
 left join medcase sls on ahr.sls=sls.id
left join StatisticStub ss on ss.id=sls.statisticStub_id
left join VocHospitalizationResult vhr on vhr.id=sls.result_id
left join ReportSetTYpeParameterType rspt on ahr.idcDischarge between rspt.codefrom and rspt.codeto
left join VocReportSetParameterType vrspt on rspt.parameterType_id=vrspt.id
left join ReportSetTYpeParameterType rspt1 on ahr.idcDischarge between rspt1.codefrom and rspt1.codeto
left join VocReportSetParameterType vrspt1 on rspt1.parameterType_id=vrspt1.id and vrspt1.classname='F36_DIAG' 
left join Patient p on p.id=sls.patient_id
where ahr.dischargeDate24 between to_date('${dateBegin}','dd.mm.yyyy') and to_date('${dateEnd}','dd.mm.yyyy')
  and ahr.transferDepartmentIn is null
${age_sql} 
${paramSql}
${departmentSql}
${strcodeSql}

group by ahr.sls,ahr.dischargeDate24,ahr.entranceDate24,ahr.idcDepartmentCode,ahr.idcEntranceCode,ahr.idcDischarge
,ss.code,sls.emergency,sls.orderType_id,p.lastname,p.firstname
,p.middlename,p.birthday,sls.dateStart,sls.dateFinish,ahr.entranceHospDate24,ahr.addbeddays
,ahr.idcDischarge,ahr.isDeath,ahr.isEmergency
order by p.lastname,p.firstname,p.middlename " />
    <msh:table name="journal_surOperation" 
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
      <msh:tableColumn columnName="МКБ вып." property="9"/>
      <msh:tableColumn columnName="МКБ отд. кл." property="10"/>
      <msh:tableColumn columnName="МКБ напр." property="11"/>
      <msh:tableColumn columnName="Лет. исход" property="12"/>
      <msh:tableColumn columnName="Показания" property="13"/>
      <msh:tableColumn columnName="К.дн" property="14"/>
    </msh:table>
    </msh:sectionContent>
    </msh:section>    		
        <% } else if (view.equals("6")) {
    	%>
    
    <msh:section>
    <msh:sectionTitle>Результаты поиска за период с ${dateBegin} по ${dateEnd}.</msh:sectionTitle>
    </msh:section>
   
    <msh:section>
    <msh:sectionTitle>Состоящие на начало периода пациентов ${param.strname}
    
    </msh:sectionTitle>
    <msh:sectionContent>
    <ecom:webQuery name="journal_surOperation" nativeSql="
select 
ahr.sls as slsid,list(vrspt1.strCode) as listStr
,ss.code as sscode
,p.lastname||' '||p.firstname||' '||p.middlename as fio
,p.birthday as birthday
,to_char(ahr.entranceHospDate24,'dd.mm.yyyy') as slsdateStart
,to_char(ahr.entranceDate24,'dd.mm.yyyy') as slodateStart
,to_char(ahr.dischargeDate24,'dd.mm.yyyy') as slodateFinish
 ,ahr.idcDischarge as idcDischarge
 ,ahr.idcDepartmentCode as idcDepartmentCode
 ,ahr.idcEntranceCode as idcEntranceCode
,case when ahr.isDeath='1' then 'Да' else null end as death
,case when ahr.isEmergency='1' then 'Экстр.' else 'План.' end as emer
,
case when (ahr.dischargeDate24-ahr.entranceHospDate24)=0 then 1
else 
ahr.dischargeDate24-ahr.entranceHospDate24+ahr.addbeddays
end as beddays

 from AggregateHospitalReport ahr
 left join medcase sls on ahr.sls=sls.id
left join StatisticStub ss on ss.id=sls.statisticStub_id
left join VocHospitalizationResult vhr on vhr.id=sls.result_id
left join ReportSetTYpeParameterType rspt on ahr.idcDischarge between rspt.codefrom and rspt.codeto
left join VocReportSetParameterType vrspt on rspt.parameterType_id=vrspt.id
left join ReportSetTYpeParameterType rspt1 on ahr.idcDischarge between rspt1.codefrom and rspt1.codeto
left join VocReportSetParameterType vrspt1 on rspt1.parameterType_id=vrspt1.id and vrspt1.classname='F36_DIAG' 
left join Patient p on p.id=sls.patient_id
where ahr.entranceDate24 < to_date('${dateBegin}','dd.mm.yyyy') 
and (ahr.dischargeDate24 > to_date('${dateBegin}','dd.mm.yyyy') 
or ahr.dischargeDate24 is null
) ${departmentSql}
and ahr.idcDepartmentCode between rspt.codefrom and rspt.codeto 
${age_sql} 
${paramSql}
${departmentSql}
${strcodeSql}

group by ahr.sls,ahr.dischargeDate24,ahr.entranceDate24,ahr.idcDepartmentCode,ahr.idcEntranceCode,ahr.idcDischarge
,ss.code,sls.emergency,sls.orderType_id,p.lastname,p.firstname
,p.middlename,p.birthday,sls.dateStart,sls.dateFinish,ahr.entranceHospDate24,ahr.addbeddays
,ahr.idcDischarge,ahr.isDeath,ahr.isEmergency
order by p.lastname,p.firstname,p.middlename" />
    <msh:table name="journal_surOperation" 
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
      <msh:tableColumn columnName="МКБ вып." property="9"/>
      <msh:tableColumn columnName="МКБ отд. кл." property="10"/>
      <msh:tableColumn columnName="МКБ напр." property="11"/>
      <msh:tableColumn columnName="Лет. исход" property="12"/>
      <msh:tableColumn columnName="Показания" property="13"/>
      <msh:tableColumn columnName="К.дн" property="14"/>
    </msh:table>
    </msh:sectionContent>
    </msh:section>    		
        <% } else if (view.equals("7")) {
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
ahr.sls as slsid,list(vrspt1.strCode) as listStr
,ss.code as sscode
,p.lastname||' '||p.firstname||' '||p.middlename as fio
,p.birthday as birthday
,to_char(ahr.entranceHospDate24,'dd.mm.yyyy') as slsdateStart
,to_char(ahr.entranceDate24,'dd.mm.yyyy') as slodateStart
,to_char(ahr.dischargeDate24,'dd.mm.yyyy') as slodateFinish
 ,ahr.idcDischarge as idcDischarge
 ,ahr.idcDepartmentCode as idcDepartmentCode
 ,ahr.idcEntranceCode as idcEntranceCode
,case when ahr.isDeath='1' then 'Да' else null end as death
,case when ahr.isEmergency='1' then 'Экстр.' else 'План.' end as emer
,
case when (ahr.dischargeDate24-ahr.entranceHospDate24)=0 then 1
else 
ahr.dischargeDate24-ahr.entranceHospDate24+ahr.addbeddays
end as beddays

 from AggregateHospitalReport ahr
 left join medcase sls on ahr.sls=sls.id
left join StatisticStub ss on ss.id=sls.statisticStub_id
left join VocHospitalizationResult vhr on vhr.id=sls.result_id
left join ReportSetTYpeParameterType rspt on ahr.idcDischarge between rspt.codefrom and rspt.codeto
left join VocReportSetParameterType vrspt on rspt.parameterType_id=vrspt.id
left join ReportSetTYpeParameterType rspt1 on ahr.idcDischarge between rspt1.codefrom and rspt1.codeto
left join VocReportSetParameterType vrspt1 on rspt1.parameterType_id=vrspt1.id and vrspt1.classname='F36_DIAG' 
left join Patient p on p.id=sls.patient_id
where ahr.dischargeDate24 between to_date('${dateBegin}','dd.mm.yyyy') and to_date('${dateEnd}','dd.mm.yyyy')
  and ahr.transferDepartmentIn is null
${age_sql} 
${paramSql}
${departmentSql}
${strcodeSql}

group by ahr.sls,ahr.dischargeDate24,ahr.entranceDate24,ahr.idcDepartmentCode,ahr.idcEntranceCode,ahr.idcDischarge
,ss.code,sls.emergency,sls.orderType_id,p.lastname,p.firstname
,p.middlename,p.birthday,sls.dateStart,sls.dateFinish,ahr.entranceHospDate24,ahr.addbeddays
,ahr.idcDischarge,ahr.isDeath,ahr.isEmergency
order by p.lastname,p.firstname,p.middlename " />
       <msh:table name="journal_surOperation" 
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
      <msh:tableColumn columnName="МКБ вып." property="9"/>
      <msh:tableColumn columnName="МКБ отд. кл." property="10"/>
      <msh:tableColumn columnName="МКБ напр." property="11"/>
      <msh:tableColumn columnName="Лет. исход" property="12"/>
      <msh:tableColumn columnName="Показания" property="13"/>
      <msh:tableColumn columnName="К.дн" property="14"/>
    </msh:table>
    </msh:sectionContent>
    </msh:section>    		
 		
    <%
    } 
    	
    }
    	%>
    
  </tiles:put>
</tiles:insert>