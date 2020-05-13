<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://struts.apache.org/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://www.nuzmsh.ru/tags/msh" prefix="msh" %>
<%@ taglib uri="http://www.ecom-ast.ru/tags/ecom" prefix="ecom" %>
<%@ taglib tagdir="/WEB-INF/tags" prefix="tags" %>

<tiles:insert page="/WEB-INF/tiles/main${param.short}Layout.jsp" flush="true">
    <tiles:put name="side" type="string">
        <msh:ifFormTypeAreViewOrEdit formName="calc_prescForm">
            <msh:sideMenu>
                <msh:sideLink key="ALT+2" params="id" action="/entityParentEdit-calc_presc" name="Изменить" title="Изменить" roles="/Policy/Mis/Calc/Calculation/Edit"/>
                <msh:sideLink key="ALT+DEL" confirm="Вы точно хотите удалить?" params="id" action="/entityParentDeleteGoParentView-calc_presc" name="Удалить" title="Удалить" roles="/Policy/Mis/Calc/Calculation/Edit"/>
            </msh:sideMenu>
        </msh:ifFormTypeAreViewOrEdit>
    </tiles:put>
    <tiles:put name="body" type="string">
        <msh:form action="/entityParentSaveGoParentView-calc_presc.do" defaultField="prescValue">
            <msh:hidden property="id" />
            <msh:hidden property="saveType" />
            <msh:hidden property="calculator" />
            <msh:panel>
                <msh:row>
                    <msh:textField property="prescValue" label="Назначение" size="150" />
                </msh:row>
                <msh:row>
                    <msh:autoComplete  property="calcRisk" label="Риск" vocName="vocCalcRiskparent" parentId="" size="25"/>
                </msh:row>
                <msh:submitCancelButtonsRow colSpan="2" />
            </msh:panel>
        </msh:form>
    </tiles:put>
    <tiles:put name="title" type="string">
        <ecom:titleTrail mainMenu="Calculator" beginForm="calc_prescForm"/>
    </tiles:put>
    <tiles:put name="javascript" type="string">
    <script type="text/javascript" >
        calcRiskAutocomplete.setParentId($('calculator').value);
    </script>
    </tiles:put>
</tiles:insert>