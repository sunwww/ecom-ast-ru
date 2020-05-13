<%@page import="ru.ecom.web.util.ActionUtil"%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://struts.apache.org/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://www.nuzmsh.ru/tags/msh" prefix="msh" %>
<%@ taglib uri="http://www.ecom-ast.ru/tags/ecom" prefix="ecom" %>
<%@ taglib tagdir="/WEB-INF/tags" prefix="tags" %>

<tiles:insert page="/WEB-INF/tiles/mainLayout.jsp" flush="true" >
	<tiles:put name="style" type="string">
	
	</tiles:put>

    <tiles:put name='title' type='string'>
        <msh:title mainMenu="Journals">Просмотр реестра пациентов по результатам госпитализации</msh:title>
    </tiles:put>

    <tiles:put name='side' type='string'>
        <tags:stac_journal currentAction="stac_journalByDeathPatient"/>
    </tiles:put>
    
  <tiles:put name="body" type="string">
    <msh:form action="/stac_resultPatient_list.do" defaultField="department" disableFormDataConfirm="true" method="GET">
    <msh:panel>
    <input type="hidden" value="printDeathList" name="m">
    <input type="hidden" value="HospitalReportService" name="s">
    <msh:hidden property="param"/>
      <msh:row>
        <msh:separator label="Дополнительные параметры для реестра (в своде не учитываются)" colSpan="7"/>
       </msh:row>
        <msh:row styleId="noswod">
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
        <msh:row styleId="noswod">
	        <td class="label" title="Поиск по проведена ли была операция (typeOperation)" colspan="1"><label for="typeOperationName" id="typeOperationLabel">Была ли операция:</label></td>
	        <td onclick="this.childNodes[1].checked='checked';">
	        	<input type="radio" name="typeOperation" value="1">  да
	        </td>
	        <td onclick="this.childNodes[1].checked='checked';">
	        	<input type="radio" name="typeOperation" value="2"  >  нет
	        </td>
	        <td onclick="this.childNodes[1].checked='checked';">
	        	<input type="radio" name="typeOperation" value="3">  все
	        </td>
        </msh:row>
      
      <msh:row>
        <msh:separator label="Основные параметры" colSpan="7"/>
      </msh:row>
      <msh:row>
        <td class="label" title="Поиск по дате  (typeDate)" colspan="1"><label for="typeDateName" id="typeDateLabel">Искать по дате:</label></td>
        <td onclick="this.childNodes[1].checked='checked';">
        	<input type="radio" name="typeDate" value="1">  поступления
        </td>
        <td onclick="this.childNodes[1].checked='checked';" colspan="2">
        	<input type="radio" name="typeDate" value="2">  выписки
        </td>
        </msh:row>
      <msh:row>
        <td class="label" title="Поиск по пациентам (typePatient)" colspan="1"><label for="typePatientName" id="typePatientLabel">Пациенты:</label></td>
        <td onclick="this.childNodes[1].checked='checked';">
        	<input type="radio" name="typePatient" value="1">  иностранцы
        </td>
        <td onclick="this.childNodes[1].checked='checked';"  colspan="2">
        	<input type="radio" name="typePatient" value="2">  проживающим в других регионах
        </td>
        <td onclick="this.childNodes[1].checked='checked';">
        	<input type="radio" name="typePatient" value="3">  все
        </td>
        </msh:row>
        <msh:row>
	        <td class="label" title="Просмотр данных (typeView)" colspan="1"><label for="typeViewName" id="typeViewLabel">Отобразить:</label></td>
	        <td onclick="this.childNodes[1].checked='checked';">
	        	<input type="radio" name="typeView" value="1">  реестр
	        </td>
	        <td onclick="this.childNodes[1].checked='checked';">
	        	<input type="radio" name="typeView" value="2"  >  свод по отделениям
	        </td>
	        <td onclick="this.childNodes[1].checked='checked';" colspan="3">
	        	<input type="radio" name="typeView" value="3"  >  свод по отделениям без учета отд., которые не входят в ОМС 
	        </td>
        </msh:row>
        <msh:row>
        	<td></td>
	        <td onclick="this.childNodes[1].checked='checked';">
	        	<input type="radio" name="typeView" value="4"  >  общий свод по ЛПУ
	        </td>
	        <td onclick="this.childNodes[1].checked='checked';">
	        	<input type="radio" name="typeView" value="5">  все
	        </td>
        </msh:row>
        <msh:row>
        	<msh:autoComplete property="result" fieldColSpan="4" horizontalFill="true" label="Результат госпитализации" vocName="vocHospitalizationResult"/>
        </msh:row>
        <msh:row>
        	<msh:autoComplete property="department" fieldColSpan="4" horizontalFill="true" label="Отделение" vocName="lpu"/>
        </msh:row>
        <msh:row>
	        <msh:textField property="dateBegin" label="Период с" />
	        <msh:textField property="dateEnd" label="по" />
		<td>
            <input type="submit" onclick="find()" value="Найти" />
            <input type="submit" onclick="print()" value="Печать" />
          </td>
         </msh:row>
        
    </msh:panel>
    </msh:form>
    
    <%
    String date = (String)request.getParameter("dateBegin") ;
    String result = (String)request.getParameter("result") ;
    String view = (String)request.getAttribute("typeView") ;
    
    if (date!=null && !date.equals(""))  {
    	ActionUtil.setParameterFilterSql("result","vhr.id", request) ;
    	String dateEnd = (String)request.getParameter("dateEnd") ;
    	if (dateEnd==null||dateEnd.equals("")) {
    		dateEnd=date ;
    	}
    	request.setAttribute("dateEnd", dateEnd) ;
    	request.setAttribute("isReportBase", ActionUtil.isReportBase(date, dateEnd,request));
    	String dep = (String) request.getParameter("department") ; 
    	if (dep!=null && !dep.equals("") && !dep.equals("0")) {
    		request.setAttribute("depIsNoOmc", " and (dmc.department_id='"+dep+"' or d.isNoOmc='1' and pdmc.department_id='"+dep+"')");
    		request.setAttribute("dep", " and dmc.department_id='"+dep+"'");
    		request.setAttribute("dep1", " and hmc1.department_id='"+dep+"'");
    	} else {
    		request.setAttribute("depIsNoOmc", "") ;
    		request.setAttribute("dep", "") ;
    		request.setAttribute("dep1", "") ;
    	}
    	if ((view!=null && (view.equals("1") || view.equals("5")) )&& result!=null && !result.equals("")) {
    	%>
    
    <msh:section title="${infoTypePat} ${infoTypeEmergency} ${infoTypeOperation}. Период с ${param.dateBegin} по ${dateEnd}. ${infoSearch} ${dateInfo}">
    <msh:sectionContent>
    <ecom:webQuery isReportBase="${isReportBase}" maxResult="2000" name="journal_list" nativeSql="
    
    select  
    hmc.id as hmcid
    ,ss.code as sscode
    ,pat.lastname||' '||pat.firstname||' '||coalesce(pat.middlename,'') as fio
    ,	cast(to_char(${dateT},'yyyy') as int)-cast(to_char(pat.birthday,'yyyy') as int)
+(case when (cast(to_char(${dateT}, 'mm') as int)-cast(to_char(pat.birthday, 'mm') as int)
+(case when (cast(to_char(${dateT},'dd') as int) - cast(to_char(pat.birthday,'dd') as int)<0) then -1 else 0 end)<0)
then -1 else 0 end) as age
    ,case when hmc.emergency='1' then 'Э' else 'П' end
    
    ,vpat.name as vpatname
    , case 
		when (coalesce(hmc.dateFinish,CURRENT_DATE)-hmc.dateStart)=0 then 1 
		when vht.code='DAYTIMEHOSP' then ((coalesce(hmc.dateFinish,CURRENT_DATE)-hmc.dateStart)+1) 
		else (coalesce(hmc.dateFinish,CURRENT_DATE)-hmc.dateStart)
		end as countDays
	,hmc.dateStart as hmcdatestart,hmc.dateFinish as hmcdatefinish
    , list(distinct case when vpd.code='1' and vdrt.code='3' then mkb.code||' '||mkb.name else null end) as diag
	, cast(count(soHosp.id)+count(soDep.id) as int) as cntOper
	, list(to_char(soDep.operationDate,'dd.mm.yyyy')|| '-'||voDep.code|| ' '||voDep.name) ||' ' ||list(to_char(soHosp.operationDate,'dd.mm.yyyy')|| '-'||voHosp.code|| ' '||voHosp.name)
	, d.name ||case when d.isNoOmc='1' then coalesce(' ('||pd.name||')','') else '' end as depname
	, coalesce(oml.name,'') as omlname1,coalesce(vof.name,'') as whomOrder1  
	,coalesce(vhtS.name,'') as vhtSname
    , list(distinct case when vpd.code='1' and vdrt.code='5' then mkb.code||' '||mkb.name else null end) as diagPat
    from MedCase as hmc
   	left join diagnosis diag on diag.medcase_id=hmc.id
   	left join VocIdc10 mkb on mkb.id=diag.idc10_id 
   	left join VocPriorityDiagnosis vpd on vpd.id=diag.priority_id
   	left join VocDiagnosisRegistrationType vdrt on vdrt.id=diag.registrationType_id
    left join statisticstub ss on hmc.statisticstub_id=ss.id
    left join MedCase as dmc on dmc.dtype='DepartmentMedCase' and hmc.id=dmc.parent_id
    left join MedCase as pdmc on pdmc.dtype='DepartmentMedCase' and dmc.prevMedCase_id=pdmc.id
    left join mislpu as pd on pd.id=pdmc.department_id  
    left join MedCase as admc on admc.dtype='DepartmentMedCase' and hmc.id=admc.parent_id 
    left join vocservicestream as vss on vss.id=hmc.servicestream_id
    left join VocPreAdmissionTime vpat on vpat.id=hmc.preAdmissionTime_id 
    left join mislpu as d on d.id=dmc.department_id 
    
    left join patient pat on pat.id=hmc.patient_id
    left join VocHospType vht on vht.id=hmc.hospType_id
    left join VocHospType vhtS on vhtS.id=hmc.sourceHospType_id
    left join address2 adr on adr.addressId = pat.address_addressId
    left join vocHospitalizationResult vhr on vhr.id=hmc.result_id
    left join SurgicalOperation soHosp on soHosp.medCase_id=hmc.id
    left join MedService voHosp on soHosp.medService_id=voHosp.id
    left join SurgicalOperation soDep on soDep.medCase_id=admc.id
    left join MedService voDep on soDep.medService_id=voDep.id
    left join Omc_Oksm ok on pat.nationality_id=ok.id
    
    left join MisLpu oml on oml.id=hmc.orderLpu_id
    left join Omc_Frm vof on vof.id=hmc.orderType_id
    
    where hmc.DTYPE='HospitalMedCase' 
    and ${dateT} between to_date('${param.dateBegin}','dd.mm.yyyy')  
    	and to_date('${dateEnd}','dd.mm.yyyy') 
    	
    	and dmc.dateFinish is not null
    	${resultSql}
    	${depIsNoOmc}
    	
    ${addPat} 
    ${addEmergency}
    
    group by hmc.id,pat.lastname,pat.firstname,pat.middlename,pat.birthday
    ,vpat.name,hmc.dateFinish,hmc.dateStart,hmc.emergency,vht.code,ss.code
    ,d.name,d.isNoOmc,pd.name, oml.name,vof.name,vhtS.name
    ${hav} ${addOperation}
    order by pat.lastname,pat.firstname,pat.middlename
    " />
        <msh:table name="journal_list" printToExcelButton="Сохранить в excel"
        viewUrl="entitySubclassShortView-mis_medCase.do"
         action="entitySubclassView-mis_medCase.do" idField="1" noDataMessage="Не найдено">
            <msh:tableColumn columnName="#" property="sn"/>
            <msh:tableColumn columnName="Отделение" property="13"/>
            <msh:tableColumn columnName="№ стат. карты" property="2"/>
            <msh:tableColumn columnName="ФАМИЛИЯ ИМЯ ОТЧЕСТВО" property="3"/>
            <msh:tableColumn columnName="Возраст" property="4"/>
            <msh:tableColumn columnName="Поступил" property="5"/>
            <msh:tableColumn columnName="Доставлен в стационар" property="6"/>
            <msh:tableColumn columnName="Кол-во койко дней" property="7"/>
            <msh:tableColumn columnName="Дата поступления" property="8"/>
            <msh:tableColumn columnName="Дата выписки" property="9"/>
            <msh:tableColumn columnName="Диагноз выписной" property="10"/>
            <msh:tableColumn columnName="Диагноз пат." property="17"/>
            <msh:tableColumn columnName="Кол-во операций" property="11"/>
            <msh:tableColumn columnName="Операции" property="12"/>
            <msh:tableColumn columnName="Тип напр.ЛПУ" property="16"/>
            <msh:tableColumn columnName="Кем направлен" property="14"/>
            <msh:tableColumn columnName="Кем доставлен" property="15"/>
        </msh:table>
    </msh:sectionContent>
    </msh:section>
    <%} 
    	if (view==null || view.equals("2") || view.equals("5")) { 
    %>
    <msh:section title="Свод по отделениям">
    <msh:sectionContent>
    <ecom:webQuery isReportBase="${isReportBase}" name="journal_list_swod" nativeSql="
    
    select  '&department='||d.id||'&result='||vhr.id as id
    ,d.name as depname,vhr.name as vhrname
    ,count(*)
    ,count(case when hmc.emergency='1' then 1 else null end) as cntEmer
    ,count(case when hmc.emergency='1' then null else 1 end) as cntPlan
	,count(case
		when (select count(*) from SurgicalOperation so left join medcase m on m.id=so.medcase_id where m.id=hmc.id or m.parent_id=hmc.id)>0 then 1 else null
		end) as cntoper
    
    , cast(round(sum(case 
		when (coalesce(hmc.dateFinish,CURRENT_DATE)-hmc.dateStart)=0 then 1 
		when vht.code='DAYTIMEHOSP' then ((coalesce(hmc.dateFinish,CURRENT_DATE)-hmc.dateStart)+1) 
		else (coalesce(hmc.dateFinish,CURRENT_DATE)-hmc.dateStart)
		end)/cast(count(hmc.id) as numeric),1) as numeric) as spCountDays
	
    from MedCase as hmc
    left join statisticstub ss on hmc.statisticstub_id=ss.id
    left join MedCase as dmc on dmc.dtype='DepartmentMedCase' and hmc.id=dmc.parent_id 
    left join vocservicestream as vss on vss.id=hmc.servicestream_id
    left join VocPreAdmissionTime vpat on vpat.id=hmc.preAdmissionTime_id 
    left join mislpu as d on d.id=dmc.department_id 
    left join patient pat on pat.id=hmc.patient_id
    left join VocHospType vht on vht.id=hmc.hospType_id
    left join address2 adr on adr.addressId = pat.address_addressId
    left join vocHospitalizationResult vhr on vhr.id=hmc.result_id
    left join Omc_Oksm ok on pat.nationality_id=ok.id
    where hmc.DTYPE='HospitalMedCase' 
    and ${dateT} between to_date('${param.dateBegin}','dd.mm.yyyy')  
    	and to_date('${dateEnd}','dd.mm.yyyy') 
    	
    	and dmc.dateFinish is not null
    	${resultSql}
    	${dep}
    	
    ${addPat} 
    
    
    group by d.id,d.name,vhr.id,vhr.name
    order by d.name,vhr.name
    
    " />
        <msh:table name="journal_list_swod"  printToExcelButton="Сохранить в excel"
         action="stac_resultPatient_list.do?dateBegin=${param.dateBegin}&dateEnd=${dateEnd}" idField="1" noDataMessage="Не найдено">
            <msh:tableColumn columnName="#" property="sn"/>
            <msh:tableColumn columnName="Отделение" property="2"/>
            <msh:tableColumn columnName="Результат госпитализации" property="3"/>
            <msh:tableColumn isCalcAmount="true" columnName="Кол-во" property="4" />
            <msh:tableColumn isCalcAmount="true" columnName="Кол-во экстренных" property="5" />
            <msh:tableColumn isCalcAmount="true" columnName="Кол-во плановых" property="6" />
            <msh:tableColumn isCalcAmount="true" columnName="Кол-во оперированных" property="7" />
            <msh:tableColumn columnName="Ср. койко дней" property="8" />
            
        </msh:table>
    </msh:sectionContent>
    </msh:section>
    <%} 
    	if (view==null || view.equals("3") || view.equals("5")) { 
    %>
    <msh:section title="Свод по отделениям без учета отд., которые не входят в ОМС">
    <msh:sectionContent>
    <ecom:webQuery isReportBase="${isReportBase}" name="journal_list_swod" nativeSql="
    
    select  
    '&department='||case when d.isNoOmc='1' and pd.id is not null then pd.id else d.id end
    ||'&result='||vhr.id as id
    
    ,case when d.isNoOmc='1' and pd.id is not null then pd.name else d.name end as depname
    ,vhr.name as vhrname
    ,count(*)
    ,count(case when hmc.emergency='1' then 1 else null end) as cntEmer
    ,count(case when hmc.emergency='1' then null else 1 end) as cntPlan
	,count(case
		when (select count(*) from SurgicalOperation so left join medcase m on m.id=so.medcase_id where m.id=hmc.id or m.parent_id=hmc.id)>0 then 1 else null
		end) as cntoper
    
    , cast(round(sum(case 
		when (coalesce(hmc.dateFinish,CURRENT_DATE)-hmc.dateStart)=0 then 1 
		when vht.code='DAYTIMEHOSP' then ((coalesce(hmc.dateFinish,CURRENT_DATE)-hmc.dateStart)+1) 
		else (coalesce(hmc.dateFinish,CURRENT_DATE)-hmc.dateStart)
		end)/cast(count(hmc.id) as numeric),1) as numeric) as spCountDays
	
    from MedCase as hmc
    left join statisticstub ss on hmc.statisticstub_id=ss.id
    left join MedCase as dmc on dmc.dtype='DepartmentMedCase' and hmc.id=dmc.parent_id
    left join MedCase as pdmc on pdmc.dtype='DepartmentMedCase' and pdmc.id=dmc.prevMedCase_id 
    left join vocservicestream as vss on vss.id=hmc.servicestream_id
    left join VocPreAdmissionTime vpat on vpat.id=hmc.preAdmissionTime_id 
    left join mislpu as d on d.id=dmc.department_id 
    left join mislpu as pd on pd.id=pdmc.department_id 
    left join patient pat on pat.id=hmc.patient_id
    left join VocHospType vht on vht.id=hmc.hospType_id
    left join address2 adr on adr.addressId = pat.address_addressId
    left join vocHospitalizationResult vhr on vhr.id=hmc.result_id
    left join Omc_Oksm ok on pat.nationality_id=ok.id
    where hmc.DTYPE='HospitalMedCase' 
    and ${dateT} between to_date('${param.dateBegin}','dd.mm.yyyy')  
    	and to_date('${dateEnd}','dd.mm.yyyy') 
    	
    	and dmc.dateFinish is not null
    	${resultSql}
    	${depIsNoOmc}
    	
    ${addPat} 
    
    
    group by 
    case when d.isNoOmc='1' and pd.id is not null then pd.id else d.id end
    , case when d.isNoOmc='1' and pd.id is not null then pd.name else d.name end
    ,vhr.id,vhr.name
    order by case when d.isNoOmc='1' and pd.id is not null then pd.name else d.name end
    ,vhr.name
    
    " />
        <msh:table name="journal_list_swod"  printToExcelButton="Сохранить в excel"
         action="stac_resultPatient_list.do?dateBegin=${param.dateBegin}&dateEnd=${dateEnd}" idField="1" noDataMessage="Не найдено">
            <msh:tableColumn columnName="#" property="sn"/>
            <msh:tableColumn columnName="Отделение" property="2"/>
            <msh:tableColumn columnName="Результат госпитализации" property="3"/>
            <msh:tableColumn isCalcAmount="true" columnName="Кол-во" property="4"/>
            <msh:tableColumn isCalcAmount="true" columnName="Кол-во экстренных" property="5"/>
            <msh:tableColumn isCalcAmount="true" columnName="Кол-во плановых" property="6"/>
            <msh:tableColumn isCalcAmount="true" columnName="Кол-во оперированных" property="7"/>
            <msh:tableColumn columnName="Ср. койко дней" property="8"/>
            
        </msh:table>
    </msh:sectionContent>
    </msh:section>
    <%} 
    	if (view==null || view.equals("4") || view.equals("5")) { 
    %>
    <msh:section title="Общий свод">
    <msh:sectionContent>
    <ecom:webQuery isReportBase="${isReportBase}" name="journal_list_swod_all" nativeSql="
    
    select  
    '&result='||vhr.id as id
    ,vhr.name as vhrname
	,count(*) as cnt
    ,count(case when hmc.emergency='1' then 1 else null end) as cntEmer
    ,count(case when hmc.emergency='1' then null else 1 end) as cntPlan
	,count(case
		when (select count(*) from SurgicalOperation so left join medcase m on m.id=so.medcase_id where m.id=hmc.id or m.parent_id=hmc.id)>0 then 1 else null
		end) as cntoper
    
    , cast(round(sum(case 
		when (coalesce(hmc.dateFinish,CURRENT_DATE)-hmc.dateStart)=0 then 1 
		when vht.code='DAYTIMEHOSP' then ((coalesce(hmc.dateFinish,CURRENT_DATE)-hmc.dateStart)+1) 
		else (coalesce(hmc.dateFinish,CURRENT_DATE)-hmc.dateStart)
		end)/cast(count(hmc.id) as numeric),1) as numeric) as spCountDays
    from MedCase as hmc
    left join statisticstub ss on hmc.statisticstub_id=ss.id
    left join MedCase as dmc on dmc.dtype='DepartmentMedCase' and hmc.id=dmc.parent_id 
    left join vocservicestream as vss on vss.id=hmc.servicestream_id
    left join VocPreAdmissionTime vpat on vpat.id=hmc.preAdmissionTime_id 
    left join mislpu as d on d.id=dmc.department_id 
    left join patient pat on pat.id=hmc.patient_id
    left join VocHospType vht on vht.id=hmc.hospType_id
    left join address2 adr on adr.addressId = pat.address_addressId
    left join vocHospitalizationResult vhr on vhr.id=hmc.result_id
    left join Omc_Oksm ok on pat.nationality_id=ok.id
    where hmc.DTYPE='HospitalMedCase' 
    and ${dateT} between to_date('${param.dateBegin}','dd.mm.yyyy')  
    	and to_date('${dateEnd}','dd.mm.yyyy') 
    	
    	and dmc.dateFinish is not null
    	${resultSql}
    	${dep}
    	
    ${addPat} 
    
    group by vhr.id,vhr.name

    
    " />
        <msh:table name="journal_list_swod_all"  printToExcelButton="Сохранить в excel"
         action="stac_resultPatient_list.do?dateBegin=${param.dateBegin}&dateEnd=${dateEnd}" idField="1" noDataMessage="Не найдено">
            <msh:tableColumn columnName="Результат госпитализации" property="2"/>
            <msh:tableColumn isCalcAmount="true" columnName="Кол-во" property="3"/>
            <msh:tableColumn isCalcAmount="true" columnName="Кол-во экстренных" property="4"/>
            <msh:tableColumn isCalcAmount="true" columnName="Кол-во плановых" property="5"/>
            <msh:tableColumn isCalcAmount="true" columnName="Кол-во оперированных" property="6"/>
            <msh:tableColumn columnName="Ср. койко/дней" property="7"/>
            
        </msh:table>
    </msh:sectionContent>
    </msh:section>
    <% }} else {%>
    	<i>Выберите параметры и нажмите найти </i>
    	<% }   %>
    <script type='text/javascript'>
    
     checkFieldUpdate('typeDate','${typeDate}',2) ;
     checkFieldUpdate('typePatient','${typePatient}',3) ;
     checkFieldUpdate('typeOperation','${typeOperation}',3) ;
     checkFieldUpdate('typeEmergency','${typeEmergency}',3) ;
     checkFieldUpdate('typeView','${typeView}',4) ;
     
   
    function checkFieldUpdate(aField,aValue,aMax) {
    	eval('var chk =  document.forms[0].'+aField) ;
    	if ((+aValue)>aMax) {
    		chk[+aMax-1].checked='checked' ;
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
			+":"+getCheckedValue("typePatient")
			+":"+getCheckedValue("typeEmergency")
			+":"+getCheckedValue("typeOperation")
			+":"+getCheckedValue("typeDate")
			+":"+$('department').value
			+":"+$('result').value
			;
			//alert(args) ;
		$('param').value = args ;
    }
    function find() {
    	var frm = document.forms[0] ;
    	frm.target='' ;
    	frm.action='stac_resultPatient_list.do' ;
    }
    function print() {
    	updateId() ;
    	var frm = document.forms[0] ;
    	frm.target='_blank' ;
    	
    	frm.action='print-stac_resultPatient.do' ;
    }
    </script>
  </tiles:put>
</tiles:insert>

