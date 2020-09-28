<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://struts.apache.org/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://www.nuzmsh.ru/tags/msh" prefix="msh" %>
<%@ taglib uri="http://www.ecom-ast.ru/tags/ecom" prefix="ecom" %>

<tiles:insert page="/WEB-INF/tiles/mainLayout.jsp" flush="true" >

  <tiles:put name="title" type="string">
    
  </tiles:put>
  <tiles:put name="side" type="string">
    <msh:sideMenu>
      <msh:sideLink roles="/Policy/Mis/AssessmentCard/Create" key="ALT+N" action="/entityPrepareCreate-mis_assessmentCardTemplate" name="Создать"  />
    </msh:sideMenu>
  </tiles:put>
  <tiles:put name="body" type="string">
  <ecom:webQuery name="cardList" nameFldSql="cardList_sql" nativeSql="select id, name from assessmentCardTemplate act" />
    <msh:table name="cardList" action="entityView-mis_assessmentCardTemplate.do" idField="1">
      <msh:tableColumn columnName="ИД" property="2" />
      <msh:tableColumn columnName="Название" property="2" />
    </msh:table>
  </tiles:put>
</tiles:insert>

