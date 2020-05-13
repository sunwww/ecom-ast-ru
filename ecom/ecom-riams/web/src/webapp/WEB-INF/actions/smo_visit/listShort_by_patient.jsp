<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://struts.apache.org/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://www.nuzmsh.ru/tags/msh" prefix="msh" %>
<%@ taglib uri="http://www.ecom-ast.ru/tags/ecom" prefix="ecom" %>
<%@ taglib tagdir="/WEB-INF/tags" prefix="tags" %>

<tiles:insert page="/WEB-INF/tiles/mainShortLayout.jsp" flush="true" >

  <tiles:put name="title" type="string">
    <ecom:titleTrail beginForm="mis_patientForm" mainMenu="Patient" title="Просмотр информации по посещениям"/>
  </tiles:put>
  <tiles:put name="side" type="string">
  <tags:style_currentMenu currentAction='inform'/>
    <msh:sideMenu>
      <msh:sideLink params="id" action="/entityView-mis_patient" name="Пациент" />
        <msh:sideLink styleId="inform" params="id" action="/js-smo_visit-infoByPatient" name="Информация по визитам" title="Показать информацию посещений по пациенту" roles="/Policy/Mis/MedCase/Spo/View" />
        <msh:sideLink  params="id" action="/js-smo_diagnosis-infoByPatient" name="Диагнозы" title="Показать все диагнозы" roles="/Policy/Mis/MedCase/Diagnosis/View" />
        <msh:sideLink  params="id" action="/js-smo_visitProtocol-infoByPatient" name="Заключения" title="Показать все заключения" roles="/Policy/Mis/MedCase/Diagnosis/View" />
        
    </msh:sideMenu>
  </tiles:put>
  <tiles:put name="body" type="string">
  	<ecom:webQuery name="listByPatient" nativeSql="select mc.id
,mc.dateStart as datstart, 
vwf.name ||' '|| wp.lastname ||' '|| wp.firstname ||' '|| wp.middlename as wfExecute,
list(vpd.name ||' '||mkb.code||' '||ds.name) as dsname
,list(distinct case when prot.dtype='Protocol' then prot.record else null end) as protrecord, vr.name as vrname, vvr.name as vvrname
,list(distinct case when smc.dtype='ServiceMedCase' then ms.code||' '||ms.name else null end) as mslist
 from medcase mc
 left join medcase smc on smc.parent_id=mc.id 
 left join medservice ms on ms.id=smc.medservice_id
left join vocreason vr on vr.id=mc.visitreason_id
left join vocvisitresult vvr on vvr.id = mc.visitresult_id
left join diagnosis ds on ds.medcase_id=mc.id
left join vocidc10 mkb on mkb.id=ds.idc10_id
left join VocPriorityDiagnosis vpd on vpd.id = ds.priority_id
left join diary prot on prot.medcase_id=mc.id
left join workFunction wf on wf.id=mc.workFunctionExecute_id
left join vocWorkFunction vwf on vwf.id=wf.workFunction_id
left join worker w on w.id=wf.worker_id
left join patient wp on wp.id=w.person_id
where mc.patient_id='${param.id}' and upper(mc.DTYPE) in ('VISIT','SHORTMEDCASE') 
and CASE WHEN mc.dateStart is not null and (mc.noActuality is null or mc.noActuality='0') THEN '1' ELSE '0' END = '1'
group by mc.id
,mc.dateStart ,
vwf.name, wp.lastname, wp.firstname,wp.middlename , vr.name , vvr.name,mc.timeExecute
order by mc.dateStart desc, mc.timeExecute desc
"/>
    <msh:table viewUrl="entityShortView-smo_visit.do" name="listByPatient" action="entityView-smo_visit.do" idField="1">
      <msh:tableColumn columnName="#" property="sn" />
      <msh:tableColumn columnName="Дата приема" property="2" />
      <msh:tableColumn columnName="Специалист" property="3"/>
      <msh:tableColumn columnName="Диагноз" property="4" role="/Policy/Mis/MedCase/Diagnosis/View"/>
      <msh:tableColumn columnName="Услуги" property="8" role="/Policy/Mis/MedCase/MedService/View"/>
      <msh:tableColumn columnName="Заключение" property="5" cssClass="preCell" role="/Policy/Mis/MedCase/Protocol/View"/>
      <msh:tableColumn columnName="Цель визита" property="6"/>
      <msh:tableColumn columnName="Результат" property="7"/>
      
    </msh:table>
  </tiles:put>
</tiles:insert>

