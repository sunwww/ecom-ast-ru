<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://struts.apache.org/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://www.nuzmsh.ru/tags/msh" prefix="msh" %>
<%@ taglib tagdir="/WEB-INF/tags" prefix="tags" %>

<tiles:insert page="/WEB-INF/tiles/mainLayout.jsp" flush="true">

    <tiles:put name='title' type='string'>
        <msh:title mainMenu="Medcard">Поиск талонов по номеру</msh:title>
    </tiles:put>

    <tiles:put name='side' type='string'>
        <tags:ticket_finds currentAction="tickets"/>
    </tiles:put>

    <tiles:put name='body' type='string'>
            <msh:form action="/poly_tickets.do" defaultField="numberTicket" method="GET">
                <msh:panel colsWidth="10%,10%,80%">
                    <msh:row>
                        <msh:textField property="numberTicket" label="Номер талона" size='10' />
                        <td><input type='submit' value='Найти'></td>
                    </msh:row>
                </msh:panel>
            </msh:form>

        <msh:table name="list" action="entityParentEdit-poly_ticket.do" idField="id" noDataMessage="Не найдено">
            <msh:tableColumn columnName="Номер" property="id"/>
            <msh:tableColumn columnName="Дата выдачи талона" property="date"/>
            <msh:tableColumn columnName="Время выдачи талона" property="time"/>
            <msh:tableColumn columnName="Специалист" property="workFunctionInfo" />
            <msh:tableColumn columnName="Пациент" property="patientName" />
            <msh:tableColumn columnName="Статус" property="statusName"/>
        </msh:table>
    </tiles:put>

</tiles:insert>