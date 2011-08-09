package ru.ecom.mis.ejb.form.medcase.poly;

import ru.ecom.ejb.services.entityform.WebTrail;
import ru.ecom.mis.ejb.domain.medcase.PolyclinicMedCase;
import ru.ecom.mis.ejb.form.medcase.MedCaseForm;
import ru.ecom.mis.ejb.form.patient.PatientForm;
import ru.nuzmsh.commons.formpersistence.annotation.Comment;
import ru.nuzmsh.commons.formpersistence.annotation.EntityForm;
import ru.nuzmsh.commons.formpersistence.annotation.EntityFormSecurityPrefix;
import ru.nuzmsh.commons.formpersistence.annotation.Parent;
import ru.nuzmsh.commons.formpersistence.annotation.Persist;
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
@WebTrail(comment = "Случай поликлинического обслуживания", nameProperties= "id", view="entityParentView-smo_spo.do")
@Parent(property="patient", parentForm= PatientForm.class)
@EntityFormSecurityPrefix("/Policy/Mis/MedCase/Spo")
public class PolyclinicMedCaseForm extends MedCaseForm {

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
	public Long getFinishWorker() {return theFinishWorker;}
	public void setFinishWorker(Long aFinishWorker) {	theFinishWorker = aFinishWorker;	}

	/** Владелец */
	@Comment("Владелец")
	@Persist
	public Long getOwner() {return theOwner;}
	public void setOwner(Long aOwner) {theOwner = aOwner;}

	/** Кем открыт (текст) */
	@Comment("Кем открыт (текст)")
	@Persist
	public String getFinishWorkerText() {return theFinishWorkerText;}
	public void setFinishWorkerText(String aFinishWorkerText) {theFinishWorkerText = aFinishWorkerText;}

	/** Владелец текст*/
	@Comment("Владелец")
	@Persist 	
	public String getOwnerText() {return theOwnerText;}
	public void setOwnerText(String aOwnerText) {theOwnerText = aOwnerText;}

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
	/** Владелец текст */
	private String theOwnerText;
	/** Кем открыт (текст) */
	private String theFinishWorkerText;
	/** Владелец */
	private Long theOwner;
	/** Кто завершил */
	private Long theFinishWorker;
	/** Код МКБ-10 */
	private Long theIdc10;
	/** Продолжительность */
	private String theDuration;
	/** Дата окончания */
	private String theDateFinish;
}

