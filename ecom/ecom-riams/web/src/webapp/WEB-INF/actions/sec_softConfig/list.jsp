<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://struts.apache.org/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://www.nuzmsh.ru/tags/msh" prefix="msh" %>
<%@ taglib uri="http://www.ecom-ast.ru/tags/ecom" prefix="ecom" %>

<tiles:insert page="/WEB-INF/tiles/mainLayout.jsp" flush="true" >

  <tiles:put name="title" type="string">
    <msh:title mainMenu="Config" title="Настройки приложения" guid="5d110f49-20c3-4699-b6d0-b657032a8fae" />
  </tiles:put>
  <tiles:put name="body" type="string">
    <msh:table name="list" action="entityView-sec_softConfig.do" idField="id" guid="34ddbeac-1d7e-4552-a53a-c6938c9f8bad">
      <msh:tableColumn columnName="Ключ" property="key" guid="34416cd7-307b-4963-9bec-4c3a9a7527af" />
      <msh:tableColumn columnName="Значение" property="keyValue" guid="83eb9636-f2a9-4377-a4e7-58fc0f46f748" />
      <msh:tableColumn columnName="Описание" property="description" guid="4b323854-8fa1-41e0-b959-632b342cd506" />
    </msh:table>
  </tiles:put>
  <tiles:put name="side" type="string">
    <msh:sideMenu title="Добавить" guid="b1b57fd2-b9ed-4760-b54e-f59b245e755a">
      <msh:sideLink params="" action="/entityPrepareCreate-sec_softConfig" name="Новую настройку" title="Добавить новую настройку" guid="cd3d23c1-1cfe-42ec-b53d-6e45f7555073" />
    </msh:sideMenu>
  </tiles:put>
</tiles:insert>

