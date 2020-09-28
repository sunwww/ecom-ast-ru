<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://struts.apache.org/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://www.nuzmsh.ru/tags/msh" prefix="msh" %>
<%@ taglib uri="http://www.ecom-ast.ru/tags/ecom" prefix="ecom" %>
<%@ taglib tagdir="/WEB-INF/tags" prefix="tags" %>

<tiles:insert page="/WEB-INF/tiles/mainLayout.jsp" flush="true">

    <tiles:put name="body" type="string">
        <!--
        - Конcультация специалиста
        -->

        <tiles:put name="side" type="string">
            <msh:ifFormTypeIsView formName="pres_wfConsultationForm">
                <msh:sideMenu title="Конcультация специалиста">
                    <msh:sideLink key="ALT+2" params="id" action="/entityParentEdit-pres_wfConsultation" name="Изменить" roles="/Policy/Mis/Prescription/ServicePrescription/Edit" />
                    <tags:chooseCnslReasonWf name="chooseCnslReasonWf"/>
                    <msh:sideLink roles="/Policy/Mis/Prescription/ServicePrescription/Edit" params="" action="/javascript:showchooseCnslReasonWf(${param.id})" name="Отменить" key="ALT+2"/>
                    <msh:sideLink key="ALT+DEL" confirm="Удалить?" params="id" action="/entityParentDelete-pres_wfConsultation" name="Удалить" roles="/Policy/Mis/Prescription/ServicePrescription/Delete" />
                     </msh:sideMenu>
                <msh:sideMenu title="Перейти" >
                    <msh:sideLink roles="/Policy/Mis/Prescription/DietPrescription/View" params="id" action="/entityParentListRedirect-pres_wfConsultation" name="К списку консультаций" key="ALT+4"/>
                    <msh:sideLink roles="/Policy/Mis/Prescription/View" params="id" action="/entityParentListRedirect-pres_prescription" name="К списку назначений" key="ALT+5"/>
                </msh:sideMenu>
            </msh:ifFormTypeIsView>
        </tiles:put>
        <msh:form action="/entityParentSaveGoSubclassView-pres_wfConsultation.do" defaultField="">
            <msh:hidden property="id"/>
            <msh:hidden property="diary"/>
            <msh:hidden property="prescriptionList" />
            <msh:hidden property="saveType"/>
            <msh:hidden property="transferDate"/>
            <msh:hidden property="transferTime"/>
            <msh:hidden property="transferUsername"/>
            <msh:panel>
                <msh:row>
                    <msh:autoComplete property="vocConsultingType" label="Тип консультации" horizontalFill="true" vocName="vocConsultingType" fieldColSpan="3" size="150"/>
                </msh:row>
                <msh:ifFormTypeIsCreate formName="pres_wfConsultationForm">
                    <msh:row>
                        <msh:autoComplete label="Направлен к" property="prescriptCabinet" vocName="workFunctionCons"    horizontalFill="true" fieldColSpan="3"/>
                    </msh:row>
                </msh:ifFormTypeIsCreate>
                <msh:ifFormTypeAreViewOrEdit formName="pres_wfConsultationForm">
                    <msh:row>
                        <msh:autoComplete label="Направлен к" property="prescriptCabinet" vocName="workFunctionCons"  viewOnlyField="true"  horizontalFill="true" fieldColSpan="3"/>
                    </msh:row>
                </msh:ifFormTypeAreViewOrEdit>

                <msh:row>
                    <msh:textField property="createDate" label="Дата создания" viewOnlyField="true" />
                    <msh:textField property="createTime" label="Время создания" viewOnlyField="true" />
                    <msh:textField property="createUsername" label="Пользователь" viewOnlyField="true" />
                </msh:row>
                <msh:row>
                    <msh:textField property="editDate" label="Дата редак." viewOnlyField="true" />
                    <msh:textField property="editTime" label="Время редак." viewOnlyField="true" />
                    <msh:textField property="editUsername" label="Пользователь" viewOnlyField="true" />
                </msh:row>
                <msh:row>
                    <msh:textField property="intakeDate" label="Дата выполнения" viewOnlyField="true" />
                    <msh:textField property="intakeTime" label="Время выполнения" viewOnlyField="true" />
                    <msh:autoComplete label="Пользователь" property="intakeSpecial" vocName="workFunction"  viewOnlyField="true"  horizontalFill="true" fieldColSpan="3" size="150"/>
                </msh:row>
                <msh:submitCancelButtonsRow colSpan="3" />
            </msh:panel>
        </msh:form>
    </tiles:put>
    <tiles:put name="title" type="string">
        <ecom:titleTrail mainMenu="StacJournal" beginForm="pres_wfConsultationForm" />
    </tiles:put>
</tiles:insert>