<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ include file="/WEB-INF/tiles/header.jsp" %>
<%@ taglib uri="http://www.nuzmsh.ru/tags/msh" prefix="msh" %>

<tiles:insert page="/WEB-INF/tiles/mainLayout.jsp" flush="true" >

    <tiles:put name='title' type='string'>
        <h1>Список страховый компаний</h1>
    </tiles:put>

    <tiles:put name='side' type='string'>
        <msh:sideMenu>
            <msh:sideLink key='ALT+N' params="" action="/entityPrepareCreate-exp_regic.do" name="Добавить страховую компанию" />
        </msh:sideMenu>
    </tiles:put>

    <tiles:put name='body' type='string' >
        <msh:table name="list" action="entityEdit-exp_regic.do" idField="id">
            <msh:tableColumn columnName="Наименование" property="name" />
            <msh:tableColumn columnName="Код ОМС" property="omcCode" />
        </msh:table>
    </tiles:put>


</tiles:insert>