<%--
    ikouzmin 070308 +++ Форма загрузки XML файла
--%>

<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ include file="/WEB-INF/tiles/header.jsp" %>
<%@ taglib uri="http://www.nuzmsh.ru/tags/msh" prefix="msh" %>

<tiles:insert page="/WEB-INF/tiles/mainLayout.jsp" flush="true" >

    <tiles:put name='title' type='string'>
        <%--<ecom:titleTrail mainMenu="Document" beginForm="exp_importformatForm"/>--%>
        <h1>/ Документ / Импорт данных (XML)</h1>
    </tiles:put>

    <tiles:put name='side' type='string'>
        <msh:sideMenu>
            <msh:sideLink key="ALT+1" params="id" action="/entityParentList-exp_importtime" name="⇧ К списку импортированных" />
        </msh:sideMenu>
    </tiles:put>

    <tiles:put name='body' type='string' >
        <msh:form action="exp_importformatUploadSave.do" defaultField="comment" fileTransferSupports="true">
            <msh:hidden property="saveType" />
            <msh:hidden property="document" />

            <msh:panel>
                <msh:row>
                    <msh:textField property="comment" label="Комментарий" fieldColSpan="3" horizontalFill="true"/>
                </msh:row>
                <msh:row>
                    <msh:autoComplete showId='true' parentId='exp_importformatUploadForm.document' property="format" label='Формат импорта' vocName="importFormatByDocument" fieldColSpan="3" horizontalFill="true"/>
                </msh:row>
                <msh:row>
                    <msh:textField property="actualDateFrom" label="Дата действия с" />
                    <msh:textField property="actualDateTo" label="Дата действия по" />
                </msh:row>

                <msh:row>
                    <td align="right">Файл:</td>
                    <td colspan="3">
                        <html:file property="file" />
                    </td>
                </msh:row>
                <msh:row>
                    <msh:row>
                        <msh:checkBox property="verifyAfterSave" label="Проверять после сохранения" fieldColSpan="3"/>                </msh:row>
                    <msh:checkBox property="updateModifiedOnly" label="Обновлять только измененные записи" fieldColSpan="3"/>                </msh:row>
                <msh:row>
                    <msh:checkBox property="viewLog" label="Показать журнал" fieldColSpan="3"/>
                </msh:row>
                <msh:row>
                    <msh:checkBox property="debug" label="Отладка импорта" fieldColSpan="3"/>
                </msh:row>
                <msh:submitCancelButtonsRow colSpan="4" labelSave="Импортировать" labelSaving="Импорт файла ..."/>
            </msh:panel>
        </msh:form>
    </tiles:put>

    <tiles:put name="javascript" type="string">
        <script type="text/javascript">
            $('viewLog').checked=true;
            $('verifyAfterSave').checked = true;
            $('updateModifiedOnly').checked = true;
                    
            $('debug').onclick = function () {
                if ($('debug').checked) {
                    $('viewLog').checked = true;
                    $('verifyAfterSave').checked = false;
                }
            }
            $('viewLog').onclick = function () {
                if (!$('viewLog').checked) {
                    $('debug').checked = false;
                }
            }
            $('verifyAfterSave').onclick = function () {
                if ($('verifyAfterSave').checked) {
                    $('debug').checked = false;
                }
            }
        </script>
    </tiles:put>


</tiles:insert>