<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ include file="/WEB-INF/tiles/header.jsp" %>
<%@ taglib uri="http://www.nuzmsh.ru/tags/msh" prefix="msh" %>

<tiles:insert page="/WEB-INF/tiles/mainLayout.jsp" flush="true" >

    <tiles:put name='title' type='string'>
        <msh:title mainMenu="Document">Список документов</msh:title>
    </tiles:put>

    <tiles:put name='side' type='string'>
        <msh:sideMenu>
            <msh:sideLink key='ALT+N' params="" action="/entityPrepareCreate-exp_importdocument.do" name="Создать документ" />
        </msh:sideMenu>
    </tiles:put>

    <tiles:put name='body' type='string' >
        <msh:table name="list" action="entityView-exp_importdocument.do" idField="id">
            <msh:tableColumn columnName="Ключ импорта" property="keyName" />
            <msh:tableColumn columnName="Комментарий" property="comment" />
            <msh:tableColumn columnName="Класс" property="prettyClassName" />
            <msh:tableColumn columnName="Поддержка импорта по времени" property="importTimeSupports" />

        </msh:table>
    </tiles:put>

    <tiles:put name="javascript" type="string">
        <script type="text/javascript">
            function update() {
                window.location = "entityList-exp_importdocument.do" ;
            }
//            setTimeout(update,300) ;
        </script>
    </tiles:put>


</tiles:insert>