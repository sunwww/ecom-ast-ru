<%@page import="ru.ecom.web.util.ActionUtil"%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://struts.apache.org/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://www.nuzmsh.ru/tags/msh" prefix="msh" %>
<%@ taglib uri="http://www.ecom-ast.ru/tags/ecom" prefix="ecom" %>
<%@ taglib tagdir="/WEB-INF/tags" prefix="tags" %>

<tiles:insert page="/WEB-INF/tiles/mainLayout.jsp" flush="true" >

    <tiles:put name='title' type='string'>
        <msh:title mainMenu="Poly">Просмотр данных по диагнозам посещений</msh:title>
    </tiles:put>

    <tiles:put name='side' type='string'>
         <tags:visit_finds currentAction="reportDiagnosis"/>
    </tiles:put>
    
  <tiles:put name="body" type="string">
  	<%
	String typePat =ActionUtil.updateParameter("GroupByBedFund","typePatient","4", request) ;
	ActionUtil.updateParameter("GroupByBedFund","period","2", request) ;
	String typeDate = ActionUtil.updateParameter("GroupByBedFund","typeDate","2", request) ;
	String typeSwod = ActionUtil.updateParameter("GroupByBedFund","typeSwod","1", request) ;
	String typeStatus = ActionUtil.updateParameter("GroupByBedFund","typeStatus","2", request) ;
	String typeView = ActionUtil.updateParameter("DiagnosisByVisit","typeView","1", request) ;
	String typeMKB = ActionUtil.updateParameter("DiagnosisBySlo","typeMKB","4", request) ;
	String typeAge = ActionUtil.updateParameter("DiagnosisBySlo","typeAge","1", request) ;
	String typeEmergency = ActionUtil.updateParameter("DiagnosisBySlo","typeEmergency","3", request) ;
	String typeReestr = request.getParameter("typeReestr") ;
	String typeDtype = ActionUtil.updateParameter("DiagnosisBySlo","typeDtype","3", request) ;
  	
  	%>
    <msh:form action="/journal_visit_diagnosis.do" defaultField="department" disableFormDataConfirm="true" method="GET">
    <msh:panel>
      <msh:row>
        <msh:separator label="Параметры поиска" colSpan="7" />
      </msh:row>
    
         <msh:row>
	        <td class="label" title="База (typeDtype)" colspan="1">
	        <label for="typeDtypeName" id="typeDtypeLabel">Отобразить:</label></td>
	        <td onclick="this.childNodes[1].checked='checked';">
	        	<input type="radio" name="typeDtype" value="1">  Визит.
	        </td>
	        <td onclick="this.childNodes[1].checked='checked';" colspan="2">
	        	<input type="radio" name="typeDtype" value="2" >  Талон.
	        </td>
	        <td onclick="this.childNodes[1].checked='checked';">
	        	<input type="radio" name="typeDtype" value="3">  Все
	        </td>
        </msh:row>   
        <msh:row>
        	        <td class="label" title="База (typeAge)" colspan="1">
	        <label for="typeAgeName" id="typeAgeLabel">Возраст рассчитывать:</label></td>
	        <td onclick="this.childNodes[1].checked='checked';">
	        	<input type="radio" name="typeAge" value="1">  на дату посещения
	        </td>
	        <td onclick="this.childNodes[1].checked='checked';" colspan="2">
	        	<input type="radio" name="typeAge" value="2" >  на конец периода
	        </td>
        </msh:row>        
     <msh:row>
        <td class="label" title="Поиск по МКБ (typeMKB)" colspan="1"><label for="typeMKbName" id="typeMkbLabel">МКБ:</label></td>
        <td onclick="this.childNodes[1].checked='checked';">
        	<input type="radio" name="typeMKB" value="1">  перв. симв.
        </td>
        <td onclick="this.childNodes[1].checked='checked';">
        	<input type="radio" name="typeMKB" value="2">  два симв.
        </td>
        <td onclick="this.childNodes[1].checked='checked';">
        	<input type="radio" name="typeMKB" value="3">  три симв.
        </td>
        <td onclick="this.childNodes[1].checked='checked';">
        	<input type="radio" name="typeMKB" value="4">  полностью
        </td>
        </msh:row>
        <msh:row>
	        <td class="label" title="Поиск по показаниям (typeEmergency)" colspan="1"><label for="typeEmergencyName" id="typeEmergencyLabel">Показания:</label></td>
	        <td onclick="this.childNodes[1].checked='checked';">
	        	<input type="radio" name="typeEmergency" value="1">  экстренные
	        </td>
	        <td onclick="this.childNodes[1].checked='checked';">
	        	<input type="radio" name="typeEmergency" value="2"  >  плановые
	        </td>
	        <td onclick="this.childNodes[1].checked='checked';">
	        	<input type="radio" name="typeEmergency" value="3">  все
	        </td>
        </msh:row> 
        <msh:row>
	        <td class="label" title="Отобразить (typeView)" colspan="1"><label for="typeViewName" id="typeViewLabel">Отобразить:</label></td>
	        <td onclick="this.childNodes[1].checked='checked';">
	        	<input type="radio" name="typeView" value="1"> общий свод
	        </td>
	        <td onclick="this.childNodes[1].checked='checked';">
	        	<input type="radio" name="typeView" value="2"  > по возрастам (общий свод)
	        </td>
	        <td onclick="this.childNodes[1].checked='checked';" colspan="2">
	        	<input type="radio" name="typeView" value="3"  > по возрастам (впервые выявлено)
	        </td>
        </msh:row>
             
        <msh:row>
        	<msh:autoComplete property="department" fieldColSpan="5"
        	label="ЛПУ" horizontalFill="true" vocName="lpu"/>
        </msh:row>
        <msh:row>
<%--         	<msh:autoComplete property="spec" fieldColSpan="5" label="Специалист" horizontalFill="true" vocName="workFunctionByDirect"/> --%>
        	<msh:autoComplete property="spec" fieldColSpan="5" label="Специалист" horizontalFill="true" vocName="workFunction"/>
        </msh:row>
        <msh:row>
        	<msh:autoComplete property="workFunction" fieldColSpan="1"
        	label="Раб.функция" horizontalFill="true" vocName="vocWorkFunction"/>
            <msh:autoComplete vocName="vocIllnesPrimary" property="illnesPrimary" 
            label="Характер заболевания" horizontalFill="true" fieldColSpan="1"/>
        </msh:row>
        <msh:row>
        	<msh:autoComplete property="serviceStream" fieldColSpan="2"
        	label="Поток обслуживания" horizontalFill="true" vocName="vocServiceStream"/>
          <msh:autoComplete vocName="vocPriorityDiagnosis" property="priority" label="Приоритет"  horizontalFill="true" />
        </msh:row>       
        <msh:row>
        	<msh:textField property="filterAdd" label="Фильтр МКБ" fieldColSpan="3" horizontalFill="true"/>
        	<td><i>Пример: <b>A0</b>, <b>N</b>, <b>A00-B87</b></i></td>
        </msh:row>        <msh:row>
	        <msh:textField property="dateBegin" label="Период с" />
	        <msh:textField property="dateEnd" label="по" />
			<td>
	            <input type="submit" onclick="find()" value="Найти" />
	          </td>
        </msh:row>
    </msh:panel>
    </msh:form>
    
    <%
    String date = request.getParameter("dateBegin") ;
    if (date!=null && !date.equals(""))  {
    	String dateEnd = request.getParameter("dateEnd") ;
    	if (dateEnd==null||dateEnd.equals("")) {
    		request.setAttribute("dateEnd", date) ;
    	} else {
    		request.setAttribute("dateEnd", dateEnd) ;
    	}
    	String dtypeSql = " (vis.dtype='Visit' or vis.dtype='ShortMedCase') " ;
    	if (typeDtype.equals("1")) {
    		dtypeSql="vis.dtype='Visit'" ;
    	} else if (typeDtype.equals("2")) {
    		dtypeSql="vis.dtype='ShortMedCase'" ;
    	}
    	if (typeAge!=null&&typeAge.equals("1")) {
    		request.setAttribute("ageSql", "vis.dateStart") ;
    	} else {
    		request.setAttribute("ageSql", "to_date('"+dateEnd+"','dd.mm.yyyy')") ;
    	}
    	request.setAttribute("dtypeSql", dtypeSql);
    //	String stat = request.getParameter("typeStatus") ;
    	String mkbCode = "mkb.code" ;
    	String mkbName = "mkb.name" ;
    	if (typeMKB!=null) {
    		if (typeMKB.equals("1")) {mkbName="substring(mkb.code,1,1)";mkbCode="substring(mkb.code,1,1)" ;} 
    		else if (typeMKB.equals("2")) {mkbName="substring(mkb.code,1,2)";mkbCode="substring(mkb.code,1,2)" ;}
    		else if (typeMKB.equals("3")) {mkbName="substring(mkb.code,1,3)";mkbCode="substring(mkb.code,1,3)" ;}
    	} 
    	request.setAttribute("mkbName",mkbName) ;
    	request.setAttribute("mkbNameGroup",mkbName.equals("mkb.name")?",mkb.name":"") ;
    	request.setAttribute("mkbCode",mkbCode) ;
    	//request.setAttribute("mkbLike",mkbLike) ;

/*    	boolean isStat = true ;
    	if (stat!=null && stat.equals("2")) {
    		isStat = false ;
    	}
*/
    	String dep = request.getParameter("department") ;
    	if (dep!=null && !dep.equals("") && !dep.equals("0")) {
    		request.setAttribute("departmentSql", " and w.lpu_id="+dep) ;
    		request.setAttribute("department",dep) ;
    	} else {
    		request.setAttribute("department","0") ;
    	}
    	String spec = request.getParameter("spec") ;
    	if (spec!=null && !spec.equals("") && !spec.equals("0")) {
    		request.setAttribute("specSql", " and vis.workFunctionExecute_id="+spec) ;
    		request.setAttribute("spec",spec) ;
    	} else {
    		request.setAttribute("workFunction","0") ;
    	}
    	String workFunction = request.getParameter("workFunction") ;
    	if (workFunction!=null && !workFunction.equals("") && !workFunction.equals("0")) {
    		request.setAttribute("workFunctionSql", " and wf.workFunction_id="+workFunction) ;
    		request.setAttribute("workFunction",spec) ;
    	} else {
    		request.setAttribute("workFunction","0") ;
    	}
    	String servStream = request.getParameter("serviceStream") ;
    	if (servStream!=null && !servStream.equals("") && !servStream.equals("0")) {
    		request.setAttribute("serviceStreamSql", " and vss.id="+servStream) ;
    		request.setAttribute("serviceStream", servStream) ;
    	} else {
    		request.setAttribute("serviceStream", "0") ;
    	}
    	String priority = request.getParameter("priority") ;
    	if (priority!=null && !priority.equals("") && !priority.equals("0")) {
    		request.setAttribute("prioritySql", " and diag.priority_id="+priority) ;
    	} 
    	String illnesPrimary = request.getParameter("illnesPrimary") ;
    	if (illnesPrimary!=null && !illnesPrimary.equals("") && !illnesPrimary.equals("0")) {
    		request.setAttribute("illnesPrimarySql", " and diag.illnesPrimary_id="+illnesPrimary) ;
    	} 
    	
    	if ("1".equals(typeEmergency)) {
    		request.setAttribute("emergencySql", " and vis.emergency='1' ") ;
    	} else if ("2".equals(typeEmergency)) {
    		request.setAttribute("emergencySql", " and (vis.emergency is null or vis.emergency='0') ") ;
    	} 
    	String filter = request.getParameter("filterAdd") ;
    	if (filter!=null && !filter.equals("")) {
    		filter = filter.toUpperCase() ;
    		request.setAttribute("filterAdd",filter) ;
    		//filter = filter.replaceAll(" ", "") ;
    		String[] fs1=filter.split(",") ;
    		StringBuilder filtOr = new StringBuilder() ;

			for (String s : fs1) {
				String filt1 = s.trim();
				String[] fs = filt1.split("-");
				if (filt1.length() > 0) {
					if (filtOr.length() > 0) filtOr.append(" or ");
					if (fs.length > 1) {
						filtOr.append(" mkb.code between '").append(fs[0].trim()).append("' and '").append(fs[1].trim()).append("'");
					} else {
						filtOr.append(" substring(mkb.code,1,").append(filt1.length()).append(") = '").append(filt1).append("'");
					}
				}
			}
    		request.setAttribute("filterSql", filtOr.insert(0, " and (").append(")").toString()) ;
    		
    	} 
    	if ("1".equals(typeReestr)) {
    		String mkbCodeP = request.getParameter("mkbcode") ;
    		String mkbCodeA = " and mkb.code = '"+mkbCodeP+"'" ;
    		if (!"4".equals(typeMKB)) {
    			mkbCodeA=" and mkb.code like '"+mkbCodeP+"%'" ;
    		}
    		request.setAttribute("mkbCodeSql",mkbCodeA) ;
    		
    		%>
    		<msh:section>
	<ecom:webQuery isReportBase="true" name="datelist" nameFldSql="datelist_sql" nativeSql="
	select vis.id,to_char(vis.dateStart,'dd.mm.yyyy') as datestart
	,pat.lastname ||' ' ||pat.firstname|| ' ' || pat.middlename as fio
	,to_char(pat.birthday,'dd.mm.yyyy') as birthday,mkb.code,vip.name
	,vwf.name||' '||wp.lastname ||' ' ||wp.firstname|| ' ' || wp.middlename as doctor
	,coalesce(a.fullname)||' ' || case when pat.houseNumber is not null and pat.houseNumber!='' then ' д.'||pat.houseNumber else '' end 
	 ||case when pat.houseBuilding is not null and pat.houseBuilding!='' then ' корп.'|| pat.houseBuilding else '' end 
	||case when pat.flatNumber is not null and pat.flatNumber!='' then ' кв. '|| pat.flatNumber else '' end as address
	
	 from Diagnosis diag
	left join VocIllnesPrimary vip on vip.id=diag.illnesPrimary_id
	left join VocIdc10 mkb on mkb.id=diag.idc10_id
	left join MedCase vis on vis.id=diag.medCase_id
	left join Patient pat on pat.id=vis.patient_id
	left join VocSex vs on vs.id=pat.sex_id
	left join VocServiceStream vss on vss.id=vis.serviceStream_id
	left join VocPriorityDiagnosis vpd on vpd.id=diag.priority_id
	left join WorkFunction wf on wf.id=vis.workFunctionExecute_id
	left join VocWorkFunction vwf on vwf.id=wf.workFunction_id
	left join Worker w on w.id=wf.worker_id
	left join Patient wp on wp.id=w.person_id
	left join Address2 a on a.addressid=pat.address_addressId
	where vis.dateStart between to_date('${param.dateBegin}','dd.mm.yyyy')
			and to_date('${dateEnd}','dd.mm.yyyy') and ${dtypeSql}
			and (vis.noActuality is null or vis.noActuality='0')
			${serviceStreamSql} ${departmentSql} ${prioritySql} ${specSql} ${workFunctionSql}
			${emergencySql} ${mkbCodeSql} ${illnesPrimarySql}
			${filterSql}
	order by pat.lastname,pat.firstname,pat.middlename
	" 
	/>
	<msh:sectionTitle>	 
	<form action="print-journal_visit_diagnosis_r.do" method="post" target="_blank">
    <input type='hidden' name="sqlText" id="sqlText" value="${datelist_sql}"> 
    <input type='hidden' name="sqlInfo" id="sqlInfo" value="Период с ${param.dateBegin} по ${dateEnd}">
    <input type='hidden' name="s" id="s" value="PrintService">
    <input type='hidden' name="m" id="m" value="printNativeQuery">
    <input type="submit" value="Печать"> 
    </form></msh:sectionTitle>
	<msh:sectionContent>
    <msh:table printToExcelButton="Сохранить в excel"  name="datelist" viewUrl="entitySubclassShortView-mis_medCase.do" action="entitySubclassView-mis_medCase.do" idField="1">
      <msh:tableColumn property="sn" columnName="#"/>
      <msh:tableColumn property="2" columnName="Дата приема"/>
      <msh:tableColumn columnName="Фамилия имя отчество пациента" property="3" />
      <msh:tableColumn columnName="Год рождения" property="4" />
      <msh:tableColumn columnName="МКБ 10" property="5" />
      <msh:tableColumn columnName="Характер заболевания" property="6" />
      <msh:tableColumn columnName="Специалист" property="7" />
      <msh:tableColumn columnName="Адрес" property="8" />
    </msh:table>    
    </msh:sectionContent>		
    		</msh:section>
    		
    		<%
    	} else {
    		if (typeView.equals("2") || typeView.equals("3")) {
    			if (typeView.equals("3")) {
    				request.setAttribute("sqlAddParamView", " and (vip.code='2' or vip.code='3') ") ;
    				request.setAttribute("titleInfo", "Разбивка пациентов по возрастам (впервые выявленные)") ;
    			} else {
    				request.setAttribute("sqlAddParamView", "") ;
    				request.setAttribute("titleInfo", "Разбивка пациентов по возрастам (общий свод)") ;
    			}
    			%>
    <msh:section>

		<ecom:webQuery isReportBase="true" name="diag_list" nameFldSql="diag_list_sql" nativeSql="
		select '&mkbcode='||${mkbCode}||'&priority='||vpd.id as id
		,vpd.name as vpdname
		,${mkbCode} as mkb,${mkbName} as mkbname
		,count(distinct pat.id) as cntPat
		,count(distinct case when (vs.omccode='2') then pat.id else null end) as cntPatWoman
		,count(distinct case when (cast(to_char(${ageSql},'yyyy') as int)-cast(to_char(pat.birthday,'yyyy') as int)+case when (cast(to_char(${ageSql}, 'mm') as int)-cast(to_char(pat.birthday, 'mm') as int)) <0 then -1 when (cast(to_char(${ageSql},'dd') as int) - cast(to_char(pat.birthday,'dd') as int)<0) and ((cast(to_char(${ageSql}, 'mm') as int)-cast(to_char(pat.birthday, 'mm') as int)-1)<0)  then -1 else 0 end) between 0 and 14 then pat.id else null end) as cntPat0_14
		,count(distinct case when (cast(to_char(${ageSql},'yyyy') as int)-cast(to_char(pat.birthday,'yyyy') as int)+case when (cast(to_char(${ageSql}, 'mm') as int)-cast(to_char(pat.birthday, 'mm') as int)) <0 then -1 when (cast(to_char(${ageSql},'dd') as int) - cast(to_char(pat.birthday,'dd') as int)<0) and ((cast(to_char(${ageSql}, 'mm') as int)-cast(to_char(pat.birthday, 'mm') as int)-1)<0)  then -1 else 0 end) between 15 and 17 then pat.id else null end) as cntPat15_17
		,count(distinct case when (cast(to_char(${ageSql},'yyyy') as int)-cast(to_char(pat.birthday,'yyyy') as int)+case when (cast(to_char(${ageSql}, 'mm') as int)-cast(to_char(pat.birthday, 'mm') as int)) <0 then -1 when (cast(to_char(${ageSql},'dd') as int) - cast(to_char(pat.birthday,'dd') as int)<0) and ((cast(to_char(${ageSql}, 'mm') as int)-cast(to_char(pat.birthday, 'mm') as int)-1)<0)  then -1 else 0 end) between 18 and 19 then pat.id else null end) as cntPat18_19
		,count(distinct case when (cast(to_char(${ageSql},'yyyy') as int)-cast(to_char(pat.birthday,'yyyy') as int)+case when (cast(to_char(${ageSql}, 'mm') as int)-cast(to_char(pat.birthday, 'mm') as int)) <0 then -1 when (cast(to_char(${ageSql},'dd') as int) - cast(to_char(pat.birthday,'dd') as int)<0) and ((cast(to_char(${ageSql}, 'mm') as int)-cast(to_char(pat.birthday, 'mm') as int)-1)<0)  then -1 else 0 end) between 20 and 39 then pat.id else null end) as cntPat20_39
		,count(distinct case when (cast(to_char(${ageSql},'yyyy') as int)-cast(to_char(pat.birthday,'yyyy') as int)+case when (cast(to_char(${ageSql}, 'mm') as int)-cast(to_char(pat.birthday, 'mm') as int)) <0 then -1 when (cast(to_char(${ageSql},'dd') as int) - cast(to_char(pat.birthday,'dd') as int)<0) and ((cast(to_char(${ageSql}, 'mm') as int)-cast(to_char(pat.birthday, 'mm') as int)-1)<0)  then -1 else 0 end) between 40 and 59 then pat.id else null end) as cntPat40_59
		,count(distinct case when (cast(to_char(${ageSql},'yyyy') as int)-cast(to_char(pat.birthday,'yyyy') as int)+case when (cast(to_char(${ageSql}, 'mm') as int)-cast(to_char(pat.birthday, 'mm') as int)) <0 then -1 when (cast(to_char(${ageSql},'dd') as int) - cast(to_char(pat.birthday,'dd') as int)<0) and ((cast(to_char(${ageSql}, 'mm') as int)-cast(to_char(pat.birthday, 'mm') as int)-1)<0)  then -1 else 0 end) > 59 then pat.id else null end) as cntPat60_
		
		,count(distinct case when adr.addressIsVillage='1' then pat.id else null end) as cntVPat
		,count(distinct case when (adr.addressIsVillage='1') and (vs.omccode='2') then pat.id else null end) as cntVPatWoman
		,count(distinct case when (adr.addressIsVillage='1') and (cast(to_char(${ageSql},'yyyy') as int)-cast(to_char(pat.birthday,'yyyy') as int)+case when (cast(to_char(${ageSql}, 'mm') as int)-cast(to_char(pat.birthday, 'mm') as int)) <0 then -1 when (cast(to_char(${ageSql},'dd') as int) - cast(to_char(pat.birthday,'dd') as int)<0) and ((cast(to_char(${ageSql}, 'mm') as int)-cast(to_char(pat.birthday, 'mm') as int)-1)<0)  then -1 else 0 end) between 0 and 14 then pat.id else null end) as cntVPat0_14
		,count(distinct case when (adr.addressIsVillage='1') and (cast(to_char(${ageSql},'yyyy') as int)-cast(to_char(pat.birthday,'yyyy') as int)+case when (cast(to_char(${ageSql}, 'mm') as int)-cast(to_char(pat.birthday, 'mm') as int)) <0 then -1 when (cast(to_char(${ageSql},'dd') as int) - cast(to_char(pat.birthday,'dd') as int)<0) and ((cast(to_char(${ageSql}, 'mm') as int)-cast(to_char(pat.birthday, 'mm') as int)-1)<0)  then -1 else 0 end) between 15 and 17 then pat.id else null end) as cntVPat15_17
		,count(distinct case when (adr.addressIsVillage='1') and (cast(to_char(${ageSql},'yyyy') as int)-cast(to_char(pat.birthday,'yyyy') as int)+case when (cast(to_char(${ageSql}, 'mm') as int)-cast(to_char(pat.birthday, 'mm') as int)) <0 then -1 when (cast(to_char(${ageSql},'dd') as int) - cast(to_char(pat.birthday,'dd') as int)<0) and ((cast(to_char(${ageSql}, 'mm') as int)-cast(to_char(pat.birthday, 'mm') as int)-1)<0)  then -1 else 0 end) between 18 and 19 then pat.id else null end) as cntVPat18_19
		,count(distinct case when (adr.addressIsVillage='1') and (cast(to_char(${ageSql},'yyyy') as int)-cast(to_char(pat.birthday,'yyyy') as int)+case when (cast(to_char(${ageSql}, 'mm') as int)-cast(to_char(pat.birthday, 'mm') as int)) <0 then -1 when (cast(to_char(${ageSql},'dd') as int) - cast(to_char(pat.birthday,'dd') as int)<0) and ((cast(to_char(${ageSql}, 'mm') as int)-cast(to_char(pat.birthday, 'mm') as int)-1)<0)  then -1 else 0 end) between 20 and 39 then pat.id else null end) as cntVPat20_39
		,count(distinct case when (adr.addressIsVillage='1') and (cast(to_char(${ageSql},'yyyy') as int)-cast(to_char(pat.birthday,'yyyy') as int)+case when (cast(to_char(${ageSql}, 'mm') as int)-cast(to_char(pat.birthday, 'mm') as int)) <0 then -1 when (cast(to_char(${ageSql},'dd') as int) - cast(to_char(pat.birthday,'dd') as int)<0) and ((cast(to_char(${ageSql}, 'mm') as int)-cast(to_char(pat.birthday, 'mm') as int)-1)<0)  then -1 else 0 end) between 40 and 59 then pat.id else null end) as cntVPat40_59
		,count(distinct case when (adr.addressIsVillage='1') and (cast(to_char(${ageSql},'yyyy') as int)-cast(to_char(pat.birthday,'yyyy') as int)+case when (cast(to_char(${ageSql}, 'mm') as int)-cast(to_char(pat.birthday, 'mm') as int)) <0 then -1 when (cast(to_char(${ageSql},'dd') as int) - cast(to_char(pat.birthday,'dd') as int)<0) and ((cast(to_char(${ageSql}, 'mm') as int)-cast(to_char(pat.birthday, 'mm') as int)-1)<0)  then -1 else 0 end) > 59 then pat.id else null end) as cntVPat60_
		,count(distinct case when (adr.addressIsVillage='1') and vvr.omcCode='2' then pat.id else null end) as cntVAllImprovement
		from Diagnosis diag
		left join VocIllnesPrimary vip on vip.id=diag.illnesPrimary_id
		left join VocIdc10 mkb on mkb.id=diag.idc10_id
		left join MedCase vis on vis.id=diag.medCase_id
		left join VocVisitResult vvr on vvr.id=vis.visitResult_id
		left join Patient pat on pat.id=vis.patient_id
		left join Address2 adr on adr.addressid=pat.address_addressid
		left join VocSex vs on vs.id=pat.sex_id
		left join VocServiceStream vss on vss.id=vis.serviceStream_id
		left join VocPriorityDiagnosis vpd on vpd.id=diag.priority_id
		left join WorkFunction wf on wf.id=vis.workFunctionExecute_id
		left join Worker w on w.id=wf.worker_id
		where vis.dateStart between to_date('${param.dateBegin}','dd.mm.yyyy')
			and to_date('${dateEnd}','dd.mm.yyyy') and ${dtypeSql}
			and (vis.noActuality is null or vis.noActuality='0')
			${serviceStreamSql} ${departmentSql} ${prioritySql} ${specSql} ${workFunctionSql}
			${emergencySql} ${illnesPrimarySql}
			${sqlAddParamView}
			${filterSql}
		group by ${mkbCode},vpd.id,vpd.name ${mkbNameGroup}
		order by ${mkbCode},vpd.id
		"/>
    <msh:sectionTitle>
    
    	    <form action="print-journal_visit_diagnosis_2_3.do" method="post" target="_blank">
	    Разбивка пациентов по возрастам (общий свод)
	    <input type='hidden' name="sqlText" id="sqlText" value="${diag_list_sql}"> 
	    <input type='hidden' name="sqlInfo" id="sqlInfo" value="Разбивка пациентов по возрастам (общий свод) за ${param.dateBegin}-${dateEnd}.">
	    <input type='hidden' name="sqlColumn" id="sqlColumn" value="${groupName}">
	    <input type='hidden' name="s" id="s" value="PrintService">
	    <input type='hidden' name="m" id="m" value="printNativeQuery">
	    <input type="submit" value="Печать"> 
	    </form>     </msh:sectionTitle>
	<msh:sectionContent>
			<msh:table printToExcelButton="Сохранить в excel" name="diag_list" idField="1"
			action="journal_visit_diagnosis.do?typeReestr=1&filterAdd=${param.filterAdd}&serviceStream=${param.serviceStream}&illnesPrimary=${param.illnesPrimary}&spec=${param.spec}&workFunction=${param.workFunction}&department=${param.department}&dateBegin=${param.dateBegin}&dateEnd=${param.dateEnd}&typeDtype=${typeDtype}&typeMKB=${typeMKB}&typeEmergency=${typeEmergency}">
            <msh:tableNotEmpty>
              <tr>
                <th colspan="1" />
                <th colspan="1" />
                <th colspan="1" />
                <th colspan="1" />
                <th colspan="2" >Зарегистр.</th>
                <th colspan="6" class="rightBold">в том числе из общего кол-ва по возрастам</th>
                <th colspan="2" >Зарегистр. с/ж</th>
                <th colspan="6" class="rightBold">в том числе из общего кол-ва с/ж по возрастам</th>
              </tr>
            </msh:tableNotEmpty>            
		
			<msh:tableColumn property="sn" columnName="#"/>
			<msh:tableColumn property="2" columnName="Приоритет"/>
			<msh:tableColumn property="3" columnName="Код МКБ10"/>
			<msh:tableColumn property="4" columnName="Наименование болезни"/>
			<msh:tableColumn property="5" isCalcAmount="true" columnName="Кол-во общее"/>
			<msh:tableColumn property="6" isCalcAmount="true" columnName="из них женщин"/>
			<msh:tableColumn property="7" isCalcAmount="true" columnName="0-14"/>
			<msh:tableColumn property="8" isCalcAmount="true" columnName="15-17"/>
			<msh:tableColumn property="9" isCalcAmount="true" columnName="18-19"/>
			<msh:tableColumn property="10" isCalcAmount="true" columnName="20-39"/>
			<msh:tableColumn property="11" isCalcAmount="true" columnName="40-59"/>
			<msh:tableColumn property="12" isCalcAmount="true" columnName=">=60"/>

			<msh:tableColumn property="13" isCalcAmount="true" columnName="Кол-во общее"/>
			<msh:tableColumn property="14" isCalcAmount="true" columnName="из них женщин"/>
			<msh:tableColumn property="15" isCalcAmount="true" columnName="0-14"/>
			<msh:tableColumn property="16" isCalcAmount="true" columnName="15-17"/>
			<msh:tableColumn property="17" isCalcAmount="true" columnName="18-19"/>
			<msh:tableColumn property="18" isCalcAmount="true" columnName="20-39"/>
			<msh:tableColumn property="19" isCalcAmount="true" columnName="40-59"/>
			<msh:tableColumn property="20" isCalcAmount="true" columnName=">=60"/>
			<msh:tableColumn property="21" isCalcAmount="true" columnName="Улуч."/>

		</msh:table>
    			    </msh:sectionContent>		
    		</msh:section>
    			<%
    			
    		} else {
    			
    		
    	%>
		<ecom:webQuery isReportBase="true" name="diag_list" nativeSql="
		select '&mkbcode='||${mkbCode}||'&priority='||vpd.id as id
		,vpd.name as vpdname
		,${mkbCode} as mkb,${mkbName} as mkbname
		,count(distinct vis.id) as cntVisit
		,count(distinct case when (vip.code='2' or vip.code='3') then vis.id else null end) as cntVisPrimary
		,count(distinct pat.id) as cntPat
		,count(distinct case when (vip.code='2' or vip.code='3') then pat.id else null end) as cntPatPrimary
		,count(distinct case when (cast(to_char(${ageSql},'yyyy') as int)-cast(to_char(pat.birthday,'yyyy') as int)+case when (cast(to_char(${ageSql}, 'mm') as int)-cast(to_char(pat.birthday, 'mm') as int)) <0 then -1 when (cast(to_char(${ageSql},'dd') as int) - cast(to_char(pat.birthday,'dd') as int)<0) and ((cast(to_char(${ageSql}, 'mm') as int)-cast(to_char(pat.birthday, 'mm') as int)-1)<0)  then -1 else 0 end) between 0 and 14 then pat.id else null end) as cntPat14
		,count(distinct case when (vip.code='2' or vip.code='3') and (cast(to_char(${ageSql},'yyyy') as int)-cast(to_char(pat.birthday,'yyyy') as int)+case when (cast(to_char(${ageSql}, 'mm') as int)-cast(to_char(pat.birthday, 'mm') as int)) <0 then -1 when (cast(to_char(${ageSql},'dd') as int) - cast(to_char(pat.birthday,'dd') as int)<0) and ((cast(to_char(${ageSql}, 'mm') as int)-cast(to_char(pat.birthday, 'mm') as int)-1)<0)  then -1 else 0 end) between 0 and 14 then pat.id else null end) as cntPatPrimary14
		,count(distinct case when (cast(to_char(${ageSql},'yyyy') as int)-cast(to_char(pat.birthday,'yyyy') as int)+case when (cast(to_char(${ageSql}, 'mm') as int)-cast(to_char(pat.birthday, 'mm') as int)) <0 then -1 when (cast(to_char(${ageSql},'dd') as int) - cast(to_char(pat.birthday,'dd') as int)<0) and ((cast(to_char(${ageSql}, 'mm') as int)-cast(to_char(pat.birthday, 'mm') as int)-1)<0)  then -1 else 0 end) between 15 and 17 then pat.id else null end) as cntPat17
		,count(distinct case when (vip.code='2' or vip.code='3') and (cast(to_char(${ageSql},'yyyy') as int)-cast(to_char(pat.birthday,'yyyy') as int)+case when (cast(to_char(${ageSql}, 'mm') as int)-cast(to_char(pat.birthday, 'mm') as int)) <0 then -1 when (cast(to_char(${ageSql},'dd') as int) - cast(to_char(pat.birthday,'dd') as int)<0) and ((cast(to_char(${ageSql}, 'mm') as int)-cast(to_char(pat.birthday, 'mm') as int)-1)<0)  then -1 else 0 end) between 15 and 17 then pat.id else null end) as cntPatPrimary17

		,count(distinct case 
			when vs.omccode='1' and (cast(to_char(${ageSql},'yyyy') as int)-cast(to_char(pat.birthday,'yyyy') as int)+case when (cast(to_char(${ageSql}, 'mm') as int)-cast(to_char(pat.birthday, 'mm') as int)) <0 then -1 when (cast(to_char(${ageSql},'dd') as int) - cast(to_char(pat.birthday,'dd') as int)<0) and ((cast(to_char(${ageSql}, 'mm') as int)-cast(to_char(pat.birthday, 'mm') as int)-1)<0)  then -1 else 0 end) between 18 and 60 
				or vs.omccode='2' and (cast(to_char(${ageSql},'yyyy') as int)-cast(to_char(pat.birthday,'yyyy') as int)+case when (cast(to_char(${ageSql}, 'mm') as int)-cast(to_char(pat.birthday, 'mm') as int)) <0 then -1 when (cast(to_char(${ageSql},'dd') as int) - cast(to_char(pat.birthday,'dd') as int)<0) and ((cast(to_char(${ageSql}, 'mm') as int)-cast(to_char(pat.birthday, 'mm') as int)-1)<0)  then -1 else 0 end) between 18 and 55
			then pat.id else null end) as cntPat55
		,count(distinct case 
			when (vip.code='2' or vip.code='3')
		 and (vs.omccode='1' and (cast(to_char(${ageSql},'yyyy') as int)-cast(to_char(pat.birthday,'yyyy') as int)+case when (cast(to_char(${ageSql}, 'mm') as int)-cast(to_char(pat.birthday, 'mm') as int)) <0 then -1 when (cast(to_char(${ageSql},'dd') as int) - cast(to_char(pat.birthday,'dd') as int)<0) and ((cast(to_char(${ageSql}, 'mm') as int)-cast(to_char(pat.birthday, 'mm') as int)-1)<0)  then -1 else 0 end) between 18 and 60 
				or vs.omccode='2' and (cast(to_char(${ageSql},'yyyy') as int)-cast(to_char(pat.birthday,'yyyy') as int)+case when (cast(to_char(${ageSql}, 'mm') as int)-cast(to_char(pat.birthday, 'mm') as int)) <0 then -1 when (cast(to_char(${ageSql},'dd') as int) - cast(to_char(pat.birthday,'dd') as int)<0) and ((cast(to_char(${ageSql}, 'mm') as int)-cast(to_char(pat.birthday, 'mm') as int)-1)<0)  then -1 else 0 end) between 18 and 55)
			then pat.id else null end) as cntPatPrimary55
		
		
		,count(distinct case 
			when vs.omccode='1' and (cast(to_char(${ageSql},'yyyy') as int)-cast(to_char(pat.birthday,'yyyy') as int)+case when (cast(to_char(${ageSql}, 'mm') as int)-cast(to_char(pat.birthday, 'mm') as int)) <0 then -1 when (cast(to_char(${ageSql},'dd') as int) - cast(to_char(pat.birthday,'dd') as int)<0) and ((cast(to_char(${ageSql}, 'mm') as int)-cast(to_char(pat.birthday, 'mm') as int)-1)<0)  then -1 else 0 end)> 60 
				or vs.omccode='2' and (cast(to_char(${ageSql},'yyyy') as int)-cast(to_char(pat.birthday,'yyyy') as int)+case when (cast(to_char(${ageSql}, 'mm') as int)-cast(to_char(pat.birthday, 'mm') as int)) <0 then -1 when (cast(to_char(${ageSql},'dd') as int) - cast(to_char(pat.birthday,'dd') as int)<0) and ((cast(to_char(${ageSql}, 'mm') as int)-cast(to_char(pat.birthday, 'mm') as int)-1)<0)  then -1 else 0 end) > 55
			then pat.id else null end) as cntPatOld
		,count(distinct case 
			when (vip.code='2' or vip.code='3')
		 and (vs.omccode='1' and (cast(to_char(${ageSql},'yyyy') as int)-cast(to_char(pat.birthday,'yyyy') as int)+case when (cast(to_char(${ageSql}, 'mm') as int)-cast(to_char(pat.birthday, 'mm') as int)) <0 then -1 when (cast(to_char(${ageSql},'dd') as int) - cast(to_char(pat.birthday,'dd') as int)<0) and ((cast(to_char(${ageSql}, 'mm') as int)-cast(to_char(pat.birthday, 'mm') as int)-1)<0)  then -1 else 0 end)> 60 
				or vs.omccode='2' and (cast(to_char(${ageSql},'yyyy') as int)-cast(to_char(pat.birthday,'yyyy') as int)+case when (cast(to_char(${ageSql}, 'mm') as int)-cast(to_char(pat.birthday, 'mm') as int)) <0 then -1 when (cast(to_char(${ageSql},'dd') as int) - cast(to_char(pat.birthday,'dd') as int)<0) and ((cast(to_char(${ageSql}, 'mm') as int)-cast(to_char(pat.birthday, 'mm') as int)-1)<0)  then -1 else 0 end) > 55)
			then pat.id else null end) as cntPatPrimaryOld
		from Diagnosis diag
		left join VocIllnesPrimary vip on vip.id=diag.illnesPrimary_id
		left join VocIdc10 mkb on mkb.id=diag.idc10_id
		left join MedCase vis on vis.id=diag.medCase_id
		left join Patient pat on pat.id=vis.patient_id
		left join VocSex vs on vs.id=pat.sex_id
		left join VocServiceStream vss on vss.id=vis.serviceStream_id
		left join VocPriorityDiagnosis vpd on vpd.id=diag.priority_id
		left join WorkFunction wf on wf.id=vis.workFunctionExecute_id
		left join Worker w on w.id=wf.worker_id
		where vis.dateStart between to_date('${param.dateBegin}','dd.mm.yyyy')
			and to_date('${dateEnd}','dd.mm.yyyy') and ${dtypeSql}
			and (vis.noActuality is null or vis.noActuality='0')
			${serviceStreamSql} ${departmentSql} ${prioritySql} ${specSql} ${workFunctionSql}
			${emergencySql} ${illnesPrimarySql}
			${filterSql}
		group by ${mkbCode},vpd.id,vpd.name ${mkbNameGroup}
		order by ${mkbCode},vpd.id
		"/>
		<msh:table printToExcelButton="Сохранить в excel" name="diag_list" idField="1" action="journal_visit_diagnosis.do?typeReestr=1&filterAdd=${param.filterAdd}&serviceStream=${param.serviceStream}&spec=${param.spec}&illnesPrimary=${param.illnesPrimary}&workFunction=${param.workFunction}&department=${param.department}&dateBegin=${param.dateBegin}&dateEnd=${param.dateEnd}&typeDtype=${typeDtype}&typeMKB=${typeMKB}&typeEmergency=${typeEmergency}">
            <msh:tableNotEmpty>
              <tr>
                <th colspan="1" />
                <th colspan="1" />
                <th colspan="1" />
                <th colspan="1" />
                <th colspan="2" class="rightBold">Посещения</th>
                <th colspan="5" class="rightBold">Кол-во пациентов с диагнозом</th>
                <th colspan="5" class="rightBold">из них пациенты с диагнозом, установленным впервые в жизни</th>
              </tr>
            </msh:tableNotEmpty>            
		
			<msh:tableColumn property="sn" columnName="#"/>
			<msh:tableColumn property="2" columnName="Приоритет"/>
			<msh:tableColumn property="3" columnName="Код МКБ10"/>
			<msh:tableColumn property="4" columnName="Наименование болезни"/>
			<msh:tableColumn property="5" isCalcAmount="true" columnName="Кол-во общее"/>
			<msh:tableColumn property="6" isCalcAmount="true" columnName="из них устан. впервые в жизни"/>
			<msh:tableColumn property="7" isCalcAmount="true" columnName="Кол-во общее"/>
			<msh:tableColumn property="9" isCalcAmount="true" columnName="до 14 лет"/>
			<msh:tableColumn property="11" isCalcAmount="true" columnName="15-17"/>
			<msh:tableColumn property="13" isCalcAmount="true" columnName="труд. возраста (от 18 до 55 жен и 60 муж)"/>
			<msh:tableColumn property="15" isCalcAmount="true" columnName="старше труд. возраста"/>
			<msh:tableColumn property="8" isCalcAmount="true" columnName="Кол-во общее"/>
			<msh:tableColumn property="10" isCalcAmount="true" columnName="до 14 лет"/>
			<msh:tableColumn property="12" isCalcAmount="true" columnName="15-17"/>
			<msh:tableColumn property="14" isCalcAmount="true" columnName="труд. возраста (от 18 до 55 жен и 60 муж)"/>
			<msh:tableColumn property="16" isCalcAmount="true" columnName="старше труд. возраста"/>
			
		</msh:table>
		<%} %>
    <% }
    	} else { %>
    	<i>Выберите параметры и нажмите найти </i>
    <% }   %>
    
    <script type='text/javascript'>
    //var typePatient = document.forms[0].typePatient ;
    //var typeDate = document.forms[0].typeDate ;
    //checkFieldUpdate('typeSwod','${typeSwod}',2,1) ;
    //checkFieldUpdate('typeDate','${typeDate}',2) ;
    checkFieldUpdate('typeMKB','${typeMKB}',4) ;
    checkFieldUpdate('typeEmergency','${typeEmergency}',3) ;
    checkFieldUpdate('typeDtype','${typeDtype}',3) ;
    checkFieldUpdate('typeView','${typeView}',1) ;
    checkFieldUpdate('typeAge','${typeAge}',1) ;
    //checkFieldUpdate('typePatient','${typePatient}',3,3) ;
    //checkFieldUpdate('typeStatus','${typeStatus}',2,2) ;
    
    function checkFieldUpdate(aField,aValue,aDefaultValue) {
       	eval('var chk =  document.forms[0].'+aField) ;
       	var max = chk.length ;
       	aValue=+aValue ;
       	if (aValue==0 || (aValue>max)) {aValue=+aDefaultValue}
       	if (aValue==0 || (aValue>max)) {
       		chk[max-1].checked='checked' ;
       	} else {
       		chk[aValue-1].checked='checked' ;
       	}
    }
    function find() {
    	var frm = document.forms[0] ;
    	frm.target='' ;
    	frm.action='journal_visit_diagnosis.do' ;
    }
    function print() {
    	var frm = document.forms[0] ;
    	frm.target='_blank' ;
    	//frm.action='stac_groupByBedFundList.do' ;
    }
	eventutil.addEventListener($('filterAdd'), eventutil.EVENT_KEY_UP, 
	  		  	function() {$('filterAdd').value = latRus($('filterAdd').value) ;}
  		) ;
		function latRus(aText) {
  			aText=aText.toUpperCase() ;
  			aText=replaceAll(aText,"Й", "Q" ,1) ;
  			aText=replaceAll(aText,"Ц", "W" ,1) ;
  	    	aText=replaceAll(aText,"У","E"  ,1) ;
  	    	aText=replaceAll(aText, "К", "R" ,1) ;
  	    	aText=replaceAll(aText,"Е", "T"  ,1) ;
  	    	aText=replaceAll(aText, "Ф","A" ,1) ;
  	    	aText=replaceAll(aText, "Ы", "S",1) ;
  	    	aText=replaceAll(aText,"В", "D" ,1 ) ;
  	    	aText=replaceAll(aText,"А","F" ,1) ;
  	    	aText=replaceAll(aText,"П","G"  ,1) ;
  	    	aText=replaceAll(aText,"Я","Z" ,1 ) ;
  	    	aText=replaceAll(aText,"Ч","X"  ,1) ;
  	    	aText=replaceAll(aText,"С","C" ,1 ) ;
  	    	aText=replaceAll(aText, "М", "V" ,1) ;
  	    	aText=replaceAll(aText,"И", "B",1 ) ;
  	    	aText=replaceAll(aText,"Н", "Y" ,1 ) ;
  	    	aText=replaceAll(aText,"Г", "U" ,1 ) ;
  	    	aText=replaceAll(aText,"Ш", "I" ,1 ) ;
  	    	aText=replaceAll(aText,"Щ", "O" ,1 ) ;
  	    	aText=replaceAll(aText,"З","P",1 ) ;
  	    	aText=replaceAll(aText, "Р","H",1 ) ;
  	    	aText=replaceAll(aText,"О", "J" ,1 ) ;
  	    	aText=replaceAll(aText,"Л","K" ,1 ) ;
  	    	aText=replaceAll(aText,"Д", "L",1 ) ;
  	    	aText=replaceAll(aText,"Т","N" ,1) ;
  	    	aText=replaceAll(aText, "Ь","M",1 ) ;
  	    	aText=replaceAll(aText, "Ю",".",1 ) ;
  	    	aText=replaceAll(aText, "Б","," ,1) ;
  	    	aText=replaceAll(aText, "Х","[" ,1) ;
  	    	aText=replaceAll(aText, "Ъ","]",1 ) ;
  	    	aText=replaceAll(aText, "Ж",";",1 ) ;
  	    	aText=replaceAll(aText, "Э","'",1 ) ;
  	    	return aText ;
  		}
  		function replaceAll(aText,aSymbRep,aSymbIs,aRedict) {
  			var sym1=aSymbRep,sym2=aSymbIs;
  			if (+aRedict<1) {
  				sym2=aSymbRep,sym1=aSymbIs;
  			} 
  			while (aText.indexOf(sym1)>-1) {
  				aText = aText.replace(sym1,sym2) ;
  			}
  			return aText ;
  		}
    </script>
  </tiles:put>
</tiles:insert>