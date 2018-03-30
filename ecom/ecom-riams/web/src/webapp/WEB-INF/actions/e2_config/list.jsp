<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://struts.apache.org/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://www.nuzmsh.ru/tags/msh" prefix="msh" %>
<%@ taglib tagdir="/WEB-INF/tags" prefix="tags" %>

<tiles:insert page="/WEB-INF/tiles/mainLayout.jsp" flush="true">

    <tiles:put name='title' type='string'>
        <msh:title mainMenu="Expert2">Реестры омс</msh:title>
    </tiles:put>

    <tiles:put name='side' type='string'>
        <msh:sideMenu title="Добавить" guid="fdcda21a-c1c6-4e0e-a74e-1bf843a8c1c8">
            <msh:sideLink key="ALT+2" action="/entityPrepareCreate-e2_config.do" name="Создать" roles="/Policy/E2/Create" />
        </msh:sideMenu>
        <tags:expertvoc_menu currentAction="main"/>
    </tiles:put>

    <tiles:put name='body' type='string'>

        <msh:hideException>
            <msh:section title='Результат поиска'>
                <msh:table  name="list" action="entityView-e2_config.do" idField="id" disableKeySupport="true">
                    <msh:tableColumn columnName="Название" property="name" guid="8c2a3f9b-89d7-46a9-a8c3-c08029ec047e" />
                    <msh:tableColumn columnName="Код"  property="code" guid="5b05897f-5dfd-4aee-ada9-d04244ef20c6" />
                    <msh:tableColumn columnName="Значение" identificator="false" property="value" guid="5b05897f-5dfd-4aee-ada9-d04244ef20c6" />
                </msh:table>
            </msh:section>
        </msh:hideException>
    </tiles:put>
</tiles:insert>