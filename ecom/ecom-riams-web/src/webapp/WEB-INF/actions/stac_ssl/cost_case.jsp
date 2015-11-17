<%@page import="ru.ecom.ejb.services.query.WebQueryResult"%>
<%@page import="java.util.List"%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://struts.apache.org/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://www.nuzmsh.ru/tags/msh" prefix="msh" %>
<%@ taglib uri="http://www.ecom-ast.ru/tags/ecom" prefix="ecom" %>
<%@ taglib tagdir="/WEB-INF/tags" prefix="tags" %>

<tiles:insert page="/WEB-INF/tiles/mainLayout.jsp" flush="true" >

  <tiles:put name="title" type="string">
    <msh:title guid="helloItle-123" mainMenu="StacJournal" title="Стоимость госпитализации (СЛС)" />
  </tiles:put>
  <tiles:put name="side" type="string">
   
  </tiles:put>
  <tiles:put name="body" type="string">
  <ecom:webQuery name="patinfoQ" nativeSql="select m.patient_id,to_char(m.datestart,'dd.mm.yyyy') as dstart
  ,to_char(coalesce(m.datefinish,current_date),'dd.mm.yyyy') as dfinish,m.serviceStream_id as servstream from medcase m where m.id=${param.id}
  "/>
  <%
  request.setAttribute("priceList", "3");
  request.setAttribute("idsertypebed","11") ;
  List l = (List)request.getAttribute("patinfoQ") ;
  if (l!=null && l.size()>0) {
      WebQueryResult p = (WebQueryResult) l.get(0) ;
      request.setAttribute("patient_id", p.get1());
      request.setAttribute("datestart", p.get2());
      request.setAttribute("datefinish", p.get3());
    request.setAttribute("serStreamId",p.get4()) ;
  %>
  <msh:section>
  <msh:sectionTitle>Койко-дни</msh:sectionTitle>
  <msh:sectionContent>
 
      <ecom:webQuery name="list" nativeSql="
      select slo.id,ml.name||' '||vbt.name||' '||vbst.name||' '||vrt.name as sloinfo
      ,pp.code||' '||pp.name as ppname
      ,

case when coalesce(slo.datefinish,slo.transferdate,current_date)-slo.datestart=0 then '1'
      else coalesce(slo.datefinish,slo.transferdate,current_date)-slo.datestart+case when vht.code='ALLTIMEHOSP' then 0 else 1 end end as cntDays
      ,pp.cost as ppcost
      ,case when coalesce(slo.datefinish,slo.transferdate,current_date)-slo.datestart=0 then '1'
      else coalesce(slo.datefinish,slo.transferdate,current_date)-slo.datestart+1 end
      * pp.cost as ppsum,ms.code||' '||ms.name as msifo
       from medcase slo
      left join medcase sls on sls.id=slo.parent_id
left join Vochosptype vht on vht.id=sls.hosptype_id
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
left join medservice ms on ms.id=wfs.medservice_id
    left join pricemedservice pms on pms.medservice_id=wfs.medservice_id
        left join priceposition pp on pp.id=pms.priceposition_id

where slo.parent_id='${param.id}'
and pp.priceList_id='${priceList}' and ms.servicetype_id=${idsertypebed} and (pp.isvat is null or pp.isvat='0')
      "/>
    <msh:table name="list" action="javascript:void(0)" idField="1" noDataMessage="Не найдено" guid="b0e1aebf-a031-48b1-bc75-ce1fbeb6c6db">
      <msh:tableColumn columnName="#" property="sn" />
      <msh:tableColumn columnName="Информация о СЛО" property="2" />
      <msh:tableColumn columnName="Наименование услуги" property="7" />
      <msh:tableColumn columnName="Наименование прейскурнт" property="3" />
      <msh:tableColumn columnName="Кол-во" property="4" />
      <msh:tableColumn columnName="Цена" property="5" />
      <msh:tableColumn columnName="Сумма" property="6" isCalcAmount="true" />
    </msh:table>
  </msh:sectionContent>
  <msh:sectionTitle>Диагностика</msh:sectionTitle>
  <msh:sectionContent>
      <ecom:webQuery name="list2" nativeSql="
      select
      vis.id,vis.datestart||' - '||ms.code||'. '||ms.name||' - '||vwf.name||' '||wp.lastname as sloinfo
      from medcase vis
      left join workfunction wf on wf.id=vis.workfunctionexecute_id
      left join vocworkfunction vwf on vwf.id=wf.workfunction_id
      left join worker w on w.id=wf.worker_id
      left join patient wp on wp.id=w.person_id
      left join vocservicestream vss on vss.id=vis.servicestream_id
      left join medcase smc on smc.parent_id=vis.id and upper(smc.dtype)='SERVICEMEDCASE'
      left join medservice ms on ms.id=smc.medservice_id
    left join pricemedservice pms on pms.medservice_id=smc.medservice_id
    left join priceposition pp on pp.id=pms.priceposition_id
      where vis.patient_id='${patient_id}' and vis.datestart between to_date('${datestart}','dd.mm.yyyy') and to_date('${datefinish}','dd.mm.yyyy')
       and upper(vis.dtype)='VISIT' and (vss.code='HOSPITAL' or vss.id='${serStreamId}' or vss.code='OTHER')
       
       group by vis.id,vis.datestart,ms.code,ms.name,vwf.name,wp.lastname
       having count(case when pp.priceList_id='${priceList}' then '1' else null end)=0
      "/>
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
      left join medcase smc on smc.parent_id=vis.id and upper(smc.dtype)='SERVICEMEDCASE'
      left join medservice ms on ms.id=smc.medservice_id
    left join pricemedservice pms on pms.medservice_id=smc.medservice_id
    left join priceposition pp on pp.id=pms.priceposition_id
      where vis.patient_id='${patient_id}' and vis.datestart between to_date('${datestart}','dd.mm.yyyy') and to_date('${datefinish}','dd.mm.yyyy')
       and upper(vis.dtype)='VISIT' and (vss.code='HOSPITAL' or vss.id='${serStreamId}' or vss.code='OTHER')
       and pp.priceList_id='${priceList}'
      "/>

    <msh:table name="list1" action="javascript:void(0)" idField="1" noDataMessage="Не найдено" guid="b0e1aebf-a031-48b1-bc75-ce1fbeb6c6db">
      <msh:tableColumn columnName="#" property="sn" />
      <msh:tableColumn columnName="Информация о визите" property="2" />
      <msh:tableColumn columnName="Наименование услуги" property="3" />
      <msh:tableColumn columnName="Сумма" property="4" isCalcAmount="true" />
    </msh:table>
    <msh:table name="list2" action="javascript:void(0)" idField="1" noDataMessage="Не найдено" guid="b0e1aebf-a031-48b1-bc75-ce1fbeb6c6db">
      <msh:tableColumn columnName="#" property="sn" />
      <msh:tableColumn columnName="Информация о визите" property="2" />
    </msh:table>
  </msh:sectionContent>
  <msh:sectionTitle>Лаборатория</msh:sectionTitle>
  <msh:sectionContent>
      <ecom:webQuery name="list" nativeSql="
select
      vis.id,vis.datestart||' - '||ms.code||'. '||ms.name,vwf.name||' '||wp.lastname as sloinfo
      ,pp.code||' '||pp.name as ppname
      ,pp.cost as ppcost
      from medcase vis
      left join workfunction wf on wf.id=vis.workfunctionexecute_id
      left join vocworkfunction vwf on vwf.id=wf.workfunction_id
      left join worker w on w.id=wf.worker_id
      left join patient wp on wp.id=w.person_id
      left join vocservicestream vss on vss.id=vis.servicestream_id
      left join medcase smc on smc.parent_id=vis.id
      left join medservice ms on ms.id=smc.medservice_id
    left join pricemedservice pms on pms.medservice_id=smc.medservice_id
    left join priceposition pp on pp.id=pms.priceposition_id and pp.priceList_id='${priceList}'
      where vis.parent_id='${param.id}'
      and vis.datestart between to_date('${datestart}','dd.mm.yyyy') and to_date('${datefinish}','dd.mm.yyyy')
       and upper(vis.dtype)='VISIT' and upper(smc.dtype)='SERVICEMEDCASE' and (vss.code='HOSPITAL' or vss.id is null)
       
      "/>
    <msh:table name="list" action="javascript:void(0)" idField="1" noDataMessage="Не найдено">
      <msh:tableColumn columnName="#" property="sn" />
      <msh:tableColumn columnName="Наименование услуги" property="2" />
      <msh:tableColumn columnName="Исполнитель" property="3" />
      <msh:tableColumn columnName="Кол-во" property="4" />
      <msh:tableColumn columnName="Цена" property="5" isCalcAmount="true" />
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
    left join priceposition pp on pp.id=pms.priceposition_id
      where
      (slo.parent_id='${param.id}' or slo.id='${param.id}')
       and pp.priceList_id='${priceList}'
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
