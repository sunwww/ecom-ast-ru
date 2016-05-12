<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://struts.apache.org/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://www.nuzmsh.ru/tags/msh" prefix="msh" %>
<%@ taglib uri="http://www.ecom-ast.ru/tags/ecom" prefix="ecom" %>

<tiles:insert page="/WEB-INF/tiles/mainLayout.jsp" flush="true" >

  <tiles:put name="title" type="string">
    <ecom:titleTrail beginForm="mis_patientForm" mainMenu="Patient" title="Вакцинации" guid="e3fd66c327-4ccd-429c-99e7-1935f2ed4b38" />
  </tiles:put>
  <tiles:put name="side" type="string">
    <msh:sideMenu guid="helloSideMenu-123">
      <!--      <msh:sideLink guid="helloSideLinkNew" roles="/Policy/Mis/Vaccination/Create" key="ALT+N" action="/entityParentPrepareCreate-vac_vaccination" name="Создать новое" params="id" />-->
    </msh:sideMenu>
  </tiles:put>
  <tiles:put name="body" type="string">
    <msh:table name="list" action="entityParentView-vac_vaccination.do" idField="id" guid="b621e361-1e0b-4ebd-9f58-b7d919b45bd6">
      <msh:tableColumn columnName="Дата" property="executeDate" guid="77e4dccc-438e-4ef9-adfa-dff6031c2338" />
      <msh:tableColumn columnName="Время" property="executeTime" guid="d045f4c0-1473-47a5-a962-0d6a7fe3dd25" />
      <msh:tableColumn columnName="Вакцина" property="vaccineInfo" guid="78bd62aa-39d5-42c9-b1e6-cc6937477e44" />
      <msh:tableColumn columnName="Серия" property="series" guid="b3a4dba3-dee0-486f-8824-70c2ed18979f" />
      <msh:tableColumn columnName="Номер" property="controlNumber" guid="699d7ffa-ca7b-4b5c-b65e-e6a37cd261fe" />
      <msh:tableColumn columnName="Доза" property="dose" guid="2e0094e7-e57e-4120-a35f-57fd81655e0a" />
      <msh:tableColumn columnName="Метод" property="methodText" guid="c7d3fdb9-a981-4b2c-b35d-674b23e4aaf1" />
      <msh:tableColumn columnName="Фаза" property="phaseText" guid="c8384d17-9d29-455a-97c3-06699ea17a6b" />
      <msh:tableColumn columnName="Годен до" property="expirationDate" guid="afe48e2d-1662-40fe-881d-b50222bf97cb" />
      <msh:tableColumn columnName="Разрешил" property="permitWorkerInfo" guid="49b7f020-097c-466d-95ba-56b44b2ea5de" />
      <msh:tableColumn columnName="Исполнитель" property="executeWorkerInfo" guid="70c5fb98-9a0b-4dac-8380-388fa77d2561" />
    </msh:table>
  </tiles:put>
</tiles:insert>

