<%@ tag pageEncoding="utf8" %>
<%@ taglib uri="http://www.nuzmsh.ru/tags/msh" prefix="msh" %>
<%@ taglib tagdir="/WEB-INF/tags" prefix="tags" %>
<%@ taglib uri="/WEB-INF/mis.tld" prefix="mis" %>
<%@ taglib uri="http://www.ecom-ast.ru/tags/ecom" prefix="ecom" %>

<%@  attribute name="form" required="true" description="" %>

    <msh:ifFormTypeIsView formName="${form}" guid="48eb9700-d07d-4115-a476-a5a439731d3e">
      <msh:ifInRole roles="/Policy/Mis/MedPolicy/View">
        <ecom:webQuery nativeSql="select mp.id, case when (mp.DTYPE='MedPolicyOmc') then 'ОМС' when (mp.DTYPE='MedPolicyDmcForeign') then 'ДМС иногороднего' when (mp.DTYPE='MedPolicyDmc') then 'ДМС' else 'ОМС иногороднего' end, ri.name as riname,mp.polnumber,mp.series,mp.actualDateFrom,mp.actualDateTo from MedCase_MedPolicy as mc left join MedPolicy as mp on mp.id=mc.policies_id left join reg_ic as ri on ri.id=mp.company_id where mc.MedCase_id=${param.id}" name="policies" guid="12ed9815-1a16-441a-8d89-36bb94761f9b" />
          <msh:section guid="746b6d8a-b92f-4bd0-9899-d32855f3aa95">
          <msh:sectionTitle>
          <msh:link action="stac_policiesEdit.do?id=${param.id}"  >Прикрепленные полисы к случаю</msh:link>
          </msh:sectionTitle>
          <msh:sectionContent>
            <msh:table name="policies" hideTitle="false" action="entitySubclassView-mis_medPolicy.do" idField="1" guid="86ef8e69-6d58-4e49-961a-f2f463e02f80">
              <msh:tableColumn property="sn" columnName="#" />
              <msh:tableColumn property="2" columnName="Тип" />
              <msh:tableColumn property="3" columnName="Страховая компания" />
              <msh:tableColumn property="4" columnName="Номер"/>
              <msh:tableColumn property="5" columnName="Серия"/>
              <msh:tableColumn property="6" columnName="Дата начала" />
              <msh:tableColumn property="7" columnName="Дата окончания" />
            </msh:table>
          </msh:sectionContent>
          </msh:section>
      </msh:ifInRole>
      <msh:ifInRole roles="/Policy/Mis/MedCase/Stac/Ssl/Slo/View" guid="0cbf528e-b3d1-48bb-9d50-a878dbbd0c4e">
        <msh:section title="Движения по отделениям. <a href='entityParentPrepareCreate-stac_slo.do?id=${param.id }'>Добавить новый случай лечения в отделении</a>" guid="313bfb94-a58e-4041-be05-dacad7710873">
          <ecom:webQuery name="allSLOs" nativeSql="select MedCase.id, MedCase.dateStart&#xA;        , MedCase.department_id||' '||postDep.Name&#xA;        , MedCase.transferDate &#xA;        , MedCase.transferDepartment_id||' '||tranDep.Name&#xA;        , MedCase.dateFinish&#xA;&#x9;, Patient.lastname || ' ' ||  Patient.firstname || ' ' || Patient.middlename as startWorker&#xA;&#x9;, op.lastname || ' ' ||  op.firstname || ' ' || op.middlename as ownerWorker&#xA;         ,ifnull(MedCase.dateFinish, ifnull(MedCase.transferDate,($$GetBedDays^ZExpCheck('000' || (case when BedFund.addCaseDuration=1 then 'J' else 'A' end),MedCase.dateStart,CURRENT_DATE)) || '(на текущий момент)' ,($$GetBedDays^ZExpCheck('000' || (case when BedFund.addCaseDuration=1 then 'J' else 'A' end),MedCase.dateStart,MedCase.transferDate)) ||' (перевод)') ,($$GetBedDays^ZExpCheck('000' || (case when BedFund.addCaseDuration=1 then 'J' else 'A' end),MedCase.dateStart,MedCase.dateFinish)) ||' (выписан)')           , MedCase.entranceTime,MedCase.transferTime,MedCase.dischargeTime            
          ,VocServiceStream.name
          from MedCase&#xA;  
          left outer join Worker     on MedCase.startWorker_id = Worker.id&#xA; 
           left outer join Patient    on Worker.person_id       = Patient.id&#xA;  
           left outer join Worker ow  on MedCase.owner_id       = ow.id &#xA; 
            left outer join Patient op on ow.person_id           = op.id&#xA; 
             left outer join MisLpu tranDep on MedCase.transferDepartment_id           = tranDep.id&#xA; 
              left outer join MisLpu postDep on MedCase.department_id           = postDep.id&#xA;
               left join BedFund on BedFund.id=MedCase.bedFund_id 
          left join VocServiceStream on VocServiceStream.id=MedCase.serviceStream_id
               left join VocBedSubType on VocBedSubType.id = BedFund.bedSubType_id where MedCase.parent_id=${param.id} &#xA;   and MedCase.DTYPE='DepartmentMedCase'" guid="624771b1-fdf1-449e-b49e-5fcc34e03fb5" />
          <msh:table name="allSLOs" action="entityParentView-stac_slo.do" idField="1" guid="a99e7bed-cb69-49df-bbe6-ac9718cd22e0">
            <msh:tableNotEmpty guid="a6284e48-9209-412d-8436-c1e8e37eb8aa">
              <tr>
                <th colspan="1" />
                <th colspan="4" class="rightBold">Поступление</th>
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
          <ecom:webQuery name="allSurgOper" nativeSql="select so.id,so.operationDate,so.operationTime,vo.name as voname
          , case when parent.DTYPE='HospitalMedCase' then 'Приемное отделение' when parent.DTYPE='DepartmentMedCase' then d.name else '' end 
          , vwf.name||' '||wp.lastname||' '||wp.firstname||' '||wp.middlename
          from SurgicalOperation as so 
          left join VocOperation vo on vo.id=so.operation_id 
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
	    	<msh:table name="allSurgOper" action="entityParentView-stac_surOperation.do" idField="1">
	    		<msh:tableColumn columnName="#" property="sn"/>
	    		<msh:tableColumn columnName="Отделение" property="5"/>
	    		<msh:tableColumn columnName="Дата" property="2"/>
	    		<msh:tableColumn columnName="Время" property="3"/>
	    		<msh:tableColumn columnName="Операция" property="4"/>
	    		<msh:tableColumn columnName="Хирург" property="6"/>
	    	</msh:table>
	    </msh:section>
    </msh:tableNotEmpty>
      
        <%-- 
        <msh:section title="Хирургические операции. <a href='entityParentPrepareCreate-stac_surOperation.do?id=${param.id }'>Добавить новую хирургическую операцию</a>" guid="14258dfa-85a8-466d-98d9-b384a96ecc91">
        
          <ecom:parentEntityListAll formName="stac_surOperationForm" attribute="surgicalOperations" guid="275c530f-4793-410e-aa4e-f4097633d319" />
          <msh:table idField="id" name="surgicalOperations" action="entityParentView-stac_surOperation.do" guid="8a451fa2-b1c0-4ce0-a12b-ecd5c7739907">
            <msh:tableColumn columnName="Дата" property="operationDate" guid="1c6ad238-4b69-46f1-98be-e076cca3b323" />
            <msh:tableColumn columnName="Время" property="operationTime" guid="e0ca512f-c83d-47aa-8e3b-5e77bcad0a30" />
	      <msh:tableColumn columnName="Хирург" property="surgeonInfo" guid="805b92e3-4d45-4918-9b8a-3a629591e030" />
	      <msh:tableColumn columnName="Ассистенты" property="surgeonsInfo" guid="8045-4918-9b8a-3a629591e030" />
            <msh:tableColumn columnName="Операция" property="operationInfo" guid="037fe2d5-da32-4e97-a5da-631277c96d3a" />
            <msh:tableColumn columnName="Основная" property="base" guid="9f03b67b-d1a9-4566-b2d1-327fd2ddcb65" />
            <msh:tableColumn columnName="Анестезия" property="anesthesiaInfo" guid="66a7929d-af80-4862-b449-89004b56612b" />
          </msh:table>
        </msh:section>
        --%>
      </msh:ifInRole>
      <msh:ifInRole roles="/Policy/Mis/MedCase/Diagnosis/View" guid="4cc62e2b-bf2a-4839-8bab-481886e8e721">
         <ecom:webQuery name="diags" nativeSql="select Diagnosis.id, VocDiagnosisRegistrationType.name as vdrtname, Diagnosis.establishDate, &#xA;  ' '||Diagnosis.name,VocIdc10.code, VocPriorityDiagnosis.name as vpdname&#xA;  from Diagnosis&#xA;  left outer join VocDiagnosisRegistrationType on Diagnosis.registrationType_id = VocDiagnosisRegistrationType.id left outer join VocPriorityDiagnosis on Diagnosis.priority_id = VocPriorityDiagnosis.id&#xA;  left outer join VocIdc10     on Diagnosis.idc10_id = VocIdc10.id&#xA; where Diagnosis.medCase_id=${param.id} &#xA;" guid="b72ef17b-92bc-4d99-9d2f-a129e9b2cc3f" />
        <msh:section title="Диагнозы. <a href='entityParentPrepareCreate-stac_diagnosis.do?id=${param.id }'>Добавить новый диагноз</a>" guid="a2bc2525-333d-4548-a7bf-0065569f87ba">
          <msh:table name="diags" action="entityParentView-stac_diagnosis.do" idField="1" guid="7312ce0c-0c61-482d-9079-71b2a0f29484">
              <msh:tableColumn property="sn" columnName="#" />
            <msh:tableColumn columnName="Тип регистрации" property="2" guid="e2fc05a6-e089-4c90-8fb2-a8377e6f8967" />
            <msh:tableColumn columnName="Приоритет" property="6" guid="e2fc05a6-e089-4c90-8fb2-a8377e6f8967" />
            <msh:tableColumn columnName="Дата" property="3" guid="718ec416-3543-4f8d-89cd-b24aa8177377" />
            <msh:tableColumn columnName="Наименование" property="4" guid="2a519337-384d-4695-9d19-72dd7e02936c" />
            <msh:tableColumn columnName="Код МКБ" property="5" guid="150732a8-0a5a-4ac3-bbbb-9ff669be37a6" />
          </msh:table>
        </msh:section>
      </msh:ifInRole>
      <msh:ifInRole roles="/Policy/Mis/MedCase/Protocol/View" guid="141a017a-569a-4fa2-9a0e-3788f69da">
        <msh:section>
        <msh:sectionTitle>
        	Дневник специалиста. 
        		<msh:ifNotInRole roles="/Policy/Mis/MedCase/Protocol/CreateOnlyInMedService">
        			<msh:ifInRole roles="/Policy/Mis/MedCase/Protocol/Create">
		        		<a href='entityParentPrepareCreate-smo_visitProtocol.do?id=${param.id }'> Добавить новый дневник специалиста</a>
        			</msh:ifInRole>
        		</msh:ifNotInRole>
        </msh:sectionTitle>
        <msh:sectionContent>
          <ecom:webQuery name="protocols" nativeSql="select p.id,p.dateRegistration,p.timeRegistration,p.record,vwf.name||' '||wp.lastname||' '||wp.firstname||' '||wp.middlename from diary p left join workfunction wf on wf.id=p.specialist_id left join worker w on w.id=wf.worker_id left join patient wp on wp.id=w.person_id left join vocworkfunction vwf on vwf.id=wf.workfunction_id where p.medcase_id=${param.id} and p.dtype='Protocol'"/>
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

