<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ include file="/WEB-INF/tiles/header.jsp" %>
<%@ taglib uri="http://www.nuzmsh.ru/tags/msh" prefix="msh" %>

<tiles:insert page="/WEB-INF/tiles/mainLayout.jsp" flush="true" >

    <tiles:put name='title' type='string'>
        <ecom:titleTrail mainMenu="Document" beginForm="exp_importtimeForm" title="Список сообщений"/>
    </tiles:put>

    <tiles:put name='side' type='string'>
        <msh:sideMenu>
        </msh:sideMenu>
    </tiles:put>

    <tiles:put name='body' type='string' >
        <msh:table name="list" action="exp_messageView.do" idField="id">
            <msh:tableColumn columnName="Сообщение" property="name" />
            <msh:tableColumn columnName="Комментарий" property="comment" />
            <msh:tableColumn columnName="Тип сообщения" property="checkTypeName" />
        </msh:table>
    </tiles:put>


</tiles:insert>