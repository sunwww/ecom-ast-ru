<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib uri="http://struts.apache.org/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://www.nuzmsh.ru/tags/msh" prefix="msh" %>
<%@ taglib uri="http://www.ecom-ast.ru/tags/ecom" prefix="ecom" %>
<%@ taglib tagdir="/WEB-INF/tags" prefix="tags" %>
<tiles:insert page="/WEB-INF/tiles/mainLayout.jsp" flush="true">
    <tiles:put name="title" type="string">
        <ecom:titleTrail mainMenu="Expert2" beginForm="e2_entryForm" />
    </tiles:put>

    <tiles:put name="body" type="string">
        <tags:E2ServiceAdd name="Diagnosis"/>
        <msh:form action="/entityParentSaveGoView-e2_entry.do" defaultField="lastname">
            <msh:hidden property="id" />
            <msh:hidden property="saveType" />
            <msh:hidden property="listEntry" />
            <msh:hidden property="isDeleted" />
            <msh:hidden property="KLADRRegistration"/>
            <msh:hidden property="KLADRReal" />
            <msh:hidden property="okatoReg" />
            <msh:hidden property="okatoReal"/>
            <msh:panel>
     <msh:separator colSpan="4" label="Общие"/>
                <msh:row>
                    <msh:checkBox property="doNotSend" label="Не включать в реестр"/>
                    <msh:checkBox property="isForeign" />
                  </msh:row>
                <msh:row>
                <msh:checkBox property="isDefect" label="Дефект"/>
                <msh:checkBox property="medicalAbort" label="Аборт по медицинским показаниям"/>
                  </msh:row>
                <msh:row>
                <msh:checkBox property="isMobilePolyclinic" />
                <msh:checkBox property="isChildBirthDepartment" />
                  </msh:row>
                <msh:row>
                    <msh:textField property="mainMkb" size="50"/>
                    <msh:textField property="mainService" size="50"/>
                </msh:row>
                <msh:row>
                    <msh:autoComplete property="serviceStream" vocName="vocServiceStreamCode" size="50"/>
                    <msh:textField property="lpuCode"  size="50"/>
                </msh:row><msh:row>
                    <msh:checkBox property="isCriminalMessage" />
                    <msh:checkBox property="hotelServices" />
                </msh:row>
     <msh:separator colSpan="4" label="Пациент"/>
                <msh:row>
                    <msh:textField property="lastname"  size="50"/>
                    <msh:textField property="firstname"  size="50"/>
                </msh:row><msh:row>
                    <msh:textField property="middlename" size="50"/>
                    <msh:textField property="birthDate" size="50" />
                </msh:row><msh:row>
                    <msh:autoComplete vocName="vocSexCode" property="sex"  size="10"/>
                <msh:textField property="patientSnils"  size="50"/>
            </msh:row><msh:row>
                <msh:autoComplete vocName="vocSocialStatusCode" property="socialStatus"  size="50"/>
                    <msh:autoComplete vocName="vocNationalityCode" property="nationality"  size="50"/>
            </msh:row><msh:row>
                    <msh:autoComplete vocName="vocPassportTypeCode" property="passportType"  size="50"/>
            </msh:row><msh:row>
                    <msh:textField property="passportSeries" size="50" />
                    <msh:textField property="passportNumber" size="50" />
            </msh:row><msh:row>
                    <msh:textField property="passportDateIssued" size="50" />
                    <msh:textField property="passportWhomIssued" size="50" />
            </msh:row>
            <msh:row>
                <msh:textField property="height" size="10" />
                <msh:textField property="weigth" size="10" />
            </msh:row>
            <msh:row>
                <msh:textField property="addressRegistration" size="50" />
                <msh:textField property="addressReal" size="50" />
            </msh:row>
            <msh:row>
                <msh:textField property="birthPlace" size="50" />
            </msh:row>
    <msh:separator colSpan="4" label="Представитель"/>
                <msh:row>
                    <msh:textField property="kinsmanLastname" size="50" />
                    <msh:textField property="kinsmanFirstname" size="50" />
            </msh:row><msh:row>
                    <msh:textField property="kinsmanMiddlename" size="50"/>
                    <msh:textField property="kinsmanBirthDate" size="50" />
            </msh:row><msh:row>
                    <msh:autoComplete vocName="vocSexCode" property="kinsmanSex" size="50" />
                     <msh:textField property="kinsmanSnils" size="50" />
            </msh:row><msh:row>
                    <msh:textField property="kinsmanRole"  size="50" />
            </msh:row>

    <msh:separator colSpan="4" label="Медицинский полис"/>
                <msh:row>
                    <msh:textField property="commonNumber" size="50"/>
                </msh:row>
                <msh:row>
                    <msh:autoComplete property="medPolicyType" vocName="VocMedPolicyTypeCode" size="50"/>
                </msh:row><msh:row>
                    <msh:textField property="medPolicySeries" size="50" />
                    <msh:textField property="medPolicyNumber" size="50" />
                </msh:row><msh:row>
                    <msh:autoComplete vocName="vocInsuranceCompanySmoCode" property="insuranceCompanyCode"  size="50"/>
                    <msh:textField property="insuranceCompanyName" size="50"/>
                </msh:row>
                <msh:row>
                    <msh:textField property="insuranceCompanyOgrn" size="50"/>
                    <msh:textField property="insuranceCompanyTerritory" size="50"/>
                </msh:row>
    <msh:separator colSpan="4" label="Случай мед. обслуживания"/>
                <msh:row>
                    <msh:textField property="directLpu" size="50"/>
                    <msh:textField property="directLpuType" size="50"/>
                </msh:row>

                <msh:row>
                    <msh:checkBox property="isEmergency"/>
                    <msh:checkBox property="isChild"/>
                </msh:row>
                <msh:row>
                    <msh:autoComplete property="workPlace" vocName="vocWorkPlaceTypeCode"/>
                </msh:row>
                <msh:row>
                    <msh:textField property="historyNumber" size="50"/>
                    <msh:textField property="ticket263Number" size="50"/>
                </msh:row>
                <msh:row>
                    <msh:textField property="planHospDate" size="50"/>
                    <msh:textField property="directDate" size="50"/>
                </msh:row>
                <msh:row>
                    <msh:textField property="helpKind" size="50" />
                    <msh:textField property="stacType"  size="50"/>

                </msh:row>
                <msh:row>
                    <msh:textField property="departmentName" size="50"/>
                    <msh:textField property="departmentId" size="50" />
                </msh:row>
                <msh:row>
                    <msh:textField property="startDate"/>
                    <msh:textField property="startTime"/>
                </msh:row>
                <msh:row>
                    <msh:textField property="finishDate"/>
                    <msh:textField property="finishTime"/>
                </msh:row>
                <msh:row>
                    <msh:textField property="bedDays" size="50"/>
                    <msh:textField property="bedSubType" size="50" />
                </msh:row>
                <msh:row>
                    <msh:textField property="calendarDays" size="50"/>
                    <msh:checkBox property="isDiagnosticSpo" />

                </msh:row>
              <msh:row>
                    <msh:textField property="newbornWeight" size="50"/>
                  <msh:textField property="birthOrder"  size="50"/>
            </msh:row><msh:row>
                    <msh:textField property="departmentType" size="50" />
                    <msh:textField property="departmentCode" size="50" />
            </msh:row><msh:row>
                <msh:textField property="newbornAmount" size="50"/>
                <msh:checkBox property="multiplyBirth"/>

            </msh:row><msh:row>
                <msh:autoComplete vocName="vocE2FondV021Code" property="doctorWorkfunction" size="50" />
                    <msh:textField property="doctorName" size="50" />
            </msh:row><msh:row>
                    <msh:textField property="doctorSnils" size="50" />
            </msh:row><msh:row>
                    <msh:textField property="operationList" size="50" />
                    <msh:textField property="diagnosisList" size="50" />
                </msh:row><msh:row>
                    <msh:textField property="prescriptionList" size="50" />
            </msh:row>
                <msh:row>
                    <msh:textField property="services" size="50" />

                </msh:row>
               <msh:row>
                <msh:textField property="result" size="50"/>
                <msh:checkBox property="isCancer" />
            </msh:row><msh:row>
                <msh:textField property="reachedResult" size="50"/>
                <msh:textField property="reHospitalization" size="50"/>
                </msh:row>

    <msh:separator colSpan="8" label="ВМП"/>
           <msh:row>
                    <msh:textField property="VMPKind" size="50" />
                    <msh:textField property="VMPMethod" size="50" />
           </msh:row><msh:row>
                    <msh:textField property="VMPStantAmount" size="50" />
                    <msh:textField property="VMPTicketNumber" size="50" />
            </msh:row><msh:row>
                    <msh:textField property="VMPTicketDate" />
                    <msh:textField property="VMPPlanHospDate" />
            </msh:row>
    <msh:separator colSpan="4" label="Экономическая информация"/>
           <msh:row>
               <msh:autoComplete property="ksg" size="100" vocName="vocKSGByBedSubType" parentId="bedSubType" fieldColSpan="2" horizontalFill="true"/>
                </msh:row>
                <msh:row>
               <msh:textField property="dopKritKSG"  size="50"/>
           </msh:row>
                <msh:row>
               <msh:autoComplete property="ksgPosition" vocName="vocKsgPositionByKsg" parentAutocomplete="ksg" size="50" fieldColSpan="2" horizontalFill="true"/>
           </msh:row>
                <msh:row>
               <msh:checkBox property="isManualKsg" />
               <msh:textField property="cost" size="50" />

            </msh:row><msh:row>
               <msh:textField property="notFullPaymentReason" size="50"/>
               <msh:textField property="baseTarif" size="50"/>
            </msh:row><msh:row>
                <msh:textField property="totalCoefficient" size="50"/>
                <msh:row>
               <msh:textField property="costFormulaString" size="100" fieldColSpan="4"/>
                </msh:row>
            </msh:row>
                <msh:row>
                    <msh:hidden property="bill" />
                </msh:row>
                <msh:row>
                    <msh:textField property="billNumber" size="50"/>
                    <msh:textField property="billDate"/>
            </msh:row>
    <msh:separator colSpan="4" label="Всё в куче"/>
                <msh:row>
                    <msh:autoComplete property="medHelpKind" size="50" vocName="vocE2FondV008"/>
                    <msh:autoComplete property="medHelpUsl" size="50" vocName="vocE2FondV006"/>
                </msh:row>
            <msh:row>
                    <msh:autoComplete property="fondResult" size="50" vocName="vocE2FondV009"/>
                    <msh:autoComplete property="fondIshod" size="50" vocName="vocE2FondV012"/>
            </msh:row><msh:row>
                    <msh:autoComplete property="fondDoctorSpecV021" size="50" vocName="vocE2FondV021"/>
                    <msh:autoComplete property="medHelpProfile" size="50" vocName="vocE2MedHelpProfile"/>
                </msh:row><msh:row>
                <msh:autoComplete property="bedProfile" size="50" vocName="vocE2FondV020"/>
                </msh:row><msh:row>
                <msh:checkBox property="isRehabBed" />
            </msh:row><msh:row>


                </msh:row><msh:row>
                <msh:autoComplete property="vidSluch" size="50" vocName="vocE2VidSluch"/>
                <msh:autoComplete property="visitPurpose" size="50" vocName="vocE2FondV025"/>

                </msh:row><msh:row>
                    <msh:autoComplete property="kdpVisit" vocName="vocDiagnosticVisit" size="100" />
            </msh:row>
                <msh:row>
                    <msh:textField property="entryType" size="50"/>
                    <msh:textField property="fileType" size="50"/>
                </msh:row>
                <msh:row>
                    <msh:autoComplete vocName="vocE2FondV010" property="IDSP" size="50"/>
            </msh:row>
                <msh:row>
                    <msh:checkBox property="noOmcDepartment"/>
                <msh:textField property="parentEntry" size="50"/>
            </msh:row>
                <msh:row>
                <msh:checkBox property="isUnion"/>
                </msh:row>
                <msh:row>
                <msh:textArea property="fondComment" fieldColSpan="4"/>
                </msh:row>
            <msh:row>
                <msh:textArea property="comment" fieldColSpan="4"/>
            </msh:row>
            <msh:row>
                <msh:textField property="reanimationEntry"/>
            </msh:row>
            <msh:row>
                <msh:autoComplete vocName="vocE2EntrySubType" property="subType" size="100"/>
                </msh:row><msh:row>
            </msh:row>
                <msh:row>
                    <msh:checkBox property="PRNOV"/>
                </msh:row>
                <msh:separator colSpan="4" label="Доп. диспансеризация"/>
                <msh:row>
                    <msh:textField property="extDispType" size="50" />
                    <msh:textField property="extDispAge" size="50" />
                    </msh:row><msh:row>
                    <msh:textField property="extDispHealthGroup" size="50" />
                    <msh:textField property="extDispSocialGroup" size="50" />
                </msh:row>
                <msh:row>
                <msh:textField property="extDispAppointments" size="50" />
                    <msh:checkBox property="extDispNextStage"  />
                </msh:row>
                <msh:row>
                    <msh:autoComplete property="dispResult" vocName="vocE2FondV017" fieldColSpan="3" size="100" />
                </msh:row>

    <msh:separator colSpan="4" label="Служебная информация"/>
            <msh:row>
                <msh:textField property="hospitalStartDate" size="50" />
                <msh:textField property="hospitalFinishDate" size="50" />
            </msh:row><msh:row>
                    <msh:textField property="attachedLpu" size="50" />
                    <msh:textField property="externalPatientId" size="50" />
            </msh:row><msh:row>
                    <msh:textField property="externalId" size="50" />
                    <msh:textField property="externalPrevMedcaseId" size="50" />
            </msh:row><msh:row>
                    <msh:textField property="externalParentId" size="50" />
                </msh:row>

                <msh:submitCancelButtonsRow colSpan="1" />
            </msh:panel>
        </msh:form>
        <msh:ifFormTypeIsView formName="e2_entryForm">
            <msh:separator colSpan="4" label="Комплексные СЛО"/>
                <ecom:webQuery name="childEntries" nativeSql="select e.id
 ,e.departmentName, e.startDate, e.finishDate
 from e2entry e
 where e.parententry_id=${param.id} and (e.isDeleted is null or e.isDeleted='0') order by e.startdate, e.starttime"/>
                <msh:table idField="1" name="childEntries" action="entityParentView-e2_entry.do" noDataMessage="Нет дочерних СЛО">
                    <msh:tableColumn columnName="ИД" property="1"/>
                    <msh:tableColumn columnName="Отделение" property="2"/>
                    <msh:tableColumn columnName="Дата начала" property="3"/>
                    <msh:tableColumn columnName="Дата окончания" property="4"/>
                </msh:table>

            <msh:separator colSpan="4" label="Диагнозы по случаю"/>
            <msh:section>
                <ecom:webQuery name="diagnosisList" nativeSql="select d.id
 , mkb.code ||' '|| mkb.name as f2_name
 , vdrt.code||' '||vdrt.name as f3_regType
 , vpd.code||' '||vpd.name as f4_priority
 ,d.dopMkb as f5_dop
 ,(select code||' '||name from voce2fondv027 where code=d.illnessprimary) as f6_illnessPrimary
 ,vip.code||' '||vip.name as f7_vocIllnessPrimary
 from entryDiagnosis d
 left join vocidc10 mkb on mkb.id=d.mkb_id
 left join vocDiagnosisRegistrationType vdrt on vdrt.id=d.registrationType_id
 left join VocPriorityDiagnosis vpd on vpd.id=d.priority_id
 left join VocE2FondV027 vip on vip.id=d.vocillnessprimary_id
 where d.entry_id=${param.id}"/>
                <msh:table
                        idField="1" name="diagnosisList" action="javascript:void()"
                        deleteUrl="entityParentDeleteGoParentView-e2_entryDiagnosis.do"

                        noDataMessage="Нет диагнозов по случаю">
                    <msh:tableColumn columnName="ИД" property="1"/>
                    <msh:tableColumn columnName="Диагноз" property="2"/>
                    <msh:tableColumn columnName="Приоритет" property="4"/>
                    <msh:tableColumn columnName="Тип регистрации" property="3"/>
                    <msh:tableColumn columnName="Характер заболевания" property="7"/>
                    <msh:tableColumn columnName="Доп. код" property="5"/>
                </msh:table>
            </msh:section>

            <msh:separator colSpan="4" label="Услуги по случаю"/>
                <ecom:webQuery name="servicesList" nativeSql="select ms.id, coalesce(vms.code ||' '|| coalesce(vms.name,'Нет наименования')||coalesce(' ('|| ms.comment||')',''),'Нет услуги '||ms.comment) as f2
                , ms.serviceDate as name
                ,ms.doctorsnils as dsnils
                ,ms.cost as f5_cost
                ,case when ms.medservice_id is null then 'color: red'
                  when ms.comment!='' then 'color: #8B4513' else '' end as f6_styleRow
                  ,v021.code as f7_doctorWf
                from entryMedService ms
                left join VocMedService vms on vms.id=ms.medservice_id
                left join voce2fondv021 v021 on v021.id=ms.doctorspeciality_id
                     where ms.entry_id=${param.id}"/>
                <msh:table idField="1" styleRow="6" name="servicesList" action="jabascript:void()" noDataMessage="Нет услуг по случаю"
                           deleteUrl="entityParentDeleteGoParentView-e2_entryMedService.do" >
                    <msh:tableColumn columnName="ИД" property="1"/>
                    <msh:tableColumn columnName="Услуга" property="2"/>
                    <msh:tableColumn columnName="Дата оказания" property="3"/>
                    <msh:tableColumn columnName="СНИЛС" property="4"/>
                    <msh:tableColumn columnName="Должность" property="7"/>
                    <msh:tableColumn columnName="Цена" property="5"/>
                </msh:table>

            <msh:separator colSpan="4" label="Сложности лечения пациента"/>
        <ecom:webQuery name="patientDifficulty" nativeSql="select link.id, vd.code||' '||vd.name as name, coalesce(link.value, vd.value) as value
from E2CoefficientPatientDifficultyEntryLink link
left join VocE2CoefficientPatientDifficulty vd on vd.id=link.difficulty_id
where link.entry_id=${param.id}"/>
        <msh:table  idField="1" name="patientDifficulty" action="/javascript:void()" noDataMessage="Нет уровней сложности">
            <msh:tableColumn columnName="ИД" property="1"/>
            <msh:tableColumn columnName="Уровень сложности" property="2"/>
            <msh:tableColumn columnName="Коэффициент" property="3"/>
        </msh:table>
            <msh:section title="Случаи онкологического лечения" createUrl="entityParentPrepareCreate-e2_cancerEntry.do?id=${param.id}">
            <ecom:webQuery name="cancerEntry" nativeSql="select cancer.id, cancer.serviceType
from E2CancerEntry cancer
where cancer.entry_id=${param.id}"/>
            <msh:table idField="1" deleteUrl="entityParentDeleteGoParentView-e2_cancerEntry.do" name="cancerEntry" action="entityView-e2_cancerEntry.do" noDataMessage="Нет онкослучаев">
                <msh:tableColumn columnName="ИД" property="1"/>
                <msh:tableColumn columnName="тип услуги" property="2"/>
                <msh:tableColumn columnName="Коэффициент" property="3"/>
            </msh:table>
            </msh:section>
            <msh:section title="Дефекты оплаты">
            <ecom:webQuery name="sanctionList" nativeSql="select es.dopcode , coalesce(es.comment,'')
            , case when es.ismainDefect='1' then 'background-color:red' else '' end as f3_style
  from e2entrysanction es
  where es.entry_id=${param.id} "/>
            <msh:tableNotEmpty  name="sanctionList"  >
                <msh:table  idField="1" name="sanctionList" styleRow="3" action="/javascript:void()" >
                <msh:tableColumn columnName="Код" property="1"/>
                <msh:tableColumn columnName="Дефект" property="2"/>
                </msh:table>
            </msh:tableNotEmpty>
            </msh:section>

            <msh:section title="Особенности случая">
                <msh:autoComplete label="Добавить особенность" property="newFactor" vocName="vocE2EntryFactor" /><input type="button" value="Д. фактор" onclick="addOrDeleteEntryFactor($('newFactor').value,false)">
            <ecom:webQuery name="factorList" nativeSql="select ef.factor_id, vef.code ||' '|| vef.name
            from e2entry_factor ef
            left join VocE2EntryFactor vef on vef.id=ef.factor_id
  where ef.entry_id=${param.id} "/>
            <msh:tableNotEmpty  name="factorList"  >
                <msh:table idField="1" name="factorList" action="javascript:deleteEntryFactor" >
                <msh:tableColumn columnName="Фактор" property="2"/>
                </msh:table>
            </msh:tableNotEmpty>
            </msh:section>
        </msh:ifFormTypeIsView>

    </tiles:put>

    <tiles:put name="javascript" type="string">
        <msh:ifFormTypeAreViewOrEdit formName="e2_entryForm">
            <script type="text/javascript" src="./dwr/interface/Expert2Service.js"></script>
        <msh:ifFormTypeIsView formName="e2_entryForm">

                <script type="text/javascript">

                    function deleteEntryFactor(factor) {
                        addOrDeleteEntryFactor(factor,true);
                    }

                    function addOrDeleteEntryFactor(factor, isDelete) {
                        if (+factor>0) {
                            Expert2Service.addDeleteEntryFactor(${param.id}, factor ,isDelete , {
                                callback: function () {
                                    window.document.location.reload();
                                }
                            });
                        }
                    }

                    String.prototype.replaceAt=function(iFrom, iTo, replacement) {
                        return this.substr(0, iFrom+1) + replacement+ this.substr((iTo-1) + replacement.length);
                    };

                    function splitLongCase() {
                        Expert2Service.splitLongCase(${param.id}, {callback: function (a) {
                            alert(a);
                        }});
                    }
            function getEntryJson () {
                Expert2Service.getEntryJson(${param.id}, {
                    callback:function(js) {
                        console.log(js);
                        alert(js);
                    }
                });
            }

            function gotoMedcase() {
                window.open('entitySubclassView-mis_medCase.do?id='+$('externalId').value);
            }
            function makeCheck() {
                var recalcKsg=true;
                if ($('ksg').value && !confirm('Пересчитать КСГ?')) {
                    recalcKsg=false;
                }
                Expert2Service.checkEntry(${param.id}, recalcKsg, {
                    callback: function () {
                        window.location.reload();
                    }
                });
            }
            function makeMPFromRecord() {
                //Long aEntryListId, String aType, String aBillNumber, String aBillDate, Long aEntryId,
                var ver = "3.2";
                Expert2Service.makeMPFIle(null,$('entryType').value,$('billNumber').value, $('billDate').value
                    ,${param.id},false,ver,$('fileType').value,{
                    callback: function (monitorId) {
                        monitor.id=monitorId;
                        jQuery.toast("Проверка запущена");
                        updateStatus();

                    }
                });
            }
                    var monitor = {};
                    var statusToast;
                    function updateStatus() {
                        var id=monitor.id;
                        if (id){ //Если есть действующий монитор
                            if (statusToast) {
                            } else {
                                statusToast =jQuery.toast({
                                    heading:"Формирование"
                                    ,text:"Идет расчет..."
                                    ,icon:"info"
                                    ,hideAfter:false
                                });
                            }
                            RemoteMonitorService.getMonitorStatus(id, {
                                callback: function(aStatus) {
                                    var txt;
                                    if (aStatus.finish) {
                                        txt="Завеpшено!";
                                        if (aStatus.finishedParameters) {
                                            var hName =  aStatus.finishedParameters.replace(".MP",".xml");
                                            hName=hName.replaceAt(hName.lastIndexOf('/'),hName.indexOf('M30'),'H');
                                            //var hName =  filename.replace(".MP",".xml");hName=hName.replaceAt(16,"H");
                                            window.open("http://"+window.location.host+""+hName);
                                            //txt+=" <a href='"+aStatus.finishedParameters+"'>ПЕРЕЙТИ</a>";
                                        }
                                        monitor = {};
                                    } else {
                                        txt=aStatus.text;
                                        setTimeout(updateStatus,4000) ;
                                    }
                                    statusToast.update({
                                        text:txt
                                    });
                                },
                                errorHandler:function(message) {
                                    setTimeout(updateStatus,4000) ;
                                },
                                warningHandler:function(message) {
                                    setTimeout(updateStatus,4000) ;
                                }
                            });
                        }

                    }

            function showAllEntriesByPatient() {
                window.open("entityList-e2_entry.do?id=0&filter=commonnumber:"+$('commonNumber').value);
            }

            function makeSosud() {
                Expert2Service.changeToSosud(${param.id}, {
                   callback: function(ret) {
                       alert(ret);
                   }
                });
            }
                </script>

        </msh:ifFormTypeIsView>
            <msh:ifFormTypeIsNotView formName="e2_entryForm">
                <script type="text/javascript">
                ksgAutocomplete.setParentId($('bedSubType').value) ;
                ksgAutocomplete.addOnChangeCallback(function() {$('isManualKsg').checked=true;$('ksgPosition').value="";$('ksgPositionName').value="";});
                ksgPositionAutocomplete.addOnChangeCallback(function() {$('isManualKsg').checked=true;});
                </script>
            </msh:ifFormTypeIsNotView>
            <script type="text/javascript">
                function addMedServiceToEntry() {
                    if ($('newMedService').value) {
                        alert("запущено");
                        Expert2Service.addMedServiceToEntry(${param.id},$('newMedService').value, {
                            callback: function() {alert('a');}
                        });
                    }

                }
            </script>
        </msh:ifFormTypeAreViewOrEdit>

    </tiles:put>

    <tiles:put name="side" type="string">
        <msh:ifFormTypeIsView formName="e2_entryForm">
            <msh:sideMenu>
                <msh:sideLink action="/javascript:window.history.back()" name="Назад" roles="/Policy/E2/Edit" />
                 <%--<msh:IfPropertyIsFalse formName="some_shit" propertyName="doNotSend">--%>
                <msh:sideLink params="id" action="/entityParentEdit-e2_entry" name="Изменить" roles="/Policy/E2/Edit" />
                <msh:sideLink key="ALT+DEL" confirm="Удалить?" params="id" action="/entityParentDeleteGoParentView-e2_entry" name="Удалить" roles="/Policy/E2/Delete" />
                <msh:sideLink action="/javascript:makeCheck()" name="Пересчитать случай" roles="/Policy/E2/Edit" />
                <msh:sideLink action="/javascript:makeMPFromRecord()" name="Сделать файл из случая" roles="/Policy/E2/Edit" />
                <msh:sideLink action="/javascript:showDiagnosisAddServiceDialog()" name="Добавить диагноз/услугу" roles="/Policy/E2/Edit" />
                <%--</msh:IfPropertyIsFalse>--%>
                <msh:sideLink action="/javascript:gotoMedcase()" name="Перейти к СМО" roles="/Policy/E2" />
                <msh:sideLink action="/javascript:showAllEntriesByPatient()" name="Показать все случаи по пациенту" roles="/Policy/E2" />
                <msh:sideLink action="/javascript:makeSosud()" name="Сделать сосудистым случаем" roles="/Policy/E2" />
                <msh:sideLink action="/javascript:splitLongCase()" name="Расклеить обращение" roles="/Policy/E2/Admin" />
                <msh:sideLink action="/javascript:getEntryJson()" name="тест - получить случай json" roles="/Policy/E2/Admin" />
            </msh:sideMenu>
        </msh:ifFormTypeIsView>
    </tiles:put>
</tiles:insert>

