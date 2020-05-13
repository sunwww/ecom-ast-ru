<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://struts.apache.org/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://www.nuzmsh.ru/tags/msh" prefix="msh" %>
<%@ taglib uri="http://www.ecom-ast.ru/tags/ecom" prefix="ecom" %>

<tiles:insert page="/WEB-INF/tiles/mainLayout.jsp" flush="true" >

  <tiles:put name="title" type="string">
    <msh:title mainMenu="Template" title="Классификаторы шаблонов" />
  </tiles:put>
  <tiles:put name="side" type="string">
    <msh:sideMenu title="Показать" />
    <msh:sideMenu title="Добавить">
      <msh:sideLink roles="/Policy/Mis/Template/Category/Create" key="ALT+N" action="/entityPrepareCreate-temp_category" name="Категорию" />
    </msh:sideMenu>
  </tiles:put>
  <tiles:put name="body" type="string">
    <msh:table name="list" action="entityParentView-temp_category.do" idField="id">
      <msh:tableColumn columnName="Название" property="name" />
      <msh:tableColumn columnName="Комментарии" property="comments" />
      <msh:tableColumn columnName="Дата создания" property="dateCreate" />
      <msh:tableColumn columnName="Создан" property="username" />
    </msh:table>
  </tiles:put>
</tiles:insert>

