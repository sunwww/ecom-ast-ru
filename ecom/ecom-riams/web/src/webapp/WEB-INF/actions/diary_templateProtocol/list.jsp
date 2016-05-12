<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://struts.apache.org/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://www.nuzmsh.ru/tags/msh" prefix="msh" %>

<tiles:insert page="/WEB-INF/tiles/mainLayout.jsp" flush="true" >

    <tiles:put name='title' type='string'>
        <msh:title mainMenu="Template">Шаблоны протоколов</msh:title>
    </tiles:put>

    <tiles:put name='side' type='string'>
        <msh:sideMenu>
            <msh:sideLink key='ALT+N' params="" action="/entityPrepareCreate-diary_templateProtocol" name="Добавить шаблон протокола" />
            <msh:sideLink key="ALT+1" params="" action="/entityList-diary_templateClassif" name="Список классификаторов" roles="/Policy/Diary/Template/Classif/View"/>
            <msh:sideLink key='ALT+2' params="" action="/entityList-diary_templateword" name="Список ключевых слов" roles="/Policy/Diary/KeyWord/View"/>
        </msh:sideMenu>
    </tiles:put>
    
    <tiles:put name='body' type='string' >
        <msh:table name="list" action="entityView-diary_templateProtocol.do" idField="id">
            <msh:tableColumn columnName="Заголовок" property="title"/>
            <msh:tableColumn columnName="Текст протокола" property="text"/>
        </msh:table>
    </tiles:put>

</tiles:insert>