<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://struts.apache.org/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://www.nuzmsh.ru/tags/msh" prefix="msh" %>
<%@ taglib uri="http://www.ecom-ast.ru/tags/ecom" prefix="ecom" %>

<tiles:insert page="/WEB-INF/tiles/mainLayout.jsp" flush="true">

  <tiles:put name="body" type="string">
    <!-- 
    	  - Шаблон календаря
    	  -->
    <msh:form guid="formHello" action="/entitySaveGoView-cal_patternByOperRoom.do" defaultField="name">
      <msh:hidden guid="hiddenId" property="id" />
      <msh:hidden guid="hiddenSaveType" property="saveType" />
      <msh:hidden property="operatingRoom"/>
      <msh:panel guid="panel">
        <msh:row guid="b5f456eb-b971-441e-9a90-5194a8019c07">
          <msh:textField property="name" label="Название" guid="6ee66514-7a06-46dc-aabc-3eecb8bd8d00" fieldColSpan="3" horizontalFill="true" />
        </msh:row>
        <msh:row guid="00237e8b-4ca9-40a6-af95-6b44444">
          <msh:autoComplete vocName="vocWorkCalendarType" property="type" guid="3a3e4d1b-8802-467d-b205-715fb379b018" horizontalFill="true" fieldColSpan="3" />
        </msh:row>
        <msh:row guid="00237e8b-4ca9-40a6-af95-6b4e14bb9b74">
          <msh:checkBox property="noActive" label="Недействует" guid="65519dd8-f032-4a80-a311-e8df67b0c5ae" />
        </msh:row>
        <msh:row guid="64713a7e-aa81-41ec-a822-c1accce38fe0" />
        <msh:separator colSpan="4" label="Значения по умолчанию для рабочего дня" guid="220340b1-784e-4203-8a2e-2a8596900f7a" />
        <msh:row guid="48705316-7e6a-4252-b252-b1f5eeb049be">
          <msh:textField property="defaultTimeFrom" label="Время начала" guid="c5aadc99-0e80-4bcf-b268-19361922b052" />
          <msh:textField property="defaultTimeTo" label="Время окончания" guid="ffd05d4c-176b-49d8-9838-d47b71d0d374" />
        </msh:row>
        <msh:row guid="0b82d17c-b1f7-402c-aca5-631ff01fd1f3">
          <msh:textField property="defaultTimeInterval" label="Интервал рабочих времен" guid="0bb1959d-d043-4b31-9913-b85fc1a6b6b0" />
        </msh:row>
        <msh:submitCancelButtonsRow guid="submitCancel" colSpan="4" />
      </msh:panel>
    </msh:form>
  </tiles:put>
  <tiles:put name="title" type="string">
    <ecom:titleTrail guid="titleTrail-123" mainMenu="Template" beginForm="cal_patternByOperRoomForm" />
  </tiles:put>
  <tiles:put name="side" type="string">
   <msh:ifFormTypeIsView formName="cal_patternByOperRoomForm">
    <msh:sideMenu title="Шаблон рабочего календаря" guid="4955f4ae-d3eb-4292-af38-3cb43c9ce6aa">
      <msh:sideLink key="ALT+2" params="id" action="/entityEdit-cal_patternByOperRoom" name="Изменить" roles="/Policy/Mis/Worker/WorkCalendar/Pattern/OperatingRoom/Edit" guid="d77fa1fb-3c90-4ecb-b774-90f12b9b206d" />
      <msh:sideLink key="ALT+DEL" confirm="Удалить?" params="id" action="/entityDelete-cal_patternByOperRoom" name="Удалить" roles="/Policy/Mis/Worker/WorkCalendar/Pattern/OperatingRoom/Delete" guid="e55a56ef-e7d8-4c0a-84bf-f517fdb712cf" />
    </msh:sideMenu>
    <msh:sideMenu title="Добавить" guid="f0db08bf-5df5-4891-a5b8-fa30bf6c1669">
      <msh:sideLink key="ALT+3" params="id" action="/entityParentPrepareCreate-cal_patternByOperRoomDay" name="Рабочий день" roles="/Policy/Mis/Worker/WorkCalendar/Pattern/Day/Create" guid="ee514c09-1849-4e9b-87f1-7d46cc2fd447" />
    </msh:sideMenu>
    </msh:ifFormTypeIsView>
  </tiles:put>
</tiles:insert>

