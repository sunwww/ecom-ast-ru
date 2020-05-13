<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://struts.apache.org/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://www.nuzmsh.ru/tags/msh" prefix="msh" %>
<%@ taglib uri="http://www.ecom-ast.ru/tags/ecom" prefix="ecom" %>

<tiles:insert page="/WEB-INF/tiles/mainLayout.jsp" flush="true" >

  <tiles:put name="title" type="string">
    <ecom:titleTrail beginForm="preg_childBirthForm" mainMenu="Patient" title="Данные о новорожденном" />
  </tiles:put>
  <tiles:put name="body" type="string">
    <msh:table name="list" action="entityParentView-preg_newBorn.do" idField="id">
      <msh:tableColumn columnName="Дата рождения" property="birthDate" />
      <msh:tableColumn columnName="Время рождения" property="birthTime" />
    </msh:table>
  </tiles:put>
  <tiles:put name="side" type="string">
    <msh:sideMenu title="Добавить">
      <msh:sideLink params="id" action="/entityParentPrepareCreate-preg_newBorn" name="Создать" roles="/Policy/Mis/NewBorn/Create" />
    </msh:sideMenu>
  </tiles:put>
</tiles:insert>

