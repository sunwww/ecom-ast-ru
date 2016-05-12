<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://struts.apache.org/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://www.nuzmsh.ru/tags/msh" prefix="msh" %>
<%@ taglib uri="http://www.ecom-ast.ru/tags/ecom" prefix="ecom" %>
<%@ taglib uri="/WEB-INF/mis.tld" prefix="mis" %>
<%@ taglib tagdir="/WEB-INF/tags" prefix="tags" %>

<tiles:insert page="/WEB-INF/tiles/mainLayout.jsp" flush="true">

  <tiles:put name="body" type="string">
    <msh:form action="entityParentSaveGoParentView-mis_consultingRoom.do" defaultField="name" guid="17f720e4-3690-4ae5-961b-6d69348757b6">
      <msh:hidden property="id" guid="df46ed12-bbf3-4f90-9046-dcf1b595541c" />
      <msh:hidden property="saveType" guid="12fee02b-f848-4039-b3f6-35cbf5f3a629" />
      <msh:hidden property="parent"/>
      <msh:panel>
        <msh:row >
          <msh:textField property="name" label="Название" fieldColSpan="3" size="50" />
        </msh:row>
        <ecom:oneToManyOneAutocomplete label="Специалисты"
         	vocName="workFunctionByDirect" property="workFunctions" colSpan="4"/>
        <msh:row>
          <msh:textArea property="comment" label="Комментарий" fieldColSpan="3" horizontalFill="true"/>
        </msh:row>
        <msh:submitCancelButtonsRow colSpan="2"/>
      </msh:panel>
    </msh:form>
  </tiles:put>
  <tiles:put name="side" type="string">
    <msh:sideMenu title="Кабинет">
      <msh:ifFormTypeIsView formName="mis_consultingRoomForm">
        <msh:sideLink key="ALT+2" roles="/Policy/Mis/WorkPlace/ConsultingRoom/Edit" params="id" action="/entityEdit-mis_consultingRoom" name="Изменить" />
      </msh:ifFormTypeIsView>
      <msh:ifFormTypeAreViewOrEdit formName="mis_consultingRoomForm" guid="de889210-1aba-4447-96ab-a729de7a2c8a">
        <msh:sideLink key="ALT+DEL" params="id" roles="/Policy/Mis/WorkPlace/ConsultingRoom/Delete" action="/entityParentDeleteGoParentView-mis_consultingRoom" name="Удалить" confirm="Удалить кабинет?" />
      </msh:ifFormTypeAreViewOrEdit>
    </msh:sideMenu>
    <msh:sideMenu title="Дополнительно">
      <msh:sideLink key="ALT+6" action="/entityParentList-mis_buildingPlace.do?id=-1" name="Список зданий" />
    </msh:sideMenu>
  </tiles:put>
  <tiles:put name="title" type="string">
    <ecom:titleTrail mainMenu="Lpu" beginForm="mis_consultingRoomForm" />
  </tiles:put>

</tiles:insert>

