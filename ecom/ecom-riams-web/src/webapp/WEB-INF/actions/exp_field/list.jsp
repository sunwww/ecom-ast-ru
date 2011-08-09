<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ include file="/WEB-INF/tiles/header.jsp" %>
<%@ taglib uri="http://www.nuzmsh.ru/tags/msh" prefix="msh" %>

<tiles:insert page="/WEB-INF/tiles/mainLayout.jsp" flush="true" >

    <tiles:put name='title' type='string'>
        <ecom:titleTrail mainMenu="Document" beginForm="exp_formatForm" title="Список полей"/>
    </tiles:put>

    <tiles:put name='side' type='string'>
        <msh:sideMenu>
            <msh:sideLink key='ALT+1' params="id" action="/entityParentEdit-exp_format" name="К формату" />
            
            <msh:sideLink key='ALT+N' params="id" action="/exp_fieldPrepareCreate" name="Создать новое поле" />

            <msh:sideLink key='ALT+3' params="id" action="/exp_formatRemoveFieldsWithEmptyProperty" name="Удалить поля которые некуда сохранять" />

            <msh:sideLink key='ALT+4' params="id" action="/exp_formatRedirectToImport" name="Загрузить данные" />

        </msh:sideMenu>
    </tiles:put>

    <tiles:put name='body' type='string' >
        <msh:table name="list" action="exp_fieldEdit.do" idField="id">
            <msh:tableColumn columnName="№ пп" property="serialNumber" />
            <msh:tableColumn columnName="Название поля" property="name" />
            <msh:tableColumn columnName="Тип" property="dbfInfo" />
            <msh:tableColumn columnName="Куда сохранять" property="property" />
            <msh:tableColumn columnName="Комментарий" property="comment" />
            <msh:tableColumn columnName="Описание" property="description" />

        </msh:table>
    </tiles:put>


</tiles:insert>