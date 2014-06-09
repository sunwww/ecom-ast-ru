<%@page import="ru.nuzmsh.util.query.ReportParamUtil"%>
<%@page import="ru.ecom.mis.web.action.util.ActionUtil"%>
<%@page import="java.text.SimpleDateFormat"%>
<%@page import="java.util.Calendar"%>
<%@page import="ru.nuzmsh.util.format.DateFormat"%>
<%@page import="java.util.Date"%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://struts.apache.org/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://www.nuzmsh.ru/tags/msh" prefix="msh" %>
<%@ taglib uri="http://www.ecom-ast.ru/tags/ecom" prefix="ecom" %>
<%@ taglib tagdir="/WEB-INF/tags" prefix="tags" %>

<tiles:insert page="/WEB-INF/tiles/mainLayout.jsp" flush="true" >

  <tiles:put name="title" type="string">
    <msh:title guid="helloItle-123" mainMenu="StacJournal">Ежедневный отчет по стационару</msh:title>
  </tiles:put>
  <tiles:put name="side" type="string">
  	<tags:stac_journal currentAction="stac_everyday"/>
  </tiles:put>
  <tiles:put name="body" type="string">
    <%
  	String noViewForm = request.getParameter("noViewForm") ;

	String typeDate =ActionUtil.updateParameter("ReestrByHospitalMedCase","typeDate","1", request) ;
	String typeEmergency =ActionUtil.updateParameter("ReestrByHospitalMedCase","typeEmergency","3", request) ;
	String typeHour =ActionUtil.updateParameter("ReestrByHospitalMedCase","typeHour","4", request) ;
	String typeView =ActionUtil.updateParameter("ReestrByHospitalMedCase","typeView","1", request) ;
  	%>
    <msh:form action="/stac_everyday_report.do" defaultField="dateBegin" disableFormDataConfirm="true" method="GET" guid="d7b31bc2-38f0-42cc-8d6d-19395273168f">
    <input type="hidden" name="s" id="s" value="HospitalPrintService" />
    <input type="hidden" name="m" id="m" value="printReestrByDay" />
    <input type="hidden" name="id" id="id" value=""/>
        <input type='hidden' id="sqlText1" name="sqlText1">
        <input type='hidden' id="sqlText2" name="sqlText2">
        <input type='hidden' id="infoText1" name="infoText1">
        <input type='hidden' id="infoText2" name="infoText2">
      <msh:panel guid="6ae283c8-7035-450a-8eb4-6f0f7da8a8ff">
      <msh:row guid="53627d05-8914-48a0-b2ec-792eba5b07d9">
        <msh:separator label="Параметры поиска" colSpan="7" guid="15c6c628-8aab-4c82-b3d8-ac77b7b3f700" />
      </msh:row>
      <msh:row guid="7d80be13-710c-46b8-8503-ce0413686b69">
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
      <msh:row guid="7d80be13-710c-46b8-8503-ce0413686b69">
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
      <msh:row>
        <td class="label" title="Начало суток (typeeHour)" colspan="1"><label for="typeHourName" id="typeHourLabel">Начало суток:</label></td>
        <td onclick="this.childNodes[1].checked='checked';">
        	<input type="radio" name="typeHour" value="1">  7 часов
        </td>
        <td onclick="this.childNodes[1].checked='checked';">
        	<input type="radio" name="typeHour" value="2">  8 часов
        </td>
        <td onclick="this.childNodes[1].checked='checked';">
        	<input type="radio" name="typeHour" value="3" >  9 часов
        </td>
        <td onclick="this.childNodes[1].checked='checked';">
        	<input type="radio" name="typeHour" value="4">  календар. день
        </td>
      </msh:row>
      <msh:row guid="7d80be13-710c-46b8-8503-ce0413686b69">
        <td class="label" title="Поиск по дате  (typeDate)" colspan="1"><label for="typeDateName" id="typeDateLabel">Искать по дате:</label></td>
        <td onclick="this.childNodes[1].checked='checked';">
        	<input type="radio" name="typeDate" value="1">  поступления в стац.
        </td>
        <td onclick="this.childNodes[1].checked='checked';" colspan="1">
        	<input type="radio" name="typeDate" value="2">  выписки
        </td>
        <td onclick="this.childNodes[1].checked='checked';" colspan="3">
        	<input type="radio" name="typeDate" value="3">  состоящие
        </td>
        </msh:row>
        <msh:row>
	        <td class="label" title="Отделение (typeDepartment)" colspan="1"><label for="typeDepartmentName" id="typeDepartmentLabel">Отделение:</label></td>
	        <td onclick="this.childNodes[1].checked='checked';">
	        	<input type="radio" name="typeDepartment" value="1">  госпитализации
	        </td>
	        <td onclick="this.childNodes[1].checked='checked';">
	        	<input type="radio" name="typeDepartment" value="2"  >  по СЛО
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
        </msh:row>
        <msh:row guid="Дата">
        <msh:textField fieldColSpan="2" property="dateBegin" label="Дата" guid="8d7ef035-1273-4839-a4d8-1551c623caf1" />
      </msh:row>
      <msh:row>
        <msh:autoComplete property="serviceStream" fieldColSpan="4"
        	label="Поток обслуживания" horizontalFill="true" vocName="vocServiceStream"/>
      </msh:row>
      <msh:row>
        <msh:autoComplete property="department" fieldColSpan="4" horizontalFill="true" label="Отделение" vocName="lpu"/>
      </msh:row>
      <msh:row>
           <td colspan="11">
            <input type="submit" onclick="find()" value="Найти" />

          </td>
      </msh:row>
    </msh:panel>
    </msh:form>
       <script type='text/javascript'>
    
    checkFieldUpdate('typeDate','${typeDate}',1) ;
    checkFieldUpdate('typePatient','${typePatient}',4) ;
    checkFieldUpdate('typeEmergency','${typeEmergency}',3) ;
    checkFieldUpdate('typeView','${typeView}',1) ;
    checkFieldUpdate('typeHour','${typeHour}',3) ;
    checkFieldUpdate('typeDepartment','${typeDepartment}',1) ;
    
  
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
    <%
    String date = request.getParameter("dateBegin") ;
    
    if (date!=null && !date.equals(""))  {
    	String view = (String)request.getAttribute("typeView") ;
    	//String fldDepartment = "ml.id" ;
    	
    	String pigeonHole="" ;
    	String pigeonHole1="" ;
    	String pHole = request.getParameter("pigeonHole") ;
    	if (pHole!=null && !pHole.equals("") && !pHole.equals("0")) {
    		pigeonHole1= " and (ml.pigeonHole_id='"+pHole+"' or ml1.pigeonHole_id='"+pHole+"')" ;
    		pigeonHole= " and ml.pigeonHole_id='"+pHole+"'" ;
    	}
    	request.setAttribute("pigeonHole", pigeonHole) ;
    	request.setAttribute("pigeonHole1", pigeonHole1) ;
    	
    	String department="" ;
    	String dep = request.getParameter("department") ;
    	if (dep!=null && !dep.equals("") && !dep.equals("0")) {
    		request.setAttribute("department", " and "+request.getAttribute("departmentFldIdSql")+"='"+dep+"'") ;
    	}
    	ActionUtil.setParameterFilterSql("serviceStream", "m.serviceStream_id", request);
    	//ActionUtil.setParameterFilterSql("pigeonHole", "m.department_id", request);
    	//ActionUtil.setParameterFilterSql("pigeonHole","pigeonHole1", "m.department_id", request);
    	
    	if (view!=null && (view.equals("1"))) {
    	%>
    
    <msh:section>
    <msh:sectionTitle>Реестр ${dateInfo} за день ${param.dateBegin}. 
    По ${dischInfo}  ${emerInfo} ${hourInfo} ${infoTypePat}
    </msh:sectionTitle>
    <msh:sectionContent>
    <ecom:webQuery name="journal_priem" nameFldSql="journal_priem_sql" nativeSql=" 
   select vph.id,vph.name
,count(*) as cntAll
,count(distinct case when pat.newborn_id is not null then m.id else null end) as cntNewBorn
,count(distinct case when (current_date-pat.birthday)<(17*355) then m.id else null end) as cntChild
,count(distinct case when adr.addressisvillage='1' then m.id else null end) as cntVill
,count(distinct case when adr.addressiscity='1' then m.id else null end) as cntCity
,count(distinct case when pvss.omccode='И0' or adr.kladr is not null and adr.kladr not like '30%' then m.id else null end) as cntInog
,count(distinct case when ok.voc_code is not null and ok.voc_code!='643' then m.id else null end) as cntInost
from medcase m
left join Patient pat on pat.id=m.patient_id
left join Address2 adr on adr.addressid=pat.address_addressid
left join Mislpu ml on m.department_id=ml.id
left join VocPigeonHole vph on vph.id=ml.pigeonHole_id
left join Omc_Oksm ok on pat.nationality_id=ok.id
where m.dtype='HospitalMedCase' 
and m.datefinish is null 
and m.deniedHospitalizating_id is null
${period}
${emerIs} ${pigeonHole} 
${department} ${serviceStreamSql} ${addPat} ${departmentFldAddSql}
group by vph.id,vph.name
order by vph.name
      " guid="4a720225-8d94-4b47-bef3-4dbbe79eec74" />
    <msh:table name="journal_priem" viewUrl="entityShortView-stac_ssl.do" action="entityParentView-stac_ssl.do" idField="1" guid="b621e361-1e0b-4ebd-9f58-b7d919b45bd6">
      <msh:tableColumn columnName="#" property="sn" guid="34a9f56ab-a3fa-5c1afdf6c41d" />
      <msh:tableColumn columnName="Стат.карта" property="7" guid="34a9f56a-2b47-4feb-a3fa-5c1afdf6c41d" />
      <msh:tableColumn columnName="Фамилия имя отчество пациента" property="5" guid="34a9f56a-2b47-4feb-a3fa-5c1afdf6c41d" />
      <msh:tableColumn columnName="Дата поступления" property="2" guid="3cf775aa-e94d-4393-a489-b83b2be02d60" />
      <msh:tableColumn columnName="Отделение" property="9" guid="e29229e1-d243-47d6-a5c7-997df74eaf73" />
      <msh:tableColumn columnName="Дата выписки" property="3" guid="d9642df9-5653-4920-bb78-1622cbeefa34" />
      <msh:tableColumn columnName="Пациент" property="10" guid="d9642df9-5653-4920-bb78-1622cbeefa34" />
    </msh:table>
    </msh:sectionContent>
    </msh:section>
    <% if (request.getAttribute("period1")!=null) { %>
    <msh:section>
    <msh:sectionTitle>Реестр отказов от госпитализаций за день ${param.dateBegin}
    . По ${emerInfo} ${hourInfo} ${infoTypePat}</msh:sectionTitle>
    <msh:sectionContent>
    <ecom:webQuery nameFldSql="journal_priem_denied_sql" name="journal_priem"  nativeSql="
    select 
    m.id as mid
    ,to_char(m.dateStart,'dd.mm.yyyy')||' '||cast(m.entranceTime as varchar(5)) as mdateStart
    ,pat.lastname ||' ' ||pat.firstname|| ' ' || pat.middlename||' гр '||to_char(pat.birthday,'dd.mm.yyyy') as fio
    ,sc.code as sccode,m.emergency as memergency
    ,vdh.name as vdhname
    , case when (ok.voc_code is not null and ok.voc_code!='643') then 'ИНОСТ'  
    when (pvss.omccode='И0' or adr.kladr is not null and adr.kladr not like '30%') then 'ИНОГ' else '' end as typePatient
    
     from MedCase as m  
     left join StatisticStub as sc on sc.medCase_id=m.id 
     left outer join Patient pat on m.patient_id = pat.id 
     left join VocDeniedHospitalizating as vdh on vdh.id=m.deniedHospitalizating_id
     left join Omc_Oksm ok on pat.nationality_id=ok.id
	 left join VocSocialStatus pvss on pvss.id=pat.socialStatus_id
	 left join address2 adr on adr.addressId = pat.address_addressId  

	left join MisLpu as ml on ml.id=m.department_id 

	 left join SecUser su on su.login=m.username
	left join WorkFunction wf on wf.secUser_id=su.id
	left join Worker w on w.id=wf.worker_id
	left join MisLpu ml1 on ml1.id=w.lpu_id 
     ${period}
      and m.deniedHospitalizating_id is not null
      ${emerIs} ${pigeonHole1} ${department} ${serviceStreamSql}
      ${addPat}
       order by m.${dateI},pat.lastname,pat.firstname,pat.middlename
      " guid="4a720225-8d94-4b47-bef3-4dbbe79eec74" />
    <msh:table name="journal_priem"  viewUrl="entityShortView-stac_ssl.do" action="entityParentView-stac_ssl.do" idField="1" guid="b621e361-1e0b-4ebd-9f58-b7d919b45bd6">
      <msh:tableColumn columnName="#" property="sn" guid="34a9f56ab-a3fa-5c1afdf6c41d" />
      <msh:tableColumn columnName="Стат.карта" property="4" guid="34a9f56a-2b47-4feb-a3fa-5c1afdf6c41d" />
      <msh:tableColumn columnName="Фамилия имя отчество пациента" property="3" guid="34a9f56a-2b47-4feb-a3fa-5c1afdf6c41d" />
      <msh:tableColumn columnName="Дата обращения" property="2" guid="3cf775aa-e94d-4393-a489-b83b2be02d60" />
      <msh:tableColumn columnName="Экстрено" property="5" guid="3cf775aa-e94d-4393-a489-b83b2be02d60" />
      <msh:tableColumn columnName="Причина отказа" property="6" guid="e29229e1-d243-47d6-a5c7-997df74eaf73" />
      <msh:tableColumn columnName="Пациент" property="7"/>
    </msh:table>
    </msh:sectionContent>
    </msh:section>    <% }
    	
    	
    	}  else {
    		
    		// СВОД
    		
    		%>
    		<%--
    		
    		Поступившие пациенты в стационар
    		
    		select  
    vph.id,vph.name
, count(distinct m.id) as all1
,count(distinct case when m.deniedHospitalizating_id is null then m.id else null end) as obr
,count(distinct case when m.deniedHospitalizating_id is null 
and (oo.voc_code='643' or oo.id is null) and substring(a.kladr,1,2)='30' and a.addressIsVillage='1'
then m.id else null end) as obrVil
,count(distinct case when m.deniedHospitalizating_id is null 
and (oo.voc_code='643' or oo.id is null) and substring(a.kladr,1,2)='30' and a.addressIsCity='1'
then m.id else null end) as obrCity
,count(distinct case when m.deniedHospitalizating_id is null 
and (oo.voc_code='643' or oo.id is null) and a.addressid is not null and substring(a.kladr,1,2)!='30'
then m.id else null end) as obrInog
,count(distinct case when m.deniedHospitalizating_id is null 
and oo.voc_code!='643'  then m.id else null end) as obrInost
,count(distinct case when m.deniedHospitalizating_id is null 
and (oo.voc_code='643' or oo.id is null) and (a.addressid is null or a.domen<3)  then m.id else null end) as obrOther


,count(distinct case when m.emergency='1' and m.deniedHospitalizating_id is null then m.id else null end) as em
,count(distinct case when m.emergency='1' and m.deniedHospitalizating_id is null and vof.voc_code='О' then m.id else null end) as emSam
,count(distinct case when m.emergency='1' and m.deniedHospitalizating_id is null and vof.voc_code='К' then m.id else null end) as emSkor
,count(distinct case when (m.emergency is null or m.emergency='0') and m.deniedHospitalizating_id is null then m.id else null end) as pl

, count(distinct case when m.deniedHospitalizating_id is not null then m.id else null end) as denied 
from medcase m 
left join MisLpu as ml on ml.id=m.department_id
left join VocPigeonHole vph on vph.id=ml.pigeonHole_id
left join Patient p on p.id=m.patient_id
left join Address2 a on p.address_addressid=a.addressid
left join Omc_Oksm oo on oo.id=p.nationality_id 
left join Omc_Frm vof on vof.id=m.orderType_id
where m.dtype='HospitalMedCase' and m.dateStart 
=to_date('07.03.2014','dd.mm.yyyy')  
and ( m.noActuality is null or m.noActuality='0')
group by vph.id,vph.name



Выбывшие из стационара

select  
    vph.id,vph.name
, count(distinct m.id) as cntAll
,count(distinct case when vho.code!='1' then m.id else null end) as cntDischargeOtherLpu
,count(distinct case when vhr.code='11' then m.id else null end) as cntDeathPatient
,list(distinct case when vhr.code='11' then pat.lastname||' '||pat.firstname||' '||coalesce(pat.middlename,'')||' г.р.'||to_char(pat.birthday,'dd.mm.yyyy') else null end) as listDeathPatient
from medcase m 
left join Patient pat on pat.id=m.patient_id
left join MisLpu as ml on ml.id=m.department_id
left join VocPigeonHole vph on vph.id=ml.pigeonHole_id
left join Omc_Frm vof on vof.id=m.orderType_id
left join VocHospitalizationOutcome vho on vho.id=m.outcome_id
left join VocHospitalizationResult vhr on vhr.id=m.result_id
where m.dtype='HospitalMedCase' and m.dateFinish 
=to_date('07.03.2014','dd.mm.yyyy')  
and ( m.noActuality is null or m.noActuality='0')
group by vph.id,vph.name
    		 --%>
    		    <msh:section>
    <msh:sectionTitle>Реестр ${dateInfo} за день ${param.dateBegin}. По ${dischInfo}  ${emerInfo} ${hourInfo}
    </msh:sectionTitle>
    <msh:sectionContent>
    <ecom:webQuery name="journal_priem" nameFldSql="journal_priem_sql" nativeSql="select 
    
    ${departmentFldIdSql} as mlid,${departmentFldNameSql} as mlname
    ,count(distinct m.id)
  	,count(distinct case when m.emergency='1' then m.id else null end) as memergency
  	,count(distinct case when (m.emergency='0' or m.emergency is null) then m.id else null end) as mplan
    ,count(distinct case when (ok.voc_code is not null and ok.voc_code!='643') then m.id else null end) as inostr  
    ,count(distinct case when (pvss.omccode='И0' or adr.kladr is not null and adr.kladr not like '30%') then m.id else null end) as inog
    
     from MedCase as m  
     left join StatisticStub as sc on sc.medCase_id=m.id 
     left outer join Patient pat on m.patient_id = pat.id 
     left join MisLpu as ml on ml.id=m.department_id 
     left join Omc_Oksm ok on pat.nationality_id=ok.id
	 left join VocSocialStatus pvss on pvss.id=pat.socialStatus_id
	 left join address2 adr on adr.addressId = pat.address_addressId
     
     left join MedCase slo on slo.parent_id=m.id
     left join MisLpu as sloml on sloml.id=slo.department_id
     where m.DTYPE='HospitalMedCase' ${period} ${department} 
     and m.deniedHospitalizating_id is null
     ${emerIs} ${pigeonHole} ${serviceStreamSql} ${addPat} ${departmentFldAddSql}
     group by ${departmentFldIdSql},${departmentFldNameSql}
        order by ${departmentFldIdSql},${departmentFldNameSql}
      " guid="4a720225-8d94-4b47-bef3-4dbbe79eec74" />
    <msh:table name="journal_priem" action="stac_reestrByHospital.do" idField="1" guid="b621e361-1e0b-4ebd-9f58-b7d919b45bd6">
      <msh:tableColumn columnName="#" property="sn" guid="34a9f56ab-a3fa-5c1afdf6c41d" />
      <msh:tableColumn columnName="Отделение" property="2" guid="34a9f56a-2b47-4feb-a3fa-5c1afdf6c41d" />
      <msh:tableColumn columnName="Кол-во" isCalcAmount="true" property="3" guid="d9642df9-5653-4920-bb78-1622cbeefa34" />
      <msh:tableColumn columnName="Кол-во экстренных" isCalcAmount="true" property="4" guid="34a9f56a-2b47-4feb-a3fa-5c1afdf6c41d" />
      <msh:tableColumn columnName="Кол-во плановых" isCalcAmount="true" property="5" guid="34a9f56a-2b47-4feb-a3fa-5c1afdf6c41d" />
      <msh:tableColumn columnName="Кол-во иностранцев" isCalcAmount="true" property="6" guid="3cf775aa-e94d-4393-a489-b83b2be02d60" />
      <msh:tableColumn columnName="Кол-во иногородних" isCalcAmount="true" property="7" guid="e29229e1-d243-47d6-a5c7-997df74eaf73" />
    </msh:table>
    </msh:sectionContent>
    </msh:section>
    <% if (request.getAttribute("period1")!=null) { %>
    <msh:section>
    <msh:sectionTitle>Свод отказов от госпитализаций за день ${param.dateBegin}. По ${emerInfo} ${hourInfo}</msh:sectionTitle>
    <msh:sectionContent>
    <ecom:webQuery name="journal_priem" nameFldSql="journal_priem_denied_sql" nativeSql="select 
    vdh.name as vdhname
    ,count(distinct m.id)
  	,count(distinct case when m.emergency='1' then m.id else null end) as memergency
    ,count(distinct case when (ok.voc_code is not null and ok.voc_code!='643') then m.id else null end) as inostr  
    ,count(distinct case when (pvss.omccode='И0' or adr.kladr is not null and adr.kladr not like '30%') then m.id else null end) as inog
     from MedCase as m  
     left join StatisticStub as sc on sc.medCase_id=m.id 
     left outer join Patient pat on m.patient_id = pat.id 
     left join VocDeniedHospitalizating as vdh on vdh.id=m.deniedHospitalizating_id
     left join Omc_Oksm ok on pat.nationality_id=ok.id
	 left join VocSocialStatus pvss on pvss.id=pat.socialStatus_id
	 left join address2 adr on adr.addressId = pat.address_addressId  
	 left join SecUser su on su.login=m.username
	left join WorkFunction wf on wf.secUser_id=su.id
	left join MisLpu as ml on ml.id=m.department_id 
	left join Worker w on w.id=wf.worker_id
	left join MisLpu ml1 on ml1.id=w.lpu_id 
     where m.DTYPE='HospitalMedCase' ${period}
      and m.deniedHospitalizating_id is not null
      ${emerIs} ${pigeonHole1} group by vdh.name order by vdh.name
      " guid="4a720225-8d94-4b47-bef3-4dbbe79eec74" />
    <msh:table name="journal_priem" action="stac_reestrByHospital.do" idField="1" guid="b621e361-1e0b-4ebd-9f58-b7d919b45bd6">
      <msh:tableColumn columnName="#" property="sn" guid="34a9f56ab-a3fa-5c1afdf6c41d" />
      <msh:tableColumn columnName="Причина отказа" property="1" guid="34a9f56a-2b47-4feb-a3fa-5c1afdf6c41d" />
      <msh:tableColumn isCalcAmount="true" columnName="Кол-во" property="2" guid="d9642df9-5653-4920-bb78-1622cbeefa34" />
      <msh:tableColumn isCalcAmount="true" columnName="Кол-во экстренных" property="3" guid="34a9f56a-2b47-4feb-a3fa-5c1afdf6c41d" />
      <msh:tableColumn isCalcAmount="true" columnName="Кол-во иностранцев" property="4" guid="3cf775aa-e94d-4393-a489-b83b2be02d60" />
      <msh:tableColumn isCalcAmount="true" columnName="Кол-во иногородних" property="5" guid="e29229e1-d243-47d6-a5c7-997df74eaf73" />
    </msh:table>
    </msh:sectionContent>
    </msh:section>
    		<%
    	}
    	}} else {%>
    	<i>Нет данных </i>
    	<% }   %>
    
		<script type="text/javascript">
	    function find() {
	    	var frm = document.forms[0] ;
	    	frm.target='' ;
	    	frm.action='stac_everyday_report.do' ;
	    }
	    function print(aFile) {
	    	var frm = document.forms[0] ;
	    	frm.m.value="printReestrByDay" ;
	    	frm.s.value="HospitalPrintService" ;
	    	//frm.sqlText1.value = "${journal_priem_sql}" ;
	    	//frm.sqlText2.value = "${journal_priem_denied_sql}" ;
	    	frm.target='_blank' ;
	    	frm.action='print-'+aFile+'.do' ;
	    	$('id').value = getCheckedRadio(frm,"typeEmergency")+":"
	    		+getCheckedRadio(frm,"typeHour")+":"
	    		+getCheckedRadio(frm,"typeDate")+":"
	    		+getCheckedRadio(frm,"typePatient")+":"
	    		+getCheckedRadio(frm,"typeDepartment")+":"
	    		+$('dateBegin').value+":"
	    		+$('pigeonHole').value+":"
	    		+$('department').value+":"
	    		+$('serviceStream').value;
	    }
		</script>
  </tiles:put>
</tiles:insert>

