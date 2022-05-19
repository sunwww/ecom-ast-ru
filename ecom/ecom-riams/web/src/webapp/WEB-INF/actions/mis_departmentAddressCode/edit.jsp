<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib uri="http://struts.apache.org/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://www.nuzmsh.ru/tags/msh" prefix="msh" %>
<%@ taglib uri="http://www.ecom-ast.ru/tags/ecom" prefix="ecom" %>

<tiles:insert page="/WEB-INF/tiles/mainLayout.jsp" flush="true">

    <tiles:put name="body" type="string">
        <msh:form action="/entityParentSaveGoView-mis_departmentAddressCode.do" defaultField="departmentAddressCode">
            <msh:hidden property="id"/>
            <msh:hidden property="saveType"/>
            <msh:hidden property="department"/>
            <msh:panel>
                <msh:row>
                    <msh:autoComplete vocName="vocE2FondV021" property="profile" horizontalFill="true"
                                      fieldColSpan="3"/>
                </msh:row>
                <msh:row>
                    <msh:textField property="departmentAddressCode"  size="50" fieldColSpan="3"/>
                </msh:row>
                <msh:submitCancelButtonsRow colSpan="4"/>
            </msh:panel>
        </msh:form>
    </tiles:put>
    <tiles:put name="title" type="string">
        <ecom:titleTrail mainMenu="Lpu" beginForm="mis_departmentAddressCodeForm"/>
    </tiles:put>
    <tiles:put name="side" type="string">
        <msh:ifFormTypeIsView formName="mis_departmentAddressCodeForm">
            <msh:sideMenu>
                <msh:sideLink key="ALT+2" params="id" action="/entityEdit-mis_departmentAddressCode" name="Изменить"
                              roles="/Policy/Mis/BedFund/Edit"/>
                <msh:sideLink key="ALT+DEL" confirm="Удалить?" params="id" action="/entityParentDelete-mis_departmentAddressCode"
                              name="Удалить" roles="/Policy/Mis/BedFund/Delete"/>
            </msh:sideMenu>
        </msh:ifFormTypeIsView>
    </tiles:put>
</tiles:insert>

