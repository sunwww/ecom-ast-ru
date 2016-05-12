
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ include file="/WEB-INF/tiles/header.jsp" %>
<%@ taglib uri="http://www.nuzmsh.ru/tags/msh" prefix="msh" %>

<tiles:insert page="/WEB-INF/tiles/mainLayout.jsp" flush="true" >

    <tiles:put name='title' type='string'>
        <h1>/ Документ / Импорт данных</h1>
    </tiles:put>

    <tiles:put name='side' type='string'>
        <msh:sideMenu>
            <msh:sideLink key="ALT+1" params="id" action="/entityParentList-exp_importtime" name="⇧ К списку импортированных" />
        </msh:sideMenu>
    </tiles:put>

    <tiles:put name='body' type='string' >
        <msh:form action="exp_importTimeUploadSave.do" defaultField="comment" fileTransferSupports="true">
            <msh:hidden property="saveType" />
            <msh:hidden property="document" />

            <msh:panel>
                <msh:row>
                    <msh:textField property="comment" label="Комментарий" fieldColSpan="3" horizontalFill="true"/>
                </msh:row>
                <msh:row>
                    <msh:autoComplete showId='true' parentId='exp_importTimeUploadForm.document' property="format" label='Формат импорта' vocName="formatByDocument" fieldColSpan="3" horizontalFill="true"/>
                </msh:row>
                <msh:row>
                    <msh:textField property="actualDateFrom" label="Дата действия с" />
                    <msh:textField property="actualDateTo" label="Дата действия по" />
                </msh:row>

                <msh:row>
                    <td>Файл</td>
                    <td colspan="3">
                        <html:file property="file" />
                    </td>
                </msh:row>
                <msh:submitCancelButtonsRow colSpan="4" labelSave="Импортировать" labelSaving="Импорт файла ..."/>
            </msh:panel>
        </msh:form>
    </tiles:put>

    <tiles:put name="javascript" type="string">
        <script type="text/javascript">
            //formatAutocomplete.setParentId($('document').value);
        </script>
    </tiles:put>


</tiles:insert>