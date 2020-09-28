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
    <msh:sideMenu>
      <msh:sideLink params="id" action="/entityView-mis_patient" name="Пациент" />
        <msh:sideLink params="id" action="/js-smo_visit-infoByPatient" name="Информация по визитам" title="Показать информацию посещений по пациенту" roles="/Policy/Mis/MedCase/Spo/View" />
        <msh:sideLink styleId="inform" params="id" action="/js-smo_diagnosis-infoByPatient" name="Диагнозы" title="Показать все диагнозы" roles="/Policy/Mis/MedCase/Diagnosis/View" />
        <msh:sideLink params="id" action="/js-smo_visitProtocol-infoByPatient" name="Заключения" title="Показать все заключения" roles="/Policy/Mis/MedCase/Diagnosis/View" />
        
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
    order by mc.dateStart" />

    <msh:table name="list" action="entityView-stac_ssl.do" idField="1" 
    viewUrl="entityShortView-stac_ssl.do">
      <msh:tableColumn columnName="№" property="sn" />
      <msh:tableColumn columnName="Дата госпит." property="2" />
      <msh:tableColumn columnName="Дата выписки" property="3" />
      <msh:tableColumn columnName="Отделение госпит." property="4" />
      <msh:tableColumn columnName="Показания к госпитализации" property="6" />
      <msh:tableColumn columnName="Диагноз" property="7" />
      <msh:tableColumn columnName="Причина отказа от госпитализации" property="5" />
    </msh:table>
  </tiles:put>
</tiles:insert>  