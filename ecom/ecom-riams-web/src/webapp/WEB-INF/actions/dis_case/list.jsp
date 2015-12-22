<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://struts.apache.org/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://www.nuzmsh.ru/tags/msh" prefix="msh" %>
<%@ taglib uri="http://www.ecom-ast.ru/tags/ecom" prefix="ecom" %>

<tiles:insert page="/WEB-INF/tiles/main${param.short}Layout.jsp" flush="true" >

  <tiles:put name="title" type="string">
    <ecom:titleTrail mainMenu="Patient" title="Случаи нетрудоспособности" beginForm="mis_patientForm" guid="b6v61-1e0b-4ebd-9f58-bdb45bd6" />
  </tiles:put>
  <tiles:put name="side" type="string">
    <msh:sideMenu guid="sideMenu-123" title="Добавить">
      <msh:sideLink guid="sideLinkCreate" key="ALT+N" params="id" action="/entityParentPrepareCreate-dis_documentByPatient" name="Случай нетрудоспособности" roles="/Policy/Mis/Disability/Case/Create" title="Добавить новый случай нетрудоспособности" />
    </msh:sideMenu>
  </tiles:put>
  <tiles:put name="body" type="string">
  <msh:section createRoles="/Policy/Mis/Disability/Case/Create" createUrl="entityParentPrepareCreate-dis_documentByPatient.do?id=${param.id}" title="Список случаев временной нетрудоспособности">
  	<ecom:webQuery name="list" nativeSql="
  	select dc.id
  	, min(dr.datefrom) as mindatefrom
  	, case when 
count(case when dr.dateto is null then '1' else null end)>0 
then '-' else to_char(max(dr.dateto),'dd.mm.yyyy') end maxdateto 
, case when 
count(case when dr.dateto is null then '1' else null end)>0 
then null 
else 
case when max(dr.dateto)=min(dr.datefrom) then '1' else max(dr.dateto)-min(dr.datefrom)+1 end end
  	from disabilitycase dc
  	left join disabilitydocument dd on dd.disabilitycase_id=dc.id
  	left join disabilityrecord dr on dr.disabilitydocument_id=dd.id 
  	where dc.patient_id='${param.id}'
  	group by dc.id
  	order by dc.id
  	"/>
    <msh:table viewUrl="entityView-dis_case.do?short=Short" name="list" 
    action="entityParentView-dis_case.do" idField="1" >
      <msh:tableColumn columnName="#" property="sn" guid="06d94f6a7-ed40-4ebf-a274-1efd69d01cfe4" />
      <msh:tableColumn columnName="Дата начала" property="2" guid="0694f6a7-ed40-4ebf-a274-1efd6901cfe4" />
      <msh:tableColumn columnName="Дата окончания" property="3" guid="6682eeef-105f-43a0-be61-30a865f27972" />
      <msh:tableColumn columnName="Продолжительность" property="4" guid="f34e1b12-3392-4978-b31f-5e54ff2e45bd" />
    </msh:table>
    </msh:section>
  </tiles:put>
</tiles:insert>

