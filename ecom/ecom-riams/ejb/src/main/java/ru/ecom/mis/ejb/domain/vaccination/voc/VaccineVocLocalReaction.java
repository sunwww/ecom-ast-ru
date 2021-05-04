package ru.ecom.mis.ejb.domain.vaccination.voc;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import lombok.Getter;
import lombok.Setter;
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
@Getter
@Setter
public class VaccineVocLocalReaction extends BaseEntity{
	
	/** Локальная реакция */
	@Comment("Локальная реакция")
	@ManyToOne
	public VocVaccinationLocalReaction getLocalReaction() {
		return localReaction;
	}

	/** Локальная реакция */
	private VocVaccinationLocalReaction localReaction;
	
	/** Вакцина */
	@Comment("Вакцина")
	@ManyToOne
	public Vaccine getVaccine() {
		return vaccine;
	}

	/** Вакцина */
	private Vaccine vaccine;

}
