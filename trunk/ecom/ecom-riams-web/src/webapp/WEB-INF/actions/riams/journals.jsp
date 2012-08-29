<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@ taglib uri="http://struts.apache.org/tags-tiles" prefix="tiles"%>
<%@ taglib uri="http://www.nuzmsh.ru/tags/msh" prefix="msh"%>
<%@ taglib uri="/WEB-INF/mis.tld" prefix="mis"%>

<tiles:insert page="/WEB-INF/tiles/mainLayout.jsp" flush="true">

	<tiles:put name='title' type='string'>
		<msh:title mainMenu="Journals">Журналы</msh:title>
	</tiles:put>

	<tiles:put name='body' type='string'>
		<table class='mainMenu'>
			<tr>
				<td class='col1'>
				<div class='menu'>
				<h2>Сообщения</h2>
				<ul>
					<li><msh:link roles='/Policy/Mis/Journal/InfectiousMessages' action="journal_infectMessage.do">
                            Журнал экстренных сообщений об инфекции
                        </msh:link></li>
					<li><msh:link roles="/Policy/Mis/Journal/CriminalMessages" action="journal_militiaMessage.do">
                             Журнал сообщений в милицию
                        </msh:link></li>
                        
				</ul>
				</div>
				
				</td><td style='padding-left: 5em'>
					<div class='menu'>
					<h2>Общие</h2>
					<ul>
						<li><msh:link action="journal_vaccination.do" roles="/Policy/Mis/Journal/Vaccination">
	                            Журнал прививок
	                        </msh:link></li>
						<li><msh:link action="journal_prescription.do" roles="/Policy/Mis/Journal/Prescription">
	                            Журнал назначений
	                        </msh:link></li>
						<li><msh:link action="journal_serviceMedCase.do" roles="/Policy/Mis/Journal/MedService">
	                            Журнал услуг
	                        </msh:link></li>
						<li><msh:link action="journal_nationality_smo_list.do" roles="/Policy/Mis/MedCase/Visit/ReportNationality">
	                            Журнал обращений с группировкой по гражданству (475-Пр)
	                        </msh:link></li>
	                   <li><msh:link action="stac_journalRepeatCaseByHospital_list" roles="/Policy/Mis/Journal/RepeatCase">
	                   		Повторные случаи мед. обслуживания
	                   		</msh:link></li>
					</ul>
					</div>
				</td>
			</tr>
			<tr>
				<td style='padding-left: 5em'>
				<div class='menu'>
					<h2>Поликлиника</h2>
					<ul>
						<li><msh:link action="visit_f039_list.do?ticketIs=0" roles="/Policy/Mis/MedCase/Visit/Report039">
	                            039 форма
	                        </msh:link></li>
						<li><msh:link action="visit_journal_direction.do" roles="/Policy/Mis/MedCase/Direction/Journal">
	                            Журнал направленных
	                        </msh:link></li>
						<li><msh:link action="journal_visits_list.do" roles="/Policy/Mis/MedCase/Visit/ReportVisits">
	                            Журнал обращений
	                        </msh:link></li>
						<li><msh:link action="journal_visit_diagnosis.do" roles="/Policy/Mis/MedCase/Visit/ReportDiagnosis">
	                            Журнал диагнозов по визитам
	                        </msh:link></li>
					</ul>
					</div>
				</td>			
				
				<td style='padding-left: 5em'>
					<div class='menu'>
					<h2>Стационар</h2>
					<ul>
						<li><msh:link action="stac_journalByHospital.do" roles="/Policy/Mis/MedCase/Stac/Journal/ByHospital">
	                            Журнал по поступившим и выбывшим стационара, а также отказам от госпитализаций 
	                        </msh:link></li>
						<li><msh:link action="stac_report_007.do" roles="/Policy/Mis/MedCase/Stac/Journal/ByHospital">
	                            007/у-02 форма
	                        </msh:link></li>
						<li><msh:link action="journal_surOperation.do" roles="/Policy/Mis/Journal/SurgicalOperation">
	                            Журнал хирургических операций
	                        </msh:link></li>
						<li><msh:link action="stac_resultPatient_list.do" roles="/Policy/Mis/Journal/ResultHospitalization">
	                            Журнал результатов госпитализации (в том числе по смерти)
	                        </msh:link></li>
						<li><msh:link action="stac_analysis_department_list.do" roles="/Policy/Mis/Journal/AnalysisWorkDepartment">
	                            Анализ работы отделений
	                        </msh:link></li>
						<li><msh:link action="stac_attending_doctor_list.do" roles="/Policy/Mis/Journal/AttendingDoctor">
	                            Журнал по лечащему врачу
	                        </msh:link></li>
						<li><msh:link action="stac_diagnosis_by_slo_list.do" roles="/Policy/Mis/Journal/DiagnosisBySlo">
	                            Журнал диагнозов по СЛО
	                        </msh:link></li>
						<li><msh:link action="stac_journalOpenByHospital.do" roles="/Policy/Mis/MedCase/Stac/Journal/OpenningCaseByHospital">
	                            Журнал открытых СЛС (госпитализаций)
	                        </msh:link></li>
						<li><msh:link action="stac_journalByDepartmentAdmission.do" roles="/Policy/Mis/MedCase/Stac/Journal/ByDepartmentAdmission">
	                            Журнал направленных пациентов по отделениям (неоформленных)
	                        </msh:link></li>
						<li><msh:link action="stac_journalCurrentByUserDepartment.do" roles="/Policy/Mis/MedCase/Stac/Journal/CurrentByUserDepartment">
	                            По состоящим в отделениях пациентам (оформленных)
	                        </msh:link></li>
						<li><msh:link action="stac_journalByCurator.do" roles="/Policy/Mis/MedCase/Stac/Journal/ByCurator">
	                            По состоящим в отделениях пациентам с разбивкой по леч.врачу (оформленных)
	                        </msh:link></li>
					</ul>
					</div>
				</td>
			</tr>
			
		</table>
	</tiles:put>
</tiles:insert>
