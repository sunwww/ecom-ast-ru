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
					<li><msh:link roles='/Policy/Mis/MedCase/Visit/Report039' action="journal_foreignPatients.do">
						Отчет об оказанной помощи иногородними и иностранцам
					</msh:link></li>
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
					<li><msh:link isReport="true" roles="/Policy/Mis/Claim/Operator" action="all_claims.do">
                             Просмотр заявок
                        </msh:link></li>
					<li><msh:link isReport="true" roles="/Policy/Mis/Elections" action="js-mis_lpuArea-elections.do">
                             Отчетные формы к выборам
                        </msh:link></li>
					<li><msh:link isReport="true" roles="/Policy/Mis/Journal/ShortDiaryReport" action="stac_report_cases_short_protocol.do">
                             Отчет по коротким дневникам
                        </msh:link></li>
					<li><msh:link isReport="true" roles="/Policy/Mis/Journal/AbsentDiary" action="stac_report_cases_not_filled.do">
						Журнал по пациентам, у которых не заполнялись дневниковые записи
                        </msh:link></li>

				</ul>
				</div>
				
				</td><td style='padding-left: 5em'>
					<div class='menu'>
					<h2>Общие</h2>
					<ul style='list-style: none outside none; '>
						<li><msh:sideLink name="Список оборудования за отделением" action="/js-mis_medicalEquipment-listByDep"
									roles="/Policy/Mis/Prescription/Prescript/View"
	 										/></li>
	 
						<li><msh:link action="journal_vaccination.do" roles="/Policy/Mis/Journal/Vaccination">
	                            Журнал прививок
	                        </msh:link></li>
						<li><msh:link action="journal_prescription.do" roles="/Policy/Mis/Journal/Prescription">
	                            Журнал назначений
	                        </msh:link></li>
						<li><msh:link action="journal_serviceMedCase.do" roles="/Policy/Mis/Journal/MedService">
	                            Журнал услуг
	                        </msh:link></li>
	                    <li><msh:link isReport="true" action="journal_nationality_new.do" roles="/Policy/Mis/MedCase/Visit/ReportNationality">
	                            Журнал обращений с группировкой по гражданству (Приказ №47р от 19.01.2017)
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
	                   <li>
	                   		<msh:link isReport="true" action="lab_chief_report.do" roles="/Policy/Mis/Journal/Prescription/LabSurvey/TransferToLaboratory">
	                   		Журнал заведующего лабораторией</msh:link>
	                   </li>
						<li><msh:link isReport="true" action="reportKDL.do" roles="/Policy/MainMenu/LaboratoryJournal/ChiefLabJournal">
							Сводный отчёт по КДЛ
						</msh:link></li>
	                   <li>
	                   <msh:link isReport="true" action="/pres_journal" roles="/Policy/Mis/Journal/Prescription/LabSurvey/JournalPrescript">Журнал лабораторных назначений</msh:link>
	                   </li>
	                   <li>
	                   		<msh:link isReport="true" action="journal_kidney.do" roles="/Policy/Mis/MedCase/Visit/Report039">
	                   		Отчет по ХБП</msh:link>
	                   </li>
	                   <li>
	                   		<msh:link isReport="true" action="quality_card_journal.do" roles="/Policy/Mis/journal/QualityEstimationCard">
	                   		Журнал внутреннего контроля качества</msh:link>
	                   </li>
	                        <li><msh:link  isReport="true" action="mis_diagnosis_by_period.do" roles="/Policy/Mis/Journal/DiagnosisByPeriodF">
	                            Журнал смены диагноза по листу уточненных диагнозов F...
	                        </msh:link></li>
	                        <li><msh:link  isReport="true" action="mis_archive_journal.do" roles="/Policy/Mis/ArchiveCase/View">
	                          Журнал переданных в архив историй болезни
	                        </msh:link></li>
	                        <li><msh:link  isReport="false" action="move_to_archive.do" roles="/Policy/Mis/ArchiveCase/Create">
	                           Передача историй болезни в архив
	                        </msh:link></li>
	                        <li><msh:link  isReport="false" action="eln_count.do" roles="/Policy/Mis/ArchiveCase/Create">
	                           Количество сформированных ЭЛН
	                        </msh:link></li>
							<li><msh:link  isReport="false" action="allLn_count_report.do" roles="/Policy/Mis/ArchiveCase/Create">
								Количество ЛН по отделениям
							</msh:link></li>
                        <li><msh:link  isReport="false" action="reestrNaOplatyDogovorov.do" roles="/Policy/Mis/Journal/JasperReports/RegistryForPaymentForContracts">
                            Реестр на оплату для договоров
                        </msh:link></li>
						<li><msh:link  isReport="false" action="finPlan.do" roles="/Policy/Mis/Journal/JasperReports/ExecutionOfFinancialPlan">
							Выполнение финансового плана
						</msh:link></li>
						<li><msh:link  isReport="false" action="registriesForSMO.do" roles="/Policy/Mis/Journal/JasperReports/RegistriesForSMO">
							Реестры для СМО
						</msh:link></li>
						<li><msh:link  isReport="false" action="smo_deniedHospitelByAttach.do" roles="/Policy/Mis/ArchiveCase/Create">
							Отказы госпитализации по прикрепленным
						</msh:link></li>
						<li><msh:link  isReport="false" action="actRVKReport.do" roles="/Policy/Mis/MedCase/ActRVK/View">
							Отчёт по актам РВК
						</msh:link></li>
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
						<li><msh:link isReport="true" action="visit_f039_param.do" roles="/Policy/Mis/MedCase/Visit/Report039Param">
	                            039 форма (хранимые параметры)
	                        </msh:link></li>
						<li><msh:link isReport="true" action="visit_report_service.do" roles="/Policy/Mis/MedCase/Visit/ReportService">
	                            Отчет по услугам
	                        </msh:link></li>
						<%-- <li><msh:link isReport="true" action="visit_report_service_OFD.do" roles="/Policy/Mis/MedCase/Visit/ReportService">
	                            Отчет по услугам ОФД
	                        </msh:link> </li> --%>
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
						<li><msh:link isReport="true" action="extDisp_reestr_card.do" roles="/Policy/Mis/ExtDisp/Card/View">
	                            Реестр по диспансеризации (для проверки)
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
						<li><msh:link isReport="true" action="entityList-extDispPlan_plan.do" roles="/Policy/Mis/ExtDisp/Card/View">
							Список планов доп. диспансеризации
						</msh:link></li>
						<li><msh:link isReport="false" action="exportDispensary.do" roles="/Policy/Mis/Patient/Dispensary/View">
							Экспорт карт диспансерного учета
						</msh:link></li>
						<li><msh:link isReport="true" action="gosuslugi_report.do" roles="/Policy/Mis/MedCase/Visit/Report039">
							Отчет "Запись на прием к врачу"
	                        </msh:link></li>
	                        <li><msh:link isReport="true" action="report_timeexecute.do" roles="/Policy/Mis/TimeExecute/View">
	                            Отчет "время ожидания мед.помощи"
	                        </msh:link></li>
						<li><msh:link isReport="true" action="leanClinicReport.do" roles="/Policy/Mis/MedCase/Visit/Report039">
							Отчет "Бережливая поликлиника"
						</msh:link></li>
						<li><msh:link isReport="true" action="sheduleToday.do" roles="/Policy/Mis/MedCase/Visit/Report039">
							Расписание специалистов консультативной поликлиники
						</msh:link></li>
						<li><msh:link isReport="true" action="recordReport.do?serviceStream=1" roles="/Policy/Mis/MedCase/Visit/Report039">
							Отчет по записанным на дату
						</msh:link></li>
					</ul>
					</div>
				<div class='menu'>
					<h2>Роддом</h2>
					<ul style='list-style: none outside none; margin-bottom: 10px;  padding-left: 5px;'>
						<li><msh:link action="journal_roddom_histology.do" roles="/Policy/Mis/Pregnancy/Report/HistologyPlacenta">
	                            Журнал гистологий плацент
	                        </msh:link></li>
						<li><msh:link isReport="true" action="preg_child_birth_report.do" roles="/Policy/Mis/Pregnancy/ChildBirth/View">
	                            Отчет по родам
	                        </msh:link></li>
						<li><msh:link action="stac_report_32.do" roles="/Policy/Mis/Pregnancy/Report/Report32">
	                            32 форма
	                        </msh:link></li>
						<li><msh:link action="stac_pregnant_hosp_account.do" roles="/Policy/Mis/Pregnancy/Report/PregnantHospAccount">
	                            Учёт случаев госпитализации беременных
	                        </msh:link></li>
	                    <li><msh:link isReport="true" action="mis_assessment_risk_report.do" roles="/Policy/Mis/Pregnancy/Report/RiskReport">
	                            Отчет по рискам
	                    </msh:link></li>
						<li><msh:link action="stac_report_BirthTotal.do" roles="/Policy/Mis/Pregnancy/Report/Report32">
							Анализ работы родового отделения
						</msh:link></li>
						<li><msh:link  isReport="false" action="journal_cardiacScreening.do" roles="/Policy/Mis/Pregnancy/CardiacScreening/View">
							Отчёт о проведении кардиоскрининга новорождённых
						</msh:link></li>
						<li><msh:link  isReport="false" action="journal_robson.do" roles="/Policy/Mis/Pregnancy/ChildBirth/View">
							Отчёт по классификации Робсона
						</msh:link></li>
						<li><msh:link isReport="true" action="preg_nosology.do" roles="/Policy/Mis/Pregnancy/BirthNosologyCard/View">
							Отчёт по нозологиям
						</msh:link></li>
					</ul>
					</div>
				</td>			
				
				<td style='padding-left: 5em'>
					<div class='menu'>
					<h2>Стационар</h2>
					<ul style='list-style: none outside none; '>
						<li><msh:link isReport="true" action="/stac_admissionDoctorDiaryList.do" roles="/Policy/Mis/MedCase/Stac/Journal/HospitalByPeriod">
							Журнал пациентов, осмотренных врачами приемного отделения
	                        </msh:link></li>
						<li><msh:link isReport="true" action="stac_journalByHospital.do" roles="/Policy/Mis/MedCase/Stac/Journal/HospitalByPeriod">
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
						<li><msh:link isReport="true" action="stac_diagnosis_by_sls_list.do" roles="/Policy/Mis/Journal/DiagnosisBySlo">
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
						<li><msh:link  isReport="true" action="stac_planning_OphtHospitalizations.do" roles="/Policy/Mis/MedCase/Stac/Ssl/Planning/Opht/Report">
							Планирование введения ингибиторов ангиогенеза
						</msh:link></li>
						<li><msh:link  isReport="true" action="fillbedsreport.do" roles="/Policy/Mis/MedCase/Stac/Journal/FillBedsReport">
							Распределение пациентов по палатам
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
						<li><msh:link  isReport="true" action="stac_journal_compulsory_treatment_psych.do" roles="/Policy/Mis/MedCase/Stac/Journal/CompulsoryTreatmentByPsych">
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
	                        <li><msh:link  isReport="true" action="stac_diff_diagnosis.do" roles="/Policy/Mis/MedCase/Stac/Journal/Report14">
	                            Журнал расхождений диагнозов
	                        </msh:link></li>
	                        <li><msh:link  isReport="true" action="protocolReport.do" roles="/Policy/Mis/MedCase/Stac/Journal/ProtocolKili">
	                            Отчет по КИЛИ
	                        </msh:link></li>
	                        <li><msh:link  isReport="true" action="patientWatch.do" roles="/Policy/Mis/MedCase/Stac/Ssl/View">
	                            Отчет по пациентам под наблюдением
	                        </msh:link></li>
							<li><msh:link  isReport="true" action="reportIMT.do" roles="/Policy/Mis/MedCase/Stac/Ssl/View">
								Отчет по ИМТ пациентов
							</msh:link></li>
	                        <li><msh:link  isReport="true" action="pres_report4385.do" roles="/Policy/Mis/MedCase/Stac/Journal/ReportForOlesya">
	                            Отчет по антибиотикорезистентности
	                        </msh:link></li>
							<li><msh:link  isReport="true" action="report203.do" roles="/Policy/Mis/MedCase/Stac/Ssl/View">
								Отчет по 203 приказу
							</msh:link></li>
						<li><msh:link  isReport="true" action="reportBandage.do" roles="/Policy/Mis/MedCase/Stac/Ssl/View">
							Отчет по перевязкам
						</msh:link></li>
						<li><msh:link  isReport="false" action="journal_svetofor.do?mode=ADMISSION" roles="/Policy/Mis/MedCase/Stac/Ssl/View">
							Светофор
						</msh:link></li>
						<li><msh:link  isReport="false" action="sls_listAdmissionWait.do" roles="/Policy/Mis/MedCase/Stac/Ssl/View">
							Свод по светофору
						</msh:link></li>
						<li><msh:link  isReport="false" action="journal_onco.do" roles="/Policy/Mis/MedCase/Stac/Ssl/View">
							Отчёт по онкологическим случаям
						</msh:link></li>
						<li><msh:link  isReport="false" action="journal_noIdentSls.do" roles="/Policy/Mis/MedCase/Stac/Ssl/View">
							Отчёт по неидентифицированным госпитализациям
						</msh:link></li>
					</ul>
					</div>
				</td>
			</tr>
			
		</table>
	</tiles:put>
</tiles:insert>
