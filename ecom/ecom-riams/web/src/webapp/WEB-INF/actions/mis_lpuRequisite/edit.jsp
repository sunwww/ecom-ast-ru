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

                <msh:submitCancelButtonsRow colSpan="2" guid="a332e241-83f4-4e61-ad6f-d0f69cc6076e" />
            </msh:panel>
        </msh:form>

    </tiles:put>
    <tiles:put name="side" type="string">
        <msh:sideMenu guid="b08ac525-aee5-493c-bd30-a245e7c80200">
            <msh:ifFormTypeIsView formName="mis_lpuRequisiteForm" guid="24576531-bcea-4103-9c80-fe9e95c4dfaf">
                <msh:sideLink key="ALT+2" roles="/Policy/Mis/MisLpu/Edit" params="id" action="/entityEdit-mis_lpuRequisite" name="Изменить" guid="dc8c6883-5faf-41b0-b068-296214839e40" />
            </msh:ifFormTypeIsView>
            <hr />
            <msh:ifFormTypeAreViewOrEdit formName="mis_lpuRequisiteForm" guid="b0b9ca4d-8f54-4f15-87a7-34b467ed10d5">
                <msh:sideLink key="ALT+DEL" roles="/Policy/Mis/MisLpu/Delete" params="id" action="/entityParentDeleteGoParentView-mis_lpuRequisite" name="Удалить" confirm="Удалить?" guid="d3d19781-f1b0-42b3-a314-f5e6a2b55584" />
            </msh:ifFormTypeAreViewOrEdit>
        </msh:sideMenu>
    </tiles:put>
    <tiles:put name="title" type="string">
        <ecom:titleTrail mainMenu="Lpu" beginForm="mis_lpuRequisiteForm" guid="04eb4fb1-03b4-4011-9e85-30cd955d2c41" />
    </tiles:put>
    <tiles:put name="javascript" type="string">


    </tiles:put>
</tiles:insert>