<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://struts.apache.org/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://www.nuzmsh.ru/tags/msh" prefix="msh" %>
<%@ taglib uri="http://www.ecom-ast.ru/tags/ecom" prefix="ecom" %>

<tiles:insert page="/WEB-INF/tiles/mainLayout.jsp" flush="true" >

  <tiles:put name="title" type="string">
    <ecom:titleTrail beginForm="mis_lpuForm" mainMenu="Lpu" title="Коечный фонд" />
  </tiles:put>
  <tiles:put name="side" type="string">
    <msh:sideMenu>
      <msh:sideLink roles="/Policy/Mis/BedFund/Create" key="ALT+N" action="/entityParentPrepareCreate-mis_bedFund" name="Создать новое" params="id" />
    </msh:sideMenu>
  </tiles:put>
  <tiles:put name="body" type="string">
    <msh:table name="list" action="entityParentView-mis_bedFund.do" idField="id">
      <msh:tableColumn columnName="ИД" property="id" />
      <msh:tableColumn columnName="Количество" property="amount" />
      <msh:tableColumn columnName="Профиль коек" property="bedTypeName" />
      <msh:tableColumn columnName="Тип свертывания" property="reductionTypeName" />
      <msh:tableColumn columnName="Поток обслуживания" property="serviceStreamName" />
      <msh:tableColumn columnName="Тип госпитального обслуживания" property="serviceTypeName" />
    </msh:table>
  </tiles:put>
</tiles:insert>

