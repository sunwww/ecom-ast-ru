<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://struts.apache.org/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://www.nuzmsh.ru/tags/msh" prefix="msh" %>
<%@ taglib uri="http://www.ecom-ast.ru/tags/ecom" prefix="ecom" %>

<tiles:insert page="/WEB-INF/tiles/mainLayout.jsp" flush="true" >

  <tiles:put name="title" type="string">
    <ecom:titleTrail beginForm="mis_patientForm" mainMenu="Patient" title="Список всех диагнозов" />
  </tiles:put>
  <tiles:put name="side" type="string">
    <msh:sideMenu />
  </tiles:put>
  <tiles:put name="body" type="string">
    <ecom:webQuery name="list" nativeSql="select diagnosis.id&#xA;&#x9;, establishDate&#xA;        , diagnosis.name &#xA;&#x9;, VocIdc10.code || ' ' || VocIdc10.name   as idc10&#xA;&#x9;, VocAcuityDiagnosis.name                 as acuity&#xA;&#x9;, VocKsg.code || ' ' || VocKsg.name       as ksg&#xA;        from diagnosis&#xA; inner join MedCase visit            on Diagnosis.medcase_id  = visit.id&#xA; inner join MedCase spo              on visit.parent_id       = spo.id&#xA; inner join Patient                  on visit.patient_id      = patient.id&#xA; left  outer join VocIdc10           on Diagnosis.idc10_id    = VocIdc10.id&#xA; left  outer join VocAcuityDiagnosis on Diagnosis.acuity_id   = VocAcuityDiagnosis.id&#xA; left  outer join VocKsg on Diagnosis.clinicStatisticGroup_id = VocKsg.id&#xA;where  patient.id=${param.id}&#xA; order by establishDate" />
    <msh:table name="list" action="entityView-smo_diagnosis.do" idField="1">
      <msh:tableColumn columnName="№" property="sn" />
      <msh:tableColumn columnName="Дата установления" property="2" />
      <msh:tableColumn columnName="Наименование" property="3" />
      <msh:tableColumn columnName="Код МКБ" property="4" />
      <msh:tableColumn columnName="Острота" property="5" />
    </msh:table>
  </tiles:put>
</tiles:insert>

