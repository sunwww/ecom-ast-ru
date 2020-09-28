<%@page import="ru.ecom.ejb.services.query.WebQueryResult"%>
<%@page import="java.util.List"%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://struts.apache.org/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://www.nuzmsh.ru/tags/msh" prefix="msh" %>
<%@ taglib uri="http://www.ecom-ast.ru/tags/ecom" prefix="ecom" %>
<%@ taglib tagdir="/WEB-INF/tags" prefix="tags" %>

<tiles:insert page="/WEB-INF/tiles/main${param.short}Layout.jsp" flush="true" >

  <tiles:put name="title" type="string">
    <msh:title mainMenu="StacJournal" title="Стоимость поликлиническго обслуживания (СПО)" />
  </tiles:put>
  <tiles:put name="side" type="string">
   
  </tiles:put>
  <tiles:put name="body" type="string">
  <ecom:webQuery name="patinfoQ" nativeSql="select m.patient_id,to_char(m.datestart,'dd.mm.yyyy') as dstart
  ,to_char(coalesce(m.datefinish,current_date),'dd.mm.yyyy') as dfinish,m.serviceStream_id as servstream from medcase m where m.id=${param.id}
  "/>
  <%
  request.setAttribute("priceList", "4");
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
  <msh:sectionTitle>
  <input type="button" onclick="showVISITGetAccount('${param.id}')" value="ПРИВЯЗАТЬ К СЧЕТУ"/>
  </msh:sectionTitle>  
  <msh:sectionTitle>Услуги</msh:sectionTitle>
  <msh:sectionContent>
      <ecom:webQuery name="list" nameFldSql="list_sql" nativeSql="
select
      vis.id,vis.datestart||' - '||ms.code||'. '||ms.name,vwf.name||' '||wp.lastname as sloinfo
      ,pp.code||' '||pp.name as ppname
      ,pp.cost as ppcost
      ,coalesce(smc.medserviceAmount,1) as cntService
      ,pp.cost * coalesce(smc.medserviceAmount,1) as ppSum
      from medcase vis
      left join workfunction wf on wf.id=vis.workfunctionexecute_id
      left join vocworkfunction vwf on vwf.id=wf.workfunction_id
      left join worker w on w.id=wf.worker_id
      left join patient wp on wp.id=w.person_id
      left join vocservicestream vss on vss.id=vis.servicestream_id
      left join medcase smc on smc.parent_id=vis.id
      left join medservice ms on ms.id=smc.medservice_id
    left join pricemedservice pms on pms.medservice_id=smc.medservice_id
    left join priceposition pp on pp.id=pms.priceposition_id 
      where vis.id='${param.id}'
       and upper(vis.dtype) in ('VISIT','SHORTMEDCASE') and upper(smc.dtype)='SERVICEMEDCASE'
        and (vis.noActuality='0' or vis.noActuality is null) and pp.priceList_id='${priceList}'
       
      "/>
    <msh:table name="list" action="javascript:void(0)" idField="1" noDataMessage="Не найдено">
      <msh:tableColumn columnName="#" property="sn" />
      <msh:tableColumn columnName="Наименование услуги" property="2" />
      <msh:tableColumn columnName="Исполнитель" property="3" />
      <msh:tableColumn columnName="Кол-во" property="6" />
      <msh:tableColumn columnName="Цена" property="5" />
      <msh:tableColumn columnName="Сумма" property="7" isCalcAmount="true" />
    </msh:table>
  </msh:sectionContent>
  <msh:sectionTitle>ДОП.УСЛУГИ</msh:sectionTitle>
  <msh:sectionContent>
      <ecom:webQuery name="list" nativeSql="
      select
      so.id,to_char(so.dateStart,'dd.mm.yyyy')||' - '||ms.code||'. '||ms.name||' - '||vwf.name||' '||wp.lastname as sloinfo
      ,pp.code||' '||pp.name as ppname
      ,pp.cost as ppcost
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
      and upper(so.dtype)='SERVICEMEDCASE' and upper(slo.dtype)!='VISIT' and upper(slo.dtype)!='SHORTMEDCASE' 
      "/>
    <msh:table name="list" action="javascript:void(0)" idField="1" noDataMessage="Не найдено">
      <msh:tableColumn columnName="#" property="sn" />
      <msh:tableColumn columnName="Наименование услуги" property="1" />
      <msh:tableColumn columnName="Кол-во" property="2" />
      <msh:tableColumn columnName="Цена" property="3" />
      <msh:tableColumn columnName="Сумма" property="4" isCalcAmount="true" />
      <msh:tableColumn columnName="МКБ10" property="5" />
    </msh:table>
  </msh:sectionContent>
  </msh:section>
  
  <%} %>
  </tiles:put>
</tiles:insert>
