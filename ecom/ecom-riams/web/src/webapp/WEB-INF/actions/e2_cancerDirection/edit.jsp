<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://struts.apache.org/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://www.nuzmsh.ru/tags/msh" prefix="msh" %>
<%@ taglib uri="http://www.ecom-ast.ru/tags/ecom" prefix="ecom" %>
<%@ taglib tagdir="/WEB-INF/tags" prefix="tags" %>
<tiles:insert page="/WEB-INF/tiles/mainLayout.jsp" flush="true">

    <tiles:put name="body" type="string">
        <msh:form action="/entityParentSaveGoParentView-e2_cancerDirection.do" defaultField="maybeCancer">
            <msh:hidden property="id" />
            <msh:hidden property="saveType" />
            <msh:hidden property="cancerEntry" />
            <msh:panel>
                <msh:separator label="Направление" colSpan="4"/>
                    <msh:row>
                        <msh:textField property="date"/>
                        <msh:autoComplete property="type" vocName="vocOncologyTypeDirectionCode" size="50"/>
                    </msh:row>
                    <msh:row>
                        <msh:autoComplete property="surveyMethod" vocName="vocOncologyMethodDiagTreatCode" size="50"/>
                        <msh:autoComplete property="medService" vocName="vocMedServiceCode" size="50"/>
                    </msh:row>
                <msh:row>
                    <msh:textField property="directLpu" />
                </msh:row>


                <msh:submitCancelButtonsRow colSpan="1" />
            </msh:panel>
        </msh:form>
    </tiles:put>
    <tiles:put name="title" type="string">
        <ecom:titleTrail mainMenu="Expert2" beginForm="e2_cancerDirectionForm" />
    </tiles:put>
    <tiles:put name="javascript" type="string">
        <msh:ifFormTypeIsView formName="e2_cancerDirectionForm">
            <script type="text/javascript" src="./dwr/interface/Expert2Service.js"></script>

                <script type="text/javascript">

                </script>

          </msh:ifFormTypeIsView>

    </tiles:put>

    <tiles:put name="side" type="string">
        <msh:ifFormTypeAreViewOrEdit formName="e2_cancerDirectionForm">
            <msh:sideMenu>
                <msh:sideLink params="id" action="/entityParentEdit-e2_cancerDirection" name="Изменить" roles="/Policy/E2/Edit" />
                <msh:sideLink params="id" action="/entityParentDelete-e2_cancerDirection" name="Удалить" roles="/Policy/E2/Delete" />
            </msh:sideMenu>
        </msh:ifFormTypeAreViewOrEdit>
    </tiles:put>
</tiles:insert>

