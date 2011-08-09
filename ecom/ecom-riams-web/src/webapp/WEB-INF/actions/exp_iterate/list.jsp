<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ include file="/WEB-INF/tiles/header.jsp" %>
<%@ taglib uri="http://www.nuzmsh.ru/tags/msh" prefix="msh" %>

<tiles:insert page="/WEB-INF/tiles/mainLayout.jsp" flush="true" >

    <tiles:put name='title' type='string'>
        <msh:title mainMenu="Document">Перебор</msh:title>
    </tiles:put>

    <tiles:put name='side' type='string'>
        <msh:sideMenu>
            <msh:sideLink roles="/Policy/Exp/FillTime/Create" key='ALT+N' action="/entityPrepareCreate-exp_iterate" name="Создать новый перебор" />
        </msh:sideMenu>
    </tiles:put>

    <tiles:put name='body' type='string' >
        <msh:table name="list" action="entityView-exp_iterate.do" idField="id">
            <msh:tableColumn columnName="Название" property="name" />
            <msh:tableColumn columnName="Запрос" property="hqlString" />
        </msh:table>
    </tiles:put>
</tiles:insert>