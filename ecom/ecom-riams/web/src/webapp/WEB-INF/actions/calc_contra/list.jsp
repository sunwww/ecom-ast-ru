<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://struts.apache.org/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://www.nuzmsh.ru/tags/msh" prefix="msh" %>
<%@ taglib uri="http://www.ecom-ast.ru/tags/ecom" prefix="ecom" %>

<tiles:insert page="/WEB-INF/tiles/main${param.short}Layout.jsp" flush="true">

    <tiles:put name="title" type="string">
        <ecom:titleTrail mainMenu="Calculator" beginForm="calc_calculatorForm"/>
    </tiles:put>


    <tiles:put name="body" type="string">
        <ecom:webQuery name="list" nativeSql="select id,contravalue from contracalc where id=${param.id}" />

        <msh:table name="list" action="entityView-calc_contra.do" idField="1">
            <msh:tableColumn columnName="#" property="sn"/>
            <msh:tableColumn columnName="Противопоказание" property="2"/>
        </msh:table>

    </tiles:put>

    <tiles:put name="side" type="string">
        <msh:sideMenu title="Добавить">
            <msh:sideLink params="id" action="/entityParentPrepareCreate-calc_contra" name="Новое противопоказание" title="Добавить противопоказание"
                          roles="/Policy/Mis/Calc/Calculation/Create" key="ALT+N"/>
        </msh:sideMenu>
    </tiles:put>
</tiles:insert>