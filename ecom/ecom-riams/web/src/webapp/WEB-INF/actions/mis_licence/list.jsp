<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://struts.apache.org/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://www.nuzmsh.ru/tags/msh" prefix="msh" %>

<tiles:insert page="/WEB-INF/tiles/mainLayout.jsp" flush="true" >

  <tiles:put name="title" type="string">
    <msh:title mainMenu="Lpu">Список лицензий</msh:title>
    <ecom:titleTrail beginForm="mis_lpuForm" mainMenu="Lpu" title="Список лицензий" />
  </tiles:put>
  <tiles:put name="side" type="string">
    <msh:sideMenu>
      <msh:sideLink key="ALT+1" params="id" action="/entityView-mis_lpu" name="⇧ К ЛПУ" />
      <hr />
      <msh:sideLink key="ALT+N" params="id" action="/entityParentPrepareCreate-mis_licence" name="Добавить лицензию" />
    </msh:sideMenu>
  </tiles:put>
  <tiles:put name="body" type="string">
    <msh:table name="list" action="entityParentView-mis_licence.do" idField="id">
      <msh:tableColumn columnName="Номер" property="numberDoc" />
      <msh:tableColumn columnName="Вид" property="nameTypeWork" />
      <msh:tableColumn columnName="Дата выдачи" property="dateVidal" />
      <msh:tableColumn columnName="Дата c" property="dateStart" />
      <msh:tableColumn columnName="Дата по" property="dateFinish" />
      <msh:tableColumn columnName="Статус" property="nameStatus" />
    </msh:table>
  </tiles:put>
</tiles:insert>

