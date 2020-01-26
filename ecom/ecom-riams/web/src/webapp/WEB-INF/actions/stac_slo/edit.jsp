<%@page import="ru.ecom.web.login.LoginInfo"%>
<%@page import="ru.ecom.web.util.ActionUtil"%>
<%@page import="ru.nuzmsh.web.tags.helper.RolesHelper"%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://struts.apache.org/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://www.nuzmsh.ru/tags/msh" prefix="msh" %>
<%@ taglib uri="http://www.ecom-ast.ru/tags/ecom" prefix="ecom" %>
<%@ taglib uri="/WEB-INF/mis.tld" prefix="mis" %>
<%@ taglib tagdir="/WEB-INF/tags" prefix="tags" %>


<%
    ActionUtil.getValueBySql("select id,parent_id from medcase where id="+(String)request.getParameter("id"),"id","parent_id",request);
    Object parent_id = request.getAttribute("parent_id") ;
    if (parent_id==null || (parent_id+"").equals("")) {
        request.setAttribute("parent_id", "0") ;
    }

%>
<tiles:insert page="/WEB-INF/tiles/main${param.short}Layout.jsp" flush="true">
    <tiles:put name="style" type="string">
        <style type="text/css">


            #clinicalDiagnosLabel, #clinicalMkbLabel, #clinicalActuityLabel,#mkbAdcLabel {
                color: blue ;
            }
            #concomitantDiagnosLabel, #concomitantMkbLabel, .concomitantDiags {
                color: green ;
            }

            #concludingDiagnosLabel, #concludingMkbLabel {
                color: black ;
            }
            #complicationDiagnosLabel, #complicationMkbLabel, .complicationDiags {
                color: purple;
            }

            #pathanatomicalDiagnosLabel, #pathanatomicalMkbLabel {
                color: red ;
            }
            .otherTable {
                width:99% ;
            }
            .otherTable tr {
                border: 1px solid ;
            }
        </style>
    </tiles:put>
    <tiles:put name="side" type="string">
        <msh:ifFormTypeIsView formName="stac_sloForm" guid="e2054544-fdd1-4285-a21c-3bb9b4569efc">
            <msh:sideMenu guid="9ec15353-1f35-4c18-b99d-e2b63ecc60c9" title="СЛО">
                <msh:sideLink key="ALT+2" params="id" action="/entityParentEdit-stac_slo" name="Изменить" roles="/Policy/Mis/MedCase/Stac/Ssl/Slo/Edit" guid="5a1450f5-7629-4458-b5a5-e5566af6a914" />
                <msh:sideLink key="ALT+DEL" confirm="Удалить?" params="id" action="/entityParentDelete-stac_slo" name="Удалить" roles="/Policy/Mis/MedCase/Stac/Ssl/Slo/Delete" guid="7767f5b6-c131-47f4-b8a0-2604050c450f" />
                <msh:sideLink roles="/Policy/Mis/MedCase/Stac/Ssl/Slo/Create"  key="CTRL+1"
                              name="Перевод &larr;"   action="/javascript:goTransfer('.do')"
                              title='Перевод' styleId="stac_sloTransfer" />
                <msh:sideLink roles="/Policy/Mis/MedCase/Stac/Ssl/Discharge/Show"  key="CTRL+2"
                              name="Выписка &larr;"   action="/javascript:goDischarge('.do')"
                              title='Выписка' styleId="stac_sslDischarge" />
            </msh:sideMenu>
            <msh:sideMenu guid="9ec15353-1f35-4c18-b99d-e2b63ecc9" title="Добавить">
                <msh:sideLink guid="helloSideLinkNew" roles="/Policy/Mis/MedCase/ClinicExpertCard/Direct/Create"
                              action="/entityParentPrepareCreate-expert_ker_direct" name="Направление на ВК" params="id" />
                <msh:ifNotInRole roles="/Policy/Mis/MedCase/Protocol/CreateOnlyInMedService">
                    <msh:sideLink roles="/Policy/Mis/MedCase/Protocol/Create" name="Дневник специалиста" params="id" action="/entityParentPrepareCreate-smo_visitProtocol" title="Дневник специалиста" guid="11cc057f-b309-4193-9d22-199373cfd28d" />
                </msh:ifNotInRole>
                <msh:sideLink roles="/Policy/Mis/MedCase/Stac/Ssl/Diagnosis/Create" name="Диагноз" params="id" action="/entityParentPrepareCreate-stac_diagnosis" title="Диагноз" guid="c3e59a04-8858-4523-9370-74b16ec784e6" />
                <msh:sideLink roles="/Policy/Mis/Prescription/Prescript/Create" name="Лист назначений" action="/javascript:showCreatePrescriptList('${param.id}','.do')" title="Лист назначений" guid="abd8a59e-4968-4a55-adac-c257c1e8a899" />
                <msh:sideLink roles="/Policy/Mis/MedCase/Stac/Ssl/TemperatureCurve/Create" name="Температурный лист"  action="/javascript:showNewCurve()" title="Добавить температурный лист" guid="df23-45a-43cc-826d-5hfd" />

                <msh:sideLink roles="/Policy/Mis/MedCase/Transfusion/Blood/Create" params="id" action="/entityParentPrepareCreate-trans_blood" name="Переливание донорской крови и её компонентов" title="Добавить донорской крови и её компонентов" />
                <msh:sideLink roles="/Policy/Mis/MedCase/Transfusion/Other/Create" key="ALT+3" params="id" action="/entityParentPrepareCreate-trans_other" name="Переливание кровезамещающих растворов" title="Добавить переливание кровезамещающих растворов" />

                <msh:sideLink roles="/Policy/Mis/MedCase/Stac/Ssl/SurOper/Create" name="Операцию"
                              params="id"  action='/entityParentPrepareCreate-stac_surOperation'  key='Alt+7' title="Операции"
                />
                <msh:sideLink roles="/Policy/Mis/MedCase/Stac/Ssl/Bandage/Create" name="Перевязку"
                              params="id"  action='/entityParentPrepareCreate-stac_bandage'  key='Alt+9' title="Перевязки"
                />
                <msh:sideLink roles="/Policy/Mis/MedCase/MedService/View" name="Мед.услуг по СЛО" action="/printMedServiciesBySMO.do?medcase=${param.id}" params="id"/>
                <msh:sideLink roles="/Policy/Mis/MedCase/Stac/Ssl/HitechMedCase/Create" name="Случай ВМП" action="/entityParentPrepareCreate-stac_vmpCase" params="id" title="Добавить случай ВМП"/>
                <msh:sideLink action="/javascript:watchThisPatient()" name="Наблюдать пациента на дежурстве" title="Наблюдать пациента на дежурстве" roles="/Policy/Mis/MedCase/Stac/Ssl/View"/>
                <msh:sideLink action="/javascript:notWatchThisPatient()" name="НЕ наблюдать пациента на дежурстве" title="НЕ наблюдать пациента на дежурстве" roles="/Policy/Mis/MedCase/Stac/Ssl/View"/>
                <msh:sideLink roles="/Policy/Mis/Pregnancy/CardiacScreening/Create" name="Кардио-скрининг нов. (I этап)" action="/entityParentPrepareCreate-stac_screeningCardiacFirst" params="id" title="Добавить кардио-скрининг нов. (I этап)"/>
                <msh:sideLink roles="/Policy/Mis/Pregnancy/CardiacScreening/Create" name="Кардио-скрининг нов. (II этап)" action="/entityParentPrepareCreate-stac_screeningCardiacSecond" params="id" title="Добавить кардио-скрининг нов. (II этап)"/>
            </msh:sideMenu>
            <msh:ifNotInRole roles="/Policy/Mis/MedCase/Stac/Ssl/ShortEnter">
                <msh:sideMenu title="Показать" guid="c65476c8-6c6a-43c4-a70a-84f40bda76e1">
                    <msh:sideLink styleId="viewShort" action="/javascript:viewOtherVisitsByPatient('.do')" name='ВИЗИТЫ' title="Просмотр визитов по пациенту" key="ALT+4" guid="2156670f-b32c-4634-942b-2f8a4467567c" params="" roles="/Policy/Mis/MedCase/Visit/View" />
                    <msh:sideLink styleId="viewShort" action="/javascript:viewOtherDiagnosisByPatient('.do')" name='ДИАГНОЗЫ' title="Просмотр диагнозов по пациенту" key="ALT+5" guid="2156670f-b32c-4634-942b-2f8a4467567c" params="" roles="/Policy/Mis/MedCase/Diagnosis/View" />
                    <msh:sideLink styleId="viewShort" action="/javascript:viewOtherHospitalMedCase('.do')" name='Госпитализации' title="Просмотр госпитазиций по пациенту" key="ALT+6" guid="2156670f-b32c-4634-942b-2f8a4467567c" params="" roles="/Policy/Mis/MedCase/Stac/Ssl/View" />
                    <msh:sideLink styleId="viewShort"  action="/javascript:getDefinition('entityParentList-expert_ker.do?short=Short&id=${param.id}',null)" name='Врачеб. комиссии' title="Просмотр врачебных комиссий" guid="2156670f-b32c-4634-942b-2f8a4467567c" roles="/Policy/Mis/MedCase/ClinicExpertCard/View" />
                    <msh:sideLink styleId="viewShort" action="/javascript:viewOtherExtMedserviceByPatient('.do')" name='Внешние лаб. исследования' title="Просмотр внешних лабораторных данных по пациенту" key="ALT+5" guid="2156670f-b32c-4634-942b-2f8a4467567c" params="" roles="/Policy/Mis/MedCase/Document/External/Medservice/View" />
                    <msh:sideLink styleId="viewShort" roles="/Policy/Mis/MedCase/QualityEstimationCard/View" name="Экспертные карты" params="id" action="/javascript:getDefinition('entityParentList-expert_card.do?short=Short&id=${param.id}',null)"/>
                    <msh:sideLink roles="/Policy/Mis/Prescription/Prescript/View" name="Сводный лист назначений" params="" action="/javascript:showSvod()" title="Показать все назначения СЛС" guid="7b0b69ae-3b9c-47d9-ab3c-5055fbe6fa9f" />
                    <msh:sideLink roles="/Policy/Mis/MedCase/Stac/Ssl/Diagnosis/View" name="Диагнозы" params="id" action="/entityParentList-stac_diagnosis" title="Показать все диагнозы СЛО" guid="4ac8c095-3853-4150-9e4a-d01b4abc8061" />
                    <msh:sideLink roles="/Policy/Mis/MedCase/Protocol/View" name="Дневники специалистов" params="id" action="/entityParentList-smo_visitProtocol" title="Показать все дневники специалиста" guid="d43123-45ca-43cc-826d-bc85" />
                    <msh:sideLink name="Температурные листы" action="/entityParentList-stac_temperatureCurve" title="Показать все температурные листы" guid="df23-45ca-43cc-826d-5hf5dd" params="id" />
                    <msh:sideLink roles="/Policy/Mis/MedCase/MedService/View" name="Услуги" styleId="viewShort"  action="/javascript:getDefinition('entityParentList-smo_medService.do?short=Short&id=${param.id}')" title="Показать все услуги" guid="df23-45a26d-5hfd" />
                    <msh:sideLink name="Случаи ВМП" action="/entityParentList-stac_vmpCase" title="Показать все случаи ВМП" guid="df23-45ca-43cc-826d-5hf5dd" roles="/Policy/Mis/MedCase/Stac/Ssl/HitechMedCase/View" params="id" />

                    <msh:sideLink roles="/Policy/Mis/MedCase/Transfusion/View" name="Переливание"
                                  params="id"  action='/entityParentList-trans_transfusion'  key='Alt+8'
                                  title='Переливание трансфузионных сред'/>

                    <msh:sideLink roles="/Policy/Mis/MedCase/Stac/Ssl/SurOper/View" name="Операции"
                                  params="id"  action='/entityParentList-stac_surOperation'  key='Alt+7' title='Операции'
                                  styleId="stac_surOperation"
                    />
                    <msh:sideLink roles="/Policy/Mis/MedCase/Stac/Ssl/Bandage/View" name="Перевязки"
                                  params="id"  action='/entityParentList-stac_bandage'  key='Alt+9' title='Перевязки'
                                  styleId="stac_bandage"
                    />
                    <mis:sideLinkForWoman classByObject="MedCase" id="${param.id}" styleId="viewShort"
                                          action="/javascript:getDefinition('entityParentList-preg_childBirth.do?short=Short&id=${param.id}',null)" name="Описание родов" title="Показать описание родов" roles="/Policy/Mis/Pregnancy/ChildBirth/View"/>
                    <msh:sideLink roles="/Policy/Mis/Inspection/View" name="Осмотры"
                                  params="id"  action='/entityParentList-preg_inspection'  key='Alt+0'
                                  title='Медицинские осмотры'/>
                    <tags:QECriteria name="QECriteria" />
                    <msh:sideLink styleId="viewShort" action="/javascript:showQECriteriaCloseDocument(${param.id})" name='Критерии' title="Просмотр критериев" params="" roles="/Policy/Mis/MedCase/Visit/View" />
                    <!--msh:sideLink styleId="viewShort" action="/javascript:viewAssessmentCardsByPatient('.do')" name="Карты оценки"  title="Показать все карты оценки" roles="/Policy/Mis/AssessmentCard/View"/-->
                    <tags:CardiacScreening name="CardiacScreening" />
                    <msh:sideLink styleId="viewShort" action="/javascript:showCardiacScreening(${param.id})" name='Кардиоскрининги новорождённых' title="Кардио-скрининги нов." params="" roles="/Policy/Mis/Pregnancy/CardiacScreening/View" />
                    <tags:identityPatient name="identityPatient" />
                    <msh:sideLink roles="/Policy/Mis/ColorIdentityEdit/PatientSet" name="Браслеты" styleId="viewShort" action="/javascript:showidentityPatient($('parent').value,true)"  title='Браслеты'/>
                    <msh:sideLink roles="/Policy/Mis/MedCase/ActRVK" name="Акт РВК" params="" action="/javascript:showOrCreateAktRvk();" title="Акт РВК"
                    />
                </msh:sideMenu>
                <msh:sideMenu title="Печать">

                    <tags:stac_documentsPrint name="Docum" roles="/Policy/Mis/MedCase/Stac/Ssl/Slo/Print/ConsentImplant" title="Документов" medCase="${param.id}"/>
                    <msh:sideLink roles="/Policy/Mis/MedCase/Protocol/View" name="Дневников по СЛО" action="/printProtocolsBySLO.do?stAll=selected&medcase=${param.id}" params="id"/>
                    <msh:sideLink roles="/Policy/Mis/MedCase/MedService/View" name="Мед.услуг по СЛО" action="/printMedServiciesBySMO.do?medcase=${param.id}" params="id"/>
                    <msh:sideLink roles="/Policy/Mis/Pregnancy/CardiacScreening/View" name="Кардиоскрининг" action="/javascript:window.open('print-cardiacScreeningForm.do?s=PrintNewBornHistoryService&m=printCardiacScreeningForm&id='+${param.id});" params="id"/>
                    <msh:sideLink roles="/Policy/Mis/MedCase/Stac/Ssl/PrintAnestResPatient" name="Печать перс. данных А4" action="/javascript:window.open('print-anestResStat.do?m=printAnestResPatient&s=HospitalPrintService&id=${param.id}');" params="id"/>
                </msh:sideMenu>
            </msh:ifNotInRole>
            <msh:sideMenu title="Администрирование">
                <msh:sideLink name="Ориентировочная цена по ОМС" action=".javascript:getMedcaseCost()" roles="/Policy/E2/Admin"/>
                <msh:sideLink confirm="Вы точно хотите объединить несколько СЛО?" name="Объединить со след. СЛО" action=".javascript:unionSloWithNextSlo()" roles="/Policy/Mis/MedCase/Stac/Ssl/Slo/UnionSlo"/>
                <tags:mis_changeServiceStream name="CSS" service="HospitalMedCaseService" title="Изменить поток обслуживания" roles="/Policy/Mis/MedCase/Stac/Ssl/Slo/ChangeServiceStream" />
            </msh:sideMenu>
            <msh:sideMenu title="Перейти" guid="ad80d37d-5a0b-44e3-a4ae-3df85de3d1c3">
                <msh:sideLink styleId="viewShort" action='/javascript:getDefinition("js-smo_draftProtocol-list.do?short=Short", null);' name='Черновики' title="Просмотр черновиков специалиста" key="ALT+4" roles="/Policy/Mis/MedCase/Protocol/View" />
                <msh:sideLink action='/js-stac_slo-list_edit_protocol.do' name='На редакцию' title="Просмотр дневников для редакции" roles="/Policy/Mis/MedCase/Protocol/View" />
                <msh:sideLink params="id" action="/entityParentListRedirect-stac_slo" name="⇧Cписок СЛО" roles="/Policy/Mis/MedCase/Stac/Ssl/Slo/View" guid="f6a4b395-ccee-4db6-aad7-9bc15aa2f7b8" title="Перейти к списку случаев лечения в отделении" />
                <msh:sideLink
                        roles="/Policy/Mis/MedCase/Stac/Journal/ByDepartmentAdmission"
                        action="/stac_journalByDepartmentAdmission" name="Журнал по направленным в отделение" />
                <msh:sideLink
                        roles="/Policy/Mis/MedCase/Stac/Journal/CurrentByUserDepartment"
                        action="/stac_journalCurrentByUserDepartment" name="Журнал по состоящим в отделение пациентам" />
                <msh:sideLink
                        roles="/Policy/Mis/MedCase/Stac/Journal/ByCurator"
                        action="/stac_journalByCurator" name="Журнал по лечащему врачу" />

            </msh:sideMenu>
        </msh:ifFormTypeIsView>
    </tiles:put>
    <tiles:put name="body" type="string">
        <!--
        - Случай стационарного лечения в отделении
        -->
        <msh:form action="/entityParentSaveGoView-stac_slo.do" defaultField="dateStart" guid="be2c889f-ed1d-4a2b-9cda-9127e9d94885">
            <msh:hidden property="id" guid="d10f460a-e434-45a5-90f0-b0a7aed00ec6" />
            <msh:hidden property="prevMedCase" guid="710eb92b-fc3f-4390-b32df6837280" />
            <msh:hidden property="parent" guid="710eb92b-fc3f-4b44-9390-b32df6837280" />
            <msh:hidden property="patient" guid="9d908e88-e051-4d0a-8da6-3f5f4b226493" />
            <msh:hidden property="saveType" guid="bd322f07-c944-4587-a963-a09db2b93caf" />
            <msh:hidden property="lpuAndDate" guid="9cc5ff9f-b68c-423a-be34-50ebeecf4b18" />
            <msh:hidden property="lpu" guid="756525c0-3c91-41da-b2ba-27ebdbdc001b" />
            <msh:hidden property="dateFinish"/>
            <msh:hidden property="dischargeTime"/>
            <msh:hidden property="transferDate"/>
            <msh:hidden property="transferTime"/>
            <msh:hidden property="transferDepartment"/>
            <msh:hidden property="kindHighCare"/>
            <msh:hidden property="methodHighCare"/>
            <msh:hidden property="targetHospType"/>
            <msh:ifFormTypeIsCreate formName="stac_sloForm">
                <msh:hidden property="emergency"/>
            </msh:ifFormTypeIsCreate>
            <msh:ifFormTypeAreViewOrEdit formName="stac_sloForm">
            <msh:ifFormTypeIsNotView formName="stac_sloForm">
                <msh:hidden property="emergency"/>
            </msh:ifFormTypeIsNotView>
            </msh:ifFormTypeAreViewOrEdit>
            <msh:ifNotInRole roles="/Policy/Mis/MedCase/Stac/Slo/ForceCreatePrescriptionList">
                <msh:hidden property="diet"/>
                <msh:hidden property="mode"/>
            </msh:ifNotInRole>
            <msh:panel guid="d1cd0310-bf53-4ce1-9dd5-06388b51ec01" colsWidth="5%,5%,5%,75%,5%,5%">
                <msh:ifFormTypeAreViewOrEdit formName="stac_sloForm">
                    <msh:row >
                        <msh:label property="statCardBySLS" label="Номер стат.карты" labelColSpan="1"/>
                        <td colspan="2">
                            <msh:link action="entityParentListRedirect-stac_slo.do?id=${param.id}" roles="/Policy/Mis/MedCase/Stac/Ssl/Slo/View">⇧Cписок СЛО</msh:link>
                        </td>
                    </msh:row>
                </msh:ifFormTypeAreViewOrEdit>
                <msh:separator label="Переведен из отделения" colSpan="6" guid="d4313623-45ca-43cc-826d-bc1b66526744" />
                <msh:row guid="f244aba5-68fb-4ccc-9982-7b4480cca147">
                    <msh:autoComplete viewAction="entityParentView-stac_slo.do" shortViewAction="entityShortView-stac_slo.do" parentId="stac_sloForm.parent" viewOnlyField="true"  vocName="sloBySls" property="prevMedCase" label="СЛО" fieldColSpan="6" horizontalFill="true" guid="109f7264-23b2-42c0-ba47-65d90747816c" size="30" />
                </msh:row>
                <msh:separator label="Поступление в отделение" colSpan="6" guid="d4313623-45ca-43cc-826d-bc1b66526744" />
                <msh:row guid="d6321f29-4e95-42a5-9063-96df480e55a8">
                    <msh:textField property="dateStart" label="Дата поступления" />
                    <msh:textField property="entranceTime" label="время" />
                </msh:row>

                <msh:row guid="f244aba5-68fb-4ccc-9982-7b4480cca147">
                    <msh:autoComplete parentId="stac_sloForm.lpu" vocName="vocLpuHospOtd" property="department" label="Отделение" fieldColSpan="6" horizontalFill="true" guid="109f7264-23b2-42c0-ba47-65d90747816c" size="30" />
                </msh:row>
                <msh:row guid="f2-68fb-4ccc-9982-7b4480cca147">
                    <msh:autoComplete vocName="serviceStreamByDepAndDate" property="serviceStream" label="Поток обслуживания" fieldColSpan="6" horizontalFill="true" guid="109f7264-23b216c"/>
                </msh:row>
                <msh:row guid="f2aba5-68fb-4ccc-9982-7b4480cmca147">
                    <msh:autoComplete vocName="bedFundByDepAndStreamAndDate" property="bedFund" label="Профиль коек" fieldColSpan="6" horizontalFill="true" guid="1064-23b2-42c0-ba47-65d90747816c" size="30" />
                </msh:row>
                <msh:ifNotInRole roles="/Policy/Mis/MedCase/Stac/Ssl/ShortEnter">
                    <msh:row guid="9b781235-66ad-4f9d-991b-afb9aedfb7a8">
                        <msh:autoComplete property="roomNumber" vocName="hospitalRoomByLpu" label="№палаты" parentId="stac_sloForm.department"/>
                        <msh:autoComplete property="bedNumber" vocName="hospitalBedByRoom" label="№ койки" parentAutocomplete="roomNumber"/>
                    </msh:row>
                    <msh:row>
                    </msh:row>
                </msh:ifNotInRole>
                <msh:ifInRole roles="/Policy/Mis/MedCase/Stac/Ssl/ShortEnter">
                    <msh:hidden property="roomNumber"/>
                    <msh:hidden property="roomType"/>
                    <msh:hidden property="bedNumber"/>
                </msh:ifInRole>
                <msh:ifFormTypeIsView formName="stac_sloForm">
                    <msh:row>
                        <msh:checkBox label="Экстренно" property="emergency" guid="dhcahb04f82b" viewOnlyField="true" />
                    </msh:row>
                </msh:ifFormTypeIsView>
                <msh:ifFormTypeIsCreate formName="stac_sloForm" guid="e2054544-fdd1-4285-a21c-3bb9b4569efc">
                    <msh:ifInRole roles="/Policy/Mis/MedCase/Stac/Slo/ForceCreatePrescriptionList">
                        <msh:row>
                            <msh:autoComplete property="diet" vocName="Diet" />
                            <msh:autoComplete property="mode" vocName="vocModePrescription"  fieldColSpan="2"/>
                        </msh:row>
                    </msh:ifInRole>
                </msh:ifFormTypeIsCreate>
                <msh:row guid="1d32ce64-883b-4be9-8db1-a421709f4470">
                    <msh:autoComplete vocName="workFunctionByLpu" parentId="stac_sloForm.department" property="ownerFunction" label="Лечащий врач" fieldColSpan="6" horizontalFill="true" guid="968469ce-dd95-40f4-af14-deef6cd3e4f3" viewAction="entitySubclassView-work_workFunction.do" size="30" />
                </msh:row>
                <msh:ifInRole roles="/Policy/Mis/MedCase/Stac/Ssl/Slo/OmcStandart">

                    <msh:row>
                        <msh:autoComplete property="omcStandart" fieldColSpan="6" label="ОМС стандарт (врач)" horizontalFill="true" vocName="omcStandart"/>
                    </msh:row>
                    <msh:row>
                        <msh:autoComplete property="omcStandartExpert" fieldColSpan="6" label="ОМС стандарт (эксперт)" horizontalFill="true" vocName="omcStandart"/>
                    </msh:row>
                </msh:ifInRole>
                <msh:ifNotInRole roles="/Policy/Mis/MedCase/Stac/Ssl/Slo/OmcStandart">
                    <msh:hidden property="omcStandart"/>
                    <msh:hidden property="omcStandartExpert"/>
                </msh:ifNotInRole>


                <msh:row>
                    <msh:autoComplete vocName="vocIllnesPrimary" fieldColSpan="6" property="clinicalActuity" horizontalFill="true" label="Характер заболевания"/>
                </msh:row>
                <msh:row>
                    <msh:autoComplete vocName="vocIdc10" label="МКБ клин.диаг." property="clinicalMkb" fieldColSpan="6" horizontalFill="true"/>
                </msh:row>
                <msh:row>
                    <msh:textField label="Клинический диагноз" property="clinicalDiagnos" fieldColSpan="6" horizontalFill="true"/>
                </msh:row>
                <msh:row>
                    <msh:autoComplete property="mkbAdc" vocName="vocMkbAdc" parentAutocomplete="clinicalMkb" label="Доп.код клин.диаг." fieldColSpan="6" horizontalFill="true"/>
                </msh:row>
                <msh:hidden property="complicationDiags"/>
                <msh:hidden property="concomitantDiags"/>
                <msh:ifFormTypeIsNotView formName="stac_sloForm">
                    <msh:row>
                        <msh:autoComplete vocName="vocIdc10" label="МКБ-10 клин.диаг.соп." property="concomitantMkb" fieldColSpan="6" horizontalFill="true"/>
                    </msh:row>

                    <msh:row>
                        <msh:textField label="Клин. диаг. сопут" property="concomitantDiagnos" fieldColSpan="5" horizontalFill="true"/>
                        <td><input type="button" value="+ диагноз" onclick="addDiag('concomitant')"/></td>
                    </msh:row>
                </msh:ifFormTypeIsNotView>
                <tr><td colspan="7">
                    <table class="otherTable" id='otherconcomitantDiagsTable'></table>
                </td></tr>
                <msh:ifFormTypeIsNotView formName="stac_sloForm">
                    <msh:row>
                        <msh:autoComplete vocName="vocIdc10" label="МКБ-10 клин.диаг.осл." property="complicationMkb" fieldColSpan="6" horizontalFill="true"/>
                    </msh:row>
                    <msh:row>
                        <msh:textField label="Клин. диаг. осл." property="complicationDiagnos" fieldColSpan="5" horizontalFill="true"/>
                        <td><input type="button" value="+ диагноз" onclick="addDiag('complication')"/></td>
                    </msh:row>
                </msh:ifFormTypeIsNotView>
                <msh:row><td colspan="7">
                    <table class="otherTable" id='othercomplicationDiagsTable'></table>
                </td></msh:row>

                <msh:ifFormTypeIsView formName="stac_sloForm">
                    <msh:row>
                        <msh:separator label="Перевод в другое отделение" colSpan="6" guid="dd7185d0-e499-4307-9e58-6ef41d83c2b0" />
                    </msh:row>
                    <msh:row guid="a3509d1f-9324-4997-a7c3-6ca8f12a9347">
                        <msh:textField viewOnlyField="true"  property="transferDate" label="Дата" guid="f8f5c912-00b8-4fd8-87b9-abe417212d78" />
                        <msh:textField viewOnlyField="true" property="transferTime" label="Время" guid="c04ab410-42df-4f5b-b365-b4acf17a2616" />
                    </msh:row>
                    <msh:row guid="72adfc11-ef9b-47c0-8eb4-a23ee9e84ed8">
                        <msh:autoComplete viewOnlyField="true" vocName="vocLpuHospOtd" property="transferDepartment" label="Отделение" fieldColSpan="6" horizontalFill="true" guid="f793944a-6afe-4c26-82f3-50532049a8bc" />
                    </msh:row>
                    <msh:row guid="f2a5-68fb-4ccc-9982-7b4447">
                        <msh:autoComplete viewOnlyField="true" vocName="vocHospType" property="targetHospType" label="Куда переведен" fieldColSpan="6" horizontalFill="true" guid="10964-23b2-42c0-ba47-6547816c" />
                    </msh:row>
                    <msh:separator label="Выписка (производится по СЛС)" colSpan="" guid="a5bd9711-b033-4104-b794-0ca3ebc8b827" />
                    <msh:row guid="21b4ac48-1773-410d-b85f-537680420aa4">
                        <msh:textField property="dateFinish" label="Дата" viewOnlyField="true" guid="bb7b87a8-c542-47ef-93b6-91106abf9f19" />
                        <msh:textField property="dischargeTime" label="Время" viewOnlyField="true" guid="a8bfc5ac-8d19-4656-a30b-bd87da1918df" />
                    </msh:row>
                </msh:ifFormTypeIsView>
                <msh:row>
                    <msh:separator label="Дополнительно" colSpan="4"/>
                </msh:row>
            </msh:panel>
            <msh:panel>
                <msh:row>
                    <msh:label property="createDate" label="Дата создания"/>
                    <msh:label property="createTime" label="время"/>
                    <msh:label property="username" label="пользователь" guid="2258d5ca-cde5-46e9-a1cc-3ffc278353fe" />
                </msh:row>
                <msh:row>
                    <msh:label property="editDate" label="Дата редак."/>
                    <msh:label property="editTime" label="время"/>
                    <msh:label property="editUsername" label="пользователь" guid="2258d5ca-cde5-46e9-a1cc-3ffc278353fe" />
                </msh:row>
                <msh:submitCancelButtonsRow colSpan="3" functionSubmit="check_diags('') ;" />
            </msh:panel>
        </msh:form>

        <%
            String login = LoginInfo.find(request.getSession(true)).getUsername() ;
            //request.setAttribute("login", login) ;
            ActionUtil.getValueBySql("select wf.id,w.lpu_id from SecUser su left join WorkFunction wf on su.id=wf.secUSer_id left join Worker w on wf.worker_id=w.id where su.login='"+login+"'"
                    ,"wf_id","lpu_wf",request);
            boolean isRoleVk = RolesHelper.checkRoles("/Policy/Mis/MedCase/Stac/Journal/ControlVk", request) ;
            Object wf_lpu = request.getAttribute("lpu_wf") ;
            if (wf_lpu==null || (wf_lpu+"").equals("")) {
                request.setAttribute("lpu_wf", "0") ;
            }
            if (isRoleVk) {
                request.setAttribute("edit_vk_all", "vk") ;
            }


        %>
        <msh:ifFormTypeIsView formName="stac_sloForm" guid="48eb9700-d07d-4115-a476-a5a5e">
            <tags:temperatureCurve name="New"  />

            <tags:pres_newPrescriptList name="Create" parentID="${param.id}" />

            <msh:ifInRole roles="/Policy/Mis/MedCase/Protocol/View" guid="932601e0-0d99-4b63-8f44-2466f6e91c0f">
                <msh:section title="Дневники специалистов (последние 50).
        <a href='entityParentPrepareCreate-smo_visitProtocol.do?id=${param.id }'>Добавить</a>&nbsp;&nbsp;
        <a href='entityParentList-smo_visitProtocol.do?id=${param.id }'>Полный список дневников</a>&nbsp;&nbsp;
        <a href='printProtocolsBySLO.do?medcase=${param.id }&id=${param.id}&stAll=selected'>Печать (весь список)</a>&nbsp;&nbsp;
        <a href='printProtocolsBySLO.do?medcase=${param.id }&id=${param.id}&stNoPrint=selected'>Печать (список нераспеч.)</a>
        " guid="1f21294-8ea0-4b66-a0f3-62713c1">

                    <ecom:webQuery maxResult="50"  name="protocols" nameFldSql="protocols_sql"  nativeSql="
      select d.id as did, to_char(d.dateRegistration,'dd.mm.yyyy') ||' '|| cast(d.timeRegistration as varchar(5)) as dtimeRegistration, d.record 
      ||'<'||'br'||'/>'|| vwf.name||' '||pw.lastname||' '||pw.firstname||' '||pw.middlename as doctor
      ,case when aslo.dtype='Visit' then 'background:#F6D8CE;color:black;' 
      when aslo.dtype='DepartmentMedCase' and slo.department_id!=aslo.department_id then 'background:#E0F8EC;color:black;'
      else '' end as f4style
      ,case when sls.datefinish is null and aslo.dtype!='Visit' and w.lpu_id='${lpu_wf}'  and (select count(*) from DiaryMessage dm where dm.diary_id=d.id and dm.createDate>current_date-2)=0 then d.id else null end as cntmessage
      ,case when sls.datefinish is null and aslo.dtype!='Visit' and 'vk'='${edit_vk_all}' and (select count(*) from DiaryMessage dm where dm.diary_id=d.id and dm.createDate>current_date-2)=0 then d.id else null end as cntmessage1
      , (select list(vdd.name) from DiaryMessage dm left join VocDefectDiary vdd on vdd.id=dm.defect_id where dm.diary_id=d.id and dm.createDate>current_date-2) as message
      from MedCase slo
      left join medcase sls on sls.id = slo.parent_id
      left join MedCase aslo on aslo.parent_id=slo.parent_id
      left join Diary as d on aslo.id=d.medCase_id
      left join WorkFunction wf on wf.id=d.specialist_id
      left join Worker w on w.id=wf.worker_id
      left join Patient pw on pw.id=w.person_id
      
      left join VocWorkFunction vwf on vwf.id=wf.workFunction_id
            	where slo.id='${param.id}' and upper(d.DTYPE)='PROTOCOL'
            	and upper(aslo.dtype) in ('DEPARTMENTMEDCASE','HOSPITALMEDCASE')
            	order by  d.dateRegistration desc,  d.timeRegistration desc"/>

                    <msh:tableNotEmpty name="protocols">
                        <table>
                            <msh:toolbar>
                                <tr>
                                    <td colspan="12">
                                        <button onclick="getDefinition('js-stac_slo-list_protocols.do?id=${param.id}&type=1&short=Page',1,'divprotocolslo${param.id}')">Дневники</button>
                                        <button onclick="getDefinition('js-stac_slo-list_protocols.do?id=${param.id}&type=2&short=Page',1,'divprotocolslo${param.id}')">Лаб.исслед.</button>
                                        <button onclick="getDefinition('js-stac_slo-list_protocols.do?id=${param.id}&type=3&short=Page',1,'divprotocolslo${param.id}')">Диаг.исслед.</button>
                                        <button onclick="getDefinition('js-stac_slo-list_protocols.do?id=${param.id}&type=4&short=Page',1,'divprotocolslo${param.id}')">Заголовки</button>
                                        <button onclick="getDefinition('js-stac_slo-list_protocols.do?id=${param.id}&short=Page',1,'divprotocolslo${param.id}')">Все</button>
                                    </td>
                                </tr>
                            </msh:toolbar>
                        </table>
                    </msh:tableNotEmpty>
                    <div id='divprotocolslo${param.id}'>
                        <msh:table hideTitle="false" styleRow="4" idField="1" name="protocols" action="entityParentView-smo_visitProtocol.do" guid="d0267-9aec-4ee0-b20a-4f26b37">
                            <msh:tableButton property="5" buttonFunction="checkErrorProtocol"
                                             buttonName="На редакцию врачу" buttonShortName="На редак."
                                             hideIfEmpty="true" role="/Policy/Mis/MedCase/Stac/Journal/Control"/>
                            <msh:tableButton property="6" buttonFunction="checkErrorProtocolVk"
                                             buttonName="На редакцию врачу VK" buttonShortName="На редак."
                                             hideIfEmpty="true" role="/Policy/Mis/MedCase/Stac/Journal/ControlVk"/>
                            <msh:tableColumn columnName="#" property="sn"/>
                            <msh:tableColumn columnName="Дата и время" property="2"/>
                            <msh:tableColumn columnName="Протокол" property="3" cssClass="preCell"/>
                        </msh:table>
                    </div>
                </msh:section>

            </msh:ifInRole>
            <msh:ifInRole roles="/Policy/Mis/MedCase/Stac/Ssl/Diagnosis/View" guid="b0ceb3e4-a6a2-41fa-be6b-ea222196a33d">
                <ecom:webQuery name='diagnosis' nativeSql="select d.id as did, d.establishDate as destablishDate, vrt.name as vrtinfo
		, vpd.name as vpdname, d.name as dname, mkb.code
		,vwf.name|| ' '||wp.lastname||' '||wp.firstname||' '||wp.middlename as doctor
		,case 
		when vpd.code='4' then 'color: purple;'
		when vpd.code='3' then 'color: green;'
		when vpd.code='1' then 'color: blue;'
		 end as style
		from Diagnosis d
		left join VocDiagnosisRegistrationType vrt on vrt.id=d.registrationType_id
		left join VocPriorityDiagnosis vpd on vpd.id=d.priority_id
		left join VocIdc10 mkb on mkb.id=d.idc10_id
		left join WorkFunction wf on wf.id=d.medicalWorker_id
		left join VocWorkFunction vwf on vwf.id=wf.workFunction_id
		left join Worker w on w.id=wf.worker_id
		left join Patient wp on wp.id=w.person_id
		where d.medcase_id='${param.id}'
		order by vpd.code,mkb.code
		"/>


                <msh:section title="Диагнозы. <a href='entityParentPrepareCreate-stac_diagnosis.do?id=${param.id }'> Добавить новый диагноз</a>" guid="1f214-8ea0-4b66-a0f3-62713c1">
                    <msh:table name="diagnosis" action="entityParentView-stac_diagnosis.do" idField="1" guid="b621e361-1e0b-4ebd-9f58-b7d916" styleRow="8">
                        <msh:tableColumn property="sn" columnName="#"/>
                        <msh:tableColumn columnName="Тип регистрации" property="3" guid="6682eeef-105f-43a0-be61-30a865f27972" />
                        <msh:tableColumn columnName="Приоритет" property="4" guid="6682eeef-12" />
                        <msh:tableColumn columnName="Наименование" property="5" guid="6682eeef-105f-43a0-be61-30a865f27972" />
                        <msh:tableColumn columnName="Код МКБ" property="6" guid="f34e1b12-3392-4978-b31f-5e54ff2e45bd" />
                        <msh:tableColumn columnName="Специалист" property="7" guid="-3392-4978-b31f-5e54ff2e45" />
                    </msh:table>
                </msh:section>
            </msh:ifInRole>
            <msh:ifInRole roles="/Policy/Mis/MedCase/Stac/Ssl/SurOper/View">
                <ecom:webQuery name="allSurgOper" nativeSql="select so.id
          ,to_char(so.operationDate,'dd.mm.yyyy')||' '||coalesce(cast(so.operationTime as varchar(5)),'') as soperationTime
          ,ms.code||' '||ms.name as voname
          , d.name as whoIs  
          , vwf.name||' '||wp.lastname||' '||wp.firstname||' '||wp.middlename as doctor
          ,substring(so.operationText,1,100)||' ...' as operationText
          ,pat.lastname||' '||pat.firstname||' '||pat.middlename as fioan
          from SurgicalOperation as so 
          left join MedService ms on ms.id=so.medService_id
          left join medcase parent on parent.id=so.medcase_id
           left join anesthesia a on a.surgicaloperation_id=so.id
      		left join MedCase aslo on aslo.id=so.medCase_id
      		left join MedCase slo on slo.parent_id=aslo.parent_id
          left join MisLpu d on d.id=so.department_id 
          left join WorkFunction wf on wf.id=so.surgeon_id
          left join Worker w on w.id=wf.worker_id
          left join VocWorkFunction vwf on vwf.id=wf.workFunction_id
          left join Patient wp on wp.id=w.person_id
          left join workfunction wfan on wfan.id=a.anesthesist_id
left join worker wan on wan.id=wfan.worker_id
left join Patient pat on pat.id=wan.person_id
          where  
           slo.id=${param.id}
          order by so.operationDate
          "/>
                <msh:tableNotEmpty name="allSurgOper">
                    <msh:section title="Хирургические операции " createUrl="entityParentPrepareCreate-stac_surOperation.do?id=${param.id}"
                                 createRoles="/Policy/Mis/MedCase/Stac/Ssl/SurOper/Create">
                        <msh:table viewUrl="entityShortView-stac_surOperation.do"
                                   editUrl="entityParentEdit-stac_surOperation.do"
                                   name="allSurgOper" action="entityParentView-stac_surOperation.do" idField="1">
                            <msh:tableColumn columnName="#" property="sn"/>
                            <msh:tableColumn columnName="Дата и время" property="2"/>
                            <msh:tableColumn columnName="Операция" property="3"/>
                            <msh:tableColumn columnName="Хирург" property="5"/>
                            <msh:tableColumn cssClass="preCell" property="6" columnName="Протокол операции"/>
                            <msh:tableColumn columnName="Отделение" property="4"/>
                            <msh:tableColumn columnName="Анестезиолог" property="7"/>
                        </msh:table>
                    </msh:section>
                </msh:tableNotEmpty>
            </msh:ifInRole>
            <msh:ifInRole roles="/Policy/Mis/MedCase/Stac/Ssl/Bandage/View">
                <ecom:webQuery name="allBandage" nativeSql="select so.id,
            to_char(so.startdate,'dd.mm.yyyy')||' '||coalesce(cast(so.starttime as varchar(5)),'') as datetime,
            a.duration,vam.name as vamname,va.name as vaname,substring(so.text,1,100)||' ...' as text,pat.lastname||' '||pat.firstname||' '||pat.middlename as fioan from medicalmanipulation so
            left join MedService ms on ms.id=so.medService_id
            left join medcase parent on parent.id=so.medcase_id
            left join MisLpu d on d.id=so.thedepartment_id
            left join anesthesia a on a.manipulation_id=so.id
            left join vocanesthesiamethod vam on vam.id=a.method_id
            left join vocanesthesia va on va.id=a.type_id
left join workfunction wfan on wfan.id=a.anesthesist_id
left join worker wan on wan.id=wfan.worker_id
left join Patient pat on pat.id=wan.person_id
          where
           so.medCase_id=${param.id} and so.dtype='Bandage'
          order by so.startdate
          "/>
                <msh:tableNotEmpty name="allBandage">
                    <msh:section title="Перевязки " createUrl="entityParentPrepareCreate-stac_bandage.do?id=${param.id}"
                                 createRoles="/Policy/Mis/MedCase/Stac/Ssl/Bandage/Create">
                        <msh:table viewUrl="entityShortView-stac_bandage.do"
                                   editUrl="entityParentEdit-stac_bandage.do"
                                   name="allBandage" action="entityParentView-stac_bandage.do" idField="1">
                            <msh:tableColumn columnName="#" property="sn"/>
                            <msh:tableColumn columnName="Дата и время" property="2"/>
                            <msh:tableColumn columnName="Длительность" property="3"/>
                            <msh:tableColumn columnName="Метод" property="4"/>
                            <msh:tableColumn columnName="Тип" property="5"/>
                            <msh:tableColumn columnName="Протокол перевязки" property="6"/>
                            <msh:tableColumn columnName="Анестезиолог" property="7"/>
                        </msh:table>
                    </msh:section>
                </msh:tableNotEmpty>
            </msh:ifInRole>
            <msh:ifInRole roles="/Policy/Mis/MedCase/Transfusion/View">
                <msh:tableNotEmpty name="transfusions">
                    <ecom:webQuery name="select tr.id as tr1id, tr.journalNumber as tr2journalnumber
      	,coalesce(vbp.name,votr.name) as tr3information
      	,tr.preparationDate as tr4preparationDate
      	,tr.startDate as tr5startDate
      	,tr.doze as tr6doze
      	,vwf.name||' '||wp.lastname||' '||wp.firstname||' '||wp.middlename as tr7executorInfo
      	from transfusion tr 
      	left join VocBloodPreparation vbp on vbp.id=tr.bloodPreparation_id
      	left join VocOtherTransfusPreparation votr on votr.id=tr.otherPreparation_id
      	left join WorkFunction wf on wf.id=tr.executor_id left join worker w on w.id=wf.worker_id left join patient wp on wp.id=w.person_id left join vocworkfunction vwf.id=wf.workfunction_id
      	where tr.medcase_id=${param.id}"/>
                    <msh:section title="Переливание">
                        <msh:table name="transfusions" action="entitySubclassView-trans_transfusion.do" idField="1">
                            <msh:tableColumn columnName="Номер в журнале" property="2" guid="ed7e6ec7-524e-4b87-8b2c-5a722792a123" />
                            <msh:tableColumn columnName="Трансфузионная среда" property="3" guid="c4b30e10-9ca0-42b1-94fb-88cf0f7afa2e" />
                            <msh:tableColumn columnName="Дата приготовления" property="" guid="1ef2e314-6eb6-4c85-be47-ca392566d371" />
                            <msh:tableColumn columnName="Изготовитель" property="4" guid="dk29-5653-4920-bb78-168ha34" />
                            <msh:tableColumn columnName="Дата начала" property="5" guid="2976f5c7-3844-4ae2-be91-2a395cae0f1f" />
                            <msh:tableColumn columnName="Доза" property="6" guid="d9642df9-5653-4920-bb78-1622cbeefa34" />
                            <msh:tableColumn columnName="Исполнитель" property="7" guid="8e832f90-6905-44cf-952e-76495689c35b" />
                        </msh:table>
                    </msh:section>
                </msh:tableNotEmpty>
            </msh:ifInRole>

            <msh:ifInRole roles="/Policy/Mis/Prescription/Prescript/View">
                <msh:section title="Лист назначений"
                             createRoles="/Policy/Mis/Prescription/Prescript/Create" 	>
                    <msh:sectionTitle ><a href="javascript:void(0)" onclick="javascript:showCreatePrescriptList(${param.id})">Добавить назначение</a></msh:sectionTitle>
                    <msh:sectionContent>
                        <ecom:webQuery name="presLists" nativeSql="select pl.id as ilid
	          ,vwf.name||' '||wp.lastname||' '||wp.firstname||' '||wp.middlename as doctor
	          ,pl.comments as plcomments,pl.date as dldate
	          ,(select coalesce(to_char(min(p.planStartDate),'DD.MM.YYYY'),'нет даты начала')||'-'||coalesce(to_char(max(p.planEndDate),'dd.mm.yyyy'),'нет даты окончания') as pldatend 
	          from prescription p where prescriptionList_id=pl.id) from PrescriptionList pl left join WorkFunction wf on wf.id = pl.workFunction_id left join VocWorkFunction vwf on vwf.id=wf.workFunction_id left join worker w on w.id=wf.worker_id left join Patient wp on wp.id=w.person_id where pl.medCase_id =  '${param.id}'"/>
                        <msh:table name="presLists" action="entityParentView-pres_prescriptList.do" idField="1" guid="3c4adc65-cfce-4205-a2dd-91ba8ba87543">
                            <msh:tableColumn columnName="Назначил" property="2" guid="44482100-2200-4c8b-9df5-4f5cc0e3fe68" />
                            <msh:tableColumn columnName="Комментарии" property="3" guid="5c893448-9084-4b1a-b301-d7aca8f6307c" />
                            <msh:tableColumn columnName="Дата создания" property="4" guid="dbe4fc52-03f7-42af-9555-a4bee397a800" />
                            <msh:tableColumn columnName="Период актуальности" property="5"/>
                        </msh:table>
                    </msh:sectionContent>
                </msh:section>
            </msh:ifInRole>
            <msh:ifInRole roles="/Policy/Mis/Pregnancy/ChildBirth/View">
                <ecom:webQuery name="childBirth" nativeSql="select cb.id,cb.birthFinishDate, count(nb.id), list(pat.firstname||' ' ||pat.middlename),vocr.name,
                to_char(mb.misbirthdate,'dd.mm.yyyy')||' '||vtmb.name as txt1
                ,'&chbId='||coalesce(cb.id,0)||'&rbId='||coalesce(rc.id,0)||'&mbId='||coalesce(mb.id,0)||'&mcId='||coalesce(mc.id,0) as txt2
                from medcase mc
                left join childbirth cb on cb.medcase_id=mc.id
                left join newborn nb on nb.childbirth_id=cb.id left join patient pat on pat.id=nb.patient_id
                left join robsonclass rc on rc.medcase_id=mc.id
                left join VocRobsonClass vocr on vocr.id=robsontype_id
                left join misbirth mb on mb.medcase_id=mc.id
                left join VocTypeMisbirth vtmb on vtmb.id=mb.typemisbirth_id
                where mc.id='${param.id}'  and (vocr.name is not null or cb.id is not null or mb.misbirthdate is not null) group by cb.id,cb.birthFinishDate,
                vocr.name,mb.misbirthdate,vtmb.name,rc.id,mb.id,mc.id"/>
                <msh:section>
                    <msh:sectionTitle>
                        Роды
                        <msh:ifInRole roles="/Policy/Mis/Pregnancy/ChildBirth/Create"><a href="entityParentPrepareCreate-preg_childBirth.do?id=${param.id}">Добавить роды</a>
                        </msh:ifInRole>
                        <msh:ifInRole roles="/Policy/Mis/Pregnancy/ChildBirth/Create"><a href="entityParentPrepareCreate-preg_misbirth.do?id=${param.id}">Выкидыш</a>
                        </msh:ifInRole>
                        <msh:ifInRole roles="/Policy/Mis/NewBorn/Create">
                            <a href="entityParentPrepareCreate-preg_neonatalNewBorn.do?id=${param.id}"> Добавить инф. о новорожденном</a>
                        </msh:ifInRole>
                    </msh:sectionTitle>
                    <msh:sectionContent>
                        <msh:table name="childBirth" action="js-stac_slo-gotoChildBirthOrMisbirthOrRobson.do" cellFunction="true" idField="7" openNewWindow="true">
                            <msh:tableColumn property="sn" columnName="##"/>
                            <msh:tableColumn property="2" columnName="Дата окончания родов" addParam="&type=chb"/>
                            <msh:tableColumn property="3" columnName="Кол-во плодов" addParam="&type=chb"/>
                            <msh:tableColumn property="4" columnName="ФИО ребенка (детей)" addParam="&type=chb"/>
                            <msh:tableColumn property="5" columnName="Классификация Робсона" addParam="&type=chb"/>
                            <msh:tableColumn property="6" columnName="Выкидыш" addParam="&type=mb"/>
                        </msh:table>
                    </msh:sectionContent>
                </msh:section>
            </msh:ifInRole>
            <msh:ifInRole roles="/Policy/Mis/AssessmentCard/View">
                <ecom:webQuery name="asCard" nativeSql="  select ac.id, act.name, to_char(ac.startDate,'dd.MM.yyyy') as priemDate
                  ,ac.ballsum as f4_ballsum
                  from assessmentCard ac
                  left join assessmentcardtemplate act on act.id=ac.template
                  where ac.medcase_id=${param.id}
                order by ac.startDate desc"/>
                <msh:section>
                    <msh:sectionTitle>
                        Карты оценки рисков
                        <msh:ifInRole roles="/Policy/Mis/AssessmentCard/Create"><a href="javascript:goCreateAssessmentCard()">Добавить карту оценки</a></msh:ifInRole>
                    </msh:sectionTitle>
                    <msh:sectionContent>
                        <msh:table name="asCard" action="entityParentView-mis_assessmentCard.do" idField="1">
                            <msh:tableColumn columnName="Название" property="2" guid="f34e-392-4978-b31f-5e54ff2e45bd" />
                            <msh:tableColumn columnName="Дата приема" property="3" guid="f34e-392-4978-b31f-5e54ff2e45bd" />
                            <msh:tableColumn columnName="Сумма баллов" property="4" guid="f34e-392-4978-b31f-5e54ff2e45bd" />
                        </msh:table>
                    </msh:sectionContent>
                </msh:section>
            </msh:ifInRole>
            <msh:ifInRole roles="/Policy/Mis/Calc/Calculation">
                <ecom:webQuery name="calcs" nativeSql="select cr.id,c.name, cr.result, vmu.name as vmu, cr.resdate
from calculationsresult cr 
left join calculator c on c.id=cr.calculator_id 
left join vocmeasureunit vmu on c.valueofresult_id =vmu.id
left join medcase m on cr.departmentmedcase_id = m.id
where m.id ='${param.id}'"/>
                <msh:section>
                    <msh:sectionTitle>
                        Расчеты
                        <msh:ifInRole roles="/Policy/Mis/Calc/Calculation/Create">
                            <a href="entityParentPrepareCreate-calc_calculationsResult.do?id=${param.id}&calculator=15">Добавить вычисление риска ВТЭО</a>
                        </msh:ifInRole>
                        <msh:ifInRole roles="/Policy/Mis/Calc/Calculation/Create">
                            <a href="entityParentPrepareCreate-calc_calculationsResult.do?id=${param.id}">Добавить вычисление</a>
                        </msh:ifInRole>
                        <msh:ifInRole roles="/Policy/Mis/Calc/Calculator/Create">
                            <a href="entityPrepareCreate-calc_calculator.do"> Добавить новый калькулятор</a>
                        </msh:ifInRole>

                    </msh:sectionTitle>

                    <msh:sectionContent>
                        <msh:table name="calcs" action="entityParentView-calc_calculationsResult.do" idField="1">
                            <msh:tableColumn property="sn" columnName="##"/>
                            <msh:tableColumn property="2" columnName="Название" />
                            <msh:tableColumn property="3" columnName="Результат" />
                            <msh:tableColumn property="4" columnName="Ед. измерения" />
                            <msh:tableColumn property="5" columnName="Дата" />
                        </msh:table>
                    </msh:sectionContent>
                </msh:section>
            </msh:ifInRole>

        </msh:ifFormTypeIsView>
    </tiles:put>
    <tiles:put name="title" type="string">
        <ecom:titleTrail mainMenu="Patient" beginForm="stac_sloForm" guid="fb43e71c-1ba9-4e61-8632-a6f4a72b461c" />
    </tiles:put>
    <tiles:put name="javascript" type="string">
        <script type="text/javascript">
            var slo_form_is_view = 0 ;
        </script>
        <msh:ifFormTypeIsView formName="stac_sloForm">
            <script type="text/javascript">
                slo_form_is_view = 1 ;

                function getMedcaseCost() {
                    Expert2Service.getMedcaseCost(${param.id},{
                        callback:function(aResult) {
                            aResult=JSON.parse(aResult);
                            if (aResult.status=='ok') {
                                showToastMessage("ПРИМЕРНАЯ стоимость случая по ОМС:<br>КСГ="+aResult.ksg+"<br>Цена="+aResult.price+"<br>Формула расчета: "+aResult.formulaCost);
                            } else {
                                showToastMessage("Ошибка расчета цены:"+aResult.errorName);
                            }
                        }
                    })
                }
                //проставить идентификацию
                <msh:ifInRole roles="/Policy/Mis/MedCase/Stac/PatientIdentify">
                function saveIdentityWithAsk() {
                    HospitalMedCaseService.getIsPatientIdentified(${param.id}, {
                        callback: function(aResult) {
                            if (aResult!='1' && confirm('Проведена ли идентификация личности пациента?')) {
                                HospitalMedCaseService.setIsPatientIdentified(${param.id}, {
                                    callback: function() {
                                        showToastMessage('Отметка об идентификации пациента проставлена',null,true);
                                    }
                                });
                            }
                        }
                    });
                }
                saveIdentityWithAsk();
                </msh:ifInRole>
            </script>
        </msh:ifFormTypeIsView>
        <tags:CreateDiagnoseCriteria name="CreateDiagnoseCriteria" />
        <msh:ifInRole roles="/Policy/Mis/Order203">
            <script type="text/javascript">
                function check_diags() {
                    //#185 - проверка на необходимость заполнения карты нозологий в акушерстве
                    <msh:ifFormTypeIsCreate formName="stac_sloForm">
                    var ifNotSubmit=true;
                    <msh:ifInRole roles="/Policy/Mis/Pregnancy/BirthNosologyCard/Create">
                    if ($('prevMedCase').value>0) {
                        checkNessessaryTransferNosologyCard('entityParentView-stac_slo.do?id='+$('prevMedCase').value+"&tmp="+Math.random(),document.forms[0]);
                        ifNotSubmit=false;
                    }
                    </msh:ifInRole>
                    </msh:ifFormTypeIsCreate>
                    var list_diag = ["complication","concomitant"] ;
                    var isnext=true ;
                    for (var i=0;i<list_diag.length;i++) {
                        isnext=addDiag(list_diag[i],1);
                        if (!isnext) break ;
                        createOtherDiag(list_diag[i]);
                    }
                    if (isnext) {
                        document.forms[0].action=old_action ;
                        <msh:ifFormTypeIsCreate formName="stac_sloForm">
                        <msh:ifInRole roles="/Policy/Mis/Pregnancy/BirthNosologyCard/Create">
                        showCreateDiagnoseCriteriaCloseDocument($('clinicalMkb').value,null,null, document.forms[0],${param.id},ifNotSubmit);
                        </msh:ifInRole>
                        <msh:ifNotInRole roles="/Policy/Mis/Pregnancy/BirthNosologyCard/Create">
                        showCreateDiagnoseCriteriaCloseDocument($('clinicalMkb').value,null,null, document.forms[0],${param.id},true);
                        </msh:ifNotInRole>
                        </msh:ifFormTypeIsCreate>
                        <msh:ifFormTypeAreViewOrEdit formName="stac_sloForm">
                        document.forms[0].submit() ;
                        </msh:ifFormTypeAreViewOrEdit>
                    } else {
                        $('submitButton').disabled=false ;
                    }
                }
            </script>
        </msh:ifInRole>
        <msh:ifInRole roles="/Policy/Mis/MedCase/ActRVK">
            <script type="text/javascript">
                function showOrCreateAktRvk() {
                    PatientService.getIfRVKAlreadyExists(${param.id}, {
                        callback: function (aResult) {
                            if (aResult != '{}') {
                                if (confirm('У пациента уже есть акт РВК в этом СЛС. Перейти к нему?')) {
                                    var res = JSON.parse(aResult);
                                    if (res.dtype=='Visit')
                                        window.location.href = 'entityView-rvk_aktVisit.do?id=' +res.id;
                                    else if (res.dtype=='DepartmentMedCase')
                                        window.location.href = 'entityView-rvk_aktSlo.do?id=' +res.id;
                                    else
                                        showToastMessage('Тип medcase в акте РВК некорректный! Обратитесь к администратору',null,true,true);
                                }
                            }
                            else
                                window.location.href = 'entityParentPrepareCreate-rvk_aktSlo.do?id=' + ${param.id};
                        }
                    });
                }
            </script>
        </msh:ifInRole>
        <msh:ifNotInRole roles="/Policy/Mis/Order203">
            <script type="text/javascript">
                function check_diags() {
                    //#185 - проверка на необходимость заполнения карты нозологий в акушерстве
                    <msh:ifFormTypeIsCreate formName="stac_sloForm">
                    <msh:ifInRole roles="/Policy/Mis/Pregnancy/BirthNosologyCard/Create">
                    if ($('prevMedCase').value>0) {
                        checkNessessaryTransferNosologyCard('entityParentView-stac_slo.do?id='+$('prevMedCase').value+"&tmp="+Math.random(),document.forms[0]);
                    }
                    </msh:ifInRole>
                    </msh:ifFormTypeIsCreate>
                    var list_diag = ["complication","concomitant"] ;
                    var isnext=true ;
                    for (var i=0;i<list_diag.length;i++) {
                        isnext=addDiag(list_diag[i],1);
                        if (!isnext) break ;
                        createOtherDiag(list_diag[i]);
                    }
                    if (isnext) {
                        document.forms[0].action=old_action ;
                        //showCreateDiagnoseCriteriaCloseDocument($('clinicalMkb').value,null,null, document.forms[0]);
                        <msh:ifNotInRole roles="/Policy/Mis/Pregnancy/BirthNosologyCard/Create">
                        document.forms[0].submit() ;
                        </msh:ifNotInRole>
                    } else {
                        $('submitButton').disabled=false ;
                    }
                }
            </script>
        </msh:ifNotInRole>
        <script type="text/javascript">
            function watchThisPatient() {
                HospitalMedCaseService.watchThisPatient(
                    '${param.id}', {
                        callback: function(res) {
                            alert(res);
                        }
                    }
                );
            }

            function notWatchThisPatient() {
                HospitalMedCaseService.notWatchThisPatient(
                    '${param.id}', {
                        callback: function(res) {
                            alert(res);
                        }
                    }
                );
            }
            try {
                var old_action = document.forms["mainForm"].action ;
                document.forms["mainForm"].action="javascript:check_diags('')" ;
                $('submitButton').click=function () {
                    $('submitButton').value='Сохранение данных';
                    check_diags('') ;
                }
            } catch(e) {}
            function trim(aStr) {
                return aStr.replace(/|\s+|\s+$/gm,'') ;
            }
            <msh:ifInRole roles="/Policy/Mis/Pregnancy/BirthNosologyCard/Create">
            //функция проверки необходимости наличия нозологии при переводе
            function checkNessessaryTransferNosologyCard(goIfNotSave,form) {
                HospitalMedCaseService.checkNessessaryTransferNosologyCard($('prevMedCase').value,{
                    callback: function(aResult) {
                        if (aResult=='0') {
                            showToastMessage("При переводе из патологии беременности или из родового необходимо выбрать нозологию!",null,true,false);
                            showbirthNosologyCard($('parent').value,goIfNotSave,false,form);
                        }
                        else
                            showCreateDiagnoseCriteriaCloseDocument($('clinicalMkb').value,null,null, document.forms[0],null,true);
                    }
                }) ;
            }
            </msh:ifInRole>

                onload=function() {
                var list_diag = ["complication", "concomitant"];
                for (var j = 0; j < list_diag.length; j++) {

                    if ($(list_diag[j] + 'Diags').value != '') {
                        var addRowF = "";
                        var ind_f = 0;
                        for (var i = 0; i < theFld.length; i++) {
                            addRowF += "ar[" + (ind_f++) + "],"
                            if (theFld[i][2] == 1) {
                                addRowF += "ar[" + (ind_f++) + "],"
                            }
                        }
                        addRowF = addRowF.length > 0 ? trim(addRowF).substring(0, addRowF.length - 1) : "";
                        addRowF = "addRowDiag('" + list_diag[j] + "'," + addRowF + ",1);"

                        var arr = $(list_diag[j] + 'Diags').value.split("#@#");
                        for (var i = 0; i < arr.length; i++) {
                            var ar = arr[i].split("@#@");
                            //alert(addRowF);
                            eval(addRowF);
                        }
                    }

                }
                if (isFunction(window["setDefaultWorkPlaceByDepartment"])) //если ф-я существует (т.е. если создание)
                    setDefaultWorkPlaceByDepartment($('department').value);  //если отделение уже проставлено
            }
            // isFunction
            function isFunction(functionToCheck)  {
                var getType = {};
                return functionToCheck && getType.toString.call(functionToCheck) === '[object Function]';
            }
            function addDiag(aDiagType,aCheck) {
                var addRowF="";
                var isCheckReq =true ;
                for (var i=0;i<theFld.length;i++) {
                    var fld_i = theFld[i] ;
                    eval("var "+fld_i[1]+"=$('"+aDiagType+fld_i[5]+"').value;");
                    var fld_i = theFld[i] ;addRowF+=fld_i[1]+","

                    if (fld_i[2]==1) {
                        eval("var "+fld_i[1]+"Name=$('"+aDiagType+fld_i[5]+"Name').value;");
                        eval("if ("+fld_i[1]+">0) {} else {isCheckReq=false ;}") ;
                        addRowF+=fld_i[1]+"Name," ;
                    } else {
                        eval("if ("+fld_i[1]+".length>0) {} else {isCheckReq=false ;}") ;
                    }
                }
                addRowF=addRowF.length>0?trim(addRowF).substring(0,addRowF.length-1):"";
                addRowF="addRowDiag('"+aDiagType+"',"+addRowF+");"

                if (isCheckReq) {
                    var servs = document.getElementById('other'+aDiagType+"DiagsTable").childNodes;
                    var l = servs.length;
                    for (var i=1; i<l;i++) {

                        var isCheckDouble = (+$(aDiagType+theFld[0][5]).value
                            == +servs[i].childNodes[0].childNodes[theFld[0][3]].value)?false:true ;
                        if (!isCheckDouble) {
                            if (+aCheck!=1) {
                                if (confirm("Такой диагноз уже зарегистрирован. Вы хотите его заменить?")) {
                                    var node=servs[i];node.parentNode.removeChild(node);
                                } else {
                                    return true;
                                }
                            } else {return true;}
                        }
                    }


                    eval(addRowF) ;
                    for (var i=0;i<theFld.length;i++) {
                        var fld_i = theFld[i] ;
                        if (fld_i[6]==1) {
                            eval("$('"+aDiagType+fld_i[5]+"').value='';");
                            if (fld_i[2]==1) {
                                eval("$('"+aDiagType+fld_i[5]+"Name').value='';");
                            }
                        }
                    }
                } else {
                    if (+aCheck!=1) {
                        alert("Заполнены не все поля диагноза!!");
                    } else {
                        if (+$(aDiagType+"Mkb").value>0&&$(aDiagType+"Diagnos").length>0&&!confirm('Диагнозы, где не заполнены все данные (МКБ и наименование) сохранены не будут!!! Продолжить сохранение?')) {
                            return false ;
                        }
                    }
                }
                return true ;
            }
            //alert(document.getElementById('othercomplicationDiagsTable').childNodes.childNodes[0].childNodes[4].value);
            function createOtherDiag(aDiagType) {
                var servs = document.getElementById('other'+aDiagType+"DiagsTable").childNodes;
                var str = ""; $(aDiagType+"Diags").value='';
                for (var i=0;i<servs.length;i++) {

                    for (var ii=0;ii<theFld.length;ii++) {
                        str+=servs[i].childNodes[0].childNodes[theFld[ii][3]].value+"@#@";
                        if (theFld[ii][2]==1) {
                            str+=servs[i].childNodes[0].childNodes[theFld[ii][3]+1].value+"@#@";
                        }
                    }
                    str=str.length>0?trim(str).substring(0,str.length-3):"";
                    str+="#@#" ;
                }
                str=str.length>0?trim(str).substring(0,str.length-3):"";
                $(aDiagType+"Diags").value=str;

            }
            // 0. наименование 1. Наим. поля в функции 2. autocomplete-1,textFld-2
            // 3. Номер node в добавленной услуге 4. Обяз.поля да-1 нет-2
            // 5. наим. поля в форме 6. очищать поле в форме при добавление да-1, нет-0
            var theFld = [['Код МКБ','Mkb',1,3,1,'Mkb',1],['Наименование','Diagnos',2,8,1,'Diagnos',1]] ;
            function editMkbByDiag(aDiagType,aNode) {
                if (+$(aDiagType+'Mkb').value==0 || confirm("Вы точно хотите продолжить? В этом случае Вы потеряете дааные еще недобавленного диагноза!")) {
                    for (var ii=0;ii<theFld.length;ii++) {
                        $(aDiagType+theFld[ii][5]).value=aNode.childNodes[0].childNodes[theFld[ii][3]].value;
                        if (theFld[ii][2]==1) {
                            $(aDiagType+theFld[ii][5]+'Name').value=aNode.childNodes[0].childNodes[theFld[ii][3]+1].value;
                        }

                    }
                    aNode.parentNode.removeChild(aNode) ;
                }
            }
            function addRowDiag(aDiagType,aMkb,aMkbName,aDiagnos,aIsLoad) {
                var table = document.getElementById('other' + aDiagType + "DiagsTable");
                var row = document.createElement('TR');
                var td = document.createElement('TD');
                var tdDel = document.createElement('TD');
                table.appendChild(row);
                row.appendChild(td);
                var txt = "";
                addText = "";
                if (aDiagType == "complication") {
                    addText = "ослож."
                } else if (aDiagType == "concomitant") {
                    addText = "сопут.";
                }
                for (var i = 0; i < theFld.length; i++) {
                    var fld_i = theFld[i];
                    if (fld_i[2] == 1) {
                        txt += " <label class='" + aDiagType + "Diags'>" + fld_i[0] + " " + addText + ": </label>" + eval("a" + fld_i[1] + "Name") + " <input type='hidden' value='" + eval("a" + fld_i[1]) + "'><input type='hidden' value='" + eval("a" + fld_i[1] + "Name") + "'>"
                    } else if (fld_i[2] == 2) {
                        txt += " <label class='" + aDiagType + "Diags'>" + fld_i[0] + " " + addText + ":  </label><input type='text' style='width:85%' value='" + eval("a" + fld_i[1]) + "'>"
                    }
                    if (i < theFld.length - 1) txt += "<br>";
                }
                td.innerHTML = txt;
                if (slo_form_is_view == 0) {
                    row.appendChild(tdDel);
                    tdDel.style.width = '2%';
                    tdDel.innerHTML = "<input type='button' name='subm' onclick='var node=this.parentNode.parentNode;node.parentNode.removeChild(node);createOtherDiag(\"" + aDiagType + "\")' value='- диагноз' />"
                        + "<input type='button' name='subm' onclick='var node=this.parentNode.parentNode;editMkbByDiag(\"" + aDiagType + "\",node);' value='редак.' />";
                    if (+aIsLoad > 0 && (+aMkb == 0)) {
                        //if (+$(aDiagType+"Mkb").value==0) editMkbByDiag(aDiagType,row) ;
                    }
                }
            }
//last release milamesher 06.04.2018 #97
            function viewAssessmentCardsByPatient(d) {
                getDefinition("js-mis_assessmentCard-listByPatient.do?short=Short&id="+$('patient').value, null);
            }
            function goCreateAssessmentCard(codeCard) {
                if (codeCard) {
                    HospitalMedCaseService.getDiabetCardByCode(
                        codeCard, {
                            callback: function(typeCard) {
                                var way = "entityParentPrepareCreate-mis_assessmentCard.do?id="+$('patient').value+"&slo="+${param.id};
                                window.location.href = typeCard? way+"&typeCard="+typeCard : way;
                            }}
                    );
                }
                else
                    window.location.href ="entityParentPrepareCreate-mis_assessmentCard.do?id="+$('patient').value+"&slo="+${param.id};
            }
        </script>

        <msh:ifFormTypeIsView formName="stac_sloForm">
            <tags:smo_diary_defect name="SMODef" title="Выбор причины редакции дневника" parentID="${param.id}" />
            <msh:ifInRole roles="/Policy/Mis/MedCase/Stac/Journal/Control">
                <script type="text/javascript">
                    function checkErrorProtocol(aId) {
                        showSMODefDiaryDefect(aId,'0') ;

                    }
                </script>
            </msh:ifInRole>
            <msh:ifInRole roles="/Policy/Mis/MedCase/Stac/Journal/ControlVk">
                <script type="text/javascript">
                    function checkErrorProtocolVk(aId) {
                        showSMODefDiaryDefect(aId,'1') ;

                    }
                </script>
            </msh:ifInRole>
        </msh:ifFormTypeIsView>
        <msh:ifInRole roles="/Policy/Mis/Pregnancy/BirthNosologyCard/Create">
        <tags:birthNosologyCard  name="birthNosologyCard"  />
        </msh:ifInRole>
        <script type="text/javascript">//var theBedFund = $('bedFund').value;
        function viewOtherVisitsByPatient(d) {
            //alert("js-smo_visit-infoShortByPatient.do?id="+$('patient').value) ;

            getDefinition("js-smo_visit-infoShortByPatient.do?id="+$('patient').value, null);
        }
        function viewOtherDiagnosisByPatient(d) {
            getDefinition("js-smo_diagnosis-infoShortByPatient.do?id="+$('patient').value, null);
        }
        function viewOtherHospitalMedCase(d) {
            getDefinition("js-stac_ssl-infoShortByPatient.do?id="+$('patient').value, null);
        }
        function viewOtherExtMedserviceByPatient(d) {
            getDefinition("js-doc_externalMedservice-list.do?short=Short&id=${param.id}", null);
        }

        function goDischarge() {
            window.location = 'entityParentEdit-stac_sslDischarge.do?id='+$('parent').value+"&tmp="+Math.random() ;
        }
        function goTransfer() {
            window.location = 'entityParentPrepareCreate-stac_slo.do?id='+$('parent').value+"&tmp="+Math.random();
        }
        function showSvod() {
            getDefinition('entityParentList-pres_prescriptList.do?short=Short&id='+$('parent').value);
        }
        function printPrescriptionList(id) {
            window.document.location='print-prescriptList_1.do?s=HospitalPrintService&m=printPrescriptList&id='+id;
        }
        function printPrescriptionListTotal (id) {
            window.document.location='print-prescriptListTotal.do?s=HospitalPrintService&m=printPrescriptListTotal&id='+id;
        }
        </script>
        <script type="text/javascript" src="./dwr/interface/HospitalMedCaseService.js">/**/</script>
        <script type="text/javascript" src="./dwr/interface/PrescriptionService.js"></script>
        <script type="text/javascript" src="./dwr/interface/Expert2Service.js"></script>
        <script type="text/javascript" src="./dwr/interface/PatientService.js"></script>
        <msh:ifFormTypeIsView formName="stac_sloForm">
            <script type="text/javascript">
                function unionSloWithNextSlo() {
                    HospitalMedCaseService.unionSloWithNextSlo('${param.id}',{

                        callback: function(aResult) {
                            alert(aResult) ;
                            window.location.reload() ;
                        }
                    }) ;
                }
                var info = document.getElementById('mainFormLegend').parentNode.innerHTML;
                function loadBracelets() {
                    //вывод браслетов #151
                    HospitalMedCaseService.selectIdentityPatient(
                        $('parent').value,true, {
                            callback: function(res) {
                                document.getElementById('mainFormLegend').parentNode.innerHTML=info;
                                if (res!=null && res!='[]') {
                                    var aResult = JSON.parse(res);
                                    var str='<table style="margin-left:45%"><tr>';
                                    for (var i=0; i<aResult.length; i++) {
                                        var style = 'width: 30px;height: 30px;outline: 1px solid gray; border:2px; margin-right: 2px; margin-left: 2px;';
                                        style+=aResult[i].picture!=''? '">':' background: '+aResult[i].colorCode +';">';
                                        if (aResult[i].picture!='') style+='<img src="/skin/images/patology.png" title="Патология" height="30px" width="30px">';
                                        var msg = aResult[i].info!=''? aResult[i].info : aResult[i].vsipnameJust;
                                        str+='<td><div onclick="showToastMessage(\''+msg+'\',null,true,false);" title="'+aResult[i].vsipnameJust+'" style="'+style+'</div></td>';
                                    }
                                    str+="</tr></table>";
                                    document.getElementById('mainFormLegend').parentNode.innerHTML=document.getElementById('mainFormLegend').parentNode.innerHTML.replace('<h2 id="mainFormLegend">Лечение в отделении</h2>',"<h2 id=\"mainFormLegend\">Лечение в отделении</h2>"+str);
                                }
                            }
                        }
                    );
                }
                loadBracelets();
            </script>
        </msh:ifFormTypeIsView>
        <msh:ifFormTypeIsNotView formName="stac_sloForm" guid="518fe547-aed9-be2229f04ba3">
            <script type="text/javascript">//var theBedFund = $('bedFund').value;
            if ($('dietName')) {
                $('dietName').style="color: blue ; background-color:#FFFFA0;";
            }
            if ($('modeName')) {
                $('modeName').style="color: blue ; background-color:#FFFFA0;";
            }
            if (+$('prevMedCase').value==0) {
                $('serviceStreamName').select() ;
                $('serviceStreamName').focus() ;
            }
            var lpuDate ;

            try {
                if (clinicalMkbAutocomplete) clinicalMkbAutocomplete.addOnChangeCallback(function() {
                    setDiagnosisText('clinicalMkb','clinicalDiagnos');
                });} catch(e) {}
            try {
                if (concomitantMkbAutocomplete) concomitantMkbAutocomplete.addOnChangeCallback(function() {
                    setDiagnosisText('concomitantMkb','concomitantDiagnos');
                });} catch(e) {}
            try{
                if (complicationMkbAutocomplete) complicationMkbAutocomplete.addOnChangeCallback(function() {
                    setDiagnosisText('complicationMkb','complicationDiagnos');

                });} catch(e) {}
            function setDiagnosisText(aFieldMkb,aFieldText) {
                var val = $(aFieldMkb+'Name').value ;
                var ind = val.indexOf(' ') ;
                //alert(ind+' '+val)
                if (ind!=-1) {
                    if ($(aFieldText).value=="") $(aFieldText).value=val.substring(ind+1) ;
                }
            }

            function goDischarge() {
                window.location = 'entityParentEdit-stac_sslDischarge.do?id='+$('parent').value+"&tmp="+Math.random() ;
            }
            eventutil.addEventListener($('dateStart'),'blur',function(){updateLpuAndDate() ;}) ;
            $('lpuAndDate').value = (+$('department').value) +"#"+(+$('serviceStream').value)+"#" +$('dateStart').value;
            lpuDate = (+$('department').value) +"#"+$('dateStart').value  ;
            bedFundAutocomplete.setParentId($('lpuAndDate').value) ;
            serviceStreamAutocomplete.setParentId(lpuDate) ;

            if (bedFundAutocomplete) bedFundAutocomplete.setParentId($('lpuAndDate').value) ;
            serviceStreamAutocomplete.addOnChangeCallback(function() {
                $('bedFund').value="";
                $('bedFundName').value="";
                updateLpuAndDate() ;
                updateBedFund() ;
            });
            updateLpuAndDate() ;
            function updateLpuAndDate() {
                $('lpuAndDate').value = (+$('department').value) +"#"+(+$('serviceStream').value)+"#" +$('dateStart').value;
                lpuDate = (+$('department').value) +"#"+$('dateStart').value ;
                bedFundAutocomplete.setParentId($('lpuAndDate').value) ;
                serviceStreamAutocomplete.setParentId(lpuDate) ;
            }
            function updateBedFund() {
                HospitalMedCaseService.getDefaultBedFundBySlo($('parent').value
                    , $('department').value, $('serviceStream').value
                    , $('dateStart').value,{
                        callback: function(aResult) {
                            var res = aResult.split('#') ;

                            if (+res[0]!=0) {
                                $('bedFund').value = res[0] ;
                                $('bedFundName').value = res[1] ;
                            } else {
                                $('bedFund').value='0';
                                $('bedFundName').value='';
                            }
                        }
                    }) ;

            }

            function setDefaultWorkPlaceByDepartment(dep) {
                if (+dep != 0 && window.location.href.indexOf("Create")!=-1) {
                    HospitalMedCaseService.getDefaultWorkPlaceByDepartment(dep, {
                        callback: function (aResult) {
                            if (aResult != null) {
                                var roomArr = JSON.parse(aResult);
                                if (typeof(roomArr.wpid)!=='undefined' && +roomArr.wpid != 0 && +roomArr.wp2id != 0) {
                                    $('roomNumber').value = roomArr.wpid;
                                    if ($('roomNumberName')!=null) $('roomNumberName').value = roomArr.wpname;
                                    $('bedNumber').value = roomArr.wp2id;
                                    if ($('bedNumberName')!=null) $('bedNumberName').value = roomArr.wp2name;
                                }
                            }
                        }
                    });
                }
            }
            </script>

            <msh:ifNotInRole roles="/Policy/Mis/MedCase/Stac/Ssl/OwnerFunction">
                <script type="text/javascript">
                    try {
                        //departmentAutocomplete.setParentId($('lpu').value) ;
                        departmentAutocomplete.addOnChangeCallback(function() {
                            try {//Milamesher 17072018 ошибка на roomNumberAutocomplete и roomNumberName при ShortEnter
                                if (typeof(roomNumberAutocomplete)!== 'undefined') roomNumberAutocomplete.setParentId($('department').value) ;
                                $('roomNumber').value='0' ;
                                if ($('roomNumberName')!=null) $('roomNumberName').value='' ;
                                $('ownerFunction').value="0";
                                $('ownerFunctionName').value="";

                            } catch(e) {}
                            var oldid = $('serviceStream').value ;
                            updateLpuAndDate() ;
                            ownerFunctionAutocomplete.setParentId($('department').value) ;
                            updateBedFund() ;

                            var newid = $('serviceStream').value ;
                            //	if (oldid!=newid) {
                            //		$('kindHighCare').value = "" ;
                            //		$('kindHighCareName').value = "" ;
                            //		kindHighCareAutocomplete.setParentId(+newid) ;
                            //	}
                            setDefaultWorkPlaceByDepartment($('department').value);
                        });
                    } catch (e) {
                    }
                </script>
            </msh:ifNotInRole>
            <msh:ifInRole roles="/Policy/Mis/MedCase/Stac/Ssl/OwnerFunction">
                <script type="text/javascript">
                    try {
                        //departmentAutocomplete.setParentId($('lpu').value) ;
                        departmentAutocomplete.addOnChangeCallback(function() {
                            try { //Milamesher 17072018 ошибка на roomNumberAutocomplete и roomNumberName при ShortEnter
                            updateLpuAndDate() ;
                            if (typeof(roomNumberAutocomplete)!== 'undefined') roomNumberAutocomplete.setParentId($('department').value) ;
                            $('roomNumber').value='0' ;
                            if ($('roomNumberName')!=null) $('roomNumberName').value='' ;
                            ownerFunctionAutocomplete.setParentId($('department').value) ;
                            HospitalMedCaseService.getDefaultInfoBySlo($('parent').value
                                , $('department').value, $('serviceStream').value
                                , $('dateStart').value,{
                                    callback: function(aResult) {
                                        var res = aResult.split('#') ;
                                        if (+res[0]!=0) {
                                            $('ownerFunction').value = res[0] ;
                                            $('ownerFunctionName').value = res[1] ;
                                        } else {
                                            $('ownerFunction').value='0';
                                            $('ownerFunctionName').value='';
                                        }
                                        if (+res[2]!=0) {
                                            $('bedFund').value = res[2] ;
                                            $('bedFundName').value = res[3] ;
                                        } else {
                                            $('bedFund').value='0';
                                            $('bedFundName').value='';
                                        }
                                    }
                                })
                            setDefaultWorkPlaceByDepartment($('department').value);
                        }catch (e) {
                            }}
                        );
                    } catch (e) {
                    }
                </script>
            </msh:ifInRole>
        </msh:ifFormTypeIsNotView>
    </tiles:put>
</tiles:insert>

