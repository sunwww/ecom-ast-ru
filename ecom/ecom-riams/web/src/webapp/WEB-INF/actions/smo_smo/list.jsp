<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://struts.apache.org/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://www.nuzmsh.ru/tags/msh" prefix="msh" %>
<%@ taglib uri="http://www.ecom-ast.ru/tags/ecom" prefix="ecom" %>

<tiles:insert page="/WEB-INF/tiles/mainLayout.jsp" flush="true" >

  <tiles:put name="title" type="string">
    <msh:title mainMenu="Patient">Проба</msh:title>
  </tiles:put>
  <tiles:put name="side" type="string">
    <msh:sideMenu>
      <msh:sideLink roles="/Policy/IdeMode/Hello/Create" key="ALT+N" action="/entityPrepareCreate-smo_smo" name="Создать новое" />
    </msh:sideMenu>
  </tiles:put>
  <tiles:put name="body" type="string">
    <msh:table name="list" action="entityView-smo_smo.do" idField="id">
      <msh:tableColumn columnName="Номер" property="id" />
      <msh:tableColumn columnName="Дата открытия" property="dateStart" />
      <msh:tableColumn columnName="Кем открыт" property="parent" />
      <msh:tableColumn columnName="Дата закрытия" property="hello" />
      <msh:tableColumn columnName="Кем закрыт" property="hello" />
      <msh:tableColumn columnName="Владелец" property="hello" />
      <msh:tableColumn columnName="Количество визитов" property="link" />
      <msh:tableColumn columnName="Количество дней" property="link" />
    </msh:table>
  </tiles:put>
</tiles:insert>

