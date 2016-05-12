<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://struts.apache.org/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://www.nuzmsh.ru/tags/msh" prefix="msh" %>
<%@ taglib uri="http://www.ecom-ast.ru/tags/ecom" prefix="ecom" %>

<tiles:insert page="/WEB-INF/tiles/mainLayout.jsp" flush="true">

    <tiles:put name='body' type='string'>
        <msh:form action="entityParentSaveGoParentView-mis_identityCardPassport.do" defaultField="series">
            <msh:hidden property="id"/>
            <msh:hidden property="saveType"/>
            <msh:hidden property="patient"/>

            <msh:panel>
                <msh:row>
                    <msh:textField property="series" label="Серия" size="15"/>
                    <msh:textField property="number" label="Номер" size="30"/>
                </msh:row>

                <msh:row>
                    <msh:textField property="dateIssue" label="Дата выдачи" size="15"/>
                </msh:row>

                <msh:row>
                    <msh:autoComplete vocName="vocPassportWhomIssue" property="whomIssue" label="Кто выдал" horizontalFill="true" fieldColSpan="3"/>
                </msh:row>


                <msh:submitCancelButtonsRow colSpan="4"/>
            </msh:panel>



        </msh:form>


    </tiles:put>

    <tiles:put name='side' type='string'>
        <msh:sideMenu>

            <msh:ifFormTypeIsView formName="mis_identityCardPassportForm">
                <msh:sideLink key="ALT+2" params="id" action="/entityParentEdit-mis_identityCardPassport" name="Изменить"/>
            </msh:ifFormTypeIsView>

            <hr/>
            <msh:ifFormTypeAreViewOrEdit formName="mis_identityCardPassportForm">
                <msh:sideLink key='ALT+DEL' params="id" action="/entityParentDeleteGoParentView-mis_identityCardPassport" name="Удалить"
                              confirm="Удалить пасспорт?"/>
            </msh:ifFormTypeAreViewOrEdit>
        </msh:sideMenu>
    </tiles:put>

    <tiles:put name='title' type='string'>
        <ecom:titleTrail mainMenu="Patient" beginForm="mis_identityCardPassportForm"/>
    </tiles:put>


</tiles:insert>