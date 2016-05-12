<%@ page import="ru.ecom.jaas.ejb.form.SecUserForm" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://struts.apache.org/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html" %>
<%@ taglib uri="http://www.nuzmsh.ru/tags/msh" prefix="msh" %>

<tiles:insert page="/WEB-INF/tiles/mainLayout.jsp" flush="true">

    <tiles:put name='title' type='string'>
        <h1>Импорт списка политик</h1>
    </tiles:put>


    <tiles:put name='body' type='string'>
        <msh:form action="serviceImportPoliciesList.do" defaultField="file" fileTransferSupports="true">
            <msh:hidden property="saveType"/>

            <msh:panel>
                <msh:row>
                    <td>Файл</td>
                    <td colspan="1">
                        <html:file property="file"/>
                    </td>
                </msh:row>

                <msh:submitCancelButtonsRow colSpan="4"/>
            </msh:panel>
        </msh:form>
    </tiles:put>

    <tiles:put name='side' type='string'>
        <msh:sideMenu>

        </msh:sideMenu>
    </tiles:put>

    <tiles:put name="javascript" type="string">
        <script type="text/javascript">
            Element.addClassName($('mainMenuService'), "selected");
        </script>
    </tiles:put>

</tiles:insert>