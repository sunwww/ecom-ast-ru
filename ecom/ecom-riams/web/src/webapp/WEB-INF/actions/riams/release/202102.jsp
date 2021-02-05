<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@ taglib uri="http://struts.apache.org/tags-tiles" prefix="tiles"%>
<%@ taglib uri="http://www.nuzmsh.ru/tags/msh" prefix="msh"%>
<%@ taglib uri="/WEB-INF/mis.tld" prefix="mis"%>

<tiles:insert page="/WEB-INF/tiles/main${param.short}Layout.jsp" flush="true">

    <tiles:put name='title' type='string'>
    </tiles:put>

    <tiles:put name='body' type='string'>
        <table class='mainMenu'>
            <tr>
                <td class='col1'>
                    <div class='menu'>
                        <% // Версия от 29 января %>
                        <h1>Февраль 2021 года</h1>
                        <ul>
                            <li></li>
                            <li>Изменения в выписке: можно выписывать текущим числом любых пациентов или вчерашней датой умерших/пациентов не инфекционных отделений</li>
                            <li>Печать адреса в направлениях на исследования </li>
                            <li>Назначение SARS-COV2 в НЕинфекционных отделениях:</li>
                            <ul>
                                <li> - номер пробирки обязателен</li>
                                <li> - планируемая дата выполнения может быть любой</li>
                                <li> - необходимо отмечать забор биоматериала</li>
                            </ul>
                            <li>Выписка и удаление данных выписки: настройка приложения по периоду, в рамках которого можно выписывать и удалять выписку</li>
                        </ul>
                    </div>
            </tr>
        </table>
    </tiles:put>
</tiles:insert>