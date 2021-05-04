package ru.ecom.mis.ejb.domain.medcase;

import javax.persistence.Entity;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import lombok.Getter;
import lombok.Setter;
import ru.ecom.ejb.domain.simple.BaseEntity;
import ru.ecom.ejb.services.index.annotation.AIndex;
import ru.ecom.ejb.services.index.annotation.AIndexes;
import ru.ecom.mis.ejb.domain.medcase.voc.VocTransfusionReaction;
import ru.nuzmsh.commons.formpersistence.annotation.Comment;

@Entity
@AIndexes({
	@AIndex(properties="transfusion")
    })
@Table(schema="SQLUser")
@Getter
@Setter
public class TransfusionComplication extends BaseEntity {
	/** Переливание */
	@Comment("Переливание")
	@OneToOne
	public Transfusion getTransfusion() {return transfusion;}

	/** Осложнение */
	@Comment("Осложнение")
	@OneToOne
	public VocTransfusionReaction getReaction() {return reaction;}

	/** Осложнение */
	private VocTransfusionReaction reaction;
	/** Переливание */
	private Transfusion transfusion;
}
