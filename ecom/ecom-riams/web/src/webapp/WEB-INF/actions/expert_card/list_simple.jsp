<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://struts.apache.org/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://www.nuzmsh.ru/tags/msh" prefix="msh" %>
<%@ taglib tagdir="/WEB-INF/tags" prefix="tags" %>

<tiles:insert page="/WEB-INF/tiles/mainLayout.jsp" flush="true" >

  <tiles:put name="title" type="string">
    <msh:title mainMenu="Expert" guid="a79e22af-e87a-45dd-9743-59a1f8f3d66a">Экспертные карты</msh:title>
  </tiles:put>
  <tiles:put name="side" type="string">

    <tags:expert_menu currentAction="expert_card"/>
  </tiles:put>
  <tiles:put name="body" type="string">
    <form />
    <msh:table name="list" action="entityView-expert_card.do" idField="id" guid="c0ba5c22-fda6-4d4f-89c4-aa58abb1e9d8" navigationAction="js-expert_card-list.do">
      <msh:tableColumn columnName="Тип экспертизы" property="kind" guid="69-8a75-e825fd37e296" />
      <msh:tableColumn columnName="Леч.врач" property="doctorCase" guid="69-8a75-e825fd37e296" />
      <msh:tableColumn columnName="Отделение" property="department" guid="81b717f5-f9db-4033-aa22-c680b21" />
      <msh:tableColumn columnName="Диагноз" property="mkb" />
      <msh:tableColumn columnName="Пациент" property="patient" guid="69-8a75-e825fd37e296" />
      <msh:tableColumn columnName="№мед.карты" property="numberMedCard" guid="698c-d4e3-4be5-8a75-e825fd37e296" />
      <msh:tableColumn columnName="Зав.отд." property="zavMark" guid="6c34a7-4512-90aa-75e4e5ae6d63" />
      <msh:tableColumn columnName="Эксперт (КР)" property="expertMark" guid="6c3be340-b4a7-4512-90aa-75e4e5ae6d63" />
      <msh:tableColumn columnName="Пользователь" property="createUsername" guid="c28f06f0-c64a-4cdd-b84f-b1e081186496" />
      <msh:tableColumn columnName="Дата создания" property="createDate" guid="c28f06f0-c64a-4cdd-b84f-b1e081186496" />
    </msh:table>
  </tiles:put>
</tiles:insert>

