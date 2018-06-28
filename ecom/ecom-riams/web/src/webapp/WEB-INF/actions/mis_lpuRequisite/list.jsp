<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@page import="ru.ecom.web.util.ActionUtil"%>
<%@ taglib uri="http://struts.apache.org/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://www.nuzmsh.ru/tags/msh" prefix="msh" %>
<%@ taglib uri="http://www.ecom-ast.ru/tags/ecom" prefix="ecom" %>
<tiles:insert page="/WEB-INF/tiles/mainLayout.jsp" flush="true" >

    <tiles:put name='title' type='string'>
        <msh:title mainMenu="Lpu">ЛПУ</msh:title>
    </tiles:put>

    <tiles:put name='side' type='string'>
        <msh:sideMenu>
            <msh:sideLink key='ALT+N' roles="/Policy/Mis/MisLpu/Create" params="id" action="/entityParentPrepareCreate-mis_lpuRequisite" name="Добавить реквизит" />

        </msh:sideMenu>
    </tiles:put>
    <tiles:put name='body' type='string' >

        Текущее ЛПУ:
        <ecom:webQuery name="requisites" nativeSql="select id, name, code, value
        from MisLpuRequisite where lpu=${param.id}" />
        <msh:table name="requisites" action="entityView-mis_lpuRequisite.do" idField="1">
            <msh:tableColumn columnName="Название" property="2" />
            <msh:tableColumn columnName="Код" property="3" />
            <msh:tableColumn columnName="Значение" property="4" />
        </msh:table>


    </tiles:put>

</tiles:insert>