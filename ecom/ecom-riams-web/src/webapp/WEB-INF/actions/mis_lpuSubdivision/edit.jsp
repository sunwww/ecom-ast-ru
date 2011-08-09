<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://struts.apache.org/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://www.nuzmsh.ru/tags/msh" prefix="msh" %>
<%@ taglib uri="http://www.ecom-ast.ru/tags/ecom" prefix="ecom" %>

<tiles:insert page="/WEB-INF/tiles/mainLayout.jsp" flush="true">

    <tiles:put name='body' type='string'>
        <msh:form action="entityParentSaveGoParentView-mis_lpuSubdivision.do" defaultField="name">
            <msh:hidden property="id"/>
            <msh:hidden property="saveType"/>
            <msh:hidden property="lpu"/>

            <msh:panel>

                <msh:row>
                    <msh:textField property="name" label="Название подразделения" horizontalFill="true" size="50"/>
                </msh:row>
                <msh:submitCancelButtonsRow colSpan="2"/>
            </msh:panel>
        </msh:form>

        <msh:ifFormTypeIsView formName="mis_lpuSubdivisionForm">
            <msh:section>
                <msh:sectionTitle>Пустой</msh:sectionTitle>
            </msh:section>
        </msh:ifFormTypeIsView>


    </tiles:put>

    <tiles:put name='side' type='string'>
        <msh:sideMenu>
            <msh:ifFormTypeIsView formName="mis_lpuAreaForm">
                <msh:sideLink key="ALT+2" params="id" action="/entityEdit-mis_lpuSubdivision" name="Изменить"/>
            </msh:ifFormTypeIsView>
            <hr/>
            <msh:ifFormTypeAreViewOrEdit formName="mis_lpuSubdivisionForm">
                <msh:sideLink key='ALT+DEL' params="id" action="/entityDelete-mis_lpuArea" name="Удалить" confirm="Удалить подразделение?"/>
            </msh:ifFormTypeAreViewOrEdit>
        </msh:sideMenu>
    </tiles:put>

    <tiles:put name='title' type='string'>
        <ecom:titleTrail mainMenu="Lpu" beginForm="mis_lpuSubdivisionForm" />
    </tiles:put>


</tiles:insert>