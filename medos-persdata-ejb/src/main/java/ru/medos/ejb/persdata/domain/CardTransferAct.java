package ru.medos.ejb.persdata.domain;

import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import ru.nuzmsh.commons.formpersistence.annotation.Comment;

	/**
	 * Акт передачи идентификационных карт
	 */
	@Comment("Акт передачи идентификационных карт")
@Entity
@Table(schema="SQLUser")
public class CardTransferAct extends Act{
	@OneToMany(mappedBy="cardTransferAct", cascade=CascadeType.ALL)
	public List<IdentificationCard> getIdentificationCards() {
		return theIdentificationCards;
	}
	public void setIdentificationCards(List<IdentificationCard> aIdentificationCards) {
		theIdentificationCards = aIdentificationCards;
	}
	/**
	 * Идентификационные карты
	 */
	private List<IdentificationCard> theIdentificationCards;
}
