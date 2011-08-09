<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://struts.apache.org/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://www.nuzmsh.ru/tags/msh" prefix="msh" %>
<%@ taglib uri="http://www.ecom-ast.ru/tags/ecom" prefix="ecom" %>
<%@ taglib uri="/WEB-INF/mis.tld" prefix="mis" %>
<%@ taglib tagdir="/WEB-INF/tags" prefix="tags" %>

<tiles:insert page="/WEB-INF/tiles/mainLayout.jsp" flush="true">

    <tiles:put name='body' type='string'>
        <msh:form action="entityParentSaveGoParentView-poly_concomitantDisease.do" defaultField="diagnosis">
            <msh:hidden property="id"/>
            <msh:hidden property="saveType"/>
            <msh:hidden property="ticket"/>

            <msh:panel>

                <msh:row>
                    <msh:autoComplete vocName="omcMkb10" property="diagnosis" 
                    	label="Диагноз код МКБ"
                    	size='50' />
                </msh:row>

                <msh:submitCancelButtonsRow colSpan="2"/>
            </msh:panel>

        </msh:form>

    </tiles:put>

    <tiles:put name='side' type='string'>
        <msh:sideMenu>

            <msh:ifFormTypeIsView formName="poly_concomitantDiseaseForm">
                <msh:sideLink roles="/Policy/Poly/ConcomitantDisease/Edit" key="ALT+2" params="id"
                              action="/entityParentEdit-poly_concomitantDisease"
                              name="Редактировать"/>


            </msh:ifFormTypeIsView>

            <msh:ifFormTypeAreViewOrEdit formName="poly_concomitantDiseaseForm">
                <msh:sideLink roles="/Policy/Poly/ConcomitantDisease/Delete" key='ALT+DEL' params="id"
                              action="/entityParentDeleteGoParentView-poly_concomitantDisease" name="Удалить"
                              confirm="Вы действительно хотите удалить?"/>
            </msh:ifFormTypeAreViewOrEdit>

        </msh:sideMenu>
    </tiles:put>


</tiles:insert>