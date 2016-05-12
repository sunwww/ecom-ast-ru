package ru.ecom.mis.ejb.form.lpu;

import ru.ecom.ejb.form.simple.IdEntityForm;
import ru.ecom.ejb.services.entityform.WebTrail;
import ru.ecom.mis.ejb.domain.lpu.BedFundCapacity;
import ru.nuzmsh.commons.formpersistence.annotation.Comment;
import ru.nuzmsh.commons.formpersistence.annotation.EntityForm;
import ru.nuzmsh.commons.formpersistence.annotation.EntityFormSecurityPrefix;
import ru.nuzmsh.commons.formpersistence.annotation.Persist;
import ru.nuzmsh.ejb.formpersistence.annotation.EntityFormPersistance;
import ru.nuzmsh.forms.validator.transforms.DoDateString;
import ru.nuzmsh.forms.validator.validators.DateString;
import ru.nuzmsh.forms.validator.validators.Required;

@Comment("Объемы коечного фонда")
@EntityForm
@EntityFormPersistance(clazz = BedFundCapacity.class)
@WebTrail(comment = "Объемы коечного фонда", nameProperties = "id", view = "entityView-mis_bedFundCapacity.do")
@EntityFormSecurityPrefix("/Policy/Mis/BedFund")

public class BedFundCapacityForm extends IdEntityForm{	 

		/** Профиль коек */
		@Comment("Профиль коек")
		@Persist @Required
		public Long getBedType() {return theBedType;}
		public void setBedType(Long aBedType) {theBedType = aBedType;}
		/** Профиль коек */
		private Long theBedType;
		
		/** Источник финансирования */
		@Comment("Источник финансирования")
		@Persist @Required
		public Long getFinanceSource() {return theFinanceSource;}
		public void setFinanceSource(Long aFinanceSource) {theFinanceSource = aFinanceSource;}
		/** Источник финансирования */
		private Long theFinanceSource;
		
		/** Фактическое количество коек */
		@Comment("Фактическое количество коек")
		@Persist
		public Long getActualBedCount() {return theActualBedCount;}
		public void setActualBedCount(Long aActualBedCount) {theActualBedCount = aActualBedCount;}
		/** Фактическое количество коек */
		private Long theActualBedCount;
		
		/** Дата начала действия */
		@Comment("Дата начала действия")
		@Persist @DoDateString @DateString 
		@Required
		public String getStartDate() {return theStartDate;}
		public void setStartDate(String aStartDate) {theStartDate = aStartDate;}
		/** Дата начала действия */
		private String theStartDate;
		
		/** Дата окончания действия */
		@Comment("Дата окончания действия")
		@Persist @DoDateString @DateString
		public String getFinishDate() {return theFinishDate;}
		public void setFinishDate(String aFinishDate) {theFinishDate = aFinishDate;}
		/** Дата окончания действия */
		private String theFinishDate;
		
		/** Средняя длительность лечения */
		@Comment("Средняя длительность лечения")
		@Persist
		public Float getPlanTreatmentDuration() {return thePlanTreatmentDuration;}
		public void setPlanTreatmentDuration(Float aPlanTreatmentDuration) {thePlanTreatmentDuration = aPlanTreatmentDuration;}
		/** Средняя длительность лечения */
		private Float thePlanTreatmentDuration;
		
		/** Количество дней работы в году (план) */
		@Comment("Количество дней работы в году (план)")
		@Persist
		public Long getWorkingDays() {return theWorkingDays;}
		public void setWorkingDays(Long aWorkingDays) {theWorkingDays = aWorkingDays;}
		/** Количество дней работы в году (план) */
		private Long theWorkingDays;
		
		/** Количество коек по плану*/
		@Comment("Количество коек по плану")
		@Persist @Required
		public Long getPlanBedCount() {return thePlanBedCount;}
		public void setPlanBedCount(Long aPlanBedCount) {thePlanBedCount = aPlanBedCount;}
		/** Количество коек по плану*/
		private Long thePlanBedCount;
		
		/** Количество койкодней (план) */
		@Comment("Количество койкодней (план)")
		@Persist
		public Long getCountDays() {return theCountDays;}
		public void setCountDays(Long aCountDays) {theCountDays = aCountDays;}
		/** Количество койкодней (план) */
		private Long theCountDays;
		
		/** Количество госпитализаций */
		@Comment("Количество госпитализаций")
		@Persist
		public Long getCountHospitals() {return theCountHospitals;}
		public void setCountHospitals(Long aCountHospitals) {theCountHospitals = aCountHospitals;}
		/** Количество госпитализаций */
		private Long theCountHospitals;
		
		/** Отделение */
		@Comment("Отделение")
		@Persist
		public Long getDepartment() {return theDepartment;}
		public void setDepartment(Long aDepartment) {theDepartment = aDepartment;}
		/** Отделение */
		private Long theDepartment;
		
		/** Тип коек */
		@Comment("Тип коек")
		@Persist
		public Long getBedSubType() {return theBedSubType;}
		public void setBedSubType(Long aBedSubType) {theBedSubType = aBedSubType;}
		/** Тип коек */
		private Long theBedSubType;
		
}
