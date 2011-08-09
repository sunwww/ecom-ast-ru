<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ include file="/WEB-INF/tiles/header.jsp" %>
<%@ taglib uri="http://www.nuzmsh.ru/tags/msh" prefix="msh" %>

<tiles:insert page="/WEB-INF/tiles/mainLayout.jsp" flush="true" >

    <tiles:put name='title' type='string'>
        <h1>Работа с реестрами.</h1>
    </tiles:put>

    <tiles:put name='side' type='string'>
        <msh:sideMenu>
        </msh:sideMenu>
    </tiles:put>

    <tiles:put name='body' type='string' >
        <msh:link action="/entityList-exp_regperiod.do">Периоды</msh:link>
        <msh:link action="/exp_regPreImportEdit.do">Импорт реестра</msh:link>

    </tiles:put>

</tiles:insert>