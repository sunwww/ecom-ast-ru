<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://struts.apache.org/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://www.nuzmsh.ru/tags/msh" prefix="msh" %>
<%@ taglib uri="http://www.ecom-ast.ru/tags/ecom" prefix="ecom" %>

<tiles:insert page="/WEB-INF/tiles/mainLayout.jsp" flush="true" >

  <tiles:put name="title" type="string">
    <ecom:titleTrail beginForm="mis_patientForm" mainMenu="Patient" title="Список всех СПО" guid="3c259ba8-b962-4333-9aab-3316f984fdde" />
  </tiles:put>
  <tiles:put name="side" type="string" />
  <tiles:put name="body" type="string">
    <msh:table name="list" action="entityView-smo_spo.do" idField="id" guid="b621e361-1e0b-4ebd-9f58-b7d919b45bd6">
      <msh:tableColumn columnName="Номер" property="id" guid="0694f6a7-ed40-4ebf-a274-1efd6901cfe4" />
      <msh:tableColumn columnName="Дата открытия" property="dateStart" guid="6682eeef-105f-43a0-be61-30a865f27972" />
      <msh:tableColumn columnName="Кем открыт" property="startWorkerText" guid="f34e1b12-3392-4978-b31f-5e54ff2e45bd" />
      <msh:tableColumn columnName="Дата закрытия" property="dateFinish" guid="e4dcd1af-9c73-47f4-9c6a-a84b94bb9c58" />
      <msh:tableColumn columnName="Кем закрыт" property="finishWorkerText" guid="6f4c5761-6c0e-4d18-9ae2-4677f528c3e0" />
      <msh:tableColumn columnName="Владелец" property="ownerText" guid="2def3de6-d012-424a-9182-044589ef60de" />
      <msh:tableColumn columnName="Количество визитов" property="visitsCount" guid="9195faa3-a32b-42a1-8efa-3ca84bc74b66" />
      <msh:tableColumn columnName="Количество дней" property="daysCount" guid="f65381b5-6e76-4a75-be15-8933a0c1de82" />
    </msh:table>
  </tiles:put>
</tiles:insert>

