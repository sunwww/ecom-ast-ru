<%@ tag pageEncoding="utf8" %>
<%@ taglib uri="http://www.nuzmsh.ru/tags/msh" prefix="msh" %>
<%@ taglib tagdir="/WEB-INF/tags" prefix="tags" %>
<%@ taglib uri="/WEB-INF/mis.tld" prefix="mis" %>
<%@ taglib uri="http://www.ecom-ast.ru/tags/ecom" prefix="ecom" %>

<%@  attribute name="form" required="true" description="" %>

    <msh:ifFormTypeIsView formName="${form}" guid="48eb9700-d07d-4115-a476-a5a439731d3e">
      <msh:ifInRole roles="/Policy/Mis/MedPolicy/View,/Policy/Mis/MedCase/Stac/Ssl/Admission/Edit">
        <ecom:webQuery nativeSql="select mp.id, case when (mp.DTYPE='MedPolicyOmc')
        then 'ОМС' when (mp.DTYPE='MedPolicyDmcForeign')
        then 'ДМС иногороднего' when (mp.DTYPE='MedPolicyDmc')
        then 'ДМС' else 'ОМС иногороднего' end, ri.name as riname,mp.polnumber,mp.series,mp.actualDateFrom,mp.actualDateTo
        ,case when mc.datesync is not null then '&#9989;' else '&#x2716;' end as actually
        from MedCase_MedPolicy as mc left join MedPolicy as mp on mp.id=mc.policies_id
        left join reg_ic as ri on ri.id=mp.company_id where mc.MedCase_id=${param.id}"
                       name="policies" guid="12ed9815-1a16-441a-8d89-36bb94761f9b" />
          <msh:section guid="746b6d8a-b92f-4bd0-9899-d32855f3aa95">
          <msh:sectionTitle>
          <msh:link action="stac_policiesEdit.do?id=${param.id}"  >Прикрепленные полисы к случаю</msh:link>
          </msh:sectionTitle>
          <msh:sectionContent>
            <msh:table name="policies" hideTitle="false" action="entitySubclassView-mis_medPolicy.do" idField="1" guid="polices">
              <msh:tableColumn property="sn" columnName="#" />
              <msh:tableColumn property="2" columnName="Тип" />
              <msh:tableColumn property="3" columnName="Страховая компания" />
              <msh:tableColumn property="4" columnName="Номер"/>
              <msh:tableColumn property="5" columnName="Серия"/>
              <msh:tableColumn property="6" columnName="Дата начала" />
              <msh:tableColumn property="7" columnName="Дата окончания" />
              <msh:tableColumn property="8" columnName="Проверка"/>
            </msh:table>
          </msh:sectionContent>
          </msh:section>
      </msh:ifInRole>
      <msh:ifInRole roles="/Policy/Mis/MedCase/Stac/Ssl/PhoneMessage/CriminalMessage/View">
      	<msh:section createRoles="/Policy/Mis/MedCase/Stac/Ssl/PhoneMessage/CriminalMessage/Create" createUrl="entityParentPrepareCreate-stac_criminalMessages.do?id=${param.id}" title="Сообщения в полицию">
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
      			<msh:tableColumn columnName="Дата" property="2" />
      <msh:tableColumn property="9" columnName="Пациент"/>
      <msh:tableColumn columnName="Тип" property="3" />
      <msh:tableColumn columnName="Когда" property="4" />
      <msh:tableColumn columnName="Место" property="5" />
      <msh:tableColumn columnName="Фамилия принявшего" property="6" />
      <msh:tableColumn columnName="Фамилия передавшего" property="8" />
      <msh:tableColumn columnName="Диагноз" property="11" />
      <msh:tableColumn columnName="Исход" property="7" />
      		</msh:table>
      	</msh:section>
      </msh:ifInRole>
      <msh:ifInRole roles="/Policy/Mis/MedCase/Stac/Ssl/Slo/View" guid="0cbf528e-b3d1-48bb-9d50-a878dbbd0c4e">
        <msh:section title="Движения по отделениям. <a href='entityParentPrepareCreate-stac_slo.do?id=${param.id }'>Добавить новый случай лечения в отделении</a>" guid="313bfb94-a58e-4041-be05-dacad7710873">
          <ecom:webQuery  name="allSLOs" nativeSql="select MedCase.id
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
               " />
          <msh:table name="allSLOs" viewUrl="entityShortView-stac_slo.do" action="entityParentView-stac_slo.do" idField="1" guid="a99e7bed-cb69-49df-bbe6-ac9718cd22e0">
            <msh:tableNotEmpty guid="a6284e48-9209-412d-8436-c1e8e37eb8aa">
              <tr>
                <th colspan="1" />
                <th colspan="7" class="rightBold">Поступление</th>
                <th colspan="3" class="rightBold">Перевод</th>
                <th colspan="2" class="rightBold">Выписка</th>
                <th colspan="1" />
              </tr>
            </msh:tableNotEmpty>
              <msh:tableColumn property="sn" columnName="#" />
            <msh:tableColumn columnName="Дата" property="2" guid="024d14d9-7e0b-484a-ad93-7db35046b351" />
            <msh:tableColumn columnName="Время" property="10" />
            <msh:tableColumn columnName="Отделение" property="3" cssClass="rightBold" guid="e52974a2-76d9-404c-bd22-78f28d3456ed" />
            <msh:tableColumn columnName="Поток" property="13" cssClass="rightBold" />
            <msh:tableColumn property="8" columnName="Леч. врач"/>
            <msh:tableColumn property="7" columnName="Стандарт"/>
            <msh:tableColumn columnName="Дата" property="4" guid="26ab551e-5512-4c99-91bd-ea0e083e98cb" />
            <msh:tableColumn columnName="Время" property="11" />
            <msh:tableColumn columnName="Отделение" property="5" cssClass="rightBold" guid="cedd2007-2476-48d1-b24c-92a4606d2eca" />
            <msh:tableColumn columnName="Дата" property="6" guid="5b3627ee-362b-4923-a1d9-f0213cd89480" />
            <msh:tableColumn columnName="Время" property="12" />
            <msh:tableColumn columnName="Койко-дни" property="9" guid="8110d88e-6422-47c8-b13d-9e7720250627" />
          </msh:table>
        </msh:section>
      </msh:ifInRole>
      <msh:ifInRole roles="/Policy/Mis/MedCase/Stac/Ssl/SurOper/View" guid="ea6d2cd5-0263-4602-960d-644c49ab4975">
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
      <msh:ifInRole roles="/Policy/Mis/MedCase/Diagnosis/View,/Policy/Mis/MedCase/Stac/Ssl/Diagnosis/View" guid="4cc62e2b-bf2a-4839-8bab-481886e8e721">
         <ecom:webQuery name="diags" nativeSql="
         select Diagnosis.id as did, VocDiagnosisRegistrationType.name as vdrtname
         ,Diagnosis.establishDate as establishDate
         ,Diagnosis.name as dname,VocIdc10.code as mkbcode
         ,VocPriorityDiagnosis.name as vpdname  
         ,mkbB.code||' ' ||mkbB.name as backgroundName
         from Diagnosis
         left join VocIdc10 mkbB on mkbB.id=Diagnosis.backgroundDisease_id
         left outer join VocDiagnosisRegistrationType on Diagnosis.registrationType_id = VocDiagnosisRegistrationType.id 
         left outer join VocPriorityDiagnosis on Diagnosis.priority_id = VocPriorityDiagnosis.id
         left outer join VocIdc10     on Diagnosis.idc10_id = VocIdc10.id
         where Diagnosis.medCase_id=${param.id}" />
        <msh:section createRoles="/Policy/Mis/MedCase/Diagnosis/Create" createUrl="entityParentPrepareCreate-stac_diagnosis.do?id=${param.id }"
         title="Диагнозы" >
          <msh:table name="diags" action="entityParentView-stac_diagnosis.do" idField="1" guid="7312ce0c-0c61-482d-9079-71b2a0f29484">
              <msh:tableColumn property="sn" columnName="#" />
            <msh:tableColumn columnName="Тип регистрации" property="2" guid="e2fc05a6-e089-4c90-8fb2-a8377e6f8967" />
            <msh:tableColumn columnName="Приоритет" property="6" guid="e2fc05a6-e089-4c90-8fb2-a8377e6f8967" />
            <msh:tableColumn columnName="Дата" property="3" guid="718ec416-3543-4f8d-89cd-b24aa8177377" />
            <msh:tableColumn columnName="Наименование" property="4" guid="2a519337-384d-4695-9d19-72dd7e02936c" />
            <msh:tableColumn columnName="Код МКБ" property="5" guid="150732a8-0a5a-4ac3-bbbb-9ff669be37a6" />
            <msh:tableColumn columnName="Фоновое заболевание" property="7" guid="150732a8-0a5a-4ac3-bbbb-9ff669be37a6" />
          </msh:table>
        </msh:section>
      </msh:ifInRole>
      <msh:ifNotInRole roles="/Policy/Mis/MedCase/Protocol/View,/Policy/Mis/MedCase/Stac/Ssl/Protocol/View">
      	<msh:ifInRole roles="/Policy/Mis/MedCase/Protocol/View,/Policy/Mis/MedCase/Stac/Ssl/Protocol/ViewForDeniedHospitalization">
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
		           "/>
		           <msh:tableNotEmpty name="protocols">
		            <msh:sectionTitle>
		        	Дневник специалиста
			        </msh:sectionTitle>
			        <msh:sectionContent>
			          <msh:table hideTitle="false" disableKeySupport="false" idField="1" name="protocols" action="entityParentView-smo_visitProtocol.do" disabledGoFirst="false" disabledGoLast="false" guid="d0e60067-9aec-4ee0-b20a-4f6b37">
			              <msh:tableColumn property="sn" columnName="#"/>
					      <msh:tableColumn columnName="Дата" property="2" guid="0694f6a7-ed40-4ebf-a274-1efd6901cfe4" />
					      <msh:tableColumn columnName="Время" property="3" guid="0694f40-4ebf-a274-1efd6901cfe4" />
					      <msh:tableColumn columnName="Текст" property="4" guid="6682eeef-105f-43a0-be61-30a865f27972" cssClass="preCell"/>
					      <msh:tableColumn columnName="Специалист" property="5" guid="f31b12-3392-4978-b31f-5e54ff2e45bd" />
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
        			<msh:ifInRole roles="/Policy/Mis/MedCase/Protocol/Create,/Policy/Mis/MedCase/Stac/Ssl/Protocol/Create">
		        		<a href='entityParentPrepareCreate-smo_visitProtocol.do?id=${param.id}'> Добавить новый дневник специалиста ПРИЕМНОГО ОТДЕЛЕНИЯ</a>
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
           where p.medcase_id=${param.id} and p.dtype='Protocol'"/>
          <msh:table hideTitle="false" disableKeySupport="false" idField="1" name="protocols" action="entityParentView-smo_visitProtocol.do" disabledGoFirst="false" disabledGoLast="false" guid="d0e60067-9aec-4ee0-b20a-4f6b37">
              <msh:tableColumn property="sn" columnName="#"/>
		      <msh:tableColumn columnName="Дата" property="2" guid="0694f6a7-ed40-4ebf-a274-1efd6901cfe4" />
		      <msh:tableColumn columnName="Время" property="3" guid="0694f40-4ebf-a274-1efd6901cfe4" />
		      <msh:tableColumn columnName="Текст" property="4" guid="6682eeef-105f-43a0-be61-30a865f27972" cssClass="preCell"/>
		      <msh:tableColumn columnName="Специалист" property="5" guid="f31b12-3392-4978-b31f-5e54ff2e45bd" />
	     </msh:table>
	     </msh:sectionContent>
        </msh:section>
      </msh:ifInRole>
    </msh:ifFormTypeIsView>

