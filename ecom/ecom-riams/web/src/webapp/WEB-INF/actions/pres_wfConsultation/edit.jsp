<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://struts.apache.org/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://www.nuzmsh.ru/tags/msh" prefix="msh" %>
<%@ taglib uri="http://www.ecom-ast.ru/tags/ecom" prefix="ecom" %>
<%@ taglib tagdir="/WEB-INF/tags" prefix="tags" %>

<tiles:insert page="/WEB-INF/tiles/mainLayout.jsp" flush="true">

    <script type="text/javascript" src="./dwr/interface/PrescriptionService.js"></script>
    <tiles:put name="body" type="string">
        <!--
        - Конcультация специалиста
        -->

        <tiles:put name="side" type="string">
            <msh:ifFormTypeIsView formName="pres_wfConsultationForm" guid="e20545-4285-a21c-3bb9b4569efc">
                <msh:sideMenu guid="9ec15353-1f35-4c18-b99d-e2b63ecc60c9" title="Конcультация специалиста">
                    <msh:sideLink key="ALT+2" params="id" action="/entityParentEdit-pres_wfConsultation" name="Изменить" roles="/Policy/Mis/Prescription/ServicePrescription/Edit" guid="5a1450f5-7629-4458-b5a5-e5566af6a914" />
                    <msh:sideLink roles="/Policy/Mis/Prescription/ServicePrescription/Edit" params="" action="/javascript:cancelWFPrescription()" name="Отменить" guid="ca5sui7r-9239-47e3-aec4-995462584" key="ALT+2"/>
                    <msh:sideLink key="ALT+DEL" confirm="Удалить?" params="id" action="/entityParentDelete-pres_wfConsultation" name="Удалить" roles="/Policy/Mis/Prescription/ServicePrescription/Delete" guid="7767f5b6-c131-47f4-b8a0-2604050c450f" />
                     </msh:sideMenu>
                <msh:sideMenu title="Перейти" guid="67f8ha758-3ad2-4e6f-a791-4839460955" >
                    <msh:sideLink roles="/Policy/Mis/Prescription/DietPrescription/View" params="id" action="/entityParentListRedirect-pres_wfConsultation" name="К списку консультаций" guid="e98f5d94-fe74-4c43-85b1-267231ff" key="ALT+4"/>
                    <msh:sideLink roles="/Policy/Mis/Prescription/View" params="id" action="/entityParentListRedirect-pres_prescription" name="К списку назначений" guid="e98f5d94-fe74-4c43-85b1-267231ff" key="ALT+5"/>
                </msh:sideMenu>
            </msh:ifFormTypeIsView>
        </tiles:put>
        <msh:form action="/entityParentSaveGoSubclassView-pres_wfConsultation.do" defaultField="" guid="be2c889f-ed1d-4a2b-9cda-9127e9d94885">
            <msh:hidden property="id"/>
            <msh:hidden property="diary"/>
            <msh:hidden property="prescriptionList" />
            <msh:hidden property="saveType"/>
            <msh:panel guid="d1cd0310-bf53-4ce1-9dd5-06388b51ec01">
                <msh:row>
                    <msh:autoComplete property="vocConsultingType" label="Тип консультации" horizontalFill="true" vocName="vocConsultingType" fieldColSpan="3" size="150"/>
                </msh:row>
                <msh:ifFormTypeIsCreate formName="pres_wfConsultationForm" guid="e20545-4285-a21c-3bb9b4569efc">
                    <msh:row>
                        <msh:autoComplete label="Направлен к" property="prescriptCabinet" vocName="workFunctionCons"    horizontalFill="true" fieldColSpan="3"/>
                    </msh:row>
                </msh:ifFormTypeIsCreate>
                <msh:ifFormTypeAreViewOrEdit formName="pres_wfConsultationForm" guid="e20545-4285-a21c-3bb9b4569efc">
                    <msh:row>
                        <msh:autoComplete label="Направлен к" property="prescriptCabinet" vocName="workFunctionCons"  viewOnlyField="true"  horizontalFill="true" fieldColSpan="3"/>
                    </msh:row>
                </msh:ifFormTypeAreViewOrEdit>

                <msh:row guid="d1744869-a80c-4e09-ada3-22a87b8e40b3">
                    <msh:textField property="createDate" label="Дата создания" viewOnlyField="true" guid="76b0f8ed-caa9-496b-a1bd-9a0081c69198" />
                    <msh:textField property="createTime" label="Время создания" viewOnlyField="true" guid="76b0f8ed-caa9-496b-a1bd-9a0081c69198" />
                    <msh:textField property="createUsername" label="Пользователь" viewOnlyField="true" guid="4eac72aa-0de9-427e-b2db-15635bb16fe0" />
                </msh:row>
                <msh:row guid="d1744869-a80c-4e09-ada3-22a87b8e40b3">
                    <msh:textField property="editDate" label="Дата редак." viewOnlyField="true" guid="76b0f8ed-caa9-496b-a1bd-9a0081c69198" />
                    <msh:textField property="editTime" label="Время редак." viewOnlyField="true" guid="76b0f8ed-caa9-496b-a1bd-9a0081c69198" />
                    <msh:textField property="editUsername" label="Пользователь" viewOnlyField="true" guid="4eac72aa-0de9-427e-b2db-15635bb16fe0" />
                </msh:row>
                <msh:row>
                    <msh:textField property="transferDate" label="Дата передачи специалисту" viewOnlyField="true" guid="76b0f8ed-caa9-496b-a1bd-9a0081c69198" />
                    <msh:textField property="transferTime" label="Время передачи специалисту" viewOnlyField="true" guid="76b0f8ed-caa9-496b-a1bd-9a0081c69198" />
                    <msh:textField property="transferUsername" label="Пользователь" viewOnlyField="true" guid="4eac72aa-0de9-427e-b2db-15635bb16fe0" />
                </msh:row>
                <msh:row>
                    <msh:textField property="intakeDate" label="Дата выполнения" viewOnlyField="true" guid="76b0f8ed-caa9-496b-a1bd-9a0081c69198" />
                    <msh:textField property="intakeTime" label="Время выполнения" viewOnlyField="true" guid="76b0f8ed-caa9-496b-a1bd-9a0081c69198" />
                    <msh:autoComplete label="Пользователь" property="intakeSpecial" vocName="workFunction"  viewOnlyField="true"  horizontalFill="true" fieldColSpan="3" size="150"/>
                </msh:row>
                <msh:submitCancelButtonsRow guid="submitCancel" colSpan="3" />
            </msh:panel>
        </msh:form>
    </tiles:put>
    <tiles:put name="title" type="string">
        <ecom:titleTrail mainMenu="StacJournal" beginForm="pres_wfConsultationForm" guid="fb43e71c-1ba9-4e61-8632-a6f4a72b461c" />
    </tiles:put>
    <tiles:put name="javascript" type="string">
        <msh:ifFormTypeIsView formName="pres_wfConsultationForm">
            <script type="text/javascript">
                function cancelWFPrescription() {
                    var reason = ''+ prompt('Введите причину отмены');
                    PrescriptionService.cancelWFPrescription($('id').value, reason, {
                        callback:function (a) {
                            alert(a);
                        }
                    }) ;
                }
            </script>
        </msh:ifFormTypeIsView>
    </tiles:put>
</tiles:insert>