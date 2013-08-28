<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://struts.apache.org/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://www.nuzmsh.ru/tags/msh" prefix="msh" %>
<%@ taglib uri="http://www.ecom-ast.ru/tags/ecom" prefix="ecom" %>
<tiles:insert page="/WEB-INF/tiles/mainLayout.jsp" flush="true" >
	<tiles:put name='title' type='string'>
		<ecom:titleTrail mainMenu="Lpu" beginForm="asset_networkPointLinkForm" title="Список Соединение с сетевой точкой"/>
	</tiles:put>
	<tiles:put name='side' type='string'>
		<msh:sideMenu title="Добавить">
			<msh:sideLink key='ALT+N' roles="ROLE/Create" params="" action="/entityPrepareCreate-asset_networkPointLink" title="Соединение с сетевой точкой" name="Соединение с сетевой точкой" />
		</msh:sideMenu>
	</tiles:put>
	<tiles:put name='body' type='string' >
		<msh:table name="list" action="entityView-asset_networkPointLink.do" idField="id">
			<msh:tableColumn columnName="#" property="sn" />
			<msh:tableColumn columnName="ИД" property="id" />
			<msh:tableColumn columnName="Сетевая точка" property="networkPoint" />
			<msh:tableColumn columnName="Оборудование" property="equipment" />
			<msh:tableColumn columnName="Длина соединения в метрах" property="linkLength" />
			<msh:tableColumn columnName="Фабричное" property="factory" />
		</msh:table>
	</tiles:put>
</tiles:insert>
