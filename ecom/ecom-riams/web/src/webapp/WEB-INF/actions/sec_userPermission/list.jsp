<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://struts.apache.org/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://www.nuzmsh.ru/tags/msh" prefix="msh" %>
<%@ taglib uri="http://www.ecom-ast.ru/tags/ecom" prefix="ecom" %>

<tiles:insert page="/WEB-INF/tiles/mainLayout.jsp" flush="true" >

  <tiles:put name="title" type="string">
    <msh:title mainMenu="Config" title="Разрешения пользователя" />
  </tiles:put>
  <tiles:put name="body" type="string">
    <msh:table navigationAction="js-sec_userPermission-listNext.do" name="list" action="entityView-sec_userPermission.do" idField="id">
      <msh:tableColumn columnName="Дата начала" property="dateFrom" />
      <msh:tableColumn columnName="Дата окончания" property="dateTo" />
      <msh:tableColumn columnName="Объект" property="objectInfo" />
      <msh:tableColumn columnName="Разрешение" property="permissionInfo" />
      <msh:tableColumn columnName="Id объекта" property="idObject" />
      <msh:tableColumn columnName="Пользователь" property="userInfo" />
      <msh:tableColumn columnName="Кто дал разрешение" property="createUsername" />
      <msh:tableColumn columnName="Когда" property="createDate" />
    </msh:table>
  </tiles:put>
  <tiles:put name="side" type="string">
    <msh:sideMenu title="Добавить">
      <msh:sideLink params="" action="/entityPrepareCreate-sec_userPermission" name="Новое разрешение" title="Добавить новое разрешение" roles="/Policy/Jaas/Permission/User/Create" />
    </msh:sideMenu>
  </tiles:put>
</tiles:insert>

