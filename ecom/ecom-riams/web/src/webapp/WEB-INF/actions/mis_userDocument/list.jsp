<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://struts.apache.org/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://www.nuzmsh.ru/tags/msh" prefix="msh" %>
<%@ include file="/WEB-INF/tiles/header.jsp" %>

<tiles:insert page="/WEB-INF/tiles/mainLayout.jsp" flush="true" >

  <tiles:put name="title" type="string">
    </tiles:put>
  <tiles:put name="side" type="string">
    <msh:sideMenu title="Добавить">
      <msh:sideLink roles="/Policy/Mis/UserDocument/Create" key="ALT+2" action="/entityPrepareCreate-mis_userDocument" name="Создать документ" title="Создать документ" />
    </msh:sideMenu>
    </tiles:put>
  <tiles:put name="body" type="string">
    <msh:table name="list" action="entityView-mis_userDocument.do" idField="id">
      <msh:tableColumn columnName="Название" property="name" />
      <msh:tableColumn columnName="Имя файла" property="fileName" />
      <msh:tableColumn columnName="Группа" property="groupType" />
    </msh:table>
  </tiles:put>
</tiles:insert>