<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://struts.apache.org/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://www.nuzmsh.ru/tags/msh" prefix="msh" %>
<%@ taglib uri="/WEB-INF/mis.tld" prefix="mis" %>

<tiles:insert page="/WEB-INF/tiles/main${param.short}Layout.jsp" flush="true">

    <tiles:put name='title' type='string'>
    </tiles:put>

    <tiles:put name='body' type='string'>
        <table class='mainMenu'>
            <tr>
                <td class='col1'>
                    <div class='menu'>
                        <% %>
                        <h1>Август 2021 года</h1>
                        <ul>
                            <li></li>
                            <li>
                                - Добавлен экспорт посещений по дате начала СПО, с потоком обслуживания - только ОМС с
                                передачей id СПО и визита в промеде
                            </li>
                            <li>
                                - Лаборатория: настройка доступности услуги для реанимации изменена на: всегда доступно
                                для реанимаций при типе назначения 24Ч
                            </li>
                            <li>
                                - Добавлена печать согласия на процедуру подготовки шейки матки к родам и
                                родовозбуждения (Печать -> Документов)
                            </li>
                        </ul>
                    </div>
            </tr>
        </table>
    </tiles:put>
</tiles:insert>