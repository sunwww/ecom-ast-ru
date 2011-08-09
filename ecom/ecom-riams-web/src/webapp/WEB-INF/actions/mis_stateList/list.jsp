<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://struts.apache.org/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://www.nuzmsh.ru/tags/msh" prefix="msh" %>

<tiles:insert page="/WEB-INF/tiles/mainLayout.jsp" flush="true" >

    <tiles:put name='title' type='string'>
        <msh:title mainMenu="Lpu">Штатное расписание</msh:title>
    </tiles:put>

    <tiles:put name='side' type='string'>
        <msh:sideMenu>
            <msh:sideLink key="ALT+1" params="id" action="/entityView-mis_lpu" name="⇧ К ЛПУ"/>
            <msh:sideLink key='ALT+N' params="id" action="/entityParentPrepareCreate-mis_stateList" name="Добавить должность в штатное расписание" />
        </msh:sideMenu>
    </tiles:put>

    <tiles:put name='body' type='string' >

        <msh:table name="list" action="entityParentView-mis_stateList.do" idField="id">
            <msh:tableColumn columnName="Должность" property="namePost"/>
            <msh:tableColumn columnName="Ставок всего" property="fullRate"/>
            <msh:tableColumn columnName="Ставок вакантных всего" property="freeFullRate"/>
        </msh:table>
    </tiles:put>

</tiles:insert>