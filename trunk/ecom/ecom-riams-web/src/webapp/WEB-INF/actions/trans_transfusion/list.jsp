<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://struts.apache.org/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://www.nuzmsh.ru/tags/msh" prefix="msh" %>
<%@ taglib uri="http://www.ecom-ast.ru/tags/ecom" prefix="ecom" %>

<tiles:insert page="/WEB-INF/tiles/mainLayout.jsp" flush="true" >

  <tiles:put name="title" type="string">
    <ecom:titleTrail beginForm="mis_medCaseForm" mainMenu="Patient" title="Список переливаний за случай медицинского обслуживания" guid="c951c449-0ed2-489d-9163-da1263effaa3" />
  </tiles:put>
  <tiles:put name="body" type="string">
    <msh:table name="list" action="entitySubclassView-trans_transfusion.do" idField="id" guid="be9cacbc-17e8-4a04-8d57-bd2cbbaeba30">
      <msh:tableColumn columnName="Номер в журнале" property="journalNumber" guid="ed7e6ec7-524e-4b87-8b2c-5a722792a123" />
      <msh:tableColumn columnName="Дата приготовления" property="preparationDate" guid="1ef2e314-6eb6-4c85-be47-ca392566d371" />
      <msh:tableColumn columnName="Дата начала" property="startDate" guid="2976f5c7-3844-4ae2-be91-2a395cae0f1f" />
      <msh:tableColumn columnName="Доза" property="doze" guid="d9642df9-5653-4920-bb78-1622cbeefa34" />
    </msh:table>
  </tiles:put>
  <tiles:put name="side" type="string">
    <msh:sideMenu title="Добавить" guid="b33faf64-b72e-4845-bf32-5fda8e274fc3">
      <msh:sideLink params="id" action="/entityParentPrepareCreate-trans_blood" name="Переливание крови" title="Добавить переливание крови" guid="dc488234-9da8-4290-9e71-3b4558d27ec7" />
      <msh:sideLink roles="/Policy/Mis/MedCase/Transfusion/Other/Create" key="ALT+3" params="id" action="/entityParentPrepareCreate-trans_other" name="Переливание транс.сред, кроме крови" title="Добавить переливание кроме трансфузионных сред всех, кроме крови" guid="42b3d7fc-e998-458f-b259-0c865d5270b8" />
    </msh:sideMenu>
  </tiles:put>
</tiles:insert>

