package ru.ecom.mis.ejb.domain.vaccination.voc;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import ru.ecom.ejb.domain.simple.BaseEntity;
import ru.ecom.mis.ejb.domain.vaccination.Vaccine;
import ru.nuzmsh.commons.formpersistence.annotation.Comment;

/**
 * Локальные реакции по вакцине
 * @author oegorova
 *
 */

@Comment("Локальные реакции по вакцине")
@Entity
@Table(schema="SQLUser")
public class VaccineVocLocalReaction extends BaseEntity{
	
	/** Локальная реакция */
	@Comment("Локальная реакция")
	@ManyToOne
	public VocVaccinationLocalReaction getLocalReaction() {
		return theLocalReaction;
	}

	public void setLocalReaction(VocVaccinationLocalReaction aLocalReaction) {
		theLocalReaction = aLocalReaction;
	}

	/** Локальная реакция */
	private VocVaccinationLocalReaction theLocalReaction;
	
	/** Вакцина */
	@Comment("Вакцина")
	@ManyToOne
	public Vaccine getVaccine() {
		return theVaccine;
	}

	public void setVaccine(Vaccine aVaccine) {
		theVaccine = aVaccine;
	}

	/** Вакцина */
	private Vaccine theVaccine;

}
