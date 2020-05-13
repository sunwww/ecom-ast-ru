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
    <msh:title mainMenu="Journals">Форма 007у-02</msh:title>
  </tiles:put>
  <tiles:put name="side" type="string">
  	<tags:stac_journal currentAction="stac_report007"/>
  </tiles:put>
  <tiles:put name="body" type="string">
  
  <% 
  boolean sh = true ;
  //String shS =  ;
  if (request.getParameter("short")!=null) {
	  sh=false ;
  }
  ActionUtil.updateParameter("Report007","typeView","4", request) ;
  String typeHour = ActionUtil.updateParameter("Report007","typeHour","3", request) ;
  if (sh) {
	 
  %>
    <msh:form action="/stac_report_007.do" defaultField="dateBegin" disableFormDataConfirm="true" method="GET">
    <input type="hidden" name="s" id="s" value="HospitalPrintReport" />
    <input type="hidden" name="m" id="m" value="printReport007" />
    <input type="hidden" name="id" id="id" value=""/>
    <msh:panel>
      <msh:row>
        <msh:separator label="Параметры поиска" colSpan="7" />
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
        <td class="label" title="Просмотр данных (typeView)" colspan="1"><label for="typeViewName" id="typeViewLabel">Отобразить:</label></td>
        <td onclick="this.childNodes[1].checked='checked';">
        	<input type="radio" name="typeView" value="1">  7 форма
        </td>
        <td onclick="this.childNodes[1].checked='checked';">
        	<input type="radio" name="typeView" value="2"  >  реестр состоящих пациентов
        </td>
        <td onclick="this.childNodes[1].checked='checked';">
        	<input type="radio" name="typeView" value="3"  >  реестр пациентов
        </td>
        <td onclick="this.childNodes[1].checked='checked';">
        	<input type="radio" name="typeView" value="4"  >  свод по отделениям
        </td>
       </msh:row>
      <msh:row>
        <msh:textField fieldColSpan="2" property="dateBegin" label="Дата" />
      </msh:row>
        <msh:row>
        	<msh:autoComplete property="department" fieldColSpan="4" horizontalFill="true" label="Отделение" vocName="vocLpuHospOtdAll"/>
        </msh:row>
      <msh:row>
           <td colspan="11">
            <input type="submit" onclick="find()" value="Найти" />
            <input type="submit" onclick="print()" value="Печать" />
<%--            <input type="submit" onclick="printJournal()" value="Печать журнала госпитализаций и отказов от госпитализаций" />
            <input type="submit" onclick="printNew()" value="Печать (по отделениям)" />
            <input type="submit" onclick="printNew1()" value="Печать (сопроводительного листа) 1" />
            <input type="submit" onclick="printNew2()" value="Печать (сопроводительного листа) 2" />
             --%>
          </td>
      </msh:row>
    </msh:panel>
    </msh:form>
    
       <script type='text/javascript'>
    
<%--    checkFieldUpdate('typeDate','${typeDate}',1) ;
     checkFieldUpdate('typePatient','${typePatient}',3) ;
    checkFieldUpdate('typeEmergency','${typeEmergency}',3) ;--%>
    checkFieldUpdate('typeHour','${typeHour}',4) ;
    checkFieldUpdate('typeView','${typeView}',1) ;
    
  
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
			 
    function find() {
    	var frm = document.forms[0] ;
    	frm.target='' ;
    	frm.action='stac_report_007.do' ;
    }

    function print() {
    	var frm = document.forms[0] ;
    	frm.m.value="printReport007" ;
    	frm.target='_blank' ;
    	frm.action='print-stac_report007.do';
    	/* var div = document.getElementsByClassName("sectionContent")[0];
    	if (div.innerHTML.startsWith('<p>Нет данных</p>')) {
    		frm.action='print-stac_report007Empty.do' ;
    		
    	} */
    	//frm.action=act ;
    	//frm.submit();
   // 	$('id').value = getCheckeddepartmentRadio(frm,"typeEmergency")+":"
   // 		+getCheckedRadio(frm,"typeHour")+":"
   // 		+getCheckedRadio(frm,"typeDate")+":"
   // 		+$('dateBegin').value+":"
  //  		+$('pigeonHole').value+":"
   // 		+$('department').value;
    }
    function printJournal() {
    	var frm = document.forms[0] ;
    	frm.m.value="printJournalByDay" ;
    	frm.target='_blank' ;
    	frm.action='print-stac_journal001.do' ;
    	$('id').value = 
    		$('dateBegin').value+":"
    		
    		+$('department').value;
    }
    /**/
    if ($('dateBegin').value=="") {
    	$('dateBegin').value=getCurrentDate() ;
    }

			 
    </script>
     <%
  }
     String date = request.getParameter("dateBegin") ;
     String dep1 = request.getParameter("department1") ;
     String dep = request.getParameter("department") ;
     //if (dep!=null && dep.equals("undefined")) dep=null ;
     String id = request.getParameter("id") ;
     String view = (String)request.getAttribute("typeView") ;
    
if (date!=null && !date.equals("")) {
	String timeSql = "09:00" ;
	if (typeHour.equals("1")) {
		timeSql="07:00" ;
	} else if (typeHour.equals("2")) {
		timeSql="08:00";
	} else if (typeHour.equals("4")) {
		timeSql="00:00";
	}
	request.setAttribute("timeSql", timeSql) ;
	request.setAttribute("dateBegin", date) ;
	String department="" ;
	
	if (dep!=null &&!dep.equals("") && !dep.equals("0")) {
		department= " and slo.department_id='"+dep+"'" ;
	}
	request.setAttribute("department", department) ;
	Date dat = DateFormat.parseDate(date) ;
    Calendar cal = Calendar.getInstance() ;
    cal.setTime(dat) ;
    cal.add(Calendar.DAY_OF_MONTH, 1) ;
    SimpleDateFormat format=new SimpleDateFormat("dd.MM.yyyy") ;
    String date1=format.format(cal.getTime()) ;
    request.setAttribute("dateNext", date1) ;
    
	
    String bedFund = request.getParameter("bedFund") ;
	if (bedFund!=null && !bedFund.equals("") && !bedFund.equals("0")) {
		%>

	    <msh:section>
	    <msh:sectionTitle>007/у-02 форма. Реестр пациентов</msh:sectionTitle>
	    <msh:sectionContent>
	    <ecom:webQuery name="journal_priem" nativeSql="select 
	    slo.id
	    ,ss.code as sscode
	    ,pat.lastname||' '||pat.middlename||' '||coalesce(pat.firstname,'')||' '||to_char(pat.birthday,'dd.mm.yyyy') as patfio
	    ,
	    vbst.name as vbstname,case when vss.id!=vss1.id then '<font color=red>'||vss1.name||'</font>' else vss.name end as vssname
	,case when (slo.datestart = to_date('${dateBegin}','dd.mm.yyyy') and cast('${timeSql}:00' as time)>slo.entrancetime
	or to_date('${dateBegin}','dd.mm.yyyy')>slo.datestart)
	and (slo.datefinish is null 
	or slo.datefinish > to_date('${dateBegin}','dd.mm.yyyy') 
	or slo.datefinish = to_date('${dateBegin}','dd.mm.yyyy') and slo.dischargetime>=cast('${timeSql}' as time))
	and (slo.transferdate is null 
	or slo.transferdate > to_date('${dateBegin}','dd.mm.yyyy') 
	or
	slo.transferdate = to_date('${dateBegin}','dd.mm.yyyy') and slo.transfertime>=cast('${timeSql}' as time))

	 then 'Х' else null end
	as cnt5CurrentFrom
	,case when slo.prevmedcase_id is null and (slo.datestart = to_date('${dateBegin}','dd.mm.yyyy') and slo.entrancetime>=cast('${timeSql}:00' as time)
	or slo.datestart = to_date('${dateNext}','dd.mm.yyyy') and cast('${timeSql}' as time)>slo.entrancetime) then 'Х' else null end
	as cnt6Entrance
	,case when slo.prevmedcase_id is null and vht.code='DAYTIMEHOSP' and(slo.datestart = to_date('${dateBegin}','dd.mm.yyyy') and slo.entrancetime>=cast('${timeSql}:00' as time)
	or slo.datestart = to_date('${dateNext}','dd.mm.yyyy') and cast('${timeSql}' as time)>slo.entrancetime) then 'Х' else null end
	as cnt7EntranceDayHosp
	,case when slo.prevmedcase_id is null and (slo.datestart = to_date('${dateBegin}','dd.mm.yyyy') and slo.entrancetime>=cast('${timeSql}:00' as time)
	or slo.datestart = to_date('${dateNext}','dd.mm.yyyy') and cast('${timeSql}' as time)>slo.entrancetime) 
	and (ad1.addressIsVillage='1')
	then 'Х' else null end
	as cnt8EntranceVillagers
	,case when slo.prevmedcase_id is null and (slo.datestart = to_date('${dateBegin}','dd.mm.yyyy') and slo.entrancetime>=cast('${timeSql}:00' as time)
	or slo.datestart = to_date('${dateNext}','dd.mm.yyyy') and cast('${timeSql}' as time)>slo.entrancetime) 
	and (
	cast(to_char(slo.datestart,'yyyy') as int)-cast(to_char(pat.birthday,'yyyy') as int)
	+case when (cast(to_char(slo.datestart, 'mm') as int)-cast(to_char(pat.birthday, 'mm') as int)) <0 then -1 when (cast(to_char(slo.datestart,'dd') as int) - cast(to_char(pat.birthday,'dd') as int)<0) and ((cast(to_char(slo.datestart, 'mm') as int)-cast(to_char(pat.birthday, 'mm') as int)-1)<0)  then -1 else 0 end
	)<18
	then 'Х' else null end

	as cnt9EntranceTo17
	,case when slo.prevmedcase_id is null and (slo.datestart = to_date('${dateBegin}','dd.mm.yyyy') and slo.entrancetime>=cast('${timeSql}:00' as time)
	or slo.datestart = to_date('${dateNext}','dd.mm.yyyy') and cast('${timeSql}' as time)>slo.entrancetime) 
	and 
	60<=(
	cast(to_char(slo.datestart,'yyyy') as int)-cast(to_char(pat.birthday,'yyyy') as int)
	+case when (cast(to_char(slo.datestart, 'mm') as int)-cast(to_char(pat.birthday, 'mm') as int)) <0 then -1 when (cast(to_char(slo.datestart,'dd') as int) - cast(to_char(pat.birthday,'dd') as int)<0) and ((cast(to_char(slo.datestart, 'mm') as int)-cast(to_char(pat.birthday, 'mm') as int)-1)<0)  then -1 else 0 end
	)
	then 'Х' else null end
	as cnt10EntranceFrom60
	,case when slo.prevmedcase_id is not null and (slo.datestart = to_date('${dateBegin}','dd.mm.yyyy') and slo.entrancetime>=cast('${timeSql}:00' as time)
	or slo.datestart = to_date('${dateNext}','dd.mm.yyyy') and cast('${timeSql}' as time)>slo.entrancetime) then 'Х' else null end
	as cnt11TransferOutOtherDepartment
	,case when (slo.transferdate = to_date('${dateBegin}','dd.mm.yyyy') and slo.transfertime>=cast('${timeSql}' as time)
	or slo.transferdate = to_date('${dateNext}','dd.mm.yyyy') and cast('${timeSql}' as time)>slo.transfertime) then 'Х' else null end
	as cnt12TransferInOtherDepartment
	,case when vhr.code!='11' and (slo.datefinish = to_date('${dateBegin}','dd.mm.yyyy') and slo.dischargetime>=cast('${timeSql}' as time)
	or slo.datefinish = to_date('${dateNext}','dd.mm.yyyy') and cast('${timeSql}' as time)>slo.dischargetime) then 'Х' else null end
	as cnt13Finished
	,case when vho.code='4' and vhr.code!='11' and (slo.datefinish = to_date('${dateBegin}','dd.mm.yyyy') and slo.dischargetime>=cast('${timeSql}' as time)
	or slo.datefinish = to_date('${dateNext}','dd.mm.yyyy') and cast('${timeSql}' as time)>slo.dischargetime) then 'Х' else null end
	as cnt14FinishedOtherHospital
	,case when vho.code='3' and vhr.code!='11' and (slo.datefinish = to_date('${dateBegin}','dd.mm.yyyy') and slo.dischargetime>=cast('${timeSql}' as time)
	or slo.datefinish = to_date('${dateNext}','dd.mm.yyyy') and cast('${timeSql}' as time)>slo.dischargetime) then 'Х' else null end
	as cnt15FinishedHourHospital
	,case when vho.code='2' and vhr.code!='11' and (slo.datefinish = to_date('${dateBegin}','dd.mm.yyyy') and slo.dischargetime>=cast('${timeSql}' as time)
	or slo.datefinish = to_date('${dateNext}','dd.mm.yyyy') and cast('${timeSql}' as time)>slo.dischargetime) then 'Х' else null end
	as cnt16FinishedDayHospital
	,case when vhr.code='11' and (slo.datefinish = to_date('${dateBegin}','dd.mm.yyyy') and slo.dischargetime>=cast('${timeSql}' as time)
	or slo.datefinish = to_date('${dateNext}','dd.mm.yyyy') and cast('${timeSql}' as time)>slo.dischargetime) then 'Х' else null end
	as cnt17Death
	,
	case when 
			(
				slo.transferdate is null
				or slo.transferdate > to_date('${dateNext}','dd.mm.yyyy')
				or slo.transferdate = to_date('${dateNext}','dd.mm.yyyy') and slo.transfertime>=cast('${timeSql}' as time)
			) and (
				slo.datefinish is null or
				slo.datefinish > to_date('${dateNext}','dd.mm.yyyy')
				or slo.datefinish = to_date('${dateNext}','dd.mm.yyyy') and slo.dischargetime>=cast('${timeSql}' as time)
			)
		 then 'Х' else null end

	 as cnt18CurrentTo
	 
	,case when
	 sls.hotelServices='1' and (
				slo.transferdate is null
				or slo.transferdate > to_date('${dateNext}','dd.mm.yyyy')
				or slo.transferdate = to_date('${dateNext}','dd.mm.yyyy') and slo.transfertime>=cast('${timeSql}' as time)
			) and (
				slo.datefinish is null or
				slo.datefinish > to_date('${dateNext}','dd.mm.yyyy')
				or slo.datefinish = to_date('${dateNext}','dd.mm.yyyy') and slo.dischargetime>=cast('${timeSql}' as time)
			)  then 'Х' else null end as cnt19CurrentMother
	
	 from medcase slo
	 left join patient pat on pat.id=slo.patient_id
	 left join medcase sls on sls.id=slo.parent_id
	 left join statisticstub ss on ss.id=sls.statisticstub_id
	 left join vochospitalizationoutcome vho on vho.id=sls.outcome_id
	 left join vochospitalizationresult vhr on vhr.id=sls.result_id
	left join bedfund bf on bf.id=slo.bedfund_id
	left join vocbedsubtype vbst on vbst.id=bf.bedsubtype_id
	left join vocservicestream vss on vss.id=bf.servicestream_id
	left join vocservicestream vss1 on vss1.id=slo.servicestream_id
	left join vochosptype vht on vht.id=sls.sourceHospType_id
	LEFT JOIN Address2 ad1 on ad1.addressId=pat.address_addressId 
	where 
	slo.dtype='DepartmentMedCase' ${department}
	 and (to_date('${dateBegin}','dd.mm.yyyy')>=slo.datestart or
	slo.datestart = to_date('${dateNext}','dd.mm.yyyy') and cast('${timeSql}' as time)>slo.entrancetime)
	and (slo.datefinish is null or slo.datefinish > to_date('${dateBegin}','dd.mm.yyyy') or 
	slo.datefinish = to_date('${dateBegin}','dd.mm.yyyy') and slo.dischargetime>=cast('${timeSql}' as time) or
	slo.datefinish = to_date('${dateNext}','dd.mm.yyyy') and cast('${timeSql}' as time)>slo.dischargetime)
	and (slo.transferdate is null or slo.transferdate > to_date('${dateBegin}','dd.mm.yyyy') or
	slo.transferdate = to_date('${dateBegin}','dd.mm.yyyy') and slo.transfertime>=cast('${timeSql}' as time) or
	slo.transferdate = to_date('${dateNext}','dd.mm.yyyy') and cast('${timeSql}' as time)>slo.transfertime)
	and slo.bedfund_id=${param.bedFund}
	      " />
	    <msh:table name="journal_priem" viewUrl="entityShortView-stac_slo.do" action="entityParentView-stac_slo.do" idField="1">
	      <msh:tableColumn columnName="#" property="sn" />
	      <msh:tableColumn property="2" columnName="№ ИБ"/>
	      <msh:tableColumn property="3" columnName="ФИО пациента"/>
	      <msh:tableColumn columnName="профиль коек" property="4" />
	      <msh:tableColumn columnName="поток обслуживания" property="5" />
	      <msh:tableColumn isCalcAmount="true" columnName="Состоит на начало истекших суток" property="6" />
	      <msh:tableColumn isCalcAmount="true" columnName="поступило всего" property="7" />
	      <msh:tableColumn isCalcAmount="true" columnName="в т.ч. из дневного стационар" property="8" />
	      <msh:tableColumn isCalcAmount="true" columnName="с.ж" property="9" />
	      <msh:tableColumn isCalcAmount="true" columnName="0-17 лет" property="10" />
	      <msh:tableColumn isCalcAmount="true" columnName="60 лет и старше" property="11" />
	      <msh:tableColumn isCalcAmount="true" columnName="переведено из других отд" property="12" />
	      <msh:tableColumn isCalcAmount="true" columnName="переведено в другие отд" property="13" />
	      <msh:tableColumn isCalcAmount="true" columnName="выписано" property="14" />
	      <msh:tableColumn isCalcAmount="true" columnName="в другие стационары" property="15" />
	      <msh:tableColumn isCalcAmount="true" columnName="в круглосуточный стационар" property="16" />
	      <msh:tableColumn isCalcAmount="true" columnName="в дневной стационар" property="17" />
	      <msh:tableColumn isCalcAmount="true" columnName="умерло" property="18" />
	      <msh:tableColumn isCalcAmount="true" columnName="состоит всего" property="19" />
	      <msh:tableColumn isCalcAmount="true" columnName="состоит матерей" property="20" />
	    </msh:table>
	    </msh:sectionContent>
	    </msh:section>

	<%		
		} else {
		    	
		    if ( dep!=null && !dep.equals("") && !dep.equals("0")) { 	
		    	
		    if (view.equals("1")) {
	    	%>
	    
	    <msh:section>
	    <msh:sectionTitle>007/у-02 форма</msh:sectionTitle>
	    <msh:sectionContent>
	    <ecom:webQuery name="journal_priem" nativeSql="select 
	    '&department=${param.department}&dateBegin=${dateBegin}&bedFund='||slo.bedfund_id as vbstid,
	    vbt.name||' '||vbst.name as vbstname,vss.name as vssname
	,count(distinct case when 
	(slo.datestart = to_date('${dateBegin}','dd.mm.yyyy') and cast('${timeSql}:00' as time)>slo.entrancetime
	or to_date('${dateBegin}','dd.mm.yyyy')>slo.datestart)
	and (slo.datefinish is null 
	or slo.datefinish > to_date('${dateBegin}','dd.mm.yyyy') 
	or slo.datefinish = to_date('${dateBegin}','dd.mm.yyyy') and slo.dischargetime>=cast('${timeSql}' as time))
	and (slo.transferdate is null 
	or slo.transferdate > to_date('${dateBegin}','dd.mm.yyyy') 
	or
	slo.transferdate = to_date('${dateBegin}','dd.mm.yyyy') and slo.transfertime>=cast('${timeSql}' as time))

	 then slo.id else null end)
	as cnt5CurrentFrom
	,count(distinct case when slo.prevmedcase_id is null and (slo.datestart = to_date('${dateBegin}','dd.mm.yyyy') and slo.entrancetime>=cast('${timeSql}:00' as time)
	or slo.datestart = to_date('${dateNext}','dd.mm.yyyy') and cast('${timeSql}' as time)>slo.entrancetime) then slo.id else null end)
	as cnt6Entrance
	,count(distinct case when slo.prevmedcase_id is null and vht.code='DAYTIMEHOSP' and(slo.datestart = to_date('${dateBegin}','dd.mm.yyyy') and slo.entrancetime>=cast('${timeSql}:00' as time)
	or slo.datestart = to_date('${dateNext}','dd.mm.yyyy') and cast('${timeSql}' as time)>slo.entrancetime) then slo.id else null end)
	as cnt7EntranceDayHosp
	,count(distinct case when slo.prevmedcase_id is null and (slo.datestart = to_date('${dateBegin}','dd.mm.yyyy') and slo.entrancetime>=cast('${timeSql}:00' as time)
	or slo.datestart = to_date('${dateNext}','dd.mm.yyyy') and cast('${timeSql}' as time)>slo.entrancetime) 
	and (ad1.addressIsVillage='1')
	then slo.id else null end)
	as cnt8EntranceVillagers
	,count(distinct case when slo.prevmedcase_id is null and(slo.datestart = to_date('${dateBegin}','dd.mm.yyyy') and slo.entrancetime>=cast('${timeSql}:00' as time)
	or slo.datestart = to_date('${dateNext}','dd.mm.yyyy') and cast('${timeSql}' as time)>slo.entrancetime) 
	and (
	cast(to_char(slo.datestart,'yyyy') as int)-cast(to_char(pat.birthday,'yyyy') as int)
	+case when (cast(to_char(slo.datestart, 'mm') as int)-cast(to_char(pat.birthday, 'mm') as int)) <0 then -1 when (cast(to_char(slo.datestart,'dd') as int) - cast(to_char(pat.birthday,'dd') as int)<0) and ((cast(to_char(slo.datestart, 'mm') as int)-cast(to_char(pat.birthday, 'mm') as int)-1)<0)  then -1 else 0 end
	)<18
	then slo.id else null end)

	as cnt9EntranceTo17
	,count(distinct case when slo.prevmedcase_id is null and (slo.datestart = to_date('${dateBegin}','dd.mm.yyyy') and slo.entrancetime>=cast('${timeSql}:00' as time)
	or slo.datestart = to_date('${dateNext}','dd.mm.yyyy') and cast('${timeSql}' as time)>slo.entrancetime) 
	and 
	60<=(
	cast(to_char(slo.datestart,'yyyy') as int)-cast(to_char(pat.birthday,'yyyy') as int)
	+case when (cast(to_char(slo.datestart, 'mm') as int)-cast(to_char(pat.birthday, 'mm') as int)) <0 then -1 when (cast(to_char(slo.datestart,'dd') as int) - cast(to_char(pat.birthday,'dd') as int)<0) and ((cast(to_char(slo.datestart, 'mm') as int)-cast(to_char(pat.birthday, 'mm') as int)-1)<0)  then -1 else 0 end
	)
	then slo.id else null end)
	as cnt10EntranceFrom60
	,count(distinct case when slo.prevmedcase_id is not null and (slo.datestart = to_date('${dateBegin}','dd.mm.yyyy') and slo.entrancetime>=cast('${timeSql}:00' as time)
	or slo.datestart = to_date('${dateNext}','dd.mm.yyyy') and cast('${timeSql}' as time)>slo.entrancetime) then slo.id else null end)
	as cnt11TransferOutOtherDepartment
	,count(distinct case when (slo.transferdate = to_date('${dateBegin}','dd.mm.yyyy') and slo.transfertime>=cast('${timeSql}' as time)
	or slo.transferdate = to_date('${dateNext}','dd.mm.yyyy') and cast('${timeSql}' as time)>slo.transfertime) then slo.id else null end)
	as cnt12TransferInOtherDepartment
	,count(distinct case when vhr.code!='11' and (slo.datefinish = to_date('${dateBegin}','dd.mm.yyyy') and slo.dischargetime>=cast('${timeSql}' as time)
	or slo.datefinish = to_date('${dateNext}','dd.mm.yyyy') and cast('${timeSql}' as time)>slo.dischargetime) then slo.id else null end)
	as cnt13Finished
	,count(distinct case when vho.code='4' and vhr.code!='11' and (slo.datefinish = to_date('${dateBegin}','dd.mm.yyyy') and slo.dischargetime>=cast('${timeSql}' as time)
	or slo.datefinish = to_date('${dateNext}','dd.mm.yyyy') and cast('${timeSql}' as time)>slo.dischargetime) then slo.id else null end)
	as cnt14FinishedOtherHospital
	,count(distinct case when vho.code='3' and vhr.code!='11' and (slo.datefinish = to_date('${dateBegin}','dd.mm.yyyy') and slo.dischargetime>=cast('${timeSql}' as time)
	or slo.datefinish = to_date('${dateNext}','dd.mm.yyyy') and cast('${timeSql}' as time)>slo.dischargetime) then slo.id else null end)
	as cnt15FinishedHourHospital
	,count(distinct case when vho.code='2' and vhr.code!='11' and (slo.datefinish = to_date('${dateBegin}','dd.mm.yyyy') and slo.dischargetime>=cast('${timeSql}' as time)
	or slo.datefinish = to_date('${dateNext}','dd.mm.yyyy') and cast('${timeSql}' as time)>slo.dischargetime) then slo.id else null end)
	as cnt16FinishedDayHospital
	,count(distinct case when vhr.code='11' and (slo.datefinish = to_date('${dateBegin}','dd.mm.yyyy') and slo.dischargetime>=cast('${timeSql}' as time)
	or slo.datefinish = to_date('${dateNext}','dd.mm.yyyy') and cast('${timeSql}' as time)>slo.dischargetime) then slo.id else null end)
	as cnt17Death
	,

	count(distinct case when 
			(
				slo.transferdate is null
				or slo.transferdate > to_date('${dateNext}','dd.mm.yyyy')
				or slo.transferdate = to_date('${dateNext}','dd.mm.yyyy') and slo.transfertime>=cast('${timeSql}' as time)
			) and (
				slo.datefinish is null or
				slo.datefinish > to_date('${dateNext}','dd.mm.yyyy')
				or slo.datefinish = to_date('${dateNext}','dd.mm.yyyy') and slo.dischargetime>=cast('${timeSql}' as time)
			)
		 then slo.id else null end
	)
	 as cnt18CurrentTo
	 
	,count(distinct case when sls.hotelServices='1' and 
	 (
				slo.transferdate is null
				or slo.transferdate > to_date('${dateNext}','dd.mm.yyyy')
				or slo.transferdate = to_date('${dateNext}','dd.mm.yyyy') and slo.transfertime>=cast('${timeSql}' as time)
			) and (
				slo.datefinish is null or
				slo.datefinish > to_date('${dateNext}','dd.mm.yyyy')
				or slo.datefinish = to_date('${dateNext}','dd.mm.yyyy') and slo.dischargetime>=cast('${timeSql}' as time)
			)  then slo.id else null end)
	 as cnt19CurrentMother
	,bf.amount as BfAmount20
	 from medcase slo
	 left join patient pat on pat.id=slo.patient_id
	 left join medcase sls on sls.id=slo.parent_id
	 left join vochospitalizationoutcome vho on vho.id=sls.outcome_id
	 left join vochospitalizationresult vhr on vhr.id=sls.result_id
	left join bedfund bf on bf.id=slo.bedfund_id
	left join vocbedsubtype vbst on vbst.id=bf.bedsubtype_id
	left join vocbedtype vbt on vbt.id=bf.bedtype_id
	left join vocservicestream vss on vss.id=bf.servicestream_id
	left join vochosptype vht on vht.id=sls.sourceHospType_id
	LEFT JOIN Address2 ad1 on ad1.addressId=pat.address_addressId 
	LEFT JOIN Address2 ad2 on ad2.addressId=ad1.parent_addressId
	where 
	slo.dtype='DepartmentMedCase' 
	 and (to_date('${dateBegin}','dd.mm.yyyy')>=slo.datestart or
	slo.datestart = to_date('${dateNext}','dd.mm.yyyy') and cast('${timeSql}' as time)>slo.entrancetime)
	and (slo.datefinish is null or slo.datefinish > to_date('${dateBegin}','dd.mm.yyyy') or 
	slo.datefinish = to_date('${dateBegin}','dd.mm.yyyy') and slo.dischargetime>=cast('${timeSql}' as time) or
	slo.datefinish = to_date('${dateNext}','dd.mm.yyyy') and cast('${timeSql}' as time)>slo.dischargetime)
	and (slo.transferdate is null or slo.transferdate > to_date('${dateBegin}','dd.mm.yyyy') or
	slo.transferdate = to_date('${dateBegin}','dd.mm.yyyy') and slo.transfertime>=cast('${timeSql}' as time) or
	slo.transferdate = to_date('${dateNext}','dd.mm.yyyy') and cast('${timeSql}' as time)>slo.transfertime)
	${department}
	group by slo.bedfund_id,bf.bedsubtype_id,vbst.name,vbt.name,bf.serviceStream_id,vss.name,bf.amount
	      " />
	    <msh:table name="journal_priem" 
	    viewUrl="stac_report_007.do?short=Short" 
	    action="stac_report_007.do" idField="1">
	      <msh:tableColumn columnName="#" property="sn" />
	      <msh:tableColumn columnName="профиль коек" property="2" />
	      <msh:tableColumn columnName="поток обслуживания" property="3" />
	      <msh:tableColumn isCalcAmount="true" columnName="Состоит на начало истекших суток" property="4" />
	      <msh:tableColumn isCalcAmount="true" columnName="поступило всего" property="5" />
	      <msh:tableColumn isCalcAmount="true" columnName="в т.ч. из дневного стационар" property="6" />
	      <msh:tableColumn isCalcAmount="true" columnName="с.ж" property="7" />
	      <msh:tableColumn isCalcAmount="true" columnName="0-17 лет" property="8" />
	      <msh:tableColumn isCalcAmount="true" columnName="60 лет и старше" property="9" />
	      <msh:tableColumn isCalcAmount="true" columnName="переведено из других отд" property="10" />
	      <msh:tableColumn isCalcAmount="true" columnName="переведено в другие отд" property="11" />
	      <msh:tableColumn isCalcAmount="true" columnName="выписано" property="12" />
	      <msh:tableColumn isCalcAmount="true" columnName="в другие стационары" property="13" />
	      <msh:tableColumn isCalcAmount="true" columnName="в круглосуточный стационар" property="14" />
	      <msh:tableColumn isCalcAmount="true" columnName="в дневной стационар" property="15" />
	      <msh:tableColumn isCalcAmount="true" columnName="умерло" property="16" />
	      <msh:tableColumn isCalcAmount="true" columnName="состоит всего" property="17" />
	      <msh:tableColumn isCalcAmount="true" columnName="состоит матерей" property="18" />
	    </msh:table>
	    </msh:sectionContent>
	    </msh:section>
	    <%
	    }
	    if (view.equals("2")) {
	    %>
	    <table border=0>
	    <tr>
	    <td valign="top"  style="padding-right: 4px">
	    <msh:section>
	    <msh:sectionTitle>Реестр состоящих на начало истекших суток</msh:sectionTitle>
	    <msh:sectionContent>
	    <ecom:webQuery name="journal_priem01" nativeSql="select slo.id as sloid
	    	,ss.code as sscode
	    	,pat.lastname||' '||substring(pat.firstname,1,1)||' '||coalesce(substring(pat.middlename,1,1),'')||' '||case when slo.prevmedcase_id is not null then ' ('||pdep.name||')' else '' end as fio
	    	,to_char(slo.dateStart,'dd.mm.yyyy') ||' '|| cast(slo.entrancetime as varchar(5)) as slodateStart
	    	,to_char(slo.transferdate,'dd.mm.yyyy') ||' '|| cast(slo.transfertime as varchar(5)) as slotransferdate
	    	,to_char(slo.datefinish,'dd.mm.yyyy') ||' '|| cast(slo.dischargetime as varchar(5)) as slodatefinish
	    	,vss.name as vssname
	from medcase slo
	left join medcase sls on sls.id=slo.parent_id
	 left join VocServiceStream vss on vss.id=slo.serviceStream_id
	left join statisticstub ss on ss.medcase_id=sls.id
	left join patient pat on slo.patient_id=pat.id
	left join medcase pslo on pslo.id=slo.prevmedcase_id
	left join mislpu pdep on pdep.id=pslo.department_id
	where  slo.dtype='DepartmentMedCase' ${department} 
	and
	(slo.datestart = to_date('${dateBegin}','dd.mm.yyyy') and cast('${timeSql}:00' as time)>slo.entrancetime
	or to_date('${dateBegin}','dd.mm.yyyy')>slo.datestart)
	and (slo.datefinish is null 
	or slo.datefinish > to_date('${dateBegin}','dd.mm.yyyy') 
	or slo.datefinish = to_date('${dateBegin}','dd.mm.yyyy') and slo.dischargetime>=cast('${timeSql}' as time))
	and (slo.transferdate is null 
	or slo.transferdate > to_date('${dateBegin}','dd.mm.yyyy') 
	or
	slo.transferdate = to_date('${dateBegin}','dd.mm.yyyy') and slo.transfertime>=cast('${timeSql}' as time))

	order by pat.lastname,pat.firstname,pat.middlename
	      " />
	    <msh:table name="journal_priem01" viewUrl="entityShortView-stac_slo.do" action="entityParentView-stac_slo.do" idField="1">
	      <msh:tableColumn columnName="#" property="sn"/>
	      <msh:tableColumn columnName="Стат.карта" property="2"/>
	      <msh:tableColumn columnName="Фамилия имя отчество пациента" property="3"/>
	      <msh:tableColumn property="4" columnName="Дата пост"/>
	      <msh:tableColumn property="5" columnName="Дата перевода"/>
	      <msh:tableColumn property="6" columnName="Дата выписки"/>
	      <msh:tableColumn property="7" columnName="Поток обслуживания"/>
	    </msh:table>
	    </msh:sectionContent>
	    </msh:section>
	    </td>
	    <td valign="top" style="padding-right: 4px">
	    <msh:section>
	    <msh:sectionTitle>Реестр состоящих на начало текущего дня</msh:sectionTitle>
	    <msh:sectionContent>
	    <ecom:webQuery name="journal_priem02" nativeSql="select slo.id as sloid
	    	,ss.code as sscode
	    	,pat.lastname||' '||substring(pat.firstname,1,1)||' '||coalesce(substring(pat.middlename,1,1),'')||' '||case when slo.prevmedcase_id is not null then ' ('||pdep.name||')' else '' end as fio
	    	,to_char(slo.dateStart,'dd.mm.yyyy') ||' '|| cast(slo.entrancetime as varchar(5)) as slodateStart
	    	,to_char(slo.transferdate,'dd.mm.yyyy') ||' '|| cast(slo.transfertime as varchar(5)) as slotransferdate
	    	,to_char(slo.datefinish,'dd.mm.yyyy') ||' '|| cast(slo.dischargetime as varchar(5)) as slodatefinish
	    	,vss.name as vssname
	from medcase slo
	left join medcase sls on sls.id=slo.parent_id
	left join VocServiceStream vss on vss.id=slo.serviceStream_id
	left join statisticstub ss on ss.medcase_id=sls.id
	left join patient pat on slo.patient_id=pat.id
	left join medcase pslo on pslo.id=slo.prevmedcase_id
	left join mislpu pdep on pdep.id=pslo.department_id
	where  slo.dtype='DepartmentMedCase' ${department} 
	and 
	(slo.datestart = to_date('${dateNext}','dd.mm.yyyy') and cast('${timeSql}:00' as time)>slo.entrancetime
	or to_date('${dateNext}','dd.mm.yyyy')>slo.datestart)
	and (slo.datefinish is null 
	or slo.datefinish > to_date('${dateNext}','dd.mm.yyyy') 
	or slo.datefinish = to_date('${dateNext}','dd.mm.yyyy') and slo.dischargetime>=cast('${timeSql}' as time))
	and (slo.transferdate is null 
	or slo.transferdate > to_date('${dateNext}','dd.mm.yyyy') 
	or
	slo.transferdate = to_date('${dateNext}','dd.mm.yyyy') and slo.transfertime>=cast('${timeSql}' as time))


	order by pat.lastname,pat.firstname,pat.middlename

	      " />
	    <msh:table name="journal_priem02" viewUrl="entityShortView-stac_slo.do" action="entityParentView-stac_slo.do" idField="1">
	      <msh:tableColumn columnName="#" property="sn"/>
	      <msh:tableColumn columnName="Стат.карта" property="2"/>
	      <msh:tableColumn columnName="Фамилия имя отчество пациента" property="3"/>
	      <msh:tableColumn property="4" columnName="Дата пост"/>
	      <msh:tableColumn property="5" columnName="Дата перевода"/>
	      <msh:tableColumn property="6" columnName="Дата выписки"/>
	      <msh:tableColumn property="7" columnName="Поток обслуживания"/>
	    </msh:table>
	    </msh:sectionContent>
	    </msh:section>
	 	</td></tr></table>   
	    <%
	    }
	    if (view.equals("3")) {%>
	 	<table border=0>
	 	<tr><td valign="top" style="padding-right: 4px">
	    <msh:section>
	    <msh:sectionTitle>Реестр поступивших</msh:sectionTitle>
	    <msh:sectionContent>
	    <ecom:webQuery name="journal_priem1" nativeSql="select slo.id as sloid
	    	,ss.code as sscode
	    	,pat.lastname||' '||substring(pat.firstname,1,1)||' '||coalesce(substring(pat.middlename,1,1),'') as fio
	    	,to_char(slo.dateStart,'dd.mm.yyyy') ||' '|| cast(slo.entrancetime as varchar(5)) as slodateStart
	    	,to_char(slo.transferdate,'dd.mm.yyyy') ||' '|| cast(slo.transfertime as varchar(5)) as slotransferdate
	    	,to_char(slo.datefinish,'dd.mm.yyyy') ||' '|| cast(slo.dischargetime as varchar(5)) as slodatefinish
	    	,vss.name
	from medcase slo
	left join VocServiceStream vss on vss.id=slo.serviceStream_id
	left join medcase sls on sls.id=slo.parent_id
	left join statisticstub ss on ss.medcase_id=sls.id
	left join patient pat on slo.patient_id=pat.id
	left join medcase pslo on pslo.id=slo.prevmedcase_id
	left join mislpu pdep on pdep.id=pslo.department_id
	left join vochosptype vht on vht.id=sls.sourceHospType_id
	where  slo.dtype='DepartmentMedCase' ${department} and (slo.datestart = to_date('${dateBegin}','dd.mm.yyyy') and slo.entrancetime>=cast('${timeSql}:00' as time)
	or slo.datestart = to_date('${dateNext}','dd.mm.yyyy') and cast('${timeSql}:00' as time)>slo.entrancetime)
	and slo.prevMedCase_id is null
	and (vht.code is null or vht.code!='ALLTIMEHOSP')
	order by pat.lastname,pat.firstname,pat.middlename
	      " />
	    <msh:table name="journal_priem1" viewUrl="entityShortView-stac_slo.do" action="entityParentView-stac_slo.do" idField="1">
	      <msh:tableColumn columnName="#" property="sn"/>
	      <msh:tableColumn columnName="Стат.карта" property="2"/>
	      <msh:tableColumn columnName="Фамилия имя отчество пациента" property="3"/>
	      <msh:tableColumn property="4" columnName="Дата пост"/>
	      <msh:tableColumn property="5" columnName="Дата перевода"/>
	      <msh:tableColumn property="6" columnName="Дата выписки"/>
	      <msh:tableColumn property="7" columnName="Поток обслуживания"/>
	    </msh:table>
	    </msh:sectionContent>
	    </msh:section>    
	    </td>
	    <td valign="top" style="padding-right: 4px">
	    <msh:section>
	    <msh:sectionTitle>Реестр переведенных из других отделений</msh:sectionTitle>
	    <msh:sectionContent>
	    <ecom:webQuery name="journal_priem1" nativeSql="select slo.id as sloid
	    	,ss.code as sscode
	    	,pat.lastname||' '||substring(pat.firstname,1,1)||' '||coalesce(substring(pat.middlename,1,1),'') as fio,pdep.name as dep
	    	,to_char(slo.dateStart,'dd.mm.yyyy') ||' '|| cast(slo.entrancetime as varchar(5)) as slodateStart
	    	,to_char(slo.transferdate,'dd.mm.yyyy') ||' '|| cast(slo.transfertime as varchar(5)) as slotransferdate
	    	,to_char(slo.datefinish,'dd.mm.yyyy') ||' '|| cast(slo.dischargetime as varchar(5)) as slodatefinish
	    	,vss.name as vssname
	from medcase slo 
	left join VocServiceStream vss on vss.id=slo.serviceStream_id
	left join medcase sls on sls.id=slo.parent_id
	left join statisticstub ss on ss.medcase_id=sls.id
	left join patient pat on slo.patient_id=pat.id
	left join medcase pslo on pslo.id=slo.prevmedcase_id
	left join mislpu pdep on pdep.id=pslo.department_id
	left join vochosptype vht on vht.id=sls.sourceHospType_id
	where  slo.dtype='DepartmentMedCase' ${department} and (slo.datestart = to_date('${dateBegin}','dd.mm.yyyy') and slo.entrancetime>=cast('${timeSql}:00' as time)
	or slo.datestart = to_date('${dateNext}','dd.mm.yyyy') and cast('${timeSql}:00' as time)>slo.entrancetime)
	and slo.prevMedCase_id is not null
	order by pat.lastname,pat.firstname,pat.middlename
	      " />
	    <msh:table name="journal_priem1" viewUrl="entityShortView-stac_slo.do" action="entityParentView-stac_slo.do" idField="1">
	      <msh:tableColumn columnName="#" property="sn"/>
	      <msh:tableColumn columnName="Стат.карта" property="2"/>
	      <msh:tableColumn columnName="Фамилия имя отчество пациента" property="3"/>
	      <msh:tableColumn columnName="Отделение, из которого был переведен" property="4"/>
	      <msh:tableColumn property="5" columnName="Дата пост"/>
	      <msh:tableColumn property="6" columnName="Дата перевода"/>
	      <msh:tableColumn property="7" columnName="Дата выписки"/>
	    </msh:table>
	    </msh:sectionContent>
	    </msh:section>    
	    </td>
	    <td valign="top" style="padding-right: 4px">
	        <msh:section>
	    <msh:sectionTitle>Реестр поступивших из круглосуточного стационара</msh:sectionTitle>
	    <msh:sectionContent>
	    <ecom:webQuery name="journal_priem2" nativeSql=" select slo.id as sloid
	    	,ss.code as sscode
	    	,pat.lastname||' '||substring(pat.firstname,1,1)||' '||coalesce(substring(pat.middlename,1,1),'')||' '||case when slo.prevmedcase_id is not null then ' ('||pdep.name||')' else '' end as fio
	    	,to_char(slo.dateStart,'dd.mm.yyyy') ||' '|| cast(slo.entrancetime as varchar(5)) as slodateStart
	    	,to_char(slo.transferdate,'dd.mm.yyyy') ||' '|| cast(slo.transfertime as varchar(5)) as slotransferdate
	    	,to_char(slo.datefinish,'dd.mm.yyyy') ||' '|| cast(slo.dischargetime as varchar(5)) as slodatefinish
	    	,vss.name as vssname
	from medcase slo
	 left join VocServiceStream vss on vss.id=slo.serviceStream_id
	left join medcase sls on sls.id=slo.parent_id
	left join statisticstub ss on ss.medcase_id=sls.id
	left join patient pat on slo.patient_id=pat.id
	left join medcase pslo on pslo.id=slo.prevmedcase_id
	left join mislpu pdep on pdep.id=pslo.department_id
	left join vochosptype vht on vht.id=sls.sourceHospType_id
	where  slo.dtype='DepartmentMedCase' ${department} and (slo.datestart = to_date('${dateBegin}','dd.mm.yyyy') and slo.entrancetime>=cast('${timeSql}:00' as time)
	or slo.datestart = to_date('${dateNext}','dd.mm.yyyy') and cast('${timeSql}:00' as time)>slo.entrancetime)
	and slo.prevMedCase_id is null  
	and vht.code='ALLTIMEHOSP'
	order by pat.lastname,pat.firstname,pat.middlename
	      " />
	    <msh:table name="journal_priem2" viewUrl="entityShortView-stac_slo.do" action="entityParentView-stac_slo.do" idField="1">
	      <msh:tableColumn columnName="#" property="sn"/>
	      <msh:tableColumn columnName="Стат.карта" property="2"/>
	      <msh:tableColumn columnName="Фамилия имя отчество пациента" property="3"/>
	      <msh:tableColumn property="4" columnName="Дата пост"/>
	      <msh:tableColumn property="5" columnName="Дата перевода"/>
	      <msh:tableColumn property="6" columnName="Дата выписки"/>
	      <msh:tableColumn property="7" columnName="Поток обслуживания"/>
	    </msh:table>
	    </msh:sectionContent>
	    </msh:section>    
	    </td></tr>
	    <tr><td valign="top" style="padding-right: 4px">
	    <msh:section>
	    <msh:sectionTitle>Реестр выписанных</msh:sectionTitle>
	    <msh:sectionContent>
	    <ecom:webQuery name="journal_priem3" nativeSql="select slo.id as sloid
	    	,ss.code as sscode
	    	,pat.lastname||' '||substring(pat.firstname,1,1)||' '||coalesce(substring(pat.middlename,1,1),'')||' '||case when slo.prevmedcase_id is not null then ' ('||pdep.name||')' else '' end as fio
	    	,to_char(slo.dateStart,'dd.mm.yyyy') ||' '|| cast(slo.entrancetime as varchar(5)) as slodateStart
	    	,to_char(slo.transferdate,'dd.mm.yyyy') ||' '|| cast(slo.transfertime as varchar(5)) as slotransferdate
	    	,to_char(slo.datefinish,'dd.mm.yyyy') ||' '|| cast(slo.dischargetime as varchar(5)) as slodatefinish
	    	,vss.name as vssname
	    	,vho.name as vhoname
	from medcase slo
	 left join VocServiceStream vss on vss.id=slo.serviceStream_id
	left join medcase sls on sls.id=slo.parent_id
	left join statisticstub ss on ss.medcase_id=sls.id
	left join patient pat on slo.patient_id=pat.id
	left join medcase pslo on pslo.id=slo.prevmedcase_id
	left join mislpu pdep on pdep.id=pslo.department_id
	left join vochosptype vht on vht.id=sls.sourceHospType_id
	left join vochospitalizationoutcome vho on vho.id=sls.outcome_id
	left join vochospitalizationresult vhr on vhr.id=sls.result_id
	where  slo.dtype='DepartmentMedCase' ${department} and (slo.datefinish = to_date('${dateBegin}','dd.mm.yyyy') and slo.dischargetime>=cast('${timeSql}' as time)
	or slo.datefinish = to_date('${dateNext}','dd.mm.yyyy') and cast('${timeSql}' as time)>slo.dischargetime)
	
	and vhr.code!='11'
	order by pat.lastname,pat.firstname,pat.middlename
	      " />
	    <msh:table name="journal_priem3" viewUrl="entityShortView-stac_slo.do" action="entityParentView-stac_slo.do" idField="1">
	      <msh:tableColumn columnName="#" property="sn"/>
	      <msh:tableColumn columnName="Стат.карта" property="2"/>
	      <msh:tableColumn columnName="Фамилия имя отчество пациента" property="3"/>
	      <msh:tableColumn property="4" columnName="Дата пост"/>
	      <msh:tableColumn property="5" columnName="Дата перевода"/>
	      <msh:tableColumn property="6" columnName="Дата выписки"/>
	      <msh:tableColumn property="7" columnName="Поток обслуживания"/>
	      <msh:tableColumn property="8" columnName="Исход"/>
	    </msh:table>
	    </msh:sectionContent>
	    </msh:section>    
	    </td><td valign="top" style="padding-right: 4px">
	    <msh:section>
	    <msh:sectionTitle>Реестр переведенных в другие отделения</msh:sectionTitle>
	    <msh:sectionContent>
	    <ecom:webQuery name="journal_priem4" nativeSql="select slo.id as sloid
	    	,ss.code as sscode
	    	,pat.lastname||' '||substring(pat.firstname,1,1)||' '||coalesce(substring(pat.middlename,1,1),'')||' '||case when slo.prevmedcase_id is not null then ' ('||pdep.name||')' else '' end as fio
	    	,tdep.name as tdepname
	    	,to_char(slo.dateStart,'dd.mm.yyyy') ||' '|| cast(slo.entrancetime as varchar(5)) as slodateStart
	    	,to_char(slo.transferdate,'dd.mm.yyyy') ||' '|| cast(slo.transfertime as varchar(5)) as slotransferdate
	    	,to_char(slo.datefinish,'dd.mm.yyyy') ||' '|| cast(slo.dischargetime as varchar(5)) as slodatefinish
	    	,vss.name as vssname
	from medcase slo
	 left join VocServiceStream vss on vss.id=slo.serviceStream_id
	left join medcase sls on sls.id=slo.parent_id
	left join statisticstub ss on ss.medcase_id=sls.id
	left join patient pat on slo.patient_id=pat.id
	left join medcase pslo on pslo.id=slo.prevmedcase_id
	left join mislpu pdep on pdep.id=pslo.department_id
	left join mislpu tdep on tdep.id=slo.transferdepartment_id
	left join vochosptype vht on vht.id=sls.sourceHospType_id
	left join vochospitalizationoutcome vho on vho.id=sls.outcome_id
	left join vochospitalizationresult vhr on vhr.id=sls.result_id
	where  slo.dtype='DepartmentMedCase' ${department} and (slo.transferdate = to_date('${dateBegin}','dd.mm.yyyy') and slo.transfertime>=cast('${timeSql}' as time)
	or slo.transferdate = to_date('${dateNext}','dd.mm.yyyy') and cast('${timeSql}' as time)>slo.transfertime)
	order by pat.lastname,pat.firstname,pat.middlename
	      " />
	    <msh:table name="journal_priem4" viewUrl="entityShortView-stac_slo.do" action="entityParentView-stac_slo.do" idField="1">
	      <msh:tableColumn columnName="#" property="sn"/>
	      <msh:tableColumn columnName="Стат.карта" property="2"/>
	      <msh:tableColumn columnName="Фамилия имя отчество пациента" property="3"/>
	      <msh:tableColumn columnName="Куда переведен" property="4"/>
	      <msh:tableColumn property="5" columnName="Дата пост"/>
	      <msh:tableColumn property="6" columnName="Дата перевода"/>
	      <msh:tableColumn property="7" columnName="Дата выписки"/>
	      <msh:tableColumn property="8" columnName="Поток обслуживания"/>
	    </msh:table>
	    </msh:sectionContent>
	    </msh:section>    
	    </td><td valign="top">
	    <msh:section>
	    <msh:sectionTitle>Реестр умерших</msh:sectionTitle>
	    <msh:sectionContent>
	    <ecom:webQuery name="journal_priem4" nativeSql="select slo.id as sloid
	    	,ss.code as sscode
	    	,pat.lastname||' '||substring(pat.firstname,1,1)||' '||coalesce(substring(pat.middlename,1,1),'')||' '||case when slo.prevmedcase_id is not null then ' ('||pdep.name||')' else '' end as fio
	    	,slo.dateStart ||' '|| cast(slo.entrancetime as varchar(5)) as slodateStart
	    	,slo.transferdate ||' '|| cast(slo.transfertime as varchar(5)) as slotransferdate
	    	,slo.datefinish ||' '|| cast(slo.dischargetime as varchar(5)) as slodatefinish
	    	,vss.name as vssname
	from medcase slo
	 left join VocServiceStream vss on vss.id=slo.serviceStream_id
	left join medcase sls on sls.id=slo.parent_id
	left join statisticstub ss on ss.medcase_id=sls.id
	left join patient pat on slo.patient_id=pat.id
	left join medcase pslo on pslo.id=slo.prevmedcase_id
	left join mislpu pdep on pdep.id=pslo.department_id
	left join vochosptype vht on vht.id=sls.sourceHospType_id
	left join vochospitalizationoutcome vho on vho.id=sls.outcome_id
	left join vochospitalizationresult vhr on vhr.id=sls.result_id
	where  slo.dtype='DepartmentMedCase' ${department} and (slo.datefinish = to_date('${dateBegin}','dd.mm.yyyy') and slo.dischargetime>=cast('${timeSql}' as time)
	or slo.datefinish = to_date('${dateNext}','dd.mm.yyyy') and cast('${timeSql}' as time)>slo.dischargetime)
	and vhr.code='11'
	order by pat.lastname,pat.firstname,pat.middlename
	      " />
	    <msh:table name="journal_priem4" viewUrl="entityShortView-stac_slo.do" action="entityParentView-stac_slo.do" idField="1">
	      <msh:tableColumn columnName="#" property="sn"/>
	      <msh:tableColumn columnName="Стат.карта" property="2"/>
	      <msh:tableColumn columnName="Фамилия имя отчество пациента" property="3"/>
	      <msh:tableColumn property="4" columnName="Дата пост"/>
	      <msh:tableColumn property="5" columnName="Дата перевода"/>
	      <msh:tableColumn property="6" columnName="Дата выписки"/>
	      <msh:tableColumn property="7" columnName="Поток обслуживания"/>
	    </msh:table>
	    </msh:sectionContent>
	    </msh:section>    
	    </td></tr></table>
	    
	    <%}} else {
	    	view="4" ;
	    }
	    	
	    	if (view.equals("4")) {
	    		%>
	    		
	    		
	    		
	    		
	    		
	    		
	    		
	    		
	    		
	    		
	    		    <msh:section>
	    <msh:sectionTitle>
	    <ecom:webQuery name="journal_priem" nameFldSql="journal_priem_sql" nativeSql="select '${dateBegin}:'||lpu.id||'&dateBegin=${dateBegin}&typeView=1&department='||lpu.id,lpu.name,list(distinct vbst.name) as vbstname,list(distinct vss.name) as vssname
	,count(distinct case when (slo.datestart = to_date('${dateBegin}','dd.mm.yyyy') and cast('${timeSql}:00' as time)>slo.entrancetime
	or to_date('${dateBegin}','dd.mm.yyyy')>slo.datestart)
	and (slo.datefinish is null 
	or slo.datefinish > to_date('${dateBegin}','dd.mm.yyyy') 
	or slo.datefinish = to_date('${dateBegin}','dd.mm.yyyy') and slo.dischargetime>=cast('${timeSql}' as time))
	and (slo.transferdate is null 
	or slo.transferdate > to_date('${dateBegin}','dd.mm.yyyy') 
	or
	slo.transferdate = to_date('${dateBegin}','dd.mm.yyyy') and slo.transfertime>=cast('${timeSql}' as time))

	 then slo.id else null end)
	as cnt5CurrentFrom
	,count(distinct case when slo.prevmedcase_id is null and (slo.datestart = to_date('${dateBegin}','dd.mm.yyyy') and slo.entrancetime>=cast('${timeSql}:00' as time)
	or slo.datestart = to_date('${dateNext}','dd.mm.yyyy') and cast('${timeSql}' as time)>slo.entrancetime) then slo.id else null end)
	as cnt6Entrance
	,count(distinct case when slo.prevmedcase_id is null and vht.code='DAYTIMEHOSP' and(slo.datestart = to_date('${dateBegin}','dd.mm.yyyy') and slo.entrancetime>=cast('${timeSql}:00' as time)
	or slo.datestart = to_date('${dateNext}','dd.mm.yyyy') and cast('${timeSql}' as time)>slo.entrancetime) then slo.id else null end)
	as cnt7EntranceDayHosp
	,count(distinct case when slo.prevmedcase_id is null and(slo.datestart = to_date('${dateBegin}','dd.mm.yyyy') and slo.entrancetime>=cast('${timeSql}:00' as time)
	or slo.datestart = to_date('${dateNext}','dd.mm.yyyy') and cast('${timeSql}' as time)>slo.entrancetime) 
	and (
	cast(to_char(slo.datestart,'yyyy') as int)-cast(to_char(pat.birthday,'yyyy') as int)
	+case when (cast(to_char(slo.datestart, 'mm') as int)-cast(to_char(pat.birthday, 'mm') as int)) <0 then -1 when (cast(to_char(slo.datestart,'dd') as int) - cast(to_char(pat.birthday,'dd') as int)<0) and ((cast(to_char(slo.datestart, 'mm') as int)-cast(to_char(pat.birthday, 'mm') as int)-1)<0)  then -1 else 0 end
	)<18
	then slo.id else null end)

	as cnt9EntranceTo17
	,count(distinct case when slo.prevmedcase_id is null and (slo.datestart = to_date('${dateBegin}','dd.mm.yyyy') and slo.entrancetime>=cast('${timeSql}:00' as time)
	or slo.datestart = to_date('${dateNext}','dd.mm.yyyy') and cast('${timeSql}' as time)>slo.entrancetime) 
	and 
	60<=(
	cast(to_char(slo.datestart,'yyyy') as int)-cast(to_char(pat.birthday,'yyyy') as int)
	+case when (cast(to_char(slo.datestart, 'mm') as int)-cast(to_char(pat.birthday, 'mm') as int)) <0 then -1 when (cast(to_char(slo.datestart,'dd') as int) - cast(to_char(pat.birthday,'dd') as int)<0) and ((cast(to_char(slo.datestart, 'mm') as int)-cast(to_char(pat.birthday, 'mm') as int)-1)<0)  then -1 else 0 end
	)
	then slo.id else null end)
	as cnt10EntranceFrom60
	,count(distinct case when slo.prevmedcase_id is not null and (slo.datestart = to_date('${dateBegin}','dd.mm.yyyy') and slo.entrancetime>=cast('${timeSql}:00' as time)
	or slo.datestart = to_date('${dateNext}','dd.mm.yyyy') and cast('${timeSql}' as time)>slo.entrancetime) then slo.id else null end)
	as cnt11TransferOutOtherDepartment
	,count(distinct case when (slo.transferdate = to_date('${dateBegin}','dd.mm.yyyy') and slo.transfertime>=cast('${timeSql}' as time)
	or slo.transferdate = to_date('${dateNext}','dd.mm.yyyy') and cast('${timeSql}' as time)>slo.transfertime) then slo.id else null end)
	as cnt12TransferInOtherDepartment
	,count(distinct case when vhr.code!='11' and (slo.datefinish = to_date('${dateBegin}','dd.mm.yyyy') and slo.dischargetime>=cast('${timeSql}' as time)
	or slo.datefinish = to_date('${dateNext}','dd.mm.yyyy') and cast('${timeSql}' as time)>slo.dischargetime) then slo.id else null end)
	as cnt13Finished
	,count(distinct case when vho.code='4' and vhr.code!='11' and (slo.datefinish = to_date('${dateBegin}','dd.mm.yyyy') and slo.dischargetime>=cast('${timeSql}' as time)
	or slo.datefinish = to_date('${dateNext}','dd.mm.yyyy') and cast('${timeSql}' as time)>slo.dischargetime) then slo.id else null end)
	as cnt14FinishedOtherHospital
	,count(distinct case when vho.code='3' and vhr.code!='11' and (slo.datefinish = to_date('${dateBegin}','dd.mm.yyyy') and slo.dischargetime>=cast('${timeSql}' as time)
	or slo.datefinish = to_date('${dateNext}','dd.mm.yyyy') and cast('${timeSql}' as time)>slo.dischargetime) then slo.id else null end)
	as cnt15FinishedHourHospital
	,count(distinct case when vho.code='2' and vhr.code!='11' and (slo.datefinish = to_date('${dateBegin}','dd.mm.yyyy') and slo.dischargetime>=cast('${timeSql}' as time)
	or slo.datefinish = to_date('${dateNext}','dd.mm.yyyy') and cast('${timeSql}' as time)>slo.dischargetime) then slo.id else null end)
	as cnt16FinishedDayHospital
	,count(distinct case when vhr.code='11' and (slo.datefinish = to_date('${dateBegin}','dd.mm.yyyy') and slo.dischargetime>=cast('${timeSql}' as time)
	or slo.datefinish = to_date('${dateNext}','dd.mm.yyyy') and cast('${timeSql}' as time)>slo.dischargetime) then slo.id else null end)
	as cnt17Death
	,

	count(distinct case when 
			(
				slo.transferdate is null
				or slo.transferdate > to_date('${dateNext}','dd.mm.yyyy')
				or slo.transferdate = to_date('${dateNext}','dd.mm.yyyy') and slo.transfertime>=cast('${timeSql}' as time)
			) and (
				slo.datefinish is null or
				slo.datefinish > to_date('${dateNext}','dd.mm.yyyy')
				or slo.datefinish = to_date('${dateNext}','dd.mm.yyyy') and slo.dischargetime>=cast('${timeSql}' as time)
			)
		 then slo.id else null end
	)
	 as cnt18CurrentTo
	 
	,count(distinct case when sls.hotelServices='1' and 
	 (
				slo.transferdate is null
				or slo.transferdate > to_date('${dateNext}','dd.mm.yyyy')
				or slo.transferdate = to_date('${dateNext}','dd.mm.yyyy') and slo.transfertime>=cast('${timeSql}' as time)
			) and (
				slo.datefinish is null or
				slo.datefinish > to_date('${dateNext}','dd.mm.yyyy')
				or slo.datefinish = to_date('${dateNext}','dd.mm.yyyy') and slo.dischargetime>=cast('${timeSql}' as time)
			) then slo.id else null end)
	 as cnt19CurrentMother
	,cast((select sum(bf1.amount) from BedFund bf1 where bf1.lpu_id=lpu.id and bf1.dateStart<=to_date('${dateNext}','dd.mm.yyyy')
	and (bf1.dateFinish is null or bf1.dateFinish>=to_date('${dateNext}','dd.mm.yyyy')) ) as int) as cnt20BedFund
	 from medcase slo
	 left join patient pat on pat.id=slo.patient_id
	 left join mislpu lpu on lpu.id=slo.department_id
	 left join medcase sls on sls.id=slo.parent_id
	 left join vochospitalizationoutcome vho on vho.id=sls.outcome_id
	 left join vochospitalizationresult vhr on vhr.id=sls.result_id
	left join bedfund bf on bf.id=slo.bedfund_id
	left join vocbedsubtype vbst on vbst.id=bf.bedsubtype_id
	left join vocservicestream vss on vss.id=bf.servicestream_id
	left join vochosptype vht on vht.id=sls.sourceHospType_id
	where 
	slo.dtype='DepartmentMedCase' 
	 and (to_date('${dateBegin}','dd.mm.yyyy')>=slo.datestart or
	slo.datestart = to_date('${dateNext}','dd.mm.yyyy') and cast('${timeSql}' as time)>slo.entrancetime)
	and (slo.datefinish is null or slo.datefinish > to_date('${dateBegin}','dd.mm.yyyy') or 
	slo.datefinish = to_date('${dateBegin}','dd.mm.yyyy') and slo.dischargetime>=cast('${timeSql}' as time) or
	slo.datefinish = to_date('${dateNext}','dd.mm.yyyy') and cast('${timeSql}' as time)>slo.dischargetime)
	and (slo.transferdate is null or slo.transferdate > to_date('${dateBegin}','dd.mm.yyyy') or
	slo.transferdate = to_date('${dateBegin}','dd.mm.yyyy') and slo.transfertime>=cast('${timeSql}' as time) or
	slo.transferdate = to_date('${dateNext}','dd.mm.yyyy') and cast('${timeSql}' as time)>slo.transfertime)
	${department}
	group by lpu.id,lpu.name
	      " />
	    <form action="print-stac_report007_swod.do" method="post" target="_blank">
	    007/у-02 форма. Свод по отделениям
	    <input type='hidden' name="sqlText" id="sqlText" value="${journal_priem_sql}"> 
	    <input type='hidden' name="sqlInfo" id="sqlInfo" value="Свод по отделениям 007 формы за ${param.dateBegin}.">
	    <input type='hidden' name="sqlColumn" id="sqlColumn" value="${groupName}">
	    <input type='hidden' name="s" id="s" value="PrintService">
	    <input type='hidden' name="m" id="m" value="printNativeQuery">
	    <input type="submit" value="Печать"> 
	    </form>     
	    </msh:sectionTitle>
	    <msh:sectionContent>
	    <msh:table name="journal_priem" 
	    viewUrl="stac_report_007.do?short=Short" 
	    action="stac_report_007.do" idField="1">
	      <msh:tableColumn columnName="#" property="sn" />
	      <msh:tableColumn columnName="отделение" property="2" />
	      <msh:tableColumn columnName="профили коек" property="3" />
	      <msh:tableColumn columnName="потоки обслуживания" property="4" />
	      <msh:tableColumn columnName="число коек" property="19" />
	      <msh:tableColumn isCalcAmount="true" columnName="Состоит на начало истекших суток" property="5" />
	      <msh:tableColumn isCalcAmount="true" columnName="поступило всего" property="6" />
	      <msh:tableColumn isCalcAmount="true" columnName="в т.ч. из дневного стационар" property="7" />
	      <msh:tableColumn isCalcAmount="true" columnName="0-17 лет" property="8" />
	      <msh:tableColumn isCalcAmount="true" columnName="60 лет и старше" property="9" />
	      <msh:tableColumn isCalcAmount="true" columnName="переведено из других отд" property="10" />
	      <msh:tableColumn isCalcAmount="true" columnName="переведено в другие отд" property="11" />
	      <msh:tableColumn isCalcAmount="true" columnName="выписано" property="12" />
	      <msh:tableColumn isCalcAmount="true" columnName="в другие стационары" property="13" />
	      <msh:tableColumn isCalcAmount="true" columnName="в круглосуточный стационар" property="14" />
	      <msh:tableColumn isCalcAmount="true" columnName="в дневной стационар" property="15" />
	      <msh:tableColumn isCalcAmount="true" columnName="умерло" property="16" />
	      <msh:tableColumn isCalcAmount="true" columnName="состоит всего" property="17" />
	      <msh:tableColumn isCalcAmount="true" columnName="состоит матерей" property="18" />
	    </msh:table>
	    </msh:sectionContent>
	    </msh:section>
	    		
	    		<%
	    	}
	    	
	    	}
	
} else {
	  out.println("<i>Выберите параметры и нажмите кнопку \"Найти\"</i>");
} %>
  
    

  </tiles:put>
</tiles:insert>

