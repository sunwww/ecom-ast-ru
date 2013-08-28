<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://struts.apache.org/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://www.nuzmsh.ru/tags/msh" prefix="msh" %>
<%@ taglib uri="http://www.ecom-ast.ru/tags/ecom" prefix="ecom" %>
<tiles:insert page="/WEB-INF/tiles/mainLayout.jsp" flush="true" >
	<tiles:put name='title' type='string'>
		<ecom:titleTrail mainMenu="Lpu" beginForm="asset_assetWorkcardForm" title="Список Рабочая карточка актива"/>
	</tiles:put>
	<tiles:put name='side' type='string'>
		<msh:sideMenu title="Добавить">
			<msh:sideLink key='ALT+N' roles="/Policy/Mis/Asset/AssetWorkcard/Create" params="id" action="/entityParentPrepareCreate-asset_assetWorkcard" title="Рабочая карточка актива" name="Рабочая карточка актива" />
		</msh:sideMenu>
	</tiles:put>
	<tiles:put name='body' type='string' >
		<msh:table name="list" action="entityView-asset_assetWorkcard.do" idField="id">
			<msh:tableColumn columnName="#" property="sn" />
			<msh:tableColumn columnName="ИД" property="id" />
			<msh:tableColumn columnName="Работа" property="assetWork" />
			<msh:tableColumn columnName="Дата работы" property="workDate" />
			<msh:tableColumn columnName="Исполнитель" property="worker" />
			<msh:tableColumn columnName="Заказчик" property="customer" />
			<msh:tableColumn columnName="Время работы в часах" property="workTime" />
			<msh:tableColumn columnName="Нарушение опечатывания" property="sealingBreak" />
			<msh:tableColumn columnName="Проведено опечатывание" property="sealing" />
			<msh:tableColumn columnName="Актив" property="asset" />
		</msh:table>
	</tiles:put>
</tiles:insert>
