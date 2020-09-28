<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://struts.apache.org/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://www.nuzmsh.ru/tags/msh" prefix="msh" %>
<%@ taglib tagdir="/WEB-INF/tags" prefix="tags" %>

<tiles:insert page="/WEB-INF/tiles/mainLayout.jsp" flush="true" >

  <tiles:put name="title" type="string">
    <msh:title mainMenu="Expert">Экспертные карты</msh:title>
  </tiles:put>
  <tiles:put name="side" type="string">

    <tags:expert_menu currentAction="expert_card"/>
  </tiles:put>
  <tiles:put name="body" type="string">
    <form />
    <msh:table name="list" action="entityView-expert_card.do" idField="id" navigationAction="js-expert_card-list.do">
      <msh:tableColumn columnName="Тип экспертизы" property="kind" />
      <msh:tableColumn columnName="Леч.врач" property="doctorCase" />
      <msh:tableColumn columnName="Отделение" property="department" />
      <msh:tableColumn columnName="Диагноз" property="mkb" />
      <msh:tableColumn columnName="Пациент" property="patient" />
      <msh:tableColumn columnName="№мед.карты" property="numberMedCard" />
      <msh:tableColumn columnName="Зав.отд." property="zavMark" />
      <msh:tableColumn columnName="Эксперт (КР)" property="expertMark" />
      <msh:tableColumn columnName="Пользователь" property="createUsername" />
      <msh:tableColumn columnName="Дата создания" property="createDate" />
    </msh:table>
  </tiles:put>
</tiles:insert>

