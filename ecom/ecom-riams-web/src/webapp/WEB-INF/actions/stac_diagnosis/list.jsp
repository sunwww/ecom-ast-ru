<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://struts.apache.org/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://www.nuzmsh.ru/tags/msh" prefix="msh" %>
<%@ taglib uri="http://www.ecom-ast.ru/tags/ecom" prefix="ecom" %>

<tiles:insert page="/WEB-INF/tiles/mainLayout.jsp" flush="true" >

  <tiles:put name="title" type="string">
    <ecom:titleTrail beginForm="mis_medCaseForm" mainMenu="Patient" title="Список всех диагнозов" guid="40efbd1b-4177-47a8-9aad-1971732f3f98" />
  </tiles:put>
  <tiles:put name="side" type="string">
    <msh:sideMenu guid="helloSideMenu-123" title="Добавить">
      <msh:sideLink params="id" action="/entityParentPrepareCreate-stac_diagnosis" name="Добавить диагноз" title="Добавить диагноз" guid="2209b5f9-4b4f-4ed5-b825-b66f2ac57e87" roles="/Policy/Mis/MedCase/Stac/Ssl/Diagnosis/Create" key="ALT+N" />
    </msh:sideMenu>
  </tiles:put>
  <tiles:put name="body" type="string">
    <msh:table name="list" action="entityParentView-stac_diagnosis.do" idField="id" guid="b621e361-1e0b-4ebd-9f58-b7d919b45bd6">
      <msh:tableColumn columnName="Дата установления" property="establishDate" guid="0694f6a7-ed40-4ebf-a274-1efd6901cfe4" />
      <msh:tableColumn columnName="Тип регистрации" property="registrationTypeInfo" guid="781559cd-fd34-40f5-a214-cec404fe19e3" />
      <msh:tableColumn columnName="Первичность" identificator="false" property="primaryInfo" guid="5905cf65-048f-4ce1-8301-5aef1e9ac80e" />
      <msh:tableColumn columnName="Приоритет" property="priorityDiagnosisText" guid="2bab495e-eadb-4cd9-b2e9-140bf7a5f43f" />
      <msh:tableColumn columnName="Наименование" property="name" guid="6682eeef-105f-43a0-be61-30a865f27972" />
      <msh:tableColumn columnName="Код МКБ" property="idc10Text" guid="f34e1b12-3392-4978-b31f-5e54ff2e45bd" />
      <msh:tableColumn columnName="Специалист" property="workerInfo" guid="f31b12-3392-4978-b31f-5e54ff2e45bd" />
    </msh:table>
  </tiles:put>
</tiles:insert>

