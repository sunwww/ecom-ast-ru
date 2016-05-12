<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://struts.apache.org/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://www.nuzmsh.ru/tags/msh" prefix="msh" %>
<%@ taglib uri="http://www.ecom-ast.ru/tags/ecom" prefix="ecom" %>

<tiles:insert page="/WEB-INF/tiles/mainLayout.jsp" flush="true" >

  <tiles:put name="title" type="string">
    <ecom:titleTrail beginForm="mis_lpuForm" guid="helloItle-123" mainMenu="Lpu" title="Коечный фонд" />
  </tiles:put>
  <tiles:put name="side" type="string">
    <msh:sideMenu guid="helloSideMenu-123">
      <msh:sideLink guid="helloSideLinkNew" roles="/Policy/Mis/BedFund/Create" key="ALT+N" action="/entityParentPrepareCreate-mis_bedFund" name="Создать новое" params="id" />
    </msh:sideMenu>
  </tiles:put>
  <tiles:put name="body" type="string">
    <msh:table name="list" action="entityParentView-mis_bedFund.do" idField="id" guid="e699b892-d71e-4622-ae5e-eaec3ed85bb4">
      <msh:tableColumn columnName="ИД" property="id" guid="0696a7-ed40-4ebf-a274-1e4" />
      <msh:tableColumn columnName="Количество" property="amount" guid="f34e-392-4978-b31f-5e54ff2e45bd" />
      <msh:tableColumn columnName="Профиль коек" property="bedTypeName" guid="66f-105f-43a0-be61-30972" />
      <msh:tableColumn columnName="Тип свертывания" property="reductionTypeName" guid="fr12-3392-4978-b31f-5e54ff2e45bd" />
      <msh:tableColumn columnName="Поток обслуживания" property="serviceStreamName" guid="f3t-3392-4978-b31f-5e54ff2e45bd" />
      <msh:tableColumn columnName="Тип госпитального обслуживания" property="serviceTypeName" guid="e1b12-3392-4978-b31f-5e54ff2e45bd" />
    </msh:table>
  </tiles:put>
</tiles:insert>

