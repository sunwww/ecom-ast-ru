<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://struts.apache.org/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://www.nuzmsh.ru/tags/msh" prefix="msh" %>
<%@ taglib uri="http://www.ecom-ast.ru/tags/ecom" prefix="ecom" %>

<tiles:insert page="/WEB-INF/tiles/mainLayout.jsp" flush="true">

    <tiles:put name='body' type='string'>
        <msh:form action="entityParentSaveGoParentView-mis_identityCard.do" defaultField="series">
            <msh:hidden property="id"/>
            <msh:hidden property="saveType"/>
            <msh:hidden property="patient"/>

            <msh:panel>
                <ms:row>
                    <msh:textField property="name" label="Пр. удост. личности"/>
                </ms:row>
                <msh:row>
                    <msh:textField property="series" label="Серия" horizontalFill="true" size="30"/>
                    <msh:textField property="number" label="Номер" horizontalFill="true" size="30"/>
                </msh:row>
                <ms:row>
                    <msh:textField property="dateIssue" label='Выдан' />
                    <msh:textField property="whomIssue" label='кем' />
                </ms:row>
                <msh:submitCancelButtonsRow colSpan="2"/>
            </msh:panel>



        </msh:form>


    </tiles:put>

    <tiles:put name='side' type='string'>
        <msh:sideMenu>

            <msh:ifFormTypeIsView formName="mis_medPolicyOmcForm">
                <msh:sideLink key="ALT+2" params="id" action="/entityParentEdit-mis_medPolicyOmc" name="Изменить"/>
            </msh:ifFormTypeIsView>

            <hr/>
            <msh:ifFormTypeAreViewOrEdit formName="mis_medPolicyOmcForm">
                <msh:sideLink key='ALT+DEL' params="id" action="/entityParentDeleteGoParentView-mis_medPolicyOmc" name="Удалить"
                              confirm="Удалить полис?"/>
            </msh:ifFormTypeAreViewOrEdit>
        </msh:sideMenu>
    </tiles:put>

    <tiles:put name='title' type='string'>
        <ecom:titleTrail mainMenu="Patient" beginForm="mis_medPolicyOmcForm"/>
    </tiles:put>


</tiles:insert>