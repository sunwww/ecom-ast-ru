<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://struts.apache.org/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://www.nuzmsh.ru/tags/msh" prefix="msh" %>
<%@ taglib uri="http://www.ecom-ast.ru/tags/ecom" prefix="ecom" %>

<tiles:insert page="/WEB-INF/tiles/mainLayout.jsp" flush="true" >

  <tiles:put name="title" type="string">
    <ecom:titleTrail beginForm="mis_assessmentCardForm" guid="helloItle-123" mainMenu="Patient" title="Коечный фонд" />
  </tiles:put>
  <tiles:put name="side" type="string">
    <msh:sideMenu guid="helloSideMenu-123">
      <msh:sideLink guid="helloSideLinkNew" roles="/Policy/Mis/BedFund/Create" key="ALT+N" action="/entityParentPrepareCreate-mis_assessmentCard" name="Создать новую карту" params="id" />
    </msh:sideMenu>
  </tiles:put>
  <tiles:put name="body" type="string">
    <msh:table name="list" action="entityParentView-mis_assessmentCard.do" idField="id" guid="e699b892-d71e-4622-ae5e-eaec3ed85bb4">
      <msh:tableColumn columnName="ИД" property="id" guid="0696a7-ed40-4ebf-a274-1e4" />
      <msh:tableColumn columnName="Название" property="name" guid="f34e-392-4978-b31f-5e54ff2e45bd" />
    </msh:table>
  </tiles:put>
</tiles:insert>

