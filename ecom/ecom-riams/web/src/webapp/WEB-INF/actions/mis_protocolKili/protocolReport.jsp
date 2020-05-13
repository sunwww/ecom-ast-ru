<%@ page import="ru.ecom.web.util.ActionUtil"%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://struts.apache.org/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://www.nuzmsh.ru/tags/msh" prefix="msh" %>
<%@ taglib uri="http://www.ecom-ast.ru/tags/ecom" prefix="ecom" %>
<%@ taglib tagdir="/WEB-INF/tags" prefix="tags" %>

<tiles:insert page="/WEB-INF/tiles/main${param.short}Layout.jsp" flush="true" >

  <tiles:put name="title" type="string">
    <msh:title mainMenu="StacJournal">Отчет по КИЛИ</msh:title>
  </tiles:put>
  <tiles:put name="side" type="string">
    <msh:sideMenu>
      <msh:sideLink roles="/Policy/MedCase" key="ALT+N" action="/" name="По отделению" />
    </msh:sideMenu>
  </tiles:put>
  <tiles:put name="body" type="string">
  <%
  String shor = request.getParameter("short");
  String typeSearch = ActionUtil.updateParameter("GroupByBedFund", "typeSearch", "2", request);
	  if (shor==null|| shor.equals("")){
  %>
	  <msh:form action="/protocolReport.do" defaultField="dateBegin" disableFormDataConfirm="true" method="GET">
		  <msh:panel>
			  <msh:row>
				  <msh:separator label="Параметры поиска" colSpan="7" />
			  </msh:row><msh:row>
			  <msh:textField property="dateBegin" label="Период с" />
			  <msh:textField property="dateEnd" label="по" />
		  </msh:row><msh:row>
			  <msh:autoComplete property="department" fieldColSpan="5"
								label="Профиль" horizontalFill="true" vocName="vocKiliProfile"/>
		  </msh:row>
			  <msh:row>
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
	  <%
		  }
	  String profile = (String)request.getParameter("department") ; //Это не отделение, как можно подумать, а профиль КИЛИ
	  if (profile==null || profile.equals("")) profile="0";
	  String department = request.getParameter("dep") ;
	  String startDate = (String) request.getParameter("dateBegin");
	  String finishDate = (String) request.getParameter("dateEnd");
	  if (startDate!=null && !startDate.equals("")) {
		  request.setAttribute("dateBegin", startDate);
		  if (finishDate==null||finishDate.equals("")) finishDate = startDate;
		  request.setAttribute("dateEnd", finishDate);
	  }
	  if (startDate!=null && !startDate.equals("")) {
	      String profSql="";
	      if (profile != null && !profile.equals("")) {
			  if (profile!=null && !profile.equals("") && !profile.equals("0") && !profile.equals("10") && !profile.equals("16")) profSql=" and vkp.id = "+profile ;
		  String sql = "";
		  String tableName = "";
		  if (typeSearch != null && (shor==null|| shor.equals(""))) {
			  if (typeSearch.equals("1") || typeSearch.equals("2")) {
				  sql = "SELECT {header} as depName, COUNT(sls.id) as cnt_sls, COUNT(dc.id) as cnt_dc, count(pk.id) as cnt_pk" +
						  " ,case when count(sls.id)>0 then count(pk.id)*100/ count(sls.id) else 0 end  as persDead" +
						  " ,case when count(dc.id)>0 then count(pk.id)*100/ count(dc.id) else 0 end  as persCase" +
						  " from medcase sls" +
						  " left join medcase slo on slo.parent_id=sls.id and slo.datefinish is not null" +
						  " left join mislpu dep on dep.id=slo.department_id" +
						  " left join medcase sloPrev on sloPrev.id=slo.prevmedcase_id" +
						  " left join mislpu depPrev on depPrev.id=sloPrev.department_id" +
						  " left join deathcase dc on dc.medcase_id=sls.id" +
						  " left join protocolkili pk on pk.deathcase_id=dc.id" +
						  " left join vockiliprofile vkp on vkp.id=case when dep.isnoomc='1' then depPrev.kiliprofile_id else dep.kiliprofile_id end" +
						  " left join vochospitalizationresult vhr on vhr.id=sls.result_id" +
						  " left join Patient p on p.id=sls.patient_id" +
						  " where sls.deniedhospitalizating_id is null" +
						  " and sls.dateFinish between to_date('{dateBegin}','dd.MM.yyyy') and to_date('{dateEnd}','dd.MM.yyyy')" +
						  " {where}" +
						  " {group}" +
						  " {order}";
				  tableName = (typeSearch.equals("1")) ? "Наименование отделения" : "Наименование профиля";
				  if (!profile.equals("10") && !profile.equals("16")) {
					  sql = sql.replace("{where}", " and slo.dtype='DepartmentMedCase' and sls.dtype='HospitalMedCase' " + profSql + " and vhr.code='11' ");
					  sql = (typeSearch.equals("1")) ?
							  sql.replace("{header}", "'&dep='||case when dep.isnoomc='1' then depPrev.id else dep.id end, case when dep.isnoomc='1' then depPrev.name else dep.name end ")
									  .replace("{group}", " group by case when dep.isnoomc='1' then depPrev.id else dep.id end, case when dep.isnoomc='1' then depPrev.name else dep.name end ")
									  .replace("{order}", " order by depName") :
							  sql.replace("{header}", "'&profile='||coalesce(vkp.id,0), coalesce(vkp.name,'РЕАНИМАЦИЯ?')")
									  .replace("{group}", " group by vkp.id, vkp.name ")
									  .replace("{order}", " order by vkp.name");
				  } else if (profile.equals("16")) {
					  String depProf = typeSearch.equals("1") ? "АКУШЕРСКОЕ ОБСЕРВАЦИОННОЕ ОТДЕЛЕНИЕ" : "Акушерский";
					  sql = sql.replace("{group}", "").replace("{order}", "").replace("{header}", "CAST('' AS varchar(1)) as useless,CAST('" + depProf + "' AS varchar(50))").replace("{where}", " and dc.isneonatologic = true ");
				  } else if (profile.equals("10")) {
					  sql = (typeSearch.equals("1")) ?
							  sql.replace("{header}", "'&dep='||case when dep.isnoomc='1' then depPrev.id else dep.id end, case when dep.isnoomc='1' then depPrev.name else dep.name end ")
									  .replace("{group}", " group by case when dep.isnoomc='1' then depPrev.id else dep.id end, case when dep.isnoomc='1' then depPrev.name else dep.name end ")
									  .replace("{order}", " order by depName")
									  .replace("{where}"," and vhr.code='11' and cast(to_char(sls.dateFinish,'yyyy') as int) -cast(to_char(p.birthday,'yyyy') as int) +(case when (cast(to_char(sls.dateFinish, 'mm') as int) -cast(to_char(p.birthday, 'mm') as int) +(case when (cast(to_char(sls.dateFinish,'dd') as int) - cast(to_char(p.birthday,'dd') as int)<0) then -1 else 0 end) <0) then -1 else 0 end) < 1 "):
							  sql.replace("{header}", "'&profile='||coalesce(vkp.id,0), coalesce(vkp.name,'РЕАНИМАЦИЯ?')")
									  .replace("{group}", " group by vkp.id, vkp.name ")
									  .replace("{order}", " order by vkp.name")
									  .replace("{where}"," and vhr.code='11' and cast(to_char(sls.dateFinish,'yyyy') as int) -cast(to_char(p.birthday,'yyyy') as int) +(case when (cast(to_char(sls.dateFinish, 'mm') as int) -cast(to_char(p.birthday, 'mm') as int) +(case when (cast(to_char(sls.dateFinish,'dd') as int) - cast(to_char(p.birthday,'dd') as int)<0) then -1 else 0 end) <0) then -1 else 0 end) < 1 ");

				  }
			  } else if (typeSearch.equals("3")) {
				  if (profile.equals("16")) profSql+=" and dc.isneonatologic = true ";
				  if (profile.equals("10")) profSql+=" and cast(to_char(sls.dateFinish,'yyyy') as int) -cast(to_char(p.birthday,'yyyy') as int) +(case when (cast(to_char(sls.dateFinish, 'mm') as int) -cast(to_char(p.birthday, 'mm') as int) +(case when (cast(to_char(sls.dateFinish,'dd') as int) - cast(to_char(p.birthday,'dd') as int)<0) then -1 else 0 end) <0) then -1 else 0 end) < 1 ";

				  sql = "select f1 as f1,f2 as f2, f3 as f3, sum(cntPat) as cntPat, '&protocolNumber='||f4||'&protocolDate='||f5||'&profile='||'&profileName='||profileName as f5,profileName as justProfName from (" +
						  " select pk.protocolnumber as f1" +
						  " ,'№'||pk.protocolnumber|| ' от '||to_char(pk.protocoldate,'dd.MM.yyyy') as f2" +
						  " ,pk.protocolDate as f3" +
						  " ,count(pat.id) as cntPat" +
						  " ,pk.protocolnumber as f4" +
						  " ,to_char(pk.protocoldate,'dd.MM.yyyy') as f5" +
						  " ,case when dc.isneonatologic=true then cast('Акушерский' as varchar(20)) else case when " +
						  " cast(to_char(sls.dateFinish,'yyyy') as int) -cast(to_char(p.birthday,'yyyy') as int) +(case when (cast(to_char(sls.dateFinish, 'mm') as int) -cast(to_char(p.birthday, 'mm') as int) +(case when (cast(to_char(sls.dateFinish,'dd') as int) - cast(to_char(p.birthday,'dd') as int)<0) then -1 else 0 end) <0) then -1 else 0 end) < 1 " +
						  " then cast('Неонатологический' as varchar(20)) else coalesce(vkp.name,'Не определен профиль') end end" +
						  " as profileName" +
						  " from protocolKili pk" +
						  " left join deathcase dc on dc.id = pk.deathcase_id" +
						  " left join medcase sls on sls.id = dc.medcase_id" +
						  " left join medcase slo on slo.parent_id=sls.id and slo.dtype='DepartmentMedCase' and slo.transferdate is null" +
						  " left join medcase sloPrev on sloPrev.id=slo.prevmedcase_id" +
						  " left join patient pat on sls.patient_id = pat.id" +
						  " left join mislpu dep on dep.id=slo.department_id" +
						  " left join mislpu depPrev on depPrev.id=sloPrev.department_id" +
						  " left join vockiliprofile vkp on vkp.id= case when dep.isnoomc='1' then depPrev.kiliprofile_id else dep.kiliprofile_id end" +
						  " left join Patient p on p.id=sls.patient_id" +
						  " where pk.id is not null and sls.dateFinish between to_date('{dateBegin}','dd.MM.yyyy') and to_date('{dateEnd}','dd.MM.yyyy') " + profSql  +
						  " group by pk.protocolNumber, pk.protocolDate, dc.isneonatologic,sls.dateFinish,p.birthday,vkp.name" +
						  " ) as t" +
						  " group by f1,f2,f3,f4,f5,profileName" +
						  " ORDER BY f3, f1";
			  }
			  sql = sql.replace("{dateBegin}", startDate);
			  sql = sql.replace("{dateEnd}", finishDate);
			  request.setAttribute("tableName", tableName);
			  request.setAttribute("sql", sql);
			  request.setAttribute("dep", profile);
		  }
	  }

		if ((shor==null|| shor.equals("")) && typeSearch!=null && (typeSearch.equals("1") || typeSearch.equals("2"))) {
%>
	  <ecom:webQuery isReportBase="true"  name="datelist" nameFldSql="datelist_sql" nativeSql="${sql}" />
	  <msh:section>
		  <msh:sectionContent>
			  <msh:table name="datelist" idField="1" cellFunction="true" action="protocolReport.do?short=Short&dateBegin=${dateBegin}&dateEnd=${dateEnd}&depprofile=${param.department}">
				  <msh:tableColumn columnName="${tableName}" property="2" />
				  <msh:tableColumn columnName="Умерших всего" property="3" />
				  <msh:tableColumn columnName="Случаев смерти" property="4" addParam="&addParam=1" />
				  <msh:tableColumn columnName="Протоколов КИЛИ" property="5" addParam="&addParam=2"/>
				  <msh:tableColumn columnName="% от умерших" property="6" />
				  <msh:tableColumn columnName="% от оформленных" property="7" />
			  </msh:table>
		  </msh:sectionContent>
	  </msh:section>
	  <%
				  //для вывода акушерских случаев
			  if ((String)request.getParameter("department")==null || (String)request.getParameter("department")=="" || (String)request.getParameter("department")=="0") {
				  String tableName = (typeSearch.equals("1")) ? "Наименование отделения" : "Наименование профиля";
				  String depProf = typeSearch.equals("1") ? "АКУШЕРСКОЕ ОБСЕРВАЦИОННОЕ ОТДЕЛЕНИЕ" : "Акушерский";
				  request.setAttribute("tableName", tableName);
				  request.setAttribute("depProf", depProf);
%>
	  <ecom:webQuery isReportBase="true" name="datelist1" nameFldSql="datelist1_sql" nativeSql="SELECT CAST('' AS varchar(1)) as useless,CAST('${depProf}' AS varchar(50)) as depName, COUNT(sls.id) as cnt_sls, COUNT(dc.id) as cnt_dc, count(pk.id) as cnt_pk
		,case when count(sls.id)>0 then count(pk.id)*100/ count(sls.id) else 0 end  as persDead
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
		left join Patient p on p.id=sls.patient_id
		where sls.deniedhospitalizating_id is null
		and sls.dateFinish between to_date('${dateBegin}','dd.MM.yyyy') and to_date('${dateEnd}','dd.MM.yyyy')
		 and dc.isneonatologic = true " />
	  <msh:section>
		  <msh:sectionContent>
			  <msh:table name="datelist1" idField="1" cellFunction="true" action="protocolReport.do?short=Short&dateBegin=${dateBegin}&dateEnd=${dateEnd}&depprofile=16">
				  <msh:tableColumn columnName="${tableName}" property="2" />
				  <msh:tableColumn columnName="Умерших всего" property="3" />
				  <msh:tableColumn columnName="Случаев смерти" property="4" addParam="&addParam=1" />
				  <msh:tableColumn columnName="Протоколов КИЛИ" property="5" addParam="&addParam=2"/>
				  <msh:tableColumn columnName="% от умерших" property="6" />
				  <msh:tableColumn columnName="% от оформленных" property="7" />
			  </msh:table>
		  </msh:sectionContent>
	  </msh:section>
	  <%
			  }
	      }
	  else if ((shor==null|| shor.equals("")) && typeSearch!=null && typeSearch.equals("3")) {
	  %>
	  <ecom:webQuery isReportBase="true" name="datelist" nameFldSql="datelist_sql" nativeSql="${sql}" />
	  <msh:section>
		  <msh:sectionContent>
			  <msh:table cellFunction="true" name="datelist" idField="5" noDataMessage="Не найдено"
						 action="protocolReport.do?typeSearch=3&short=Short&dateBegin=${dateBegin}&dateEnd=${dateEnd}&depprofile=${param.department}">
				  <msh:tableColumn columnName="Протокол КИЛИ" property="2" />
				  <msh:tableColumn columnName="Дата протокола" property="3" />
				  <msh:tableColumn columnName="Пациентов" property="4" />
				  <msh:tableColumn columnName="Профиль" property="6" />
			  </msh:table>
		  </msh:sectionContent>
	  </msh:section>
	  <%
	  }
		  else if (typeSearch!=null && shor!=null) {
		  String printS="",printM="",printFileName="";
		  String protocolDate = request.getParameter("protocolDate");
		  String protocolNumber = request.getParameter("protocolNumber");
		  if (typeSearch.equals("1") || typeSearch.equals("2")) {
			  printS = "PrintService";
			  printM = "printNativeQuery";
			  printFileName = "kiliReport";
		  }
		  String sqlAdd = "";
		  /*String dep=request.getParameter("dep");
		  if ()*/
		  profile=request.getParameter("depprofile");
		  if (profile==null || profile.equals("") || profile.equals("0")) profile = request.getParameter("profile");
		  String profileName = request.getParameter("profileName");
		  if (profileName!=null && profileName.equals("Акушерский")) profile="16";
		  if (profileName!=null && profileName.equals("Неонатологический")) profile="10";
		  String addParam = request.getParameter("addParam");
		  if (addParam != null && addParam.equals("1")) sqlAdd += " and dc.id is not null";
		  else if (addParam != null && addParam.equals("2")) sqlAdd += " and pk.id is not null";
		  sqlAdd += " and sls.dateFinish between to_date('" + startDate + "','dd.MM.yyyy') and to_date('" + finishDate + "','dd.MM.yyyy')";
		  if (typeSearch.equals("3")) {
			  printS = "HospitalPrintService";
			  printM = "printKiliProtocol";
			  printFileName = "KiliProtocol";
		  }
		  if (protocolDate != null && !protocolDate.equals(""))
			  sqlAdd += " and pk.protocolDate = to_date('" + protocolDate + "','dd.MM.yyyy')";
		  if (protocolNumber != null && !protocolNumber.equals(""))
			  sqlAdd += " and pk.protocolNumber='" + protocolNumber + "'";
		  if (profile != null && !profile.equals("10") && !profile.equals("16") && !profile.equals("") && !profile.equals("0")) {
			  sqlAdd += " and vkp.id=" + profile;
		  }
		  if (profile != null && profile.equals("16")) sqlAdd += " and dc.isneonatologic = " + true;
		  if (profile != null && profile.equals("10"))
		  	sqlAdd+= " and vhr.code='11' and cast(to_char(sls.dateFinish,'yyyy') as int) -cast(to_char(pat.birthday,'yyyy') as int) +(case when (cast(to_char(sls.dateFinish, 'mm') as int) -cast(to_char(pat.birthday, 'mm') as int) +(case when (cast(to_char(sls.dateFinish,'dd') as int) - cast(to_char(pat.birthday,'dd') as int)<0) then -1 else 0 end) <0) then -1 else 0 end) < 1 ";

		  if (department != null && !department.equals(""))
			  sqlAdd += " and case when dep.isnoomc='1' then depPrev.id else dep.id end = '" + department + "'";
		  if (profile!=null) {
		      if (!profile.equals("16") && !profile.equals("10")) sqlAdd += " and vhr.code='11'";
		  }
		  else sqlAdd += " and vhr.code='11'";
		  String dbMother = "В акушерском профиле указывается СЛС матери мертворождённого.";
		  request.setAttribute("sqlAdd", sqlAdd);
		  request.setAttribute("printM", printM);
		  request.setAttribute("printS", printS);
		  request.setAttribute("printFileName", printFileName);
		  request.setAttribute("dbMother", dbMother);
	  %>
	  <ecom:webQuery isReportBase="true" name="calc_reestr" nameFldSql="calc_reestr_sql" nativeSql="
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
   where sls.deniedhospitalizating_id is null
		 ${sqlAdd}
"/>
	  <form action="print-${printFileName}.do" method="post" target="_blank">
			  ${dbMother}
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

	  <%
		  }
		  }
		  else {
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