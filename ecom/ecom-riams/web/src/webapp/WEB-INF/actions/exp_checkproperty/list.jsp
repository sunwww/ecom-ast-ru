<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ include file="/WEB-INF/tiles/header.jsp" %>
<%@ taglib uri="http://www.nuzmsh.ru/tags/msh" prefix="msh" %>

<tiles:insert page="/WEB-INF/tiles/mainLayout.jsp" flush="true" >

    <tiles:put name='title' type='string'>
            <ecom:titleTrail mainMenu="Document" beginForm="exp_checkForm" />
    </tiles:put>

    <tiles:put name='side' type='string'>
        <msh:sideMenu>
            <msh:sideLink key="ALT+1" params="id" action="/entityParentView-exp_check" name="⇧ К проверке" />

        </msh:sideMenu>
    </tiles:put>

    <tiles:put name='body' type='string' >
        <msh:table name="list" action="exp_checkPropertyEdit.do" idField="checkAndProperty">
            <msh:tableColumn columnName="Свойство" property="property" />
            <msh:tableColumn columnName="Значение" property="value" />
            <msh:tableColumn columnName="Комментарий" property="comment" />
        </msh:table>
    </tiles:put>


</tiles:insert>