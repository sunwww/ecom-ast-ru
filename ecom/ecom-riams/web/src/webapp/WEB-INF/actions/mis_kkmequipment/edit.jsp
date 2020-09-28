<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://struts.apache.org/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://www.nuzmsh.ru/tags/msh" prefix="msh" %>
<%@ taglib uri="http://www.ecom-ast.ru/tags/ecom" prefix="ecom" %>

<tiles:insert page="/WEB-INF/tiles/mainLayout.jsp" flush="true">

    <tiles:put name='body' type='string'>
        <msh:form action="entityParentSave-mis_kkmequipment.do" defaultField="name">
            <msh:hidden property="id"/>
            <msh:hidden property="saveType"/>
            <msh:hidden property="lpu"/>

            <msh:panel>
                <msh:row>
                    <msh:textField property="name" label="Наименование" horizontalFill="true" size="100"/>
                </msh:row>
                <msh:row>
                    <msh:textField property="url" label="URL"  horizontalFill="true" size="100"/>
                </msh:row>
                <msh:submitCancelButtonsRow colSpan="4"/>
            </msh:panel>
        </msh:form>

    </tiles:put>

    <tiles:put name='side' type='string'>
        <msh:sideMenu>
            <msh:ifFormTypeIsView formName="mis_kkmequipmentForm">
                <msh:sideLink key="ALT+2" params="id" action="/entityEdit-mis_kkmequipment" name="Изменить"/>
            </msh:ifFormTypeIsView>
            <hr/>
            <msh:ifFormTypeAreViewOrEdit formName="mis_kkmequipmentForm">
                <msh:sideLink key='ALT+DEL' params="id" action="/entityParentDeleteGoParentView-mis_kkmequipment" name="Удалить" confirm="Удалить ККМ?"/>
            </msh:ifFormTypeAreViewOrEdit>
        </msh:sideMenu>
    </tiles:put>
    <tiles:put name='title' type='string'>
        <ecom:titleTrail mainMenu="Lpu" beginForm="mis_kkmequipmentForm" />
    </tiles:put>


</tiles:insert>
