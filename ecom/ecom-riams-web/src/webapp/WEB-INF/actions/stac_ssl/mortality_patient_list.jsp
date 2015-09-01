<%@page import="ru.ecom.web.util.ActionUtil"%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://struts.apache.org/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://www.nuzmsh.ru/tags/msh" prefix="msh" %>
<%@ taglib uri="http://www.ecom-ast.ru/tags/ecom" prefix="ecom" %>
<%@ taglib tagdir="/WEB-INF/tags" prefix="tags" %>

<tiles:insert page="/WEB-INF/tiles/main${param.short}Layout.jsp" flush="true" >
	<tiles:put name="style" type="string">
	
	</tiles:put>

    <tiles:put name='title' type='string'>
        <msh:title mainMenu="Journals">Анализ летальности в отделениях</msh:title>
    </tiles:put>

    <tiles:put name='side' type='string'>
        <tags:stac_journal currentAction="stac_journalByDeathPatient"/>
    </tiles:put>
    
  <tiles:put name="body" type="string">
    <msh:form action="/mis_mortality_report.do" defaultField="dateBegin" disableFormDataConfirm="true" method="GET" guid="d7b31bc2-38f0-42cc-8d6d-19395273168f">
     <%if (request.getParameter("short")==null ||request.getParameter("short").equals(""))  {%>
    <msh:panel guid="6ae283c8-7035-450a-8eb4-6f0f7da8a8ff">
    <input type="hidden" value="printDeathList" name="m">
    <input type="hidden" value="HospitalReportService" name="s">
<%--       <msh:row>
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
        </msh:row> --%>
     <%--    <msh:row styleId="noswod">
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
        </msh:row> --%>
      
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
    <%--   <msh:row guid="7d80be13-710c-46b8-8503-ce0413686b69">
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
        </msh:row> --%>
      <msh:row>
	        <td class="label" title="Просмотр данных (typeView)" colspan="1"><label for="typeViewName" id="typeViewLabel">Отобразить:</label></td>
	        <td onclick="this.childNodes[1].checked='checked';">
	        	<input type="radio" name="typeView" value="1">  реестр
	        </td>
	        <td onclick="this.childNodes[1].checked='checked';">
	        	<input type="radio" name="typeView" value="2"  >  свод по отделениям без учета отд., которые не входят в ОМС 
	        </td>
	        <td onclick="this.childNodes[1].checked='checked';">
	        	<input type="radio" name="typeView" value="3"  >  свод по типам госпитализации (экстренно/планово) 
	        </td>
	        <td onclick="this.childNodes[1].checked='checked';">
	        	<input type="radio" name="typeView" value="4"  >  свод по дате смерти 
	        </td>
        </msh:row> 
         <msh:row>
        	<msh:autoComplete property="department" fieldColSpan="4" horizontalFill="true" label="Отделение" vocName="lpu"/>
        </msh:row>
        <msh:row>
	        <msh:textField property="dateBegin" label="Период с" guid="8d7ef035-1273-4839-a4d8-1551c623caf1" />
	        <msh:textField property="dateEnd" label="по" guid="f54568f6-b5b8-4d48-a045-ba7b9f875245" />
		<td>
            <input type="submit" onclick="find()" value="Найти" />
           <!--  <input type="submit" onclick="print()" value="Печать" /> -->
          </td>
         </msh:row>
        
    </msh:panel>
    <%} %>
    </msh:form>
    
    <%
    String date = (String)request.getParameter("dateBegin") ;
    String result = (String)request.getParameter("result") ;
    String view = (String)request.getAttribute("typeView") ;
    String department=(String) request.getAttribute("department");
    
    if (date!=null && !date.equals(""))  {
    //	ActionUtil.setParameterFilterSql("result","vhr.id", request) ;
    	String dateEnd = (String)request.getParameter("dateEnd") ;
    	if (dateEnd==null||dateEnd.equals("")) {
    		dateEnd=date ;
    	}
    	request.setAttribute("dateEnd", dateEnd) ;
    	request.setAttribute("isReportBase", ActionUtil.isReportBase(date, dateEnd,request));
    	String dep = (String) request.getParameter("department") ; 
    	String cellF = (String) request.getParameter("addCell");
    	if (cellF!=null) {
    		if (cellF.equals("dead")) {
    			request.setAttribute("cellAdd", "and vhr.omccode='11'");
    		} else if (cellF.equals("planDead")) {
    			request.setAttribute("cellAdd", "and (hmc.emergency is null or hmc.emergency='0') and vhr.omccode='11'") ;
    		} else if (cellF.equals("emmergDead")) {
    			request.setAttribute("cellAdd", "and hmc.emergency='1' and vhr.omccode='11'") ;
    		} else if (cellF.equals("dead6")) {
    			request.setAttribute("cellAdd","and vhr.omccode='11' and (hmc.datestart=hmc.datefinish and"+ 
    					" (hmc.datestart=hmc.datefinish and (hmc.dischargetime-hmc.entrancetime)<=cast('06:00:00' as time))  "+
    					" or (hmc.datefinish-hmc.datestart=1 and hmc.entrancetime>hmc.dischargetime and ((cast('24:00:00' as time) -"+ 
    					" hmc.entrancetime))+hmc.dischargetime<=cast('06:00:00' as time) and ((cast('24:00:00' as time)"+ 
    					" - hmc.entrancetime)<=cast('06:00:00' as time))))");
    		} else if (cellF.equals("dead624")){
    			request.setAttribute("cellAdd","and vhr.omccode='11' and ((hmc.datestart=hmc.datefinish and (hmc.dischargetime-hmc.entrancetime)>cast('06:00:00' as time))  or (hmc.datefinish-hmc.datestart=1 and hmc.dischargetime<=hmc.entrancetime and (((cast('24:00:00' as time) - hmc.entrancetime))+hmc.dischargetime>cast('06:00:00' as time))or hmc.dischargetime=hmc.entrancetime))");
    			
    		} else if (cellF.equals("deadUp5")){
    			request.setAttribute("cellAdd","and vhr.omccode='11' and ((hmc.datefinish-hmc.datestart=1 and hmc.dischargetime>hmc.entrancetime) or (hmc.datefinish-hmc.datestart between 2 and 4) or (hmc.datefinish-hmc.datestart =5 and hmc.dischargetime<=hmc.entrancetime))");
    		} else if (cellF.equals("dead5More")) {
    			request.setAttribute("cellAdd", "and vhr.omccode='11' and (hmc.datefinish-hmc.datestart>5 or (hmc.datefinish-hmc.datestart=5 and hmc.dischargetime>hmc.entrancetime))");
    		}
    		
    	}
    	if (dep!=null && !dep.equals("") && !dep.equals("0")) {
    		request.setAttribute("depIsNoOmc", " and (dmc.department_id='"+dep+"' or d.isNoOmc='1' and pdmc.department_id='"+dep+"')");
    		request.setAttribute("dep", " and dmc.department_id='"+dep+"'");
    		request.setAttribute("dep1", " and hmc1.department_id='"+dep+"'");
    	} else {
    		request.setAttribute("depIsNoOmc", "") ;
    		request.setAttribute("dep", "") ;
    		request.setAttribute("dep1", "") ;
    	}    	
    	if (view!=null && view.equals("2")) { 
    %>
    <msh:section title="Свод по отделениям без учета отд., которые не входят в ОМС">
    <msh:sectionContent>
    <ecom:webQuery isReportBase="${isReportBase}"  nameFldSql="journal_list_swod_sql" name="journal_list_swod" nativeSql="
	select '&department='||case when d.isNoOmc='1' then pd.id else d.id end as id 
	,case when d.isnoomc='1' then pd.id else d.id end as f1_lpuId
	,case when d.isnoomc='1' then pd.name else d.name end  as f2_lpuName
	,count(case when vhr.omccode='11' then 1 else null end) as f3_deadPat
	,count (pat.id) as f4_allPat
	,round(cast((count(case when vhr.omccode='11' then 1 else null end)*100)as numeric(9,2))/count (pat.id) ,2) as f5_percentOtdel
	,cast (cast((count(case when vhr.omccode='11' then 1 else null end)*100)as numeric(9,2))/(select count (smo2.patient_id) from medcase smo2 where smo2.dtype='HospitalMedCase' and smo2.datefinish between to_date('${param.dateBegin}','dd.MM.yyyy') and to_date('${dateEnd}','dd.MM.yyyy') and smo2.datefinish is not null  )as numeric(9,2)) as f6_percentAll
	
	from medcase hmc 
	left join vochospitalizationresult vhr on vhr.id=hmc.result_id
	left join patient pat on pat.id=hmc.patient_id
	left join medcase dmc on dmc.parent_id=hmc.id and dmc.dtype='DepartmentMedCase' and dmc.datefinish is not null
	left join medcase pdmc on pdmc.id=dmc.prevmedcase_id
	left join mislpu d on d.id=dmc.department_id
	left join mislpu pd on pd.id=pdmc.department_id
	
	where hmc.dtype='HospitalMedCase' and hmc.deniedhospitalizating_id is null and ${dateT} between to_date('${param.dateBegin}','dd.MM.yyyy') and to_date('${dateEnd}','dd.MM.yyyy')
	and hmc.datefinish is not null and hmc.dischargetime is not null
    	${depIsNoOmc}
	group by case when (d.isnoomc='1') then pd.id else d.id end , case when (d.isnoomc='1') then pd.name else d.name end 
	
	order by f5_percentOtdel desc , f2_lpuName
   " guid="4a720225-8d94-4b47-bef3-4dbbe79eec74" />
        <msh:table name="journal_list_swod" cellFunction="true"
         action="mis_mortality_report.do?short=Short&typeView=1&dateBegin=${param.dateBegin}&dateEnd=${dateEnd}" idField="1" noDataMessage="Не найдено" >
            <msh:tableColumn columnName="#" property="sn"/>
            <msh:tableColumn columnName="Отделение" property="3"/>
            <msh:tableColumn isCalcAmount="true" columnName="Число пролеченных" property="5"/>
            <msh:tableColumn isCalcAmount="true" columnName="Число летальных исходов" property="4" addParam="&addCell=dead"/>
            <msh:tableColumn columnName="% летальных исходов по отделению" property="6"/>
            <msh:tableColumn columnName="% летальных исходов всего" property="7"/>
        </msh:table>
    </msh:sectionContent>
    </msh:section>
    <%} 
    	if (view != null && view.equals("1") ) { 
    %>
    <msh:section title="Реестр пациентов">
    <msh:sectionContent>
        ${isReportBase}<ecom:webQuery isReportBase="${isReportBase}" nameFldSql="journal_surOperation_sql"  name="journal_surOperation" nativeSql="
    select
    hmc.id as slsid,hmc.id as slsid2
    ,ss.code as sscode
    ,pat.lastname||' '||pat.firstname||' '||pat.middlename as fio
    ,cast(to_char(${dateT},'yyyy') as int)
    -cast(to_char(pat.birthday,'yyyy') as int)
    +(case when (cast(to_char(${dateT}, 'mm') as int)
    -cast(to_char(pat.birthday, 'mm') as int)
    +(case when (cast(to_char(${dateT},'dd') as int)
    - cast(to_char(pat.birthday,'dd') as int)<0) then -1 else 0 end) <0)
    then -1 else 0 end) as age
    ,to_char(hmc.dateStart,'dd.mm.yyyy') as slsdateStart
    ,to_char(hmc.dateFinish,'dd.mm.yyyy') as slsdateFinish
    ,case when hmc.emergency='1' then 'Да' else null end as emer
    from medcase hmc 
     left join StatisticStub ss on ss.id=hmc.statisticStub_id
	left join vochospitalizationresult vhr on vhr.id=hmc.result_id
	left join patient pat on pat.id=hmc.patient_id
	left join medcase dmc on dmc.parent_id=hmc.id and dmc.dtype='DepartmentMedCase' and dmc.datefinish is not null
	left join medcase pdmc on pdmc.id=dmc.prevmedcase_id
	left join mislpu d on d.id=dmc.department_id
	left join mislpu pd on pd.id=pdmc.department_id
	where hmc.dtype='HospitalMedCase' and hmc.deniedhospitalizating_id is null and ${dateT} between to_date('${param.dateBegin}','dd.MM.yyyy') and to_date('${dateEnd}','dd.MM.yyyy')
	and hmc.datefinish is not null 
    	${depIsNoOmc}
    ${cellAdd}
    order by pat.lastname,pat.firstname,pat.middlename"/>
        <msh:table name="journal_surOperation"
        viewUrl="entityShortView-stac_ssl.do"
         action="entityView-stac_ssl.do" idField="1">
         
          <msh:tableColumn columnName="##" property="sn" />
          <msh:tableColumn columnName="№стат. карт" property="3" />
          <msh:tableColumn columnName="ФИО пациента" property="4" />
          <msh:tableColumn columnName="Возраст" property="5" />
          <msh:tableColumn columnName="Дата поступления" property="6"/>
          <msh:tableColumn columnName="Дата выписки" property="7"/>
          <msh:tableColumn columnName="Экстренно?" property="8"/>
		  </msh:table>
        </msh:sectionContent>
        </msh:section>  
 
    <% } else if (view!=null&&view.equals("3")) {
    	%>
    	 <msh:section title="Свод по типам госпитализации">
    <msh:sectionContent>
        ${isReportBase}<ecom:webQuery isReportBase="${isReportBase}" nameFldSql="journal_hospType_sql"  name="journal_hospType" nativeSql="
    select count(hmc.id) as f1_cntAll, count(case when vhr.omccode='11' then 1 else null end) as f2_allDead
,count(case when vhr.omccode='11' and hmc.emergency='1' then 1 else null end) as f3_emmerDead
,count(case when vhr.omccode='11' and (hmc.emergency='0' or hmc.emergency is null) then 1 else null end) as f4_notEmmerDead
,case when count(case when vhr.omccode='11' then 1 else null end)>0 then round(count(case when vhr.omccode='11' and hmc.emergency='1' then 1 else null end) * 100 /cast(count(case when vhr.omccode='11' then 1 else null end) as numeric(9,2)),2) else 0 end as f5_percEmmergDead
,case when count(case when vhr.omccode='11' then 1 else null end)>0 then round(count(case when vhr.omccode='11' and (hmc.emergency='0' or hmc.emergency is null) then 1 else null end) * 100 /cast(count(case when vhr.omccode='11' then 1 else null end) as numeric(9,2)),2) else 0 end as f6_percPlanDead
,cast('&department=${param.department}' as varchar)
from medcase hmc 
left join vochospitalizationresult vhr on vhr.id=hmc.result_id
	left join patient pat on pat.id=hmc.patient_id
	left join medcase dmc on dmc.parent_id=hmc.id and dmc.dtype='DepartmentMedCase' and dmc.datefinish is not null
	left join medcase pdmc on pdmc.id=dmc.prevmedcase_id
	left join mislpu d on d.id=dmc.department_id
	left join mislpu pd on pd.id=pdmc.department_id
where hmc.dtype='HospitalMedCase' and hmc.deniedhospitalizating_id is null and ${dateT} between to_date('${param.dateBegin}','dd.MM.yyyy') and to_date('${dateEnd}','dd.MM.yyyy') and hmc.datefinish is not null and hmc.dischargetime is not null 

    	${depIsNoOmc}
    
"/>
        <msh:table name="journal_hospType" cellFunction="true"
          action="mis_mortality_report.do?short=Short&typeView=1&dateBegin=${param.dateBegin}&dateEnd=${dateEnd}"  idField="7" >
          <msh:tableColumn columnName="Всего госпитализаций" property="1" />
          <msh:tableColumn columnName="Умершие" property="2" addParam="&addCell=dead" />
          <msh:tableColumn columnName="Экстренная госп/умершие" property="3" addParam="&addCell=emmergDead"/>
          <msh:tableColumn columnName="Плановая госп/умершие" property="4" addParam="&addCell=planDead"/>
          <msh:tableColumn columnName="Экстренная госп/ % умерших" property="5"/>
          <msh:tableColumn columnName="Плановая госп/% умерших" property="6"/>

        </msh:table>
        </msh:sectionContent>
        </msh:section>  
    	<%  } else if (view!=null&&view.equals("4")) {
    		%>
 <msh:section title="Свод по срокам смерти с момента госпитализации">
    <msh:sectionContent>
    <ecom:webQuery isReportBase="${isReportBase}"  nameFldSql="journal_list_swod_sql" name="journal_list_swod" nativeSql="
select 
count (case when (hmc.datestart=hmc.datefinish and (hmc.dischargetime-hmc.entrancetime)<=cast('06:00:00' as time))  or (hmc.datefinish-hmc.datestart=1 and hmc.entrancetime>hmc.dischargetime and ((cast('24:00:00' as time) - hmc.entrancetime))+hmc.dischargetime<=cast('06:00:00' as time) and ((cast('24:00:00' as time) - hmc.entrancetime)<=cast('06:00:00' as time))  ) then 1 else null end) as cnt1_h_0_6 
,count (case when (hmc.datestart=hmc.datefinish and (hmc.dischargetime-hmc.entrancetime)>cast('06:00:00' as time))  or (hmc.datefinish-hmc.datestart=1 and hmc.dischargetime<=hmc.entrancetime and (((cast('24:00:00' as time) - hmc.entrancetime))+hmc.dischargetime>cast('06:00:00' as time))or hmc.dischargetime=hmc.entrancetime) then 1 else null end) as cnt2_h_6_24 
,count(case when (hmc.datefinish-hmc.datestart=1 and hmc.dischargetime>hmc.entrancetime) or (hmc.datefinish-hmc.datestart between 2 and 4) or (hmc.datefinish-hmc.datestart =5 and hmc.dischargetime<=hmc.entrancetime) then 1 else null end) as cnt3_up5 
,count (case when hmc.datefinish-hmc.datestart>5 or (hmc.datefinish-hmc.datestart=5 and hmc.dischargetime>hmc.entrancetime) then 1 else null end) as cnt4_5More
,cast('&department=${param.department}' as varchar) as cntId
from medcase hmc 
	left join vochospitalizationresult vhr on vhr.id=hmc.result_id
	left join patient pat on pat.id=hmc.patient_id
	left join medcase dmc on dmc.parent_id=hmc.id and dmc.dtype='DepartmentMedCase' and dmc.datefinish is not null
	left join medcase pdmc on pdmc.id=dmc.prevmedcase_id
	left join mislpu d on d.id=dmc.department_id
	left join mislpu pd on pd.id=pdmc.department_id
	
	where hmc.dtype='HospitalMedCase' and ${dateT} between to_date('${param.dateBegin}','dd.MM.yyyy') and to_date('${dateEnd}','dd.MM.yyyy')
	and hmc.datefinish is not null and hmc.dischargetime is not null
	and hmc.deniedhospitalizating_id is null
	and vhr.omccode='11'	 ${depIsNoOmc}

   " guid="4a720225-8d94-4b47-bef3-4dbbe79eec74" />
        <msh:table name="journal_list_swod" cellFunction="true"
         action="mis_mortality_report.do?short=Short&typeView=1&dateBegin=${param.dateBegin}&dateEnd=${dateEnd}" idField="5" noDataMessage="Не найдено" >
            <msh:tableColumn columnName="#" property="sn"/>
            <msh:tableColumn columnName="Умерло в первые 6 часов" property="1" addParam="&addCell=dead6"/>
            <msh:tableColumn columnName="Умерло с 6 до 24 часов" property="2" addParam="&addCell=dead624"/>
            <msh:tableColumn  columnName="Умерло в первые 5 суток" property="3" addParam="&addCell=deadUp5"/>
            <msh:tableColumn columnName="Умерло более 5 суток" property="4" addParam="&addCell=dead5More"/>
       </msh:table>
    </msh:sectionContent>
    </msh:section>    		
    		<%
    	}
    	} else {%>
    	<i>Выберите параметры и нажмите найти </i>
    	<% }   %>
    <script type='text/javascript'>
    
     checkFieldUpdate('typeDate','${typeDate}',2) ;
    // checkFieldUpdate('typePatient','${typePatient}',3) ;
    checkFieldUpdate('typeView','${typeView}',4); 
     
     
   
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
    	frm.action='mis_mortality_report.do' ;
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

