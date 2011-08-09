<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ include file="/WEB-INF/tiles/header.jsp" %>
<%@ taglib uri="http://www.nuzmsh.ru/tags/msh" prefix="msh" %>

<tiles:insert page="/WEB-INF/tiles/mainLayout.jsp" flush="true" >

    <tiles:put name='title' type='string'>
        <h1>/ Список реестров по дате периодам</h1>
    </tiles:put>

    <tiles:put name='side' type='string'>
        <msh:sideMenu>
            <msh:sideLink key='ALT+N' params="" action="/entityPrepareCreate-exp_regperiod.do" name="Добавить период" />
        </msh:sideMenu>
    </tiles:put>

    <tiles:put name='body' type='string' >
        <msh:table name="list" action="entityEdit-exp_regperiod.do" idField="id">
            <msh:tableColumn columnName="Дата c" property="dateFrom" />
            <msh:tableColumn columnName="Дата по" property="dateTo" />
        </msh:table>
    </tiles:put>


</tiles:insert>