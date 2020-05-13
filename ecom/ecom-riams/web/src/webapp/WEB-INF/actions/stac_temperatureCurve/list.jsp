<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://struts.apache.org/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://www.nuzmsh.ru/tags/msh" prefix="msh" %>
<%@ taglib uri="http://www.ecom-ast.ru/tags/ecom" prefix="ecom" %>

<tiles:insert page="/WEB-INF/tiles/mainLayout.jsp" flush="true" >

  <tiles:put name="title" type="string">
    <ecom:titleTrail beginForm="mis_medCaseForm" mainMenu="Patient" title="Температурные листы" />
  </tiles:put>
  <tiles:put name="side" type="string">
  <msh:sideMenu title="Добавить">
      <msh:sideLink params="id" action="/entityParentPrepareCreate-stac_temperatureCurve" name="Новые показатели температурного листа" title="Добавить новые показатели температурного листа" roles="/Policy/Mis/MedCase/Stac/Ssl/TemperatureCurve/Create" />
    </msh:sideMenu>
 <msh:sideMenu  title="Перейти">
      <msh:sideLink params="id" action="/js-stac_temperatureCurve-graph" name="График температурного листа" title="Показать график температурного листа"/>
 </msh:sideMenu>
     </tiles:put>
  <tiles:put name="body" type="string">
    <msh:table name="list" action="entityParentView-stac_temperatureCurve.do" idField="id">
      <msh:tableColumn columnName="Дата" property="takingDate" />
      <msh:tableColumn columnName="Время суток" property="dayTimeText" />
      <msh:tableColumn columnName="Температура" property="degree" />
      <msh:tableColumn columnName="АД" property="bloodPressureInfo" />
      <msh:tableColumn columnName="Пульс" property="pulse" />
      <msh:tableColumn columnName="ЧДД" property="respirationRate" />
      <msh:tableColumn columnName="Вес" property="weight" />
    </msh:table>
  </tiles:put>
  
</tiles:insert>

