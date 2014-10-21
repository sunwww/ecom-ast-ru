<%@page import="ru.nuzmsh.util.query.ReportParamUtil"%>
<%@page import="ru.ecom.web.util.ActionUtil"%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://struts.apache.org/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://www.nuzmsh.ru/tags/msh" prefix="msh" %>
<%@ taglib uri="http://www.ecom-ast.ru/tags/ecom" prefix="ecom" %>
<%@ taglib tagdir="/WEB-INF/tags" prefix="tags" %>

<tiles:insert page="/WEB-INF/tiles/mainLayout.jsp" flush="true" >

    <tiles:put name='title' type='string'>
        <msh:title mainMenu="StacJournal">Просмотр данных по смертности</msh:title>
    </tiles:put>

    <tiles:put name='side' type='string'>
        <tags:stac_journal currentAction="stac_deathCase_list"/>
    </tiles:put>
    
  <tiles:put name="body" type="string">
  <%
	String typeEmergency =ActionUtil.updateParameter("Report_DEATHCASE","typeEmergency","3", request) ;
	String typeView =ActionUtil.updateParameter("Report_DEATHCASE","typeView","1", request) ;
	String typePatient =ActionUtil.updateParameter("Report_DEATHCASE","typePatient","4", request) ;
	String typeAutopsy =ActionUtil.updateParameter("Report_DEATHCASE","typeAutopsy","3", request) ;
	String typeOperation =ActionUtil.updateParameter("Report_DEATHCASE","typeOperation","3", request) ;
	String typeDifference =ActionUtil.updateParameter("Report_DEATHCASE","typeDifference","3", request) ;
  
  %>
    <msh:form action="/poly_ticketsByNonredidentPatientList.do" defaultField="department" disableFormDataConfirm="true" method="GET" guid="d7b31bc2-38f0-42cc-8d6d-19395273168f">
    <msh:panel guid="6ae283c8-7035-450a-8eb4-6f0f7da8a8ff">
      <msh:row guid="53627d05-8914-48a0-b2ec-792eba5b07d9">
        <msh:separator label="Параметры поиска" colSpan="7" guid="15c6c628-8aab-4c82-b3d8-ac77b7b3f700" />
        <input type='hidden' id="sqlText" name="sqlText">
        <input type='hidden' id="infoText" name="infoText">
        <input type='hidden' id="m" name="m">
        <input type='hidden' id="s" name="s">
      </msh:row>
        <msh:row>
	        <td class="label" title="Поиск по показаниям поступления (typeEmergency)" colspan="1"><label for="typeEmergencyName" id="typeEmergencyLabel">Показания:</label></td>
	        <td onclick="this.childNodes[1].checked='checked';">
	        	<input type="radio" name="typeEmergency" value="1">  экстренные
	        </td>
	        <td onclick="this.childNodes[1].checked='checked';" >
	        	<input type="radio" name="typeEmergency"  value="2">  плановые
	        </td>
	        <td onclick="this.childNodes[1].checked='checked';">
	        	<input type="radio" name="typeEmergency" value="3">  все
	        </td>
        </msh:row>      
        <msh:row>
	        <td class="label" title="Вскрытие (typeAutopsy)" colspan="1"><label for="typeAutopsyName" id="typeAutopsyLabel">Вскрытие:</label></td>
	        <td onclick="this.childNodes[1].checked='checked';">
	        	<input type="radio" name="typeAutopsy" value="1">  производилось
	        </td>
	        <td onclick="this.childNodes[1].checked='checked';" >
	        	<input type="radio" name="typeAutopsy"  value="2">  не было
	        </td>
	        <td onclick="this.childNodes[1].checked='checked';">
	        	<input type="radio" name="typeAutopsy" value="3">  все
	        </td>
        </msh:row>      
        <msh:row>
	        <td class="label" title="Операция (typeOperation)" colspan="1"><label for="typeOperationName" id="typeOperationLabel">Операция:</label></td>
	        <td onclick="this.childNodes[1].checked='checked';">
	        	<input type="radio" name="typeOperation" value="1">  была
	        </td>
	        <td onclick="this.childNodes[1].checked='checked';" >
	        	<input type="radio" name="typeOperation"  value="2">  не было
	        </td>
	        <td onclick="this.childNodes[1].checked='checked';">
	        	<input type="radio" name="typeOperation" value="3">  все
	        </td>
        </msh:row>      
        <msh:row>
	        <td class="label" title="Поиск по рахождениям (typeDifference)" colspan="1"><label for="typeDifferenceName" id="typeDifferenceLabel">Расхождения:</label></td>
	        <td onclick="this.childNodes[1].checked='checked';">
	        	<input type="radio" name="typeDifference" value="1">  были
	        </td>
	        <td onclick="this.childNodes[1].checked='checked';" >
	        	<input type="radio" name="typeDifference"  value="2">  без расхож.
	        </td>
	        <td onclick="this.childNodes[1].checked='checked';">
	        	<input type="radio" name="typeDifference" value="3">  все
	        </td>
        </msh:row>      
        <msh:row>
        	<msh:autoComplete property="department" fieldColSpan="4"
        	label="Отделение" horizontalFill="true" vocName="lpu"/>
        </msh:row>
        <msh:row>
        	<msh:autoComplete property="sex" fieldColSpan="4" label="Пол" horizontalFill="true" vocName="vocSex"/>
        </msh:row>
        <msh:row>
        	<msh:autoComplete property="categoryDifference" fieldColSpan="4"
        	label="Категория" horizontalFill="true" vocName="vocDeathCategory"/>
        </msh:row>
        <msh:row>
	        <msh:textField property="dateBegin" label="Период с" guid="8d7ef035-1273-4839-a4d8-1551c623caf1" />
    	    <msh:textField property="dateEnd" label="по" guid="f54568f6-b5b8-4d48-a045-ba7b9f875245" />
           <td>
            <input type="submit" onclick="find()" value="Найти" />
          </td>
        </msh:row>
        <msh:row>


      </msh:row>
    </msh:panel>
    </msh:form>
    
    
    <script type='text/javascript'>
     //checkFieldUpdate('typePatient','${typePatient}',4) ;
     checkFieldUpdate('typeEmergency','${typeEmergency}',3) ;
     checkFieldUpdate('typeAutopsy','${typeAutopsy}',3) ;
     checkFieldUpdate('typeOperation','${typeOperation}',3) ;
     checkFieldUpdate('typeDifference','${typeDifference}',3) ;
     //checkFieldUpdate('period','${period}',3) ;
     
     function checkFieldUpdate(aField,aValue,aDefaultValue) {
        	eval('var chk =  document.forms[0].'+aField) ;
        	//alert(aField+" "+aValue+" "+aMax+" "+chk) ;
        	aValue=+aValue ;
        	var max=chk.length ;
        	if (aValue==0 || (aValue)>(max)) {
        		chk[+aDefaultValue-1].checked='checked' ;
        	} else {
        		chk[+aValue-1].checked='checked' ;
        	}
     }
    
    function find() {
    	var frm = document.forms[0] ;
    	frm.target='' ;
    	frm.action='stac_deathCase_list.do' ;
    	frm.submit() ;
    }
    </script>
    
    <%
    String date = (String)request.getParameter("dateBegin") ;
    if (date!=null && !date.equals(""))  {
        String dateEnd = request.getParameter("dateEnd") ;
        //String id = (String)request.getParameter("id") ;
        if (dateEnd==null || dateEnd.equals("")) dateEnd=date ;
        request.setAttribute("dateEnd", dateEnd) ;
        request.setAttribute("emerSql", ReportParamUtil.getEmergencySql(typeEmergency, "m")) ;
		request.setAttribute( "emerInfo",ReportParamUtil.getEmergencyInfo(typeEmergency)) ;
		if (typeAutopsy!=null && typeAutopsy.equals("1")) {
			request.setAttribute("autopsySql", " and dc.isAutopsy='1' ") ;
			request.setAttribute("autopsyInfo", "производилось вскрытие") ;
		} else if (typeAutopsy!=null && typeAutopsy.equals("2")) {
			request.setAttribute("autopsyInfo", "не производилось вскрытие") ;
			request.setAttribute("autopsySql", " and (dc.isAutopsy is null or dc.isAutopsy='0') ") ;
		}
		if (typeDifference!=null && typeDifference.equals("1")) {
			request.setAttribute("differenceSql", " and dc.categoryDifference_id is not null") ;
			request.setAttribute("differenceInfo", " были расхождения по диагнозу ") ;
		} else if (typeDifference!=null && typeDifference.equals("2")) {
			request.setAttribute("differenceInfo", "не было расхождений по диагнозу ") ;
			request.setAttribute("differenceSql", " and dc.categoryDifference_id is null ") ;
		}
    	
		if (typeOperation!=null && typeOperation.equals("1")) {
			request.setAttribute("operationSql", " and (so1.id is not null or so2.id is not null) ") ;
			request.setAttribute("operationInfo", " были произведены хирургические вмешательства ") ;
		} else if (typeOperation!=null && typeOperation.equals("2")) {
			request.setAttribute("operationInfo", "не было хирургических вмешательств ") ;
			request.setAttribute("operationSql", " and (so1.id is null and so2.id is null) ") ;
		}
		String categoryDifference=request.getParameter("categoryDifference") ;
		String sex=request.getParameter("sex") ;
		String department=request.getParameter("department") ;
		
    	if (sex!=null && !sex.equals("")&& !sex.equals("0")) {
    		request.setAttribute("sexSql", " and p.sex_id='"+sex+"' ") ;
    	}
    	if (department!=null && !department.equals("")&& !department.equals("0")) {
    		request.setAttribute("departmentSql", " and dmc.department_id='"+department+"' ") ;
    	}
    	if (categoryDifference!=null && !categoryDifference.equals("")&& !categoryDifference.equals("0")) {
    		request.setAttribute("categoryDifferenceSql", " and dc.categoryDifference_id='"+categoryDifference+"' ") ;
    	}
    	%>
    <ecom:webQuery nameFldSql="journal_ticket_sql" name="journal_ticket" nativeSql="select
    m.id, ss.code as sscode
    ,p.lastname||' '||p.firstname||' '||p.middlename||' гр '||to_char(p.birthday,'DD.MM.YYYY') as fiodr
    ,cast(to_char(dc.deathDate,'yyyy') as int)
-cast(to_char(p.birthday,'yyyy') as int)
+(case when (cast(to_char(dc.deathDate, 'mm') as int)
-cast(to_char(p.birthday, 'mm') as int)
+(case when (cast(to_char(dc.deathDate,'dd') as int) 
- cast(to_char(p.birthday,'dd') as int)<0) then -1 else 0 end)
<0)
then -1 else 0 end)
 as age
    ,dml.name || case when dml.isNoOmc='1' then ' (перевед. из отд. '||coalesce(pml.name,'-')||')' else '' end as depname
    ,to_char(m.dateStart,'dd.mm.yyyy')||' '||cast(m.entranceTime as varchar(5)) as dateStart
    ,to_char(dc.deathDate,'dd.mm.yyyy')||' '||cast(dc.deathTime as varchar(5)) as deathdate
    ,	  case 
			when (dc.deathDate-m.dateStart)=0 then 1 
			when bf.addCaseDuration='1' then ((dc.deathDate-m.dateStart)+1) 
			else (dc.deathDate-m.dateStart)
		  end as cntDays 
    ,case when m.emergency='1' then 'экстр.' else 'план.' end as emergency 
    ,list(distinct case when (vdrt.code='3' or vdrt.code='4') then vpd.name||' '||mkb.code else null end) as conclDiag
    ,list(distinct case when vdrt.code='5' then vpd.name||' '||mkb.code else null end) as deathDiag
    ,dc.commentReason as dccommentReason
    ,vdcC.name||' '||coalesce(dcvpd.name,'нет данных') as categoryDifference
    ,vdcL.name as latrogeny
    ,dc.commentCategory as dccommentCategory 
    from deathcase dc 
    left join medcase m on m.id=dc.medcase_id
    left join medcase dmc on dmc.parent_id=m.id and dmc.dtype='DepartmentMedCase'
    left join medcase allmc on allmc.parent_id=m.id and allmc.dtype='DepartmentMedCase'
    left join SurgicalOperation so1 on so1.medcase_id=allmc.id
    left join SurgicalOperation so2 on so2.medcase_id=m.id
    left join bedfund bf on bf.id=dmc.bedfund_id
    left join medcase pdmc on pdmc.id=dmc.prevMedCase_id and pdmc.dtype='DepartmentMedCase'
    left join MisLpu dml on dml.id=dmc.department_id
    left join MisLpu pml on pml.id=pdmc.department_id
    left join Diagnosis d on d.medCase_id=m.id
    left join VocPriorityDiagnosis vpd on vpd.id=d.priority_id
    left join VocIdc10 mkb on mkb.id=d.idc10_id
    left join VocDiagnosisRegistrationType vdrt on vdrt.id=d.registrationType_id
    left join VocPriorityDiagnosis dcvpd on dcvpd.id=dc.diagnosisDifference_id
    left join VocDeathCategory vdcC on vdcC.id=dc.categoryDifference_id
    left join VocDeathCategory vdcL on vdcL.id=dc.latrogeny_id
    left join statisticstub ss on ss.id=m.statisticstub_id 
    left join patient p on p.id=m.patient_id 
    where dc.deathDate between to_date('${param.dateBegin}','dd.mm.yyyy') 
    and to_date('${dateEnd}','dd.mm.yyyy')
    and dmc.transferDate is null
    ${emerSql} ${autopsySql} ${differenceSql} ${operationSql}
    ${sexSql} ${departmentSql} ${categoryDifferenceSql}
    group by dc.id,m.id,p.lastname,p.firstname,p.middlename,p.birthday
    ,m.emergency,dc.deathDate,dc.deathTime,dc.commentReason
    ,ss.code  ,vdcL.name,vdcC.name,dcvpd.name
    ,m.dateStart,m.entranceTime,pml.name,dml.name,dml.isNoOmc,bf.addCaseDuration
    ,dc.commentCategory
    " guid="4a720225-8d94-4b47-bef3-4dbbe79eec74" />
    <script type="text/javascript">
    function print() {
    	var frm = document.forms[0] ;
    	frm.target='_blank' ;
    	frm.sqlText.value="${journal_ticket_sql}" ;
    	frm.m.value='printNativeQuery' ;
    	frm.s.value='PrintService' ;
    	frm.action='print-stac_journal_death_case.do' ;
    	frm.submit() ;
    }
    </script>
    
    <msh:section>
    <msh:sectionTitle>Результаты поиска ${infoTypePat}. Период с ${param.dateBegin} по ${dateEnd}.
    <input type="submit" onclick="print()" value="Печать" />
    </msh:sectionTitle>
    <msh:sectionContent>
    	<input type="button" value="Печать экс. карт по выбранным ИБ" onclick="printExpCard('stac_expcards_death_empty')"> 
        <msh:table selection="multiply" name="journal_ticket" action="entityView-stac_ssl.do" idField="1" noDataMessage="Не найдено">
            <msh:tableColumn columnName="#" property="sn"/>
            <msh:tableColumn columnName="№ИБ" property="2"/>
            <msh:tableColumn columnName="ФИО пациента" property="3"/>
            <msh:tableColumn columnName="Возраст" property="4"/>
            <msh:tableColumn columnName="Отделение" property="5"/>
            <msh:tableColumn columnName="Дата пост. в стац." property="6"/>
            <msh:tableColumn columnName="Дата смерти" property="7"/>
            <msh:tableColumn columnName="К.дни" property="8"/>
            <msh:tableColumn property="9" columnName="Показания к поступлению"/>
            <msh:tableColumn property="10" columnName="МКБ клин. диагноза"/>
            <msh:tableColumn property="11" columnName="МКБ пат. анат. диагноза"/>
            <msh:tableColumn property="12" columnName="Причина смерти"/>
            <msh:tableColumn property="13" columnName="Категория расхождения"/>
            <msh:tableColumn property="14" columnName="Ятрогения"/>
            <msh:tableColumn property="15" columnName="Комментарий"/>
        </msh:table>
    </msh:sectionContent>
    </msh:section>
    <% } else {%>
    	<i>Выберите параметры и нажмите найти </i>
    	<% }   %>
    
  </tiles:put>
     <tiles:put name="javascript" type="string">
        <script type="text/javascript">
            function printExpCard(aFile) {
            	var ids = theTableArrow.getInsertedIdsAsParams("id","journal_ticket") ;
            	if(ids) {
            		//alert(ids) ;
            		window.location = 'print-'+aFile+'.do?multy=1&m=printStatCards&s=HospitalPrintService1&'+ids ;
            		
            	} else {
            		alert("Нет выделенных случаев");
            	}
            	
            }
       </script>
   </tiles:put>  
</tiles:insert>

