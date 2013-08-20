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

<tiles:insert page="/WEB-INF/tiles/main${param.short}Layout.jsp" flush="true" >

  <tiles:put name="title" type="string">
    <msh:title guid="helloItle-123" mainMenu="Journals">Форма 16у-02</msh:title>
  </tiles:put>
  <tiles:put name="side" type="string">
  	<tags:stac_journal currentAction="stac_report016"/>
  </tiles:put>
  <tiles:put name="body" type="string">
  
  <% 
  boolean sh = true ;
  //String shS =  ;
  if (request.getParameter("short")!=null) {
	  sh=false ;
  }
  ActionUtil.updateParameter("Report16","typeView","4", request) ;
  if (sh) {
	 
  %>
    <msh:form action="/stac_report_016.do" defaultField="dateBegin" disableFormDataConfirm="true" method="GET" guid="d7b31bc2-38f0-42cc-8d6d-19395273168f">
    <input type="hidden" name="s" id="s" value="HospitalPrintReport" />
    <input type="hidden" name="m" id="m" value="printReport016" />
    <input type="hidden" name="id" id="id" value=""/>
    <msh:panel>
      <msh:row>
        <msh:separator label="Параметры поиска" colSpan="7" guid="15c6c628-8aab-4c82-b3d8-ac77b7b3f700" />
      </msh:row>
      <msh:row>
        <td class="label" title="Просмотр данных (typeView)" colspan="1"><label for="typeViewName" id="typeViewLabel">Отобразить:</label></td>
        <td onclick="this.childNodes[1].checked='checked';">
        	<input type="radio" name="typeView" value="1">  16 форма
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
      <msh:row guid="Дата">
        <msh:textField fieldColSpan="2" property="dateBegin" label="Дата начала периода" guid="8d7ef035-1273-4839-a4d8-1551c623caf1" />
        <msh:textField fieldColSpan="2" property="dateEnd" label="Дата окончания периода" guid="8d7ef035-1273-4839-a4d8-1551c623caf1" />
      </msh:row>
        <msh:row>
        	<msh:autoComplete property="department" fieldColSpan="4" horizontalFill="true" label="Отделение" vocName="lpu"/>
        </msh:row>
      <msh:row>
           <td colspan="11">
            <input type="submit" onclick="find()" value="Найти" />
            <input type="submit" onclick="print()" value="Печать" />
          </td>
      </msh:row>
    </msh:panel>
    </msh:form>
    
       <script type='text/javascript'>
    
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
    	frm.action='stac_report_016.do' ;
    }

    function print() {
    	var frm = document.forms[0] ;
    	frm.m.value="printReport016" ;
    	frm.target='_blank' ;
    	frm.action='print-stac_report016.do' ;

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
	
	String dateEnd = (String)request.getParameter("dateEnd") ;

    if (dateEnd==null || dateEnd.equals("")) dateEnd=date ;
    request.setAttribute("dateBegin", date) ;
    request.setAttribute("dateEnd", dateEnd) ;
    String timeSql="09:00" ;
    String periodEntrance = ReportParamUtil.getPeriodByDate(false,true, "slo.dateStart", "slo.entranceTime", date, dateEnd, timeSql) ;
    String periodTransfer = ReportParamUtil.getPeriodByDate(false,true, "slo.transferDate", "slo.transferTime", date, dateEnd, timeSql) ;
    String periodDischarge = ReportParamUtil.getPeriodByDate(false,true, "slo.dateFinish", "slo.dischargeTime", date, dateEnd, timeSql) ;
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
    request.setAttribute("dateNextBegin", date1) ;
    cal.add(Calendar.DAY_OF_MONTH, -2) ;
    date1=format.format(cal.getTime()) ;
    request.setAttribute("datePrevBegin", date1) ;
	
    dat = DateFormat.parseDate(dateEnd) ;
    cal.setTime(dat) ;
    cal.add(Calendar.DAY_OF_MONTH, 1) ;
    date1=format.format(cal.getTime()) ;
    request.setAttribute("dateNextEnd", date1) ;    
    
	
    String bedFund = request.getParameter("bedFund") ;
	if (bedFund!=null && !bedFund.equals("") && !bedFund.equals("0")) {
		%>

	    <msh:section>
	    <msh:sectionTitle>016/у-02 форма. Реестр пациентов</msh:sectionTitle>
	    <msh:sectionContent>
	    <ecom:webQuery name="journal_priem" nativeSql="select 
	    slo.id
	    ,ss.code as sscode
	    ,pat.lastname||' '||pat.middlename||' '||pat.firstname||' '||to_char(pat.birthday,'dd.mm.yyyy') as patfio
	    ,
	    vbst.name as vbstname,case when vss.id!=vss1.id then '<font color=red>'||vss1.name||'</font>' else vss.name end as vssname
	, case when 
	(slo.datestart = to_date('${dateBegin}','dd.mm.yyyy') and cast('09:00:00' as time)>slo.entrancetime
	or to_date('${dateBegin}','dd.mm.yyyy')>slo.datestart)
	and (slo.datefinish is null 
	or slo.datefinish > to_date('${dateBegin}','dd.mm.yyyy') 
	or slo.datefinish = to_date('${dateBegin}','dd.mm.yyyy') and slo.dischargetime>=cast('09:00' as time))
	and (slo.transferdate is null 
	or slo.transferdate > to_date('${dateBegin}','dd.mm.yyyy') 
	or
	slo.transferdate = to_date('${dateBegin}','dd.mm.yyyy') and slo.transfertime>=cast('09:00' as time))

	 then 'X' else null end
	as cnt5CurrentFrom
	, case when slo.prevmedcase_id is null and (slo.datestart = to_date('${dateBegin}','dd.mm.yyyy') and slo.entrancetime>=cast('09:00:00' as time)
	or slo.dateStart between to_date('${dateNextBegin}','dd.mm.yyyy') and to_date('${dateEnd}','dd.mm.yyyy')
	or slo.datestart = to_date('${dateNextEnd}','dd.mm.yyyy') and cast('09:00' as time)>slo.entrancetime) then 'X' else null end
	as cnt6Entrance
	,case when slo.prevmedcase_id is null and vht.code='DAYTIMEHOSP' and(slo.datestart = to_date('${dateBegin}','dd.mm.yyyy') and slo.entrancetime>=cast('09:00:00' as time)
	or slo.dateStart between to_date('${dateNextBegin}','dd.mm.yyyy') and to_date('${dateEnd}','dd.mm.yyyy')
	or slo.datestart = to_date('${dateNextEnd}','dd.mm.yyyy') and cast('09:00' as time)>slo.entrancetime) then 'X' else null end
	as cnt7EntranceDayHosp
	,case when slo.prevmedcase_id is null and (slo.datestart = to_date('${dateBegin}','dd.mm.yyyy') and slo.entrancetime>=cast('09:00:00' as time)
	or slo.dateStart between to_date('${dateNextBegin}','dd.mm.yyyy') and to_date('${dateEnd}','dd.mm.yyyy')
	or slo.datestart = to_date('${dateNextEnd}','dd.mm.yyyy') and cast('09:00' as time)>slo.entrancetime) 
	and (ad1.domen=5 or ad2.domen=5)
	then 'X' else null end
	as cnt8EntranceVillagers
	,case when slo.prevmedcase_id is null and (slo.datestart = to_date('${dateBegin}','dd.mm.yyyy') and slo.entrancetime>=cast('09:00:00' as time)
	or slo.dateStart between to_date('${dateNextBegin}','dd.mm.yyyy') and to_date('${dateEnd}','dd.mm.yyyy')
	or slo.datestart = to_date('${dateNextEnd}','dd.mm.yyyy') and cast('09:00' as time)>slo.entrancetime)
	and (
	cast(to_char(slo.datestart,'yyyy') as int)-cast(to_char(pat.birthday,'yyyy') as int)
	+case when (cast(to_char(slo.datestart, 'mm') as int)-cast(to_char(pat.birthday, 'mm') as int)) <0 then -1 when (cast(to_char(slo.datestart,'dd') as int) - cast(to_char(pat.birthday,'dd') as int)<0) and ((cast(to_char(slo.datestart, 'mm') as int)-cast(to_char(pat.birthday, 'mm') as int)-1)<0)  then -1 else 0 end
	)<18
	then 'X' else null end

	as cnt9EntranceTo17
	, case when slo.prevmedcase_id is null and vht.code='DAYTIMEHOSP' and (slo.datestart = to_date('${dateBegin}','dd.mm.yyyy') and slo.entrancetime>=cast('09:00:00' as time)
	or slo.dateStart between to_date('${dateNextBegin}','dd.mm.yyyy') and to_date('${dateEnd}','dd.mm.yyyy')
	or slo.datestart = to_date('${dateNextEnd}','dd.mm.yyyy') and cast('09:00' as time)>slo.entrancetime) 
	and 
	60<=(
	cast(to_char(slo.datestart,'yyyy') as int)-cast(to_char(pat.birthday,'yyyy') as int)
	+case when (cast(to_char(slo.datestart, 'mm') as int)-cast(to_char(pat.birthday, 'mm') as int)) <0 then -1 when (cast(to_char(slo.datestart,'dd') as int) - cast(to_char(pat.birthday,'dd') as int)<0) and ((cast(to_char(slo.datestart, 'mm') as int)-cast(to_char(pat.birthday, 'mm') as int)-1)<0)  then -1 else 0 end
	)
	then 'X' else null end
	as cnt10EntranceFrom60
	, case when slo.prevmedcase_id is not null and (slo.datestart between to_date('${dateNextBegin}','dd.mm.yyyy') and to_date('${dateEnd}','dd.mm.yyyy')
	 or slo.datestart = to_date('${dateBegin}','dd.mm.yyyy') and slo.entrancetime>=cast('09:00:00' as time)
	or slo.datestart = to_date('${dateNextEnd}','dd.mm.yyyy') and cast('09:00' as time)>slo.entrancetime) then 'X' else null end
	as cnt11TransferOutOtherDepartment
	, case when  (slo.transferdate between to_date('${dateNextBegin}','dd.mm.yyyy') and to_date('${dateEnd}','dd.mm.yyyy')
	 or slo.transferdate = to_date('${dateBegin}','dd.mm.yyyy') and slo.transfertime>=cast('09:00:00' as time)
	or slo.transferdate = to_date('${dateNextEnd}','dd.mm.yyyy') and cast('09:00' as time)>slo.transfertime) then 'X' else null end 
	as cnt12TransferInOtherDepartment
	, case when vhr.code!='11' and (slo.dateFinish between to_date('${dateNextBegin}','dd.mm.yyyy') and to_date('${dateEnd}','dd.mm.yyyy')
	 or slo.dateFinish = to_date('${dateBegin}','dd.mm.yyyy') and slo.dischargetime>=cast('09:00:00' as time)
	or slo.dateFinish = to_date('${dateNextEnd}','dd.mm.yyyy') and cast('09:00' as time)>slo.dischargetime) then 'X' else null end 
	as cnt13Finished
	, case when vho.code='4' and vhr.code!='11' and (slo.dateFinish between to_date('${dateNextBegin}','dd.mm.yyyy') and to_date('${dateEnd}','dd.mm.yyyy')
	 or slo.dateFinish = to_date('${dateBegin}','dd.mm.yyyy') and slo.dischargetime>=cast('09:00:00' as time)
	or slo.dateFinish = to_date('${dateNextEnd}','dd.mm.yyyy') and cast('09:00' as time)>slo.dischargetime) then 'X' else null end 
	as cnt14FinishedOtherHospital
	, case when vho.code='3' and vhr.code!='11' and (slo.dateFinish between to_date('${dateNextBegin}','dd.mm.yyyy') and to_date('${dateEnd}','dd.mm.yyyy')
	 or slo.dateFinish = to_date('${dateBegin}','dd.mm.yyyy') and slo.dischargetime>=cast('09:00:00' as time)
	or slo.dateFinish = to_date('${dateNextEnd}','dd.mm.yyyy') and cast('09:00' as time)>slo.dischargetime) then 'X' else null end 
	as cnt15FinishedHourHospital
	, case when vho.code='2' and vhr.code!='11' and (slo.dateFinish between to_date('${dateNextBegin}','dd.mm.yyyy') and to_date('${dateEnd}','dd.mm.yyyy')
	 or slo.dateFinish = to_date('${dateBegin}','dd.mm.yyyy') and slo.dischargetime>=cast('09:00:00' as time)
	or slo.dateFinish = to_date('${dateNextEnd}','dd.mm.yyyy') and cast('09:00' as time)>slo.dischargetime) then 'X' else null end 
	as cnt16FinishedDayHospital
	, case when vhr.code='11' and (slo.dateFinish between to_date('${dateNextBegin}','dd.mm.yyyy') and to_date('${dateEnd}','dd.mm.yyyy')
	 or slo.dateFinish = to_date('${dateBegin}','dd.mm.yyyy') and slo.dischargetime>=cast('09:00:00' as time)
	or slo.dateFinish = to_date('${dateNextEnd}','dd.mm.yyyy') and cast('09:00' as time)>slo.dischargetime) then 'X' else null end 
	as cnt17Death
	,

	 case when 
			(
				slo.transferdate is null
				or slo.transferdate > to_date('${dateNextEnd}','dd.mm.yyyy')
				or slo.transferdate = to_date('${dateNextEnd}','dd.mm.yyyy') and slo.transfertime>=cast('09:00' as time)
			) and (
				slo.datefinish is null or
				slo.datefinish > to_date('${dateNextEnd}','dd.mm.yyyy')
				or slo.datefinish = to_date('${dateNextEnd}','dd.mm.yyyy') and slo.dischargetime>=cast('09:00' as time)
			)
		 then 'X' else null end
	
	 as cnt18CurrentTo
	 
	, case when sls.hotelServices='1' and 
	 (
				slo.transferdate is null
				or slo.transferdate > to_date('${dateNextEnd}','dd.mm.yyyy')
				or slo.transferdate = to_date('${dateNextEnd}','dd.mm.yyyy') and slo.transfertime>=cast('09:00' as time)
			) and (
				slo.datefinish is null or
				slo.datefinish > to_date('${dateNextEnd}','dd.mm.yyyy')
				or slo.datefinish = to_date('${dateNextEnd}','dd.mm.yyyy') and slo.dischargetime>=cast('09:00' as time)
			) then 'X' else null end
	 as cnt19CurrentMother
	 
	 	,	 	
	 	
	case when 
		slo.dateFinish between to_date('${dateBegin}','dd.mm.yyyy')+2 and to_date('${dateEnd}','dd.mm.yyyy')
			or slo.dateFinish = to_date('${dateNextBegin}','dd.mm.yyyy') and slo.dischargetime>=cast('09:00:00' as time)
			or slo.dateFinish = to_date('${dateNextEnd}','dd.mm.yyyy') and cast('09:00' as time)>slo.dischargetime		
		then slo.dateFinish
			-case
				when slo.dateFinish=to_date('${dateEnd}','dd.mm.yyyy') and slo.dischargetime>=cast('09:00:00' as time) then 1
				when slo.dateFinish = to_date('${dateNextEnd}','dd.mm.yyyy') and cast('09:00' as time)>slo.dischargetime then 2
				when cast('09:00' as time)>slo.dischargetime then 2 
				else 1 end
		when slo.dateFinish=to_date('${dateBegin}','dd.mm.yyyy') then slo.dateFinish-1
		when slo.dateFinish=to_date('${dateNextBegin}','dd.mm.yyyy') and cast('09:00:00' as time)>slo.dischargetime then slo.dateFinish-2
	     when 
		slo.transferDate between to_date('${dateBegin}','dd.mm.yyyy')+2 and to_date('${dateEnd}','dd.mm.yyyy')
			or slo.transferDate = to_date('${dateNextBegin}','dd.mm.yyyy') and slo.transfertime>=cast('09:00:00' as time)
			or slo.transferDate = to_date('${dateNextEnd}','dd.mm.yyyy') and cast('09:00' as time)>slo.transfertime		
		then slo.transferDate
			-case 
			when slo.transferDate = to_date('${dateEnd}','dd.mm.yyyy') and slo.transfertime>=cast('09:00:00' as time) then 1
			when slo.transferdate = to_date('${dateNextEnd}','dd.mm.yyyy') and cast('09:00' as time)>slo.transfertime then 2
			when cast('09:00' as time)>slo.transfertime then 2 else 1 end
		when slo.transferDate=to_date('${dateBegin}','dd.mm.yyyy') then slo.transferDate-1
		when slo.transferDate=to_date('${dateNextBegin}','dd.mm.yyyy') and cast('09:00:00' as time)>slo.transfertime then slo.transferDate-2
		else to_date('${dateEnd}','dd.mm.yyyy')
		
		end
	-
	case when
		(slo.datestart = to_date('${dateBegin}','dd.mm.yyyy') and cast('09:00:00' as time)>slo.entrancetime
		or to_date('${dateBegin}','dd.mm.yyyy')>slo.datestart)
		and (slo.datefinish is null 
		or slo.datefinish > to_date('${dateBegin}','dd.mm.yyyy') 
		or slo.datefinish = to_date('${dateBegin}','dd.mm.yyyy') and slo.dischargetime>=cast('09:00' as time))
		and (slo.transferdate is null 
		or slo.transferdate > to_date('${dateBegin}','dd.mm.yyyy') 
		or
		slo.transferdate = to_date('${dateBegin}','dd.mm.yyyy') and slo.transfertime>=cast('09:00' as time))
	then to_date('${datePrevBegin}','dd.mm.yyyy')
	else slo.dateStart - case 
		when slo.datestart = to_date('${dateBegin}','dd.mm.yyyy') and slo.entrancetime>=cast('09:00' as time) then 1
		when slo.datestart = to_date('${dateNextBegin}','dd.mm.yyyy') and cast('09:00' as time)>slo.entrancetime then 2
		when slo.datestart = to_date('${dateEnd}','dd.mm.yyyy') and slo.entrancetime>=cast('09:00' as time) then 1
		when slo.datestart = to_date('${dateNextEnd}','dd.mm.yyyy') and cast('09:00' as time)>slo.entrancetime then 2
		when cast('09:00' as time)>slo.entrancetime then 2 else 1 end
	end
	
	
	
	
	
	
	 as cntDays

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
	LEFT JOIN Address2 ad2 on ad2.addressId=ad1.parent_addressId
	where 
		slo.dtype='DepartmentMedCase' 
		${department}
	and (to_date('${dateNextEnd}','dd.mm.yyyy')>slo.datestart
	or to_date('${dateNextEnd}','dd.mm.yyyy')=slo.dateStart and cast('09:00' as time)>slo.entrancetime
	)
	and (slo.datefinish is null 
	or
	slo.datefinish>to_date('${dateBegin}','dd.mm.yyyy')
	or to_date('${dateBegin}','dd.mm.yyyy')=slo.datefinish and slo.dischargetime>=cast('09:00' as time)
	)
	and (slo.transferdate is null 
	or slo.transferdate > to_date('${dateBegin}','dd.mm.yyyy')
	or to_date('${dateBegin}','dd.mm.yyyy')=slo.transferdate and slo.transfertime>=cast('09:00' as time)
	)

	
	and slo.bedfund_id=${param.bedFund}
	order by pat.lastname,pat.firstname,pat.middlename
	      " />
	    <msh:table name="journal_priem" viewUrl="entityShortView-stac_slo.do" action="entityParentView-stac_slo.do" idField="1" guid="b621e361-1e0b-4ebd-9f58-b7d919b45bd6">
	      <msh:tableColumn columnName="#" property="sn" />
	      <msh:tableColumn property="2" columnName="№СК"/>
	      <msh:tableColumn property="3" columnName="ФИО пациента"/>
	      <msh:tableColumn columnName="профиль коек" property="4" />
	      <msh:tableColumn columnName="поток обслуж." property="5" />
	      <msh:tableColumn columnName="Состоит на начало отчетного периода" property="6" />
	      <msh:tableColumn columnName="пост. всего" property="7" />
	      <msh:tableColumn columnName="в т.ч. из дневного стационар" property="8" />
	      <msh:tableColumn columnName="с.ж" property="9" />
	      <msh:tableColumn columnName="до 17 лет" property="10" />
	      <msh:tableColumn columnName="60 лет и старше" property="11" />
	      <msh:tableColumn columnName="перев. из других отд" property="12" />
	      <msh:tableColumn columnName="перев. в другие отд" property="13" />
	      <msh:tableColumn columnName="выписано" property="14" />
	      <msh:tableColumn columnName="в др. стац." property="15" />
	      <msh:tableColumn columnName="в кругл. стац." property="16" />
	      <msh:tableColumn columnName="в дн. стац." property="17" />
	      <msh:tableColumn columnName="умерло" property="18" />
	      <msh:tableColumn columnName="состоит всего" property="19" />
	      <msh:tableColumn columnName="состоит матерей" property="20" />
	      <msh:tableColumn isCalcAmount="true" columnName="к/дней" property="21" />
	    </msh:table>
	    </msh:sectionContent>
	    </msh:section>

	<%		
		} else {
		    	
		    if ( dep!=null && !dep.equals("") && !dep.equals("0")) { 	
		    	
		    if (view.equals("1")) {
	    	%>
	    
	    <msh:section>
	    <msh:sectionTitle>016/у-02 форма</msh:sectionTitle>
	    <msh:sectionContent>
	    <ecom:webQuery name="journal_priem" nativeSql="select 
	    '&department=${param.department}&dateBegin=${dateBegin}&dateEnd=${dateEnd}&bedFund='||slo.bedfund_id as vbstid,
	    vbt.name||' '||vbst.name as vbstname,vss.name as vssname
	,count(distinct case when 
	(slo.datestart = to_date('${dateBegin}','dd.mm.yyyy') and cast('09:00:00' as time)>slo.entrancetime
	or to_date('${dateBegin}','dd.mm.yyyy')>slo.datestart)
	and (slo.datefinish is null 
	or slo.datefinish > to_date('${dateBegin}','dd.mm.yyyy') 
	or slo.datefinish = to_date('${dateBegin}','dd.mm.yyyy') and slo.dischargetime>=cast('09:00' as time))
	and (slo.transferdate is null 
	or slo.transferdate > to_date('${dateBegin}','dd.mm.yyyy') 
	or
	slo.transferdate = to_date('${dateBegin}','dd.mm.yyyy') and slo.transfertime>=cast('09:00' as time))

	 then slo.id else null end)
	as cnt5CurrentFrom
	,count(distinct case when slo.prevmedcase_id is null and (slo.datestart = to_date('${dateBegin}','dd.mm.yyyy') and slo.entrancetime>=cast('09:00:00' as time)
	or slo.dateStart between to_date('${dateNextBegin}','dd.mm.yyyy') and to_date('${dateEnd}','dd.mm.yyyy')
	or slo.datestart = to_date('${dateNextEnd}','dd.mm.yyyy') and cast('09:00' as time)>slo.entrancetime) then slo.id else null end)
	as cnt6Entrance
	,count(distinct case when slo.prevmedcase_id is null and vht.code='DAYTIMEHOSP' and(slo.datestart = to_date('${dateBegin}','dd.mm.yyyy') and slo.entrancetime>=cast('09:00:00' as time)
	or slo.dateStart between to_date('${dateNextBegin}','dd.mm.yyyy') and to_date('${dateEnd}','dd.mm.yyyy')
	or slo.datestart = to_date('${dateNextEnd}','dd.mm.yyyy') and cast('09:00' as time)>slo.entrancetime) then slo.id else null end)
	as cnt7EntranceDayHosp
	,count(distinct case when slo.prevmedcase_id is null and (slo.datestart = to_date('${dateBegin}','dd.mm.yyyy') and slo.entrancetime>=cast('09:00:00' as time)
	or slo.dateStart between to_date('${dateNextBegin}','dd.mm.yyyy') and to_date('${dateEnd}','dd.mm.yyyy')
	or slo.datestart = to_date('${dateNextEnd}','dd.mm.yyyy') and cast('09:00' as time)>slo.entrancetime) 
	and (ad1.domen=5 or ad2.domen=5)
	then slo.id else null end)
	as cnt8EntranceVillagers
	,count(distinct case when slo.prevmedcase_id is null and (slo.datestart = to_date('${dateBegin}','dd.mm.yyyy') and slo.entrancetime>=cast('09:00:00' as time)
	or slo.dateStart between to_date('${dateNextBegin}','dd.mm.yyyy') and to_date('${dateEnd}','dd.mm.yyyy')
	or slo.datestart = to_date('${dateNextEnd}','dd.mm.yyyy') and cast('09:00' as time)>slo.entrancetime)
	and (
	cast(to_char(slo.datestart,'yyyy') as int)-cast(to_char(pat.birthday,'yyyy') as int)
	+case when (cast(to_char(slo.datestart, 'mm') as int)-cast(to_char(pat.birthday, 'mm') as int)) <0 then -1 when (cast(to_char(slo.datestart,'dd') as int) - cast(to_char(pat.birthday,'dd') as int)<0) and ((cast(to_char(slo.datestart, 'mm') as int)-cast(to_char(pat.birthday, 'mm') as int)-1)<0)  then -1 else 0 end
	)<18
	then slo.id else null end)

	as cnt9EntranceTo17
	,count(distinct case when slo.prevmedcase_id is null and vht.code='DAYTIMEHOSP' and (slo.datestart = to_date('${dateBegin}','dd.mm.yyyy') and slo.entrancetime>=cast('09:00:00' as time)
	or slo.dateStart between to_date('${dateNextBegin}','dd.mm.yyyy') and to_date('${dateEnd}','dd.mm.yyyy')
	or slo.datestart = to_date('${dateNextEnd}','dd.mm.yyyy') and cast('09:00' as time)>slo.entrancetime) 
	and 
	60<=(
	cast(to_char(slo.datestart,'yyyy') as int)-cast(to_char(pat.birthday,'yyyy') as int)
	+case when (cast(to_char(slo.datestart, 'mm') as int)-cast(to_char(pat.birthday, 'mm') as int)) <0 then -1 when (cast(to_char(slo.datestart,'dd') as int) - cast(to_char(pat.birthday,'dd') as int)<0) and ((cast(to_char(slo.datestart, 'mm') as int)-cast(to_char(pat.birthday, 'mm') as int)-1)<0)  then -1 else 0 end
	)
	then slo.id else null end)
	as cnt10EntranceFrom60
	,count(distinct case when slo.prevmedcase_id is not null and (slo.datestart between to_date('${dateNextBegin}','dd.mm.yyyy') and to_date('${dateEnd}','dd.mm.yyyy')
	 or slo.datestart = to_date('${dateBegin}','dd.mm.yyyy') and slo.entrancetime>=cast('09:00:00' as time)
	or slo.datestart = to_date('${dateNextEnd}','dd.mm.yyyy') and cast('09:00' as time)>slo.entrancetime) then slo.id else null end)
	as cnt11TransferOutOtherDepartment
	,count(distinct case when  (slo.transferdate between to_date('${dateNextBegin}','dd.mm.yyyy') and to_date('${dateEnd}','dd.mm.yyyy')
	 or slo.transferdate = to_date('${dateBegin}','dd.mm.yyyy') and slo.transfertime>=cast('09:00:00' as time)
	or slo.transferdate = to_date('${dateNextEnd}','dd.mm.yyyy') and cast('09:00' as time)>slo.transfertime) then slo.id else null end) 
	as cnt12TransferInOtherDepartment
	,count(distinct case when vhr.code!='11' and (slo.dateFinish between to_date('${dateNextBegin}','dd.mm.yyyy') and to_date('${dateEnd}','dd.mm.yyyy')
	 or slo.dateFinish = to_date('${dateBegin}','dd.mm.yyyy') and slo.dischargetime>=cast('09:00:00' as time)
	or slo.dateFinish = to_date('${dateNextEnd}','dd.mm.yyyy') and cast('09:00' as time)>slo.dischargetime) then slo.id else null end) 
	as cnt13Finished
	,count(distinct case when vho.code='4' and vhr.code!='11' and (slo.dateFinish between to_date('${dateNextBegin}','dd.mm.yyyy') and to_date('${dateEnd}','dd.mm.yyyy')
	 or slo.dateFinish = to_date('${dateBegin}','dd.mm.yyyy') and slo.dischargetime>=cast('09:00:00' as time)
	or slo.dateFinish = to_date('${dateNextEnd}','dd.mm.yyyy') and cast('09:00' as time)>slo.dischargetime) then slo.id else null end) 
	as cnt14FinishedOtherHospital
	,count(distinct case when vho.code='3' and vhr.code!='11' and (slo.dateFinish between to_date('${dateNextBegin}','dd.mm.yyyy') and to_date('${dateEnd}','dd.mm.yyyy')
	 or slo.dateFinish = to_date('${dateBegin}','dd.mm.yyyy') and slo.dischargetime>=cast('09:00:00' as time)
	or slo.dateFinish = to_date('${dateNextEnd}','dd.mm.yyyy') and cast('09:00' as time)>slo.dischargetime) then slo.id else null end) 
	as cnt15FinishedHourHospital
	,count(distinct case when vho.code='2' and vhr.code!='11' and (slo.dateFinish between to_date('${dateNextBegin}','dd.mm.yyyy') and to_date('${dateEnd}','dd.mm.yyyy')
	 or slo.dateFinish = to_date('${dateBegin}','dd.mm.yyyy') and slo.dischargetime>=cast('09:00:00' as time)
	or slo.dateFinish = to_date('${dateNextEnd}','dd.mm.yyyy') and cast('09:00' as time)>slo.dischargetime) then slo.id else null end) 
	as cnt16FinishedDayHospital
	,count(distinct case when vhr.code='11' and (slo.dateFinish between to_date('${dateNextBegin}','dd.mm.yyyy') and to_date('${dateEnd}','dd.mm.yyyy')
	 or slo.dateFinish = to_date('${dateBegin}','dd.mm.yyyy') and slo.dischargetime>=cast('09:00:00' as time)
	or slo.dateFinish = to_date('${dateNextEnd}','dd.mm.yyyy') and cast('09:00' as time)>slo.dischargetime) then slo.id else null end) 
	as cnt17Death
	,

	count(distinct case when 
			(
				slo.transferdate is null
				or slo.transferdate > to_date('${dateNextEnd}','dd.mm.yyyy')
				or slo.transferdate = to_date('${dateNextEnd}','dd.mm.yyyy') and slo.transfertime>=cast('09:00' as time)
			) and (
				slo.datefinish is null or
				slo.datefinish > to_date('${dateNextEnd}','dd.mm.yyyy')
				or slo.datefinish = to_date('${dateNextEnd}','dd.mm.yyyy') and slo.dischargetime>=cast('09:00' as time)
			)
		 then slo.id else null end
	)
	 as cnt18CurrentTo
	 
	,count(distinct case when sls.hotelServices='1' and 
	 (
				slo.transferdate is null
				or slo.transferdate > to_date('${dateNextEnd}','dd.mm.yyyy')
				or slo.transferdate = to_date('${dateNextEnd}','dd.mm.yyyy') and slo.transfertime>=cast('09:00' as time)
			) and (
				slo.datefinish is null or
				slo.datefinish > to_date('${dateNextEnd}','dd.mm.yyyy')
				or slo.datefinish = to_date('${dateNextEnd}','dd.mm.yyyy') and slo.dischargetime>=cast('09:00' as time)
			) then slo.id else null end)
	 as cnt19CurrentMother
	 
	 	,sum(	 	
	 	
	case when 
		slo.dateFinish between to_date('${dateBegin}','dd.mm.yyyy')+2 and to_date('${dateEnd}','dd.mm.yyyy')
			or slo.dateFinish = to_date('${dateNextBegin}','dd.mm.yyyy') and slo.dischargetime>=cast('09:00:00' as time)
			or slo.dateFinish = to_date('${dateNextEnd}','dd.mm.yyyy') and cast('09:00' as time)>slo.dischargetime		
		then slo.dateFinish
			-case
				when slo.dateFinish=to_date('${dateEnd}','dd.mm.yyyy') and slo.dischargetime>=cast('09:00:00' as time) then 1
				when slo.dateFinish = to_date('${dateNextEnd}','dd.mm.yyyy') and cast('09:00' as time)>slo.dischargetime then 2
				when cast('09:00' as time)>slo.dischargetime then 2 
				else 1 end
		when slo.dateFinish=to_date('${dateBegin}','dd.mm.yyyy') then slo.dateFinish-1
		when slo.dateFinish=to_date('${dateNextBegin}','dd.mm.yyyy') and cast('09:00:00' as time)>slo.dischargetime then slo.dateFinish-2
	     when 
		slo.transferDate between to_date('${dateBegin}','dd.mm.yyyy')+2 and to_date('${dateEnd}','dd.mm.yyyy')
			or slo.transferDate = to_date('${dateNextBegin}','dd.mm.yyyy') and slo.transfertime>=cast('09:00:00' as time)
			or slo.transferDate = to_date('${dateNextEnd}','dd.mm.yyyy') and cast('09:00' as time)>slo.transfertime		
		then slo.transferDate
			-case 
			when slo.transferDate = to_date('${dateEnd}','dd.mm.yyyy') and slo.transfertime>=cast('09:00:00' as time) then 1
			when slo.transferdate = to_date('${dateNextEnd}','dd.mm.yyyy') and cast('09:00' as time)>slo.transfertime then 2
			when cast('09:00' as time)>slo.transfertime then 2 else 1 end
		when slo.transferDate=to_date('${dateBegin}','dd.mm.yyyy') then slo.transferDate-1
		when slo.transferDate=to_date('${dateNextBegin}','dd.mm.yyyy') and cast('09:00:00' as time)>slo.transfertime then slo.transferDate-2
		else to_date('${dateEnd}','dd.mm.yyyy')
		
		end
	-
	case when
		(slo.datestart = to_date('${dateBegin}','dd.mm.yyyy') and cast('09:00:00' as time)>slo.entrancetime
		or to_date('${dateBegin}','dd.mm.yyyy')>slo.datestart)
		and (slo.datefinish is null 
		or slo.datefinish > to_date('${dateBegin}','dd.mm.yyyy') 
		or slo.datefinish = to_date('${dateBegin}','dd.mm.yyyy') and slo.dischargetime>=cast('09:00' as time))
		and (slo.transferdate is null 
		or slo.transferdate > to_date('${dateBegin}','dd.mm.yyyy') 
		or
		slo.transferdate = to_date('${dateBegin}','dd.mm.yyyy') and slo.transfertime>=cast('09:00' as time))
	then to_date('${datePrevBegin}','dd.mm.yyyy')
	else slo.dateStart - case 
		when slo.datestart = to_date('${dateBegin}','dd.mm.yyyy') and slo.entrancetime>=cast('09:00' as time) then 1
		when slo.datestart = to_date('${dateNextBegin}','dd.mm.yyyy') and cast('09:00' as time)>slo.entrancetime then 2
		when slo.datestart = to_date('${dateEnd}','dd.mm.yyyy') and slo.entrancetime>=cast('09:00' as time) then 1
		when slo.datestart = to_date('${dateNextEnd}','dd.mm.yyyy') and cast('09:00' as time)>slo.entrancetime then 2
		when cast('09:00' as time)>slo.entrancetime then 2 else 1 end
	end
	
	
	
	
	
	
	) as cntDays

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
	and (to_date('${dateNextEnd}','dd.mm.yyyy')>slo.datestart
	or to_date('${dateNextEnd}','dd.mm.yyyy')=slo.dateStart and cast('09:00' as time)>slo.entrancetime
	)
	and (slo.datefinish is null 
	or
	slo.datefinish>to_date('${dateBegin}','dd.mm.yyyy')
	or to_date('${dateBegin}','dd.mm.yyyy')=slo.datefinish and slo.dischargetime>=cast('09:00' as time)
	)
	and (slo.transferdate is null 
	or slo.transferdate > to_date('${dateBegin}','dd.mm.yyyy')
	or to_date('${dateBegin}','dd.mm.yyyy')=slo.transferdate and slo.transfertime>=cast('09:00' as time)
	)

	${department}
	group by slo.bedfund_id,bf.bedsubtype_id,vbst.name,vbt.name,bf.serviceStream_id,vss.name
	      " />
	    <msh:table name="journal_priem" 
	    viewUrl="stac_report_016.do?short=Short" 
	    action="stac_report_016.do" idField="1" guid="b621e361-1e0b-4ebd-9f58-b7d919b45bd6">
	      <msh:tableColumn columnName="#" property="sn" />
	      <msh:tableColumn columnName="профиль коек" property="2" />
	      <msh:tableColumn columnName="поток обслуживания" property="3" />
	      <msh:tableColumn isCalcAmount="true" columnName="Состоит на начало истекших суток" property="4" />
	      <msh:tableColumn isCalcAmount="true" columnName="поступило всего" property="5" />
	      <msh:tableColumn isCalcAmount="true" columnName="в т.ч. из дневного стационар" property="6" />
	      <msh:tableColumn isCalcAmount="true" columnName="с.ж" property="7" />
	      <msh:tableColumn isCalcAmount="true" columnName="до 17 лет" property="8" />
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
	      <msh:tableColumn isCalcAmount="true" columnName="проведено койко дней" property="19" />
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
	    <msh:sectionTitle>Реестр состоящих на начало отчетного периода</msh:sectionTitle>
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
	(slo.datestart = to_date('${dateBegin}','dd.mm.yyyy') and cast('09:00:00' as time)>slo.entrancetime
	or to_date('${dateBegin}','dd.mm.yyyy')>slo.datestart)
	and (slo.datefinish is null 
	or slo.datefinish > to_date('${dateBegin}','dd.mm.yyyy') 
	or slo.datefinish = to_date('${dateBegin}','dd.mm.yyyy') and slo.dischargetime>=cast('09:00' as time))
	and (slo.transferdate is null 
	or slo.transferdate > to_date('${dateBegin}','dd.mm.yyyy') 
	or
	slo.transferdate = to_date('${dateBegin}','dd.mm.yyyy') and slo.transfertime>=cast('09:00' as time))

	order by pat.lastname,pat.firstname,pat.middlename
	      " guid="4a720225-8d94-4b47-bef3-4dbbe79eec74" />
	    <msh:table name="journal_priem01" viewUrl="entityShortView-stac_slo.do" action="entityParentView-stac_slo.do" idField="1" guid="b621e361-1e0b-4ebd-9f58-b7d919b45bd6">
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
	    <msh:sectionTitle>Реестр состоящих на начало конца отчетного периода</msh:sectionTitle>
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
	and (to_date('${dateNextEnd}','dd.mm.yyyy')>slo.datestart
	or to_date('${dateNextEnd}','dd.mm.yyyy')=slo.dateStart and cast('09:00' as time)>slo.entrancetime
	)
	and (slo.datefinish is null 
	or
	slo.datefinish>to_date('${dateBegin}','dd.mm.yyyy')
	or to_date('${dateBegin}','dd.mm.yyyy')=slo.datefinish and slo.dischargetime>=cast('09:00' as time)
	)
	and (slo.transferdate is null 
	or slo.transferdate > to_date('${dateBegin}','dd.mm.yyyy')
	or to_date('${dateBegin}','dd.mm.yyyy')=slo.transferdate and slo.transfertime>=cast('09:00' as time)
	)
	
	and 
	(
		slo.transferdate is null or slo.transferdate > to_date('${dateNextEnd}','dd.mm.yyyy')
		or slo.transferdate = to_date('${dateNextEnd}','dd.mm.yyyy') and slo.transfertime>=cast('09:00' as time)
	) and (
		slo.datefinish is null or slo.datefinish > to_date('${dateNextEnd}','dd.mm.yyyy')
		or slo.datefinish = to_date('${dateNextEnd}','dd.mm.yyyy') and slo.dischargetime>=cast('09:00' as time)
	)

	order by pat.lastname,pat.firstname,pat.middlename

	      " guid="4a720225-8d94-4b47-bef3-4dbbe79eec74" />
	    <msh:table name="journal_priem02" viewUrl="entityShortView-stac_slo.do" action="entityParentView-stac_slo.do" idField="1" guid="b621e361-1e0b-4ebd-9f58-b7d919b45bd6">
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
	where  slo.dtype='DepartmentMedCase' ${department} and (slo.datestart = to_date('${dateBegin}','dd.mm.yyyy') and slo.entrancetime>=cast('09:00:00' as time)
	or slo.dateStart between to_date('${dateNextBegin}','dd.mm.yyyy') and to_date('${dateEnd}','dd.mm.yyyy')
	or slo.datestart = to_date('${dateNextEnd}','dd.mm.yyyy') and cast('09:00' as time)>slo.entrancetime)
	and slo.prevMedCase_id is null
	and (vht.code is null or vht.code!='ALLTIMEHOSP')
	order by pat.lastname,pat.firstname,pat.middlename
	      " guid="4a720225-8d94-4b47-bef3-4dbbe79eec74" />
	    <msh:table name="journal_priem1" viewUrl="entityShortView-stac_slo.do" action="entityParentView-stac_slo.do" idField="1" guid="b621e361-1e0b-4ebd-9f58-b7d919b45bd6">
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
	where  slo.dtype='DepartmentMedCase' ${department} and (slo.datestart = to_date('${dateBegin}','dd.mm.yyyy') and slo.entrancetime>=cast('09:00:00' as time)
	or slo.dateStart between to_date('${dateNextBegin}','dd.mm.yyyy') and to_date('${dateEnd}','dd.mm.yyyy')
	or slo.datestart = to_date('${dateNextEnd}','dd.mm.yyyy') and cast('09:00' as time)>slo.entrancetime)
	and slo.prevMedCase_id is not null
	order by pat.lastname,pat.firstname,pat.middlename
	      " guid="4a720225-8d94-4b47-bef3-4dbbe79eec74" />
	    <msh:table name="journal_priem1" viewUrl="entityShortView-stac_slo.do" action="entityParentView-stac_slo.do" idField="1" guid="b621e361-1e0b-4ebd-9f58-b7d919b45bd6">
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
	where  slo.dtype='DepartmentMedCase' ${department} and (slo.datestart = to_date('${dateBegin}','dd.mm.yyyy') and slo.entrancetime>=cast('09:00:00' as time)
	or slo.dateStart between to_date('${dateNextBegin}','dd.mm.yyyy') and to_date('${dateEnd}','dd.mm.yyyy')
	or slo.datestart = to_date('${dateNextEnd}','dd.mm.yyyy') and cast('09:00' as time)>slo.entrancetime)
	and slo.prevMedCase_id is null  
	and vht.code='ALLTIMEHOSP'
	order by pat.lastname,pat.firstname,pat.middlename
	      " guid="4a720225-8d94-4b47-bef3-4dbbe79eec74" />
	    <msh:table name="journal_priem2" viewUrl="entityShortView-stac_slo.do" action="entityParentView-stac_slo.do" idField="1" guid="b621e361-1e0b-4ebd-9f58-b7d919b45bd6">
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
	where  slo.dtype='DepartmentMedCase' ${department} 
	 and (slo.dateFinish between to_date('${dateNextBegin}','dd.mm.yyyy') and to_date('${dateEnd}','dd.mm.yyyy')
	 or slo.dateFinish = to_date('${dateBegin}','dd.mm.yyyy') and slo.dischargetime>=cast('09:00:00' as time)
	or slo.dateFinish = to_date('${dateNextEnd}','dd.mm.yyyy') and cast('09:00' as time)>slo.dischargetime)
	
	
	and vhr.code!='11'
	order by pat.lastname,pat.firstname,pat.middlename
	      " guid="4a720225-8d94-4b47-bef3-4dbbe79eec74" />
	    <msh:table name="journal_priem3" viewUrl="entityShortView-stac_slo.do" action="entityParentView-stac_slo.do" idField="1" guid="b621e361-1e0b-4ebd-9f58-b7d919b45bd6">
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
	where  slo.dtype='DepartmentMedCase' ${department} 
	 and (slo.transferDate between to_date('${dateNextBegin}','dd.mm.yyyy') and to_date('${dateEnd}','dd.mm.yyyy')
	 or slo.transferDate = to_date('${dateBegin}','dd.mm.yyyy') and slo.transfertime>=cast('09:00:00' as time)
	or slo.transferDate = to_date('${dateNextEnd}','dd.mm.yyyy') and cast('09:00' as time)>slo.transferTime)
	
	order by pat.lastname,pat.firstname,pat.middlename
	      " guid="4a720225-8d94-4b47-bef3-4dbbe79eec74" />
	    <msh:table name="journal_priem4" viewUrl="entityShortView-stac_slo.do" action="entityParentView-stac_slo.do" idField="1" guid="b621e361-1e0b-4ebd-9f58-b7d919b45bd6">
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
	where  slo.dtype='DepartmentMedCase' ${department}
	
     and (slo.dateFinish between to_date('${dateNextBegin}','dd.mm.yyyy') and to_date('${dateEnd}','dd.mm.yyyy')
	 or slo.dateFinish = to_date('${dateBegin}','dd.mm.yyyy') and slo.dischargetime>=cast('09:00:00' as time)
	or slo.dateFinish = to_date('${dateNextEnd}','dd.mm.yyyy') and cast('09:00' as time)>slo.dischargetime)
	
	and vhr.code='11'
	order by pat.lastname,pat.firstname,pat.middlename
	      " guid="4a720225-8d94-4b47-bef3-4dbbe79eec74" />
	    <msh:table name="journal_priem4" viewUrl="entityShortView-stac_slo.do" action="entityParentView-stac_slo.do" idField="1" guid="b621e361-1e0b-4ebd-9f58-b7d919b45bd6">
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
	    <msh:sectionTitle>16/у-02 форма. Свод по отделениям</msh:sectionTitle>
	    <msh:sectionContent>
	    <ecom:webQuery name="journal_priem" nativeSql="select '${dateBegin}:'||lpu.id||'&dateBegin=${dateBegin}&dateEnd=${dateEnd}&typeView=1&department='||lpu.id,lpu.name,list(distinct vbst.name) as vbstname,list(distinct vss.name) as vssname
	,count(distinct case when (slo.datestart = to_date('${dateBegin}','dd.mm.yyyy') and cast('09:00:00' as time)>slo.entrancetime
	or to_date('${dateBegin}','dd.mm.yyyy')>slo.datestart)
	and (slo.datefinish is null 
	or slo.datefinish > to_date('${dateBegin}','dd.mm.yyyy') 
	or slo.datefinish = to_date('${dateBegin}','dd.mm.yyyy') and slo.dischargetime>=cast('09:00' as time))
	and (slo.transferdate is null 
	or slo.transferdate > to_date('${dateBegin}','dd.mm.yyyy') 
	or
	slo.transferdate = to_date('${dateBegin}','dd.mm.yyyy') and slo.transfertime>=cast('09:00' as time))

	 then slo.id else null end)
	as cnt5CurrentFrom
	,count(distinct case when slo.prevmedcase_id is null and (slo.datestart between to_date('${dateNextBegin}','dd.mm.yyyy') and to_date('${dateEnd}','dd.mm.yyyy')
	 or slo.datestart = to_date('${dateBegin}','dd.mm.yyyy') and slo.entrancetime>=cast('09:00:00' as time)
	or slo.datestart = to_date('${dateNextEnd}','dd.mm.yyyy') and cast('09:00' as time)>slo.entrancetime) then slo.id else null end)
	as cnt6Entrance
	,count(distinct case when slo.prevmedcase_id is null and vht.code='DAYTIMEHOSP' and (slo.datestart between to_date('${dateNextBegin}','dd.mm.yyyy') and to_date('${dateEnd}','dd.mm.yyyy')
	 or slo.datestart = to_date('${dateBegin}','dd.mm.yyyy') and slo.entrancetime>=cast('09:00:00' as time)
	or slo.datestart = to_date('${dateNextEnd}','dd.mm.yyyy') and cast('09:00' as time)>slo.entrancetime) then slo.id else null end)
	as cnt7EntranceDayHosp
	,count(distinct case when slo.prevmedcase_id is null and (slo.datestart between to_date('${dateNextBegin}','dd.mm.yyyy') and to_date('${dateEnd}','dd.mm.yyyy')
	 or slo.datestart = to_date('${dateBegin}','dd.mm.yyyy') and slo.entrancetime>=cast('09:00:00' as time)
	or slo.datestart = to_date('${dateNextEnd}','dd.mm.yyyy') and cast('09:00' as time)>slo.entrancetime) 
	and (
	cast(to_char(slo.datestart,'yyyy') as int)-cast(to_char(pat.birthday,'yyyy') as int)
	+case when (cast(to_char(slo.datestart, 'mm') as int)-cast(to_char(pat.birthday, 'mm') as int)) <0 then -1 when (cast(to_char(slo.datestart,'dd') as int) - cast(to_char(pat.birthday,'dd') as int)<0) and ((cast(to_char(slo.datestart, 'mm') as int)-cast(to_char(pat.birthday, 'mm') as int)-1)<0)  then -1 else 0 end
	)<18
	then slo.id else null end)

	as cnt9EntranceTo17
	,count(distinct case when slo.prevmedcase_id is null and (slo.datestart between to_date('${dateNextBegin}','dd.mm.yyyy') and to_date('${dateEnd}','dd.mm.yyyy')
	 or slo.datestart = to_date('${dateBegin}','dd.mm.yyyy') and slo.entrancetime>=cast('09:00:00' as time)
	or slo.datestart = to_date('${dateNextEnd}','dd.mm.yyyy') and cast('09:00' as time)>slo.entrancetime) 
	and 
	60<=(
	cast(to_char(slo.datestart,'yyyy') as int)-cast(to_char(pat.birthday,'yyyy') as int)
	+case when (cast(to_char(slo.datestart, 'mm') as int)-cast(to_char(pat.birthday, 'mm') as int)) <0 then -1 when (cast(to_char(slo.datestart,'dd') as int) - cast(to_char(pat.birthday,'dd') as int)<0) and ((cast(to_char(slo.datestart, 'mm') as int)-cast(to_char(pat.birthday, 'mm') as int)-1)<0)  then -1 else 0 end
	)
	then slo.id else null end)
	as cnt10EntranceFrom60
	,count(distinct case when slo.prevmedcase_id is not null and (slo.datestart between to_date('${dateNextBegin}','dd.mm.yyyy') and to_date('${dateEnd}','dd.mm.yyyy')
	 or slo.datestart = to_date('${dateBegin}','dd.mm.yyyy') and slo.entrancetime>=cast('09:00:00' as time)
	or slo.datestart = to_date('${dateNextEnd}','dd.mm.yyyy') and cast('09:00' as time)>slo.entrancetime) then slo.id else null end)
	as cnt11TransferOutOtherDepartment
	,count(distinct case when  (slo.transferdate between to_date('${dateNextBegin}','dd.mm.yyyy') and to_date('${dateEnd}','dd.mm.yyyy')
	 or slo.transferdate = to_date('${dateBegin}','dd.mm.yyyy') and slo.transfertime>=cast('09:00:00' as time)
	or slo.transferdate = to_date('${dateNextEnd}','dd.mm.yyyy') and cast('09:00' as time)>slo.transfertime) then slo.id else null end) 
	as cnt12TransferInOtherDepartment
	,count(distinct case when vhr.code!='11' and (slo.dateFinish between to_date('${dateNextBegin}','dd.mm.yyyy') and to_date('${dateEnd}','dd.mm.yyyy')
	 or slo.dateFinish = to_date('${dateBegin}','dd.mm.yyyy') and slo.dischargetime>=cast('09:00:00' as time)
	or slo.dateFinish = to_date('${dateNextEnd}','dd.mm.yyyy') and cast('09:00' as time)>slo.dischargetime) then slo.id else null end) 
	as cnt13Finished
	,count(distinct case when vho.code='4' and vhr.code!='11' and (slo.dateFinish between to_date('${dateNextBegin}','dd.mm.yyyy') and to_date('${dateEnd}','dd.mm.yyyy')
	 or slo.dateFinish = to_date('${dateBegin}','dd.mm.yyyy') and slo.dischargetime>=cast('09:00:00' as time)
	or slo.dateFinish = to_date('${dateNextEnd}','dd.mm.yyyy') and cast('09:00' as time)>slo.dischargetime) then slo.id else null end) 
	as cnt14FinishedOtherHospital
	,count(distinct case when vho.code='3' and vhr.code!='11' and (slo.dateFinish between to_date('${dateNextBegin}','dd.mm.yyyy') and to_date('${dateEnd}','dd.mm.yyyy')
	 or slo.dateFinish = to_date('${dateBegin}','dd.mm.yyyy') and slo.dischargetime>=cast('09:00:00' as time)
	or slo.dateFinish = to_date('${dateNextEnd}','dd.mm.yyyy') and cast('09:00' as time)>slo.dischargetime) then slo.id else null end) 
	as cnt15FinishedHourHospital
	,count(distinct case when vho.code='2' and vhr.code!='11' and (slo.dateFinish between to_date('${dateNextBegin}','dd.mm.yyyy') and to_date('${dateEnd}','dd.mm.yyyy')
	 or slo.dateFinish = to_date('${dateBegin}','dd.mm.yyyy') and slo.dischargetime>=cast('09:00:00' as time)
	or slo.dateFinish = to_date('${dateNextEnd}','dd.mm.yyyy') and cast('09:00' as time)>slo.dischargetime) then slo.id else null end) 
	as cnt16FinishedDayHospital
	,count(distinct case when vhr.code='11' and (slo.dateFinish between to_date('${dateNextBegin}','dd.mm.yyyy') and to_date('${dateEnd}','dd.mm.yyyy')
	 or slo.dateFinish = to_date('${dateBegin}','dd.mm.yyyy') and slo.dischargetime>=cast('09:00:00' as time)
	or slo.dateFinish = to_date('${dateNextEnd}','dd.mm.yyyy') and cast('09:00' as time)>slo.dischargetime) then slo.id else null end) 
	as cnt17Death
	,

	count(distinct case when 
			(
				slo.transferdate is null
				or slo.transferdate > to_date('${dateNextEnd}','dd.mm.yyyy')
				or slo.transferdate = to_date('${dateNextEnd}','dd.mm.yyyy') and slo.transfertime>=cast('09:00' as time)
			) and (
				slo.datefinish is null or
				slo.datefinish > to_date('${dateNextEnd}','dd.mm.yyyy')
				or slo.datefinish = to_date('${dateNextEnd}','dd.mm.yyyy') and slo.dischargetime>=cast('09:00' as time)
			)
		 then slo.id else null end
	)
	 as cnt18CurrentTo
	 
	,count(distinct case when sls.hotelServices='1' and 
	 (
				slo.transferdate is null
				or slo.transferdate > to_date('${dateNextEnd}','dd.mm.yyyy')
				or slo.transferdate = to_date('${dateNextEnd}','dd.mm.yyyy') and slo.transfertime>=cast('09:00' as time)
			) and (
				slo.datefinish is null or
				slo.datefinish > to_date('${dateNextEnd}','dd.mm.yyyy')
				or slo.datefinish = to_date('${dateNextEnd}','dd.mm.yyyy') and slo.dischargetime>=cast('09:00' as time)
			) then slo.id else null end)
	 as cnt19CurrentMother
	 
	 	,sum(	 	
	 	
	case when 
		slo.dateFinish between to_date('${dateBegin}','dd.mm.yyyy')+2 and to_date('${dateEnd}','dd.mm.yyyy')
			or slo.dateFinish = to_date('${dateNextBegin}','dd.mm.yyyy') and slo.dischargetime>=cast('09:00:00' as time)
			or slo.dateFinish = to_date('${dateNextEnd}','dd.mm.yyyy') and cast('09:00' as time)>slo.dischargetime		
		then slo.dateFinish
			-case
				when slo.dateFinish=to_date('${dateEnd}','dd.mm.yyyy') and slo.dischargetime>=cast('09:00:00' as time) then 1
				when slo.dateFinish = to_date('${dateNextEnd}','dd.mm.yyyy') and cast('09:00' as time)>slo.dischargetime then 2
				when cast('09:00' as time)>slo.dischargetime then 2 
				else 1 end
		when slo.dateFinish=to_date('${dateBegin}','dd.mm.yyyy') then slo.dateFinish-1
		when slo.dateFinish=to_date('${dateNextBegin}','dd.mm.yyyy') and cast('09:00:00' as time)>slo.dischargetime then slo.dateFinish-2
	     when 
		slo.transferDate between to_date('${dateBegin}','dd.mm.yyyy')+2 and to_date('${dateEnd}','dd.mm.yyyy')
			or slo.transferDate = to_date('${dateNextBegin}','dd.mm.yyyy') and slo.transfertime>=cast('09:00:00' as time)
			or slo.transferDate = to_date('${dateNextEnd}','dd.mm.yyyy') and cast('09:00' as time)>slo.transfertime		
		then slo.transferDate
			-case 
			when slo.transferDate = to_date('${dateEnd}','dd.mm.yyyy') and slo.transfertime>=cast('09:00:00' as time) then 1
			when slo.transferdate = to_date('${dateNextEnd}','dd.mm.yyyy') and cast('09:00' as time)>slo.transfertime then 2
			when cast('09:00' as time)>slo.transfertime then 2 else 1 end
		when slo.transferDate=to_date('${dateBegin}','dd.mm.yyyy') then slo.transferDate-1
		when slo.transferDate=to_date('${dateNextBegin}','dd.mm.yyyy') and cast('09:00:00' as time)>slo.transfertime then slo.transferDate-2
		else to_date('${dateEnd}','dd.mm.yyyy')
		
		end
	-
	case when
		(slo.datestart = to_date('${dateBegin}','dd.mm.yyyy') and cast('09:00:00' as time)>slo.entrancetime
		or to_date('${dateBegin}','dd.mm.yyyy')>slo.datestart)
		and (slo.datefinish is null 
		or slo.datefinish > to_date('${dateBegin}','dd.mm.yyyy') 
		or slo.datefinish = to_date('${dateBegin}','dd.mm.yyyy') and slo.dischargetime>=cast('09:00' as time))
		and (slo.transferdate is null 
		or slo.transferdate > to_date('${dateBegin}','dd.mm.yyyy') 
		or
		slo.transferdate = to_date('${dateBegin}','dd.mm.yyyy') and slo.transfertime>=cast('09:00' as time))
	then to_date('${datePrevBegin}','dd.mm.yyyy')
	else slo.dateStart - case 
		when slo.datestart = to_date('${dateBegin}','dd.mm.yyyy') and slo.entrancetime>=cast('09:00' as time) then 1
		when slo.datestart = to_date('${dateNextBegin}','dd.mm.yyyy') and cast('09:00' as time)>slo.entrancetime then 2
		when slo.datestart = to_date('${dateEnd}','dd.mm.yyyy') and slo.entrancetime>=cast('09:00' as time) then 1
		when slo.datestart = to_date('${dateNextEnd}','dd.mm.yyyy') and cast('09:00' as time)>slo.entrancetime then 2
		when cast('09:00' as time)>slo.entrancetime then 2 else 1 end
	end
	
	
	
	
	
	
	) as cntDays
	 
	,count(distinct slo.id) as cntAll
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
	and (to_date('${dateNextEnd}','dd.mm.yyyy')>slo.datestart
	or to_date('${dateNextEnd}','dd.mm.yyyy')=slo.dateStart and cast('09:00' as time)>slo.entrancetime
	)
	and (slo.datefinish is null 
	or
	slo.datefinish>to_date('${dateBegin}','dd.mm.yyyy')
	or to_date('${dateBegin}','dd.mm.yyyy')=slo.datefinish and slo.dischargetime>=cast('09:00' as time)
	)
	and (slo.transferdate is null 
	or slo.transferdate > to_date('${dateBegin}','dd.mm.yyyy')
	or to_date('${dateBegin}','dd.mm.yyyy')=slo.transferdate and slo.transfertime>=cast('09:00' as time)
	)

	${department}
	group by lpu.id,lpu.name
	      " />
	    <msh:table name="journal_priem" 
	    viewUrl="stac_report_016.do?short=Short" 
	    action="stac_report_016.do" idField="1" guid="b621e361-1e0b-4ebd-9f58-b7d919b45bd6">
	      <msh:tableColumn columnName="#" property="sn" />
	      <msh:tableColumn columnName="отделение" property="2" />
	      <msh:tableColumn columnName="профили коек" property="3" />
	      <msh:tableColumn columnName="потоки обслуживания" property="4" />
	      <msh:tableColumn isCalcAmount="true" columnName="Состоит на начало истекших суток" property="5" />
	      <msh:tableColumn isCalcAmount="true" columnName="поступило всего" property="6" />
	      <msh:tableColumn isCalcAmount="true" columnName="в т.ч. из дневного стационар" property="7" />
	      
	      <msh:tableColumn isCalcAmount="true" columnName="до 17 лет" property="8" />
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
	      <msh:tableColumn isCalcAmount="true" columnName="проведено койко дней" property="19" />
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