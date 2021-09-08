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
                        <h1>Сентябрь 2021 года</h1>
                        <ul>
                            <li></li>
                            <li>
                                - Забор биоматериала: отменена автоматическая перезагрузка страницы после каждого приёма
                                биоматериала
                            </li>
                            <li>
                                - 14 форма: убраны списки кодов в результирующей выборке и исправлено дублирование строк
                            </li>
                            <li>
                                - Экспорт посещений в Промед:
                            </li>
                            <li></li>
                            <ul>
                                <li>
                                    - Добавлен вывод кодов промеда поликлинических случаев в СПО и визите (доступно
                                    только пользователям с соответствующей ролью)
                                </li>
                            </ul>
                        </ul>
                    </div>
            </tr>
        </table>
    </tiles:put>
</tiles:insert>