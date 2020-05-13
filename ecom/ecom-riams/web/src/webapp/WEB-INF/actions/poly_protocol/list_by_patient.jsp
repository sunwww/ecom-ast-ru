<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://struts.apache.org/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://www.nuzmsh.ru/tags/msh" prefix="msh" %>
<%@ taglib uri="http://www.ecom-ast.ru/tags/ecom" prefix="ecom" %>
<%@ taglib tagdir="/WEB-INF/tags" prefix="tags" %>

<tiles:insert page="/WEB-INF/tiles/mainLayout.jsp" flush="true" >

  <tiles:put name="title" type="string">
    <ecom:titleTrail beginForm="mis_patientForm" mainMenu="Patient" title="Просмотр информации по посещениям"/>
  </tiles:put>
  <tiles:put name="side" type="string">
  <tags:style_currentMenu currentAction='informByTicket'/>
    <msh:sideMenu>
      <msh:sideLink params="id" action="/entityView-mis_patient" name="Пациент" />
        <msh:sideLink styleId="inform" params="id" action="/js-smo_visit-infoByPatient" name="Информация по визитам" title="Показать информацию посещений по пациенту" roles="/Policy/Mis/MedCase/Spo/View" />
        <msh:sideLink styleId="informByTicket" params="id" action="/js-poly_protocol-infoByPatient" name="Информация по талонам" title="Показать информацию посещений по пациенту (талоны)" roles="/Policy/Poly/Ticket/View,/Policy/Mis/MedCase/Protocol/View" />
        <msh:sideLink  params="id" action="/js-smo_diagnosis-infoByPatient" name="Диагнозы" title="Показать все диагнозы" roles="/Policy/Mis/MedCase/Diagnosis/View" />
        <msh:sideLink  params="id" action="/js-smo_visitProtocol-infoByPatient" name="Заключения" title="Показать все заключения" roles="/Policy/Mis/MedCase/Diagnosis/View" />
        
    </msh:sideMenu>
  </tiles:put>
  <tiles:put name="body" type="string">
  <msh:section>
  	<ecom:webQuery name="listByPatient" nativeSql="select t.id
,t.date as datstart, 
vwf.name ||' '|| wp.lastname ||' '|| wp.firstname ||' '|| wp.middlename as wfExecute,
vpd.name as vpdname ,mkb.code,mkb.name as dsname,prot.record as protrecord, vr.name as vrname, vvr.name as vvrname
 from ticket t 
left join vocreason vr on vr.id=t.vocreason_id
left join vocvisitresult vvr on vvr.id = t.vocvisitresult_id
left join vocidc10 mkb on mkb.id=t.idc10_id
left join VocPrimaryDiagnosis vpd on vpd.id = t.primary_id
left join diary prot on prot.ticket_id=t.id
left join workFunction wf on wf.id=t.workFunction_id
left join vocWorkFunction vwf on vwf.id=wf.workFunction_id
left join worker w on w.id=wf.worker_id
left join patient wp on wp.id=w.person_id
left join medcard m on m.id=t.medcard_id
where m.person_id='${param.id}'  and t.date is not null and t.status='2'"/>
    <msh:table name="listByPatient" action="entityView-poly_ticket.do" idField="1">
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
    </msh:section>
  </tiles:put>
</tiles:insert>

