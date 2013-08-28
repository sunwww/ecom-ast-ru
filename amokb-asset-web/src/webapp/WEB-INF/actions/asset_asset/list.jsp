<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://struts.apache.org/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://www.nuzmsh.ru/tags/msh" prefix="msh" %>
<%@ taglib uri="http://www.ecom-ast.ru/tags/ecom" prefix="ecom" %>
<tiles:insert page="/WEB-INF/tiles/mainLayout.jsp" flush="true" >
	<tiles:put name='title' type='string'>
		<ecom:titleTrail mainMenu="Lpu" beginForm="mis_lpuForm" title="Список активов"/>
	</tiles:put>
	<tiles:put name='side' type='string'>
		<msh:sideMenu title="Добавить">
			<msh:sideLink key='ALT+N' roles="/Policy/Mis/Asset/PermanentAsset/Territory/Create" params="id" action="/entityParentPrepareCreate-asset_territory" title="Добавить территория в ЛПУ" name="Территорию" />
			<msh:sideLink key='ALT+N' roles="/Policy/Mis/Asset/PermanentAsset/AutomatedWorkplace/Create" params="id" action="/entityParentPrepareCreate-asset_automatedWorkplace" title="Добавить автоматизированное рабочее место в ЛПУ" name="Автоматизированное рабочее место" />
			<msh:sideLink key='ALT+N' roles="/Policy/Mis/Asset/PermanentAsset/Network/Create" params="id" action="/entityParentPrepareCreate-asset_network" title="Добавить ЛВС в ЛПУ" name="ЛВС" />
			<msh:sideLink key='ALT+N' roles="/Policy/Mis/Asset/PermanentAsset/Cartridge/Create" params="id" action="/entityParentPrepareCreate-asset_cartridge" title="Добавить катридж в ЛПУ" name="Катридж" />
			<msh:sideLink key='ALT+N' roles="/Policy/Mis/Asset/PermanentAsset/Program/Create" params="id" action="/entityParentPrepareCreate-asset_program" title="Добавить программу в ЛПУ" name="Программу" />
		</msh:sideMenu>
	</tiles:put>
	<tiles:put name='body' type='string' >
		<msh:table name="list" action="entitySubclassView-asset_asset.do" idField="id">
			<msh:tableColumn columnName="#" property="sn" />
			<msh:tableColumn columnName="ИД" property="id" />
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
