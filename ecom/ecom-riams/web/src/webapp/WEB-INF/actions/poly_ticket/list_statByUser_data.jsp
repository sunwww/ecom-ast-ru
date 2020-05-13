<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://struts.apache.org/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://www.nuzmsh.ru/tags/msh" prefix="msh" %>
<%@ taglib tagdir="/WEB-INF/tags" prefix="tags" %>
<%@ taglib uri="http://www.ecom-ast.ru/tags/ecom" prefix="ecom" %>

<tiles:insert page="/WEB-INF/tiles/mainLayout.jsp" flush="true">

  <tiles:put name="title" type="string">
    <msh:title mainMenu="Medcard">Список талонов по специалисту  ${stat_user}  ${stat_date}</msh:title>
  </tiles:put>
  <tiles:put name="side" type="string">
    <tags:ticket_finds currentAction="ticketsByUser" />
  </tiles:put>
  <tiles:put name="body" type="string">
    <msh:table name="list" action="entityParentEdit-poly_ticket.do" idField="id" noDataMessage="Не найдено">
      <msh:tableColumn columnName="Номер" property="id" />
      <msh:tableColumn columnName="Дата выдачи" property="dateCreate" />
      <msh:tableColumn columnName="Время выдачи" property="timeCreate" />
      <msh:tableColumn columnName="Дата приема" property="date" />
      <msh:tableColumn columnName="Время приема" property="time" />
      <msh:tableColumn columnName="Специалист" property="workFunctionInfo" />
      <msh:tableColumn columnName="Пациент" property="patientName" />
      <msh:tableColumn columnName="Статус" property="statusName" />
    </msh:table>
  </tiles:put>
</tiles:insert>

