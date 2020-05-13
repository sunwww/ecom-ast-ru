<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://struts.apache.org/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://www.nuzmsh.ru/tags/msh" prefix="msh" %>
<%@ taglib uri="http://www.ecom-ast.ru/tags/ecom" prefix="ecom" %>
<%@ taglib tagdir="/WEB-INF/tags" prefix="tags" %>
<%@ taglib uri="/WEB-INF/mis.tld" prefix="mis" %>

<tiles:insert page="/WEB-INF/tiles/main${param.short}Layout.jsp" flush="true">

    <tiles:put name="title" type="string">
        <ecom:titleTrail mainMenu="Patient" beginForm="stac_sslForm"  />
    </tiles:put>
    <tiles:put name="side" type="string">
        <tags:stac_hospitalMenu currentAction="stac_ssl" />
    </tiles:put>
    <tiles:put name="body" type="string">
        <!--
        - Случай стационарного лечения (вся информация)
        -->
        <msh:ifFormTypeIsView formName="stac_sslForm">
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
                 where wchb.visit_id =${param.id}"/>
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
        <msh:form action="/entityParentSaveGoView-stac_ssl.do" defaultField="dateStart" title="Случай стационарного лечения. Поступление" editRoles="/Policy/Mis/MedCase/Stac/Ssl/Edit" createRoles="/Police/Mis/MedCase/Stac/Ssl/Create">
            <msh:hidden property="id" />
            <msh:hidden property="patient" />
            <msh:hidden property="saveType" />
            <msh:hidden property="guarantee"/>
            <msh:hidden property="identDate"/>
            <msh:hidden property="identTime"/>
            <msh:hidden property="identUsername"/>
            <msh:hidden property="isIdentified"/>
            <msh:ifNotInRole roles="/Policy/Mis/MedCase/IsPsychiatry">
                <msh:hidden property="lawCourtDesicionDate"/>
                <msh:hidden property="psychReason"/>
            </msh:ifNotInRole>
            <msh:panel>
                <msh:ifInRole roles="/Policy/Mis/MedCase/Stac/Ssl/Admission/Edit">
                    <msh:separator label="<a href='entityParentEdit-stac_sslAdmission.do?id=${param.id}'>Приемное отделение</a>" colSpan="8" />
                </msh:ifInRole>
                <msh:ifNotInRole roles="/Policy/Mis/MedCase/Stac/Ssl/Admission/Edit">
                    <msh:separator label="Приемное отделение" colSpan="8" />
                </msh:ifNotInRole>
                <msh:row >
                    <td colspan="6"><div style="display: none;" id='medPolicyInformation' class="errorMessage"/></td>
                </msh:row>        <msh:row>
                <msh:autoComplete showId="false" vocName="lpu" hideLabel="false" property="lpu" viewOnlyField="false" label="Лечебное учреждение" fieldColSpan="5" horizontalFill="true" />
            </msh:row>
                <msh:row>
                    <msh:textField property="statCardNumber" label="Номер стат.карты" horizontalFill="true" viewOnlyField="true" />
                </msh:row>
                <msh:row>
                    <msh:textField property="dateStart" label="Дата поступления" />
                    <msh:textField property="entranceTime" label="время" fieldColSpan="2" />
                </msh:row>
                <msh:row>
                    <msh:autoComplete property="orderLpu" label="Кем направлен" vocName="mainLpu" horizontalFill="true" />
                    <msh:textField property="orderNumber" label="№ напр" />
                    <msh:textField property="orderDate" label="Дата" />
                </msh:row>
                <msh:row>
                    <msh:textField property="orderDiagnos" label="ДИАГНОЗ направившего учреждения" fieldColSpan="6" horizontalFill="true" />
                </msh:row>
                <msh:row>
                    <msh:autoComplete property="orderMkb" label="Код МКБ направителя" horizontalFill="true" vocName="vocIdc10" fieldColSpan="6" />
                </msh:row>
                <msh:row>
                    <msh:autoComplete showId="false" vocName="vocOmcFrm" hideLabel="false" property="orderType" viewOnlyField="false" horizontalFill="true" />
                    <msh:textField property="supplyOrderNumber" label="Номер наряда" />
                </msh:row>
                <msh:row>
                    <msh:autoComplete property="intoxication" label="Состояние опьянения" vocName="vocIntoxication" horizontalFill="true" />
                    <msh:autoComplete property="deniedHospitalizating" label="Отказа от госп." vocName="vocDeniedHospitalizating" fieldColSpan="2" horizontalFill="true" />
                </msh:row>
                <msh:row>
                    <msh:autoComplete property="preAdmissionDefect" label="Дефекты догоспитального этапа" vocName="vocPreAdmissionDefect" fieldColSpan="3" horizontalFill="true" />
                </msh:row>
                <msh:row>
                    <msh:autoComplete vocName="vocServiceStream" property="serviceStream" label="Поток обслуживания" fieldColSpan="1" horizontalFill="true" />
                    <msh:checkBox property="ambulanceTreatment" label="Амбулаторное лечение" />
                </msh:row>
                <msh:row>
                    <msh:textField property="entranceDiagnos" label="ДИАГНОЗ приемного отделения" fieldColSpan="6" horizontalFill="true" />
                </msh:row>
                <msh:row>
                    <msh:autoComplete property="entranceMkb" label="Код МКБ приемного отделения" horizontalFill="true" vocName="vocIdc10" fieldColSpan="6" />
                </msh:row>
                <msh:row>
                    <msh:checkBox property="emergency" label="Экстренное поступление"  />
                    <msh:autoComplete property="preAdmissionTime" label="Часы заболевания" horizontalFill="true" vocName="vocPreAdmissionTime" fieldColSpan="2" />
                </msh:row>
                <msh:row>
                    <msh:autoComplete horizontalFill="true" property="pediculosis" vocName="vocPediculosis" label="Педикулез" fieldColSpan="3"/>
                </msh:row>
                <msh:row>
                    <msh:autoComplete property="department" label="Отделение" vocName="vocLpuOtd" fieldColSpan="5" horizontalFill="true" parentAutocomplete="lpu" />
                </msh:row>
                <msh:ifInRole roles="/Policy/Mis/MedCase/IsPsychiatry">
                    <msh:row>
                        <msh:autoComplete label="Причина госпитализации в психиатрический стационар" vocName="vocPsychHospitalReason" property="psychReason" labelColSpan="3"/>
                    </msh:row>
                    <msh:row>
                        <msh:checkBox property="compulsoryTreatment" label="Принудительное лечение"/>
                    </msh:row>
                    <msh:row>
                        <msh:checkBox property="incapacity" label="Недееспособный (статья 29)"/>
                        <msh:textField property="lawCourtDesicionDate" label="Дата решения суда"/>
                    </msh:row>
                </msh:ifInRole>
                <msh:row>
                    <msh:autoComplete property="bedType" label="Профиль коек" horizontalFill="true" vocName="vocBedType" fieldColSpan="6" />
                </msh:row>
                <msh:row>
                    <msh:checkBox property="relativeMessage" label="Сообщение родственникам" />
                    <msh:checkBox property="medicalAid" label="Оказана помощь в приемном отделении" fieldColSpan="3" />
                </msh:row>
                <msh:row>
                    <msh:textField property="attendant" label="Сопровождающее лицо" fieldColSpan="5" horizontalFill="true" />
                </msh:row>
                <msh:row>
                    <msh:autoComplete property="trauma" label="Травма" vocName="vocTraumaType" fieldColSpan="5" horizontalFill="true" />
                </msh:row>
                <msh:row>
                    <msh:textField property="height" label="Рост (см)"  fieldColSpan="3" horizontalFill="false" />
                    <msh:textField property="weight" label="Вес (кг)" fieldColSpan="3" horizontalFill="false" />
                    <msh:textField property="theIMT" label="ИМТ" fieldColSpan="3" horizontalFill="false" />
                </msh:row>
                <msh:row />
                <msh:row>
                    <msh:checkBox property="noActuality" label="Недействительность" />
                    <msh:label property="username" label="Оператор" />
                </msh:row>
            </msh:panel>
            <msh:panel styleId="epicriPanel" colsWidth="1%,1%,1%,1%">
                <msh:ifInRole roles="/Policy/Mis/MedCase/Protocol/View">
                    <msh:ifNotInRole roles="/Policy/Mis/MedCase/Stac/Ssl/Discharge/NotViewDischargeEpicrisis">
                        <msh:row>
                            <msh:separator colSpan="8" label="Выписной эпикриз" />
                        </msh:row>
                        <msh:row>
                            <msh:textArea property="dischargeEpicrisis" fieldColSpan="3" label="Текст" />
                        </msh:row>
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
                <msh:ifInRole roles="/Policy/Mis/MedCase/Stac/Ssl/Discharge/Edit">
                    <msh:separator colSpan="8" label="<a href='entityParentEdit-stac_sslDischarge.do?id=${param.id}'>Выписка</a>" />
                </msh:ifInRole>
                <msh:ifNotInRole roles="/Policy/Mis/MedCase/Stac/Ssl/Discharge/Edit">
                    <msh:separator colSpan="8" label="Выписка" />
                </msh:ifNotInRole>
                <msh:row>
                    <msh:autoComplete vocName="vocIdc10" label="МКБ-10 закл.диаг." property="concludingMkb" fieldColSpan="3" horizontalFill="true"/>
                </msh:row>
                <msh:row>
                    <msh:textField label="Заключительный диагноз" property="concludingDiagnos" fieldColSpan="3" horizontalFill="true"/>
                </msh:row>

                <msh:row>
                    <msh:autoComplete label="Результат госпитализации" property="result" horizontalFill="true" vocName="vocHospitalizationResult" />
                    <msh:autoComplete label="Исход" property="outcome" vocName="vocHospitalizationOutcome" horizontalFill="true" />
                </msh:row>
                <msh:row>
                    <msh:textField label="Дата выписки" property="dateFinish" />
                    <msh:textField label="Время выписки" property="dischargeTime" />
                </msh:row>
                <msh:row>
                    <msh:autoComplete label="Перевод в др ЛПУ" property="moveToAnotherLPU" fieldColSpan="5" horizontalFill="true" vocName="lpu" />
                </msh:row>
                <msh:row>
                    <msh:checkBox property="rareCase" label="Редкий случай" />
                </msh:row>
                <mis:ifPatientIsWoman classByObject="Patient" idObject="stac_sslForm.patient" roles="/Policy/Mis/Pregnancy/History/View">
                    <msh:separator label="Беременность" colSpan="9"/>
                    <msh:row>
                        <msh:autoComplete viewAction="entityParentView-preg_pregnancy.do" property="pregnancy" label="Беременность" fieldColSpan="3" parentId="patient" vocName="pregnancyByPatient" horizontalFill="true"/>
                    </msh:row>
                </mis:ifPatientIsWoman>

                <msh:row>
                    <msh:separator label="Дополнительно" colSpan="4"/>
                </msh:row>
                <msh:row>
                    <msh:label property="createDate" label="Дата создания"/>
                    <msh:label property="username" label="Оператор" />
                </msh:row>
                <msh:row>
                    <msh:label property="editDate" label="Дата редак."/>
                    <msh:label property="editUsername" label="Оператор" />
                </msh:row>
                <msh:submitCancelButtonsRow colSpan="4" labelSave="Сохранить изменения" labelCreating="Создание" labelCreate="Создать новый случай" labelSaving="Сохранение данных" />
            </msh:panel>
        </msh:form>

        <msh:ifFormTypeIsView formName="stac_sslForm">
            <msh:ifInRole roles="/Policy/Mis/Oncology/Case/View">
                <msh:section createUrl="entityParentPrepareCreate-oncology_case_reestr.do?id=${param.id}"
                             title="Случай онкологического лечения">
                    <ecom:webQuery name="list" nativeSql="
                    select oc.id,
                    case when oc.suspiciononcologist=true then 'Да' else 'Нет' end as sus,
                    ort.name as reason,
                    case when oc.distantmetastasis=true then 'Да' else 'Нет' end as disy,
                    n02.name as stad,
                    case when n03.name is not null and n03.name!='' then n03.name else n03.tumorcode end as tumor,
                    case when n04.name is not null and n04.name!='' then n04.name else n04.noduscode end as nodus,
                    case when n05.name is not null and n05.name!='' then n05.name else n05.metastasiscode end as metastasis,
                    cons.name as consil,
                    n13.name as typetreat,
                    oc.mkb
                    from oncologycase oc
                    left join vocOncologyReasonTreat ort on ort.id = oc.vocOncologyReasonTreat_id
                    left join vocOncologyN002 n02 on n02.id = oc.stad_id
                    left join vocOncologyN003 n03 on n03.id = oc.tumor_id
                    left join vocOncologyN004 n04 on n04.id = oc.nodus_id
                    left join vocOncologyN005 n05 on n05.id = oc.metastasis_id
                    left join voconcologyconsilium cons on cons.id = oc.consilium_id
                    left join vocOncologyN013 n13 on n13.id = oc.typeTreatment_id
                    where medcase_id = ${param.id}"/>
                    <msh:table viewUrl="entityView-oncology_case_reestr.do?short=Short" name="list"
                               action="entityParentView-oncology_case_reestr.do" idField="1" >
                        <msh:tableColumn columnName="#" property="sn"/>
                        <msh:tableColumn columnName="Подозрение на ЗНО" property="2"/>
                        <msh:tableColumn columnName="Причина обращения" property="3"/>
                        <msh:tableColumn columnName="Удаленные метастазы" property="4"/>
                        <msh:tableColumn columnName="Стадия" property="5"/>
                        <msh:tableColumn columnName="Tumor" property="6"/>
                        <msh:tableColumn columnName="Nodus" property="7"/>
                        <msh:tableColumn columnName="Metastasis" property="8"/>
                        <msh:tableColumn columnName="Консилиум" property="9"/>
                        <msh:tableColumn columnName="Тип лечения" property="10"/>
                        <msh:tableColumn columnName="Диагноз" property="11"/>
                    </msh:table>
                </msh:section>
            </msh:ifInRole>

            <mis:ifPatientIsWoman classByObject="Patient" idObject="stac_sslForm.patient" roles="/Policy/Mis/Pregnancy/ConfinementCertificate">
                <msh:section createUrl="entityParentPrepareCreate-preg_shortConfCertificate.do?id=${param.id}"
                             title="Родовый сертификат">
                    <ecom:webQuery name="list" nativeSql="
                    select c.id, c.number from Certificate c
                    where c.dtype='ShortConfinementCertificate' and c.medcase_id = ${param.id}"/>
                    <msh:table viewUrl="entityView-preg_shortConfCertificate.do?short=Short" name="list"
                               action="entityParentView-preg_shortConfCertificate.do" idField="1" >
                        <msh:tableColumn columnName="#" property="sn"/>
                        <msh:tableColumn columnName="Номер" property="2"/>
                    </msh:table>
                </msh:section>
            </mis:ifPatientIsWoman>
        </msh:ifFormTypeIsView>
        <tags:stac_infoBySls form="stac_sslForm"/>
    </tiles:put>
    <tiles:put name="javascript" type="string">
        <script type='text/javascript' src='./dwr/interface/HospitalMedCaseService.js'></script>
        <script type="text/javascript" src="./dwr/interface/PatientService.js"></script>

        <script type="text/javascript" >
        <msh:ifFormTypeIsView formName="stac_sslForm">
        function makeTimurHappy() {
            showToastMessage("Подождите, идет расчет...",null,true,null, 5000);
                jQuery.ajax({
                    url:"api/login/getTimurBiohim?medcaseId=${param.id}&token=123"
                    ,error: function(jqXHR,ex){console.log(ex);alert('Произошла какая-то ошибка: '+ex);}
                    ,success: function(el) {
                        if (!el)  {
                            alert("Ошибка расчета гиперкоэффициента абразивности!");
                        } else {
                     jQuery.ajax({
                         url: "http://192.168.1.52:8080/api/1.0/sdrisk"
                         ,method:"POST"
                         , data: JSON.stringify(el)
                         , contentType: "application/json; charset=utf-8"
                         , dataType: "json"
                         , success: function (el2) {
                             if (el2 && el2.status && el2.status == "ok") {
                                 var txt = el2.clinical_risk+"<br>"+el2.ins_risk+"<br>"+el2.final_risk;
                                 showToastMessage(txt,null,false);
                             } else {
                                 console.log(JSON.stringify(el2));
                             }
                         }
                     });
                        }
                    }
                });
        }
            <msh:ifInRole roles="/Policy/Mis/Pregnancy/ConfinementCertificate">
            PatientService.checkSLSonDepartment(${param.id},{
                callback : function(res) {
                    if(res==true){
                        if(confirm("Требуется добавить родовый сертификат!")){
                            document.location.replace("entityParentPrepareCreate-preg_shortConfCertificate.do?id=${param.id}");
                        }
                    }
                }
            });
        </msh:ifInRole>
        var info = document.getElementById('mainFormLegend').parentNode.innerHTML;
        function loadBracelets() {
            //вывод браслетов #151
            HospitalMedCaseService.selectIdentityPatient(
                ${param.id},true, {
                    callback: function(res) {
                        document.getElementById('mainFormLegend').parentNode.innerHTML=info;
                        if (res!=null && res!='[]') {
                            var aResult = JSON.parse(res);
                            var str='<table style="margin-left:45%"><tr>';
                            for (var i=0; i<aResult.length; i++) {
                                var brace = aResult[i];
                                var msg = brace.info ? brace.info : brace.vsipnameJust;
                                var style = 'width: 40px;height: 40px;outline: 1px solid gray; border:2px; margin-right: 2px; margin-left: 2px;';
                                style+= brace.picture ? 'background-image: url(\'/skin/images/bracelet/'+brace.picture+'\');background-size: 40px 40px; '
                                    :' background-color: '+brace.colorCode +';';
                                str+='<td><div onclick="showToastMessage(\''+msg+'\',null,true,false);" title="'+msg+'" style="'+style+'"></div></td>';
                            }
                            str+="</tr></table>";
                            document.getElementById('mainFormLegend').parentNode.innerHTML=document.getElementById('mainFormLegend').parentNode.innerHTML.replace('<h2 id="mainFormLegend">Госпитализация</h2>',"<h2 id=\"mainFormLegend\">Госпитализация</h2>"+str);
                        }
                    }
                }
            );
        }
        loadBracelets();
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
        <msh:ifInRole roles="/Policy/Mis/Journal/CheckDiabetes">
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
        </msh:ifInRole>
            </msh:ifFormTypeIsView>
            function printPrescriptionList(id) {
                window.document.location='print-prescriptList_1.do?s=HospitalPrintService&m=printPrescriptList&id='+id;
            }
            function printPrescriptionListTotal (id) {
                window.document.location='print-prescriptListTotal.do?s=HospitalPrintService&m=printPrescriptListTotal&id='+id;
            }
        </script>
    </tiles:put>
</tiles:insert>

