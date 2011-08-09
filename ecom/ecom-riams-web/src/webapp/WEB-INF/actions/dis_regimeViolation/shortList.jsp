<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://struts.apache.org/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://www.nuzmsh.ru/tags/msh" prefix="msh" %>
<%@ taglib uri="http://www.ecom-ast.ru/tags/ecom" prefix="ecom" %>

<tiles:insert page="/WEB-INF/tiles/mainShortLayout.jsp" flush="true" >

  <tiles:put name="title" type="string">
    <ecom:titleTrail mainMenu="Disability" beginForm="dis_documentForm" title="Список нарушений"/>
  </tiles:put>
  <tiles:put name="side" type="string">
    <msh:sideMenu>
      <msh:sideLink roles="/Policy/Mis/Disability/RegimeViolationRecord/Create" key="ALT+N" action="/entityParentPrepareCreate-dis_regimeViolation" params="id" name="Создать новое" />
    </msh:sideMenu>
  </tiles:put>
  <tiles:put name="body" type="string">
    <msh:table viewUrl="entityShortView-dis_regimeViolation.do" editUrl="entityParentEdit-dis_regimeViolation.do" name="list" action="entityParentView-dis_regimeViolation.do" idField="id" guid="b621e361-1e0b-4ebd-9f58-b7d919b45bd6">
      <msh:tableColumn columnName="ИД" property="id" guid="0694f6a7-ed40-4ebf-a274-1efd6901cfe4" />
      <msh:tableColumn columnName="Дата начала нарушения" property="dateFrom" guid="6682eeef-105f-43a0-be61-30a865f27972" />
      <msh:tableColumn columnName="Дата окончания нарушения" property="dateTo" guid="f34e1b12-3392-4978-b31f-5e54ff2e45bd" />
      <msh:tableColumn columnName="Комментарий" identificator="false" property="comment" guid="94de7782-f708-4e61-988a-dea86538aabd" />
    </msh:table>
  </tiles:put>
</tiles:insert>

