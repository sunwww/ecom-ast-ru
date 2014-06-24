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
    String typeReestr = request.getParameter("typeReestr") ;
    if (typeReestr==null) {
	  	String noViewForm = request.getParameter("noViewForm") ;
		String typeDate =ActionUtil.updateParameter("ReestrByHospitalMedCase","typeDate","1", request) ;
		String typeEmergency =ActionUtil.updateParameter("ReestrByHospitalMedCase","typeEmergency","3", request) ;
		String typeHour =ActionUtil.updateParameter("ReestrByHospitalMedCase","typeHour","4", request) ;
		String typeView =ActionUtil.updateParameter("ReestrByHospitalMedCase","typeView","1", request) ;
    }
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
	        <td class="label" title="Просмотр данных (typeView)" colspan="1"><label for="typeViewName" id="typeViewLabel">Отобразить информацию:</label></td>
	        <td onclick="this.childNodes[1].checked='checked';">
	        	<input type="radio" name="typeView" value="1">  приемному отделению
	        </td>
	        <td onclick="this.childNodes[1].checked='checked';">
	        	<input type="radio" name="typeView" value="2"  >  реанимациям
	        </td>
	        <td onclick="this.childNodes[1].checked='checked';">
	        	<input type="radio" name="typeView" value="3"  >  отделениям
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
        <msh:autoComplete property="department" fieldColSpan="4" horizontalFill="true" label="Отделение" vocName="vocLpuHospOtdAll"/>
      </msh:row>
      <msh:row>
           <td colspan="11">
            <input type="submit" onclick="find()" value="Найти" />

          </td>
      </msh:row>
    </msh:panel>
    </msh:form>
       <script type='text/javascript'>
    
    //checkFieldUpdate('typeDate','${typeDate}',1) ;
    //checkFieldUpdate('typePatient','${typePatient}',4) ;
    //checkFieldUpdate('typeEmergency','${typeEmergency}',3) ;
    checkFieldUpdate('typeView','${typeView}',1) ;
    //checkFieldUpdate('typeHour','${typeHour}',3) ;
    //checkFieldUpdate('typeDepartment','${typeDepartment}',1) ;
    
  
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
    	
    	String pigeonHole="" ;
    	String pigeonHole1="" ;
    	StringBuilder paramHref = new StringBuilder();
    	paramHref.append("typeReestr=1");
    	paramHref.append("&typeView=").append(view);
    	String pHole = request.getParameter("pigeonHole") ;
    	//paramHref.append("&pigeonHole=").append(pHole);
    	
    	if (pHole!=null && !pHole.equals("") && !pHole.equals("0")) {
    		pigeonHole1= " and (ml.pigeonHole_id='"+pHole+"' or ml1.pigeonHole_id='"+pHole+"')" ;
    		pigeonHole= " and ml.pigeonHole_id='"+pHole+"'" ;
    	}
    	request.setAttribute("pigeonHole", pigeonHole) ;
    	request.setAttribute("pigeonHole1", pigeonHole1) ;
    	ActionUtil.setParameterFilterSql("serviceStream", "m.serviceStream_id", request);
    	ActionUtil.setParameterFilterSql("department", "ml.id", request);
    	String serviceStream=request.getParameter("serviceStream");
    	paramHref.append("&serviceStream=").append(serviceStream!=null?serviceStream:"");
    	paramHref.append("&dateBegin=").append(date);
    	request.setAttribute("paramHref", paramHref.toString()) ;
    	/*
    	String dateI = null ;
			String timeI = null ;
			
			String period ="";
			
			//String date = form.getDateBegin() ;
			Date dat = DateFormat.parseDate(date) ;
		    Calendar cal = Calendar.getInstance() ;
		    cal.setTime(dat) ;
		    cal.add(Calendar.DAY_OF_MONTH, 1) ;
		    SimpleDateFormat format=new SimpleDateFormat("dd.MM.yyyy") ;
		    String date1=format.format(cal.getTime()) ;
		    String timeSql = null, timeInfo ="";
		    if (typeHour!=null && typeHour.equals("1")) {
				timeSql= "07:00" ;timeInfo="(7 часов)" ;
		    } else if (typeHour!=null && typeHour.equals("2")) {
		    	timeSql= "08:00" ;timeInfo="(8 часов)" ;
	    	} else if (typeHour!=null && typeHour.equals("2")) {
	    		timeSql= "09:00" ;timeInfo="(9 часов)" ;
	    	} 
		    if (typeDate!=null && typeDate.equals("1")) {
	    		//aRequest.setAttribute("dateIs"," and m.dateStart between to_date('"+form.getDateBegin()+"','dd.mm.yyyy') and to_date('"+form.getDateBegin()+"','dd.mm.yyyy') ") ;
				dateI = "dateStart" ; timeI = "entranceTime" ;
	    		request.setAttribute("dateInfo","поступившим") ;
	    	} else if (typeDate!=null && typeDate.equals("2")) {
	    		dateI = "dateFinish" ; timeI = "dischargeTime" ;
	    		request.setAttribute("dateInfo","выписанным") ;
	    	} else {
	    		request.setAttribute("dateI", "dateStart") ;
	    		StringBuilder periodF = new StringBuilder() ;
    			periodF.append(" and (m.dateFinish is null ");
	    		periodF.append(" or ") ;
	    		periodF.append(ReportParamUtil.getDateFrom(Boolean.TRUE, "m.dateFinish", "m.dischargeTime", timeSql!=null?date1:date, timeSql)) ;
	    		periodF.append(" and ") ;
	    		periodF.append(ReportParamUtil.getDateTo(Boolean.FALSE, "m.dateStart", "m.entranceTime", timeSql!=null?date:date1, timeSql)) ;
	    		periodF.append(") ") ;
	    	
	    		request.setAttribute("period",periodF.toString()) ;
	    		request.setAttribute("period1",null) ;
	    		request.setAttribute("dateInfo","состоящим") ;
	    	}
		    if (dateI!=null) {
		    	request.setAttribute("dateI", dateI) ;
		    	period = ReportParamUtil.getPeriodByDate(true,false, "m."+dateI, "m."+timeI, date, date1, timeSql) ;
		    	request.setAttribute("period",period) ;
		    	request.setAttribute("hourInfo",timeInfo) ;
				request.setAttribute("period1",period) ;
		    }*/
    	
		    if (typeReestr!=null && typeReestr.equals("1")) {
    			String patient=request.getParameter("patient") ; String dateinfo=request.getParameter("dateinfo") ;
    			String age=request.getParameter("age") ; String discharge=request.getParameter("discharge") ;
    			String denied=request.getParameter("denied") ; String emergency=request.getParameter("emergency") ;
    			String orderType=request.getParameter("orderType") ; String stream=request.getParameter("stream") ;
    			String operation=request.getParameter("operation") ; String dtype=request.getParameter("dtype" );
    			StringBuilder where = new StringBuilder() ;
    			
    			if (dateinfo==null) {
    				//No date dateinfo
    			} else {
    				String dtypeAs = "slo";String groupBy="" ;
    				if (dtype!=null&&dtype.equals("Hosp")) {
    					dtype="HospitalMedCase" ;dtypeAs="sls" ;
    					groupBy="sls.id,pat.id,pat.lastname,pat.firstname,pat.middlename" ;
    				} else {
    					dtype="DepartmentMedCase" ;
    				}
    				if (dateinfo.equals("dateCurrent")) {
    					if (dtype.equals("HospitalMedCase")) {
        					where.append(dtypeAs).append(".datestart <= to_date('").append(date).append("','dd.mm.yyyy') and (").append(dtypeAs).append(".datefinish is null or ").append(dtypeAs).append(".datefinish > to_date('").append(date).append("','dd.mm.yyyy'))") ;
    					} else {
        					where.append(dtypeAs).append(".datestart <= to_date('").append(date).append("','dd.mm.yyyy') and (coalesce(").append(dtypeAs).append(".datefinish,").append(dtypeAs).append(".transferdate) is null or coalesce(").append(dtypeAs).append(".datefinish,").append(dtypeAs).append(".transferdate) > to_date('").append(date).append("','dd.mm.yyyy'))") ;
    					}
    				} else if (dateinfo.equals("dateStart")) {
    					where.append(dtypeAs).append(".dateStart = to_date('").append(date).append("','dd.mm.yyyy')");
    				} else if (dateinfo.equals("dateFinish")) {
    					where.append(dtypeAs).append(".dateFinish = to_date('").append(date).append("','dd.mm.yyyy')");
    				} else if (dateinfo.equals("coalesce(transferDate,dateFinish)")) {
    					where.append("coalesce(").append(dtypeAs).append(".transferDate,").append(dtypeAs).append("dateFinish) = to_date('").append(date).append("','dd.mm.yyyy')");
    				} else if (dateinfo.equals("transferDate")) {
    					where.append(dtypeAs).append(".transferDate = to_date('").append(date).append("','dd.mm.yyyy')");
    				}
    				where.append(ActionUtil.setParameterFilterSql("pigeonHole", "ml.pigeonHole_id", request)) ;
    				where.append(ActionUtil.setParameterFilterSql("department", "ml.id", request)) ;
    				where.append(ActionUtil.setParameterFilterSql("serviceStream", "vss.id", request)) ;
    				if (denied!=null && denied.equals("all")) {
    					where.append(" and sls.deniedHospitalizating_id is not null") ;
    				} else if (denied!=null && denied.equals("0")) {
        				where.append(" and sls.deniedHospitalizating_id is null") ;
    				} else if (denied!=null) {
    					where.append(" and sls.deniedHospitalizating_id='").append(denied).append("'") ;
    				}
    				if (emergency!=null && emergency.equals("1")) {
    					where.append(" and ").append(dtypeAs).append(".emergency='1'") ;
    				} else if (emergency!=null && emergency.equals("0")) {
        					where.append(" and (").append(dtypeAs).append(".emergency='0' or ").append(dtypeAs).append(".emergency is null)") ;
    				}
    				if (orderType!=null) {
    					where.append(" and vof.voc_code='").append(orderType).append("'") ;
    				} 
    				if (discharge!=null) {
    					if (discharge.equals("otherLpu")) {
    						where.append(" and vho.code!='1'");
    					} else if (discharge.equals("death")) {
    						where.append(" and vhr.code='11'");
    					}
    				}
    				if (patient==null) {
    				} else if (patient.equals("village")) {
    					where.append(" and (ok.voc_code='643' or ok.id is null) and adr.kladr is not null and substring(adr.kladr,1,2)='30' and adr.addressisvillage='1'");
    				} else if (patient.equals("city")) {
    					where.append(" and (ok.voc_code='643' or ok.id is null) and adr.kladr is not null and substring(adr.kladr,1,2)='30' and adr.addressiscity='1'");
    				} else if (patient.equals("inostr")) {
    					where.append(" and ok.voc_code!='643' and ok.id is not null") ;
    				} else if (patient.equals("inog")) {
    					where.append(" and (ok.voc_code='643' or ok.id is null) and substring(adr.kladr,1,2)!='30'");
    				} else if (patient.equals("other")) {
    					where.append(" and (ok.voc_code='643' or ok.id is null) and (adr.addressid is null or adr.domen<3)") ;
    				}
    				if (stream!=null) {
    					String[] st=stream.split(",");
    					StringBuilder s = new StringBuilder() ;
    					for (int i=0;i<st.length;i++) {
    						if (i>0) s.append(",") ;
    						s.append("'").append(st[i]).append("'");
    					}
    					where.append(" and vss.code in (").append(s.toString()).append(")") ;
    				}
    				if (age!=null) {
    					if (age.equals("0")) {
    						where.append(" and pat.newborn_id is not null") ;
    					} else if (age.equals("0-14")){
    						where.append(" and (to_date('").append(date).append("','dd.mm.yyyy')-pat.birthday)<(17*355)") ;
    					}
    				}
    				request.setAttribute("whereSql", where.toString()) ;
    				request.setAttribute("groupBy", groupBy) ;
    				if (dtype.equals("DepartmentMedCase")) {
    			%>
    <ecom:webQuery name="reestr" nameFldSql="reestr_sql" nativeSql="
    select slo.id as sloid,to_char(slo.dateStart,'dd.mm.yyyy')||' '||cast(slo.Entrancetime as varchar(5)) as slsdatestart,ss.code as sscode
    ,pat.lastname||' '||pat.firstname||' '||pat.middlename as fio
    ,vss.name as vssname
     from MedCase slo
    left join MedCase sls on sls.id=slo.parent_id
    left join StatisticStub ss on ss.id=sls.statisticStub_id
    left join Patient pat on pat.id=slo.patient_id
    left join VocSocialStatus pvss on pvss.id=pat.socialStatus_id
	left join Address2 adr on adr.addressid=pat.address_addressid
	left join Mislpu ml on slo.department_id=ml.id
	left join VocPigeonHole vph on vph.id=ml.pigeonHole_id
	left join Omc_Oksm ok on pat.nationality_id=ok.id
	left join SurgicalOperation so on so.medcase_id=slo.id
	left join Omc_Frm vof on vof.id=slo.orderType_id
	left join Diary d on d.medcase_id=slo.id
	left join VocHospitalizationOutcome vho on vho.id=sls.outcome_id
	left join VocHospitalizationResult vhr on vhr.id=sls.result_id
	left join VocServiceStream vss on vss.id=slo.serviceStream_id
	where slo.dtype='DepartmentMedCase' and ${whereSql} 
	group by slo.id,pat.id,pat.lastname,pat.firstname,pat.middlename,ss.code,vss.name,slo.Entrancetime,slo.dateStart
	order by pat.lastname,pat.firstname,pat.middlename
    "/>
    ${reestr_sql}
    <msh:table action="entitySubclassView-mis_medCase.do" name="reestr" idField="1">
    	<msh:tableColumn property="sn" columnName="#"/>
    	<msh:tableColumn property="2" columnName="Дата и время пост."/>
    	<msh:tableColumn property="3" columnName="ИБ"/>
    	<msh:tableColumn property="4" columnName="ФИО"/>
    	<msh:tableColumn property="5" columnName="Поток обслуживания"/>
    </msh:table>
    			<%
    				} else if (dtype.equals("HospitalMedCase")) {
    					%>
    				    <ecom:webQuery name="reestr" nameFldSql="reestr_sql" nativeSql="
    				    select sls.id as slsid,to_char(slS.dateStart,'dd.mm.yyyy')||' '||cast(slS.Entrancetime as varchar(5)) as datestart,ss.code as sscode
    				    ,pat.lastname||' '||pat.firstname||' '||pat.middlename as fio,vss.name as vssname
    				    ,ml.name as mlname,to_char(sloLast.dateStart,'dd.mm.yyyy')||' '||cast(sloLast.Entrancetime as varchar(5)) as slodatestart,mlLast.name as mllastname
    				    from MedCase sls
    				    left join StatisticStub ss on ss.id=sls.statisticStub_id
    				    left join Patient pat on pat.id=sls.patient_id
    				    left join VocSocialStatus pvss on pvss.id=pat.socialStatus_id
    					left join Address2 adr on adr.addressid=pat.address_addressid
    					left join Mislpu ml on sls.department_id=ml.id
    					left join VocPigeonHole vph on vph.id=ml.pigeonHole_id
    					left join Omc_Oksm ok on pat.nationality_id=ok.id
    					left join Diary d on d.medcase_id=sls.id
    					left join Omc_Frm vof on vof.id=sls.orderType_id
						left join VocHospitalizationOutcome vho on vho.id=sls.outcome_id
						left join VocHospitalizationResult vhr on vhr.id=sls.result_id
						left join VocServiceStream vss on vss.id=sls.serviceStream_id
						left join MedCase sloLast on sloLast.parent_id=sls.id and sloLast.transferdate is null
						left join Mislpu mlLast on sloLast.department_id=mlLast.id
    					where sls.dtype='HospitalMedCase' and ${whereSql} 
    					group by sls.id,pat.id,pat.lastname,pat.firstname,pat.middlename,ss.code,vss.name,sls.Entrancetime,sls.dateStart
    					,ml.name,mlLast.name,sloLast.dateStart,sloLast.Entrancetime
    					order by mlLast.name,pat.lastname,pat.firstname,pat.middlename
    				    "/>
    				    ${reestr_sql}
    				    <msh:table action="entitySubclassView-mis_medCase.do" name="reestr" idField="1">
    				    	<msh:tableColumn property="sn" columnName="#"/>
    				    	<msh:tableColumn property="2" columnName="Дата и время пост. в стационар"/>
    				    	<msh:tableColumn property="3" columnName="ИБ"/>
    				    	<msh:tableColumn property="4" columnName="ФИО"/>
    				    	<msh:tableColumn property="5" columnName="Поток обслуживания"/>
    				    	<msh:tableColumn property="6" columnName="Отделение пост."/>
    				    	<msh:tableColumn property="7" columnName="Отделение сост."/>
    				    	<msh:tableColumn property="8" columnName="Дата пост. в отд сост."/>
    				    </msh:table>
    				    			<%    					
    				}
    			
    				
    				}} else if (view!=null && (view.equals("1"))) {
    		
    	%>
    
    <msh:section>
    <msh:sectionTitle>
    Список, состоящих в стационаре
    </msh:sectionTitle>
    <msh:sectionContent>
    <ecom:webQuery name="journal_priem" nameFldSql="journal_priem_sql" nativeSql=" 
   select '&pigeonHole='||vph.id,vph.name
,count(distinct  m.id ) as cntAll
,count(distinct case when pat.newborn_id is not null then m.id else null end) as cntNewBorn
,count(distinct case when (to_date('${param.dateBegin}','dd.mm.yyyy')-pat.birthday)<(17*355) then m.id else null end) as cntChild
,count(distinct case when (ok.voc_code is null or ok.voc_code='643') and substring(adr.kladr,1,2)='30' and adr.addressisvillage='1' then m.id else null end) as cntVill
,count(distinct case when (ok.voc_code is null or ok.voc_code='643') and substring(adr.kladr,1,2)='30' and adr.addressiscity='1' then m.id else null end) as cntCity
,count(distinct case when (ok.voc_code is null or ok.voc_code='643') and adr.kladr is not null and substring(adr.kladr,1,2)!='30' then m.id else null end) as cntInog
,count(distinct case when ok.voc_code is not null and ok.voc_code!='643' then m.id else null end) as cntInost
from medcase m
left join Patient pat on pat.id=m.patient_id
left join VocSocialStatus pvss on pvss.id=pat.socialStatus_id
left join Address2 adr on adr.addressid=pat.address_addressid
left join Mislpu ml on m.department_id=ml.id
left join VocPigeonHole vph on vph.id=ml.pigeonHole_id
left join Omc_Oksm ok on pat.nationality_id=ok.id
where  
 (m.datefinish is null or m.datefinish > to_date('${param.dateBegin}','dd.mm.yyyy'))
and m.dtype='HospitalMedCase'
and m.datestart <= to_date('${param.dateBegin}','dd.mm.yyyy')
${departmentSql}
and m.deniedHospitalizating_id is null
${emerIs} ${pigeonHole} 
 ${serviceStreamSql} ${addPat} 
group by vph.id,vph.name
order by vph.name
      " guid="4a720225-8d94-4b47-bef3-4dbbe79eec74" />
    <msh:table cellFunction="true" name="journal_priem" 
    action="stac_everyday_report.do?${paramHref}&dtype=Hosp&dateinfo=dateCurrent&denied=0" idField="1" guid="b621e361-1e0b-4ebd-9f58-b7d919b45bd6">
      <msh:tableColumn columnName="#" property="sn" guid="34a9f56ab-a3fa-5c1afdf6c41d" />
      <msh:tableColumn columnName="Приемник" property="2" guid="34a9f56a-2b47-4feb-a3fa-5c1afdf6c41d" />
      <msh:tableColumn isCalcAmount="true" columnName="Кол-во" property="3" guid="34a9f56a-2b47-4feb-a3fa-5c1afdf6c41d" />
      <msh:tableColumn isCalcAmount="true" columnName="Кол-во новорожд." addParam="&age=0" property="4" guid="3cf775aa-e94d-4393-a489-b83b2be02d60" />
      <msh:tableColumn isCalcAmount="true" columnName="Кол-во детей" addParam="&age=0-14" property="5" guid="e29229e1-d243-47d6-a5c7-997df74eaf73" />
      <msh:tableColumn isCalcAmount="true" columnName="Кол-во с.ж." addParam="&patient=village" property="6" guid="d9642df9-5653-4920-bb78-1622cbeefa34" />
      <msh:tableColumn isCalcAmount="true" columnName="Кол-во гор." addParam="&patient=city" property="7" guid="d9642df9-5653-4920-bb78-1622cbeefa34" />
      <msh:tableColumn isCalcAmount="true" columnName="Кол-во иног." addParam="&patient=inog" property="8" guid="d9642df9-5653-4920-bb78-1622cbeefa34" />
      <msh:tableColumn isCalcAmount="true" columnName="Кол-во иноcт." addParam="&patient=inostr" property="9" guid="d9642df9-5653-4920-bb78-1622cbeefa34" />
    </msh:table>
    </msh:sectionContent>
    </msh:section>
    
     <msh:section>
    	    <msh:sectionTitle>Список, поступивших пациентов</msh:sectionTitle>
    	    <msh:sectionContent>
    	    <ecom:webQuery name="journal_priem" nameFldSql="journal_priem_sql" nativeSql=" 
    	   select  
    '&pigeonHole='||vph.id as idpar,vph.name
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
, count(distinct case when m.deniedHospitalizating_id='4' then m.id else null end) as denied4 
, count(distinct case when m.deniedHospitalizating_id='8' then m.id else null end) as denied8
, count(distinct case when m.deniedHospitalizating_id='5' then m.id else null end) as denied5
, count(distinct case when m.deniedHospitalizating_id='2' then m.id else null end) as denied2
from medcase m 
left join MisLpu as ml on ml.id=m.department_id
left join VocPigeonHole vph on vph.id=ml.pigeonHole_id
left join Patient p on p.id=m.patient_id
left join Address2 a on p.address_addressid=a.addressid
left join Omc_Oksm oo on oo.id=p.nationality_id 
left join Omc_Frm vof on vof.id=m.orderType_id
where  m.dateStart 
=to_date('${param.dateBegin}','dd.mm.yyyy') 
${departmentSql} 
and m.dtype='HospitalMedCase' and ( m.noActuality is null or m.noActuality='0')

    	${period}
    	${emerIs} ${pigeonHole} 
    	 ${serviceStreamSql} ${addPat} 
    	group by vph.id,vph.name
    	order by vph.name
    	      " guid="4a720225-8d94-4b47-bef3-4dbbe79eec74" />
    	    <msh:table cellFunction="true" name="journal_priem" 
    	    action="stac_everyday_report.do?${paramHref}&dtype=Hosp&dateinfo=dateStart" idField="1">
    	      <msh:tableColumn columnName="#" property="sn" addParam="" guid="34a9f56ab-a3fa-5c1afdf6c41d" />
    	      <msh:tableColumn columnName="Приемник" addParam="" property="2"/>
    	      <msh:tableColumn isCalcAmount="true" columnName="Кол-во обрат." addParam="" property="3"/>
    	      <msh:tableColumn isCalcAmount="true" columnName="Кол-во госп" addParam="&denied=0" property="4"/>
    	      <msh:tableColumn isCalcAmount="true" columnName="с.ж." addParam="&denied=0&patient=village" property="5"/>
    	      <msh:tableColumn isCalcAmount="true" columnName="гор." addParam="&denied=0&patient=city" property="6"/>
    	      <msh:tableColumn isCalcAmount="true" columnName="иног." addParam="&denied=0&patient=inog" property="7"/>
    	      <msh:tableColumn isCalcAmount="true" columnName="иност." addParam="&denied=0&patient=inostr" property="8"/>
    	      <msh:tableColumn isCalcAmount="true" columnName="др." addParam="&denied=0&patient=other" property="9"/>
    	      <msh:tableColumn isCalcAmount="true" columnName="Кол-во экстр." addParam="&denied=0&emergency=1" property="10" />
    	      <msh:tableColumn isCalcAmount="true" columnName="самообр." addParam="&denied=0&emergency=1&orderType=О" property="11" />
    	      <msh:tableColumn isCalcAmount="true" columnName="ск. пом." addParam="&denied=0&emergency=1&orderType=К" property="12" />
    	      <msh:tableColumn isCalcAmount="true" columnName="Кол-во план." addParam="&denied=0&emergency=0" property="13" />
    	      <msh:tableColumn isCalcAmount="true" columnName="Кол-во отказов" addParam="&denied=all" property="14" />
    	      <msh:tableColumn isCalcAmount="true" columnName="направ. в др. ЛПУ" addParam="&denied=4" property="15" />
    	      <msh:tableColumn isCalcAmount="true" columnName="отказ больного" addParam="&denied=8" property="16" />
    	      <msh:tableColumn isCalcAmount="true" columnName="Самовольно покинул отделение" addParam="&denied=5" property="17" />
    	      <msh:tableColumn isCalcAmount="true" columnName="Отсутствие показаний" addParam="&denied=2" property="18" />
    	    </msh:table>
    	    </msh:sectionContent>
    	    </msh:section>
    	    
    	     <msh:section>
    	    <msh:sectionTitle>Список, выписанных пациентов
    	    </msh:sectionTitle>
    	    <msh:sectionContent>
    	    <ecom:webQuery name="journal_priem" nameFldSql="journal_priem_sql" nativeSql=" 
    	  select  
    '&pigeonHole='||vph.id as vphid,vph.name
, count(distinct m.id) as cntAll
,count(distinct case when vho.code!='1' then m.id else null end) as cntDischargeOtherLpu
,count(distinct case when vhr.code='11' then m.id else null end) as cntDeathPatient
,list(distinct case when vhr.code='11' then '<br/>'||pat.lastname||' '||pat.firstname||' '||coalesce(pat.middlename,'')||' г.р.'||to_char(pat.birthday,'dd.mm.yyyy') else null end) as listDeathPatient
from medcase m 
left join Patient pat on pat.id=m.patient_id
left join MisLpu as ml on ml.id=m.department_id
left join VocPigeonHole vph on vph.id=ml.pigeonHole_id
left join Omc_Frm vof on vof.id=m.orderType_id
left join VocHospitalizationOutcome vho on vho.id=m.outcome_id
left join VocHospitalizationResult vhr on vhr.id=m.result_id
where  m.dateFinish=to_date('${param.dateBegin}','dd.mm.yyyy') and m.dtype='HospitalMedCase' 
${departmentSql}
and ( m.noActuality is null or m.noActuality='0')
    	${emerIs} ${pigeonHole} 
    	${serviceStreamSql} ${addPat}
    	group by vph.id,vph.name
    	order by vph.name
    	      " guid="4a720225-8d94-4b47-bef3-4dbbe79eec74" />
    	    <msh:table cellFunction="true" name="journal_priem" action="stac_everyday_report.do?${paramHref}&dtype=Hosp&dateinfo=dateFinish" idField="1">
    	      <msh:tableColumn columnName="#" property="sn" addParam="&dateinfo=dateFinish"/>
    	      <msh:tableColumn columnName="Приемник" property="2" addParam="&dateinfo=dateFinish" />
    	      <msh:tableColumn isCalcAmount="true" columnName="Кол-во выбывших" property="3" addParam="&dateinfo=dateFinish" />
    	      <msh:tableColumn isCalcAmount="true" columnName="выписан. в др. ЛПУ" property="4" addParam="&dateinfo=dateFinish&discharge=otherLpu" />
    	      <msh:tableColumn isCalcAmount="true" columnName="Кол-во умер." property="5"  addParam="&dateinfo=dateFinish&discharge=death" />
    	      <msh:tableColumn columnName="Список умерших пациентов" property="6"  addParam="&dateinfo=dateFinish&discharge=death"/>
    	    </msh:table>
    	    </msh:sectionContent>
    	    </msh:section>
    <%
    		
    	}  else if (view!=null && (view.equals("2"))) {
    		%>
    <msh:section>
    <msh:sectionTitle>
    Список разбивка по реанимациям
    </msh:sectionTitle>
    <msh:sectionContent>
    <ecom:webQuery name="journal_priem" nameFldSql="journal_priem_sql" nativeSql=" 
   select '&department='||ml.id,ml.name
,count(distinct case when (coalesce(m.datefinish,m.transferdate) is null or coalesce(m.datefinish,m.transferdate) > to_date('${param.dateBegin}','dd.mm.yyyy')) then m.id else null end) as cntCurrent
,count(distinct case when m.datestart=to_date('${param.dateBegin}','dd.mm.yyyy')  then m.id else null end) as cntEntr
,count(distinct case when coalesce(transferDate,dateFinish)=to_date('${param.dateBegin}','dd.mm.yyyy')  then m.id else null end) as cntFinish
from medcase m
left join VocServiceStream vss on vss.id=m.serviceStream_id
left join Patient pat on pat.id=m.patient_id
left join VocSocialStatus pvss on pvss.id=pat.socialStatus_id
left join Address2 adr on adr.addressid=pat.address_addressid
left join Mislpu ml on m.department_id=ml.id
left join VocPigeonHole vph on vph.id=ml.pigeonHole_id
left join Omc_Oksm ok on pat.nationality_id=ok.id
left join Diary d on d.medcase_id=m.id
where m.datestart <= to_date('${param.dateBegin}','dd.mm.yyyy') 
and m.dtype='DepartmentMedCase'
and (coalesce(m.datefinish,m.transferdate) is null or coalesce(m.datefinish,m.transferdate) >= to_date('${param.dateBegin}','dd.mm.yyyy'))


and m.deniedHospitalizating_id is null

${emerIs} ${pigeonHole} 
${departmentSql} ${serviceStreamSql} ${addPat}
and ml.isNoOmc='1'
group by ml.id,ml.name
order by ml.name
      " guid="4a720225-8d94-4b47-bef3-4dbbe79eec74" />
    <msh:table  cellFunction="true" name="journal_priem" 
     action="stac_everyday_report.do?${paramHref}" 
    idField="1">
      <msh:tableColumn columnName="#" property="sn" addParam="&dateinfo=dateCurrent"/>
      <msh:tableColumn columnName="Отделение" property="2" addParam="&dateinfo=dateCurrent"/>
      <msh:tableColumn isCalcAmount="true" columnName="Кол-во состоящих" property="3" 
      addParam="&dateinfo=dateCurrent"/>
      <msh:tableColumn isCalcAmount="true" columnName="Кол-во поступивших" property="4" 
      addParam="&dateinfo=dateStart"/>
      <msh:tableColumn isCalcAmount="true" columnName="Кол-во выбывших (переведенных)" property="5" 
      addParam="&dateinfo=coalesce(transferDate,dateFinish)"
      />
    </msh:table>
    </msh:sectionContent>
    </msh:section>
        	   
    	    <%
    	}  else if (view!=null && (view.equals("3"))) {
    		%>
  <msh:section>
    <msh:sectionTitle>
    Список разбивка по отделениям
    </msh:sectionTitle>
    <msh:sectionContent>
    <ecom:webQuery name="journal_priem" nameFldSql="journal_priem_sql" nativeSql=" 
   select '&department='||ml.id,ml.name
,count(distinct case when (coalesce(m.datefinish,m.transferdate) is null or coalesce(m.datefinish,m.transferdate) > to_date('${param.dateBegin}','dd.mm.yyyy')) then m.id else null end) as cntCurrent
,count(distinct case when (coalesce(m.datefinish,m.transferdate) is null or coalesce(m.datefinish,m.transferdate) > to_date('${param.dateBegin}','dd.mm.yyyy')) and vss.code='OBLIGATORYINSURANCE' then m.id else null end) as cntCurrentOmc
,count(distinct case when (coalesce(m.datefinish,m.transferdate) is null or coalesce(m.datefinish,m.transferdate) > to_date('${param.dateBegin}','dd.mm.yyyy')) and (vss.code in ('PRIVATEINSURANCE','DOGOVOR','FCB')) then m.id else null end) as cntCurrentDmc
,count(distinct case when m.datestart=to_date('${param.dateBegin}','dd.mm.yyyy') then m.id else null end) as cntEntr
,count(distinct case when m.datestart=to_date('${param.dateBegin}','dd.mm.yyyy') and  m.emergency='1' then m.id else null end) as cntEntrEmergency
,count(distinct case when m.datestart=to_date('${param.dateBegin}','dd.mm.yyyy') and (m.emergency='0' or m.emergency is null) then m.id else null end) as cntEntrPlan
,count(distinct case when m.transferdate=to_date('${param.dateBegin}','dd.mm.yyyy') and (m.emergency='0' or m.emergency is null) then m.id else null end) as cntTransfer
,count(distinct case when m.datefinish=to_date('${param.dateBegin}','dd.mm.yyyy') then m.id else null end) as cntFinish
,count(distinct case when m.datefinish=to_date('${param.dateBegin}','dd.mm.yyyy') and vhr.code='11' then m.id else null end) as cntDeath
,count(distinct case when so.operationdate=to_date('${param.dateBegin}','dd.mm.yyyy') then m.id else null end) as cntOper
,count(distinct case when so.operationdate=to_date('${param.dateBegin}','dd.mm.yyyy') and m.emergency='1' then m.id else null end) as cntOperEm
,count(distinct case when so.operationdate=to_date('${param.dateBegin}','dd.mm.yyyy') and (m.emergency='0' or m.emergency is null) then m.id else null end) as cntOperPl
,count(distinct case when d.date=to_date('${param.dateBegin}','dd.mm.yyyy') and d.state_id=3 then m.id else null end) as cntOperPl
from medcase m
left join VocServiceStream vss on vss.id=m.serviceStream_id
left join Patient pat on pat.id=m.patient_id
left join VocSocialStatus pvss on pvss.id=pat.socialStatus_id
left join Address2 adr on adr.addressid=pat.address_addressid
left join Mislpu ml on m.department_id=ml.id
left join VocPigeonHole vph on vph.id=ml.pigeonHole_id
left join SurgicalOperation so on so.medcase_id=m.id
left join Omc_Oksm ok on pat.nationality_id=ok.id
left join Diary d on d.medcase_id=m.id
left join MedCase sls on sls.id=m.parent_id
left join VocHospitalizationResult vhr on vhr.id=sls.result_id
where m.datestart <= to_date('${param.dateBegin}','dd.mm.yyyy') 
and m.dtype='DepartmentMedCase'
and (coalesce(m.datefinish,m.transferdate) is null or coalesce(m.datefinish,m.transferdate) >= to_date('${param.dateBegin}','dd.mm.yyyy'))
and (ml.isNoOmc='0' or ml.isNoOmc is null)
and m.deniedHospitalizating_id is null
${emerIs} ${pigeonHole} 
${departmentSql} ${serviceStreamSql} ${addPat} 
group by ml.id,ml.name
order by ml.name
      " guid="4a720225-8d94-4b47-bef3-4dbbe79eec74" />
    <msh:table cellFunction="true" name="journal_priem" 
     action="stac_everyday_report.do?${paramHref}" idField="1">
      <msh:tableColumn columnName="#" property="sn" />
      <msh:tableColumn columnName="Отделение" property="2" />
      <msh:tableColumn isCalcAmount="true" columnName="Кол-во состоящих" addParam="&dateinfo=dateCurrent" property="3" />
      <msh:tableColumn isCalcAmount="true" columnName="ОМС" property="4" addParam="&dateinfo=dateCurrent&stream=OBLIGATORYINSURANCE" />
      <msh:tableColumn isCalcAmount="true" columnName="внебюдж./ДМС" property="5" addParam="&dateinfo=dateCurrent&stream=PRIVATEINSURANCE,DOGOVOR,FCB"/>
      <msh:tableColumn isCalcAmount="true" columnName="Кол-во пост." property="6" addParam="&dateinfo=dateStart"/>
      <msh:tableColumn isCalcAmount="true" columnName="экстр." property="7" addParam="&dateinfo=dateStart&emergency=1"/>
      <msh:tableColumn isCalcAmount="true" columnName="план." property="8" addParam="&dateinfo=dateStart&emergency=0"/>
      <msh:tableColumn isCalcAmount="true" columnName="Кол-во перев. в др.отд" property="9" addParam="&dateinfo=transferDate"/>
      <msh:tableColumn isCalcAmount="true" columnName="Кол-во выбывших" property="10" addParam="&dateinfo=dateFinish"/>
      <msh:tableColumn isCalcAmount="true" columnName="из них умерло" property="11" addParam="&dateinfo=dateCurrent&discharge=death"/>
      <msh:tableColumn isCalcAmount="true" columnName="Кол-во опер." property="12" addParam="&dateinfo=dateCurrent&operation=all"/>
      <msh:tableColumn isCalcAmount="true" columnName="экстр." property="13" addParam="&dateinfo=dateCurrent&operation=emergency"/>
      <msh:tableColumn isCalcAmount="true" columnName="план." property="14" addParam="&dateinfo=dateCurrent&operation=plan"/>
    </msh:table>
    </msh:sectionContent>
    </msh:section>    	   
    	    <%
    	} else {
    		
    		
    
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

