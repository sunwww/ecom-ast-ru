<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://struts.apache.org/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://www.nuzmsh.ru/tags/msh" prefix="msh" %>
<%@ taglib uri="http://www.ecom-ast.ru/tags/ecom" prefix="ecom" %>

<tiles:insert page="/WEB-INF/tiles/mainLayout.jsp" flush="true">

    <tiles:put name='body' type='string'>
        <msh:form action="entityParentSave-mis_colorIdentity.do" defaultField="name">
            <msh:hidden property="id"/>
            <msh:hidden property="saveType"/>
            <msh:hidden property="lpu"/>
            <msh:panel>
                <msh:row>
                    <msh:textField property="name" label="Информация" horizontalFill="true" size="100"/>
                </msh:row>
                <msh:ifFormTypeAreViewOrEdit formName="mis_colorIdentityForm">
                    <msh:ifFormTypeIsNotView formName="mis_colorIdentityForm">
                        <msh:autoComplete vocName="vocColorFree" size="50"  property="color" label="Цвет браслета" fieldColSpan="3" horizontalFill="true" parentId="${param.id}"/>
                    </msh:ifFormTypeIsNotView>
                    <msh:ifFormTypeIsView formName="mis_colorIdentityForm">
                        <msh:autoComplete vocName="vocColorFree" size="50"  property="color" label="Цвет браслета" fieldColSpan="3" horizontalFill="true" viewOnlyField="true"/>
                    </msh:ifFormTypeIsView>
                </msh:ifFormTypeAreViewOrEdit>
                <msh:ifFormTypeIsCreate formName="mis_colorIdentityForm">
                    <msh:autoComplete vocName="vocColorFree" size="50"  property="color" label="Цвет браслета" fieldColSpan="3" horizontalFill="true" parentId="0"/>
                </msh:ifFormTypeIsCreate>
                <msh:row>
                    <msh:checkBox property="isForNewborn" label="Может ли заполняться в родах?"  horizontalFill="true" size="100"/>
                </msh:row>
                <msh:row>
                    <msh:checkBox property="isDeniedManual" label="Запрещено создавать вручную?"  horizontalFill="true" size="100"/>
                </msh:row>
                <msh:submitCancelButtonsRow colSpan="4"/>
            </msh:panel>
        </msh:form>

    </tiles:put>

    <tiles:put name='side' type='string'>
        <msh:sideMenu>
            <msh:ifFormTypeIsView formName="mis_colorIdentityForm">
                <msh:sideLink key="ALT+2" params="id" action="/entityEdit-mis_colorIdentity" name="Изменить"/>
            </msh:ifFormTypeIsView>
            <hr/>
            <msh:ifFormTypeAreViewOrEdit formName="mis_colorIdentityForm">
                <msh:sideLink key='ALT+DEL' params="id" action="/entityParentDeleteGoParentView-mis_colorIdentity" name="Удалить" confirm="Удалить цвет?"/>
            </msh:ifFormTypeAreViewOrEdit>
        </msh:sideMenu>
    </tiles:put>
    <tiles:put name='title' type='string'>
        <ecom:titleTrail mainMenu="Lpu" beginForm="mis_colorIdentityForm" />
    </tiles:put>


</tiles:insert>
