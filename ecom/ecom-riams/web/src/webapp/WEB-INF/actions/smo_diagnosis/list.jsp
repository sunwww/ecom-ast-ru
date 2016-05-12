<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://struts.apache.org/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://www.nuzmsh.ru/tags/msh" prefix="msh" %>
<%@ taglib uri="http://www.ecom-ast.ru/tags/ecom" prefix="ecom" %>

<tiles:insert page="/WEB-INF/tiles/mainLayout.jsp" flush="true" >

  <tiles:put name="title" type="string">
    <ecom:titleTrail beginForm="mis_patientForm" mainMenu="Patient" title="Список всех диагнозов" guid="40efbd1b-4177-47a8-9aad-1971732f3f98" />
  </tiles:put>
  <tiles:put name="side" type="string">
    <msh:sideMenu guid="helloSideMenu-123" />
  </tiles:put>
  <tiles:put name="body" type="string">
    <ecom:webQuery name="list" nativeSql="select diagnosis.id&#xA;&#x9;, establishDate&#xA;        , diagnosis.name &#xA;&#x9;, VocIdc10.code || ' ' || VocIdc10.name   as idc10&#xA;&#x9;, VocAcuityDiagnosis.name                 as acuity&#xA;&#x9;, VocKsg.code || ' ' || VocKsg.name       as ksg&#xA;        from diagnosis&#xA; inner join MedCase visit            on Diagnosis.medcase_id  = visit.id&#xA; inner join MedCase spo              on visit.parent_id       = spo.id&#xA; inner join Patient                  on visit.patient_id      = patient.id&#xA; left  outer join VocIdc10           on Diagnosis.idc10_id    = VocIdc10.id&#xA; left  outer join VocAcuityDiagnosis on Diagnosis.acuity_id   = VocAcuityDiagnosis.id&#xA; left  outer join VocKsg on Diagnosis.clinicStatisticGroup_id = VocKsg.id&#xA;where  patient.id=${param.id}&#xA; order by establishDate" guid="2d59a9bf-327f-4f4f-8336-531458b6caed" />
    <msh:table name="list" action="entityView-smo_diagnosis.do" idField="1" guid="b621e361-1e0b-4ebd-9f58-b7d919b45bd6">
      <msh:tableColumn columnName="№" property="sn" guid="0694f6a7-ed40-4ebf-a274-1efd6901cfe4-1" />
      <msh:tableColumn columnName="Дата установления" property="2" guid="0694f6a7-ed40-4ebf-a274-1efd6901cfe4" />
      <msh:tableColumn columnName="Наименование" property="3" guid="6682eeef-105f-43a0-be61-30a865f27972" />
      <msh:tableColumn columnName="Код МКБ" property="4" guid="f34e1b12-3392-4978-b31f-5e54ff2e45bd" />
      <msh:tableColumn columnName="Острота" property="5" guid="7f7d011d-624c-4003-9c7d-4db6e3dda647" />
    </msh:table>
  </tiles:put>
</tiles:insert>

