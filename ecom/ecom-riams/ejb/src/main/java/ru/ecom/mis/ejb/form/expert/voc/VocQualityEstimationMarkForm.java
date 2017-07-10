package ru.ecom.mis.ejb.form.expert.voc;

import ru.ecom.ejb.form.simple.IdEntityForm;
import ru.ecom.ejb.services.entityform.WebTrail;
import ru.ecom.mis.ejb.domain.expert.voc.VocQualityEstimationMark;
import ru.nuzmsh.commons.formpersistence.annotation.Comment;
import ru.nuzmsh.commons.formpersistence.annotation.EntityForm;
import ru.nuzmsh.commons.formpersistence.annotation.EntityFormSecurityPrefix;
import ru.nuzmsh.commons.formpersistence.annotation.Parent;
import ru.nuzmsh.commons.formpersistence.annotation.Persist;
import ru.nuzmsh.ejb.formpersistence.annotation.EntityFormPersistance;
import ru.nuzmsh.forms.validator.validators.Required;

/**
 * Справочник баллов критериев оценки качества
 */
@EntityForm
@EntityFormPersistance(clazz = VocQualityEstimationMark.class)
@Comment("Справочник видов оценок качества")
@WebTrail(comment = "Вид оценок качества", nameProperties = "name", view = "entityParentView-exp_vocMark.do")
@EntityFormSecurityPrefix("/Policy/Voc/VocQualityEstimationMark")
@Parent(property = "criterion", parentForm = VocQualityEstimationCritForm.class)
public class VocQualityEstimationMarkForm extends IdEntityForm{
		 /** Оценочный балл*/
		 @Comment("Оценочный балл")
		 @Persist 
		 public Double getMark() {return theMark;}
		 public void setMark(Double aMark) {theMark = aMark;}
		 
		 /** Критерий оценки качества*/
		 @Comment("Критерий оценки качества")
		 @Persist @Required
		 public Long getCriterion() {return theCriterion;}
		 public void setCriterion(Long aCriterion) {theCriterion = aCriterion;}
		 
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

		 /** Полное название */
		@Comment("Полное название")
		@Persist
		public String getFullname() {
			return theFullname;
		}

		public void setFullname(String aFullname) {
			theFullname = aFullname;
		}

		 /** Не учитывать */
		@Comment("Не учитывать")
		@Persist
		public Boolean getIsIgnore() {return theIsIgnore;}
		public void setIsIgnore(Boolean aIsIgnore) {theIsIgnore = aIsIgnore;}

		/** Не учитывать */
		private Boolean theIsIgnore;
		/** Полное название */
		private String theFullname;
		/** Оценочный балл*/
		 private Double theMark;
		/** Код */
		private String theCode;
		/** Наименование */
		private String theName;
		 /** Критерий оценки качества */
		 private Long theCriterion;
		 
		 /** Обязательно указывать примечание */
		 @Comment("Обязательно указывать примечание")
		 @Persist
		 public Boolean getIsNeedComment() {return theIsNeedComment;}
		 public void setIsNeedComment(Boolean aIsNeedComment) {theIsNeedComment = aIsNeedComment;}
		 /** Обязательно указывать примечание */
		 private Boolean theIsNeedComment;
}
