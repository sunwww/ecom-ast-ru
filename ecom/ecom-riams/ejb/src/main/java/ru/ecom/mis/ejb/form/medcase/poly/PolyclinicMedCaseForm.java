package ru.ecom.mis.ejb.form.medcase.poly;

import ru.ecom.ejb.services.entityform.WebTrail;
import ru.ecom.mis.ejb.domain.medcase.PolyclinicMedCase;
import ru.ecom.mis.ejb.form.medcase.MedCaseForm;
import ru.ecom.mis.ejb.form.patient.PatientForm;
import ru.nuzmsh.commons.formpersistence.annotation.*;
import ru.nuzmsh.ejb.formpersistence.annotation.EntityFormPersistance;
import ru.nuzmsh.forms.validator.transforms.DoDateString;
import ru.nuzmsh.forms.validator.validators.DateString;
/**
 * Форма случая поликлинического обслуживания
 * @author STkacheva
 *
 */
@Comment("Случай поликлинического обслуживания")
@EntityForm
@EntityFormPersistance(clazz= PolyclinicMedCase.class)
@WebTrail(comment = "Случай поликлинического обслуживания", nameProperties= "id"
, view="entityParentView-smo_spo.do",shortList="entityParentList-smo_spo.do?short=Short",list="entityParentList-smo_spo.do")
@Parent(property="patient", parentForm= PatientForm.class)
@EntityFormSecurityPrefix("/Policy/Mis/MedCase/Spo")
public class PolyclinicMedCaseForm extends MedCaseForm {

	/** Признак консультативно-диагностического обращения */
	@Comment("Признак консультативно-диагностического обращения")
	@Persist @Deprecated
	public Boolean getIsDiagnosticSpo() {return theIsDiagnosticSpo;}
	public void setIsDiagnosticSpo(Boolean aIsDiagnosticSpo) {theIsDiagnosticSpo = aIsDiagnosticSpo;}
	/** Признак консультативно-диагностического обращения */
	private Boolean theIsDiagnosticSpo ;

	/** Дата окончания */
	@Comment("Дата окончания")
	@Persist	@DateString @DoDateString
	public String getDateFinish() {return theDateFinish;}
	public void setDateFinish(String aDateFinish) {theDateFinish = aDateFinish;}

	/** Продолжительность */
	@Comment("Продолжительность")
	@Persist
	public String getDuration() {return theDuration;}
	public void setDuration(String aDuration) {theDuration = aDuration;}

	/** Код МКБ-10 */
	@Comment("Код МКБ-10")
	@Persist
	public Long getIdc10() {return theIdc10;}
	public void setIdc10(Long aIdc10) {theIdc10 = aIdc10;}

	/** Кто завершил */
	@Comment("Кто завершил")
	@Persist
	public Long getFinishFunction() {return theFinishFunction;}
	public void setFinishFunction(Long aFinishWorker) {	theFinishFunction = aFinishWorker;	}

	/** Владелец */
	@Comment("Владелец")
	@Persist
	public Long getOwnerFunction() {return theOwnerFunction;}
	public void setOwnerFunction(Long aOwner) {theOwnerFunction = aOwner;}


	/** Кол-во визитов */
	@Comment("Кол-во визитов")
	@Persist
	public Integer getVisitsCount() {return theVisitsCount;}
	public void setVisitsCount(Integer aVisitsCount) {theVisitsCount = aVisitsCount;}

	/** Кол-во дней */
	@Comment("Кол-во дней")
	@Persist
	public String getDaysCount() {return theDaysCount;}
	public void setDaysCount(String aVisitsCount) {theDaysCount = aVisitsCount;}

	/** Кол-во дней */
	private String theDaysCount;
	/** Кол-во визитов */
	private Integer theVisitsCount;
	/** Владелец */
	private Long theOwnerFunction;
	/** Кто завершил */
	private Long theFinishFunction;
	/** Код МКБ-10 */
	private Long theIdc10;
	/** Продолжительность */
	private String theDuration;
	/** Дата окончания */
	private String theDateFinish;
}

