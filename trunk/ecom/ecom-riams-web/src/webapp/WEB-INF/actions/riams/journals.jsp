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
					<li><msh:link roles="/Policy/Mis/Journal/MilitiaMessage" action="journal_militiaMessage.do">
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
					</ul>
					</div>
				</td>
			</tr>
			<tr>
				<td style='padding-left: 5em'>
				
				</td>			
				
				<td style='padding-left: 5em'>
					<div class='menu'>
					<h2>Стационар</h2>
					<ul>
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
					</ul>
					</div>
				</td>
			</tr>
			
		</table>
	</tiles:put>
</tiles:insert>
