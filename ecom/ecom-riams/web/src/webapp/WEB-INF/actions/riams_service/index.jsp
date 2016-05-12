<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://struts.apache.org/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://www.nuzmsh.ru/tags/msh" prefix="msh" %>

<tiles:insert page="/WEB-INF/tiles/mainLayout.jsp" flush="true">

    <tiles:put name='title' type='string'>
        <h1>Сервис</h1>
    </tiles:put>


    <tiles:put name='body' type='string'>
        <ul class='mainMenu'>
            <li>
                <a href='serviceImportPoliciesListEdit.do'>Импорт списка политик безопасноти</a>
            </li>
            <li>
                <a href='serviceImportRolesEdit.do'>Импорт из файла roles.properties</a>
            </li>
            <li>
                <a href='serviceExport.do'>Экспорт</a>
            </li>
            <li>
                <a href='formCustomizeServletConfig'>Настройка форм</a>
            </li>
            <li>
            	<a href='ecom_hibernateCacheConfig.do'>Кэш для hibernate</a>
            </li>
        </ul>
    </tiles:put>

    <tiles:put name='side' type='string'>
    </tiles:put>

    <tiles:put name="style" type="string">
        <style type="text/css">
            ul.mainMenu li {
                margin: 1em ;
            }
        </style>
    </tiles:put>

    <tiles:put name="javascript" type="string">
        <script type="text/javascript">
            Element.addClassName($('mainMenuService'), "selected");
        </script>
    </tiles:put>

</tiles:insert>