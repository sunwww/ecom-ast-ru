package ru.ecom.mis.ejb.form.psychiatry;

import ru.ecom.ejb.services.entityform.WebTrail;
import ru.ecom.mis.ejb.domain.psychiatry.PsychiaticObservation;
import ru.nuzmsh.commons.formpersistence.annotation.Comment;
import ru.nuzmsh.commons.formpersistence.annotation.EntityForm;
import ru.nuzmsh.commons.formpersistence.annotation.EntityFormSecurityPrefix;
import ru.nuzmsh.commons.formpersistence.annotation.Parent;
import ru.nuzmsh.commons.formpersistence.annotation.Persist;
import ru.nuzmsh.ejb.formpersistence.annotation.EntityFormPersistance;
import ru.nuzmsh.forms.validator.validators.Required;

@EntityForm
@EntityFormPersistance(clazz = PsychiaticObservation.class)
@Comment("Динамика наблюдений (АДН, АПЛ)")
@WebTrail(comment = "Динамика наблюдений (АДН, АПЛ)", nameProperties= "id", view="entityParentView-psych_careobservation.do")
@Parent(property="careCard", parentForm=PsychiatricCareCardForm.class,orderBy="startDate")
@EntityFormSecurityPrefix("/Policy/Mis/Psychiatry/CareCard/PsychiaticObservation")
public class PsychiatricObservationByCareCardForm extends PsychiatricObservationForm {
	 /**
	  * Диспансерная группа
	  */
	 @Comment("Диспансерная группа")
	 @Persist @Required
	 public Long getDispensaryGroup() {
	  return theDispensaryGroup;
	 }
	 public void setDispensaryGroup(Long aDispensaryGroup) {
	  theDispensaryGroup = aDispensaryGroup;
	 }
		@Comment("Статья уголовного кодекса")
		@Persist @Required
		public Long getCriminalCodeArticle() {
			return theCriminalCodeArticle;
		}

		public void setCriminalCodeArticle(Long aCriminalCodeArticle) {
			theCriminalCodeArticle = aCriminalCodeArticle;
		}

		/** Статья уголовного кодекса */
		private Long theCriminalCodeArticle;
		
	 /**
	  * Диспансерная группа
	  */
	 private Long theDispensaryGroup;

}
