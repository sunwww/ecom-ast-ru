<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ include file="/WEB-INF/tiles/header.jsp" %>
<%@ taglib uri="http://www.nuzmsh.ru/tags/msh" prefix="msh" %>

<tiles:insert page="/WEB-INF/tiles/mainLayout.jsp" flush="true">


    <tiles:put name='side' type='string'>
        <msh:sideMenu>
            <msh:sideLink confirm="Удалить поле?" params="id" action="/entityParentDelete-exp_field" name="Удалить"/>
        </msh:sideMenu>
    </tiles:put>

    <tiles:put name='body' type='string'>
        <msh:form action="/entityParentSave-exp_field.do" defaultField="propertyName">
            <msh:hidden property="id"/>
            <msh:hidden property="saveType"/>
            <msh:hidden property="format"/>
            <msh:hidden property="document"/>

            <msh:panel>

                <msh:row>
                    <msh:autoComplete property="property" label="Поле для сохранения" fieldColSpan="3"
                                      horizontalFill="true" vocName="importDocumentProperties"
                                      showId='true' parentId='exp_fieldForm.document'
                                      />

                </msh:row>

                <msh:row>
                    <msh:textField property="comment" label="Комментарий" fieldColSpan="3" horizontalFill="true"
                                   size="50"/>
                </msh:row>

                <msh:separator colSpan="4" label="DBF: Описание поля"/>

                <msh:row>
                    <msh:textField property="serialNumber" label="Порядковый номер поля"/>
                    <msh:textField property="defaultValue" label="Значение по-умолчанию"/>
                </msh:row>

                <msh:row>
                    <msh:textField property="name" label="Название поля" />
                    <msh:autoComplete property="dbfType" label="Тип поля" vocName="dbfField"
                                      
                            />
                </msh:row>

                <msh:row>
                    <msh:textField property="dbfSize" label="Размер поля"/>
                    <msh:textField property="dbfDecimal" label="Знаков после запятой"/>
                </msh:row>

                <msh:separator colSpan="4" label="Описание"/>
                <msh:row>
                    <msh:textArea fieldColSpan="3" property="description" label="Описание" horizontalFill="true"/>
                </msh:row>

                <msh:submitCancelButtonsRow colSpan="4"/>
            </msh:panel>
        </msh:form>
    </tiles:put>

    <tiles:put name='title' type='string'>
        <ecom:titleTrail mainMenu="Document" beginForm="exp_fieldForm" />
    </tiles:put>
    
</tiles:insert>