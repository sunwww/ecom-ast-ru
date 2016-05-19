<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://struts.apache.org/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://www.nuzmsh.ru/tags/msh" prefix="msh" %>
<%@ taglib uri="/WEB-INF/mis.tld" prefix="mis" %>

<tiles:insert page="/WEB-INF/tiles/mainLayout.jsp" flush="true">

    <tiles:put name='title' type='string'>
        <msh:title mainMenu="Poly">Поликлиника</msh:title>
    </tiles:put>

    <tiles:put name='body' type='string'>
        <table class='mainPoly'>
        <tr>
        <td class='col1'>


            <div class='menu'>
                <h2>Поликлиника</h2>
                <ul>
                    <li>
                        <msh:link action="js-smo_direction-findDirections.do" >
                            Журнал направлений
                        </msh:link>
                    </li>
                    <li>
                        <msh:link action="js-smo_direction-findPolyAdmissions.do" >
                            Журнал приема
                        </msh:link>
                    </li>
                </ul>
            </div>
        </td>
        </tr>
        </table>
    </tiles:put>
</tiles:insert>