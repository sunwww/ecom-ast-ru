<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://struts.apache.org/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://www.nuzmsh.ru/tags/msh" prefix="msh" %>
<%@ taglib uri="/WEB-INF/mis.tld" prefix="mis" %>

<tiles:insert page="/WEB-INF/tiles/mainLayout.jsp" flush="true">

    <tiles:put name='title' type='string'>
        <msh:title mainMenu="Lpu">ЛПУ</msh:title>
    </tiles:put>

    <tiles:put name='body' type='string'>
        <table class='mainMenu'>
        <tr>
        <td class='col1'>


            <div class='menu'>
                <h2>Правила</h2>
                <ul>
                    <li>
                        <msh:link action="entityList-checkrule.do" >
                            Библиотека правил
                        </msh:link>
                    </li>
                    <li>
                        <msh:link action="checkrulePrepareCreate.do" >
                            Создать новое правило
                        </msh:link>
                    </li>
                </ul>
            </div>

            <div class='menu'>
                <h2>Настройка правил проверок</h2>
                <ul>
                    <li>
                        <msh:link action="entityList-stat_checkGroup.do" >
                            Группа правил проверок
                        </msh:link>
                    </li>
                    <li>
                        <msh:link action="entityList-checkproperty.do" >
                            Справочник доступных свойств
                        </msh:link>
                    </li>
                </ul>
            </div>
        </td>
        </tr>
        </table>
    </tiles:put>
</tiles:insert>