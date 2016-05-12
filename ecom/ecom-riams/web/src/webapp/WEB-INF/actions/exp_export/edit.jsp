<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ include file="/WEB-INF/tiles/header.jsp" %>
<%@ taglib uri="http://www.nuzmsh.ru/tags/msh" prefix="msh" %>
<%@ taglib uri="http://www.ecom-ast.ru/tags/ecom" prefix="ecom" %>

<tiles:insert page="/WEB-INF/tiles/mainLayout.jsp" flush="true" >



    <tiles:put name='body' type='string' >
        <msh:form action="exp_exportSave.do" defaultField="formatName">
            <msh:hidden property="saveType" />
            <msh:hidden property="document" />

            <msh:panel>
                <msh:row>
                    <msh:autoComplete parentId='exp_exportForm.document' property="format" label="Формат"  size='60' vocName="formatByDocument"/>
                </msh:row>

                <msh:submitCancelButtonsRow labelSave='Экспорт' labelSaving='Экспорт...' colSpan="2" />
            </msh:panel>
        </msh:form>
    </tiles:put>

    <tiles:put name='side' type='string'>
    </tiles:put>

    <tiles:put name='title' type='string'>
    </tiles:put>

    <tiles:put name="javascript" type="string">
        <script type="text/javascript">
            formatAutocomplete.setParentId($('document').value);
        </script>
    </tiles:put>

</tiles:insert>