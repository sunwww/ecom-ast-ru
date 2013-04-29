<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://struts.apache.org/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://www.nuzmsh.ru/tags/msh" prefix="msh" %>
<%@ taglib uri="http://www.ecom-ast.ru/tags/ecom" prefix="ecom" %>
<%@ taglib tagdir="/WEB-INF/tags" prefix="tags" %>

<tiles:insert page="/WEB-INF/tiles/mainLayout.jsp" flush="true" >

  <tiles:put name="title" type="string">
    <ecom:titleTrail guid="helloItle-123" beginForm="poly_medcardForm" mainMenu="Patient" title="Просмотр информации по посещениям"/>
  </tiles:put>
  <tiles:put name="side" type="string">
  <tags:style_currentMenu currentAction='inform'/>
    <msh:sideMenu guid="helloSideMenu-123">
      <msh:sideLink params="id" guid="Перейти к мед.карте" action="/entityView-poly_ticket" name="Мед.карта" />
        
    </msh:sideMenu>
  </tiles:put>
  <tiles:put name="body" type="string">
  	<ecom:webQuery name="listByMedcard" nativeSql="select t.id
,t.datestart as datstart, 
vwf.name ||' '|| wp.lastname ||' '|| wp.firstname ||' '|| wp.middlename as wfExecute,
(select vpd.name ||' '||mkb.code||' '||mkb.name 
from diagnosis diag
left join vocidc10 mkb on mkb.id=diag.idc10_id
left join VocPrimaryDiagnosis vpd on vpd.id = diag.primary_id
where diag.medcase_id=t.id) as diag,prot.record as protrecord, vr.name as vrname, vvr.name as vvrname
 from MedCase t 
left join vocreason vr on vr.id=t.visitreason_id
left join vocvisitresult vvr on vvr.id = t.visitresult_id

left join diary prot on prot.medCase_id=t.id
left join workFunction wf on wf.id=t.workFunctionExecute_id
left join vocWorkFunction vwf on vwf.id=wf.workFunction_id
left join worker w on w.id=wf.worker_id
left join patient wp on wp.id=w.person_id
left join medcard m on m.id=t.medcard_id
where m.id='${param.id}' and t.dtype='ShortMedCase' and t.dateStart is not null"/>
    <msh:table name="listByMedcard" action="entityView-poly_ticket.do" idField="1" guid="b621e361-1e0b-4ebd-9f58-b7d919b45bd6">
      <msh:tableColumn columnName="#" property="sn" />
      <msh:tableColumn columnName="Дата приема" property="2" />
      <msh:tableColumn columnName="Специалист" property="3"/>
      <msh:tableColumn columnName="Приоритет диаг." property="4"/>
      <msh:tableColumn columnName="Код МКБ" property="5"/>
      <msh:tableColumn columnName="Диагноз" property="6"/>
      <msh:tableColumn columnName="Заключение" property="7" cssClass="preCell"/>
      <msh:tableColumn columnName="Цель визита" property="8"/>
      <msh:tableColumn columnName="Результат" property="9"/>
      
    </msh:table>
  </tiles:put>
</tiles:insert>

