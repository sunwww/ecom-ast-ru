<%@page import="ru.ecom.web.util.ActionUtil"%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://struts.apache.org/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://www.nuzmsh.ru/tags/msh" prefix="msh" %>
<%@ taglib uri="http://www.ecom-ast.ru/tags/ecom" prefix="ecom" %>
<%@ taglib tagdir="/WEB-INF/tags" prefix="tags" %>

<tiles:insert page="/WEB-INF/tiles/mainLayout.jsp" flush="true" >

    <tiles:put name='title' type='string'>
        <msh:title mainMenu="StacJournal">Просмотр данных по диагнозам госпитализаций (выписным)</msh:title>
    </tiles:put>

    <tiles:put name='side' type='string'>
        <tags:stac_journal currentAction="stac_journalDiagBySlo"/>
    </tiles:put>
    
  <tiles:put name="body" type="string">
  <%
	String typePat =ActionUtil.updateParameter("GroupByBedFund","typePatient","4", request) ;
	ActionUtil.updateParameter("GroupByBedFund","period","2", request) ;
	String typeDate = ActionUtil.updateParameter("GroupByBedFund","typeDate","2", request) ;
	String typeSwod = ActionUtil.updateParameter("GroupByBedFund","typeSwod","1", request) ;
	String typeStatus = ActionUtil.updateParameter("GroupByBedFund","typeStatus","2", request) ;
	String typeView = ActionUtil.updateParameter("GroupByBedFund","typeView","2", request) ;
	String typeMKB = ActionUtil.updateParameter("DiagnosisBySlo","typeMKB","4", request) ;
	String typeEmergency = ActionUtil.updateParameter("DiagnosisBySlo","typeEmergency","3", request) ;
	String typeReestr = request.getParameter("typeReestr") ;
  %>
    <msh:form action="/stac_diagnosis_by_sls_list.do" defaultField="department" disableFormDataConfirm="true" method="GET">
    <msh:panel>
    	<input type="hidden" name="m" id="m" value="printDiagnosisJournal"/>
    	<input type="hidden" name="s" id="s" value="HospitalPrintReport"/>
      <msh:row>
        <msh:separator label="Параметры поиска" colSpan="7"/>
      </msh:row>
      <msh:row>
        <td class="label" title="Поиск по дате  (typeDate)" colspan="1"><label for="typeDateName" id="typeDateLabel">Искать по дате:</label></td>
        <td onclick="this.childNodes[1].checked='checked';">
        	<input type="radio" name="typeDate" value="1">  поступления
        </td>
        <td onclick="this.childNodes[1].checked='checked';">
        	<input type="radio" name="typeDate" value="2">  выписки
        </td>
        </msh:row>
        <msh:row>
	        <td class="label" title="Поиск по показаниям поступления (typeEmergency)" colspan="1"><label for="typeEmergencyName" id="typeEmergencyLabel">Показания:</label></td>
	        <td onclick="this.childNodes[1].checked='checked';">
	        	<input type="radio" name="typeEmergency" value="1">  экстренные
	        </td>
	        <td onclick="this.childNodes[1].checked='checked';">
	        	<input type="radio" name="typeEmergency" value="2"  >  плановые
	        </td>
	        <td onclick="this.childNodes[1].checked='checked';">
	        	<input type="radio" name="typeEmergency" value="3">  все
	        </td>
        </msh:row>
     
   
     <msh:row>
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
          <msh:autoComplete vocName="vocBedType" property="bedType" label="Профиль коек" horizontalFill="true" fieldColSpan="5" />
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
        	<msh:textField property="filterAdd" label="Фильтр МКБ" fieldColSpan="3" horizontalFill="true"/>
        	<td><i>Пример: <b>A0</b>, <b>N</b>, <b>A00-B87</b></i></td>
        </msh:row>
        <msh:row>
	        <msh:textField property="dateBegin" label="Период с"/>
	        <msh:textField property="dateEnd" label="по"/>
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
    	if (typeStatus!=null && typeStatus.equals("2")) {
    		isStat = false ;
    	}

    	String dep = (String)request.getParameter("department") ;
    	if (dep!=null && !dep.equals("") && !dep.equals("0")) {
    		request.setAttribute("departmentSql", " and slo.department_id="+dep) ;
    		request.setAttribute("department",dep) ;
    	} else {
    		request.setAttribute("department","0") ;
    	}
    	
    	String servStream = (String)request.getParameter("serviceStream") ;
    	
    	if (servStream!=null && !servStream.equals("") && !servStream.equals("0")) {
    		request.setAttribute("serviceStreamSql", " and vss.id="+servStream) ;
    		request.setAttribute("serviceStream", servStream) ;
    	} else {
    		request.setAttribute("serviceStream", "0") ;
    	}
    	String regType = (String)request.getParameter("registrationType") ;
    	if (regType!=null && !regType.equals("") && !regType.equals("0")) {
    		request.setAttribute("registrationTypeSql", " and diag.registrationType_id="+regType) ;
    	} 
    	String priority = (String)request.getParameter("priority") ;
    	if (priority!=null && !priority.equals("") && !priority.equals("0")) {
    		request.setAttribute("prioritySql", " and diag.priority_id="+priority) ;
    	} 
    	String bedSubType = (String)request.getParameter("bedSubType") ;
    	if (bedSubType!=null && !bedSubType.equals("") && !bedSubType.equals("0")) {
    		request.setAttribute("bedSubTypeSql", " and bf.bedSubType_id="+bedSubType) ;
    	} 
    	ActionUtil.setParameterFilterSql("bedType","bf.bedType_id", request) ;
    	String filter = (String)request.getParameter("filterAdd") ;
    	if (filter!=null && !filter.equals("")) {
    		filter = filter.toUpperCase() ;
    		request.setAttribute("filterAdd",filter) ;
    		//filter = filter.replaceAll(" ", "") ;
    		String[] fs1=filter.split(",") ;
    		StringBuilder filtOr = new StringBuilder() ;
    		
    		for (int i=0;i<fs1.length;i++) {
    			String filt1 = fs1[i].trim() ;
    			String[] fs=filt1.split("-") ;
    			if (filt1.length()>0) {
	    			if (filtOr.length()>0) filtOr.append(" or ") ;
		    		if (fs.length>1) {
		    			String fsE1 = fs[1].trim() ;
		    			int ind = fsE1.indexOf(".");
		    			if (ind>-1) {fsE1=fsE1+"999";} else if (fsE1.length()<2) {
		    				for (int ij=0;ij<2-fsE1.length();ij++) {fsE1=fsE1+"9" ;}
		    				fsE1=fsE1+".999";
		    			} else {
		    				fsE1=fsE1+".999";}
		    			filtOr.append(" mkb.code between '"+fs[0].trim()+"' and '"+fsE1+"'") ;
		    		} else {
		    			filtOr.append(" substring(mkb.code,1,"+filt1.length()+")='"+filt1+"'") ;
		    		}
    			}
    		}
    		request.setAttribute("filterSql", filtOr.insert(0, " and (").append(")").toString()) ;
    		
    	} 
    	if (typeEmergency!=null && typeEmergency.equals("1")) {
    		request.setAttribute("emergencySql", " and sls.emergency='1' ") ;
    	} else if (typeEmergency!=null && typeEmergency.equals("2")) {
    		request.setAttribute("emergencySql", " and (sls.emergency is null or sls.emergency='0') ") ;
    	} 
    	if (typeReestr!=null && typeReestr.equals("1")) {
    		String mkbCodeP = request.getParameter("mkbcode") ;
    		String mkbCodeA = "and mkb.code = '"+mkbCodeP+"'" ;
    		mkbCodeA=" and mkb.code = '"+mkbCodeP+"'" ;
    		if (!typeMKB.equals("4")) {
    			mkbCodeA=" and mkb.code like '"+mkbCodeP+"%'" ;
    		}
    		request.setAttribute("mkbCodeSql",mkbCodeA) ;
    		
    		%>
    		
	<ecom:webQuery name="datelist" nativeSql="
	select sls.id,sls.dateStart,sls.dateFinish
	,pat.lastname ||' ' ||pat.firstname|| ' ' || pat.middlename
	,pat.birthday,sc.code,mkb.code||' '||diag.name as mkbcode,vh.name as vhname
	,coalesce(a.fullname)||' ' || case when pat.houseNumber is not null and pat.houseNumber!='' then ' д.'||pat.houseNumber else '' end 
	 ||case when pat.houseBuilding is not null and pat.houseBuilding!='' then ' корп.'|| pat.houseBuilding else '' end 
	||case when pat.flatNumber is not null and pat.flatNumber!='' then ' кв. '|| pat.flatNumber else '' end as address
	, (cast(to_char(${fldDate},'yyyy') as int)-cast(to_char(pat.birthday,'yyyy') as int)
+case when (cast(to_char(${fldDate}, 'mm') as int)-cast(to_char(pat.birthday, 'mm') as int)) <0 then -1 when (cast(to_char(${fldDate},'dd') as int) - cast(to_char(pat.birthday,'dd') as int)<0) and ((cast(to_char(${fldDate}, 'mm') as int)-cast(to_char(pat.birthday, 'mm') as int)-1)<0)  then -1 else 0 end
) as age,
	  case 
			when (coalesce(sls.dateFinish,CURRENT_DATE)-sls.dateStart)=0 then 1 
			when bf.addCaseDuration='1' then ((coalesce(sls.dateFinish,CURRENT_DATE)-sls.dateStart)+1) 
			else (coalesce(sls.dateFinish,CURRENT_DATE)-sls.dateStart)
	 end as cntDays
	 ,vof.name as vofname,vr.name as vrname
	 ,case when sls.emergency='1' then 'Экстр.' else 'План.' end as emergency
	from Diagnosis diag
		left join VocIdc10 mkb on mkb.id=diag.idc10_id
		left join MedCase sls on sls.id=diag.medCase_id
		left join MedCase slo on sls.id=slo.parent_id
		left join BedFund bf on bf.id=slo.bedFund_id
		left join Omc_Frm vof on vof.id=sls.orderType_id
		
	left join StatisticStub as sc on sc.medCase_id=sls.id 
	left join Patient pat on sls.patient_id = pat.id 
	left join VocDiagnosisRegistrationType vdrt on vdrt.id=diag.registrationType_id
	left join VocPriorityDiagnosis vpd on vpd.id=diag.priority_id
	left join VocServiceStream vss on vss.id=sls.serviceStream_id
	left join VocHospitalization vh on vh.id=sls.hospitalization_id
	left join Address2 a on a.addressid=pat.address_addressId
	left join VocRayon vr on vr.id=pat.rayon_id
	where ${fldDate} between to_date('${param.dateBegin}','dd.mm.yyyy')
			and to_date('${dateEnd}','dd.mm.yyyy') and slo.dtype='DepartmentMedCase'
			${serviceStreamSql} ${departmentSql} ${prioritySql} ${registrationTypeSql} ${bedSubTypeSql}
			${emergencySql} ${bedTypeSql}
			${mkbCodeSql}
			${filterSql}
	order by pat.lastname,pat.firstname,pat.middlename
	" 
	/>
	
    <msh:table name="datelist" action="entityParentView-stac_sls.do" idField="1" >
      <msh:tableColumn property="sn" columnName="#"/>
      <msh:tableColumn columnName="Стат.карта" property="6"/>
      <msh:tableColumn columnName="Фамилия имя отчество пациента" property="4"/>
      <msh:tableColumn columnName="Адрес" property="9" role="/Policy/Mis/Journal/ViewInfoPatient" />
      <msh:tableColumn columnName="Дата рождения" property="5"/>
      <msh:tableColumn columnName="Возраст" property="10"/>
      <msh:tableColumn columnName="Дата поступления" property="2"/>
      <msh:tableColumn columnName="Дата выписки" property="3"/>
      <msh:tableColumn columnName="Кол-во к.дней" property="11"/>
      <msh:tableColumn columnName="Госпитализация" property="8"/>
      <msh:tableColumn columnName="Диагноз" property="7"/>
      <msh:tableColumn columnName="Тип доставки" property="12"/>
      <msh:tableColumn columnName="Район" property="13"/>
      <msh:tableColumn columnName="Показания" property="14"/>
    </msh:table>
    	
    		
    		<%
    	} else {
    		
    	%>
		<ecom:webQuery name="diag_list" nameFldSql="diag_list_sql" nativeSql="
		select '&mkbcode='||${mkbCode}||'&registrationType='||vdrt.id||'&priority='||vpd.id as id
		,${mkbCode} as mkb,count(distinct slo.id) as cntSlo,vpd.name as vpdname,vdrt.name as vdrtname 
		,${mkbName} as mkbname
		,count(distinct sls.id) as cntSls
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
		,count(distinct case when so.id is not null then sls.id else null end) as cntOper
		,round(sum(
			distinct 
			  cast(case
			    when coalesce(slo.transferDate,slo.dateFinish) is null then 0 
				when (coalesce(slo.transferDate,slo.dateFinish)-slo.dateStart)=0 then 1 
				when bf.addCaseDuration='1' then ((coalesce(slo.transferDate,slo.dateFinish)-slo.dateStart)+1) 
				else (coalesce(slo.transferDate,slo.dateFinish)-slo.dateStart) end 
				||'.'|| slo.id as decimal)
					)
			-sum(distinct 
			  cast('0.'|| case when coalesce(slo.transferDate,slo.dateFinish) is not null then slo.id else 0 end as decimal))
			  ,0) as cntSloDays
		,
		case when count(distinct case 
		when coalesce(slo.transferDate,slo.dateFinish) is not null then slo.id 
		else null end)>0 
		then
			round((
			sum(
			distinct 
			  cast(case 
				when (coalesce(slo.transferDate,slo.dateFinish) is null) then 0 
				when (coalesce(slo.transferDate,slo.dateFinish)-slo.dateStart)=0 then 1 
				when bf.addCaseDuration='1' then ((coalesce(slo.transferDate,slo.dateFinish)-slo.dateStart)+1) 
				else (coalesce(slo.transferDate,slo.dateFinish)-slo.dateStart) end 
				||'.'|| slo.id as decimal)
					)
			-sum(distinct 
			  cast('0.'|| case when coalesce(slo.transferDate,slo.dateFinish) is not null then slo.id else 0 end as decimal))
			 )
			/count(distinct case when coalesce(slo.transferDate,slo.dateFinish) is not null then slo.id else null end),1)
			else 0 end as cntSrSloDays
		from Diagnosis diag
		left join VocIdc10 mkb on mkb.id=diag.idc10_id
		left join MedCase sls on sls.id=diag.medCase_id
		left join MedCase slo on sls.id=slo.parent_id
		left join BedFund bf on bf.id=slo.bedFund_id
		left join SurgicalOperation so on so.medcase_id=slo.id
		left join VocHospitalizationResult vhr on vhr.id=sls.result_id
		left join VocServiceStream vss on vss.id=sls.serviceStream_id
		left join VocDiagnosisRegistrationType vdrt on vdrt.id=diag.registrationType_id
		left join VocPriorityDiagnosis vpd on vpd.id=diag.priority_id
		where ${fldDate} between to_date('${param.dateBegin}','dd.mm.yyyy')
			and to_date('${dateEnd}','dd.mm.yyyy') and slo.dtype='DepartmentMedCase'
			${serviceStreamSql} ${departmentSql} ${prioritySql} ${registrationTypeSql} ${bedSubTypeSql}
			${emergencySql} ${bedTypeSql}
			${filterSql}
		group by ${mkbCode},vpd.id,vpd.name,vdrt.id,vdrt.name ${mkbNameGroup}
		order by ${mkbCode},vpd.id,vdrt.id
		"/>
	 <form action="print-stac_journalDiag_sls.do" method="post" target="_blank">
    
    <input type='hidden' name="sqlText" id="sqlText" value="${diag_list_sql}"> 
    <input type='hidden' name="sqlInfo" id="sqlInfo" value="Период с ${param.dateBegin} по ${dateEnd}">
    
    <input type='hidden' name="s" id="s" value="PrintService">
    <input type='hidden' name="m" id="m" value="printNativeQuery">
   
    <input type="submit" value="Печать"> 
    </form>
		<msh:table name="diag_list" idField="1" action="stac_diagnosis_by_sls_list.do?typeReestr=1&filterAdd=${param.filterAdd}&serviceStream=${param.serviceStream}&bedSubType=${param.bedSubType}&bedType=${param.bedType}&department=${param.department}&dateBegin=${param.dateBegin}&dateEnd=${param.dateEnd}&typeDate=${typeDate}&typeMKB=${typeMKB}&typeEmergency=${typeEmergency}">
			<msh:tableColumn property="sn" columnName="#"/>
			<msh:tableColumn property="5" columnName="Тип регистрации"/>
			<msh:tableColumn property="4" columnName="Приоритет"/>
			<msh:tableColumn property="2" columnName="Код МКБ10"/>
			<msh:tableColumn property="6" columnName="Наименование болезни"/>
			<msh:tableColumn property="7" isCalcAmount="true" columnName="Кол-во госпитализаций"/>
			<msh:tableColumn property="12" isCalcAmount="true" columnName="Кол-во госпитализаций с операцией"/>
			<msh:tableColumn property="8" isCalcAmount="true" columnName="Кол-во выписанных"/>
			<msh:tableColumn property="10" isCalcAmount="true" columnName="Кол-во к/дней госп."/>
			<msh:tableColumn property="11" columnName="Средний к/день госп."/>
			<msh:tableColumn property="9" isCalcAmount="true" columnName="Кол-во умерших"/>
		</msh:table>
    <% }
    	} else { %>
    	<i>Выберите параметры и нажмите найти </i>
    <% }   %>
    
    <script type='text/javascript'>
    //var typePatient = document.forms[0].typePatient ;
    //var typeDate = document.forms[0].typeDate ;
    //checkFieldUpdate('typeSwod','${typeSwod}',2,1) ;
    checkFieldUpdate('typeDate','${typeDate}',2) ;
    checkFieldUpdate('typeMKB','${typeMKB}',4) ;
    checkFieldUpdate('typeEmergency','${typeEmergency}',4) ;
    //checkFieldUpdate('typePatient','${typePatient}',3,3) ;
    //checkFieldUpdate('typeStatus','${typeStatus}',2,2) ;
    
    function checkFieldUpdate(aField,aValue,aDefaultValue) {
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
    	frm.action='stac_diagnosis_by_sls_list.do' ;
    }
    function print() {
    	var frm = document.forms[0] ;
    	frm.target='_blank' ;
    	frm.action='print-stac_journalDiag.do' ;
    }
	eventutil.addEventListener($('filterAdd'), eventutil.EVENT_KEY_UP, 
  		  	function() {$('filterAdd').value = latRus($('filterAdd').value) ;}
		) ;
	function latRus(aText) {
			aText=aText.toUpperCase() ;
			aText=replaceAll(aText,"Й", "Q" ,1) ;
			aText=replaceAll(aText,"Ц", "W" ,1) ;
	    	aText=replaceAll(aText,"У","E"  ,1) ;
	    	aText=replaceAll(aText, "К", "R" ,1) ;
	    	aText=replaceAll(aText,"Е", "T"  ,1) ;
	    	aText=replaceAll(aText, "Ф","A" ,1) ;
	    	aText=replaceAll(aText, "Ы", "S",1) ;
	    	aText=replaceAll(aText,"В", "D" ,1 ) ;
	    	aText=replaceAll(aText,"А","F" ,1) ;
	    	aText=replaceAll(aText,"П","G"  ,1) ;
	    	aText=replaceAll(aText,"Я","Z" ,1 ) ;
	    	aText=replaceAll(aText,"Ч","X"  ,1) ;
	    	aText=replaceAll(aText,"С","C" ,1 ) ;
	    	aText=replaceAll(aText, "М", "V" ,1) ;
	    	aText=replaceAll(aText,"И", "B",1 ) ;
	    	aText=replaceAll(aText,"Н", "Y" ,1 ) ;
	    	aText=replaceAll(aText,"Г", "U" ,1 ) ;
	    	aText=replaceAll(aText,"Ш", "I" ,1 ) ;
	    	aText=replaceAll(aText,"Щ", "O" ,1 ) ;
	    	aText=replaceAll(aText,"З","P",1 ) ;
	    	aText=replaceAll(aText, "Р","H",1 ) ;
	    	aText=replaceAll(aText,"О", "J" ,1 ) ;
	    	aText=replaceAll(aText,"Л","K" ,1 ) ;
	    	aText=replaceAll(aText,"Д", "L",1 ) ;
	    	aText=replaceAll(aText,"Т","N" ,1) ;
	    	aText=replaceAll(aText, "Ь","M",1 ) ;
	    	aText=replaceAll(aText, "Ю",".",1 ) ;
	    	aText=replaceAll(aText, "Б","," ,1) ;
	    	aText=replaceAll(aText, "Х","[" ,1) ;
	    	aText=replaceAll(aText, "Ъ","]",1 ) ;
	    	aText=replaceAll(aText, "Ж",";",1 ) ;
	    	aText=replaceAll(aText, "Э","'",1 ) ;
	    	return aText ;
		}
		function replaceAll(aText,aSymbRep,aSymbIs,aRedict) {
			var sym1=aSymbRep,sym2=aSymbIs;
			if (+aRedict<1) {
				sym2=aSymbRep,sym1=aSymbIs;
			} 
			while (aText.indexOf(sym1)>-1) {
				aText = aText.replace(sym1,sym2) ;
			}
			return aText ;
		}
    </script>
  </tiles:put>
</tiles:insert>