<%-- ikouzmin 070228: Свойства формата экспорта --%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ include file="/WEB-INF/tiles/header.jsp" %>
<%@ taglib uri="http://www.nuzmsh.ru/tags/msh" prefix="msh" %>

<tiles:insert page="/WEB-INF/tiles/mainLayout.jsp" flush="true">


<tiles:put name='side' type='string'>
    <msh:sideMenu>

        <msh:sideLink key="ALT+1" params="id" action="/entityParentListRedirect-exp_exportformat"
                      name="⇧ Список форматов"/>
        <msh:ifFormTypeAreViewOrEdit formName="exp_exportformatForm">
            <msh:ifInRole roles="/Policy/Exp/ExportFormat/Edit">
                <msh:sideLink params="id" action="/entityParentEdit-exp_exportformat" key="ALT+2" name="Изменить"/>
            </msh:ifInRole>
            <msh:ifInRole roles="/Policy/Exp/ExportFormat/Exec">
                <msh:sideLink action="/exp_exportformat-download" name="Экспорт (XML)" params="id" key="ALT+3"/>
            </msh:ifInRole>
            <msh:ifInRole roles="/Policy/Exp/ExportFormat/Edit">
                <msh:sideLink action="/exp_exportformat-testSql" name="Тестирование запроса" params="id" key="ALT+4"/>

                <msh:sideLink action="/exp_exportformat-testXsl" name="Тестирование преобразования" params="id"
                              key="ALT+5"/>

            </msh:ifInRole>
            <%--msh:sideLink action="/exp_exportFormat-createXsl" name="Создать стандартное преобразование" params="id"
                          key="ALT+6"/--%>

            <msh:sideLink params="id" action="/entityParentDelete-exp_exportformat" key="ALT+DEL"
                          confirm="Удалить формат?" name="Удалить"/>

        </msh:ifFormTypeAreViewOrEdit>


        <%--msh:ifFormTypeAreViewOrEdit formName="exp_formatForm">

            <msh:sideLink params="id" action="/entityParentEdit-exp_format" key="ALT+2" name="Изменить"/>
            <msh:sideLink params="id" action="/exp_formatImportFromDbfEdit" key='ALT+3' name="Импорт полей из DBF"/>

            <%--<msh:sideLink params="id" action="/entityParentList-exp_field" name="Список полей" />-%>
            <msh:sideLink params="id" action="/exp_formatImportFromWordEdit" name="Импорт полей из Word"/>
            <msh:sideLink params="id" confirm="Добавить поля KOD и NAME?" action="/exp_formatDefaultKodNameAdd"
                          name="Добавить поля: KOD и NAME"/>

            <msh:sideLink params="id" action="/exp_checkTablePropertiesEdit" name="Проверки"/>

            <msh:sideLink params="id" action="/exp_formatRedirectToImport" name="Загрузить данные"/>
                   Т

            <msh:sideLink params="id" action="/entityParentDelete-exp_format" key="ALT+DEL"
                          confirm="Удалить формат?" name="Удалить"/>
        </msh:ifFormTypeAreViewOrEdit--%>

    </msh:sideMenu>
</tiles:put>

<tiles:put name='body' type='string'>
    <msh:form action="entityParentSaveGoView-exp_exportformat.do" defaultField="comment">
        <msh:hidden property="id"/>
        <msh:hidden property="saveType"/>
        <msh:hidden property="document"/>

        <msh:panel>
            <msh:row>
                <msh:textField property="comment" label="Комментарий" fieldColSpan="3" horizontalFill="true"/>
            </msh:row>
            <msh:row>
                <msh:textField property="actualDateFrom" label="Дата действия с"/>
                <msh:textField property="actualDateTo" label="по"/>
            </msh:row>
            <msh:row>
                <msh:checkBox property="disabled" label="Отключен"/>
            </msh:row>
            <msh:submitCancelButtonsRow colSpan="4"/>
        </msh:panel>
        <msh:section title="Настройка экспорта">
            <msh:panel>
                <%--msh:row>
                    <msh:checkBox property="native" label="Использовать SQL базы данных"/>
                </msh:row--%>
                <msh:row>
                    <msh:textArea property="query" label="HQL"/>
                </msh:row>
                <msh:row>
                    <msh:textArea property="xslt" label="XSLT"/>
                </msh:row>
            </msh:panel>
        </msh:section>
    </msh:form>

    <%--msh:ifFormTypeIsView formName="exp_exportFormatForm">
        <msh:section title='Список полей'>
            <ecom:parentEntityListAll formName="exp_fieldForm" attribute="fields"/>
            <msh:table name="fields" action="exp_fieldEdit.do" idField="id">
                <msh:tableColumn columnName="№ пп" property="serialNumber"/>
                <msh:tableColumn columnName="Название поля" property="name"/>
                <msh:tableColumn columnName="Тип" property="dbfInfo"/>
                <msh:tableColumn columnName="Куда сохранять" property="property"/>
                <msh:tableColumn columnName="Комментарий" property="comment"/>
                <msh:tableColumn columnName="Описание" property="description"/>
            </msh:table>
        </msh:section>
    </msh:ifFormTypeIsView--%>
</tiles:put>

<tiles:put name='title' type='string'>
    <ecom:titleTrail mainMenu="Document" beginForm="exp_exportformatForm"/>
</tiles:put>

<tiles:put name="javascript" type="string">
    <script type="text/javascript">
        <msh:ifFormTypeIsCreate formName="exp_exportformatForm">
        $('actualDateFrom').value = '01.01.2006';
        </msh:ifFormTypeIsCreate>
    </script>
</tiles:put>

<tiles:put name="style" type="string">
    <style type="text/css">
        .query, .xslt {
            white-space: pre;
            font-family: monospace;
            font-size: 160%;
        }

        #query {
            height: 5em;
            width: 78em;
        }

        #xslt {
            height: 33em;
            width: 78em;
        }
    </style>
</tiles:put>


</tiles:insert>