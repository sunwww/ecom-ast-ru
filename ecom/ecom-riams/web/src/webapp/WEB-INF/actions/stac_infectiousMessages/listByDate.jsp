<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://struts.apache.org/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://www.nuzmsh.ru/tags/msh" prefix="msh" %>
<%@ taglib uri="http://www.ecom-ast.ru/tags/ecom" prefix="ecom" %>
<%@ taglib tagdir="/WEB-INF/tags" prefix="tags" %>

<tiles:insert page="/WEB-INF/tiles/mainLayout.jsp" flush="true" >

  <tiles:put name="title" type="string">
    <msh:title mainMenu="Journals" title="Журнал экстренных извещений об инфекционных заболеваниях" />
  </tiles:put>
  <tiles:put name="side" type="string">
    <tags:style_currentMenu currentAction="stac_infectiousMessages" />
    <tags:mis_journal />
  </tiles:put>
  <tiles:put name="body" type="string">
    <msh:section>
      <msh:sectionTitle>Результаты поиска за дату с ${param.id}.<msh:link action="journal_infectMessage.do">Выбрать другую дату</msh:link></msh:sectionTitle>
      <msh:sectionContent>
        <ecom:webQuery name="listInf" nativeSql="select pm.id&#xA; ,pat.lastname || ' ' || pat.firstname || ' ' || pat.middlename&#xA; ,pat.birthday&#xA; ,pm.number, pm.phoneDate, pm.phoneTime,pm.phone,pm.recieverOrganization from PhoneMessage pm left join MedCase ms on pm.medCase_id=ms.id left join Patient pat on ms.patient_id = pat.id  where pm.phoneMessageType_id=1 and pm.phoneDate='${param.id}' " />
        <msh:table name="listInf" action="entityView-stac_infectiousMessages.do" idField="1">
          <msh:tableColumn identificator="false" property="2" columnName="Пациент" />
          <msh:tableColumn columnName="Год рождения" identificator="false" property="3" />
          <msh:tableColumn columnName="Номер сообщения" property="4" />
          <msh:tableColumn columnName="Дата регистрации" property="5" />
          <msh:tableColumn columnName="Время регистрации" property="6" />
          <msh:tableColumn columnName="Телефон" property="7" />
          <msh:tableColumn columnName="Принявшая сообщение организация" property="8" />
        </msh:table>
      </msh:sectionContent>
    </msh:section>
  </tiles:put>
</tiles:insert>

