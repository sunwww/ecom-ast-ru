<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://struts.apache.org/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://www.nuzmsh.ru/tags/msh" prefix="msh" %>
<%@ taglib uri="http://www.ecom-ast.ru/tags/ecom" prefix="ecom" %>

<tiles:insert page="/WEB-INF/tiles/mainLayout.jsp" flush="true" >

  <tiles:put name="title" type="string">
    <ecom:titleTrail beginForm="mis_medCaseForm" mainMenu="Patient" title="Список осмотров" />
  </tiles:put>
  <tiles:put name="body" type="string">
    <msh:table name="list" action="entitySubclassView-preg_inspection.do" idField="id">
      <msh:tableColumn columnName="Дата" property="inspectionDate" />
      <msh:tableColumn columnName="Время" identificator="false" property="inspectionTime" />
      <msh:tableColumn columnName="Тип осмотра" identificator="false" property="typeInformation" />
      <msh:tableColumn columnName="Описание" identificator="false" property="information" />
      <msh:tableColumn columnName="Специалист" identificator="false" property="workFunctionInfo" />
    </msh:table>
  </tiles:put>
  <tiles:put name="side" type="string">
    <msh:sideMenu title="Добавить">
      <msh:sideLink params="id" action="/entityParentPrepareCreate-preg_pregInspection" name="Осмотр беременной" title="Добавить осмотр беременной" roles="/Policy/Mis/Inspection/Pregnancy/Create" />
      <msh:sideLink params="id" action="/entityParentPrepareCreate-preg_confInspection" name="Осмотр родильницы" title="Добавить осмотр родильницы" roles="/Policy/Mis/Inspection/Confined/Create" />
      <msh:sideLink params="id" action="/entityParentPrepareCreate-preg_apgarEstimation" name="Оценка по Апгар" title="Добавить оценку по Апгар" roles="/Policy/Mis/Inspection/ApgarEstimation/Create" />
      <msh:sideLink params="id" action="/entityParentPrepareCreate-preg_downesEstimation" name="Оценка по Downes" title="Добавить оценку по Downes" roles="/Policy/Mis/Inspection/DownesEstimation/Create" />
      <msh:sideLink params="id" action="/entityParentPrepareCreate-preg_newBornInfRiskEstimation" name="Оценка риска бак. инфекции" title="Добавить оценку риска бак. инфекции" roles="/Policy/Mis/Inspection/NewBornInfRiskEstimation/Create" />
      <msh:sideLink params="id" action="/entityParentPrepareCreate-preg_audiologicScreening" name="Аудиологический скрининг" title="Добавить аудиологический скрининг" roles="/Policy/Mis/Inspection/AudiologicScreening/Create" />
      <msh:sideLink params="id" action="/entityParentPrepareCreate-preg_hereditaryScreening" name="Скрининг на наследственные заболевания" title="Добавить скрининг на наследственные заболевания" roles="/Policy/Mis/Inspection/HereditaryScreening/Create" />
    </msh:sideMenu>
    <msh:sideMenu title="Перейти">
      <msh:sideLink key="ALT+5" roles="/Policy/Mis/Patient/View" params="patient" action="/entityView-mis_patient" name="Пациент" />
    </msh:sideMenu>
  </tiles:put>
</tiles:insert>

