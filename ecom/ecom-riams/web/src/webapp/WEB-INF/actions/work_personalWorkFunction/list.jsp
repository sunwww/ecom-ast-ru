<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://struts.apache.org/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://www.nuzmsh.ru/tags/msh" prefix="msh" %>
<%@ taglib uri="http://www.ecom-ast.ru/tags/ecom" prefix="ecom" %>

<tiles:insert page="/WEB-INF/tiles/mainLayout.jsp" flush="true" >

  <tiles:put name="title" type="string">
    <ecom:titleTrail beginForm="mis_workerForm" mainMenu="Lpu" title="Список рабочих функций" />
  </tiles:put>
  <tiles:put name="side" type="string">
    <msh:sideMenu title="Добавить">
      <msh:sideLink roles="/Policy/Mis/Worker/WorkFunction/Create" key="ALT+N" action="/entityParentPrepareCreate-work_personalWorkFunction"  name="Создать новую функцию" params="id" />
    </msh:sideMenu>
  </tiles:put>
  <tiles:put name="body" type="string">
    <msh:table name="list" action="entityView-work_personalWorkFunction.do" idField="id">
      <msh:tableColumn columnName="ИД" property="id" />
      <msh:tableColumn columnName="Код" property="code" />
      <msh:tableColumn columnName="Функция" property="name" />
      <msh:tableColumn columnName="Сотрудник" property="workerInfo" />
      <msh:tableColumn columnName="Рабочая группа" identificator="false" property="groupInfo" />
    </msh:table>
  </tiles:put>
</tiles:insert>

