<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ include file="/WEB-INF/tiles/header.jsp" %>
<%@ taglib uri="http://www.nuzmsh.ru/tags/msh" prefix="msh" %>

<tiles:insert page="/WEB-INF/tiles/mainLayout.jsp" flush="true" >

    <tiles:put name='title' type='string'>
        <h1>Импорт из Word</h1>
    </tiles:put>

    <tiles:put name='side' type='string'>
        <msh:sideMenu>
            <msh:sideLink params="id" action="/entityParentDelete-exp_format" name="Удалить" />
            <msh:sideLink params="id" action="/entityParentList-exp_field" name="Список полей" />
        </msh:sideMenu>
    </tiles:put>

    <tiles:put name='body' type='string' >
        <msh:form action="exp_formatImportFromWordSave.do" defaultField="comment">
            <msh:hidden property="id" />

            <p>Скопируйте текст из таблицы Word</p>
            <msh:panel>
                <msh:row>
                    <msh:textArea label="Текст для импорта" property="text" />
                </msh:row>
                <msh:submitCancelButtonsRow colSpan="1" />
            </msh:panel>
        </msh:form>
    </tiles:put>


</tiles:insert>