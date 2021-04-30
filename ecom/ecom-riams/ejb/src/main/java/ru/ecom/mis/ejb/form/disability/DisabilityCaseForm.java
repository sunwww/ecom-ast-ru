package ru.ecom.mis.ejb.form.disability;

import lombok.Setter;
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
			list="entityParentList-dis_case.do",listComment="СНТ по пациенту"
			, shortView="entityShortView-dis_case.do"
		)
@Parent(property="patient", parentForm=PatientForm.class)
@EntityFormSecurityPrefix("/Policy/Mis/Disability/Case")
@Setter
public class DisabilityCaseForm extends IdEntityForm {

	/** Пациент */
	@Comment("Пациент")
	@Persist @Required
	public Long getPatient() {return patient;}

	/** СМО */
	@Comment("СМО")
	@Persist
	public Long getMedCase() {return medCase;}

	/** Дата начала */
	@Comment("Дата начала")
	@DateString @DoDateString @Persist 
	public String getDateFrom() {return dateFrom;}

	/** Дата окончания */
	@Comment("Дата окончания")
	@DateString @DoDateString @Persist
	public String getDateTo() {return dateTo;}

	/** Продолжительность */
	@Comment("Продолжительность")
	@Persist
	public String getDuration() {return duration;}

	/** Родительский случай нетрудоспособности */
	@Comment("Родительский случай нетрудоспособности")
	@Persist
	public Long getParentDisabiblityCase() {return parentDisabiblityCase;}

	/** Пациент */
	private Long patient;
	/** СМО */
	private Long medCase;
	/** Дата начала */
	private String dateFrom;
	/** Дата окончания */
	private String dateTo;
	/** Продолжительность */
	private String duration;
	/** Родительский случай нетрудоспособности */
	private Long parentDisabiblityCase;
	/**
	 * Состоит на учете в службе занятости
	 */
	@Comment("Состоит на учете в службе занятости")
	@Persist
	public Boolean getPlacementService() {return placementService;}
	/**
	 * Состоит на учете в службе занятости
	 */
	private Boolean placementService;
	/**
	 * Поставлена на учет в ранние сроки беременности (до 12 недель)
	 */
	@Comment("Поставлена на учет в ранние сроки беременности (до 12 недель)")
	@Persist
	public Boolean getEarlyPregnancyRegistration() {return earlyPregnancyRegistration;}
	/**
	 * Поставлена на учет в ранние сроки беременности (до 12 недель)
	 */
	private Boolean earlyPregnancyRegistration;


	/** Лицо по уходу 1*/
	@Comment("Лицо по уходу 1")
	@Persist
	public Long getNursingPerson1() {return nursingPerson1;}

	/** Лицо по уходу 2*/
	@Comment("Лицо по уходу 2")
	@Persist
	public Long getNursingPerson2() {return nursingPerson2;}

	/** Лицо по уходу 2*/
	private Long nursingPerson2;
	/** Лицо по уходу 1*/
	private Long nursingPerson1;
	/** Пользователь, создавший документ */
	@Comment("Пользователь, создавший документ")
	@Persist
	public String getCreateUsername() {return createUsername;}

	/** Дата создания */
	@Comment("Дата создания")
	@Persist @DateString @DoDateString
	public String getCreateDate() {return createDate;}

	/** Пользователь, редактировавший документ */
	@Comment("Пользователь, редактировавший документ")
	@Persist
	public String getEditUsername() {return editUsername;}

	/** Дата редактирования */
	@Comment("Дата редактирования")
	@Persist @DateString @DoDateString
	public String getEditDate() {return editDate;}

	/** Дата редактирования */
	private String editDate;
	/** Пользователь, редактировавший документ */
	private String editUsername;
	/** Дата создания */
	private String createDate;
	/** Пользователь, создавший документ */
	private String createUsername;
}
