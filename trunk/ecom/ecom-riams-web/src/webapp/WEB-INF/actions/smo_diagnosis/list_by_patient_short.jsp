<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://struts.apache.org/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://www.nuzmsh.ru/tags/msh" prefix="msh" %>
<%@ taglib uri="http://www.ecom-ast.ru/tags/ecom" prefix="ecom" %>
<%@ taglib tagdir="/WEB-INF/tags" prefix="tags" %>

<tiles:insert page="/WEB-INF/tiles/mainShortLayout.jsp" flush="true" >

  <tiles:put name="title" type="string">
    <ecom:titleTrail beginForm="mis_patientForm" mainMenu="Patient" title="Список всех диагнозов" guid="40efbd1b-4177-47a8-9aad-1971732f3f98" />
  </tiles:put>
  <tiles:put name="side" type="string">
  <tags:style_currentMenu currentAction='inform'/>
    <msh:sideMenu guid="helloSideMenu-123">
      <msh:sideLink params="id" guid="Перейти к пациенту" action="/entityView-mis_patient" name="Пациент" />
        <msh:sideLink params="id" action="/js-smo_visit-infoByPatient" name="Информация по визитам" title="Показать информацию посещений по пациенту" guid="dd2ad6a3-5fb2-4586-a24e-1a0f1b796397" roles="/Policy/Mis/MedCase/Spo/View" />
        <msh:sideLink styleId="inform" params="id" action="/js-smo_diagnosis-infoByPatient" name="Диагнозы" title="Показать все диагнозы" guid="68b36632-8d07-4a87-b469-6695694b2bab" roles="/Policy/Mis/MedCase/Diagnosis/View" />
        <msh:sideLink params="id" action="/js-smo_visitProtocol-infoByPatient" name="Заключения" title="Показать все заключения" guid="68b36632-8d07-4a87-b469-6695694b2bab" roles="/Policy/Mis/MedCase/Diagnosis/View" />
        
   </msh:sideMenu>
  </tiles:put>
  <tiles:put name="body" type="string">
    <ecom:webQuery name="list" nativeSql="select 
    d.id, d.establishDate , d.name 
    , vi.code || ' ' || vi.name   as idc10
    , vad.name                 as acuity
    , vk.code || ' ' || vk.name       as ksg   
    , case when (visit.DTYPE='VISIT' or visit.DTYPE='POLYCLINICMEDCASE') then 'Поликлиника' 
    	when (visit.DTYPE='HOSPITALMEDCASE' or visit.DTYPE='DEPARTMENTMEDCASE') then 'Стационар' 
    	when (visit.DTYPE='SERVICEMEDCASE') then 'Услуги' 
    	else 'неизвестно'
    	end   
    , vpd.name as priority
    from diagnosis d 
    left join MedCase visit            on d.medcase_id  = visit.id 
    left join MedCase spo              on visit.parent_id       = spo.id
    left join Patient p                 on d.patient_id      = p.id 
    left   join VocIdc10 vi          on d.idc10_id    = vi.id 
    left   join VocAcuityDiagnosis vad on d.acuity_id   = vad.id
    left   join VocKsg vk on d.clinicStatisticGroup_id = vk.id
    left join VocPriorityDiagnosis vpd on vpd.id=d.priority_id
    where  d.patient_id=${param.id} 
    order by d.establishDate" guid="2d59a9bf-327f-4f4f-8336-531458b6caed" />
    <msh:table name="list" action="entityView-mis_diagnosis.do" idField="1" guid="b621e361-1e0b-4ebd-9f58-b7d919b45bd6">
      <msh:tableColumn columnName="№" property="sn" guid="0694f6a7-ed40-4ebf-a274-1efd6901cfe4-1" />
      <msh:tableColumn columnName="ПО" property="7" />
      <msh:tableColumn columnName="Дата установления" property="2" guid="0694f6a7-ed40-4ebf-a274-1efd6901cfe4" />
      <msh:tableColumn columnName="Наименование" property="3" guid="6682eeef-105f-43a0-be61-30a865f27972" />
      <msh:tableColumn columnName="Код МКБ" property="4" guid="f34e1b12-3392-4978-b31f-5e54ff2e45bd" />
      <msh:tableColumn columnName="Острота" property="5" guid="7f7d011d-624c-4003-9c7d-4db6e3dda647" />
      <msh:tableColumn columnName="Приоритет" property="8" guid="7f7d011d-624c-4003-9c7d-4db6e3dda647" />
    </msh:table>
  </tiles:put>
</tiles:insert>