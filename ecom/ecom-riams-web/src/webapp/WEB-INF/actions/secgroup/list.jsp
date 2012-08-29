<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ include file="/WEB-INF/tiles/header.jsp" %>
<%@ taglib uri="http://www.nuzmsh.ru/tags/msh" prefix="msh" %>
<%@ taglib tagdir="/WEB-INF/tags" prefix="tags" %>

<tiles:insert page="/WEB-INF/tiles/mainLayout.jsp" flush="true" >

    <tiles:put name='title' type='string'>
        <h1>Список групп</h1>
    </tiles:put>
    <tiles:put name='side' type='string'>
        <msh:sideMenu title="Создать">
            <msh:sideLink key='ALT+N' params="" action="/entityPrepareCreate-secgroup" name="Группу" />
        </msh:sideMenu>
        <tags:menuJaas currentAction="groups"/>
    </tiles:put>
    <tiles:put name='body' type='string' >
        <msh:table name="list" action="entityView-secgroup.do" idField="id">
            <msh:tableColumn columnName="Название" property="name" />
            <msh:tableColumn columnName="Комментарий" property="comment" />
        </msh:table>
    </tiles:put>    
</tiles:insert>