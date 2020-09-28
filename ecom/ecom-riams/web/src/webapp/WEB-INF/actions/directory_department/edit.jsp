<%@page import="ru.ecom.web.util.ActionUtil"%>
<%@page import="ru.ecom.web.login.LoginInfo"%>
<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@ taglib uri="http://struts.apache.org/tags-tiles" prefix="tiles"%>
<%@ taglib uri="http://www.nuzmsh.ru/tags/msh" prefix="msh"%>
<%@ taglib tagdir="/WEB-INF/tags" prefix="tags"%>
<%@ taglib uri="http://www.ecom-ast.ru/tags/ecom" prefix="ecom"%>

<tiles:insert page="/WEB-INF/tiles/main${param.short}Layout.jsp"
	flush="true">
	
		<tiles:put name="side" type="string">
		<msh:ifFormTypeAreViewOrEdit formName="directory_departmentForm">
			<msh:sideMenu title="Управление">
				<msh:sideLink key="ALT+2" params="id"
					action="/entityPrepareCreate-directory_department" name="Добавить"
					roles="/Policy/Mis/Directory/Department" />
				<msh:sideLink key="ALT+DEL" confirm="Удалить?"
					params="id"
					action="/entityParentDeleteGoParentView-directory_department"
					name="Удалить" roles="/Policy/Mis/Directory/Department" />
			</msh:sideMenu>
		</msh:ifFormTypeAreViewOrEdit>
	</tiles:put>
	
	<tiles:put name="body" type="string">
	 	<msh:form action="/entitySaveGoView-directory_department.do"
			defaultField="name">
			<msh:hidden property="id" />
			<msh:hidden property="saveType" />
			<msh:hidden property="building" />
			<msh:hidden property="buildingLevel" />
			<msh:hidden property="department" />

			<msh:panel>
				<msh:row>
					<msh:autoComplete vocName="vocBuildingShort" property="building"
						label="Выбор корпуса" fieldColSpan="4" size="60" />
				</msh:row>
				<msh:row>
					<msh:autoComplete vocName="vocBuildingLevelShort"
						property="buildingLevel" label="Выбор этажа" fieldColSpan="4"
						size="60" />
				</msh:row>
				<msh:row>
					<msh:autoComplete vocName="vocDepartmet" property="department"
						label="Выбор отделения" fieldColSpan="4" size="60" />
				</msh:row>
			</msh:panel>
			<msh:submitCancelButtonsRow colSpan="1000" />
		</msh:form>
		<ecom:webQuery name="list" nativeSql="select d.id,vb.name,vbl.name, m.name as dep from department d
left join mislpu m on m.id = d.department_id
left join vocbuildinglevel vbl on vbl.id = d.buildinglevel_id
left join vocbuilding vb on vb.id = d.building_id" />
      
	<msh:table name="list" action="entityEdit-directory_department.do" idField="1">
	<msh:tableColumn columnName="#" property="sn"/>
	<msh:tableColumn columnName="Корпус" property="2"/>
	<msh:tableColumn columnName="Этаж" property="3"/>
	<msh:tableColumn columnName="Отделение" property="4"/>
	</msh:table>
	</tiles:put>
</tiles:insert>