<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://struts.apache.org/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://www.nuzmsh.ru/tags/msh" prefix="msh" %>
<%@ taglib uri="http://www.ecom-ast.ru/tags/ecom" prefix="ecom" %>
<tiles:insert page="/WEB-INF/tiles/mainLayout.jsp" flush="true" >
	<tiles:put name='title' type='string'>
		<ecom:titleTrail mainMenu="Lpu" beginForm="mis_lpuForm" title="Список копировального оборудования"/>
	</tiles:put>
	<tiles:put name='side' type='string'>
		<msh:sideMenu title="Добавить">
			<msh:sideLink key='ALT+N' roles="/Policy/Mis/Asset/PermanentAsset/AutomatedWorkplace/Equipment/CopyingEquipment/Create" params="id" action="/entityParentPrepareCreate-mis_copyingEquipment" title="Копировальное оборудование" name="Копировальное оборудование" />
		</msh:sideMenu>
	</tiles:put>
	<tiles:put name='body' type='string' >
		<msh:table name="list" action="entityView-mis_copyingEquipment.do" idField="id">
			<msh:tableColumn columnName="#" property="sn" />
			<msh:tableColumn columnName="ИД" property="id" />
			<msh:tableColumn columnName="Наименование" property="name" />
			<msh:tableColumn columnName="IP адрес" property="ipaddress" />
		</msh:table>
	</tiles:put>
</tiles:insert>