package ru.ecom.mis.ejb.form.expert.voc;

import ru.ecom.ejb.form.simple.IdEntityForm;
import ru.ecom.ejb.services.entityform.WebTrail;
import ru.ecom.mis.ejb.domain.expert.voc.VocQualityEstimationCrit;
import ru.nuzmsh.commons.formpersistence.annotation.Comment;
import ru.nuzmsh.commons.formpersistence.annotation.EntityForm;
import ru.nuzmsh.commons.formpersistence.annotation.EntityFormSecurityPrefix;
import ru.nuzmsh.commons.formpersistence.annotation.Parent;
import ru.nuzmsh.commons.formpersistence.annotation.Persist;
import ru.nuzmsh.ejb.formpersistence.annotation.EntityFormPersistance;
import ru.nuzmsh.forms.validator.validators.Required;

/**
 * Справочник критериев оценки качества
 */
@EntityForm
@EntityFormPersistance(clazz = VocQualityEstimationCrit.class)
@Comment("Справочник видов оценок качества")
@WebTrail(comment = "Вид оценок качества", nameProperties = "name", view = "entityParentView-exp_vocCrit.do")
@EntityFormSecurityPrefix("/Policy/Voc/VocQualityEstimationCrit")
@Parent(property = "kind", parentForm = VocQualityEstimationKindForm.class)
public class VocQualityEstimationCritForm extends IdEntityForm {
		/** Короткое название*/
		 @Comment("Короткое название")
		 @Persist @Required
		 public String getShortName() {return theShortName;}
		 public void setShortName(String aShortName) {theShortName = aShortName;}
		 /** Вид оценки качества*/
		 @Comment("Вид оценки качества")
		 @Persist @Required
		 public Long getKind() {return theKind;}
		 public void setKind(Long aKind) {theKind = aKind;}
		/** Наименование */
		@Comment("Наименование")
		@Persist @Required
		public String getName() {return theName;}
		public void setName(String aName) {theName = aName;}
		
		/** Код */
		@Comment("Код")
		@Persist @Required
		public String getCode() {return theCode;}
		public void setCode(String aCode) {theCode = aCode;}

		 /** Тип критерия */
		@Comment("Тип критерия")
		@Persist
		public Long getType() {
			return theType;
		}

		public void setType(Long aType) {
			theType = aType;
		}

		/** Тип критерия */
		private Long theType;
		/** Короткое название */
		 private String theShortName;
		/** Код */
		private String theCode;
		/** Наименование */
		private String theName;
		 /** Вид оценки качества*/
		 private Long theKind;
}
