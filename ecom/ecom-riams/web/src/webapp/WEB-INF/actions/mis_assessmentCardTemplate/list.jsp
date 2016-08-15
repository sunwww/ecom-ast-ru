<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://struts.apache.org/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://www.nuzmsh.ru/tags/msh" prefix="msh" %>
<%@ taglib uri="http://www.ecom-ast.ru/tags/ecom" prefix="ecom" %>

<tiles:insert page="/WEB-INF/tiles/mainLayout.jsp" flush="true" >

  <tiles:put name="title" type="string">
    
  </tiles:put>
  <tiles:put name="side" type="string">
    <msh:sideMenu guid="helloSideMenu-123">
      <msh:sideLink guid="helloSideLinkNew" roles="/Policy/Mis/AssessmentCard/Create" key="ALT+N" action="/entityPrepareCreate-mis_assessmentCardTemplate" name="Создать"  />
    </msh:sideMenu>
  </tiles:put>
  <tiles:put name="body" type="string">
  <ecom:webQuery name="cardList" nameFldSql="cardList_sql" nativeSql="select id, name from assessmentCardTemplate act" />
    <msh:table name="cardList" action="entityView-mis_assessmentCardTemplate.do" idField="1" guid="e699b892-d71e-4622-ae5e-eaec3ed85bb4">
      <msh:tableColumn columnName="ИД" property="2" guid="0696a7-ed40-4ebf-a274-1e4" />
      <msh:tableColumn columnName="Название" property="2" guid="e1b12-3392-4978-b31f-5e54ff2e45bd" />
    </msh:table>
  </tiles:put>
</tiles:insert>

