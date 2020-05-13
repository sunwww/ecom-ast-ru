<%@page import="ru.ecom.web.util.ActionUtil"%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://struts.apache.org/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://www.nuzmsh.ru/tags/msh" prefix="msh" %>
<%@ taglib uri="http://www.ecom-ast.ru/tags/ecom" prefix="ecom" %>
<%@ taglib tagdir="/WEB-INF/tags" prefix="tags" %>

<tiles:insert page="/WEB-INF/tiles/main${param.short}Layout.jsp" flush="true" >

  <tiles:put name="title" type="string">
    <msh:title mainMenu="Journals" title="Переводы по отделения"/>
  </tiles:put>
  <tiles:put name="side" type="string">
  	 <tags:stac_journal currentAction="transfer"/>
  </tiles:put>
  <tiles:put name="body" type="string">
  <%
    String typeView =ActionUtil.updateParameter("Stac_journal_transfer","typeView","1", request) ;
    String typeReport =ActionUtil.updateParameter("Stac_journal_transfer","typeReport","1", request) ;
    if (request.getParameter("short")==null) {
  %>
  
    <msh:form action="/stac_journal_transfer_department.do" defaultField="departmentName" disableFormDataConfirm="true" method="GET">
    <msh:panel>
      <msh:row>
        <msh:separator label="Параметры поиска" colSpan="7"/>
      </msh:row>

        <msh:row>
	        <td class="label" title="Просмотр данных (typeView)" colspan="1"><label for="typeViewName" id="typeViewLabel">Отобразить:</label></td>
	        <td onclick="this.childNodes[1].checked='checked';"  colspan="2">
	        	<input type="radio" name="typeView" value="1">  начиная с любого отделения с госпитализации 
	        </td>
	        <td onclick="this.childNodes[1].checked='checked';"  colspan="2">
	        	<input type="radio" name="typeView" value="2">  1 отделение при госпитализации
	        </td>
        </msh:row>
        <msh:row>
	        <td class="label" title="Вид отчета (typeReport)" colspan="1"><label for="typeReportName" id="typeReportLabel">Вид отчета:</label></td>
	        <td onclick="this.childNodes[1].checked='checked';"  colspan="2">
	        	<input type="radio" name="typeReport" value="1">  реестр
	        </td>
	        <td onclick="this.childNodes[1].checked='checked';"  colspan="4">
	        	<input type="radio" name="typeReport" value="2">  свод по длительности 1 отделения
	        </td>
        </msh:row>
        <msh:row>
        	<msh:textField property="dateBegin" label="Дата начала"/>
        	<msh:textField property="dateEnd" label="Дата окончания"/>
        </msh:row>
        <msh:row>
        	<msh:autoComplete property="department" fieldColSpan="7" horizontalFill="true" label="Отделение 1" vocName="vocLpuHospOtdAll"/>
        </msh:row>
        <msh:row>
        	<msh:autoComplete property="department1" fieldColSpan="7" horizontalFill="true" label="Отделение 2" vocName="vocLpuHospOtdAll"/>
        </msh:row>
        <msh:row>
        	<msh:autoComplete property="department2" fieldColSpan="7" horizontalFill="true" label="Отделение 3" vocName="vocLpuHospOtdAll"/>
        </msh:row>
      <msh:row>
           <td colspan="11">
            <input type="submit" onclick="find()" value="Найти" />
          </td>
      </msh:row>
      
    </msh:panel>
    </msh:form>
    <script type='text/javascript'>
    
    checkFieldUpdate('typeView','${typeView}',1) ;
    checkFieldUpdate('typeReport','${typeReport}',1) ;
  
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
	function getPatientByCntDays() {
		var dateBegin='${param.dateBegin}' ; var dateEnd='${param.dateEnd}';
		var typeView = '1' ; var typeReport = '${typeReport}' ;
	}
    function find() {
    	var frm = document.forms[0] ;
    	frm.target='' ;
    	frm.action='stac_journal_transfer_department.do' ;
    }
    function print() {
    	
    }
    </script>

    
    <%
    }
    String department = request.getParameter("department") ;
    String date = request.getParameter("dateBegin") ;
    String date1 = request.getParameter("dateEnd") ;
    if (date1==null || date1.equals("")) {date1=date;}
    if (department!=null && !department.equals("") && !department.equals("0") 
    		&& date!=null && !date.equals("") )  {
    	
    	ActionUtil.setParameterFilterSql("department","slo.department_id", request) ;
    	ActionUtil.setParameterFilterSql("department1","slo1.department_id", request) ;
    	ActionUtil.setParameterFilterSql("department2","slo2.department_id", request) ;
        request.setAttribute("isReportBase", ActionUtil.isReportBase(date,date1,request));
    	if (typeView!=null && typeView.equals("1")) {
    		String days = request.getParameter("cntDays") ;
    		String sloPrev = request.getParameter("sloFirst") ;
    		if (days!=null &&!days.equals("")) request.setAttribute("daysSql", " and case when (coalesce(slo.dateFinish,slo.transferDate,CURRENT_DATE)-slo.dateStart)=0 then 1 when bf.addCaseDuration='1' then ((coalesce(slo.dateFinish,slo.transferDate,CURRENT_DATE)-slo.dateStart)+1) else (coalesce(slo.dateFinish,slo.transferDate,CURRENT_DATE)-slo.dateStart) end="+days);
    		if (sloPrev!=null &&sloPrev.equals("Перевод")) {
    			request.setAttribute("sloPrevSql", " and slo.prevMedCase_id is not null") ;
    		} else if (sloPrev!=null) {
    			request.setAttribute("sloPrevSql", " and slo.prevMedCase_id is null") ;
    		}
    		if (typeReport!=null && typeReport.equals("1")) {
    			
    	%>
    
    <msh:section title="Реестр за период ${param.dateBegin}-${param.dateEnd} ${emergencyInfo} ${param.sloFirst}">
    <ecom:webQuery isReportBase="${isReportBase}" nameFldSql="journal_expert_sql" name="journal_expert" nativeSql="
select sls.id,pat.lastname||' '||pat.firstname||' '||pat.middlename as fio,ss.code,mlSls.name as mlSls
,to_char(sls.dateStart,'dd.mm.yyyy')||' '||cast(sls.entranceTime as varchar(5)) as dateStartSls
,ml.name as mlname
,to_char(slo.dateStart,'dd.mm.yyyy')||' '||cast(slo.entranceTime as varchar(5)) as dateStartSlo
,ml1.name as mlSlo1,to_char(slo1.dateStart,'dd.mm.yyyy')||' '||cast(slo1.entranceTime as varchar(5)) as dateStartSlo1
,ml2.name as mlSlo2,to_char(slo2.dateStart,'dd.mm.yyyy')||' '||cast(slo2.entranceTime as varchar(5)) as dateStartSlo2
,case
            when (coalesce(slo.dateFinish,slo.transferDate,CURRENT_DATE)-slo.dateStart)=0 then 1
            when bf.addCaseDuration='1' then ((coalesce(slo.dateFinish,slo.transferDate,CURRENT_DATE)-slo.dateStart)+1)
            else (coalesce(slo.dateFinish,slo.transferDate,CURRENT_DATE)-slo.dateStart)
          end as beds
,case
            when (coalesce(slo1.dateFinish,slo1.transferDate,CURRENT_DATE)-slo1.dateStart)=0 then 1
            when bf.addCaseDuration='1' then ((coalesce(slo1.dateFinish,slo1.transferDate,CURRENT_DATE)-slo1.dateStart)+1)
            else (coalesce(slo1.dateFinish,slo1.transferDate,CURRENT_DATE)-slo1.dateStart)
          end as beds1
,case
            when (coalesce(slo2.dateFinish,slo2.transferDate,CURRENT_DATE)-slo2.dateStart)=0 then 1
            when bf.addCaseDuration='1' then ((coalesce(slo2.dateFinish,slo2.transferDate,CURRENT_DATE)-slo2.dateStart)+1)
            else (coalesce(slo2.dateFinish,slo2.transferDate,CURRENT_DATE)-slo2.dateStart)
          end as beds2
from medcase slo
left join BedFund bf on bf.id=slo.bedFund_id
left join MedCase sls on sls.id=slo.parent_id
left join Patient pat on pat.id=sls.patient_id
left join StatisticStub ss on ss.id=sls.statisticStub_id
left join medcase slo1 on slo1.prevMedCase_id=slo.id
left join medcase slo2 on slo2.prevMedCase_id=slo1.id
left join mislpu mlSls on mlSls.id=sls.department_id
left join mislpu ml on ml.id=slo.department_id
left join mislpu ml1 on ml1.id=slo1.department_id
left join mislpu ml2 on ml2.id=slo2.department_id

where 
slo.dateStart between to_date('${param.dateBegin}','dd.mm.yyyy') and to_date('${param.dateEnd}','dd.mm.yyyy')
${departmentSql} ${department1Sql} ${department2Sql}
and slo.dtype='DepartmentMedCase' and slo1.dtype='DepartmentMedCase'
and (slo2.dtype='DepartmentMedCase' or slo2.dtype is null)
 ${viewSql} 
${filterByCodeSql} ${filterByNameSql} ${sloPrevSql} ${daysSql}
order by pat.lastname
    " />
    <msh:sectionTitle>
    
    <form action="print-stac_journal_transfer_department.do" method="post" target="_blank">
    Реестр услуг.
    <input type='hidden' name="sqlText" id="sqlText" value="${journal_expert_sql}"> 
    <input type='hidden' name="sqlInfo" id="sqlInfo" value="Период с ${param.dateBegin} по ${param.dateEnd}.">
    <input type='hidden' name="sqlColumn" id="sqlColumn" value="">
    <input type='hidden' name="s" id="s" value="PrintService">
    <input type='hidden' name="m" id="m" value="printNativeQuery">
    <input type="submit" value="Печать"> 
    </form>
    </msh:sectionTitle>
    <msh:sectionContent>
    <msh:table name="journal_expert"
    viewUrl="entityParentView-contract_pricePosition.do?short=Short" 
     action="entityParentView-contract_pricePosition.do" idField="1" >
         	<msh:tableNotEmpty>
         	<tr>
     			<th></th>
     			<th></th>
     			<th></th>
     			<th></th>
     			<th colspan="2">Приемное отделение куда направило</th>
     			<th colspan="3">Отделение 1</th>
     			<th colspan="3">Отделение 2</th>
     			<th colspan="3">Отделение 3</th>
     			<th></th>
     		</tr>
     	</msh:tableNotEmpty>
      <msh:tableColumn columnName="#" property="sn" />
      <msh:tableColumn columnName="Стат.карта" property="2" />
      <msh:tableColumn columnName="ФИО пациента" property="3" />
      <msh:tableColumn columnName="Наименование" property="4" />
      <msh:tableColumn columnName="Дата поступления" property="5" />
      <msh:tableColumn columnName="Наименование" property="6" />
      <msh:tableColumn columnName="Дата поступления" property="7" />
      <msh:tableColumn columnName="Койко-дни" property="12" />
      <msh:tableColumn columnName="Наименование" property="8" />
      <msh:tableColumn columnName="Дата поступления" property="9" />
      <msh:tableColumn columnName="Койко-дни" property="13" />
      <msh:tableColumn columnName="Наименование" property="10" />
      <msh:tableColumn columnName="Дата поступления" property="11" />
      <msh:tableColumn columnName="Койко-дни" property="14" />
      </msh:table>
    </msh:sectionContent>
    </msh:section>
    <%
    		} else if (typeReport!=null &&typeReport.equals("2")) {
    			%>
    		    
    		    <msh:section title="Свод за период ${param.dateBegin}-${param.dateEnd} ${emergencyInfo}  ${param.sloFirst}">
    		    <ecom:webQuery isReportBase="${isReportBase}" nameFldSql="journal_expert_sql" name="journal_expert" nativeSql="
    		select '&sloFirst='||case when slo.prevMedcase_id is null then '' else 'Перевод' end||'&department='||ml.id||'&department1='||coalesce(ml1.id,-1)||'&department2='||coalesce(ml2.id,-1)||'&cntDays='||case
            when (coalesce(slo.dateFinish,slo.transferDate,CURRENT_DATE)-slo.dateStart)=0 then 1
            when bf.addCaseDuration='1' then ((coalesce(slo.dateFinish,slo.transferDate,CURRENT_DATE)-slo.dateStart)+1)
            else (coalesce(slo.dateFinish,slo.transferDate,CURRENT_DATE)-slo.dateStart)
          end,case when slo.prevMedcase_id is null then '' else 'Перевод' end as mlSls
    		,ml.name as mlname
    		,ml1.name as mlSlo1
    		,ml2.name as mlSlo2
    		,case
            when (coalesce(slo.dateFinish,slo.transferDate,CURRENT_DATE)-slo.dateStart)=0 then 1
            when bf.addCaseDuration='1' then ((coalesce(slo.dateFinish,slo.transferDate,CURRENT_DATE)-slo.dateStart)+1)
            else (coalesce(slo.dateFinish,slo.transferDate,CURRENT_DATE)-slo.dateStart)
          end as beds
    		,count(slo.id) as slocnt
    		from medcase slo
    		left join BedFund bf on bf.id=slo.bedfund_id
    		left join MedCase sls on sls.id=slo.parent_id
    		left join Patient pat on pat.id=sls.patient_id
    		left join StatisticStub ss on ss.id=sls.statisticStub_id
    		left join medcase slo1 on slo1.prevMedCase_id=slo.id
    		left join medcase slo2 on slo2.prevMedCase_id=slo1.id
    		left join mislpu mlSls on mlSls.id=sls.department_id
    		left join mislpu ml on ml.id=slo.department_id
    		left join mislpu ml1 on ml1.id=slo1.department_id
    		left join mislpu ml2 on ml2.id=slo2.department_id

    		where 
    		slo.dateStart between to_date('${param.dateBegin}','dd.mm.yyyy') and to_date('${param.dateEnd}','dd.mm.yyyy')
    		${departmentSql} ${department1Sql} ${department2Sql}
    		and slo.dtype='DepartmentMedCase' and slo1.dtype='DepartmentMedCase'
    		and (slo2.dtype='DepartmentMedCase' or slo2.dtype is null)
    		 ${viewSql} 
    		${filterByCodeSql} ${filterByNameSql}
    		 ${sloPrevSql} ${daysSql}
    		group by ml.id,ml1.id,ml2.id
    		,ml.name, ml1.name, ml2.name ,case when slo.prevMedcase_id is null then '' else 'Перевод' end
    		,case
            when (coalesce(slo.dateFinish,slo.transferDate,CURRENT_DATE)-slo.dateStart)=0 then 1
            when bf.addCaseDuration='1' then ((coalesce(slo.dateFinish,slo.transferDate,CURRENT_DATE)-slo.dateStart)+1)
            else (coalesce(slo.dateFinish,slo.transferDate,CURRENT_DATE)-slo.dateStart)
          end
    		order by ml.name
    		    " />
    		    <msh:sectionTitle>
    		    
    		    <form action="print-stac_journal_transfer_department.do" method="post" target="_blank">
    		    Реестр услуг.
    		    <input type='hidden' name="sqlText" id="sqlText" value="${journal_expert_sql}"> 
    		    <input type='hidden' name="sqlInfo" id="sqlInfo" value="Период с ${param.dateBegin} по ${param.dateEnd}.">
    		    <input type='hidden' name="sqlColumn" id="sqlColumn" value="">
    		    <input type='hidden' name="s" id="s" value="PrintService">
    		    <input type='hidden' name="m" id="m" value="printNativeQuery">
    		    <input type="submit" value="Печать"> 
    		    </form>
    		    </msh:sectionTitle>
    		    <msh:sectionContent>
    		    <msh:table name="journal_expert" action=" javascript:void(0)"
    		    viewUrl="stac_journal_transfer_department.do?typeReport=1&typeView=${param.typeView}&short=Short&dateBegin=${param.dateBegin}&dateEnd=${param.dateEnd}"
    		     idField="1" >
    		      <msh:tableColumn columnName="#" property="sn" />
    		      <msh:tableColumn columnName="Тип пост." property="2" />
    		      <msh:tableColumn columnName="Отделение 1" property="3" />
   		          <msh:tableColumn columnName="Кол-во случаев" property="7" />
      		      
    		      <msh:tableColumn columnName="Отделение 2" property="4" />
    		      <msh:tableColumn columnName="Отделение 3" property="5" />
    		      <msh:tableColumn columnName="Койко-дней" property="6" />
    		      </msh:table>
    		    </msh:sectionContent>
    		    </msh:section>
    		    <%    			
    		}
    		
    		} else { 
    			if (typeReport!=null && typeReport.equals("1")) {
    		
    		%>
    <msh:section title="Реестр за период ${param.dateBegin}-${param.dateEnd} ${emergencyInfo}">
    <ecom:webQuery isReportBase="${isReportBase}" nameFldSql="journal_expert_sql" name="journal_expert" nativeSql="
select sls.id,pat.lastname||' '||pat.firstname||' '||pat.middlename as fio,ss.code,mlSls.name as mlSls
,to_char(sls.dateStart,'dd.mm.yyyy')||' '||cast(sls.entranceTime as varchar(5)) as dateStartSls
,ml.name as mlname
,to_char(slo.dateStart,'dd.mm.yyyy')||' '||cast(slo.entranceTime as varchar(5)) as dateStartSlo
,ml1.name as mlSlo1,to_char(slo1.dateStart,'dd.mm.yyyy')||' '||cast(slo1.entranceTime as varchar(5)) as dateStartSlo1
,ml2.name as mlSlo2,to_char(slo2.dateStart,'dd.mm.yyyy')||' '||cast(slo2.entranceTime as varchar(5)) as dateStartSlo2
from medcase slo
left join BedFund bf on bf.id=slo.bedFund_id
left join MedCase sls on sls.id=slo.parent_id
left join Patient pat on pat.id=sls.patient_id
left join StatisticStub ss on ss.id=sls.statisticStub_id
left join medcase slo1 on slo1.prevMedCase_id=slo.id
left join medcase slo2 on slo2.prevMedCase_id=slo1.id
left join mislpu mlSls on mlSls.id=sls.department_id
left join mislpu ml on ml.id=slo.department_id
left join mislpu ml1 on ml1.id=slo1.department_id
left join mislpu ml2 on ml2.id=slo2.department_id

where 
slo.dateStart between to_date('${param.dateBegin}','dd.mm.yyyy') and to_date('${param.dateEnd}','dd.mm.yyyy')
${departmentSql} ${department1Sql} ${department2Sql}
and slo.dtype='DepartmentMedCase' and slo1.dtype='DepartmentMedCase'
and (slo2.dtype='DepartmentMedCase' or slo2.dtype is null)
 ${viewSql} 
${filterByCodeSql} ${filterByNameSql}
 ${sloPrevSql} ${daysSql}
and slo.prevMedCase_id is null
order by pat.lastname
    " />
    <msh:sectionTitle>
    
    <form action="print-contract_analisis_PMservices.do" method="post" target="_blank">
    Реестр услуг.
    <input type='hidden' name="sqlText" id="sqlText" value="${journal_expert_sql}"> 
    <input type='hidden' name="sqlInfo" id="sqlInfo" value="Период с ${param.dateBegin} по ${param.dateEnd}.">
    <input type='hidden' name="sqlColumn" id="sqlColumn" value="">
    <input type='hidden' name="s" id="s" value="PrintService">
    <input type='hidden' name="m" id="m" value="printNativeQuery">
    <input type="submit" value="Печать"> 
    </form>
    </msh:sectionTitle>
    <msh:sectionContent>
    <msh:table name="journal_expert"
    viewUrl="entityParentView-stac_sslAllInfo.do?short=Short" 
     action="entityParentView-stac_sslAllInfo.do" idField="1" >
          	<msh:tableNotEmpty>
          	<tr>
     			<th></th>
     			<th></th>
     			<th></th>
     			<th></th>
     			<th colspan="2">Приемное отделение куда направило</th>
     			<th colspan="2">Отделение 1</th>
     			<th colspan="2">Отделение 2</th>
     			<th colspan="2">Отделение 3</th>
     		</tr>
     	</msh:tableNotEmpty>
      <msh:tableColumn columnName="#" property="sn" />
      <msh:tableColumn columnName="Стат.карта" property="2" />
      <msh:tableColumn columnName="ФИО пациента" property="3" />
      <msh:tableColumn columnName="Наименование" property="4" />
      <msh:tableColumn columnName="Дата поступления" property="5" />
      <msh:tableColumn columnName="Наименование" property="6" />
      <msh:tableColumn columnName="Дата поступления" property="7" />
      <msh:tableColumn columnName="Наименование" property="8" />
      <msh:tableColumn columnName="Дата поступления" property="9" />
      <msh:tableColumn columnName="Наименование" property="10" />
      <msh:tableColumn columnName="Дата поступления" property="11" />
    </msh:table>
    </msh:sectionContent>
    </msh:section>
    <% 
    } else if (typeReport!=null && typeReport.equals("2")) {
    	
		%>
<msh:section title="Реестр за период ${param.dateBegin}-${param.dateEnd} ${emergencyInfo}">
<ecom:webQuery isReportBase="${isReportBase}" nameFldSql="journal_expert_sql" name="journal_expert" nativeSql="
select '&sloFirst='||case when slo.prevMedcase_id is null then '' else 'Перевод' end||'&department='||ml.id||'&department1='||coalesce(ml1.id,-2)||'&department2='||coalesce(ml2.id,-1)||'&cntDays='||case
            when (coalesce(slo.dateFinish,slo.transferDate,CURRENT_DATE)-slo.dateStart)=0 then 1
            when bf.addCaseDuration='1' then ((coalesce(slo.dateFinish,slo.transferDate,CURRENT_DATE)-slo.dateStart)+1)
            else (coalesce(slo.dateFinish,slo.transferDate,CURRENT_DATE)-slo.dateStart)
          end as id,case when slo.prevMedcase_id is null then '' else 'Перевод' end as mlSls
,ml.name as mlname
,ml1.name as mlSlo1
,ml2.name as mlSlo2
,case
            when (coalesce(slo.dateFinish,slo.transferDate,CURRENT_DATE)-slo.dateStart)=0 then 1
            when bf.addCaseDuration='1' then ((coalesce(slo.dateFinish,slo.transferDate,CURRENT_DATE)-slo.dateStart)+1)
            else (coalesce(slo.dateFinish,slo.transferDate,CURRENT_DATE)-slo.dateStart)
          end as days
,count(slo.id) as cntSlo
from medcase slo
left join bedfund bf on slo.bedFund_id=bf.id
left join MedCase sls on sls.id=slo.parent_id
left join Patient pat on pat.id=sls.patient_id
left join StatisticStub ss on ss.id=sls.statisticStub_id
left join medcase slo1 on slo1.prevMedCase_id=slo.id
left join medcase slo2 on slo2.prevMedCase_id=slo1.id
left join mislpu mlSls on mlSls.id=sls.department_id
left join mislpu ml on ml.id=slo.department_id
left join mislpu ml1 on ml1.id=slo1.department_id
left join mislpu ml2 on ml2.id=slo2.department_id

where 
slo.dateStart between to_date('${param.dateBegin}','dd.mm.yyyy') and to_date('${param.dateEnd}','dd.mm.yyyy')
${departmentSql} ${department1Sql} ${department2Sql}
and slo.dtype='DepartmentMedCase' and slo1.dtype='DepartmentMedCase'
and (slo2.dtype='DepartmentMedCase' or slo2.dtype is null)
${viewSql} 
${filterByCodeSql} ${filterByNameSql}
and slo.prevMedCase_id is null
group by ml.id,ml1.id,ml2.id
,ml.name ,ml1.name ,ml2.name ,case when slo.prevMedcase_id is null then '' else 'Перевод' end
,case
            when (coalesce(slo.dateFinish,slo.transferDate,CURRENT_DATE)-slo.dateStart)=0 then 1
            when bf.addCaseDuration='1' then ((coalesce(slo.dateFinish,slo.transferDate,CURRENT_DATE)-slo.dateStart)+1)
            else (coalesce(slo.dateFinish,slo.transferDate,CURRENT_DATE)-slo.dateStart)
          end
order by ml.name
" />
<msh:sectionTitle>

<form action="print-.do" method="post" target="_blank">
Свод.
<input type='hidden' name="sqlText" id="sqlText" value="${journal_expert_sql}"> 
<input type='hidden' name="sqlInfo" id="sqlInfo" value="Период с ${param.dateBegin} по ${param.dateEnd}.">
<input type='hidden' name="sqlColumn" id="sqlColumn" value="">
<input type='hidden' name="s" id="s" value="PrintService">
<input type='hidden' name="m" id="m" value="printNativeQuery">
<input type="submit" value="Печать"> 
</form>
</msh:sectionTitle>
<msh:sectionContent>
<msh:table name="journal_expert"
action=" javascript:void(0)" 
viewUrl="stac_journal_transfer_department.do?typeReport=1&typeView=${param.typeView}&short=Short&dateBegin=${param.dateBegin}&dateEnd=${param.dateEnd}"
    		    
idField="1" >
      	
  <msh:tableColumn columnName="#" property="sn" />
  <msh:tableColumn columnName="Тип пост." property="2" />
  <msh:tableColumn columnName="Отделение 1" property="3" />
  <msh:tableColumn columnName="Кол-во случаев" property="7" />
  
  <msh:tableColumn columnName="Отделение 2" property="4" />
  <msh:tableColumn columnName="Отделение 3" property="5" />
  <msh:tableColumn columnName="Койко-дней" property="6" />
</msh:table>
</msh:sectionContent>
</msh:section>
<%     
    
    
    }
    			
    		}
    } else {%>
    	<i>Нет данных </i>
    	<% 
    	}%>
    
  </tiles:put>
</tiles:insert>