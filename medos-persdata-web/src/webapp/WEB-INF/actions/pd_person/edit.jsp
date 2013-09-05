<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://struts.apache.org/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://www.nuzmsh.ru/tags/msh" prefix="msh" %>
<%@ taglib uri="http://www.ecom-ast.ru/tags/ecom" prefix="ecom" %>
<%@ taglib tagdir="/WEB-INF/tags" prefix="tags" %>
<tiles:insert page="/WEB-INF/tiles/main${param.short}Layout.jsp" flush="true">
	<tiles:put name="body" type="string">
		<msh:form action="/entitySaveGoView-pd_person.do" defaultField="lastname">
			<msh:hidden property="id" />
			<msh:hidden property="saveType" />
			<msh:panel>
				<msh:row>
					<msh:textField property="lastname" label="Фамилия"/>
					<msh:textField property="firstname" label="Имя"/>
					<msh:textField property="patronymic" label="Отчетство"/>
				</msh:row>
				<msh:row>
					<msh:textField property="birthday" label="Дата рождения"/>
					<msh:autoComplete property="sex" label="Пол" vocName="vocSex"/>
					<msh:textField property="snils" label="СНИЛС"/>
				</msh:row>
					<msh:autoComplete property="nationality"/>
				<msh:row>
				</msh:row>
					<msh:textField property="email"/>
				<msh:row>
				</msh:row>
				<msh:row>
				</msh:row>
			<msh:submitCancelButtonsRow colSpan="4" />
			</msh:panel>
		</msh:form>
		<msh:ifFormTypeIsView formName="extDisp_cardForm">
			<msh:section title="Услуги">
			<ecom:webQuery name="examQuery" nativeSql="
select eds.card_id as adscard,veds.name as vedsname
,eds.servicedate,eds.isPathology as edsIsPathology
from ExtDispService eds 
left join VocExtDispService veds on veds.id=eds.serviceType_id
where eds.card_id=${param.id}
			"/>
				<msh:table name="examQuery" action="js-extDisp_service-edit.do" idField="1">
					<msh:tableColumn columnName="Услуга" property="2"/>
					<msh:tableColumn columnName="Дата" property="3"/>
					<msh:tableColumn columnName="Выявлена патология" property="4"/>
				</msh:table>
			</msh:section>
		</msh:ifFormTypeIsView>
	</tiles:put>
	<tiles:put name="title" type="string">
		<ecom:titleTrail mainMenu="Patient" beginForm="extDisp_cardForm" />
	</tiles:put>
	<tiles:put name="side" type="string">
		<msh:ifFormTypeAreViewOrEdit formName="extDisp_cardForm">
			<msh:sideMenu>
				<msh:sideLink key="ALT+2" params="id" action="/entityParentEdit-extDisp_card" name="Изменить" title="Изменить" roles=""/>
				<msh:sideLink key="ALT+DEL" params="id" action="/entityParentDelete-extDisp_card" name="Удалить" title="Удалить" roles=""/>
			</msh:sideMenu>
			<msh:sideMenu title="Добавить" >
				<msh:sideLink key="ALT+N" params="id" action="/js-extDisp_service-edit" name="Услуги" title="Услуги" roles=""/>
			</msh:sideMenu>
		</msh:ifFormTypeAreViewOrEdit>
	</tiles:put>
</tiles:insert>
