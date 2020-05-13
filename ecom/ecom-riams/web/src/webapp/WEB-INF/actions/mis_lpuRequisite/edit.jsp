<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://struts.apache.org/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://www.nuzmsh.ru/tags/msh" prefix="msh" %>
<%@ taglib uri="http://www.ecom-ast.ru/tags/ecom" prefix="ecom" %>

<tiles:insert page = '/WEB-INF/tiles/mainLayout.jsp' flush = 'true'>

    <tiles:put name="body" type="string">
        <msh:form action="entityParentSaveGoParentView-mis_lpuRequisite.do" defaultField="lpu" >
            <msh:hidden property="id" />
            <msh:hidden property="lpu" />
            <msh:hidden property="saveType"/>
            <msh:panel>

                <msh:row >
                    <msh:textField property="name" label="Название" size="50" fieldColSpan="3" horizontalFill="true" />
                </msh:row>
                <msh:row >
                    <msh:textField property="code" label="Код" size="50" fieldColSpan="3" horizontalFill="true" />
                </msh:row>
                <msh:row >
                    <msh:textField property="value" label="Значение" size="50" fieldColSpan="3" horizontalFill="true" />
                </msh:row>

                <msh:submitCancelButtonsRow colSpan="2" />
            </msh:panel>
        </msh:form>

    </tiles:put>
    <tiles:put name="side" type="string">
        <msh:sideMenu>
            <msh:ifFormTypeIsView formName="mis_lpuRequisiteForm">
                <msh:sideLink key="ALT+2" roles="/Policy/Mis/MisLpu/Edit" params="id" action="/entityEdit-mis_lpuRequisite" name="Изменить" />
            </msh:ifFormTypeIsView>
            <hr />
            <msh:ifFormTypeAreViewOrEdit formName="mis_lpuRequisiteForm">
                <msh:sideLink key="ALT+DEL" roles="/Policy/Mis/MisLpu/Delete" params="id" action="/entityParentDeleteGoParentView-mis_lpuRequisite" name="Удалить" confirm="Удалить?" />
            </msh:ifFormTypeAreViewOrEdit>
        </msh:sideMenu>
    </tiles:put>
    <tiles:put name="title" type="string">
        <ecom:titleTrail mainMenu="Lpu" beginForm="mis_lpuRequisiteForm" />
    </tiles:put>
    <tiles:put name="javascript" type="string">


    </tiles:put>
</tiles:insert>