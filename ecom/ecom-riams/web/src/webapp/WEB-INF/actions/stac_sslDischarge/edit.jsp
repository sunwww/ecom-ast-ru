<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://struts.apache.org/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://www.nuzmsh.ru/tags/msh" prefix="msh" %>
<%@ taglib uri="http://www.ecom-ast.ru/tags/ecom" prefix="ecom" %>
<%@ taglib tagdir="/WEB-INF/tags" prefix="tags" %>
<%@ taglib uri="/WEB-INF/mis.tld" prefix="mis" %>

<tiles:insert page="/WEB-INF/tiles/main${param.short}Layout.jsp" flush="true">
    <tiles:put name="style" type="string">
        <msh:ifInRole roles="/Policy/Mis/MedCase/IsPsychiatry">
            <style type="text/css">
                #sourceHospTypeLabel,#admissionOrderLabel, #whereAdmissionLabel,
                #orderMkbLabel,#orderDiagnosLabel,
                #admissionInHospitalLabel, #psychReasonLabel, #clinicalActuityLabel
                ,#orderLpuLabel
                {
                    color: blue ;
                }
                #sourceHospTypeName,#admissionOrderName, #whereAdmissionName,
                #orderMkbName,#orderDiagnos,
                #admissionInHospitalName, #psychReasonName, #clinicalActuityName {
                    background-color:#FFFFA0;
                }
            </style>
        </msh:ifInRole>
        <msh:ifInRole roles="/Policy/Mis/MedCase/IsPsychiatry">
            <style type="text/css">
                #sourceHospTypeLabel,#admissionOrderLabel, #whereAdmissionLabel,
                #admissionInHospitalLabel, #psychReasonLabel, #clinicalActuityLabel
                ,#judgment35Label,#lawCourtDesicionDate
                {
                    color: blue ;
                }
                #sourceHospTypeName,#admissionOrderName, #whereAdmissionName,
                #admissionInHospitalName, #psychReasonName, #clinicalActuityName {
                    background-color:#FFFFA0;
                }
            </style>
        </msh:ifInRole>
        <style type="text/css">


            #clinicalDiagnosLabel, #clinicalMkbLabel, #clinicalActuityLabel,#mkbAdcLabel {
                color: blue ;
            }
            #concomitantDiagnosLabel, #concomitantMkbLabel, .concomitantDiags {
                color: green ;
            }

            #concludingDiagnosLabel, #concludingMkbLabel, .concludingDiags {
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

            .epicrisis {
                left:0px;  width:99%;
                top:0px;  height:45em;
            }
            #epicriPanel {
                width:100%;
            }
            .dischargeEpicrisis {
                width:100%;
            }
        </style>

    </tiles:put>
    <tiles:put name="side" type="string">
        <msh:ifNotInRole roles="/Policy/Mis/MedCase/Stac/Ssl/Discharge/NotViewDischargeEpicrisis">
            <tags:template_new_diary name="newTemp" roles="/Policy/Diary/Template/Create" field="dischargeEpicrisis" title="Создание шаблона на основе выписки"/>
        </msh:ifNotInRole>
        <tags:stac_hospitalMenu currentAction="stac_sslDischarge"/>
    </tiles:put>
    <tiles:put name="body" type="string">
        <!--
        - Случай стационарного лечения (выписка)
        -->

        <msh:ifFormTypeIsView formName="stac_sslDischargeForm">
            <ecom:webQuery name="isTransferLpu" nativeSql="select id,lpu_id from medcase where id=${param.id} and moveToAnotherLpu_id is not null"/>
            <msh:tableNotEmpty name="isTransferLpu">
                <ecom:webQuery name="directOtherLpuQ" nativeSql="select wchb.id as id, to_char(wchb.createDate,'yyyy-MM-dd') as w0chbcreatedate
 ,cast('1' as varchar(1)) as f1orPom
 ,case when lpu.codef is null or lpu.codef='' then plpu.codef else lpu.codef end as l2puSent
 ,case when olpu.codef is null or olpu.codef='' then oplpu.codef else olpu.codef end as l3puDirect
 ,vmc.code as m4edpolicytype
 ,mp.series as m5pseries
 , mp.polnumber as p6olnumber
 , case when oss.smocode is null or oss.smocode='' then ri.smocode else oss.smoCode end as o7ossmocode
 , ri.ogrn as o8grnSmo
 ,case when mp.dtype='MedPolicyOmc' then '12000' else okt.okato end as o9katoSmo
 ,p.lastname as l10astname
 ,p.firstname as f11irstname
 ,p.middlename as m12iddlename
 ,vs.omcCode as v13somccode
 ,to_char(p.birthday,'yyyy-mm-dd') as b14irthday
 ,wchb.phone as p15honepatient
 ,mkb.code as m16kbcode
 ,vbt.codeF as v17btcodef
 ,wp.snils as w18psnils
 ,wchb.dateFrom as w19chbdatefrom
, wchb.visit_id as v20isit
, case when vbst.code='3' then '2' else vbst.code end as v21bstcode
, cast('0' as varchar(1)) as f22det 
 from WorkCalendarHospitalBed wchb
 left join VocBedType vbt on vbt.id=wchb.bedType_id
 left join VocBedSubType vbst on vbst.id=wchb.bedSubType_id
 left join Patient p on p.id=wchb.patient_id
 left join VocSex vs on vs.id=p.sex_id
 left join VocServiceStream vss on vss.id=wchb.serviceStream_id
 left join MedCase mc on mc.id=wchb.visit_id
 left join medpolicy mp on mp.patient_id=wchb.patient_id and mp.actualdatefrom<=wchb.createDate and coalesce(mp.actualdateto,current_date)>=wchb.createDate
 left join VocIdc10 mkb on mkb.id=wchb.idc10_id
 left join MisLpu ml on ml.id=wchb.department_id
 left join Vocmedpolicyomc vmc on mp.type_id=vmc.id
 left join Omc_kodter okt on okt.id=mp.insuranceCompanyArea_id
 left join Omc_SprSmo oss on oss.id=mp.insuranceCompanyCode_id
 left join reg_ic ri on ri.id=mp.company_id
 left join WorkFunction wf on wf.id=mc.workFunctionExecute_id
 left join Worker w on w.id=wf.worker_id
 left join Patient wp on wp.id=w.person_id
 left join mislpu lpu on lpu.id=w.lpu_id
 left join mislpu plpu on plpu.id=lpu.parent_id
 left join mislpu olpu on olpu.id=wchb.orderLpu_id
 left join mislpu oplpu on oplpu.id=olpu.parent_id
 where wchb.visit_id =${param.id}
    	"/>
                <msh:tableEmpty name="directOtherLpuQ">
    	<span style="background-color: red; font-size: 200%">НЕОБХОДИМО ЗАПОЛНИТЬ ФОРМУ НАПРАВЛЕНИЯ В ДРУГОЕ ЛПУ !!!
    	<msh:link action="entityParentPrepareCreate-smo_planHospitalByHosp.do?id=${param.id}">Создать</msh:link>
    	</span>
                </msh:tableEmpty>
                <msh:tableNotEmpty name="directOtherLpuQ">
                    <msh:table  action="entityView-smo_planHospitalByHosp.do" name="directOtherLpuQ" idField="1">
                        <msh:tableColumn property="1" columnName="?"/>
                    </msh:table>
                </msh:tableNotEmpty>
            </msh:tableNotEmpty>


        </msh:ifFormTypeIsView>
        <msh:form action="/entityParentSaveGoView-stac_sslDischarge.do" defaultField="" title="Случай стационарного лечения. ВЫПИСКА">
            <msh:hidden property="id" />
            <msh:hidden property="patient" />
            <msh:hidden property="saveType" />
            <msh:hidden property="lpu" />
            <msh:hidden property="emergency"/>
            <msh:hidden property="ambulanceTreatment"/>
            <msh:hidden property="ownerFunction"/>
            <msh:hidden property="bedType"/>
            <msh:hidden property="department"/>
            <msh:hidden property="hospType"/>
            <msh:hidden property="serviceStream"/>
            <msh:hidden property="intoxication"/>
            <msh:hidden property="medicalAid"/>
            <msh:hidden property="relativeMessage"/>
            <msh:hidden property="orderLpu"/>
            <msh:hidden property="sourceHospType"/>
            <msh:hidden property="orderNumber"/>
            <msh:hidden property="orderDate"/>
            <msh:hidden property="orderType"/>
            <msh:hidden property="intoxication"/>
            <msh:hidden property="preAdmissionTime"/>
            <msh:hidden property="pediculosis"/>
            <msh:hidden property="attendant"/>
            <msh:hidden property="supplyOrderNumber"/>
            <msh:hidden property="deniedHospitalizating"/>
            <msh:hidden property="ambulanceTreatment"/>
            <msh:hidden property="username"/>
            <msh:hidden property="judgment35"/>
            <msh:hidden property="admissionOrder"/>
            <msh:hidden property="lawCourtDesicionDate"/>
            <msh:hidden property="psychReason"/>
            <msh:hidden property="guarantee"/>
            <msh:hidden property="identDate"/>
            <msh:hidden property="identTime"/>
            <msh:hidden property="identUsername"/>
            <msh:hidden property="isIdentified"/>
            <msh:ifNotInRole roles="/Policy/Mis/Patient/Newborn">
                <msh:hidden property="hotelServices"/>
            </msh:ifNotInRole>
            <msh:ifFormTypeIsView formName="stac_sslDischargeForm">
                <msh:ifNotInRole roles="/Policy/Mis/MedCase/Stac/Ssl/Discharge/NotViewDischargeEpicrisis">
                    <msh:hidden property="dischargeEpicrisis"/>
                </msh:ifNotInRole>
            </msh:ifFormTypeIsView>
            <msh:panel colsWidth="5%,10%,5%,80%">
                <msh:row>
                    <td colspan="4"><div id='errorInformation' style="display: none;" class="errorMessage"/></td>
                </msh:row>
                <msh:separator label="Приемное отделение" colSpan="8" />
                <msh:row>
                    <msh:textField property="statCardNumber" label="Номер стат.карты" horizontalFill="true" viewOnlyField="true" />
                </msh:row>
                <msh:row>
                    <msh:textField property="dateStart" label="Дата поступления" viewOnlyField="true" />
                    <msh:textField property="entranceTime" label="время" viewOnlyField="true" />
                </msh:row>
                <msh:row>
                    <msh:textField property="transferDate" label="Выбыт. из приемника"  viewOnlyField="true"/>
                    <msh:textField property="transferTime" label="время" fieldColSpan="3"  viewOnlyField="true"/>
                </msh:row>
                <msh:row>
                    <msh:checkBox property="relativeMessage" label="Сообщение родственникам" viewOnlyField="true" />
                    <msh:autoComplete property="department" label="Отделение" vocName="vocLpuOtd" horizontalFill="true" viewOnlyField="true" />
                </msh:row>
                <mis:ifPatientIsWoman classByObject="Patient" idObject="stac_sslDischargeForm.patient" roles="/Policy/Mis/Pregnancy/History/View">
                    <msh:separator label="Беременность" colSpan="9"/>
                    <msh:row>
                        <msh:autoComplete viewOnlyField="true" viewAction="entityParentView-preg_pregnancy.do" property="pregnancy" label="Беременность" fieldColSpan="3" parentId="patient" vocName="pregnancyByPatient" horizontalFill="true"/>
                    </msh:row>
                </mis:ifPatientIsWoman>
            </msh:panel>
            <msh:panel colsWidth="5%,10%,5%,80%">
                <msh:row>
                    <msh:autoComplete vocName="vocIllnesPrimary" property="clinicalActuity" horizontalFill="true" label="Характер заболевания"
                                      fieldColSpan="3"
                    />
                </msh:row>
                <msh:row>
                    <msh:autoComplete vocName="vocIdc10" label="МКБ клин.диаг." property="clinicalMkb" fieldColSpan="3" horizontalFill="true"/>
                </msh:row>
                <msh:row>
                    <msh:textField label="Клинический диагноз" property="clinicalDiagnos" fieldColSpan="3" horizontalFill="true"/>
                </msh:row>
            </msh:panel>
            <msh:panel styleId="epicriPanel" colsWidth="1%,1%,1%,1%">
                <msh:ifInRole roles="/Policy/Mis/MedCase/Protocol/View">
                    <msh:ifNotInRole roles="/Policy/Mis/MedCase/Stac/Ssl/Discharge/NotViewDischargeEpicrisis">
                        <msh:row>
                            <msh:separator colSpan="8" label="Выписной эпикриз" />
                        </msh:row>
                        <msh:ifFormTypeIsNotView formName="stac_sslDischargeForm">
                            <msh:row>
                                <td colspan="4" align="center">
                                    <tags:oncologyTag parentId="${param.id}" name="Onk"/>
                                    <input type="button" value="Шаблон" onclick="showTextTemplateProtocol()"/>
                                    <input type="button" value="Доп. сведения" onclick="showTextEpicrisis()"/>
                                    <input type="button" value="Сохранить пред. выписку" onclick="savePreRecord()"/>
                                    <input type="button" id="submitPreDischrge1" name="submitPreDischrge1" value="Сохранить пред. выписку+диагноз" onclick="check_diags('1');"/>
                                    <input type="button" id="changeSizeEpicrisisButton1" value="Увеличить" onclick="changeSizeEpicrisis()">
                                    <input type="button" onclick="checkStorage();" value="Восстановить потерянные данные" />

                                </td>
                            </msh:row>
                        </msh:ifFormTypeIsNotView>
                        <msh:row>
                            <msh:textArea property="dischargeEpicrisis" fieldColSpan="3" label="Текст" />
                        </msh:row>
                        <msh:ifFormTypeIsNotView formName="stac_sslDischargeForm">
                            <msh:row>
                                <td colspan="4" align="center">
                                    <input type="button" value="Шаблон" onclick="showTextTemplateProtocol()"/>
                                    <input type="button" value="Доп. сведения" onclick="showTextEpicrisis()"/>
                                    <input type="button" value="Сохранить пред. выписку" onclick="savePreRecord()"/>
                                    <input type="button" id="submitPreDischrge2" name="submitPreDischrge2" value="Сохранить пред. выписку+диагноз" onclick="check_diags('1');"/>
                                    <input type="button" id="changeSizeEpicrisisButton" value="Увеличить" onclick="changeSizeEpicrisis()">
                                    <input type="button" onclick="checkStorage();" value="Восстановить потерянные данные" />
                                </td>
                            </msh:row>
                        </msh:ifFormTypeIsNotView>
                    </msh:ifNotInRole>
                    <msh:ifInRole roles="/Policy/Mis/MedCase/Stac/Ssl/Discharge/NotViewDischargeEpicrisis">
                        <msh:hidden property="dischargeEpicrisis"/>
                    </msh:ifInRole>
                </msh:ifInRole>
                <msh:ifNotInRole roles="/Policy/Mis/MedCase/Protocol/View">
                    <msh:hidden property="dischargeEpicrisis"/>
                </msh:ifNotInRole>
            </msh:panel>
            <msh:panel colsWidth="5%,10%,5%,80%">
                <msh:separator colSpan="8" label="Выписка" />
                <msh:row>
                    <msh:autoComplete vocName="vocIllnesPrimary" property="concludingActuity" horizontalFill="true" label="Характер заболевания"
                                      fieldColSpan="3"
                    />
                </msh:row>
                <msh:row>
                    <msh:autoComplete vocName="vocIdc10" label="МКБ-10 закл.диаг." property="concludingMkb" fieldColSpan="3" horizontalFill="true"/>
                </msh:row>
                <msh:row>
                    <msh:textField label="Заключительный диагноз" property="concludingDiagnos" fieldColSpan="3" horizontalFill="true"/>
                </msh:row>
                <msh:hidden property="complicationDiags"/>
                <msh:hidden property="concomitantDiags"/>
                <msh:ifFormTypeIsNotView formName="stac_sslDischargeForm">
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
                <msh:ifFormTypeIsNotView formName="stac_sslDischargeForm">
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


                <msh:ifFormTypeIsView formName="stac_sslDischargeForm">
                    <msh:row>
                        <msh:autoComplete vocName="vocIdc10" label="МКБ-10 патанат.диаг." property="pathanatomicalMkb" fieldColSpan="3" horizontalFill="true"/>
                    </msh:row>
                    <msh:row>
                        <msh:textField label="Патанатомический диагноз" property="pathanatomicalDiagnos" fieldColSpan="3" horizontalFill="true"/>
                    </msh:row>
                </msh:ifFormTypeIsView>
                <msh:row>
                    <msh:autoComplete property="kinsman" label="Представитель (иног.)" viewAction="entityParentView-mis_kinsman.do"
                                      parentId="stac_sslDischargeForm.patient" vocName="kinsmanBySMO" horizontalFill="true" fieldColSpan="3"/>
                </msh:row>
                <msh:ifInRole roles="/Policy/Mis/Patient/Newborn">
                    <msh:row>
                        <msh:checkBox property="hotelServices" label="Находится в больнице по уходу за пациентом" fieldColSpan="3"/>
                    </msh:row>
                </msh:ifInRole>
                <msh:row>
                    <msh:autoComplete label="Исход" property="outcome" fieldColSpan="1" horizontalFill="true" vocName="vocHospitalizationOutcome" />
                    <msh:autoComplete label="Результат госп." property="result" fieldColSpan="1" horizontalFill="true" vocName="vocHospitalizationResult" />
                </msh:row>
                <msh:row>
                    <msh:autoComplete label="Причина выписки" property="reasonDischarge"  horizontalFill="true" vocName="vocReasonDischarge"/>
                    <msh:autoComplete label="Дефекты догоспитального этапа" property="preAdmissionDefect"  horizontalFill="true" vocName="vocPreAdmissionDefect"/>
                </msh:row>

                <msh:row>
                    <msh:textField label="Дата выписки" property="dateFinish" />
                    <msh:textField label="Время выписки" property="dischargeTime" />
                </msh:row>
                <msh:row>
                    <msh:autoComplete label="Перевод в др ЛПУ" property="moveToAnotherLPU" horizontalFill="true" vocName="mainLpu" fieldColSpan="3" />
                </msh:row>
                <msh:row>
                    <msh:autoComplete vocName="vocHospType" property="targetHospType" label="Куда выписан" horizontalFill="true" />
                    <msh:autoComplete label="Итог выписки" property="resultDischarge"  horizontalFill="true" vocName="vocResultDischarge" />
                </msh:row>

                <msh:row>
                    <msh:checkBox property="rareCase" label="Редкий случай" />
                </msh:row>
                <msh:row>
                    <msh:autoComplete labelColSpan="3" property="hospitalization" label="Госпитализация в данном году по данному заболевания" vocName="vocHospitalization" horizontalFill="true" fieldColSpan="1" />
                </msh:row>
                <msh:ifInRole roles="/Policy/Mis/MedCase/IsPsychiatry">
                    <msh:row>
                        <msh:autoComplete vocName="vocHospitalization" property="admissionInHospital" label="Поступление в стационар" horizontalFill="true" labelColSpan="1"/>
                        <msh:autoComplete label="Причина госпитализации" vocName="vocPsychHospitalReason" property="psychReason" labelColSpan="1" horizontalFill="true"/>
                    </msh:row>
                    <msh:row>
                        <msh:autoComplete property="admissionOrder" label="Порядок поступления" fieldColSpan="1" vocName="vocAdmissionOrder" horizontalFill="true"/>
                        <msh:autoComplete label="Откуда поступил" vocName="vocHospitalizationWhereAdmission" property="whereAdmission" labelColSpan="1" horizontalFill="true"/>
                    </msh:row>
                    <msh:row>
                        <msh:textField property="lawCourtDesicionDate" label="Дата решения суда"/>
                        <msh:autoComplete property="judgment35" label="Решение судьи по ст. 35" horizontalFill="true" vocName="vocJudgment"/>
                    </msh:row>
                </msh:ifInRole>

                <msh:row>
                    <msh:separator label="Дополнительно" colSpan="4"/>
                </msh:row>
            </msh:panel>
            <msh:panel>
                <msh:row>
                    <msh:label property="createDate" label="Дата создания"/>
                    <msh:label property="createTime" label="время"/>
                    <msh:label property="username" label="пользователь" />
                </msh:row>
                <msh:row>
                    <msh:label property="editDate" label="Дата редак."/>
                    <msh:label property="editTime" label="время"/>
                    <msh:label property="editUsername" label="пользователь" />
                </msh:row>

                <msh:submitCancelButtonsRow functionSubmit="check_diags('') ;" colSpan="4" labelSave="Сохранить изменения" labelCreating="Создание" labelCreate="Создать новый случай" labelSaving="Сохранение данных" />
            </msh:panel>
        </msh:form>
        <msh:ifNotInRole roles="/Policy/Mis/MedCase/Stac/Ssl/Discharge/NotViewDischargeEpicrisis">
            <msh:ifFormTypeIsNotView formName="stac_sslDischargeForm">
                <tags:templateProtocol property="dischargeEpicrisis" name="Text" version="Visit" idSmo="stac_sslDischargeForm.id" voc="protocolVisitByPatient" />
                <tags:dischargeEpicrisis property="dischargeEpicrisis" name="Text" patient="patient" dateStart="dateStart" dateFinish="dateFinish"/>
            </msh:ifFormTypeIsNotView>
        </msh:ifNotInRole>
        <tags:stac_infoBySls form="stac_sslDischargeForm"/>
    </tiles:put>
    <tiles:put name="title" type="string">
        <ecom:titleTrail mainMenu="Patient" beginForm="stac_sslDischargeForm" />
    </tiles:put>
    <tiles:put name="javascript" type="string">
        <script type="text/javascript">
            var slo_form_is_view = 0 ;
            var medCaseId = $('id');
            function saveToStorage() {
                try {
                    localStorage.setItem("stac_sslDischargeForm" + ";" + medCaseId.value + ";" + document.getElementById('current_username_li').innerHTML, $('dischargeEpicrisis').value);
                }
                catch (e) {}
            }
            function removeFromStorage() {
                try {
                    localStorage.removeItem("stac_sslDischargeForm"+";"+medCaseId.value+";"+document.getElementById('current_username_li').innerHTML);
                }
                catch (e) {}
            }
            eventutil.addEventListener($('dischargeEpicrisis'), "input", function(){saveToStorage();}) ;
            eventutil.addEventListener($('dischargeEpicrisis'), "keyup", function(){saveToStorage();}) ;
            //eventutil.addEventListener($('dischargeEpicrisis'), "blur", function(){saveToStorage();}) ;
            eventutil.addEventListener($('dischargeEpicrisis'), "paste", function(){saveToStorage();}) ;
        </script>
        <msh:ifFormTypeIsView formName="stac_sslDischargeForm">
            <script type="text/javascript">
                slo_form_is_view = 1 ;
            </script>
        </msh:ifFormTypeIsView>

        <script type="text/javascript" src="./dwr/interface/OncologyService.js"></script>
        <script type="text/javascript">
            function trim(aStr) {
                return aStr.replace(/|\s+|\s+$/gm,'') ;
            }

            try {
                var old_action = document.forms["mainForm"].action ;
                document.forms["mainForm"].action="javascript:check_diags('')" ;

            } catch(e) {}

            function checkCountSLO() {
                var count = 1;
                OncologyService.checkSLO(${param.id},{
                    callback : function(res) {
                        return res;
                    }});

            }

            function check_diags(aPrefix) {
                //checkNessessaryDischargeNosologyCard(aPrefix);
                //update 1501 - пока запускать при открытии страницы, не при сохранении
                saveNext(aPrefix);
            }

            function saveNext(aPrefix) {
                var a= $('concludingMkbName').value;

                var concludingMkb=$('concludingMkbName').value;
                var index=concludingMkb.indexOf(' ');
                if (index!=-1) concludingMkb=concludingMkb.substring(0,index);
                //if (a.match(/C\d\d/ )==null) concludingMkb='';
                OncologyService.checkSLO(${param.id},{
                    callback : function(res) {
                        if(res=="0" && a.match(/C\d\d/ )!=null) {
                            alert('Внимание! Для выбранного диагноза нужно заполнить случай ЗНО');
                            savePreRecord();
                            //window.open
                            showOnkOncology("entityParentPrepareCreate-oncology_case_reestr.do?id="+'${param.id}'+"&mkb="+concludingMkb+"&short=ShortCreate");
                            try{$('submitPreDischrge2').disabled=false;
                                $('submitPreDischrge1').disabled=false ;}catch(e){}
                            $('submitButton').disabled=false ;

                        }else {
                            OncologyService.checkDiagnosisOnkoForm(${param.id},concludingMkb, {
                                callback: function (res) {
                                    if (res!='' && res!='0' && a.match(/C\d\d/ )!=null) {
                                        var mas = res.split('#');
                                        alert(mas[0]);
                                        try{$('submitPreDischrge2').disabled=false;
                                            $('submitPreDischrge1').disabled=false ;}catch(e){}
                                        $('submitButton').disabled=false ;
                                        showOnkOncology(/*'.do'*/"entityEdit-oncology_case_reestr.do?id="+mas[1]+"&mkb="+concludingMkb+"&short=ShortCreate");
                                        //window.open();
                                    }
                                    //смена С на не С
                                    /*else if (res!='' && res!='0' && !a.match(/C\d\d/ )!=null) {
                                        var mas = res.split('#');
                                        alert(mas[0]);
                                        try{$('submitPreDischrge2').disabled=false;
                                            $('submitPreDischrge1').disabled=false ;}catch(e){}
                                        $('submitButton').disabled=false ;
                                        window.open("entityEdit-oncology_case_reestr.do?id="+mas[1]+"&mkb="+concludingMkb);// + "&actualMsg=Созданная ранее онкологическая форма была удалена. Если необходимо, создайте подозрение на ЗНО.");
                                    }*/
                                    else {
                                        if ((res != '' && res != '0' && !a.match(/C\d\d/) != null && confirm(res.split('#')[0])) || (res=='' || res=='0')) {
                                            if (res != '' && res != '0' && !a.match(/C\d\d/) != null) { //смена С на не С
                                                var mas = res.split('#');
                                                //удаление формы
                                                if (mas[1]!='') {
                                                    OncologyService.deleteAllByCase(mas[1], {
                                                        callback: function () {
                                                            showToastMessage('Неактуальная онкологическая форма была удалена',null,true);
                                                        }
                                                    });
                                                }
                                            }
                                            //if (res=='0' && a.match(/C\d\d/ )!=null) alert('Есть несколько ЗНО, у одного из них совпадает диагноз с основным выписным.');
                                            var list_diag = ["complication", "concomitant"];
                                            var isnext = true;
                                            try {
                                                $('submitPreDischrge2').disabled = true;
                                                $('submitPreDischrge1').disabled = true;
                                            } catch (e) {
                                            }
                                            $('submitButton').disabled = true;
                                            for (var i = 0; i < list_diag.length; i++) {
                                                isnext = addDiag(list_diag[i], 1);
                                                if (!isnext) break;
                                                createOtherDiag(list_diag[i]);
                                            }
                                            if (isnext) {
                                                if (+aPrefix > 0) {
                                                    document.forms["mainForm"].action = 'entityParentSaveGoView-stac_sslDischargePre.do';
                                                } else {
                                                    document.forms["mainForm"].action = old_action;
                                                }
                                                removeFromStorage();
                                                document.forms["mainForm"].submit();
                                            } else {
                                                try {
                                                    $('submitPreDischrge2').disabled = false;
                                                    $('submitPreDischrge1').disabled = false;
                                                } catch (e) {
                                                }
                                                $('submitButton').disabled = false;
                                            }
                                        }
                                        else if (res != '' && res != '0' && !a.match(/C\d\d/) != null) {
                                            try {
                                                $('submitPreDischrge2').disabled = false;
                                                $('submitPreDischrge1').disabled = false;
                                            } catch (e) {
                                            }
                                            $('submitButton').disabled = false;
                                        }
                                    }
                                }
                            });

                        }}});
            }
            <msh:ifFormTypeIsNotView formName="stac_sslDischargeForm">
            <msh:ifInRole roles="/Policy/Mis/Pregnancy/BirthNosologyCard/Create">
            //проверка, необходимо ли наличие карты нозологий перед сохранением
            /*function checkNessessaryDischargeNosologyCard(aPrefix) {
                HospitalMedCaseService.checkNessessaryDischargeNosologyCard(${param.id},{
                    callback: function(aResult) {
                        if (aResult=='0') {
                            showToastMessage("При выписке из патологии беременности необходимо выбрать нозологию!",null,true,false,4000);
                            showbirthNosologyCard(${param.id},null,null,null,aPrefix);
                        }
                        else saveNext(aPrefix);
                    }
                }) ;
            }*/

            function checkNessessaryDischargeNosologyCardOnload() {
                HospitalMedCaseService.checkNessessaryDischargeNosologyCard(${param.id},{
                    callback: function(aResult) {
                        if (aResult=='0') {
                            showToastMessage("При выписке из патологии беременности необходимо выбрать нозологию!",null,true,false,4000);
                            showbirthNosologyCard(${param.id},null,null,null,null,null,null,true);
                        }
                    }
                }) ;
            }
            //создание сопутствующих диагнозов из списка нозологий (в случае, когда заполнено)
            function fillСoncomitantDiagnosis(id) {
                if (id==${param.id}) { //если это - текущее окно
                    HospitalMedCaseService.getConcomitantDiagnosisFromNosCard(
                        id, {
                            callback: function(aResult) {
                                if (aResult!=null && aResult!='[]') {
                                    var result = JSON.parse(aResult);
                                    for (var i=0; i<result.length; i++) {
                                        $('concomitantMkb').value=result[i].idcId; $('concomitantMkbName').value=result[i].idcName;
                                        $('concomitantDiagnos').value=result[i].idcName;
                                        addDiag('concomitant');
                                    }
                            }}}
                    );
                }
            }
            checkNessessaryDischargeNosologyCardOnload();
            </msh:ifInRole>
            </msh:ifFormTypeIsNotView>
            onload=function(){

                var list_diag = ["complication","concomitant"] ;
                for (var j=0;j<list_diag.length;j++) {

                    if ($(list_diag[j]+'Diags').value!='') {
                        var addRowF="";
                        var ind_f=0 ;
                        for (var i=0;i<theFld.length;i++) {
                            addRowF+="ar["+(ind_f++)+"],"
                            if (theFld[i][2]==1) {
                                addRowF+="ar["+(ind_f++)+"],"
                            }
                        }
                        addRowF=addRowF.length>0?trim(addRowF).substring(0,addRowF.length-1):"";
                        addRowF="addRowDiag('"+list_diag[j]+"',"+addRowF+",1);"

                        var arr = $(list_diag[j]+'Diags').value.split("#@#");
                        for (var i=0;i<arr.length;i++) {
                            var ar=arr[i].split("@#@") ;
                            //alert(addRowF);
                            eval(addRowF) ;
                        }
                    }

                }

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
                if (+$(aDiagType+'Mkb').value==0 || confirm("Вы точно хотите продолжить? В этом случае Вы потеряете данные еще недобавленного диагноза!")) {
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
                var table = document.getElementById('other'+aDiagType+"DiagsTable");
                var row = document.createElement('TR');
                var td = document.createElement('TD');
                var tdDel = document.createElement('TD');
                table.appendChild(row);
                row.appendChild(td);
                var txt ="" ;addText="" ;
                if (aDiagType=="complication") {addText="ослож."} else if (aDiagType=="concomitant") {addText="сопут." ;}
                for (var i=0;i<theFld.length;i++) {
                    var fld_i = theFld[i] ;
                    if (fld_i[2]==1) {
                        txt+=" <label class='"+aDiagType+"Diags'>"+fld_i[0]+" "+addText+": </label>"+eval("a"+fld_i[1]+"Name")+" <input type='hidden' value='"+eval("a"+fld_i[1])+"'><input type='hidden' value='"+eval("a"+fld_i[1]+"Name")+"'>"
                    } else if (fld_i[2]==2) {
                        txt+=" <label class='"+aDiagType+"Diags'>"+fld_i[0]+" "+addText+":  </label><input type='text' style='width:85%' value='"+eval("a"+fld_i[1])+"'>"
                    }
                    if (i<theFld.length-1) txt+="<br>" ;
                }
                td.innerHTML=txt ;
                if (slo_form_is_view==0) {
                    row.appendChild(tdDel);
                    tdDel.style.width='2%' ;
                    tdDel.innerHTML = "<input type='button' name='subm' onclick='var node=this.parentNode.parentNode;node.parentNode.removeChild(node);createOtherDiag(\""+aDiagType+"\")' value='- диагноз' />"
                        + "<input type='button' name='subm' onclick='var node=this.parentNode.parentNode;editMkbByDiag(\""+aDiagType+"\",node);' value='редак.' />";
                    if (+aIsLoad>0 && (+aMkb==0)) {
                        //if (+$(aDiagType+"Mkb").value==0) editMkbByDiag(aDiagType,row) ;
                    }
                }
            }
        </script>

        <msh:ifInRole roles="/Policy/Mis/MedCase/Stac/Ssl/Discharge/NotViewDischargeEpicrisis">
            <script type="text/javascript">
                $('outcomeName').select() ;
                $('outcomeName').focus() ;
            </script>
        </msh:ifInRole>
        <msh:ifFormTypeIsNotView formName="stac_sslDischargeForm">
            <script type="text/javascript">
                function checkStorage() {
                    try {
                        if (localStorage.getItem("stac_sslDischargeForm" + ";" + medCaseId.value + ";" + document.getElementById('current_username_li').innerHTML) != null) {
                            if (confirm('Обнаружена несохранённая выписка. Восстановить? Она заменит введённый текст.')) {
                                $('dischargeEpicrisis').value = localStorage.getItem("stac_sslDischargeForm" + ";" + medCaseId.value + ";" + document.getElementById('current_username_li').innerHTML);
                            }
                            //removeFromStorage();
                        }
                        else showToastMessage("Данных для восстановления не найдено!",null,true);
                    } catch (e) {
                    }
                }
                function submitFunc() {
                    var frm = document.stac_sslDischargeForm;
                    var medCaseId = document.querySelector('#id');
                    removeFromStorage();
                    frm.action= "entityParentEdit-stac_sslDischarge.do";
                    frm.submit();
                }

                HospitalMedCaseService.isCanDischarge('${param.id}', {
                    callback: function(aResult) {
                        if (aResult!=null) {
                            $('errorInformation').innerHTML=aResult + " <u>Выписка создаваться не будет!!!</u>";
                            $('errorInformation').style.display = 'block' ;
                        } else {
                            $('errorInformation').style.display = 'none' ;
                        }
                    }
                })
            </script>
            <msh:ifNotInRole roles="/Policy/Mis/MedCase/Stac/Ssl/Discharge/NotViewDischargeEpicrisis">
                <script type="text/javascript">
                    $('dischargeEpicrisis').select() ;
                    $('dischargeEpicrisis').focus() ;
                    var isChangeSizeEpicrisis = 1 ;
                    function changeSizeEpicrisis() {
                        if (isChangeSizeEpicrisis==1) {
                            Element.addClassName($('dischargeEpicrisis'), "epicrisis") ;
                            if ($('changeSizeEpicrisisButton')) {
                                $('changeSizeEpicrisisButton').value='Уменьшить'
                                $('changeSizeEpicrisisButton1').value='Уменьшить'
                            } ;
                            isChangeSizeEpicrisis=0 ;
                        } else {
                            Element.removeClassName($('dischargeEpicrisis'), "epicrisis") ;
                            if ($('changeSizeEpicrisisButton')) {
                                $('changeSizeEpicrisisButton').value='Увеличить' ;
                                $('changeSizeEpicrisisButton1').value='Увеличить' ;
                            }
                            isChangeSizeEpicrisis=1;
                        }
                    }
                    eventutil.addEventListener($('dischargeEpicrisis'), "dblclick",
                        function() {
                            changeSizeEpicrisis() ;
                        }) ;
                </script>
            </msh:ifNotInRole>

            <script type="text/javascript">
                try {
                    if (concludingMkbAutocomplete) concludingMkbAutocomplete.addOnChangeCallback(function() {
                        setDiagnosisText('concludingMkb','concludingDiagnos');

                    });} catch(e) {
                }
                try {
                    if (clinicalMkbAutocomplete) clinicalMkbAutocomplete.addOnChangeCallback(function() {
                        setDiagnosisText('clinicalMkb','clinicalDiagnos');
                    });} catch(e) {}
                try {
                    if (pathanatomicalMkbAutocomplete) pathanatomicalMkbAutocomplete.addOnChangeCallback(function() {
                        setDiagnosisText('pathanatomicalMkb','pathanatomicalDiagnos');
                    });} catch(e) {}
                try {
                    if (concomitantMkbAutocomplete) concomitantMkbAutocomplete.addOnChangeCallback(function() {
                        setDiagnosisText('concomitantMkb','concomitantDiagnos');
                    });} catch(e) {}
                try {
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
                function getDiagnosis(aFieldMkb) {
                    var val = $(aFieldMkb+'Name').value ;
                    var ind = val.indexOf(' ') ;
                    //alert(ind+' '+val)
                    if (ind!=-1) {
                        return val.substring(0,ind) ;
                    }
                    return null
                }
                function savePreRecord() {
                    HospitalMedCaseService.preRecordDischarge(
                        $('id').value,$('dischargeEpicrisis').value, {
                            callback: function(aResult) {
                                removeFromStorage();
                                alert("Текст выписки сохранён") ;
                            }
                        }
                    ) ;
                }
            </script>
            <msh:ifNotInRole roles="/Policy/Mis/MedCase/Protocol/NoCheckTime">
                <script type="text/javascript">

                    setTimeout(checktime,600000);

                    function checktime() {
                        if (confirm('Вы хотите сохранить выписку?')) {

                            check_diags('');
                            removeFromStorage();
                        }else {setTimeout(checktime,600000); }

                    }

                </script>
            </msh:ifNotInRole>
        </msh:ifFormTypeIsNotView>
    </tiles:put>
</tiles:insert>

