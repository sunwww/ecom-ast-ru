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
          <msh:autoComplete property="lpu" horizontalFill="true" size="50" label="ЛПУ" vocName="mainLpu"/>
        </msh:row>
        <msh:row guid="9a12d78d-7956-406c-b453-45f4394c6c46">
          <msh:checkBox property="firstTimeDiseased" label="Впервые в жизни заболевший" guid="fc6dda3a-0acb-48de-ae14-dcc5bffbc022" />
          <msh:checkBox property="convictionsBeforeCare" label="Судимость до обращения к психиатру" guid="88e32876-cbd9-40e8-9d05-1caf9ac40188" />
        </msh:row>
        <msh:row>
        	<msh:checkBox property="updateDates" label="Обновлять даты по дате начала заболевания" fieldColSpan="3"/>
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
        <msh:row guid="24ce0983-813b-4cc4-a329-f7487c49b66c">
          <msh:textField viewOnlyField="true" property="dateRegistration" label="Дата регистрации" horizontalFill="true" size="10" guid="e4905c10-14f2-471f-a07a-9d3119230614" />
          <msh:textField viewOnlyField="true" property="registrator" label="Регистратор" horizontalFill="true" size="10" guid="e4905c10-14f2-471f-a07a-9d3119230614" />
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
	    	<ecom:webQuery name="diag" nativeSql="
	    	select '&card='||cc.id||'&mkb='||t.idc10_id||'&dtype=ShortMedCase&priority='||d.priority_id,mkb.code,mkb.name
	    	,count(*),max(t.dateStart)
	    	, vpd.name as priority6 
	    		from MedCase t 
	    		left join Diagnosis d on d.medcase_id=t.id
	    		left join vocidc10 mkb on mkb.id=d.idc10_id 
	    		left join PsychiatricCareCard cc on cc.id=${param.id} 
	    		left join VocPriorityDiagnosis vpd on vpd.id=d.priority_id
	    		where t.patient_id=cc.patient_id 
	    		and (t.dtype='ShortMedCase' or t.DTYPE='VISIT' or t.DTYPE='POLYCLINICMEDCASE')
	    		and t.dateStart is not null and (t.istalk is null or t.istalk='0')
	    		 and mkb.id is not null
	    		group by d.idc10_id,mkb.code,mkb.name,d.priority_id,vpd.name,cc.id,t.idc10_id
	    		
	    		"/>
	    	<msh:table name="diag" action="js-smo_ticket-listDiag.do" idField="1">
	    		
	    		<msh:tableColumn property="2" columnName="Код МКБ"/>
	    		<msh:tableColumn property="3" columnName="Наим.МКБ"/>
	    		<msh:tableColumn property="6" columnName="Приоритет"/>
	    		<msh:tableColumn property="4" columnName="Кол-во"/>
	    		<msh:tableColumn property="5" columnName="Дата послед.посещения"/>
	    	</msh:table>
    	</msh:sectionContent>
    	</td><td>
    	<msh:sectionTitle>Диагнозы по стационару</msh:sectionTitle>
    	<msh:sectionContent>
		    <ecom:webQuery name="list" nativeSql=" 
		    select 
		    '&card=${param.id}&mkb='||d.idc10_id||'&priority='||d.priority_id||'&dtype='||coalesce(visit.dtype,'') as id1
		    , min(spo.datefinish) as mind2,max(spo.datefinish) as maxd3
		    , list(distinct d.name ) as dname4
		    , vi.code || ' ' || vi.name   as idc105
		    
		    , vpd.name as priority6, count(*) as cnt7
		    from diagnosis d 
		    left join PsychiatricCareCard cc on cc.patient_id=d.patient_id 
		    left join MedCase visit            on d.medcase_id  = visit.id 
		    left join MedCase spo              on visit.parent_id       = spo.id
		    left   join VocIdc10 vi          on d.idc10_id    = vi.id 
		    left join VocPriorityDiagnosis vpd on vpd.id=d.priority_id
		    where  cc.id=${param.id} and (visit.DTYPE='HOSPITALMEDCASE') and vi.id is not null
		    group by d.idc10_id,d.priority_id,vi.code,vi.name,visit.DTYPE,vpd.name,vpd.id 
		    order by d.establishDate" guid="2d59a9bf-327f-4f4f-8336-531458b6caed" />
		    <msh:table name="list" action="js-smo_ticket-listDiag.do" idField="1" guid="b621e361-1e0b-4ebd-9f58-b7d919b45bd6">
		      <msh:tableColumn columnName="№" property="sn" guid="0694f6a7-ed40-4ebf-a274-1efd6901cfe4-1" />
		      <msh:tableColumn columnName="ПО" property="6" />
		      <msh:tableColumn columnName="1 раз регистр." property="2" guid="0694f6a7-ed40-4ebf-a274-1efd6901cfe4" />
		      <msh:tableColumn columnName="Послед. раз регистр." property="3" guid="0694f6a7-ed40-4ebf-a274-1efd6901cfe4" />
		      <msh:tableColumn columnName="Наименование" property="4" guid="6682eeef-105f-43a0-be61-30a865f27972" />
		      <msh:tableColumn columnName="Код МКБ" property="5" guid="f34e1b12-3392-4978-b31f-5e54ff2e45bd" />
		      <msh:tableColumn columnName="Приоритет" property="6" guid="7f7d011d-624c-4003-9c7d-4db6e3dda647" />
		      <msh:tableColumn columnName="Кол-во" property="7" guid="7f7d011d-624c-4003-9c7d-4db6e3dda647" />
		    </msh:table>
    	</msh:sectionContent>
    	</td><td>
    	<msh:sectionTitle>Диагнозы по листу уточненных диагнозов</msh:sectionTitle>
    	<msh:sectionContent>
		    <ecom:webQuery name="list" nativeSql=" 
		    select 
		    '&card=${param.id}&mkb='||d.idc10_id||'&priority='||d.priority_id||'&dtype='||coalesce(visit.dtype,'') as id1
		    , min(d.establishDate) as mind2,max(d.establishDate) as maxd3
		    , list(distinct d.name ) as dname4
		    , vi.code || ' ' || vi.name   as idc105
		    
		    , vpd.name as priority6, count(*) as cnt7
		    from diagnosis d 
		    left join PsychiatricCareCard cc on cc.patient_id=d.patient_id 
		    left join MedCase visit            on d.medcase_id  = visit.id 
		    left join MedCase spo              on visit.parent_id       = spo.id
		    left   join VocIdc10 vi          on d.idc10_id    = vi.id 
		    left join VocPriorityDiagnosis vpd on vpd.id=d.priority_id
		    where  cc.id=${param.id} and d.medcase_id is null
		    group by d.idc10_id,d.priority_id,vi.code,vi.name,visit.DTYPE,vpd.name,vpd.id 
		    order by d.establishDate" guid="2d59a9bf-327f-4f4f-8336-531458b6caed" />
		    <msh:table name="list" action="js-smo_ticket-listDiag.do" idField="1" guid="b621e361-1e0b-4ebd-9f58-b7d919b45bd6">
		      <msh:tableColumn columnName="№" property="sn" guid="0694f6a7-ed40-4ebf-a274-1efd6901cfe4-1" />
		      <msh:tableColumn columnName="ПО" property="6" />
		      <msh:tableColumn columnName="1 раз регистр." property="2" guid="0694f6a7-ed40-4ebf-a274-1efd6901cfe4" />
		      <msh:tableColumn columnName="Послед. раз регистр." property="3" guid="0694f6a7-ed40-4ebf-a274-1efd6901cfe4" />
		      <msh:tableColumn columnName="Наименование" property="4" guid="6682eeef-105f-43a0-be61-30a865f27972" />
		      <msh:tableColumn columnName="Код МКБ" property="5" guid="f34e1b12-3392-4978-b31f-5e54ff2e45bd" />
		      <msh:tableColumn columnName="Приоритет" property="6" guid="7f7d011d-624c-4003-9c7d-4db6e3dda647" />
		      <msh:tableColumn columnName="Кол-во" property="7" guid="7f7d011d-624c-4003-9c7d-4db6e3dda647" />
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
        >
          
            <ecom:webQuery name="lpuAreas" nativeSql="select pl.id as plid,pl.startDate as plStartDate
            ,coalesce(pl.finishDate,pl.transferDate) as plFinishDate ,la.number as laname
            ,(select list(coalesce(to_char(po.startDate,'dd.mm.yyyy'),'')||' '||coalesce(vpdg.name,vpac.name,' ')) 
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
	    		<ecom:webQuery nativeSql="select i.id, i.firstDiscloseDate,i.dateFrom,i.lastRevisionDate,i.dateTo,vi.name as viname,mkb.code as mkbcode,i.childhoodInvalid,i.greatePatrioticWarInvalid
	    		,i.incapable,i.nextRevisionDate, viwp.name as viwpname,i.withoutExam from invalidity i 
	    		left join vocInvalidWorkPlace viwp on viwp.id=i.workPlace_id 
	    		left join VocInvalidity vi on vi.id=i.group_id 
	    		left join vocidc10 mkb on mkb.id=i.idc10_id 
	    		left join PsychiatricCareCard card on card.id=${param.id} where card.patient_id=i.patient_id and (mkb.code is null or mkb.code like 'F%' )" name="invalidities"/>
	    		<msh:table name="invalidities" action="entityParentView-mis_invalidity.do" idField="1">
	    			<msh:tableColumn property="sn" columnName="#"/>
	    			<msh:tableColumn property="6" columnName="Группа инвалидности"/>
	    			<msh:tableColumn property="2" columnName="Дата постановки (впервые)"/>
	    			<msh:tableColumn property="3" columnName="Дата установления"/>
	    			<msh:tableColumn property="4" columnName="Дата посл. пересмотра"/>
	    			<msh:tableColumn property="11" columnName="Дата след. пересмотра"/>
	    			<msh:tableColumn property="13" columnName="Без переосв."/>
	    			<msh:tableColumn property="5" columnName="Дата снятия (с учета)"/>
	    			<msh:tableColumn property="7" columnName="Диагноз"/>
	    			<msh:tableColumn property="8" columnName="Инвалид с детства"/>
	    			<msh:tableColumn property="9" columnName="Инвалид ВОВ"/>
	    			<msh:tableColumn property="10" columnName="Недеесп."/>
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
            	,to_char(COALESCE(ct.dateReplace,current_date)-ct.decisionDate,'9 999') as day1 
 ,to_char( COALESCE(ct.dateReplace,current_date) -(select min(ct1.decisionDate )  from CompulsoryTreatment ct1 where ct1.careCard_id=ct.careCard_id and ct1.orderNumber=ct.orderNumber)
            	
            	,'9 999') as day2
            	 ,vpctdR.name as vpctdRcode
            	 ,ct.registrationReplaceDate as ctregistrationReplaceDate
            	 ,ct.dateReplace as ctdateReplace,vlcR.name as vlcRcode
            from CompulsoryTreatment ct
            left join VocLawCourt vlc on vlc.id=ct.lawCourt_id
            left join VocLawCourt vlcR on vlcR.id=ct.lawCourtReplace_id
            left join VocCriminalCodeArticle vcca on vcca.id=ct.crimainalCodeArticle_id
            left join VocPsychCompulsoryTreatment vpct on vpct.id=ct.kind_id
            left join vocPsychCourtTreatmentDecision vpctdR on vpctdR.id=ct.courtDecisionReplace_id
            where ct.careCard_id=${param.id} order by ct.decisionDate
            "/>
            <msh:table idField="1" name="compulsoryTreatments" action="entityParentView-psych_compulsoryTreatment.do" guid="d0c8c1bc-8947-483f-a682-bf6cab9fef9f">
              <msh:tableColumn property="2" columnName="Поряд. № лечения" />
              <msh:tableColumn property="3" columnName="Дата регистрации" />
              <msh:tableColumn property="4" columnName="Дата решения суда" />
              <msh:tableColumn property="5" columnName="Суд, принявший решение" />
              <msh:tableColumn property="6" columnName="Статья уголовного кодекса" />
              <msh:tableColumn property="7" columnName="Решение суда" />
              <msh:tableColumn property="11" columnName="Дата регистрация замены" />
              <msh:tableColumn property="12" columnName="Дата замены" />
              <msh:tableColumn property="13" columnName="Суд, заменивший решение" />
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
       
        <msh:section title="Талоны по суицидальным попыткам">
        <ecom:webQuery name="listds" nativeSql="select s.id,s.suicideDate
  	from SuicideMessage s
  	left join PsychiatricCareCard card on card.patient_id=s.patient_id where card.id=${param.id}
  	
  	order by s.suicideDate
  	"/>
    <msh:table name="listds" action="entityParentView-psych_suicideMessage.do" idField="1" guid="b621e361-1e0b-4ebd-9f58-b7d919b45bd6">
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
      		select h.id,h.dateStart,h.dateFinish,h.lawCourtDesicionDate, case when h.incapacity='1' then 'Да' else 'Нет' end  as hincapacity
				, (select list(vdrt.name||' '||vpd.name||' '||mkb.code) from Diagnosis diag left join vocidc10 mkb on mkb.id=diag.idc10_id left join VocPriorityDiagnosis vpd on vpd.id=diag.priority_id left join VocDiagnosisRegistrationType vdrt on vdrt.id=diag.registrationType_id where diag.medcase_id=h.id) as diag
				,vdh.name as vdhname
				,(select mldep.name from medcase slo left join mislpu mldep on mldep.id=slo.department_id where slo.parent_id=h.id and upper(slo.dtype)='DEPARTMENTMEDCASE' and slo.transferdate is null) as slodepname
      		from MedCase h 
      		left join VocDeniedHospitalizating vdh on vdh.id=h.deniedHospitalizating_id
      		left join PsychiatricCareCard cc on cc.patient_id=h.patient_id
      		left join mislpu lpu on lpu.id=h.lpu_id
      		left join VocMzDepType vmdt on vmdt.id=lpu.profile_id
      		where cc.id=${param.id} and upper(h.dtype) in ('HOSPITALMEDCASE','EXTHOSPITALMEDCASE') 
      		and vmdt.code='К25'"/>
      		<msh:table viewUrl="entitySubclassShortView-mis_medCase.do" name="hospitalMedCase" action="entitySubclassView-mis_medCase.do" idField="1">
      			<msh:tableColumn property="2" columnName="Дата поступления"/>
      			<msh:tableColumn property="3" columnName="Дата выписки"/>
      			<msh:tableColumn property="4" columnName="Дата суда"/>
      			<msh:tableColumn property="5" columnName="Статья 29"/>
      			<msh:tableColumn property="6" columnName="Диагноз"/>
      			<msh:tableColumn property="7" columnName="Отказ от госпит."/>
      			<msh:tableColumn property="8" columnName="Отделение"/>
      		</msh:table>
      	</msh:section>
      </msh:ifInRole>
    </msh:ifFormTypeIsView>
  </tiles:put>
  <tiles:put name="title" type="string">
    <ecom:titleTrail mainMenu="Patient" beginForm="psych_careCardForm" guid="786cb94b-6b42-4361-909b-7a230436395e" />
  </tiles:put>
  <tiles:put name="javascript" type="string">
  <msh:ifFormTypeIsCreate formName="psych_careCardForm">
  	<script type="text/javascript">
  		$('updateDates').checked = true ;
  	</script>
  </msh:ifFormTypeIsCreate>
	  <msh:ifFormTypeIsNotView formName="psych_careCardForm">
	  	<script type="text/javascript">
	  	eventutil.addEventListener($('illnessStartDate'), eventutil.EVENT_KEY_DOWN, 
	  		  	function() {
	  		setDates() ;
	  		  	}) ;
	  	eventutil.addEventListener($('illnessStartDate'), eventutil.EVENT_KEY_UP, 
	  		  	function() {
	  		setDates() ;
	  		  	}) ;
	  	//eventutil.addEventListener($('illnessStartDate'), "change", 
	  	//	  	function() {
	  	//	setDates() ;
	  	//	  	}) ;
	  	eventutil.addEventListener($('illnessStartDate'), "blur", 
	  		  	function() {
	  		setDates() ;
	  		  	}) ;
	  	function setDates() {
	  		if ($('updateDates').checked) {
	  			$('firstPsychiatricVisitDate').value = $('illnessStartDate').value ;
	  			$('startDate').value = $('illnessStartDate').value ;
	  		}
	  	}
	  	</script>
	  </msh:ifFormTypeIsNotView>
  </tiles:put>
</tiles:insert>

