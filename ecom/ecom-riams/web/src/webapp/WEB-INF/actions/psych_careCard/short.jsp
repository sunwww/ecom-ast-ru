<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://struts.apache.org/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://www.nuzmsh.ru/tags/msh" prefix="msh" %>
<%@ taglib uri="http://www.ecom-ast.ru/tags/ecom" prefix="ecom" %>

<tiles:insert page="/WEB-INF/tiles/mainShortLayout.jsp" flush="true">

  <tiles:put name="side" type="string">
    <msh:ifFormTypeIsView formName="psych_careCardForm">
      <msh:sideMenu title="Карта обратившего">
        <msh:sideLink key="ALT+2" params="id" action="/entityParentEdit-psych_careCard" name="Изменить" roles="/Policy/Mis/Psychiatry/CareCard/Edit" />
        <msh:sideLink key="ALT+DEL" confirm="Удалить?" params="id" action="/entityParentDeleteGoParentView-psych_careCard" name="Удалить" roles="/Policy/Mis/Psychiatry/CareCard/Delete" />
      </msh:sideMenu>
      <msh:sideMenu title="Добавить">
        <msh:sideLink action="/entityParentPrepareCreate-psych_compulsoryTreatment" name="Принудительное лечение" params="id" roles="/Policy/Mis/Psychiatry/CareCard/CompulsoryTreatment/Create" title="Принудительное лечение" />
        <msh:sideLink action="/entityParentPrepareCreate-psych_examination" name="Судебно-психиатрическую экспертизу" params="id" roles="/Policy/Mis/Psychiatry/CareCard/PsychiatricExamination/Create" title="Судебно-психиатрическую экспертизу" />
        <msh:sideLink action="/entityParentPrepareCreate-psych_healthState" name="Состояние здоровья" params="id" roles="/Policy/Mis/Psychiatry/CareCard/HealthState/Create" title="Состояние здоровья" />
        <msh:sideLink action="/entityParentPrepareCreate-psych_lpuAreaPsychCareCard" name="Участок" params="id" roles="/Policy/Mis/Psychiatry/CareCard/LpuAreaPsychCareCard/Create" title="Участок" />
        <msh:sideLink action="/entityParentPrepareCreate-psych_observation" name="Психиатрическое наблюдение" params="id" roles="/Policy/Mis/Psychiatry/CareCard/PsychiaticObservation/Create" title="Психиатрическое наблюдение" />
        <msh:sideLink action="/entityParentPrepareCreate-psych_publicDangerousEffect" name="Общественно-опасные события" params="id" roles="/Policy/Mis/Psychiatry/CareCard/PublicDangerousEffect/Create" title="Общественно-опасные события" />
        <msh:sideLink action="/entityParentPrepareCreate-psych_suicide" name="Суицид" params="id" roles="/Policy/Mis/Psychiatry/CareCard/Suicide/Create" title="Суицид" />
      </msh:sideMenu>
      <%-- 
      <msh:sideMenu title="Перейти">
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
    <msh:form action="/entityParentSaveGoView-psych_careCard.do" defaultField="cardNumber">
      <msh:hidden property="id" />
      <msh:hidden property="patient" />
      <msh:hidden property="saveType" />
      <msh:panel>
        <msh:row>
          <msh:textField property="cardNumber" label="Номер карты" />
        </msh:row>
        <msh:row>
          <msh:checkBox property="firstTimeDiseased" label="Впервые в жизни заболевший" />
          <msh:checkBox property="convictionsBeforeCare" label="Судимость до обращения к психиатру" />
        </msh:row>
        <msh:row>
          <msh:textField property="illnessStartDate" label="Дата начала заболевания" />
          <msh:textField property="firstPsychiatricVisitDate" label="Дата 1 обращ. к психиатру" />
        </msh:row>
        <msh:row>
          <msh:textField property="startDate" label="Дата взятия на учет" />
          <msh:autoComplete property="observationReason" vocName="vocPsychObservationReason" label="Причина наблюдения" horizontalFill="true"/>
        </msh:row>
        <msh:row>
          <msh:textField property="lastInvoluntaryExamDate" label="Дата посл. недоб. освид-ния" />
          <msh:textField property="finishDate" label="Дата окончания учета" />
        </msh:row>
        <msh:row>
          <msh:autoComplete property="strikeOffReason" vocName="vocPsychStrikeOffReason" label="Причина прекр. набл." fieldColSpan="3" horizontalFill="true"/>
        </msh:row>
        <msh:row>
          <msh:textField property="strikeOffOtherReasonNotes" label="Другая причина"  fieldColSpan="3" horizontalFill="true"/>
        </msh:row>
        <msh:row>
          <msh:textField property="deathDate" label="Дата смерти" />
          <msh:autoComplete property="deathReason" label="Причина смерти" vocName="vocPsychDeathReason" horizontalFill="true"/>
        </msh:row>
        <msh:row>
          <msh:textArea property="notes" label="Примечание" fieldColSpan="4" />
        </msh:row>
        <msh:submitCancelButtonsRow colSpan="3" />
      </msh:panel>
    </msh:form>
    <msh:ifFormTypeIsView formName="psych_careCardForm">
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
		    group by d.idc10_id,d.priority_id order by vi.code " />
		    <msh:table name="list" action="entityView-stac_diagnosis.do" idField="1">
		      <msh:tableColumn columnName="№" property="sn" />
		      <msh:tableColumn columnName="ПО" property="8" />
		      <msh:tableColumn columnName="1 раз регистр." property="2" />
		      <msh:tableColumn columnName="Послед. раз регистр." property="3" />
		      <msh:tableColumn columnName="Наименование" property="4" />
		      <msh:tableColumn columnName="Код МКБ" property="5" />
		      <msh:tableColumn columnName="Приоритет" property="9" />
		      <msh:tableColumn columnName="Кол-во" property="10" />
		    </msh:table>
    	</msh:sectionContent>
    	</td>
    </tr></table>
    </msh:section>
    </msh:ifInRole>
    <table><tr valign="top">
      <msh:ifInRole roles="/Policy/Mis/Psychiatry/CareCard/LpuAreaPsychCareCard/View">
      <td>
        <msh:section title="Движение по участку." createRoles="/Policy/Mis/Psychiatry/CareCard/LpuAreaPsychCareCard/Create" createUrl="entityParentPrepareCreate-psych_lpuAreaPsychCareCard.do?id=${param.id}"
        >
          
            <ecom:webQuery name="lpuAreas" nativeSql="select pl.id as plid,pl.startDate as plStartDate
            ,coalesce(pl.finishDate,pl.transferDate) as plFinishDate ,la.number as laname
            ,(select list(coalesce(to_char(po.startDate,'dd.mm.yyyy'),'')||' '||isnull(vpdg.name,isnull(vpac.name,' '))) 
            	from PsychiaticObservation po  
            	left join VocPsychAmbulatoryCare vpac on vpac.id=po.ambulatoryCare_id 
            	left join VocPsychDispensaryGroup vpdg on vpdg.id=po.dispensaryGroup_id 
            	where po.careCard_id=pl.careCard_id and po.lpuAreaPsychCareCard_id=pl.id
            ),vpor.name as vporname,vptr.name as vptrname
            ,vpsor.name as vpsorname from LpuAreaPsychCareCard pl  
            left join LpuArea la on la.id=pl.lpuArea_id 
            left join VocPsychObservationReason vpor on vpor.id=pl.observationReason_id
            left join VocPsychTransferReason vptr on vptr.id=pl.transferReason_id
            left join VocPsychStrikeOffReason vpsor on vpsor.id=pl.stikeOffReason_id
            where pl.careCard_id='${param.id}' order by pl.startDate
            "/>
            <msh:tableEmpty name="lpuAreas">
            	<a href="js-psych_careCard-createComissia.do?id=${param.id}">Создать данные по комиссии</a>
            </msh:tableEmpty>
            <msh:table name="lpuAreas" idField="1" action="entityParentView-psych_lpuAreaPsychCareCard.do">
              <msh:tableColumn property="4" columnName="Участок"/>
              <msh:tableColumn property="5" columnName="Наблюдения"/>
              <msh:tableColumn property="2" columnName="Дата взятия"/>
              <msh:tableColumn property="6" columnName="Причина взятия"/>
              <msh:tableColumn property="3" columnName="Дата снятия (перевода)"/>
              <msh:tableColumn property="7" columnName="Причина перевода"/>
              <msh:tableColumn property="8" columnName="Причина снятия"/>
            </msh:table>
          
        </msh:section>
     </td>
     <td valign="top">
     	<msh:section  title="Дин. наблюдения (АДН+АПЛ)"
     	createRoles="/Policy/Mis/Psychiatry/CareCard/PsychiaticObservation/Create" 
     	createUrl="entityParentPrepareCreate-psych_careobservation.do?id=${param.id}"
     	>
     	<ecom:webQuery name="dinObservation" nativeSql="select po.id as poid
     		,to_char(po.startDate,'dd.mm.yyyy') as stratDate
     		,to_char(po.finishDate,'dd.mm.yyyy') as finishDate
     		,vpdg.name as vpdgname,vpac.name as vpacname
     		,vcca.name as vccaname,vpsora.name as vpsoraname
            	from PsychiaticObservation po  
            	left join VocPsychAmbulatoryCare vpac on vpac.id=po.ambulatoryCare_id 
            	left join VocPsychDispensaryGroup vpdg on vpdg.id=po.dispensaryGroup_id
            	left join vocCriminalCodeArticle vcca on vcca.id=po.criminalCodeArticle_id
            	left join VocPsychStrikeOffReasonAdn vpsora on vpsora.id=po.strikeOffReason_id
            	where po.careCard_id=${param.id} and po.lpuAreaPsychCareCard_id is null
           "/>
           <msh:table name="dinObservation" idField="1" action="entityParentView-psych_careobservation.do">
              <msh:tableColumn property="4" columnName="Наблюдение"/>
              <msh:tableColumn property="2" columnName="Дата взятия"/>
              <msh:tableColumn property="6" columnName="Статья уг. кодекса"/>
              <msh:tableColumn property="3" columnName="Дата снятия (перевода)"/>
              <msh:tableColumn property="7" columnName="Причина снятия"/>
            </msh:table>
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
      
      <msh:ifInRole roles="/Policy/Mis/Psychiatry/CareCard/PsychiatricExamination/View">
        <msh:section title="Судебно-психиатрические экспертизы." createUrl="entityParentPrepareCreate-psych_examination.do?id=${param.id}" createRoles="/Policy/Mis/Psychiatry/CareCard/PsychiatricExamination/Create"
        listUrl="entityParentList-psych_examination.do?id=${param.id}"
        >
          <msh:sectionContent>
            <ecom:parentEntityListAll attribute="examinations" formName="psych_examinationForm" />
            <msh:table name="examinations" idField="id" action="entityParentView-psych_examination.do">
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
      <msh:ifInRole roles="/Policy/Mis/Psychiatry/CareCard/CompulsoryTreatment/View">
        <msh:section title="Принудительное лечение." createRoles="/Policy/Mis/Psychiatry/CareCard/CompulsoryTreatment/Edit" createUrl="entityParentPrepareCreate-psych_compulsoryTreatment.do?id=${param.id}"
        listUrl="entityParentList-psych_compulsoryTreatment.do?id=${param.id}">
          <msh:sectionContent>
            <%--<ecom:parentEntityListAll attribute="compulsoryTreatments" formName="psych_compulsoryTreatmentForm" />
            <msh:table idField="id" name="compulsoryTreatments" action="entityParentView-psych_compulsoryTreatment.do">
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
            <msh:table idField="1" name="compulsoryTreatments" action="entityParentView-psych_compulsoryTreatment.do">
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
      <msh:ifInRole roles="/Policy/Mis/Psychiatry/CareCard/HealthState/View">
      <td style="padding-right: 8px"  >
        <msh:section title="Динамика состояний." createRoles="/Policy/Mis/Psychiatry/CareCard/HealthState/Create" createUrl="entityParentPrepareCreate-psych_healthState.do?id=${param.id}"
        listUrl="entityParentList-psych_healthState.do?id=${param.id}"
        >
          <msh:sectionContent>
            <ecom:parentEntityListAll attribute="healthState" formName="psych_healthStateForm" />
            <msh:table name="healthState" idField="id" action="entityParentView-psych_healthState.do">
              <msh:tableColumn property="id" columnName="ИД" />
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
      <msh:ifInRole roles="/Policy/Mis/Psychiatry/CareCard/PublicDangerousEffect/View">
      <td style="padding-right: 8px">
        <msh:section title="Общественно-опасные действия." createRoles="/Policy/Mis/Psychiatry/CareCard/PublicDangerousEffect" createUrl="entityParentPrepareCreate-psych_publicDangerousEffect.do?id=${param.id}"
        listUrl="entityParentList-psych_publicDangerousEffect.do?id=${param.id}">
          <msh:sectionContent>
            <ecom:parentEntityListAll attribute="publicDangerousEffects" formName="psych_publicDangerousEffectForm" />
            <msh:table name="publicDangerousEffects" idField="id" action="entityParentView-psych_publicDangerousEffect.do">
              <msh:tableColumn property="effectNumber" columnName="Номер"/>
              <msh:tableColumn property="effectDate" columnName="Дата действия"/>
              <msh:tableColumn property="criminalCodeArticalInfo" columnName="Статья уголовного кодекса"/>
              <msh:tableColumn property="notes" columnName="Примечание"/>
            </msh:table>
          </msh:sectionContent>
        </msh:section>
        </td>
      </msh:ifInRole>
      <msh:ifInRole roles="/Policy/Mis/Psychiatry/CareCard/Suicide/View">
      <td>
       <msh:section title="Суициды." createRoles="/Policy/Mis/Psychiatry/CareCard/Suicide/Create" createUrl="entityParentPrepareCreate-psych_suicide.do?id=${param.id}"
        listUrl="entityParentList-psych_suicide.do?id=${param.id}">
          <msh:sectionContent>
            <ecom:parentEntityListAll attribute="suicides" formName="psych_suicideForm" />
            <msh:table name="suicides" idField="id" action="entityParentView-psych_suicide.do">
              <msh:tableColumn property="id" columnName="ИД" />
              <msh:tableColumn property="fulfilmentDate" columnName="Дата совершения"/>
              <msh:tableColumn property="notes" columnName="Описание" />
            </msh:table>
          </msh:sectionContent>
        </msh:section>
        <msh:section title="Талоны по суицидальным попыткам">
        <ecom:webQuery name="listds" nativeSql="select s.id,s.suicideDate
  	from SuicideMessage s
  	left join PsychiatricCareCard card on card.patient_id=s.patient_id where card.id=${param.id}
  	
  	
  	order by s.suicideDate
  	"/>
    <msh:table name="listds" action="entityParentView-psych_suicideMessage.do" idField="1">
              <msh:tableColumn property="sn" columnName="#"/>
              <msh:tableColumn property="1" columnName="ИД"/>
              <msh:tableColumn property="2" columnName="Дата суицида"/>
    </msh:table>
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
    <ecom:titleTrail mainMenu="Patient" beginForm="psych_careCardForm" />
  </tiles:put>
</tiles:insert>

