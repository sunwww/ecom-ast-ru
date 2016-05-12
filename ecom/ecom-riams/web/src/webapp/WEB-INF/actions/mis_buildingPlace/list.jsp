<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://struts.apache.org/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://www.nuzmsh.ru/tags/msh" prefix="msh" %>

<tiles:insert page="/WEB-INF/tiles/mainLayout.jsp" flush="true" >

    <tiles:put name='title' type='string'>
        <msh:title mainMenu="Lpu">Здания</msh:title>
    </tiles:put>

    <tiles:put name='side' type='string'>
        <msh:sideMenu>
            <msh:sideLink key='ALT+N' roles="/Policy/Mis/WorkPlace/BuildingPlace/Create" action="/entityPrepareCreate-mis_buildingPlace" name="Добавить здание" />

        </msh:sideMenu>
    </tiles:put>

    <tiles:put name='body' type='string' >
        <msh:table name="list" action="entityView-mis_buildingPlace.do" idField="id">
            <msh:tableColumn columnName="Код" property="id" />
            <msh:tableColumn columnName="Наименование здания" property="name" />
            <msh:tableColumn columnName="Комментарий" property="comment" cssClass="preCell" />
        </msh:table>
    </tiles:put>

</tiles:insert>