<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://struts.apache.org/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://www.nuzmsh.ru/tags/msh" prefix="msh" %>
<%@ taglib uri="http://www.ecom-ast.ru/tags/ecom" prefix="ecom" %>

<tiles:insert page="/WEB-INF/tiles/mainLayout.jsp" flush="true" >

  <tiles:put name="title" type="string">
    <ecom:titleTrail beginForm="stac_sslForm" mainMenu="Patient" title="Список всех CЛО"/>
  </tiles:put>
  <tiles:put name="side" type="string" />
  <tiles:put name="body" type="string">
    <msh:table name="list" action="entityView-stac_sso.do" idField="id">
      <msh:tableColumn columnName="Номер" property="id"/>
      <msh:tableColumn columnName="Код отделения" property="dateStart"/>
      <msh:tableColumn columnName="Профиль коек" property="startWorkerText"/>
      <msh:tableColumn columnName="Код врача" property="dateFinish"/>
      <msh:tableColumn columnName="Дата поступления" property="finishWorkerText"/>
      <msh:tableColumn columnName="Дата выписки, перевода" property="ownerText"/>
      <msh:tableColumn columnName="Код диагноза по МКБ" property="daysCount"/>
    </msh:table>
  </tiles:put>
  <tiles:put name="side" type="string">
    <msh:sideMenu title="Добавить">
      <msh:sideLink params="id" action="/entityParentPrepareCreate-stac_slo" name="Новый ССО" title="Добавить случай стационарного лечения в отделении"/>
    </msh:sideMenu>
    <msh:sideMenu title="Перейти">
      <msh:sideLink key="ALT+2" params="patient" action="/entityView-mis_patient" name="Пациент"/>
    </msh:sideMenu>
  </tiles:put>
</tiles:insert>

