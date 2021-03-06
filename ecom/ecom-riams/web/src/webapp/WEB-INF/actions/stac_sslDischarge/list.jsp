<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://struts.apache.org/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://www.nuzmsh.ru/tags/msh" prefix="msh" %>
<%@ taglib uri="http://www.ecom-ast.ru/tags/ecom" prefix="ecom" %>

<tiles:insert page="/WEB-INF/tiles/mainLayout.jsp" flush="true" >

  <tiles:put name="title" type="string">
    <ecom:titleTrail beginForm="mis_patientForm" mainMenu="Patient" title="Список всех CЛС" guid="7c2f862e-0345-431a-a391-39b33538ad1b" />
  </tiles:put>
  <tiles:put name="side" type="string" />
  <tiles:put name="body" type="string">
    <msh:table name="list" action="entityView-stac_sslClose.do" idField="id" guid="d579127c-69a0-4eca-b3e3-950381d1585c">
      <msh:tableColumn columnName="Номер" property="id" guid="ce16c32c-9459-4673-9ce8-d6e646f969ff" />
      <msh:tableColumn columnName="Дата открытия" property="dateStart" guid="fc26523a-eb9c-44bc-b12e-42cb7ca9ac5b" />
      <msh:tableColumn columnName="Кем открыт" property="startWorkerText" guid="35347247-b552-4154-a82a-ee484a1714ad" />
      <msh:tableColumn columnName="Дата закрытия" property="dateFinish" guid="d2eebfd0-f043-4230-8d24-7ab99f0d5b45" />
      <msh:tableColumn columnName="Кем закрыт" property="finishWorkerText" guid="6b562107-5017-4559-9b94-ab525b579202" />
      <msh:tableColumn columnName="Владелец" property="ownerText" guid="e98f73b5-8b9e-4a3e-966f-4d43576bbc96" />
      <msh:tableColumn columnName="Количество дней" property="daysCount" guid="8b496fc7-80e9-4beb-878b-5bfb20e98f31" />
    </msh:table>
  </tiles:put>
  <tiles:put name="side" type="string">
    <msh:sideMenu title="Добавить" guid="6372e109-9e1b-49dc-840b-9b38f524efeb">
      <msh:sideLink params="id" action="/entityParentPrepareCreate-stac_ssl" name="Новый ССЛ" title="Добавить случай стационарного лечения" guid="436bbb7b-497c-4b10-ba0e-c5601675a713" />
    </msh:sideMenu>
    <msh:sideMenu title="Перейти" guid="b43f7427-60be-4539-8b79-38a6882a8512">
      <msh:sideLink key="ALT+2" params="id" action="/entityView-mis_patient" name="Пациент" guid="f07e71b2-bfbe-4137-8bba-b347b8056561" />
    </msh:sideMenu>
  </tiles:put>
</tiles:insert>

