<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://struts.apache.org/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://www.nuzmsh.ru/tags/msh" prefix="msh" %>
<%@ taglib uri="http://www.ecom-ast.ru/tags/ecom" prefix="ecom" %>
<%@ taglib tagdir="/WEB-INF/tags" prefix="tags" %>
<tiles:insert page="/WEB-INF/tiles/mainLayout.jsp" flush="true">
	<tiles:put name="body" type="string">
		<msh:form action="/entitySaveGoView-asset_asset.do" defaultField="">
			<msh:hidden property="id" />
			<msh:hidden property="saveType" />
			<msh:hidden property="parent" />
			<msh:panel>
				<msh:row>
					<msh:autoComplete property="lpu" label="ЛПУ" vocName="misLpu" horizontalFill="true" />
				</msh:row>
				<msh:row>
					<msh:autoComplete property="producer" label="Производитель" vocName="vocAssetProducer" horizontalFill="true" />
				</msh:row>
				<msh:row>
					<msh:textField property="discardingDate" label="Дата списания"/>
				</msh:row>
				<msh:row>
					<msh:textField property="entranceDate" label="Дата поступления"/>
				</msh:row>
				<msh:row>
					<msh:autoComplete property="responsiblePerson" label="Ответственное лицо" vocName="vocAssetResponsiblePerson" horizontalFill="true" />
				</msh:row>
				<msh:row>
					<msh:textField property="comment" label="Комментарии"/>
				</msh:row>
				<msh:row>
					<msh:textField property="name" label="Название"/>
				</msh:row>
			<msh:submitCancelButtonsRow colSpan="4" />
			</msh:panel>
		</msh:form>
		<msh:ifFormTypeIsView formName="asset_assetForm">
			<msh:section title="Рабочие карточки">
			<ecom:parentEntityListAll formName="asset_assetForm" attribute="workcards" />
				<msh:table name="workcards" action="entityParentView-asset_assetWorkcard.do" idField="id">
					<msh:tableColumn columnName="id" property="id"/>
				</msh:table>
			</msh:section>
		</msh:ifFormTypeIsView>
	</tiles:put>
	<tiles:put name="title" type="string">
		<ecom:titleTrail mainMenu="Patient" beginForm="asset_assetForm" />
	</tiles:put>
	<tiles:put name="side" type="string">
		<msh:ifFormTypeAreViewOrEdit formName="asset_assetForm">
			<msh:sideMenu>
				<msh:sideLink key="ALT+2" params="id" action="/entityParentEdit-asset_asset" name="Изменить" title="Изменить" roles=""/>
				<msh:sideLink key="ALT+DEL" params="id" action="/entityParentDelete-asset_asset" name="Удалить" title="Удалить" roles=""/>
			</msh:sideMenu>
			<msh:sideMenu title="Добавить" >
				<msh:sideLink key="ALT+N" params="id" action="/entityParentPrepareCreate-asset_assetWorkcard" name="Рабочие карточки" title="Рабочие карточки" roles=""/>
			</msh:sideMenu>
		</msh:ifFormTypeAreViewOrEdit>
	</tiles:put>
</tiles:insert>
