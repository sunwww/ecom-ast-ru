<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://struts.apache.org/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://www.nuzmsh.ru/tags/msh" prefix="msh" %>
<%@ taglib uri="http://www.ecom-ast.ru/tags/ecom" prefix="ecom" %>
<%@ taglib tagdir="/WEB-INF/tags" prefix="tags" %>

<tiles:insert page="/WEB-INF/tiles/mainLayout.jsp" flush="true" >
	<tiles:put name="style" type="string">
	
	</tiles:put>

    <tiles:put name='title' type='string'>
        <msh:title mainMenu="Journals">АНАЛИЗ РАБОТЫ УЧРЕЖДЕНИЯ
        </msh:title>
    </tiles:put>

    <tiles:put name='side' type='string'>
        <tags:stac_journal currentAction="stac_analysis_department"/>
    </tiles:put>
    
  <tiles:put name="body" type="string">
    <msh:form action="/stac_analysis_department_list.do" defaultField="dateBegin" disableFormDataConfirm="true" method="GET" guid="d7b31bc2-38f0-42cc-8d6d-19395273168f">
    <msh:panel guid="6ae283c8-7035-450a-8eb4-6f0f7da8a8ff">
    <input type="hidden" value="printDeathList" name="m">
    <input type="hidden" value="HospitalReportService" name="s">
    <input type="hidden" value="" name="param">
      <msh:row>
        <msh:separator label="Дополнительные параметры для реестра (в своде не учитываются)" colSpan="7"/>
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

      <msh:row>
        <msh:separator label="Основные параметры" colSpan="7"/>
      </msh:row>
      <msh:row guid="7d80be13-710c-46b8-8503-ce0413686b69">
        <td class="label" title="Поиск по дате  (typeDate)" colspan="1"><label for="typeDateName" id="typeDateLabel">Искать по дате:</label></td>
        <td onclick="this.childNodes[1].checked='checked';">
        	<input type="radio" name="typeDate" value="1">  поступления
        </td>
        <td onclick="this.childNodes[1].checked='checked';" colspan="2">
        	<input type="radio" name="typeDate" value="2">  выписки
        </td>
        </msh:row>
        <msh:row>
	        <td class="label" title="Просмотр данных (typeView)" colspan="1"><label for="typeViewName" id="typeViewLabel">Отобразить:</label></td>
	        <td onclick="this.childNodes[1].checked='checked';" colspan="3">
	        	<input type="radio" name="typeView" value="1">  хир. реестр по выпис. отделениям
	        </td>
	        <td onclick="this.childNodes[1].checked='checked';" colspan="3">
	        	<input type="radio" name="typeView" value="2">  хир. реестр, разные отд. (выписки и опер.)
	        </td>
        </msh:row>
        <msh:row>
        	<td></td>
	        <td onclick="this.childNodes[1].checked='checked';" colspan="2">
	        	<input type="radio" name="typeView" value="3"  >  анализ хир. работы
	        </td>
	        <td onclick="this.childNodes[1].checked='checked';" colspan="5">
	        	<input type="radio" name="typeView" value="4"  >  свод по отд. по разным отд. (выписки и опер.)
	        </td>
        </msh:row>
        <msh:row>
        	<td></td>
	        <td onclick="this.childNodes[1].checked='checked';" colspan="2">
	        	<input type="radio" name="typeView" value="5"  >  свод по отделениям
	        </td>
	        <td onclick="this.childNodes[1].checked='checked';" colspan="5">
	        	<input type="radio" name="typeView" value="6"  >  свод по отделениям по районам
	        </td>
	    </msh:row>
        <msh:row>
        	<td></td>
	        <td onclick="this.childNodes[1].checked='checked';" colspan="2">
	        	<input type="radio" name="typeView" value="7"  >  свод по леч.врачам (отд)
	        </td>
	        <td onclick="this.childNodes[1].checked='checked';" colspan="2">
	        	<input type="radio" name="typeView" value="8"  >  свод по хирургам (отд)
	        </td>
	        <td onclick="this.childNodes[1].checked='checked';" colspan="5">
	        	<input type="radio" name="typeView" value="9"  >  свод по хирургам
	        </td>
	    </msh:row>
        <msh:row>
        	<td></td>

	        <td onclick="this.childNodes[1].checked='checked';" colspan="5">
	        	<input type="radio" name="typeView" value="10"  >  реестр операций с 0 уровнем сложности
	        </td>
	    </msh:row>
        <msh:row>
        	<msh:autoComplete property="department" fieldColSpan="6" horizontalFill="true" label="Отделение" vocName="vocLpuHospOtdAll"/>
        </msh:row>
        <msh:row>
	        <msh:textField property="dateBegin" label="Период с" guid="8d7ef035-1273-4839-a4d8-1551c623caf1" />
	        <msh:textField property="dateEnd" label="по" guid="f54568f6-b5b8-4d48-a045-ba7b9f875245" />
		<td colspan="3">
            <input type="submit" onclick="find()" value="Найти" />
<!--            <input type="submit" onclick="print()" value="Печать" />-->
             <input type="submit" onclick="printReestr()" value="Реестр пациентов в excel" /> 
          </td>
         </msh:row>
        
    </msh:panel>
    </msh:form>
    
    <%
    String date = (String)request.getParameter("dateBegin") ;
    String view = (String)request.getAttribute("typeView") ;
    if (date!=null && !date.equals("") )  {
    	String dateEnd = (String)request.getParameter("dateEnd") ;
    	if (dateEnd==null||dateEnd.equals("")) {
    		request.setAttribute("dateEnd", date) ;
    	} else {
    		request.setAttribute("dateEnd", dateEnd) ;
    	}
    	String dep = (String) request.getParameter("department") ; 
    	if (dep!=null && !dep.equals("") && !dep.equals("0")) {
    		request.setAttribute("dep", " and dmc.department_id='"+dep+"'");
    		request.setAttribute("depOper", " and so.department_id='"+dep+"'");
    	} else{
    		request.setAttribute("dep", "") ;
    	}
    	if (view!=null && (view.equals("1"))) {
    	%>
    
    <msh:section title="${infoTypePat} ${infoTypeEmergency} ${infoTypeOperation}. Период с ${param.dateBegin} по ${dateEnd}. ${infoSearch} ${dateInfo}">
    <msh:sectionContent>
    <ecom:webQuery name="journal_list" nativeSql="
    
select 
soDep.id as soDepid,dep.name as depname
,voDep.code as voDepcode,voDep.name as voDepname
,ss.code as sscode
    ,pat.lastname||' '||pat.firstname||' '||coalesce(pat.middlename,'') as fio
    ,(hmc.dateFinish-pat.birthday)/365 as age
    ,case when hmc.emergency='1' then 'Э' else 'П' end
    
    ,vpat.name as vpatname
    , case 
		when (coalesce(hmc.dateFinish,CURRENT_DATE)-hmc.dateStart)=0 then 1 
		when vht.code='DAYTIMEHOSP' then ((coalesce(hmc.dateFinish,CURRENT_DATE)-hmc.dateStart)+1) 
		else (coalesce(hmc.dateFinish,CURRENT_DATE)-hmc.dateStart)
		end as countDays
	,hmc.dateStart as hmcdatestart,hmc.dateFinish as hmcdatefinish
    , (select list(mkb.code||' '||mkb.name) from diagnosis diag
    	left join VocIdc10 mkb on mkb.id=idc10_id 
    	left join VocPriorityDiagnosis vpd on vpd.id=diag.priority_id
    	left join VocDiagnosisRegistrationType vdrt on vdrt.id=diag.registrationType_id
    	where diag.medCase_id=hmc.id  
    	and vpd.code='1' and vdrt.code='4'
		) as diag
		,soDep.operationDate-hmc.dateStart as durationBefore
		,hmc.dateFinish-soDep.operationDate as durationAfter
		,soDep.operationDate
		,soD.name as soDname
		,vhaDep.name as vhaDepname
from MedCase hmc
    left join statisticstub ss on hmc.statisticstub_id=ss.id
    left join MedCase as dmc on dmc.dtype='DepartmentMedCase' and hmc.id=dmc.parent_id 
    left join MedCase as admc on admc.dtype='DepartmentMedCase' and hmc.id=admc.parent_id 
    left join vocservicestream as vss on vss.id=hmc.servicestream_id
    left join VocPreAdmissionTime vpat on vpat.id=hmc.preAdmissionTime_id 
    left join mislpu as dep on dep.id=dmc.department_id 
    left join patient pat on pat.id=hmc.patient_id
    left join VocHospType vht on vht.id=hmc.hospType_id
    left join address2 adr on adr.addressId = pat.address_addressId
    left join vocHospitalizationResult vhr on vhr.id=hmc.result_id
    left join SurgicalOperation soHosp on soHosp.medCase_id=hmc.id
    left join MedService voHosp on soHosp.medService_id=voHosp.id
    left join SurgicalOperation soDep on soDep.medCase_id=admc.id
    left join mislpu as soD on soD.id=soDep.department_id 
    left join MedService voDep on soDep.medService_id=voDep.id
    left join Omc_Oksm ok on pat.nationality_id=ok.id
    left join VocHospitalAspect vhaHosp on vhaHosp.id=soHosp.aspect_id
    left join VocHospitalAspect vhaDep on vhaDep.id=soDep.aspect_id
    
where hmc.DTYPE='HospitalMedCase' 
    and ${dateT} between to_date('${param.dateBegin}','dd.mm.yyyy')  
    	and to_date('${dateEnd}','dd.mm.yyyy') 
    	
    	and dmc.dateFinish is not null
    	${dep}
    	and soDep.id is not null
    
    ${addEmergency} 
order by dep.name   
    " guid="4a720225-8d94-4b47-bef3-4dbbe79eec74" />
        <msh:table name="journal_list"
        viewUrl="entityShortView-stac_surOperation.do"
         action="entityView-stac_surOperation.do" idField="1" noDataMessage="Не найдено">
            <msh:tableColumn columnName="#" property="sn"/>
            <msh:tableColumn columnName="Отделение выписки" property="2"/>
            <msh:tableColumn columnName="Отделение, где производилась операция" property="17"/>
            <msh:tableColumn columnName="№ стат. карты" property="5"/>
            <msh:tableColumn columnName="ФАМИЛИЯ ИМЯ ОТЧЕСТВО" property="6"/>
            <msh:tableColumn columnName="Возраст" property="7"/>
            <msh:tableColumn columnName="Поступил" property="8"/>
            <msh:tableColumn columnName="Доставлен в стационар" property="9"/>
            <msh:tableColumn columnName="Кол-во койко дней" property="10"/>
            <msh:tableColumn columnName="Дата поступления" property="11"/>
            <msh:tableColumn columnName="Дата выписки" property="12"/>
            <msh:tableColumn columnName="Дата операции" property="16"/>
            <msh:tableColumn columnName="Диагноз" property="13"/>
            <msh:tableColumn columnName="Операции" property="3"/>
            <msh:tableColumn columnName="До операции" property="14"/>
            <msh:tableColumn columnName="После операции" property="15"/>
            <msh:tableColumn columnName="Показания" property="17"/>
        </msh:table>
    </msh:sectionContent>
    </msh:section>
    <%
    } 
    	if (view!=null && view.equals("2")) { 
    %>
    
    <msh:section title="Реестр хир. операций, где разные отделения выписки и где проводилась операция. ${infoTypePat} ${infoTypeEmergency} ${infoTypeOperation}. Период с ${param.dateBegin} по ${dateEnd}. ${infoSearch} ${dateInfo}">
    <msh:sectionContent>
    <ecom:webQuery name="journal_list_suroper" nativeSql="
    
select 
soDep.id as soDepid,dep.name as depname
,voDep.code as voDepcode,voDep.name as voDepname
,ss.code as sscode
    ,pat.lastname||' '||pat.firstname||' '||coalesce(pat.middlename,'') as fio
    ,(hmc.dateFinish-pat.birthday)/365 as age
    ,case when hmc.emergency='1' then 'Э' else 'П' end
    
    ,vpat.name as vpatname
    , case 
		when (coalesce(hmc.dateFinish,CURRENT_DATE)-hmc.dateStart)=0 then 1 
		when vht.code='DAYTIMEHOSP' then ((coalesce(hmc.dateFinish,CURRENT_DATE)-hmc.dateStart)+1) 
		else (coalesce(hmc.dateFinish,CURRENT_DATE)-hmc.dateStart)
		end as countDays
	,hmc.dateStart as hmcdatestart,hmc.dateFinish as hmcdatefinish
    , (select list(mkb.code||' '||mkb.name) from diagnosis diag
    	left join VocIdc10 mkb on mkb.id=idc10_id 
    	left join VocPriorityDiagnosis vpd on vpd.id=diag.priority_id
    	left join VocDiagnosisRegistrationType vdrt on vdrt.id=diag.registrationType_id
    	where diag.medCase_id=hmc.id  
    	and vpd.code='1' and vdrt.code='4'
		) as diag
		,soDep.operationDate-hmc.dateStart as durationBefore
		,hmc.dateFinish-soDep.operationDate as durationAfter
		,soDep.operationDate
		,soD.name as soDname
from MedCase hmc
    left join statisticstub ss on hmc.statisticstub_id=ss.id
    left join MedCase as dmc on dmc.dtype='DepartmentMedCase' and hmc.id=dmc.parent_id 
    left join MedCase as admc on admc.dtype='DepartmentMedCase' and hmc.id=admc.parent_id 
    left join vocservicestream as vss on vss.id=hmc.servicestream_id
    left join VocPreAdmissionTime vpat on vpat.id=hmc.preAdmissionTime_id 
    left join mislpu as dep on dep.id=dmc.department_id 
    left join patient pat on pat.id=hmc.patient_id
    left join VocHospType vht on vht.id=hmc.hospType_id
    left join address2 adr on adr.addressId = pat.address_addressId
    left join vocHospitalizationResult vhr on vhr.id=hmc.result_id
    left join SurgicalOperation soHosp on soHosp.medCase_id=hmc.id
    left join MedService voHosp on soHosp.medService_id=voHosp.id
    left join SurgicalOperation soDep on soDep.medCase_id=admc.id
    left join mislpu as soD on soD.id=soDep.department_id 
    left join MedService voDep on soDep.medService_id=voDep.id
    left join Omc_Oksm ok on pat.nationality_id=ok.id
where hmc.DTYPE='HospitalMedCase' 
    and ${dateT} between to_date('${param.dateBegin}','dd.mm.yyyy')  
    	and to_date('${dateEnd}','dd.mm.yyyy') 
    	
    	and dmc.dateFinish is not null
    	${dep}
    	and soDep.id is not null
    	and dmc.department_id!=soDep.department_id
    ${addEmergency} 
order by dep.name   
    " guid="4a720225-8d94-4b47-bef3-4dbbe79eec74" />
        <msh:table name="journal_list_suroper"
        viewUrl="entityShortView-stac_surOperation.do"
         action="entityView-stac_surOperation.do" idField="1" noDataMessage="Не найдено">
            <msh:tableColumn columnName="#" property="sn"/>
            <msh:tableColumn columnName="Отделение выписки" property="2"/>
            <msh:tableColumn columnName="Отделение, где производилась операция" property="17"/>
            <msh:tableColumn columnName="№ стат. карты" property="5"/>
            <msh:tableColumn columnName="ФАМИЛИЯ ИМЯ ОТЧЕСТВО" property="6"/>
            <msh:tableColumn columnName="Возраст" property="7"/>
            <msh:tableColumn columnName="Поступил" property="8"/>
            <msh:tableColumn columnName="Доставлен в стационар" property="9"/>
            <msh:tableColumn columnName="Кол-во койко дней" property="10"/>
            <msh:tableColumn columnName="Дата поступления" property="11"/>
            <msh:tableColumn columnName="Дата выписки" property="12"/>
            <msh:tableColumn columnName="Дата операции" property="16"/>
            <msh:tableColumn columnName="Диагноз" property="13"/>
            <msh:tableColumn columnName="Операции" property="3"/>
            <msh:tableColumn columnName="До операции" property="14"/>
            <msh:tableColumn columnName="После операции" property="15"/>
        </msh:table>
    </msh:sectionContent>
    </msh:section>
    <%
    } 
    	if (view!=null && view.equals("3")) { 
    %>
    <msh:section title="Анализ хирургической работы учреждения">
    <msh:sectionContent>
    <ecom:webQuery name="journal_list_swod" nativeSql="
    select
dmc.department_id,dep.name
,count(distinct hmc.id) as cntDischarge

,(select count(distinct hmc1.id)  from medcase hmc1 
 left join MedCase dmc1 on dmc1.parent_id=hmc1.id and dmc1.dtype='DepartmentMedCase'
 left join SurgicalOperation so1 on so1.medCase_id=dmc1.id
 left join VocHospitalAspect vha1 on vha1.id=so1.aspect_id
 where 
 hmc1.dtype='HospitalMedCase' and ${dateT1} between to_date('${param.dateBegin}','dd.mm.yyyy')  
    	and to_date('${dateEnd}','dd.mm.yyyy') and 
 hmc1.dateFinish is not null 
and hmc1.dischargeTime is not null and so1.department_id=dmc.department_id
) as cntOperPat

,(select count(distinct case when vha1.code='EMERGENCY' then hmc1.id else null end)  from medcase hmc1 
 left join MedCase dmc1 on dmc1.parent_id=hmc1.id and dmc1.dtype='DepartmentMedCase'
 left join SurgicalOperation so1 on so1.medCase_id=dmc1.id
 left join VocHospitalAspect vha1 on vha1.id=so1.aspect_id
 where 
  hmc1.dtype='HospitalMedCase' and ${dateT1} between to_date('${param.dateBegin}','dd.mm.yyyy')  
    	and to_date('${dateEnd}','dd.mm.yyyy') and 
 hmc1.dateFinish is not null 
and hmc1.dischargeTime is not null and so1.department_id=dmc.department_id
) as cntEmerPat

,(select count(distinct case when (vha1.code='PLAN') then hmc1.id else null end)  from medcase hmc1 
 left join MedCase dmc1 on dmc1.parent_id=hmc1.id and dmc1.dtype='DepartmentMedCase'
 left join SurgicalOperation so1 on so1.medCase_id=dmc1.id
 left join VocHospitalAspect vha1 on vha1.id=so1.aspect_id
 where 
  hmc1.dtype='HospitalMedCase' and ${dateT1} between to_date('${param.dateBegin}','dd.mm.yyyy')  
    	and to_date('${dateEnd}','dd.mm.yyyy') and 
  hmc1.dateFinish is not null 
and hmc1.dischargeTime is not null and so1.department_id=dmc.department_id
) as cntPlanPat

,(select (count(distinct case when vha1.code='PLAN' then hmc1.id else null end) +count(distinct case when vha1.code='EMERGENCY' then hmc1.id else null end)-count(distinct hmc1.id)) from medcase hmc1 
 left join MedCase dmc1 on dmc1.parent_id=hmc1.id and dmc1.dtype='DepartmentMedCase'
 left join SurgicalOperation so1 on so1.medCase_id=dmc1.id
 left join VocHospitalAspect vha1 on vha1.id=so1.aspect_id
 where 
  hmc1.dtype='HospitalMedCase' and ${dateT1} between to_date('${param.dateBegin}','dd.mm.yyyy')  
    	and to_date('${dateEnd}','dd.mm.yyyy') and 
  hmc1.dateFinish is not null 
and hmc1.dischargeTime is not null and so1.department_id=dmc.department_id
) as cntEmerAndPlanPat


,(select count(distinct so1.id)  from medcase hmc1 
 left join MedCase dmc1 on dmc1.parent_id=hmc1.id and dmc1.dtype='DepartmentMedCase'
 left join SurgicalOperation so1 on so1.medCase_id=dmc1.id
 where  hmc1.dtype='HospitalMedCase' and ${dateT1} between to_date('${param.dateBegin}','dd.mm.yyyy')  
    	and to_date('${dateEnd}','dd.mm.yyyy') and 
 hmc1.dateFinish is not null 
and hmc1.dischargeTime is not null and so1.department_id=dmc.department_id
) as cntOper

,(select count( case when vha1.code='EMERGENCY' then hmc1.id else null end)  from medcase hmc1 
 left join MedCase dmc1 on dmc1.parent_id=hmc1.id and dmc1.dtype='DepartmentMedCase'
 left join SurgicalOperation so1 on so1.medCase_id=dmc1.id
 left join VocHospitalAspect vha1 on vha1.id=so1.aspect_id
 where  hmc1.dtype='HospitalMedCase' and ${dateT1} between to_date('${param.dateBegin}','dd.mm.yyyy')  
    	and to_date('${dateEnd}','dd.mm.yyyy') and 
 hmc1.dateFinish is not null 
and hmc1.dischargeTime is not null and so1.department_id=dmc.department_id
) as cntEmerOper
,(select count( case when vha1.code='PLAN' then hmc1.id else null end)  from medcase hmc1 
 left join MedCase dmc1 on dmc1.parent_id=hmc1.id and dmc1.dtype='DepartmentMedCase'
 left join SurgicalOperation so1 on so1.medCase_id=dmc1.id
 left join VocHospitalAspect vha1 on vha1.id=so1.aspect_id
 where  hmc1.dtype='HospitalMedCase' and ${dateT1} between to_date('${param.dateBegin}','dd.mm.yyyy')  
    	and to_date('${dateEnd}','dd.mm.yyyy') and 
 hmc1.dateFinish is not null 
and hmc1.dischargeTime is not null and so1.department_id=dmc.department_id
) as cntPlanOper

,case when (select count(distinct so1.id)  from medcase hmc1 
 left join MedCase dmc1 on dmc1.parent_id=hmc1.id and dmc1.dtype='DepartmentMedCase'
 left join SurgicalOperation so1 on so1.medCase_id=dmc1.id
 where  hmc1.dtype='HospitalMedCase' and ${dateT1} between to_date('${param.dateBegin}','dd.mm.yyyy')  
    	and to_date('${dateEnd}','dd.mm.yyyy') and 
 hmc1.dateFinish is not null 
and hmc1.dischargeTime is not null and so1.department_id=dmc.department_id
)>0 then
cast(round(100*(select count( case when vha1.code='EMERGENCY' then hmc1.id else null end)  from medcase hmc1 
 left join MedCase dmc1 on dmc1.parent_id=hmc1.id and dmc1.dtype='DepartmentMedCase'
 left join SurgicalOperation so1 on so1.medCase_id=dmc1.id
 left join VocHospitalAspect vha1 on vha1.id=so1.aspect_id
 where  hmc1.dtype='HospitalMedCase' and ${dateT1} between to_date('${param.dateBegin}','dd.mm.yyyy')  
    	and to_date('${dateEnd}','dd.mm.yyyy') and 
 hmc1.dateFinish is not null 
and hmc1.dischargeTime is not null and so1.department_id=dmc.department_id
)/cast((select count(distinct so1.id)  from medcase hmc1 
 left join MedCase dmc1 on dmc1.parent_id=hmc1.id and dmc1.dtype='DepartmentMedCase'
 left join SurgicalOperation so1 on so1.medCase_id=dmc1.id
 where  hmc1.dtype='HospitalMedCase' and ${dateT1} between to_date('${param.dateBegin}','dd.mm.yyyy')  
    	and to_date('${dateEnd}','dd.mm.yyyy') and 
 hmc1.dateFinish is not null 
and hmc1.dischargeTime is not null and so1.department_id=dmc.department_id
) as numeric) ,2) as numeric) else 0 end as procEmer

,case when (select count(distinct so1.id)  from medcase hmc1 
 left join MedCase dmc1 on dmc1.parent_id=hmc1.id and dmc1.dtype='DepartmentMedCase'
 left join SurgicalOperation so1 on so1.medCase_id=dmc1.id
 where  hmc1.dtype='HospitalMedCase' and ${dateT1} between to_date('${param.dateBegin}','dd.mm.yyyy')  
    	and to_date('${dateEnd}','dd.mm.yyyy') and 
 hmc1.dateFinish is not null 
and hmc1.dischargeTime is not null and so1.department_id=dmc.department_id
)>0 then cast(round(100*(select count( case when vha1.code='PLAN' then hmc1.id else null end)  from medcase hmc1 
 left join MedCase dmc1 on dmc1.parent_id=hmc1.id and dmc1.dtype='DepartmentMedCase'
 left join SurgicalOperation so1 on so1.medCase_id=dmc1.id
 left join VocHospitalAspect vha1 on vha1.id=so1.aspect_id
 where  hmc1.dtype='HospitalMedCase' and ${dateT1} between to_date('${param.dateBegin}','dd.mm.yyyy')  
    	and to_date('${dateEnd}','dd.mm.yyyy') and 
 hmc1.dateFinish is not null 
and hmc1.dischargeTime is not null and so1.department_id=dmc.department_id
)/cast((select count(distinct so1.id)  from medcase hmc1 
 left join MedCase dmc1 on dmc1.parent_id=hmc1.id and dmc1.dtype='DepartmentMedCase'
 left join SurgicalOperation so1 on so1.medCase_id=dmc1.id
 where  hmc1.dtype='HospitalMedCase' and ${dateT1} between to_date('${param.dateBegin}','dd.mm.yyyy')  
    	and to_date('${dateEnd}','dd.mm.yyyy') and 
 hmc1.dateFinish is not null 
and hmc1.dischargeTime is not null and so1.department_id=dmc.department_id
) as numeric) ,2) as numeric) else 0 end  as procPlan

,case when count(distinct hmc.id)>0 then cast(round(100*(select count(distinct hmc1.id)  from medcase hmc1 
 left join MedCase dmc1 on dmc1.parent_id=hmc1.id and dmc1.dtype='DepartmentMedCase'
 left join SurgicalOperation so1 on so1.medCase_id=dmc1.id
 where  hmc1.dtype='HospitalMedCase' and ${dateT1} between to_date('${param.dateBegin}','dd.mm.yyyy')  
    	and to_date('${dateEnd}','dd.mm.yyyy') and 
 hmc1.dateFinish is not null 
and hmc1.dischargeTime is not null and so1.department_id=dmc.department_id
)/cast(count(distinct hmc.id) as numeric) ,1) as numeric) else 0 end as alanysisPat

,case when count(distinct hmc.id)>0 then cast(round(100*(select count(distinct so1.id)  from medcase hmc1 
 left join MedCase dmc1 on dmc1.parent_id=hmc1.id and dmc1.dtype='DepartmentMedCase'
 left join SurgicalOperation so1 on so1.medCase_id=dmc1.id
 where  hmc1.dtype='HospitalMedCase' and ${dateT1} between to_date('${param.dateBegin}','dd.mm.yyyy')  
    	and to_date('${dateEnd}','dd.mm.yyyy') and 
 hmc1.dateFinish is not null 
and hmc1.dischargeTime is not null and so1.department_id=dmc.department_id
) /cast(count(distinct hmc.id) as numeric) ,1) as numeric) else 0 end  as analysisOper


,case when (select count(so1.id)  from medcase hmc1 
 left join MedCase dmc1 on dmc1.parent_id=hmc1.id and dmc1.dtype='DepartmentMedCase'
 left join SurgicalOperation so1 on so1.medCase_id=dmc1.id
 left join VocHospitalAspect vha1 on vha1.id=so1.aspect_id
 where  hmc1.dtype='HospitalMedCase' and ${dateT1} between to_date('${param.dateBegin}','dd.mm.yyyy')  
    	and to_date('${dateEnd}','dd.mm.yyyy') and 
 hmc1.dateFinish is not null 
and hmc1.dischargeTime is not null and so1.department_id=dmc.department_id
and (vha1.code='PLAN')
)>0 then
cast(round((select sum(so1.operationDate-hmc1.dateStart)  from medcase hmc1 
 left join MedCase dmc1 on dmc1.parent_id=hmc1.id and dmc1.dtype='DepartmentMedCase'
 left join SurgicalOperation so1 on so1.medCase_id=dmc1.id
 left join VocHospitalAspect vha1 on vha1.id=so1.aspect_id
 where  hmc1.dtype='HospitalMedCase' and ${dateT1} between to_date('${param.dateBegin}','dd.mm.yyyy')  
    	and to_date('${dateEnd}','dd.mm.yyyy') and 
 hmc1.dateFinish is not null 
and hmc1.dischargeTime is not null and so1.department_id=dmc.department_id
and (vha1.code='PLAN')
) / cast((select count(so1.id)  from medcase hmc1 
 left join MedCase dmc1 on dmc1.parent_id=hmc1.id and dmc1.dtype='DepartmentMedCase'
 left join SurgicalOperation so1 on so1.medCase_id=dmc1.id
 left join VocHospitalAspect vha1 on vha1.id=so1.aspect_id
 where  hmc1.dtype='HospitalMedCase' and ${dateT1} between to_date('${param.dateBegin}','dd.mm.yyyy')  
    	and to_date('${dateEnd}','dd.mm.yyyy') and 
 hmc1.dateFinish is not null 
and hmc1.dischargeTime is not null and so1.department_id=dmc.department_id
and (vha1.code='PLAN')
) as numeric),1) as numeric)  else 0 end as srDayPlanTo

,case when (select count(so1.id)  from medcase hmc1 
 left join MedCase dmc1 on dmc1.parent_id=hmc1.id and dmc1.dtype='DepartmentMedCase'
 left join SurgicalOperation so1 on so1.medCase_id=dmc1.id
 left join VocHospitalAspect vha1 on vha1.id=so1.aspect_id
 where  hmc1.dtype='HospitalMedCase' and ${dateT1} between to_date('${param.dateBegin}','dd.mm.yyyy')  
    	and to_date('${dateEnd}','dd.mm.yyyy') and 
 hmc1.dateFinish is not null 
and hmc1.dischargeTime is not null and so1.department_id=dmc.department_id
and (vha1.code='PLAN')
)>0 then
cast(round((select sum(hmc1.dateFinish-so1.operationDate) from medcase hmc1 
 left join MedCase dmc1 on dmc1.parent_id=hmc1.id and dmc1.dtype='DepartmentMedCase'
 left join SurgicalOperation so1 on so1.medCase_id=dmc1.id
 left join VocHospitalAspect vha1 on vha1.id=so1.aspect_id
 where  hmc1.dtype='HospitalMedCase' and ${dateT1} between to_date('${param.dateBegin}','dd.mm.yyyy')  
    	and to_date('${dateEnd}','dd.mm.yyyy') and 
 hmc1.dateFinish is not null 
and hmc1.dischargeTime is not null and so1.department_id=dmc.department_id
and (vha1.code='PLAN')
) / cast((select count(so1.id)  from medcase hmc1 
 left join MedCase dmc1 on dmc1.parent_id=hmc1.id and dmc1.dtype='DepartmentMedCase'
 left join SurgicalOperation so1 on so1.medCase_id=dmc1.id
 left join VocHospitalAspect vha1 on vha1.id=so1.aspect_id
 where  hmc1.dtype='HospitalMedCase' and ${dateT1} between to_date('${param.dateBegin}','dd.mm.yyyy')  
    	and to_date('${dateEnd}','dd.mm.yyyy') and 
 hmc1.dateFinish is not null 
and hmc1.dischargeTime is not null and so1.department_id=dmc.department_id
and (vha1.code='PLAN')
) as numeric),1) as numeric) else 0 end as srDayPlanAfter


,case when (select count(so1.id)  from medcase hmc1 
 left join MedCase dmc1 on dmc1.parent_id=hmc1.id and dmc1.dtype='DepartmentMedCase'
 left join SurgicalOperation so1 on so1.medCase_id=dmc1.id
 left join VocHospitalAspect vha1 on vha1.id=so1.aspect_id
 where  hmc1.dtype='HospitalMedCase' and ${dateT1} between to_date('${param.dateBegin}','dd.mm.yyyy')  
    	and to_date('${dateEnd}','dd.mm.yyyy') and 
 hmc1.dateFinish is not null 
and hmc1.dischargeTime is not null and so1.department_id=dmc.department_id
and (vha1.code='EMERGENCY')
)>0 then
cast(round((select sum(so1.operationDate-hmc1.dateStart)  from medcase hmc1 
 left join MedCase dmc1 on dmc1.parent_id=hmc1.id and dmc1.dtype='DepartmentMedCase'
 left join SurgicalOperation so1 on so1.medCase_id=dmc1.id
 left join VocHospitalAspect vha1 on vha1.id=so1.aspect_id
 where  hmc1.dtype='HospitalMedCase' and ${dateT1} between to_date('${param.dateBegin}','dd.mm.yyyy')  
    	and to_date('${dateEnd}','dd.mm.yyyy') and 
 hmc1.dateFinish is not null 
and hmc1.dischargeTime is not null and so1.department_id=dmc.department_id
and (vha1.code='EMERGENCY')
) / cast((select count(so1.id)  from medcase hmc1 
 left join MedCase dmc1 on dmc1.parent_id=hmc1.id and dmc1.dtype='DepartmentMedCase'
 left join SurgicalOperation so1 on so1.medCase_id=dmc1.id
 left join VocHospitalAspect vha1 on vha1.id=so1.aspect_id
 where  hmc1.dtype='HospitalMedCase' and ${dateT1} between to_date('${param.dateBegin}','dd.mm.yyyy')  
    	and to_date('${dateEnd}','dd.mm.yyyy') and 
 hmc1.dateFinish is not null 
and hmc1.dischargeTime is not null and so1.department_id=dmc.department_id
and (vha1.code='EMERGENCY')
) as numeric),1) as numeric)  else 0 end as srDayEmerTo

,case when (select count(so1.id)  from medcase hmc1 
 left join MedCase dmc1 on dmc1.parent_id=hmc1.id and dmc1.dtype='DepartmentMedCase'
 left join SurgicalOperation so1 on so1.medCase_id=dmc1.id
 left join VocHospitalAspect vha1 on vha1.id=so1.aspect_id
 where  hmc1.dtype='HospitalMedCase' and ${dateT1} between to_date('${param.dateBegin}','dd.mm.yyyy')  
    	and to_date('${dateEnd}','dd.mm.yyyy') and 
 hmc1.dateFinish is not null 
and hmc1.dischargeTime is not null and so1.department_id=dmc.department_id
and (vha1.code='EMERGENCY')
)>0 then
cast(round((select sum(hmc1.dateFinish-so1.operationDate) from medcase hmc1 
 left join MedCase dmc1 on dmc1.parent_id=hmc1.id and dmc1.dtype='DepartmentMedCase'
 left join SurgicalOperation so1 on so1.medCase_id=dmc1.id
 left join VocHospitalAspect vha1 on vha1.id=so1.aspect_id
 where  hmc1.dtype='HospitalMedCase' and ${dateT1} between to_date('${param.dateBegin}','dd.mm.yyyy')  
    	and to_date('${dateEnd}','dd.mm.yyyy') and 
 hmc1.dateFinish is not null 
and hmc1.dischargeTime is not null and so1.department_id=dmc.department_id
and (vha1.code='EMERGENCY')
) / cast((select count(so1.id)  from medcase hmc1 
 left join MedCase dmc1 on dmc1.parent_id=hmc1.id and dmc1.dtype='DepartmentMedCase'
 left join SurgicalOperation so1 on so1.medCase_id=dmc1.id
 left join VocHospitalAspect vha1 on vha1.id=so1.aspect_id
 where  hmc1.dtype='HospitalMedCase' and ${dateT1} between to_date('${param.dateBegin}','dd.mm.yyyy')  
    	and to_date('${dateEnd}','dd.mm.yyyy') and 
 hmc1.dateFinish is not null 
and hmc1.dischargeTime is not null and so1.department_id=dmc.department_id
and (vha1.code='EMERGENCY')
) as numeric),1) as numeric) else 0 end as srDayEmerAfter

from MedCase hmc
left join MedCase dmc on dmc.parent_id=hmc.id
left join Patient pat on pat.id=hmc.patient_id
left join MisLpu dep on dep.id=dmc.department_id
where hmc.DTYPE='HospitalMedCase' 
    and ${dateT} between to_date('${param.dateBegin}','dd.mm.yyyy')  
    	and to_date('${dateEnd}','dd.mm.yyyy') 
    	
    	and dmc.dateFinish is not null
    	${dep}
    	
    
group by dmc.department_id,dep.name 
order by dep.name   
    
    " guid="4a720225-8d94-4b47-bef3-4dbbe79eec74" />
        <msh:table name="journal_list_swod"
         action="stac_analysis_department_list.do" idField="1" noDataMessage="Не найдено">
            <msh:tableNotEmpty>
              <tr>
                <th colspan="1" />
                <th colspan="1" />
                <th colspan="1" />
                <th colspan="4" class="rightBold">Количество оперированных больных</th>
                <th colspan="3" class="rightBold">Количество операций</th>
                <th colspan="2" class="rightBold">Процент</th>
                <th colspan="2" class="rightBold">Хирург. активность (%)</th>
                <th colspan="4" class="rightBold">Длительность пребывания больных</th>
              </tr>
            </msh:tableNotEmpty>            
            <msh:tableColumn columnName="#" property="sn"/>
            <msh:tableColumn columnName="Наименование отделения" property="2"/>
            <msh:tableColumn columnName="Число выбывших больных" isCalcAmount="true" property="3"/>
            <msh:tableColumn columnName="всего" isCalcAmount="true" property="4"/>
            <msh:tableColumn columnName="экст." isCalcAmount="true" property="5"/>
            <msh:tableColumn columnName="план. " isCalcAmount="true" property="6"/>
            <msh:tableColumn columnName="экстр. + план. " isCalcAmount="true" property="7"/>
            
            <msh:tableColumn columnName="всего" isCalcAmount="true" property="8"/>
            <msh:tableColumn columnName="экст." isCalcAmount="true" property="9"/>
            <msh:tableColumn columnName="план. " isCalcAmount="true" property="10"/>
            <msh:tableColumn columnName="экст." property="11"/>
            <msh:tableColumn columnName="план. " property="12"/>
            <msh:tableColumn columnName="по пациентам" property="13"/>
            <msh:tableColumn columnName="по операциям" property="14"/>
            <msh:tableColumn columnName="экст. до" property="17"/>
            <msh:tableColumn columnName="экст. после" property="18"/>
            <msh:tableColumn columnName="план. до" property="15"/>
            <msh:tableColumn columnName="план. после" property="16"/>
        </msh:table>
    </msh:sectionContent>
    </msh:section>
    <%
    } 
    	if (view!=null && view.equals("4")) { 
    %>
    <msh:section title="Свод хир. операций по отделениям, где отличаются отд.выписки и отд, где проводилась операция">
    <msh:sectionContent>
    <ecom:webQuery name="journal_list_oper_otd_swod" nativeSql="
select ldmc.department_id as depid
,operdep.name as operdepname,dischdep.name as dischdepname
,count(distinct hmc.id) as cntDischarge
,count(distinct case when hmc.emergency='1' then hmc.id else null end) as cntEmerPat
,count(distinct case when (hmc.emergency='0' or hmc.emergency is null) then hmc.id else null end ) as cntPlanPat
,count(distinct so.id)  as cntOper
,count(distinct case when hmc.emergency='1' then so.id else null end)  as cntEmerOper
,count(distinct case when (hmc.emergency='0' or hmc.emergency is null) then so.id else null end) as cntPlanOper
,cast(round(sum(so.operationDate-hmc.dateStart) / cast(count(so.id)  as numeric),1) as numeric) as srDayTo
,cast(round(sum(hmc.dateFinish-so.operationDate) / cast(count(so.id) as numeric),1) as numeric)  as srDayAfter

from SurgicalOperation so
left join MedCase dmc on dmc.id=so.medCase_id
left join MedCase hmc on dmc.parent_id=hmc.id
left join MedCase ldmc on ldmc.parent_id=dmc.parent_id
left join Patient pat on pat.id=hmc.patient_id
left join Address2 adr on adr.addressid=pat.address_addressid
left join Omc_Oksm ok on pat.nationality_id=ok.id
left join VocRayon vr on vr.id=pat.rayon_id
left join MisLpu operdep on operdep.id=so.department_id
left join MisLpu dischdep on dischdep.id=ldmc.department_id
left join VocHospType vht on vht.id=hmc.hospType_id
where ldmc.DTYPE='DepartmentMedCase' 
    and ldmc.dateFinish between to_date('${param.dateBegin}','dd.mm.yyyy')  
    	and to_date('${dateEnd}','dd.mm.yyyy') 
    	and ldmc.department_id!=so.department_id
    	${dep}
    	
    
group by ldmc.department_id,operdep.name,dischdep.name 
order by operdep.name, dischdep.name
    
    " guid="4a720225-8d94-4b47-bef3-4dbbe79eec74" />
        <msh:table name="journal_list_oper_otd_swod"
         action="journal_list_oper_otd_swod.do" idField="1" noDataMessage="Не найдено">
            <msh:tableColumn columnName="#" property="sn"/>
            <msh:tableColumn columnName="Отделение (опер)" property="2"/>
            <msh:tableColumn columnName="Отделение выписки" property="3"/>
            <msh:tableColumn columnName="Кол-во СЛС"  isCalcAmount="true" property="4"/>
            <msh:tableColumn columnName="Кол-во экстр. пациентов" isCalcAmount="true" property="5"/>
            <msh:tableColumn columnName="Кол-во плановых пациентов" isCalcAmount="true" property="6"/>
            <msh:tableColumn columnName="кол-во операций" isCalcAmount="true" property="7"/>
            <msh:tableColumn columnName="Кол-во экстр. операций " isCalcAmount="true" property="8"/>
            <msh:tableColumn columnName="Кол-во плановых операций" isCalcAmount="true" property="9"/>
            <msh:tableColumn columnName="К/дней до операции" property="10"/>
            <msh:tableColumn columnName="К/дней после операции" property="11"/>
        </msh:table>
    </msh:sectionContent>
    </msh:section>
    <%
    } 
    	if (view!=null && view.equals("6")) { 
    %>
    <msh:section>
    <ecom:webQuery nameFldSql="journal_list_otd_rayon_swod_sql" name="journal_list_otd_rayon_swod" nativeSql="
    select
dmc.department_id as depid,dep.name as depname
,count(distinct hmc.id) as cntDischarge
,count(distinct case when adr.kladr like '30000001%' then hmc.id else null end) as cntAstrakhan
,count(distinct case when adr.kladr like '30000002%' then hmc.id else null end) as cntZnamenkc
,count(distinct case when adr.kladr like '30%' and adr.kladr not like '30000%' then hmc.id else null end) as cntRayon
,count(distinct case when adr.kladr like '30002%' then hmc.id else null end) as cntAhtub
,count(distinct case when adr.kladr like '30003%' then hmc.id else null end) as cntVolodar
,count(distinct case when adr.kladr like '30004%' then hmc.id else null end) as cntEnotaev
,count(distinct case when adr.kladr like '30005%' then hmc.id else null end) as cntIkrian
,count(distinct case when adr.kladr like '30006%' then hmc.id else null end) as cntKamiz
,count(distinct case when adr.kladr like '30007%' then hmc.id else null end) as cntKrasnoiar
,count(distinct case when adr.kladr like '30008%' then hmc.id else null end) as cntLiman
,count(distinct case when adr.kladr like '30009%' then hmc.id else null end) as cntNarim
,count(distinct case when adr.kladr like '30010%' then hmc.id else null end) as cntPrivol
,count(distinct case when adr.kladr like '30011%' then hmc.id else null end) as cntHarab
,count(distinct case when adr.kladr like '30012%' then hmc.id else null end) as cntChernoiar
,count(distinct case when adr.kladr not like '30%' and ok.voc_code='643' then hmc.id else null end) as cntInog
,count(distinct case when ok.voc_code!='643' then hmc.id else null end) as cntInost
,(select count(*) from MedCase hmc1 
    left join patient pat1 on pat1.id=hmc1.patient_id
     left join Omc_Oksm ok1 on pat1.nationality_id=ok1.id
     left join address2 adr1 on adr1.addressId = pat1.address_addressId
	where hmc1.DTYPE='HospitalMedCase' 
    and hmc1.dateStart between to_date('${param.dateBegin}','dd.mm.yyyy')  
    	and to_date('${dateEnd}','dd.mm.yyyy') 
    	and hmc1.department_id=dmc.department_id
    	and hmc1.deniedHospitalizating_id is null
    	) as cntAdmisPat
from MedCase hmc
left join MedCase dmc on dmc.parent_id=hmc.id
left join Patient pat on pat.id=hmc.patient_id
left join Address2 adr on adr.addressid=pat.address_addressid
left join Omc_Oksm ok on pat.nationality_id=ok.id
left join VocRayon vr on vr.id=pat.rayon_id
left join MisLpu dep on dep.id=dmc.department_id
left join VocHospType vht on vht.id=hmc.hospType_id
where hmc.DTYPE='HospitalMedCase' 
    and ${dateT} between to_date('${param.dateBegin}','dd.mm.yyyy')  
    	and to_date('${dateEnd}','dd.mm.yyyy') 
    	and hmc.deniedHospitalizating_id is null
    	and dmc.dateFinish is not null
    	${dep}
    	

group by dmc.department_id,dep.name 
order by dep.name   
    
    " guid="4a720225-8d94-4b47-bef3-4dbbe79eec74" />
    <msh:sectionTitle>
	    <form action="print-stac_analysis_department_6.do" method="post" target="_blank">
	    Свод по отделениям по районам. Период с ${param.dateBegin} по ${dateEnd}.
	    <input type='hidden' name="sqlText" id="sqlText" value="${journal_list_otd_rayon_swod_sql}"> 
	    <input type='hidden' name="sqlInfo" id="sqlInfo" value="период с ${param.dateBegin} по ${dateEnd}.">
	    <input type='hidden' name="sqlColumn" id="sqlColumn" value="${groupName}">
	    <input type='hidden' name="s" id="s" value="PrintService">
	    <input type='hidden' name="m" id="m" value="printNativeQuery">
	    <input type="submit" value="Печать"> 
	    </form>     
    </msh:sectionTitle>
    <msh:sectionContent>
        <msh:table name="journal_list_otd_rayon_swod"
         action="stac_analysis_department_list.do" idField="1" noDataMessage="Не найдено">
             <msh:tableNotEmpty>
              <tr>
                <th colspan="1" />
                <th colspan="1" />
                <th colspan="1" />
                <th colspan="1" />
                <th colspan="1" />
                <th colspan="1" />
                <th colspan="12" class="rightBold">Районы</th>
                <th colspan="1" />
                <th colspan="1" />
              </tr>
            </msh:tableNotEmpty>  
            <msh:tableColumn columnName="#" property="sn"/>
            <msh:tableColumn columnName="Отделение" property="2"/>
            <msh:tableColumn columnName="Всего посту- пило" isCalcAmount="true" property="20"/>
            <msh:tableColumn columnName="Всего выпи- сано" isCalcAmount="true" property="3"/>
            <msh:tableColumn columnName="г. Астра- хань" isCalcAmount="true" property="4"/>
            <msh:tableColumn columnName="г. Зна- менск" isCalcAmount="true" property="5"/>
            <msh:tableColumn columnName="Всего" isCalcAmount="true" property="6"/>
            <msh:tableColumn columnName="Ахту- бин- ский" isCalcAmount="true" property="7"/>
            <msh:tableColumn columnName="Воло- дар- ский " isCalcAmount="true" property="8"/>
            <msh:tableColumn columnName="Ено- таевский" isCalcAmount="true" property="9"/>
            <msh:tableColumn columnName="Икря- нинский" isCalcAmount="true" property="10"/>
            <msh:tableColumn columnName="Камы- зякский" isCalcAmount="true" property="11"/>
            <msh:tableColumn columnName="Красно- ярский" isCalcAmount="true" property="12"/>
            <msh:tableColumn columnName="Лиман- ский" isCalcAmount="true" property="13"/>
            <msh:tableColumn columnName="Нари- манов- ский" isCalcAmount="true" property="14"/>
            <msh:tableColumn columnName="Привол- жский" isCalcAmount="true" property="15"/>
            <msh:tableColumn columnName="Хара- балин- ский" isCalcAmount="true" property="16"/>
            <msh:tableColumn columnName="Черно- ярский" isCalcAmount="true" property="17"/>
            <msh:tableColumn columnName="Ино- город- ние" isCalcAmount="true" property="18"/>
            <msh:tableColumn columnName="Иност- ранцы" isCalcAmount="true" property="19"/>
        </msh:table>
    </msh:sectionContent>
    </msh:section>
    <%
    } 
    	if (view!=null && view.equals("7")) { 
    %>
    <msh:section title="Свод по леч. врачам">
    <msh:sectionContent>
    <ecom:webQuery name="journal_list_otd_owner" nativeSql="
    select
'7:'||dmc.department_id||':'||dmc.ownerFunction_id as depid,dep.name as depname,ovwf.name,owp.lastname||' '||owp.firstname||' '||owp.middlename
,count(distinct hmc.id) as cntStatCard
,count(distinct case when hmc.emergency='1' then hmc.id else null end) cntEmerStatCard
,count(distinct pat.id) as cntPat
from MedCase hmc
left join MedCase dmc on dmc.parent_id=hmc.id
left join Patient pat on pat.id=hmc.patient_id
left join Address2 adr on adr.addressid=pat.address_addressid
left join Omc_Oksm ok on pat.nationality_id=ok.id
left join VocRayon vr on vr.id=pat.rayon_id
left join MisLpu dep on dep.id=dmc.department_id
left join VocHospType vht on vht.id=hmc.hospType_id
left join WorkFunction owf on owf.id=dmc.ownerFunction_id
left join Worker ow on ow.id=owf.worker_id
left join Patient owp on owp.id=ow.person_id
left join VocWorkFunction ovwf on ovwf.id=owf.workFunction_id
where hmc.DTYPE='HospitalMedCase' 
    and ${dateT} between to_date('${param.dateBegin}','dd.mm.yyyy')  
    	and to_date('${dateEnd}','dd.mm.yyyy') 
    	
    	and dmc.dateFinish is not null
    	${dep}
    	

group by dmc.department_id,dep.name,dmc.ownerFunction_id,ovwf.name 
,owp.lastname,owp.firstname,owp.middlename
order by dep.name   ,ovwf.name 
,owp.lastname,owp.firstname,owp.middlename
    
    " guid="4a720225-8d94-4b47-bef3-4dbbe79eec74" />
        <msh:table name="journal_list_otd_owner"
         action="stac_analysis_department_list.do" idField="1" noDataMessage="Не найдено">
            <msh:tableColumn columnName="#" property="sn"/>
            <msh:tableColumn columnName="Отделение" property="2"/>
            <msh:tableColumn columnName="Должность" property="3"/>
            <msh:tableColumn columnName="ФИО врача" property="4"/>
            <msh:tableColumn columnName="Кол-во пациентов" isCalcAmount="true" property="5"/>
            <msh:tableColumn columnName="из них экстр." isCalcAmount="true" property="6"/>

        </msh:table>
    </msh:sectionContent>
    </msh:section>
    <%
    } 
    	if (view!=null && view.equals("8")) { 
    %>
    <msh:section title="Сводный отчет отделений по работе хирургам">
    <msh:sectionContent>
    <ecom:webQuery name="journal_list_otd_surgeon" nativeSql="
     select
so.department_id ||':'||so.surgeon_id as depid,dep.name as depname
,svwf.name as svwfname,swp.lastname||' '||swp.firstname||' '||swp.middlename as fiodoctor
,(select count(distinct hmc1.id) from MedCase hmc1
left join MedCase dmc1 on dmc1.parent_id=hmc1.id
where hmc1.DTYPE='HospitalMedCase' 
    and hmc1.dateFinish between to_date('${param.dateBegin}','dd.mm.yyyy')  
    	and to_date('${dateEnd}','dd.mm.yyyy') 
    	and so.surgeon_id=dmc1.ownerFunction_id
    	and so.department_id=dmc1.department_id
) as cntOwnerPat
,count(distinct hmc.id) as cntOperPat
,count(distinct case when hmc.emergency='1' then hmc.id else null end) as cntEmOperPatHosp
,count(distinct case when vha.code='EMERGENCY' then hmc.id else null end) as cntEmOperPatOper
,count(distinct so.id) as cntOper
,count(distinct case when hmc.emergency='1' then so.id else null end) as cntEmOperHosp
,count(distinct case when vha.code='EMERGENCY' then so.id else null end) as cntEmOperOper
,sum(case when vo.complexity is null then 0 else vo.complexity end)+count(so.id) as cntComp
,
round(100*count(distinct so.id)/
cast((select count(so1.id) from  SurgicalOperation so1
left join MedCase dmc1 on so1.medCase_id = dmc1.id
left join MedCase hmc1 on dmc1.parent_id=hmc1.id
where so.department_id=so1.department_id and
    	 ${dateT1} between to_date('${param.dateBegin}','dd.mm.yyyy')  
    	and to_date('${dateEnd}','dd.mm.yyyy') 
    	and hmc1.DTYPE='HospitalMedCase' 
     and so1.surgeon_id is not null
    	and dmc1.dateFinish is not null)
as numeric) ,2) 

 as SpByDepartment
from MedCase hmc
left join MedCase dmc on dmc.parent_id=hmc.id
left join SurgicalOperation so on so.medCase_id = dmc.id
left join VocHospitalAspect vha on vha.id=so.aspect_id
left join MedService vo on vo.id=so.medService_id
left join Patient pat on pat.id=hmc.patient_id
left join Address2 adr on adr.addressid=pat.address_addressid
left join Omc_Oksm ok on pat.nationality_id=ok.id
left join VocRayon vr on vr.id=pat.rayon_id
left join MisLpu dep on dep.id=so.department_id
left join VocHospType vht on vht.id=hmc.hospType_id
left join WorkFunction swf on swf.id=so.surgeon_id
left join Worker sw on sw.id=swf.worker_id
left join Patient swp on swp.id=sw.person_id
left join VocWorkFunction svwf on svwf.id=swf.workFunction_id
where hmc.DTYPE='HospitalMedCase' 
    and ${dateT} between to_date('${param.dateBegin}','dd.mm.yyyy')  
    	and to_date('${dateEnd}','dd.mm.yyyy') 
    	${depOper}
    	and so.surgeon_id is not null
    	and dmc.dateFinish is not null
group by so.department_id,dep.name,so.surgeon_id
,svwf.name,swp.lastname,swp.firstname,swp.middlename
order by dep.name,svwf.name,swp.lastname,swp.firstname,swp.middlename
   
    " guid="4a720225-8d94-4b47-bef3-4dbbe79eec74" />
        <msh:table name="journal_list_otd_surgeon"
         action="stac_analysis_department_list.do" idField="1" noDataMessage="Не найдено">
            <msh:tableColumn columnName="#" property="sn"/>
            <msh:tableColumn columnName="Отделение" property="2"/>
            <msh:tableColumn columnName="Должность" property="3"/>
            <msh:tableColumn columnName="ФИО врача" property="4"/>
            <msh:tableColumn columnName="Кол-во пациентов, у которых был леч. врачом" isCalcAmount="true" property="5"/>            
            <msh:tableColumn columnName="Кол-во опер. пациентов" isCalcAmount="true" property="6"/>            
            <msh:tableColumn columnName="из них экстр. госпит." isCalcAmount="true" property="7"/>            
            <msh:tableColumn columnName="из них экстр. опер." isCalcAmount="true" property="8"/>            
            <msh:tableColumn columnName="Кол-во операций" isCalcAmount="true" property="9"/>            
            <msh:tableColumn columnName="из них экстр. госп." isCalcAmount="true" property="10"/>            
            <msh:tableColumn columnName="из них экстр. опер." isCalcAmount="true" property="11"/>            
            <msh:tableColumn columnName="Сводный коэффициент" property="12"/>            
            <msh:tableColumn columnName="% от общ. числа опер. по отд." property="13"/>            
        </msh:table>
    </msh:sectionContent>
    </msh:section>
    <%
    } 
    	if (view!=null && view.equals("9")) { 
    %>
    <msh:section title="Свод по хирургам">
    <msh:sectionContent>
    <ecom:webQuery name="journal_list_surgeon" nativeSql="
     select
swf.workFunction_id||':'||sw.person_id as depid
,svwf.name as svwfname,swp.lastname||' '||swp.firstname||' '||swp.middlename as fiodoctor
,(select count(distinct hmc1.id) from MedCase hmc1
left join MedCase dmc1 on dmc1.parent_id=hmc1.id
left join WorkFunction wf on wf.id=dmc1.ownerFunction_id
left join Worker w on w.id=wf.worker_id
where hmc1.DTYPE='HospitalMedCase' 
    and hmc1.dateFinish between to_date('${param.dateBegin}','dd.mm.yyyy')  
    	and to_date('${dateEnd}','dd.mm.yyyy')
    	and sw.person_id=w.person_id
    	and swf.workFunction_id=wf.workFunction_id
    	
) as cntOwnerPat
,count(distinct hmc.id) as cntOperPat
,count(distinct case when hmc.emergency='1' then hmc.id else null end) as cntEmOperPatHosp
,count(distinct case when vha.code='EMERGENCY' then hmc.id else null end) as cntEmOperPatOper
,count(distinct so.id) as cntOper
,count(distinct case when hmc.emergency='1' then so.id else null end) as cntEmOperHosp
,count(distinct case when vha.code='EMERGENCY' then so.id else null end) as cntEmOperOper
,sum(case when vo.complexity is null then 0 else vo.complexity end)+count(so.id) as cntComp
from MedCase hmc
left join MedCase dmc on dmc.parent_id=hmc.id
left join SurgicalOperation so on so.medCase_id = dmc.id
left join VocHospitalAspect vha on vha.id=so.aspect_id
left join MedService vo on vo.id=so.medService_id
left join Patient pat on pat.id=hmc.patient_id
left join Address2 adr on adr.addressid=pat.address_addressid
left join Omc_Oksm ok on pat.nationality_id=ok.id
left join VocRayon vr on vr.id=pat.rayon_id
left join MisLpu dep on dep.id=so.department_id
left join VocHospType vht on vht.id=hmc.hospType_id
left join WorkFunction swf on swf.id=so.surgeon_id
left join Worker sw on sw.id=swf.worker_id
left join Patient swp on swp.id=sw.person_id
left join VocWorkFunction svwf on svwf.id=swf.workFunction_id
where hmc.DTYPE='HospitalMedCase' 
    and ${dateT} between to_date('${param.dateBegin}','dd.mm.yyyy')  
    	and to_date('${dateEnd}','dd.mm.yyyy') 
    	
    	and so.surgeon_id is not null
    	and dmc.dateFinish is not null
group by swf.workFunction_id,sw.person_id,svwf.name,swp.lastname,swp.firstname,swp.middlename
order by svwf.name,swp.lastname,swp.firstname,swp.middlename

    
    	
   
    " guid="4a720225-8d94-4b47-bef3-4dbbe79eec74" />
        <msh:table name="journal_list_surgeon"
         action="stac_analysis_department_list.do" idField="1" noDataMessage="Не найдено">
            <msh:tableColumn columnName="#" property="sn"/>
            <msh:tableColumn columnName="Должность" property="2"/>
            <msh:tableColumn columnName="ФИО врача" property="3"/>
            <msh:tableColumn columnName="Кол-во пациентов, у которых был леч. врачом" isCalcAmount="true" property="4"/>            
            <msh:tableColumn columnName="Кол-во опер. пациентов" isCalcAmount="true" property="5"/>            
            <msh:tableColumn columnName="из них экстр. госпит." isCalcAmount="true" property="6"/>            
            <msh:tableColumn columnName="из них экстр. опер." isCalcAmount="true" property="7"/>            
            <msh:tableColumn columnName="Кол-во операций" isCalcAmount="true" property="8"/>            
            <msh:tableColumn columnName="из них экстр. госп." isCalcAmount="true"property="9"/>            
            <msh:tableColumn columnName="из них экстр. опер." isCalcAmount="true" property="10"/>            
            <msh:tableColumn columnName="Сводный коэффициент" property="11"/>            
        </msh:table>
    </msh:sectionContent>
    </msh:section>
    <%
    } 
    	if (view!=null && view.equals("10")) { 
    %>
    <msh:section title="Реестр операций с 0 уровнем сложности">
    <msh:sectionContent>
    <ecom:webQuery name="journal_list_oper" nativeSql="
    select
vo.id,vo.code,vo.name,count(distinct so.id),list(dep.name) as dep
from SurgicalOperation so
left join MedCase dmc on so.medCase_id = dmc.id
left join MedCase hmc on dmc.parent_id=hmc.id
left join MedService vo on vo.id=so.medService_id
left join Patient pat on pat.id=hmc.patient_id
left join Address2 adr on adr.addressid=pat.address_addressid
left join Omc_Oksm ok on pat.nationality_id=ok.id
left join VocRayon vr on vr.id=pat.rayon_id
left join MisLpu dep on dep.id=so.department_id
left join VocHospType vht on vht.id=hmc.hospType_id
left join WorkFunction swf on swf.id=so.surgeon_id
left join Worker sw on sw.id=swf.worker_id
left join Patient swp on swp.id=sw.person_id
left join VocWorkFunction svwf on svwf.id=swf.workFunction_id
where hmc.DTYPE='HospitalMedCase' 
   and ${dateT} between to_date('${param.dateBegin}','dd.mm.yyyy')  
    	and to_date('${dateEnd}','dd.mm.yyyy') 
    	
    	and dmc.dateFinish is not null
    	${depOper}
    	and so.surgeon_id is not null
    	and (vo.complexity is null or vo.complexity<1)
group by vo.id,vo.code,vo.name
order by vo.id,vo.code,vo.name
    
    " guid="4a720225-8d94-4b47-bef3-4dbbe79eec74" />
        <msh:table name="journal_list_oper"
         action="entityView-mis_medService.do" idField="1" noDataMessage="Не найдено">
            <msh:tableColumn columnName="#" property="sn"/>
            <msh:tableColumn columnName="Код" property="2"/>
            <msh:tableColumn columnName="Операция" property="3"/>
            <msh:tableColumn columnName="Кол-во операций" isCalcAmount="true" property="4"/>            
            <msh:tableColumn columnName="Отделения" property="5"/>            
        </msh:table>
    </msh:sectionContent>
    </msh:section>
    <%
    } 
    	if (view!=null && view.equals("5")) { 
    %>
    <msh:section title="Свод по отделениям общий">
    <msh:sectionContent>
    <ecom:webQuery name="journal_list_otd_swod" nativeSql="
    select
dmc.department_id,dep.name
,count(distinct hmc.id) as cntDischarge

    , sum(case 
		when (coalesce(hmc.dateFinish,CURRENT_DATE)-hmc.dateStart)=0 then 1 
		when vht.code='DAYTIMEHOSP' then ((coalesce(hmc.dateFinish,CURRENT_DATE)-hmc.dateStart)+1) 
		else (coalesce(hmc.dateFinish,CURRENT_DATE)-hmc.dateStart)
		end) as countBedDays
    , cast(round(sum(case 
		when (coalesce(hmc.dateFinish,CURRENT_DATE)-hmc.dateStart)=0 then 1 
		when vht.code='DAYTIMEHOSP' then ((coalesce(hmc.dateFinish,CURRENT_DATE)-hmc.dateStart)+1) 
		else (coalesce(hmc.dateFinish,CURRENT_DATE)-hmc.dateStart)
		end)/cast(count(hmc.id) as numeric),1) as numeric) as spCountDays

,(select count(distinct hmc1.id)  from medcase hmc1 
 left join MedCase dmc1 on dmc1.parent_id=hmc1.id and dmc1.dtype='DepartmentMedCase'
 left join SurgicalOperation so1 on so1.medCase_id=dmc1.id
 where 
 hmc1.dtype='HospitalMedCase' and ${dateT1} between to_date('${param.dateBegin}','dd.mm.yyyy')  
    	and to_date('${dateEnd}','dd.mm.yyyy') and 
 hmc1.dateFinish is not null 
and hmc1.dischargeTime is not null and so1.department_id=dmc.department_id
) as cntOperPat

,(select count(distinct so1.id)  from medcase hmc1 
 left join MedCase dmc1 on dmc1.parent_id=hmc1.id and dmc1.dtype='DepartmentMedCase'
 left join SurgicalOperation so1 on so1.medCase_id=dmc1.id
 where  hmc1.dtype='HospitalMedCase' and ${dateT1} between to_date('${param.dateBegin}','dd.mm.yyyy')  
    	and to_date('${dateEnd}','dd.mm.yyyy') and 
 hmc1.dateFinish is not null 
and hmc1.dischargeTime is not null and so1.department_id=dmc.department_id
) as cntOper
,(select count(*) from MedCase hmc1 
    left join patient pat1 on pat1.id=hmc1.patient_id
     left join Omc_Oksm ok1 on pat1.nationality_id=ok1.id
     left join address2 adr1 on adr1.addressId = pat1.address_addressId
	where hmc1.DTYPE='HospitalMedCase' 
    and hmc1.dateStart between to_date('${param.dateBegin}','dd.mm.yyyy')  
    	and to_date('${dateEnd}','dd.mm.yyyy') 
    	and hmc1.department_id=dmc.department_id and hmc1.deniedHospitalizating_id is null) as cntAdmisPat
from MedCase hmc
left join MedCase dmc on dmc.parent_id=hmc.id
left join Patient pat on pat.id=hmc.patient_id
left join MisLpu dep on dep.id=dmc.department_id
left join VocHospType vht on vht.id=hmc.hospType_id
where hmc.DTYPE='HospitalMedCase' 
    and ${dateT} between to_date('${param.dateBegin}','dd.mm.yyyy')  
    	and to_date('${dateEnd}','dd.mm.yyyy') 
    	
    	and dmc.dateFinish is not null
    	${dep}
    	
    
group by dmc.department_id,dep.name 
order by dep.name   
    
    " guid="4a720225-8d94-4b47-bef3-4dbbe79eec74" />
        <msh:table name="journal_list_otd_swod"
         action="stac_analysis_department_list.do" idField="1" noDataMessage="Не найдено">
            <msh:tableColumn columnName="#" property="sn"/>
            <msh:tableColumn columnName="Наименование отделения" property="2"/>
            <msh:tableColumn columnName="Всего поступивших больных" isCalcAmount="true" property="8"/>
            <msh:tableColumn columnName="Всего выписанных больных" isCalcAmount="true" property="3"/>
            <msh:tableColumn columnName="Всего койко- дней" isCalcAmount="true" property="4"/>
            <msh:tableColumn columnName="Сред. койко- дней " property="5"/>
            <msh:tableColumn columnName="Опер. больных" isCalcAmount="true" property="6"/>
            <msh:tableColumn columnName="Кол-во операций" isCalcAmount="true" property="7"/>
        </msh:table>
    </msh:sectionContent>
    </msh:section>
    <% }} else {%>
    	<i>Выберите параметры и нажмите найти </i>
    	<% }   %>
    <script type='text/javascript'>
    
     checkFieldUpdate('typeDate','${typeDate}',2) ;
     //checkFieldUpdate('typePatient','${typePatient}',3) ;
     //checkFieldUpdate('typeOperation','${typeOperation}',3) ;
     checkFieldUpdate('typeEmergency','${typeEmergency}',3) ;
     checkFieldUpdate('typeView','${typeView}',8) ;
     
   
    function checkFieldUpdate(aField,aValue,aDefault) {
    	
    	eval('var chk =  document.forms[0].'+aField) ;
    	var max = chk.length ;
    	if ((+aValue)>max) {
    		chk[+aDefault-1].checked='checked' ;
    	} else {
    		chk[+aValue-1].checked='checked' ;
    	}
    }
    function getCheckedValue(aField) {
    	eval('var radioGrp =  document.forms[0].'+aField) ;
  		var radioValue ;
  		for(i=0; i < radioGrp.length; i++){
  		  if (radioGrp[i].checked == true){
  		    radioValue = radioGrp[i].value;
  		    break ;
  		  }
  		} 
  		return radioValue ;
  	}
    function updateId() {
    	var args=$('dateBegin').value+":"+$('dateEnd').value
			+":"+getCheckedValue("typeView")
			+":"+getCheckedValue("typeEmergency")
			//+":"+getCheckedValue("typeOperation")
			+":"+getCheckedValue("typeDate")
			+":"+$('department').value
			//+":"+$('result').value
			;
			//alert(args) ;
		//$('param').value = args ;
		//alert(args) ;
    }
    function find() {
    	var frm = document.forms[0] ;
    	frm.target='' ;
    	frm.action='stac_analysis_department_list.do' ;
    }
    function print() {
    	updateId() ;
    	var frm = document.forms[0] ;
    	frm.target='_blank' ;
    	
    	frm.action='print-stac_department_analysis.do' ;
    }
    function printReestr() {
    	//updateId() ;
    	var frm = document.forms[0] ;
    	frm.target='_blank' ;
    	
    	frm.action='js-stac_ssl-printReestr.do' ;
    }
    </script>
  </tiles:put>
</tiles:insert>

