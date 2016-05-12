<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://struts.apache.org/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://www.nuzmsh.ru/tags/msh" prefix="msh" %>
<%@ taglib uri="http://www.ecom-ast.ru/tags/ecom" prefix="ecom" %>
<%@ taglib tagdir="/WEB-INF/tags" prefix="tags" %>


<tiles:insert page="/WEB-INF/tiles/mainLayout.jsp" flush="true">

    <tiles:put name='body' type='string'>
        <msh:form action="entityParentSaveGoParentView-diary_templateProtocolCategory" defaultField="classif">
            <msh:hidden property="id"/>
            <msh:hidden property="saveType"/>
            <msh:hidden property="templateProtocol"/>
            <msh:hidden property="category"/>


            <msh:panel>
                <msh:ifFormTypeIsView formName="diary_templateProtocolCategoryForm">
                    <msh:row>
                        <msh:textField property="classifName" label="Название классификатора" size="60" horizontalFill="true" fieldColSpan="3"/>
                    </msh:row>
                    <msh:row>
                        <msh:textField property="categoryName" label="Название категории" size="60" horizontalFill="true" fieldColSpan="3"/>
                    </msh:row>
                </msh:ifFormTypeIsView>
                <msh:ifFormTypeIsNotView formName="diary_templateProtocolCategoryForm">
                    <msh:row>
                        <msh:autoComplete property="classif" label="Классификатор" vocName="TemplateClassif" size="60" horizontalFill="true" fieldColSpan="3"/>
                    </msh:row>
                    <msh:row>
                    <%--<msh:textField property="category" label="Номер" size="30" horizontalFill="true" fieldColSpan="3"/>--%>
                        <tags:categoryTree nameAutocompleteClassif="classif" nameFieldCategory="category" label='Категория:'/>
                    </msh:row>
                </msh:ifFormTypeIsNotView>
                <msh:submitCancelButtonsRow colSpan="4"/>
            </msh:panel>



        </msh:form>


    </tiles:put>

    <tiles:put name='side' type='string'>
        <msh:sideMenu>

            <msh:ifFormTypeIsView formName="diary_templateProtocolCategoryForm">
                <msh:sideLink key="ALT+2" params="id" action="/entityParentEdit-diary_templateProtocolCategory" name="Изменить"/>
            </msh:ifFormTypeIsView>

            <hr/>
            <msh:ifFormTypeAreViewOrEdit formName="diary_templateProtocolCategoryForm">
                <msh:sideLink key='ALT+DEL' params="id" action="/entityParentDeleteGoParentView-diary_templateProtocolCategory" name="Удалить"
                              confirm="Удалить категорию?"/>
            </msh:ifFormTypeAreViewOrEdit>
        </msh:sideMenu>
    </tiles:put>

    <tiles:put name='title' type='string'>
        <ecom:titleTrail mainMenu="Template" beginForm="diary_templateProtocolCategoryForm"/>
    </tiles:put>
    <tiles:put name="javascript" type='string'>
        <script type="text/javascript">
            initCategoryTree() ;
        </script>
    </tiles:put>


</tiles:insert>