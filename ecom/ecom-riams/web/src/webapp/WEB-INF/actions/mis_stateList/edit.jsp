<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://struts.apache.org/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://www.nuzmsh.ru/tags/msh" prefix="msh" %>
<%@ taglib uri="http://www.ecom-ast.ru/tags/ecom" prefix="ecom" %>

<tiles:insert page="/WEB-INF/tiles/mainLayout.jsp" flush="true">

    <tiles:put name='body' type='string'>
        <msh:form action="entitySaveGoView-mis_stateList.do" defaultField="id">
            <msh:hidden property="id"/>
            <msh:hidden property="saveType"/>
            <msh:hidden property="lpu"/>

            <msh:panel>
                <msh:row>
                    <msh:autoComplete property="post" label="Должность" vocName="vocPost" fieldColSpan="5" horizontalFill="true" size="50"/>
                </msh:row>
                <msh:row>
                    <msh:ifFormTypeIsView formName="mis_stateListForm">
                        <msh:textField property="fullRate" label="Ставок всего"/>
                    </msh:ifFormTypeIsView>
                    <msh:textField property="budjetRate" label="Ставок бюджетных"/>
                    <msh:textField property="offBudjetRate" label="Ставок внебюджетных"/>
                </msh:row>
                <msh:ifFormTypeIsView formName="mis_stateListForm">
                    <msh:row>
                        <msh:textField property="freeFullRate" label="Ставок вакантных всего"/>
                        <msh:textField property="freeBudjetRate" label="Ставок вакантных бюджетных"/>
                        <msh:textField property="freeOffBudjetRate" label="Ставок вакантных внебюджетных"/>
                    </msh:row>
                </msh:ifFormTypeIsView>
                <msh:submitCancelButtonsRow colSpan="6"/>
            </msh:panel>

        </msh:form>

    </tiles:put>

    <tiles:put name='side' type='string'>
        <msh:sideMenu>
            <msh:ifFormTypeIsView formName="mis_stateListForm">
                <msh:sideLink key="ALT+1" params="id" action="/entityParentList-mis_stateList" name="⇧ Штатное расписание"/>
                <msh:sideLink key="ALT+2" params="id" action="/entityEdit-mis_stateList" name="Изменить"/>
            </msh:ifFormTypeIsView>
            <hr/>
            <msh:ifFormTypeAreViewOrEdit formName="mis_stateListForm">
                <msh:sideLink key='ALT+DEL' params="id" action="/entityDelete-mis_stateList" name="Удалить" confirm="Удалить?"/>
            </msh:ifFormTypeAreViewOrEdit>
        </msh:sideMenu>
    </tiles:put>

    <tiles:put name='title' type='string'>
        <ecom:titleTrail mainMenu="Lpu" beginForm="mis_stateListForm" />
    </tiles:put>


</tiles:insert>