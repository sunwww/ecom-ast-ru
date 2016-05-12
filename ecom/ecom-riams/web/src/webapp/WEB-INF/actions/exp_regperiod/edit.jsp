<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ include file="/WEB-INF/tiles/header.jsp" %>
<%@ taglib uri="http://www.nuzmsh.ru/tags/msh" prefix="msh" %>

<tiles:insert page="/WEB-INF/tiles/mainLayout.jsp" flush="true" >

    <tiles:put name='title' type='string'>
        <h1>/ Периоды</h1>
    </tiles:put>

    <tiles:put name='side' type='string'>
        <msh:sideMenu>

            <msh:sideLink key="ALT+1" params="" action="/entityList-exp_regperiod" name="⇧ Список периодов" />
            <%--<msh:sideLink key="ALT+2" params="id" action="/importDataList" name="Данные" />--%>
            <%--<msh:sideLink key="ALT+3" params="id" action="/messageList" name="Список сообщений о проверке" />--%>
            <%--<msh:sideLink key="ALT+4" params="id" action="/importtimeCheck" name="Проверить" />--%>

            <msh:sideLink params="id" confirm="Удалить?" action="/entityDelete-exp_regperiod" name="Удалить" />
        </msh:sideMenu>
    </tiles:put>

    <tiles:put name='body' type='string' >
        <msh:form action="entitySave-exp_regperiod.do" defaultField="dateFrom">
            <msh:hidden property="id" />
            <msh:hidden property="saveType" />

            <msh:panel>
                <msh:row>
                    <msh:textField property="dateFrom" label="Дата с" />
                    <msh:textField property="dateTo" label="Дата по" />
                </msh:row>
                <msh:submitCancelButtonsRow colSpan="4" />
            </msh:panel>
        </msh:form>
    </tiles:put>


</tiles:insert>