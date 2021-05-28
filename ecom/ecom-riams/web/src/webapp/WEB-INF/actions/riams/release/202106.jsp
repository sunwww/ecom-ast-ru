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
                        <h1>Июнь 2021 года</h1>
                        <ul>
                            <li></li>
                            <li> - Планирование госпитализаций в офтальмологическое отделение:
                            <li></li>
                            <ul>
                                <li> - Добавлена роль для настройки доступности дней для плановой госпитализации</li>
                                <li> - У пользователей с ролью есть 2 кнопки: для открытия и закрытия дня</li>
                                <li> - Для удобства выводится выбранный в календаре день</li>
                                <li> - Если день открыт, можно создать плановую госпитализацию на выбранный день. Если
                                    закрыт, нельзя
                                </li>
                                <li> - При создании и редактировании планирования госпитализации для офтальмологического
                                    отделения проверяется, доступна ли выбранная дата госпитализации
                                </li>
                                <li> - При создании и редактировании введения ингибиторов ангиогенеза для
                                    офтальмологического отделения/при установке даты проверяется, доступна ли выбранная
                                    дата
                                    госпитализации
                                </li>
                            </ul>
                        </ul>
                    </div>
            </tr>
        </table>
    </tiles:put>
</tiles:insert>