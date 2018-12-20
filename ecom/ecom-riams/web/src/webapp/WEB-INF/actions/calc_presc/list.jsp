<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://struts.apache.org/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://www.nuzmsh.ru/tags/msh" prefix="msh" %>
<%@ taglib uri="http://www.ecom-ast.ru/tags/ecom" prefix="ecom" %>

<tiles:insert page="/WEB-INF/tiles/main${param.short}Layout.jsp" flush="true">

    <tiles:put name="title" type="string">
        <ecom:titleTrail mainMenu="Calculator" beginForm="calc_calculatorForm"/>
    </tiles:put>


    <tiles:put name="body" type="string">
        <ecom:webQuery name="list" nativeSql="select p.id,p.prescvalue,r.riskvalue,r.lowscore,r.upscore from presccalc p
        left join calcrisk r on r.id=p.calcrisk_id where p.calculator_id=${param.id}" />

        <msh:table name="list" action="entityView-calc_presc.do" idField="1">
            <msh:tableColumn columnName="#" property="sn"/>
            <msh:tableColumn columnName="Назначение" property="2"/>
            <msh:tableColumn columnName="Риск" property="3"/>
            <msh:tableColumn columnName="Баллы риска от" property="4"/>
            <msh:tableColumn columnName="Да" property="5"/>
        </msh:table>

    </tiles:put>

    <tiles:put name="side" type="string">
        <msh:sideMenu title="Добавить">
            <msh:sideLink params="id" action="/entityParentPrepareCreate-calc_presc" name="Новое назначение" title="Добавить назначение"
                          roles="/Policy/Mis/Calc/Calculation/Create" key="ALT+N"/>
        </msh:sideMenu>
    </tiles:put>
</tiles:insert>
