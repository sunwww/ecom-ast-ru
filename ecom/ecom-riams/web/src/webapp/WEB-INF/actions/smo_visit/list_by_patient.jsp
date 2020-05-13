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
  <tags:style_currentMenu currentAction='inform'/>
    <msh:sideMenu>
      <msh:sideLink params="id" action="/entityView-mis_patient" name="Пациент" />
        <msh:sideLink styleId="inform" params="id" action="/js-smo_visit-infoByPatient" name="Информация по визитам" title="Показать информацию посещений по пациенту" roles="/Policy/Mis/MedCase/Spo/View" />
        <msh:sideLink  params="id" action="/js-smo_diagnosis-infoByPatient" name="Диагнозы" title="Показать все диагнозы" roles="/Policy/Mis/MedCase/Diagnosis/View" />
        <msh:sideLink  params="id" action="/js-smo_visitProtocol-infoByPatient" name="Заключения" title="Показать все заключения" roles="/Policy/Mis/MedCase/Diagnosis/View" />
        
    </msh:sideMenu>
  </tiles:put>
  <tiles:put name="body" type="string">
  	<ecom:webQuery  name="listByPatient" nativeSql="select mc.id||'&id='||mc.dateStart||'!'||mc.timeExecute||'!'||mc.id||'!'||coalesce(prot.id,0) as idf
,mc.dateStart as datstart, 
vwf.name ||' '|| wp.lastname ||' '|| wp.firstname ||' '|| wp.middlename as wfExecute,
vpd.name as vpdname ,mkb.code,ds.name as dsname,prot.record as protrecord, vr.name as vrname, vvr.name as vvrname
 from medcase mc 
left join vocreason vr on vr.id=mc.visitreason_id
left join vocvisitresult vvr on vvr.id = mc.visitresult_id
left join diagnosis ds on ds.medcase_id=mc.id
left join vocidc10 mkb on mkb.id=ds.idc10_id
left join VocPriorityDiagnosis vpd on vpd.id = ds.priority_id
left join diary prot on prot.medcase_id=mc.id and prot.dtype='Protocol'
left join workFunction wf on wf.id=mc.workFunctionExecute_id
left join vocWorkFunction vwf on vwf.id=wf.workFunction_id
left join worker w on w.id=wf.worker_id
left join patient wp on wp.id=w.person_id
where mc.patient_id='${param.id}' and (mc.DTYPE='Visit' or mc.DTYPE='ShortMedCase')
and mc.dateStart is not null and (mc.noActuality is null or mc.noActuality='0')
order by mc.dateStart desc,mc.timeExecute desc
"/>
	<msh:ifNotInRole roles="/Policy/Mis/MedCase/Visit/PrintAllInfoByPatient">
    <msh:table name="listByPatient" viewUrl="entitySubclassView-mis_medCase.do?short=Short" action="entitySubclassView-mis_medCase.do" idField="1">
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
    </msh:ifNotInRole>
    <msh:ifInRole roles="/Policy/Mis/MedCase/Visit/PrintAllInfoByPatient">
    <msh:table  selection="multiply" 
    name="listByPatient" viewUrl="entitySubclassView-mis_medCase.do?short=Short" action="entitySubclassView-mis_medCase.do" idField="1">
                    <msh:tableNotEmpty>
                        <tr>
                            <th colspan='11'>
                                <msh:toolbar>
                                    <a href='javascript:printProtocols("poly_protocols")'>Печать протоколов</a>
                                </msh:toolbar>
                            </th>
                        </tr>
                    </msh:tableNotEmpty>      
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
    <script type="text/javascript">
    function printProtocols(aFile) {
    	var ids = theTableArrow.getInsertedIdsAsParams("id","listByPatient") ;
    	
    	if(ids) {
    		//alert(ids) ;
    		window.location = 'print-'+aFile+'.do?multy=1&m=printVisits&s=VisitPrintService&'+ids ;
    		
    	} else {
    		alert("Нет выделенных протоколов");
    	}
    	
    }
    </script>
    </msh:ifInRole>
  </tiles:put>
</tiles:insert>

