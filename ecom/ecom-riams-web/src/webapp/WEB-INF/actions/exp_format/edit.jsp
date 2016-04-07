<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ include file="/WEB-INF/tiles/header.jsp" %>
<%@ taglib uri="http://www.nuzmsh.ru/tags/msh" prefix="msh" %>

<tiles:insert page="/WEB-INF/tiles/mainLayout.jsp" flush="true">


    <tiles:put name='side' type='string'>
        <msh:sideMenu>

            <msh:sideLink key="ALT+1" params="id" action="/entityParentListRedirect-exp_format"
                          name="⇧ Список форматов"/>
            <msh:ifFormTypeAreViewOrEdit formName="exp_formatForm">

                <msh:sideLink params="id" action="/entityParentEdit-exp_format" key="ALT+2" name="Изменить"/>
                <msh:sideLink params="id" action="/exp_formatImportFromDbfEdit" key='ALT+3' name="Импорт полей из DBF"/>

                <%--<msh:sideLink params="id" action="/entityParentList-exp_field" name="Список полей" />--%>
                <msh:sideLink params="id" action="/exp_formatImportFromWordEdit" name="Импорт полей из Word"/>
                <msh:sideLink params="id" confirm="Добавить поля KOD и NAME?" action="/exp_formatDefaultKodNameAdd"
                              name="Добавить поля: KOD и NAME"/>

                <msh:sideLink params="id" action="/exp_checkTablePropertiesEdit" name="Проверки"/>

                <msh:sideLink params="id" action="/exp_formatRedirectToImport" name="Загрузить данные"/>

                <msh:sideLink key='ALT+N' params="id" action="/exp_fieldPrepareCreate" name="Создать новое поле" />

                <msh:sideLink params="id" action="/entityParentDelete-exp_format" key="ALT+DEL"
                              confirm="Удалить формат?" name="Удалить"/>
            </msh:ifFormTypeAreViewOrEdit>

        </msh:sideMenu>
    </tiles:put>

    <tiles:put name='body' type='string'>
        <msh:form action="entityParentSaveGoView-exp_format.do" defaultField="comment">
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
                <msh:row>
                    <msh:checkBox property="systemFormat" label="Системный"/>
                </msh:row>
                <msh:submitCancelButtonsRow colSpan="4"/>
            </msh:panel>
        </msh:form>

        <msh:ifFormTypeIsView formName="exp_formatForm">
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
        </msh:ifFormTypeIsView>
    </tiles:put>

    <tiles:put name='title' type='string'>
        <ecom:titleTrail mainMenu="Document" beginForm="exp_formatForm"/>
    </tiles:put>

    <tiles:put name="javascript" type="string">
        <script type="text/javascript">
            <msh:ifFormTypeIsCreate formName="exp_formatForm">
                    $('actualDateFrom').value ='01.01.2006' ;
            </msh:ifFormTypeIsCreate>
        </script>
    </tiles:put>


</tiles:insert>