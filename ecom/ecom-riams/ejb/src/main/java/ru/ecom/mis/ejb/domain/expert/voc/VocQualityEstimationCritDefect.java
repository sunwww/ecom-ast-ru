package ru.ecom.mis.ejb.domain.expert.voc;

import ru.ecom.ejb.domain.simple.VocBaseEntity;
import ru.ecom.ejb.services.index.annotation.AIndex;
import ru.nuzmsh.commons.formpersistence.annotation.Comment;

import javax.persistence.Entity;

@Entity
@AIndex(properties="criterion")
public class VocQualityEstimationCritDefect extends VocBaseEntity {

	/** Критерий оценки качества */
	@Comment("Критерий оценки качества")
	public Long getCriterion() {return theCriterion;}
	public void setCriterion(Long aCriterion) {theCriterion = aCriterion;}
	/** Критерий оценки качества */
	private Long theCriterion;
}
