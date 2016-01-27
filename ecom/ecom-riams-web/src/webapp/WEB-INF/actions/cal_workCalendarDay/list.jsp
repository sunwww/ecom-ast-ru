<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://struts.apache.org/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://www.nuzmsh.ru/tags/msh" prefix="msh" %>
<%@ taglib uri="http://www.ecom-ast.ru/tags/ecom" prefix="ecom" %>

<tiles:insert page="/WEB-INF/tiles/mainLayout.jsp" flush="true" >

  <tiles:put name="title" type="string">
    <ecom:titleTrail mainMenu="Lpu" beginForm="cal_workCalendarForm" guid="122a0918-38e6-471d-9e0d-713c186a114d" title="Рабочие дни" />
  </tiles:put>
  <tiles:put name="side" type="string" />
  <tiles:put name="body" type="string">
  	<ecom:webQuery name="list"  nativeSql="select wcd.id,wcd.calendarDate from WorkCalendarDay wcd where wcd.workcalendar_id=${param.id} order by wcd.calendarDate desc" maxResult="100"/>
    <msh:table name="list" action="patientsByWorkCalendarDay.do" idField="1" guid="b621e361-1e0b-4ebd-9f58-b7d919b45bd6">
      <msh:tableColumn identificator="false" property="2" guid="3e782865-fa26-4da8-8523-758368fb1c1c" columnName="Дата" />
    </msh:table>
  </tiles:put>
</tiles:insert>

