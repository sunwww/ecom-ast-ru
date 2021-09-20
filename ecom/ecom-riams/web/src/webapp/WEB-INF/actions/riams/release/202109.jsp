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
                                - 14 форма: убраны списки кодов в результирующей выборке, исправлено дублирование строк
                                и добавлен подсчёт общего количества операций без кода
                            </li>
                            <li>
                                - Добавление информации о новорождённом до оформления родов теперь возможно только в
                                последнем СЛО (родовое отделение) в истории пациентки
                            </li>
                            <li>
                                - Механизм быстрого создания пользователей: если создаётся новый пользователь,
                                обязательно необходимо выбрать, у кого копировать роли
                            </li>
                            <li>
                                - Добавлена проверка на null пароля пользователей на уровне приложения и уровне БД
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