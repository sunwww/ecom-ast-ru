<%@page import="ru.ecom.web.util.ActionUtil"%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://struts.apache.org/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://www.nuzmsh.ru/tags/msh" prefix="msh" %>
<%@ taglib uri="http://www.ecom-ast.ru/tags/ecom" prefix="ecom" %>
<%@ taglib tagdir="/WEB-INF/tags" prefix="tags" %>

<tiles:insert page="/WEB-INF/tiles/mainLayout.jsp" flush="true" >

  <tiles:put name="title" type="string">
    <msh:title mainMenu="StacJournal">Реестр по пациентам поступившим/ выбывшим из стационара</msh:title>
  </tiles:put>
  <tiles:put name="side" type="string">
  	<tags:stac_journal currentAction="stac_reestrByHospital"/>
  </tiles:put>
  <tiles:put name="body" type="string">
    <%
  	String noViewForm = request.getParameter("noViewForm") ;

	String typeDate =ActionUtil.updateParameter("ReestrByHospitalMedCase","typeDate","1", request) ;
	String typeEmergency =ActionUtil.updateParameter("ReestrByHospitalMedCase","typeEmergency","3", request) ;
	String typeHour =ActionUtil.updateParameter("ReestrByHospitalMedCase","typeHour","4", request) ;
	String typeView =ActionUtil.updateParameter("ReestrByHospitalMedCase","typeView","1", request) ;
	String typeFormat =ActionUtil.updateParameter("ReestrByHospitalMedCase","typeFormat","2", request) ;
	String typeNumber =ActionUtil.updateParameter("ReestrByHospitalMedCase","typeNumber","3", request) ;
  	%>
    <msh:form action="/stac_reestrByHospital.do" defaultField="dateBegin" disableFormDataConfirm="true" method="GET">
    <input type="hidden" name="s" id="s" value="HospitalPrintService" />
    <input type="hidden" name="m" id="m" value="printReestrByDay" />
    <input type="hidden" name="id" id="id" value=""/>
        <input type='hidden' id="sqlText1" name="sqlText1">
        <input type='hidden' id="sqlText2" name="sqlText2">
        <input type='hidden' id="infoText1" name="infoText1">
        <input type='hidden' id="infoText2" name="infoText2">
      <msh:panel>
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
      <msh:row>
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
        <msh:row>
        <msh:textField fieldColSpan="2" property="dateBegin" label="Дата" />
      </msh:row>
      <msh:row>
        <msh:autoComplete property="serviceStream" fieldColSpan="4"
        	label="Поток обслуживания" horizontalFill="true" vocName="vocServiceStream"/>
      </msh:row>
      <msh:row>
        <msh:autoComplete property="department" fieldColSpan="4" horizontalFill="true" label="Отделение" vocName="vocLpuHospOtdAll"/>
      </msh:row>
      <msh:row>
           <td colspan="11">
            <input type="submit" onclick="find()" value="Найти" />
            <msh:ifInRole roles="/Policy/Mis/MedCase/Stac/Journal/ShowInfoAllDepartments">
            	<input type="submit" onclick="print('stac_reestrForDay1')" value="Печать реестра" />
            </msh:ifInRole>
            <msh:ifNotInRole roles="/Policy/Mis/MedCase/Stac/Journal/ShowInfoAllDepartments">
            	<input type="submit" onclick="print('stac_reestrForDay')" value="Печать" />
            </msh:ifNotInRole>
            
            <input type="button" onclick="viewJournal()" value="Отобразить печать журнала госп. и отказов" />
            </td>
      </msh:row>
      <msh:row styleId="journal1">
      	<msh:textField property="numberInJournal" label="Начать с поряд. номера"/>
      </msh:row>
      <msh:row styleId="journal2">
        <td class="label" title="Кол-во страниц в ширину (typeFormat)" colspan="1"><label for="typeFormatName" id="typeFormatLabel">Кол-во стр. в ширину:</label></td>
        <td onclick="this.childNodes[1].checked='checked';">
        	<input type="radio" name="typeFormat" value="1">  одна страница
        </td>
        <td onclick="this.childNodes[1].checked='checked';">
        	<input type="radio" name="typeFormat" value="2" >  две страницы
        </td>
      </msh:row>
      <msh:row styleId="journal3">
        <td class="label" title="Печатать пункты (typeNumber)" colspan="1"><label for="typeNumberName" id="typeNumberLabel">Пункты в журнале:</label></td>
        <td onclick="this.childNodes[1].checked='checked';">
        	<input type="radio" name="typeNumber" value="1">  четные
        </td>
        <td onclick="this.childNodes[1].checked='checked';">
        	<input type="radio" name="typeNumber" value="2" >  нечетные
        </td>
        <td onclick="this.childNodes[1].checked='checked';">
        	<input type="radio" name="typeNumber" value="3" >  по порядку
        </td>
      </msh:row>
      <msh:row styleId="journal4">
	      <td colspan="11">
    	  	<input type="submit" onclick="printJournal()" value="Печать журнала госпитализаций и отказов от госпитализаций" />
          </td>
      </msh:row>
    </msh:panel>
    </msh:form>
       <script type='text/javascript'>
    var viewPrintJournal=false ;
    checkFieldUpdate('typeDate','${typeDate}',1) ;
    checkFieldUpdate('typePatient','${typePatient}',4) ;
    checkFieldUpdate('typeEmergency','${typeEmergency}',3) ;
    checkFieldUpdate('typeView','${typeView}',1) ;
    checkFieldUpdate('typeHour','${typeHour}',3) ;
    checkFieldUpdate('typeDepartment','${typeDepartment}',1) ;
    checkFieldUpdate('typeFormat','${typeFormat}',1) ;
    checkFieldUpdate('typeNumber','${typeNumber}',1) ;
    
  
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
    	
    	String dep = request.getParameter("department") ;
    	if (dep!=null && !dep.equals("") && !dep.equals("0")) {
    		request.setAttribute("department", " and "+request.getAttribute("departmentFldIdSql")+"='"+dep+"'") ;
    	}
    	String serviceStream = request.getParameter("serviceStream") ;
    	if (serviceStream!=null && !serviceStream.equals("") && !serviceStream.equals("0")) {
    		request.setAttribute("serviceStreamSql", " and m.serviceStream_id='"+serviceStream+"' " ) ;
    	}
    	
    	if (view!=null && (view.equals("1"))) {
    	%>
    
    <msh:section>
    <msh:sectionTitle>Реестр ${dateInfo} за день ${param.dateBegin}. 
    По ${dischInfo}  ${emerInfo} ${hourInfo} ${infoTypePat}
    </msh:sectionTitle>
    <msh:sectionContent>
    <ecom:webQuery name="journal_priem" nameFldSql="journal_priem_sql" nativeSql="select 
    m.id as mid
    ,to_char(m.dateStart,'dd.mm.yyyy')||' '||cast(m.entranceTime as varchar(5)) as mdateStart
    ,to_char(m.dateFinish,'dd.mm.yyyy')||' '||cast(m.dischargeTime as varchar(5)) as mdateFinish
    ,cast(m.dischargeTime as varchar(5)) as mdischargeTime
    ,pat.lastname ||' ' ||pat.firstname|| ' ' || pat.middlename||' гр '||to_char(pat.birthday,'dd.mm.yyyy') as fio
    ,to_char(pat.birthday,'dd.mm.yyyy') as birthday,sc.code as sccode,m.emergency as memergency
    ,${departmentFldNameSql} as mlname
    , case when (ok.voc_code is not null and ok.voc_code!='643') then 'ИНОСТ'  
    when (pvss.omccode='И0' or adr.kladr is not null and adr.kladr not like '30%') then 'ИНОГ' else '' end as typePatient
    
     from MedCase as m  
     left join StatisticStub as sc on sc.medCase_id=m.id 
     left outer join Patient pat on m.patient_id = pat.id 
     left join MisLpu as ml on ml.id=m.department_id 
     left join Omc_Oksm ok on pat.nationality_id=ok.id
	 left join VocSocialStatus pvss on pvss.id=pat.socialStatus_id
	 left join address2 adr on adr.addressId = pat.address_addressId
     left join MedCase slo on slo.parent_id=m.id
     left join MisLpu as sloml on sloml.id=slo.department_id
     where m.DTYPE='HospitalMedCase' ${period}
     and m.deniedHospitalizating_id is null
       ${emerIs} ${pigeonHole} 
       
       ${department} ${serviceStreamSql} ${addPat} ${departmentFldAddSql}
       group by m.id,m.dateStart,m.entranceTime,m.dateFinish,m.dischargeTime
		    ,m.dischargeTime,pat.lastname,pat.firstname,pat.middlename
		    ,pat.birthday,sc.code,m.emergency,${departmentFldNameSql}
		    ,ok.voc_code,pvss.omccode,adr.kladr
		
       order by m.${dateI},${departmentFldNameSql},pat.lastname,pat.firstname,pat.middlename
      " />
    <msh:table name="journal_priem" viewUrl="entityShortView-stac_ssl.do" action="entityParentView-stac_ssl.do" idField="1">
      <msh:tableColumn columnName="#" property="sn" />
      <msh:tableColumn columnName="Стат.карта" property="7" />
      <msh:tableColumn columnName="Фамилия имя отчество пациента" property="5" />
      <msh:tableColumn columnName="Дата поступления" property="2" />
      <msh:tableColumn columnName="Отделение" property="9" />
      <msh:tableColumn columnName="Дата выписки" property="3" />
      <msh:tableColumn columnName="Пациент" property="10" />
    </msh:table>
    </msh:sectionContent>
    </msh:section>
    <% if (request.getAttribute("period1")!=null) { %>
    <msh:section>
    <msh:sectionTitle>Реестр отказов от госпитализаций за день ${param.dateBegin}
    . По ${emerInfo} ${hourInfo} ${infoTypePat}</msh:sectionTitle>
    <msh:sectionContent>
    <ecom:webQuery nameFldSql="journal_priem_denied_sql" name="journal_priem"  nativeSql="select
    m.id as mid
    ,to_char(m.dateStart,'dd.mm.yyyy')||' '||cast(m.entranceTime as varchar(5)) as mdateStart
    ,pat.lastname ||' ' ||pat.firstname|| ' ' || pat.middlename||' гр '||to_char(pat.birthday,'dd.mm.yyyy') as fio
    ,sc.code as sccode,m.emergency as memergency
    ,vdh.name as vdhname
    , case when (ok.voc_code is not null and ok.voc_code!='643') then 'ИНОСТ'  
    when (pvss.omccode='И0' or adr.kladr is not null and adr.kladr not like '30%') then 'ИНОГ' else '' end as typePatient
    , ml.name as f8_depName
     from MedCase as m
     left join StatisticStub as sc on sc.medCase_id=m.id 
     left outer join Patient pat on m.patient_id = pat.id 
     left join VocDeniedHospitalizating as vdh on vdh.id=m.deniedHospitalizating_id
     left join Omc_Oksm ok on pat.nationality_id=ok.id
	 left join VocSocialStatus pvss on pvss.id=pat.socialStatus_id
	 left join address2 adr on adr.addressId = pat.address_addressId  
	 left join MisLpu as ml on ml.id=m.department_id
     left join MedCase slo on slo.parent_id=m.id and slo.dtype='DepartmentMedCase'
	 left join SecUser su on su.login=m.username
	left join WorkFunction wf on wf.secUser_id=su.id
	left join Worker w on w.id=wf.worker_id
	left join MisLpu ml1 on ml1.id=w.lpu_id 
     where m.DTYPE='HospitalMedCase' and m.datestart= to_date('${param.dateBegin}','dd.mm.yyyy')
      and m.deniedHospitalizating_id is not null
      ${emerIs} ${pigeonHole1} ${department} ${serviceStreamSql}
      ${addPat}
       order by m.${dateI},pat.lastname,pat.firstname,pat.middlename
      " />
    <msh:table printToExcelButton="Сохранить в excel" name="journal_priem"  viewUrl="entityShortView-stac_ssl.do" action="entityParentView-stac_ssl.do" idField="1">
      <msh:tableColumn columnName="#" property="sn" />
      <msh:tableColumn columnName="Стат.карта" property="4" />
      <msh:tableColumn columnName="Фамилия имя отчество пациента" property="3" />
      <msh:tableColumn columnName="Дата обращения" property="2" />
      <msh:tableColumn columnName="Экстрено" property="5" />
      <msh:tableColumn columnName="Причина отказа" property="6" />
      <msh:tableColumn columnName="Пациент" property="7"/>
      <msh:tableColumn columnName="Отделение" property="8"/>
    </msh:table>
    </msh:sectionContent>
    </msh:section>    <% }
    	
    	
    	}  else {
    		
    		// СВОД
    		%>
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
      " />
    <msh:table name="journal_priem" action="stac_reestrByHospital.do" idField="1">
      <msh:tableColumn columnName="#" property="sn" />
      <msh:tableColumn columnName="Отделение" property="2" />
      <msh:tableColumn columnName="Кол-во" isCalcAmount="true" property="3" />
      <msh:tableColumn columnName="Кол-во экстренных" isCalcAmount="true" property="4" />
      <msh:tableColumn columnName="Кол-во плановых" isCalcAmount="true" property="5" />
      <msh:tableColumn columnName="Кол-во иностранцев" isCalcAmount="true" property="6" />
      <msh:tableColumn columnName="Кол-во иногородних" isCalcAmount="true" property="7" />
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
      " />
    <msh:table name="journal_priem" action="stac_reestrByHospital.do" idField="1">
      <msh:tableColumn columnName="#" property="sn" />
      <msh:tableColumn columnName="Причина отказа" property="1" />
      <msh:tableColumn isCalcAmount="true" columnName="Кол-во" property="2" />
      <msh:tableColumn isCalcAmount="true" columnName="Кол-во экстренных" property="3" />
      <msh:tableColumn isCalcAmount="true" columnName="Кол-во иностранцев" property="4" />
      <msh:tableColumn isCalcAmount="true" columnName="Кол-во иногородних" property="5" />
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
	    	frm.action='stac_reestrByHospital.do' ;
	    }
	    function print(aFile) {
	    	var frm = document.forms[0] ;
	    	frm.m.value="printReestrByDay" ;
	    	frm.s.value="HospitalPrintService" ;
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
	    
	    function viewJournal() {
	    	var val =false;
	    	if (viewPrintJournal) {
	    		val=true ;
	    	}
	    	for (var i=1;i<=4;i++) {
    			showRow("journal"+i,val) ;
    		}
	    	viewPrintJournal=!viewPrintJournal ;
	    }
	    viewJournal() ;
	    function showRow(aRowId, aCanShow ) {
    		//alert(aRowId) ;
			try {
				//alert( aCanShow ? 'table-row' : 'none') ;
				$(aRowId).style.display = aCanShow ? 'table-row' : 'none' ;
			} catch (e) {
				// for IE
				//alert(aCanShow ? 'block' : 'none') ;
				try{
				$(aRowId).style.display = aCanShow ? 'block' : 'none' ;
				}catch(e) {}
			}	
		}
	    function printJournal() {
	    	var frm = document.forms[0] ;
	    	frm.m.value="printJournalByDay" ;
	    	frm.s.value="HospitalPrintReport" ;
	    	frm.target='_blank' ;
	    	var format ="_A4" ;
	    	var fC = getCheckedRadio(frm,"typeFormat") ;
	    	if (+fC>1) {
	    		format="" ;
	    	}
	    	frm.action='print-stac_report001'+format+'.do' ;
	    	$('id').value = getCheckedRadio(frm,"typeEmergency")+":"
	    		+getCheckedRadio(frm,"typeHour")+":"
	    		+getCheckedRadio(frm,"typeDate")+":"
	    		+$('dateBegin').value+":"
	    		+$('pigeonHole').value+":"
	    		+$('department').value+":"
	    		+$('numberInJournal').value
	    		+":"+getCheckedRadio(frm,"typeNumber");
	    }
	    function printNew() {
	    	//print() ;
	    	var frm = document.forms[0] ;
	    	frm.m.value="printReestrByDay" ;
	    	frm.s.value="HospitalPrintService" ;
	    	frm.target='_blank' ;
	    	frm.action='print-stac_reestrForDay.do' ;
	    }
		</script>
  </tiles:put>
</tiles:insert>
<!--last release milamesher 04.04.2018 - Реестр отказов от госпитализаций за день -->
<!--last release milamesher 18.04.2018 - Убран абсолютно ненужный left join, но откуда тогда берётся фрагмент and coalesce(slo.department_id,m.department_id)='220' в логах postgresа? -->