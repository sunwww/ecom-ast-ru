<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://struts.apache.org/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://www.nuzmsh.ru/tags/msh" prefix="msh" %>

<tiles:insert page="/WEB-INF/tiles/mainLayout.jsp" flush="true" >

    <tiles:put name='title' type='string'>
        <msh:title mainMenu="Protocol">Протоколы</msh:title>
    </tiles:put>

    <tiles:put name='side' type='string'>
        <msh:sideMenu>
            <msh:sideLink key='ALT+N' params="sloId" action="/entityPrepareCreate-diary_protocol" name="Добавить протокол к случаю" />
        </msh:sideMenu>
    </tiles:put>
    <tiles:put name='body' type='string' >
        <msh:table name="list" action="entityView-diary_protocol.do" idField="id">
            <msh:tableColumn columnName="Ид" property="id" />
            <msh:tableColumn columnName="Ид СЛC" property="slsId" />
            <msh:tableColumn columnName="Ид СЛО" property="sloId" />
            <msh:tableColumn columnName="Лечаший врач" property="doctorId"/>
            <msh:tableColumn columnName="Заключение" property="record"/>
            <msh:tableColumn columnName="Дата" property="date"/>
        </msh:table>
    </tiles:put>

</tiles:insert>