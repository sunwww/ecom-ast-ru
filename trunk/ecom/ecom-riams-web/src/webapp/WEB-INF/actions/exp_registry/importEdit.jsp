<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ include file="/WEB-INF/tiles/header.jsp" %>
<%@ taglib uri="http://www.nuzmsh.ru/tags/msh" prefix="msh" %>

<tiles:insert page="/WEB-INF/tiles/mainLayout.jsp" flush="true" >

    <tiles:put name='title' type='string'>
        <h1>Импорт реестра. Шаг 2. Выбор параметров импорта.</h1>
    </tiles:put>

    <tiles:put name='side' type='string'>
        <msh:sideMenu>
        </msh:sideMenu>
    </tiles:put>

    <tiles:put name='body' type='string' >
        <h2>Результат обработки импорта</h2>
        <table>
            <tr><td>Количество записей:</td><td>${result.count}</td></tr>
            <tr><td>Страховая компания:</td><td>${result.companyOmcCode}</td></tr>
            <tr><td>Счет:</td><td>${result.billNumber}</td></tr>
            <tr><td>Реестр:</td><td>${result.registryNumber}</td></tr>
        </table>

        
    </tiles:put>


</tiles:insert>