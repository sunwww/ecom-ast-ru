<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://struts.apache.org/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://www.nuzmsh.ru/tags/msh" prefix="msh" %>
<%@ taglib uri="http://www.ecom-ast.ru/tags/ecom" prefix="ecom" %>
<%@ taglib uri="/WEB-INF/mis.tld" prefix="mis" %>
<%@ taglib tagdir="/WEB-INF/tags" prefix="tags" %>

<tiles:insert page="/WEB-INF/tiles/mainLayout.jsp" flush="true">

    <tiles:put name='body' type='string'>
        <msh:form action="entityParentSaveGoParentView-poly_prescriptionBlank.do" defaultField="series">
            <msh:hidden property="id"/>
            <msh:hidden property="saveType"/>
            <msh:hidden property="ticket"/>

            <msh:panel>

                <msh:row>
                    <msh:textField label="Серия бланка" property="series" fieldColSpan="1" horizontalFill="true"
                                   size="5"/>

                    <msh:textField label="Номер бланка" property="number" fieldColSpan="1" horizontalFill="true"
                                   size="10"/>
                </msh:row>
                <msh:row>
                    <msh:textField label="Дата выписки" property="writingOutDate" fieldColSpan="1" horizontalFill="true"
                                   size="5"/>
                </msh:row>

                <msh:submitCancelButtonsRow colSpan="2"/>
            </msh:panel>

        </msh:form>

    </tiles:put>

    <tiles:put name='side' type='string'>
        <msh:sideMenu>

            <msh:ifFormTypeIsView formName="poly_prescriptionBlankForm">
                <msh:sideLink roles="/Policy/Poly/PrescriptionBlank/Edit" key="ALT+2" params="id"
                              action="/entityParentEdit-poly_prescriptionBlank"
                              name="Редактировать"/>


            </msh:ifFormTypeIsView>

            <msh:ifFormTypeAreViewOrEdit formName="poly_prescriptionBlankForm">
                <msh:sideLink roles="/Policy/Poly/PrescriptionBlank/Delete" key='ALT+DEL' params="id"
                              action="/entityParentDeleteGoParentView-poly_prescriptionBlank" name="Удалить"
                              confirm="Вы действительно хотите удалить?"/>
            </msh:ifFormTypeAreViewOrEdit>

        </msh:sideMenu>
    </tiles:put>

<tiles:put name='title' type='string'>
    <ecom:titleTrail mainMenu="Ticket" beginForm="poly_prescriptionBlankForm"/>
</tiles:put>
</tiles:insert>