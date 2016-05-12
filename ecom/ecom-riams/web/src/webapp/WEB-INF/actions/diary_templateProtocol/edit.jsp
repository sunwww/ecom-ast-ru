<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://struts.apache.org/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://www.nuzmsh.ru/tags/msh" prefix="msh" %>
<%@ taglib uri="http://www.ecom-ast.ru/tags/ecom" prefix="ecom" %>
<%@ taglib tagdir="/WEB-INF/tags" prefix="tags" %>

<tiles:insert page="/WEB-INF/tiles/mainLayout.jsp" flush="true">

<tiles:put name='body' type='string'>
    <msh:form action="entitySaveGoView-diary_templateProtocol" defaultField="title">
        <msh:hidden property="id"/>
        <msh:hidden property="saveType"/>

        <msh:panel>
            <msh:ifFormTypeIsNotView formName="diary_templateProtocolForm" >

                <msh:row>
                    <msh:textField property="title" label="Заголовок шаблона" horizontalFill="true"/>
                </msh:row>
                <msh:row>
                    <msh:textArea property="text" label="Текст протокола" horizontalFill="true"/>
                    <!--begin-->
                    <tags:keyWord name="text" service="KeyWordService" methodService="getDecryption"/>
                    <!--end-->
                </msh:row>
            </msh:ifFormTypeIsNotView>

            <msh:ifFormTypeIsView formName="diary_templateProtocolForm" >
                <msh:section>
                    <msh:sectionTitle>Категории, в которые входит шаблон</msh:sectionTitle>
                    <msh:sectionContent>
                        <ecom:parentEntityListAll formName="diary_templateProtocolCategoryForm" attribute="category"/>
                        <msh:table name="category" action="entityParentView-diary_templateProtocolCategory.do" idField="id">
                            <msh:tableColumn columnName="ИД" property="id"/>
                            <msh:tableColumn columnName="Id классификатора" property="classif"/>
                            <msh:tableColumn columnName="Название классификатора" property="classifName"/>
                            <msh:tableColumn columnName="Категория" property="categoryName"/>
                        </msh:table>
                    </msh:sectionContent>
                </msh:section>

            </msh:ifFormTypeIsView>
            <msh:submitCancelButtonsRow colSpan="6"/>
        </msh:panel>

    </msh:form>


</tiles:put>

<tiles:put name='side' type='string'>
    <msh:sideMenu>
        <msh:sideLink key="ALT+1" params="" action="/entityList-diary_templateProtocol" name="⇧ Список шаблонов протоколов"/>

        <msh:ifFormTypeIsView formName="diary_templateProtocolForm">
            <msh:sideLink key="ALT+2" params="id" action="/entityEdit-diary_templateProtocol" name="Изменить" roles="/Policy/Diary/Template/Edit"/>
            <msh:sideLink key="ALT+3" params="id" action="/entityParentPrepareCreate-diary_templateProtocolCategory" name="Добавить категорию, к которой относится шаблон"/>

        </msh:ifFormTypeIsView>

        <hr/>
        <msh:ifFormTypeAreViewOrEdit formName="diary_templateProtocolForm">
            <msh:sideLink key='ALT+DEL' params="id" action="/entityDelete-diary_templateProtocol" name="Удалить"
                          confirm="Удалить шаблон протокола?"/>
        </msh:ifFormTypeAreViewOrEdit>

    </msh:sideMenu>
</tiles:put>

<tiles:put name="javascript" type="string">
    <msh:ifFormTypeIsView formName="diary_templateProtocolForm">

        <script type="text/javascript">
//            $('buttonShowAddress').style.display = 'none';
        </script>
    </msh:ifFormTypeIsView>

</tiles:put>


<tiles:put name='title' type='string'>
    <ecom:titleTrail mainMenu="Template" beginForm="diary_templateProtocolForm"/>
</tiles:put>


</tiles:insert>