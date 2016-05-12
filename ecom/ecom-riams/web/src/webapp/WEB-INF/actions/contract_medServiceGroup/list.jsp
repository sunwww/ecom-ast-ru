<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://struts.apache.org/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://www.nuzmsh.ru/tags/msh" prefix="msh" %>
<%@ taglib uri="http://www.ecom-ast.ru/tags/ecom" prefix="ecom" %>
<%@ taglib tagdir="/WEB-INF/tags" prefix="tags" %>

<tiles:insert page="/WEB-INF/tiles/mainLayout.jsp" flush="true" >
	<tiles:put name='title' type='string'>
		<msh:title mainMenu="Contract">Список медицинских групп по договору</msh:title>
	</tiles:put>
	<tiles:put name='side' type='string'>
		<msh:sideMenu title="Добавить">
			<msh:sideLink key='ALT+N' roles="/Policy/Mis/Contract/GroupRules/ContractMedServiceGroup/Create" params="" action="/entityPrepareCreate-contract_medServiceGroup" title="Добавить медицинскую группу по договору" name="Медицинскую группу по договору" />
		</msh:sideMenu>
		<tags:contractMenu currentAction="medServiceGroup"/>
	</tiles:put>
	<tiles:put name='body' type='string' >
	<ecom:webQuery name="list" nativeSql="
		select cng.id,cng.name
		,list(cni.fromMedServiceCode||'-'||cni.toMedServiceCode)
		from ContractMedServiceGroup cng
		left join MedServiceInterval cni on cng.id=cni.medServiceGroup_id
		group by cng.id,cng.name
		order by cng.name
		"/>
		<msh:table name="list" action="entityView-contract_medServiceGroup.do" idField="1">
			<msh:tableColumn columnName="Название" property="2" />
			<msh:tableColumn columnName="Маска" property="3" />
		</msh:table>
	</tiles:put>
</tiles:insert>
