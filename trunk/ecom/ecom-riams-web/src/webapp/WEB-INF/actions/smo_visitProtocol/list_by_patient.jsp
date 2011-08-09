<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://struts.apache.org/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://www.nuzmsh.ru/tags/msh" prefix="msh" %>
<%@ taglib uri="http://www.ecom-ast.ru/tags/ecom" prefix="ecom" %>
<%@ taglib tagdir="/WEB-INF/tags" prefix="tags" %>

<tiles:insert page="/WEB-INF/tiles/mainLayout.jsp" flush="true" >

  <tiles:put name="title" type="string">
    <ecom:titleTrail beginForm="mis_patientForm" mainMenu="Patient" title="Список всех дневников" guid="40efbd1b-4177-47a8-9aad-1971732f3f98" />
  </tiles:put>
  <tiles:put name="side" type="string">
  <tags:style_currentMenu currentAction='inform'/>
    <msh:sideMenu guid="helloSideMenu-123">
      <msh:sideLink params="id" guid="Перейти к пациенту" action="/entityView-mis_patient" name="Пациент" />
        <msh:sideLink params="id" action="/js-smo_visit-infoByPatient" name="Информация по визитам" title="Показать информацию посещений по пациенту" guid="dd2ad6a3-5fb2-4586-a24e-1a0f1b796397" roles="/Policy/Mis/MedCase/Spo/View" />
        <msh:sideLink  params="id" action="/js-smo_diagnosis-infoByPatient" name="Диагнозы" title="Показать все диагнозы" guid="68b36632-8d07-4a87-b469-6695694b2bab" roles="/Policy/Mis/MedCase/Diagnosis/View" />
        <msh:sideLink styleId="inform"  params="id" action="/js-smo_visitProtocol-infoByPatient" name="Заключения" title="Показать все заключения" guid="68b36632-8d07-4a87-b469-6695694b2bab" roles="/Policy/Mis/MedCase/Diagnosis/View" />
        
    </msh:sideMenu>
  </tiles:put>
  <tiles:put name="body" type="string">
    <ecom:webQuery name="listByPatient" nativeSql="select pr.id, pr.dateREgistration,pr.timeRegistration,pr.record,vwf.name||' '||wp.lastname||' '||wp.firstname|| ' '||wp.middlename, case when (mc.DTYPE='VISIT' or mc.DTYPE='POLYCLINICMEDCASE') then 'Поликлиника' when mc.DTYPE='SERVICEMEDCASE' then 'Услуга' else 'Стационар' end from diary pr left join medcase mc on mc.id=pr.medcase_id  left join workfunction wf on wf.id=pr.specialist_id left join worker w on w.id=wf.worker_id left join vocWorkFunction vwf on vwf.id=wf.workFunction_id left join patient wp on wp.id=w.person_id where mc.patient_id='${param.id}' "/>
    <msh:tableNotEmpty name="listByPatient">
  <msh:section title="Заключения по медицинским случаям обслуживания">
    <msh:table name="listByPatient" action="entityParentView-smo_visitProtocol.do" idField="1" guid="b621e361-1e0b-4ebd-9f58-b7d919b45bd6">
      <msh:tableColumn property="6" columnName="ПО"/>
      <msh:tableColumn columnName="Дата" property="2" guid="0694f6a7-ed40-4ebf-a274-1efd6901cfe4" />
      <msh:tableColumn columnName="Время" property="3" guid="0694f40-4ebf-a274-1efd6901cfe4" />
      <msh:tableColumn columnName="Текст" property="4" guid="6682eeef-105f-43a0-be61-30a865f27972" cssClass="preCell"/>
      <msh:tableColumn columnName="Специалист" property="5" guid="f31b12-3392-4978-b31f-5e54ff2e45bd" />
    </msh:table>
  </msh:section>
  </msh:tableNotEmpty>
    <ecom:webQuery name="listByPatientTic" nativeSql="select pr.id, pr.dateREgistration,pr.timeRegistration,pr.record,vwf.name||' '||wp.lastname||' '||wp.firstname|| ' '||wp.middlename from diary pr left join ticket tic on tic.id=pr.ticket_id  left join workfunction wf on wf.id=pr.specialist_id left join worker w on w.id=wf.worker_id left join medcard m on m.id=tic.medcard_id left join vocWorkFunction vwf on vwf.id=wf.workFunction_id left join patient wp on wp.id=w.person_id where m.person_id='${param.id}' "/>
    <msh:tableNotEmpty name="listByPatientTic">
    <msh:section title="Заключения по талонам">
    <msh:table name="listByPatientTic" action="entityParentView-poly_protocol.do" idField="1" guid="b621e361-1e0b-4ebd-9f58-b7d919b45bd6">
      <msh:tableColumn columnName="1" property="dateRegistration" guid="0694f6a7-ed40-4ebf-a274-1efd6901cfe4" />
      <msh:tableColumn columnName="2" property="timeRegistration" guid="0694f40-4ebf-a274-1efd6901cfe4" />
      <msh:tableColumn columnName="3" property="record" guid="6682eeef-105f-43a0-be61-30a865f27972" cssClass="preCell"/>
      <msh:tableColumn columnName="4" property="specialistInfo" guid="f31b12-3392-4978-b31f-5e54ff2e45bd" />
    </msh:table>
  </msh:section>
  </msh:tableNotEmpty>
  </tiles:put>
</tiles:insert>