package ru.ecom.mis.ejb.form.medcase.hospital;

import lombok.Setter;
import ru.ecom.ejb.services.entityform.WebTrail;
import ru.ecom.ejb.services.entityform.interceptors.AEntityFormInterceptor;
import ru.ecom.ejb.services.entityform.interceptors.ASaveInterceptors;
import ru.ecom.ejb.services.entityform.interceptors.AViewInterceptors;
import ru.ecom.mis.ejb.domain.medcase.HospitalMedCase;
import ru.ecom.mis.ejb.form.medcase.hospital.interceptors.DischargeMedCaseSaveInterceptor;
import ru.ecom.mis.ejb.form.medcase.hospital.interceptors.DischargeMedCaseViewInterceptor;
import ru.ecom.mis.ejb.form.patient.PatientForm;
import ru.nuzmsh.commons.formpersistence.annotation.*;
import ru.nuzmsh.ejb.formpersistence.annotation.EntityFormPersistance;
import ru.nuzmsh.forms.validator.transforms.DoDateString;
import ru.nuzmsh.forms.validator.transforms.DoTimeString;
import ru.nuzmsh.forms.validator.validators.*;

@Comment("Случай стационарного лечения. Выписка")
@EntityForm
@EntityFormPersistance(clazz= HospitalMedCase.class)
@WebTrail(comment = "Случай стационарного лечения. Выписка", nameProperties= "id", view="entityParentView-stac_sslDischarge.do")
@Parent(property="patient", parentForm= PatientForm.class)
@EntityFormSecurityPrefix("/Policy/Mis/MedCase/Stac/Ssl/Discharge")
@AViewInterceptors(
        @AEntityFormInterceptor(DischargeMedCaseViewInterceptor.class)
)
@ASaveInterceptors(
        @AEntityFormInterceptor(DischargeMedCaseSaveInterceptor.class)
)
@Setter
public class DischargeMedCaseForm extends AdmissionMedCaseForm {
	/** Причина выписки */
	@Comment("Причина выписки")
	@Required @Persist
	public Long getReasonDischarge() {return reasonDischarge;}

	/** Причина выписки */
	private Long reasonDischarge;
	/** Время выписки */
	@Comment("Время выписки")
	@TimeString @DoTimeString
	@Persist @Required
	public String getDischargeTime() {return dischargeTime;}

	/** Результат госпитализации */
	@Comment("Результат госпитализации")
	@Persist @Required
	public Long getResult() {return result;	}

	/** Дата окончания */
	@Comment("Дата окончания")
	@Persist @DateString @DoDateString
	@Required @MaxDateCurrent
	public String getDateFinish() {return dateFinish;}

	/** Куда переведен */
	@Comment("Куда переведен")
	@Persist
	public Long getTargetHospType() {return targetHospType;}

	/** Клинический диагноз */
	@Comment("Клинический диагноз")
	public String getClinicalDiagnos() {return clinicalDiagnos;}

	/** Клинический диагноз по МКБ-10 */
	@Comment("Клинический диагноз по МКБ-10")
	@Mkb
	public Long getClinicalMkb() {return clinicalMkb;}

	/** Патанатомический диагноз */
	@Comment("Патанатомический диагноз")
	public String getPathanatomicalDiagnos() {return pathanatomicalDiagnos;}

	/** Патанатомический диагноз по МКБ-10 */
	@Comment("Патанатомический диагноз по МКБ-10")
	@Mkb
	public Long getPathanatomicalMkb() {return pathanatomicalMkb;}

	/** Заключительный диагноз */
	@Comment("Заключительный диагноз")
	@Required
	public String getConcludingDiagnos() {return concludingDiagnos;}

	/** Заключительный диагноз по МКБ-10 */
	@Comment("Заключительный диагноз по МКБ-10")
	@Required @Mkb
	public Long getConcludingMkb() {return concludingMkb;}

	/** Исход госпитализации */
	@Comment("Исход госпитализации")
	@Persist @Required
	public Long getOutcome() {	return outcome;}

	/** Острота диагноза клинического */
	@Comment("Острота диагноза клинического")
	public Long getClinicalActuity() {return clinicalActuity;}

	/** Острота диагноза заключительного */
	@Comment("Острота диагноза клинического")
	@Required
	public Long getConcludingActuity() {return concludingActuity;}

	/** Острота диагноза заключительного */
	private Long concludingActuity;
	/** Острота диагноза клинического */
	private Long clinicalActuity;
	/** Исход госпитализации */
	private Long outcome;
	/** Заключительный диагноз по МКБ-10 */
	private Long concludingMkb;
	/** Заключительный диагноз */
	private String concludingDiagnos;
	/** Патанатомический диагноз */
	private String pathanatomicalDiagnos;
	/** Патанатомический диагноз по МКБ-10 */
	private Long pathanatomicalMkb;
	/** Клинический диагноз */
	private String clinicalDiagnos;
	/** Клинический диагноз по МКБ-10 */
	private Long clinicalMkb;
	/** Куда переведен */
	private Long targetHospType;
	/** Дата окончания */
	private String dateFinish;
	/** Результат госпитализации */
	private Long result;
	/** Время выписки */
	private String dischargeTime;
}
