<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://struts.apache.org/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://www.nuzmsh.ru/tags/msh" prefix="msh" %>
<%@ taglib uri="http://www.ecom-ast.ru/tags/ecom" prefix="ecom" %>
<%@ taglib tagdir="/WEB-INF/tags" prefix="tags" %>
<%@ taglib uri="/WEB-INF/mis.tld" prefix="mis" %>

<tiles:insert page="/WEB-INF/tiles/main${param.short}Layout.jsp" flush="true">

  <tiles:put name="body" type="string">
    <!-- 
    	  - Случай стационарного лечения
    	  -->
    	  
    	      	  <msh:ifFormTypeIsView formName="stac_sslAllInfoForm">
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
    	  
    <msh:form action="/entityParentSaveGoView-stac_sslAllInfo.do" defaultField="dateStart" title="Случай стационарного лечения. ВЫПИСКА">
      <msh:hidden property="id" />
      <msh:hidden property="patient" />
      <msh:hidden property="saveType" />
      <msh:hidden property="guarantee"/>
      <msh:panel>
        <msh:separator colSpan="8" label="Поступление" />
        <msh:row>
          <msh:autoComplete property="orderLpu" label="Кем направлен" />
          <msh:textField property="orderNumber" label="№ напр" />
          <msh:textField property="orderDate" label="Дата" />
        </msh:row>
        <msh:row>
          <msh:autoComplete property="owner" label="Кем доставлен" />
          <msh:textField property="externalId" label="Код " />
          <msh:textField property="supplyOrderNumber" label="Номер наряда" />
        </msh:row>
        <msh:row>
          <msh:autoComplete property="emergency" label="Экстренность" />
          <msh:autoComplete property="intoxication" label="Состояние опьянения" vocName="vocIntoxication" labelColSpan="2" fieldColSpan="2" horizontalFill="true" />
        </msh:row>
        <msh:row>
          <msh:autoComplete property="preAdmissionTime" label="Доставлен от начала заболевания" vocName="vocPreAdmissionTime" fieldColSpan="3" horizontalFill="true" labelColSpan="2" />
        </msh:row>
        <msh:row>
          <msh:autoComplete property="preAdmissionDefect" label="Дефекты догоспитального этапа" vocName="vocPreAdmissionDefect" fieldColSpan="3" horizontalFill="true" labelColSpan="2" />
        </msh:row>
        <msh:row>
          <msh:autoComplete property="trauma" label="Травма" horizontalFill="true" fieldColSpan="2" vocName="vocTraumaType" />
          <msh:autoComplete viewAction="entityView-mis_worker.do" vocName="vocWorker" property="startWorker" label="Кто начал" fieldColSpan="2" horizontalFill="true" />
        </msh:row>
        <msh:row>
          <msh:textField property="dateStart" label="Дата поступления" labelColSpan="2" />
          <msh:textField property="entranceTime" label="Время" />
        </msh:row>
        <msh:row>
          <msh:autoComplete viewAction="entityView-mis_Worker" vocName="vocWorker" property="owner" label="Владелец" fieldColSpan="3" horizontalFill="true" />
        </msh:row>
        <msh:separator colSpan="8" label="Выписка" />
        <msh:row>
          <msh:textField property="dateFinish" label="Дата выписки (смерти)" />
          <msh:textField property="dischargeTime" label="время" />
        </msh:row>
        <msh:row>
          <msh:autoComplete vocName="vocServiceStream" property="serviceStream" label="Поток обслуживания" fieldColSpan="2" horizontalFill="true" />
        </msh:row>
        <msh:row>
          <msh:checkBox property="ambulanceTreatment" label="Амбулаторное лечение" />
          <msh:checkBox property="rareCase" label="Редкий случай" />
        </msh:row>
        <msh:row>
          <msh:autoComplete showId="false" hideLabel="false" property="result" viewOnlyField="false" label="Результат" horizontalFill="true" fieldColSpan="2" vocName="vocHospitalizationResult" />
          <msh:autoComplete vocName="vocHospitalizationOutcome" property="outcome" label="Исход госпитализации" horizontalFill="true" fieldColSpan="2" />
        </msh:row>
        <mis:ifPatientIsWoman classByObject="Patient" idObject="stac_sslAllInfoForm.patient" roles="/Policy/Mis/Pregnancy/History/View">
        <msh:separator label="Беременность" colSpan="9"/>
        <msh:row>
        	<msh:autoComplete viewAction="entityParentView-preg_pregnancy.do" property="pregnancy" label="Беременность" fieldColSpan="3" parentId="stac_sslAdmissionForm.patient" vocName="pregnancyByPatient" horizontalFill="true"/>
        </msh:row>

        </mis:ifPatientIsWoman>
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
        <msh:submitCancelButtonsRow colSpan="4" labelSave="Сохранить изменения" labelCreating="Создание" labelCreate="Создать новый случай" labelSaving="Сохранение данных" />
      </msh:panel>
    </msh:form>
    <tags:stac_infoBySls form="stac_sslAllInfoForm"/>
  </tiles:put>
  <tiles:put name="title" type="string">
    <ecom:titleTrail mainMenu="Patient" beginForm="stac_sslCloseForm" />
  </tiles:put>
  <tiles:put name="side" type="string">
    	  	<tags:stac_hospitalMenu currentAction="stac_sslAllInfo"/>  
  </tiles:put>
</tiles:insert>

