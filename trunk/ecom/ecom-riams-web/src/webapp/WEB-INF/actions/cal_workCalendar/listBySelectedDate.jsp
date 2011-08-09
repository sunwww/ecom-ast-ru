<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://struts.apache.org/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://www.nuzmsh.ru/tags/msh" prefix="msh" %>
<%@ taglib uri="http://www.ecom-ast.ru/tags/ecom" prefix="ecom" %>

<tiles:insert page="/WEB-INF/tiles/mainLayout.jsp" flush="true" >

  <tiles:put name="title" type="string">
    <msh:title guid="helloItle-123" mainMenu="Poly" title="Рабочий календарь" property="worker" />
  </tiles:put>
  <tiles:put name="side" type="string">
    <msh:sideMenu guid="helloSideMenu-123">
      <msh:sideLink action="/js-cal_workCalendar-findByAllPlanDates" name="Выбрать дату" title="Выбор даты" guid="4c13dfdr371-c72a-4bc0-b2cd-c0bcfce1be6f" />
    </msh:sideMenu>
  </tiles:put>
  <tiles:put name="body" type="string">
    <msh:table name="list" action="entityView-smo_visit.do" idField="id" guid="b6ef21e36f1-1e0b-4ebd-9f58-b7d919b45bd6">
      <msh:tableColumn columnName="№" identificator="false" property="sn" guid="2cx70ae0dvc-e1c6-45c5-b8b8-26d034ec3878" />
      <msh:tableColumn columnName="Дата" property="date" guid="de1fxfv59v1c-02b8-4875-969f-d2698689db5d" />
      <msh:tableColumn columnName="Время" property="time" guid="84xvh1018s5d-711a-4851-bca4-913a77381989" />
      <msh:tableColumn columnName="Исполнен" identificator="false" property="dateTimeStart" guid="b3esa2fbd6e-53b6-4e69-8427-2534cf1edcca" />
      <msh:tableColumn columnName="Кто направил" property="orderWorker" guid="6s68ds2eeef-105f-43a0-be61-30a865f27972" />
      <msh:tableColumn columnName="Кем направлен" property="orderLpu" guid="fr34e1b12-3392-4978-b31f-5e54ff2e45bd" />
      <msh:tableColumn columnName="Пациент" property="patient" guid="315cbdr56eb-3db8-4de5-8b0c-a49e3cacf382" />
      <msh:tableColumn columnName="Исполнитель" identificator="false" property="executer" guid="31erh45e72a-cce5-4994-a507-b1a81efefdfe" />
    </msh:table>
  </tiles:put>
</tiles:insert>

