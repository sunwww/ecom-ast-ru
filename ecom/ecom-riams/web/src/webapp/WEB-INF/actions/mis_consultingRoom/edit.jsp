<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://struts.apache.org/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://www.nuzmsh.ru/tags/msh" prefix="msh" %>
<%@ taglib uri="http://www.ecom-ast.ru/tags/ecom" prefix="ecom" %>
<%@ taglib uri="/WEB-INF/mis.tld" prefix="mis" %>
<%@ taglib tagdir="/WEB-INF/tags" prefix="tags" %>

<tiles:insert page="/WEB-INF/tiles/mainLayout.jsp" flush="true">

  <tiles:put name="body" type="string">
    <msh:form action="entityParentSaveGoParentView-mis_consultingRoom.do" defaultField="name">
      <msh:hidden property="id" />
      <msh:hidden property="saveType" />
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
      <msh:ifFormTypeAreViewOrEdit formName="mis_consultingRoomForm">
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

