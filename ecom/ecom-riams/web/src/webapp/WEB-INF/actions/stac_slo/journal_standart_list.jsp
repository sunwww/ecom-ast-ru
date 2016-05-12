<%@page import="ru.ecom.mis.ejb.service.patient.HospitalLibrary"%>
<%@page import="ru.ecom.web.util.ActionUtil"%>
<%@page import="java.util.Calendar"%>
<%@page import="ru.nuzmsh.util.format.DateFormat"%>
<%@page import="java.util.Date"%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://struts.apache.org/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://www.nuzmsh.ru/tags/msh" prefix="msh" %>
<%@ taglib uri="http://www.ecom-ast.ru/tags/ecom" prefix="ecom" %>
<%@ taglib tagdir="/WEB-INF/tags" prefix="tags" %>

<tiles:insert page="/WEB-INF/tiles/mainLayout.jsp" flush="true" >

    <tiles:put name='title' type='string'>
        <msh:title mainMenu="StacJournal">Просмотр данных по стандартам</msh:title>
    </tiles:put>

    <tiles:put name='side' type='string'>
        <tags:stac_journal currentAction="stac_journalByStandart"/>
    </tiles:put>
    
  <tiles:put name="body" type="string">
  <%
	//String typeView =ActionUtil.updateParameter("Report_hospital_standart","typeView","1", request) ;
	String typePatient =ActionUtil.updateParameter("Report_hospital_standart","typePatient","4", request) ;
	String typeDate =ActionUtil.updateParameter("Report_hospital_standart","typeDate","2", request) ;
	String typeStandart =ActionUtil.updateParameter("Report_hospital_standart","typeStandart","1", request) ;
	String typeViewDepartment =ActionUtil.updateParameter("Report_hospital_standart","typeViewDepartment","1", request) ;
	String typeViewBed =ActionUtil.updateParameter("Report_hospital_standart","typeViewBed","1", request) ;
	String typeView =ActionUtil.updateParameter("Report_hospital_standart","typeView","2", request) ;
	String typeEmergency =ActionUtil.updateParameter("Report_hospital_standart","typeEmergency","3", request) ;
  

  %>
  
    <msh:form action="/stac_report_standartOmc.do" defaultField="department" disableFormDataConfirm="true" method="GET" guid="d7b31bc2-38f0-42cc-8d6d-19395273168f">
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
        <msh:row styleId="noswod">
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
      <msh:row guid="7d80be13-710c-46b8-8503-ce0413686b69">
        <td class="label" title="Поиск по пациентам (typePatient)" colspan="1"><label for="typePatientName" id="typePatientLabel">Пациенты:</label></td>
        <td onclick="this.childNodes[1].checked='checked';">
        	<input type="radio" name="typePatient" value="1">  региональные
        </td>
        <td onclick="this.childNodes[1].checked='checked';">
        	<input type="radio" name="typePatient" value="2">  иногородные
        </td>
        <td onclick="this.childNodes[1].checked='checked';">
        	<input type="radio" name="typePatient" value="3">  иностранцы
        </td>
        <td onclick="this.childNodes[1].checked='checked';">
        	<input type="radio" name="typePatient" value="4">  все
        </td>
      </msh:row>
      <msh:row>
        <td class="label" title="Стандарт (typeStandart)" colspan="1"><label for="typeStandartName" id="typeStandartLabel">Стандарт ОМС:</label></td>
        <td onclick="this.childNodes[1].checked='checked';" colspan="2">
        	<input type="radio" name="typeStandart" value="1"> установл. врачом
        </td>
        <td onclick="this.childNodes[1].checked='checked';" colspan="2">
        	<input type="radio" name="typeStandart" value="2"> установл. экспертом
        </td>
        </msh:row>
      <msh:row>
        <td class="label" title="Отображать отделение (typeViewDepartment)" colspan="1"><label for="typeViewDepartmentName" id="typeViewDepartmentLabel">Колонку отделение:</label></td>
        <td onclick="this.childNodes[1].checked='checked';" colspan="2">
        	<input type="radio" name="typeViewDepartment" value="1">  отображать
        </td>
        <td onclick="this.childNodes[1].checked='checked';" colspan="2">
        	<input type="radio" name="typeViewDepartment" value="2">  не отображать
        </td>
        </msh:row>
      <msh:row>
        <td class="label" title="Отображать колонку профиль коек (typeViewBed)" colspan="1"><label for="typeViewBedName" id="typeViewBedLabel">Колонку проф. коек:</label></td>
        <td onclick="this.childNodes[1].checked='checked';" colspan="2">
        	<input type="radio" name="typeViewBed" value="1">  отображать
        </td>
        <td onclick="this.childNodes[1].checked='checked';" colspan="2">
        	<input type="radio" name="typeViewBed" value="2">  не отображать
        </td>
        </msh:row>
      <msh:row>
        <td class="label" title="Отображать (typeView)" colspan="1"><label for="typeViewName" id="typeViewLabel">Отобразить:</label></td>
        <td onclick="this.childNodes[1].checked='checked';" colspan="2">
        	<input type="radio" name="typeView" value="1">  реестр
        </td>
        <td onclick="this.childNodes[1].checked='checked';" colspan="2">
        	<input type="radio" name="typeView" value="2">  свод
        </td>
        </msh:row>
        <msh:row>
        	<msh:autoComplete property="department" fieldColSpan="4"
        	label="Отделение" horizontalFill="true" vocName="vocLpuHospOtdAll"/>
        </msh:row>
        <msh:row>
        	<msh:autoComplete property="serviceStream" fieldColSpan="4"
        	label="Поток обслуживания" horizontalFill="true" vocName="vocServiceStream"/>
        </msh:row>
        <msh:row>
        	<msh:autoComplete property="bedType" fieldColSpan="4"
        	label="Профиль коек" horizontalFill="true" vocName="vocBedType"/>
        </msh:row>
        <msh:row>
        	<msh:autoComplete property="bedSubType" fieldColSpan="4"
        	label="Тип коек" horizontalFill="true" vocName="vocBedSubType"/>
        </msh:row>
        <msh:row>
        	<msh:autoComplete parentAutocomplete="serviceStream" property="standart" fieldColSpan="4"
        	label="ВМП" horizontalFill="true" vocName="vocKindHighCare"/>
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
    	request.setAttribute("dateBegin", date) ;
    	
    	if (typeEmergency!=null && typeEmergency.equals("1")) {
    		request.setAttribute("emergencySql", " and hmc.emergency='1' ") ;
    	} else if (typeEmergency!=null && typeEmergency.equals("2")) {
    		request.setAttribute("emergencySql", " and (hmc.emergency is null or hmc.emergency='0') ") ;
    	} 
    	if (typePatient.equals("2")) {
			//aRequest.setAttribute("add", "and $$isForeignPatient^ZExpCheck(m.patient_id,m.dateStart)>0") ;
			request.setAttribute("patientSql", HospitalLibrary.getSqlForPatient(true, true, "m.Datestart", "p", "pvss", "pmp","ok")) ;
			request.setAttribute("infoTypePat", "Поиск по иногородним") ;
		} else if (typePatient.equals("1")){
			//aRequest.setAttribute("add", "and $$isForeignPatient^ZExpCheck(m.patient_id,m.dateStart)=0") ;
			request.setAttribute("patientSql", HospitalLibrary.getSqlForPatient(true, false, "m.Datestart", "p", "pvss", "pmp","ok")) ;
			request.setAttribute("infoTypePat", "Поиск по региональным") ;
		} else if (typePatient.equals("3")){
			//aRequest.setAttribute("add", "and $$isForeignPatient^ZExpCheck(m.patient_id,m.dateStart)=0") ;
			request.setAttribute("patientSql", HospitalLibrary.getSqlGringo(true, "ok")) ;
			request.setAttribute("infoTypePat", "Поиск по иностранцам") ;
		} else {
			request.setAttribute("patientSql", "") ;
			request.setAttribute("infoTypePat", "Поиск по всем") ;
		}
    	
    	String dep = (String)request.getParameter("department") ;
    	if (dep!=null && !dep.equals("") && !dep.equals("0")) {
    		request.setAttribute("departmentSqlId", "'&deparment="+dep+"'") ;
    		request.setAttribute("departmentSql", " and d.id="+dep) ;
    		request.setAttribute("department",dep) ;
    	} else {
    		request.setAttribute("department","0") ;
    		request.setAttribute("departmentSqlId", "''") ;
    	}
    	String servStream = (String)request.getParameter("serviceStream") ;
    	if (servStream!=null && !servStream.equals("") && !servStream.equals("0")) {
    		request.setAttribute("serviceStreamSqlId", "'&serviceStream="+servStream+"'") ;
    		request.setAttribute("serviceStreamSql", " and vss.id="+servStream) ;
    		request.setAttribute("serviceStream", servStream) ;
    	} else {
    		request.setAttribute("serviceStream", "0") ;
    		request.setAttribute("serviceStreamSqlId", "'&serviceStream='||vss.id") ;
    	}
    	String bedSubType = (String)request.getParameter("bedSubType") ;
    	if (bedSubType!=null && !bedSubType.equals("") && !bedSubType.equals("0")) {
    		request.setAttribute("bedSubTypeSqlId", "'&bedSubType="+bedSubType+"'");
    		request.setAttribute("bedSubTypeSql", " and bf.bedSubType_id="+bedSubType);
    	} else {
    		request.setAttribute("bedSubTypeSqlId", "'&bedSubType='||vbst.id");
    	}
    	String standart = (String)request.getParameter("standart") ;
    	if (standart!=null && !standart.equals("") && !standart.equals("0")) {
    		request.setAttribute("standartSqlId", "'&standart="+standart+"'");
    		request.setAttribute("standartSql", " and os.id="+standart);
    	} else {
    		request.setAttribute("standartSqlId", "'&standart='||os.id");
    	}
    	String bedType = (String)request.getParameter("bedType") ;
    	if (bedType!=null && !bedType.equals("") && !bedType.equals("0")) {
    		request.setAttribute("bedTypeSqlId", "'&bedType="+bedType+"'");
    		request.setAttribute("bedTypeSql", " and bf.bedType_id="+bedType);
    	} else {
    		request.setAttribute("bedTypeSqlId", "''") ;
    		//request.setAttribute("bedTypeSqlId", "'&bedType='||bf.bedType_id");
    	}
    	
    	if (typeStandart!=null && typeStandart.equals("1")) {
    		request.setAttribute("fldStandart", "kindHighCare_id") ;
    	} else {
    		request.setAttribute("fldStandart", "kindHighCare_id") ;
    	}
    	if (typeDate!=null && typeDate.equals("1")) {
    		request.setAttribute("dateSql","dateStart") ;
    	} else if (typeDate!=null && typeDate.equals("2")) {
    		request.setAttribute("dateSql","dateFinish") ;
    	} else {
    		request.setAttribute("dateSql","transferDate") ;
    	}
    	
    	if (typeViewDepartment!=null && typeViewDepartment.equals("1")) {
    		request.setAttribute("viewDepartmentSql", "d.name as mlname ") ;
    		request.setAttribute("viewDepartmentGroup", " d.id, d.name ,") ;
    		request.setAttribute("viewDepartmentOrder", " d.name, ") ;
    		request.setAttribute("viewDepartmentSqlId", "'&department='||d.id") ;
    	} else {
    		request.setAttribute("viewDepartmentSql", "1 as mlname ") ;
    		request.setAttribute("viewDepartmentGroup", "") ;
    		request.setAttribute("viewDepartmentOrder", "") ;
    		request.setAttribute("viewDepartmentSqlId", "''") ;
    		request.setAttribute("viewDepartmentStyle", " td.noDepartment,th.noDepartment{display:none;}") ;
    		
    	}
    	if (typeViewBed!=null && typeViewBed.equals("1")) {
    		request.setAttribute("viewBedSql", " vbt.name as vbtname ") ;
    		request.setAttribute("viewBedGroup", " vbt.id, vbt.name ,") ;
    		request.setAttribute("viewBedOrder", " vbt.name, ") ;
    		request.setAttribute("viewBedSqlId", "'&bedType='||vbt.id") ;
    	} else {
    		request.setAttribute("viewBedSql", " 1 as vbtname ") ;
    		request.setAttribute("viewBedGroup", "") ;
    		request.setAttribute("viewBedOrder", "") ;
    		request.setAttribute("viewBedSqlId", "''") ;
    		request.setAttribute("viewBedStyle", " td.noBed,th.noBed{display:none;}") ;
    	}
    	if (typeView!=null && typeView.equals("1")) {
    	%>
    		<ecom:webQuery nameFldSql="swod_by_standart_sql" name="swod_by_standart" nativeSql="
    	select  m.id as mid, p.lastname||' '||p.firstname||' '||p.middlename as fio,to_char(p.birthday,'dd.mm.yyyy') as birthday
    	,ss.code as sscode
    	,d.name
    	,list(distinct case when  (vdrt.code='3' or vdrt.code='4') and vpd.code='1' then mkb.code when mkb.id is null then 'не указан' else null end) 
    	,os.code ||' '||os.name as osname
    	,to_char(hmc.dateStart,'dd.mm.yyyy') as hmcDateStart
    	,to_char(m.dateStart,'dd.mm.yyyy')||'-'||to_char(coalesce(m.dateFinish,m.transferDate),'dd.mm.yyyy') as mdateStart
    	,to_char(hmc.dateFinish,'dd.mm.yyyy') as hmcdateFinish

    	
    	,
    	  case 
			when (coalesce(m.dateFinish,m.transferDate,CURRENT_DATE)-m.dateStart)=0 then 1 
			when bf.addCaseDuration='1' then ((coalesce(m.dateFinish,m.transferDate,CURRENT_DATE)-m.dateStart)+1) 
			else (coalesce(m.dateFinish,m.transferDate,CURRENT_DATE)-m.dateStart)
		  end
    	
    	
    	 as sum1
    	,
    	  case 
			when (coalesce(hmc.dateFinish,CURRENT_DATE)-hmc.dateStart)=0 then 1 
			when bf.addCaseDuration='1' then ((coalesce(hmc.dateFinish,CURRENT_DATE)-hmc.dateStart)+1) 
			else (coalesce(hmc.dateFinish,CURRENT_DATE)-hmc.dateStart)
		  end
    	
    	 as sum2
    	
    	,case when vhr.code='11' then 'Да' else null end as deathCase
    from MedCase as m 
    left join vocKindHighCare os on os.id=m.${fldStandart}
    left join Diagnosis diag on diag.medcase_id=m.id
    left join VocIdc10 mkb on mkb.id=diag.idc10_id
    left join VocPriorityDiagnosis vpd on vpd.id=diag.priority_id
    left join VocDiagnosisRegistrationType vdrt on vdrt.id=diag.registrationType_id
    left join MedCase as hmc on hmc.id=m.parent_id 
    left join StatisticStub ss on ss.id=hmc.statisticStub_id
    left join VocHospitalizationResult vhr on vhr.id=hmc.result_id 
    left join bedfund as bf on bf.id=m.bedfund_id 
    left join vocservicestream as vss on vss.id=bf.servicestream_id 
    left join vocbedsubtype as vbst on vbst.id=bf.bedSubType_id 
    left join vocbedtype as vbt on vbt.id=bf.bedtype_id 
    left join mislpu as d on d.id=m.department_id 
    left join patient p on p.id=hmc.patient_id
    left join VocSocialStatus pvss on pvss.id=p.socialStatus_id 
    left join VocAdditionStatus vas on vas.id=p.additionStatus_id
    left join Omc_Oksm ok on p.nationality_id=ok.id
    where m.DTYPE='DepartmentMedCase' and hmc.${dateSql} between 
    to_date('${dateBegin}','dd.mm.yyyy')  and to_date('${dateEnd}','dd.mm.yyyy') 
    ${departmentSql} ${serviceStreamSql} ${bedSubTypeSql} ${bedTypeSql} ${standartSql}  
    and os.id is not null  ${emergencySql} ${patientSql} 
    group by  m.id,hmc.id,ss.code,p.lastname,p.firstname,p.middlename,p.birthday,d.name, vbst.id,vbst.name,
    vss.id,vss.name,os.id,os.code,os.name,vhr.code,hmc.dateStart,hmc.dateFinish,m.dateStart,m.dateFinish,m.transferDate,bf.addCaseDuration
    order by  vss.name,vbst.name,os.code
    	"/>
    	    <form action="print-stac_report_standart_reestr.do" method="post" target="_blank">
    Период с ${beginDate} по ${finishDate}. ${filterInfo} ${specInfo} ${workFunctionInfo} ${lpuInfo} ${serviceStreamInfo}
    <input type='hidden' name="sqlText" id="sqlText" value="${swod_by_standart_sql}"> 
    <input type='hidden' name="sqlInfo" id="sqlInfo" value="">
    <input type='hidden' name="s" id="s" value="PrintService">
    <input type='hidden' name="m" id="m" value="printNativeQuery">
    <input type="submit" value="Печать"> 
    </form>
    	
    	<msh:table name="swod_by_standart" 
    	action="entityParentView-stac_slo.do" viewUrl="entityShortView-stac_slo.do" idField="1">
    		<msh:tableColumn property="sn" columnName="#"/>
    		<msh:tableColumn property="2" columnName="ФИО пациента"/>
    		<msh:tableColumn property="3" columnName="Дата рождения"/>
    		<msh:tableColumn property="4" columnName="№Ист.бол."/>
    		<msh:tableColumn property="5" columnName="Отделение"/>
    		<msh:tableColumn property="6" columnName="Диагноз"/>
    		<msh:tableColumn property="7" columnName="ВМП"/>
    		<msh:tableColumn property="8" columnName="Дата госпит."/>
    		<msh:tableColumn property="9" columnName="Дата пост. в отделение"/>
    		<msh:tableColumn property="10" columnName="Дата выписки"/>
    		<msh:tableColumn property="11" columnName="Кол-во к.дней по СЛО" isCalcAmount="true"/>
    		<msh:tableColumn property="12" columnName="Кол-во к.дней по СЛС" isCalcAmount="true"/>
    	</msh:table>
    	<%
    	} else {
    		//Начинается свод
    	%>
    	<style type="text/css">
    		${viewBedStyle}
    		${viewDepartmentStyle}
    	</style>
    	<ecom:webQuery name="swod_by_standart" nativeSql="
    	select  '&standart='||os.id||${viewDepartmentSqlId}||${departmentSqlId}
    	||${bedTypeSqlId}||${viewBedSqlId}
    	||${bedSubTypeSqlId}||${serviceStreamSqlId}
    	 ,${viewDepartmentSql}, ${viewBedSql}
    	,vbst.name as vbstname
    	,vss.name as vssname
    	,os.code as osmodel
    	,os.name as osname
    	,count(distinct m.id) as cnt
    	,sum(
    	  case 
			when (coalesce(m.dateFinish,m.transferDate,CURRENT_DATE)-m.dateStart)=0 then 1 
			when bf.addCaseDuration='1' then ((coalesce(m.dateFinish,m.transferDate,CURRENT_DATE)-m.dateStart)+1) 
			else (coalesce(m.dateFinish,m.transferDate,CURRENT_DATE)-m.dateStart)
		  end
    	
    	
    	) as sum1
    	,sum(
    	  case 
			when (coalesce(hmc.dateFinish,CURRENT_DATE)-hmc.dateStart)=0 then 1 
			when bf.addCaseDuration='1' then ((coalesce(hmc.dateFinish,CURRENT_DATE)-hmc.dateStart)+1) 
			else (coalesce(hmc.dateFinish,CURRENT_DATE)-hmc.dateStart)
		  end
    	
    	) as sum2
    	
    	    	,count(
    		case when vhr.code='11' then hmc.id else null end
    	) as deathCnt
    	
,list(distinct coalesce((select list(mkb.code)
    	from Diagnosis diag 
    left join VocIdc10 mkb on mkb.id=diag.idc10_id
    left join VocPriorityDiagnosis vpd on vpd.id=diag.priority_id
    left join VocDiagnosisRegistrationType vdrt on vdrt.id=diag.registrationType_id
    	where diag.medcase_id=m.id
    	and (vdrt.code='3' or vdrt.code='4') and vpd.code='1'
    	),'не указан')) as diag  
    from MedCase as m 
    left join vocKindHighCare os on os.id=m.${fldStandart}
    left join MedCase as hmc on hmc.id=m.parent_id 
    left join VocHospitalizationResult vhr on vhr.id=hmc.result_id 
    left join bedfund as bf on bf.id=m.bedfund_id 
    left join vocservicestream as vss on vss.id=bf.servicestream_id 
    left join vocbedsubtype as vbst on vbst.id=bf.bedSubType_id 
    left join vocbedtype as vbt on vbt.id=bf.bedtype_id 
    left join mislpu as d on d.id=m.department_id 
    left join patient p on p.id=hmc.patient_id
    left join VocSocialStatus pvss on pvss.id=p.socialStatus_id 
    left join VocAdditionStatus vas on vas.id=p.additionStatus_id
    left join Omc_Oksm ok on p.nationality_id=ok.id
    where m.DTYPE='DepartmentMedCase' and hmc.${dateSql} between 
    to_date('${dateBegin}','dd.mm.yyyy')  and to_date('${dateEnd}','dd.mm.yyyy') 
    ${departmentSql} ${serviceStreamSql} ${bedSubTypeSql} 
    ${bedTypeSql} ${standartSql} ${emergencySql}
    and os.id is not null ${patientSql}
    group by  ${viewDepartmentGroup} ${viewBedGroup} vbst.id,vbst.name,
    vss.id,vss.name,os.id,os.code,os.name
    order by  ${viewDepartmentOrder} ${viewBedOrder} vss.name,vbst.name,os.code
    	"/>
    	<msh:table name="swod_by_standart" 
    	action="stac_report_standartOmc.do?typeEmergency=${typeEmergency}&typePatient=${typePatient}&typeView=1&dateBegin=${dateBegin}&dateEnd=${dateEnd}" idField="1">
    		<msh:tableColumn property="2" cssClass="noDepartment" columnName="Отделение"/>
    		<msh:tableColumn property="3" cssClass="noBed" columnName="Профиль коек"/>
    		<msh:tableColumn property="4" columnName="Тип коек"/>
    		<msh:tableColumn property="5" columnName="Поток"/>
    		<msh:tableColumn property="6" columnName="Код"/>
    		<msh:tableColumn property="7" columnName="Наименование ВМП"/>
    		<msh:tableColumn property="8" isCalcAmount="true" columnName="Кол-во СЛО"/>
    		<msh:tableColumn property="9" isCalcAmount="true" columnName="Кол-во к.дней по СЛО"/>
    		<msh:tableColumn property="10" isCalcAmount="true" columnName="Кол-во к.дней по СЛС"/>
    		<msh:tableColumn property="11" isCalcAmount="true" columnName="Кол-во случаев смерти"/>
    		<msh:tableColumn property="12" columnName="Диагнозы"/>
    	</msh:table>
    	
		<%
		//Заканчивается свод
    	}
    	} else {%>
    
    	<i>Выберите параметры и нажмите найти </i>
    	<% }   %>
     
    <script type='text/javascript'>
  //checkFieldUpdate('typeSwod','${typeSwod}',1) ;
    checkFieldUpdate('typeDate','${typeDate}',2) ;
    checkFieldUpdate('typePatient','${typePatient}',4) ;
    checkFieldUpdate('typeEmergency','${typeEmergency}',4) ;
    checkFieldUpdate('typeStandart','${typeStandart}',2) ;
    checkFieldUpdate('typeViewDepartment','${typeViewDepartment}',1) ;
    checkFieldUpdate('typeViewBed','${typeViewBed}',1) ;
    checkFieldUpdate('typeView','${typeView}',2) ;
    /*
    kindHighCareAutocomplete.addOnChangeCallback(function() {
  		updateMethod() ;
  	}) ;
  	
  	serviceStreamAutocomplete.addOnChangeCallback(function() {
  	 	updateMethod() ;
  	 });
  	methodParam = "" ;
  	updateMethod() ;
  	function updateMethod() {
  		methodParam1 = (+$('kindHighCare').value)+"#"+(+$('serviceStream').value)  ;
      	if (methodParam1!=methodParam) {
      		
      		if (methodParam!="") {
	      		$('methodHighCare').value="" ;
	      		$('methodHighCareName').value="" ;
      		}
      		methodParam=methodParam1 ;
      		methodHighCareAutocomplete.setParentId(methodParam1) ;
      	}
  	}*/
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
    	frm.action='stac_report_standartOmc.do' ;
    }
    function print() {
    	var frm = document.forms[0] ;
    	frm.target='_blank' ;
    	frm.action='stac_report_standartOmc.do' ;
    }
    </script>
  </tiles:put>
</tiles:insert>

