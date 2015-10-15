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
    <msh:title guid="helloItle-123" mainMenu="StacJournal">Журнал черновиков дневников </msh:title>
  </tiles:put>
  <tiles:put name="side" type="string">
  	<tags:stac_journal currentAction="stac_journalDraftByCurator"/>
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
        	/**/curator=workFunc ;
       		request.setAttribute("department", department) ;
       		request.setAttribute("curator", curator) ;     
       		type=3 ;
       	%>
        	
    <%if (type==1) { %>
    <msh:section>
    <msh:sectionTitle>Свод по отделениям</msh:sectionTitle>
    <msh:sectionContent>
    <ecom:webQuery name="datelist" nativeSql="
    select ml.id||'&department='||ml.id,ml.name ,count(distinct slo.id) 
	from MedCase slo 
	left join Diary p on slo.id=p.medcase_id 
	left join MisLpu ml on slo.department_id=ml.id 
	where p.dtype='RoughDraft'
	and slo.dtype='DepartmentMedCase'
	group by ml.id,ml.name order by ml.name
    " guid="81cbfcaf-6737-4785-bac0-6691c6e6b501" />
    <msh:table name="datelist" 
    viewUrl="js-smo_draftProtocol-list.do?short=Short"
    action="js-smo_draftProtocol-list.do" idField="1">
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
left join Diary p on slo.id=p.medcase_id 
where slo.department_id='${department}' and slo.dtype='DepartmentMedCase' 
 and p.dtype='RoughDraft'
group by owf.id,owp.lastname,owp.middlename,owp.firstname 
order by owp.lastname,owp.middlename,owp.firstname
    " guid="81cbfcaf-6737-4785-bac0-6691c6e6b501" />
    <msh:table name="datelist" 
    viewUrl="js-smo_draftProtocol-list.do?short=Short"
    action="js-smo_draftProtocol-list.do" idField="1">
      <msh:tableColumn property="sn" columnName="#"/>
      <msh:tableColumn columnName="Лечащий врач" property="2" />
      <msh:tableColumn columnName="Кол-во пациентов" property="3" />
    </msh:table>
    </msh:sectionContent>
    </msh:section>
         <%}%>
         <%if (type==3 )  {	%>
    <msh:section>
    <msh:sectionTitle>Реестр черновиков</msh:sectionTitle>
    <msh:sectionContent>
    <ecom:webQuery name="datelist" nativeSql="
select dm.diary_id as diaryid
,slo.dateStart
    ,pat.lastname ||' ' ||pat.firstname|| ' ' || pat.middlename as fio
    ,pat.birthday,sc.code
    ,d.dateRegistration
,vdd.name as vddname
,dm.record as dmrecord
,d.record as drecord 
,to_char(dm.validitydate,'dd.mm.yyyy')||cast(dm.validitytime as varchar(5)) as validity
, d.id as pid
,dm.id as dmid

    from DiaryMessage dm
left join vocdefectdiary vdd on vdd.id=dm.defect_id
left join diary d on d.id=dm.diary_id 
    left join  medCase slo on slo.id=d.medcase_id 
    left join MedCase as sls on sls.id = slo.parent_id 
    left join bedfund as bf on bf.id=slo.bedfund_id 
    left join StatisticStub as sc on sc.medCase_id=sls.id 
    left join Patient pat on slo.patient_id = pat.id 
    
    where  d.specialist_id='${curator}'
    and (dm.validitydate>current_date or dm.validitydate=current_date 
    and dm.validitytime>=current_time
    )
   
    " guid="81cbfcaf-6737-4785-bac0-6691c6e6b501" />
    <msh:table name="datelist" 
    viewUrl="entityView-smo_visitProtocol.do?short=Short" 
    action="entityParentEdit-smo_visitProtocol.do" idField="1">
      <msh:tableColumn property="sn" columnName="#"/>
      <msh:tableColumn columnName="Стат.карта" property="5" />
      <msh:tableColumn columnName="Фамилия имя отчество пациента" property="3" />
      <msh:tableColumn columnName="Год рождения" property="4" />
      <msh:tableColumn columnName="Дата поступления" property="2" />
      <msh:tableColumn columnName="Дата" property="6"/>
      <msh:tableColumn columnName="Проблема" property="7" cssClass=""/>
      <msh:tableColumn columnName="Текст после редакции заведующего" property="9" cssClass="preCell"/>
      <msh:tableColumn columnName="Текст протокола" property="8" cssClass="preCell"/>
      <msh:tableColumn property="10" columnName="Срок на редактирование"/>

    </msh:table>
    </msh:sectionContent>
    </msh:section>
    <% } %>

  </tiles:put>
</tiles:insert>

