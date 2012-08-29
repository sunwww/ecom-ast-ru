<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://struts.apache.org/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://www.nuzmsh.ru/tags/msh" prefix="msh" %>
<%@ taglib uri="http://www.ecom-ast.ru/tags/ecom" prefix="ecom" %>
<%@ taglib uri="/WEB-INF/mis.tld" prefix="mis" %>
<%@ taglib tagdir="/WEB-INF/tags" prefix="tags" %>

<tiles:insert page="/WEB-INF/tiles/mainLayout.jsp" flush="true">

  <tiles:put name="body" type="string">
    <msh:form action="entityParentSaveGoParentView-mis_hospitalRoom.do" defaultField="lpuName">
      <msh:hidden property="id" />
      <msh:hidden property="saveType" />
      <msh:hidden property="parent"/>
      <msh:panel>
        <msh:row>
	        <msh:autoComplete property="lpu" fieldColSpan="3" vocName="lpu" horizontalFill="true"/>
        </msh:row>
        <msh:row>
	        <msh:autoComplete property="roomType" fieldColSpan="3" vocName="vocRoomType" horizontalFill="true" label="Тип коек"/>
        </msh:row>
        <msh:row >
          <msh:textField property="name" label="№палаты"  />
          <msh:textField property="bedCapacity" label="Количество коек"/>
        </msh:row>
        <msh:row>
          <msh:textArea property="comment" label="Комментарий" fieldColSpan="3" horizontalFill="true"/>
        </msh:row>
        
        <msh:submitCancelButtonsRow colSpan="2"/>
      </msh:panel>
    </msh:form>
  </tiles:put>
  <tiles:put name="side" type="string">
    <msh:sideMenu title="Палата">
      <msh:ifFormTypeIsView formName="mis_hospitalRoomForm">
        <msh:sideLink key="ALT+2" roles="/Policy/Mis/WorkPlace/HospitalRoom/Edit" params="id" action="/entityEdit-mis_hospitalRoom" name="Изменить" />
      </msh:ifFormTypeIsView>
      <msh:ifFormTypeAreViewOrEdit formName="mis_hospitalRoomForm" guid="de889210-1aba-4447-96ab-a729de7a2c8a">
        <msh:sideLink key="ALT+DEL" params="id" roles="/Policy/Mis/WorkPlace/HospitalRoom/Delete" action="/entityParentDeleteGoParentView-mis_hospitalRoom" name="Удалить" confirm="Удалить палату?" />
      </msh:ifFormTypeAreViewOrEdit>
    </msh:sideMenu>
    <msh:sideMenu title="Дополнительно">
      <msh:sideLink key="ALT+6" action="/entityParentList-mis_buildingPlace.do?id=-1" name="Список зданий" />
    </msh:sideMenu>
  </tiles:put>
  <tiles:put name="title" type="string">
    <ecom:titleTrail mainMenu="Lpu" beginForm="mis_hospitalRoomForm" />
  </tiles:put>

</tiles:insert>