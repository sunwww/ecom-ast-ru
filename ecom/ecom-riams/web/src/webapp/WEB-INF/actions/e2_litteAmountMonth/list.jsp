<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://struts.apache.org/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://www.nuzmsh.ru/tags/msh" prefix="msh" %>
<%@ taglib uri="http://www.ecom-ast.ru/tags/ecom" prefix="ecom" %>
<%@ taglib tagdir="/WEB-INF/tags" prefix="tags" %>

<tiles:insert page="/WEB-INF/tiles/mainLayout.jsp" flush="true">
    <tiles:put name="title" type="string">
        <msh:title mainMenu="Expert2">Настроечная таблица - месяца и объемы</msh:title>
    </tiles:put>
    <tiles:put name='body' type='string' >
        <msh:table name="list" action="entityEdit-e2_litteAmountMonth.do" idField="id">
            <msh:tableColumn columnName="Количество" property="amount" />
            <msh:tableColumn columnName="Месяцы" property="months" />
        </msh:table>
    </tiles:put>
    <tiles:put name='side' type='string'>
        <msh:sideMenu title="Добавить" >
            <msh:sideLink key='ALT+N' action="/entityPrepareCreate-e2_litteAmountMonth" name="Создать новый" roles="/Policy/E2/Create" />
        </msh:sideMenu>
        <tags:expertvoc_menu currentAction="e2_litteAmountMonth_st"/>
    </tiles:put>


    <tiles:put name="javascript" type="string">
        <msh:ifFormTypeIsView formName="e2_litteAmountMonthForm">
            <script type="text/javascript" src="./dwr/interface/Expert2Service.js"></script>
            <script type="text/javascript">
                function addHistoryNumberToList() {

                }

            </script>

        </msh:ifFormTypeIsView>
    </tiles:put>
</tiles:insert>