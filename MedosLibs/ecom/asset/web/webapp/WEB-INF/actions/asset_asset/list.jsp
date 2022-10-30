<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://struts.apache.org/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://www.nuzmsh.ru/tags/msh" prefix="msh" %>
<%@ taglib uri="http://www.ecom-ast.ru/tags/ecom" prefix="ecom" %>
<tiles:insert page="/WEB-INF/tiles/mainLayout.jsp" flush="true" >
	<tiles:put name='title' type='string'>
		<ecom:titleTrail mainMenu="Lpu" beginForm="asset_assetForm" title="Список Актив"/>
	</tiles:put>
	<tiles:put name='side' type='string'>
		<msh:sideMenu title="Добавить">
			<msh:sideLink key='ALT+N' roles="ROLE/Create" params="" action="/entityPrepareCreate-asset_asset" title="Актив" name="Актив" />
		</msh:sideMenu>
	</tiles:put>
	<tiles:put name='body' type='string' >
		<msh:table name="list" action="entityView-asset_asset.do" idField="id">
			<msh:tableColumn columnName="#" property="sn" />
			<msh:tableColumn columnName="ИД" property="id" />
			<msh:tableColumn columnName="ЛПУ" property="lpu" />
			<msh:tableColumn columnName="Производитель" property="producer" />
			<msh:tableColumn columnName="Дата списания" property="discardingDate" />
			<msh:tableColumn columnName="Дата поступления" property="entranceDate" />
			<msh:tableColumn columnName="Ответственное лицо" property="responsiblePerson" />
			<msh:tableColumn columnName="Рабочие карточки" property="workcards" />
			<msh:tableColumn columnName="Комментарии" property="comment" />
			<msh:tableColumn columnName="Название" property="name" />
		</msh:table>
	</tiles:put>
</tiles:insert>
