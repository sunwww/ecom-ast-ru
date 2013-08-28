<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://struts.apache.org/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://www.nuzmsh.ru/tags/msh" prefix="msh" %>
<%@ taglib uri="http://www.ecom-ast.ru/tags/ecom" prefix="ecom" %>
<tiles:insert page="/WEB-INF/tiles/mainLayout.jsp" flush="true" >
	<tiles:put name='title' type='string'>
		<ecom:titleTrail mainMenu="Lpu" beginForm="asset_networkPortForm" title="Список Сетевой порт"/>
	</tiles:put>
	<tiles:put name='side' type='string'>
		<msh:sideMenu title="Добавить">
			<msh:sideLink key='ALT+N' roles="ROLE/Create" params="" action="/entityPrepareCreate-asset_networkPort" title="Сетевой порт" name="Сетевой порт" />
		</msh:sideMenu>
	</tiles:put>
	<tiles:put name='body' type='string' >
		<msh:table name="list" action="entityView-asset_networkPort.do" idField="id">
			<msh:tableColumn columnName="#" property="sn" />
			<msh:tableColumn columnName="ИД" property="id" />
			<msh:tableColumn columnName="Оборудование" property="equipment" />
			<msh:tableColumn columnName="Номер сетевого порта" property="portNumber" />
			<msh:tableColumn columnName="Номер VLAN" property="vlanNumber" />
			<msh:tableColumn columnName="Номер транка" property="trankNumber" />
			<msh:tableColumn columnName="MAC адрес" property="macAddress" />
			<msh:tableColumn columnName="Скорость передачи в Мб\с" property="speed" />
			<msh:tableColumn columnName="Оптический" property="optical" />
		</msh:table>
	</tiles:put>
</tiles:insert>
