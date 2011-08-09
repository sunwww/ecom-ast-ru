<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@ taglib uri="http://struts.apache.org/tags-tiles" prefix="tiles"%>
<%@ taglib uri="http://www.nuzmsh.ru/tags/msh" prefix="msh"%>
<%@ taglib uri="http://www.ecom-ast.ru/tags/ecom" prefix="ecom"%>
<%@ include file="/WEB-INF/tiles/header.jsp" %>

<tiles:insert page="/WEB-INF/tiles/mainLayout.jsp" flush="true">

  <tiles:put name="body" type="string">
    <msh:form guid="123" action="entityParentSaveGoView-mis_operatingRoom.do" defaultField="name">
      <msh:hidden property="id" guid="67476bdd-7eb2-4eac-8512-9476a6e2d5ad" />
      <msh:hidden property="saveType" guid="0a37e6e5-4875-4dae-a226-50fba9990881" />
      <msh:hidden property="lpu" guid="e38246ea-4a88-40fb-8f4d-3986e8856332" />
      <msh:panel guid="04fd7a8f-37bc-4492-996b-5778911d56cc">
        <msh:row guid="1e6e2aa0-b434-4025-b877-58993d9b320d">
        	<msh:textField property="name" label="Наименование" size="100"/>
        </msh:row>
        <msh:submitCancelButtonsRow colSpan="4" guid="9279e49d-2ee0-426b-9024-b06947a1f0a8" />
      </msh:panel>
    </msh:form>
  </tiles:put>
  <tiles:put name="side" type="string">
    <msh:sideMenu title="Операционная" guid="8709feb9-c102-4a0f-8bb5-3404cf624927">
      <msh:ifFormTypeIsView formName="mis_operatingRoomForm" guid="e4a7c0d3-c936-4947-a202-2c14949a8567">
        <msh:sideLink key="ALT+2" params="id" action="/entityParentEdit-mis_operatingRoom" name="Изменить" guid="175cf029-eaae-47a2-8ad7-0486fcbea707" roles="/Policy/Mis/MisLpu/OperatingRoom/Edit" />
        <msh:sideLink key="ALT+DEL" params="id" action="/entityParentDelete-mis_opertingRoom" name="Удалить" confirm="Удалить сотрудника?" guid="bed2b91a-84fc-4a8b-9cb1-0a83d94fd2de" roles="/Policy/Mis/MisLpu/OperatingRoom/Delete" />
      </msh:ifFormTypeIsView>
    </msh:sideMenu>
    <msh:ifFormTypeIsView formName="mis_operatingRoomForm" guid="b0b5c1a9-5459-43b9-9030-ba0177a24cbd">
      <msh:sideMenu title="Добавить" guid="652cf5dc-1acf-4fb5-b064-7eb3912e6531">
              <msh:sideLink params="id" action="/entityParentPrepareCreate-cal_workCalendarPatternByOperRoom" name="Шаблон операций" title="Шаблон операций" guid="6b2a67-8d42-4281-baaf-a288f426" roles="/Policy/Mis/Worker/WorkBook/Create" />
      </msh:sideMenu>
    </msh:ifFormTypeIsView>
  </tiles:put>
  <tiles:put name="title" type="string">
    <ecom:titleTrail mainMenu="Lpu" beginForm="mis_operatingRoomForm" guid="049dd53d-f3b2-495a-bfec-be41a32d7a27" />
  </tiles:put>
</tiles:insert>
