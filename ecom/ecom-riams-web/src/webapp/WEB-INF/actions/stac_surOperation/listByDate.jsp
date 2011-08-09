<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://struts.apache.org/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://www.nuzmsh.ru/tags/msh" prefix="msh" %>
<%@ taglib uri="http://www.ecom-ast.ru/tags/ecom" prefix="ecom" %>
<%@ taglib tagdir="/WEB-INF/tags" prefix="tags" %>

<tiles:insert page="/WEB-INF/tiles/mainLayout.jsp" flush="true" >

  <tiles:put name="title" type="string">
    <msh:title guid="helloItle-123" mainMenu="Journals" title="Журнал операций" />
  </tiles:put>
  <tiles:put name="side" type="string">
    <tags:style_currentMenu currentAction="stac_surOperation" />
    <tags:mis_journal />
  </tiles:put>
  <tiles:put name="body" type="string">
    <msh:section guid="863b6d75-fded-49ba-8eab-108bec8e092a">
      <msh:sectionTitle guid="1dcd4d93-235d-4141-a7ee-eca528858925">
        Результаты поиска за дату с ${param.id}.
        <msh:link action="journal_surOperation.do" guid="0078d0ce-6563-4bea-ae46-3de70c24c573">Выбрать другую дату</msh:link>
      </msh:sectionTitle>
      <msh:sectionContent guid="ab8e5a72-aadd-4c65-8691-2205506e63dc">
        <msh:table name="list" action="entityParentView-stac_surOperation.do" idField="id" guid="d579127c-69a0-4eca-b3e3-950381d1585c">
          <msh:tableColumn columnName="Пациент" identificator="false" property="patientInfo" guid="90a1e314-97e1-4584-b36c-1078f1f653fb" />
          <msh:tableColumn columnName="Период"  property="period"  />
          <msh:tableColumn columnName="Операция"  property="operationInfo"  />
          <msh:tableColumn columnName="Хирург"  property="surgeonInfo" />
          <msh:tableColumn columnName="Ассистенты"  property="surgeonsInfo" />          
          
<%--           <msh:tableColumn columnName="Год рождения" identificator="false" property="4" guid="18f2ca97-04f2-45a6-b2a7-ffe69fe13dfc" />
          <msh:tableColumn columnName="Операция" property=""/> --%>
        </msh:table>
      </msh:sectionContent>
    </msh:section>
  </tiles:put>
</tiles:insert>