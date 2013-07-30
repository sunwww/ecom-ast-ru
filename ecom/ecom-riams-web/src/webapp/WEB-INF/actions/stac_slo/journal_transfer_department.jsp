<%@page import="ru.ecom.mis.web.action.util.ActionUtil"%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://struts.apache.org/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://www.nuzmsh.ru/tags/msh" prefix="msh" %>
<%@ taglib uri="http://www.ecom-ast.ru/tags/ecom" prefix="ecom" %>
<%@ taglib tagdir="/WEB-INF/tags" prefix="tags" %>

<tiles:insert page="/WEB-INF/tiles/mainLayout.jsp" flush="true" >

  <tiles:put name="title" type="string">
    <msh:title guid="helloItle-123" mainMenu="Contract" title="Анализ услуг прейскуранта"/>
  </tiles:put>
  <tiles:put name="side" type="string">
  	
    	<tags:contractMenu currentAction="price"/>
  </tiles:put>
  <tiles:put name="body" type="string">
  <%
    String typeView =ActionUtil.updateParameter("Stac_journal_transfer","typeView","1", request) ;
  %>
  
    <msh:form action="/stac_journal_transfer_department.do" defaultField="departmentName" disableFormDataConfirm="true" method="GET">
    <msh:panel>
      <msh:row>
        <msh:separator label="Параметры поиска" colSpan="7"/>
      </msh:row>

        <msh:row>
	        <td class="label" title="Просмотр данных (typeView)" colspan="1"><label for="typeViewName" id="typeViewLabel">Отобразить:</label></td>
	        <td onclick="this.childNodes[1].checked='checked';"  colspan="2">
	        	<input type="radio" name="typeView" value="1">  реестр переводов
	        </td>
	        <td onclick="this.childNodes[1].checked='checked';"  colspan="2">
	        	<input type="radio" name="typeView" value="2">  реестр поступивших в отделение 1
	        </td>
        </msh:row>
        <msh:row>
        	<msh:textField property="dateBegin" label="Дата начала"/>
        	<msh:textField property="dateEnd" label="Дата окончания"/>
        </msh:row>
        <msh:row>
        	<msh:autoComplete property="department" fieldColSpan="7" horizontalFill="true" label="Отделение 1" vocName="lpu"/>
        </msh:row>
        <msh:row>
        	<msh:autoComplete property="department1" fieldColSpan="7" horizontalFill="true" label="Отделение 2" vocName="lpu"/>
        </msh:row>
        <msh:row>
        	<msh:autoComplete property="department2" fieldColSpan="7" horizontalFill="true" label="Отделение 3" vocName="lpu"/>
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
    	frm.action='stac_journal_transfer_department.do' ;
    }
    function print() {

    	
    }
    </script>

    
    <%
    String department = (String)request.getParameter("department") ;
    String date = (String)request.getParameter("dateBegin") ;
    String date1 = (String)request.getParameter("dateEnd") ;
    
    if (department!=null && !department.equals("") && !department.equals("0") 
    		&& date!=null && !date.equals("") )  {
    	/*
    	if (typeFindMed!=null && typeFindMed.equals("1")) {
    		request.setAttribute("findMedSql", " pp.code=ms.code") ;
    		request.setAttribute("findMedAddSql", " and ms.id is not null") ;
    	} else if (typeFindMed!=null && typeFindMed.equals("2")) {
    		request.setAttribute("findMedSql", " trim(upper(ms.name))=trim(upper(pp.name))") ;
    		request.setAttribute("findMedAddSql", " and ms.id is not null") ;
    	} else if (typeFindMed!=null && typeFindMed.equals("3")) {
    		request.setAttribute("findMedSql", " pp.code=ms.code and trim(upper(ms.name))=trim(upper(pp.name))") ;
    		request.setAttribute("findMedAddSql", " and ms.id is not null") ;
    	} else if (typeFindMed!=null && typeFindMed.equals("4")) {
    		request.setAttribute("findMedSql", " (pp.code=ms.code or trim(upper(ms.name))=trim(upper(pp.name)))") ;
    		request.setAttribute("findMedAddSql", " and ms.id is null") ;
    	} else if (typeFindMed!=null && typeFindMed.equals("5")) {
    		request.setAttribute("findMedSql", "") ;
    	} 
    	if (typeView!=null && typeView.equals("1")) {
    		request.setAttribute("viewSql", " and pms.id is not null and ms1.id is not null") ;
    	} else if (typeView!=null && typeView.equals("2")) {
    		request.setAttribute("viewSql", " and ms1.id is null") ;
    	} else if (typeView!=null && typeView.equals("3")) {
    		request.setAttribute("viewSql", "") ;
    	} 
    	*/
    	ActionUtil.setParameterFilterSql("department","slo.department_id", request) ;
    	ActionUtil.setParameterFilterSql("department1","slo1.department_id", request) ;
    	ActionUtil.setParameterFilterSql("department2","slo2.department_id", request) ;
    	if (typeView!=null && typeView.equals("1")) {
    	%>
    
    <msh:section title="Реестр за период ${param.dateBegin}-${param.dateEnd} ${emergencyInfo}">
    <ecom:webQuery nameFldSql="journal_expert_sql" name="journal_expert" nativeSql="
select sls.id,pat.lastname||' '||pat.firstname||' '||pat.middlename as fio,ss.code,mlSls.name as mlSls
,to_char(sls.dateStart,'dd.mm.yyyy')||' '||cast(sls.entranceTime as varchar(5)) as dateStartSls
,ml.name as mlname
,to_char(slo.dateStart,'dd.mm.yyyy')||' '||cast(slo.entranceTime as varchar(5)) as dateStartSlo
,ml1.name as mlSlo1,to_char(slo1.dateStart,'dd.mm.yyyy')||' '||cast(slo1.entranceTime as varchar(5)) as dateStartSlo1
,ml2.name as mlSlo2,to_char(slo2.dateStart,'dd.mm.yyyy')||' '||cast(slo2.entranceTime as varchar(5)) as dateStartSlo2
from medcase slo
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
order by pat.lastname
    " guid="4a720225-8d94-4b47-bef3-4dbbe79eec74" />
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
    <%} else { %>
    <msh:section title="Реестр за период ${param.dateBegin}-${param.dateEnd} ${emergencyInfo}">
    <ecom:webQuery nameFldSql="journal_expert_sql" name="journal_expert" nativeSql="
select sls.id,pat.lastname||' '||pat.firstname||' '||pat.middlename as fio,ss.code,mlSls.name as mlSls
,to_char(sls.dateStart,'dd.mm.yyyy')||' '||cast(sls.entranceTime as varchar(5)) as dateStartSls
,ml.name as mlname
,to_char(slo.dateStart,'dd.mm.yyyy')||' '||cast(slo.entranceTime as varchar(5)) as dateStartSlo
,ml1.name as mlSlo1,to_char(slo1.dateStart,'dd.mm.yyyy')||' '||cast(slo1.entranceTime as varchar(5)) as dateStartSlo1
,ml2.name as mlSlo2,to_char(slo2.dateStart,'dd.mm.yyyy')||' '||cast(slo2.entranceTime as varchar(5)) as dateStartSlo2
from medcase slo
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
order by pat.lastname
    " guid="4a720225-8d94-4b47-bef3-4dbbe79eec74" />
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
    }
    } else {%>
    	<i>Нет данных </i>
    	<% 
    	}%>
    
  </tiles:put>
</tiles:insert>