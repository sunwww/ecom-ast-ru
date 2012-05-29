<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://struts.apache.org/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://www.nuzmsh.ru/tags/msh" prefix="msh" %>
<%@ taglib uri="http://www.ecom-ast.ru/tags/ecom" prefix="ecom" %>
<%@ taglib tagdir="/WEB-INF/tags" prefix="tags" %>

<tiles:insert page="/WEB-INF/tiles/mainLayout.jsp" flush="true" >

    <tiles:put name='title' type='string'>
        <msh:title mainMenu="StacJournal">Просмотр данных по диагнозам в СЛО</msh:title>
    </tiles:put>

    <tiles:put name='side' type='string'>
        <tags:stac_journal currentAction="stac_journalDiagBySlo"/>
    </tiles:put>
    
  <tiles:put name="body" type="string">
    <msh:form action="/poly_ticketsByNonredidentPatientList.do" defaultField="department" disableFormDataConfirm="true" method="GET" guid="d7b31bc2-38f0-42cc-8d6d-19395273168f">
    <msh:panel guid="6ae283c8-7035-450a-8eb4-6f0f7da8a8ff">
      <msh:row guid="53627d05-8914-48a0-b2ec-792eba5b07d9">
        <msh:separator label="Параметры поиска" colSpan="7" guid="15c6c628-8aab-4c82-b3d8-ac77b7b3f700" />
      </msh:row>
      <msh:row guid="7d80be13-710c-46b8-8503-ce0413686b69">
        <td class="label" title="Поиск по дате  (typeDate)" colspan="1"><label for="typeDateName" id="typeDateLabel">Искать по дате:</label></td>
        <td onclick="this.childNodes[1].checked='checked';">
        	<input type="radio" name="typeDate" value="1">  поступления
        </td>
        <td onclick="this.childNodes[1].checked='checked';">
        	<input type="radio" name="typeDate" value="2">  выписки
        </td>
        <td onclick="this.childNodes[1].checked='checked';">
        	<input type="radio" name="typeDate" value="3">  перевода
        </td>
        </msh:row>
     <%-- <msh:row guid="7d80be13-710c-46b8-8503-ce0413686b69">
        <td class="label" title="Поиск по пациентам (typePatient)" colspan="1"><label for="typePatientName" id="typePatientLabel">Пациенты:</label></td>
        <td onclick="this.childNodes[1].checked='checked';">
        	<input type="radio" name="typePatient" value="1">  региональные
        </td>
        <td onclick="this.childNodes[1].checked='checked';">
        	<input type="radio" name="typePatient" value="2">  иногородные
        </td>
        <td onclick="this.childNodes[1].checked='checked';">
        	<input type="radio" name="typePatient" value="3">  все
        </td>
        </msh:row>  --%>
     <msh:row guid="7d80be13-710c-46b8-8503-ce0413686b69">
        <td class="label" title="Поиск по МКБ (typeMKB)" colspan="1"><label for="typeMKbName" id="typeMkbLabel">МКБ:</label></td>
        <td onclick="this.childNodes[1].checked='checked';">
        	<input type="radio" name="typeMKB" value="1">  перв. симв.
        </td>
        <td onclick="this.childNodes[1].checked='checked';">
        	<input type="radio" name="typeMKB" value="2">  два симв.
        </td>
        <td onclick="this.childNodes[1].checked='checked';">
        	<input type="radio" name="typeMKB" value="3">  три симв.
        </td>
        <td onclick="this.childNodes[1].checked='checked';">
        	<input type="radio" name="typeMKB" value="4">  полностью
        </td>
        </msh:row>
        <msh:row>
        	<msh:autoComplete property="department" fieldColSpan="5"
        	label="Отделение" horizontalFill="true" vocName="lpu"/>
        </msh:row>
        <msh:row>
        	<msh:autoComplete property="serviceStream" fieldColSpan="2"
        	label="Поток обслуживания" horizontalFill="true" vocName="vocServiceStream"/>
          <msh:autoComplete vocName="vocBedSubType" property="bedSubType" label="Тип коек" horizontalFill="true" fieldColSpan="1" />
        </msh:row>
        <msh:row>
          <msh:autoComplete property="registrationType" label="Тип регистрации" horizontalFill="true" fieldColSpan="2" vocName="vocDiagnosisRegistrationType"  />
          <msh:autoComplete vocName="vocPriorityDiagnosis" property="priority" label="Приоритет"  horizontalFill="true" />
        </msh:row>        
        <msh:row>
        	<msh:textField property="filterAdd" label="Фильтр МКБ" fieldColSpan="2"/>
        	<td><i>Пример: <b>A0</b>, <b>N</b>, <b>A00-B87</b></i></td>
        </msh:row>
        <msh:row>
	        <msh:textField property="dateBegin" label="Период с" guid="8d7ef035-1273-4839-a4d8-1551c623caf1" />
	        <msh:textField property="dateEnd" label="по" guid="f54568f6-b5b8-4d48-a045-ba7b9f875245" />
			<td>
	            <input type="submit" onclick="find()" value="Найти" />
	          </td>
        </msh:row>
    </msh:panel>
    </msh:form>
    
    <%
    String date = (String)request.getParameter("dateBegin") ;
    if (date!=null && !date.equals(""))  {
    	String dateEnd = (String)request.getParameter("dateEnd") ;
    	if (dateEnd==null||dateEnd.equals("")) {
    		request.setAttribute("dateEnd", date) ;
    	} else {
    		request.setAttribute("dateEnd", dateEnd) ;
    	}
    	
    	String stat = (String)request.getParameter("typeStatus") ;
    	String typeMKB = (String)request.getAttribute("typeMKB") ;
    	String typeDate = (String)request.getAttribute("typeDate") ;
    	String mkbCode = "mkb.code" ;
    	String mkbName = "mkb.name" ;
    	String mkbLike = "0" ;
    	if (typeMKB!=null) {
    		if (typeMKB.equals("1")) {mkbName="substring(mkb.code,1,1)";mkbCode="substring(mkb.code,1,1)" ;mkbLike="1";} 
    		else if (typeMKB.equals("2")) {mkbName="substring(mkb.code,1,2)";mkbCode="substring(mkb.code,1,2)" ;mkbLike="1";}
    		else if (typeMKB.equals("3")) {mkbName="substring(mkb.code,1,3)";mkbCode="substring(mkb.code,1,3)" ;mkbLike="1";}
    	} 
    	request.setAttribute("mkbName",mkbName) ;
    	request.setAttribute("mkbNameGroup",mkbName.equals("mkb.name")?",mkb.name":"") ;
    	request.setAttribute("mkbCode",mkbCode) ;
    	request.setAttribute("mkbLike",mkbLike) ;
    	String fldDate = "slo.dateFinish" ;
    	if (typeDate!=null) {
    		if (typeDate.equals("1")) {fldDate="slo.dateStart" ;} 
    		else if (typeDate.equals("3")) {fldDate="slo.transferDate" ;}
    	} 
    	request.setAttribute("fldDate",fldDate) ;
    	boolean isStat = true ;
    	if (stat!=null && stat.equals("2")) {
    		isStat = false ;
    	}

    	String dep = (String)request.getParameter("department") ;
    	if (dep!=null && !dep.equals("") && !dep.equals("0")) {
    		request.setAttribute("dep", " and slo.department_id="+dep) ;
    		request.setAttribute("department",dep) ;
    	} else {
    		request.setAttribute("department","0") ;
    	}
    	String servStream = (String)request.getParameter("serviceStream") ;
    	if (servStream!=null && !servStream.equals("") && !servStream.equals("0")) {
    		request.setAttribute("servStream", " and vss.id="+servStream) ;
    		request.setAttribute("serviceStream", servStream) ;
    	} else {
    		request.setAttribute("serviceStream", "0") ;
    	}
    	String regType = (String)request.getParameter("registrationType") ;
    	if (regType!=null && !regType.equals("") && !regType.equals("0")) {
    		request.setAttribute("regType", " and diag.registrationType_id="+regType) ;
    	} 
    	String priority = (String)request.getParameter("priority") ;
    	if (priority!=null && !priority.equals("") && !priority.equals("0")) {
    		request.setAttribute("prioritySql", " and diag.priority_id="+priority) ;
    	} 
    	String bedSubType = (String)request.getParameter("bedSubType") ;
    	if (bedSubType!=null && !bedSubType.equals("") && !bedSubType.equals("0")) {
    		request.setAttribute("bedSubTypeSql", " and bf.bedSubType_id="+bedSubType) ;
    	} 
    	String filter = (String)request.getParameter("filterAdd") ;
    	if (filter!=null && !filter.equals("")) {
    		filter = filter.toUpperCase() ;
    		request.setAttribute("filterAdd",filter) ;
    		String[] fs=filter.split("-") ;
    		if (fs.length>1) {
    			request.setAttribute("filterSql", " and mkb.code between '"+fs[0].trim()+"' and '"+fs[1].trim()+"'") ;
    		} else {
    			request.setAttribute("filterSql", " and mkb.code like '"+filter.trim()+"%'") ;
    		}
    		
    	} 
    	
    	%>
		<ecom:webQuery name="diag_list" nativeSql="
		select ${mkbCode}||':${mkbLike}:${dep}:${servStream}:${fldDate}:${param.dateBegin}:${dateEnd}:'||vpd.id||':'||vdrt.id||':${bedSubTypeSql}:${filterAdd}' as id
		,${mkbCode} as mkb,count(*),vpd.name as vpdname,vdrt.name as vdrtname 
		,${mkbName} as mkbname
		,count(distinct sls.id)
		,count(distinct case when sls.dateFinish is not null and sls.dischargeTime is not null then sls.id else null end) as cntDischarge
		,count(distinct case when vhr.code='11' then sls.id else null end) as cntDeath
		,round(sum(
			distinct 
			  cast(case 
				when (sls.dateFinish is null or sls.dischargeTime is null) then 0 
				when (sls.dateFinish-sls.dateStart)=0 then 1 
				when bf.addCaseDuration='1' then ((sls.dateFinish-sls.dateStart)+1) 
				else (sls.dateFinish-sls.dateStart) end 
				||'.'|| sls.id as decimal)
					)
			-sum(distinct 
			  cast('0.'|| case when sls.dateFinish is not null and sls.dischargeTime is not null then sls.id else 0 end as decimal))
			  ,0) as cntSlsDays
		,
		case when count(distinct case 
		when sls.dateFinish is not null and sls.dischargeTime is not null then sls.id 
		else null end)>0 
		then
			round((
			sum(
			distinct 
			  cast(case 
				when (sls.dateFinish is null or sls.dischargeTime is null) then 0 
				when (sls.dateFinish-sls.dateStart)=0 then 1 
				when bf.addCaseDuration='1' then ((sls.dateFinish-sls.dateStart)+1) 
				else (sls.dateFinish-sls.dateStart) end 
				||'.'|| sls.id as decimal)
					)
			-sum(distinct 
			  cast('0.'|| case when sls.dateFinish is not null and sls.dischargeTime is not null then sls.id else 0 end as decimal))
			 )
			/count(distinct case when sls.dateFinish is not null and sls.dischargeTime is not null then sls.id else null end),1)
			else 0 end as cntSrDays
		from Diagnosis diag
		left join VocIdc10 mkb on mkb.id=diag.idc10_id
		left join MedCase slo on slo.id=diag.medCase_id
		left join MedCase sls on sls.id=slo.parent_id
		left join VocHospitalizationResult vhr on vhr.id=sls.result_id
		left join BedFund bf on bf.id=slo.bedFund_id
		left join VocServiceStream vss on vss.id=slo.serviceStream_id
		left join VocDiagnosisRegistrationType vdrt on vdrt.id=diag.registrationType_id
		left join VocPriorityDiagnosis vpd on vpd.id=diag.priority_id
		where ${fldDate} between to_date('${param.dateBegin}','dd.mm.yyyy')
			and to_date('${dateEnd}','dd.mm.yyyy') and slo.dtype='DepartmentMedCase'
			${servStream} ${dep} ${prioritySql} ${regType} ${bedSubTypeSql}
			${filterSql}
		group by ${mkbCode},vpd.id,vpd.name,vdrt.id,vdrt.name ${mkbNameGroup}
		order by ${mkbCode},vpd.id,vdrt.id
		"/>
		<msh:table name="diag_list" idField="1" action="js-stac_diagnosis-by_slo_data.do">
			<msh:tableColumn property="sn" columnName="#"/>
			<msh:tableColumn property="5" columnName="Тип регистрации"/>
			<msh:tableColumn property="4" columnName="Приоритет"/>
			<msh:tableColumn property="2" columnName="Код МКБ10"/>
			<msh:tableColumn property="6" columnName="Наименование болезни"/>
			<msh:tableColumn property="3" columnName="Кол-во СЛО"/>
			<msh:tableColumn property="7" columnName="Кол-во госпитализаций"/>
			<msh:tableColumn property="8" columnName="Кол-во выписанных"/>
			<msh:tableColumn property="10" columnName="Кол-во к/дней"/>
			<msh:tableColumn property="11" columnName="Средний к/день"/>
			<msh:tableColumn property="9" columnName="Кол-во умерших"/>
		</msh:table>
    <% } else { %>
    	<i>Выберите параметры и нажмите найти </i>
    <% }   %>
    
    <script type='text/javascript'>
    //var typePatient = document.forms[0].typePatient ;
    //var typeDate = document.forms[0].typeDate ;
    //checkFieldUpdate('typeSwod','${typeSwod}',2,1) ;
    checkFieldUpdate('typeDate','${typeDate}',2) ;
    checkFieldUpdate('typeMKB','${typeMKB}',4) ;
    //checkFieldUpdate('typePatient','${typePatient}',3,3) ;
    //checkFieldUpdate('typeStatus','${typeStatus}',2,2) ;
    
    function checkFieldUpdate(aField,aValue,aDefaulValue) {
       	eval('var chk =  document.forms[0].'+aField) ;
       	var max = chk.length ;
       	aValue=+aValue ;
       	if (aValue==0 || (aValue>max)) {aValue=+aDefaultValue}
       	if (aValue==0 || (aValue>max)) {
       		chk[max-1].checked='checked' ;
       	} else {
       		chk[aValue-1].checked='checked' ;
       	}
    }
    function find() {
    	var frm = document.forms[0] ;
    	frm.target='' ;
    	frm.action='stac_diagnosis_by_slo_list.do' ;
    }
    function print() {
    	var frm = document.forms[0] ;
    	frm.target='_blank' ;
    	//frm.action='stac_groupByBedFundList.do' ;
    }
    </script>
  </tiles:put>
</tiles:insert>