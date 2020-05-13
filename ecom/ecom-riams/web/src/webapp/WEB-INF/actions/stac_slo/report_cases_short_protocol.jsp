<%@page import="ru.ecom.ejb.services.query.WebQueryResult"%>
<%@page import="ru.ecom.web.login.LoginInfo"%>
<%@page import="ru.nuzmsh.web.tags.helper.RolesHelper"%>
<%@page import="java.util.List"%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://struts.apache.org/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://www.nuzmsh.ru/tags/msh" prefix="msh" %>
<%@ taglib uri="http://www.ecom-ast.ru/tags/ecom" prefix="ecom" %>
<%@ taglib tagdir="/WEB-INF/tags" prefix="tags" %>

<tiles:insert page="/WEB-INF/tiles/main${param.short}Layout.jsp" flush="true" >

  <tiles:put name="title" type="string">
    <msh:title mainMenu="StacJournal">Журнал по коротким дневникам</msh:title>
  </tiles:put>
  <tiles:put name="side" type="string">
  	<tags:stac_journal currentAction="stac_journalByCurator"/>
  </tiles:put>
  <tiles:put name="body" type="string">    
        <%
        	String login = LoginInfo.find(request.getSession(true)).getUsername() ;
        	request.setAttribute("login", login) ;
        %>
        <ecom:webQuery name="infoByLogin"
        maxResult="1" nativeSql="
        select wf.id,w.lpu_id,case when wf.isAdministrator='1' then '1' else null end as isAdmin
        from SecUser su
        left join WorkFunction wf on su.id=wf.secUSer_id
        left join Worker w on wf.worker_id=w.id
        where su.login='${login}'
        "
        />
        
        
        <%
        	boolean isViewAllDepartment=RolesHelper.checkRoles("/Policy/Mis/MedCase/Stac/Journal/ShowInfoAllDepartments",request) ;
	    	List list= (List)request.getAttribute("infoByLogin");
	    	WebQueryResult wqr = list.isEmpty() ? null : (WebQueryResult)list.get(0) ;
        	String department = request.getParameter("department") ;
        	if (department==null || department.equals("")) {
        		department = "0" ;
        	}
        	String spec = request.getParameter("spec") ;
        	if (spec==null || spec.equals("")) {
        		spec = "0" ;
        	}
        	String workFunc = wqr!=null?""+wqr.get1():"0" ;
        	boolean isBossDepartment= wqr != null && wqr.get3() != null;

 
        	int type=0 ;
        	if (!spec.equals("0")) {
        		type=3 ;
        		if (!isViewAllDepartment&&!isBossDepartment&&!spec.equals(workFunc)) {
        			spec=workFunc ;
        		}
        	} else if (!department.equals("0") && (isViewAllDepartment || isBossDepartment)) {
        		type=2 ;
       		} else if (isViewAllDepartment) {
       			type=1 ;
       		} else if (wqr!=null) {
       			if (isBossDepartment) {
       				type=2 ;
       				department=""+wqr.get2() ;
       			} else {
       				type=3 ;
       				spec=workFunc ;
       			}
       		}
       		request.setAttribute("department", department) ;
       		request.setAttribute("spec", spec) ;        	
       	%>
        	    <msh:form action="/stac_report_cases_short_protocol.do" method="GET" defaultField="dateBegin">
    <msh:panel>
      <msh:row>
        <msh:separator label="Параметры поиска" colSpan="7" />
      </msh:row>

      <msh:ifInRole roles="/Policy/Mis/MedCase/Stac/Journal/ShowInfoAllDepartments">
      <msh:row>
      	<msh:autoComplete property="department" vocName="lpu" label="Отделение" fieldColSpan="6" horizontalFill="true" size="70"/>
      </msh:row>
      <msh:row>
      	<msh:autoComplete property="spec" vocName="workFunction" label="Специалист" fieldColSpan="6" horizontalFill="true" size="70"/>
      </msh:row>
      </msh:ifInRole>
      <msh:row>
        <msh:textField property="dateBegin" label="Период с" />
        <msh:textField property="dateEnd" label="по" />
     </msh:row>
     <msh:row>
     	<msh:textField property="cntSymbols"/>
     </msh:row>
     <msh:row>
        <msh:submitCancelButtonsRow colSpan="4"/>
     </msh:row>
        	</msh:panel></msh:form>
    <%
    String dateBegin = request.getParameter("dateBegin") ;
    if (dateBegin!=null && !dateBegin.equals("")) {
    	String dateEnd = request.getParameter("dateEnd") ;
    	String cntSymbols = request.getParameter("cntSymbols") ;
    	if (dateEnd==null||dateEnd.equals("")) {
    		request.setAttribute("finishDate", dateBegin) ;
    	} else {
    		request.setAttribute("finishDate", dateEnd) ;
    	}
    	request.setAttribute("beginDate", dateBegin) ;
	    StringBuilder paramHref = new StringBuilder() ;
	    paramHref.append("dateBegin=").append(dateBegin).append("&dateEnd=").append(dateEnd) ;
	    if (cntSymbols==null||cntSymbols.equals("")||
	    		!cntSymbols.matches("^-?\\d+$")) {
	    	cntSymbols="30" ;
	    }
	    paramHref.append("&cntSymbols=").append(cntSymbols) ;
    	StringBuilder paramSql = new StringBuilder() ;
    	paramSql.append("pr.dateRegistration between to_date('").append(dateBegin)
    			.append("','dd.mm.yyyy') and to_date('").append(dateEnd)
    			.append("','dd.mm.yyyy')") 
    			.append(" and pr.dtype='Protocol'")
    			.append(" and length(pr.record)<").append(cntSymbols)
    			.append(" and slo.dtype='DepartmentMedCase'") ;
    	request.setAttribute("paramSql", paramSql.toString()) ;
	    request.setAttribute("paramHref", paramHref.toString()) ;
	    if (type==1) { 
    
    %>
    <msh:section>
    <msh:sectionTitle>Свод по отделениям</msh:sectionTitle>
    <msh:sectionContent>
    <ecom:webQuery name="datelist" nativeSql="
    select ml.id||'&department='||ml.id,ml.name ,count(distinct pr.id) 
	from Diary pr
	left join MedCase slo on slo.id=pr.medcase_id and pr.dtype='Protocol'
	left join WorkFunction wf on wf.id=pr.specialist_id
	left join Worker w on w.id=wf.worker_id 
	left join MisLpu ml on w.lpu_id=ml.id 
	where ${paramSql}
	group by ml.id,ml.name order by ml.name
    " />
    <msh:table name="datelist" 
    viewUrl="stac_report_cases_short_protocol.do?${paramHref}&short=Short"
    action="stac_report_cases_short_protocol.do?${paramHref}" idField="1">
      <msh:tableColumn property="sn" columnName="#"/>
      <msh:tableColumn columnName="Отделение" property="2" />
      <msh:tableColumn columnName="Кол-во" property="3" />
    </msh:table>
    </msh:sectionContent>
    </msh:section> 
    <% } else if (type==2 )  {	%>
    <msh:section>
    <msh:sectionTitle>Реестр по лечащим врачам</msh:sectionTitle>
    <msh:sectionContent>
    <ecom:webQuery name="datelist" nativeSql="
    select 
wf.id||'&department=${department}&spec='||wf.id as id
,wp.lastname||' '||wp.firstname||' '||wp.middlename as lechVr
,count(distinct pr.id) as cntSlo 
from Diary pr
left join MedCase slo on slo.id=pr.medCase_id 
left join Patient pat on slo.patient_id=pat.id 
left join WorkFunction wf on pr.specialist_id=wf.id 
left join Worker w on wf.worker_id=w.id 
left join Patient wp on w.person_id=wp.id 
where ${paramSql} and w.lpu_id=${department}
group by wf.id,wp.lastname,wp.middlename,wp.firstname 
order by wp.lastname,wp.middlename,wp.firstname
    " />
    <msh:table name="datelist" 
    viewUrl="stac_report_cases_short_protocol.do?${paramHref}&short=Short"
    action="stac_report_cases_short_protocol.do?${paramHref}" idField="1">
      <msh:tableColumn property="sn" columnName="#"/>
      <msh:tableColumn columnName="Лечащий врач" property="2" />
      <msh:tableColumn columnName="Кол-во пациентов" property="3" />
    </msh:table>
    </msh:sectionContent>
    </msh:section>
         <%} else if (type==3 )  {	%>
    <msh:section>
    <msh:sectionTitle>Реестр пациентов</msh:sectionTitle>
    <msh:sectionContent>
    <ecom:webQuery name="datelist" nativeSql="
select pr.id,to_char(pr.dateRegistration,'dd.mm.yyyy')||' '||cast(pr.timeRegistration as varchar(5)) as dattim
    ,pat.lastname ||' ' ||pat.firstname|| ' ' || pat.middlename ||' '||
    to_char(pat.birthday,'dd.mm.yyyy') as fio,sc.code
    ,pr.record as prrecord
    from Diary pr 
    left join medCase slo on slo.id=pr.medcase_id  
    left join MedCase as sls on sls.id = slo.parent_id 
    left join bedfund as bf on bf.id=slo.bedfund_id 
    left join StatisticStub as sc on sc.medCase_id=sls.id 
    left join Patient pat on slo.patient_id = pat.id 
    where 
    ${paramSql} and pr.specialist_id='${spec}' 
    group by  pr.id,pr.dateRegistration,pr.timeRegistration,pat.lastname,pat.firstname
    ,pat.middlename,pat.birthday,sc.code
    ,bf.addCaseDuration,slo.dateStart,sls.dateStart
    ,pr.record
    order by pat.lastname,pat.firstname,pat.middlename
    " />
    <msh:table name="datelist" 
    viewUrl="entityShortView-smo_visitProtocol.do"
    action="entityParentView-smo_visitProtocol.do" idField="1">
      <msh:tableColumn property="sn" columnName="#"/>
      <msh:tableColumn columnName="Стат.карта" property="4" />
      <msh:tableColumn columnName="Фамилия имя отчество пациента" property="3" />
      <msh:tableColumn columnName="Дата и время протокола" property="2" />
      <msh:tableColumn columnName="Протокол" property="5"/>
    </msh:table>
    </msh:sectionContent>
    </msh:section>
    <% } 
    }
    %>

  </tiles:put>
</tiles:insert>