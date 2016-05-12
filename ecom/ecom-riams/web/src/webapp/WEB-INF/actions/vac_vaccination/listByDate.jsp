<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://struts.apache.org/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://www.nuzmsh.ru/tags/msh" prefix="msh" %>
<%@ taglib uri="http://www.ecom-ast.ru/tags/ecom" prefix="ecom" %>
<%@ taglib tagdir="/WEB-INF/tags" prefix="tags" %>

<tiles:insert page="/WEB-INF/tiles/mainLayout.jsp" flush="true" >

  <tiles:put name="title" type="string">
    <msh:title guid="helloItle-123" mainMenu="Journals" title="Журнал вакцинаций" />
  </tiles:put>
  <tiles:put name="side" type="string">
    <tags:style_currentMenu currentAction="vac_vaccination" />
    <tags:mis_journal />
  </tiles:put>
  <tiles:put name="body" type="string">
    <msh:section guid="863b6d75-fded-49ba-8eab-108bec8e092a">
      <msh:sectionTitle guid="1dcd4d93-235d-4141-a7ee-eca528858925">
        Результаты поиска за дату с ${param.id}.
        <msh:link action="journal_vaccination.do" guid="0078d0ce-6563-4bea-ae46-3de70c24c573">Выбрать другую дату</msh:link>
      </msh:sectionTitle>
      <msh:sectionContent guid="ab8e5a72-aadd-4c65-8691-2205506e63dc">
        <ecom:webQuery name="listInf" guid="909fd277-28b6-4ce3-841c-7d76ee74c9e0" nativeSql="select smc.id,pat.id&#xA;from MedCase as smc&#xA;left join Patient pat on smc.patient_id=pat.id &#xA;where smc.DTYPE='ServiceMedCase' and smc.dateExecute='${param.id}'" />
        <msh:table name="listInf" action="entityView-vac_vaccination.do" idField="1" guid="d579127c-69a0-4eca-b3e3-950381d1585c">
          <msh:tableColumn columnName="ФИО пациента" identificator="false" property="2" guid="b36f0512-f20a-433c-8a96-bb33d291a745" />
          <msh:tableColumn columnName="Время" identificator="false" property="3" guid="90a1e314-97e1-4584-b36c-1078f1f653fb" />
          <msh:tableColumn columnName="Мед.услуга" identificator="false" property="4" guid="18f2ca97-04f2-45a6-b2a7-ffe69fe13dfc" />
          <msh:tableColumn identificator="false" property="2" guid="f78053fd-d2c4-4c8b-86ae-94b6612d36ea" columnName="Кол-во" />
          <msh:tableColumn columnName="Специалист" property="5" guid="d64d2425-82f9-41d8-88b9-9a5fb57719f2" />
          <msh:tableColumn columnName="Случай мед.обслуживания" property="6" guid="dd0902ee-6c65-4b6b-9242-0942167b5b9d" />
        </msh:table>
      </msh:sectionContent>
    </msh:section>
  </tiles:put>
</tiles:insert>

