package ru.ecom.mis.ejb.form.medcase.hospital;

import ru.ecom.ejb.services.entityform.WebTrail;
import ru.ecom.ejb.services.entityform.interceptors.AEntityFormInterceptor;
import ru.ecom.ejb.services.entityform.interceptors.ASaveInterceptors;
import ru.ecom.ejb.services.entityform.interceptors.AViewInterceptors;
import ru.ecom.mis.ejb.domain.medcase.HospitalMedCase;
import ru.ecom.mis.ejb.form.medcase.hospital.interceptors.DischargeMedCaseSaveInterceptor;
import ru.ecom.mis.ejb.form.medcase.hospital.interceptors.DischargeMedCaseViewInterceptor;
import ru.ecom.mis.ejb.form.patient.PatientForm;
import ru.nuzmsh.commons.formpersistence.annotation.Comment;
import ru.nuzmsh.commons.formpersistence.annotation.EntityForm;
import ru.nuzmsh.commons.formpersistence.annotation.EntityFormSecurityPrefix;
import ru.nuzmsh.commons.formpersistence.annotation.Parent;
import ru.nuzmsh.commons.formpersistence.annotation.Persist;
import ru.nuzmsh.ejb.formpersistence.annotation.EntityFormPersistance;
import ru.nuzmsh.forms.validator.transforms.DoDateString;
import ru.nuzmsh.forms.validator.transforms.DoTimeString;
import ru.nuzmsh.forms.validator.validators.DateString;
import ru.nuzmsh.forms.validator.validators.MaxDateCurrent;
import ru.nuzmsh.forms.validator.validators.Mkb;
import ru.nuzmsh.forms.validator.validators.Required;
import ru.nuzmsh.forms.validator.validators.TimeString;

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
public class DischargeMedCaseForm extends AdmissionMedCaseForm {

	/** Время выписки */
	@Comment("Время выписки")
	@TimeString @DoTimeString
	@Persist @Required
	public String getDischargeTime() {return theDischargeTime;}
	public void setDischargeTime(String aDischargeTime) {theDischargeTime = aDischargeTime;	}

	/** Результат госпитализации */
	@Comment("Результат госпитализации")
	@Persist @Required
	public Long getResult() {return theResult;	}
	public void setResult(Long aResult) {theResult = aResult;}
	
	/** Дата окончания */
	@Comment("Дата окончания")
	@Persist @DateString @DoDateString
	@Required @MaxDateCurrent
	public String getDateFinish() {return theDateFinish;}
	public void setDateFinish(String aDateFinish) {theDateFinish = aDateFinish;}
	
	/** Куда переведен */
	@Comment("Куда переведен")
	@Persist
	public Long getTargetHospType() {return theTargetHospType;}
	public void setTargetHospType(Long aTargetHospType) {theTargetHospType = aTargetHospType;}
	
	/** Клинический диагноз */
	@Comment("Клинический диагноз")
	@Required
	public String getClinicalDiagnos() {return theClinicalDiagnos;}
	public void setClinicalDiagnos(String aClinicalDiagnos) {theClinicalDiagnos = aClinicalDiagnos;}

	/** Клинический диагноз по МКБ-10 */
	@Comment("Клинический диагноз по МКБ-10")
	@Required @Mkb
	public Long getClinicalMkb() {return theClinicalMkb;}
	public void setClinicalMkb(Long aClinicalMkb) {theClinicalMkb = aClinicalMkb;}

	/** Патанатомический диагноз */
	@Comment("Патанатомический диагноз")
	public String getPathanatomicalDiagnos() {return thePathanatomicalDiagnos;}
	public void setPathanatomicalDiagnos(String aPathanatomicalDiagnos) {thePathanatomicalDiagnos = aPathanatomicalDiagnos;}
	
	/** Патанатомический диагноз по МКБ-10 */
	@Comment("Патанатомический диагноз по МКБ-10")
	@Mkb
	public Long getPathanatomicalMkb() {return thePathanatomicalMkb;}
	public void setPathanatomicalMkb(Long aPathanatomicalMkb) {thePathanatomicalMkb = aPathanatomicalMkb;}

	/** Заключительный диагноз */
	@Comment("Заключительный диагноз")
	public String getConcludingDiagnos() {return theConcludingDiagnos;}
	public void setConcludingDiagnos(String aConcludingDiagnos) {theConcludingDiagnos = aConcludingDiagnos;}

	/** Заключительный диагноз по МКБ-10 */
	@Comment("Заключительный диагноз по МКБ-10")
	@Mkb
	public Long getConcludingMkb() {return theConcludingMkb;}
	public void setConcludingMkb(Long aConcludingMkb) {theConcludingMkb = aConcludingMkb;}

	/** Исход госпитализации */
	@Comment("Исход госпитализации")
	@Persist @Required
	public Long getOutcome() {	return theOutcome;}
	public void setOutcome(Long aOutcome) {theOutcome = aOutcome;	}
	
	/** Острота диагноза клинического */
	@Comment("Острота диагноза клинического")
	@Required
	public Long getClinicalActuity() {return theClinicalActuity;}
	public void setClinicalActuity(Long aClinicalActuity) {theClinicalActuity = aClinicalActuity;}

	/** Острота диагноза клинического */
	private Long theClinicalActuity;
	/** Исход госпитализации */
	private Long theOutcome;
	/** Заключительный диагноз по МКБ-10 */
	private Long theConcludingMkb;
	/** Заключительный диагноз */
	private String theConcludingDiagnos;
	/** Патанатомический диагноз */
	private String thePathanatomicalDiagnos;
	/** Патанатомический диагноз по МКБ-10 */
	private Long thePathanatomicalMkb;
	/** Клинический диагноз */
	private String theClinicalDiagnos;
	/** Клинический диагноз по МКБ-10 */
	private Long theClinicalMkb;
	/** Куда переведен */
	private Long theTargetHospType;
	/** Дата окончания */
	private String theDateFinish;
	/** Результат госпитализации */
	private Long theResult;
	/** Время выписки */
	private String theDischargeTime;
}
