package ru.ecom.mis.ejb.domain.lpu;

import javax.persistence.Entity;

import lombok.Getter;
import lombok.Setter;
import ru.ecom.ejb.domain.simple.BaseEntity;
import ru.nuzmsh.commons.formpersistence.annotation.Comment;

@Entity
@Getter
@Setter
public class LpuContractNosologyGroup extends BaseEntity {
	
	/** Правило диагноза в отделении */
	private Long lpuDiagnosisRule;
	
	/** Нозологическая группа */
	private Long nosologyGroup;

}
 	