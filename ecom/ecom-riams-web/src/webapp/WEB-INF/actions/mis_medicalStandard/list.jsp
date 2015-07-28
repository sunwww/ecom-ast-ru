<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://struts.apache.org/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://www.nuzmsh.ru/tags/msh" prefix="msh" %>
<%@ taglib uri="http://www.ecom-ast.ru/tags/ecom" prefix="ecom" %>

<tiles:insert page="/WEB-INF/tiles/mainLayout.jsp" flush="true" >

    <tiles:put name='title' type='string'>
        <msh:title mainMenu="Lpu">Список стандартов мед. обслуживания</msh:title>
    </tiles:put>

    <tiles:put name='side' type='string'>
        <msh:sideMenu>
            <msh:sideLink key='ALT+N' action="/entityPrepareCreate-mis_medicalStandard" name="Создать новый стандарт" />
        </msh:sideMenu>
    </tiles:put>

    <tiles:put name='body' type='string' >
<msh:section>
<msh:sectionContent>
<ecom:webQuery name="list" nativeSql="select id, name, code, startDate from medicalStandard order by name"/>
<msh:table name="list" action="entityView-mis_medicalStandard.do" idField="1">
            <msh:tableColumn columnName="Название" property="2"/>
            <msh:tableColumn columnName="Код" property="3"/>
            <msh:tableColumn columnName="Дата начала действия" property="4"/>
        </msh:table>
</msh:sectionContent>
</msh:section>
        </tiles:put>

</tiles:insert>