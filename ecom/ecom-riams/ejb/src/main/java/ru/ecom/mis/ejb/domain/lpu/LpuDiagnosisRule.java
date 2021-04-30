package ru.ecom.mis.ejb.domain.lpu;

import lombok.Getter;
import lombok.Setter;
import ru.ecom.ejb.domain.simple.BaseEntity;
import ru.nuzmsh.commons.formpersistence.annotation.Comment;
import javax.persistence.Entity;

@Entity
@Getter
@Setter
public class LpuDiagnosisRule  extends BaseEntity{
	
	/** Разрешающее правило */
	private Boolean permissionRule;

	/** Поток обслуживания */
	private Long serviceStream;
	
	/** Отделение */
	private Long department;
	
	/** Тип регистрации диагноза */
	private Long diagnosisRegistrationType;
	
	/** Приоритет диагноза */
	private Long diagnosisPriority;
	
	/** Пол */
	private Long sex;
}
