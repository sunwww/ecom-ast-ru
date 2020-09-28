<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://struts.apache.org/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://www.nuzmsh.ru/tags/msh" prefix="msh" %>
<%@ taglib uri="http://www.ecom-ast.ru/tags/ecom" prefix="ecom" %>

<tiles:insert page="/WEB-INF/tiles/main${param.short}Layout.jsp" flush="true" >

  <tiles:put name="title" type="string">
    <ecom:titleTrail mainMenu="Patient" title="Случаи нетрудоспособности" beginForm="mis_patientForm" />
  </tiles:put>
  <tiles:put name="side" type="string">
    <msh:sideMenu title="Добавить">
      <msh:sideLink key="ALT+N" params="id" action="/entityParentPrepareCreate-dis_documentByPatient" name="Случай нетрудоспособности" roles="/Policy/Mis/Disability/Case/Create" title="Добавить новый случай нетрудоспособности" />
    </msh:sideMenu>
  </tiles:put>
  <tiles:put name="body" type="string">
  <msh:section createRoles="/Policy/Mis/Disability/Case/Create" createUrl="entityParentPrepareCreate-dis_documentByPatient.do?id=${param.id}" title="Список случаев временной нетрудоспособности">
    <a href="dis_import.do?id=${param.id}"><img src="/skin/images/main/plus.png" alt="Импорт ЭЛН с ФСС" title="Импорт ЭЛН с ФСС" height="14" width="14">Импорт ЭЛН с ФСС</a>
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
      <msh:tableColumn columnName="#" property="sn" />
      <msh:tableColumn columnName="Дата начала" property="2" />
      <msh:tableColumn columnName="Дата окончания" property="3" />
      <msh:tableColumn columnName="Продолжительность" property="4" />
    </msh:table>
    </msh:section>
  </tiles:put>
</tiles:insert>

