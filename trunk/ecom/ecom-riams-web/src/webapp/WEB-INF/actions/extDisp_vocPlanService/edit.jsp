<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://struts.apache.org/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://www.nuzmsh.ru/tags/msh" prefix="msh" %>
<%@ taglib uri="http://www.ecom-ast.ru/tags/ecom" prefix="ecom" %>
<%@ taglib tagdir="/WEB-INF/tags" prefix="tags" %>
<tiles:insert page="/WEB-INF/tiles/main${param.short}Layout.jsp" flush="true">
	<tiles:put name="body" type="string">
		<msh:form action="/entityParentSaveGoParentView-extDisp_vocPlanService.do" defaultField="ageGroupName">
			<msh:hidden property="id" />
			<msh:hidden property="saveType" />
			<msh:hidden property="plan" />
			<msh:panel>
				<msh:row>
					<msh:autoComplete property="ageGroup" label="Возрастная группа" vocName="vocExtDispAgeGroup" />
					<msh:autoComplete property="sex" label="Пол" vocName="vocSex" />
				</msh:row>
				<msh:row>
					<msh:autoComplete property="serviceType" label="Услуга" 
						vocName="vocExtDispService" horizontalFill="true" fieldColSpan="3"/>
				</msh:row>
				<msh:row>
					<msh:checkBox property="isVisit" label="Посещение"/>
				</msh:row>
			<msh:submitCancelButtonsRow colSpan="4" />
			</msh:panel>
		</msh:form>
	</tiles:put>
	<tiles:put name="title" type="string">
		<ecom:titleTrail mainMenu="Voc" beginForm="extDisp_vocPlanServiceForm" />
	</tiles:put>
	<tiles:put name="side" type="string">
		<msh:ifFormTypeAreViewOrEdit formName="extDisp_vocPlanServiceForm">
			<msh:sideMenu>
				<msh:sideLink key="ALT+2" params="id" action="/entityParentEdit-extDisp_vocPlanService" name="Изменить" title="Изменить" roles="/Policy/Mis/ExtDisp/Card/Voc/Plan/Service/Edit"/>
				<msh:sideLink key="ALT+DEL" params="id" action="/entityParentDelete-extDisp_vocPlanService" name="Удалить" title="Удалить" roles="/Policy/Mis/ExtDisp/Card/Voc/Plan/Service/Delete"/>
			</msh:sideMenu>
		</msh:ifFormTypeAreViewOrEdit>
        <tags:voc_menu currentAction="medService"/>
	</tiles:put>
	
</tiles:insert>
