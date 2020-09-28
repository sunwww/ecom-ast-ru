<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://struts.apache.org/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://www.nuzmsh.ru/tags/msh" prefix="msh" %>
<%@ taglib uri="http://www.ecom-ast.ru/tags/ecom" prefix="ecom" %>
<%@ taglib uri="/WEB-INF/mis.tld" prefix="mis" %>
<%@ taglib tagdir="/WEB-INF/tags" prefix="tags" %>

<tiles:insert page="/WEB-INF/tiles/mainLayout.jsp" flush="true">

  <tiles:put name="body" type="string">
    <msh:form action="entityParentSaveGoParentView-mis_floorBuilding.do" defaultField="phoneNumber">
      <msh:hidden property="id"/>
      <msh:hidden property="saveType"/>
      <msh:hidden property="parent"/>
      <msh:panel>
        <msh:row>
          <msh:textField property="phoneNumber" label="Номер" fieldColSpan="3" size="50" horizontalFill="true" />
        </msh:row>
        <msh:row>
        	<msh:autoComplete property="phoneType" fieldColSpan="3" horizontalFill="true" vocName="vocPhoneType"/>
        </msh:row>
        <msh:row>
        	<msh:textField property="isPrimary" label="Основной"/>
        </msh:row>
        <msh:row>
          <msh:textArea property="comment" label="Комментарий" fieldColSpan="3" horizontalFill="true"/>
        </msh:row>
        <msh:submitCancelButtonsRow colSpan="2" />
      </msh:panel>
    </msh:form>
    <msh:ifFormTypeIsView formName="mis_floorBuildingForm">
    <table style='width:100%'>
    	<tr>
			<msh:ifInRole roles="/Policy/Mis/WorkPlace/FloorBuilding/View">
    		<td width="15%" valign="top" style="padding-right: 1em">
		    	<msh:section title="Кабинеты" createRoles="/Policy/Mis/WorkPlace/ConsultingRoom/Create" 
		    		createUrl="entityParentPrepareCreate-mis_consultingRoom.do?id=${param.id}" >
		    		<ecom:webQuery name="consultingRoom" nativeSql="
		    		select wp.id,wp.name,list(coalesce(wf.groupname,vwf.name||' '||wpat.lastname)) 
		    		from WorkPlace wp
		    		left join WorkPlace_WorkFunction wpf on wpf.workPlace_id=wp.id
		    		left join WorkFunction wf on wf.id=wpf.workFunctions_id
		    		left join VocWorkFunction vwf on vwf.id=wf.workFunction_id
		    		left join Worker w on w.id=wf.worker_id
		    		left join Patient wpat on wpat.id=w.person_id 
		    		where wp.parent_id='${param.id}' and wp.dtype='ConsultingRoom'
		    		group by wp.id,wp.name
		    		order by wp.name"/>
		    		<msh:table idField="1" name="consultingRoom" action="entityParentView-mis_consultingRoom.do">
		    			<msh:tableColumn property="2" columnName="Кабинет"/>
		    			<msh:tableColumn property="3" columnName="Рабочие функции"/>
		    		</msh:table>
		    	</msh:section>
      		</td>
	        </msh:ifInRole>
  			<msh:ifInRole roles="/Policy/Mis/WorkPlace/HospitalRoom/View">
    		<td width="70%" valign="top">
		    	<msh:section title="Палаты" createRoles="/Policy/Mis/WorkPlace/HospitalRoom/Create" 
		    		createUrl="entityParentPrepareCreate-mis_hospitalRoom.do?id=${param.id}" >
		    		<ecom:webQuery  name="hospitalRoom" nativeSql="select wp.id
		    		,wp.name as wpname,ml.name as mlname, wp.bedCapacity as wpbedCapacity
		    		from WorkPlace wp 
		    		left join MisLpu ml on ml.id=wp.lpu_id
		    		where wp.parent_id='${param.id}' and wp.dtype='HospitalRoom'
		    		order by ml.name,wp.name"/>
		    		<msh:table idField="1" name="hospitalRoom"  action="entityParentView-mis_hospitalRoom.do">
		    			<msh:tableColumn property="3" columnName="Отделение"/>
		    			<msh:tableColumn property="2" columnName="Палата"/>
		    			<msh:tableColumn property="4" columnName="Кол-во коек"/>
		    		</msh:table>
		    	</msh:section>
		    </td>
			</msh:ifInRole>
    		<msh:ifInRole roles="/Policy/Mis/WorkPlace/OperatingRoom/View">
    		<td width="15%" valign="top" style="padding-right: 1em">
    			<msh:section>
    				<msh:sectionTitle>Список операционных. <a href='entityParentPrepareCreate-mis_operatingRoom.do?id=${param.id}'>Добавить</a></msh:sectionTitle>
    				<msh:sectionContent>
    					<ecom:webQuery name="operatingRooms" nativeSql="select wp.id,wp.name from WorkPlace wp where wp.parent_id='${param.id}' and wp.dtype='OperatingRoom'"/>
    					<msh:table  name="operatingRooms" hideTitle="true" action="entityParentView-mis_operatingRoom.do" idField="1">
    						<msh:tableColumn property="2"/>
    					</msh:table>
    				</msh:sectionContent>
    			</msh:section>
    		</td>
  			</msh:ifInRole>
    	</tr>
    </table>

    </msh:ifFormTypeIsView>
  </tiles:put>
  <tiles:put name="side" type="string">
    <msh:sideMenu title="Этаж здания">
      <msh:ifFormTypeIsView formName="mis_floorBuildingForm">
        <msh:sideLink key="ALT+2" roles="/Policy/Mis/WorkPlace/FloorBuilding/Edit" params="id" action="/entityEdit-mis_floorBuilding" name="Изменить" />
      </msh:ifFormTypeIsView>
      <msh:ifFormTypeAreViewOrEdit formName="mis_floorBuildingForm">
        <msh:sideLink key="ALT+DEL" params="id" roles="/Policy/Mis/WorkPlace/FloorBuilding/Delete" action="/entityParentDeleteGoParentView-mis_floorBuilding" name="Удалить" confirm="Удалить этаж здания?" />
      </msh:ifFormTypeAreViewOrEdit>
    </msh:sideMenu>
    <msh:ifFormTypeIsView formName="mis_floorBuildingForm" >
      <msh:sideMenu title="Добавить">
        <msh:sideLink key="ALT+3" params="id" roles="/Policy/Mis/WorkPlace/ConsultingRoom/Create" action="/entityParentPrepareCreate-mis_consultingRoom" name="Кабинет" title="Добавить кабинет" />
        <msh:sideLink key="ALT+4" params="id" roles="/Policy/Mis/WorkPlace/HospitalRoom/Create" action="/entityParentPrepareCreate-mis_hospitalRoom" name="Палату" title="Добавить палату" />
        <msh:sideLink key="ALT+5" params="id" roles="/Policy/Mis/WorkPlace/OperatingRoom/Create" action="/entityParentPrepareCreate-mis_operatingRoom" name="Операционную" title="Добавить операционную" />
      </msh:sideMenu>
    </msh:ifFormTypeIsView>
    <msh:sideMenu title="Дополнительно">
      <msh:sideLink key="ALT+6" action="/entityParentList-mis_buildingPlace.do?id=-1" name="Список зданий" />
    </msh:sideMenu>
  </tiles:put>
  <tiles:put name="title" type="string">
    <ecom:titleTrail mainMenu="Lpu" beginForm="mis_floorBuildingForm" />
  </tiles:put>

</tiles:insert>