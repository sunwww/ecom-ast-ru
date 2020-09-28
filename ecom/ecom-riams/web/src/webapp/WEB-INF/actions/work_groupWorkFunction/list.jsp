<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://struts.apache.org/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://www.nuzmsh.ru/tags/msh" prefix="msh" %>
<%@ taglib uri="http://www.ecom-ast.ru/tags/ecom" prefix="ecom" %>

<tiles:insert page="/WEB-INF/tiles/mainLayout.jsp" flush="true" >

  <tiles:put name="title" type="string">
    <ecom:titleTrail beginForm="mis_lpuForm" mainMenu="Lpu" title="Список рабочих групп" />
  </tiles:put>
  <tiles:put name="side" type="string">
    <msh:sideMenu title="Добавить">
      <msh:sideLink roles="/Policy/Mis/Worker/WorkFunction/Create" key="ALT+N" action="/entityParentPrepareCreate-work_groupWorkFunction" name="Групповая функция" params="id" title="Добавить новую групповую функцию" />
    </msh:sideMenu>
    <msh:sideMenu title="Перейти">
      <msh:sideLink roles="/Policy/Mis/Worker/WorkFunction/View" key="ALT+2" action="/entityParentList-mis_worker" name="К списку сотрудников" title="Перейти к списку сотрудников" params="id" />
    </msh:sideMenu>
  </tiles:put>
  <tiles:put name="body" type="string">
    <msh:table name="list" action="entityView-work_groupWorkFunction.do" idField="id">
      <msh:tableColumn columnName="Название" property="groupName" />
      <msh:tableColumn columnName="Функция" property="name" />
    </msh:table>
  </tiles:put>
</tiles:insert>

