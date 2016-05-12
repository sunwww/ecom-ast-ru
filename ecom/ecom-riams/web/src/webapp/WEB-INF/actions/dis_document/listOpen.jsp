<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://struts.apache.org/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://www.nuzmsh.ru/tags/msh" prefix="msh" %>
<%@ taglib tagdir="/WEB-INF/tags" prefix="tags" %>

<tiles:insert page="/WEB-INF/tiles/mainLayout.jsp" flush="true">

    <tiles:put name='title' type='string'>
        <msh:title mainMenu="Disability">Открытые документы по датам</msh:title>
    </tiles:put>

    <tiles:put name='side' type='string'>
        <tags:dis_menu currentAction="openDNT"/>
    </tiles:put>

    <tiles:put name='body' type='string'>
        <msh:table name="list" action="dis_documentByDate.do?info=открытых&type=open&infoSearch=Поиск по дате открытия&" idField="dateInfo" noDataMessage="Не найдено">
            <msh:tableColumn columnName="#" property="sn"/>
            <msh:tableColumn columnName="Дата начала" property="date"/>
            <msh:tableColumn columnName="Кол-во" property="cnt"/>
        </msh:table>
    </tiles:put>

</tiles:insert>