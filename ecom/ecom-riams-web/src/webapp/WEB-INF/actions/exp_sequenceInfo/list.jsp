<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://struts.apache.org/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://www.nuzmsh.ru/tags/msh" prefix="msh" %>
<%@ taglib uri="http://www.ecom-ast.ru/tags/ecom" prefix="ecom" %>

<tiles:insert page="/WEB-INF/tiles/mainLayout.jsp" flush="true" >
    <tiles:put name='title' type='string'>
        <msh:title guid='hello' mainMenu="Config">Название</msh:title>
    </tiles:put>
    <tiles:put name='side' type='string'>
        <msh:sideMenu>
            <msh:sideLink roles="/Policy/Mis/Patient/Create" key='ALT+N' action="/entityPrepareCreate-exp_sequenceInfo" name="Создать новое NAME" />
        </msh:sideMenu>
    </tiles:put>
    <tiles:put name='body' type='string' >
        <msh:table name="list" action="entityView-FORM_NAME_WITHOUT_FORM.do" idField="id">
            <msh:tableColumn columnName="Название" property="uniqueName" />
        </msh:table>
    </tiles:put>
</tiles:insert>