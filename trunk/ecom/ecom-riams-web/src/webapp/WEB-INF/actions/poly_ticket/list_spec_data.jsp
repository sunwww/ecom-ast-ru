<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://struts.apache.org/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://www.nuzmsh.ru/tags/msh" prefix="msh" %>
<%@ taglib tagdir="/WEB-INF/tags" prefix="tags" %>
<%@ taglib uri="http://www.ecom-ast.ru/tags/ecom" prefix="ecom" %>

<tiles:insert page="/WEB-INF/tiles/mainLayout.jsp" flush="true">

  <tiles:put name="title" type="string">
    <msh:title mainMenu="Medcard" guid="4b11dc98-30fc-413e-8bc6-976f292e704f">Список талонов ....</msh:title>
  </tiles:put>
  <tiles:put name="side" type="string">
    <tags:ticket_finds currentAction="ticketsBySpec" />
  </tiles:put>
  <tiles:put name="body" type="string">
    <msh:table name="list" action="entityParentEdit-poly_ticket.do" idField="id" noDataMessage="Не найдено" guid="6600cebc-4548-4f57-a048-5a3a2e67a673">
      <msh:tableColumn columnName="Номер" property="id" guid="612d85fd-ca3a-46a4-9598-a611b83a01ab" />
      <msh:tableColumn columnName="Дата выдачи" property="dateCreate" guid="1b4ed9e3-9e46-43fd-b9a6-700b3269c7c5" />
      <msh:tableColumn columnName="Время выдачи" property="timeCreate" guid="d214034d-3ab6-4a6d-b698-5abbd75f3c68" />
      <msh:tableColumn columnName="Дата приема" property="date" guid="ee9ce01d-4924-4e76-bc93-3ecb73d8b18f" />
      <msh:tableColumn columnName="Время приема" property="time" guid="f62732ff-2c80-471e-8d1d-bdc8f358e730" />
      <msh:tableColumn columnName="Специалист" property="workFunctionInfo" guid="9465992e-5fe3-42ee-b125-63929fda5158" />
      <msh:tableColumn columnName="Пациент" property="patientName" guid="d7955208-4c68-42ce-85d6-684a4b9076a9" />
      <msh:tableColumn columnName="Статус" property="statusName" guid="4d08977e-791d-42d6-8b84-7968aede7e79" />
    </msh:table>
  </tiles:put>
</tiles:insert>

