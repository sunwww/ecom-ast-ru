<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://struts.apache.org/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://www.nuzmsh.ru/tags/msh" prefix="msh" %>
<%@ taglib uri="http://www.ecom-ast.ru/tags/ecom" prefix="ecom" %>
<%@ taglib uri="/WEB-INF/mis.tld" prefix="mis" %>
<%@ taglib tagdir="/WEB-INF/tags" prefix="tags" %>

<tiles:insert page="/WEB-INF/tiles/mainLayout.jsp" flush="true">

  <tiles:put name="body" type="string">
    <msh:form action="entityParentSaveGoView-mis_buildingPlace.do" defaultField="name" guid="17f720e4-3690-4ae5-961b-6d69348757b6">
      <msh:hidden property="id" guid="df46ed12-bbf3-4f90-9046-dcf1b595541c" />
      <msh:hidden property="saveType" guid="12fee02b-f848-4039-b3f6-35cbf5f3a629" />
      <msh:hidden property="parent"/>
      <msh:panel guid="eb62e08a-d34a-4af0-9f13-d23197a33fef">
        <msh:row guid="2df6f0d2-a60d-44a5-b64b-3aeb3f298d04">
          <msh:textField property="name" label="Название" fieldColSpan="3" size="50" />
        </msh:row>
        <msh:row>
          <msh:textArea property="comment" label="Комментарий" fieldColSpan="3" horizontalFill="true"/>
        </msh:row>
        <msh:submitCancelButtonsRow colSpan="2" guid="fe0172d0-16e6-490d-9bf2-ab6ac96e7402" />
      </msh:panel>
    </msh:form>
    <msh:ifFormTypeIsView formName="mis_buildingPlaceForm">
      <msh:ifInRole roles="/Policy/Mis/WorkPlace/FloorBuilding/View">
    	<msh:section title="Этажи здания" createRoles="/Policy/Mis/WorkPlace/FloorBuilding/Create" 
    		createUrl="entityParentPrepareCreate-mis_floorBuilding.do?id=${param.id}" >
    		<ecom:webQuery name="floorBuilding" nativeSql="select wp.id,wp.name from WorkPlace wp where wp.parent_id='${param.id}' and wp.dtype='FloorBuilding'"/>
    		<msh:table idField="1" name="floorBuilding" hideTitle="true" action="entityParentView-mis_floorBuilding.do">
    			<msh:tableColumn property="1" columnName="Этаж"/>
    		</msh:table>
    	</msh:section>
      </msh:ifInRole>
    </msh:ifFormTypeIsView>
  </tiles:put>
  <tiles:put name="side" type="string">
    <msh:sideMenu title="Здание">
      <msh:sideLink key="ALT+1" action="/entityParentList-mis_buildingPlace.do?id=-1" name="Список зданий" />
      <msh:ifFormTypeIsView formName="mis_buildingPlaceForm">
        <msh:sideLink key="ALT+2" roles="/Policy/Mis/WorkPlace/BuildingPlace/Edit" params="id" action="/entityEdit-mis_buildingPlace" name="Изменить" />
      </msh:ifFormTypeIsView>
      <msh:ifFormTypeAreViewOrEdit formName="mis_buildingPlaceForm" guid="de889210-1aba-4447-96ab-a729de7a2c8a">
        <msh:sideLink key="ALT+DEL" params="id" roles="/Policy/Mis/WorkPlace/BuildingPlace/Delete" action="/entityParentDelete-mis_buildingPlace" name="Удалить" confirm="Удалить здание?" />
      </msh:ifFormTypeAreViewOrEdit>
    </msh:sideMenu>
    <msh:ifFormTypeIsView formName="mis_buildingPlaceForm" guid="441c731d-212d-45e8-9964-dde5c8db0a4b">
      <msh:sideMenu title="Добавить" guid="f692ef30-e3cb-4cb7-9f0f-1e0a38e4b08d">
        <msh:sideLink key="ALT+3" params="id" roles="/Policy/Mis/WorkPlace/FloorBuilding/Create" action="/entityParentPrepareCreate-mis_buildingPlace" name="Этаж здания" title="Добавить этаж здания" />
      </msh:sideMenu>

    </msh:ifFormTypeIsView>
  </tiles:put>
  <tiles:put name="title" type="string">
    <ecom:titleTrail mainMenu="Lpu" beginForm="mis_buildingPlaceForm" />
  </tiles:put>

</tiles:insert>

