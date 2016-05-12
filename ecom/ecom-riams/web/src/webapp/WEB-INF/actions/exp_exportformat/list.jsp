<%-- ikouzmin 070228 +++ Список форматов экспорта --%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ include file="/WEB-INF/tiles/header.jsp" %>
<%@ taglib uri="http://www.nuzmsh.ru/tags/msh" prefix="msh" %>
<%@ taglib uri="http://www.ecom-ast.ru/tags/ecom" prefix="ecom" %>

<tiles:insert page="/WEB-INF/tiles/mainLayout.jsp" flush="true" >

    <tiles:put name='title' type='string'>
        <ecom:titleTrail mainMenu="Document" beginForm="exp_importdocumentForm"  title="Список форматов"/>
    </tiles:put>

    <tiles:put name='side' type='string'>
        <msh:sideMenu>

            <msh:sideLink key="ALT+1" params="id" action="/entityEdit-exp_importdocument" name="⇧ К документу" />

            <msh:sideLink key='ALT+N' params="id" action="/entityParentPrepareCreate-exp_exportformat" name="Создать новый формат" />

        </msh:sideMenu>
    </tiles:put>

    <tiles:put name='body' type='string' >
        <msh:table name="list" action="entityParentEdit-exp_exportformat.do" idField="id">
            <msh:tableColumn columnName="Комментарий" property="comment" />
            <msh:tableColumn columnName="Дата действия с" property="actualDateFrom" />
            <msh:tableColumn columnName="Дата действия по" property="actualDateTo" />
            <msh:tableColumn columnName="Отключен" property="disabled" />
        </msh:table>
    </tiles:put>


</tiles:insert>