<%@page import="ru.ecom.ejb.services.query.WebQueryResult"%>
<%@page import="java.util.List"%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://struts.apache.org/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://www.nuzmsh.ru/tags/msh" prefix="msh" %>
<%@ taglib uri="http://www.ecom-ast.ru/tags/ecom" prefix="ecom" %>
<%@ taglib tagdir="/WEB-INF/tags" prefix="tags" %>

<tiles:insert page="/WEB-INF/tiles/main${param.short}Layout.jsp" flush="true" >

  <tiles:put name="title" type="string">
    <msh:title guid="helloItle-123" mainMenu="StacJournal" title="Стоимость госпитализации (СЛС)" />
  </tiles:put>
  <tiles:put name="side" type="string">
    <tags:stac_infoBySls form="stac_sslForm" />
  </tiles:put>
  <tiles:put name="body" type="string">
  <ecom:webQuery name="patinfoQ" nativeSql="select m.patient_id,to_char(m.datestart,'dd.mm.yyyy') as dstart
  ,to_char(coalesce(m.datestart,current_date),'dd.mm.yyyy') as dfinish from medcase m 
  left join vocservicestream vss on vss.id=m.servicestream_id
  where m.id=${param.id} and vss.code in ('DOGOVOR','PRIVATEINSURANCE')
  "/>
  <%
  request.setAttribute("priceList", "3");
  List l = (List)request.getAttribute("patinfoQ") ;
  if (l!=null && l.size()>0) {
	  WebQueryResult p = (WebQueryResult) l.get(0) ;
	  request.setAttribute("patient_id", p.get1());
	  request.setAttribute("datestart", p.get1());
	  request.setAttribute("datefinish", p.get1());
  %>
  <msh:section>
  <msh:sectionTitle>Койко-дни</msh:sectionTitle>
  <msh:sectionContent>
  
  	<ecom:webQuery name="list" nativeSql="
  	select slo.id as sloid
  	,ms.id as msid, ms.code||' '||ms.name as msinfo
  	,ml.name||' '||vbt.name||' '||vbst.name||' '||vrt.name as sloinfo
  	,pp.name as ppname
  	,case when coalesce(slo.datefinish,slo.transferdate,current_date)-slo.datestart=0 then '1'
  	else coalesce(slo.datefinish,slo.transferdate,current_date)-slo.datestart+1 end as cntDays
  	,pp.cost as ppcost
  	,case when coalesce(slo.datefinish,slo.transferdate,current_date)-slo.datestart=0 then '1'
  	else coalesce(slo.datefinish,slo.transferdate,current_date)-slo.datestart+1 end 
  	* pp.cost as ppsum
  	 from medcase slo
  	left join medcase sls on sls.id=slo.parent_id
left join statisticstub ss on ss.id=sls.statisticStub_id
left join bedfund bf on bf.id=slo.bedfund_id
left join vocbedtype vbt on vbt.id=bf.bedtype_id
left join vocbedsubtype vbst on vbst.id=bf.bedsubtype_id
left join workPlace wp on wp.id=slo.roomNumber_id
left join Patient pat on pat.id=slo.patient_id
left join VocRoomType vrt on vrt.id=wp.roomType_id
left join mislpu ml on ml.id=slo.department_id
left join workfunctionservice wfs on wfs.lpu_id=slo.department_id
    and bf.bedtype_id=wfs.bedtype_id and bf.bedsubtype_id=wfs.bedsubtype_id
    and wfs.roomType_id=wp.roomType_id
left join medservice ms on ms.id=wfs.medservice_id and ms.servicetype_id=11
	left join pricemedservice pms on pms.medservice_id=wfs.medservice_id
		left join priceposition pp on pp.id=pms.priceposition_id and pp.priceList_id='${priceList}'

where slo.parent_id='${param.id}'

  	"/>
    <msh:table name="list" action="javascript:void(0)" idField="1" noDataMessage="Не найдено" guid="b0e1aebf-a031-48b1-bc75-ce1fbeb6c6db">
      <msh:tableColumn columnName="#" property="sn" />
      <msh:tableColumn columnName="Информация о СЛО" property="4" />
      <msh:tableColumn columnName="Наименование услуги" property="3" />
      <msh:tableColumn columnName="Наименование услуги по прейскуранту" property="5" />
      <msh:tableColumn columnName="Кол-во" property="6" />
      <msh:tableColumn columnName="Цена" property="7" />
      <msh:tableColumn columnName="Сумма" property="8" isCalcAmount="true" />
    </msh:table>
  </msh:sectionContent>
  <msh:sectionTitle>Диагностика</msh:sectionTitle>
  <msh:sectionContent>
  	<ecom:webQuery name="list1" nativeSql="
  	select
  	vis.id,vis.datestart||' - '||ms.code||'. '||ms.name||' - '||vwf.name||' '||wp.lastname as sloinfo
  	,pp.code||' '||pp.name as ppname
  	,pp.cost as ppcost
  	from medcase vis
  	left join workfunction wf on wf.id=vis.workfunctionexecute_id
  	left join vocworkfunction vwf on vwf.id=wf.workfunction_id
  	left join worker w on w.id=wf.worker_id
  	left join patient wp on wp.id=w.person_id
  	left join vocservicestream vss on vss.id=vis.servicestream_id
  	left join medcase smc on smc.parent_id=vis.id and smc.dtype='SERVICEMEDCASE'
  	left join medservice ms on ms.id=smc.medservice_id
	left join pricemedservice pms on pms.medservice_id=smc.medservice_id
	left join priceposition pp on pp.id=pms.priceposition_id and pp.priceList_id='${priceList}'
  	where vis.patient_id='${patient_id}' and vis.datestart between to_date('${datestart}','dd.mm.yyyy') and to_date('${datefinish}','dd.mm.yyyy')
  	 and upper(vis.dtype)='VISIT' and vss.code='HOSPITAL'
  	 
  	"/>
    <msh:table name="list1" action="javascript:void(0)" idField="1" noDataMessage="Не найдено" guid="b0e1aebf-a031-48b1-bc75-ce1fbeb6c6db">
      <msh:tableColumn columnName="#" property="sn" />
      <msh:tableColumn columnName="Информация о визите" property="2" />
      <msh:tableColumn columnName="Наименование услуги" property="3" />
      <msh:tableColumn columnName="Сумма" property="4" isCalcAmount="true" />
    </msh:table>
  </msh:sectionContent>
  <msh:sectionTitle>Лаборатория</msh:sectionTitle>
  <msh:sectionContent>
  	<ecom:webQuery name="list" nativeSql="
  	select
  	vis.id,vis.datestart||' - '||ms.code||'. '||ms.name||' - '||vwf.name||' '||wp.lastname as sloinfo
  	,pp.code||' '||pp.name as ppname
  	,pp.cost as ppcost
  	from medcase vis
  	left join workfunction wf on wf.id=vis.workfunctionexecute_id
  	left join vocworkfunction vwf on vwf.id=wf.workfunction_id
  	left join worker w on w.id=wf.worker_id
  	left join patient wp on wp.id=w.person_id
  	left join medcase slo on slo.id=vis.parent_id
  	left join vocservicestream vss on vss.id=vis.servicestream_id
  	left join medcase smc on smc.parent_id=vis.id and smc.dtype='SERVICEMEDCASE'
  	left join medservice ms on ms.id=smc.medservice_id
	left join pricemedservice pms on pms.medservice_id=smc.medservice_id
	left join priceposition pp on pp.id=pms.priceposition_id and pp.priceList_id='${priceList}'
  	where vis.patient_id='${patient_id}' 
  	and slo.parent_id='${param.id}'
  	and vis.datestart between to_date('${datestart}','dd.mm.yyyy') and to_date('${datefinish}','dd.mm.yyyy')
  	 and upper(vis.dtype)='VISIT' and vss.code='HOSPITAL'
  	 
  	"/>
    <msh:table name="list" action="javascript:void(0)" idField="1" noDataMessage="Не найдено" guid="b0e1aebf-a031-48b1-bc75-ce1fbeb6c6db">
      <msh:tableColumn columnName="#" property="sn" />
      <msh:tableColumn columnName="Наименование услуги" property="1" />
      <msh:tableColumn columnName="Кол-во" property="2" />
      <msh:tableColumn columnName="Цена" property="3" />
      <msh:tableColumn columnName="Сумма" property="4" isCalcAmount="true" />
    </msh:table>
  </msh:sectionContent>
  <msh:sectionTitle>Операции</msh:sectionTitle>
  <msh:sectionContent>
  	<ecom:webQuery name="list" nativeSql="
  	select
  	so.id,so.operationdate||' - '||ms.code||'. '||ms.name||' - '||vwf.name||' '||wp.lastname as sloinfo
  	,pp.code||' '||pp.name as ppname
  	,pp.cost as ppcost
  	from SurgicalOperation so
  	left join workfunction wf on wf.id=so.surgeon_id
  	left join vocworkfunction vwf on vwf.id=wf.workfunction_id
  	left join worker w on w.id=wf.worker_id
  	left join patient wp on wp.id=w.person_id
  	left join medcase slo on slo.id=so.medcase_id
  	left join vocservicestream vss on vss.id=so.servicestream_id
  	left join medservice ms on ms.id=so.medservice_id
	left join pricemedservice pms on pms.medservice_id=so.medservice_id
	left join priceposition pp on pp.id=pms.priceposition_id and pp.priceList_id='${priceList}'
  	where 
  	(slo.parent_id='${param.id}' or slo.id='${param.id}')
  	 
  	"/>
    <msh:table name="list" action="javascript:void(0)" idField="1" noDataMessage="Не найдено" guid="b0e1aebf-a031-48b1-bc75-ce1fbeb6c6db">
      <msh:tableColumn columnName="#" property="sn" />
      <msh:tableColumn columnName="Наименование услуги" property="1" />
      <msh:tableColumn columnName="Кол-во" property="2" />
      <msh:tableColumn columnName="Цена" property="3" />
      <msh:tableColumn columnName="Сумма" property="4" isCalcAmount="true" />
    </msh:table>
  </msh:sectionContent>
  </msh:section>
  <%} %>
  </tiles:put>
</tiles:insert>

