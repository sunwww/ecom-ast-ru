<%@ tag pageEncoding="utf8" %>
<%@ taglib uri="http://www.nuzmsh.ru/tags/msh" prefix="msh" %>
<%@ taglib tagdir="/WEB-INF/tags" prefix="tags" %>
<%@ taglib uri="/WEB-INF/mis.tld" prefix="mis" %>
<%@ taglib uri="http://www.ecom-ast.ru/tags/ecom" prefix="ecom" %>

<%@  attribute name="form" required="true" description="" %>

<msh:ifFormTypeIsView formName="${form}">
    <msh:ifInRole roles="/Policy/Mis/MedPolicy/View,/Policy/Mis/MedCase/Stac/Ssl/Admission/Edit">
        <ecom:webQuery nativeSql="select mp.id, case when (mp.DTYPE='MedPolicyOmc')
        then 'ОМС' when (mp.DTYPE='MedPolicyDmcForeign')
        then 'ДМС иногороднего' when (mp.DTYPE='MedPolicyDmc')
        then 'ДМС' else 'ОМС иногороднего' end, ri.name as riname,mp.polnumber,mp.series,mp.actualDateFrom,mp.actualDateTo
        ,case when mc.datesync is not null then '&#9989;' else '&#x2716;' end as actually
        from MedCase_MedPolicy as mc left join MedPolicy as mp on mp.id=mc.policies_id
        left join reg_ic as ri on ri.id=mp.company_id where mc.MedCase_id=${param.id}"
                       name="policies"/>
        <msh:section>
            <msh:sectionTitle>
                <msh:link action="stac_policiesEdit.do?id=${param.id}">Прикрепленные полисы к случаю</msh:link>
            </msh:sectionTitle>
            <msh:sectionContent>
                <msh:table name="policies" hideTitle="false" action="entitySubclassView-mis_medPolicy.do" idField="1">
                    <msh:tableColumn property="sn" columnName="#"/>
                    <msh:tableColumn property="2" columnName="Тип"/>
                    <msh:tableColumn property="3" columnName="Страховая компания"/>
                    <msh:tableColumn property="4" columnName="Номер"/>
                    <msh:tableColumn property="5" columnName="Серия"/>
                    <msh:tableColumn property="6" columnName="Дата начала"/>
                    <msh:tableColumn property="7" columnName="Дата окончания"/>
                    <msh:tableColumn property="8" columnName="Проверка"/>
                </msh:table>
            </msh:sectionContent>
        </msh:section>
    </msh:ifInRole>
    <msh:ifInRole roles="/Policy/Mis/MedCase/Stac/Ssl/PhoneMessage/CriminalMessage/View">
        <msh:section createRoles="/Policy/Mis/MedCase/Stac/Ssl/PhoneMessage/CriminalMessage/Create"
                     createUrl="entityParentPrepareCreate-stac_criminalMessages.do?id=${param.id}"
                     title="Сообщения в полицию">
            <ecom:webQuery name="milMessages" nativeSql="
      		    select pm.id, pm.phoneDate
    ,vpht.name||coalesce(' '||vpmst.name,'')
    ,to_char(pm.whenDateEventOccurred,'dd.mm.yyyy')||' '||cast(pm.whenTimeEventOccurred as varchar(5)) as whenevent
    ,pm.place as pmplace
    ,coalesce(vpme.name,pm.recieverFio) as reciever
    ,vpmo.name as vphoname,wp.lastname as wplastname
    ,p.lastname||' '||p.firstname||' '||p.middlename||' г.р.'||to_char(p.birthday,'dd.mm.yyyy') as fiopat
    ,coalesce(vpmorg.name,pm.phone,pm.recieverOrganization) as organization
    ,pm.diagnosis as pmdiagnosis
    from PhoneMessage pm 
    left join VocPhoneMessageType vpht on vpht.id=pm.phoneMessageType_id
    left join VocPhoneMessageSubType vpmst on vpmst.id=pm.phoneMessageSubType_id
    left join VocPhoneMessageOrganization vpmorg on vpmorg.id=pm.organization_id
    left join VocPhoneMessageEmploye vpme on vpme.id=pm.recieverEmploye_id
    left join VocPhoneMessageOutcome vpmo on vpmo.id=pm.outcome_id
    left join WorkFunction wf on wf.id=pm.workFunction_id
    left join Worker w on w.id=wf.worker_id
    left join Patient wp on wp.id=w.person_id
    left join medcase m on m.id=pm.medCase_id
    left join Patient p on p.id=m.patient_id
	left join MisLpu as ml on ml.id=m.department_id 
	left join SecUser su on su.login=m.username
	left join WorkFunction wf1 on wf1.secUser_id=su.id
	left join Worker w1 on w1.id=wf1.worker_id
	left join MisLpu ml1 on ml1.id=w1.lpu_id     
    where pm.dtype='CriminalPhoneMessage'
    and pm.medCase_id=${param.id}
    order by pm.phoneDate
      		"/>
            <msh:table name="milMessages"
                       viewUrl="entityShortView-stac_criminalMessages.do"
                       action="entityParentView-stac_criminalMessages.do" idField="1">
                <msh:tableColumn columnName="Дата" property="2"/>
                <msh:tableColumn property="9" columnName="Пациент"/>
                <msh:tableColumn columnName="Тип" property="3"/>
                <msh:tableColumn columnName="Когда" property="4"/>
                <msh:tableColumn columnName="Место" property="5"/>
                <msh:tableColumn columnName="Фамилия принявшего" property="6"/>
                <msh:tableColumn columnName="Фамилия передавшего" property="8"/>
                <msh:tableColumn columnName="Диагноз" property="11"/>
                <msh:tableColumn columnName="Исход" property="7"/>
            </msh:table>
        </msh:section>
    </msh:ifInRole>
    <msh:ifInRole roles="/Policy/Mis/MedCase/Stac/Ssl/Slo/View">
        <msh:section
                title="Движения по отделениям. <a href='entityParentPrepareCreate-stac_slo.do?id=${param.id }'>Добавить новый случай лечения в отделении</a>">
            <ecom:webQuery name="allSLOs" nativeSql="select MedCase.id
          , MedCase.dateStart as mdateStart 
          , postDep.Name as mdepartment      
          , MedCase.transferDate as mtransferDate
          , tranDep.Name as mtransferDepartment        
          , MedCase.dateFinish as mdateFinish
          ,coalesce(osd.model,'-')||'('||coalesce(ose.model,'-')||')' as standart
          ,coalesce(owf.code||' ','')||ovwf.name||' '||owp.lastname||' '||substring(owp.firstname,1,1)||'. '||substring(owp.middlename,1,1)||'.' as doctor       
          ,
          case when (coalesce(MedCase.dateFinish,MedCase.transferDate,CURRENT_DATE)-MedCase.dateStart)=0 then 1
               when cast(BedFund.addCaseDuration as integer)=1 then (coalesce(MedCase.dateFinish,MedCase.transferDate,CURRENT_DATE)-MedCase.dateStart)+1
               else (coalesce(MedCase.dateFinish,MedCase.transferDate,CURRENT_DATE)-MedCase.dateStart)
          end
          ||
          case 
            when MedCase.dateFinish is not null then ' (выписан)'
          	when MedCase.transferDate is not null then ' (перевод)'
          	else ' (на текущий момент)'
          end as bedCount
          , MedCase.entranceTime mentranceTime,MedCase.transferTime as mtransferTime,MedCase.dischargeTime as mdischargeTime            
          ,VocServiceStream.name as vssname
          
          
          from MedCase
           left  join WorkFunction owf  on MedCase.ownerFunction_id       = owf.id 
           left join Worker ow on ow.id=owf.worker_id
           left join VocWorkFunction ovwf on ovwf.id=owf.workFunction_id 
            left outer join Patient owp on ow.person_id           = owp.id 
             left outer join MisLpu tranDep on MedCase.transferDepartment_id           = tranDep.id 
              left outer join MisLpu postDep on MedCase.department_id           = postDep.id
               left join BedFund on BedFund.id=MedCase.bedFund_id 
          left join VocServiceStream on VocServiceStream.id=MedCase.serviceStream_id
          left join OMC_STANDART osd on MedCase.omcStandart_id=osd.id
          left join OMC_STANDART ose on MedCase.omcStandartExpert_id=ose.id
               left join VocBedSubType on VocBedSubType.id = BedFund.bedSubType_id 
               where MedCase.parent_id=${param.id}   and upper(MedCase.DTYPE)='DEPARTMENTMEDCASE'
               order by MedCase.dateStart,MedCase.entranceTime
               "/>
            <msh:table name="allSLOs" viewUrl="entityShortView-stac_slo.do" action="entityParentView-stac_slo.do"
                       idField="1">
                <msh:tableNotEmpty>
                    <tr>
                        <th colspan="1"/>
                        <th colspan="7" class="rightBold">Поступление</th>
                        <th colspan="3" class="rightBold">Перевод</th>
                        <th colspan="2" class="rightBold">Выписка</th>
                        <th colspan="1"/>
                    </tr>
                </msh:tableNotEmpty>
                <msh:tableColumn property="sn" columnName="#"/>
                <msh:tableColumn columnName="Дата" property="2"/>
                <msh:tableColumn columnName="Время" property="10"/>
                <msh:tableColumn columnName="Отделение" property="3" cssClass="rightBold"/>
                <msh:tableColumn columnName="Поток" property="13" cssClass="rightBold"/>
                <msh:tableColumn property="8" columnName="Леч. врач"/>
                <msh:tableColumn property="7" columnName="Стандарт"/>
                <msh:tableColumn columnName="Дата" property="4"/>
                <msh:tableColumn columnName="Время" property="11"/>
                <msh:tableColumn columnName="Отделение" property="5" cssClass="rightBold"/>
                <msh:tableColumn columnName="Дата" property="6"/>
                <msh:tableColumn columnName="Время" property="12"/>
                <msh:tableColumn columnName="Койко-дни" property="9"/>
            </msh:table>
        </msh:section>
    </msh:ifInRole>
    <msh:ifInRole roles="/Policy/Mis/MedCase/Stac/Ssl/SurOper/View">
        <ecom:webQuery name="allSurgOper" nativeSql="select so.id
          ,so.operationDate as sooperationDate
          ,so.operationTime as soperationTime
          ,ms.code||' '||ms.name as voname
          , case when parent.DTYPE='HospitalMedCase' then 'Приемное отделение' when parent.DTYPE='DepartmentMedCase' then d.name else '' end as whoIs  
          , vwf.name||' '||wp.lastname||' '||wp.firstname||' '||wp.middlename as doctor
          from SurgicalOperation as so 
          left join MedService ms on ms.id=so.medService_id
          left join medcase parent on parent.id=so.medcase_id 
          left join MisLpu d on d.id=parent.department_id 
          left join WorkFunction wf on wf.id=so.surgeon_id
          left join Worker w on w.id=wf.worker_id
          left join VocWorkFunction vwf on vwf.id=wf.workFunction_id
          left join Patient wp on wp.id=w.person_id
          where ('DepartmentMedCase'=(select DTYPE from medcase where id=${param.id}) 
          and  (so.medCase_id in (select id from medcase  where  parent_id=(select parent_id from medcase where id=${param.id} )) or so.medCase_id=(select parent_id from medcase where id=${param.id} )) ) or ('HospitalMedCase'=(select DTYPE from medcase where id=${param.id}) and  (so.medCase_id in (select id from medcase  where  parent_id=${param.id}) or so.medCase_id=${param.id}) )
          order by so.operationDate
          "/>
        <msh:tableNotEmpty name="allSurgOper">
            <msh:section title="Просмотр всех хир.операций по СЛС, включая все СЛО">
                <msh:table name="allSurgOper"
                           action="entityParentView-stac_surOperation.do" idField="1">
                    <msh:tableColumn columnName="#" property="sn"/>
                    <msh:tableColumn columnName="Отделение" property="5"/>
                    <msh:tableColumn columnName="Дата" property="2"/>
                    <msh:tableColumn columnName="Время" property="3"/>
                    <msh:tableColumn columnName="Операция" property="4"/>
                    <msh:tableColumn columnName="Хирург" property="6"/>
                </msh:table>
            </msh:section>
        </msh:tableNotEmpty>


    </msh:ifInRole>

    <msh:ifInRole roles="/Policy/Mis/MedCase/Stac/Ssl/HitechMedCase/View">
        <ecom:webQuery name="hitechCases" nameFldSql="hitechCases_sql" nativeSql="select himc.id
          ,to_char(himc.ticketDate,'dd.mm.yyyy') as f2_ticketDate
          ,to_char(himc.planHospDate,'dd.mm.yyyy') as f3_planHospDate
          ,coalesce(vkhc1.code ||' '||vkhc1.name,vkhc2.code ||' '||vkhc2.name) as f4_kind
          ,coalesce(vmhc1.code ||' '||vmhc1.name, vmhc2.code ||' '||vmhc2.name) as f5_method
          ,coalesce(himc.ticketNumber,'') as f6_ticketNumber
          from medcase slo
          left join HitechMedicalCase himc on  himc.medCase_id = slo.id
          left join vocKindHighCare vkhc1 on vkhc1.id=himc.kind_id
          left join vocKindHighCare vkhc2 on vkhc2.id= slo.kindhighcare_id
          left join vocMethodHighCare vmhc1 on vmhc1.id=himc.method_id
          left join vocMethodHighCare vmhc2 on vmhc2.id= slo.methodhighcare_id
          where
          slo.parent_id=${param.id} and slo.dtype='DepartmentMedCase'  and coalesce(vkhc1.id, vkhc2.id) is not null
          order by himc.ticketDate
          "/>
        <msh:section title="Случаи ВМП (включая случаи в СЛО) ">
            <msh:table
                    editUrl="entityParentEdit-stac_vmpCase.do"
                    name="hitechCases" action="entityParentView-stac_vmpCase.do" idField="1">
                <msh:tableColumn columnName="#" property="sn"/>
                <msh:tableColumn columnName="Номер талона" property="6"/>
                <msh:tableColumn columnName="Дата направления" property="2"/>
                <msh:tableColumn columnName="Дата предварительной госпитализации" property="3"/>
                <msh:tableColumn columnName="Вид" property="4"/>
                <msh:tableColumn columnName="Метод" property="5"/>
            </msh:table>
        </msh:section>
    </msh:ifInRole>
    <msh:ifInRole roles="/Policy/Mis/Journal/CheckDiabetes">
        <ecom:webQuery name="asCard" nativeSql="  select ac.id, act.name, to_char(ac.startDate,'dd.MM.yyyy') as priemDate
                  ,ac.ballsum as f4_ballsum
                  ,ass.name as risk
                  from assessmentCard ac
                  left join assessmentcardtemplate act on act.id=ac.template
                  left join assessment ass on ass.assessmentcard=ac.id
                  where ac.medcase_id=${param.id} and act.code='checkDiabetes'
                order by ac.startDate desc"/>
        <msh:section>
            <msh:sectionTitle>
                Риск развития сахарного диабета
                <msh:ifInRole roles="/Policy/Mis/AssessmentCard/Create"><a
                        href="javascript:goCreateAssessmentCard('checkDiabetes')">Добавить карту
                    оценки</a></msh:ifInRole>
            </msh:sectionTitle>
            <msh:sectionContent>
                <msh:table name="asCard" action="entityParentView-mis_assessmentCard.do" idField="1">
                    <msh:tableColumn columnName="Название" property="2"/>
                    <msh:tableColumn columnName="Дата приема" property="3"/>
                    <msh:tableColumn columnName="Сумма баллов" property="4"/>
                    <msh:tableColumn columnName="Риск" property="5"/>
                </msh:table>
            </msh:sectionContent>
        </msh:section>
    </msh:ifInRole>
    <msh:ifInRole roles="/Policy/Mis/MedCase/Diagnosis/View,/Policy/Mis/MedCase/Stac/Ssl/Diagnosis/View">
        <ecom:webQuery name="diags" nativeSql="
         select d.id as did, VocDiagnosisRegistrationType.name as vdrtname
         ,d.establishDate as establishDate
         ,d.name as dname,VocIdc10.code as mkbcode
         ,VocPriorityDiagnosis.name as vpdname  
         ,mkbB.code||' ' ||mkbB.name as backgroundName
         ,vwf.name || ' '||wpat.lastname||' '||wpat.firstname||' '||wpat.middlename as f8_worker
         from Diagnosis d
         left join VocIdc10 mkbB on mkbB.id=d.backgroundDisease_id
         left outer join VocDiagnosisRegistrationType on d.registrationType_id = VocDiagnosisRegistrationType.id
         left outer join VocPriorityDiagnosis on d.priority_id = VocPriorityDiagnosis.id
         left outer join VocIdc10 on d.idc10_id = VocIdc10.id
         left join workfunction wf on wf.id=d.medicalWorker_id
         left join worker w on w.id=wf.worker_id
         left join patient wpat on wpat.id=w.person_id
         left join vocworkfunction vwf on vwf.id=wf.workfunction_id
         where d.medCase_id=${param.id}"/>
        <msh:section createRoles="/Policy/Mis/MedCase/Diagnosis/Create"
                     createUrl="entityParentPrepareCreate-stac_diagnosis.do?id=${param.id }"
                     title="Диагнозы">
            <msh:table name="diags" action="entityParentView-stac_diagnosis.do" idField="1">
                <msh:tableColumn property="sn" columnName="#"/>
                <msh:tableColumn columnName="Тип регистрации" property="2"/>
                <msh:tableColumn columnName="Приоритет" property="6"/>
                <msh:tableColumn columnName="Дата" property="3"/>
                <msh:tableColumn columnName="Наименование" property="4"/>
                <msh:tableColumn columnName="Код МКБ" property="5"/>
                <msh:tableColumn columnName="Фоновое заболевание" property="7"/>
                <msh:tableColumn columnName="Установил" property="8"/>
            </msh:table>
        </msh:section>
    </msh:ifInRole>
    <msh:ifNotInRole roles="/Policy/Mis/MedCase/Protocol/View,/Policy/Mis/MedCase/Stac/Ssl/Protocol/View">
        <msh:ifInRole
                roles="/Policy/Mis/MedCase/Protocol/View,/Policy/Mis/MedCase/Stac/Ssl/Protocol/ViewForDeniedHospitalization">
        </msh:ifInRole>
        <msh:section>
            <ecom:webQuery name="protocols" nativeSql="select p.id as pid
		          ,p.dateRegistration as pdateregistration,p.timeRegistration as ptimeRegistration
		          ,p.record as precord
		          ,vwf.name||' '||wp.lastname||' '||wp.firstname||' '||wp.middlename as doctor
		           from diary p left join workfunction wf on wf.id=p.specialist_id 
		           left join medcase m on m.id=p.medcase_id
		           left join worker w on w.id=wf.worker_id left join patient wp on wp.id=w.person_id 
		           left join vocworkfunction vwf on vwf.id=wf.workfunction_id 
		           where p.medcase_id=${param.id} and p.dtype='Protocol'
		           and m.deniedHospitalizating_id is not null
		           order by p.dateRegistration desc, p.timeRegistration desc
		           "/>
            <msh:tableNotEmpty name="protocols">
                <msh:sectionTitle>
                    Дневник специалиста
                </msh:sectionTitle>
                <msh:sectionContent>
                    <msh:table hideTitle="false" disableKeySupport="false" idField="1" name="protocols"
                               action="entityParentView-smo_visitProtocol.do" disabledGoFirst="false"
                               disabledGoLast="false">
                        <msh:tableColumn property="sn" columnName="#"/>
                        <msh:tableColumn columnName="Дата" property="2"/>
                        <msh:tableColumn columnName="Время" property="3"/>
                        <msh:tableColumn columnName="Текст" property="4" cssClass="preCell"/>
                        <msh:tableColumn columnName="Специалист" property="5"/>
                    </msh:table>
                </msh:sectionContent>
            </msh:tableNotEmpty>
        </msh:section>

    </msh:ifNotInRole>
    <msh:ifInRole roles="/Policy/Mis/MedCase/Protocol/View,/Policy/Mis/MedCase/Stac/Ssl/Protocol/View">
        <msh:section>
            <msh:sectionTitle>
                Дневник специалиста<span style="color: purple;"> ПРИЕМНОГО ОТДЕЛЕНИЯ</span>.
                <msh:ifNotInRole roles="/Policy/Mis/MedCase/Protocol/CreateOnlyInMedService">
                    <msh:ifInRole
                            roles="/Policy/Mis/MedCase/Protocol/Create,/Policy/Mis/MedCase/Stac/Ssl/Protocol/Create">
                        <a href='entityParentPrepareCreate-smo_visitProtocol.do?id=${param.id}'> Добавить новый дневник
                            специалиста ПРИЕМНОГО ОТДЕЛЕНИЯ</a>
                        <a href="printProtocolsBySLS.do?id=${param.id}&medcase=${param.id}">Печать дневников приемного
                            отделения</a>
                    </msh:ifInRole>
                </msh:ifNotInRole>
            </msh:sectionTitle>
            <msh:sectionContent>
                <ecom:webQuery name="protocols" nativeSql="select p.id as pid
          ,p.dateRegistration as pdateregistration,p.timeRegistration as ptimeRegistration
          ,p.record as precord
          ,vwf.name||' '||wp.lastname||' '||wp.firstname||' '||wp.middlename as doctor
           from diary p left join workfunction wf on wf.id=p.specialist_id 
           left join worker w on w.id=wf.worker_id left join patient wp on wp.id=w.person_id 
           left join vocworkfunction vwf on vwf.id=wf.workfunction_id 
           where p.medcase_id=${param.id} and p.dtype='Protocol'
            order by p.dateRegistration desc, p.timeRegistration desc"/>
                <msh:table hideTitle="false" disableKeySupport="false" idField="1" name="protocols"
                           action="entityParentView-smo_visitProtocol.do" disabledGoFirst="false"
                           disabledGoLast="false">
                    <msh:tableColumn property="sn" columnName="#"/>
                    <msh:tableColumn columnName="Дата" property="2"/>
                    <msh:tableColumn columnName="Время" property="3"/>
                    <msh:tableColumn columnName="Текст" property="4" cssClass="preCell"/>
                    <msh:tableColumn columnName="Специалист" property="5"/>
                </msh:table>
            </msh:sectionContent>
        </msh:section>
    </msh:ifInRole>
</msh:ifFormTypeIsView>