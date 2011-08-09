package ru.ecom.mis.ejb.form.diet;
	
import ru.nuzmsh.commons.formpersistence.annotation.Parent;
import ru.ecom.ejb.form.simple.IdEntityForm;
import ru.ecom.ejb.services.entityform.WebTrail;
import ru.ecom.mis.ejb.domain.diet.MealQualityControl;
import ru.nuzmsh.commons.formpersistence.annotation.Comment;
import ru.nuzmsh.commons.formpersistence.annotation.EntityForm;
import ru.nuzmsh.commons.formpersistence.annotation.EntityFormSecurityPrefix;
import ru.nuzmsh.commons.formpersistence.annotation.Persist;
import ru.nuzmsh.ejb.formpersistence.annotation.EntityFormPersistance;
import ru.nuzmsh.forms.validator.transforms.DoDateString;
import ru.nuzmsh.forms.validator.validators.DateString;
	
	/**
	 * Журнал контроля качества готовой пищи (УФ 6-лп)
	 * @author oegorova
	 *
	 */
	
	@EntityForm
	@EntityFormPersistance(clazz = MealQualityControl.class)
	@Comment("Журнал контроля качества готовой пищи (УФ 6-лп)")
	@WebTrail(comment = "Журнал контроля качества готовой пищи (УФ 6-лп)", nameProperties= "id", view="entityView-diet_mealQualityControl.do")
	@Parent(property="diet", parentForm=DietForm.class)
	@EntityFormSecurityPrefix("/Policy/Mis/InvalidFood/MealQualityControl")
	public class MealQualityControlForm extends IdEntityForm{
		
		/** Диета */
		@Comment("Диета")
		@Persist
		public Long getDiet() {
			return theDiet;
		}
		public void setDiet(Long aDiet) {
			theDiet = aDiet;
		}
		/** Диета */
		private Long theDiet;
		
		/** Информация о снявшем пробу */
	@Comment("Информация о снявшем пробу")
	@Persist
	public String getTesterInfo() {
		return theTesterInfo;
	}
	
	public void setTesterInfo(String aTesterInfo) {
		theTesterInfo = aTesterInfo;
	}
	
	/** Информация о снявшем пробу */
	private String theTesterInfo;
		
	/** Информация о дежурном враче */
	@Comment("Информация о дежурном враче")
	@Persist
	public String getDutyDoctorInfo() {
		return theDutyDoctorInfo;
	}
	
	public void setDutyDoctorInfo(String aDutyDoctorInfo) {
		theDutyDoctorInfo = aDutyDoctorInfo;
	}
	
	/** Информация о дежурном враче */
	private String theDutyDoctorInfo;
		
		/** Разрешение на выдачу */
		@Comment("Разрешение на выдачу")
		@Persist
		public Boolean getIssuePermission() {
			return theIssuePermission;
		}
	
		public void setIssuePermission(Boolean aIssuePermission) {
			theIssuePermission = aIssuePermission;
		}
	
		/** Разрешение на выдачу */
		private Boolean theIssuePermission;
		
		/** Оценка санитарии */
		@Comment("Оценка санитарии")
		@Persist
		public String getEstimateSanitation() {
			return theEstimateSanitation;
		}
	
		public void setEstimateSanitation(String aEstimateSanitation) {
			theEstimateSanitation = aEstimateSanitation;
		}
	
		/** Оценка санитарии */
		private String theEstimateSanitation;
		
		/** Оценка веса */
		@Comment("Оценка веса")
		@Persist
		public String getEstimateWeight() {
			return theEstimateWeight;
		}
	
		public void setEstimateWeight(String aEstimateWeight) {
			theEstimateWeight = aEstimateWeight;
		}
	
		/** Оценка веса */
		private String theEstimateWeight;
		
		/** Оценка приготовления */
		@Comment("Оценка приготовления")
		@Persist
		public String getEstimatePreparation() {
			return theEstimatePreparation;
		}
	
		public void setEstimatePreparation(String aEstimatePreparation) {
			theEstimatePreparation = aEstimatePreparation;
		}
	
		/** Оценка приготовления */
		private String theEstimatePreparation;
		
		/** Оценка блюда */
		@Comment("Оценка блюда")
		@Persist
		public String getEstimateDish() {
			return theEstimateDish;
		}
	
		public void setEstimateDish(String aEstimateDish) {
			theEstimateDish = aEstimateDish;
		}
	
		/** Оценка блюда */
		private String theEstimateDish;
		
		/** Оценка меню */
		@Comment("Оценка меню")
		@Persist
		public String getEstimateMenu() {
			return theEstimateMenu;
		}
	
		public void setEstimateMenu(String aEstimateMenu) {
			theEstimateMenu = aEstimateMenu;
		}
	
		/** Оценка меню */
		private String theEstimateMenu;
		
		/** Прием пищи */
		@Comment("Прием пищи")
		@Persist
		public Long getMealTime() {
			return theMealTime;
		}
	
		public void setMealTime(Long aMealTime) {
			theMealTime = aMealTime;
		}
	
		/** Прием пищи */
		private Long theMealTime;
		
		/** Дата контроля */
		@Comment("Дата контроля")
		@Persist
		@DateString
		@DoDateString
		public String getControlDate() {
			return theControlDate;
		}
	
		public void setControlDate(String aControlDate) {
			theControlDate = aControlDate;
		}
	
		/** Дата контроля */
		private String theControlDate;
	
	}
