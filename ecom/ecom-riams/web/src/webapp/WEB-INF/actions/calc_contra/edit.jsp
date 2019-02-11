<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://struts.apache.org/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://www.nuzmsh.ru/tags/msh" prefix="msh" %>
<%@ taglib uri="http://www.ecom-ast.ru/tags/ecom" prefix="ecom" %>
<%@ taglib tagdir="/WEB-INF/tags" prefix="tags" %>

<tiles:insert page="/WEB-INF/tiles/main${param.short}Layout.jsp" flush="true">

    <tiles:put name="body" type="string">
        <tiles:put name="side" type="string">
            <msh:ifFormTypeAreViewOrEdit formName="calc_contraForm">
                <msh:sideMenu>
                    <msh:sideLink key="ALT+2" params="id" action="/entityParentEdit-calc_contra" name="Изменить" title="Изменить" roles="/Policy/Mis/Calc/Calculation/Edit"/>
                    <msh:sideLink key="ALT+DEL" confirm="Вы точно хотите удалить?" params="id" action="/entityParentDeleteGoParentView-calc_contra" name="Удалить" title="Удалить" roles="/Policy/Mis/Calc/Calculation/Edit"/>
                </msh:sideMenu>
            </msh:ifFormTypeAreViewOrEdit>
        </tiles:put>
        <msh:form action="/entityParentSaveGoParentView-calc_contra.do" defaultField="contraValue" guid="be2c889f-ed1d-4a2b-9cda-9127e9d94885">
            <msh:hidden property="id" guid="d10f460a-e434-45a5-90f0-b0a7aed00ec6" />
            <msh:hidden property="saveType" guid="bd322f07-c944-4587-a963-a09db2b93caf" />
            <msh:hidden property="calculator" guid="bd322f07-c944-4587-a963-a09db2b93caf" />
            <msh:panel guid="d1cd0310-bf53-4ce1-9dd5-06388b51ec01">
                <msh:row>
                    <msh:textField property="contraValue" label="Противопоказание" size="150" />
                </msh:row>
                <msh:submitCancelButtonsRow colSpan="2" guid="6bece8ec-9b93-4faf-b729-851f1447d54f" />
            </msh:panel>
        </msh:form>
    </tiles:put>
    <tiles:put name="title" type="string">
        <ecom:titleTrail mainMenu="Calculator" beginForm="calc_contraForm"/>
    </tiles:put>
</tiles:insert>