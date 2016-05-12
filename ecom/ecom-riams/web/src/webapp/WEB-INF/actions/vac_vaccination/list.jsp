<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://struts.apache.org/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://www.nuzmsh.ru/tags/msh" prefix="msh" %>
<%@ taglib uri="http://www.ecom-ast.ru/tags/ecom" prefix="ecom" %>

<tiles:insert page="/WEB-INF/tiles/mainLayout.jsp" flush="true" >

  <tiles:put name="title" type="string">
    <ecom:titleTrail beginForm="mis_medCaseForm" mainMenu="Patient" title="Вакцинации" guid="e3fd66c327-4ccd-429c-99e7-1935f2ed4b38" />
  </tiles:put>
  <tiles:put name="side" type="string">
    <msh:sideMenu guid="helloSideMenu-123">
      <msh:sideLink guid="helloSideLinkNew" roles="/Policy/Mis/Vaccination/Create" key="ALT+N" action="/entityParentPrepareCreate-vac_vaccination" name="Создать новое" params="id" />
    </msh:sideMenu>
  </tiles:put>
  <tiles:put name="body" type="string">
    <msh:table name="list" action="entityParentView-vac_vaccination.do" idField="id" guid="b621e361-1e0b-4ebd-9f58-b7d919b45bd6">
      <msh:tableColumn columnName="Дата" property="executeDate" guid="0694f6a7-ed40-4ebf-a274-1efd6901cfe4" />
      <msh:tableColumn columnName="Время" property="executeTime" guid="6682eeef-105f-43a0-be61-30a865f27972" />
      <msh:tableColumn columnName="Вакцина" property="materialText" guid="d64d2425-82f9-41d8-88b9-9a5fb57719f2" />
      <msh:tableColumn columnName="Серия" property="series" guid="dd0902ee-6c65-4b6b-9242-0942167b5b9d" />
      <msh:tableColumn columnName="Номер" property="controlNumber" guid="2948b984-8f8e-44bf-9558-e417e30c57b7" />
      <msh:tableColumn columnName="Доза" property="dose" guid="1ed0218f-8c15-4b5f-8452-a493bfbd3369" />
      <msh:tableColumn columnName="Метод" property="methodText" guid="cb964ce4-c8c4-42af-a02f-a1608d007d99" />
      <msh:tableColumn columnName="Фаза" property="phaseText" guid="9c8687df-1c87-4a77-976b-d2209a832094" />
      <msh:tableColumn columnName="Годен до" property="expirationDate" guid="c970605e-d86c-4af8-8afe-515c0908c5e5" />
      <msh:tableColumn columnName="Разрешил" property="permitWorkerInfo" guid="cfd0c43f-4de8-4778-a134-5e3e47e21b5f" />
      <msh:tableColumn columnName="Исполнитель" property="executeWorkerInfo" guid="b313f7f9-c0f8-43eb-b309-51f79ef88895" />
    </msh:table>
  </tiles:put>
</tiles:insert>

