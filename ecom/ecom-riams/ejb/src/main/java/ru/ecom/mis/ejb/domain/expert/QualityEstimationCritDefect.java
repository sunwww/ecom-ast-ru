package ru.ecom.mis.ejb.domain.expert;

import javax.persistence.Entity;

import lombok.Getter;
import lombok.Setter;
import ru.ecom.ejb.domain.simple.BaseEntity;
import ru.nuzmsh.commons.formpersistence.annotation.Comment;

@Entity
@Getter
@Setter
public class QualityEstimationCritDefect extends BaseEntity {
	/** Критерий оценки */
	private Long criterion;
	/** Дефект */
	private Long defect;

}
