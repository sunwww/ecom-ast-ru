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
        </msh:row>
        <%-- <msh:row guid="7d80be13-710c-46b8-8503-ce0413686b69">
        <td class="label" title="Поиск по дате  (typeDate)" colspan="1"><label for="typeDateName" id="typeDateLabel">Искать по дате:</label></td>
        <td onclick="this.childNodes[1].checked='checked';">
        	<input type="radio" name="typeDate" value="1">  поступления
        </td>
        <td onclick="this.childNodes[1].checked='checked';">
        	<input type="radio" name="typeDate" value="2">  выписки
        </td>
        </msh:row> --%>
        
        <!-- Свод по отделениям/протоколам -->
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

<%} %>
 <ecom:webQuery name="diag_typeReg_vip_sql"	 nativeSql="select id,name from VocDiagnosisRegistrationType where code='3'"/>
 <ecom:webQuery name="diag_typeReg_klin_sql" nativeSql="select id,name from VocDiagnosisRegistrationType where code='4'"/>
 <ecom:webQuery name="diag_mainPriority_sql" nativeSql="select id,name from VocPrimaryDiagnosis where code='1'"/>
 
  <%
  String isReestr = request.getParameter("reestr");
    //String department = (String) request.getParameter("department");
  //String typeDate=ActionUtil.updateParameter("Report14","typeDate","2", request) ;
  	
  	String department = (String)request.getParameter("department") ;
    	if (department!=null && !department.equals("") && !department.equals("0")) {
    		request.setAttribute("departmentSql", " and vkp.id = "+department) ;
    	} 
    	String fldSearch = "" ;
    	String groupBySql = "" ;
    	if (typeSearch!=null) {
    		if (typeSearch.equals("1")) {
    			fldSearch=" ORDER BY depName" ;
    			groupBySql = "'&depIdParam='||case when dep.isnoomc='1' then depPrev.id else dep.id end, case when dep.isnoomc='1' then depPrev.name else dep.name end ";
    			//groupBySql = "'&dep='||case when dep.isnoomc='1' then depPrev.id else dep.id end as depId, case when dep.isnoomc='1' then depPrev.name else dep.name end ";
    		} else if (typeSearch.equals("2")) {
    			fldSearch = " ORDER BY vkp.name" ;
    			groupBySql = "'&profile='||vkp.id, vkp.name";
    		} else if (typeSearch.equals("3")) {
    			fldSearch = " ORDER BY vkp.name" ;
    			groupBySql = "vkp.id , '&protocol='||vkp.name"; //as depId
    		}
    	}
    	request.setAttribute("fldSearch",fldSearch) ;
    	request.setAttribute("groupBySql",groupBySql) ;

    	String fldDate = "sls.dateFinish" ;
    	/* if (typeDate!=null) {
    		if (typeDate.equals("1")) {fldDate="sls.dateStart" ;} 
    		else if (typeDate.equals("2")) {fldDate = "sls.dateFinish" ;}
    	} */
    	request.setAttribute("fldDate",fldDate) ;
    //profileFilter += request.getParameter("department");
	String startDate = (String) request.getParameter("dateBegin"); //Получаем значение текстового поля с первой датой
	String finishDate = (String) request.getParameter("dateEnd");	//Получаем значение текстового поля со второй датой
	request.setAttribute("dateBegin", startDate);
	request.setAttribute("dateEnd", finishDate);
  //String clinicalMkbName = "ml.name || '<br> '|| mkb2.code||' ' ||mkb2.name";
	String addSql = "";
	
  if (startDate!=null&&!startDate.equals("")) {
	  if (finishDate!=null&&!finishDate.equals("")) {
		  addSql+="between to_date('"+startDate+"','dd.MM.yyyy') and to_date('"+finishDate+"','dd.MM.yyyy')";
	  } else {
		  addSql += " and >=to_date('"+startDate+"','dd.MM.yyyy')";
	  }
  } else {
	  addSql +="between current_date and current_date";
  }
	request.setAttribute("addSql", addSql);
//	out.print("<H1>"+rees)
	if (isReestr!=null&&isReestr.equals("1")) {
		String addParam = request.getParameter("addParam");
		if (addParam!=null&&addParam.equals("1")) {
			addParam = " and dc.id is not null";
		} else if (addParam!=null&&addParam.equals("2")) {
			addParam = " and pk.id is not null";
		}
	
	String id = request.getParameter("id");
	String isParamNull;
	if (id==null|| id.equals("null"))	{
		isParamNull = "IS ";
	} else {
		isParamNull = " = ";
		
	}
	request.setAttribute("isParamNull", isParamNull);
	request.setAttribute("addParam", addParam!=null?addParam:"");
		%> 
	<form action="print-KiliProtocol.do" method="post" target="_blank">
    Период с ${dateBegin} по ${dateEnd}.
    <input type='hidden' name="s" id="s" value="HospitalPrintService">
    <input type='hidden' name="protocolNumber" id="protocolNumber" value="${param.protocolNumber}">
    <input type='hidden' name="protocolDate" id="protocolDate" value="${param.protocolDate}">
    <input type='hidden' name="m" id="m" value="printKiliProtocol">
    <input type="submit" value="Печать"> 
    </form>
		<%
	}
	if ((isReestr==null||!isReestr.equals("1"))&&  typeSearch.equals("1")||typeSearch.equals("2")||typeSearch.equals("3")){
 %>
 
    <ecom:webQuery name="datelist" nameFldSql="datelist_sql" nativeSql="    
SELECT ${groupBySql} as depName, COUNT(sls.id) as cnt_sls, COUNT(dc.id) as cnt_dc, count(pk.id) as cnt_pk 
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
WHERE sls.dtype='HospitalMedCase' and ${fldDate} ${addSql} and slo.dtype='DepartmentMedCase'  and vhr.code='11' 
and sls.deniedhospitalizating_id is null
${departmentSql}
GROUP BY ${groupBySql}
 ${fldSearch}
" guid="ac83420f-43a0-4ede-b576-394b4395a23a" />



   
<%
	}
	if (isReestr==null||!isReestr.equals("1")) {
	if (typeSearch.equals("1")){
%>
        <msh:section>
    <msh:sectionContent>
    <msh:table name="datelist" idField="1" cellFunction="true" action="protocolReport.do?short=Short&reestr=1&dateBegin=${dateBegin}&dateEnd=${dateEnd}" guid="d579127c-69a0-4eca-b3e3-950381d1585c">
      <msh:tableColumn columnName="Наименование отделения" property="2" guid="fc26523a-eb9c-44bc-b12e-42cb7ca9ac5b" />
      <msh:tableColumn columnName="Умерших всего" property="3" guid="e98f73b5-8b9e-4a3e-966f-4d43576bbc96" />
      <msh:tableColumn columnName="Случаев смерти" property="4" guid="e98f73b5-8b9e-4a3e-966f-4d43576bbc96" addParam="&addParam=1" />
      <msh:tableColumn columnName="Протоколов КИЛИ" property="5" guid="e98f73b5-8b9e-4a3e-966f-4d43576bbc96" addParam="&addParam=2"/>
      <msh:tableColumn columnName="% от умерших" property="6" guid="e98f73b5-8b9e-4a3e-966f-4d43576bbc96" />
      <msh:tableColumn columnName="% от оформленных" property="7" guid="e98f73b5-8b9e-4a3e-966f-4d43576bbc96" />
    </msh:table>
    </msh:sectionContent>
    </msh:section>
  <%}
 else if (typeSearch.equals("2")){
	  %> 
	  <msh:table name="datelist" idField="1" cellFunction="true" action="protocolReport.do?short=Short&reestr=1&dateBegin=${dateBegin}&dateEnd=${dateEnd}" guid="d579127c-69a0-4eca-b3e3-950381d1585c">
      <msh:tableColumn columnName="Наименование профиля" property="2" guid="fc26523a-eb9c-44bc-b12e-42cb7ca9ac5b" />
      <msh:tableColumn columnName="Умерших всего" property="3" guid="e98f73b5-8b9e-4a3e-966f-4d43576bbc96" />
      <msh:tableColumn columnName="Случаев смерти" property="4" guid="e98f73b5-8b9e-4a3e-966f-4d43576bbc96" addParam="&addParam=1" />
      <msh:tableColumn columnName="Протоколов КИЛИ" property="5" guid="e98f73b5-8b9e-4a3e-966f-4d43576bbc96" addParam="&addParam=2"/>
      <msh:tableColumn columnName="% от умерших" property="6" guid="e98f73b5-8b9e-4a3e-966f-4d43576bbc96" />
      <msh:tableColumn columnName="% от оформленных" property="7" guid="e98f73b5-8b9e-4a3e-966f-4d43576bbc96" />
    </msh:table>
		  <%
  } else if (typeSearch.equals("3")){
	%>  	
	<ecom:webQuery name="showProfile" nameFldSql="showProfile_sql" nativeSql="
select
pk.protocolnumber
,'№'||pk.protocolnumber|| ' от '||to_char(pk.protocoldate,'dd.MM.yyyy')
,pk.protocolDate
,count(pat.id)
,vkp.name
,'&protocolNumber='||pk.protocolnumber||'&protocolDate='||to_char(pk.protocoldate,'dd.MM.yyyy') as fldId
,'&profileName='||vkp.name as profileName
from protocolKili pk 
left join deathcase dth on dth.id = pk.deathcase_id
left join medcase med on med.id = dth.medcase_id
left join patient pat on med.patient_id = pat.id
left join mislpu dep on dep.id=med.department_id
left join vockiliprofile vkp on vkp.id= dep.kiliprofile_id
where pk.protocolDate ${addSql}
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
	  String profileName = "";
	  String sqlJoin = "";
	  String sqlWhere = "";
	  String value = "";
	  String addDate = "";
	  String addSelect = "";
	  String addFrom = "";
	  
			  if (typeSearch.equals("1")) {
				 addSelect = "SELECT pat.id, pat.lastname||' '||pat.firstname||' '||pat.middlename, 'Протокол №'||pk.protocolNumber||' от '||pk.protocoldate as protocolField ";
				 addFrom = " FROM medcase sls "+
					"left join medcase slo on slo.parent_id=sls.id and slo.datefinish is not null "+ 
					"left join mislpu dep on dep.id=slo.department_id "+ 
					"left join medcase sloPrev on sloPrev.id=slo.prevmedcase_id "+ 
					"left join mislpu depPrev on depPrev.id=sloPrev.department_id "+
					"left join deathcase dc on dc.medcase_id=sls.id "+
					"left join protocolkili pk on pk.deathcase_id=dc.id "+ 
					"left join vockiliprofile vkp on vkp.id=case when dep.isnoomc='1' then depPrev.kiliprofile_id else dep.kiliprofile_id end "+ 
					"left join vochospitalizationresult vhr on vhr.id=sls.result_id "+
					"left join patient pat on sls.patient_id = pat.id ";
				  value = request.getParameter("depIdParam");
				  sqlWhere = " where vhr.code='11' "+ 
						  "AND sls.deniedhospitalizating_id is null "+
						  "AND slo.dtype='DepartmentMedCase' "+
						  "and case when dep.isnoomc='1' then depPrev.id = '" + value +"' else dep.id = '" + value +"' end ";
				  addDate = " and sls.dateFinish ";
			  }
			  if (typeSearch.equals("2")) {
				  addSelect = "SELECT pat.id, pat.lastname||' '||pat.firstname||' '||pat.middlename, 'Протокол №'||pk.protocolNumber||' от '||pk.protocoldate as protocolField ";
				  addFrom = " from medcase sls "+
				  		"left join medcase slo on slo.parent_id=sls.id and slo.datefinish is not null "+ 
						  "left join mislpu dep on dep.id=slo.department_id "+ 
						  "left join medcase sloPrev on sloPrev.id=slo.prevmedcase_id "+ 
						  "left join mislpu depPrev on depPrev.id=sloPrev.department_id "+
						  "left join deathcase dc on dc.medcase_id=sls.id "+
						  "left join protocolkili pk on pk.deathcase_id=dc.id "+ 
						  "left join vockiliprofile vkp on vkp.id=case when dep.isnoomc='1' then depPrev.kiliprofile_id else dep.kiliprofile_id end "+ 
						  "left join vochospitalizationresult vhr on vhr.id=sls.result_id "+
						  "left join patient pat on sls.patient_id = pat.id ";
				  value = request.getParameter("profile");
				  sqlWhere = " where vhr.code='11' "+ 
						  "AND sls.deniedhospitalizating_id is null "+
						  "AND slo.dtype='DepartmentMedCase' "+
						  "and vkp.id = '" + value + "' ";
				  addDate = " and sls.dateFinish ";
			  }
			  if (typeSearch.equals("3")) {
					addSelect = "select pat.id, pat.lastname||' '||pat.firstname||' '||pat.middlename as patName, '№ '||pk.protocolNumber||' от '||pk.protocoldate as info ";
					addFrom = " from protocolKili pk "+
							"left join deathcase dth on dth.id = pk.deathcase_id "+
							"left join medcase sls on sls.id = dth.medcase_id "+
							"left join patient pat on pat.id =  sls.patient_id";
					value = request.getParameter("protocolNumber");
					sqlWhere = " where pk.protocolNumber = '" + value + "'";
					addDate = " and pk.protocoldate ";
			  }
	  request.setAttribute("addSelect", addSelect);
	  request.setAttribute("sqlJoin", sqlJoin);
	  request.setAttribute("sqlWhere", sqlWhere);
	  request.setAttribute("value", value);
	  request.setAttribute("addFrom", addFrom);
	  
	  if (startDate!=null&&!startDate.equals("")) {
		  if (finishDate!=null&&!finishDate.equals("")) {
			  addDate+="between to_date('"+startDate+"','dd.MM.yyyy') and to_date('"+finishDate+"','dd.MM.yyyy')";
		  } else {
			  addDate += " and >=to_date('"+startDate+"','dd.MM.yyyy')";
		  }
	  } else {
		  addDate +="between current_date and current_date";
	  }
		request.setAttribute("addDate", addDate);
%>
<ecom:webQuery name="calc_reestr" nameFldSql="calc_reestr_sql" nativeSql="
${addSelect} 
${addFrom} 
${sqlJoin}
${sqlWhere}  ${addDate}


"/>

 <msh:section>
    <msh:sectionContent>
 <msh:table name="calc_reestr"
     action="entityView-mis_patient.do" idField="1">
      <msh:tableColumn columnName="Пациент" property="2" />
      <msh:tableColumn columnName="Номер протокола" property="3" />
    </msh:table>
    </msh:sectionContent>
    </msh:section> 
    
<%
  }%>


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