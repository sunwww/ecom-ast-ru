package ru.ecom.mis.ejb.domain.expert.voc;

import lombok.Getter;
import lombok.Setter;
import ru.ecom.ejb.domain.simple.VocBaseEntity;
import ru.ecom.ejb.services.index.annotation.AIndex;
import ru.nuzmsh.commons.formpersistence.annotation.Comment;

import javax.persistence.Entity;

@Entity
@AIndex(properties="criterion")
@Getter
@Setter
public class VocQualityEstimationCritDefect extends VocBaseEntity {

	/** Критерий оценки качества */
	private Long criterion;
}
