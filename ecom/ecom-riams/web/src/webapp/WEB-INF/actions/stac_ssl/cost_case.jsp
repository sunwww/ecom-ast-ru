<%@page import="ru.ecom.ejb.services.query.WebQueryResult"%>
<%@page import="java.util.List"%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://struts.apache.org/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://www.nuzmsh.ru/tags/msh" prefix="msh" %>
<%@ taglib uri="http://www.ecom-ast.ru/tags/ecom" prefix="ecom" %>
<%@ taglib tagdir="/WEB-INF/tags" prefix="tags" %>

<tiles:insert page="/WEB-INF/tiles/main${param.short}Layout.jsp" flush="true" >

  <tiles:put name="title" type="string">
    <msh:title mainMenu="StacJournal" title="Стоимость госпитализации (СЛС)" />
  </tiles:put>
  <tiles:put name="side" type="string">
   
  </tiles:put>
  <tiles:put name="body" type="string">
  <ecom:webQuery name="patinfoQ" nativeSql="select m.patient_id,to_char(m.datestart,'dd.mm.yyyy') as dstart
  ,to_char(coalesce(m.datefinish,current_date),'dd.mm.yyyy') as dfinish,m.serviceStream_id as servstream from medcase m where m.id=${param.id}
  "/>
  <%
  request.setAttribute("priceList", "5");
  request.setAttribute("idsertypebed","11") ;
  List l = (List)request.getAttribute("patinfoQ") ;
  if (!l.isEmpty()) {
      WebQueryResult p = (WebQueryResult) l.get(0) ;
      request.setAttribute("patient_id", p.get1());
      request.setAttribute("datestart", p.get2());
      request.setAttribute("datefinish", p.get3());
    request.setAttribute("serStreamId",p.get4()) ;
  %>
  <msh:section>
  <msh:sectionTitle>
  <input type="button" onclick="showACCOUNTGetAccount(${param.id})" value="ПРИВЯЗАТЬ К СЧЕТУ"/>
  </msh:sectionTitle>
  </msh:section>
<msh:ifInRole roles="/Policy/Mis/MedPolicy/View">
        <ecom:webQuery nativeSql="select mp.id, case when (mp.DTYPE='MedPolicyOmc') then 'ОМС' when (mp.DTYPE='MedPolicyDmcForeign') then 'ДМС иногороднего' when (mp.DTYPE='MedPolicyDmc') then 'ДМС' else 'ОМС иногороднего' end, ri.name as riname,mp.polnumber,mp.series,mp.actualDateFrom,mp.actualDateTo from MedCase_MedPolicy as mc left join MedPolicy as mp on mp.id=mc.policies_id left join reg_ic as ri on ri.id=mp.company_id where mc.MedCase_id=${param.id}" name="policies" />
          <msh:section>
          <msh:sectionTitle>
          <msh:link action="stac_policiesEdit.do?id=${param.id}"  >Прикрепленные полисы к случаю</msh:link>
          </msh:sectionTitle>
          <msh:sectionContent>
            <msh:table name="policies" hideTitle="false" action="entitySubclassView-mis_medPolicy.do" idField="1">
              <msh:tableColumn property="sn" columnName="#" />
              <msh:tableColumn property="2" columnName="Тип" />
              <msh:tableColumn property="3" columnName="Страховая компания" />
              <msh:tableColumn property="4" columnName="Номер"/>
              <msh:tableColumn property="5" columnName="Серия"/>
              <msh:tableColumn property="6" columnName="Дата начала" />
              <msh:tableColumn property="7" columnName="Дата окончания" />
            </msh:table>
          </msh:sectionContent>
          </msh:section>
      </msh:ifInRole>  
  <msh:section>
  <msh:sectionTitle>Койко-дни не опред.</msh:sectionTitle>
  <msh:sectionContent>
 
      <ecom:webQuery name="list" nameFldSql="list_bedroom_no" nativeSql="
select slo.id,ml.name||' '||vbt.name||' '||vbst.name||' '||vrt.name as sloinfo
      ,list(pp.code||' '||pp.name) as ppname
      ,
case when coalesce(slo.datefinish,slo.transferdate,current_date)-slo.datestart=0 then '1'
      else coalesce(slo.datefinish,slo.transferdate,current_date)-slo.datestart+case when vht.code='ALLTIMEHOSP' then 0 else 1 end end as cntDays
      ,list(''||pp.cost) as ppcost
      ,list(''||(case when coalesce(slo.datefinish,slo.transferdate,current_date)-slo.datestart=0 then '1'
      else coalesce(slo.datefinish,slo.transferdate,current_date)-slo.datestart+case when vht.code='ALLTIMEHOSP' then 0 else 1 end end
      * pp.cost)) as ppsum,list(ms.code||' '||ms.name) as msifo
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
        left join priceposition pp on pp.id=pms.priceposition_id and pp.priceList_id='${priceList}' 
and (pp.isvat is null or pp.isvat='0')
where slo.parent_id='${param.id}'
 and ms.servicetype_id='${idsertypebed}' 
 group by slo.id,ml.name,vbt.name,vbst.name,vrt.name,slo.datefinish,slo.transferdate,slo.datestart,vht.code
 having count(pp.id)=0
      "/>
    <msh:table name="list" action="javascript:void(0)" idField="1" noDataMessage="Не найдено">
      <msh:tableColumn columnName="#" property="sn" />
      <msh:tableColumn columnName="Информация о СЛО" property="2" />
      <msh:tableColumn columnName="Наименование услуги" property="7" />
      <msh:tableColumn columnName="Наименование прейскурнт" property="3" />
      <msh:tableColumn columnName="Кол-во" property="4" />
      <msh:tableColumn columnName="Цена" property="5" />
      <msh:tableColumn columnName="Сумма" property="6" isCalcAmount="true" />
    </msh:table>
  </msh:sectionContent>
  <msh:sectionTitle>Койко-дни</msh:sectionTitle>
  <msh:sectionContent>
 
      <ecom:webQuery name="list" nameFldSql="list_bedroom_yes" nativeSql="
select slo.id,ml.name||' '||vbt.name||' '||vbst.name||' '||vrt.name as sloinfo
      ,list(pp.code||' '||pp.name) as ppname
      ,

case when coalesce(slo.datefinish,slo.transferdate,current_date)-slo.datestart=0 then '1'
      else coalesce(slo.datefinish,slo.transferdate,current_date)-slo.datestart+case when vbst.code='1' then 0 else 1 end end as cntDays
      ,list(''||pp.cost) as ppcost
      ,list(''||(
case when coalesce(slo.datefinish,slo.transferdate,current_date)-slo.datestart=0 then '1'
      else coalesce(slo.datefinish,slo.transferdate,current_date)-slo.datestart+case when vbst.code='1' then 0 else 1 end end
      * pp.cost)) as ppsum,list(ms.code||' '||ms.name) as msifo
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
and (pp.isvat is null or pp.isvat='0')
where slo.parent_id='${param.id}'
 and ms.servicetype_id='${idsertypebed}' and pp.priceList_id='${priceList}'
 group by slo.id,ml.name,vbt.name,vbst.code,vbst.name,vrt.name,slo.datefinish,slo.transferdate,slo.datestart,vht.code
      "/>
    <msh:table name="list" action="javascript:void(0)" idField="1" noDataMessage="Не найдено">
      <msh:tableColumn columnName="#" property="sn" />
      <msh:tableColumn columnName="Информация о СЛО" property="2" />
      <msh:tableColumn columnName="Наименование услуги" property="7" />
      <msh:tableColumn columnName="Наименование прейскурнт" property="3" />
      <msh:tableColumn columnName="Кол-во" property="4" />
      <msh:tableColumn columnName="Цена" property="5" />
      <msh:tableColumn columnName="Сумма" property="6" isCalcAmount="true" />
    </msh:table>
  </msh:sectionContent>
  <msh:sectionTitle>Специалисты из других отделений</msh:sectionTitle>
  <msh:sectionContent>
      <ecom:webQuery name="list1" nativeSql="
      select
      d.id,to_char(d.dateRegistration,'dd.mm.yyyy'),vwf.name||' '||wp.lastname as sloinfo
      from Diary d
      left join workfunction wf on wf.id=d.specialist_id
      left join vocworkfunction vwf on vwf.id=wf.workfunction_id
      left join worker w on w.id=wf.worker_id
      left join patient wp on wp.id=w.person_id
      left join medcase slo on slo.id=d.medcase_id
      where
      (slo.parent_id='${param.id}' and upper(slo.dtype)='DEPARTMENTMEDCASE' and w.lpu_id!=slo.department_id or slo.id='${param.id}')
      
       
      "/>


    <msh:table name="list1" action="javascript:void(0)" idField="1" noDataMessage="Не найдено">
      <msh:tableColumn columnName="#" property="sn" />
      <msh:tableColumn columnName="Дата" property="2" />
      <msh:tableColumn columnName="Специалист" property="3" />
      <msh:tableColumn columnName="Услуга" property="4" />
      <msh:tableColumn columnName="Сумма" property="5" isCalcAmount="true" />
    </msh:table>
  </msh:sectionContent>
  </msh:section>
  <msh:section title="Диагностика (Визиты)" shortList="js-smo_visit-infoShortByPatient.do?short=Short&amp;id=${patient_id}">
  <msh:sectionContent>
      <ecom:webQuery name="list1" nameFldSql="list_diag_" nativeSql="
      select
      vis.id,to_char(vis.datestart,'dd.mm.yyyy')||' - '||ms.code||'. '||ms.name||' - '||vwf.name||' '||wp.lastname as sloinfo
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
      where vis.patient_id='${patient_id}' and (vis.datestart between to_date('${datestart}','dd.mm.yyyy') and to_date('${datefinish}','dd.mm.yyyy')
       and upper(vis.dtype)='VISIT' and (vss.code='HOSPITAL' or vss.id='${serStreamId}' or vss.code='OTHER')
       or 
       vis.datestart-to_date('${datestart}','dd.mm.yyyy') = -1
       and upper(vis.dtype)='VISIT' and ( vss.id='${serStreamId}' )
       )
        and pp.priceList_id='${priceList}' and (pp.dateTo is null or pp.dateTo>=vis.dateStart)
        and (vis.noActuality='0' or vis.noActuality is null)
        order by vis.datestart
      "/>


    <msh:table name="list1" action="javascript:void(0)" idField="1" noDataMessage="Не найдено">
      <msh:tableColumn columnName="#" property="sn" />
      <msh:tableColumn columnName="Информация о визите" property="2" />
      <msh:tableColumn columnName="Наименование услуги" property="3" />
      <msh:tableColumn columnName="Сумма" property="4" isCalcAmount="true" />
    </msh:table>
  </msh:sectionContent>
  </msh:section>
  <msh:section>
  <msh:sectionTitle>Диагностика без соответствия</msh:sectionTitle>
  <msh:sectionContent>
      <ecom:webQuery name="list1" nativeSql="
      select
      vis.id,to_char(vis.datestart,'dd.mm.yyyy')||' - '||ms.code||'. '||ms.name||' - '||vwf.name||' '||wp.lastname as sloinfo
      from medcase vis
      left join workfunction wf on wf.id=vis.workfunctionexecute_id
      left join vocworkfunction vwf on vwf.id=wf.workfunction_id
      left join worker w on w.id=wf.worker_id
      left join patient wp on wp.id=w.person_id
      left join vocservicestream vss on vss.id=vis.servicestream_id
      left join medcase smc on smc.parent_id=vis.id and upper(smc.dtype)='SERVICEMEDCASE'
      left join medservice ms on ms.id=smc.medservice_id
    left join pricemedservice pms on pms.medservice_id=smc.medservice_id
    left join priceposition pp on pp.id=pms.priceposition_id and pp.priceList_id='${priceList}'
      where vis.patient_id='${patient_id}' and vis.datestart between to_date('${datestart}','dd.mm.yyyy')-1 and to_date('${datefinish}','dd.mm.yyyy')
       and upper(vis.dtype)='VISIT' and (vss.code='HOSPITAL' or vss.id='${serStreamId}' or vss.code='OTHER')
       and (vis.noActuality='0' or vis.noActuality is null)
       group by vis.id,vis.datestart,ms.code,ms.name,vwf.name,wp.lastname
       having count(pp.id)=0
       order by vis.datestart
      "/>


    <msh:table name="list1" action="javascript:void(0)" idField="1" noDataMessage="Не найдено">
      <msh:tableColumn columnName="#" property="sn" />
      <msh:tableColumn columnName="Информация о визите" property="2" />
      <msh:tableColumn columnName="Наименование услуги" property="3" />
      <msh:tableColumn columnName="Сумма" property="4" isCalcAmount="true" />
    </msh:table>
  </msh:sectionContent>
  </msh:section>
  <msh:section>
  <msh:sectionTitle>Лаборатория</msh:sectionTitle>
  <msh:sectionContent>
      <ecom:webQuery name="list" nativeSql="
select
      vis.id,to_char(vis.datestart,'dd.mm.yyyy')||' - '||ms.code||'. '||ms.name
      ,vwf.name||' '||wp.lastname as sloinfo
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
       and upper(vis.dtype)='VISIT' and upper(smc.dtype)='SERVICEMEDCASE'
        and (vss.code='HOSPITAL' or vss.id is null)
        and (vis.noActuality='0' or vis.noActuality is null) and pp.id is not null
       
      "/>
    <msh:table name="list" action="javascript:void(0)" idField="1" noDataMessage="Не найдено">
      <msh:tableColumn columnName="#" property="sn" />
      <msh:tableColumn columnName="Наименование услуги" property="2" />
      <msh:tableColumn columnName="Исполнитель" property="3" />
      <msh:tableColumn columnName="Наименование по прейскуранту" property="4" />
      <msh:tableColumn columnName="Цена" property="5" isCalcAmount="true" />
    </msh:table>
  </msh:sectionContent>
  </msh:section>
  <msh:section>
  <msh:sectionTitle>Лаборатория без соответствия</msh:sectionTitle>
  <msh:sectionContent>
      <ecom:webQuery name="list" nativeSql="
select
      vis.id,to_char(vis.datestart,'dd.mm.yyyy')||' - '||ms.code||'. '||ms.name
      ,vwf.name||' '||wp.lastname as sloinfo
     
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
       and upper(vis.dtype)='VISIT' and upper(smc.dtype)='SERVICEMEDCASE'
        and (vss.code='HOSPITAL' or vss.id is null)
        and (vis.noActuality='0' or vis.noActuality is null) 
       group by vis.id,vis.datestart,ms.code,ms.name,vwf.name,wp.lastname
        having count(pp.id)=0
      "/>
    <msh:table name="list" action="javascript:void(0)" idField="1" noDataMessage="Не найдено">
      <msh:tableColumn columnName="#" property="sn" />
      <msh:tableColumn columnName="Наименование услуги" property="2" />
      <msh:tableColumn columnName="Исполнитель" property="3" />
     
    </msh:table>
  </msh:sectionContent>
  </msh:section>
  <msh:section>
  <msh:sectionTitle>Операции</msh:sectionTitle>
  <msh:sectionContent>
      <ecom:webQuery name="list" nativeSql="
      select
      so.id,to_char(so.operationdate,'dd.mm.yyyy')||' - '||ms.code||'. '||ms.name||' - '||vwf.name||' '||wp.lastname as sloinfo
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
      and pp.id is not null and (pms.dateto is null or pms.dateto>=so.operationDate)
      "/>
    <msh:table name="list" action="javascript:void(0)" idField="1" noDataMessage="Не найдено">
      <msh:tableColumn columnName="#" property="sn" />
      <msh:tableColumn columnName="Наименование услуги" property="2" />
      <msh:tableColumn columnName="Наименование услуги по прейскуранту" property="3" />
      <msh:tableColumn columnName="Сумма" property="4" isCalcAmount="true" />
    </msh:table>
  </msh:sectionContent>
  </msh:section>
  <msh:section>
  <msh:sectionTitle>Операции без соответствия</msh:sectionTitle>
  <msh:sectionContent>
      <ecom:webQuery name="list" nativeSql="
      select
      so.id,to_char(so.operationdate,'dd.mm.yyyy')||' - '||ms.code||'. '||ms.name||' - '||vwf.name||' '||wp.lastname as sloinfo
     
      from SurgicalOperation so
      left join workfunction wf on wf.id=so.surgeon_id
      left join vocworkfunction vwf on vwf.id=wf.workfunction_id
      left join worker w on w.id=wf.worker_id
      left join patient wp on wp.id=w.person_id
      left join medcase slo on slo.id=so.medcase_id
      left join vocservicestream vss on vss.id=so.servicestream_id
      left join medservice ms on ms.id=so.medservice_id
    left join pricemedservice pms on pms.medservice_id=so.medservice_id and pms.dateto is null or pms.dateto>=so.operationdate
    left join priceposition pp on pp.id=pms.priceposition_id and pp.priceList_id='${priceList}'
      where
      (slo.parent_id='${param.id}' or slo.id='${param.id}')
      group by so.id,so.operationdate,ms.code,ms.name,vwf.name,wp.lastname 
     
        having count(pp.id)=0
      "/>
    <msh:table name="list" action="javascript:void(0)" idField="1" noDataMessage="Не найдено">
      <msh:tableColumn columnName="#" property="sn" />
      <msh:tableColumn columnName="Наименование услуги" property="2" />
     
    </msh:table>
  </msh:sectionContent>
  </msh:section>
  <msh:section>
  <msh:sectionTitle>Анестезия</msh:sectionTitle>
  <msh:sectionContent>
      <ecom:webQuery name="listA" nativeSql="
      select
      so.id,to_char(so.operationdate,'dd.mm.yyyy')
      ||' - '||coalesce(vam.name,'')
      ||' - '||coalesce(va.name,'')
      ||' - '||coalesce(aso.duration,'0')
      ||' - '||coalesce(ms.code||'. '||ms.name,'')
      ||' - '||vwf.name||' '||wp.lastname as sloinfo
      ,pp.code||' '||pp.name as ppname
      ,pp.cost as ppcost
      from Anesthesia aso 
      left join VocAnesthesiaMethod vam on vam.id=aso.method_id
      left join VocAnesthesia va on va.id=aso.type_id
      left join SurgicalOperation so on so.id=aso.surgicalOperation_id
      left join workfunction wf on wf.id=aso.anesthesist_id
      left join vocworkfunction vwf on vwf.id=wf.workfunction_id
      left join worker w on w.id=wf.worker_id
      left join patient wp on wp.id=w.person_id
      left join medcase slo on slo.id=so.medcase_id
      left join vocservicestream vss on vss.id=so.servicestream_id
      left join medservice ms on ms.id=aso.medservice_id
    left join pricemedservice pms on pms.medservice_id=aso.medservice_id
    left join priceposition pp on pp.id=pms.priceposition_id
      where
      (slo.parent_id='${param.id}' or slo.id='${param.id}') and (pms.id is null or  pp.priceList_id='${priceList}')
      
      "/>
    <msh:table name="listA" action="javascript:void(0)" idField="1" noDataMessage="Не найдено">
      <msh:tableColumn columnName="#" property="sn" />
      <msh:tableColumn columnName="Операция" property="2" />
      <msh:tableColumn columnName="Наименование услуги" property="3" />
      <msh:tableColumn columnName="Цена" property="4" />
      <msh:tableColumn columnName="Сумма" property="4" isCalcAmount="true" />
    </msh:table>
  </msh:sectionContent>
  </msh:section>
  <msh:section title="ДОП.УСЛУГИ" createUrl="js-smo_medService-add.do?id=${param.id}" createRoles="/Policy/Mis/MedCase/MedService/Create">
  <msh:sectionContent>
      <ecom:webQuery name="list" nativeSql="
      select
      so.id,to_char(so.dateStart,'dd.mm.yyyy')||' - '||ms.code||'. '||ms.name||' - '||vwf.name||' '||wp.lastname as sloinfo
      ,pp.code||' '||pp.name as ppname
      ,pp.cost as ppcost
      ,mkb.code as mkbcode
      ,coalesce(so.medserviceamount,'1') as f6_amount
      ,pp.cost*coalesce(so.medserviceamount,'1') as f7_sum
      from MedCase so
      left join VocIdc10 mkb on mkb.id=so.idc10_id
      left join workfunction wf on wf.id=so.workFunctionExecute_id
      left join vocworkfunction vwf on vwf.id=wf.workfunction_id
      left join worker w on w.id=wf.worker_id
      left join patient wp on wp.id=w.person_id
      left join medcase slo on slo.id=so.parent_id
      left join vocservicestream vss on vss.id=so.servicestream_id
      left join medservice ms on ms.id=so.medservice_id
    left join pricemedservice pms on pms.medservice_id=so.medservice_id
    left join priceposition pp on pp.id=pms.priceposition_id and pp.priceList_id='${priceList}'
      where
      (slo.parent_id='${param.id}' or slo.id='${param.id}')
      and upper(so.dtype)='SERVICEMEDCASE' and upper(slo.dtype)!='VISIT'
      and pp.id is not null
      "/>
    <msh:table name="list" action="javascript:void(0)" idField="1" noDataMessage="Не найдено">
      <msh:tableColumn columnName="#" property="sn" />
      <msh:tableColumn columnName="Наименование услуги" property="2" />
      <msh:tableColumn columnName="Кол-во" property="6" />
      <msh:tableColumn columnName="Цена" property="4" />
      <msh:tableColumn columnName="Сумма" property="7" isCalcAmount="true" />
      <msh:tableColumn columnName="МКБ10" property="5" />
    </msh:table>
  </msh:sectionContent>
  </msh:section>
  <msh:section title="ДОП.УСЛУГИ без соответствия" createUrl="js-smo_medService-add.do?id=${param.id}" createRoles="/Policy/Mis/MedCase/MedService/Create">
  <msh:sectionContent>
      <ecom:webQuery name="list" nativeSql="
      select
      so.id,to_char(so.dateStart,'dd.mm.yyyy')||' - '||ms.code||'. '||ms.name||' - '||vwf.name||' '||wp.lastname as sloinfo
      ,mkb.code as mkbcode
      from MedCase so
      left join VocIdc10 mkb on mkb.id=so.idc10_id
      left join workfunction wf on wf.id=so.workFunctionExecute_id
      left join vocworkfunction vwf on vwf.id=wf.workfunction_id
      left join worker w on w.id=wf.worker_id
      left join patient wp on wp.id=w.person_id
      left join medcase slo on slo.id=so.parent_id
      left join vocservicestream vss on vss.id=so.servicestream_id
      left join medservice ms on ms.id=so.medservice_id
    left join pricemedservice pms on pms.medservice_id=so.medservice_id
    left join priceposition pp on pp.id=pms.priceposition_id and pp.priceList_id='${priceList}'
      where
      (slo.parent_id='${param.id}' or slo.id='${param.id}')
      and upper(so.dtype)='SERVICEMEDCASE' and upper(slo.dtype)!='VISIT'
      group by so.id,so.dateStart,ms.code,ms.name,vwf.name,wp.lastname ,mkb.code
      having count(pp.id)=0
      "/>
    <msh:table name="list" action="javascript:void(0)" idField="1" noDataMessage="Не найдено">
      <msh:tableColumn columnName="#" property="sn" />
      <msh:tableColumn columnName="Наименование услуги" property="1" />
      <msh:tableColumn columnName="Кол-во" property="2" />
      <msh:tableColumn columnName="МКБ10" property="3" />
    </msh:table>
  </msh:sectionContent>
  </msh:section>
  <%} %>
  </tiles:put>
</tiles:insert>
