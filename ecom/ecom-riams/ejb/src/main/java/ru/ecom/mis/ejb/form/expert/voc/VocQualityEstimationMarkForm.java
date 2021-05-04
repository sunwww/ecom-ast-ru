package ru.ecom.mis.ejb.form.expert.voc;

import lombok.Setter;
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
@Setter
public class VocQualityEstimationMarkForm extends IdEntityForm{
		 /** Оценочный балл*/
		 @Comment("Оценочный балл")
		 @Persist 
		 public Double getMark() {return mark;}

		 /** Критерий оценки качества*/
		 @Comment("Критерий оценки качества")
		 @Persist @Required
		 public Long getCriterion() {return criterion;}

		/** Наименование */
		@Comment("Наименование")
		@Persist @Required
		public String getName() {return name;}

		/** Код */
		@Comment("Код")
		@Persist @Required
		public String getCode() {return code;}

		 /** Полное название */
		@Comment("Полное название")
		@Persist
		public String getFullname() {
			return fullname;
		}

		 /** Не учитывать */
		@Comment("Не учитывать")
		@Persist
		public Boolean getIsIgnore() {return isIgnore;}

		/** Не учитывать */
		private Boolean isIgnore;
		/** Полное название */
		private String fullname;
		/** Оценочный балл*/
		 private Double mark;
		/** Код */
		private String code;
		/** Наименование */
		private String name;
		 /** Критерий оценки качества */
		 private Long criterion;
		 
		 /** Обязательно указывать примечание */
		 @Comment("Обязательно указывать примечание")
		 @Persist
		 public Boolean getIsNeedComment() {return isNeedComment;}
		 /** Обязательно указывать примечание */
		 private Boolean isNeedComment;
}
