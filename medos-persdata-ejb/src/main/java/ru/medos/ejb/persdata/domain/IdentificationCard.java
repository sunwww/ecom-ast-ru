package ru.medos.ejb.persdata.domain;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import ru.medos.ejb.persdata.domain.CardTransferAct;
import ru.nuzmsh.commons.formpersistence.annotation.Comment;

	/**
	 * Идентификационная карта
	 */
	@Comment("Идентификационная карта")
@Entity
@Table(schema="SQLUser")
public class IdentificationCard extends OutgoingDocument{
	/**
	 * Акт передачи
	 */
	@Comment("Акт передачи")
	@ManyToOne
	public CardTransferAct getAct() {
		return theAct;
	}
	public void setAct(CardTransferAct aAct) {
		theAct = aAct;
	}
	/**
	 * Акт передачи
	 */
	private CardTransferAct theAct;
}
