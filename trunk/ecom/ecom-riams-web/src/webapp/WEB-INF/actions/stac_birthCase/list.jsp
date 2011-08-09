<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://struts.apache.org/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://www.nuzmsh.ru/tags/msh" prefix="msh" %>
<%@ taglib uri="http://www.ecom-ast.ru/tags/ecom" prefix="ecom" %>

<tiles:insert page="/WEB-INF/tiles/mainLayout.jsp" flush="true" >

  <tiles:put name="title" type="string">
    <ecom:titleTrail beginForm="stac_sslForm" mainMenu="Patient" title="Случай рождения"  />
  </tiles:put>
  <tiles:put name="body" type="string">
    <msh:table name="list" action="entityParentView-stac_birthCase.do" idField="id" >
      <msh:tableColumn columnName="Номер" property="id"  />
      <msh:tableColumn columnName="Дата родов" property="childbirthDate"  />
      <msh:tableColumn columnName="Время родов" property="childbirthTime"  />
    </msh:table>
  </tiles:put>
  <tiles:put name="side" type="string">
    <msh:sideMenu title="Добавить" >
      <msh:sideLink params="id" action="/entityParentPrepareCreate-stac_birthCase" name="Новый случай рождения" title="Добавить случай рождения" />
    </msh:sideMenu>
    <msh:sideMenu title="Перейти" >
      <msh:sideLink key="ALT+2" roles="/Policy/Mis/Patient/View" params="patient" action="/entityView-mis_patient" name="Пациент" />
    </msh:sideMenu>
  </tiles:put>
</tiles:insert>

