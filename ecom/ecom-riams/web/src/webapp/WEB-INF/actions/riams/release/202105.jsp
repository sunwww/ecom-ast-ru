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
                        <% // Версия от 29 апреля %>
                        <h1>Май 2021 года</h1>
                        <ul>
                            <li></li>
                            <li> - печать согласия на применение методов экстракорпоральной гемокоррекции и
                                фотогемотерапии
                            </li>
                            <li> - температурные листы: возможность ввода температуры и с запятой, и с точкой
                            </li>
                            <li> - Новый вид оценки качества медицинской помощи:
                            <li></li>
                            <ul>
                                <li> - перед оценкой качества должен быть добавлен основной клинический диагноз в СЛО
                                </li>
                                <li> - если для выбранного клинического основного диагноза нет критериев по 203 приказу,
                                    то запускается новый тип оценки
                                </li>
                                <li> - в критериях есть 2 варианта ответа: да и нет, а также возможность ввести
                                    комментарий
                                </li>
                                <li> - по аналогии с 203 приказом врач может создавать черновик экспертной карты,
                                    заведующий его утверждает, либо сразу создаёт экспертную карту
                                </li>
                            </ul>
                        </ul>
                    </div>
            </tr>
        </table>
    </tiles:put>
</tiles:insert>