<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://struts.apache.org/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://www.nuzmsh.ru/tags/msh" prefix="msh" %>
<%@ taglib uri="http://www.ecom-ast.ru/tags/ecom" prefix="ecom" %>
<tiles:insert page="/WEB-INF/tiles/mainLayout.jsp" flush="true" >
	<tiles:put name='title' type='string'>
		<msh:title>Список актов передач копий</msh:title>
	</tiles:put>
	<tiles:put name='side' type='string'>
		<msh:sideMenu title="Добавить">
			<msh:sideLink key='ALT+N' roles="/Policy/PersData/Act/CopiesTransfer/Create" action="/entityPrepareCreate-pd_copiesTransferAct" title="Акт передачи копий" name="Акт передачи копий" />
		</msh:sideMenu>
	</tiles:put>
	<tiles:put name='body' type='string' >
		<msh:table name="list" action="entityView-pd_copiesTransferAct.do" idField="id">
			<msh:tableColumn columnName="#" property="sn" />
			<msh:tableColumn columnName="Номер акта" property="actNumber" />
			<msh:tableColumn columnName="Дата" property="urgencyStartDate" />
			<msh:tableColumn columnName="Кол-во копий" property="copiesAmount" />
		</msh:table>
	</tiles:put>
</tiles:insert>