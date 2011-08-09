<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://struts.apache.org/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://www.nuzmsh.ru/tags/msh" prefix="msh" %>
<%@ taglib uri="http://www.ecom-ast.ru/tags/ecom" prefix="ecom" %>

<tiles:insert page="/WEB-INF/tiles/mainLayout.jsp" flush="true" >

  <tiles:put name="title" type="string">
    <ecom:titleTrail beginForm="mis_lpuForm" mainMenu="Lpu" title="Штатное расписание" guid="efab0af8-8479-4b82-9bce-9eb03f965bfd" />
  </tiles:put>
  <tiles:put name="side" type="string">
    <msh:sideMenu guid="helloSideMenu-123">
      <msh:sideLink guid="helloSideLinkNew" roles="/Policy/Mis/Worker/Staff/Create" key="ALT+N" action="/entityParentPrepareCreate-work_staff" name="Создать новое" params="id" />
    </msh:sideMenu>
  </tiles:put>
  <tiles:put name="body" type="string">
    <msh:table name="list" action="entityView-work_staff.do" idField="id" guid="b621e361-1e0b-4ebd-9f58-b7d919b45bd6">
      <msh:tableColumn columnName="Ставок всего" property="fullRate" guid="0694f6a7-ed40-4ebf-a274-1efd6901cfe4" />
      <msh:tableColumn columnName="Ставок бюджетных" property="budjetRate" guid="6682eeef-105f-43a0-be61-30a865f27972" />
      <msh:tableColumn columnName="Ставок внебюджетных" property="offBudjetRate" guid="f34e1b12-3392-4978-b31f-5e54ff2e45bd" cssClass="offBudjetRate" />
      <msh:tableColumn columnName="Ставок свободных всего" identificator="false" property="freeFullRate" guid="8a73eda4-f8bd-49ad-9f40-73f057f2f4b8" />
      <msh:tableColumn columnName="Ставок свободных бюджетных" identificator="false" property="freeBudjetRate" guid="0aa1e542-2103-4797-8604-580a19417e8e" />
      <msh:tableColumn columnName="Ставок свободных внебюджетных" identificator="false" property="freeOffBudjetRate" guid="24163dc5-057f-4186-851a-c417eb20535e" />
    </msh:table>
  </tiles:put>
</tiles:insert>

