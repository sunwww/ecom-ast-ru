<%@page import="ru.nuzmsh.util.query.ReportParamUtil"%>
<%@page import="ru.ecom.web.util.ActionUtil"%>
<%@page import="java.text.SimpleDateFormat"%>
<%@page import="java.util.Calendar"%>
<%@page import="ru.nuzmsh.util.format.DateFormat"%>
<%@page import="java.util.Date"%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://struts.apache.org/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://www.nuzmsh.ru/tags/msh" prefix="msh" %>
<%@ taglib uri="http://www.ecom-ast.ru/tags/ecom" prefix="ecom" %>
<%@ taglib tagdir="/WEB-INF/tags" prefix="tags" %>

<tiles:insert page="/WEB-INF/tiles/main${param.short}Layout.jsp" flush="true" >

  <tiles:put name="title" type="string">
    <msh:title mainMenu="Journal">Разбивка по страховым компаниям СЛС</msh:title>
  </tiles:put>
  <tiles:put name="side" type="string">
  	<tags:stac_journal currentAction="stac_report_insCompany"/>
  </tiles:put>
  <tiles:put name="body" type="string">
    <%
  	String noViewForm = request.getParameter("short") ;
	//String typeDate =ActionUtil.updateParameter("ReestrByInsCompany","typeDate","1", request) ;
	//String typeEmergency =ActionUtil.updateParameter("ReestrByInsCompany","typeEmergency","3", request) ;
	String typeView =ActionUtil.updateParameter("ReestrByInsCompany","typeView","1", request) ;
	if (noViewForm==null) {
  	%>
    <msh:form action="/stac_report_insCompany.do" defaultField="dateBegin" disableFormDataConfirm="true" method="GET">
      <msh:panel>
      <%--
      <msh:row>
        <msh:separator label="Параметры поиска" colSpan="7" />
      </msh:row>
      <msh:row>
      	<msh:autoComplete property="pigeonHole" fieldColSpan="3" 
      		horizontalFill="true" label="Приемник"
      		vocName="vocPigeonHole"
      		/>
      </msh:row>
      <msh:row>
        <td class="label" title="Поиск по показаниям поступления (typeEmergency)" colspan="1"><label for="typeEmergencyName" id="typeEmergencyLabel">Показания:</label></td>
        <td onclick="this.childNodes[1].checked='checked';">
        	<input type="radio" name="typeEmergency" value="1">  экстренные
        </td>
        <td onclick="this.childNodes[1].checked='checked';">
        	<input type="radio" name="typeEmergency" value="2" >  плановые
        </td>
        <td onclick="this.childNodes[1].checked='checked';">
        	<input type="radio" name="typeEmergency" value="3">  все
        </td>
      </msh:row>
      
      <msh:row>
        <td class="label" title="Поиск по пациентам (typePatient)" colspan="1"><label for="typePatientName" id="typePatientLabel">Пациенты:</label></td>
        <td onclick="this.childNodes[1].checked='checked';">
        	<input type="radio" name="typePatient" value="1">  региональные
        </td>
        <td onclick="this.childNodes[1].checked='checked';">
        	<input type="radio" name="typePatient" value="2">  прожив. в др.регионах
        </td>
        <td onclick="this.childNodes[1].checked='checked';">
        	<input type="radio" name="typePatient" value="3">  иностранцы
        </td>
        <td onclick="this.childNodes[1].checked='checked';">
        	<input type="radio" name="typePatient" value="4">  все
        </td>
        </msh:row>      
 --%>
   
        <msh:row>
	        <td class="label" title="Просмотр данных (typeView)" colspan="1"><label for="typeViewName" id="typeViewLabel">Отобразить:</label></td>
	        <td onclick="this.childNodes[1].checked='checked';">
	        	<input type="radio" name="typeView" value="1"  >  поступившие
	        </td>
	        <td onclick="this.childNodes[1].checked='checked';">
	        	<input type="radio" name="typeView" value="2"  >  поступившие по отделениям
	        </td>
	        <td onclick="this.childNodes[1].checked='checked';">
	        	<input type="radio" name="typeView" value="3"  >  выписанные
	        </td>
	        <td onclick="this.childNodes[1].checked='checked';">
	        	<input type="radio" name="typeView" value="4"  >  выписанные по отделениям
	        </td>
	        <td onclick="this.childNodes[1].checked='checked';">
	        	<input type="radio" name="typeView" value="5"  >  отказы от госпитализаций
	        </td>
	        <td onclick="this.childNodes[1].checked='checked';">
	        	<input type="radio" name="typeView" value="6"  >  свод по причинам отказов от госпитализаций
	        </td>
        </msh:row>
        <msh:row>
        <msh:textField fieldColSpan="1" property="dateBegin" label="Дата c"/>
        <msh:textField fieldColSpan="1" property="dateEnd" label="по"/>
      </msh:row>
        <msh:row>
       		<msh:autoComplete property="hospType" fieldColSpan="4" horizontalFill="true" label="Тип стационара" vocName="vocHospType"/>
        </msh:row>      
      <msh:row>
        <msh:autoComplete property="serviceStream" fieldColSpan="4"
        	label="Поток обслуживания" horizontalFill="true" vocName="vocServiceStream"/>
      </msh:row>
      <msh:row>
        <msh:autoComplete property="department" fieldColSpan="4" horizontalFill="true" label="Отделение" vocName="vocLpuHospOtdAll"/>
      </msh:row>
      <msh:row>
      	<msh:submitCancelButtonsRow colSpan="4"/>
      </msh:row>
    </msh:panel>
    </msh:form>
       <script type='text/javascript'>
    
    //checkFieldUpdate('typePatient','${typePatient}',4) ;
    //checkFieldUpdate('typeEmergency','${typeEmergency}',3) ;
    checkFieldUpdate('typeView','${typeView}',1) ;
    //checkFieldUpdate('typeDepartment','${typeDepartment}',1) ;
    
  
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
			 

    if ($('dateBegin').value=="") {
    	$('dateBegin').value=getCurrentDate() ;
    }

			 
    </script>
        <%} %>
    
    <%
    String date = request.getParameter("dateBegin") ;
    
    if (date!=null && !date.equals(""))  {
    	String dateEnd = (String)request.getParameter("dateEnd") ;
    	if (dateEnd==null||dateEnd.equals("")) {
    		dateEnd=date;
    		request.setAttribute("dateEnd", date) ;
    	} else {
    		request.setAttribute("dateEnd", dateEnd) ;
    	}
    	request.setAttribute("dateBegin",date) ;
    	String view = (String)request.getAttribute("typeView") ;
    	/*ActionUtil.setParameterFilterSql("department", "mc.department_id", request) ;
    	ActionUtil.setParameterFilterSql("department","departmentDischarge", "sloD.department_id", request) ;
        ActionUtil.setParameterFilterSql("serviceStream", "mc.serviceStream_id", request) ;*/
    	boolean isReestr = request.getParameter("typeReestr")!=null?true:false ;
    	StringBuilder paramSql= new StringBuilder() ;
    	StringBuilder paramHref= new StringBuilder() ;
    	paramHref.append("&typeReestr=1") ;
    	paramHref.append("&dateBegin=").append(date) ;
    	paramHref.append("&dateEnd=").append(dateEnd) ;
    	paramHref.append("&hospType=").append(request.getParameter("hospType")!=null?request.getParameter("hospType"):"") ;
      	paramHref.append("&serviceStream=").append(request.getParameter("serviceStream")!=null?request.getParameter("serviceStream"):"") ;
      	
    	paramSql.append(" ").append(ActionUtil.setParameterFilterSql("department", "ml.id", request)) ;
      	paramSql.append(" ").append(ActionUtil.setParameterFilterSql("hospType", "mc.hospType_id", request)) ;
      	paramSql.append(" ").append(ActionUtil.setParameterFilterSql("serviceStream", "mc.serviceStream_id", request)) ;
      	request.setAttribute("paramSql", paramSql.toString()) ;
      	request.setAttribute("paramHref", paramHref.toString()) ;
      	String insComp = request.getParameter("insCompany") ;
      	if (insComp==null || insComp.equals("-1")) {
      		request.setAttribute("insCompSql", "and ri.id is null "+ActionUtil.setParameterFilterSql("dtype", "mp.dtype", request)) ;
      	} else {
      		request.setAttribute("insCompSql", "and ri.id="+insComp+" "+ActionUtil.setParameterFilterSql("dtype", "mp.dtype", request)) ;
      	}
      	
    	if (view!=null && (view.equals("1"))) {
    		if (isReestr) {
    			%>
    <msh:section>
    <msh:sectionTitle>Реестр поступивших ${dateInfo} за день ${param.dateBegin}. 
    </msh:sectionTitle>
    <msh:sectionContent>
    <ecom:webQuery name="swod" nameFldSql="swod_sql" nativeSql=" 
select mc.id as slsid
    ,ss.code as sscode
    ,pat.lastname||' '||pat.firstname||' '||coalesce(pat.middlename) as fio
    ,to_char(pat.birthday,'dd.mm.yyyy') as patbirthday
    ,to_char(mc.datestart,'dd.mm.yyyy') as mcdatestratt
    ,to_char(mc.datefinish,'dd.mm.yyyy') as mcdatefinish
    ,case when mc.emergency='1' then 'Экстренно' else 'Планово' end  as cntmcEmergency
    ,ml.name as mlname
    ,list(''||mp.polnumber||' '||ri.name) as riname
    from medcase mc
left join statisticstub ss on ss.id=mc.statisticStub_id
left join patient pat on pat.id=mc.patient_id
left join medcase_medpolicy mcmp on mcmp.medcase_id=mc.id
left join medpolicy mp on mp.id=mcmp.policies_id
left join reg_ic ri on ri.id=mp.company_id
left join vocservicestream vss on vss.id=mc.serviceStream_id
left join mislpu ml on ml.id=mc.department_id

where mc.dtype='HospitalMedCase' 
and mc.datestart between to_date('${dateBegin}','dd.mm.yyyy') and to_date('${dateEnd}','dd.mm.yyyy')
and mc.deniedhospitalizating_id is null  ${paramSql}  ${insCompSql}
group by mc.id,ss.code
    ,pat.lastname,pat.firstname,pat.middlename
    ,pat.birthday,mc.datestart
    ,mc.datefinish,mc.emergency,ml.name
order by pat.lastname,pat.middlename,pat.middlename
      " />
    <msh:table name="swod" viewUrl="entitySubclassShortView-mis_medCase.do" action="entitySubclassView-mis_medCase.do" idField="1">
      <msh:tableColumn columnName="№" property="sn" />
      <msh:tableColumn columnName="Полис" property="9" />
      <msh:tableColumn columnName="№СК" property="2" />
      <msh:tableColumn columnName="ФИО пациента" property="3" />
      <msh:tableColumn columnName="Дата рождения" property="4" />
      <msh:tableColumn columnName="Дата поступления" property="5" />
      <msh:tableColumn columnName="Дата выписки" property="6" />
      <msh:tableColumn columnName="Показания" property="7" />
      <msh:tableColumn columnName="Отделение пост." property="8" />
    </msh:table>
    </msh:sectionContent>
    </msh:section>
    			<%
    		} else {
    		//свод поступивших
    	%>
    
    <msh:section>
    <msh:sectionTitle>Свод поступивших ${dateInfo} за день ${param.dateBegin}. 
    </msh:sectionTitle>
    <msh:sectionContent>
    <ecom:webQuery name="swod" nameFldSql="swod_sql" nativeSql=" 
    select '&insCompany='||coalesce(ri.id,'-1')||coalesce('&dtype='||mp.dtype,'') as dtype
    ,case when mp.dtype='MedPolicyOmc' then 'ОМС' when mp.dtype='MedPolicyOmcForeign' then 'ОМС иногороднего' when mp.dtype is null then 'нет' else 'ДМС' end as dtypeName
    ,ri.name as riname
    ,count(distinct mc.patient_id) as cntpatient
    ,count(distinct mc.id)  as cntmc
    ,count(distinct case when mc.emergency='1' then mc.id else null end)  as cntmcEmergency
    from medcase mc
left join medcase_medpolicy mcmp on mcmp.medcase_id=mc.id
left join medpolicy mp on mp.id=mcmp.policies_id
left join reg_ic ri on ri.id=mp.company_id
left join vocservicestream vss on vss.id=mc.serviceStream_id
left join mislpu ml on ml.id=mc.department_id

where mc.dtype='HospitalMedCase' 
and mc.datestart between to_date('${dateBegin}','dd.mm.yyyy') and to_date('${dateEnd}','dd.mm.yyyy')
and mc.deniedhospitalizating_id is null  ${paramSql} 
group by mp.dtype,ri.name,ri.id
order by ri.name
      " />
    <msh:table name="swod" action="stac_report_insCompany.do?typeView=1&department=${param.department}${paramHref}" 
    viewUrl="stac_report_insCompany.do?short=Short&typeView=1&department=${param.department}${paramHref}"
    idField="1">
      <msh:tableColumn columnName="Тип полиса" property="2" />
      <msh:tableColumn columnName="Страховая компания" property="3" />
      <msh:tableColumn columnName="Кол-во пациентов" property="4" />
      <msh:tableColumn columnName="Кол-во случаев" property="5" />
      <msh:tableColumn columnName="из них экстренных" property="6" />
    </msh:table>
    </msh:sectionContent>
    </msh:section>
    <%
    		}
    	} else if (view!=null && (view.equals("2"))) {
    		
    		if (isReestr) {
    			%><%
    		} else {
    		%>
    		    <msh:section>
    <msh:sectionTitle>Свод поступивших по отделениям ${dateInfo} за день ${param.dateBegin}. По ${dischInfo}  ${emerInfo} ${hourInfo}
    </msh:sectionTitle>
    <msh:sectionContent>
    <ecom:webQuery name="swod" nameFldSql="journal_priem_sql" nativeSql="
    select '&insCompany='||coalesce(ri.id,'-1')||'&department='||ml.id||'&dtype='||mp.dtype as mcdtype
    ,case when mp.dtype='MedPolicyOmc' then 'ОМС' when mp.dtype='MedPolicyOmcForeign' then 'ОМС иногороднего' when mp.dtype is null then 'нет' else 'ДМС' end as dtypeName
    ,ri.name as riname
    ,ml.name as mlname
    ,count(distinct mc.patient_id) as cntpatient
    ,count(distinct mc.id)  as cntmc
    ,count(distinct case when mc.emergency='1' then mc.id else null end)  as cntmcEmergency
    from medcase mc
left join mislpu ml on ml.id=mc.department_id
left join medcase_medpolicy mcmp on mcmp.medcase_id=mc.id
left join medpolicy mp on mp.id=mcmp.policies_id
left join reg_ic ri on ri.id=mp.company_id
left join vocservicestream vss on vss.id=mc.serviceStream_id
where mc.dtype='HospitalMedCase' 
and mc.datestart between to_date('${dateBegin}','dd.mm.yyyy') and to_date('${dateEnd}','dd.mm.yyyy')
and mc.deniedhospitalizating_id is null  ${paramSql} 
group by mp.dtype,ri.name,ri.id,ml.name,ml.id
order by ri.name,ml.name

      " />
    <msh:table name="swod" action="stac_report_insCompany.do?typeView=1${paramHref}" 
    viewUrl="stac_report_insCompany.do?short=Short&typeView=1${paramHref}"
    idField="1">
      <msh:tableColumn columnName="Тип полиса" property="2" />
      <msh:tableColumn columnName="Страховая компания" property="3" />
      <msh:tableColumn columnName="Отделение" property="4" />
      <msh:tableColumn columnName="Кол-во пациентов" property="5" />
      <msh:tableColumn columnName="Кол-во случаев" property="6" />
      <msh:tableColumn columnName="из них экстренных" property="7" />

    </msh:table>
    </msh:sectionContent>
    </msh:section>
    <%
    		}
    		} else if (view!=null && (view.equals("3"))) {
    			if (isReestr) {
        			%>    <msh:section>
    <msh:sectionTitle>Реестр выписанных ${dateInfo} за день ${param.dateBegin}. 
    </msh:sectionTitle>
    <msh:sectionContent>
    <ecom:webQuery name="swod" nameFldSql="swod_sql" nativeSql=" 
select mc.id as slsid
    ,ss.code as sscode
    ,pat.lastname||' '||pat.firstname||' '||coalesce(pat.middlename) as fio
    ,to_char(pat.birthday,'dd.mm.yyyy') as patbirthday
    ,to_char(mc.datestart,'dd.mm.yyyy') as mcdatestratt
    ,to_char(mc.datefinish,'dd.mm.yyyy') as mcdatefinish
    ,case when mc.emergency='1' then 'Экстренно' else 'Планово' end  as cntmcEmergency
    ,ml.name as mlname
    ,list(''||mp.polnumber||' '||ri.name) as riname
    ,ml1.name as ml1name
    from medcase mc
left join statisticstub ss on ss.id=mc.statisticStub_id
left join patient pat on pat.id=mc.patient_id
left join medcase_medpolicy mcmp on mcmp.medcase_id=mc.id
left join medpolicy mp on mp.id=mcmp.policies_id
left join reg_ic ri on ri.id=mp.company_id
left join vocservicestream vss on vss.id=mc.serviceStream_id
left join MedCase sloD on sloD.parent_id=mc.id and sloD.dateFinish is not null
left join mislpu ml on ml.id=sloD.department_id
left join mislpu ml1 on ml1.id=mc.department_id
where mc.dtype='HospitalMedCase' 
and mc.datefinish between to_date('${dateBegin}','dd.mm.yyyy') and to_date('${dateEnd}','dd.mm.yyyy')
and mc.deniedhospitalizating_id is null  
and sloD.dtype='DepartmentMedCase'
${paramSql}  ${insCompSql}
group by mc.id,ss.code
    ,pat.lastname,pat.firstname,pat.middlename
    ,pat.birthday,mc.datestart
    ,mc.datefinish,mc.emergency,ml.name,ml1.name
order by pat.lastname,pat.middlename,pat.middlename
      " />
    <msh:table name="swod" viewUrl="entitySubclassShortView-mis_medCase.do" action="entitySubclassView-mis_medCase.do" idField="1">
      <msh:tableColumn columnName="№" property="sn" />
      <msh:tableColumn columnName="Полис" property="9" />
      <msh:tableColumn columnName="№СК" property="2" />
      <msh:tableColumn columnName="ФИО пациента" property="3" />
      <msh:tableColumn columnName="Дата рождения" property="4" />
      <msh:tableColumn columnName="Дата поступления" property="5" />
      <msh:tableColumn columnName="Дата выписки" property="6" />
      <msh:tableColumn columnName="Показания" property="7" />
      <msh:tableColumn columnName="Отделение поступления" property="10" />
      <msh:tableColumn columnName="Отделение выписки" property="8" />
    </msh:table>
    </msh:sectionContent>
    </msh:section><%
        		} else {
    		// свод выписанных
    		%>
    		    <msh:section>
    <msh:sectionTitle>Свод выписанных ${dateInfo} за день ${param.dateBegin}. По ${dischInfo}  ${emerInfo} ${hourInfo}
    </msh:sectionTitle>
    <msh:sectionContent>
    <ecom:webQuery name="swod" nameFldSql="journal_priem_sql" nativeSql="select
     '&insCompany='||coalesce(ri.id,'-1')||'&dtype='||mp.dtype as mcdtype 
    ,case when mp.dtype='MedPolicyOmc' then 'ОМС' when mp.dtype='MedPolicyOmcForeign' then 'ОМС иногороднего' when mp.dtype is null then 'нет' else 'ДМС' end as dtypeName
     ,ri.name as riname
     ,count(mc.patient_id) as cntpatient
     ,count(mc.id) as cntMc
    ,count(distinct case when mc.emergency='1' then mc.id else null end)  as cntmcEmergency
     from medcase mc
left join medcase_medpolicy mcmp on mcmp.medcase_id=mc.id
left join medpolicy mp on mp.id=mcmp.policies_id
left join reg_ic ri on ri.id=mp.company_id
left join vocservicestream vss on vss.id=mc.serviceStream_id
left join MedCase sloD on sloD.parent_id=mc.id and sloD.dateFinish is not null
left join mislpu ml on ml.id=sloD.department_id
where mc.dtype='HospitalMedCase' and mc.datefinish between to_date('${dateBegin}','dd.mm.yyyy') and to_date('${dateEnd}','dd.mm.yyyy')
and mc.deniedhospitalizating_id is null  ${paramSql} 
and sloD.dtype='DepartmentMedCase'
group by mp.dtype,ri.name,ri.id
order by ri.name

      " />
    <msh:table name="swod" action="stac_report_insCompany.do?typeView=3&department=${param.department}${paramHref}" 
    viewUrl="stac_report_insCompany.do?short=Short&typeView=3&department=${param.department}${paramHref}"
    idField="1">
      <msh:tableColumn columnName="Тип полиса" property="2" />
      <msh:tableColumn columnName="Страховая компания" property="3" />
      <msh:tableColumn columnName="Кол-во пациентов" property="4" />
      <msh:tableColumn columnName="Кол-во случаев" property="5" />
      <msh:tableColumn columnName="из них экстренных" property="6" />
    </msh:table>
    </msh:sectionContent>
    </msh:section>
    <%
        		}
    	} else if (view!=null && (view.equals("4"))) {
    		if (isReestr) {
    			%><%
    		} else {
    		// свод выписанных
    		%>
    		    <msh:section>
    <msh:sectionTitle>Свод выписанных по отделениям ${dateInfo} за день ${param.dateBegin}. По ${dischInfo}  ${emerInfo} ${hourInfo}
    </msh:sectionTitle>
    <msh:sectionContent>
    <ecom:webQuery name="swod" nameFldSql="journal_priem_sql" nativeSql="select
     '&insCompany='||coalesce(ri.id,'-1')||'&department='||ml.id||'&dtype='||mp.dtype as riid
    ,case when mp.dtype='MedPolicyOmc' then 'ОМС' when mp.dtype='MedPolicyOmcForeign' then 'ОМС иногороднего' when mp.dtype is null then 'нет' else 'ДМС' end as dtypeName
     ,ri.name as riname
     ,ml.name as mlname
     ,count(mc.patient_id) as cntpatient
     ,count(mc.id) as cntMc
    ,count(distinct case when mc.emergency='1' then mc.id else null end)  as cntmcEmergency
     from medcase mc
left join medcase_medpolicy mcmp on mcmp.medcase_id=mc.id
left join medpolicy mp on mp.id=mcmp.policies_id
left join reg_ic ri on ri.id=mp.company_id
left join vocservicestream vss on vss.id=mc.serviceStream_id
left join MedCase sloD on sloD.parent_id=mc.id and sloD.dateFinish is not null
left join mislpu ml on ml.id=sloD.department_id
where mc.dtype='HospitalMedCase' and mc.datefinish between to_date('${dateBegin}','dd.mm.yyyy') and to_date('${dateEnd}','dd.mm.yyyy')
and mc.deniedhospitalizating_id is null  ${paramSql} 
and sloD.dtype='DepartmentMedCase'
group by mp.dtype,ri.name,ri.id,ml.name,ml.id
order by ri.name

      " />
    <msh:table name="swod" action="stac_report_insCompany.do?typeView=3${paramHref}"
    viewUrl="stac_report_insCompany.do?short=Short&typeView=3${paramHref}"
     idField="1">
      <msh:tableColumn columnName="Тип полиса" property="2" />
      <msh:tableColumn columnName="Страховая компания" property="3" />
      <msh:tableColumn columnName="Отделение" property="4" />
      <msh:tableColumn columnName="Кол-во пациентов" property="5" />
      <msh:tableColumn columnName="Кол-во случаев" property="6" />
      <msh:tableColumn columnName="из них экстренных" property="7" />
    </msh:table>
    </msh:sectionContent>
    </msh:section>
    <%
    		}
    	} else if (view!=null && (view.equals("5"))) {
    		if (isReestr) {
    			ActionUtil.setParameterFilterSql("deniedHospitalizating", "mc.deniedhospitalizating_id", request) ;
    			
    			%>    <msh:section>
    <msh:sectionTitle>Реестр отказов ${dateInfo} за день ${param.dateBegin}. 
    </msh:sectionTitle>
    <msh:sectionContent>
    <ecom:webQuery name="swod" nameFldSql="swod_sql" nativeSql=" 
select mc.id as slsid
    ,ss.code as sscode
    ,pat.lastname||' '||pat.firstname||' '||coalesce(pat.middlename) as fio
    ,to_char(pat.birthday,'dd.mm.yyyy') as patbirthday
    ,to_char(mc.datestart,'dd.mm.yyyy') as mcdatestratt
    ,to_char(mc.datefinish,'dd.mm.yyyy') as mcdatefinish
    ,case when mc.emergency='1' then 'Экстренно' else 'Планово' end  as cntmcEmergency
    ,ml.name as mlname
    ,list(''||mp.polnumber||' '||ri.name) as riname
    ,vdh.name as vdhname
    from medcase mc
left join statisticstub ss on ss.id=mc.statisticStub_id
left join patient pat on pat.id=mc.patient_id
left join medcase_medpolicy mcmp on mcmp.medcase_id=mc.id
left join medpolicy mp on mp.id=mcmp.policies_id
left join reg_ic ri on ri.id=mp.company_id
left join vocservicestream vss on vss.id=mc.serviceStream_id
left join mislpu ml on ml.id=mc.department_id
left join VocDeniedHospitalizating vdh on vdh.id=mc.deniedHospitalizating_id

where mc.dtype='HospitalMedCase' 
and mc.datestart between to_date('${dateBegin}','dd.mm.yyyy') and to_date('${dateEnd}','dd.mm.yyyy')
and mc.deniedhospitalizating_id is not null  ${paramSql}  ${insCompSql} ${deniedHospitalizatingSql}
group by mc.id,ss.code
    ,pat.lastname,pat.firstname,pat.middlename
    ,pat.birthday,mc.datestart
    ,mc.datefinish,mc.emergency,ml.name,vdh.name
order by pat.lastname,pat.middlename,pat.middlename
      " />
    <msh:table name="swod" viewUrl="entitySubclassShortView-mis_medCase.do" action="entitySubclassView-mis_medCase.do" idField="1">
      <msh:tableColumn columnName="№" property="sn" />
      <msh:tableColumn columnName="Полис" property="10" />
      <msh:tableColumn columnName="ФИО пациента" property="3" />
      <msh:tableColumn columnName="Дата рождения" property="4" />
      <msh:tableColumn columnName="Дата отказа" property="5" />
      <msh:tableColumn columnName="Показания" property="7" />
      <msh:tableColumn columnName="Отделение пост." property="8" />
      <msh:tableColumn columnName="Причина поступления" property="9" />
      
    </msh:table>
    </msh:sectionContent>
    </msh:section><%
    		} else {
    		// свод отказов
    		%>
    		    <msh:section>
    <msh:sectionTitle>Свод отказов от госпитализаций по стаховым компаниям ${dateInfo} за день ${param.dateBegin}. По ${dischInfo}  ${emerInfo} ${hourInfo}
    </msh:sectionTitle>
    <msh:sectionContent>
    <ecom:webQuery name="swod" nameFldSql="journal_priem_sql" nativeSql="    
    select '&insCompany='||coalesce(ri.id,'-1')||'&dtype='||mp.dtype as mcdtype
    ,case when mp.dtype='MedPolicyOmc' then 'ОМС' when mp.dtype='MedPolicyOmcForeign' then 'ОМС иногороднего' when mp.dtype is null then 'нет' else 'ДМС' end as dtypeName
    ,ri.name as riname
    ,count(distinct mc.patient_id) as cntpatient
    ,count(distinct mc.id)  as cntmc
    ,count(distinct case when mc.emergency='1' then mc.id else null end)  as cntmcEmergency
    from medcase mc
left join medcase_medpolicy mcmp on mcmp.medcase_id=mc.id
left join medpolicy mp on mp.id=mcmp.policies_id
left join reg_ic ri on ri.id=mp.company_id
left join vocservicestream vss on vss.id=mc.serviceStream_id
left join mislpu ml on ml.id=mc.department_id
where mc.dtype='HospitalMedCase' 
and mc.datestart between to_date('${dateBegin}','dd.mm.yyyy') and to_date('${dateEnd}','dd.mm.yyyy')
and mc.deniedhospitalizating_id is not null  ${paramSql} 
group by mp.dtype,ri.name,ri.id
order by ri.name
      " />
    <msh:table name="swod" action="stac_report_insCompany.do?typeView=5&department=${param.department}${paramHref}" 
    viewUrl="stac_report_insCompany.do?short=Short&typeView=5&department=${param.department}${paramHref}"
    idField="1">
      <msh:tableColumn columnName="Тип полиса" property="2" />
      <msh:tableColumn columnName="Страховая компания" property="3" />
      <msh:tableColumn columnName="Кол-во пациентов" property="4" />
      <msh:tableColumn columnName="Кол-во случаев" property="5" />
      <msh:tableColumn columnName="из них экстренных" property="6" />
    </msh:table>
    </msh:sectionContent>
    </msh:section>
    <%
    		}
    	} else if (view!=null && (view.equals("6"))) {
    		if (isReestr) {
    			%>    <%
    		} else {
    		// свод отказов
    		%>
    		    <msh:section>
    <msh:sectionTitle>Свод по отказам от госпитализаций ${dateInfo} за день ${param.dateBegin}. По ${dischInfo}  ${emerInfo} ${hourInfo}
    </msh:sectionTitle>
    <msh:sectionContent>
    <ecom:webQuery name="swod" nameFldSql="journal_priem_sql" nativeSql="    
    select '&deniedHospitalizating='||vdh.id||'&insCompany='||coalesce(ri.id,'-1')||'&dtype='||mp.dtype as riid
    ,case when mp.dtype='MedPolicyOmc' then 'ОМС' when mp.dtype='MedPolicyOmcForeign' then 'ОМС иногороднего' when mp.dtype is null then 'нет' else 'ДМС' end as dtypeName
    ,ri.name as riname
    ,vdh.name as vdhname
    ,count(distinct mc.patient_id) as cntpatient
    ,count(distinct mc.id)  as cntmc
    ,count(distinct case when mc.emergency='1' then mc.id else null end)  as cntmcEmergency
    from medcase mc
left join medcase_medpolicy mcmp on mcmp.medcase_id=mc.id
left join medpolicy mp on mp.id=mcmp.policies_id
left join reg_ic ri on ri.id=mp.company_id
left join vocservicestream vss on vss.id=mc.serviceStream_id
left join VocDeniedHospitalizating vdh on vdh.id=mc.deniedHospitalizating_id
left join mislpu ml on ml.id=mc.department_id
where mc.dtype='HospitalMedCase' 
and mc.datestart between to_date('${dateBegin}','dd.mm.yyyy') and to_date('${dateEnd}','dd.mm.yyyy')
and mc.deniedhospitalizating_id is not null  ${paramSql} 
group by mp.dtype,ri.name,ri.id,vdh.id,vdh.name
order by ri.name,vdh.name
      " />
    <msh:table name="swod" action="stac_report_insCompany.do?typeView=5&department=${param.department}${paramHref}"
    viewUrl="stac_report_insCompany.do?short=Short&typeView=5&department=${param.department}${paramHref}"
     idField="1">
      <msh:tableColumn columnName="Тип полиса" property="2" />
      <msh:tableColumn columnName="Страховая компания" property="3" />
      <msh:tableColumn columnName="Причина отказа от госпитализаций" property="4" />
      <msh:tableColumn columnName="Кол-во пациентов" property="5" />
      <msh:tableColumn columnName="Кол-во случаев" property="6" />
      <msh:tableColumn columnName="из них экстренных" property="7" />
    </msh:table>
    </msh:sectionContent>
    </msh:section>
    
    		<%
    		}
    	}} else {%>
    	<i>Нет данных </i>
    	<% }   %>
    

  </tiles:put>
</tiles:insert>

