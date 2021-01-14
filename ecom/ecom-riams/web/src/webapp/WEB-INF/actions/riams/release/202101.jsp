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
                        <% // Версия от 29 декабря %>
                        <h1>Январь 2021 года</h1>
                        <ul>
                            <li></li>
                            <li>Изменения протокола трансфузиологии согласно служебной записке</li>
                            <li>Печать медицинской карты при беременности из СЛС</li>
                            <li>Протокол трансфузий:</li>
                            <ul>
                                <li> - возможность не вносить данные об индивидуальном подборе (дату исследования и заключение) в случае, если индивиудальный подбор не проводился</li>
                                <li> - для компонентов крови с эритроцитами проверка на плоскости не выполняется</li>
                            </ul>
                        </ul>
                    </div>
            </tr>
        </table>
    </tiles:put>
</tiles:insert>