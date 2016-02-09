<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://struts.apache.org/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://www.nuzmsh.ru/tags/msh" prefix="msh" %>
<%@ include file="/WEB-INF/tiles/header.jsp" %>

<tiles:insert page="/WEB-INF/tiles/mainLayout.jsp" flush="true" >

  <tiles:put name="title" type="string">
    </tiles:put>
  <tiles:put name="side" type="string">
    <msh:sideMenu guid="360e85c3-7aa1-4a04-8c1d-a9c0a6739efa" title="Добавить">
      <msh:sideLink roles="/Policy/Mis/UserDocument/Create" key="ALT+2" action="/entityPrepareCreate-mis_userDocument" name="Создать документ" title="Создать документ" guid="712b4156-54e2-4e7b-b0da-a46821eba3de" />
    </msh:sideMenu>
    </tiles:put>
  <tiles:put name="body" type="string">
    <msh:table name="list" action="entityView-mis_userDocument.do" idField="id" guid="d20ae6f6-f534-4d56-affe-ff02d3034d32">
      <msh:tableColumn columnName="Название" property="name" guid="4797395b-c239-4020-9c23-5b64f1ceb96e" />
      <msh:tableColumn columnName="Имя файла" property="fileName" guid="4797395b-c239-4020-9c23-5b64f1ceb96e" />
      <msh:tableColumn columnName="Группа" property="groupType" guid="4797395b-c239-4020-9c23-5b64f1ceb96e" />
    </msh:table>
  </tiles:put>
</tiles:insert>