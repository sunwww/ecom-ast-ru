<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ include file="/WEB-INF/tiles/header.jsp" %>
<%@ taglib uri="http://www.nuzmsh.ru/tags/msh" prefix="msh" %>

<tiles:insert page="/WEB-INF/tiles/mainLayout.jsp" flush="true" >

  <tiles:put name="title" type="string">
    <msh:title mainMenu="Document" guid="068362ba-1b6d-4f51-ab6d-c1c53f814790">Заполнения</msh:title>
  </tiles:put>
  <tiles:put name="side" type="string">
    <msh:sideMenu guid="e900dd3f-ead5-4507-beec-8788cad09880">
      <msh:sideLink roles="/Policy/Exp/FillTime/Create" key="ALT+N" action="/entityPrepareCreate-exp_fillTime" name="Создать новое заполнение" guid="17068f14-219a-4b7b-84e8-49b844d8765c" />
    </msh:sideMenu>
  </tiles:put>
  <tiles:put name="body" type="string">
    <msh:table name="list" action="entityView-exp_fillTime.do" idField="id" guid="52b0f477-c54b-4a96-9319-e647bd3eeb3c">
      <msh:tableColumn columnName="Название" property="name" guid="22e5e595-b712-4e2d-916f-b00037ff8b91" />
      <msh:tableColumn columnName="Запрос" property="queryString" guid="db750dec-58f4-4e37-b2c3-f2508e74531f" />
    </msh:table>
  </tiles:put>
</tiles:insert>

