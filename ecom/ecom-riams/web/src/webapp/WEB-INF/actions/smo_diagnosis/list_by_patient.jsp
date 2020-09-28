<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://struts.apache.org/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://www.nuzmsh.ru/tags/msh" prefix="msh" %>
<%@ taglib uri="http://www.ecom-ast.ru/tags/ecom" prefix="ecom" %>
<%@ taglib tagdir="/WEB-INF/tags" prefix="tags" %>

<tiles:insert page="/WEB-INF/tiles/mainLayout.jsp" flush="true" >

  <tiles:put name="title" type="string">
    <ecom:titleTrail beginForm="mis_patientForm" mainMenu="Patient" title="Список всех диагнозов" />
  </tiles:put>
  <tiles:put name="side" type="string">
  <tags:style_currentMenu currentAction='inform'/>
    <msh:sideMenu title="Добавить">
    	<msh:sideLink name="Диагноз" params="id" roles="/Policy/Mis/MedCase/Diagnosis/Create" key="ALT+2" action="/entityParentPrepareCreate-mis_diagnosis.do"/>
    </msh:sideMenu>
    <msh:sideMenu>
      <msh:sideLink params="id" action="/entityView-mis_patient" name="Пациент" />
        <msh:sideLink params="id" action="/js-smo_visit-infoByPatient" name="Информация по визитам" title="Показать информацию посещений по пациенту" roles="/Policy/Mis/MedCase/Spo/View" />
        <msh:sideLink styleId="inform" params="id" action="/js-smo_diagnosis-infoByPatient" name="Диагнозы" title="Показать все диагнозы" roles="/Policy/Mis/MedCase/Diagnosis/View" />
        <msh:sideLink params="id" action="/js-smo_visitProtocol-infoByPatient" name="Заключения" title="Показать все заключения" roles="/Policy/Mis/MedCase/Diagnosis/View" />
        
   </msh:sideMenu>
  </tiles:put>
  <tiles:put name="body" type="string">
    <ecom:webQuery name="list" nativeSql="select 
    d.id, d.establishDate , d.name 
    , vi.code || ' ' || vi.name   as idc10
    , vad.name                 as acuity
    , vk.code || ' ' || vk.name       as ksg   
    , case when (visit.DTYPE='Visit' or visit.DTYPE='PolyclinicMedCase' or visit.dtype='ShortMedCase') then 'Поликлиника' 
    	when (visit.DTYPE='HospitalMedCase' or visit.DTYPE='DepartmentMedCase') then 'Стационар' 
    	when (visit.DTYPE='ServiceMedCase') then 'Услуги' 
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
    order by d.establishDate" />
    <msh:table name="list" action="entityView-mis_diagnosis.do" idField="1">
      <msh:tableColumn columnName="№" property="sn" />
      <msh:tableColumn columnName="ПО" property="7" />
      <msh:tableColumn columnName="Дата установления" property="2" />
      <msh:tableColumn columnName="Наименование" property="3" />
      <msh:tableColumn columnName="Код МКБ" property="4" />
      <msh:tableColumn columnName="Острота" property="5" />
      <msh:tableColumn columnName="Приоритет" property="8" />
    </msh:table>
  </tiles:put>
</tiles:insert>