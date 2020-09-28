<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://struts.apache.org/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://www.nuzmsh.ru/tags/msh" prefix="msh" %>
<%@ taglib uri="http://www.ecom-ast.ru/tags/ecom" prefix="ecom" %>
<%@ taglib tagdir="/WEB-INF/tags" prefix="tags" %>

<tiles:insert page="/WEB-INF/tiles/mainLayout.jsp" flush="true">

    <tiles:put name="side" type="string">
        <tags:style_currentMenu currentAction="diary_parameterGroup" />
        <msh:sideMenu title="Параметр">
            <msh:ifFormTypeIsView formName="diary_parameterRefForm">
                <msh:sideLink key="ALT+1" params="id" action="/entityParentEdit-diary_parameterRef" name="Изменить" roles="/Policy/Diary/ParameterGroup/Parameter/Edit" />
            </msh:ifFormTypeIsView>
            <msh:ifFormTypeAreViewOrEdit formName="diary_parameterRefForm" >
                <msh:sideLink key="ALT+DEL" confirm="Удалить?" params="id" action="/entityParentDelete-diary_parameterRef" name="Удалить" roles="/Policy/Diary/ParameterGroup/Parameter/Delete" />
            </msh:ifFormTypeAreViewOrEdit>
        </msh:sideMenu>
    </tiles:put>

    <tiles:put name="body" type="string">
        <msh:form action="/entityParentSaveGoParentView-diary_parameterRef.do" defaultField="normaMin" >
            <msh:hidden property="id"  />
            <msh:hidden property="saveType"  />
            <msh:hidden property="parameter"  />
            <msh:panel>
                <msh:row>
                    <msh:autoComplete property="sex" vocName="vocSex" fieldColSpan="3" horizontalFill="true" />
                </msh:row>
                <msh:row>
                    <msh:textField property="ageFrom" />
                    <msh:textField property="ageTo" />
                </msh:row>
                <msh:separator label="Референтные значения" colSpan="4" />
                <msh:row>
                    <msh:textField property="normaMin" />
                    <msh:textField property="normaMax" />
                </msh:row>
                <msh:row>
                    <msh:separator label="Границы допустимых значений" colSpan="4" />
                </msh:row>
                <msh:row>
                    <msh:textField property="superMin" />
                    <msh:textField property="superMax" />
                </msh:row>
                <msh:submitCancelButtonsRow colSpan="3" />
            </msh:panel>
        </msh:form>


    </tiles:put>
    <tiles:put name="title" type="string">
        <ecom:titleTrail mainMenu="Config" beginForm="diary_parameterRefForm" />
    </tiles:put>
</tiles:insert>

