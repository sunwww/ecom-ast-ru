<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://struts.apache.org/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://www.nuzmsh.ru/tags/msh" prefix="msh" %>
<%@ taglib uri="http://www.ecom-ast.ru/tags/ecom" prefix="ecom" %>
<%@ taglib tagdir="/WEB-INF/tags" prefix="tags" %>

<tiles:insert page="/WEB-INF/tiles/mainLayout.jsp" flush="true" >

  <tiles:put name="title" type="string">
    <ecom:titleTrail beginForm="mis_patientForm" mainMenu="Patient" title="Список всех дневников" />
  </tiles:put>
  <tiles:put name="side" type="string">
  <tags:style_currentMenu currentAction='inform'/>
    <msh:sideMenu>
      <msh:sideLink params="id" action="/entityView-mis_patient" name="Пациент" />
        <msh:sideLink params="id" action="/js-smo_visit-infoByPatient" name="Информация по визитам" title="Показать информацию посещений по пациенту" roles="/Policy/Mis/MedCase/Spo/View" />
        <msh:sideLink  params="id" action="/js-smo_diagnosis-infoByPatient" name="Диагнозы" title="Показать все диагнозы" roles="/Policy/Mis/MedCase/Diagnosis/View" />
        <msh:sideLink styleId="inform"  params="id" action="/js-smo_visitProtocol-infoByPatient" name="Заключения" title="Показать все заключения" roles="/Policy/Mis/MedCase/Diagnosis/View" />
        
    </msh:sideMenu>
  </tiles:put>
  <tiles:put name="body" type="string">
    <ecom:webQuery name="listByPatient" nativeSql="select pr.id, pr.dateREgistration
    ,pr.timeRegistration,pr.record,vwf.name||' '||wp.lastname||' '||wp.firstname|| ' '||wp.middlename
    , case when (mc.DTYPE='Visit' or mc.DTYPE='PolyclinicMedCase' or mc.dtype='ShortMedCase') 
    then 'Поликлиника' when mc.DTYPE='ServiceMedCase' then 'Услуга' 
    else 'Стационар' end from diary pr 
    left join medcase mc on mc.id=pr.medcase_id  
    left join workfunction wf on wf.id=pr.specialist_id 
    left join worker w on w.id=wf.worker_id 
    left join vocWorkFunction vwf on vwf.id=wf.workFunction_id 
    left join patient wp on wp.id=w.person_id where mc.patient_id='${param.id}' and pr.dtype='Protocol'"/>
    <msh:tableNotEmpty name="listByPatient">
  <msh:section title="Заключения по медицинским случаям обслуживания">
    <msh:table name="listByPatient" action="entityParentView-smo_visitProtocol.do" idField="1">
      <msh:tableColumn property="6" columnName="ПО"/>
      <msh:tableColumn columnName="Дата" property="2" />
      <msh:tableColumn columnName="Время" property="3" />
      <msh:tableColumn columnName="Текст" property="4" cssClass="preCell"/>
      <msh:tableColumn columnName="Специалист" property="5" />
    </msh:table>
  </msh:section>
  </msh:tableNotEmpty>
  </tiles:put>
</tiles:insert>