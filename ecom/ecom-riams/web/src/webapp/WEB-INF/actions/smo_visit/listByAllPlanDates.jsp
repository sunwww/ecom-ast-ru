<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://struts.apache.org/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://www.nuzmsh.ru/tags/msh" prefix="msh" %>
<%@ taglib uri="http://www.ecom-ast.ru/tags/ecom" prefix="ecom" %>

<tiles:insert page="/WEB-INF/tiles/mainLayout.jsp" flush="true" >

  <tiles:put name="title" type="string">
    <msh:title mainMenu="Poly" property="worker" title="Выбор даты приема">Выбор журнала направлений</msh:title>
  </tiles:put>
  <tiles:put name="side" type="string" />
  <tiles:put name="body" type="string">
    <ecom:webQuery name="list" nativeSql="select WorkCalendarDay.id, WorkCalendarDay.calendarDate&#xA;       , ( select count(*) &#xA;             from medcase &#xA;            where medcase.workfunctionplan_id &#xA;               in ( select id &#xA;                      from workfunction &#xA;                     where secuser_id=${workerId}&#xA;                  ) &#xA;              and medcase.datePlan_id=WorkCalendarDay.id&#xA;          ) as planned&#xA;       , ( select count(*) &#xA;             from medcase &#xA;            where medcase.workfunctionExecute_id &#xA;               in ( select id &#xA;                      from workfunction &#xA;                     where secuser_id=${workerId}&#xA;                  ) &#xA;              and medcase.dateStart=WorkCalendarDay.calendarDate&#xA;          ) as executed&#xA;  from WorkCalendarDay&#xA; inner join WorkCalendar on WorkCalendarDay.workCalendar_id = WorkCalendar.id&#xA; inner join WorkFunction on WorkCalendar.workFunction_id    = WorkFunction.id&#xA; where WorkFunction.secUser_id = ${workerId}&#xA; group by WorkCalendarDay.id, WorkCalendarDay.calendarDate &#xA; order by WorkCalendarDay.calendarDate" />
    <msh:table name="list" action="js-smo_visit-findPolyAdmissions.do" idField="1">
      <msh:tableColumn columnName="Дата" property="2" />
      <msh:tableColumn columnName="Количество направленных" property="3" />
      <msh:tableColumn columnName="Количество принятых" property="4" />
    </msh:table>
  </tiles:put>
</tiles:insert>

