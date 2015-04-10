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
  %>
    <msh:form action="/blood_report.do" defaultField="dateBegin" disableFormDataConfirm="true" method="GET" guid="d7b31bc2-38f0-42cc-8d6d-19395273168f">
    <input type="hidden" name="id" id="id"/>
    <input type="hidden" name="ticketIs" id="ticketIs" value="0"/>
    <input type="hidden" name="typeReestr" id="typeReestr" value="2"/>
    <input type="hidden" name="person" id="person" value="${param.person}"/>
    <%if (request.getParameter("short")==null ||request.getParameter("short").equals(""))  {%>
    <msh:panel>
      <msh:row guid="53627d05-8914-48a0-b2ec-792eba5b07d9">
        <msh:separator label="Параметры поиска" colSpan="9" guid="15c6c628-8aab-4c82-b3d8-ac77b7b3f700" />
      </msh:row>
      <msh:row>
        	<msh:textField property="dateBegin" label="Период с" guid="8d7ef035-1273-4839-a4d8-1551c623caf1" />
        	<msh:textField property="dateEnd" label="по" guid="f54568f6-b5b8-4d48-a045-ba7b9f875245" />
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
    		SimpleDateFormat format = new SimpleDateFormat("dd.MM.yyyy") ;		
    		if (typeDate.equals("1")) {
    			request.setAttribute("dateSql", "sls.dateStart") ;
    		} else {
    			request.setAttribute("dateSql","sls.dateFinish" ) ;
    		}
    		if (typeDiagnosis!=null && (typeDiagnosis.equals("1"))) {    			
    			 sqlAdd.append(" (mkb.code between 'I60' and 'I64') ");
    		} else if (typeDiagnosis!=null && (typeDiagnosis.equals("2"))) {
    			sqlAdd.append(" mkb.code='I20.2' ");    			
    		} else if (typeDiagnosis!=null && (typeDiagnosis.equals("3"))) {
    			sqlAdd.append(" (mkb.code between 'I21' and 'I23') ");
    		}
    		
    		request.setAttribute ("appendSQL", sqlAdd.toString());
    	%>
    
    <msh:section>
${isReportBase}<ecom:webQuery isReportBase="${isReportBase}" maxResult="1500" name="journal_ticket" nameFldSql="journal_ticket_sql" nativeSql="
select sls.id
,p.lastname||' '||p.firstname||' '||p.middlename as f1
,coalesce(adr.fullname)||' ' || case when p.houseNumber is not null and p.houseNumber!='' then ' д.'||p.houseNumber else '' end 
	||case when p.houseBuilding is not null and p.houseBuilding!='' then ' корп.'|| p.houseBuilding else '' end  	
	||case when p.flatNumber is not null and p.flatNumber!='' then ' кв. '|| p.flatNumber else '' end as address
,to_char(p.birthday,'dd.mm.yyyy') as f3
,cast(to_char(sls.dateStart,'yyyy') as int)-cast(to_char(p.birthday,'yyyy') as int) 
	+(case when (cast(to_char(sls.dateStart, 'mm') as int)-cast(to_char(p.birthday, 'mm') as int) 
	+(case when (cast(to_char(sls.dateStart,'dd') as int) - cast(to_char(p.birthday,'dd') as int)<0) then -1 else 0 end)<0) then -1 else 0 end) 
	as f4_Age
,case when ot.voc_code='К' then to_char(sls.datestart,'dd.mm.yyyy') else null end as f5_SMP
,case when (ot.id isnull or ot.voc_code!='К') then to_char(sls.datestart,'dd.mm.yyyy') else null end as f6_notSMP
,to_char(sls.datefinish,'dd.mm.yyyy') as f7_dateFinish
,mkb.code as f8
,case when sls.datefinish is null then (select dep.name from medcase slo left join 
	mislpu dep on dep.id=slo.department_id where slo.dtype='DepartmentMedCase' and slo.parent_id=sls.id and slo.transferdate is null) else '' end as f9_dep 
,case when sls.datefinish is null then '+' else '-' end as f10_inHospital
,case when vhr.code='11' then sls.datefinish else null end as f11_isDead
from medcase sls 
left join omc_frm  ot on ot.id=sls.ordertype_id
left join diagnosis diag on diag.medcase_id=sls.id
left join vocdiagnosisregistrationtype vdrt on vdrt.id=diag.registrationtype_id
left join vocidc10 mkb on mkb.id=diag.idc10_id
left join patient p on p.id=sls.patient_id
left join address2 adr on adr.addressid = p.address_addressid
left join vochospitalizationresult vhr on vhr.id=sls.result_id
where sls.deniedhospitalizating_id is null and  sls.dtype='HospitalMedCase'
and ${dateSql} between to_date('${dateBegin}','dd.MM.yyyy') and to_date('${dateEnd}','dd.MM.yyyy')
and vdrt.code='3'
and ${appendSQL}  " guid="4a720225-8d94-4b47-bef3-4dbbe79eec74" /> 
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
        <msh:table
         name="journal_ticket" action="entitySubclassView-mis_medCase.do" idField="1" noDataMessage="Не найдено">
            <msh:tableColumn columnName="ФИО пациента" property="2"/>            
            <msh:tableColumn columnName="Адрес проживания пациента" property="3"/>
            <msh:tableColumn columnName="Дата рождения" property="4"/>
            <msh:tableColumn columnName="Возраст" property="5"/>
            <msh:tableColumn columnName="Доставлен машиной СМП" property="6"/>
            <msh:tableColumn columnName="Самостоятельно" property="7"/>
            <msh:tableColumn columnName="Дата выписки" property="8"/>
            <msh:tableColumn columnName="Диагноз (Код МКБ)" property="9"/>
            <msh:tableColumn columnName="Отделение стационара" property="10"/>
            <msh:tableColumn columnName="Продолжает лечиться" property="11"/>
            <msh:tableColumn columnName="Умер?" property="12"/>
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