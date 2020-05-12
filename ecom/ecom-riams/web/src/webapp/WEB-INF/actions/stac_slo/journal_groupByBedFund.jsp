<%@page import="ru.ecom.mis.ejb.service.patient.HospitalLibrary"%>
<%@page import="ru.ecom.web.util.ActionUtil"%>
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
	String typePolicy =ActionUtil.updateParameter("Report_hospital_groupByBedFund","typePolicy","3", request) ;
	String typePatient =ActionUtil.updateParameter("Report_hospital_groupByBedFund","typePatient","4", request) ;
	String typeDate =ActionUtil.updateParameter("Report_hospital_groupByBedFund","typeDate","2", request) ;
	String typeStandart =ActionUtil.updateParameter("Report_hospital_groupByBedFund","typeStandart","1", request) ;
	String typeViewAddStatus =ActionUtil.updateParameter("Report_hospital_groupByBedFund","typeViewAddStatus","2", request) ;
	String typeViewServiceStream =ActionUtil.updateParameter("Report_hospital_groupByBedFund","typeViewServiceStream","1", request) ;
	String typeView =ActionUtil.updateParameter("Report_hospital_groupByBedFund","typeView","2", request) ;
	String typeEmergency =ActionUtil.updateParameter("Report_hospital_groupByBedFund","typeEmergency","3", request) ;
	String typeResult =ActionUtil.updateParameter("Report_hospital_groupByBedFund","typeResult","3", request) ;

  	String shortI = request.getParameter("short") ;
	ActionUtil.setParameterFilterSql("department","d.id", request) ;
	ActionUtil.setParameterFilterSql("serviceStream","vss.id", request) ;
	ActionUtil.setParameterFilterSql("bedSubType","bf.bedSubType_id", request) ;
	ActionUtil.setParameterFilterSql("bedType","bf.bedType_id", request) ;
	ActionUtil.setParameterFilterSql("additionStatus","p.additionStatus_id", request) ;
	ActionUtil.setParameterFilterSql("sex","p.sex_id", request) ;

  if (shortI==null || shortI.equals("")) {
  %>
    <msh:form action="/stac_groupByBedFundList.do" defaultField="department" disableFormDataConfirm="true" method="GET" >
    <msh:panel >
      <msh:row >
        <msh:separator label="Параметры поиска" colSpan="7"  />
      </msh:row>
      <msh:row >
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
	        <td class="label" title="Результат госпитализации (typeResult)" colspan="1"><label for="typeResultName" id="typeResultLabel">Результат госпитализации:</label></td>
	        <td onclick="this.childNodes[1].checked='checked';">
	        	<input type="radio" name="typeResult" value="1">  перевод ЛПУ
	        </td>
	        <td onclick="this.childNodes[1].checked='checked';"  colspan="2">
	        	<input type="radio" name="typeResult" value="2"  >  смерть
	        </td>
	        <td onclick="this.childNodes[1].checked='checked';">
	        	<input type="radio" name="typeResult" value="3">  все
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
      <msh:row >
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
      <msh:row >
        <td class="label" title="Поиск по полисам (typePolicy)" colspan="1"><label for="typePolicyName" id="typePolicyLabel">Полис:</label></td>
        <td onclick="this.childNodes[1].checked='checked';">
        	<input type="radio" name="typePolicy" value="1">  региональные
        </td>
        <td onclick="this.childNodes[1].checked='checked';"  colspan="2">
        	<input type="radio" name="typePolicy" value="2">  иногородные
        </td>
        <td onclick="this.childNodes[1].checked='checked';" colspan="3">
        	<input type="radio" name="typePolicy" value="3">  не учитывается параметр
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
        	label="Отделение" horizontalFill="true" vocName="vocLpuHospOtdAll"/>
        	
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
        	<msh:autoComplete property="sex" fieldColSpan="6"
        	label="Пол" horizontalFill="true" vocName="vocSex"/>
        </msh:row>
        <msh:row>
        <msh:textField property="dateBegin" label="Период с"  />
        <msh:textField property="dateEnd" label="по"  />
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
    checkFieldUpdate('typePolicy','${typePolicy}',3) ;
    checkFieldUpdate('typeEmergency','${typeEmergency}',4) ;
    //checkFieldUpdate('typeStandart','${typeStandart}',2) ;
    checkFieldUpdate('typeViewAddStatus','${typeViewAddStatus}',2) ;
    checkFieldUpdate('typeViewServiceStream','${typeViewServiceStream}',1) ;
    checkFieldUpdate('typeView','${typeView}',2) ;
    checkFieldUpdate('typeResult','${typeResult}',2) ;
    
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
    String date = request.getParameter("dateBegin") ;
    if (date!=null && !date.equals(""))  {
    	String dateEnd = request.getParameter("dateEnd") ;
    	if (dateEnd==null||dateEnd.equals("")) {
    		request.setAttribute("dateEnd", date) ;
    	} else {
    		request.setAttribute("dateEnd", dateEnd) ;
    	}
    	request.setAttribute("dateBegin", date) ;
    	
    	if ("1".equals(typeEmergency)) {
    		request.setAttribute("emergencySql", " and hmc.emergency='1' ") ;
    	} else if ("2".equals(typeEmergency)) {
    		request.setAttribute("emergencySql", " and (hmc.emergency is null or hmc.emergency='0') ") ;
    	} 
    	if ("1".equals(typeResult)) {
    		request.setAttribute("result_dateSql"," and hmc.moveToAnotherLPU_id is not null") ;
    	} else if ("2".equals(typeResult)) {
    		request.setAttribute("result_dateSql"," and vhr.code='11'") ;
    	}
    			
    	if ("2".equals(typePolicy)) {
			request.setAttribute("policySql", " and upper(mp.dtype) like '%FOREIGN'") ;
			request.setAttribute("infoTypePolicy", "иногородние") ;
		} else if ("1".equals(typePolicy)){
			request.setAttribute("policySql", " and upper(mp.dtype) not like '%FOREIGN'") ;
			request.setAttribute("infoTypePolicy", "региональные") ;
		}
    	if (typePatient.equals("2")) { //иногородние
			request.setAttribute("patientSql", HospitalLibrary.getSqlForPatient(true, true, "m.Datestart", "p", "pvss", "pmp","ok")) ;
			request.setAttribute("infoTypePat", "Поиск по иногородним") ;
		} else if (typePatient.equals("1")){ //региональные
			request.setAttribute("patientSql", HospitalLibrary.getSqlForPatient(true, false, "m.Datestart", "p", "pvss", "pmp","ok")) ;
			request.setAttribute("infoTypePat", "Поиск по региональным") ;
		} else if (typePatient.equals("3")){ //иностранцы
			request.setAttribute("patientSql", HospitalLibrary.getSqlGringo(true, "ok")) ;
			request.setAttribute("infoTypePat", "Поиск по иностранцам") ;
		} else {
			request.setAttribute("patientSql", "") ;
			request.setAttribute("infoTypePat", "Поиск по всем") ;
		}
    	String dateSql ;
    	if ("1".equals(typeDate)) {
    		dateSql="dateStart" ;
    	} else if ("2".equals(typeDate)) {
    		dateSql="dateFinish" ;
    	} else {
    		dateSql="transferDate" ;
    	}
    	request.setAttribute("dateSql",dateSql) ;
    	if ("1".equals(typeViewAddStatus)) {
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
    	if ("1".equals(typeViewServiceStream)) {
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
    	if ("1".equals(typeView)) {
    	%>
    		<ecom:webQuery name="swod_by_standart" maxResult="15000" nameFldSql="swod_by_standart_sql" nativeSql="
        select m.id
        ,d.name as depname,ss.code as sscode
        ,p.lastname||' '||p.firstname||' '||p.middlename as fio
    ,to_char(p.birthday,'dd.mm.yyyy') as birthday
    ,to_char(m.dateStart,'dd.mm.yyyy') as mdateStart
    ,to_char(coalesce(m.dateFinish,m.transferDate),'dd.mm.yyyy') as mdateFinish
    ,      case
            when (coalesce(m.dateFinish,m.transferDate,CURRENT_DATE)-m.dateStart)=0 then 1
            when bf.addCaseDuration='1' then ((coalesce(m.dateFinish,m.transferDate,CURRENT_DATE)-m.dateStart)+1)
            else (coalesce(m.dateFinish,m.transferDate,CURRENT_DATE)-m.dateStart)
          end as cnt1
    ,to_char(hmc.dateStart,'dd.mm.yyyy') as hdateStart
    ,to_char(hmc.dateFinish,'dd.mm.yyyy') as hdateFinish
    ,      case
            when (coalesce(hmc.dateFinish,CURRENT_DATE)-hmc.dateStart)=0 then 1
            when bf.addCaseDuration='1' then ((coalesce(hmc.dateFinish,CURRENT_DATE)-hmc.dateStart)+1)
            else (coalesce(hmc.dateFinish,CURRENT_DATE)-hmc.dateStart)
          end as cnt2
    ,(select list(distinct vdrt.name||' '||vpd.name||' '||mkb.code) from Diagnosis diag left join vocidc10 mkb on mkb.id=diag.idc10_id left join VocPriorityDiagnosis vpd on vpd.id=diag.priority_id left join VocDiagnosisRegistrationType vdrt on vdrt.id=diag.registrationType_id where diag.medcase_id=m.id) as diag
    ,vhr.name as vhrname,tml.name,vs.omcCode as vsomccode,p.lastname as plastname, p.firstname as pfirstname, p.middlename as pmiddlename
    ,mp.commonNumber as mpcommonNumber
    from MedCase as m
    left join medcase as hmc on hmc.id=m.parent_id
    left join VocHospitalizationResult vhr on vhr.id=hmc.result_id
    left join statisticstub as ss on ss.id=hmc.statisticstub_id
    left join bedfund as bf on bf.id=m.bedfund_id
    left join vocbedsubtype as vbst on vbst.id=bf.bedSubType_id
    left join vocbedtype as vbt on vbt.id=bf.bedtype_id
    left join mislpu as d on d.id=m.department_id
    left join patient as p on p.id=m.patient_id
left join VocSex vs on vs.id=p.sex_id
    left join VocSocialStatus pvss on pvss.id=p.socialStatus_id
    left join Omc_Oksm ok on p.nationality_id=ok.id
    left join VocServiceStream vss on vss.id=m.serviceStream_id
    left join MisLpu tml on tml.id=hmc.moveToAnotherLPU_id
    left join MedCase_MedPolicy mcmp on mcmp.medCase_id=hmc.id  
	left join medpolicy mp on mp.id=mcmp.policies_id  

    where m.DTYPE='DepartmentMedCase' and m.${dateSql} between
    to_date('${dateBegin}','dd.mm.yyyy')  and to_date('${dateEnd}','dd.mm.yyyy')
    ${departmentSql} ${emergencySql} ${serviceStreamSql} ${bedTypeSql} ${bedSubTypeSql}
    ${patientSql} ${additionStatusSql} ${result_dateSql}
    ${policySql} ${sexSql}
    order by  p.lastname,p.firstname,p.middlename
        "/>
   <form action="print-stac_report_bedFund_reestr.do" method="post" target="_blank">
    Период с ${dateBegin} по ${dateEnd}.
    <input type='hidden' name="sqlText" id="sqlText" value="${swod_by_standart_sql}"> 
    <input type='hidden' name="sqlInfo" id="sqlInfo" value="">
    <input type='hidden' name="s" id="s" value="PrintService">
    <input type='hidden' name="m" id="m" value="printNativeQuery">
    <input type="submit" value="Печать всего" onclick="this.form.action='print-stac_report_bedFund_reestr.do'"> 
    <input type="submit" value="Печать для проверки" onclick="this.form.action='print-stac_report_bedFund_reestr_inog.do'"> 
    </form>
    	<msh:table name="swod_by_standart" selection="multiply" printToExcelButton="Сохранить в excel"
    	action="entityParentView-stac_slo.do" viewUrl="entityShortView-stac_slo.do" idField="1">
		      <msh:tableColumn columnName="#" property="sn"  />
		      <msh:tableColumn columnName="Отделение" property="2"  />
		      <msh:tableColumn columnName="№ИБ" property="3"  />
		      <msh:tableColumn columnName="Фамилия имя отчество пациента" property="4"  />
		      <msh:tableColumn columnName="Год рождения" property="5"  />
		      <msh:tableColumn columnName="Дата пост. (отд)" property="6"  />
		      <msh:tableColumn columnName="Дата выписки / перевода (отд)" property="7"  />
		      <msh:tableColumn columnName="К.Д по отд" property="8"/>
		      <msh:tableColumn cssClass="NotViewInfoStac" columnName="Дата пост. (стац)" property="9"  />
		      <msh:tableColumn cssClass="NotViewInfoStac" columnName="Дата выписки (стац)" property="10"  />
		      <msh:tableColumn cssClass="NotViewInfoStac" columnName="К.Д по стац" property="11"/>
		      <msh:tableColumn columnName="Диагноз" property="12"/>
		      <msh:tableColumn columnName="Результат госпитализации" property="13"/>
		      <msh:tableColumn columnName="Переведен в ЛПУ" property="14"/>
    	</msh:table>
    	<%
    	} else {
    		//Начинается свод
    		
    	if ("3".equals(typeView)) {
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
    	||${bedSubTypeSqlId}||${serviceStreamSqlId}||${sexSqlId} as id
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
    left join MedCase_MedPolicy mcmp on mcmp.medCase_id=hmc.id  
	left join MedPolicy mp on mp.id=mcmp.policies_id  
    
    where m.DTYPE='DepartmentMedCase' and m.${dateSql} between 
    to_date('${dateBegin}','dd.mm.yyyy')  and to_date('${dateEnd}','dd.mm.yyyy') 
    ${departmentSql}  ${emergencySql} ${serviceStreamSql} ${bedTypeSql} ${bedSubTypeSql} 
    ${patientSql} ${additionStatusSql} ${result_dateSql}
    ${policySql} ${sexSql}
    group by  
		m.department_id , d.name,
		${viewDateGroup} ${viewServiceStreamGroup} ${viewAddStatusGroup} 
	 vbt.id , vbt.name,vbst.id,vbst.name
    order by  ${viewDateOrder} d.name,${viewServiceStreamOrder} ${viewAddStatusOrder} vbt.name,vbst.name
    	"/>
    	<msh:table name="swod_by_standart"  selection="multiply" printToExcelButton="Сохранить в excel"
    	viewUrl="stac_groupByBedFundList.do?short=Short&typeResult=${typeResult}&typeEmergency=${typeEmergency}&typePatient=${typePatient}&typePolicy=${typePolicy}&typeView=1"
    	action="stac_groupByBedFundList.do?typeEmergency=${typeEmergency}&typePatient=${typePatient}&typePolicy=${typePolicy}&typeView=1" idField="1">
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