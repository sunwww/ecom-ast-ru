package ru.ecom.mis.ejb.form.vaccination;

import lombok.Setter;
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
@Setter
public class VaccinationForm extends IdEntityForm {

	/** Вакцина */
	@Comment("Вакцина")
	@Persist @Required 
	public Long getVaccine() {return vaccine;}

	/** Вакцинный материал	 */
	@Comment("Вакцинный материал")
	@Persist 
	public Long getMaterial() {return material;}

	/**  Исполнитель */
	@Comment("Исполнитель")
	@Persist @Required
	public Long getExecuteWorker() {return executeWorker;}

	/**  Дата исполнения  */
	@Comment("Дата исполнения")
	@DateString @DoDateString
	@Persist @Required
	public String getExecuteDate() {return executeDate;}

	/**  Время исполнения */
	@Comment("Время исполнения")
	@TimeString @DoTimeString
	@Persist @Required
	public String  getExecuteTime() {return executeTime;}

	/** Доза */
	@Comment("Доза")
	@Persist @Required
	public String getDose() {return dose;}

	/** Серия */
	@Comment("Серия")
	@Persist @Required @DoUpperCase
	public String getSeries() {return series;}

	/** Метод вакцинации */
	@Comment("Метод вакцинации")
	@Persist @Required
	public Long getMethod() {return method;}

	/** Контрольный номер */
	@Comment("Контрольный номер")
	@Persist @Required
	public String getControlNumber() {return controlNumber;}

	/** Дата, на которую планировалась вакцинация */
	@Comment("Дата, на которую планировалась вакцинация")
	@DateString @DoDateString
	@Persist
	public String getPlanDate() {return planDate;}

	/** Дата следующей вакцинации */
	@Comment("Дата следующей вакцинации")
	@DateString @DoDateString
	@Persist
	public String getNextDate() {return nextDate;}

	/** Фаза вакцинации */
	@Comment("Фаза вакцинации")
	@Persist @Required
	public Long getPhase() {return phase;}

	/** Тип причины вакцинации */
	@Comment("Тип причины вакцинации")
	@Persist @Required
	public Long getReasonType() {return reasonType;}

	/** Комментарии */
	@Comment("Комментарии")
	@Persist
	public String getComments() {return comments;}

	/** Медотвод */
	@Comment("Медотвод")
	@Persist
	public Long getEstop() {return estop;}

	/** Оценка */
	@Comment("Оценка")
	@Persist
	public Long getAssesment() {return assesment;}

	/** Пациент */
	@Comment("Пациент")
	@Persist
	public Long getPatient() {return patient;}

	/** СМО */
	@Comment("СМО")
	@Persist @Required
	public Long getMedCase() {return medCase;}

	/** Дата окончания годности */
	@Comment("Дата окончания годности")
	@DateString @DoDateString
	@Persist @Required
	public String getExpirationDate() {return expirationDate;}

	/** Разрешил */
	@Comment("Разрешил")
	@Persist @Required
	public Long getPermitWorker() {return permitWorker;}

	/** Недействительность */
	@Comment("Недействительность")
	@Persist
	public Boolean getNoActuality() {return noActuality;}

	/** Информация по вакцине */
	@Comment("Информация по вакцине")
	@Persist
	public String getVaccineInfo() {return vaccineInfo;}

	/** Разрешил (инфо) */
	@Comment("Разрешил (инфо)")
	@Persist
	public String getPermitWorkerInfo() {return permitWorkerInfo;}

	/** Исполнитель (инфо) */
	@Comment("Исполнитель (инфо)")
	@Persist
	public String getExecuteWorkerInfo() {return executeWorkerInfo;}

	/** Фаза (текст) */
	@Comment("Фаза (текст)")
	@Persist
	public String getPhaseText() {return phaseText;}

	/** Метод (текст) */
	@Comment("Метод (текст)")
	@Persist
	public String getMethodText() {return methodText;}

	/** Вакцинный материал (текст) */
	@Comment("Вакцинный материал (текст)")
	@Persist
	public String getMaterialText() {return materialText;}

	/** Дата создания */
	@Comment("Дата создания")
	@Persist @DoDateString @DateString
	public String getCreateDate() {return createDate;}

	/** Пользователь, создавший запись */
	@Comment("Пользователь, создавший запись")
	@Persist
	public String getCreateUsername() {return createUsername;}

	/** Реакция вакцинации */
	@Comment("Реакция вакцинации")
	@Persist
	public Long getVaccinationReaction() {return vaccinationReaction;}

	/** Реакция вакцинации */
	private Long vaccinationReaction;
	/** Пользователь, создавший запись */
	private String createUsername;
	/** Дата создания */
	private String createDate;
	/** Вакцинный материал (текст) */
	private String materialText;
	/** Недействительность */
	private Boolean noActuality;
	/** Информация по вакцине */
	private String vaccineInfo;
	/** Исполнитель (инфо) */
	private String executeWorkerInfo;
	/** Метод (текст) */
	private String methodText;
	/** Фаза (текст) */
	private String phaseText;
	/** Разрешил (инфо) */
	private String permitWorkerInfo;
	/** Тип причины вакцинации */
	private Long reasonType;
	/** Фаза вакцинации */
	private Long phase;
	/** Дата следующей вакцинации */
	private String nextDate;
	/** Дата, на которую планировалась вакцинация */
	private String planDate;
	/** Контрольный номер */
	private String controlNumber;
	/** Метод вакцинации */
	private Long method;
	/** Серия */
	private String series;
	/**  Время исполнения */
	private String executeTime;
	/** Вакцина */
	private Long vaccine;
	/** Вакцинный материал	 */
	private Long material;
	/**  Исполнитель */
	private Long executeWorker;
	/**  Дата исполнения  */
	private String executeDate;
	/** Доза */
	private String dose;
	/** Разрешил */
	private Long permitWorker;
	/** Дата окончания годности */
	private String expirationDate;
	/** СМО */
	private Long medCase;
	/** Пациент */
	private Long patient;
	/** Комментарии */
	private String comments;
	/** Медотвод */
	private Long estop;
	/** Оценка */
	private Long assesment;
}
