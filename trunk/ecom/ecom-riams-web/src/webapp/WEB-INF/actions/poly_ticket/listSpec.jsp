<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://struts.apache.org/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://www.nuzmsh.ru/tags/msh" prefix="msh" %>
<%@ taglib tagdir="/WEB-INF/tags" prefix="tags" %>

<tiles:insert page="/WEB-INF/tiles/mainLayout.jsp" flush="true">

    <tiles:put name='title' type='string'>
        <msh:title mainMenu="Medcard">Поиск талонов по специалисту</msh:title>
    </tiles:put>

    <tiles:put name='side' type='string'>
        <tags:ticket_finds currentAction="ticketspec"/>
    </tiles:put>

    <tiles:put name='body' type='string'>
            <msh:form action="/poly_ticketspec.do" defaultField="doctorName" method="GET">
                <msh:panel colsWidth="10%,10%,80%">
                	<msh:ifNotInRole roles="/Policy/Poly/Ticket/IsDoctorEdit">
                    <msh:row>
                        <msh:autoComplete property="doctor" label="Специалист" size='50' horizontalFill="true"
                                          vocName="workFunction" fieldColSpan="3"/>
                    </msh:row>
                    </msh:ifNotInRole>
                    <msh:row>                      
                        <msh:textField property="dateFilter" label="Дата обращения" size='10' />
                        <td><input type='submit' value='Найти'></td>
                    </msh:row>
                </msh:panel>
            </msh:form>
            

            <msh:section title="Результаты поиска">
        <msh:table viewUrl="entityShortView-poly_ticket.do" editUrl="entityParentEdit-poly_ticket.do" deleteUrl="entityParentDeleteGoParentView-poly_ticket.do" name="list" action="entityView-poly_ticket.do" idField="id" noDataMessage="Не найдено">
            <msh:tableColumn columnName="Номер" property="id"/>
            <msh:tableColumn columnName="Дата приема" property="date"/>
            <msh:tableColumn columnName="Время приема" property="time"/>
            <msh:tableColumn columnName="Пациент" property="patientName" />
            <msh:tableColumn columnName="Статус" property="statusName"/>
            <msh:tableColumn columnName="Специалист" property="workFunctionInfo" />
        </msh:table>
        </msh:section>

    </tiles:put>

</tiles:insert>