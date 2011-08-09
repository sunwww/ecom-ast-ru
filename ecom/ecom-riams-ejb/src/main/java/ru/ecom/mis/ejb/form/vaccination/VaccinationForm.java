package ru.ecom.mis.ejb.form.vaccination;

import ru.ecom.ejb.form.simple.IdEntityForm;
import ru.ecom.ejb.services.entityform.WebTrail;
import ru.ecom.mis.ejb.domain.vaccination.Vaccination;
import ru.ecom.mis.ejb.form.medcase.MedCaseForm;
import ru.nuzmsh.commons.formpersistence.annotation.Comment;
import ru.nuzmsh.commons.formpersistence.annotation.EntityForm;
import ru.nuzmsh.commons.formpersistence.annotation.EntityFormSecurityPrefix;
import ru.nuzmsh.commons.formpersistence.annotation.Parent;
import ru.nuzmsh.commons.formpersistence.annotation.Persist;
import ru.nuzmsh.ejb.formpersistence.annotation.EntityFormPersistance;
import ru.nuzmsh.forms.validator.transforms.DoDateString;
import ru.nuzmsh.forms.validator.transforms.DoTimeString;
import ru.nuzmsh.forms.validator.transforms.DoUpperCase;
import ru.nuzmsh.forms.validator.validators.DateString;
import ru.nuzmsh.forms.validator.validators.Required;
import ru.nuzmsh.forms.validator.validators.TimeString;

/**
 * Вакцинация
 */

@Comment("Вакцинация")
@EntityForm
@EntityFormPersistance(clazz=Vaccination.class)
@WebTrail(comment = "Вакцинация", nameProperties= "id", view="entityView-vac_vaccination.do")
@Parent(property = "medCase", parentForm =MedCaseForm.class)
@EntityFormSecurityPrefix("/Policy/Mis/Vaccination")
public class VaccinationForm extends IdEntityForm {

	/** Вакцина */
	@Comment("Вакцина")
	@Persist @Required 
	public Long getVaccine() {return theVaccine;}
	public void setVaccine(Long aNewProperty) {theVaccine = aNewProperty;}

	/** Вакцинный материал	 */
	@Comment("Вакцинный материал")
	@Persist 
	public Long getMaterial() {return theMaterial;}
	public void setMaterial(Long aNewProperty) {theMaterial = aNewProperty;}

	/**  Исполнитель */
	@Comment("Исполнитель")
	@Persist @Required
	public Long getExecuteWorker() {return theExecuteWorker;}
	public void setExecuteWorker(Long aNewProperty) {theExecuteWorker = aNewProperty;}

	/**  Дата исполнения  */
	@Comment("Дата исполнения")
	@DateString @DoDateString
	@Persist @Required
	public String getExecuteDate() {return theExecuteDate;}
	public void setExecuteDate(String aNewProperty) {theExecuteDate = aNewProperty;}

	/**  Время исполнения */
	@Comment("Время исполнения")
	@TimeString @DoTimeString
	@Persist @Required
	public String  getExecuteTime() {return theExecuteTime;}
	public void setExecuteTime(String aNewProperty) {theExecuteTime = aNewProperty;}

	/** Доза */
	@Comment("Доза")
	@Persist @Required
	public String getDose() {return theDose;}
	public void setDose(String aNewProperty) {theDose = aNewProperty;}

	/** Серия */
	@Comment("Серия")
	@Persist @Required @DoUpperCase
	public String getSeries() {return theSeries;}
	public void setSeries(String aNewProperty) {theSeries = aNewProperty;}

	/** Метод вакцинации */
	@Comment("Метод вакцинации")
	@Persist @Required
	public Long getMethod() {return theMethod;}
	public void setMethod(Long aNewProperty) {theMethod = aNewProperty;}

	/** Контрольный номер */
	@Comment("Контрольный номер")
	@Persist @Required
	public String getControlNumber() {return theControlNumber;}
	public void setControlNumber(String aNewProperty) {theControlNumber = aNewProperty;}

	/** Дата, на которую планировалась вакцинация */
	@Comment("Дата, на которую планировалась вакцинация")
	@DateString @DoDateString
	@Persist
	public String getPlanDate() {return thePlanDate;}
	public void setPlanDate(String aNewProperty) {thePlanDate = aNewProperty;}

	/** Дата следующей вакцинации */
	@Comment("Дата следующей вакцинации")
	@DateString @DoDateString
	@Persist
	public String getNextDate() {return theNextDate;}
	public void setNextDate(String aNewProperty) {theNextDate = aNewProperty;}

	/** Фаза вакцинации */
	@Comment("Фаза вакцинации")
	@Persist @Required
	public Long getPhase() {return thePhase;}
	public void setPhase(Long aNewProperty) {thePhase = aNewProperty;}

	/** Тип причины вакцинации */
	@Comment("Тип причины вакцинации")
	@Persist @Required
	public Long getReasonType() {return theReasonType;}
	public void setReasonType(Long aNewProperty) {theReasonType = aNewProperty;}

	/** Комментарии */
	@Comment("Комментарии")
	@Persist
	public String getComments() {return theComments;}
	public void setComments(String a_Property) {theComments = a_Property;}

	/** Медотвод */
	@Comment("Медотвод")
	@Persist
	public Long getEstop() {return theEstop;}
	public void setEstop(Long a_Property) {theEstop = a_Property;}

	/** Оценка */
	@Comment("Оценка")
	@Persist
	public Long getAssesment() {return theAssesment;}
	public void setAssesment(Long a_Property) {theAssesment = a_Property;}

	/** Пациент */
	@Comment("Пациент")
	@Persist
	public Long getPatient() {return thePatient;}
	public void setPatient(Long aPatient) {thePatient = aPatient;}

	/** СМО */
	@Comment("СМО")
	@Persist @Required
	public Long getMedCase() {return theMedCase;}
	public void setMedCase(Long aMedCase) {theMedCase = aMedCase;}

	/** Дата окончания годности */
	@Comment("Дата окончания годности")
	@DateString @DoDateString
	@Persist @Required
	public String getExpirationDate() {return theExpirationDate;}
	public void setExpirationDate(String a_Property) {theExpirationDate = a_Property;}

	/** Разрешил */
	@Comment("Разрешил")
	@Persist @Required
	public Long getPermitWorker() {return thePermitWorker;}
	public void setPermitWorker(Long a_Property) {thePermitWorker = a_Property;}
	
	/** Недействительность */
	@Comment("Недействительность")
	@Persist
	public Boolean getNoActuality() {return theNoActuality;}
	public void setNoActuality(Boolean a_Property) {theNoActuality = a_Property;}
	
	/** Информация по вакцине */
	@Comment("Информация по вакцине")
	@Persist
	public String getVaccineInfo() {return theVaccineInfo;}
	public void setVaccineInfo(String aVaccineInfo) {theVaccineInfo = aVaccineInfo;}

	/** Разрешил (инфо) */
	@Comment("Разрешил (инфо)")
	@Persist
	public String getPermitWorkerInfo() {return thePermitWorkerInfo;}
	public void setPermitWorkerInfo(String aPermitWorkerInfo) {thePermitWorkerInfo = aPermitWorkerInfo;}

	/** Исполнитель (инфо) */
	@Comment("Исполнитель (инфо)")
	@Persist
	public String getExecuteWorkerInfo() {return theExecuteWorkerInfo;}
	public void setExecuteWorkerInfo(String aExecuteWorkerInfo) {theExecuteWorkerInfo = aExecuteWorkerInfo;}

	/** Фаза (текст) */
	@Comment("Фаза (текст)")
	@Persist
	public String getPhaseText() {return thePhaseText;}
	public void setPhaseText(String aPhaseText) {thePhaseText = aPhaseText;}

	/** Метод (текст) */
	@Comment("Метод (текст)")
	@Persist
	public String getMethodText() {return theMethodText;}
	public void setMethodText(String aMethodText) {theMethodText = aMethodText;}

	/** Вакцинный материал (текст) */
	@Comment("Вакцинный материал (текст)")
	@Persist
	public String getMaterialText() {return theMaterialText;}
	public void setMaterialText(String aMaterialText) {theMaterialText = aMaterialText;}

	/** Дата создания */
	@Comment("Дата создания")
	@Persist @DoDateString @DateString
	public String getCreateDate() {return theCreateDate;}
	public void setCreateDate(String aCreateDate) {theCreateDate = aCreateDate;}

	/** Пользователь, создавший запись */
	@Comment("Пользователь, создавший запись")
	@Persist
	public String getCreateUsername() {return theCreateUsername;}
	public void setCreateUsername(String aCreateUsername) {theCreateUsername = aCreateUsername;}

	/** Реакция вакцинации */
	@Comment("Реакция вакцинации")
	@Persist
	public Long getVaccinationReaction() {return theVaccinationReaction;}
	public void setVaccinationReaction(Long aVaccinationReaction) {theVaccinationReaction = aVaccinationReaction;}

	/** Реакция вакцинации */
	private Long theVaccinationReaction;
	/** Пользователь, создавший запись */
	private String theCreateUsername;
	/** Дата создания */
	private String theCreateDate;
	/** Вакцинный материал (текст) */
	private String theMaterialText;
	/** Недействительность */
	private Boolean theNoActuality;
	/** Информация по вакцине */
	private String theVaccineInfo;
	/** Исполнитель (инфо) */
	private String theExecuteWorkerInfo;
	/** Метод (текст) */
	private String theMethodText;
	/** Фаза (текст) */
	private String thePhaseText;
	/** Разрешил (инфо) */
	private String thePermitWorkerInfo;
	/** Тип причины вакцинации */
	private Long theReasonType;
	/** Фаза вакцинации */
	private Long thePhase;
	/** Дата следующей вакцинации */
	private String theNextDate;
	/** Дата, на которую планировалась вакцинация */
	private String thePlanDate;
	/** Контрольный номер */
	private String theControlNumber;
	/** Метод вакцинации */
	private Long theMethod;
	/** Серия */
	private String theSeries;
	/**  Время исполнения */
	private String theExecuteTime;
	/** Вакцина */
	private Long theVaccine;
	/** Вакцинный материал	 */
	private Long theMaterial;
	/**  Исполнитель */
	private Long theExecuteWorker;
	/**  Дата исполнения  */
	private String theExecuteDate;
	/** Доза */
	private String theDose;
	/** Разрешил */
	private Long thePermitWorker;
	/** Дата окончания годности */
	private String theExpirationDate;
	/** СМО */
	private Long theMedCase;
	/** Пациент */
	private Long thePatient;
	/** Комментарии */
	private String theComments;
	/** Медотвод */
	private Long theEstop;
	/** Оценка */
	private Long theAssesment;

}
