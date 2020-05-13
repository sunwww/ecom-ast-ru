<%@page import="ru.ecom.web.util.ActionUtil"%>
<%@page import="ru.nuzmsh.util.query.ReportParamUtil"%>
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
	String typeCertificate =ActionUtil.updateParameter("Report_DEATHCASE","typeCertificate","3", request) ;
  
  %>
    <msh:form action="/poly_ticketsByNonredidentPatientList.do" defaultField="department" disableFormDataConfirm="true" method="GET">
    <msh:panel>
      <msh:row>
        <msh:separator label="Параметры поиска" colSpan="7" />
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
	        <td class="label" title="Поиск по свидетельство о смерти (typeCertificate)" colspan="1"><label for="typeDifferenceName" id="typeDifferenceLabel">Расхождения:</label></td>
	        <td onclick="this.childNodes[1].checked='checked';">
	        	<input type="radio" name="typeCertificate" value="1">  выдавали
	        </td>
	        <td onclick="this.childNodes[1].checked='checked';" >
	        	<input type="radio" name="typeCertificate"  value="2">  не выдавали
	        </td>
	        <td onclick="this.childNodes[1].checked='checked';">
	        	<input type="radio" name="typeCertificate" value="3">  параметр не учитывать
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
        	<msh:textField property="deathReason" label="Причина вскрытия" fieldColSpan="3"/>
        </msh:row>
        <msh:row>
        	<msh:autoComplete property="bedSubType" fieldColSpan="4"
        	label="Тип коек" horizontalFill="true" vocName="vocBedSubType"/>
        </msh:row>
        <msh:row>
	        <msh:textField property="dateBegin" label="Период с" />
    	    <msh:textField property="dateEnd" label="по" />
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
     checkFieldUpdate('typeCertificate','${typeCertificate}',3) ;
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
    String date = request.getParameter("dateBegin") ;
    if (date!=null && !date.equals(""))  {
        String dateEnd = request.getParameter("dateEnd") ;
        //String id = (String)request.getParameter("id") ;
        if (dateEnd==null || dateEnd.equals("")) dateEnd=date ;
        request.setAttribute("dateEnd", dateEnd) ;
        request.setAttribute("emerSql", ReportParamUtil.getEmergencySql(typeEmergency, "m")) ;
		request.setAttribute( "emerInfo",ReportParamUtil.getEmergencyInfo(typeEmergency)) ;
		if ("1".equals(typeAutopsy)) {
			request.setAttribute("autopsySql", " and dc.isAutopsy='1' ") ;
			request.setAttribute("autopsyInfo", "производилось вскрытие") ;
		} else if ("2".equals(typeAutopsy)) {
			request.setAttribute("autopsyInfo", "не производилось вскрытие") ;
			request.setAttribute("autopsySql", " and (dc.isAutopsy is null or dc.isAutopsy='0') ") ;
		}
		if ("1".equals(typeDifference)) {
			request.setAttribute("differenceSql", " and dc.categoryDifference_id is not null") ;
			request.setAttribute("differenceInfo", " были расхождения по диагнозу ") ;
		} else if ("2".equals(typeDifference)) {
			request.setAttribute("differenceInfo", "не было расхождений по диагнозу ") ;
			request.setAttribute("differenceSql", " and dc.categoryDifference_id is null ") ;
		}
		String deathReson=request.getParameter("deathReason") ;
    	if (deathReson!=null&&!deathReson.equals("")) {
    		request.setAttribute("deathReasonSql", " and upper(dc.commentReason) like '%"+deathReson.toUpperCase()+"%'") ;
    	}
		if ("1".equals(typeOperation)) {
			request.setAttribute("operationSql", " and (so1.id is not null or so2.id is not null) ") ;
			request.setAttribute("operationInfo", " были произведены хирургические вмешательства ") ;
		} else if ("2".equals(typeOperation)) {
			request.setAttribute("operationInfo", "не было хирургических вмешательств ") ;
			request.setAttribute("operationSql", " and (so1.id is null and so2.id is null) ") ;
		}
		if ("1".equals(typeCertificate)) {
			request.setAttribute("certificateSql", " and c.id is not null ") ;
			request.setAttribute("certificateInfo", " были выданы свидетельства о смерти ") ;
		} else if ("2".equals(typeCertificate)) {
			request.setAttribute("certificateInfo", " нет свидетельств о смерти ") ;
			request.setAttribute("certificateSql", " and c.id is null ") ;
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
    	String bedSubType = request.getParameter("bedSubType");
    	
    	if (bedSubType!=null&&!bedSubType.equals("")&&!bedSubType.equals("0")) {
    		request.setAttribute("bedSubTypeSql", " and vbst.id='"+bedSubType+"'");
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
    ,to_char(dc.dateForensic,'dd.mm.yyyy') as dateForensic
    ,rmkb.code as rmkbcode
    ,case when dc.isAutopsy='1' then 'производилось' ||case when dc.isPresenceDoctorAutopsy='1' then ', врач присутствовал при вскрытие' else '' end  else '' end as isAutopsy
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
    left join VocIdc10 rmkb on rmkb.id=dc.reasonMainMkb_id
    left join VocDiagnosisRegistrationType vdrt on vdrt.id=d.registrationType_id
    left join VocPriorityDiagnosis dcvpd on dcvpd.id=dc.diagnosisDifference_id
    left join VocDeathCategory vdcC on vdcC.id=dc.categoryDifference_id
    left join VocDeathCategory vdcL on vdcL.id=dc.latrogeny_id
    left join statisticstub ss on ss.id=m.statisticstub_id 
    left join patient p on p.id=m.patient_id 
    left join Certificate c on c.DeathCase_id=dc.id and c.dtype='DeathCertificate'
    left join vocbedsubtype vbst on vbst.id=bf.bedsubtype_id
    where dc.deathDate between to_date('${param.dateBegin}','dd.mm.yyyy') 
    and to_date('${dateEnd}','dd.mm.yyyy')
    and dmc.transferDate is null
    ${emerSql} ${autopsySql} ${differenceSql} ${operationSql}
    ${sexSql} ${departmentSql} ${categoryDifferenceSql} ${deathReasonSql} ${bedSubTypeSql}
    group by dc.id,m.id,p.lastname,p.firstname,p.middlename,p.birthday
    ,m.emergency,dc.deathDate,dc.deathTime,dc.commentReason
    ,ss.code  ,vdcL.name,vdcC.name,dcvpd.name
    ,m.dateStart,m.entranceTime,pml.name,dml.name,dml.isNoOmc,bf.addCaseDuration
    ,dc.commentCategory,rmkb.code,dc.isAutopsy,dc.isPresenceDoctorAutopsy,dc.dateForensic
    " />
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
        <msh:table printToExcelButton="Сохранить в excel" selection="multiply" name="journal_ticket" action="entityView-stac_ssl.do" idField="1" noDataMessage="Не найдено">
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
            <msh:tableColumn property="17" columnName="МКБ"/>
            <msh:tableColumn property="18" columnName="Вкрытие"/>
            <msh:tableColumn property="13" columnName="Категория расхождения"/>
            <msh:tableColumn property="14" columnName="Ятрогения"/>
            <msh:tableColumn property="15" columnName="Комментарий"/>
            <msh:tableColumn property="16" columnName="Дата СМЭ"/>
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
            		window.location = 'print-'+aFile+'.do?multy=1&m=printStatCards&s=HospitalPrintService1&'+ids ;
            	} else {
            		alert("Нет выделенных случаев");
            	}
            }
       </script>
   </tiles:put>  
</tiles:insert>

