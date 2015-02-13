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
  
    <msh:table viewUrl="entityShortView-dis_case.do" name="list" action="entityParentView-dis_case.do" idField="id" guid="b621e361-1e0b-4ebd-9f58-b7d919b45bd6">
      <msh:tableColumn columnName="#" property="sn" guid="06d94f6a7-ed40-4ebf-a274-1efd69d01cfe4" />
      <msh:tableColumn columnName="Дата начала" property="dateFrom" guid="0694f6a7-ed40-4ebf-a274-1efd6901cfe4" />
      <msh:tableColumn columnName="Дата окончания" property="dateTo" guid="6682eeef-105f-43a0-be61-30a865f27972" />
      <msh:tableColumn columnName="Продолжительность" property="duration" guid="f34e1b12-3392-4978-b31f-5e54ff2e45bd" />
    </msh:table>
    </msh:section>
  </tiles:put>
</tiles:insert>

