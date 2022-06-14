<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib uri="http://struts.apache.org/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://www.nuzmsh.ru/tags/msh" prefix="msh" %>
<%@ taglib uri="http://www.ecom-ast.ru/tags/ecom" prefix="ecom" %>
<%@ taglib tagdir="/WEB-INF/tags" prefix="tags" %>
<tiles:insert page="/WEB-INF/tiles/main${param.short}Layout.jsp" flush="true">
    <tiles:put name="title" type="string">
        <ecom:titleTrail mainMenu="Expert2" beginForm="e2_entryMedServiceMedImplantForm" />
    </tiles:put>

    <tiles:put name="body" type="string">
        <msh:form action="/entityParentSaveGoParentView-e2_entryMedServiceMedImplant.do" defaultField="serialNumber">
            <msh:hidden property="id" />
            <msh:hidden property="saveType" />
            <msh:hidden property="medService" />
            <msh:panel>
     <msh:separator colSpan="4" label="Общие"/>
                <msh:row>
                    <msh:textField property="serialNumber"  size="50"/>
                </msh:row>
                <msh:row>
                    <msh:autoComplete property="typeCode" vocName="vocSurgicalImplantCode" size="50" horizontalFill="true" fieldColSpan="3"/>
                </msh:row>
                <msh:submitCancelButtonsRow colSpan="1" />
            </msh:panel>
        </msh:form>
    </tiles:put>

    <tiles:put name="javascript" type="string">
        <msh:ifFormTypeAreViewOrEdit formName="e2_entryMedServiceMedImplantForm">
            <script type="text/javascript" src="./dwr/interface/Expert2Service.js"></script>

            <script type="text/javascript">

            </script>
        </msh:ifFormTypeAreViewOrEdit>

    </tiles:put>

    <tiles:put name="side" type="string">
        <msh:ifFormTypeIsView formName="e2_entryMedServiceMedImplantForm">
            <msh:sideMenu>
                <msh:sideLink params="id" action="/entityParentEdit-e2_entryMedServiceMedImplant" name="Изменить" roles="/Policy/E2/Edit" />
                <msh:sideLink key="ALT+DEL" confirm="Удалить?" params="id" action="/entityParentDeleteGoParentView-e2_entryMedServiceMedImplant" name="Удалить" roles="/Policy/E2/Delete" />

            </msh:sideMenu>
        </msh:ifFormTypeIsView>
    </tiles:put>
</tiles:insert>

