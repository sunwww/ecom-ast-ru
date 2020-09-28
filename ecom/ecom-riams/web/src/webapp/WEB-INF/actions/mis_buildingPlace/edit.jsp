<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://struts.apache.org/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://www.nuzmsh.ru/tags/msh" prefix="msh" %>
<%@ taglib uri="http://www.ecom-ast.ru/tags/ecom" prefix="ecom" %>
<%@ taglib uri="/WEB-INF/mis.tld" prefix="mis" %>
<%@ taglib tagdir="/WEB-INF/tags" prefix="tags" %>

<tiles:insert page="/WEB-INF/tiles/mainLayout.jsp" flush="true">

  <tiles:put name="body" type="string">
    <msh:form action="entityParentSaveGoView-mis_buildingPlace.do" defaultField="name">
      <msh:hidden property="id" />
      <msh:hidden property="saveType" />
      <msh:hidden property="parent"/>
      <msh:panel>
        <msh:row>
          <msh:textField property="name" label="Название" fieldColSpan="3" size="50" />
        </msh:row>
        <msh:row>
          <msh:textArea property="comment" label="Комментарий" fieldColSpan="3" horizontalFill="true"/>
        </msh:row>
        <msh:submitCancelButtonsRow colSpan="2" />
      </msh:panel>
    </msh:form>
    <msh:ifFormTypeIsView formName="mis_buildingPlaceForm">
      <msh:ifInRole roles="/Policy/Mis/WorkPlace/FloorBuilding/View">
    	<msh:section title="Этажи здания" createRoles="/Policy/Mis/WorkPlace/FloorBuilding/Create" 
    		createUrl="entityParentPrepareCreate-mis_floorBuilding.do?id=${param.id}" >
    		<ecom:webQuery name="floorBuilding" nativeSql="select wp.id,wp.name from WorkPlace wp where wp.parent_id='${param.id}' and wp.dtype='FloorBuilding' order by wp.name"/>
    		<msh:table idField="1" name="floorBuilding" hideTitle="true" action="entityParentView-mis_floorBuilding.do">
    			<msh:tableColumn property="2" columnName="Этаж"/>
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
      <msh:ifFormTypeAreViewOrEdit formName="mis_buildingPlaceForm">
        <msh:sideLink key="ALT+DEL" params="id" roles="/Policy/Mis/WorkPlace/BuildingPlace/Delete" action="/entityParentDelete-mis_buildingPlace" name="Удалить" confirm="Удалить здание?" />
      </msh:ifFormTypeAreViewOrEdit>
    </msh:sideMenu>
    <msh:ifFormTypeIsView formName="mis_buildingPlaceForm">
      <msh:sideMenu title="Добавить">
        <msh:sideLink key="ALT+3" params="id" roles="/Policy/Mis/WorkPlace/FloorBuilding/Create" action="/entityParentPrepareCreate-mis_floorBuilding" name="Этаж здания" title="Добавить этаж здания" />
      </msh:sideMenu>

    </msh:ifFormTypeIsView>
  </tiles:put>
  <tiles:put name="title" type="string">
    <ecom:titleTrail mainMenu="Lpu" beginForm="mis_buildingPlaceForm" />
  </tiles:put>

</tiles:insert>

