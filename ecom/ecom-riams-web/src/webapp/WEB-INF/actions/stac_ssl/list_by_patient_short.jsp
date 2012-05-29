<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://struts.apache.org/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://www.nuzmsh.ru/tags/msh" prefix="msh" %>
<%@ taglib uri="http://www.ecom-ast.ru/tags/ecom" prefix="ecom" %>
<%@ taglib tagdir="/WEB-INF/tags" prefix="tags" %>

<tiles:insert page="/WEB-INF/tiles/mainShortLayout.jsp" flush="true" >

  <tiles:put name="title" type="string">
    <ecom:titleTrail beginForm="mis_patientForm" mainMenu="Patient" title="Список всех госпитализаций"/>
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
    mc.id,mc.dateStart,mc.dateFinish,dep.name as depname,vdh.name as vdhname
    , case when mc.emergency='1' then 'экстренные' else 'плановые' end as emer
    , list(mkb.code||' '||diag.name||' ('||vdrt.name||' '||vpd.name||')')
    from medcase mc
    left join diagnosis diag on diag.medcase_id=mc.id
    left join vocidc10 mkb on mkb.id=diag.idc10_id
    left join VocDiagnosisRegistrationType vdrt on vdrt.id=diag.registrationType_id
    left join VocPriorityDiagnosis vpd on vpd.id=diag.priority_id
    left join MisLpu dep on dep.id=mc.department_id
    left join VocDeniedHospitalizating vdh on vdh.id=mc.deniedHospitalizating_id
    where  mc.patient_id=${param.id} and mc.dtype='HospitalMedCase'
    group by mc.id,mc.dateStart,mc.dateFinish,dep.name,vdh.name
    , mc.emergency
    order by mc.dateStart" guid="2d59a9bf-327f-4f4f-8336-531458b6caed" />

    <msh:table name="list" action="entityView-stac_ssl.do" idField="1" 
    viewUrl="entityShortView-stac_ssl.do">
      <msh:tableColumn columnName="№" property="sn" guid="0694f6a7-ed40-4ebf-a274-1efd6901cfe4-1" />
      <msh:tableColumn columnName="Дата госпит." property="2" />
      <msh:tableColumn columnName="Дата выписки" property="3" guid="0694f6a7-ed40-4ebf-a274-1efd6901cfe4" />
      <msh:tableColumn columnName="Отделение госпит." property="4" guid="6682eeef-105f-43a0-be61-30a865f27972" />
      <msh:tableColumn columnName="Показания к госпитализации" property="6" guid="6682eeef-105f-43a0-be61-30a865f27972" />
      <msh:tableColumn columnName="Диагноз" property="7" guid="f34e1b12-3392-4978-b31f-5e54ff2e45bd" />
      <msh:tableColumn columnName="Причина отказа от госпитализации" property="5" guid="f34e1b12-3392-4978-b31f-5e54ff2e45bd" />
    </msh:table>
  </tiles:put>
</tiles:insert>  