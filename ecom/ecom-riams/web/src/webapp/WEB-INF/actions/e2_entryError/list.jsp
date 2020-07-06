<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib uri="http://struts.apache.org/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://www.nuzmsh.ru/tags/msh" prefix="msh" %>
<%@ taglib tagdir="/WEB-INF/tags" prefix="tags" %>
<%@ taglib uri="http://www.ecom-ast.ru/tags/ecom" prefix="ecom" %>

<tiles:insert page="/WEB-INF/tiles/mainLayout.jsp" flush="true">

    <tiles:put name='title' type='string'>
        <msh:title mainMenu="Expert2">Список ошибок по заполнению</msh:title>
    </tiles:put>

    <tiles:put name='side' type='string'>

        <tags:expertvoc_menu currentAction="main"/>
    </tiles:put>

    <tiles:put name='body' type='string'>

        <msh:hideException>
            <ecom:webQuery name="entriesList" nativeSql="select '${param.id}&errorCode='||e.errorcode, e.errorcode as error , count( distinct e.entry_id) as cnt
                from e2entryerror e
                where e.listentry_id=${param.id} and isdeleted='0' group by e.errorcode"/>
            <msh:table idField="1" name="entriesList" action="entityParentList-e2_entry.do" noDataMessage="Нет ошибок по заполнению">
                <msh:tableColumn columnName="Ошибка" property="2" />
                <msh:tableColumn columnName="Кол-во записей с ошшбкой" property="3"/>
            </msh:table>
        </msh:hideException>
    </tiles:put>
</tiles:insert>