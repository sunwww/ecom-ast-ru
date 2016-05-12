package ru.ecom.mis.ejb.domain.ambulance;

import javax.persistence.Entity;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import ru.ecom.ejb.domain.simple.BaseEntity;
import ru.ecom.ejb.services.index.annotation.AIndex;
import ru.ecom.ejb.services.index.annotation.AIndexes;
import ru.ecom.mis.ejb.domain.medcase.MedCase;
import ru.nuzmsh.commons.formpersistence.annotation.Comment;

@Entity
@Table(schema="SQLUser")
@AIndexes(value = { @AIndex(properties = { "medCase" }) })
public class AmbulanceCard extends BaseEntity {
	/** Номер карты скорой помощи */
	@Comment("Номер карты скорой помощи")
	public String getNumberCard() {return theNumberCard;}
	public void setNumberCard(String aNumberCard) {theNumberCard = aNumberCard;}

	/** Номер карты скорой помощи */
	private String theNumberCard;
	/** СМО */
	@Comment("СМО")
	@OneToOne
	public MedCase getMedCase() {return theMedCase;}
	public void setMedCase(MedCase aMedCase) {theMedCase = aMedCase;}

	/** СМО */
	private MedCase theMedCase;
}
