package ru.ecom.mis.ejb.domain.vaccination.voc;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import lombok.Getter;
import lombok.Setter;
import ru.ecom.ejb.domain.simple.BaseEntity;
import ru.ecom.mis.ejb.domain.vaccination.VaccinationNosology;
import ru.ecom.mis.ejb.domain.vaccination.Vaccine;
import ru.nuzmsh.commons.formpersistence.annotation.Comment;

/**
 * Нозология вакцины
 * @author oegorova
 *
 */

@Comment("Нозология вакцины")
@Entity
@Table(schema="SQLUser")
@Getter
@Setter
public class VaccineVocNosology extends BaseEntity{
	
	/** Нозология */
	@Comment("Нозология")
	@ManyToOne
	public VaccinationNosology getNosology() {
		return nosology;
	}

	/** Нозология */
	private VaccinationNosology nosology;
	
	/** Вацина */
	@Comment("Вацина")
	@ManyToOne
	public Vaccine getVaccine() {
		return vaccine;
	}

	/** Вацина */
	private Vaccine vaccine;

}
