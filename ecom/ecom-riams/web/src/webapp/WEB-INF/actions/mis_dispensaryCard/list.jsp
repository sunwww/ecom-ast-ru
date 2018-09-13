<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://struts.apache.org/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://www.nuzmsh.ru/tags/msh" prefix="msh" %>

<tiles:insert page="/WEB-INF/tiles/mainLayout.jsp" flush="true" >

    <tiles:put name='title' type='string'>
        <msh:title mainMenu="Lpu">Карты диспансерного наблюденияу</msh:title>
    </tiles:put>

    <tiles:put name='side' type='string'>
        <msh:sideMenu>
           
        </msh:sideMenu>
    </tiles:put>

    <tiles:put name='body' type='string' >
        <msh:table name="list" action="entityView-mis_dispensaryCard.do" idField="id">
            <msh:tableColumn columnName="Дата постановки на учет" property="startDate" />
            <msh:tableColumn columnName="Диагноз" property="diagnosis" />
        </msh:table>
    </tiles:put>
    
    <tiles:put name='javascript' type='string'>
    <script type='text/javascript'>

    </script>
</tiles:put>
</tiles:insert>