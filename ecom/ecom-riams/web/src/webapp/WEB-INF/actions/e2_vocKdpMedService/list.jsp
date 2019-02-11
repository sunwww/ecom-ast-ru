<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://struts.apache.org/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://www.nuzmsh.ru/tags/msh" prefix="msh" %>
<%@ taglib tagdir="/WEB-INF/tags" prefix="tags" %>
<%@ taglib prefix="ecom" uri="http://www.ecom-ast.ru/tags/ecom" %>
<tiles:insert page="/WEB-INF/tiles/mainLayout.jsp" flush="true">

    <tiles:put name='title' type='string'>
        <msh:title mainMenu="Expert2">Реестры омс</msh:title>
    </tiles:put>

    <tiles:put name='side' type='string'>
        <msh:sideMenu title="Добавить" >
            <msh:sideLink key="ALT+2" action="/entityPrepareCreate-e2_vocKdp" name="Новый КДП" roles="/Policy/E2/Create" />
        </msh:sideMenu>
        <tags:expertvoc_menu currentAction="main"/>
    </tiles:put>

    <tiles:put name='body' type='string'>
        <ecom:webQuery name="entryList" nativeSql="select kdp.id
            ,kdp.code||' '||kdp.name as ksg
            ,list(ms.code||' '||ms.name) as services
             from VocDiagnosticVisit kdp
             left join VocDiagnosticVisitMedService vis on vis.visit_id=kdp.id
             left join vocMedService ms on ms.id=vis.medservice_id
             group by kdp.id, kdp.code, kdp.name
             order by kdp.code, kdp.name
"/>
        <msh:section title='Результат поиска'>
            <msh:table  name="entryList" action="entityView-e2_vocKdp.do" idField="1" disableKeySupport="true" styleRow="6">
                <msh:tableColumn columnName="КПГ" property="2" guid="5b05897f-5dfd-4aee-ada9-d04244ef20c6" />
                <msh:tableColumn columnName="Услуги" property="3" guid="5b05897f-5dfd-4aee-ada9-d04244ef20c6" />
            </msh:table>
        </msh:section>

    </tiles:put>
    <tiles:put name="javascript" type="string">
        <script type="text/javascript" src="./dwr/interface/Expert2Service.js"></script>
        <script type="text/javascript">

        </script>
            </tiles:put>
</tiles:insert>