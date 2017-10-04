<%--
  Created by IntelliJ IDEA.
  User: vtsybulin
  Date: 23.05.2017
  Time: 14:30
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://struts.apache.org/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://www.nuzmsh.ru/tags/msh" prefix="msh" %>
<%@ taglib uri="http://www.ecom-ast.ru/tags/ecom" prefix="ecom" %>
<%@ taglib tagdir="/WEB-INF/tags" prefix="tags" %>

<tiles:insert page="/WEB-INF/tiles/mainLayout.jsp" flush="true">

    <tiles:put name='body' type='string'>


        <msh:form action="entityParentSaveGoParentView-mis_patientExternalServiceAccount.do" defaultField="phoneNumber">
            <msh:hidden property="id"/>
            <msh:hidden property="saveType"/>
            <msh:hidden property="patient"/>
            <msh:hidden property="disabled"/>
            <msh:hidden property="sendHistoryAgain"/>

            <msh:panel colsWidth="5%,15%,5%,15%,5%">

                <msh:row>
                    <msh:textField viewOnlyField="true" property="externalCode" label="Код пациента" size="50"/>
                </msh:row>
                 <msh:ifFormTypeAreViewOrEdit formName="mis_patientExternalServiceAccountForm">
                    <msh:row>
                        <msh:textField property="dateStart" label="Дата получения согласия" viewOnlyField="true"/>
                    </msh:row>
                    <msh:row>
                        <msh:textField property="dateTo" label="Дата отзыва согласия" viewOnlyField="true"/>
                    </msh:row>
                 </msh:ifFormTypeAreViewOrEdit>
                <msh:row>
                    <msh:textField property="phoneNumber" size="50" label="Номер телефона (в формате +7ХХХХХХХХХХ)"/>
                </msh:row>
                <msh:row>
                    <msh:textField property="email" size="50"/>
                </msh:row>
                <msh:row>
                    <msh:checkBox property="exportAllHistory" label="Выгрузить всю истории лечения пациента" />
                </msh:row>


                <msh:submitCancelButtonsRow colSpan="1"/>
            </msh:panel>
        </msh:form>
    </tiles:put>

    <tiles:put name='side' type='string'>
        <msh:sideMenu>

            <msh:ifFormTypeIsView formName="mis_patientExternalServiceAccountForm">
                <msh:sideLink roles="/Policy/Mis/Patient/PatientExternalServiceAccount/Edit" key="ALT+2" params="id" action="/entityParentEdit-mis_patientExternalServiceAccount" name="Изменить"/>
            </msh:ifFormTypeIsView>

            <hr/>
            <msh:ifFormTypeAreViewOrEdit formName="mis_patientExternalServiceAccountForm">
                <msh:sideLink roles="/Policy/Mis/Patient/PatientExternalServiceAccount/Delete" key='ALT+DEL' params="id" action="/entityParentDeleteGoParentView-mis_patientExternalServiceAccount" name="Удалить"
                              confirm="Удалить согласие?"/>

                <msh:sideLink roles="/Policy/Mis/Patient/PatientExternalServiceAccount/Edit" key='ALT+3' params="id" action="/javascript:setDisabled()" name="Аннулировать согласие"
                              confirm="Вы хотите аннулировать согласие?"/>
                <msh:sideLink roles="/Policy/Mis/Patient/PatientExternalServiceAccount/Edit" key='ALT+3' params="id" action="/javascript:sendHistoryAgain()" name="Повторно выгрузить историю лечения"
                              confirm="Вы хотите повторно выгрузить историю лечения?"/>
            </msh:ifFormTypeAreViewOrEdit>
        </msh:sideMenu>
    </tiles:put>

    <tiles:put name='title' type='string'>
        <ecom:titleTrail mainMenu="Patient" beginForm="mis_patientExternalServiceAccountForm"/>
    </tiles:put>

    <tiles:put type="string" name="javascript">
        <script type="text/javascript">
            setChecked();
            function setChecked() {
                $('exportAllHistory').checked=true;
            }
            function setDisabled() {


                $('disabled').value="1";
                document.forms[0].submit();
            }
            function sendHistoryAgain() {
                $('sendHistoryAgain').value='1';
                document.forms[0].submit();
            }
        </script>
    </tiles:put>

</tiles:insert>