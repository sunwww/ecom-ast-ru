<%@page import="ru.ecom.ejb.services.query.WebQueryResult"%>
<%@page import="ru.ecom.web.login.LoginInfo"%>
<%@page import="ru.ecom.web.util.ActionUtil"%>
<%@page import="ru.nuzmsh.web.tags.helper.RolesHelper"%>
<%@page import="java.util.List"%>
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
    <msh:form action="/stac_diagnosis_hospital.do" defaultField="department" disableFormDataConfirm="true" method="GET">
    <msh:panel>
    	<input type="hidden" name="m" id="m" value="printDiagnosisJournal"/>
    	<input type="hidden" name="s" id="s" value="HospitalPrintReport"/>
      <msh:row>
        <msh:separator label="Параметры поиска" colSpan="7" />
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
        <msh:ifInRole roles="/Policy/Mis/MedCase/Stac/Journal/ShowInfoAllDepartments">
        <msh:row>
        	<msh:autoComplete property="department" fieldColSpan="5"
        	label="Отделение" horizontalFill="true" vocName="lpu"/>
        </msh:row>
        </msh:ifInRole>
        <msh:row>
          <msh:autoComplete vocName="vocBedType" property="bedType" label="Профиль коек" horizontalFill="true" fieldColSpan="5" />
        </msh:row>
        <msh:row>
        	<msh:autoComplete property="serviceStream" fieldColSpan="2"
        	label="Поток обслуживания" horizontalFill="true" vocName="vocServiceStream"/>
          <msh:autoComplete vocName="vocBedSubType" property="bedSubType" label="Тип коек" horizontalFill="true" fieldColSpan="1" />
        </msh:row>
        <msh:row>
        	<msh:textField property="filterAdd" label="Фильтр МКБ напр.СЛС" fieldColSpan="3" horizontalFill="true"/>
        	<td><i>Пример: <b>A0</b>, <b>N</b>, <b>A00-B87</b></i></td>
        </msh:row>
        <msh:row>
        	<msh:textField property="filterAdd1" label="Фильтр МКБ по СЛО клин. осн." fieldColSpan="3" horizontalFill="true"/>
        	<td><i>Пример: <b>A0</b>, <b>N</b>, <b>A00-B87</b></i></td>
        </msh:row>
        <msh:row>
        	<msh:textField property="filterAdd2" label="Фильтр МКБ выпис. СЛС" fieldColSpan="3" horizontalFill="true"/>
        	<td><i>Пример: <b>A0</b>, <b>N</b>, <b>A00-B87</b></i></td>
        </msh:row>
        <msh:row>
	        <msh:textField property="dateBegin" label="Период с" />
	        <msh:textField property="dateEnd" label="по" />
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
        String login = LoginInfo.find(request.getSession(true)).getUsername() ;
        request.setAttribute("login", login) ;
    	%>
  <ecom:webQuery name="diag_typeReg_cl_sql" nativeSql="select id,name from VocDiagnosisRegistrationType where code='4'"/>
  <ecom:webQuery name="diag_typeReg_ord_sql" nativeSql="select id,name from VocDiagnosisRegistrationType where code='2'"/>
  <ecom:webQuery name="diag_typeReg_disch_sql" nativeSql="select id,name from VocDiagnosisRegistrationType where code='3'"/>
  <ecom:webQuery name="diag_priority_m_sql" nativeSql="select id,name from VocPriorityDiagnosis where code='1'"/>
  <ecom:webQuery name="diag_priority_conclud_sql" nativeSql="select id,name from VocPriorityDiagnosis where code='3'"/>
        <ecom:webQuery name="infoByLogin"
        maxResult="1" nativeSql="
        select wf.id,w.lpu_id,case when wf.isAdministrator='1' then '1' else null end as isAdmin
        from SecUser su
        left join WorkFunction wf on su.id=wf.secUSer_id
        left join Worker w on wf.worker_id=w.id
        
        where su.login='${login}'
        "
        />
    	
    	<%
      	ActionUtil.getValueByList("diag_typeReg_cl_sql", "diag_typeReg_cl", request) ;
      	ActionUtil.getValueByList("diag_typeReg_ord_sql", "diag_typeReg_ord", request) ;
      	ActionUtil.getValueByList("diag_typeReg_disch_sql", "diag_typeReg_disch", request) ;
      	ActionUtil.getValueByList("diag_priority_m_sql", "diag_priority_m", request) ;
      	ActionUtil.getValueByList("diag_priority_conclud_sql", "diag_priority_conclud", request) ;
    	
    	//String typeStatus = (String)request.getParameter("typeStatus") ;
    	//String typeMKB = (String)request.getAttribute("typeMKB") ;
    	//String typeDate = (String)request.getAttribute("typeDate") ;
    	String fldDate = "sls.dateFinish" ;
    	if (typeDate!=null) {
    		if (typeDate.equals("1")) {fldDate="sls.dateStart" ;} 
    	} 
    	request.setAttribute("fldDate",fldDate) ;
    	
    	boolean isStat = true ;
    	if (typeStatus!=null && typeStatus.equals("2")) {
    		isStat = false ;
    	}

    	String dep = (String)request.getParameter("department") ;
        boolean isViewAllDepartment=RolesHelper.checkRoles("/Policy/Mis/MedCase/Stac/Journal/ShowInfoAllDepartments",request) ;
        if (!isViewAllDepartment) {
        	List list= (List)request.getAttribute("infoByLogin");
	        WebQueryResult wqr = list.size()>0?(WebQueryResult)list.get(0):null ;
	        dep=""+wqr.get2() ;
        }
    	if (dep!=null && !dep.equals("") && !dep.equals("0")) {
    		if (typeDate!=null && typeDate.equals("2")) {
        		request.setAttribute("departmentSql", " and slo.department_id="+dep) ;
        		request.setAttribute("department",dep) ;
    		} else {
        		request.setAttribute("departmentSql", " and sls.department_id="+dep) ;
        		request.setAttribute("department",dep) ;
    		}
    		ActionUtil.getValueBySql("select id,name from mislpu where id="+dep, "depId", "infoDepartment", request);
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
    	String bedSubType = (String)request.getParameter("bedSubType") ;
    	if (bedSubType!=null && !bedSubType.equals("") && !bedSubType.equals("0")) {
    		request.setAttribute("bedSubTypeSql", " and bf.bedSubType_id="+bedSubType) ;
    	} 
    	ActionUtil.setParameterFilterSql("bedType","bf.bedType_id", request) ;
    	String filter = (String)request.getParameter("filterAdd") ;
    	if (filter!=null && !filter.equals("")) {
    		filter = filter.toUpperCase() ;
    		request.setAttribute("filterAdd",filter) ;
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
    		filtOr.insert(0, " and (").append(")") ;
    		filtOr.append(" and vdrt.id='").append(request.getAttribute("diag_typeReg_ord")).append("'") ;
    		request.setAttribute("filterSql", filtOr.toString()) ;
    	}
    	String addD = "1" ;
    	filter = (String)request.getParameter("filterAdd"+addD) ;
    	String addD1="1" ;
    	if (filter!=null && !filter.equals("")) {
    		filter = filter.toUpperCase() ;
    		request.setAttribute("filterAdd"+addD,filter) ;
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
		    			filtOr.append(" mkb"+addD1+".code between '"+fs[0].trim()+"' and '"+fsE1+"'") ;
		    		} else {
		    			filtOr.append(" substring(mkb"+addD1+".code,1,"+filt1.length()+")='"+filt1+"'") ;
		    		}
    			}
    		}
    		filtOr.insert(0, " and (").append(")") ;
    		filtOr.append(" and vdrt").append(addD1).append(".id='").append(request.getAttribute("diag_typeReg_cl")).append("' and vpd").append(addD1).append(".id='").append(request.getAttribute("diag_priority_m")).append("'") ;
    		request.setAttribute("filter"+addD+"Sql", filtOr.toString()) ;
    	}
    	addD = "2" ;
    	filter = (String)request.getParameter("filterAdd"+addD) ;
    	addD1="2" ;
    	if (filter!=null && !filter.equals("")) {
    		filter = filter.toUpperCase() ;
    		request.setAttribute("filterAdd"+addD,filter) ;
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
		    			filtOr.append(" mkb"+addD1+".code between '"+fs[0].trim()+"' and '"+fsE1+"'") ;
		    		} else {
		    			filtOr.append(" substring(mkb"+addD1+".code,1,"+filt1.length()+")='"+filt1+"'") ;
		    		}
    			}
    		}
    		filtOr.insert(0, " and (").append(")") ;
    		filtOr.append(" and vdrt").append(addD1).append(".id='").append(request.getAttribute("diag_typeReg_disch")).append("' and (vpd").append(addD1).append(".id='").append(request.getAttribute("diag_priority_m")).append("' or vpd").append(addD1).append(".id='").append(request.getAttribute("diag_priority_conclud")).append("')") ;
    		request.setAttribute("filter"+addD+"Sql", filtOr.toString()) ;
    	}
    	
    	
    	if (typeEmergency!=null && typeEmergency.equals("1")) {
    		request.setAttribute("emergencySql", " and sls.emergency='1' ") ;
    	} else if (typeEmergency!=null && typeEmergency.equals("2")) {
    		request.setAttribute("emergencySql", " and (sls.emergency is null or sls.emergency='0') ") ;
    	} 
    	
    		%>
    		
	<ecom:webQuery name="datelist" nameFldSql="datelist_sql" nativeSql="
	select sls.id,sls.dateStart,sls.dateFinish
	,pat.lastname ||' ' ||pat.firstname|| ' ' || pat.middlename as fio
	,pat.birthday,sc.code,vh.name as vhname 
	,coalesce(a.fullname)||' ' || case when pat.houseNumber is not null and pat.houseNumber!='' then ' д.'||pat.houseNumber else '' end 
	 ||case when pat.houseBuilding is not null and pat.houseBuilding!='' then ' корп.'|| pat.houseBuilding else '' end 
	||case when pat.flatNumber is not null and pat.flatNumber!='' then ' кв. '|| pat.flatNumber else '' end as address
	,cast( (cast(to_char(${fldDate},'yyyy') as int)-cast(to_char(pat.birthday,'yyyy') as int)
+case when (cast(to_char(${fldDate}, 'mm') as int)-cast(to_char(pat.birthday, 'mm') as int)) <0 then -1 when (cast(to_char(${fldDate},'dd') as int) - cast(to_char(pat.birthday,'dd') as int)<0) and ((cast(to_char(${fldDate}, 'mm') as int)-cast(to_char(pat.birthday, 'mm') as int)-1)<0)  then -1 else 0 end
) as int) as age,
	  case 
			when (coalesce(sls.dateFinish,CURRENT_DATE)-sls.dateStart)=0 then 1 
			when bf.addCaseDuration='1' then ((coalesce(sls.dateFinish,CURRENT_DATE)-sls.dateStart)+1) 
			else (coalesce(sls.dateFinish,CURRENT_DATE)-sls.dateStart)
	 end as cntDays
	 ,list(distinct case when vdrt.id='${diag_typeReg_ord}' then mkb.code else null end) as mkbcode
	 ,list(distinct case when vdrt1.id='${diag_typeReg_cl}' and vpd1.id='${diag_priority_m}' then mkb1.code else null end) as mkb1code
	 ,list(distinct case when vdrt2.id='${diag_typeReg_disch}' and vpd2.id='${diag_priority_m}' then mkb2.code else null end) as mkb2code
	 ,ml.name as mlEntrance,ml1.name as mlDischarge,vr.name as vrname ,vhr.name as vhrname,vs.name as vsname
	 ,list(distinct case when vdrt2.id='${diag_typeReg_disch}' and vpd2.id='${diag_priority_conclud}' then mkb2.code else null end) as mkb2codeconclud
	from MedCase as sls
	left join Mislpu ml on ml.id=sls.department_id
	left join VocHospitalizationResult vhr on vhr.id=sls.result_id
	left join Patient pat on sls.patient_id = pat.id
	left join VocSex vs on vs.id=pat.sex_id
	left join VocRayon vr on vr.id=pat.rayon_id 
	left join Diagnosis diag on sls.id=diag.medCase_id
	left join Diagnosis diag2 on sls.id=diag2.medCase_id
	left join medCase slo on sls.id = slo.parent_id
	left join Mislpu ml1 on ml1.id=slo.department_id
	
	left join medCase sloa on sls.id = sloa.parent_id
	left join BedFund bf on bf.id=slo.bedFund_id
	left join StatisticStub as sc on sc.medCase_id=sls.id 
	left join Diagnosis diag1 on sloa.id=diag1.medCase_id
	left join VocIdc10 mkb on mkb.id=diag.idc10_id
	left join VocDiagnosisRegistrationType vdrt on vdrt.id=diag.registrationType_id
	left join VocPriorityDiagnosis vpd on vpd.id=diag.priority_id
	
	left join VocIdc10 mkb1 on mkb1.id=diag1.idc10_id
	left join VocDiagnosisRegistrationType vdrt1 on vdrt1.id=diag1.registrationType_id
	left join VocPriorityDiagnosis vpd1 on vpd1.id=diag1.priority_id
	
	left join VocIdc10 mkb2 on mkb2.id=diag2.idc10_id
	left join VocDiagnosisRegistrationType vdrt2 on vdrt2.id=diag2.registrationType_id
	left join VocPriorityDiagnosis vpd2 on vpd2.id=diag2.priority_id
	
	left join VocServiceStream vss on vss.id=sls.serviceStream_id
	left join VocHospitalization vh on vh.id=sls.hospitalization_id
	left join Address2 a on a.addressid=pat.address_addressId
	where sls.dtype='HospitalMedCase' and ${fldDate} between to_date('${param.dateBegin}','dd.mm.yyyy')
			and to_date('${dateEnd}','dd.mm.yyyy') 
			and slo.dtype='DepartmentMedCase'
			and sloa.dtype='DepartmentMedCase'
			and slo.dateFinish is not null
			${serviceStreamSql} ${departmentSql} ${bedSubTypeSql}
			${emergencySql} ${bedTypeSql}
			${mkbCodeSql}
			${filterSql}
			${filter1Sql}
			${filter2Sql}
	group by sls.id,sls.dateStart,sls.dateFinish
		,pat.lastname,pat.firstname,pat.middlename
				,pat.birthday,sc.code,vh.name,a.fullname
		,pat.houseBuilding,pat.houseNumber
		,pat.flatNumber,sls.dateFinish,sls.dateStart,bf.addCaseDuration,ml.name,ml1.name
		,vr.name,vhr.name,vs.name
	order by pat.lastname,pat.firstname,pat.middlename
	
	" 
	/>
	 <form action="print-stac_diagnosis_hospital.do" method="post" target="_blank">
    <input type='hidden' name="sqlText" id="sqlText" value="${datelist_sql}"> 
    <input type='hidden' name="sqlInfo" id="sqlInfo" value="Период с ${param.dateBegin} по ${dateEnd}">
    <input type='hidden' name="s" id="s" value="PrintService">
    <input type='hidden' name="m" id="m" value="printNativeQuery">
    <input type="submit" value="Печать"> 
    </form>
	${infoDepartment}
    <msh:table name="datelist" action="entitySubclassView-mis_medCase.do" idField="1">
      <msh:tableColumn property="sn" columnName="#"/>
      <msh:tableColumn columnName="Стат.карта" property="6" />
      <msh:tableColumn columnName="Фамилия имя отчество пациента" property="4" />
      <msh:tableColumn columnName="Дата рождения" property="5" />
      <msh:tableColumn columnName="Пол" property="18" />
      <msh:tableColumn columnName="Возраст" property="9" />
      <msh:tableColumn columnName="Район" property="16" />
      
      <msh:tableColumn columnName="Дата поступления" property="2" />
      <msh:tableColumn columnName="Дата выписки" property="3" />
      <msh:tableColumn columnName="Госпитализация" property="7" />
      <msh:tableColumn columnName="Адрес" role="/Policy/Mis/Journal/ViewInfoPatient" property="8" />
      <msh:tableColumn columnName="Кол-во к.дней" isCalcAmount="true" property="10" />
      <msh:tableColumn columnName="Диагноз направ. СЛС" property="11" />
      <msh:tableColumn columnName="Диагноз клин СЛО" property="12" />
      <msh:tableColumn columnName="Диагноз выпис. СЛС" property="13" />
      <msh:tableColumn columnName="Диагноз выпис. СЛС сопут." property="19" />
      
      <msh:tableColumn columnName="Отделение пост." property="14" />
      <msh:tableColumn columnName="Отделение выписки" property="15" />
      <msh:tableColumn columnName="Исход" property="17" />
    </msh:table>
    	
    		
    		<%

    	} else { %>
    	<i>Выберите параметры и нажмите найти </i>
    <% }   %>
    
    <script type='text/javascript'>
    //var typePatient = document.forms[0].typePatient ;
    //var typeDate = document.forms[0].typeDate ;
    //checkFieldUpdate('typeSwod','${typeSwod}',2,1) ;
    checkFieldUpdate('typeDate','${typeDate}',2) ;
    //checkFieldUpdate('typeMKB','${typeMKB}',4) ;
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
    	frm.action='stac_diagnosis_hospital.do' ;
    }
    function print() {
    	var frm = document.forms[0] ;
    	frm.target='_blank' ;
    	frm.action='print-stac_journalDiag.do' ;
    }
	eventutil.addEventListener($('filterAdd'), eventutil.EVENT_KEY_UP, 
  		  	function() {$('filterAdd').value = latRus($('filterAdd').value) ;}
		) ;
	eventutil.addEventListener($('filterAdd1'), eventutil.EVENT_KEY_UP, 
  		  	function() {$('filterAdd1').value = latRus($('filterAdd1').value) ;}
		) ;
	eventutil.addEventListener($('filterAdd2'), eventutil.EVENT_KEY_UP, 
  		  	function() {$('filterAdd2').value = latRus($('filterAdd2').value) ;}
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