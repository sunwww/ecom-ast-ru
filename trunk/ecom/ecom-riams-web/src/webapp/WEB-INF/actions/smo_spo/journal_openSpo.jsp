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
    <msh:title guid="helloItle-123" mainMenu="Poly">Журнал по пациентам, у которых есть открытые СПО</msh:title>
  </tiles:put>
  <tiles:put name="side" type="string">
  	<tags:visit_finds currentAction="smo_journal_openSpo"/>
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
        	List list= (List)request.getAttribute("infoByLogin");
	    	WebQueryResult wqr = list.size()>0?(WebQueryResult)list.get(0):null ;
	    	//WebQueryResult wqr = null ;
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
	left join WorkFunction wf on wf.id=spo.ownerFunction_id
	left join Worker w on w.id=wf.worker_id
	left join MisLpu ml on w.lpu_id=ml.id 
	where spo.dtype='PolyclinicMedCase' and spo.dateFinish is null  
	group by ml.id,ml.name order by ml.name
    " guid="81cbfcaf-6737-4785-bac0-6691c6e6b501" />
    <msh:table name="datelist" 
    viewUrl="smo_journal_openSpo.do?short=Short"
    action="smo_journal_openSpo.do" idField="1">
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
owf.id||'&department=${department}&curator='||owf.id as id
,owp.lastname||' '||owp.firstname||' '||owp.middlename as lechVr
,count(distinct pat.id) as cntPat 
,count(distinct spo.id) as cntSpo 
,count(distinct vis.id) as cntVis 
from MedCase spo
left join MedCase vis on vis.parent_id=spo.id 
left join Patient pat on spo.patient_id=pat.id 
left join WorkFunction owf on spo.ownerFunction_id=owf.id 
left join Worker ow on owf.worker_id=ow.id 
left join Patient owp on ow.person_id=owp.id 
where spo.dtype='PolyclinicMedCase' 
and spo.dateFinish is null and ow.lpu_id='${department}' 
and (vis.DTYPE='Visit' or vis.DTYPE='ShortMedCase')
group by owf.id,owp.lastname,owp.middlename,owp.firstname 
order by owp.lastname,owp.middlename,owp.firstname
    " guid="81cbfcaf-6737-4785-bac0-6691c6e6b501" />
    <msh:table name="datelist" 
    viewUrl="smo_journal_openSpo.do?short=Short"
    action="smo_journal_openSpo.do" idField="1">
      <msh:tableColumn property="sn" columnName="#"/>
      <msh:tableColumn columnName="Лечащий врач" property="2" />
      <msh:tableColumn columnName="Кол-во пациентов" isCalcAmount="true" property="3" />
      <msh:tableColumn columnName="Кол-во СПО" isCalcAmount="true" property="4" />
      <msh:tableColumn columnName="Кол-во визитов" isCalcAmount="true" property="5" />
    </msh:table>
    </msh:sectionContent>
    </msh:section>
         <%}%>
         <%if (type==3 )  {	%>
    <msh:section>
    <msh:sectionTitle>Реестр пациентов</msh:sectionTitle>
    <msh:sectionContent>
    <ecom:webQuery name="datelist" nativeSql="
select spo.id,spo.dateStart
    ,pat.lastname ||' ' ||pat.firstname|| ' ' || pat.middlename
    ,pat.birthday  
    ,CURRENT_DATE-spo.dateStart as cnt1
    ,max(vis.dateStart) as maxvisDateStart
    ,case when max(vis.dateStart) is not null then current_date-max(vis.dateStart) else null end as cntdaymax
    ,to_char(max(case when vis.dateStart is null then wcd.calendarDate else null end),'dd.mm.yyyy') as maxPlanDate
    ,list(distinct vvr.name) as vvrname,list(distinct mkb.code||' '||vpd.name) as diag
    ,count(distinct vis.id) as cntVis
    from medCase spo 
    left join MedCase vis on vis.parent_id=spo.id 
    left join Diagnosis diag on diag.medcase_id=vis.id
    left join VocIdc10 mkb on mkb.id=diag.idc10_id
    left join VocPriorityDiagnosis vpd on vpd.id=diag.priority_id
    left join VocVisitResult vvr on vvr.id=vis.visitResult_id
    left join WorkCalendarDay wcd on wcd.id=vis.datePlan_id
    left join Patient pat on spo.patient_id = pat.id 
    where spo.DTYPE='PolyclinicMedCase' and spo.ownerFunction_id='${curator}' 
     and spo.dateFinish is null and (vis.DTYPE='Visit' or vis.DTYPE='ShortMedCase')
    group by  spo.id,spo.dateStart,pat.lastname,pat.firstname
    ,pat.middlename,pat.birthday
    order by pat.lastname,pat.firstname,pat.middlename,spo.dateStart
    " guid="81cbfcaf-6737-4785-bac0-6691c6e6b501" />
    <msh:table name="datelist" 
    viewUrl="entityParentView-smo_spo.do?short=Short"
    action="entityParentView-smo_spo.do" idField="1" guid="be9cacbc-17e8-4a04-8d57-bd2cbbaeba30">
      <msh:tableColumn property="sn" columnName="#"/>
      <msh:tableColumn columnName="Фамилия имя отчество пациента" property="3" guid="34a9f56a-2b47-4feb-a3fa-5c1afdf6c41d" />
      <msh:tableColumn columnName="Год рождения" property="4" guid="34a9f56a-2b47-4feb-a3fa-5c1afdf6c41d" />
      <msh:tableColumn columnName="Дата начала СПО" property="2" guid="3cf775aa-e94d-4393-a489-b83b2be02d60" />
      <msh:tableColumn columnName="Кол-во дней" property="5"/>
      <msh:tableColumn isCalcAmount="true" columnName="Кол-во визитов" property="11" />
      <msh:tableColumn columnName="Диагнозы" property="10"/>
      <msh:tableColumn columnName="Результаты визитов" property="9"/>
      <msh:tableColumn columnName="Дата последнего посещения" property="6"/>
      <msh:tableColumn columnName="Кол-во дней после посл. посещения" property="7"/>
      <msh:tableColumn columnName="Дата посл. планир. посещения" property="8"/>
      
    </msh:table>
    </msh:sectionContent>
    </msh:section>
    <% } %>

  </tiles:put>
</tiles:insert>