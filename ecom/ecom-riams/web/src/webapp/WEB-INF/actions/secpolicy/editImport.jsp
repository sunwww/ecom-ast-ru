<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://struts.apache.org/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html" %>
<%@ taglib uri="http://www.nuzmsh.ru/tags/msh" prefix="msh" %>
<%@ taglib tagdir="/WEB-INF/tags" prefix="tags" %>

<tiles:insert page="/WEB-INF/tiles/mainLayout.jsp" flush="true">

    <tiles:put name='title' type='string'>
        <msh:title title="Импорт списка политик"/>
    </tiles:put>


    <tiles:put name='body' type='string'>
        <msh:form action="/servicePolicy-import.do" defaultField="file" fileTransferSupports="true">
            <msh:hidden property="saveType"/>

            <msh:panel>
                <msh:row>
                    <td>Файл *.xml</td>
                    <td colspan="1">
                        <html:file property="file" size="50" />
                    </td>
                </msh:row>

                <msh:submitCancelButtonsRow colSpan="4"/>
            </msh:panel>
        </msh:form>
    </tiles:put>

    <tiles:put name='side' type='string'>
        <msh:sideMenu>

        </msh:sideMenu>
        <tags:menuJaas currentAction="policies"/>
    </tiles:put>

    <tiles:put name="javascript" type="string">
        <script type="text/javascript">
            Element.addClassName($('mainMenuPolicies'), "selected");
        </script>
    </tiles:put>

</tiles:insert>