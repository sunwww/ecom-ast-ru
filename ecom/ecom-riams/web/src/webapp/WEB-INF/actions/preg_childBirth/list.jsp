<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://struts.apache.org/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://www.nuzmsh.ru/tags/msh" prefix="msh" %>
<%@ taglib uri="http://www.ecom-ast.ru/tags/ecom" prefix="ecom" %>

<tiles:insert page="/WEB-INF/tiles/main${param.short}Layout.jsp" flush="true" >

  <tiles:put name="title" type="string">
    <ecom:titleTrail beginForm="mis_medCaseForm" mainMenu="Patient" title="Описание родов"  />
  </tiles:put>
  <tiles:put name="body" type="string">
  <msh:tableEmpty name="list">
  <msh:section>
  	<msh:sectionTitle>
  	Описание родов
  	<a href="entityParentPrepareCreate-preg_childBirth.do?id=${param.id}"><img src="/skin/images/main/plus.png" alt="Добавить запись" title="Добавить запись" height="14" width="14">Добавить описание родов</a>
  	<a href="entityParentPrepareCreate-preg_neonatalNewBorn.do?id=${param.id}"><img src="/skin/images/main/plus.png" alt="Добавить запись" title="Добавить запись" height="14" width="14">Добавить описание новорожденного (с пустой записью родов)</a>
  	</msh:sectionTitle>
  </msh:section>
  </msh:tableEmpty>
  <msh:tableNotEmpty name="list">
  <msh:section title="Описание родов">
    <msh:table  name="list" action="entityParentView-preg_childBirth.do" idField="id" >
      <msh:tableColumn columnName="Дата окончания родов" property="birthFinishDate" />
    </msh:table>
    </msh:section>
    </msh:tableNotEmpty>
    
  </tiles:put>
  <tiles:put name="side" type="string">
    <msh:sideMenu title="Добавить">
      <msh:sideLink params="id" action="/entityParentPrepareCreate-preg_childBirth" name="Создать" roles="/Policy/Mis/Pregnancy/ChildBirth/Create" />
      <msh:sideLink params="id" action="/entityParentPrepareCreate-preg_neonatalNewBorn" name="Добавить информацию о новорожденном" roles="/Policy/Mis/NewBorn/Create"/>
    </msh:sideMenu>    
  </tiles:put>
</tiles:insert>

