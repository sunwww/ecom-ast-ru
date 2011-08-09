package ru.ecom.mis.ejb.form.disability;

import ru.ecom.ejb.form.simple.IdEntityForm;
import ru.ecom.ejb.services.entityform.WebTrail;
import ru.ecom.mis.ejb.domain.disability.DisabilityCase;
import ru.ecom.mis.ejb.form.patient.PatientForm;
import ru.nuzmsh.commons.formpersistence.annotation.Comment;
import ru.nuzmsh.commons.formpersistence.annotation.EntityForm;
import ru.nuzmsh.commons.formpersistence.annotation.EntityFormSecurityPrefix;
import ru.nuzmsh.commons.formpersistence.annotation.Parent;
import ru.nuzmsh.commons.formpersistence.annotation.Persist;
import ru.nuzmsh.ejb.formpersistence.annotation.EntityFormPersistance;
import ru.nuzmsh.forms.validator.transforms.DoDateString;
import ru.nuzmsh.forms.validator.validators.DateString;
import ru.nuzmsh.forms.validator.validators.Required;
/**
 * Случай нетрудоспособности
 * @author azviagin,stkacheva
 */

@EntityForm
@EntityFormPersistance (clazz = DisabilityCase.class)
@Comment("Случай нетрудоспособности")
@WebTrail(comment = "Случай нетрудоспособности", nameProperties= "id", view="entityParentView-dis_case.do",
			list="entityParentList-dis_case.do",listComment="СНТ по пациенту")
@Parent(property="patient", parentForm=PatientForm.class)
@EntityFormSecurityPrefix("/Policy/Mis/Disability/Case")
public class DisabilityCaseForm extends IdEntityForm {

	/** Пациент */
	@Comment("Пациент")
	@Persist @Required
	public Long getPatient() {return thePatient;}
	public void setPatient(Long aPatient) {thePatient = aPatient;}


	/** СМО */
	@Comment("СМО")
	@Persist
	public Long getMedCase() {return theMedCase;}
	public void setMedCase(Long aMedCase) {theMedCase = aMedCase;}


	/** Дата начала */
	@Comment("Дата начала")
	@DateString @DoDateString @Persist 
	public String getDateFrom() {return theDateFrom;}
	public void setDateFrom(String aDateFrom) {theDateFrom = aDateFrom;}


	/** Дата окончания */
	@Comment("Дата окончания")
	@DateString @DoDateString @Persist
	public String getDateTo() {return theDateTo;}
	public void setDateTo(String aDateTo) {theDateTo = aDateTo;}


	/** Продолжительность */
	@Comment("Продолжительность")
	@Persist
	public String getDuration() {return theDuration;}
	public void setDuration(String aDuration) {theDuration = aDuration;}


	
	/** Родительский случай нетрудоспособности */
	@Comment("Родительский случай нетрудоспособности")
	@Persist
	public Long getParentDisabiblityCase() {return theParentDisabiblityCase;}
	public void setParentDisabiblityCase(Long aParentDisabiblityCase) {theParentDisabiblityCase = aParentDisabiblityCase;}

	/** Пациент */
	private Long thePatient;
	/** СМО */
	private Long theMedCase;
	/** Дата начала */
	private String theDateFrom;
	/** Дата окончания */
	private String theDateTo;
	/** Продолжительность */
	private String theDuration;
	/** Родительский случай нетрудоспособности */
	private Long theParentDisabiblityCase;
	/**
	 * Состоит на учете в службе занятости
	 */
	@Comment("Состоит на учете в службе занятости")
	@Persist
	public Boolean getPlacementService() {return thePlacementService;}
	public void setPlacementService(Boolean aPlacementService) {thePlacementService = aPlacementService;}
	/**
	 * Состоит на учете в службе занятости
	 */
	private Boolean thePlacementService;
	/**
	 * Поставлена на учет в ранние сроки беременности (до 12 недель)
	 */
	@Comment("Поставлена на учет в ранние сроки беременности (до 12 недель)")
	@Persist
	public Boolean getEarlyPregnancyRegistration() {return theEarlyPregnancyRegistration;}
	public void setEarlyPregnancyRegistration(Boolean aEarlyPregnancyRegistration) {theEarlyPregnancyRegistration = aEarlyPregnancyRegistration;}
	/**
	 * Поставлена на учет в ранние сроки беременности (до 12 недель)
	 */
	private Boolean theEarlyPregnancyRegistration;
	/** Место работы */
	@Comment("Место работы")
	@Persist
	public String getJob() {return theJob;}
	public void setJob(String aJob) {theJob = aJob;}

	/** Место работы */
	private String theJob;
}
