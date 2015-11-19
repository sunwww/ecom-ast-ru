<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://struts.apache.org/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://www.nuzmsh.ru/tags/msh" prefix="msh" %>
<%@ taglib uri="http://www.ecom-ast.ru/tags/ecom" prefix="ecom" %>
<%@ taglib tagdir="/WEB-INF/tags" prefix="tags" %>

<tiles:insert page="/WEB-INF/tiles/mainLayout.jsp" flush="true" >
	<tiles:put name='title' type='string'>
		<msh:title mainMenu="Contract">Список Нозологическая группа по договору</msh:title>
	</tiles:put>
	<tiles:put name='side' type='string'>
		<msh:sideMenu title="Добавить">
			<msh:sideLink key='ALT+N' roles="/Policy/Mis/Contract/GroupRules/ContractNosologyGroup/Create" params="" action="/entityPrepareCreate-contract_nosologyGroup" title="Нозологическая группа по договору" name="Нозологическая группа по договору" />
		</msh:sideMenu>
		<tags:contractMenu currentAction="nosologyGroup"/>
	</tiles:put>
	<tiles:put name='body' type='string' >
		<ecom:webQuery name="list" nativeSql="
		select cng.id,cng.name
		,list(cni.fromIdc10Code||'-'||cni.toIdc10Code)
		from ContractNosologyGroup cng
		left join NosologyInterval cni on cng.id=cni.nosologyGroup_id
		group by cng.id,cng.name
		order by cng.name
		"/>
		<msh:table name="list" action="entityView-contract_nosologyGroup.do" idField="1">
			<msh:tableColumn columnName="Название" property="2" />
			<msh:tableColumn columnName="Маска" property="3" />
		</msh:table>
	</tiles:put>
</tiles:insert>
