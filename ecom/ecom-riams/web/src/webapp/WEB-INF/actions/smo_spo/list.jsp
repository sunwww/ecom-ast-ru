<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://struts.apache.org/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://www.nuzmsh.ru/tags/msh" prefix="msh" %>
<%@ taglib uri="http://www.ecom-ast.ru/tags/ecom" prefix="ecom" %>

<tiles:insert page="/WEB-INF/tiles/main${param.short}Layout.jsp" flush="true" >

  <tiles:put name="title" type="string">
    <ecom:titleTrail beginForm="mis_patientForm" mainMenu="Patient" title="Список всех СПО" />
  </tiles:put>
  <tiles:put name="side" type="string" />
  <tiles:put name="body" type="string">
  	<ecom:webQuery name="listSpo" nativeSql=" select spo.id
  	,to_char(spo.dateStart,'dd.mm.yyyy') as dateStart
  	,to_char(spo.dateFinish,'dd.mm.yyyy') as dateFinish 
  	, coalesce(spo.dateFinish,CURRENT_DATE)-spo.dateStart as cnt1 
  	, count(distinct case when vis.noActuality='1' or vis.dateStart is null then null else vis.id end) as cnt2 
  	, count(distinct case when vis.noActuality='1' or vis.dateStart is not null then null else vis.id end) as cnt3 
  	, count(distinct case when vis.noActuality='1' then vis.id else null end) as cnt3 
  	,to_char(max(vis.dateStart),'dd.mm.yyyy') as maxvisDateStart 
  	,ovwf.name || ' '||owp.lastname as docfio 
  	,list(distinct vvr.name) as vvrname
  	,list(distinct mkb.code||' '||vpd.name) as diag 
  	from medCase spo 
  	left join WorkFunction owf on owf.id=spo.ownerFunction_id 
  	left join Worker ow on ow.id=owf.worker_id 
  	left join Patient owp on owp.id=ow.person_id 
  	left join VocWorkFunction ovwf on ovwf.id=owf.workFunction_id 
  	left join MedCase vis on vis.parent_id=spo.id and vis.DTYPE='Visit' 
  	left join Diagnosis diag on diag.medcase_id=vis.id 
  	left join VocIdc10 mkb on mkb.id=diag.idc10_id 
  	left join VocPriorityDiagnosis vpd on vpd.id=diag.priority_id 
  	left join VocVisitResult vvr on vvr.id=vis.visitResult_id 
  	left join WorkCalendarDay wcd on wcd.id=vis.datePlan_id 
  	where spo.DTYPE='PolyclinicMedCase' and spo.patient_id='${param.id}' 
  	 
  	group by  spo.id,spo.dateStart,spo.dateFinish,ovwf.name,owp.lastname 
  	order by spo.dateStart
  	"/>
    <msh:table name="listSpo" action="entityView-smo_spo.do" viewUrl="entityView-smo_spo.do?short=Short" idField="1"
    >
      <msh:tableColumn columnName="Номер" property="1" />
      <msh:tableColumn columnName="Дата открытия" property="2"/>
      <msh:tableColumn columnName="Дата закрытия" property="3"/>
      <msh:tableColumn columnName="Длит-ть" property="4" />
      <msh:tableColumn columnName="Кол-во визитов" property="5" />
      <msh:tableColumn columnName="Кол-во направ." property="6" />
      <msh:tableColumn columnName="Кол-во недейс." property="7" />
      <msh:tableColumn columnName="Дата посл. визита" property="8"/>
      <msh:tableColumn columnName="Специалист" property="9"/>
      <msh:tableColumn columnName="Результат визитов" property="10"/>
      <msh:tableColumn columnName="Диагнозы" property="11"/>
    </msh:table>
  </tiles:put>
</tiles:insert>

