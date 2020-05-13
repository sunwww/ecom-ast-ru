<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://struts.apache.org/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://www.nuzmsh.ru/tags/msh" prefix="msh" %>
<%@ taglib uri="http://www.ecom-ast.ru/tags/ecom" prefix="ecom" %>

<tiles:insert page="/WEB-INF/tiles/mainLayout.jsp" flush="true" >

  <tiles:put name="title" type="string">
    <ecom:titleTrail beginForm="mis_patientForm" mainMenu="Patient" title="Список всех случаев лечения в стационаре" />
  </tiles:put>
  <tiles:put name="side" type="string" />
  <tiles:put name="body" type="string">
    <msh:table name="list" action="entityView-stac_ssl.do" idField="id">
      <msh:tableColumn columnName="Номер" property="id" />
      <msh:tableColumn columnName="Дата открытия" property="dateStart" />
      <msh:tableColumn columnName="Кем открыт" property="startWorkerText" />
      <msh:tableColumn columnName="Дата закрытия" property="dateFinish" />
      <msh:tableColumn columnName="Кем закрыт" property="finishWorkerText" />
      <msh:tableColumn columnName="Владелец" property="ownerText" />
      <msh:tableColumn columnName="Количество дней" property="daysCount" />
    </msh:table>
  </tiles:put>
  <tiles:put name="side" type="string">
    <msh:sideMenu title="Добавить">
      <msh:sideLink params="id" action="/entityParentPrepareCreate-stac_ssl" name="Новый случай лечения в стационаре" title="Добавить случай стационарного лечения" />
    </msh:sideMenu>
    <msh:sideMenu title="Перейти">
      <msh:sideLink key="ALT+2" params="id" action="/entityView-mis_patient" name="Пациент" />
    </msh:sideMenu>
  </tiles:put>
</tiles:insert>

