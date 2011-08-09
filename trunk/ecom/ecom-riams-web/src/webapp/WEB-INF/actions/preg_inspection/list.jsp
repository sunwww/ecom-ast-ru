<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://struts.apache.org/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://www.nuzmsh.ru/tags/msh" prefix="msh" %>
<%@ taglib uri="http://www.ecom-ast.ru/tags/ecom" prefix="ecom" %>

<tiles:insert page="/WEB-INF/tiles/mainLayout.jsp" flush="true" >

  <tiles:put name="title" type="string">
    <ecom:titleTrail beginForm="mis_medCaseForm" mainMenu="Patient" title="Список осмотров" guid="c951c449-0ed2-489d-9163-da1263effaa3" />
  </tiles:put>
  <tiles:put name="body" type="string">
    <msh:table name="list" action="entitySubclassView-preg_inspection.do" idField="id" guid="be9cacbc-17e8-4a04-8d57-bd2cbbaeba30">
      <msh:tableColumn columnName="Дата" property="inspectionDate" guid="c4b30e10-9ca0-42b1-94fb-88cf0f7afa2e" />
      <msh:tableColumn columnName="Время" identificator="false" property="inspectionTime" guid="6c4ac335-9f67-4e6b-a333-116b2a0856a3" />
      <msh:tableColumn columnName="Тип осмотра" identificator="false" property="typeInformation" guid="7cc9a6de-3b09-4191-a8af-7c7d1efcfd90" />
      <msh:tableColumn columnName="Описание" identificator="false" property="information" guid="f10e1b3b-80ea-437a-b3aa-878dc87f110f" />
      <msh:tableColumn columnName="Специалист" identificator="false" property="workFunctionInfo" guid="a34edf2e-52a8-4cbf-b15b-86b7e4c1a1bb" />
    </msh:table>
  </tiles:put>
  <tiles:put name="side" type="string">
    <msh:sideMenu title="Добавить" guid="b33faf64-b72e-4845-bf32-5fda8e274fc3">
      <msh:sideLink params="id" action="/entityParentPrepareCreate-preg_pregInspection" name="Осмотр беременной" title="Добавить осмотр беременной" roles="/Policy/Mis/Inspection/Pregnancy/Create" guid="dc488234-9da8-4290-9e71-3b4558d27ec7" />
      <msh:sideLink params="id" action="/entityParentPrepareCreate-preg_confInspection" name="Осмотр родильницы" title="Добавить осмотр родильницы" roles="/Policy/Mis/Inspection/Confined/Create" guid="42b3d7fc-e998-458f-b259-0c865d5270b8" />
      <msh:sideLink params="id" action="/entityParentPrepareCreate-preg_apgarEstimation" name="Оценка по Апгар" title="Добавить оценку по Апгар" roles="/Policy/Mis/Inspection/ApgarEstimation/Create" guid="c383a3a3-64f7-41a8-8441-6d1864cdd6be" />
      <msh:sideLink params="id" action="/entityParentPrepareCreate-preg_downesEstimation" name="Оценка по Downes" title="Добавить оценку по Downes" roles="/Policy/Mis/Inspection/DownesEstimation/Create" guid="eb51e73b-7997-468e-985d-c922c1eb20c6" />
      <msh:sideLink params="id" action="/entityParentPrepareCreate-preg_newBornInfRiskEstimation" name="Оценка риска бак. инфекции" title="Добавить оценку риска бак. инфекции" roles="/Policy/Mis/Inspection/NewBornInfRiskEstimation/Create" guid="c9f5998f-c049-411c-946c-2696c3db73a9" />
      <msh:sideLink params="id" action="/entityParentPrepareCreate-preg_audiologicScreening" name="Аудиологический скрининг" title="Добавить аудиологический скрининг" roles="/Policy/Mis/Inspection/AudiologicScreening/Create" guid="de3b9f2b-5eb4-4780-a16e-2651333cd085" />
      <msh:sideLink params="id" action="/entityParentPrepareCreate-preg_hereditaryScreening" name="Скрининг на наследственные заболевания" title="Добавить скрининг на наследственные заболевания" roles="/Policy/Mis/Inspection/HereditaryScreening/Create" guid="98783be7-464c-4ef0-a477-6da8442190c3" />
    </msh:sideMenu>
    <msh:sideMenu title="Перейти" guid="241de050-e15f-4b4d-bc49-3c311ecd7c75">
      <msh:sideLink key="ALT+5" roles="/Policy/Mis/Patient/View" params="patient" action="/entityView-mis_patient" name="Пациент" guid="ac6f1e63-a6e4-4338-8fc3-d3b6c417b7f4" />
    </msh:sideMenu>
  </tiles:put>
</tiles:insert>

