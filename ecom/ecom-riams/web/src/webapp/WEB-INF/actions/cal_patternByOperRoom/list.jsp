<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://struts.apache.org/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://www.nuzmsh.ru/tags/msh" prefix="msh" %>
<%@ taglib uri="http://www.ecom-ast.ru/tags/ecom" prefix="ecom" %>

<tiles:insert page="/WEB-INF/tiles/mainLayout.jsp" flush="true" >

  <tiles:put name="title" type="string">
    <msh:title mainMenu="Patient">Шаблоны рабочий календарей</msh:title>
  </tiles:put>
  <tiles:put name="side" type="string">
    <msh:sideMenu title="Добавить">
      <msh:sideLink params="id" roles="/Policy/Mis/Worker/WorkCalendar/Pattern/OperatingRoom/Create" key="ALT+N" action="/entityParentPrepareCreate-cal_workCalendarPattern" name="Шаблон календаря" />
    </msh:sideMenu>
  </tiles:put>
  <tiles:put name="body" type="string">
    <msh:table name="list" action="entityView-cal_patternByOperRoom.do" idField="id" guid="b621e361-1e0b-4ebd-9f58-b7d919b45bd6" navigationAction="js-cal_workCalendarPattern-listTemplate.do">
      <msh:tableColumn columnName="Информация" property="info" guid="6682eeef-105f-43a0-be61-30a865f27972" />
      <msh:tableColumn columnName="Время начала по умолчанию" property="defaultTimeFrom" guid="f34e1b3392-4978-b31f-5e54ff2e45bd" />
      <msh:tableColumn columnName="Время окончания по умолчанию" property="defaultTimeTo" guid="f34e1b12-3392-4978-b31f-5e54ff5bd" />
      <msh:tableColumn columnName="Интервал по умолчанию" property="defaultTimeInterval" guid="f34e1b12-3392-4978-b31f-5e54ff2e" />
      <msh:tableColumn columnName="Недействует" identificator="false" property="noActive" guid="b43103ba-dff4-4c8e-9840-2f8fc9b0a2af" />
    </msh:table>
  </tiles:put>
</tiles:insert>

