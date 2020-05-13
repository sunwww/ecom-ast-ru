<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://struts.apache.org/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://www.nuzmsh.ru/tags/msh" prefix="msh" %>
<%@ taglib uri="http://www.ecom-ast.ru/tags/ecom" prefix="ecom" %>

<tiles:insert page="/WEB-INF/tiles/mainLayout.jsp" flush="true" >

  <tiles:put name="title" type="string">
    <ecom:titleTrail beginForm="mis_lpuForm" mainMenu="Lpu" title="Сведения о летальности" />
  </tiles:put>
  <tiles:put name="side" type="string">
    <msh:sideMenu>
      <msh:sideLink roles="/Policy/Mis/Report/Mortality/Create" key="ALT+N" action="/entityParentPrepareCreate-mis_mortalityReportDate" name="Добавить сведения" params="id" />
    </msh:sideMenu>
  </tiles:put>
  <tiles:put name="body" type="string">
    <msh:table name="list" action="entityParentEdit-mis_mortalityReportDate.do" idField="id">
      <msh:tableColumn columnName="Дата" property="reportDate" />
      <msh:tableColumn columnName="Редакция завершена" identificator="false" property="editComplete" />
    </msh:table>
  </tiles:put>
</tiles:insert>

