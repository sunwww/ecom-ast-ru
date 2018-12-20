<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://struts.apache.org/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://www.nuzmsh.ru/tags/msh" prefix="msh" %>
<%@ taglib tagdir="/WEB-INF/tags" prefix="tags" %>
<%@ taglib prefix="ecom" uri="http://www.ecom-ast.ru/tags/ecom" %>

<tiles:insert page="/WEB-INF/tiles/mainLayout.jsp" flush="true" >

    <tiles:put name="body" type="string">
        <ecom:webQuery name="list" nativeSql="select c.id,c.name from calculator c" />

        <msh:table name="list" action="entityView-calc_calculator.do" idField="1">
            <msh:tableColumn columnName="#" property="sn"/>
            <msh:tableColumn columnName="Название" property="2"/>
        </msh:table>

    </tiles:put>

    <tiles:put name="side" type="string">
        <msh:sideMenu title="Добавить">
            <msh:sideLink action="/entityPrepareCreate-calc_calculator" name="Калькулятор" title="Добавить калькулятор"
                          roles="/Policy/Mis/Calc/Calculator" key="ALT+1"/>
        </msh:sideMenu>
    </tiles:put>
</tiles:insert>