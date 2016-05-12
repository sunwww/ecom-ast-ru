<%@ page import="java.util.Collection"%>
<%@ page import="ru.ecom.expomc.web.actions.importdata.ImportDataTableHelper"%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ include file="/WEB-INF/tiles/header.jsp" %>
<%@ taglib uri="http://www.nuzmsh.ru/tags/msh" prefix="msh" %>

<tiles:insert page="/WEB-INF/tiles/mainLayout.jsp" flush="true" >

    <tiles:put name='title' type='string'>
        <ecom:titleTrail mainMenu="Document" beginForm="exp_importtimeForm"/>
    </tiles:put>

    <tiles:put name='side' type='string'>
    </tiles:put>

    <tiles:put name='body' type='string' >
        <%
            Collection list = (Collection) request.getAttribute("list") ;
            ImportDataTableHelper.print(list, out);

        %>

    </tiles:put>


</tiles:insert>