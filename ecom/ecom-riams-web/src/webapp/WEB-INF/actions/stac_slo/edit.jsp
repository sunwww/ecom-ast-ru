<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://struts.apache.org/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://www.nuzmsh.ru/tags/msh" prefix="msh" %>
<%@ taglib uri="http://www.ecom-ast.ru/tags/ecom" prefix="ecom" %>
<%@ taglib uri="/WEB-INF/mis.tld" prefix="mis" %>
<%@ taglib tagdir="/WEB-INF/tags" prefix="tags" %>

<tiles:insert page="/WEB-INF/tiles/main${param.short}Layout.jsp" flush="true">
	<tiles:put name="style" type="string">
        <style type="text/css">


            #clinicalDiagnosLabel, #clinicalMkbLabel, #clinicalActuityLabel {
                color: blue ;
            }
            #concomitantDiagnosLabel, #concomitantMkbLabel {
                color: green ;
            }

            #concludingDiagnosLabel, #concludingMkbLabel {
                color: black ;
            }
            #pathanatomicalDiagnosLabel, #pathanatomicalMkbLabel {
                color: red ;
            }

        </style>
    </tiles:put>
  <tiles:put name="side" type="string">
    <msh:ifFormTypeIsView formName="stac_sloForm" guid="e2054544-fdd1-4285-a21c-3bb9b4569efc">
      <msh:sideMenu guid="9ec15353-1f35-4c18-b99d-e2b63ecc60c9" title="СЛО">
        <msh:sideLink key="ALT+2" params="id" action="/entityParentEdit-stac_slo" name="Изменить" roles="/Policy/Mis/MedCase/Stac/Ssl/Slo/Edit" guid="5a1450f5-7629-4458-b5a5-e5566af6a914" />
        <msh:sideLink key="ALT+DEL" confirm="Удалить?" params="id" action="/entityParentDelete-stac_slo" name="Удалить" roles="/Policy/Mis/MedCase/Stac/Ssl/Slo/Delete" guid="7767f5b6-c131-47f4-b8a0-2604050c450f" />
            <msh:sideLink roles="/Policy/Mis/MedCase/Stac/Ssl/Slo/Create"  key="CTRL+1"
    	name="Перевод &larr;"   action="/javascript:goTransfer('.do')"  
    	 title='Перевод' styleId="stac_sloTransfer" />
            <msh:sideLink roles="/Policy/Mis/MedCase/Stac/Ssl/Discharge/Show"  key="CTRL+2"
    	name="Выписка &larr;"   action="/javascript:goDischarge('.do')"  
    	 title='Выписка' styleId="stac_sslDischarge" />
      </msh:sideMenu>
      <msh:sideMenu guid="9ec15353-1f35-4c18-b99d-e2b63ecc9" title="Добавить">
      <msh:sideLink guid="helloSideLinkNew" roles="/Policy/Mis/MedCase/ClinicExpertCard/Direct/Create" 
       action="/entityParentPrepareCreate-expert_ker_direct" name="Направление на ВК" params="id" />
      	<msh:ifNotInRole roles="/Policy/Mis/MedCase/Protocol/CreateOnlyInMedService">
	        <msh:sideLink roles="/Policy/Mis/MedCase/Protocol/Create" name="Дневник специалиста" params="id" action="/entityParentPrepareCreate-smo_visitProtocol" title="Дневник специалиста" guid="11cc057f-b309-4193-9d22-199373cfd28d" />
        </msh:ifNotInRole>
        <msh:sideLink roles="/Policy/Mis/MedCase/Stac/Ssl/Diagnosis/Create" name="Диагноз" params="id" action="/entityParentPrepareCreate-stac_diagnosis" title="Диагноз" guid="c3e59a04-8858-4523-9370-74b16ec784e6" />
        <msh:sideLink roles="/Policy/Mis/Prescription/Prescript/Create" name="Лист назначений" params="id" action="/entityParentPrepareCreate-pres_prescriptList" title="Лист назначений" guid="abd8a59e-4968-4a55-adac-c257c1e8a899" />
        <msh:sideLink roles="/Policy/Mis/MedCase/Stac/Ssl/TemperatureCurve/Create" name="Температурный лист" params="id" action="/entityParentPrepareCreate-stac_temperatureCurve" title="Добавить температурный лист" guid="df23-45a-43cc-826d-5hfd" />
        <msh:sideLink roles="/Policy/Mis/MedCase/MedService/Create" name="Услугу" params="id" action="/entityParentPrepareCreate-smo_medService" title="Добавить услугу" guid="df23-45a26d-5hfd" />

      <msh:sideLink roles="/Policy/Mis/MedCase/Transfusion/Blood/Create" params="id" action="/entityParentPrepareCreate-trans_blood" name="Переливание донорской крови и её компонентов" title="Добавить донорской крови и её компонентов" />
      <msh:sideLink roles="/Policy/Mis/MedCase/Transfusion/Other/Create" key="ALT+3" params="id" action="/entityParentPrepareCreate-trans_other" name="Переливание кровезамещающих растворов" title="Добавить переливание кровезамещающих растворов" />
    	
    	<msh:sideLink roles="/Policy/Mis/MedCase/Stac/Ssl/SurOper/Create" name="Операцию"  
    	params="id"  action='/entityParentPrepareCreate-stac_surOperation'  key='Alt+7' title="Операции"
    	/>

        <mis:sideLinkForWoman classByObject="MedCase" id="${param.id}" params="id" action="/entityParentPrepareCreate-preg_childBirth" name="Описание родов" title="Описание родов" roles="/Policy/Mis/Pregnancy/ChildBirth/Create"/>
      </msh:sideMenu>
      <msh:ifNotInRole roles="/Policy/Mis/MedCase/Stac/Ssl/ShortEnter">
      <msh:sideMenu title="Показать" guid="c65476c8-6c6a-43c4-a70a-84f40bda76e1">
        <msh:sideLink styleId="viewShort" action="/javascript:viewOtherVisitsByPatient('.do')" name='ВИЗИТЫ' title="Просмотр визитов по пациенту" key="ALT+4" guid="2156670f-b32c-4634-942b-2f8a4467567c" params="" roles="/Policy/Mis/MedCase/Visit/View" />
        <msh:sideLink styleId="viewShort" action="/javascript:viewOtherDiagnosisByPatient('.do')" name='ДИАГНОЗЫ' title="Просмотр диагнозов по пациенту" key="ALT+5" guid="2156670f-b32c-4634-942b-2f8a4467567c" params="" roles="/Policy/Mis/MedCase/Diagnosis/View" />
        <msh:sideLink styleId="viewShort" action="/javascript:viewOtherHospitalMedCase('.do')" name='Госпитализации' title="Просмотр госпитазиций по пациенту" key="ALT+6" guid="2156670f-b32c-4634-942b-2f8a4467567c" params="" roles="/Policy/Mis/MedCase/Stac/Ssl/View" />
        <msh:sideLink styleId="viewShort"  action="/javascript:getDefinition('entityParentList-expert_ker.do?short=Short&id=${param.id}',null)" name='Врачеб. комиссии' title="Просмотр врачебных комиссий" guid="2156670f-b32c-4634-942b-2f8a4467567c" roles="/Policy/Mis/MedCase/ClinicExpertCard/View" />
        <msh:sideLink styleId="viewShort" action="/javascript:viewOtherExtMedserviceByPatient('.do')" name='Внешние лаб. исследования' title="Просмотр внешних лабораторных данных по пациенту" key="ALT+5" guid="2156670f-b32c-4634-942b-2f8a4467567c" params="" roles="/Policy/Mis/MedCase/Document/External/Medservice/View" />
	  	<msh:sideLink styleId="viewShort" roles="/Policy/Mis/MedCase/QualityEstimationCard/View" name="Экспертные карты" params="id" action="/javascript:getDefinition('entityParentList-expert_card.do?short=Short&id=${param.id}',null)"/>
        <msh:sideLink roles="/Policy/Mis/Prescription/Prescript/View" name="Листы назначений" params="id" action="/entityParentList-pres_prescriptList" title="Показать все листы назначений СЛО" guid="7b0b69ae-3b9c-47d9-ab3c-5055fbe6fa9f" />
        <msh:sideLink roles="/Policy/Mis/MedCase/Stac/Ssl/Diagnosis/View" name="Диагнозы" params="id" action="/entityParentList-stac_diagnosis" title="Показать все диагнозы СЛО" guid="4ac8c095-3853-4150-9e4a-d01b4abc8061" />
        <msh:sideLink roles="/Policy/Mis/MedCase/Protocol/View" name="Дневники специалистов" params="id" action="/entityParentList-smo_visitProtocol" title="Показать все дневники специалиста" guid="d43123-45ca-43cc-826d-bc85" />
        <msh:sideLink name="Температурные листы" action="/entityParentList-stac_temperatureCurve" title="Показать все температурные листы" guid="df23-45ca-43cc-826d-5hf5dd" params="id" />
        <msh:sideLink roles="/Policy/Mis/MedCase/MedService/View" name="Услуги" params="id" action="/entityParentList-smo_medService" title="Показать все услуги" guid="df23-45a26d-5hfd" />
        
        <msh:sideLink roles="/Policy/Mis/MedCase/Transfusion/View" name="Переливание"     
    	params="id"  action='/entityParentList-trans_transfusion'  key='Alt+8' 
    	title='Переливание трансфузионных сред'/>
    	
    	<msh:sideLink roles="/Policy/Mis/MedCase/Stac/Ssl/SurOper/View" name="Операции"  
    	params="id"  action='/entityParentList-stac_surOperation'  key='Alt+7' title='Операции'
    	styleId="stac_surOperation"
    	/>

        <mis:sideLinkForWoman classByObject="MedCase" id="${param.id}" params="id" action="/entityParentList-preg_childBirth" name="Описание родов" title="Показать описание родов" roles="/Policy/Mis/Pregnancy/ChildBirth/View"/>
    <msh:sideLink roles="/Policy/Mis/Inspection/View" name="Осмотры"     
    	params="id"  action='/entityParentList-preg_inspection'  key='Alt+0' 
    	title='Медицинские осмотры'/>
      </msh:sideMenu>
      <msh:sideMenu title="Печать">
      	<tags:stac_documentsPrint name="Docum" roles="/Policy/Mis/MedCase/Stac/Ssl/Slo/Print/ConsentImplant" title="Документов" medCase="${param.id}"/>
      	<msh:sideLink roles="/Policy/Mis/MedCase/Protocol/View" name="Дневников по СЛО" action="/printProtocolsBySLO.do?stAll=selected&medcase=${param.id}" params="id"/>
      	<msh:sideLink roles="/Policy/Mis/MedCase/MedService/View" name="Мед.услуг по СЛО" action="/printMedServiciesBySMO.do?medcase=${param.id}" params="id"/>
      	<%--<msh:sideLink roles="/Policy/Mis/MedCase/Stac/Ssl/Slo/Print/ConsentImplant" name="Согласие на установку имплантанта" action="/print-consentImplant.do?s=HospitalPrintService&m=printConsentBySlo&id=${param.id}" params="id"/>
      	<msh:sideLink roles="/Policy/Mis/MedCase/Stac/Ssl/Slo/Print/ConsentOperation" name="Согласие на медицинское (операционное) вмешательство" action="/print-consentOperation.do?s=HospitalPrintService&m=printConsentBySlo&id=${param.id}" params="id"/>
      	<msh:sideLink roles="/Policy/Mis/MedCase/Stac/Ssl/Slo/Print/ConsentTransfusion" name="Согласие на переливание крови" action="/print-consentTransfusion.do?s=HospitalPrintService&m=printConsentBySlo&id=${param.id}" params="id"/>
      	<msh:sideLink roles="/Policy/Mis/MedCase/Stac/Ssl/Slo/Print/ConsentTreatment" name="Согласие с обшим планом лечения и обследования" action="/print-consentTreatment.do?s=HospitalPrintService&m=printConsentBySlo&id=${param.id}" params="id"/>
      	<msh:sideLink roles="/Policy/Mis/MedCase/Stac/Ssl/Slo/Print/RejectionIntervention" name="Отказ от медицинского лечения" action="/print-rejectionIntervention.do?s=HospitalPrintService&m=printConsentBySlo&id=${param.id}" params="id"/>
      	<msh:sideLink roles="/Policy/Mis/MedCase/Stac/Ssl/Slo/Print/RejectionIntervention1" name="Отказ от стац. лечения" action="/print-rejectionIntervention1.do?s=HospitalPrintService&m=printConsentBySlo&id=${param.id}" params="id"/>
      	<msh:sideLink roles="/Policy/Mis/MedCase/Stac/Ssl/Slo/Print/RejectionOperation1" name="Отказ от операции" action="/print-rejectionOperation1.do?s=HospitalPrintService&m=printConsentBySlo&id=${param.id}" params="id"/>
      	<msh:sideLink roles="/Policy/Mis/MedCase/Stac/Ssl/Slo/Print/DirectVK" name="Направление на ВК" action="/print-directVK.do?s=HospitalPrintService&m=printConsentBySlo&id=${param.id}" params="id"/>
      	 --%>
      </msh:sideMenu>
      </msh:ifNotInRole>
      <msh:sideMenu title="Администрирование">
      	<msh:sideLink confirm="Вы точно хотите объединить несколько СЛО?" name="Объединить со след. СЛО" action=".javascript:unionSloWithNextSlo()" roles="/Policy/Mis/MedCase/Stac/Ssl/Slo/UnionSlo"/>
    	<tags:mis_changeServiceStream name="CSS" service="HospitalMedCaseService" title="Изменить поток обслуживания" roles="/Policy/Mis/MedCase/Stac/Ssl/Slo/ChangeServiceStream" />
      </msh:sideMenu>
      <msh:sideMenu title="Перейти" guid="ad80d37d-5a0b-44e3-a4ae-3df85de3d1c3">
        <msh:sideLink styleId="viewShort" action='/javascript:getDefinition("js-smo_draftProtocol-list.do?short=Short", null);' name='Черновики' title="Просмотр черновиков специалиста" key="ALT+4" roles="/Policy/Mis/MedCase/Protocol/View" />
        <msh:sideLink params="id" action="/entityParentListRedirect-stac_slo" name="⇧Cписок СЛО" roles="/Policy/Mis/MedCase/Stac/Ssl/Slo/View" guid="f6a4b395-ccee-4db6-aad7-9bc15aa2f7b8" title="Перейти к списку случаев лечения в отделении" />
	    <msh:sideLink 
		        roles="/Policy/Mis/MedCase/Stac/Journal/ByDepartmentAdmission" 
		        action="/stac_journalByDepartmentAdmission" name="Журнал по направленным в отделение" />
	    <msh:sideLink
		         roles="/Policy/Mis/MedCase/Stac/Journal/CurrentByUserDepartment"
		         action="/stac_journalCurrentByUserDepartment" name="Журнал по состоящим в отделение пациентам" />
 	    <msh:sideLink 
		        roles="/Policy/Mis/MedCase/Stac/Journal/ByCurator" 
		        action="/stac_journalByCurator" name="Журнал по лечащему врачу" />  

      </msh:sideMenu>
    </msh:ifFormTypeIsView>
  </tiles:put>
  <tiles:put name="body" type="string">
    <!-- 
    	  - Случай стационарного лечения в отделении
    	  -->
    <msh:form action="/entityParentSaveGoView-stac_slo.do" defaultField="dateStart" guid="be2c889f-ed1d-4a2b-9cda-9127e9d94885">
      <msh:hidden property="id" guid="d10f460a-e434-45a5-90f0-b0a7aed00ec6" />
      <msh:hidden property="prevMedCase" guid="710eb92b-fc3f-4390-b32df6837280" />
      <msh:hidden property="parent" guid="710eb92b-fc3f-4b44-9390-b32df6837280" />
      <msh:hidden property="patient" guid="9d908e88-e051-4d0a-8da6-3f5f4b226493" />
      <msh:hidden property="saveType" guid="bd322f07-c944-4587-a963-a09db2b93caf" />
      <msh:hidden property="lpuAndDate" guid="9cc5ff9f-b68c-423a-be34-50ebeecf4b18" />
      <msh:hidden property="lpu" guid="756525c0-3c91-41da-b2ba-27ebdbdc001b" />
      <msh:hidden property="dateFinish"/>
      <msh:hidden property="dischargeTime"/>
      <msh:hidden property="transferDate"/>
      <msh:hidden property="transferTime"/>
      <msh:hidden property="transferDepartment"/>
      <msh:hidden property="targetHospType"/>
      <msh:panel guid="d1cd0310-bf53-4ce1-9dd5-06388b51ec01" colsWidth="5%,5%,5%">
      <msh:ifFormTypeAreViewOrEdit formName="stac_sloForm">
      	<msh:row >
      		<msh:label property="statCardBySLS" label="Номер стат.карты" labelColSpan="1"/>
      		<td colspan="2">
      		<msh:link action="entityParentListRedirect-stac_slo.do?id=${param.id}" roles="/Policy/Mis/MedCase/Stac/Ssl/Slo/View">⇧Cписок СЛО</msh:link>
      		</td>
      	</msh:row>
      	</msh:ifFormTypeAreViewOrEdit>
        <msh:separator label="Переведен из отделения" colSpan="6" guid="d4313623-45ca-43cc-826d-bc1b66526744" />
        <msh:row guid="f244aba5-68fb-4ccc-9982-7b4480cca147">
          <msh:autoComplete viewAction="entityParentView-stac_slo.do" shortViewAction="entityShortView-stac_slo.do" parentId="stac_sloForm.parent" viewOnlyField="true"  vocName="sloBySls" property="prevMedCase" label="СЛО" fieldColSpan="6" horizontalFill="true" guid="109f7264-23b2-42c0-ba47-65d90747816c" size="30" />
        </msh:row>
        <msh:separator label="Поступление в отделение" colSpan="6" guid="d4313623-45ca-43cc-826d-bc1b66526744" />
	        <msh:row guid="d6321f29-4e95-42a5-9063-96df480e55a8">
	          <msh:textField property="dateStart" label="Дата поступления" />
	          <msh:textField property="entranceTime" label="время" />
	        </msh:row>

        <msh:row guid="f244aba5-68fb-4ccc-9982-7b4480cca147">
          <msh:autoComplete parentId="stac_sloForm.lpu" vocName="vocLpuHospOtd" property="department" label="Отделение" fieldColSpan="6" horizontalFill="true" guid="109f7264-23b2-42c0-ba47-65d90747816c" size="30" />
        </msh:row>
        <msh:row guid="f2-68fb-4ccc-9982-7b4480cca147">
          <msh:autoComplete vocName="serviceStreamByDepAndDate" property="serviceStream" label="Поток обслуживания" fieldColSpan="6" horizontalFill="true" guid="109f7264-23b216c" />
        </msh:row>
        <msh:row guid="f2aba5-68fb-4ccc-9982-7b4480cmca147">
          <msh:autoComplete vocName="bedFundByDepAndStreamAndDate" property="bedFund" label="Профиль коек" fieldColSpan="6" horizontalFill="true" guid="1064-23b2-42c0-ba47-65d90747816c" size="30" />
        </msh:row>
        <msh:ifNotInRole roles="/Policy/Mis/MedCase/Stac/Ssl/ShortEnter">
        <msh:row guid="9b781235-66ad-4f9d-991b-afb9aedfb7a8">
          <%-- <msh:textField label="№палаты" property="roomNumber" guid="fff1dd1d-b7a5-4fe2-899b-3292ec9f3fad" /> --%>
          <msh:autoComplete property="roomNumber" vocName="hospitalRoomByLpu" label="№палаты" parentId="stac_sloForm.department"/>
          <msh:autoComplete property="bedNumber" vocName="hospitalBedByRoom" label="№ койки" parentAutocomplete="roomNumber"/>
          <%-- <msh:autoComplete property="roomType" vocName="vocRoomType" label="Тип палаты" horizontalFill="true"/> --%>
         </msh:row>
         <msh:row>
         <%-- <msh:textField label="№ койки" property="bedNumber" guid="ed0d86e6-71b9-44f6-9c3a-213f5e8465c8" />  --%>
        </msh:row>
        </msh:ifNotInRole>
        <msh:ifInRole roles="/Policy/Mis/MedCase/Stac/Ssl/ShortEnter">
        <msh:hidden property="roomNumber"/>
        <msh:hidden property="roomType"/>
        <msh:hidden property="bedNumber"/>
        </msh:ifInRole>
        <msh:row>
        	<msh:checkBox label="Провизорность" property="provisional" guid="dh88d59-3adb-4485-af94-cahb04f82b" />
        	<msh:checkBox label="Экстренно" property="emergency" guid="dhcahb04f82b" />
        </msh:row>
        <msh:row guid="1d32ce64-883b-4be9-8db1-a421709f4470">
          <msh:autoComplete vocName="workFunctionByLpu" parentId="stac_sloForm.department" property="ownerFunction" label="Лечащий врач" fieldColSpan="6" horizontalFill="true" guid="968469ce-dd95-40f4-af14-deef6cd3e4f3" viewAction="entitySubclassView-work_workFunction.do" size="30" />
        </msh:row>
        <msh:ifInRole roles="/Policy/Mis/MedCase/Stac/Ssl/Slo/OmcStandart">
        
        <msh:row>
        	<msh:autoComplete property="omcStandart" fieldColSpan="6" label="ОМС стандарт (врач)" horizontalFill="true" vocName="omcStandart"/>
        </msh:row>
        <msh:row>
        	<msh:autoComplete property="omcStandartExpert" fieldColSpan="6" label="ОМС стандарт (эксперт)" horizontalFill="true" vocName="omcStandart"/>
        </msh:row>
        </msh:ifInRole>
        <msh:ifNotInRole roles="/Policy/Mis/MedCase/Stac/Ssl/Slo/OmcStandart">
        <msh:hidden property="omcStandart"/>
        <msh:hidden property="omcStandartExpert"/>
        </msh:ifNotInRole>
        <msh:row>
        	<msh:autoComplete property="kindHighCare" fieldColSpan="6" label="Вид ВМП" horizontalFill="true" vocName="vocKindHighCare"/>
        </msh:row>
        <msh:row>
        	<msh:autoComplete property="methodHighCare" parentAutocomplete="kindHighCare" fieldColSpan="6" label="Метод ВМП" horizontalFill="true" vocName="vocMethodHighCare"/>
        </msh:row>
        <msh:row>
        	<msh:autoComplete vocName="vocIllnesPrimary" fieldColSpan="3" property="clinicalActuity" horizontalFill="true" label="Характер заболевания"/>
        </msh:row>
        <msh:row>
	        <msh:autoComplete vocName="vocIdc10" label="МКБ клин.диаг." property="clinicalMkb" fieldColSpan="3" horizontalFill="true"/>
        </msh:row>
        <msh:row>
    	    <msh:textField label="Клинический диагноз" property="clinicalDiagnos" fieldColSpan="3" horizontalFill="true"/>
        </msh:row>
        <msh:row>
	        <msh:autoComplete vocName="vocIdc10" label="МКБ-10 клин.диаг.соп." property="concomitantMkb" fieldColSpan="3" horizontalFill="true"/>
        </msh:row>
        <msh:row>
    	    <msh:textField label="Клин. диаг. сопут" property="concomitantDiagnos" fieldColSpan="3" horizontalFill="true"/>
        </msh:row>      
        <msh:ifFormTypeIsView formName="stac_sloForm">
        <msh:separator label="Перевод в другое отделение" colSpan="" guid="dd7185d0-e499-4307-9e58-6ef41d83c2b0" />
        <msh:row guid="a3509d1f-9324-4997-a7c3-6ca8f12a9347">
          <msh:textField viewOnlyField="true"  property="transferDate" label="Дата" guid="f8f5c912-00b8-4fd8-87b9-abe417212d78" />
          <msh:textField viewOnlyField="true" property="transferTime" label="Время" guid="c04ab410-42df-4f5b-b365-b4acf17a2616" />
        </msh:row>
        <msh:row guid="72adfc11-ef9b-47c0-8eb4-a23ee9e84ed8">
          <msh:autoComplete viewOnlyField="true" vocName="vocLpuHospOtd" property="transferDepartment" label="Отделение" fieldColSpan="6" horizontalFill="true" guid="f793944a-6afe-4c26-82f3-50532049a8bc" />
        </msh:row>
        <msh:row guid="f2a5-68fb-4ccc-9982-7b4447">
          <msh:autoComplete viewOnlyField="true" vocName="vocHospType" property="targetHospType" label="Куда переведен" fieldColSpan="6" horizontalFill="true" guid="10964-23b2-42c0-ba47-6547816c" />
        </msh:row>
        <msh:separator label="Выписка (производится по СЛС)" colSpan="" guid="a5bd9711-b033-4104-b794-0ca3ebc8b827" />
        <msh:row guid="21b4ac48-1773-410d-b85f-537680420aa4">
          <msh:textField property="dateFinish" label="Дата" viewOnlyField="true" guid="bb7b87a8-c542-47ef-93b6-91106abf9f19" />
          <msh:textField property="dischargeTime" label="Время" viewOnlyField="true" guid="a8bfc5ac-8d19-4656-a30b-bd87da1918df" />
        </msh:row>
        </msh:ifFormTypeIsView>
        <msh:row>
        	<msh:separator label="Дополнительно" colSpan="4"/>
        </msh:row>
        </msh:panel>
        <msh:panel>
        <msh:row>
        	<msh:label property="createDate" label="Дата создания"/>
        	<msh:label property="createTime" label="время"/>
            <msh:label property="username" label="пользователь" guid="2258d5ca-cde5-46e9-a1cc-3ffc278353fe" />
        </msh:row>
        <msh:row>
        	<msh:label property="editDate" label="Дата редак."/>
        	<msh:label property="editTime" label="время"/>
          	<msh:label property="editUsername" label="пользователь" guid="2258d5ca-cde5-46e9-a1cc-3ffc278353fe" />
        </msh:row>
        <msh:submitCancelButtonsRow colSpan="3" guid="6bece8ec-9b93-4faf-b729-851f1447d54f" />
      </msh:panel>
    </msh:form>
    <msh:ifFormTypeIsView formName="stac_sloForm" guid="48eb9700-d07d-4115-a476-a5a5e">
      <msh:ifInRole roles="/Policy/Mis/MedCase/Protocol/View" guid="932601e0-0d99-4b63-8f44-2466f6e91c0f">
        <msh:section title="Дневники специалистов (последние 50). 
        <a href='entityParentPrepareCreate-smo_visitProtocol.do?id=${param.id }'>Добавить</a>&nbsp;&nbsp;
        <a href='entityParentList-smo_visitProtocol.do?id=${param.id }'>Полный список дневников</a>&nbsp;&nbsp;
        <a href='printProtocolsBySLO.do?medcase=${param.id }&id=${param.id}&stAll=selected'>Печать (весь список)</a>&nbsp;&nbsp;
        <a href='printProtocolsBySLO.do?medcase=${param.id }&id=${param.id}&stNoPrint=selected'>Печать (список нераспеч.)</a>
        " guid="1f21294-8ea0-4b66-a0f3-62713c1">
          <%--
<ecom:parentEntityListAll formName="smo_visitProtocolForm" attribute="protocols" guid="30260c-7369-4ec7-a67c-882abcf" />          
          <msh:table hideTitle="true" idField="id" name="protocols" action="entityParentView-smo_visitProtocol.do" guid="d0267-9aec-4ee0-b20a-4f26b37">
            <msh:tableColumn columnName="Дата" property="dateRegistration" guid="b85fe4-b1cb-4320-aa85-014d26" cssClass="preCell" />
            <msh:tableColumn columnName="Время" property="timeRegistration" guid="b85b1cb-4320-aa85-014d26" cssClass="preCell" />
            <msh:tableColumn columnName="Запись" property="record" guid="bd2fe4-b1cb-4320-aa85-02bd26" cssClass="preCell" />
            <msh:tableColumn columnName="Специалист" property="specialistInfo" guid="bd2fe4-b1cb-4320-aa85-02bd26" cssClass="preCell" />
          </msh:table>--%>
      <ecom:webQuery maxResult="50" name="protocols"  nativeSql="
      select d.id as did, d.dateRegistration as ddateRegistration
      , d.timeRegistration as dtimeRegistration, d.record as drecord 
     , vwf.name||' '||pw.lastname||' '||pw.firstname||' '||pw.middlename as doctor
      from MedCase slo
      left join MedCase aslo on aslo.parent_id=slo.parent_id
      left join Diary as d on aslo.id=d.medCase_id
      left join WorkFunction wf on wf.id=d.specialist_id
      left join Worker w on w.id=wf.worker_id
      left join Patient pw on pw.id=w.person_id
      
      left join VocWorkFunction vwf on vwf.id=wf.workFunction_id
            	where slo.id='${param.id}' and d.DTYPE='Protocol'
            	order by  d.dateRegistration desc,  d.timeRegistration desc"/>

          <msh:table hideTitle="false" idField="1" name="protocols" action="entityParentView-smo_visitProtocol.do" guid="d0267-9aec-4ee0-b20a-4f26b37">
                    <msh:tableColumn columnName="#" property="sn"/>
                    <msh:tableColumn columnName="Дата" property="2"/>
                    <msh:tableColumn columnName="Время" property="3"/>
                    <msh:tableColumn columnName="Протокол" property="4" cssClass="preCell"/>
                    <msh:tableColumn columnName="Специалист" property="5"/>
                </msh:table>
          
        </msh:section>
        
      </msh:ifInRole>
      <msh:ifInRole roles="/Policy/Mis/MedCase/Stac/Ssl/Diagnosis/View" guid="b0ceb3e4-a6a2-41fa-be6b-ea222196a33d">
       <%--  <ecom:parentEntityListAll formName="stac_diagnosisForm" attribute="diagnosis" guid="302c-7369-4ec7-a67c-882abcf" />
		--%>
		<ecom:webQuery name='diagnosis' nativeSql="select d.id as did, d.establishDate as destablishDate, vrt.name as vrtinfo
		, vpd.name as vpdname, d.name as dname, mkb.code
		,vwf.name|| ' '||wp.lastname||' '||wp.firstname||' '||wp.middlename as doctor
		from Diagnosis d
		left join VocDiagnosisRegistrationType vrt on vrt.id=d.registrationType_id
		left join VocPriorityDiagnosis vpd on vpd.id=d.priority_id
		left join VocIdc10 mkb on mkb.id=d.idc10_id
		left join WorkFunction wf on wf.id=d.medicalWorker_id
		left join VocWorkFunction vwf on vwf.id=wf.workFunction_id
		left join Worker w on w.id=wf.worker_id
		left join Patient wp on wp.id=w.person_id
		where d.medcase_id='${param.id}'
		"/>
		<%-- 
        <msh:section title="Диагнозы. <a href='entityParentPrepareCreate-stac_diagnosis.do?id=${param.id }'> Добавить новый диагноз</a>" guid="1f214-8ea0-4b66-a0f3-62713c1">
          <msh:table name="diagnosis" action="entityParentView-stac_diagnosis.do" idField="id" guid="b621e361-1e0b-4ebd-9f58-b7d916">
            <msh:tableColumn columnName="Дата установления" property="establishDate" guid="0694f6a7-ed40-4ebf-a274-1efd6901cfe4" />
            <msh:tableColumn columnName="Тип регистрации" property="registrationTypeInfo" guid="6682eeef-105f-43a0-be61-30a865f27972" />
            <msh:tableColumn columnName="Приоритет" property="priorityDiagnosisText" guid="6682eeef-12" />            
            <msh:tableColumn columnName="Наименование" property="name" guid="6682eeef-105f-43a0-be61-30a865f27972" />
            <msh:tableColumn columnName="Код МКБ" property="idc10Text" guid="f34e1b12-3392-4978-b31f-5e54ff2e45bd" />
            <msh:tableColumn columnName="Специалист" property="workerInfo" guid="f31b12-3392-4978-b31f-5e54ff2e45" />
          </msh:table>
        </msh:section>
        --%>
        <msh:section title="Диагнозы. <a href='entityParentPrepareCreate-stac_diagnosis.do?id=${param.id }'> Добавить новый диагноз</a>" guid="1f214-8ea0-4b66-a0f3-62713c1">
          <msh:table name="diagnosis" action="entityParentView-stac_diagnosis.do" idField="1" guid="b621e361-1e0b-4ebd-9f58-b7d916">
          	<msh:tableColumn property="sn" columnName="#"/>
            <msh:tableColumn columnName="Дата установления" property="2" guid="0694f6a7-ed40-4ebf-a274-1efd6901cfe4" />
            <msh:tableColumn columnName="Тип регистрации" property="3" guid="6682eeef-105f-43a0-be61-30a865f27972" />
            <msh:tableColumn columnName="Приоритет" property="4" guid="6682eeef-12" />            
            <msh:tableColumn columnName="Наименование" property="5" guid="6682eeef-105f-43a0-be61-30a865f27972" />
            <msh:tableColumn columnName="Код МКБ" property="6" guid="f34e1b12-3392-4978-b31f-5e54ff2e45bd" />
            <msh:tableColumn columnName="Специалист" property="7" guid="-3392-4978-b31f-5e54ff2e45" />
          </msh:table>
        </msh:section>
      </msh:ifInRole>
      <msh:ifInRole roles="/Policy/Mis/MedCase/Stac/Ssl/SurOper/View">
          <ecom:webQuery name="allSurgOper" nativeSql="select so.id
          ,to_char(so.operationDate,'dd.mm.yyyy')||' '||coalesce(cast(so.operationTime as varchar(5)),'') as soperationTime
          ,ms.code||' '||ms.name as voname
          , d.name as whoIs  
          , vwf.name||' '||wp.lastname||' '||wp.firstname||' '||wp.middlename as doctor
          ,substring(so.operationText,1,100)||' ...' as operationText
          from SurgicalOperation as so 
          left join MedService ms on ms.id=so.medService_id
          left join medcase parent on parent.id=so.medcase_id 
          
      		left join MedCase aslo on aslo.id=so.medCase_id
      		left join MedCase slo on slo.parent_id=aslo.parent_id
          left join MisLpu d on d.id=so.department_id 
          left join WorkFunction wf on wf.id=so.surgeon_id
          left join Worker w on w.id=wf.worker_id
          left join VocWorkFunction vwf on vwf.id=wf.workFunction_id
          left join Patient wp on wp.id=w.person_id
          where  
           slo.id=${param.id}
          order by so.operationDate
          "/>
    <msh:tableNotEmpty name="allSurgOper">
	    <msh:section title="Хирургические операции " createUrl="entityParentPrepareCreate-stac_surOperation.do?id=${param.id}"
	    createRoles="/Policy/Mis/MedCase/Stac/Ssl/SurOper/Create">
	    	<msh:table viewUrl="entityShortView-stac_surOperation.do"
	    	editUrl="entityParentEdit-stac_surOperation.do"  
	    	name="allSurgOper" action="entityParentView-stac_surOperation.do" idField="1">
	    		<msh:tableColumn columnName="#" property="sn"/>
	    		<msh:tableColumn columnName="Дата и время" property="2"/>
	    		<msh:tableColumn columnName="Операция" property="3"/>
	    		<msh:tableColumn columnName="Хирург" property="5"/>
	    		<msh:tableColumn cssClass="preCell" property="6" columnName="Протокол операции"/>
	    		<msh:tableColumn columnName="Отделение" property="4"/>
	    	</msh:table>
	    </msh:section>
    </msh:tableNotEmpty>      
      </msh:ifInRole>
      
      <msh:ifInRole roles="/Policy/Mis/MedCase/Transfusion/View">
      	<ecom:parentEntityListAll attribute="transfusions" formName="trans_transfusionForm"/>
      	<msh:tableNotEmpty name="transfusions">
      	<msh:section title="Переливание">
      		<msh:table name="transfusions" action="entitySubclassView-trans_transfusion.do" idField="id">
		      <msh:tableColumn columnName="Номер в журнале" property="journalNumber" guid="ed7e6ec7-524e-4b87-8b2c-5a722792a123" />
		      <msh:tableColumn columnName="Трансфузионная среда" property="information" guid="c4b30e10-9ca0-42b1-94fb-88cf0f7afa2e" />
		      <msh:tableColumn columnName="Дата приготовления" property="preparationDate" guid="1ef2e314-6eb6-4c85-be47-ca392566d371" />
		      <msh:tableColumn columnName="Изготовитель" property="preparator" guid="dk29-5653-4920-bb78-168ha34" />
		      <msh:tableColumn columnName="Дата начала" property="startDate" guid="2976f5c7-3844-4ae2-be91-2a395cae0f1f" />
		      <msh:tableColumn columnName="Доза" property="doze" guid="d9642df9-5653-4920-bb78-1622cbeefa34" />
		      <msh:tableColumn columnName="Исполнитель" property="executorInfo" guid="8e832f90-6905-44cf-952e-76495689c35b" />
      		</msh:table>
      		</msh:section>
      	</msh:tableNotEmpty>
      </msh:ifInRole>
      
      <msh:ifInRole roles="/Policy/Mis/Prescription/Prescript/View">
      	<msh:section title="Лист назначний " createUrl="entityParentPrepareCreate-pres_prescriptList.do?id=${param.id}"
      	createRoles="/Policy/Mis/Prescription/Prescript/Create"
      	>
	      	<%-- <msh:sectionTitle >Листы назначений. Добавить ЛН</msh:sectionTitle> --%>
	      	<msh:sectionContent>
	          <ecom:webQuery name="presLists" nativeSql="select pl.id as ilid
	          ,vwf.name||' '||wp.lastname||' '||wp.firstname||' '||wp.middlename as doctor
	          ,pl.comments as plcomments,pl.date as dldate
	          ,(select coalesce(to_char(min(p.planStartDate),'DD.MM.YYYY'),'нет даты начала')||'-'||coalesce(to_char(max(p.planEndDate),'dd.mm.yyyy'),'нет даты окончания') as pldatend 
	          from prescription p where prescriptionList_id=pl.id) from PrescriptionList pl left join WorkFunction wf on wf.id = pl.workFunction_id left join VocWorkFunction vwf on vwf.id=wf.workFunction_id left join worker w on w.id=wf.worker_id left join Patient wp on wp.id=w.person_id where pl.medCase_id =  '${param.id}'"/>
		      <msh:table name="presLists" action="entityParentView-pres_prescriptList.do" idField="1" guid="3c4adc65-cfce-4205-a2dd-91ba8ba87543">
			      <msh:tableColumn columnName="Назначил" property="2" guid="44482100-2200-4c8b-9df5-4f5cc0e3fe68" />
			      <msh:tableColumn columnName="Комментарии" property="3" guid="5c893448-9084-4b1a-b301-d7aca8f6307c" />
			      <msh:tableColumn columnName="Дата создания" property="4" guid="dbe4fc52-03f7-42af-9555-a4bee397a800" />
			      <msh:tableColumn columnName="Период актуальности" property="5"/>
		      </msh:table>
		    </msh:sectionContent>
      	</msh:section>
      </msh:ifInRole>
      <msh:ifInRole roles="/Policy/Mis/Pregnancy/ChildBirth/View">
      	<ecom:webQuery name="childBirth" nativeSql="select id,birthFinishDate from ChildBirth where medCase_id='${param.id}'"/>
      	<msh:section>
      		<msh:sectionTitle> 
	      		Роды <a href="entityParentPrepareCreate-preg_childBirth.do?id=${param.id}">Добавить роды</a>
	      		<a href="entityParentPrepareCreate-preg_neonatalNewBorn.do?id=${param.id}"> Добавить инф. о новорожденному</a>
      		</msh:sectionTitle>
      		<msh:sectionContent>
		      	<msh:table name="childBirth" action="entityParentView-preg_childBirth.do" idField="1">
		      		<msh:tableColumn property="sn" columnName="##"/>
		      		<msh:tableColumn property="2" columnName="Дата окончания родов" />
		      	</msh:table>
      		</msh:sectionContent>
      	</msh:section>
      </msh:ifInRole>
    </msh:ifFormTypeIsView>
  </tiles:put>
  <tiles:put name="title" type="string">
    <ecom:titleTrail mainMenu="Patient" beginForm="stac_sloForm" guid="fb43e71c-1ba9-4e61-8632-a6f4a72b461c" />
  </tiles:put>
  <tiles:put name="javascript" type="string">
        <script type="text/javascript">//var theBedFund = $('bedFund').value;
        function viewOtherVisitsByPatient(d) {
      	  //alert("js-smo_visit-infoShortByPatient.do?id="+$('patient').value) ;
      	  
      	  getDefinition("js-smo_visit-infoShortByPatient.do?id="+$('patient').value, null); 
        }
        function viewOtherDiagnosisByPatient(d) {
      	  getDefinition("js-smo_diagnosis-infoShortByPatient.do?id="+$('patient').value, null);
        }
        function viewOtherHospitalMedCase(d) {
      	  getDefinition("js-stac_ssl-infoShortByPatient.do?id="+$('patient').value, null);
        }
        function viewOtherExtMedserviceByPatient(d) {
      	  getDefinition("js-doc_externalMedservice-list.do?short=Short&id=${param.id}", null);
        }

      	function goDischarge() {
      		window.location = 'entityParentEdit-stac_sslDischarge.do?id='+$('parent').value+"&tmp="+Math.random() ;
      	}
      	function goTransfer() {
      		window.location = 'entityParentPrepareCreate-stac_slo.do?id='+$('parent').value+"&tmp="+Math.random() ;
      	}
      	</script>
<script type="text/javascript" src="./dwr/interface/HospitalMedCaseService.js">/**/</script>
	<msh:ifFormTypeIsView formName="stac_sloForm">
		<script type="text/javascript">
		function unionSloWithNextSlo() {
			HospitalMedCaseService.unionSloWithNextSlo('${param.id}',{
		
	  			callback: function(aResult) {
	  				alert(aResult) ;
	  				window.location.reload() ;
	  			}
	  		}) ;
		}
		</script>
	</msh:ifFormTypeIsView>  
    <msh:ifFormTypeIsNotView formName="stac_sloForm" guid="518fe547-aed9-be2229f04ba3">
      <script type="text/javascript">//var theBedFund = $('bedFund').value;
      if (+$('prevMedCase').value==0) {
    	  $('serviceStreamName').select() ;
    	  $('serviceStreamName').focus() ;
      }
      var lpuDate ;
       
  		try {
	    if (clinicalMkbAutocomplete) clinicalMkbAutocomplete.addOnChangeCallback(function() {
	      	 	setDiagnosisText('clinicalMkb','clinicalDiagnos');
	    });} catch(e) {}
  		try {
	    if (concomitantMkbAutocomplete) concomitantMkbAutocomplete.addOnChangeCallback(function() {
	      	 	setDiagnosisText('concomitantMkb','concomitantDiagnos');
	    });} catch(e) {}
  		function setDiagnosisText(aFieldMkb,aFieldText) {
  			var val = $(aFieldMkb+'Name').value ;
  			var ind = val.indexOf(' ') ;
  			//alert(ind+' '+val)
  			if (ind!=-1) {
  				if ($(aFieldText).value=="") $(aFieldText).value=val.substring(ind+1) ;
  			}
  		}
      
      
      
      
      	function goDischarge() {
      		window.location = 'entityParentEdit-stac_sslDischarge.do?id='+$('parent').value+"&tmp="+Math.random() ;
      	}
      	$('lpuAndDate').value = (+$('department').value) +"#"+(+$('serviceStream').value)+"#" +$('dateStart').value;
      	lpuDate = (+$('department').value) +"#"+$('dateStart').value  ;
      	bedFundAutocomplete.setParentId($('lpuAndDate').value) ;
      	serviceStreamAutocomplete.setParentId(lpuDate) ;
      	kindHighCareAutocomplete.setParentId(+$('serviceStream').value) ;
      	//bedFundAutocomplete.setVocId(theBedFund);
      	//alert(departmentAutocomplete) ;
      	//transferDepartmentAutocomplete.setParentId($('lpu').value) ;
      	
      	if (bedFundAutocomplete) bedFundAutocomplete.setParentId($('lpuAndDate').value) ;
      	serviceStreamAutocomplete.addOnChangeCallback(function() {
      			$('kindHighCare').value = "" ;
      	 		$('kindHighCareName').value = "" ;
      	 		kindHighCareAutocomplete.setParentId(+$('serviceStream').value) ;
      	 	
      	 	updateLpuAndDate() ;
      	 	updateBedFund() ;
      	 	
      	 });
      	updateLpuAndDate() ;
      	 function updateLpuAndDate() {
      		 //var serviceStream=+$('serviceStream').value ;
           	$('lpuAndDate').value = (+$('department').value) +"#"+(+$('serviceStream').value)+"#" +$('dateStart').value;
          	lpuDate = (+$('department').value) +"#"+$('dateStart').value  ;
      	 	//alert("lpuAndDate"+$('lpuAndDate').value) ;
      	 	lpuDate = (+$('department').value) +"#"+$('dateStart').value ;
      	 	bedFundAutocomplete.setParentId($('lpuAndDate').value) ;
      	 	serviceStreamAutocomplete.setParentId(lpuDate) ;
      	 	//var id = $('bedFund').value ;
      	 	//bedFundAutocomplete.setVocId(id);
      	 }
      	 function updateBedFund() {
      		HospitalMedCaseService.getDefaultBedFundBySlo($('parent').value
      				, $('department').value, $('serviceStream').value
      				, $('dateStart').value,{
      			callback: function(aResult) {
      				var res = aResult.split('#') ;

      				if (+res[0]!=0) {
      					$('bedFund').value = res[0] ; 
      					$('bedFundName').value = res[1] ; 
      				} else {
      		      	 	$('bedFund').value='0';
      		      	 	$('bedFundName').value='';
      				}
      			}
      		}) ;

      	 }
      	 </script>
      	 	
      	 <msh:ifNotInRole roles="/Policy/Mis/MedCase/Stac/Ssl/OwnerFunction">
      	 	<script type="text/javascript">
      	 	try {
          		//departmentAutocomplete.setParentId($('lpu').value) ;
    	      	departmentAutocomplete.addOnChangeCallback(function() {
    	      		try {
    	      			roomNumberAutocomplete.setParentId($('department').value) ;
    	      			$('roomNumber').value='0' ;
    	      			$('roomNumberName').value='' ;
    	      		} catch(e) {}
    	      		var oldid = $('serviceStream').value ;
    	      		updateLpuAndDate() ;
    	      	 	ownerFunctionAutocomplete.setParentId($('department').value) ;
    	      		updateBedFund() ;
    	      		
    	      	 	var newid = $('serviceStream').value ;
    	      	 	if (oldid!=newid) {
    	      	 		$('kindHighCare').value = "" ;
    	      	 		$('kindHighCareName').value = "" ;
    	      	 		kindHighCareAutocomplete.setParentId(+newid) ;
    	      	 	}
    	      	 });
          	} catch (e) {
          	}
      	 	</script>
      	 </msh:ifNotInRole>
      	 <msh:ifInRole roles="/Policy/Mis/MedCase/Stac/Ssl/OwnerFunction">
      	 	<script type="text/javascript">
      	 	try {
          		//departmentAutocomplete.setParentId($('lpu').value) ;
    	      	departmentAutocomplete.addOnChangeCallback(function() {
    	      		updateLpuAndDate() ;
    	      		roomNumberAutocomplete.setParentId($('department').value) ;
    	      		$('roomNumber').value='0' ;
    	      		$('roomNumberName').value='' ;
    	      	 	ownerFunctionAutocomplete.setParentId($('department').value) ;
    	      		HospitalMedCaseService.getDefaultInfoBySlo($('parent').value
    	      				, $('department').value, $('serviceStream').value
    	      				, $('dateStart').value,{
    	      			callback: function(aResult) {
    	      				var res = aResult.split('#') ;
    	      				if (+res[0]!=0) {
    	      					$('ownerFunction').value = res[0] ; 
    	      					$('ownerFunctionName').value = res[1] ; 
    	      				} else {
    	      		      	 	$('ownerFunction').value='0';
    	      		      	 	$('ownerFunctionName').value='';
    	      				}
    	      				if (+res[2]!=0) {
    	      					$('bedFund').value = res[2] ; 
    	      					$('bedFundName').value = res[3] ; 
    	      				} else {
    	      		      	 	$('bedFund').value='0';
    	      		      	 	$('bedFundName').value='';
    	      				}
    	      			}
    	      		}) 
    	      	 });
          	} catch (e) {
          	}
      	 	</script>
      	 </msh:ifInRole>
    </msh:ifFormTypeIsNotView>
  </tiles:put>
</tiles:insert>

