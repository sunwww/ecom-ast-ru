<%@page import="ru.ecom.ejb.services.query.WebQueryResult"%>
<%@page import="java.util.List"%>
<%@page import="ru.ecom.ejb.services.util.ConvertSql"%>
<%@page import="ru.nuzmsh.web.tags.helper.RolesHelper"%>
<%@page import="ru.ecom.web.login.LoginInfo"%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://struts.apache.org/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://www.nuzmsh.ru/tags/msh" prefix="msh" %>
<%@ taglib uri="http://www.ecom-ast.ru/tags/ecom" prefix="ecom" %>
<%@ taglib tagdir="/WEB-INF/tags" prefix="tags" %>

<tiles:insert page="/WEB-INF/tiles/main${param.short}Layout.jsp" flush="true" >

  <tiles:put name="title" type="string">
    <msh:title guid="helloItle-123" mainMenu="StacJournal">Журнал по пациентам, у которых есть открытые СПО</msh:title>
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
        	boolean isViewAllDepartment=RolesHelper.checkRoles("/Policy/Mis/MedCase/Visit/ViewAll",request) ;
	    	List<WebQueryResult> list= (List<WebQueryResult>)request.getAttribute("infoByLogin");
	    	WebQueryResult wqr = list.size()>0?list.get(0):null ;
        	String department = request.getParameter("department") ;
        	String curator = request.getParameter("curator") ;
        	String workFunc = wqr!=null?""+wqr.get1():"0" ;
        	boolean isBossDepartment=(wqr!=null&&wqr.get3()!=null)?true:false ;

 
        	int type=0 ;
        	if (curator!=null && !curator.equals("0")) {
        		type=3 ;
        		if (!isViewAllDepartment&&!isBossDepartment&&!curator.equals(workFunc)) {
        			curator=workFunc ;
        		}
        	} else if (department!=null && !department.equals("0") && (isViewAllDepartment || isBossDepartment)) {
        		type=2 ;
       		} else if (isViewAllDepartment) {
       			type=1 ;
       		} else if (wqr!=null) {
       			if (isBossDepartment) {
       				type=2 ;
       				department=""+wqr.get2() ;
       			} else {
       				type=3 ;
       				curator=workFunc ;
       			}
       		}
       		request.setAttribute("department", department) ;
       		request.setAttribute("curator", curator) ;        	
       	%>
        	
    <%if (type==1) { %>
    <msh:section>
    <msh:sectionTitle>Свод по отделениям</msh:sectionTitle>
    <msh:sectionContent>
    <ecom:webQuery name="datelist" nativeSql="
    select ml.id||'&department='||ml.id,ml.name ,count(distinct spo.id) 
	from MedCase spo 
	left join WorkFunction owf on owf.id=spo.ownerFunction_id
	left join Worker w on w.id=wf.worker_id
	left join MisLpu ml on spo.department_id=ml.id 
	where spo.dtype='PolyclinicMedCase' and spo.dateFinish is null  
	group by ml.id,ml.name order by ml.name
    " guid="81cbfcaf-6737-4785-bac0-6691c6e6b501" />
    <msh:table name="datelist" 
    viewUrl="stac_report_cases_not_filled.do?short=Short"
    action="stac_report_cases_not_filled.do" idField="1">
      <msh:tableColumn property="sn" columnName="#"/>
      <msh:tableColumn columnName="Отделение" property="2" />
      <msh:tableColumn columnName="Кол-во" property="3" />
    </msh:table>
    </msh:sectionContent>
    </msh:section> 
    <% } %>
    <%   if (type==2 )  {	%>
    <msh:section>
    <msh:sectionTitle>Реестр по лечащим врачам</msh:sectionTitle>
    <msh:sectionContent>
    <ecom:webQuery name="datelist" nativeSql="
    select 
owf.id||'&department=${department}&curator='||owf.id as id,owp.lastname||' '||owp.firstname||' '||owp.middlename as lechVr
,count(distinct slo.id) as cntSlo 
from MedCase slo 
left join Patient pat on slo.patient_id=pat.id 
left join WorkFunction owf on slo.ownerFunction_id=owf.id 
left join Worker ow on owf.worker_id=ow.id 
left join Patient owp on ow.person_id=owp.id 
where slo.department_id='${department}' and slo.dtype='DepartmentMedCase' 
and slo.dateFinish is null and slo.transferDate is null 
and (select max(p.dateRegistration) from diary p where p.medcase_id=slo.id)<(current_date-2)
group by owf.id,owp.lastname,owp.middlename,owp.firstname 
order by owp.lastname,owp.middlename,owp.firstname
    " guid="81cbfcaf-6737-4785-bac0-6691c6e6b501" />
    <msh:table name="datelist" 
    viewUrl="stac_report_cases_not_filled.do?short=Short"
    action="stac_report_cases_not_filled.do" idField="1">
      <msh:tableColumn property="sn" columnName="#"/>
      <msh:tableColumn columnName="Лечащий врач" property="2" />
      <msh:tableColumn columnName="Кол-во пациентов" property="3" />
    </msh:table>
    </msh:sectionContent>
    </msh:section>
         <%}%>
         <%if (type==3 )  {	%>
    <msh:section>
    <msh:sectionTitle>Реестр пациентов</msh:sectionTitle>
    <msh:sectionContent>
    <ecom:webQuery name="datelist" nativeSql="
select slo.id,slo.dateStart
    ,pat.lastname ||' ' ||pat.firstname|| ' ' || pat.middlename
    ,pat.birthday,sc.code,list((current_Date-so.operationDate)||' дн. после операции: '||ms.name) as oper  
    ,	  case 
			when (CURRENT_DATE-sls.dateStart)=0 then 1 
			when bf.addCaseDuration='1' then ((CURRENT_DATE-sls.dateStart)+1) 
			else (CURRENT_DATE-sls.dateStart)
		  end as cnt1
    ,	  case 
			when (CURRENT_DATE-slo.dateStart)=0 then 1 
			when bf.addCaseDuration='1' then ((CURRENT_DATE-slo.dateStart)+1) 
			else (CURRENT_DATE-slo.dateStart)
		  end as cnt2
    from medCase slo 
    left join MedCase as sls on sls.id = slo.parent_id 
    left join bedfund as bf on bf.id=slo.bedfund_id 
    left join StatisticStub as sc on sc.medCase_id=sls.id 
    left join Patient pat on slo.patient_id = pat.id 
    left join Diary p on slo.id=p.medcase_id 
    left join Diagnosis diag on diag.medcase_id=slo.id 
    left join SurgicalOperation so on so.medCase_id in (slo.id,sls.id)
    left join medservice ms on ms.id=so.medService_id
    where slo.DTYPE='DepartmentMedCase' and slo.ownerFunction_id='${curator}' 
    and slo.transferDate is null and slo.dateFinish is null
    group by  slo.id,slo.dateStart,pat.lastname,pat.firstname
    ,pat.middlename,pat.birthday,sc.code
    ,bf.addCaseDuration,slo.dateStart,sls.dateStart
    having (current_date-2)>max(p.dateRegistration) 

    order by pat.lastname,pat.firstname,pat.middlename
    " guid="81cbfcaf-6737-4785-bac0-6691c6e6b501" />
    <msh:table name="datelist" 
    viewUrl="entityShortView-stac_slo.do"
    action="entityParentView-stac_slo.do" idField="1" guid="be9cacbc-17e8-4a04-8d57-bd2cbbaeba30">
      <msh:tableColumn property="sn" columnName="#"/>
      <msh:tableColumn columnName="Стат.карта" property="5" guid="34a9f56a-2b47-4feb-a3fa-5c1afdf6c41d" />
      <msh:tableColumn columnName="Фамилия имя отчество пациента" property="3" guid="34a9f56a-2b47-4feb-a3fa-5c1afdf6c41d" />
      <msh:tableColumn columnName="Год рождения" property="4" guid="34a9f56a-2b47-4feb-a3fa-5c1afdf6c41d" />
      <msh:tableColumn columnName="Дата поступления" property="2" guid="3cf775aa-e94d-4393-a489-b83b2be02d60" />
      <msh:tableColumn columnName="Кол-во к.дней СЛС" property="8"/>
      <msh:tableColumn columnName="Операции" property="6"/>
      <msh:tableColumn columnName="Кол-во к.дней СЛО" property="9"/>
    </msh:table>
    </msh:sectionContent>
    </msh:section>
    <% } %>

  </tiles:put>
</tiles:insert>