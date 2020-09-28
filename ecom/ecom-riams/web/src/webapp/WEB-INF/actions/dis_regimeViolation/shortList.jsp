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
    <msh:table viewUrl="entityShortView-dis_regimeViolation.do" editUrl="entityParentEdit-dis_regimeViolation.do" name="list" action="entityParentView-dis_regimeViolation.do" idField="id">
      <msh:tableColumn columnName="ИД" property="id" />
      <msh:tableColumn columnName="Дата начала нарушения" property="dateFrom" />
      <msh:tableColumn columnName="Дата окончания нарушения" property="dateTo" />
      <msh:tableColumn columnName="Комментарий" identificator="false" property="comment" />
    </msh:table>
  </tiles:put>
</tiles:insert>

