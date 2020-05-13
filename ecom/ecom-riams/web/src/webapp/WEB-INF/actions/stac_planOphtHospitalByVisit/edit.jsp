<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://struts.apache.org/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://www.nuzmsh.ru/tags/msh" prefix="msh" %>
<%@ taglib uri="http://www.ecom-ast.ru/tags/ecom" prefix="ecom" %>
<%@ taglib tagdir="/WEB-INF/tags" prefix="tags" %>

<tiles:insert page="/WEB-INF/tiles/mainLayout.jsp" flush="true">
    <tiles:put name="body" type="string">
        <msh:form action="/entitySaveGoView-stac_planOphtHospitalByVisit.do" defaultField="patientName" editRoles="/Policy/Mis/MedCase/Stac/Ssl/Planning/Opht/Edit" createRoles="/Policy/Mis/MedCase/Stac/Ssl/Planning/Opht/Create">
        <msh:panel>
        <msh:hidden property="id" />
        <msh:hidden property="saveType" />
            <msh:hidden property="patient" />
            <msh:hidden property="department" />
            <msh:hidden property="workFunction" />
            <msh:hidden property="visit" />
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

            <msh:submitCancelButtonsRow colSpan="3" />
        </msh:panel>
        </msh:form>
    </tiles:put>
    <tiles:put name="title" type="string">
        <ecom:titleTrail mainMenu="Patient" beginForm="stac_planOphtHospitalByVisitForm" />
    </tiles:put>
    <tiles:put name="side" type="string">
        <msh:ifFormTypeIsView formName="stac_planOphtHospitalByVisitForm">
            <msh:sideMenu title="Планирование госпитализаций">
                <msh:sideLink key="ALT+2" params="id" action="/entityEdit-stac_planOphtHospitalByVisit" name="Изменить" roles="/Policy/Mis/MedCase/Stac/Ssl/Planning/Opht/Edit" />
                <msh:sideLink key="ALT+DEL" confirm="Удалить?" params="id" action="/entityParentDeleteGoParentView-stac_planOphtHospitalByVisit" name="Удалить" roles="/Policy/Mis/MedCase/Stac/Ssl/Planning/Opht/Delete" />
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