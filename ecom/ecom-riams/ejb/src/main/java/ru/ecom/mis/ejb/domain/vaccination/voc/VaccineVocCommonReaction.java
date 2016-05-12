package ru.ecom.mis.ejb.domain.vaccination.voc;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import ru.ecom.ejb.domain.simple.BaseEntity;
import ru.ecom.mis.ejb.domain.vaccination.Vaccine;
import ru.nuzmsh.commons.formpersistence.annotation.Comment;

/**
 * Общие реакции по вакцинам
 * @author oegorova
 *
 */

@Comment("Общие реакции по вакцинам")
@Entity
@Table(schema="SQLUser")
public class VaccineVocCommonReaction extends BaseEntity{
	
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
	
	/** Общая реакция */
	@Comment("Общая реакция")
	@ManyToOne
	public VocVaccinationCommonReaction getCommonReaction() {
		return theCommonReaction;
	}

	public void setCommonReaction(VocVaccinationCommonReaction aCommonReaction) {
		theCommonReaction = aCommonReaction;
	}

	/** Общая реакция */
	private VocVaccinationCommonReaction theCommonReaction;
	


}
