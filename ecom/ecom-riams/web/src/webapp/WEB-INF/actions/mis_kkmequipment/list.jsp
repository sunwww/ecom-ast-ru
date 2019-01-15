<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://struts.apache.org/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://www.nuzmsh.ru/tags/msh" prefix="msh" %>
<%@ taglib uri="http://www.ecom-ast.ru/tags/ecom" prefix="ecom" %>

<tiles:insert page="/WEB-INF/tiles/mainLayout.jsp" flush="true" >

    <tiles:put name='title' type='string'>
        <msh:title mainMenu="Lpu">Список ККМ</msh:title>
    </tiles:put>

    <tiles:put name='side' type='string'>
        <msh:sideMenu>
            <msh:sideLink key="ALT+1" params="id" action="/javascript:window.history.back()" name="⇧ Назад"/>
            <msh:sideLink key='ALT+N' params="id" action="/entityParentPrepareCreate-mis_kkmequipment" name="Добавить ККМ" />
        </msh:sideMenu>
    </tiles:put>

    <tiles:put name="body" type="string">
        <ecom:webQuery name="kkmList" nameFldSql="kkmList_sql" nativeSql="select id,name,url from equipment where lpu_id=${param.id} and dtype='KkmEquipment'"/>
        <msh:section title="Все ККМ">
            <msh:table name="kkmList" action="entityParentView-mis_kkmequipment.do" idField="1" guid="e699b892-d71e-4622-ae5e-eaec3ed85bb4">
                <msh:tableColumn columnName="Название" property="2" guid="f34e-392-4978-b31f-5e54ff2e45bd" />
                <msh:tableColumn columnName="URL" property="3" guid="f34e-392-4978-b31f-5e54ff2e45bd" />
            </msh:table>
        </msh:section>
    </tiles:put>
</tiles:insert>