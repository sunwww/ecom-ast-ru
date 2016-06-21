<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://struts.apache.org/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://www.nuzmsh.ru/tags/msh" prefix="msh" %>

<tiles:insert page="/WEB-INF/tiles/mainLayout.jsp" flush="true" >

    <tiles:put name='title' type='string'>
        <msh:title mainMenu="Lpu">Списки пациентов</msh:title>
    </tiles:put>

    <tiles:put name='side' type='string'>
        <msh:sideMenu>
            <msh:sideLink key='ALT+N' params="id" action="/entityPrepareCreate-mis_patientList" name="Создать новый список" />
        </msh:sideMenu>
    </tiles:put>

    <tiles:put name='body' type='string' >

        <msh:table name="list" action="entityView-mis_patientList.do" idField="id">
            <msh:tableColumn columnName="Название" property="name"/>
            <msh:tableColumn columnName="Цвет сообщения" property="colorName"/>
        </msh:table>
    </tiles:put>

</tiles:insert>