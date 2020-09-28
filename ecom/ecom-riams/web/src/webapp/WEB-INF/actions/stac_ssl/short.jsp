<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://struts.apache.org/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://www.nuzmsh.ru/tags/msh" prefix="msh" %>
<%@ taglib uri="http://www.ecom-ast.ru/tags/ecom" prefix="ecom" %>
<%@ taglib uri="/WEB-INF/mis.tld" prefix="mis" %>
<%@ taglib tagdir="/WEB-INF/tags" prefix="tags" %>

<tiles:insert page="/WEB-INF/tiles/mainShortLayout.jsp" flush="true">

  <tiles:put name="body" type="string">
    <msh:form action="/entityParentSaveGoView-stac_ssl.do" defaultField="dateStart" title="<a href='entityParentView-stac_ssl.do?id=${param.id}'>Случай стационарного лечения. Общая информация</a>" editRoles="/Policy/Mis/MedCase/Stac/Ssl/Edit" createRoles="/Police/Mis/MedCase/Stac/Ssl/Create">
      <msh:hidden property="id" />
      <msh:hidden property="saveType" />

      <msh:panel>
        <msh:separator label="<a href='entityParentEdit-stac_sslAdmission.do?id=${param.id}'>Приемное отделение</a>" colSpan="8" />
        <msh:row>
          <msh:textField property="statCardNumber" label="Номер стат.карты" horizontalFill="true" viewOnlyField="true" />
        </msh:row>
        <msh:row>
          <msh:textField property="dateStart" label="Дата поступления" />
          <msh:textField property="entranceTime" label="время" fieldColSpan="2" />
        </msh:row>
        <msh:row>
          <msh:autoComplete property="orderLpu" label="Кем направлен" vocName="mainLpu" horizontalFill="true" />
          <msh:textField property="orderDate" label="Дата" />
        </msh:row>
        <msh:row>
          <msh:textField property="orderDiagnos" label="ДИАГНОЗ направившего учреждения" fieldColSpan="6" horizontalFill="true" />
        </msh:row>
        <msh:row>
          <msh:autoComplete showId="false" vocName="vocOmcFrm" hideLabel="false" property="orderType" viewOnlyField="false" horizontalFill="true" />
          <msh:textField property="supplyOrderNumber" label="Номер наряда" />
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
          <msh:textField property="attendant" label="Сопровождающее лицо" fieldColSpan="5" horizontalFill="true" />
        </msh:row>
        <msh:row>
          <msh:autoComplete property="trauma" label="Травма" vocName="vocTraumaType" fieldColSpan="5" horizontalFill="true" />
        </msh:row>
        <msh:row />
        <msh:row>
          <msh:checkBox property="noActuality" label="Недействительность" />
        <msh:label property="username" label="Оператор" />
        </msh:row>
        <msh:separator colSpan="8" label="<a href='entityParentEdit-stac_sslDischarge.do?id=${param.id}'>Выписка</a>" />
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
        </msh:panel>
    </msh:form>
    <msh:ifFormTypeIsView formName="stac_sslForm">
      <msh:ifInRole roles="/Policy/Mis/MedPolicy/View">
        <ecom:webQuery nativeSql="select mp.id, case when (mp.DTYPE='MedPolicyOmc') then 'ОМС' when (mp.DTYPE='MedPolicyDmcForeign') then 'ДМС иногороднего' when (mp.DTYPE='MedPolicyDmc') then 'ДМС' else 'ОМС иногороднего' end, ri.name as riname,mp.polnumber,mp.series,mp.actualDateFrom,mp.actualDateTo from MedCase_MedPolicy as mc left join MedPolicy as mp on mp.id=mc.policies_id left join reg_ic as ri on ri.id=mp.company_id where mc.MedCase_id=${param.id}" name="policies" />
          <msh:section>
          <msh:sectionTitle>
          <msh:link action="stac_policiesEdit.do?id=${param.id}"  >Прикрепленные полисы к случаю</msh:link>
          </msh:sectionTitle>
          <msh:sectionContent>
            <msh:table name="policies" hideTitle="false" action="entitySubclassView-mis_medPolicy.do" idField="1">
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
      <msh:ifInRole roles="/Policy/Mis/MedCase/Stac/Ssl/Slo/View">
        <msh:section title="Движения по отделениям. <a href='entityParentPrepareCreate-stac_slo.do?id=${param.id }'>Добавить новый случай лечения в отделении</a>">
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
               left join VocBedSubType on VocBedSubType.id = BedFund.bedSubType_id where MedCase.parent_id=${param.id} &#xA;   and MedCase.DTYPE='DepartmentMedCase'" />
          

          <msh:table name="allSLOs" 
          viewUrl="entityShortView-stac_slo.do"
          action="entityParentView-stac_slo.do" idField="1">
            <msh:tableNotEmpty>
              <tr>
                <th colspan="1" />
                <th colspan="4" class="rightBold">Поступление</th>
                <th colspan="3" class="rightBold">Перевод</th>
                <th colspan="2" class="rightBold">Выписка</th>
                <th colspan="1" />
              </tr>
            </msh:tableNotEmpty>
              <msh:tableColumn property="sn" columnName="#" />
            <msh:tableColumn columnName="Дата" property="2" />
            <msh:tableColumn columnName="Время" property="10" />
            <msh:tableColumn columnName="Отделение" property="3" cssClass="rightBold" />
            <msh:tableColumn columnName="Поток" property="13" cssClass="rightBold" />
            <msh:tableColumn columnName="Дата" property="4" />
            <msh:tableColumn columnName="Время" property="11" />
            <msh:tableColumn columnName="Отделение" property="5" cssClass="rightBold" />
            <msh:tableColumn columnName="Дата" property="6" />
            <msh:tableColumn columnName="Время" property="12" />
            <msh:tableColumn columnName="Койко-дни" property="9" />
          </msh:table>
        </msh:section>
      </msh:ifInRole>
      <msh:ifInRole roles="/Policy/Mis/MedCase/Stac/Ssl/SurOper/View">
          <ecom:webQuery name="allSurgOper" nativeSql="select so.id
          ,so.operationDate as sooperationDate,so.operationTime as soperationTime,vo.name as voname
          , case when parent.DTYPE='HospitalMedCase' then 'Приемное отделение' when parent.DTYPE='DepartmentMedCase' then d.name else '' end as whoIs  
          , vwf.name||' '||wp.lastname||' '||wp.firstname||' '||wp.middlename as doctor
          from SurgicalOperation as so 
          left join MedService vo on vo.id=so.medService_id 
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
    <ecom:titleTrail mainMenu="Patient" beginForm="stac_sslForm" />
  </tiles:put>
</tiles:insert>