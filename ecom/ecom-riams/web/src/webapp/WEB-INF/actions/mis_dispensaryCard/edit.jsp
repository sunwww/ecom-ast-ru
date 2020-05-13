<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib uri="http://struts.apache.org/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://www.nuzmsh.ru/tags/msh" prefix="msh" %>
<%@ taglib uri="http://www.ecom-ast.ru/tags/ecom" prefix="ecom" %>
<%@ taglib tagdir="/WEB-INF/tags" prefix="tags" %>


<tiles:insert page="/WEB-INF/tiles/main${param.short}Layout.jsp" flush="true">


    <tiles:put name='body' type='string'>

        <msh:form action="entityParentSaveGoParentView-mis_dispensaryCard.do" defaultField="startDate">

            <msh:hidden property="id"/>
            <msh:hidden property="saveType"/>
            <msh:hidden property="patient"/>
            <msh:panel >
                    <msh:row>
                        <msh:textField property="startDate" />
                    </msh:row>
                    <msh:row>
                        <msh:autoComplete vocName="vocIdc10" property="diagnosis" size="50" fieldColSpan="5"/>
                    </msh:row>
                <msh:row>
                    <msh:autoComplete vocName="workFunction" size="50"  property="workFunction" label="Кто установил Д наблюдение" fieldColSpan="5" horizontalFill="true" />
                </msh:row>
                <msh:row>
                        <msh:textField property="finishDate" />
                    </msh:row>
                <msh:row>
                        <msh:autoComplete property="endReason" vocName="vocDispensaryEndReason" size="50" fieldColSpan="5"/>
                    </msh:row>

                <msh:row>
                    <msh:textField property="createDate" viewOnlyField="true"/>
                    <msh:textField property="createTime" viewOnlyField="true"/>
                    <msh:textField property="createUsername" viewOnlyField="true"/>
                </msh:row>
                <msh:row>
                    <msh:textField property="editDate" viewOnlyField="true"/>
                    <msh:textField property="editTime" viewOnlyField="true"/>
                    <msh:textField property="editUsername" viewOnlyField="true"/>
                </msh:row>
            </msh:panel>
            <msh:submitCancelButtonsRow colSpan="6"/>
        </msh:form>
    </tiles:put>

    <tiles:put name='side' type='string'>
        <msh:ifFormTypeAreViewOrEdit formName="mis_dispensaryCardForm">
            <msh:sideMenu>
                <msh:sideLink key="ALT+2" params="id" action="/entityParentEdit-mis_dispensaryCard" name="Изменить" roles="/Policy/Mis/Patient/Dispensary/Edit" />
                <msh:sideLink key="ALT+2" params="id" action="/entityParentDelete-mis_dispensaryCard" name="Удалить" confirm="Уверены что делаете?" roles="/Policy/Mis/Patient/Dispensary/Delete" />
            </msh:sideMenu>
        </msh:ifFormTypeAreViewOrEdit>
    </tiles:put>

    <tiles:put name='title' type='string'>
        <ecom:titleTrail mainMenu="Patient" beginForm="mis_dispensaryCardForm" />
    </tiles:put>
</tiles:insert>