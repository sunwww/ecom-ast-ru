<%@page import="ru.nuzmsh.util.query.ReportParamUtil"%>
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
    <msh:title guid="helloItle-123" mainMenu="StacJournal">Ежедневный отчет по стационару</msh:title>
  </tiles:put>
  <tiles:put name="side" type="string">
  	<tags:stac_journal currentAction="stac_everyday"/>
  </tiles:put>
  <tiles:put name="body" type="string">
    <%
    String typeReestr = request.getParameter("typeReestr") ;
    String typeHour =ActionUtil.updateParameter("ReestrByHospitalMedCase","typeHour","2", request) ;
	String typeView =ActionUtil.updateParameter("ReestrByHospitalMedCase","typeView","1", request) ;
    if (typeReestr==null) {
	  	String noViewForm = request.getParameter("noViewForm") ;
		String typeDate =ActionUtil.updateParameter("ReestrByHospitalMedCase","typeDate","1", request) ;
		String typeEmergency =ActionUtil.updateParameter("ReestrByHospitalMedCase","typeEmergency","3", request) ;
		
    
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
      <%--
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
      --%>
        <msh:row>
	        <td class="label" title="Просмотр данных (typeView)" colspan="1"><label for="typeViewName" id="typeViewLabel">Отобразить информацию:</label></td>
	        <td onclick="this.childNodes[1].checked='checked';">
	        	<input type="radio" name="typeView" value="1">  общий отчет на 8 часов
	        </td>
	        <td onclick="this.childNodes[1].checked='checked';">
	        	<input type="radio" name="typeView" value="2"  >  иностранцам
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
    	var dt = new Date() ;
    	dt.setDate(dt.getDate()-1);
    	$('dateBegin').value=format2day(dt.getDate())+"."+format2day(dt.getMonth()+1)+"."+dt.getFullYear() ;
    }

			 
    </script>
    <%
    }
    String date = request.getParameter("dateBegin") ;
    
    if (date!=null && !date.equals(""))  {
    	//request.setAttribute("date", date) ;
    	//String view = (String)request.getAttribute("typeView") ;
    	
    	String pigeonHole="" ;
    	String pigeonHole1="" ;
    	StringBuilder paramHref = new StringBuilder();
    	paramHref.append("typeReestr=1");
    	paramHref.append("&typeView=").append(typeView);
    	
    	ActionUtil.setParameterFilterSql("serviceStream", "m.serviceStream_id", request);
    	ActionUtil.setParameterFilterSql("department", "ml.id", request);
    	ActionUtil.setParameterFilterSql("pigeonHole", "ml.pigeonHole_id", request);
    	String serviceStream=request.getParameter("serviceStream");
    	paramHref.append("&serviceStream=").append(serviceStream!=null?serviceStream:"");
    	paramHref.append("&dateBegin=").append(date);
    	request.setAttribute("paramHref", paramHref.toString()) ;
    	
    	
			String timeI = null ;
			
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
	    	} else if (typeHour!=null && typeHour.equals("3")) {
	    		timeSql= "09:00" ;timeInfo="(9 часов)" ;
	    	} else if (typeHour!=null && typeHour.equals("4")) {
	    		timeSql= "15:00" ;timeInfo="(15 часов)" ;
	    	}
		    
		    %>

		    <%
		    if (typeReestr!=null && typeReestr.equals("1")) {
    			String patient=request.getParameter("patient") ; String dateinfo=request.getParameter("dateinfo") ;
    			String age=request.getParameter("age") ; String discharge=request.getParameter("discharge") ;
    			String denied=request.getParameter("denied") ; String emergency=request.getParameter("emergency") ;
    			String orderType=request.getParameter("orderType") ; String stream=request.getParameter("stream") ;
    			String operation=request.getParameter("operation") ; String dtype=request.getParameter("dtype" );
    			String anesthesia=request.getParameter("anesthesia") ;
    			StringBuilder where = new StringBuilder() ;
    			
    			if (dateinfo==null) {
    				//No date dateinfo
    			} else {
    				String dtypeAs = "slo";
    				if (dtype!=null&&dtype.equals("Hosp")) {
    					dtype="HospitalMedCase" ;dtypeAs="sls" ;
    				} else if (dtype!=null&&dtype.equals("SurgicalOperation")) {
    					dtype="SurgicalOperation" ;dtypeAs="so" ;
    				} else {
    					dtype="DepartmentMedCase" ;
    				}
    				if (dateinfo.equals("dateCurrent")) {
    					//TODO
    					if (dtype.equals("HospitalMedCase")) {
        					where.append(new StringBuilder() 
        		   			.append(" (medcase.dateFinish is null ")
        		    		.append(" or ") 
        		    		.append(ReportParamUtil.getDateFrom(Boolean.FALSE, "medcase.dateFinish", "medcase.dischargeTime", timeSql!=null?date1:date, timeSql)) 
        		    		.append(") and ") 
        		    		.append(ReportParamUtil.getDateTo(Boolean.FALSE, "medcase.dateStart", "medcase.entranceTime", timeSql!=null?date1:date1, timeSql)) 
        		    		.append(" ").toString().replaceAll("medcase", dtypeAs)) ;
    					} else {
        					where.append(new StringBuilder() 
        		   			.append(" (coalesce(medcase.dateFinish,medcase.transferDate) is null ")
        		    		.append(" or ") 
        		    		.append(ReportParamUtil.getDateFrom(Boolean.FALSE, "coalesce(medcase.dateFinish,medcase.transferDate)", "coalesce(medcase.dischargeTime,medcase.transferTime)", timeSql!=null?date1:date, timeSql)) 
        		    		.append(") and ") 
        		    		.append(ReportParamUtil.getDateTo(Boolean.FALSE, "medcase.dateStart", "medcase.entranceTime", timeSql!=null?date1:date1, timeSql)) 
        		    		.append(" ").toString().replaceAll("medcase", dtypeAs)) ;
    					}
    				} else if (dateinfo.equals("dateStart")) {
    					where.append(new StringBuilder() 
     					.append(ReportParamUtil.getPeriodByDate(Boolean.FALSE, "medcase.dateStart", "medcase.entranceTime", date, timeSql)) 
		    			.toString().replaceAll("medcase", dtypeAs)); 
    				} else if (dateinfo.equals("dateFinish")) {
    					where.append(new StringBuilder() 
     					.append(ReportParamUtil.getPeriodByDate(Boolean.FALSE, "medcase.dateFinish", "medcase.dischargeTime", date, timeSql)) 
		    			.toString().replaceAll("medcase", dtypeAs)); 
    				} else if (dateinfo.equals("coalesce(transferDate,dateFinish)")) {
    					
    					//TODO
    					where.append(new StringBuilder() 
     					.append(ReportParamUtil.getPeriodByDate(Boolean.FALSE, "coalesce(medcase.dateFinish,medcase.transferdate)", "coalesce(medcase.dischargeTime,medcase.transferTime)", date, timeSql)) 
		    			.toString().replaceAll("medcase", dtypeAs)); 
    				} else if (dateinfo.equals("transferDate")) {
    					where.append(new StringBuilder() 
     					.append(ReportParamUtil.getPeriodByDate(Boolean.FALSE, "medcase.transferDate", "medcase.transferTime", date, timeSql)) 
		    			.toString().replaceAll("medcase", dtypeAs)); 
    				} else if (dateinfo.equals("operationDate")) {
    					where.append(new StringBuilder() 
     					.append(ReportParamUtil.getPeriodByDate(Boolean.FALSE, "medcase.operationDate", "medcase.operationTime", date, timeSql)) 
		    			.toString().replaceAll("medcase", dtypeAs)); 
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
    				if (operation!=null) {
    					if (operation.equals("plan")) {
    						where.append(" and ").append(dtypeAs).append(".aspect_id='1'") ;
    					} else if (operation.equals("emergency")) {
    						where.append(" and ").append(dtypeAs).append(".aspect_id='2'") ;
    					}
    				}
    				if (anesthesia!=null) {
    					request.setAttribute("leftJoinAdd", "left join workfunction wf on wf.id=a.anesthesist_id left join worker w on w.id=wf.worker_id left join MisLpu ml on ml.id=w.lpu_id");
    					where.append(" and a.id is not null") ;
    				} else {
    					request.setAttribute("leftJoinAdd", "left join MisLpu ml on ml.id=so.department_id");
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
    					where.append(" and (ok.isCurrent='1' or ok.id is null) and adr.kladr is not null and adr.isCurrentRegion='1' and adr.addressisvillage='1'");
    				} else if (patient.equals("city")) {
    					where.append(" and (ok.isCurrent='1' or ok.id is null) and adr.kladr is not null and adr.isCurrentRegion='1' and adr.addressiscity='1'");
    				} else if (patient.equals("inostr")) {
    					where.append(" and ok.isCurrent='0'") ;
    				} else if (patient.equals("inog")) {
    					where.append(" and (ok.isCurrent='1' or ok.id is null) and adr.kladr is not null and adr.isCurrentRegion is null");
    				} else if (patient.equals("other")) {
    					where.append(" and (ok.isCurrent='0' or ok.id is null) and (adr.addressid is null or adr.domen<3)") ;
    				}
    				if (stream!=null) {
    					String stIn = "in" ;
    					if (stream.startsWith("-")) {stIn="not in";stream=stream.substring(1);}
    					String[] st=stream.split(",");
    					StringBuilder s = new StringBuilder() ;
    					for (int i=0;i<st.length;i++) {
    						if (i>0) s.append(",") ;
    						s.append("'").append(st[i]).append("'");
    					}
    					where.append(" and vss.code ").append(stIn).append(" (").append(s.toString()).append(")") ;
    				}
    				if (age!=null) {
    					if (age.equals("0")) {
    						where.append(" and pat.newborn_id is not null") ;
    					} else if (age.equals("0-14")){
    						where.append(" and (to_date('").append(date).append("','dd.mm.yyyy')-pat.birthday)<(17*355)") ;
    					}
    				}
    				request.setAttribute("whereSql", where.toString()) ;
    				//request.setAttribute("groupBy", groupBy) ;
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
   
    <msh:table action="entitySubclassView-mis_medCase.do"
    viewUrl="entitySubclassShortView-mis_medCase.do"
     name="reestr" idField="1">
    	<msh:tableColumn property="sn" columnName="#"/>
    	<msh:tableColumn property="2" columnName="Дата и время пост."/>
    	<msh:tableColumn property="3" columnName="ИБ"/>
    	<msh:tableColumn property="4" columnName="ФИО"/>
    	<msh:tableColumn property="5" columnName="Поток обслуживания"/>
    </msh:table>
    			<%
    				} else if (dtype.equals("SurgicalOperation")) {
    					//String operation=request.getParameter("operation") ;
    					
//&dateinfo=operationDate&anesthesia=1&dtype=SurgicalOperation&operation=plan
    					%>
    <ecom:webQuery name="reestr" nameFldSql="reestr_sql" nativeSql="
    select so.id as sloid
    ,to_char(so.operationDate,'dd.mm.yyyy')||' '||cast(so.operationTime as varchar(5)) as sooparetiondate
    ,ss.code as sscode
    ,pat.lastname||' '||pat.firstname||' '||pat.middlename as fio
    ,vss.name as vssname
    ,ms.code||' '||ms.name as msname,vha.name as vhaname
    ,case when (count(a.id)>0) then 'Да' else null end as anest 
     from SurgicalOperation so
     left join Anesthesia a on a.surgicalOPeration_id=so.id
     left join MedService ms on ms.id=so.medService_id
	left join MedCase slo on so.medcase_id=slo.id
    left join MedCase sls on sls.id=slo.parent_id
    left join StatisticStub ss on ss.id=sls.statisticStub_id
    left join Patient pat on pat.id=slo.patient_id
    left join VocSocialStatus pvss on pvss.id=pat.socialStatus_id
	left join Address2 adr on adr.addressid=pat.address_addressid
	${leftJoinAdd}
	left join VocPigeonHole vph on vph.id=ml.pigeonHole_id
	left join Omc_Oksm ok on pat.nationality_id=ok.id
	left join Omc_Frm vof on vof.id=slo.orderType_id
	left join Diary d on d.medcase_id=slo.id
	left join VocHospitalizationOutcome vho on vho.id=sls.outcome_id
	left join VocHospitalizationResult vhr on vhr.id=sls.result_id
	left join VocServiceStream vss on vss.id=slo.serviceStream_id
	left join VocHospitalAspect vha on vha.id=so.aspect_id
	where ${whereSql} 
	group by so.id,pat.id,pat.lastname,pat.firstname,pat.middlename,ss.code,vss.name
	,slo.Entrancetime,slo.dateStart,so.operationDate,so.operationTime,ms.name,vha.name,ms.code
	order by pat.lastname,pat.firstname,pat.middlename
    "/>
   
    <msh:table action="entityView-stac_surOperation.do"
    viewUrl="entityShortView-stac_surOperation.do"
     name="reestr" idField="1">
    	<msh:tableColumn property="sn" columnName="#"/>
    	<msh:tableColumn property="2" columnName="Дата и время операции"/>
    	<msh:tableColumn property="3" columnName="ИБ"/>
    	<msh:tableColumn property="4" columnName="ФИО"/>
    	<msh:tableColumn property="5" columnName="Поток обслуживания"/>
    	<msh:tableColumn property="6" columnName="Операция"/>
    	<msh:tableColumn property="7" columnName="Показания"/>
    	<msh:tableColumn property="8" columnName="Анестезия"/>
    </msh:table>
        			<%
    				} else if (dtype.equals("HospitalMedCase")) {
    					%>
    					
    				    <ecom:webQuery name="reestr" nameFldSql="reestr_sql" nativeSql="
    				    select sls.id as slsid,to_char(slS.dateStart,'dd.mm.yyyy')||' '||cast(slS.Entrancetime as varchar(5)) as datestart,ss.code as sscode
    				    ,pat.lastname||' '||pat.firstname||' '||pat.middlename as fio,vss.name as vssname
    				    ,ml.name as mlname
    				    ,to_char(sloLast.dateStart,'dd.mm.yyyy')||' '||cast(sloLast.Entrancetime as varchar(5)) as slodatestart
    				    ,mlLast.name as mllastname
    				    ,to_char(sls.dateFinish,'dd.mm.yyyy')||' '||cast(sls.dischargetime as varchar(5)) as slsdatefinish
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
    					,ml.name,mlLast.name,sloLast.dateStart,sloLast.Entrancetime,sls.dateFinish,sls.dischargeTime
    					order by mlLast.name,pat.lastname,pat.firstname,pat.middlename
    				    "/>
    				    
    				    <msh:table action="entitySubclassView-mis_medCase.do" 
    				    viewUrl="entitySubclassShortView-mis_medCase.do"
    				    name="reestr" idField="1">
    				    	<msh:tableColumn property="sn" columnName="#"/>
    				    	<msh:tableColumn property="2" columnName="Дата и время пост. в стационар"/>
    				    	<msh:tableColumn property="3" columnName="ИБ"/>
    				    	<msh:tableColumn property="4" columnName="ФИО"/>
    				    	<msh:tableColumn property="5" columnName="Поток обслуживания"/>
    				    	<msh:tableColumn property="6" columnName="Отделение пост."/>
    				    	<msh:tableColumn property="7" columnName="Отделение сост."/>
    				    	<msh:tableColumn property="8" columnName="Дата пост. в отд сост."/>
    				    	<msh:tableColumn property="9" columnName="Дата выписки"/>
    				    </msh:table>
    				    			<%    					
    				}


    			
    				
    				}} else if (typeView!=null && (typeView.equals("1"))) {
    					request.setAttribute("periodCurrentSls",new StringBuilder() 
    		   			.append(" (medcase.dateFinish is null ")
    		    		.append(" or ") 
    		    		.append(ReportParamUtil.getDateFrom(Boolean.FALSE, "medcase.dateFinish", "medcase.dischargeTime", timeSql!=null?date1:date, timeSql)) 
    		    		.append(") and ") 
    		    		.append(ReportParamUtil.getDateTo(Boolean.FALSE, "medcase.dateStart", "medcase.entranceTime", timeSql!=null?date1:date1, timeSql)) 
    		    		.append(" ").toString().replaceAll("medcase", "sls")); 
    		    		
    					request.setAttribute("periodEntranceSls",new StringBuilder() 
	     					.append(ReportParamUtil.getPeriodByDate(Boolean.FALSE, "medcase.dateStart", "medcase.entranceTime", date, timeSql)) 
    		    			.toString().replaceAll("medcase", "sls")); 
    					request.setAttribute("periodDischargeSls",new StringBuilder() 
	     					.append(ReportParamUtil.getPeriodByDate(Boolean.FALSE, "medcase.dateFinish", "medcase.dischargeTime", date, timeSql)) 
    		    			.toString().replaceAll("medcase", "sls")); 
    		    		
    	%>
    
    <msh:section>
    <msh:sectionTitle>
    Список, состоящих в стационаре
    </msh:sectionTitle>
    <msh:sectionContent>
    <ecom:webQuery name="journal_priem" nameFldSql="journal_priem_sql" nativeSql=" 
   select '&department=${param.department}&pigeonHole='||vph.id,vph.name
,count(distinct  sls.id ) as cntAll
,count(distinct case when pat.newborn_id is not null then sls.id else null end) as cntNewBorn
,count(distinct case when (to_date('${param.dateBegin}','dd.mm.yyyy')-pat.birthday)<(17*355) then sls.id else null end) as cntChild
,count(distinct case when (ok.id is null or ok.isCurrent='1') and adr.isCurrentRegion='1' and adr.addressisvillage='1' then sls.id else null end) as cntVill
,count(distinct case when (ok.id is null or ok.isCurrent='1') and adr.isCurrentRegion='1' and adr.addressiscity='1' then sls.id else null end) as cntCity
,count(distinct case when (ok.id is null or ok.isCurrent='1') and adr.kladr is not null and adr.isCurrentRegion is null then sls.id else null end) as cntInog
,count(distinct case when ok.isCurrent='0' then sls.id else null end) as cntInost
from medcase sls
left join Patient pat on pat.id=sls.patient_id
left join VocSocialStatus pvss on pvss.id=pat.socialStatus_id
left join Address2 adr on adr.addressid=pat.address_addressid
left join Mislpu ml on sls.department_id=ml.id
left join VocPigeonHole vph on vph.id=ml.pigeonHole_id
left join Omc_Oksm ok on pat.nationality_id=ok.id
where  
 ${periodCurrentSls}
and sls.dtype='HospitalMedCase'
${departmentSql}
and sls.deniedHospitalizating_id is null
${pigeonHoleSql} 
 ${serviceStreamSql} 
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
    	    <msh:sectionTitle>Список, поступивших пациентов по приемному отделению</msh:sectionTitle>
    	    <msh:sectionContent>
    	    
    	    <ecom:webQuery name="journal_priem" nameFldSql="journal_priem_sql" nativeSql=" 
    	   select  
    '&department=${param.department}&pigeonHole='||vph.id as idpar,vph.name
, count(distinct sls.id) as all1
,count(distinct case when sls.emergency='1' then sls.id else null end) as obrem
,count(distinct case when sls.emergency='1' and vof.voc_code='О' then sls.id else null end) as allEmSam
,count(distinct case when sls.emergency='1' and vof.voc_code='К' then sls.id else null end) as allEmSkor
,count(distinct case when (sls.emergency is null or sls.emergency='0') then sls.id else null end) as allPl

,count(distinct case when sls.deniedHospitalizating_id is null then sls.id else null end) as obr
,count(distinct case when sls.deniedHospitalizating_id is null 
and (ok.isCurrent='1' or ok.id is null) and adr.isCurrentRegion='1' and adr.addressIsVillage='1'
then sls.id else null end) as obrVil
,count(distinct case when sls.deniedHospitalizating_id is null 
and (ok.isCurrent='1' or ok.id is null) and adr.isCurrentRegion='1' and adr.addressIsCity='1'
then sls.id else null end) as obrCity
,count(distinct case when sls.deniedHospitalizating_id is null 
and (ok.isCurrent='1' or ok.id is null) and adr.addressid is not null and adr.isCurrentRegion is null
then sls.id else null end) as obrInog
,count(distinct case when sls.deniedHospitalizating_id is null 
and ok.isCurrent='0'  then sls.id else null end) as obrInost
,count(distinct case when sls.deniedHospitalizating_id is null 
and (ok.isCurrent='1' or ok.id is null) and (adr.addressid is null or adr.domen<3)  then sls.id else null end) as obrOther


,count(distinct case when sls.emergency='1' and sls.deniedHospitalizating_id is null then sls.id else null end) as em
,count(distinct case when sls.emergency='1' and sls.deniedHospitalizating_id is null and vof.voc_code='О' then sls.id else null end) as emSam
,count(distinct case when sls.emergency='1' and sls.deniedHospitalizating_id is null and vof.voc_code='К' then sls.id else null end) as emSkor
,count(distinct case when (sls.emergency is null or sls.emergency='0') and sls.deniedHospitalizating_id is null then sls.id else null end) as pl
, count(distinct case when sls.deniedHospitalizating_id is not null then sls.id else null end) as denied 
, count(distinct case when sls.deniedHospitalizating_id='4' then sls.id else null end) as denied4 
, count(distinct case when sls.deniedHospitalizating_id='8' then sls.id else null end) as denied8
, count(distinct case when sls.deniedHospitalizating_id='5' then sls.id else null end) as denied5
, count(distinct case when sls.deniedHospitalizating_id='2' then sls.id else null end) as denied2
from medcase sls 
left join MisLpu as ml on ml.id=sls.department_id
left join VocPigeonHole vph on vph.id=ml.pigeonHole_id
left join Patient p on p.id=sls.patient_id
left join Address2 adr on p.address_addressid=adr.addressid
left join Omc_Oksm ok on ok.id=p.nationality_id 
left join Omc_Frm vof on vof.id=sls.orderType_id
where ${periodEntranceSls}
${departmentSql} 
and sls.dtype='HospitalMedCase' and ( sls.noActuality is null or sls.noActuality='0')

    	 ${pigeonHoleSql}  
    	 ${serviceStreamSql} 
    	group by vph.id,vph.name
    	order by vph.name
    	      " guid="4a720225-8d94-4b47-bef3-4dbbe79eec74" />
    	     
    	    <msh:table cellFunction="true" name="journal_priem" 
    	    action="stac_everyday_report.do?${paramHref}&dtype=Hosp&dateinfo=dateStart" idField="1">
    	      <msh:tableColumn columnName="#" property="sn" addParam="" guid="34a9f56ab-a3fa-5c1afdf6c41d" />
    	      <msh:tableColumn columnName="Приемник" addParam="" property="2"/>
    	      <msh:tableColumn isCalcAmount="true" columnName="Кол-во обрат." addParam="" property="3"/>
    	      <msh:tableColumn isCalcAmount="true" columnName="кол-во экстр." addParam="&emergency=1" property="4" />
    	      <msh:tableColumn isCalcAmount="true" columnName="самообр." addParam="&emergency=1&orderType=О" property="5" />
    	      <msh:tableColumn isCalcAmount="true" columnName="ск. пом." addParam="&emergency=1&orderType=К" property="6" />
    	      <msh:tableColumn isCalcAmount="true" columnName="кол-во план." addParam="&emergency=0" property="7" />

    	      <msh:tableColumn isCalcAmount="true" columnName="Кол-во госп" addParam="&denied=0" property="8"/>
    	      <msh:tableColumn isCalcAmount="true" columnName="с.ж." addParam="&denied=0&patient=village" property="9"/>
    	      <msh:tableColumn isCalcAmount="true" columnName="гор." addParam="&denied=0&patient=city" property="10"/>
    	      <msh:tableColumn isCalcAmount="true" columnName="иног." addParam="&denied=0&patient=inog" property="11"/>
    	      <msh:tableColumn isCalcAmount="true" columnName="иност." addParam="&denied=0&patient=inostr" property="12"/>
    	      <msh:tableColumn isCalcAmount="true" columnName="др." addParam="&denied=0&patient=other" property="13"/>
    	      <msh:tableColumn isCalcAmount="true" columnName="Кол-во экстр." addParam="&denied=0&emergency=1" property="14" />
    	      <msh:tableColumn isCalcAmount="true" columnName="самообр." addParam="&denied=0&emergency=1&orderType=О" property="15" />
    	      <msh:tableColumn isCalcAmount="true" columnName="ск. пом." addParam="&denied=0&emergency=1&orderType=К" property="16" />
    	      <msh:tableColumn isCalcAmount="true" columnName="Кол-во план." addParam="&denied=0&emergency=0" property="17" />
    	      <msh:tableColumn isCalcAmount="true" columnName="Кол-во отказов" addParam="&denied=all" property="18" />
    	      <msh:tableColumn isCalcAmount="true" columnName="направ. в др. ЛПУ" addParam="&denied=4" property="19" />
    	      <msh:tableColumn isCalcAmount="true" columnName="отказ больного" addParam="&denied=8" property="20" />
    	      <msh:tableColumn isCalcAmount="true" columnName="Самовольно покинул отделение" addParam="&denied=5" property="17" />
    	      <msh:tableColumn isCalcAmount="true" columnName="Отсутствие показаний" addParam="&denied=2" property="18" />
    	    </msh:table>
    	    </msh:sectionContent>
    	    </msh:section>
    	    
    	     <msh:section>
    	    <msh:sectionTitle>Список, выписанных пациентов по приемному отделению
    	    </msh:sectionTitle>
    	    <msh:sectionContent>
    	    <ecom:webQuery name="journal_priem" nameFldSql="journal_priem_sql" nativeSql=" 
    	  select  
    '&department=${param.department}&pigeonHole='||vph.id as vphid,vph.name
, count(distinct sls.id) as cntAll
,count(distinct case when vho.code!='1' then sls.id else null end) as cntDischargeOtherLpu
,count(distinct case when vhr.code='11' then sls.id else null end) as cntDeathPatient
,list(distinct case when vhr.code='11' then '<br/>'||pat.lastname||' '||pat.firstname||' '||coalesce(pat.middlename,'')||' г.р.'||to_char(pat.birthday,'dd.mm.yyyy') else null end) as listDeathPatient
from medcase sls 
left join Patient pat on pat.id=sls.patient_id
left join MisLpu as ml on ml.id=sls.department_id
left join VocPigeonHole vph on vph.id=ml.pigeonHole_id
left join Omc_Frm vof on vof.id=sls.orderType_id
left join VocHospitalizationOutcome vho on vho.id=sls.outcome_id
left join VocHospitalizationResult vhr on vhr.id=sls.result_id
where  ${periodDischargeSls} and sls.dtype='HospitalMedCase' 
${departmentSql}
and ( sls.noActuality is null or sls.noActuality='0')
    	${pigeonHoleSql}  
    	${serviceStreamSql}
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
    		
    	
    		request.setAttribute("periodCurrentSlo",new StringBuilder() 
   			.append(" (coalesce(medcase.dateFinish,medcase.transferDate) is null ")
    		.append(" or ") 
    		.append(ReportParamUtil.getDateFrom(Boolean.FALSE, "coalesce(medcase.dateFinish,medcase.transferDate)", "coalesce(medcase.dischargeTime,medcase.transferTime)", timeSql!=null?date1:date, timeSql)) 
    		.append(") and ") 
    		.append(ReportParamUtil.getDateTo(Boolean.FALSE, "medcase.dateStart", "medcase.entranceTime", timeSql!=null?date1:date1, timeSql)) 
    		.append(" ").toString().replaceAll("medcase", "slo")); 
    		
    		request.setAttribute("periodCurrentSlo1",new StringBuilder() 
   			.append(" (medcase.dateFinish is null ")
    		.append(" or ") 
    		.append(ReportParamUtil.getDateFrom(Boolean.FALSE, "coalesce(medcase.dateFinish,medcase.transferDate)", "coalesce(medcase.dischargeTime,medcase.transferTime)", timeSql!=null?date:date, timeSql)) 
    		.append(") and ") 
    		.append(ReportParamUtil.getDateTo(Boolean.FALSE, "medcase.dateStart", "medcase.entranceTime", timeSql!=null?date1:date1, timeSql)) 
    		.append(" ").toString().replaceAll("medcase", "slo")); 
    		
			request.setAttribute("periodEntranceSlo",new StringBuilder() 
					.append(ReportParamUtil.getPeriodByDate(Boolean.FALSE, "medcase.dateStart", "medcase.entranceTime", date, timeSql)) 
    			.toString().replaceAll("medcase", "slo")); 
			request.setAttribute("periodTransferSlo",new StringBuilder() 
					.append(ReportParamUtil.getPeriodByDate(Boolean.FALSE, "medcase.transferDate", "medcase.transferTime", date, timeSql)) 
    			.toString().replaceAll("medcase", "slo")); 
			request.setAttribute("periodDischargeSlo",new StringBuilder() 
					.append(ReportParamUtil.getPeriodByDate(Boolean.FALSE, "medcase.dateFinish", "medcase.dischargeTime", date, timeSql)) 
    			.toString().replaceAll("medcase", "slo")); 
			request.setAttribute("periodOperation",new StringBuilder() 
				.append(ReportParamUtil.getPeriodByDate(Boolean.FALSE, "medcase.operationdate", "medcase.operationtime", date, timeSql)) 
				.toString().replaceAll("medcase", "so")); 

    		%>
    <msh:section>
    <msh:sectionTitle>
    Разбивка по реанимациям
    </msh:sectionTitle>
    <msh:sectionContent>
    <ecom:webQuery name="journal_priem" nameFldSql="journal_priem_sql" nativeSql=" 
   select '&department='||ml.id,ml.name
,count(distinct case when ${periodCurrentSlo} then slo.id else null end) as cntCurrent
,count(distinct case when ${periodEntranceSlo}  then slo.id else null end) as cntEntr
,count(distinct case when ${periodTransferSlo}  then slo.id else null end) as cntTransfer
,count(distinct case when ${periodDischargeSlo}  then slo.id else null end) as cntFinish
,count(distinct case when ${periodDischargeSlo} and vhr.code='11'  then slo.id else null end) as cntFinish1
,(select count(distinct so.id) from Anesthesia a left join SurgicalOperation so on so.id=a.surgicalOperation_id left join workfunction wf on wf.id=a.anesthesist_id left join worker w on w.id=wf.worker_id where ${periodOperation} and w.lpu_id=ml.id) as cntOper
,(select count(distinct so.id) from Anesthesia a left join SurgicalOperation so on so.id=a.surgicalOperation_id left join workfunction wf on wf.id=a.anesthesist_id left join worker w on w.id=wf.worker_id where ${periodOperation} and w.lpu_id=ml.id and so.aspect_id='2') as cntOperEmer
,(select count(distinct so.id) from Anesthesia a left join SurgicalOperation so on so.id=a.surgicalOperation_id left join workfunction wf on wf.id=a.anesthesist_id left join worker w on w.id=wf.worker_id where ${periodOperation} and w.lpu_id=ml.id and so.aspect_id='1') as cntOperPlan

from medcase slo
left join MedCase sls on sls.id=slo.parent_id and sls.dtype='HospitalMedCase'
left join VocServiceStream vss on vss.id=slo.serviceStream_id
left join Patient pat on pat.id=slo.patient_id
left join VocSocialStatus pvss on pvss.id=pat.socialStatus_id
left join Address2 adr on adr.addressid=pat.address_addressid
left join Mislpu ml on slo.department_id=ml.id
left join VocPigeonHole vph on vph.id=ml.pigeonHole_id
left join Omc_Oksm ok on pat.nationality_id=ok.id
left join VocHospitalizationResult vhr on vhr.id=sls.result_id

where slo.dtype='DepartmentMedCase'
and ${periodCurrentSlo1}

${pigeonHoleSql} ${departmentSql} ${serviceStreamSql} 
and ml.isNoOmc='1'
group by ml.id,ml.name
order by ml.name
      "/>
      
    <msh:table  cellFunction="true" name="journal_priem" 
     action="stac_everyday_report.do?${paramHref}" 
    idField="1">
      <msh:tableColumn columnName="#" property="sn" addParam="&dateinfo=dateCurrent"/>
      <msh:tableColumn columnName="Отделение" property="2" addParam="&dateinfo=dateCurrent"/>
      <msh:tableColumn isCalcAmount="true" columnName="Кол-во состоящих" property="3" 
      addParam="&dateinfo=dateCurrent"/>
      <msh:tableColumn isCalcAmount="true" columnName="Кол-во поступивших" property="4" 
      addParam="&dateinfo=dateStart"/>
      <msh:tableColumn isCalcAmount="true" columnName="Кол-во переведенных" property="5" 
      addParam="&dateinfo=transferDate"/>
      <msh:tableColumn isCalcAmount="true" columnName="Кол-во выбывших" property="6" 
      addParam="&dateinfo=dateFinish"
      />
      <msh:tableColumn isCalcAmount="true" columnName="Кол-во умерших" property="7" 
      addParam="&dateinfo=dateFinish&discharge=death"
      />
      <msh:tableColumn isCalcAmount="true" columnName="Кол-во наркозов" property="8" 
      addParam="&dateinfo=operationDate&anesthesia=1&dtype=SurgicalOperation"
      />
      <msh:tableColumn isCalcAmount="true" columnName="экстр." property="9" 
      addParam="&dateinfo=operationDate&anesthesia=1&dtype=SurgicalOperation&operation=emergency"
      />
      <msh:tableColumn isCalcAmount="true" columnName="план." property="10" 
      addParam="&dateinfo=operationDate&anesthesia=1&dtype=SurgicalOperation&operation=plan"
      />
    </msh:table>
    </msh:sectionContent>
    </msh:section>
        	   
    	    <%
    	
    		request.setAttribute("periodCurrentSlo",new StringBuilder() 
   			.append(" (coalesce(medcase.dateFinish,medcase.transferDate) is null ")
    		.append(" or ") 
    		.append(ReportParamUtil.getDateFrom(Boolean.FALSE, "coalesce(medcase.dateFinish,medcase.transferDate)", "coalesce(medcase.dischargeTime,medcase.transferTime)", timeSql!=null?date1:date, timeSql)) 
    		.append(") and ") 
    		.append(ReportParamUtil.getDateTo(Boolean.FALSE, "medcase.dateStart", "medcase.entranceTime", timeSql!=null?date1:date1, timeSql)) 
    		.append(" ").toString().replaceAll("medcase", "slo")); 
    		
    		request.setAttribute("periodCurrentSlo1",new StringBuilder() 
   			.append(" (medcase.dateFinish is null ")
    		.append(" or ") 
    		.append(ReportParamUtil.getDateFrom(Boolean.FALSE, "coalesce(medcase.dateFinish,medcase.transferDate)", "coalesce(medcase.dischargeTime,medcase.transferTime)", timeSql!=null?date:date, timeSql)) 
    		.append(") and ") 
    		.append(ReportParamUtil.getDateTo(Boolean.FALSE, "medcase.dateStart", "medcase.entranceTime", timeSql!=null?date1:date1, timeSql)) 
    		.append(" ").toString().replaceAll("medcase", "slo")); 
    		
			request.setAttribute("periodOperation",new StringBuilder() 
					.append(ReportParamUtil.getPeriodByDate(Boolean.FALSE, "medcase.operationdate", "medcase.operationtime", date, timeSql)) 
    			.toString().replaceAll("medcase", "so")); 
			request.setAttribute("periodEntranceSlo",new StringBuilder() 
					.append(ReportParamUtil.getPeriodByDate(Boolean.FALSE, "medcase.dateStart", "medcase.entranceTime", date, timeSql)) 
    			.toString().replaceAll("medcase", "slo")); 
			request.setAttribute("periodDischargeTransferSlo",new StringBuilder() 
					.append(ReportParamUtil.getPeriodByDate(Boolean.FALSE, "coalesce(medcase.dateFinish,medcase.transferDate)", "coalesce(medcase.dischargeTime,medcase.transferTime)", date, timeSql)) 
    			.toString().replaceAll("medcase", "slo")); 
			request.setAttribute("periodDischargeSlo",new StringBuilder() 
					.append(ReportParamUtil.getPeriodByDate(Boolean.FALSE, "medcase.dateFinish", "medcase.dischargeTime", date, timeSql)) 
    			.toString().replaceAll("medcase", "slo")); 
			request.setAttribute("periodTransferSlo",new StringBuilder() 
					.append(ReportParamUtil.getPeriodByDate(Boolean.FALSE, "medcase.transferDate", "medcase.transferTime", date, timeSql)) 
    			.toString().replaceAll("medcase", "slo")); 
    		%>
  <msh:section>
    <msh:sectionTitle>
    Разбивка по отделениям
    </msh:sectionTitle>
    <msh:sectionContent>
    <ecom:webQuery name="journal_priem" nameFldSql="journal_priem_sql" nativeSql=" 
   select '&department='||ml.id,ml.name
,count(distinct case when ${periodCurrentSlo} then slo.id else null end) as cntCurrent
,count(distinct case when ${periodCurrentSlo} and vss.code='OBLIGATORYINSURANCE' then slo.id else null end) as cntCurrentOmc
,count(distinct case when ${periodCurrentSlo} and (vss.code not in ('PRIVATEINSURANCE','OBLIGATORYINSURANCE','OTHER','BUDGET')) then slo.id else null end) as cntCurrentVnebud
,count(distinct case when ${periodCurrentSlo} and (vss.code in ('PRIVATEINSURANCE')) then slo.id else null end) as cntCurrentDmc
,count(distinct case when ${periodCurrentSlo} and (vss.code in ('OTHER')) then slo.id else null end) as cntCurrentOther
,count(distinct case when ${periodEntranceSlo} then slo.id else null end) as cntEntr
,count(distinct case when ${periodEntranceSlo} and  sls.emergency='1' then slo.id else null end) as cntEntrEmergency
,count(distinct case when ${periodEntranceSlo} and (sls.emergency='0' or sls.emergency is null) then slo.id else null end) as cntEntrPlan
,count(distinct case when ${periodTransferSlo} then slo.id else null end) as cntTransfer
,count(distinct case when ${periodDischargeSlo} then slo.id else null end) as cntFinish
,count(distinct case when ${periodDischargeSlo} and vhr.code='11' then slo.id else null end) as cntDeath
,(select count(distinct so.id) from SurgicalOperation so where so.department_id=ml.id and ${periodOperation}) as cntOper
,(select count(distinct so.id) from SurgicalOperation so where so.department_id=ml.id and ${periodOperation} and so.aspect_id='2') as cntOperEmer
,(select count(distinct so.id) from SurgicalOperation so where so.department_id=ml.id and ${periodOperation} and so.aspect_id='1') as cntOperPlan
from medcase slo
left join VocServiceStream vss on vss.id=slo.serviceStream_id
left join Patient pat on pat.id=slo.patient_id
left join VocSocialStatus pvss on pvss.id=pat.socialStatus_id
left join Address2 adr on adr.addressid=pat.address_addressid
left join Mislpu ml on slo.department_id=ml.id
left join VocPigeonHole vph on vph.id=ml.pigeonHole_id
left join Omc_Oksm ok on pat.nationality_id=ok.id
left join MedCase sls on sls.id=slo.parent_id and sls.dtype='HospitalMedCase'
left join VocHospitalizationResult vhr on vhr.id=sls.result_id
where slo.dtype='DepartmentMedCase'
and ${periodCurrentSlo1} 
and (ml.isNoOmc='0' or ml.isNoOmc is null)
and slo.deniedHospitalizating_id is null
${pigeonHoleSql} ${departmentSql} ${serviceStreamSql} 
group by ml.id,ml.name
order by ml.name
      " guid="4a720225-8d94-4b47-bef3-4dbbe79eec74" />
      
    <msh:table cellFunction="true" name="journal_priem" 
     action="stac_everyday_report.do?${paramHref}" idField="1">
      <msh:tableColumn columnName="#" property="sn" />
      <msh:tableColumn columnName="Отделение" property="2" />
      <msh:tableColumn isCalcAmount="true" columnName="Кол-во состоящих" addParam="&dateinfo=dateCurrent" property="3" />
      <msh:tableColumn isCalcAmount="true" columnName="ОМС" property="4" addParam="&dateinfo=dateCurrent&stream=OBLIGATORYINSURANCE" />
      <msh:tableColumn isCalcAmount="true" columnName="внебюдж." property="5" addParam="&dateinfo=dateCurrent&stream=-PRIVATEINSURANCE,OBLIGATORYINSURANCE,OTHER,BUDGET"/>
      <msh:tableColumn isCalcAmount="true" columnName="ДМС" property="6" addParam="&dateinfo=dateCurrent&stream=PRIVATEINSURANCE"/>
      <msh:tableColumn isCalcAmount="true" columnName="без  полиса" property="7" addParam="&dateinfo=dateCurrent&stream=OTHER"/>
      <msh:tableColumn isCalcAmount="true" columnName="Кол-во пост." property="8" addParam="&dateinfo=dateStart"/>
      <msh:tableColumn isCalcAmount="true" columnName="экстр." property="9" addParam="&dateinfo=dateStart&emergency=1"/>
      <msh:tableColumn isCalcAmount="true" columnName="план." property="10" addParam="&dateinfo=dateStart&emergency=0"/>
      <msh:tableColumn isCalcAmount="true" columnName="Кол-во перев. в др.отд" property="11" addParam="&dateinfo=transferDate"/>
      <msh:tableColumn isCalcAmount="true" columnName="Кол-во выбывших" property="12" addParam="&dateinfo=dateFinish"/>
      <msh:tableColumn isCalcAmount="true" columnName="из них умерло" property="13" addParam="&dateinfo=dateFinish&discharge=death"/>
      <msh:tableColumn isCalcAmount="true" columnName="Кол-во опер." property="14" addParam="&dateinfo=operationDate&dtype=SurgicalOperation"/>
      <msh:tableColumn isCalcAmount="true" columnName="экстр." property="15" addParam="&dateinfo=operationDate&dtype=SurgicalOperation&operation=emergency"/>
      <msh:tableColumn isCalcAmount="true" columnName="план." property="16" addParam="&dateinfo=operationDate&dtype=SurgicalOperation&operation=plan"/>
    </msh:table>
    </msh:sectionContent>
    </msh:section>    	   
    	    <%
    	}  else if (typeView!=null && (typeView.equals("2"))) {
    		
    		Date dt = DateFormat.parseDate(date) ;
    		Calendar c = Calendar.getInstance() ;
    		c.setTime(dt) ;
    		c.add(Calendar.DAY_OF_MONTH, -1) ;
    		SimpleDateFormat f=new SimpleDateFormat("dd.MM.yyyy") ;
    		String datePrev=format.format(c.getTime()) ;
    		request.setAttribute("datePrev", datePrev) ;
			request.setAttribute("periodSmo",new StringBuilder() 
				.append(ReportParamUtil.getPeriodByDate(Boolean.FALSE, "medcase.dateStart", "coalesce(medcase.entranceTime,medcase.timeExecute)", datePrev, "15:00")) 
				.toString().replaceAll("medcase", "smo"));
			
    		%>
<msh:section>
    <msh:sectionTitle>
    Обратились за мед.помощью иностранцы ${datePrev} 15:00 - ${param.dateBegin} 14:59
    </msh:sectionTitle>
    <msh:sectionContent>
    <ecom:webQuery name="journal_priem" nameFldSql="journal_priem_sql" nativeSql=" 
   select '&nationality='||vn.id,vn.name
,count(distinct case when smo.dtype='Visit'  and (vwf.isNoDiagnosis='0' or vwf.isNoDiagnosis is null) then smo.id else null end) as cntVisitMain
,count(distinct case when smo.dtype='Visit' and smo.dateStart=to_date('${param.dateBegin}','dd.mm.yyyy') and smo.timeExecute between cast('07:00' as time) and cast('14:59' as time) and (vwf.isNoDiagnosis='0' or vwf.isNoDiagnosis is null) then smo.id else null end) as cntVisitMain2
,count(distinct case when smo.dtype='Visit' and vwf.isNoDiagnosis='1' then smo.id else null end) as cntVisitDiag
,count(distinct case when smo.dtype='Visit' and smo.dateStart=to_date('${param.dateBegin}','dd.mm.yyyy') and smo.timeExecute between cast('07:00' as time) and cast('14:59' as time) and vwf.isNoDiagnosis='1' then smo.id else null end) as cntVisitDiag2
,count(distinct case when smo.dtype='HospitalMedCase' and smo.deniedHospitalizating_id is null then smo.id else null end) as cntHosp
,count(distinct case when smo.dtype='HospitalMedCase' and smo.dateStart=to_date('${param.dateBegin}','dd.mm.yyyy') and smo.entranceTime between cast('07:00' as time) and cast('14:59' as time) and smo.deniedHospitalizating_id is null then smo.id else null end) as cntHosp2
,count(distinct case when smo.dtype='HospitalMedCase' and smo.deniedHospitalizating_id is not null then smo.id else null end) as cntDenied
,count(distinct case when smo.dtype='HospitalMedCase' and smo.dateStart=to_date('${param.dateBegin}','dd.mm.yyyy') and smo.entranceTime between cast('07:00' as time) and cast('14:59' as time) and smo.deniedHospitalizating_id is not null then smo.id else null end) as cntDenied2

from medcase smo
left join WorkFunction wf on wf.id=smo.workFunctionExecute_id
left join VocWorkFunction vwf on vwf.id=wf.workFunction_id
left join VocServiceStream vss on vss.id=smo.serviceStream_id
left join Patient pat on pat.id=smo.patient_id
left join Omc_Oksm vn on vn.id=pat.nationality_id
left join VocSocialStatus pvss on pvss.id=pat.socialStatus_id
left join Omc_Oksm ok on pat.nationality_id=ok.id
where smo.dtype in('HospitalMedCase','Visit')
and ${periodSmo} 

group by vn.id,vn.name
order by vn.name
      " guid="4a720225-8d94-4b47-bef3-4dbbe79eec74" />
      
    <msh:table cellFunction="true" name="journal_priem" 
     action="stac_everyday_report.do?${paramHref}" idField="1">
      <msh:tableColumn columnName="#" property="sn" />
      <msh:tableColumn columnName="Гражданство" property="2" />
      <msh:tableColumn isCalcAmount="true" columnName="Кол-во амб. посещ." property="3" addParam="&dtype=Visit&isdiag=0&dateinfo=dateStart&stream=OBLIGATORYINSURANCE" />
      <msh:tableColumn isCalcAmount="true" columnName="Кол-во амб. посещ. 07:00-14:59" property="4" addParam="&hours=07:00-14:59&dtype=Visit&isdiag=0&dateinfo=dateStart&stream=OBLIGATORYINSURANCE" />
      <msh:tableColumn isCalcAmount="true" columnName="Кол-во диаг. посещ." property="5" addParam="&dtype=Visit&isdiag=1&dateinfo=dateCurrent&stream=-PRIVATEINSURANCE,OBLIGATORYINSURANCE,OTHER,BUDGET"/>
      <msh:tableColumn isCalcAmount="true" columnName="Кол-во диаг. посещ. 07:00-14:59" property="6" addParam="&hours=07:00-14:59&dtype=Visit&isdiag=1&dateinfo=dateCurrent&stream=-PRIVATEINSURANCE,OBLIGATORYINSURANCE,OTHER,BUDGET"/>
      <msh:tableColumn isCalcAmount="true" columnName="Кол-во госпит." property="7" addParam="&dateinfo=dateStart&stream=PRIVATEINSURANCE"/>
      <msh:tableColumn isCalcAmount="true" columnName="Кол-во госпит. 07:00-14:59" property="8" addParam="&hours=07:00-14:59&dateinfo=dateStart&stream=PRIVATEINSURANCE"/>
      <msh:tableColumn isCalcAmount="true" columnName="Кол-во отказов от госп." property="9" addParam="&dateinfo=dateStart&stream=OTHER"/>
      <msh:tableColumn isCalcAmount="true" columnName="Кол-во отказов от госп. 07:00-14:59" property="10" addParam="&hours=07:00-14:59&dateinfo=dateCurrent&stream=OTHER"/>
    </msh:table>
    </msh:sectionContent>
    </msh:section>    	       		<%
    		
    	}else {
    		
    		
    
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

