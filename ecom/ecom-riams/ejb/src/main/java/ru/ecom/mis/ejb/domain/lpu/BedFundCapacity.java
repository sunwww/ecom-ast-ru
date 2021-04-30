package ru.ecom.mis.ejb.domain.lpu;

import lombok.Getter;
import lombok.Setter;
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
@Getter
@Setter
public class BedFundCapacity extends BaseEntity{

	/** Профиль коек */
	private Long bedType;
	
	/** Источник финансирования */
	private Long financeSource;
	
	/** Фактическое количество коек */
	private Long actualBedCount;
	
	/** Дата начала действия */
	private Date startDate;
	
	/** Дата окончания действия */
	private Date finishDate;
	
	/** Средняя длительность лечения */
	private Float planTreatmentDuration;
	
	/** Количество дней работы в году (план) */
	private Long workingDays;
	
	/** Количество коек по плану*/
	private Long planBedCount;
	
	/** Количество койкодней (план) */
	private Long countDays;
	
	/** Количество госпитализаций */
	private Long countHospitals;
	
	/** Отделение */
	private Long department;
	
	/** Тип коек */
	private Long bedSubType;
	
}
