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
                        </ul>
                    </div>
            </tr>
        </table>
    </tiles:put>
</tiles:insert>