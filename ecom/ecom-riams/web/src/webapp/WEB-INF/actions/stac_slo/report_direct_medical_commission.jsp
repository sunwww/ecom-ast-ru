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
    <msh:title mainMenu="StacJournal">Журнал по пациентам, которым надо делать напрваление на ВК </msh:title>
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
	    	WebQueryResult wqr = list.size()>0?(WebQueryResult)list.get(0):null ;
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
    select ml.id||'&department='||ml.id,ml.name ,count(distinct slo.id) 
    , count(distinct case when (current_date - sls.dateStart)=(case when vss.omcCode='05' or vss.omcCode='10' then 14 else 29 end) then slo.id else null end) as cntSloOfen
	, count(distinct case when (current_date - sls.dateStart)>(case when vss.omcCode='05' or vss.omcCode='10' then 14 else 29 end) then slo.id else null end) as cntSloLast
    
    , count(distinct case when (current_date - sls.dateStart)=(14) then slo.id else null end) as cntSloOfen1
	, count(distinct case when (current_date - sls.dateStart)>(14) then slo.id else null end) as cntSloLast1
	from MedCase slo 
	left join MisLpu ml on ml.id=slo.department_id
	left join MedCase sls on sls.id=slo.parent_id
	left join Patient p on p.id=sls.patient_id
	left join VocSocialStatus vss on vss.id=p.socialStatus_id
	left join ClinicExpertCard cec on slo.id=cec.medcase_id  
	where slo.dateFinish is null  
	and slo.dtype='DepartmentMedCase' 
	and slo.transferDate is null 
	and (current_date - sls.dateStart)>(12)
	group by ml.id,ml.name order by ml.name
    " />
    <msh:table name="datelist" 
    viewUrl="stac_report_direct_medical_commission.do?short=Short"
    action="stac_report_direct_medical_commission.do" idField="1">
      <msh:tableColumn property="sn" columnName="#"/>
      <msh:tableColumn columnName="Отделение" property="2" />
      <msh:tableColumn columnName="Кол-во пациентов, которым надо делать направление на ВК" property="3" />
      <msh:tableColumn columnName="из них надо делать сегодня (работающие-15, неработающие-30)" property="4" />
      <msh:tableColumn columnName="из них просрочены (работающие-15, неработающие-30)" property="5" />
      <msh:tableColumn columnName="из них надо делать сегодня (15 кален.дн.)" property="6" />
      <msh:tableColumn columnName="из них просрочены (15 кален.дн.)" property="7" />
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
    , count(distinct case when (current_date - sls.dateStart)=(case when vss.omcCode='05' or vss.omcCode='10' then 14 else 29 end) then slo.id else null end) as cntSloOfen
	, count(distinct case when (current_date - sls.dateStart)>(case when vss.omcCode='05' or vss.omcCode='10' then 14 else 29 end) then slo.id else null end) as cntSloLast
    , count(distinct case when (current_date - sls.dateStart)=(14) then slo.id else null end) as cntSloOfen1
	, count(distinct case when (current_date - sls.dateStart)>(14) then slo.id else null end) as cntSloLast1
from MedCase slo 
	left join MedCase sls on sls.id=slo.parent_id
	left join Patient p on p.id=sls.patient_id
	left join VocSocialStatus vss on vss.id=p.socialStatus_id
left join WorkFunction owf on slo.ownerFunction_id=owf.id 
left join Worker ow on owf.worker_id=ow.id 
left join Patient owp on ow.person_id=owp.id 
where slo.department_id='${department}' and slo.dtype='DepartmentMedCase' 
and slo.dateFinish is null and slo.transferDate is null 
	and (current_date - sls.dateStart)>(12)
group by owf.id,owp.lastname,owp.middlename,owp.firstname 
order by owp.lastname,owp.middlename,owp.firstname
    " />
    <msh:table name="datelist" 
    viewUrl="stac_report_direct_medical_commission.do?short=Short"
    action="stac_report_direct_medical_commission.do" idField="1">
      <msh:tableColumn property="sn" columnName="#"/>
      <msh:tableColumn columnName="Лечащий врач" property="2" />
      <msh:tableColumn columnName="Кол-во пациентов, которым надо делать направление на ВК" property="3" />
      <msh:tableColumn columnName="из них надо делать сегодня (работающие-15, неработающие-30)" property="4" />
      <msh:tableColumn columnName="из них просрочены (работающие-15, неработающие-30)" property="5" />
      <msh:tableColumn columnName="из них надо делать сегодня (15 кален.дн.)" property="6" />
      <msh:tableColumn columnName="из них просрочены (15 кален.дн.)" property="7" />
    </msh:table>
    </msh:sectionContent>
    </msh:section>
         <%}%>
         <% if (type==3 )  {	%>
    <msh:section>
    <msh:sectionTitle>Реестр пациентов</msh:sectionTitle>
    <msh:sectionContent>
    <ecom:webQuery name="datelist" nativeSql="
select slo.id,slo.dateStart
    ,pat.lastname ||' ' ||pat.firstname|| ' ' || pat.middlename
    ,pat.birthday,vss.name as vssname,sc.code,list((current_Date-so.operationDate)||' дн. после операции: '||ms.name) as oper  
    ,	  ((CURRENT_DATE-sls.dateStart)+1) as cnt1
    ,	  case 
			when (CURRENT_DATE-slo.dateStart)=0 then 1 
			when bf.addCaseDuration='1' then ((CURRENT_DATE-slo.dateStart)+1) 
			else (CURRENT_DATE-slo.dateStart)
		  end as cnt2
    from medCase slo 
	left join MedCase sls on sls.id=slo.parent_id
    left join bedfund as bf on bf.id=slo.bedfund_id 
    left join StatisticStub as sc on sc.medCase_id=sls.id 
    left join Patient pat on slo.patient_id = pat.id 
	left join VocSocialStatus vss on vss.id=pat.socialStatus_id
    left join Diagnosis diag on diag.medcase_id=slo.id 
    left join SurgicalOperation so on so.medCase_id in (slo.id,sls.id)
    left join medservice ms on ms.id=so.medService_id
    where slo.DTYPE='DepartmentMedCase' and slo.ownerFunction_id='${curator}' 
    and slo.transferDate is null and slo.dateFinish is null
	and (current_date - sls.dateStart)>(12)
    group by  slo.id,slo.dateStart,pat.lastname,pat.firstname
    ,pat.middlename,pat.birthday,sc.code
    ,bf.addCaseDuration,slo.dateStart,sls.dateStart, vss.name

    order by pat.lastname,pat.firstname,pat.middlename
    " />
    <msh:table name="datelist" 
    viewUrl="entityShortView-stac_slo.do"
    action="entityParentView-stac_slo.do" idField="1">
      <msh:tableColumn property="sn" columnName="#"/>
      <msh:tableColumn columnName="Стат.карта" property="6" />
      <msh:tableColumn columnName="Фамилия имя отчество пациента" property="3" />
      <msh:tableColumn columnName="Год рождения" property="4" />
      <msh:tableColumn columnName="Соц.статус" property="5"/>
      <msh:tableColumn columnName="Дата поступления" property="2" />
      <msh:tableColumn columnName="Кол-во кален.дней СЛС" property="8"/>
      <msh:tableColumn columnName="Операции" property="7"/>
    </msh:table>
    </msh:sectionContent>
    </msh:section>
    <% } %>

  </tiles:put>
</tiles:insert>

