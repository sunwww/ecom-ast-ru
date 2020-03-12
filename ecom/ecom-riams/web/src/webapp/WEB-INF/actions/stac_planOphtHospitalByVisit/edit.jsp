<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://struts.apache.org/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://www.nuzmsh.ru/tags/msh" prefix="msh" %>
<%@ taglib uri="http://www.ecom-ast.ru/tags/ecom" prefix="ecom" %>
<%@ taglib tagdir="/WEB-INF/tags" prefix="tags" %>

<tiles:insert page="/WEB-INF/tiles/mainLayout.jsp" flush="true">
    <tiles:put name="body" type="string">
        <msh:form action="/entitySaveGoView-stac_planOphtHospitalByVisit.do" defaultField="patientName" guid="137f576d-2283-4edd-9978-74290e04b873" editRoles="/Policy/Mis/MedCase/Stac/Ssl/Planning/Opht/Edit" createRoles="/Policy/Mis/MedCase/Stac/Ssl/Planning/Opht/Create">
        <msh:panel guid="80209fa0-fbd4-45d0-be90-26ca4219af2e">
        <msh:hidden property="id" guid="95d2afaa-1cdb-46a9-bb71-756352439795" />
        <msh:hidden property="saveType" guid="c409dfd8-f4e7-469f-9322-1982b666a087" />
            <msh:hidden property="patient" guid="95d2afaa-1cdb-46a9-bb71-756352439795" />
            <msh:hidden property="department" guid="95d2afaa-1cdb-46a9-bb71-756352439795" />
            <msh:hidden property="workFunction" guid="95d2afaa-1cdb-46a9-bb71-756352439795" />
            <msh:hidden property="visit" guid="95d2afaa-1cdb-46a9-bb71-756352439795" />
            <msh:row>
                <msh:textField property="phone" label="Телефон" fieldColSpan="3" horizontalFill="true"/>
            </msh:row>
            <msh:row>
                <msh:textField property="dateOKT" label="Дата ОКТ"/>
            </msh:row>
            <msh:row>
                <msh:autoComplete property="eye" label="Глаз" fieldColSpan="3" horizontalFill="true" vocName="vocEye"/>
            </msh:row>
            <msh:row>
                <msh:textField property="dateFrom" label="Предварительная дата госпитализации"/>
            </msh:row>
            <msh:row>
                <msh:textArea property="comment" fieldColSpan="3" horizontalFill="true" label="Замечания"/>
            </msh:row>
            <msh:row>
                <msh:separator label="Дополнительная информация" colSpan="4"/>
            </msh:row>
            <msh:row>
                <msh:label property="createDate" label="Дата создания"/>
                <msh:label property="createTime" label="время"/>
            </msh:row>
            <msh:row>
                <msh:label property="createUsername" label="пользователь"/>
            </msh:row>
            <msh:row>
                <msh:label property="editDate" label="Дата редактирования"/>
                <msh:label property="editTime" label="время"/>
            </msh:row>
            <msh:row>
                <msh:label property="editUsername" label="пользователь"/>
            </msh:row>

            <msh:submitCancelButtonsRow guid="submitCancel" colSpan="3" />
        </msh:panel>
        </msh:form>
    </tiles:put>
    <tiles:put name="title" type="string">
        <ecom:titleTrail mainMenu="Patient" beginForm="stac_planOphtHospitalByVisitForm" guid="fb43e71c-1ba9-4e61-8632-a6f4a72b461c" />
    </tiles:put>
    <tiles:put name="side" type="string">
        <msh:ifFormTypeIsView formName="stac_planOphtHospitalByVisitForm" guid="c7cae1b4-31ca-4b76-ab51-7f75b52d11b6">
            <msh:sideMenu title="Планирование госпитализаций" guid="edd9bfa6-e6e7-4998-b4c2-08754057b0aa">
                <msh:sideLink key="ALT+2" params="id" action="/entityEdit-stac_planOphtHospitalByVisit" name="Изменить" roles="/Policy/Mis/MedCase/Stac/Ssl/Planning/Opht/Edit" guid="5a1450f5-7629-4458-b5a5-e5566af6a914" />
                <msh:sideLink key="ALT+DEL" confirm="Удалить?" params="id" action="/entityParentDeleteGoParentView-stac_planOphtHospitalByVisit" name="Удалить" roles="/Policy/Mis/MedCase/Stac/Ssl/Planning/Opht/Delete" guid="7767f5b6-c131-47f4-b8a0-2604050c450f" />
            </msh:sideMenu>
        </msh:ifFormTypeIsView>
    </tiles:put>
    <tiles:put name="javascript" type="string">
    <script type="text/javascript">
        <msh:ifFormTypeIsCreate formName="stac_planOphtHospitalByVisitForm">
            </script>
            <script type="text/javascript" src="./dwr/interface/PatientService.js"></script>
            <script type="text/javascript" src="./dwr/interface/HospitalMedCaseService.js">/**/</script>
            <script type="text/javascript">
                $('visit').value='${param.id}';
                        PatientService.getIdPhoneByVisitId($('visit').value, {
                            callback: function(res) {
                                if (res != null && res != '[]') {
                                    var Result = JSON.parse(res);
                                    if (typeof(Result.id) !== 'undefined') $('patient').value = Result.id;
                                    if (typeof(Result.phone) !== 'undefined') $('phone').value = Result.phone;
                                }
                            }
                        });
                        HospitalMedCaseService.getOpthalmicDep({
                            callback: function(dep) {
                                if (dep!='')
                                    $('department').value=dep;
                                else alert('Не настроено офтальмологическое отделение!')
                            }
                        });
            </script>
        </msh:ifFormTypeIsCreate>
        </script>
    </tiles:put>
</tiles:insert>