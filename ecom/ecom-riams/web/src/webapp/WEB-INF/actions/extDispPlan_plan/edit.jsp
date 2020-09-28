<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://struts.apache.org/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://www.nuzmsh.ru/tags/msh" prefix="msh" %>
<%@ taglib uri="http://www.ecom-ast.ru/tags/ecom" prefix="ecom" %>
<%@ taglib tagdir="/WEB-INF/tags" prefix="tags" %>
<tiles:insert page="/WEB-INF/tiles/main${param.short}Layout.jsp" flush="true">
	<tiles:put name="title" type="string">
		<ecom:titleTrail mainMenu="Patient" beginForm="extDispPlan_planForm" />
	</tiles:put>

	<tiles:put name="body" type="string">
		<msh:form action="/entitySaveGoView-extDispPlan_plan.do" defaultField="year">
			<msh:hidden property="id" /> 
			<msh:hidden property="saveType" />
			<msh:panel >
				<msh:row>
					<msh:textField property="year" label="Год"  />
				</msh:row>
				<msh:submitCancelButtonsRow colSpan="4" />
			</msh:panel>
		</msh:form>
		<msh:ifFormTypeIsView formName="extDispPlan_planForm">
			<script>window.document.location="js-extDispPlan_plan-view.do?id=${param.id}";</script>
		</msh:ifFormTypeIsView>
	</tiles:put>

	<tiles:put name="side" type="string">
		<msh:ifFormTypeAreViewOrEdit formName="extDispPlan_planForm">
			<msh:sideMenu>
				<msh:sideLink key="ALT+2" params="id" action="/entityParentEdit-extDispPlan_plan" name="Изменить" title="Изменить" roles="/Policy/Mis/ExtDisp/Card/Edit"/>
				<msh:sideLink confirm="Удалить доп.диспансеризацию?" key="ALT+DEL" params="id" action="/entityParentDeleteGoParentView-extDispPlan_plan" name="Удалить" title="Удалить" roles="/Policy/Mis/ExtDisp/Card/Delete"/>

			</msh:sideMenu>
			<msh:sideMenu title="Добавить" >
			
			<msh:ifFormTypeIsView formName="extDispPlan_planForm">
			   <msh:sideLink key="ALT+M" params="id" action="entityPrepareCreate-extDispPlan_record.do" name="Добавить пациента в план" title="Добавить пациента в план" roles="/Policy/Mis/ExtDisp/Card/Edit"/>
			</msh:ifFormTypeIsView>
			</msh:sideMenu>
		</msh:ifFormTypeAreViewOrEdit>
	</tiles:put>
</tiles:insert>
