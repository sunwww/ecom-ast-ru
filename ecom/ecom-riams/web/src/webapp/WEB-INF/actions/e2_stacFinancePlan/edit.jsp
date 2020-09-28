<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://struts.apache.org/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://www.nuzmsh.ru/tags/msh" prefix="msh" %>
<%@ taglib uri="http://www.ecom-ast.ru/tags/ecom" prefix="ecom" %>
<%@ taglib tagdir="/WEB-INF/tags" prefix="tags" %>

<tiles:insert page="/WEB-INF/tiles/mainLayout.jsp" flush="true">
    <tiles:put name="title" type="string">
        <ecom:titleTrail mainMenu="Expert2" beginForm="e2_stacFinancePlanForm" />
    </tiles:put>
    <tiles:put name="body" type="string">
        <msh:ifFormTypeIsView formName="e2_stacFinancePlanForm">
        </msh:ifFormTypeIsView>
        <msh:form action="/entitySaveGoView-e2_stacFinancePlan.do" defaultField="name">
            <msh:hidden property="id" />
            <msh:hidden property="saveType" />
            <msh:panel>
                <msh:separator colSpan="8" label="Общие"/>
                <msh:row>
                </msh:row><msh:row>
                <msh:textField property="startDate"/>
                <msh:textField property="finishDate"/>
            </msh:row>
                <msh:row>
                <msh:autoComplete property="profile" vocName="vocE2MedHelpProfile" size="100"/>
            </msh:row>
                <msh:row>
                <msh:autoComplete property="department" vocName="lpu" size="100"/>
            </msh:row>
                <msh:row>
                    <msh:autoComplete property="bedSubType" vocName="vocBedSubType" size="100"/>
                </msh:row>
                <msh:row>
                    <msh:autoComplete property="ksg" vocName="vocKSGByBedSubType" size="100" parentAutocomplete="bedSubType"/>
                </msh:row>
                <msh:row>
                    <msh:autoComplete property="ksgGroup" vocName="vocKsgGroup" size="100" />
                </msh:row>
                <msh:row>
                    <msh:autoComplete property="vidSluch" vocName="vocE2VidSluch" label="Вид случая" size="50"/>
                </msh:row>
                <msh:row>
                    <msh:autoComplete property="bedProfile" vocName="vocE2FondV020" label="Профиль коек" size="50"/>
                </msh:row>
                <msh:row>
                    <msh:textField property="count" />
                    <msh:textField property="cost" />
                </msh:row>

                <msh:submitCancelButtonsRow colSpan="4" />

            </msh:panel>
        </msh:form>
    </tiles:put>


    <tiles:put name="side" type="string">
        <msh:ifFormTypeIsView formName="e2_stacFinancePlanForm">
            <msh:sideMenu>
                <msh:sideLink key="ALT+2" params="id" action="/entityEdit-e2_stacFinancePlan" name="Изменить" roles="/Policy/E2/Edit" />
                <msh:sideLink params="id" action="/entityDelete-e2_stacFinancePlan" name="Удалить" roles="/Policy/E2/View" />
            </msh:sideMenu>
        </msh:ifFormTypeIsView>
    </tiles:put>
    <tiles:put name="javascript" type="string">
        <msh:ifFormTypeIsView formName="e2_stacFinancePlanForm">
            <script type="text/javascript" src="./dwr/interface/Expert2Service.js"></script>
            <script type="text/javascript">
                function addHistoryNumberToList() {

                }

            </script>

        </msh:ifFormTypeIsView>
    </tiles:put>
</tiles:insert>