<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://struts.apache.org/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://www.nuzmsh.ru/tags/msh" prefix="msh" %>

<tiles:insert page="/WEB-INF/tiles/mainLayout.jsp" flush="true" >

    <tiles:put name='title' type='string'>
        <msh:title mainMenu="Lpu">Заявки в техподдержку</msh:title>
    </tiles:put>

    <tiles:put name='side' type='string'>
        <msh:sideMenu>
           
        </msh:sideMenu>
    </tiles:put>

    <tiles:put name='body' type='string' >
        <msh:table name="list" action="entityView-mis_claim.do" idField="id">
            <msh:tableColumn columnName="Код" property="id" />
            <msh:tableColumn columnName="Наименование здания" property="description" />
            <msh:tableColumn columnName="Создатель" property="username" cssClass="preCell" />
            <msh:tableButton property="id" buttonName="Просмотрено" buttonFunction="setView" />
            <msh:tableButton property="id" buttonFunction="setStarted" buttonShortName="Передано" buttonName="Передано в работу"/>
        </msh:table>
    </tiles:put>
    
    <tiles:put name='javascript' type='string'>
    <script type='text/javascript' src='./dwr/interface/ClaimService.js'></script>
    <script type='text/javascript'>
    function setView(aId) {
    	ClaimService.setView(aId);
    }
    
    function setStarted(aId, aExecutorId) {
    	ClaimService.setStarted(aId, aExecutorId);
    }
    </script>
</tiles:put>
</tiles:insert>