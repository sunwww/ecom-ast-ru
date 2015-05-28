<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://struts.apache.org/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://www.nuzmsh.ru/tags/msh" prefix="msh" %>
<%@ taglib uri="http://www.ecom-ast.ru/tags/ecom" prefix="ecom" %>
<%@ taglib tagdir="/WEB-INF/tags" prefix="tags" %>
<tiles:insert page="/WEB-INF/tiles/main${param.short}Layout.jsp" flush="true">
	<tiles:put name="body" type="string">
		<msh:form action="/entitySaveGoView-extDisp_voc.do" defaultField="code">
			<msh:hidden property="id" />
			<msh:hidden property="saveType" />
			<msh:panel>
				<msh:row>
					<msh:textField property="code" label="Код"/>
				</msh:row>
				<msh:row>
					<msh:textField property="name" label="Наименование" fieldColSpan="3" size="100" horizontalFill="true"/>
				</msh:row>
				<msh:row>
					<msh:checkBox property="isComission" label="Медосмотр"/>
				</msh:row>
				<msh:row>
					<msh:checkBox property="attachmentPopulation" label="Оказывается только прикрепленному населению"/>
				</msh:row>
			<msh:submitCancelButtonsRow colSpan="4" />
			</msh:panel>
		</msh:form>
		<msh:ifFormTypeIsView formName="extDisp_vocForm">
			<msh:section title="План по доп.диспансеризации" createRoles="/Policy/Mis/ExtDisp/Card/Voc/Plan/Create" createUrl="entityParentPrepareCreate-extDisp_vocPlan.do?id=${param.id}"
			shortList="entityParentList-extDisp_vocPlan.do?id=${param.id}&short=Short" viewRoles="/Policy/Mis/ExtDisp/Card/Voc/Plan/View" >
			<ecom:webQuery name="extDispPlan" nativeSql="select edp.id,edp.dispType_id 
			from ExtDispPlan edp where edp.dispType_id=${param.id}" />
				<msh:table editUrl="js-extDisp_service-editPlan.do"
				 
				name="extDispPlan" action="entityParentView-extDisp_vocPlan.do" idField="1">
					<msh:tableColumn columnName="Ид" property="1"/>
				</msh:table>
			</msh:section>
			<msh:section title="Возрастные группы" createRoles="/Policy/Mis/ExtDisp/Card/Voc/AgeGroup/Create" createUrl="entityParentPrepareCreate-extDisp_vocAgeGroup.do?id=${param.id}"
			shortList="entityParentList-extDisp_vocAgeGroup.do?id=${param.id}&short=Short" viewRoles="/Policy/Mis/ExtDisp/Card/Voc/AgeGroup/View">
			<ecom:webQuery name="vocExtDispAgeGroup" nativeSql="select vedag.id,vedag.code,vedag.name, case when (vedag.isArchival is null or vedag.isArchival='0') then 'Нет' else 'Да' end from VocExtDispAgeGroup vedag where vedag.dispType_id=${param.id}"/>
				<msh:table name="vocExtDispAgeGroup" action="entityParentView-extDisp_vocAgeGroup.do" idField="1">
					<msh:tableColumn columnName="Код" property="2"/>
					<msh:tableColumn columnName="Наименование" property="3"/>
					<msh:tableColumn columnName="В архиве" property="4"/>
				</msh:table>
			</msh:section>
			<msh:section title="Возрастные периоды для отчета" createRoles="/Policy/Mis/ExtDisp/Card/Voc/AgeGroup/Create" createUrl="entityParentPrepareCreate-extDisp_vocAgeReportGroup.do?id=${param.id}"
			shortList="entityParentList-extDisp_vocAgeReportGroup.do?id=${param.id}&short=Short" viewRoles="/Policy/Mis/ExtDisp/Card/Voc/AgeGroup/View">
			<ecom:webQuery name="vocExtDispAgeReportGroup" nativeSql="select vedag.id,vedag.code,vedag.name from VocExtDispAgeReportGroup vedag where vedag.dispType_id=${param.id}"/>
				<msh:table name="vocExtDispAgeReportGroup" action="entityParentView-extDisp_vocAgeReportGroup.do" idField="1">
					<msh:tableColumn columnName="Код" property="2"/>
					<msh:tableColumn columnName="Наименование" property="3"/>
				</msh:table>
			</msh:section>
			<msh:section title="Группы здоровья" createRoles="/Policy/Mis/ExtDisp/Card/Voc/HealthGroup/Create" createUrl="entityParentPrepareCreate-extDisp_vocHealthGroup.do?id=${param.id}"
			shortList="entityParentList-extDisp_vocHealthGroup.do?id=${param.id}&short=Short" viewRoles="/Policy/Mis/ExtDisp/Card/Voc/HealthGroup/View">
			<ecom:webQuery name="vocExtDispHealthGroup" nativeSql="select vedhg.id,vedhg.code,vedhg.name from VocExtDispHealthGroup vedhg where vedhg.dispType_id=${param.id}"/>
				<msh:table name="vocExtDispHealthGroup" action="entityParentView-extDisp_vocHealthGroup.do" idField="1">
					<msh:tableColumn columnName="Код" property="2"/>
					<msh:tableColumn columnName="Наименование" property="3"/>
				</msh:table>
			</msh:section>
		</msh:ifFormTypeIsView>
	</tiles:put>
	<tiles:put name="title" type="string">
		<ecom:titleTrail mainMenu="Voc" beginForm="extDisp_vocForm" />
	</tiles:put>
	<tiles:put name="side" type="string">
		<msh:ifFormTypeAreViewOrEdit formName="extDisp_vocForm">
			<msh:sideMenu>
				<msh:sideLink key="ALT+2" params="id" action="/entityEdit-extDisp_voc" name="Изменить" title="Изменить" roles="/Policy/Mis/ExtDisp/Card/Voc/Edit"/>
				<msh:sideLink key="ALT+DEL" params="id" action="/entityDelete-extDisp_voc" name="Удалить" title="Удалить" roles="/Policy/Mis/ExtDisp/Card/Voc/Delete"/>
			</msh:sideMenu>
			<msh:sideMenu title="Добавить" >
				<msh:sideLink params="id" action="/entityParentPrepareCreate-extDisp_vocAgeGroup" name="Возрастную группу" title="Добавить возрастную группу" roles="/Policy/Mis/ExtDisp/Card/Voc/AgeGroup/Create"/>
				<msh:sideLink params="id" action="/entityParentPrepareCreate-extDisp_vocHealthGroup" name="Группу здоровья" title="Добавить группу здоровья" roles="/Policy/Mis/ExtDisp/Card/Voc/HealthGroup/Create"/>
				<msh:sideLink params="id" action="/entityParentPrepareCreate-extDisp_vocPlan" name="План по доп. диспасеризации" title="Добавить план по доп. диспансеризации" roles="/Policy/Mis/ExtDisp/Card/Voc/Plan/Create"/>
			</msh:sideMenu>
		</msh:ifFormTypeAreViewOrEdit>
      <tags:voc_menu currentAction="extDisp"/>
	</tiles:put>
</tiles:insert>
