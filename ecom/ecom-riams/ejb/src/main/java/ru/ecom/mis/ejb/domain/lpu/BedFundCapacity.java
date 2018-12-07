package ru.ecom.mis.ejb.domain.lpu;

import ru.ecom.ejb.domain.simple.BaseEntity;
import ru.ecom.ejb.services.index.annotation.AIndex;
import ru.ecom.ejb.services.index.annotation.AIndexes;
import ru.nuzmsh.commons.formpersistence.annotation.Comment;

import javax.persistence.Entity;
import javax.persistence.Table;
import java.sql.Date;

@Entity
@Comment("Объемы коечного фонда")
@Table(schema="SQLUser")
@AIndexes(value={
		@AIndex(properties={"department","bedType"})
})
public class BedFundCapacity extends BaseEntity{

	/** Профиль коек */
	@Comment("Профиль коек")
	public Long getBedType() {return theBedType;}
	public void setBedType(Long aBedType) {theBedType = aBedType;}
	/** Профиль коек */
	private Long theBedType;
	
	/** Источник финансирования */
	@Comment("Источник финансирования")
	public Long getFinanceSource() {return theFinanceSource;}
	public void setFinanceSource(Long aFinanceSource) {theFinanceSource = aFinanceSource;}
	/** Источник финансирования */
	private Long theFinanceSource;
	
	/** Фактическое количество коек */
	@Comment("Фактическое количество коек")
	public Long getActualBedCount() {return theActualBedCount;}
	public void setActualBedCount(Long aActualBedCount) {theActualBedCount = aActualBedCount;}
	/** Фактическое количество коек */
	private Long theActualBedCount;
	
	/** Дата начала действия */
	@Comment("Дата начала действия")
	public Date getStartDate() {return theStartDate;}
	public void setStartDate(Date aStartDate) {theStartDate = aStartDate;}
	/** Дата начала действия */
	private Date theStartDate;
	
	/** Дата окончания действия */
	@Comment("Дата окончания действия")
	public Date getFinishDate() {return theFinishDate;}
	public void setFinishDate(Date aFinishDate) {theFinishDate = aFinishDate;}
	/** Дата окончания действия */
	private Date theFinishDate;
	
	/** Средняя длительность лечения */
	@Comment("Средняя длительность лечения")
	public Float getPlanTreatmentDuration() {return thePlanTreatmentDuration;}
	public void setPlanTreatmentDuration(Float aPlanTreatmentDuration) {thePlanTreatmentDuration = aPlanTreatmentDuration;}
	/** Средняя длительность лечения */
	private Float thePlanTreatmentDuration;
	
	/** Количество дней работы в году (план) */
	@Comment("Количество дней работы в году (план)")
	public Long getWorkingDays() {return theWorkingDays;}
	public void setWorkingDays(Long aWorkingDays) {theWorkingDays = aWorkingDays;}
	/** Количество дней работы в году (план) */
	private Long theWorkingDays;
	
	/** Количество коек по плану*/
	@Comment("Количество коек по плану")
	public Long getPlanBedCount() {return thePlanBedCount;}
	public void setPlanBedCount(Long aPlanBedCount) {thePlanBedCount = aPlanBedCount;}
	/** Количество коек по плану*/
	private Long thePlanBedCount;
	
	/** Количество койкодней (план) */
	@Comment("Количество койкодней (план)")
	public Long getCountDays() {return theCountDays;}
	public void setCountDays(Long aCountDays) {theCountDays = aCountDays;}
	/** Количество койкодней (план) */
	private Long theCountDays;
	
	/** Количество госпитализаций */
	@Comment("Количество госпитализаций")
	public Long getCountHospitals() {return theCountHospitals;}
	public void setCountHospitals(Long aCountHospitals) {theCountHospitals = aCountHospitals;}
	/** Количество госпитализаций */
	private Long theCountHospitals;
	
	/** Отделение */
	@Comment("Отделение")
	public Long getDepartment() {return theDepartment;}
	public void setDepartment(Long aDepartment) {theDepartment = aDepartment;}
	/** Отделение */
	private Long theDepartment;
	
	/** Тип коек */
	@Comment("Тип коек")
	public Long getBedSubType() {return theBedSubType;}
	public void setBedSubType(Long aBedSubType) {theBedSubType = aBedSubType;}
	/** Тип коек */
	private Long theBedSubType;
	
}
