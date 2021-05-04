package ru.ecom.mis.ejb.form.expert;

import lombok.Setter;
import ru.ecom.ejb.form.simple.IdEntityForm;
import ru.ecom.ejb.services.entityform.WebTrail;
import ru.ecom.mis.ejb.domain.expert.QualityEstimationCard;
import ru.ecom.mis.ejb.form.medcase.MedCaseForm;
import ru.nuzmsh.commons.formpersistence.annotation.Comment;
import ru.nuzmsh.commons.formpersistence.annotation.EntityForm;
import ru.nuzmsh.commons.formpersistence.annotation.EntityFormSecurityPrefix;
import ru.nuzmsh.commons.formpersistence.annotation.Parent;
import ru.nuzmsh.commons.formpersistence.annotation.Persist;
import ru.nuzmsh.ejb.formpersistence.annotation.EntityFormPersistance;
import ru.nuzmsh.forms.validator.transforms.DoDateString;
import ru.nuzmsh.forms.validator.validators.DateString;
import ru.nuzmsh.forms.validator.validators.Required;

@EntityForm
@EntityFormPersistance(clazz = QualityEstimationCard.class)
@Comment("Экспертная карта")
@WebTrail(comment = "Экспертная карта", nameProperties = "id", view = "entityView-expert_card.do")
@Parent(property = "medcase", parentForm=MedCaseForm.class)
@EntityFormSecurityPrefix("/Policy/Mis/MedCase/QualityEstimationCard")
@Setter
public class QualityEstimationCardForm extends IdEntityForm{
	/**
	  * Номер карты
	  */
	 @Comment("Номер карты")
	 @Persist
	 public String getCardNumber() {
	  return cardNumber;
	 }
	 /**
	  * Номер карты
	  */
	 private String cardNumber;
	 /**
	  * Случай медицинского обслуживания
	  */
	 @Comment("Случай медицинского обслуживания")
	 @Persist @Required
	 public Long getMedcase() {
	  return medcase;
	 }
	 /**
	  * Случай медицинского обслуживания
	  */
	 private Long medcase;
	 /**
	  * Вид оценки качества
	  */
	 @Comment("Вид оценки качества")
	 @Persist @Required
	 public Long getKind() {
	  return kind;
	 }
	 /**
	  * Вид оценки качества
	  */
	 private Long kind;
	 
	 /** Пациент */
	@Comment("Пациент")
	@Persist 
	public Long getPatient() {
		return patient;
	}

	/** Пациент */
	private Long patient;
	/** Дата создания */
	@Comment("Дата создания")
	@Persist @DateString @DoDateString
	public String getCreateDate() {
		return createDate;
	}

	/** Пользователь, создавший экспертную карту */
	@Comment("Пользователь, создавший экспертную карту")
	@Persist
	public String getCreateUsername() {
		return createUsername;
	}


	/** Лечащий врач */
	@Comment("Лечащий врач")
	@Persist 
	public Long getDoctorCase() {
		return doctorCase;
	}

	/** Отделение */
	@Comment("Отделение")
	@Persist 
	public Long getDepartment() {
		return department;
	}

	/** Диагноз */
	@Comment("Диагноз")
	@Persist
	public Long getIdc10() {
		return idc10;
	}

	/** Текст диагноза */
	@Comment("Текст диагноза")
	@Persist
	public String getDiagnosis() {
		return diagnosis;
	}

	/** СЛО */
	@Comment("СЛО")
	public Long getSlo() {
		return slo;
	}

	/** СЛО */
	private Long slo;

	/** Текст диагноза */
	private String diagnosis;

	/** Диагноз */
	private Long idc10;

	/** Отделение */
	private Long department;

	/** Лечащий врач */
	private Long doctorCase;
	/** Пользователь, создавший экспертную карту */
	private String createUsername;

	/** Дата создания */
	private String createDate;
}
