<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://struts.apache.org/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://www.nuzmsh.ru/tags/msh" prefix="msh" %>
<%@ taglib tagdir="/WEB-INF/tags" prefix="tags" %>
<%@ taglib uri="http://www.ecom-ast.ru/tags/ecom" prefix="ecom" %>

<tiles:insert page="/WEB-INF/tiles/mainLayout.jsp" flush="true">

    <tiles:put name='title' type='string'>
        <msh:title mainMenu="Voc">Просмотр справочника типов оборудования</msh:title>
    </tiles:put>

    <tiles:put name='side' type='string'>
    <msh:sideMenu title="Добавить" >
      <msh:sideLink key="ALT+N" roles="/Policy/Voc/VocTypeEquip/Create" action="/entityPrepareCreate-voc_typeEquip" name="Тип оборудования" />
    </msh:sideMenu>
    <tags:voc_menu currentAction="typeEquip"/>
    </tiles:put>

    <tiles:put name='body' type='string'>
       <msh:section>
	       	<msh:sectionTitle>Справочник типов оборудования</msh:sectionTitle>
	       	<msh:sectionContent>
	       		<ecom:webQuery nativeSql="
	       			select vte.id, vte.name, vte.code
	       			from VocTypeEquip vte
	       		" name="list"/>
	            <msh:table name="list" action="entityView-voc_typeEquip.do" idField="1" disableKeySupport="true">
	                <msh:tableColumn columnName="Название" property="2"/>
	                <msh:tableColumn columnName="код" property="3"/>
	                </msh:table>
	       	</msh:sectionContent>
       </msh:section>

    </tiles:put>

</tiles:insert>