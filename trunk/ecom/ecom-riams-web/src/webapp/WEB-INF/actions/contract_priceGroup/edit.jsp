<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://struts.apache.org/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://www.nuzmsh.ru/tags/msh" prefix="msh" %>
<%@ taglib uri="http://www.ecom-ast.ru/tags/ecom" prefix="ecom" %>
<%@ taglib tagdir="/WEB-INF/tags" prefix="tags" %>

<tiles:insert page="/WEB-INF/tiles/mainLayout.jsp" flush="true">
	<tiles:put name="body" type="string">
		<msh:form action="/entityParentSaveGoView-contract_priceGroup.do" defaultField="code">
			<msh:hidden property="id" />
			<msh:hidden property="saveType" />
			<msh:hidden property="priceList" />
			<msh:hidden property="parent" />
			<msh:panel>
				<msh:row>
					<msh:textField property="code" label="Код"/>
				</msh:row>
				<msh:row>
					<msh:textField fieldColSpan="3" property="name" label="Название" horizontalFill="true" size="100"/>
				</msh:row>
				<msh:textArea property="comment" label="Комментарий" fieldColSpan="3"/>
			<msh:submitCancelButtonsRow colSpan="4" />
			</msh:panel>
		</msh:form>
		<msh:ifFormTypeIsView formName="contract_priceGroupForm">
			<msh:section title="Группы прейскуранта" createRoles="/Policy/Mis/Contract/PriceList/PriceGroup/Create" 
				createUrl="entityParentPrepareCreate-contract_priceGroup.do?id=${param.id}">
			<ecom:webQuery name="priceGroup" nativeSql="
							select pg.id,pg.code,pg.name from PriceGroup pg 
							where pg.parent_id = '${param.id}' order by pg.code" />
				<msh:table name="priceGroup" action="entityParentView-contract_priceGroup.do" idField="1">
					<msh:tableColumn columnName="#" property="sn"/>
					<msh:tableColumn columnName="Код" property="2"/>
					<msh:tableColumn columnName="Наименование" property="3"/>
				</msh:table>
			</msh:section>
			<msh:section title="Позиции прейскуранта" createRoles="/Policy/Mis/Contract/PriceList/PriceGroup/Create" 
				createUrl="entityParentPrepareCreate-contract_pricePosition.do?id=${param.id}">
			<ecom:webQuery name="pricePosition" nativeSql="
							select pp.id,pp.code,pp.name,pp.cost,pp.dateFrom,pp.dateTo from PricePosition pp 
							where pp.priceGroup_id = '${param.id}' ORDER BY pp.code" />
				<msh:table name="pricePosition" action="entityParentView-contract_pricePosition.do" idField="1">
					<msh:tableColumn columnName="#" property="sn"/>
					<msh:tableColumn columnName="Код" property="2"/>
					<msh:tableColumn columnName="Наименование" property="3"/>
					<msh:tableColumn columnName="Стоимость" property="4"/>
					<msh:tableColumn columnName="Дата начала" property="5"/>
					<msh:tableColumn columnName="Дата окончания" property="6"/>
				</msh:table>
			</msh:section>
		</msh:ifFormTypeIsView>
	</tiles:put>
	<tiles:put name="title" type="string">
		<ecom:titleTrail mainMenu="Contract" beginForm="contract_priceGroupForm" />
	</tiles:put>
	<tiles:put name="side" type="string">
		<msh:sideMenu>
			<msh:sideLink key="ALT+2" params="id" action="/entityParentEdit-contract_priceGroup" name="Изменить" title="Изменить" roles="/Policy/Mis/Contract/PriceList/PriceGroup/Edit"/>
			<msh:sideLink key="ALT+DEL" params="id" action="/entityParentDelete-contract_priceGroup" name="Удалить" title="Удалить" roles="/Policy/Mis/Contract/PriceList/PricePGroup/Delete"/>
		</msh:sideMenu>
		<msh:sideMenu title="Добавить" >
			<msh:sideLink key="ALT+N" params="id" action="/entityParentPrepareCreate-contract_pricePosition" name="Позицию" title="Позицию" roles="/Policy/Mis/Contract/PriceList/PricePosition/Create"/>
		</msh:sideMenu>
		<tags:contractMenu currentAction="price"/>
	</tiles:put>
</tiles:insert>
