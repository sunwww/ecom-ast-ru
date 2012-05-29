<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://struts.apache.org/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://www.nuzmsh.ru/tags/msh" prefix="msh" %>
<%@ taglib uri="http://www.ecom-ast.ru/tags/ecom" prefix="ecom" %>
<%@ taglib uri="/WEB-INF/mis.tld" prefix="mis" %>
<%@ taglib tagdir="/WEB-INF/tags" prefix="tags" %>

<tiles:insert page="/WEB-INF/tiles/mainShortLayout.jsp" flush="true">

  <tiles:put name="body" type="string">
    <msh:form action="/entityParentSaveGoView-stac_ssl.do" defaultField="dateStart" guid="b37b5d4a-9687-45bf-b42c-613fd9b1739d" title="<a href='entityParentView-stac_ssl.do?id=${param.id}'>Случай стационарного лечения. Общая информация</a>" editRoles="/Policy/Mis/MedCase/Stac/Ssl/Edit" createRoles="/Police/Mis/MedCase/Stac/Ssl/Create">
      <msh:hidden property="id" guid="c5a9c42c-d593-4fae-a685-44586a81938b" />
      <msh:hidden property="saveType" guid="7cb7bcb7-2cb3-4e65-92ce-2a4a2cec809f" />

      <msh:panel guid="6e8d827a-d32c-4a05-b4b0-5ff7eed6eedc">
        <msh:separator label="<a href='entityParentEdit-stac_sslAdmission.do?id=${param.id}'>Приемное отделение</a>" colSpan="8" />
        <msh:row guid="25f2a536-4fb6-4413-89db-a478145e097e">
          <msh:textField property="statCardNumber" label="Номер стат.карты" guid="e5f3d524-cca8-4a5a-a408-196ab6b79627" horizontalFill="true" viewOnlyField="true" />
        </msh:row>
        <msh:row guid="0e91a1ca-c366-435c-8f2c-274d23d87fd3">
          <msh:textField property="dateStart" label="Дата поступления" guid="e3fd4642-a532-4510-a528-c6e766328d61" />
          <msh:textField property="entranceTime" label="время" guid="f94ff57c-bbf9-44f8-9e8d-f21927edbcff" fieldColSpan="2" />
        </msh:row>
        <msh:row guid="a53d1f37-afe8-4779-9e63-0b2684e14828">
          <msh:autoComplete property="orderLpu" label="Кем направлен" vocName="mainLpu" guid="c44b474f-6dba-4ba8-9af7-56a0dca363ad" horizontalFill="true" />
          <msh:textField property="orderDate" label="Дата" guid="3e74c0ff-d603-4923-b207-b4ce0d665841" />
        </msh:row>
        <msh:row guid="8036eaf5-7144-46a4-9015-e4f198230a2c">
          <msh:textField property="orderDiagnos" label="ДИАГНОЗ направившего учреждения" fieldColSpan="6" horizontalFill="true" guid="7d5f0ad3-3f43-42b7-8c46-f2fcceead05c" />
        </msh:row>
        <msh:row guid="e811bd41-db0a-4275-b786-75f958759453">
          <msh:autoComplete showId="false" vocName="vocOmcFrm" hideLabel="false" property="orderType" viewOnlyField="false" guid="ff2a0045-c4fc-4efd-9bcd-f84951ac74a2" horizontalFill="true" />
          <msh:textField property="supplyOrderNumber" label="Номер наряда" guid="63f0777e-6c0f-4ea9-b389-8a620004a456" />
        </msh:row>
        <msh:row guid="cff159af-0e93-42de-b29d-b68c05e371d0">
          <msh:autoComplete property="preAdmissionDefect" label="Дефекты догоспитального этапа" vocName="vocPreAdmissionDefect" guid="7d35c85f-1f39-48c1-b48c-0b8fd4da28e3" fieldColSpan="3" horizontalFill="true" />
        </msh:row>
        <msh:row guid="5d9db3cf-010f-463e-a2e6-3bbec49fa646">
          <msh:autoComplete vocName="vocServiceStream" property="serviceStream" label="Поток обслуживания" fieldColSpan="1" horizontalFill="true" guid="10h64-23b2-42c0-ba47-65p8g16c" />
          <msh:checkBox property="ambulanceTreatment" label="Амбулаторное лечение" guid="8aa14ab3-7940-4b9d-aa14-2c3ce9fb4e7b" />
        </msh:row>
        <msh:row guid="194b2a92-9b7f-485e-b0f4-93092bf1e1c4">
          <msh:textField property="entranceDiagnos" label="ДИАГНОЗ приемного отделения" guid="cf405118-1d6c-4bde-81ee-57eb04dbb879" fieldColSpan="6" horizontalFill="true" />
        </msh:row>
        <msh:row guid="22259fc2-76df-4244-88fa-4a610e76ce31">
          <msh:autoComplete property="entranceMkb" label="Код МКБ приемного отделения" horizontalFill="true" guid="064ba98b-1b09-454a-8c1b-348aed0b0b01" vocName="vocIdc10" fieldColSpan="6" />
        </msh:row>
        <msh:row guid="16d11e99-4017-4385-87c1-b2fe485895e2">
          <msh:checkBox property="emergency" label="Экстренное поступление" guid="68f70746-42df-4d8f-a575-74bf4b4cc1ae"  />
          <msh:autoComplete property="preAdmissionTime" label="Часы заболевания" guid="ddc10e76-a708-4efd-8ef3-f18ee913984f" horizontalFill="true" vocName="vocPreAdmissionTime" fieldColSpan="2" />
        </msh:row>
        <msh:row guid="b88b81ab-1b89-4747-ac27-a865e920eb33">
          <msh:autoComplete property="department" label="Отделение" guid="bf59f5d5-2843-4abc-bf23-cbbbda89a67e" vocName="vocLpuOtd" fieldColSpan="5" horizontalFill="true" parentAutocomplete="lpu" />
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
        <msh:row guid="bg81ab-1b89-4747-ac27-ag0eb33">
          <msh:autoComplete property="bedType" label="Профиль коек" guid="b57bb12a-0622-49bf-9905-11728fd8ecdb" horizontalFill="true" vocName="vocBedType" fieldColSpan="6" />
        </msh:row>
        <msh:row guid="8gaf5-7144-46a4-9015-eg230a2c">
          <msh:textField property="attendant" label="Сопровождающее лицо" guid="7fvd3-3f43-42b7-8c46-ffd05c" fieldColSpan="5" horizontalFill="true" />
        </msh:row>
        <msh:row guid="e101a36c-d874-4d43-9cfe-fff88ff64ffa">
          <msh:autoComplete property="trauma" label="Травма" guid="ee46d9b9-2961-42be-9a05-c015f4caff23" vocName="vocTraumaType" fieldColSpan="5" horizontalFill="true" />
        </msh:row>
        <msh:row guid="f0f8aa80-2ed5-460e-88b7-a86906e249a3" />
        <msh:row guid="20c4aa51-33db-4141-9de9-4c5060ee9049">
          <msh:checkBox property="noActuality" label="Недействительность" guid="6299a6be-428f-4a09-9db5-e4c60154b605" />
        <msh:label property="username" label="Оператор" guid="2258d5ca-cde5-46e9-a1cc-3ffc278353fe" />
        </msh:row>
        <msh:separator colSpan="8" label="<a href='entityParentEdit-stac_sslDischarge.do?id=${param.id}'>Выписка</a>" guid="597ac93d-a5d0-4b08-a6b1-79efee0f497a" />
        <msh:row guid="ef812a6e-c7ab-465f-8a63-25ae169ed2b2">
          <msh:autoComplete label="Результат госпитализации" property="result" horizontalFill="true" guid="63d091a8-90b9-479f-8aef-0064a789fade" vocName="vocHospitalizationResult" />
          <msh:autoComplete label="Исход" property="outcome" guid="117c2e24-87b0-4fb0-842e-5411b62b1d3e" vocName="vocHospitalizationOutcome" horizontalFill="true" />
        </msh:row>
        <msh:row guid="3009274e-f253-4805-baeb-0ab4ac5ffca8">
          <msh:textField label="Дата выписки" property="dateFinish" guid="430fa31a-5126-4628-8617-4ae67b4829a3" />
          <msh:textField label="Время выписки" property="dischargeTime" guid="1bee7682-f5a4-40f8-8e10-b6b6500ec0f4" />
        </msh:row>
        <msh:row guid="b2c54e43-3ae2-4716-af12-e32a7ac4a115">
          <msh:autoComplete label="Перевод в др ЛПУ" property="moveToAnotherLPU" guid="8c90d4e3-6351-405e-a5b0-0ea5cf61db87" fieldColSpan="5" horizontalFill="true" vocName="lpu" />
        </msh:row>
        <msh:row guid="efdbb0a9-eddc-4841-9985-5edf61220623">
          <msh:checkBox label="Провизорность" property="provisional" guid="d8588d59-3adb-4485-af94-cadecb04f82b" />
          <msh:checkBox property="rareCase" label="Редкий случай" guid="6299a6be-428f-4a095" />
        </msh:row>
        <msh:row>
        	<msh:separator label="Дополнительно" colSpan="4"/>
        </msh:row>
                <msh:row>
        	<msh:label property="createDate" label="Дата создания"/>
          <msh:label property="username" label="Оператор" guid="2258d5ca-cde5-46e9-a1cc-3ffc278353fe" />
        </msh:row>
        <msh:row>
        	<msh:label property="editDate" label="Дата редак."/>
          	<msh:label property="editUsername" label="Оператор" guid="2258d5ca-cde5-46e9-a1cc-3ffc278353fe" />
        </msh:row>
        </msh:panel>
    </msh:form>
    <msh:ifFormTypeIsView formName="stac_sslForm">
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
         <ecom:webQuery  name="allSLOs" nativeSql="select MedCase.id
          , MedCase.dateStart as mdateStart 
          , MedCase.department_id||' '||postDep.Name as mdepartment      
          , MedCase.transferDate as mtransferDate
          , MedCase.transferDepartment_id||' '||tranDep.Name as mtransferDepartment        
          , MedCase.dateFinish as mdateFinish
          , Patient.lastname || ' ' ||  Patient.firstname || ' ' || Patient.middlename as startWorker
          , op.lastname || ' ' ||  op.firstname || ' ' || op.middlename as ownerWorker       
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
          <ecom:webQuery name="allSurgOper" nativeSql="select so.id
          ,so.operationDate as sooperationDate,so.operationTime as soperationTime,vo.name as voname
          , case when parent.DTYPE='HospitalMedCase' then 'Приемное отделение' when parent.DTYPE='DepartmentMedCase' then d.name else '' end as whoIs  
          , vwf.name||' '||wp.lastname||' '||wp.firstname||' '||wp.middlename as doctor
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
      
       
      </msh:ifInRole>
      
    </msh:ifFormTypeIsView>
  </tiles:put>
  <tiles:put name="title" type="string">
    <ecom:titleTrail mainMenu="Patient" beginForm="stac_sslForm" guid="638ddd30-b48e-4058-b3ad-866c0c70ee1f" />
  </tiles:put>
</tiles:insert>