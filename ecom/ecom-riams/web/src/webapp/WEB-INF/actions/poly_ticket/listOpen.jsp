<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://struts.apache.org/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://www.nuzmsh.ru/tags/msh" prefix="msh" %>
<%@ taglib tagdir="/WEB-INF/tags" prefix="tags" %>

<tiles:insert page="/WEB-INF/tiles/mainLayout.jsp" flush="true">

    <tiles:put name='title' type='string'>
        <msh:title mainMenu="Medcard">Открытые талоны по датам</msh:title>
    </tiles:put>

    <tiles:put name='side' type='string'>
        <tags:ticket_finds currentAction="ticketopen"/>
    </tiles:put>

    <tiles:put name='body' type='string'>
        <msh:table name="list" action="poly_ticketOpenByDate.do" idField="dateInfo" noDataMessage="Не найдено">
            <msh:tableColumn columnName="#" property="sn"/>
            <msh:tableColumn columnName="Дата приема" property="date"/>
            <msh:tableColumn columnName="Кол-во" property="cnt"/>
        </msh:table>
    </tiles:put>

</tiles:insert>