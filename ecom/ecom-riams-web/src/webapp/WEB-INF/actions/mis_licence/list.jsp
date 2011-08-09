<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://struts.apache.org/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://www.nuzmsh.ru/tags/msh" prefix="msh" %>

<tiles:insert page="/WEB-INF/tiles/mainLayout.jsp" flush="true" >

  <tiles:put name="title" type="string">
    <msh:title mainMenu="Lpu" guid="474c4027-f405-4624-8586-1338612f102b">Список лицензий</msh:title>
    <ecom:titleTrail beginForm="mis_lpuForm" mainMenu="Lpu" title="Список лицензий" guid="d9968105-ea03-4f93-a3a7-7c4ea84f5b73" />
  </tiles:put>
  <tiles:put name="side" type="string">
    <msh:sideMenu guid="9681ddef-cd30-4108-940c-6550098e67d4">
      <msh:sideLink key="ALT+1" params="id" action="/entityView-mis_lpu" name="⇧ К ЛПУ" guid="2a85e965-7ab7-4334-9f63-10f74fdfcff5" />
      <hr />
      <msh:sideLink key="ALT+N" params="id" action="/entityParentPrepareCreate-mis_licence" name="Добавить лицензию" guid="633446e5-bed6-4c0d-9167-a2493f374285" />
    </msh:sideMenu>
  </tiles:put>
  <tiles:put name="body" type="string">
    <msh:table name="list" action="entityParentView-mis_licence.do" idField="id" guid="bf48ca5a-ef93-4725-9a84-f0319b600f7d">
      <msh:tableColumn columnName="Номер" property="numberDoc" guid="c8a88aaa-7c37-45ca-8228-1ff9766558aa" />
      <msh:tableColumn columnName="Вид" property="nameTypeWork" guid="a07ba254-de0d-4ddf-b1c1-e84d79e62038" />
      <msh:tableColumn columnName="Дата выдачи" property="dateVidal" guid="8ad2f88f-ea9a-4dcc-9a5c-77364b85dd28" />
      <msh:tableColumn columnName="Дата c" property="dateStart" guid="38547384-5259-4bfa-86b1-e96d9bccf062" />
      <msh:tableColumn columnName="Дата по" property="dateFinish" guid="5b4cb84a-61ab-42df-9647-7a442fd0bc68" />
      <msh:tableColumn columnName="Статус" property="nameStatus" guid="69e142b3-bc58-4d42-a796-e10846c2e21a" />
    </msh:table>
  </tiles:put>
</tiles:insert>

