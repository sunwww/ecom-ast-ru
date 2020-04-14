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
    <msh:form action="/mis_mortality_report.do" defaultField="dateBegin" disableFormDataConfirm="true" method="GET">
     <%if (request.getParameter("short")==null ||request.getParameter("short").equals(""))  {%>
    <msh:panel>
    <input type="hidden" value="printDeathList" name="m">
    <input type="hidden" value="HospitalReportService" name="s">
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
        <td class="label" title="Поиск по пациентам (typeNewborn)" colspan="1"><label for="typeNewbornName" id="typeNewbornLabel">Пациенты:</label></td>
        <td onclick="this.childNodes[1].checked='checked';">
	        	<input type="radio" name="typeNewborn" value="1"  >  Только новорожденные 
	        </td>
	        <td onclick="this.childNodes[1].checked='checked';">
	        	<input type="radio" name="typeNewborn" value="2"  >  Без учета новорожденных
	        </td>
	        <td onclick="this.childNodes[1].checked='checked';">
	        	<input type="radio" name="typeNewborn" value="3"  >  Все
	        </td>
	      </msh:row>
    <%--   <msh:row>
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
	        </msh:row>
	        <msh:row><td></td>
	        <td onclick="this.childNodes[1].checked='checked';">
	        	<input type="radio" name="typeView" value="3"  >  свод по типам госпитализации (экстренно/планово) 
	        </td>
	        <td onclick="this.childNodes[1].checked='checked';">
	        	<input type="radio" name="typeView" value="4"  >  свод по дате смерти 
	        </td>
        </msh:row> 
	        <msh:row><td></td>
	        <td onclick="this.childNodes[1].checked='checked';">
	        	<input type="radio" name="typeView" value="5"  >  Свод по возрастным группам 
	        </td>
	        <td onclick="this.childNodes[1].checked='checked';">
	        	<input type="radio" name="typeView" value="6"  >  Летальность иногородних/иностранных граждан
	        </td>
        </msh:row> 
         <msh:row>
        	<msh:autoComplete property="department" fieldColSpan="4" horizontalFill="true" label="Отделение" vocName="lpu"/>
        </msh:row>
        <msh:row>
        	<msh:autoComplete property="bedSubType" fieldColSpan="4"
        	label="Тип коек" horizontalFill="true" vocName="vocBedSubType"/>
        </msh:row>
        <msh:row>
	        <msh:textField property="dateBegin" label="Период с" />
	        <msh:textField property="dateEnd" label="по" />
		<td>
            <input type="submit" onclick="find()" value="Найти" />
          </td>
         </msh:row>
        
    </msh:panel>
    <%} %>
    </msh:form>
    
    <%
    String date = request.getParameter("dateBegin") ;
    String view = (String)request.getAttribute("typeView") ;
    String dateT = (String) request.getAttribute("dateT");
    String sex =  request.getParameter("sex");
    
    
    if (date!=null && !date.equals(""))  {
    	String dateEnd = request.getParameter("dateEnd") ;
    	if (dateEnd==null||dateEnd.equals("")) {
    		dateEnd=date ;
    	}
    	if (sex!=null&&!sex.equals("")) {
    			sex="and pat.sex_id="+sex;    		
    	} else {sex="";}
    	request.setAttribute("dateEnd", dateEnd) ;
    	request.setAttribute("isReportBase", ActionUtil.isReportBase(date, dateEnd,request));
    	String dep = request.getParameter("department") ;
    	String cellF = request.getParameter("addCell");
    	if (cellF!=null) {
    		if (cellF.equals("dead")) {
    			request.setAttribute("cellAdd", ") "+sex);
    		} else if (cellF.equals("notDead")) {
    			request.setAttribute("cellAdd", " or vhr.omccode!='11') "+sex) ;
    		} else if (cellF.equals("planDead")) {
    			request.setAttribute("cellAdd", ") and (hmc.emergency is null or hmc.emergency='0') "+sex) ;
    		} else if (cellF.equals("emmergDead")) {
    			request.setAttribute("cellAdd", ") and hmc.emergency='1' "+sex) ;
    		} else if (cellF.equals("dead6")) {
    			request.setAttribute("cellAdd",") and (hmc.datestart=hmc.datefinish and"+ 
    					" (hmc.datestart=hmc.datefinish and (hmc.dischargetime-hmc.entrancetime)<=cast('06:00:00' as time))  "+
    					" or (hmc.datefinish-hmc.datestart=1 and hmc.entrancetime>hmc.dischargetime and ((cast('24:00:00' as time) -"+ 
    					" hmc.entrancetime))+hmc.dischargetime<=cast('06:00:00' as time) and ((cast('24:00:00' as time)"+ 
    					" - hmc.entrancetime)<=cast('06:00:00' as time))))"+sex);
    		} else if (cellF.equals("dead624")){
    			request.setAttribute("cellAdd",")  and ((hmc.datestart=hmc.datefinish and (hmc.dischargetime-hmc.entrancetime)>cast('06:00:00' as time))  or (hmc.datefinish-hmc.datestart=1 and hmc.dischargetime<=hmc.entrancetime and (((cast('24:00:00' as time) - hmc.entrancetime))+hmc.dischargetime>cast('06:00:00' as time))or hmc.dischargetime=hmc.entrancetime))"+sex);
    		} else if (cellF.equals("deadUp5")){
    			request.setAttribute("cellAdd",") and ((hmc.datefinish-hmc.datestart=1 and hmc.dischargetime>hmc.entrancetime) or (hmc.datefinish-hmc.datestart between 2 and 4) or (hmc.datefinish-hmc.datestart =5 and hmc.dischargetime<=hmc.entrancetime))"+sex);
    		} else if (cellF.equals("dead5More")) {
    			request.setAttribute("cellAdd", ") and (hmc.datefinish-hmc.datestart>5 or (hmc.datefinish-hmc.datestart=5 and hmc.dischargetime>hmc.entrancetime))"+sex);
    		} else if (cellF.equals("age1")) {
    			request.setAttribute ("cellAdd",") and (cast(to_char("+dateT+",'yyyy') as int)-cast(to_char(pat.birthday,'yyyy') as int) "+
    					" +(case when (cast(to_char("+dateT+", 'mm') as int)-cast(to_char(pat.birthday, 'mm') as int)"+
    					" +(case when (cast(to_char("+dateT+",'dd') as int) - cast(to_char(pat.birthday,'dd') as int)<0)"+
    					" then -1 else 0 end)<0) then -1 else 0 end)<18)"+sex);
    		}else if (cellF.equals("age2")) {
    			request.setAttribute ("cellAdd",") and (cast(to_char("+dateT+",'yyyy') as int)-cast(to_char(pat.birthday,'yyyy') as int) "+
    					" +(case when (cast(to_char("+dateT+", 'mm') as int)-cast(to_char(pat.birthday, 'mm') as int)"+
    					" +(case when (cast(to_char("+dateT+",'dd') as int) - cast(to_char(pat.birthday,'dd') as int)<0)"+
    					" then -1 else 0 end)<0) then -1 else 0 end) between 18 and 45)"+sex);
    		}else if (cellF.equals("age3")) {
    			request.setAttribute ("cellAdd",") and (cast(to_char("+dateT+",'yyyy') as int)-cast(to_char(pat.birthday,'yyyy') as int) "+
    					" +(case when (cast(to_char("+dateT+", 'mm') as int)-cast(to_char(pat.birthday, 'mm') as int)"+
    					" +(case when (cast(to_char("+dateT+",'dd') as int) - cast(to_char(pat.birthday,'dd') as int)<0)"+
    					" then -1 else 0 end)<0) then -1 else 0 end) between 46 and 60)"+sex);
    		}else if (cellF.equals("age4")) {
    			request.setAttribute ("cellAdd",") and (cast(to_char("+dateT+",'yyyy') as int)-cast(to_char(pat.birthday,'yyyy') as int) "+
    					" +(case when (cast(to_char("+dateT+", 'mm') as int)-cast(to_char(pat.birthday, 'mm') as int)"+
    					" +(case when (cast(to_char("+dateT+",'dd') as int) - cast(to_char(pat.birthday,'dd') as int)<0)"+
    					" then -1 else 0 end)<0) then -1 else 0 end) between 61 and 75)"+sex);
    		}else if (cellF.equals("age5")) {
    			request.setAttribute ("cellAdd",") and (cast(to_char("+dateT+",'yyyy') as int)-cast(to_char(pat.birthday,'yyyy') as int) "+
    					" +(case when (cast(to_char("+dateT+", 'mm') as int)-cast(to_char(pat.birthday, 'mm') as int)"+
    					" +(case when (cast(to_char("+dateT+",'dd') as int) - cast(to_char(pat.birthday,'dd') as int)<0)"+
    					" then -1 else 0 end)<0) then -1 else 0 end)>75)"+sex);
    				
    		} else if (cellF.equals("allAge")) {
    			request.setAttribute("cellAdd", ")"+sex);
    		} else if (cellF.equals("foreign")) {
    			request.setAttribute("cellAdd", ") and (ok.voc_code!='643' or adr.kladr not like '30%')"+sex);
    		} else if (cellF.equals("inostr")) {
    			request.setAttribute("cellAdd", ") and ok.voc_code!='643'"+sex);
    		} else if (cellF.equals("inogor")) {
    			request.setAttribute("cellAdd", ") and adr.kladr not like '30%' and ok.voc_code='643' "+sex);
    		} 
    	} else {
    		request.setAttribute("cellAdd",")"+sex);
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
    	String bedSubType = request.getParameter("bedSubType");
    	if (bedSubType!=null&&!bedSubType.equals("")&&!bedSubType.equals("0")) {
    		request.setAttribute("bedSubTypeSql", " and vbst.id='"+bedSubType+"'");
    	}
    	if ("2".equals(view)) {
    %>
    <msh:section title="Свод по отделениям без учета отд., которые не входят в ОМС">
    <msh:sectionContent>
    <ecom:webQuery isReportBase="${isReportBase}" nameFldSql="journal_list_swod_sql" name="journal_list_swod" nativeSql="
	select '&department='||case when d.isNoOmc='1' then pd.id else d.id end as id 
	,case when d.isnoomc='1' then pd.id else d.id end as f1_lpuId
	,case when d.isnoomc='1' then pd.name else d.name end  as f2_lpuName
	,count(case when vhr.omccode='11' then 1 else null end) as f3_deadPat
	,count (pat.id) as f4_allPat
	,round(cast((count(case when vhr.omccode='11' then 1 else null end)*100)as numeric(9,2))/count (pat.id) ,2) as f5_percentOtdel
	,cast (cast((count(case when vhr.omccode='11' then 1 else null end)*100)as numeric(9,2))/(select case when count (smo2.patient_id)>0 then count (smo2.patient_id) else 1 end from medcase smo2 left join vochospitalizationresult vhr2 on vhr2.id=smo2.result_id where smo2.dtype='HospitalMedCase' and vhr2.omccode='11' and smo2.datefinish between to_date('${param.dateBegin}','dd.MM.yyyy') and to_date('${dateEnd}','dd.MM.yyyy') and smo2.datefinish is not null  )as numeric(9,2)) as f6_percentAll
	,cast (cast((count(case when vhr.omccode='11' then 1 else null end)*100)as numeric(9,2))/(select case when count (smo2.patient_id)>0 then count (smo2.patient_id) else 1 end from medcase smo2 left join vochospitalizationresult vhr2 on vhr2.id=smo2.result_id where smo2.dtype='HospitalMedCase' and vhr2.omccode='11' and smo2.datefinish between to_date('${param.dateBegin}','dd.MM.yyyy') and to_date('${dateEnd}','dd.MM.yyyy') and smo2.datefinish is not null  )as numeric(9,2)) as f6_percentAll
	,count(distinct case when dc.isAutopsy='1' then hmc.id else null end) as f7_autopsy
,count(distinct case when dc.categoryDifference_id is not null or dc.latrogeny_id is not null then hmc.id else null end) as f8_cntRash 
,case when count(distinct case when dc.isAutopsy='1' then hmc.id else null end)=0 
then '0' 
--else (cast(((count(distinct case when dc.categoryDifference_id is not null or dc.latrogeny_id is not null then hmc.id else null end))/(count(distinct case when dc.isAutopsy='1' then hmc.id else null end)))*100 as numeric(9,2)) ) end as f9_procent
else cast(((cast((count(distinct case when dc.categoryDifference_id is not null or dc.latrogeny_id is not null then hmc.id else null end))as float) / cast(count(distinct case when dc.isAutopsy='1' then hmc.id else null end) as float))*100) as numeric(9,2))  end

	from medcase hmc 
	left join DeathCase dc on dc.medCase_id=hmc.id 
	left join vochospitalizationresult vhr on vhr.id=hmc.result_id
	left join patient pat on pat.id=hmc.patient_id
	left join medcase dmc on dmc.parent_id=hmc.id and dmc.dtype='DepartmentMedCase' and dmc.datefinish is not null
	left join medcase pdmc on pdmc.id=dmc.prevmedcase_id
	left join mislpu d on d.id=dmc.department_id
	left join mislpu pd on pd.id=pdmc.department_id
	left join bedfund bf on bf.id=dmc.bedfund_id
	left join vocbedsubtype vbst on vbst.id=bf.bedsubtype_id
	
	where hmc.dtype='HospitalMedCase' and hmc.deniedhospitalizating_id is null and ${dateT} between to_date('${param.dateBegin}','dd.MM.yyyy') and to_date('${dateEnd}','dd.MM.yyyy')
	and hmc.datefinish is not null and hmc.dischargetime is not null
    	${depIsNoOmc} ${addNewborn} ${bedSubTypeSql}
	group by case when (d.isnoomc='1') then pd.id else d.id end , case when (d.isnoomc='1') then pd.name else d.name end 
	
	order by f5_percentOtdel desc , f2_lpuName
   " />
        <msh:table name="journal_list_swod" cellFunction="true" printToExcelButton="Сохранить в excel"
         action="mis_mortality_report.do?short=Short&typeView=1&dateBegin=${param.dateBegin}&dateEnd=${dateEnd}" idField="1" noDataMessage="Не найдено" >
            <msh:tableColumn columnName="#" property="sn"/>
            <msh:tableColumn columnName="Отделение" property="3" />
            <msh:tableColumn isCalcAmount="true" columnName="Число пролеченных" property="5" addParam="&addCell=notDead"/>
            <msh:tableColumn isCalcAmount="true" columnName="Число летальных исходов" property="4" addParam="&addCell=dead"/>
            <msh:tableColumn columnName="% летальных исходов по отделению" property="6"/>
            <msh:tableColumn columnName="%  от летальных исходов всего" property="7"/>
            <msh:tableColumn columnName="Кол-во вскрытий" property="9"/>
            <msh:tableColumn columnName="Кол-во расхождений" property="10"/>
            <msh:tableColumn columnName="% расхождения" property="11"/>
        </msh:table>
    </msh:sectionContent>
    </msh:section>
    <%}  else if ("1".equals(view)) {
    %>
    <msh:section title="Реестр пациентов">
    <msh:sectionContent>
        ${isReportBase}<ecom:webQuery isReportBase="${isReportBase}" nameFldSql="journal_surOperation_sql"  name="journal_surOperation" nativeSql="
    select
    hmc.id as slsid
    ,list (vdrt.name||' - '||mkb.code)
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
	left join address2 adr on adr.addressid=pat.address_addressid
	left join Omc_oksm ok on ok.id=pat.nationality_id
	left join diagnosis diag on diag.medcase_id = hmc.id
	left join vocdiagnosisregistrationtype vdrt on vdrt.id=diag.registrationtype_id
	left join vocprioritydiagnosis vpd on vpd.id=diag.priority_id
	left join vocidc10 mkb on mkb.id=diag.idc10_id
	left join bedfund bf on bf.id=dmc.bedfund_id
	left join vocbedsubtype vbst on vbst.id=bf.bedsubtype_id
	where hmc.dtype='HospitalMedCase' and hmc.deniedhospitalizating_id is null and ${dateT} between to_date('${param.dateBegin}','dd.MM.yyyy') and to_date('${dateEnd}','dd.MM.yyyy')
	and hmc.datefinish is not null and vdrt.code in ('5','3') and vpd.code='1' 
    and (vhr.omccode='11'
    ${cellAdd}
    	${depIsNoOmc}
     ${addNewborn} ${bedSubTypeSql}
    group by hmc.id,ss.code, pat.lastname,pat.firstname,pat.middlename,pat.birthday
    order by pat.lastname,pat.firstname,pat.middlename "/>
        <msh:table name="journal_surOperation" printToExcelButton="Сохранить в excel"
        viewUrl="entityShortView-stac_ssl.do"
         action="entityView-stac_ssl.do" idField="1">
         
          <msh:tableColumn columnName="##" property="sn" />
          <msh:tableColumn columnName="№стат. карт" property="3" />
          <msh:tableColumn columnName="ФИО пациента" property="4" />
          <msh:tableColumn columnName="Возраст" property="5" />
          <msh:tableColumn columnName="Дата поступления" property="6"/>
          <msh:tableColumn columnName="Дата выписки" property="7"/>
          <msh:tableColumn columnName="Экстренно?" property="8"/>
          <msh:tableColumn columnName="Диагноз" property="2"/>
		  </msh:table>
        </msh:sectionContent>
        </msh:section>  
 
    <% } else if ("3".equals(view)) {
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
	left join bedfund bf on bf.id=dmc.bedfund_id
	left join vocbedsubtype vbst on vbst.id=bf.bedsubtype_id
where hmc.dtype='HospitalMedCase' and hmc.deniedhospitalizating_id is null and ${dateT} between to_date('${param.dateBegin}','dd.MM.yyyy') and to_date('${dateEnd}','dd.MM.yyyy') and hmc.datefinish is not null and hmc.dischargetime is not null 

    	${depIsNoOmc} ${addNewborn}
    
"/>
        <msh:table name="journal_hospType" cellFunction="true" printToExcelButton="Сохранить в excel"
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
    	<%  } else if ("4".equals(view)) {
    		%>
 <msh:section title="Свод по срокам смерти с момента госпитализации">
    <msh:sectionContent>
    <ecom:webQuery isReportBase="${isReportBase}" nameFldSql="journal_list_swod_sql" name="journal_list_swod" nativeSql="
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
	left join bedfund bf on bf.id=dmc.bedfund_id
	left join vocbedsubtype vbst on vbst.id=bf.bedsubtype_id
	
	where hmc.dtype='HospitalMedCase' and ${dateT} between to_date('${param.dateBegin}','dd.MM.yyyy') and to_date('${dateEnd}','dd.MM.yyyy')
	and hmc.datefinish is not null and hmc.dischargetime is not null
	and hmc.deniedhospitalizating_id is null
	and vhr.omccode='11'	 ${depIsNoOmc} ${addNewborn}

   " />
        <msh:table name="journal_list_swod" cellFunction="true" printToExcelButton="Сохранить в excel"
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
    	} else if ("5".equals(view)) {
    		%>
    		<msh:section title="Свод по возрастным группам">
    <msh:sectionContent>
    <ecom:webQuery isReportBase="${isReportBase}" nameFldSql="journal_list_swod_sql" name="journal_list_swod" nativeSql="
select 

count (case when (cast(to_char(${dateT},'yyyy') as int)-cast(to_char(pat.birthday,'yyyy') as int)
	+(case when (cast(to_char(${dateT}, 'mm') as int)-cast(to_char(pat.birthday, 'mm') as int)
	+(case when (cast(to_char(${dateT},'dd') as int) - cast(to_char(pat.birthday,'dd') as int)<0)
	then -1 else 0 end)<0) then -1 else 0 end)<18) then 1 else null end) as f1_age1
,count (case when (cast(to_char(${dateT},'yyyy') as int)-cast(to_char(pat.birthday,'yyyy') as int)
	+(case when (cast(to_char(${dateT}, 'mm') as int)-cast(to_char(pat.birthday, 'mm') as int)
	+(case when (cast(to_char(${dateT},'dd') as int) - cast(to_char(pat.birthday,'dd') as int)<0)
	then -1 else 0 end)<0) then -1 else 0 end) between 18 and 45) then 1 else null end) as f2_age2
,count (case when (cast(to_char(${dateT},'yyyy') as int)-cast(to_char(pat.birthday,'yyyy') as int)
	+(case when (cast(to_char(${dateT}, 'mm') as int)-cast(to_char(pat.birthday, 'mm') as int)
	+(case when (cast(to_char(${dateT},'dd') as int) - cast(to_char(pat.birthday,'dd') as int)<0)
	then -1 else 0 end)<0) then -1 else 0 end) between 46 and 60) then 1 else null end) as f3_age3
,count (case when (cast(to_char(${dateT},'yyyy') as int)-cast(to_char(pat.birthday,'yyyy') as int)
	+(case when (cast(to_char(${dateT}, 'mm') as int)-cast(to_char(pat.birthday, 'mm') as int)
	+(case when (cast(to_char(${dateT},'dd') as int) - cast(to_char(pat.birthday,'dd') as int)<0)
	then -1 else 0 end)<0) then -1 else 0 end) between 61 and 75) then 1 else null end) as f4_age4
,count (case when (cast(to_char(${dateT},'yyyy') as int)-cast(to_char(pat.birthday,'yyyy') as int)
	+(case when (cast(to_char(${dateT}, 'mm') as int)-cast(to_char(pat.birthday, 'mm') as int)
	+(case when (cast(to_char(${dateT},'dd') as int) - cast(to_char(pat.birthday,'dd') as int)<0)
	then -1 else 0 end)<0) then -1 else 0 end)>75) then 1 else null end) as f5_age5
,count(hmc.id) as f6_all
,round(count (case when (cast(to_char(${dateT},'yyyy') as int)-cast(to_char(pat.birthday,'yyyy') as int)
	+(case when (cast(to_char(${dateT}, 'mm') as int)-cast(to_char(pat.birthday, 'mm') as int)
	+(case when (cast(to_char(${dateT},'dd') as int) - cast(to_char(pat.birthday,'dd') as int)<0)
	then -1 else 0 end)<0) then -1 else 0 end)<18) then 1 else null end)/cast(count(hmc.id)as numeric(9,2))*100,2) as f7_age1_per
,round(count (case when (cast(to_char(${dateT},'yyyy') as int)-cast(to_char(pat.birthday,'yyyy') as int)
	+(case when (cast(to_char(${dateT}, 'mm') as int)-cast(to_char(pat.birthday, 'mm') as int)
	+(case when (cast(to_char(${dateT},'dd') as int) - cast(to_char(pat.birthday,'dd') as int)<0)
	then -1 else 0 end)<0) then -1 else 0 end) between 18 and 45) then 1 else null end)/cast(count(hmc.id)as numeric(9,2))*100,2) as f8_age2_per
,round(count (case when (cast(to_char(${dateT},'yyyy') as int)-cast(to_char(pat.birthday,'yyyy') as int)
	+(case when (cast(to_char(${dateT}, 'mm') as int)-cast(to_char(pat.birthday, 'mm') as int)
	+(case when (cast(to_char(${dateT},'dd') as int) - cast(to_char(pat.birthday,'dd') as int)<0)
	then -1 else 0 end)<0) then -1 else 0 end) between 46 and 60) then 1 else null end)/cast(count(hmc.id)as numeric(9,2))*100,2) as f9_age3_per
,round(count (case when (cast(to_char(${dateT},'yyyy') as int)-cast(to_char(pat.birthday,'yyyy') as int)
	+(case when (cast(to_char(${dateT}, 'mm') as int)-cast(to_char(pat.birthday, 'mm') as int)
	+(case when (cast(to_char(${dateT},'dd') as int) - cast(to_char(pat.birthday,'dd') as int)<0)
	then -1 else 0 end)<0) then -1 else 0 end) between 61 and 75) then 1 else null end)/cast(count(hmc.id)as numeric(9,2))*100,2) as f10_age4_per
,round(count (case when (cast(to_char(${dateT},'yyyy') as int)-cast(to_char(pat.birthday,'yyyy') as int)
	+(case when (cast(to_char(${dateT}, 'mm') as int)-cast(to_char(pat.birthday, 'mm') as int)
	+(case when (cast(to_char(${dateT},'dd') as int) - cast(to_char(pat.birthday,'dd') as int)<0)
	then -1 else 0 end)<0) then -1 else 0 end)>75) then 1 else null end)/cast(count(hmc.id)as numeric(9,2))*100,2) as f11_age5_per

,cast('&department=${param.department}&sex=' as varchar)||vs.id as f12_cntId
,vs.name as f13_sex
from medcase hmc 
	left join vochospitalizationresult vhr on vhr.id=hmc.result_id
	left join patient pat on pat.id=hmc.patient_id
	left join medcase dmc on dmc.parent_id=hmc.id and dmc.dtype='DepartmentMedCase' and dmc.datefinish is not null
	left join medcase pdmc on pdmc.id=dmc.prevmedcase_id
	left join mislpu d on d.id=dmc.department_id
	left join mislpu pd on pd.id=pdmc.department_id
	left join vocsex vs on vs.id=pat.sex_id
	left join bedfund bf on bf.id=dmc.bedfund_id
	left join vocbedsubtype vbst on vbst.id=bf.bedsubtype_id
	
	where hmc.dtype='HospitalMedCase' and ${dateT} between to_date('${param.dateBegin}','dd.MM.yyyy') and to_date('${dateEnd}','dd.MM.yyyy')
	and hmc.datefinish is not null and hmc.dischargetime is not null
	and hmc.deniedhospitalizating_id is null
	and vhr.omccode='11'	 ${depIsNoOmc} ${addNewborn}
group by vs.name, vs.id
   " />
        <msh:table name="journal_list_swod" cellFunction="true" printToExcelButton="Сохранить в excel"
         action="mis_mortality_report.do?short=Short&typeView=1&dateBegin=${param.dateBegin}&dateEnd=${dateEnd}" idField="12" noDataMessage="Не найдено" >
            <msh:tableColumn columnName="#" property="sn"/>
               <msh:tableColumn columnName="Пол" property="13" addParam=""/>
            <msh:tableColumn columnName="от 0 до 17 лет" property="1" addParam="&addCell=age1" isCalcAmount="true"/>
            <msh:tableColumn columnName="%" property="7" addParam="" />
            <msh:tableColumn columnName="18 - 45 лет" property="2" addParam="&addCell=age2" isCalcAmount="true"/>
            <msh:tableColumn columnName="%" property="8" addParam="" />
            <msh:tableColumn columnName="46 - 60 лет" property="3" addParam="&addCell=age3" isCalcAmount="true"/>
            <msh:tableColumn columnName="%" property="9" addParam="" />
            <msh:tableColumn columnName="61 - 75 лет" property="4" addParam="&addCell=age4" isCalcAmount="true"/>
            <msh:tableColumn columnName="%" property="10" addParam="" />
            <msh:tableColumn columnName="76 лет и более" property="5" addParam="&addCell=age5" isCalcAmount="true"/>
            <msh:tableColumn columnName="%" property="11" addParam="" />
            <msh:tableColumn columnName="Итого" property="6" isCalcAmount="true" addParam="&addCell=allAge"/>
            
       </msh:table>
    </msh:sectionContent>
    </msh:section>   
    	<%
    	} else if ("6".equals(view)) {
    	%>
    	<msh:section title="Свод по иногородним и иностранным гражданам">
    <msh:sectionContent>
    <ecom:webQuery isReportBase="${isReportBase}" nameFldSql="journal_list_swod_sql" name="journal_list_swod" nativeSql="
	select '&department='||case when d.isNoOmc='1' then pd.id else d.id end as id 
	,case when d.isnoomc='1' then pd.id else d.id end as f2_lpuId
	,case when d.isnoomc='1' then pd.name else d.name end  as f3_lpuName
	,count (case when ok.voc_code!='643' then pat.id else null end) as f4_inostrPat
	,count (case when adr.kladr not like '30%' and ok.voc_code='643' then 1 else null end) as f5_inogPat
	,count(hmc.id) as f6_all
	from medcase hmc 
	left join vochospitalizationresult vhr on vhr.id=hmc.result_id
	left join patient pat on pat.id=hmc.patient_id
	left join medcase dmc on dmc.parent_id=hmc.id and dmc.dtype='DepartmentMedCase' and dmc.datefinish is not null
	left join medcase pdmc on pdmc.id=dmc.prevmedcase_id
	left join mislpu d on d.id=dmc.department_id
	left join mislpu pd on pd.id=pdmc.department_id
	left join address2 adr on adr.addressid=pat.address_addressid
	left join Omc_oksm ok on ok.id=pat.nationality_id
	left join bedfund bf on bf.id=dmc.bedfund_id
	left join vocbedsubtype vbst on vbst.id=bf.bedsubtype_id
	where hmc.dtype='HospitalMedCase' and hmc.deniedhospitalizating_id is null and ${dateT} between to_date('${param.dateBegin}','dd.MM.yyyy') and to_date('${dateEnd}','dd.MM.yyyy')
	and vhr.omccode='11' and hmc.datefinish is not null and hmc.dischargetime is not null and (ok.voc_code!='643' or adr.kladr not like '30%')
    	${depIsNoOmc} ${addNewborn}
	group by case when (d.isnoomc='1') then pd.id else d.id end , case when (d.isnoomc='1') then pd.name else d.name end 
	
	order by f3_lpuName
   " />
        <msh:table name="journal_list_swod" cellFunction="true" printToExcelButton="Сохранить в excel"
         action="mis_mortality_report.do?short=Short&typeView=1&dateBegin=${param.dateBegin}&dateEnd=${dateEnd}" idField="1" noDataMessage="Не найдено" >
            <msh:tableColumn columnName="#" property="sn"/>
            <msh:tableColumn columnName="Отделение" property="3"/>
            <msh:tableColumn isCalcAmount="true" columnName="Иногородние" property="5" addParam="&addCell=inogor"/>
            <msh:tableColumn isCalcAmount="true" columnName="Иностранные" property="4" addParam="&addCell=inostr"/>
            <msh:tableColumn columnName="Всего" property="6" addParam="&addParam=foreign"/>
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
    checkFieldUpdate('typeView','${typeView}',6);
    checkFieldUpdate('typeNewborn','${typeNewborn}',3); 
     
     
   
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

