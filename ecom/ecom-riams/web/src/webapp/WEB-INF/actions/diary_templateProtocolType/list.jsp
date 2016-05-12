<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://struts.apache.org/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://www.nuzmsh.ru/tags/msh" prefix="msh" %>

<tiles:insert page="/WEB-INF/tiles/mainLayout.jsp" flush="true" >

    <tiles:put name='title' type='string'>
        <msh:title mainMenu="Templateword">Типы шаблонов</msh:title>
    </tiles:put>

    <tiles:put name='side' type='string'>
        <msh:sideMenu>
            <msh:sideLink key='ALT+N' params="" action="/entityPrepareCreate-diary_templateProtocolType" name="Добавить тип протокола" />
        </msh:sideMenu>
    </tiles:put>
    <tiles:put name='body' type='string' >
        <msh:table name="list" action="entityView-diary_templateProtocolType.do" idField="id">
            <msh:tableColumn columnName="Ид" property="id" />
            <msh:tableColumn columnName="Наименование" property="name" />
        </msh:table>
    </tiles:put>
</tiles:insert>