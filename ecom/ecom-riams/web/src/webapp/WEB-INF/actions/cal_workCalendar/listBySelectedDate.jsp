<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://struts.apache.org/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://www.nuzmsh.ru/tags/msh" prefix="msh" %>
<%@ taglib uri="http://www.ecom-ast.ru/tags/ecom" prefix="ecom" %>

<tiles:insert page="/WEB-INF/tiles/mainLayout.jsp" flush="true" >

  <tiles:put name="title" type="string">
    <msh:title mainMenu="Poly" title="Рабочий календарь" property="worker" />
  </tiles:put>
  <tiles:put name="side" type="string">
    <msh:sideMenu>
      <msh:sideLink action="/js-cal_workCalendar-findByAllPlanDates" name="Выбрать дату" title="Выбор даты" />
    </msh:sideMenu>
  </tiles:put>
  <tiles:put name="body" type="string">
    <msh:table name="list" action="entityView-smo_visit.do" idField="id">
      <msh:tableColumn columnName="№" identificator="false" property="sn" />
      <msh:tableColumn columnName="Дата" property="date" />
      <msh:tableColumn columnName="Время" property="time" />
      <msh:tableColumn columnName="Исполнен" identificator="false" property="dateTimeStart" />
      <msh:tableColumn columnName="Кто направил" property="orderWorker" />
      <msh:tableColumn columnName="Кем направлен" property="orderLpu" />
      <msh:tableColumn columnName="Пациент" property="patient" />
      <msh:tableColumn columnName="Исполнитель" identificator="false" property="executer" />
    </msh:table>
  </tiles:put>
</tiles:insert>

