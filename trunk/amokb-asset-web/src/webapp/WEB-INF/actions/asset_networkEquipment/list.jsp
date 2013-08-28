<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://struts.apache.org/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://www.nuzmsh.ru/tags/msh" prefix="msh" %>
<%@ taglib uri="http://www.ecom-ast.ru/tags/ecom" prefix="ecom" %>
<tiles:insert page="/WEB-INF/tiles/mainLayout.jsp" flush="true" >
	<tiles:put name='title' type='string'>
		<ecom:titleTrail mainMenu="Lpu" beginForm="asset_networkEquipmentForm" title="Список Сетевое оборудование"/>
	</tiles:put>
	<tiles:put name='side' type='string'>
		<msh:sideMenu title="Добавить">
			<msh:sideLink key='ALT+N' roles="ROLE/Create" params="" action="/entityPrepareCreate-asset_networkEquipment" title="Сетевое оборудование" name="Сетевое оборудование" />
		</msh:sideMenu>
	</tiles:put>
	<tiles:put name='body' type='string' >
		<msh:table name="list" action="entityView-asset_networkEquipment.do" idField="id">
			<msh:tableColumn columnName="#" property="sn" />
			<msh:tableColumn columnName="ИД" property="id" />
			<msh:tableColumn columnName="Порты" property="ports" />
			<msh:tableColumn columnName="Активное оборудование" property="active" />
			<msh:tableColumn columnName="IP адрес" property="ipaddress" />
			<msh:tableColumn columnName="Количество портов" property="portNumber" />
			<msh:tableColumn columnName="Пароль локального администратора" property="localAdminPassword" />
		</msh:table>
	</tiles:put>
</tiles:insert>
