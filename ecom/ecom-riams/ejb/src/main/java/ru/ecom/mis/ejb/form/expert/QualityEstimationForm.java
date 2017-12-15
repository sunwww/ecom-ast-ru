package ru.ecom.mis.ejb.form.expert;

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
public class QualityEstimationForm extends IdEntityForm {
	 /**
	  * Карта оценки качества
	  */
	 @Comment("Карта оценки качества")
	 @Persist
	 public Long getCard() {
	  return theCard;
	 }
	 public void setCard(Long aCard) {
	  theCard = aCard;
	 }
	 /**
	  * Карта оценки качества
	  */
	 private Long theCard;
	 /**
	  * Критерии оценки качества
	  */
	 @Comment("Критерии оценки качества")
	 @Required
	 public String getCriterions() {
	  return theCriterions;
	 }
	 public void setCriterions(String aCriterions) {
	  theCriterions = aCriterions;
	 }
	 /**
	  * Критерии оценки качества
	  */
	 private String theCriterions;
	 /**
	  * Эсперт
	  */
	 @Comment("Эсперт")
	 @Persist @Required
	 public Long getExpert() {
	  return theExpert;
	 }
	 public void setExpert(Long aExpert) {
	  theExpert = aExpert;
	 }
	 
	 /** Тип эксперта */
	@Comment("Тип эксперта")
	@Persist @Required
	public String getExpertType() {
		return theExpertType;
	}

	public void setExpertType(String aExpertType) {
		theExpertType = aExpertType;
	}
	/** Дата создания */
	@Comment("Дата создания")
	@Persist @DateString @DoDateString
	public String getCreateDate() {
		return theCreateDate;
	}

	public void setCreateDate(String aNAME) {
		theCreateDate = aNAME;
	}
	/** Пользователь, создавший экспертную карту */
	@Comment("Пользователь, создавший экспертную карту")
	@Persist
	public String getCreateUsername() {
		return theCreateUsername;
	}

	public void setCreateUsername(String aCreateUsername) {
		theCreateUsername = aCreateUsername;
	}
	

	private String theCreateUsername;
	private String theCreateDate;
	/** Тип эксперта */
	private String theExpertType;
	 /**
	  * Эсперт
	  */
	 private Long theExpert;

	@Comment("Является ли черновиком")
	@Persist
	public Boolean getIsDraft() {
		return theIsDraft;
	}
	public void setIsDraft(Boolean aIsDraft) {
		theIsDraft = aIsDraft;
	}
	/** Является ли черновиком */
	private Boolean theIsDraft;
}