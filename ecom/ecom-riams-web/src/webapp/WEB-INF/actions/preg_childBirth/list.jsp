<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://struts.apache.org/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://www.nuzmsh.ru/tags/msh" prefix="msh" %>
<%@ taglib uri="http://www.ecom-ast.ru/tags/ecom" prefix="ecom" %>

<tiles:insert page="/WEB-INF/tiles/mainLayout.jsp" flush="true" >

  <tiles:put name="title" type="string">
    <ecom:titleTrail beginForm="mis_medCaseForm" mainMenu="Patient" title="Описание родов"  />
  </tiles:put>
  <tiles:put name="body" type="string">
    <msh:table name="list" action="entityParentView-preg_childBirth.do" idField="id" >
      <msh:tableColumn columnName="Дата окончания родов" property="birthFinishDate" />
    </msh:table>
  </tiles:put>
  <tiles:put name="side" type="string">
    <msh:sideMenu title="Добавить" guid="d05af144-f458-45dd-a3eb-94bb77eb2007">
      <msh:sideLink params="id" action="/entityParentPrepareCreate-preg_childBirth" name="Создать" roles="/Policy/Mis/Pregnancy/ChildBirth/Create" guid="71d2536e-c475-4b91-975c-a3440b2a6afa" />
      <msh:sideLink params="id" action="/entityParentPrepareCreate-preg_neonatalNewBorn" name="Добавить информацию о новорожденном" roles="/Policy/Mis/NewBorn/Create"/>
    </msh:sideMenu>    
  </tiles:put>
</tiles:insert>

