<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://struts.apache.org/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://www.nuzmsh.ru/tags/msh" prefix="msh" %>
<%@ taglib uri="http://www.ecom-ast.ru/tags/ecom" prefix="ecom" %>
<%@ taglib tagdir="/WEB-INF/tags" prefix="tags" %>

<tiles:insert page="/WEB-INF/tiles/main${param.s}Layout.jsp" flush="true" >

  <tiles:put name="title" type="string">
    <msh:title guid="helloItle-123" mainMenu="StacJournal">Журнал СЛО (открытых) по лечащему врачу </msh:title>
  </tiles:put>
  <tiles:put name="side" type="string">
  	<tags:stac_journal currentAction="stac_journalByCurator"/>
  </tiles:put>
  <tiles:put name="body" type="string">    
        <%
		    Long curator = (Long)request.getAttribute("curator") ;
		    if (curator!=null && curator.intValue()>0 )  {
    	%>
    <msh:section>
    <msh:sectionTitle>Журнал по лечащему врачу:  ${curatorInfo}. Печать дневников:
    &nbsp;<a href='stac_print_protocol.do?curator=${curator}&stNoPrint=selected'>включая осмотры других специалистом...</a>
    &nbsp; <a href='stac_print_protocol.do?owner=${curator}&stNoPrint=selected'>только своих дневников</a>
    </msh:sectionTitle>
    <msh:sectionContent>
    <ecom:webQuery name="datelist" nativeSql="
    select m.id,m.dateStart
    ,pat.lastname ||' ' ||pat.firstname|| ' ' || pat.middlename
    ,pat.birthday,sc.code,list((current_Date-so.operationDate)||' дн. после операции: '||ms.name) as oper  
    ,	  case 
			when (CURRENT_DATE-sls.dateStart)=0 then 1 
			when bf.addCaseDuration='1' then ((CURRENT_DATE-sls.dateStart)+1) 
			else (CURRENT_DATE-sls.dateStart)
		  end as cnt1
    ,	  case 
			when (CURRENT_DATE-m.dateStart)=0 then 1 
			when bf.addCaseDuration='1' then ((CURRENT_DATE-m.dateStart)+1) 
			else (CURRENT_DATE-m.dateStart)
		  end as cnt2
    from medCase m 
    left join MedCase as sls on sls.id = m.parent_id 
    left join bedfund as bf on bf.id=m.bedfund_id 
    left join StatisticStub as sc on sc.medCase_id=sls.id 
    left outer join Patient pat on m.patient_id = pat.id 
    left join SurgicalOperation so on so.medCase_id in (m.id,sls.id)
    left join medservice ms on ms.id=so.medService_id
    where m.DTYPE='DepartmentMedCase' and m.ownerFunction_id='${curator}' and m.transferDate is null and m.dateFinish is null
    group by  m.id,m.dateStart,pat.lastname,pat.firstname
    ,pat.middlename,pat.birthday,sc.code
    ,bf.addCaseDuration,m.dateStart,sls.dateStart
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
    <% } else { %>
    <msh:ifInRole roles="/Policy/Mis/MedCase/Stac/Journal/ShowInfoAllDepartments">
    <msh:section>
    <msh:sectionTitle>Свод по лечащим врачам</msh:sectionTitle>
    <msh:sectionContent>
    <ecom:webQuery name="datelist" nativeSql="
    select m.ownerFunction_id,ml.name as mlname,ovwf.name as ovwfname,owp.lastname||' '||substring(owp.firstname,1,1)||' '||coalesce(substring(owp.middlename,1,1),'') as worker
    ,count(distinct sls.id) as cntAll
    ,count(distinct case when sls.emergency='1' then sls.id else null end) as cntEmergency
    ,count(distinct case when so.id is not null then sls.id else null end) as cntOper    
    from medCase m 
    left join MedCase as sls on sls.id = m.parent_id 
    left join bedfund as bf on bf.id=m.bedfund_id 
    left join StatisticStub as sc on sc.medCase_id=sls.id 
    left outer join Patient pat on m.patient_id = pat.id 
    left join SurgicalOperation so on so.medCase_id in (m.id,sls.id)
    left join medservice ms on ms.id=so.medService_id
    left join WorkFunction owf on owf.id=m.ownerFunction_id
    left join VocWorkFunction ovwf on ovwf.id=owf.workFunction_id
    left join Worker ow on ow.id=owf.worker_id
    left join mislpu ml on ml.id=ow.lpu_id
    left join Patient owp on owp.id=ow.person_id
    where m.DTYPE='DepartmentMedCase' and m.transferDate is null and m.dateFinish is null
    group by ml.id,ml.name,m.ownerFunction_id,owp.lastname,owp.firstname,owp.middlename,ovwf.name
    order by ml.name,owp.lastname,owp.firstname
    " guid="81cbfcaf-6737-4785-bac0-6691c6e6b501" />
    <msh:table name="datelist" 
    viewUrl="stac_journalByCurator.do?s=Short"
    action="stac_journalByCurator.do" idField="1">
      <msh:tableColumn property="sn" columnName="#"/>
      <msh:tableColumn columnName="Отделение" property="2" />
      <msh:tableColumn columnName="Раб.функция" property="3" />
      <msh:tableColumn columnName="Специалист" property="4" />
      <msh:tableColumn columnName="Кол-во пациентов" property="5" />
      <msh:tableColumn columnName="Кол-во экстренных" property="6"/>
      <msh:tableColumn columnName="Кол-во оперированных" property="7"/>
    </msh:table>
    </msh:sectionContent>
    </msh:section> 
    </msh:ifInRole>
    <% } %>
  </tiles:put>
</tiles:insert>

