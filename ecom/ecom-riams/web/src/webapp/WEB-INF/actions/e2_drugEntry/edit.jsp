<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib uri="http://struts.apache.org/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://www.nuzmsh.ru/tags/msh" prefix="msh" %>
<%@ taglib uri="http://www.ecom-ast.ru/tags/ecom" prefix="ecom" %>
<%@ taglib tagdir="/WEB-INF/tags" prefix="tags" %>
<tiles:insert page="/WEB-INF/tiles/mainLayout.jsp" flush="true">

    <tiles:put name="body" type="string">
        <msh:form action="/entityParentSaveGoParentView-e2_drugEntry.do" defaultField="injectDate">
            <msh:hidden property="id" />
            <msh:hidden property="saveType" />
            <msh:hidden property="entry" />
            <msh:panel>
                <msh:row>
                    <msh:textField property="injectDate"/>
                </msh:row>
                <msh:separator colSpan="4" label="Общие"/>
                <msh:row>
                    <msh:autoComplete property="vocSchema" vocName="vocE2FondV030" size="50"/>
                  </msh:row>
                <msh:row>
                    <msh:autoComplete property="drugGroupSchema" vocName="vocE2FondV032BySchema" parentAutocomplete="vocSchema" size="50"/>
                </msh:row>
                <msh:row>
                    <msh:autoComplete property="drug" vocName="vocE2FondN020BySchema" size="50" parentAutocomplete="drugGroupSchema"/>
                    <msh:autoComplete property="injectUnit" vocName="vocE2FondV034" size="50"/>
                </msh:row>
                <msh:row>
                    <msh:autoComplete property="injectMethod" vocName="vocE2FondV035" size="50"/>
                </msh:row>
                <msh:row>
                    <msh:textField property="injectAmount" />
                    <msh:textField property="injectNumber" />
                </msh:row>
                <msh:submitCancelButtonsRow colSpan="1" />
            </msh:panel>
        </msh:form>
    </tiles:put>
    <tiles:put name="title" type="string">
        <ecom:titleTrail mainMenu="Expert2" beginForm="e2_drugEntryForm" />
    </tiles:put>
    <tiles:put name="javascript" type="string">
        <script type="text/javascript" src="./dwr/interface/Expert2Service.js"></script>
        <script type="text/javascript">
            loadSchema();
            function loadSchema() {
                if ($('vocSchemaName') && !$('vocSchema').value && $('drugGroupSchema').value) {
                    Expert2Service.loadDrugSchemaByDrugGroupSchemaId($('drugGroupSchema').value, {
                        callback: function(ret) {
                            if (ret) {
                                ret = JSON.parse(ret);
                                $('vocSchema').value = ret.schemaId;
                                $('vocSchemaName').value = ret.schemaName;

                            }
                        }
                    })
                }
            }
        </script>

    </tiles:put>

    <tiles:put name="side" type="string">
        <msh:ifFormTypeAreViewOrEdit formName="e2_drugEntryForm">
            <msh:sideMenu>
                <msh:sideLink params="id" action="/entityParentEdit-e2_drugEntry" name="Изменить" roles="/Policy/E2/Edit" />
                <msh:sideLink params="id" action="/entityParentDelete-e2_drugEntry" name="Удалить" roles="/Policy/E2/Delete" />
            </msh:sideMenu>
        </msh:ifFormTypeAreViewOrEdit>
    </tiles:put>
</tiles:insert>

