<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://struts.apache.org/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://www.nuzmsh.ru/tags/msh" prefix="msh" %>
<%@ taglib uri="http://www.ecom-ast.ru/tags/ecom" prefix="ecom" %>
<%@ taglib tagdir="/WEB-INF/tags" prefix="tags" %>

<tiles:insert page="/WEB-INF/tiles/mainLayout.jsp" flush="true">
    <tiles:put name="title" type="string">
        <ecom:titleTrail mainMenu="Expert2" beginForm="e2_extDispPriceMedServiceForm" />

    </tiles:put>
    <tiles:put name="body" type="string">
        <msh:form action="/entityParentSaveGoParentView-e2_extDispPriceMedService.do" defaultField="medService">
            <msh:hidden property="id" />
            <msh:hidden property="saveType" />
            <msh:hidden property="price" />
            <msh:panel>
                <msh:separator colSpan="8" label="Общие"/>
                <msh:row>
                    <msh:autoComplete property="medService" vocName="vocMedServiceCode" size="100" />
                    </msh:row>
                <msh:row>
                    <msh:checkBox property="isRequired" />
                </msh:row>
                <msh:submitCancelButtonsRow colSpan="4" />
            </msh:panel>
        </msh:form>
    </tiles:put>

    <tiles:put name="side" type="string">
        <msh:ifFormTypeIsView formName="e2_extDispPriceMedServiceForm">
            <msh:sideMenu>
                <msh:sideLink key="ALT+2" params="id" action="/entityEdit-e2_extDispPriceMedService" name="Изменить" roles="/Policy/E2/Edit" />
                <msh:sideLink key="ALT+2" params="id" action="/entityDelete-e2_extDispPriceMedService" name="Удалить" roles="/Policy/E2/Delete" />
            </msh:sideMenu>
        </msh:ifFormTypeIsView>
    </tiles:put>
    <tiles:put name="javascript" type="string">
        <msh:ifFormTypeIsView formName="e2_extDispPriceMedServiceForm">
            <script type="text/javascript" src="./dwr/interface/Expert2Service.js"></script>
            <script type="text/javascript">

             </script>

        </msh:ifFormTypeIsView>
    </tiles:put>
</tiles:insert>

