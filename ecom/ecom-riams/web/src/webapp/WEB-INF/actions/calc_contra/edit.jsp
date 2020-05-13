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
        <msh:form action="/entityParentSaveGoParentView-calc_contra.do" defaultField="contraValue">
            <msh:hidden property="id" />
            <msh:hidden property="saveType" />
            <msh:hidden property="calculator" />
            <msh:panel>
                <msh:row>
                    <msh:textField property="contraValue" label="Противопоказание" size="150" />
                </msh:row>
                <msh:submitCancelButtonsRow colSpan="2" />
            </msh:panel>
        </msh:form>
    </tiles:put>
    <tiles:put name="title" type="string">
        <ecom:titleTrail mainMenu="Calculator" beginForm="calc_contraForm"/>
    </tiles:put>
</tiles:insert>