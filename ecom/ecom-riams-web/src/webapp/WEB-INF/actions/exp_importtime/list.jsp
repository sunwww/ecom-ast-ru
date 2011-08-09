<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ include file="/WEB-INF/tiles/header.jsp" %>
<%@ taglib uri="http://www.nuzmsh.ru/tags/msh" prefix="msh" %>

<tiles:insert page="/WEB-INF/tiles/mainLayout.jsp" flush="true" >

    <tiles:put name='title' type='string'>
        <ecom:titleTrail mainMenu="Document" beginForm="exp_importdocumentForm" title="Импортировано по дате"/>
    </tiles:put>

    <tiles:put name='side' type='string'>
        <msh:sideMenu>
            <msh:sideLink key="ALT+1" params="id" action="/entityEdit-exp_importdocument" name="⇧ К документу" />

            <msh:sideLink key="ALT+2" params="id" action="/exp_importTimeUploadEdit" name="Загрузить данные" />
        </msh:sideMenu>
    </tiles:put>

    <tiles:put name='body' type='string' >
        <pre>
            <%--<%=session.getAttribute("importResultMessages")%>--%>
        </pre>
        <msh:table name="list" action="entityParentEdit-exp_importtime.do" idField="id">
            <msh:tableColumn columnName="Комментарий" property="comment" />
            <msh:tableColumn columnName="Дата импорта" property="importDate" />
            <msh:tableColumn columnName="Дата актуальности с" property="actualDateFrom" />
            <msh:tableColumn columnName="Дата актуальности по" property="actualDateTo" />
        </msh:table>
    </tiles:put>


</tiles:insert>