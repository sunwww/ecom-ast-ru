package ru.ecom.mis.ejb.form.expert.voc;

import ru.ecom.ejb.form.simple.IdEntityForm;
import ru.ecom.ejb.services.entityform.WebTrail;
import ru.ecom.mis.ejb.domain.expert.voc.VocQualityEstimationCrit;
import ru.nuzmsh.commons.formpersistence.annotation.*;
import ru.nuzmsh.ejb.formpersistence.annotation.EntityFormPersistance;
import ru.nuzmsh.forms.validator.validators.Required;

/**
 * Справочник критериев оценки качества
 */
@EntityForm
@EntityFormPersistance(clazz = VocQualityEstimationCrit.class)
@Comment("Справочник видов оценок качества")
@WebTrail(comment = "Вид оценок качества", nameProperties = {"code","name"}, view = "entityParentView-exp_vocCrit.do")
@EntityFormSecurityPrefix("/Policy/Voc/VocQualityEstimationCrit")
@Parent(property = "parent", parentForm = VocQualityEstimationCritForm.class)
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
		public Long getType() {return theType;}
		public void setType(Long aType) {theType = aType;}

		
		/** Родитель */
		@Comment("Родитель")
		@Persist
		public Long getParent() {return theParent;}
		public void setParent(Long aParent) {theParent = aParent;}

		/** Коды медицинских услуг */
		@Comment("Коды медицинских услуг")
		@Persist
		public String getMedServiceCodes() {
			return theMedServiceCodes;
		}
		public void setMedServiceCodes(String medServiceCodes) {
			theMedServiceCodes = medServiceCodes;
		}

		/** Логический тип критерия? */
		@Comment("Логический тип критерия?")
		@Persist
		public Boolean getIsBoolean() {
			return theIsBoolean;
		}
		public void setIsBoolean(Boolean aIsBoolean) {
			theIsBoolean = aIsBoolean;
		}

		/** Для взрослых? */
		@Comment("Для взрослых?")
		@Persist
		public Boolean getIsGrownup() {
			return theIsGrownup;
		}
		public void setIsGrownup(Boolean aIsGrownup) {
			theIsGrownup = aIsGrownup;
		}

		/** Для детей? */
		@Comment("Для детей?")
		@Persist
		public Boolean getIsChild() {
			return theIsChild;
		}
		public void setIsChild(Boolean aIsChild) {
			theIsChild = aIsChild;
		}


		/** Родитель */
		private Long theParent;
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
		/** Коды медицинских услуг */
		private String theMedServiceCodes;
		/** Логический тип критерия? */
		private Boolean theIsBoolean;
		/** Для взрослых? */
		private Boolean theIsGrownup;
		/** Для детей? */
		private Boolean theIsChild;
}
