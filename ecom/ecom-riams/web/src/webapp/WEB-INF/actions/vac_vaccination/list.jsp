<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://struts.apache.org/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://www.nuzmsh.ru/tags/msh" prefix="msh" %>
<%@ taglib uri="http://www.ecom-ast.ru/tags/ecom" prefix="ecom" %>

<tiles:insert page="/WEB-INF/tiles/mainLayout.jsp" flush="true" >

  <tiles:put name="title" type="string">
    <ecom:titleTrail beginForm="mis_medCaseForm" mainMenu="Patient" title="Вакцинации" />
  </tiles:put>
  <tiles:put name="side" type="string">
    <msh:sideMenu>
      <msh:sideLink roles="/Policy/Mis/Vaccination/Create" key="ALT+N" action="/entityParentPrepareCreate-vac_vaccination" name="Создать новое" params="id" />
    </msh:sideMenu>
  </tiles:put>
  <tiles:put name="body" type="string">
    <msh:table name="list" action="entityParentView-vac_vaccination.do" idField="id">
      <msh:tableColumn columnName="Дата" property="executeDate" />
      <msh:tableColumn columnName="Время" property="executeTime" />
      <msh:tableColumn columnName="Вакцина" property="materialText" />
      <msh:tableColumn columnName="Серия" property="series" />
      <msh:tableColumn columnName="Номер" property="controlNumber" />
      <msh:tableColumn columnName="Доза" property="dose" />
      <msh:tableColumn columnName="Метод" property="methodText" />
      <msh:tableColumn columnName="Фаза" property="phaseText" />
      <msh:tableColumn columnName="Годен до" property="expirationDate" />
      <msh:tableColumn columnName="Разрешил" property="permitWorkerInfo" />
      <msh:tableColumn columnName="Исполнитель" property="executeWorkerInfo" />
    </msh:table>
  </tiles:put>
</tiles:insert>

