<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://struts.apache.org/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://www.nuzmsh.ru/tags/msh" prefix="msh" %>
<%@ taglib uri="http://www.ecom-ast.ru/tags/ecom" prefix="ecom" %>

<tiles:insert page="/WEB-INF/tiles/mainLayout.jsp" flush="true">

  <tiles:put name="side" type="string">
    <msh:ifFormTypeIsView formName="psych_careCardForm" guid="ee0e1e09-4d44-485b-b8b8-5b07490cb739">
      <msh:sideMenu title="Карта обратившего" guid="85a36b27-06d7-46ee-9a8a-d553cf8b898e">
        <msh:sideLink key="ALT+2" params="id" action="/entityParentEdit-psych_careCard" name="Изменить" roles="/Policy/Mis/Psychiatry/CareCard/Edit" guid="96374d25-f0ce-4900-ac0b-8a07022a076e" />
        <msh:sideLink key="ALT+DEL" confirm="Удалить?" params="id" action="/entityParentDeleteGoParentView-psych_careCard" name="Удалить" roles="/Policy/Mis/Psychiatry/CareCard/Delete" guid="fa18866d-ff06-4c2a-ae5f-b834501b45c3" />
      </msh:sideMenu>
      <msh:sideMenu title="Добавить" guid="46a542d6-da04-4268-bb9a-5ebddf3baea2">
        <msh:sideLink action="/entityParentPrepareCreate-psych_compulsoryTreatment" name="Принудительное лечение" params="id" roles="/Policy/Mis/Psychiatry/CareCard/CompulsoryTreatment/Create" title="Принудительное лечение" guid="9fafb360-d7ab-4d48-be63-22cc1f75115b" />
        <msh:sideLink action="/entityParentPrepareCreate-psych_examination" name="Судебно-психиатрическую экспертизу" params="id" roles="/Policy/Mis/Psychiatry/CareCard/PsychiatricExamination/Create" title="Судебно-психиатрическую экспертизу" guid="c29f5244-63e7-4a89-b916-221b6d70806c" />
        <msh:sideLink action="/entityParentPrepareCreate-psych_healthState" name="Состояние здоровья" params="id" roles="/Policy/Mis/Psychiatry/CareCard/HealthState/Create" title="Состояние здоровья" guid="da16deaf-9863-49c6-b7a8-fd8908e6dfca" />
        <msh:sideLink action="/entityParentPrepareCreate-psych_lpuAreaPsychCareCard" name="Участок" params="id" roles="/Policy/Mis/Psychiatry/CareCard/LpuAreaPsychCareCard/Create" title="Участок" guid="d17e5b22-15d4-4695-ad93-5c6b30f8c996" />
        <msh:sideLink action="/entityParentPrepareCreate-psych_observation" name="Психиатрическое наблюдение" params="id" roles="/Policy/Mis/Psychiatry/CareCard/PsychiaticObservation/Create" title="Психиатрическое наблюдение" guid="3f56bc78-9f74-4f51-b714-6ced0d6c8689" />
        <msh:sideLink action="/entityParentPrepareCreate-psych_publicDangerousEffect" name="Общественно-опасные события" params="id" roles="/Policy/Mis/Psychiatry/CareCard/PublicDangerousEffect/Create" title="Общественно-опасные события" guid="a3b15385-fd4e-40e5-8c37-4fc8debab13e" />
        <msh:sideLink action="/entityParentPrepareCreate-psych_suicide" name="Суицид" params="id" roles="/Policy/Mis/Psychiatry/CareCard/Suicide/Create" title="Суицид" guid="d46b4e0c-a361-4826-9801-1bca61cda0ac" />
      </msh:sideMenu>
      <%-- 
      <msh:sideMenu title="Перейти" guid="46a542d6-da04-4268-bb9a-5ebddf3baea2">
        <msh:sideLink styleId="selected_menu" action="/entityParentView-psych_careCard" name="Карта обратившегося за психиатрической помощью" params="id" roles="/Policy/Mis/Psychiatry/CareCard/View"/>
        <msh:sideLink action="/entityParentList-psych_compulsoryTreatment" name="Список принудительных лечений по пациенту" params="id" roles="/Policy/Mis/Psychiatry/CareCard/CompulsoryTreatment/View"/>
        <msh:sideLink action="/entityParentList-psych_examination" name="Список судебно-психиатрических экспертиз по пациенту" params="id" roles="/Policy/Mis/Psychiatry/CareCard/PsychiatricExamination/View"/>
        <msh:sideLink action="/entityParentList-psych_healthState" name="Список состояний здоровья пациента" params="id" roles="/Policy/Mis/Psychiatry/CareCard/HealthState/View"/>
        <msh:sideLink action="/entityParentList-psych_lpuAreaPsychCareCard" name="Движение по участкам пациента" params="id" roles="/Policy/Mis/Psychiatry/CareCard/LpuAreaPsychCareCard/View"/>
        <msh:sideLink action="/entityParentList-psych_observation" name="Список психиатрических наблюдений по пациенту" params="id" roles="/Policy/Mis/Psychiatry/CareCard/PsychiaticObservation/View"/>
        <msh:sideLink action="/entityParentList-psych_publicDangerousEffect" name="Список общественно-опасных событий по пациенту" params="id" roles="/Policy/Mis/Psychiatry/CareCard/PublicDangerousEffect/View"/>
        <msh:sideLink action="/entityParentList-psych_suicide" name="Список суицидов пациента" params="id" roles="/Policy/Mis/Psychiatry/CareCard/Suicide/View"/>
      </msh:sideMenu>--%>
    </msh:ifFormTypeIsView>
  </tiles:put>
  <tiles:put name="body" type="string">
    <!-- 
    	  - Карта обратившегося за психиатрической помощью
    	  -->
    <msh:form action="/entityParentSaveGoView-psych_careCard.do" defaultField="cardNumber" guid="f45d72b1-cbea-4ed5-bc74-eec49fcc95db">
      <msh:hidden property="id" guid="5e240f39-9ae6-4545-a96b-52ce1d11144d" />
      <msh:hidden property="patient" guid="90c53d1b-df89-4991-96b3-d149f7470e3c" />
      <msh:hidden property="saveType" guid="c91f0298-9911-4f04-833f-9b7436b23306" />
      <msh:panel guid="683d43fa-e645-4910-b9bd-5463c6f0f301">
        <msh:row guid="2de726bc-2350-403d-ae49-c4e75091a2ad">
          <msh:textField property="cardNumber" label="Номер карты" guid="75c0b483-8a7b-49ae-a468-1145a3150545" />
        </msh:row>
        <msh:row guid="9a12d78d-7956-406c-b453-45f4394c6c46">
          <msh:checkBox property="firstTimeDiseased" label="Впервые в жизни заболевший" guid="fc6dda3a-0acb-48de-ae14-dcc5bffbc022" />
          <msh:checkBox property="convictionsBeforeCare" label="Судимость до обращения к психиатру" guid="88e32876-cbd9-40e8-9d05-1caf9ac40188" />
        </msh:row>
        <msh:row guid="4be2b2b2-ba15-4738-beb4-9ec9920b3b3c">
          <msh:textField property="illnessStartDate" label="Дата начала заболевания" guid="cbf2bfdc-af29-4314-8c17-f8a9983a7ccc" />
          <msh:textField property="firstPsychiatricVisitDate" label="Дата 1 обращ. к психиатру" guid="f764c785-7925-4fe9-9fad-4b8f605198a8" />
        </msh:row>
        <msh:row guid="d4304626-940b-45f3-96b8-4651ff84b0ef">
          <msh:textField property="startDate" label="Дата взятия на учет" guid="09b39ad5-b443-4afd-a109-6f80f9685608" />
          <msh:autoComplete property="observationReason" vocName="vocPsychObservationReason" label="Причина наблюдения" guid="50373939-4624-4d83-93ea-19c6719edc58" horizontalFill="true"/>
        </msh:row>
        <msh:row guid="f8f2a7b1-04ae-4d12-a319-a981332a77c7">
          <msh:textField property="lastInvoluntaryExamDate" label="Дата посл. недоб. освид-ния" guid="c48fbab5-0dec-48e3-895e-018cada5eb24" />
          <msh:textField property="finishDate" label="Дата окончания учета" guid="f85747f9-6784-4e61-8886-c568b9b1cac5" />
        </msh:row>
        <msh:row guid="b7919f7c-5eac-4415-958a-d925403f7d72">
          <msh:autoComplete property="strikeOffReason" vocName="vocPsychStrikeOffReason" label="Причина прекр. набл." guid="150d6828-8187-4372-b71d-fe725015a8c9" fieldColSpan="3" horizontalFill="true"/>
        </msh:row>
        <msh:row>
          <msh:textField property="strikeOffOtherReasonNotes" label="Другая причина" guid="d9094664-0efc-40e6-b78f-f3ec1b3b95c9"  fieldColSpan="3" horizontalFill="true"/>
        </msh:row>
        <msh:row>
          <msh:textField property="deathDate" label="Дата смерти" guid="d9930eca-3a2f-424b-bcc0-5a5135134ead" />
          <msh:autoComplete property="deathReason" label="Причина смерти" vocName="vocPsychDeathReason" guid="726787a3-c556-484c-b451-e48777c60d3c" horizontalFill="true"/>
        </msh:row>
        <msh:row guid="5fbeb0ef-1d87-407f-9c0c-a73ffbefca51">
          <msh:textArea property="notes" label="Примечание" fieldColSpan="4" guid="4f7a1064-703f-4bd2-b6d4-e1fa029e8adc" />
        </msh:row>
        <msh:submitCancelButtonsRow colSpan="3" guid="b2bee8d9-b811-4475-bd13-16b678d5a2dd" />
      </msh:panel>
    </msh:form>
    <msh:ifFormTypeIsView formName="psych_careCardForm" guid="bfc2cb34-7f8a-4185-b6ee-d50199077dce">
    <msh:ifInRole roles="/Policy/Poly/Medcard/View">
    	<msh:section>
    		<msh:sectionTitle>Медкарты</msh:sectionTitle>
    		<msh:sectionContent>
	    	<ecom:webQuery name="medcard" nativeSql="select m.id,m.number from Medcard m left join PsychiatricCareCard cc on cc.id=${param.id} where m.person_id=cc.patient_id"/>
	    	<msh:table viewUrl="entityShortView-poly_medcard.do" name="medcard" action="entityView-poly_medcard.do" idField="1">
	    		<msh:tableColumn property="2" columnName="Номер медкарты"/>
	    	</msh:table>
    		</msh:sectionContent>
    	</msh:section>
    </msh:ifInRole>
    <msh:ifInRole roles="/Policy/Poly/Ticket/View">
    <msh:section title="Диагнозы">
    <table><tr valign="top"><td style="padding-right: 8px">
    	<msh:sectionTitle>Диагнозы по талонам</msh:sectionTitle>
    	<msh:sectionContent>
	    	<ecom:webQuery name="diag" nativeSql="select cc.id||':'||t.idc10_id,mkb.code,mkb.name,count(*),max(t.date) 
	    		from Ticket t left join vocidc10 mkb on mkb.id=t.idc10_id 
	    		left join PsychiatricCareCard cc on cc.id=${param.id} 
	    		left join medcard m on m.person_id=cc.patient_id 
	    		where t.medcard_id=m.id and t.status=2 and (t.talk is null or t.talk=0) group by idc10_id"/>
	    	<msh:table name="diag" action="js-poly_ticket-listDiag.do" idField="1">
	    		
	    		<msh:tableColumn property="2" columnName="Код МКБ"/>
	    		<msh:tableColumn property="3" columnName="Наим.МКБ"/>
	    		<msh:tableColumn property="4" columnName="Кол-во"/>
	    		<msh:tableColumn property="5" columnName="Дата послед.посещения"/>
	    	</msh:table>
    	</msh:sectionContent>
    	</td><td>
    	<msh:sectionTitle>Диагнозы по листу уточненных диагнозов</msh:sectionTitle>
    	<msh:sectionContent>
		    <ecom:webQuery name="list" nativeSql="select 
		    d.id, min(d.establishDate),max(d.establishDate) , d.name 
		    , vi.code || ' ' || vi.name   as idc10
		    , vad.name                 as acuity
		    , vk.code || ' ' || vk.name       as ksg   
		    , case when (visit.DTYPE='VISIT' or visit.DTYPE='POLYCLINICMEDCASE') then 'Поликлиника' 
		    	when (visit.DTYPE='HOSPITALMEDCASE' or visit.DTYPE='DEPARTMENTMEDCASE') then 'Стационар' 
		    	when (visit.DTYPE='SERVICEMEDCASE') then 'Услуги' 
		    	else 'неизвестно'
		    	end   
		    , vpd.name as priority, count(*)
		    from diagnosis d 
		    left join PsychiatricCareCard cc on cc.patient_id=d.patient_id 
		    left join MedCase visit            on d.medcase_id  = visit.id 
		    left join MedCase spo              on visit.parent_id       = spo.id
		    left   join VocIdc10 vi          on d.idc10_id    = vi.id 
		    left   join VocAcuityDiagnosis vad on d.acuity_id   = vad.id
		    left   join VocKsg vk on d.clinicStatisticGroup_id = vk.id
		    left join VocPriorityDiagnosis vpd on vpd.id=d.priority_id
		    where  cc.id=${param.id} 
		    group by d.idc10_id,d.priority_id order by d.establishDate" guid="2d59a9bf-327f-4f4f-8336-531458b6caed" />
		    <msh:table name="list" action="entityView-stac_diagnosis.do" idField="1" guid="b621e361-1e0b-4ebd-9f58-b7d919b45bd6">
		      <msh:tableColumn columnName="№" property="sn" guid="0694f6a7-ed40-4ebf-a274-1efd6901cfe4-1" />
		      <msh:tableColumn columnName="ПО" property="8" />
		      <msh:tableColumn columnName="1 раз регистр." property="2" guid="0694f6a7-ed40-4ebf-a274-1efd6901cfe4" />
		      <msh:tableColumn columnName="Послед. раз регистр." property="3" guid="0694f6a7-ed40-4ebf-a274-1efd6901cfe4" />
		      <msh:tableColumn columnName="Наименование" property="4" guid="6682eeef-105f-43a0-be61-30a865f27972" />
		      <msh:tableColumn columnName="Код МКБ" property="5" guid="f34e1b12-3392-4978-b31f-5e54ff2e45bd" />
		      <msh:tableColumn columnName="Приоритет" property="9" guid="7f7d011d-624c-4003-9c7d-4db6e3dda647" />
		      <msh:tableColumn columnName="Кол-во" property="10" guid="7f7d011d-624c-4003-9c7d-4db6e3dda647" />
		    </msh:table>
    	</msh:sectionContent>
    	</td>
    </tr></table>
    </msh:section>
    </msh:ifInRole>
    <table><tr valign="top">
      <msh:ifInRole roles="/Policy/Mis/Psychiatry/CareCard/LpuAreaPsychCareCard/View" guid="f950bd96-a00b-4497-ad74-61fa7a713004">
      <td>
        <msh:section title="Движение по участку." createRoles="/Policy/Mis/Psychiatry/CareCard/LpuAreaPsychCareCard/Create" createUrl="entityParentPrepareCreate-psych_lpuAreaPsychCareCard.do?id=${param.id}"
        listUrl="entityParentList-psych_lpuAreaPsychCareCard.do?id=${param.id}"
        >
          <msh:sectionContent guid="bed88479-5b63-4d93-98a9-ee37dde8ce22">
            <ecom:webQuery name="lpuAreas" nativeSql="select pl.id as plid,pl.startDate as plStartDate
            ,pl.finishDate as plFinishDate ,la.number as laname
            ,(select list(isnull(vpdg.name,isnull(vpac.name,' '))) 
            	from PsychiaticObservation po  
            	left join VocPsychAmbulatoryCare vpac on vpac.id=po.ambulatoryCare_id 
            	left join VocPsychDispensaryGroup vpdg on vpdg.id=po.dispensaryGroup_id 
            	where po.careCard_id=pl.careCard_id and po.startDate>=pl.startDate 
            	and ( pl.finishDate is null or po.startDate<pl.finishDate)
            ) from LpuAreaPsychCareCard pl  
            left join LpuArea la on la.id=pl.lpuArea_id 
            where pl.careCard_id='${param.id}' order by pl.startDate
            "/>
            <msh:table name="lpuAreas" idField="1" action="entityParentView-psych_lpuAreaPsychCareCard.do">
              <msh:tableColumn property="2" columnName="Дата взятия"/>
              <msh:tableColumn property="3" columnName="Дата снятия"/>
              <msh:tableColumn property="4" columnName="Участок"/>
              <msh:tableColumn property="5" columnName="Наблюдения"/>
            </msh:table>
          </msh:sectionContent>
        </msh:section>
     </td>
      </msh:ifInRole>
      
      <msh:ifInRole roles="/Policy/Mis/Psychiatry/CareCard/PsychiaticObservation/View" guid="be0c3268-8280-4e65-b73a-e5933fea4741">
      <td>
        <msh:section title="Динамика наблюдений." createRoles="/Policy/Mis/Psychiatry/CareCard/PsychiaticObservation/Create" createUrl="entityParentPrepareCreate-psych_observation.do?id=${param.id}"
        listUrl="entityParentList-psych_observation.do?id=${param.id}">
          <msh:sectionContent guid="252153e1-1c93-469a-8184-4d61f6fa08e3">
            <ecom:parentEntityListAll attribute="observations" formName="psych_observationForm" guid="d2316e2f-aafe-44ad-9dda-aa73f1506131" />
            <msh:table name="observations" idField="id" action="entityParentView-psych_observation.do" guid="c3767948-f614-492c-ba65-560430eb7646">
              <msh:tableColumn property="startDate" columnName="Дата начала"/>
              <msh:tableColumn property="finishDate" columnName="Дата окончания"/>
              <msh:tableColumn property="ambulatoryCareInfo" columnName="Вид наблюдения"/>
              <msh:tableColumn property="dispensaryGroupInfo" columnName="Группа"/>
            </msh:table>
          </msh:sectionContent>
        </msh:section>
        </td>
      </msh:ifInRole>
      </tr></table>
    
    <!-- Инвалидность -->
   	<msh:ifInRole roles="/Policy/Mis/Patient/Invalidity/View">
   		<msh:section>
   			<msh:sectionTitle>Инвалидности по психическому заболеванию
   			</msh:sectionTitle>
   			<msh:sectionContent>
	    		<ecom:webQuery nativeSql="select i.id, i.firstDiscloseDate,i.dateFrom,i.revisionDate,i.dateTo,vi.name as viname,mkb.code as mkbcode,i.childhoodInvalid,i.greatePatrioticWarInvalid,i.isWorking,i.nextRevisionDate, viwp.name as viwpname from invalidity i left join vocInvalidWorkPlace viwp on viwp.id=i.workPlace_id left join VocInvalidity vi on vi.id=i.group_id left join vocidc10 mkb on mkb.id=i.idc10_id left join PsychiatricCareCard card on card.id=${param.id} where card.patient_id=i.patient_id and (mkb.code is null or mkb.code like 'F%' )" name="invalidities"/>
	    		<msh:table name="invalidities" action="entityParentView-mis_invalidity.do" idField="1">
	    			<msh:tableColumn property="sn" columnName="#"/>
	    			<msh:tableColumn property="6" columnName="Группа инвалидности"/>
	    			<msh:tableColumn property="2" columnName="Дата постановки (впервые)"/>
	    			<msh:tableColumn property="3" columnName="Дата установления"/>
	    			<msh:tableColumn property="4" columnName="Дата пересмотра"/>
	    			<msh:tableColumn property="11" columnName="Дата след. пересмотра"/>
	    			<msh:tableColumn property="12" columnName="Вид места работы"/>
	    			<msh:tableColumn property="7" columnName="Диагноз"/>
	    			<msh:tableColumn property="8" columnName="Инвалид с детства"/>
	    			<msh:tableColumn property="9" columnName="Инвалид ВОВ"/>
	    			<msh:tableColumn property="10" columnName="Трудоспособен"/>
	    			<msh:tableColumn property="5" columnName="Дата снятия (с учета)"/>
	    		</msh:table>
   			</msh:sectionContent>
   		</msh:section>
   	</msh:ifInRole>
      
      <msh:ifInRole roles="/Policy/Mis/Psychiatry/CareCard/PsychiatricExamination/View" guid="7d1c6824-a159-4a7d-81a1-a711db48386e">
        <msh:section title="Судебно-психиатрические экспертизы." createUrl="entityParentPrepareCreate-psych_examination.do?id=${param.id}" createRoles="/Policy/Mis/Psychiatry/CareCard/PsychiatricExamination/Create"
        listUrl="entityParentList-psych_examination.do?id=${param.id}"
        >
          <msh:sectionContent guid="95327f7f-d788-4e85-aac4-31c322a67b79">
            <ecom:parentEntityListAll attribute="examinations" formName="psych_examinationForm" guid="ddea4b33-71de-4ca5-8fe7-5ac29cf8d247" />
            <msh:table name="examinations" idField="id" action="entityParentView-psych_examination.do" guid="51082605-0ce4-46fc-b2e9-824a2ddfcb5b">
              <msh:tableColumn property="actNumber" columnName="Номер акта"/>
              <msh:tableColumn property="examinationDate" columnName="Дата экспертизы"/>
              <msh:tableColumn property="criminalCaseInfo" columnName="Вид уголовного дела"/>
              <msh:tableColumn property="criminalCodeArticalInfo" columnName="Статья уголовного кодекса"/>
              <msh:tableColumn property="kindInfo" columnName="Вид экспертизы"/>
              <msh:tableColumn property="paticipationInfo" columnName="Вид участия в экспертизе"/>
              <msh:tableColumn property="reporter" columnName="Докладчик"/>
              <msh:tableColumn property="expertDecision" columnName="Экспертное заключение"/>
              <msh:tableColumn property="actNotes" columnName="Описание акта"/>
            </msh:table>
          </msh:sectionContent>
        </msh:section>
      </msh:ifInRole>
      <msh:ifInRole roles="/Policy/Mis/Psychiatry/CareCard/CompulsoryTreatment/View" guid="d95cd064-61fe-47bb-ab71-bc33103adc15">
        <msh:section title="Принудительное лечение." createRoles="/Policy/Mis/Psychiatry/CareCard/CompulsoryTreatment/Edit" createUrl="entityParentPrepareCreate-psych_compulsoryTreatment.do?id=${param.id}"
        listUrl="entityParentList-psych_compulsoryTreatment.do?id=${param.id}">
          <msh:sectionContent guid="3e092419-4f65-4908-8431-786b6ac6f4dc">
            <%--<ecom:parentEntityListAll attribute="compulsoryTreatments" formName="psych_compulsoryTreatmentForm" guid="21377aec-7851-48a2-9754-0e6079c33042" />
            <msh:table idField="id" name="compulsoryTreatments" action="entityParentView-psych_compulsoryTreatment.do" guid="d0c8c1bc-8947-483f-a682-bf6cab9fef9f">
              <msh:tableColumn property="orderNumber" columnName="Поряд. № лечения" />
              <msh:tableColumn property="registrationDate" columnName="Дата регистрации" />
              <msh:tableColumn property="decisionDate" columnName="Дата решения суда" />
              <msh:tableColumn property="lawCourtInfo" columnName="Суд, принявший решение" />
              <msh:tableColumn property="crimainalCodeArticleInfo" columnName="Статья уголовного кодекса" />
              <msh:tableColumn property="courtDecisionInfo" columnName="Решение суда" />
              <msh:tableColumn property="kindInfo" columnName="Вид принудительного лечения" />
            </msh:table>--%>
            <ecom:webQuery name="compulsoryTreatments" nativeSql="
            select ct.id,ct.orderNumber,ct.registrationDate,ct.decisionDate,vlc.name as vlcname
            	,vcca.name as vccaname
            	,vpct.name as vkname 
            	,to_char(COALESCE(cast(ct.dateReplace as integer),cast(current_date as integer))-cast(ct.decisionDate as integer),'9 999')
            	,to_char(
            		COALESCE(cast(ct.dateReplace as integer),cast(current_date as integer))
            		-(select min(cast(ct1.decisionDate as integer))  from CompulsoryTreatment ct1 where ct1.careCard_id=ct.careCard_id and ct1.orderNumber=ct.orderNumber)
            	
            	,'9 999')
            	 ,vpctR.code as vpctRcode,ct.dateReplace,vlcR.code as vlcRcode
            from CompulsoryTreatment ct
            left join VocLawCourt vlc on vlc.id=ct.lawCourt_id
            left join VocLawCourt vlcR on vlcR.id=ct.lawCourtReplace_id
            left join VocCriminalCodeArticle vcca on vcca.id=ct.crimainalCodeArticle_id
            left join VocPsychCompulsoryTreatment vpct on vpct.id=ct.kind_id
            left join VocPsychCompulsoryTreatment vpctR on vpctR.id=ct.courtDecisionReplace_id
            where ct.careCard_id=${param.id} order by ct.decisionDate
            "/>
            <msh:table idField="1" name="compulsoryTreatments" action="entityParentView-psych_compulsoryTreatment.do" guid="d0c8c1bc-8947-483f-a682-bf6cab9fef9f">
              <msh:tableColumn property="2" columnName="Поряд. № лечения" />
              <msh:tableColumn property="3" columnName="Дата регистрации" />
              <msh:tableColumn property="4" columnName="Дата решения суда" />
              <msh:tableColumn property="5" columnName="Суд, принявший решение" />
              <msh:tableColumn property="6" columnName="Статья уголовного кодекса" />
              <msh:tableColumn property="7" columnName="Решение суда" />
              <msh:tableColumn property="11" columnName="Дата замены" />
              <msh:tableColumn property="12" columnName="Суд, заменивший решение" />
              <msh:tableColumn property="10" columnName="Решение заменено на" />
              
              <msh:tableColumn property="8" columnName="Кол-во к/дней " />
              <msh:tableColumn property="9" columnName="Кол-во к/дней общее" />

            </msh:table>
          </msh:sectionContent>
        </msh:section>
      </msh:ifInRole>
      <table><tr valign="top">
      <msh:ifInRole roles="/Policy/Mis/Psychiatry/CareCard/HealthState/View" guid="97d9a49f-c774-4f8f-8ce1-8ea36756e272">
      <td style="padding-right: 8px"  >
        <msh:section title="Динамика состояний." createRoles="/Policy/Mis/Psychiatry/CareCard/HealthState/Create" createUrl="entityParentPrepareCreate-psych_healthState.do?id=${param.id}"
        listUrl="entityParentList-psych_healthState.do?id=${param.id}"
        >
          <msh:sectionContent guid="d3d23d31-6088-4052-943d-0846586488e0">
            <ecom:parentEntityListAll attribute="healthState" formName="psych_healthStateForm" guid="07415339-4378-4727-b0be-898e1c2bfc2e" />
            <msh:table name="healthState" idField="id" action="entityParentView-psych_healthState.do" guid="111932b3-829a-4096-80a0-5c4605a0dbef">
              <msh:tableColumn property="id" columnName="ИД" guid="7b0d4521-fbb8-4cb5-9a38-9f13bc9fd6e9" />
              <msh:tableColumn property="startDate" columnName="Дата начала"/>
              <msh:tableColumn property="finishDate" columnName="Дата окончания"/>
              <msh:tableColumn property="kindInfo" columnName="Вид состояния псих.здоровья"/>
              <msh:tableColumn property="notes" columnName="Описание"/>
            </msh:table>
          </msh:sectionContent>
        </msh:section>
        </td>
      </msh:ifInRole>
      </tr></table>
      <table><tr valign="top">
      <msh:ifInRole roles="/Policy/Mis/Psychiatry/CareCard/PublicDangerousEffect/View" guid="34b4b66c-a108-4648-91ee-326707ea702d">
      <td style="padding-right: 8px">
        <msh:section title="Общественно-опасные действия." createRoles="/Policy/Mis/Psychiatry/CareCard/PublicDangerousEffect" createUrl="entityParentPrepareCreate-psych_publicDangerousEffect.do?id=${param.id}"
        listUrl="entityParentList-psych_publicDangerousEffect.do?id=${param.id}">
          <msh:sectionContent guid="bf90a38f-a199-4da3-a858-aed5ae5fe4da">
            <ecom:parentEntityListAll attribute="publicDangerousEffects" formName="psych_publicDangerousEffectForm" guid="ceda1678-b48b-4319-884d-e1657624b81f" />
            <msh:table name="publicDangerousEffects" idField="id" action="entityParentView-psych_publicDangerousEffect.do" guid="bd3b7306-a90c-4ad5-9875-f24ce7a12654">
              <msh:tableColumn property="effectNumber" columnName="Номер"/>
              <msh:tableColumn property="effectDate" columnName="Дата действия"/>
              <msh:tableColumn property="criminalCodeArticalInfo" columnName="Статья уголовного кодекса"/>
              <msh:tableColumn property="notes" columnName="Примечание"/>
            </msh:table>
          </msh:sectionContent>
        </msh:section>
        </td>
      </msh:ifInRole>
      <msh:ifInRole roles="/Policy/Mis/Psychiatry/CareCard/Suicide/View" guid="62c676d9-c94e-4333-9d86-f99033d5c672">
      <td>
       <msh:section title="Суициды." createRoles="/Policy/Mis/Psychiatry/CareCard/Suicide/Create" createUrl="entityParentPrepareCreate-psych_suicide.do?id=${param.id}"
        listUrl="entityParentList-psych_suicide.do?id=${param.id}">
          <msh:sectionContent guid="dc298afa-d1da-4059-bc63-0612623efcc4">
            <ecom:parentEntityListAll attribute="suicides" formName="psych_suicideForm" guid="80d89232-1c00-4a2f-a590-d9fa66ec5815" />
            <msh:table name="suicides" idField="id" action="entityParentView-psych_suicide.do" guid="764bb8ab-3a0f-4b21-bec0-cd2449d7252b">
              <msh:tableColumn property="id" columnName="ИД" guid="260b7cf5-d2eb-4335-a31d-7a6dd1b1664b" />
              <msh:tableColumn property="fulfilmentDate" columnName="Дата совершения"/>
              <msh:tableColumn property="notes" columnName="Описание" />
            </msh:table>
          </msh:sectionContent>
        </msh:section>
        </td>
      </msh:ifInRole>
      </tr></table>
      <msh:ifInRole roles="/Policy/Mis/MedCase/View">
      	<msh:section title="Госпитализации по псих.профилю ЛПУ">
      		<ecom:webQuery name="hospitalMedCase" nativeSql="
      		select h.id,h.dateStart,h.dateFinish,h.lawCourtDesicionDate, case when h.incapacity=1 then 'Да' else 'Нет' end  as hincapacity
				, (select list(vdrt.name||' '||vpd.name||' '||mkb.code) from Diagnosis diag left join vocidc10 mkb on mkb.id=diag.idc10_id left join VocPriorityDiagnosis vpd on vpd.id=diag.priority_id left join VocDiagnosisRegistrationType vdrt on vdrt.id=diag.registrationType_id where diag.medcase_id=h.id)
      		from MedCase h 
      		left join PsychiatricCareCard cc on cc.patient_id=h.patient_id
      		left join mislpu lpu on lpu.id=h.lpu_id
      		left join VocMzDepType vmdt on vmdt.id=lpu.profile_id
      		where cc.id=${param.id} and (h.dtype='HospitalMedCase' or h.dtype='ExtHospitalMedCase') and vmdt.code='К25'"/>
      		<msh:table viewUrl="entitySubclassShortView-mis_medCase.do" name="hospitalMedCase" action="entitySubclassView-mis_medCase.do" idField="1">
      			<msh:tableColumn property="2" columnName="Дата поступления"/>
      			<msh:tableColumn property="3" columnName="Дата выписки"/>
      			<msh:tableColumn property="4" columnName="Дата суда"/>
      			<msh:tableColumn property="5" columnName="Статья 29"/>
      			<msh:tableColumn property="6" columnName="Диагноз"/>
      		</msh:table>
      	</msh:section>
      </msh:ifInRole>
    </msh:ifFormTypeIsView>
  </tiles:put>
  <tiles:put name="title" type="string">
    <ecom:titleTrail mainMenu="Patient" beginForm="psych_careCardForm" guid="786cb94b-6b42-4361-909b-7a230436395e" />
  </tiles:put>
</tiles:insert>

