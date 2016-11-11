package ru.ecom.mis.ejb.domain.expert;

import javax.persistence.Entity;

import ru.ecom.ejb.domain.simple.BaseEntity;
import ru.nuzmsh.commons.formpersistence.annotation.Comment;

@Entity
public class QualityEstimationCritDefect extends BaseEntity {
	
	/** Критерий оценки */
	@Comment("Критерий оценки")
	public Long getCriterion() {return theCriterion;}
	public void setCriterion(Long aCriterion) {theCriterion = aCriterion;}
	/** Критерий оценки */
	private Long theCriterion;
	
	/** Дефект */
	@Comment("Дефект")
	public Long getDefect() {return theDefect;}
	public void setDefect(Long aDefect) {theDefect = aDefect;}
	/** Дефект */
	private Long theDefect;

}
