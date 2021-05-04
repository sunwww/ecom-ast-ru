package ru.ecom.mis.ejb.form.expert.voc;

import lombok.Setter;
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
@Setter
public class VocQualityEstimationCritForm extends IdEntityForm {
		/** Короткое название*/
		 @Comment("Короткое название")
		 @Persist @Required
		 public String getShortName() {return shortName;}
		 /** Вид оценки качества*/
		 @Comment("Вид оценки качества")
		 @Persist @Required
		 public Long getKind() {return kind;}
		/** Наименование */
		@Comment("Наименование")
		@Persist @Required
		public String getName() {return name;}

		/** Код */
		@Comment("Код")
		@Persist @Required
		public String getCode() {return code;}

		 /** Тип критерия */
		@Comment("Тип критерия")
		@Persist
		public Long getType() {return type;}

		/** Родитель */
		@Comment("Родитель")
		@Persist
		public Long getParent() {return parent;}

		/** Коды медицинских услуг */
		@Comment("Коды медицинских услуг")
		@Persist
		public String getMedServiceCodes() {
			return medServiceCodes;
		}

		/** Логический тип критерия? */
		@Comment("Логический тип критерия?")
		@Persist
		public Boolean getIsBoolean() {
			return isBoolean;
		}

		/** Для взрослых? */
		@Comment("Для взрослых?")
		@Persist
		public Boolean getIsGrownup() {
			return isGrownup;
		}

		/** Для детей? */
		@Comment("Для детей?")
		@Persist
		public Boolean getIsChild() {
			return isChild;
		}

		/** Родитель */
		private Long parent;
		/** Тип критерия */
		private Long type;
		/** Короткое название */
		private String shortName;
		/** Код */
		private String code;
		/** Наименование */
		private String name;
		/** Вид оценки качества*/
		private Long kind;
		/** Коды медицинских услуг */
		private String medServiceCodes;
		/** Логический тип критерия? */
		private Boolean isBoolean;
		/** Для взрослых? */
		private Boolean isGrownup;
		/** Для детей? */
		private Boolean isChild;
}
