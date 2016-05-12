package ru.ecom.mis.ejb.form.psychiatry;

import ru.ecom.ejb.form.simple.IdEntityForm;
import ru.ecom.ejb.services.entityform.WebTrail;
import ru.ecom.mis.ejb.domain.psychiatry.Suicide;
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
@EntityFormPersistance(clazz = Suicide.class)
@Comment("Суицид")
@WebTrail(comment = "Суицид", nameProperties= "id",list="entityParentList-psych_suicide.do",listComment="список по пациенту", view="entityParentView-psych_suicide.do")
@Parent(property="careCard", parentForm=PsychiatricCareCardForm.class,orderBy="fulfilmentDate")
@EntityFormSecurityPrefix("/Policy/Mis/Psychiatry/CareCard/Suicide")
public class SuicideForm extends IdEntityForm {
	 /**
	  * Карта обратившегося за психиатрической помощью
	  */
	 @Comment("Карта обратившегося за психиатрической помощью")
	 @Persist @Required
	 public Long getCareCard() {
	  return theCareCard;
	 }
	 public void setCareCard(Long aCareCard) {
	  theCareCard = aCareCard;
	 }
	 /**
	  * Карта обратившегося за психиатрической помощью
	  */
	 private Long theCareCard;
	 /**
	  * Дата совершения
	  */
	 @Comment("Дата совершения")
	 @Persist @DoDateString @DateString @Required
	 public String getFulfilmentDate() {
	  return theFulfilmentDate;
	 }
	 public void setFulfilmentDate(String aFulfilmentDate) {
	  theFulfilmentDate = aFulfilmentDate;
	 }
	 /**
	  * Дата совершения
	  */
	 private String theFulfilmentDate;
	 /**
	  * Описание
	  */
	 @Comment("Описание")
	 @Persist @Required
	 public String getNotes() {
	  return theNotes;
	 }
	 public void setNotes(String aNotes) {
	  theNotes = aNotes;
	 }
	 	/** Характер суицида */
		@Comment("Характер суицида")
		@Persist @Required
		public Long getNature() {
			return theNature;
		}

		public void setNature(Long aNature) {
			theNature = aNature;
		}
		
		/** Завершен? */
		@Comment("Завершен?")
		@Persist
		public Boolean getIsFinished() {
			return theIsFinished;
		}

		public void setIsFinished(Boolean aIsFinished) {
			theIsFinished = aIsFinished;
		}

		/** Завершен? */
		private Boolean theIsFinished;

		/** Характер суицида */
		private Long theNature;
		/**
	  * Описание
	  */
	 private String theNotes;
	 /** Дата регистрации */
	@Comment("Дата регистрации")
	@Persist @DateString @DoDateString @Required
	public String getRegistrationDate() {
		return theRegistrationDate;
	}
	
	public void setRegistrationDate(String aRegistrationDate) {
		theRegistrationDate = aRegistrationDate;
	}
	
	/** Дата регистрации */
	private String theRegistrationDate;

}
