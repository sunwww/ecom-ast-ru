<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://struts.apache.org/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://www.nuzmsh.ru/tags/msh" prefix="msh" %>
<%@ taglib uri="http://www.ecom-ast.ru/tags/ecom" prefix="ecom" %>
<%@ taglib tagdir="/WEB-INF/tags" prefix="tags" %>

<tiles:insert page="/WEB-INF/tiles/main${param.short}Layout.jsp" flush="true">
	<tiles:put name="body" type="string">
		<msh:form action="/entitySaveGoView-contract_priceList.do" defaultField="name">
			<msh:hidden property="id" />
			<msh:hidden property="saveType" />
			<msh:panel>
				<msh:row>
					<msh:textField property="name" label="Название" fieldColSpan="3" horizontalFill="true"/>
				</msh:row>
				<msh:row>
					<msh:autoComplete property="vocPrice" label="Справочник типов цен" vocName="vocPrice" horizontalFill="true" fieldColSpan="3"/>
				</msh:row>
				<msh:row>
					<msh:textField property="dateFrom" label="Дата начала действия"/>
					<msh:textField property="dateTo" label="Дата окончания"/>
				</msh:row>
			<msh:submitCancelButtonsRow colSpan="4" />
			</msh:panel>
		</msh:form>
		<msh:ifFormTypeIsView formName="contract_priceListForm">
			<msh:section title="Группы позиций" createRoles="/Policy/Mis/Contract/PriceList/PriceGroup/Create" 
				createUrl="entityPrepareCreate-contract_priceGroup.do?priceList=${param.id}">
			<ecom:webQuery name="priceGroup" nativeSql="
							select pg.id,pg.code,pg.name from PricePosition pg 
							where pg.priceList_id = '${param.id}' and pg.dtype='PriceGroup' and pg.parent_id is null
							order by pg.code
							" />
				<msh:table name="priceGroup" action="entityParentView-contract_priceGroup.do" idField="1">
					<msh:tableColumn columnName="#" property="sn"/>
					<msh:tableColumn columnName="Код" property="2"/>
					<msh:tableColumn columnName="Наименование" property="3"/>
				</msh:table>
			</msh:section>
		</msh:ifFormTypeIsView>
	</tiles:put>
	<tiles:put name="title" type="string">
		<ecom:titleTrail mainMenu="Contract" beginForm="contract_priceListForm" />
	</tiles:put>
	<tiles:put name="side" type="string">
		<msh:sideMenu>
			<msh:sideLink key="ALT+2" params="id" action="/entityEdit-contract_priceList" name="Изменить" title="Изменить" roles="/Policy/Mis/Contract/PriceList/Edit"/>
			<msh:sideLink key="ALT+DEL" params="id" action="/entityDelete-contract_priceList" name="Удалить" title="Удалить" roles="/Policy/Mis/Contract/PriceList/Delete"/>
		</msh:sideMenu>
		<msh:sideMenu title="Добавить" >
			<msh:sideLink key="ALT+N" action="/entityPrepareCreate-contract_priceGroup.do?priceList=${param.id}" name="Группа позиций" title="Группа позиций" roles="/Policy/Mis/Contract/PriceList/PriceGroup/Create"/>
		</msh:sideMenu>
		<msh:sideMenu title="Печать">
			<msh:sideLink action="/print-contract_priceList.do?s=CertificatePersonPrintService&m=printPriceList" params="id" 
			name="Прейскуранта" title="Печать прейскуранта" roles="/Policy/Mis/Contract/PriceList/View"/>
		</msh:sideMenu>
		<tags:contractMenu currentAction="price"/>
	</tiles:put>
</tiles:insert>
