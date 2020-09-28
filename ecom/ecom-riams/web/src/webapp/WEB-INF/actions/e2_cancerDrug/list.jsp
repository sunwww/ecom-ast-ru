<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://struts.apache.org/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://www.nuzmsh.ru/tags/msh" prefix="msh" %>
<%@ taglib tagdir="/WEB-INF/tags" prefix="tags" %>
<%@ taglib prefix="ecom" uri="http://www.ecom-ast.ru/tags/ecom" %>

<tiles:insert page="/WEB-INF/tiles/mainLayout.jsp" flush="true">

    <tiles:put name='title' type='string'>
        <ecom:titleTrail beginForm="e2_entryListForm" mainMenu="Expert2" title="Записи по заполнению" />
    </tiles:put>

    <tiles:put name='side' type='string'>
        <tags:expertvoc_menu currentAction="main"/>
    </tiles:put>

    <tiles:put name='body' type='string'>
        не реализовано
    </tiles:put>
    <tiles:put name="javascript" type="string">
        <script type="text/javascript">

        </script>
    </tiles:put>
</tiles:insert>