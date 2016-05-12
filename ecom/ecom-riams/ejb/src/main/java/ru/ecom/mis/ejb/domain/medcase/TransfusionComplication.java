package ru.ecom.mis.ejb.domain.medcase;

import javax.persistence.Entity;
import javax.persistence.OneToOne;
import javax.persistence.Table;

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
public class TransfusionComplication extends BaseEntity {
	/** Переливание */
	@Comment("Переливание")
	@OneToOne
	public Transfusion getTransfusion() {return theTransfusion;}
	public void setTransfusion(Transfusion aTransfusion) {theTransfusion = aTransfusion;}

	/** Осложнение */
	@Comment("Осложнение")
	@OneToOne
	public VocTransfusionReaction getReaction() {return theReaction;}
	public void setReaction(VocTransfusionReaction aReaction) {theReaction = aReaction;}

	/** Осложнение */
	private VocTransfusionReaction theReaction;
	/** Переливание */
	private Transfusion theTransfusion;
}
