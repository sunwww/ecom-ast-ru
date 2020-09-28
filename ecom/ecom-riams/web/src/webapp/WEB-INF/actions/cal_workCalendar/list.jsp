<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://struts.apache.org/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://www.nuzmsh.ru/tags/msh" prefix="msh" %>
<%@ taglib uri="http://www.ecom-ast.ru/tags/ecom" prefix="ecom" %>

<tiles:insert page="/WEB-INF/tiles/mainLayout.jsp" flush="true" >

  <tiles:put name="title" type="string">
    <msh:title mainMenu="Patient">Проба</msh:title>
  </tiles:put>
  <tiles:put name="side" type="string">
    <msh:sideMenu>
      <msh:sideLink roles="/Policy/Mis/Worker/WorkCalendar/Delete/Create" key="ALT+N" action="/entityPrepareCreate-cal_workCalendar" name="Создать новое" />
    </msh:sideMenu>
  </tiles:put>
  <tiles:put name="body" type="string">
    <msh:table name="list" action="entityView-cal_workCalendar.do" idField="id">
      <msh:tableColumn columnName="ИД" property="id" />
      <msh:tableColumn columnName="Рабочая функция" property="workFunction" />
    </msh:table>
  </tiles:put>
</tiles:insert>

