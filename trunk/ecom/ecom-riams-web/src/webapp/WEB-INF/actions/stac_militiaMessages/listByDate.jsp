<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://struts.apache.org/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://www.nuzmsh.ru/tags/msh" prefix="msh" %>
<%@ taglib uri="http://www.ecom-ast.ru/tags/ecom" prefix="ecom" %>
<%@ taglib tagdir="/WEB-INF/tags" prefix="tags" %>

<tiles:insert page="/WEB-INF/tiles/mainLayout.jsp" flush="true" >

  <tiles:put name="title" type="string">
    <msh:title guid="helloItle-123" mainMenu="Journals" title="Журнал сообщений в милицию" />
  </tiles:put>
  <tiles:put name="side" type="string">
    <tags:style_currentMenu currentAction="stac_militiaMessages" />
    <tags:mis_journal />
  </tiles:put>
  <tiles:put name="body" type="string">
    <msh:section guid="863b6d75-fded-49ba-8eab-108bec8e092a">
      <msh:sectionTitle guid="1dcd4d93-235d-4141-a7ee-eca528858925">Результаты поиска за дату с ${param.id}.<msh:link action="journal_militiaMessage.do">Выбрать другую дату</msh:link></msh:sectionTitle>
      <msh:sectionContent guid="ab8e5a72-aadd-4c65-8691-2205506e63dc">
        <ecom:webQuery name="listInf" nativeSql="select pm.id&#xA; ,pat.lastname || ' ' || pat.firstname || ' ' || pat.middlename&#xA; ,pat.birthday&#xA; ,pm.number, pm.phoneDate, pm.phoneTime,pm.phone,pm.recieverOrganization from PhoneMessage pm left join MedCase ms on pm.medCase_id=ms.id left join Patient pat on ms.patient_id = pat.id  where pm.phoneMessageType_id=2 and pm.phoneDate='${param.id}' " guid="909fd277-28b6-4ce3-841c-7d76ee74c9e0" />
        <msh:table name="listInf" action="entityView-stac_militiaMessages.do" idField="1" guid="d579127c-69a0-4eca-b3e3-950381d1585c">
          <msh:tableColumn identificator="false" property="2" guid="f78053fd-d2c4-4c8b-86ae-94b6612d36ea" columnName="Пациент" />
          <msh:tableColumn columnName="Год рождения" identificator="false" property="3" guid="18f2ca97-04f2-45a6-b2a7-ffe69fe13dfc" />
          <msh:tableColumn columnName="Номер сообщения" property="4" guid="ce16c32c-9459-4673-9ce8-d6e646f969ff" />
          <msh:tableColumn columnName="Дата регистрации" property="5" guid="fc26523a-eb9c-44bc-b12e-42cb7ca9ac5b" />
          <msh:tableColumn columnName="Время регистрации" property="6" guid="35347247-b552-4154-a82a-ee484a1714ad" />
          <msh:tableColumn columnName="Телефон" property="7" guid="d2eebfd0-f043-4230-8d24-7ab99f0d5b45" />
          <msh:tableColumn columnName="Принявшая сообщение организация" property="8" guid="6b562107-5017-4559-9b94-ab525b579202" />
        </msh:table>
      </msh:sectionContent>
    </msh:section>
  </tiles:put>
</tiles:insert>

