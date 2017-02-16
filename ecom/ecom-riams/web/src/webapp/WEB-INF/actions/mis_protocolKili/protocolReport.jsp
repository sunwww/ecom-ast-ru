<%@ page import="ru.ecom.web.util.ActionUtil"%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://struts.apache.org/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://www.nuzmsh.ru/tags/msh" prefix="msh" %>
<%@ taglib uri="http://www.ecom-ast.ru/tags/ecom" prefix="ecom" %>
<%@ taglib tagdir="/WEB-INF/tags" prefix="tags" %>

<tiles:insert page="/WEB-INF/tiles/main${param.short}Layout.jsp" flush="true" >

  <tiles:put name="title" type="string">
    <msh:title mainMenu="StacJournal" guid="f6e72e89-0ba7-4f9e-97f6-0a1ecaf5b162">Отчет по КИЛИ</msh:title>
  </tiles:put>
  <tiles:put name="side" type="string">
    <msh:sideMenu guid="677746d8-d63e-44a3-8e8b-e227dea8decb">
      <msh:sideLink roles="/Policy/MedCase" key="ALT+N" action="/" name="По отделению" guid="b6f99225-3f13-4e39-91a4-3b371f8dce53" />
    </msh:sideMenu>
  </tiles:put>
  <tiles:put name="body" type="string">
  <%
  String shor = request.getParameter("short");
  
  String typeDate = ActionUtil.updateParameter("GroupByBedFund","typeDate","2", request);
  String typeSearch = ActionUtil.updateParameter("GroupByBedFund", "typeSearch", "2", request);
  if (shor==null|| shor.equals("")){
  %>
     <msh:form action="/protocolReport.do" defaultField="dateBegin" disableFormDataConfirm="true" method="GET" guid="d7b31bc2-38f0-42cc-8d6d-19395273168f">
     <msh:panel guid="6ae283c8-7035-450a-8eb4-6f0f7da8a8ff">
      <msh:row guid="53627d05-8914-48a0-b2ec-792eba5b07d9">
        <msh:separator label="Параметры поиска" colSpan="7" />
        </msh:row><msh:row>
        <msh:textField property="dateBegin" label="Период с" />
        <msh:textField property="dateEnd" label="по" />
        </msh:row><msh:row>
        <msh:autoComplete property="department" fieldColSpan="5"
        	label="Профиль" horizontalFill="true" vocName="vocKiliProfile"/>
        </msh:row>  <!-- Свод по отделениям/протоколам -->
        <msh:row guid="7d80be13-710c-46b8-8503-ce0413686b69">
        <td class="label" title="Поиск по дате  (typeSearch)" colspan="1"><label for="typeSearchName" id="typeSearchLabel">Отобразить:</label></td>
        <td onclick="this.childNodes[1].checked='checked';">
        	<input type="radio" name="typeSearch" value="1">  свод по отделениям
        </td>
        <td onclick="this.childNodes[1].checked='checked';">
        	<input type="radio" name="typeSearch" value="2">  свод по профилям
        </td>
        <td onclick="this.childNodes[1].checked='checked';">
        	<input type="radio" name="typeSearch" value="3">  свод по протоколам
        </td>
        </msh:row>

        <msh:row>
            <td><input type="submit" value="Найти" /></td>
      </msh:row>
          </msh:panel>
</msh:form>

<%}
  
  String isReestr = request.getParameter("reestr");
  	String addSql = "";
  	String profile = (String)request.getParameter("department") ; //Это не отделение, как можно подумать, а профиль КИЛИ
  	String department = request.getParameter("dep") ;
  	String startDate = (String) request.getParameter("dateBegin");
	String finishDate = (String) request.getParameter("dateEnd");
	if (startDate!=null&&!startDate.equals("")) {
	request.setAttribute("dateBegin", startDate);
	request.setAttribute("dateEnd", finishDate);
    	if (profile!=null && !profile.equals("") && !profile.equals("0")) {
    		addSql+=" and vkp.id = "+profile ;
    	} 
    	String orderBySql = "" ;
    	String groupBySql = "" ;
    	String selectSql = "" ;
    	String typeDateSql="sls.dateFinish";
    	String tableName = "";
    	if (typeSearch!=null) {
    		if (typeSearch.equals("1")) {
    			tableName = "Наименование отделения";
    			orderBySql=" ORDER BY depName" ;
    			groupBySql = "case when dep.isnoomc='1' then depPrev.id else dep.id end, case when dep.isnoomc='1' then depPrev.name else dep.name end ";
    			selectSql = "'&dep='||case when dep.isnoomc='1' then depPrev.id else dep.id end, case when dep.isnoomc='1' then depPrev.name else dep.name end ";
    		} else if (typeSearch.equals("2")) { //поиск по
    			tableName = "Наименование профиля";
    			orderBySql = " ORDER BY vkp.name" ;
    			groupBySql = "vkp.id, vkp.name";
    			selectSql = "'&profile='||coalesce(vkp.id,0), coalesce(vkp.name,'РЕАНИМАЦИЯ?')";    			
    		} else if (typeSearch.equals("3")) {
    			orderBySql = " ORDER BY vkp.name" ;
    			groupBySql = "vkp.id , vkp.name";
    			selectSql = "'&profile='||vkp.id||'&protocolNumber='||pk.protocolNumber||'&protocolDate='||to_char(pk.protocolDate,'dd.MM.yyyy') , '&protocol='||vkp.name";
    			typeDateSql="pk.protocolDate";
    		}
    	}
    	request.setAttribute("tableName", tableName);
    	request.setAttribute("orderBySql",orderBySql) ;
    	request.setAttribute("groupBySql",groupBySql) ;
    	request.setAttribute("selectSql",selectSql) ;
		
	
	
	
  if (startDate!=null&&!startDate.equals("")) {
	  if (addSql.length()==0) {
		  addSql+=" and ";
	  }
	  addSql+=typeDateSql;
	  if (finishDate==null||finishDate.equals("")) {
		  finishDate = startDate;
	  }
	  addSql+=" between to_date('"+startDate+"','dd.MM.yyyy') and to_date('"+finishDate+"','dd.MM.yyyy')";
	  
  } 
	request.setAttribute("addSql", addSql);
//	out.print("<H1>"+rees)

	if (isReestr==null||isReestr.equals("")||isReestr.equals("0")){
 
		if (typeSearch.equals("1")||typeSearch.equals("2")){
%>
 <ecom:webQuery name="datelist" nameFldSql="datelist_sql" nativeSql="    
SELECT ${selectSql} as depName, COUNT(sls.id) as cnt_sls, COUNT(dc.id) as cnt_dc, count(pk.id) as cnt_pk 
,count(pk.id)*100/ count(sls.id)  as persDead
,case when count(dc.id)>0 then count(pk.id)*100/ count(dc.id) else 0 end  as persCase

from medcase sls
left join medcase slo on slo.parent_id=sls.id and slo.datefinish is not null
left join mislpu dep on dep.id=slo.department_id
left join medcase sloPrev on sloPrev.id=slo.prevmedcase_id
left join mislpu depPrev on depPrev.id=sloPrev.department_id

left join deathcase dc on dc.medcase_id=sls.id
	left join protocolkili pk on pk.deathcase_id=dc.id
	left join vockiliprofile vkp on vkp.id=case when dep.isnoomc='1' then depPrev.kiliprofile_id else dep.kiliprofile_id end
left join vochospitalizationresult vhr on vhr.id=sls.result_id
WHERE sls.dtype='HospitalMedCase' ${addSql} and slo.dtype='DepartmentMedCase'  and vhr.code='11' 
and sls.deniedhospitalizating_id is null

GROUP BY ${groupBySql}
 ${orderBySql}
" guid="ac83420f-43a0-4ede-b576-394b4395a23a" />
        <msh:section>
    <msh:sectionContent>
    <msh:table name="datelist" idField="1" cellFunction="true" action="protocolReport.do?short=Short&reestr=1&dateBegin=${dateBegin}&dateEnd=${dateEnd}" guid="d579127c-69a0-4eca-b3e3-950381d1585c">
      <msh:tableColumn columnName="${tableName}" property="2" guid="fc26523a-eb9c-44bc-b12e-42cb7ca9ac5b" />
      <msh:tableColumn columnName="Умерших всего" property="3" guid="e98f73b5-8b9e-4a3e-966f-4d43576bbc96" />
      <msh:tableColumn columnName="Случаев смерти" property="4" guid="e98f73b5-8b9e-4a3e-966f-4d43576bbc96" addParam="&addParam=1" />
      <msh:tableColumn columnName="Протоколов КИЛИ" property="5" guid="e98f73b5-8b9e-4a3e-966f-4d43576bbc96" addParam="&addParam=2"/>
      <msh:tableColumn columnName="% от умерших" property="6" guid="e98f73b5-8b9e-4a3e-966f-4d43576bbc96" />
      <msh:tableColumn columnName="% от оформленных" property="7" guid="e98f73b5-8b9e-4a3e-966f-4d43576bbc96" />
    </msh:table>
    </msh:sectionContent>
    </msh:section>
  <%} else if (typeSearch.equals("3")){	  
	%>  	
	<ecom:webQuery name="showProfile" nameFldSql="showProfile_sql" nativeSql="
select
pk.protocolnumber
,'№'||pk.protocolnumber|| ' от '||to_char(pk.protocoldate,'dd.MM.yyyy') as f2
,pk.protocolDate
,count(pat.id) as cntPat
,coalesce(vkp.name,'Не определен профиль') as profileName
,'&protocolNumber='||pk.protocolnumber||'&protocolDate='||to_char(pk.protocoldate,'dd.MM.yyyy')||'&profile='||'' as fldId
,'&profileName='||vkp.name as profileName
from protocolKili pk 
left join deathcase dth on dth.id = pk.deathcase_id
left join medcase sls on sls.id = dth.medcase_id
left join medcase slo on slo.parent_id=sls.id and slo.dtype='DepartmentMedCase' and slo.transferdate is null
left join medcase sloPrev on sloPrev.id=slo.prevmedcase_id 
left join patient pat on sls.patient_id = pat.id
left join mislpu dep on dep.id=slo.department_id
left join mislpu depPrev on depPrev.id=sloPrev.department_id
left join vockiliprofile vkp on vkp.id= case when dep.isnoomc='1' then depPrev.kiliprofile_id else dep.kiliprofile_id end
where pk.id is not null ${addSql}
group by pk.protocolNumber, pk.protocolDate, vkp.id
ORDER BY cast(pk.protocolNumber as int)
"/>

	  <msh:section>
    <msh:sectionContent>
    <msh:table cellFunction="true" name="showProfile" idField="6" noDataMessage="Не найдено" 
    action="protocolReport.do?typeSearch=3&reestr=1&dateBegin=${dateBegin}&dateEnd=${dateEnd}" guid="d579127c-69a0-4eca-b3e3-950381d1585c"> 
      <msh:tableColumn columnName="Протокол КИЛИ" property="2" guid="fc26523a-eb9c-44bc-b12e-42cb7ca9ac5b" />
      <msh:tableColumn columnName="Дата протокола" property="3" guid="e98f73b5-8b9e-4a3e-966f-4d43576bbc96" />
      <msh:tableColumn columnName="Пациентов" property="4" guid="e98f73b5-8b9e-4a3e-966f-4d43576bbc96" />
      <msh:tableColumn columnName="Профиль" property="5" guid="e98f73b5-8b9e-4a3e-966f-4d43576bbc96" />
    </msh:table>
    </msh:sectionContent>
    </msh:section>
  <%}
	}  else if (isReestr.equals("1")){
		String printS = "HospitalPrintService";
		String printM = "printKiliProtocol";
		String printFileName = "KiliProtocol";
		String sqlAdd = "";
		profile = request.getParameter("profile");
		String addParam = request.getParameter("addParam");
	if (addParam!=null&&addParam.equals("1")) {
		sqlAdd += " and dc.id is not null";
	} else if (addParam!=null&&addParam.equals("2")) {
		sqlAdd += " and pk.id is not null";
	}
	
	//request.setAttribute("addParam", addParam!=null?addParam:"");
	
	String protocolDate = request.getParameter("protocolDate");
	String protocolNumber = request.getParameter("protocolNumber");
	
	if (typeSearch!=null&&typeSearch.equals("3")) { //Поиск по протоколу
	
	} else {	  
			sqlAdd+=" and sls.dateFinish between to_date('"+startDate+"','dd.MM.yyyy') and to_date('"+finishDate+"','dd.MM.yyyy')";
			printS = "PrintService";
			printM = "printNativeQuery";
			printFileName = "kiliReport";
	}
	
	if (protocolDate!=null&&!protocolDate.equals("")) {
		sqlAdd += " and pk.protocolDate = to_date('"+protocolDate+"','dd.MM.yyyy')";
	}
	if (protocolNumber!=null&&!protocolNumber.equals("")) {
	  sqlAdd +=" and pk.protocolNumber='"+protocolNumber+"'";
	}
	if (profile!=null&&!profile.equals("")) {
		if (profile.equals("0")) {
			sqlAdd +=" and vkp.id is null";
		} else {
			sqlAdd +=" and vkp.id="+profile;
		}
	   
	} 
	  if (department!=null&&!department.equals("")) {
		  sqlAdd +=" and case when dep.isnoomc='1' then depPrev.id else dep.id end = '"+department+"'";
	  }	
	  
		request.setAttribute("sqlAdd", sqlAdd);
		request.setAttribute("printM", printM);
		request.setAttribute("printS", printS);
		request.setAttribute("printFileName", printFileName);
%>
<ecom:webQuery name="calc_reestr" nameFldSql="calc_reestr_sql" nativeSql="
SELECT pat.id, pat.lastname||' '||pat.firstname||' '||pat.middlename||' '||to_char(pat.birthday,'dd.MM.yyyy') as f1_fio, 'Протокол №'||pk.protocolNumber||' от '||to_char(pk.protocoldate,'dd.MM.yyyy') as protocolField, sts.code
, to_char(sls.dateFinish,'dd.MM.yyyy') as dateFinish 
  FROM medcase sls 
	left join medcase slo on slo.parent_id=sls.id and slo.datefinish is not null and slo.dtype='DepartmentMedCase'  
	left join mislpu dep on dep.id=slo.department_id 
	left join medcase sloPrev on sloPrev.id=slo.prevmedcase_id 
	left join mislpu depPrev on depPrev.id=sloPrev.department_id
	left join deathcase dc on dc.medcase_id=sls.id
	left join protocolkili pk on pk.deathcase_id=dc.id 
	left join vockiliprofile vkp on vkp.id=case when dep.isnoomc='1' then depPrev.kiliprofile_id else dep.kiliprofile_id end 
	left join vochospitalizationresult vhr on vhr.id=sls.result_id
	left join patient pat on sls.patient_id = pat.id
	left join statisticstub sts on sls.id = sts.medcase_id
   where vhr.code='11' AND sls.deniedhospitalizating_id is null 
		  ${sqlAdd}
"/>${calc_reestr_sql}

<form action="print-${printFileName}.do" method="post" target="_blank">
    Период с ${dateBegin} по ${dateEnd}.
    <input type='hidden' name="s" id="s" value="${printS}">
    <input type='hidden' name="sqlText" id="sqlText" value="${calc_reestr_sql}">
    <input type='hidden' name="protocolNumber" id="protocolNumber" value="${param.protocolNumber}">
    <input type='hidden' name="protocolDate" id="protocolDate" value="${param.protocolDate}">
    <input type='hidden' name="m" id="m" value="${printM}">
    <input type="submit" value="Печать"> 
    </form>

 <msh:section>
    <msh:sectionContent> 
 <msh:table name="calc_reestr"
     action="entityView-mis_patient.do" idField="1">
      <msh:tableColumn columnName="Пациент" property="2" />
      <msh:tableColumn columnName="Номер протокола" property="3" />
      <msh:tableColumn columnName="Номер истории" property="4" />
      <msh:tableColumn columnName="Дата смерти" property="5" />
    </msh:table>
    </msh:sectionContent>
    </msh:section> 
    
<% } 
	} else {
		out.print("Выберите период");
	}
%>

  <script type='text/javascript'>
  checkFieldUpdate('typeSearch','${typeSearch}',1) ;

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
</script>    
</tiles:put>



</tiles:insert> 