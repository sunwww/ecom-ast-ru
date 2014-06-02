<%@page import="ru.ecom.mis.ejb.service.patient.HospitalLibrary"%>
<%@page import="ru.ecom.mis.web.action.util.ActionUtil"%>
<%@page import="java.util.Calendar"%>
<%@page import="ru.nuzmsh.util.format.DateFormat"%>
<%@page import="java.util.Date"%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://struts.apache.org/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://www.nuzmsh.ru/tags/msh" prefix="msh" %>
<%@ taglib uri="http://www.ecom-ast.ru/tags/ecom" prefix="ecom" %>
<%@ taglib tagdir="/WEB-INF/tags" prefix="tags" %>

<tiles:insert page="/WEB-INF/tiles/main${param.short}Layout.jsp" flush="true" >

    <tiles:put name='title' type='string'>
        <msh:title mainMenu="StacJournal">Просмотр данных по коечному фонду</msh:title>
    </tiles:put>

    <tiles:put name='side' type='string'>
        <tags:stac_journal currentAction="stac_journalByBedFund"/>
    </tiles:put>
    
  <tiles:put name="body" type="string">
  <%
	//String typeView =ActionUtil.updateParameter("Report_hospital_groupByBedFund","typeView","1", request) ;
	String typePatient =ActionUtil.updateParameter("Report_hospital_groupByBedFund","typePatient","4", request) ;
	String typeDate =ActionUtil.updateParameter("Report_hospital_groupByBedFund","typeDate","2", request) ;
	String typeStandart =ActionUtil.updateParameter("Report_hospital_groupByBedFund","typeStandart","1", request) ;
	String typeViewAddStatus =ActionUtil.updateParameter("Report_hospital_groupByBedFund","typeViewAddStatus","2", request) ;
	String typeViewServiceStream =ActionUtil.updateParameter("Report_hospital_groupByBedFund","typeViewServiceStream","1", request) ;
	String typeView =ActionUtil.updateParameter("Report_hospital_groupByBedFund","typeView","2", request) ;
	String typeEmergency =ActionUtil.updateParameter("Report_hospital_groupByBedFund","typeEmergency","3", request) ;
  %>
  <%
  	String shortI = request.getParameter("short") ;
	ActionUtil.setParameterFilterSql("department","d.id", request) ;
	ActionUtil.setParameterFilterSql("serviceStream","vss.id", request) ;
	ActionUtil.setParameterFilterSql("bedSubType","bf.bedSubType_id", request) ;
	ActionUtil.setParameterFilterSql("bedType","bf.bedType_id", request) ;
	ActionUtil.setParameterFilterSql("additionStatus","p.additionStatus_id", request) ;

  if (shortI==null || shortI.equals("")) {
  %>
    <msh:form action="/stac_groupByBedFundList.do" defaultField="department" disableFormDataConfirm="true" method="GET" guid="d7b31bc2-38f0-42cc-8d6d-19395273168f">
    <msh:panel guid="6ae283c8-7035-450a-8eb4-6f0f7da8a8ff">
      <msh:row guid="53627d05-8914-48a0-b2ec-792eba5b07d9">
        <msh:separator label="Параметры поиска" colSpan="7" guid="15c6c628-8aab-4c82-b3d8-ac77b7b3f700" />
      </msh:row>
      <msh:row guid="7d80be13-710c-46b8-8503-ce0413686b69">
        <td class="label" title="Поиск по дате  (typeDate)" colspan="1"><label for="typeDateName" id="typeDateLabel">Искать по дате:</label></td>
        <td onclick="this.childNodes[1].checked='checked';">
        	<input type="radio" name="typeDate" value="1">  поступления
        </td>
        <td onclick="this.childNodes[1].checked='checked';" colspan="2">
        	<input type="radio" name="typeDate" value="2">  выписки
        </td>
        <td onclick="this.childNodes[1].checked='checked';">
        	<input type="radio" name="typeDate" value="3">  перевода
        </td>
        </msh:row>
        <msh:row>
	        <td class="label" title="Поиск по показаниям поступления (typeEmergency)" colspan="1"><label for="typeEmergencyName" id="typeEmergencyLabel">Показания:</label></td>
	        <td onclick="this.childNodes[1].checked='checked';">
	        	<input type="radio" name="typeEmergency" value="1">  экстренные
	        </td>
	        <td onclick="this.childNodes[1].checked='checked';"  colspan="2">
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
        <td onclick="this.childNodes[1].checked='checked';"  colspan="2">
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
        <td class="label" title="Отображать доп. статус (typeViewAddStatus)" colspan="1"><label for="typeViewAddStatusName" id="typeViewAddStatusLabel">Колонку доп.статус:</label></td>
        <td onclick="this.childNodes[1].checked='checked';" >
        	<input type="radio" name="typeViewAddStatus" value="1">  отображать
        </td>
        <td onclick="this.childNodes[1].checked='checked';" colspan="2">
        	<input type="radio" name="typeViewAddStatus" value="2">  не отображать
        </td>
        </msh:row>
      <msh:row>
        <td class="label" title="Отображать колонку поток обслуживания (typeViewServiceStream)" colspan="1"><label for="typeViewServiceStreamName" id="typeViewServiceStreamLabel">Колонку поток обсл.:</label></td>
        <td onclick="this.childNodes[1].checked='checked';" >
        	<input type="radio" name="typeViewServiceStream" value="1">  отображать
        </td>
        <td onclick="this.childNodes[1].checked='checked';" colspan="2">
        	<input type="radio" name="typeViewServiceStream" value="2">  не отображать
        </td>
        </msh:row>
      <msh:row>
        <td class="label" title="Отображать (typeView)" colspan="1"><label for="typeViewName" id="typeViewLabel">Отобразить:</label></td>
        <td onclick="this.childNodes[1].checked='checked';">
        	<input type="radio" name="typeView" value="1">  реестр
        </td>
        <td onclick="this.childNodes[1].checked='checked';" colspan="2">
        	<input type="radio" name="typeView" value="2">  свод
        </td>
        <td onclick="this.childNodes[1].checked='checked';">
        	<input type="radio" name="typeView" value="3">  свод по датам
        </td>
        </msh:row>
        <msh:row>
        	<msh:autoComplete property="department" fieldColSpan="6"
        	label="Отделение" horizontalFill="true" vocName="lpu"/>
        </msh:row>
        <msh:row>
        	<msh:autoComplete property="serviceStream" fieldColSpan="6"
        	label="Поток обслуживания" horizontalFill="true" vocName="vocServiceStream"/>
        </msh:row>
        <msh:row>
        	<msh:autoComplete property="bedType" fieldColSpan="6"
        	label="Профиль коек" horizontalFill="true" vocName="vocBedType"/>
        </msh:row>
        <msh:row>
        	<msh:autoComplete property="bedSubType" fieldColSpan="6"
        	label="Тип коек" horizontalFill="true" vocName="vocBedSubType"/>
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
        <script type='text/javascript'>
  //checkFieldUpdate('typeSwod','${typeSwod}',1) ;
    checkFieldUpdate('typeDate','${typeDate}',2) ;
    checkFieldUpdate('typePatient','${typePatient}',4) ;
    checkFieldUpdate('typeEmergency','${typeEmergency}',4) ;
    //checkFieldUpdate('typeStandart','${typeStandart}',2) ;
    checkFieldUpdate('typeViewAddStatus','${typeViewAddStatus}',2) ;
    checkFieldUpdate('typeViewServiceStream','${typeViewServiceStream}',1) ;
    checkFieldUpdate('typeView','${typeView}',2) ;
    
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
    	frm.action='stac_groupByBedFundList.do' ;
    }
    </script>
    <%
  }
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
    	String dateSql = "transferDate" ;
    	if (typeDate!=null && typeDate.equals("1")) {
    		dateSql="dateStart" ;
    	} else if (typeDate!=null && typeDate.equals("2")) {
    		dateSql="dateFinish" ;
    	} else {
    		dateSql="transferDate" ;
    	}
    	request.setAttribute("dateSql",dateSql) ;
    	if (typeViewAddStatus!=null && typeViewAddStatus.equals("1")) {
    		request.setAttribute("viewAddStatusSql", "vas.name as vasname ") ;
    		request.setAttribute("viewAddStatusGroup", " p.additionStatus_id, vas.name ,") ;
    		request.setAttribute("viewAddStatusOrder", " vas.name, ") ;
    		request.setAttribute("viewAddStatusSqlId", "'&additionStatus='||p.additionStatus_id") ;
    	} else {
    		request.setAttribute("viewAddStatusSql", "1 as vasname ") ;
    		request.setAttribute("viewAddStatusGroup", "") ;
    		request.setAttribute("viewAddStatusOrder", "") ;
    		request.setAttribute("viewAddStatusSqlId", "''") ;
    		request.setAttribute("viewAddStatusStyle", " td.noAddStatus,th.noAddStatus{display:none;}") ;
    		
    	}
    	if (typeViewServiceStream!=null && typeViewServiceStream.equals("1")) {
    		request.setAttribute("viewServiceStreamSql", " vss.name as vssname ") ;
    		request.setAttribute("viewServiceStreamGroup", " vss.id, vss.name ,") ;
    		request.setAttribute("viewServiceStreamOrder", " vss.name, ") ;
    		request.setAttribute("viewServiceStreamSqlId", "'&serviceStream='||vss.id") ;
    	} else {
    		request.setAttribute("viewServiceStreamSql", " 1 as vssname ") ;
    		request.setAttribute("viewServiceStreamGroup", "") ;
    		request.setAttribute("viewServiceStreamOrder", "") ;
    		request.setAttribute("viewServiceStreamSqlId", "''") ;
    		request.setAttribute("viewServiceStreamStyle", " td.noServiceStream,th.noServiceStream{display:none;}") ;
    	}
    	%>
   		<msh:ifInRole roles="/Policy/Mis/MedCase/Stac/Journal/ReestrByBedFund/NotViewInfoStac">
        <style type="text/css">
		td.NotViewInfoStac,th.NotViewInfoStac {
			display: none;
		} 
		</style>
   		</msh:ifInRole>    	
    	<%
    	if (typeView!=null && typeView.equals("1")) {
    	%>
    		<ecom:webQuery name="swod_by_standart" maxResult="1500" nameFldSql="swod_by_standart_sql" nativeSql="
    	select m.id
    	,d.name as depname,ss.code as sscode
    	,p.lastname||' '||p.firstname||' '||p.middlename as fio
    ,to_char(p.birthday,'dd.mm.yyyy') as birthday
    ,to_char(m.dateStart,'dd.mm.yyyy') as mdateStart
    ,to_char(coalesce(m.dateFinish,m.transferDate),'dd.mm.yyyy') as mdateFinish
    ,	  case 
			when (coalesce(m.dateFinish,m.transferDate,CURRENT_DATE)-m.dateStart)=0 then 1 
			when bf.addCaseDuration='1' then ((coalesce(m.dateFinish,m.transferDate,CURRENT_DATE)-m.dateStart)+1) 
			else (coalesce(m.dateFinish,m.transferDate,CURRENT_DATE)-m.dateStart)
		  end as cnt1
    ,to_char(h.dateStart,'dd.mm.yyyy') as hdateStart
    ,to_char(h.dateFinish,'dd.mm.yyyy') as hdateFinish
    ,	  case 
			when (coalesce(h.dateFinish,CURRENT_DATE)-h.dateStart)=0 then 1 
			when bf.addCaseDuration='1' then ((coalesce(h.dateFinish,CURRENT_DATE)-h.dateStart)+1) 
			else (coalesce(h.dateFinish,CURRENT_DATE)-h.dateStart)
		  end as cnt2
    ,(select list(vdrt.name||' '||vpd.name||' '||mkb.code) from Diagnosis diag left join vocidc10 mkb on mkb.id=diag.idc10_id left join VocPriorityDiagnosis vpd on vpd.id=diag.priority_id left join VocDiagnosisRegistrationType vdrt on vdrt.id=diag.registrationType_id where diag.medcase_id=m.id) as diag
    ,vhr.name as vhrname
    from MedCase as m 
    left join medcase as h on h.id=m.parent_id 
    left join VocHospitalizationResult vhr on vhr.id=h.result_id
    left join statisticstub as ss on ss.id=h.statisticstub_id 
    left join bedfund as bf on bf.id=m.bedfund_id 
    left join vocbedsubtype as vbst on vbst.id=bf.bedSubType_id 
    left join vocbedtype as vbt on vbt.id=bf.bedtype_id 
    left join mislpu as d on d.id=m.department_id 
    left join patient as p on p.id=m.patient_id 
    left join VocSocialStatus pvss on pvss.id=p.socialStatus_id
    left join Omc_Oksm ok on p.nationality_id=ok.id 
    left join VocServiceStream vss on vss.id=m.serviceStream_id
    where m.DTYPE='DepartmentMedCase' and m.${dateSql} between 
    to_date('${dateBegin}','dd.mm.yyyy')  and to_date('${dateEnd}','dd.mm.yyyy') 
    ${departmentSql} ${emergencySql} ${serviceStreamSql} ${bedTypeSql} ${bedSubTypeSql} 
    ${patientSql} ${additionStatusSql} 
    
    order by  p.lastname,p.firstname,p.middlename
    	"/>
   <form action="print-stac_report_bedFund_reestr.do" method="post" target="_blank">
    Период с ${dateBegin} по ${dateEnd}.
    <input type='hidden' name="sqlText" id="sqlText" value="${swod_by_standart_sql}"> 
    <input type='hidden' name="sqlInfo" id="sqlInfo" value="">
    <input type='hidden' name="s" id="s" value="PrintService">
    <input type='hidden' name="m" id="m" value="printNativeQuery">
    <input type="submit" value="Печать всего"> 
    </form>
 
    	<msh:table name="swod_by_standart" selection="multiply" 
    	action="entityParentView-stac_slo.do" viewUrl="entityShortView-stac_slo.do" idField="1">
		      <msh:tableColumn columnName="#" property="sn" guid="e98f73b5-8b9e-4a3e-966f-4d43576bbc96" />
		      <msh:tableColumn columnName="Отделение" property="2" guid="e98f73b5-8b9e-4a3e-966f-4d43576bbc96" />
		      <msh:tableColumn columnName="№ИБ" property="3" guid="e98f73b5-8b9e-4a3e-966f-4d43576bbc96" />
		      <msh:tableColumn columnName="Фамилия имя отчество пациента" property="4" guid="fc26523a-eb9c-44bc-b12e-42cb7ca9ac5b" />
		      <msh:tableColumn columnName="Год рождения" property="5" guid="fc223a-eb9c-44bc-b12e-42cb7ca9ac5b" />
		      <msh:tableColumn columnName="Дата пост. (отд)" property="6" guid="f6523a-eb9c-44bc-b12e-42cb7ca9ac5b" />
		      <msh:tableColumn columnName="Дата выписки / перевода (отд)" property="7" guid="f6523a-eb9c-44bc-b12e-42cb7ca9ac5b" />
		      <msh:tableColumn columnName="К.Д по отд" property="8"/>
		      <msh:tableColumn cssClass="NotViewInfoStac" columnName="Дата пост. (стац)" property="9" guid="f6523a-eb9c-44bc-b12e-42cb7ca9ac5b" />
		      <msh:tableColumn cssClass="NotViewInfoStac" columnName="Дата выписки (стац)" property="10" guid="f6523a-eb9c-44bc-b12e-42cb7ca9ac5b" />
		      <msh:tableColumn cssClass="NotViewInfoStac" columnName="К.Д по стац" property="11"/>
		      <msh:tableColumn columnName="Диагноз" property="12"/>
		      <msh:tableColumn columnName="Результат госпитализации" property="13"/>
    	</msh:table>
    	<%
    	} else {
    		//Начинается свод
    		
    	if (typeView!=null && typeView.equals("3")) {
    		request.setAttribute("viewDateSql", " to_char(m."+dateSql+",'dd.mm.yyyy') as dateViewS ") ;
    		request.setAttribute("viewDateGroup", " m."+dateSql+" ,") ;
    		request.setAttribute("viewDateOrder", " m."+dateSql+", ") ;
    		request.setAttribute("viewDateSqlId", "'&dateBegin='||to_char(m."+dateSql+",'dd.mm.yyyy')") ;
    	} else {
    		request.setAttribute("viewDateSql", " 1 as dateViewS ") ;
    		request.setAttribute("viewDateGroup", "") ;
    		request.setAttribute("viewDateOrder", "") ;
    		request.setAttribute("viewDateSqlId", "'&dateBegin="+date+"&dateEnd="+dateEnd+"'") ;
    		request.setAttribute("viewDateStyle", " td.noDate,th.noDate{display:none;}") ;
    	}

    	%>
    	<style type="text/css">
    		${viewAddStatusStyle}
    		${viewServiceStreamStyle}
    		${viewDateStyle}
    	</style>
    	<ecom:webQuery name="swod_by_standart" nativeSql="
    	select  '&department='||m.department_id
    	||'&bedType='||vbt.id
    	||'&bedSubType='||vbst.id
    	||${viewDateSqlId}
    	||${viewAddStatusSqlId}||${departmentSqlId}
    	||${bedTypeSqlId}||${viewServiceStreamSqlId}
    	||${bedSubTypeSqlId}||${serviceStreamSqlId} as id
    	,${viewDateSql}
    	, ${viewAddStatusSql}
    	, d.name as dname
    	,vbt.name as vbtname
    	,vbst.name as vbstname
    	,${viewServiceStreamSql}
    	,count(*) as cnt
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
    	
    from MedCase as m 
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
    
    where m.DTYPE='DepartmentMedCase' and m.${dateSql} between 
    to_date('${dateBegin}','dd.mm.yyyy')  and to_date('${dateEnd}','dd.mm.yyyy') 
    ${departmentSql}  ${emergencySql} ${serviceStreamSql} ${bedTypeSql} ${bedSubTypeSql} 
    ${patientSql} ${additionStatusSql} 
    group by  
		m.department_id , d.name,
		${viewDateGroup} ${viewServiceStreamGroup} ${viewAddStatusGroup} 
	 vbt.id , vbt.name,vbst.id,vbst.name
    order by  ${viewDateOrder} d.name,${viewServiceStreamOrder} ${viewAddStatusOrder} vbt.name,vbst.name
    	"/>
    	<msh:table name="swod_by_standart"  selection="multiply"
    	viewUrl="stac_groupByBedFundList.do?short=Short&typeEmergency=${typeEmergency}&typePatient=${typePatient}&typeView=1"
    	action="stac_groupByBedFundList.do?typeEmergency=${typeEmergency}&typePatient=${typePatient}&typeView=1" idField="1">
    		<msh:tableColumn property="2" cssClass="noDate" columnName="Дата"/>
    		<msh:tableColumn property="3" cssClass="noAddStatus" columnName="Доп. статус"/>
    		<msh:tableColumn property="4" cssClass="noDepartment" columnName="Отделение"/>
    		<msh:tableColumn property="5" cssClass="noBed" columnName="Профиль коек"/>
    		<msh:tableColumn property="6" columnName="Тип коек"/>
    		<msh:tableColumn property="7" cssClass="noServiceStream" columnName="Поток"/>
    		<msh:tableColumn property="8" isCalcAmount="true" columnName="Кол-во СЛО"/>
    		<msh:tableColumn property="9" isCalcAmount="true" columnName="Кол-во к.дней по СЛО"/>
    		<msh:tableColumn property="10" isCalcAmount="true" cssClass="NotViewInfoStac" columnName="Кол-во к.дней по СЛС"/>
    		<msh:tableColumn property="11" isCalcAmount="true" columnName="Кол-во случаев смерти"/>
    	</msh:table>
    	
		<%
		//Заканчивается свод
    	}
    	} else {%>
    
    	<i>Выберите параметры и нажмите найти </i>
    	<% }   %>
     

  </tiles:put>

</tiles:insert>