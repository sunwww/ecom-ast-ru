<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://struts.apache.org/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://www.nuzmsh.ru/tags/msh" prefix="msh" %>
<%@ taglib uri="http://www.ecom-ast.ru/tags/ecom" prefix="ecom" %>
<tiles:insert page="/WEB-INF/tiles/mainLayout.jsp" flush="true" >
	<tiles:put name='title' type='string'>
		<ecom:titleTrail mainMenu="Lpu" beginForm="asset_computerForm" title="Список "/>
	</tiles:put>
	<tiles:put name='side' type='string'>
		<msh:sideMenu title="Добавить">
			<msh:sideLink key='ALT+N' roles="ROLE/Create" params="" action="/entityPrepareCreate-asset_computer" title="" name="" />
		</msh:sideMenu>
	</tiles:put>
	<tiles:put name='body' type='string' >
		<msh:table name="list" action="entityView-asset_computer.do" idField="id">
			<msh:tableColumn columnName="#" property="sn" />
			<msh:tableColumn columnName="ИД" property="id" />
			<msh:tableColumn columnName="Компоненты" property="components" />
			<msh:tableColumn columnName="Доступ в Интернет" property="internetAccess" />
			<msh:tableColumn columnName="Доступ к USB" property="usbAccess" />
			<msh:tableColumn columnName="Пароль на BIOS" property="biosPassword" />
			<msh:tableColumn columnName="Сетевое имя" property="networkName" />
			<msh:tableColumn columnName="" property="" />
			<msh:tableColumn columnName="IP адрес" property="ipaddress" />
			<msh:tableColumn columnName="Опечатывание" property="sealing" />
		</msh:table>
	</tiles:put>
</tiles:insert>
