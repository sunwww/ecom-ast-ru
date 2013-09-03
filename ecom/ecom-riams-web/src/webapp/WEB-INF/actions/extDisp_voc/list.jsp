<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://struts.apache.org/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://www.nuzmsh.ru/tags/msh" prefix="msh" %>
<%@ taglib uri="http://www.ecom-ast.ru/tags/ecom" prefix="ecom" %>
<tiles:insert page="/WEB-INF/tiles/main${param.short}Layout.jsp" flush="true" >
	<tiles:put name='title' type='string'>
		<msh:title mainMenu="Voc" title="Виды доп. диспансеризации"/>
	</tiles:put>
	<tiles:put name='side' type='string'>
		<msh:sideMenu title="Добавить">
			<msh:sideLink key='ALT+N' roles="/Policy/Mis/ExtDisp/Card/Voc/Create" action="/entityPrepareCreate-extDisp_voc" title="Добавить вид диспансеризации" name="Вид диспансеризации" />
		</msh:sideMenu>
	</tiles:put>
	<tiles:put name='body' type='string' >
	<msh:section createRoles="/Policy/Mis/ExtDisp/Card/Voc/Create" 
		createUrl="entityPrepareCreate-extDisp_voc.do"
		title="Список видов доп.диспансеризации">
		<ecom:webQuery name="list" nativeSql="
		select ved.id, ved.code, ved.name
		from VocExtDisp ved
		"/>
		<msh:table name="list" action="entityView-extDisp_voc.do" idField="1" >
			<msh:tableColumn columnName="#" property="sn" />
			<msh:tableColumn columnName="Код" property="2" />
			<msh:tableColumn columnName="Наименование" property="3" />
		</msh:table>
	</msh:section>
</tiles:put>
</tiles:insert>
