<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://struts.apache.org/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://www.nuzmsh.ru/tags/msh" prefix="msh" %>
<%@ taglib uri="http://www.ecom-ast.ru/tags/ecom" prefix="ecom" %>
<tiles:insert page="/WEB-INF/tiles/mainLayout.jsp" flush="true" >
	<tiles:put name='title' type='string'>
		<msh:title mainMenu="Lpu" title="Список копировального оборудования"></msh:title>
	</tiles:put>
	<tiles:put name='side' type='string'>
		<msh:sideMenu title="Добавить">
			<msh:sideLink key='ALT+N' roles="/Policy/Mis/Asset/PermanentAsset/AutomatedWorkplace/Equipment/CopyingEquipment/Create" action="/entityPrepareCreate-mis_copyingEquipment" title="Копировальное оборудование" name="Копировальное оборудование" />
		</msh:sideMenu>
	</tiles:put>
	<tiles:put name='body' type='string' >
	<ecom:webQuery name="list" />
		<ecom:webQuery name="child" nativeSql="select id,name,ipaddress,isTxtFile from copyingEquipment where parent_id is null"/>
		<msh:table name="list" action="entityView-mis_copyingEquipment.do" idField="id">
			<msh:tableColumn columnName="#" property="sn" />
			<msh:tableColumn columnName="1" property="id" />
			<msh:tableColumn columnName="Наименование" property="2" />
			<msh:tableColumn columnName="IP адрес" property="3" />
			<msh:tableColumn property="4" columnName="Приоритет текстового файла"/>
		</msh:table>
	</tiles:put>
</tiles:insert>