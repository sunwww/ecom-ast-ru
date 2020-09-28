<%@page import="java.text.SimpleDateFormat"%>
<%@page import="ru.ecom.web.util.ActionUtil"%>
<%@page import="ru.nuzmsh.web.tags.helper.RolesHelper"%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://struts.apache.org/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://www.nuzmsh.ru/tags/msh" prefix="msh" %>
<%@ taglib uri="http://www.ecom-ast.ru/tags/ecom" prefix="ecom" %>
<%@ taglib tagdir="/WEB-INF/tags" prefix="tags" %>

<tiles:insert page="/WEB-INF/tiles/main${param.short}Layout.jsp" flush="true" >

    <tiles:put name='title' type='string'>
        <msh:title mainMenu="Poly">Просмотр данных по болезням кровообращения </msh:title>
    </tiles:put>

    <tiles:put name='side' type='string'>
        <tags:visit_finds currentAction="blood_report"/>
    </tiles:put>
    
  <tiles:put name="body" type="string">
  <%
	//String typeDate =request.getParameter("typeDate") ;
  String typeDate=ActionUtil.updateParameter("BloodReport","typeDate","1", request) ;
  String typeDiagnosis=ActionUtil.updateParameter("BloodReport","typeDiagnosis","1", request) ;
  String typeDiagnosisType=ActionUtil.updateParameter("BloodReport","typeDiagnosisType","1", request) ;
  %>
    <msh:form action="/blood_report.do" defaultField="dateBegin" disableFormDataConfirm="true" method="GET">
    <input type="hidden" name="id" id="id"/>
    <input type="hidden" name="ticketIs" id="ticketIs" value="0"/>
    <input type="hidden" name="typeReestr" id="typeReestr" value="2"/>
    <input type="hidden" name="person" id="person" value="${param.person}"/>
    <%if (request.getParameter("short")==null ||request.getParameter("short").equals(""))  {%>
    <msh:panel>
      <msh:row>
        <msh:separator label="Параметры поиска" colSpan="9" />
      </msh:row>
      <msh:row>
        	<msh:textField property="dateBegin" label="Период с" />
        	<msh:textField property="dateEnd" label="по" />
        </msh:row>
            <msh:row>
	        <td class="label" title="Отображать" colspan="1">
	        <label for="typeDiagnosisName" id="typeDiagnosisLabel">Отобразить:</label></td>
	        <td onclick="this.childNodes[1].checked='checked';">
	        	<input type="radio" name="typeDate" value="1"> Дата начала госпитализации
	        </td>
	        <td onclick="this.childNodes[1].checked='checked';">
	        	<input type="radio" name="typeDate" value="2" >  Дата окончания госпитализации
	        </td>
        </msh:row> 
        <msh:row>
	        <td class="label" title="Тип диагноза" colspan="1">
	        <label for="typeDiagnosisName" id="typeDiagnosisLabel">Отобразить:</label></td>
	        <td onclick="this.childNodes[1].checked='checked';">
	        	<input type="radio" name="typeDiagnosis" value="1"> Острые нарушения кровообращения (I60-I64)
	        </td>
	        <td onclick="this.childNodes[1].checked='checked';">
	        	<input type="radio" name="typeDiagnosis" value="2" >  Нестабильная стенокардия (I20.0)
	        </td>
	        <td onclick="this.childNodes[1].checked='checked';">
	        	<input type="radio" name="typeDiagnosis" value="3">  Острый инфаркт миокарда (I21-I23)
	        </td>
        </msh:row> 
        <msh:row>
	        <td class="label" title="Тип диагноза" colspan="1">
	        <label for="typeDiagnosisTypeName" id="typeDiagnosisTypeLabel">Отобразить:</label></td>
	        <td onclick="this.childNodes[1].checked='checked';">
	        	<input type="radio" name="typeDiagnosisType" value="1"> Клинический
	        </td>
	        <td onclick="this.childNodes[1].checked='checked';">
	        	<input type="radio" name="typeDiagnosisType" value="2" >  Выписной
	        </td>
	      </msh:row> 
        
        <msh:row> 
        	<msh:submitCancelButtonsRow labelSave="Сформировать" doNotDisableButtons="cancel" labelSaving="Формирование..." colSpan="4"/>
        
        </msh:row>

    </msh:panel>
    <%} %>
    </msh:form>
    
    <%
    	String date = request.getParameter("dateBegin");
    	String dateEnd = request.getParameter("dateEnd");
    	if (date!=null  
    			&& !date.equals("") 
    			&& dateEnd!=null
    			&& !dateEnd.equals("")) {
    		request.setAttribute("dateBegin", date);
    		request.setAttribute("dateEnd", dateEnd);
    		request.setAttribute("isReportBase", ActionUtil.isReportBase(request.getParameter("dateBegin"),request.getParameter("dateEnd"),request));
    		
    		StringBuilder sqlAdd = new StringBuilder();
    		if (typeDate.equals("1")) {
    			request.setAttribute("dateSql", "sls.dateStart") ;
    		} else {
    			request.setAttribute("dateSql","sls.dateFinish" ) ;
    		}
    		if (typeDiagnosisType!=null&&typeDiagnosisType.equals("1")) {
    			sqlAdd.append(" and vdrt.code='4'"); 
    		} else {
    			sqlAdd.append(" and vdrt.code='3'"); 
    		}
    		if (typeDiagnosis!=null && (typeDiagnosis.equals("1"))) {    			
    			 sqlAdd.append(" and (mkb.code between 'I60' and 'I64.999') ");
    		} else if (typeDiagnosis!=null && (typeDiagnosis.equals("2"))) {
    			sqlAdd.append(" and mkb.code='I20.0' ");    			
    		} else if (typeDiagnosis!=null && (typeDiagnosis.equals("3"))) {
    			sqlAdd.append(" and (mkb.code between 'I21' and 'I23.999') ");
    		}
    		
    		request.setAttribute ("appendSQL", sqlAdd.toString());
    	%>
    
    <msh:section>
${isReportBase}<ecom:webQuery isReportBase="${isReportBase}" maxResult="1500" name="journal_ticket" nameFldSql="journal_ticket_sql" nativeSql="
select sls.id
,p.lastname||' '||p.firstname||' '||p.middlename as f1
,max(coalesce(adr.fullname)||' ' || case when p.houseNumber is not null and p.houseNumber!='' then ' д.'||p.houseNumber else '' end 
	||case when p.houseBuilding is not null and p.houseBuilding!='' then ' корп.'|| p.houseBuilding else '' end  	
	||case when p.flatNumber is not null and p.flatNumber!='' then ' кв. '|| p.flatNumber else '' end) as address
,to_char(p.birthday,'dd.mm.yyyy') as f3
,cast(to_char(sls.dateStart,'yyyy') as int)-cast(to_char(p.birthday,'yyyy') as int) 
	+(case when (cast(to_char(sls.dateStart, 'mm') as int)-cast(to_char(p.birthday, 'mm') as int) 
	+(case when (cast(to_char(sls.dateStart,'dd') as int) - cast(to_char(p.birthday,'dd') as int)<0) then -1 else 0 end)<0) then -1 else 0 end) 
	as f4_Age
,max(case when ot.voc_code='К' then to_char(sls.datestart,'dd.mm.yyyy') else null end) as f5_SMP
,max(case when (ot.id isnull or ot.voc_code!='К') then to_char(sls.datestart,'dd.mm.yyyy') else null end ) as f6_notSMP
,to_char(sls.datefinish,'dd.mm.yyyy') as f7_dateFinish
,LIST(mkb.code)
,case when sls.datefinish is null then (select list(dep.name) from medcase slo left join
	mislpu dep on dep.id=slo.department_id where slo.dtype='DepartmentMedCase' and slo.parent_id=sls.id and slo.transferdate is null) else '' end as f9_dep 
,case when sls.datefinish is null then '+' else '-' end as f10_inHospital
,max(case when vhr.code='11' then to_char(sls.datefinish,'dd.mm.yyyy') else null end) as f11_isDead
,max(vr.name) as f12_rayon
,max(case when ms.code='A06.10.006' or ms.code='A06.10.006.1' then '+' else '-' end) as f14_coronaro
,max(case when ms.code='A16.12.028.017' or ms.code='A16.12.004.009' or ms.code='A16.12.004.008' then '+' else '-' end) as f15_stentirovanie 
from medcase sls 
left join omc_frm  ot on ot.id=sls.ordertype_id
left join medcase slo on slo.parent_id=sls.id
left join bedfund bf on bf.id=slo.bedfund_id
left join vocbedsubtype vbst on vbst.id=bf.bedsubtype_id
left join diagnosis diag on diag.medcase_id=slo.id or diag.medcase_id=sls.id
left join vocprioritydiagnosis vpd on vpd.id=diag.priority_id
left join vocdiagnosisregistrationtype vdrt on vdrt.id=diag.registrationtype_id
left join vocidc10 mkb on mkb.id=diag.idc10_id
left join patient p on p.id=sls.patient_id
left join address2 adr on adr.addressid = p.address_addressid
left join vochospitalizationresult vhr on vhr.id=sls.result_id
left join vocrayon vr on vr.id=p.rayon_id
left join surgicaloperation sop on sop.medcase_id=slo.id
left join medservice ms on ms.id=sop.medservice_id
where sls.deniedhospitalizating_id is null and  sls.dtype='HospitalMedCase'
and ${dateSql} between to_date('${dateBegin}','dd.MM.yyyy') and to_date('${dateEnd}','dd.MM.yyyy')
and vpd.code='1'
and vbst.code='1'
 ${appendSQL} 
group by sls.id, p.lastname, p.firstname, p.middlename, p.birthday, sls.datestart, sls.datefinish 
order by p.lastname, p.firstname, p.middlename 
" />
    <msh:sectionTitle>
        <form action="print-blReport.do" method="post" target="_blank">
    Период с ${dateBegin} по ${dateEnd}. 
    <input type='hidden' name="sqlText" id="sqlText" value="${journal_ticket_sql}"> 
    <input type='hidden' name="sqlInfo" id="sqlInfo" value="Период с ${dateBegin} по ${dateEnd}.">
    <input type='hidden' name="s" id="s" value="PrintService"><input type='hidden' name="isReportBase" id="isReportBase" value="${isReportBase}">
    <input type='hidden' name="m" id="m" value="printNativeQuery">
    <input type='hidden' name="date1" id="date1" value="${dateBegin}">
    <input type='hidden' name="date2" id="date2" value="${dateEnd}">
    <input type="submit" value="Печать"> 
    </form>
    </msh:sectionTitle>

    <msh:sectionContent>
        <msh:table printToExcelButton="Сохранить в excel"
         name="journal_ticket" action="entitySubclassView-mis_medCase.do" idField="1" noDataMessage="Не найдено">
            <msh:tableColumn columnName="ФИО пациента" property="2"/>            
            <msh:tableColumn columnName="Адрес проживания пациента" property="3"/>
            <msh:tableColumn columnName="Район" property="13"/>
            <msh:tableColumn columnName="Дата рождения" property="4"/>
            <msh:tableColumn columnName="Возраст" property="5"/>
            <msh:tableColumn columnName="Доставлен машиной СМП" property="6"/>
            <msh:tableColumn columnName="Самостоятельно" property="7"/>
            <msh:tableColumn columnName="Дата выписки" property="8"/>
            <msh:tableColumn columnName="Диагноз (Код МКБ)" property="9"/>
            <msh:tableColumn columnName="Отделение стационара" property="10"/>
            <msh:tableColumn columnName="Продолжает лечиться" property="11"/>
            <msh:tableColumn columnName="Умер?" property="12"/>
            <msh:tableColumn columnName="Проводилась коронография" property="14"/>
            <msh:tableColumn columnName="Проводилось стентирование" property="15"/>
        </msh:table>
    </msh:sectionContent>

    </msh:section>
    <% }  else {%>
    	<i>Выберите параметры поиска и нажмите "Найти" </i>
    	<% }   %>
  </tiles:put>
  <tiles:put name="javascript" type="string">
  	<script type="text/javascript">

    checkFieldUpdate('typeDate','${typeDate}',1) ;
    checkFieldUpdate('typeDiagnosis','${typeDiagnosis}',1) ;
    checkFieldUpdate('typeDiagnosisType','${typeDiagnosisType}',1) ;
    
    function checkFieldUpdate(aField,aValue,aDefault) {
    	
    	eval('var chk =  document.forms[0].'+aField) ;
    	var max = chk.length ;
    	if ((+aValue)>max) {
    		chk[+aDefault-1].checked='checked' ;
    	} else {
    		chk[+aValue-1].checked='checked' ;
    	}
    }
  		
  	</script>
  </tiles:put>

</tiles:insert>