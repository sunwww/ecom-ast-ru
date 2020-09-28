<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://struts.apache.org/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://www.nuzmsh.ru/tags/msh" prefix="msh" %>
<%@ taglib uri="http://www.ecom-ast.ru/tags/ecom" prefix="ecom" %>
<%@ taglib tagdir="/WEB-INF/tags" prefix="tags" %>
<%@page import="ru.ecom.web.util.ActionUtil"%>

<tiles:insert page="/WEB-INF/tiles/mainLayout.jsp" flush="true" >

    <tiles:put name='title' type='string'>
        <msh:title mainMenu="StacJournal">Просмотр данных по госпитализациям, без прикрепленных полисов</msh:title>
    </tiles:put>

    <tiles:put name='side' type='string'>
        <tags:stac_journal currentAction="stac_receivedWithoutPolicy"/>
    </tiles:put>
    
  <tiles:put name="body" type="string">
    <msh:form action="/poly_ticketsByNonredidentPatientList.do" defaultField="department" disableFormDataConfirm="true" method="GET">
    <msh:panel>
    	<input type="hidden" name="s" value="HospitalPrintService"/>
    	<input type="hidden" name="m" value="receivedWithoutPolicy"/>
      <msh:row>
        <msh:separator label="Дополнительные параметры для реестра (в своде не учитываются)" colSpan="7"/>
      </msh:row>
      <msh:row>
        <td class="label" title="Поиск по пациентам (typePatient)" colspan="1"><label for="typeDurationName" id="typeDurationLabel">Длительность:</label></td>
        <td onclick="this.childNodes[1].checked='checked';">
        	<input type="radio" name="typeDuration" value="1">  более 3х дней
        </td>
        <td onclick="this.childNodes[1].checked='checked';"   colspan="2">
        	<input type="radio" name="typeDuration" value="2" >  до 3х дней включительно
        </td>
        <td onclick="this.childNodes[1].checked='checked';">
        	<input type="radio" name="typeDuration" value="3">  все
        </td>
        </msh:row>
      <msh:row>
        <td class="label" title="Поиск по пациентам (typePatientIs)" colspan="1"><label for="typeDurationName" id="typeDurationLabel">Пациенты:</label></td>
        <td onclick="this.childNodes[1].checked='checked';">
        	<input type="radio" name="typePatientIs" value="1">  включая состоящих
        </td>
        <td onclick="this.childNodes[1].checked='checked';"   colspan="3">
        	<input type="radio" name="typePatientIs" value="2" >  только поступивших за период
        </td>
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
        <msh:separator label="Основные параметры" colSpan="7"/>
      </msh:row>
      <msh:row>
        <td class="label" title="Поиск по пациентов (typeDischargePatientIs)" colspan="1"><label for="typeDurationName" id="typeDurationLabel">Пациенты:</label></td>
        <td onclick="this.childNodes[1].checked='checked';" colspan="1">
        	<input type="radio" name="typeDischargePatientIs" value="1">  выписанные
        </td>
        <td onclick="this.childNodes[1].checked='checked';"   colspan="1">
        	<input type="radio" name="typeDischargePatientIs" value="2" > состоящие
        </td>
        <td onclick="this.childNodes[1].checked='checked';"   colspan="1">
        	<input type="radio" name="typeDischargePatientIs" value="3" > все
        </td>
        </msh:row>

        <msh:row>
	        <td class="label" title="Просмотр данных (typeView)" colspan="1"><label for="typeViewName" id="typeViewLabel">Отобразить:</label></td>
	        <td onclick="this.childNodes[1].checked='checked';">
	        	<input type="radio" name="typeView" value="1">  реестр по ОМС
	        </td>
	        <td onclick="this.childNodes[1].checked='checked';">
	        	<input type="radio" name="typeView" value="2">  реестр по ОМС+ДРУГИМ
	        </td>
	        <td colspan="3" onclick="this.childNodes[1].checked='checked';">
	        	<input type="radio" name="typeView" value="3">  реестр по ОМС не проверенных по базе фонда
	        </td>
	    </msh:row>
	    <msh:row>
	    	<td></td>
	        <td colspan="1" onclick="this.childNodes[1].checked='checked';">
	        	<input type="radio" name="typeView" value="4">  реестр по ДРУГИМ
	        </td>
	        <td colspan="1" onclick="this.childNodes[1].checked='checked';">
	        	<input type="radio" name="typeView" value="5">  реестр по ИНОГ.ПОЛИСАМ
	        </td>
	        <td onclick="this.childNodes[1].checked='checked';">
	        	<input type="radio" name="typeView" value="6"  >  свод по отделениям
	        </td>
	        <td onclick="this.childNodes[1].checked='checked';">
	        	<input type="radio" name="typeView" value="7">  все
	        </td>
        </msh:row>
        <msh:row>
        	<msh:autoComplete property="department" horizontalFill="true" label="Отделение" vocName="lpu" fieldColSpan="7"/>
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
    
    String date = request.getParameter("dateBegin") ;
    String view = ActionUtil.updateParameter("ExtDispAction","typeView","1", request) ;
    String typeDischargePatientIs = ActionUtil.updateParameter("ExtDispAction","typeDischargePatientIs","1", request) ; 
	String typePatientIs = ActionUtil.updateParameter("ExtDispAction","typePatientIs","1", request) ;
	String typeDuration = ActionUtil.updateParameter("ExtDispAction","typeDuration","1", request) ;
	String typeEmergency = ActionUtil.updateParameter("ExtDispAction","typeEmergency","1", request) ;
	request.setAttribute("typeView", view);
	request.setAttribute("typePatientIs", typePatientIs);
	request.setAttribute("typeDuration", typeDuration);
	request.setAttribute("typeEmergency", typeEmergency);
    if (date!=null && !date.equals(""))  {
    	String dateEnd = request.getParameter("dateEnd") ;
    	String department = request.getParameter("department") ;
    	if (department!=null && !department.equals("")) {
    		request.setAttribute("departmentSql", " and hmc.department_id="+department) ;
    		request.setAttribute("department1Sql", " and case when dmc.id is not null then ml1.id else ml.id end="+department) ;
    	}
    	if (dateEnd==null||dateEnd.equals("")) {
    		request.setAttribute("dateEnd", date) ;
    	} else {
    		request.setAttribute("dateEnd", dateEnd) ;
    	}
    	if ("1".equals(typePatientIs)) {
    		request.setAttribute("isPat", " or hmc.dateFinish is null") ;
    	}
    	if ("1".equals(typeDischargePatientIs)) {
    		request.setAttribute("isDischarge", " and hmc.dateFinish is not null") ;
    	} else if ("2".equals(typeDischargePatientIs)) {
    		request.setAttribute("isDischarge", " and hmc.dateFinish is null") ;
    	} 
    	if (view.equals("1") || view.equals("7")) {
    	%>
    
    <msh:section title="${infoTypePat} ${infoTypeEmergency} ${infoTypeDuration}. Период с ${param.dateBegin} по ${dateEnd}. ${infoSearch}">
    
    <msh:sectionContent>
    <ecom:webQuery name="journal_hosp" nativeSql="
    
    select 
    hmc.id as hospid
    , dep.name as depname
    , vss.name as vssname 
    , hmc.dateStart as hospdateStart
    , ss.code as statcard
    , vas.name as vasname
    , pat.lastname||' '||pat.firstname||' '||pat.middlename || ' г.р. '|| pat.birthday as pbirthday
    , case when (ok.voc_code is not null and ok.voc_code!='643') then 'ИНОСТ'  
    when pvss.omccode='И0' then 'ИНОГ' else '' end as typePatient
    , case when (select count(*) from medpolicy mp where
     mp.patient_id=pat.id and
    mp.dtype like 'MedPolicyOmc%' and mp.actualDateFrom<=hmc.dateStart and
    (mp.actualDateTo is null or mp.actualDateTo>=hmc.dateStart)
    )>0 then 'Есть' else '' end  as policy
    ,case when hmc.emergency='1' then 'Э' else 'П' end as emer
    ,
    	  case 
			when (coalesce(hmc.dateFinish,CURRENT_DATE)-hmc.dateStart)=0 then 1 
			when vht.code='DAYTIMEHOSP' then ((coalesce(hmc.dateFinish,CURRENT_DATE)-hmc.dateStart)+1) 
			else (coalesce(hmc.dateFinish,CURRENT_DATE)-hmc.dateStart)
		  end
    	
    	 as sum2
    ,hmc.dateFinish as hospdateFinish
from Medcase hmc 
left join StatisticStub ss on ss.id=hmc.statisticStub_id 
left join MisLpu dep on dep.id=hmc.department_id 
left join vocservicestream vss on vss.id=hmc.servicestream_id 
left join patient pat on pat.id=hmc.patient_id 
left join vocAdditionStatus vas on vas.id=pat.additionStatus_id 
left join medcase_medpolicy pol on pol.medCase_id=hmc.id
left join Omc_Oksm ok on pat.nationality_id=ok.id
left join VocSocialStatus pvss on pvss.id=pat.socialStatus_id
left join VocHospType vht on vht.id=hmc.hospType_id
left join address2 adr on adr.addressId = pat.address_addressId
 where (hmc.dateStart between to_date('${param.dateBegin}','dd.mm.yyyy') 
      and to_date('${dateEnd}','dd.mm.yyyy') ${isPat})
      and hmc.DTYPE='HospitalMedCase'
      ${departmentSql}
 and (vss.code = 'OBLIGATORYINSURANCE') 

and hmc.deniedHospitalizating_id is null
${addPat} ${addEmergency} ${isDischarge}
group by hmc.id, dep.name, vss.name, hmc.dateStart, ss.code 
    , vas.name , pat.id , pat.lastname,pat.firstname,pat.middlename 
    , pat.birthday ,ok.voc_code,pvss.omccode,hmc.emergency
    ,hmc.dateFinish,vht.code
having count(pol.medCase_id)=0 ${addDuration}
order by dep.name,vss.name,pat.lastname,pat.firstname,pat.middlename


        "  />
        <msh:table name="journal_hosp"
        viewUrl="entitySubclassShortView-mis_medCase.do" 
        action="entitySubclassView-mis_medCase.do" idField="1" noDataMessage="Не найдено">
            <msh:tableColumn columnName="#" property="sn"/>
            <msh:tableColumn columnName="Вид оплаты" property="3"/>
            <msh:tableColumn columnName="Показания" property="10"/>
            <msh:tableColumn columnName="Отделение" property="2"/>
            <msh:tableColumn columnName="№ стат. карты" property="5"/>
            <msh:tableColumn columnName="Дата пост." property="4"/>
            <msh:tableColumn columnName="Дата выписки" property="12"/>
            <msh:tableColumn columnName="К.дни" property="11" isCalcAmount="true"/>
            <msh:tableColumn columnName="Тип" property="8"/>
            <msh:tableColumn columnName="ФИО пациента" property="7"/>
            <msh:tableColumn columnName="Наличие страх. документов" property="9"/>
            
        </msh:table>
    </msh:sectionContent>

    </msh:section>
    
    <%} if (view.equals("2") || view.equals("7")) {
    	%>
    
    <msh:section title="По безполисным по состоящим или направленным в отделение по ОМС+ДРУГИМ. ${infoTypePat} ${infoTypeEmergency} ${infoTypeDuration}. Период с ${param.dateBegin} по ${dateEnd}. ${infoSearch}">
    
    <msh:sectionContent>
    <ecom:webQuery name="journal_hosp" nativeSql="
    select  hmc.id as hospid
    , coalesce(ml.name,ml1.name) as depname
    , vss.name as vssname 
    , hmc.dateStart as hospdateStart
    , ss.code as statcard
    , vas.name as vasname
    , pat.lastname||' '||pat.firstname||' '||pat.middlename || ' г.р. '|| pat.birthday as pbirthday
    , case when (ok.voc_code is not null and ok.voc_code!='643') then 'ИНОСТ'  
    when pvss.omccode='И0' then 'ИНОГ' else '' end as typePatient
    , case when (select count(*) from medpolicy mp1 where
     mp1.patient_id=pat.id and
    mp1.dtype like 'MedPolicyOmc%' and mp1.actualDateFrom<=hmc.dateStart and
    (mp1.actualDateTo is null or mp1.actualDateTo>=hmc.dateStart)
    )>0 then 'Есть' else '' end  as policy
    ,case when hmc.emergency='1' then 'Э' else 'П' end as emer
    ,sum(
    	  case 
			when (coalesce(hmc.dateFinish,CURRENT_DATE)-hmc.dateStart)=0 then 1 
			when vht.code='DAYTIMEHOSP' then ((coalesce(hmc.dateFinish,CURRENT_DATE)-hmc.dateStart)+1) 
			else (coalesce(hmc.dateFinish,CURRENT_DATE)-hmc.dateStart)
		  end
    	
    	) as sum2
    ,hmc.dateFinish as hospdateFinish
from Medcase hmc  
left join StatisticStub ss on ss.id=hmc.statisticStub_id
left join Patient pat on pat.id=hmc.patient_id
left join mislpu ml on ml.id=hmc.department_id 
left join MedCase dmc on dmc.parent_id=hmc.id 
left join mislpu ml1 on ml1.id=dmc.department_id 
left join vocservicestream vss on vss.id=hmc.servicestream_id  
left join medcase_medpolicy pol on pol.medCase_id=hmc.id  
left join medpolicy polI on polI.id=pol.policies_id  
left join reg_ic ri on ri.id=polI.company_id  
left join vocAdditionStatus vas on vas.id=pat.additionStatus_id 
left join Omc_Oksm ok on pat.nationality_id=ok.id
left join VocSocialStatus pvss on pvss.id=pat.socialStatus_id
left join VocHospType vht on vht.id=hmc.hospType_id
left join address2 adr on adr.addressId = pat.address_addressId

where  (hmc.dateStart between to_date('${param.dateBegin}','dd.mm.yyyy')
      and to_date('${dateEnd}','dd.mm.yyyy') ${isPat})
      and hmc.DTYPE='HospitalMedCase' and dmc.dtype='DepartmentMedCase'
      and hmc.deniedHospitalizating_id is null  
        and (dmc.id is null or dmc.transferDate is null) 
      ${department1Sql}
and (vss.code = 'OBLIGATORYINSURANCE'
 and (pol.id is null or (polI.dtype = 'MedPolicyOmc' and (ri.deprecated='1' or ri.isRegion='0' or ri.isRegion is null)))  or vss.code='OTHER') 
${addPat} ${addEmergency}  ${isDischarge}
${addDuration1}

group by  hmc.id  , 
hmc.dateStart ,hmc.dateFinish,pat.id
,pat.lastname,pat.firstname,pat.middlename,ss.code,vss.name,hmc.emergency, pat.birthday
, ok.voc_code,vht.code,vas.name,pvss.omccode,ml1.name ,ml.name
order by coalesce(ml.name,ml1.name),vss.name,pat.lastname,pat.firstname,pat.middlename
        "  />
        <msh:table name="journal_hosp"
        viewUrl="entitySubclassShortView-mis_medCase.do" 
        action="entitySubclassView-mis_medCase.do" idField="1" noDataMessage="Не найдено">
           <msh:tableColumn columnName="#" property="sn"/>
            <msh:tableColumn columnName="Вид оплаты" property="3"/>
            <msh:tableColumn columnName="Показания" property="10"/>
            <msh:tableColumn columnName="Отделение" property="2"/>
            <msh:tableColumn columnName="№ стат. карты" property="5"/>
            <msh:tableColumn columnName="Дата пост." property="4"/>
            <msh:tableColumn columnName="Дата выписки" property="12"/>
            <msh:tableColumn columnName="К.дни" property="11"/>
            <msh:tableColumn columnName="Тип" property="8"/>
            <msh:tableColumn columnName="ФИО пациента" property="7"/>
            <msh:tableColumn columnName="Наличие страх. документов" property="9"/>
            
        </msh:table>
    </msh:sectionContent>
    
    </msh:section>
    
    <%} if (view.equals("3") || view.equals("7")) {
    	%>
    <msh:section>
    <msh:sectionTitle>Не была произведена проверка по базе фонда. ${infoTypePat} ${infoTypeEmergency} ${infoTypeDuration}. Период с ${param.dateBegin} по ${dateEnd}. ${infoSearch}
    </msh:sectionTitle>
    
    <msh:sectionContent>
    <ecom:webQuery name="journal_hosp" nativeSql="
    
    select 
    hmc.id as hospid
    , dep.name as depname
    , vss.name as vssname 
    , hmc.dateStart as hospdateStart
    , ss.code as statcard
    , vas.name as vasname
    , pat.lastname||' '||pat.firstname||' '||pat.middlename || ' г.р. '|| pat.birthday as pbirthday
    , case when (ok.voc_code is not null and ok.voc_code!='643') then 'ИНОСТ'  
    when pvss.omccode='И0' then 'ИНОГ' else '' end as typePatient
    
    ,case when hmc.emergency='1' then 'Э' else 'П' end as emer
    ,
    	  case 
			when (coalesce(hmc.dateFinish,CURRENT_DATE)-hmc.dateStart)=0 then 1 
			when vht.code='DAYTIMEHOSP' then ((coalesce(hmc.dateFinish,CURRENT_DATE)-hmc.dateStart)+1) 
			else (coalesce(hmc.dateFinish,CURRENT_DATE)-hmc.dateStart)
		  end
    	
    	as sum2
    ,hmc.dateFinish as hospdateFinish
from Medcase hmc 
left join StatisticStub ss on ss.id=hmc.statisticStub_id 
left join MisLpu dep on dep.id=hmc.department_id 
left join vocservicestream vss on vss.id=hmc.servicestream_id 
left join patient pat on pat.id=hmc.patient_id 
left join vocAdditionStatus vas on vas.id=pat.additionStatus_id 
left join medcase_medpolicy pol on pol.medCase_id=hmc.id
left join medpolicy polI on polI.id=pol.policies_id
left join Omc_Oksm ok on pat.nationality_id=ok.id
left join VocSocialStatus pvss on pvss.id=pat.socialStatus_id
left join VocHospType vht on vht.id=hmc.hospType_id
left join address2 adr on adr.addressId = pat.address_addressId
left join PatientFond pf on pf.commonNumber=polI.commonNumber
 where (hmc.dateStart between to_date('${param.dateBegin}','dd.mm.yyyy') 
      and to_date('${dateEnd}','dd.mm.yyyy') ${isPat})
      and hmc.DTYPE='HospitalMedCase'
      ${departmentSql}
 and (vss.code = 'OBLIGATORYINSURANCE') 
and hmc.deniedHospitalizating_id is null and pol.dateSync is null
and polI.dtype='MedPolicyOmc'
${addPat} ${addEmergency}  ${isDischarge}
group by hmc.id, dep.name, vss.name, hmc.dateStart, ss.code 
    , vas.name , pat.id , pat.lastname,pat.firstname,pat.middlename 
    , pat.birthday ,ok.voc_code,pvss.omccode,hmc.emergency
    ,hmc.dateFinish,vht.code
having count(pol.medCase_id)>0 ${addDuration}
order by dep.name,vss.name,pat.lastname,pat.firstname,pat.middlename


        " nameFldSql="journal_hosp_sql" />
        <msh:table name="journal_hosp"
        viewUrl="entitySubclassShortView-mis_medCase.do" 
        action="entitySubclassView-mis_medCase.do" idField="1" noDataMessage="Не найдено">
            <msh:tableColumn columnName="#" property="sn"/>
            <msh:tableColumn columnName="Вид оплаты" property="3"/>
            <msh:tableColumn columnName="Показания" property="9"/>
            <msh:tableColumn columnName="Отделение" property="2"/>
            <msh:tableColumn columnName="№ стат. карты" property="5"/>
            <msh:tableColumn columnName="Дата пост." property="4"/>
            <msh:tableColumn columnName="Дата выписки" property="11"/>
            <msh:tableColumn columnName="К.дни" property="10" isCalcAmount="true"/>
            <msh:tableColumn columnName="Тип" property="8"/>
            <msh:tableColumn columnName="ФИО пациента" property="7"/>
        </msh:table>${journal_hosp_sql}
    </msh:sectionContent>

    </msh:section>
        <%	}
    if (view.equals("4") || view.equals("7")) {
    	%>
    
    <msh:section title="${infoTypePat} ${infoTypeEmergency} ${infoTypeDuration}. Период с ${param.dateBegin} по ${dateEnd}. ${infoSearch}">
    
    <msh:sectionContent>
    <ecom:webQuery name="journal_hosp" nativeSql="
    
    select 
    hmc.id as hospid
    , dep.name as depname
    , vss.name as vssname 
    , hmc.dateStart as hospdateStart
    , ss.code as statcard
    , vas.name as vasname
    , pat.lastname||' '||pat.firstname||' '||pat.middlename || ' г.р. '|| pat.birthday as pbirthday
    , case when (ok.voc_code is not null and ok.voc_code!='643') then 'ИНОСТ'  
    when pvss.omccode='И0' then 'ИНОГ' else '' end as typePatient
    , case when (select count(*) from medpolicy mp where
     mp.patient_id=pat.id and
    mp.dtype like 'MedPolicyOmc%' and mp.actualDateFrom<=hmc.dateStart and
    (mp.actualDateTo is null or mp.actualDateTo>=hmc.dateStart)
    )>0 then 'Есть' else '' end  as policy
    ,case when hmc.emergency='1' then 'Э' else 'П' end as emer
    ,sum(
    	  case 
			when (coalesce(hmc.dateFinish,CURRENT_DATE)-hmc.dateStart)=0 then 1 
			when vht.code='DAYTIMEHOSP' then ((coalesce(hmc.dateFinish,CURRENT_DATE)-hmc.dateStart)+1) 
			else (coalesce(hmc.dateFinish,CURRENT_DATE)-hmc.dateStart)
		  end
    	
    	) as sum2
    ,hmc.dateFinish as hospdateFinish
from Medcase hmc 
left join StatisticStub ss on ss.id=hmc.statisticStub_id 
left join MisLpu dep on dep.id=hmc.department_id 
left join vocservicestream vss on vss.id=hmc.servicestream_id 
left join patient pat on pat.id=hmc.patient_id 
left join vocAdditionStatus vas on vas.id=pat.additionStatus_id 
left join medcase_medpolicy pol on pol.medCase_id=hmc.id
left join Omc_Oksm ok on pat.nationality_id=ok.id
left join VocSocialStatus pvss on pvss.id=pat.socialStatus_id
left join VocHospType vht on vht.id=hmc.hospType_id
left join address2 adr on adr.addressId = pat.address_addressId
 where (hmc.dateStart between to_date('${param.dateBegin}','dd.mm.yyyy') 
      and to_date('${dateEnd}','dd.mm.yyyy') ${isPat})
      and hmc.DTYPE='HospitalMedCase'
 and (vss.code = 'OTHER') 

and hmc.deniedHospitalizating_id is null
${departmentSql}
${addPat} ${addEmergency}  ${isDischarge}
${addDuration1}
group by hmc.id, dep.name, vss.name, hmc.dateStart, ss.code 
    , vas.name , pat.id , pat.lastname,pat.firstname,pat.middlename 
    , pat.birthday ,ok.voc_code,pvss.omccode,hmc.emergency
    ,hmc.dateFinish
 
order by dep.name,vss.name,pat.lastname,pat.firstname,pat.middlename


        "  />
        <msh:table name="journal_hosp"
        viewUrl="entitySubclassShortView-mis_medCase.do" 
        action="entitySubclassView-mis_medCase.do" idField="1" noDataMessage="Не найдено">
            <msh:tableColumn columnName="#" property="sn"/>
            <msh:tableColumn columnName="Вид оплаты" property="3"/>
            <msh:tableColumn columnName="Показания" property="10"/>
            <msh:tableColumn columnName="Отделение" property="2"/>
            <msh:tableColumn columnName="№ стат. карты" property="5"/>
            <msh:tableColumn columnName="Дата пост." property="4"/>
            <msh:tableColumn columnName="Дата выписки" property="12"/>
            <msh:tableColumn columnName="К.дни" property="11"/>
            <msh:tableColumn columnName="Тип" property="8"/>
            <msh:tableColumn columnName="ФИО пациента" property="7"/>
            <msh:tableColumn columnName="Наличие страх. документов" property="9"/>
            
        </msh:table>
    </msh:sectionContent>

    </msh:section>
    
    <%} 
    if (view.equals("5") || view.equals("7")) {
    	%>
    
    <msh:section title="${infoTypePat} ${infoTypeEmergency} ${infoTypeDuration}. Период с ${param.dateBegin} по ${dateEnd}. ${infoSearch}">
    
    <msh:sectionContent>
    <ecom:webQuery name="journal_hosp" nativeSql="
    
    select 
    hmc.id as hospid
    , dep.name as depname
    , vss.name as vssname 
    , hmc.dateStart as hospdateStart
    , ss.code as statcard
    , vas.name as vasname
    , pat.lastname||' '||pat.firstname||' '||pat.middlename || ' г.р. '|| pat.birthday as pbirthday
    , case when (ok.voc_code is not null and ok.voc_code!='643') then 'ИНОСТ'  
    when pvss.omccode='И0' then 'ИНОГ' else '' end as typePatient
    , case when (select count(*) from medpolicy mp where
     mp.patient_id=pat.id and
    mp.dtype like 'MedPolicyOmc%' and mp.actualDateFrom<=hmc.dateStart and
    (mp.actualDateTo is null or mp.actualDateTo>=hmc.dateStart)
    )>0 then 'Есть' else '' end  as policy
    ,case when hmc.emergency='1' then 'Э' else 'П' end as emer
    ,sum(
    	  case 
			when (coalesce(hmc.dateFinish,CURRENT_DATE)-hmc.dateStart)=0 then 1 
			when vht.code='DAYTIMEHOSP' then ((coalesce(hmc.dateFinish,CURRENT_DATE)-hmc.dateStart)+1) 
			else (coalesce(hmc.dateFinish,CURRENT_DATE)-hmc.dateStart)
		  end
    	
    	) as sum2
    ,hmc.dateFinish as hospdateFinish
from Medcase hmc 
left join StatisticStub ss on ss.id=hmc.statisticStub_id 
left join MisLpu dep on dep.id=hmc.department_id 
left join vocservicestream vss on vss.id=hmc.servicestream_id 
left join patient pat on pat.id=hmc.patient_id 
left join vocAdditionStatus vas on vas.id=pat.additionStatus_id 
left join medcase_medpolicy pol on pol.medCase_id=hmc.id
left join medpolicy mp on mp.id=pol.policies_id
left join Omc_Oksm ok on pat.nationality_id=ok.id
left join VocSocialStatus pvss on pvss.id=pat.socialStatus_id
left join VocHospType vht on vht.id=hmc.hospType_id
left join address2 adr on adr.addressId = pat.address_addressId
 where (hmc.dateStart between to_date('${param.dateBegin}','dd.mm.yyyy') 
      and to_date('${dateEnd}','dd.mm.yyyy') ${isPat})
      and hmc.DTYPE='HospitalMedCase'
 

and hmc.deniedHospitalizating_id is null
and mp.dtype = 'MedPolicyOmcForeign'
${departmentSql}
${addPat} ${addEmergency}  ${isDischarge}
${addDuration1}
group by hmc.id, dep.name, vss.name, hmc.dateStart, ss.code 
    , vas.name , pat.id , pat.lastname,pat.firstname,pat.middlename 
    , pat.birthday ,ok.voc_code,pvss.omccode,hmc.emergency
    ,hmc.dateFinish
 
order by dep.name,vss.name,pat.lastname,pat.firstname,pat.middlename


        "  />
        <msh:table name="journal_hosp"
        viewUrl="entitySubclassShortView-mis_medCase.do" 
        action="entitySubclassView-mis_medCase.do" idField="1" noDataMessage="Не найдено">
            <msh:tableColumn columnName="#" property="sn"/>
            <msh:tableColumn columnName="Вид оплаты" property="3"/>
            <msh:tableColumn columnName="Показания" property="10"/>
            <msh:tableColumn columnName="Отделение" property="2"/>
            <msh:tableColumn columnName="№ стат. карты" property="5"/>
            <msh:tableColumn columnName="Дата пост." property="4"/>
            <msh:tableColumn columnName="Дата выписки" property="12"/>
            <msh:tableColumn columnName="К.дни" property="11"/>
            <msh:tableColumn columnName="Тип" property="8"/>
            <msh:tableColumn columnName="ФИО пациента" property="7"/>
            <msh:tableColumn columnName="Наличие страх. документов" property="9"/>
            
        </msh:table>
    </msh:sectionContent>

    </msh:section>
    
    <%} 
    if (view.equals("6") || view.equals("7")) {
    %>
    <msh:section title="Свод">
    <msh:sectionContent>
    <ecom:webQuery name="journal_list_swod" nativeSql="
    select 
    '&department='||dep.id as id
    ,dep.name as depname
    , vss.name as vssname 
    , count(*) as cnt
    , count(case when hmc.emergency='1' then 1 else null end) as cntEmer
    , count(case when hmc.emergency='1' then null else 1 end) as cntPlan
	, count(case when (ok.voc_code is not null and ok.voc_code!='643') then 1  
     else null end) as typePatientInost
	, count(case when (ok.voc_code is not null and ok.voc_code!='643') then null  
     when pvss.omccode='И0' then 1 else null end) as typePatientInog
    , count(case when (select count(*) from medpolicy mp where
     mp.patient_id=pat.id and
    mp.dtype like 'MedPolicyOmc%' and mp.actualDateFrom<=hmc.dateStart and
    (mp.actualDateTo is null or mp.actualDateTo>=hmc.dateStart)
    )>0 then 1 else null end)  as policy
    
    ,count (case when (
    	  case 
			when (coalesce(hmc.dateFinish,CURRENT_DATE)-hmc.dateStart)=0 then 1 
			when vht.code='DAYTIMEHOSP' then ((coalesce(hmc.dateFinish,CURRENT_DATE)-hmc.dateStart)+1) 
			else (coalesce(hmc.dateFinish,CURRENT_DATE)-hmc.dateStart)
		  end
    	
    	)>3 then 1 else null end) as sum3max
    ,count (case when(
    	  case 
			when (coalesce(hmc.dateFinish,CURRENT_DATE)-hmc.dateStart)=0 then 1 
			when vht.code='DAYTIMEHOSP' then ((coalesce(hmc.dateFinish,CURRENT_DATE)-hmc.dateStart)+1) 
			else (coalesce(hmc.dateFinish,CURRENT_DATE)-hmc.dateStart)
		  end
    	
    	)<=3 then 1 else null end) as sum3min
   
from Medcase hmc 
left join StatisticStub ss on ss.id=hmc.statisticStub_id 
left join MisLpu dep on dep.id=hmc.department_id 
left join vocservicestream vss on vss.id=hmc.servicestream_id 
left join patient pat on pat.id=hmc.patient_id 
left join vocAdditionStatus vas on vas.id=pat.additionStatus_id 
left join medcase_medpolicy pol on pol.medCase_id=hmc.id
left join Omc_Oksm ok on pat.nationality_id=ok.id
left join VocSocialStatus pvss on pvss.id=pat.socialStatus_id
left join VocHospType vht on vht.id=hmc.hospType_id
left join address2 adr on adr.addressId = pat.address_addressId
 where (hmc.dateStart between to_date('${param.dateBegin}','dd.mm.yyyy')
      and to_date('${dateEnd}','dd.mm.yyyy') ${isPat})
      and hmc.DTYPE='HospitalMedCase'
      ${departmentSql}
 and (vss.code = 'OBLIGATORYINSURANCE') 

and hmc.deniedHospitalizating_id is null
and pol.medCase_id is null
${addPat}  ${isDischarge}
group by dep.id,dep.name, vss.name

order by dep.name,vss.name
    
    "/>
    <msh:table name="journal_list_swod"
         action="stac_receivedWithoutPolicy_list.do?dateBegin=${param.dateBegin}&dateEnd=${param.dateEnd}&typeDuration=${typeDuration}&typePatient=${typePatient}&typePatientIs=${typePatientIs}&typeEmergency=${typeEmergency}&typeDischargePatientIs=${typeDischargePatientIs}&typeView=1" idField="1" noDataMessage="Не найдено">
            <msh:tableColumn columnName="#" property="sn"/>
            <msh:tableColumn columnName="Отделение" property="2"/>
            <msh:tableColumn columnName="Поток обслуживания" property="3"/>
            <msh:tableColumn columnName="Кол-во" property="4" isCalcAmount="true"/>
            <msh:tableColumn columnName="Кол-во экстренных" property="5" isCalcAmount="true"/>
            <msh:tableColumn columnName="Кол-во плановых" property="6" isCalcAmount="true"/>
            <msh:tableColumn columnName="Кол-во иностр. гражд." property="7" isCalcAmount="true"/>
            <msh:tableColumn columnName="Кол-во иног. гражд." property="8" isCalcAmount="true"/>
            <msh:tableColumn columnName="Налич. страх. докум." property="9" isCalcAmount="true"/>
            <msh:tableColumn columnName="Более 3х к/ дней" property="10" isCalcAmount="true"/>
            <msh:tableColumn columnName="Менее 3х к/ дней" property="11" isCalcAmount="true"/>
            
        </msh:table>
    </msh:sectionContent>
    </msh:section>
    <%} 
	} else {%>
    	<i>Выберите параметры поиска и нажмите "Найти" </i>
    	<% }   %>

    <script type='text/javascript'>
    //checkFieldUpdate('typeDate','${typeDate}',2) ;
    checkFieldUpdate('typeView','${typeView}',6) ;
    checkFieldUpdate('typeDuration','${typeDuration}',3) ;
    checkFieldUpdate('typePatient','${typePatient}',3) ;
    checkFieldUpdate('typePatientIs','${typePatientIs}',2) ;
    //checkFieldUpdate('typeOperation','${typeOperation}',3) ;
    checkFieldUpdate('typeEmergency','${typeEmergency}',3) ;
    checkFieldUpdate('typeDischargePatientIs','${typeDischargePatientIs}',3) ;
    
  
   function checkFieldUpdate(aField,aValue,aDefault) {
   	eval('var chk =  document.forms[0].'+aField) ;
   	eval('var aMax =  chk.length') ;
   	if (aMax>aDefault) {aDefault=aMax}
   	if ((+aValue)>aMax) {
   		chk[+aDefault-1].checked='checked' ;
   	} else {
   		chk[+aValue-1].checked='checked' ;
   	}
   }    function find() {
    	var frm = document.forms[0] ;
    	frm.target='' ;
    	frm.action='stac_receivedWithoutPolicy_list.do' ;
    }
    function print() {
    	var frm = document.forms[0] ;
    	frm.target='_blank' ;
    	frm.action='print-stac_receivedWithoutPolicy.do' ;
    }
    
    </script>
  </tiles:put>
</tiles:insert>

