<%@page import="ru.ecom.ejb.services.query.WebQueryResult"%>
<%@page import="ru.ecom.web.util.ActionUtil"%>
<%@page import="ru.nuzmsh.util.format.DateFormat"%>
<%@page import="ru.nuzmsh.util.query.ReportParamUtil"%>
<%@page import="java.math.BigDecimal"%>
<%@page import="java.text.SimpleDateFormat"%>
<%@page import="java.util.*"%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://struts.apache.org/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://www.nuzmsh.ru/tags/msh" prefix="msh" %>
<%@ taglib uri="http://www.ecom-ast.ru/tags/ecom" prefix="ecom" %>
<%@ taglib tagdir="/WEB-INF/tags" prefix="tags" %>

<tiles:insert page="/WEB-INF/tiles/main${param.short}Layout.jsp" flush="true" >

  <tiles:put name="title" type="string">
    <msh:title mainMenu="Journals">Форма 16у-02</msh:title>
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
  String typeHour = ActionUtil.updateParameter("Report007","typeHour","3", request) ;
  
  if (sh) {
	 
  %>
    <msh:form action="/stac_report_016.do" defaultField="dateBegin" disableFormDataConfirm="true" method="GET">
    <input type="hidden" name="s" id="s" value="HospitalPrintReport" />
    <input type="hidden" name="m" id="m" value="printReport016" />
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
        	<input type="radio" name="typeView" value="1">  16 форма
        </td>
        <td onclick="this.childNodes[1].checked='checked';">
        	<input type="radio" name="typeView" value="2"  >  реестр состоящих пациентов
        </td>
        <td onclick="this.childNodes[1].checked='checked';">
        	<input type="radio" name="typeView" value="3"  >  реестр пациентов
        </td>
       </msh:row>
       <msh:row>
        <td></td>
        <td onclick="this.childNodes[1].checked='checked';">
        	<input type="radio" name="typeView" value="4"  >  свод по отделениям
        </td>
        <td onclick="this.childNodes[1].checked='checked';">
        	<input type="radio" name="typeView" value="5"  >  свод по отделениям и профилю коек
        </td>
        <td onclick="this.childNodes[1].checked='checked';">
        	<input type="radio" name="typeView" value="6"  >  свод по типу коек
        </td>
       
       </msh:row>
       <msh:row>
        <td></td>
        <td onclick="this.childNodes[1].checked='checked';" colspan="2">
        	<input type="radio" name="typeView" value="7"  >  свод по отделениям с учетом с.ж. 
        </td>
        <td onclick="this.childNodes[1].checked='checked';" colspan="2">
        	<input type="radio" name="typeView" value="8"  >  анализ работы учреждения 
        </td>
       </msh:row>
      <msh:row>
        <msh:textField property="dateBegin" label="Дата начала периода" />
        <msh:textField property="dateEnd" label="Дата окончания периода" />
      </msh:row>
        <msh:row>
        	<msh:autoComplete property="department" fieldColSpan="5" horizontalFill="true" label="Отделение" vocName="vocLpuHospOtdAll"/>
        </msh:row>
        <msh:row>
        	<msh:autoComplete property="serviceStream" fieldColSpan="4" horizontalFill="true" label="Поток обслуживания" vocName="vocServiceStream"/>
        </msh:row>
        <msh:row>
        	<msh:autoComplete property="bedType" fieldColSpan="2" horizontalFill="true" label="Профиль коек" vocName="vocBedType"/>
        	<msh:autoComplete property="bedSubType" fieldColSpan="2" horizontalFill="true" label="Тип коек" vocName="vocBedSubType"/>
        </msh:row>
      <msh:row>
           <td colspan="11">
            <input type="submit" onclick="find()" value="Найти" />
          </td>
      </msh:row>
    </msh:panel>
    </msh:form>
    
       <script type='text/javascript'>
    
    checkFieldUpdate('typeView','${typeView}',1) ;
    checkFieldUpdate('typeHour','${typeHour}',3) ;
    
  
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
    request.setAttribute("isReportBase", ActionUtil.isReportBase(date, dateEnd,request));
    String timeSql="09:00" ;
	if (typeHour.equals("1")) {
		timeSql="07:00" ;
	} else if (typeHour.equals("2")) {
		timeSql="08:00";
	} else if (typeHour.equals("4")) {
		timeSql="00:00";
	}
	request.setAttribute("timeSql", timeSql) ;
    String periodEntrance = ReportParamUtil.getPeriodByDate(false,true, "slo.dateStart", "slo.entranceTime", date, dateEnd, timeSql) ;
    String periodTransfer = ReportParamUtil.getPeriodByDate(false,true, "slo.transferDate", "slo.transferTime", date, dateEnd, timeSql) ;
    String periodDischarge = ReportParamUtil.getPeriodByDate(false,true, "slo.dateFinish", "slo.dischargeTime", date, dateEnd, timeSql) ;
    String department="" ;
	
	/*if (dep!=null &&!dep.equals("") && !dep.equals("0")) {
		department= " and slo.department_id='"+dep+"'" ;
	}
	request.setAttribute("department", department) ;*/
	ActionUtil.setParameterFilterSql("department","slo.department_id", request) ;
	ActionUtil.setParameterFilterSql("serviceStream","slo.serviceStream_id", request) ;
	ActionUtil.setParameterFilterSql("bedType","bf.bedType_id", request) ;
	ActionUtil.setParameterFilterSql("bedSubType","bf.bedSubType_id", request) ;
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
	    <ecom:webQuery isReportBase="${isReportBase}"  name="journal_priem" nameFldSql="journal_priem_sql" nativeSql="select
	    slo.id
	    ,ss.code as sscode
	    ,pat.lastname||' '||pat.middlename||' '||pat.firstname||' '||to_char(pat.birthday,'dd.mm.yyyy') as patfio
	    ,vbst.name as vbstname,case when vss.id!=vss1.id then '<font color=red>'||vss1.name||'</font>' else vss.name end as vssname
	, case when 
	(slo.datestart = to_date('${dateBegin}','dd.mm.yyyy') and cast('${timeSql}:00' as time)>slo.entrancetime
	or to_date('${dateBegin}','dd.mm.yyyy')>slo.datestart)
	and (slo.datefinish is null 
	or slo.datefinish > to_date('${dateBegin}','dd.mm.yyyy') 
	or slo.datefinish = to_date('${dateBegin}','dd.mm.yyyy') and slo.dischargetime>=cast('${timeSql}' as time))
	and (slo.transferdate is null 
	or slo.transferdate > to_date('${dateBegin}','dd.mm.yyyy') 
	or
	slo.transferdate = to_date('${dateBegin}','dd.mm.yyyy') and slo.transfertime>=cast('${timeSql}' as time))

	 then 'X' else null end
	as cnt5CurrentFrom
	, case when slo.prevmedcase_id is null and (slo.datestart = to_date('${dateBegin}','dd.mm.yyyy') and slo.entrancetime>=cast('${timeSql}:00' as time)
	or slo.dateStart between to_date('${dateNextBegin}','dd.mm.yyyy') and to_date('${dateEnd}','dd.mm.yyyy')
	or slo.datestart = to_date('${dateNextEnd}','dd.mm.yyyy') and cast('${timeSql}' as time)>slo.entrancetime) then 'X' else null end
	as cnt6Entrance
	,case when slo.prevmedcase_id is null and vht.code='DAYTIMEHOSP' and(slo.datestart = to_date('${dateBegin}','dd.mm.yyyy') and slo.entrancetime>=cast('${timeSql}:00' as time)
	or slo.dateStart between to_date('${dateNextBegin}','dd.mm.yyyy') and to_date('${dateEnd}','dd.mm.yyyy')
	or slo.datestart = to_date('${dateNextEnd}','dd.mm.yyyy') and cast('${timeSql}' as time)>slo.entrancetime) then 'X' else null end
	as cnt7EntranceDayHosp
	,case when slo.prevmedcase_id is null and (slo.datestart = to_date('${dateBegin}','dd.mm.yyyy') and slo.entrancetime>=cast('${timeSql}:00' as time)
	or slo.dateStart between to_date('${dateNextBegin}','dd.mm.yyyy') and to_date('${dateEnd}','dd.mm.yyyy')
	or slo.datestart = to_date('${dateNextEnd}','dd.mm.yyyy') and cast('${timeSql}' as time)>slo.entrancetime) 
	and (ad1.addressIsVillage='1')
	then 'X' else null end
	as cnt8EntranceVillagers
	,case when slo.prevmedcase_id is null and (slo.datestart = to_date('${dateBegin}','dd.mm.yyyy') and slo.entrancetime>=cast('${timeSql}:00' as time)
	or slo.dateStart between to_date('${dateNextBegin}','dd.mm.yyyy') and to_date('${dateEnd}','dd.mm.yyyy')
	or slo.datestart = to_date('${dateNextEnd}','dd.mm.yyyy') and cast('${timeSql}' as time)>slo.entrancetime)
	and (
	cast(to_char(slo.datestart,'yyyy') as int)-cast(to_char(pat.birthday,'yyyy') as int)
	+case when (cast(to_char(slo.datestart, 'mm') as int)-cast(to_char(pat.birthday, 'mm') as int)) <0 then -1 when (cast(to_char(slo.datestart,'dd') as int) - cast(to_char(pat.birthday,'dd') as int)<0) and ((cast(to_char(slo.datestart, 'mm') as int)-cast(to_char(pat.birthday, 'mm') as int)-1)<0)  then -1 else 0 end
	)<18
	then 'X' else null end

	as cnt9EntranceTo17
	, case when slo.prevmedcase_id is null and (slo.datestart = to_date('${dateBegin}','dd.mm.yyyy') and slo.entrancetime>=cast('${timeSql}:00' as time)
	or slo.dateStart between to_date('${dateNextBegin}','dd.mm.yyyy') and to_date('${dateEnd}','dd.mm.yyyy')
	or slo.datestart = to_date('${dateNextEnd}','dd.mm.yyyy') and cast('${timeSql}' as time)>slo.entrancetime) 
	and 
	60<=(
	cast(to_char(slo.datestart,'yyyy') as int)-cast(to_char(pat.birthday,'yyyy') as int)
	+case when (cast(to_char(slo.datestart, 'mm') as int)-cast(to_char(pat.birthday, 'mm') as int)) <0 then -1 when (cast(to_char(slo.datestart,'dd') as int) - cast(to_char(pat.birthday,'dd') as int)<0) and ((cast(to_char(slo.datestart, 'mm') as int)-cast(to_char(pat.birthday, 'mm') as int)-1)<0)  then -1 else 0 end
	)
	then 'X' else null end
	as cnt10EntranceFrom60
	, case when slo.prevmedcase_id is not null and (slo.datestart between to_date('${dateNextBegin}','dd.mm.yyyy') and to_date('${dateEnd}','dd.mm.yyyy')
	 or slo.datestart = to_date('${dateBegin}','dd.mm.yyyy') and slo.entrancetime>=cast('${timeSql}:00' as time)
	or slo.datestart = to_date('${dateNextEnd}','dd.mm.yyyy') and cast('${timeSql}' as time)>slo.entrancetime) then 'X' else null end
	as cnt11TransferOutOtherDepartment
	, case when  (slo.transferdate between to_date('${dateNextBegin}','dd.mm.yyyy') and to_date('${dateEnd}','dd.mm.yyyy')
	 or slo.transferdate = to_date('${dateBegin}','dd.mm.yyyy') and slo.transfertime>=cast('${timeSql}:00' as time)
	or slo.transferdate = to_date('${dateNextEnd}','dd.mm.yyyy') and cast('${timeSql}' as time)>slo.transfertime) then 'X' else null end 
	as cnt12TransferInOtherDepartment
	, case when vhr.code!='11' and (slo.dateFinish between to_date('${dateNextBegin}','dd.mm.yyyy') and to_date('${dateEnd}','dd.mm.yyyy')
	 or slo.dateFinish = to_date('${dateBegin}','dd.mm.yyyy') and slo.dischargetime>=cast('${timeSql}:00' as time)
	or slo.dateFinish = to_date('${dateNextEnd}','dd.mm.yyyy') and cast('${timeSql}' as time)>slo.dischargetime) then 'X' else null end 
	as cnt13Finished
	, case when vho.code='4' and vhr.code!='11' and (slo.dateFinish between to_date('${dateNextBegin}','dd.mm.yyyy') and to_date('${dateEnd}','dd.mm.yyyy')
	 or slo.dateFinish = to_date('${dateBegin}','dd.mm.yyyy') and slo.dischargetime>=cast('${timeSql}:00' as time)
	or slo.dateFinish = to_date('${dateNextEnd}','dd.mm.yyyy') and cast('${timeSql}' as time)>slo.dischargetime) then 'X' else null end 
	as cnt14FinishedOtherHospital
	, case when vho.code='3' and vhr.code!='11' and (slo.dateFinish between to_date('${dateNextBegin}','dd.mm.yyyy') and to_date('${dateEnd}','dd.mm.yyyy')
	 or slo.dateFinish = to_date('${dateBegin}','dd.mm.yyyy') and slo.dischargetime>=cast('${timeSql}:00' as time)
	or slo.dateFinish = to_date('${dateNextEnd}','dd.mm.yyyy') and cast('${timeSql}' as time)>slo.dischargetime) then 'X' else null end 
	as cnt15FinishedHourHospital
	, case when vho.code='2' and vhr.code!='11' and (slo.dateFinish between to_date('${dateNextBegin}','dd.mm.yyyy') and to_date('${dateEnd}','dd.mm.yyyy')
	 or slo.dateFinish = to_date('${dateBegin}','dd.mm.yyyy') and slo.dischargetime>=cast('${timeSql}:00' as time)
	or slo.dateFinish = to_date('${dateNextEnd}','dd.mm.yyyy') and cast('${timeSql}' as time)>slo.dischargetime) then 'X' else null end 
	as cnt16FinishedDayHospital
	, case when vhr.code='11' and (slo.dateFinish between to_date('${dateNextBegin}','dd.mm.yyyy') and to_date('${dateEnd}','dd.mm.yyyy')
	 or slo.dateFinish = to_date('${dateBegin}','dd.mm.yyyy') and slo.dischargetime>=cast('${timeSql}:00' as time)
	or slo.dateFinish = to_date('${dateNextEnd}','dd.mm.yyyy') and cast('${timeSql}' as time)>slo.dischargetime) then 'X' else null end 
	as cnt17Death
	,

	 case when 
			(
				slo.transferdate is null
				or slo.transferdate > to_date('${dateNextEnd}','dd.mm.yyyy')
				or slo.transferdate = to_date('${dateNextEnd}','dd.mm.yyyy') and slo.transfertime>=cast('${timeSql}' as time)
			) and (
				slo.datefinish is null or
				slo.datefinish > to_date('${dateNextEnd}','dd.mm.yyyy')
				or slo.datefinish = to_date('${dateNextEnd}','dd.mm.yyyy') and slo.dischargetime>=cast('${timeSql}' as time)
			)
		 then 'X' else null end
	
	 as cnt18CurrentTo
	 
	, case when sls.hotelServices='1' and 
	 (
				slo.transferdate is null
				or slo.transferdate > to_date('${dateNextEnd}','dd.mm.yyyy')
				or slo.transferdate = to_date('${dateNextEnd}','dd.mm.yyyy') and slo.transfertime>=cast('${timeSql}' as time)
			) and (
				slo.datefinish is null or
				slo.datefinish > to_date('${dateNextEnd}','dd.mm.yyyy')
				or slo.datefinish = to_date('${dateNextEnd}','dd.mm.yyyy') and slo.dischargetime>=cast('${timeSql}' as time)
			) then 'X' else null end
	 as cnt19CurrentMother
	 
	 	,	 	
	 	
	case when 
		slo.dateFinish between to_date('${dateBegin}','dd.mm.yyyy')+2 and to_date('${dateEnd}','dd.mm.yyyy')
			or slo.dateFinish = to_date('${dateNextBegin}','dd.mm.yyyy') and slo.dischargetime>=cast('${timeSql}:00' as time)
			or slo.dateFinish = to_date('${dateNextEnd}','dd.mm.yyyy') and cast('${timeSql}' as time)>slo.dischargetime		
		then slo.dateFinish
			-case
				when slo.dateFinish=to_date('${dateEnd}','dd.mm.yyyy') and slo.dischargetime>=cast('${timeSql}:00' as time) then 1
				when slo.dateFinish = to_date('${dateNextEnd}','dd.mm.yyyy') and cast('${timeSql}' as time)>slo.dischargetime then 2
				when cast('${timeSql}' as time)>slo.dischargetime then 2 
				else 1 end
		when slo.dateFinish=to_date('${dateBegin}','dd.mm.yyyy') then slo.dateFinish-1
		when slo.dateFinish=to_date('${dateNextBegin}','dd.mm.yyyy') and cast('${timeSql}:00' as time)>slo.dischargetime then slo.dateFinish-2
	     when 
		slo.transferDate between to_date('${dateBegin}','dd.mm.yyyy')+2 and to_date('${dateEnd}','dd.mm.yyyy')
			or slo.transferDate = to_date('${dateNextBegin}','dd.mm.yyyy') and slo.transfertime>=cast('${timeSql}:00' as time)
			or slo.transferDate = to_date('${dateNextEnd}','dd.mm.yyyy') and cast('${timeSql}' as time)>slo.transfertime		
		then slo.transferDate
			-case 
			when slo.transferDate = to_date('${dateEnd}','dd.mm.yyyy') and slo.transfertime>=cast('${timeSql}:00' as time) then 1
			when slo.transferdate = to_date('${dateNextEnd}','dd.mm.yyyy') and cast('${timeSql}' as time)>slo.transfertime then 2
			when cast('${timeSql}' as time)>slo.transfertime then 2 else 1 end
		when slo.transferDate=to_date('${dateBegin}','dd.mm.yyyy') then slo.transferDate-1
		when slo.transferDate=to_date('${dateNextBegin}','dd.mm.yyyy') and cast('${timeSql}:00' as time)>slo.transfertime then slo.transferDate-2
		else to_date('${dateEnd}','dd.mm.yyyy')
		
		end
	-
	case when
		(slo.datestart = to_date('${dateBegin}','dd.mm.yyyy') and cast('${timeSql}:00' as time)>slo.entrancetime
		or to_date('${dateBegin}','dd.mm.yyyy')>slo.datestart)
		and (slo.datefinish is null 
		or slo.datefinish > to_date('${dateBegin}','dd.mm.yyyy') 
		or slo.datefinish = to_date('${dateBegin}','dd.mm.yyyy') and slo.dischargetime>=cast('${timeSql}' as time))
		and (slo.transferdate is null 
		or slo.transferdate > to_date('${dateBegin}','dd.mm.yyyy') 
		or
		slo.transferdate = to_date('${dateBegin}','dd.mm.yyyy') and slo.transfertime>=cast('${timeSql}' as time))
	then to_date('${datePrevBegin}','dd.mm.yyyy')
	else slo.dateStart - case 
		when slo.datestart = to_date('${dateBegin}','dd.mm.yyyy') and slo.entrancetime>=cast('${timeSql}' as time) then 1
		when slo.datestart = to_date('${dateNextBegin}','dd.mm.yyyy') and cast('${timeSql}' as time)>slo.entrancetime then 2
		when slo.datestart = to_date('${dateEnd}','dd.mm.yyyy') and slo.entrancetime>=cast('${timeSql}' as time) then 1
		when slo.datestart = to_date('${dateNextEnd}','dd.mm.yyyy') and cast('${timeSql}' as time)>slo.entrancetime then 2
		when cast('${timeSql}' as time)>slo.entrancetime then 2 else 1 end
	end
	
	
	
	
	
	
	 as cntDays
, case when slo.prevmedcase_id is null and (slo.datestart between to_date('${dateNextBegin}','dd.mm.yyyy') and to_date('${dateEnd}','dd.mm.yyyy')
	 or slo.datestart = to_date('${dateBegin}','dd.mm.yyyy') and slo.entrancetime>=cast('${timeSql}:00' as time)
	or slo.datestart = to_date('${dateNextEnd}','dd.mm.yyyy') and cast('${timeSql}' as time)>slo.entrancetime) 
	and 
	case when vs.omccode='1' then 60 else 55 end
	 <=(cast(to_char(slo.datestart,'yyyy') as int)-cast(to_char(pat.birthday,'yyyy') as int)
	+case when (cast(to_char(slo.datestart, 'mm') as int)-cast(to_char(pat.birthday, 'mm') as int)) <0 then -1 when (cast(to_char(slo.datestart,'dd') as int) - cast(to_char(pat.birthday,'dd') as int)<0) and ((cast(to_char(slo.datestart, 'mm') as int)-cast(to_char(pat.birthday, 'mm') as int)-1)<0)  then -1 else 0 end
	)
	then 'X' else null end
	as cnt21EntranceMoreTRUDOSPOSOB

,case when vhr.code!='11' and (slo.dateFinish between to_date('${dateNextBegin}','dd.mm.yyyy') and to_date('${dateEnd}','dd.mm.yyyy')
	 or slo.dateFinish = to_date('${dateBegin}','dd.mm.yyyy') and slo.dischargetime>=cast('${timeSql}:00' as time)
	or slo.dateFinish = to_date('${dateNextEnd}','dd.mm.yyyy') and cast('${timeSql}' as time)>slo.dischargetime) and 
	case when vs.omccode='1' then 60 else 55 end
	 <=(
	cast(to_char(slo.datestart,'yyyy') as int)-cast(to_char(pat.birthday,'yyyy') as int)
	+case when (cast(to_char(slo.datestart, 'mm') as int)-cast(to_char(pat.birthday, 'mm') as int)) <0 then -1 when (cast(to_char(slo.datestart,'dd') as int) - cast(to_char(pat.birthday,'dd') as int)<0) and ((cast(to_char(slo.datestart, 'mm') as int)-cast(to_char(pat.birthday, 'mm') as int)-1)<0)  then -1 else 0 end
	) then 'X' else null end 
	as cnt22FinishedTrudosposob	

,case when vhr.code='11' and (slo.dateFinish between to_date('${dateNextBegin}','dd.mm.yyyy') and to_date('${dateEnd}','dd.mm.yyyy')
	 or slo.dateFinish = to_date('${dateBegin}','dd.mm.yyyy') and slo.dischargetime>=cast('${timeSql}:00' as time)
	or slo.dateFinish = to_date('${dateNextEnd}','dd.mm.yyyy') and cast('${timeSql}' as time)>slo.dischargetime) and 
	case when vs.omccode='1' then 60 else 55 end
	 <=(
	cast(to_char(slo.datestart,'yyyy') as int)-cast(to_char(pat.birthday,'yyyy') as int)
	+case when (cast(to_char(slo.datestart, 'mm') as int)-cast(to_char(pat.birthday, 'mm') as int)) <0 then -1 when (cast(to_char(slo.datestart,'dd') as int) - cast(to_char(pat.birthday,'dd') as int)<0) and ((cast(to_char(slo.datestart, 'mm') as int)-cast(to_char(pat.birthday, 'mm') as int)-1)<0)  then -1 else 0 end
	) then 'X' else null end 
	as cnt23DeathTrudosposob

, case when 	
	 case when vs.omccode='1' then 60 else 55 end
	 <=(
	cast(to_char(slo.datestart,'yyyy') as int)-cast(to_char(pat.birthday,'yyyy') as int)
	+case when (cast(to_char(slo.datestart, 'mm') as int)-cast(to_char(pat.birthday, 'mm') as int)) <0 then -1 when (cast(to_char(slo.datestart,'dd') as int) - cast(to_char(pat.birthday,'dd') as int)<0) and ((cast(to_char(slo.datestart, 'mm') as int)-cast(to_char(pat.birthday, 'mm') as int)-1)<0)  then -1 else 0 end
	)	then 
	case when 
		slo.dateFinish between to_date('${dateBegin}','dd.mm.yyyy')+2 and to_date('${dateEnd}','dd.mm.yyyy')
			or slo.dateFinish = to_date('${dateNextBegin}','dd.mm.yyyy') and slo.dischargetime>=cast('${timeSql}:00' as time)
			or slo.dateFinish = to_date('${dateNextEnd}','dd.mm.yyyy') and cast('${timeSql}' as time)>slo.dischargetime		
		then slo.dateFinish
			-case
				when slo.dateFinish=to_date('${dateEnd}','dd.mm.yyyy') and slo.dischargetime>=cast('${timeSql}:00' as time) then 1
				when slo.dateFinish = to_date('${dateNextEnd}','dd.mm.yyyy') and cast('${timeSql}' as time)>slo.dischargetime then 2
				when cast('${timeSql}' as time)>slo.dischargetime then 2 
				else 1 end
		when slo.dateFinish=to_date('${dateBegin}','dd.mm.yyyy') then slo.dateFinish-1
		when slo.dateFinish=to_date('${dateNextBegin}','dd.mm.yyyy') and cast('${timeSql}:00' as time)>slo.dischargetime then slo.dateFinish-2
	     when 
		slo.transferDate between to_date('${dateBegin}','dd.mm.yyyy')+2 and to_date('${dateEnd}','dd.mm.yyyy')
			or slo.transferDate = to_date('${dateNextBegin}','dd.mm.yyyy') and slo.transfertime>=cast('${timeSql}:00' as time)
			or slo.transferDate = to_date('${dateNextEnd}','dd.mm.yyyy') and cast('${timeSql}' as time)>slo.transfertime		
		then slo.transferDate
			-case 
			when slo.transferDate = to_date('${dateEnd}','dd.mm.yyyy') and slo.transfertime>=cast('${timeSql}:00' as time) then 1
			when slo.transferdate = to_date('${dateNextEnd}','dd.mm.yyyy') and cast('${timeSql}' as time)>slo.transfertime then 2
			when cast('${timeSql}' as time)>slo.transfertime then 2 else 1 end
		when slo.transferDate=to_date('${dateBegin}','dd.mm.yyyy') then slo.transferDate-1
		when slo.transferDate=to_date('${dateNextBegin}','dd.mm.yyyy') and cast('${timeSql}:00' as time)>slo.transfertime then slo.transferDate-2
		else to_date('${dateEnd}','dd.mm.yyyy')
		
		end
	-
	case when
		(slo.datestart = to_date('${dateBegin}','dd.mm.yyyy') and cast('${timeSql}:00' as time)>slo.entrancetime
		or to_date('${dateBegin}','dd.mm.yyyy')>slo.datestart)
		and (slo.datefinish is null 
		or slo.datefinish > to_date('${dateBegin}','dd.mm.yyyy') 
		or slo.datefinish = to_date('${dateBegin}','dd.mm.yyyy') and slo.dischargetime>=cast('${timeSql}' as time))
		and (slo.transferdate is null 
		or slo.transferdate > to_date('${dateBegin}','dd.mm.yyyy') 
		or
		slo.transferdate = to_date('${dateBegin}','dd.mm.yyyy') and slo.transfertime>=cast('${timeSql}' as time))
	then to_date('${datePrevBegin}','dd.mm.yyyy')
	else slo.dateStart - case 
		when slo.datestart = to_date('${dateBegin}','dd.mm.yyyy') and slo.entrancetime>=cast('${timeSql}' as time) then 1
		when slo.datestart = to_date('${dateNextBegin}','dd.mm.yyyy') and cast('${timeSql}' as time)>slo.entrancetime then 2
		when slo.datestart = to_date('${dateEnd}','dd.mm.yyyy') and slo.entrancetime>=cast('${timeSql}' as time) then 1
		when slo.datestart = to_date('${dateNextEnd}','dd.mm.yyyy') and cast('${timeSql}' as time)>slo.entrancetime then 2
		when cast('${timeSql}' as time)>slo.entrancetime then 2 else 1 end
	end 
	else null end 
	
	 as cnt24DaysTrudosposob
	 from medcase slo
	 left join patient pat on pat.id=slo.patient_id
	 left join vocsex vs on vs.id=pat.sex_id
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
		slo.dtype='DepartmentMedCase' 
		${departmentSql} ${serviceStreamSql} 
	and (to_date('${dateNextEnd}','dd.mm.yyyy')>slo.datestart
	or to_date('${dateNextEnd}','dd.mm.yyyy')=slo.dateStart and cast('${timeSql}' as time)>slo.entrancetime
	)
	and (slo.datefinish is null 
	or
	slo.datefinish>to_date('${dateBegin}','dd.mm.yyyy')
	or to_date('${dateBegin}','dd.mm.yyyy')=slo.datefinish and slo.dischargetime>=cast('${timeSql}' as time)
	)
	and (slo.transferdate is null 
	or slo.transferdate > to_date('${dateBegin}','dd.mm.yyyy')
	or to_date('${dateBegin}','dd.mm.yyyy')=slo.transferdate and slo.transfertime>=cast('${timeSql}' as time)
	)

	
	and slo.bedfund_id=${param.bedFund} ${bedTypeSql} ${bedSubTypeSql}
	order by pat.lastname,pat.firstname,pat.middlename
	      " />
	    <msh:table printToExcelButton="Сохранить в excel" name="journal_priem" viewUrl="entityShortView-stac_slo.do" action="entityParentView-stac_slo.do" idField="1">
	      <msh:tableColumn columnName="#" property="sn" />
	      <msh:tableColumn property="2" columnName="№СК"/>
	      <msh:tableColumn property="3" columnName="ФИО пациента"/>
	      <msh:tableColumn columnName="профиль коек" property="4" />
	      <msh:tableColumn columnName="поток обслуж." property="5" />
	      <msh:tableColumn columnName="Состоит на начало отчетного периода" property="6" />
	      <msh:tableColumn columnName="пост. всего" property="7" />
	      <msh:tableColumn columnName="в т.ч. из дневного стационар" property="8" />
	      <msh:tableColumn columnName="с.ж" property="9" />
	      <msh:tableColumn columnName="0-17 лет" property="10" />
	      <msh:tableColumn columnName="60 лет и старше" property="11" />
	      <msh:tableColumn columnName="старше труд. возраста" property="22" />
	      <msh:tableColumn columnName="перев. из других отд" property="12" />
	      <msh:tableColumn columnName="перев. в другие отд" property="13" />
	      <msh:tableColumn columnName="выписано" property="14" />
	      <msh:tableColumn columnName="старше труд. возраста" property="23" />
	      <msh:tableColumn columnName="в др. стац." property="15" />
	      <msh:tableColumn columnName="в кругл. стац." property="16" />
	      <msh:tableColumn columnName="в дн. стац." property="17" />
	      <msh:tableColumn columnName="умерло" property="18" />
	      <msh:tableColumn columnName="старше труд. возраста" property="24" />
	      <msh:tableColumn columnName="состоит всего" property="19" />
	      <msh:tableColumn columnName="состоит матерей" property="20" />
	      <msh:tableColumn isCalcAmount="true" columnName="к/дней" property="21" />
	      <msh:tableColumn isCalcAmount="true" columnName="старше труд. возраста" property="25" />
	    </msh:table>
	    </msh:sectionContent>
	    </msh:section>

	<%		
		} else {
		    	
		    if ( dep!=null && !dep.equals("") && !dep.equals("0")) { 	
		    	
		    if (view.equals("1")) {
	    	%>
	    
	    <msh:section>
	    <msh:sectionTitle>
	    <ecom:webQuery isReportBase="${isReportBase}" name="journal_priem" nameFldSql="journal_priem_sql" nativeSql="select
	    '&department=${param.department}&dateBegin=${dateBegin}&dateEnd=${dateEnd}&serviceStream=${serviceStream}&bedFund='||slo.bedfund_id as vbstid,
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
	or slo.dateStart between to_date('${dateNextBegin}','dd.mm.yyyy') and to_date('${dateEnd}','dd.mm.yyyy')
	or slo.datestart = to_date('${dateNextEnd}','dd.mm.yyyy') and cast('${timeSql}' as time)>slo.entrancetime) then slo.id else null end)
	as cnt6Entrance
	,count(distinct case when slo.prevmedcase_id is null and vht.code='DAYTIMEHOSP' and(slo.datestart = to_date('${dateBegin}','dd.mm.yyyy') and slo.entrancetime>=cast('${timeSql}:00' as time)
	or slo.dateStart between to_date('${dateNextBegin}','dd.mm.yyyy') and to_date('${dateEnd}','dd.mm.yyyy')
	or slo.datestart = to_date('${dateNextEnd}','dd.mm.yyyy') and cast('${timeSql}' as time)>slo.entrancetime) then slo.id else null end)
	as cnt7EntranceDayHosp
	,count(distinct case when slo.prevmedcase_id is null and (slo.datestart = to_date('${dateBegin}','dd.mm.yyyy') and slo.entrancetime>=cast('${timeSql}:00' as time)
	or slo.dateStart between to_date('${dateNextBegin}','dd.mm.yyyy') and to_date('${dateEnd}','dd.mm.yyyy')
	or slo.datestart = to_date('${dateNextEnd}','dd.mm.yyyy') and cast('${timeSql}' as time)>slo.entrancetime) 
	and (ad1.addressIsVillage='1')
	then slo.id else null end)
	as cnt8EntranceVillagers
	,count(distinct case when slo.prevmedcase_id is null and (slo.datestart = to_date('${dateBegin}','dd.mm.yyyy') and slo.entrancetime>=cast('${timeSql}:00' as time)
	or slo.dateStart between to_date('${dateNextBegin}','dd.mm.yyyy') and to_date('${dateEnd}','dd.mm.yyyy')
	or slo.datestart = to_date('${dateNextEnd}','dd.mm.yyyy') and cast('${timeSql}' as time)>slo.entrancetime)
	and (
	cast(to_char(slo.datestart,'yyyy') as int)-cast(to_char(pat.birthday,'yyyy') as int)
	+case when (cast(to_char(slo.datestart, 'mm') as int)-cast(to_char(pat.birthday, 'mm') as int)) <0 then -1 when (cast(to_char(slo.datestart,'dd') as int) - cast(to_char(pat.birthday,'dd') as int)<0) and ((cast(to_char(slo.datestart, 'mm') as int)-cast(to_char(pat.birthday, 'mm') as int)-1)<0)  then -1 else 0 end
	)<18
	then slo.id else null end)

	as cnt9EntranceTo17
	,count(distinct case when slo.prevmedcase_id is null and (slo.datestart = to_date('${dateBegin}','dd.mm.yyyy') and slo.entrancetime>=cast('${timeSql}:00' as time)
	or slo.dateStart between to_date('${dateNextBegin}','dd.mm.yyyy') and to_date('${dateEnd}','dd.mm.yyyy')
	or slo.datestart = to_date('${dateNextEnd}','dd.mm.yyyy') and cast('${timeSql}' as time)>slo.entrancetime) 
	and 
	60<=(
	cast(to_char(slo.datestart,'yyyy') as int)-cast(to_char(pat.birthday,'yyyy') as int)
	+case when (cast(to_char(slo.datestart, 'mm') as int)-cast(to_char(pat.birthday, 'mm') as int)) <0 then -1 when (cast(to_char(slo.datestart,'dd') as int) - cast(to_char(pat.birthday,'dd') as int)<0) and ((cast(to_char(slo.datestart, 'mm') as int)-cast(to_char(pat.birthday, 'mm') as int)-1)<0)  then -1 else 0 end
	)
	then slo.id else null end)
	as cnt10EntranceFrom60
	,count(distinct case when slo.prevmedcase_id is not null and (slo.datestart between to_date('${dateNextBegin}','dd.mm.yyyy') and to_date('${dateEnd}','dd.mm.yyyy')
	 or slo.datestart = to_date('${dateBegin}','dd.mm.yyyy') and slo.entrancetime>=cast('${timeSql}:00' as time)
	or slo.datestart = to_date('${dateNextEnd}','dd.mm.yyyy') and cast('${timeSql}' as time)>slo.entrancetime) then slo.id else null end)
	as cnt11TransferOutOtherDepartment
	,count(distinct case when  (slo.transferdate between to_date('${dateNextBegin}','dd.mm.yyyy') and to_date('${dateEnd}','dd.mm.yyyy')
	 or slo.transferdate = to_date('${dateBegin}','dd.mm.yyyy') and slo.transfertime>=cast('${timeSql}:00' as time)
	or slo.transferdate = to_date('${dateNextEnd}','dd.mm.yyyy') and cast('${timeSql}' as time)>slo.transfertime) then slo.id else null end) 
	as cnt12TransferInOtherDepartment
	,count(distinct case when vhr.code!='11' and (slo.dateFinish between to_date('${dateNextBegin}','dd.mm.yyyy') and to_date('${dateEnd}','dd.mm.yyyy')
	 or slo.dateFinish = to_date('${dateBegin}','dd.mm.yyyy') and slo.dischargetime>=cast('${timeSql}:00' as time)
	or slo.dateFinish = to_date('${dateNextEnd}','dd.mm.yyyy') and cast('${timeSql}' as time)>slo.dischargetime) then slo.id else null end) 
	as cnt13Finished
	,count(distinct case when vho.code='4' and vhr.code!='11' and (slo.dateFinish between to_date('${dateNextBegin}','dd.mm.yyyy') and to_date('${dateEnd}','dd.mm.yyyy')
	 or slo.dateFinish = to_date('${dateBegin}','dd.mm.yyyy') and slo.dischargetime>=cast('${timeSql}:00' as time)
	or slo.dateFinish = to_date('${dateNextEnd}','dd.mm.yyyy') and cast('${timeSql}' as time)>slo.dischargetime) then slo.id else null end) 
	as cnt14FinishedOtherHospital
	,count(distinct case when vho.code='3' and vhr.code!='11' and (slo.dateFinish between to_date('${dateNextBegin}','dd.mm.yyyy') and to_date('${dateEnd}','dd.mm.yyyy')
	 or slo.dateFinish = to_date('${dateBegin}','dd.mm.yyyy') and slo.dischargetime>=cast('${timeSql}:00' as time)
	or slo.dateFinish = to_date('${dateNextEnd}','dd.mm.yyyy') and cast('${timeSql}' as time)>slo.dischargetime) then slo.id else null end) 
	as cnt15FinishedHourHospital
	,count(distinct case when vho.code='2' and vhr.code!='11' and (slo.dateFinish between to_date('${dateNextBegin}','dd.mm.yyyy') and to_date('${dateEnd}','dd.mm.yyyy')
	 or slo.dateFinish = to_date('${dateBegin}','dd.mm.yyyy') and slo.dischargetime>=cast('${timeSql}:00' as time)
	or slo.dateFinish = to_date('${dateNextEnd}','dd.mm.yyyy') and cast('${timeSql}' as time)>slo.dischargetime) then slo.id else null end) 
	as cnt16FinishedDayHospital
	,count(distinct case when vhr.code='11' and (slo.dateFinish between to_date('${dateNextBegin}','dd.mm.yyyy') and to_date('${dateEnd}','dd.mm.yyyy')
	 or slo.dateFinish = to_date('${dateBegin}','dd.mm.yyyy') and slo.dischargetime>=cast('${timeSql}:00' as time)
	or slo.dateFinish = to_date('${dateNextEnd}','dd.mm.yyyy') and cast('${timeSql}' as time)>slo.dischargetime) then slo.id else null end) 
	as cnt17Death
	,count(distinct case when 
			(
				slo.transferdate is null
				or slo.transferdate > to_date('${dateNextEnd}','dd.mm.yyyy')
				or slo.transferdate = to_date('${dateNextEnd}','dd.mm.yyyy') and slo.transfertime>=cast('${timeSql}' as time)
			) and (
				slo.datefinish is null or
				slo.datefinish > to_date('${dateNextEnd}','dd.mm.yyyy')
				or slo.datefinish = to_date('${dateNextEnd}','dd.mm.yyyy') and slo.dischargetime>=cast('${timeSql}' as time)
			)
		 then slo.id else null end
	)
	 as cnt18CurrentTo
	 
	,count(distinct case when sls.hotelServices='1' and 
	 (
				slo.transferdate is null
				or slo.transferdate > to_date('${dateNextEnd}','dd.mm.yyyy')
				or slo.transferdate = to_date('${dateNextEnd}','dd.mm.yyyy') and slo.transfertime>=cast('${timeSql}' as time)
			) and (
				slo.datefinish is null or
				slo.datefinish > to_date('${dateNextEnd}','dd.mm.yyyy')
				or slo.datefinish = to_date('${dateNextEnd}','dd.mm.yyyy') and slo.dischargetime>=cast('${timeSql}' as time)
			) then slo.id else null end)
	 as cnt19CurrentMother
	 
	 	,sum(	 	
	 	
	case when 
		slo.dateFinish between to_date('${dateBegin}','dd.mm.yyyy')+2 and to_date('${dateEnd}','dd.mm.yyyy')
			or slo.dateFinish = to_date('${dateNextBegin}','dd.mm.yyyy') and slo.dischargetime>=cast('${timeSql}:00' as time)
			or slo.dateFinish = to_date('${dateNextEnd}','dd.mm.yyyy') and cast('${timeSql}' as time)>slo.dischargetime		
		then slo.dateFinish
			-case
				when slo.dateFinish=to_date('${dateEnd}','dd.mm.yyyy') and slo.dischargetime>=cast('${timeSql}:00' as time) then 1
				when slo.dateFinish = to_date('${dateNextEnd}','dd.mm.yyyy') and cast('${timeSql}' as time)>slo.dischargetime then 2
				when cast('${timeSql}' as time)>slo.dischargetime then 2 
				else 1 end
		when slo.dateFinish=to_date('${dateBegin}','dd.mm.yyyy') then slo.dateFinish-1
		when slo.dateFinish=to_date('${dateNextBegin}','dd.mm.yyyy') and cast('${timeSql}:00' as time)>slo.dischargetime then slo.dateFinish-2
	     when 
		slo.transferDate between to_date('${dateBegin}','dd.mm.yyyy')+2 and to_date('${dateEnd}','dd.mm.yyyy')
			or slo.transferDate = to_date('${dateNextBegin}','dd.mm.yyyy') and slo.transfertime>=cast('${timeSql}:00' as time)
			or slo.transferDate = to_date('${dateNextEnd}','dd.mm.yyyy') and cast('${timeSql}' as time)>slo.transfertime		
		then slo.transferDate
			-case 
			when slo.transferDate = to_date('${dateEnd}','dd.mm.yyyy') and slo.transfertime>=cast('${timeSql}:00' as time) then 1
			when slo.transferdate = to_date('${dateNextEnd}','dd.mm.yyyy') and cast('${timeSql}' as time)>slo.transfertime then 2
			when cast('${timeSql}' as time)>slo.transfertime then 2 else 1 end
		when slo.transferDate=to_date('${dateBegin}','dd.mm.yyyy') then slo.transferDate-1
		when slo.transferDate=to_date('${dateNextBegin}','dd.mm.yyyy') and cast('${timeSql}:00' as time)>slo.transfertime then slo.transferDate-2
		else to_date('${dateEnd}','dd.mm.yyyy')
		
		end
	-
	case when
		(slo.datestart = to_date('${dateBegin}','dd.mm.yyyy') and cast('${timeSql}:00' as time)>slo.entrancetime
		or to_date('${dateBegin}','dd.mm.yyyy')>slo.datestart)
		and (slo.datefinish is null 
		or slo.datefinish > to_date('${dateBegin}','dd.mm.yyyy') 
		or slo.datefinish = to_date('${dateBegin}','dd.mm.yyyy') and slo.dischargetime>=cast('${timeSql}' as time))
		and (slo.transferdate is null 
		or slo.transferdate > to_date('${dateBegin}','dd.mm.yyyy') 
		or
		slo.transferdate = to_date('${dateBegin}','dd.mm.yyyy') and slo.transfertime>=cast('${timeSql}' as time))
	then to_date('${datePrevBegin}','dd.mm.yyyy')
	else slo.dateStart - case 
		when slo.datestart = to_date('${dateBegin}','dd.mm.yyyy') and slo.entrancetime>=cast('${timeSql}' as time) then 1
		when slo.datestart = to_date('${dateNextBegin}','dd.mm.yyyy') and cast('${timeSql}' as time)>slo.entrancetime then 2
		when slo.datestart = to_date('${dateEnd}','dd.mm.yyyy') and slo.entrancetime>=cast('${timeSql}' as time) then 1
		when slo.datestart = to_date('${dateNextEnd}','dd.mm.yyyy') and cast('${timeSql}' as time)>slo.entrancetime then 2
		when cast('${timeSql}' as time)>slo.entrancetime then 2 else 1 end
	end
	
	
	
	
	
	
	) as cnt20Days
,count(distinct case when slo.prevmedcase_id is null and (slo.datestart between to_date('${dateNextBegin}','dd.mm.yyyy') and to_date('${dateEnd}','dd.mm.yyyy')
	 or slo.datestart = to_date('${dateBegin}','dd.mm.yyyy') and slo.entrancetime>=cast('${timeSql}:00' as time)
	or slo.datestart = to_date('${dateNextEnd}','dd.mm.yyyy') and cast('${timeSql}' as time)>slo.entrancetime) 
	and 
	case when vs.omccode='1' then 60 else 55 end
	 <=(cast(to_char(slo.datestart,'yyyy') as int)-cast(to_char(pat.birthday,'yyyy') as int)
	+case when (cast(to_char(slo.datestart, 'mm') as int)-cast(to_char(pat.birthday, 'mm') as int)) <0 then -1 when (cast(to_char(slo.datestart,'dd') as int) - cast(to_char(pat.birthday,'dd') as int)<0) and ((cast(to_char(slo.datestart, 'mm') as int)-cast(to_char(pat.birthday, 'mm') as int)-1)<0)  then -1 else 0 end
	)
	then slo.id else null end)
	as cnt21EntranceMoreTRUDOSPOSOB

,count(distinct case when vhr.code!='11' and (slo.dateFinish between to_date('${dateNextBegin}','dd.mm.yyyy') and to_date('${dateEnd}','dd.mm.yyyy')
	 or slo.dateFinish = to_date('${dateBegin}','dd.mm.yyyy') and slo.dischargetime>=cast('${timeSql}:00' as time)
	or slo.dateFinish = to_date('${dateNextEnd}','dd.mm.yyyy') and cast('${timeSql}' as time)>slo.dischargetime) and 
	case when vs.omccode='1' then 60 else 55 end
	 <=(
	cast(to_char(slo.datestart,'yyyy') as int)-cast(to_char(pat.birthday,'yyyy') as int)
	+case when (cast(to_char(slo.datestart, 'mm') as int)-cast(to_char(pat.birthday, 'mm') as int)) <0 then -1 when (cast(to_char(slo.datestart,'dd') as int) - cast(to_char(pat.birthday,'dd') as int)<0) and ((cast(to_char(slo.datestart, 'mm') as int)-cast(to_char(pat.birthday, 'mm') as int)-1)<0)  then -1 else 0 end
	) then slo.id else null end) 
	as cnt22FinishedTrudosposob	

,count(distinct case when vhr.code='11' and (slo.dateFinish between to_date('${dateNextBegin}','dd.mm.yyyy') and to_date('${dateEnd}','dd.mm.yyyy')
	 or slo.dateFinish = to_date('${dateBegin}','dd.mm.yyyy') and slo.dischargetime>=cast('${timeSql}:00' as time)
	or slo.dateFinish = to_date('${dateNextEnd}','dd.mm.yyyy') and cast('${timeSql}' as time)>slo.dischargetime) and 
	case when vs.omccode='1' then 60 else 55 end
	 <=(
	cast(to_char(slo.datestart,'yyyy') as int)-cast(to_char(pat.birthday,'yyyy') as int)
	+case when (cast(to_char(slo.datestart, 'mm') as int)-cast(to_char(pat.birthday, 'mm') as int)) <0 then -1 when (cast(to_char(slo.datestart,'dd') as int) - cast(to_char(pat.birthday,'dd') as int)<0) and ((cast(to_char(slo.datestart, 'mm') as int)-cast(to_char(pat.birthday, 'mm') as int)-1)<0)  then -1 else 0 end
	) then slo.id else null end) 
	as cnt23DeathTrudosposob

,sum(	 case when 	
	 case when vs.omccode='1' then 60 else 55 end
	 <=(
	cast(to_char(slo.datestart,'yyyy') as int)-cast(to_char(pat.birthday,'yyyy') as int)
	+case when (cast(to_char(slo.datestart, 'mm') as int)-cast(to_char(pat.birthday, 'mm') as int)) <0 then -1 when (cast(to_char(slo.datestart,'dd') as int) - cast(to_char(pat.birthday,'dd') as int)<0) and ((cast(to_char(slo.datestart, 'mm') as int)-cast(to_char(pat.birthday, 'mm') as int)-1)<0)  then -1 else 0 end
	)	then 
	case when 
		slo.dateFinish between to_date('${dateBegin}','dd.mm.yyyy')+2 and to_date('${dateEnd}','dd.mm.yyyy')
			or slo.dateFinish = to_date('${dateNextBegin}','dd.mm.yyyy') and slo.dischargetime>=cast('${timeSql}:00' as time)
			or slo.dateFinish = to_date('${dateNextEnd}','dd.mm.yyyy') and cast('${timeSql}' as time)>slo.dischargetime		
		then slo.dateFinish
			-case
				when slo.dateFinish=to_date('${dateEnd}','dd.mm.yyyy') and slo.dischargetime>=cast('${timeSql}:00' as time) then 1
				when slo.dateFinish = to_date('${dateNextEnd}','dd.mm.yyyy') and cast('${timeSql}' as time)>slo.dischargetime then 2
				when cast('${timeSql}' as time)>slo.dischargetime then 2 
				else 1 end
		when slo.dateFinish=to_date('${dateBegin}','dd.mm.yyyy') then slo.dateFinish-1
		when slo.dateFinish=to_date('${dateNextBegin}','dd.mm.yyyy') and cast('${timeSql}:00' as time)>slo.dischargetime then slo.dateFinish-2
	     when 
		slo.transferDate between to_date('${dateBegin}','dd.mm.yyyy')+2 and to_date('${dateEnd}','dd.mm.yyyy')
			or slo.transferDate = to_date('${dateNextBegin}','dd.mm.yyyy') and slo.transfertime>=cast('${timeSql}:00' as time)
			or slo.transferDate = to_date('${dateNextEnd}','dd.mm.yyyy') and cast('${timeSql}' as time)>slo.transfertime		
		then slo.transferDate
			-case 
			when slo.transferDate = to_date('${dateEnd}','dd.mm.yyyy') and slo.transfertime>=cast('${timeSql}:00' as time) then 1
			when slo.transferdate = to_date('${dateNextEnd}','dd.mm.yyyy') and cast('${timeSql}' as time)>slo.transfertime then 2
			when cast('${timeSql}' as time)>slo.transfertime then 2 else 1 end
		when slo.transferDate=to_date('${dateBegin}','dd.mm.yyyy') then slo.transferDate-1
		when slo.transferDate=to_date('${dateNextBegin}','dd.mm.yyyy') and cast('${timeSql}:00' as time)>slo.transfertime then slo.transferDate-2
		else to_date('${dateEnd}','dd.mm.yyyy')
		
		end
	-
	case when
		(slo.datestart = to_date('${dateBegin}','dd.mm.yyyy') and cast('${timeSql}:00' as time)>slo.entrancetime
		or to_date('${dateBegin}','dd.mm.yyyy')>slo.datestart)
		and (slo.datefinish is null 
		or slo.datefinish > to_date('${dateBegin}','dd.mm.yyyy') 
		or slo.datefinish = to_date('${dateBegin}','dd.mm.yyyy') and slo.dischargetime>=cast('${timeSql}' as time))
		and (slo.transferdate is null 
		or slo.transferdate > to_date('${dateBegin}','dd.mm.yyyy') 
		or
		slo.transferdate = to_date('${dateBegin}','dd.mm.yyyy') and slo.transfertime>=cast('${timeSql}' as time))
	then to_date('${datePrevBegin}','dd.mm.yyyy')
	else slo.dateStart - case 
		when slo.datestart = to_date('${dateBegin}','dd.mm.yyyy') and slo.entrancetime>=cast('${timeSql}' as time) then 1
		when slo.datestart = to_date('${dateNextBegin}','dd.mm.yyyy') and cast('${timeSql}' as time)>slo.entrancetime then 2
		when slo.datestart = to_date('${dateEnd}','dd.mm.yyyy') and slo.entrancetime>=cast('${timeSql}' as time) then 1
		when slo.datestart = to_date('${dateNextEnd}','dd.mm.yyyy') and cast('${timeSql}' as time)>slo.entrancetime then 2
		when cast('${timeSql}' as time)>slo.entrancetime then 2 else 1 end
	end 
	else null end 
	
	) as cnt24DaysTrudosposob
	 from medcase slo
	 left join patient pat on pat.id=slo.patient_id
	 left join vocsex vs on vs.id=pat.sex_id
	 left join medcase sls on sls.id=slo.parent_id
	 left join vochospitalizationoutcome vho on vho.id=sls.outcome_id
	 left join vochospitalizationresult vhr on vhr.id=sls.result_id
	left join bedfund bf on bf.id=slo.bedfund_id
	left join vocbedsubtype vbst on vbst.id=bf.bedsubtype_id
	left join vocbedtype vbt on vbt.id=bf.bedtype_id
	left join vocservicestream vss on vss.id=bf.servicestream_id
	left join vochosptype vht on vht.id=sls.sourceHospType_id
	LEFT JOIN Address2 ad1 on ad1.addressId=pat.address_addressId 
	where 
	slo.dtype='DepartmentMedCase' 
	and (to_date('${dateNextEnd}','dd.mm.yyyy')>slo.datestart
	or to_date('${dateNextEnd}','dd.mm.yyyy')=slo.dateStart and cast('${timeSql}' as time)>slo.entrancetime
	)
	and (slo.datefinish is null 
	or
	slo.datefinish>to_date('${dateBegin}','dd.mm.yyyy')
	or to_date('${dateBegin}','dd.mm.yyyy')=slo.datefinish and slo.dischargetime>=cast('${timeSql}' as time)
	)
	and (slo.transferdate is null 
	or slo.transferdate > to_date('${dateBegin}','dd.mm.yyyy')
	or to_date('${dateBegin}','dd.mm.yyyy')=slo.transferdate and slo.transfertime>=cast('${timeSql}' as time)
	)
	${departmentSql} ${serviceStreamSql} ${bedTypeSql} ${bedSubTypeSql}
	
	group by slo.bedfund_id,bf.bedsubtype_id,vbst.name,vbt.name,bf.serviceStream_id,vss.name
	      " />
	    
	    
	    <form action="print-stac_report016_1.do" method="post" target="_blank">
	    016/у-02 форма
	    <input type='hidden' name="sqlText" id="sqlText" value="${journal_priem_sql}"> 
	    <input type='hidden' name="sqlInfo" id="sqlInfo" value="16 форма ${param.dateBegin}-${param.dateEnd}.">
	    <input type='hidden' name="sqlColumn" id="sqlColumn" value="${groupName}">
	    <input type='hidden' name="s" id="s" value="PrintService"><input type='hidden' name="isReportBase" id="isReportBase" value="${isReportBase}">
	    <input type='hidden' name="m" id="m" value="printNativeQuery">
	    <input type="submit" value="Печать"> 
	    </form>     
	    </msh:sectionTitle>	    
	    
	    <msh:sectionContent>
	    <msh:table name="journal_priem" 
	    viewUrl="stac_report_016.do?short=Short" 
	    action="stac_report_016.do" idField="1">
	      <msh:tableColumn columnName="#" property="sn" />
	      <msh:tableColumn columnName="профиль коек" property="2" />
	      <msh:tableColumn columnName="поток обслуживания" property="3" />
	      <msh:tableColumn isCalcAmount="true" columnName="Состоит на начало истекших суток" property="4" />
	      <msh:tableColumn isCalcAmount="true" columnName="поступило всего" property="5" />
	      <msh:tableColumn isCalcAmount="true" columnName="в т.ч. из дневного стационар" property="6" />
	      <msh:tableColumn isCalcAmount="true" columnName="с.ж" property="7" />
	      <msh:tableColumn isCalcAmount="true" columnName="0-17 лет" property="8" />
	      <msh:tableColumn isCalcAmount="true" columnName="60 лет и старше" property="9" />
	      <msh:tableColumn isCalcAmount="true" columnName="в т.ч. старше труд. возраста" property="20" />
	      <msh:tableColumn isCalcAmount="true" columnName="переведено из других отд" property="10" />
	      <msh:tableColumn isCalcAmount="true" columnName="переведено в другие отд" property="11" />
	      <msh:tableColumn isCalcAmount="true" columnName="выписано" property="12" />
	      <msh:tableColumn isCalcAmount="true" columnName="в т.ч. старше труд. возраста" property="21" />
	      <msh:tableColumn isCalcAmount="true" columnName="в другие стационары" property="13" />
	      <msh:tableColumn isCalcAmount="true" columnName="в круглосуточный стационар" property="14" />
	      <msh:tableColumn isCalcAmount="true" columnName="в дневной стационар" property="15" />
	      <msh:tableColumn isCalcAmount="true" columnName="умерло" property="16" />
	      <msh:tableColumn isCalcAmount="true" columnName="в т.ч. старше труд. возраста" property="22" />
	      <msh:tableColumn isCalcAmount="true" columnName="состоит всего" property="17" />
	      <msh:tableColumn isCalcAmount="true" columnName="состоит матерей" property="18" />
	      <msh:tableColumn isCalcAmount="true" columnName="проведено койко дней" property="19" />
	      <msh:tableColumn isCalcAmount="true" columnName="в т.ч. старше труд. возраста" property="23" />
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
	    <ecom:webQuery isReportBase="${isReportBase}" name="journal_priem01" nativeSql="select slo.id as sloid
	    	,ss.code as sscode
	    	,pat.lastname||' '||substring(pat.firstname,1,1)||' '||coalesce(substring(pat.middlename,1,1),'')||' '||case when slo.prevmedcase_id is not null then ' ('||pdep.name||')' else '' end as fio
	    	,to_char(slo.dateStart,'dd.mm.yyyy') ||' '|| cast(slo.entrancetime as varchar(5)) as slodateStart
	    	,to_char(slo.transferdate,'dd.mm.yyyy') ||' '|| cast(slo.transfertime as varchar(5)) as slotransferdate
	    	,to_char(slo.datefinish,'dd.mm.yyyy') ||' '|| cast(slo.dischargetime as varchar(5)) as slodatefinish
	    	,vss.name as vssname
	from medcase slo
	left join bedfund bf on bf.id=slo.bedfund_id
	left join medcase sls on sls.id=slo.parent_id
	 left join VocServiceStream vss on vss.id=slo.serviceStream_id
	left join statisticstub ss on ss.medcase_id=sls.id
	left join patient pat on slo.patient_id=pat.id
	left join medcase pslo on pslo.id=slo.prevmedcase_id
	left join mislpu pdep on pdep.id=pslo.department_id
	where  slo.dtype='DepartmentMedCase' ${departmentSql} ${serviceStreamSql} ${bedTypeSql} ${bedSubTypeSql}
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
	    <msh:table  printToExcelButton="Сохранить в excel" name="journal_priem01" viewUrl="entityShortView-stac_slo.do" action="entityParentView-stac_slo.do" idField="1">
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
	    <ecom:webQuery isReportBase="${isReportBase}" name="journal_priem02" nativeSql="select slo.id as sloid
	    	,ss.code as sscode
	    	,pat.lastname||' '||substring(pat.firstname,1,1)||' '||coalesce(substring(pat.middlename,1,1),'')||' '||case when slo.prevmedcase_id is not null then ' ('||pdep.name||')' else '' end as fio
	    	,to_char(slo.dateStart,'dd.mm.yyyy') ||' '|| cast(slo.entrancetime as varchar(5)) as slodateStart
	    	,to_char(slo.transferdate,'dd.mm.yyyy') ||' '|| cast(slo.transfertime as varchar(5)) as slotransferdate
	    	,to_char(slo.datefinish,'dd.mm.yyyy') ||' '|| cast(slo.dischargetime as varchar(5)) as slodatefinish
	    	,vss.name as vssname
	from medcase slo
	left join bedfund bf on bf.id=slo.bedfund_id
	left join medcase sls on sls.id=slo.parent_id
	left join VocServiceStream vss on vss.id=slo.serviceStream_id
	left join statisticstub ss on ss.medcase_id=sls.id
	left join patient pat on slo.patient_id=pat.id
	left join medcase pslo on pslo.id=slo.prevmedcase_id
	left join mislpu pdep on pdep.id=pslo.department_id
	where  slo.dtype='DepartmentMedCase' ${departmentSql} ${serviceStreamSql} ${bedTypeSql} ${bedSubTypeSql}
	and (to_date('${dateNextEnd}','dd.mm.yyyy')>slo.datestart
	or to_date('${dateNextEnd}','dd.mm.yyyy')=slo.dateStart and cast('${timeSql}' as time)>slo.entrancetime
	)
	and (slo.datefinish is null 
	or
	slo.datefinish>to_date('${dateBegin}','dd.mm.yyyy')
	or to_date('${dateBegin}','dd.mm.yyyy')=slo.datefinish and slo.dischargetime>=cast('${timeSql}' as time)
	)
	and (slo.transferdate is null 
	or slo.transferdate > to_date('${dateBegin}','dd.mm.yyyy')
	or to_date('${dateBegin}','dd.mm.yyyy')=slo.transferdate and slo.transfertime>=cast('${timeSql}' as time)
	)
	
	and 
	(
		slo.transferdate is null or slo.transferdate > to_date('${dateNextEnd}','dd.mm.yyyy')
		or slo.transferdate = to_date('${dateNextEnd}','dd.mm.yyyy') and slo.transfertime>=cast('${timeSql}' as time)
	) and (
		slo.datefinish is null or slo.datefinish > to_date('${dateNextEnd}','dd.mm.yyyy')
		or slo.datefinish = to_date('${dateNextEnd}','dd.mm.yyyy') and slo.dischargetime>=cast('${timeSql}' as time)
	)

	order by pat.lastname,pat.firstname,pat.middlename

	      " />
	    <msh:table printToExcelButton="Сохранить в excel" name="journal_priem02" viewUrl="entityShortView-stac_slo.do" action="entityParentView-stac_slo.do" idField="1">
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
	    <ecom:webQuery isReportBase="${isReportBase}" name="journal_priem1" nativeSql="select slo.id as sloid
	    	,ss.code as sscode
	    	,pat.lastname||' '||substring(pat.firstname,1,1)||' '||coalesce(substring(pat.middlename,1,1),'') as fio
	    	,to_char(slo.dateStart,'dd.mm.yyyy') ||' '|| cast(slo.entrancetime as varchar(5)) as slodateStart
	    	,to_char(slo.transferdate,'dd.mm.yyyy') ||' '|| cast(slo.transfertime as varchar(5)) as slotransferdate
	    	,to_char(slo.datefinish,'dd.mm.yyyy') ||' '|| cast(slo.dischargetime as varchar(5)) as slodatefinish
	    	,vss.name
	from medcase slo
	left join bedfund bf on bf.id=slo.bedfund_id
	left join VocServiceStream vss on vss.id=slo.serviceStream_id
	left join medcase sls on sls.id=slo.parent_id
	left join statisticstub ss on ss.medcase_id=sls.id
	left join patient pat on slo.patient_id=pat.id
	left join medcase pslo on pslo.id=slo.prevmedcase_id
	left join mislpu pdep on pdep.id=pslo.department_id
	left join vochosptype vht on vht.id=sls.sourceHospType_id
	where  slo.dtype='DepartmentMedCase' ${departmentSql} ${serviceStreamSql} ${bedTypeSql} ${bedSubTypeSql} and (slo.datestart = to_date('${dateBegin}','dd.mm.yyyy') and slo.entrancetime>=cast('${timeSql}:00' as time)
	or slo.dateStart between to_date('${dateNextBegin}','dd.mm.yyyy') and to_date('${dateEnd}','dd.mm.yyyy')
	or slo.datestart = to_date('${dateNextEnd}','dd.mm.yyyy') and cast('${timeSql}' as time)>slo.entrancetime)
	and slo.prevMedCase_id is null
	and (vht.code is null or vht.code!='ALLTIMEHOSP')
	order by pat.lastname,pat.firstname,pat.middlename
	      " />
	    <msh:table printToExcelButton="Сохранить в excel" name="journal_priem1" viewUrl="entityShortView-stac_slo.do" action="entityParentView-stac_slo.do" idField="1">
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
	    <ecom:webQuery isReportBase="${isReportBase}" name="journal_priem1" nativeSql="select slo.id as sloid
	    	,ss.code as sscode
	    	,pat.lastname||' '||substring(pat.firstname,1,1)||' '||coalesce(substring(pat.middlename,1,1),'') as fio,pdep.name as dep
	    	,to_char(slo.dateStart,'dd.mm.yyyy') ||' '|| cast(slo.entrancetime as varchar(5)) as slodateStart
	    	,to_char(slo.transferdate,'dd.mm.yyyy') ||' '|| cast(slo.transfertime as varchar(5)) as slotransferdate
	    	,to_char(slo.datefinish,'dd.mm.yyyy') ||' '|| cast(slo.dischargetime as varchar(5)) as slodatefinish
	    	,vss.name as vssname
	from medcase slo 
	left join bedfund bf on bf.id=slo.bedfund_id
	left join VocServiceStream vss on vss.id=slo.serviceStream_id
	left join medcase sls on sls.id=slo.parent_id
	left join statisticstub ss on ss.medcase_id=sls.id
	left join patient pat on slo.patient_id=pat.id
	left join medcase pslo on pslo.id=slo.prevmedcase_id
	left join mislpu pdep on pdep.id=pslo.department_id
	left join vochosptype vht on vht.id=sls.sourceHospType_id
	where  slo.dtype='DepartmentMedCase' ${departmentSql} ${serviceStreamSql} ${bedTypeSql} ${bedSubTypeSql} and (slo.datestart = to_date('${dateBegin}','dd.mm.yyyy') and slo.entrancetime>=cast('${timeSql}:00' as time)
	or slo.dateStart between to_date('${dateNextBegin}','dd.mm.yyyy') and to_date('${dateEnd}','dd.mm.yyyy')
	or slo.datestart = to_date('${dateNextEnd}','dd.mm.yyyy') and cast('${timeSql}' as time)>slo.entrancetime)
	and slo.prevMedCase_id is not null
	order by pat.lastname,pat.firstname,pat.middlename
	      " />
	    <msh:table printToExcelButton="Сохранить в excel" name="journal_priem1" viewUrl="entityShortView-stac_slo.do" action="entityParentView-stac_slo.do" idField="1">
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
	    <ecom:webQuery isReportBase="${isReportBase}" name="journal_priem2" nativeSql=" select slo.id as sloid
	    	,ss.code as sscode
	    	,pat.lastname||' '||substring(pat.firstname,1,1)||' '||coalesce(substring(pat.middlename,1,1),'')||' '||case when slo.prevmedcase_id is not null then ' ('||pdep.name||')' else '' end as fio
	    	,to_char(slo.dateStart,'dd.mm.yyyy') ||' '|| cast(slo.entrancetime as varchar(5)) as slodateStart
	    	,to_char(slo.transferdate,'dd.mm.yyyy') ||' '|| cast(slo.transfertime as varchar(5)) as slotransferdate
	    	,to_char(slo.datefinish,'dd.mm.yyyy') ||' '|| cast(slo.dischargetime as varchar(5)) as slodatefinish
	    	,vss.name as vssname
	from medcase slo
	left join bedfund bf on bf.id=slo.bedfund_id
	 left join VocServiceStream vss on vss.id=slo.serviceStream_id
	left join medcase sls on sls.id=slo.parent_id
	left join statisticstub ss on ss.medcase_id=sls.id
	left join patient pat on slo.patient_id=pat.id
	left join medcase pslo on pslo.id=slo.prevmedcase_id
	left join mislpu pdep on pdep.id=pslo.department_id
	left join vochosptype vht on vht.id=sls.sourceHospType_id
	where  slo.dtype='DepartmentMedCase' ${departmentSql} ${serviceStreamSql} ${bedTypeSql} ${bedSubTypeSql} and (slo.datestart = to_date('${dateBegin}','dd.mm.yyyy') and slo.entrancetime>=cast('${timeSql}:00' as time)
	or slo.dateStart between to_date('${dateNextBegin}','dd.mm.yyyy') and to_date('${dateEnd}','dd.mm.yyyy')
	or slo.datestart = to_date('${dateNextEnd}','dd.mm.yyyy') and cast('${timeSql}' as time)>slo.entrancetime)
	and slo.prevMedCase_id is null  
	and vht.code='ALLTIMEHOSP'
	order by pat.lastname,pat.firstname,pat.middlename
	      " />
	    <msh:table printToExcelButton="Сохранить в excel" name="journal_priem2" viewUrl="entityShortView-stac_slo.do" action="entityParentView-stac_slo.do" idField="1">
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
	    <ecom:webQuery isReportBase="${isReportBase}" name="journal_priem3" nativeSql="select slo.id as sloid
	    	,ss.code as sscode
	    	,pat.lastname||' '||substring(pat.firstname,1,1)||' '||coalesce(substring(pat.middlename,1,1),'')||' '||case when slo.prevmedcase_id is not null then ' ('||pdep.name||')' else '' end as fio
	    	,to_char(slo.dateStart,'dd.mm.yyyy') ||' '|| cast(slo.entrancetime as varchar(5)) as slodateStart
	    	,to_char(slo.transferdate,'dd.mm.yyyy') ||' '|| cast(slo.transfertime as varchar(5)) as slotransferdate
	    	,to_char(slo.datefinish,'dd.mm.yyyy') ||' '|| cast(slo.dischargetime as varchar(5)) as slodatefinish
	    	,vss.name as vssname
	    	,vho.name as vhoname
	from medcase slo
	left join bedfund bf on bf.id=slo.bedfund_id
	 left join VocServiceStream vss on vss.id=slo.serviceStream_id
	left join medcase sls on sls.id=slo.parent_id
	left join statisticstub ss on ss.medcase_id=sls.id
	left join patient pat on slo.patient_id=pat.id
	left join medcase pslo on pslo.id=slo.prevmedcase_id
	left join mislpu pdep on pdep.id=pslo.department_id
	left join vochosptype vht on vht.id=sls.sourceHospType_id
	left join vochospitalizationoutcome vho on vho.id=sls.outcome_id
	left join vochospitalizationresult vhr on vhr.id=sls.result_id
	where  slo.dtype='DepartmentMedCase' ${departmentSql} ${serviceStreamSql} ${bedTypeSql} ${bedSubTypeSql}
	 and (slo.dateFinish between to_date('${dateNextBegin}','dd.mm.yyyy') and to_date('${dateEnd}','dd.mm.yyyy')
	 or slo.dateFinish = to_date('${dateBegin}','dd.mm.yyyy') and slo.dischargetime>=cast('${timeSql}:00' as time)
	or slo.dateFinish = to_date('${dateNextEnd}','dd.mm.yyyy') and cast('${timeSql}' as time)>slo.dischargetime)
	
	
	and vhr.code!='11'
	order by pat.lastname,pat.firstname,pat.middlename
	      " />
	    <msh:table printToExcelButton="Сохранить в excel" name="journal_priem3" viewUrl="entityShortView-stac_slo.do" action="entityParentView-stac_slo.do" idField="1">
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
	    <ecom:webQuery isReportBase="${isReportBase}" name="journal_priem4" nativeSql="select slo.id as sloid
	    	,ss.code as sscode
	    	,pat.lastname||' '||substring(pat.firstname,1,1)||' '||coalesce(substring(pat.middlename,1,1),'')||' '||case when slo.prevmedcase_id is not null then ' ('||pdep.name||')' else '' end as fio
	    	,tdep.name as tdepname
	    	,to_char(slo.dateStart,'dd.mm.yyyy') ||' '|| cast(slo.entrancetime as varchar(5)) as slodateStart
	    	,to_char(slo.transferdate,'dd.mm.yyyy') ||' '|| cast(slo.transfertime as varchar(5)) as slotransferdate
	    	,to_char(slo.datefinish,'dd.mm.yyyy') ||' '|| cast(slo.dischargetime as varchar(5)) as slodatefinish
	    	,vss.name as vssname
	from medcase slo
	left join bedfund bf on bf.id=slo.bedfund_id
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
	where  slo.dtype='DepartmentMedCase' ${departmentSql} ${serviceStreamSql} ${bedTypeSql} ${bedSubTypeSql} 
	 and (slo.transferDate between to_date('${dateNextBegin}','dd.mm.yyyy') and to_date('${dateEnd}','dd.mm.yyyy')
	 or slo.transferDate = to_date('${dateBegin}','dd.mm.yyyy') and slo.transfertime>=cast('${timeSql}:00' as time)
	or slo.transferDate = to_date('${dateNextEnd}','dd.mm.yyyy') and cast('${timeSql}' as time)>slo.transferTime)
	
	order by pat.lastname,pat.firstname,pat.middlename
	      " />
	    <msh:table printToExcelButton="Сохранить в excel" name="journal_priem4" viewUrl="entityShortView-stac_slo.do" action="entityParentView-stac_slo.do" idField="1">
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
	    <ecom:webQuery isReportBase="${isReportBase}" name="journal_priem4" nativeSql="select slo.id as sloid
	    	,ss.code as sscode
	    	,pat.lastname||' '||substring(pat.firstname,1,1)||' '||coalesce(substring(pat.middlename,1,1),'')||' '||case when slo.prevmedcase_id is not null then ' ('||pdep.name||')' else '' end as fio
	    	,slo.dateStart ||' '|| cast(slo.entrancetime as varchar(5)) as slodateStart
	    	,slo.transferdate ||' '|| cast(slo.transfertime as varchar(5)) as slotransferdate
	    	,slo.datefinish ||' '|| cast(slo.dischargetime as varchar(5)) as slodatefinish
	    	,vss.name as vssname
	from medcase slo
	left join bedfund bf on bf.id=slo.bedfund_id
	 left join VocServiceStream vss on vss.id=slo.serviceStream_id
	left join medcase sls on sls.id=slo.parent_id
	left join statisticstub ss on ss.medcase_id=sls.id
	left join patient pat on slo.patient_id=pat.id
	left join medcase pslo on pslo.id=slo.prevmedcase_id
	left join mislpu pdep on pdep.id=pslo.department_id
	left join vochosptype vht on vht.id=sls.sourceHospType_id
	left join vochospitalizationoutcome vho on vho.id=sls.outcome_id
	left join vochospitalizationresult vhr on vhr.id=sls.result_id
	where  slo.dtype='DepartmentMedCase' ${departmentSql} ${serviceStreamSql} ${bedTypeSql} ${bedSubTypeSql}
	
     and (slo.dateFinish between to_date('${dateNextBegin}','dd.mm.yyyy') and to_date('${dateEnd}','dd.mm.yyyy')
	 or slo.dateFinish = to_date('${dateBegin}','dd.mm.yyyy') and slo.dischargetime>=cast('${timeSql}:00' as time)
	or slo.dateFinish = to_date('${dateNextEnd}','dd.mm.yyyy') and cast('${timeSql}' as time)>slo.dischargetime)
	
	and vhr.code='11'
	order by pat.lastname,pat.firstname,pat.middlename
	      " />
	    <msh:table printToExcelButton="Сохранить в excel" name="journal_priem4" viewUrl="entityShortView-stac_slo.do" action="entityParentView-stac_slo.do" idField="1">
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
	    	if (view!=null &&(view.equals("5") || view.equals("6")|| view.equals("7")||view.equals("8"))) {} else {view="4";}
	    }
	    	
	    	if (view.equals("4") || view.equals("5") || view.equals("6")) {
	    		
	    		if (view.equals("4")) {
	    			request.setAttribute("queryField", "lpu.name") ;
	           		request.setAttribute("queryId", "department='||lpu.id") ;
	           		request.setAttribute("queryBedFundSql", "bf1.lpu_id=lpu.id") ;
	           		request.setAttribute("queryTitle", "Свод по отделениям") ;
	           		request.setAttribute("queryName", "Отделение") ;
	           		request.setAttribute("queryGroup", "lpu.id,lpu.name") ;
	           		request.setAttribute("queryGroupNext", "5") ;
	           		request.setAttribute("queryOrder", "lpu.name") ;	    			
	    		} else if (view.equals("5")) {
	    			request.setAttribute("queryField", "vbt.name||'-'||lpu.name") ;
	           		request.setAttribute("queryBedFundSql", "bf1.lpu_id=lpu.id and bf1.bedType_id=bf.bedType_id") ;
	           		request.setAttribute("queryId", "department='||lpu.id||'&bedType='||bf.bedType_id") ;
	           		request.setAttribute("queryTitle", "Свод по отделениям и профилям коек") ;
	           		request.setAttribute("queryName", "Отделение и профиль коек") ;
	           		request.setAttribute("queryGroup", "lpu.id,lpu.name,bf.bedType_id,vbt.name") ;
	           		request.setAttribute("queryGroupNext", "1") ;
	           		request.setAttribute("queryOrder", "vbt.name,lpu.name") ;	    			
	    		} else {
	    			request.setAttribute("queryField", "list(distinct lpu.name)") ;
	           		request.setAttribute("queryBedFundSql", "bf1.bedSubType_id=bf.bedSubType_id") ;
	           		request.setAttribute("queryId", "&bedSubType='||bf.bedSubType_id") ;
	           		request.setAttribute("queryTitle", "Свод типу коек") ;
	           		request.setAttribute("queryName", "Отделения") ;
	           		request.setAttribute("queryGroup", "bf.bedSubType_id,vbst.name") ;
	           		request.setAttribute("queryGroupNext", "4") ;
	           		request.setAttribute("queryOrder", "vbst.name") ;	    			
	    		}
	    		
	    		%>
	    		
	    		
	    		    <msh:section>
	    <msh:sectionTitle>	    
	    <ecom:webQuery isReportBase="${isReportBase}" nameFldSql="journal_priem_sql" name="journal_priem" nativeSql="select '&dateBegin=${dateBegin}&dateEnd=${dateEnd}&serviceStream=${serviceStream}&typeView=${queryGroupNext}&${queryId}||${departmentSqlId} as fldId,${queryField} as fldName,list(distinct vbst.name) as vbstname,list(distinct vss.name) as vssname
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
	,count(distinct case when slo.prevmedcase_id is null and (slo.datestart between to_date('${dateNextBegin}','dd.mm.yyyy') and to_date('${dateEnd}','dd.mm.yyyy')
	 or slo.datestart = to_date('${dateBegin}','dd.mm.yyyy') and slo.entrancetime>=cast('${timeSql}:00' as time)
	or slo.datestart = to_date('${dateNextEnd}','dd.mm.yyyy') and cast('${timeSql}' as time)>slo.entrancetime) then slo.id else null end)
	as cnt6Entrance
	,count(distinct case when slo.prevmedcase_id is null and vht.code='DAYTIMEHOSP' and (slo.datestart between to_date('${dateNextBegin}','dd.mm.yyyy') and to_date('${dateEnd}','dd.mm.yyyy')
	 or slo.datestart = to_date('${dateBegin}','dd.mm.yyyy') and slo.entrancetime>=cast('${timeSql}:00' as time)
	or slo.datestart = to_date('${dateNextEnd}','dd.mm.yyyy') and cast('${timeSql}' as time)>slo.entrancetime) then slo.id else null end)
	as cnt7EntranceDayHosp
	,count(distinct case when slo.prevmedcase_id is null and a.addressIsVillage='1' and (slo.datestart between to_date('${dateNextBegin}','dd.mm.yyyy') and to_date('${dateEnd}','dd.mm.yyyy')
	 or slo.datestart = to_date('${dateBegin}','dd.mm.yyyy') and slo.entrancetime>=cast('${timeSql}:00' as time)
	or slo.datestart = to_date('${dateNextEnd}','dd.mm.yyyy') and cast('${timeSql}' as time)>slo.entrancetime) then slo.id else null end)
	as cnt7EntrV
	
	,count(distinct case when slo.prevmedcase_id is null and (slo.datestart between to_date('${dateNextBegin}','dd.mm.yyyy') and to_date('${dateEnd}','dd.mm.yyyy')
	 or slo.datestart = to_date('${dateBegin}','dd.mm.yyyy') and slo.entrancetime>=cast('${timeSql}:00' as time)
	or slo.datestart = to_date('${dateNextEnd}','dd.mm.yyyy') and cast('${timeSql}' as time)>slo.entrancetime) 
	and (
	cast(to_char(slo.datestart,'yyyy') as int)-cast(to_char(pat.birthday,'yyyy') as int)
	+case when (cast(to_char(slo.datestart, 'mm') as int)-cast(to_char(pat.birthday, 'mm') as int)) <0 then -1 when (cast(to_char(slo.datestart,'dd') as int) - cast(to_char(pat.birthday,'dd') as int)<0) and ((cast(to_char(slo.datestart, 'mm') as int)-cast(to_char(pat.birthday, 'mm') as int)-1)<0)  then -1 else 0 end
	)<18
	then slo.id else null end)

	as cnt9EntranceTo17
	,count(distinct case when slo.prevmedcase_id is null and (slo.datestart between to_date('${dateNextBegin}','dd.mm.yyyy') and to_date('${dateEnd}','dd.mm.yyyy')
	 or slo.datestart = to_date('${dateBegin}','dd.mm.yyyy') and slo.entrancetime>=cast('${timeSql}:00' as time)
	or slo.datestart = to_date('${dateNextEnd}','dd.mm.yyyy') and cast('${timeSql}' as time)>slo.entrancetime) 
	and 
	60<=(
	cast(to_char(slo.datestart,'yyyy') as int)-cast(to_char(pat.birthday,'yyyy') as int)
	+case when (cast(to_char(slo.datestart, 'mm') as int)-cast(to_char(pat.birthday, 'mm') as int)) <0 then -1 when (cast(to_char(slo.datestart,'dd') as int) - cast(to_char(pat.birthday,'dd') as int)<0) and ((cast(to_char(slo.datestart, 'mm') as int)-cast(to_char(pat.birthday, 'mm') as int)-1)<0)  then -1 else 0 end
	)
	then slo.id else null end)
	as cnt10EntranceFrom60
	,count(distinct case when slo.prevmedcase_id is not null and (slo.datestart between to_date('${dateNextBegin}','dd.mm.yyyy') and to_date('${dateEnd}','dd.mm.yyyy')
	 or slo.datestart = to_date('${dateBegin}','dd.mm.yyyy') and slo.entrancetime>=cast('${timeSql}:00' as time)
	or slo.datestart = to_date('${dateNextEnd}','dd.mm.yyyy') and cast('${timeSql}' as time)>slo.entrancetime) then slo.id else null end)
	as cnt11TransferOutOtherDepartment
	,count(distinct case when  (slo.transferdate between to_date('${dateNextBegin}','dd.mm.yyyy') and to_date('${dateEnd}','dd.mm.yyyy')
	 or slo.transferdate = to_date('${dateBegin}','dd.mm.yyyy') and slo.transfertime>=cast('${timeSql}:00' as time)
	or slo.transferdate = to_date('${dateNextEnd}','dd.mm.yyyy') and cast('${timeSql}' as time)>slo.transfertime) then slo.id else null end) 
	as cnt12TransferInOtherDepartment
	,count(distinct case when vhr.code!='11' and (slo.dateFinish between to_date('${dateNextBegin}','dd.mm.yyyy') and to_date('${dateEnd}','dd.mm.yyyy')
	 or slo.dateFinish = to_date('${dateBegin}','dd.mm.yyyy') and slo.dischargetime>=cast('${timeSql}:00' as time)
	or slo.dateFinish = to_date('${dateNextEnd}','dd.mm.yyyy') and cast('${timeSql}' as time)>slo.dischargetime) then slo.id else null end) 
	as cnt13Finished
	,count(distinct case when vho.code='4' and vhr.code!='11' and (slo.dateFinish between to_date('${dateNextBegin}','dd.mm.yyyy') and to_date('${dateEnd}','dd.mm.yyyy')
	 or slo.dateFinish = to_date('${dateBegin}','dd.mm.yyyy') and slo.dischargetime>=cast('${timeSql}:00' as time)
	or slo.dateFinish = to_date('${dateNextEnd}','dd.mm.yyyy') and cast('${timeSql}' as time)>slo.dischargetime) then slo.id else null end) 
	as cnt14FinishedOtherHospital
	,count(distinct case when vho.code='3' and vhr.code!='11' and (slo.dateFinish between to_date('${dateNextBegin}','dd.mm.yyyy') and to_date('${dateEnd}','dd.mm.yyyy')
	 or slo.dateFinish = to_date('${dateBegin}','dd.mm.yyyy') and slo.dischargetime>=cast('${timeSql}:00' as time)
	or slo.dateFinish = to_date('${dateNextEnd}','dd.mm.yyyy') and cast('${timeSql}' as time)>slo.dischargetime) then slo.id else null end) 
	as cnt15FinishedHourHospital
	,count(distinct case when vho.code='2' and vhr.code!='11' and (slo.dateFinish between to_date('${dateNextBegin}','dd.mm.yyyy') and to_date('${dateEnd}','dd.mm.yyyy')
	 or slo.dateFinish = to_date('${dateBegin}','dd.mm.yyyy') and slo.dischargetime>=cast('${timeSql}:00' as time)
	or slo.dateFinish = to_date('${dateNextEnd}','dd.mm.yyyy') and cast('${timeSql}' as time)>slo.dischargetime) then slo.id else null end) 
	as cnt16FinishedDayHospital
	,count(distinct case when vhr.code='11' and (slo.dateFinish between to_date('${dateNextBegin}','dd.mm.yyyy') and to_date('${dateEnd}','dd.mm.yyyy')
	 or slo.dateFinish = to_date('${dateBegin}','dd.mm.yyyy') and slo.dischargetime>=cast('${timeSql}:00' as time)
	or slo.dateFinish = to_date('${dateNextEnd}','dd.mm.yyyy') and cast('${timeSql}' as time)>slo.dischargetime) then slo.id else null end) 
	as cnt17Death
	,

	count(distinct case when 
			(
				slo.transferdate is null
				or slo.transferdate > to_date('${dateNextEnd}','dd.mm.yyyy')
				or slo.transferdate = to_date('${dateNextEnd}','dd.mm.yyyy') and slo.transfertime>=cast('${timeSql}' as time)
			) and (
				slo.datefinish is null or
				slo.datefinish > to_date('${dateNextEnd}','dd.mm.yyyy')
				or slo.datefinish = to_date('${dateNextEnd}','dd.mm.yyyy') and slo.dischargetime>=cast('${timeSql}' as time)
			)
		 then slo.id else null end
	)
	 as cnt18CurrentTo
	 
	,count(distinct case when sls.hotelServices='1' and 
	 (
				slo.transferdate is null
				or slo.transferdate > to_date('${dateNextEnd}','dd.mm.yyyy')
				or slo.transferdate = to_date('${dateNextEnd}','dd.mm.yyyy') and slo.transfertime>=cast('${timeSql}' as time)
			) and (
				slo.datefinish is null or
				slo.datefinish > to_date('${dateNextEnd}','dd.mm.yyyy')
				or slo.datefinish = to_date('${dateNextEnd}','dd.mm.yyyy') and slo.dischargetime>=cast('${timeSql}' as time)
			) then slo.id else null end)
	 as cnt19CurrentMother
	 
	 	,sum(	 	
	 	
	case when 
		slo.dateFinish between to_date('${dateBegin}','dd.mm.yyyy')+2 and to_date('${dateEnd}','dd.mm.yyyy')
			or slo.dateFinish = to_date('${dateNextBegin}','dd.mm.yyyy') and slo.dischargetime>=cast('${timeSql}:00' as time)
			or slo.dateFinish = to_date('${dateNextEnd}','dd.mm.yyyy') and cast('${timeSql}' as time)>slo.dischargetime		
		then slo.dateFinish
			-case
				when slo.dateFinish=to_date('${dateEnd}','dd.mm.yyyy') and slo.dischargetime>=cast('${timeSql}:00' as time) then 1
				when slo.dateFinish = to_date('${dateNextEnd}','dd.mm.yyyy') and cast('${timeSql}' as time)>slo.dischargetime then 2
				when cast('${timeSql}' as time)>slo.dischargetime then 2 
				else 1 end
		when slo.dateFinish=to_date('${dateBegin}','dd.mm.yyyy') then slo.dateFinish-1
		when slo.dateFinish=to_date('${dateNextBegin}','dd.mm.yyyy') and cast('${timeSql}:00' as time)>slo.dischargetime then slo.dateFinish-2
	     when 
		slo.transferDate between to_date('${dateBegin}','dd.mm.yyyy')+2 and to_date('${dateEnd}','dd.mm.yyyy')
			or slo.transferDate = to_date('${dateNextBegin}','dd.mm.yyyy') and slo.transfertime>=cast('${timeSql}:00' as time)
			or slo.transferDate = to_date('${dateNextEnd}','dd.mm.yyyy') and cast('${timeSql}' as time)>slo.transfertime		
		then slo.transferDate
			-case 
			when slo.transferDate = to_date('${dateEnd}','dd.mm.yyyy') and slo.transfertime>=cast('${timeSql}:00' as time) then 1
			when slo.transferdate = to_date('${dateNextEnd}','dd.mm.yyyy') and cast('${timeSql}' as time)>slo.transfertime then 2
			when cast('${timeSql}' as time)>slo.transfertime then 2 else 1 end
		when slo.transferDate=to_date('${dateBegin}','dd.mm.yyyy') then slo.transferDate-1
		when slo.transferDate=to_date('${dateNextBegin}','dd.mm.yyyy') and cast('${timeSql}:00' as time)>slo.transfertime then slo.transferDate-2
		else to_date('${dateEnd}','dd.mm.yyyy')
		
		end
	-
	case when
		(slo.datestart = to_date('${dateBegin}','dd.mm.yyyy') and cast('${timeSql}:00' as time)>slo.entrancetime
		or to_date('${dateBegin}','dd.mm.yyyy')>slo.datestart)
		and (slo.datefinish is null 
		or slo.datefinish > to_date('${dateBegin}','dd.mm.yyyy') 
		or slo.datefinish = to_date('${dateBegin}','dd.mm.yyyy') and slo.dischargetime>=cast('${timeSql}' as time))
		and (slo.transferdate is null 
		or slo.transferdate > to_date('${dateBegin}','dd.mm.yyyy') 
		or
		slo.transferdate = to_date('${dateBegin}','dd.mm.yyyy') and slo.transfertime>=cast('${timeSql}' as time))
	then to_date('${datePrevBegin}','dd.mm.yyyy')
	else slo.dateStart - case 
		when slo.datestart = to_date('${dateBegin}','dd.mm.yyyy') and slo.entrancetime>=cast('${timeSql}' as time) then 1
		when slo.datestart = to_date('${dateNextBegin}','dd.mm.yyyy') and cast('${timeSql}' as time)>slo.entrancetime then 2
		when slo.datestart = to_date('${dateEnd}','dd.mm.yyyy') and slo.entrancetime>=cast('${timeSql}' as time) then 1
		when slo.datestart = to_date('${dateNextEnd}','dd.mm.yyyy') and cast('${timeSql}' as time)>slo.entrancetime then 2
		when cast('${timeSql}' as time)>slo.entrancetime then 2 else 1 end
	end	
	) as cnt20Days
	 
 ,cast((select sum(bf1.amount) from BedFund bf1 where ${queryBedFundSql} and bf1.dateStart<=to_date('${dateEnd}','dd.mm.yyyy')
	and (bf1.dateFinish is null or bf1.dateFinish>=to_date('${dateEnd}','dd.mm.yyyy')) ) as int) as cnt21BF

,count(distinct case when slo.prevmedcase_id is null and (slo.datestart between to_date('${dateNextBegin}','dd.mm.yyyy') and to_date('${dateEnd}','dd.mm.yyyy')
	 or slo.datestart = to_date('${dateBegin}','dd.mm.yyyy') and slo.entrancetime>=cast('${timeSql}:00' as time)
	or slo.datestart = to_date('${dateNextEnd}','dd.mm.yyyy') and cast('${timeSql}' as time)>slo.entrancetime) 
	and 
	case when vs.omccode='1' then 60 else 55 end
	 <=(cast(to_char(slo.datestart,'yyyy') as int)-cast(to_char(pat.birthday,'yyyy') as int)
	+case when (cast(to_char(slo.datestart, 'mm') as int)-cast(to_char(pat.birthday, 'mm') as int)) <0 then -1 when (cast(to_char(slo.datestart,'dd') as int) - cast(to_char(pat.birthday,'dd') as int)<0) and ((cast(to_char(slo.datestart, 'mm') as int)-cast(to_char(pat.birthday, 'mm') as int)-1)<0)  then -1 else 0 end
	)
	then slo.id else null end)
	as cnt22EntranceMoreTRUDOSPOSOB

,count(distinct case when vhr.code!='11' and (slo.dateFinish between to_date('${dateNextBegin}','dd.mm.yyyy') and to_date('${dateEnd}','dd.mm.yyyy')
	 or slo.dateFinish = to_date('${dateBegin}','dd.mm.yyyy') and slo.dischargetime>=cast('${timeSql}:00' as time)
	or slo.dateFinish = to_date('${dateNextEnd}','dd.mm.yyyy') and cast('${timeSql}' as time)>slo.dischargetime) and 
	case when vs.omccode='1' then 60 else 55 end
	 <=(
	cast(to_char(slo.datestart,'yyyy') as int)-cast(to_char(pat.birthday,'yyyy') as int)
	+case when (cast(to_char(slo.datestart, 'mm') as int)-cast(to_char(pat.birthday, 'mm') as int)) <0 then -1 when (cast(to_char(slo.datestart,'dd') as int) - cast(to_char(pat.birthday,'dd') as int)<0) and ((cast(to_char(slo.datestart, 'mm') as int)-cast(to_char(pat.birthday, 'mm') as int)-1)<0)  then -1 else 0 end
	) then slo.id else null end) 
	as cnt23FinishedTrudosposob	

,count(distinct case when vhr.code='11' and (slo.dateFinish between to_date('${dateNextBegin}','dd.mm.yyyy') and to_date('${dateEnd}','dd.mm.yyyy')
	 or slo.dateFinish = to_date('${dateBegin}','dd.mm.yyyy') and slo.dischargetime>=cast('${timeSql}:00' as time)
	or slo.dateFinish = to_date('${dateNextEnd}','dd.mm.yyyy') and cast('${timeSql}' as time)>slo.dischargetime) and 
	case when vs.omccode='1' then 60 else 55 end
	 <=(
	cast(to_char(slo.datestart,'yyyy') as int)-cast(to_char(pat.birthday,'yyyy') as int)
	+case when (cast(to_char(slo.datestart, 'mm') as int)-cast(to_char(pat.birthday, 'mm') as int)) <0 then -1 when (cast(to_char(slo.datestart,'dd') as int) - cast(to_char(pat.birthday,'dd') as int)<0) and ((cast(to_char(slo.datestart, 'mm') as int)-cast(to_char(pat.birthday, 'mm') as int)-1)<0)  then -1 else 0 end
	) then slo.id else null end) 
	as cnt24DeathTrudosposob

,sum(	 case when 	
	 case when vs.omccode='1' then 60 else 55 end
	 <=(
	cast(to_char(slo.datestart,'yyyy') as int)-cast(to_char(pat.birthday,'yyyy') as int)
	+case when (cast(to_char(slo.datestart, 'mm') as int)-cast(to_char(pat.birthday, 'mm') as int)) <0 then -1 when (cast(to_char(slo.datestart,'dd') as int) - cast(to_char(pat.birthday,'dd') as int)<0) and ((cast(to_char(slo.datestart, 'mm') as int)-cast(to_char(pat.birthday, 'mm') as int)-1)<0)  then -1 else 0 end
	)	then 
	case when 
		slo.dateFinish between to_date('${dateBegin}','dd.mm.yyyy')+2 and to_date('${dateEnd}','dd.mm.yyyy')
			or slo.dateFinish = to_date('${dateNextBegin}','dd.mm.yyyy') and slo.dischargetime>=cast('${timeSql}:00' as time)
			or slo.dateFinish = to_date('${dateNextEnd}','dd.mm.yyyy') and cast('${timeSql}' as time)>slo.dischargetime		
		then slo.dateFinish
			-case
				when slo.dateFinish=to_date('${dateEnd}','dd.mm.yyyy') and slo.dischargetime>=cast('${timeSql}:00' as time) then 1
				when slo.dateFinish = to_date('${dateNextEnd}','dd.mm.yyyy') and cast('${timeSql}' as time)>slo.dischargetime then 2
				when cast('${timeSql}' as time)>slo.dischargetime then 2 
				else 1 end
		when slo.dateFinish=to_date('${dateBegin}','dd.mm.yyyy') then slo.dateFinish-1
		when slo.dateFinish=to_date('${dateNextBegin}','dd.mm.yyyy') and cast('${timeSql}:00' as time)>slo.dischargetime then slo.dateFinish-2
	     when 
		slo.transferDate between to_date('${dateBegin}','dd.mm.yyyy')+2 and to_date('${dateEnd}','dd.mm.yyyy')
			or slo.transferDate = to_date('${dateNextBegin}','dd.mm.yyyy') and slo.transfertime>=cast('${timeSql}:00' as time)
			or slo.transferDate = to_date('${dateNextEnd}','dd.mm.yyyy') and cast('${timeSql}' as time)>slo.transfertime		
		then slo.transferDate
			-case 
			when slo.transferDate = to_date('${dateEnd}','dd.mm.yyyy') and slo.transfertime>=cast('${timeSql}:00' as time) then 1
			when slo.transferdate = to_date('${dateNextEnd}','dd.mm.yyyy') and cast('${timeSql}' as time)>slo.transfertime then 2
			when cast('${timeSql}' as time)>slo.transfertime then 2 else 1 end
		when slo.transferDate=to_date('${dateBegin}','dd.mm.yyyy') then slo.transferDate-1
		when slo.transferDate=to_date('${dateNextBegin}','dd.mm.yyyy') and cast('${timeSql}:00' as time)>slo.transfertime then slo.transferDate-2
		else to_date('${dateEnd}','dd.mm.yyyy')
		
		end
	-
	case when
		(slo.datestart = to_date('${dateBegin}','dd.mm.yyyy') and cast('${timeSql}:00' as time)>slo.entrancetime
		or to_date('${dateBegin}','dd.mm.yyyy')>slo.datestart)
		and (slo.datefinish is null 
		or slo.datefinish > to_date('${dateBegin}','dd.mm.yyyy') 
		or slo.datefinish = to_date('${dateBegin}','dd.mm.yyyy') and slo.dischargetime>=cast('${timeSql}' as time))
		and (slo.transferdate is null 
		or slo.transferdate > to_date('${dateBegin}','dd.mm.yyyy') 
		or
		slo.transferdate = to_date('${dateBegin}','dd.mm.yyyy') and slo.transfertime>=cast('${timeSql}' as time))
	then to_date('${datePrevBegin}','dd.mm.yyyy')
	else slo.dateStart - case 
		when slo.datestart = to_date('${dateBegin}','dd.mm.yyyy') and slo.entrancetime>=cast('${timeSql}' as time) then 1
		when slo.datestart = to_date('${dateNextBegin}','dd.mm.yyyy') and cast('${timeSql}' as time)>slo.entrancetime then 2
		when slo.datestart = to_date('${dateEnd}','dd.mm.yyyy') and slo.entrancetime>=cast('${timeSql}' as time) then 1
		when slo.datestart = to_date('${dateNextEnd}','dd.mm.yyyy') and cast('${timeSql}' as time)>slo.entrancetime then 2
		when cast('${timeSql}' as time)>slo.entrancetime then 2 else 1 end
	end 
	else null end 
	
	) as cnt25DaysTrudosposob
		
	 from medcase slo
	 left join patient pat on pat.id=slo.patient_id
	 left join vocsex vs on vs.id=pat.sex_id
	 left join address2 a on a.addressid=pat.address_addressid
	 left join mislpu lpu on lpu.id=slo.department_id
	 left join medcase sls on sls.id=slo.parent_id
	 left join vochospitalizationoutcome vho on vho.id=sls.outcome_id
	 left join vochospitalizationresult vhr on vhr.id=sls.result_id
	left join bedfund bf on bf.id=slo.bedfund_id
	left join vocbedtype vbt on vbt.id=bf.bedtype_id
	left join vocbedsubtype vbst on vbst.id=bf.bedsubtype_id
	left join vocservicestream vss on vss.id=bf.servicestream_id
	left join vochosptype vht on vht.id=sls.sourceHospType_id
	where 
	slo.dtype='DepartmentMedCase' 
	and (to_date('${dateNextEnd}','dd.mm.yyyy')>slo.datestart
	or to_date('${dateNextEnd}','dd.mm.yyyy')=slo.dateStart and cast('${timeSql}' as time)>slo.entrancetime
	)
	and (slo.datefinish is null 
	or
	slo.datefinish>to_date('${dateBegin}','dd.mm.yyyy')
	or to_date('${dateBegin}','dd.mm.yyyy')=slo.datefinish and slo.dischargetime>=cast('${timeSql}' as time)
	)
	and (slo.transferdate is null 
	or slo.transferdate > to_date('${dateBegin}','dd.mm.yyyy')
	or to_date('${dateBegin}','dd.mm.yyyy')=slo.transferdate and slo.transfertime>=cast('${timeSql}' as time)
	)

	${departmentSql} ${serviceStreamSql} ${bedTypeSql} ${bedSubTypeSql}
	group by ${queryGroup}
	order by ${queryOrder}
	      " />
	    
	    <form action="print-stac_report016_456.do" method="post" target="_blank">
	    016/у-02 форма. ${queryTitle}
	    <input type='hidden' name="sqlText" id="sqlText" value="${journal_priem_sql}"> 
	    <input type='hidden' name="sqlInfo" id="sqlInfo" value="16 форма ${param.dateBegin}-${param.dateEnd}.">
	    <input type='hidden' name="sqlColumn" id="sqlColumn" value="${queryName}">
	    <input type='hidden' name="s" id="s" value="PrintService"><input type='hidden' name="isReportBase" id="isReportBase" value="${isReportBase}">
	    <input type='hidden' name="m" id="m" value="printNativeQuery">
	    <input type="submit" value="Печать"> 
	    </form>     
	    </msh:sectionTitle>	    
	    <msh:sectionContent>
	    <msh:table printToExcelButton="Сохранить в excel" name="journal_priem"
	    viewUrl="stac_report_016.do?short=Short" 
	    action="stac_report_016.do" idField="1">
	      <msh:tableColumn columnName="#" property="sn" />
	      <msh:tableColumn columnName="${queryName}" property="2" />
	      <msh:tableColumn columnName="тип коек" property="3" />
	      <msh:tableColumn columnName="потоки обслуживания" property="4" />
	      <msh:tableColumn columnName="число коек" property="21" />
	      <msh:tableColumn isCalcAmount="true" columnName="Состоит на начало истекших суток" property="5" />
	      <msh:tableColumn isCalcAmount="true" columnName="поступило всего" property="6" />
	      <msh:tableColumn isCalcAmount="true" columnName="пост. из дн. стац." property="7" />
	      <msh:tableColumn isCalcAmount="true" columnName="пост. т.ч. с.ж." property="8" />
	      <msh:tableColumn isCalcAmount="true" columnName="0-17 лет" property="9" />
	      <msh:tableColumn isCalcAmount="true" columnName="60 лет и старше" property="10" />
	      <msh:tableColumn isCalcAmount="true" columnName="в т.ч. старше трудоспособного возраста" property="22" />
	      <msh:tableColumn isCalcAmount="true" columnName="переведено из других отд" property="11" />
	      <msh:tableColumn isCalcAmount="true" columnName="переведено в другие отд" property="12" />
	      <msh:tableColumn isCalcAmount="true" columnName="выписано" property="13" />
	      <msh:tableColumn isCalcAmount="true" columnName="в т.ч. старше трудоспособного возраста" property="23" />
	      <msh:tableColumn isCalcAmount="true" columnName="в другие стационары" property="14" />
	      <msh:tableColumn isCalcAmount="true" columnName="в кругл. стационар" property="15" />
	      <msh:tableColumn isCalcAmount="true" columnName="в дневной стационар" property="16" />
	      <msh:tableColumn isCalcAmount="true" columnName="умерло" property="17" />
	      <msh:tableColumn isCalcAmount="true" columnName="в т.ч. старше трудоспособного возраста" property="24" />
	      <msh:tableColumn isCalcAmount="true" columnName="состоит всего" property="18" />
	      <msh:tableColumn isCalcAmount="true" columnName="состоит матерей" property="19" />
	      <msh:tableColumn isCalcAmount="true" columnName="проведено койко дней" property="20" />
	      <msh:tableColumn isCalcAmount="true" columnName="в т.ч. старше трудоспособного возраста" property="25" />
	    </msh:table>
	    </msh:sectionContent>
	    </msh:section>
	    		
	    		
	    		
	    		<%
	    	}
	    	

	    	if (view.equals("7")) {
	    			request.setAttribute("queryField", "lpu.name") ;
	           		request.setAttribute("queryId", "department='||lpu.id") ;
	           		request.setAttribute("queryTitle", "Свод по отделениям с учётом с.ж.") ;
	           		request.setAttribute("queryName", "Отделение") ;
	           		request.setAttribute("queryGroup", "lpu.id,lpu.name") ;
	           		request.setAttribute("queryGroupNext", "5") ;
	           		request.setAttribute("queryOrder", "lpu.name") ;	    			
	    		%>
	    		    <msh:section>
	    <msh:sectionTitle>
	    <ecom:webQuery isReportBase="${isReportBase}" nameFldSql="journal_priem_sql" name="journal_priem" nativeSql="select '&${queryId}||${departmentSqlId} as f1Id,${queryField} as f2Name
	 ,cast((select sum(bf1.amount) from BedFund bf1 where bf1.lpu_id=lpu.id and bf1.dateStart<=to_date('${dateEnd}','dd.mm.yyyy')
	and (bf1.dateFinish is null or bf1.dateFinish>=to_date('${dateEnd}','dd.mm.yyyy')) ) as int) as cnt3BF
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
	as cnt4CurrentFrom
	,count(distinct case when slo.prevmedcase_id is null and (slo.datestart between to_date('${dateNextBegin}','dd.mm.yyyy') and to_date('${dateEnd}','dd.mm.yyyy')
	 or slo.datestart = to_date('${dateBegin}','dd.mm.yyyy') and slo.entrancetime>=cast('${timeSql}:00' as time)
	or slo.datestart = to_date('${dateNextEnd}','dd.mm.yyyy') and cast('${timeSql}' as time)>slo.entrancetime) then slo.id else null end)
	as cnt5Entr
	,count(distinct case when slo.prevmedcase_id is null and vht.code='DAYTIMEHOSP' and (slo.datestart between to_date('${dateNextBegin}','dd.mm.yyyy') and to_date('${dateEnd}','dd.mm.yyyy')
	 or slo.datestart = to_date('${dateBegin}','dd.mm.yyyy') and slo.entrancetime>=cast('${timeSql}:00' as time)
	or slo.datestart = to_date('${dateNextEnd}','dd.mm.yyyy') and cast('${timeSql}' as time)>slo.entrancetime) then slo.id else null end)
	as cnt6EntrDayHosp
	,count(distinct case when slo.prevmedcase_id is null and a.addressIsVillage='1' and (slo.datestart between to_date('${dateNextBegin}','dd.mm.yyyy') and to_date('${dateEnd}','dd.mm.yyyy')
	 or slo.datestart = to_date('${dateBegin}','dd.mm.yyyy') and slo.entrancetime>=cast('${timeSql}:00' as time)
	or slo.datestart = to_date('${dateNextEnd}','dd.mm.yyyy') and cast('${timeSql}' as time)>slo.entrancetime) then slo.id else null end)
	as cnt7EntrV
	,count(distinct case when slo.prevmedcase_id is null and (slo.datestart between to_date('${dateNextBegin}','dd.mm.yyyy') and to_date('${dateEnd}','dd.mm.yyyy')
	 or slo.datestart = to_date('${dateBegin}','dd.mm.yyyy') and slo.entrancetime>=cast('${timeSql}:00' as time)
	or slo.datestart = to_date('${dateNextEnd}','dd.mm.yyyy') and cast('${timeSql}' as time)>slo.entrancetime) 
	and (
	cast(to_char(slo.datestart,'yyyy') as int)-cast(to_char(pat.birthday,'yyyy') as int)
	+case when (cast(to_char(slo.datestart, 'mm') as int)-cast(to_char(pat.birthday, 'mm') as int)) <0 then -1 when (cast(to_char(slo.datestart,'dd') as int) - cast(to_char(pat.birthday,'dd') as int)<0) and ((cast(to_char(slo.datestart, 'mm') as int)-cast(to_char(pat.birthday, 'mm') as int)-1)<0)  then -1 else 0 end
	)<18
	then slo.id else null end)

	as cnt8EntrTo17
	,count(distinct case when slo.prevmedcase_id is null and (slo.datestart between to_date('${dateNextBegin}','dd.mm.yyyy') and to_date('${dateEnd}','dd.mm.yyyy')
	 or slo.datestart = to_date('${dateBegin}','dd.mm.yyyy') and slo.entrancetime>=cast('${timeSql}:00' as time)
	or slo.datestart = to_date('${dateNextEnd}','dd.mm.yyyy') and cast('${timeSql}' as time)>slo.entrancetime) 
	and (
	cast(to_char(slo.datestart,'yyyy') as int)-cast(to_char(pat.birthday,'yyyy') as int)
	+case when (cast(to_char(slo.datestart, 'mm') as int)-cast(to_char(pat.birthday, 'mm') as int)) <0 then -1 when (cast(to_char(slo.datestart,'dd') as int) - cast(to_char(pat.birthday,'dd') as int)<0) and ((cast(to_char(slo.datestart, 'mm') as int)-cast(to_char(pat.birthday, 'mm') as int)-1)<0)  then -1 else 0 end
	)<15
	then slo.id else null end)

	as cnt9EntrTo14
	,count(distinct case when slo.prevmedcase_id is null and (slo.datestart between to_date('${dateNextBegin}','dd.mm.yyyy') and to_date('${dateEnd}','dd.mm.yyyy')
	 or slo.datestart = to_date('${dateBegin}','dd.mm.yyyy') and slo.entrancetime>=cast('${timeSql}:00' as time)
	or slo.datestart = to_date('${dateNextEnd}','dd.mm.yyyy') and cast('${timeSql}' as time)>slo.entrancetime) 
	and (
	cast(to_char(slo.datestart,'yyyy') as int)-cast(to_char(pat.birthday,'yyyy') as int)
	+case when (cast(to_char(slo.datestart, 'mm') as int)-cast(to_char(pat.birthday, 'mm') as int)) <0 then -1 when (cast(to_char(slo.datestart,'dd') as int) - cast(to_char(pat.birthday,'dd') as int)<0) and ((cast(to_char(slo.datestart, 'mm') as int)-cast(to_char(pat.birthday, 'mm') as int)-1)<0)  then -1 else 0 end
	) between 15 and 17
	then slo.id else null end)

	as cnt10Entr15To17
	,count(distinct case when slo.prevmedcase_id is null and (slo.datestart between to_date('${dateNextBegin}','dd.mm.yyyy') and to_date('${dateEnd}','dd.mm.yyyy')
	 or slo.datestart = to_date('${dateBegin}','dd.mm.yyyy') and slo.entrancetime>=cast('${timeSql}:00' as time)
	or slo.datestart = to_date('${dateNextEnd}','dd.mm.yyyy') and cast('${timeSql}' as time)>slo.entrancetime) 
	and 
	60<=(
	cast(to_char(slo.datestart,'yyyy') as int)-cast(to_char(pat.birthday,'yyyy') as int)
	+case when (cast(to_char(slo.datestart, 'mm') as int)-cast(to_char(pat.birthday, 'mm') as int)) <0 then -1 when (cast(to_char(slo.datestart,'dd') as int) - cast(to_char(pat.birthday,'dd') as int)<0) and ((cast(to_char(slo.datestart, 'mm') as int)-cast(to_char(pat.birthday, 'mm') as int)-1)<0)  then -1 else 0 end
	)
	then slo.id else null end)
	as cnt11EntrFrom60
	,count(distinct case when slo.prevmedcase_id is not null and (slo.datestart between to_date('${dateNextBegin}','dd.mm.yyyy') and to_date('${dateEnd}','dd.mm.yyyy')
	 or slo.datestart = to_date('${dateBegin}','dd.mm.yyyy') and slo.entrancetime>=cast('${timeSql}:00' as time)
	or slo.datestart = to_date('${dateNextEnd}','dd.mm.yyyy') and cast('${timeSql}' as time)>slo.entrancetime) then slo.id else null end)
	as cnt12TranOutOtherDep
	,count(distinct case when  (slo.transferdate between to_date('${dateNextBegin}','dd.mm.yyyy') and to_date('${dateEnd}','dd.mm.yyyy')
	 or slo.transferdate = to_date('${dateBegin}','dd.mm.yyyy') and slo.transfertime>=cast('${timeSql}:00' as time)
	or slo.transferdate = to_date('${dateNextEnd}','dd.mm.yyyy') and cast('${timeSql}' as time)>slo.transfertime) then slo.id else null end) 
	as cnt13TranInOtherDep
	,count(distinct case when vhr.code!='11' and (slo.dateFinish between to_date('${dateNextBegin}','dd.mm.yyyy') and to_date('${dateEnd}','dd.mm.yyyy')
	 or slo.dateFinish = to_date('${dateBegin}','dd.mm.yyyy') and slo.dischargetime>=cast('${timeSql}:00' as time)
	or slo.dateFinish = to_date('${dateNextEnd}','dd.mm.yyyy') and cast('${timeSql}' as time)>slo.dischargetime) then slo.id else null end) 
	as cnt14Fin
	,count(distinct case when vho.code='4' and vhr.code!='11' and (slo.dateFinish between to_date('${dateNextBegin}','dd.mm.yyyy') and to_date('${dateEnd}','dd.mm.yyyy')
	 or slo.dateFinish = to_date('${dateBegin}','dd.mm.yyyy') and slo.dischargetime>=cast('${timeSql}:00' as time)
	or slo.dateFinish = to_date('${dateNextEnd}','dd.mm.yyyy') and cast('${timeSql}' as time)>slo.dischargetime) then slo.id else null end) 
	as cnt15FinOtherHosp
	,count(distinct case when vho.code='3' and vhr.code!='11' and (slo.dateFinish between to_date('${dateNextBegin}','dd.mm.yyyy') and to_date('${dateEnd}','dd.mm.yyyy')
	 or slo.dateFinish = to_date('${dateBegin}','dd.mm.yyyy') and slo.dischargetime>=cast('${timeSql}:00' as time)
	or slo.dateFinish = to_date('${dateNextEnd}','dd.mm.yyyy') and cast('${timeSql}' as time)>slo.dischargetime) then slo.id else null end) 
	as cnt16FinHourHosp
	,count(distinct case when vho.code='2' and vhr.code!='11' and (slo.dateFinish between to_date('${dateNextBegin}','dd.mm.yyyy') and to_date('${dateEnd}','dd.mm.yyyy')
	 or slo.dateFinish = to_date('${dateBegin}','dd.mm.yyyy') and slo.dischargetime>=cast('${timeSql}:00' as time)
	or slo.dateFinish = to_date('${dateNextEnd}','dd.mm.yyyy') and cast('${timeSql}' as time)>slo.dischargetime) then slo.id else null end) 
	as cnt17FinDayHosp
	,count(distinct case when vhr.code='11' and (slo.dateFinish between to_date('${dateNextBegin}','dd.mm.yyyy') and to_date('${dateEnd}','dd.mm.yyyy')
	 or slo.dateFinish = to_date('${dateBegin}','dd.mm.yyyy') and slo.dischargetime>=cast('${timeSql}:00' as time)
	or slo.dateFinish = to_date('${dateNextEnd}','dd.mm.yyyy') and cast('${timeSql}' as time)>slo.dischargetime) then slo.id else null end) 
	as cnt18Death
	, count(distinct case when 
			(
				slo.transferdate is null
				or slo.transferdate > to_date('${dateNextEnd}','dd.mm.yyyy')
				or slo.transferdate = to_date('${dateNextEnd}','dd.mm.yyyy') and slo.transfertime>=cast('${timeSql}' as time)
			) and (
				slo.datefinish is null or
				slo.datefinish > to_date('${dateNextEnd}','dd.mm.yyyy')
				or slo.datefinish = to_date('${dateNextEnd}','dd.mm.yyyy') and slo.dischargetime>=cast('${timeSql}' as time)
			)
		 then slo.id else null end
	)
	 as cnt19CurTo
	, count(distinct case when 
			(
				slo.transferdate is null
				or slo.transferdate > to_date('${dateNextEnd}','dd.mm.yyyy')
				or slo.transferdate = to_date('${dateNextEnd}','dd.mm.yyyy') and slo.transfertime>=cast('${timeSql}' as time)
			) and (
				slo.datefinish is null or
				slo.datefinish > to_date('${dateNextEnd}','dd.mm.yyyy')
				or slo.datefinish = to_date('${dateNextEnd}','dd.mm.yyyy') and slo.dischargetime>=cast('${timeSql}' as time)
			) and a.addressIsVillage='1'
		 then slo.id else null end
	)
	 as cnt20CurToV
	 
	 	,sum(
	 	
	case when 
		slo.dateFinish between to_date('${dateBegin}','dd.mm.yyyy')+2 and to_date('${dateEnd}','dd.mm.yyyy')
			or slo.dateFinish = to_date('${dateNextBegin}','dd.mm.yyyy') and slo.dischargetime>=cast('${timeSql}:00' as time)
			or slo.dateFinish = to_date('${dateNextEnd}','dd.mm.yyyy') and cast('${timeSql}' as time)>slo.dischargetime		
		then slo.dateFinish
			-case
				when slo.dateFinish=to_date('${dateEnd}','dd.mm.yyyy') and slo.dischargetime>=cast('${timeSql}:00' as time) then 1
				when slo.dateFinish = to_date('${dateNextEnd}','dd.mm.yyyy') and cast('${timeSql}' as time)>slo.dischargetime then 2
				when cast('${timeSql}' as time)>slo.dischargetime then 2 
				else 1 end
		when slo.dateFinish=to_date('${dateBegin}','dd.mm.yyyy') then slo.dateFinish-1
		when slo.dateFinish=to_date('${dateNextBegin}','dd.mm.yyyy') and cast('${timeSql}:00' as time)>slo.dischargetime then slo.dateFinish-2
	     when 
		slo.transferDate between to_date('${dateBegin}','dd.mm.yyyy')+2 and to_date('${dateEnd}','dd.mm.yyyy')
			or slo.transferDate = to_date('${dateNextBegin}','dd.mm.yyyy') and slo.transfertime>=cast('${timeSql}:00' as time)
			or slo.transferDate = to_date('${dateNextEnd}','dd.mm.yyyy') and cast('${timeSql}' as time)>slo.transfertime		
		then slo.transferDate
			-case 
			when slo.transferDate = to_date('${dateEnd}','dd.mm.yyyy') and slo.transfertime>=cast('${timeSql}:00' as time) then 1
			when slo.transferdate = to_date('${dateNextEnd}','dd.mm.yyyy') and cast('${timeSql}' as time)>slo.transfertime then 2
			when cast('${timeSql}' as time)>slo.transfertime then 2 else 1 end
		when slo.transferDate=to_date('${dateBegin}','dd.mm.yyyy') then slo.transferDate-1
		when slo.transferDate=to_date('${dateNextBegin}','dd.mm.yyyy') and cast('${timeSql}:00' as time)>slo.transfertime then slo.transferDate-2
		else to_date('${dateEnd}','dd.mm.yyyy')
		
		end
	-
	case when
		(slo.datestart = to_date('${dateBegin}','dd.mm.yyyy') and cast('${timeSql}:00' as time)>slo.entrancetime
		or to_date('${dateBegin}','dd.mm.yyyy')>slo.datestart)
		and (slo.datefinish is null 
		or slo.datefinish > to_date('${dateBegin}','dd.mm.yyyy') 
		or slo.datefinish = to_date('${dateBegin}','dd.mm.yyyy') and slo.dischargetime>=cast('${timeSql}' as time))
		and (slo.transferdate is null 
		or slo.transferdate > to_date('${dateBegin}','dd.mm.yyyy') 
		or
		slo.transferdate = to_date('${dateBegin}','dd.mm.yyyy') and slo.transfertime>=cast('${timeSql}' as time))
	then to_date('${datePrevBegin}','dd.mm.yyyy')
	else slo.dateStart - case 
		when slo.datestart = to_date('${dateBegin}','dd.mm.yyyy') and slo.entrancetime>=cast('${timeSql}' as time) then 1
		when slo.datestart = to_date('${dateNextBegin}','dd.mm.yyyy') and cast('${timeSql}' as time)>slo.entrancetime then 2
		when slo.datestart = to_date('${dateEnd}','dd.mm.yyyy') and slo.entrancetime>=cast('${timeSql}' as time) then 1
		when slo.datestart = to_date('${dateNextEnd}','dd.mm.yyyy') and cast('${timeSql}' as time)>slo.entrancetime then 2
		when cast('${timeSql}' as time)>slo.entrancetime then 2 else 1 end
	end	
	) as cnt21Days
	 	,sum(
	 	
	case when a.addressIsVillage='1' then
	
	
	case when 
		slo.dateFinish between to_date('${dateBegin}','dd.mm.yyyy')+2 and to_date('${dateEnd}','dd.mm.yyyy')
			or slo.dateFinish = to_date('${dateNextBegin}','dd.mm.yyyy') and slo.dischargetime>=cast('${timeSql}:00' as time)
			or slo.dateFinish = to_date('${dateNextEnd}','dd.mm.yyyy') and cast('${timeSql}' as time)>slo.dischargetime		
		then slo.dateFinish
			-case
				when slo.dateFinish=to_date('${dateEnd}','dd.mm.yyyy') and slo.dischargetime>=cast('${timeSql}:00' as time) then 1
				when slo.dateFinish = to_date('${dateNextEnd}','dd.mm.yyyy') and cast('${timeSql}' as time)>slo.dischargetime then 2
				when cast('${timeSql}' as time)>slo.dischargetime then 2 
				else 1 end
		when slo.dateFinish=to_date('${dateBegin}','dd.mm.yyyy') then slo.dateFinish-1
		when slo.dateFinish=to_date('${dateNextBegin}','dd.mm.yyyy') and cast('${timeSql}:00' as time)>slo.dischargetime then slo.dateFinish-2
	     when 
		slo.transferDate between to_date('${dateBegin}','dd.mm.yyyy')+2 and to_date('${dateEnd}','dd.mm.yyyy')
			or slo.transferDate = to_date('${dateNextBegin}','dd.mm.yyyy') and slo.transfertime>=cast('${timeSql}:00' as time)
			or slo.transferDate = to_date('${dateNextEnd}','dd.mm.yyyy') and cast('${timeSql}' as time)>slo.transfertime		
		then slo.transferDate
			-case 
			when slo.transferDate = to_date('${dateEnd}','dd.mm.yyyy') and slo.transfertime>=cast('${timeSql}:00' as time) then 1
			when slo.transferdate = to_date('${dateNextEnd}','dd.mm.yyyy') and cast('${timeSql}' as time)>slo.transfertime then 2
			when cast('${timeSql}' as time)>slo.transfertime then 2 else 1 end
		when slo.transferDate=to_date('${dateBegin}','dd.mm.yyyy') then slo.transferDate-1
		when slo.transferDate=to_date('${dateNextBegin}','dd.mm.yyyy') and cast('${timeSql}:00' as time)>slo.transfertime then slo.transferDate-2
		else to_date('${dateEnd}','dd.mm.yyyy')
		
		end
	-
	case when
		(slo.datestart = to_date('${dateBegin}','dd.mm.yyyy') and cast('${timeSql}:00' as time)>slo.entrancetime
		or to_date('${dateBegin}','dd.mm.yyyy')>slo.datestart)
		and (slo.datefinish is null 
		or slo.datefinish > to_date('${dateBegin}','dd.mm.yyyy') 
		or slo.datefinish = to_date('${dateBegin}','dd.mm.yyyy') and slo.dischargetime>=cast('${timeSql}' as time))
		and (slo.transferdate is null 
		or slo.transferdate > to_date('${dateBegin}','dd.mm.yyyy') 
		or
		slo.transferdate = to_date('${dateBegin}','dd.mm.yyyy') and slo.transfertime>=cast('${timeSql}' as time))
	then to_date('${datePrevBegin}','dd.mm.yyyy')
	else slo.dateStart - case 
		when slo.datestart = to_date('${dateBegin}','dd.mm.yyyy') and slo.entrancetime>=cast('${timeSql}' as time) then 1
		when slo.datestart = to_date('${dateNextBegin}','dd.mm.yyyy') and cast('${timeSql}' as time)>slo.entrancetime then 2
		when slo.datestart = to_date('${dateEnd}','dd.mm.yyyy') and slo.entrancetime>=cast('${timeSql}' as time) then 1
		when slo.datestart = to_date('${dateNextEnd}','dd.mm.yyyy') and cast('${timeSql}' as time)>slo.entrancetime then 2
		when cast('${timeSql}' as time)>slo.entrancetime then 2 else 1 end
	end
	
	
	
	
	else 0 end
	
	) as cnt22DaysV
	 
	 from medcase slo
	 left join patient pat on pat.id=slo.patient_id
	 left join address2 a on a.addressid=pat.address_addressid
	 left join mislpu lpu on lpu.id=slo.department_id
	 left join medcase sls on sls.id=slo.parent_id
	 left join vochospitalizationoutcome vho on vho.id=sls.outcome_id
	 left join vochospitalizationresult vhr on vhr.id=sls.result_id
	left join bedfund bf on bf.id=slo.bedfund_id
	left join vocbedtype vbt on vbt.id=bf.bedtype_id
	left join vocbedsubtype vbst on vbst.id=bf.bedsubtype_id
	left join vocservicestream vss on vss.id=bf.servicestream_id
	left join vochosptype vht on vht.id=sls.sourceHospType_id
	where 
	slo.dtype='DepartmentMedCase' 
	and (to_date('${dateNextEnd}','dd.mm.yyyy')>slo.datestart
	or to_date('${dateNextEnd}','dd.mm.yyyy')=slo.dateStart and cast('${timeSql}' as time)>slo.entrancetime
	)
	and (slo.datefinish is null 
	or
	slo.datefinish>to_date('${dateBegin}','dd.mm.yyyy')
	or to_date('${dateBegin}','dd.mm.yyyy')=slo.datefinish and slo.dischargetime>=cast('${timeSql}' as time)
	)
	and (slo.transferdate is null 
	or slo.transferdate > to_date('${dateBegin}','dd.mm.yyyy')
	or to_date('${dateBegin}','dd.mm.yyyy')=slo.transferdate and slo.transfertime>=cast('${timeSql}' as time)
	)

	${departmentSql} ${serviceStreamSql} ${bedTypeSql} ${bedSubTypeSql}
	group by ${queryGroup}
	order by ${queryOrder}
	      " />
	    	    <form action="print-stac_report016_7.do" method="post" target="_blank">
	    016/у-02 форма. ${queryTitle}
	    <input type='hidden' name="sqlText" id="sqlText" value="${journal_priem_sql}"> 
	    <input type='hidden' name="sqlInfo" id="sqlInfo" value="16 форма ${param.dateBegin}-${param.dateEnd}.">
	    <input type='hidden' name="sqlColumn" id="sqlColumn" value="${groupName}">
	    <input type='hidden' name="s" id="s" value="PrintService"><input type='hidden' name="isReportBase" id="isReportBase" value="${isReportBase}">
	    <input type='hidden' name="m" id="m" value="printNativeQuery">
	    <input type="submit" value="Печать"> 
	    </form>     
	    
	    16/у-02 форма. ${queryTitle}</msh:sectionTitle>
	    <msh:sectionContent>
	    <msh:table printToExcelButton="Сохранить в excel" name="journal_priem"
	    viewUrl="stac_report_016.do?short=Short&dateBegin=${dateBegin}&dateEnd=${dateEnd}&serviceStream=${serviceStream}&typeView=${queryGroupNext}" 
	    action="stac_report_016.do?&dateBegin=${dateBegin}&dateEnd=${dateEnd}&serviceStream=${serviceStream}&typeView=${queryGroupNext}" idField="1">
	      <msh:tableColumn columnName="#" property="sn" />
	      <msh:tableColumn columnName="${queryName}" property="2" />
<%--	      <msh:tableColumn columnName="тип коек" property="3" />
	      <msh:tableColumn columnName="потоки обслуживания" property="4" />
	       --%>
	      <msh:tableColumn isCalcAmount="true" columnName="Число коек" property="3" />
	      <msh:tableColumn isCalcAmount="true" columnName="Состоит на начало истекших суток" property="4" />
	      <msh:tableColumn isCalcAmount="true" columnName="поступило всего" property="5" />
	      <msh:tableColumn isCalcAmount="true" columnName="из дн. стац." property="6" />
	      <msh:tableColumn isCalcAmount="true" columnName="с.ж." property="7" />
	      <msh:tableColumn isCalcAmount="true" columnName="0-17 лет" property="8" />
	      <msh:tableColumn isCalcAmount="true" columnName="до 14 лет" property="9" />
	      <msh:tableColumn isCalcAmount="true" columnName="15-17" property="10" />
	      <msh:tableColumn isCalcAmount="true" columnName="60 и ст" property="11" />
	      <msh:tableColumn isCalcAmount="true" columnName="переведено из других отд" property="12" />
	      <msh:tableColumn isCalcAmount="true" columnName="переведено в другие отд" property="13" />
	      <msh:tableColumn isCalcAmount="true" columnName="выписано" property="14" />
	      <msh:tableColumn isCalcAmount="true" columnName="в др. стац." property="15" />
	      <msh:tableColumn isCalcAmount="true" columnName="в кр. стац." property="16" />
	      <msh:tableColumn isCalcAmount="true" columnName="в дн. стац." property="17" />
	      <msh:tableColumn isCalcAmount="true" columnName="умерло" property="18" />
	      <msh:tableColumn isCalcAmount="true" columnName="состоит всего" property="19" />
	      <msh:tableColumn isCalcAmount="true" columnName="состоит с.ж." property="20" />
	      <msh:tableColumn isCalcAmount="true" columnName="проведено к/дней" property="21" />
	      <msh:tableColumn isCalcAmount="true" columnName="проведено к/дней с.ж." property="22" />
	    </msh:table>
	    </msh:sectionContent>
	    </msh:section>
	    		
	    		<%
	    	}  if (view.equals("8")) {
	    		 String typeGroup = (String) request.getParameter("typeGroup");
	    		//String isReestr = (String) request.getAttribute("reestr");
	    		/* String groupBy = "" ;
	    		String orderBy = "" ;
	    		String fldId = "" ;
	    		String fldName = "" ; */
	    		if (typeGroup!=null&&typeGroup.equals("2")) {
	    			request.setAttribute("groupBy","vfs.id, vfs.name, vbst.name, vbt.name");
	    			request.setAttribute("orderBy","vfs.name");
	    			//request.setAttribute("fldId","cast ('&department="+dep+"&dateBegin="+date+"&dateEnd="+dateEnd+"&bedSubType='||vbst.id||'&bedType='||vbt.id as varchar)") ;
	    			request.setAttribute("fldId","cast ('' as varchar)");
	    			request.setAttribute("fldName","vbt.name||' '||vbst.name ||' '||list(ml.name)||' '||list(vss.name) ");
	    			request.setAttribute("typeGroupNext", "javascript:void(0)") ;
	    		} else {
	    			typeGroup = "1" ;
	    			request.setAttribute("typeGroupNext", "stac_report_016.do?typeGroup=2") ;
	    			request.setAttribute("groupBy","vbst.id, vbst.name,vbt.id, vbt.name ");
	    			request.setAttribute("orderBy","vbst.name,vbt.name");
	    			request.setAttribute("fldId","cast ('&department="+dep+"&dateBegin="+date+"&dateEnd="+dateEnd+"&bedSubType='||vbst.id||'&bedType='||vbt.id as varchar)") ;
	    			request.setAttribute("fldName","vbt.name||' '||vbst.name ||' '||list(ml.name)||' '||list(vss.name) ");
	    			
	    		} 
	    		
	    		String serviceStream = (String) request.getAttribute("serviceStream") ;
	    		String departmentBFC = "";
	    		 if (dep!=null&&!dep.equals("")) {
	    		//	departmentBFC+= " and bfc.financeSource=vfs.id";
	    		} else if (serviceStream!=null&&!serviceStream.equals("")&&!serviceStream.equals("0")) {
	    			departmentBFC+=" and bfc.financeSource=vfs.id";
	    		} 
	    		
	    		request.setAttribute("departmentBFC",departmentBFC);
	    		%>
	    <ecom:webQuery isReportBase="${isReportBase}"  name="journal_priem" nameFldSql="journal_priem_sql" nativeSql="select
	    ${fldId} as vbstid,
	    ${fldName} as vbstname,cast('' as varchar)
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
	or slo.dateStart between to_date('${dateNextBegin}','dd.mm.yyyy') and to_date('${dateEnd}','dd.mm.yyyy')
	or slo.datestart = to_date('${dateNextEnd}','dd.mm.yyyy') and cast('${timeSql}' as time)>slo.entrancetime) then slo.id else null end)
	as cnt6Entrance
	,count(distinct case when slo.prevmedcase_id is null and vht.code='DAYTIMEHOSP' and(slo.datestart = to_date('${dateBegin}','dd.mm.yyyy') and slo.entrancetime>=cast('${timeSql}:00' as time)
	or slo.dateStart between to_date('${dateNextBegin}','dd.mm.yyyy') and to_date('${dateEnd}','dd.mm.yyyy')
	or slo.datestart = to_date('${dateNextEnd}','dd.mm.yyyy') and cast('${timeSql}' as time)>slo.entrancetime) then slo.id else null end)
	as cnt7EntranceDayHosp
	,count(distinct case when slo.prevmedcase_id is null and (slo.datestart = to_date('${dateBegin}','dd.mm.yyyy') and slo.entrancetime>=cast('${timeSql}:00' as time)
	or slo.dateStart between to_date('${dateNextBegin}','dd.mm.yyyy') and to_date('${dateEnd}','dd.mm.yyyy')
	or slo.datestart = to_date('${dateNextEnd}','dd.mm.yyyy') and cast('${timeSql}' as time)>slo.entrancetime) 
	and (ad1.addressIsVillage='1')
	then slo.id else null end)
	as cnt8EntranceVillagers
	,count(distinct case when slo.prevmedcase_id is null and (slo.datestart = to_date('${dateBegin}','dd.mm.yyyy') and slo.entrancetime>=cast('${timeSql}:00' as time)
	or slo.dateStart between to_date('${dateNextBegin}','dd.mm.yyyy') and to_date('${dateEnd}','dd.mm.yyyy')
	or slo.datestart = to_date('${dateNextEnd}','dd.mm.yyyy') and cast('${timeSql}' as time)>slo.entrancetime)
	and (
	cast(to_char(slo.datestart,'yyyy') as int)-cast(to_char(pat.birthday,'yyyy') as int)
	+case when (cast(to_char(slo.datestart, 'mm') as int)-cast(to_char(pat.birthday, 'mm') as int)) <0 then -1 when (cast(to_char(slo.datestart,'dd') as int) - cast(to_char(pat.birthday,'dd') as int)<0) and ((cast(to_char(slo.datestart, 'mm') as int)-cast(to_char(pat.birthday, 'mm') as int)-1)<0)  then -1 else 0 end
	)<18
	then slo.id else null end)

	as cnt9EntranceTo17
	,count(distinct case when slo.prevmedcase_id is null and (slo.datestart = to_date('${dateBegin}','dd.mm.yyyy') and slo.entrancetime>=cast('${timeSql}:00' as time)
	or slo.dateStart between to_date('${dateNextBegin}','dd.mm.yyyy') and to_date('${dateEnd}','dd.mm.yyyy')
	or slo.datestart = to_date('${dateNextEnd}','dd.mm.yyyy') and cast('${timeSql}' as time)>slo.entrancetime) 
	and 
	60<=(
	cast(to_char(slo.datestart,'yyyy') as int)-cast(to_char(pat.birthday,'yyyy') as int)
	+case when (cast(to_char(slo.datestart, 'mm') as int)-cast(to_char(pat.birthday, 'mm') as int)) <0 then -1 when (cast(to_char(slo.datestart,'dd') as int) - cast(to_char(pat.birthday,'dd') as int)<0) and ((cast(to_char(slo.datestart, 'mm') as int)-cast(to_char(pat.birthday, 'mm') as int)-1)<0)  then -1 else 0 end
	)
	then slo.id else null end)
	as cnt10EntranceFrom60
	,count(distinct case when slo.prevmedcase_id is not null and (slo.datestart between to_date('${dateNextBegin}','dd.mm.yyyy') and to_date('${dateEnd}','dd.mm.yyyy')
	 or slo.datestart = to_date('${dateBegin}','dd.mm.yyyy') and slo.entrancetime>=cast('${timeSql}:00' as time)
	or slo.datestart = to_date('${dateNextEnd}','dd.mm.yyyy') and cast('${timeSql}' as time)>slo.entrancetime) then slo.id else null end)
	as cnt11TransferOutOtherDepartment
	,count(distinct case when  (slo.transferdate between to_date('${dateNextBegin}','dd.mm.yyyy') and to_date('${dateEnd}','dd.mm.yyyy')
	 or slo.transferdate = to_date('${dateBegin}','dd.mm.yyyy') and slo.transfertime>=cast('${timeSql}:00' as time)
	or slo.transferdate = to_date('${dateNextEnd}','dd.mm.yyyy') and cast('${timeSql}' as time)>slo.transfertime) then slo.id else null end) 
	as cnt12TransferInOtherDepartment
	,count(distinct case when vhr.code!='11' and (slo.dateFinish between to_date('${dateNextBegin}','dd.mm.yyyy') and to_date('${dateEnd}','dd.mm.yyyy')
	 or slo.dateFinish = to_date('${dateBegin}','dd.mm.yyyy') and slo.dischargetime>=cast('${timeSql}:00' as time)
	or slo.dateFinish = to_date('${dateNextEnd}','dd.mm.yyyy') and cast('${timeSql}' as time)>slo.dischargetime) then slo.id else null end) 
	as cnt13Finished
	,count(distinct case when vho.code='4' and vhr.code!='11' and (slo.dateFinish between to_date('${dateNextBegin}','dd.mm.yyyy') and to_date('${dateEnd}','dd.mm.yyyy')
	 or slo.dateFinish = to_date('${dateBegin}','dd.mm.yyyy') and slo.dischargetime>=cast('${timeSql}:00' as time)
	or slo.dateFinish = to_date('${dateNextEnd}','dd.mm.yyyy') and cast('${timeSql}' as time)>slo.dischargetime) then slo.id else null end) 
	as cnt14FinishedOtherHospital
	,count(distinct case when vho.code='3' and vhr.code!='11' and (slo.dateFinish between to_date('${dateNextBegin}','dd.mm.yyyy') and to_date('${dateEnd}','dd.mm.yyyy')
	 or slo.dateFinish = to_date('${dateBegin}','dd.mm.yyyy') and slo.dischargetime>=cast('${timeSql}:00' as time)
	or slo.dateFinish = to_date('${dateNextEnd}','dd.mm.yyyy') and cast('${timeSql}' as time)>slo.dischargetime) then slo.id else null end) 
	as cnt15FinishedHourHospital
	,count(distinct case when vho.code='2' and vhr.code!='11' and (slo.dateFinish between to_date('${dateNextBegin}','dd.mm.yyyy') and to_date('${dateEnd}','dd.mm.yyyy')
	 or slo.dateFinish = to_date('${dateBegin}','dd.mm.yyyy') and slo.dischargetime>=cast('${timeSql}:00' as time)
	or slo.dateFinish = to_date('${dateNextEnd}','dd.mm.yyyy') and cast('${timeSql}' as time)>slo.dischargetime) then slo.id else null end) 
	as cnt16FinishedDayHospital
	,count(distinct case when vhr.code='11' and (slo.dateFinish between to_date('${dateNextBegin}','dd.mm.yyyy') and to_date('${dateEnd}','dd.mm.yyyy')
	 or slo.dateFinish = to_date('${dateBegin}','dd.mm.yyyy') and slo.dischargetime>=cast('${timeSql}:00' as time)
	or slo.dateFinish = to_date('${dateNextEnd}','dd.mm.yyyy') and cast('${timeSql}' as time)>slo.dischargetime) then slo.id else null end) 
	as cnt17Death
	,

	count(distinct case when 
			(
				slo.transferdate is null
				or slo.transferdate > to_date('${dateNextEnd}','dd.mm.yyyy')
				or slo.transferdate = to_date('${dateNextEnd}','dd.mm.yyyy') and slo.transfertime>=cast('${timeSql}' as time)
			) and (
				slo.datefinish is null or
				slo.datefinish > to_date('${dateNextEnd}','dd.mm.yyyy')
				or slo.datefinish = to_date('${dateNextEnd}','dd.mm.yyyy') and slo.dischargetime>=cast('${timeSql}' as time)
			)
		 then slo.id else null end
	)
	 as cnt18CurrentTo
	 
	,count(distinct case when sls.hotelServices='1' and 
	 (
				slo.transferdate is null
				or slo.transferdate > to_date('${dateNextEnd}','dd.mm.yyyy')
				or slo.transferdate = to_date('${dateNextEnd}','dd.mm.yyyy') and slo.transfertime>=cast('${timeSql}' as time)
			) and (
				slo.datefinish is null or
				slo.datefinish > to_date('${dateNextEnd}','dd.mm.yyyy')
				or slo.datefinish = to_date('${dateNextEnd}','dd.mm.yyyy') and slo.dischargetime>=cast('${timeSql}' as time)
			) then slo.id else null end)
	 as cnt19CurrentMother
	 
	 	,sum(	 	
	 	
	case when 
		slo.dateFinish between to_date('${dateBegin}','dd.mm.yyyy')+2 and to_date('${dateEnd}','dd.mm.yyyy')
			or slo.dateFinish = to_date('${dateNextBegin}','dd.mm.yyyy') and slo.dischargetime>=cast('${timeSql}:00' as time)
			or slo.dateFinish = to_date('${dateNextEnd}','dd.mm.yyyy') and cast('${timeSql}' as time)>slo.dischargetime		
		then slo.dateFinish
			-case
				when slo.dateFinish=to_date('${dateEnd}','dd.mm.yyyy') and slo.dischargetime>=cast('${timeSql}:00' as time) then 1
				when slo.dateFinish = to_date('${dateNextEnd}','dd.mm.yyyy') and cast('${timeSql}' as time)>slo.dischargetime then 2
				when cast('${timeSql}' as time)>slo.dischargetime then 2 
				else 1 end
		when slo.dateFinish=to_date('${dateBegin}','dd.mm.yyyy') then slo.dateFinish-1
		when slo.dateFinish=to_date('${dateNextBegin}','dd.mm.yyyy') and cast('${timeSql}:00' as time)>slo.dischargetime then slo.dateFinish-2
	     when 
		slo.transferDate between to_date('${dateBegin}','dd.mm.yyyy')+2 and to_date('${dateEnd}','dd.mm.yyyy')
			or slo.transferDate = to_date('${dateNextBegin}','dd.mm.yyyy') and slo.transfertime>=cast('${timeSql}:00' as time)
			or slo.transferDate = to_date('${dateNextEnd}','dd.mm.yyyy') and cast('${timeSql}' as time)>slo.transfertime		
		then slo.transferDate
			-case 
			when slo.transferDate = to_date('${dateEnd}','dd.mm.yyyy') and slo.transfertime>=cast('${timeSql}:00' as time) then 1
			when slo.transferdate = to_date('${dateNextEnd}','dd.mm.yyyy') and cast('${timeSql}' as time)>slo.transfertime then 2
			when cast('${timeSql}' as time)>slo.transfertime then 2 else 1 end
		when slo.transferDate=to_date('${dateBegin}','dd.mm.yyyy') then slo.transferDate-1
		when slo.transferDate=to_date('${dateNextBegin}','dd.mm.yyyy') and cast('${timeSql}:00' as time)>slo.transfertime then slo.transferDate-2
		else to_date('${dateEnd}','dd.mm.yyyy')
		
		end
	-
	case when
		(slo.datestart = to_date('${dateBegin}','dd.mm.yyyy') and cast('${timeSql}:00' as time)>slo.entrancetime
		or to_date('${dateBegin}','dd.mm.yyyy')>slo.datestart)
		and (slo.datefinish is null 
		or slo.datefinish > to_date('${dateBegin}','dd.mm.yyyy') 
		or slo.datefinish = to_date('${dateBegin}','dd.mm.yyyy') and slo.dischargetime>=cast('${timeSql}' as time))
		and (slo.transferdate is null 
		or slo.transferdate > to_date('${dateBegin}','dd.mm.yyyy') 
		or
		slo.transferdate = to_date('${dateBegin}','dd.mm.yyyy') and slo.transfertime>=cast('${timeSql}' as time))
	then to_date('${datePrevBegin}','dd.mm.yyyy')
	else slo.dateStart - case 
		when slo.datestart = to_date('${dateBegin}','dd.mm.yyyy') and slo.entrancetime>=cast('${timeSql}' as time) then 1
		when slo.datestart = to_date('${dateNextBegin}','dd.mm.yyyy') and cast('${timeSql}' as time)>slo.entrancetime then 2
		when slo.datestart = to_date('${dateEnd}','dd.mm.yyyy') and slo.entrancetime>=cast('${timeSql}' as time) then 1
		when slo.datestart = to_date('${dateNextEnd}','dd.mm.yyyy') and cast('${timeSql}' as time)>slo.entrancetime then 2
		when cast('${timeSql}' as time)>slo.entrancetime then 2 else 1 end
	end
	
	)/count(distinct bfc.id) as cntDays
,sum(distinct cast(bfc.actualbedcount||'.'||bfc.id as numeric))-sum(distinct cast('0.'||bfc.id as numeric)) as f21
,sum(distinct cast(bfc.countdays||'.'||bfc.id as numeric))-sum(distinct cast('0.'||bfc.id as numeric)) as f22
,sum(distinct cast(bfc.counthospitals||'.'||bfc.id as numeric))-sum(distinct cast('0.'||bfc.id as numeric)) as f23 
,cast(avg(bfc.plantreatmentduration) as numeric(9,2))	 as f24
,cast ('' as varchar) as f25
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
	left join bedfundcapacity bfc on bfc.bedtype=vbt.id
	left join vocfinancesource vfs on vfs.code=vss.financesource
	left join mislpu ml on ml.id=bfc.department
	where 
	slo.dtype='DepartmentMedCase' 
	and (to_date('${dateNextEnd}','dd.mm.yyyy')>slo.datestart
	or to_date('${dateNextEnd}','dd.mm.yyyy')=slo.dateStart and cast('${timeSql}' as time)>slo.entrancetime
	)
	and (slo.datefinish is null 
	or
	slo.datefinish>to_date('${dateBegin}','dd.mm.yyyy')
	or to_date('${dateBegin}','dd.mm.yyyy')=slo.datefinish and slo.dischargetime>=cast('${timeSql}' as time)
	)
	and (slo.transferdate is null 
	or slo.transferdate > to_date('${dateBegin}','dd.mm.yyyy')
	or to_date('${dateBegin}','dd.mm.yyyy')=slo.transferdate and slo.transfertime>=cast('${timeSql}' as time)
	)
	${departmentSql} ${serviceStreamSql} ${bedTypeSql} ${bedSubTypeSql}
	and bfc.financesource=vfs.id ${departmentBFC} 
	and bfc.department=slo.department_id and bfc.bedsubtype=vbst.id
	and (coalesce(slo.datefinish,current_date) between bfc.startDate and coalesce(bfc.finishDate, current_date))
	group by ${groupBy} 
	order by ${orderBy} 
	
	      " />
	    <%-- <form action="print-stac_report016_1.do" method="post" target="_blank">
	    Анализ работы отделений и учреждения в целом
	    <input type='hidden' name="sqlText" id="sqlText" value="${journal_priem_sql}"> 
	    <input type='hidden' name="sqlInfo" id="sqlInfo" value="16 форма ${param.dateBegin}-${param.dateEnd}.">
	    <input type='hidden' name="sqlColumn" id="sqlColumn" value="${groupName}">
	    <input type='hidden' name="s" id="s" value="PrintService"><input type='hidden' name="isReportBase" id="isReportBase" value="${isReportBase}">
	    <input type='hidden' name="m" id="m" value="printNativeQuery">
	    <input type="submit" value="Печать"> 
	    </form>     --%> 
	   
	    		<%
    List f16 = (List) request.getAttribute("journal_priem");
    		List newList = new ArrayList();
    if (!f16.isEmpty()) {
    	Date dateStart = DateFormat.parseDate(date);
    	Date dateFinish = DateFormat.parseDate(dateEnd);
    	Calendar cale = new GregorianCalendar();
    	cale.setTime(dateStart);
    	int m=cale.get(Calendar.MONTH);
    	cale.setTime(dateFinish);
    	m=cale.get(Calendar.MONTH) -m +1;
    	
    	int s = f16.size();
    	 for (int i=0;i<s;i++) {
    		 //try{
    		WebQueryResult newWQR = new WebQueryResult();
    		WebQueryResult wqr = (WebQueryResult)f16.get(i);
    		double cnt20Days = Double.valueOf((wqr.get19().toString())).doubleValue();	newWQR.set20(new BigDecimal(cnt20Days).setScale(1,0));
    		double cnt17Dead = Double.valueOf(wqr.get16().toString()).doubleValue();
    		double cnt13Finished = Double.valueOf(wqr.get12().toString()).doubleValue();
    		double cnt6Entrance = Double.valueOf(wqr.get5().toString()).doubleValue();
    		double usePatients = (cnt17Dead+cnt13Finished+cnt6Entrance)/2; //Пользованные пациенты
    	//	System.out.println("usePatient="+usePatients);
    		double f1_actualBedCount = Double.valueOf(wqr.get20().toString()).doubleValue();	newWQR.set1(new BigDecimal(f1_actualBedCount).setScale(1,BigDecimal.ROUND_HALF_UP));
    		double f2_countDays = Double.valueOf(wqr.get21().toString()).doubleValue()/12*m	;	newWQR.set2(new BigDecimal(f2_countDays).setScale(1,BigDecimal.ROUND_HALF_UP));
    		double f3_countHospitals = Double.valueOf(wqr.get22().toString()).doubleValue()/12*m;			newWQR.set3(new Long(Math.round(f3_countHospitals)));
    	//	System.out.println("countHospit = "+f3_countHospitals+" base = "+wqr.get22().toString());
    		double f4_obor_plan = (f3_countHospitals/f1_actualBedCount);				newWQR.set4(new BigDecimal(f4_obor_plan).setScale(1,BigDecimal.ROUND_HALF_UP));
    		double f5_obor_fact = (usePatients/f1_actualBedCount);						newWQR.set5(new BigDecimal(f5_obor_fact).setScale(1,BigDecimal.ROUND_HALF_UP));
    		double f6_obor_perc = (f5_obor_fact*100/f4_obor_plan)-100;					newWQR.set6(new BigDecimal(f6_obor_perc).setScale(1,BigDecimal.ROUND_HALF_UP));
    		double f7_zan_plan = f2_countDays/f1_actualBedCount;						newWQR.set7(new BigDecimal(f7_zan_plan).setScale(1,BigDecimal.ROUND_HALF_UP));
    		double f8_zan_fact = cnt20Days/f1_actualBedCount;							newWQR.set8(new BigDecimal(f8_zan_fact).setScale(1,BigDecimal.ROUND_HALF_UP));
    		double f9_zan_perc = (f8_zan_fact*100/f7_zan_plan)-100.0;						newWQR.set9(new BigDecimal(f9_zan_perc).setScale(1,BigDecimal.ROUND_HALF_UP));
    		double f10_dead = 0.00;
    		double f11_perc_kd = cnt20Days*100/f2_countDays;							newWQR.set11(new BigDecimal(f11_perc_kd).setScale(1,BigDecimal.ROUND_HALF_UP));
    		double f12_perc_hosp = 0.00;
    		double f13_aver_kd_plan = Double.valueOf(wqr.get23().toString()).doubleValue();			newWQR.set13(new BigDecimal(f13_aver_kd_plan).setScale(1,BigDecimal.ROUND_HALF_UP));
    		double f14_aver_kd_fact = 0.00;
    		if (!Double.isInfinite(usePatients)&&!Double.isNaN(usePatients)) {
	   			f10_dead = cnt17Dead*100/usePatients; 
	   			f12_perc_hosp = usePatients*100/f3_countHospitals;
	   			f14_aver_kd_fact = cnt20Days/usePatients;
	   		}	
    		double f15_otkl_kd = (f14_aver_kd_fact*100/f13_aver_kd_plan)-100;			newWQR.set15(new BigDecimal(f15_otkl_kd).setScale(1,BigDecimal.ROUND_HALF_UP));
    		double f16_fact_work_k = f1_actualBedCount*cnt20Days/f2_countDays;			newWQR.set16(new BigDecimal(f16_fact_work_k).setScale(1,BigDecimal.ROUND_HALF_UP));
    		double f17_nowork_k = f1_actualBedCount-f16_fact_work_k; 					newWQR.set17(f17_nowork_k>0.00?(new BigDecimal(f17_nowork_k).setScale(1,BigDecimal.ROUND_HALF_UP)):null);
    		 					newWQR.set18(wqr.get2());
    		//double f19_fact_kd = null;
    		double f20_fact_sluch = cnt13Finished+cnt17Dead;							newWQR.set21(Integer.valueOf(new Double(f20_fact_sluch).intValue()));
    		double f21_otkl_kd_absolute = f14_aver_kd_fact-f13_aver_kd_plan;			newWQR.set22(new BigDecimal(f21_otkl_kd_absolute).setScale(1,BigDecimal.ROUND_HALF_UP));
			double f23_ost_sdelat = f20_fact_sluch - f3_countHospitals; 				newWQR.set23(new Long(Math.round(f23_ost_sdelat)));
			newWQR.set10(new BigDecimal(f10_dead).setScale(1,BigDecimal.ROUND_HALF_UP));
			newWQR.set14(new BigDecimal(f14_aver_kd_fact).setScale(1,BigDecimal.ROUND_HALF_UP));
			newWQR.set12(new BigDecimal(f12_perc_hosp).setScale(1,BigDecimal.ROUND_HALF_UP));
			newWQR.set19(wqr.get1());
    		newList.add(i, newWQR);
    	// } catch (Exception e) {
    	//	 e.printStackTrace();
    	// }
    	} 
    }
    request.setAttribute("list_newform", newList);
	    %> 
	    <msh:section>
	    <msh:sectionTitle>Отчет о деятельности отделений стационара</msh:sectionTitle>
	    <msh:sectionContent>
	    <msh:table printToExcelButton="Сохранить в excel" name="list_newform" action="${typeGroupNext}" idField="19">
	      
	      <msh:tableColumn property="18" columnName="Профиль коек"/>
	      <msh:tableColumn property="1" columnName="Факт коек"/>
	      <msh:tableColumn property="2" columnName="План по к/д"/>
	      <msh:tableColumn property="20" columnName="Факт по к/д"/>
	      <msh:tableColumn property="11" columnName="% выполнения по к/д"/>
	      <msh:tableColumn property="3" columnName="План по случ"/>
	      <msh:tableColumn property="21" columnName="Факт по случ "/>
	      <msh:tableColumn property="23" columnName="Факт по случ"/>
	      <msh:tableColumn property="12" columnName="% выполнения по случ"/>
	      <msh:tableColumn property="4" columnName="Оборот план"/>
	      <msh:tableColumn property="5" columnName="Оборот факт"/>
	      <msh:tableColumn property="6" columnName="Отклонение, %"/>
	      <msh:tableColumn property="7" columnName="Занятость план"/>
	      <msh:tableColumn property="8" columnName="Занятость факт"/>
	      <msh:tableColumn property="9" columnName="Отклонение, %"/>
	      <msh:tableColumn property="10" columnName="Летальность, %"/>
	      <msh:tableColumn property="13" columnName="Средний к/д план"/>
	      <msh:tableColumn property="14" columnName="Средний к/д факт"/>
	      <msh:tableColumn property="22" columnName="Отклонение, к/д"/>
	      <msh:tableColumn property="15" columnName="Отклонение, %"/>
	      <msh:tableColumn property="16" columnName="Факт работало коек"/>
	      <msh:tableColumn property="17" columnName="Не работало коек"/>
	      </msh:table></msh:sectionContent></msh:section>
	    <%-- <msh:section>
	    <msh:sectionTitle>HELLLLO</msh:sectionTitle>
	    <msh:sectionContent>
	    <msh:table  name="list_newform" action="javascript:void()" idField="1">
	      <msh:tableColumn columnName="#" property="sn" />
	      <msh:tableColumn property="18" columnName="f0_Профиль коек"/>
	      <msh:tableColumn property="1" columnName="f1_Факт коек"/>
	      <msh:tableColumn property="2" columnName="f2_План по к/д"/>
	      <msh:tableColumn property="3" columnName="f3_План по случ"/>
	      <msh:tableColumn property="4" columnName="f4_Оборот план"/>
	      <msh:tableColumn property="5" columnName="f5_Оборот факт"/>
	      <msh:tableColumn property="6" columnName="f6_Отклонение"/>
	      <msh:tableColumn property="7" columnName="f7_Занятость план"/>
	      <msh:tableColumn property="8" columnName="f8_Занятость факт"/>
	      <msh:tableColumn property="9" columnName="f9_Отклонение"/>
	      <msh:tableColumn property="10" columnName="f10_Летальность"/>
	      <msh:tableColumn property="11" columnName="f11_% выполнения по к/д"/>
	      <msh:tableColumn property="12" columnName="f12_% выполнения по случ"/>
	      <msh:tableColumn property="13" columnName="f13_Средний к/д план"/>
	      <msh:tableColumn property="14" columnName="f14_Средний к/д факт"/>
	      <msh:tableColumn property="15" columnName="f15_Отклонение"/>
	      <msh:tableColumn property="16" columnName="f16_Факт работало коек"/>
	      <msh:tableColumn property="17" columnName="f17_Не работало коек"/>
	      </msh:table></msh:sectionContent></msh:section> --%>
	    		
	    		<%
	    	}
		
		}
	
} else {
	  out.println("<i>Выберите параметры и нажмите кнопку \"Найти\"</i>");
} %>
  
    

  </tiles:put>
</tiles:insert>