<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@ taglib uri="http://struts.apache.org/tags-tiles" prefix="tiles"%>
<%@ taglib uri="http://www.nuzmsh.ru/tags/msh" prefix="msh"%>
<%@ taglib uri="/WEB-INF/mis.tld" prefix="mis"%>

<tiles:insert page="/WEB-INF/tiles/main${param.short}Layout.jsp" flush="true">

	<tiles:put name='title' type='string'>
		<msh:title mainMenu="Journals">Журналы</msh:title>
	</tiles:put>

	<tiles:put name='body' type='string'>
		<table class='mainMenu'>
			<tr>
				<td class='col1'>
				<div class='menu'>
				<h2>Сообщения</h2>
				<ul style='list-style: none outside none; '>
					<li><msh:link roles='/Policy/Mis/Journal/InfectiousMessages' action="journal_infectMessage.do">
                            Журнал экстренных сообщений об инфекции
                        </msh:link></li>
					<li><msh:link isReport="true" roles="/Policy/Mis/Journal/CriminalMessages" action="journal_militiaMessage.do">
                             Журнал сообщений в полицию
                        </msh:link></li>
					<li><msh:link isReport="true" roles="/Policy/Mis/Journal/263injunction" action="stac_direct_in_fond.do">
                             263 приказ
                        </msh:link></li>
					<li><msh:link isReport="true" roles="/Policy/Mis/MedCase/Stac/Journal/AddressList" action="stac_address_list.do">
                             Адресные листки
                        </msh:link></li>
                        
				</ul>
				</div>
				
				</td><td style='padding-left: 5em'>
					<div class='menu'>
					<h2>Общие</h2>
					<ul style='list-style: none outside none; '>
						<li><msh:link action="journal_vaccination.do" roles="/Policy/Mis/Journal/Vaccination">
	                            Журнал прививок
	                        </msh:link></li>
						<li><msh:link action="journal_prescription.do" roles="/Policy/Mis/Journal/Prescription">
	                            Журнал назначений
	                        </msh:link></li>
						<li><msh:link action="journal_serviceMedCase.do" roles="/Policy/Mis/Journal/MedService">
	                            Журнал услуг
	                        </msh:link></li>
						<li><msh:link isReport="true" action="journal_nationality_smo.do" roles="/Policy/Mis/MedCase/Visit/ReportNationality">
	                            Журнал обращений с группировкой по гражданству (475-Пр)
	                        </msh:link></li>
						<li><msh:link isReport="true" action="journal_nationality_ukraine.do" roles="/Policy/Mis/MedCase/Visit/ReportNationality">
	                            Журнал обращений с группировкой по гражданству (только украинцы)
	                        </msh:link></li>
	                   <li><msh:link isReport="true" action="stac_journalRepeatCaseByHospital_list" roles="/Policy/Mis/Journal/RepeatCase">
	                   		Повторные случаи мед. обслуживания
	                   		</msh:link></li>
	                   <li>
	                   		<msh:link isReport="true" action="journal_doc_externalMedService.do" roles="/Policy/Mis/MedCase/Document/External/Medservice/View,/Policy/Mis/MedCase/Stac/Journal/ExternalMedservice">
	                   		Внеш. лаборатория</msh:link>
	                   </li>
	                   <li>
	                   		<msh:link action="http://192.168.10.25:57772/csp/riams/omceconreportlist.csp?CacheUserName=guest&CachePassword=guest&tmp=" roles="/Policy/Mis/Journal/AnalysisWorkDepartment">
	                   		Отчеты по экономической документации</msh:link>
	                   </li>
	                   <li>
	                   		<msh:link isReport="true" action="mis_report_16vn.do" roles="/Policy/Mis/Journal/Report16vn">
	                   		Отчет 16ВН</msh:link>
	                   </li>
	                   <li>
	                   		<msh:link isReport="true" action="pres_journal_intake.do" roles="/Policy/Mis/Journal/Prescription/LabSurvey/IntakeByCurrentDepartment">
	                   		Журнал забора биоматериала для лаборатории </msh:link>
	                   </li>
	                   <li>
	                   		<msh:link isReport="true" action="pres_journal_intake_transfer.do" roles="/Policy/Mis/Journal/Prescription/LabSurvey/TransferToLaboratory">
	                   		Журнал прием биоматериала в лаборатории</msh:link>
	                   </li>
	                   <li>
	                   		<msh:link isReport="true" action="pres_journal_by_department.do" roles="/Policy/Mis/Journal/Prescription/LabSurvey/DirectFromDepartment">
	                   		Отчет по направлениям на лаб. исследования (по отделениям)</msh:link>
	                   </li>
	                   <li>
	                   		<msh:link isReport="true" action="pres_journal.do" roles="/Policy/Mis/Journal/Prescription/LabSurvey/Control">
	                   		Отчет по направлениям на лаб. исследования (по отделениям)</msh:link>
	                   </li>
					</ul>
					</div>
				</td>
			</tr>
			<tr>
				<td style='padding-left: 5em'>
				<div class='menu'>
					<h2>Поликлиника</h2>
					<ul style='list-style: none outside none; '>
						<li><msh:link isReport="true" action="visit_f039_list.do" roles="/Policy/Mis/MedCase/Visit/Report039">
	                            039 форма
	                        </msh:link></li>
						<li><msh:link isReport="true" action="visit_report_service.do" roles="/Policy/Mis/MedCase/Visit/ReportService">
	                            Отчет по услугам
	                        </msh:link></li>
						<li><msh:link isReport="true" action="smo_journal_closeSpo.do" roles="/Policy/Mis/MedCase/Visit/CloseSpo">
	                            Закрытые СПО
	                        </msh:link></li>
						<li><msh:link isReport="true" action="smo_journal_openSpo.do" roles="/Policy/Mis/MedCase/Visit/OpenSpo">
	                            Открытые СПО
	                        </msh:link></li>
						<li><msh:link isReport="true" action="visit_journal_direction.do" roles="/Policy/Mis/MedCase/Direction/Journal">
	                            Журнал направленных
	                        </msh:link></li>
						<li><msh:link isReport="true" action="journal_visits_list.do" roles="/Policy/Mis/MedCase/Visit/ReportVisits">
	                            Журнал обращений
	                        </msh:link></li>
						<li><msh:link isReport="true" action="journal_visit_diagnosis.do" roles="/Policy/Mis/MedCase/Visit/ReportDiagnosis">
	                            Журнал диагнозов по визитам
	                        </msh:link></li>
						<li><msh:link isReport="true" action="extDisp_journal_card.do" roles="/Policy/Mis/ExtDisp/Card/View">
	                            Реестр по диспансеризации
	                        </msh:link></li>
						<li><msh:link isReport="true" action="extDisp_journal_user.do" roles="/Policy/Mis/ExtDisp/Card/View">
	                            Свод по пользователям (по диспансеризации) 
	                        </msh:link></li>
						<li><msh:link isReport="true" action="mis_patient_by_age.do" roles="/Policy/Mis/ExtDisp/Card/View">
	                            Реестр пациентов по возрастам
	                        </msh:link></li>
						<li><msh:link isReport="true" action="mis_attachment.do" roles="/Policy/Mis/ExtDisp/Card/View">
	                            Отчет по прикрепленному населению
	                        </msh:link></li>
					</ul>
					</div>
				<div class='menu'>
					<h2>Роддом</h2>
					<ul style='list-style: none outside none; margin-bottom: 10px;  padding-left: 5px;'>
						<li><msh:link action="journal_roddom_histology.do" roles="/Policy/Mis/Pregnancy/Report/HistologyPlacenta">
	                            Журнал гистологий плацент
	                        </msh:link></li>
					</ul>
					</div>
				</td>			
				
				<td style='padding-left: 5em'>
					<div class='menu'>
					<h2>Стационар</h2>
					<ul style='list-style: none outside none; '>
						<li><msh:link isReport="true" action="stac_journalByHospital.do" roles="/Policy/Mis/MedCase/Stac/Journal/ByHospital">
	                            Журнал по поступившим и выбывшим стационара, а также отказам от госпитализаций 
	                        </msh:link></li>
						<li><msh:link action="stac_report_007.do" roles="/Policy/Mis/MedCase/Stac/Journal/Report007">
	                            007/у-02 форма
	                        </msh:link></li>
						<li><msh:link isReport="true" action="stac_report_13.do" roles="/Policy/Mis/MedCase/Stac/Journal/Report13">
	                            13 форма
	                        </msh:link></li>
						<li><msh:link isReport="true" action="stac_report_14.do" roles="/Policy/Mis/MedCase/Stac/Journal/Report14">
	                            14 форма
	                        </msh:link></li>
						<li><msh:link isReport="true" action="stac_report_016.do" roles="/Policy/Mis/MedCase/Stac/Journal/Report016">
	                            016/у-02 форма
	                        </msh:link></li>
						<li><msh:link action="stac_report_36.do" roles="/Policy/Mis/MedCase/Stac/Journal/Report36">
	                            36 форма
	                        </msh:link></li>
						<li><msh:link isReport="true" action="stac_everyday_report.do" roles="/Policy/Mis/MedCase/Stac/Journal/EveryDayReport">
	                            Ежедневный отчет
	                        </msh:link></li>
						<li><msh:link isReport="true" action="stac_report_direct_in_hospital.do" roles="/Policy/Mis/Journal/DirectByHospital">
	                            Отчет по направленным
	                        </msh:link></li>
						<li><msh:link isReport="true" action="journal_surOperation.do" roles="/Policy/Mis/Journal/SurgicalOperation">
	                            Журнал хирургических операций
	                        </msh:link></li>
						<li><msh:link isReport="true" action="stac_journal_surgical_help.do" roles="/Policy/Mis/Journal/SurgicalHelp">
	                            Журнал экстренной хирургической помощи (Форма 30), операции по поводу злокачественных новообразований
	                        </msh:link></li>
						<li><msh:link isReport="true" action="stac_resultPatient_list.do" roles="/Policy/Mis/Journal/ResultHospitalization">
	                            Журнал результатов госпитализации (в том числе по смерти)
	                        </msh:link></li>
	                    <li><msh:link isReport="true" roles="/Policy/Mis/MedCase/Stac/Journal/DeathCase" action="stac_deathCase_list">Журнал по смертности</msh:link></li>
						<li><msh:link isReport="true" action="stac_analysis_department_list.do" roles="/Policy/Mis/Journal/AnalysisWorkDepartment">
	                            Анализ работы отделений
	                        </msh:link></li>
						<li><msh:link isReport="true" action="stac_journalByCurator.do" roles="/Policy/Mis/MedCase/Stac/Journal/ByCurator">
	                            Журнал по лечащему врачу (СЛО)
	                        </msh:link></li>
						<li><msh:link isReport="true" action="stac_diagnosis_hospital.do" roles="/Policy/Mis/Journal/DiagnosisBySls">
	                            Журнал диагнозов по стационару (несколько диагнозов)
	                        </msh:link></li>
						<li><msh:link isReport="true" action="stac_diagnosis_by_slo_list.do" roles="/Policy/Mis/Journal/DiagnosisBySlo">
	                            Журнал диагнозов по отделениям (СЛО)
	                        </msh:link></li>
						<li><msh:link isReport="true" action="stac_diagnosis_by_slo_list.do" roles="/Policy/Mis/Journal/DiagnosisBySlo">
	                            Журнал диагнозов по стационару (СЛС)
	                        </msh:link></li>
	                    <li><msh:link  isReport="true" roles="/Policy/Mis/MedCase/Stac/Journal/StandartOmc" action="stac_report_standartOmc.do">
	                    		Журнал по стандартам (СЛО)
	                    	</msh:link></li>
						<li><msh:link  isReport="true" action="stac_journalOpenByHospital.do" roles="/Policy/Mis/MedCase/Stac/Journal/OpenningCaseByHospital">
	                            Журнал открытых СЛС (госпитализаций)
	                        </msh:link></li>
						<li><msh:link  isReport="true" action="stac_journalByDepartmentAdmission.do" roles="/Policy/Mis/MedCase/Stac/Journal/ByDepartmentAdmission">
	                            Журнал направленных пациентов по отделениям (неоформленных)
	                        </msh:link></li>
						<li><msh:link  isReport="true" action="stac_journalCurrentByUserDepartment.do" roles="/Policy/Mis/MedCase/Stac/Journal/CurrentByUserDepartment">
	                            По состоящим в отделениях пациентам (оформленных)
	                        </msh:link></li>
						<li><msh:link  isReport="true" action="stac_journalByCurator.do" roles="/Policy/Mis/MedCase/Stac/Journal/ByCurator">
	                            По состоящим в отделениях пациентам с разбивкой по леч.врачу (оформленных)
	                        </msh:link></li>
						<li><msh:link  isReport="true" action="stac_planning_hospitalizations.do" roles="/Policy/Mis/MedCase/Stac/Journal/PlanningHospitalizations">
	                            Планирование госпитализаций
	                        </msh:link></li>
						<li><msh:link  isReport="true" action="stac_journal_denied_without_diagnosis.do" roles="/Policy/Mis/MedCase/Stac/Journal/DeniedWithoutDiagnosis">
	                            Отказы от госпитализаций без диагнозов
	                        </msh:link></li>
						<li><msh:link  isReport="true" action="stac_journal_transfer_department.do" roles="/Policy/Mis/MedCase/Stac/Journal/DeniedWithoutDiagnosis">
	                            Переводы между отделениями
	                        </msh:link></li>
						<li><msh:link  isReport="true" action="stac_journal_direct_psych.do" roles="/Policy/Mis/MedCase/Stac/Journal/AdmissionOrderByPsych">
	                            Журнал по порядку поступления (добров., недобров.)
	                        </msh:link></li>
						<li><msh:link  isReport="true" action="stac_journal_compulsory_treatment_psych.do" roles="/Policy/Mis/MedCase/Stac/Journal/AdmissionOrderByPsych">
	                            Журнал по принудительному лечению
	                        </msh:link></li>
						<li><msh:link  isReport="true" action="stac_report_insCompany.do" roles="/Policy/Mis/MedCase/Stac/Journal/InsuranceCompany">
	                            Журнал по страховым компаниям
	                        </msh:link></li>
	                        <li><msh:link  isReport="true" action="blood_report.do" roles="/Policy/Mis/MedCase/Stac/Journal/Report14">
	                            Госпитализации с болезнями системы кровообращения
	                        </msh:link></li>
	                        <li><msh:link  isReport="true" action="mis_mortality_report.do" roles="/Policy/Mis/MedCase/Stac/Journal/Report14">
	                            Анализ летальности в отделениях
	                        </msh:link></li>
	                        <li><msh:link  isReport="true" action="stac_elections.do" roles="/Policy/Mis/MedCase/Stac/Journal/ReportElections">
	                            Выборы
	                        </msh:link></li>
					</ul>
					</div>
				</td>
			</tr>
			
		</table>
	</tiles:put>
</tiles:insert>
