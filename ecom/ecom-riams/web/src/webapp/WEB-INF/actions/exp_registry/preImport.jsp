<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ include file="/WEB-INF/tiles/header.jsp" %>
<%@ taglib uri="http://www.nuzmsh.ru/tags/msh" prefix="msh" %>

<tiles:insert page="/WEB-INF/tiles/mainLayout.jsp" flush="true" >

    <tiles:put name='title' type='string'>
        <h1>Импорт реестра. Шаг 1. Загрузка файла.</h1>
    </tiles:put>

    <tiles:put name='side' type='string'>
        <msh:sideMenu>
        </msh:sideMenu>
    </tiles:put>

    <tiles:put name='body' type='string' >
        <msh:form action="exp_regPreImportSave.do" defaultField="format" fileTransferSupports="true">
            <msh:hidden property="saveType" />
            <msh:hidden property="documentAstrakhan" />
            <msh:hidden property="documentForeign" />

            <msh:panel>
                <msh:row>
                    <msh:checkBox property="foreign" label="Иногородние?"/>
                </msh:row>
                <msh:row>
                    <msh:autoComplete property="format" label='Формат импорта' vocName="formatByDocument" fieldColSpan="3" horizontalFill="true"/>
                </msh:row>
                <msh:row>
                    <tr><td></td><td colspan="3">/home/esinev/downloads/in/10p691t.dbf</td></tr>
                    <td class='label'>Файл:</td>
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

            alert($('documentForeign').value) ;

            formatAutocomplete.setParentId($('documentAstrakhan').value);
            $('foreign').onclick=changeFormat ;

            function changeFormat() {
                if($('foreign').checked) {
                    formatAutocomplete.setParentId($('documentForeign').value);
                } else {
                    formatAutocomplete.setParentId($('documentAstrakhan').value);
                }
            }
        </script>
    </tiles:put>

</tiles:insert>