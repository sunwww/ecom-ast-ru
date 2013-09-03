<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://struts.apache.org/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://www.nuzmsh.ru/tags/msh" prefix="msh" %>
<%@ taglib uri="http://www.ecom-ast.ru/tags/ecom" prefix="ecom" %>
<%@ taglib tagdir="/WEB-INF/tags" prefix="tags" %>
<tiles:insert page="/WEB-INF/tiles/main${param.short}Layout.jsp" flush="true">
	<tiles:put name="body" type="string">
		<msh:form action="/entityParentSaveGoView-extDisp_vocPlan.do" defaultField="lpuName">
			<msh:hidden property="id" />
			<msh:hidden property="saveType" />
			<msh:hidden property="dispType" />
			<msh:panel>
			<msh:submitCancelButtonsRow colSpan="4" />
			</msh:panel>
		</msh:form>
		<msh:ifFormTypeIsView formName="extDisp_vocPlanForm">
			<msh:section title="Услуги">
			<ecom:webQuery name="services" nativeSql="
				select edps.id,vs.name as vsname
				,veds.name as vedsname,vedag.name as vedagname
				,edps.isVisit as edpsisVisit
				 from ExtDispPlanService edps
				left join VocSex vs on vs.id=edps.sex_id
				left join VocExtDispService veds on veds.id=edps.serviceType_id
				left join VocExtDispAgeGroup vedag on vedag.id=edps.ageGroup_id
					where edps.plan_id=${param.id}
			"/>
				<msh:table name="services" action="entityParentView-extDisp_vocPlanService.do" idField="1">
					<msh:tableColumn columnName="#" property="sn"/>
					<msh:tableColumn columnName="Пол" property="2"/>
					<msh:tableColumn columnName="Услуга" property="3"/>
					<msh:tableColumn columnName="Возрастная группа" property="4"/>
					<msh:tableColumn columnName="Посещение" property="5"/>
				</msh:table>
			</msh:section>
		</msh:ifFormTypeIsView>
	</tiles:put>
	<tiles:put name="title" type="string">
		<ecom:titleTrail mainMenu="Voc" beginForm="extDisp_vocPlanForm" />
	</tiles:put>
	<tiles:put name="side" type="string">
		<msh:ifFormTypeAreViewOrEdit formName="extDisp_vocPlanForm">
			<msh:sideMenu>
				<msh:sideLink key="ALT+2" params="id" action="/entityParentEdit-extDisp_vocPlan" name="Изменить" title="Изменить" roles=""/>
				<msh:sideLink key="ALT+DEL" params="id" action="/entityParentDelete-extDisp_vocPlan" name="Удалить" title="Удалить" roles=""/>
			</msh:sideMenu>
			<msh:sideMenu title="Добавить" >
				<msh:sideLink key="ALT+N" params="id" 
					action="/entityParentPrepareCreate-extDisp_vocPlanService" 
					name="Услугу" title="Добавить услугу" roles=""/>
			</msh:sideMenu>
		</msh:ifFormTypeAreViewOrEdit>
		<tags:voc_menu currentAction="extDisp"/>
		
	</tiles:put>
</tiles:insert>
