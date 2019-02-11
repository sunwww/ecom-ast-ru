<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://struts.apache.org/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://www.nuzmsh.ru/tags/msh" prefix="msh" %>
<%@ taglib uri="http://www.ecom-ast.ru/tags/ecom" prefix="ecom" %>
<%@ taglib tagdir="/WEB-INF/tags" prefix="tags" %>

<tiles:insert page="/WEB-INF/tiles/mainLayout.jsp" flush="true">
    <tiles:put name="title" type="string">
        <ecom:titleTrail mainMenu="Expert2" beginForm="e2_polyclinicCoefficientForm" guid="fbc3d5c0-2bf8-4584-a23f-1e2389d03646" />
    </tiles:put>
    <tiles:put name="body" type="string">
        <msh:ifFormTypeIsView formName="e2_polyclinicCoefficientForm">
                   
        </msh:ifFormTypeIsView>
        <msh:form action="/entitySaveGoView-e2_polyclinicCoefficient.do" defaultField="value" guid="05d29ef5-3f3c-43b5-bc22-e5d5494c5762">
            <msh:hidden property="id" />
            <msh:hidden property="saveType" />
            <msh:hidden property="profile"/>
            <msh:panel>
                <msh:separator colSpan="8" label="Общие"/>
              <msh:row>
                   <msh:autoComplete property="entryType" vocName="vocE2EntrySubType" size="100"/>
                </msh:row>
                <msh:row>
                    <msh:textField property="startDate"/>
                    <msh:textField property="finishDate"/>
                </msh:row>
                <msh:row>
                    <msh:textField property="value"/>

                </msh:row>
                <msh:row>
                    <msh:checkBox property="isMobilePolyclinic"/>
                    <msh:checkBox property="isConsultation"/>
                    <msh:checkBox property="isDiagnosticSpo"/>
                </msh:row>

                <msh:submitCancelButtonsRow guid="submitCancel" colSpan="4" />

            </msh:panel>
        </msh:form>
    </tiles:put>

    <tiles:put name="side" type="string">
        <msh:ifFormTypeIsView formName="e2_polyclinicCoefficientForm" guid="22417d8b-beb9-42c6-aa27-14f794d73b32">
            <msh:sideMenu guid="32ef99d6-ea77-41c6-93bb-aeffa8ce9d55">
                <msh:sideLink key="ALT+2" params="id" action="/entityEdit-e2_polyclinicCoefficient" name="Изменить" roles="/Policy/E2/Edit" />
                <msh:sideLink key="ALT+2" params="id" action="/entityPrepareDelete-e2_polyclinicCoefficient" name="Удалить" roles="/Policy/E2/Delete" />
            </msh:sideMenu>
        </msh:ifFormTypeIsView>
    </tiles:put>
    <tiles:put name="javascript" type="string">
        <msh:ifFormTypeIsView formName="e2_polyclinicCoefficientForm">
            <script type="text/javascript" src="./dwr/interface/Expert2Service.js"></script>
            <script type="text/javascript">
                function addHistoryNumberToList() {

                }

            </script>

        </msh:ifFormTypeIsView>
    </tiles:put>
</tiles:insert>

