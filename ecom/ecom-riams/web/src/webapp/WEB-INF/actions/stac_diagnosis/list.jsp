<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://struts.apache.org/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://www.nuzmsh.ru/tags/msh" prefix="msh" %>
<%@ taglib uri="http://www.ecom-ast.ru/tags/ecom" prefix="ecom" %>

<tiles:insert page="/WEB-INF/tiles/mainLayout.jsp" flush="true" >

  <tiles:put name="title" type="string">
    <ecom:titleTrail beginForm="mis_medCaseForm" mainMenu="Patient" title="Список всех диагнозов" />
  </tiles:put>
  <tiles:put name="side" type="string">
    <msh:sideMenu title="Добавить">
      <msh:sideLink params="id" action="/entityParentPrepareCreate-stac_diagnosis" name="Добавить диагноз" title="Добавить диагноз" roles="/Policy/Mis/MedCase/Stac/Ssl/Diagnosis/Create" key="ALT+N" />
    </msh:sideMenu>
  </tiles:put>
  <tiles:put name="body" type="string">
    <msh:table name="list" action="entityParentView-stac_diagnosis.do" idField="id">
      <msh:tableColumn columnName="Дата установления" property="establishDate" />
      <msh:tableColumn columnName="Тип регистрации" property="registrationTypeInfo" />
      <msh:tableColumn columnName="Первичность" identificator="false" property="primaryInfo" />
      <msh:tableColumn columnName="Приоритет" property="priorityDiagnosisText" />
      <msh:tableColumn columnName="Наименование" property="name" />
      <msh:tableColumn columnName="Код МКБ" property="idc10Text" />
      <msh:tableColumn columnName="Специалист" property="workerInfo" />
    </msh:table>
  </tiles:put>
</tiles:insert>

