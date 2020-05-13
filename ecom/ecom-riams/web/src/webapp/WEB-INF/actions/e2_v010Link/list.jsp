<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://struts.apache.org/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://www.nuzmsh.ru/tags/msh" prefix="msh" %>
<%@ taglib tagdir="/WEB-INF/tags" prefix="tags" %>

<tiles:insert page="/WEB-INF/tiles/mainLayout.jsp" flush="true">

    <tiles:put name='title' type='string'>
        <msh:title mainMenu="Expert2">Реестры омс</msh:title>
    </tiles:put>

    <tiles:put name='side' type='string'>
        <msh:sideMenu title="Добавить">
            <msh:sideLink key="ALT+2" action="/entityPrepareCreate-e2_vocMedHelpProfile" name="Сформировать новое" roles="/Policy/E2/Create" />
        </msh:sideMenu>
        <tags:expertvoc_menu currentAction="main"/>
    </tiles:put>

    <tiles:put name='body' type='string'>

        <msh:hideException>
            <msh:section title='Результат поиска'>
                <msh:table  name="list" action="entityView-e2_vocMedHelpProfile.do" idField="id" disableKeySupport="true">
                    <msh:tableColumn columnName="Название" property="name" />
                    <msh:tableColumn columnName="Код" identificator="false" property="code" />
                    <msh:tableColumn columnName="profileK" identificator="false" property="profileK" />
                </msh:table>
            </msh:section>
        </msh:hideException>
    </tiles:put>
</tiles:insert>