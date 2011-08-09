<%-- ikouzmin 070308: Свойства формата импорта --%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ include file="/WEB-INF/tiles/header.jsp" %>
<%@ taglib uri="http://www.nuzmsh.ru/tags/msh" prefix="msh" %>

<tiles:insert page="/WEB-INF/tiles/mainLayout.jsp" flush="true">


    <tiles:put name='side' type='string'>
        <msh:sideMenu>

            <msh:sideLink key="ALT+1" params="id" action="/entityParentListRedirect-exp_importformat"
                          name="⇧ Список форматов"/>
            <msh:ifFormTypeAreViewOrEdit formName="exp_importformatForm">
                <msh:sideLink params="id" action="/entityParentEdit-exp_importformat" key="ALT+2" name="Изменить"/>
                <msh:ifInRole roles="/Policy/Exp/ImportFormat/Exec">
                <msh:sideLink action="/exp_importformatRedirectToImport" name="Импорт (XML)" params="id" key="ALT+3"/>
                </msh:ifInRole>
                <msh:sideLink params="id" action="/entityParentDelete-exp_importformat" key="ALT+DEL"
                              confirm="Удалить формат?" name="Удалить"/>

            </msh:ifFormTypeAreViewOrEdit>

        </msh:sideMenu>
    </tiles:put>

    <tiles:put name='body' type='string'>
        <msh:form action="entityParentSaveGoView-exp_importformat.do" defaultField="comment">
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
            <msh:section title="Настройка импорта">
                <msh:textArea property="config" label="XML: " size="180"/>
            </msh:section>
        </msh:form>

    </tiles:put>

    <tiles:put name='title' type='string'>
        <ecom:titleTrail mainMenu="Document" beginForm="exp_importformatForm"/>
    </tiles:put>

    <tiles:put name="javascript" type="string">
        <script type="text/javascript">
            <msh:ifFormTypeIsCreate formName="exp_importformatForm">
                    $('actualDateFrom').value ='01.01.2006' ;
            </msh:ifFormTypeIsCreate>
        </script>
    </tiles:put>

    <tiles:put name="style" type="string">
        <style type="text/css">
            .viewOnlyTextField {
                font-family: monospace;
                white-space: pre;
                font-size: 160%;
            }
            #config {
                height:40em;
                width:80em;
            }
        </style>
    </tiles:put>


</tiles:insert>