package ru.ecom.mis.ejb.form.expert;

import lombok.Setter;
import ru.ecom.ejb.form.simple.IdEntityForm;
import ru.ecom.ejb.services.entityform.WebTrail;
import ru.ecom.ejb.services.entityform.interceptors.AParentEntityFormInterceptor;
import ru.ecom.ejb.services.entityform.interceptors.AParentPrepareCreateInterceptors;
import ru.ecom.mis.ejb.domain.expert.QualityEstimation;
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
@EntityFormPersistance(clazz = QualityEstimation.class)
@Comment("Оценочные баллы")
@WebTrail(comment = "Оценочные баллы", nameProperties = "id", view = "entityParentView-expert_qualityEstimation.do")
@EntityFormSecurityPrefix("/Policy/Mis/MedCase/QualityEstimationCard/QualityEstimation")
@Parent(property = "card", parentForm =QualityEstimationCardForm.class)
@AParentPrepareCreateInterceptors(
        @AParentEntityFormInterceptor(QualityEstimationPrepareCreateInterceptor.class)
)
@Setter
public class QualityEstimationForm extends IdEntityForm {
	 /**
	  * Карта оценки качества
	  */
	 @Comment("Карта оценки качества")
	 @Persist
	 public Long getCard() {
	  return card;
	 }
	 /**
	  * Карта оценки качества
	  */
	 private Long card;
	 /**
	  * Критерии оценки качества
	  */
	 @Comment("Критерии оценки качества")
	 @Required
	 public String getCriterions() {
	  return criterions;
	 }
	 /**
	  * Критерии оценки качества
	  */
	 private String criterions;
	 /**
	  * Эсперт
	  */
	 @Comment("Эсперт")
	 @Persist @Required
	 public Long getExpert() {
	  return expert;
	 }

	 /** Тип эксперта */
	@Comment("Тип эксперта")
	@Persist @Required
	public String getExpertType() {
		return expertType;
	}

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

	private String createUsername;
	private String createDate;
	/** Тип эксперта */
	private String expertType;
	 /**
	  * Эсперт
	  */
	 private Long expert;

	@Comment("Является ли черновиком")
	@Persist
	public Boolean getIsDraft() {
		return isDraft;
	}
	/** Является ли черновиком */
	private Boolean isDraft;
}