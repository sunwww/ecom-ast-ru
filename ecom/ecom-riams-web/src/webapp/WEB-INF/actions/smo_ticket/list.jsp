<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://struts.apache.org/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://www.nuzmsh.ru/tags/msh" prefix="msh" %>
<%@ taglib tagdir="/WEB-INF/tags" prefix="tags" %>
<%@ taglib uri="http://www.ecom-ast.ru/tags/ecom" prefix="ecom" %>
<tiles:insert page="/WEB-INF/tiles/main${param.short}Layout.jsp" flush="true">

    <tiles:put name='title' type='string'>
        <ecom:titleTrail mainMenu="Medcard" beginForm="poly_medcardForm" title="Талоны"/>
    </tiles:put>

    <tiles:put name='side' type='string'>
        <tags:ticket_finds currentAction="tickets"/>
    </tiles:put>

    <tiles:put name='body' type='string'>
            
            <msh:ifInRole roles="/Policy/Mis/MisLpu/Psychiatry">
        <msh:table name="list" 
        deleteUrl="entityParentDeleteGoParentView-smo_ticket.do" editUrl="entityParentEdit-smo_ticket.do" viewUrl="entityShortView-smo_ticket.do"
        action="entityParentView-smo_ticket.do" idField="id" noDataMessage="Не найдено">
            <msh:tableColumn columnName="Номер" property="id"/>
            <msh:tableColumn columnName="Дата выдачи талона" property="dateStart"/>
            <msh:tableColumn columnName="Время выдачи талона" property="timeExecute"/>
            <msh:tableColumn columnName="Специалист" property="workFunctionExecuteInfo" />
	        <msh:tableColumn columnName="Беседа с родст." property="isTalk"/>
        </msh:table>
            </msh:ifInRole>
            <msh:ifNotInRole roles="/Policy/Mis/MisLpu/Psychiatry">
        <msh:table name="list" 
        deleteUrl="entityParentDeleteGoParentView-smo_ticket.do" editUrl="entityParentEdit-smo_ticket.do" viewUrl="entityShortView-smo_ticket.do"
        action="entityParentView-smo_ticket.do" idField="id" noDataMessage="Не найдено">
            <msh:tableColumn columnName="Номер" property="id"/>
            <msh:tableColumn columnName="Дата выдачи талона" property="dateStart"/>
            <msh:tableColumn columnName="Время выдачи талона" property="timeExecute"/>
            <msh:tableColumn columnName="Специалист" property="workFunctionExecuteInfo" />
         </msh:table>
            </msh:ifNotInRole>
            
    </tiles:put>

</tiles:insert>