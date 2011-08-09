<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ include file="/WEB-INF/tiles/header.jsp" %>
<%@ taglib uri="http://www.nuzmsh.ru/tags/msh" prefix="msh" %>

<tiles:insert page="/WEB-INF/tiles/mainLayout.jsp" flush="true" >

    <tiles:put name='title' type='string'>
        <h1>Импорт cтруктуры формата из DBF</h1>
    </tiles:put>

    <tiles:put name='side' type='string'>
        <msh:sideMenu>
        </msh:sideMenu>
    </tiles:put>

    <tiles:put name='body' type='string' >
        <msh:form action="exp_formatImportFromDbfSave.do" defaultField="comment" fileTransferSupports="true">
            <msh:hidden property="id" />
            <msh:panel>
                <msh:row>
                    <msh:checkBox property="doNotImportWhatWhen" label='Не импортировать поля WHAT, WHEN, WHO'/>
                </msh:row>
                <msh:row>
                    <td>Файл DBF</td>
                    <td colspan="1">
                        <html:file property="file" />
                    </td>

                </msh:row>
                <msh:submitCancelButtonsRow colSpan="1" />
            </msh:panel>
        </msh:form>
    </tiles:put>

</tiles:insert>