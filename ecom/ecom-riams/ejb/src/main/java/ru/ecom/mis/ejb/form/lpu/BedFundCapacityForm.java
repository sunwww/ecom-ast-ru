package ru.ecom.mis.ejb.form.lpu;

import lombok.Setter;
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
@Setter

public class BedFundCapacityForm extends IdEntityForm{	 

		/** Профиль коек */
		@Comment("Профиль коек")
		@Persist @Required
		public Long getBedType() {return bedType;}
		/** Профиль коек */
		private Long bedType;
		
		/** Источник финансирования */
		@Comment("Источник финансирования")
		@Persist @Required
		public Long getFinanceSource() {return financeSource;}
		/** Источник финансирования */
		private Long financeSource;
		
		/** Фактическое количество коек */
		@Comment("Фактическое количество коек")
		@Persist
		public Long getActualBedCount() {return actualBedCount;}
		/** Фактическое количество коек */
		private Long actualBedCount;
		
		/** Дата начала действия */
		@Comment("Дата начала действия")
		@Persist @DoDateString @DateString 
		@Required
		public String getStartDate() {return startDate;}
		/** Дата начала действия */
		private String startDate;
		
		/** Дата окончания действия */
		@Comment("Дата окончания действия")
		@Persist @DoDateString @DateString
		public String getFinishDate() {return finishDate;}
		/** Дата окончания действия */
		private String finishDate;
		
		/** Средняя длительность лечения */
		@Comment("Средняя длительность лечения")
		@Persist
		public Float getPlanTreatmentDuration() {return planTreatmentDuration;}
		/** Средняя длительность лечения */
		private Float planTreatmentDuration;
		
		/** Количество дней работы в году (план) */
		@Comment("Количество дней работы в году (план)")
		@Persist
		public Long getWorkingDays() {return workingDays;}
		/** Количество дней работы в году (план) */
		private Long workingDays;
		
		/** Количество коек по плану*/
		@Comment("Количество коек по плану")
		@Persist @Required
		public Long getPlanBedCount() {return planBedCount;}
		/** Количество коек по плану*/
		private Long planBedCount;
		
		/** Количество койкодней (план) */
		@Comment("Количество койкодней (план)")
		@Persist
		public Long getCountDays() {return countDays;}
		/** Количество койкодней (план) */
		private Long countDays;
		
		/** Количество госпитализаций */
		@Comment("Количество госпитализаций")
		@Persist
		public Long getCountHospitals() {return countHospitals;}
		/** Количество госпитализаций */
		private Long countHospitals;
		
		/** Отделение */
		@Comment("Отделение")
		@Persist
		public Long getDepartment() {return department;}
		/** Отделение */
		private Long department;
		
		/** Тип коек */
		@Comment("Тип коек")
		@Persist
		public Long getBedSubType() {return bedSubType;}
		/** Тип коек */
		private Long bedSubType;
		
}
