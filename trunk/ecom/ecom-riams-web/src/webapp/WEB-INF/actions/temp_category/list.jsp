<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://struts.apache.org/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://www.nuzmsh.ru/tags/msh" prefix="msh" %>
<%@ taglib uri="http://www.ecom-ast.ru/tags/ecom" prefix="ecom" %>

<tiles:insert page="/WEB-INF/tiles/mainLayout.jsp" flush="true" >

  <tiles:put name="title" type="string">
    <msh:title mainMenu="Template" guid="65127a6f-d6d3-4b8e-b436-c6aeeaea35ae" title="Классификаторы шаблонов" />
  </tiles:put>
  <tiles:put name="side" type="string">
    <msh:sideMenu title="Показать" guid="a47dfc0b-97d1-4cb5-b904-4ff717e612a7" />
    <msh:sideMenu title="Добавить" guid="60616958-11ef-48b0-bec7-f6b1d0b8463f">
      <msh:sideLink roles="/Policy/Mis/Template/Category/Create" key="ALT+N" action="/entityPrepareCreate-temp_category" name="Категорию" guid="1faa5477-419b-4f77-8379-232e33a61922" />
    </msh:sideMenu>
  </tiles:put>
  <tiles:put name="body" type="string">
    <msh:table name="list" action="entityParentView-temp_category.do" idField="id" guid="3c4adc65-cfce-4205-a2dd-91ba8ba87543">
      <msh:tableColumn columnName="Название" property="name" guid="ef71e615-5b55-443c-a439-27cc7968454a" />
      <msh:tableColumn columnName="Комментарии" property="comments" guid="5c893448-9084-4b1a-b301-d7aca8f6307c" />
      <msh:tableColumn columnName="Дата создания" property="dateCreate" guid="dbe4fc52-03f7-42af-9555-a4bee397a800" />
      <msh:tableColumn columnName="Создан" property="username" guid="715694de-3af4-4395-b777-2cb19bdbcf62" />
    </msh:table>
  </tiles:put>
</tiles:insert>

