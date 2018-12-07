package ru.ecom.mis.ejb.form.medcase.hospital;

import ru.ecom.ejb.services.entityform.WebTrail;
import ru.ecom.ejb.services.entityform.interceptors.ACreateInterceptors;
import ru.ecom.ejb.services.entityform.interceptors.AEntityFormInterceptor;
import ru.ecom.ejb.services.entityform.interceptors.ASaveInterceptors;
import ru.ecom.ejb.services.entityform.interceptors.AViewInterceptors;
import ru.ecom.mis.ejb.domain.medcase.ExtHospitalMedCase;
import ru.ecom.mis.ejb.form.medcase.hospital.interceptors.DischargeMedCaseSaveInterceptor;
import ru.ecom.mis.ejb.form.medcase.hospital.interceptors.DischargeMedCaseViewInterceptor;
import ru.ecom.mis.ejb.form.patient.PatientForm;
import ru.nuzmsh.commons.formpersistence.annotation.*;
import ru.nuzmsh.ejb.formpersistence.annotation.EntityFormPersistance;
import ru.nuzmsh.forms.validator.transforms.DoDateString;
import ru.nuzmsh.forms.validator.validators.DateString;
import ru.nuzmsh.forms.validator.validators.MaxDateCurrent;
import ru.nuzmsh.forms.validator.validators.Mkb;
import ru.nuzmsh.forms.validator.validators.Required;

@Comment("Госпитализация в другие стационары")
@EntityForm
@EntityFormPersistance(clazz= ExtHospitalMedCase.class)
@WebTrail(comment = "Госпитализация в другие стационары", nameProperties= "dateStart", view="entityParentView-stac_extssl.do")
@Parent(property="patient", parentForm= PatientForm.class)
@EntityFormSecurityPrefix("/Policy/Mis/MedCase/Stac/Ssl/Ext")
@AViewInterceptors(
        @AEntityFormInterceptor(DischargeMedCaseViewInterceptor.class)
)
@ASaveInterceptors(
        @AEntityFormInterceptor(DischargeMedCaseSaveInterceptor.class)
)
@ACreateInterceptors(
		@AEntityFormInterceptor(DischargeMedCaseSaveInterceptor.class)
)
public class ExtHospitalMedCaseForm extends HospitalMedCaseForm {
	/** Дата начала */
	@Comment("Дата начала") 
	@DateString @DoDateString 
	@Persist @Required @MaxDateCurrent
	public String getDateStart() {return theDateStart;	}
	public void setDateStart(String aDateStart) {theDateStart = aDateStart;}
	/** Дата окончания */
	@Comment("Дата окончания")
	@Persist @DateString @DoDateString
	@Required @MaxDateCurrent
	public String getDateFinish() {return theDateFinish;}
	public void setDateFinish(String aDateFinish) {theDateFinish = aDateFinish;}

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
	/** Острота диагноза клинического */
	@Comment("Острота диагноза клинического")
	@Required
	public Long getClinicalActuity() {return theClinicalActuity;}
	public void setClinicalActuity(Long aClinicalActuity) {theClinicalActuity = aClinicalActuity;}

	/** Острота диагноза клинического */
	private Long theClinicalActuity;
	/** Клинический диагноз */
	private String theClinicalDiagnos;
	/** Клинический диагноз по МКБ-10 */
	private Long theClinicalMkb;
	/** Дата окончания */
	private String theDateFinish;
	/** Дата начала */
	private String theDateStart;
}
