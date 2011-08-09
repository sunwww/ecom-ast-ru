<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://struts.apache.org/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://www.nuzmsh.ru/tags/msh" prefix="msh" %>
<%@ taglib uri="http://www.ecom-ast.ru/tags/ecom" prefix="ecom" %>

<tiles:insert page="/WEB-INF/tiles/mainLayout.jsp" flush="true" >

  <tiles:put name="title" type="string">
    <ecom:titleTrail beginForm="mis_medCaseForm" mainMenu="Patient" title="Температурные листы" guid="c951cf449-0ed2-489d-9163-fa3" />
  </tiles:put>
  <tiles:put name="side" type="string">
  <msh:sideMenu title="Добавить" guid="b33f4-b72e-4845-bf32-5f5c3">
      <msh:sideLink params="id" action="/entityParentPrepareCreate-stac_temperatureCurve" name="Новые показатели температурного листа" title="Добавить новые показатели температурного листа" roles="/Policy/Mis/MedCase/Stac/Ssl/TemperatureCurve/Create" guid="dc488g-9da8-4290-9e71-3c7" />
    </msh:sideMenu>
 <msh:sideMenu  title="Перейти" guid="b33f4-b72e-4845-bf32-5f65"> 
      <msh:sideLink params="id" action="/js-stac_temperatureCurve-graph" name="График температурного листа" title="Показать график температурного листа" guid="dc488g-9da8-4290-9e71-3c7235"/>
 </msh:sideMenu>
     </tiles:put>
  <tiles:put name="body" type="string">
    <msh:table name="list" action="entityParentView-stac_temperatureCurve.do" idField="id" guid="bfcgbc-17e8-4a04-8d57-bdga30">
      <msh:tableColumn columnName="Дата" property="takingDate" guid="34d6a-2b47-4feb-a3fa-5c1afdf6c41d" />
      <msh:tableColumn columnName="Время суток" property="dayTimeText" guid="bf13ae-74fe-4614-966e-21f8d95e4418" />
      <msh:tableColumn columnName="Температура" property="degree" guid="3cff775aa-e94d-4393-a489-b83b2be02d60" />
      <msh:tableColumn columnName="АД" property="bloodPressureInfo" guid="ede1-d243-47d6-a5c7-997df74eaf73" />
      <msh:tableColumn columnName="Пульс" property="pulse" guid="d96ff9-5653-4920-bb78-1622cbeefa34" />
      <msh:tableColumn columnName="ЧДД" property="respirationRate" guid="dkf29-5653-4920-bb78-168ha34" />
      <msh:tableColumn columnName="Вес" property="weight" guid="dhk29-5653-4920-bb78-168ha34" />
    </msh:table>
  </tiles:put>
  
</tiles:insert>

