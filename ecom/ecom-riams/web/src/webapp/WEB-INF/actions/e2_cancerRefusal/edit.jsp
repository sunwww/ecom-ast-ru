<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://struts.apache.org/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://www.nuzmsh.ru/tags/msh" prefix="msh" %>
<%@ taglib uri="http://www.ecom-ast.ru/tags/ecom" prefix="ecom" %>
<%@ taglib tagdir="/WEB-INF/tags" prefix="tags" %>
<tiles:insert page="/WEB-INF/tiles/mainLayout.jsp" flush="true">

    <tiles:put name="body" type="string">
        <msh:form action="/entityParentSaveGoParentView-e2_cancerRefusal.do" defaultField="maybeCancer">
            <msh:hidden property="id" />
            <msh:hidden property="saveType" />
            <msh:hidden property="cancerEntry" />
            <msh:panel>
                <msh:separator label="Противопоказания" colSpan="4"/>
                <msh:row>
                <msh:textField property="date"/>
                <msh:autoComplete property="code" vocName="vocOncologyN001Code" size="50"/>
                </msh:row>

                <msh:submitCancelButtonsRow colSpan="1" />
            </msh:panel>
        </msh:form>
    </tiles:put>
    <tiles:put name="title" type="string">
        <ecom:titleTrail mainMenu="Expert2" beginForm="e2_cancerRefusalForm" />
    </tiles:put>
    <tiles:put name="javascript" type="string">
        <msh:ifFormTypeIsView formName="e2_cancerRefusalForm">
            <script type="text/javascript" src="./dwr/interface/Expert2Service.js"></script>

                <script type="text/javascript">

                </script>

          </msh:ifFormTypeIsView>

    </tiles:put>

    <tiles:put name="side" type="string">
        <msh:ifFormTypeAreViewOrEdit formName="e2_cancerRefusalForm">
            <msh:sideMenu>
                <msh:sideLink params="id" action="/entityParentEdit-e2_cancerRefusal" name="Изменить" roles="/Policy/E2/Edit" />
                <msh:sideLink params="id" action="/entityParentDelete-e2_cancerRefusal" name="Удалить" roles="/Policy/E2/Delete" />
            </msh:sideMenu>
        </msh:ifFormTypeAreViewOrEdit>
    </tiles:put>
</tiles:insert>

